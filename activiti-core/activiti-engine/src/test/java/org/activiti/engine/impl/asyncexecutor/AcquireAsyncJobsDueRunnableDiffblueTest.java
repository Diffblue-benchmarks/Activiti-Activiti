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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AcquireAsyncJobsDueRunnableDiffblueTest {
  /**
   * Test {@link AcquireAsyncJobsDueRunnable#AcquireAsyncJobsDueRunnable(AsyncExecutor)}.
   *
   * <p>Method under test: {@link
   * AcquireAsyncJobsDueRunnable#AcquireAsyncJobsDueRunnable(AsyncExecutor)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AcquireAsyncJobsDueRunnable.<init>(AsyncExecutor)"})
  public void testNewAcquireAsyncJobsDueRunnable() {
    // Arrange and Act
    AcquireAsyncJobsDueRunnable actualAcquireAsyncJobsDueRunnable =
        new AcquireAsyncJobsDueRunnable(new DefaultAsyncJobExecutor());

    // Assert
    assertTrue(actualAcquireAsyncJobsDueRunnable.asyncExecutor instanceof DefaultAsyncJobExecutor);
    assertEquals(0L, actualAcquireAsyncJobsDueRunnable.getMillisToWait());
    assertFalse(actualAcquireAsyncJobsDueRunnable.isInterrupted);
  }

  /**
   * Test {@link AcquireAsyncJobsDueRunnable#stop()}.
   *
   * <p>Method under test: {@link AcquireAsyncJobsDueRunnable#stop()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void AcquireAsyncJobsDueRunnable.stop()"})
  public void testStop() {
    // Arrange
    AcquireAsyncJobsDueRunnable acquireAsyncJobsDueRunnable =
        new AcquireAsyncJobsDueRunnable(new DefaultAsyncJobExecutor());

    // Act
    acquireAsyncJobsDueRunnable.stop();

    // Assert
    assertTrue(acquireAsyncJobsDueRunnable.isInterrupted);
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link AcquireAsyncJobsDueRunnable#setMillisToWait(long)}
   *   <li>{@link AcquireAsyncJobsDueRunnable#getMillisToWait()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "long AcquireAsyncJobsDueRunnable.getMillisToWait()",
    "void AcquireAsyncJobsDueRunnable.setMillisToWait(long)"
  })
  public void testGettersAndSetters() {
    // Arrange
    AcquireAsyncJobsDueRunnable acquireAsyncJobsDueRunnable =
        new AcquireAsyncJobsDueRunnable(new DefaultAsyncJobExecutor());

    // Act
    acquireAsyncJobsDueRunnable.setMillisToWait(1L);

    // Assert
    assertEquals(1L, acquireAsyncJobsDueRunnable.getMillisToWait());
  }
}
