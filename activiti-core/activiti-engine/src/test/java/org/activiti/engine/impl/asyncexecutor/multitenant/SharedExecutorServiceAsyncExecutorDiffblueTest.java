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
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import org.activiti.engine.impl.asyncexecutor.DefaultJobManager;
import org.activiti.engine.impl.asyncexecutor.ExecuteAsyncRunnableFactory;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.multitenant.TenantInfoHolder;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.impl.persistence.entity.TimerJobEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisTimerJobDataManager;
import org.activiti.engine.test.cfg.multitenant.DummyTenantInfoHolder;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.junit.Test;
import org.mockito.Mockito;

public class SharedExecutorServiceAsyncExecutorDiffblueTest {
  /**
   * Test
   * {@link SharedExecutorServiceAsyncExecutor#SharedExecutorServiceAsyncExecutor(TenantInfoHolder)}.
   * <p>
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

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   * <p>
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}
   */
  @Test
  public void testGetTenantIds() {
    // Arrange, Act and Assert
    assertTrue((new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder())).getTenantIds().isEmpty());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#getTenantIds()}.
   * <p>
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
   * Test
   * {@link SharedExecutorServiceAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.
   * <p>
   * Method under test:
   * {@link SharedExecutorServiceAsyncExecutor#addTenantAsyncExecutor(String, boolean)}
   */
  @Test
  public void testAddTenantAsyncExecutor() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        null);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());

    // Act
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    assertTrue(
        sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration() instanceof JtaProcessEngineConfiguration);
    Map<String, TenantAwareAcquireAsyncJobsDueRunnable> stringTenantAwareAcquireAsyncJobsDueRunnableMap = sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireAsyncJobsDueRunnableMap.size());
    assertNull(stringTenantAwareAcquireAsyncJobsDueRunnableMap.get("42").tenantInfoHolder);
    Map<String, TenantAwareAcquireTimerJobsRunnable> stringTenantAwareAcquireTimerJobsRunnableMap = sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireTimerJobsRunnableMap.size());
    assertNull(stringTenantAwareAcquireTimerJobsRunnableMap.get("42").tenantInfoHolder);
    Map<String, TenantAwareResetExpiredJobsRunnable> stringTenantAwareResetExpiredJobsRunnableMap = sharedExecutorServiceAsyncExecutor.resetExpiredJobsRunnables;
    assertEquals(1, stringTenantAwareResetExpiredJobsRunnableMap.size());
    assertNull(stringTenantAwareResetExpiredJobsRunnableMap.get("42").tenantInfoHolder);
  }

  /**
   * Test
   * {@link SharedExecutorServiceAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.
   * <p>
   * Method under test:
   * {@link SharedExecutorServiceAsyncExecutor#addTenantAsyncExecutor(String, boolean)}
   */
  @Test
  public void testAddTenantAsyncExecutor2() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    processEngineConfiguration
        .setCommandExecutor(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    assertTrue(
        sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration() instanceof JtaProcessEngineConfiguration);
    Map<String, TenantAwareAcquireAsyncJobsDueRunnable> stringTenantAwareAcquireAsyncJobsDueRunnableMap = sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireAsyncJobsDueRunnableMap.size());
    Map<String, TenantAwareResetExpiredJobsRunnable> stringTenantAwareResetExpiredJobsRunnableMap = sharedExecutorServiceAsyncExecutor.resetExpiredJobsRunnables;
    assertEquals(1, stringTenantAwareResetExpiredJobsRunnableMap.size());
    Map<String, TenantAwareAcquireTimerJobsRunnable> stringTenantAwareAcquireTimerJobsRunnableMap = sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireTimerJobsRunnableMap.size());
    TenantInfoHolder tenantInfoHolder = sharedExecutorServiceAsyncExecutor.tenantInfoHolder;
    assertSame(tenantInfoHolder, stringTenantAwareAcquireAsyncJobsDueRunnableMap.get("42").tenantInfoHolder);
    assertSame(tenantInfoHolder, stringTenantAwareAcquireTimerJobsRunnableMap.get("42").tenantInfoHolder);
    assertSame(tenantInfoHolder, stringTenantAwareResetExpiredJobsRunnableMap.get("42").tenantInfoHolder);
  }

  /**
   * Test
   * {@link SharedExecutorServiceAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.
   * <p>
   * Method under test:
   * {@link SharedExecutorServiceAsyncExecutor#addTenantAsyncExecutor(String, boolean)}
   */
  @Test
  public void testAddTenantAsyncExecutor3() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setTimerJobEntityManager(new TimerJobEntityManagerImpl(processEngineConfiguration2,
        new MybatisTimerJobDataManager(new JtaProcessEngineConfiguration())));

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    assertTrue(
        sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration() instanceof JtaProcessEngineConfiguration);
    Map<String, TenantAwareAcquireAsyncJobsDueRunnable> stringTenantAwareAcquireAsyncJobsDueRunnableMap = sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireAsyncJobsDueRunnableMap.size());
    Map<String, TenantAwareResetExpiredJobsRunnable> stringTenantAwareResetExpiredJobsRunnableMap = sharedExecutorServiceAsyncExecutor.resetExpiredJobsRunnables;
    assertEquals(1, stringTenantAwareResetExpiredJobsRunnableMap.size());
    Map<String, TenantAwareAcquireTimerJobsRunnable> stringTenantAwareAcquireTimerJobsRunnableMap = sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireTimerJobsRunnableMap.size());
    TenantInfoHolder tenantInfoHolder = sharedExecutorServiceAsyncExecutor.tenantInfoHolder;
    assertSame(tenantInfoHolder, stringTenantAwareAcquireAsyncJobsDueRunnableMap.get("42").tenantInfoHolder);
    assertSame(tenantInfoHolder, stringTenantAwareAcquireTimerJobsRunnableMap.get("42").tenantInfoHolder);
    assertSame(tenantInfoHolder, stringTenantAwareResetExpiredJobsRunnableMap.get("42").tenantInfoHolder);
  }

  /**
   * Test
   * {@link SharedExecutorServiceAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.
   * <p>
   * Method under test:
   * {@link SharedExecutorServiceAsyncExecutor#addTenantAsyncExecutor(String, boolean)}
   */
  @Test
  public void testAddTenantAsyncExecutor4() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setAsyncExecutorMaxAsyncJobsDuePerAcquisition(1);

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Assert
    assertTrue(
        sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration() instanceof JtaProcessEngineConfiguration);
    Map<String, TenantAwareAcquireAsyncJobsDueRunnable> stringTenantAwareAcquireAsyncJobsDueRunnableMap = sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireAsyncJobsDueRunnableMap.size());
    Map<String, TenantAwareResetExpiredJobsRunnable> stringTenantAwareResetExpiredJobsRunnableMap = sharedExecutorServiceAsyncExecutor.resetExpiredJobsRunnables;
    assertEquals(1, stringTenantAwareResetExpiredJobsRunnableMap.size());
    Map<String, TenantAwareAcquireTimerJobsRunnable> stringTenantAwareAcquireTimerJobsRunnableMap = sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireTimerJobsRunnableMap.size());
    TenantInfoHolder tenantInfoHolder = sharedExecutorServiceAsyncExecutor.tenantInfoHolder;
    assertSame(tenantInfoHolder, stringTenantAwareAcquireAsyncJobsDueRunnableMap.get("42").tenantInfoHolder);
    assertSame(tenantInfoHolder, stringTenantAwareAcquireTimerJobsRunnableMap.get("42").tenantInfoHolder);
    assertSame(tenantInfoHolder, stringTenantAwareResetExpiredJobsRunnableMap.get("42").tenantInfoHolder);
  }

  /**
   * Test
   * {@link SharedExecutorServiceAsyncExecutor#addTenantAsyncExecutor(String, boolean)}.
   * <ul>
   *   <li>When {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SharedExecutorServiceAsyncExecutor#addTenantAsyncExecutor(String, boolean)}
   */
  @Test
  public void testAddTenantAsyncExecutor_whenFalse() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        null);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());

    // Act
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", false);

    // Assert
    assertTrue(
        sharedExecutorServiceAsyncExecutor.getProcessEngineConfiguration() instanceof JtaProcessEngineConfiguration);
    Map<String, TenantAwareAcquireAsyncJobsDueRunnable> stringTenantAwareAcquireAsyncJobsDueRunnableMap = sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireAsyncJobsDueRunnableMap.size());
    assertNull(stringTenantAwareAcquireAsyncJobsDueRunnableMap.get("42").tenantInfoHolder);
    Map<String, TenantAwareAcquireTimerJobsRunnable> stringTenantAwareAcquireTimerJobsRunnableMap = sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireTimerJobsRunnableMap.size());
    assertNull(stringTenantAwareAcquireTimerJobsRunnableMap.get("42").tenantInfoHolder);
    Map<String, TenantAwareResetExpiredJobsRunnable> stringTenantAwareResetExpiredJobsRunnableMap = sharedExecutorServiceAsyncExecutor.resetExpiredJobsRunnables;
    assertEquals(1, stringTenantAwareResetExpiredJobsRunnableMap.size());
    assertNull(stringTenantAwareResetExpiredJobsRunnableMap.get("42").tenantInfoHolder);
  }

  /**
   * Test
   * {@link SharedExecutorServiceAsyncExecutor#removeTenantAsyncExecutor(String)}.
   * <p>
   * Method under test:
   * {@link SharedExecutorServiceAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  public void testRemoveTenantAsyncExecutor() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        null);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    Map<String, TenantAwareAcquireAsyncJobsDueRunnable> stringTenantAwareAcquireAsyncJobsDueRunnableMap = sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireAsyncJobsDueRunnableMap.size());
    assertEquals(0L, stringTenantAwareAcquireAsyncJobsDueRunnableMap.get("42").getMillisToWait());
    Map<String, TenantAwareAcquireTimerJobsRunnable> stringTenantAwareAcquireTimerJobsRunnableMap = sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireTimerJobsRunnableMap.size());
    assertEquals(0L, stringTenantAwareAcquireTimerJobsRunnableMap.get("42").getMillisToWait());
  }

  /**
   * Test
   * {@link SharedExecutorServiceAsyncExecutor#removeTenantAsyncExecutor(String)}.
   * <p>
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
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    assertEquals(1, sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables.size());
    assertEquals(1, sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables.size());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#start()}.
   * <p>
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
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#start()}.
   * <p>
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
   * Test {@link SharedExecutorServiceAsyncExecutor#start()}.
   * <p>
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
  }

  /**
   * Test
   * {@link SharedExecutorServiceAsyncExecutor#startAsyncJobAcquisitionForTenant(String)}.
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getJobManager()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SharedExecutorServiceAsyncExecutor#startAsyncJobAcquisitionForTenant(String)}
   */
  @Test
  public void testStartAsyncJobAcquisitionForTenant_thenCallsGetJobManager() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        mock(TenantInfoHolder.class));
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", false);

    // Act
    sharedExecutorServiceAsyncExecutor.startAsyncJobAcquisitionForTenant("42");

    // Assert that nothing has changed
    verify(processEngineConfiguration).getJobManager();
  }

  /**
   * Test
   * {@link SharedExecutorServiceAsyncExecutor#startResetExpiredJobsForTenant(String)}.
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getJobManager()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SharedExecutorServiceAsyncExecutor#startResetExpiredJobsForTenant(String)}
   */
  @Test
  public void testStartResetExpiredJobsForTenant_thenCallsGetJobManager() {
    // Arrange
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        mock(TenantInfoHolder.class));
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", false);

    // Act
    sharedExecutorServiceAsyncExecutor.startResetExpiredJobsForTenant("42");

    // Assert that nothing has changed
    verify(processEngineConfiguration).getJobManager();
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}.
   * <p>
   * Method under test:
   * {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}
   */
  @Test
  public void testStopJobAcquisitionThread() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        null);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread();

    // Assert
    Map<String, TenantAwareAcquireAsyncJobsDueRunnable> stringTenantAwareAcquireAsyncJobsDueRunnableMap = sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireAsyncJobsDueRunnableMap.size());
    assertEquals(0L, stringTenantAwareAcquireAsyncJobsDueRunnableMap.get("42").getMillisToWait());
    Map<String, TenantAwareAcquireTimerJobsRunnable> stringTenantAwareAcquireTimerJobsRunnableMap = sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireTimerJobsRunnableMap.size());
    assertEquals(0L, stringTenantAwareAcquireTimerJobsRunnableMap.get("42").getMillisToWait());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}.
   * <p>
   * Method under test:
   * {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}
   */
  @Test
  public void testStopJobAcquisitionThread2() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        null);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(new StandaloneInMemProcessEngineConfiguration());
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread();

    // Assert
    Map<String, TenantAwareAcquireAsyncJobsDueRunnable> stringTenantAwareAcquireAsyncJobsDueRunnableMap = sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireAsyncJobsDueRunnableMap.size());
    assertEquals(0L, stringTenantAwareAcquireAsyncJobsDueRunnableMap.get("42").getMillisToWait());
    Map<String, TenantAwareAcquireTimerJobsRunnable> stringTenantAwareAcquireTimerJobsRunnableMap = sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireTimerJobsRunnableMap.size());
    assertEquals(0L, stringTenantAwareAcquireTimerJobsRunnableMap.get("42").getMillisToWait());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}.
   * <p>
   * Method under test:
   * {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}
   */
  @Test
  public void testStopThreadsForTenant() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        null);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopThreadsForTenant("42");

    // Assert
    Map<String, TenantAwareAcquireAsyncJobsDueRunnable> stringTenantAwareAcquireAsyncJobsDueRunnableMap = sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireAsyncJobsDueRunnableMap.size());
    assertEquals(0L, stringTenantAwareAcquireAsyncJobsDueRunnableMap.get("42").getMillisToWait());
    Map<String, TenantAwareAcquireTimerJobsRunnable> stringTenantAwareAcquireTimerJobsRunnableMap = sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireTimerJobsRunnableMap.size());
    assertEquals(0L, stringTenantAwareAcquireTimerJobsRunnableMap.get("42").getMillisToWait());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}.
   * <p>
   * Method under test:
   * {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}
   */
  @Test
  public void testStopThreadsForTenant2() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(new CommandConfig(), mock(CommandInterceptor.class)));

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
    assertEquals(1, sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables.size());
    assertEquals(1, sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables.size());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}.
   * <p>
   * Method under test:
   * {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}
   */
  @Test
  public void testStopThreadsForTenant3() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(new CommandConfig(), mock(CommandInterceptor.class)));

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
   * Test {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}.
   * <p>
   * Method under test:
   * {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}
   */
  @Test
  public void testStopThreadsForTenant4() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(null);
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(null);

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
    assertEquals(1, sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables.size());
    assertEquals(1, sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables.size());
  }
}
