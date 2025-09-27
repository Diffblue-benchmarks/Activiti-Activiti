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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntityImpl;
import org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.YearMonthDay;
import org.joda.time.chrono.ISOChronology;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class JodaDateTimeTypeDiffblueTest {
  /**
   * Test {@link JodaDateTimeType#isAbleToStore(Object)}.
   *
   * <ul>
   *   <li>When {@link JSONObject#NULL}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JodaDateTimeType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JodaDateTimeType.isAbleToStore(Object)"})
  public void testIsAbleToStore_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new JodaDateTimeType().isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test {@link JodaDateTimeType#isAbleToStore(Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link JodaDateTimeType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JodaDateTimeType.isAbleToStore(Object)"})
  public void testIsAbleToStore_whenNull_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(new JodaDateTimeType().isAbleToStore(null));
  }

  /**
   * Test {@link JodaDateTimeType#getValue(ValueFields)}.
   *
   * <ul>
   *   <li>Given {@link Long#MAX_VALUE}.
   *   <li>Then return Weekyear is {@code -292275055}.
   * </ul>
   *
   * <p>Method under test: {@link JodaDateTimeType#getValue(ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JodaDateTimeType.getValue(ValueFields)"})
  public void testGetValue_givenMax_value_thenReturnWeekyearIs292275055() {
    // Arrange
    JodaDateTimeType jodaDateTimeType = new JodaDateTimeType();

    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getLongValue()).thenReturn(Long.MAX_VALUE);

    // Act
    Object actualValue = jodaDateTimeType.getValue(valueFields);

    // Assert
    verify(valueFields).getLongValue();
    assertTrue(actualValue instanceof DateTime);
    assertEquals(-292275055, ((DateTime) actualValue).getWeekyear());
    assertEquals(12, ((DateTime) actualValue).getMinuteOfHour());
    assertEquals(17, ((DateTime) actualValue).getDayOfMonth());
    assertEquals(229, ((DateTime) actualValue).getDayOfYear());
    assertEquals(25975, ((DateTime) actualValue).getSecondOfDay());
    assertEquals(25975807, ((DateTime) actualValue).getMillisOfDay());
    assertEquals(2922789, ((DateTime) actualValue).getCenturyOfEra());
    assertEquals(292278994, ((DateTime) actualValue).getYear());
    assertEquals(292278994, ((DateTime) actualValue).getYearOfEra());
    assertEquals(432, ((DateTime) actualValue).getMinuteOfDay());
    assertEquals(55, ((DateTime) actualValue).getSecondOfMinute());
    assertEquals(7, ((DateTime) actualValue).getDayOfWeek());
    assertEquals(7, ((DateTime) actualValue).getHourOfDay());
    assertEquals(8, ((DateTime) actualValue).getMonthOfYear());
    assertEquals(807, ((DateTime) actualValue).getMillisOfSecond());
    assertEquals(94, ((DateTime) actualValue).getYearOfCentury());
    assertEquals(Long.MAX_VALUE, ((DateTime) actualValue).getMillis());
  }

  /**
   * Test {@link JodaDateTimeType#getValue(ValueFields)}.
   *
   * <ul>
   *   <li>Given {@link Long#MIN_VALUE}.
   *   <li>Then return Year is {@code -292275055}.
   * </ul>
   *
   * <p>Method under test: {@link JodaDateTimeType#getValue(ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JodaDateTimeType.getValue(ValueFields)"})
  public void testGetValue_givenMin_value_thenReturnYearIs292275055() {
    // Arrange
    JodaDateTimeType jodaDateTimeType = new JodaDateTimeType();

    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getLongValue()).thenReturn(Long.MIN_VALUE);

    // Act
    Object actualValue = jodaDateTimeType.getValue(valueFields);

    // Assert
    verify(valueFields).getLongValue();
    assertTrue(actualValue instanceof DateTime);
    assertEquals(-292275055, ((DateTime) actualValue).getYear());
    assertEquals(0, ((DateTime) actualValue).getEra());
    assertEquals(1007, ((DateTime) actualValue).getMinuteOfDay());
    assertEquals(136, ((DateTime) actualValue).getDayOfYear());
    assertEquals(192, ((DateTime) actualValue).getMillisOfSecond());
    assertEquals(2922750, ((DateTime) actualValue).getCenturyOfEra());
    assertEquals(292275056, ((DateTime) actualValue).getYearOfEra());
    assertEquals(292278994, ((DateTime) actualValue).getWeekyear());
    assertEquals(4, ((DateTime) actualValue).getSecondOfMinute());
    assertEquals(47, ((DateTime) actualValue).getMinuteOfHour());
    assertEquals(5, ((DateTime) actualValue).getMonthOfYear());
    assertEquals(52, ((DateTime) actualValue).getWeekOfWeekyear());
    assertEquals(55, ((DateTime) actualValue).getYearOfCentury());
    assertEquals(6, ((DateTime) actualValue).getDayOfWeek());
    assertEquals(60424, ((DateTime) actualValue).getSecondOfDay());
    assertEquals(60424192, ((DateTime) actualValue).getMillisOfDay());
    assertEquals(Long.MIN_VALUE, ((DateTime) actualValue).getMillis());
    assertEquals(Short.SIZE, ((DateTime) actualValue).getDayOfMonth());
    assertEquals(Short.SIZE, ((DateTime) actualValue).getHourOfDay());
  }

  /**
   * Test {@link JodaDateTimeType#getValue(ValueFields)}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.
   *   <li>Then Chronology return {@link ISOChronology}.
   * </ul>
   *
   * <p>Method under test: {@link JodaDateTimeType#getValue(ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JodaDateTimeType.getValue(ValueFields)"})
  public void testGetValue_givenNull_thenChronologyReturnISOChronology() {
    // Arrange
    JodaDateTimeType jodaDateTimeType = new JodaDateTimeType();

    HistoricVariableInstanceEntityImpl valueFields = new HistoricVariableInstanceEntityImpl();
    valueFields.setCachedValue(JSONObject.NULL);
    valueFields.setCreateTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setDeleted(true);
    valueFields.setDoubleValue(10.0d);
    valueFields.setExecutionId("42");
    valueFields.setId("42");
    valueFields.setInserted(true);
    valueFields.setLastUpdatedTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setLongValue(42L);
    valueFields.setName("Name");
    valueFields.setProcessInstanceId("42");
    valueFields.setRevision(1);
    valueFields.setTaskId("42");
    valueFields.setTextValue("42");
    valueFields.setTextValue2("42");
    valueFields.setUpdated(true);
    valueFields.setVariableType(new BigDecimalType());

    // Act
    Object actualValue = jodaDateTimeType.getValue(valueFields);

    // Assert
    assertTrue(actualValue instanceof DateTime);
    assertTrue(((DateTime) actualValue).getChronology() instanceof ISOChronology);
    assertEquals(actualValue, ((DateTime) actualValue).toMutableDateTime());
    assertEquals(actualValue, ((DateTime) actualValue).toMutableDateTimeISO());
    assertSame(actualValue, ((DateTime) actualValue).toDateTime());
    assertSame(actualValue, ((DateTime) actualValue).toDateTimeISO());
  }

  /**
   * Test {@link JodaDateTimeType#getValue(ValueFields)}.
   *
   * <ul>
   *   <li>Then return array length is three.
   * </ul>
   *
   * <p>Method under test: {@link JodaDateTimeType#getValue(ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JodaDateTimeType.getValue(ValueFields)"})
  public void testGetValue_thenReturnArrayLengthIsThree() {
    // Arrange
    JodaDateTimeType jodaDateTimeType = new JodaDateTimeType();

    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getLongValue()).thenReturn(42L);

    // Act
    Object actualValue = jodaDateTimeType.getValue(valueFields);

    // Assert
    verify(valueFields).getLongValue();
    assertTrue(actualValue instanceof DateTime);
    YearMonthDay toYearMonthDayResult = ((DateTime) actualValue).toYearMonthDay();
    assertEquals(3, toYearMonthDayResult.getFields().length);
    assertSame(actualValue, ((DateTime) actualValue).toDateTimeISO());
    Instant toInstantResult = ((DateTime) actualValue).toInstant();
    Instant actualToInstantResult = toInstantResult.toInstant();
    assertSame(toInstantResult, actualToInstantResult);
    assertArrayEquals(new int[] {1970, 1, 1}, toYearMonthDayResult.getValues());
    assertArrayEquals(new int[] {0, 0, 0, 42}, ((DateTime) actualValue).toTimeOfDay().getValues());
    assertArrayEquals(
        new int[] {1970, 1, 1, 42}, ((DateTime) actualValue).toLocalDateTime().getValues());
  }

  /**
   * Test {@link JodaDateTimeType#getValue(ValueFields)}.
   *
   * <ul>
   *   <li>When {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JodaDateTimeType#getValue(ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JodaDateTimeType.getValue(ValueFields)"})
  public void testGetValue_whenHistoricDetailVariableInstanceUpdateEntityImpl_thenReturnNull() {
    // Arrange
    JodaDateTimeType jodaDateTimeType = new JodaDateTimeType();

    // Act and Assert
    assertNull(jodaDateTimeType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link JodaDateTimeType#setValue(Object, ValueFields)}.
   *
   * <p>Method under test: {@link JodaDateTimeType#setValue(Object, ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JodaDateTimeType.setValue(Object, ValueFields)"})
  public void testSetValue() {
    // Arrange
    JodaDateTimeType jodaDateTimeType = new JodaDateTimeType();

    DateTime dateTime = mock(DateTime.class);
    when(dateTime.getMillis()).thenReturn(1L);
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields =
        new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    jodaDateTimeType.setValue(dateTime, valueFields);

    // Assert
    verify(dateTime).getMillis();
    assertEquals(1L, valueFields.getLongValue().longValue());
  }

  /**
   * Test {@link JodaDateTimeType#setValue(Object, ValueFields)}.
   *
   * <ul>
   *   <li>Then {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default constructor)
   *       LongValue is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JodaDateTimeType#setValue(Object, ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JodaDateTimeType.setValue(Object, ValueFields)"})
  public void testSetValue_thenHistoricDetailVariableInstanceUpdateEntityImplLongValueIsNull() {
    // Arrange
    JodaDateTimeType jodaDateTimeType = new JodaDateTimeType();
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields =
        new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    jodaDateTimeType.setValue(null, valueFields);

    // Assert that nothing has changed
    assertNull(valueFields.getLongValue());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link JodaDateTimeType}
   *   <li>{@link JodaDateTimeType#getTypeName()}
   *   <li>{@link JodaDateTimeType#isCachable()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void JodaDateTimeType.<init>()",
    "String JodaDateTimeType.getTypeName()",
    "boolean JodaDateTimeType.isCachable()"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    JodaDateTimeType actualJodaDateTimeType = new JodaDateTimeType();
    String actualTypeName = actualJodaDateTimeType.getTypeName();

    // Assert
    assertEquals("jodadatetime", actualTypeName);
    assertTrue(actualJodaDateTimeType.isCachable());
  }
}
