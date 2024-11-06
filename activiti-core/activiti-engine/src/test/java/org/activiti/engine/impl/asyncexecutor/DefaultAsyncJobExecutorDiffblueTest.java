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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import ch.qos.logback.classic.net.SimpleSSLSocketServer;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import org.activiti.engine.impl.asyncexecutor.multitenant.SharedExecutorServiceAsyncExecutor;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityImpl;
import org.activiti.engine.impl.persistence.entity.JobEntityImpl;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.test.cfg.multitenant.DummyTenantInfoHolder;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultAsyncJobExecutorDiffblueTest {
  @InjectMocks
  private DefaultAsyncJobExecutor defaultAsyncJobExecutor;

  /**
   * Test {@link DefaultAsyncJobExecutor#executeAsyncJob(Job)}.
   * <ul>
   *   <li>When {@link DeadLetterJobEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#executeAsyncJob(Job)}
   */
  @Test
  public void testExecuteAsyncJob_whenDeadLetterJobEntityImpl() {
    // Arrange, Act and Assert
    assertTrue(defaultAsyncJobExecutor.executeAsyncJob(new DeadLetterJobEntityImpl()));
    assertTrue(defaultAsyncJobExecutor.executeAsyncJob(mock(DeadLetterJobEntityImpl.class)));
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#createRunnableForJob(Job)}.
   * <ul>
   *   <li>Given {@code true}.</li>
   *   <li>Then {@link ExecuteAsyncRunnable#job} return {@link JobEntityImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#createRunnableForJob(Job)}
   */
  @Test
  public void testCreateRunnableForJob_givenTrue_thenJobReturnJobEntityImpl() {
    // Arrange
    JobEntityImpl job = new JobEntityImpl();
    job.setDeleted(true);
    Date duedate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    job.setDuedate(duedate);
    job.setEndDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    job.setExceptionMessage("An error occurred");
    job.setExclusive(true);
    job.setExecutionId("42");
    job.setId("42");
    job.setInserted(true);
    job.setJobHandlerConfiguration("Job Handler Configuration");
    job.setJobHandlerType("Job Handler Type");
    job.setJobType("Job Type");
    Date claimedUntil = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    job.setLockExpirationTime(claimedUntil);
    job.setLockOwner("Claimed By");
    job.setMaxIterations(3);
    job.setProcessDefinitionId("42");
    job.setProcessInstanceId("42");
    job.setRepeat("Repeat");
    job.setRetries(1);
    job.setRevision(1);
    job.setTenantId("42");
    job.setUpdated(true);

    // Act
    Runnable actualCreateRunnableForJobResult = defaultAsyncJobExecutor.createRunnableForJob(job);

    // Assert
    Job job2 = ((ExecuteAsyncRunnable) actualCreateRunnableForJobResult).job;
    Object persistentState = ((JobEntityImpl) job2).getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateRunnableForJobResult instanceof ExecuteAsyncRunnable);
    assertTrue(job2 instanceof JobEntityImpl);
    assertEquals("42", job2.getExecutionId());
    assertEquals("42", job2.getId());
    assertEquals("42", job2.getProcessDefinitionId());
    assertEquals("42", job2.getProcessInstanceId());
    assertEquals("42", job2.getTenantId());
    assertEquals("An error occurred", job2.getExceptionMessage());
    assertEquals("Claimed By", ((JobEntityImpl) job2).getLockOwner());
    assertEquals(5, ((Map<String, Object>) persistentState).size());
    assertEquals("Claimed By", ((Map<String, Object>) persistentState).get("lockOwner"));
    assertEquals("Job Handler Configuration", job2.getJobHandlerConfiguration());
    assertEquals("Job Handler Type", job2.getJobHandlerType());
    assertEquals("Job Type", job2.getJobType());
    assertEquals("Repeat", ((JobEntityImpl) job2).getRepeat());
    assertEquals(1, job2.getRetries());
    assertEquals(3, ((JobEntityImpl) job2).getMaxIterations());
    assertTrue(((Map<String, Object>) persistentState).containsKey("retries"));
    assertTrue(((JobEntityImpl) job2).isDeleted());
    assertTrue(((JobEntityImpl) job2).isInserted());
    assertTrue(((JobEntityImpl) job2).isUpdated());
    assertSame(duedate, ((Map<String, Object>) persistentState).get("duedate"));
    assertSame(claimedUntil, ((Map<String, Object>) persistentState).get("lockExpirationTime"));
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#createRunnableForJob(Job)}.
   * <ul>
   *   <li>Then {@link ExecuteAsyncRunnable#job} return
   * {@link DeadLetterJobEntityImpl}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#createRunnableForJob(Job)}
   */
  @Test
  public void testCreateRunnableForJob_thenJobReturnDeadLetterJobEntityImpl() {
    // Arrange and Act
    Runnable actualCreateRunnableForJobResult = defaultAsyncJobExecutor
        .createRunnableForJob(new DeadLetterJobEntityImpl());

    // Assert
    Job job = ((ExecuteAsyncRunnable) actualCreateRunnableForJobResult).job;
    Object persistentState = ((DeadLetterJobEntityImpl) job).getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(actualCreateRunnableForJobResult instanceof ExecuteAsyncRunnable);
    assertTrue(job instanceof DeadLetterJobEntityImpl);
    assertEquals("", job.getTenantId());
    assertEquals(3, ((Map<String, Integer>) persistentState).size());
    assertNull(((Map<String, Integer>) persistentState).get("duedate"));
    assertNull(((Map<String, Integer>) persistentState).get("exceptionMessage"));
    assertNull(((DeadLetterJobEntityImpl) job).getRepeat());
    assertNull(job.getExceptionMessage());
    assertNull(job.getExecutionId());
    assertNull(job.getId());
    assertNull(job.getJobHandlerConfiguration());
    assertNull(job.getJobHandlerType());
    assertNull(job.getJobType());
    assertNull(job.getProcessDefinitionId());
    assertNull(job.getProcessInstanceId());
    assertNull(((ExecuteAsyncRunnable) actualCreateRunnableForJobResult).jobId);
    assertNull(((DeadLetterJobEntityImpl) job).getEndDate());
    assertNull(job.getDuedate());
    assertEquals(0, ((Map<String, Integer>) persistentState).get("retries").intValue());
    assertEquals(0, ((DeadLetterJobEntityImpl) job).getMaxIterations());
    assertEquals(0, job.getRetries());
    assertFalse(((DeadLetterJobEntityImpl) job).isDeleted());
    assertFalse(((DeadLetterJobEntityImpl) job).isInserted());
    assertFalse(((DeadLetterJobEntityImpl) job).isUpdated());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#createRunnableForJob(Job)}.
   * <ul>
   *   <li>Then return {@link ExecuteAsyncRunnable#jobId} is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#createRunnableForJob(Job)}
   */
  @Test
  public void testCreateRunnableForJob_thenReturnJobIdIs42() {
    // Arrange
    DeadLetterJobEntityImpl job = mock(DeadLetterJobEntityImpl.class);
    when(job.getId()).thenReturn("42");

    // Act
    Runnable actualCreateRunnableForJobResult = defaultAsyncJobExecutor.createRunnableForJob(job);

    // Assert
    verify(job).getId();
    assertTrue(actualCreateRunnableForJobResult instanceof ExecuteAsyncRunnable);
    assertEquals("42", ((ExecuteAsyncRunnable) actualCreateRunnableForJobResult).jobId);
    assertNull(((ExecuteAsyncRunnable) actualCreateRunnableForJobResult).processEngineConfiguration);
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#start()}.
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#start()}
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
   * Test {@link DefaultAsyncJobExecutor#start()}.
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#start()}
   */
  @Test
  public void testStart2() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    DefaultAsyncJobExecutor asyncExecutor = new DefaultAsyncJobExecutor();
    AcquireTimerJobsRunnable timerJobRunnable = new AcquireTimerJobsRunnable(asyncExecutor, new DefaultJobManager());

    defaultAsyncJobExecutor.setTimerJobRunnable(timerJobRunnable);

    // Act
    defaultAsyncJobExecutor.start();

    // Assert
    AcquireTimerJobsRunnable acquireTimerJobsRunnable = defaultAsyncJobExecutor.timerJobRunnable;
    AsyncExecutor asyncExecutor2 = acquireTimerJobsRunnable.asyncExecutor;
    assertTrue(asyncExecutor2 instanceof DefaultAsyncJobExecutor);
    JobManager jobManager = acquireTimerJobsRunnable.jobManager;
    assertTrue(jobManager instanceof DefaultJobManager);
    assertEquals(0L, defaultAsyncJobExecutor.asyncJobsDueRunnable.getMillisToWait());
    assertEquals(0L, acquireTimerJobsRunnable.getMillisToWait());
    assertFalse(acquireTimerJobsRunnable.isWaiting.get());
    assertFalse(acquireTimerJobsRunnable.isInterrupted);
    assertSame(timerJobRunnable.asyncExecutor, asyncExecutor2);
    assertSame(timerJobRunnable.jobManager, jobManager);
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#initAsyncJobExecutionThreadPool()}.
   * <p>
   * Method under test:
   * {@link DefaultAsyncJobExecutor#initAsyncJobExecutionThreadPool()}
   */
  @Test
  public void testInitAsyncJobExecutionThreadPool() {
    // Arrange and Act
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
    assertEquals(0, ((ThreadPoolExecutor) executorService).getActiveCount());
    assertEquals(0, ((ThreadPoolExecutor) executorService).getLargestPoolSize());
    assertEquals(0, ((ThreadPoolExecutor) executorService).getPoolSize());
    assertEquals(0L, ((ThreadPoolExecutor) executorService).getCompletedTaskCount());
    assertEquals(0L, ((ThreadPoolExecutor) executorService).getTaskCount());
    assertEquals(0L, ((BasicThreadFactory) threadFactory).getThreadCount());
    assertEquals(10, ((ThreadPoolExecutor) executorService).getMaximumPoolSize());
    assertEquals(2, ((ThreadPoolExecutor) executorService).getCorePoolSize());
    BlockingQueue<Runnable> threadPoolQueue = defaultAsyncJobExecutor.getThreadPoolQueue();
    assertTrue(threadPoolQueue.isEmpty());
    assertSame(threadPoolQueue, ((ThreadPoolExecutor) executorService).getQueue());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#stopExecutingAsyncJobs()}.
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#stopExecutingAsyncJobs()}
   */
  @Test
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
  public void testStopExecutingAsyncJobs2() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setExecuteAsyncRunnableFactory(mock(ExecuteAsyncRunnableFactory.class));

    // Act
    defaultAsyncJobExecutor.stopExecutingAsyncJobs();

    // Assert that nothing has changed
    assertNull(defaultAsyncJobExecutor.getExecutorService());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#stopExecutingAsyncJobs()}.
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#stopExecutingAsyncJobs()}
   */
  @Test
  public void testStopExecutingAsyncJobs3() {
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
   * <p>
   * Method under test: {@link DefaultAsyncJobExecutor#stopJobAcquisitionThread()}
   */
  @Test
  public void testStopJobAcquisitionThread2() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setExecuteAsyncRunnableFactory(mock(ExecuteAsyncRunnableFactory.class));

    // Act
    defaultAsyncJobExecutor.stopJobAcquisitionThread();

    // Assert that nothing has changed
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
   * Method under test:
   * {@link DefaultAsyncJobExecutor#stopTimerAcquisitionThread()}
   */
  @Test
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
   * <p>
   * Method under test:
   * {@link DefaultAsyncJobExecutor#stopTimerAcquisitionThread()}
   */
  @Test
  public void testStopTimerAcquisitionThread2() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setExecuteAsyncRunnableFactory(mock(ExecuteAsyncRunnableFactory.class));

    // Act
    defaultAsyncJobExecutor.stopTimerAcquisitionThread();

    // Assert that nothing has changed
    assertNull(defaultAsyncJobExecutor.getTimerJobAcquisitionThread());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#stopTimerAcquisitionThread()}.
   * <ul>
   *   <li>Given {@link DefaultAsyncJobExecutor} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultAsyncJobExecutor#stopTimerAcquisitionThread()}
   */
  @Test
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
   * Method under test:
   * {@link DefaultAsyncJobExecutor#stopTimerAcquisitionThread()}
   */
  @Test
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
   * Method under test:
   * {@link DefaultAsyncJobExecutor#stopResetExpiredJobsThread()}
   */
  @Test
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
   * <p>
   * Method under test:
   * {@link DefaultAsyncJobExecutor#stopResetExpiredJobsThread()}
   */
  @Test
  public void testStopResetExpiredJobsThread2() {
    // Arrange
    DefaultAsyncJobExecutor defaultAsyncJobExecutor = new DefaultAsyncJobExecutor();
    defaultAsyncJobExecutor.setExecuteAsyncRunnableFactory(mock(ExecuteAsyncRunnableFactory.class));

    // Act
    defaultAsyncJobExecutor.stopResetExpiredJobsThread();

    // Assert that nothing has changed
    assertNull(defaultAsyncJobExecutor.getResetExpiredJobThread());
  }

  /**
   * Test {@link DefaultAsyncJobExecutor#stopResetExpiredJobsThread()}.
   * <ul>
   *   <li>Given {@link DefaultAsyncJobExecutor} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultAsyncJobExecutor#stopResetExpiredJobsThread()}
   */
  @Test
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
   * Method under test:
   * {@link DefaultAsyncJobExecutor#stopResetExpiredJobsThread()}
   */
  @Test
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
   * Test
   * {@link DefaultAsyncJobExecutor#applyConfig(ProcessEngineConfigurationImpl)}.
   * <p>
   * Method under test:
   * {@link DefaultAsyncJobExecutor#applyConfig(ProcessEngineConfigurationImpl)}
   */
  @Test
  public void testApplyConfig() {
    // Arrange and Act
    defaultAsyncJobExecutor.applyConfig(new JtaProcessEngineConfiguration());

    // Assert
    assertEquals(0, defaultAsyncJobExecutor.getDefaultQueueSizeFullWaitTimeInMillis());
    assertEquals(10, defaultAsyncJobExecutor.getMaxPoolSize());
    assertEquals(10, defaultAsyncJobExecutor.getRetryWaitTimeInMillis());
    assertEquals(100, defaultAsyncJobExecutor.getQueueSize());
    assertEquals(10000, defaultAsyncJobExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis());
    assertEquals(10000, defaultAsyncJobExecutor.getDefaultTimerJobAcquireWaitTimeInMillis());
    assertEquals(2, defaultAsyncJobExecutor.getCorePoolSize());
    assertEquals(300000, defaultAsyncJobExecutor.getAsyncJobLockTimeInMillis());
    assertEquals(300000, defaultAsyncJobExecutor.getTimerLockTimeInMillis());
    assertEquals(5000L, defaultAsyncJobExecutor.getKeepAliveTime());
    assertEquals(60000, defaultAsyncJobExecutor.getResetExpiredJobsInterval());
    assertEquals(60L, defaultAsyncJobExecutor.getSecondsToWaitOnShutdown());
    assertFalse(defaultAsyncJobExecutor.isMessageQueueMode());
  }

  /**
   * Test
   * {@link DefaultAsyncJobExecutor#applyConfig(ProcessEngineConfigurationImpl)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>Then {@link DefaultAsyncJobExecutor} ThreadPoolQueue is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultAsyncJobExecutor#applyConfig(ProcessEngineConfigurationImpl)}
   */
  @Test
  public void testApplyConfig_givenNull_thenDefaultAsyncJobExecutorThreadPoolQueueIsNull() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.isAsyncExecutorIsMessageQueueMode()).thenReturn(true);
    when(processEngineConfiguration.getAsyncFailedJobWaitTime()).thenReturn(1);
    when(processEngineConfiguration.getAsyncExecutorAsyncJobLockTimeInMillis()).thenReturn(1);
    when(processEngineConfiguration.getAsyncExecutorCorePoolSize()).thenReturn(3);
    when(processEngineConfiguration.getAsyncExecutorDefaultAsyncJobAcquireWaitTime()).thenReturn(1);
    when(processEngineConfiguration.getAsyncExecutorDefaultQueueSizeFullWaitTime()).thenReturn(3);
    when(processEngineConfiguration.getAsyncExecutorDefaultTimerJobAcquireWaitTime()).thenReturn(1);
    when(processEngineConfiguration.getAsyncExecutorMaxAsyncJobsDuePerAcquisition()).thenReturn(1);
    when(processEngineConfiguration.getAsyncExecutorMaxPoolSize()).thenReturn(3);
    when(processEngineConfiguration.getAsyncExecutorMaxTimerJobsPerAcquisition()).thenReturn(1);
    when(processEngineConfiguration.getAsyncExecutorResetExpiredJobsInterval()).thenReturn(42);
    when(processEngineConfiguration.getAsyncExecutorResetExpiredJobsPageSize()).thenReturn(3);
    when(processEngineConfiguration.getAsyncExecutorThreadPoolQueueSize()).thenReturn(3);
    when(processEngineConfiguration.getAsyncExecutorTimerLockTimeInMillis()).thenReturn(1);
    when(processEngineConfiguration.getAsyncExecutorLockOwner()).thenReturn("Async Executor Lock Owner");
    when(processEngineConfiguration.getAsyncExecutorThreadPoolQueue()).thenReturn(null);
    when(processEngineConfiguration.getAsyncExecutorSecondsToWaitOnShutdown()).thenReturn(1L);
    when(processEngineConfiguration.getAsyncExecutorThreadKeepAliveTime()).thenReturn(1L);

    // Act
    defaultAsyncJobExecutor.applyConfig(processEngineConfiguration);

    // Assert
    verify(processEngineConfiguration).getAsyncFailedJobWaitTime();
    verify(processEngineConfiguration).getAsyncExecutorAsyncJobLockTimeInMillis();
    verify(processEngineConfiguration).getAsyncExecutorCorePoolSize();
    verify(processEngineConfiguration).getAsyncExecutorDefaultAsyncJobAcquireWaitTime();
    verify(processEngineConfiguration).getAsyncExecutorDefaultQueueSizeFullWaitTime();
    verify(processEngineConfiguration).getAsyncExecutorDefaultTimerJobAcquireWaitTime();
    verify(processEngineConfiguration, atLeast(1)).getAsyncExecutorLockOwner();
    verify(processEngineConfiguration).getAsyncExecutorMaxAsyncJobsDuePerAcquisition();
    verify(processEngineConfiguration).getAsyncExecutorMaxPoolSize();
    verify(processEngineConfiguration).getAsyncExecutorMaxTimerJobsPerAcquisition();
    verify(processEngineConfiguration).getAsyncExecutorResetExpiredJobsInterval();
    verify(processEngineConfiguration).getAsyncExecutorResetExpiredJobsPageSize();
    verify(processEngineConfiguration).getAsyncExecutorSecondsToWaitOnShutdown();
    verify(processEngineConfiguration).getAsyncExecutorThreadKeepAliveTime();
    verify(processEngineConfiguration).getAsyncExecutorThreadPoolQueue();
    verify(processEngineConfiguration).getAsyncExecutorThreadPoolQueueSize();
    verify(processEngineConfiguration).getAsyncExecutorTimerLockTimeInMillis();
    verify(processEngineConfiguration).isAsyncExecutorIsMessageQueueMode();
    assertEquals("Async Executor Lock Owner", defaultAsyncJobExecutor.getLockOwner());
    assertNull(defaultAsyncJobExecutor.getThreadPoolQueue());
    assertEquals(1, defaultAsyncJobExecutor.getAsyncJobLockTimeInMillis());
    assertEquals(1, defaultAsyncJobExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis());
    assertEquals(1, defaultAsyncJobExecutor.getDefaultTimerJobAcquireWaitTimeInMillis());
    assertEquals(1, defaultAsyncJobExecutor.getRetryWaitTimeInMillis());
    assertEquals(1, defaultAsyncJobExecutor.getTimerLockTimeInMillis());
    assertEquals(1L, defaultAsyncJobExecutor.getKeepAliveTime());
    assertEquals(1L, defaultAsyncJobExecutor.getSecondsToWaitOnShutdown());
    assertEquals(3, defaultAsyncJobExecutor.getCorePoolSize());
    assertEquals(3, defaultAsyncJobExecutor.getDefaultQueueSizeFullWaitTimeInMillis());
    assertEquals(3, defaultAsyncJobExecutor.getMaxPoolSize());
    assertEquals(3, defaultAsyncJobExecutor.getQueueSize());
    assertEquals(42, defaultAsyncJobExecutor.getResetExpiredJobsInterval());
    assertTrue(defaultAsyncJobExecutor.isMessageQueueMode());
  }

  /**
   * Test
   * {@link DefaultAsyncJobExecutor#applyConfig(ProcessEngineConfigurationImpl)}.
   * <ul>
   *   <li>Then {@link DefaultAsyncJobExecutor} ThreadPoolQueue Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultAsyncJobExecutor#applyConfig(ProcessEngineConfigurationImpl)}
   */
  @Test
  public void testApplyConfig_thenDefaultAsyncJobExecutorThreadPoolQueueEmpty() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = mock(JtaProcessEngineConfiguration.class);
    when(processEngineConfiguration.isAsyncExecutorIsMessageQueueMode()).thenReturn(true);
    when(processEngineConfiguration.getAsyncFailedJobWaitTime()).thenReturn(1);
    when(processEngineConfiguration.getAsyncExecutorAsyncJobLockTimeInMillis()).thenReturn(1);
    when(processEngineConfiguration.getAsyncExecutorCorePoolSize()).thenReturn(3);
    when(processEngineConfiguration.getAsyncExecutorDefaultAsyncJobAcquireWaitTime()).thenReturn(1);
    when(processEngineConfiguration.getAsyncExecutorDefaultQueueSizeFullWaitTime()).thenReturn(3);
    when(processEngineConfiguration.getAsyncExecutorDefaultTimerJobAcquireWaitTime()).thenReturn(1);
    when(processEngineConfiguration.getAsyncExecutorMaxAsyncJobsDuePerAcquisition()).thenReturn(1);
    when(processEngineConfiguration.getAsyncExecutorMaxPoolSize()).thenReturn(3);
    when(processEngineConfiguration.getAsyncExecutorMaxTimerJobsPerAcquisition()).thenReturn(1);
    when(processEngineConfiguration.getAsyncExecutorResetExpiredJobsInterval()).thenReturn(42);
    when(processEngineConfiguration.getAsyncExecutorResetExpiredJobsPageSize()).thenReturn(3);
    when(processEngineConfiguration.getAsyncExecutorThreadPoolQueueSize()).thenReturn(3);
    when(processEngineConfiguration.getAsyncExecutorTimerLockTimeInMillis()).thenReturn(1);
    when(processEngineConfiguration.getAsyncExecutorLockOwner()).thenReturn("Async Executor Lock Owner");
    LinkedBlockingDeque<Runnable> linkedBlockingDeque = new LinkedBlockingDeque<>();
    when(processEngineConfiguration.getAsyncExecutorThreadPoolQueue()).thenReturn(linkedBlockingDeque);
    when(processEngineConfiguration.getAsyncExecutorSecondsToWaitOnShutdown()).thenReturn(1L);
    when(processEngineConfiguration.getAsyncExecutorThreadKeepAliveTime()).thenReturn(1L);

    // Act
    defaultAsyncJobExecutor.applyConfig(processEngineConfiguration);

    // Assert
    verify(processEngineConfiguration).getAsyncFailedJobWaitTime();
    verify(processEngineConfiguration).getAsyncExecutorAsyncJobLockTimeInMillis();
    verify(processEngineConfiguration).getAsyncExecutorCorePoolSize();
    verify(processEngineConfiguration).getAsyncExecutorDefaultAsyncJobAcquireWaitTime();
    verify(processEngineConfiguration).getAsyncExecutorDefaultQueueSizeFullWaitTime();
    verify(processEngineConfiguration).getAsyncExecutorDefaultTimerJobAcquireWaitTime();
    verify(processEngineConfiguration, atLeast(1)).getAsyncExecutorLockOwner();
    verify(processEngineConfiguration).getAsyncExecutorMaxAsyncJobsDuePerAcquisition();
    verify(processEngineConfiguration).getAsyncExecutorMaxPoolSize();
    verify(processEngineConfiguration).getAsyncExecutorMaxTimerJobsPerAcquisition();
    verify(processEngineConfiguration).getAsyncExecutorResetExpiredJobsInterval();
    verify(processEngineConfiguration).getAsyncExecutorResetExpiredJobsPageSize();
    verify(processEngineConfiguration).getAsyncExecutorSecondsToWaitOnShutdown();
    verify(processEngineConfiguration).getAsyncExecutorThreadKeepAliveTime();
    verify(processEngineConfiguration, atLeast(1)).getAsyncExecutorThreadPoolQueue();
    verify(processEngineConfiguration).getAsyncExecutorThreadPoolQueueSize();
    verify(processEngineConfiguration).getAsyncExecutorTimerLockTimeInMillis();
    verify(processEngineConfiguration).isAsyncExecutorIsMessageQueueMode();
    assertEquals("Async Executor Lock Owner", defaultAsyncJobExecutor.getLockOwner());
    assertEquals(1, defaultAsyncJobExecutor.getAsyncJobLockTimeInMillis());
    assertEquals(1, defaultAsyncJobExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis());
    assertEquals(1, defaultAsyncJobExecutor.getDefaultTimerJobAcquireWaitTimeInMillis());
    assertEquals(1, defaultAsyncJobExecutor.getRetryWaitTimeInMillis());
    assertEquals(1, defaultAsyncJobExecutor.getTimerLockTimeInMillis());
    assertEquals(1L, defaultAsyncJobExecutor.getKeepAliveTime());
    assertEquals(1L, defaultAsyncJobExecutor.getSecondsToWaitOnShutdown());
    assertEquals(3, defaultAsyncJobExecutor.getCorePoolSize());
    assertEquals(3, defaultAsyncJobExecutor.getDefaultQueueSizeFullWaitTimeInMillis());
    assertEquals(3, defaultAsyncJobExecutor.getMaxPoolSize());
    assertEquals(3, defaultAsyncJobExecutor.getQueueSize());
    assertEquals(42, defaultAsyncJobExecutor.getResetExpiredJobsInterval());
    BlockingQueue<Runnable> threadPoolQueue = defaultAsyncJobExecutor.getThreadPoolQueue();
    assertTrue(threadPoolQueue.isEmpty());
    assertTrue(defaultAsyncJobExecutor.isMessageQueueMode());
    assertSame(linkedBlockingDeque, threadPoolQueue);
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link DefaultAsyncJobExecutor#setAsyncJobAcquisitionThread(Thread)}
   *   <li>{@link DefaultAsyncJobExecutor#setAsyncJobLockTimeInMillis(int)}
   *   <li>
   * {@link DefaultAsyncJobExecutor#setAsyncJobsDueRunnable(AcquireAsyncJobsDueRunnable)}
   *   <li>{@link DefaultAsyncJobExecutor#setCorePoolSize(int)}
   *   <li>
   * {@link DefaultAsyncJobExecutor#setDefaultAsyncJobAcquireWaitTimeInMillis(int)}
   *   <li>
   * {@link DefaultAsyncJobExecutor#setDefaultQueueSizeFullWaitTimeInMillis(int)}
   *   <li>
   * {@link DefaultAsyncJobExecutor#setDefaultTimerJobAcquireWaitTimeInMillis(int)}
   *   <li>
   * {@link DefaultAsyncJobExecutor#setExecuteAsyncRunnableFactory(ExecuteAsyncRunnableFactory)}
   *   <li>{@link DefaultAsyncJobExecutor#setExecutorService(ExecutorService)}
   *   <li>{@link DefaultAsyncJobExecutor#setKeepAliveTime(long)}
   *   <li>{@link DefaultAsyncJobExecutor#setLockOwner(String)}
   *   <li>{@link DefaultAsyncJobExecutor#setMaxAsyncJobsDuePerAcquisition(int)}
   *   <li>{@link DefaultAsyncJobExecutor#setMaxPoolSize(int)}
   *   <li>{@link DefaultAsyncJobExecutor#setMaxTimerJobsPerAcquisition(int)}
   *   <li>{@link DefaultAsyncJobExecutor#setMessageQueueMode(boolean)}
   *   <li>
   * {@link DefaultAsyncJobExecutor#setProcessEngineConfiguration(ProcessEngineConfigurationImpl)}
   *   <li>{@link DefaultAsyncJobExecutor#setQueueSize(int)}
   *   <li>{@link DefaultAsyncJobExecutor#setResetExpiredJobThread(Thread)}
   *   <li>{@link DefaultAsyncJobExecutor#setResetExpiredJobsInterval(int)}
   *   <li>{@link DefaultAsyncJobExecutor#setResetExpiredJobsPageSize(int)}
   *   <li>
   * {@link DefaultAsyncJobExecutor#setResetExpiredJobsRunnable(ResetExpiredJobsRunnable)}
   *   <li>{@link DefaultAsyncJobExecutor#setRetryWaitTimeInMillis(int)}
   *   <li>{@link DefaultAsyncJobExecutor#setSecondsToWaitOnShutdown(long)}
   *   <li>{@link DefaultAsyncJobExecutor#setThreadPoolQueue(BlockingQueue)}
   *   <li>{@link DefaultAsyncJobExecutor#setTimerJobAcquisitionThread(Thread)}
   *   <li>
   * {@link DefaultAsyncJobExecutor#setTimerJobRunnable(AcquireTimerJobsRunnable)}
   *   <li>{@link DefaultAsyncJobExecutor#setTimerLockTimeInMillis(int)}
   *   <li>{@link DefaultAsyncJobExecutor#setAutoActivate(boolean)}
   *   <li>{@link DefaultAsyncJobExecutor#getAsyncJobAcquisitionThread()}
   *   <li>{@link DefaultAsyncJobExecutor#getAsyncJobLockTimeInMillis()}
   *   <li>{@link DefaultAsyncJobExecutor#getCorePoolSize()}
   *   <li>
   * {@link DefaultAsyncJobExecutor#getDefaultAsyncJobAcquireWaitTimeInMillis()}
   *   <li>{@link DefaultAsyncJobExecutor#getDefaultQueueSizeFullWaitTimeInMillis()}
   *   <li>
   * {@link DefaultAsyncJobExecutor#getDefaultTimerJobAcquireWaitTimeInMillis()}
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
    defaultAsyncJobExecutor.getThreadPoolQueue();
    Thread actualTimerJobAcquisitionThread = defaultAsyncJobExecutor.getTimerJobAcquisitionThread();
    int actualTimerLockTimeInMillis = defaultAsyncJobExecutor.getTimerLockTimeInMillis();
    boolean actualIsActiveResult = defaultAsyncJobExecutor.isActive();
    boolean actualIsAutoActivateResult = defaultAsyncJobExecutor.isAutoActivate();

    // Assert that nothing has changed
    assertEquals("Lock Owner", actualLockOwner);
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
   * Method under test: default or parameterless constructor of
   * {@link DefaultAsyncJobExecutor}
   */
  @Test
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
