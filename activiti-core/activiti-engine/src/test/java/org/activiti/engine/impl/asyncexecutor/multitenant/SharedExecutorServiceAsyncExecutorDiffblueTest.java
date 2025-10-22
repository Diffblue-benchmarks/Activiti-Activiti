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
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadPoolExecutor;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.impl.asyncexecutor.DefaultJobManager;
import org.activiti.engine.impl.asyncexecutor.JobManager;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.TransactionPropagation;
import org.activiti.engine.impl.cfg.multitenant.TenantInfoHolder;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.CommandInvoker;
import org.activiti.engine.test.cfg.multitenant.DummyTenantInfoHolder;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class SharedExecutorServiceAsyncExecutorDiffblueTest {
  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#SharedExecutorServiceAsyncExecutor(TenantInfoHolder)}.
   * <p>
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#SharedExecutorServiceAsyncExecutor(TenantInfoHolder)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.<init>(TenantInfoHolder)"})
  public void testNewSharedExecutorServiceAsyncExecutor() {
    // Arrange and Act
    SharedExecutorServiceAsyncExecutor actualSharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());

    // Assert
    assertTrue(actualSharedExecutorServiceAsyncExecutor.tenantInfoHolder instanceof DummyTenantInfoHolder);
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.util.Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds() {
    // Arrange, Act and Assert
    assertTrue((new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder())).getTenantIds().isEmpty());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#removeTenantAsyncExecutor(String)}.
   * <p>
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        null);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert that nothing has changed
    Map<String, TenantAwareAcquireAsyncJobsDueRunnable> stringTenantAwareAcquireAsyncJobsDueRunnableMap = sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireAsyncJobsDueRunnableMap.size());
    assertEquals(0L, stringTenantAwareAcquireAsyncJobsDueRunnableMap.get("42").getMillisToWait());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#removeTenantAsyncExecutor(String)}.
   * <p>
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.removeTenantAsyncExecutor(String)"})
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
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#removeTenantAsyncExecutor(String)}.
   * <p>
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor3() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
   * Test {@link SharedExecutorServiceAsyncExecutor#removeTenantAsyncExecutor(String)}.
   * <p>
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor4() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(mock(JobManager.class));
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
   * Test {@link SharedExecutorServiceAsyncExecutor#removeTenantAsyncExecutor(String)}.
   * <p>
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#removeTenantAsyncExecutor(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.removeTenantAsyncExecutor(String)"})
  public void testRemoveTenantAsyncExecutor5() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        tenantInfoHolder);
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = new CommandConfig(true, TransactionPropagation.REQUIRED);

    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));
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
   * Test {@link SharedExecutorServiceAsyncExecutor#start()}.
   * <p>
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#start()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.start()"})
  public void testStart() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());

    // Act
    sharedExecutorServiceAsyncExecutor.start();

    // Assert
    ExecutorService executorService = sharedExecutorServiceAsyncExecutor.getExecutorService();
    assertTrue(executorService instanceof ThreadPoolExecutor);
    assertTrue(((ThreadPoolExecutor) executorService).getThreadFactory() instanceof BasicThreadFactory);
    assertEquals(0, ((ThreadPoolExecutor) executorService).getActiveCount());
    assertEquals(0, ((ThreadPoolExecutor) executorService).getLargestPoolSize());
    assertEquals(0, ((ThreadPoolExecutor) executorService).getPoolSize());
    assertEquals(0L, ((ThreadPoolExecutor) executorService).getCompletedTaskCount());
    assertEquals(0L, ((ThreadPoolExecutor) executorService).getTaskCount());
    assertEquals(10, ((ThreadPoolExecutor) executorService).getMaximumPoolSize());
    assertEquals(2, ((ThreadPoolExecutor) executorService).getCorePoolSize());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#start()}.
   * <p>
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#start()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.start()"})
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
   * Test {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}.
   * <p>
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread()"})
  public void testStopJobAcquisitionThread() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        null);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread();

    // Assert that nothing has changed
    Map<String, TenantAwareAcquireAsyncJobsDueRunnable> stringTenantAwareAcquireAsyncJobsDueRunnableMap = sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireAsyncJobsDueRunnableMap.size());
    assertEquals(0L, stringTenantAwareAcquireAsyncJobsDueRunnableMap.get("42").getMillisToWait());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}.
   * <p>
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread()"})
  public void testStopJobAcquisitionThread2() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    ProcessEngineConfigurationImpl processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = mock(CommandConfig.class);
    when(processEngineConfiguration.getCommandExecutor())
        .thenReturn(new CommandExecutorImpl(defaultConfig, new CommandInvoker()));

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread();

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    assertEquals(1, sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables.size());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}.
   * <p>
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.stopThreadsForTenant(String)"})
  public void testStopThreadsForTenant() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        null);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(new JtaProcessEngineConfiguration());
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopThreadsForTenant("42");

    // Assert that nothing has changed
    Map<String, TenantAwareAcquireAsyncJobsDueRunnable> stringTenantAwareAcquireAsyncJobsDueRunnableMap = sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireAsyncJobsDueRunnableMap.size());
    assertEquals(0L, stringTenantAwareAcquireAsyncJobsDueRunnableMap.get("42").getMillisToWait());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}.
   * <p>
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.stopThreadsForTenant(String)"})
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

    // Assert that nothing has changed
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
    assertEquals(1, sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables.size());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}.
   * <p>
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.stopThreadsForTenant(String)"})
  public void testStopThreadsForTenant3() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(new StandaloneProcessEngineConfiguration());
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopThreadsForTenant("42");

    // Assert
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId(eq("42"));
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}.
   * <p>
   * Method under test: {@link SharedExecutorServiceAsyncExecutor#stopThreadsForTenant(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.stopThreadsForTenant(String)"})
  public void testStopThreadsForTenant4() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
}
