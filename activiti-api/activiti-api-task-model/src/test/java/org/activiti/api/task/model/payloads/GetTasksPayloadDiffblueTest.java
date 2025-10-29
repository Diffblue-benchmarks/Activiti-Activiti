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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class GetTasksPayloadDiffblueTest {
  /**
   * Method under test: {@link GetTasksPayload#isStandalone()}
   */
  @Test
  void testIsStandalone() {
    // Arrange, Act and Assert
    assertTrue((new GetTasksPayload()).isStandalone());
  }

  /**
   * Method under test: {@link GetTasksPayload#isStandalone()}
   */
  @Test
  void testIsStandalone2() {
    // Arrange
    GetTasksPayload getTasksPayload = new GetTasksPayload();
    getTasksPayload.setProcessInstanceId("foo");

    // Act and Assert
    assertFalse(getTasksPayload.isStandalone());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetTasksPayload#setAssigneeId(String)}
   *   <li>{@link GetTasksPayload#setGroups(List)}
   *   <li>{@link GetTasksPayload#setParentTaskId(String)}
   *   <li>{@link GetTasksPayload#setProcessInstanceId(String)}
   *   <li>{@link GetTasksPayload#getAssigneeId()}
   *   <li>{@link GetTasksPayload#getGroups()}
   *   <li>{@link GetTasksPayload#getId()}
   *   <li>{@link GetTasksPayload#getParentTaskId()}
   *   <li>{@link GetTasksPayload#getProcessInstanceId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    GetTasksPayload getTasksPayload = new GetTasksPayload();

    // Act
    getTasksPayload.setAssigneeId("42");
    ArrayList<String> groups = new ArrayList<>();
    getTasksPayload.setGroups(groups);
    getTasksPayload.setParentTaskId("42");
    getTasksPayload.setProcessInstanceId("42");
    String actualAssigneeId = getTasksPayload.getAssigneeId();
    List<String> actualGroups = getTasksPayload.getGroups();
    getTasksPayload.getId();
    String actualParentTaskId = getTasksPayload.getParentTaskId();

    // Assert that nothing has changed
    assertEquals("42", actualAssigneeId);
    assertEquals("42", actualParentTaskId);
    assertEquals("42", getTasksPayload.getProcessInstanceId());
    assertTrue(actualGroups.isEmpty());
    assertSame(groups, actualGroups);
  }

  /**
   * Method under test: {@link GetTasksPayload#GetTasksPayload()}
   */
  @Test
  void testNewGetTasksPayload() {
    // Arrange and Act
    GetTasksPayload actualGetTasksPayload = new GetTasksPayload();

    // Assert
    assertNull(actualGetTasksPayload.getAssigneeId());
    assertNull(actualGetTasksPayload.getParentTaskId());
    assertNull(actualGetTasksPayload.getProcessInstanceId());
    assertNull(actualGetTasksPayload.getGroups());
    assertTrue(actualGetTasksPayload.isStandalone());
  }

  /**
   * Method under test:
   * {@link GetTasksPayload#GetTasksPayload(String, List, String, String)}
   */
  @Test
  void testNewGetTasksPayload2() {
    // Arrange
    ArrayList<String> groups = new ArrayList<>();

    // Act
    GetTasksPayload actualGetTasksPayload = new GetTasksPayload("42", groups, "42", "42");

    // Assert
    assertEquals("42", actualGetTasksPayload.getAssigneeId());
    assertEquals("42", actualGetTasksPayload.getParentTaskId());
    assertEquals("42", actualGetTasksPayload.getProcessInstanceId());
    assertFalse(actualGetTasksPayload.isStandalone());
    List<String> groups2 = actualGetTasksPayload.getGroups();
    assertTrue(groups2.isEmpty());
    assertSame(groups, groups2);
  }

  /**
   * Method under test:
   * {@link GetTasksPayload#GetTasksPayload(String, List, String, String)}
   */
  @Test
  void testNewGetTasksPayload3() {
    // Arrange
    ArrayList<String> groups = new ArrayList<>();
    groups.add("foo");

    // Act
    GetTasksPayload actualGetTasksPayload = new GetTasksPayload("42", groups, "42", "42");

    // Assert
    assertEquals("42", actualGetTasksPayload.getAssigneeId());
    assertEquals("42", actualGetTasksPayload.getParentTaskId());
    assertEquals("42", actualGetTasksPayload.getProcessInstanceId());
    List<String> groups2 = actualGetTasksPayload.getGroups();
    assertEquals(1, groups2.size());
    assertEquals("foo", groups2.get(0));
    assertFalse(actualGetTasksPayload.isStandalone());
    assertSame(groups, groups2);
  }

  /**
   * Method under test:
   * {@link GetTasksPayload#GetTasksPayload(String, List, String, String)}
   */
  @Test
  void testNewGetTasksPayload4() {
    // Arrange
    ArrayList<String> groups = new ArrayList<>();
    groups.add("42");
    groups.add("foo");

    // Act
    GetTasksPayload actualGetTasksPayload = new GetTasksPayload("42", groups, "42", "42");

    // Assert
    assertEquals("42", actualGetTasksPayload.getAssigneeId());
    assertEquals("42", actualGetTasksPayload.getParentTaskId());
    assertEquals("42", actualGetTasksPayload.getProcessInstanceId());
    assertFalse(actualGetTasksPayload.isStandalone());
    assertSame(groups, actualGetTasksPayload.getGroups());
  }
}
