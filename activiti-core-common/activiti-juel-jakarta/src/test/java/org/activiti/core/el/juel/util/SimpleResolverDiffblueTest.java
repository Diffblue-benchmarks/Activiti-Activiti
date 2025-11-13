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
package org.activiti.core.el.juel.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.el.ArrayELResolver;
import jakarta.el.BeanNameELResolver;
import jakarta.el.BeanNameResolver;
import jakarta.el.CompositeELResolver;
import jakarta.el.ELContext;
import jakarta.el.StaticFieldELResolver;
import java.beans.FeatureDescriptor;
import java.beans.PropertyDescriptor;
import java.util.Iterator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SimpleResolverDiffblueTest {
  /**
   * Test {@link SimpleResolver#getCommonPropertyType(ELContext, Object)}.
   *
   * <p>Method under test: {@link SimpleResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getCommonPropertyType(ELContext, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class SimpleResolver.getCommonPropertyType(ELContext, Object)"})
  void testGetCommonPropertyType() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver(new SimpleResolver(), true);

    // Act
    Class<?> actualCommonPropertyType =
        simpleResolver.getCommonPropertyType(new SimpleContext(), "Base");

    // Assert
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, actualCommonPropertyType);
  }

  /**
   * Test {@link SimpleResolver#getCommonPropertyType(ELContext, Object)}.
   *
   * <ul>
   *   <li>Given {@link SimpleResolver#SimpleResolver()}.
   *   <li>When {@code null}.
   *   <li>Then return {@link Object}.
   * </ul>
   *
   * <p>Method under test: {@link SimpleResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  @DisplayName(
      "Test getCommonPropertyType(ELContext, Object); given SimpleResolver(); when 'null'; then return Object")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class SimpleResolver.getCommonPropertyType(ELContext, Object)"})
  void testGetCommonPropertyType_givenSimpleResolver_whenNull_thenReturnObject() {
    // Arrange and Act
    Class<?> actualCommonPropertyType = new SimpleResolver().getCommonPropertyType(null, "Base");

    // Assert
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, actualCommonPropertyType);
  }

  /**
   * Test {@link SimpleResolver#getCommonPropertyType(ELContext, Object)}.
   *
   * <ul>
   *   <li>Given {@link SimpleResolver#SimpleResolver()}.
   *   <li>When {@link SimpleContext#SimpleContext()}.
   *   <li>Then return {@link Object}.
   * </ul>
   *
   * <p>Method under test: {@link SimpleResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  @DisplayName(
      "Test getCommonPropertyType(ELContext, Object); given SimpleResolver(); when SimpleContext(); then return Object")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class SimpleResolver.getCommonPropertyType(ELContext, Object)"})
  void testGetCommonPropertyType_givenSimpleResolver_whenSimpleContext_thenReturnObject() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver();

    // Act
    Class<?> actualCommonPropertyType =
        simpleResolver.getCommonPropertyType(new SimpleContext(), "Base");

    // Assert
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, actualCommonPropertyType);
  }

  /**
   * Test {@link SimpleResolver#getCommonPropertyType(ELContext, Object)}.
   *
   * <ul>
   *   <li>Then return {@link String}.
   * </ul>
   *
   * <p>Method under test: {@link SimpleResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getCommonPropertyType(ELContext, Object); then return String")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class SimpleResolver.getCommonPropertyType(ELContext, Object)"})
  void testGetCommonPropertyType_thenReturnString() {
    // Arrange
    BeanNameELResolver resolver = new BeanNameELResolver(mock(BeanNameResolver.class));

    // Act
    Class<?> actualCommonPropertyType =
        new SimpleResolver(resolver, true).getCommonPropertyType(null, "Base");

    // Assert
    Class<String> expectedCommonPropertyType = String.class;
    assertEquals(expectedCommonPropertyType, actualCommonPropertyType);
  }

  /**
   * Test {@link SimpleResolver#getFeatureDescriptors(ELContext, Object)}.
   *
   * <p>Method under test: {@link SimpleResolver#getFeatureDescriptors(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getFeatureDescriptors(ELContext, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Iterator SimpleResolver.getFeatureDescriptors(ELContext, Object)"})
  void testGetFeatureDescriptors() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver();

    // Act
    Iterator<FeatureDescriptor> actualFeatureDescriptors =
        simpleResolver.getFeatureDescriptors(new SimpleContext(), "Base");

    // Assert
    assertTrue(actualFeatureDescriptors.next() instanceof PropertyDescriptor);
    assertTrue(actualFeatureDescriptors.next() instanceof PropertyDescriptor);
    assertTrue(actualFeatureDescriptors.next() instanceof PropertyDescriptor);
    assertTrue(actualFeatureDescriptors.next() instanceof PropertyDescriptor);
    assertFalse(actualFeatureDescriptors.hasNext());
  }

  /**
   * Test {@link SimpleResolver#getType(ELContext, Object, Object)}.
   *
   * <p>Method under test: {@link SimpleResolver#getType(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getType(ELContext, Object, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class SimpleResolver.getType(ELContext, Object, Object)"})
  void testGetType() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver(new CompositeELResolver(), true);

    // Act and Assert
    assertNull(simpleResolver.getType(new SimpleContext(), "Base", "Property"));
  }

  /**
   * Test {@link SimpleResolver#getType(ELContext, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link SimpleResolver#SimpleResolver(ELResolver, boolean)} with resolver is {@link
   *       ArrayELResolver#ArrayELResolver()} and readOnly is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link SimpleResolver#getType(ELContext, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test getType(ELContext, Object, Object); given SimpleResolver(ELResolver, boolean) with resolver is ArrayELResolver() and readOnly is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class SimpleResolver.getType(ELContext, Object, Object)"})
  void testGetType_givenSimpleResolverWithResolverIsArrayELResolverAndReadOnlyIsTrue() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver(new ArrayELResolver(), true);

    // Act and Assert
    assertNull(simpleResolver.getType(new SimpleContext(), "Base", "Property"));
  }

  /**
   * Test {@link SimpleResolver#getValue(ELContext, Object, Object)}.
   *
   * <p>Method under test: {@link SimpleResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getValue(ELContext, Object, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object SimpleResolver.getValue(ELContext, Object, Object)"})
  void testGetValue() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver(new CompositeELResolver(), true);

    // Act and Assert
    assertNull(simpleResolver.getValue(new SimpleContext(), "Base", "Property"));
  }

  /**
   * Test {@link SimpleResolver#getValue(ELContext, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link SimpleResolver#SimpleResolver(ELResolver, boolean)} with resolver is {@link
   *       ArrayELResolver#ArrayELResolver()} and readOnly is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link SimpleResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test getValue(ELContext, Object, Object); given SimpleResolver(ELResolver, boolean) with resolver is ArrayELResolver() and readOnly is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object SimpleResolver.getValue(ELContext, Object, Object)"})
  void testGetValue_givenSimpleResolverWithResolverIsArrayELResolverAndReadOnlyIsTrue() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver(new ArrayELResolver(), true);

    // Act and Assert
    assertNull(simpleResolver.getValue(new SimpleContext(), "Base", "Property"));
  }

  /**
   * Test {@link SimpleResolver#isReadOnly(ELContext, Object, Object)}.
   *
   * <ul>
   *   <li>Then not {@link SimpleContext#SimpleContext()} PropertyResolved.
   * </ul>
   *
   * <p>Method under test: {@link SimpleResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test isReadOnly(ELContext, Object, Object); then not SimpleContext() PropertyResolved")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean SimpleResolver.isReadOnly(ELContext, Object, Object)"})
  void testIsReadOnly_thenNotSimpleContextPropertyResolved() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver(new CompositeELResolver(), true);
    SimpleContext context = new SimpleContext();

    // Act
    boolean actualIsReadOnlyResult = simpleResolver.isReadOnly(context, "Base", "Property");

    // Assert
    assertFalse(context.isPropertyResolved());
    assertFalse(actualIsReadOnlyResult);
  }

  /**
   * Test {@link SimpleResolver#isReadOnly(ELContext, Object, Object)}.
   *
   * <ul>
   *   <li>Then {@link SimpleContext#SimpleContext()} PropertyResolved.
   * </ul>
   *
   * <p>Method under test: {@link SimpleResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test isReadOnly(ELContext, Object, Object); then SimpleContext() PropertyResolved")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean SimpleResolver.isReadOnly(ELContext, Object, Object)"})
  void testIsReadOnly_thenSimpleContextPropertyResolved() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver(true);
    SimpleContext context = new SimpleContext();

    // Act
    boolean actualIsReadOnlyResult = simpleResolver.isReadOnly(context, "Base", "Property");

    // Assert
    assertTrue(context.isPropertyResolved());
    assertTrue(actualIsReadOnlyResult);
  }

  /**
   * Test {@link SimpleResolver#setValue(ELContext, Object, Object, Object)}.
   *
   * <p>Method under test: {@link SimpleResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName("Test setValue(ELContext, Object, Object, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SimpleResolver.setValue(ELContext, Object, Object, Object)"})
  void testSetValue() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver(new CompositeELResolver(), true);
    SimpleContext context = new SimpleContext();

    // Act
    simpleResolver.setValue(context, "Base", "Property", "Value");

    // Assert that nothing has changed
    assertFalse(context.isPropertyResolved());
  }

  /**
   * Test {@link SimpleResolver#setValue(ELContext, Object, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link SimpleResolver#SimpleResolver(ELResolver, boolean)} with resolver is {@link
   *       ArrayELResolver#ArrayELResolver()} and readOnly is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link SimpleResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test setValue(ELContext, Object, Object, Object); given SimpleResolver(ELResolver, boolean) with resolver is ArrayELResolver() and readOnly is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SimpleResolver.setValue(ELContext, Object, Object, Object)"})
  void testSetValue_givenSimpleResolverWithResolverIsArrayELResolverAndReadOnlyIsTrue() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver(new ArrayELResolver(), true);
    SimpleContext context = new SimpleContext();

    // Act
    simpleResolver.setValue(context, "Base", "Property", "Value");

    // Assert that nothing has changed
    assertFalse(context.isPropertyResolved());
  }

  /**
   * Test {@link SimpleResolver#setValue(ELContext, Object, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link SimpleResolver#SimpleResolver(ELResolver, boolean)} with resolver is {@link
   *       SimpleResolver#SimpleResolver()} and readOnly is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link SimpleResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test setValue(ELContext, Object, Object, Object); given SimpleResolver(ELResolver, boolean) with resolver is SimpleResolver() and readOnly is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SimpleResolver.setValue(ELContext, Object, Object, Object)"})
  void testSetValue_givenSimpleResolverWithResolverIsSimpleResolverAndReadOnlyIsTrue() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver(new SimpleResolver(), true);
    SimpleContext context = new SimpleContext();

    // Act
    simpleResolver.setValue(context, "Base", null, "Value");

    // Assert that nothing has changed
    assertFalse(context.isPropertyResolved());
  }

  /**
   * Test {@link SimpleResolver#setValue(ELContext, Object, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link SimpleResolver#SimpleResolver()}.
   *   <li>When {@code null}.
   *   <li>Then not {@link SimpleContext#SimpleContext()} PropertyResolved.
   * </ul>
   *
   * <p>Method under test: {@link SimpleResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test setValue(ELContext, Object, Object, Object); given SimpleResolver(); when 'null'; then not SimpleContext() PropertyResolved")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SimpleResolver.setValue(ELContext, Object, Object, Object)"})
  void testSetValue_givenSimpleResolver_whenNull_thenNotSimpleContextPropertyResolved() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver();
    SimpleContext context = new SimpleContext();

    // Act
    simpleResolver.setValue(context, "Base", null, "Value");

    // Assert that nothing has changed
    assertFalse(context.isPropertyResolved());
  }

  /**
   * Test {@link SimpleResolver#setValue(ELContext, Object, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link SimpleResolver#SimpleResolver()}.
   *   <li>When {@code null}.
   *   <li>Then {@link SimpleContext#SimpleContext()} PropertyResolved.
   * </ul>
   *
   * <p>Method under test: {@link SimpleResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test setValue(ELContext, Object, Object, Object); given SimpleResolver(); when 'null'; then SimpleContext() PropertyResolved")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SimpleResolver.setValue(ELContext, Object, Object, Object)"})
  void testSetValue_givenSimpleResolver_whenNull_thenSimpleContextPropertyResolved() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver();
    SimpleContext context = new SimpleContext();

    // Act
    simpleResolver.setValue(context, null, "Property", "Value");

    // Assert
    assertTrue(context.isPropertyResolved());
  }

  /**
   * Test {@link SimpleResolver#setValue(ELContext, Object, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link SimpleResolver#SimpleResolver()}.
   *   <li>When one.
   *   <li>Then not {@link SimpleContext#SimpleContext()} PropertyResolved.
   * </ul>
   *
   * <p>Method under test: {@link SimpleResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test setValue(ELContext, Object, Object, Object); given SimpleResolver(); when one; then not SimpleContext() PropertyResolved")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SimpleResolver.setValue(ELContext, Object, Object, Object)"})
  void testSetValue_givenSimpleResolver_whenOne_thenNotSimpleContextPropertyResolved() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver();
    SimpleContext context = new SimpleContext();

    // Act
    simpleResolver.setValue(context, null, 1, "Value");

    // Assert that nothing has changed
    assertFalse(context.isPropertyResolved());
  }

  /**
   * Test {@link SimpleResolver#invoke(ELContext, Object, Object, Class[], Object[])}.
   *
   * <p>Method under test: {@link SimpleResolver#invoke(ELContext, Object, Object, Class[],
   * Object[])}
   */
  @Test
  @DisplayName("Test invoke(ELContext, Object, Object, Class[], Object[])")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object SimpleResolver.invoke(ELContext, Object, Object, Class[], Object[])"})
  void testInvoke() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver(new StaticFieldELResolver(), true);
    SimpleContext context = new SimpleContext();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertNull(
        simpleResolver.invoke(
            context, "Base", "Method", new Class[] {forNameResult}, new Object[] {"Params"}));
  }

  /**
   * Test {@link SimpleResolver#invoke(ELContext, Object, Object, Class[], Object[])}.
   *
   * <ul>
   *   <li>Given {@link SimpleResolver#SimpleResolver(ELResolver, boolean)} with resolver is {@link
   *       ArrayELResolver#ArrayELResolver()} and readOnly is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link SimpleResolver#invoke(ELContext, Object, Object, Class[],
   * Object[])}
   */
  @Test
  @DisplayName(
      "Test invoke(ELContext, Object, Object, Class[], Object[]); given SimpleResolver(ELResolver, boolean) with resolver is ArrayELResolver() and readOnly is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object SimpleResolver.invoke(ELContext, Object, Object, Class[], Object[])"})
  void testInvoke_givenSimpleResolverWithResolverIsArrayELResolverAndReadOnlyIsTrue() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver(new ArrayELResolver(), true);
    SimpleContext context = new SimpleContext();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertNull(
        simpleResolver.invoke(
            context, "Base", "Method", new Class[] {forNameResult}, new Object[] {"Params"}));
  }

  /**
   * Test {@link SimpleResolver#invoke(ELContext, Object, Object, Class[], Object[])}.
   *
   * <ul>
   *   <li>Given {@link SimpleResolver#SimpleResolver(ELResolver, boolean)} with resolver is {@link
   *       CompositeELResolver} (default constructor) and readOnly is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link SimpleResolver#invoke(ELContext, Object, Object, Class[],
   * Object[])}
   */
  @Test
  @DisplayName(
      "Test invoke(ELContext, Object, Object, Class[], Object[]); given SimpleResolver(ELResolver, boolean) with resolver is CompositeELResolver (default constructor) and readOnly is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object SimpleResolver.invoke(ELContext, Object, Object, Class[], Object[])"})
  void testInvoke_givenSimpleResolverWithResolverIsCompositeELResolverAndReadOnlyIsTrue() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver(new CompositeELResolver(), true);
    SimpleContext context = new SimpleContext();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertNull(
        simpleResolver.invoke(
            context, "Base", "Method", new Class[] {forNameResult}, new Object[] {"Params"}));
  }
}
