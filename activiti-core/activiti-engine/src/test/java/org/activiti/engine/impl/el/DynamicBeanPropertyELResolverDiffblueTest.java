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
package org.activiti.engine.impl.el;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.el.ELContext;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DynamicBeanPropertyELResolverDiffblueTest {
  /**
   * Test {@link DynamicBeanPropertyELResolver#DynamicBeanPropertyELResolver(Class, String,
   * String)}.
   *
   * <ul>
   *   <li>When {@link Object}.
   *   <li>Then return not {@link DynamicBeanPropertyELResolver#readOnly}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBeanPropertyELResolver#DynamicBeanPropertyELResolver(Class,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBeanPropertyELResolver.<init>(Class, String, String)",
    "void DynamicBeanPropertyELResolver.<init>(boolean, Class, String, String)"
  })
  public void testNewDynamicBeanPropertyELResolver_whenObject_thenReturnNotReadOnly() {
    // Arrange
    Class<Object> subject = Object.class;

    // Act
    DynamicBeanPropertyELResolver actualDynamicBeanPropertyELResolver =
        new DynamicBeanPropertyELResolver(subject, "Read Method Name", "Write Method Name");

    // Assert
    assertEquals("Read Method Name", actualDynamicBeanPropertyELResolver.readMethodName);
    assertEquals("Write Method Name", actualDynamicBeanPropertyELResolver.writeMethodName);
    assertFalse(actualDynamicBeanPropertyELResolver.readOnly);
    Class<Object> expectedResultClass = Object.class;
    assertEquals(expectedResultClass, actualDynamicBeanPropertyELResolver.subject);
  }

  /**
   * Test {@link DynamicBeanPropertyELResolver#DynamicBeanPropertyELResolver(boolean, Class, String,
   * String)}.
   *
   * <ul>
   *   <li>When {@code true}.
   *   <li>Then return {@link DynamicBeanPropertyELResolver#readOnly}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DynamicBeanPropertyELResolver#DynamicBeanPropertyELResolver(boolean, Class, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DynamicBeanPropertyELResolver.<init>(Class, String, String)",
    "void DynamicBeanPropertyELResolver.<init>(boolean, Class, String, String)"
  })
  public void testNewDynamicBeanPropertyELResolver_whenTrue_thenReturnReadOnly() {
    // Arrange
    Class<Object> subject = Object.class;

    // Act
    DynamicBeanPropertyELResolver actualDynamicBeanPropertyELResolver =
        new DynamicBeanPropertyELResolver(true, subject, "Read Method Name", "Write Method Name");

    // Assert
    assertEquals("Read Method Name", actualDynamicBeanPropertyELResolver.readMethodName);
    assertEquals("Write Method Name", actualDynamicBeanPropertyELResolver.writeMethodName);
    assertTrue(actualDynamicBeanPropertyELResolver.readOnly);
    Class<Object> expectedResultClass = Object.class;
    assertEquals(expectedResultClass, actualDynamicBeanPropertyELResolver.subject);
  }

  /**
   * Test {@link DynamicBeanPropertyELResolver#getCommonPropertyType(ELContext, Object)}.
   *
   * <ul>
   *   <li>Given {@code Object}.
   *   <li>Then return {@link Object}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBeanPropertyELResolver#getCommonPropertyType(ELContext,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Class DynamicBeanPropertyELResolver.getCommonPropertyType(ELContext, Object)"
  })
  public void testGetCommonPropertyType_givenJavaLangObject_thenReturnObject() {
    // Arrange
    Class<Object> subject = Object.class;
    DynamicBeanPropertyELResolver dynamicBeanPropertyELResolver =
        new DynamicBeanPropertyELResolver(subject, "Read Method Name", "Write Method Name");

    // Act
    Class<?> actualCommonPropertyType =
        dynamicBeanPropertyELResolver.getCommonPropertyType(
            new ParsingElContext(), JSONObject.NULL);

    // Assert
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, actualCommonPropertyType);
  }

  /**
   * Test {@link DynamicBeanPropertyELResolver#getFeatureDescriptors(ELContext, Object)}.
   *
   * <p>Method under test: {@link DynamicBeanPropertyELResolver#getFeatureDescriptors(ELContext,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.util.Iterator DynamicBeanPropertyELResolver.getFeatureDescriptors(ELContext, Object)"
  })
  public void testGetFeatureDescriptors() {
    // Arrange
    Class<Object> subject = Object.class;
    DynamicBeanPropertyELResolver dynamicBeanPropertyELResolver =
        new DynamicBeanPropertyELResolver(subject, "Read Method Name", "Write Method Name");

    // Act and Assert
    assertNull(
        dynamicBeanPropertyELResolver.getFeatureDescriptors(
            new ParsingElContext(), JSONObject.NULL));
  }

  /**
   * Test {@link DynamicBeanPropertyELResolver#getType(ELContext, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@code Object}.
   *   <li>Then {@link ParsingElContext} (default constructor) PropertyResolved.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBeanPropertyELResolver#getType(ELContext, Object, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Class DynamicBeanPropertyELResolver.getType(ELContext, Object, Object)"})
  public void testGetType_givenJavaLangObject_thenParsingElContextPropertyResolved() {
    // Arrange
    Class<Object> subject = Object.class;
    DynamicBeanPropertyELResolver dynamicBeanPropertyELResolver =
        new DynamicBeanPropertyELResolver(subject, "Read Method Name", "Write Method Name");
    ParsingElContext context = new ParsingElContext();

    // Act
    Class<?> actualType =
        dynamicBeanPropertyELResolver.getType(context, JSONObject.NULL, JSONObject.NULL);

    // Assert
    assertTrue(context.isPropertyResolved());
    Class<Object> expectedType = Object.class;
    assertEquals(expectedType, actualType);
  }

  /**
   * Test {@link DynamicBeanPropertyELResolver#getType(ELContext, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@code Object}.
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBeanPropertyELResolver#getType(ELContext, Object, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Class DynamicBeanPropertyELResolver.getType(ELContext, Object, Object)"})
  public void testGetType_givenJavaLangObject_whenNull_thenReturnNull() {
    // Arrange
    Class<Object> subject = Object.class;
    DynamicBeanPropertyELResolver dynamicBeanPropertyELResolver =
        new DynamicBeanPropertyELResolver(subject, "Read Method Name", "Write Method Name");
    ParsingElContext context = new ParsingElContext();

    // Act and Assert
    assertNull(dynamicBeanPropertyELResolver.getType(context, null, JSONObject.NULL));
    assertFalse(context.isPropertyResolved());
  }

  /**
   * Test {@link DynamicBeanPropertyELResolver#getValue(ELContext, Object, Object)}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBeanPropertyELResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object DynamicBeanPropertyELResolver.getValue(ELContext, Object, Object)"})
  public void testGetValue_thenReturnFalse() {
    // Arrange
    Class<Object> subject = Object.class;
    DynamicBeanPropertyELResolver dynamicBeanPropertyELResolver =
        new DynamicBeanPropertyELResolver(subject, "equals", "Write Method Name");
    ParsingElContext context = new ParsingElContext();

    // Act and Assert
    assertFalse(
        (Boolean)
            dynamicBeanPropertyELResolver.getValue(context, JSONObject.NULL, JSONObject.NULL));
    assertTrue(context.isPropertyResolved());
  }

  /**
   * Test {@link DynamicBeanPropertyELResolver#getValue(ELContext, Object, Object)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBeanPropertyELResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object DynamicBeanPropertyELResolver.getValue(ELContext, Object, Object)"})
  public void testGetValue_thenReturnNull() {
    // Arrange
    Class<Object> subject = Object.class;
    DynamicBeanPropertyELResolver dynamicBeanPropertyELResolver =
        new DynamicBeanPropertyELResolver(subject, "Read Method Name", "Write Method Name");
    ParsingElContext context = new ParsingElContext();

    // Act and Assert
    assertNull(dynamicBeanPropertyELResolver.getValue(context, null, JSONObject.NULL));
    assertFalse(context.isPropertyResolved());
  }

  /**
   * Test {@link DynamicBeanPropertyELResolver#getValue(ELContext, Object, Object)}.
   *
   * <ul>
   *   <li>Then return {@code String[]}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBeanPropertyELResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object DynamicBeanPropertyELResolver.getValue(ELContext, Object, Object)"})
  public void testGetValue_thenReturnString() {
    // Arrange
    Class<Object> subject = Object.class;
    DynamicBeanPropertyELResolver dynamicBeanPropertyELResolver =
        new DynamicBeanPropertyELResolver(subject, "split", "Write Method Name");
    ParsingElContext context = new ParsingElContext();

    // Act
    Object actualValue = dynamicBeanPropertyELResolver.getValue(context, "null", JSONObject.NULL);

    // Assert
    assertTrue(actualValue instanceof String[]);
    assertEquals(0, ((String[]) actualValue).length);
    assertTrue(context.isPropertyResolved());
  }

  /**
   * Test {@link DynamicBeanPropertyELResolver#getValue(ELContext, Object, Object)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBeanPropertyELResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object DynamicBeanPropertyELResolver.getValue(ELContext, Object, Object)"})
  public void testGetValue_thenReturnTrue() {
    // Arrange
    Class<Object> subject = Object.class;
    DynamicBeanPropertyELResolver dynamicBeanPropertyELResolver =
        new DynamicBeanPropertyELResolver(subject, "equals", "Write Method Name");
    ParsingElContext context = new ParsingElContext();

    // Act
    Object actualValue = dynamicBeanPropertyELResolver.getValue(context, "null", JSONObject.NULL);

    // Assert
    assertTrue(context.isPropertyResolved());
    assertTrue((Boolean) actualValue);
  }

  /**
   * Test {@link DynamicBeanPropertyELResolver#isReadOnly(ELContext, Object, Object)}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBeanPropertyELResolver#isReadOnly(ELContext, Object,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DynamicBeanPropertyELResolver.isReadOnly(ELContext, Object, Object)"})
  public void testIsReadOnly_thenReturnFalse() {
    // Arrange
    Class<Object> subject = Object.class;
    DynamicBeanPropertyELResolver dynamicBeanPropertyELResolver =
        new DynamicBeanPropertyELResolver(subject, "Read Method Name", "Write Method Name");

    // Act and Assert
    assertFalse(
        dynamicBeanPropertyELResolver.isReadOnly(
            new ParsingElContext(), JSONObject.NULL, JSONObject.NULL));
  }

  /**
   * Test {@link DynamicBeanPropertyELResolver#isReadOnly(ELContext, Object, Object)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link DynamicBeanPropertyELResolver#isReadOnly(ELContext, Object,
   * Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DynamicBeanPropertyELResolver.isReadOnly(ELContext, Object, Object)"})
  public void testIsReadOnly_thenReturnTrue() {
    // Arrange
    Class<Object> subject = Object.class;
    DynamicBeanPropertyELResolver dynamicBeanPropertyELResolver =
        new DynamicBeanPropertyELResolver(true, subject, "Read Method Name", "Write Method Name");

    // Act and Assert
    assertTrue(
        dynamicBeanPropertyELResolver.isReadOnly(
            new ParsingElContext(), JSONObject.NULL, JSONObject.NULL));
  }
}
