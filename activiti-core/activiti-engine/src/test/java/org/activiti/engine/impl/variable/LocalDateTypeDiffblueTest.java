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
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class LocalDateTypeDiffblueTest {
  /**
   * Method under test: {@link LocalDateType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore() {
    // Arrange, Act and Assert
    assertFalse((new LocalDateType()).isAbleToStore(JSONObject.NULL));
    assertTrue((new LocalDateType()).isAbleToStore(null));
  }

  /**
   * Method under test: {@link LocalDateType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue() {
    // Arrange
    LocalDateType localDateType = new LocalDateType();

    // Act and Assert
    assertNull(localDateType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Method under test: {@link LocalDateType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue2() {
    // Arrange
    LocalDateType localDateType = new LocalDateType();

    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();
    valueFields.setActivityInstanceId("42");
    valueFields.setCachedValue(JSONObject.NULL);
    valueFields.setDeleted(true);
    valueFields.setDetailType("Detail Type");
    valueFields.setDoubleValue(10.0d);
    valueFields.setExecutionId("42");
    valueFields.setId("42");
    valueFields.setInserted(true);
    valueFields.setName("Name");
    valueFields.setProcessInstanceId("42");
    valueFields.setRevision(1);
    valueFields.setTaskId("42");
    valueFields.setTextValue("42");
    valueFields.setTextValue2("42");
    valueFields.setTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setUpdated(true);
    valueFields.setVariableType(new BigDecimalType());
    valueFields.setLongValue(42L);

    // Act and Assert
    assertEquals("1970-01-01", localDateType.getValue(valueFields).toString());
  }

  /**
   * Method under test: {@link LocalDateType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue3() {
    // Arrange
    LocalDateType localDateType = new LocalDateType();
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getLongValue()).thenReturn(42L);

    // Act
    Object actualValue = localDateType.getValue(valueFields);

    // Assert
    verify(valueFields).getLongValue();
    assertEquals("1970-01-01", actualValue.toString());
  }

  /**
   * Method under test: {@link LocalDateType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue() {
    // Arrange
    LocalDateType localDateType = new LocalDateType();
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    localDateType.setValue(null, valueFields);

    // Assert
    assertNull(valueFields.getLongValue());
  }

  /**
   * Method under test: {@link LocalDateType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue2() {
    // Arrange
    LocalDateType localDateType = new LocalDateType();
    LocalDate ofResult = LocalDate.of(1970, 1, 1);
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    localDateType.setValue(ofResult, valueFields);

    // Assert
    assertEquals(0L, valueFields.getLongValue().longValue());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link LocalDateType}
   *   <li>{@link LocalDateType#getTypeName()}
   *   <li>{@link LocalDateType#isCachable()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    LocalDateType actualLocalDateType = new LocalDateType();
    String actualTypeName = actualLocalDateType.getTypeName();

    // Assert
    assertEquals("localDate", actualTypeName);
    assertTrue(actualLocalDateType.isCachable());
  }
}
