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
package org.activiti.api.task.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TaskCandidateUserImplDiffblueTest {
  /**
   * Test getters and setters.
   * <ul>
   *   <li>Then return TaskId is {@code null}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link TaskCandidateUserImpl#TaskCandidateUserImpl()}
   *   <li>{@link TaskCandidateUserImpl#getUserId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters; then return TaskId is 'null'")
  void testGettersAndSetters_thenReturnTaskIdIsNull() {
    // Arrange and Act
    TaskCandidateUserImpl actualTaskCandidateUserImpl = new TaskCandidateUserImpl();
    String actualUserId = actualTaskCandidateUserImpl.getUserId();

    // Assert
    assertNull(actualTaskCandidateUserImpl.getTaskId());
    assertNull(actualUserId);
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return TaskId is {@code 42}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link TaskCandidateUserImpl#TaskCandidateUserImpl(String, String)}
   *   <li>{@link TaskCandidateUserImpl#getUserId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters; when '42'; then return TaskId is '42'")
  void testGettersAndSetters_when42_thenReturnTaskIdIs42() {
    // Arrange and Act
    TaskCandidateUserImpl actualTaskCandidateUserImpl = new TaskCandidateUserImpl("42", "42");
    String actualUserId = actualTaskCandidateUserImpl.getUserId();

    // Assert
    assertEquals("42", actualTaskCandidateUserImpl.getTaskId());
    assertEquals("42", actualUserId);
  }

  /**
   * Test {@link TaskCandidateUserImpl#equals(Object)}, and
   * {@link TaskCandidateUserImpl#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link TaskCandidateUserImpl#equals(Object)}
   *   <li>{@link TaskCandidateUserImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    TaskCandidateUserImpl taskCandidateUserImpl = new TaskCandidateUserImpl("42", "42");
    TaskCandidateUserImpl taskCandidateUserImpl2 = new TaskCandidateUserImpl("42", "42");

    // Act and Assert
    assertEquals(taskCandidateUserImpl, taskCandidateUserImpl2);
    int expectedHashCodeResult = taskCandidateUserImpl.hashCode();
    assertEquals(expectedHashCodeResult, taskCandidateUserImpl2.hashCode());
  }

  /**
   * Test {@link TaskCandidateUserImpl#equals(Object)}, and
   * {@link TaskCandidateUserImpl#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link TaskCandidateUserImpl#equals(Object)}
   *   <li>{@link TaskCandidateUserImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    TaskCandidateUserImpl taskCandidateUserImpl = new TaskCandidateUserImpl("42", "42");

    // Act and Assert
    assertEquals(taskCandidateUserImpl, taskCandidateUserImpl);
    int expectedHashCodeResult = taskCandidateUserImpl.hashCode();
    assertEquals(expectedHashCodeResult, taskCandidateUserImpl.hashCode());
  }

  /**
   * Test {@link TaskCandidateUserImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskCandidateUserImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    TaskCandidateUserImpl taskCandidateUserImpl = new TaskCandidateUserImpl("User Id", "42");

    // Act and Assert
    assertNotEquals(taskCandidateUserImpl, new TaskCandidateUserImpl("42", "42"));
  }

  /**
   * Test {@link TaskCandidateUserImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskCandidateUserImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    TaskCandidateUserImpl taskCandidateUserImpl = new TaskCandidateUserImpl("42", "Task Id");

    // Act and Assert
    assertNotEquals(taskCandidateUserImpl, new TaskCandidateUserImpl("42", "42"));
  }

  /**
   * Test {@link TaskCandidateUserImpl#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskCandidateUserImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new TaskCandidateUserImpl("42", "42"), null);
  }

  /**
   * Test {@link TaskCandidateUserImpl#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskCandidateUserImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new TaskCandidateUserImpl("42", "42"), "Different type to TaskCandidateUserImpl");
  }
}
