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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.activiti.api.process.model.payloads.StartProcessPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class StartProcessPayloadBuilderDiffblueTest {
  /**
   * Test {@link StartProcessPayloadBuilder#withVariable(String, Object)}.
   *
   * <ul>
   *   <li>Given start withVariables {@code null}.
   *   <li>When {@code Name}.
   *   <li>Then return start.
   * </ul>
   *
   * <p>Method under test: {@link StartProcessPayloadBuilder#withVariable(String, Object)}
   */
  @Test
  @DisplayName(
      "Test withVariable(String, Object); given start withVariables 'null'; when 'Name'; then return start")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "StartProcessPayloadBuilder StartProcessPayloadBuilder.withVariable(String, Object)"
  })
  void testWithVariable_givenStartWithVariablesNull_whenName_thenReturnStart() {
    // Arrange
    StartProcessPayloadBuilder startResult = ProcessPayloadBuilder.start();
    startResult.withVariables(null);

    // Act
    StartProcessPayloadBuilder actualWithVariableResult = startResult.withVariable("Name", "Value");

    // Assert
    assertSame(startResult, actualWithVariableResult);
  }

  /**
   * Test {@link StartProcessPayloadBuilder#build()}.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link StartProcessPayloadBuilder#build()}
   *   <li>default or parameterless constructor of {@link StartProcessPayloadBuilder}
   *   <li>{@link StartProcessPayloadBuilder#withBusinessKey(String)}
   *   <li>{@link StartProcessPayloadBuilder#withName(String)}
   *   <li>{@link StartProcessPayloadBuilder#withProcessDefinitionId(String)}
   *   <li>{@link StartProcessPayloadBuilder#withProcessDefinitionKey(String)}
   *   <li>{@link StartProcessPayloadBuilder#withVariables(Map)}
   * </ul>
   */
  @Test
  @DisplayName("Test build()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void StartProcessPayloadBuilder.<init>()",
    "StartProcessPayload StartProcessPayloadBuilder.build()",
    "StartProcessPayloadBuilder StartProcessPayloadBuilder.withBusinessKey(String)",
    "StartProcessPayloadBuilder StartProcessPayloadBuilder.withName(String)",
    "StartProcessPayloadBuilder StartProcessPayloadBuilder.withProcessDefinitionId(String)",
    "StartProcessPayloadBuilder StartProcessPayloadBuilder.withProcessDefinitionKey(String)",
    "StartProcessPayloadBuilder StartProcessPayloadBuilder.withVariables(Map)"
  })
  void testBuild() {
    // Arrange and Act
    StartProcessPayloadBuilder actualWithVariableResult =
        new StartProcessPayloadBuilder()
            .withBusinessKey("Business Key")
            .withName("Name")
            .withProcessDefinitionId("42")
            .withProcessDefinitionKey("Process Definition Key")
            .withVariable("Name", "Value");
    HashMap<String, Object> variables = new HashMap<>();
    StartProcessPayload actualStartProcessPayload =
        actualWithVariableResult.withVariables(variables).build();

    // Assert
    assertEquals("42", actualStartProcessPayload.getProcessDefinitionId());
    assertEquals("Business Key", actualStartProcessPayload.getBusinessKey());
    assertEquals("Name", actualStartProcessPayload.getName());
    assertEquals("Process Definition Key", actualStartProcessPayload.getProcessDefinitionKey());
    Map<String, Object> variables2 = actualStartProcessPayload.getVariables();
    assertTrue(variables2.isEmpty());
    assertSame(variables, variables2);
  }
}
