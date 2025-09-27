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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class LocalDateTimeTypeDiffblueTest {
  /**
   * Test {@link LocalDateTimeType#isAbleToStore(Object)}.
   *
   * <ul>
   *   <li>When {@link JSONObject#NULL}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link LocalDateTimeType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean LocalDateTimeType.isAbleToStore(Object)"})
  public void testIsAbleToStore_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new LocalDateTimeType().isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test {@link LocalDateTimeType#isAbleToStore(Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link LocalDateTimeType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean LocalDateTimeType.isAbleToStore(Object)"})
  public void testIsAbleToStore_whenNull_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(new LocalDateTimeType().isAbleToStore(null));
  }

  /**
   * Test {@link LocalDateTimeType#setValue(Object, ValueFields)}.
   *
   * <p>Method under test: {@link LocalDateTimeType#setValue(Object, ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LocalDateTimeType.setValue(Object, ValueFields)"})
  public void testSetValue() {
    // Arrange
    LocalDateTimeType localDateTimeType = new LocalDateTimeType();
    LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields =
        new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    localDateTimeType.setValue(atStartOfDayResult, valueFields);

    // Assert
    assertEquals(0L, valueFields.getLongValue().longValue());
  }

  /**
   * Test {@link LocalDateTimeType#setValue(Object, ValueFields)}.
   *
   * <ul>
   *   <li>Then {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default constructor)
   *       LongValue is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link LocalDateTimeType#setValue(Object, ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LocalDateTimeType.setValue(Object, ValueFields)"})
  public void testSetValue_thenHistoricDetailVariableInstanceUpdateEntityImplLongValueIsNull() {
    // Arrange
    LocalDateTimeType localDateTimeType = new LocalDateTimeType();
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields =
        new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    localDateTimeType.setValue(null, valueFields);

    // Assert that nothing has changed
    assertNull(valueFields.getLongValue());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link LocalDateTimeType}
   *   <li>{@link LocalDateTimeType#getTypeName()}
   *   <li>{@link LocalDateTimeType#isCachable()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void LocalDateTimeType.<init>()",
    "String LocalDateTimeType.getTypeName()",
    "boolean LocalDateTimeType.isCachable()"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    LocalDateTimeType actualLocalDateTimeType = new LocalDateTimeType();
    String actualTypeName = actualLocalDateTimeType.getTypeName();

    // Assert
    assertEquals("localDateTime", actualTypeName);
    assertTrue(actualLocalDateTimeType.isCachable());
  }
}
