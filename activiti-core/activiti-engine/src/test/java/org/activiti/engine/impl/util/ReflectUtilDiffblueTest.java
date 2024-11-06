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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import org.activiti.engine.ActivitiClassLoadingException;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

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
  public void testGetResource_whenName_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(ReflectUtil.getResource("Name"));
  }

  /**
   * Test {@link ReflectUtil#instantiate(String, Object[])} with
   * {@code className}, {@code args}.
   * <ul>
   *   <li>Then throw {@link ActivitiClassLoadingException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReflectUtil#instantiate(String, Object[])}
   */
  @Test
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
  public void testInstantiateWithClassName_whenClassName_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> ReflectUtil.instantiate("Class Name"));
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
  public void testInvoke_whenEmptyArrayOfObject_thenReturnNull() {
    // Arrange, Act and Assert
    assertEquals("null", ReflectUtil.invoke(JSONObject.NULL, "toString", new Object[]{}));
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
  public void testInvoke_whenToString_thenReturnNull() {
    // Arrange, Act and Assert
    assertEquals("null", ReflectUtil.invoke(JSONObject.NULL, "toString", null));
  }

  /**
   * Test {@link ReflectUtil#getField(String, Class)} with {@code fieldName},
   * {@code clazz}.
   * <ul>
   *   <li>When {@code Field Name}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReflectUtil#getField(String, Class)}
   */
  @Test
  public void testGetFieldWithFieldNameClazz_whenFieldName_thenReturnNull() {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act and Assert
    assertNull(ReflectUtil.getField("Field Name", clazz));
  }

  /**
   * Test {@link ReflectUtil#getField(String, Object)} with {@code fieldName},
   * {@code object}.
   * <ul>
   *   <li>When {@code Field Name}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ReflectUtil#getField(String, Object)}
   */
  @Test
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
  public void testGetSetter_whenFieldName_thenReturnNull() {
    // Arrange
    Class<Object> clazz = Object.class;
    Class<Object> fieldType = Object.class;

    // Act and Assert
    assertNull(ReflectUtil.getSetter("Field Name", clazz, fieldType));
  }
}
