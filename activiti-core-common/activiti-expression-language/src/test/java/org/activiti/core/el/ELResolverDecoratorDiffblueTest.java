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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ELResolverDecoratorDiffblueTest {
  /**
   * Test {@link ELResolverDecorator#getValue(ELContext, Object, Object)}.
   * <p>
   * Method under test:
   * {@link ELResolverDecorator#getValue(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getValue(ELContext, Object, Object)")
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
   *   <li>Given {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ELResolverDecorator#getValue(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getValue(ELContext, Object, Object); given 'Name'")
  void testGetValue_givenName() {
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
   * Test {@link ELResolverDecorator#getValue(ELContext, Object, Object)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ELResolverDecorator#getValue(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getValue(ELContext, Object, Object); then return 'null'")
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
   * Method under test:
   * {@link ELResolverDecorator#getType(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getType(ELContext, Object, Object)")
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
   *   <li>Given {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ELResolverDecorator#getType(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getType(ELContext, Object, Object); given 'Name'")
  void testGetType_givenName() {
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
   * Test {@link ELResolverDecorator#getType(ELContext, Object, Object)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ELResolverDecorator#getType(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getType(ELContext, Object, Object); then return 'null'")
  void testGetType_thenReturnNull() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new JsonNodeELResolver());

    // Act and Assert
    assertNull(elResolverReflectionBlockerDecorator.getType(new ActivitiElContext(), "Base", "Property"));
  }

  /**
   * Test {@link ELResolverDecorator#setValue(ELContext, Object, Object, Object)}.
   * <ul>
   *   <li>Given {@link ELResolver}
   * {@link ELResolver#setValue(ELContext, Object, Object, Object)} does
   * nothing.</li>
   *   <li>Then calls
   * {@link ELResolver#setValue(ELContext, Object, Object, Object)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ELResolverDecorator#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName("Test setValue(ELContext, Object, Object, Object); given ELResolver setValue(ELContext, Object, Object, Object) does nothing; then calls setValue(ELContext, Object, Object, Object)")
  void testSetValue_givenELResolverSetValueDoesNothing_thenCallsSetValue() {
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
   * Test
   * {@link ELResolverDecorator#invoke(ELContext, Object, Object, Class[], Object[])}.
   * <p>
   * Method under test:
   * {@link ELResolverDecorator#invoke(ELContext, Object, Object, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(ELContext, Object, Object, Class[], Object[])")
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
   * Test
   * {@link ELResolverDecorator#invoke(ELContext, Object, Object, Class[], Object[])}.
   * <p>
   * Method under test:
   * {@link ELResolverDecorator#invoke(ELContext, Object, Object, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(ELContext, Object, Object, Class[], Object[])")
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
   * Test
   * {@link ELResolverDecorator#invoke(ELContext, Object, Object, Class[], Object[])}.
   * <p>
   * Method under test:
   * {@link ELResolverDecorator#invoke(ELContext, Object, Object, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(ELContext, Object, Object, Class[], Object[])")
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
   * Test
   * {@link ELResolverDecorator#invoke(ELContext, Object, Object, Class[], Object[])}.
   * <p>
   * Method under test:
   * {@link ELResolverDecorator#invoke(ELContext, Object, Object, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(ELContext, Object, Object, Class[], Object[])")
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
   * Test
   * {@link ELResolverDecorator#invoke(ELContext, Object, Object, Class[], Object[])}.
   * <ul>
   *   <li>Given {@link BeanNameELResolver#BeanNameELResolver(BeanNameResolver)}
   * with {@link BeanNameResolver}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ELResolverDecorator#invoke(ELContext, Object, Object, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(ELContext, Object, Object, Class[], Object[]); given BeanNameELResolver(BeanNameResolver) with BeanNameResolver; then return 'null'")
  void testInvoke_givenBeanNameELResolverWithBeanNameResolver_thenReturnNull() {
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
   * Test
   * {@link ELResolverDecorator#invoke(ELContext, Object, Object, Class[], Object[])}.
   * <ul>
   *   <li>Given {@link CompositeELResolver} (default constructor) add
   * {@link JsonNodeELResolver#JsonNodeELResolver()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ELResolverDecorator#invoke(ELContext, Object, Object, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(ELContext, Object, Object, Class[], Object[]); given CompositeELResolver (default constructor) add JsonNodeELResolver(); then return 'null'")
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
   * Method under test:
   * {@link ELResolverDecorator#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test isReadOnly(ELContext, Object, Object)")
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
   *   <li>Given {@link JsonNodeELResolver#JsonNodeELResolver(boolean)} with
   * readOnly is {@code true}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ELResolverDecorator#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test isReadOnly(ELContext, Object, Object); given JsonNodeELResolver(boolean) with readOnly is 'true'; then return 'true'")
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
   *   <li>Given {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ELResolverDecorator#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test isReadOnly(ELContext, Object, Object); given 'Name'")
  void testIsReadOnly_givenName() {
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
   * Test {@link ELResolverDecorator#isReadOnly(ELContext, Object, Object)}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ELResolverDecorator#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test isReadOnly(ELContext, Object, Object); then return 'false'")
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
   * Method under test:
   * {@link ELResolverDecorator#getFeatureDescriptors(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getFeatureDescriptors(ELContext, Object)")
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
   *   <li>Given {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ELResolverDecorator#getFeatureDescriptors(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getFeatureDescriptors(ELContext, Object); given 'Name'")
  void testGetFeatureDescriptors_givenName() {
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
   * Test {@link ELResolverDecorator#getFeatureDescriptors(ELContext, Object)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ELResolverDecorator#getFeatureDescriptors(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getFeatureDescriptors(ELContext, Object); then return 'null'")
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
   * Method under test:
   * {@link ELResolverDecorator#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getCommonPropertyType(ELContext, Object)")
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
   *   <li>Given {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ELResolverDecorator#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getCommonPropertyType(ELContext, Object); given 'Name'")
  void testGetCommonPropertyType_givenName() {
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

  /**
   * Test {@link ELResolverDecorator#getCommonPropertyType(ELContext, Object)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ELResolverDecorator#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getCommonPropertyType(ELContext, Object); then return 'null'")
  void testGetCommonPropertyType_thenReturnNull() {
    // Arrange
    ELResolverReflectionBlockerDecorator elResolverReflectionBlockerDecorator = new ELResolverReflectionBlockerDecorator(
        new JsonNodeELResolver());

    // Act and Assert
    assertNull(elResolverReflectionBlockerDecorator.getCommonPropertyType(new ActivitiElContext(), "Base"));
  }
}
