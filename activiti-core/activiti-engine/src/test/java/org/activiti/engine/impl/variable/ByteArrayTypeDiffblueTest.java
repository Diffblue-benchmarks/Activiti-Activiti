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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class ByteArrayTypeDiffblueTest {
  /**
   * Method under test: {@link ByteArrayType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue() {
    // Arrange
    ByteArrayType byteArrayType = new ByteArrayType();

    // Act and Assert
    assertNull(byteArrayType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Method under test: {@link ByteArrayType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore() {
    // Arrange, Act and Assert
    assertFalse((new ByteArrayType()).isAbleToStore(JSONObject.NULL));
    assertTrue((new ByteArrayType()).isAbleToStore(null));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ByteArrayType}
   *   <li>{@link ByteArrayType#getTypeName()}
   *   <li>{@link ByteArrayType#isCachable()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    ByteArrayType actualByteArrayType = new ByteArrayType();
    String actualTypeName = actualByteArrayType.getTypeName();

    // Assert
    assertEquals("bytes", actualTypeName);
    assertTrue(actualByteArrayType.isCachable());
  }
}
