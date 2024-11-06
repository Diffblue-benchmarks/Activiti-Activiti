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
import java.util.LinkedList;
import org.activiti.engine.impl.asyncexecutor.multitenant.ExecutorPerTenantAsyncExecutor;
import org.activiti.engine.impl.asyncexecutor.multitenant.TenantAwareAsyncExecutorFactory;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.test.cfg.multitenant.DummyTenantInfoHolder;
import org.junit.Test;

public class AcquireTimerJobsRunnableDiffblueTest {
  /**
   * Test
   * {@link AcquireTimerJobsRunnable#AcquireTimerJobsRunnable(AsyncExecutor, JobManager)}.
   * <p>
   * Method under test:
   * {@link AcquireTimerJobsRunnable#AcquireTimerJobsRunnable(AsyncExecutor, JobManager)}
   */
  @Test
  public void testNewAcquireTimerJobsRunnable() {
    // Arrange
    ExecutorPerTenantAsyncExecutor asyncExecutor = new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder(),
        mock(TenantAwareAsyncExecutorFactory.class));

    // Act and Assert
    AsyncExecutor asyncExecutor2 = (new AcquireTimerJobsRunnable(asyncExecutor, new DefaultJobManager())).asyncExecutor;
    assertTrue(asyncExecutor2 instanceof ExecutorPerTenantAsyncExecutor);
    assertFalse(asyncExecutor2.isActive());
    assertFalse(asyncExecutor2.isAutoActivate());
    assertTrue(((ExecutorPerTenantAsyncExecutor) asyncExecutor2).getTenantIds().isEmpty());
  }

  /**
   * Test
   * {@link AcquireTimerJobsRunnable#AcquireTimerJobsRunnable(AsyncExecutor, JobManager)}.
   * <ul>
   *   <li>Then {@link AcquireTimerJobsRunnable#asyncExecutor} return
   * {@link DefaultAsyncJobExecutor}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AcquireTimerJobsRunnable#AcquireTimerJobsRunnable(AsyncExecutor, JobManager)}
   */
  @Test
  public void testNewAcquireTimerJobsRunnable_thenAsyncExecutorReturnDefaultAsyncJobExecutor() {
    // Arrange
    DefaultAsyncJobExecutor asyncExecutor = new DefaultAsyncJobExecutor();

    // Act and Assert
    AsyncExecutor asyncExecutor2 = (new AcquireTimerJobsRunnable(asyncExecutor, new DefaultJobManager())).asyncExecutor;
    assertTrue(asyncExecutor2 instanceof DefaultAsyncJobExecutor);
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor2).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor2).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor2).getTimerJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor2).getThreadPoolQueue());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor2).getExecutorService());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor2).asyncJobsDueRunnable);
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor2).timerJobRunnable);
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor2).getExecuteAsyncRunnableFactory());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor2).resetExpiredJobsRunnable);
    assertNull(asyncExecutor2.getProcessEngineConfiguration());
    assertEquals(0, asyncExecutor2.getDefaultQueueSizeFullWaitTimeInMillis());
    assertEquals(1, asyncExecutor2.getMaxAsyncJobsDuePerAcquisition());
    assertEquals(1, asyncExecutor2.getMaxTimerJobsPerAcquisition());
    assertEquals(10, ((DefaultAsyncJobExecutor) asyncExecutor2).getMaxPoolSize());
    assertEquals(100, ((DefaultAsyncJobExecutor) asyncExecutor2).getQueueSize());
    assertEquals(10000, asyncExecutor2.getDefaultAsyncJobAcquireWaitTimeInMillis());
    assertEquals(10000, asyncExecutor2.getDefaultTimerJobAcquireWaitTimeInMillis());
    assertEquals(2, ((DefaultAsyncJobExecutor) asyncExecutor2).getCorePoolSize());
    assertEquals(3, asyncExecutor2.getResetExpiredJobsPageSize());
    assertEquals(300000, asyncExecutor2.getAsyncJobLockTimeInMillis());
    assertEquals(300000, asyncExecutor2.getTimerLockTimeInMillis());
    assertEquals(500, asyncExecutor2.getRetryWaitTimeInMillis());
    assertEquals(5000L, ((DefaultAsyncJobExecutor) asyncExecutor2).getKeepAliveTime());
    assertEquals(60000, asyncExecutor2.getResetExpiredJobsInterval());
    assertEquals(60L, ((DefaultAsyncJobExecutor) asyncExecutor2).getSecondsToWaitOnShutdown());
    assertFalse(asyncExecutor2.isActive());
    assertFalse(asyncExecutor2.isAutoActivate());
    assertFalse(((DefaultAsyncJobExecutor) asyncExecutor2).isMessageQueueMode());
    LinkedList<Job> jobList = ((DefaultAsyncJobExecutor) asyncExecutor2).temporaryJobQueue;
    assertTrue(jobList.isEmpty());
    assertSame(asyncExecutor.temporaryJobQueue, jobList);
  }

  /**
   * Test {@link AcquireTimerJobsRunnable#stop()}.
   * <p>
   * Method under test: {@link AcquireTimerJobsRunnable#stop()}
   */
  @Test
  public void testStop() {
    // Arrange
    DefaultAsyncJobExecutor asyncExecutor = new DefaultAsyncJobExecutor();
    AcquireTimerJobsRunnable acquireTimerJobsRunnable = new AcquireTimerJobsRunnable(asyncExecutor,
        new DefaultJobManager());

    // Act
    acquireTimerJobsRunnable.stop();

    // Assert
    assertTrue(acquireTimerJobsRunnable.isInterrupted);
  }

  /**
   * Test {@link AcquireTimerJobsRunnable#stop()}.
   * <p>
   * Method under test: {@link AcquireTimerJobsRunnable#stop()}
   */
  @Test
  public void testStop2() {
    // Arrange
    ExecutorPerTenantAsyncExecutor asyncExecutor = new ExecutorPerTenantAsyncExecutor(new DummyTenantInfoHolder(),
        mock(TenantAwareAsyncExecutorFactory.class));

    AcquireTimerJobsRunnable acquireTimerJobsRunnable = new AcquireTimerJobsRunnable(asyncExecutor,
        new DefaultJobManager());

    // Act
    acquireTimerJobsRunnable.stop();

    // Assert
    assertTrue(acquireTimerJobsRunnable.isInterrupted);
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AcquireTimerJobsRunnable#setMillisToWait(long)}
   *   <li>{@link AcquireTimerJobsRunnable#getMillisToWait()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    DefaultAsyncJobExecutor asyncExecutor = new DefaultAsyncJobExecutor();
    AcquireTimerJobsRunnable acquireTimerJobsRunnable = new AcquireTimerJobsRunnable(asyncExecutor,
        new DefaultJobManager());

    // Act
    acquireTimerJobsRunnable.setMillisToWait(1L);

    // Assert that nothing has changed
    assertEquals(1L, acquireTimerJobsRunnable.getMillisToWait());
  }
}
