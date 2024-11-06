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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import jakarta.el.ELContext;
import jakarta.el.PropertyNotFoundException;
import jakarta.el.PropertyNotWritableException;
import java.util.Set;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RootPropertyResolverDiffblueTest {
  /**
   * Test {@link RootPropertyResolver#RootPropertyResolver()}.
   * <p>
   * Method under test: {@link RootPropertyResolver#RootPropertyResolver()}
   */
  @Test
  @DisplayName("Test new RootPropertyResolver()")
  void testNewRootPropertyResolver() {
    // Arrange, Act and Assert
    Iterable<String> propertiesResult = (new RootPropertyResolver()).properties();
    assertTrue(propertiesResult instanceof Set);
    assertTrue(((Set<String>) propertiesResult).isEmpty());
  }

  /**
   * Test {@link RootPropertyResolver#RootPropertyResolver(boolean)}.
   * <p>
   * Method under test: {@link RootPropertyResolver#RootPropertyResolver(boolean)}
   */
  @Test
  @DisplayName("Test new RootPropertyResolver(boolean)")
  void testNewRootPropertyResolver2() {
    // Arrange, Act and Assert
    Iterable<String> propertiesResult = (new RootPropertyResolver(true)).properties();
    assertTrue(propertiesResult instanceof Set);
    assertTrue(((Set<String>) propertiesResult).isEmpty());
  }

  /**
   * Test {@link RootPropertyResolver#getCommonPropertyType(ELContext, Object)}.
   * <ul>
   *   <li>Given {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RootPropertyResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getCommonPropertyType(ELContext, Object); given 'Name'")
  void testGetCommonPropertyType_givenName() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    SimpleContext context = new SimpleContext();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    context.setVariable("Name", new ObjectValueExpression(converter, "Object", type));

    // Act and Assert
    assertNull(rootPropertyResolver.getCommonPropertyType(context, "Base"));
  }

  /**
   * Test {@link RootPropertyResolver#getCommonPropertyType(ELContext, Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@link String}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RootPropertyResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getCommonPropertyType(ELContext, Object); when 'null'; then return String")
  void testGetCommonPropertyType_whenNull_thenReturnString() {
    // Arrange and Act
    Class<?> actualCommonPropertyType = (new RootPropertyResolver()).getCommonPropertyType(null, "Base");

    // Assert
    Class<String> expectedCommonPropertyType = String.class;
    assertEquals(expectedCommonPropertyType, actualCommonPropertyType);
  }

  /**
   * Test {@link RootPropertyResolver#getCommonPropertyType(ELContext, Object)}.
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RootPropertyResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getCommonPropertyType(ELContext, Object); when SimpleContext(); then return 'null'")
  void testGetCommonPropertyType_whenSimpleContext_thenReturnNull() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    // Act and Assert
    assertNull(rootPropertyResolver.getCommonPropertyType(new SimpleContext(), "Base"));
  }

  /**
   * Test {@link RootPropertyResolver#getFeatureDescriptors(ELContext, Object)}.
   * <ul>
   *   <li>Given {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RootPropertyResolver#getFeatureDescriptors(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getFeatureDescriptors(ELContext, Object); given 'Name'")
  void testGetFeatureDescriptors_givenName() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    SimpleContext context = new SimpleContext();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    context.setVariable("Name", new ObjectValueExpression(converter, "Object", type));

    // Act and Assert
    assertNull(rootPropertyResolver.getFeatureDescriptors(context, "Base"));
  }

  /**
   * Test {@link RootPropertyResolver#getFeatureDescriptors(ELContext, Object)}.
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RootPropertyResolver#getFeatureDescriptors(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getFeatureDescriptors(ELContext, Object); when SimpleContext()")
  void testGetFeatureDescriptors_whenSimpleContext() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    // Act and Assert
    assertNull(rootPropertyResolver.getFeatureDescriptors(new SimpleContext(), "Base"));
  }

  /**
   * Test {@link RootPropertyResolver#getType(ELContext, Object, Object)}.
   * <ul>
   *   <li>Given {@code false}.</li>
   *   <li>Then {@link SimpleContext#SimpleContext()} PropertyResolved.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RootPropertyResolver#getType(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getType(ELContext, Object, Object); given 'false'; then SimpleContext() PropertyResolved")
  void testGetType_givenFalse_thenSimpleContextPropertyResolved() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    SimpleContext context = new SimpleContext();
    context.setPropertyResolved(false);

    // Act
    Class<?> actualType = rootPropertyResolver.getType(context, null, "Property");

    // Assert
    assertTrue(context.isPropertyResolved());
    Class<Object> expectedType = Object.class;
    assertEquals(expectedType, actualType);
  }

  /**
   * Test {@link RootPropertyResolver#getType(ELContext, Object, Object)}.
   * <ul>
   *   <li>Given {@code false}.</li>
   *   <li>When one.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RootPropertyResolver#getType(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getType(ELContext, Object, Object); given 'false'; when one; then return 'null'")
  void testGetType_givenFalse_whenOne_thenReturnNull() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    SimpleContext context = new SimpleContext();
    context.setPropertyResolved(false);

    // Act and Assert
    assertNull(rootPropertyResolver.getType(context, null, 1));
    assertFalse(context.isPropertyResolved());
  }

  /**
   * Test {@link RootPropertyResolver#getType(ELContext, Object, Object)}.
   * <ul>
   *   <li>Given {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RootPropertyResolver#getType(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getType(ELContext, Object, Object); given 'Name'")
  void testGetType_givenName() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    SimpleContext context = new SimpleContext();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    context.setVariable("Name", new ObjectValueExpression(converter, "Object", type));

    // Act and Assert
    assertNull(rootPropertyResolver.getType(context, "Base", "Property"));
    assertFalse(context.isPropertyResolved());
  }

  /**
   * Test {@link RootPropertyResolver#getType(ELContext, Object, Object)}.
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RootPropertyResolver#getType(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getType(ELContext, Object, Object); when SimpleContext(); then return 'null'")
  void testGetType_whenSimpleContext_thenReturnNull() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();
    SimpleContext context = new SimpleContext();

    // Act and Assert
    assertNull(rootPropertyResolver.getType(context, "Base", "Property"));
    assertFalse(context.isPropertyResolved());
  }

  /**
   * Test {@link RootPropertyResolver#getValue(ELContext, Object, Object)}.
   * <ul>
   *   <li>Given {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RootPropertyResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getValue(ELContext, Object, Object); given 'Name'")
  void testGetValue_givenName() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    SimpleContext context = new SimpleContext();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    context.setVariable("Name", new ObjectValueExpression(converter, "Object", type));

    // Act and Assert
    assertNull(rootPropertyResolver.getValue(context, "Base", "Property"));
  }

  /**
   * Test {@link RootPropertyResolver#getValue(ELContext, Object, Object)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RootPropertyResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getValue(ELContext, Object, Object); when one; then return 'null'")
  void testGetValue_whenOne_thenReturnNull() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    // Act and Assert
    assertNull(rootPropertyResolver.getValue(new SimpleContext(), null, 1));
  }

  /**
   * Test {@link RootPropertyResolver#getValue(ELContext, Object, Object)}.
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RootPropertyResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getValue(ELContext, Object, Object); when SimpleContext(); then return 'null'")
  void testGetValue_whenSimpleContext_thenReturnNull() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    // Act and Assert
    assertNull(rootPropertyResolver.getValue(new SimpleContext(), "Base", "Property"));
  }

  /**
   * Test {@link RootPropertyResolver#getValue(ELContext, Object, Object)}.
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext()}.</li>
   *   <li>Then throw {@link PropertyNotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RootPropertyResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getValue(ELContext, Object, Object); when SimpleContext(); then throw PropertyNotFoundException")
  void testGetValue_whenSimpleContext_thenThrowPropertyNotFoundException() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    // Act and Assert
    assertThrows(PropertyNotFoundException.class,
        () -> rootPropertyResolver.getValue(new SimpleContext(), null, "Property"));
  }

  /**
   * Test {@link RootPropertyResolver#isReadOnly(ELContext, Object, Object)}.
   * <ul>
   *   <li>Given {@code false}.</li>
   *   <li>Then {@link SimpleContext#SimpleContext()} PropertyResolved.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RootPropertyResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test isReadOnly(ELContext, Object, Object); given 'false'; then SimpleContext() PropertyResolved")
  void testIsReadOnly_givenFalse_thenSimpleContextPropertyResolved() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    SimpleContext context = new SimpleContext();
    context.setPropertyResolved(false);

    // Act and Assert
    assertFalse(rootPropertyResolver.isReadOnly(context, null, "Property"));
    assertTrue(context.isPropertyResolved());
  }

  /**
   * Test {@link RootPropertyResolver#isReadOnly(ELContext, Object, Object)}.
   * <ul>
   *   <li>Given {@code false}.</li>
   *   <li>When one.</li>
   *   <li>Then not {@link SimpleContext#SimpleContext()} PropertyResolved.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RootPropertyResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test isReadOnly(ELContext, Object, Object); given 'false'; when one; then not SimpleContext() PropertyResolved")
  void testIsReadOnly_givenFalse_whenOne_thenNotSimpleContextPropertyResolved() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    SimpleContext context = new SimpleContext();
    context.setPropertyResolved(false);

    // Act
    boolean actualIsReadOnlyResult = rootPropertyResolver.isReadOnly(context, null, 1);

    // Assert
    assertFalse(context.isPropertyResolved());
    assertFalse(actualIsReadOnlyResult);
  }

  /**
   * Test {@link RootPropertyResolver#isReadOnly(ELContext, Object, Object)}.
   * <ul>
   *   <li>Given {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RootPropertyResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test isReadOnly(ELContext, Object, Object); given 'Name'")
  void testIsReadOnly_givenName() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    SimpleContext context = new SimpleContext();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    context.setVariable("Name", new ObjectValueExpression(converter, "Object", type));

    // Act
    boolean actualIsReadOnlyResult = rootPropertyResolver.isReadOnly(context, "Base", "Property");

    // Assert
    assertFalse(context.isPropertyResolved());
    assertFalse(actualIsReadOnlyResult);
  }

  /**
   * Test {@link RootPropertyResolver#isReadOnly(ELContext, Object, Object)}.
   * <ul>
   *   <li>Given {@link RootPropertyResolver#RootPropertyResolver(boolean)} with
   * readOnly is {@code true}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RootPropertyResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test isReadOnly(ELContext, Object, Object); given RootPropertyResolver(boolean) with readOnly is 'true'; then return 'true'")
  void testIsReadOnly_givenRootPropertyResolverWithReadOnlyIsTrue_thenReturnTrue() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver(true);

    SimpleContext context = new SimpleContext();
    context.setPropertyResolved(false);

    // Act
    boolean actualIsReadOnlyResult = rootPropertyResolver.isReadOnly(context, null, "Property");

    // Assert
    assertTrue(context.isPropertyResolved());
    assertTrue(actualIsReadOnlyResult);
  }

  /**
   * Test {@link RootPropertyResolver#isReadOnly(ELContext, Object, Object)}.
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext()}.</li>
   *   <li>Then not {@link SimpleContext#SimpleContext()} PropertyResolved.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RootPropertyResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test isReadOnly(ELContext, Object, Object); when SimpleContext(); then not SimpleContext() PropertyResolved")
  void testIsReadOnly_whenSimpleContext_thenNotSimpleContextPropertyResolved() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();
    SimpleContext context = new SimpleContext();

    // Act
    boolean actualIsReadOnlyResult = rootPropertyResolver.isReadOnly(context, "Base", "Property");

    // Assert
    assertFalse(context.isPropertyResolved());
    assertFalse(actualIsReadOnlyResult);
  }

  /**
   * Test
   * {@link RootPropertyResolver#setValue(ELContext, Object, Object, Object)}.
   * <ul>
   *   <li>Given {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RootPropertyResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName("Test setValue(ELContext, Object, Object, Object); given 'Name'")
  void testSetValue_givenName() throws PropertyNotWritableException {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    SimpleContext context = new SimpleContext();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    context.setVariable("Name", new ObjectValueExpression(converter, "Object", type));

    // Act
    rootPropertyResolver.setValue(context, "Base", "Property", "Value");

    // Assert
    Iterable<String> propertiesResult = rootPropertyResolver.properties();
    assertTrue(propertiesResult instanceof Set);
    assertFalse(context.isPropertyResolved());
    assertTrue(((Set<String>) propertiesResult).isEmpty());
  }

  /**
   * Test
   * {@link RootPropertyResolver#setValue(ELContext, Object, Object, Object)}.
   * <ul>
   *   <li>Then throw {@link PropertyNotWritableException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RootPropertyResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName("Test setValue(ELContext, Object, Object, Object); then throw PropertyNotWritableException")
  void testSetValue_thenThrowPropertyNotWritableException() throws PropertyNotWritableException {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver(true);

    // Act and Assert
    assertThrows(PropertyNotWritableException.class,
        () -> rootPropertyResolver.setValue(new SimpleContext(), null, "Property", "Value"));
  }

  /**
   * Test
   * {@link RootPropertyResolver#setValue(ELContext, Object, Object, Object)}.
   * <ul>
   *   <li>When {@code Base}.</li>
   *   <li>Then not {@link SimpleContext#SimpleContext()} PropertyResolved.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RootPropertyResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName("Test setValue(ELContext, Object, Object, Object); when 'Base'; then not SimpleContext() PropertyResolved")
  void testSetValue_whenBase_thenNotSimpleContextPropertyResolved() throws PropertyNotWritableException {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();
    SimpleContext context = new SimpleContext();

    // Act
    rootPropertyResolver.setValue(context, "Base", "Property", "Value");

    // Assert
    Iterable<String> propertiesResult = rootPropertyResolver.properties();
    assertTrue(propertiesResult instanceof Set);
    assertFalse(context.isPropertyResolved());
    assertTrue(((Set<String>) propertiesResult).isEmpty());
  }

  /**
   * Test
   * {@link RootPropertyResolver#setValue(ELContext, Object, Object, Object)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then not {@link SimpleContext#SimpleContext()} PropertyResolved.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RootPropertyResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName("Test setValue(ELContext, Object, Object, Object); when one; then not SimpleContext() PropertyResolved")
  void testSetValue_whenOne_thenNotSimpleContextPropertyResolved() throws PropertyNotWritableException {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();
    SimpleContext context = new SimpleContext();

    // Act
    rootPropertyResolver.setValue(context, null, 1, "Value");

    // Assert
    Iterable<String> propertiesResult = rootPropertyResolver.properties();
    assertTrue(propertiesResult instanceof Set);
    assertFalse(context.isPropertyResolved());
    assertTrue(((Set<String>) propertiesResult).isEmpty());
  }

  /**
   * Test
   * {@link RootPropertyResolver#setValue(ELContext, Object, Object, Object)}.
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext()}.</li>
   *   <li>Then {@link RootPropertyResolver#RootPropertyResolver()} properties size
   * is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RootPropertyResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName("Test setValue(ELContext, Object, Object, Object); when SimpleContext(); then RootPropertyResolver() properties size is one")
  void testSetValue_whenSimpleContext_thenRootPropertyResolverPropertiesSizeIsOne()
      throws PropertyNotWritableException {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();
    SimpleContext context = new SimpleContext();

    // Act
    rootPropertyResolver.setValue(context, null, "Property", "Value");

    // Assert
    Iterable<String> propertiesResult = rootPropertyResolver.properties();
    assertTrue(propertiesResult instanceof Set);
    assertEquals(1, ((Set<String>) propertiesResult).size());
    assertTrue(context.isPropertyResolved());
    assertTrue(((Set<String>) propertiesResult).contains("Property"));
  }

  /**
   * Test
   * {@link RootPropertyResolver#invoke(ELContext, Object, Object, Class[], Object[])}.
   * <ul>
   *   <li>Given {@code false}.</li>
   *   <li>When one.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RootPropertyResolver#invoke(ELContext, Object, Object, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(ELContext, Object, Object, Class[], Object[]); given 'false'; when one; then return 'null'")
  void testInvoke_givenFalse_whenOne_thenReturnNull() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    SimpleContext context = new SimpleContext();
    context.setPropertyResolved(false);
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertNull(rootPropertyResolver.invoke(context, null, 1, new Class[]{forNameResult}, new Object[]{"Params"}));
  }

  /**
   * Test
   * {@link RootPropertyResolver#invoke(ELContext, Object, Object, Class[], Object[])}.
   * <ul>
   *   <li>Given {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RootPropertyResolver#invoke(ELContext, Object, Object, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(ELContext, Object, Object, Class[], Object[]); given 'Name'")
  void testInvoke_givenName() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    SimpleContext context = new SimpleContext();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    context.setVariable("Name", new ObjectValueExpression(converter, "Object", type));
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertNull(
        rootPropertyResolver.invoke(context, "Base", "Method", new Class[]{forNameResult}, new Object[]{"Params"}));
  }

  /**
   * Test
   * {@link RootPropertyResolver#invoke(ELContext, Object, Object, Class[], Object[])}.
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link RootPropertyResolver#invoke(ELContext, Object, Object, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(ELContext, Object, Object, Class[], Object[]); when SimpleContext(); then return 'null'")
  void testInvoke_whenSimpleContext_thenReturnNull() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();
    SimpleContext context = new SimpleContext();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertNull(
        rootPropertyResolver.invoke(context, "Base", "Method", new Class[]{forNameResult}, new Object[]{"Params"}));
  }

  /**
   * Test {@link RootPropertyResolver#getProperty(String)}.
   * <p>
   * Method under test: {@link RootPropertyResolver#getProperty(String)}
   */
  @Test
  @DisplayName("Test getProperty(String)")
  void testGetProperty() {
    // Arrange, Act and Assert
    assertNull((new RootPropertyResolver()).getProperty("Property"));
  }

  /**
   * Test {@link RootPropertyResolver#setProperty(String, Object)}.
   * <p>
   * Method under test: {@link RootPropertyResolver#setProperty(String, Object)}
   */
  @Test
  @DisplayName("Test setProperty(String, Object)")
  void testSetProperty() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    // Act
    rootPropertyResolver.setProperty("Property", "Value");

    // Assert
    Iterable<String> propertiesResult = rootPropertyResolver.properties();
    assertTrue(propertiesResult instanceof Set);
    assertEquals(1, ((Set<String>) propertiesResult).size());
    assertTrue(((Set<String>) propertiesResult).contains("Property"));
  }

  /**
   * Test {@link RootPropertyResolver#isProperty(String)}.
   * <p>
   * Method under test: {@link RootPropertyResolver#isProperty(String)}
   */
  @Test
  @DisplayName("Test isProperty(String)")
  void testIsProperty() {
    // Arrange, Act and Assert
    assertFalse((new RootPropertyResolver()).isProperty("Property"));
  }

  /**
   * Test {@link RootPropertyResolver#properties()}.
   * <p>
   * Method under test: {@link RootPropertyResolver#properties()}
   */
  @Test
  @DisplayName("Test properties()")
  void testProperties() {
    // Arrange and Act
    Iterable<String> actualPropertiesResult = (new RootPropertyResolver()).properties();

    // Assert
    assertTrue(actualPropertiesResult instanceof Set);
    assertFalse(actualPropertiesResult.iterator().hasNext());
    assertTrue(((Set<String>) actualPropertiesResult).isEmpty());
  }
}
