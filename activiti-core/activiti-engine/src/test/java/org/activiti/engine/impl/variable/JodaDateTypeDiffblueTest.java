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
   * Test {@link JodaDateType#isAbleToStore(Object)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JodaDateType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new JodaDateType()).isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test {@link JodaDateType#isAbleToStore(Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JodaDateType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_whenNull_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((new JodaDateType()).isAbleToStore(null));
  }

  /**
   * Test {@link JodaDateType#getValue(ValueFields)}.
   * <ul>
   *   <li>Given minus one.</li>
   *   <li>When {@link ValueFields} {@link ValueFields#getLongValue()} return minus
   * one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JodaDateType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_givenMinusOne_whenValueFieldsGetLongValueReturnMinusOne() {
    // Arrange
    JodaDateType jodaDateType = new JodaDateType();
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getLongValue()).thenReturn(-1L);

    // Act
    Object actualValue = jodaDateType.getValue(valueFields);

    // Assert
    verify(valueFields).getLongValue();
    assertEquals("1970-01-01", actualValue.toString());
  }

  /**
   * Test {@link JodaDateType#getValue(ValueFields)}.
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.</li>
   *   <li>When {@link HistoricVariableInstanceEntityImpl} (default constructor)
   * CachedValue is {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JodaDateType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_givenNull_whenHistoricVariableInstanceEntityImplCachedValueIsNull() {
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
   * Test {@link JodaDateType#getValue(ValueFields)}.
   * <ul>
   *   <li>When {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default
   * constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JodaDateType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_whenHistoricDetailVariableInstanceUpdateEntityImpl_thenReturnNull() {
    // Arrange
    JodaDateType jodaDateType = new JodaDateType();

    // Act and Assert
    assertNull(jodaDateType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link JodaDateType#getValue(ValueFields)}.
   * <ul>
   *   <li>When {@link ValueFields} {@link ValueFields#getLongValue()} return
   * forty-two.</li>
   *   <li>Then calls {@link ValueFields#getLongValue()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JodaDateType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_whenValueFieldsGetLongValueReturnFortyTwo_thenCallsGetLongValue() {
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
   * Test {@link JodaDateType#setValue(Object, ValueFields)}.
   * <p>
   * Method under test: {@link JodaDateType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue() {
    // Arrange
    JodaDateType jodaDateType = new JodaDateType();
    org.joda.time.LocalDate localDate = new org.joda.time.LocalDate(1970, 1, 1);

    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    jodaDateType.setValue(localDate, valueFields);

    // Assert
    assertEquals(-3600000L, valueFields.getLongValue().longValue());
  }

  /**
   * Test {@link JodaDateType#setValue(Object, ValueFields)}.
   * <ul>
   *   <li>Then {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default
   * constructor) LongValue is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JodaDateType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue_thenHistoricDetailVariableInstanceUpdateEntityImplLongValueIsNull() {
    // Arrange
    JodaDateType jodaDateType = new JodaDateType();
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    jodaDateType.setValue(null, valueFields);

    // Assert
    assertNull(valueFields.getLongValue());
  }

  /**
   * Test getters and setters.
   * <p>
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
