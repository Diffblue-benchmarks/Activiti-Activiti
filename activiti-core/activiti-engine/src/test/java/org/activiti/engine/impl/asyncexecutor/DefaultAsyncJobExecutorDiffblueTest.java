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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import ch.qos.logback.classic.net.SimpleSSLSocketServer;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import javax.enterprise.concurrent.ManagedThreadFactory;
import javax.xml.namespace.QName;
import org.activiti.engine.impl.asyncexecutor.multitenant.SharedExecutorServiceAsyncExecutor;
import org.activiti.engine.impl.asyncexecutor.multitenant.TenantAwareExecuteAsyncRunnable;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityImpl;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.test.cfg.multitenant.DummyTenantInfoHolder;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class DefaultAsyncJobExecutorDiffblueTest {
  /**
   * Test {@link DefaultAsyncJobExecutor#executeAsyncJob(Job)}.
   * <ul>
   *   <li>Then {@link DefaultAsyncJobExecutor} (default constructor) {@link DefaultAsyncJobExecutor#temporaryJobQueue} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#executeAsyncJob(Job)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean DefaultAsyncJobExecutor.executeAsyncJob(Job)"})
  public void testExecuteAsyncJob_thenDefaultAsyncJobExecutorTemporaryJobQueueEmpty() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setMessageQueueMode(true);

    // Act
    boolean actualExecuteAsyncJobResult = defaultAsyncJobExecutor.executeAsyncJob(new DeadLetterJobEntityImpl());

    // Assert
    assertTrue(defaultAsyncJobExecutor.temporaryJobQueue.isEmpty());
    assertTrue(actualExecuteAsyncJobResult);
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#executeAsyncJob(Job)}.
   * <ul>
   *   <li>Then {@link DefaultAsyncJobExecutor} (default constructor) {@link DefaultAsyncJobExecutor#temporaryJobQueue} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#executeAsyncJob(Job)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#createRunnableForJob(Job)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Runnable DefaultAsyncJobExecutor.createRunnableForJob(Job)"})
  public void testCreateRunnableForJob() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setExecuteAsyncRunnableFactory(null);

    // Act
    Runnable actualCreateRunnableForJobResult = defaultAsyncJobExecutor
        .createRunnableForJob(new DeadLetterJobEntityImpl());

    // Assert
    assertTrue(actualCreateRunnableForJobResult instanceof ExecuteAsyncRunnable);
    assertTrue(((ExecuteAsyncRunnable) actualCreateRunnableForJobResult).job instanceof DeadLetterJobEntityImpl);
    assertNull(((ExecuteAsyncRunnable) actualCreateRunnableForJobResult).jobId);
    assertNull(((ExecuteAsyncRunnable) actualCreateRunnableForJobResult).processEngineConfiguration);
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#createRunnableForJob(Job)}.
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#createRunnableForJob(Job)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Runnable DefaultAsyncJobExecutor.createRunnableForJob(Job)"})
  public void testCreateRunnableForJob2() {
    // Arrange
    ExecuteAsyncRunnableFactory executeAsyncRunnableFactory = mock(ExecuteAsyncRunnableFactory.class);
    when(executeAsyncRunnableFactory.createExecuteAsyncRunnable(Mockito.<Job>any(),
        Mockito.<ProcessEngineConfigurationImpl>any())).thenReturn(mock(Runnable.class));

    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setExecuteAsyncRunnableFactory(executeAsyncRunnableFactory);

    // Act
    defaultAsyncJobExecutor.createRunnableForJob(new DeadLetterJobEntityImpl());

    // Assert
    verify(executeAsyncRunnableFactory).createExecuteAsyncRunnable(isA(Job.class), isNull());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#createRunnableForJob(Job)}.
   * <ul>
   *   <li>Then return {@link ExecuteAsyncRunnable}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#createRunnableForJob(Job)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Runnable DefaultAsyncJobExecutor.createRunnableForJob(Job)"})
  public void testCreateRunnableForJob_thenReturnExecuteAsyncRunnable() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();

    // Act
    Runnable actualCreateRunnableForJobResult = defaultAsyncJobExecutor
        .createRunnableForJob(new DeadLetterJobEntityImpl());

    // Assert
    assertTrue(actualCreateRunnableForJobResult instanceof ExecuteAsyncRunnable);
    assertTrue(((ExecuteAsyncRunnable) actualCreateRunnableForJobResult).job instanceof DeadLetterJobEntityImpl);
    assertNull(((ExecuteAsyncRunnable) actualCreateRunnableForJobResult).jobId);
    assertNull(((ExecuteAsyncRunnable) actualCreateRunnableForJobResult).processEngineConfiguration);
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#createRunnableForJob(Job)}.
   * <ul>
   *   <li>Then return {@link TenantAwareExecuteAsyncRunnable}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#createRunnableForJob(Job)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Runnable DefaultAsyncJobExecutor.createRunnableForJob(Job)"})
  public void testCreateRunnableForJob_thenReturnTenantAwareExecuteAsyncRunnable() {
    // Arrange
    SharedExecutorServiceAsyncExecutor sharedExecutorServiceAsyncExecutor = new SharedExecutorServiceAsyncExecutor(
        new DummyTenantInfoHolder());

    // Act
    Runnable actualCreateRunnableForJobResult = sharedExecutorServiceAsyncExecutor
        .createRunnableForJob(new DeadLetterJobEntityImpl());

    // Assert
    assertTrue(actualCreateRunnableForJobResult instanceof TenantAwareExecuteAsyncRunnable);
    assertTrue(
        ((TenantAwareExecuteAsyncRunnable) actualCreateRunnableForJobResult).job instanceof DeadLetterJobEntityImpl);
    assertNull(((TenantAwareExecuteAsyncRunnable) actualCreateRunnableForJobResult).jobId);
    assertNull(((TenantAwareExecuteAsyncRunnable) actualCreateRunnableForJobResult).processEngineConfiguration);
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#createRunnableForJob(Job)}.
   * <ul>
   *   <li>Then throw {@link RejectedExecutionException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#createRunnableForJob(Job)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Runnable DefaultAsyncJobExecutor.createRunnableForJob(Job)"})
  public void testCreateRunnableForJob_thenThrowRejectedExecutionException() {
    // Arrange
    ExecuteAsyncRunnableFactory executeAsyncRunnableFactory = mock(ExecuteAsyncRunnableFactory.class);
    when(executeAsyncRunnableFactory.createExecuteAsyncRunnable(Mockito.<Job>any(),
        Mockito.<ProcessEngineConfigurationImpl>any())).thenThrow(new RejectedExecutionException("foo"));

    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setExecuteAsyncRunnableFactory(executeAsyncRunnableFactory);

    // Act and Assert
    assertThrows(RejectedExecutionException.class,
        () -> defaultAsyncJobExecutor.createRunnableForJob(new DeadLetterJobEntityImpl()));
    verify(executeAsyncRunnableFactory).createExecuteAsyncRunnable(isA(Job.class), isNull());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#start()}.
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#start()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.start()"})
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
   * Test {@link DefaultAsyncJobExecutor#start()}.
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#start()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.start()"})
  public void testStart2() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    DefaultAsyncJobExecutor asyncExecutor = new DefaultAsyncJobExecutor();
    AcquireTimerJobsRunnable timerJobRunnable = new AcquireTimerJobsRunnable(asyncExecutor, new DefaultJobManager());

    defaultAsyncJobExecutor.setTimerJobRunnable(timerJobRunnable);

    // Act
    defaultAsyncJobExecutor.start();

    // Assert that nothing has changed
    AcquireTimerJobsRunnable acquireTimerJobsRunnable = defaultAsyncJobExecutor.timerJobRunnable;
    AsyncExecutor asyncExecutor2 = acquireTimerJobsRunnable.asyncExecutor;
    assertTrue(asyncExecutor2 instanceof DefaultAsyncJobExecutor);
    JobManager jobManager = acquireTimerJobsRunnable.jobManager;
    assertTrue(jobManager instanceof DefaultJobManager);
    assertFalse(acquireTimerJobsRunnable.isWaiting.get());
    assertFalse(acquireTimerJobsRunnable.isInterrupted);
    assertSame(timerJobRunnable.asyncExecutor, asyncExecutor2);
    assertSame(timerJobRunnable.jobManager, jobManager);
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#start()}.
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#start()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.start()"})
  public void testStart3() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setProcessEngineConfiguration(new StandaloneInMemProcessEngineConfiguration());

    // Act
    defaultAsyncJobExecutor.start();

    // Assert
    AcquireTimerJobsRunnable acquireTimerJobsRunnable = defaultAsyncJobExecutor.timerJobRunnable;
    assertNull(acquireTimerJobsRunnable.jobManager);
    assertFalse(acquireTimerJobsRunnable.isInterrupted);
    assertSame(defaultAsyncJobExecutor, acquireTimerJobsRunnable.asyncExecutor);
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#start()}.
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#start()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.start()"})
  public void testStart4() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setHistory("Starting up the default async job executor [{}].");
    processEngineConfiguration.addWsEndpointAddress(QName.valueOf("Starting up the default async job executor [{}]."),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    defaultAsyncJobExecutor.start();

    // Assert
    AcquireTimerJobsRunnable acquireTimerJobsRunnable = defaultAsyncJobExecutor.timerJobRunnable;
    assertNull(acquireTimerJobsRunnable.jobManager);
    assertFalse(acquireTimerJobsRunnable.isInterrupted);
    assertSame(defaultAsyncJobExecutor, acquireTimerJobsRunnable.asyncExecutor);
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#start()}.
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#start()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.start()"})
  public void testStart5() throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setJdbcDriver("Starting up the default async job executor [{}].");
    processEngineConfiguration.addWsEndpointAddress(QName.valueOf("Starting up the default async job executor [{}]."),
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    defaultAsyncJobExecutor.start();

    // Assert
    AcquireTimerJobsRunnable acquireTimerJobsRunnable = defaultAsyncJobExecutor.timerJobRunnable;
    assertNull(acquireTimerJobsRunnable.jobManager);
    assertFalse(acquireTimerJobsRunnable.isInterrupted);
    assertSame(defaultAsyncJobExecutor, acquireTimerJobsRunnable.asyncExecutor);
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#start()}.
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#start()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.start()"})
  public void testStart6() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    AcquireTimerJobsRunnable timerJobRunnable = new AcquireTimerJobsRunnable(null, new DefaultJobManager());

    defaultAsyncJobExecutor.setTimerJobRunnable(timerJobRunnable);

    // Act
    defaultAsyncJobExecutor.start();

    // Assert that nothing has changed
    AcquireTimerJobsRunnable acquireTimerJobsRunnable = defaultAsyncJobExecutor.timerJobRunnable;
    JobManager jobManager = acquireTimerJobsRunnable.jobManager;
    assertTrue(jobManager instanceof DefaultJobManager);
    assertFalse(acquireTimerJobsRunnable.isWaiting.get());
    assertFalse(acquireTimerJobsRunnable.isInterrupted);
    assertSame(timerJobRunnable.jobManager, jobManager);
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#start()}.
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#start()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.start()"})
  public void testStart7() {
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
    assertSame(executorService, executorService2);
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#start()}.
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#start()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.start()"})
  public void testStart8() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    processEngineConfiguration
        .setCommandExecutor(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    AcquireAsyncJobsDueRunnable asyncJobsDueRunnable = new AcquireAsyncJobsDueRunnable(new DefaultAsyncJobExecutor());
    defaultAsyncJobExecutor.setAsyncJobsDueRunnable(asyncJobsDueRunnable);
    defaultAsyncJobExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    defaultAsyncJobExecutor.start();

    // Assert that nothing has changed
    AcquireAsyncJobsDueRunnable acquireAsyncJobsDueRunnable = defaultAsyncJobExecutor.asyncJobsDueRunnable;
    AsyncExecutor asyncExecutor = acquireAsyncJobsDueRunnable.asyncExecutor;
    assertTrue(asyncExecutor instanceof DefaultAsyncJobExecutor);
    assertFalse(acquireAsyncJobsDueRunnable.isWaiting.get());
    assertFalse(acquireAsyncJobsDueRunnable.isInterrupted);
    assertSame(asyncJobsDueRunnable.asyncExecutor, asyncExecutor);
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#start()}.
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#start()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.start()"})
  public void testStart9() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    CommandConfig defaultConfig = new CommandConfig();
    processEngineConfiguration
        .setCommandExecutor(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));

    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    ResetExpiredJobsRunnable resetExpiredJobsRunnable = new ResetExpiredJobsRunnable(new DefaultAsyncJobExecutor());
    defaultAsyncJobExecutor.setResetExpiredJobsRunnable(resetExpiredJobsRunnable);
    defaultAsyncJobExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    defaultAsyncJobExecutor.start();

    // Assert that nothing has changed
    ResetExpiredJobsRunnable resetExpiredJobsRunnable2 = defaultAsyncJobExecutor.resetExpiredJobsRunnable;
    AsyncExecutor asyncExecutor = resetExpiredJobsRunnable2.asyncExecutor;
    assertTrue(asyncExecutor instanceof DefaultAsyncJobExecutor);
    assertFalse(resetExpiredJobsRunnable2.isInterrupted);
    assertSame(resetExpiredJobsRunnable.asyncExecutor, asyncExecutor);
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#start()}.
   * <ul>
   *   <li>Given {@link DefaultAsyncJobExecutor} (default constructor) TimerJobAcquisitionThread is {@link Thread#Thread()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#start()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.start()"})
  public void testStart_givenDefaultAsyncJobExecutorTimerJobAcquisitionThreadIsThread() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setTimerJobAcquisitionThread(new Thread());
    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    when(commandExecutor.execute(Mockito.<Command<AcquiredJobEntities>>any())).thenReturn(new AcquiredJobEntities());

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
    processEngineConfiguration.setCommandExecutor(commandExecutor);
    defaultAsyncJobExecutor.setProcessEngineConfiguration(processEngineConfiguration);

    // Act
    defaultAsyncJobExecutor.start();

    // Assert
    verify(commandExecutor).execute(isA(Command.class));
    AcquireTimerJobsRunnable acquireTimerJobsRunnable = defaultAsyncJobExecutor.timerJobRunnable;
    assertNull(acquireTimerJobsRunnable.jobManager);
    assertEquals(0L, acquireTimerJobsRunnable.getMillisToWait());
    assertEquals(10000L, defaultAsyncJobExecutor.asyncJobsDueRunnable.getMillisToWait());
    assertFalse(acquireTimerJobsRunnable.isWaiting.get());
    assertFalse(acquireTimerJobsRunnable.isInterrupted);
    assertSame(defaultAsyncJobExecutor, acquireTimerJobsRunnable.asyncExecutor);
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#initAsyncJobExecutionThreadPool()}.
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#initAsyncJobExecutionThreadPool()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
    assertEquals("activiti-async-job-executor-thread-%d", ((BasicThreadFactory) threadFactory).getNamingPattern());
    assertNull(((BasicThreadFactory) threadFactory).getDaemonFlag());
    assertNull(((BasicThreadFactory) threadFactory).getPriority());
    assertNull(((BasicThreadFactory) threadFactory).getUncaughtExceptionHandler());
    assertEquals(0L, ((BasicThreadFactory) threadFactory).getThreadCount());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#initAsyncJobExecutionThreadPool()}.
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#initAsyncJobExecutionThreadPool()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.initAsyncJobExecutionThreadPool()"})
  public void testInitAsyncJobExecutionThreadPool2() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setThreadPoolQueue(null);
    ForkJoinPool executorService = ForkJoinPool.commonPool();
    defaultAsyncJobExecutor.setExecutorService(executorService);

    // Act
    defaultAsyncJobExecutor.initAsyncJobExecutionThreadPool();

    // Assert
    ExecutorService executorService2 = defaultAsyncJobExecutor.getExecutorService();
    assertTrue(executorService2 instanceof ForkJoinPool);
    assertTrue(defaultAsyncJobExecutor.getThreadPoolQueue().isEmpty());
    assertSame(executorService, executorService2);
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#initAsyncJobExecutionThreadPool()}.
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#initAsyncJobExecutionThreadPool()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.initAsyncJobExecutionThreadPool()"})
  public void testInitAsyncJobExecutionThreadPool3() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    LinkedBlockingDeque<Runnable> threadPoolQueue = new LinkedBlockingDeque<>();
    defaultAsyncJobExecutor.setThreadPoolQueue(threadPoolQueue);
    ForkJoinPool executorService = ForkJoinPool.commonPool();
    defaultAsyncJobExecutor.setExecutorService(executorService);

    // Act
    defaultAsyncJobExecutor.initAsyncJobExecutionThreadPool();

    // Assert that nothing has changed
    ExecutorService executorService2 = defaultAsyncJobExecutor.getExecutorService();
    assertTrue(executorService2 instanceof ForkJoinPool);
    BlockingQueue<Runnable> threadPoolQueue2 = defaultAsyncJobExecutor.getThreadPoolQueue();
    assertTrue(threadPoolQueue2.isEmpty());
    assertSame(threadPoolQueue, threadPoolQueue2);
    assertSame(executorService, executorService2);
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#initAsyncJobExecutionThreadPool()}.
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#initAsyncJobExecutionThreadPool()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.initAsyncJobExecutionThreadPool()"})
  public void testInitAsyncJobExecutionThreadPool4() {
    // Arrange
    ManagedAsyncJobExecutor managedAsyncJobExecutor = new ManagedAsyncJobExecutor();
    managedAsyncJobExecutor.setThreadFactory(mock(ManagedThreadFactory.class));

    // Act
    managedAsyncJobExecutor.initAsyncJobExecutionThreadPool();

    // Assert
    ExecutorService executorService = managedAsyncJobExecutor.getExecutorService();
    assertTrue(executorService instanceof ThreadPoolExecutor);
    assertEquals(0, ((ThreadPoolExecutor) executorService).getActiveCount());
    assertEquals(0, ((ThreadPoolExecutor) executorService).getLargestPoolSize());
    assertEquals(0, ((ThreadPoolExecutor) executorService).getPoolSize());
    assertEquals(0L, ((ThreadPoolExecutor) executorService).getCompletedTaskCount());
    assertEquals(0L, ((ThreadPoolExecutor) executorService).getTaskCount());
    assertEquals(10, ((ThreadPoolExecutor) executorService).getMaximumPoolSize());
    assertEquals(2, ((ThreadPoolExecutor) executorService).getCorePoolSize());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#initAsyncJobExecutionThreadPool()}.
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#initAsyncJobExecutionThreadPool()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.initAsyncJobExecutionThreadPool()"})
  public void testInitAsyncJobExecutionThreadPool5() {
    // Arrange
    ManagedAsyncJobExecutor managedAsyncJobExecutor = new ManagedAsyncJobExecutor();
    managedAsyncJobExecutor.setAsyncJobAcquisitionThread(new Thread());
    managedAsyncJobExecutor.setThreadFactory(mock(ManagedThreadFactory.class));

    // Act
    managedAsyncJobExecutor.initAsyncJobExecutionThreadPool();

    // Assert
    ExecutorService executorService = managedAsyncJobExecutor.getExecutorService();
    assertTrue(executorService instanceof ThreadPoolExecutor);
    assertEquals(0, ((ThreadPoolExecutor) executorService).getActiveCount());
    assertEquals(0, ((ThreadPoolExecutor) executorService).getLargestPoolSize());
    assertEquals(0, ((ThreadPoolExecutor) executorService).getPoolSize());
    assertEquals(0L, ((ThreadPoolExecutor) executorService).getCompletedTaskCount());
    assertEquals(0L, ((ThreadPoolExecutor) executorService).getTaskCount());
    assertEquals(10, ((ThreadPoolExecutor) executorService).getMaximumPoolSize());
    assertEquals(2, ((ThreadPoolExecutor) executorService).getCorePoolSize());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#stopExecutingAsyncJobs()}.
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#stopExecutingAsyncJobs()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.stopExecutingAsyncJobs()"})
  public void testStopExecutingAsyncJobs() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setExecutorService(ForkJoinPool.commonPool());

    // Act
    defaultAsyncJobExecutor.stopExecutingAsyncJobs();

    // Assert
    assertNull(defaultAsyncJobExecutor.getExecutorService());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#stopExecutingAsyncJobs()}.
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#stopExecutingAsyncJobs()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.stopExecutingAsyncJobs()"})
  public void testStopExecutingAsyncJobs2() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setExecutorService(new ForkJoinPool());

    // Act
    defaultAsyncJobExecutor.stopExecutingAsyncJobs();

    // Assert
    assertNull(defaultAsyncJobExecutor.getExecutorService());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#stopExecutingAsyncJobs()}.
   * <ul>
   *   <li>Given {@link DefaultAsyncJobExecutor} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#stopExecutingAsyncJobs()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.stopExecutingAsyncJobs()"})
  public void testStopExecutingAsyncJobs_givenDefaultAsyncJobExecutor() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();

    // Act
    defaultAsyncJobExecutor.stopExecutingAsyncJobs();

    // Assert that nothing has changed
    assertNull(defaultAsyncJobExecutor.getExecutorService());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#stopJobAcquisitionThread()}.
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#stopJobAcquisitionThread()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * <ul>
   *   <li>Given {@link DefaultAsyncJobExecutor} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#stopJobAcquisitionThread()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * <ul>
   *   <li>Then calls {@link Thread#join()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#stopJobAcquisitionThread()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.stopJobAcquisitionThread()"})
  public void testStopJobAcquisitionThread_thenCallsJoin() throws InterruptedException {
    // Arrange
    SimpleSSLSocketServer asyncJobAcquisitionThread = mock(SimpleSSLSocketServer.class);
    doThrow(new InterruptedException("foo")).when(asyncJobAcquisitionThread).join();

    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setAsyncJobAcquisitionThread(asyncJobAcquisitionThread);

    // Act
    defaultAsyncJobExecutor.stopJobAcquisitionThread();

    // Assert
    verify(asyncJobAcquisitionThread).join();
    assertNull(defaultAsyncJobExecutor.getAsyncJobAcquisitionThread());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#stopTimerAcquisitionThread()}.
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#stopTimerAcquisitionThread()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.stopTimerAcquisitionThread()"})
  public void testStopTimerAcquisitionThread() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setTimerJobAcquisitionThread(new Thread());

    // Act
    defaultAsyncJobExecutor.stopTimerAcquisitionThread();

    // Assert
    assertNull(defaultAsyncJobExecutor.getTimerJobAcquisitionThread());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#stopTimerAcquisitionThread()}.
   * <ul>
   *   <li>Given {@link DefaultAsyncJobExecutor} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#stopTimerAcquisitionThread()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.stopTimerAcquisitionThread()"})
  public void testStopTimerAcquisitionThread_givenDefaultAsyncJobExecutor() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();

    // Act
    defaultAsyncJobExecutor.stopTimerAcquisitionThread();

    // Assert that nothing has changed
    assertNull(defaultAsyncJobExecutor.getTimerJobAcquisitionThread());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#stopTimerAcquisitionThread()}.
   * <ul>
   *   <li>Then calls {@link Thread#join()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#stopTimerAcquisitionThread()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.stopTimerAcquisitionThread()"})
  public void testStopTimerAcquisitionThread_thenCallsJoin() throws InterruptedException {
    // Arrange
    SimpleSSLSocketServer timerJobAcquisitionThread = mock(SimpleSSLSocketServer.class);
    doThrow(new InterruptedException("foo")).when(timerJobAcquisitionThread).join();

    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setTimerJobAcquisitionThread(timerJobAcquisitionThread);

    // Act
    defaultAsyncJobExecutor.stopTimerAcquisitionThread();

    // Assert
    verify(timerJobAcquisitionThread).join();
    assertNull(defaultAsyncJobExecutor.getTimerJobAcquisitionThread());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#stopResetExpiredJobsThread()}.
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#stopResetExpiredJobsThread()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.stopResetExpiredJobsThread()"})
  public void testStopResetExpiredJobsThread() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setResetExpiredJobThread(new Thread());

    // Act
    defaultAsyncJobExecutor.stopResetExpiredJobsThread();

    // Assert
    assertNull(defaultAsyncJobExecutor.getResetExpiredJobThread());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#stopResetExpiredJobsThread()}.
   * <ul>
   *   <li>Given {@link DefaultAsyncJobExecutor} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#stopResetExpiredJobsThread()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.stopResetExpiredJobsThread()"})
  public void testStopResetExpiredJobsThread_givenDefaultAsyncJobExecutor() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();

    // Act
    defaultAsyncJobExecutor.stopResetExpiredJobsThread();

    // Assert that nothing has changed
    assertNull(defaultAsyncJobExecutor.getResetExpiredJobThread());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#stopResetExpiredJobsThread()}.
   * <ul>
   *   <li>Then calls {@link Thread#join()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#stopResetExpiredJobsThread()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.stopResetExpiredJobsThread()"})
  public void testStopResetExpiredJobsThread_thenCallsJoin() throws InterruptedException {
    // Arrange
    SimpleSSLSocketServer resetExpiredJobThread = mock(SimpleSSLSocketServer.class);
    doThrow(new InterruptedException("foo")).when(resetExpiredJobThread).join();

    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setResetExpiredJobThread(resetExpiredJobThread);

    // Act
    defaultAsyncJobExecutor.stopResetExpiredJobsThread();

    // Assert
    verify(resetExpiredJobThread).join();
    assertNull(defaultAsyncJobExecutor.getResetExpiredJobThread());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#applyConfig(ProcessEngineConfigurationImpl)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#applyConfig(ProcessEngineConfigurationImpl)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.applyConfig(ProcessEngineConfigurationImpl)"})
  public void testApplyConfig_givenNull() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
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
   * <ul>
   *   <li>Then {@link DefaultAsyncJobExecutor} (default constructor) ThreadPoolQueue is {@link LinkedBlockingDeque#LinkedBlockingDeque()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#applyConfig(ProcessEngineConfigurationImpl)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultAsyncJobExecutor.applyConfig(ProcessEngineConfigurationImpl)"})
  public void testApplyConfig_thenDefaultAsyncJobExecutorThreadPoolQueueIsLinkedBlockingDeque() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();

    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();
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
   * <ul>
   *   <li>When {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#applyConfig(ProcessEngineConfigurationImpl)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link DefaultAsyncJobExecutor#setAsyncJobAcquisitionThread(Thread)}
   *   <li>{@link DefaultAsyncJobExecutor#setAsyncJobLockTimeInMillis(int)}
   *   <li>{@link DefaultAsyncJobExecutor#setAsyncJobsDueRunnable(AcquireAsyncJobsDueRunnable)}
   *   <li>{@link DefaultAsyncJobExecutor#setCorePoolSize(int)}
   *   <li>{@link DefaultAsyncJobExecutor#setDefaultAsyncJobAcquireWaitTimeInMillis(int)}
   *   <li>{@link DefaultAsyncJobExecutor#setDefaultQueueSizeFullWaitTimeInMillis(int)}
   *   <li>{@link DefaultAsyncJobExecutor#setDefaultTimerJobAcquireWaitTimeInMillis(int)}
   *   <li>{@link DefaultAsyncJobExecutor#setExecuteAsyncRunnableFactory(ExecuteAsyncRunnableFactory)}
   *   <li>{@link DefaultAsyncJobExecutor#setExecutorService(ExecutorService)}
   *   <li>{@link DefaultAsyncJobExecutor#setKeepAliveTime(long)}
   *   <li>{@link DefaultAsyncJobExecutor#setLockOwner(String)}
   *   <li>{@link DefaultAsyncJobExecutor#setMaxAsyncJobsDuePerAcquisition(int)}
   *   <li>{@link DefaultAsyncJobExecutor#setMaxPoolSize(int)}
   *   <li>{@link DefaultAsyncJobExecutor#setMaxTimerJobsPerAcquisition(int)}
   *   <li>{@link DefaultAsyncJobExecutor#setMessageQueueMode(boolean)}
   *   <li>{@link DefaultAsyncJobExecutor#setProcessEngineConfiguration(ProcessEngineConfigurationImpl)}
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Thread DefaultAsyncJobExecutor.getAsyncJobAcquisitionThread()",
      "int DefaultAsyncJobExecutor.getAsyncJobLockTimeInMillis()", "int DefaultAsyncJobExecutor.getCorePoolSize()",
      "int DefaultAsyncJobExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis()",
      "int DefaultAsyncJobExecutor.getDefaultQueueSizeFullWaitTimeInMillis()",
      "int DefaultAsyncJobExecutor.getDefaultTimerJobAcquireWaitTimeInMillis()",
      "ExecuteAsyncRunnableFactory DefaultAsyncJobExecutor.getExecuteAsyncRunnableFactory()",
      "ExecutorService DefaultAsyncJobExecutor.getExecutorService()", "long DefaultAsyncJobExecutor.getKeepAliveTime()",
      "String DefaultAsyncJobExecutor.getLockOwner()", "int DefaultAsyncJobExecutor.getMaxAsyncJobsDuePerAcquisition()",
      "int DefaultAsyncJobExecutor.getMaxPoolSize()", "int DefaultAsyncJobExecutor.getMaxTimerJobsPerAcquisition()",
      "ProcessEngineConfigurationImpl DefaultAsyncJobExecutor.getProcessEngineConfiguration()",
      "int DefaultAsyncJobExecutor.getQueueSize()", "Thread DefaultAsyncJobExecutor.getResetExpiredJobThread()",
      "int DefaultAsyncJobExecutor.getResetExpiredJobsInterval()",
      "int DefaultAsyncJobExecutor.getResetExpiredJobsPageSize()",
      "int DefaultAsyncJobExecutor.getRetryWaitTimeInMillis()",
      "long DefaultAsyncJobExecutor.getSecondsToWaitOnShutdown()",
      "BlockingQueue DefaultAsyncJobExecutor.getThreadPoolQueue()",
      "Thread DefaultAsyncJobExecutor.getTimerJobAcquisitionThread()",
      "int DefaultAsyncJobExecutor.getTimerLockTimeInMillis()", "boolean DefaultAsyncJobExecutor.isActive()",
      "boolean DefaultAsyncJobExecutor.isAutoActivate()", "boolean DefaultAsyncJobExecutor.isMessageQueueMode()",
      "void DefaultAsyncJobExecutor.setAsyncJobAcquisitionThread(Thread)",
      "void DefaultAsyncJobExecutor.setAsyncJobLockTimeInMillis(int)",
      "void DefaultAsyncJobExecutor.setAsyncJobsDueRunnable(AcquireAsyncJobsDueRunnable)",
      "void DefaultAsyncJobExecutor.setAutoActivate(boolean)", "void DefaultAsyncJobExecutor.setCorePoolSize(int)",
      "void DefaultAsyncJobExecutor.setDefaultAsyncJobAcquireWaitTimeInMillis(int)",
      "void DefaultAsyncJobExecutor.setDefaultQueueSizeFullWaitTimeInMillis(int)",
      "void DefaultAsyncJobExecutor.setDefaultTimerJobAcquireWaitTimeInMillis(int)",
      "void DefaultAsyncJobExecutor.setExecuteAsyncRunnableFactory(ExecuteAsyncRunnableFactory)",
      "void DefaultAsyncJobExecutor.setExecutorService(ExecutorService)",
      "void DefaultAsyncJobExecutor.setKeepAliveTime(long)", "void DefaultAsyncJobExecutor.setLockOwner(String)",
      "void DefaultAsyncJobExecutor.setMaxAsyncJobsDuePerAcquisition(int)",
      "void DefaultAsyncJobExecutor.setMaxPoolSize(int)",
      "void DefaultAsyncJobExecutor.setMaxTimerJobsPerAcquisition(int)",
      "void DefaultAsyncJobExecutor.setMessageQueueMode(boolean)",
      "void DefaultAsyncJobExecutor.setProcessEngineConfiguration(ProcessEngineConfigurationImpl)",
      "void DefaultAsyncJobExecutor.setQueueSize(int)", "void DefaultAsyncJobExecutor.setResetExpiredJobThread(Thread)",
      "void DefaultAsyncJobExecutor.setResetExpiredJobsInterval(int)",
      "void DefaultAsyncJobExecutor.setResetExpiredJobsPageSize(int)",
      "void DefaultAsyncJobExecutor.setResetExpiredJobsRunnable(ResetExpiredJobsRunnable)",
      "void DefaultAsyncJobExecutor.setRetryWaitTimeInMillis(int)",
      "void DefaultAsyncJobExecutor.setSecondsToWaitOnShutdown(long)",
      "void DefaultAsyncJobExecutor.setThreadPoolQueue(BlockingQueue)",
      "void DefaultAsyncJobExecutor.setTimerJobAcquisitionThread(Thread)",
      "void DefaultAsyncJobExecutor.setTimerJobRunnable(AcquireTimerJobsRunnable)",
      "void DefaultAsyncJobExecutor.setTimerLockTimeInMillis(int)"})
  public void testGettersAndSetters() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    Thread asyncJobAcquisitionThread = new Thread();

    // Act
    defaultAsyncJobExecutor.setAsyncJobAcquisitionThread(asyncJobAcquisitionThread);
    defaultAsyncJobExecutor.setAsyncJobLockTimeInMillis(1);
    defaultAsyncJobExecutor.setAsyncJobsDueRunnable(new AcquireAsyncJobsDueRunnable(new DefaultAsyncJobExecutor()));
    defaultAsyncJobExecutor.setCorePoolSize(3);
    defaultAsyncJobExecutor.setDefaultAsyncJobAcquireWaitTimeInMillis(1);
    defaultAsyncJobExecutor.setDefaultQueueSizeFullWaitTimeInMillis(3);
    defaultAsyncJobExecutor.setDefaultTimerJobAcquireWaitTimeInMillis(1);
    ExecuteAsyncRunnableFactory executeAsyncRunnableFactory = mock(ExecuteAsyncRunnableFactory.class);
    defaultAsyncJobExecutor.setExecuteAsyncRunnableFactory(executeAsyncRunnableFactory);
    ForkJoinPool executorService = ForkJoinPool.commonPool();
    defaultAsyncJobExecutor.setExecutorService(executorService);
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
    defaultAsyncJobExecutor.setResetExpiredJobsRunnable(new ResetExpiredJobsRunnable(new DefaultAsyncJobExecutor()));
    defaultAsyncJobExecutor.setRetryWaitTimeInMillis(1);
    defaultAsyncJobExecutor.setSecondsToWaitOnShutdown(1L);
    defaultAsyncJobExecutor.setThreadPoolQueue(null);
    Thread timerJobAcquisitionThread = new Thread();
    defaultAsyncJobExecutor.setTimerJobAcquisitionThread(timerJobAcquisitionThread);
    DefaultAsyncJobExecutor asyncExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setTimerJobRunnable(new AcquireTimerJobsRunnable(asyncExecutor, new DefaultJobManager()));
    defaultAsyncJobExecutor.setTimerLockTimeInMillis(1);
    defaultAsyncJobExecutor.setAutoActivate(true);
    Thread actualAsyncJobAcquisitionThread = defaultAsyncJobExecutor.getAsyncJobAcquisitionThread();
    int actualAsyncJobLockTimeInMillis = defaultAsyncJobExecutor.getAsyncJobLockTimeInMillis();
    int actualCorePoolSize = defaultAsyncJobExecutor.getCorePoolSize();
    int actualDefaultAsyncJobAcquireWaitTimeInMillis = defaultAsyncJobExecutor
        .getDefaultAsyncJobAcquireWaitTimeInMillis();
    int actualDefaultQueueSizeFullWaitTimeInMillis = defaultAsyncJobExecutor.getDefaultQueueSizeFullWaitTimeInMillis();
    int actualDefaultTimerJobAcquireWaitTimeInMillis = defaultAsyncJobExecutor
        .getDefaultTimerJobAcquireWaitTimeInMillis();
    ExecuteAsyncRunnableFactory actualExecuteAsyncRunnableFactory = defaultAsyncJobExecutor
        .getExecuteAsyncRunnableFactory();
    ExecutorService actualExecutorService = defaultAsyncJobExecutor.getExecutorService();
    long actualKeepAliveTime = defaultAsyncJobExecutor.getKeepAliveTime();
    String actualLockOwner = defaultAsyncJobExecutor.getLockOwner();
    int actualMaxAsyncJobsDuePerAcquisition = defaultAsyncJobExecutor.getMaxAsyncJobsDuePerAcquisition();
    int actualMaxPoolSize = defaultAsyncJobExecutor.getMaxPoolSize();
    int actualMaxTimerJobsPerAcquisition = defaultAsyncJobExecutor.getMaxTimerJobsPerAcquisition();
    ProcessEngineConfigurationImpl actualProcessEngineConfiguration = defaultAsyncJobExecutor
        .getProcessEngineConfiguration();
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
    assertSame(executorService, actualExecutorService);
    assertSame(executeAsyncRunnableFactory, actualExecuteAsyncRunnableFactory);
  }

  /**
   * Test new {@link DefaultAsyncJobExecutor} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link DefaultAsyncJobExecutor}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
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
