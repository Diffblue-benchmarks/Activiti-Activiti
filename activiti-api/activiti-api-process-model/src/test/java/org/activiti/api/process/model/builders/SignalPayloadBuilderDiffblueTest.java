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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SignalPayloadBuilderDiffblueTest {
  /**
   * Test {@link SignalPayloadBuilder#withVariable(String, Object)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SignalPayloadBuilder#withVariable(String, Object)}
   */
  @Test
  @DisplayName("Test withVariable(String, Object); given HashMap() computeIfPresent 'foo' and BiFunction")
  void testWithVariable_givenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("foo", mock(BiFunction.class));
    SignalPayloadBuilder signalResult = ProcessPayloadBuilder.signal();
    signalResult.withVariables(variables);

    // Act and Assert
    assertSame(signalResult, signalResult.withVariable("Name", "Value"));
  }

  /**
   * Test {@link SignalPayloadBuilder#withVariable(String, Object)}.
   * <ul>
   *   <li>Given signal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SignalPayloadBuilder#withVariable(String, Object)}
   */
  @Test
  @DisplayName("Test withVariable(String, Object); given signal")
  void testWithVariable_givenSignal() {
    // Arrange
    SignalPayloadBuilder signalResult = ProcessPayloadBuilder.signal();

    // Act and Assert
    assertSame(signalResult, signalResult.withVariable("Name", "Value"));
  }

  /**
   * Test {@link SignalPayloadBuilder#withVariable(String, Object)}.
   * <ul>
   *   <li>Given signal withVariables {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SignalPayloadBuilder#withVariable(String, Object)}
   */
  @Test
  @DisplayName("Test withVariable(String, Object); given signal withVariables HashMap()")
  void testWithVariable_givenSignalWithVariablesHashMap() {
    // Arrange
    SignalPayloadBuilder signalResult = ProcessPayloadBuilder.signal();
    signalResult.withVariables(new HashMap<>());

    // Act and Assert
    assertSame(signalResult, signalResult.withVariable("Name", "Value"));
  }

  /**
   * Test {@link SignalPayloadBuilder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SignalPayloadBuilder#build()}
   *   <li>default or parameterless constructor of {@link SignalPayloadBuilder}
   *   <li>{@link SignalPayloadBuilder#withName(String)}
   *   <li>{@link SignalPayloadBuilder#withVariables(Map)}
   * </ul>
   */
  @Test
  @DisplayName("Test build()")
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
