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
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.persistence.entity.ByteArrayRef;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.joda.time.chrono.ISOChronology;
import org.junit.Test;
import org.mockito.Mockito;

public class SerializableTypeDiffblueTest {
  /**
   * Method under test: {@link SerializableType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue() {
    // Arrange
    SerializableType serializableType = new SerializableType(true);

    // Act and Assert
    assertNull(serializableType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Method under test: {@link SerializableType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue2() {
    // Arrange
    SerializableType serializableType = new SerializableType(true);
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getCachedValue()).thenReturn(JSONObject.NULL);

    // Act
    serializableType.getValue(valueFields);

    // Assert
    verify(valueFields).getCachedValue();
  }

  /**
   * Method under test: {@link SerializableType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue3() throws UnsupportedEncodingException {
    // Arrange
    SerializableType serializableType = new SerializableType(true);
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getName()).thenReturn("Name");
    when(valueFields.getBytes()).thenReturn("AXAXAXAX".getBytes("UTF-8"));
    when(valueFields.getCachedValue()).thenReturn(null);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> serializableType.getValue(valueFields));
    verify(valueFields).getBytes();
    verify(valueFields).getCachedValue();
    verify(valueFields).getName();
  }

  /**
   * Method under test: {@link SerializableType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue4() throws UnsupportedEncodingException {
    // Arrange
    SerializableType serializableType = new SerializableType(true);
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getName()).thenThrow(new ActivitiException("An error occurred"));
    when(valueFields.getBytes()).thenReturn("AXAXAXAX".getBytes("UTF-8"));
    when(valueFields.getCachedValue()).thenReturn(null);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> serializableType.getValue(valueFields));
    verify(valueFields).getBytes();
    verify(valueFields).getCachedValue();
    verify(valueFields).getName();
  }

  /**
   * Method under test: {@link SerializableType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue5() throws UnsupportedEncodingException {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));
    ValueFields valueFields = mock(ValueFields.class);
    doNothing().when(valueFields).setCachedValue(Mockito.<Object>any());
    when(valueFields.getName()).thenReturn("Name");
    when(valueFields.getBytes()).thenReturn("AXAXAXAX".getBytes("UTF-8"));
    when(valueFields.getCachedValue()).thenReturn(null);

    // Act
    Object actualValue = longJsonType.getValue(valueFields);

    // Assert
    verify(valueFields).getBytes();
    verify(valueFields).getCachedValue();
    verify(valueFields).getName();
    verify(valueFields).setCachedValue(isNull());
    assertNull(actualValue);
  }

  /**
   * Method under test: {@link SerializableType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue6() throws UnsupportedEncodingException {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));
    ValueFields valueFields = mock(ValueFields.class);
    doThrow(new ActivitiException("An error occurred")).when(valueFields).setCachedValue(Mockito.<Object>any());
    when(valueFields.getName()).thenReturn("Name");
    when(valueFields.getBytes()).thenReturn("AXAXAXAX".getBytes("UTF-8"));
    when(valueFields.getCachedValue()).thenReturn(null);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> longJsonType.getValue(valueFields));
    verify(valueFields).getBytes();
    verify(valueFields).getCachedValue();
    verify(valueFields).getName();
    verify(valueFields).setCachedValue(isNull());
  }

  /**
   * Method under test: {@link SerializableType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue() {
    // Arrange
    SerializableType serializableType = new SerializableType(true);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> serializableType.setValue(JSONObject.NULL, new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Method under test: {@link SerializableType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue2() {
    // Arrange
    SerializableType serializableType = new SerializableType(true);
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    serializableType.setValue(null, valueFields);

    // Assert
    ByteArrayRef byteArrayRef = valueFields.getByteArrayRef();
    assertEquals("hist.detail.var-null", byteArrayRef.getName());
    assertNull(byteArrayRef.getBytes());
    assertNull(byteArrayRef.getId());
    assertNull(byteArrayRef.getEntity());
    assertFalse(byteArrayRef.isDeleted());
  }

  /**
   * Method under test: {@link SerializableType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue3() {
    // Arrange
    SerializableType serializableType = new SerializableType(true);
    ValueFields valueFields = mock(ValueFields.class);
    doThrow(new ActivitiException("An error occurred")).when(valueFields).setCachedValue(Mockito.<Object>any());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> serializableType.setValue("42", valueFields));
    verify(valueFields).setCachedValue(isA(Object.class));
  }

  /**
   * Method under test: {@link SerializableType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue4() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "null"));
    ValueFields valueFields = mock(ValueFields.class);
    doNothing().when(valueFields).setTextValue2(Mockito.<String>any());
    doThrow(new ActivitiException("An error occurred")).when(valueFields).setCachedValue(Mockito.<Object>any());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> longJsonType.setValue("42", valueFields));
    verify(valueFields).setCachedValue(isA(Object.class));
    verify(valueFields).setTextValue2(eq("java.lang.String"));
  }

  /**
   * Method under test: {@link SerializableType#serialize(Object, ValueFields)}
   */
  @Test
  public void testSerialize() {
    // Arrange
    SerializableType serializableType = new SerializableType(true);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> serializableType.serialize(JSONObject.NULL, new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Method under test: {@link SerializableType#serialize(Object, ValueFields)}
   */
  @Test
  public void testSerialize2() {
    // Arrange
    SerializableType serializableType = new SerializableType(true);

    // Act and Assert
    assertNull(serializableType.serialize(null, new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Method under test: {@link SerializableType#serialize(Object, ValueFields)}
   */
  @Test
  public void testSerialize3() {
    // Arrange
    SerializableType serializableType = new SerializableType(true);

    // Act
    byte[] actualSerializeResult = serializableType.serialize(42, new HistoricDetailVariableInstanceUpdateEntityImpl());

    // Assert
    assertEquals((byte) -107, actualSerializeResult[66]);
    assertEquals((byte) -108, actualSerializeResult[69]);
    assertEquals((byte) -117, actualSerializeResult[71]);
    assertEquals((byte) -122, actualSerializeResult[Double.SIZE]);
    assertEquals((byte) -19, actualSerializeResult[1]);
    assertEquals((byte) -32, actualSerializeResult[70]);
    assertEquals((byte) -84, actualSerializeResult[0]);
    assertEquals((byte) -84, actualSerializeResult[65]);
    assertEquals((byte) 0, actualSerializeResult[2]);
    assertEquals((byte) 0, actualSerializeResult[6]);
    assertEquals((byte) 0, actualSerializeResult[73]);
    assertEquals((byte) 0, actualSerializeResult[74]);
    assertEquals((byte) 0, actualSerializeResult[77]);
    assertEquals((byte) 0, actualSerializeResult[78]);
    assertEquals((byte) 0, actualSerializeResult[79]);
    assertEquals((byte) 11, actualSerializeResult[68]);
    assertEquals((byte) 17, actualSerializeResult[7]);
    assertEquals((byte) 29, actualSerializeResult[67]);
    assertEquals((byte) 2, actualSerializeResult[72]);
    assertEquals((byte) 5, actualSerializeResult[3]);
    assertEquals(81, actualSerializeResult.length);
    assertEquals('*', actualSerializeResult[80]);
    assertEquals('.', actualSerializeResult[12]);
    assertEquals('.', actualSerializeResult[17]);
    assertEquals('.', actualSerializeResult[57]);
    assertEquals('I', actualSerializeResult[18]);
    assertEquals('N', actualSerializeResult[58]);
    assertEquals('a', actualSerializeResult[11]);
    assertEquals('a', actualSerializeResult[14]);
    assertEquals('a', actualSerializeResult[9]);
    assertEquals('b', actualSerializeResult[61]);
    assertEquals('e', actualSerializeResult[21]);
    assertEquals('e', actualSerializeResult[23]);
    assertEquals('e', actualSerializeResult[62]);
    assertEquals('g', actualSerializeResult[22]);
    assertEquals('g', actualSerializeResult[56]);
    assertEquals('g', actualSerializeResult[Short.SIZE]);
    assertEquals('j', actualSerializeResult[8]);
    assertEquals('l', actualSerializeResult[13]);
    assertEquals('m', actualSerializeResult[60]);
    assertEquals('n', actualSerializeResult[15]);
    assertEquals('n', actualSerializeResult[19]);
    assertEquals('p', actualSerializeResult[76]);
    assertEquals('r', actualSerializeResult[5]);
    assertEquals('r', actualSerializeResult[63]);
    assertEquals('r', actualSerializeResult[Float.PRECISION]);
    assertEquals('s', actualSerializeResult[4]);
    assertEquals('t', actualSerializeResult[20]);
    assertEquals('u', actualSerializeResult[59]);
    assertEquals('v', actualSerializeResult[10]);
    assertEquals('x', actualSerializeResult[75]);
  }

  /**
   * Method under test: {@link SerializableType#deserialize(byte[], ValueFields)}
   */
  @Test
  public void testDeserialize() throws UnsupportedEncodingException {
    // Arrange
    SerializableType serializableType = new SerializableType(true);
    byte[] bytes = "AXAXAXAX".getBytes("UTF-8");

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> serializableType.deserialize(bytes, new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Method under test: {@link SerializableType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore() {
    // Arrange, Act and Assert
    assertFalse((new SerializableType(true)).isAbleToStore(JSONObject.NULL));
  }

  /**
   * Method under test: {@link SerializableType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore2() {
    // Arrange
    SerializableType serializableType = new SerializableType(true);

    // Act and Assert
    assertTrue(serializableType.isAbleToStore(ISOChronology.getInstanceUTC()));
  }

  /**
   * Method under test:
   * {@link SerializableType#createObjectOutputStream(OutputStream)}
   */
  @Test
  public void testCreateObjectOutputStream() throws IOException {
    // Arrange
    SerializableType serializableType = new SerializableType(true);
    ByteArrayOutputStream os = new ByteArrayOutputStream(1);

    // Act
    serializableType.createObjectOutputStream(os);

    // Assert
    assertArrayEquals(new byte[]{-84, -19, 0, 5}, os.toByteArray());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SerializableType#SerializableType()}
   *   <li>{@link SerializableType#getTypeName()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals(SerializableType.TYPE_NAME, (new SerializableType()).getTypeName());
    assertEquals(SerializableType.TYPE_NAME, (new SerializableType(true)).getTypeName());
  }
}
