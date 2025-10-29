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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.sql.Date;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class DateTypeDiffblueTest {
  /**
   * Method under test: {@link DateType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore() {
    // Arrange, Act and Assert
    assertFalse((new DateType()).isAbleToStore(JSONObject.NULL));
    assertTrue((new DateType()).isAbleToStore(null));
  }

  /**
   * Method under test: {@link DateType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue() {
    // Arrange
    DateType dateType = new DateType();

    // Act and Assert
    assertNull(dateType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Method under test: {@link DateType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue2() {
    // Arrange
    DateType dateType = new DateType();
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getLongValue()).thenReturn(42L);

    // Act
    dateType.getValue(valueFields);

    // Assert
    verify(valueFields).getLongValue();
  }

  /**
   * Method under test: {@link DateType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue() {
    // Arrange
    DateType dateType = new DateType();
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    dateType.setValue(null, valueFields);

    // Assert
    assertNull(valueFields.getLongValue());
  }

  /**
   * Method under test: {@link DateType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue2() {
    // Arrange
    DateType dateType = new DateType();
    Date date = mock(Date.class);
    when(date.getTime()).thenReturn(10L);
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    dateType.setValue(date, valueFields);

    // Assert
    verify(date).getTime();
    assertEquals(10L, valueFields.getLongValue().longValue());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link DateType}
   *   <li>{@link DateType#getTypeName()}
   *   <li>{@link DateType#isCachable()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    DateType actualDateType = new DateType();
    String actualTypeName = actualDateType.getTypeName();

    // Assert
    assertEquals("date", actualTypeName);
    assertTrue(actualDateType.isCachable());
  }
}
