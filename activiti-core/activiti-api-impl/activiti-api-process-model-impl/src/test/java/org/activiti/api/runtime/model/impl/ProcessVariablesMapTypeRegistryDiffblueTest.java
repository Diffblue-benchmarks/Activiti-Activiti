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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProcessVariablesMapTypeRegistryDiffblueTest {
  /**
   * Test {@link ProcessVariablesMapTypeRegistry#forType(String)} with
   * {@code type}.
   * <p>
   * Method under test: {@link ProcessVariablesMapTypeRegistry#forType(String)}
   */
  @Test
  @DisplayName("Test forType(String) with 'type'")
  void testForTypeWithType() {
    // Arrange and Act
    Class<?> actualForTypeResult = ProcessVariablesMapTypeRegistry.forType("Type");

    // Assert
    Class<ObjectValue> expectedForTypeResult = ObjectValue.class;
    assertEquals(expectedForTypeResult, actualForTypeResult);
  }

  /**
   * Test {@link ProcessVariablesMapTypeRegistry#forType(String, Class)} with
   * {@code type}, {@code defaultType}.
   * <p>
   * Method under test:
   * {@link ProcessVariablesMapTypeRegistry#forType(String, Class)}
   */
  @Test
  @DisplayName("Test forType(String, Class) with 'type', 'defaultType'")
  void testForTypeWithTypeDefaultType() {
    // Arrange
    Class<Object> defaultType = Object.class;

    // Act
    Class<?> actualForTypeResult = ProcessVariablesMapTypeRegistry.forType("Type", defaultType);

    // Assert
    Class<Object> expectedForTypeResult = Object.class;
    assertEquals(expectedForTypeResult, actualForTypeResult);
  }

  /**
   * Test {@link ProcessVariablesMapTypeRegistry#forClass(Class)}.
   * <p>
   * Method under test: {@link ProcessVariablesMapTypeRegistry#forClass(Class)}
   */
  @Test
  @DisplayName("Test forClass(Class)")
  void testForClass() {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act and Assert
    assertEquals(ProcessVariablesMapTypeRegistry.OBJECT_TYPE_KEY, ProcessVariablesMapTypeRegistry.forClass(clazz));
  }

  /**
   * Test {@link ProcessVariablesMapTypeRegistry#isScalarType(Class)}.
   * <p>
   * Method under test:
   * {@link ProcessVariablesMapTypeRegistry#isScalarType(Class)}
   */
  @Test
  @DisplayName("Test isScalarType(Class)")
  void testIsScalarType() {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act and Assert
    assertFalse(ProcessVariablesMapTypeRegistry.isScalarType(clazz));
  }

  /**
   * Test {@link ProcessVariablesMapTypeRegistry#getContainerType(Class, Object)}.
   * <p>
   * Method under test:
   * {@link ProcessVariablesMapTypeRegistry#getContainerType(Class, Object)}
   */
  @Test
  @DisplayName("Test getContainerType(Class, Object)")
  void testGetContainerType() {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act
    Optional<Class<?>> actualContainerType = ProcessVariablesMapTypeRegistry.getContainerType(clazz, "Value");

    // Assert
    assertFalse(actualContainerType.isPresent());
  }

  /**
   * Test {@link ProcessVariablesMapTypeRegistry#canConvert(Object)}.
   * <p>
   * Method under test: {@link ProcessVariablesMapTypeRegistry#canConvert(Object)}
   */
  @Test
  @DisplayName("Test canConvert(Object)")
  void testCanConvert() {
    // Arrange, Act and Assert
    assertTrue(ProcessVariablesMapTypeRegistry.canConvert("Value"));
  }

  /**
   * Test {@link ProcessVariablesMapTypeRegistry#containsType(String)}.
   * <ul>
   *   <li>When {@link ProcessVariablesMapTypeRegistry#OBJECT_TYPE_KEY}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessVariablesMapTypeRegistry#containsType(String)}
   */
  @Test
  @DisplayName("Test containsType(String); when OBJECT_TYPE_KEY; then return 'true'")
  void testContainsType_whenObject_type_key_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(ProcessVariablesMapTypeRegistry.containsType(ProcessVariablesMapTypeRegistry.OBJECT_TYPE_KEY));
  }

  /**
   * Test {@link ProcessVariablesMapTypeRegistry#containsType(String)}.
   * <ul>
   *   <li>When {@code Type}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessVariablesMapTypeRegistry#containsType(String)}
   */
  @Test
  @DisplayName("Test containsType(String); when 'Type'; then return 'false'")
  void testContainsType_whenType_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(ProcessVariablesMapTypeRegistry.containsType("Type"));
  }
}
