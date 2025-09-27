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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import javax.xml.namespace.QName;
import org.activiti.api.runtime.shared.identity.UserGroupManager;
import org.activiti.engine.ProcessEngineLifecycleListener;
import org.activiti.engine.impl.asyncexecutor.AcquireAsyncJobsDueRunnable;
import org.activiti.engine.impl.asyncexecutor.DefaultAsyncJobExecutor;
import org.activiti.engine.impl.asyncexecutor.DefaultJobManager;
import org.activiti.engine.impl.asyncexecutor.ExecuteAsyncRunnableFactory;
import org.activiti.engine.impl.asyncexecutor.ResetExpiredJobsRunnable;
import org.activiti.engine.impl.bpmn.deployer.BpmnDeploymentHelper;
import org.activiti.engine.impl.bpmn.deployer.ParsedDeploymentBuilderFactory;
import org.activiti.engine.impl.bpmn.listener.ListenerNotificationHelper;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.multitenant.MultiSchemaMultiTenantProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.multitenant.TenantInfoHolder;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextFactory;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.impl.interceptor.DebugCommandInvoker;
import org.activiti.engine.impl.interceptor.LogInterceptor;
import org.activiti.engine.impl.jobexecutor.FailedJobCommandFactory;
import org.activiti.engine.impl.persistence.deploy.DeploymentManager;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.TimerJobEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisByteArrayDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisDeploymentDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisExecutionDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisModelDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisProcessDefinitionInfoDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisTimerJobDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisVariableInstanceDataManager;
import org.activiti.engine.test.cfg.multitenant.DummyTenantInfoHolder;
import org.activiti.engine.test.impl.logger.ProcessExecutionLoggerConfigurator;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class SharedExecutorServiceAsyncExecutorDiffblueTest {
  /**
   * Test {@link
   * SharedExecutorServiceAsyncExecutor#SharedExecutorServiceAsyncExecutor(TenantInfoHolder)}.
   *
   * <p>Method under test: {@link
   * SharedExecutorServiceAsyncExecutor#SharedExecutorServiceAsyncExecutor(TenantInfoHolder)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.<init>(TenantInfoHolder)"})
  public void testNewSharedExecutorServiceAsyncExecutor() {
    // Arrange and Act
    SharedExecutorServiceAsyncExecutor actualSharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());

    // Assert
    assertTrue(
        actualSharedExecutorServiceAsyncExecutor.tenantInfoHolder instanceof DummyTenantInfoHolder);
    assertNull(actualSharedExecutorServiceAsyncExecutor.getAsyncJobAcquisitionThread());
    assertNull(actualSharedExecutorServiceAsyncExecutor.getResetExpiredJobThread());
    assertNull(actualSharedExecutorServiceAsyncExecutor.getTimerJobAcquisitionThread());
    assertNull(actualSharedExecutorServiceAsyncExecutor.getThreadPoolQueue());
    assertNull(actualSharedExecutorServiceAsyncExecutor.getExecutorService());
    assertNull(actualSharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration());
    assertEquals(
        0, actualSharedExecutorServiceAsyncExecutor.getDefaultQueueSizeFullWaitTimeInMillis());
    assertEquals(1, actualSharedExecutorServiceAsyncExecutor.getMaxAsyncJobsDuePerAcquisition());
    assertEquals(1, actualSharedExecutorServiceAsyncExecutor.getMaxTimerJobsPerAcquisition());
    assertEquals(10, actualSharedExecutorServiceAsyncExecutor.getMaxPoolSize());
    assertEquals(100, actualSharedExecutorServiceAsyncExecutor.getQueueSize());
    assertEquals(
        10000,
        actualSharedExecutorServiceAsyncExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis());
    assertEquals(
        10000,
        actualSharedExecutorServiceAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis());
    assertEquals(2, actualSharedExecutorServiceAsyncExecutor.getCorePoolSize());
    assertEquals(3, actualSharedExecutorServiceAsyncExecutor.getResetExpiredJobsPageSize());
    assertEquals(300000, actualSharedExecutorServiceAsyncExecutor.getAsyncJobLockTimeInMillis());
    assertEquals(300000, actualSharedExecutorServiceAsyncExecutor.getTimerLockTimeInMillis());
    assertEquals(500, actualSharedExecutorServiceAsyncExecutor.getRetryWaitTimeInMillis());
    assertEquals(5000L, actualSharedExecutorServiceAsyncExecutor.getKeepAliveTime());
    assertEquals(60000, actualSharedExecutorServiceAsyncExecutor.getResetExpiredJobsInterval());
    assertEquals(60L, actualSharedExecutorServiceAsyncExecutor.getSecondsToWaitOnShutdown());
    assertFalse(actualSharedExecutorServiceAsyncExecutor.isActive());
    assertFalse(actualSharedExecutorServiceAsyncExecutor.isAutoActivate());
    assertFalse(actualSharedExecutorServiceAsyncExecutor.isMessageQueueMode());
    assertTrue(actualSharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables.isEmpty());
    assertTrue(actualSharedExecutorServiceAsyncExecutor.asyncJobAcquisitionThreads.isEmpty());
    assertTrue(actualSharedExecutorServiceAsyncExecutor.resetExpiredJobsRunnables.isEmpty());
    assertTrue(actualSharedExecutorServiceAsyncExecutor.resetExpiredJobsThreads.isEmpty());
    assertTrue(actualSharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables.isEmpty());
    assertTrue(actualSharedExecutorServiceAsyncExecutor.timerJobAcquisitionThreads.isEmpty());
    assertTrue(actualSharedExecutorServiceAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds2() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneInMemProcessEngineConfiguration());
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds3() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(
        new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder()));
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds4() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds5() {
    // Arrange
    StandaloneInMemProcessEngineConfiguration processEngineConfiguration =
        new StandaloneInMemProcessEngineConfiguration();
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds6() {
    // Arrange
    StandaloneProcessEngineConfiguration processEngineConfiguration =
        new StandaloneProcessEngineConfiguration();
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds7() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("foo"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds8() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomMybatisXMLMappers(new HashSet<>());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds9() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDefaultCommandConfig(new CommandConfig());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds10() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCommandInterceptors(new ArrayList<>());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds11() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setUserGroupManager(mock(UserGroupManager.class));
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds12() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setBpmnDeploymentHelper(new BpmnDeploymentHelper());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds13() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setExpressionManager(new ExpressionManager());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds14() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTransactionFactory(new JdbcTransactionFactory());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds15() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomDefaultBpmnParseHandlers(new ArrayList<>());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds16() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setFailedJobCommandFactory(mock(FailedJobCommandFactory.class));
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds17() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    DeploymentEntityManagerImpl deploymentEntityManager =
        new DeploymentEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setDeploymentEntityManager(deploymentEntityManager);
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds18() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAsyncExecutorDefaultQueueSizeFullWaitTime(3);
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds19() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAsyncExecutorTimerLockTimeInMillis(1);
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds20() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAsyncExecutorExecuteAsyncRunnableFactory(
        mock(ExecuteAsyncRunnableFactory.class));
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds21() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setTimerJobAcquisitionThread(new Thread());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneInMemProcessEngineConfiguration());
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds22() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setAsyncJobAcquisitionThread(new Thread());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneInMemProcessEngineConfiguration());
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds23() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setCorePoolSize(3);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneInMemProcessEngineConfiguration());
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds24() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setResetExpiredJobsRunnable(
        new ResetExpiredJobsRunnable(new DefaultAsyncJobExecutor()));
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneInMemProcessEngineConfiguration());
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds25() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setTimerJobAcquisitionThread(new Thread());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(
        new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder()));
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds26() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setSecondsToWaitOnShutdown(1L);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(
        new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder()));
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds27() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfiguration =
        new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    processEngineConfiguration.setDeployers(new ArrayList<>());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds28() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfiguration =
        new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    processEngineConfiguration.setSerializableVariableTypeTrackDeserializedObjects(true);

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds29() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfiguration =
        new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    processEngineConfiguration.setSerializePOJOsInVariablesToJson(true);

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds30() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfiguration =
        new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    processEngineConfiguration.setBulkInsertEnabled(true);

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds31() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfiguration =
        new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    processEngineConfiguration.setByteArrayDataManager(
        new MybatisByteArrayDataManager(new JtaProcessEngineConfiguration()));

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds32() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfiguration =
        new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    ExecutionEntityManagerImpl executionEntityManager =
        new ExecutionEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisExecutionDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setExecutionEntityManager(executionEntityManager);

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds33() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfiguration =
        new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    processEngineConfiguration.setAsyncExecutorTimerLockTimeInMillis(1);

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds34() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfiguration =
        new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    processEngineConfiguration.setAsyncExecutorResetExpiredJobsInterval(42);

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds35() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfiguration =
        new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    processEngineConfiguration.setProcessEngineLifecycleListener(
        mock(ProcessEngineLifecycleListener.class));

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds36() {
    // Arrange
    StandaloneInMemProcessEngineConfiguration processEngineConfiguration =
        new StandaloneInMemProcessEngineConfiguration();
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setKeepAliveTime(1L);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds37() {
    // Arrange
    StandaloneInMemProcessEngineConfiguration processEngineConfiguration =
        new StandaloneInMemProcessEngineConfiguration();
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setAsyncJobsDueRunnable(
        new AcquireAsyncJobsDueRunnable(new DefaultAsyncJobExecutor()));
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds38() {
    // Arrange
    StandaloneInMemProcessEngineConfiguration processEngineConfiguration =
        new StandaloneInMemProcessEngineConfiguration();
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setExecuteAsyncRunnableFactory(
        mock(ExecuteAsyncRunnableFactory.class));
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds39() {
    // Arrange
    StandaloneProcessEngineConfiguration processEngineConfiguration =
        new StandaloneProcessEngineConfiguration();
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setAsyncJobAcquisitionThread(new Thread());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds40() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("foo"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setThreadPoolQueue(null);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds41() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTransactionFactory(new JdbcTransactionFactory());
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("foo"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds42() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDeploymentManager(new DeploymentManager());
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("foo"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds43() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setModelDataManager(
        new MybatisModelDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("foo"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds44() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    TimerJobEntityManagerImpl timerJobEntityManager =
        new TimerJobEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setTimerJobEntityManager(timerJobEntityManager);
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("foo"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds45() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAsyncExecutorThreadPoolQueueSize(3);
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("foo"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) Beans is {@link
   *       HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds_givenJtaProcessEngineConfigurationBeansIsHashMap()
      throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setBeans(new HashMap<>());
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("foo"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) JdbcDriver is {@code
   *       Jdbc Driver}.
   * </ul>
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds_givenJtaProcessEngineConfigurationJdbcDriverIsJdbcDriver() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJdbcDriver("Jdbc Driver");
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) JdbcMaxCheckoutTime is
   *       one.
   * </ul>
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds_givenJtaProcessEngineConfigurationJdbcMaxCheckoutTimeIsOne() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJdbcMaxCheckoutTime(1);
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) JpaHandleTransaction is
   *       {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds_givenJtaProcessEngineConfigurationJpaHandleTransactionIsTrue() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJpaHandleTransaction(true);
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) TablePrefixIsSchema is
   *       {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds_givenJtaProcessEngineConfigurationTablePrefixIsSchemaIsTrue() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTablePrefixIsSchema(true);
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) TaskQueryLimit is one.
   * </ul>
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds_givenJtaProcessEngineConfigurationTaskQueryLimitIsOne() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTaskQueryLimit(1);
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder())
            .getTenantIds()
            .isEmpty());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#addTenantAsyncExecutor(String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor(String, boolean)"
  })
  public void testAddTenantAsyncExecutor() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(null);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());

    // Act
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    assertTrue(
        sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration()
            instanceof JtaProcessEngineConfiguration);
    Map<String, TenantAwareAcquireAsyncJobsDueRunnable>
        stringTenantAwareAcquireAsyncJobsDueRunnableMap =
            sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireAsyncJobsDueRunnableMap.size());
    TenantAwareAcquireAsyncJobsDueRunnable getResult =
        stringTenantAwareAcquireAsyncJobsDueRunnableMap.get("42");
    assertNull(getResult.tenantInfoHolder);
    Map<String, TenantAwareAcquireTimerJobsRunnable> stringTenantAwareAcquireTimerJobsRunnableMap =
        sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireTimerJobsRunnableMap.size());
    TenantAwareAcquireTimerJobsRunnable getResult2 =
        stringTenantAwareAcquireTimerJobsRunnableMap.get("42");
    assertNull(getResult2.tenantInfoHolder);
    Map<String, TenantAwareResetExpiredJobsRunnable> stringTenantAwareResetExpiredJobsRunnableMap =
        sharedExecutorServiceAsyncExecutor.resetExpiredJobsRunnables;
    assertEquals(1, stringTenantAwareResetExpiredJobsRunnableMap.size());
    assertNull(stringTenantAwareResetExpiredJobsRunnableMap.get("42").tenantInfoHolder);
    assertEquals(0L, getResult.getMillisToWait());
    assertEquals(0L, getResult2.getMillisToWait());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#addTenantAsyncExecutor(String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor(String, boolean)"
  })
  public void testAddTenantAsyncExecutor2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomPreCommandInterceptors(new ArrayList<>());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(null);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    assertTrue(
        sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration()
            instanceof JtaProcessEngineConfiguration);
    Map<String, TenantAwareAcquireAsyncJobsDueRunnable>
        stringTenantAwareAcquireAsyncJobsDueRunnableMap =
            sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireAsyncJobsDueRunnableMap.size());
    TenantAwareAcquireAsyncJobsDueRunnable getResult =
        stringTenantAwareAcquireAsyncJobsDueRunnableMap.get("42");
    assertNull(getResult.tenantInfoHolder);
    Map<String, TenantAwareAcquireTimerJobsRunnable> stringTenantAwareAcquireTimerJobsRunnableMap =
        sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireTimerJobsRunnableMap.size());
    TenantAwareAcquireTimerJobsRunnable getResult2 =
        stringTenantAwareAcquireTimerJobsRunnableMap.get("42");
    assertNull(getResult2.tenantInfoHolder);
    Map<String, TenantAwareResetExpiredJobsRunnable> stringTenantAwareResetExpiredJobsRunnableMap =
        sharedExecutorServiceAsyncExecutor.resetExpiredJobsRunnables;
    assertEquals(1, stringTenantAwareResetExpiredJobsRunnableMap.size());
    assertNull(stringTenantAwareResetExpiredJobsRunnableMap.get("42").tenantInfoHolder);
    assertEquals(0L, getResult.getMillisToWait());
    assertEquals(0L, getResult2.getMillisToWait());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#addTenantAsyncExecutor(String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor(String, boolean)"
  })
  public void testAddTenantAsyncExecutor3() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCommandContextFactory(new CommandContextFactory());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(null);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    assertTrue(
        sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration()
            instanceof JtaProcessEngineConfiguration);
    Map<String, TenantAwareAcquireAsyncJobsDueRunnable>
        stringTenantAwareAcquireAsyncJobsDueRunnableMap =
            sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireAsyncJobsDueRunnableMap.size());
    TenantAwareAcquireAsyncJobsDueRunnable getResult =
        stringTenantAwareAcquireAsyncJobsDueRunnableMap.get("42");
    assertNull(getResult.tenantInfoHolder);
    Map<String, TenantAwareAcquireTimerJobsRunnable> stringTenantAwareAcquireTimerJobsRunnableMap =
        sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireTimerJobsRunnableMap.size());
    TenantAwareAcquireTimerJobsRunnable getResult2 =
        stringTenantAwareAcquireTimerJobsRunnableMap.get("42");
    assertNull(getResult2.tenantInfoHolder);
    Map<String, TenantAwareResetExpiredJobsRunnable> stringTenantAwareResetExpiredJobsRunnableMap =
        sharedExecutorServiceAsyncExecutor.resetExpiredJobsRunnables;
    assertEquals(1, stringTenantAwareResetExpiredJobsRunnableMap.size());
    assertNull(stringTenantAwareResetExpiredJobsRunnableMap.get("42").tenantInfoHolder);
    assertEquals(0L, getResult.getMillisToWait());
    assertEquals(0L, getResult2.getMillisToWait());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#addTenantAsyncExecutor(String,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor(String, boolean)"
  })
  public void testAddTenantAsyncExecutor4() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTablePrefixIsSchema(true);

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(null);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    assertTrue(
        sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration()
            instanceof JtaProcessEngineConfiguration);
    Map<String, TenantAwareAcquireAsyncJobsDueRunnable>
        stringTenantAwareAcquireAsyncJobsDueRunnableMap =
            sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireAsyncJobsDueRunnableMap.size());
    TenantAwareAcquireAsyncJobsDueRunnable getResult =
        stringTenantAwareAcquireAsyncJobsDueRunnableMap.get("42");
    assertNull(getResult.tenantInfoHolder);
    Map<String, TenantAwareAcquireTimerJobsRunnable> stringTenantAwareAcquireTimerJobsRunnableMap =
        sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireTimerJobsRunnableMap.size());
    TenantAwareAcquireTimerJobsRunnable getResult2 =
        stringTenantAwareAcquireTimerJobsRunnableMap.get("42");
    assertNull(getResult2.tenantInfoHolder);
    Map<String, TenantAwareResetExpiredJobsRunnable> stringTenantAwareResetExpiredJobsRunnableMap =
        sharedExecutorServiceAsyncExecutor.resetExpiredJobsRunnables;
    assertEquals(1, stringTenantAwareResetExpiredJobsRunnableMap.size());
    assertNull(stringTenantAwareResetExpiredJobsRunnableMap.get("42").tenantInfoHolder);
    assertEquals(0L, getResult.getMillisToWait());
    assertEquals(0L, getResult2.getMillisToWait());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link
   * SharedExecutorServiceAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(null);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert that nothing has changed
    Map<String, TenantAwareAcquireAsyncJobsDueRunnable>
        stringTenantAwareAcquireAsyncJobsDueRunnableMap =
            sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireAsyncJobsDueRunnableMap.size());
    assertEquals(0L, stringTenantAwareAcquireAsyncJobsDueRunnableMap.get("42").getMillisToWait());
    Map<String, TenantAwareAcquireTimerJobsRunnable> stringTenantAwareAcquireTimerJobsRunnableMap =
        sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireTimerJobsRunnableMap.size());
    assertEquals(0L, stringTenantAwareAcquireTimerJobsRunnableMap.get("42").getMillisToWait());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link
   * SharedExecutorServiceAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor2() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneProcessEngineConfiguration());
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(1, sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables.size());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link
   * SharedExecutorServiceAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor3() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setExpressionManager(new ExpressionManager());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(1, sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables.size());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link
   * SharedExecutorServiceAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor4() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(null, new CommandContextInterceptor());
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    verify(processEngineConfiguration).getJobManager();
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    Map<String, TenantAwareAcquireAsyncJobsDueRunnable>
        stringTenantAwareAcquireAsyncJobsDueRunnableMap =
            sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireAsyncJobsDueRunnableMap.size());
    assertEquals(0L, stringTenantAwareAcquireAsyncJobsDueRunnableMap.get("42").getMillisToWait());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link
   * SharedExecutorServiceAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor5() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setLockOwner("exception during async job acquisition: {}");

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    verify(processEngineConfiguration).getJobManager();
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    assertEquals(1, sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables.size());
    assertEquals(1, sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables.size());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link
   * SharedExecutorServiceAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor6() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(null);

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setDefaultTimerJobAcquireWaitTimeInMillis(0);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    Map<String, TenantAwareAcquireTimerJobsRunnable> stringTenantAwareAcquireTimerJobsRunnableMap =
        sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireTimerJobsRunnableMap.size());
    assertEquals(0L, stringTenantAwareAcquireTimerJobsRunnableMap.get("42").getMillisToWait());
    assertEquals(1, sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables.size());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#removeTenantAsyncExecutor(String)}.
   *
   * <p>Method under test: {@link
   * SharedExecutorServiceAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor7() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(new CommandConfig(), mock(CommandInterceptor.class));
    commandExecutorImpl.setFirst(new CommandContextInterceptor());

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setDefaultTimerJobAcquireWaitTimeInMillis(0);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    Map<String, TenantAwareAcquireAsyncJobsDueRunnable>
        stringTenantAwareAcquireAsyncJobsDueRunnableMap =
            sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireAsyncJobsDueRunnableMap.size());
    assertEquals(0L, stringTenantAwareAcquireAsyncJobsDueRunnableMap.get("42").getMillisToWait());
    Map<String, TenantAwareAcquireTimerJobsRunnable> stringTenantAwareAcquireTimerJobsRunnableMap =
        sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireTimerJobsRunnableMap.size());
    assertEquals(0L, stringTenantAwareAcquireTimerJobsRunnableMap.get("42").getMillisToWait());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#start()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.start()"})
  public void testStart() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());

    // Act
    sharedExecutorServiceAsyncExecutor.start();

    // Assert
    ExecutorService executorService = sharedExecutorServiceAsyncExecutor.getExecutorService();
    assertTrue(executorService instanceof ThreadPoolExecutor);
    assertTrue(
        ((ThreadPoolExecutor) executorService).getThreadFactory() instanceof BasicThreadFactory);
    assertEquals(0, ((ThreadPoolExecutor) executorService).getActiveCount());
    assertEquals(0, ((ThreadPoolExecutor) executorService).getLargestPoolSize());
    assertEquals(0, ((ThreadPoolExecutor) executorService).getPoolSize());
    assertEquals(0L, ((ThreadPoolExecutor) executorService).getCompletedTaskCount());
    assertEquals(0L, ((ThreadPoolExecutor) executorService).getTaskCount());
    assertEquals(10, ((ThreadPoolExecutor) executorService).getMaximumPoolSize());
    assertEquals(2, ((ThreadPoolExecutor) executorService).getCorePoolSize());
    BlockingQueue<Runnable> threadPoolQueue =
        sharedExecutorServiceAsyncExecutor.getThreadPoolQueue();
    assertTrue(threadPoolQueue.isEmpty());
    assertTrue(sharedExecutorServiceAsyncExecutor.isActive());
    assertSame(threadPoolQueue, ((ThreadPoolExecutor) executorService).getQueue());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread()"})
  public void testStopJobAcquisitionThread() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(null);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread();

    // Assert that nothing has changed
    Map<String, TenantAwareAcquireAsyncJobsDueRunnable>
        stringTenantAwareAcquireAsyncJobsDueRunnableMap =
            sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireAsyncJobsDueRunnableMap.size());
    assertEquals(0L, stringTenantAwareAcquireAsyncJobsDueRunnableMap.get("42").getMillisToWait());
    Map<String, TenantAwareAcquireTimerJobsRunnable> stringTenantAwareAcquireTimerJobsRunnableMap =
        sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireTimerJobsRunnableMap.size());
    assertEquals(0L, stringTenantAwareAcquireTimerJobsRunnableMap.get("42").getMillisToWait());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread()"})
  public void testStopJobAcquisitionThread2() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setMaxPoolSize(3);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread();

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread()"})
  public void testStopJobAcquisitionThread3() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setDefaultAsyncJobAcquireWaitTimeInMillis(1);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread();

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(1, sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables.size());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread()"})
  public void testStopJobAcquisitionThread4() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    ParsedDeploymentBuilderFactory parsedDeploymentBuilderFactory =
        new ParsedDeploymentBuilderFactory();
    parsedDeploymentBuilderFactory.setBpmnParser(new BpmnParser());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setParsedDeploymentBuilderFactory(parsedDeploymentBuilderFactory);

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread();

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(1, sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables.size());
    assertEquals(1, sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables.size());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread()"})
  public void testStopJobAcquisitionThread5() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setListenerNotificationHelper(new ListenerNotificationHelper());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread();

    // Assert that nothing has changed
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(1, sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables.size());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread()"})
  public void testStopJobAcquisitionThread6() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setEventHandlers(new HashMap<>());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread();

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(1, sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables.size());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread()"})
  public void testStopJobAcquisitionThread7() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setProcessDefinitionInfoDataManager(
        new MybatisProcessDefinitionInfoDataManager(new JtaProcessEngineConfiguration()));

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread();

    // Assert that nothing has changed
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(1, sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables.size());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread()"})
  public void testStopJobAcquisitionThread8() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    VariableInstanceEntityManagerImpl variableInstanceEntityManager =
        new VariableInstanceEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisVariableInstanceDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setVariableInstanceEntityManager(variableInstanceEntityManager);

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread();

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(1, sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables.size());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread()"})
  public void testStopJobAcquisitionThread9() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(defaultConfig, new DebugCommandInvoker());
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread();

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(1, sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables.size());
    assertEquals(1, sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables.size());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread()"})
  public void testStopJobAcquisitionThread10() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(defaultConfig, new LogInterceptor());
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread();

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    verify(processEngineConfiguration).getJobManager();
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    assertEquals(1, sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables.size());
    assertEquals(1, sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables.size());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread()"})
  public void testStopJobAcquisitionThread11() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(null);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread();

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    verify(processEngineConfiguration).getJobManager();
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    assertEquals(1, sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables.size());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.stopThreadsForTenant(String)"})
  public void testStopThreadsForTenant() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(null);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopThreadsForTenant("42");

    // Assert that nothing has changed
    Map<String, TenantAwareAcquireAsyncJobsDueRunnable>
        stringTenantAwareAcquireAsyncJobsDueRunnableMap =
            sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireAsyncJobsDueRunnableMap.size());
    assertEquals(0L, stringTenantAwareAcquireAsyncJobsDueRunnableMap.get("42").getMillisToWait());
    Map<String, TenantAwareAcquireTimerJobsRunnable> stringTenantAwareAcquireTimerJobsRunnableMap =
        sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireTimerJobsRunnableMap.size());
    assertEquals(0L, stringTenantAwareAcquireTimerJobsRunnableMap.get("42").getMillisToWait());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.stopThreadsForTenant(String)"})
  public void testStopThreadsForTenant2() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setLockOwner("{} stopped async job due acquisition");
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopThreadsForTenant("42");

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(1, sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables.size());
    assertEquals(1, sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables.size());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.stopThreadsForTenant(String)"})
  public void testStopThreadsForTenant3() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(defaultConfig, new LogInterceptor());
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopThreadsForTenant("42");

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(1, sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables.size());
  }
}
