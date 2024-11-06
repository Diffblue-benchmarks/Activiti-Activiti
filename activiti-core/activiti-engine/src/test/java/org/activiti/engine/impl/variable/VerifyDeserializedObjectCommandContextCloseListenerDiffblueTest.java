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

import static org.junit.Assert.assertSame;
import java.io.UnsupportedEncodingException;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class VerifyDeserializedObjectCommandContextCloseListenerDiffblueTest {
  /**
   * Test
   * {@link VerifyDeserializedObjectCommandContextCloseListener#VerifyDeserializedObjectCommandContextCloseListener(DeserializedObject)}.
   * <p>
   * Method under test:
   * {@link VerifyDeserializedObjectCommandContextCloseListener#VerifyDeserializedObjectCommandContextCloseListener(DeserializedObject)}
   */
  @Test
  public void testNewVerifyDeserializedObjectCommandContextCloseListener() throws UnsupportedEncodingException {
    // Arrange
    SerializableType type = new SerializableType(true);
    byte[] serializedBytes = "AXAXAXAX".getBytes("UTF-8");
    DeserializedObject deserializedObject = new DeserializedObject(type, JSONObject.NULL, serializedBytes,
        new VariableInstanceEntityImpl());

    // Act and Assert
    DeserializedObject deserializedObject2 = (new VerifyDeserializedObjectCommandContextCloseListener(
        deserializedObject)).deserializedObject;
    assertSame(deserializedObject.deserializedObject, deserializedObject2.deserializedObject);
    assertSame(deserializedObject.originalBytes, deserializedObject2.originalBytes);
    assertSame(deserializedObject.type, deserializedObject2.type);
    assertSame(deserializedObject.variableInstanceEntity, deserializedObject2.variableInstanceEntity);
  }
}
