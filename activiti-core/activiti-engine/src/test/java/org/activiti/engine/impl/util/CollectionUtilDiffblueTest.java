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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CollectionUtilDiffblueTest {
  @InjectMocks
  private CollectionUtil collectionUtil;

  /**
   * Test {@link CollectionUtil#singletonMap(String, Object)}.
   * <p>
   * Method under test: {@link CollectionUtil#singletonMap(String, Object)}
   */
  @Test
  public void testSingletonMap() {
    // Arrange
    Object object = JSONObject.NULL;

    // Act
    Map<String, Object> actualSingletonMapResult = CollectionUtil.singletonMap("Key", object);

    // Assert
    assertEquals(1, actualSingletonMapResult.size());
    assertSame(object, actualSingletonMapResult.get("Key"));
  }

  /**
   * Test {@link CollectionUtil#map(Object[])}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtil#map(Object[])}
   */
  @Test
  public void testMap_thenReturnEmpty() {
    // Arrange and Act
    Map<String, Object> actualMapResult = CollectionUtil.map();

    // Assert
    assertTrue(actualMapResult.isEmpty());
  }

  /**
   * Test {@link CollectionUtil#map(Object[])}.
   * <ul>
   *   <li>When {@link JSONObject#NULL} and {@link JSONObject#NULL}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtil#map(Object[])}
   */
  @Test
  public void testMap_whenNullAndNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> CollectionUtil.map(JSONObject.NULL, JSONObject.NULL));
  }

  /**
   * Test {@link CollectionUtil#map(Object[])}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtil#map(Object[])}
   */
  @Test
  public void testMap_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> CollectionUtil.map(JSONObject.NULL));
  }

  /**
   * Test {@link CollectionUtil#map(Object[])}.
   * <ul>
   *   <li>When {@code Objects} and {@link JSONObject#NULL}.</li>
   *   <li>Then return containsKey {@code Objects}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtil#map(Object[])}
   */
  @Test
  public void testMap_whenObjectsAndNull_thenReturnContainsKeyObjects() {
    // Arrange and Act
    Map<String, Object> actualMapResult = CollectionUtil.map("Objects", JSONObject.NULL);

    // Assert
    assertEquals(1, actualMapResult.size());
    assertTrue(actualMapResult.containsKey("Objects"));
  }

  /**
   * Test {@link CollectionUtil#map(Object[])}.
   * <ul>
   *   <li>When {@code Objects} and {@code null}.</li>
   *   <li>Then return {@code Objects} is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtil#map(Object[])}
   */
  @Test
  public void testMap_whenObjectsAndNull_thenReturnObjectsIsNull() {
    // Arrange and Act
    Map<String, Object> actualMapResult = CollectionUtil.map("Objects", null);

    // Assert
    assertEquals(1, actualMapResult.size());
    assertNull(actualMapResult.get("Objects"));
  }

  /**
   * Test {@link CollectionUtil#mapOfClass(Class, Object[])}.
   * <ul>
   *   <li>When {@code java.lang.Object}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtil#mapOfClass(Class, Object[])}
   */
  @Test
  public void testMapOfClass_whenJavaLangObject_thenReturnEmpty() {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act
    Map<String, Object> actualMapOfClassResult = CollectionUtil.mapOfClass(clazz);

    // Assert
    assertTrue(actualMapOfClassResult.isEmpty());
  }

  /**
   * Test {@link CollectionUtil#mapOfClass(Class, Object[])}.
   * <ul>
   *   <li>When {@code java.lang.String}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtil#mapOfClass(Class, Object[])}
   */
  @Test
  public void testMapOfClass_whenJavaLangString_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    Class<String> clazz = String.class;

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> CollectionUtil.mapOfClass(clazz, "Objects", JSONObject.NULL));
  }

  /**
   * Test {@link CollectionUtil#mapOfClass(Class, Object[])}.
   * <ul>
   *   <li>When {@link JSONObject#NULL} and {@link JSONObject#NULL}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtil#mapOfClass(Class, Object[])}
   */
  @Test
  public void testMapOfClass_whenNullAndNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> CollectionUtil.mapOfClass(clazz, JSONObject.NULL, JSONObject.NULL));
  }

  /**
   * Test {@link CollectionUtil#mapOfClass(Class, Object[])}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtil#mapOfClass(Class, Object[])}
   */
  @Test
  public void testMapOfClass_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> CollectionUtil.mapOfClass(clazz, JSONObject.NULL));
  }

  /**
   * Test {@link CollectionUtil#mapOfClass(Class, Object[])}.
   * <ul>
   *   <li>When {@code Objects} and {@link JSONObject#NULL}.</li>
   *   <li>Then return containsKey {@code Objects}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtil#mapOfClass(Class, Object[])}
   */
  @Test
  public void testMapOfClass_whenObjectsAndNull_thenReturnContainsKeyObjects() {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act
    Map<String, Object> actualMapOfClassResult = CollectionUtil.mapOfClass(clazz, "Objects", JSONObject.NULL);

    // Assert
    assertEquals(1, actualMapOfClassResult.size());
    assertTrue(actualMapOfClassResult.containsKey("Objects"));
  }

  /**
   * Test {@link CollectionUtil#mapOfClass(Class, Object[])}.
   * <ul>
   *   <li>When {@code Objects} and {@code null}.</li>
   *   <li>Then return {@code Objects} is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtil#mapOfClass(Class, Object[])}
   */
  @Test
  public void testMapOfClass_whenObjectsAndNull_thenReturnObjectsIsNull() {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act
    Map<String, Object> actualMapOfClassResult = CollectionUtil.mapOfClass(clazz, "Objects", null);

    // Assert
    assertEquals(1, actualMapOfClassResult.size());
    assertNull(actualMapOfClassResult.get("Objects"));
  }

  /**
   * Test {@link CollectionUtil#isEmpty(Collection)}.
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtil#isEmpty(Collection)}
   */
  @Test
  public void testIsEmpty_givenNull_whenArrayListAddNull_thenReturnFalse() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(JSONObject.NULL);

    // Act and Assert
    assertFalse(CollectionUtil.isEmpty(collection));
  }

  /**
   * Test {@link CollectionUtil#isEmpty(Collection)}.
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtil#isEmpty(Collection)}
   */
  @Test
  public void testIsEmpty_givenNull_whenArrayListAddNull_thenReturnFalse2() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(JSONObject.NULL);
    collection.add(JSONObject.NULL);

    // Act and Assert
    assertFalse(CollectionUtil.isEmpty(collection));
  }

  /**
   * Test {@link CollectionUtil#isEmpty(Collection)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtil#isEmpty(Collection)}
   */
  @Test
  public void testIsEmpty_whenArrayList_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(CollectionUtil.isEmpty(new ArrayList<>()));
  }

  /**
   * Test {@link CollectionUtil#isEmpty(Collection)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtil#isEmpty(Collection)}
   */
  @Test
  public void testIsEmpty_whenNull_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(CollectionUtil.isEmpty(null));
  }

  /**
   * Test {@link CollectionUtil#isNotEmpty(Collection)}.
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtil#isNotEmpty(Collection)}
   */
  @Test
  public void testIsNotEmpty_givenNull_whenArrayListAddNull_thenReturnTrue() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(JSONObject.NULL);

    // Act and Assert
    assertTrue(CollectionUtil.isNotEmpty(collection));
  }

  /**
   * Test {@link CollectionUtil#isNotEmpty(Collection)}.
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtil#isNotEmpty(Collection)}
   */
  @Test
  public void testIsNotEmpty_givenNull_whenArrayListAddNull_thenReturnTrue2() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(JSONObject.NULL);
    collection.add(JSONObject.NULL);

    // Act and Assert
    assertTrue(CollectionUtil.isNotEmpty(collection));
  }

  /**
   * Test {@link CollectionUtil#isNotEmpty(Collection)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtil#isNotEmpty(Collection)}
   */
  @Test
  public void testIsNotEmpty_whenArrayList_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(CollectionUtil.isNotEmpty(new ArrayList<>()));
  }

  /**
   * Test {@link CollectionUtil#isNotEmpty(Collection)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CollectionUtil#isNotEmpty(Collection)}
   */
  @Test
  public void testIsNotEmpty_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(CollectionUtil.isNotEmpty(null));
  }
}
