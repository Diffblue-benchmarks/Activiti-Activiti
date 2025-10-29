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
   * Method under test: {@link JPAEntityMappings#isJPAEntity(Object)}
   */
  @Test
  public void testIsJPAEntity() {
    // Arrange, Act and Assert
    assertFalse((new JPAEntityMappings()).isJPAEntity(JSONObject.NULL));
    assertFalse((new JPAEntityMappings()).isJPAEntity(null));
  }

  /**
   * Method under test: {@link JPAEntityMappings#getEntityMetaData(Class)}
   */
  @Test
  public void testGetEntityMetaData() {
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
   * Method under test: {@link JPAEntityMappings#getEntityMetaData(Class)}
   */
  @Test
  public void testGetEntityMetaData2() {
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
   * Method under test: {@link JPAEntityMappings#getJPAClassString(Object)}
   */
  @Test
  public void testGetJPAClassString() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new JPAEntityMappings()).getJPAClassString(JSONObject.NULL));
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new JPAEntityMappings()).getJPAClassString(null));
  }

  /**
   * Method under test: {@link JPAEntityMappings#getJPAIdString(Object)}
   */
  @Test
  public void testGetJPAIdString() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new JPAEntityMappings()).getJPAIdString(JSONObject.NULL));
  }

  /**
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
   * Method under test: {@link JPAEntityMappings#createId(EntityMetaData, String)}
   */
  @Test
  public void testCreateId() {
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
   * Method under test: {@link JPAEntityMappings#createId(EntityMetaData, String)}
   */
  @Test
  public void testCreateId2() {
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
   * Method under test: {@link JPAEntityMappings#createId(EntityMetaData, String)}
   */
  @Test
  public void testCreateId3() {
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
   * Method under test: {@link JPAEntityMappings#createId(EntityMetaData, String)}
   */
  @Test
  public void testCreateId4() {
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
   * Method under test: {@link JPAEntityMappings#createId(EntityMetaData, String)}
   */
  @Test
  public void testCreateId5() {
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
   * Method under test: {@link JPAEntityMappings#createId(EntityMetaData, String)}
   */
  @Test
  public void testCreateId6() {
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
   * Method under test: {@link JPAEntityMappings#createId(EntityMetaData, String)}
   */
  @Test
  public void testCreateId7() {
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
   * Method under test: {@link JPAEntityMappings#createId(EntityMetaData, String)}
   */
  @Test
  public void testCreateId8() {
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
   * Method under test: {@link JPAEntityMappings#createId(EntityMetaData, String)}
   */
  @Test
  public void testCreateId9() {
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
   * Method under test: {@link JPAEntityMappings#createId(EntityMetaData, String)}
   */
  @Test
  public void testCreateId10() {
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
   * Method under test: {@link JPAEntityMappings#createId(EntityMetaData, String)}
   */
  @Test
  public void testCreateId11() {
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
   * Method under test: {@link JPAEntityMappings#createId(EntityMetaData, String)}
   */
  @Test
  public void testCreateId12() {
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
   * Method under test: {@link JPAEntityMappings#createId(EntityMetaData, String)}
   */
  @Test
  public void testCreateId13() {
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
   * Method under test: {@link JPAEntityMappings#getIdString(Object)}
   */
  @Test
  public void testGetIdString() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new JPAEntityMappings()).getIdString(JSONObject.NULL));
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new JPAEntityMappings()).getIdString(null));
    assertEquals("42", (new JPAEntityMappings()).getIdString(42L));
    assertEquals("42", (new JPAEntityMappings()).getIdString("42"));
    assertEquals("65", (new JPAEntityMappings()).getIdString((byte) 'A'));
    assertEquals("42", (new JPAEntityMappings()).getIdString(42));
    assertEquals("1", (new JPAEntityMappings()).getIdString((short) 1));
    assertEquals("10.0", (new JPAEntityMappings()).getIdString(10.0f));
    assertEquals("10.0", (new JPAEntityMappings()).getIdString(10.0d));
    assertEquals("A", (new JPAEntityMappings()).getIdString('A'));
  }

  /**
   * Method under test: {@link JPAEntityMappings#getIdString(Object)}
   */
  @Test
  public void testGetIdString2() {
    // Arrange
    JPAEntityMappings jpaEntityMappings = new JPAEntityMappings();
    Date date = mock(Date.class);
    when(date.getTime()).thenReturn(10L);

    // Act
    String actualIdString = jpaEntityMappings.getIdString(date);

    // Assert
    verify(date).getTime();
    assertEquals("10", actualIdString);
  }
}
