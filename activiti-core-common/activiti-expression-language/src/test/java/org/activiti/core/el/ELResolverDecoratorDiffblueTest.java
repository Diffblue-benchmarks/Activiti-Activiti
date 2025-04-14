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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.el.CompositeELResolver;
import jakarta.el.ELContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ELResolverDecoratorDiffblueTest {
  /**
   * Test {@link ELResolverDecorator#getValue(ELContext, Object, Object)}.
   * <p>
   * Method under test: {@link ELResolverDecorator#getValue(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getValue(ELContext, Object, Object)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object ELResolverDecorator.getValue(ELContext, Object, Object)"})
  void testGetValue() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new ELResolverReflectionBlockerDecorator(new JsonNodeELResolver()));

    // Act and Assert
    assertNull(elResolverReflectionBlockerDecorator.getValue(new ActivitiElContext(), "Base", "Property"));
  }

  /**
   * Test {@link ELResolverDecorator#getValue(ELContext, Object, Object)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ELResolverDecorator#getValue(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getValue(ELContext, Object, Object); then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object ELResolverDecorator.getValue(ELContext, Object, Object)"})
  void testGetValue_thenReturnNull() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new JsonNodeELResolver());

    // Act and Assert
    assertNull(elResolverReflectionBlockerDecorator.getValue(new ActivitiElContext(), "Base", "Property"));
  }

  /**
   * Test {@link ELResolverDecorator#getType(ELContext, Object, Object)}.
   * <p>
   * Method under test: {@link ELResolverDecorator#getType(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getType(ELContext, Object, Object)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Class ELResolverDecorator.getType(ELContext, Object, Object)"})
  void testGetType() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new ELResolverReflectionBlockerDecorator(new JsonNodeELResolver()));

    // Act and Assert
    assertNull(elResolverReflectionBlockerDecorator.getType(new ActivitiElContext(), "Base", "Property"));
  }

  /**
   * Test {@link ELResolverDecorator#getType(ELContext, Object, Object)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ELResolverDecorator#getType(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getType(ELContext, Object, Object); then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Class ELResolverDecorator.getType(ELContext, Object, Object)"})
  void testGetType_thenReturnNull() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new JsonNodeELResolver());

    // Act and Assert
    assertNull(elResolverReflectionBlockerDecorator.getType(new ActivitiElContext(), "Base", "Property"));
  }

  /**
   * Test {@link ELResolverDecorator#invoke(ELContext, Object, Object, Class[], Object[])}.
   * <p>
   * Method under test: {@link ELResolverDecorator#invoke(ELContext, Object, Object, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(ELContext, Object, Object, Class[], Object[])")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object ELResolverDecorator.invoke(ELContext, Object, Object, Class[], Object[])"})
  void testInvoke() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new JsonNodeELResolver());
    ActivitiElContext context = new ActivitiElContext();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertNull(elResolverReflectionBlockerDecorator.invoke(context, "Base", "Method", new Class[]{forNameResult},
        new Object[]{"Params"}));
  }

  /**
   * Test {@link ELResolverDecorator#invoke(ELContext, Object, Object, Class[], Object[])}.
   * <p>
   * Method under test: {@link ELResolverDecorator#invoke(ELContext, Object, Object, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(ELContext, Object, Object, Class[], Object[])")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object ELResolverDecorator.invoke(ELContext, Object, Object, Class[], Object[])"})
  void testInvoke2() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new CompositeELResolver());
    ActivitiElContext context = new ActivitiElContext();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertNull(elResolverReflectionBlockerDecorator.invoke(context, "Base", "Method", new Class[]{forNameResult},
        new Object[]{"Params"}));
  }

  /**
   * Test {@link ELResolverDecorator#invoke(ELContext, Object, Object, Class[], Object[])}.
   * <p>
   * Method under test: {@link ELResolverDecorator#invoke(ELContext, Object, Object, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(ELContext, Object, Object, Class[], Object[])")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object ELResolverDecorator.invoke(ELContext, Object, Object, Class[], Object[])"})
  void testInvoke3() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new ELResolverReflectionBlockerDecorator(new JsonNodeELResolver()));
    ActivitiElContext context = new ActivitiElContext();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertNull(elResolverReflectionBlockerDecorator.invoke(context, "Base", "Method", new Class[]{forNameResult},
        new Object[]{"Params"}));
  }

  /**
   * Test {@link ELResolverDecorator#invoke(ELContext, Object, Object, Class[], Object[])}.
   * <p>
   * Method under test: {@link ELResolverDecorator#invoke(ELContext, Object, Object, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(ELContext, Object, Object, Class[], Object[])")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object ELResolverDecorator.invoke(ELContext, Object, Object, Class[], Object[])"})
  void testInvoke4() {
    // Arrange
    CompositeELResolver resolver = new CompositeELResolver();
    resolver.add(new JsonNodeELResolver());
    resolver.add(new ELResolverReflectionBlockerDecorator(new JsonNodeELResolver()));
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        resolver);
    ActivitiElContext context = new ActivitiElContext();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertNull(elResolverReflectionBlockerDecorator.invoke(context, "Base", "Method", new Class[]{forNameResult},
        new Object[]{"Params"}));
  }

  /**
   * Test {@link ELResolverDecorator#invoke(ELContext, Object, Object, Class[], Object[])}.
   * <ul>
   *   <li>Given {@link CompositeELResolver} (default constructor) add {@link JsonNodeELResolver#JsonNodeELResolver()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ELResolverDecorator#invoke(ELContext, Object, Object, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(ELContext, Object, Object, Class[], Object[]); given CompositeELResolver (default constructor) add JsonNodeELResolver(); then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object ELResolverDecorator.invoke(ELContext, Object, Object, Class[], Object[])"})
  void testInvoke_givenCompositeELResolverAddJsonNodeELResolver_thenReturnNull() {
    // Arrange
    CompositeELResolver resolver = new CompositeELResolver();
    resolver.add(new JsonNodeELResolver());
    resolver.add(new JsonNodeELResolver());
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        resolver);
    ActivitiElContext context = new ActivitiElContext();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertNull(elResolverReflectionBlockerDecorator.invoke(context, "Base", "Method", new Class[]{forNameResult},
        new Object[]{"Params"}));
  }

  /**
   * Test {@link ELResolverDecorator#isReadOnly(ELContext, Object, Object)}.
   * <p>
   * Method under test: {@link ELResolverDecorator#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test isReadOnly(ELContext, Object, Object)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ELResolverDecorator.isReadOnly(ELContext, Object, Object)"})
  void testIsReadOnly() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new ELResolverReflectionBlockerDecorator(new JsonNodeELResolver()));

    // Act and Assert
    assertFalse(elResolverReflectionBlockerDecorator.isReadOnly(new ActivitiElContext(), "Base", "Property"));
  }

  /**
   * Test {@link ELResolverDecorator#isReadOnly(ELContext, Object, Object)}.
   * <ul>
   *   <li>Given {@link JsonNodeELResolver#JsonNodeELResolver(boolean)} with readOnly is {@code true}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ELResolverDecorator#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test isReadOnly(ELContext, Object, Object); given JsonNodeELResolver(boolean) with readOnly is 'true'; then return 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ELResolverDecorator.isReadOnly(ELContext, Object, Object)"})
  void testIsReadOnly_givenJsonNodeELResolverWithReadOnlyIsTrue_thenReturnTrue() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new JsonNodeELResolver(true));

    // Act and Assert
    assertTrue(elResolverReflectionBlockerDecorator.isReadOnly(new ActivitiElContext(), "Base", "Property"));
  }

  /**
   * Test {@link ELResolverDecorator#isReadOnly(ELContext, Object, Object)}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ELResolverDecorator#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test isReadOnly(ELContext, Object, Object); then return 'false'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ELResolverDecorator.isReadOnly(ELContext, Object, Object)"})
  void testIsReadOnly_thenReturnFalse() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new JsonNodeELResolver());

    // Act and Assert
    assertFalse(elResolverReflectionBlockerDecorator.isReadOnly(new ActivitiElContext(), "Base", "Property"));
  }

  /**
   * Test {@link ELResolverDecorator#getFeatureDescriptors(ELContext, Object)}.
   * <p>
   * Method under test: {@link ELResolverDecorator#getFeatureDescriptors(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getFeatureDescriptors(ELContext, Object)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.util.Iterator ELResolverDecorator.getFeatureDescriptors(ELContext, Object)"})
  void testGetFeatureDescriptors() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new ELResolverReflectionBlockerDecorator(new JsonNodeELResolver()));

    // Act and Assert
    assertNull(elResolverReflectionBlockerDecorator.getFeatureDescriptors(new ActivitiElContext(), "Base"));
  }

  /**
   * Test {@link ELResolverDecorator#getFeatureDescriptors(ELContext, Object)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ELResolverDecorator#getFeatureDescriptors(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getFeatureDescriptors(ELContext, Object); then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.util.Iterator ELResolverDecorator.getFeatureDescriptors(ELContext, Object)"})
  void testGetFeatureDescriptors_thenReturnNull() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new JsonNodeELResolver());

    // Act and Assert
    assertNull(elResolverReflectionBlockerDecorator.getFeatureDescriptors(new ActivitiElContext(), "Base"));
  }

  /**
   * Test {@link ELResolverDecorator#getCommonPropertyType(ELContext, Object)}.
   * <p>
   * Method under test: {@link ELResolverDecorator#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getCommonPropertyType(ELContext, Object)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Class ELResolverDecorator.getCommonPropertyType(ELContext, Object)"})
  void testGetCommonPropertyType() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new ELResolverReflectionBlockerDecorator(new JsonNodeELResolver()));

    // Act and Assert
    assertNull(elResolverReflectionBlockerDecorator.getCommonPropertyType(new ActivitiElContext(), "Base"));
  }

  /**
   * Test {@link ELResolverDecorator#getCommonPropertyType(ELContext, Object)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ELResolverDecorator#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getCommonPropertyType(ELContext, Object); then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Class ELResolverDecorator.getCommonPropertyType(ELContext, Object)"})
  void testGetCommonPropertyType_thenReturnNull() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new JsonNodeELResolver());

    // Act and Assert
    assertNull(elResolverReflectionBlockerDecorator.getCommonPropertyType(new ActivitiElContext(), "Base"));
  }
}
