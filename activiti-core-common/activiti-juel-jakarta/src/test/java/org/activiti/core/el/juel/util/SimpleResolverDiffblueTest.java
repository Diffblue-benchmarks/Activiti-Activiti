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
import static org.junit.jupiter.api.Assertions.assertSame;
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
import org.junit.jupiter.api.Test;

class SimpleResolverDiffblueTest {
  /**
   * Method under test:
   * {@link SimpleResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  void testGetCommonPropertyType() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver();

    // Act
    Class<?> actualCommonPropertyType = simpleResolver.getCommonPropertyType(new SimpleContext(), "Base");

    // Assert
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, actualCommonPropertyType);
  }

  /**
   * Method under test:
   * {@link SimpleResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  void testGetCommonPropertyType2() {
    // Arrange and Act
    Class<?> actualCommonPropertyType = (new SimpleResolver()).getCommonPropertyType(null, "Base");

    // Assert
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, actualCommonPropertyType);
  }

  /**
   * Method under test:
   * {@link SimpleResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  void testGetCommonPropertyType3() {
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
    assertSame(type, actualCommonPropertyType);
  }

  /**
   * Method under test:
   * {@link SimpleResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  void testGetCommonPropertyType4() {
    // Arrange and Act
    Class<?> actualCommonPropertyType = (new SimpleResolver(new RootPropertyResolver(), true))
        .getCommonPropertyType(null, "Base");

    // Assert
    Class<String> expectedCommonPropertyType = String.class;
    assertEquals(expectedCommonPropertyType, actualCommonPropertyType);
  }

  /**
   * Method under test:
   * {@link SimpleResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  void testGetCommonPropertyType5() {
    // Arrange and Act
    Class<?> actualCommonPropertyType = (new SimpleResolver(new SimpleResolver(), true)).getCommonPropertyType(null,
        "Base");

    // Assert
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, actualCommonPropertyType);
  }

  /**
   * Method under test: {@link SimpleResolver#getType(ELContext, Object, Object)}
   */
  @Test
  void testGetType() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver(new RootPropertyResolver(), true);

    // Act and Assert
    assertNull(simpleResolver.getType(new SimpleContext(), "Base", "Property"));
  }

  /**
   * Method under test: {@link SimpleResolver#getType(ELContext, Object, Object)}
   */
  @Test
  void testGetType2() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver(new BeanNameELResolver(mock(BeanNameResolver.class)), true);

    // Act and Assert
    assertNull(simpleResolver.getType(new SimpleContext(), "Base", "Property"));
  }

  /**
   * Method under test: {@link SimpleResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  void testGetValue() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver(new RootPropertyResolver(), true);

    // Act and Assert
    assertNull(simpleResolver.getValue(new SimpleContext(), "Base", "Property"));
  }

  /**
   * Method under test: {@link SimpleResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  void testGetValue2() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver(new BeanNameELResolver(mock(BeanNameResolver.class)), true);

    // Act and Assert
    assertNull(simpleResolver.getValue(new SimpleContext(), "Base", "Property"));
  }

  /**
   * Method under test:
   * {@link SimpleResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  void testIsReadOnly() {
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
   * Method under test:
   * {@link SimpleResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  void testIsReadOnly2() {
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
   * Method under test:
   * {@link SimpleResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  void testIsReadOnly3() {
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
   * Method under test:
   * {@link SimpleResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  void testSetValue() {
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
   * Method under test:
   * {@link SimpleResolver#invoke(ELContext, Object, Object, Class[], Object[])}
   */
  @Test
  void testInvoke() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver(new RootPropertyResolver(), true);
    SimpleContext context = new SimpleContext();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertNull(simpleResolver.invoke(context, "Base", "Method", new Class[]{forNameResult}, new Object[]{"Params"}));
  }

  /**
   * Method under test:
   * {@link SimpleResolver#invoke(ELContext, Object, Object, Class[], Object[])}
   */
  @Test
  void testInvoke2() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver(new BeanNameELResolver(mock(BeanNameResolver.class)), true);
    SimpleContext context = new SimpleContext();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertNull(simpleResolver.invoke(context, "Base", "Method", new Class[]{forNameResult}, new Object[]{"Params"}));
  }

  /**
   * Method under test:
   * {@link SimpleResolver#invoke(ELContext, Object, Object, Class[], Object[])}
   */
  @Test
  void testInvoke3() {
    // Arrange
    SimpleResolver simpleResolver = new SimpleResolver(new StaticFieldELResolver(), true);
    SimpleContext context = new SimpleContext();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertNull(simpleResolver.invoke(context, "Base", "Method", new Class[]{forNameResult}, new Object[]{"Params"}));
  }
}
