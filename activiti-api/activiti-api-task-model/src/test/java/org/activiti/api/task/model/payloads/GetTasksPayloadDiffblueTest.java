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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GetTasksPayloadDiffblueTest {
  /**
   * Test {@link GetTasksPayload#GetTasksPayload()}.
   * <p>
   * Method under test: {@link GetTasksPayload#GetTasksPayload()}
   */
  @Test
  @DisplayName("Test new GetTasksPayload()")
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
   * Test {@link GetTasksPayload#GetTasksPayload(String, List, String, String)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   *   <li>Then return Groups is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link GetTasksPayload#GetTasksPayload(String, List, String, String)}
   */
  @Test
  @DisplayName("Test new GetTasksPayload(String, List, String, String); given '42'; when ArrayList() add '42'; then return Groups is ArrayList()")
  void testNewGetTasksPayload_given42_whenArrayListAdd42_thenReturnGroupsIsArrayList() {
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

  /**
   * Test {@link GetTasksPayload#GetTasksPayload(String, List, String, String)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.</li>
   *   <li>Then return Groups is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link GetTasksPayload#GetTasksPayload(String, List, String, String)}
   */
  @Test
  @DisplayName("Test new GetTasksPayload(String, List, String, String); given 'foo'; when ArrayList() add 'foo'; then return Groups is ArrayList()")
  void testNewGetTasksPayload_givenFoo_whenArrayListAddFoo_thenReturnGroupsIsArrayList() {
    // Arrange
    ArrayList<String> groups = new ArrayList<>();
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

  /**
   * Test {@link GetTasksPayload#GetTasksPayload(String, List, String, String)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Groups Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link GetTasksPayload#GetTasksPayload(String, List, String, String)}
   */
  @Test
  @DisplayName("Test new GetTasksPayload(String, List, String, String); when ArrayList(); then return Groups Empty")
  void testNewGetTasksPayload_whenArrayList_thenReturnGroupsEmpty() {
    // Arrange and Act
    GetTasksPayload actualGetTasksPayload = new GetTasksPayload("42", new ArrayList<>(), "42", "42");

    // Assert
    assertEquals("42", actualGetTasksPayload.getAssigneeId());
    assertEquals("42", actualGetTasksPayload.getParentTaskId());
    assertEquals("42", actualGetTasksPayload.getProcessInstanceId());
    assertFalse(actualGetTasksPayload.isStandalone());
    assertTrue(actualGetTasksPayload.getGroups().isEmpty());
  }

  /**
   * Test getters and setters.
   * <p>
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
  @DisplayName("Test getters and setters")
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
   * Test {@link GetTasksPayload#isStandalone()}.
   * <ul>
   *   <li>Given {@link GetTasksPayload#GetTasksPayload()} ProcessInstanceId is
   * {@code foo}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetTasksPayload#isStandalone()}
   */
  @Test
  @DisplayName("Test isStandalone(); given GetTasksPayload() ProcessInstanceId is 'foo'; then return 'false'")
  void testIsStandalone_givenGetTasksPayloadProcessInstanceIdIsFoo_thenReturnFalse() {
    // Arrange
    GetTasksPayload getTasksPayload = new GetTasksPayload();
    getTasksPayload.setProcessInstanceId("foo");

    // Act and Assert
    assertFalse(getTasksPayload.isStandalone());
  }

  /**
   * Test {@link GetTasksPayload#isStandalone()}.
   * <ul>
   *   <li>Given {@link GetTasksPayload#GetTasksPayload()}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetTasksPayload#isStandalone()}
   */
  @Test
  @DisplayName("Test isStandalone(); given GetTasksPayload(); then return 'true'")
  void testIsStandalone_givenGetTasksPayload_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((new GetTasksPayload()).isStandalone());
  }
}
