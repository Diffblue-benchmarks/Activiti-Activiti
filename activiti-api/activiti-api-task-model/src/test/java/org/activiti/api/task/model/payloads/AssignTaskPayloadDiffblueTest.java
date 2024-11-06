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

class AssignTaskPayloadDiffblueTest {
  /**
   * Test {@link AssignTaskPayload#AssignTaskPayload()}.
   * <p>
   * Method under test: {@link AssignTaskPayload#AssignTaskPayload()}
   */
  @Test
  @DisplayName("Test new AssignTaskPayload()")
  void testNewAssignTaskPayload() {
    // Arrange and Act
    AssignTaskPayload actualAssignTaskPayload = new AssignTaskPayload();

    // Assert
    assertNull(actualAssignTaskPayload.getAssignee());
    assertNull(actualAssignTaskPayload.getTaskId());
  }

  /**
   * Test {@link AssignTaskPayload#AssignTaskPayload(String, String)}.
   * <p>
   * Method under test:
   * {@link AssignTaskPayload#AssignTaskPayload(String, String)}
   */
  @Test
  @DisplayName("Test new AssignTaskPayload(String, String)")
  void testNewAssignTaskPayload2() {
    // Arrange and Act
    AssignTaskPayload actualAssignTaskPayload = new AssignTaskPayload("42", "Assignee");

    // Assert
    assertEquals("42", actualAssignTaskPayload.getTaskId());
    assertEquals("Assignee", actualAssignTaskPayload.getAssignee());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AssignTaskPayload#setAssignee(String)}
   *   <li>{@link AssignTaskPayload#setTaskId(String)}
   *   <li>{@link AssignTaskPayload#getAssignee()}
   *   <li>{@link AssignTaskPayload#getId()}
   *   <li>{@link AssignTaskPayload#getTaskId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    AssignTaskPayload assignTaskPayload = new AssignTaskPayload();

    // Act
    assignTaskPayload.setAssignee("Assignee");
    assignTaskPayload.setTaskId("42");
    String actualAssignee = assignTaskPayload.getAssignee();
    assignTaskPayload.getId();

    // Assert that nothing has changed
    assertEquals("42", assignTaskPayload.getTaskId());
    assertEquals("Assignee", actualAssignee);
  }
}
