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
import jakarta.el.ELContext;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class DynamicBeanPropertyELResolverDiffblueTest {
  /**
   * Test
   * {@link DynamicBeanPropertyELResolver#DynamicBeanPropertyELResolver(Class, String, String)}.
   * <ul>
   *   <li>When {@link Object}.</li>
   *   <li>Then return not {@link DynamicBeanPropertyELResolver#readOnly}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBeanPropertyELResolver#DynamicBeanPropertyELResolver(Class, String, String)}
   */
  @Test
  public void testNewDynamicBeanPropertyELResolver_whenObject_thenReturnNotReadOnly() {
    // Arrange
    Class<Object> subject = Object.class;

    // Act
    DynamicBeanPropertyELResolver actualDynamicBeanPropertyELResolver = new DynamicBeanPropertyELResolver(subject,
        "Read Method Name", "Write Method Name");

    // Assert
    assertEquals("Read Method Name", actualDynamicBeanPropertyELResolver.readMethodName);
    assertEquals("Write Method Name", actualDynamicBeanPropertyELResolver.writeMethodName);
    assertFalse(actualDynamicBeanPropertyELResolver.readOnly);
    Class<Object> expectedResultClass = Object.class;
    assertEquals(expectedResultClass, actualDynamicBeanPropertyELResolver.subject);
  }

  /**
   * Test
   * {@link DynamicBeanPropertyELResolver#DynamicBeanPropertyELResolver(boolean, Class, String, String)}.
   * <ul>
   *   <li>When {@code true}.</li>
   *   <li>Then return {@link DynamicBeanPropertyELResolver#readOnly}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBeanPropertyELResolver#DynamicBeanPropertyELResolver(boolean, Class, String, String)}
   */
  @Test
  public void testNewDynamicBeanPropertyELResolver_whenTrue_thenReturnReadOnly() {
    // Arrange
    Class<Object> subject = Object.class;

    // Act
    DynamicBeanPropertyELResolver actualDynamicBeanPropertyELResolver = new DynamicBeanPropertyELResolver(true, subject,
        "Read Method Name", "Write Method Name");

    // Assert
    assertEquals("Read Method Name", actualDynamicBeanPropertyELResolver.readMethodName);
    assertEquals("Write Method Name", actualDynamicBeanPropertyELResolver.writeMethodName);
    assertTrue(actualDynamicBeanPropertyELResolver.readOnly);
    Class<Object> expectedResultClass = Object.class;
    assertEquals(expectedResultClass, actualDynamicBeanPropertyELResolver.subject);
  }

  /**
   * Test
   * {@link DynamicBeanPropertyELResolver#getCommonPropertyType(ELContext, Object)}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>Then return {@link Object}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBeanPropertyELResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  public void testGetCommonPropertyType_givenJavaLangObject_thenReturnObject() {
    // Arrange
    Class<Object> subject = Object.class;
    DynamicBeanPropertyELResolver dynamicBeanPropertyELResolver = new DynamicBeanPropertyELResolver(subject,
        "Read Method Name", "Write Method Name");

    // Act
    Class<?> actualCommonPropertyType = dynamicBeanPropertyELResolver.getCommonPropertyType(new ParsingElContext(),
        JSONObject.NULL);

    // Assert
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, actualCommonPropertyType);
  }

  /**
   * Test
   * {@link DynamicBeanPropertyELResolver#getFeatureDescriptors(ELContext, Object)}.
   * <p>
   * Method under test:
   * {@link DynamicBeanPropertyELResolver#getFeatureDescriptors(ELContext, Object)}
   */
  @Test
  public void testGetFeatureDescriptors() {
    // Arrange
    Class<Object> subject = Object.class;
    DynamicBeanPropertyELResolver dynamicBeanPropertyELResolver = new DynamicBeanPropertyELResolver(subject,
        "Read Method Name", "Write Method Name");

    // Act and Assert
    assertNull(dynamicBeanPropertyELResolver.getFeatureDescriptors(new ParsingElContext(), JSONObject.NULL));
  }

  /**
   * Test
   * {@link DynamicBeanPropertyELResolver#getType(ELContext, Object, Object)}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>Then {@link ParsingElContext} (default constructor)
   * PropertyResolved.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBeanPropertyELResolver#getType(ELContext, Object, Object)}
   */
  @Test
  public void testGetType_givenJavaLangObject_thenParsingElContextPropertyResolved() {
    // Arrange
    Class<Object> subject = Object.class;
    DynamicBeanPropertyELResolver dynamicBeanPropertyELResolver = new DynamicBeanPropertyELResolver(subject,
        "Read Method Name", "Write Method Name");
    ParsingElContext context = new ParsingElContext();

    // Act
    Class<?> actualType = dynamicBeanPropertyELResolver.getType(context, JSONObject.NULL, JSONObject.NULL);

    // Assert
    assertTrue(context.isPropertyResolved());
    Class<Object> expectedType = Object.class;
    assertEquals(expectedType, actualType);
  }

  /**
   * Test
   * {@link DynamicBeanPropertyELResolver#getType(ELContext, Object, Object)}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBeanPropertyELResolver#getType(ELContext, Object, Object)}
   */
  @Test
  public void testGetType_givenJavaLangObject_whenNull_thenReturnNull() {
    // Arrange
    Class<Object> subject = Object.class;
    DynamicBeanPropertyELResolver dynamicBeanPropertyELResolver = new DynamicBeanPropertyELResolver(subject,
        "Read Method Name", "Write Method Name");
    ParsingElContext context = new ParsingElContext();

    // Act and Assert
    assertNull(dynamicBeanPropertyELResolver.getType(context, null, JSONObject.NULL));
    assertFalse(context.isPropertyResolved());
  }

  /**
   * Test
   * {@link DynamicBeanPropertyELResolver#getValue(ELContext, Object, Object)}.
   * <ul>
   *   <li>Then {@link ParsingElContext} (default constructor)
   * PropertyResolved.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBeanPropertyELResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  public void testGetValue_thenParsingElContextPropertyResolved() {
    // Arrange
    Class<Object> subject = Object.class;
    DynamicBeanPropertyELResolver dynamicBeanPropertyELResolver = new DynamicBeanPropertyELResolver(subject, "equals",
        "Write Method Name");
    ParsingElContext context = new ParsingElContext();

    // Act
    dynamicBeanPropertyELResolver.getValue(context, JSONObject.NULL, JSONObject.NULL);

    // Assert
    assertTrue(context.isPropertyResolved());
  }

  /**
   * Test
   * {@link DynamicBeanPropertyELResolver#getValue(ELContext, Object, Object)}.
   * <ul>
   *   <li>Then {@link ParsingElContext} (default constructor)
   * PropertyResolved.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBeanPropertyELResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  public void testGetValue_thenParsingElContextPropertyResolved2() {
    // Arrange
    Class<Object> subject = Object.class;
    DynamicBeanPropertyELResolver dynamicBeanPropertyELResolver = new DynamicBeanPropertyELResolver(subject, "equals",
        "Write Method Name");
    ParsingElContext context = new ParsingElContext();

    // Act
    dynamicBeanPropertyELResolver.getValue(context, "null", JSONObject.NULL);

    // Assert
    assertTrue(context.isPropertyResolved());
  }

  /**
   * Test
   * {@link DynamicBeanPropertyELResolver#getValue(ELContext, Object, Object)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBeanPropertyELResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  public void testGetValue_thenReturnNull() {
    // Arrange
    Class<Object> subject = Object.class;
    DynamicBeanPropertyELResolver dynamicBeanPropertyELResolver = new DynamicBeanPropertyELResolver(subject,
        "Read Method Name", "Write Method Name");
    ParsingElContext context = new ParsingElContext();

    // Act and Assert
    assertNull(dynamicBeanPropertyELResolver.getValue(context, null, JSONObject.NULL));
    assertFalse(context.isPropertyResolved());
  }

  /**
   * Test
   * {@link DynamicBeanPropertyELResolver#isReadOnly(ELContext, Object, Object)}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBeanPropertyELResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  public void testIsReadOnly_thenReturnFalse() {
    // Arrange
    Class<Object> subject = Object.class;
    DynamicBeanPropertyELResolver dynamicBeanPropertyELResolver = new DynamicBeanPropertyELResolver(subject,
        "Read Method Name", "Write Method Name");

    // Act and Assert
    assertFalse(dynamicBeanPropertyELResolver.isReadOnly(new ParsingElContext(), JSONObject.NULL, JSONObject.NULL));
  }

  /**
   * Test
   * {@link DynamicBeanPropertyELResolver#isReadOnly(ELContext, Object, Object)}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DynamicBeanPropertyELResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  public void testIsReadOnly_thenReturnTrue() {
    // Arrange
    Class<Object> subject = Object.class;
    DynamicBeanPropertyELResolver dynamicBeanPropertyELResolver = new DynamicBeanPropertyELResolver(true, subject,
        "Read Method Name", "Write Method Name");

    // Act and Assert
    assertTrue(dynamicBeanPropertyELResolver.isReadOnly(new ParsingElContext(), JSONObject.NULL, JSONObject.NULL));
  }
}
