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
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.activiti.api.task.model.payloads.CompleteTaskPayload;
import org.junit.jupiter.api.Test;

class CompleteTaskPayloadBuilderDiffblueTest {
  /**
   * Method under test:
   * {@link CompleteTaskPayloadBuilder#withVariable(String, Object)}
   */
  @Test
  void testWithVariable() {
    // Arrange
    CompleteTaskPayloadBuilder completeResult = TaskPayloadBuilder.complete();

    // Act and Assert
    assertSame(completeResult, completeResult.withVariable("Name", "Value"));
  }

  /**
   * Method under test:
   * {@link CompleteTaskPayloadBuilder#withVariable(String, Object)}
   */
  @Test
  void testWithVariable2() {
    // Arrange
    CompleteTaskPayloadBuilder completeResult = TaskPayloadBuilder.complete();
    completeResult.withVariables(new HashMap<>());

    // Act and Assert
    assertSame(completeResult, completeResult.withVariable("Name", "Value"));
  }

  /**
   * Method under test:
   * {@link CompleteTaskPayloadBuilder#withVariable(String, Object)}
   */
  @Test
  void testWithVariable3() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("foo", mock(BiFunction.class));
    CompleteTaskPayloadBuilder completeResult = TaskPayloadBuilder.complete();
    completeResult.withVariables(variables);

    // Act and Assert
    assertSame(completeResult, completeResult.withVariable("Name", "Value"));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CompleteTaskPayloadBuilder#build()}
   *   <li>default or parameterless constructor of
   * {@link CompleteTaskPayloadBuilder}
   *   <li>{@link CompleteTaskPayloadBuilder#withTaskId(String)}
   *   <li>{@link CompleteTaskPayloadBuilder#withVariables(Map)}
   * </ul>
   */
  @Test
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
