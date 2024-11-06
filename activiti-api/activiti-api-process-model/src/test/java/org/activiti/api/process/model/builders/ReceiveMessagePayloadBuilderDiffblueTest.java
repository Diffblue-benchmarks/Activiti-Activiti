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
import org.activiti.api.process.model.payloads.ReceiveMessagePayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReceiveMessagePayloadBuilderDiffblueTest {
  /**
   * Test {@link ReceiveMessagePayloadBuilder#from(ReceiveMessagePayload)}.
   * <ul>
   *   <li>Given {@code messagePayload must not be null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ReceiveMessagePayloadBuilder#from(ReceiveMessagePayload)}
   */
  @Test
  @DisplayName("Test from(ReceiveMessagePayload); given 'messagePayload must not be null'")
  void testFrom_givenMessagePayloadMustNotBeNull() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("messagePayload must not be null", mock(BiFunction.class));

    // Act and Assert
    ReceiveMessagePayload buildResult = ReceiveMessagePayloadBuilder
        .from(
            new ReceiveMessagePayload("messagePayload must not be null", "messagePayload must not be null", variables))
        .build();
    assertEquals("messagePayload must not be null", buildResult.getCorrelationKey());
    assertEquals("messagePayload must not be null", buildResult.getName());
    assertTrue(buildResult.getVariables().isEmpty());
  }

  /**
   * Test {@link ReceiveMessagePayloadBuilder#from(ReceiveMessagePayload)}.
   * <ul>
   *   <li>Then return build CorrelationKey is
   * {@code messagePayload must not be null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ReceiveMessagePayloadBuilder#from(ReceiveMessagePayload)}
   */
  @Test
  @DisplayName("Test from(ReceiveMessagePayload); then return build CorrelationKey is 'messagePayload must not be null'")
  void testFrom_thenReturnBuildCorrelationKeyIsMessagePayloadMustNotBeNull() {
    // Arrange, Act and Assert
    ReceiveMessagePayload buildResult = ReceiveMessagePayloadBuilder
        .from(new ReceiveMessagePayload("messagePayload must not be null", "messagePayload must not be null",
            new HashMap<>()))
        .build();
    assertEquals("messagePayload must not be null", buildResult.getCorrelationKey());
    assertEquals("messagePayload must not be null", buildResult.getName());
    assertTrue(buildResult.getVariables().isEmpty());
  }

  /**
   * Test {@link ReceiveMessagePayloadBuilder#receive(String)}.
   * <p>
   * Method under test: {@link ReceiveMessagePayloadBuilder#receive(String)}
   */
  @Test
  @DisplayName("Test receive(String)")
  void testReceive() {
    // Arrange, Act and Assert
    ReceiveMessagePayload buildResult = ReceiveMessagePayloadBuilder.receive("Name").build();
    assertEquals("Name", buildResult.getName());
    assertNull(buildResult.getCorrelationKey());
    assertNull(buildResult.getVariables());
  }

  /**
   * Test {@link ReceiveMessagePayloadBuilder#withVariable(String, Object)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ReceiveMessagePayloadBuilder#withVariable(String, Object)}
   */
  @Test
  @DisplayName("Test withVariable(String, Object); given HashMap() computeIfPresent 'foo' and BiFunction")
  void testWithVariable_givenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("foo", mock(BiFunction.class));

    ReceiveMessagePayloadBuilder receiveMessagePayloadBuilder = new ReceiveMessagePayloadBuilder();
    receiveMessagePayloadBuilder.withVariables(variables);

    // Act and Assert
    assertSame(receiveMessagePayloadBuilder, receiveMessagePayloadBuilder.withVariable("Name", "Value"));
  }

  /**
   * Test {@link ReceiveMessagePayloadBuilder#withVariable(String, Object)}.
   * <ul>
   *   <li>Given {@link ReceiveMessagePayloadBuilder} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ReceiveMessagePayloadBuilder#withVariable(String, Object)}
   */
  @Test
  @DisplayName("Test withVariable(String, Object); given ReceiveMessagePayloadBuilder (default constructor)")
  void testWithVariable_givenReceiveMessagePayloadBuilder() {
    // Arrange
    ReceiveMessagePayloadBuilder receiveMessagePayloadBuilder = new ReceiveMessagePayloadBuilder();

    // Act and Assert
    assertSame(receiveMessagePayloadBuilder, receiveMessagePayloadBuilder.withVariable("Name", "Value"));
  }

  /**
   * Test {@link ReceiveMessagePayloadBuilder#withVariable(String, Object)}.
   * <ul>
   *   <li>Given {@link ReceiveMessagePayloadBuilder} (default constructor)
   * withVariables {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ReceiveMessagePayloadBuilder#withVariable(String, Object)}
   */
  @Test
  @DisplayName("Test withVariable(String, Object); given ReceiveMessagePayloadBuilder (default constructor) withVariables HashMap()")
  void testWithVariable_givenReceiveMessagePayloadBuilderWithVariablesHashMap() {
    // Arrange
    ReceiveMessagePayloadBuilder receiveMessagePayloadBuilder = new ReceiveMessagePayloadBuilder();
    receiveMessagePayloadBuilder.withVariables(new HashMap<>());

    // Act and Assert
    assertSame(receiveMessagePayloadBuilder, receiveMessagePayloadBuilder.withVariable("Name", "Value"));
  }

  /**
   * Test {@link ReceiveMessagePayloadBuilder#equals(Object)}, and
   * {@link ReceiveMessagePayloadBuilder#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ReceiveMessagePayloadBuilder#equals(Object)}
   *   <li>{@link ReceiveMessagePayloadBuilder#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ReceiveMessagePayloadBuilder receiveMessagePayloadBuilder = new ReceiveMessagePayloadBuilder();
    ReceiveMessagePayloadBuilder receiveMessagePayloadBuilder2 = new ReceiveMessagePayloadBuilder();

    // Act and Assert
    assertEquals(receiveMessagePayloadBuilder, receiveMessagePayloadBuilder2);
    int expectedHashCodeResult = receiveMessagePayloadBuilder.hashCode();
    assertEquals(expectedHashCodeResult, receiveMessagePayloadBuilder2.hashCode());
  }

  /**
   * Test {@link ReceiveMessagePayloadBuilder#equals(Object)}, and
   * {@link ReceiveMessagePayloadBuilder#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ReceiveMessagePayloadBuilder#equals(Object)}
   *   <li>{@link ReceiveMessagePayloadBuilder#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
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
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReceiveMessagePayloadBuilder#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ReceiveMessagePayloadBuilder(), 1);
  }

  /**
   * Test {@link ReceiveMessagePayloadBuilder#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReceiveMessagePayloadBuilder#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ReceiveMessagePayloadBuilder receiveMessagePayloadBuilder = new ReceiveMessagePayloadBuilder();
    receiveMessagePayloadBuilder.withName("Name");

    // Act and Assert
    assertNotEquals(receiveMessagePayloadBuilder, new ReceiveMessagePayloadBuilder());
  }

  /**
   * Test {@link ReceiveMessagePayloadBuilder#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReceiveMessagePayloadBuilder#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    ReceiveMessagePayloadBuilder receiveMessagePayloadBuilder = new ReceiveMessagePayloadBuilder();
    receiveMessagePayloadBuilder.withVariables(new HashMap<>());

    // Act and Assert
    assertNotEquals(receiveMessagePayloadBuilder, new ReceiveMessagePayloadBuilder());
  }

  /**
   * Test {@link ReceiveMessagePayloadBuilder#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReceiveMessagePayloadBuilder#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    ReceiveMessagePayloadBuilder receiveMessagePayloadBuilder = new ReceiveMessagePayloadBuilder();
    receiveMessagePayloadBuilder.withCorrelationKey("Correlation Key");

    // Act and Assert
    assertNotEquals(receiveMessagePayloadBuilder, new ReceiveMessagePayloadBuilder());
  }

  /**
   * Test {@link ReceiveMessagePayloadBuilder#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReceiveMessagePayloadBuilder#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("foo", mock(BiFunction.class));

    ReceiveMessagePayloadBuilder receiveMessagePayloadBuilder = new ReceiveMessagePayloadBuilder();
    receiveMessagePayloadBuilder.withVariables(variables);

    // Act and Assert
    assertNotEquals(receiveMessagePayloadBuilder, new ReceiveMessagePayloadBuilder());
  }

  /**
   * Test {@link ReceiveMessagePayloadBuilder#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReceiveMessagePayloadBuilder#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ReceiveMessagePayloadBuilder(), null);
  }

  /**
   * Test {@link ReceiveMessagePayloadBuilder#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReceiveMessagePayloadBuilder#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ReceiveMessagePayloadBuilder(), "Different type to ReceiveMessagePayloadBuilder");
  }

  /**
   * Test {@link ReceiveMessagePayloadBuilder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ReceiveMessagePayloadBuilder#build()}
   *   <li>default or parameterless constructor of
   * {@link ReceiveMessagePayloadBuilder}
   *   <li>{@link ReceiveMessagePayloadBuilder#withCorrelationKey(String)}
   *   <li>{@link ReceiveMessagePayloadBuilder#withName(String)}
   *   <li>{@link ReceiveMessagePayloadBuilder#withVariables(Map)}
   * </ul>
   */
  @Test
  @DisplayName("Test build()")
  void testBuild() {
    // Arrange
    ReceiveMessagePayloadBuilder withVariableResult = (new ReceiveMessagePayloadBuilder())
        .withCorrelationKey("Correlation Key")
        .withName("Name")
        .withVariable("Name", "Value");
    HashMap<String, Object> variables = new HashMap<>();

    // Act
    ReceiveMessagePayload actualBuildResult = withVariableResult.withVariables(variables).build();

    // Assert
    assertEquals("Correlation Key", actualBuildResult.getCorrelationKey());
    assertEquals("Name", actualBuildResult.getName());
    Map<String, Object> variables2 = actualBuildResult.getVariables();
    assertTrue(variables2.isEmpty());
    assertSame(variables, variables2);
  }
}
