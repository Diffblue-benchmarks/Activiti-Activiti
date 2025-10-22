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
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.el.ELContext;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ReadOnlyMapELResolverDiffblueTest {
  /**
   * Test {@link ReadOnlyMapELResolver#ReadOnlyMapELResolver(Map)}.
   * <p>
   * Method under test: {@link ReadOnlyMapELResolver#ReadOnlyMapELResolver(Map)}
   */
  @Test
  @DisplayName("Test new ReadOnlyMapELResolver(Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ReadOnlyMapELResolver.<init>(Map)"})
  void testNewReadOnlyMapELResolver() {
    // Arrange, Act and Assert
    assertTrue((new ReadOnlyMapELResolver(new HashMap<>())).wrappedMap.isEmpty());
  }

  /**
   * Test {@link ReadOnlyMapELResolver#getValue(ELContext, Object, Object)}.
   * <ul>
   *   <li>When {@code Base}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReadOnlyMapELResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getValue(ELContext, Object, Object); when 'Base'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object ReadOnlyMapELResolver.getValue(ELContext, Object, Object)"})
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
   * Method under test: {@link ReadOnlyMapELResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getValue(ELContext, Object, Object); when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object ReadOnlyMapELResolver.getValue(ELContext, Object, Object)"})
  void testGetValue_whenNull() {
    // Arrange
    ReadOnlyMapELResolver readOnlyMapELResolver = new ReadOnlyMapELResolver(new HashMap<>());

    // Act and Assert
    assertNull(readOnlyMapELResolver.getValue(new ActivitiElContext(), null, "Property"));
  }

  /**
   * Test {@link ReadOnlyMapELResolver#isReadOnly(ELContext, Object, Object)}.
   * <p>
   * Method under test: {@link ReadOnlyMapELResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test isReadOnly(ELContext, Object, Object)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ReadOnlyMapELResolver.isReadOnly(ELContext, Object, Object)"})
  void testIsReadOnly() {
    // Arrange
    ReadOnlyMapELResolver readOnlyMapELResolver = new ReadOnlyMapELResolver(new HashMap<>());

    // Act and Assert
    assertTrue(readOnlyMapELResolver.isReadOnly(new ActivitiElContext(), "Base", "Property"));
  }

  /**
   * Test {@link ReadOnlyMapELResolver#getCommonPropertyType(ELContext, Object)}.
   * <p>
   * Method under test: {@link ReadOnlyMapELResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getCommonPropertyType(ELContext, Object)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Class ReadOnlyMapELResolver.getCommonPropertyType(ELContext, Object)"})
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
   * Test {@link ReadOnlyMapELResolver#getFeatureDescriptors(ELContext, Object)}.
   * <p>
   * Method under test: {@link ReadOnlyMapELResolver#getFeatureDescriptors(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getFeatureDescriptors(ELContext, Object)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.util.Iterator ReadOnlyMapELResolver.getFeatureDescriptors(ELContext, Object)"})
  void testGetFeatureDescriptors() {
    // Arrange
    ReadOnlyMapELResolver readOnlyMapELResolver = new ReadOnlyMapELResolver(new HashMap<>());

    // Act and Assert
    assertNull(readOnlyMapELResolver.getFeatureDescriptors(new ActivitiElContext(), "Arg"));
  }

  /**
   * Test {@link ReadOnlyMapELResolver#getType(ELContext, Object, Object)}.
   * <p>
   * Method under test: {@link ReadOnlyMapELResolver#getType(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getType(ELContext, Object, Object)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Class ReadOnlyMapELResolver.getType(ELContext, Object, Object)"})
  void testGetType() {
    // Arrange
    ReadOnlyMapELResolver readOnlyMapELResolver = new ReadOnlyMapELResolver(new HashMap<>());

    // Act
    Class<?> actualType = readOnlyMapELResolver.getType(new ActivitiElContext(), "Arg1", "Arg2");

    // Assert
    Class<Object> expectedType = Object.class;
    assertEquals(expectedType, actualType);
  }
}
