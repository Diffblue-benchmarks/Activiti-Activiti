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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CreateTaskPayloadDiffblueTest {
  /**
   * Test {@link CreateTaskPayload#CreateTaskPayload()}.
   *
   * <p>Method under test: {@link CreateTaskPayload#CreateTaskPayload()}
   */
  @Test
  @DisplayName("Test new CreateTaskPayload()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CreateTaskPayload.<init>()"})
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
   * Test {@link CreateTaskPayload#CreateTaskPayload(String, String, Date, int, String, List, List,
   * String, String)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>Then return CandidateGroups is {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link CreateTaskPayload#CreateTaskPayload(String, String, Date, int,
   * String, List, List, String, String)}
   */
  @Test
  @DisplayName(
      "Test new CreateTaskPayload(String, String, Date, int, String, List, List, String, String); given '42'; then return CandidateGroups is ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void CreateTaskPayload.<init>(String, String, Date, int, String, List, List, String, String)"
  })
  void testNewCreateTaskPayload_given42_thenReturnCandidateGroupsIsArrayList() {
    // Arrange
    Date dueDate =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("42");
    candidateGroups.add("foo");

    // Act
    CreateTaskPayload actualCreateTaskPayload =
        new CreateTaskPayload(
            "Name",
            "The characteristics of someone or something",
            dueDate,
            1,
            "Assignee",
            candidateGroups,
            new ArrayList<>(),
            "42",
            "Form Key");

    // Assert
    assertEquals("42", actualCreateTaskPayload.getParentTaskId());
    assertEquals("Assignee", actualCreateTaskPayload.getAssignee());
    assertEquals("Form Key", actualCreateTaskPayload.getFormKey());
    assertEquals("Name", actualCreateTaskPayload.getName());
    assertEquals(
        "The characteristics of someone or something", actualCreateTaskPayload.getDescription());
    assertEquals(1, actualCreateTaskPayload.getPriority());
    assertTrue(actualCreateTaskPayload.getCandidateUsers().isEmpty());
    assertSame(candidateGroups, actualCreateTaskPayload.getCandidateGroups());
  }

  /**
   * Test {@link CreateTaskPayload#CreateTaskPayload(String, String, Date, int, String, List, List,
   * String, String)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>Then return CandidateUsers is {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link CreateTaskPayload#CreateTaskPayload(String, String, Date, int,
   * String, List, List, String, String)}
   */
  @Test
  @DisplayName(
      "Test new CreateTaskPayload(String, String, Date, int, String, List, List, String, String); given '42'; then return CandidateUsers is ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void CreateTaskPayload.<init>(String, String, Date, int, String, List, List, String, String)"
  })
  void testNewCreateTaskPayload_given42_thenReturnCandidateUsersIsArrayList() {
    // Arrange
    Date dueDate =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    ArrayList<String> candidateGroups = new ArrayList<>();

    ArrayList<String> candidateUsers = new ArrayList<>();
    candidateUsers.add("42");
    candidateUsers.add("foo");

    // Act
    CreateTaskPayload actualCreateTaskPayload =
        new CreateTaskPayload(
            "Name",
            "The characteristics of someone or something",
            dueDate,
            1,
            "Assignee",
            candidateGroups,
            candidateUsers,
            "42",
            "Form Key");

    // Assert
    assertSame(candidateUsers, actualCreateTaskPayload.getCandidateUsers());
  }

  /**
   * Test {@link CreateTaskPayload#CreateTaskPayload(String, String, Date, int, String, List, List,
   * String, String)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>Then return CandidateGroups is {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link CreateTaskPayload#CreateTaskPayload(String, String, Date, int,
   * String, List, List, String, String)}
   */
  @Test
  @DisplayName(
      "Test new CreateTaskPayload(String, String, Date, int, String, List, List, String, String); given 'foo'; then return CandidateGroups is ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void CreateTaskPayload.<init>(String, String, Date, int, String, List, List, String, String)"
  })
  void testNewCreateTaskPayload_givenFoo_thenReturnCandidateGroupsIsArrayList() {
    // Arrange
    Date dueDate =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("foo");

    // Act
    CreateTaskPayload actualCreateTaskPayload =
        new CreateTaskPayload(
            "Name",
            "The characteristics of someone or something",
            dueDate,
            1,
            "Assignee",
            candidateGroups,
            new ArrayList<>(),
            "42",
            "Form Key");

    // Assert
    assertEquals("42", actualCreateTaskPayload.getParentTaskId());
    assertEquals("Assignee", actualCreateTaskPayload.getAssignee());
    assertEquals("Form Key", actualCreateTaskPayload.getFormKey());
    assertEquals("Name", actualCreateTaskPayload.getName());
    assertEquals(
        "The characteristics of someone or something", actualCreateTaskPayload.getDescription());
    assertEquals(1, actualCreateTaskPayload.getPriority());
    assertTrue(actualCreateTaskPayload.getCandidateUsers().isEmpty());
    assertSame(candidateGroups, actualCreateTaskPayload.getCandidateGroups());
  }

  /**
   * Test {@link CreateTaskPayload#CreateTaskPayload(String, String, Date, int, String, List, List,
   * String, String)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>Then return CandidateUsers size is one.
   * </ul>
   *
   * <p>Method under test: {@link CreateTaskPayload#CreateTaskPayload(String, String, Date, int,
   * String, List, List, String, String)}
   */
  @Test
  @DisplayName(
      "Test new CreateTaskPayload(String, String, Date, int, String, List, List, String, String); given 'foo'; then return CandidateUsers size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void CreateTaskPayload.<init>(String, String, Date, int, String, List, List, String, String)"
  })
  void testNewCreateTaskPayload_givenFoo_thenReturnCandidateUsersSizeIsOne() {
    // Arrange
    Date dueDate =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    ArrayList<String> candidateGroups = new ArrayList<>();

    ArrayList<String> candidateUsers = new ArrayList<>();
    candidateUsers.add("foo");

    // Act
    CreateTaskPayload actualCreateTaskPayload =
        new CreateTaskPayload(
            "Name",
            "The characteristics of someone or something",
            dueDate,
            1,
            "Assignee",
            candidateGroups,
            candidateUsers,
            "42",
            "Form Key");

    // Assert
    List<String> candidateUsers2 = actualCreateTaskPayload.getCandidateUsers();
    assertEquals(1, candidateUsers2.size());
    assertEquals("foo", candidateUsers2.get(0));
    assertTrue(actualCreateTaskPayload.getCandidateGroups().isEmpty());
    assertSame(candidateUsers, candidateUsers2);
  }

  /**
   * Test {@link CreateTaskPayload#CreateTaskPayload(String, String, Date, int, String, List, List,
   * String, String)}.
   *
   * <ul>
   *   <li>Then return CandidateGroups Empty.
   * </ul>
   *
   * <p>Method under test: {@link CreateTaskPayload#CreateTaskPayload(String, String, Date, int,
   * String, List, List, String, String)}
   */
  @Test
  @DisplayName(
      "Test new CreateTaskPayload(String, String, Date, int, String, List, List, String, String); then return CandidateGroups Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void CreateTaskPayload.<init>(String, String, Date, int, String, List, List, String, String)"
  })
  void testNewCreateTaskPayload_thenReturnCandidateGroupsEmpty() {
    // Arrange
    Date dueDate =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    ArrayList<String> candidateGroups = new ArrayList<>();

    // Act
    CreateTaskPayload actualCreateTaskPayload =
        new CreateTaskPayload(
            "Name",
            "The characteristics of someone or something",
            dueDate,
            1,
            "Assignee",
            candidateGroups,
            new ArrayList<>(),
            "42",
            "Form Key");

    // Assert
    assertEquals("42", actualCreateTaskPayload.getParentTaskId());
    assertEquals("Assignee", actualCreateTaskPayload.getAssignee());
    assertEquals("Form Key", actualCreateTaskPayload.getFormKey());
    assertEquals("Name", actualCreateTaskPayload.getName());
    assertEquals(
        "The characteristics of someone or something", actualCreateTaskPayload.getDescription());
    assertEquals(1, actualCreateTaskPayload.getPriority());
    assertTrue(actualCreateTaskPayload.getCandidateGroups().isEmpty());
    assertTrue(actualCreateTaskPayload.getCandidateUsers().isEmpty());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
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
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String CreateTaskPayload.getAssignee()",
    "List CreateTaskPayload.getCandidateGroups()",
    "List CreateTaskPayload.getCandidateUsers()",
    "String CreateTaskPayload.getDescription()",
    "Date CreateTaskPayload.getDueDate()",
    "String CreateTaskPayload.getFormKey()",
    "String CreateTaskPayload.getId()",
    "String CreateTaskPayload.getName()",
    "String CreateTaskPayload.getParentTaskId()",
    "int CreateTaskPayload.getPriority()",
    "void CreateTaskPayload.setAssignee(String)",
    "void CreateTaskPayload.setCandidateGroups(List)",
    "void CreateTaskPayload.setCandidateUsers(List)",
    "void CreateTaskPayload.setDescription(String)",
    "void CreateTaskPayload.setDueDate(Date)",
    "void CreateTaskPayload.setFormKey(String)",
    "void CreateTaskPayload.setName(String)",
    "void CreateTaskPayload.setParentTaskId(String)",
    "void CreateTaskPayload.setPriority(int)"
  })
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
    Date dueDate =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
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

    // Assert
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
}
