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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.api.task.model.payloads.AssignTaskPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class AssignTaskPayloadBuilderDiffblueTest {
  /**
   * Test {@link AssignTaskPayloadBuilder#build()}.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link AssignTaskPayloadBuilder#build()}
   *   <li>default or parameterless constructor of {@link AssignTaskPayloadBuilder}
   *   <li>{@link AssignTaskPayloadBuilder#withAssignee(String)}
   *   <li>{@link AssignTaskPayloadBuilder#withTaskId(String)}
   * </ul>
   */
  @Test
  @DisplayName("Test build()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AssignTaskPayloadBuilder.<init>()",
    "AssignTaskPayload AssignTaskPayloadBuilder.build()",
    "AssignTaskPayloadBuilder AssignTaskPayloadBuilder.withAssignee(String)",
    "AssignTaskPayloadBuilder AssignTaskPayloadBuilder.withTaskId(String)"
  })
  void testBuild() {
    // Arrange and Act
    AssignTaskPayload actualAssignTaskPayload =
        new AssignTaskPayloadBuilder().withAssignee("Assignee").withTaskId("42").build();

    // Assert
    assertEquals("42", actualAssignTaskPayload.getTaskId());
    assertEquals("Assignee", actualAssignTaskPayload.getAssignee());
  }
}
