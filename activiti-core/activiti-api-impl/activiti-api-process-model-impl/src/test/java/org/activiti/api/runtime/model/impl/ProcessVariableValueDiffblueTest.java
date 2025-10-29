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
import org.junit.jupiter.api.Test;

class ProcessVariableValueDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ProcessVariableValue#equals(Object)}
   *   <li>{@link ProcessVariableValue#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link ProcessVariableValue#equals(Object)}
   *   <li>{@link ProcessVariableValue#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ProcessVariableValue processVariableValue = new ProcessVariableValue("Type", "42");

    // Act and Assert
    assertEquals(processVariableValue, processVariableValue);
    int expectedHashCodeResult = processVariableValue.hashCode();
    assertEquals(expectedHashCodeResult, processVariableValue.hashCode());
  }

  /**
   * Method under test: {@link ProcessVariableValue#toMap()}
   */
  @Test
  void testToMap() {
    // Arrange and Act
    Map<String, String> actualToMapResult = (new ProcessVariableValue("Type", "42")).toMap();

    // Assert
    assertEquals(2, actualToMapResult.size());
    assertEquals("42", actualToMapResult.get("value"));
    assertEquals("Type", actualToMapResult.get("type"));
  }

  /**
   * Method under test: {@link ProcessVariableValue#toJson()}
   */
  @Test
  void testToJson() {
    // Arrange, Act and Assert
    assertEquals("{\"type\":\"Type\",\"value\":\"42\"}", (new ProcessVariableValue("Type", "42")).toJson());
    assertEquals("{\"type\":\"Type\",\"value\":\"{\\\"type\\\":\\\"\"}",
        (new ProcessVariableValue("Type", "{\"type\":\"")).toJson());
    assertEquals("{\"type\":\"Type\",\"value\":\"\\\\'\"}", (new ProcessVariableValue("Type", "\\'")).toJson());
    assertEquals("{\"type\":\"null\",\"value\":null}", (new ProcessVariableValue()).toJson());
  }

  /**
   * Method under test: {@link ProcessVariableValue#builder()}
   */
  @Test
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

  /**
   * Method under test: {@link ProcessVariableValue#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ProcessVariableValue processVariableValue = new ProcessVariableValue(null, "42");

    // Act and Assert
    assertNotEquals(processVariableValue, new ProcessVariableValue("Type", "42"));
  }

  /**
   * Method under test: {@link ProcessVariableValue#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ProcessVariableValue processVariableValue = new ProcessVariableValue("Type", "Value");

    // Act and Assert
    assertNotEquals(processVariableValue, new ProcessVariableValue("Type", "42"));
  }

  /**
   * Method under test: {@link ProcessVariableValue#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ProcessVariableValue("Type", "42"), null);
  }

  /**
   * Method under test: {@link ProcessVariableValue#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new ProcessVariableValue("Type", "42"), "Different type to ProcessVariableValue");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ProcessVariableValue#ProcessVariableValue()}
   *   <li>{@link ProcessVariableValue#toString()}
   *   <li>{@link ProcessVariableValue#getType()}
   *   <li>{@link ProcessVariableValue#getValue()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
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
   * Methods under test:
   * <ul>
   *   <li>{@link ProcessVariableValue#ProcessVariableValue(String, String)}
   *   <li>{@link ProcessVariableValue#toString()}
   *   <li>{@link ProcessVariableValue#getType()}
   *   <li>{@link ProcessVariableValue#getValue()}
   * </ul>
   */
  @Test
  void testGettersAndSetters2() {
    // Arrange and Act
    ProcessVariableValue actualProcessVariableValue = new ProcessVariableValue("Type", "42");
    String actualToStringResult = actualProcessVariableValue.toString();
    String actualType = actualProcessVariableValue.getType();

    // Assert
    assertEquals("42", actualProcessVariableValue.getValue());
    assertEquals("Type", actualType);
    assertEquals("{\"type\":\"Type\",\"value\":\"42\"}", actualToStringResult);
  }
}
