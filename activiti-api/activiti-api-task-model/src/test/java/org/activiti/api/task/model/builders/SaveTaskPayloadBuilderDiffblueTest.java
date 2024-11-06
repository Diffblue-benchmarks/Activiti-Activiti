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
import org.activiti.api.task.model.payloads.SaveTaskPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SaveTaskPayloadBuilderDiffblueTest {
  /**
   * Test {@link SaveTaskPayloadBuilder#withVariable(String, Object)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SaveTaskPayloadBuilder#withVariable(String, Object)}
   */
  @Test
  @DisplayName("Test withVariable(String, Object); given HashMap() computeIfPresent 'foo' and BiFunction")
  void testWithVariable_givenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("foo", mock(BiFunction.class));
    SaveTaskPayloadBuilder saveResult = TaskPayloadBuilder.save();
    saveResult.withVariables(variables);

    // Act and Assert
    assertSame(saveResult, saveResult.withVariable("Name", "Value"));
  }

  /**
   * Test {@link SaveTaskPayloadBuilder#withVariable(String, Object)}.
   * <ul>
   *   <li>Given save.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SaveTaskPayloadBuilder#withVariable(String, Object)}
   */
  @Test
  @DisplayName("Test withVariable(String, Object); given save")
  void testWithVariable_givenSave() {
    // Arrange
    SaveTaskPayloadBuilder saveResult = TaskPayloadBuilder.save();

    // Act and Assert
    assertSame(saveResult, saveResult.withVariable("Name", "Value"));
  }

  /**
   * Test {@link SaveTaskPayloadBuilder#withVariable(String, Object)}.
   * <ul>
   *   <li>Given save withVariables {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SaveTaskPayloadBuilder#withVariable(String, Object)}
   */
  @Test
  @DisplayName("Test withVariable(String, Object); given save withVariables HashMap()")
  void testWithVariable_givenSaveWithVariablesHashMap() {
    // Arrange
    SaveTaskPayloadBuilder saveResult = TaskPayloadBuilder.save();
    saveResult.withVariables(new HashMap<>());

    // Act and Assert
    assertSame(saveResult, saveResult.withVariable("Name", "Value"));
  }

  /**
   * Test {@link SaveTaskPayloadBuilder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SaveTaskPayloadBuilder#build()}
   *   <li>default or parameterless constructor of {@link SaveTaskPayloadBuilder}
   *   <li>{@link SaveTaskPayloadBuilder#withTaskId(String)}
   *   <li>{@link SaveTaskPayloadBuilder#withVariables(Map)}
   * </ul>
   */
  @Test
  @DisplayName("Test build()")
  void testBuild() {
    // Arrange
    SaveTaskPayloadBuilder withVariableResult = (new SaveTaskPayloadBuilder()).withTaskId("42")
        .withVariable("Name", "Value");
    HashMap<String, Object> variables = new HashMap<>();

    // Act
    SaveTaskPayload actualBuildResult = withVariableResult.withVariables(variables).build();

    // Assert
    assertEquals("42", actualBuildResult.getTaskId());
    Map<String, Object> variables2 = actualBuildResult.getVariables();
    assertTrue(variables2.isEmpty());
    assertSame(variables, variables2);
  }
}
