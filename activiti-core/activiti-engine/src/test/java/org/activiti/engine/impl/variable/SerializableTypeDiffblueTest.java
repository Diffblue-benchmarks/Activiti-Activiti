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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntityImpl;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.joda.time.chrono.ISOChronology;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class SerializableTypeDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link SerializableType#SerializableType()}
   *   <li>{@link SerializableType#getTypeName()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SerializableType.<init>()",
    "void SerializableType.<init>(boolean)",
    "java.lang.String SerializableType.getTypeName()"
  })
  public void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals(SerializableType.TYPE_NAME, new SerializableType().getTypeName());
  }

  /**
   * Test getters and setters.
   *
   * <ul>
   *   <li>When {@code true}.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link SerializableType#SerializableType(boolean)}
   *   <li>{@link SerializableType#getTypeName()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SerializableType.<init>()",
    "void SerializableType.<init>(boolean)",
    "java.lang.String SerializableType.getTypeName()"
  })
  public void testGettersAndSetters_whenTrue() {
    // Arrange, Act and Assert
    assertEquals(SerializableType.TYPE_NAME, new SerializableType(true).getTypeName());
  }

  /**
   * Test {@link SerializableType#getValue(ValueFields)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link SerializableType#getValue(ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object SerializableType.getValue(ValueFields)"})
  public void testGetValue_thenThrowActivitiException() {
    // Arrange
    SerializableType serializableType = new SerializableType(true);

    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getCachedValue()).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> serializableType.getValue(valueFields));
    verify(valueFields).getCachedValue();
  }

  /**
   * Test {@link SerializableType#getValue(ValueFields)}.
   *
   * <ul>
   *   <li>When {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link SerializableType#getValue(ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object SerializableType.getValue(ValueFields)"})
  public void testGetValue_whenHistoricDetailVariableInstanceUpdateEntityImpl_thenReturnNull() {
    // Arrange
    SerializableType serializableType = new SerializableType(true);

    // Act and Assert
    assertNull(serializableType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link SerializableType#setValue(Object, ValueFields)}.
   *
   * <p>Method under test: {@link SerializableType#setValue(Object, ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SerializableType.setValue(Object, ValueFields)"})
  public void testSetValue() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter =
        new JsonTypeConverter(objectMapper2, "Java Class Field For Jackson");

    LongJsonType longJsonType = new LongJsonType(3, objectMapper, true, jsonTypeConverter);
    VariableInstanceEntityImpl valueFields = new VariableInstanceEntityImpl();

    // Act
    longJsonType.setValue(null, valueFields);

    // Assert
    assertEquals("var-null", valueFields.getByteArrayRef().getName());
  }

  /**
   * Test {@link SerializableType#setValue(Object, ValueFields)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link SerializableType#setValue(Object, ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SerializableType.setValue(Object, ValueFields)"})
  public void testSetValue_thenThrowActivitiException() {
    // Arrange
    SerializableType serializableType = new SerializableType(true);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            serializableType.setValue(
                JSONObject.NULL, new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link SerializableType#setValue(Object, ValueFields)}.
   *
   * <ul>
   *   <li>Then {@link VariableInstanceEntityImpl} (default constructor) ByteArrayRef Name is {@code
   *       var-null}.
   * </ul>
   *
   * <p>Method under test: {@link SerializableType#setValue(Object, ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SerializableType.setValue(Object, ValueFields)"})
  public void testSetValue_thenVariableInstanceEntityImplByteArrayRefNameIsVarNull() {
    // Arrange
    SerializableType serializableType = new SerializableType(false);
    VariableInstanceEntityImpl valueFields = new VariableInstanceEntityImpl();

    // Act
    serializableType.setValue(null, valueFields);

    // Assert
    assertEquals("var-null", valueFields.getByteArrayRef().getName());
  }

  /**
   * Test {@link SerializableType#serialize(Object, ValueFields)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link SerializableType#serialize(Object, ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] SerializableType.serialize(Object, ValueFields)"})
  public void testSerialize_whenNull_thenReturnNull() {
    // Arrange
    SerializableType serializableType = new SerializableType(true);

    // Act and Assert
    assertNull(
        serializableType.serialize(null, new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link SerializableType#serialize(Object, ValueFields)}.
   *
   * <ul>
   *   <li>When {@link JSONObject#NULL}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link SerializableType#serialize(Object, ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"byte[] SerializableType.serialize(Object, ValueFields)"})
  public void testSerialize_whenNull_thenThrowActivitiException() {
    // Arrange
    SerializableType serializableType = new SerializableType(true);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            serializableType.serialize(
                JSONObject.NULL, new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link SerializableType#deserialize(byte[], ValueFields)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link SerializableType#deserialize(byte[], ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object SerializableType.deserialize(byte[], ValueFields)"})
  public void testDeserialize_thenThrowActivitiException() throws UnsupportedEncodingException {
    // Arrange
    SerializableType serializableType = new SerializableType(true);
    byte[] bytes = "AXAXAXAX".getBytes("UTF-8");

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            serializableType.deserialize(
                bytes, new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link SerializableType#isAbleToStore(Object)}.
   *
   * <ul>
   *   <li>When InstanceUTC.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link SerializableType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean SerializableType.isAbleToStore(Object)"})
  public void testIsAbleToStore_whenInstanceUTC_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(new SerializableType(true).isAbleToStore(ISOChronology.getInstanceUTC()));
  }

  /**
   * Test {@link SerializableType#isAbleToStore(Object)}.
   *
   * <ul>
   *   <li>When {@link JSONObject#NULL}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link SerializableType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean SerializableType.isAbleToStore(Object)"})
  public void testIsAbleToStore_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new SerializableType(true).isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test {@link SerializableType#createObjectOutputStream(OutputStream)}.
   *
   * <p>Method under test: {@link SerializableType#createObjectOutputStream(OutputStream)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.io.ObjectOutputStream SerializableType.createObjectOutputStream(OutputStream)"
  })
  public void testCreateObjectOutputStream() throws IOException {
    // Arrange
    SerializableType serializableType = new SerializableType(true);
    ByteArrayOutputStream os = new ByteArrayOutputStream();

    // Act
    serializableType.createObjectOutputStream(os);

    // Assert
    assertArrayEquals(new byte[] {-84, -19, 0, 5}, os.toByteArray());
  }
}
