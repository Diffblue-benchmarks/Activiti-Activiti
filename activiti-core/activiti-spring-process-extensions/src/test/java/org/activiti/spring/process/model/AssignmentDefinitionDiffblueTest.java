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
import org.junit.jupiter.api.Test;

class AssignmentDefinitionDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AssignmentDefinition#equals(Object)}
   *   <li>{@link AssignmentDefinition#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    AssignmentDefinition assignmentDefinition = new AssignmentDefinition("42",
        AssignmentDefinition.AssignmentEnum.ASSIGNEE, AssignmentDefinition.AssignmentType.STATIC,
        AssignmentDefinition.AssignmentMode.SEQUENTIAL);
    AssignmentDefinition assignmentDefinition2 = new AssignmentDefinition("42",
        AssignmentDefinition.AssignmentEnum.ASSIGNEE, AssignmentDefinition.AssignmentType.STATIC,
        AssignmentDefinition.AssignmentMode.SEQUENTIAL);

    // Act and Assert
    assertEquals(assignmentDefinition, assignmentDefinition2);
    int expectedHashCodeResult = assignmentDefinition.hashCode();
    assertEquals(expectedHashCodeResult, assignmentDefinition2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AssignmentDefinition#equals(Object)}
   *   <li>{@link AssignmentDefinition#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    AssignmentDefinition assignmentDefinition = new AssignmentDefinition("42",
        AssignmentDefinition.AssignmentEnum.ASSIGNEE, AssignmentDefinition.AssignmentType.STATIC,
        AssignmentDefinition.AssignmentMode.SEQUENTIAL);

    // Act and Assert
    assertEquals(assignmentDefinition, assignmentDefinition);
    int expectedHashCodeResult = assignmentDefinition.hashCode();
    assertEquals(expectedHashCodeResult, assignmentDefinition.hashCode());
  }

  /**
   * Method under test: {@link AssignmentDefinition#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    AssignmentDefinition assignmentDefinition = new AssignmentDefinition("Id",
        AssignmentDefinition.AssignmentEnum.ASSIGNEE, AssignmentDefinition.AssignmentType.STATIC,
        AssignmentDefinition.AssignmentMode.SEQUENTIAL);

    // Act and Assert
    assertNotEquals(assignmentDefinition, new AssignmentDefinition("42", AssignmentDefinition.AssignmentEnum.ASSIGNEE,
        AssignmentDefinition.AssignmentType.STATIC, AssignmentDefinition.AssignmentMode.SEQUENTIAL));
  }

  /**
   * Method under test: {@link AssignmentDefinition#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    AssignmentDefinition assignmentDefinition = new AssignmentDefinition("42", null,
        AssignmentDefinition.AssignmentType.STATIC, AssignmentDefinition.AssignmentMode.SEQUENTIAL);

    // Act and Assert
    assertNotEquals(assignmentDefinition, new AssignmentDefinition("42", AssignmentDefinition.AssignmentEnum.ASSIGNEE,
        AssignmentDefinition.AssignmentType.STATIC, AssignmentDefinition.AssignmentMode.SEQUENTIAL));
  }

  /**
   * Method under test: {@link AssignmentDefinition#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    AssignmentDefinition assignmentDefinition = new AssignmentDefinition("42",
        AssignmentDefinition.AssignmentEnum.ASSIGNEE, null, AssignmentDefinition.AssignmentMode.SEQUENTIAL);

    // Act and Assert
    assertNotEquals(assignmentDefinition, new AssignmentDefinition("42", AssignmentDefinition.AssignmentEnum.ASSIGNEE,
        AssignmentDefinition.AssignmentType.STATIC, AssignmentDefinition.AssignmentMode.SEQUENTIAL));
  }

  /**
   * Method under test: {@link AssignmentDefinition#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    AssignmentDefinition assignmentDefinition = new AssignmentDefinition("42",
        AssignmentDefinition.AssignmentEnum.ASSIGNEE, AssignmentDefinition.AssignmentType.STATIC, null);

    // Act and Assert
    assertNotEquals(assignmentDefinition, new AssignmentDefinition("42", AssignmentDefinition.AssignmentEnum.ASSIGNEE,
        AssignmentDefinition.AssignmentType.STATIC, AssignmentDefinition.AssignmentMode.SEQUENTIAL));
  }

  /**
   * Method under test: {@link AssignmentDefinition#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new AssignmentDefinition("42", AssignmentDefinition.AssignmentEnum.ASSIGNEE,
        AssignmentDefinition.AssignmentType.STATIC, AssignmentDefinition.AssignmentMode.SEQUENTIAL), null);
  }

  /**
   * Method under test: {@link AssignmentDefinition#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(
        new AssignmentDefinition("42", AssignmentDefinition.AssignmentEnum.ASSIGNEE,
            AssignmentDefinition.AssignmentType.STATIC, AssignmentDefinition.AssignmentMode.SEQUENTIAL),
        "Different type to AssignmentDefinition");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AssignmentDefinition#AssignmentDefinition()}
   *   <li>
   * {@link AssignmentDefinition#setAssignment(AssignmentDefinition.AssignmentEnum)}
   *   <li>{@link AssignmentDefinition#setId(String)}
   *   <li>{@link AssignmentDefinition#setMode(AssignmentDefinition.AssignmentMode)}
   *   <li>{@link AssignmentDefinition#setType(AssignmentDefinition.AssignmentType)}
   *   <li>{@link AssignmentDefinition#toString()}
   *   <li>{@link AssignmentDefinition#getAssignment()}
   *   <li>{@link AssignmentDefinition#getId()}
   *   <li>{@link AssignmentDefinition#getMode()}
   *   <li>{@link AssignmentDefinition#getType()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    AssignmentDefinition actualAssignmentDefinition = new AssignmentDefinition();
    actualAssignmentDefinition.setAssignment(AssignmentDefinition.AssignmentEnum.ASSIGNEE);
    actualAssignmentDefinition.setId("42");
    actualAssignmentDefinition.setMode(AssignmentDefinition.AssignmentMode.SEQUENTIAL);
    actualAssignmentDefinition.setType(AssignmentDefinition.AssignmentType.STATIC);
    String actualToStringResult = actualAssignmentDefinition.toString();
    AssignmentDefinition.AssignmentEnum actualAssignment = actualAssignmentDefinition.getAssignment();
    String actualId = actualAssignmentDefinition.getId();
    AssignmentDefinition.AssignmentMode actualMode = actualAssignmentDefinition.getMode();

    // Assert that nothing has changed
    assertEquals("42", actualId);
    assertEquals("AssignmentDefinition{id='42', assignment=ASSIGNEE, type=STATIC, mode=SEQUENTIAL}",
        actualToStringResult);
    assertEquals(AssignmentDefinition.AssignmentEnum.ASSIGNEE, actualAssignment);
    assertEquals(AssignmentDefinition.AssignmentMode.SEQUENTIAL, actualMode);
    assertEquals(AssignmentDefinition.AssignmentType.STATIC, actualAssignmentDefinition.getType());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link AssignmentDefinition#AssignmentDefinition(String, AssignmentDefinition.AssignmentEnum, AssignmentDefinition.AssignmentType, AssignmentDefinition.AssignmentMode)}
   *   <li>
   * {@link AssignmentDefinition#setAssignment(AssignmentDefinition.AssignmentEnum)}
   *   <li>{@link AssignmentDefinition#setId(String)}
   *   <li>{@link AssignmentDefinition#setMode(AssignmentDefinition.AssignmentMode)}
   *   <li>{@link AssignmentDefinition#setType(AssignmentDefinition.AssignmentType)}
   *   <li>{@link AssignmentDefinition#toString()}
   *   <li>{@link AssignmentDefinition#getAssignment()}
   *   <li>{@link AssignmentDefinition#getId()}
   *   <li>{@link AssignmentDefinition#getMode()}
   *   <li>{@link AssignmentDefinition#getType()}
   * </ul>
   */
  @Test
  void testGettersAndSetters2() {
    // Arrange and Act
    AssignmentDefinition actualAssignmentDefinition = new AssignmentDefinition("42",
        AssignmentDefinition.AssignmentEnum.ASSIGNEE, AssignmentDefinition.AssignmentType.STATIC,
        AssignmentDefinition.AssignmentMode.SEQUENTIAL);
    actualAssignmentDefinition.setAssignment(AssignmentDefinition.AssignmentEnum.ASSIGNEE);
    actualAssignmentDefinition.setId("42");
    actualAssignmentDefinition.setMode(AssignmentDefinition.AssignmentMode.SEQUENTIAL);
    actualAssignmentDefinition.setType(AssignmentDefinition.AssignmentType.STATIC);
    String actualToStringResult = actualAssignmentDefinition.toString();
    AssignmentDefinition.AssignmentEnum actualAssignment = actualAssignmentDefinition.getAssignment();
    String actualId = actualAssignmentDefinition.getId();
    AssignmentDefinition.AssignmentMode actualMode = actualAssignmentDefinition.getMode();

    // Assert that nothing has changed
    assertEquals("42", actualId);
    assertEquals("AssignmentDefinition{id='42', assignment=ASSIGNEE, type=STATIC, mode=SEQUENTIAL}",
        actualToStringResult);
    assertEquals(AssignmentDefinition.AssignmentEnum.ASSIGNEE, actualAssignment);
    assertEquals(AssignmentDefinition.AssignmentMode.SEQUENTIAL, actualMode);
    assertEquals(AssignmentDefinition.AssignmentType.STATIC, actualAssignmentDefinition.getType());
  }
}
