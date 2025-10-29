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
import org.junit.jupiter.api.Test;

class RootPropertyResolverDiffblueTest {
  /**
   * Method under test:
   * {@link RootPropertyResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  void testGetCommonPropertyType() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    // Act and Assert
    assertNull(rootPropertyResolver.getCommonPropertyType(new SimpleContext(), "Base"));
  }

  /**
   * Method under test:
   * {@link RootPropertyResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  void testGetCommonPropertyType2() {
    // Arrange and Act
    Class<?> actualCommonPropertyType = (new RootPropertyResolver()).getCommonPropertyType(null, "Base");

    // Assert
    Class<String> expectedCommonPropertyType = String.class;
    assertEquals(expectedCommonPropertyType, actualCommonPropertyType);
  }

  /**
   * Method under test:
   * {@link RootPropertyResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  void testGetCommonPropertyType3() {
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
   * Method under test:
   * {@link RootPropertyResolver#getFeatureDescriptors(ELContext, Object)}
   */
  @Test
  void testGetFeatureDescriptors() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    // Act and Assert
    assertNull(rootPropertyResolver.getFeatureDescriptors(new SimpleContext(), "Base"));
  }

  /**
   * Method under test:
   * {@link RootPropertyResolver#getFeatureDescriptors(ELContext, Object)}
   */
  @Test
  void testGetFeatureDescriptors2() {
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
   * Method under test:
   * {@link RootPropertyResolver#getType(ELContext, Object, Object)}
   */
  @Test
  void testGetType() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();
    SimpleContext context = new SimpleContext();

    // Act and Assert
    assertNull(rootPropertyResolver.getType(context, "Base", "Property"));
    assertFalse(context.isPropertyResolved());
  }

  /**
   * Method under test:
   * {@link RootPropertyResolver#getType(ELContext, Object, Object)}
   */
  @Test
  void testGetType2() {
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
   * Method under test:
   * {@link RootPropertyResolver#getType(ELContext, Object, Object)}
   */
  @Test
  void testGetType3() {
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
   * Method under test:
   * {@link RootPropertyResolver#getType(ELContext, Object, Object)}
   */
  @Test
  void testGetType4() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    SimpleContext context = new SimpleContext();
    context.setPropertyResolved(false);

    // Act and Assert
    assertNull(rootPropertyResolver.getType(context, null, 1));
    assertFalse(context.isPropertyResolved());
  }

  /**
   * Method under test:
   * {@link RootPropertyResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  void testGetValue() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    // Act and Assert
    assertNull(rootPropertyResolver.getValue(new SimpleContext(), "Base", "Property"));
  }

  /**
   * Method under test:
   * {@link RootPropertyResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  void testGetValue2() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    // Act and Assert
    assertThrows(PropertyNotFoundException.class,
        () -> rootPropertyResolver.getValue(new SimpleContext(), null, "Property"));
  }

  /**
   * Method under test:
   * {@link RootPropertyResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  void testGetValue3() {
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
   * Method under test:
   * {@link RootPropertyResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  void testGetValue4() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    // Act and Assert
    assertNull(rootPropertyResolver.getValue(new SimpleContext(), null, 1));
  }

  /**
   * Method under test:
   * {@link RootPropertyResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  void testIsReadOnly() {
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
   * Method under test:
   * {@link RootPropertyResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  void testIsReadOnly2() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    SimpleContext context = new SimpleContext();
    context.setPropertyResolved(false);

    // Act and Assert
    assertFalse(rootPropertyResolver.isReadOnly(context, null, "Property"));
    assertTrue(context.isPropertyResolved());
  }

  /**
   * Method under test:
   * {@link RootPropertyResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  void testIsReadOnly3() {
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
   * Method under test:
   * {@link RootPropertyResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  void testIsReadOnly4() {
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
   * Method under test:
   * {@link RootPropertyResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  void testIsReadOnly5() {
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
   * Method under test:
   * {@link RootPropertyResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  void testSetValue() throws PropertyNotWritableException {
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
   * Method under test:
   * {@link RootPropertyResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  void testSetValue2() throws PropertyNotWritableException {
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
   * Method under test:
   * {@link RootPropertyResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  void testSetValue3() throws PropertyNotWritableException {
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
   * Method under test:
   * {@link RootPropertyResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  void testSetValue4() throws PropertyNotWritableException {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver(true);

    // Act and Assert
    assertThrows(PropertyNotWritableException.class,
        () -> rootPropertyResolver.setValue(new SimpleContext(), null, "Property", "Value"));
  }

  /**
   * Method under test:
   * {@link RootPropertyResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  void testSetValue5() throws PropertyNotWritableException {
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
   * Method under test:
   * {@link RootPropertyResolver#invoke(ELContext, Object, Object, Class[], Object[])}
   */
  @Test
  void testInvoke() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();
    SimpleContext context = new SimpleContext();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertNull(
        rootPropertyResolver.invoke(context, "Base", "Method", new Class[]{forNameResult}, new Object[]{"Params"}));
  }

  /**
   * Method under test:
   * {@link RootPropertyResolver#invoke(ELContext, Object, Object, Class[], Object[])}
   */
  @Test
  void testInvoke2() {
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
   * Method under test:
   * {@link RootPropertyResolver#invoke(ELContext, Object, Object, Class[], Object[])}
   */
  @Test
  void testInvoke3() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    SimpleContext context = new SimpleContext();
    context.setPropertyResolved(false);
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertNull(rootPropertyResolver.invoke(context, null, 1, new Class[]{forNameResult}, new Object[]{"Params"}));
  }

  /**
   * Method under test: {@link RootPropertyResolver#getProperty(String)}
   */
  @Test
  void testGetProperty() {
    // Arrange, Act and Assert
    assertNull((new RootPropertyResolver()).getProperty("Property"));
  }

  /**
   * Method under test: {@link RootPropertyResolver#setProperty(String, Object)}
   */
  @Test
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
   * Method under test: {@link RootPropertyResolver#isProperty(String)}
   */
  @Test
  void testIsProperty() {
    // Arrange, Act and Assert
    assertFalse((new RootPropertyResolver()).isProperty("Property"));
  }

  /**
   * Method under test: {@link RootPropertyResolver#properties()}
   */
  @Test
  void testProperties() {
    // Arrange and Act
    Iterable<String> actualPropertiesResult = (new RootPropertyResolver()).properties();

    // Assert
    assertTrue(actualPropertiesResult instanceof Set);
    assertFalse(actualPropertiesResult.iterator().hasNext());
    assertTrue(((Set<String>) actualPropertiesResult).isEmpty());
  }

  /**
   * Method under test: {@link RootPropertyResolver#RootPropertyResolver()}
   */
  @Test
  void testNewRootPropertyResolver() {
    // Arrange, Act and Assert
    Iterable<String> propertiesResult = (new RootPropertyResolver()).properties();
    assertTrue(propertiesResult instanceof Set);
    assertTrue(((Set<String>) propertiesResult).isEmpty());
  }

  /**
   * Method under test: {@link RootPropertyResolver#RootPropertyResolver(boolean)}
   */
  @Test
  void testNewRootPropertyResolver2() {
    // Arrange, Act and Assert
    Iterable<String> propertiesResult = (new RootPropertyResolver(true)).properties();
    assertTrue(propertiesResult instanceof Set);
    assertTrue(((Set<String>) propertiesResult).isEmpty());
  }
}
