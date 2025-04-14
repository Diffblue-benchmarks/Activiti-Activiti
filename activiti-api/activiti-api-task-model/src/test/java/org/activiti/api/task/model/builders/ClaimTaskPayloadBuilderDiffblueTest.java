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
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.api.task.model.payloads.ClaimTaskPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ClaimTaskPayloadBuilder.class})
@ExtendWith(SpringExtension.class)
class ClaimTaskPayloadBuilderDiffblueTest {
  @Autowired
  private ClaimTaskPayloadBuilder claimTaskPayloadBuilder;

  /**
   * Test {@link ClaimTaskPayloadBuilder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ClaimTaskPayloadBuilder#build()}
   *   <li>default or parameterless constructor of {@link ClaimTaskPayloadBuilder}
   *   <li>{@link ClaimTaskPayloadBuilder#withAssignee(String)}
   *   <li>{@link ClaimTaskPayloadBuilder#withTaskId(String)}
   * </ul>
   */
  @Test
  @DisplayName("Test build()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ClaimTaskPayloadBuilder.<init>()", "ClaimTaskPayload ClaimTaskPayloadBuilder.build()",
      "ClaimTaskPayloadBuilder ClaimTaskPayloadBuilder.withAssignee(String)",
      "ClaimTaskPayloadBuilder ClaimTaskPayloadBuilder.withTaskId(String)"})
  void testBuild() {
    // Arrange and Act
    ClaimTaskPayload actualBuildResult = (new ClaimTaskPayloadBuilder()).withAssignee("Assignee")
        .withTaskId("42")
        .build();

    // Assert
    assertEquals("42", actualBuildResult.getTaskId());
    assertEquals("Assignee", actualBuildResult.getAssignee());
  }
}
