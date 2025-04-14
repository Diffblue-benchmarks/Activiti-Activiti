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
   * <ul>
   *   <li>Given start.</li>
   * </ul>
   * <p>
   * Method under test: {@link StartProcessPayloadBuilder#withVariable(String, Object)}
   */
  @Test
  @DisplayName("Test withVariable(String, Object); given start")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"StartProcessPayloadBuilder StartProcessPayloadBuilder.withVariable(String, Object)"})
  void testWithVariable_givenStart() {
    // Arrange
    StartProcessPayloadBuilder startResult = ProcessPayloadBuilder.start();

    // Act and Assert
    assertSame(startResult, startResult.withVariable("Name", "Value"));
  }

  /**
   * Test {@link StartProcessPayloadBuilder#withVariable(String, Object)}.
   * <ul>
   *   <li>Given start withVariables {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StartProcessPayloadBuilder#withVariable(String, Object)}
   */
  @Test
  @DisplayName("Test withVariable(String, Object); given start withVariables 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"StartProcessPayloadBuilder StartProcessPayloadBuilder.withVariable(String, Object)"})
  void testWithVariable_givenStartWithVariablesNull() {
    // Arrange
    StartProcessPayloadBuilder startResult = ProcessPayloadBuilder.start();
    startResult.withVariables(null);

    // Act and Assert
    assertSame(startResult, startResult.withVariable("Name", "Value"));
  }

  /**
   * Test {@link StartProcessPayloadBuilder#build()}.
   * <p>
   * Methods under test:
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
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void StartProcessPayloadBuilder.<init>()",
      "StartProcessPayload StartProcessPayloadBuilder.build()",
      "StartProcessPayloadBuilder StartProcessPayloadBuilder.withBusinessKey(String)",
      "StartProcessPayloadBuilder StartProcessPayloadBuilder.withName(String)",
      "StartProcessPayloadBuilder StartProcessPayloadBuilder.withProcessDefinitionId(String)",
      "StartProcessPayloadBuilder StartProcessPayloadBuilder.withProcessDefinitionKey(String)",
      "StartProcessPayloadBuilder StartProcessPayloadBuilder.withVariables(Map)"})
  void testBuild() {
    // Arrange
    StartProcessPayloadBuilder withVariableResult = (new StartProcessPayloadBuilder()).withBusinessKey("Business Key")
        .withName("Name")
        .withProcessDefinitionId("42")
        .withProcessDefinitionKey("Process Definition Key")
        .withVariable("Name", "Value");
    HashMap<String, Object> variables = new HashMap<>();

    // Act
    StartProcessPayload actualBuildResult = withVariableResult.withVariables(variables).build();

    // Assert
    assertEquals("42", actualBuildResult.getProcessDefinitionId());
    assertEquals("Business Key", actualBuildResult.getBusinessKey());
    assertEquals("Name", actualBuildResult.getName());
    assertEquals("Process Definition Key", actualBuildResult.getProcessDefinitionKey());
    Map<String, Object> variables2 = actualBuildResult.getVariables();
    assertTrue(variables2.isEmpty());
    assertSame(variables, variables2);
  }
}
