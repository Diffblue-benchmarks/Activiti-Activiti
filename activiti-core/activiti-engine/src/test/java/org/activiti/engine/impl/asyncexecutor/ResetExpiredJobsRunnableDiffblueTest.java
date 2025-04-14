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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ResetExpiredJobsRunnableDiffblueTest {
  /**
   * Test {@link ResetExpiredJobsRunnable#ResetExpiredJobsRunnable(AsyncExecutor)}.
   * <p>
   * Method under test: {@link ResetExpiredJobsRunnable#ResetExpiredJobsRunnable(AsyncExecutor)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ResetExpiredJobsRunnable.<init>(AsyncExecutor)"})
  public void testNewResetExpiredJobsRunnable() {
    // Arrange and Act
    ResetExpiredJobsRunnable actualResetExpiredJobsRunnable = new ResetExpiredJobsRunnable(
        new DefaultAsyncJobExecutor());

    // Assert
    assertTrue(actualResetExpiredJobsRunnable.asyncExecutor instanceof DefaultAsyncJobExecutor);
    assertFalse(actualResetExpiredJobsRunnable.isInterrupted);
  }

  /**
   * Test {@link ResetExpiredJobsRunnable#stop()}.
   * <p>
   * Method under test: {@link ResetExpiredJobsRunnable#stop()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ResetExpiredJobsRunnable.stop()"})
  public void testStop() {
    // Arrange
    ResetExpiredJobsRunnable resetExpiredJobsRunnable = new ResetExpiredJobsRunnable(new DefaultAsyncJobExecutor());

    // Act
    resetExpiredJobsRunnable.stop();

    // Assert
    assertTrue(resetExpiredJobsRunnable.isInterrupted);
  }
}
