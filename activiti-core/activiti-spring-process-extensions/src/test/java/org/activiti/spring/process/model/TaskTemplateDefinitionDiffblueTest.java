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
package org.activiti.spring.process.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TaskTemplateDefinitionDiffblueTest {
  /**
   * Test {@link TaskTemplateDefinition#equals(Object)}, and
   * {@link TaskTemplateDefinition#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link TaskTemplateDefinition#equals(Object)}
   *   <li>{@link TaskTemplateDefinition#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    TaskTemplateDefinition taskTemplateDefinition = new TaskTemplateDefinition();
    taskTemplateDefinition.setAssignee(new TemplateDefinition(TemplateDefinition.TemplateType.VARIABLE, "42"));
    taskTemplateDefinition.setCandidate(new TemplateDefinition(TemplateDefinition.TemplateType.VARIABLE, "42"));

    TaskTemplateDefinition taskTemplateDefinition2 = new TaskTemplateDefinition();
    taskTemplateDefinition2.setAssignee(new TemplateDefinition(TemplateDefinition.TemplateType.VARIABLE, "42"));
    taskTemplateDefinition2.setCandidate(new TemplateDefinition(TemplateDefinition.TemplateType.VARIABLE, "42"));

    // Act and Assert
    assertEquals(taskTemplateDefinition, taskTemplateDefinition2);
    int expectedHashCodeResult = taskTemplateDefinition.hashCode();
    assertEquals(expectedHashCodeResult, taskTemplateDefinition2.hashCode());
  }

  /**
   * Test {@link TaskTemplateDefinition#equals(Object)}, and
   * {@link TaskTemplateDefinition#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link TaskTemplateDefinition#equals(Object)}
   *   <li>{@link TaskTemplateDefinition#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    TaskTemplateDefinition taskTemplateDefinition = new TaskTemplateDefinition();
    taskTemplateDefinition.setAssignee(new TemplateDefinition(TemplateDefinition.TemplateType.VARIABLE, "42"));
    taskTemplateDefinition.setCandidate(new TemplateDefinition(TemplateDefinition.TemplateType.VARIABLE, "42"));

    // Act and Assert
    assertEquals(taskTemplateDefinition, taskTemplateDefinition);
    int expectedHashCodeResult = taskTemplateDefinition.hashCode();
    assertEquals(expectedHashCodeResult, taskTemplateDefinition.hashCode());
  }

  /**
   * Test {@link TaskTemplateDefinition#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskTemplateDefinition#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    TaskTemplateDefinition taskTemplateDefinition = new TaskTemplateDefinition();
    taskTemplateDefinition.setAssignee(new TemplateDefinition(null, "42"));
    taskTemplateDefinition.setCandidate(new TemplateDefinition(TemplateDefinition.TemplateType.VARIABLE, "42"));

    TaskTemplateDefinition taskTemplateDefinition2 = new TaskTemplateDefinition();
    taskTemplateDefinition2.setAssignee(new TemplateDefinition(TemplateDefinition.TemplateType.VARIABLE, "42"));
    taskTemplateDefinition2.setCandidate(new TemplateDefinition(TemplateDefinition.TemplateType.VARIABLE, "42"));

    // Act and Assert
    assertNotEquals(taskTemplateDefinition, taskTemplateDefinition2);
  }

  /**
   * Test {@link TaskTemplateDefinition#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskTemplateDefinition#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    TaskTemplateDefinition taskTemplateDefinition = new TaskTemplateDefinition();
    taskTemplateDefinition.setAssignee(mock(TemplateDefinition.class));
    taskTemplateDefinition.setCandidate(new TemplateDefinition(TemplateDefinition.TemplateType.VARIABLE, "42"));

    TaskTemplateDefinition taskTemplateDefinition2 = new TaskTemplateDefinition();
    taskTemplateDefinition2.setAssignee(new TemplateDefinition(TemplateDefinition.TemplateType.VARIABLE, "42"));
    taskTemplateDefinition2.setCandidate(new TemplateDefinition(TemplateDefinition.TemplateType.VARIABLE, "42"));

    // Act and Assert
    assertNotEquals(taskTemplateDefinition, taskTemplateDefinition2);
  }

  /**
   * Test {@link TaskTemplateDefinition#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskTemplateDefinition#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    TaskTemplateDefinition taskTemplateDefinition = new TaskTemplateDefinition();
    taskTemplateDefinition.setAssignee(new TemplateDefinition(TemplateDefinition.TemplateType.VARIABLE, "42"));
    taskTemplateDefinition.setCandidate(new TemplateDefinition(null, "42"));

    TaskTemplateDefinition taskTemplateDefinition2 = new TaskTemplateDefinition();
    taskTemplateDefinition2.setAssignee(new TemplateDefinition(TemplateDefinition.TemplateType.VARIABLE, "42"));
    taskTemplateDefinition2.setCandidate(new TemplateDefinition(TemplateDefinition.TemplateType.VARIABLE, "42"));

    // Act and Assert
    assertNotEquals(taskTemplateDefinition, taskTemplateDefinition2);
  }

  /**
   * Test {@link TaskTemplateDefinition#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskTemplateDefinition#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    TaskTemplateDefinition taskTemplateDefinition = new TaskTemplateDefinition();
    taskTemplateDefinition.setAssignee(new TemplateDefinition(TemplateDefinition.TemplateType.VARIABLE, "42"));
    taskTemplateDefinition.setCandidate(new TemplateDefinition(TemplateDefinition.TemplateType.VARIABLE, "42"));

    // Act and Assert
    assertNotEquals(taskTemplateDefinition, null);
  }

  /**
   * Test {@link TaskTemplateDefinition#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskTemplateDefinition#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    TaskTemplateDefinition taskTemplateDefinition = new TaskTemplateDefinition();
    taskTemplateDefinition.setAssignee(new TemplateDefinition(TemplateDefinition.TemplateType.VARIABLE, "42"));
    taskTemplateDefinition.setCandidate(new TemplateDefinition(TemplateDefinition.TemplateType.VARIABLE, "42"));

    // Act and Assert
    assertNotEquals(taskTemplateDefinition, "Different type to TaskTemplateDefinition");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link TaskTemplateDefinition}
   *   <li>{@link TaskTemplateDefinition#setAssignee(TemplateDefinition)}
   *   <li>{@link TaskTemplateDefinition#setCandidate(TemplateDefinition)}
   *   <li>{@link TaskTemplateDefinition#getAssignee()}
   *   <li>{@link TaskTemplateDefinition#getCandidate()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    TaskTemplateDefinition actualTaskTemplateDefinition = new TaskTemplateDefinition();
    TemplateDefinition assignee = new TemplateDefinition(TemplateDefinition.TemplateType.VARIABLE, "42");

    actualTaskTemplateDefinition.setAssignee(assignee);
    TemplateDefinition candidate = new TemplateDefinition(TemplateDefinition.TemplateType.VARIABLE, "42");

    actualTaskTemplateDefinition.setCandidate(candidate);
    TemplateDefinition actualAssignee = actualTaskTemplateDefinition.getAssignee();

    // Assert that nothing has changed
    assertSame(assignee, actualAssignee);
    assertSame(candidate, actualTaskTemplateDefinition.getCandidate());
  }
}
