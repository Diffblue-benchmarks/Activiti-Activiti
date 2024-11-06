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
package org.activiti.engine.impl.jobexecutor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.Map;
import org.activiti.engine.impl.asyncexecutor.AsyncExecutor;
import org.activiti.engine.impl.asyncexecutor.DefaultAsyncJobExecutor;
import org.activiti.engine.impl.persistence.entity.JobEntity;
import org.activiti.engine.impl.persistence.entity.JobEntityImpl;
import org.junit.Test;

public class AsyncJobAddedNotificationDiffblueTest {
  /**
   * Test
   * {@link AsyncJobAddedNotification#AsyncJobAddedNotification(JobEntity, AsyncExecutor)}.
   * <p>
   * Method under test:
   * {@link AsyncJobAddedNotification#AsyncJobAddedNotification(JobEntity, AsyncExecutor)}
   */
  @Test
  public void testNewAsyncJobAddedNotification() {
    // Arrange
    JobEntityImpl job = new JobEntityImpl();

    // Act
    AsyncJobAddedNotification actualAsyncJobAddedNotification = new AsyncJobAddedNotification(job,
        new DefaultAsyncJobExecutor());

    // Assert
    JobEntity jobEntity = actualAsyncJobAddedNotification.job;
    Object persistentState = jobEntity.getPersistentState();
    assertTrue(persistentState instanceof Map);
    AsyncExecutor asyncExecutor = actualAsyncJobAddedNotification.asyncExecutor;
    assertTrue(asyncExecutor instanceof DefaultAsyncJobExecutor);
    assertTrue(jobEntity instanceof JobEntityImpl);
    assertEquals("", jobEntity.getTenantId());
    assertEquals(5, ((Map<String, Integer>) persistentState).size());
    assertNull(((Map<String, Integer>) persistentState).get("duedate"));
    assertNull(((Map<String, Integer>) persistentState).get("lockExpirationTime"));
    assertNull(((Map<String, Integer>) persistentState).get("lockOwner"));
    assertNull(jobEntity.getExceptionStacktrace());
    assertNull(jobEntity.getJobHandlerConfiguration());
    assertNull(jobEntity.getJobHandlerType());
    assertNull(jobEntity.getJobType());
    assertNull(jobEntity.getRepeat());
    assertNull(jobEntity.getId());
    assertNull(jobEntity.getLockOwner());
    assertNull(jobEntity.getExceptionMessage());
    assertNull(jobEntity.getExecutionId());
    assertNull(jobEntity.getProcessDefinitionId());
    assertNull(jobEntity.getProcessInstanceId());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getAsyncJobAcquisitionThread());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getResetExpiredJobThread());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getTimerJobAcquisitionThread());
    assertNull(jobEntity.getEndDate());
    assertNull(jobEntity.getLockExpirationTime());
    assertNull(jobEntity.getDuedate());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getThreadPoolQueue());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getExecutorService());
    assertNull(((DefaultAsyncJobExecutor) asyncExecutor).getExecuteAsyncRunnableFactory());
    assertNull(asyncExecutor.getProcessEngineConfiguration());
    assertNull(jobEntity.getExceptionByteArrayRef());
    assertEquals(0, ((Map<String, Integer>) persistentState).get("retries").intValue());
    assertEquals(0, asyncExecutor.getDefaultQueueSizeFullWaitTimeInMillis());
    assertEquals(0, jobEntity.getMaxIterations());
    assertEquals(0, jobEntity.getRetries());
    assertEquals(1, asyncExecutor.getMaxAsyncJobsDuePerAcquisition());
    assertEquals(1, asyncExecutor.getMaxTimerJobsPerAcquisition());
    assertEquals(1, jobEntity.getRevision());
    assertEquals(10, ((DefaultAsyncJobExecutor) asyncExecutor).getMaxPoolSize());
    assertEquals(100, ((DefaultAsyncJobExecutor) asyncExecutor).getQueueSize());
    assertEquals(10000, asyncExecutor.getDefaultAsyncJobAcquireWaitTimeInMillis());
    assertEquals(10000, asyncExecutor.getDefaultTimerJobAcquireWaitTimeInMillis());
    assertEquals(2, ((DefaultAsyncJobExecutor) asyncExecutor).getCorePoolSize());
    assertEquals(2, jobEntity.getRevisionNext());
    assertEquals(3, asyncExecutor.getResetExpiredJobsPageSize());
    assertEquals(300000, asyncExecutor.getAsyncJobLockTimeInMillis());
    assertEquals(300000, asyncExecutor.getTimerLockTimeInMillis());
    assertEquals(500, asyncExecutor.getRetryWaitTimeInMillis());
    assertEquals(5000L, ((DefaultAsyncJobExecutor) asyncExecutor).getKeepAliveTime());
    assertEquals(60000, asyncExecutor.getResetExpiredJobsInterval());
    assertEquals(60L, ((DefaultAsyncJobExecutor) asyncExecutor).getSecondsToWaitOnShutdown());
    assertFalse(asyncExecutor.isActive());
    assertFalse(asyncExecutor.isAutoActivate());
    assertFalse(((DefaultAsyncJobExecutor) asyncExecutor).isMessageQueueMode());
    assertFalse(jobEntity.isDeleted());
    assertFalse(jobEntity.isInserted());
    assertFalse(jobEntity.isUpdated());
    assertTrue(jobEntity.isExclusive());
  }
}
