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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreateTaskVariablePayloadDiffblueTest {
  /**
   * Test {@link CreateTaskVariablePayload#CreateTaskVariablePayload()}.
   * <p>
   * Method under test:
   * {@link CreateTaskVariablePayload#CreateTaskVariablePayload()}
   */
  @Test
  @DisplayName("Test new CreateTaskVariablePayload()")
  void testNewCreateTaskVariablePayload() {
    // Arrange and Act
    CreateTaskVariablePayload actualCreateTaskVariablePayload = new CreateTaskVariablePayload();

    // Assert
    assertNull(actualCreateTaskVariablePayload.getValue());
    assertNull(actualCreateTaskVariablePayload.getName());
    assertNull(actualCreateTaskVariablePayload.getTaskId());
  }

  /**
   * Test
   * {@link CreateTaskVariablePayload#CreateTaskVariablePayload(String, String, Object)}.
   * <p>
   * Method under test:
   * {@link CreateTaskVariablePayload#CreateTaskVariablePayload(String, String, Object)}
   */
  @Test
  @DisplayName("Test new CreateTaskVariablePayload(String, String, Object)")
  void testNewCreateTaskVariablePayload2() {
    // Arrange and Act
    CreateTaskVariablePayload actualCreateTaskVariablePayload = new CreateTaskVariablePayload("42", "Name", "Value");

    // Assert
    assertEquals("42", actualCreateTaskVariablePayload.getTaskId());
    assertEquals("Name", actualCreateTaskVariablePayload.getName());
    assertEquals("Value", actualCreateTaskVariablePayload.getValue());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link CreateTaskVariablePayload#setName(String)}
   *   <li>{@link CreateTaskVariablePayload#setTaskId(String)}
   *   <li>{@link CreateTaskVariablePayload#setValue(Object)}
   *   <li>{@link CreateTaskVariablePayload#getId()}
   *   <li>{@link CreateTaskVariablePayload#getName()}
   *   <li>{@link CreateTaskVariablePayload#getTaskId()}
   *   <li>{@link CreateTaskVariablePayload#getValue()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    CreateTaskVariablePayload createTaskVariablePayload = new CreateTaskVariablePayload();

    // Act
    createTaskVariablePayload.setName("Name");
    createTaskVariablePayload.setTaskId("42");
    createTaskVariablePayload.setValue("Value");
    createTaskVariablePayload.getId();
    String actualName = createTaskVariablePayload.getName();
    String actualTaskId = createTaskVariablePayload.getTaskId();

    // Assert that nothing has changed
    assertEquals("42", actualTaskId);
    assertEquals("Name", actualName);
    assertEquals("Value", createTaskVariablePayload.getValue());
  }
}
