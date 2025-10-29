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
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class BooleanTypeDiffblueTest {
  /**
   * Method under test: {@link BooleanType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue() {
    // Arrange
    BooleanType booleanType = new BooleanType();

    // Act and Assert
    assertNull(booleanType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Method under test: {@link BooleanType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue2() {
    // Arrange
    BooleanType booleanType = new BooleanType();
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getLongValue()).thenReturn(42L);

    // Act
    booleanType.getValue(valueFields);

    // Assert
    verify(valueFields, atLeast(1)).getLongValue();
  }

  /**
   * Method under test: {@link BooleanType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue() {
    // Arrange
    BooleanType booleanType = new BooleanType();
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    booleanType.setValue(null, valueFields);

    // Assert
    assertNull(valueFields.getLongValue());
  }

  /**
   * Method under test: {@link BooleanType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue2() {
    // Arrange
    BooleanType booleanType = new BooleanType();
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    booleanType.setValue(true, valueFields);

    // Assert
    assertEquals(1L, valueFields.getLongValue().longValue());
  }

  /**
   * Method under test: {@link BooleanType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue3() {
    // Arrange
    BooleanType booleanType = new BooleanType();
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    booleanType.setValue(false, valueFields);

    // Assert
    assertEquals(0L, valueFields.getLongValue().longValue());
  }

  /**
   * Method under test: {@link BooleanType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore() {
    // Arrange, Act and Assert
    assertFalse((new BooleanType()).isAbleToStore(JSONObject.NULL));
    assertTrue((new BooleanType()).isAbleToStore(null));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link BooleanType}
   *   <li>{@link BooleanType#getTypeName()}
   *   <li>{@link BooleanType#isCachable()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    BooleanType actualBooleanType = new BooleanType();
    String actualTypeName = actualBooleanType.getTypeName();

    // Assert
    assertEquals("boolean", actualTypeName);
    assertTrue(actualBooleanType.isCachable());
  }
}
