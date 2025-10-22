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
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.activiti.api.task.model.payloads.CompleteTaskPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CompleteTaskPayloadBuilderDiffblueTest {
  /**
   * Test {@link CompleteTaskPayloadBuilder#withVariable(String, Object)}.
   * <ul>
   *   <li>Given complete.</li>
   * </ul>
   * <p>
   * Method under test: {@link CompleteTaskPayloadBuilder#withVariable(String, Object)}
   */
  @Test
  @DisplayName("Test withVariable(String, Object); given complete")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"CompleteTaskPayloadBuilder CompleteTaskPayloadBuilder.withVariable(String, Object)"})
  void testWithVariable_givenComplete() {
    // Arrange
    CompleteTaskPayloadBuilder completeResult = TaskPayloadBuilder.complete();

    // Act and Assert
    assertSame(completeResult, completeResult.withVariable("Name", "Value"));
  }

  /**
   * Test {@link CompleteTaskPayloadBuilder#withVariable(String, Object)}.
   * <ul>
   *   <li>Given complete withVariables {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CompleteTaskPayloadBuilder#withVariable(String, Object)}
   */
  @Test
  @DisplayName("Test withVariable(String, Object); given complete withVariables HashMap()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"CompleteTaskPayloadBuilder CompleteTaskPayloadBuilder.withVariable(String, Object)"})
  void testWithVariable_givenCompleteWithVariablesHashMap() {
    // Arrange
    CompleteTaskPayloadBuilder completeResult = TaskPayloadBuilder.complete();
    completeResult.withVariables(new HashMap<>());

    // Act and Assert
    assertSame(completeResult, completeResult.withVariable("Name", "Value"));
  }

  /**
   * Test {@link CompleteTaskPayloadBuilder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link CompleteTaskPayloadBuilder#build()}
   *   <li>default or parameterless constructor of {@link CompleteTaskPayloadBuilder}
   *   <li>{@link CompleteTaskPayloadBuilder#withTaskId(String)}
   *   <li>{@link CompleteTaskPayloadBuilder#withVariables(Map)}
   * </ul>
   */
  @Test
  @DisplayName("Test build()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void CompleteTaskPayloadBuilder.<init>()",
      "CompleteTaskPayload CompleteTaskPayloadBuilder.build()",
      "CompleteTaskPayloadBuilder CompleteTaskPayloadBuilder.withTaskId(String)",
      "CompleteTaskPayloadBuilder CompleteTaskPayloadBuilder.withVariables(Map)"})
  void testBuild() {
    // Arrange
    CompleteTaskPayloadBuilder withVariableResult = (new CompleteTaskPayloadBuilder()).withTaskId("42")
        .withVariable("Name", "Value");
    HashMap<String, Object> variables = new HashMap<>();

    // Act
    CompleteTaskPayload actualBuildResult = withVariableResult.withVariables(variables).build();

    // Assert
    assertEquals("42", actualBuildResult.getTaskId());
    Map<String, Object> variables2 = actualBuildResult.getVariables();
    assertTrue(variables2.isEmpty());
    assertSame(variables, variables2);
  }
}
