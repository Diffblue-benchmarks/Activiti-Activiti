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
import org.junit.jupiter.api.Test;

class ProcessVariablesMapTypeRegistryDiffblueTest {
  /**
   * Method under test: {@link ProcessVariablesMapTypeRegistry#forType(String)}
   */
  @Test
  void testForType() {
    // Arrange and Act
    Class<?> actualForTypeResult = ProcessVariablesMapTypeRegistry.forType("Type");

    // Assert
    Class<ObjectValue> expectedForTypeResult = ObjectValue.class;
    assertEquals(expectedForTypeResult, actualForTypeResult);
  }

  /**
   * Method under test:
   * {@link ProcessVariablesMapTypeRegistry#forType(String, Class)}
   */
  @Test
  void testForType2() {
    // Arrange
    Class<Object> defaultType = Object.class;

    // Act
    Class<?> actualForTypeResult = ProcessVariablesMapTypeRegistry.forType("Type", defaultType);

    // Assert
    Class<Object> expectedForTypeResult = Object.class;
    assertEquals(expectedForTypeResult, actualForTypeResult);
  }

  /**
   * Method under test: {@link ProcessVariablesMapTypeRegistry#forClass(Class)}
   */
  @Test
  void testForClass() {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act and Assert
    assertEquals(ProcessVariablesMapTypeRegistry.OBJECT_TYPE_KEY, ProcessVariablesMapTypeRegistry.forClass(clazz));
  }

  /**
   * Method under test:
   * {@link ProcessVariablesMapTypeRegistry#isScalarType(Class)}
   */
  @Test
  void testIsScalarType() {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act and Assert
    assertFalse(ProcessVariablesMapTypeRegistry.isScalarType(clazz));
  }

  /**
   * Method under test:
   * {@link ProcessVariablesMapTypeRegistry#getContainerType(Class, Object)}
   */
  @Test
  void testGetContainerType() {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act
    Optional<Class<?>> actualContainerType = ProcessVariablesMapTypeRegistry.getContainerType(clazz, "Value");

    // Assert
    assertFalse(actualContainerType.isPresent());
  }

  /**
   * Method under test: {@link ProcessVariablesMapTypeRegistry#canConvert(Object)}
   */
  @Test
  void testCanConvert() {
    // Arrange, Act and Assert
    assertTrue(ProcessVariablesMapTypeRegistry.canConvert("Value"));
  }

  /**
   * Method under test:
   * {@link ProcessVariablesMapTypeRegistry#containsType(String)}
   */
  @Test
  void testContainsType() {
    // Arrange, Act and Assert
    assertFalse(ProcessVariablesMapTypeRegistry.containsType("Type"));
    assertTrue(ProcessVariablesMapTypeRegistry.containsType(ProcessVariablesMapTypeRegistry.OBJECT_TYPE_KEY));
  }
}
