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
package org.activiti.api.task.model.payloads;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class UpdateTaskVariablePayloadDiffblueTest {
  /**
   * Test {@link UpdateTaskVariablePayload#UpdateTaskVariablePayload()}.
   *
   * <p>Method under test: {@link UpdateTaskVariablePayload#UpdateTaskVariablePayload()}
   */
  @Test
  @DisplayName("Test new UpdateTaskVariablePayload()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateTaskVariablePayload.<init>()"})
  void testNewUpdateTaskVariablePayload() {
    // Arrange and Act
    UpdateTaskVariablePayload actualUpdateTaskVariablePayload = new UpdateTaskVariablePayload();

    // Assert
    assertNull(actualUpdateTaskVariablePayload.getValue());
    assertNull(actualUpdateTaskVariablePayload.getName());
    assertNull(actualUpdateTaskVariablePayload.getTaskId());
  }

  /**
   * Test {@link UpdateTaskVariablePayload#UpdateTaskVariablePayload(String, String, Object)}.
   *
   * <p>Method under test: {@link UpdateTaskVariablePayload#UpdateTaskVariablePayload(String,
   * String, Object)}
   */
  @Test
  @DisplayName("Test new UpdateTaskVariablePayload(String, String, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateTaskVariablePayload.<init>(String, String, Object)"})
  void testNewUpdateTaskVariablePayload2() {
    // Arrange and Act
    UpdateTaskVariablePayload actualUpdateTaskVariablePayload =
        new UpdateTaskVariablePayload("42", "Name", "Value");

    // Assert
    assertEquals("42", actualUpdateTaskVariablePayload.getTaskId());
    assertEquals("Name", actualUpdateTaskVariablePayload.getName());
    assertEquals("Value", actualUpdateTaskVariablePayload.getValue());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link UpdateTaskVariablePayload#setName(String)}
   *   <li>{@link UpdateTaskVariablePayload#setTaskId(String)}
   *   <li>{@link UpdateTaskVariablePayload#setValue(Object)}
   *   <li>{@link UpdateTaskVariablePayload#getId()}
   *   <li>{@link UpdateTaskVariablePayload#getName()}
   *   <li>{@link UpdateTaskVariablePayload#getTaskId()}
   *   <li>{@link UpdateTaskVariablePayload#getValue()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String UpdateTaskVariablePayload.getId()",
    "String UpdateTaskVariablePayload.getName()",
    "String UpdateTaskVariablePayload.getTaskId()",
    "Object UpdateTaskVariablePayload.getValue()",
    "void UpdateTaskVariablePayload.setName(String)",
    "void UpdateTaskVariablePayload.setTaskId(String)",
    "void UpdateTaskVariablePayload.setValue(Object)"
  })
  void testGettersAndSetters() {
    // Arrange
    UpdateTaskVariablePayload updateTaskVariablePayload = new UpdateTaskVariablePayload();

    // Act
    updateTaskVariablePayload.setName("Name");
    updateTaskVariablePayload.setTaskId("42");
    updateTaskVariablePayload.setValue("Value");
    updateTaskVariablePayload.getId();
    String actualName = updateTaskVariablePayload.getName();
    String actualTaskId = updateTaskVariablePayload.getTaskId();

    // Assert
    assertEquals("42", actualTaskId);
    assertEquals("Name", actualName);
    assertEquals("Value", updateTaskVariablePayload.getValue());
  }
}
