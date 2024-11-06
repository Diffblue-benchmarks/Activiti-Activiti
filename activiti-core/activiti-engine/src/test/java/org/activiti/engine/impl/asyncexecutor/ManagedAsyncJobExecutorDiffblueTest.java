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
import static org.mockito.Mockito.mock;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import javax.enterprise.concurrent.ManagedThreadFactory;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.junit.Test;

public class ManagedAsyncJobExecutorDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ManagedAsyncJobExecutor#setThreadFactory(ManagedThreadFactory)}
   *   <li>{@link ManagedAsyncJobExecutor#getThreadFactory()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    ManagedAsyncJobExecutor managedAsyncJobExecutor = new ManagedAsyncJobExecutor();
    ManagedThreadFactory threadFactory = mock(ManagedThreadFactory.class);

    // Act
    managedAsyncJobExecutor.setThreadFactory(threadFactory);

    // Assert that nothing has changed
    assertSame(threadFactory, managedAsyncJobExecutor.getThreadFactory());
  }

  /**
   * Test {@link ManagedAsyncJobExecutor#initAsyncJobExecutionThreadPool()}.
   * <p>
   * Method under test:
   * {@link ManagedAsyncJobExecutor#initAsyncJobExecutionThreadPool()}
   */
  @Test
  public void testInitAsyncJobExecutionThreadPool() {
    // Arrange
    ManagedAsyncJobExecutor managedAsyncJobExecutor = new ManagedAsyncJobExecutor();

    // Act
    managedAsyncJobExecutor.initAsyncJobExecutionThreadPool();

    // Assert
    ExecutorService executorService = managedAsyncJobExecutor.getExecutorService();
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
   * Test {@link ManagedAsyncJobExecutor#initAsyncJobExecutionThreadPool()}.
   * <p>
   * Method under test:
   * {@link ManagedAsyncJobExecutor#initAsyncJobExecutionThreadPool()}
   */
  @Test
  public void testInitAsyncJobExecutionThreadPool2() {
    // Arrange
    ManagedAsyncJobExecutor managedAsyncJobExecutor = new ManagedAsyncJobExecutor();
    managedAsyncJobExecutor.setThreadFactory(null);
    managedAsyncJobExecutor.setThreadPoolQueue(null);
    ForkJoinPool executorService = ForkJoinPool.commonPool();
    managedAsyncJobExecutor.setExecutorService(executorService);

    // Act
    managedAsyncJobExecutor.initAsyncJobExecutionThreadPool();

    // Assert
    ExecutorService executorService2 = managedAsyncJobExecutor.getExecutorService();
    assertTrue(executorService2 instanceof ForkJoinPool);
    assertTrue(managedAsyncJobExecutor.getThreadPoolQueue().isEmpty());
    assertSame(executorService, executorService2);
  }

  /**
   * Test {@link ManagedAsyncJobExecutor#initAsyncJobExecutionThreadPool()}.
   * <p>
   * Method under test:
   * {@link ManagedAsyncJobExecutor#initAsyncJobExecutionThreadPool()}
   */
  @Test
  public void testInitAsyncJobExecutionThreadPool3() {
    // Arrange
    ManagedAsyncJobExecutor managedAsyncJobExecutor = new ManagedAsyncJobExecutor();
    managedAsyncJobExecutor.setThreadFactory(mock(ManagedThreadFactory.class));
    managedAsyncJobExecutor.setThreadPoolQueue(null);
    managedAsyncJobExecutor.setExecutorService(null);

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
   * Test {@link ManagedAsyncJobExecutor#initAsyncJobExecutionThreadPool()}.
   * <p>
   * Method under test:
   * {@link ManagedAsyncJobExecutor#initAsyncJobExecutionThreadPool()}
   */
  @Test
  public void testInitAsyncJobExecutionThreadPool4() {
    // Arrange
    ManagedAsyncJobExecutor managedAsyncJobExecutor = new ManagedAsyncJobExecutor();
    managedAsyncJobExecutor.setThreadFactory(mock(ManagedThreadFactory.class));
    managedAsyncJobExecutor.setThreadPoolQueue(null);
    ForkJoinPool executorService = ForkJoinPool.commonPool();
    managedAsyncJobExecutor.setExecutorService(executorService);

    // Act
    managedAsyncJobExecutor.initAsyncJobExecutionThreadPool();

    // Assert
    ExecutorService executorService2 = managedAsyncJobExecutor.getExecutorService();
    assertTrue(executorService2 instanceof ForkJoinPool);
    assertTrue(managedAsyncJobExecutor.getThreadPoolQueue().isEmpty());
    assertSame(executorService, executorService2);
  }

  /**
   * Test {@link ManagedAsyncJobExecutor#initAsyncJobExecutionThreadPool()}.
   * <p>
   * Method under test:
   * {@link ManagedAsyncJobExecutor#initAsyncJobExecutionThreadPool()}
   */
  @Test
  public void testInitAsyncJobExecutionThreadPool5() {
    // Arrange
    ManagedAsyncJobExecutor managedAsyncJobExecutor = new ManagedAsyncJobExecutor();
    managedAsyncJobExecutor.setThreadFactory(null);
    LinkedBlockingDeque<Runnable> threadPoolQueue = new LinkedBlockingDeque<>();
    managedAsyncJobExecutor.setThreadPoolQueue(threadPoolQueue);
    managedAsyncJobExecutor.setExecutorService(ForkJoinPool.commonPool());

    // Act
    managedAsyncJobExecutor.initAsyncJobExecutionThreadPool();

    // Assert that nothing has changed
    BlockingQueue<Runnable> threadPoolQueue2 = managedAsyncJobExecutor.getThreadPoolQueue();
    assertTrue(threadPoolQueue2.isEmpty());
    assertSame(threadPoolQueue, threadPoolQueue2);
  }

  /**
   * Test {@link ManagedAsyncJobExecutor#initAsyncJobExecutionThreadPool()}.
   * <p>
   * Method under test:
   * {@link ManagedAsyncJobExecutor#initAsyncJobExecutionThreadPool()}
   */
  @Test
  public void testInitAsyncJobExecutionThreadPool6() {
    // Arrange
    ManagedAsyncJobExecutor managedAsyncJobExecutor = new ManagedAsyncJobExecutor();
    managedAsyncJobExecutor.setThreadFactory(mock(ManagedThreadFactory.class));
    LinkedBlockingDeque<Runnable> threadPoolQueue = new LinkedBlockingDeque<>();
    managedAsyncJobExecutor.setThreadPoolQueue(threadPoolQueue);
    managedAsyncJobExecutor.setExecutorService(ForkJoinPool.commonPool());

    // Act
    managedAsyncJobExecutor.initAsyncJobExecutionThreadPool();

    // Assert
    BlockingQueue<Runnable> threadPoolQueue2 = managedAsyncJobExecutor.getThreadPoolQueue();
    assertTrue(threadPoolQueue2.isEmpty());
    assertSame(threadPoolQueue, threadPoolQueue2);
  }

  /**
   * Test {@link ManagedAsyncJobExecutor#initAsyncJobExecutionThreadPool()}.
   * <p>
   * Method under test:
   * {@link ManagedAsyncJobExecutor#initAsyncJobExecutionThreadPool()}
   */
  @Test
  public void testInitAsyncJobExecutionThreadPool7() {
    // Arrange
    ManagedAsyncJobExecutor managedAsyncJobExecutor = new ManagedAsyncJobExecutor();
    managedAsyncJobExecutor.setAsyncJobAcquisitionThread(new Thread());
    managedAsyncJobExecutor.setThreadFactory(mock(ManagedThreadFactory.class));
    LinkedBlockingDeque<Runnable> threadPoolQueue = new LinkedBlockingDeque<>();
    managedAsyncJobExecutor.setThreadPoolQueue(threadPoolQueue);
    managedAsyncJobExecutor.setExecutorService(ForkJoinPool.commonPool());

    // Act
    managedAsyncJobExecutor.initAsyncJobExecutionThreadPool();

    // Assert that nothing has changed
    BlockingQueue<Runnable> threadPoolQueue2 = managedAsyncJobExecutor.getThreadPoolQueue();
    assertTrue(threadPoolQueue2.isEmpty());
    assertSame(threadPoolQueue, threadPoolQueue2);
  }

  /**
   * Test new {@link ManagedAsyncJobExecutor} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link ManagedAsyncJobExecutor}
   */
  @Test
  public void testNewManagedAsyncJobExecutor() {
    // Arrange and Act
    ManagedAsyncJobExecutor actualManagedAsyncJobExecutor = new ManagedAsyncJobExecutor();

    // Assert
    assertNull(actualManagedAsyncJobExecutor.getAsyncJobAcquisitionThread());
    assertNull(actualManagedAsyncJobExecutor.getResetExpiredJobThread());
    assertNull(actualManagedAsyncJobExecutor.getTimerJobAcquisitionThread());
    assertNull(actualManagedAsyncJobExecutor.getThreadPoolQueue());
    assertNull(actualManagedAsyncJobExecutor.getExecutorService());
    assertNull(actualManagedAsyncJobExecutor.getThreadFactory());
    assertNull(actualManagedAsyncJobExecutor.asyncJobsDueRunnable);
    assertNull(actualManagedAsyncJobExecutor.timerJobRunnable);
    assertNull(actualManagedAsyncJobExecutor.getExecuteAsyncRunnableFactory());
    assertNull(actualManagedAsyncJobExecutor.resetExpiredJobsRunnable);
    assertNull(actualManagedAsyncJobExecutor.getProcessEngineConfiguration());
    assertEquals(0, actualManagedAsyncJobExecutor.getDefaultQueueSizeFullWaitTimeInMillis());
    assertEquals(1, actualManagedAsyncJobExecutor.getMaxAsyncJobsDuePerAcquisition());
    assertEquals(1, actualManagedAsyncJobExecutor.getMaxTimerJobsPerAcquisition());
    assertEquals(10, actualManagedAsyncJobExecutor.getMaxPoolSize());
    assertEquals(100, actualManagedAsyncJobExecutor.getQueueSize());
    assertEquals(10000, actualManagedAsyncJobExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis());
    assertEquals(10000, actualManagedAsyncJobExecutor.getDefaultTimerJobAcquireWaitTimeInMillis());
    assertEquals(2, actualManagedAsyncJobExecutor.getCorePoolSize());
    assertEquals(3, actualManagedAsyncJobExecutor.getResetExpiredJobsPageSize());
    assertEquals(300000, actualManagedAsyncJobExecutor.getAsyncJobLockTimeInMillis());
    assertEquals(300000, actualManagedAsyncJobExecutor.getTimerLockTimeInMillis());
    assertEquals(500, actualManagedAsyncJobExecutor.getRetryWaitTimeInMillis());
    assertEquals(5000L, actualManagedAsyncJobExecutor.getKeepAliveTime());
    assertEquals(60000, actualManagedAsyncJobExecutor.getResetExpiredJobsInterval());
    assertEquals(60L, actualManagedAsyncJobExecutor.getSecondsToWaitOnShutdown());
    assertFalse(actualManagedAsyncJobExecutor.isActive());
    assertFalse(actualManagedAsyncJobExecutor.isAutoActivate());
    assertFalse(actualManagedAsyncJobExecutor.isMessageQueueMode());
    assertTrue(actualManagedAsyncJobExecutor.temporaryJobQueue.isEmpty());
  }
}
