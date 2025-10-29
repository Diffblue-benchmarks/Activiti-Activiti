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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import jakarta.el.BeanNameELResolver;
import jakarta.el.BeanNameResolver;
import jakarta.el.CompositeELResolver;
import jakarta.el.ELContext;
import jakarta.el.ELResolver;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ELResolverDecoratorDiffblueTest {
  /**
   * Method under test:
   * {@link ELResolverDecorator#getValue(ELContext, Object, Object)}
   */
  @Test
  void testGetValue() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new JsonNodeELResolver());

    // Act and Assert
    assertNull(elResolverReflectionBlockerDecorator.getValue(new ActivitiElContext(), "Base", "Property"));
  }

  /**
   * Method under test:
   * {@link ELResolverDecorator#getValue(ELContext, Object, Object)}
   */
  @Test
  void testGetValue2() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new ELResolverReflectionBlockerDecorator(new JsonNodeELResolver()));

    // Act and Assert
    assertNull(elResolverReflectionBlockerDecorator.getValue(new ActivitiElContext(), "Base", "Property"));
  }

  /**
   * Method under test:
   * {@link ELResolverDecorator#getValue(ELContext, Object, Object)}
   */
  @Test
  void testGetValue3() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new JsonNodeELResolver());

    ActivitiElContext context = new ActivitiElContext();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    context.setVariable("Name", new ObjectValueExpression(converter, "Object", type));

    // Act and Assert
    assertNull(elResolverReflectionBlockerDecorator.getValue(context, "Base", "Property"));
  }

  /**
   * Method under test:
   * {@link ELResolverDecorator#getType(ELContext, Object, Object)}
   */
  @Test
  void testGetType() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new JsonNodeELResolver());

    // Act and Assert
    assertNull(elResolverReflectionBlockerDecorator.getType(new ActivitiElContext(), "Base", "Property"));
  }

  /**
   * Method under test:
   * {@link ELResolverDecorator#getType(ELContext, Object, Object)}
   */
  @Test
  void testGetType2() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new ELResolverReflectionBlockerDecorator(new JsonNodeELResolver()));

    // Act and Assert
    assertNull(elResolverReflectionBlockerDecorator.getType(new ActivitiElContext(), "Base", "Property"));
  }

  /**
   * Method under test:
   * {@link ELResolverDecorator#getType(ELContext, Object, Object)}
   */
  @Test
  void testGetType3() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new JsonNodeELResolver());

    ActivitiElContext context = new ActivitiElContext();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    context.setVariable("Name", new ObjectValueExpression(converter, "Object", type));

    // Act and Assert
    assertNull(elResolverReflectionBlockerDecorator.getType(context, "Base", "Property"));
  }

  /**
   * Method under test:
   * {@link ELResolverDecorator#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  void testSetValue() {
    // Arrange
    ELResolver resolver = mock(ELResolver.class);
    doNothing().when(resolver)
        .setValue(Mockito.<ELContext>any(), Mockito.<Object>any(), Mockito.<Object>any(), Mockito.<Object>any());
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        resolver);

    // Act
    elResolverReflectionBlockerDecorator.setValue(new ActivitiElContext(), "Base", "Property", "Value");

    // Assert
    verify(resolver).setValue(isA(ELContext.class), isA(Object.class), isA(Object.class), isA(Object.class));
  }

  /**
   * Method under test:
   * {@link ELResolverDecorator#invoke(ELContext, Object, Object, Class[], Object[])}
   */
  @Test
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
   * Method under test:
   * {@link ELResolverDecorator#invoke(ELContext, Object, Object, Class[], Object[])}
   */
  @Test
  void testInvoke2() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new BeanNameELResolver(mock(BeanNameResolver.class)));
    ActivitiElContext context = new ActivitiElContext();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertNull(elResolverReflectionBlockerDecorator.invoke(context, "Base", "Method", new Class[]{forNameResult},
        new Object[]{"Params"}));
  }

  /**
   * Method under test:
   * {@link ELResolverDecorator#invoke(ELContext, Object, Object, Class[], Object[])}
   */
  @Test
  void testInvoke3() {
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
   * Method under test:
   * {@link ELResolverDecorator#invoke(ELContext, Object, Object, Class[], Object[])}
   */
  @Test
  void testInvoke4() {
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
   * Method under test:
   * {@link ELResolverDecorator#invoke(ELContext, Object, Object, Class[], Object[])}
   */
  @Test
  void testInvoke5() {
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
   * Method under test:
   * {@link ELResolverDecorator#invoke(ELContext, Object, Object, Class[], Object[])}
   */
  @Test
  void testInvoke6() {
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
   * Method under test:
   * {@link ELResolverDecorator#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  void testIsReadOnly() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new JsonNodeELResolver());

    // Act and Assert
    assertFalse(elResolverReflectionBlockerDecorator.isReadOnly(new ActivitiElContext(), "Base", "Property"));
  }

  /**
   * Method under test:
   * {@link ELResolverDecorator#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  void testIsReadOnly2() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new JsonNodeELResolver(true));

    // Act and Assert
    assertTrue(elResolverReflectionBlockerDecorator.isReadOnly(new ActivitiElContext(), "Base", "Property"));
  }

  /**
   * Method under test:
   * {@link ELResolverDecorator#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  void testIsReadOnly3() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new ELResolverReflectionBlockerDecorator(new JsonNodeELResolver()));

    // Act and Assert
    assertFalse(elResolverReflectionBlockerDecorator.isReadOnly(new ActivitiElContext(), "Base", "Property"));
  }

  /**
   * Method under test:
   * {@link ELResolverDecorator#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  void testIsReadOnly4() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new JsonNodeELResolver());

    ActivitiElContext context = new ActivitiElContext();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    context.setVariable("Name", new ObjectValueExpression(converter, "Object", type));

    // Act and Assert
    assertFalse(elResolverReflectionBlockerDecorator.isReadOnly(context, "Base", "Property"));
  }

  /**
   * Method under test:
   * {@link ELResolverDecorator#getFeatureDescriptors(ELContext, Object)}
   */
  @Test
  void testGetFeatureDescriptors() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new JsonNodeELResolver());

    // Act and Assert
    assertNull(elResolverReflectionBlockerDecorator.getFeatureDescriptors(new ActivitiElContext(), "Base"));
  }

  /**
   * Method under test:
   * {@link ELResolverDecorator#getFeatureDescriptors(ELContext, Object)}
   */
  @Test
  void testGetFeatureDescriptors2() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new ELResolverReflectionBlockerDecorator(new JsonNodeELResolver()));

    // Act and Assert
    assertNull(elResolverReflectionBlockerDecorator.getFeatureDescriptors(new ActivitiElContext(), "Base"));
  }

  /**
   * Method under test:
   * {@link ELResolverDecorator#getFeatureDescriptors(ELContext, Object)}
   */
  @Test
  void testGetFeatureDescriptors3() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new JsonNodeELResolver());

    ActivitiElContext context = new ActivitiElContext();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    context.setVariable("Name", new ObjectValueExpression(converter, "Object", type));

    // Act and Assert
    assertNull(elResolverReflectionBlockerDecorator.getFeatureDescriptors(context, "Base"));
  }

  /**
   * Method under test:
   * {@link ELResolverDecorator#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  void testGetCommonPropertyType() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new JsonNodeELResolver());

    // Act and Assert
    assertNull(elResolverReflectionBlockerDecorator.getCommonPropertyType(new ActivitiElContext(), "Base"));
  }

  /**
   * Method under test:
   * {@link ELResolverDecorator#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  void testGetCommonPropertyType2() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new ELResolverReflectionBlockerDecorator(new JsonNodeELResolver()));

    // Act and Assert
    assertNull(elResolverReflectionBlockerDecorator.getCommonPropertyType(new ActivitiElContext(), "Base"));
  }

  /**
   * Method under test:
   * {@link ELResolverDecorator#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  void testGetCommonPropertyType3() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new JsonNodeELResolver());

    ActivitiElContext context = new ActivitiElContext();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    context.setVariable("Name", new ObjectValueExpression(converter, "Object", type));

    // Act and Assert
    assertNull(elResolverReflectionBlockerDecorator.getCommonPropertyType(context, "Base"));
  }
}
