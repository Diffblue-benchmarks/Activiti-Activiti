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
package org.activiti.api.runtime.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProcessVariableValueDiffblueTest {
  /**
   * Test getters and setters.
   * <ul>
   *   <li>Then return toString is {@code {"type":"null","value":null}}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ProcessVariableValue#ProcessVariableValue()}
   *   <li>{@link ProcessVariableValue#toString()}
   *   <li>{@link ProcessVariableValue#getType()}
   *   <li>{@link ProcessVariableValue#getValue()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters; then return toString is '{\"type\":\"null\",\"value\":null}'")
  void testGettersAndSetters_thenReturnToStringIsTypeNullValueNull() {
    // Arrange and Act
    ProcessVariableValue actualProcessVariableValue = new ProcessVariableValue();
    String actualToStringResult = actualProcessVariableValue.toString();
    String actualType = actualProcessVariableValue.getType();

    // Assert
    assertEquals("{\"type\":\"null\",\"value\":null}", actualToStringResult);
    assertNull(actualType);
    assertNull(actualProcessVariableValue.getValue());
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code Type}.</li>
   *   <li>Then return Value is {@code 42}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ProcessVariableValue#ProcessVariableValue(String, String)}
   *   <li>{@link ProcessVariableValue#toString()}
   *   <li>{@link ProcessVariableValue#getType()}
   *   <li>{@link ProcessVariableValue#getValue()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters; when 'Type'; then return Value is '42'")
  void testGettersAndSetters_whenType_thenReturnValueIs42() {
    // Arrange and Act
    ProcessVariableValue actualProcessVariableValue = new ProcessVariableValue("Type", "42");
    String actualToStringResult = actualProcessVariableValue.toString();
    String actualType = actualProcessVariableValue.getType();

    // Assert
    assertEquals("42", actualProcessVariableValue.getValue());
    assertEquals("Type", actualType);
    assertEquals("{\"type\":\"Type\",\"value\":\"42\"}", actualToStringResult);
  }

  /**
   * Test {@link ProcessVariableValue#equals(Object)}, and
   * {@link ProcessVariableValue#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ProcessVariableValue#equals(Object)}
   *   <li>{@link ProcessVariableValue#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ProcessVariableValue processVariableValue = new ProcessVariableValue("Type", "42");
    ProcessVariableValue processVariableValue2 = new ProcessVariableValue("Type", "42");

    // Act and Assert
    assertEquals(processVariableValue, processVariableValue2);
    int expectedHashCodeResult = processVariableValue.hashCode();
    assertEquals(expectedHashCodeResult, processVariableValue2.hashCode());
  }

  /**
   * Test {@link ProcessVariableValue#equals(Object)}, and
   * {@link ProcessVariableValue#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ProcessVariableValue#equals(Object)}
   *   <li>{@link ProcessVariableValue#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ProcessVariableValue processVariableValue = new ProcessVariableValue("Type", "42");

    // Act and Assert
    assertEquals(processVariableValue, processVariableValue);
    int expectedHashCodeResult = processVariableValue.hashCode();
    assertEquals(expectedHashCodeResult, processVariableValue.hashCode());
  }

  /**
   * Test {@link ProcessVariableValue#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessVariableValue#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ProcessVariableValue processVariableValue = new ProcessVariableValue(null, "42");

    // Act and Assert
    assertNotEquals(processVariableValue, new ProcessVariableValue("Type", "42"));
  }

  /**
   * Test {@link ProcessVariableValue#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessVariableValue#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ProcessVariableValue processVariableValue = new ProcessVariableValue("Type", "Value");

    // Act and Assert
    assertNotEquals(processVariableValue, new ProcessVariableValue("Type", "42"));
  }

  /**
   * Test {@link ProcessVariableValue#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessVariableValue#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ProcessVariableValue("Type", "42"), null);
  }

  /**
   * Test {@link ProcessVariableValue#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessVariableValue#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ProcessVariableValue("Type", "42"), "Different type to ProcessVariableValue");
  }

  /**
   * Test {@link ProcessVariableValue#toMap()}.
   * <p>
   * Method under test: {@link ProcessVariableValue#toMap()}
   */
  @Test
  @DisplayName("Test toMap()")
  void testToMap() {
    // Arrange and Act
    Map<String, String> actualToMapResult = (new ProcessVariableValue("Type", "42")).toMap();

    // Assert
    assertEquals(2, actualToMapResult.size());
    assertEquals("42", actualToMapResult.get("value"));
    assertEquals("Type", actualToMapResult.get("type"));
  }

  /**
   * Test {@link ProcessVariableValue#toJson()}.
   * <ul>
   *   <li>Given {@link ProcessVariableValue#ProcessVariableValue()}.</li>
   *   <li>Then return {@code {"type":"null","value":null}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessVariableValue#toJson()}
   */
  @Test
  @DisplayName("Test toJson(); given ProcessVariableValue(); then return '{\"type\":\"null\",\"value\":null}'")
  void testToJson_givenProcessVariableValue_thenReturnTypeNullValueNull() {
    // Arrange, Act and Assert
    assertEquals("{\"type\":\"null\",\"value\":null}", (new ProcessVariableValue()).toJson());
  }

  /**
   * Test {@link ProcessVariableValue#toJson()}.
   * <ul>
   *   <li>Then return {@code {"type":"Type","value":"\\'"}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessVariableValue#toJson()}
   */
  @Test
  @DisplayName("Test toJson(); then return '{\"type\":\"Type\",\"value\":\"\\\\'\"}'")
  void testToJson_thenReturnTypeTypeValue() {
    // Arrange, Act and Assert
    assertEquals("{\"type\":\"Type\",\"value\":\"\\\\'\"}", (new ProcessVariableValue("Type", "\\'")).toJson());
  }

  /**
   * Test {@link ProcessVariableValue#toJson()}.
   * <ul>
   *   <li>Then return {@code {"type":"Type","value":"42"}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessVariableValue#toJson()}
   */
  @Test
  @DisplayName("Test toJson(); then return '{\"type\":\"Type\",\"value\":\"42\"}'")
  void testToJson_thenReturnTypeTypeValue42() {
    // Arrange, Act and Assert
    assertEquals("{\"type\":\"Type\",\"value\":\"42\"}", (new ProcessVariableValue("Type", "42")).toJson());
  }

  /**
   * Test {@link ProcessVariableValue#toJson()}.
   * <ul>
   *   <li>Then return {@code {"type":"Type","value":"{\"type\":\""}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessVariableValue#toJson()}
   */
  @Test
  @DisplayName("Test toJson(); then return '{\"type\":\"Type\",\"value\":\"{\\\"type\\\":\\\"\"}'")
  void testToJson_thenReturnTypeTypeValueType() {
    // Arrange, Act and Assert
    assertEquals("{\"type\":\"Type\",\"value\":\"{\\\"type\\\":\\\"\"}",
        (new ProcessVariableValue("Type", "{\"type\":\"")).toJson());
  }

  /**
   * Test {@link ProcessVariableValue#builder()}.
   * <p>
   * Method under test: {@link ProcessVariableValue#builder()}
   */
  @Test
  @DisplayName("Test builder()")
  void testBuilder() {
    // Arrange and Act
    ProcessVariableValue.ITypeStage actualBuilderResult = ProcessVariableValue.builder();
    ProcessVariableValue.IValueStage actualTypeResult = actualBuilderResult.type("Type");

    // Assert
    assertTrue(actualBuilderResult instanceof ProcessVariableValue.Builder);
    ProcessVariableValue buildResult = ((ProcessVariableValue.Builder) actualBuilderResult).build();
    assertEquals("Type", buildResult.getType());
    assertNull(buildResult.getValue());
    assertSame(actualBuilderResult, actualTypeResult);
  }
}
