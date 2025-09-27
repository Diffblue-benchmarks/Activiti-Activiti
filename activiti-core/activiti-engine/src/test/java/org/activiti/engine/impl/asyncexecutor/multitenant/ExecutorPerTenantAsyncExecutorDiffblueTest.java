/*
 * Copyright 2010-2020 Alfresco Software, Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.engine.impl.asyncexecutor.multitenant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import groovy.lang.GroovyClassLoader;
import jakarta.transaction.TransactionManager;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;
import javax.script.ScriptEngineManager;
import javax.xml.namespace.QName;
import org.activiti.api.runtime.shared.identity.UserGroupManager;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.impl.DynamicBpmnServiceImpl;
import org.activiti.engine.impl.HistoryServiceImpl;
import org.activiti.engine.impl.ManagementServiceImpl;
import org.activiti.engine.impl.ProcessDefinitionHelper;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.asyncexecutor.AcquireAsyncJobsDueRunnable;
import org.activiti.engine.impl.asyncexecutor.AcquireTimerJobsRunnable;
import org.activiti.engine.impl.asyncexecutor.AsyncExecutor;
import org.activiti.engine.impl.asyncexecutor.DefaultAsyncJobExecutor;
import org.activiti.engine.impl.asyncexecutor.DefaultJobManager;
import org.activiti.engine.impl.asyncexecutor.ExecuteAsyncRunnableFactory;
import org.activiti.engine.impl.asyncexecutor.ManagedAsyncJobExecutor;
import org.activiti.engine.impl.asyncexecutor.ResetExpiredJobsRunnable;
import org.activiti.engine.impl.bpmn.deployer.BpmnDeploymentHelper;
import org.activiti.engine.impl.bpmn.deployer.EventSubscriptionManager;
import org.activiti.engine.impl.bpmn.deployer.ParsedDeploymentBuilderFactory;
import org.activiti.engine.impl.bpmn.deployer.TimerManager;
import org.activiti.engine.impl.bpmn.listener.ListenerNotificationHelper;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.calendar.BusinessCalendarManager;
import org.activiti.engine.impl.cfg.BpmnParseFactory;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.cfg.DelegateExpressionFieldInjectionMode;
import org.activiti.engine.impl.cfg.IdGenerator;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.PerformanceSettings;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.TransactionContextFactory;
import org.activiti.engine.impl.cfg.multitenant.TenantInfoHolder;
import org.activiti.engine.impl.db.DbSqlSessionFactory;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.jobexecutor.FailedJobCommandFactory;
import org.activiti.engine.impl.persistence.deploy.DefaultDeploymentCache;
import org.activiti.engine.impl.persistence.deploy.DeploymentManager;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionInfoEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.ResourceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.TableDataManagerImpl;
import org.activiti.engine.impl.persistence.entity.TimerJobEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisAttachmentDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisByteArrayDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisDeadLetterJobDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisDeploymentDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisEventSubscriptionDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisExecutionDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricActivityInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricIdentityLinkDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricTaskInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisIdentityLinkDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisProcessDefinitionDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisProcessDefinitionInfoDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisPropertyDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisResourceDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisTimerJobDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisVariableInstanceDataManager;
import org.activiti.engine.impl.scripting.ScriptingEngines;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.impl.variable.DefaultVariableTypes;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.test.cfg.multitenant.DummyTenantInfoHolder;
import org.activiti.engine.test.impl.logger.ProcessExecutionLoggerConfigurator;
import org.activiti.engine.test.regression.ActivitiTestCaseProcessValidator;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ExecutorPerTenantAsyncExecutorDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <ul>
   *   <li>When {@link DummyTenantInfoHolder} (default constructor).
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ExecutorPerTenantAsyncExecutor#ExecutorPerTenantAsyncExecutor(TenantInfoHolder)}
   *   <li>{@link ExecutorPerTenantAsyncExecutor#setAutoActivate(boolean)}
   *   <li>{@link ExecutorPerTenantAsyncExecutor#isActive()}
   *   <li>{@link ExecutorPerTenantAsyncExecutor#isAutoActivate()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutorPerTenantAsyncExecutor.<init>(TenantInfoHolder)",
    "void ExecutorPerTenantAsyncExecutor.<init>(TenantInfoHolder, TenantAwareAsyncExecutorFactory)",
    "boolean ExecutorPerTenantAsyncExecutor.isActive()",
    "boolean ExecutorPerTenantAsyncExecutor.isAutoActivate()",
    "void ExecutorPerTenantAsyncExecutor.setAutoActivate(boolean)"
  })
  public void testGettersAndSetters_whenDummyTenantInfoHolder() {
    // Arrange and Act
    ExecutorPerTenantAsyncExecutor actualExecutorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    actualExecutorPerTenantAsyncExecutor.setAutoActivate(true);
    boolean actualIsActiveResult = actualExecutorPerTenantAsyncExecutor.isActive();
    boolean actualIsAutoActivateResult = actualExecutorPerTenantAsyncExecutor.isAutoActivate();

    // Assert
    assertFalse(actualIsActiveResult);
    assertTrue(actualExecutorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(actualIsAutoActivateResult);
  }

  /**
   * Test getters and setters.
   *
   * <ul>
   *   <li>When {@link TenantAwareAsyncExecutorFactory}.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ExecutorPerTenantAsyncExecutor#ExecutorPerTenantAsyncExecutor(TenantInfoHolder,
   *       TenantAwareAsyncExecutorFactory)}
   *   <li>{@link ExecutorPerTenantAsyncExecutor#setAutoActivate(boolean)}
   *   <li>{@link ExecutorPerTenantAsyncExecutor#isActive()}
   *   <li>{@link ExecutorPerTenantAsyncExecutor#isAutoActivate()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutorPerTenantAsyncExecutor.<init>(TenantInfoHolder)",
    "void ExecutorPerTenantAsyncExecutor.<init>(TenantInfoHolder, TenantAwareAsyncExecutorFactory)",
    "boolean ExecutorPerTenantAsyncExecutor.isActive()",
    "boolean ExecutorPerTenantAsyncExecutor.isAutoActivate()",
    "void ExecutorPerTenantAsyncExecutor.setAutoActivate(boolean)"
  })
  public void testGettersAndSetters_whenTenantAwareAsyncExecutorFactory() {
    // Arrange and Act
    ExecutorPerTenantAsyncExecutor actualExecutorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), mock(TenantAwareAsyncExecutorFactory.class));
    actualExecutorPerTenantAsyncExecutor.setAutoActivate(true);
    boolean actualIsActiveResult = actualExecutorPerTenantAsyncExecutor.isActive();
    boolean actualIsAutoActivateResult = actualExecutorPerTenantAsyncExecutor.isAutoActivate();

    // Assert
    assertFalse(actualIsActiveResult);
    assertTrue(actualExecutorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(actualIsAutoActivateResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set ExecutorPerTenantAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds() {
    // Arrange, Act and Assert
    assertTrue(
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder()).getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.addTenantAsyncExecutor(String, boolean)"})
  public void testAddTenantAsyncExecutor() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(
        ((DefaultAsyncJobExecutor) getResult).getExecutorService() instanceof ThreadPoolExecutor);
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    ExecuteAsyncRunnableFactory executeAsyncRunnableFactory =
        ((DefaultAsyncJobExecutor) getResult).getExecuteAsyncRunnableFactory();
    assertTrue(executeAsyncRunnableFactory instanceof TenantAwareExecuteAsyncRunnableFactory);
    assertTrue(
        executorPerTenantAsyncExecutor.processEngineConfiguration
            instanceof JtaProcessEngineConfiguration);
    assertSame(processEngineConfiguration, getResult.getProcessEngineConfiguration());
    assertSame(
        executorPerTenantAsyncExecutor.tenantInfoHolder,
        ((TenantAwareExecuteAsyncRunnableFactory) executeAsyncRunnableFactory).tenantInfoHolder);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.addTenantAsyncExecutor(String, boolean)"})
  public void testAddTenantAsyncExecutor2() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    StandaloneInMemProcessEngineConfiguration processEngineConfiguration =
        new StandaloneInMemProcessEngineConfiguration();
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(
        ((DefaultAsyncJobExecutor) getResult).getExecutorService() instanceof ThreadPoolExecutor);
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    ExecuteAsyncRunnableFactory executeAsyncRunnableFactory =
        ((DefaultAsyncJobExecutor) getResult).getExecuteAsyncRunnableFactory();
    assertTrue(executeAsyncRunnableFactory instanceof TenantAwareExecuteAsyncRunnableFactory);
    assertTrue(
        executorPerTenantAsyncExecutor.processEngineConfiguration
            instanceof StandaloneInMemProcessEngineConfiguration);
    assertSame(processEngineConfiguration, getResult.getProcessEngineConfiguration());
    assertSame(
        executorPerTenantAsyncExecutor.tenantInfoHolder,
        ((TenantAwareExecuteAsyncRunnableFactory) executeAsyncRunnableFactory).tenantInfoHolder);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.addTenantAsyncExecutor(String, boolean)"})
  public void testAddTenantAsyncExecutor3() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    processEngineConfiguration.setCommandExecutor(commandExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(
        ((DefaultAsyncJobExecutor) getResult).getExecutorService() instanceof ThreadPoolExecutor);
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    ExecuteAsyncRunnableFactory executeAsyncRunnableFactory =
        ((DefaultAsyncJobExecutor) getResult).getExecuteAsyncRunnableFactory();
    assertTrue(executeAsyncRunnableFactory instanceof TenantAwareExecuteAsyncRunnableFactory);
    assertTrue(
        executorPerTenantAsyncExecutor.processEngineConfiguration
            instanceof JtaProcessEngineConfiguration);
    assertSame(processEngineConfiguration, getResult.getProcessEngineConfiguration());
    assertSame(
        executorPerTenantAsyncExecutor.tenantInfoHolder,
        ((TenantAwareExecuteAsyncRunnableFactory) executeAsyncRunnableFactory).tenantInfoHolder);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.addTenantAsyncExecutor(String, boolean)"})
  public void testAddTenantAsyncExecutor4() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomFunctionProviders(new ArrayList<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(
        ((DefaultAsyncJobExecutor) getResult).getExecutorService() instanceof ThreadPoolExecutor);
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    ExecuteAsyncRunnableFactory executeAsyncRunnableFactory =
        ((DefaultAsyncJobExecutor) getResult).getExecuteAsyncRunnableFactory();
    assertTrue(executeAsyncRunnableFactory instanceof TenantAwareExecuteAsyncRunnableFactory);
    assertTrue(
        executorPerTenantAsyncExecutor.processEngineConfiguration
            instanceof JtaProcessEngineConfiguration);
    assertSame(processEngineConfiguration, getResult.getProcessEngineConfiguration());
    assertSame(
        executorPerTenantAsyncExecutor.tenantInfoHolder,
        ((TenantAwareExecuteAsyncRunnableFactory) executeAsyncRunnableFactory).tenantInfoHolder);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.addTenantAsyncExecutor(String, boolean)"})
  public void testAddTenantAsyncExecutor5() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(null);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneInMemProcessEngineConfiguration());

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(
        ((DefaultAsyncJobExecutor) getResult).getExecutorService() instanceof ThreadPoolExecutor);
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    ExecuteAsyncRunnableFactory executeAsyncRunnableFactory =
        ((DefaultAsyncJobExecutor) getResult).getExecuteAsyncRunnableFactory();
    assertTrue(executeAsyncRunnableFactory instanceof TenantAwareExecuteAsyncRunnableFactory);
    assertTrue(
        executorPerTenantAsyncExecutor.processEngineConfiguration
            instanceof StandaloneInMemProcessEngineConfiguration);
    assertNull(
        ((TenantAwareExecuteAsyncRunnableFactory) executeAsyncRunnableFactory).tenantInfoHolder);
    assertSame(
        executorPerTenantAsyncExecutor.processEngineConfiguration,
        getResult.getProcessEngineConfiguration());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.addTenantAsyncExecutor(String, boolean)"})
  public void testAddTenantAsyncExecutor6() {
    // Arrange
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(defaultAsyncJobExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneInMemProcessEngineConfiguration());

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    assertSame(defaultAsyncJobExecutor, stringAsyncExecutorMap.get("42"));
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.addTenantAsyncExecutor(String, boolean)"})
  public void testAddTenantAsyncExecutor7() {
    // Arrange
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenThrow(new UnsupportedOperationException());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneInMemProcessEngineConfiguration());

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true));
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.addTenantAsyncExecutor(String, boolean)"})
  public void testAddTenantAsyncExecutor8() {
    // Arrange
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(executorPerTenantAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor2 =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor2.setProcessEngineConfiguration(
        new StandaloneInMemProcessEngineConfiguration());

    // Act
    executorPerTenantAsyncExecutor2.addTenantAsyncExecutor("42", true);

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor2.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    assertSame(executorPerTenantAsyncExecutor, stringAsyncExecutorMap.get("42"));
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.addTenantAsyncExecutor(String, boolean)"})
  public void testAddTenantAsyncExecutor9() {
    // Arrange
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneInMemProcessEngineConfiguration());

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    assertSame(sharedExecutorServiceAsyncExecutor, stringAsyncExecutorMap.get("42"));
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.addTenantAsyncExecutor(String, boolean)"})
  public void testAddTenantAsyncExecutor10() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        mock(SharedExecutorServiceAsyncExecutor.class);
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneInMemProcessEngineConfiguration());

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof StandaloneInMemProcessEngineConfiguration);
    Set<String> tenantIds = executorPerTenantAsyncExecutor.getTenantIds();
    assertEquals(1, tenantIds.size());
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(tenantIds.contains("42"));
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.addTenantAsyncExecutor(String, boolean)"})
  public void testAddTenantAsyncExecutor11() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        mock(SharedExecutorServiceAsyncExecutor.class);
    doThrow(new UnsupportedOperationException())
        .when(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneInMemProcessEngineConfiguration());

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true));
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.addTenantAsyncExecutor(String, boolean)"})
  public void testAddTenantAsyncExecutor12() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        mock(SharedExecutorServiceAsyncExecutor.class);
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", false);

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor).setProcessEngineConfiguration(isNull());
    verify(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    Set<String> tenantIds = executorPerTenantAsyncExecutor.getTenantIds();
    assertEquals(1, tenantIds.size());
    assertTrue(stringAsyncExecutorMap.containsKey("42"));
    assertTrue(tenantIds.contains("42"));
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    TenantInfoHolder tenantInfoHolder = executorPerTenantAsyncExecutor.tenantInfoHolder;
    Collection<String> allTenants = tenantInfoHolder.getAllTenants();
    assertTrue(allTenants instanceof Set);
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(tenantInfoHolder instanceof DummyTenantInfoHolder);
    assertTrue(allTenants.isEmpty());
    assertEquals(
        executorPerTenantAsyncExecutor.tenantExecutors,
        processEngineConfigurationImpl.getWsOverridenEndpointAddresses());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor2() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(null);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor3() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doThrow(new UnsupportedOperationException())
        .when(tenantInfoHolder)
        .setCurrentTenantId(Mockito.<String>any());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor4() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneInMemProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof StandaloneInMemProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor5() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor6() throws MalformedURLException {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("exception during timer job acquisition: {}"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertEquals(1, processEngineConfigurationImpl.getWsOverridenEndpointAddresses().size());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor7() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor8() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(mock(TenantInfoHolder.class));
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", false);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor9() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomMybatisXMLMappers(new HashSet<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor10() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    processEngineConfiguration.setCommandExecutor(commandExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor11() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setHistoryService(
        new HistoryServiceImpl(new JtaProcessEngineConfiguration()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor12() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setManagementService(new ManagementServiceImpl());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor13() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setUserGroupManager(mock(UserGroupManager.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor14() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setConfigurators(new ArrayList<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor15() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setVariableTypes(new DefaultVariableTypes());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor16() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJavaClassFieldForJackson(
        "exception during timer job acquisition: {}");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor17() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setExpressionManager(new ExpressionManager());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor18() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setBusinessCalendarManager(mock(BusinessCalendarManager.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor19() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setListenerNotificationHelper(new ListenerNotificationHelper());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor20() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomPreVariableTypes(new ArrayList<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor21() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setIdGeneratorDataSourceJndiName(
        "exception during timer job acquisition: {}");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor22() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setVariableInstanceDataManager(
        new MybatisVariableInstanceDataManager(new JtaProcessEngineConfiguration()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor23() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    DeploymentEntityManagerImpl deploymentEntityManager =
        new DeploymentEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setDeploymentEntityManager(deploymentEntityManager);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor24() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    HistoricActivityInstanceEntityManagerImpl historicActivityInstanceEntityManager =
        new HistoricActivityInstanceEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisHistoricActivityInstanceDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setHistoricActivityInstanceEntityManager(
        historicActivityInstanceEntityManager);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor25() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    IdentityLinkEntityManagerImpl identityLinkEntityManager =
        new IdentityLinkEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisIdentityLinkDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setIdentityLinkEntityManager(identityLinkEntityManager);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor26() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    TimerJobEntityManagerImpl timerJobEntityManager =
        new TimerJobEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setTimerJobEntityManager(timerJobEntityManager);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor27() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDelegateExpressionFieldInjectionMode(
        DelegateExpressionFieldInjectionMode.COMPATIBILITY);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor28() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAsyncExecutorMessageQueueMode(true);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor29() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAsyncExecutorActivate(true);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor30() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setClassLoader(new GroovyClassLoader());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor31() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDefaultCamelContext("exception during timer job acquisition: {}");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor32() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setLockTimeAsyncJobWaitTime(1);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor33() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    TenantInfoHolder tenantInfoHolder = executorPerTenantAsyncExecutor.tenantInfoHolder;
    Collection<String> allTenants = tenantInfoHolder.getAllTenants();
    assertTrue(allTenants instanceof Set);
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(tenantInfoHolder instanceof DummyTenantInfoHolder);
    assertTrue(allTenants.isEmpty());
    assertEquals(
        executorPerTenantAsyncExecutor.tenantExecutors,
        processEngineConfigurationImpl.getWsOverridenEndpointAddresses());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor34() {
    // Arrange
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(executorPerTenantAsyncExecutor.tenantInfoHolder instanceof DummyTenantInfoHolder);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor35() {
    // Arrange
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(executorPerTenantAsyncExecutor.tenantInfoHolder instanceof DummyTenantInfoHolder);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor36() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        mock(SharedExecutorServiceAsyncExecutor.class);
    doNothing().when(sharedExecutorServiceAsyncExecutor).shutdown();
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).shutdown();
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    TenantInfoHolder tenantInfoHolder = executorPerTenantAsyncExecutor.tenantInfoHolder;
    Collection<String> allTenants = tenantInfoHolder.getAllTenants();
    assertTrue(allTenants instanceof Set);
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(tenantInfoHolder instanceof DummyTenantInfoHolder);
    assertTrue(allTenants.isEmpty());
    assertEquals(
        executorPerTenantAsyncExecutor.tenantExecutors,
        processEngineConfigurationImpl.getWsOverridenEndpointAddresses());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <ul>
   *   <li>Then calls {@link TenantInfoHolder#clearCurrentTenantId()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor_thenCallsClearCurrentTenantId() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor() {
    // Arrange, Act and Assert
    assertNull(
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder()).determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor2() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor3() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor4() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("exception during resetting expired jobs");
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor5() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.setCurrentTenantId("exception during resetting expired jobs");
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor6() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("exception during resetting expired jobs");
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor7() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("");
    tenantInfoHolder.addTenant("exception during resetting expired jobs");
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor8() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("exception during resetting expired jobs");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setSchemaCommandConfig(new CommandConfig());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor9() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("exception during resetting expired jobs");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomPostCommandInterceptors(new ArrayList<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor10() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("exception during resetting expired jobs");
    tenantInfoHolder.addTenant("42");

    ParsedDeploymentBuilderFactory parsedDeploymentBuilderFactory =
        new ParsedDeploymentBuilderFactory();
    parsedDeploymentBuilderFactory.setBpmnParser(new BpmnParser());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setParsedDeploymentBuilderFactory(parsedDeploymentBuilderFactory);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor11() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("exception during resetting expired jobs");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTransactionContextFactory(mock(TransactionContextFactory.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor12() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("exception during resetting expired jobs");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setPreBpmnParseHandlers(new ArrayList<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor13() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("exception during resetting expired jobs");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setPostBpmnParseHandlers(new ArrayList<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor14() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("exception during resetting expired jobs");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor15() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("exception during resetting expired jobs");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setUsingRelationalDatabase(true);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor16() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("exception during resetting expired jobs");
    tenantInfoHolder.addTenant("42");

    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setPerformanceSettings(performanceSettings);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor17() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("exception during resetting expired jobs");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDeploymentDataManager(
        new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor18() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("exception during resetting expired jobs");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJobManager(new DefaultJobManager());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor19() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("");
    tenantInfoHolder.addTenant("exception during resetting expired jobs");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    processEngineConfiguration.setCommandExecutor(commandExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor20() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("");
    tenantInfoHolder.addTenant("exception during resetting expired jobs");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setIdGenerator(mock(IdGenerator.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor21() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("");
    tenantInfoHolder.addTenant("exception during resetting expired jobs");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setBatchSizeTasks(3);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor22() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("");
    tenantInfoHolder.addTenant("exception during resetting expired jobs");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setHistoricIdentityLinkDataManager(
        new MybatisHistoricIdentityLinkDataManager(new JtaProcessEngineConfiguration()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor23() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("");
    tenantInfoHolder.addTenant("exception during resetting expired jobs");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setMailServerUsername("janedoe");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor24() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("");
    tenantInfoHolder.addTenant("exception during resetting expired jobs");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDatabaseTablePrefix("Database Table Prefix");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   *
   * <ul>
   *   <li>Given {@link DummyTenantInfoHolder} (default constructor) addTenant empty string.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor_givenDummyTenantInfoHolderAddTenantEmptyString() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("");
    tenantInfoHolder.addTenant("exception during resetting expired jobs");
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   *
   * <ul>
   *   <li>Given {@link DummyTenantInfoHolder} (default constructor) addTenant empty string.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor_givenDummyTenantInfoHolderAddTenantEmptyString2() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");
    tenantInfoHolder.addTenant("");
    tenantInfoHolder.addTenant("exception during resetting expired jobs");
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#executeAsyncJob(Job)}.
   *
   * <ul>
   *   <li>Given {@link AsyncExecutor} {@link AsyncExecutor#executeAsyncJob(Job)} return {@code
   *       false}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#executeAsyncJob(Job)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ExecutorPerTenantAsyncExecutor.executeAsyncJob(Job)"})
  public void testExecuteAsyncJob_givenAsyncExecutorExecuteAsyncJobReturnFalse_thenReturnFalse() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");

    AsyncExecutor asyncExecutor = mock(AsyncExecutor.class);
    when(asyncExecutor.executeAsyncJob(Mockito.<Job>any())).thenReturn(false);
    doNothing()
        .when(asyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    doNothing().when(asyncExecutor).start();

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(asyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder, tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        mock(ProcessEngineConfigurationImpl.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    boolean actualExecuteAsyncJobResult =
        executorPerTenantAsyncExecutor.executeAsyncJob(mock(Job.class));

    // Assert
    verify(asyncExecutor).executeAsyncJob(isA(Job.class));
    verify(asyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(asyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    assertFalse(actualExecuteAsyncJobResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#executeAsyncJob(Job)}.
   *
   * <ul>
   *   <li>Given {@link AsyncExecutor} {@link AsyncExecutor#executeAsyncJob(Job)} return {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#executeAsyncJob(Job)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ExecutorPerTenantAsyncExecutor.executeAsyncJob(Job)"})
  public void testExecuteAsyncJob_givenAsyncExecutorExecuteAsyncJobReturnTrue() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");

    AsyncExecutor asyncExecutor = mock(AsyncExecutor.class);
    when(asyncExecutor.executeAsyncJob(Mockito.<Job>any())).thenReturn(true);
    doNothing()
        .when(asyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    doNothing().when(asyncExecutor).start();

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(asyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder, tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        mock(ProcessEngineConfigurationImpl.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    boolean actualExecuteAsyncJobResult =
        executorPerTenantAsyncExecutor.executeAsyncJob(mock(Job.class));

    // Assert
    verify(asyncExecutor).executeAsyncJob(isA(Job.class));
    verify(asyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(asyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    assertTrue(actualExecuteAsyncJobResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getJobManager()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getJobManager()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.asyncexecutor.JobManager ExecutorPerTenantAsyncExecutor.getJobManager()"
  })
  public void testGetJobManager() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder()).getJobManager());
  }

  /**
   * Test {@link
   * ExecutorPerTenantAsyncExecutor#setProcessEngineConfiguration(ProcessEngineConfigurationImpl)}.
   *
   * <p>Method under test: {@link
   * ExecutorPerTenantAsyncExecutor#setProcessEngineConfiguration(ProcessEngineConfigurationImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutorPerTenantAsyncExecutor.setProcessEngineConfiguration(ProcessEngineConfigurationImpl)"
  })
  public void testSetProcessEngineConfiguration() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());

    // Act
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());

    // Assert
    assertTrue(
        executorPerTenantAsyncExecutor.processEngineConfiguration
            instanceof JtaProcessEngineConfiguration);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getProcessEngineConfiguration()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getProcessEngineConfiguration()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfigurationImpl ExecutorPerTenantAsyncExecutor.getProcessEngineConfiguration()"
  })
  public void testGetProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder())
                .getProcessEngineConfiguration());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#start()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#start()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart2() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#start()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart3() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneInMemProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#start()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart4() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#start()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart5() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("foo"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#start()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart6() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("foo"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#start()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart7() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventSubscriptionManager(new EventSubscriptionManager());
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("foo"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#start()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart8() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTransactionContextFactory(mock(TransactionContextFactory.class));
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("foo"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#start()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart9() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("foo"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#start()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart10() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    ProcessDefinitionInfoEntityManagerImpl processDefinitionInfoEntityManager =
        new ProcessDefinitionInfoEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisProcessDefinitionInfoDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setProcessDefinitionInfoEntityManager(
        processDefinitionInfoEntityManager);
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("foo"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#start()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart11() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setProcessEngineName("{} starting to reset expired jobs");
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("foo"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#start()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart12() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setFailedJobCommandFactory(mock(FailedJobCommandFactory.class));
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#start()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart13() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAttachmentDataManager(
        new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#start()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart14() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManager =
        new HistoricTaskInstanceEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisHistoricTaskInstanceDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setHistoricTaskInstanceEntityManager(
        historicTaskInstanceEntityManager);
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#start()}.
   *
   * <ul>
   *   <li>Given {@link DummyTenantInfoHolder} (default constructor) addTenant {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart_givenDummyTenantInfoHolderAddTenant42() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#start()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) CustomMybatisXMLMappers
   *       is {@link HashSet#HashSet()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart_givenJtaProcessEngineConfigurationCustomMybatisXMLMappersIsHashSet()
      throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomMybatisXMLMappers(new HashSet<>());
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("foo"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#start()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) ExecutionQueryLimit is
   *       one.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart_givenJtaProcessEngineConfigurationExecutionQueryLimitIsOne()
      throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setExecutionQueryLimit(1);
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("foo"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#start()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) ExpressionManager is
   *       {@link ExpressionManager#ExpressionManager()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart_givenJtaProcessEngineConfigurationExpressionManagerIsExpressionManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setExpressionManager(new ExpressionManager());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#start()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) HistoricTaskQueryLimit
   *       is one.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart_givenJtaProcessEngineConfigurationHistoricTaskQueryLimitIsOne()
      throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setHistoricTaskQueryLimit(1);
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("foo"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#start()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) RollbackDeployment is
   *       {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart_givenJtaProcessEngineConfigurationRollbackDeploymentIsTrue()
      throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setRollbackDeployment(true);
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("foo"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#start()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) SchemaCommandConfig is
   *       {@link CommandConfig#CommandConfig()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart_givenJtaProcessEngineConfigurationSchemaCommandConfigIsCommandConfig()
      throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setSchemaCommandConfig(new CommandConfig());
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("foo"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#start()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) SessionFactories is
   *       {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart_givenJtaProcessEngineConfigurationSessionFactoriesIsHashMap()
      throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setSessionFactories(new HashMap<>());
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("foo"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown2() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(null);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown3() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown4() {
    // Arrange
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown5() {
    // Arrange
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof ExecutorPerTenantAsyncExecutor);
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown6() {
    // Arrange
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof SharedExecutorServiceAsyncExecutor);
    assertNull(((SharedExecutorServiceAsyncExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((SharedExecutorServiceAsyncExecutor) getResult).getResetExpiredJobThread());
    assertNull(((SharedExecutorServiceAsyncExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((SharedExecutorServiceAsyncExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown7() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(defaultAsyncJobExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown8() {
    // Arrange
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneInMemProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown9() {
    // Arrange
    StandaloneProcessEngineConfiguration processEngineConfiguration =
        new StandaloneProcessEngineConfiguration();
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown10() {
    // Arrange
    StandaloneProcessEngineConfiguration processEngineConfiguration =
        new StandaloneProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown11() {
    // Arrange
    StandaloneProcessEngineConfiguration processEngineConfiguration =
        new StandaloneProcessEngineConfiguration();
    processEngineConfiguration.setHistoryService(
        new HistoryServiceImpl(new JtaProcessEngineConfiguration()));

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown12() {
    // Arrange
    StandaloneProcessEngineConfiguration processEngineConfiguration =
        new StandaloneProcessEngineConfiguration();
    processEngineConfiguration.setManagementService(new ManagementServiceImpl());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown13() {
    // Arrange
    StandaloneProcessEngineConfiguration processEngineConfiguration =
        new StandaloneProcessEngineConfiguration();
    processEngineConfiguration.setDynamicBpmnService(
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration()));

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown14() {
    // Arrange
    StandaloneProcessEngineConfiguration processEngineConfiguration =
        new StandaloneProcessEngineConfiguration();
    processEngineConfiguration.setUserGroupManager(mock(UserGroupManager.class));

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown15() {
    // Arrange
    StandaloneProcessEngineConfiguration processEngineConfiguration =
        new StandaloneProcessEngineConfiguration();
    processEngineConfiguration.setEnableConfiguratorServiceLoader(true);

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown16() {
    // Arrange
    StandaloneProcessEngineConfiguration processEngineConfiguration =
        new StandaloneProcessEngineConfiguration();
    processEngineConfiguration.setSerializableVariableTypeTrackDeserializedObjects(true);

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown17() {
    // Arrange
    StandaloneProcessEngineConfiguration processEngineConfiguration =
        new StandaloneProcessEngineConfiguration();
    processEngineConfiguration.setBpmnParseFactory(mock(BpmnParseFactory.class));

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown18() {
    // Arrange
    StandaloneProcessEngineConfiguration processEngineConfiguration =
        new StandaloneProcessEngineConfiguration();
    processEngineConfiguration.setDeploymentManager(new DeploymentManager());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown19() {
    // Arrange
    StandaloneProcessEngineConfiguration processEngineConfiguration =
        new StandaloneProcessEngineConfiguration();
    processEngineConfiguration.setKnowledgeBaseCacheLimit(10000);

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown20() {
    // Arrange
    StandaloneProcessEngineConfiguration processEngineConfiguration =
        new StandaloneProcessEngineConfiguration();
    processEngineConfiguration.setEventSubscriptionDataManager(
        new MybatisEventSubscriptionDataManager(new JtaProcessEngineConfiguration()));

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown21() {
    // Arrange
    StandaloneProcessEngineConfiguration processEngineConfiguration =
        new StandaloneProcessEngineConfiguration();
    processEngineConfiguration.setProcessDefinitionInfoDataManager(
        new MybatisProcessDefinitionInfoDataManager(new JtaProcessEngineConfiguration()));

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown22() {
    // Arrange
    StandaloneProcessEngineConfiguration processEngineConfiguration =
        new StandaloneProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManager =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setExecutionEntityManager(executionEntityManager);

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown23() {
    // Arrange
    StandaloneProcessEngineConfiguration processEngineConfiguration =
        new StandaloneProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    ResourceEntityManagerImpl resourceEntityManager =
        new ResourceEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisResourceDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setResourceEntityManager(resourceEntityManager);

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown24() {
    // Arrange
    StandaloneProcessEngineConfiguration processEngineConfiguration =
        new StandaloneProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    VariableInstanceEntityManagerImpl variableInstanceEntityManager =
        new VariableInstanceEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisVariableInstanceDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setVariableInstanceEntityManager(variableInstanceEntityManager);

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown25() {
    // Arrange
    StandaloneProcessEngineConfiguration processEngineConfiguration =
        new StandaloneProcessEngineConfiguration();
    processEngineConfiguration.setJobManager(new DefaultJobManager());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown26() {
    // Arrange
    StandaloneProcessEngineConfiguration processEngineConfiguration =
        new StandaloneProcessEngineConfiguration();
    processEngineConfiguration.setAsyncExecutorDefaultTimerJobAcquireWaitTime(10000);

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown27() throws MalformedURLException {
    // Arrange
    StandaloneProcessEngineConfiguration processEngineConfiguration =
        new StandaloneProcessEngineConfiguration();
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("exception during resetting expired jobs"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown28() throws MalformedURLException {
    // Arrange
    StandaloneProcessEngineConfiguration processEngineConfiguration =
        new StandaloneProcessEngineConfiguration();
    processEngineConfiguration.setEnableConfiguratorServiceLoader(true);
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("exception during resetting expired jobs"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown29() throws MalformedURLException {
    // Arrange
    StandaloneProcessEngineConfiguration processEngineConfiguration =
        new StandaloneProcessEngineConfiguration();
    processEngineConfiguration.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("exception during resetting expired jobs"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown30() throws MalformedURLException {
    // Arrange
    StandaloneProcessEngineConfiguration processEngineConfiguration =
        new StandaloneProcessEngineConfiguration();
    processEngineConfiguration.setTableDataManager(
        new TableDataManagerImpl(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("exception during resetting expired jobs"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown31() throws MalformedURLException {
    // Arrange
    StandaloneProcessEngineConfiguration processEngineConfiguration =
        new StandaloneProcessEngineConfiguration();
    processEngineConfiguration.setUseClassForNameClassLoading(true);
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("exception during resetting expired jobs"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <ul>
   *   <li>Given {@link DefaultAsyncJobExecutor} (default constructor) KeepAliveTime is {@code
   *       10000}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown_givenDefaultAsyncJobExecutorKeepAliveTimeIs10000() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setKeepAliveTime(10000L);

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(defaultAsyncJobExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <ul>
   *   <li>Given {@link DefaultAsyncJobExecutor} (default constructor) MaxPoolSize is three.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown_givenDefaultAsyncJobExecutorMaxPoolSizeIsThree() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setMaxPoolSize(3);

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(defaultAsyncJobExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <ul>
   *   <li>Given {@link DefaultAsyncJobExecutor} (default constructor) MessageQueueMode is {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown_givenDefaultAsyncJobExecutorMessageQueueModeIsTrue() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setMessageQueueMode(true);

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(defaultAsyncJobExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <ul>
   *   <li>Given {@link DefaultAsyncJobExecutor} (default constructor) ThreadPoolQueue is {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown_givenDefaultAsyncJobExecutorThreadPoolQueueIsNull() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setThreadPoolQueue(null);

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(defaultAsyncJobExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <ul>
   *   <li>Given {@link DummyTenantInfoHolder} (default constructor) addTenant {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown_givenDummyTenantInfoHolderAddTenant42() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <ul>
   *   <li>Given {@link DummyTenantInfoHolder} (default constructor) addTenant {@code {} starting to
   *       reset expired jobs}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown_givenDummyTenantInfoHolderAddTenantStartingToResetExpiredJobs() {
    // Arrange
    StandaloneProcessEngineConfiguration processEngineConfiguration =
        new StandaloneProcessEngineConfiguration();
    processEngineConfiguration.setManagementService(new ManagementServiceImpl());

    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("{} starting to reset expired jobs");

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder, tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <ul>
   *   <li>Given {@link ParsedDeploymentBuilderFactory} (default constructor) BpmnParser is {@link
   *       BpmnParser} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown_givenParsedDeploymentBuilderFactoryBpmnParserIsBpmnParser() {
    // Arrange
    ParsedDeploymentBuilderFactory parsedDeploymentBuilderFactory =
        new ParsedDeploymentBuilderFactory();
    parsedDeploymentBuilderFactory.setBpmnParser(new BpmnParser());

    StandaloneProcessEngineConfiguration processEngineConfiguration =
        new StandaloneProcessEngineConfiguration();
    processEngineConfiguration.setParsedDeploymentBuilderFactory(parsedDeploymentBuilderFactory);

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <ul>
   *   <li>Given {@link ParsedDeploymentBuilderFactory} (default constructor) BpmnParser is {@link
   *       BpmnParser} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown_givenParsedDeploymentBuilderFactoryBpmnParserIsBpmnParser2() {
    // Arrange
    ParsedDeploymentBuilderFactory parsedDeploymentBuilderFactory =
        new ParsedDeploymentBuilderFactory();
    parsedDeploymentBuilderFactory.setBpmnParser(new BpmnParser());

    StandaloneProcessEngineConfiguration processEngineConfiguration =
        new StandaloneProcessEngineConfiguration();
    processEngineConfiguration.setParsedDeploymentBuilderFactory(parsedDeploymentBuilderFactory);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <ul>
   *   <li>Given {@link StandaloneProcessEngineConfiguration} (default constructor) Deployers is
   *       {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown_givenStandaloneProcessEngineConfigurationDeployersIsArrayList()
      throws MalformedURLException {
    // Arrange
    StandaloneProcessEngineConfiguration processEngineConfiguration =
        new StandaloneProcessEngineConfiguration();
    processEngineConfiguration.setDeployers(new ArrayList<>());
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("exception during resetting expired jobs"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <ul>
   *   <li>Given {@link StandaloneProcessEngineConfiguration} (default constructor) SessionFactories
   *       is {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown_givenStandaloneProcessEngineConfigurationSessionFactoriesIsHashMap() {
    // Arrange
    StandaloneProcessEngineConfiguration processEngineConfiguration =
        new StandaloneProcessEngineConfiguration();
    processEngineConfiguration.setSessionFactories(new HashMap<>());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <ul>
   *   <li>Given {@link StandaloneProcessEngineConfiguration} (default constructor)
   *       TablePrefixIsSchema is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown_givenStandaloneProcessEngineConfigurationTablePrefixIsSchemaIsTrue() {
    // Arrange
    StandaloneProcessEngineConfiguration processEngineConfiguration =
        new StandaloneProcessEngineConfiguration();
    processEngineConfiguration.setTablePrefixIsSchema(true);

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <ul>
   *   <li>Given {@link StandaloneProcessEngineConfiguration} (default constructor) TimerManager is
   *       {@link TimerManager} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown_givenStandaloneProcessEngineConfigurationTimerManagerIsTimerManager() {
    // Arrange
    StandaloneProcessEngineConfiguration processEngineConfiguration =
        new StandaloneProcessEngineConfiguration();
    processEngineConfiguration.setTimerManager(new TimerManager());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor2() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(null);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor3() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("exception during resetting expired jobs");
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor4() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.setCurrentTenantId("exception during resetting expired jobs");
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor5() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor6() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor7() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor8() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor9() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTransactionManager(mock(TransactionManager.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor10() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCommandInvoker(new CommandContextInterceptor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor11() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    processEngineConfiguration.setCommandExecutor(commandExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor12() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTaskService(
        new TaskServiceImpl(new JtaProcessEngineConfiguration()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor13() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setSessionFactories(new HashMap<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor14() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDeployers(new ArrayList<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor15() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setBusinessCalendarManager(mock(BusinessCalendarManager.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor16() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJobHandlers(new HashMap<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor17() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomFunctionProviders(new ArrayList<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor18() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setKnowledgeBaseCache(new DefaultDeploymentCache<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor19() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setProcessValidator(new ActivitiTestCaseProcessValidator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor20() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEnableEagerExecutionTreeFetching(true);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor21() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setByteArrayDataManager(
        new MybatisByteArrayDataManager(new JtaProcessEngineConfiguration()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor22() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setExecutionDataManager(
        new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor23() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setProcessDefinitionDataManager(
        new MybatisProcessDefinitionDataManager(new JtaProcessEngineConfiguration()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor24() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManager =
        new DeadLetterJobEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisDeadLetterJobDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setDeadLetterJobEntityManager(deadLetterJobEntityManager);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor25() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setClock(new DefaultClockImpl());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor26() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAsyncExecutorDefaultTimerJobAcquireWaitTime(1);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor27() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setMailServerHost("localhost");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor28() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTransactionsExternallyManaged(true);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor29() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDataSourceJndiName("exception during async job acquisition: {}");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor30() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDatabaseCatalog("exception during async job acquisition: {}");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor31() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor32() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor33() {
    // Arrange
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        mock(ProcessEngineConfigurationImpl.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof ExecutorPerTenantAsyncExecutor);
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor34() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(processEngineConfiguration).getJobManager();
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof SharedExecutorServiceAsyncExecutor);
    assertNull(((SharedExecutorServiceAsyncExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((SharedExecutorServiceAsyncExecutor) getResult).getResetExpiredJobThread());
    assertNull(((SharedExecutorServiceAsyncExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((SharedExecutorServiceAsyncExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor35() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor36() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.setCurrentTenantId("exception during resetting expired jobs");
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor37() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    processEngineConfiguration.setCommandExecutor(commandExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor38() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomDefaultBpmnParseHandlers(new ArrayList<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor39() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setMaxNrOfStatementsInBulkInsert(3);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <ul>
   *   <li>Given {@link DummyTenantInfoHolder} (default constructor) addTenant {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor_givenDummyTenantInfoHolderAddTenant42() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) getResult).getExecutorService());
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <ul>
   *   <li>Then calls {@link SharedExecutorServiceAsyncExecutor#getProcessEngineConfiguration()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor_thenCallsGetProcessEngineConfiguration() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        mock(SharedExecutorServiceAsyncExecutor.class);
    doNothing().when(sharedExecutorServiceAsyncExecutor).shutdown();
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        mock(ProcessEngineConfigurationImpl.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).shutdown();
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getLockOwner()}.
   *
   * <ul>
   *   <li>Then return {@code Lock Owner}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getLockOwner()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ExecutorPerTenantAsyncExecutor.getLockOwner()"})
  public void testGetLockOwner_thenReturnLockOwner() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        mock(SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.getLockOwner()).thenReturn("Lock Owner");
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder, tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        mock(JtaProcessEngineConfiguration.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    String actualLockOwner = executorPerTenantAsyncExecutor.getLockOwner();

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getLockOwner();
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals("Lock Owner", actualLockOwner);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getTimerLockTimeInMillis()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getTimerLockTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getTimerLockTimeInMillis()"})
  public void testGetTimerLockTimeInMillis() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualTimerLockTimeInMillis = executorPerTenantAsyncExecutor.getTimerLockTimeInMillis();

    // Assert
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(300000, actualTimerLockTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getTimerLockTimeInMillis()}.
   *
   * <ul>
   *   <li>Then throw {@link UnsupportedOperationException}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getTimerLockTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getTimerLockTimeInMillis()"})
  public void testGetTimerLockTimeInMillis_thenThrowUnsupportedOperationException() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenThrow(new UnsupportedOperationException());
    doThrow(new UnsupportedOperationException())
        .when(tenantInfoHolder)
        .setCurrentTenantId(Mockito.<String>any());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor(
        "org.activiti.engine.impl.asyncexecutor.DefaultAsyncJobExecutor", true);

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> executorPerTenantAsyncExecutor.getTimerLockTimeInMillis());
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1))
        .setCurrentTenantId("org.activiti.engine.impl.asyncexecutor.DefaultAsyncJobExecutor");
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setTimerLockTimeInMillis(int)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#setTimerLockTimeInMillis(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setTimerLockTimeInMillis(int)"})
  public void testSetTimerLockTimeInMillis() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setTimerLockTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getTimerLockTimeInMillis());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setTimerLockTimeInMillis(int)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#setTimerLockTimeInMillis(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setTimerLockTimeInMillis(int)"})
  public void testSetTimerLockTimeInMillis2() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("foo"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setTimerLockTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getTimerLockTimeInMillis());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setTimerLockTimeInMillis(int)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#setTimerLockTimeInMillis(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setTimerLockTimeInMillis(int)"})
  public void testSetTimerLockTimeInMillis3() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setTimerLockTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getTimerLockTimeInMillis());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setTimerLockTimeInMillis(int)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#setTimerLockTimeInMillis(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setTimerLockTimeInMillis(int)"})
  public void testSetTimerLockTimeInMillis4() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomScriptingEngineClasses(new ArrayList<>());
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("foo"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setTimerLockTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getTimerLockTimeInMillis());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setTimerLockTimeInMillis(int)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#setTimerLockTimeInMillis(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setTimerLockTimeInMillis(int)"})
  public void testSetTimerLockTimeInMillis5() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEnableVerboseExecutionTreeLogging(true);
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("foo"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setTimerLockTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getTimerLockTimeInMillis());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setTimerLockTimeInMillis(int)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#setTimerLockTimeInMillis(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setTimerLockTimeInMillis(int)"})
  public void testSetTimerLockTimeInMillis6() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setPropertyDataManager(
        new MybatisPropertyDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("foo"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setTimerLockTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getTimerLockTimeInMillis());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setTimerLockTimeInMillis(int)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#setTimerLockTimeInMillis(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setTimerLockTimeInMillis(int)"})
  public void testSetTimerLockTimeInMillis7() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAsyncExecutorActivate(true);
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("foo"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setTimerLockTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getTimerLockTimeInMillis());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setTimerLockTimeInMillis(int)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#setTimerLockTimeInMillis(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setTimerLockTimeInMillis(int)"})
  public void testSetTimerLockTimeInMillis8() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAsyncExecutorDefaultTimerJobAcquireWaitTime(1);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setTimerLockTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getTimerLockTimeInMillis());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis()"})
  public void testGetAsyncJobLockTimeInMillis() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualAsyncJobLockTimeInMillis =
        executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis();

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(300000, actualAsyncJobLockTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis()"})
  public void testGetAsyncJobLockTimeInMillis2() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneInMemProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualAsyncJobLockTimeInMillis =
        executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis();

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(300000, actualAsyncJobLockTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis()"})
  public void testGetAsyncJobLockTimeInMillis3() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualAsyncJobLockTimeInMillis =
        executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis();

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(300000, actualAsyncJobLockTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis()"})
  public void testGetAsyncJobLockTimeInMillis4() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualAsyncJobLockTimeInMillis =
        executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis();

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(300000, actualAsyncJobLockTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis()"})
  public void testGetAsyncJobLockTimeInMillis5() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomMybatisMappers(new HashSet<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertEquals(300000, executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis());
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis()"})
  public void testGetAsyncJobLockTimeInMillis6() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDefaultCommandConfig(new CommandConfig());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualAsyncJobLockTimeInMillis =
        executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis();

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(300000, actualAsyncJobLockTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis()"})
  public void testGetAsyncJobLockTimeInMillis7() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    processEngineConfiguration.setCommandExecutor(commandExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualAsyncJobLockTimeInMillis =
        executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis();

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(300000, actualAsyncJobLockTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis()"})
  public void testGetAsyncJobLockTimeInMillis8() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setRepositoryService(new RepositoryServiceImpl());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualAsyncJobLockTimeInMillis =
        executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis();

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(300000, actualAsyncJobLockTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis()"})
  public void testGetAsyncJobLockTimeInMillis9() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEnableConfiguratorServiceLoader(true);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualAsyncJobLockTimeInMillis =
        executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis();

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(300000, actualAsyncJobLockTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis()"})
  public void testGetAsyncJobLockTimeInMillis10() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setBpmnDeploymentHelper(new BpmnDeploymentHelper());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualAsyncJobLockTimeInMillis =
        executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis();

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(300000, actualAsyncJobLockTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis()"})
  public void testGetAsyncJobLockTimeInMillis11() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDeployers(new ArrayList<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertEquals(300000, executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis());
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis()"})
  public void testGetAsyncJobLockTimeInMillis12() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setVariableTypes(new DefaultVariableTypes());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualAsyncJobLockTimeInMillis =
        executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis();

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(300000, actualAsyncJobLockTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis()"})
  public void testGetAsyncJobLockTimeInMillis13() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomScriptingEngineClasses(new ArrayList<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualAsyncJobLockTimeInMillis =
        executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis();

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(300000, actualAsyncJobLockTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis()"})
  public void testGetAsyncJobLockTimeInMillis14() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEnableSafeBpmnXml(true);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualAsyncJobLockTimeInMillis =
        executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis();

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(300000, actualAsyncJobLockTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis()"})
  public void testGetAsyncJobLockTimeInMillis15() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEnableVerboseExecutionTreeLogging(true);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualAsyncJobLockTimeInMillis =
        executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis();

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(300000, actualAsyncJobLockTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis()"})
  public void testGetAsyncJobLockTimeInMillis16() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setByteArrayDataManager(
        new MybatisByteArrayDataManager(new JtaProcessEngineConfiguration()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualAsyncJobLockTimeInMillis =
        executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis();

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(300000, actualAsyncJobLockTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis()"})
  public void testGetAsyncJobLockTimeInMillis17() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDeploymentDataManager(
        new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualAsyncJobLockTimeInMillis =
        executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis();

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(300000, actualAsyncJobLockTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis()"})
  public void testGetAsyncJobLockTimeInMillis18() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    DeploymentEntityManagerImpl deploymentEntityManager =
        new DeploymentEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setDeploymentEntityManager(deploymentEntityManager);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualAsyncJobLockTimeInMillis =
        executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis();

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(300000, actualAsyncJobLockTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis()"})
  public void testGetAsyncJobLockTimeInMillis19() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    StandaloneInMemProcessEngineConfiguration processEngineConfiguration =
        new StandaloneInMemProcessEngineConfiguration();
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualAsyncJobLockTimeInMillis =
        executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis();

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(300000, actualAsyncJobLockTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis()"})
  public void testGetAsyncJobLockTimeInMillis20() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJobHandlers(new HashMap<>());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertEquals(300000, executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis());
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis()"})
  public void testGetAsyncJobLockTimeInMillis21() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEnableVerboseExecutionTreeLogging(true);
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualAsyncJobLockTimeInMillis =
        executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis();

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(300000, actualAsyncJobLockTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis()"})
  public void testGetAsyncJobLockTimeInMillis22() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    HistoricTaskInstanceEntityManagerImpl historicTaskInstanceEntityManager =
        new HistoricTaskInstanceEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisHistoricTaskInstanceDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setHistoricTaskInstanceEntityManager(
        historicTaskInstanceEntityManager);
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualAsyncJobLockTimeInMillis =
        executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis();

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(300000, actualAsyncJobLockTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis()"})
  public void testGetAsyncJobLockTimeInMillis23() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJpaEntityManagerFactory(JSONObject.NULL);
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualAsyncJobLockTimeInMillis =
        executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis();

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(300000, actualAsyncJobLockTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis()"})
  public void testGetAsyncJobLockTimeInMillis24() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setXmlEncoding("UTF-8");
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualAsyncJobLockTimeInMillis =
        executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis();

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(300000, actualAsyncJobLockTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis()"})
  public void testGetAsyncJobLockTimeInMillis25() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTransactionManager(mock(TransactionManager.class));
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");

    AsyncExecutor asyncExecutor = mock(AsyncExecutor.class);
    when(asyncExecutor.getAsyncJobLockTimeInMillis()).thenReturn(1);
    doNothing()
        .when(asyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    doNothing().when(asyncExecutor).start();

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(asyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder, tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualAsyncJobLockTimeInMillis =
        executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis();

    // Assert
    verify(asyncExecutor).getAsyncJobLockTimeInMillis();
    verify(asyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(asyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals(1, actualAsyncJobLockTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}.
   *
   * <ul>
   *   <li>Then return one.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis()"})
  public void testGetAsyncJobLockTimeInMillis_thenReturnOne() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");

    AsyncExecutor asyncExecutor = mock(AsyncExecutor.class);
    when(asyncExecutor.getAsyncJobLockTimeInMillis()).thenReturn(1);
    doNothing()
        .when(asyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    doNothing().when(asyncExecutor).start();

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(asyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder, tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualAsyncJobLockTimeInMillis =
        executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis();

    // Assert
    verify(asyncExecutor).getAsyncJobLockTimeInMillis();
    verify(asyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(asyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals(1, actualAsyncJobLockTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}.
   *
   * <ul>
   *   <li>Then return one.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis()"})
  public void testGetAsyncJobLockTimeInMillis_thenReturnOne2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");

    AsyncExecutor asyncExecutor = mock(AsyncExecutor.class);
    when(asyncExecutor.getAsyncJobLockTimeInMillis()).thenReturn(1);
    doNothing()
        .when(asyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    doNothing().when(asyncExecutor).start();

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(asyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder, tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualAsyncJobLockTimeInMillis =
        executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis();

    // Assert
    verify(asyncExecutor).getAsyncJobLockTimeInMillis();
    verify(asyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(asyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals(1, actualAsyncJobLockTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setAsyncJobLockTimeInMillis(int)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#setAsyncJobLockTimeInMillis(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setAsyncJobLockTimeInMillis(int)"})
  public void testSetAsyncJobLockTimeInMillis() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setAsyncJobLockTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getAsyncJobLockTimeInMillis());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setAsyncJobLockTimeInMillis(int)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#setAsyncJobLockTimeInMillis(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setAsyncJobLockTimeInMillis(int)"})
  public void testSetAsyncJobLockTimeInMillis2() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(null);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setAsyncJobLockTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getAsyncJobLockTimeInMillis());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}.
   *
   * <p>Method under test: {@link
   * ExecutorPerTenantAsyncExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "int ExecutorPerTenantAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis()"
  })
  public void testGetDefaultTimerJobAcquireWaitTimeInMillis() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneInMemProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualDefaultTimerJobAcquireWaitTimeInMillis =
        executorPerTenantAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis();

    // Assert
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(10000, actualDefaultTimerJobAcquireWaitTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}.
   *
   * <p>Method under test: {@link
   * ExecutorPerTenantAsyncExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "int ExecutorPerTenantAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis()"
  })
  public void testGetDefaultTimerJobAcquireWaitTimeInMillis2() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomMybatisMappers(new HashSet<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualDefaultTimerJobAcquireWaitTimeInMillis =
        executorPerTenantAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis();

    // Assert
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(10000, actualDefaultTimerJobAcquireWaitTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}.
   *
   * <p>Method under test: {@link
   * ExecutorPerTenantAsyncExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "int ExecutorPerTenantAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis()"
  })
  public void testGetDefaultTimerJobAcquireWaitTimeInMillis3() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setScriptingEngines(new ScriptingEngines(new ScriptEngineManager()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualDefaultTimerJobAcquireWaitTimeInMillis =
        executorPerTenantAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis();

    // Assert
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(10000, actualDefaultTimerJobAcquireWaitTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}.
   *
   * <p>Method under test: {@link
   * ExecutorPerTenantAsyncExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "int ExecutorPerTenantAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis()"
  })
  public void testGetDefaultTimerJobAcquireWaitTimeInMillis4() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomEventHandlers(new ArrayList<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualDefaultTimerJobAcquireWaitTimeInMillis =
        executorPerTenantAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis();

    // Assert
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(10000, actualDefaultTimerJobAcquireWaitTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}.
   *
   * <p>Method under test: {@link
   * ExecutorPerTenantAsyncExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "int ExecutorPerTenantAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis()"
  })
  public void testGetDefaultTimerJobAcquireWaitTimeInMillis5() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEnableDatabaseEventLogging(true);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualDefaultTimerJobAcquireWaitTimeInMillis =
        executorPerTenantAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis();

    // Assert
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(10000, actualDefaultTimerJobAcquireWaitTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}.
   *
   * <p>Method under test: {@link
   * ExecutorPerTenantAsyncExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "int ExecutorPerTenantAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis()"
  })
  public void testGetDefaultTimerJobAcquireWaitTimeInMillis6() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEnableExecutionRelationshipCounts(true);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualDefaultTimerJobAcquireWaitTimeInMillis =
        executorPerTenantAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis();

    // Assert
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(10000, actualDefaultTimerJobAcquireWaitTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}.
   *
   * <p>Method under test: {@link
   * ExecutorPerTenantAsyncExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "int ExecutorPerTenantAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis()"
  })
  public void testGetDefaultTimerJobAcquireWaitTimeInMillis7() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAsyncExecutorSecondsToWaitOnShutdown(1L);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualDefaultTimerJobAcquireWaitTimeInMillis =
        executorPerTenantAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis();

    // Assert
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(10000, actualDefaultTimerJobAcquireWaitTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}.
   *
   * <p>Method under test: {@link
   * ExecutorPerTenantAsyncExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "int ExecutorPerTenantAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis()"
  })
  public void testGetDefaultTimerJobAcquireWaitTimeInMillis8() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setHistoryLevel(HistoryLevel.NONE);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualDefaultTimerJobAcquireWaitTimeInMillis =
        executorPerTenantAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis();

    // Assert
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(10000, actualDefaultTimerJobAcquireWaitTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}.
   *
   * <p>Method under test: {@link
   * ExecutorPerTenantAsyncExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "int ExecutorPerTenantAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis()"
  })
  public void testGetDefaultTimerJobAcquireWaitTimeInMillis9() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        mock(SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis())
        .thenThrow(new UnsupportedOperationException());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder, tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        mock(ProcessEngineConfigurationImpl.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> executorPerTenantAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis());
    verify(sharedExecutorServiceAsyncExecutor).getDefaultTimerJobAcquireWaitTimeInMillis();
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(tenantInfoHolder).getCurrentTenantId();
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}.
   *
   * <p>Method under test: {@link
   * ExecutorPerTenantAsyncExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "int ExecutorPerTenantAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis()"
  })
  public void testGetDefaultTimerJobAcquireWaitTimeInMillis10() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        mock(SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis())
        .thenReturn(1);
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new StandaloneInMemProcessEngineConfiguration());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder, tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        mock(ProcessEngineConfigurationImpl.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualDefaultTimerJobAcquireWaitTimeInMillis =
        executorPerTenantAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis();

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getDefaultTimerJobAcquireWaitTimeInMillis();
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals(1, actualDefaultTimerJobAcquireWaitTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}.
   *
   * <p>Method under test: {@link
   * ExecutorPerTenantAsyncExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "int ExecutorPerTenantAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis()"
  })
  public void testGetDefaultTimerJobAcquireWaitTimeInMillis11() throws MalformedURLException {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("exception during resetting expired jobs"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        mock(SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis())
        .thenReturn(1);
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(jtaProcessEngineConfiguration);
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder, tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        mock(ProcessEngineConfigurationImpl.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualDefaultTimerJobAcquireWaitTimeInMillis =
        executorPerTenantAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis();

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getDefaultTimerJobAcquireWaitTimeInMillis();
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals(1, actualDefaultTimerJobAcquireWaitTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}.
   *
   * <p>Method under test: {@link
   * ExecutorPerTenantAsyncExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "int ExecutorPerTenantAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis()"
  })
  public void testGetDefaultTimerJobAcquireWaitTimeInMillis12() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        mock(SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis())
        .thenReturn(1);
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder, tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        mock(ProcessEngineConfigurationImpl.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", false);

    // Act
    int actualDefaultTimerJobAcquireWaitTimeInMillis =
        executorPerTenantAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis();

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getDefaultTimerJobAcquireWaitTimeInMillis();
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals(1, actualDefaultTimerJobAcquireWaitTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}.
   *
   * <p>Method under test: {@link
   * ExecutorPerTenantAsyncExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "int ExecutorPerTenantAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis()"
  })
  public void testGetDefaultTimerJobAcquireWaitTimeInMillis13() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTaskService(
        new TaskServiceImpl(new JtaProcessEngineConfiguration()));

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        mock(SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis())
        .thenReturn(1);
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(jtaProcessEngineConfiguration);
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder, tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        mock(ProcessEngineConfigurationImpl.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualDefaultTimerJobAcquireWaitTimeInMillis =
        executorPerTenantAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis();

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getDefaultTimerJobAcquireWaitTimeInMillis();
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals(1, actualDefaultTimerJobAcquireWaitTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}.
   *
   * <ul>
   *   <li>Then return one.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutorPerTenantAsyncExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "int ExecutorPerTenantAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis()"
  })
  public void testGetDefaultTimerJobAcquireWaitTimeInMillis_thenReturnOne() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        mock(SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis())
        .thenReturn(1);
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder, tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        mock(ProcessEngineConfigurationImpl.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualDefaultTimerJobAcquireWaitTimeInMillis =
        executorPerTenantAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis();

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getDefaultTimerJobAcquireWaitTimeInMillis();
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals(1, actualDefaultTimerJobAcquireWaitTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setDefaultTimerJobAcquireWaitTimeInMillis(int)}.
   *
   * <p>Method under test: {@link
   * ExecutorPerTenantAsyncExecutor#setDefaultTimerJobAcquireWaitTimeInMillis(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutorPerTenantAsyncExecutor.setDefaultTimerJobAcquireWaitTimeInMillis(int)"
  })
  public void testSetDefaultTimerJobAcquireWaitTimeInMillis() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setDefaultTimerJobAcquireWaitTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getDefaultTimerJobAcquireWaitTimeInMillis());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setDefaultTimerJobAcquireWaitTimeInMillis(int)}.
   *
   * <p>Method under test: {@link
   * ExecutorPerTenantAsyncExecutor#setDefaultTimerJobAcquireWaitTimeInMillis(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutorPerTenantAsyncExecutor.setDefaultTimerJobAcquireWaitTimeInMillis(int)"
  })
  public void testSetDefaultTimerJobAcquireWaitTimeInMillis2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setExecutionDataManager(
        new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setDefaultTimerJobAcquireWaitTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getDefaultTimerJobAcquireWaitTimeInMillis());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getDefaultAsyncJobAcquireWaitTimeInMillis()}.
   *
   * <p>Method under test: {@link
   * ExecutorPerTenantAsyncExecutor#getDefaultAsyncJobAcquireWaitTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "int ExecutorPerTenantAsyncExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis()"
  })
  public void testGetDefaultAsyncJobAcquireWaitTimeInMillis() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new ManagedAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder, tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualDefaultAsyncJobAcquireWaitTimeInMillis =
        executorPerTenantAsyncExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(10000, actualDefaultAsyncJobAcquireWaitTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getDefaultAsyncJobAcquireWaitTimeInMillis()}.
   *
   * <ul>
   *   <li>Then calls {@link TenantInfoHolder#setCurrentTenantId(String)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutorPerTenantAsyncExecutor#getDefaultAsyncJobAcquireWaitTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "int ExecutorPerTenantAsyncExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis()"
  })
  public void testGetDefaultAsyncJobAcquireWaitTimeInMillis_thenCallsSetCurrentTenantId() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder, tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualDefaultAsyncJobAcquireWaitTimeInMillis =
        executorPerTenantAsyncExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(10000, actualDefaultAsyncJobAcquireWaitTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getDefaultAsyncJobAcquireWaitTimeInMillis()}.
   *
   * <ul>
   *   <li>Then return one.
   * </ul>
   *
   * <p>Method under test: {@link
   * ExecutorPerTenantAsyncExecutor#getDefaultAsyncJobAcquireWaitTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "int ExecutorPerTenantAsyncExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis()"
  })
  public void testGetDefaultAsyncJobAcquireWaitTimeInMillis_thenReturnOne() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        mock(SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis())
        .thenReturn(1);
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder, tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualDefaultAsyncJobAcquireWaitTimeInMillis =
        executorPerTenantAsyncExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis();

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getDefaultAsyncJobAcquireWaitTimeInMillis();
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals(1, actualDefaultAsyncJobAcquireWaitTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setDefaultAsyncJobAcquireWaitTimeInMillis(int)}.
   *
   * <p>Method under test: {@link
   * ExecutorPerTenantAsyncExecutor#setDefaultAsyncJobAcquireWaitTimeInMillis(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutorPerTenantAsyncExecutor.setDefaultAsyncJobAcquireWaitTimeInMillis(int)"
  })
  public void testSetDefaultAsyncJobAcquireWaitTimeInMillis() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setDefaultAsyncJobAcquireWaitTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getDefaultAsyncJobAcquireWaitTimeInMillis());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setDefaultQueueSizeFullWaitTimeInMillis(int)}.
   *
   * <p>Method under test: {@link
   * ExecutorPerTenantAsyncExecutor#setDefaultQueueSizeFullWaitTimeInMillis(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutorPerTenantAsyncExecutor.setDefaultQueueSizeFullWaitTimeInMillis(int)"
  })
  public void testSetDefaultQueueSizeFullWaitTimeInMillis() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setDefaultQueueSizeFullWaitTimeInMillis(3);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(3, getResult.getDefaultQueueSizeFullWaitTimeInMillis());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setDefaultQueueSizeFullWaitTimeInMillis(int)}.
   *
   * <p>Method under test: {@link
   * ExecutorPerTenantAsyncExecutor#setDefaultQueueSizeFullWaitTimeInMillis(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutorPerTenantAsyncExecutor.setDefaultQueueSizeFullWaitTimeInMillis(int)"
  })
  public void testSetDefaultQueueSizeFullWaitTimeInMillis2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAsyncExecutorResetExpiredJobsPageSize(3);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setDefaultQueueSizeFullWaitTimeInMillis(3);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(3, getResult.getDefaultQueueSizeFullWaitTimeInMillis());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getMaxAsyncJobsDuePerAcquisition()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getMaxAsyncJobsDuePerAcquisition()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getMaxAsyncJobsDuePerAcquisition()"})
  public void testGetMaxAsyncJobsDuePerAcquisition() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualMaxAsyncJobsDuePerAcquisition =
        executorPerTenantAsyncExecutor.getMaxAsyncJobsDuePerAcquisition();

    // Assert
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(1, actualMaxAsyncJobsDuePerAcquisition);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getMaxAsyncJobsDuePerAcquisition()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getMaxAsyncJobsDuePerAcquisition()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getMaxAsyncJobsDuePerAcquisition()"})
  public void testGetMaxAsyncJobsDuePerAcquisition2() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomMybatisXMLMappers(new HashSet<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualMaxAsyncJobsDuePerAcquisition =
        executorPerTenantAsyncExecutor.getMaxAsyncJobsDuePerAcquisition();

    // Assert
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(1, actualMaxAsyncJobsDuePerAcquisition);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getMaxAsyncJobsDuePerAcquisition()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getMaxAsyncJobsDuePerAcquisition()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getMaxAsyncJobsDuePerAcquisition()"})
  public void testGetMaxAsyncJobsDuePerAcquisition3() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setHistoryService(
        new HistoryServiceImpl(new JtaProcessEngineConfiguration()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualMaxAsyncJobsDuePerAcquisition =
        executorPerTenantAsyncExecutor.getMaxAsyncJobsDuePerAcquisition();

    // Assert
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(1, actualMaxAsyncJobsDuePerAcquisition);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getMaxAsyncJobsDuePerAcquisition()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getMaxAsyncJobsDuePerAcquisition()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getMaxAsyncJobsDuePerAcquisition()"})
  public void testGetMaxAsyncJobsDuePerAcquisition4() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualMaxAsyncJobsDuePerAcquisition =
        executorPerTenantAsyncExecutor.getMaxAsyncJobsDuePerAcquisition();

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(1, actualMaxAsyncJobsDuePerAcquisition);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getMaxAsyncJobsDuePerAcquisition()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getMaxAsyncJobsDuePerAcquisition()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getMaxAsyncJobsDuePerAcquisition()"})
  public void testGetMaxAsyncJobsDuePerAcquisition5() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(new CommandConfig(true), first);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertEquals(1, executorPerTenantAsyncExecutor.getMaxAsyncJobsDuePerAcquisition());
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    verify(processEngineConfiguration).getJobManager();
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(first, atLeast(1)).execute(isA(CommandConfig.class), Mockito.<Command<Object>>any());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getMaxAsyncJobsDuePerAcquisition()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getMaxAsyncJobsDuePerAcquisition()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getMaxAsyncJobsDuePerAcquisition()"})
  public void testGetMaxAsyncJobsDuePerAcquisition6() throws MalformedURLException {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("exception during timer job acquisition: {}"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        mock(SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.getMaxAsyncJobsDuePerAcquisition()).thenReturn(3);
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(jtaProcessEngineConfiguration);
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder, tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        mock(ProcessEngineConfigurationImpl.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualMaxAsyncJobsDuePerAcquisition =
        executorPerTenantAsyncExecutor.getMaxAsyncJobsDuePerAcquisition();

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getMaxAsyncJobsDuePerAcquisition();
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals(3, actualMaxAsyncJobsDuePerAcquisition);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getMaxAsyncJobsDuePerAcquisition()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getMaxAsyncJobsDuePerAcquisition()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getMaxAsyncJobsDuePerAcquisition()"})
  public void testGetMaxAsyncJobsDuePerAcquisition7() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        mock(SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.getMaxAsyncJobsDuePerAcquisition()).thenReturn(3);
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(jtaProcessEngineConfiguration);
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder, tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        mock(ProcessEngineConfigurationImpl.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualMaxAsyncJobsDuePerAcquisition =
        executorPerTenantAsyncExecutor.getMaxAsyncJobsDuePerAcquisition();

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getMaxAsyncJobsDuePerAcquisition();
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals(3, actualMaxAsyncJobsDuePerAcquisition);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getMaxAsyncJobsDuePerAcquisition()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getMaxAsyncJobsDuePerAcquisition()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getMaxAsyncJobsDuePerAcquisition()"})
  public void testGetMaxAsyncJobsDuePerAcquisition8() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setDefaultCommandConfig(new CommandConfig());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        mock(SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.getMaxAsyncJobsDuePerAcquisition()).thenReturn(3);
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(jtaProcessEngineConfiguration);
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder, tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        mock(ProcessEngineConfigurationImpl.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualMaxAsyncJobsDuePerAcquisition =
        executorPerTenantAsyncExecutor.getMaxAsyncJobsDuePerAcquisition();

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getMaxAsyncJobsDuePerAcquisition();
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals(3, actualMaxAsyncJobsDuePerAcquisition);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getMaxAsyncJobsDuePerAcquisition()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getMaxAsyncJobsDuePerAcquisition()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getMaxAsyncJobsDuePerAcquisition()"})
  public void testGetMaxAsyncJobsDuePerAcquisition9() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setUserGroupManager(mock(UserGroupManager.class));

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        mock(SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.getMaxAsyncJobsDuePerAcquisition()).thenReturn(3);
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(jtaProcessEngineConfiguration);
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder, tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        mock(ProcessEngineConfigurationImpl.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualMaxAsyncJobsDuePerAcquisition =
        executorPerTenantAsyncExecutor.getMaxAsyncJobsDuePerAcquisition();

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getMaxAsyncJobsDuePerAcquisition();
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals(3, actualMaxAsyncJobsDuePerAcquisition);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getMaxAsyncJobsDuePerAcquisition()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getMaxAsyncJobsDuePerAcquisition()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getMaxAsyncJobsDuePerAcquisition()"})
  public void testGetMaxAsyncJobsDuePerAcquisition10() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setSessionFactories(new HashMap<>());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        mock(SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.getMaxAsyncJobsDuePerAcquisition()).thenReturn(3);
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(jtaProcessEngineConfiguration);
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder, tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        mock(ProcessEngineConfigurationImpl.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualMaxAsyncJobsDuePerAcquisition =
        executorPerTenantAsyncExecutor.getMaxAsyncJobsDuePerAcquisition();

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getMaxAsyncJobsDuePerAcquisition();
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals(3, actualMaxAsyncJobsDuePerAcquisition);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getMaxAsyncJobsDuePerAcquisition()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getMaxAsyncJobsDuePerAcquisition()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getMaxAsyncJobsDuePerAcquisition()"})
  public void testGetMaxAsyncJobsDuePerAcquisition11() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setSerializableVariableTypeTrackDeserializedObjects(true);

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        mock(SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.getMaxAsyncJobsDuePerAcquisition()).thenReturn(3);
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(jtaProcessEngineConfiguration);
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder, tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        mock(ProcessEngineConfigurationImpl.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualMaxAsyncJobsDuePerAcquisition =
        executorPerTenantAsyncExecutor.getMaxAsyncJobsDuePerAcquisition();

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getMaxAsyncJobsDuePerAcquisition();
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals(3, actualMaxAsyncJobsDuePerAcquisition);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getMaxAsyncJobsDuePerAcquisition()}.
   *
   * <ul>
   *   <li>Then return minus one.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getMaxAsyncJobsDuePerAcquisition()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getMaxAsyncJobsDuePerAcquisition()"})
  public void testGetMaxAsyncJobsDuePerAcquisition_thenReturnMinusOne() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        mock(SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.getMaxAsyncJobsDuePerAcquisition()).thenReturn(-1);
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder, tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        mock(ProcessEngineConfigurationImpl.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualMaxAsyncJobsDuePerAcquisition =
        executorPerTenantAsyncExecutor.getMaxAsyncJobsDuePerAcquisition();

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getMaxAsyncJobsDuePerAcquisition();
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals(-1, actualMaxAsyncJobsDuePerAcquisition);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getMaxAsyncJobsDuePerAcquisition()}.
   *
   * <ul>
   *   <li>Then return three.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getMaxAsyncJobsDuePerAcquisition()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getMaxAsyncJobsDuePerAcquisition()"})
  public void testGetMaxAsyncJobsDuePerAcquisition_thenReturnThree() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        mock(SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.getMaxAsyncJobsDuePerAcquisition()).thenReturn(3);
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder, tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        mock(ProcessEngineConfigurationImpl.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualMaxAsyncJobsDuePerAcquisition =
        executorPerTenantAsyncExecutor.getMaxAsyncJobsDuePerAcquisition();

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getMaxAsyncJobsDuePerAcquisition();
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals(3, actualMaxAsyncJobsDuePerAcquisition);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setMaxAsyncJobsDuePerAcquisition(int)}.
   *
   * <p>Method under test: {@link
   * ExecutorPerTenantAsyncExecutor#setMaxAsyncJobsDuePerAcquisition(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setMaxAsyncJobsDuePerAcquisition(int)"})
  public void testSetMaxAsyncJobsDuePerAcquisition() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setMaxAsyncJobsDuePerAcquisition(3);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(3, getResult.getMaxAsyncJobsDuePerAcquisition());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setMaxAsyncJobsDuePerAcquisition(int)}.
   *
   * <p>Method under test: {@link
   * ExecutorPerTenantAsyncExecutor#setMaxAsyncJobsDuePerAcquisition(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setMaxAsyncJobsDuePerAcquisition(int)"})
  public void testSetMaxAsyncJobsDuePerAcquisition2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setMaxAsyncJobsDuePerAcquisition(3);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(3, getResult.getMaxAsyncJobsDuePerAcquisition());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setMaxAsyncJobsDuePerAcquisition(int)}.
   *
   * <p>Method under test: {@link
   * ExecutorPerTenantAsyncExecutor#setMaxAsyncJobsDuePerAcquisition(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setMaxAsyncJobsDuePerAcquisition(int)"})
  public void testSetMaxAsyncJobsDuePerAcquisition3() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setPreBpmnParseHandlers(new ArrayList<>());
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setMaxAsyncJobsDuePerAcquisition(3);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(3, getResult.getMaxAsyncJobsDuePerAcquisition());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setMaxAsyncJobsDuePerAcquisition(int)}.
   *
   * <p>Method under test: {@link
   * ExecutorPerTenantAsyncExecutor#setMaxAsyncJobsDuePerAcquisition(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setMaxAsyncJobsDuePerAcquisition(int)"})
  public void testSetMaxAsyncJobsDuePerAcquisition4() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAsyncExecutorThreadPoolQueue(null);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setMaxAsyncJobsDuePerAcquisition(3);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(3, getResult.getMaxAsyncJobsDuePerAcquisition());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setMaxAsyncJobsDuePerAcquisition(int)}.
   *
   * <p>Method under test: {@link
   * ExecutorPerTenantAsyncExecutor#setMaxAsyncJobsDuePerAcquisition(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setMaxAsyncJobsDuePerAcquisition(int)"})
  public void testSetMaxAsyncJobsDuePerAcquisition5() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setProcessDefinitionHelper(mock(ProcessDefinitionHelper.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setMaxAsyncJobsDuePerAcquisition(3);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(3, getResult.getMaxAsyncJobsDuePerAcquisition());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setMaxAsyncJobsDuePerAcquisition(int)}.
   *
   * <p>Method under test: {@link
   * ExecutorPerTenantAsyncExecutor#setMaxAsyncJobsDuePerAcquisition(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setMaxAsyncJobsDuePerAcquisition(int)"})
  public void testSetMaxAsyncJobsDuePerAcquisition6() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setSchemaCommandConfig(new CommandConfig());
    processEngineConfiguration.setAsyncExecutorThreadPoolQueue(null);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setMaxAsyncJobsDuePerAcquisition(3);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(3, getResult.getMaxAsyncJobsDuePerAcquisition());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getMaxTimerJobsPerAcquisition()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getMaxTimerJobsPerAcquisition()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getMaxTimerJobsPerAcquisition()"})
  public void testGetMaxTimerJobsPerAcquisition() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualMaxTimerJobsPerAcquisition =
        executorPerTenantAsyncExecutor.getMaxTimerJobsPerAcquisition();

    // Assert
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(1, actualMaxTimerJobsPerAcquisition);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getMaxTimerJobsPerAcquisition()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getMaxTimerJobsPerAcquisition()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getMaxTimerJobsPerAcquisition()"})
  public void testGetMaxTimerJobsPerAcquisition2() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneInMemProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualMaxTimerJobsPerAcquisition =
        executorPerTenantAsyncExecutor.getMaxTimerJobsPerAcquisition();

    // Assert
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(1, actualMaxTimerJobsPerAcquisition);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getMaxTimerJobsPerAcquisition()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getMaxTimerJobsPerAcquisition()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getMaxTimerJobsPerAcquisition()"})
  public void testGetMaxTimerJobsPerAcquisition3() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualMaxTimerJobsPerAcquisition =
        executorPerTenantAsyncExecutor.getMaxTimerJobsPerAcquisition();

    // Assert
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(1, actualMaxTimerJobsPerAcquisition);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getMaxTimerJobsPerAcquisition()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getMaxTimerJobsPerAcquisition()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getMaxTimerJobsPerAcquisition()"})
  public void testGetMaxTimerJobsPerAcquisition4() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCommandInterceptors(new ArrayList<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualMaxTimerJobsPerAcquisition =
        executorPerTenantAsyncExecutor.getMaxTimerJobsPerAcquisition();

    // Assert
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(1, actualMaxTimerJobsPerAcquisition);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getMaxTimerJobsPerAcquisition()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getMaxTimerJobsPerAcquisition()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getMaxTimerJobsPerAcquisition()"})
  public void testGetMaxTimerJobsPerAcquisition5() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTypedEventListeners(new HashMap<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualMaxTimerJobsPerAcquisition =
        executorPerTenantAsyncExecutor.getMaxTimerJobsPerAcquisition();

    // Assert
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(1, actualMaxTimerJobsPerAcquisition);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getMaxTimerJobsPerAcquisition()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getMaxTimerJobsPerAcquisition()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getMaxTimerJobsPerAcquisition()"})
  public void testGetMaxTimerJobsPerAcquisition6() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJdbcPassword("iloveyou");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualMaxTimerJobsPerAcquisition =
        executorPerTenantAsyncExecutor.getMaxTimerJobsPerAcquisition();

    // Assert
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(1, actualMaxTimerJobsPerAcquisition);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getMaxTimerJobsPerAcquisition()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getMaxTimerJobsPerAcquisition()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getMaxTimerJobsPerAcquisition()"})
  public void testGetMaxTimerJobsPerAcquisition7() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJpaCloseEntityManager(true);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualMaxTimerJobsPerAcquisition =
        executorPerTenantAsyncExecutor.getMaxTimerJobsPerAcquisition();

    // Assert
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(1, actualMaxTimerJobsPerAcquisition);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getMaxTimerJobsPerAcquisition()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getMaxTimerJobsPerAcquisition()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getMaxTimerJobsPerAcquisition()"})
  public void testGetMaxTimerJobsPerAcquisition8() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDatabaseSchema("{} starting to reset expired jobs");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualMaxTimerJobsPerAcquisition =
        executorPerTenantAsyncExecutor.getMaxTimerJobsPerAcquisition();

    // Assert
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(1, actualMaxTimerJobsPerAcquisition);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getMaxTimerJobsPerAcquisition()}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getMaxTimerJobsPerAcquisition()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getMaxTimerJobsPerAcquisition()"})
  public void testGetMaxTimerJobsPerAcquisition9() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAsyncFailedJobWaitTime(1);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualMaxTimerJobsPerAcquisition =
        executorPerTenantAsyncExecutor.getMaxTimerJobsPerAcquisition();

    // Assert
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(1, actualMaxTimerJobsPerAcquisition);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getMaxTimerJobsPerAcquisition()}.
   *
   * <ul>
   *   <li>Then calls {@link TenantAwareAsyncExecutorFactory#createAsyncExecutor(String)}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getMaxTimerJobsPerAcquisition()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getMaxTimerJobsPerAcquisition()"})
  public void testGetMaxTimerJobsPerAcquisition_thenCallsCreateAsyncExecutor() {
    // Arrange
    StandaloneInMemProcessEngineConfiguration processEngineConfiguration =
        new StandaloneInMemProcessEngineConfiguration();
    processEngineConfiguration.setExpressionManager(new ExpressionManager());

    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder, tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualMaxTimerJobsPerAcquisition =
        executorPerTenantAsyncExecutor.getMaxTimerJobsPerAcquisition();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(1, actualMaxTimerJobsPerAcquisition);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getMaxTimerJobsPerAcquisition()}.
   *
   * <ul>
   *   <li>Then return three.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getMaxTimerJobsPerAcquisition()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getMaxTimerJobsPerAcquisition()"})
  public void testGetMaxTimerJobsPerAcquisition_thenReturnThree() {
    // Arrange
    StandaloneInMemProcessEngineConfiguration processEngineConfiguration =
        new StandaloneInMemProcessEngineConfiguration();
    processEngineConfiguration.setExpressionManager(new ExpressionManager());

    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        mock(SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.getMaxTimerJobsPerAcquisition()).thenReturn(3);
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder, tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualMaxTimerJobsPerAcquisition =
        executorPerTenantAsyncExecutor.getMaxTimerJobsPerAcquisition();

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getMaxTimerJobsPerAcquisition();
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals(3, actualMaxTimerJobsPerAcquisition);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getMaxTimerJobsPerAcquisition()}.
   *
   * <ul>
   *   <li>Then throw {@link UnsupportedOperationException}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getMaxTimerJobsPerAcquisition()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getMaxTimerJobsPerAcquisition()"})
  public void testGetMaxTimerJobsPerAcquisition_thenThrowUnsupportedOperationException() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenThrow(new UnsupportedOperationException());
    doThrow(new UnsupportedOperationException())
        .when(tenantInfoHolder)
        .setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAsyncExecutorMaxPoolSize(3);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> executorPerTenantAsyncExecutor.getMaxTimerJobsPerAcquisition());
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setMaxTimerJobsPerAcquisition(int)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#setMaxTimerJobsPerAcquisition(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setMaxTimerJobsPerAcquisition(int)"})
  public void testSetMaxTimerJobsPerAcquisition() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setMaxTimerJobsPerAcquisition(3);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(3, getResult.getMaxTimerJobsPerAcquisition());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setMaxTimerJobsPerAcquisition(int)}.
   *
   * <ul>
   *   <li>Given {@link DummyTenantInfoHolder} (default constructor) addTenant {@code Tenant Id}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#setMaxTimerJobsPerAcquisition(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setMaxTimerJobsPerAcquisition(int)"})
  public void testSetMaxTimerJobsPerAcquisition_givenDummyTenantInfoHolderAddTenantTenantId() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("Tenant Id");
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setMaxTimerJobsPerAcquisition(3);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(3, getResult.getMaxTimerJobsPerAcquisition());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setRetryWaitTimeInMillis(int)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#setRetryWaitTimeInMillis(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setRetryWaitTimeInMillis(int)"})
  public void testSetRetryWaitTimeInMillis() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setRetryWaitTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getRetryWaitTimeInMillis());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setRetryWaitTimeInMillis(int)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#setRetryWaitTimeInMillis(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setRetryWaitTimeInMillis(int)"})
  public void testSetRetryWaitTimeInMillis2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTransactionManager(mock(TransactionManager.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setRetryWaitTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getRetryWaitTimeInMillis());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setRetryWaitTimeInMillis(int)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#setRetryWaitTimeInMillis(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setRetryWaitTimeInMillis(int)"})
  public void testSetRetryWaitTimeInMillis3() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTransactionManager(mock(TransactionManager.class));

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setRetryWaitTimeInMillis(1);

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getRetryWaitTimeInMillis());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setRetryWaitTimeInMillis(int)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#setRetryWaitTimeInMillis(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setRetryWaitTimeInMillis(int)"})
  public void testSetRetryWaitTimeInMillis4() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTransactionManager(mock(TransactionManager.class));

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setRetryWaitTimeInMillis(1);

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setRetryWaitTimeInMillis(int)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#setRetryWaitTimeInMillis(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setRetryWaitTimeInMillis(int)"})
  public void testSetRetryWaitTimeInMillis5() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTransactionManager(mock(TransactionManager.class));

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new ManagedAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setRetryWaitTimeInMillis(1);

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof ManagedAsyncJobExecutor);
    assertEquals(1, getResult.getRetryWaitTimeInMillis());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setRetryWaitTimeInMillis(int)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#setRetryWaitTimeInMillis(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setRetryWaitTimeInMillis(int)"})
  public void testSetRetryWaitTimeInMillis6() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomPreCommandInterceptors(new ArrayList<>());
    processEngineConfiguration.setTransactionManager(mock(TransactionManager.class));

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setRetryWaitTimeInMillis(1);

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getRetryWaitTimeInMillis());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setRetryWaitTimeInMillis(int)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#setRetryWaitTimeInMillis(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setRetryWaitTimeInMillis(int)"})
  public void testSetRetryWaitTimeInMillis7() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDefaultCamelContext("Default Camel Context");
    processEngineConfiguration.setTransactionManager(mock(TransactionManager.class));

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setRetryWaitTimeInMillis(1);

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getRetryWaitTimeInMillis());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setRetryWaitTimeInMillis(int)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#setRetryWaitTimeInMillis(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setRetryWaitTimeInMillis(int)"})
  public void testSetRetryWaitTimeInMillis8() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDatabaseCatalog("Database Catalog");
    processEngineConfiguration.setTransactionManager(mock(TransactionManager.class));

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setRetryWaitTimeInMillis(1);

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getRetryWaitTimeInMillis());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setRetryWaitTimeInMillis(int)}.
   *
   * <ul>
   *   <li>Given {@link DummyTenantInfoHolder} (default constructor) CurrentTenantId is {@code
   *       Tenantid}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#setRetryWaitTimeInMillis(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setRetryWaitTimeInMillis(int)"})
  public void testSetRetryWaitTimeInMillis_givenDummyTenantInfoHolderCurrentTenantIdIsTenantid() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTransactionManager(mock(TransactionManager.class));

    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.setCurrentTenantId("Tenantid");

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new ManagedAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder, tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setRetryWaitTimeInMillis(1);

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof ManagedAsyncJobExecutor);
    assertEquals(1, getResult.getRetryWaitTimeInMillis());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setResetExpiredJobsInterval(int)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#setResetExpiredJobsInterval(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setResetExpiredJobsInterval(int)"})
  public void testSetResetExpiredJobsInterval() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setResetExpiredJobsInterval(42);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(42, getResult.getResetExpiredJobsInterval());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setResetExpiredJobsInterval(int)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#setResetExpiredJobsInterval(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setResetExpiredJobsInterval(int)"})
  public void testSetResetExpiredJobsInterval2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDatabaseWildcardEscapeCharacter(
        "Database Wildcard Escape Character");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setResetExpiredJobsInterval(42);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(42, getResult.getResetExpiredJobsInterval());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setResetExpiredJobsInterval(int)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#setResetExpiredJobsInterval(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setResetExpiredJobsInterval(int)"})
  public void testSetResetExpiredJobsInterval3() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setVariableInstanceDataManager(
        new MybatisVariableInstanceDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setDatabaseWildcardEscapeCharacter(
        "Database Wildcard Escape Character");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setResetExpiredJobsInterval(42);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(42, getResult.getResetExpiredJobsInterval());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setResetExpiredJobsPageSize(int)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#setResetExpiredJobsPageSize(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setResetExpiredJobsPageSize(int)"})
  public void testSetResetExpiredJobsPageSize() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doThrow(new UnsupportedOperationException())
        .when(tenantInfoHolder)
        .setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(null);
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);
    doNothing().when(processEngineConfiguration).addSessionFactory(Mockito.<SessionFactory>any());
    processEngineConfiguration.addSessionFactory(new DbSqlSessionFactory());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setResetExpiredJobsPageSize(3);

    // Assert
    verify(processEngineConfiguration).addSessionFactory(isA(SessionFactory.class));
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setResetExpiredJobsPageSize(int)}.
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#setResetExpiredJobsPageSize(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setResetExpiredJobsPageSize(int)"})
  public void testSetResetExpiredJobsPageSize2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    doNothing().when(processEngineConfiguration).addSessionFactory(Mockito.<SessionFactory>any());
    processEngineConfiguration.addSessionFactory(new DbSqlSessionFactory());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            mock(DummyTenantInfoHolder.class), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setResetExpiredJobsPageSize(3);

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(processEngineConfiguration).addSessionFactory(isA(SessionFactory.class));
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setResetExpiredJobsPageSize(int)}.
   *
   * <ul>
   *   <li>Then calls {@link SharedExecutorServiceAsyncExecutor#getProcessEngineConfiguration()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#setResetExpiredJobsPageSize(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setResetExpiredJobsPageSize(int)"})
  public void testSetResetExpiredJobsPageSize_thenCallsGetProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration =
        mock(JtaProcessEngineConfiguration.class);
    doNothing().when(processEngineConfiguration).addSessionFactory(Mockito.<SessionFactory>any());
    processEngineConfiguration.addSessionFactory(new DbSqlSessionFactory());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        mock(SharedExecutorServiceAsyncExecutor.class);
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).setResetExpiredJobsPageSize(anyInt());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing()
        .when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            mock(DummyTenantInfoHolder.class), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setResetExpiredJobsPageSize(3);

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor).setResetExpiredJobsPageSize(3);
    verify(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor)
        .setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(processEngineConfiguration).addSessionFactory(isA(SessionFactory.class));
  }
}
