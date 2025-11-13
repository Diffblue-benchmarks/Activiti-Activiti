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
package org.activiti.api.process.model.builders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.api.process.model.payloads.CreateProcessInstancePayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CreateProcessPayloadBuilderDiffblueTest {
  /**
   * Test {@link CreateProcessPayloadBuilder#build()}.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link CreateProcessPayloadBuilder#build()}
   *   <li>default or parameterless constructor of {@link CreateProcessPayloadBuilder}
   *   <li>{@link CreateProcessPayloadBuilder#withBusinessKey(String)}
   *   <li>{@link CreateProcessPayloadBuilder#withName(String)}
   *   <li>{@link CreateProcessPayloadBuilder#withProcessDefinitionId(String)}
   *   <li>{@link CreateProcessPayloadBuilder#withProcessDefinitionKey(String)}
   * </ul>
   */
  @Test
  @DisplayName("Test build()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void CreateProcessPayloadBuilder.<init>()",
    "CreateProcessInstancePayload CreateProcessPayloadBuilder.build()",
    "CreateProcessPayloadBuilder CreateProcessPayloadBuilder.withBusinessKey(String)",
    "CreateProcessPayloadBuilder CreateProcessPayloadBuilder.withName(String)",
    "CreateProcessPayloadBuilder CreateProcessPayloadBuilder.withProcessDefinitionId(String)",
    "CreateProcessPayloadBuilder CreateProcessPayloadBuilder.withProcessDefinitionKey(String)"
  })
  void testBuild() {
    // Arrange and Act
    CreateProcessInstancePayload actualCreateProcessInstancePayload =
        new CreateProcessPayloadBuilder()
            .withBusinessKey("Business Key")
            .withName("Name")
            .withProcessDefinitionId("42")
            .withProcessDefinitionKey("Process Definition Key")
            .build();

    // Assert
    assertEquals("42", actualCreateProcessInstancePayload.getProcessDefinitionId());
    assertEquals("Business Key", actualCreateProcessInstancePayload.getBusinessKey());
    assertEquals("Name", actualCreateProcessInstancePayload.getName());
    assertEquals(
        "Process Definition Key", actualCreateProcessInstancePayload.getProcessDefinitionKey());
  }
}
