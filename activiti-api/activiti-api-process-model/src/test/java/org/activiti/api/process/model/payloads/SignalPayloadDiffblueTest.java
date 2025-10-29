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

class SignalPayloadDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SignalPayload#setName(String)}
   *   <li>{@link SignalPayload#setVariables(Map)}
   *   <li>{@link SignalPayload#getId()}
   *   <li>{@link SignalPayload#getName()}
   *   <li>{@link SignalPayload#getVariables()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    SignalPayload signalPayload = new SignalPayload();

    // Act
    signalPayload.setName("Name");
    HashMap<String, Object> variables = new HashMap<>();
    signalPayload.setVariables(variables);
    signalPayload.getId();
    String actualName = signalPayload.getName();
    Map<String, Object> actualVariables = signalPayload.getVariables();

    // Assert that nothing has changed
    assertEquals("Name", actualName);
    assertTrue(actualVariables.isEmpty());
    assertSame(variables, actualVariables);
  }

  /**
   * Method under test: {@link SignalPayload#SignalPayload()}
   */
  @Test
  void testNewSignalPayload() {
    // Arrange and Act
    SignalPayload actualSignalPayload = new SignalPayload();

    // Assert
    assertNull(actualSignalPayload.getName());
    assertTrue(actualSignalPayload.getVariables().isEmpty());
  }

  /**
   * Method under test: {@link SignalPayload#SignalPayload(String, Map)}
   */
  @Test
  void testNewSignalPayload2() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    // Act
    SignalPayload actualSignalPayload = new SignalPayload("Name", variables);

    // Assert
    assertEquals("Name", actualSignalPayload.getName());
    Map<String, Object> variables2 = actualSignalPayload.getVariables();
    assertTrue(variables2.isEmpty());
    assertSame(variables, variables2);
  }

  /**
   * Method under test: {@link SignalPayload#SignalPayload(String, Map)}
   */
  @Test
  void testNewSignalPayload3() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("foo", mock(BiFunction.class));

    // Act
    SignalPayload actualSignalPayload = new SignalPayload("Name", variables);

    // Assert
    assertEquals("Name", actualSignalPayload.getName());
    Map<String, Object> variables2 = actualSignalPayload.getVariables();
    assertTrue(variables2.isEmpty());
    assertSame(variables, variables2);
  }
}
