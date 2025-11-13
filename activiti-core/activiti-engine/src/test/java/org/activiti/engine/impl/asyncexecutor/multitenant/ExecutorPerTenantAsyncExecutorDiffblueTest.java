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
import com.fasterxml.jackson.databind.json.JsonMapper;
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
import javax.sql.DataSource;
import javax.xml.namespace.QName;
import org.activiti.api.runtime.shared.identity.UserGroupManager;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.ActivitiEngineAgendaFactory;
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.impl.DynamicBpmnServiceImpl;
import org.activiti.engine.impl.ManagementServiceImpl;
import org.activiti.engine.impl.ProcessDefinitionHelper;
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
import org.activiti.engine.impl.bpmn.deployer.BpmnDeployer;
import org.activiti.engine.impl.bpmn.deployer.ParsedDeploymentBuilderFactory;
import org.activiti.engine.impl.bpmn.deployer.TimerManager;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.cfg.BpmnParseFactory;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.PerformanceSettings;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.multitenant.TenantInfoHolder;
import org.activiti.engine.impl.event.EventSubscriptionPayloadMappingProvider;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextFactory;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.persistence.deploy.DefaultDeploymentCache;
import org.activiti.engine.impl.persistence.deploy.DeploymentManager;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityImpl;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.SuspendedJobEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.TaskEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisAttachmentDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisByteArrayDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisDeadLetterJobDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisDeploymentDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisEventSubscriptionDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricIdentityLinkDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricProcessInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisIdentityLinkDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisModelDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisProcessDefinitionDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisProcessDefinitionInfoDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisPropertyDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisSuspendedJobDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisTaskDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisTimerJobDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisVariableInstanceDataManager;
import org.activiti.engine.impl.scripting.ScriptingEngines;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.test.cfg.multitenant.DummyTenantInfoHolder;
import org.activiti.engine.test.impl.logger.ProcessExecutionLoggerConfigurator;
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
    assertTrue(stringAsyncExecutorMap.get("42") instanceof DefaultAsyncJobExecutor);
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
  public void testAddTenantAsyncExecutor2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    assertTrue(stringAsyncExecutorMap.get("42") instanceof DefaultAsyncJobExecutor);
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
  public void testAddTenantAsyncExecutor3() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomMybatisXMLMappers(new HashSet<>());
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    assertTrue(stringAsyncExecutorMap.get("42") instanceof DefaultAsyncJobExecutor);
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
  public void testAddTenantAsyncExecutor4() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    processEngineConfiguration.setCommandExecutor(commandExecutor);
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    assertTrue(stringAsyncExecutorMap.get("42") instanceof DefaultAsyncJobExecutor);
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
  public void testAddTenantAsyncExecutor5() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventSubscriptionDataManager(
        new MybatisEventSubscriptionDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    assertTrue(stringAsyncExecutorMap.get("42") instanceof DefaultAsyncJobExecutor);
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
  public void testAddTenantAsyncExecutor6() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDatabaseSchemaUpdate("2020-03-01");
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    assertTrue(stringAsyncExecutorMap.get("42") instanceof DefaultAsyncJobExecutor);
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
  public void testAddTenantAsyncExecutor7() {
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
    assertTrue(stringAsyncExecutorMap.get("42") instanceof DefaultAsyncJobExecutor);
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
  public void testAddTenantAsyncExecutor8() {
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
  public void testAddTenantAsyncExecutor9() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAsyncExecutorMaxAsyncJobsDuePerAcquisition(2);

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
  public void testAddTenantAsyncExecutor10() {
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
  public void testAddTenantAsyncExecutor11() {
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
  public void testAddTenantAsyncExecutor12() {
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
  public void testAddTenantAsyncExecutor13() {
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
  public void testAddTenantAsyncExecutor14() {
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
  public void testAddTenantAsyncExecutor15() {
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
  public void testAddTenantAsyncExecutor16() {
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
  public void testRemoveTenantAsyncExecutor3() {
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
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    TenantInfoHolder tenantInfoHolder2 = executorPerTenantAsyncExecutor.tenantInfoHolder;
    Collection<String> allTenants = tenantInfoHolder2.getAllTenants();
    assertEquals(2, allTenants.size());
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
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    ProcessEngineConfigurationImpl processEngineConfigurationImpl =
        executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof StandaloneProcessEngineConfiguration);
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
  public void testRemoveTenantAsyncExecutor7() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setSchemaCommandConfig(new CommandConfig());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
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
  public void testRemoveTenantAsyncExecutor8() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomPostCommandInterceptors(new ArrayList<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
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
  public void testRemoveTenantAsyncExecutor9() {
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
  public void testRemoveTenantAsyncExecutor10() {
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
  public void testRemoveTenantAsyncExecutor11() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setConfigurators(new ArrayList<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
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
  public void testRemoveTenantAsyncExecutor12() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setBpmnParser(new BpmnParser());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
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
  public void testRemoveTenantAsyncExecutor13() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
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
  public void testRemoveTenantAsyncExecutor14() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setHistoricProcessInstancesQueryLimit(1);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
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
  public void testRemoveTenantAsyncExecutor15() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomPostDeployers(new ArrayList<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
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
  public void testRemoveTenantAsyncExecutor16() {
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
  public void testRemoveTenantAsyncExecutor17() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setPostBpmnParseHandlers(new ArrayList<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
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
  public void testRemoveTenantAsyncExecutor18() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setBpmnParseFactory(mock(BpmnParseFactory.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
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
  public void testRemoveTenantAsyncExecutor19() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventListeners(new ArrayList<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
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
  public void testRemoveTenantAsyncExecutor20() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEnableDatabaseEventLogging(true);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
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
  public void testRemoveTenantAsyncExecutor21() {
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
  public void testRemoveTenantAsyncExecutor22() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTaskDataManager(
        new MybatisTaskDataManager(new JtaProcessEngineConfiguration()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
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
  public void testRemoveTenantAsyncExecutor23() {
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
  public void testRemoveTenantAsyncExecutor24() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setProcessDefinitionHelper(mock(ProcessDefinitionHelper.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
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
  public void testRemoveTenantAsyncExecutor25() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setMailServerUseSSL(true);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
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
  public void testRemoveTenantAsyncExecutor26() {
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
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
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
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    TenantInfoHolder tenantInfoHolder = executorPerTenantAsyncExecutor.tenantInfoHolder;
    Collection<String> allTenants = tenantInfoHolder.getAllTenants();
    assertTrue(allTenants instanceof Set);
    assertTrue(tenantInfoHolder instanceof DummyTenantInfoHolder);
    assertTrue(allTenants.isEmpty());
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
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    TenantInfoHolder tenantInfoHolder = executorPerTenantAsyncExecutor.tenantInfoHolder;
    Collection<String> allTenants = tenantInfoHolder.getAllTenants();
    assertTrue(allTenants instanceof Set);
    assertTrue(tenantInfoHolder instanceof DummyTenantInfoHolder);
    assertTrue(allTenants.isEmpty());
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
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(processEngineConfiguration).getJobManager();
    TenantInfoHolder tenantInfoHolder = executorPerTenantAsyncExecutor.tenantInfoHolder;
    Collection<String> allTenants = tenantInfoHolder.getAllTenants();
    assertTrue(allTenants instanceof Set);
    assertTrue(tenantInfoHolder instanceof DummyTenantInfoHolder);
    assertTrue(allTenants.isEmpty());
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
        mock(ProcessEngineConfigurationImpl.class));
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
    assertTrue(tenantInfoHolder instanceof DummyTenantInfoHolder);
    assertTrue(allTenants.isEmpty());
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
    tenantInfoHolder.setCurrentTenantId("exception during resetting expired jobs");
    tenantInfoHolder.addTenant("Tenant Id");
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
    tenantInfoHolder.addTenant("42");

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
  public void testDetermineAsyncExecutor6() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
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
  public void testDetermineAsyncExecutor7() throws MalformedURLException {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("Q Name As String"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

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
  public void testDetermineAsyncExecutor8() throws MalformedURLException {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    StandaloneInMemProcessEngineConfiguration processEngineConfiguration =
        new StandaloneInMemProcessEngineConfiguration();
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("Q Name As String"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

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
  public void testDetermineAsyncExecutor9() throws MalformedURLException {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    StandaloneProcessEngineConfiguration processEngineConfiguration =
        new StandaloneProcessEngineConfiguration();
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("Q Name As String"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

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
  public void testDetermineAsyncExecutor10() throws MalformedURLException {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("Q Name As String"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

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
  public void testDetermineAsyncExecutor11() throws MalformedURLException {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setSchemaCommandConfig(new CommandConfig());
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("Q Name As String"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

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
  public void testDetermineAsyncExecutor12() throws MalformedURLException {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomPostCommandInterceptors(new ArrayList<>());
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("Q Name As String"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

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
  public void testDetermineAsyncExecutor13() throws MalformedURLException {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setRuntimeService(new RuntimeServiceImpl());
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("Q Name As String"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

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
  public void testDetermineAsyncExecutor14() throws MalformedURLException {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTimerManager(new TimerManager());
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("Q Name As String"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

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
  public void testDetermineAsyncExecutor15() throws MalformedURLException {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setIdGeneratorDataSourceJndiName(
        "{} starting to reset expired jobs");
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("Q Name As String"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

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
  public void testDetermineAsyncExecutor16() throws MalformedURLException {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDeploymentDataManager(
        new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("Q Name As String"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

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
  public void testDetermineAsyncExecutor17() throws MalformedURLException {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    SuspendedJobEntityManagerImpl suspendedJobEntityManager =
        new SuspendedJobEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisSuspendedJobDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setSuspendedJobEntityManager(suspendedJobEntityManager);
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("Q Name As String"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

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
  public void testDetermineAsyncExecutor18() throws MalformedURLException {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAsyncExecutorNumberOfRetries(10);
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("Q Name As String"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

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
  public void testDetermineAsyncExecutor19() throws MalformedURLException {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJdbcMaxCheckoutTime(10000);
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("Q Name As String"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

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
  public void testDetermineAsyncExecutor20() throws MalformedURLException {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    processEngineConfiguration.setCommandExecutor(commandExecutor);
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("Q Name As String"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

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
  public void testDetermineAsyncExecutor21() throws MalformedURLException {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventDispatcher(new ActivitiEventDispatcherImpl());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("Q Name As String"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

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
  public void testDetermineAsyncExecutor22() throws MalformedURLException {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDeploymentDataManager(
        new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("Q Name As String"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

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
  public void testDetermineAsyncExecutor23() throws MalformedURLException {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    VariableInstanceEntityManagerImpl variableInstanceEntityManager =
        new VariableInstanceEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisVariableInstanceDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setVariableInstanceEntityManager(variableInstanceEntityManager);
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("Q Name As String"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

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
  public void testDetermineAsyncExecutor24() throws MalformedURLException {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setMailSessionJndi("Mail Session Jndi");
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("Q Name As String"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

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
  public void testDetermineAsyncExecutor25() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
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
  public void testDetermineAsyncExecutor26() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setIdGeneratorDataSource(mock(DataSource.class));

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
  public void testDetermineAsyncExecutor27() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
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
  public void testDetermineAsyncExecutor28() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTimerJobDataManager(
        new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));

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
  public void testDetermineAsyncExecutor29() throws MalformedURLException {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("activiti-reset-expired-jobs");
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setRuntimeService(new RuntimeServiceImpl());
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("Q Name As String"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

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
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#executeAsyncJob(Job)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ExecutorPerTenantAsyncExecutor.executeAsyncJob(Job)"})
  public void testExecuteAsyncJob() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCustomPostCommandInterceptors(new ArrayList<>());

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
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#executeAsyncJob(Job)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ExecutorPerTenantAsyncExecutor.executeAsyncJob(Job)"})
  public void testExecuteAsyncJob2() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");

    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTypedEventListeners(new HashMap<>());

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
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
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
        new StandaloneProcessEngineConfiguration());
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
    processEngineConfiguration.setTransactionManager(mock(TransactionManager.class));

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
    processEngineConfiguration.setCustomPostCommandInterceptors(new ArrayList<>());

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
    processEngineConfiguration.setSerializableVariableTypeTrackDeserializedObjects(true);

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
    processEngineConfiguration.setIdentityLinkDataManager(
        new MybatisIdentityLinkDataManager(new JtaProcessEngineConfiguration()));

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
    processEngineConfiguration.setModelDataManager(
        new MybatisModelDataManager(new JtaProcessEngineConfiguration()));

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
    HistoricIdentityLinkEntityManagerImpl historicIdentityLinkEntityManager =
        new HistoricIdentityLinkEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisHistoricIdentityLinkDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setHistoricIdentityLinkEntityManager(
        historicIdentityLinkEntityManager);

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
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
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
   *   <li>Given {@link DummyTenantInfoHolder} (default constructor) addTenant {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart_givenDummyTenantInfoHolderAddTenant422() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTransactionManager(mock(TransactionManager.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
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
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) BpmnDeployer is {@link
   *       BpmnDeployer} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart_givenJtaProcessEngineConfigurationBpmnDeployerIsBpmnDeployer() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setBpmnDeployer(new BpmnDeployer());

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
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) DefaultCommandConfig is
   *       {@link CommandConfig#CommandConfig()}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart_givenJtaProcessEngineConfigurationDefaultCommandConfigIsCommandConfig() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDefaultCommandConfig(new CommandConfig());

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
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) DeploymentManager is
   *       {@link DeploymentManager} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart_givenJtaProcessEngineConfigurationDeploymentManagerIsDeploymentManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDeploymentManager(new DeploymentManager());
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
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
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) EnableEventDispatcher
   *       is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart_givenJtaProcessEngineConfigurationEnableEventDispatcherIsTrue() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEnableEventDispatcher(true);

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
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) TaskQueryLimit is one.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart_givenJtaProcessEngineConfigurationTaskQueryLimitIsOne() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTaskQueryLimit(1);

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
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) TimerManager is {@link
   *       TimerManager} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart_givenJtaProcessEngineConfigurationTimerManagerIsTimerManager() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTimerManager(new TimerManager());

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
  public void testStart_givenPerformanceSettingsEnableEagerExecutionTreeFetchingIsTrue() {
    // Arrange
    PerformanceSettings performanceSettings = new PerformanceSettings();
    performanceSettings.setEnableEagerExecutionTreeFetching(true);
    performanceSettings.setEnableExecutionRelationshipCounts(true);
    performanceSettings.setEnableLocalization(true);
    performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setPerformanceSettings(performanceSettings);

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
        new JtaProcessEngineConfiguration());
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
        new JtaProcessEngineConfiguration());
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
        new JtaProcessEngineConfiguration());
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
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof ManagedAsyncJobExecutor);
    assertNull(((ManagedAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((ManagedAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((ManagedAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((ManagedAsyncJobExecutor) getResult).getExecutorService());
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
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setSchemaCommandConfig(new CommandConfig());

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
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomPreCommandInterceptors(new ArrayList<>());

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
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
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
  public void testShutdown12() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setScriptingEngines(new ScriptingEngines(new ScriptEngineManager()));

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
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCommandContextFactory(new CommandContextFactory());

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
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setKnowledgeBaseCache(new DefaultDeploymentCache<>());

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
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAttachmentDataManager(
        new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration()));

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
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setSuspendedJobDataManager(
        new MybatisSuspendedJobDataManager(new JtaProcessEngineConfiguration()));

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
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setPropertyDataManager(
        new MybatisPropertyDataManager(new JtaProcessEngineConfiguration()));

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
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManager =
        new HistoricProcessInstanceEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisHistoricProcessInstanceDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setHistoricProcessInstanceEntityManager(
        historicProcessInstanceEntityManager);

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
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManager =
        new ProcessDefinitionEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisProcessDefinitionDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setProcessDefinitionEntityManager(processDefinitionEntityManager);

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
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventSubscriptionPayloadMappingProvider(
        mock(EventSubscriptionPayloadMappingProvider.class));

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
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJdbcDefaultTransactionIsolationLevel(10000);

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
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setAsyncJobsDueRunnable(
        new AcquireAsyncJobsDueRunnable(new DefaultAsyncJobExecutor()));

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(defaultAsyncJobExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder, tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
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
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAttachmentDataManager(
        new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration()));

    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("exception during resetting expired jobs");

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
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown24() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setPropertyDataManager(
        new MybatisPropertyDataManager(new JtaProcessEngineConfiguration()));

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
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof ManagedAsyncJobExecutor);
    assertNull(((ManagedAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((ManagedAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((ManagedAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((ManagedAsyncJobExecutor) getResult).getExecutorService());
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
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManager =
        new HistoricProcessInstanceEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisHistoricProcessInstanceDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setHistoricProcessInstanceEntityManager(
        historicProcessInstanceEntityManager);

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
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown26() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setHistoricProcessInstanceDataManager(
        new MybatisHistoricProcessInstanceDataManager(new JtaProcessEngineConfiguration()));

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
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof ManagedAsyncJobExecutor);
    assertNull(((ManagedAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((ManagedAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((ManagedAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((ManagedAsyncJobExecutor) getResult).getExecutorService());
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
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setProcessDefinitionInfoDataManager(
        new MybatisProcessDefinitionInfoDataManager(new JtaProcessEngineConfiguration()));

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
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof ManagedAsyncJobExecutor);
    assertNull(((ManagedAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((ManagedAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((ManagedAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((ManagedAsyncJobExecutor) getResult).getExecutorService());
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
  public void testShutdown28() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManager =
        new DeadLetterJobEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisDeadLetterJobDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setDeadLetterJobEntityManager(deadLetterJobEntityManager);

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
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof ManagedAsyncJobExecutor);
    assertNull(((ManagedAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((ManagedAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((ManagedAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((ManagedAsyncJobExecutor) getResult).getExecutorService());
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
  public void testShutdown29() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEngineAgendaFactory(mock(ActivitiEngineAgendaFactory.class));

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
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof ManagedAsyncJobExecutor);
    assertNull(((ManagedAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((ManagedAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((ManagedAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((ManagedAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <ul>
   *   <li>Given {@link DefaultAsyncJobExecutor} (default constructor) AsyncJobLockTimeInMillis is
   *       {@code 10000}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown_givenDefaultAsyncJobExecutorAsyncJobLockTimeInMillisIs10000() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setAsyncJobLockTimeInMillis(10000);

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(defaultAsyncJobExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
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
        new JtaProcessEngineConfiguration());
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
        new JtaProcessEngineConfiguration());
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
  public void testShutdown_givenJtaProcessEngineConfigurationCustomSessionFactoriesIsArrayList() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomSessionFactories(new ArrayList<>());

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
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof ManagedAsyncJobExecutor);
    assertNull(((ManagedAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((ManagedAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((ManagedAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((ManagedAsyncJobExecutor) getResult).getExecutorService());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) JdbcMaxCheckoutTime is
   *       {@code 10000}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown_givenJtaProcessEngineConfigurationJdbcMaxCheckoutTimeIs10000() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJdbcMaxCheckoutTime(10000);

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
  public void testShutdown_givenParsedDeploymentBuilderFactoryBpmnParserIsBpmnParser() {
    // Arrange
    ParsedDeploymentBuilderFactory parsedDeploymentBuilderFactory =
        new ParsedDeploymentBuilderFactory();
    parsedDeploymentBuilderFactory.setBpmnParser(new BpmnParser());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setParsedDeploymentBuilderFactory(parsedDeploymentBuilderFactory);

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
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    Map<String, AsyncExecutor> stringAsyncExecutorMap =
        executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof ManagedAsyncJobExecutor);
    assertNull(((ManagedAsyncJobExecutor) getResult).getAsyncJobAcquisitionThread());
    assertNull(((ManagedAsyncJobExecutor) getResult).getResetExpiredJobThread());
    assertNull(((ManagedAsyncJobExecutor) getResult).getTimerJobAcquisitionThread());
    assertNull(((ManagedAsyncJobExecutor) getResult).getExecutorService());
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
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
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
  public void testShutdownTenantExecutor7() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder()));

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
  public void testShutdownTenantExecutor8() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder()));

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

    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setResetExpiredJobThread(new Thread());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(defaultAsyncJobExecutor);

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

    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setSecondsToWaitOnShutdown(1L);

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(defaultAsyncJobExecutor);

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

    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setMaxTimerJobsPerAcquisition(3);

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(defaultAsyncJobExecutor);

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

    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setDefaultTimerJobAcquireWaitTimeInMillis(1);

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(defaultAsyncJobExecutor);

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
  public void testShutdownTenantExecutor15() {
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
  public void testShutdownTenantExecutor16() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomPreCommandInterceptors(new ArrayList<>());

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
  public void testShutdownTenantExecutor18() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setObjectMapper(JsonMapper.builder().findAndAddModules().build());

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
    processEngineConfiguration.setJdbcDefaultTransactionIsolationLevel(1);

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
  public void testShutdownTenantExecutor21() {
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
  public void testShutdownTenantExecutor22() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());

    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doThrow(new UnsupportedOperationException())
        .when(tenantInfoHolder)
        .setCurrentTenantId(Mockito.<String>any());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(processEngineConfiguration).getJobManager();
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
   *   <li>Given {@link DefaultAsyncJobExecutor} (default constructor) KeepAliveTime is one.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor_givenDefaultAsyncJobExecutorKeepAliveTimeIsOne() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setKeepAliveTime(1L);

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(defaultAsyncJobExecutor);

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
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <ul>
   *   <li>Given {@link DefaultAsyncJobExecutor} (default constructor) MessageQueueMode is {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor_givenDefaultAsyncJobExecutorMessageQueueModeIsTrue() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setMessageQueueMode(true);

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(defaultAsyncJobExecutor);

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
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <ul>
   *   <li>Given {@link DefaultAsyncJobExecutor} (default constructor) ThreadPoolQueue is {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor_givenDefaultAsyncJobExecutorThreadPoolQueueIsNull() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setThreadPoolQueue(null);

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(defaultAsyncJobExecutor);

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
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   *
   * <ul>
   *   <li>Then calls {@link
   *       AsyncExecutor#setProcessEngineConfiguration(ProcessEngineConfigurationImpl)}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor_thenCallsSetProcessEngineConfiguration() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    AsyncExecutor asyncExecutor = mock(AsyncExecutor.class);
    doNothing()
        .when(asyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    doNothing().when(asyncExecutor).shutdown();
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
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(asyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(asyncExecutor).shutdown();
    verify(asyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(tenantInfoHolder).addTenant("42");
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
    int actualAsyncJobLockTimeInMillis =
        executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(300000, actualAsyncJobLockTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}.
   *
   * <ul>
   *   <li>Then calls {@link TenantInfoHolder#setCurrentTenantId(String)}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis()"})
  public void testGetAsyncJobLockTimeInMillis_thenCallsSetCurrentTenantId() {
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
    int actualAsyncJobLockTimeInMillis =
        executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis();

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(300000, actualAsyncJobLockTimeInMillis);
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
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        mock(SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.getAsyncJobLockTimeInMillis()).thenReturn(1);
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
    int actualAsyncJobLockTimeInMillis =
        executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis();

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getAsyncJobLockTimeInMillis();
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
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
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
  public void testSetDefaultQueueSizeFullWaitTimeInMillis3() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setUserGroupManager(mock(UserGroupManager.class));

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
    processEngineConfiguration.setByteArrayDataManager(
        new MybatisByteArrayDataManager(new JtaProcessEngineConfiguration()));

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
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEnableVerboseExecutionTreeLogging(true);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(tenantInfoHolder);
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
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setManagementService(new ManagementServiceImpl());

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
    executorPerTenantAsyncExecutor.setMaxAsyncJobsDuePerAcquisition(3);

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
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
    processEngineConfiguration.setManagementService(new ManagementServiceImpl());

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
    executorPerTenantAsyncExecutor.setMaxAsyncJobsDuePerAcquisition(3);

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
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
    processEngineConfiguration.setManagementService(new ManagementServiceImpl());

    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setTimerJobAcquisitionThread(new Thread());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(defaultAsyncJobExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setMaxAsyncJobsDuePerAcquisition(3);

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
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
    processEngineConfiguration.setManagementService(new ManagementServiceImpl());

    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setExecuteAsyncRunnableFactory(mock(ExecuteAsyncRunnableFactory.class));

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(defaultAsyncJobExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setMaxAsyncJobsDuePerAcquisition(3);

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
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
  public void testSetMaxAsyncJobsDuePerAcquisition7() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomMybatisMappers(new HashSet<>());
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
    executorPerTenantAsyncExecutor.setMaxAsyncJobsDuePerAcquisition(3);

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
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
  public void testSetMaxAsyncJobsDuePerAcquisition8() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDynamicBpmnService(
        new DynamicBpmnServiceImpl(new JtaProcessEngineConfiguration()));
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
    executorPerTenantAsyncExecutor.setMaxAsyncJobsDuePerAcquisition(3);

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
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
  public void testSetMaxAsyncJobsDuePerAcquisition9() {
    // Arrange
    StandaloneInMemProcessEngineConfiguration processEngineConfiguration =
        new StandaloneInMemProcessEngineConfiguration();
    processEngineConfiguration.setManagementService(new ManagementServiceImpl());

    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setExecuteAsyncRunnableFactory(mock(ExecuteAsyncRunnableFactory.class));

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(defaultAsyncJobExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setMaxAsyncJobsDuePerAcquisition(3);

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
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
  public void testSetMaxAsyncJobsDuePerAcquisition10() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTaskService(
        new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setManagementService(new ManagementServiceImpl());

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
    executorPerTenantAsyncExecutor.setMaxAsyncJobsDuePerAcquisition(3);

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
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
  public void testSetMaxAsyncJobsDuePerAcquisition11() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomEventHandlers(new ArrayList<>());
    processEngineConfiguration.setManagementService(new ManagementServiceImpl());

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
    executorPerTenantAsyncExecutor.setMaxAsyncJobsDuePerAcquisition(3);

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
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
  public void testSetMaxAsyncJobsDuePerAcquisition12() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEnableEagerExecutionTreeFetching(true);
    processEngineConfiguration.setManagementService(new ManagementServiceImpl());

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
    executorPerTenantAsyncExecutor.setMaxAsyncJobsDuePerAcquisition(3);

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
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
  public void testSetMaxAsyncJobsDuePerAcquisition13() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setIdentityLinkDataManager(
        new MybatisIdentityLinkDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setManagementService(new ManagementServiceImpl());

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
    executorPerTenantAsyncExecutor.setMaxAsyncJobsDuePerAcquisition(3);

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
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
  public void testSetMaxAsyncJobsDuePerAcquisition14() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setRuntimeService(new RuntimeServiceImpl());
    processEngineConfiguration.setManagementService(new ManagementServiceImpl());

    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setTimerJobAcquisitionThread(new Thread());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(defaultAsyncJobExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setMaxAsyncJobsDuePerAcquisition(3);

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
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
  public void testSetMaxAsyncJobsDuePerAcquisition15() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTaskService(
        new TaskServiceImpl(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setManagementService(new ManagementServiceImpl());

    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setTimerJobAcquisitionThread(new Thread());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(defaultAsyncJobExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setMaxAsyncJobsDuePerAcquisition(3);

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
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
  public void testSetMaxAsyncJobsDuePerAcquisition16() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setIdentityLinkDataManager(
        new MybatisIdentityLinkDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setManagementService(new ManagementServiceImpl());

    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setTimerJobAcquisitionThread(new Thread());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(defaultAsyncJobExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setMaxAsyncJobsDuePerAcquisition(3);

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
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
  public void testSetMaxAsyncJobsDuePerAcquisition17() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    TaskEntityManagerImpl taskEntityManager =
        new TaskEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisTaskDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setTaskEntityManager(taskEntityManager);
    processEngineConfiguration.setManagementService(new ManagementServiceImpl());

    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setTimerJobAcquisitionThread(new Thread());

    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory =
        mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(defaultAsyncJobExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor =
        new ExecutorPerTenantAsyncExecutor(
            new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setMaxAsyncJobsDuePerAcquisition(3);

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor("42");
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
   * <ul>
   *   <li>Then calls {@link TenantInfoHolder#setCurrentTenantId(String)}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutorPerTenantAsyncExecutor#setResetExpiredJobsPageSize(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setResetExpiredJobsPageSize(int)"})
  public void testSetResetExpiredJobsPageSize_thenCallsSetCurrentTenantId() {
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
    executorPerTenantAsyncExecutor.setResetExpiredJobsPageSize(3);

    // Assert
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
  }
}
