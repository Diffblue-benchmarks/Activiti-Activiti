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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;

public class UUIDTypeDiffblueTest {
  /**
   * Method under test: {@link UUIDType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue() {
    // Arrange
    UUIDType uuidType = new UUIDType();

    // Act and Assert
    assertNull(uuidType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Method under test: {@link UUIDType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue() {
    // Arrange
    UUIDType uuidType = new UUIDType();
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    uuidType.setValue(JSONObject.NULL, valueFields);

    // Assert
    assertEquals("null", valueFields.getTextValue());
  }

  /**
   * Method under test: {@link UUIDType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue2() {
    // Arrange
    UUIDType uuidType = new UUIDType();
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    uuidType.setValue(null, valueFields);

    // Assert
    assertNull(valueFields.getTextValue());
  }

  /**
   * Method under test: {@link UUIDType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue3() {
    // Arrange
    UUIDType uuidType = new UUIDType();
    ValueFields valueFields = mock(ValueFields.class);
    doNothing().when(valueFields).setTextValue(Mockito.<String>any());

    // Act
    uuidType.setValue(JSONObject.NULL, valueFields);

    // Assert
    verify(valueFields).setTextValue(eq("null"));
  }

  /**
   * Method under test: {@link UUIDType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore() {
    // Arrange, Act and Assert
    assertFalse((new UUIDType()).isAbleToStore(JSONObject.NULL));
    assertTrue((new UUIDType()).isAbleToStore(null));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link UUIDType}
   *   <li>{@link UUIDType#getTypeName()}
   *   <li>{@link UUIDType#isCachable()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    UUIDType actualUuidType = new UUIDType();
    String actualTypeName = actualUuidType.getTypeName();

    // Assert
    assertEquals("uuid", actualTypeName);
    assertTrue(actualUuidType.isCachable());
  }
}
