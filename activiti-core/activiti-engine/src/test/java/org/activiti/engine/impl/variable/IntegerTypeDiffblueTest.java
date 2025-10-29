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

public class IntegerTypeDiffblueTest {
  /**
   * Method under test: {@link IntegerType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue() {
    // Arrange
    IntegerType integerType = new IntegerType();

    // Act and Assert
    assertNull(integerType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Method under test: {@link IntegerType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue2() {
    // Arrange
    IntegerType integerType = new IntegerType();
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getLongValue()).thenReturn(42L);

    // Act
    integerType.getValue(valueFields);

    // Assert
    verify(valueFields, atLeast(1)).getLongValue();
  }

  /**
   * Method under test: {@link IntegerType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue() {
    // Arrange
    IntegerType integerType = new IntegerType();
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    integerType.setValue(null, valueFields);

    // Assert
    assertNull(valueFields.getLongValue());
    assertNull(valueFields.getTextValue());
  }

  /**
   * Method under test: {@link IntegerType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue2() {
    // Arrange
    IntegerType integerType = new IntegerType();
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    integerType.setValue(42, valueFields);

    // Assert
    assertEquals("42", valueFields.getTextValue());
    assertEquals(42L, valueFields.getLongValue().longValue());
  }

  /**
   * Method under test: {@link IntegerType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore() {
    // Arrange, Act and Assert
    assertFalse((new IntegerType()).isAbleToStore(JSONObject.NULL));
    assertTrue((new IntegerType()).isAbleToStore(null));
    assertTrue((new IntegerType()).isAbleToStore(42));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link IntegerType}
   *   <li>{@link IntegerType#getTypeName()}
   *   <li>{@link IntegerType#isCachable()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    IntegerType actualIntegerType = new IntegerType();
    String actualTypeName = actualIntegerType.getTypeName();

    // Assert
    assertEquals("integer", actualTypeName);
    assertTrue(actualIntegerType.isCachable());
  }
}
