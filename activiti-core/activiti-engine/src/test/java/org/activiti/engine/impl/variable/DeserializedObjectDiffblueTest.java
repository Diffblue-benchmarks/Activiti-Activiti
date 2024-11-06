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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.UnsupportedEncodingException;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntity;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;

public class DeserializedObjectDiffblueTest {
  /**
   * Test
   * {@link DeserializedObject#DeserializedObject(SerializableType, Object, byte[], VariableInstanceEntity)}.
   * <p>
   * Method under test:
   * {@link DeserializedObject#DeserializedObject(SerializableType, Object, byte[], VariableInstanceEntity)}
   */
  @Test
  public void testNewDeserializedObject() throws UnsupportedEncodingException {
    // Arrange
    SerializableType type = new SerializableType(true);
    byte[] serializedBytes = "AXAXAXAX".getBytes("UTF-8");

    // Act and Assert
    SerializableType serializableType = (new DeserializedObject(type, JSONObject.NULL, serializedBytes,
        new VariableInstanceEntityImpl())).type;
    assertTrue(serializableType.isCachable());
    assertEquals(SerializableType.TYPE_NAME, serializableType.getTypeName());
  }

  /**
   * Test {@link DeserializedObject#verifyIfBytesOfSerializedObjectChanged()}.
   * <p>
   * Method under test:
   * {@link DeserializedObject#verifyIfBytesOfSerializedObjectChanged()}
   */
  @Test
  public void testVerifyIfBytesOfSerializedObjectChanged() throws UnsupportedEncodingException {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntity = mock(VariableInstanceEntityImpl.class);
    when(variableInstanceEntity.isDeleted()).thenReturn(true);
    when(variableInstanceEntity.getCachedValue()).thenReturn(JSONObject.NULL);
    SerializableType type = new SerializableType(true);

    // Act
    (new DeserializedObject(type, JSONObject.NULL, "AXAXAXAX".getBytes("UTF-8"), variableInstanceEntity))
        .verifyIfBytesOfSerializedObjectChanged();

    // Assert that nothing has changed
    verify(variableInstanceEntity).isDeleted();
    verify(variableInstanceEntity).getCachedValue();
  }

  /**
   * Test {@link DeserializedObject#verifyIfBytesOfSerializedObjectChanged()}.
   * <p>
   * Method under test:
   * {@link DeserializedObject#verifyIfBytesOfSerializedObjectChanged()}
   */
  @Test
  public void testVerifyIfBytesOfSerializedObjectChanged2() throws UnsupportedEncodingException {
    // Arrange
    LongJsonType type = mock(LongJsonType.class);
    when(type.serialize(Mockito.<Object>any(), Mockito.<ValueFields>any())).thenReturn("AXAXAXAX".getBytes("UTF-8"));
    VariableInstanceEntityImpl variableInstanceEntity = mock(VariableInstanceEntityImpl.class);
    when(variableInstanceEntity.isDeleted()).thenReturn(false);
    when(variableInstanceEntity.getCachedValue()).thenReturn(JSONObject.NULL);

    // Act
    (new DeserializedObject(type, JSONObject.NULL, "AXAXAXAX".getBytes("UTF-8"), variableInstanceEntity))
        .verifyIfBytesOfSerializedObjectChanged();

    // Assert that nothing has changed
    verify(variableInstanceEntity).isDeleted();
    verify(variableInstanceEntity).getCachedValue();
    verify(type).serialize(isA(Object.class), isA(ValueFields.class));
  }

  /**
   * Test {@link DeserializedObject#verifyIfBytesOfSerializedObjectChanged()}.
   * <ul>
   *   <li>Given {@code A}.</li>
   *   <li>Then calls {@link LongJsonType#deserialize(byte[], ValueFields)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DeserializedObject#verifyIfBytesOfSerializedObjectChanged()}
   */
  @Test
  public void testVerifyIfBytesOfSerializedObjectChanged_givenA_thenCallsDeserialize()
      throws UnsupportedEncodingException {
    // Arrange
    LongJsonType type = mock(LongJsonType.class);
    when(type.serialize(Mockito.<Object>any(), Mockito.<ValueFields>any()))
        .thenReturn(new byte[]{1, 'X', 'A', 'X', 'A', 'X', 'A', 'X'});
    when(type.deserialize(Mockito.<byte[]>any(), Mockito.<ValueFields>any())).thenReturn(JSONObject.NULL);
    VariableInstanceEntityImpl variableInstanceEntity = mock(VariableInstanceEntityImpl.class);
    when(variableInstanceEntity.isDeleted()).thenReturn(false);
    when(variableInstanceEntity.getCachedValue()).thenReturn(JSONObject.NULL);

    // Act
    (new DeserializedObject(type, JSONObject.NULL, "AXAXAXAX".getBytes("UTF-8"), variableInstanceEntity))
        .verifyIfBytesOfSerializedObjectChanged();

    // Assert that nothing has changed
    verify(variableInstanceEntity).isDeleted();
    verify(variableInstanceEntity).getCachedValue();
    verify(type).deserialize(isA(byte[].class), isA(ValueFields.class));
    verify(type, atLeast(1)).serialize(isA(Object.class), isA(ValueFields.class));
  }
}
