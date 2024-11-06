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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.activiti.api.process.model.payloads.StartMessagePayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StartMessagePayloadBuilderDiffblueTest {
  /**
   * Test {@link StartMessagePayloadBuilder#from(StartMessagePayload)}.
   * <ul>
   *   <li>Given {@code messagePayload must not be null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartMessagePayloadBuilder#from(StartMessagePayload)}
   */
  @Test
  @DisplayName("Test from(StartMessagePayload); given 'messagePayload must not be null'")
  void testFrom_givenMessagePayloadMustNotBeNull() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("messagePayload must not be null", mock(BiFunction.class));

    // Act and Assert
    StartMessagePayload buildResult = StartMessagePayloadBuilder
        .from(new StartMessagePayload("messagePayload must not be null", "messagePayload must not be null", variables))
        .build();
    assertEquals("messagePayload must not be null", buildResult.getBusinessKey());
    assertEquals("messagePayload must not be null", buildResult.getName());
    assertTrue(buildResult.getVariables().isEmpty());
  }

  /**
   * Test {@link StartMessagePayloadBuilder#from(StartMessagePayload)}.
   * <ul>
   *   <li>Then return build BusinessKey is
   * {@code messagePayload must not be null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartMessagePayloadBuilder#from(StartMessagePayload)}
   */
  @Test
  @DisplayName("Test from(StartMessagePayload); then return build BusinessKey is 'messagePayload must not be null'")
  void testFrom_thenReturnBuildBusinessKeyIsMessagePayloadMustNotBeNull() {
    // Arrange, Act and Assert
    StartMessagePayload buildResult = StartMessagePayloadBuilder
        .from(new StartMessagePayload("messagePayload must not be null", "messagePayload must not be null",
            new HashMap<>()))
        .build();
    assertEquals("messagePayload must not be null", buildResult.getBusinessKey());
    assertEquals("messagePayload must not be null", buildResult.getName());
    assertTrue(buildResult.getVariables().isEmpty());
  }

  /**
   * Test {@link StartMessagePayloadBuilder#start(String)}.
   * <p>
   * Method under test: {@link StartMessagePayloadBuilder#start(String)}
   */
  @Test
  @DisplayName("Test start(String)")
  void testStart() {
    // Arrange, Act and Assert
    StartMessagePayload buildResult = StartMessagePayloadBuilder.start("Name").build();
    assertEquals("Name", buildResult.getName());
    assertNull(buildResult.getBusinessKey());
    assertNull(buildResult.getVariables());
  }

  /**
   * Test {@link StartMessagePayloadBuilder#withVariable(String, Object)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartMessagePayloadBuilder#withVariable(String, Object)}
   */
  @Test
  @DisplayName("Test withVariable(String, Object); given HashMap() computeIfPresent 'foo' and BiFunction")
  void testWithVariable_givenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("foo", mock(BiFunction.class));

    StartMessagePayloadBuilder startMessagePayloadBuilder = new StartMessagePayloadBuilder();
    startMessagePayloadBuilder.withVariables(variables);

    // Act and Assert
    assertSame(startMessagePayloadBuilder, startMessagePayloadBuilder.withVariable("Name", "Value"));
  }

  /**
   * Test {@link StartMessagePayloadBuilder#withVariable(String, Object)}.
   * <ul>
   *   <li>Given {@link StartMessagePayloadBuilder} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartMessagePayloadBuilder#withVariable(String, Object)}
   */
  @Test
  @DisplayName("Test withVariable(String, Object); given StartMessagePayloadBuilder (default constructor)")
  void testWithVariable_givenStartMessagePayloadBuilder() {
    // Arrange
    StartMessagePayloadBuilder startMessagePayloadBuilder = new StartMessagePayloadBuilder();

    // Act and Assert
    assertSame(startMessagePayloadBuilder, startMessagePayloadBuilder.withVariable("Name", "Value"));
  }

  /**
   * Test {@link StartMessagePayloadBuilder#withVariable(String, Object)}.
   * <ul>
   *   <li>Given {@link StartMessagePayloadBuilder} (default constructor)
   * withVariables {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartMessagePayloadBuilder#withVariable(String, Object)}
   */
  @Test
  @DisplayName("Test withVariable(String, Object); given StartMessagePayloadBuilder (default constructor) withVariables HashMap()")
  void testWithVariable_givenStartMessagePayloadBuilderWithVariablesHashMap() {
    // Arrange
    StartMessagePayloadBuilder startMessagePayloadBuilder = new StartMessagePayloadBuilder();
    startMessagePayloadBuilder.withVariables(new HashMap<>());

    // Act and Assert
    assertSame(startMessagePayloadBuilder, startMessagePayloadBuilder.withVariable("Name", "Value"));
  }

  /**
   * Test {@link StartMessagePayloadBuilder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link StartMessagePayloadBuilder#build()}
   *   <li>default or parameterless constructor of
   * {@link StartMessagePayloadBuilder}
   *   <li>{@link StartMessagePayloadBuilder#withBusinessKey(String)}
   *   <li>{@link StartMessagePayloadBuilder#withName(String)}
   *   <li>{@link StartMessagePayloadBuilder#withVariables(Map)}
   * </ul>
   */
  @Test
  @DisplayName("Test build()")
  void testBuild() {
    // Arrange
    StartMessagePayloadBuilder withVariableResult = (new StartMessagePayloadBuilder()).withBusinessKey("Business Key")
        .withName("Name")
        .withVariable("Name", "Value");
    HashMap<String, Object> variables = new HashMap<>();

    // Act
    StartMessagePayload actualBuildResult = withVariableResult.withVariables(variables).build();

    // Assert
    assertEquals("Business Key", actualBuildResult.getBusinessKey());
    assertEquals("Name", actualBuildResult.getName());
    Map<String, Object> variables2 = actualBuildResult.getVariables();
    assertTrue(variables2.isEmpty());
    assertSame(variables, variables2);
  }
}
