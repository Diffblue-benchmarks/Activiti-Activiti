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
   * Test {@link UUIDType#getValue(ValueFields)}.
   * <ul>
   *   <li>When {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default
   * constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UUIDType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_whenHistoricDetailVariableInstanceUpdateEntityImpl_thenReturnNull() {
    // Arrange
    UUIDType uuidType = new UUIDType();

    // Act and Assert
    assertNull(uuidType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link UUIDType#setValue(Object, ValueFields)}.
   * <ul>
   *   <li>Then {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default
   * constructor) TextValue is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UUIDType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue_thenHistoricDetailVariableInstanceUpdateEntityImplTextValueIsNull() {
    // Arrange
    UUIDType uuidType = new UUIDType();
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    uuidType.setValue(JSONObject.NULL, valueFields);

    // Assert
    assertEquals("null", valueFields.getTextValue());
  }

  /**
   * Test {@link UUIDType#setValue(Object, ValueFields)}.
   * <ul>
   *   <li>Then {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default
   * constructor) TextValue is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UUIDType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue_thenHistoricDetailVariableInstanceUpdateEntityImplTextValueIsNull2() {
    // Arrange
    UUIDType uuidType = new UUIDType();
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    uuidType.setValue(null, valueFields);

    // Assert
    assertNull(valueFields.getTextValue());
  }

  /**
   * Test {@link UUIDType#setValue(Object, ValueFields)}.
   * <ul>
   *   <li>When {@link ValueFields} {@link ValueFields#setTextValue(String)} does
   * nothing.</li>
   *   <li>Then calls {@link ValueFields#setTextValue(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UUIDType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue_whenValueFieldsSetTextValueDoesNothing_thenCallsSetTextValue() {
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
   * Test {@link UUIDType#isAbleToStore(Object)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UUIDType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new UUIDType()).isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test {@link UUIDType#isAbleToStore(Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UUIDType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_whenNull_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((new UUIDType()).isAbleToStore(null));
  }

  /**
   * Test getters and setters.
   * <p>
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
