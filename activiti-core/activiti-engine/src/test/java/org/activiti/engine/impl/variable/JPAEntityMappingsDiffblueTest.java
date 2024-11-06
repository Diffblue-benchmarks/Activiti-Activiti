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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JPAEntityMappingsDiffblueTest {
  @InjectMocks
  private JPAEntityMappings jPAEntityMappings;

  /**
   * Test {@link JPAEntityMappings#isJPAEntity(Object)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityMappings#isJPAEntity(Object)}
   */
  @Test
  public void testIsJPAEntity_whenNull() {
    // Arrange, Act and Assert
    assertFalse((new JPAEntityMappings()).isJPAEntity(JSONObject.NULL));
    assertFalse((new JPAEntityMappings()).isJPAEntity(null));
  }

  /**
   * Test {@link JPAEntityMappings#getEntityMetaData(Class)}.
   * <ul>
   *   <li>When {@code java.lang.Byte}.</li>
   *   <li>Then return EntityClass is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityMappings#getEntityMetaData(Class)}
   */
  @Test
  public void testGetEntityMetaData_whenJavaLangByte_thenReturnEntityClassIsNull() {
    // Arrange
    JPAEntityMappings jpaEntityMappings = new JPAEntityMappings();
    Class<Byte> clazz = Byte.class;

    // Act
    EntityMetaData actualEntityMetaData = jpaEntityMappings.getEntityMetaData(clazz);

    // Assert
    assertNull(actualEntityMetaData.getEntityClass());
    assertNull(actualEntityMetaData.getIdType());
    assertNull(actualEntityMetaData.getIdField());
    assertNull(actualEntityMetaData.getIdMethod());
    assertFalse(actualEntityMetaData.isJPAEntity());
  }

  /**
   * Test {@link JPAEntityMappings#getEntityMetaData(Class)}.
   * <ul>
   *   <li>When {@code java.lang.Object}.</li>
   *   <li>Then return EntityClass is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityMappings#getEntityMetaData(Class)}
   */
  @Test
  public void testGetEntityMetaData_whenJavaLangObject_thenReturnEntityClassIsNull() {
    // Arrange
    JPAEntityMappings jpaEntityMappings = new JPAEntityMappings();
    Class<Object> clazz = Object.class;

    // Act
    EntityMetaData actualEntityMetaData = jpaEntityMappings.getEntityMetaData(clazz);

    // Assert
    assertNull(actualEntityMetaData.getEntityClass());
    assertNull(actualEntityMetaData.getIdType());
    assertNull(actualEntityMetaData.getIdField());
    assertNull(actualEntityMetaData.getIdMethod());
    assertFalse(actualEntityMetaData.isJPAEntity());
  }

  /**
   * Test {@link JPAEntityMappings#getJPAClassString(Object)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityMappings#getJPAClassString(Object)}
   */
  @Test
  public void testGetJPAClassString_whenNull() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new JPAEntityMappings()).getJPAClassString(JSONObject.NULL));
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new JPAEntityMappings()).getJPAClassString(null));
  }

  /**
   * Test {@link JPAEntityMappings#getJPAIdString(Object)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityMappings#getJPAIdString(Object)}
   */
  @Test
  public void testGetJPAIdString_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new JPAEntityMappings()).getJPAIdString(JSONObject.NULL));
  }

  /**
   * Test {@link JPAEntityMappings#getIdValue(Object, EntityMetaData)}.
   * <p>
   * Method under test:
   * {@link JPAEntityMappings#getIdValue(Object, EntityMetaData)}
   */
  @Test
  public void testGetIdValue() {
    // Arrange
    JPAEntityMappings jpaEntityMappings = new JPAEntityMappings();

    EntityMetaData metaData = new EntityMetaData();
    Class<Object> entityClass = Object.class;
    metaData.setEntityClass(entityClass);
    metaData.setJPAEntity(true);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> jpaEntityMappings.getIdValue(JSONObject.NULL, metaData));
  }

  /**
   * Test {@link JPAEntityMappings#createId(EntityMetaData, String)}.
   * <ul>
   *   <li>Given {@code java.lang.Byte}.</li>
   *   <li>When {@link EntityMetaData} {@link EntityMetaData#getIdType()} return
   * {@link Byte}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityMappings#createId(EntityMetaData, String)}
   */
  @Test
  public void testCreateId_givenJavaLangByte_whenEntityMetaDataGetIdTypeReturnByte() {
    // Arrange
    EntityMetaData metaData = mock(EntityMetaData.class);
    Class<Byte> forNameResult = Byte.class;
    Mockito.<Class<?>>when(metaData.getIdType()).thenReturn(forNameResult);
    doNothing().when(metaData).setEntityClass(Mockito.<Class<Object>>any());
    doNothing().when(metaData).setJPAEntity(anyBoolean());
    Class<Object> entityClass = Object.class;
    metaData.setEntityClass(entityClass);
    metaData.setJPAEntity(true);

    // Act
    jPAEntityMappings.createId(metaData, "42");

    // Assert
    verify(metaData).getIdType();
    verify(metaData).setEntityClass(isA(Class.class));
    verify(metaData).setJPAEntity(eq(true));
  }

  /**
   * Test {@link JPAEntityMappings#createId(EntityMetaData, String)}.
   * <ul>
   *   <li>Given {@code java.lang.Character}.</li>
   *   <li>When {@link EntityMetaData} {@link EntityMetaData#getIdType()} return
   * {@link Character}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityMappings#createId(EntityMetaData, String)}
   */
  @Test
  public void testCreateId_givenJavaLangCharacter_whenEntityMetaDataGetIdTypeReturnCharacter() {
    // Arrange
    EntityMetaData metaData = mock(EntityMetaData.class);
    Class<Character> forNameResult = Character.class;
    Mockito.<Class<?>>when(metaData.getIdType()).thenReturn(forNameResult);
    doNothing().when(metaData).setEntityClass(Mockito.<Class<Object>>any());
    doNothing().when(metaData).setJPAEntity(anyBoolean());
    Class<Object> entityClass = Object.class;
    metaData.setEntityClass(entityClass);
    metaData.setJPAEntity(true);

    // Act
    jPAEntityMappings.createId(metaData, "String");

    // Assert
    verify(metaData).getIdType();
    verify(metaData).setEntityClass(isA(Class.class));
    verify(metaData).setJPAEntity(eq(true));
  }

  /**
   * Test {@link JPAEntityMappings#createId(EntityMetaData, String)}.
   * <ul>
   *   <li>Given {@code java.lang.Double}.</li>
   *   <li>Then return doubleValue is forty-two.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityMappings#createId(EntityMetaData, String)}
   */
  @Test
  public void testCreateId_givenJavaLangDouble_thenReturnDoubleValueIsFortyTwo() {
    // Arrange
    EntityMetaData metaData = mock(EntityMetaData.class);
    Class<Double> forNameResult = Double.class;
    Mockito.<Class<?>>when(metaData.getIdType()).thenReturn(forNameResult);
    doNothing().when(metaData).setEntityClass(Mockito.<Class<Object>>any());
    doNothing().when(metaData).setJPAEntity(anyBoolean());
    Class<Object> entityClass = Object.class;
    metaData.setEntityClass(entityClass);
    metaData.setJPAEntity(true);

    // Act
    Object actualCreateIdResult = jPAEntityMappings.createId(metaData, "42");

    // Assert
    verify(metaData).getIdType();
    verify(metaData).setEntityClass(isA(Class.class));
    verify(metaData).setJPAEntity(eq(true));
    assertEquals(42.0d, ((Double) actualCreateIdResult).doubleValue(), 0.0);
  }

  /**
   * Test {@link JPAEntityMappings#createId(EntityMetaData, String)}.
   * <ul>
   *   <li>Given {@code java.lang.Float}.</li>
   *   <li>Then return floatValue is forty-two.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityMappings#createId(EntityMetaData, String)}
   */
  @Test
  public void testCreateId_givenJavaLangFloat_thenReturnFloatValueIsFortyTwo() {
    // Arrange
    EntityMetaData metaData = mock(EntityMetaData.class);
    Class<Float> forNameResult = Float.class;
    Mockito.<Class<?>>when(metaData.getIdType()).thenReturn(forNameResult);
    doNothing().when(metaData).setEntityClass(Mockito.<Class<Object>>any());
    doNothing().when(metaData).setJPAEntity(anyBoolean());
    Class<Object> entityClass = Object.class;
    metaData.setEntityClass(entityClass);
    metaData.setJPAEntity(true);

    // Act
    Object actualCreateIdResult = jPAEntityMappings.createId(metaData, "42");

    // Assert
    verify(metaData).getIdType();
    verify(metaData).setEntityClass(isA(Class.class));
    verify(metaData).setJPAEntity(eq(true));
    assertEquals(42.0f, ((Float) actualCreateIdResult).floatValue(), 0.0f);
  }

  /**
   * Test {@link JPAEntityMappings#createId(EntityMetaData, String)}.
   * <ul>
   *   <li>Given {@code java.lang.Integer}.</li>
   *   <li>When {@link EntityMetaData} {@link EntityMetaData#getIdType()} return
   * {@link Integer}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityMappings#createId(EntityMetaData, String)}
   */
  @Test
  public void testCreateId_givenJavaLangInteger_whenEntityMetaDataGetIdTypeReturnInteger() {
    // Arrange
    EntityMetaData metaData = mock(EntityMetaData.class);
    Class<Integer> forNameResult = Integer.class;
    Mockito.<Class<?>>when(metaData.getIdType()).thenReturn(forNameResult);
    doNothing().when(metaData).setEntityClass(Mockito.<Class<Object>>any());
    doNothing().when(metaData).setJPAEntity(anyBoolean());
    Class<Object> entityClass = Object.class;
    metaData.setEntityClass(entityClass);
    metaData.setJPAEntity(true);

    // Act
    jPAEntityMappings.createId(metaData, "42");

    // Assert
    verify(metaData).getIdType();
    verify(metaData).setEntityClass(isA(Class.class));
    verify(metaData).setJPAEntity(eq(true));
  }

  /**
   * Test {@link JPAEntityMappings#createId(EntityMetaData, String)}.
   * <ul>
   *   <li>Given {@code java.lang.Long}.</li>
   *   <li>When {@link EntityMetaData} {@link EntityMetaData#getIdType()} return
   * {@link Long}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityMappings#createId(EntityMetaData, String)}
   */
  @Test
  public void testCreateId_givenJavaLangLong_whenEntityMetaDataGetIdTypeReturnLong() {
    // Arrange
    EntityMetaData metaData = mock(EntityMetaData.class);
    Class<Long> forNameResult = Long.class;
    Mockito.<Class<?>>when(metaData.getIdType()).thenReturn(forNameResult);
    doNothing().when(metaData).setEntityClass(Mockito.<Class<Object>>any());
    doNothing().when(metaData).setJPAEntity(anyBoolean());
    Class<Object> entityClass = Object.class;
    metaData.setEntityClass(entityClass);
    metaData.setJPAEntity(true);

    // Act
    jPAEntityMappings.createId(metaData, "42");

    // Assert
    verify(metaData).getIdType();
    verify(metaData).setEntityClass(isA(Class.class));
    verify(metaData).setJPAEntity(eq(true));
  }

  /**
   * Test {@link JPAEntityMappings#createId(EntityMetaData, String)}.
   * <ul>
   *   <li>Given {@code java.lang.Short}.</li>
   *   <li>When {@link EntityMetaData} {@link EntityMetaData#getIdType()} return
   * {@link Short}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityMappings#createId(EntityMetaData, String)}
   */
  @Test
  public void testCreateId_givenJavaLangShort_whenEntityMetaDataGetIdTypeReturnShort() {
    // Arrange
    EntityMetaData metaData = mock(EntityMetaData.class);
    Class<Short> forNameResult = Short.class;
    Mockito.<Class<?>>when(metaData.getIdType()).thenReturn(forNameResult);
    doNothing().when(metaData).setEntityClass(Mockito.<Class<Object>>any());
    doNothing().when(metaData).setJPAEntity(anyBoolean());
    Class<Object> entityClass = Object.class;
    metaData.setEntityClass(entityClass);
    metaData.setJPAEntity(true);

    // Act
    jPAEntityMappings.createId(metaData, "42");

    // Assert
    verify(metaData).getIdType();
    verify(metaData).setEntityClass(isA(Class.class));
    verify(metaData).setJPAEntity(eq(true));
  }

  /**
   * Test {@link JPAEntityMappings#createId(EntityMetaData, String)}.
   * <ul>
   *   <li>Given {@code java.lang.String}.</li>
   *   <li>Then return {@code String}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityMappings#createId(EntityMetaData, String)}
   */
  @Test
  public void testCreateId_givenJavaLangString_thenReturnString() {
    // Arrange
    EntityMetaData metaData = mock(EntityMetaData.class);
    Class<String> forNameResult = String.class;
    Mockito.<Class<?>>when(metaData.getIdType()).thenReturn(forNameResult);
    doNothing().when(metaData).setEntityClass(Mockito.<Class<Object>>any());
    doNothing().when(metaData).setJPAEntity(anyBoolean());
    Class<Object> entityClass = Object.class;
    metaData.setEntityClass(entityClass);
    metaData.setJPAEntity(true);

    // Act
    Object actualCreateIdResult = jPAEntityMappings.createId(metaData, "String");

    // Assert
    verify(metaData).getIdType();
    verify(metaData).setEntityClass(isA(Class.class));
    verify(metaData).setJPAEntity(eq(true));
    assertEquals("String", actualCreateIdResult);
  }

  /**
   * Test {@link JPAEntityMappings#createId(EntityMetaData, String)}.
   * <ul>
   *   <li>Given {@code java.math.BigDecimal}.</li>
   *   <li>When {@link EntityMetaData} {@link EntityMetaData#getIdType()} return
   * {@link BigDecimal}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityMappings#createId(EntityMetaData, String)}
   */
  @Test
  public void testCreateId_givenJavaMathBigDecimal_whenEntityMetaDataGetIdTypeReturnBigDecimal() {
    // Arrange
    EntityMetaData metaData = mock(EntityMetaData.class);
    Class<BigDecimal> forNameResult = BigDecimal.class;
    Mockito.<Class<?>>when(metaData.getIdType()).thenReturn(forNameResult);
    doNothing().when(metaData).setEntityClass(Mockito.<Class<Object>>any());
    doNothing().when(metaData).setJPAEntity(anyBoolean());
    Class<Object> entityClass = Object.class;
    metaData.setEntityClass(entityClass);
    metaData.setJPAEntity(true);

    // Act
    jPAEntityMappings.createId(metaData, "42");

    // Assert
    verify(metaData).getIdType();
    verify(metaData).setEntityClass(isA(Class.class));
    verify(metaData).setJPAEntity(eq(true));
  }

  /**
   * Test {@link JPAEntityMappings#createId(EntityMetaData, String)}.
   * <ul>
   *   <li>Given {@code java.math.BigInteger}.</li>
   *   <li>Then return {@link BigInteger}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityMappings#createId(EntityMetaData, String)}
   */
  @Test
  public void testCreateId_givenJavaMathBigInteger_thenReturnBigInteger() {
    // Arrange
    EntityMetaData metaData = mock(EntityMetaData.class);
    Class<BigInteger> forNameResult = BigInteger.class;
    Mockito.<Class<?>>when(metaData.getIdType()).thenReturn(forNameResult);
    doNothing().when(metaData).setEntityClass(Mockito.<Class<Object>>any());
    doNothing().when(metaData).setJPAEntity(anyBoolean());
    Class<Object> entityClass = Object.class;
    metaData.setEntityClass(entityClass);
    metaData.setJPAEntity(true);

    // Act
    Object actualCreateIdResult = jPAEntityMappings.createId(metaData, "42");

    // Assert
    verify(metaData).getIdType();
    verify(metaData).setEntityClass(isA(Class.class));
    verify(metaData).setJPAEntity(eq(true));
    assertTrue(actualCreateIdResult instanceof BigInteger);
    assertEquals("42", actualCreateIdResult.toString());
    assertEquals(1, ((BigInteger) actualCreateIdResult).getLowestSetBit());
    assertEquals(1, ((BigInteger) actualCreateIdResult).signum());
    assertArrayEquals(new byte[]{'*'}, ((BigInteger) actualCreateIdResult).toByteArray());
  }

  /**
   * Test {@link JPAEntityMappings#createId(EntityMetaData, String)}.
   * <ul>
   *   <li>Given {@code java.sql.Date}.</li>
   *   <li>When {@link EntityMetaData} {@link EntityMetaData#getIdType()} return
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityMappings#createId(EntityMetaData, String)}
   */
  @Test
  public void testCreateId_givenJavaSqlDate_whenEntityMetaDataGetIdTypeReturnDate() {
    // Arrange
    EntityMetaData metaData = mock(EntityMetaData.class);
    Class<Date> forNameResult = Date.class;
    Mockito.<Class<?>>when(metaData.getIdType()).thenReturn(forNameResult);
    doNothing().when(metaData).setEntityClass(Mockito.<Class<Object>>any());
    doNothing().when(metaData).setJPAEntity(anyBoolean());
    Class<Object> entityClass = Object.class;
    metaData.setEntityClass(entityClass);
    metaData.setJPAEntity(true);

    // Act
    jPAEntityMappings.createId(metaData, "42");

    // Assert
    verify(metaData).getIdType();
    verify(metaData).setEntityClass(isA(Class.class));
    verify(metaData).setJPAEntity(eq(true));
  }

  /**
   * Test {@link JPAEntityMappings#createId(EntityMetaData, String)}.
   * <ul>
   *   <li>Given {@code java.util.Date}.</li>
   *   <li>When {@link EntityMetaData} {@link EntityMetaData#getIdType()} return
   * {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityMappings#createId(EntityMetaData, String)}
   */
  @Test
  public void testCreateId_givenJavaUtilDate_whenEntityMetaDataGetIdTypeReturnDate() {
    // Arrange
    EntityMetaData metaData = mock(EntityMetaData.class);
    Class<java.util.Date> forNameResult = java.util.Date.class;
    Mockito.<Class<?>>when(metaData.getIdType()).thenReturn(forNameResult);
    doNothing().when(metaData).setEntityClass(Mockito.<Class<Object>>any());
    doNothing().when(metaData).setJPAEntity(anyBoolean());
    Class<Object> entityClass = Object.class;
    metaData.setEntityClass(entityClass);
    metaData.setJPAEntity(true);

    // Act
    jPAEntityMappings.createId(metaData, "42");

    // Assert
    verify(metaData).getIdType();
    verify(metaData).setEntityClass(isA(Class.class));
    verify(metaData).setJPAEntity(eq(true));
  }

  /**
   * Test {@link JPAEntityMappings#createId(EntityMetaData, String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityMappings#createId(EntityMetaData, String)}
   */
  @Test
  public void testCreateId_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    EntityMetaData metaData = mock(EntityMetaData.class);
    Class<Object> forNameResult = Object.class;
    Mockito.<Class<?>>when(metaData.getIdType()).thenReturn(forNameResult);
    doNothing().when(metaData).setEntityClass(Mockito.<Class<Object>>any());
    doNothing().when(metaData).setJPAEntity(anyBoolean());
    Class<Object> entityClass = Object.class;
    metaData.setEntityClass(entityClass);
    metaData.setJPAEntity(true);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> jPAEntityMappings.createId(metaData, "String"));
    verify(metaData).getIdType();
    verify(metaData).setEntityClass(isA(Class.class));
    verify(metaData).setJPAEntity(eq(true));
  }

  /**
   * Test {@link JPAEntityMappings#getIdString(Object)}.
   * <ul>
   *   <li>Given ten.</li>
   *   <li>When {@link Date} {@link java.util.Date#getTime()} return ten.</li>
   *   <li>Then return {@code 10}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityMappings#getIdString(Object)}
   */
  @Test
  public void testGetIdString_givenTen_whenDateGetTimeReturnTen_thenReturn10() {
    // Arrange
    JPAEntityMappings jpaEntityMappings = new JPAEntityMappings();
    java.sql.Date date = mock(java.sql.Date.class);
    when(date.getTime()).thenReturn(10L);

    // Act
    String actualIdString = jpaEntityMappings.getIdString(date);

    // Assert
    verify(date).getTime();
    assertEquals("10", actualIdString);
  }

  /**
   * Test {@link JPAEntityMappings#getIdString(Object)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityMappings#getIdString(Object)}
   */
  @Test
  public void testGetIdString_when42_thenReturn42() {
    // Arrange, Act and Assert
    assertEquals("42", (new JPAEntityMappings()).getIdString("42"));
  }

  /**
   * Test {@link JPAEntityMappings#getIdString(Object)}.
   * <ul>
   *   <li>When {@code A}.</li>
   *   <li>Then return {@code 65}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityMappings#getIdString(Object)}
   */
  @Test
  public void testGetIdString_whenA_thenReturn65() {
    // Arrange, Act and Assert
    assertEquals("65", (new JPAEntityMappings()).getIdString((byte) 'A'));
  }

  /**
   * Test {@link JPAEntityMappings#getIdString(Object)}.
   * <ul>
   *   <li>When {@code A}.</li>
   *   <li>Then return {@code A}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityMappings#getIdString(Object)}
   */
  @Test
  public void testGetIdString_whenA_thenReturnA() {
    // Arrange, Act and Assert
    assertEquals("A", (new JPAEntityMappings()).getIdString('A'));
  }

  /**
   * Test {@link JPAEntityMappings#getIdString(Object)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityMappings#getIdString(Object)}
   */
  @Test
  public void testGetIdString_whenFortyTwo_thenReturn42() {
    // Arrange, Act and Assert
    assertEquals("42", (new JPAEntityMappings()).getIdString(42L));
    assertEquals("42", (new JPAEntityMappings()).getIdString(42));
  }

  /**
   * Test {@link JPAEntityMappings#getIdString(Object)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityMappings#getIdString(Object)}
   */
  @Test
  public void testGetIdString_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new JPAEntityMappings()).getIdString(JSONObject.NULL));
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new JPAEntityMappings()).getIdString(null));
  }

  /**
   * Test {@link JPAEntityMappings#getIdString(Object)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@code 1}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityMappings#getIdString(Object)}
   */
  @Test
  public void testGetIdString_whenOne_thenReturn1() {
    // Arrange, Act and Assert
    assertEquals("1", (new JPAEntityMappings()).getIdString((short) 1));
  }

  /**
   * Test {@link JPAEntityMappings#getIdString(Object)}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then return {@code 10.0}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JPAEntityMappings#getIdString(Object)}
   */
  @Test
  public void testGetIdString_whenTen_thenReturn100() {
    // Arrange, Act and Assert
    assertEquals("10.0", (new JPAEntityMappings()).getIdString(10.0f));
    assertEquals("10.0", (new JPAEntityMappings()).getIdString(10.0d));
  }
}
