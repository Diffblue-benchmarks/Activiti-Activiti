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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.impl.asyncexecutor.AcquireAsyncJobsDueRunnable;
import org.activiti.engine.impl.asyncexecutor.AcquireTimerJobsRunnable;
import org.activiti.engine.impl.asyncexecutor.AsyncExecutor;
import org.activiti.engine.impl.asyncexecutor.DefaultAsyncJobExecutor;
import org.activiti.engine.impl.asyncexecutor.DefaultJobManager;
import org.activiti.engine.impl.asyncexecutor.ExecuteAsyncRunnableFactory;
import org.activiti.engine.impl.asyncexecutor.ManagedAsyncJobExecutor;
import org.activiti.engine.impl.asyncexecutor.ResetExpiredJobsRunnable;
import org.activiti.engine.impl.bpmn.listener.ListenerNotificationHelper;
import org.activiti.engine.impl.cfg.BpmnParseFactory;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.TransactionContextFactory;
import org.activiti.engine.impl.cfg.TransactionPropagation;
import org.activiti.engine.impl.cfg.multitenant.MultiSchemaMultiTenantProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.multitenant.TenantInfoHolder;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextFactory;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.persistence.deploy.DefaultDeploymentCache;
import org.activiti.engine.impl.persistence.entity.AttachmentEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityImpl;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.ResourceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.SuspendedJobEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisAttachmentDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricActivityInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisResourceDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisSuspendedJobDataManager;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.test.cfg.multitenant.DummyTenantInfoHolder;
import org.activiti.engine.test.impl.logger.ProcessExecutionLoggerConfigurator;
import org.apache.ibatis.builder.CacheRefResolver;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class ExecutorPerTenantAsyncExecutorDiffblueTest {
  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@link DummyTenantInfoHolder} (default constructor).</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ExecutorPerTenantAsyncExecutor#ExecutorPerTenantAsyncExecutor(TenantInfoHolder)}
   *   <li>{@link ExecutorPerTenantAsyncExecutor#setAutoActivate(boolean)}
   *   <li>{@link ExecutorPerTenantAsyncExecutor#isActive()}
   *   <li>{@link ExecutorPerTenantAsyncExecutor#isAutoActivate()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.<init>(TenantInfoHolder)",
      "void ExecutorPerTenantAsyncExecutor.<init>(TenantInfoHolder, TenantAwareAsyncExecutorFactory)",
      "boolean ExecutorPerTenantAsyncExecutor.isActive()", "boolean ExecutorPerTenantAsyncExecutor.isAutoActivate()",
      "void ExecutorPerTenantAsyncExecutor.setAutoActivate(boolean)"})
  public void testGettersAndSetters_whenDummyTenantInfoHolder() {
    // Arrange and Act
    ExecutorPerTenantAsyncExecutor actualExecutorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
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
   * <ul>
   *   <li>When {@link TenantAwareAsyncExecutorFactory}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ExecutorPerTenantAsyncExecutor#ExecutorPerTenantAsyncExecutor(TenantInfoHolder, TenantAwareAsyncExecutorFactory)}
   *   <li>{@link ExecutorPerTenantAsyncExecutor#setAutoActivate(boolean)}
   *   <li>{@link ExecutorPerTenantAsyncExecutor#isActive()}
   *   <li>{@link ExecutorPerTenantAsyncExecutor#isAutoActivate()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.<init>(TenantInfoHolder)",
      "void ExecutorPerTenantAsyncExecutor.<init>(TenantInfoHolder, TenantAwareAsyncExecutorFactory)",
      "boolean ExecutorPerTenantAsyncExecutor.isActive()", "boolean ExecutorPerTenantAsyncExecutor.isAutoActivate()",
      "void ExecutorPerTenantAsyncExecutor.setAutoActivate(boolean)"})
  public void testGettersAndSetters_whenTenantAwareAsyncExecutorFactory() {
    // Arrange and Act
    ExecutorPerTenantAsyncExecutor actualExecutorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
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
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Set ExecutorPerTenantAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds() {
    // Arrange, Act and Assert
    assertTrue((new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder())).getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.addTenantAsyncExecutor(String, boolean)"})
  public void testAddTenantAsyncExecutor() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(((DefaultAsyncJobExecutor) getResult).getExecutorService() instanceof ThreadPoolExecutor);
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    ExecuteAsyncRunnableFactory executeAsyncRunnableFactory = ((DefaultAsyncJobExecutor) getResult)
        .getExecuteAsyncRunnableFactory();
    assertTrue(executeAsyncRunnableFactory instanceof TenantAwareExecuteAsyncRunnableFactory);
    assertTrue(executorPerTenantAsyncExecutor.processEngineConfiguration instanceof JtaProcessEngineConfiguration);
    ProcessEngineConfigurationImpl expectedProcessEngineConfiguration = executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertSame(expectedProcessEngineConfiguration, getResult.getProcessEngineConfiguration());
    assertSame(executorPerTenantAsyncExecutor.tenantInfoHolder,
        ((TenantAwareExecuteAsyncRunnableFactory) executeAsyncRunnableFactory).tenantInfoHolder);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.addTenantAsyncExecutor(String, boolean)"})
  public void testAddTenantAsyncExecutor2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    processEngineConfiguration
        .setCommandExecutor(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(((DefaultAsyncJobExecutor) getResult).getExecutorService() instanceof ThreadPoolExecutor);
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    ExecuteAsyncRunnableFactory executeAsyncRunnableFactory = ((DefaultAsyncJobExecutor) getResult)
        .getExecuteAsyncRunnableFactory();
    assertTrue(executeAsyncRunnableFactory instanceof TenantAwareExecuteAsyncRunnableFactory);
    assertTrue(executorPerTenantAsyncExecutor.processEngineConfiguration instanceof JtaProcessEngineConfiguration);
    ProcessEngineConfigurationImpl expectedProcessEngineConfiguration = executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertSame(expectedProcessEngineConfiguration, getResult.getProcessEngineConfiguration());
    assertSame(executorPerTenantAsyncExecutor.tenantInfoHolder,
        ((TenantAwareExecuteAsyncRunnableFactory) executeAsyncRunnableFactory).tenantInfoHolder);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.addTenantAsyncExecutor(String, boolean)"})
  public void testAddTenantAsyncExecutor3() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJavaClassFieldForJackson("Starting up the default async job executor [{}].");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(((DefaultAsyncJobExecutor) getResult).getExecutorService() instanceof ThreadPoolExecutor);
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    ExecuteAsyncRunnableFactory executeAsyncRunnableFactory = ((DefaultAsyncJobExecutor) getResult)
        .getExecuteAsyncRunnableFactory();
    assertTrue(executeAsyncRunnableFactory instanceof TenantAwareExecuteAsyncRunnableFactory);
    assertTrue(executorPerTenantAsyncExecutor.processEngineConfiguration instanceof JtaProcessEngineConfiguration);
    ProcessEngineConfigurationImpl expectedProcessEngineConfiguration = executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertSame(expectedProcessEngineConfiguration, getResult.getProcessEngineConfiguration());
    assertSame(executorPerTenantAsyncExecutor.tenantInfoHolder,
        ((TenantAwareExecuteAsyncRunnableFactory) executeAsyncRunnableFactory).tenantInfoHolder);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.addTenantAsyncExecutor(String, boolean)"})
  public void testAddTenantAsyncExecutor4() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setHistoricProcessInstancesQueryLimit(2);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(((DefaultAsyncJobExecutor) getResult).getExecutorService() instanceof ThreadPoolExecutor);
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    ExecuteAsyncRunnableFactory executeAsyncRunnableFactory = ((DefaultAsyncJobExecutor) getResult)
        .getExecuteAsyncRunnableFactory();
    assertTrue(executeAsyncRunnableFactory instanceof TenantAwareExecuteAsyncRunnableFactory);
    assertTrue(executorPerTenantAsyncExecutor.processEngineConfiguration instanceof JtaProcessEngineConfiguration);
    ProcessEngineConfigurationImpl expectedProcessEngineConfiguration = executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertSame(expectedProcessEngineConfiguration, getResult.getProcessEngineConfiguration());
    assertSame(executorPerTenantAsyncExecutor.tenantInfoHolder,
        ((TenantAwareExecuteAsyncRunnableFactory) executeAsyncRunnableFactory).tenantInfoHolder);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.addTenantAsyncExecutor(String, boolean)"})
  public void testAddTenantAsyncExecutor5() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTransactionContextFactory(mock(TransactionContextFactory.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(((DefaultAsyncJobExecutor) getResult).getExecutorService() instanceof ThreadPoolExecutor);
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    ExecuteAsyncRunnableFactory executeAsyncRunnableFactory = ((DefaultAsyncJobExecutor) getResult)
        .getExecuteAsyncRunnableFactory();
    assertTrue(executeAsyncRunnableFactory instanceof TenantAwareExecuteAsyncRunnableFactory);
    assertTrue(executorPerTenantAsyncExecutor.processEngineConfiguration instanceof JtaProcessEngineConfiguration);
    ProcessEngineConfigurationImpl expectedProcessEngineConfiguration = executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertSame(expectedProcessEngineConfiguration, getResult.getProcessEngineConfiguration());
    assertSame(executorPerTenantAsyncExecutor.tenantInfoHolder,
        ((TenantAwareExecuteAsyncRunnableFactory) executeAsyncRunnableFactory).tenantInfoHolder);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.addTenantAsyncExecutor(String, boolean)"})
  public void testAddTenantAsyncExecutor6() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setListenerNotificationHelper(new ListenerNotificationHelper());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(((DefaultAsyncJobExecutor) getResult).getExecutorService() instanceof ThreadPoolExecutor);
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    ExecuteAsyncRunnableFactory executeAsyncRunnableFactory = ((DefaultAsyncJobExecutor) getResult)
        .getExecuteAsyncRunnableFactory();
    assertTrue(executeAsyncRunnableFactory instanceof TenantAwareExecuteAsyncRunnableFactory);
    assertTrue(executorPerTenantAsyncExecutor.processEngineConfiguration instanceof JtaProcessEngineConfiguration);
    ProcessEngineConfigurationImpl expectedProcessEngineConfiguration = executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertSame(expectedProcessEngineConfiguration, getResult.getProcessEngineConfiguration());
    assertSame(executorPerTenantAsyncExecutor.tenantInfoHolder,
        ((TenantAwareExecuteAsyncRunnableFactory) executeAsyncRunnableFactory).tenantInfoHolder);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.addTenantAsyncExecutor(String, boolean)"})
  public void testAddTenantAsyncExecutor7() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTransactionFactory(new JdbcTransactionFactory());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(((DefaultAsyncJobExecutor) getResult).getExecutorService() instanceof ThreadPoolExecutor);
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    ExecuteAsyncRunnableFactory executeAsyncRunnableFactory = ((DefaultAsyncJobExecutor) getResult)
        .getExecuteAsyncRunnableFactory();
    assertTrue(executeAsyncRunnableFactory instanceof TenantAwareExecuteAsyncRunnableFactory);
    assertTrue(executorPerTenantAsyncExecutor.processEngineConfiguration instanceof JtaProcessEngineConfiguration);
    ProcessEngineConfigurationImpl expectedProcessEngineConfiguration = executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertSame(expectedProcessEngineConfiguration, getResult.getProcessEngineConfiguration());
    assertSame(executorPerTenantAsyncExecutor.tenantInfoHolder,
        ((TenantAwareExecuteAsyncRunnableFactory) executeAsyncRunnableFactory).tenantInfoHolder);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.addTenantAsyncExecutor(String, boolean)"})
  public void testAddTenantAsyncExecutor8() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setBpmnParseFactory(mock(BpmnParseFactory.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(((DefaultAsyncJobExecutor) getResult).getExecutorService() instanceof ThreadPoolExecutor);
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    ExecuteAsyncRunnableFactory executeAsyncRunnableFactory = ((DefaultAsyncJobExecutor) getResult)
        .getExecuteAsyncRunnableFactory();
    assertTrue(executeAsyncRunnableFactory instanceof TenantAwareExecuteAsyncRunnableFactory);
    assertTrue(executorPerTenantAsyncExecutor.processEngineConfiguration instanceof JtaProcessEngineConfiguration);
    ProcessEngineConfigurationImpl expectedProcessEngineConfiguration = executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertSame(expectedProcessEngineConfiguration, getResult.getProcessEngineConfiguration());
    assertSame(executorPerTenantAsyncExecutor.tenantInfoHolder,
        ((TenantAwareExecuteAsyncRunnableFactory) executeAsyncRunnableFactory).tenantInfoHolder);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.addTenantAsyncExecutor(String, boolean)"})
  public void testAddTenantAsyncExecutor9() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    processEngineConfiguration
        .setCommandExecutor(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(null);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(((DefaultAsyncJobExecutor) getResult).getExecutorService() instanceof ThreadPoolExecutor);
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    ExecuteAsyncRunnableFactory executeAsyncRunnableFactory = ((DefaultAsyncJobExecutor) getResult)
        .getExecuteAsyncRunnableFactory();
    assertTrue(executeAsyncRunnableFactory instanceof TenantAwareExecuteAsyncRunnableFactory);
    assertTrue(executorPerTenantAsyncExecutor.processEngineConfiguration instanceof JtaProcessEngineConfiguration);
    assertNull(((TenantAwareExecuteAsyncRunnableFactory) executeAsyncRunnableFactory).tenantInfoHolder);
    ProcessEngineConfigurationImpl expectedProcessEngineConfiguration = executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertSame(expectedProcessEngineConfiguration, getResult.getProcessEngineConfiguration());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.addTenantAsyncExecutor(String, boolean)"})
  public void testAddTenantAsyncExecutor10() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    processEngineConfiguration
        .setCommandExecutor(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any())).thenReturn(defaultAsyncJobExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    assertSame(defaultAsyncJobExecutor, stringAsyncExecutorMap.get("42"));
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.addTenantAsyncExecutor(String, boolean)"})
  public void testAddTenantAsyncExecutor11() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    processEngineConfiguration
        .setCommandExecutor(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(executorPerTenantAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor2 = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor2.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    executorPerTenantAsyncExecutor2.addTenantAsyncExecutor("42", true);

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor2.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    assertSame(executorPerTenantAsyncExecutor, stringAsyncExecutorMap.get("42"));
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.addTenantAsyncExecutor(String, boolean)"})
  public void testAddTenantAsyncExecutor12() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    processEngineConfiguration
        .setCommandExecutor(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    assertSame(sharedExecutorServiceAsyncExecutor, stringAsyncExecutorMap.get("42"));
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.
   * <ul>
   *   <li>Then calls {@link SharedExecutorServiceAsyncExecutor#start()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.addTenantAsyncExecutor(String, boolean)"})
  public void testAddTenantAsyncExecutor_thenCallsStart() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    processEngineConfiguration
        .setCommandExecutor(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = mock(
        SharedExecutorServiceAsyncExecutor.class);
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor).setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor).setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    ProcessEngineConfigurationImpl processEngineConfigurationImpl = executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    Set<String> tenantIds = executorPerTenantAsyncExecutor.getTenantIds();
    assertEquals(1, tenantIds.size());
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(tenantIds.contains("42"));
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.
   * <ul>
   *   <li>When {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#addTenantAsyncExecutor(String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.addTenantAsyncExecutor(String, boolean)"})
  public void testAddTenantAsyncExecutor_whenFalse() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    processEngineConfiguration
        .setCommandExecutor(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = mock(
        SharedExecutorServiceAsyncExecutor.class);
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", false);

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor).setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor).setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    ProcessEngineConfigurationImpl processEngineConfigurationImpl = executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    Set<String> tenantIds = executorPerTenantAsyncExecutor.getTenantIds();
    assertEquals(1, tenantIds.size());
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(tenantIds.contains("42"));
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    ProcessEngineConfigurationImpl processEngineConfigurationImpl = executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(executorPerTenantAsyncExecutor.tenantInfoHolder instanceof DummyTenantInfoHolder);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor2() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(null);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    ProcessEngineConfigurationImpl processEngineConfigurationImpl = executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof JtaProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor3() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new StandaloneProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    ProcessEngineConfigurationImpl processEngineConfigurationImpl = executorPerTenantAsyncExecutor.processEngineConfiguration;
    assertTrue(processEngineConfigurationImpl instanceof StandaloneProcessEngineConfiguration);
    assertTrue(processEngineConfigurationImpl.getWsOverridenEndpointAddresses().isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor4() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = new CommandConfig();
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
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
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor5() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = new CommandConfig();
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(tenantInfoHolder,
        tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    TenantInfoHolder tenantInfoHolder2 = executorPerTenantAsyncExecutor.tenantInfoHolder;
    Collection<String> allTenants = tenantInfoHolder2.getAllTenants();
    assertEquals(1, allTenants.size());
    assertTrue(allTenants instanceof Set);
    assertTrue(tenantInfoHolder2 instanceof DummyTenantInfoHolder);
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor6() {
    // Arrange
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        mock(TenantInfoHolder.class), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(mock(JtaProcessEngineConfiguration.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor7() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        mock(TenantInfoHolder.class), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(processEngineConfiguration).getJobManager();
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor8() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = new CommandConfig();
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
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
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor9() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = new CommandConfig();
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    TenantInfoHolder tenantInfoHolder2 = executorPerTenantAsyncExecutor.tenantInfoHolder;
    Collection<String> allTenants = tenantInfoHolder2.getAllTenants();
    assertEquals(1, allTenants.size());
    assertTrue(allTenants instanceof Set);
    assertTrue(tenantInfoHolder2 instanceof DummyTenantInfoHolder);
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor10() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = new CommandConfig();
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("exception during timer job acquisition: {}");
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    TenantInfoHolder tenantInfoHolder2 = executorPerTenantAsyncExecutor.tenantInfoHolder;
    Collection<String> allTenants = tenantInfoHolder2.getAllTenants();
    assertEquals(2, allTenants.size());
    assertTrue(allTenants instanceof Set);
    assertTrue(tenantInfoHolder2 instanceof DummyTenantInfoHolder);
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor11() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doThrow(new UnsupportedOperationException("{} starting to reset expired jobs")).when(tenantInfoHolder)
        .setCurrentTenantId(Mockito.<String>any());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor12() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        mock(TenantInfoHolder.class));
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", false);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(processEngineConfiguration).getJobManager();
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}.
   * <ul>
   *   <li>Given {@link UnsupportedOperationException#UnsupportedOperationException(String)} with {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor_givenUnsupportedOperationExceptionWithFoo() {
    // Arrange
    new UnsupportedOperationException("foo");
    new UnsupportedOperationException("foo");
    new UnsupportedOperationException("foo");
    new UnsupportedOperationException("foo");
    new UnsupportedOperationException("foo");
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = new CommandConfig();
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
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
   * <ul>
   *   <li>Then calls {@link DefaultAsyncJobExecutor#getProcessEngineConfiguration()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor_thenCallsGetProcessEngineConfiguration() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = mock(
        SharedExecutorServiceAsyncExecutor.class);
    doNothing().when(sharedExecutorServiceAsyncExecutor).shutdown();
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        mock(TenantInfoHolder.class), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(mock(JtaProcessEngineConfiguration.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor).setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor).setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).shutdown();
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    assertTrue(executorPerTenantAsyncExecutor.tenantExecutors.isEmpty());
    assertTrue(executorPerTenantAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor() {
    // Arrange, Act and Assert
    assertNull((new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder())).determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor2() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor3() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new StandaloneProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor4() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCommandInterceptors(new ArrayList<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor5() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCommandContextFactory(new CommandContextFactory());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor6() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setSqlSessionFactory(new DefaultSqlSessionFactory(new Configuration()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor7() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setProcessDefinitionCache(new DefaultDeploymentCache<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor8() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setSuspendedJobEntityManager(new SuspendedJobEntityManagerImpl(
        processEngineConfiguration2, new MybatisSuspendedJobDataManager(new JtaProcessEngineConfiguration())));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor9() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJobManager(new DefaultJobManager());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor10() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setMailServerUseTLS(true);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor11() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJdbcMaxActiveConnections(1);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor12() {
    // Arrange
    Configuration configuration = new Configuration();
    configuration.addIncompleteCacheRef(
        new CacheRefResolver(new MapperBuilderAssistant(new Configuration(), "Resource"), "Cache Ref Namespace"));
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setSqlSessionFactory(sqlSessionFactory);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor13() {
    // Arrange
    Configuration configuration = new Configuration();
    configuration.addKeyGenerator("activiti-reset-expired-jobs", new Jdbc3KeyGenerator());
    configuration.addIncompleteCacheRef(
        new CacheRefResolver(new MapperBuilderAssistant(new Configuration(), "Resource"), "Cache Ref Namespace"));
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setSqlSessionFactory(sqlSessionFactory);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor14() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setSqlSessionFactory(null);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor15() {
    // Arrange
    StandaloneProcessEngineConfiguration processEngineConfiguration = new StandaloneProcessEngineConfiguration();
    processEngineConfiguration.setCustomMybatisMappers(new HashSet<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor16() {
    // Arrange
    StandaloneProcessEngineConfiguration processEngineConfiguration = new StandaloneProcessEngineConfiguration();
    processEngineConfiguration.setEnableEagerExecutionTreeFetching(true);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <ul>
   *   <li>Given {@link Configuration#Configuration()} LogPrefix is {@code activiti-reset-expired-jobs}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor_givenConfigurationLogPrefixIsActivitiResetExpiredJobs() {
    // Arrange
    Configuration configuration = new Configuration();
    configuration.setLogPrefix("activiti-reset-expired-jobs");
    configuration.addIncompleteCacheRef(
        new CacheRefResolver(new MapperBuilderAssistant(new Configuration(), "Resource"), "Cache Ref Namespace"));
    DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setSqlSessionFactory(sqlSessionFactory);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <ul>
   *   <li>Given {@link DummyTenantInfoHolder} (default constructor) addTenant empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor_givenDummyTenantInfoHolderAddTenantEmptyString() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("");

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setMailServerUseTLS(true);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) JobManager is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#determineAsyncExecutor()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"AsyncExecutor ExecutorPerTenantAsyncExecutor.determineAsyncExecutor()"})
  public void testDetermineAsyncExecutor_givenJtaProcessEngineConfigurationJobManagerIsNull() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJobManager(null);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertNull(executorPerTenantAsyncExecutor.determineAsyncExecutor());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#executeAsyncJob(Job)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#executeAsyncJob(Job)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ExecutorPerTenantAsyncExecutor.executeAsyncJob(Job)"})
  public void testExecuteAsyncJob() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = mock(
        SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.executeAsyncJob(Mockito.<Job>any())).thenReturn(true);
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(tenantInfoHolder,
        tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(mock(ProcessEngineConfigurationImpl.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    boolean actualExecuteAsyncJobResult = executorPerTenantAsyncExecutor.executeAsyncJob(new DeadLetterJobEntityImpl());

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).executeAsyncJob(isA(Job.class));
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor).setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor).setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(tenantInfoHolder).getCurrentTenantId();
    assertTrue(actualExecuteAsyncJobResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#executeAsyncJob(Job)}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#executeAsyncJob(Job)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean ExecutorPerTenantAsyncExecutor.executeAsyncJob(Job)"})
  public void testExecuteAsyncJob_thenReturnFalse() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = mock(
        SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.executeAsyncJob(Mockito.<Job>any())).thenReturn(false);
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(tenantInfoHolder,
        tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(mock(ProcessEngineConfigurationImpl.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    boolean actualExecuteAsyncJobResult = executorPerTenantAsyncExecutor.executeAsyncJob(new DeadLetterJobEntityImpl());

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).executeAsyncJob(isA(Job.class));
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor).setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor).setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(tenantInfoHolder).getCurrentTenantId();
    assertFalse(actualExecuteAsyncJobResult);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getJobManager()}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#getJobManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.asyncexecutor.JobManager ExecutorPerTenantAsyncExecutor.getJobManager()"})
  public void testGetJobManager() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> (new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder())).getJobManager());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getJobManager()}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#getJobManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.asyncexecutor.JobManager ExecutorPerTenantAsyncExecutor.getJobManager()"})
  public void testGetJobManager2() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> executorPerTenantAsyncExecutor.getJobManager());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getJobManager()}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#getJobManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.asyncexecutor.JobManager ExecutorPerTenantAsyncExecutor.getJobManager()"})
  public void testGetJobManager3() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> executorPerTenantAsyncExecutor.getJobManager());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getJobManager()}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#getJobManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.asyncexecutor.JobManager ExecutorPerTenantAsyncExecutor.getJobManager()"})
  public void testGetJobManager4() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfiguration = new MultiSchemaMultiTenantProcessEngineConfiguration(
        new DummyTenantInfoHolder());
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> executorPerTenantAsyncExecutor.getJobManager());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getJobManager()}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#getJobManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.asyncexecutor.JobManager ExecutorPerTenantAsyncExecutor.getJobManager()"})
  public void testGetJobManager5() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> executorPerTenantAsyncExecutor.getJobManager());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getJobManager()}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#getJobManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.asyncexecutor.JobManager ExecutorPerTenantAsyncExecutor.getJobManager()"})
  public void testGetJobManager6() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomPreCommandInterceptors(new ArrayList<>());
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> executorPerTenantAsyncExecutor.getJobManager());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getJobManager()}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#getJobManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.asyncexecutor.JobManager ExecutorPerTenantAsyncExecutor.getJobManager()"})
  public void testGetJobManager7() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setHistoricActivityInstanceEntityManager(
        new HistoricActivityInstanceEntityManagerImpl(processEngineConfiguration2,
            new MybatisHistoricActivityInstanceDataManager(new JtaProcessEngineConfiguration())));
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> executorPerTenantAsyncExecutor.getJobManager());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getJobManager()}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#getJobManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.asyncexecutor.JobManager ExecutorPerTenantAsyncExecutor.getJobManager()"})
  public void testGetJobManager8() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setResourceEntityManager(new ResourceEntityManagerImpl(processEngineConfiguration2,
        new MybatisResourceDataManager(new JtaProcessEngineConfiguration())));
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> executorPerTenantAsyncExecutor.getJobManager());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getJobManager()}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#getJobManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.asyncexecutor.JobManager ExecutorPerTenantAsyncExecutor.getJobManager()"})
  public void testGetJobManager9() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDatabaseCatalog("Database Catalog");
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> executorPerTenantAsyncExecutor.getJobManager());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getJobManager()}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#getJobManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.asyncexecutor.JobManager ExecutorPerTenantAsyncExecutor.getJobManager()"})
  public void testGetJobManager10() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAttachmentEntityManager(new AttachmentEntityManagerImpl(processEngineConfiguration2,
        new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration())));
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> executorPerTenantAsyncExecutor.getJobManager());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getJobManager()}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#getJobManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.asyncexecutor.JobManager ExecutorPerTenantAsyncExecutor.getJobManager()"})
  public void testGetJobManager11() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCommandInterceptors(new ArrayList<>());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> executorPerTenantAsyncExecutor.getJobManager());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getJobManager()}.
   * <ul>
   *   <li>Given {@link DummyTenantInfoHolder} (default constructor) addTenant {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#getJobManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.asyncexecutor.JobManager ExecutorPerTenantAsyncExecutor.getJobManager()"})
  public void testGetJobManager_givenDummyTenantInfoHolderAddTenant42() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> executorPerTenantAsyncExecutor.getJobManager());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getJobManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) AsyncExecutorActivate is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#getJobManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.asyncexecutor.JobManager ExecutorPerTenantAsyncExecutor.getJobManager()"})
  public void testGetJobManager_givenJtaProcessEngineConfigurationAsyncExecutorActivateIsTrue() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAsyncExecutorActivate(true);
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> executorPerTenantAsyncExecutor.getJobManager());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getJobManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) BulkInsertEnabled is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#getJobManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.asyncexecutor.JobManager ExecutorPerTenantAsyncExecutor.getJobManager()"})
  public void testGetJobManager_givenJtaProcessEngineConfigurationBulkInsertEnabledIsTrue() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setBulkInsertEnabled(true);
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> executorPerTenantAsyncExecutor.getJobManager());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getJobManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) Deployers is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#getJobManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.asyncexecutor.JobManager ExecutorPerTenantAsyncExecutor.getJobManager()"})
  public void testGetJobManager_givenJtaProcessEngineConfigurationDeployersIsArrayList() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDeployers(new ArrayList<>());
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> executorPerTenantAsyncExecutor.getJobManager());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getJobManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) EventHandlers is {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#getJobManager()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.impl.asyncexecutor.JobManager ExecutorPerTenantAsyncExecutor.getJobManager()"})
  public void testGetJobManager_givenJtaProcessEngineConfigurationEventHandlersIsHashMap() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventHandlers(new HashMap<>());
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act and Assert
    assertThrows(UnsupportedOperationException.class, () -> executorPerTenantAsyncExecutor.getJobManager());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setProcessEngineConfiguration(ProcessEngineConfigurationImpl)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#setProcessEngineConfiguration(ProcessEngineConfigurationImpl)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void ExecutorPerTenantAsyncExecutor.setProcessEngineConfiguration(ProcessEngineConfigurationImpl)"})
  public void testSetProcessEngineConfiguration() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());

    // Act
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.processEngineConfiguration instanceof JtaProcessEngineConfiguration);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getProcessEngineConfiguration()}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#getProcessEngineConfiguration()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"ProcessEngineConfigurationImpl ExecutorPerTenantAsyncExecutor.getProcessEngineConfiguration()"})
  public void testGetProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertThrows(UnsupportedOperationException.class,
        () -> (new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder())).getProcessEngineConfiguration());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#start()}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#start()}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#start()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.start()"})
  public void testStart2() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.start();

    // Assert
    assertTrue(executorPerTenantAsyncExecutor.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdown()}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
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
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown2() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(null);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
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
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdown()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdown()"})
  public void testShutdown3() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(null);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", false);

    // Act
    executorPerTenantAsyncExecutor.shutdown();

    // Assert that nothing has changed
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
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
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor2() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(null);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
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
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor3() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
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
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor4() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new StandaloneProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
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
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor5() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = new CommandConfig();
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
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
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor6() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = new CommandConfig();
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder(), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
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
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor7() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = new CommandConfig();
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new DefaultAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(tenantInfoHolder,
        tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
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
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor8() {
    // Arrange
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        mock(TenantInfoHolder.class), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(mock(ProcessEngineConfigurationImpl.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof ExecutorPerTenantAsyncExecutor);
    assertFalse(getResult.isActive());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor9() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        mock(TenantInfoHolder.class), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(processEngineConfiguration).getJobManager();
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
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
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor10() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = new CommandConfig();
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new ManagedAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(tenantInfoHolder,
        tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
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
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor11() {
    // Arrange
    new UnsupportedOperationException("foo");
    new UnsupportedOperationException("foo");
    new UnsupportedOperationException("foo");
    new UnsupportedOperationException("foo");
    new UnsupportedOperationException("foo");
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = new CommandConfig();
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
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
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor12() {
    // Arrange
    new UnsupportedOperationException("foo");
    new UnsupportedOperationException("foo");
    new UnsupportedOperationException("foo");
    new UnsupportedOperationException("foo");
    new UnsupportedOperationException("foo");
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = new CommandConfig(true, TransactionPropagation.REQUIRED);

    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
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
   * <ul>
   *   <li>Given {@link DummyTenantInfoHolder} (default constructor) addTenant {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor_givenDummyTenantInfoHolderAddTenant42() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
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
   * <ul>
   *   <li>Given {@link DummyTenantInfoHolder} (default constructor) addTenant {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor_givenDummyTenantInfoHolderAddTenant422() {
    // Arrange
    new UnsupportedOperationException("foo");
    new UnsupportedOperationException("foo");
    new UnsupportedOperationException("foo");
    new UnsupportedOperationException("foo");
    new UnsupportedOperationException("foo");
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = new CommandConfig();
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    DummyTenantInfoHolder tenantInfoHolder = new DummyTenantInfoHolder();
    tenantInfoHolder.addTenant("42");

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
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
   * <ul>
   *   <li>Then calls {@link DefaultAsyncJobExecutor#getProcessEngineConfiguration()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#shutdownTenantExecutor(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.shutdownTenantExecutor(String)"})
  public void testShutdownTenantExecutor_thenCallsGetProcessEngineConfiguration() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = mock(
        SharedExecutorServiceAsyncExecutor.class);
    doNothing().when(sharedExecutorServiceAsyncExecutor).shutdown();
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        mock(TenantInfoHolder.class), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(mock(ProcessEngineConfigurationImpl.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.shutdownTenantExecutor("42");

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor).setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor).setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).shutdown();
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setTimerLockTimeInMillis(int)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#setTimerLockTimeInMillis(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setTimerLockTimeInMillis(int)"})
  public void testSetTimerLockTimeInMillis() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setTimerLockTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getTimerLockTimeInMillis());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis()"})
  public void testGetAsyncJobLockTimeInMillis() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualAsyncJobLockTimeInMillis = executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis();

    // Assert
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    assertEquals(300000, actualAsyncJobLockTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis()"})
  public void testGetAsyncJobLockTimeInMillis2() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new StandaloneProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualAsyncJobLockTimeInMillis = executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis();

    // Assert
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    assertEquals(300000, actualAsyncJobLockTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis()"})
  public void testGetAsyncJobLockTimeInMillis3() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualAsyncJobLockTimeInMillis = executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis();

    // Assert
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    assertEquals(300000, actualAsyncJobLockTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}.
   * <ul>
   *   <li>Then return one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#getAsyncJobLockTimeInMillis()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis()"})
  public void testGetAsyncJobLockTimeInMillis_thenReturnOne() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = mock(
        SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.getAsyncJobLockTimeInMillis()).thenReturn(1);
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(tenantInfoHolder,
        tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(mock(ProcessEngineConfigurationImpl.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualAsyncJobLockTimeInMillis = executorPerTenantAsyncExecutor.getAsyncJobLockTimeInMillis();

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getAsyncJobLockTimeInMillis();
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor).setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor).setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals(1, actualAsyncJobLockTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setAsyncJobLockTimeInMillis(int)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#setAsyncJobLockTimeInMillis(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setAsyncJobLockTimeInMillis(int)"})
  public void testSetAsyncJobLockTimeInMillis() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setAsyncJobLockTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getAsyncJobLockTimeInMillis());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis()"})
  public void testGetDefaultTimerJobAcquireWaitTimeInMillis() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualDefaultTimerJobAcquireWaitTimeInMillis = executorPerTenantAsyncExecutor
        .getDefaultTimerJobAcquireWaitTimeInMillis();

    // Assert
    verify(tenantInfoHolder).getCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    assertEquals(10000, actualDefaultTimerJobAcquireWaitTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}.
   * <ul>
   *   <li>Then return one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis()"})
  public void testGetDefaultTimerJobAcquireWaitTimeInMillis_thenReturnOne() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = mock(
        SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis()).thenReturn(1);
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(tenantInfoHolder,
        tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(mock(ProcessEngineConfigurationImpl.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualDefaultTimerJobAcquireWaitTimeInMillis = executorPerTenantAsyncExecutor
        .getDefaultTimerJobAcquireWaitTimeInMillis();

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getDefaultTimerJobAcquireWaitTimeInMillis();
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor).setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor).setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals(1, actualDefaultTimerJobAcquireWaitTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setDefaultTimerJobAcquireWaitTimeInMillis(int)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#setDefaultTimerJobAcquireWaitTimeInMillis(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setDefaultTimerJobAcquireWaitTimeInMillis(int)"})
  public void testSetDefaultTimerJobAcquireWaitTimeInMillis() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setDefaultTimerJobAcquireWaitTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getDefaultTimerJobAcquireWaitTimeInMillis());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getDefaultAsyncJobAcquireWaitTimeInMillis()}.
   * <ul>
   *   <li>Then return one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#getDefaultAsyncJobAcquireWaitTimeInMillis()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis()"})
  public void testGetDefaultAsyncJobAcquireWaitTimeInMillis_thenReturnOne() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = mock(
        SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis()).thenReturn(1);
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(tenantInfoHolder,
        tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualDefaultAsyncJobAcquireWaitTimeInMillis = executorPerTenantAsyncExecutor
        .getDefaultAsyncJobAcquireWaitTimeInMillis();

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getDefaultAsyncJobAcquireWaitTimeInMillis();
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor).setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor).setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals(1, actualDefaultAsyncJobAcquireWaitTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setDefaultAsyncJobAcquireWaitTimeInMillis(int)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#setDefaultAsyncJobAcquireWaitTimeInMillis(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setDefaultAsyncJobAcquireWaitTimeInMillis(int)"})
  public void testSetDefaultAsyncJobAcquireWaitTimeInMillis() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setDefaultAsyncJobAcquireWaitTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getDefaultAsyncJobAcquireWaitTimeInMillis());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#getDefaultQueueSizeFullWaitTimeInMillis()}.
   * <ul>
   *   <li>Then return three.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#getDefaultQueueSizeFullWaitTimeInMillis()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int ExecutorPerTenantAsyncExecutor.getDefaultQueueSizeFullWaitTimeInMillis()"})
  public void testGetDefaultQueueSizeFullWaitTimeInMillis_thenReturnThree() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    when(tenantInfoHolder.getCurrentTenantId()).thenReturn("42");
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = mock(
        SharedExecutorServiceAsyncExecutor.class);
    when(sharedExecutorServiceAsyncExecutor.getDefaultQueueSizeFullWaitTimeInMillis()).thenReturn(3);
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setExecuteAsyncRunnableFactory(Mockito.<ExecuteAsyncRunnableFactory>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setResetExpiredJobsRunnable(Mockito.<ResetExpiredJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(Mockito.<AcquireTimerJobsRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor).start();
    when(sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration())
        .thenReturn(new JtaProcessEngineConfiguration());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setAsyncJobsDueRunnable(Mockito.<AcquireAsyncJobsDueRunnable>any());
    doNothing().when(sharedExecutorServiceAsyncExecutor)
        .setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(sharedExecutorServiceAsyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(tenantInfoHolder,
        tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    int actualDefaultQueueSizeFullWaitTimeInMillis = executorPerTenantAsyncExecutor
        .getDefaultQueueSizeFullWaitTimeInMillis();

    // Assert
    verify(sharedExecutorServiceAsyncExecutor).getDefaultQueueSizeFullWaitTimeInMillis();
    verify(sharedExecutorServiceAsyncExecutor).getProcessEngineConfiguration();
    verify(sharedExecutorServiceAsyncExecutor).setAsyncJobsDueRunnable(isA(AcquireAsyncJobsDueRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setExecuteAsyncRunnableFactory(isA(ExecuteAsyncRunnableFactory.class));
    verify(sharedExecutorServiceAsyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(sharedExecutorServiceAsyncExecutor).setResetExpiredJobsRunnable(isA(ResetExpiredJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).setTimerJobRunnable(isA(AcquireTimerJobsRunnable.class));
    verify(sharedExecutorServiceAsyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(tenantInfoHolder).getCurrentTenantId();
    assertEquals(3, actualDefaultQueueSizeFullWaitTimeInMillis);
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setDefaultQueueSizeFullWaitTimeInMillis(int)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#setDefaultQueueSizeFullWaitTimeInMillis(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setDefaultQueueSizeFullWaitTimeInMillis(int)"})
  public void testSetDefaultQueueSizeFullWaitTimeInMillis() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setDefaultQueueSizeFullWaitTimeInMillis(3);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(3, getResult.getDefaultQueueSizeFullWaitTimeInMillis());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setDefaultQueueSizeFullWaitTimeInMillis(int)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#setDefaultQueueSizeFullWaitTimeInMillis(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setDefaultQueueSizeFullWaitTimeInMillis(int)"})
  public void testSetDefaultQueueSizeFullWaitTimeInMillis2() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfiguration = new MultiSchemaMultiTenantProcessEngineConfiguration(
        new DummyTenantInfoHolder());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setDefaultQueueSizeFullWaitTimeInMillis(3);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(3, getResult.getDefaultQueueSizeFullWaitTimeInMillis());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setMaxAsyncJobsDuePerAcquisition(int)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#setMaxAsyncJobsDuePerAcquisition(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setMaxAsyncJobsDuePerAcquisition(int)"})
  public void testSetMaxAsyncJobsDuePerAcquisition() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setMaxAsyncJobsDuePerAcquisition(3);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(3, getResult.getMaxAsyncJobsDuePerAcquisition());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setMaxTimerJobsPerAcquisition(int)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#setMaxTimerJobsPerAcquisition(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setMaxTimerJobsPerAcquisition(int)"})
  public void testSetMaxTimerJobsPerAcquisition() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setMaxTimerJobsPerAcquisition(3);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(3, getResult.getMaxTimerJobsPerAcquisition());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setRetryWaitTimeInMillis(int)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#setRetryWaitTimeInMillis(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setRetryWaitTimeInMillis(int)"})
  public void testSetRetryWaitTimeInMillis() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setRetryWaitTimeInMillis(1);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(1, getResult.getRetryWaitTimeInMillis());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setResetExpiredJobsInterval(int)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#setResetExpiredJobsInterval(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setResetExpiredJobsInterval(int)"})
  public void testSetResetExpiredJobsInterval() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setResetExpiredJobsInterval(42);

    // Assert
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(42, getResult.getResetExpiredJobsInterval());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setResetExpiredJobsPageSize(int)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#setResetExpiredJobsPageSize(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setResetExpiredJobsPageSize(int)"})
  public void testSetResetExpiredJobsPageSize() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setResetExpiredJobsPageSize(3);

    // Assert that nothing has changed
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(3, getResult.getResetExpiredJobsPageSize());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setResetExpiredJobsPageSize(int)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#setResetExpiredJobsPageSize(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setResetExpiredJobsPageSize(int)"})
  public void testSetResetExpiredJobsPageSize2() {
    // Arrange
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder()));

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        mock(TenantInfoHolder.class), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(mock(ProcessEngineConfigurationImpl.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setResetExpiredJobsPageSize(3);

    // Assert
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setResetExpiredJobsPageSize(int)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#setResetExpiredJobsPageSize(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setResetExpiredJobsPageSize(int)"})
  public void testSetResetExpiredJobsPageSize3() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any()))
        .thenReturn(new ManagedAsyncJobExecutor());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(tenantInfoHolder,
        tenantAwareAyncExecutorFactory);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = mock(CommandConfig.class);
    when(defaultConfig.isContextReusePossible()).thenThrow(new UnsupportedOperationException("foo"));
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setResetExpiredJobsPageSize(3);

    // Assert that nothing has changed
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    verify(defaultConfig, atLeast(1)).isContextReusePossible();
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof ManagedAsyncJobExecutor);
    assertEquals(3, getResult.getResetExpiredJobsPageSize());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setResetExpiredJobsPageSize(int)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#setResetExpiredJobsPageSize(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setResetExpiredJobsPageSize(int)"})
  public void testSetResetExpiredJobsPageSize4() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = mock(CommandConfig.class);
    when(defaultConfig.isContextReusePossible()).thenThrow(new UnsupportedOperationException("foo"));
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setResetExpiredJobsPageSize(3);

    // Assert that nothing has changed
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(defaultConfig, atLeast(1)).isContextReusePossible();
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(3, getResult.getResetExpiredJobsPageSize());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setResetExpiredJobsPageSize(int)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#setResetExpiredJobsPageSize(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setResetExpiredJobsPageSize(int)"})
  public void testSetResetExpiredJobsPageSize5() {
    // Arrange
    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        new DummyTenantInfoHolder());
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = mock(CommandConfig.class);
    when(defaultConfig.isContextReusePossible()).thenThrow(new UnsupportedOperationException(""));
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setResetExpiredJobsPageSize(3);

    // Assert that nothing has changed
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(defaultConfig, atLeast(1)).isContextReusePossible();
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(3, getResult.getResetExpiredJobsPageSize());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setResetExpiredJobsPageSize(int)}.
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#setResetExpiredJobsPageSize(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setResetExpiredJobsPageSize(int)"})
  public void testSetResetExpiredJobsPageSize6() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doThrow(new UnsupportedOperationException("")).when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        tenantInfoHolder);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);
    new UnsupportedOperationException("foo");
    new UnsupportedOperationException("foo");

    // Act
    executorPerTenantAsyncExecutor.setResetExpiredJobsPageSize(3);

    // Assert that nothing has changed
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    Map<String, AsyncExecutor> stringAsyncExecutorMap = executorPerTenantAsyncExecutor.tenantExecutors;
    assertEquals(1, stringAsyncExecutorMap.size());
    AsyncExecutor getResult = stringAsyncExecutorMap.get("42");
    assertTrue(getResult instanceof DefaultAsyncJobExecutor);
    assertEquals(3, getResult.getResetExpiredJobsPageSize());
  }

  /**
   * Test {@link ExecutorPerTenantAsyncExecutor#setResetExpiredJobsPageSize(int)}.
   * <ul>
   *   <li>Then calls {@link AsyncExecutor#setProcessEngineConfiguration(ProcessEngineConfigurationImpl)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExecutorPerTenantAsyncExecutor#setResetExpiredJobsPageSize(int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ExecutorPerTenantAsyncExecutor.setResetExpiredJobsPageSize(int)"})
  public void testSetResetExpiredJobsPageSize_thenCallsSetProcessEngineConfiguration() {
    // Arrange
    AsyncExecutor asyncExecutor = mock(AsyncExecutor.class);
    doNothing().when(asyncExecutor).setProcessEngineConfiguration(Mockito.<ProcessEngineConfigurationImpl>any());
    doNothing().when(asyncExecutor).setResetExpiredJobsPageSize(anyInt());
    doNothing().when(asyncExecutor).start();
    TenantAwareAsyncExecutorFactory tenantAwareAyncExecutorFactory = mock(TenantAwareAsyncExecutorFactory.class);
    when(tenantAwareAyncExecutorFactory.createAsyncExecutor(Mockito.<String>any())).thenReturn(asyncExecutor);

    ExecutorPerTenantAsyncExecutor executorPerTenantAsyncExecutor = new ExecutorPerTenantAsyncExecutor(
        mock(TenantInfoHolder.class), tenantAwareAyncExecutorFactory);
    executorPerTenantAsyncExecutor.setProcessEngineConfiguration(mock(ProcessEngineConfigurationImpl.class));
    executorPerTenantAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    executorPerTenantAsyncExecutor.setResetExpiredJobsPageSize(3);

    // Assert
    verify(asyncExecutor).setProcessEngineConfiguration(isA(ProcessEngineConfigurationImpl.class));
    verify(asyncExecutor).setResetExpiredJobsPageSize(eq(3));
    verify(asyncExecutor).start();
    verify(tenantAwareAyncExecutorFactory).createAsyncExecutor(eq("42"));
  }
}
