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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.jupiter.api.Test;

class MessageEventPayloadDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MessageEventPayload#equals(Object)}
   *   <li>{@link MessageEventPayload#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    MessageEventPayload messageEventPayload = new MessageEventPayload();

    // Act and Assert
    assertEquals(messageEventPayload, messageEventPayload);
    int expectedHashCodeResult = messageEventPayload.hashCode();
    assertEquals(expectedHashCodeResult, messageEventPayload.hashCode());
  }

  /**
   * Method under test: {@link MessageEventPayload#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    MessageEventPayload messageEventPayload = new MessageEventPayload();

    // Act and Assert
    assertNotEquals(messageEventPayload, new MessageEventPayload());
  }

  /**
   * Method under test: {@link MessageEventPayload#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    MessageEventPayload messageEventPayload = new MessageEventPayload("Name", "Correlation Key", "Business Key",
        new HashMap<>());

    // Act and Assert
    assertNotEquals(messageEventPayload, new MessageEventPayload());
  }

  /**
   * Method under test: {@link MessageEventPayload#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    MessageEventPayload messageEventPayload = new MessageEventPayload("Name", "Correlation Key", null, new HashMap<>());

    // Act and Assert
    assertNotEquals(messageEventPayload, new MessageEventPayload());
  }

  /**
   * Method under test: {@link MessageEventPayload#equals(Object)}
   */
  @Test
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
   * Method under test: {@link MessageEventPayload#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new MessageEventPayload(), null);
  }

  /**
   * Method under test: {@link MessageEventPayload#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new MessageEventPayload(), "Different type to MessageEventPayload");
  }

  /**
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
   * Method under test: {@link MessageEventPayload#MessageEventPayload()}
   */
  @Test
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
   * Method under test:
   * {@link MessageEventPayload#MessageEventPayload(String, String, String, Map)}
   */
  @Test
  void testNewMessageEventPayload2() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    // Act
    MessageEventPayload actualMessageEventPayload = new MessageEventPayload("Name", "Correlation Key", "Business Key",
        variables);

    // Assert
    assertEquals("Business Key", actualMessageEventPayload.getBusinessKey());
    assertEquals("Correlation Key", actualMessageEventPayload.getCorrelationKey());
    assertEquals("Name", actualMessageEventPayload.getName());
    Map<String, Object> variables2 = actualMessageEventPayload.getVariables();
    assertTrue(variables2.isEmpty());
    assertSame(variables, variables2);
  }

  /**
   * Method under test:
   * {@link MessageEventPayload#MessageEventPayload(String, String, String, Map)}
   */
  @Test
  void testNewMessageEventPayload3() {
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
    Map<String, Object> variables2 = actualMessageEventPayload.getVariables();
    assertTrue(variables2.isEmpty());
    assertSame(variables, variables2);
  }
}
