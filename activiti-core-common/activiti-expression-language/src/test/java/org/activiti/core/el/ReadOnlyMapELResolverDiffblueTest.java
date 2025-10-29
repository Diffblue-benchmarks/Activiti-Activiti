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
package org.activiti.core.el;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import jakarta.el.ELContext;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.jupiter.api.Test;

class ReadOnlyMapELResolverDiffblueTest {
  /**
   * Method under test:
   * {@link ReadOnlyMapELResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  void testGetValue() {
    // Arrange
    ReadOnlyMapELResolver readOnlyMapELResolver = new ReadOnlyMapELResolver(new HashMap<>());

    // Act and Assert
    assertNull(readOnlyMapELResolver.getValue(new ActivitiElContext(), "Base", "Property"));
  }

  /**
   * Method under test:
   * {@link ReadOnlyMapELResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  void testGetValue2() {
    // Arrange
    ReadOnlyMapELResolver readOnlyMapELResolver = new ReadOnlyMapELResolver(new HashMap<>());

    // Act and Assert
    assertNull(readOnlyMapELResolver.getValue(new ActivitiElContext(), null, "Property"));
  }

  /**
   * Method under test:
   * {@link ReadOnlyMapELResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  void testGetValue3() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.computeIfPresent("42", mock(BiFunction.class));
    ReadOnlyMapELResolver readOnlyMapELResolver = new ReadOnlyMapELResolver(map);

    // Act and Assert
    assertNull(readOnlyMapELResolver.getValue(new ActivitiElContext(), "Base", "Property"));
  }

  /**
   * Method under test:
   * {@link ReadOnlyMapELResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  void testIsReadOnly() {
    // Arrange
    ReadOnlyMapELResolver readOnlyMapELResolver = new ReadOnlyMapELResolver(new HashMap<>());

    // Act and Assert
    assertTrue(readOnlyMapELResolver.isReadOnly(new ActivitiElContext(), "Base", "Property"));
  }

  /**
   * Method under test:
   * {@link ReadOnlyMapELResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  void testIsReadOnly2() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.computeIfPresent("42", mock(BiFunction.class));
    ReadOnlyMapELResolver readOnlyMapELResolver = new ReadOnlyMapELResolver(map);

    // Act and Assert
    assertTrue(readOnlyMapELResolver.isReadOnly(new ActivitiElContext(), "Base", "Property"));
  }

  /**
   * Method under test:
   * {@link ReadOnlyMapELResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  void testGetCommonPropertyType() {
    // Arrange
    ReadOnlyMapELResolver readOnlyMapELResolver = new ReadOnlyMapELResolver(new HashMap<>());

    // Act
    Class<?> actualCommonPropertyType = readOnlyMapELResolver.getCommonPropertyType(new ActivitiElContext(), "Arg");

    // Assert
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, actualCommonPropertyType);
  }

  /**
   * Method under test:
   * {@link ReadOnlyMapELResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  void testGetCommonPropertyType2() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.computeIfPresent("42", mock(BiFunction.class));
    ReadOnlyMapELResolver readOnlyMapELResolver = new ReadOnlyMapELResolver(map);

    // Act
    Class<?> actualCommonPropertyType = readOnlyMapELResolver.getCommonPropertyType(new ActivitiElContext(), "Arg");

    // Assert
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, actualCommonPropertyType);
  }

  /**
   * Method under test:
   * {@link ReadOnlyMapELResolver#getFeatureDescriptors(ELContext, Object)}
   */
  @Test
  void testGetFeatureDescriptors() {
    // Arrange
    ReadOnlyMapELResolver readOnlyMapELResolver = new ReadOnlyMapELResolver(new HashMap<>());

    // Act and Assert
    assertNull(readOnlyMapELResolver.getFeatureDescriptors(new ActivitiElContext(), "Arg"));
  }

  /**
   * Method under test:
   * {@link ReadOnlyMapELResolver#getFeatureDescriptors(ELContext, Object)}
   */
  @Test
  void testGetFeatureDescriptors2() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.computeIfPresent("42", mock(BiFunction.class));
    ReadOnlyMapELResolver readOnlyMapELResolver = new ReadOnlyMapELResolver(map);

    // Act and Assert
    assertNull(readOnlyMapELResolver.getFeatureDescriptors(new ActivitiElContext(), "Arg"));
  }

  /**
   * Method under test:
   * {@link ReadOnlyMapELResolver#getType(ELContext, Object, Object)}
   */
  @Test
  void testGetType() {
    // Arrange
    ReadOnlyMapELResolver readOnlyMapELResolver = new ReadOnlyMapELResolver(new HashMap<>());

    // Act
    Class<?> actualType = readOnlyMapELResolver.getType(new ActivitiElContext(), "Arg1", "Arg2");

    // Assert
    Class<Object> expectedType = Object.class;
    assertEquals(expectedType, actualType);
  }

  /**
   * Method under test:
   * {@link ReadOnlyMapELResolver#getType(ELContext, Object, Object)}
   */
  @Test
  void testGetType2() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.computeIfPresent("42", mock(BiFunction.class));
    ReadOnlyMapELResolver readOnlyMapELResolver = new ReadOnlyMapELResolver(map);

    // Act
    Class<?> actualType = readOnlyMapELResolver.getType(new ActivitiElContext(), "Arg1", "Arg2");

    // Assert
    Class<Object> expectedType = Object.class;
    assertEquals(expectedType, actualType);
  }

  /**
   * Method under test: {@link ReadOnlyMapELResolver#ReadOnlyMapELResolver(Map)}
   */
  @Test
  void testNewReadOnlyMapELResolver() {
    // Arrange, Act and Assert
    assertTrue((new ReadOnlyMapELResolver(new HashMap<>())).wrappedMap.isEmpty());
  }
}
