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

class SignalPayloadDiffblueTest {
  /**
   * Test {@link SignalPayload#SignalPayload()}.
   * <p>
   * Method under test: {@link SignalPayload#SignalPayload()}
   */
  @Test
  @DisplayName("Test new SignalPayload()")
  void testNewSignalPayload() {
    // Arrange and Act
    SignalPayload actualSignalPayload = new SignalPayload();

    // Assert
    assertNull(actualSignalPayload.getName());
    assertTrue(actualSignalPayload.getVariables().isEmpty());
  }

  /**
   * Test {@link SignalPayload#SignalPayload(String, Map)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SignalPayload#SignalPayload(String, Map)}
   */
  @Test
  @DisplayName("Test new SignalPayload(String, Map); given 'foo'; when HashMap() computeIfPresent 'foo' and BiFunction")
  void testNewSignalPayload_givenFoo_whenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("foo", mock(BiFunction.class));

    // Act
    SignalPayload actualSignalPayload = new SignalPayload("Name", variables);

    // Assert
    assertEquals("Name", actualSignalPayload.getName());
    assertTrue(actualSignalPayload.getVariables().isEmpty());
  }

  /**
   * Test {@link SignalPayload#SignalPayload(String, Map)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SignalPayload#SignalPayload(String, Map)}
   */
  @Test
  @DisplayName("Test new SignalPayload(String, Map); when HashMap()")
  void testNewSignalPayload_whenHashMap() {
    // Arrange and Act
    SignalPayload actualSignalPayload = new SignalPayload("Name", new HashMap<>());

    // Assert
    assertEquals("Name", actualSignalPayload.getName());
    assertTrue(actualSignalPayload.getVariables().isEmpty());
  }

  /**
   * Test getters and setters.
   * <p>
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
  @DisplayName("Test getters and setters")
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
}
