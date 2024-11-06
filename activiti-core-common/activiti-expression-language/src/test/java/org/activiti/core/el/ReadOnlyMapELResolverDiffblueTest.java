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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReadOnlyMapELResolverDiffblueTest {
  /**
   * Test {@link ReadOnlyMapELResolver#ReadOnlyMapELResolver(Map)}.
   * <p>
   * Method under test: {@link ReadOnlyMapELResolver#ReadOnlyMapELResolver(Map)}
   */
  @Test
  @DisplayName("Test new ReadOnlyMapELResolver(Map)")
  void testNewReadOnlyMapELResolver() {
    // Arrange, Act and Assert
    assertTrue((new ReadOnlyMapELResolver(new HashMap<>())).wrappedMap.isEmpty());
  }

  /**
   * Test {@link ReadOnlyMapELResolver#getValue(ELContext, Object, Object)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code 42} and
   * {@link BiFunction}.</li>
   *   <li>When {@code Base}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ReadOnlyMapELResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getValue(ELContext, Object, Object); given HashMap() computeIfPresent '42' and BiFunction; when 'Base'")
  void testGetValue_givenHashMapComputeIfPresent42AndBiFunction_whenBase() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.computeIfPresent("42", mock(BiFunction.class));
    ReadOnlyMapELResolver readOnlyMapELResolver = new ReadOnlyMapELResolver(map);

    // Act and Assert
    assertNull(readOnlyMapELResolver.getValue(new ActivitiElContext(), "Base", "Property"));
  }

  /**
   * Test {@link ReadOnlyMapELResolver#getValue(ELContext, Object, Object)}.
   * <ul>
   *   <li>When {@code Base}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ReadOnlyMapELResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getValue(ELContext, Object, Object); when 'Base'")
  void testGetValue_whenBase() {
    // Arrange
    ReadOnlyMapELResolver readOnlyMapELResolver = new ReadOnlyMapELResolver(new HashMap<>());

    // Act and Assert
    assertNull(readOnlyMapELResolver.getValue(new ActivitiElContext(), "Base", "Property"));
  }

  /**
   * Test {@link ReadOnlyMapELResolver#getValue(ELContext, Object, Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ReadOnlyMapELResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getValue(ELContext, Object, Object); when 'null'")
  void testGetValue_whenNull() {
    // Arrange
    ReadOnlyMapELResolver readOnlyMapELResolver = new ReadOnlyMapELResolver(new HashMap<>());

    // Act and Assert
    assertNull(readOnlyMapELResolver.getValue(new ActivitiElContext(), null, "Property"));
  }

  /**
   * Test {@link ReadOnlyMapELResolver#isReadOnly(ELContext, Object, Object)}.
   * <p>
   * Method under test:
   * {@link ReadOnlyMapELResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test isReadOnly(ELContext, Object, Object)")
  void testIsReadOnly() {
    // Arrange
    ReadOnlyMapELResolver readOnlyMapELResolver = new ReadOnlyMapELResolver(new HashMap<>());

    // Act and Assert
    assertTrue(readOnlyMapELResolver.isReadOnly(new ActivitiElContext(), "Base", "Property"));
  }

  /**
   * Test {@link ReadOnlyMapELResolver#isReadOnly(ELContext, Object, Object)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code 42} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ReadOnlyMapELResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test isReadOnly(ELContext, Object, Object); given HashMap() computeIfPresent '42' and BiFunction")
  void testIsReadOnly_givenHashMapComputeIfPresent42AndBiFunction() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.computeIfPresent("42", mock(BiFunction.class));
    ReadOnlyMapELResolver readOnlyMapELResolver = new ReadOnlyMapELResolver(map);

    // Act and Assert
    assertTrue(readOnlyMapELResolver.isReadOnly(new ActivitiElContext(), "Base", "Property"));
  }

  /**
   * Test {@link ReadOnlyMapELResolver#getCommonPropertyType(ELContext, Object)}.
   * <p>
   * Method under test:
   * {@link ReadOnlyMapELResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getCommonPropertyType(ELContext, Object)")
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
   * Test {@link ReadOnlyMapELResolver#getCommonPropertyType(ELContext, Object)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code 42} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ReadOnlyMapELResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getCommonPropertyType(ELContext, Object); given HashMap() computeIfPresent '42' and BiFunction")
  void testGetCommonPropertyType_givenHashMapComputeIfPresent42AndBiFunction() {
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
   * Test {@link ReadOnlyMapELResolver#getFeatureDescriptors(ELContext, Object)}.
   * <p>
   * Method under test:
   * {@link ReadOnlyMapELResolver#getFeatureDescriptors(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getFeatureDescriptors(ELContext, Object)")
  void testGetFeatureDescriptors() {
    // Arrange
    ReadOnlyMapELResolver readOnlyMapELResolver = new ReadOnlyMapELResolver(new HashMap<>());

    // Act and Assert
    assertNull(readOnlyMapELResolver.getFeatureDescriptors(new ActivitiElContext(), "Arg"));
  }

  /**
   * Test {@link ReadOnlyMapELResolver#getFeatureDescriptors(ELContext, Object)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code 42} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ReadOnlyMapELResolver#getFeatureDescriptors(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getFeatureDescriptors(ELContext, Object); given HashMap() computeIfPresent '42' and BiFunction")
  void testGetFeatureDescriptors_givenHashMapComputeIfPresent42AndBiFunction() {
    // Arrange
    HashMap<Object, Object> map = new HashMap<>();
    map.computeIfPresent("42", mock(BiFunction.class));
    ReadOnlyMapELResolver readOnlyMapELResolver = new ReadOnlyMapELResolver(map);

    // Act and Assert
    assertNull(readOnlyMapELResolver.getFeatureDescriptors(new ActivitiElContext(), "Arg"));
  }

  /**
   * Test {@link ReadOnlyMapELResolver#getType(ELContext, Object, Object)}.
   * <p>
   * Method under test:
   * {@link ReadOnlyMapELResolver#getType(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getType(ELContext, Object, Object)")
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
   * Test {@link ReadOnlyMapELResolver#getType(ELContext, Object, Object)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code 42} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ReadOnlyMapELResolver#getType(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getType(ELContext, Object, Object); given HashMap() computeIfPresent '42' and BiFunction")
  void testGetType_givenHashMapComputeIfPresent42AndBiFunction() {
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
}
