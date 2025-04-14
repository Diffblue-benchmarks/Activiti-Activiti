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
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.api.task.model.payloads.DeleteTaskPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DeleteTaskPayloadBuilder.class})
@ExtendWith(SpringExtension.class)
class DeleteTaskPayloadBuilderDiffblueTest {
  @Autowired
  private DeleteTaskPayloadBuilder deleteTaskPayloadBuilder;

  /**
   * Test {@link DeleteTaskPayloadBuilder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link DeleteTaskPayloadBuilder#build()}
   *   <li>default or parameterless constructor of {@link DeleteTaskPayloadBuilder}
   *   <li>{@link DeleteTaskPayloadBuilder#withReason(String)}
   *   <li>{@link DeleteTaskPayloadBuilder#withTaskId(String)}
   * </ul>
   */
  @Test
  @DisplayName("Test build()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DeleteTaskPayloadBuilder.<init>()", "DeleteTaskPayload DeleteTaskPayloadBuilder.build()",
      "DeleteTaskPayloadBuilder DeleteTaskPayloadBuilder.withReason(String)",
      "DeleteTaskPayloadBuilder DeleteTaskPayloadBuilder.withTaskId(String)"})
  void testBuild() {
    // Arrange and Act
    DeleteTaskPayload actualBuildResult = (new DeleteTaskPayloadBuilder()).withReason("Just cause")
        .withTaskId("42")
        .build();

    // Assert
    assertEquals("42", actualBuildResult.getTaskId());
    assertEquals("Just cause", actualBuildResult.getReason());
    assertTrue(actualBuildResult.hasReason());
  }
}
