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
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.activiti.api.process.model.payloads.SignalPayload;
import org.junit.jupiter.api.Test;

class SignalPayloadBuilderDiffblueTest {
  /**
   * Method under test: {@link SignalPayloadBuilder#withVariable(String, Object)}
   */
  @Test
  void testWithVariable() {
    // Arrange
    SignalPayloadBuilder signalResult = ProcessPayloadBuilder.signal();

    // Act and Assert
    assertSame(signalResult, signalResult.withVariable("Name", "Value"));
  }

  /**
   * Method under test: {@link SignalPayloadBuilder#withVariable(String, Object)}
   */
  @Test
  void testWithVariable2() {
    // Arrange
    SignalPayloadBuilder signalResult = ProcessPayloadBuilder.signal();
    signalResult.withVariables(new HashMap<>());

    // Act and Assert
    assertSame(signalResult, signalResult.withVariable("Name", "Value"));
  }

  /**
   * Method under test: {@link SignalPayloadBuilder#withVariable(String, Object)}
   */
  @Test
  void testWithVariable3() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("foo", mock(BiFunction.class));
    SignalPayloadBuilder signalResult = ProcessPayloadBuilder.signal();
    signalResult.withVariables(variables);

    // Act and Assert
    assertSame(signalResult, signalResult.withVariable("Name", "Value"));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SignalPayloadBuilder#build()}
   *   <li>default or parameterless constructor of {@link SignalPayloadBuilder}
   *   <li>{@link SignalPayloadBuilder#withName(String)}
   *   <li>{@link SignalPayloadBuilder#withVariables(Map)}
   * </ul>
   */
  @Test
  void testBuild() {
    // Arrange
    SignalPayloadBuilder withVariableResult = (new SignalPayloadBuilder()).withName("Name")
        .withVariable("Name", "Value");
    HashMap<String, Object> variables = new HashMap<>();

    // Act
    SignalPayload actualBuildResult = withVariableResult.withVariables(variables).build();

    // Assert
    assertEquals("Name", actualBuildResult.getName());
    Map<String, Object> variables2 = actualBuildResult.getVariables();
    assertTrue(variables2.isEmpty());
    assertSame(variables, variables2);
  }
}
