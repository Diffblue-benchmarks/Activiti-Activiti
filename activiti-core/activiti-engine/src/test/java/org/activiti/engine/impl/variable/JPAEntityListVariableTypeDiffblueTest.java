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
package org.activiti.engine.impl.variable;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import ch.qos.logback.core.util.COWArrayList;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class JPAEntityListVariableTypeDiffblueTest {
  /**
   * Test new {@link JPAEntityListVariableType} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link JPAEntityListVariableType}
   */
  @Test
  public void testNewJPAEntityListVariableType() {
    // Arrange and Act
    JPAEntityListVariableType actualJpaEntityListVariableType = new JPAEntityListVariableType();

    // Assert
    assertFalse(actualJpaEntityListVariableType.isCachable());
    assertEquals(JPAEntityListVariableType.TYPE_NAME, actualJpaEntityListVariableType.getTypeName());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link JPAEntityListVariableType#setForceCacheable(boolean)}
   *   <li>{@link JPAEntityListVariableType#getTypeName()}
   *   <li>{@link JPAEntityListVariableType#isCachable()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    JPAEntityListVariableType jpaEntityListVariableType = new JPAEntityListVariableType();

    // Act
    jpaEntityListVariableType.setForceCacheable(true);
    String actualTypeName = jpaEntityListVariableType.getTypeName();

    // Assert that nothing has changed
    assertTrue(jpaEntityListVariableType.isCachable());
    assertEquals(JPAEntityListVariableType.TYPE_NAME, actualTypeName);
  }

  /**
   * Test {@link JPAEntityListVariableType#isAbleToStore(Object)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityListVariableType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_givenArrayListAddNull_thenReturnFalse() {
    // Arrange
    JPAEntityListVariableType jpaEntityListVariableType = new JPAEntityListVariableType();

    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(JSONObject.NULL);
    COWArrayList<Object> objectList2 = mock(COWArrayList.class);
    when(objectList2.iterator()).thenReturn(objectList.iterator());
    when(objectList2.get(anyInt())).thenReturn(JSONObject.NULL);
    when(objectList2.size()).thenReturn(3);

    // Act
    boolean actualIsAbleToStoreResult = jpaEntityListVariableType.isAbleToStore(objectList2);

    // Assert
    verify(objectList2).get(eq(0));
    verify(objectList2).iterator();
    verify(objectList2).size();
    assertFalse(actualIsAbleToStoreResult);
  }

  /**
   * Test {@link JPAEntityListVariableType#isAbleToStore(Object)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityListVariableType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_givenArrayListAddNull_thenReturnFalse2() {
    // Arrange
    JPAEntityListVariableType jpaEntityListVariableType = new JPAEntityListVariableType();

    ArrayList<Object> objectList = new ArrayList<>();
    objectList.add(null);
    COWArrayList<Object> objectList2 = mock(COWArrayList.class);
    when(objectList2.iterator()).thenReturn(objectList.iterator());
    when(objectList2.get(anyInt())).thenReturn(JSONObject.NULL);
    when(objectList2.size()).thenReturn(3);

    // Act
    boolean actualIsAbleToStoreResult = jpaEntityListVariableType.isAbleToStore(objectList2);

    // Assert
    verify(objectList2).get(eq(0));
    verify(objectList2).iterator();
    verify(objectList2).size();
    assertFalse(actualIsAbleToStoreResult);
  }

  /**
   * Test {@link JPAEntityListVariableType#isAbleToStore(Object)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} iterator.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityListVariableType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_givenArrayListIterator_thenReturnTrue() {
    // Arrange
    JPAEntityListVariableType jpaEntityListVariableType = new JPAEntityListVariableType();
    COWArrayList<Object> objectList = mock(COWArrayList.class);

    ArrayList<Object> objectList2 = new ArrayList<>();
    when(objectList.iterator()).thenReturn(objectList2.iterator());
    when(objectList.get(anyInt())).thenReturn(JSONObject.NULL);
    when(objectList.size()).thenReturn(3);

    // Act
    boolean actualIsAbleToStoreResult = jpaEntityListVariableType.isAbleToStore(objectList);

    // Assert
    verify(objectList).get(eq(0));
    verify(objectList).iterator();
    verify(objectList).size();
    assertTrue(actualIsAbleToStoreResult);
  }

  /**
   * Test {@link JPAEntityListVariableType#isAbleToStore(Object)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityListVariableType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_thenThrowActivitiException() {
    // Arrange
    JPAEntityListVariableType jpaEntityListVariableType = new JPAEntityListVariableType();
    COWArrayList<Object> objectList = mock(COWArrayList.class);
    when(objectList.iterator()).thenThrow(new ActivitiException("An error occurred"));
    when(objectList.get(anyInt())).thenReturn(JSONObject.NULL);
    when(objectList.size()).thenReturn(3);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jpaEntityListVariableType.isAbleToStore(objectList));
    verify(objectList).get(eq(0));
    verify(objectList).iterator();
    verify(objectList).size();
  }

  /**
   * Test {@link JPAEntityListVariableType#isAbleToStore(Object)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityListVariableType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_whenArrayList_thenReturnFalse() {
    // Arrange
    JPAEntityListVariableType jpaEntityListVariableType = new JPAEntityListVariableType();

    // Act and Assert
    assertFalse(jpaEntityListVariableType.isAbleToStore(new ArrayList<>()));
  }

  /**
   * Test {@link JPAEntityListVariableType#isAbleToStore(Object)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityListVariableType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new JPAEntityListVariableType()).isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test {@link JPAEntityListVariableType#getValue(ValueFields)}.
   * <ul>
   *   <li>Given {@code AXAXAXAX} Bytes is {@code UTF-8}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityListVariableType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_givenAxaxaxaxBytesIsUtf8_thenThrowActivitiException() throws UnsupportedEncodingException {
    // Arrange
    JPAEntityListVariableType jpaEntityListVariableType = new JPAEntityListVariableType();
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getBytes()).thenReturn("AXAXAXAX".getBytes("UTF-8"));
    when(valueFields.getTextValue()).thenReturn("42");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jpaEntityListVariableType.getValue(valueFields));
    verify(valueFields).getBytes();
    verify(valueFields, atLeast(1)).getTextValue();
  }

  /**
   * Test {@link JPAEntityListVariableType#getValue(ValueFields)}.
   * <ul>
   *   <li>Given {@link JPAEntityListVariableType} (default constructor)
   * ForceCacheable is {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityListVariableType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_givenJPAEntityListVariableTypeForceCacheableIsTrue() {
    // Arrange
    JPAEntityListVariableType jpaEntityListVariableType = new JPAEntityListVariableType();
    jpaEntityListVariableType.setForceCacheable(true);

    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();
    valueFields.setActivityInstanceId("42");
    valueFields.setCachedValue(JSONObject.NULL);
    valueFields.setDeleted(true);
    valueFields.setDetailType("Detail Type");
    valueFields.setDoubleValue(10.0d);
    valueFields.setExecutionId("42");
    valueFields.setId("42");
    valueFields.setInserted(true);
    valueFields.setLongValue(42L);
    valueFields.setName("Name");
    valueFields.setProcessInstanceId("42");
    valueFields.setRevision(1);
    valueFields.setTaskId("42");
    valueFields.setTextValue2("42");
    valueFields.setTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setUpdated(true);
    valueFields.setVariableType(new BigDecimalType());
    valueFields.setTextValue("42");

    // Act and Assert
    assertNull(jpaEntityListVariableType.getValue(valueFields));
  }

  /**
   * Test {@link JPAEntityListVariableType#getValue(ValueFields)}.
   * <ul>
   *   <li>When {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default
   * constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityListVariableType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_whenHistoricDetailVariableInstanceUpdateEntityImpl_thenReturnNull() {
    // Arrange
    JPAEntityListVariableType jpaEntityListVariableType = new JPAEntityListVariableType();

    // Act and Assert
    assertNull(jpaEntityListVariableType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link JPAEntityListVariableType#serializeIds(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   *   <li>Then return fifty-first element is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityListVariableType#serializeIds(List)}
   */
  @Test
  public void testSerializeIds_given42_whenArrayListAdd42_thenReturnFiftyFirstElementIsZero() {
    // Arrange
    JPAEntityListVariableType jpaEntityListVariableType = new JPAEntityListVariableType();

    ArrayList<String> ids = new ArrayList<>();
    ids.add("42");
    ids.add("foo");

    // Act
    byte[] actualSerializeIdsResult = jpaEntityListVariableType.serializeIds(ids);

    // Assert
    assertEquals((byte) 0, actualSerializeIdsResult[50]);
    assertEquals((byte) 2, actualSerializeIdsResult[43]);
    assertEquals((byte) 2, actualSerializeIdsResult[46]);
    assertEquals((byte) 3, actualSerializeIdsResult[51]);
    assertEquals(55, actualSerializeIdsResult.length);
    assertEquals('2', actualSerializeIdsResult[48]);
    assertEquals('4', actualSerializeIdsResult[47]);
    assertEquals('f', actualSerializeIdsResult[52]);
    assertEquals('o', actualSerializeIdsResult[54]);
    assertEquals('o', actualSerializeIdsResult[Double.PRECISION]);
    assertEquals('t', actualSerializeIdsResult[49]);
  }

  /**
   * Test {@link JPAEntityListVariableType#serializeIds(List)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.</li>
   *   <li>Then return forty-fourth element is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityListVariableType#serializeIds(List)}
   */
  @Test
  public void testSerializeIds_givenFoo_whenArrayListAddFoo_thenReturnFortyFourthElementIsOne() {
    // Arrange
    JPAEntityListVariableType jpaEntityListVariableType = new JPAEntityListVariableType();

    ArrayList<String> ids = new ArrayList<>();
    ids.add("foo");

    // Act
    byte[] actualSerializeIdsResult = jpaEntityListVariableType.serializeIds(ids);

    // Assert
    assertEquals((byte) 1, actualSerializeIdsResult[43]);
    assertEquals((byte) 3, actualSerializeIdsResult[46]);
    assertEquals(50, actualSerializeIdsResult.length);
    assertEquals('f', actualSerializeIdsResult[47]);
    assertEquals('o', actualSerializeIdsResult[48]);
    assertEquals('o', actualSerializeIdsResult[49]);
  }

  /**
   * Test {@link JPAEntityListVariableType#serializeIds(List)}.
   * <ul>
   *   <li>Then return array of {@code byte} with minus eighty-four and minus
   * nineteen.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityListVariableType#serializeIds(List)}
   */
  @Test
  public void testSerializeIds_thenReturnArrayOfByteWithMinusEightyFourAndMinusNineteen() {
    // Arrange
    JPAEntityListVariableType jpaEntityListVariableType = new JPAEntityListVariableType();

    // Act and Assert
    assertArrayEquals(
        new byte[]{-84, -19, 0, 5, 'u', 'r', 0, 19, '[', 'L', 'j', 'a', 'v', 'a', '.', 'l', 'a', 'n', 'g', '.', 'S',
            't', 'r', 'i', 'n', 'g', ';', -83, -46, 'V', -25, -23, 29, '{', 'G', 2, 0, 0, 'x', 'p', 0, 0, 0, 0},
        jpaEntityListVariableType.serializeIds(new ArrayList<>()));
  }

  /**
   * Test {@link JPAEntityListVariableType#deserializeIds(byte[])}.
   * <p>
   * Method under test: {@link JPAEntityListVariableType#deserializeIds(byte[])}
   */
  @Test
  public void testDeserializeIds() throws UnsupportedEncodingException {
    // Arrange
    JPAEntityListVariableType jpaEntityListVariableType = new JPAEntityListVariableType();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jpaEntityListVariableType.deserializeIds("AXAXAXAX".getBytes("UTF-8")));
  }
}
