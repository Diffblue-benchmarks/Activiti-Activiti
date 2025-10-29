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
import static org.mockito.Mockito.mock;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.activiti.api.task.model.payloads.CreateTaskPayload;
import org.junit.jupiter.api.Test;

class CreateTaskPayloadBuilderDiffblueTest {
  /**
   * Method under test: {@link CreateTaskPayloadBuilder#withCandidateGroups(List)}
   */
  @Test
  void testWithCandidateGroups() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();
    ArrayList<String> candidateGroups = new ArrayList<>();

    // Act
    CreateTaskPayloadBuilder actualWithCandidateGroupsResult = createResult.withCandidateGroups(candidateGroups);

    // Assert
    assertSame(candidateGroups, createResult.build().getCandidateGroups());
    assertSame(createResult, actualWithCandidateGroupsResult);
  }

  /**
   * Method under test: {@link CreateTaskPayloadBuilder#withCandidateGroups(List)}
   */
  @Test
  void testWithCandidateGroups2() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();

    // Act and Assert
    assertSame(createResult, createResult.withCandidateGroups(null));
  }

  /**
   * Method under test: {@link CreateTaskPayloadBuilder#withCandidateGroups(List)}
   */
  @Test
  void testWithCandidateGroups3() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("foo");

    // Act
    CreateTaskPayloadBuilder actualWithCandidateGroupsResult = createResult.withCandidateGroups(candidateGroups);

    // Assert
    assertSame(candidateGroups, createResult.build().getCandidateGroups());
    assertSame(createResult, actualWithCandidateGroupsResult);
  }

  /**
   * Method under test: {@link CreateTaskPayloadBuilder#withCandidateGroups(List)}
   */
  @Test
  void testWithCandidateGroups4() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("42");
    candidateGroups.add("foo");

    // Act
    CreateTaskPayloadBuilder actualWithCandidateGroupsResult = createResult.withCandidateGroups(candidateGroups);

    // Assert
    assertSame(candidateGroups, createResult.build().getCandidateGroups());
    assertSame(createResult, actualWithCandidateGroupsResult);
  }

  /**
   * Method under test: {@link CreateTaskPayloadBuilder#withCandidateGroups(List)}
   */
  @Test
  void testWithCandidateGroups5() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();
    createResult.withDueDate(mock(java.sql.Date.class));
    ArrayList<String> candidateGroups = new ArrayList<>();

    // Act
    CreateTaskPayloadBuilder actualWithCandidateGroupsResult = createResult.withCandidateGroups(candidateGroups);

    // Assert
    assertSame(candidateGroups, createResult.build().getCandidateGroups());
    assertSame(createResult, actualWithCandidateGroupsResult);
  }

  /**
   * Method under test:
   * {@link CreateTaskPayloadBuilder#withCandidateGroup(String)}
   */
  @Test
  void testWithCandidateGroup() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();

    // Act and Assert
    assertSame(createResult, createResult.withCandidateGroup("2020-03-01"));
  }

  /**
   * Method under test:
   * {@link CreateTaskPayloadBuilder#withCandidateGroup(String)}
   */
  @Test
  void testWithCandidateGroup2() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();
    createResult.withDueDate(mock(java.sql.Date.class));

    // Act and Assert
    assertSame(createResult, createResult.withCandidateGroup("2020-03-01"));
  }

  /**
   * Method under test:
   * {@link CreateTaskPayloadBuilder#withCandidateUsers(String)}
   */
  @Test
  void testWithCandidateUsers() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();

    // Act and Assert
    assertSame(createResult, createResult.withCandidateUsers("2020-03-01"));
  }

  /**
   * Method under test:
   * {@link CreateTaskPayloadBuilder#withCandidateUsers(String)}
   */
  @Test
  void testWithCandidateUsers2() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();
    createResult.withDueDate(mock(java.sql.Date.class));

    // Act and Assert
    assertSame(createResult, createResult.withCandidateUsers("2020-03-01"));
  }

  /**
   * Method under test: {@link CreateTaskPayloadBuilder#withCandidateUsers(List)}
   */
  @Test
  void testWithCandidateUsers3() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();
    ArrayList<String> candidateUsers = new ArrayList<>();

    // Act
    CreateTaskPayloadBuilder actualWithCandidateUsersResult = createResult.withCandidateUsers(candidateUsers);

    // Assert
    assertSame(candidateUsers, createResult.build().getCandidateUsers());
    assertSame(createResult, actualWithCandidateUsersResult);
  }

  /**
   * Method under test: {@link CreateTaskPayloadBuilder#withCandidateUsers(List)}
   */
  @Test
  void testWithCandidateUsers4() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();

    // Act and Assert
    assertSame(createResult, createResult.withCandidateUsers((List<String>) null));
  }

  /**
   * Method under test: {@link CreateTaskPayloadBuilder#withCandidateUsers(List)}
   */
  @Test
  void testWithCandidateUsers5() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();

    ArrayList<String> candidateUsers = new ArrayList<>();
    candidateUsers.add("foo");

    // Act
    CreateTaskPayloadBuilder actualWithCandidateUsersResult = createResult.withCandidateUsers(candidateUsers);

    // Assert
    assertSame(candidateUsers, createResult.build().getCandidateUsers());
    assertSame(createResult, actualWithCandidateUsersResult);
  }

  /**
   * Method under test: {@link CreateTaskPayloadBuilder#withCandidateUsers(List)}
   */
  @Test
  void testWithCandidateUsers6() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();

    ArrayList<String> candidateUsers = new ArrayList<>();
    candidateUsers.add("42");
    candidateUsers.add("foo");

    // Act
    CreateTaskPayloadBuilder actualWithCandidateUsersResult = createResult.withCandidateUsers(candidateUsers);

    // Assert
    assertSame(candidateUsers, createResult.build().getCandidateUsers());
    assertSame(createResult, actualWithCandidateUsersResult);
  }

  /**
   * Method under test: {@link CreateTaskPayloadBuilder#withCandidateUsers(List)}
   */
  @Test
  void testWithCandidateUsers7() {
    // Arrange
    CreateTaskPayloadBuilder createResult = TaskPayloadBuilder.create();
    createResult.withDueDate(mock(java.sql.Date.class));
    ArrayList<String> candidateUsers = new ArrayList<>();

    // Act
    CreateTaskPayloadBuilder actualWithCandidateUsersResult = createResult.withCandidateUsers(candidateUsers);

    // Assert
    assertSame(candidateUsers, createResult.build().getCandidateUsers());
    assertSame(createResult, actualWithCandidateUsersResult);
  }

  /**
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
