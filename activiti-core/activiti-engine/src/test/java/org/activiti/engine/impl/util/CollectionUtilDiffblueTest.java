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
   * Method under test: {@link CollectionUtil#map(Object[])}
   */
  @Test
  public void testMap() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> CollectionUtil.map(JSONObject.NULL));
    assertThrows(ActivitiIllegalArgumentException.class, () -> CollectionUtil.map(JSONObject.NULL, JSONObject.NULL));
  }

  /**
   * Method under test: {@link CollectionUtil#map(Object[])}
   */
  @Test
  public void testMap2() {
    // Arrange and Act
    Map<String, Object> actualMapResult = CollectionUtil.map();

    // Assert
    assertTrue(actualMapResult.isEmpty());
  }

  /**
   * Method under test: {@link CollectionUtil#map(Object[])}
   */
  @Test
  public void testMap3() {
    // Arrange and Act
    Map<String, Object> actualMapResult = CollectionUtil.map("Objects", JSONObject.NULL);

    // Assert
    assertEquals(1, actualMapResult.size());
    assertTrue(actualMapResult.containsKey("Objects"));
  }

  /**
   * Method under test: {@link CollectionUtil#map(Object[])}
   */
  @Test
  public void testMap4() {
    // Arrange and Act
    Map<String, Object> actualMapResult = CollectionUtil.map("Objects", null);

    // Assert
    assertEquals(1, actualMapResult.size());
    assertNull(actualMapResult.get("Objects"));
  }

  /**
   * Method under test: {@link CollectionUtil#mapOfClass(Class, Object[])}
   */
  @Test
  public void testMapOfClass() {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> CollectionUtil.mapOfClass(clazz, JSONObject.NULL));
  }

  /**
   * Method under test: {@link CollectionUtil#mapOfClass(Class, Object[])}
   */
  @Test
  public void testMapOfClass2() {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act
    Map<String, Object> actualMapOfClassResult = CollectionUtil.mapOfClass(clazz);

    // Assert
    assertTrue(actualMapOfClassResult.isEmpty());
  }

  /**
   * Method under test: {@link CollectionUtil#mapOfClass(Class, Object[])}
   */
  @Test
  public void testMapOfClass3() {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> CollectionUtil.mapOfClass(clazz, JSONObject.NULL, JSONObject.NULL));
  }

  /**
   * Method under test: {@link CollectionUtil#mapOfClass(Class, Object[])}
   */
  @Test
  public void testMapOfClass4() {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act
    Map<String, Object> actualMapOfClassResult = CollectionUtil.mapOfClass(clazz, "Objects", JSONObject.NULL);

    // Assert
    assertEquals(1, actualMapOfClassResult.size());
    assertTrue(actualMapOfClassResult.containsKey("Objects"));
  }

  /**
   * Method under test: {@link CollectionUtil#mapOfClass(Class, Object[])}
   */
  @Test
  public void testMapOfClass5() {
    // Arrange
    Class<String> clazz = String.class;

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> CollectionUtil.mapOfClass(clazz, "Objects", JSONObject.NULL));
  }

  /**
   * Method under test: {@link CollectionUtil#mapOfClass(Class, Object[])}
   */
  @Test
  public void testMapOfClass6() {
    // Arrange
    Class<Object> clazz = Object.class;

    // Act
    Map<String, Object> actualMapOfClassResult = CollectionUtil.mapOfClass(clazz, "Objects", null);

    // Assert
    assertEquals(1, actualMapOfClassResult.size());
    assertNull(actualMapOfClassResult.get("Objects"));
  }

  /**
   * Method under test: {@link CollectionUtil#isEmpty(Collection)}
   */
  @Test
  public void testIsEmpty() {
    // Arrange, Act and Assert
    assertTrue(CollectionUtil.isEmpty(new ArrayList<>()));
    assertTrue(CollectionUtil.isEmpty(null));
  }

  /**
   * Method under test: {@link CollectionUtil#isEmpty(Collection)}
   */
  @Test
  public void testIsEmpty2() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(JSONObject.NULL);

    // Act and Assert
    assertFalse(CollectionUtil.isEmpty(collection));
  }

  /**
   * Method under test: {@link CollectionUtil#isEmpty(Collection)}
   */
  @Test
  public void testIsEmpty3() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(JSONObject.NULL);
    collection.add(JSONObject.NULL);

    // Act and Assert
    assertFalse(CollectionUtil.isEmpty(collection));
  }

  /**
   * Method under test: {@link CollectionUtil#isNotEmpty(Collection)}
   */
  @Test
  public void testIsNotEmpty() {
    // Arrange, Act and Assert
    assertFalse(CollectionUtil.isNotEmpty(new ArrayList<>()));
    assertFalse(CollectionUtil.isNotEmpty(null));
  }

  /**
   * Method under test: {@link CollectionUtil#isNotEmpty(Collection)}
   */
  @Test
  public void testIsNotEmpty2() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(JSONObject.NULL);

    // Act and Assert
    assertTrue(CollectionUtil.isNotEmpty(collection));
  }

  /**
   * Method under test: {@link CollectionUtil#isNotEmpty(Collection)}
   */
  @Test
  public void testIsNotEmpty3() {
    // Arrange
    ArrayList<Object> collection = new ArrayList<>();
    collection.add(JSONObject.NULL);
    collection.add(JSONObject.NULL);

    // Act and Assert
    assertTrue(CollectionUtil.isNotEmpty(collection));
  }
}
