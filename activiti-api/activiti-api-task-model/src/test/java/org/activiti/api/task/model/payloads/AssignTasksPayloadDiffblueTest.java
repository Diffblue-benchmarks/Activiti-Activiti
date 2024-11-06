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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AssignTasksPayloadDiffblueTest {
  /**
   * Test {@link AssignTasksPayload#AssignTasksPayload()}.
   * <p>
   * Method under test: {@link AssignTasksPayload#AssignTasksPayload()}
   */
  @Test
  @DisplayName("Test new AssignTasksPayload()")
  void testNewAssignTasksPayload() {
    // Arrange and Act
    AssignTasksPayload actualAssignTasksPayload = new AssignTasksPayload();

    // Assert
    assertNull(actualAssignTasksPayload.getAssignee());
    assertNull(actualAssignTasksPayload.getTaskIds());
  }

  /**
   * Test {@link AssignTasksPayload#AssignTasksPayload(List, String)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AssignTasksPayload#AssignTasksPayload(List, String)}
   */
  @Test
  @DisplayName("Test new AssignTasksPayload(List, String); given '42'; when ArrayList() add '42'")
  void testNewAssignTasksPayload_given42_whenArrayListAdd42() {
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

  /**
   * Test {@link AssignTasksPayload#AssignTasksPayload(List, String)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>Then return TaskIds is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AssignTasksPayload#AssignTasksPayload(List, String)}
   */
  @Test
  @DisplayName("Test new AssignTasksPayload(List, String); given 'foo'; then return TaskIds is ArrayList()")
  void testNewAssignTasksPayload_givenFoo_thenReturnTaskIdsIsArrayList() {
    // Arrange
    ArrayList<String> taskIds = new ArrayList<>();
    taskIds.add("foo");

    // Act
    AssignTasksPayload actualAssignTasksPayload = new AssignTasksPayload(taskIds, "Assignee");

    // Assert
    assertEquals("Assignee", actualAssignTasksPayload.getAssignee());
    assertSame(taskIds, actualAssignTasksPayload.getTaskIds());
  }

  /**
   * Test {@link AssignTasksPayload#AssignTasksPayload(List, String)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return TaskIds Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AssignTasksPayload#AssignTasksPayload(List, String)}
   */
  @Test
  @DisplayName("Test new AssignTasksPayload(List, String); when ArrayList(); then return TaskIds Empty")
  void testNewAssignTasksPayload_whenArrayList_thenReturnTaskIdsEmpty() {
    // Arrange and Act
    AssignTasksPayload actualAssignTasksPayload = new AssignTasksPayload(new ArrayList<>(), "Assignee");

    // Assert
    assertEquals("Assignee", actualAssignTasksPayload.getAssignee());
    assertTrue(actualAssignTasksPayload.getTaskIds().isEmpty());
  }

  /**
   * Test getters and setters.
   * <p>
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
  @DisplayName("Test getters and setters")
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
}
