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
import org.junit.jupiter.api.Test;

class UpdateTaskVariablePayloadDiffblueTest {
  /**
   * Methods under test:
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

    // Assert that nothing has changed
    assertEquals("42", actualTaskId);
    assertEquals("Name", actualName);
    assertEquals("Value", updateTaskVariablePayload.getValue());
  }

  /**
   * Method under test:
   * {@link UpdateTaskVariablePayload#UpdateTaskVariablePayload()}
   */
  @Test
  void testNewUpdateTaskVariablePayload() {
    // Arrange and Act
    UpdateTaskVariablePayload actualUpdateTaskVariablePayload = new UpdateTaskVariablePayload();

    // Assert
    assertNull(actualUpdateTaskVariablePayload.getValue());
    assertNull(actualUpdateTaskVariablePayload.getName());
    assertNull(actualUpdateTaskVariablePayload.getTaskId());
  }

  /**
   * Method under test:
   * {@link UpdateTaskVariablePayload#UpdateTaskVariablePayload(String, String, Object)}
   */
  @Test
  void testNewUpdateTaskVariablePayload2() {
    // Arrange and Act
    UpdateTaskVariablePayload actualUpdateTaskVariablePayload = new UpdateTaskVariablePayload("42", "Name", "Value");

    // Assert
    assertEquals("42", actualUpdateTaskVariablePayload.getTaskId());
    assertEquals("Name", actualUpdateTaskVariablePayload.getName());
    assertEquals("Value", actualUpdateTaskVariablePayload.getValue());
  }
}
