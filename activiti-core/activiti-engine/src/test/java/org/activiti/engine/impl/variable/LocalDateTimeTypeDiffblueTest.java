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
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class LocalDateTimeTypeDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link LocalDateTimeType}
   *   <li>{@link LocalDateTimeType#getTypeName()}
   *   <li>{@link LocalDateTimeType#isCachable()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    LocalDateTimeType actualLocalDateTimeType = new LocalDateTimeType();
    String actualTypeName = actualLocalDateTimeType.getTypeName();

    // Assert
    assertEquals("localDateTime", actualTypeName);
    assertTrue(actualLocalDateTimeType.isCachable());
  }

  /**
   * Method under test: {@link LocalDateTimeType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore() {
    // Arrange, Act and Assert
    assertFalse((new LocalDateTimeType()).isAbleToStore(JSONObject.NULL));
    assertTrue((new LocalDateTimeType()).isAbleToStore(null));
  }

  /**
   * Method under test: {@link LocalDateTimeType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue() {
    // Arrange
    LocalDateTimeType localDateTimeType = new LocalDateTimeType();
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    localDateTimeType.setValue(null, valueFields);

    // Assert
    assertNull(valueFields.getLongValue());
  }

  /**
   * Method under test: {@link LocalDateTimeType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue2() {
    // Arrange
    LocalDateTimeType localDateTimeType = new LocalDateTimeType();
    LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    localDateTimeType.setValue(atStartOfDayResult, valueFields);

    // Assert
    assertEquals(0L, valueFields.getLongValue().longValue());
  }
}
