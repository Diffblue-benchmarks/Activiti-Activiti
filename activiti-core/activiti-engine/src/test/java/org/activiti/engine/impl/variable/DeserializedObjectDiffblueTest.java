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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntity;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DeserializedObjectDiffblueTest {
  /**
   * Test {@link DeserializedObject#DeserializedObject(SerializableType, Object, byte[],
   * VariableInstanceEntity)}.
   *
   * <p>Method under test: {@link DeserializedObject#DeserializedObject(SerializableType, Object,
   * byte[], VariableInstanceEntity)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DeserializedObject.<init>(SerializableType, Object, byte[], VariableInstanceEntity)"
  })
  public void testNewDeserializedObject() throws UnsupportedEncodingException {
    // Arrange
    SerializableType type = new SerializableType(true);
    byte[] serializedBytes = "AXAXAXAX".getBytes("UTF-8");

    // Act
    DeserializedObject actualDeserializedObject =
        new DeserializedObject(
            type, JSONObject.NULL, serializedBytes, new VariableInstanceEntityImpl());

    // Assert
    SerializableType serializableType = actualDeserializedObject.type;
    assertTrue(serializableType.isCachable());
    assertEquals(SerializableType.TYPE_NAME, serializableType.getTypeName());
  }

  /**
   * Test {@link DeserializedObject#verifyIfBytesOfSerializedObjectChanged()}.
   *
   * <p>Method under test: {@link DeserializedObject#verifyIfBytesOfSerializedObjectChanged()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeserializedObject.verifyIfBytesOfSerializedObjectChanged()"})
  public void testVerifyIfBytesOfSerializedObjectChanged() throws UnsupportedEncodingException {
    // Arrange
    SerializableType type = new SerializableType(true);
    byte[] serializedBytes = "AXAXAXAX".getBytes("UTF-8");

    DeserializedObject deserializedObject =
        new DeserializedObject(
            type, JSONObject.NULL, serializedBytes, new VariableInstanceEntityImpl());

    // Act
    deserializedObject.verifyIfBytesOfSerializedObjectChanged();

    // Assert that nothing has changed
    VariableInstanceEntity variableInstanceEntity = deserializedObject.variableInstanceEntity;
    Object persistentState = variableInstanceEntity.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(variableInstanceEntity instanceof VariableInstanceEntityImpl);
    assertTrue(((Map<Object, Object>) persistentState).isEmpty());
  }

  /**
   * Test {@link DeserializedObject#verifyIfBytesOfSerializedObjectChanged()}.
   *
   * <p>Method under test: {@link DeserializedObject#verifyIfBytesOfSerializedObjectChanged()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeserializedObject.verifyIfBytesOfSerializedObjectChanged()"})
  public void testVerifyIfBytesOfSerializedObjectChanged2() throws UnsupportedEncodingException {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntity = new VariableInstanceEntityImpl();
    variableInstanceEntity.setDeleted(false);
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter = new JsonTypeConverter(objectMapper2, "null");

    LongJsonType type = new LongJsonType(3, objectMapper, true, jsonTypeConverter);

    DeserializedObject deserializedObject =
        new DeserializedObject(type, null, "AXAXAXAX".getBytes("UTF-8"), variableInstanceEntity);

    // Act
    deserializedObject.verifyIfBytesOfSerializedObjectChanged();

    // Assert that nothing has changed
    VariableInstanceEntity variableInstanceEntity2 = deserializedObject.variableInstanceEntity;
    Object persistentState = variableInstanceEntity2.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(variableInstanceEntity2 instanceof VariableInstanceEntityImpl);
    assertTrue(((Map<Object, Object>) persistentState).isEmpty());
  }

  /**
   * Test {@link DeserializedObject#verifyIfBytesOfSerializedObjectChanged()}.
   *
   * <p>Method under test: {@link DeserializedObject#verifyIfBytesOfSerializedObjectChanged()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeserializedObject.verifyIfBytesOfSerializedObjectChanged()"})
  public void testVerifyIfBytesOfSerializedObjectChanged3() throws UnsupportedEncodingException {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntity = new VariableInstanceEntityImpl();
    variableInstanceEntity.setDeleted(true);
    DeserializedObject deserializedObject =
        new DeserializedObject(
            new SerializableType(true), null, "AXAXAXAX".getBytes("UTF-8"), variableInstanceEntity);

    // Act
    deserializedObject.verifyIfBytesOfSerializedObjectChanged();

    // Assert that nothing has changed
    VariableInstanceEntity variableInstanceEntity2 = deserializedObject.variableInstanceEntity;
    Object persistentState = variableInstanceEntity2.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(variableInstanceEntity2 instanceof VariableInstanceEntityImpl);
    assertTrue(((Map<Object, Object>) persistentState).isEmpty());
  }

  /**
   * Test {@link DeserializedObject#verifyIfBytesOfSerializedObjectChanged()}.
   *
   * <p>Method under test: {@link DeserializedObject#verifyIfBytesOfSerializedObjectChanged()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DeserializedObject.verifyIfBytesOfSerializedObjectChanged()"})
  public void testVerifyIfBytesOfSerializedObjectChanged4() {
    // Arrange
    VariableInstanceEntityImpl variableInstanceEntity = new VariableInstanceEntityImpl();
    variableInstanceEntity.setDeleted(false);
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    JsonTypeConverter jsonTypeConverter = new JsonTypeConverter(objectMapper2, "null");

    LongJsonType type = new LongJsonType(3, objectMapper, true, jsonTypeConverter);

    DeserializedObject deserializedObject =
        new DeserializedObject(type, null, new byte[] {}, variableInstanceEntity);

    // Act
    deserializedObject.verifyIfBytesOfSerializedObjectChanged();

    // Assert
    VariableInstanceEntity variableInstanceEntity2 = deserializedObject.variableInstanceEntity;
    Object persistentState = variableInstanceEntity2.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(variableInstanceEntity2 instanceof VariableInstanceEntityImpl);
    assertEquals(1, ((Map<String, String>) persistentState).size());
    assertEquals(
        "com.fasterxml.jackson.databind.node.MissingNode",
        ((Map<String, String>) persistentState).get("textValue2"));
    assertEquals(
        "com.fasterxml.jackson.databind.node.MissingNode", variableInstanceEntity2.getTextValue2());
    assertEquals("var-null", variableInstanceEntity2.getByteArrayRef().getName());
  }
}
