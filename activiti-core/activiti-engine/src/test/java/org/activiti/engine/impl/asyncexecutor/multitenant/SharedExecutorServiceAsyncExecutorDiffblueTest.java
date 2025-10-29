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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import groovy.lang.GroovyClassLoader;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import javax.xml.namespace.QName;
import org.activiti.engine.impl.asyncexecutor.DefaultJobManager;
import org.activiti.engine.impl.asyncexecutor.ExecuteAsyncRunnableFactory;
import org.activiti.engine.impl.asyncexecutor.JobManager;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.multitenant.MultiSchemaMultiTenantProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.multitenant.TenantInfoHolder;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextFactory;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.CommandInvoker;
import org.activiti.engine.impl.persistence.deploy.DefaultDeploymentCache;
import org.activiti.engine.impl.persistence.entity.ByteArrayEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.CommentEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisByteArrayDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisCommentDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisHistoricVariableInstanceDataManager;
import org.activiti.engine.test.cfg.multitenant.DummyTenantInfoHolder;
import org.activiti.engine.test.impl.logger.ProcessExecutionLoggerConfigurator;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.junit.Test;
import org.mockito.Mockito;

public class SharedExecutorServiceAsyncExecutorDiffblueTest {
  /**
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  public void testGetTenantIds() {
    // Arrange, Act and Assert
    assertTrue((new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder())).getTenantIds().isEmpty());
  }

  /**
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  public void testGetTenantIds2() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setExecuteAsyncRunnableFactory(mock(ExecuteAsyncRunnableFactory.class));

    // Act and Assert
    assertTrue(sharedExecutorServiceAsyncExecutor.getTenantIds().isEmpty());
  }

  /**
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  public void testGetTenantIds3() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  public void testGetTenantIds4() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  public void testGetTenantIds5() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addWsEndpointAddress(QName.valueOf("exception during async job acquisition: {}"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  public void testGetTenantIds6() {
    // Arrange
    StandaloneInMemProcessEngineConfiguration processEngineConfiguration = new StandaloneInMemProcessEngineConfiguration();
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  public void testGetTenantIds7() {
    // Arrange
    StandaloneProcessEngineConfiguration processEngineConfiguration = new StandaloneProcessEngineConfiguration();
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  public void testGetTenantIds8() throws MalformedURLException {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfiguration = new MultiSchemaMultiTenantProcessEngineConfiguration(
        new DummyTenantInfoHolder());
    processEngineConfiguration.addWsEndpointAddress(QName.valueOf("exception during async job acquisition: {}"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  public void testGetTenantIds9() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCustomPreCommandInterceptors(new ArrayList<>());
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  public void testGetTenantIds10() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJavaClassFieldForJackson("exception during resetting expired jobs");
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  public void testGetTenantIds11() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setHistoricProcessInstancesQueryLimit(10000);
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  public void testGetTenantIds12() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setByteArrayEntityManager(new ByteArrayEntityManagerImpl(processEngineConfiguration2,
        new MybatisByteArrayDataManager(new JtaProcessEngineConfiguration())));
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  public void testGetTenantIds13() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setHistoricVariableInstanceEntityManager(
        new HistoricVariableInstanceEntityManagerImpl(processEngineConfiguration2,
            new MybatisHistoricVariableInstanceDataManager(new JtaProcessEngineConfiguration())));
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  public void testGetTenantIds14() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAsyncExecutorSecondsToWaitOnShutdown(10000L);
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  public void testGetTenantIds15() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAsyncExecutorExecuteAsyncRunnableFactory(mock(ExecuteAsyncRunnableFactory.class));
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  public void testGetTenantIds16() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAsyncExecutorMessageQueueMode(true);
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  public void testGetTenantIds17() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setMailServerHost("localhost");
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  public void testGetTenantIds18() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setSerializePOJOsInVariablesToJson(true);
    processEngineConfiguration.addWsEndpointAddress(QName.valueOf("exception during async job acquisition: {}"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  public void testGetTenantIds19() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setKnowledgeBaseCache(new DefaultDeploymentCache<>());
    processEngineConfiguration.addWsEndpointAddress(QName.valueOf("exception during async job acquisition: {}"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  public void testGetTenantIds20() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAsyncExecutorResetExpiredJobsPageSize(3);
    processEngineConfiguration.addWsEndpointAddress(QName.valueOf("exception during async job acquisition: {}"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  public void testGetTenantIds21() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setHistory("History");
    processEngineConfiguration.addWsEndpointAddress(QName.valueOf("exception during async job acquisition: {}"),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  public void testGetTenantIds22() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTypedEventListeners(new HashMap<>());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  public void testGetTenantIds23() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCommentEntityManager(new CommentEntityManagerImpl(processEngineConfiguration2,
        new MybatisCommentDataManager(new JtaProcessEngineConfiguration())));

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  public void testGetTenantIds24() {
    // Arrange
    StandaloneInMemProcessEngineConfiguration processEngineConfiguration = new StandaloneInMemProcessEngineConfiguration();
    processEngineConfiguration.addConfigurator(null);

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    Set<String> actualTenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();

    // Assert
    assertEquals(1, actualTenantIds.size());
    assertTrue(actualTenantIds.contains("42"));
  }

  /**
   * Method under test:
   * {@link SharedExecutorServiceAsyncExecutor#addTenantAsyncExecutor(String, boolean)}
   */
  @Test
  public void testAddTenantAsyncExecutor() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        null);
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    Map<String, TenantAwareAcquireAsyncJobsDueRunnable> stringTenantAwareAcquireAsyncJobsDueRunnableMap = sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireAsyncJobsDueRunnableMap.size());
    TenantAwareAcquireAsyncJobsDueRunnable getResult = stringTenantAwareAcquireAsyncJobsDueRunnableMap.get("42");
    assertEquals("42", getResult.tenantId);
    Map<String, TenantAwareAcquireTimerJobsRunnable> stringTenantAwareAcquireTimerJobsRunnableMap = sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireTimerJobsRunnableMap.size());
    TenantAwareAcquireTimerJobsRunnable getResult2 = stringTenantAwareAcquireTimerJobsRunnableMap.get("42");
    assertEquals("42", getResult2.tenantId);
    Map<String, TenantAwareResetExpiredJobsRunnable> stringTenantAwareResetExpiredJobsRunnableMap = sharedExecutorServiceAsyncExecutor.resetExpiredJobsRunnables;
    assertEquals(1, stringTenantAwareResetExpiredJobsRunnableMap.size());
    TenantAwareResetExpiredJobsRunnable getResult3 = stringTenantAwareResetExpiredJobsRunnableMap.get("42");
    assertEquals("42", getResult3.tenantId);
    assertNull(getResult.tenantInfoHolder);
    assertNull(getResult2.tenantInfoHolder);
    assertNull(getResult3.tenantInfoHolder);
    assertEquals(0L, getResult.getMillisToWait());
    assertEquals(0L, getResult2.getMillisToWait());
    Map<String, Thread> stringThreadMap = sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionThreads;
    assertEquals(1, stringThreadMap.size());
    Map<String, Thread> stringThreadMap2 = sharedExecutorServiceAsyncExecutor.resetExpiredJobsThreads;
    assertEquals(1, stringThreadMap2.size());
    Map<String, Thread> stringThreadMap3 = sharedExecutorServiceAsyncExecutor.timerJobAcquisitionThreads;
    assertEquals(1, stringThreadMap3.size());
    Set<String> tenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();
    assertEquals(1, tenantIds.size());
    assertTrue(stringThreadMap.containsKey("42"));
    assertTrue(stringThreadMap2.containsKey("42"));
    assertTrue(stringThreadMap3.containsKey("42"));
    assertTrue(tenantIds.contains("42"));
    assertSame(processEngineConfiguration, sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration());
  }

  /**
   * Method under test:
   * {@link SharedExecutorServiceAsyncExecutor#addTenantAsyncExecutor(String, boolean)}
   */
  @Test
  public void testAddTenantAsyncExecutor2() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        null);
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", false);

    // Assert
    Map<String, TenantAwareAcquireAsyncJobsDueRunnable> stringTenantAwareAcquireAsyncJobsDueRunnableMap = sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireAsyncJobsDueRunnableMap.size());
    TenantAwareAcquireAsyncJobsDueRunnable getResult = stringTenantAwareAcquireAsyncJobsDueRunnableMap.get("42");
    assertEquals("42", getResult.tenantId);
    Map<String, TenantAwareAcquireTimerJobsRunnable> stringTenantAwareAcquireTimerJobsRunnableMap = sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireTimerJobsRunnableMap.size());
    TenantAwareAcquireTimerJobsRunnable getResult2 = stringTenantAwareAcquireTimerJobsRunnableMap.get("42");
    assertEquals("42", getResult2.tenantId);
    Map<String, TenantAwareResetExpiredJobsRunnable> stringTenantAwareResetExpiredJobsRunnableMap = sharedExecutorServiceAsyncExecutor.resetExpiredJobsRunnables;
    assertEquals(1, stringTenantAwareResetExpiredJobsRunnableMap.size());
    TenantAwareResetExpiredJobsRunnable getResult3 = stringTenantAwareResetExpiredJobsRunnableMap.get("42");
    assertEquals("42", getResult3.tenantId);
    assertNull(getResult.tenantInfoHolder);
    assertNull(getResult2.tenantInfoHolder);
    assertNull(getResult3.tenantInfoHolder);
    assertEquals(0L, getResult.getMillisToWait());
    assertEquals(0L, getResult2.getMillisToWait());
    Map<String, Thread> stringThreadMap = sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionThreads;
    assertEquals(1, stringThreadMap.size());
    Map<String, Thread> stringThreadMap2 = sharedExecutorServiceAsyncExecutor.resetExpiredJobsThreads;
    assertEquals(1, stringThreadMap2.size());
    Map<String, Thread> stringThreadMap3 = sharedExecutorServiceAsyncExecutor.timerJobAcquisitionThreads;
    assertEquals(1, stringThreadMap3.size());
    Set<String> tenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();
    assertEquals(1, tenantIds.size());
    assertTrue(stringThreadMap.containsKey("42"));
    assertTrue(stringThreadMap2.containsKey("42"));
    assertTrue(stringThreadMap3.containsKey("42"));
    assertTrue(tenantIds.contains("42"));
    assertSame(processEngineConfiguration, sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration());
  }

  /**
   * Method under test:
   * {@link SharedExecutorServiceAsyncExecutor#addTenantAsyncExecutor(String, boolean)}
   */
  @Test
  public void testAddTenantAsyncExecutor3() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setClassLoader(new GroovyClassLoader());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        null);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    Map<String, TenantAwareAcquireAsyncJobsDueRunnable> stringTenantAwareAcquireAsyncJobsDueRunnableMap = sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireAsyncJobsDueRunnableMap.size());
    TenantAwareAcquireAsyncJobsDueRunnable getResult = stringTenantAwareAcquireAsyncJobsDueRunnableMap.get("42");
    assertEquals("42", getResult.tenantId);
    Map<String, TenantAwareAcquireTimerJobsRunnable> stringTenantAwareAcquireTimerJobsRunnableMap = sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireTimerJobsRunnableMap.size());
    TenantAwareAcquireTimerJobsRunnable getResult2 = stringTenantAwareAcquireTimerJobsRunnableMap.get("42");
    assertEquals("42", getResult2.tenantId);
    Map<String, TenantAwareResetExpiredJobsRunnable> stringTenantAwareResetExpiredJobsRunnableMap = sharedExecutorServiceAsyncExecutor.resetExpiredJobsRunnables;
    assertEquals(1, stringTenantAwareResetExpiredJobsRunnableMap.size());
    TenantAwareResetExpiredJobsRunnable getResult3 = stringTenantAwareResetExpiredJobsRunnableMap.get("42");
    assertEquals("42", getResult3.tenantId);
    assertNull(getResult.tenantInfoHolder);
    assertNull(getResult2.tenantInfoHolder);
    assertNull(getResult3.tenantInfoHolder);
    assertEquals(0L, getResult.getMillisToWait());
    assertEquals(0L, getResult2.getMillisToWait());
    Map<String, Thread> stringThreadMap = sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionThreads;
    assertEquals(1, stringThreadMap.size());
    Map<String, Thread> stringThreadMap2 = sharedExecutorServiceAsyncExecutor.resetExpiredJobsThreads;
    assertEquals(1, stringThreadMap2.size());
    Map<String, Thread> stringThreadMap3 = sharedExecutorServiceAsyncExecutor.timerJobAcquisitionThreads;
    assertEquals(1, stringThreadMap3.size());
    Set<String> tenantIds = sharedExecutorServiceAsyncExecutor.getTenantIds();
    assertEquals(1, tenantIds.size());
    assertTrue(stringThreadMap.containsKey("42"));
    assertTrue(stringThreadMap2.containsKey("42"));
    assertTrue(stringThreadMap3.containsKey("42"));
    assertTrue(tenantIds.contains("42"));
    assertSame(processEngineConfiguration, sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration());
  }

  /**
   * Method under test:
   * {@link SharedExecutorServiceAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  public void testRemoveTenantAsyncExecutor() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
  }

  /**
   * Method under test:
   * {@link SharedExecutorServiceAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  public void testRemoveTenantAsyncExecutor2() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(new StandaloneInMemProcessEngineConfiguration());
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
  }

  /**
   * Method under test:
   * {@link SharedExecutorServiceAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  public void testRemoveTenantAsyncExecutor3() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(new StandaloneProcessEngineConfiguration());
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
  }

  /**
   * Method under test:
   * {@link SharedExecutorServiceAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  public void testRemoveTenantAsyncExecutor4() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
  }

  /**
   * Method under test:
   * {@link SharedExecutorServiceAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  public void testRemoveTenantAsyncExecutor5() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = new CommandConfig();
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
  }

  /**
   * Method under test:
   * {@link SharedExecutorServiceAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  public void testRemoveTenantAsyncExecutor6() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(null);
    CommandConfig defaultConfig = new CommandConfig();
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
  }

  /**
   * Method under test:
   * {@link SharedExecutorServiceAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  public void testRemoveTenantAsyncExecutor7() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        tenantInfoHolder);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = mock(CommandConfig.class);
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandInvoker()));
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
  }

  /**
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#start()}
   */
  @Test
  public void testStart() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());

    // Act
    sharedExecutorServiceAsyncExecutor.start();

    // Assert
    ExecutorService executorService = sharedExecutorServiceAsyncExecutor.getExecutorService();
    assertTrue(executorService instanceof ThreadPoolExecutor);
    ThreadFactory threadFactory = ((ThreadPoolExecutor) executorService).getThreadFactory();
    assertTrue(threadFactory instanceof BasicThreadFactory);
    assertEquals("activiti-async-job-executor-thread-%d", ((BasicThreadFactory) threadFactory).getNamingPattern());
    assertNull(((BasicThreadFactory) threadFactory).getDaemonFlag());
    assertNull(((BasicThreadFactory) threadFactory).getPriority());
    assertNull(((BasicThreadFactory) threadFactory).getUncaughtExceptionHandler());
    assertEquals(0, ((ThreadPoolExecutor) executorService).getActiveCount());
    assertEquals(0, ((ThreadPoolExecutor) executorService).getLargestPoolSize());
    assertEquals(0, ((ThreadPoolExecutor) executorService).getPoolSize());
    assertEquals(0L, ((ThreadPoolExecutor) executorService).getCompletedTaskCount());
    assertEquals(0L, ((ThreadPoolExecutor) executorService).getTaskCount());
    assertEquals(0L, ((BasicThreadFactory) threadFactory).getThreadCount());
    assertEquals(10, ((ThreadPoolExecutor) executorService).getMaximumPoolSize());
    assertEquals(2, ((ThreadPoolExecutor) executorService).getCorePoolSize());
    BlockingQueue<Runnable> threadPoolQueue = sharedExecutorServiceAsyncExecutor.getThreadPoolQueue();
    assertTrue(threadPoolQueue.isEmpty());
    assertTrue(sharedExecutorServiceAsyncExecutor.isActive());
    assertSame(threadPoolQueue, ((ThreadPoolExecutor) executorService).getQueue());
  }

  /**
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#start()}
   */
  @Test
  public void testStart2() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());
    ForkJoinPool executorService = ForkJoinPool.commonPool();
    sharedExecutorServiceAsyncExecutor.setExecutorService(executorService);

    // Act
    sharedExecutorServiceAsyncExecutor.start();

    // Assert
    ExecutorService executorService2 = sharedExecutorServiceAsyncExecutor.getExecutorService();
    assertTrue(executorService2 instanceof ForkJoinPool);
    assertTrue(sharedExecutorServiceAsyncExecutor.getThreadPoolQueue().isEmpty());
    assertTrue(sharedExecutorServiceAsyncExecutor.isActive());
    assertSame(executorService, executorService2);
  }

  /**
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#start()}
   */
  @Test
  public void testStart3() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setExecuteAsyncRunnableFactory(mock(ExecuteAsyncRunnableFactory.class));

    // Act
    sharedExecutorServiceAsyncExecutor.start();

    // Assert
    ExecutorService executorService = sharedExecutorServiceAsyncExecutor.getExecutorService();
    assertTrue(executorService instanceof ThreadPoolExecutor);
    ThreadFactory threadFactory = ((ThreadPoolExecutor) executorService).getThreadFactory();
    assertTrue(threadFactory instanceof BasicThreadFactory);
    assertEquals("activiti-async-job-executor-thread-%d", ((BasicThreadFactory) threadFactory).getNamingPattern());
    assertNull(((BasicThreadFactory) threadFactory).getDaemonFlag());
    assertNull(((BasicThreadFactory) threadFactory).getPriority());
    assertNull(((BasicThreadFactory) threadFactory).getUncaughtExceptionHandler());
    assertEquals(0, ((ThreadPoolExecutor) executorService).getActiveCount());
    assertEquals(0, ((ThreadPoolExecutor) executorService).getLargestPoolSize());
    assertEquals(0, ((ThreadPoolExecutor) executorService).getPoolSize());
    assertEquals(0L, ((ThreadPoolExecutor) executorService).getCompletedTaskCount());
    assertEquals(0L, ((ThreadPoolExecutor) executorService).getTaskCount());
    assertEquals(0L, ((BasicThreadFactory) threadFactory).getThreadCount());
    assertEquals(10, ((ThreadPoolExecutor) executorService).getMaximumPoolSize());
    assertEquals(2, ((ThreadPoolExecutor) executorService).getCorePoolSize());
    BlockingQueue<Runnable> threadPoolQueue = sharedExecutorServiceAsyncExecutor.getThreadPoolQueue();
    assertTrue(threadPoolQueue.isEmpty());
    assertTrue(sharedExecutorServiceAsyncExecutor.isActive());
    assertSame(threadPoolQueue, ((ThreadPoolExecutor) executorService).getQueue());
  }

  /**
   * Method under test:
   * {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}
   */
  @Test
  public void testStopJobAcquisitionThread() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());

    // Act
    sharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread();

    // Assert that nothing has changed
    assertTrue(sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables.isEmpty());
  }

  /**
   * Method under test:
   * {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}
   */
  @Test
  public void testStopJobAcquisitionThread2() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setExecuteAsyncRunnableFactory(mock(ExecuteAsyncRunnableFactory.class));

    // Act
    sharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread();

    // Assert that nothing has changed
    assertTrue(sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables.isEmpty());
  }

  /**
   * Method under test:
   * {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}
   */
  @Test
  public void testStopThreadsForTenant() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopThreadsForTenant("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
  }

  /**
   * Method under test:
   * {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}
   */
  @Test
  public void testStopThreadsForTenant2() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(new StandaloneInMemProcessEngineConfiguration());
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopThreadsForTenant("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
  }

  /**
   * Method under test:
   * {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}
   */
  @Test
  public void testStopThreadsForTenant3() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopThreadsForTenant("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
  }

  /**
   * Method under test:
   * {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}
   */
  @Test
  public void testStopThreadsForTenant4() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        tenantInfoHolder);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager())
        .thenReturn(new DefaultJobManager(new JtaProcessEngineConfiguration()));
    CommandConfig defaultConfig = new CommandConfig();
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopThreadsForTenant("42");

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
  }

  /**
   * Method under test:
   * {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}
   */
  @Test
  public void testStopThreadsForTenant5() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        tenantInfoHolder);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(mock(JobManager.class));
    CommandConfig defaultConfig = new CommandConfig();
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopThreadsForTenant("42");

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
  }

  /**
   * Method under test:
   * {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}
   */
  @Test
  public void testStopThreadsForTenant6() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = new CommandConfig(true);
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopThreadsForTenant("42");

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
  }

  /**
   * Method under test:
   * {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}
   */
  @Test
  public void testStopThreadsForTenant7() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = new CommandConfig();
    CommandContextFactory commandContextFactory = new CommandContextFactory();
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(new CommandExecutorImpl(defaultConfig,
        new CommandContextInterceptor(commandContextFactory, new JtaProcessEngineConfiguration())));

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopThreadsForTenant("42");

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
  }

  /**
   * Method under test:
   * {@link SharedExecutorServiceAsyncExecutor#SharedExecutorServiceAsyncExecutor(TenantInfoHolder)}
   */
  @Test
  public void testNewSharedExecutorServiceAsyncExecutor() {
    // Arrange and Act
    SharedExecutorServiceAsyncExecutor actualSharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());

    // Assert
    TenantInfoHolder tenantInfoHolder = actualSharedExecutorServiceAsyncExecutor.tenantInfoHolder;
    Collection<String> allTenants = tenantInfoHolder.getAllTenants();
    assertTrue(allTenants instanceof Set);
    assertTrue(tenantInfoHolder instanceof DummyTenantInfoHolder);
    assertNull(tenantInfoHolder.getCurrentTenantId());
    assertNull(((DummyTenantInfoHolder) tenantInfoHolder).getCurrentUserId());
    assertNull(actualSharedExecutorServiceAsyncExecutor.getAsyncJobAcquisitionThread());
    assertNull(actualSharedExecutorServiceAsyncExecutor.getResetExpiredJobThread());
    assertNull(actualSharedExecutorServiceAsyncExecutor.getTimerJobAcquisitionThread());
    assertNull(actualSharedExecutorServiceAsyncExecutor.getThreadPoolQueue());
    assertNull(actualSharedExecutorServiceAsyncExecutor.getExecutorService());
    assertNull(actualSharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration());
    assertEquals(0, actualSharedExecutorServiceAsyncExecutor.getDefaultQueueSizeFullWaitTimeInMillis());
    assertEquals(1, actualSharedExecutorServiceAsyncExecutor.getMaxAsyncJobsDuePerAcquisition());
    assertEquals(1, actualSharedExecutorServiceAsyncExecutor.getMaxTimerJobsPerAcquisition());
    assertEquals(10, actualSharedExecutorServiceAsyncExecutor.getMaxPoolSize());
    assertEquals(100, actualSharedExecutorServiceAsyncExecutor.getQueueSize());
    assertEquals(10000, actualSharedExecutorServiceAsyncExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis());
    assertEquals(10000, actualSharedExecutorServiceAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis());
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
    assertTrue(allTenants.isEmpty());
    assertTrue(actualSharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables.isEmpty());
    assertTrue(actualSharedExecutorServiceAsyncExecutor.asyncJobAcquisitionThreads.isEmpty());
    assertTrue(actualSharedExecutorServiceAsyncExecutor.resetExpiredJobsRunnables.isEmpty());
    assertTrue(actualSharedExecutorServiceAsyncExecutor.resetExpiredJobsThreads.isEmpty());
    assertTrue(actualSharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables.isEmpty());
    assertTrue(actualSharedExecutorServiceAsyncExecutor.timerJobAcquisitionThreads.isEmpty());
    assertTrue(actualSharedExecutorServiceAsyncExecutor.getTenantIds().isEmpty());
  }
}
