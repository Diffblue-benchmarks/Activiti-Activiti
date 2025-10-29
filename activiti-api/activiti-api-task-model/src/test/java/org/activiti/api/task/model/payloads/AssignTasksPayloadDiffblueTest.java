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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class AssignTasksPayloadDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AssignTasksPayload#setAssignee(String)}
   *   <li>{@link AssignTasksPayload#setTaskIds(List)}
   *   <li>{@link AssignTasksPayload#getAssignee()}
   *   <li>{@link AssignTasksPayload#getId()}
   *   <li>{@link AssignTasksPayload#getTaskIds()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    AssignTasksPayload assignTasksPayload = new AssignTasksPayload();

    // Act
    assignTasksPayload.setAssignee("Assignee");
    ArrayList<String> taskIds = new ArrayList<>();
    assignTasksPayload.setTaskIds(taskIds);
    String actualAssignee = assignTasksPayload.getAssignee();
    assignTasksPayload.getId();
    List<String> actualTaskIds = assignTasksPayload.getTaskIds();

    // Assert that nothing has changed
    assertEquals("Assignee", actualAssignee);
    assertTrue(actualTaskIds.isEmpty());
    assertSame(taskIds, actualTaskIds);
  }

  /**
   * Method under test: {@link AssignTasksPayload#AssignTasksPayload()}
   */
  @Test
  void testNewAssignTasksPayload() {
    // Arrange and Act
    AssignTasksPayload actualAssignTasksPayload = new AssignTasksPayload();

    // Assert
    assertNull(actualAssignTasksPayload.getAssignee());
    assertNull(actualAssignTasksPayload.getTaskIds());
  }

  /**
   * Method under test:
   * {@link AssignTasksPayload#AssignTasksPayload(List, String)}
   */
  @Test
  void testNewAssignTasksPayload2() {
    // Arrange
    ArrayList<String> taskIds = new ArrayList<>();

    // Act
    AssignTasksPayload actualAssignTasksPayload = new AssignTasksPayload(taskIds, "Assignee");

    // Assert
    assertEquals("Assignee", actualAssignTasksPayload.getAssignee());
    List<String> taskIds2 = actualAssignTasksPayload.getTaskIds();
    assertTrue(taskIds2.isEmpty());
    assertSame(taskIds, taskIds2);
  }

  /**
   * Method under test:
   * {@link AssignTasksPayload#AssignTasksPayload(List, String)}
   */
  @Test
  void testNewAssignTasksPayload3() {
    // Arrange
    ArrayList<String> taskIds = new ArrayList<>();
    taskIds.add("foo");

    // Act
    AssignTasksPayload actualAssignTasksPayload = new AssignTasksPayload(taskIds, "Assignee");

    // Assert
    assertEquals("Assignee", actualAssignTasksPayload.getAssignee());
    List<String> taskIds2 = actualAssignTasksPayload.getTaskIds();
    assertEquals(1, taskIds2.size());
    assertEquals("foo", taskIds2.get(0));
    assertSame(taskIds, taskIds2);
  }

  /**
   * Method under test:
   * {@link AssignTasksPayload#AssignTasksPayload(List, String)}
   */
  @Test
  void testNewAssignTasksPayload4() {
    // Arrange
    ArrayList<String> taskIds = new ArrayList<>();
    taskIds.add("42");
    taskIds.add("foo");

    // Act
    AssignTasksPayload actualAssignTasksPayload = new AssignTasksPayload(taskIds, "Assignee");

    // Assert
    assertEquals("Assignee", actualAssignTasksPayload.getAssignee());
    assertSame(taskIds, actualAssignTasksPayload.getTaskIds());
  }
}
