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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.activiti.api.process.model.payloads.MessageEventPayload;
import org.junit.jupiter.api.Test;

class MessageEventPayloadBuilderDiffblueTest {
  /**
   * Method under test:
   * {@link MessageEventPayloadBuilder#from(MessageEventPayload)}
   */
  @Test
  void testFrom() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();

    // Act and Assert
    MessageEventPayload buildResult = MessageEventPayloadBuilder
        .from(new MessageEventPayload("messagePayload must not be null", "messagePayload must not be null",
            "messagePayload must not be null", variables))
        .build();
    assertEquals("messagePayload must not be null", buildResult.getBusinessKey());
    assertEquals("messagePayload must not be null", buildResult.getCorrelationKey());
    assertEquals("messagePayload must not be null", buildResult.getName());
    Map<String, Object> variables2 = buildResult.getVariables();
    assertTrue(variables2.isEmpty());
    assertSame(variables, variables2);
  }

  /**
   * Method under test:
   * {@link MessageEventPayloadBuilder#from(MessageEventPayload)}
   */
  @Test
  void testFrom2() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("messagePayload must not be null", mock(BiFunction.class));

    // Act and Assert
    MessageEventPayload buildResult = MessageEventPayloadBuilder
        .from(new MessageEventPayload("messagePayload must not be null", "messagePayload must not be null",
            "messagePayload must not be null", variables))
        .build();
    assertEquals("messagePayload must not be null", buildResult.getBusinessKey());
    assertEquals("messagePayload must not be null", buildResult.getCorrelationKey());
    assertEquals("messagePayload must not be null", buildResult.getName());
    Map<String, Object> variables2 = buildResult.getVariables();
    assertTrue(variables2.isEmpty());
    assertSame(variables, variables2);
  }

  /**
   * Method under test: {@link MessageEventPayloadBuilder#messageEvent(String)}
   */
  @Test
  void testMessageEvent() {
    // Arrange, Act and Assert
    MessageEventPayload buildResult = MessageEventPayloadBuilder.messageEvent("Name").build();
    assertEquals("Name", buildResult.getName());
    assertNull(buildResult.getBusinessKey());
    assertNull(buildResult.getCorrelationKey());
    assertNull(buildResult.getVariables());
  }

  /**
   * Method under test:
   * {@link MessageEventPayloadBuilder#withVariable(String, Object)}
   */
  @Test
  void testWithVariable() {
    // Arrange
    MessageEventPayloadBuilder messageEventPayloadBuilder = new MessageEventPayloadBuilder();

    // Act and Assert
    assertSame(messageEventPayloadBuilder, messageEventPayloadBuilder.withVariable("Name", "Value"));
  }

  /**
   * Method under test:
   * {@link MessageEventPayloadBuilder#withVariable(String, Object)}
   */
  @Test
  void testWithVariable2() {
    // Arrange
    MessageEventPayloadBuilder messageEventPayloadBuilder = new MessageEventPayloadBuilder();
    messageEventPayloadBuilder.withVariables(new HashMap<>());

    // Act and Assert
    assertSame(messageEventPayloadBuilder, messageEventPayloadBuilder.withVariable("Name", "Value"));
  }

  /**
   * Method under test:
   * {@link MessageEventPayloadBuilder#withVariable(String, Object)}
   */
  @Test
  void testWithVariable3() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("foo", mock(BiFunction.class));

    MessageEventPayloadBuilder messageEventPayloadBuilder = new MessageEventPayloadBuilder();
    messageEventPayloadBuilder.withVariables(variables);

    // Act and Assert
    assertSame(messageEventPayloadBuilder, messageEventPayloadBuilder.withVariable("Name", "Value"));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MessageEventPayloadBuilder#build()}
   *   <li>default or parameterless constructor of
   * {@link MessageEventPayloadBuilder}
   *   <li>{@link MessageEventPayloadBuilder#withBusinessKey(String)}
   *   <li>{@link MessageEventPayloadBuilder#withCorrelationKey(String)}
   *   <li>{@link MessageEventPayloadBuilder#withName(String)}
   *   <li>{@link MessageEventPayloadBuilder#withVariables(Map)}
   * </ul>
   */
  @Test
  void testBuild() {
    // Arrange
    MessageEventPayloadBuilder withVariableResult = (new MessageEventPayloadBuilder()).withBusinessKey("Business Key")
        .withCorrelationKey("Correlation Key")
        .withName("Name")
        .withVariable("Name", "Value");
    HashMap<String, Object> variables = new HashMap<>();

    // Act
    MessageEventPayload actualBuildResult = withVariableResult.withVariables(variables).build();

    // Assert
    assertEquals("Business Key", actualBuildResult.getBusinessKey());
    assertEquals("Correlation Key", actualBuildResult.getCorrelationKey());
    assertEquals("Name", actualBuildResult.getName());
    Map<String, Object> variables2 = actualBuildResult.getVariables();
    assertTrue(variables2.isEmpty());
    assertSame(variables, variables2);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MessageEventPayloadBuilder#equals(Object)}
   *   <li>{@link MessageEventPayloadBuilder#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    MessageEventPayloadBuilder messageEventPayloadBuilder = new MessageEventPayloadBuilder();
    MessageEventPayloadBuilder messageEventPayloadBuilder2 = new MessageEventPayloadBuilder();

    // Act and Assert
    assertEquals(messageEventPayloadBuilder, messageEventPayloadBuilder2);
    int expectedHashCodeResult = messageEventPayloadBuilder.hashCode();
    assertEquals(expectedHashCodeResult, messageEventPayloadBuilder2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MessageEventPayloadBuilder#equals(Object)}
   *   <li>{@link MessageEventPayloadBuilder#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    MessageEventPayloadBuilder messageEventPayloadBuilder = new MessageEventPayloadBuilder();

    // Act and Assert
    assertEquals(messageEventPayloadBuilder, messageEventPayloadBuilder);
    int expectedHashCodeResult = messageEventPayloadBuilder.hashCode();
    assertEquals(expectedHashCodeResult, messageEventPayloadBuilder.hashCode());
  }

  /**
   * Method under test: {@link MessageEventPayloadBuilder#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new MessageEventPayloadBuilder(), 1);
  }

  /**
   * Method under test: {@link MessageEventPayloadBuilder#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    MessageEventPayloadBuilder messageEventPayloadBuilder = new MessageEventPayloadBuilder();
    messageEventPayloadBuilder.withName("Name");

    // Act and Assert
    assertNotEquals(messageEventPayloadBuilder, new MessageEventPayloadBuilder());
  }

  /**
   * Method under test: {@link MessageEventPayloadBuilder#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    MessageEventPayloadBuilder messageEventPayloadBuilder = new MessageEventPayloadBuilder();
    messageEventPayloadBuilder.withVariables(new HashMap<>());

    // Act and Assert
    assertNotEquals(messageEventPayloadBuilder, new MessageEventPayloadBuilder());
  }

  /**
   * Method under test: {@link MessageEventPayloadBuilder#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    MessageEventPayloadBuilder messageEventPayloadBuilder = new MessageEventPayloadBuilder();
    messageEventPayloadBuilder.withCorrelationKey("Correlation Key");

    // Act and Assert
    assertNotEquals(messageEventPayloadBuilder, new MessageEventPayloadBuilder());
  }

  /**
   * Method under test: {@link MessageEventPayloadBuilder#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    MessageEventPayloadBuilder messageEventPayloadBuilder = new MessageEventPayloadBuilder();
    messageEventPayloadBuilder.withBusinessKey("Business Key");

    // Act and Assert
    assertNotEquals(messageEventPayloadBuilder, new MessageEventPayloadBuilder());
  }

  /**
   * Method under test: {@link MessageEventPayloadBuilder#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("foo", mock(BiFunction.class));

    MessageEventPayloadBuilder messageEventPayloadBuilder = new MessageEventPayloadBuilder();
    messageEventPayloadBuilder.withVariables(variables);

    // Act and Assert
    assertNotEquals(messageEventPayloadBuilder, new MessageEventPayloadBuilder());
  }

  /**
   * Method under test: {@link MessageEventPayloadBuilder#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new MessageEventPayloadBuilder(), null);
  }

  /**
   * Method under test: {@link MessageEventPayloadBuilder#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new MessageEventPayloadBuilder(), "Different type to MessageEventPayloadBuilder");
  }
}
