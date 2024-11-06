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
package org.activiti.engine.impl.persistence.entity.data.impl.cachematcher;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.engine.impl.persistence.entity.TimerJobEntity;
import org.activiti.engine.impl.persistence.entity.TimerJobEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class TimerJobsByExecutionIdMatcherDiffblueTest {
  /**
   * Test {@link TimerJobsByExecutionIdMatcher#isRetained(TimerJobEntity, Object)}
   * with {@code TimerJobEntity}, {@code Object}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerJobsByExecutionIdMatcher#isRetained(TimerJobEntity, Object)}
   */
  @Test
  public void testIsRetainedWithTimerJobEntityObject_given42_thenReturnFalse() {
    // Arrange
    TimerJobsByExecutionIdMatcher timerJobsByExecutionIdMatcher = new TimerJobsByExecutionIdMatcher();
    TimerJobEntity jobEntity = mock(TimerJobEntity.class);
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualIsRetainedResult = timerJobsByExecutionIdMatcher.isRetained(jobEntity, JSONObject.NULL);

    // Assert
    verify(jobEntity, atLeast(1)).getExecutionId();
    assertFalse(actualIsRetainedResult);
  }

  /**
   * Test {@link TimerJobsByExecutionIdMatcher#isRetained(TimerJobEntity, Object)}
   * with {@code TimerJobEntity}, {@code Object}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@code 42}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerJobsByExecutionIdMatcher#isRetained(TimerJobEntity, Object)}
   */
  @Test
  public void testIsRetainedWithTimerJobEntityObject_given42_when42_thenReturnTrue() {
    // Arrange
    TimerJobsByExecutionIdMatcher timerJobsByExecutionIdMatcher = new TimerJobsByExecutionIdMatcher();
    TimerJobEntity jobEntity = mock(TimerJobEntity.class);
    when(jobEntity.getExecutionId()).thenReturn("42");

    // Act
    boolean actualIsRetainedResult = timerJobsByExecutionIdMatcher.isRetained(jobEntity, "42");

    // Assert
    verify(jobEntity, atLeast(1)).getExecutionId();
    assertTrue(actualIsRetainedResult);
  }

  /**
   * Test {@link TimerJobsByExecutionIdMatcher#isRetained(TimerJobEntity, Object)}
   * with {@code TimerJobEntity}, {@code Object}.
   * <ul>
   *   <li>When {@link TimerJobEntityImpl} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerJobsByExecutionIdMatcher#isRetained(TimerJobEntity, Object)}
   */
  @Test
  public void testIsRetainedWithTimerJobEntityObject_whenTimerJobEntityImpl_thenReturnFalse() {
    // Arrange
    TimerJobsByExecutionIdMatcher timerJobsByExecutionIdMatcher = new TimerJobsByExecutionIdMatcher();

    // Act and Assert
    assertFalse(timerJobsByExecutionIdMatcher.isRetained(new TimerJobEntityImpl(), JSONObject.NULL));
  }
}
