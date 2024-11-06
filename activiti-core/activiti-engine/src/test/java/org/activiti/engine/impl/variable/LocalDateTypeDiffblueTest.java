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
   * Test {@link LocalDateType#isAbleToStore(Object)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LocalDateType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new LocalDateType()).isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test {@link LocalDateType#isAbleToStore(Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LocalDateType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_whenNull_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((new LocalDateType()).isAbleToStore(null));
  }

  /**
   * Test {@link LocalDateType#getValue(ValueFields)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LocalDateType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_given42() {
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
   * Test {@link LocalDateType#getValue(ValueFields)}.
   * <ul>
   *   <li>When {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default
   * constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LocalDateType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_whenHistoricDetailVariableInstanceUpdateEntityImpl_thenReturnNull() {
    // Arrange
    LocalDateType localDateType = new LocalDateType();

    // Act and Assert
    assertNull(localDateType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link LocalDateType#getValue(ValueFields)}.
   * <ul>
   *   <li>When {@link ValueFields} {@link ValueFields#getLongValue()} return
   * forty-two.</li>
   *   <li>Then calls {@link ValueFields#getLongValue()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LocalDateType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_whenValueFieldsGetLongValueReturnFortyTwo_thenCallsGetLongValue() {
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
   * Test {@link LocalDateType#setValue(Object, ValueFields)}.
   * <p>
   * Method under test: {@link LocalDateType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue() {
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
   * Test {@link LocalDateType#setValue(Object, ValueFields)}.
   * <ul>
   *   <li>Then {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default
   * constructor) LongValue is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LocalDateType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue_thenHistoricDetailVariableInstanceUpdateEntityImplLongValueIsNull() {
    // Arrange
    LocalDateType localDateType = new LocalDateType();
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    localDateType.setValue(null, valueFields);

    // Assert
    assertNull(valueFields.getLongValue());
  }

  /**
   * Test getters and setters.
   * <p>
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
