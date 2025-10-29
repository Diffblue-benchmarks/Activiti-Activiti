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
import org.junit.jupiter.api.Test;

class StartMessagePayloadBuilderDiffblueTest {
  /**
   * Method under test:
   * {@link StartMessagePayloadBuilder#from(StartMessagePayload)}
   */
  @Test
  void testFrom() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    // Act and Assert
    StartMessagePayload buildResult = StartMessagePayloadBuilder
        .from(new StartMessagePayload("messagePayload must not be null", "messagePayload must not be null", variables))
        .build();
    assertEquals("messagePayload must not be null", buildResult.getBusinessKey());
    assertEquals("messagePayload must not be null", buildResult.getName());
    Map<String, Object> variables2 = buildResult.getVariables();
    assertTrue(variables2.isEmpty());
    assertSame(variables, variables2);
  }

  /**
   * Method under test:
   * {@link StartMessagePayloadBuilder#from(StartMessagePayload)}
   */
  @Test
  void testFrom2() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("messagePayload must not be null", mock(BiFunction.class));

    // Act and Assert
    StartMessagePayload buildResult = StartMessagePayloadBuilder
        .from(new StartMessagePayload("messagePayload must not be null", "messagePayload must not be null", variables))
        .build();
    assertEquals("messagePayload must not be null", buildResult.getBusinessKey());
    assertEquals("messagePayload must not be null", buildResult.getName());
    Map<String, Object> variables2 = buildResult.getVariables();
    assertTrue(variables2.isEmpty());
    assertSame(variables, variables2);
  }

  /**
   * Method under test: {@link StartMessagePayloadBuilder#start(String)}
   */
  @Test
  void testStart() {
    // Arrange, Act and Assert
    StartMessagePayload buildResult = StartMessagePayloadBuilder.start("Name").build();
    assertEquals("Name", buildResult.getName());
    assertNull(buildResult.getBusinessKey());
    assertNull(buildResult.getVariables());
  }

  /**
   * Method under test:
   * {@link StartMessagePayloadBuilder#withVariable(String, Object)}
   */
  @Test
  void testWithVariable() {
    // Arrange
    StartMessagePayloadBuilder startMessagePayloadBuilder = new StartMessagePayloadBuilder();

    // Act and Assert
    assertSame(startMessagePayloadBuilder, startMessagePayloadBuilder.withVariable("Name", "Value"));
  }

  /**
   * Method under test:
   * {@link StartMessagePayloadBuilder#withVariable(String, Object)}
   */
  @Test
  void testWithVariable2() {
    // Arrange
    StartMessagePayloadBuilder startMessagePayloadBuilder = new StartMessagePayloadBuilder();
    startMessagePayloadBuilder.withVariables(new HashMap<>());

    // Act and Assert
    assertSame(startMessagePayloadBuilder, startMessagePayloadBuilder.withVariable("Name", "Value"));
  }

  /**
   * Method under test:
   * {@link StartMessagePayloadBuilder#withVariable(String, Object)}
   */
  @Test
  void testWithVariable3() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("foo", mock(BiFunction.class));

    StartMessagePayloadBuilder startMessagePayloadBuilder = new StartMessagePayloadBuilder();
    startMessagePayloadBuilder.withVariables(variables);

    // Act and Assert
    assertSame(startMessagePayloadBuilder, startMessagePayloadBuilder.withVariable("Name", "Value"));
  }

  /**
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
