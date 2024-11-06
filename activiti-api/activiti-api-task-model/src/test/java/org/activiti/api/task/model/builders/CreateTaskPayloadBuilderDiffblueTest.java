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
package org.activiti.api.task.model.builders;

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
import org.activiti.api.task.model.payloads.CreateTaskPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreateTaskPayloadBuilderDiffblueTest {
  /**
   * Test {@link CreateTaskPayloadBuilder#withCandidateGroups(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateTaskPayloadBuilder#withCandidateGroups(List)}
   */
  @Test
  @DisplayName("Test withCandidateGroups(List); given '42'; when ArrayList() add '42'")
  void testWithCandidateGroups_given42_whenArrayListAdd42() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("42");
    candidateGroups.add("foo");

    // Act and Assert
    CreateTaskPayload buildResult = createResult.withCandidateGroups(candidateGroups).build();
    assertNull(buildResult.getDueDate());
    assertSame(candidateGroups, buildResult.getCandidateGroups());
    assertSame(candidateGroups, createResult.build().getCandidateGroups());
  }

  /**
   * Test {@link CreateTaskPayloadBuilder#withCandidateGroups(List)}.
   * <ul>
   *   <li>Given create withDueDate {@link Date}.</li>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateTaskPayloadBuilder#withCandidateGroups(List)}
   */
  @Test
  @DisplayName("Test withCandidateGroups(List); given create withDueDate Date; when ArrayList()")
  void testWithCandidateGroups_givenCreateWithDueDateDate_whenArrayList() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();
    createResult.withDueDate(mock(java.sql.Date.class));
    ArrayList<String> candidateGroups = new ArrayList<>();

    // Act and Assert
    assertTrue(createResult.withCandidateGroups(candidateGroups).build().getCandidateGroups().isEmpty());
    assertSame(candidateGroups, createResult.build().getCandidateGroups());
  }

  /**
   * Test {@link CreateTaskPayloadBuilder#withCandidateGroups(List)}.
   * <ul>
   *   <li>Given create.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then return build CandidateGroups Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateTaskPayloadBuilder#withCandidateGroups(List)}
   */
  @Test
  @DisplayName("Test withCandidateGroups(List); given create; when 'null'; then return build CandidateGroups Empty")
  void testWithCandidateGroups_givenCreate_whenNull_thenReturnBuildCandidateGroupsEmpty() {
    // Arrange, Act and Assert
    CreateTaskPayload buildResult = TaskPayloadBuilder.create().withCandidateGroups(null).build();
    assertNull(buildResult.getDueDate());
    assertTrue(buildResult.getCandidateGroups().isEmpty());
  }

  /**
   * Test {@link CreateTaskPayloadBuilder#withCandidateGroups(List)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>Then return build CandidateGroups is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateTaskPayloadBuilder#withCandidateGroups(List)}
   */
  @Test
  @DisplayName("Test withCandidateGroups(List); given 'foo'; then return build CandidateGroups is ArrayList()")
  void testWithCandidateGroups_givenFoo_thenReturnBuildCandidateGroupsIsArrayList() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("foo");

    // Act and Assert
    CreateTaskPayload buildResult = createResult.withCandidateGroups(candidateGroups).build();
    assertNull(buildResult.getDueDate());
    assertSame(candidateGroups, buildResult.getCandidateGroups());
    assertSame(candidateGroups, createResult.build().getCandidateGroups());
  }

  /**
   * Test {@link CreateTaskPayloadBuilder#withCandidateGroups(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return build CandidateGroups Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateTaskPayloadBuilder#withCandidateGroups(List)}
   */
  @Test
  @DisplayName("Test withCandidateGroups(List); when ArrayList(); then return build CandidateGroups Empty")
  void testWithCandidateGroups_whenArrayList_thenReturnBuildCandidateGroupsEmpty() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();
    ArrayList<String> candidateGroups = new ArrayList<>();

    // Act and Assert
    CreateTaskPayload buildResult = createResult.withCandidateGroups(candidateGroups).build();
    assertNull(buildResult.getDueDate());
    assertTrue(buildResult.getCandidateGroups().isEmpty());
    assertSame(candidateGroups, createResult.build().getCandidateGroups());
  }

  /**
   * Test {@link CreateTaskPayloadBuilder#withCandidateGroup(String)}.
   * <ul>
   *   <li>Given create.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CreateTaskPayloadBuilder#withCandidateGroup(String)}
   */
  @Test
  @DisplayName("Test withCandidateGroup(String); given create")
  void testWithCandidateGroup_givenCreate() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();

    // Act and Assert
    assertSame(createResult, createResult.withCandidateGroup("2020-03-01"));
  }

  /**
   * Test {@link CreateTaskPayloadBuilder#withCandidateGroup(String)}.
   * <ul>
   *   <li>Given create withDueDate {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CreateTaskPayloadBuilder#withCandidateGroup(String)}
   */
  @Test
  @DisplayName("Test withCandidateGroup(String); given create withDueDate Date")
  void testWithCandidateGroup_givenCreateWithDueDateDate() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();
    createResult.withDueDate(mock(java.sql.Date.class));

    // Act and Assert
    assertSame(createResult, createResult.withCandidateGroup("2020-03-01"));
  }

  /**
   * Test {@link CreateTaskPayloadBuilder#withCandidateUsers(List)} with
   * {@code List}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateTaskPayloadBuilder#withCandidateUsers(List)}
   */
  @Test
  @DisplayName("Test withCandidateUsers(List) with 'List'; given '42'; when ArrayList() add '42'")
  void testWithCandidateUsersWithList_given42_whenArrayListAdd42() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();

    ArrayList<String> candidateUsers = new ArrayList<>();
    candidateUsers.add("42");
    candidateUsers.add("foo");

    // Act and Assert
    CreateTaskPayload buildResult = createResult.withCandidateUsers(candidateUsers).build();
    assertNull(buildResult.getDueDate());
    assertSame(candidateUsers, buildResult.getCandidateUsers());
    assertSame(candidateUsers, createResult.build().getCandidateUsers());
  }

  /**
   * Test {@link CreateTaskPayloadBuilder#withCandidateUsers(List)} with
   * {@code List}.
   * <ul>
   *   <li>Given create withDueDate {@link Date}.</li>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateTaskPayloadBuilder#withCandidateUsers(List)}
   */
  @Test
  @DisplayName("Test withCandidateUsers(List) with 'List'; given create withDueDate Date; when ArrayList()")
  void testWithCandidateUsersWithList_givenCreateWithDueDateDate_whenArrayList() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();
    createResult.withDueDate(mock(java.sql.Date.class));
    ArrayList<String> candidateUsers = new ArrayList<>();

    // Act and Assert
    assertTrue(createResult.withCandidateUsers(candidateUsers).build().getCandidateUsers().isEmpty());
    assertSame(candidateUsers, createResult.build().getCandidateUsers());
  }

  /**
   * Test {@link CreateTaskPayloadBuilder#withCandidateUsers(List)} with
   * {@code List}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>Then return build CandidateUsers is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateTaskPayloadBuilder#withCandidateUsers(List)}
   */
  @Test
  @DisplayName("Test withCandidateUsers(List) with 'List'; given 'foo'; then return build CandidateUsers is ArrayList()")
  void testWithCandidateUsersWithList_givenFoo_thenReturnBuildCandidateUsersIsArrayList() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();

    ArrayList<String> candidateUsers = new ArrayList<>();
    candidateUsers.add("foo");

    // Act and Assert
    CreateTaskPayload buildResult = createResult.withCandidateUsers(candidateUsers).build();
    assertNull(buildResult.getDueDate());
    assertSame(candidateUsers, buildResult.getCandidateUsers());
    assertSame(candidateUsers, createResult.build().getCandidateUsers());
  }

  /**
   * Test {@link CreateTaskPayloadBuilder#withCandidateUsers(List)} with
   * {@code List}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return build CandidateUsers Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateTaskPayloadBuilder#withCandidateUsers(List)}
   */
  @Test
  @DisplayName("Test withCandidateUsers(List) with 'List'; when ArrayList(); then return build CandidateUsers Empty")
  void testWithCandidateUsersWithList_whenArrayList_thenReturnBuildCandidateUsersEmpty() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();
    ArrayList<String> candidateUsers = new ArrayList<>();

    // Act and Assert
    CreateTaskPayload buildResult = createResult.withCandidateUsers(candidateUsers).build();
    assertNull(buildResult.getDueDate());
    assertTrue(buildResult.getCandidateUsers().isEmpty());
    assertSame(candidateUsers, createResult.build().getCandidateUsers());
  }

  /**
   * Test {@link CreateTaskPayloadBuilder#withCandidateUsers(List)} with
   * {@code List}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return build CandidateUsers Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateTaskPayloadBuilder#withCandidateUsers(List)}
   */
  @Test
  @DisplayName("Test withCandidateUsers(List) with 'List'; when 'null'; then return build CandidateUsers Empty")
  void testWithCandidateUsersWithList_whenNull_thenReturnBuildCandidateUsersEmpty() {
    // Arrange, Act and Assert
    CreateTaskPayload buildResult = TaskPayloadBuilder.create().withCandidateUsers((List<String>) null).build();
    assertNull(buildResult.getDueDate());
    assertTrue(buildResult.getCandidateUsers().isEmpty());
  }

  /**
   * Test {@link CreateTaskPayloadBuilder#withCandidateUsers(String)} with
   * {@code String}.
   * <ul>
   *   <li>Given create.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CreateTaskPayloadBuilder#withCandidateUsers(String)}
   */
  @Test
  @DisplayName("Test withCandidateUsers(String) with 'String'; given create")
  void testWithCandidateUsersWithString_givenCreate() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();

    // Act and Assert
    assertSame(createResult, createResult.withCandidateUsers("2020-03-01"));
  }

  /**
   * Test {@link CreateTaskPayloadBuilder#withCandidateUsers(String)} with
   * {@code String}.
   * <ul>
   *   <li>Given create withDueDate {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CreateTaskPayloadBuilder#withCandidateUsers(String)}
   */
  @Test
  @DisplayName("Test withCandidateUsers(String) with 'String'; given create withDueDate Date")
  void testWithCandidateUsersWithString_givenCreateWithDueDateDate() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();
    createResult.withDueDate(mock(java.sql.Date.class));

    // Act and Assert
    assertSame(createResult, createResult.withCandidateUsers("2020-03-01"));
  }

  /**
   * Test {@link CreateTaskPayloadBuilder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link CreateTaskPayloadBuilder#build()}
   *   <li>default or parameterless constructor of {@link CreateTaskPayloadBuilder}
   *   <li>{@link CreateTaskPayloadBuilder#withAssignee(String)}
   *   <li>{@link CreateTaskPayloadBuilder#withDescription(String)}
   *   <li>{@link CreateTaskPayloadBuilder#withDueDate(Date)}
   *   <li>{@link CreateTaskPayloadBuilder#withFormKey(String)}
   *   <li>{@link CreateTaskPayloadBuilder#withName(String)}
   *   <li>{@link CreateTaskPayloadBuilder#withParentTaskId(String)}
   *   <li>{@link CreateTaskPayloadBuilder#withPriority(int)}
   * </ul>
   */
  @Test
  @DisplayName("Test build()")
  void testBuild() {
    // Arrange
    CreateTaskPayloadBuilder withAssigneeResult = (new CreateTaskPayloadBuilder()).withAssignee("Assignee");
    ArrayList<String> candidateGroups = new ArrayList<>();
    CreateTaskPayloadBuilder withCandidateGroupsResult = withAssigneeResult.withCandidateGroups(candidateGroups);
    ArrayList<String> candidateUsers = new ArrayList<>();
    CreateTaskPayloadBuilder withDescriptionResult = withCandidateGroupsResult.withCandidateUsers(candidateUsers)
        .withDescription("The characteristics of someone or something");
    Date dueDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    CreateTaskPayload actualBuildResult = withDescriptionResult.withDueDate(dueDate)
        .withFormKey("Form Key")
        .withName("Name")
        .withParentTaskId("42")
        .withPriority(1)
        .build();

    // Assert
    assertEquals("42", actualBuildResult.getParentTaskId());
    assertEquals("Assignee", actualBuildResult.getAssignee());
    assertEquals("Form Key", actualBuildResult.getFormKey());
    assertEquals("Name", actualBuildResult.getName());
    assertEquals("The characteristics of someone or something", actualBuildResult.getDescription());
    assertEquals(1, actualBuildResult.getPriority());
    List<String> candidateGroups2 = actualBuildResult.getCandidateGroups();
    assertTrue(candidateGroups2.isEmpty());
    List<String> candidateUsers2 = actualBuildResult.getCandidateUsers();
    assertTrue(candidateUsers2.isEmpty());
    assertSame(candidateGroups, candidateGroups2);
    assertSame(candidateUsers, candidateUsers2);
    assertSame(dueDate, actualBuildResult.getDueDate());
  }
}
