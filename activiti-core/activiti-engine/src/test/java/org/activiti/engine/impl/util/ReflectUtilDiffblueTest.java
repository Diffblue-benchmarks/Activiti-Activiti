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
package org.activiti.engine.impl.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.ActivitiClassLoadingException;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ReflectUtilDiffblueTest {
  /**
   * Test {@link ReflectUtil#loadClass(String)} with {@code className}.
   * <ul>
   *   <li>When {@code Class Name}.</li>
   *   <li>Then throw {@link ActivitiClassLoadingException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReflectUtil#loadClass(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Class ReflectUtil.loadClass(String)"})
  public void testLoadClassWithClassName_whenClassName_thenThrowActivitiClassLoadingException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiClassLoadingException.class, () -> ReflectUtil.loadClass("Class Name"));
  }

  /**
   * Test {@link ReflectUtil#getResourceAsStream(String)}.
   * <ul>
   *   <li>When {@code Name}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReflectUtil#getResourceAsStream(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.io.InputStream ReflectUtil.getResourceAsStream(String)"})
  public void testGetResourceAsStream_whenName_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(ReflectUtil.getResourceAsStream("Name"));
  }

  /**
   * Test {@link ReflectUtil#getResource(String)}.
   * <ul>
   *   <li>When {@code Name}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReflectUtil#getResource(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.net.URL ReflectUtil.getResource(String)"})
  public void testGetResource_whenName_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(ReflectUtil.getResource("Name"));
  }

  /**
   * Test {@link ReflectUtil#instantiate(String, Object[])} with {@code className}, {@code args}.
   * <ul>
   *   <li>Then throw {@link ActivitiClassLoadingException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReflectUtil#instantiate(String, Object[])}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object ReflectUtil.instantiate(String, Object[])"})
  public void testInstantiateWithClassNameArgs_thenThrowActivitiClassLoadingException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiClassLoadingException.class,
        () -> ReflectUtil.instantiate("Class Name", new Object[]{JSONObject.NULL}));
  }

  /**
   * Test {@link ReflectUtil#instantiate(String)} with {@code className}.
   * <ul>
   *   <li>When {@code Class Name}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReflectUtil#instantiate(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object ReflectUtil.instantiate(String)"})
  public void testInstantiateWithClassName_whenClassName_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> ReflectUtil.instantiate("Class Name"));
  }

  /**
   * Test {@link ReflectUtil#invoke(Object, String, Object[])}.
   * <ul>
   *   <li>When array of {@link Object} with {@code null}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReflectUtil#invoke(Object, String, Object[])}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object ReflectUtil.invoke(Object, String, Object[])"})
  public void testInvoke_whenArrayOfObjectWithNull_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((Boolean) ReflectUtil.invoke(JSONObject.NULL, "equals", new Object[]{null}));
  }

  /**
   * Test {@link ReflectUtil#invoke(Object, String, Object[])}.
   * <ul>
   *   <li>When empty array of {@link Object}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReflectUtil#invoke(Object, String, Object[])}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object ReflectUtil.invoke(Object, String, Object[])"})
  public void testInvoke_whenEmptyArrayOfObject_thenReturnNull() {
    // Arrange, Act and Assert
    assertEquals("null", ReflectUtil.invoke(JSONObject.NULL, "toString", new Object[]{}));
  }

  /**
   * Test {@link ReflectUtil#invoke(Object, String, Object[])}.
   * <ul>
   *   <li>When {@code equals}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReflectUtil#invoke(Object, String, Object[])}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object ReflectUtil.invoke(Object, String, Object[])"})
  public void testInvoke_whenEquals_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((Boolean) ReflectUtil.invoke(JSONObject.NULL, "equals", new Object[]{JSONObject.NULL}));
  }

  /**
   * Test {@link ReflectUtil#invoke(Object, String, Object[])}.
   * <ul>
   *   <li>When three.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReflectUtil#invoke(Object, String, Object[])}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object ReflectUtil.invoke(Object, String, Object[])"})
  public void testInvoke_whenThree_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((Boolean) ReflectUtil.invoke(3, "equals", new Object[]{JSONObject.NULL}));
  }

  /**
   * Test {@link ReflectUtil#invoke(Object, String, Object[])}.
   * <ul>
   *   <li>When {@code toString}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReflectUtil#invoke(Object, String, Object[])}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object ReflectUtil.invoke(Object, String, Object[])"})
  public void testInvoke_whenToString_thenReturnNull() {
    // Arrange, Act and Assert
    assertEquals("null", ReflectUtil.invoke(JSONObject.NULL, "toString", null));
  }

  /**
   * Test {@link ReflectUtil#getField(String, Class)} with {@code fieldName}, {@code clazz}.
   * <ul>
   *   <li>When {@code Field Name}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReflectUtil#getField(String, Class)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.reflect.Field ReflectUtil.getField(String, Class)"})
  public void testGetFieldWithFieldNameClazz_whenFieldName_thenReturnNull() {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act and Assert
    assertNull(ReflectUtil.getField("Field Name", clazz));
  }

  /**
   * Test {@link ReflectUtil#getField(String, Object)} with {@code fieldName}, {@code object}.
   * <ul>
   *   <li>When {@code Field Name}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReflectUtil#getField(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.reflect.Field ReflectUtil.getField(String, Object)"})
  public void testGetFieldWithFieldNameObject_whenFieldName_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(ReflectUtil.getField("Field Name", JSONObject.NULL));
  }

  /**
   * Test {@link ReflectUtil#getSetter(String, Class, Class)}.
   * <ul>
   *   <li>When {@code Field Name}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReflectUtil#getSetter(String, Class, Class)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.reflect.Method ReflectUtil.getSetter(String, Class, Class)"})
  public void testGetSetter_whenFieldName_thenReturnNull() {
    // Arrange
    Class<Object> clazz = Object.class;
    Class<Object> fieldType = Object.class;

    // Act and Assert
    assertNull(ReflectUtil.getSetter("Field Name", clazz, fieldType));
  }
}
