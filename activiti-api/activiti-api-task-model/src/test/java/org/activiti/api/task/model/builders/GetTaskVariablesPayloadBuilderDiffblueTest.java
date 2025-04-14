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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {GetTaskVariablesPayloadBuilder.class})
@ExtendWith(SpringExtension.class)
class GetTaskVariablesPayloadBuilderDiffblueTest {
  @Autowired
  private GetTaskVariablesPayloadBuilder getTaskVariablesPayloadBuilder;

  /**
   * Test {@link GetTaskVariablesPayloadBuilder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetTaskVariablesPayloadBuilder#build()}
   *   <li>default or parameterless constructor of {@link GetTaskVariablesPayloadBuilder}
   *   <li>{@link GetTaskVariablesPayloadBuilder#withTaskId(String)}
   * </ul>
   */
  @Test
  @DisplayName("Test build()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void GetTaskVariablesPayloadBuilder.<init>()",
      "org.activiti.api.task.model.payloads.GetTaskVariablesPayload GetTaskVariablesPayloadBuilder.build()",
      "GetTaskVariablesPayloadBuilder GetTaskVariablesPayloadBuilder.withTaskId(String)"})
  void testBuild() {
    // Arrange, Act and Assert
    assertEquals("42", (new GetTaskVariablesPayloadBuilder()).withTaskId("42").build().getTaskId());
  }
}
