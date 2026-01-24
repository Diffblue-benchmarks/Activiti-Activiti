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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.activiti.api.process.model.payloads.MessageEventPayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class MessageEventPayloadBuilderDiffblueTest {
  /**
   * Test {@link MessageEventPayloadBuilder#from(MessageEventPayload)}.
   *
   * <ul>
   *   <li>Then return build BusinessKey is {@code messagePayload must not be null}.
   * </ul>
   *
   * <p>Method under test: {@link MessageEventPayloadBuilder#from(MessageEventPayload)}
   */
  @Test
  @DisplayName(
      "Test from(MessageEventPayload); then return build BusinessKey is 'messagePayload must not be null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessageEventPayloadBuilder MessageEventPayloadBuilder.from(MessageEventPayload)"
  })
  void testFrom_thenReturnBuildBusinessKeyIsMessagePayloadMustNotBeNull() {
    // Arrange
    MessageEventPayload messagePayload =
        new MessageEventPayload(
            "messagePayload must not be null",
            "messagePayload must not be null",
            "messagePayload must not be null",
            new HashMap<>());

    // Act and Assert
    MessageEventPayload messageEventPayload =
        MessageEventPayloadBuilder.from(messagePayload).build();
    assertEquals("messagePayload must not be null", messageEventPayload.getBusinessKey());
    assertEquals("messagePayload must not be null", messageEventPayload.getCorrelationKey());
    assertEquals("messagePayload must not be null", messageEventPayload.getName());
    assertTrue(messageEventPayload.getVariables().isEmpty());
  }

  /**
   * Test {@link MessageEventPayloadBuilder#messageEvent(String)}.
   *
   * <p>Method under test: {@link MessageEventPayloadBuilder#messageEvent(String)}
   */
  @Test
  @DisplayName("Test messageEvent(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"MessageEventPayloadBuilder MessageEventPayloadBuilder.messageEvent(String)"})
  void testMessageEvent() {
    // Arrange, Act and Assert
    MessageEventPayload messageEventPayload =
        MessageEventPayloadBuilder.messageEvent("Name").build();
    assertEquals("Name", messageEventPayload.getName());
    assertNull(messageEventPayload.getBusinessKey());
    assertNull(messageEventPayload.getCorrelationKey());
    assertNull(messageEventPayload.getVariables());
  }

  /**
   * Test {@link MessageEventPayloadBuilder#withVariable(String, Object)}.
   *
   * <ul>
   *   <li>Given {@link MessageEventPayloadBuilder} (default constructor) withVariables {@link
   *       HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link MessageEventPayloadBuilder#withVariable(String, Object)}
   */
  @Test
  @DisplayName(
      "Test withVariable(String, Object); given MessageEventPayloadBuilder (default constructor) withVariables HashMap()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MessageEventPayloadBuilder MessageEventPayloadBuilder.withVariable(String, Object)"
  })
  void testWithVariable_givenMessageEventPayloadBuilderWithVariablesHashMap() {
    // Arrange
    MessageEventPayloadBuilder messageEventPayloadBuilder = new MessageEventPayloadBuilder();
    messageEventPayloadBuilder.withVariables(new HashMap<>());

    // Act
    MessageEventPayloadBuilder actualWithVariableResult =
        messageEventPayloadBuilder.withVariable("Name", "Value");

    // Assert
    assertSame(messageEventPayloadBuilder, actualWithVariableResult);
  }

  /**
   * Test {@link MessageEventPayloadBuilder#equals(Object)}, and {@link
   * MessageEventPayloadBuilder#hashCode()}.
   *
   * <ul>
   *   <li>When other is equal.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link MessageEventPayloadBuilder#equals(Object)}
   *   <li>{@link MessageEventPayloadBuilder#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MessageEventPayloadBuilder.equals(Object)",
    "int MessageEventPayloadBuilder.hashCode()"
  })
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    MessageEventPayloadBuilder messageEventPayloadBuilder = new MessageEventPayloadBuilder();
    MessageEventPayloadBuilder messageEventPayloadBuilder2 = new MessageEventPayloadBuilder();

    // Act and Assert
    assertEquals(messageEventPayloadBuilder, messageEventPayloadBuilder2);
    assertEquals(messageEventPayloadBuilder.hashCode(), messageEventPayloadBuilder2.hashCode());
  }

  /**
   * Test {@link MessageEventPayloadBuilder#equals(Object)}, and {@link
   * MessageEventPayloadBuilder#hashCode()}.
   *
   * <ul>
   *   <li>When other is same.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link MessageEventPayloadBuilder#equals(Object)}
   *   <li>{@link MessageEventPayloadBuilder#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MessageEventPayloadBuilder.equals(Object)",
    "int MessageEventPayloadBuilder.hashCode()"
  })
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    MessageEventPayloadBuilder messageEventPayloadBuilder = new MessageEventPayloadBuilder();

    // Act and Assert
    assertEquals(messageEventPayloadBuilder, messageEventPayloadBuilder);
    int expectedHashCodeResult = messageEventPayloadBuilder.hashCode();
    assertEquals(expectedHashCodeResult, messageEventPayloadBuilder.hashCode());
  }

  /**
   * Test {@link MessageEventPayloadBuilder#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link MessageEventPayloadBuilder#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MessageEventPayloadBuilder.equals(Object)",
    "int MessageEventPayloadBuilder.hashCode()"
  })
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new MessageEventPayloadBuilder(), 1);
  }

  /**
   * Test {@link MessageEventPayloadBuilder#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link MessageEventPayloadBuilder#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MessageEventPayloadBuilder.equals(Object)",
    "int MessageEventPayloadBuilder.hashCode()"
  })
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    MessageEventPayloadBuilder messageEventPayloadBuilder = new MessageEventPayloadBuilder();
    messageEventPayloadBuilder.withName("Name");

    // Act and Assert
    assertNotEquals(messageEventPayloadBuilder, new MessageEventPayloadBuilder());
  }

  /**
   * Test {@link MessageEventPayloadBuilder#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link MessageEventPayloadBuilder#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MessageEventPayloadBuilder.equals(Object)",
    "int MessageEventPayloadBuilder.hashCode()"
  })
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    MessageEventPayloadBuilder messageEventPayloadBuilder = new MessageEventPayloadBuilder();
    messageEventPayloadBuilder.withVariables(new HashMap<>());

    // Act and Assert
    assertNotEquals(messageEventPayloadBuilder, new MessageEventPayloadBuilder());
  }

  /**
   * Test {@link MessageEventPayloadBuilder#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link MessageEventPayloadBuilder#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MessageEventPayloadBuilder.equals(Object)",
    "int MessageEventPayloadBuilder.hashCode()"
  })
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    MessageEventPayloadBuilder messageEventPayloadBuilder = new MessageEventPayloadBuilder();
    messageEventPayloadBuilder.withCorrelationKey("Correlation Key");

    // Act and Assert
    assertNotEquals(messageEventPayloadBuilder, new MessageEventPayloadBuilder());
  }

  /**
   * Test {@link MessageEventPayloadBuilder#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link MessageEventPayloadBuilder#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MessageEventPayloadBuilder.equals(Object)",
    "int MessageEventPayloadBuilder.hashCode()"
  })
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    MessageEventPayloadBuilder messageEventPayloadBuilder = new MessageEventPayloadBuilder();
    messageEventPayloadBuilder.withBusinessKey("Business Key");

    // Act and Assert
    assertNotEquals(messageEventPayloadBuilder, new MessageEventPayloadBuilder());
  }

  /**
   * Test {@link MessageEventPayloadBuilder#equals(Object)}.
   *
   * <ul>
   *   <li>When other is {@code null}.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link MessageEventPayloadBuilder#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MessageEventPayloadBuilder.equals(Object)",
    "int MessageEventPayloadBuilder.hashCode()"
  })
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new MessageEventPayloadBuilder(), null);
  }

  /**
   * Test {@link MessageEventPayloadBuilder#equals(Object)}.
   *
   * <ul>
   *   <li>When other is wrong type.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link MessageEventPayloadBuilder#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean MessageEventPayloadBuilder.equals(Object)",
    "int MessageEventPayloadBuilder.hashCode()"
  })
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(
        new MessageEventPayloadBuilder(), "Different type to MessageEventPayloadBuilder");
  }

  /**
   * Test {@link MessageEventPayloadBuilder#build()}.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link MessageEventPayloadBuilder#build()}
   *   <li>default or parameterless constructor of {@link MessageEventPayloadBuilder}
   *   <li>{@link MessageEventPayloadBuilder#withBusinessKey(String)}
   *   <li>{@link MessageEventPayloadBuilder#withCorrelationKey(String)}
   *   <li>{@link MessageEventPayloadBuilder#withName(String)}
   *   <li>{@link MessageEventPayloadBuilder#withVariables(Map)}
   * </ul>
   */
  @Test
  @DisplayName("Test build()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void MessageEventPayloadBuilder.<init>()",
    "MessageEventPayload MessageEventPayloadBuilder.build()",
    "String MessageEventPayloadBuilder.toString()",
    "MessageEventPayloadBuilder MessageEventPayloadBuilder.withBusinessKey(String)",
    "MessageEventPayloadBuilder MessageEventPayloadBuilder.withCorrelationKey(String)",
    "MessageEventPayloadBuilder MessageEventPayloadBuilder.withName(String)",
    "MessageEventPayloadBuilder MessageEventPayloadBuilder.withVariables(Map)"
  })
  void testBuild() {
    // Arrange and Act
    MessageEventPayloadBuilder actualWithVariableResult =
        new MessageEventPayloadBuilder()
            .withBusinessKey("Business Key")
            .withCorrelationKey("Correlation Key")
            .withName("Name")
            .withVariable("Name", "Value");
    HashMap<String, Object> variables = new HashMap<>();
    MessageEventPayload actualMessageEventPayload =
        actualWithVariableResult.withVariables(variables).build();

    // Assert
    assertEquals("Business Key", actualMessageEventPayload.getBusinessKey());
    assertEquals("Correlation Key", actualMessageEventPayload.getCorrelationKey());
    assertEquals("Name", actualMessageEventPayload.getName());
    Map<String, Object> variables2 = actualMessageEventPayload.getVariables();
    assertTrue(variables2.isEmpty());
    assertSame(variables, variables2);
  }
}
