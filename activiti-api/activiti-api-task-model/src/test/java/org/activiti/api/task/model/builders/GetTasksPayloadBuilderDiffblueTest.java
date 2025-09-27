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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.activiti.api.task.model.payloads.GetTasksPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class GetTasksPayloadBuilderDiffblueTest {
  /**
   * Test {@link GetTasksPayloadBuilder#withGroup(String)}.
   *
   * <ul>
   *   <li>Given tasks.
   * </ul>
   *
   * <p>Method under test: {@link GetTasksPayloadBuilder#withGroup(String)}
   */
  @Test
  @DisplayName("Test withGroup(String); given tasks")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"GetTasksPayloadBuilder GetTasksPayloadBuilder.withGroup(String)"})
  void testWithGroup_givenTasks() {
    // Arrange
    GetTasksPayloadBuilder tasksResult = TaskPayloadBuilder.tasks();

    // Act
    GetTasksPayloadBuilder actualWithGroupResult = tasksResult.withGroup("Group");

    // Assert
    assertSame(tasksResult, actualWithGroupResult);
  }

  /**
   * Test {@link GetTasksPayloadBuilder#withGroup(String)}.
   *
   * <ul>
   *   <li>Given tasks withGroups {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link GetTasksPayloadBuilder#withGroup(String)}
   */
  @Test
  @DisplayName("Test withGroup(String); given tasks withGroups ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"GetTasksPayloadBuilder GetTasksPayloadBuilder.withGroup(String)"})
  void testWithGroup_givenTasksWithGroupsArrayList() {
    // Arrange
    GetTasksPayloadBuilder tasksResult = TaskPayloadBuilder.tasks();
    tasksResult.withGroups(new ArrayList<>());

    // Act
    GetTasksPayloadBuilder actualWithGroupResult = tasksResult.withGroup("Group");

    // Assert
    assertSame(tasksResult, actualWithGroupResult);
  }

  /**
   * Test {@link GetTasksPayloadBuilder#build()}.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link GetTasksPayloadBuilder#build()}
   *   <li>default or parameterless constructor of {@link GetTasksPayloadBuilder}
   *   <li>{@link GetTasksPayloadBuilder#withAssignee(String)}
   *   <li>{@link GetTasksPayloadBuilder#withGroups(List)}
   *   <li>{@link GetTasksPayloadBuilder#withParentTaskId(String)}
   *   <li>{@link GetTasksPayloadBuilder#withProcessInstanceId(String)}
   * </ul>
   */
  @Test
  @DisplayName("Test build()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void GetTasksPayloadBuilder.<init>()",
    "GetTasksPayload GetTasksPayloadBuilder.build()",
    "GetTasksPayloadBuilder GetTasksPayloadBuilder.withAssignee(String)",
    "GetTasksPayloadBuilder GetTasksPayloadBuilder.withGroups(List)",
    "GetTasksPayloadBuilder GetTasksPayloadBuilder.withParentTaskId(String)",
    "GetTasksPayloadBuilder GetTasksPayloadBuilder.withProcessInstanceId(String)"
  })
  void testBuild() {
    // Arrange and Act
    GetTasksPayloadBuilder actualWithGroupResult =
        new GetTasksPayloadBuilder().withAssignee("Assignee").withGroup("Group");
    ArrayList<String> groups = new ArrayList<>();
    GetTasksPayload actualGetTasksPayload =
        actualWithGroupResult
            .withGroups(groups)
            .withParentTaskId("42")
            .withProcessInstanceId("42")
            .build();

    // Assert
    assertEquals("42", actualGetTasksPayload.getParentTaskId());
    assertEquals("42", actualGetTasksPayload.getProcessInstanceId());
    assertEquals("Assignee", actualGetTasksPayload.getAssigneeId());
    assertFalse(actualGetTasksPayload.isStandalone());
    List<String> groups2 = actualGetTasksPayload.getGroups();
    assertTrue(groups2.isEmpty());
    assertSame(groups, groups2);
  }
}
