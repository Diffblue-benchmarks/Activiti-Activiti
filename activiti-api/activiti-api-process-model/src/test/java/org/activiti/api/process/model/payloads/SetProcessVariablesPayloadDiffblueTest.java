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
package org.activiti.api.process.model.payloads;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SetProcessVariablesPayloadDiffblueTest {
  /**
   * Test {@link SetProcessVariablesPayload#SetProcessVariablesPayload()}.
   * <p>
   * Method under test:
   * {@link SetProcessVariablesPayload#SetProcessVariablesPayload()}
   */
  @Test
  @DisplayName("Test new SetProcessVariablesPayload()")
  void testNewSetProcessVariablesPayload() {
    // Arrange and Act
    SetProcessVariablesPayload actualSetProcessVariablesPayload = new SetProcessVariablesPayload();

    // Assert
    assertNull(actualSetProcessVariablesPayload.getProcessInstanceId());
    assertTrue(actualSetProcessVariablesPayload.getVariables().isEmpty());
  }

  /**
   * Test
   * {@link SetProcessVariablesPayload#SetProcessVariablesPayload(String, Map)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SetProcessVariablesPayload#SetProcessVariablesPayload(String, Map)}
   */
  @Test
  @DisplayName("Test new SetProcessVariablesPayload(String, Map); given 'foo'")
  void testNewSetProcessVariablesPayload_givenFoo() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("foo", mock(BiFunction.class));

    // Act
    SetProcessVariablesPayload actualSetProcessVariablesPayload = new SetProcessVariablesPayload("42", variables);

    // Assert
    assertEquals("42", actualSetProcessVariablesPayload.getProcessInstanceId());
    assertTrue(actualSetProcessVariablesPayload.getVariables().isEmpty());
  }

  /**
   * Test
   * {@link SetProcessVariablesPayload#SetProcessVariablesPayload(String, Map)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SetProcessVariablesPayload#SetProcessVariablesPayload(String, Map)}
   */
  @Test
  @DisplayName("Test new SetProcessVariablesPayload(String, Map); when HashMap()")
  void testNewSetProcessVariablesPayload_whenHashMap() {
    // Arrange and Act
    SetProcessVariablesPayload actualSetProcessVariablesPayload = new SetProcessVariablesPayload("42", new HashMap<>());

    // Assert
    assertEquals("42", actualSetProcessVariablesPayload.getProcessInstanceId());
    assertTrue(actualSetProcessVariablesPayload.getVariables().isEmpty());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SetProcessVariablesPayload#setProcessInstanceId(String)}
   *   <li>{@link SetProcessVariablesPayload#setVariables(Map)}
   *   <li>{@link SetProcessVariablesPayload#getId()}
   *   <li>{@link SetProcessVariablesPayload#getProcessInstanceId()}
   *   <li>{@link SetProcessVariablesPayload#getVariables()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    SetProcessVariablesPayload setProcessVariablesPayload = new SetProcessVariablesPayload();

    // Act
    setProcessVariablesPayload.setProcessInstanceId("42");
    HashMap<String, Object> variables = new HashMap<>();
    setProcessVariablesPayload.setVariables(variables);
    setProcessVariablesPayload.getId();
    String actualProcessInstanceId = setProcessVariablesPayload.getProcessInstanceId();
    Map<String, Object> actualVariables = setProcessVariablesPayload.getVariables();

    // Assert that nothing has changed
    assertEquals("42", actualProcessInstanceId);
    assertTrue(actualVariables.isEmpty());
    assertSame(variables, actualVariables);
  }
}
