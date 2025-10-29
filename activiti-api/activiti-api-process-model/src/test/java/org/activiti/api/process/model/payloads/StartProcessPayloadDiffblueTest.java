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

class StartProcessPayloadDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link StartProcessPayload#setName(String)}
   *   <li>{@link StartProcessPayload#getBusinessKey()}
   *   <li>{@link StartProcessPayload#getId()}
   *   <li>{@link StartProcessPayload#getName()}
   *   <li>{@link StartProcessPayload#getProcessDefinitionId()}
   *   <li>{@link StartProcessPayload#getProcessDefinitionKey()}
   *   <li>{@link StartProcessPayload#getVariables()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    StartProcessPayload startProcessPayload = new StartProcessPayload();

    // Act
    startProcessPayload.setName("Name");
    startProcessPayload.getBusinessKey();
    startProcessPayload.getId();
    String actualName = startProcessPayload.getName();
    startProcessPayload.getProcessDefinitionId();
    startProcessPayload.getProcessDefinitionKey();

    // Assert that nothing has changed
    assertEquals("Name", actualName);
    assertTrue(startProcessPayload.getVariables().isEmpty());
  }

  /**
   * Method under test: {@link StartProcessPayload#StartProcessPayload()}
   */
  @Test
  void testNewStartProcessPayload() {
    // Arrange and Act
    StartProcessPayload actualStartProcessPayload = new StartProcessPayload();

    // Assert
    assertNull(actualStartProcessPayload.getBusinessKey());
    assertNull(actualStartProcessPayload.getName());
    assertNull(actualStartProcessPayload.getProcessDefinitionId());
    assertNull(actualStartProcessPayload.getProcessDefinitionKey());
    assertTrue(actualStartProcessPayload.getVariables().isEmpty());
  }

  /**
   * Method under test:
   * {@link StartProcessPayload#StartProcessPayload(String, String, String, String, Map)}
   */
  @Test
  void testNewStartProcessPayload2() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    // Act
    StartProcessPayload actualStartProcessPayload = new StartProcessPayload("42", "Process Definition Key", "Name",
        "Business Key", variables);

    // Assert
    assertEquals("42", actualStartProcessPayload.getProcessDefinitionId());
    assertEquals("Business Key", actualStartProcessPayload.getBusinessKey());
    assertEquals("Name", actualStartProcessPayload.getName());
    assertEquals("Process Definition Key", actualStartProcessPayload.getProcessDefinitionKey());
    Map<String, Object> variables2 = actualStartProcessPayload.getVariables();
    assertTrue(variables2.isEmpty());
    assertSame(variables, variables2);
  }

  /**
   * Method under test:
   * {@link StartProcessPayload#StartProcessPayload(String, String, String, String, Map)}
   */
  @Test
  void testNewStartProcessPayload3() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("foo", mock(BiFunction.class));

    // Act
    StartProcessPayload actualStartProcessPayload = new StartProcessPayload("42", "Process Definition Key", "Name",
        "Business Key", variables);

    // Assert
    assertEquals("42", actualStartProcessPayload.getProcessDefinitionId());
    assertEquals("Business Key", actualStartProcessPayload.getBusinessKey());
    assertEquals("Name", actualStartProcessPayload.getName());
    assertEquals("Process Definition Key", actualStartProcessPayload.getProcessDefinitionKey());
    Map<String, Object> variables2 = actualStartProcessPayload.getVariables();
    assertTrue(variables2.isEmpty());
    assertSame(variables, variables2);
  }
}
