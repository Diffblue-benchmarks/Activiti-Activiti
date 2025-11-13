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
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import javax.script.ScriptEngineManager;
import javax.xml.namespace.QName;
import org.activiti.engine.impl.ManagementServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.asyncexecutor.AcquireTimerJobsRunnable;
import org.activiti.engine.impl.asyncexecutor.DefaultAsyncJobExecutor;
import org.activiti.engine.impl.asyncexecutor.DefaultJobManager;
import org.activiti.engine.impl.asyncexecutor.ExecuteAsyncRunnableFactory;
import org.activiti.engine.impl.asyncexecutor.ResetExpiredJobsRunnable;
import org.activiti.engine.impl.bpmn.deployer.CachingAndArtifactsManager;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.multitenant.MultiSchemaMultiTenantProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.multitenant.TenantInfoHolder;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.CommandInvoker;
import org.activiti.engine.impl.interceptor.DebugCommandInvoker;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.DeploymentEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.ModelEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisDeadLetterJobDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisDeploymentDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricProcessInstanceDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisModelDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisProcessDefinitionDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisTaskDataManager;
import org.activiti.engine.impl.scripting.ScriptingEngines;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.engine.impl.util.ProcessInstanceHelper;
import org.activiti.engine.test.cfg.multitenant.DummyTenantInfoHolder;
import org.activiti.engine.test.impl.logger.ProcessExecutionLoggerConfigurator;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
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
        new StandaloneProcessEngineConfiguration());
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
  public void testGetTenantIds5() {
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
  public void testGetTenantIds6() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfiguration =
        new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
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
    MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfiguration =
        new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("Q Name As String"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

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
    MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfiguration =
        new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    processEngineConfiguration.setRuntimeService(new RuntimeServiceImpl());
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
    MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfiguration =
        new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    processEngineConfiguration.setConfigurators(new ArrayList<>());
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
    MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfiguration =
        new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    processEngineConfiguration.setProcessInstanceHelper(new ProcessInstanceHelper());
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
    MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfiguration =
        new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    processEngineConfiguration.setCustomScriptingEngineClasses(new ArrayList<>());
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
    MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfiguration =
        new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    HistoricProcessInstanceEntityManagerImpl historicProcessInstanceEntityManager =
        new HistoricProcessInstanceEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisHistoricProcessInstanceDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setHistoricProcessInstanceEntityManager(
        historicProcessInstanceEntityManager);
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
    MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfiguration =
        new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    processEngineConfiguration.setJpaCloseEntityManager(true);
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
  public void testGetTenantIds14() throws MalformedURLException {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfiguration =
        new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    processEngineConfiguration.setCustomPreVariableTypes(new ArrayList<>());
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("Q Name As String"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

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
  public void testGetTenantIds15() throws MalformedURLException {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfiguration =
        new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    DeadLetterJobEntityManagerImpl deadLetterJobEntityManager =
        new DeadLetterJobEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisDeadLetterJobDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setDeadLetterJobEntityManager(deadLetterJobEntityManager);
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("Q Name As String"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

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
  public void testGetTenantIds16() throws MalformedURLException {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfiguration =
        new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    processEngineConfiguration.setDatabaseSchema("{} starting to reset expired jobs");
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("Q Name As String"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

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
  public void testGetTenantIds17() throws MalformedURLException {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfiguration =
        new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    processEngineConfiguration.setEnableProcessDefinitionInfoCache(true);
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("Q Name As String"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

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
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setThreadPoolQueue(null);
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
  public void testGetTenantIds19() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setMaxPoolSize(3);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneProcessEngineConfiguration());
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
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setLockOwner("Lock Owner");
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneProcessEngineConfiguration());
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
    sharedExecutorServiceAsyncExecutor.setAsyncJobLockTimeInMillis(1);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneProcessEngineConfiguration());
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
    sharedExecutorServiceAsyncExecutor.setDefaultQueueSizeFullWaitTimeInMillis(3);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneProcessEngineConfiguration());
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
    sharedExecutorServiceAsyncExecutor.setExecuteAsyncRunnableFactory(
        mock(ExecuteAsyncRunnableFactory.class));
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneProcessEngineConfiguration());
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
    DefaultAsyncJobExecutor asyncExecutor = new DefaultAsyncJobExecutor();
    AcquireTimerJobsRunnable timerJobRunnable =
        new AcquireTimerJobsRunnable(asyncExecutor, new DefaultJobManager());
    sharedExecutorServiceAsyncExecutor.setTimerJobRunnable(timerJobRunnable);
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
  public void testGetTenantIds25() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setResetExpiredJobsRunnable(
        new ResetExpiredJobsRunnable(new DefaultAsyncJobExecutor()));
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
    MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfiguration =
        new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    processEngineConfiguration.setCustomPostCommandInterceptors(new ArrayList<>());

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
  public void testGetTenantIds27() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfiguration =
        new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    processEngineConfiguration.setRuntimeService(new RuntimeServiceImpl());

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
    processEngineConfiguration.setScriptingEngines(new ScriptingEngines(new ScriptEngineManager()));

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
    processEngineConfiguration.setCustomJobHandlers(new ArrayList<>());

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
    processEngineConfiguration.setCustomPreVariableTypes(new ArrayList<>());

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
    processEngineConfiguration.setTaskDataManager(
        new MybatisTaskDataManager(new JtaProcessEngineConfiguration()));

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
    DeploymentEntityManagerImpl deploymentEntityManager =
        new DeploymentEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisDeploymentDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setDeploymentEntityManager(deploymentEntityManager);

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
    processEngineConfiguration.setClock(new DefaultClockImpl());

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
    processEngineConfiguration.setObjectMapper(JsonMapper.builder().findAndAddModules().build());

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
    processEngineConfiguration.setAsyncExecutorMaxAsyncJobsDuePerAcquisition(1);

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
    MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfiguration =
        new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    processEngineConfiguration.setMailSessionJndi("Mail Session Jndi");

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
  public void testGetTenantIds37() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfiguration =
        new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    processEngineConfiguration.setDefaultCamelContext("Default Camel Context");

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
  public void testGetTenantIds38() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
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
  public void testGetTenantIds39() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
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
  public void testGetTenantIds40() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    DefaultAsyncJobExecutor asyncExecutor = new DefaultAsyncJobExecutor();
    AcquireTimerJobsRunnable timerJobRunnable =
        new AcquireTimerJobsRunnable(asyncExecutor, new DefaultJobManager());
    sharedExecutorServiceAsyncExecutor.setTimerJobRunnable(timerJobRunnable);
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
  public void testGetTenantIds41() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
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
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
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
    Map<String, TenantAwareResetExpiredJobsRunnable> stringTenantAwareResetExpiredJobsRunnableMap =
        sharedExecutorServiceAsyncExecutor.resetExpiredJobsRunnables;
    assertEquals(1, stringTenantAwareResetExpiredJobsRunnableMap.size());
    Map<String, TenantAwareAcquireTimerJobsRunnable> stringTenantAwareAcquireTimerJobsRunnableMap =
        sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireTimerJobsRunnableMap.size());
    TenantInfoHolder tenantInfoHolder = sharedExecutorServiceAsyncExecutor.tenantInfoHolder;
    assertSame(
        tenantInfoHolder,
        stringTenantAwareAcquireAsyncJobsDueRunnableMap.get("42").tenantInfoHolder);
    assertSame(
        tenantInfoHolder, stringTenantAwareAcquireTimerJobsRunnableMap.get("42").tenantInfoHolder);
    assertSame(
        tenantInfoHolder, stringTenantAwareResetExpiredJobsRunnableMap.get("42").tenantInfoHolder);
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
  public void testAddTenantAsyncExecutor3() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    processEngineConfiguration.setCommandExecutor(commandExecutor);

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
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
    Map<String, TenantAwareResetExpiredJobsRunnable> stringTenantAwareResetExpiredJobsRunnableMap =
        sharedExecutorServiceAsyncExecutor.resetExpiredJobsRunnables;
    assertEquals(1, stringTenantAwareResetExpiredJobsRunnableMap.size());
    Map<String, TenantAwareAcquireTimerJobsRunnable> stringTenantAwareAcquireTimerJobsRunnableMap =
        sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireTimerJobsRunnableMap.size());
    TenantInfoHolder tenantInfoHolder = sharedExecutorServiceAsyncExecutor.tenantInfoHolder;
    assertSame(
        tenantInfoHolder,
        stringTenantAwareAcquireAsyncJobsDueRunnableMap.get("42").tenantInfoHolder);
    assertSame(
        tenantInfoHolder, stringTenantAwareAcquireTimerJobsRunnableMap.get("42").tenantInfoHolder);
    assertSame(
        tenantInfoHolder, stringTenantAwareResetExpiredJobsRunnableMap.get("42").tenantInfoHolder);
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
    processEngineConfiguration.setCustomJobHandlers(new ArrayList<>());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
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
    Map<String, TenantAwareResetExpiredJobsRunnable> stringTenantAwareResetExpiredJobsRunnableMap =
        sharedExecutorServiceAsyncExecutor.resetExpiredJobsRunnables;
    assertEquals(1, stringTenantAwareResetExpiredJobsRunnableMap.size());
    Map<String, TenantAwareAcquireTimerJobsRunnable> stringTenantAwareAcquireTimerJobsRunnableMap =
        sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireTimerJobsRunnableMap.size());
    TenantInfoHolder tenantInfoHolder = sharedExecutorServiceAsyncExecutor.tenantInfoHolder;
    assertSame(
        tenantInfoHolder,
        stringTenantAwareAcquireAsyncJobsDueRunnableMap.get("42").tenantInfoHolder);
    assertSame(
        tenantInfoHolder, stringTenantAwareAcquireTimerJobsRunnableMap.get("42").tenantInfoHolder);
    assertSame(
        tenantInfoHolder, stringTenantAwareResetExpiredJobsRunnableMap.get("42").tenantInfoHolder);
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
  public void testAddTenantAsyncExecutor5() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    ModelEntityManagerImpl modelEntityManager =
        new ModelEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisModelDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setModelEntityManager(modelEntityManager);

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
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
    Map<String, TenantAwareResetExpiredJobsRunnable> stringTenantAwareResetExpiredJobsRunnableMap =
        sharedExecutorServiceAsyncExecutor.resetExpiredJobsRunnables;
    assertEquals(1, stringTenantAwareResetExpiredJobsRunnableMap.size());
    Map<String, TenantAwareAcquireTimerJobsRunnable> stringTenantAwareAcquireTimerJobsRunnableMap =
        sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireTimerJobsRunnableMap.size());
    TenantInfoHolder tenantInfoHolder = sharedExecutorServiceAsyncExecutor.tenantInfoHolder;
    assertSame(
        tenantInfoHolder,
        stringTenantAwareAcquireAsyncJobsDueRunnableMap.get("42").tenantInfoHolder);
    assertSame(
        tenantInfoHolder, stringTenantAwareAcquireTimerJobsRunnableMap.get("42").tenantInfoHolder);
    assertSame(
        tenantInfoHolder, stringTenantAwareResetExpiredJobsRunnableMap.get("42").tenantInfoHolder);
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
  public void testAddTenantAsyncExecutor6() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomScriptingEngineClasses(new ArrayList<>());

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
  public void testAddTenantAsyncExecutor7() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManager =
        new ProcessDefinitionEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisProcessDefinitionDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setProcessDefinitionEntityManager(processDefinitionEntityManager);

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
  public void testAddTenantAsyncExecutor8() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    processEngineConfiguration.setCommandExecutor(commandExecutor);

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    DefaultAsyncJobExecutor asyncExecutor = new DefaultAsyncJobExecutor();
    AcquireTimerJobsRunnable timerJobRunnable =
        new AcquireTimerJobsRunnable(asyncExecutor, new DefaultJobManager());
    sharedExecutorServiceAsyncExecutor.setTimerJobRunnable(timerJobRunnable);
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
    Map<String, TenantAwareResetExpiredJobsRunnable> stringTenantAwareResetExpiredJobsRunnableMap =
        sharedExecutorServiceAsyncExecutor.resetExpiredJobsRunnables;
    assertEquals(1, stringTenantAwareResetExpiredJobsRunnableMap.size());
    Map<String, TenantAwareAcquireTimerJobsRunnable> stringTenantAwareAcquireTimerJobsRunnableMap =
        sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireTimerJobsRunnableMap.size());
    TenantInfoHolder tenantInfoHolder = sharedExecutorServiceAsyncExecutor.tenantInfoHolder;
    assertSame(
        tenantInfoHolder,
        stringTenantAwareAcquireAsyncJobsDueRunnableMap.get("42").tenantInfoHolder);
    assertSame(
        tenantInfoHolder, stringTenantAwareAcquireTimerJobsRunnableMap.get("42").tenantInfoHolder);
    assertSame(
        tenantInfoHolder, stringTenantAwareResetExpiredJobsRunnableMap.get("42").tenantInfoHolder);
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
  public void testAddTenantAsyncExecutor9() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    processEngineConfiguration.setCommandExecutor(commandExecutor);

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setDefaultQueueSizeFullWaitTimeInMillis(3);
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
    Map<String, TenantAwareResetExpiredJobsRunnable> stringTenantAwareResetExpiredJobsRunnableMap =
        sharedExecutorServiceAsyncExecutor.resetExpiredJobsRunnables;
    assertEquals(1, stringTenantAwareResetExpiredJobsRunnableMap.size());
    Map<String, TenantAwareAcquireTimerJobsRunnable> stringTenantAwareAcquireTimerJobsRunnableMap =
        sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireTimerJobsRunnableMap.size());
    TenantInfoHolder tenantInfoHolder = sharedExecutorServiceAsyncExecutor.tenantInfoHolder;
    assertSame(
        tenantInfoHolder,
        stringTenantAwareAcquireAsyncJobsDueRunnableMap.get("42").tenantInfoHolder);
    assertSame(
        tenantInfoHolder, stringTenantAwareAcquireTimerJobsRunnableMap.get("42").tenantInfoHolder);
    assertSame(
        tenantInfoHolder, stringTenantAwareResetExpiredJobsRunnableMap.get("42").tenantInfoHolder);
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

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setBatchSizeProcessInstances(3);

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
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
  public void testRemoveTenantAsyncExecutor3() {
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
    sharedExecutorServiceAsyncExecutor.setAsyncJobAcquisitionThread(new Thread());
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

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setLockOwner("exception during timer job acquisition: {}");
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread();

    // Assert that nothing has changed
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder).addTenant("42");
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(1, sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables.size());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}.
   *
   * <ul>
   *   <li>Given {@link CommandConfig#CommandConfig(boolean)} with contextReusePossible is {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread()"})
  public void testStopJobAcquisitionThread_givenCommandConfigWithContextReusePossibleIsTrue() {
    // Arrange
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("42");

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = new CommandConfig(true);
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread();

    // Assert
    verify(tenantInfoHolder).addTenant("42");
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

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopThreadsForTenant("42");

    // Assert that nothing has changed
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
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
  public void testStopThreadsForTenant3() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setTimerJobAcquisitionThread(new Thread());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopThreadsForTenant("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
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
  public void testStopThreadsForTenant4() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setAsyncJobAcquisitionThread(new Thread());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopThreadsForTenant("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
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
  public void testStopThreadsForTenant5() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setAsyncJobLockTimeInMillis(1);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopThreadsForTenant("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
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
  public void testStopThreadsForTenant6() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setDefaultTimerJobAcquireWaitTimeInMillis(1);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopThreadsForTenant("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
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
  public void testStopThreadsForTenant7() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setResetExpiredJobsInterval(42);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopThreadsForTenant("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
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
  public void testStopThreadsForTenant8() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutor =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    processEngineConfiguration.setCommandExecutor(commandExecutor);

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopThreadsForTenant("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
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
  public void testStopThreadsForTenant9() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setManagementService(new ManagementServiceImpl());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopThreadsForTenant("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
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
  public void testStopThreadsForTenant10() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCachingAndArtifactsManager(new CachingAndArtifactsManager());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopThreadsForTenant("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
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
  public void testStopThreadsForTenant11() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setSqlSessionFactory(
        new DefaultSqlSessionFactory(new Configuration()));

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopThreadsForTenant("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
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
  public void testStopThreadsForTenant12() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomPreVariableTypes(new ArrayList<>());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopThreadsForTenant("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
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
  public void testStopThreadsForTenant13() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    ProcessDefinitionEntityManagerImpl processDefinitionEntityManager =
        new ProcessDefinitionEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisProcessDefinitionDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setProcessDefinitionEntityManager(processDefinitionEntityManager);

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopThreadsForTenant("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
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
  public void testStopThreadsForTenant14() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAsyncExecutorSecondsToWaitOnShutdown(1L);

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopThreadsForTenant("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
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
  public void testStopThreadsForTenant15() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);

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
    sharedExecutorServiceAsyncExecutor.stopThreadsForTenant("42");

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
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
  public void testStopThreadsForTenant16() {
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
    sharedExecutorServiceAsyncExecutor.setTimerJobAcquisitionThread(new Thread());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopThreadsForTenant("42");

    // Assert that nothing has changed
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
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
  public void testStopThreadsForTenant17() {
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
    sharedExecutorServiceAsyncExecutor.setAsyncJobAcquisitionThread(new Thread());
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

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.stopThreadsForTenant(String)"})
  public void testStopThreadsForTenant18() {
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
    sharedExecutorServiceAsyncExecutor.setResetExpiredJobThread(new Thread());
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

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.stopThreadsForTenant(String)"})
  public void testStopThreadsForTenant19() {
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
    sharedExecutorServiceAsyncExecutor.setTimerLockTimeInMillis(10000);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopThreadsForTenant("42");

    // Assert that nothing has changed
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
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
  public void testStopThreadsForTenant20() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager())
        .thenReturn(new DefaultJobManager(new JtaProcessEngineConfiguration()));
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopThreadsForTenant("42");

    // Assert that nothing has changed
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
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
  public void testStopThreadsForTenant21() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = new CommandConfig();
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(defaultConfig, new CommandInvoker());
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
  public void testStopThreadsForTenant22() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(mock(CommandConfig.class), null);
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

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.stopThreadsForTenant(String)"})
  public void testStopThreadsForTenant23() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    CommandConfig defaultConfig = mock(CommandConfig.class);
    when(defaultConfig.isContextReusePossible()).thenReturn(true);
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(defaultConfig, new DebugCommandInvoker());

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
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
  public void testStopThreadsForTenant24() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    CommandConfig defaultConfig = mock(CommandConfig.class);
    when(defaultConfig.isContextReusePossible()).thenReturn(true);
    CommandExecutorImpl commandExecutorImpl = new CommandExecutorImpl(defaultConfig, null);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
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
  public void testStopThreadsForTenant25() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(mock(CommandConfig.class), mock(CommandInvoker.class));
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
  public void testStopThreadsForTenant26() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    CommandConfig defaultConfig = mock(CommandConfig.class);
    when(defaultConfig.isContextReusePossible()).thenReturn(true);
    CommandExecutorImpl commandExecutorImpl =
        new CommandExecutorImpl(defaultConfig, mock(DebugCommandInvoker.class));

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
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

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}.
   *
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor) XmlEncoding is {@code
   *       UTF-8}.
   * </ul>
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.stopThreadsForTenant(String)"})
  public void testStopThreadsForTenant_givenJtaProcessEngineConfigurationXmlEncodingIsUtf8() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setXmlEncoding("UTF-8");

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopThreadsForTenant("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
  }
}
