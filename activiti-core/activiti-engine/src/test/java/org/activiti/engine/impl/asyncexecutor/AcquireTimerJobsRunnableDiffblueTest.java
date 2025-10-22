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
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AcquireTimerJobsRunnableDiffblueTest {
  /**
   * Test {@link AcquireTimerJobsRunnable#AcquireTimerJobsRunnable(AsyncExecutor, JobManager)}.
   * <p>
   * Method under test: {@link AcquireTimerJobsRunnable#AcquireTimerJobsRunnable(AsyncExecutor, JobManager)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AcquireTimerJobsRunnable.<init>(AsyncExecutor, JobManager)"})
  public void testNewAcquireTimerJobsRunnable() {
    // Arrange
    DefaultAsyncJobExecutor asyncExecutor = new DefaultAsyncJobExecutor();

    // Act
    AcquireTimerJobsRunnable actualAcquireTimerJobsRunnable = new AcquireTimerJobsRunnable(asyncExecutor,
        new DefaultJobManager());

    // Assert
    assertTrue(actualAcquireTimerJobsRunnable.asyncExecutor instanceof DefaultAsyncJobExecutor);
    assertTrue(actualAcquireTimerJobsRunnable.jobManager instanceof DefaultJobManager);
    assertEquals(0L, actualAcquireTimerJobsRunnable.getMillisToWait());
    assertFalse(actualAcquireTimerJobsRunnable.isInterrupted);
  }

  /**
   * Test {@link AcquireTimerJobsRunnable#stop()}.
   * <p>
   * Method under test: {@link AcquireTimerJobsRunnable#stop()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AcquireTimerJobsRunnable.stop()"})
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
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AcquireTimerJobsRunnable#setMillisToWait(long)}
   *   <li>{@link AcquireTimerJobsRunnable#getMillisToWait()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"long AcquireTimerJobsRunnable.getMillisToWait()",
      "void AcquireTimerJobsRunnable.setMillisToWait(long)"})
  public void testGettersAndSetters() {
    // Arrange
    DefaultAsyncJobExecutor asyncExecutor = new DefaultAsyncJobExecutor();
    AcquireTimerJobsRunnable acquireTimerJobsRunnable = new AcquireTimerJobsRunnable(asyncExecutor,
        new DefaultJobManager());

    // Act
    acquireTimerJobsRunnable.setMillisToWait(1L);

    // Assert
    assertEquals(1L, acquireTimerJobsRunnable.getMillisToWait());
  }
}
