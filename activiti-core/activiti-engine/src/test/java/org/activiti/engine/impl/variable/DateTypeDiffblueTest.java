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
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DateTypeDiffblueTest {
  /**
   * Test {@link DateType#isAbleToStore(Object)}.
   *
   * <ul>
   *   <li>When {@link JSONObject#NULL}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link DateType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DateType.isAbleToStore(Object)"})
  public void testIsAbleToStore_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new DateType().isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test {@link DateType#isAbleToStore(Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link DateType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DateType.isAbleToStore(Object)"})
  public void testIsAbleToStore_whenNull_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(new DateType().isAbleToStore(null));
  }

  /**
   * Test {@link DateType#getValue(ValueFields)}.
   *
   * <ul>
   *   <li>When {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DateType#getValue(ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object DateType.getValue(ValueFields)"})
  public void testGetValue_whenHistoricDetailVariableInstanceUpdateEntityImpl_thenReturnNull() {
    // Arrange
    DateType dateType = new DateType();

    // Act and Assert
    assertNull(dateType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link DateType#setValue(Object, ValueFields)}.
   *
   * <p>Method under test: {@link DateType#setValue(Object, ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DateType.setValue(Object, ValueFields)"})
  public void testSetValue() {
    // Arrange
    DateType dateType = new DateType();
    Date fromResult =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields =
        new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    dateType.setValue(fromResult, valueFields);

    // Assert
    assertEquals(0L, valueFields.getLongValue().longValue());
  }

  /**
   * Test {@link DateType#setValue(Object, ValueFields)}.
   *
   * <ul>
   *   <li>Then {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default constructor)
   *       LongValue is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DateType#setValue(Object, ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DateType.setValue(Object, ValueFields)"})
  public void testSetValue_thenHistoricDetailVariableInstanceUpdateEntityImplLongValueIsNull() {
    // Arrange
    DateType dateType = new DateType();
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields =
        new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    dateType.setValue(null, valueFields);

    // Assert that nothing has changed
    assertNull(valueFields.getLongValue());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link DateType}
   *   <li>{@link DateType#getTypeName()}
   *   <li>{@link DateType#isCachable()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DateType.<init>()",
    "String DateType.getTypeName()",
    "boolean DateType.isCachable()"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    DateType actualDateType = new DateType();
    String actualTypeName = actualDateType.getTypeName();

    // Assert
    assertEquals("date", actualTypeName);
    assertTrue(actualDateType.isCachable());
  }
}
