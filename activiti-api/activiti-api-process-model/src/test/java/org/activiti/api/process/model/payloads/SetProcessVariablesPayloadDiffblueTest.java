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
import org.junit.jupiter.api.Test;

class SetProcessVariablesPayloadDiffblueTest {
  /**
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

  /**
   * Method under test:
   * {@link SetProcessVariablesPayload#SetProcessVariablesPayload()}
   */
  @Test
  void testNewSetProcessVariablesPayload() {
    // Arrange and Act
    SetProcessVariablesPayload actualSetProcessVariablesPayload = new SetProcessVariablesPayload();

    // Assert
    assertNull(actualSetProcessVariablesPayload.getProcessInstanceId());
    assertTrue(actualSetProcessVariablesPayload.getVariables().isEmpty());
  }

  /**
   * Method under test:
   * {@link SetProcessVariablesPayload#SetProcessVariablesPayload(String, Map)}
   */
  @Test
  void testNewSetProcessVariablesPayload2() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    // Act
    SetProcessVariablesPayload actualSetProcessVariablesPayload = new SetProcessVariablesPayload("42", variables);

    // Assert
    assertEquals("42", actualSetProcessVariablesPayload.getProcessInstanceId());
    Map<String, Object> variables2 = actualSetProcessVariablesPayload.getVariables();
    assertTrue(variables2.isEmpty());
    assertSame(variables, variables2);
  }

  /**
   * Method under test:
   * {@link SetProcessVariablesPayload#SetProcessVariablesPayload(String, Map)}
   */
  @Test
  void testNewSetProcessVariablesPayload3() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("foo", mock(BiFunction.class));

    // Act
    SetProcessVariablesPayload actualSetProcessVariablesPayload = new SetProcessVariablesPayload("42", variables);

    // Assert
    assertEquals("42", actualSetProcessVariablesPayload.getProcessInstanceId());
    Map<String, Object> variables2 = actualSetProcessVariablesPayload.getVariables();
    assertTrue(variables2.isEmpty());
    assertSame(variables, variables2);
  }
}
