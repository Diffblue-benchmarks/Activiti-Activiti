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
import org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class JodaDateTypeDiffblueTest {
  /**
   * Method under test: {@link JodaDateType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore() {
    // Arrange, Act and Assert
    assertFalse((new JodaDateType()).isAbleToStore(JSONObject.NULL));
    assertTrue((new JodaDateType()).isAbleToStore(null));
  }

  /**
   * Method under test: {@link JodaDateType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue() {
    // Arrange
    JodaDateType jodaDateType = new JodaDateType();

    // Act and Assert
    assertNull(jodaDateType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Method under test: {@link JodaDateType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue2() {
    // Arrange
    JodaDateType jodaDateType = new JodaDateType();

    HistoricVariableInstanceEntityImpl valueFields = new HistoricVariableInstanceEntityImpl();
    valueFields.setCachedValue(JSONObject.NULL);
    valueFields.setCreateTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setDeleted(true);
    valueFields.setDoubleValue(10.0d);
    valueFields.setExecutionId("42");
    valueFields.setId("42");
    valueFields.setInserted(true);
    valueFields
        .setLastUpdatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setLongValue(42L);
    valueFields.setName("Name");
    valueFields.setProcessInstanceId("42");
    valueFields.setRevision(1);
    valueFields.setTaskId("42");
    valueFields.setTextValue("42");
    valueFields.setTextValue2("42");
    valueFields.setUpdated(true);
    valueFields.setVariableType(new BigDecimalType());

    // Act and Assert
    assertEquals("1970-01-01", jodaDateType.getValue(valueFields).toString());
  }

  /**
   * Method under test: {@link JodaDateType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue3() {
    // Arrange
    JodaDateType jodaDateType = new JodaDateType();
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getLongValue()).thenReturn(42L);

    // Act
    Object actualValue = jodaDateType.getValue(valueFields);

    // Assert
    verify(valueFields).getLongValue();
    assertEquals("1970-01-01", actualValue.toString());
  }

  /**
   * Method under test: {@link JodaDateType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue() {
    // Arrange
    JodaDateType jodaDateType = new JodaDateType();
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    jodaDateType.setValue(null, valueFields);

    // Assert
    assertNull(valueFields.getLongValue());
  }

  /**
   * Method under test: {@link JodaDateType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue2() {
    // Arrange
    JodaDateType jodaDateType = new JodaDateType();
    org.joda.time.LocalDate localDate = new org.joda.time.LocalDate(1970, 1, 1);

    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    jodaDateType.setValue(localDate, valueFields);

    // Assert
    assertEquals(0L, valueFields.getLongValue().longValue());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link JodaDateType}
   *   <li>{@link JodaDateType#getTypeName()}
   *   <li>{@link JodaDateType#isCachable()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    JodaDateType actualJodaDateType = new JodaDateType();
    String actualTypeName = actualJodaDateType.getTypeName();

    // Assert
    assertEquals("jodadate", actualTypeName);
    assertTrue(actualJodaDateType.isCachable());
  }
}
