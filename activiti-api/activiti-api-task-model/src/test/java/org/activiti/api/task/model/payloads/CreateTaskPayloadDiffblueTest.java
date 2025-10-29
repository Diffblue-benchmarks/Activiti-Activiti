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
import static org.mockito.Mockito.mock;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;

class CreateTaskPayloadDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateTaskPayload#setAssignee(String)}
   *   <li>{@link CreateTaskPayload#setCandidateGroups(List)}
   *   <li>{@link CreateTaskPayload#setCandidateUsers(List)}
   *   <li>{@link CreateTaskPayload#setDescription(String)}
   *   <li>{@link CreateTaskPayload#setDueDate(Date)}
   *   <li>{@link CreateTaskPayload#setFormKey(String)}
   *   <li>{@link CreateTaskPayload#setName(String)}
   *   <li>{@link CreateTaskPayload#setParentTaskId(String)}
   *   <li>{@link CreateTaskPayload#setPriority(int)}
   *   <li>{@link CreateTaskPayload#getAssignee()}
   *   <li>{@link CreateTaskPayload#getCandidateGroups()}
   *   <li>{@link CreateTaskPayload#getCandidateUsers()}
   *   <li>{@link CreateTaskPayload#getDescription()}
   *   <li>{@link CreateTaskPayload#getDueDate()}
   *   <li>{@link CreateTaskPayload#getFormKey()}
   *   <li>{@link CreateTaskPayload#getId()}
   *   <li>{@link CreateTaskPayload#getName()}
   *   <li>{@link CreateTaskPayload#getParentTaskId()}
   *   <li>{@link CreateTaskPayload#getPriority()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    CreateTaskPayload createTaskPayload = new CreateTaskPayload();

    // Act
    createTaskPayload.setAssignee("Assignee");
    ArrayList<String> candidateGroups = new ArrayList<>();
    createTaskPayload.setCandidateGroups(candidateGroups);
    ArrayList<String> candidateUsers = new ArrayList<>();
    createTaskPayload.setCandidateUsers(candidateUsers);
    createTaskPayload.setDescription("The characteristics of someone or something");
    Date dueDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    createTaskPayload.setDueDate(dueDate);
    createTaskPayload.setFormKey("Form Key");
    createTaskPayload.setName("Name");
    createTaskPayload.setParentTaskId("42");
    createTaskPayload.setPriority(1);
    String actualAssignee = createTaskPayload.getAssignee();
    List<String> actualCandidateGroups = createTaskPayload.getCandidateGroups();
    List<String> actualCandidateUsers = createTaskPayload.getCandidateUsers();
    String actualDescription = createTaskPayload.getDescription();
    Date actualDueDate = createTaskPayload.getDueDate();
    String actualFormKey = createTaskPayload.getFormKey();
    createTaskPayload.getId();
    String actualName = createTaskPayload.getName();
    String actualParentTaskId = createTaskPayload.getParentTaskId();

    // Assert that nothing has changed
    assertEquals("42", actualParentTaskId);
    assertEquals("Assignee", actualAssignee);
    assertEquals("Form Key", actualFormKey);
    assertEquals("Name", actualName);
    assertEquals("The characteristics of someone or something", actualDescription);
    assertEquals(1, createTaskPayload.getPriority());
    assertTrue(actualCandidateGroups.isEmpty());
    assertTrue(actualCandidateUsers.isEmpty());
    assertSame(candidateGroups, actualCandidateGroups);
    assertSame(candidateUsers, actualCandidateUsers);
    assertSame(dueDate, actualDueDate);
  }

  /**
   * Method under test: {@link CreateTaskPayload#CreateTaskPayload()}
   */
  @Test
  void testNewCreateTaskPayload() {
    // Arrange and Act
    CreateTaskPayload actualCreateTaskPayload = new CreateTaskPayload();

    // Assert
    assertNull(actualCreateTaskPayload.getAssignee());
    assertNull(actualCreateTaskPayload.getDescription());
    assertNull(actualCreateTaskPayload.getFormKey());
    assertNull(actualCreateTaskPayload.getName());
    assertNull(actualCreateTaskPayload.getParentTaskId());
    assertNull(actualCreateTaskPayload.getDueDate());
    assertNull(actualCreateTaskPayload.getCandidateGroups());
    assertNull(actualCreateTaskPayload.getCandidateUsers());
    assertEquals(0, actualCreateTaskPayload.getPriority());
  }

  /**
   * Method under test:
   * {@link CreateTaskPayload#CreateTaskPayload(String, String, Date, int, String, List, List, String, String)}
   */
  @Test
  void testNewCreateTaskPayload2() {
    // Arrange
    Date dueDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    ArrayList<String> candidateGroups = new ArrayList<>();
    ArrayList<String> candidateUsers = new ArrayList<>();

    // Act
    CreateTaskPayload actualCreateTaskPayload = new CreateTaskPayload("Name",
        "The characteristics of someone or something", dueDate, 1, "Assignee", candidateGroups, candidateUsers, "42",
        "Form Key");

    // Assert
    assertEquals("42", actualCreateTaskPayload.getParentTaskId());
    assertEquals("Assignee", actualCreateTaskPayload.getAssignee());
    assertEquals("Form Key", actualCreateTaskPayload.getFormKey());
    assertEquals("Name", actualCreateTaskPayload.getName());
    assertEquals("The characteristics of someone or something", actualCreateTaskPayload.getDescription());
    assertEquals(1, actualCreateTaskPayload.getPriority());
    List<String> candidateGroups2 = actualCreateTaskPayload.getCandidateGroups();
    assertTrue(candidateGroups2.isEmpty());
    List<String> candidateUsers2 = actualCreateTaskPayload.getCandidateUsers();
    assertTrue(candidateUsers2.isEmpty());
    assertSame(candidateGroups, candidateGroups2);
    assertSame(candidateUsers, candidateUsers2);
    assertSame(dueDate, actualCreateTaskPayload.getDueDate());
  }

  /**
   * Method under test:
   * {@link CreateTaskPayload#CreateTaskPayload(String, String, java.util.Date, int, String, List, List, String, String)}
   */
  @Test
  void testNewCreateTaskPayload3() {
    // Arrange
    java.sql.Date dueDate = mock(java.sql.Date.class);
    ArrayList<String> candidateGroups = new ArrayList<>();
    ArrayList<String> candidateUsers = new ArrayList<>();

    // Act
    CreateTaskPayload actualCreateTaskPayload = new CreateTaskPayload("Name",
        "The characteristics of someone or something", dueDate, 1, "Assignee", candidateGroups, candidateUsers, "42",
        "Form Key");

    // Assert
    assertEquals("42", actualCreateTaskPayload.getParentTaskId());
    assertEquals("Assignee", actualCreateTaskPayload.getAssignee());
    assertEquals("Form Key", actualCreateTaskPayload.getFormKey());
    assertEquals("Name", actualCreateTaskPayload.getName());
    assertEquals("The characteristics of someone or something", actualCreateTaskPayload.getDescription());
    assertEquals(1, actualCreateTaskPayload.getPriority());
    List<String> candidateGroups2 = actualCreateTaskPayload.getCandidateGroups();
    assertTrue(candidateGroups2.isEmpty());
    List<String> candidateUsers2 = actualCreateTaskPayload.getCandidateUsers();
    assertTrue(candidateUsers2.isEmpty());
    assertSame(candidateGroups, candidateGroups2);
    assertSame(candidateUsers, candidateUsers2);
    assertSame(dueDate, actualCreateTaskPayload.getDueDate());
  }

  /**
   * Method under test:
   * {@link CreateTaskPayload#CreateTaskPayload(String, String, Date, int, String, List, List, String, String)}
   */
  @Test
  void testNewCreateTaskPayload4() {
    // Arrange
    Date dueDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("foo");
    ArrayList<String> candidateUsers = new ArrayList<>();

    // Act
    CreateTaskPayload actualCreateTaskPayload = new CreateTaskPayload("Name",
        "The characteristics of someone or something", dueDate, 1, "Assignee", candidateGroups, candidateUsers, "42",
        "Form Key");

    // Assert
    assertEquals("42", actualCreateTaskPayload.getParentTaskId());
    assertEquals("Assignee", actualCreateTaskPayload.getAssignee());
    assertEquals("Form Key", actualCreateTaskPayload.getFormKey());
    assertEquals("Name", actualCreateTaskPayload.getName());
    assertEquals("The characteristics of someone or something", actualCreateTaskPayload.getDescription());
    List<String> candidateGroups2 = actualCreateTaskPayload.getCandidateGroups();
    assertEquals(1, candidateGroups2.size());
    assertEquals("foo", candidateGroups2.get(0));
    assertEquals(1, actualCreateTaskPayload.getPriority());
    List<String> candidateUsers2 = actualCreateTaskPayload.getCandidateUsers();
    assertTrue(candidateUsers2.isEmpty());
    assertSame(candidateGroups, candidateGroups2);
    assertSame(candidateUsers, candidateUsers2);
    assertSame(dueDate, actualCreateTaskPayload.getDueDate());
  }

  /**
   * Method under test:
   * {@link CreateTaskPayload#CreateTaskPayload(String, String, Date, int, String, List, List, String, String)}
   */
  @Test
  void testNewCreateTaskPayload5() {
    // Arrange
    Date dueDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("42");
    candidateGroups.add("foo");
    ArrayList<String> candidateUsers = new ArrayList<>();

    // Act
    CreateTaskPayload actualCreateTaskPayload = new CreateTaskPayload("Name",
        "The characteristics of someone or something", dueDate, 1, "Assignee", candidateGroups, candidateUsers, "42",
        "Form Key");

    // Assert
    assertEquals("42", actualCreateTaskPayload.getParentTaskId());
    assertEquals("Assignee", actualCreateTaskPayload.getAssignee());
    assertEquals("Form Key", actualCreateTaskPayload.getFormKey());
    assertEquals("Name", actualCreateTaskPayload.getName());
    assertEquals("The characteristics of someone or something", actualCreateTaskPayload.getDescription());
    assertEquals(1, actualCreateTaskPayload.getPriority());
    List<String> candidateUsers2 = actualCreateTaskPayload.getCandidateUsers();
    assertTrue(candidateUsers2.isEmpty());
    assertSame(candidateGroups, actualCreateTaskPayload.getCandidateGroups());
    assertSame(candidateUsers, candidateUsers2);
    assertSame(dueDate, actualCreateTaskPayload.getDueDate());
  }

  /**
   * Method under test:
   * {@link CreateTaskPayload#CreateTaskPayload(String, String, Date, int, String, List, List, String, String)}
   */
  @Test
  void testNewCreateTaskPayload6() {
    // Arrange
    Date dueDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    ArrayList<String> candidateGroups = new ArrayList<>();

    ArrayList<String> candidateUsers = new ArrayList<>();
    candidateUsers.add("foo");

    // Act
    CreateTaskPayload actualCreateTaskPayload = new CreateTaskPayload("Name",
        "The characteristics of someone or something", dueDate, 1, "Assignee", candidateGroups, candidateUsers, "42",
        "Form Key");

    // Assert
    assertEquals("42", actualCreateTaskPayload.getParentTaskId());
    assertEquals("Assignee", actualCreateTaskPayload.getAssignee());
    assertEquals("Form Key", actualCreateTaskPayload.getFormKey());
    assertEquals("Name", actualCreateTaskPayload.getName());
    assertEquals("The characteristics of someone or something", actualCreateTaskPayload.getDescription());
    List<String> candidateUsers2 = actualCreateTaskPayload.getCandidateUsers();
    assertEquals(1, candidateUsers2.size());
    assertEquals("foo", candidateUsers2.get(0));
    assertEquals(1, actualCreateTaskPayload.getPriority());
    List<String> candidateGroups2 = actualCreateTaskPayload.getCandidateGroups();
    assertTrue(candidateGroups2.isEmpty());
    assertSame(candidateGroups, candidateGroups2);
    assertSame(candidateUsers, candidateUsers2);
    assertSame(dueDate, actualCreateTaskPayload.getDueDate());
  }

  /**
   * Method under test:
   * {@link CreateTaskPayload#CreateTaskPayload(String, String, Date, int, String, List, List, String, String)}
   */
  @Test
  void testNewCreateTaskPayload7() {
    // Arrange
    Date dueDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    ArrayList<String> candidateGroups = new ArrayList<>();

    ArrayList<String> candidateUsers = new ArrayList<>();
    candidateUsers.add("42");
    candidateUsers.add("foo");

    // Act
    CreateTaskPayload actualCreateTaskPayload = new CreateTaskPayload("Name",
        "The characteristics of someone or something", dueDate, 1, "Assignee", candidateGroups, candidateUsers, "42",
        "Form Key");

    // Assert
    assertEquals("42", actualCreateTaskPayload.getParentTaskId());
    assertEquals("Assignee", actualCreateTaskPayload.getAssignee());
    assertEquals("Form Key", actualCreateTaskPayload.getFormKey());
    assertEquals("Name", actualCreateTaskPayload.getName());
    assertEquals("The characteristics of someone or something", actualCreateTaskPayload.getDescription());
    assertEquals(1, actualCreateTaskPayload.getPriority());
    List<String> candidateGroups2 = actualCreateTaskPayload.getCandidateGroups();
    assertTrue(candidateGroups2.isEmpty());
    assertSame(candidateGroups, candidateGroups2);
    assertSame(candidateUsers, actualCreateTaskPayload.getCandidateUsers());
    assertSame(dueDate, actualCreateTaskPayload.getDueDate());
  }
}
