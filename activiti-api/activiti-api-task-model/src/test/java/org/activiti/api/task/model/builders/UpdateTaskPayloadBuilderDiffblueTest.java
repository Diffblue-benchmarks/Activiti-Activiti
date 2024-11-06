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
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.api.task.model.payloads.UpdateTaskPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UpdateTaskPayloadBuilderDiffblueTest {
  /**
   * Test {@link UpdateTaskPayloadBuilder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateTaskPayloadBuilder#build()}
   *   <li>default or parameterless constructor of {@link UpdateTaskPayloadBuilder}
   *   <li>{@link UpdateTaskPayloadBuilder#parentTaskId(String)}
   *   <li>{@link UpdateTaskPayloadBuilder#withAssignee(String)}
   *   <li>{@link UpdateTaskPayloadBuilder#withDescription(String)}
   *   <li>{@link UpdateTaskPayloadBuilder#withDueDate(Date)}
   *   <li>{@link UpdateTaskPayloadBuilder#withFormKey(String)}
   *   <li>{@link UpdateTaskPayloadBuilder#withName(String)}
   *   <li>{@link UpdateTaskPayloadBuilder#withPriority(Integer)}
   *   <li>{@link UpdateTaskPayloadBuilder#withTaskId(String)}
   * </ul>
   */
  @Test
  @DisplayName("Test build()")
  void testBuild() {
    // Arrange
    UpdateTaskPayloadBuilder withDescriptionResult = (new UpdateTaskPayloadBuilder()).parentTaskId("42")
        .withAssignee("Assignee")
        .withDescription("The characteristics of someone or something");
    Date dueDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    UpdateTaskPayload actualBuildResult = withDescriptionResult.withDueDate(dueDate)
        .withFormKey("Form Key")
        .withName("Name")
        .withPriority(1)
        .withTaskId("42")
        .build();

    // Assert
    assertEquals("42", actualBuildResult.getParentTaskId());
    assertEquals("42", actualBuildResult.getTaskId());
    assertEquals("Assignee", actualBuildResult.getAssignee());
    assertEquals("Form Key", actualBuildResult.getFormKey());
    assertEquals("Name", actualBuildResult.getName());
    assertEquals("The characteristics of someone or something", actualBuildResult.getDescription());
    assertEquals(1, actualBuildResult.getPriority().intValue());
    assertSame(dueDate, actualBuildResult.getDueDate());
  }
}
