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

class MessageEventPayloadDiffblueTest {
  /**
   * Test {@link MessageEventPayload#MessageEventPayload()}.
   * <p>
   * Method under test: {@link MessageEventPayload#MessageEventPayload()}
   */
  @Test
  @DisplayName("Test new MessageEventPayload()")
  void testNewMessageEventPayload() {
    // Arrange and Act
    MessageEventPayload actualMessageEventPayload = new MessageEventPayload();

    // Assert
    assertNull(actualMessageEventPayload.getBusinessKey());
    assertNull(actualMessageEventPayload.getCorrelationKey());
    assertNull(actualMessageEventPayload.getName());
    assertNull(actualMessageEventPayload.getVariables());
  }

  /**
   * Test
   * {@link MessageEventPayload#MessageEventPayload(String, String, String, Map)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MessageEventPayload#MessageEventPayload(String, String, String, Map)}
   */
  @Test
  @DisplayName("Test new MessageEventPayload(String, String, String, Map); given 'foo'; when HashMap() computeIfPresent 'foo' and BiFunction")
  void testNewMessageEventPayload_givenFoo_whenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("foo", mock(BiFunction.class));

    // Act
    MessageEventPayload actualMessageEventPayload = new MessageEventPayload("Name", "Correlation Key", "Business Key",
        variables);

    // Assert
    assertEquals("Business Key", actualMessageEventPayload.getBusinessKey());
    assertEquals("Correlation Key", actualMessageEventPayload.getCorrelationKey());
    assertEquals("Name", actualMessageEventPayload.getName());
    assertTrue(actualMessageEventPayload.getVariables().isEmpty());
  }

  /**
   * Test
   * {@link MessageEventPayload#MessageEventPayload(String, String, String, Map)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MessageEventPayload#MessageEventPayload(String, String, String, Map)}
   */
  @Test
  @DisplayName("Test new MessageEventPayload(String, String, String, Map); when HashMap()")
  void testNewMessageEventPayload_whenHashMap() {
    // Arrange and Act
    MessageEventPayload actualMessageEventPayload = new MessageEventPayload("Name", "Correlation Key", "Business Key",
        new HashMap<>());

    // Assert
    assertEquals("Business Key", actualMessageEventPayload.getBusinessKey());
    assertEquals("Correlation Key", actualMessageEventPayload.getCorrelationKey());
    assertEquals("Name", actualMessageEventPayload.getName());
    assertTrue(actualMessageEventPayload.getVariables().isEmpty());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link MessageEventPayload#toString()}
   *   <li>{@link MessageEventPayload#getBusinessKey()}
   *   <li>{@link MessageEventPayload#getCorrelationKey()}
   *   <li>{@link MessageEventPayload#getId()}
   *   <li>{@link MessageEventPayload#getName()}
   *   <li>{@link MessageEventPayload#getVariables()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    MessageEventPayload messageEventPayload = new MessageEventPayload();

    // Act
    messageEventPayload.toString();
    String actualBusinessKey = messageEventPayload.getBusinessKey();
    String actualCorrelationKey = messageEventPayload.getCorrelationKey();
    messageEventPayload.getId();
    String actualName = messageEventPayload.getName();

    // Assert
    assertNull(actualBusinessKey);
    assertNull(actualCorrelationKey);
    assertNull(actualName);
    assertNull(messageEventPayload.getVariables());
  }

  /**
   * Test {@link MessageEventPayload#equals(Object)}, and
   * {@link MessageEventPayload#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link MessageEventPayload#equals(Object)}
   *   <li>{@link MessageEventPayload#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    MessageEventPayload messageEventPayload = new MessageEventPayload();

    // Act and Assert
    assertEquals(messageEventPayload, messageEventPayload);
    int expectedHashCodeResult = messageEventPayload.hashCode();
    assertEquals(expectedHashCodeResult, messageEventPayload.hashCode());
  }

  /**
   * Test {@link MessageEventPayload#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageEventPayload#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    MessageEventPayload messageEventPayload = new MessageEventPayload();

    // Act and Assert
    assertNotEquals(messageEventPayload, new MessageEventPayload());
  }

  /**
   * Test {@link MessageEventPayload#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageEventPayload#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    MessageEventPayload messageEventPayload = new MessageEventPayload("Name", "Correlation Key", "Business Key",
        new HashMap<>());

    // Act and Assert
    assertNotEquals(messageEventPayload, new MessageEventPayload());
  }

  /**
   * Test {@link MessageEventPayload#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageEventPayload#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    MessageEventPayload messageEventPayload = new MessageEventPayload("Name", "Correlation Key", null, new HashMap<>());

    // Act and Assert
    assertNotEquals(messageEventPayload, new MessageEventPayload());
  }

  /**
   * Test {@link MessageEventPayload#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageEventPayload#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("foo", mock(BiFunction.class));
    MessageEventPayload messageEventPayload = new MessageEventPayload("Name", "Correlation Key", "Business Key",
        variables);

    // Act and Assert
    assertNotEquals(messageEventPayload, new MessageEventPayload());
  }

  /**
   * Test {@link MessageEventPayload#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageEventPayload#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new MessageEventPayload(), null);
  }

  /**
   * Test {@link MessageEventPayload#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageEventPayload#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new MessageEventPayload(), "Different type to MessageEventPayload");
  }
}
