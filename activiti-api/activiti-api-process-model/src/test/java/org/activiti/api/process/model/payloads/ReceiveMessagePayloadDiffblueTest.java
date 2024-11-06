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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReceiveMessagePayloadDiffblueTest {
  /**
   * Test {@link ReceiveMessagePayload#ReceiveMessagePayload()}.
   * <p>
   * Method under test: {@link ReceiveMessagePayload#ReceiveMessagePayload()}
   */
  @Test
  @DisplayName("Test new ReceiveMessagePayload()")
  void testNewReceiveMessagePayload() {
    // Arrange and Act
    ReceiveMessagePayload actualReceiveMessagePayload = new ReceiveMessagePayload();

    // Assert
    assertNull(actualReceiveMessagePayload.getCorrelationKey());
    assertNull(actualReceiveMessagePayload.getName());
    assertTrue(actualReceiveMessagePayload.getVariables().isEmpty());
  }

  /**
   * Test
   * {@link ReceiveMessagePayload#ReceiveMessagePayload(String, String, Map)}.
   * <ul>
   *   <li>Given {@code name must not be null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ReceiveMessagePayload#ReceiveMessagePayload(String, String, Map)}
   */
  @Test
  @DisplayName("Test new ReceiveMessagePayload(String, String, Map); given 'name must not be null'")
  void testNewReceiveMessagePayload_givenNameMustNotBeNull() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("name must not be null", mock(BiFunction.class));

    // Act
    ReceiveMessagePayload actualReceiveMessagePayload = new ReceiveMessagePayload("Name", "Correlation Key", variables);

    // Assert
    assertEquals("Correlation Key", actualReceiveMessagePayload.getCorrelationKey());
    assertEquals("Name", actualReceiveMessagePayload.getName());
    assertTrue(actualReceiveMessagePayload.getVariables().isEmpty());
  }

  /**
   * Test
   * {@link ReceiveMessagePayload#ReceiveMessagePayload(String, String, Map)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ReceiveMessagePayload#ReceiveMessagePayload(String, String, Map)}
   */
  @Test
  @DisplayName("Test new ReceiveMessagePayload(String, String, Map); when HashMap()")
  void testNewReceiveMessagePayload_whenHashMap() {
    // Arrange and Act
    ReceiveMessagePayload actualReceiveMessagePayload = new ReceiveMessagePayload("Name", "Correlation Key",
        new HashMap<>());

    // Assert
    assertEquals("Correlation Key", actualReceiveMessagePayload.getCorrelationKey());
    assertEquals("Name", actualReceiveMessagePayload.getName());
    assertTrue(actualReceiveMessagePayload.getVariables().isEmpty());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ReceiveMessagePayload#toString()}
   *   <li>{@link ReceiveMessagePayload#getCorrelationKey()}
   *   <li>{@link ReceiveMessagePayload#getId()}
   *   <li>{@link ReceiveMessagePayload#getName()}
   *   <li>{@link ReceiveMessagePayload#getVariables()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    ReceiveMessagePayload receiveMessagePayload = new ReceiveMessagePayload();

    // Act
    receiveMessagePayload.toString();
    String actualCorrelationKey = receiveMessagePayload.getCorrelationKey();
    receiveMessagePayload.getId();
    String actualName = receiveMessagePayload.getName();

    // Assert
    assertNull(actualCorrelationKey);
    assertNull(actualName);
    assertTrue(receiveMessagePayload.getVariables().isEmpty());
  }

  /**
   * Test {@link ReceiveMessagePayload#equals(Object)}, and
   * {@link ReceiveMessagePayload#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ReceiveMessagePayload#equals(Object)}
   *   <li>{@link ReceiveMessagePayload#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ReceiveMessagePayload receiveMessagePayload = new ReceiveMessagePayload();

    // Act and Assert
    assertEquals(receiveMessagePayload, receiveMessagePayload);
    int expectedHashCodeResult = receiveMessagePayload.hashCode();
    assertEquals(expectedHashCodeResult, receiveMessagePayload.hashCode());
  }

  /**
   * Test {@link ReceiveMessagePayload#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReceiveMessagePayload#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ReceiveMessagePayload receiveMessagePayload = new ReceiveMessagePayload();

    // Act and Assert
    assertNotEquals(receiveMessagePayload, new ReceiveMessagePayload());
  }

  /**
   * Test {@link ReceiveMessagePayload#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReceiveMessagePayload#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ReceiveMessagePayload receiveMessagePayload = new ReceiveMessagePayload("Name", "Correlation Key", new HashMap<>());

    // Act and Assert
    assertNotEquals(receiveMessagePayload, new ReceiveMessagePayload());
  }

  /**
   * Test {@link ReceiveMessagePayload#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReceiveMessagePayload#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("foo", mock(BiFunction.class));
    ReceiveMessagePayload receiveMessagePayload = new ReceiveMessagePayload("Name", "Correlation Key", variables);

    // Act and Assert
    assertNotEquals(receiveMessagePayload, new ReceiveMessagePayload());
  }

  /**
   * Test {@link ReceiveMessagePayload#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReceiveMessagePayload#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ReceiveMessagePayload(), null);
  }

  /**
   * Test {@link ReceiveMessagePayload#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReceiveMessagePayload#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ReceiveMessagePayload(), "Different type to ReceiveMessagePayload");
  }
}
