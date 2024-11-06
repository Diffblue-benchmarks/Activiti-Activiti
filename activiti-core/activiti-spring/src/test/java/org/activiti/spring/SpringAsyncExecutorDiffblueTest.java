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
package org.activiti.spring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.concurrent.RejectedExecutionException;
import org.activiti.engine.impl.asyncexecutor.AsyncExecutor;
import org.activiti.engine.impl.persistence.entity.JobEntityImpl;
import org.activiti.engine.runtime.Job;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = {"/org/activiti/spring/test/components/SpringjobExecutorTest-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SpringAsyncExecutorDiffblueTest {
  @Autowired
  private SpringAsyncExecutor springAsyncExecutor;

  /**
   * Test {@link SpringAsyncExecutor#SpringAsyncExecutor()}.
   * <p>
   * Method under test: {@link SpringAsyncExecutor#SpringAsyncExecutor()}
   */
  @Test
  public void testNewSpringAsyncExecutor() {
    // Arrange and Act
    SpringAsyncExecutor actualSpringAsyncExecutor = new SpringAsyncExecutor();

    // Assert
    assertNull(actualSpringAsyncExecutor.getAsyncJobAcquisitionThread());
    assertNull(actualSpringAsyncExecutor.getResetExpiredJobThread());
    assertNull(actualSpringAsyncExecutor.getTimerJobAcquisitionThread());
    assertNull(actualSpringAsyncExecutor.getThreadPoolQueue());
    assertNull(actualSpringAsyncExecutor.getExecutorService());
    assertNull(actualSpringAsyncExecutor.getExecuteAsyncRunnableFactory());
    assertNull(actualSpringAsyncExecutor.getProcessEngineConfiguration());
    assertNull(actualSpringAsyncExecutor.getRejectedJobsHandler());
    assertNull(actualSpringAsyncExecutor.getTaskExecutor());
    assertEquals(0, actualSpringAsyncExecutor.getDefaultQueueSizeFullWaitTimeInMillis());
    assertEquals(1, actualSpringAsyncExecutor.getMaxAsyncJobsDuePerAcquisition());
    assertEquals(1, actualSpringAsyncExecutor.getMaxTimerJobsPerAcquisition());
    assertEquals(10, actualSpringAsyncExecutor.getMaxPoolSize());
    assertEquals(100, actualSpringAsyncExecutor.getQueueSize());
    assertEquals(10000, actualSpringAsyncExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis());
    assertEquals(10000, actualSpringAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis());
    assertEquals(2, actualSpringAsyncExecutor.getCorePoolSize());
    assertEquals(3, actualSpringAsyncExecutor.getResetExpiredJobsPageSize());
    assertEquals(300000, actualSpringAsyncExecutor.getAsyncJobLockTimeInMillis());
    assertEquals(300000, actualSpringAsyncExecutor.getTimerLockTimeInMillis());
    assertEquals(500, actualSpringAsyncExecutor.getRetryWaitTimeInMillis());
    assertEquals(5000L, actualSpringAsyncExecutor.getKeepAliveTime());
    assertEquals(60000, actualSpringAsyncExecutor.getResetExpiredJobsInterval());
    assertEquals(60L, actualSpringAsyncExecutor.getSecondsToWaitOnShutdown());
    assertFalse(actualSpringAsyncExecutor.isActive());
    assertFalse(actualSpringAsyncExecutor.isAutoActivate());
    assertFalse(actualSpringAsyncExecutor.isMessageQueueMode());
  }

  /**
   * Test
   * {@link SpringAsyncExecutor#SpringAsyncExecutor(TaskExecutor, SpringRejectedJobsHandler)}.
   * <p>
   * Method under test:
   * {@link SpringAsyncExecutor#SpringAsyncExecutor(TaskExecutor, SpringRejectedJobsHandler)}
   */
  @Test
  public void testNewSpringAsyncExecutor2() {
    // Arrange
    TaskExecutor taskExecutor = mock(TaskExecutor.class);
    SpringRejectedJobsHandler rejectedJobsHandler = mock(SpringRejectedJobsHandler.class);

    // Act
    SpringAsyncExecutor actualSpringAsyncExecutor = new SpringAsyncExecutor(taskExecutor, rejectedJobsHandler);

    // Assert
    assertNull(actualSpringAsyncExecutor.getAsyncJobAcquisitionThread());
    assertNull(actualSpringAsyncExecutor.getResetExpiredJobThread());
    assertNull(actualSpringAsyncExecutor.getTimerJobAcquisitionThread());
    assertNull(actualSpringAsyncExecutor.getThreadPoolQueue());
    assertNull(actualSpringAsyncExecutor.getExecutorService());
    assertNull(actualSpringAsyncExecutor.getExecuteAsyncRunnableFactory());
    assertNull(actualSpringAsyncExecutor.getProcessEngineConfiguration());
    assertEquals(0, actualSpringAsyncExecutor.getDefaultQueueSizeFullWaitTimeInMillis());
    assertEquals(1, actualSpringAsyncExecutor.getMaxAsyncJobsDuePerAcquisition());
    assertEquals(1, actualSpringAsyncExecutor.getMaxTimerJobsPerAcquisition());
    assertEquals(10, actualSpringAsyncExecutor.getMaxPoolSize());
    assertEquals(100, actualSpringAsyncExecutor.getQueueSize());
    assertEquals(10000, actualSpringAsyncExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis());
    assertEquals(10000, actualSpringAsyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis());
    assertEquals(2, actualSpringAsyncExecutor.getCorePoolSize());
    assertEquals(3, actualSpringAsyncExecutor.getResetExpiredJobsPageSize());
    assertEquals(300000, actualSpringAsyncExecutor.getAsyncJobLockTimeInMillis());
    assertEquals(300000, actualSpringAsyncExecutor.getTimerLockTimeInMillis());
    assertEquals(500, actualSpringAsyncExecutor.getRetryWaitTimeInMillis());
    assertEquals(5000L, actualSpringAsyncExecutor.getKeepAliveTime());
    assertEquals(60000, actualSpringAsyncExecutor.getResetExpiredJobsInterval());
    assertEquals(60L, actualSpringAsyncExecutor.getSecondsToWaitOnShutdown());
    assertFalse(actualSpringAsyncExecutor.isActive());
    assertFalse(actualSpringAsyncExecutor.isAutoActivate());
    assertFalse(actualSpringAsyncExecutor.isMessageQueueMode());
    assertSame(rejectedJobsHandler, actualSpringAsyncExecutor.getRejectedJobsHandler());
    assertSame(taskExecutor, actualSpringAsyncExecutor.getTaskExecutor());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link SpringAsyncExecutor#setRejectedJobsHandler(SpringRejectedJobsHandler)}
   *   <li>{@link SpringAsyncExecutor#setTaskExecutor(TaskExecutor)}
   *   <li>{@link SpringAsyncExecutor#initAsyncJobExecutionThreadPool()}
   *   <li>{@link SpringAsyncExecutor#getRejectedJobsHandler()}
   *   <li>{@link SpringAsyncExecutor#getTaskExecutor()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    SpringAsyncExecutor springAsyncExecutor = new SpringAsyncExecutor();
    SpringRejectedJobsHandler rejectedJobsHandler = mock(SpringRejectedJobsHandler.class);

    // Act
    springAsyncExecutor.setRejectedJobsHandler(rejectedJobsHandler);
    TaskExecutor taskExecutor = mock(TaskExecutor.class);
    springAsyncExecutor.setTaskExecutor(taskExecutor);
    springAsyncExecutor.initAsyncJobExecutionThreadPool();
    SpringRejectedJobsHandler actualRejectedJobsHandler = springAsyncExecutor.getRejectedJobsHandler();

    // Assert that nothing has changed
    assertSame(rejectedJobsHandler, actualRejectedJobsHandler);
    assertSame(taskExecutor, springAsyncExecutor.getTaskExecutor());
  }

  /**
   * Test {@link SpringAsyncExecutor#executeAsyncJob(Job)}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SpringAsyncExecutor#executeAsyncJob(Job)}
   */
  @Test
  public void testExecuteAsyncJob_thenReturnFalse() {
    // Arrange
    TaskExecutor taskExecutor = mock(TaskExecutor.class);
    doNothing().when(taskExecutor).execute(Mockito.<Runnable>any());
    SpringRejectedJobsHandler rejectedJobsHandler = mock(SpringRejectedJobsHandler.class);
    doNothing().when(rejectedJobsHandler).jobRejected(Mockito.<AsyncExecutor>any(), Mockito.<Job>any());
    TaskExecutor taskExecutor2 = mock(TaskExecutor.class);
    doThrow(new RejectedExecutionException("foo")).when(taskExecutor2).execute(Mockito.<Runnable>any());

    SpringAsyncExecutor springAsyncExecutor = new SpringAsyncExecutor(taskExecutor, rejectedJobsHandler);
    springAsyncExecutor.setTaskExecutor(taskExecutor2);
    JobEntityImpl job = mock(JobEntityImpl.class);
    when(job.getId()).thenReturn("42");

    // Act
    boolean actualExecuteAsyncJobResult = springAsyncExecutor.executeAsyncJob(job);

    // Assert
    verify(job).getId();
    verify(rejectedJobsHandler).jobRejected(isA(AsyncExecutor.class), isA(Job.class));
    verify(taskExecutor2).execute(isA(Runnable.class));
    assertFalse(actualExecuteAsyncJobResult);
  }

  /**
   * Test {@link SpringAsyncExecutor#executeAsyncJob(Job)}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SpringAsyncExecutor#executeAsyncJob(Job)}
   */
  @Test
  public void testExecuteAsyncJob_thenReturnTrue() {
    // Arrange
    TaskExecutor taskExecutor = mock(TaskExecutor.class);
    doNothing().when(taskExecutor).execute(Mockito.<Runnable>any());
    SpringAsyncExecutor springAsyncExecutor = new SpringAsyncExecutor(taskExecutor,
        mock(SpringRejectedJobsHandler.class));
    JobEntityImpl job = mock(JobEntityImpl.class);
    when(job.getId()).thenReturn("42");

    // Act
    boolean actualExecuteAsyncJobResult = springAsyncExecutor.executeAsyncJob(job);

    // Assert
    verify(job).getId();
    verify(taskExecutor).execute(isA(Runnable.class));
    assertTrue(actualExecuteAsyncJobResult);
  }

  /**
   * Test {@link SpringAsyncExecutor#executeAsyncJob(Job)}.
   * <ul>
   *   <li>Then throw {@link RejectedExecutionException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SpringAsyncExecutor#executeAsyncJob(Job)}
   */
  @Test
  public void testExecuteAsyncJob_thenThrowRejectedExecutionException() {
    // Arrange
    TaskExecutor taskExecutor = mock(TaskExecutor.class);
    doNothing().when(taskExecutor).execute(Mockito.<Runnable>any());
    SpringRejectedJobsHandler rejectedJobsHandler = mock(SpringRejectedJobsHandler.class);
    doThrow(new RejectedExecutionException("foo")).when(rejectedJobsHandler)
        .jobRejected(Mockito.<AsyncExecutor>any(), Mockito.<Job>any());
    TaskExecutor taskExecutor2 = mock(TaskExecutor.class);
    doThrow(new RejectedExecutionException("foo")).when(taskExecutor2).execute(Mockito.<Runnable>any());

    SpringAsyncExecutor springAsyncExecutor = new SpringAsyncExecutor(taskExecutor, rejectedJobsHandler);
    springAsyncExecutor.setTaskExecutor(taskExecutor2);
    JobEntityImpl job = mock(JobEntityImpl.class);
    when(job.getId()).thenReturn("42");

    // Act and Assert
    assertThrows(RejectedExecutionException.class, () -> springAsyncExecutor.executeAsyncJob(job));
    verify(job).getId();
    verify(rejectedJobsHandler).jobRejected(isA(AsyncExecutor.class), isA(Job.class));
    verify(taskExecutor2).execute(isA(Runnable.class));
  }
}
