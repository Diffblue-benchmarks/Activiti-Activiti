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
import static org.mockito.Mockito.when;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;

public class BigDecimalTypeDiffblueTest {
  /**
   * Method under test: {@link BigDecimalType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue() {
    // Arrange
    BigDecimalType bigDecimalType = new BigDecimalType();

    // Act and Assert
    assertNull(bigDecimalType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Method under test: {@link BigDecimalType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue2() {
    // Arrange
    BigDecimalType bigDecimalType = new BigDecimalType();
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getTextValue()).thenReturn("42");

    // Act
    bigDecimalType.getValue(valueFields);

    // Assert
    verify(valueFields).getTextValue();
  }

  /**
   * Method under test: {@link BigDecimalType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue() {
    // Arrange
    BigDecimalType bigDecimalType = new BigDecimalType();
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    bigDecimalType.setValue(JSONObject.NULL, valueFields);

    // Assert
    assertEquals("null", valueFields.getTextValue());
  }

  /**
   * Method under test: {@link BigDecimalType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue2() {
    // Arrange
    BigDecimalType bigDecimalType = new BigDecimalType();
    ValueFields valueFields = mock(ValueFields.class);
    doNothing().when(valueFields).setTextValue(Mockito.<String>any());

    // Act
    bigDecimalType.setValue(JSONObject.NULL, valueFields);

    // Assert
    verify(valueFields).setTextValue(eq("null"));
  }

  /**
   * Method under test: {@link BigDecimalType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore() {
    // Arrange, Act and Assert
    assertFalse((new BigDecimalType()).isAbleToStore(JSONObject.NULL));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link BigDecimalType}
   *   <li>{@link BigDecimalType#getTypeName()}
   *   <li>{@link BigDecimalType#isCachable()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    BigDecimalType actualBigDecimalType = new BigDecimalType();
    String actualTypeName = actualBigDecimalType.getTypeName();

    // Assert
    assertEquals("bigdecimal", actualTypeName);
    assertTrue(actualBigDecimalType.isCachable());
  }
}
