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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import javax.sql.DataSource;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.impl.asyncexecutor.AcquireTimerJobsRunnable;
import org.activiti.engine.impl.asyncexecutor.AcquiredTimerJobEntities;
import org.activiti.engine.impl.asyncexecutor.DefaultAsyncJobExecutor;
import org.activiti.engine.impl.asyncexecutor.DefaultJobManager;
import org.activiti.engine.impl.asyncexecutor.ResetExpiredJobsRunnable;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.multitenant.TenantInfoHolder;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.impl.persistence.entity.JobEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionInfoEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisJobDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisProcessDefinitionInfoDataManager;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.test.cfg.multitenant.DummyTenantInfoHolder;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
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
  @MethodsUnderTest({"java.util.Set SharedExecutorServiceAsyncExecutor.getTenantIds()"})
  public void testGetTenantIds() {
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
    assertNull(stringTenantAwareAcquireTimerJobsRunnableMap.get("42").tenantInfoHolder);
    Map<String, TenantAwareResetExpiredJobsRunnable> stringTenantAwareResetExpiredJobsRunnableMap =
        sharedExecutorServiceAsyncExecutor.resetExpiredJobsRunnables;
    assertEquals(1, stringTenantAwareResetExpiredJobsRunnableMap.size());
    assertNull(stringTenantAwareResetExpiredJobsRunnableMap.get("42").tenantInfoHolder);
    assertEquals(0L, getResult.getMillisToWait());
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
    processEngineConfiguration.setCustomPreVariableTypes(new ArrayList<>());

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
    assertNull(stringTenantAwareAcquireTimerJobsRunnableMap.get("42").tenantInfoHolder);
    Map<String, TenantAwareResetExpiredJobsRunnable> stringTenantAwareResetExpiredJobsRunnableMap =
        sharedExecutorServiceAsyncExecutor.resetExpiredJobsRunnables;
    assertEquals(1, stringTenantAwareResetExpiredJobsRunnableMap.size());
    assertNull(stringTenantAwareResetExpiredJobsRunnableMap.get("42").tenantInfoHolder);
    assertEquals(0L, getResult.getMillisToWait());
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
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    ProcessDefinitionInfoEntityManagerImpl processDefinitionInfoEntityManager =
        new ProcessDefinitionInfoEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisProcessDefinitionInfoDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setProcessDefinitionInfoEntityManager(
        processDefinitionInfoEntityManager);

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
    assertNull(stringTenantAwareAcquireTimerJobsRunnableMap.get("42").tenantInfoHolder);
    Map<String, TenantAwareResetExpiredJobsRunnable> stringTenantAwareResetExpiredJobsRunnableMap =
        sharedExecutorServiceAsyncExecutor.resetExpiredJobsRunnables;
    assertEquals(1, stringTenantAwareResetExpiredJobsRunnableMap.size());
    assertNull(stringTenantAwareResetExpiredJobsRunnableMap.get("42").tenantInfoHolder);
    assertEquals(0L, getResult.getMillisToWait());
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
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
    processEngineConfiguration.setIdGeneratorDataSource(mock(DataSource.class));
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

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
  public void testRemoveTenantAsyncExecutor4() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutorImpl = new CommandExecutorImpl(new CommandConfig(), first);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    when(processEngineConfiguration.addCustomFunctionProvider(
            Mockito.<CustomFunctionProvider>any()))
        .thenReturn(new JtaProcessEngineConfiguration());
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);
    processEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setAsyncJobAcquisitionThread(new Thread());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.removeTenantAsyncExecutor("42");

    // Assert
    verify(processEngineConfiguration).addCustomFunctionProvider(isA(CustomFunctionProvider.class));
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(1, sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables.size());
    assertEquals(1, sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables.size());
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
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(
        new StandaloneInMemProcessEngineConfiguration());
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

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setResetExpiredJobsRunnable(
        new ResetExpiredJobsRunnable(new DefaultAsyncJobExecutor()));
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
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
  public void testStopJobAcquisitionThread5() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    JtaProcessEngineConfiguration processEngineConfiguration2 = new JtaProcessEngineConfiguration();
    JobEntityManagerImpl jobEntityManager =
        new JobEntityManagerImpl(
            processEngineConfiguration2,
            new MybatisJobDataManager(new JtaProcessEngineConfiguration()));
    processEngineConfiguration.setJobEntityManager(jobEntityManager);

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
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
  public void testStopJobAcquisitionThread6() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setDatabaseSchema("{} starting to reset expired jobs");

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
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
  public void testStopJobAcquisitionThread7() {
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
  public void testStopJobAcquisitionThread8() {
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
    DefaultAsyncJobExecutor asyncExecutor = new DefaultAsyncJobExecutor();
    AcquireTimerJobsRunnable timerJobRunnable =
        new AcquireTimerJobsRunnable(asyncExecutor, new DefaultJobManager());
    sharedExecutorServiceAsyncExecutor.setTimerJobRunnable(timerJobRunnable);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread();

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
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
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor());
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("Tenant Id", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread();

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("Tenant Id");
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

    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutorImpl = new CommandExecutorImpl(new CommandConfig(), first);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
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

    CommandInterceptor first = mock(CommandInterceptor.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenReturn(JSONObject.NULL);
    CommandExecutorImpl commandExecutorImpl = new CommandExecutorImpl(new CommandConfig(), first);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setResetExpiredJobsInterval(42);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread();

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
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
  public void testStopJobAcquisitionThread12() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    AcquiredTimerJobEntities acquiredTimerJobEntities = mock(AcquiredTimerJobEntities.class);
    when(acquiredTimerJobEntities.size()).thenReturn(3);

    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    when(commandExecutor.execute(Mockito.<Command<Object>>any()))
        .thenReturn(acquiredTimerJobEntities);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutor);

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setMaxPoolSize(3);
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
  public void testStopJobAcquisitionThread13() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    AcquiredTimerJobEntities acquiredTimerJobEntities = mock(AcquiredTimerJobEntities.class);
    when(acquiredTimerJobEntities.size()).thenReturn(3);

    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    when(commandExecutor.execute(Mockito.<Command<Object>>any()))
        .thenReturn(acquiredTimerJobEntities);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutor);

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setAsyncJobAcquisitionThread(new Thread());
    sharedExecutorServiceAsyncExecutor.setTimerJobAcquisitionThread(null);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread();

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
   * Test {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}.
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread()"})
  public void testStopJobAcquisitionThread14() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    AcquiredTimerJobEntities acquiredTimerJobEntities = mock(AcquiredTimerJobEntities.class);
    when(acquiredTimerJobEntities.size()).thenReturn(3);

    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    when(commandExecutor.execute(Mockito.<Command<Object>>any()))
        .thenReturn(acquiredTimerJobEntities);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutor);

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setTimerJobAcquisitionThread(new Thread());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor(
        "{} stopped resetting expired jobs", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread();

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("{} stopped resetting expired jobs");
    Map<String, TenantAwareAcquireTimerJobsRunnable> stringTenantAwareAcquireTimerJobsRunnableMap =
        sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables;
    assertEquals(1, stringTenantAwareAcquireTimerJobsRunnableMap.size());
    assertEquals(
        0L,
        stringTenantAwareAcquireTimerJobsRunnableMap
            .get("{} stopped resetting expired jobs")
            .getMillisToWait());
    assertEquals(1, sharedExecutorServiceAsyncExecutor.asyncJobAcquisitionRunnables.size());
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}.
   *
   * <ul>
   *   <li>Given {@link CommandExecutor} {@link CommandExecutor#execute(Command)} return {@link
   *       JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread()"})
  public void testStopJobAcquisitionThread_givenCommandExecutorExecuteReturnNull() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    when(commandExecutor.execute(Mockito.<Command<Object>>any())).thenReturn(JSONObject.NULL);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutor);

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
   * <ul>
   *   <li>Given {@link CommandExecutor} {@link CommandExecutor#execute(Command)} return {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread()"})
  public void testStopJobAcquisitionThread_givenCommandExecutorExecuteReturnNull2() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    when(commandExecutor.execute(Mockito.<Command<Object>>any())).thenReturn(null);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutor);

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
  }

  /**
   * Test {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}.
   *
   * <ul>
   *   <li>Given {@link CommandExecutor} {@link CommandExecutor#execute(Command)} return {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link SharedExecutorServiceAsyncExecutor#stopJobAcquisitionThread()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SharedExecutorServiceAsyncExecutor.stopJobAcquisitionThread()"})
  public void testStopJobAcquisitionThread_givenCommandExecutorExecuteReturnNull3() {
    // Arrange
    TenantInfoHolder tenantInfoHolder = mock(TenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());

    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    when(commandExecutor.execute(Mockito.<Command<Object>>any())).thenReturn(null);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutor);

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
    DummyTenantInfoHolder tenantInfoHolder = mock(DummyTenantInfoHolder.class);
    doNothing().when(tenantInfoHolder).addTenant(Mockito.<String>any());
    doNothing().when(tenantInfoHolder).clearCurrentTenantId();
    doNothing().when(tenantInfoHolder).setCurrentTenantId(Mockito.<String>any());
    tenantInfoHolder.addTenant("{} stopped async job due acquisition");

    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    when(commandExecutor.execute(Mockito.<Command<Object>>any())).thenReturn(42);

    ProcessEngineConfigurationImpl processEngineConfiguration =
        mock(ProcessEngineConfigurationImpl.class);
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutor);

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(tenantInfoHolder);
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);

    // Act
    sharedExecutorServiceAsyncExecutor.stopThreadsForTenant("42");

    // Assert
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(processEngineConfiguration).getJobManager();
    verify(tenantInfoHolder).addTenant("{} stopped async job due acquisition");
    verify(tenantInfoHolder, atLeast(1)).clearCurrentTenantId();
    verify(tenantInfoHolder, atLeast(1)).setCurrentTenantId("42");
    assertEquals(1, sharedExecutorServiceAsyncExecutor.timerJobAcquisitionRunnables.size());
  }
}
