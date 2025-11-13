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
import java.util.ArrayList;
import java.util.List;
import org.activiti.api.task.model.payloads.AssignTasksPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class AssignTasksPayloadBuilderDiffblueTest {
  /**
   * Test {@link AssignTasksPayloadBuilder#withTaskIds(List)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link AssignTasksPayloadBuilder#withTaskIds(List)}
   */
  @Test
  @DisplayName("Test withTaskIds(List); given '42'; when ArrayList() add '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"AssignTasksPayloadBuilder AssignTasksPayloadBuilder.withTaskIds(List)"})
  void testWithTaskIds_given42_whenArrayListAdd42() {
    // Arrange
    AssignTasksPayloadBuilder assignMultipleResult = TaskPayloadBuilder.assignMultiple();

    ArrayList<String> taskIds = new ArrayList<>();
    taskIds.add("42");
    taskIds.add("foo");

    // Act
    AssignTasksPayloadBuilder actualWithTaskIdsResult = assignMultipleResult.withTaskIds(taskIds);

    // Assert
    assertSame(taskIds, assignMultipleResult.build().getTaskIds());
    assertSame(assignMultipleResult, actualWithTaskIdsResult);
  }

  /**
   * Test {@link AssignTasksPayloadBuilder#withTaskIds(List)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link AssignTasksPayloadBuilder#withTaskIds(List)}
   */
  @Test
  @DisplayName("Test withTaskIds(List); given 'foo'; when ArrayList() add 'foo'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"AssignTasksPayloadBuilder AssignTasksPayloadBuilder.withTaskIds(List)"})
  void testWithTaskIds_givenFoo_whenArrayListAddFoo() {
    // Arrange
    AssignTasksPayloadBuilder assignMultipleResult = TaskPayloadBuilder.assignMultiple();

    ArrayList<String> taskIds = new ArrayList<>();
    taskIds.add("foo");

    // Act
    AssignTasksPayloadBuilder actualWithTaskIdsResult = assignMultipleResult.withTaskIds(taskIds);

    // Assert
    assertSame(taskIds, assignMultipleResult.build().getTaskIds());
    assertSame(assignMultipleResult, actualWithTaskIdsResult);
  }

  /**
   * Test {@link AssignTasksPayloadBuilder#withTaskIds(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link AssignTasksPayloadBuilder#withTaskIds(List)}
   */
  @Test
  @DisplayName("Test withTaskIds(List); when ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"AssignTasksPayloadBuilder AssignTasksPayloadBuilder.withTaskIds(List)"})
  void testWithTaskIds_whenArrayList() {
    // Arrange
    AssignTasksPayloadBuilder assignMultipleResult = TaskPayloadBuilder.assignMultiple();
    ArrayList<String> taskIds = new ArrayList<>();

    // Act
    AssignTasksPayloadBuilder actualWithTaskIdsResult = assignMultipleResult.withTaskIds(taskIds);

    // Assert
    assertSame(taskIds, assignMultipleResult.build().getTaskIds());
    assertSame(assignMultipleResult, actualWithTaskIdsResult);
  }

  /**
   * Test {@link AssignTasksPayloadBuilder#withTaskId(String)}.
   *
   * <p>Method under test: {@link AssignTasksPayloadBuilder#withTaskId(String)}
   */
  @Test
  @DisplayName("Test withTaskId(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"AssignTasksPayloadBuilder AssignTasksPayloadBuilder.withTaskId(String)"})
  void testWithTaskId() {
    // Arrange
    AssignTasksPayloadBuilder assignMultipleResult = TaskPayloadBuilder.assignMultiple();

    // Act
    AssignTasksPayloadBuilder actualWithTaskIdResult = assignMultipleResult.withTaskId("42");

    // Assert
    assertSame(assignMultipleResult, actualWithTaskIdResult);
  }

  /**
   * Test {@link AssignTasksPayloadBuilder#build()}.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link AssignTasksPayloadBuilder#build()}
   *   <li>default or parameterless constructor of {@link AssignTasksPayloadBuilder}
   *   <li>{@link AssignTasksPayloadBuilder#withAssignee(String)}
   * </ul>
   */
  @Test
  @DisplayName("Test build()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AssignTasksPayloadBuilder.<init>()",
    "AssignTasksPayload AssignTasksPayloadBuilder.build()",
    "AssignTasksPayloadBuilder AssignTasksPayloadBuilder.withAssignee(String)"
  })
  void testBuild() {
    // Arrange and Act
    AssignTasksPayloadBuilder actualWithAssigneeResult =
        new AssignTasksPayloadBuilder().withAssignee("Assignee");
    ArrayList<String> taskIds = new ArrayList<>();
    AssignTasksPayload actualAssignTasksPayload =
        actualWithAssigneeResult.withTaskIds(taskIds).build();

    // Assert
    assertEquals("Assignee", actualAssignTasksPayload.getAssignee());
    List<String> taskIds2 = actualAssignTasksPayload.getTaskIds();
    assertTrue(taskIds2.isEmpty());
    assertSame(taskIds, taskIds2);
  }
}
