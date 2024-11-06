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
package org.activiti.spring.boot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AsyncExecutorPropertiesDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link AsyncExecutorProperties}
   *   <li>{@link AsyncExecutorProperties#setAsyncJobLockTimeInMillis(int)}
   *   <li>{@link AsyncExecutorProperties#setCorePoolSize(int)}
   *   <li>
   * {@link AsyncExecutorProperties#setDefaultAsyncJobAcquireWaitTimeInMillis(int)}
   *   <li>{@link AsyncExecutorProperties#setDefaultQueueSizeFullWaitTime(int)}
   *   <li>
   * {@link AsyncExecutorProperties#setDefaultTimerJobAcquireWaitTimeInMillis(int)}
   *   <li>{@link AsyncExecutorProperties#setKeepAliveTime(long)}
   *   <li>{@link AsyncExecutorProperties#setMaxAsyncJobsDuePerAcquisition(int)}
   *   <li>{@link AsyncExecutorProperties#setMaxPoolSize(int)}
   *   <li>{@link AsyncExecutorProperties#setMaxTimerJobsPerAcquisition(int)}
   *   <li>{@link AsyncExecutorProperties#setMessageQueueMode(boolean)}
   *   <li>{@link AsyncExecutorProperties#setNumberOfRetries(int)}
   *   <li>{@link AsyncExecutorProperties#setQueueSize(int)}
   *   <li>{@link AsyncExecutorProperties#setResetExpiredJobsInterval(int)}
   *   <li>{@link AsyncExecutorProperties#setResetExpiredJobsPageSize(int)}
   *   <li>{@link AsyncExecutorProperties#setRetryWaitTimeInMillis(int)}
   *   <li>{@link AsyncExecutorProperties#setSecondsToWaitOnShutdown(long)}
   *   <li>{@link AsyncExecutorProperties#setTimerLockTimeInMillis(int)}
   *   <li>{@link AsyncExecutorProperties#getAsyncJobLockTimeInMillis()}
   *   <li>{@link AsyncExecutorProperties#getCorePoolSize()}
   *   <li>
   * {@link AsyncExecutorProperties#getDefaultAsyncJobAcquireWaitTimeInMillis()}
   *   <li>{@link AsyncExecutorProperties#getDefaultQueueSizeFullWaitTime()}
   *   <li>
   * {@link AsyncExecutorProperties#getDefaultTimerJobAcquireWaitTimeInMillis()}
   *   <li>{@link AsyncExecutorProperties#getKeepAliveTime()}
   *   <li>{@link AsyncExecutorProperties#getMaxAsyncJobsDuePerAcquisition()}
   *   <li>{@link AsyncExecutorProperties#getMaxPoolSize()}
   *   <li>{@link AsyncExecutorProperties#getMaxTimerJobsPerAcquisition()}
   *   <li>{@link AsyncExecutorProperties#getNumberOfRetries()}
   *   <li>{@link AsyncExecutorProperties#getQueueSize()}
   *   <li>{@link AsyncExecutorProperties#getResetExpiredJobsInterval()}
   *   <li>{@link AsyncExecutorProperties#getResetExpiredJobsPageSize()}
   *   <li>{@link AsyncExecutorProperties#getRetryWaitTimeInMillis()}
   *   <li>{@link AsyncExecutorProperties#getSecondsToWaitOnShutdown()}
   *   <li>{@link AsyncExecutorProperties#getTimerLockTimeInMillis()}
   *   <li>{@link AsyncExecutorProperties#isMessageQueueMode()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    AsyncExecutorProperties actualAsyncExecutorProperties = new AsyncExecutorProperties();
    actualAsyncExecutorProperties.setAsyncJobLockTimeInMillis(1);
    actualAsyncExecutorProperties.setCorePoolSize(3);
    actualAsyncExecutorProperties.setDefaultAsyncJobAcquireWaitTimeInMillis(1);
    actualAsyncExecutorProperties.setDefaultQueueSizeFullWaitTime(3);
    actualAsyncExecutorProperties.setDefaultTimerJobAcquireWaitTimeInMillis(1);
    actualAsyncExecutorProperties.setKeepAliveTime(1L);
    actualAsyncExecutorProperties.setMaxAsyncJobsDuePerAcquisition(3);
    actualAsyncExecutorProperties.setMaxPoolSize(3);
    actualAsyncExecutorProperties.setMaxTimerJobsPerAcquisition(3);
    actualAsyncExecutorProperties.setMessageQueueMode(true);
    actualAsyncExecutorProperties.setNumberOfRetries(10);
    actualAsyncExecutorProperties.setQueueSize(3);
    actualAsyncExecutorProperties.setResetExpiredJobsInterval(42);
    actualAsyncExecutorProperties.setResetExpiredJobsPageSize(3);
    actualAsyncExecutorProperties.setRetryWaitTimeInMillis(1);
    actualAsyncExecutorProperties.setSecondsToWaitOnShutdown(1L);
    actualAsyncExecutorProperties.setTimerLockTimeInMillis(1);
    int actualAsyncJobLockTimeInMillis = actualAsyncExecutorProperties.getAsyncJobLockTimeInMillis();
    int actualCorePoolSize = actualAsyncExecutorProperties.getCorePoolSize();
    int actualDefaultAsyncJobAcquireWaitTimeInMillis = actualAsyncExecutorProperties
        .getDefaultAsyncJobAcquireWaitTimeInMillis();
    int actualDefaultQueueSizeFullWaitTime = actualAsyncExecutorProperties.getDefaultQueueSizeFullWaitTime();
    int actualDefaultTimerJobAcquireWaitTimeInMillis = actualAsyncExecutorProperties
        .getDefaultTimerJobAcquireWaitTimeInMillis();
    long actualKeepAliveTime = actualAsyncExecutorProperties.getKeepAliveTime();
    int actualMaxAsyncJobsDuePerAcquisition = actualAsyncExecutorProperties.getMaxAsyncJobsDuePerAcquisition();
    int actualMaxPoolSize = actualAsyncExecutorProperties.getMaxPoolSize();
    int actualMaxTimerJobsPerAcquisition = actualAsyncExecutorProperties.getMaxTimerJobsPerAcquisition();
    int actualNumberOfRetries = actualAsyncExecutorProperties.getNumberOfRetries();
    int actualQueueSize = actualAsyncExecutorProperties.getQueueSize();
    int actualResetExpiredJobsInterval = actualAsyncExecutorProperties.getResetExpiredJobsInterval();
    int actualResetExpiredJobsPageSize = actualAsyncExecutorProperties.getResetExpiredJobsPageSize();
    int actualRetryWaitTimeInMillis = actualAsyncExecutorProperties.getRetryWaitTimeInMillis();
    long actualSecondsToWaitOnShutdown = actualAsyncExecutorProperties.getSecondsToWaitOnShutdown();
    int actualTimerLockTimeInMillis = actualAsyncExecutorProperties.getTimerLockTimeInMillis();

    // Assert that nothing has changed
    assertEquals(1, actualAsyncJobLockTimeInMillis);
    assertEquals(1, actualDefaultAsyncJobAcquireWaitTimeInMillis);
    assertEquals(1, actualDefaultTimerJobAcquireWaitTimeInMillis);
    assertEquals(1, actualRetryWaitTimeInMillis);
    assertEquals(1, actualTimerLockTimeInMillis);
    assertEquals(10, actualNumberOfRetries);
    assertEquals(1L, actualKeepAliveTime);
    assertEquals(1L, actualSecondsToWaitOnShutdown);
    assertEquals(3, actualCorePoolSize);
    assertEquals(3, actualDefaultQueueSizeFullWaitTime);
    assertEquals(3, actualMaxAsyncJobsDuePerAcquisition);
    assertEquals(3, actualMaxPoolSize);
    assertEquals(3, actualMaxTimerJobsPerAcquisition);
    assertEquals(3, actualQueueSize);
    assertEquals(3, actualResetExpiredJobsPageSize);
    assertEquals(42, actualResetExpiredJobsInterval);
    assertTrue(actualAsyncExecutorProperties.isMessageQueueMode());
  }
}
