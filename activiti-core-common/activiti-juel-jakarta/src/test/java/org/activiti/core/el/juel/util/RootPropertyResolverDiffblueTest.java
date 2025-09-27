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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.el.ELContext;
import jakarta.el.PropertyNotFoundException;
import jakarta.el.PropertyNotWritableException;
import jakarta.el.StandardELContext;
import java.util.Iterator;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class RootPropertyResolverDiffblueTest {
  /**
   * Test {@link RootPropertyResolver#RootPropertyResolver()}.
   *
   * <p>Method under test: {@link RootPropertyResolver#RootPropertyResolver()}
   */
  @Test
  @DisplayName("Test new RootPropertyResolver()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void RootPropertyResolver.<init>()"})
  void testNewRootPropertyResolver() {
    // Arrange, Act and Assert
    Iterable<String> propertiesResult = new RootPropertyResolver().properties();
    assertTrue(propertiesResult instanceof Set);
    assertTrue(((Set<String>) propertiesResult).isEmpty());
  }

  /**
   * Test {@link RootPropertyResolver#RootPropertyResolver(boolean)}.
   *
   * <p>Method under test: {@link RootPropertyResolver#RootPropertyResolver(boolean)}
   */
  @Test
  @DisplayName("Test new RootPropertyResolver(boolean)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void RootPropertyResolver.<init>(boolean)"})
  void testNewRootPropertyResolver2() {
    // Arrange, Act and Assert
    Iterable<String> propertiesResult = new RootPropertyResolver(true).properties();
    assertTrue(propertiesResult instanceof Set);
    assertTrue(((Set<String>) propertiesResult).isEmpty());
  }

  /**
   * Test {@link RootPropertyResolver#getCommonPropertyType(ELContext, Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@link String}.
   * </ul>
   *
   * <p>Method under test: {@link RootPropertyResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getCommonPropertyType(ELContext, Object); when 'null'; then return String")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class RootPropertyResolver.getCommonPropertyType(ELContext, Object)"})
  void testGetCommonPropertyType_whenNull_thenReturnString() {
    // Arrange and Act
    Class<?> actualCommonPropertyType =
        new RootPropertyResolver().getCommonPropertyType(null, "Base");

    // Assert
    Class<String> expectedCommonPropertyType = String.class;
    assertEquals(expectedCommonPropertyType, actualCommonPropertyType);
  }

  /**
   * Test {@link RootPropertyResolver#getCommonPropertyType(ELContext, Object)}.
   *
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext()}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link RootPropertyResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  @DisplayName(
      "Test getCommonPropertyType(ELContext, Object); when SimpleContext(); then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class RootPropertyResolver.getCommonPropertyType(ELContext, Object)"})
  void testGetCommonPropertyType_whenSimpleContext_thenReturnNull() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    // Act and Assert
    assertNull(rootPropertyResolver.getCommonPropertyType(new SimpleContext(), "Base"));
  }

  /**
   * Test {@link RootPropertyResolver#getFeatureDescriptors(ELContext, Object)}.
   *
   * <p>Method under test: {@link RootPropertyResolver#getFeatureDescriptors(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getFeatureDescriptors(ELContext, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Iterator RootPropertyResolver.getFeatureDescriptors(ELContext, Object)"})
  void testGetFeatureDescriptors() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    // Act and Assert
    assertNull(rootPropertyResolver.getFeatureDescriptors(new SimpleContext(), "Base"));
  }

  /**
   * Test {@link RootPropertyResolver#getType(ELContext, Object, Object)}.
   *
   * <ul>
   *   <li>Then not {@link StandardELContext#StandardELContext(ELContext)} with context is {@link
   *       SimpleContext#SimpleContext()} PropertyResolved.
   * </ul>
   *
   * <p>Method under test: {@link RootPropertyResolver#getType(ELContext, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test getType(ELContext, Object, Object); then not StandardELContext(ELContext) with context is SimpleContext() PropertyResolved")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class RootPropertyResolver.getType(ELContext, Object, Object)"})
  void testGetType_thenNotStandardELContextWithContextIsSimpleContextPropertyResolved() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    StandardELContext context = new StandardELContext(new SimpleContext());
    context.setPropertyResolved(false);

    // Act and Assert
    assertNull(rootPropertyResolver.getType(context, null, 1));
    assertFalse(context.isPropertyResolved());
  }

  /**
   * Test {@link RootPropertyResolver#getType(ELContext, Object, Object)}.
   *
   * <ul>
   *   <li>Then {@link StandardELContext#StandardELContext(ELContext)} with context is {@link
   *       SimpleContext#SimpleContext()} PropertyResolved.
   * </ul>
   *
   * <p>Method under test: {@link RootPropertyResolver#getType(ELContext, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test getType(ELContext, Object, Object); then StandardELContext(ELContext) with context is SimpleContext() PropertyResolved")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class RootPropertyResolver.getType(ELContext, Object, Object)"})
  void testGetType_thenStandardELContextWithContextIsSimpleContextPropertyResolved() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    StandardELContext context = new StandardELContext(new SimpleContext());
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
   *
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext()}.
   *   <li>Then not {@link SimpleContext#SimpleContext()} PropertyResolved.
   * </ul>
   *
   * <p>Method under test: {@link RootPropertyResolver#getType(ELContext, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test getType(ELContext, Object, Object); when SimpleContext(); then not SimpleContext() PropertyResolved")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class RootPropertyResolver.getType(ELContext, Object, Object)"})
  void testGetType_whenSimpleContext_thenNotSimpleContextPropertyResolved() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();
    SimpleContext context = new SimpleContext();

    // Act and Assert
    assertNull(rootPropertyResolver.getType(context, "Base", "Property"));
    assertFalse(context.isPropertyResolved());
  }

  /**
   * Test {@link RootPropertyResolver#getValue(ELContext, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@code false}.
   *   <li>Then throw {@link PropertyNotFoundException}.
   * </ul>
   *
   * <p>Method under test: {@link RootPropertyResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test getValue(ELContext, Object, Object); given 'false'; then throw PropertyNotFoundException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object RootPropertyResolver.getValue(ELContext, Object, Object)"})
  void testGetValue_givenFalse_thenThrowPropertyNotFoundException() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    StandardELContext context = new StandardELContext(new SimpleContext());
    context.setPropertyResolved(false);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> rootPropertyResolver.getValue(context, null, "Property"));
  }

  /**
   * Test {@link RootPropertyResolver#getValue(ELContext, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@code false}.
   *   <li>When one.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link RootPropertyResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test getValue(ELContext, Object, Object); given 'false'; when one; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object RootPropertyResolver.getValue(ELContext, Object, Object)"})
  void testGetValue_givenFalse_whenOne_thenReturnNull() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    StandardELContext context = new StandardELContext(new SimpleContext());
    context.setPropertyResolved(false);

    // Act and Assert
    assertNull(rootPropertyResolver.getValue(context, null, 1));
  }

  /**
   * Test {@link RootPropertyResolver#getValue(ELContext, Object, Object)}.
   *
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext()}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link RootPropertyResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getValue(ELContext, Object, Object); when SimpleContext(); then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object RootPropertyResolver.getValue(ELContext, Object, Object)"})
  void testGetValue_whenSimpleContext_thenReturnNull() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    // Act and Assert
    assertNull(rootPropertyResolver.getValue(new SimpleContext(), "Base", "Property"));
  }

  /**
   * Test {@link RootPropertyResolver#isReadOnly(ELContext, Object, Object)}.
   *
   * <p>Method under test: {@link RootPropertyResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test isReadOnly(ELContext, Object, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean RootPropertyResolver.isReadOnly(ELContext, Object, Object)"})
  void testIsReadOnly() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    StandardELContext context = new StandardELContext(new SimpleContext());
    context.setPropertyResolved(false);

    // Act
    boolean actualIsReadOnlyResult = rootPropertyResolver.isReadOnly(context, null, 1);

    // Assert
    assertFalse(context.isPropertyResolved());
    assertFalse(actualIsReadOnlyResult);
  }

  /**
   * Test {@link RootPropertyResolver#isReadOnly(ELContext, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link RootPropertyResolver#RootPropertyResolver(boolean)} with readOnly is {@code
   *       true}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link RootPropertyResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test isReadOnly(ELContext, Object, Object); given RootPropertyResolver(boolean) with readOnly is 'true'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean RootPropertyResolver.isReadOnly(ELContext, Object, Object)"})
  void testIsReadOnly_givenRootPropertyResolverWithReadOnlyIsTrue_thenReturnTrue() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver(true);

    StandardELContext context = new StandardELContext(new SimpleContext());
    context.setPropertyResolved(false);

    // Act
    boolean actualIsReadOnlyResult = rootPropertyResolver.isReadOnly(context, null, "Property");

    // Assert
    assertTrue(context.isPropertyResolved());
    assertTrue(actualIsReadOnlyResult);
  }

  /**
   * Test {@link RootPropertyResolver#isReadOnly(ELContext, Object, Object)}.
   *
   * <ul>
   *   <li>Then {@link StandardELContext#StandardELContext(ELContext)} with context is {@link
   *       SimpleContext#SimpleContext()} PropertyResolved.
   * </ul>
   *
   * <p>Method under test: {@link RootPropertyResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test isReadOnly(ELContext, Object, Object); then StandardELContext(ELContext) with context is SimpleContext() PropertyResolved")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean RootPropertyResolver.isReadOnly(ELContext, Object, Object)"})
  void testIsReadOnly_thenStandardELContextWithContextIsSimpleContextPropertyResolved() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    StandardELContext context = new StandardELContext(new SimpleContext());
    context.setPropertyResolved(false);

    // Act and Assert
    assertFalse(rootPropertyResolver.isReadOnly(context, null, "Property"));
    assertTrue(context.isPropertyResolved());
  }

  /**
   * Test {@link RootPropertyResolver#isReadOnly(ELContext, Object, Object)}.
   *
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext()}.
   *   <li>Then not {@link SimpleContext#SimpleContext()} PropertyResolved.
   * </ul>
   *
   * <p>Method under test: {@link RootPropertyResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test isReadOnly(ELContext, Object, Object); when SimpleContext(); then not SimpleContext() PropertyResolved")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean RootPropertyResolver.isReadOnly(ELContext, Object, Object)"})
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
   * Test {@link RootPropertyResolver#setValue(ELContext, Object, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@code false}.
   *   <li>Then {@link RootPropertyResolver#RootPropertyResolver()} properties size is one.
   * </ul>
   *
   * <p>Method under test: {@link RootPropertyResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test setValue(ELContext, Object, Object, Object); given 'false'; then RootPropertyResolver() properties size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void RootPropertyResolver.setValue(ELContext, Object, Object, Object)"})
  void testSetValue_givenFalse_thenRootPropertyResolverPropertiesSizeIsOne()
      throws PropertyNotWritableException {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    StandardELContext context = new StandardELContext(new SimpleContext());
    context.setPropertyResolved(false);

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
   * Test {@link RootPropertyResolver#setValue(ELContext, Object, Object, Object)}.
   *
   * <ul>
   *   <li>Then not {@link StandardELContext#StandardELContext(ELContext)} with context is {@link
   *       SimpleContext#SimpleContext()} PropertyResolved.
   * </ul>
   *
   * <p>Method under test: {@link RootPropertyResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test setValue(ELContext, Object, Object, Object); then not StandardELContext(ELContext) with context is SimpleContext() PropertyResolved")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void RootPropertyResolver.setValue(ELContext, Object, Object, Object)"})
  void testSetValue_thenNotStandardELContextWithContextIsSimpleContextPropertyResolved()
      throws PropertyNotWritableException {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    StandardELContext context = new StandardELContext(new SimpleContext());
    context.setPropertyResolved(false);

    // Act
    rootPropertyResolver.setValue(context, null, 1, "Value");

    // Assert that nothing has changed
    Iterable<String> propertiesResult = rootPropertyResolver.properties();
    assertTrue(propertiesResult instanceof Set);
    assertFalse(context.isPropertyResolved());
    assertTrue(((Set<String>) propertiesResult).isEmpty());
  }

  /**
   * Test {@link RootPropertyResolver#setValue(ELContext, Object, Object, Object)}.
   *
   * <ul>
   *   <li>Then throw {@link PropertyNotWritableException}.
   * </ul>
   *
   * <p>Method under test: {@link RootPropertyResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test setValue(ELContext, Object, Object, Object); then throw PropertyNotWritableException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void RootPropertyResolver.setValue(ELContext, Object, Object, Object)"})
  void testSetValue_thenThrowPropertyNotWritableException() throws PropertyNotWritableException {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver(true);

    StandardELContext context = new StandardELContext(new SimpleContext());
    context.setPropertyResolved(false);

    // Act and Assert
    assertThrows(
        PropertyNotWritableException.class,
        () -> rootPropertyResolver.setValue(context, null, "Property", "Value"));
  }

  /**
   * Test {@link RootPropertyResolver#setValue(ELContext, Object, Object, Object)}.
   *
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext()}.
   *   <li>Then not {@link SimpleContext#SimpleContext()} PropertyResolved.
   * </ul>
   *
   * <p>Method under test: {@link RootPropertyResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test setValue(ELContext, Object, Object, Object); when SimpleContext(); then not SimpleContext() PropertyResolved")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void RootPropertyResolver.setValue(ELContext, Object, Object, Object)"})
  void testSetValue_whenSimpleContext_thenNotSimpleContextPropertyResolved()
      throws PropertyNotWritableException {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();
    SimpleContext context = new SimpleContext();

    // Act
    rootPropertyResolver.setValue(context, "Base", "Property", "Value");

    // Assert that nothing has changed
    Iterable<String> propertiesResult = rootPropertyResolver.properties();
    assertTrue(propertiesResult instanceof Set);
    assertFalse(context.isPropertyResolved());
    assertTrue(((Set<String>) propertiesResult).isEmpty());
  }

  /**
   * Test {@link RootPropertyResolver#invoke(ELContext, Object, Object, Class[], Object[])}.
   *
   * <ul>
   *   <li>Given {@code false}.
   *   <li>When one.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link RootPropertyResolver#invoke(ELContext, Object, Object, Class[],
   * Object[])}
   */
  @Test
  @DisplayName(
      "Test invoke(ELContext, Object, Object, Class[], Object[]); given 'false'; when one; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Object RootPropertyResolver.invoke(ELContext, Object, Object, Class[], Object[])"
  })
  void testInvoke_givenFalse_whenOne_thenReturnNull() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();

    StandardELContext context = new StandardELContext(new SimpleContext());
    context.setPropertyResolved(false);
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertNull(
        rootPropertyResolver.invoke(
            context, null, 1, new Class[] {forNameResult}, new Object[] {"Params"}));
  }

  /**
   * Test {@link RootPropertyResolver#invoke(ELContext, Object, Object, Class[], Object[])}.
   *
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext()}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link RootPropertyResolver#invoke(ELContext, Object, Object, Class[],
   * Object[])}
   */
  @Test
  @DisplayName(
      "Test invoke(ELContext, Object, Object, Class[], Object[]); when SimpleContext(); then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Object RootPropertyResolver.invoke(ELContext, Object, Object, Class[], Object[])"
  })
  void testInvoke_whenSimpleContext_thenReturnNull() {
    // Arrange
    RootPropertyResolver rootPropertyResolver = new RootPropertyResolver();
    SimpleContext context = new SimpleContext();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertNull(
        rootPropertyResolver.invoke(
            context, "Base", "Method", new Class[] {forNameResult}, new Object[] {"Params"}));
  }

  /**
   * Test {@link RootPropertyResolver#getProperty(String)}.
   *
   * <p>Method under test: {@link RootPropertyResolver#getProperty(String)}
   */
  @Test
  @DisplayName("Test getProperty(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object RootPropertyResolver.getProperty(String)"})
  void testGetProperty() {
    // Arrange, Act and Assert
    assertNull(new RootPropertyResolver().getProperty("Property"));
  }

  /**
   * Test {@link RootPropertyResolver#setProperty(String, Object)}.
   *
   * <p>Method under test: {@link RootPropertyResolver#setProperty(String, Object)}
   */
  @Test
  @DisplayName("Test setProperty(String, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void RootPropertyResolver.setProperty(String, Object)"})
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
   *
   * <p>Method under test: {@link RootPropertyResolver#isProperty(String)}
   */
  @Test
  @DisplayName("Test isProperty(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean RootPropertyResolver.isProperty(String)"})
  void testIsProperty() {
    // Arrange, Act and Assert
    assertFalse(new RootPropertyResolver().isProperty("Property"));
  }

  /**
   * Test {@link RootPropertyResolver#properties()}.
   *
   * <p>Method under test: {@link RootPropertyResolver#properties()}
   */
  @Test
  @DisplayName("Test properties()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Iterable RootPropertyResolver.properties()"})
  void testProperties() {
    // Arrange and Act
    Iterable<String> actualPropertiesResult = new RootPropertyResolver().properties();
    Iterator<String> actualIteratorResult = actualPropertiesResult.iterator();

    // Assert
    assertTrue(actualPropertiesResult instanceof Set);
    assertFalse(actualIteratorResult.hasNext());
    assertTrue(((Set<String>) actualPropertiesResult).isEmpty());
  }
}
