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

import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.api.task.model.payloads.UpdateTaskPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RemoveTaskVariablesPayloadBuilderDiffblueTest {
  /**
   * Test {@link RemoveTaskVariablesPayloadBuilder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link RemoveTaskVariablesPayloadBuilder#build()}
   *   <li>default or parameterless constructor of
   * {@link RemoveTaskVariablesPayloadBuilder}
   *   <li>{@link RemoveTaskVariablesPayloadBuilder#withAssignee(String)}
   *   <li>{@link RemoveTaskVariablesPayloadBuilder#withDescription(String)}
   *   <li>{@link RemoveTaskVariablesPayloadBuilder#withDueDate(Date)}
   *   <li>{@link RemoveTaskVariablesPayloadBuilder#withName(String)}
   *   <li>{@link RemoveTaskVariablesPayloadBuilder#withPriority(int)}
   *   <li>{@link RemoveTaskVariablesPayloadBuilder#withTaskId(String)}
   * </ul>
   */
  @Test
  @DisplayName("Test build()")
  void testBuild() {
    // Arrange
    RemoveTaskVariablesPayloadBuilder withDescriptionResult = (new RemoveTaskVariablesPayloadBuilder())
        .withAssignee("Assignee")
        .withDescription("The characteristics of someone or something");

    // Act
    UpdateTaskPayload actualBuildResult = withDescriptionResult
        .withDueDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()))
        .withName("Name")
        .withPriority(1)
        .withTaskId("42")
        .build();

    // Assert
    assertNull(actualBuildResult.getPriority());
    assertNull(actualBuildResult.getAssignee());
    assertNull(actualBuildResult.getDescription());
    assertNull(actualBuildResult.getFormKey());
    assertNull(actualBuildResult.getName());
    assertNull(actualBuildResult.getParentTaskId());
    assertNull(actualBuildResult.getTaskId());
    assertNull(actualBuildResult.getDueDate());
  }
}
