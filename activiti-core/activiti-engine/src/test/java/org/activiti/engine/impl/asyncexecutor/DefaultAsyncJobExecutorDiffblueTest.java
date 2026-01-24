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
package org.activiti.engine.impl.asyncexecutor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import ch.qos.logback.classic.net.SimpleSSLSocketServer;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.enterprise.concurrent.ManagedThreadFactory;
import javax.xml.namespace.QName;
import org.activiti.engine.impl.asyncexecutor.multitenant.SharedExecutorServiceAsyncExecutor;
import org.activiti.engine.impl.asyncexecutor.multitenant.TenantAwareExecuteAsyncRunnable;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.multitenant.MultiSchemaMultiTenantProcessEngineConfiguration;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandInvoker;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityImpl;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.test.cfg.multitenant.DummyTenantInfoHolder;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class DefaultAsyncJobExecutorDiffblueTest {
  /**
   * Test {@link DefaultAsyncJobExecutor#executeAsyncJob(Job)}.
   *
   * <ul>
   *   <li>Then {@link DefaultAsyncJobExecutor} (default constructor) {@link
   *       DefaultAsyncJobExecutor#temporaryJobQueue} Empty.
   * </ul>
   *
   * <p>Method under test: {@link DefaultAsyncJobExecutor#executeAsyncJob(Job)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DefaultAsyncJobExecutor.executeAsyncJob(Job)"})
  public void testExecuteAsyncJob_thenDefaultAsyncJobExecutorTemporaryJobQueueEmpty() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setMessageQueueMode(true);

    // Act
    boolean actualExecuteAsyncJobResult =
        defaultAsyncJobExecutor.executeAsyncJob(new DeadLetterJobEntityImpl());

    // Assert
    assertTrue(defaultAsyncJobExecutor.temporaryJobQueue.isEmpty());
    assertTrue(actualExecuteAsyncJobResult);
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#executeAsyncJob(Job)}.
   *
   * <ul>
   *   <li>Then {@link DefaultAsyncJobExecutor} (default constructor) {@link
   *       DefaultAsyncJobExecutor#temporaryJobQueue} size is one.
   * </ul>
   *
   * <p>Method under test: {@link DefaultAsyncJobExecutor#executeAsyncJob(Job)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DefaultAsyncJobExecutor.executeAsyncJob(Job)"})
  public void testExecuteAsyncJob_thenDefaultAsyncJobExecutorTemporaryJobQueueSizeIsOne() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    DeadLetterJobEntityImpl job = new DeadLetterJobEntityImpl();

    // Act
    boolean actualExecuteAsyncJobResult = defaultAsyncJobExecutor.executeAsyncJob(job);

    // Assert
    LinkedList<Job> jobList = defaultAsyncJobExecutor.temporaryJobQueue;
    assertEquals(1, jobList.size());
    assertTrue(actualExecuteAsyncJobResult);
    assertSame(job, jobList.get(0));
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#createRunnableForJob(Job)}.
   *
   * <p>Method under test: {@link DefaultAsyncJobExecutor#createRunnableForJob(Job)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Runnable DefaultAsyncJobExecutor.createRunnableForJob(Job)"})
  public void testCreateRunnableForJob() {
    // Arrange
    ExecuteAsyncRunnableFactory executeAsyncRunnableFactory =
        mock(ExecuteAsyncRunnableFactory.class);
    when(executeAsyncRunnableFactory.createExecuteAsyncRunnable(
            Mockito.<Job>any(), Mockito.<ProcessEngineConfigurationImpl>any()))
        .thenReturn(mock(Runnable.class));

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(
        new JtaProcessEngineConfiguration());
    sharedExecutorServiceAsyncExecutor.addTenantAsyncExecutor("42", true);
    sharedExecutorServiceAsyncExecutor.setExecuteAsyncRunnableFactory(executeAsyncRunnableFactory);

    // Act
    sharedExecutorServiceAsyncExecutor.createRunnableForJob(new DeadLetterJobEntityImpl());

    // Assert
    verify(executeAsyncRunnableFactory)
        .createExecuteAsyncRunnable(isA(Job.class), isA(ProcessEngineConfigurationImpl.class));
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#createRunnableForJob(Job)}.
   *
   * <ul>
   *   <li>Then calls {@link ExecuteAsyncRunnableFactory#createExecuteAsyncRunnable(Job,
   *       ProcessEngineConfigurationImpl)}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultAsyncJobExecutor#createRunnableForJob(Job)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Runnable DefaultAsyncJobExecutor.createRunnableForJob(Job)"})
  public void testCreateRunnableForJob_thenCallsCreateExecuteAsyncRunnable() {
    // Arrange
    ExecuteAsyncRunnableFactory executeAsyncRunnableFactory =
        mock(ExecuteAsyncRunnableFactory.class);
    when(executeAsyncRunnableFactory.createExecuteAsyncRunnable(
            Mockito.<Job>any(), Mockito.<ProcessEngineConfigurationImpl>any()))
        .thenReturn(mock(Runnable.class));

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setExecuteAsyncRunnableFactory(executeAsyncRunnableFactory);

    // Act
    sharedExecutorServiceAsyncExecutor.createRunnableForJob(new DeadLetterJobEntityImpl());

    // Assert
    verify(executeAsyncRunnableFactory).createExecuteAsyncRunnable(isA(Job.class), isNull());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#createRunnableForJob(Job)}.
   *
   * <ul>
   *   <li>Then return {@link ExecuteAsyncRunnable}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultAsyncJobExecutor#createRunnableForJob(Job)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Runnable DefaultAsyncJobExecutor.createRunnableForJob(Job)"})
  public void testCreateRunnableForJob_thenReturnExecuteAsyncRunnable() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();

    // Act
    Runnable actualCreateRunnableForJobResult =
        defaultAsyncJobExecutor.createRunnableForJob(new DeadLetterJobEntityImpl());

    // Assert
    assertTrue(actualCreateRunnableForJobResult instanceof ExecuteAsyncRunnable);
    assertTrue(
        ((ExecuteAsyncRunnable) actualCreateRunnableForJobResult).job
            instanceof DeadLetterJobEntityImpl);
    assertNull(((ExecuteAsyncRunnable) actualCreateRunnableForJobResult).jobId);
    assertNull(
        ((ExecuteAsyncRunnable) actualCreateRunnableForJobResult).processEngineConfiguration);
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#createRunnableForJob(Job)}.
   *
   * <ul>
   *   <li>Then return {@link TenantAwareExecuteAsyncRunnable}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultAsyncJobExecutor#createRunnableForJob(Job)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Runnable DefaultAsyncJobExecutor.createRunnableForJob(Job)"})
  public void testCreateRunnableForJob_thenReturnTenantAwareExecuteAsyncRunnable() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());

    // Act
    Runnable actualCreateRunnableForJobResult =
        sharedExecutorServiceAsyncExecutor.createRunnableForJob(new DeadLetterJobEntityImpl());

    // Assert
    assertTrue(actualCreateRunnableForJobResult instanceof TenantAwareExecuteAsyncRunnable);
    assertTrue(
        ((TenantAwareExecuteAsyncRunnable) actualCreateRunnableForJobResult).job
            instanceof DeadLetterJobEntityImpl);
    assertNull(((TenantAwareExecuteAsyncRunnable) actualCreateRunnableForJobResult).jobId);
    assertNull(
        ((TenantAwareExecuteAsyncRunnable) actualCreateRunnableForJobResult)
            .processEngineConfiguration);
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#start()}.
   *
   * <p>Method under test: {@link DefaultAsyncJobExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.start()"})
  public void testStart6() throws MalformedURLException {
    // Arrange
    StandaloneInMemProcessEngineConfiguration processEngineConfiguration =
        mock(StandaloneInMemProcessEngineConfiguration.class);
    when(processEngineConfiguration.addWsEndpointAddress(Mockito.<QName>any(), Mockito.<URL>any()))
        .thenReturn(new JtaProcessEngineConfiguration());
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("Starting up the default async job executor [{}]."),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    sharedExecutorServiceAsyncExecutor.start();

    // Assert
    verify(processEngineConfiguration).addWsEndpointAddress(isA(QName.class), isA(URL.class));
    ExecutorService executorService = sharedExecutorServiceAsyncExecutor.getExecutorService();
    assertTrue(executorService instanceof ThreadPoolExecutor);
    ThreadFactory threadFactory = ((ThreadPoolExecutor) executorService).getThreadFactory();
    assertTrue(threadFactory instanceof BasicThreadFactory);
    assertEquals(0, ((ThreadPoolExecutor) executorService).getLargestPoolSize());
    assertEquals(0L, ((ThreadPoolExecutor) executorService).getCompletedTaskCount());
    assertEquals(0L, ((ThreadPoolExecutor) executorService).getTaskCount());
    assertEquals(0L, ((BasicThreadFactory) threadFactory).getThreadCount());
    assertEquals(10, ((ThreadPoolExecutor) executorService).getMaximumPoolSize());
    assertEquals(2, ((ThreadPoolExecutor) executorService).getCorePoolSize());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#start()}.
   *
   * <p>Method under test: {@link DefaultAsyncJobExecutor#start()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.start()"})
  public void testStart7() throws MalformedURLException {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setResetExpiredJobsRunnable(
        new ResetExpiredJobsRunnable(new DefaultAsyncJobExecutor()));

    StandaloneInMemProcessEngineConfiguration processEngineConfiguration =
        mock(StandaloneInMemProcessEngineConfiguration.class);
    when(processEngineConfiguration.addWsEndpointAddress(Mockito.<QName>any(), Mockito.<URL>any()))
        .thenReturn(new JtaProcessEngineConfiguration());
    when(processEngineConfiguration.getJobManager()).thenReturn(new DefaultJobManager());
    CommandConfig defaultConfig = new CommandConfig();

    CommandInvoker first = mock(CommandInvoker.class);
    when(first.execute(Mockito.<CommandConfig>any(), Mockito.<Command<AcquiredJobEntities>>any()))
        .thenReturn(new AcquiredJobEntities());

    CommandExecutorImpl commandExecutorImpl = new CommandExecutorImpl(defaultConfig, first);
    when(processEngineConfiguration.getCommandExecutor()).thenReturn(commandExecutorImpl);
    processEngineConfiguration.addWsEndpointAddress(
        QName.valueOf("Starting up the default async job executor [{}]."),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());
    defaultAsyncJobExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    defaultAsyncJobExecutor.start();

    // Assert
    verify(processEngineConfiguration).addWsEndpointAddress(isA(QName.class), isA(URL.class));
    verify(processEngineConfiguration).getJobManager();
    verify(processEngineConfiguration, atLeast(1)).getCommandExecutor();
    verify(first, atLeast(1)).execute(isA(CommandConfig.class), Mockito.<Command<Object>>any());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#initAsyncJobExecutionThreadPool()}.
   *
   * <p>Method under test: {@link DefaultAsyncJobExecutor#initAsyncJobExecutionThreadPool()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.initAsyncJobExecutionThreadPool()"})
  public void testInitAsyncJobExecutionThreadPool() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();

    // Act
    defaultAsyncJobExecutor.initAsyncJobExecutionThreadPool();

    // Assert
    ExecutorService executorService = defaultAsyncJobExecutor.getExecutorService();
    assertTrue(executorService instanceof ThreadPoolExecutor);
    ThreadFactory threadFactory = ((ThreadPoolExecutor) executorService).getThreadFactory();
    assertTrue(threadFactory instanceof BasicThreadFactory);
    assertEquals(
        "activiti-async-job-executor-thread-%d",
        ((BasicThreadFactory) threadFactory).getNamingPattern());
    assertNull(((BasicThreadFactory) threadFactory).getDaemonFlag());
    assertNull(((BasicThreadFactory) threadFactory).getPriority());
    assertNull(((BasicThreadFactory) threadFactory).getUncaughtExceptionHandler());
    assertEquals(0L, ((BasicThreadFactory) threadFactory).getThreadCount());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#initAsyncJobExecutionThreadPool()}.
   *
   * <p>Method under test: {@link DefaultAsyncJobExecutor#initAsyncJobExecutionThreadPool()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.initAsyncJobExecutionThreadPool()"})
  public void testInitAsyncJobExecutionThreadPool2() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    LinkedBlockingDeque<Runnable> threadPoolQueue = new LinkedBlockingDeque<>();
    defaultAsyncJobExecutor.setThreadPoolQueue(threadPoolQueue);

    // Act
    defaultAsyncJobExecutor.initAsyncJobExecutionThreadPool();

    // Assert
    ExecutorService executorService = defaultAsyncJobExecutor.getExecutorService();
    assertTrue(executorService instanceof ThreadPoolExecutor);
    BlockingQueue<Runnable> threadPoolQueue2 = defaultAsyncJobExecutor.getThreadPoolQueue();
    assertTrue(threadPoolQueue2.isEmpty());
    assertSame(threadPoolQueue, ((ThreadPoolExecutor) executorService).getQueue());
    assertSame(threadPoolQueue, threadPoolQueue2);
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#initAsyncJobExecutionThreadPool()}.
   *
   * <p>Method under test: {@link DefaultAsyncJobExecutor#initAsyncJobExecutionThreadPool()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.initAsyncJobExecutionThreadPool()"})
  public void testInitAsyncJobExecutionThreadPool3() {
    // Arrange
    ManagedAsyncJobExecutor managedAsyncJobExecutor = new ManagedAsyncJobExecutor();
    managedAsyncJobExecutor.setThreadFactory(mock(ManagedThreadFactory.class));

    // Act
    managedAsyncJobExecutor.initAsyncJobExecutionThreadPool();

    // Assert
    ExecutorService executorService = managedAsyncJobExecutor.getExecutorService();
    assertTrue(executorService instanceof ThreadPoolExecutor);
    BlockingQueue<Runnable> threadPoolQueue = managedAsyncJobExecutor.getThreadPoolQueue();
    assertTrue(threadPoolQueue.isEmpty());
    assertSame(threadPoolQueue, ((ThreadPoolExecutor) executorService).getQueue());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#initAsyncJobExecutionThreadPool()}.
   *
   * <p>Method under test: {@link DefaultAsyncJobExecutor#initAsyncJobExecutionThreadPool()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.initAsyncJobExecutionThreadPool()"})
  public void testInitAsyncJobExecutionThreadPool4() {
    // Arrange
    ManagedAsyncJobExecutor managedAsyncJobExecutor = new ManagedAsyncJobExecutor();
    managedAsyncJobExecutor.setAsyncJobAcquisitionThread(new Thread());
    managedAsyncJobExecutor.setThreadFactory(mock(ManagedThreadFactory.class));

    // Act
    managedAsyncJobExecutor.initAsyncJobExecutionThreadPool();

    // Assert
    ExecutorService executorService = managedAsyncJobExecutor.getExecutorService();
    assertTrue(executorService instanceof ThreadPoolExecutor);
    BlockingQueue<Runnable> threadPoolQueue = managedAsyncJobExecutor.getThreadPoolQueue();
    assertTrue(threadPoolQueue.isEmpty());
    assertSame(threadPoolQueue, ((ThreadPoolExecutor) executorService).getQueue());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#initAsyncJobExecutionThreadPool()}.
   *
   * <p>Method under test: {@link DefaultAsyncJobExecutor#initAsyncJobExecutionThreadPool()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.initAsyncJobExecutionThreadPool()"})
  public void testInitAsyncJobExecutionThreadPool5() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setThreadPoolQueue(null);
    ForkJoinPool executorService = ForkJoinPool.commonPool();
    sharedExecutorServiceAsyncExecutor.setExecutorService(executorService);

    // Act
    sharedExecutorServiceAsyncExecutor.initAsyncJobExecutionThreadPool();

    // Assert
    ExecutorService executorService2 = sharedExecutorServiceAsyncExecutor.getExecutorService();
    assertTrue(executorService2 instanceof ForkJoinPool);
    assertTrue(sharedExecutorServiceAsyncExecutor.getThreadPoolQueue().isEmpty());
    assertSame(executorService, executorService2);
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#initAsyncJobExecutionThreadPool()}.
   *
   * <ul>
   *   <li>Then throw {@link RejectedExecutionException}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultAsyncJobExecutor#initAsyncJobExecutionThreadPool()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.initAsyncJobExecutionThreadPool()"})
  public void testInitAsyncJobExecutionThreadPool_thenThrowRejectedExecutionException() {
    // Arrange
    SimpleSSLSocketServer asyncJobAcquisitionThread = mock(SimpleSSLSocketServer.class);
    doThrow(new RejectedExecutionException()).when(asyncJobAcquisitionThread).start();

    ManagedAsyncJobExecutor managedAsyncJobExecutor = new ManagedAsyncJobExecutor();
    managedAsyncJobExecutor.setAsyncJobAcquisitionThread(asyncJobAcquisitionThread);
    managedAsyncJobExecutor.setThreadFactory(mock(ManagedThreadFactory.class));

    // Act and Assert
    assertThrows(
        RejectedExecutionException.class,
        () -> managedAsyncJobExecutor.initAsyncJobExecutionThreadPool());
    verify(asyncJobAcquisitionThread).start();
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#stopExecutingAsyncJobs()}.
   *
   * <p>Method under test: {@link DefaultAsyncJobExecutor#stopExecutingAsyncJobs()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.stopExecutingAsyncJobs()"})
  public void testStopExecutingAsyncJobs() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setExecutorService(ForkJoinPool.commonPool());

    // Act
    sharedExecutorServiceAsyncExecutor.stopExecutingAsyncJobs();

    // Assert
    assertNull(sharedExecutorServiceAsyncExecutor.getExecutorService());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#stopExecutingAsyncJobs()}.
   *
   * <p>Method under test: {@link DefaultAsyncJobExecutor#stopExecutingAsyncJobs()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.stopExecutingAsyncJobs()"})
  public void testStopExecutingAsyncJobs2() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setExecutorService(new ForkJoinPool());

    // Act
    sharedExecutorServiceAsyncExecutor.stopExecutingAsyncJobs();

    // Assert
    assertNull(sharedExecutorServiceAsyncExecutor.getExecutorService());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#stopExecutingAsyncJobs()}.
   *
   * <ul>
   *   <li>Then {@link DefaultAsyncJobExecutor} (default constructor) ExecutorService is {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultAsyncJobExecutor#stopExecutingAsyncJobs()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.stopExecutingAsyncJobs()"})
  public void testStopExecutingAsyncJobs_thenDefaultAsyncJobExecutorExecutorServiceIsNull() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();

    // Act
    defaultAsyncJobExecutor.stopExecutingAsyncJobs();

    // Assert that nothing has changed
    assertNull(defaultAsyncJobExecutor.getExecutorService());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#startTimerAcquisitionThread()}.
   *
   * <p>Method under test: {@link DefaultAsyncJobExecutor#startTimerAcquisitionThread()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.startTimerAcquisitionThread()"})
  public void testStartTimerAcquisitionThread() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setTimerJobAcquisitionThread(new Thread());

    // Act and Assert
    sharedExecutorServiceAsyncExecutor.startTimerAcquisitionThread();
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#startTimerAcquisitionThread()}.
   *
   * <ul>
   *   <li>Given {@link DefaultAsyncJobExecutor} (default constructor).
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link DefaultAsyncJobExecutor#startTimerAcquisitionThread()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.startTimerAcquisitionThread()"})
  public void testStartTimerAcquisitionThread_givenDefaultAsyncJobExecutor_thenDoesNotThrow() {
    // Arrange, Act and Assert
    new DefaultAsyncJobExecutor().startTimerAcquisitionThread();
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#startTimerAcquisitionThread()}.
   *
   * <ul>
   *   <li>Then throw {@link RejectedExecutionException}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultAsyncJobExecutor#startTimerAcquisitionThread()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.startTimerAcquisitionThread()"})
  public void testStartTimerAcquisitionThread_thenThrowRejectedExecutionException() {
    // Arrange
    SimpleSSLSocketServer timerJobAcquisitionThread = mock(SimpleSSLSocketServer.class);
    doThrow(new RejectedExecutionException()).when(timerJobAcquisitionThread).start();

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setTimerJobAcquisitionThread(timerJobAcquisitionThread);

    // Act and Assert
    assertThrows(
        RejectedExecutionException.class,
        () -> sharedExecutorServiceAsyncExecutor.startTimerAcquisitionThread());
    verify(timerJobAcquisitionThread).start();
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#stopJobAcquisitionThread()}.
   *
   * <p>Method under test: {@link DefaultAsyncJobExecutor#stopJobAcquisitionThread()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.stopJobAcquisitionThread()"})
  public void testStopJobAcquisitionThread() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setAsyncJobAcquisitionThread(new Thread());

    // Act
    defaultAsyncJobExecutor.stopJobAcquisitionThread();

    // Assert
    assertNull(defaultAsyncJobExecutor.getAsyncJobAcquisitionThread());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#stopJobAcquisitionThread()}.
   *
   * <p>Method under test: {@link DefaultAsyncJobExecutor#stopJobAcquisitionThread()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.stopJobAcquisitionThread()"})
  public void testStopJobAcquisitionThread2() throws InterruptedException {
    // Arrange
    SimpleSSLSocketServer asyncJobAcquisitionThread = mock(SimpleSSLSocketServer.class);
    doThrow(new InterruptedException()).when(asyncJobAcquisitionThread).join();

    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setAsyncJobAcquisitionThread(asyncJobAcquisitionThread);

    // Act
    defaultAsyncJobExecutor.stopJobAcquisitionThread();

    // Assert
    verify(asyncJobAcquisitionThread).join();
    assertNull(defaultAsyncJobExecutor.getAsyncJobAcquisitionThread());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#stopJobAcquisitionThread()}.
   *
   * <ul>
   *   <li>Given {@link DefaultAsyncJobExecutor} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link DefaultAsyncJobExecutor#stopJobAcquisitionThread()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.stopJobAcquisitionThread()"})
  public void testStopJobAcquisitionThread_givenDefaultAsyncJobExecutor() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();

    // Act
    defaultAsyncJobExecutor.stopJobAcquisitionThread();

    // Assert that nothing has changed
    assertNull(defaultAsyncJobExecutor.getAsyncJobAcquisitionThread());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#stopJobAcquisitionThread()}.
   *
   * <ul>
   *   <li>Then throw {@link RejectedExecutionException}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultAsyncJobExecutor#stopJobAcquisitionThread()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.stopJobAcquisitionThread()"})
  public void testStopJobAcquisitionThread_thenThrowRejectedExecutionException()
      throws InterruptedException {
    // Arrange
    SimpleSSLSocketServer asyncJobAcquisitionThread = mock(SimpleSSLSocketServer.class);
    doThrow(new RejectedExecutionException()).when(asyncJobAcquisitionThread).join();

    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setAsyncJobAcquisitionThread(asyncJobAcquisitionThread);

    // Act and Assert
    assertThrows(
        RejectedExecutionException.class, () -> defaultAsyncJobExecutor.stopJobAcquisitionThread());
    verify(asyncJobAcquisitionThread).join();
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#stopTimerAcquisitionThread()}.
   *
   * <p>Method under test: {@link DefaultAsyncJobExecutor#stopTimerAcquisitionThread()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.stopTimerAcquisitionThread()"})
  public void testStopTimerAcquisitionThread() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();

    // Act
    defaultAsyncJobExecutor.stopTimerAcquisitionThread();

    // Assert that nothing has changed
    assertNull(defaultAsyncJobExecutor.getTimerJobAcquisitionThread());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#stopTimerAcquisitionThread()}.
   *
   * <p>Method under test: {@link DefaultAsyncJobExecutor#stopTimerAcquisitionThread()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.stopTimerAcquisitionThread()"})
  public void testStopTimerAcquisitionThread2() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setTimerJobAcquisitionThread(new Thread());

    // Act
    sharedExecutorServiceAsyncExecutor.stopTimerAcquisitionThread();

    // Assert
    assertNull(sharedExecutorServiceAsyncExecutor.getTimerJobAcquisitionThread());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#stopTimerAcquisitionThread()}.
   *
   * <p>Method under test: {@link DefaultAsyncJobExecutor#stopTimerAcquisitionThread()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.stopTimerAcquisitionThread()"})
  public void testStopTimerAcquisitionThread3() throws InterruptedException {
    // Arrange
    SimpleSSLSocketServer timerJobAcquisitionThread = mock(SimpleSSLSocketServer.class);
    doThrow(new InterruptedException()).when(timerJobAcquisitionThread).join();

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setTimerJobAcquisitionThread(timerJobAcquisitionThread);

    // Act
    sharedExecutorServiceAsyncExecutor.stopTimerAcquisitionThread();

    // Assert
    verify(timerJobAcquisitionThread).join();
    assertNull(sharedExecutorServiceAsyncExecutor.getTimerJobAcquisitionThread());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#stopTimerAcquisitionThread()}.
   *
   * <ul>
   *   <li>Then throw {@link RejectedExecutionException}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultAsyncJobExecutor#stopTimerAcquisitionThread()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.stopTimerAcquisitionThread()"})
  public void testStopTimerAcquisitionThread_thenThrowRejectedExecutionException()
      throws InterruptedException {
    // Arrange
    SimpleSSLSocketServer timerJobAcquisitionThread = mock(SimpleSSLSocketServer.class);
    doThrow(new RejectedExecutionException()).when(timerJobAcquisitionThread).join();

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setTimerJobAcquisitionThread(timerJobAcquisitionThread);

    // Act and Assert
    assertThrows(
        RejectedExecutionException.class,
        () -> sharedExecutorServiceAsyncExecutor.stopTimerAcquisitionThread());
    verify(timerJobAcquisitionThread).join();
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#startResetExpiredJobsThread()}.
   *
   * <p>Method under test: {@link DefaultAsyncJobExecutor#startResetExpiredJobsThread()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.startResetExpiredJobsThread()"})
  public void testStartResetExpiredJobsThread() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setResetExpiredJobThread(new Thread());

    // Act and Assert
    sharedExecutorServiceAsyncExecutor.startResetExpiredJobsThread();
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#startResetExpiredJobsThread()}.
   *
   * <ul>
   *   <li>Given {@link DefaultAsyncJobExecutor} (default constructor).
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link DefaultAsyncJobExecutor#startResetExpiredJobsThread()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.startResetExpiredJobsThread()"})
  public void testStartResetExpiredJobsThread_givenDefaultAsyncJobExecutor_thenDoesNotThrow() {
    // Arrange, Act and Assert
    new DefaultAsyncJobExecutor().startResetExpiredJobsThread();
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#startResetExpiredJobsThread()}.
   *
   * <ul>
   *   <li>Then throw {@link RejectedExecutionException}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultAsyncJobExecutor#startResetExpiredJobsThread()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.startResetExpiredJobsThread()"})
  public void testStartResetExpiredJobsThread_thenThrowRejectedExecutionException() {
    // Arrange
    SimpleSSLSocketServer resetExpiredJobThread = mock(SimpleSSLSocketServer.class);
    doThrow(new RejectedExecutionException()).when(resetExpiredJobThread).start();

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setResetExpiredJobThread(resetExpiredJobThread);

    // Act and Assert
    assertThrows(
        RejectedExecutionException.class,
        () -> sharedExecutorServiceAsyncExecutor.startResetExpiredJobsThread());
    verify(resetExpiredJobThread).start();
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#stopResetExpiredJobsThread()}.
   *
   * <p>Method under test: {@link DefaultAsyncJobExecutor#stopResetExpiredJobsThread()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.stopResetExpiredJobsThread()"})
  public void testStopResetExpiredJobsThread() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();

    // Act
    defaultAsyncJobExecutor.stopResetExpiredJobsThread();

    // Assert that nothing has changed
    assertNull(defaultAsyncJobExecutor.getResetExpiredJobThread());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#stopResetExpiredJobsThread()}.
   *
   * <p>Method under test: {@link DefaultAsyncJobExecutor#stopResetExpiredJobsThread()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.stopResetExpiredJobsThread()"})
  public void testStopResetExpiredJobsThread2() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setResetExpiredJobThread(new Thread());

    // Act
    sharedExecutorServiceAsyncExecutor.stopResetExpiredJobsThread();

    // Assert
    assertNull(sharedExecutorServiceAsyncExecutor.getResetExpiredJobThread());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#stopResetExpiredJobsThread()}.
   *
   * <p>Method under test: {@link DefaultAsyncJobExecutor#stopResetExpiredJobsThread()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.stopResetExpiredJobsThread()"})
  public void testStopResetExpiredJobsThread3() throws InterruptedException {
    // Arrange
    SimpleSSLSocketServer resetExpiredJobThread = mock(SimpleSSLSocketServer.class);
    doThrow(new InterruptedException()).when(resetExpiredJobThread).join();

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setResetExpiredJobThread(resetExpiredJobThread);

    // Act
    sharedExecutorServiceAsyncExecutor.stopResetExpiredJobsThread();

    // Assert
    verify(resetExpiredJobThread).join();
    assertNull(sharedExecutorServiceAsyncExecutor.getResetExpiredJobThread());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#stopResetExpiredJobsThread()}.
   *
   * <ul>
   *   <li>Then throw {@link RejectedExecutionException}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultAsyncJobExecutor#stopResetExpiredJobsThread()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.stopResetExpiredJobsThread()"})
  public void testStopResetExpiredJobsThread_thenThrowRejectedExecutionException()
      throws InterruptedException {
    // Arrange
    SimpleSSLSocketServer resetExpiredJobThread = mock(SimpleSSLSocketServer.class);
    doThrow(new RejectedExecutionException()).when(resetExpiredJobThread).join();

    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor =
        new SharedExecutorServiceAsyncExecutor(new DummyTenantInfoHolder());
    sharedExecutorServiceAsyncExecutor.setResetExpiredJobThread(resetExpiredJobThread);

    // Act and Assert
    assertThrows(
        RejectedExecutionException.class,
        () -> sharedExecutorServiceAsyncExecutor.stopResetExpiredJobsThread());
    verify(resetExpiredJobThread).join();
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#applyConfig(ProcessEngineConfigurationImpl)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultAsyncJobExecutor#applyConfig(ProcessEngineConfigurationImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.applyConfig(ProcessEngineConfigurationImpl)"})
  public void testApplyConfig_givenNull() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();

    MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfiguration =
        new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    processEngineConfiguration.setAsyncExecutorThreadPoolQueue(null);
    processEngineConfiguration.setAsyncExecutorLockOwner("Process Engine Configuration");

    // Act
    defaultAsyncJobExecutor.applyConfig(processEngineConfiguration);

    // Assert
    assertEquals("Process Engine Configuration", defaultAsyncJobExecutor.getLockOwner());
    assertNull(defaultAsyncJobExecutor.getThreadPoolQueue());
    assertEquals(10, defaultAsyncJobExecutor.getRetryWaitTimeInMillis());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#applyConfig(ProcessEngineConfigurationImpl)}.
   *
   * <ul>
   *   <li>Then {@link DefaultAsyncJobExecutor} (default constructor) ThreadPoolQueue is {@link
   *       LinkedBlockingDeque#LinkedBlockingDeque()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultAsyncJobExecutor#applyConfig(ProcessEngineConfigurationImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.applyConfig(ProcessEngineConfigurationImpl)"})
  public void testApplyConfig_thenDefaultAsyncJobExecutorThreadPoolQueueIsLinkedBlockingDeque() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();

    MultiSchemaMultiTenantProcessEngineConfiguration processEngineConfiguration =
        new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    LinkedBlockingDeque<Runnable> asyncExecutorThreadPoolQueue = new LinkedBlockingDeque<>();
    processEngineConfiguration.setAsyncExecutorThreadPoolQueue(asyncExecutorThreadPoolQueue);
    processEngineConfiguration.setAsyncExecutorLockOwner("Process Engine Configuration");

    // Act
    defaultAsyncJobExecutor.applyConfig(processEngineConfiguration);

    // Assert
    assertEquals("Process Engine Configuration", defaultAsyncJobExecutor.getLockOwner());
    assertEquals(10, defaultAsyncJobExecutor.getRetryWaitTimeInMillis());
    assertSame(asyncExecutorThreadPoolQueue, defaultAsyncJobExecutor.getThreadPoolQueue());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#applyConfig(ProcessEngineConfigurationImpl)}.
   *
   * <ul>
   *   <li>When {@link JtaProcessEngineConfiguration} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultAsyncJobExecutor#applyConfig(ProcessEngineConfigurationImpl)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.applyConfig(ProcessEngineConfigurationImpl)"})
  public void testApplyConfig_whenJtaProcessEngineConfiguration() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();

    // Act
    defaultAsyncJobExecutor.applyConfig(new JtaProcessEngineConfiguration());

    // Assert
    assertNull(defaultAsyncJobExecutor.getThreadPoolQueue());
    assertEquals(10, defaultAsyncJobExecutor.getRetryWaitTimeInMillis());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link DefaultAsyncJobExecutor#setAsyncJobAcquisitionThread(Thread)}
   *   <li>{@link DefaultAsyncJobExecutor#setAsyncJobLockTimeInMillis(int)}
   *   <li>{@link DefaultAsyncJobExecutor#setAsyncJobsDueRunnable(AcquireAsyncJobsDueRunnable)}
   *   <li>{@link DefaultAsyncJobExecutor#setCorePoolSize(int)}
   *   <li>{@link DefaultAsyncJobExecutor#setDefaultAsyncJobAcquireWaitTimeInMillis(int)}
   *   <li>{@link DefaultAsyncJobExecutor#setDefaultQueueSizeFullWaitTimeInMillis(int)}
   *   <li>{@link DefaultAsyncJobExecutor#setDefaultTimerJobAcquireWaitTimeInMillis(int)}
   *   <li>{@link
   *       DefaultAsyncJobExecutor#setExecuteAsyncRunnableFactory(ExecuteAsyncRunnableFactory)}
   *   <li>{@link DefaultAsyncJobExecutor#setKeepAliveTime(long)}
   *   <li>{@link DefaultAsyncJobExecutor#setLockOwner(String)}
   *   <li>{@link DefaultAsyncJobExecutor#setMaxAsyncJobsDuePerAcquisition(int)}
   *   <li>{@link DefaultAsyncJobExecutor#setMaxPoolSize(int)}
   *   <li>{@link DefaultAsyncJobExecutor#setMaxTimerJobsPerAcquisition(int)}
   *   <li>{@link DefaultAsyncJobExecutor#setMessageQueueMode(boolean)}
   *   <li>{@link
   *       DefaultAsyncJobExecutor#setProcessEngineConfiguration(ProcessEngineConfigurationImpl)}
   *   <li>{@link DefaultAsyncJobExecutor#setQueueSize(int)}
   *   <li>{@link DefaultAsyncJobExecutor#setResetExpiredJobThread(Thread)}
   *   <li>{@link DefaultAsyncJobExecutor#setResetExpiredJobsInterval(int)}
   *   <li>{@link DefaultAsyncJobExecutor#setResetExpiredJobsPageSize(int)}
   *   <li>{@link DefaultAsyncJobExecutor#setResetExpiredJobsRunnable(ResetExpiredJobsRunnable)}
   *   <li>{@link DefaultAsyncJobExecutor#setRetryWaitTimeInMillis(int)}
   *   <li>{@link DefaultAsyncJobExecutor#setSecondsToWaitOnShutdown(long)}
   *   <li>{@link DefaultAsyncJobExecutor#setThreadPoolQueue(BlockingQueue)}
   *   <li>{@link DefaultAsyncJobExecutor#setTimerJobAcquisitionThread(Thread)}
   *   <li>{@link DefaultAsyncJobExecutor#setTimerJobRunnable(AcquireTimerJobsRunnable)}
   *   <li>{@link DefaultAsyncJobExecutor#setTimerLockTimeInMillis(int)}
   *   <li>{@link DefaultAsyncJobExecutor#setAutoActivate(boolean)}
   *   <li>{@link DefaultAsyncJobExecutor#getAsyncJobAcquisitionThread()}
   *   <li>{@link DefaultAsyncJobExecutor#getAsyncJobLockTimeInMillis()}
   *   <li>{@link DefaultAsyncJobExecutor#getCorePoolSize()}
   *   <li>{@link DefaultAsyncJobExecutor#getDefaultAsyncJobAcquireWaitTimeInMillis()}
   *   <li>{@link DefaultAsyncJobExecutor#getDefaultQueueSizeFullWaitTimeInMillis()}
   *   <li>{@link DefaultAsyncJobExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}
   *   <li>{@link DefaultAsyncJobExecutor#getExecuteAsyncRunnableFactory()}
   *   <li>{@link DefaultAsyncJobExecutor#getExecutorService()}
   *   <li>{@link DefaultAsyncJobExecutor#getKeepAliveTime()}
   *   <li>{@link DefaultAsyncJobExecutor#getLockOwner()}
   *   <li>{@link DefaultAsyncJobExecutor#getMaxAsyncJobsDuePerAcquisition()}
   *   <li>{@link DefaultAsyncJobExecutor#getMaxPoolSize()}
   *   <li>{@link DefaultAsyncJobExecutor#getMaxTimerJobsPerAcquisition()}
   *   <li>{@link DefaultAsyncJobExecutor#getProcessEngineConfiguration()}
   *   <li>{@link DefaultAsyncJobExecutor#getQueueSize()}
   *   <li>{@link DefaultAsyncJobExecutor#getResetExpiredJobThread()}
   *   <li>{@link DefaultAsyncJobExecutor#getResetExpiredJobsInterval()}
   *   <li>{@link DefaultAsyncJobExecutor#getResetExpiredJobsPageSize()}
   *   <li>{@link DefaultAsyncJobExecutor#getRetryWaitTimeInMillis()}
   *   <li>{@link DefaultAsyncJobExecutor#getSecondsToWaitOnShutdown()}
   *   <li>{@link DefaultAsyncJobExecutor#getThreadPoolQueue()}
   *   <li>{@link DefaultAsyncJobExecutor#getTimerJobAcquisitionThread()}
   *   <li>{@link DefaultAsyncJobExecutor#getTimerLockTimeInMillis()}
   *   <li>{@link DefaultAsyncJobExecutor#isActive()}
   *   <li>{@link DefaultAsyncJobExecutor#isAutoActivate()}
   *   <li>{@link DefaultAsyncJobExecutor#isMessageQueueMode()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Thread DefaultAsyncJobExecutor.getAsyncJobAcquisitionThread()",
    "int DefaultAsyncJobExecutor.getAsyncJobLockTimeInMillis()",
    "int DefaultAsyncJobExecutor.getCorePoolSize()",
    "int DefaultAsyncJobExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis()",
    "int DefaultAsyncJobExecutor.getDefaultQueueSizeFullWaitTimeInMillis()",
    "int DefaultAsyncJobExecutor.getDefaultTimerJobAcquireWaitTimeInMillis()",
    "ExecuteAsyncRunnableFactory DefaultAsyncJobExecutor.getExecuteAsyncRunnableFactory()",
    "ExecutorService DefaultAsyncJobExecutor.getExecutorService()",
    "long DefaultAsyncJobExecutor.getKeepAliveTime()",
    "String DefaultAsyncJobExecutor.getLockOwner()",
    "int DefaultAsyncJobExecutor.getMaxAsyncJobsDuePerAcquisition()",
    "int DefaultAsyncJobExecutor.getMaxPoolSize()",
    "int DefaultAsyncJobExecutor.getMaxTimerJobsPerAcquisition()",
    "ProcessEngineConfigurationImpl DefaultAsyncJobExecutor.getProcessEngineConfiguration()",
    "int DefaultAsyncJobExecutor.getQueueSize()",
    "Thread DefaultAsyncJobExecutor.getResetExpiredJobThread()",
    "int DefaultAsyncJobExecutor.getResetExpiredJobsInterval()",
    "int DefaultAsyncJobExecutor.getResetExpiredJobsPageSize()",
    "int DefaultAsyncJobExecutor.getRetryWaitTimeInMillis()",
    "long DefaultAsyncJobExecutor.getSecondsToWaitOnShutdown()",
    "BlockingQueue DefaultAsyncJobExecutor.getThreadPoolQueue()",
    "Thread DefaultAsyncJobExecutor.getTimerJobAcquisitionThread()",
    "int DefaultAsyncJobExecutor.getTimerLockTimeInMillis()",
    "boolean DefaultAsyncJobExecutor.isActive()",
    "boolean DefaultAsyncJobExecutor.isAutoActivate()",
    "boolean DefaultAsyncJobExecutor.isMessageQueueMode()",
    "void DefaultAsyncJobExecutor.setAsyncJobAcquisitionThread(Thread)",
    "void DefaultAsyncJobExecutor.setAsyncJobLockTimeInMillis(int)",
    "void DefaultAsyncJobExecutor.setAsyncJobsDueRunnable(AcquireAsyncJobsDueRunnable)",
    "void DefaultAsyncJobExecutor.setAutoActivate(boolean)",
    "void DefaultAsyncJobExecutor.setCorePoolSize(int)",
    "void DefaultAsyncJobExecutor.setDefaultAsyncJobAcquireWaitTimeInMillis(int)",
    "void DefaultAsyncJobExecutor.setDefaultQueueSizeFullWaitTimeInMillis(int)",
    "void DefaultAsyncJobExecutor.setDefaultTimerJobAcquireWaitTimeInMillis(int)",
    "void DefaultAsyncJobExecutor.setExecuteAsyncRunnableFactory(ExecuteAsyncRunnableFactory)",
    "void DefaultAsyncJobExecutor.setExecutorService(ExecutorService)",
    "void DefaultAsyncJobExecutor.setKeepAliveTime(long)",
    "void DefaultAsyncJobExecutor.setLockOwner(String)",
    "void DefaultAsyncJobExecutor.setMaxAsyncJobsDuePerAcquisition(int)",
    "void DefaultAsyncJobExecutor.setMaxPoolSize(int)",
    "void DefaultAsyncJobExecutor.setMaxTimerJobsPerAcquisition(int)",
    "void DefaultAsyncJobExecutor.setMessageQueueMode(boolean)",
    "void DefaultAsyncJobExecutor.setProcessEngineConfiguration(ProcessEngineConfigurationImpl)",
    "void DefaultAsyncJobExecutor.setQueueSize(int)",
    "void DefaultAsyncJobExecutor.setResetExpiredJobThread(Thread)",
    "void DefaultAsyncJobExecutor.setResetExpiredJobsInterval(int)",
    "void DefaultAsyncJobExecutor.setResetExpiredJobsPageSize(int)",
    "void DefaultAsyncJobExecutor.setResetExpiredJobsRunnable(ResetExpiredJobsRunnable)",
    "void DefaultAsyncJobExecutor.setRetryWaitTimeInMillis(int)",
    "void DefaultAsyncJobExecutor.setSecondsToWaitOnShutdown(long)",
    "void DefaultAsyncJobExecutor.setThreadPoolQueue(BlockingQueue)",
    "void DefaultAsyncJobExecutor.setTimerJobAcquisitionThread(Thread)",
    "void DefaultAsyncJobExecutor.setTimerJobRunnable(AcquireTimerJobsRunnable)",
    "void DefaultAsyncJobExecutor.setTimerLockTimeInMillis(int)"
  })
  public void testGettersAndSetters() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    Thread asyncJobAcquisitionThread = new Thread();

    // Act
    defaultAsyncJobExecutor.setAsyncJobAcquisitionThread(asyncJobAcquisitionThread);
    defaultAsyncJobExecutor.setAsyncJobLockTimeInMillis(1);
    defaultAsyncJobExecutor.setAsyncJobsDueRunnable(
        new AcquireAsyncJobsDueRunnable(new DefaultAsyncJobExecutor()));
    defaultAsyncJobExecutor.setCorePoolSize(3);
    defaultAsyncJobExecutor.setDefaultAsyncJobAcquireWaitTimeInMillis(1);
    defaultAsyncJobExecutor.setDefaultQueueSizeFullWaitTimeInMillis(3);
    defaultAsyncJobExecutor.setDefaultTimerJobAcquireWaitTimeInMillis(1);
    ExecuteAsyncRunnableFactory executeAsyncRunnableFactory =
        mock(ExecuteAsyncRunnableFactory.class);
    defaultAsyncJobExecutor.setExecuteAsyncRunnableFactory(executeAsyncRunnableFactory);
    defaultAsyncJobExecutor.setKeepAliveTime(1L);
    defaultAsyncJobExecutor.setLockOwner("Lock Owner");
    defaultAsyncJobExecutor.setMaxAsyncJobsDuePerAcquisition(3);
    defaultAsyncJobExecutor.setMaxPoolSize(3);
    defaultAsyncJobExecutor.setMaxTimerJobsPerAcquisition(3);
    defaultAsyncJobExecutor.setMessageQueueMode(true);
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    defaultAsyncJobExecutor.setProcessEngineConfiguration(processEngineConfiguration);
    defaultAsyncJobExecutor.setQueueSize(3);
    Thread resetExpiredJobThread = new Thread();
    defaultAsyncJobExecutor.setResetExpiredJobThread(resetExpiredJobThread);
    defaultAsyncJobExecutor.setResetExpiredJobsInterval(42);
    defaultAsyncJobExecutor.setResetExpiredJobsPageSize(3);
    defaultAsyncJobExecutor.setResetExpiredJobsRunnable(
        new ResetExpiredJobsRunnable(new DefaultAsyncJobExecutor()));
    defaultAsyncJobExecutor.setRetryWaitTimeInMillis(1);
    defaultAsyncJobExecutor.setSecondsToWaitOnShutdown(1L);
    defaultAsyncJobExecutor.setThreadPoolQueue(null);
    Thread timerJobAcquisitionThread = new Thread();
    defaultAsyncJobExecutor.setTimerJobAcquisitionThread(timerJobAcquisitionThread);
    DefaultAsyncJobExecutor asyncExecutor = new DefaultAsyncJobExecutor();
    AcquireTimerJobsRunnable timerJobRunnable =
        new AcquireTimerJobsRunnable(asyncExecutor, new DefaultJobManager());
    defaultAsyncJobExecutor.setTimerJobRunnable(timerJobRunnable);
    defaultAsyncJobExecutor.setTimerLockTimeInMillis(1);
    defaultAsyncJobExecutor.setAutoActivate(true);
    Thread actualAsyncJobAcquisitionThread = defaultAsyncJobExecutor.getAsyncJobAcquisitionThread();
    int actualAsyncJobLockTimeInMillis = defaultAsyncJobExecutor.getAsyncJobLockTimeInMillis();
    int actualCorePoolSize = defaultAsyncJobExecutor.getCorePoolSize();
    int actualDefaultAsyncJobAcquireWaitTimeInMillis =
        defaultAsyncJobExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis();
    int actualDefaultQueueSizeFullWaitTimeInMillis =
        defaultAsyncJobExecutor.getDefaultQueueSizeFullWaitTimeInMillis();
    int actualDefaultTimerJobAcquireWaitTimeInMillis =
        defaultAsyncJobExecutor.getDefaultTimerJobAcquireWaitTimeInMillis();
    ExecuteAsyncRunnableFactory actualExecuteAsyncRunnableFactory =
        defaultAsyncJobExecutor.getExecuteAsyncRunnableFactory();
    ExecutorService actualExecutorService = defaultAsyncJobExecutor.getExecutorService();
    long actualKeepAliveTime = defaultAsyncJobExecutor.getKeepAliveTime();
    String actualLockOwner = defaultAsyncJobExecutor.getLockOwner();
    int actualMaxAsyncJobsDuePerAcquisition =
        defaultAsyncJobExecutor.getMaxAsyncJobsDuePerAcquisition();
    int actualMaxPoolSize = defaultAsyncJobExecutor.getMaxPoolSize();
    int actualMaxTimerJobsPerAcquisition = defaultAsyncJobExecutor.getMaxTimerJobsPerAcquisition();
    ProcessEngineConfigurationImpl actualProcessEngineConfiguration =
        defaultAsyncJobExecutor.getProcessEngineConfiguration();
    int actualQueueSize = defaultAsyncJobExecutor.getQueueSize();
    Thread actualResetExpiredJobThread = defaultAsyncJobExecutor.getResetExpiredJobThread();
    int actualResetExpiredJobsInterval = defaultAsyncJobExecutor.getResetExpiredJobsInterval();
    int actualResetExpiredJobsPageSize = defaultAsyncJobExecutor.getResetExpiredJobsPageSize();
    int actualRetryWaitTimeInMillis = defaultAsyncJobExecutor.getRetryWaitTimeInMillis();
    long actualSecondsToWaitOnShutdown = defaultAsyncJobExecutor.getSecondsToWaitOnShutdown();
    BlockingQueue<Runnable> actualThreadPoolQueue = defaultAsyncJobExecutor.getThreadPoolQueue();
    Thread actualTimerJobAcquisitionThread = defaultAsyncJobExecutor.getTimerJobAcquisitionThread();
    int actualTimerLockTimeInMillis = defaultAsyncJobExecutor.getTimerLockTimeInMillis();
    boolean actualIsActiveResult = defaultAsyncJobExecutor.isActive();
    boolean actualIsAutoActivateResult = defaultAsyncJobExecutor.isAutoActivate();

    // Assert
    assertEquals("Lock Owner", actualLockOwner);
    assertNull(actualThreadPoolQueue);
    assertNull(actualExecutorService);
    assertEquals(1, actualAsyncJobLockTimeInMillis);
    assertEquals(1, actualDefaultAsyncJobAcquireWaitTimeInMillis);
    assertEquals(1, actualDefaultTimerJobAcquireWaitTimeInMillis);
    assertEquals(1, actualRetryWaitTimeInMillis);
    assertEquals(1, actualTimerLockTimeInMillis);
    assertEquals(1L, actualKeepAliveTime);
    assertEquals(1L, actualSecondsToWaitOnShutdown);
    assertEquals(3, actualCorePoolSize);
    assertEquals(3, actualDefaultQueueSizeFullWaitTimeInMillis);
    assertEquals(3, actualMaxAsyncJobsDuePerAcquisition);
    assertEquals(3, actualMaxPoolSize);
    assertEquals(3, actualMaxTimerJobsPerAcquisition);
    assertEquals(3, actualQueueSize);
    assertEquals(3, actualResetExpiredJobsPageSize);
    assertEquals(42, actualResetExpiredJobsInterval);
    assertFalse(actualIsActiveResult);
    assertTrue(actualIsAutoActivateResult);
    assertTrue(defaultAsyncJobExecutor.isMessageQueueMode());
    assertSame(asyncJobAcquisitionThread, actualAsyncJobAcquisitionThread);
    assertSame(resetExpiredJobThread, actualResetExpiredJobThread);
    assertSame(timerJobAcquisitionThread, actualTimerJobAcquisitionThread);
    assertSame(processEngineConfiguration, actualProcessEngineConfiguration);
    assertSame(executeAsyncRunnableFactory, actualExecuteAsyncRunnableFactory);
  }

  /**
   * Test new {@link DefaultAsyncJobExecutor} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link DefaultAsyncJobExecutor}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.<init>()"})
  public void testNewDefaultAsyncJobExecutor() {
    // Arrange and Act
    DefaultAsyncJobExecutor actualDefaultAsyncJobExecutor = new DefaultAsyncJobExecutor();

    // Assert
    assertNull(actualDefaultAsyncJobExecutor.getAsyncJobAcquisitionThread());
    assertNull(actualDefaultAsyncJobExecutor.getResetExpiredJobThread());
    assertNull(actualDefaultAsyncJobExecutor.getTimerJobAcquisitionThread());
    assertNull(actualDefaultAsyncJobExecutor.getThreadPoolQueue());
    assertNull(actualDefaultAsyncJobExecutor.getExecutorService());
    assertNull(actualDefaultAsyncJobExecutor.asyncJobsDueRunnable);
    assertNull(actualDefaultAsyncJobExecutor.timerJobRunnable);
    assertNull(actualDefaultAsyncJobExecutor.getExecuteAsyncRunnableFactory());
    assertNull(actualDefaultAsyncJobExecutor.resetExpiredJobsRunnable);
    assertNull(actualDefaultAsyncJobExecutor.getProcessEngineConfiguration());
    assertEquals(0, actualDefaultAsyncJobExecutor.getDefaultQueueSizeFullWaitTimeInMillis());
    assertEquals(1, actualDefaultAsyncJobExecutor.getMaxAsyncJobsDuePerAcquisition());
    assertEquals(1, actualDefaultAsyncJobExecutor.getMaxTimerJobsPerAcquisition());
    assertEquals(10, actualDefaultAsyncJobExecutor.getMaxPoolSize());
    assertEquals(100, actualDefaultAsyncJobExecutor.getQueueSize());
    assertEquals(10000, actualDefaultAsyncJobExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis());
    assertEquals(10000, actualDefaultAsyncJobExecutor.getDefaultTimerJobAcquireWaitTimeInMillis());
    assertEquals(2, actualDefaultAsyncJobExecutor.getCorePoolSize());
    assertEquals(3, actualDefaultAsyncJobExecutor.getResetExpiredJobsPageSize());
    assertEquals(300000, actualDefaultAsyncJobExecutor.getAsyncJobLockTimeInMillis());
    assertEquals(300000, actualDefaultAsyncJobExecutor.getTimerLockTimeInMillis());
    assertEquals(500, actualDefaultAsyncJobExecutor.getRetryWaitTimeInMillis());
    assertEquals(5000L, actualDefaultAsyncJobExecutor.getKeepAliveTime());
    assertEquals(60000, actualDefaultAsyncJobExecutor.getResetExpiredJobsInterval());
    assertEquals(60L, actualDefaultAsyncJobExecutor.getSecondsToWaitOnShutdown());
    assertFalse(actualDefaultAsyncJobExecutor.isActive());
    assertFalse(actualDefaultAsyncJobExecutor.isAutoActivate());
    assertFalse(actualDefaultAsyncJobExecutor.isMessageQueueMode());
    assertTrue(actualDefaultAsyncJobExecutor.temporaryJobQueue.isEmpty());
  }
}
