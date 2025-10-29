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
import org.junit.jupiter.api.Test;

class TaskCandidateGroupImplDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link TaskCandidateGroupImpl#equals(Object)}
   *   <li>{@link TaskCandidateGroupImpl#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    TaskCandidateGroupImpl taskCandidateGroupImpl = new TaskCandidateGroupImpl("42", "42");
    TaskCandidateGroupImpl taskCandidateGroupImpl2 = new TaskCandidateGroupImpl("42", "42");

    // Act and Assert
    assertEquals(taskCandidateGroupImpl, taskCandidateGroupImpl2);
    int expectedHashCodeResult = taskCandidateGroupImpl.hashCode();
    assertEquals(expectedHashCodeResult, taskCandidateGroupImpl2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link TaskCandidateGroupImpl#equals(Object)}
   *   <li>{@link TaskCandidateGroupImpl#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    TaskCandidateGroupImpl taskCandidateGroupImpl = new TaskCandidateGroupImpl("42", "42");

    // Act and Assert
    assertEquals(taskCandidateGroupImpl, taskCandidateGroupImpl);
    int expectedHashCodeResult = taskCandidateGroupImpl.hashCode();
    assertEquals(expectedHashCodeResult, taskCandidateGroupImpl.hashCode());
  }

  /**
   * Method under test: {@link TaskCandidateGroupImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    TaskCandidateGroupImpl taskCandidateGroupImpl = new TaskCandidateGroupImpl("Group Id", "42");

    // Act and Assert
    assertNotEquals(taskCandidateGroupImpl, new TaskCandidateGroupImpl("42", "42"));
  }

  /**
   * Method under test: {@link TaskCandidateGroupImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    TaskCandidateGroupImpl taskCandidateGroupImpl = new TaskCandidateGroupImpl("42", "Task Id");

    // Act and Assert
    assertNotEquals(taskCandidateGroupImpl, new TaskCandidateGroupImpl("42", "42"));
  }

  /**
   * Method under test: {@link TaskCandidateGroupImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new TaskCandidateGroupImpl("42", "42"), null);
  }

  /**
   * Method under test: {@link TaskCandidateGroupImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new TaskCandidateGroupImpl("42", "42"), "Different type to TaskCandidateGroupImpl");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link TaskCandidateGroupImpl#TaskCandidateGroupImpl()}
   *   <li>{@link TaskCandidateGroupImpl#getGroupId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    TaskCandidateGroupImpl actualTaskCandidateGroupImpl = new TaskCandidateGroupImpl();

    // Assert
    assertNull(actualTaskCandidateGroupImpl.getGroupId());
    assertNull(actualTaskCandidateGroupImpl.getTaskId());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link TaskCandidateGroupImpl#TaskCandidateGroupImpl(String, String)}
   *   <li>{@link TaskCandidateGroupImpl#getGroupId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters2() {
    // Arrange and Act
    TaskCandidateGroupImpl actualTaskCandidateGroupImpl = new TaskCandidateGroupImpl("42", "42");

    // Assert
    assertEquals("42", actualTaskCandidateGroupImpl.getGroupId());
    assertEquals("42", actualTaskCandidateGroupImpl.getTaskId());
  }
}
