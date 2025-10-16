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
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
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
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.impl.DynamicBpmnServiceImpl;
import org.activiti.engine.impl.HistoryServiceImpl;
import org.activiti.engine.impl.ManagementServiceImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.asyncexecutor.AcquireAsyncJobsDueRunnable;
import org.activiti.engine.impl.asyncexecutor.AcquireTimerJobsRunnable;
import org.activiti.engine.impl.asyncexecutor.AsyncExecutor;
import org.activiti.engine.impl.asyncexecutor.DefaultAsyncJobExecutor;
import org.activiti.engine.impl.asyncexecutor.DefaultJobManager;
import org.activiti.engine.impl.asyncexecutor.ExecuteAsyncRunnableFactory;
import org.activiti.engine.impl.asyncexecutor.ManagedAsyncJobExecutor;
import org.activiti.engine.impl.asyncexecutor.ResetExpiredJobsRunnable;
import org.activiti.engine.impl.bpmn.deployer.TimerManager;
import org.activiti.engine.impl.bpmn.listener.ListenerNotificationHelper;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.cfg.IdGenerator;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.PerformanceSettings;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.multitenant.TenantInfoHolder;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextFactory;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.DelegateInterceptor;
import org.activiti.engine.impl.persistence.deploy.DefaultDeploymentCache;
import org.activiti.engine.impl.persistence.deploy.DeploymentManager;
import org.activiti.engine.impl.persistence.entity.AttachmentEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.ByteArrayEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityImpl;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.IdentityLinkEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.JobEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisAttachmentDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisByteArrayDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisDeadLetterJobDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisDeploymentDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricActivityInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisIdentityLinkDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisJobDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisPropertyDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisVariableInstanceDataManager;
import org.activiti.engine.impl.scripting.ScriptingEngines;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.test.cfg.multitenant.DummyTenantInfoHolder;
import org.activiti.engine.test.impl.logger.ProcessExecutionLoggerConfigurator;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
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
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());

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
    assertSame(
        executorPerTenantAsyncExecutor.processEngineConfiguration,
        getResult.getProcessEngineConfiguration());
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
    assertSame(
        executorPerTenantAsyncExecutor.processEngineConfiguration,
        getResult.getProcessEngineConfiguration());
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
    processEngineConfiguration.setAsyncExecutorSecondsToWaitOnShutdown(2L);

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
    assertSame(
        executorPerTenantAsyncExecutor.processEngineConfiguration,
        getResult.getProcessEngineConfiguration());
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
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    processEngineConfiguration.setCommandExecutor(commandExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(null);
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
  public void testAddTenantAsyncExecutor5() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    processEngineConfiguration.setCommandExecutor(commandExecutor);

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(defaultAsyncJobExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

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
  public void testAddTenantAsyncExecutor6() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    processEngineConfiguration.setCommandExecutor(commandExecutor);

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenThrow(new UnsupportedOperationException());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

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
  public void testAddTenantAsyncExecutor7() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    processEngineConfiguration.setCommandExecutor(commandExecutor);

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(executorPerTenantAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor2 =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor2.setProcessEngineConfiguration(processEngineConfiguration);

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
  public void testAddTenantAsyncExecutor8() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    processEngineConfiguration.setCommandExecutor(commandExecutor);

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

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
  public void testAddTenantAsyncExecutor9() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    processEngineConfiguration.setCommandExecutor(commandExecutor);

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    ManagedAsyncJobExecutor managedAsyncJobExecutor = new ManagedAsyncJobExecutor();
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(managedAsyncJobExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    assertSame(managedAsyncJobExecutor, stringAsyncExecutorMap.get("42"));
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
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    processEngineConfiguration.setCommandExecutor(commandExecutor);

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
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

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
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
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
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    processEngineConfiguration.setCommandExecutor(commandExecutor);

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
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

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
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

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
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertSame(processEngineConfiguration, getResult.getProcessEngineConfiguration());
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
  public void testAddTenantAsyncExecutor13() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    processEngineConfiguration.setCommandExecutor(commandExecutor);
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

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
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertSame(processEngineConfiguration, getResult.getProcessEngineConfiguration());
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
  public void testAddTenantAsyncExecutor14() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    processEngineConfiguration.setCommandExecutor(commandExecutor);
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("Starting up the default async job executor [{}]."),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertEquals(1, processEngineConfigurationImpl.getWsOverridenEndpointAddresses().size());
    Set<String> tenantIds = executorPerTenantAsyncExecutor.getTenantIds();
    assertEquals(1, tenantIds.size());
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
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    TenantInfoHolder tenantInfoHolder2 = executorPerTenantAsyncExecutor.tenantInfoHolder;
    Collection<String> allTenants = tenantInfoHolder2.getAllTenants();
    assertEquals(1, allTenants.size());
    assertTrue(allTenants instanceof Set);
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(tenantInfoHolder2 instanceof DummyTenantInfoHolder);
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
  public void testRemoveTenantAsyncExecutor4() {
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
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    TenantInfoHolder tenantInfoHolder2 = executorPerTenantAsyncExecutor.tenantInfoHolder;
    Collection<String> allTenants = tenantInfoHolder2.getAllTenants();
    assertEquals(1, allTenants.size());
    assertTrue(allTenants instanceof Set);
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(tenantInfoHolder2 instanceof DummyTenantInfoHolder);
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
  public void testRemoveTenantAsyncExecutor5() {
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
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder).addTenant("42");
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
  public void testRemoveTenantAsyncExecutor6() {
    // Arrange
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

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
  public void testRemoveTenantAsyncExecutor7() {
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
  public void testRemoveTenantAsyncExecutor8() {
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
  public void testRemoveTenantAsyncExecutor9() {
    // Arrange
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new ManagedAsyncJobExecutor());

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
  public void testRemoveTenantAsyncExecutor10() {
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
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder).addTenant("42");
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
   * <ul>
   *   <li>Then calls {@link SharedExecutorServiceAsyncExecutor#getProcessEngineConfiguration()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor_thenCallsGetProcessEngineConfiguration() {
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
  public void testDetermineAsyncExecutor4() {
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
  public void testDetermineAsyncExecutor5() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneInMemProcessEngineConfiguration());
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
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
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
  public void testDetermineAsyncExecutor7() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.setCurrentTenantId("exception during resetting expired jobs");
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
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setRepositoryService(new RepositoryServiceImpl());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
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
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setHistoryService(
        new HistoryServiceImpl(new JtaProcessEngineConfiguration()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
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
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTaskService(
        new TaskServiceImpl(new JtaProcessEngineConfiguration()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
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
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setBpmnParser(new BpmnParser());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
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
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setIdGenerator(mock(IdGenerator.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
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
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setSerializableVariableTypeTrackDeserializedObjects(true);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
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
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomPostDeployers(new ArrayList<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
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
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
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
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEnableVerboseExecutionTreeLogging(true);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
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
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    VariableInstanceEntityManagerImpl variableInstanceEntityManager =
        new VariableInstanceEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisVariableInstanceDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setVariableInstanceEntityManager(variableInstanceEntityManager);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
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
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDatabaseSchemaUpdate("2020-03-01");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
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
    tenantInfoHolder.addTenant("Tenant Id");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneInMemProcessEngineConfiguration());
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
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
    tenantInfoHolder.setCurrentTenantId("exception during resetting expired jobs");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTransactionManager(mock(TransactionManager.class));

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
    tenantInfoHolder.setCurrentTenantId("exception during resetting expired jobs");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAsyncExecutorDefaultQueueSizeFullWaitTime(3);

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
    tenantInfoHolder.setCurrentTenantId("exception during resetting expired jobs");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJdbcMaxActiveConnections(1);

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
   *   <li>Given {@link DummyTenantInfoHolder} (default constructor) addTenant {@code 42}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor_givenDummyTenantInfoHolderAddTenant42_thenReturnNull() {
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
   * <ul>
   *   <li>Given {@link DummyTenantInfoHolder} (default constructor) addTenant {@code Tenant Id}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor_givenDummyTenantInfoHolderAddTenantTenantId() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("Tenant Id");
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
   *   <li>Given {@link DummyTenantInfoHolder} (default constructor) addTenant {@code Tenant Id}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor_givenDummyTenantInfoHolderAddTenantTenantId2() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("Tenant Id");
    tenantInfoHolder.addTenant("exception during resetting expired jobs");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
   *   <li>Given {@link DummyTenantInfoHolder} (default constructor) CurrentTenantId is {@code
   *       Tenantid}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor_givenDummyTenantInfoHolderCurrentTenantIdIsTenantid() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.setCurrentTenantId("Tenantid");

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
   *   <li>Given {@link DummyTenantInfoHolder} (default constructor) CurrentTenantId is {@code
   *       Tenantid}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor_givenDummyTenantInfoHolderCurrentTenantIdIsTenantid2() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.setCurrentTenantId("Tenantid");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) HistoryLevel is {@code
   *       NONE}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor_givenJtaProcessEngineConfigurationHistoryLevelIsNone() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("Tenant Id");
    tenantInfoHolder.addTenant("exception during resetting expired jobs");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setHistoryLevel(HistoryLevel.NONE);

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
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) IdBlockSize is one.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor_givenJtaProcessEngineConfigurationIdBlockSizeIsOne() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setIdBlockSize(1);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) TaskQueryLimit is one.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor_givenJtaProcessEngineConfigurationTaskQueryLimitIsOne() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.setCurrentTenantId("exception during resetting expired jobs");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTaskQueryLimit(1);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#executeAsyncJob(Job)}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) MailServerPort is
   *       {@code 8080}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#executeAsyncJob(Job)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ExecutorPerTenantAsyncExecutor.executeAsyncJob(Job)"})
  public void testExecuteAsyncJob_givenJtaProcessEngineConfigurationMailServerPortIs8080() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setMailServerPort(8080);

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        mock(SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.executeAsyncJob(Mockito.<Job>any())).thenReturn(true);
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
    boolean actualExecuteAsyncJobResult =
        executorPerTenantAsyncExecutor.executeAsyncJob(new DeadLetterJobEntityImpl());

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).executeAsyncJob(isA(Job.class));
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
    assertTrue(actualExecuteAsyncJobResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#executeAsyncJob(Job)}.
   *
   * <ul>
   *   <li>Then calls {@link SharedExecutorServiceAsyncExecutor#executeAsyncJob(Job)}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#executeAsyncJob(Job)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ExecutorPerTenantAsyncExecutor.executeAsyncJob(Job)"})
  public void testExecuteAsyncJob_thenCallsExecuteAsyncJob() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        mock(SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.executeAsyncJob(Mockito.<Job>any())).thenReturn(true);
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
    boolean actualExecuteAsyncJobResult =
        executorPerTenantAsyncExecutor.executeAsyncJob(new DeadLetterJobEntityImpl());

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).executeAsyncJob(isA(Job.class));
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
  public void testStart4() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("exception during timer job acquisition: {}"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

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
  public void testStart5() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomPreCommandInterceptors(new ArrayList<>());

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
  public void testStart6() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setManagementService(new ManagementServiceImpl());

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
  public void testStart7() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJavaClassFieldForJackson(
        "exception during timer job acquisition: {}");

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
  public void testStart8() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

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
  public void testStart9() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDeadLetterJobDataManager(
        new MybatisDeadLetterJobDataManager(new JtaProcessEngineConfiguration()));

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
  public void testStart10() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    HistoricActivityInstanceEntityManagerImpl historicActivityInstanceEntityManager =
        new HistoricActivityInstanceEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisHistoricActivityInstanceDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setHistoricActivityInstanceEntityManager(
        historicActivityInstanceEntityManager);

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
  public void testStart11() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    JobEntityManagerImpl jobEntityManager =
        new JobEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisJobDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setJobEntityManager(jobEntityManager);

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
  public void testStart12() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setHistoricProcessInstancesQueryLimit(1);
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("exception during timer job acquisition: {}"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

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
  public void testStart13() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCommandContextFactory(new CommandContextFactory());
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("exception during timer job acquisition: {}"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

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
  public void testStart14() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    IdentityLinkEntityManagerImpl identityLinkEntityManager =
        new IdentityLinkEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisIdentityLinkDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setIdentityLinkEntityManager(identityLinkEntityManager);
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("exception during timer job acquisition: {}"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

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
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor)
   *       AsyncExecutorMaxPoolSize is three.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart_givenJtaProcessEngineConfigurationAsyncExecutorMaxPoolSizeIsThree() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAsyncExecutorMaxPoolSize(3);

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
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor)
   *       AsyncExecutorThreadPoolQueue is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart_givenJtaProcessEngineConfigurationAsyncExecutorThreadPoolQueueIsNull()
      throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAsyncExecutorThreadPoolQueue(null);
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("exception during timer job acquisition: {}"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

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
  public void testStart_givenJtaProcessEngineConfigurationCustomMybatisXMLMappersIsHashSet() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomMybatisXMLMappers(new HashSet<>());

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
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) Deployers is {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart_givenJtaProcessEngineConfigurationDeployersIsArrayList()
      throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDeployers(new ArrayList<>());
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("exception during timer job acquisition: {}"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

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
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor)
   *       ProcessDefinitionCacheLimit is one.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart_givenJtaProcessEngineConfigurationProcessDefinitionCacheLimitIsOne()
      throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setProcessDefinitionCacheLimit(1);
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("exception during timer job acquisition: {}"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

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
  public void testStart_givenJtaProcessEngineConfigurationSchemaCommandConfigIsCommandConfig() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setSchemaCommandConfig(new CommandConfig());

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
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor)
   *       TransactionsExternallyManaged is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart_givenJtaProcessEngineConfigurationTransactionsExternallyManagedIsTrue()
      throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTransactionsExternallyManaged(true);
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("exception during timer job acquisition: {}"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

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
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) UserGroupManager is
   *       {@link UserGroupManager}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart_givenJtaProcessEngineConfigurationUserGroupManagerIsUserGroupManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setUserGroupManager(mock(UserGroupManager.class));

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
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) UsingRelationalDatabase
   *       is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart_givenJtaProcessEngineConfigurationUsingRelationalDatabaseIsTrue() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setUsingRelationalDatabase(true);

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
   *   <li>Given {@link PerformanceSettings} (default constructor) EnableEagerExecutionTreeFetching
   *       is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart_givenPerformanceSettingsEnableEagerExecutionTreeFetchingIsTrue()
      throws MalformedURLException {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setPerformanceSettings(performanceSettings);
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("exception during timer job acquisition: {}"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

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
   *   <li>Then {@link
   *       ExecutorPerTenantAsyncExecutor#ExecutorPerTenantAsyncExecutor(TenantInfoHolder)} with
   *       tenantInfoHolder is {@code null} Active.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart_thenExecutorPerTenantAsyncExecutorWithTenantInfoHolderIsNullActive() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(null);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
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
  public void testShutdown3() {
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
  public void testShutdown4() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.setCurrentTenantId("{} starting to reset expired jobs");
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
  public void testShutdown5() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("exception during timer job acquisition: {}");
    tenantInfoHolder.addTenant("{} starting to reset expired jobs");
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
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");
    tenantInfoHolder.addTenant("exception during timer job acquisition: {}");
    tenantInfoHolder.addTenant("{} starting to reset expired jobs");
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
  public void testShutdown7() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.setCurrentTenantId("exception during resetting expired jobs");
    tenantInfoHolder.addTenant("exception during timer job acquisition: {}");
    tenantInfoHolder.addTenant("{} starting to reset expired jobs");
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
  public void testShutdown8() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneInMemProcessEngineConfiguration());
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
  public void testShutdown9() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
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
  public void testShutdown10() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
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
  public void testShutdown11() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("{} starting to reset expired jobs");
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
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
  public void testShutdown12() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("{} starting to reset expired jobs");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
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
  public void testShutdown13() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDefaultCommandConfig(new CommandConfig());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
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
  public void testShutdown14() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomPostCommandInterceptors(new ArrayList<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
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
  public void testShutdown15() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setHistoryService(
        new HistoryServiceImpl(new JtaProcessEngineConfiguration()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
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
  public void testShutdown16() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTaskService(
        new TaskServiceImpl(new JtaProcessEngineConfiguration()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
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
  public void testShutdown17() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomScriptingEngineClasses(new ArrayList<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
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
  public void testShutdown18() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDelegateInterceptor(mock(DelegateInterceptor.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
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
  public void testShutdown19() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
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
  public void testShutdown20() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    ByteArrayEntityManagerImpl byteArrayEntityManager =
        new ByteArrayEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisByteArrayDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setByteArrayEntityManager(byteArrayEntityManager);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
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
  public void testShutdown21() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    JobEntityManagerImpl jobEntityManager =
        new JobEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisJobDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setJobEntityManager(jobEntityManager);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
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
  public void testShutdown22() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAsyncExecutorAsyncJobLockTimeInMillis(1);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
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
  public void testShutdown23() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    StandaloneInMemProcessEngineConfiguration processEngineConfiguration =
        new StandaloneInMemProcessEngineConfiguration();
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
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
  public void testShutdown24() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("{} starting to reset expired jobs");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setRuntimeService(new RuntimeServiceImpl());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
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
  public void testShutdown25() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("{} starting to reset expired jobs");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setKnowledgeBaseCache(new DefaultDeploymentCache<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
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
  public void testShutdown26() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("{} starting to reset expired jobs");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDeploymentDataManager(
        new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
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
  public void testShutdown27() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("{} starting to reset expired jobs");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setMailServerDefaultFrom("jane.doe@example.org");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
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
  public void testShutdown28() throws MalformedURLException {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("{} starting to reset expired jobs");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setScriptingEngines(new ScriptingEngines(new ScriptEngineManager()));
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("exception during resetting expired jobs"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
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
  public void testShutdown29() throws MalformedURLException {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("{} starting to reset expired jobs");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    AttachmentEntityManagerImpl attachmentEntityManager =
        new AttachmentEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setAttachmentEntityManager(attachmentEntityManager);
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("exception during resetting expired jobs"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
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
    assertFalse(getResult.isActive());
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
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("{} starting to reset expired jobs");
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
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) AsyncExecutorActivate
   *       is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown_givenJtaProcessEngineConfigurationAsyncExecutorActivateIsTrue()
      throws MalformedURLException {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("{} starting to reset expired jobs");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAsyncExecutorActivate(true);
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("exception during resetting expired jobs"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
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
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) CustomEventHandlers is
   *       {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown_givenJtaProcessEngineConfigurationCustomEventHandlersIsArrayList() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomEventHandlers(new ArrayList<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
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
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) CustomSessionFactories
   *       is {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown_givenJtaProcessEngineConfigurationCustomSessionFactoriesIsArrayList()
      throws MalformedURLException {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("{} starting to reset expired jobs");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomSessionFactories(new ArrayList<>());
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("exception during resetting expired jobs"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
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
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) DbHistoryUsed is {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown_givenJtaProcessEngineConfigurationDbHistoryUsedIsTrue() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDbHistoryUsed(true);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
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
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) IdBlockSize is one.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown_givenJtaProcessEngineConfigurationIdBlockSizeIsOne() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setIdBlockSize(1);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
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
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) JobHandlers is {@link
   *       HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown_givenJtaProcessEngineConfigurationJobHandlersIsHashMap()
      throws MalformedURLException {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("{} starting to reset expired jobs");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJobHandlers(new HashMap<>());
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("exception during resetting expired jobs"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
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
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) JobManager is {@link
   *       DefaultJobManager#DefaultJobManager()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown_givenJtaProcessEngineConfigurationJobManagerIsDefaultJobManager() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("{} starting to reset expired jobs");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJobManager(new DefaultJobManager());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
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
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) SessionFactories is
   *       {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown_givenJtaProcessEngineConfigurationSessionFactoriesIsHashMap() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("{} starting to reset expired jobs");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setSessionFactories(new HashMap<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
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
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) TimerManager is {@link
   *       TimerManager} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown_givenJtaProcessEngineConfigurationTimerManagerIsTimerManager() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTimerManager(new TimerManager());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
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
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor)
   *       UseClassForNameClassLoading is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown_givenJtaProcessEngineConfigurationUseClassForNameClassLoadingIsTrue()
      throws MalformedURLException {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("{} starting to reset expired jobs");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setUseClassForNameClassLoading(true);
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("exception during resetting expired jobs"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
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
    tenantInfoHolder.setCurrentTenantId("Shutting down the default async job executor [{}].");

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
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
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
  public void testShutdownTenantExecutor5() {
    // Arrange
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

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
  public void testShutdownTenantExecutor7() {
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
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

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
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("Shutting down the default async job executor [{}].");
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
  public void testShutdownTenantExecutor9() {
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
  public void testShutdownTenantExecutor10() {
    // Arrange
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

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
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
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
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof ManagedAsyncJobExecutor);
    assertNull(((ManagedAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((ManagedAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((ManagedAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((ManagedAsyncJobExecutor) getResult).getExecutorService());
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
   *   <li>Then calls {@link TenantInfoHolder#clearCurrentTenantId()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor_thenCallsClearCurrentTenantId() {
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
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
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
        new JtaProcessEngineConfiguration());
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
  public void testGetAsyncJobLockTimeInMillis4() {
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
  public void testGetAsyncJobLockTimeInMillis5() throws MalformedURLException {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("activiti-acquire-async-jobs"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

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
  public void testGetAsyncJobLockTimeInMillis6() {
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
    processEngineConfiguration.setManagementService(new ManagementServiceImpl());

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
    processEngineConfiguration.setSessionFactories(new HashMap<>());

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
  public void testGetAsyncJobLockTimeInMillis11() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setSerializePOJOsInVariablesToJson(true);

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
  public void testGetAsyncJobLockTimeInMillis12() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setMaxLengthStringVariableType(3);

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
    processEngineConfiguration.setUsingRelationalDatabase(true);

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
    processEngineConfiguration.setIdentityLinkDataManager(
        new MybatisIdentityLinkDataManager(new JtaProcessEngineConfiguration()));

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
    processEngineConfiguration.setPropertyDataManager(
        new MybatisPropertyDataManager(new JtaProcessEngineConfiguration()));

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
    processEngineConfiguration.setAsyncExecutorCorePoolSize(3);

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
    processEngineConfiguration.setAsyncExecutorMaxPoolSize(3);

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
    processEngineConfiguration.setAsyncExecutorMaxAsyncJobsDuePerAcquisition(1);

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

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJdbcMaxWaitTime(1);

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
  public void testGetAsyncJobLockTimeInMillis21() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setSchemaCommandConfig(new CommandConfig());
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
    processEngineConfiguration.setSessionFactories(new HashMap<>());
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
    processEngineConfiguration.setJobHandlers(new HashMap<>());
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
    processEngineConfiguration.setEnableSafeBpmnXml(true);
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
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setProcessEngineName("{} starting to acquire async jobs due");
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
  public void testGetAsyncJobLockTimeInMillis26() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setIdBlockSize(1);
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
  public void testGetAsyncJobLockTimeInMillis27() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDatabaseType("{} starting to acquire async jobs due");
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
    int actualDefaultAsyncJobAcquireWaitTimeInMillis =
        executorPerTenantAsyncExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis();

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(10000, actualDefaultAsyncJobAcquireWaitTimeInMillis);
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
  public void testGetDefaultAsyncJobAcquireWaitTimeInMillis2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setSqlSessionFactory(
        new DefaultSqlSessionFactory(new Configuration()));

    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    AsyncExecutor asyncExecutor = mock(AsyncExecutor.class);
    when(asyncExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis()).thenReturn(1);
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
    int actualDefaultAsyncJobAcquireWaitTimeInMillis =
        executorPerTenantAsyncExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis();

    // Assert
    verify(asyncExecutor).getDefaultAsyncJobAcquireWaitTimeInMillis();
    verify(asyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(asyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals(1, actualDefaultAsyncJobAcquireWaitTimeInMillis);
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
  public void testGetDefaultAsyncJobAcquireWaitTimeInMillis3() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManager =
        new DeadLetterJobEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisDeadLetterJobDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setDeadLetterJobEntityManager(deadLetterJobEntityManager);

    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    AsyncExecutor asyncExecutor = mock(AsyncExecutor.class);
    when(asyncExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis()).thenReturn(1);
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
    int actualDefaultAsyncJobAcquireWaitTimeInMillis =
        executorPerTenantAsyncExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis();

    // Assert
    verify(asyncExecutor).getDefaultAsyncJobAcquireWaitTimeInMillis();
    verify(asyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(asyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals(1, actualDefaultAsyncJobAcquireWaitTimeInMillis);
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
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    AsyncExecutor asyncExecutor = mock(AsyncExecutor.class);
    when(asyncExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis()).thenReturn(1);
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
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualDefaultAsyncJobAcquireWaitTimeInMillis =
        executorPerTenantAsyncExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis();

    // Assert
    verify(asyncExecutor).getDefaultAsyncJobAcquireWaitTimeInMillis();
    verify(asyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(asyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals(1, actualDefaultAsyncJobAcquireWaitTimeInMillis);
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
  public void testGetDefaultAsyncJobAcquireWaitTimeInMillis_thenReturnOne2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJdbcPingEnabled(true);

    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    AsyncExecutor asyncExecutor = mock(AsyncExecutor.class);
    when(asyncExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis()).thenReturn(1);
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
    int actualDefaultAsyncJobAcquireWaitTimeInMillis =
        executorPerTenantAsyncExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis();

    // Assert
    verify(asyncExecutor).getDefaultAsyncJobAcquireWaitTimeInMillis();
    verify(asyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(asyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(tenantInfoHolder).addTenant("42");
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
    processEngineConfiguration.setListenerNotificationHelper(new ListenerNotificationHelper());

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
  public void testSetDefaultQueueSizeFullWaitTimeInMillis3() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEnableEventDispatcher(true);

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
  public void testSetDefaultQueueSizeFullWaitTimeInMillis4() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJpaHandleTransaction(true);

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
  public void testSetDefaultQueueSizeFullWaitTimeInMillis5() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDynamicBpmnService(
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setEnableEventDispatcher(true);

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
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneInMemProcessEngineConfiguration());
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
    processEngineConfiguration.setTypedEventListeners(new HashMap<>());

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
    processEngineConfiguration.setDeploymentManager(new DeploymentManager());
    processEngineConfiguration.setTypedEventListeners(new HashMap<>());

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
    DeploymentManager deploymentManager = new DeploymentManager();
    deploymentManager.setProcessDefinitionCache(new DefaultDeploymentCache<>());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDeploymentManager(deploymentManager);
    processEngineConfiguration.setTypedEventListeners(new HashMap<>());

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
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setResetExpiredJobsPageSize(3);

    // Assert that nothing has changed
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(3, getResult.getResetExpiredJobsPageSize());
  }
}
