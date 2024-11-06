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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import jakarta.el.BeanNameELResolver;
import jakarta.el.BeanNameResolver;
import jakarta.el.ELContext;
import jakarta.el.StandardELContext;
import jakarta.el.StaticFieldELResolver;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SimpleResolverDiffblueTest {
  /**
   * Test {@link SimpleResolver#getCommonPropertyType(ELContext, Object)}.
   * <p>
   * Method under test:
   * {@link SimpleResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getCommonPropertyType(ELContext, Object)")
  void testGetCommonPropertyType() {
    // Arrange and Act
    Class<?> actualCommonPropertyType = (new SimpleResolver(new SimpleResolver(), true)).getCommonPropertyType(null,
        "Base");

    // Assert
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, actualCommonPropertyType);
  }

  /**
   * Test {@link SimpleResolver#getCommonPropertyType(ELContext, Object)}.
   * <ul>
   *   <li>Given {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SimpleResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getCommonPropertyType(ELContext, Object); given 'Name'")
  void testGetCommonPropertyType_givenName() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver();

    SimpleContext context = new SimpleContext();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    context.setVariable("Name", new ObjectValueExpression(converter, "Object", type));

    // Act
    Class<?> actualCommonPropertyType = simpleResolver.getCommonPropertyType(context, "Base");

    // Assert
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, actualCommonPropertyType);
  }

  /**
   * Test {@link SimpleResolver#getCommonPropertyType(ELContext, Object)}.
   * <ul>
   *   <li>Given {@link SimpleResolver#SimpleResolver()}.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@link Object}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SimpleResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getCommonPropertyType(ELContext, Object); given SimpleResolver(); when 'null'; then return Object")
  void testGetCommonPropertyType_givenSimpleResolver_whenNull_thenReturnObject() {
    // Arrange and Act
    Class<?> actualCommonPropertyType = (new SimpleResolver()).getCommonPropertyType(null, "Base");

    // Assert
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, actualCommonPropertyType);
  }

  /**
   * Test {@link SimpleResolver#getCommonPropertyType(ELContext, Object)}.
   * <ul>
   *   <li>Given {@link SimpleResolver#SimpleResolver()}.</li>
   *   <li>When {@link SimpleContext#SimpleContext()}.</li>
   *   <li>Then return {@link Object}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SimpleResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getCommonPropertyType(ELContext, Object); given SimpleResolver(); when SimpleContext(); then return Object")
  void testGetCommonPropertyType_givenSimpleResolver_whenSimpleContext_thenReturnObject() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver();

    // Act
    Class<?> actualCommonPropertyType = simpleResolver.getCommonPropertyType(new SimpleContext(), "Base");

    // Assert
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, actualCommonPropertyType);
  }

  /**
   * Test {@link SimpleResolver#getCommonPropertyType(ELContext, Object)}.
   * <ul>
   *   <li>Then return {@link String}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SimpleResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getCommonPropertyType(ELContext, Object); then return String")
  void testGetCommonPropertyType_thenReturnString() {
    // Arrange and Act
    Class<?> actualCommonPropertyType = (new SimpleResolver(new RootPropertyResolver(), true))
        .getCommonPropertyType(null, "Base");

    // Assert
    Class<String> expectedCommonPropertyType = String.class;
    assertEquals(expectedCommonPropertyType, actualCommonPropertyType);
  }

  /**
   * Test {@link SimpleResolver#getType(ELContext, Object, Object)}.
   * <p>
   * Method under test: {@link SimpleResolver#getType(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getType(ELContext, Object, Object)")
  void testGetType() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver(new RootPropertyResolver(), true);

    // Act and Assert
    assertNull(simpleResolver.getType(new SimpleContext(), "Base", "Property"));
  }

  /**
   * Test {@link SimpleResolver#getType(ELContext, Object, Object)}.
   * <ul>
   *   <li>Given {@link BeanNameELResolver#BeanNameELResolver(BeanNameResolver)}
   * with {@link BeanNameResolver}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SimpleResolver#getType(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getType(ELContext, Object, Object); given BeanNameELResolver(BeanNameResolver) with BeanNameResolver; then return 'null'")
  void testGetType_givenBeanNameELResolverWithBeanNameResolver_thenReturnNull() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver(new BeanNameELResolver(mock(BeanNameResolver.class)), true);

    // Act and Assert
    assertNull(simpleResolver.getType(new SimpleContext(), "Base", "Property"));
  }

  /**
   * Test {@link SimpleResolver#getValue(ELContext, Object, Object)}.
   * <p>
   * Method under test: {@link SimpleResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getValue(ELContext, Object, Object)")
  void testGetValue() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver(new RootPropertyResolver(), true);

    // Act and Assert
    assertNull(simpleResolver.getValue(new SimpleContext(), "Base", "Property"));
  }

  /**
   * Test {@link SimpleResolver#getValue(ELContext, Object, Object)}.
   * <ul>
   *   <li>Given {@link BeanNameELResolver#BeanNameELResolver(BeanNameResolver)}
   * with {@link BeanNameResolver}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SimpleResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getValue(ELContext, Object, Object); given BeanNameELResolver(BeanNameResolver) with BeanNameResolver; then return 'null'")
  void testGetValue_givenBeanNameELResolverWithBeanNameResolver_thenReturnNull() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver(new BeanNameELResolver(mock(BeanNameResolver.class)), true);

    // Act and Assert
    assertNull(simpleResolver.getValue(new SimpleContext(), "Base", "Property"));
  }

  /**
   * Test {@link SimpleResolver#isReadOnly(ELContext, Object, Object)}.
   * <p>
   * Method under test:
   * {@link SimpleResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test isReadOnly(ELContext, Object, Object)")
  void testIsReadOnly() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver(new RootPropertyResolver(), true);
    SimpleContext context = new SimpleContext();

    // Act
    boolean actualIsReadOnlyResult = simpleResolver.isReadOnly(context, "Base", "Property");

    // Assert
    assertFalse(context.isPropertyResolved());
    assertFalse(actualIsReadOnlyResult);
  }

  /**
   * Test {@link SimpleResolver#isReadOnly(ELContext, Object, Object)}.
   * <ul>
   *   <li>Given {@link BeanNameELResolver#BeanNameELResolver(BeanNameResolver)}
   * with {@link BeanNameResolver}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SimpleResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test isReadOnly(ELContext, Object, Object); given BeanNameELResolver(BeanNameResolver) with BeanNameResolver")
  void testIsReadOnly_givenBeanNameELResolverWithBeanNameResolver() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver(new BeanNameELResolver(mock(BeanNameResolver.class)), true);
    SimpleContext context = new SimpleContext();

    // Act
    boolean actualIsReadOnlyResult = simpleResolver.isReadOnly(context, "Base", "Property");

    // Assert
    assertFalse(context.isPropertyResolved());
    assertFalse(actualIsReadOnlyResult);
  }

  /**
   * Test {@link SimpleResolver#isReadOnly(ELContext, Object, Object)}.
   * <ul>
   *   <li>Then {@link SimpleContext#SimpleContext()} PropertyResolved.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SimpleResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test isReadOnly(ELContext, Object, Object); then SimpleContext() PropertyResolved")
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
   * <ul>
   *   <li>Given {@code true}.</li>
   *   <li>Then calls {@link ELContext#isPropertyResolved()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SimpleResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName("Test setValue(ELContext, Object, Object, Object); given 'true'; then calls isPropertyResolved()")
  void testSetValue_givenTrue_thenCallsIsPropertyResolved() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver();
    StandardELContext context = mock(StandardELContext.class);
    when(context.isPropertyResolved()).thenReturn(true);
    doNothing().when(context).setPropertyResolved(anyBoolean());

    // Act
    simpleResolver.setValue(context, "Base", "Property", "Value");

    // Assert
    verify(context, atLeast(1)).isPropertyResolved();
    verify(context, atLeast(1)).setPropertyResolved(eq(false));
  }

  /**
   * Test
   * {@link SimpleResolver#invoke(ELContext, Object, Object, Class[], Object[])}.
   * <p>
   * Method under test:
   * {@link SimpleResolver#invoke(ELContext, Object, Object, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(ELContext, Object, Object, Class[], Object[])")
  void testInvoke() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver(new RootPropertyResolver(), true);
    SimpleContext context = new SimpleContext();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertNull(simpleResolver.invoke(context, "Base", "Method", new Class[]{forNameResult}, new Object[]{"Params"}));
  }

  /**
   * Test
   * {@link SimpleResolver#invoke(ELContext, Object, Object, Class[], Object[])}.
   * <p>
   * Method under test:
   * {@link SimpleResolver#invoke(ELContext, Object, Object, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(ELContext, Object, Object, Class[], Object[])")
  void testInvoke2() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver(new StaticFieldELResolver(), true);
    SimpleContext context = new SimpleContext();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertNull(simpleResolver.invoke(context, "Base", "Method", new Class[]{forNameResult}, new Object[]{"Params"}));
  }

  /**
   * Test
   * {@link SimpleResolver#invoke(ELContext, Object, Object, Class[], Object[])}.
   * <ul>
   *   <li>Given {@link BeanNameELResolver#BeanNameELResolver(BeanNameResolver)}
   * with {@link BeanNameResolver}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SimpleResolver#invoke(ELContext, Object, Object, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(ELContext, Object, Object, Class[], Object[]); given BeanNameELResolver(BeanNameResolver) with BeanNameResolver; then return 'null'")
  void testInvoke_givenBeanNameELResolverWithBeanNameResolver_thenReturnNull() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver(new BeanNameELResolver(mock(BeanNameResolver.class)), true);
    SimpleContext context = new SimpleContext();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertNull(simpleResolver.invoke(context, "Base", "Method", new Class[]{forNameResult}, new Object[]{"Params"}));
  }
}
