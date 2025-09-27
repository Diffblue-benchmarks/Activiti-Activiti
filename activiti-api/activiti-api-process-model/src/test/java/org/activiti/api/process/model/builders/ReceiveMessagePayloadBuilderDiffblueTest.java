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
import org.activiti.api.process.model.payloads.ReceiveMessagePayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ReceiveMessagePayloadBuilderDiffblueTest {
  /**
   * Test {@link ReceiveMessagePayloadBuilder#from(ReceiveMessagePayload)}.
   *
   * <ul>
   *   <li>Then return build CorrelationKey is {@code messagePayload must not be null}.
   * </ul>
   *
   * <p>Method under test: {@link ReceiveMessagePayloadBuilder#from(ReceiveMessagePayload)}
   */
  @Test
  @DisplayName(
      "Test from(ReceiveMessagePayload); then return build CorrelationKey is 'messagePayload must not be null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ReceiveMessagePayloadBuilder ReceiveMessagePayloadBuilder.from(ReceiveMessagePayload)"
  })
  void testFrom_thenReturnBuildCorrelationKeyIsMessagePayloadMustNotBeNull() {
    // Arrange
    ReceiveMessagePayload messagePayload =
        new ReceiveMessagePayload(
            "messagePayload must not be null", "messagePayload must not be null", new HashMap<>());

    // Act and Assert
    ReceiveMessagePayload receiveMessagePayload =
        ReceiveMessagePayloadBuilder.from(messagePayload).build();
    assertEquals("messagePayload must not be null", receiveMessagePayload.getCorrelationKey());
    assertEquals("messagePayload must not be null", receiveMessagePayload.getName());
    assertTrue(receiveMessagePayload.getVariables().isEmpty());
  }

  /**
   * Test {@link ReceiveMessagePayloadBuilder#receive(String)}.
   *
   * <p>Method under test: {@link ReceiveMessagePayloadBuilder#receive(String)}
   */
  @Test
  @DisplayName("Test receive(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ReceiveMessagePayloadBuilder ReceiveMessagePayloadBuilder.receive(String)"})
  void testReceive() {
    // Arrange, Act and Assert
    ReceiveMessagePayload receiveMessagePayload =
        ReceiveMessagePayloadBuilder.receive("Name").build();
    assertEquals("Name", receiveMessagePayload.getName());
    assertNull(receiveMessagePayload.getCorrelationKey());
    assertNull(receiveMessagePayload.getVariables());
  }

  /**
   * Test {@link ReceiveMessagePayloadBuilder#withVariable(String, Object)}.
   *
   * <ul>
   *   <li>Given {@link ReceiveMessagePayloadBuilder} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ReceiveMessagePayloadBuilder#withVariable(String, Object)}
   */
  @Test
  @DisplayName(
      "Test withVariable(String, Object); given ReceiveMessagePayloadBuilder (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ReceiveMessagePayloadBuilder ReceiveMessagePayloadBuilder.withVariable(String, Object)"
  })
  void testWithVariable_givenReceiveMessagePayloadBuilder() {
    // Arrange
    ReceiveMessagePayloadBuilder receiveMessagePayloadBuilder = new ReceiveMessagePayloadBuilder();

    // Act
    ReceiveMessagePayloadBuilder actualWithVariableResult =
        receiveMessagePayloadBuilder.withVariable("Name", "Value");

    // Assert
    assertSame(receiveMessagePayloadBuilder, actualWithVariableResult);
  }

  /**
   * Test {@link ReceiveMessagePayloadBuilder#withVariable(String, Object)}.
   *
   * <ul>
   *   <li>Given {@link ReceiveMessagePayloadBuilder} (default constructor) withVariables {@link
   *       HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link ReceiveMessagePayloadBuilder#withVariable(String, Object)}
   */
  @Test
  @DisplayName(
      "Test withVariable(String, Object); given ReceiveMessagePayloadBuilder (default constructor) withVariables HashMap()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ReceiveMessagePayloadBuilder ReceiveMessagePayloadBuilder.withVariable(String, Object)"
  })
  void testWithVariable_givenReceiveMessagePayloadBuilderWithVariablesHashMap() {
    // Arrange
    ReceiveMessagePayloadBuilder receiveMessagePayloadBuilder = new ReceiveMessagePayloadBuilder();
    receiveMessagePayloadBuilder.withVariables(new HashMap<>());

    // Act
    ReceiveMessagePayloadBuilder actualWithVariableResult =
        receiveMessagePayloadBuilder.withVariable("Name", "Value");

    // Assert
    assertSame(receiveMessagePayloadBuilder, actualWithVariableResult);
  }

  /**
   * Test {@link ReceiveMessagePayloadBuilder#equals(Object)}, and {@link
   * ReceiveMessagePayloadBuilder#hashCode()}.
   *
   * <ul>
   *   <li>When other is equal.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ReceiveMessagePayloadBuilder#equals(Object)}
   *   <li>{@link ReceiveMessagePayloadBuilder#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ReceiveMessagePayloadBuilder.equals(Object)",
    "int ReceiveMessagePayloadBuilder.hashCode()"
  })
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ReceiveMessagePayloadBuilder receiveMessagePayloadBuilder = new ReceiveMessagePayloadBuilder();
    ReceiveMessagePayloadBuilder receiveMessagePayloadBuilder2 = new ReceiveMessagePayloadBuilder();

    // Act and Assert
    assertEquals(receiveMessagePayloadBuilder, receiveMessagePayloadBuilder2);
    assertEquals(receiveMessagePayloadBuilder.hashCode(), receiveMessagePayloadBuilder2.hashCode());
  }

  /**
   * Test {@link ReceiveMessagePayloadBuilder#equals(Object)}, and {@link
   * ReceiveMessagePayloadBuilder#hashCode()}.
   *
   * <ul>
   *   <li>When other is same.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ReceiveMessagePayloadBuilder#equals(Object)}
   *   <li>{@link ReceiveMessagePayloadBuilder#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ReceiveMessagePayloadBuilder.equals(Object)",
    "int ReceiveMessagePayloadBuilder.hashCode()"
  })
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ReceiveMessagePayloadBuilder receiveMessagePayloadBuilder = new ReceiveMessagePayloadBuilder();

    // Act and Assert
    assertEquals(receiveMessagePayloadBuilder, receiveMessagePayloadBuilder);
    int expectedHashCodeResult = receiveMessagePayloadBuilder.hashCode();
    assertEquals(expectedHashCodeResult, receiveMessagePayloadBuilder.hashCode());
  }

  /**
   * Test {@link ReceiveMessagePayloadBuilder#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link ReceiveMessagePayloadBuilder#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ReceiveMessagePayloadBuilder.equals(Object)",
    "int ReceiveMessagePayloadBuilder.hashCode()"
  })
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ReceiveMessagePayloadBuilder(), 1);
  }

  /**
   * Test {@link ReceiveMessagePayloadBuilder#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link ReceiveMessagePayloadBuilder#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ReceiveMessagePayloadBuilder.equals(Object)",
    "int ReceiveMessagePayloadBuilder.hashCode()"
  })
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ReceiveMessagePayloadBuilder receiveMessagePayloadBuilder = new ReceiveMessagePayloadBuilder();
    receiveMessagePayloadBuilder.withName("Name");

    // Act and Assert
    assertNotEquals(receiveMessagePayloadBuilder, new ReceiveMessagePayloadBuilder());
  }

  /**
   * Test {@link ReceiveMessagePayloadBuilder#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link ReceiveMessagePayloadBuilder#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ReceiveMessagePayloadBuilder.equals(Object)",
    "int ReceiveMessagePayloadBuilder.hashCode()"
  })
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    ReceiveMessagePayloadBuilder receiveMessagePayloadBuilder = new ReceiveMessagePayloadBuilder();
    receiveMessagePayloadBuilder.withVariables(new HashMap<>());

    // Act and Assert
    assertNotEquals(receiveMessagePayloadBuilder, new ReceiveMessagePayloadBuilder());
  }

  /**
   * Test {@link ReceiveMessagePayloadBuilder#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link ReceiveMessagePayloadBuilder#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ReceiveMessagePayloadBuilder.equals(Object)",
    "int ReceiveMessagePayloadBuilder.hashCode()"
  })
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    ReceiveMessagePayloadBuilder receiveMessagePayloadBuilder = new ReceiveMessagePayloadBuilder();
    receiveMessagePayloadBuilder.withCorrelationKey("Correlation Key");

    // Act and Assert
    assertNotEquals(receiveMessagePayloadBuilder, new ReceiveMessagePayloadBuilder());
  }

  /**
   * Test {@link ReceiveMessagePayloadBuilder#equals(Object)}.
   *
   * <ul>
   *   <li>When other is {@code null}.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link ReceiveMessagePayloadBuilder#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ReceiveMessagePayloadBuilder.equals(Object)",
    "int ReceiveMessagePayloadBuilder.hashCode()"
  })
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ReceiveMessagePayloadBuilder(), null);
  }

  /**
   * Test {@link ReceiveMessagePayloadBuilder#equals(Object)}.
   *
   * <ul>
   *   <li>When other is wrong type.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link ReceiveMessagePayloadBuilder#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ReceiveMessagePayloadBuilder.equals(Object)",
    "int ReceiveMessagePayloadBuilder.hashCode()"
  })
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(
        new ReceiveMessagePayloadBuilder(), "Different type to ReceiveMessagePayloadBuilder");
  }

  /**
   * Test {@link ReceiveMessagePayloadBuilder#build()}.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ReceiveMessagePayloadBuilder#build()}
   *   <li>default or parameterless constructor of {@link ReceiveMessagePayloadBuilder}
   *   <li>{@link ReceiveMessagePayloadBuilder#withCorrelationKey(String)}
   *   <li>{@link ReceiveMessagePayloadBuilder#withName(String)}
   *   <li>{@link ReceiveMessagePayloadBuilder#withVariables(Map)}
   * </ul>
   */
  @Test
  @DisplayName("Test build()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ReceiveMessagePayloadBuilder.<init>()",
    "ReceiveMessagePayload ReceiveMessagePayloadBuilder.build()",
    "String ReceiveMessagePayloadBuilder.toString()",
    "ReceiveMessagePayloadBuilder ReceiveMessagePayloadBuilder.withCorrelationKey(String)",
    "ReceiveMessagePayloadBuilder ReceiveMessagePayloadBuilder.withName(String)",
    "ReceiveMessagePayloadBuilder ReceiveMessagePayloadBuilder.withVariables(Map)"
  })
  void testBuild() {
    // Arrange and Act
    ReceiveMessagePayloadBuilder actualWithVariableResult =
        new ReceiveMessagePayloadBuilder()
            .withCorrelationKey("Correlation Key")
            .withName("Name")
            .withVariable("Name", "Value");
    HashMap<String, Object> variables = new HashMap<>();
    ReceiveMessagePayload actualReceiveMessagePayload =
        actualWithVariableResult.withVariables(variables).build();

    // Assert
    assertEquals("Correlation Key", actualReceiveMessagePayload.getCorrelationKey());
    assertEquals("Name", actualReceiveMessagePayload.getName());
    Map<String, Object> variables2 = actualReceiveMessagePayload.getVariables();
    assertTrue(variables2.isEmpty());
    assertSame(variables, variables2);
  }
}
