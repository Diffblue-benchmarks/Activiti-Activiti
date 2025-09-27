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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.activiti.api.task.model.payloads.CreateTaskPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CreateTaskPayloadBuilderDiffblueTest {
  /**
   * Test {@link CreateTaskPayloadBuilder#withCandidateGroups(List)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link CreateTaskPayloadBuilder#withCandidateGroups(List)}
   */
  @Test
  @DisplayName("Test withCandidateGroups(List); given '42'; when ArrayList() add '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CreateTaskPayloadBuilder CreateTaskPayloadBuilder.withCandidateGroups(List)"})
  void testWithCandidateGroups_given42_whenArrayListAdd42() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("42");
    candidateGroups.add("foo");

    // Act
    CreateTaskPayloadBuilder actualWithCandidateGroupsResult =
        createResult.withCandidateGroups(candidateGroups);

    // Assert
    assertSame(candidateGroups, createResult.build().getCandidateGroups());
    assertSame(createResult, actualWithCandidateGroupsResult);
  }

  /**
   * Test {@link CreateTaskPayloadBuilder#withCandidateGroups(List)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link CreateTaskPayloadBuilder#withCandidateGroups(List)}
   */
  @Test
  @DisplayName("Test withCandidateGroups(List); given 'foo'; when ArrayList() add 'foo'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CreateTaskPayloadBuilder CreateTaskPayloadBuilder.withCandidateGroups(List)"})
  void testWithCandidateGroups_givenFoo_whenArrayListAddFoo() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("foo");

    // Act
    CreateTaskPayloadBuilder actualWithCandidateGroupsResult =
        createResult.withCandidateGroups(candidateGroups);

    // Assert
    assertSame(candidateGroups, createResult.build().getCandidateGroups());
    assertSame(createResult, actualWithCandidateGroupsResult);
  }

  /**
   * Test {@link CreateTaskPayloadBuilder#withCandidateGroups(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then create build CandidateGroups is {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link CreateTaskPayloadBuilder#withCandidateGroups(List)}
   */
  @Test
  @DisplayName(
      "Test withCandidateGroups(List); when ArrayList(); then create build CandidateGroups is ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CreateTaskPayloadBuilder CreateTaskPayloadBuilder.withCandidateGroups(List)"})
  void testWithCandidateGroups_whenArrayList_thenCreateBuildCandidateGroupsIsArrayList() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();
    ArrayList<String> candidateGroups = new ArrayList<>();

    // Act
    CreateTaskPayloadBuilder actualWithCandidateGroupsResult =
        createResult.withCandidateGroups(candidateGroups);

    // Assert
    assertSame(candidateGroups, createResult.build().getCandidateGroups());
    assertSame(createResult, actualWithCandidateGroupsResult);
  }

  /**
   * Test {@link CreateTaskPayloadBuilder#withCandidateGroups(List)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CreateTaskPayloadBuilder#withCandidateGroups(List)}
   */
  @Test
  @DisplayName("Test withCandidateGroups(List); when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CreateTaskPayloadBuilder CreateTaskPayloadBuilder.withCandidateGroups(List)"})
  void testWithCandidateGroups_whenNull() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();

    // Act
    CreateTaskPayloadBuilder actualWithCandidateGroupsResult =
        createResult.withCandidateGroups(null);

    // Assert
    assertSame(createResult, actualWithCandidateGroupsResult);
  }

  /**
   * Test {@link CreateTaskPayloadBuilder#withCandidateGroup(String)}.
   *
   * <p>Method under test: {@link CreateTaskPayloadBuilder#withCandidateGroup(String)}
   */
  @Test
  @DisplayName("Test withCandidateGroup(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CreateTaskPayloadBuilder CreateTaskPayloadBuilder.withCandidateGroup(String)"
  })
  void testWithCandidateGroup() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();

    // Act
    CreateTaskPayloadBuilder actualWithCandidateGroupResult =
        createResult.withCandidateGroup("2020-03-01");

    // Assert
    assertSame(createResult, actualWithCandidateGroupResult);
  }

  /**
   * Test {@link CreateTaskPayloadBuilder#withCandidateUsers(List)} with {@code List}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link CreateTaskPayloadBuilder#withCandidateUsers(List)}
   */
  @Test
  @DisplayName("Test withCandidateUsers(List) with 'List'; given '42'; when ArrayList() add '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CreateTaskPayloadBuilder CreateTaskPayloadBuilder.withCandidateUsers(List)"})
  void testWithCandidateUsersWithList_given42_whenArrayListAdd42() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();

    ArrayList<String> candidateUsers = new ArrayList<>();
    candidateUsers.add("42");
    candidateUsers.add("foo");

    // Act
    CreateTaskPayloadBuilder actualWithCandidateUsersResult =
        createResult.withCandidateUsers(candidateUsers);

    // Assert
    assertSame(candidateUsers, createResult.build().getCandidateUsers());
    assertSame(createResult, actualWithCandidateUsersResult);
  }

  /**
   * Test {@link CreateTaskPayloadBuilder#withCandidateUsers(List)} with {@code List}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link CreateTaskPayloadBuilder#withCandidateUsers(List)}
   */
  @Test
  @DisplayName("Test withCandidateUsers(List) with 'List'; given 'foo'; when ArrayList() add 'foo'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CreateTaskPayloadBuilder CreateTaskPayloadBuilder.withCandidateUsers(List)"})
  void testWithCandidateUsersWithList_givenFoo_whenArrayListAddFoo() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();

    ArrayList<String> candidateUsers = new ArrayList<>();
    candidateUsers.add("foo");

    // Act
    CreateTaskPayloadBuilder actualWithCandidateUsersResult =
        createResult.withCandidateUsers(candidateUsers);

    // Assert
    assertSame(candidateUsers, createResult.build().getCandidateUsers());
    assertSame(createResult, actualWithCandidateUsersResult);
  }

  /**
   * Test {@link CreateTaskPayloadBuilder#withCandidateUsers(List)} with {@code List}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link CreateTaskPayloadBuilder#withCandidateUsers(List)}
   */
  @Test
  @DisplayName("Test withCandidateUsers(List) with 'List'; when ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CreateTaskPayloadBuilder CreateTaskPayloadBuilder.withCandidateUsers(List)"})
  void testWithCandidateUsersWithList_whenArrayList() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();
    ArrayList<String> candidateUsers = new ArrayList<>();

    // Act
    CreateTaskPayloadBuilder actualWithCandidateUsersResult =
        createResult.withCandidateUsers(candidateUsers);

    // Assert
    assertSame(candidateUsers, createResult.build().getCandidateUsers());
    assertSame(createResult, actualWithCandidateUsersResult);
  }

  /**
   * Test {@link CreateTaskPayloadBuilder#withCandidateUsers(List)} with {@code List}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CreateTaskPayloadBuilder#withCandidateUsers(List)}
   */
  @Test
  @DisplayName("Test withCandidateUsers(List) with 'List'; when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"CreateTaskPayloadBuilder CreateTaskPayloadBuilder.withCandidateUsers(List)"})
  void testWithCandidateUsersWithList_whenNull() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();

    // Act
    CreateTaskPayloadBuilder actualWithCandidateUsersResult =
        createResult.withCandidateUsers((List<String>) null);

    // Assert
    assertSame(createResult, actualWithCandidateUsersResult);
  }

  /**
   * Test {@link CreateTaskPayloadBuilder#withCandidateUsers(String)} with {@code String}.
   *
   * <p>Method under test: {@link CreateTaskPayloadBuilder#withCandidateUsers(String)}
   */
  @Test
  @DisplayName("Test withCandidateUsers(String) with 'String'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "CreateTaskPayloadBuilder CreateTaskPayloadBuilder.withCandidateUsers(String)"
  })
  void testWithCandidateUsersWithString() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();

    // Act
    CreateTaskPayloadBuilder actualWithCandidateUsersResult =
        createResult.withCandidateUsers("2020-03-01");

    // Assert
    assertSame(createResult, actualWithCandidateUsersResult);
  }

  /**
   * Test {@link CreateTaskPayloadBuilder#build()}.
   *
   * <p>Methods under test:
   *
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
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void CreateTaskPayloadBuilder.<init>()",
    "CreateTaskPayload CreateTaskPayloadBuilder.build()",
    "CreateTaskPayloadBuilder CreateTaskPayloadBuilder.withAssignee(String)",
    "CreateTaskPayloadBuilder CreateTaskPayloadBuilder.withDescription(String)",
    "CreateTaskPayloadBuilder CreateTaskPayloadBuilder.withDueDate(Date)",
    "CreateTaskPayloadBuilder CreateTaskPayloadBuilder.withFormKey(String)",
    "CreateTaskPayloadBuilder CreateTaskPayloadBuilder.withName(String)",
    "CreateTaskPayloadBuilder CreateTaskPayloadBuilder.withParentTaskId(String)",
    "CreateTaskPayloadBuilder CreateTaskPayloadBuilder.withPriority(int)"
  })
  void testBuild() {
    // Arrange and Act
    CreateTaskPayloadBuilder actualWithAssigneeResult =
        new CreateTaskPayloadBuilder().withAssignee("Assignee");
    ArrayList<String> candidateGroups = new ArrayList<>();
    CreateTaskPayloadBuilder actualWithCandidateGroupsResult =
        actualWithAssigneeResult.withCandidateGroups(candidateGroups);
    ArrayList<String> candidateUsers = new ArrayList<>();
    CreateTaskPayloadBuilder actualWithDescriptionResult =
        actualWithCandidateGroupsResult
            .withCandidateUsers(candidateUsers)
            .withDescription("The characteristics of someone or something");
    Date dueDate =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    CreateTaskPayload actualCreateTaskPayload =
        actualWithDescriptionResult
            .withDueDate(dueDate)
            .withFormKey("Form Key")
            .withName("Name")
            .withParentTaskId("42")
            .withPriority(1)
            .build();

    // Assert
    assertEquals("42", actualCreateTaskPayload.getParentTaskId());
    assertEquals("Assignee", actualCreateTaskPayload.getAssignee());
    assertEquals("Form Key", actualCreateTaskPayload.getFormKey());
    assertEquals("Name", actualCreateTaskPayload.getName());
    assertEquals(
        "The characteristics of someone or something", actualCreateTaskPayload.getDescription());
    assertEquals(1, actualCreateTaskPayload.getPriority());
    List<String> candidateGroups2 = actualCreateTaskPayload.getCandidateGroups();
    assertTrue(candidateGroups2.isEmpty());
    List<String> candidateUsers2 = actualCreateTaskPayload.getCandidateUsers();
    assertTrue(candidateUsers2.isEmpty());
    assertSame(candidateGroups, candidateGroups2);
    assertSame(candidateUsers, candidateUsers2);
    assertSame(dueDate, actualCreateTaskPayload.getDueDate());
  }
}
