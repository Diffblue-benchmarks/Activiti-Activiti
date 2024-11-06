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
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntityImpl;
import org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.joda.time.Chronology;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.LocalDateTime;
import org.joda.time.MutableDateTime;
import org.joda.time.TimeOfDay;
import org.joda.time.YearMonthDay;
import org.joda.time.chrono.ISOChronology;
import org.junit.Test;

public class JodaDateTimeTypeDiffblueTest {
  /**
   * Test {@link JodaDateTimeType#isAbleToStore(Object)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JodaDateTimeType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new JodaDateTimeType()).isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test {@link JodaDateTimeType#isAbleToStore(Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JodaDateTimeType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_whenNull_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((new JodaDateTimeType()).isAbleToStore(null));
  }

  /**
   * Test {@link JodaDateTimeType#getValue(ValueFields)}.
   * <ul>
   *   <li>Given {@link Long#MIN_VALUE}.</li>
   *   <li>Then return toDateTimeISO year AsShortText is {@code -292275055}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JodaDateTimeType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_givenMin_value_thenReturnToDateTimeISOYearAsShortTextIs292275055() {
    // Arrange
    JodaDateTimeType jodaDateTimeType = new JodaDateTimeType();
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getLongValue()).thenReturn(Long.MIN_VALUE);

    // Act
    Object actualValue = jodaDateTimeType.getValue(valueFields);

    // Assert
    verify(valueFields).getLongValue();
    assertTrue(actualValue instanceof DateTime);
    DateTime toDateTimeISOResult = ((DateTime) actualValue).toDateTimeISO();
    DateTime.Property yearResult = toDateTimeISOResult.year();
    assertEquals("-292275055", yearResult.getAsShortText());
    DateTime.Property yearResult2 = ((DateTime) actualValue).year();
    assertEquals("-292275055", yearResult2.getAsShortText());
    LocalDateTime toLocalDateTimeResult = ((DateTime) actualValue).toLocalDateTime();
    LocalDateTime.Property yearResult3 = toLocalDateTimeResult.year();
    assertEquals("-292275055", yearResult3.getAsShortText());
    assertEquals("-292275055", yearResult.getAsString());
    assertEquals("-292275055", yearResult2.getAsString());
    assertEquals("-292275055", yearResult3.getAsString());
    assertEquals("-292275055", yearResult.getAsText());
    assertEquals("-292275055", yearResult2.getAsText());
    assertEquals("-292275055", yearResult3.getAsText());
    YearMonthDay toYearMonthDayResult = ((DateTime) actualValue).toYearMonthDay();
    assertEquals("-292275055-05-16", toYearMonthDayResult.toLocalDate().toString());
    DateTime.Property eraResult = toDateTimeISOResult.era();
    assertEquals("0", eraResult.getAsString());
    DateTime.Property eraResult2 = ((DateTime) actualValue).era();
    assertEquals("0", eraResult2.getAsString());
    LocalDateTime.Property eraResult3 = toLocalDateTimeResult.era();
    assertEquals("0", eraResult3.getAsString());
    MutableDateTime toMutableDateTimeResult = ((DateTime) actualValue).toMutableDateTime();
    MutableDateTime.Property eraResult4 = toMutableDateTimeResult.era();
    assertEquals("0", eraResult4.getAsString());
    DateMidnight toDateMidnightResult = ((DateTime) actualValue).toDateMidnight();
    DateTime toDateTimeResult = toDateMidnightResult.toDateTime();
    assertEquals("14:25:51.616", toDateTimeResult.toLocalTime().toString());
    DateTime toDateTimeISOResult2 = toDateMidnightResult.toDateTimeISO();
    assertEquals("14:25:51.616", toDateTimeISOResult2.toLocalTime().toString());
    DateTime toDateTimeISOResult3 = toDateTimeISOResult.toDateTimeISO();
    assertEquals("16:47:04.192", toDateTimeISOResult3.toLocalTime().toString());
    assertEquals("16:47:04.192", toDateTimeISOResult.toLocalTime().toString());
    Instant toInstantResult = ((DateTime) actualValue).toInstant();
    DateTime toDateTimeResult2 = toInstantResult.toDateTime();
    assertEquals("16:47:04.192", toDateTimeResult2.toLocalTime().toString());
    DateTime toDateTimeISOResult4 = toInstantResult.toDateTimeISO();
    assertEquals("16:47:04.192", toDateTimeISOResult4.toLocalTime().toString());
    DateTime toDateTimeResult3 = toLocalDateTimeResult.toDateTime();
    assertEquals("16:47:04.192", toDateTimeResult3.toLocalTime().toString());
    assertEquals("16:47:04.192", ((DateTime) actualValue).toLocalTime().toString());
    LocalDateTime toLocalDateTimeResult2 = toDateTimeISOResult.toLocalDateTime();
    assertEquals("16:47:04.192", toLocalDateTimeResult2.toLocalTime().toString());
    assertEquals("16:47:04.192", toLocalDateTimeResult.toLocalTime().toString());
    TimeOfDay toTimeOfDayResult = toDateTimeISOResult.toTimeOfDay();
    assertEquals("16:47:04.192", toTimeOfDayResult.toLocalTime().toString());
    TimeOfDay toTimeOfDayResult2 = ((DateTime) actualValue).toTimeOfDay();
    assertEquals("16:47:04.192", toTimeOfDayResult2.toLocalTime().toString());
    DateTime.Property weekyearResult = toDateTimeISOResult.weekyear();
    assertEquals("292278994", weekyearResult.getAsShortText());
    DateTime.Property weekyearResult2 = ((DateTime) actualValue).weekyear();
    assertEquals("292278994", weekyearResult2.getAsShortText());
    LocalDateTime.Property weekyearResult3 = toLocalDateTimeResult.weekyear();
    assertEquals("292278994", weekyearResult3.getAsShortText());
    assertEquals("292278994", weekyearResult.getAsString());
    assertEquals("292278994", weekyearResult2.getAsString());
    assertEquals("292278994", weekyearResult3.getAsString());
    assertEquals("292278994", weekyearResult.getAsText());
    assertEquals("292278994", weekyearResult2.getAsText());
    assertEquals("292278994", weekyearResult3.getAsText());
    DateMidnight toDateMidnightResult2 = toDateTimeISOResult.toDateMidnight();
    assertEquals("292278994-08-16", toDateMidnightResult2.toLocalDate().toString());
    assertEquals("292278994-08-16", toDateMidnightResult.toLocalDate().toString());
    assertEquals("292278994-08-16", toDateTimeISOResult3.toLocalDate().toString());
    assertEquals("292278994-08-16", toDateTimeISOResult.toLocalDate().toString());
    assertEquals("292278994-08-16", toDateTimeResult2.toLocalDate().toString());
    assertEquals("292278994-08-16", toDateTimeISOResult4.toLocalDate().toString());
    assertEquals("292278994-08-16", toDateTimeResult3.toLocalDate().toString());
    assertEquals("292278994-08-16", toDateTimeResult.toLocalDate().toString());
    assertEquals("292278994-08-16", toDateTimeISOResult2.toLocalDate().toString());
    assertEquals("292278994-08-16", ((DateTime) actualValue).toLocalDate().toString());
    assertEquals("292278994-08-16", toLocalDateTimeResult2.toLocalDate().toString());
    assertEquals("292278994-08-16", toLocalDateTimeResult.toLocalDate().toString());
    YearMonthDay toYearMonthDayResult2 = toDateMidnightResult.toYearMonthDay();
    assertEquals("292278994-08-16", toYearMonthDayResult2.toLocalDate().toString());
    assertEquals("BC", eraResult.getAsShortText());
    assertEquals("BC", eraResult2.getAsShortText());
    assertEquals("BC", eraResult3.getAsShortText());
    assertEquals("BC", eraResult4.getAsShortText());
    assertEquals("BC", eraResult.getAsText());
    assertEquals("BC", eraResult2.getAsText());
    assertEquals("BC", eraResult3.getAsText());
    assertEquals("BC", eraResult4.getAsText());
    assertEquals(-292275055, toLocalDateTimeResult2.getYear());
    assertEquals(-292275055, toLocalDateTimeResult.getYear());
    assertEquals(-292275055, toYearMonthDayResult.getYear());
    assertEquals(-292275055, toDateTimeISOResult3.getYear());
    assertEquals(-292275055, toDateTimeISOResult.getYear());
    assertEquals(-292275055, toDateTimeResult2.getYear());
    assertEquals(-292275055, toDateTimeISOResult4.getYear());
    MutableDateTime toMutableDateTimeResult2 = toInstantResult.toMutableDateTime();
    assertEquals(-292275055, toMutableDateTimeResult2.getYear());
    MutableDateTime toMutableDateTimeISOResult = toInstantResult.toMutableDateTimeISO();
    assertEquals(-292275055, toMutableDateTimeISOResult.getYear());
    assertEquals(-292275055, toDateTimeResult3.getYear());
    MutableDateTime toMutableDateTimeResult3 = toDateTimeISOResult.toMutableDateTime();
    assertEquals(-292275055, toMutableDateTimeResult3.getYear());
    assertEquals(-292275055, toMutableDateTimeResult.getYear());
    MutableDateTime toMutableDateTimeISOResult2 = toDateTimeISOResult.toMutableDateTimeISO();
    assertEquals(-292275055, toMutableDateTimeISOResult2.getYear());
    MutableDateTime toMutableDateTimeISOResult3 = ((DateTime) actualValue).toMutableDateTimeISO();
    assertEquals(-292275055, toMutableDateTimeISOResult3.getYear());
    assertEquals(-292275055, ((DateTime) actualValue).getYear());
    assertEquals(-292275055, yearResult.get());
    assertEquals(-292275055, yearResult2.get());
    assertEquals(-292275055, yearResult3.get());
    DateMidnight.Property eraResult5 = toDateMidnightResult.era();
    assertEquals(-9223309901318400000L, eraResult5.remainder());
    assertEquals(0, toLocalDateTimeResult2.getEra());
    assertEquals(0, toLocalDateTimeResult.getEra());
    assertEquals(0, toDateTimeISOResult3.getEra());
    assertEquals(0, toDateTimeISOResult.getEra());
    assertEquals(0, toDateTimeResult2.getEra());
    assertEquals(0, toDateTimeISOResult4.getEra());
    assertEquals(0, toMutableDateTimeResult2.getEra());
    assertEquals(0, toMutableDateTimeISOResult.getEra());
    assertEquals(0, toDateTimeResult3.getEra());
    assertEquals(0, toMutableDateTimeResult3.getEra());
    assertEquals(0, toMutableDateTimeResult.getEra());
    assertEquals(0, toMutableDateTimeISOResult2.getEra());
    assertEquals(0, toMutableDateTimeISOResult3.getEra());
    assertEquals(0, ((DateTime) actualValue).getEra());
    assertEquals(0, eraResult.get());
    assertEquals(0, eraResult2.get());
    assertEquals(0, eraResult3.get());
    assertEquals(0, eraResult4.get());
    assertEquals(0L, eraResult.remainder());
    assertEquals(0L, eraResult2.remainder());
    assertEquals(0L, eraResult3.remainder());
    assertEquals(1007, toDateTimeISOResult3.getMinuteOfDay());
    assertEquals(1007, toDateTimeISOResult.getMinuteOfDay());
    assertEquals(1007, toDateTimeResult2.getMinuteOfDay());
    assertEquals(1007, toDateTimeISOResult4.getMinuteOfDay());
    assertEquals(1007, toMutableDateTimeResult2.getMinuteOfDay());
    assertEquals(1007, toMutableDateTimeISOResult.getMinuteOfDay());
    assertEquals(1007, toDateTimeResult3.getMinuteOfDay());
    assertEquals(1007, toMutableDateTimeResult3.getMinuteOfDay());
    assertEquals(1007, toMutableDateTimeResult.getMinuteOfDay());
    assertEquals(1007, toMutableDateTimeISOResult2.getMinuteOfDay());
    assertEquals(1007, toMutableDateTimeISOResult3.getMinuteOfDay());
    assertEquals(1007, ((DateTime) actualValue).getMinuteOfDay());
    assertEquals(11724424192L, yearResult.remainder());
    assertEquals(11724424192L, yearResult2.remainder());
    assertEquals(11724424192L, yearResult3.remainder());
    assertEquals(136, toLocalDateTimeResult2.getDayOfYear());
    assertEquals(136, toLocalDateTimeResult.getDayOfYear());
    assertEquals(136, toDateTimeISOResult3.getDayOfYear());
    assertEquals(136, toDateTimeISOResult.getDayOfYear());
    assertEquals(136, toDateTimeResult2.getDayOfYear());
    assertEquals(136, toDateTimeISOResult4.getDayOfYear());
    assertEquals(136, toMutableDateTimeResult2.getDayOfYear());
    assertEquals(136, toMutableDateTimeISOResult.getDayOfYear());
    assertEquals(136, toDateTimeResult3.getDayOfYear());
    assertEquals(136, toMutableDateTimeResult3.getDayOfYear());
    assertEquals(136, toMutableDateTimeResult.getDayOfYear());
    assertEquals(136, toMutableDateTimeISOResult2.getDayOfYear());
    assertEquals(136, toMutableDateTimeISOResult3.getDayOfYear());
    assertEquals(136, ((DateTime) actualValue).getDayOfYear());
    assertEquals(14, toDateMidnightResult2.getHourOfDay());
    assertEquals(14, toDateMidnightResult.getHourOfDay());
    assertEquals(14, toDateTimeResult.getHourOfDay());
    assertEquals(14, toDateTimeISOResult2.getHourOfDay());
    MutableDateTime toMutableDateTimeResult4 = toDateMidnightResult.toMutableDateTime();
    assertEquals(14, toMutableDateTimeResult4.getHourOfDay());
    MutableDateTime toMutableDateTimeISOResult4 = toDateMidnightResult.toMutableDateTimeISO();
    assertEquals(14, toMutableDateTimeISOResult4.getHourOfDay());
    assertEquals(192, toLocalDateTimeResult2.getMillisOfSecond());
    assertEquals(192, toLocalDateTimeResult.getMillisOfSecond());
    assertEquals(192, toTimeOfDayResult.getMillisOfSecond());
    assertEquals(192, toTimeOfDayResult2.getMillisOfSecond());
    assertEquals(192, toDateTimeISOResult3.getMillisOfSecond());
    assertEquals(192, toDateTimeISOResult.getMillisOfSecond());
    assertEquals(192, toDateTimeResult2.getMillisOfSecond());
    assertEquals(192, toDateTimeISOResult4.getMillisOfSecond());
    assertEquals(192, toMutableDateTimeResult2.getMillisOfSecond());
    assertEquals(192, toMutableDateTimeISOResult.getMillisOfSecond());
    assertEquals(192, toDateTimeResult3.getMillisOfSecond());
    assertEquals(192, toMutableDateTimeResult3.getMillisOfSecond());
    assertEquals(192, toMutableDateTimeResult.getMillisOfSecond());
    assertEquals(192, toMutableDateTimeISOResult2.getMillisOfSecond());
    assertEquals(192, toMutableDateTimeISOResult3.getMillisOfSecond());
    assertEquals(192, ((DateTime) actualValue).getMillisOfSecond());
    DateMidnight.Property yearResult4 = toDateMidnightResult.year();
    assertEquals(19664751616L, yearResult4.remainder());
    assertEquals(228, toDateMidnightResult2.getDayOfYear());
    assertEquals(228, toDateMidnightResult.getDayOfYear());
    assertEquals(228, toDateTimeResult.getDayOfYear());
    assertEquals(228, toDateTimeISOResult2.getDayOfYear());
    assertEquals(228, toMutableDateTimeResult4.getDayOfYear());
    assertEquals(228, toMutableDateTimeISOResult4.getDayOfYear());
    assertEquals(25, toDateMidnightResult2.getMinuteOfHour());
    assertEquals(25, toDateMidnightResult.getMinuteOfHour());
    assertEquals(25, toDateTimeResult.getMinuteOfHour());
    assertEquals(25, toDateTimeISOResult2.getMinuteOfHour());
    assertEquals(25, toMutableDateTimeResult4.getMinuteOfHour());
    assertEquals(25, toMutableDateTimeISOResult4.getMinuteOfHour());
    assertEquals(2922750, toLocalDateTimeResult2.getCenturyOfEra());
    assertEquals(2922750, toLocalDateTimeResult.getCenturyOfEra());
    assertEquals(2922750, toDateTimeISOResult3.getCenturyOfEra());
    assertEquals(2922750, toDateTimeISOResult.getCenturyOfEra());
    assertEquals(2922750, toDateTimeResult2.getCenturyOfEra());
    assertEquals(2922750, toDateTimeISOResult4.getCenturyOfEra());
    assertEquals(2922750, toMutableDateTimeResult2.getCenturyOfEra());
    assertEquals(2922750, toMutableDateTimeISOResult.getCenturyOfEra());
    assertEquals(2922750, toDateTimeResult3.getCenturyOfEra());
    assertEquals(2922750, toMutableDateTimeResult3.getCenturyOfEra());
    assertEquals(2922750, toMutableDateTimeResult.getCenturyOfEra());
    assertEquals(2922750, toMutableDateTimeISOResult2.getCenturyOfEra());
    assertEquals(2922750, toMutableDateTimeISOResult3.getCenturyOfEra());
    assertEquals(2922750, ((DateTime) actualValue).getCenturyOfEra());
    assertEquals(292275056, toLocalDateTimeResult2.getYearOfEra());
    assertEquals(292275056, toLocalDateTimeResult.getYearOfEra());
    assertEquals(292275056, toDateTimeISOResult3.getYearOfEra());
    assertEquals(292275056, toDateTimeISOResult.getYearOfEra());
    assertEquals(292275056, toDateTimeResult2.getYearOfEra());
    assertEquals(292275056, toDateTimeISOResult4.getYearOfEra());
    assertEquals(292275056, toMutableDateTimeResult2.getYearOfEra());
    assertEquals(292275056, toMutableDateTimeISOResult.getYearOfEra());
    assertEquals(292275056, toDateTimeResult3.getYearOfEra());
    assertEquals(292275056, toMutableDateTimeResult3.getYearOfEra());
    assertEquals(292275056, toMutableDateTimeResult.getYearOfEra());
    assertEquals(292275056, toMutableDateTimeISOResult2.getYearOfEra());
    assertEquals(292275056, toMutableDateTimeISOResult3.getYearOfEra());
    assertEquals(292275056, ((DateTime) actualValue).getYearOfEra());
    assertEquals(292278994, toLocalDateTimeResult2.getWeekyear());
    assertEquals(292278994, toLocalDateTimeResult.getWeekyear());
    assertEquals(292278994, toDateTimeISOResult3.getWeekyear());
    assertEquals(292278994, toDateTimeISOResult.getWeekyear());
    assertEquals(292278994, toDateTimeResult2.getWeekyear());
    assertEquals(292278994, toDateTimeISOResult4.getWeekyear());
    assertEquals(292278994, toMutableDateTimeResult2.getWeekyear());
    assertEquals(292278994, toMutableDateTimeISOResult.getWeekyear());
    assertEquals(292278994, toDateTimeResult3.getWeekyear());
    assertEquals(292278994, toMutableDateTimeResult3.getWeekyear());
    assertEquals(292278994, toMutableDateTimeResult.getWeekyear());
    assertEquals(292278994, toMutableDateTimeISOResult2.getWeekyear());
    assertEquals(292278994, toMutableDateTimeISOResult3.getWeekyear());
    assertEquals(292278994, ((DateTime) actualValue).getWeekyear());
    assertEquals(292278994, weekyearResult.get());
    assertEquals(292278994, weekyearResult2.get());
    assertEquals(292278994, weekyearResult3.get());
    int[] values = toYearMonthDayResult2.getValues();
    assertEquals(3, values.length);
    assertEquals(4, toLocalDateTimeResult2.getSecondOfMinute());
    assertEquals(4, toLocalDateTimeResult.getSecondOfMinute());
    assertEquals(4, toTimeOfDayResult.getSecondOfMinute());
    assertEquals(4, toTimeOfDayResult2.getSecondOfMinute());
    assertEquals(4, toDateTimeISOResult3.getSecondOfMinute());
    assertEquals(4, toDateTimeISOResult.getSecondOfMinute());
    assertEquals(4, toDateTimeResult2.getSecondOfMinute());
    assertEquals(4, toDateTimeISOResult4.getSecondOfMinute());
    assertEquals(4, toMutableDateTimeResult2.getSecondOfMinute());
    assertEquals(4, toMutableDateTimeISOResult.getSecondOfMinute());
    assertEquals(4, toDateTimeResult3.getSecondOfMinute());
    assertEquals(4, toMutableDateTimeResult3.getSecondOfMinute());
    assertEquals(4, toMutableDateTimeResult.getSecondOfMinute());
    assertEquals(4, toMutableDateTimeISOResult2.getSecondOfMinute());
    assertEquals(4, toMutableDateTimeISOResult3.getSecondOfMinute());
    assertEquals(4, ((DateTime) actualValue).getSecondOfMinute());
    assertEquals(47, toLocalDateTimeResult2.getMinuteOfHour());
    assertEquals(47, toLocalDateTimeResult.getMinuteOfHour());
    assertEquals(47, toTimeOfDayResult.getMinuteOfHour());
    assertEquals(47, toTimeOfDayResult2.getMinuteOfHour());
    assertEquals(47, toDateTimeISOResult3.getMinuteOfHour());
    assertEquals(47, toDateTimeISOResult.getMinuteOfHour());
    assertEquals(47, toDateTimeResult2.getMinuteOfHour());
    assertEquals(47, toDateTimeISOResult4.getMinuteOfHour());
    assertEquals(47, toMutableDateTimeResult2.getMinuteOfHour());
    assertEquals(47, toMutableDateTimeISOResult.getMinuteOfHour());
    assertEquals(47, toDateTimeResult3.getMinuteOfHour());
    assertEquals(47, toMutableDateTimeResult3.getMinuteOfHour());
    assertEquals(47, toMutableDateTimeResult.getMinuteOfHour());
    assertEquals(47, toMutableDateTimeISOResult2.getMinuteOfHour());
    assertEquals(47, toMutableDateTimeISOResult3.getMinuteOfHour());
    assertEquals(47, ((DateTime) actualValue).getMinuteOfHour());
    assertEquals(5, toLocalDateTimeResult2.getMonthOfYear());
    assertEquals(5, toLocalDateTimeResult.getMonthOfYear());
    assertEquals(5, toYearMonthDayResult.getMonthOfYear());
    assertEquals(5, toDateTimeISOResult3.getMonthOfYear());
    assertEquals(5, toDateTimeISOResult.getMonthOfYear());
    assertEquals(5, toDateTimeResult2.getMonthOfYear());
    assertEquals(5, toDateTimeISOResult4.getMonthOfYear());
    assertEquals(5, toMutableDateTimeResult2.getMonthOfYear());
    assertEquals(5, toMutableDateTimeISOResult.getMonthOfYear());
    assertEquals(5, toDateTimeResult3.getMonthOfYear());
    assertEquals(5, toMutableDateTimeResult3.getMonthOfYear());
    assertEquals(5, toMutableDateTimeResult.getMonthOfYear());
    assertEquals(5, toMutableDateTimeISOResult2.getMonthOfYear());
    assertEquals(5, toMutableDateTimeISOResult3.getMonthOfYear());
    assertEquals(5, ((DateTime) actualValue).getMonthOfYear());
    assertEquals(51, toDateMidnightResult2.getSecondOfMinute());
    assertEquals(51, toDateMidnightResult.getSecondOfMinute());
    assertEquals(51, toDateTimeResult.getSecondOfMinute());
    assertEquals(51, toDateTimeISOResult2.getSecondOfMinute());
    assertEquals(51, toMutableDateTimeResult4.getSecondOfMinute());
    assertEquals(51, toMutableDateTimeISOResult4.getSecondOfMinute());
    DateMidnight.Property weekyearResult4 = toDateMidnightResult.weekyear();
    assertEquals(518400000L, weekyearResult4.remainder());
    assertEquals(51951, toDateMidnightResult2.getSecondOfDay());
    assertEquals(51951, toDateMidnightResult.getSecondOfDay());
    assertEquals(51951, toDateTimeResult.getSecondOfDay());
    assertEquals(51951, toDateTimeISOResult2.getSecondOfDay());
    assertEquals(51951, toMutableDateTimeResult4.getSecondOfDay());
    assertEquals(51951, toMutableDateTimeISOResult4.getSecondOfDay());
    assertEquals(51951616, toDateMidnightResult2.getMillisOfDay());
    assertEquals(51951616, toDateMidnightResult.getMillisOfDay());
    assertEquals(51951616, toDateTimeResult.getMillisOfDay());
    assertEquals(51951616, toDateTimeISOResult2.getMillisOfDay());
    assertEquals(51951616, toMutableDateTimeResult4.getMillisOfDay());
    assertEquals(51951616, toMutableDateTimeISOResult4.getMillisOfDay());
    assertEquals(52, toLocalDateTimeResult2.getWeekOfWeekyear());
    assertEquals(52, toLocalDateTimeResult.getWeekOfWeekyear());
    assertEquals(52, toDateTimeISOResult3.getWeekOfWeekyear());
    assertEquals(52, toDateTimeISOResult.getWeekOfWeekyear());
    assertEquals(52, toDateTimeResult2.getWeekOfWeekyear());
    assertEquals(52, toDateTimeISOResult4.getWeekOfWeekyear());
    assertEquals(52, toMutableDateTimeResult2.getWeekOfWeekyear());
    assertEquals(52, toMutableDateTimeISOResult.getWeekOfWeekyear());
    assertEquals(52, toDateTimeResult3.getWeekOfWeekyear());
    assertEquals(52, toMutableDateTimeResult3.getWeekOfWeekyear());
    assertEquals(52, toMutableDateTimeResult.getWeekOfWeekyear());
    assertEquals(52, toMutableDateTimeISOResult2.getWeekOfWeekyear());
    assertEquals(52, toMutableDateTimeISOResult3.getWeekOfWeekyear());
    assertEquals(52, ((DateTime) actualValue).getWeekOfWeekyear());
    assertEquals(55, toLocalDateTimeResult2.getYearOfCentury());
    assertEquals(55, toLocalDateTimeResult.getYearOfCentury());
    assertEquals(55, toDateTimeISOResult3.getYearOfCentury());
    assertEquals(55, toDateTimeISOResult.getYearOfCentury());
    assertEquals(55, toDateTimeResult2.getYearOfCentury());
    assertEquals(55, toDateTimeISOResult4.getYearOfCentury());
    assertEquals(55, toMutableDateTimeResult2.getYearOfCentury());
    assertEquals(55, toMutableDateTimeISOResult.getYearOfCentury());
    assertEquals(55, toDateTimeResult3.getYearOfCentury());
    assertEquals(55, toMutableDateTimeResult3.getYearOfCentury());
    assertEquals(55, toMutableDateTimeResult.getYearOfCentury());
    assertEquals(55, toMutableDateTimeISOResult2.getYearOfCentury());
    assertEquals(55, toMutableDateTimeISOResult3.getYearOfCentury());
    assertEquals(55, ((DateTime) actualValue).getYearOfCentury());
    assertEquals(578824192L, weekyearResult.remainder());
    assertEquals(578824192L, weekyearResult2.remainder());
    assertEquals(578824192L, weekyearResult3.remainder());
    assertEquals(6, toLocalDateTimeResult2.getDayOfWeek());
    assertEquals(6, toLocalDateTimeResult.getDayOfWeek());
    assertEquals(6, toDateMidnightResult2.getDayOfWeek());
    assertEquals(6, toDateMidnightResult.getDayOfWeek());
    assertEquals(6, toDateTimeISOResult3.getDayOfWeek());
    assertEquals(6, toDateTimeISOResult.getDayOfWeek());
    assertEquals(6, toDateTimeResult2.getDayOfWeek());
    assertEquals(6, toDateTimeISOResult4.getDayOfWeek());
    assertEquals(6, toMutableDateTimeResult2.getDayOfWeek());
    assertEquals(6, toMutableDateTimeISOResult.getDayOfWeek());
    assertEquals(6, toDateTimeResult3.getDayOfWeek());
    assertEquals(6, toDateTimeResult.getDayOfWeek());
    assertEquals(6, toDateTimeISOResult2.getDayOfWeek());
    assertEquals(6, toMutableDateTimeResult4.getDayOfWeek());
    assertEquals(6, toMutableDateTimeResult3.getDayOfWeek());
    assertEquals(6, toMutableDateTimeResult.getDayOfWeek());
    assertEquals(6, toMutableDateTimeISOResult4.getDayOfWeek());
    assertEquals(6, toMutableDateTimeISOResult2.getDayOfWeek());
    assertEquals(6, toMutableDateTimeISOResult3.getDayOfWeek());
    assertEquals(6, ((DateTime) actualValue).getDayOfWeek());
    assertEquals(60424, toDateTimeISOResult3.getSecondOfDay());
    assertEquals(60424, toDateTimeISOResult.getSecondOfDay());
    assertEquals(60424, toDateTimeResult2.getSecondOfDay());
    assertEquals(60424, toDateTimeISOResult4.getSecondOfDay());
    assertEquals(60424, toMutableDateTimeResult2.getSecondOfDay());
    assertEquals(60424, toMutableDateTimeISOResult.getSecondOfDay());
    assertEquals(60424, toDateTimeResult3.getSecondOfDay());
    assertEquals(60424, toMutableDateTimeResult3.getSecondOfDay());
    assertEquals(60424, toMutableDateTimeResult.getSecondOfDay());
    assertEquals(60424, toMutableDateTimeISOResult2.getSecondOfDay());
    assertEquals(60424, toMutableDateTimeISOResult3.getSecondOfDay());
    assertEquals(60424, ((DateTime) actualValue).getSecondOfDay());
    assertEquals(60424192, toLocalDateTimeResult2.getMillisOfDay());
    assertEquals(60424192, toLocalDateTimeResult.getMillisOfDay());
    assertEquals(60424192, toDateTimeISOResult3.getMillisOfDay());
    assertEquals(60424192, toDateTimeISOResult.getMillisOfDay());
    assertEquals(60424192, toDateTimeResult2.getMillisOfDay());
    assertEquals(60424192, toDateTimeISOResult4.getMillisOfDay());
    assertEquals(60424192, toMutableDateTimeResult2.getMillisOfDay());
    assertEquals(60424192, toMutableDateTimeISOResult.getMillisOfDay());
    assertEquals(60424192, toDateTimeResult3.getMillisOfDay());
    assertEquals(60424192, toMutableDateTimeResult3.getMillisOfDay());
    assertEquals(60424192, toMutableDateTimeResult.getMillisOfDay());
    assertEquals(60424192, toMutableDateTimeISOResult2.getMillisOfDay());
    assertEquals(60424192, toMutableDateTimeISOResult3.getMillisOfDay());
    assertEquals(60424192, ((DateTime) actualValue).getMillisOfDay());
    assertEquals(616, toDateMidnightResult2.getMillisOfSecond());
    assertEquals(616, toDateMidnightResult.getMillisOfSecond());
    assertEquals(616, toDateTimeResult.getMillisOfSecond());
    assertEquals(616, toDateTimeISOResult2.getMillisOfSecond());
    assertEquals(616, toMutableDateTimeResult4.getMillisOfSecond());
    assertEquals(616, toMutableDateTimeISOResult4.getMillisOfSecond());
    assertEquals(865, toDateMidnightResult2.getMinuteOfDay());
    assertEquals(865, toDateMidnightResult.getMinuteOfDay());
    assertEquals(865, toDateTimeResult.getMinuteOfDay());
    assertEquals(865, toDateTimeISOResult2.getMinuteOfDay());
    assertEquals(865, toMutableDateTimeResult4.getMinuteOfDay());
    assertEquals(865, toMutableDateTimeISOResult4.getMinuteOfDay());
    assertEquals(9223372036794351616L, toDateMidnightResult.toInstant().getMillis());
    assertEquals(9223372036794351616L, toDateMidnightResult2.getMillis());
    assertEquals(9223372036794351616L, toDateMidnightResult.getMillis());
    assertEquals(9223372036794351616L, toDateTimeResult.getMillis());
    assertEquals(9223372036794351616L, toDateTimeISOResult2.getMillis());
    assertEquals(9223372036794351616L, toMutableDateTimeResult4.getMillis());
    assertEquals(9223372036794351616L, toMutableDateTimeISOResult4.getMillis());
    assertEquals(actualValue, toYearMonthDayResult.toDateTimeAtMidnight());
    assertEquals(eraResult5, toDateMidnightResult2.era());
    assertEquals(eraResult5, toDateTimeResult.era());
    assertEquals(eraResult5, toDateTimeISOResult2.era());
    assertEquals(eraResult5, toMutableDateTimeResult4.era());
    assertEquals(eraResult5, toMutableDateTimeISOResult4.era());
    assertEquals(weekyearResult4, toDateMidnightResult2.weekyear());
    assertEquals(weekyearResult4, toDateTimeResult.weekyear());
    assertEquals(weekyearResult4, toDateTimeISOResult2.weekyear());
    assertEquals(weekyearResult4, toMutableDateTimeResult4.weekyear());
    assertEquals(weekyearResult4, toMutableDateTimeISOResult4.weekyear());
    assertEquals(yearResult4, toDateMidnightResult2.year());
    assertEquals(yearResult4, toDateTimeResult.year());
    assertEquals(yearResult4, toDateTimeISOResult2.year());
    assertEquals(yearResult4, toMutableDateTimeResult4.year());
    assertEquals(yearResult4, toMutableDateTimeISOResult4.year());
    assertEquals(toYearMonthDayResult, toDateTimeISOResult3.toYearMonthDay());
    assertEquals(toYearMonthDayResult, toDateTimeISOResult.toYearMonthDay());
    assertEquals(toYearMonthDayResult, toDateTimeResult2.toYearMonthDay());
    assertEquals(toYearMonthDayResult, toDateTimeISOResult4.toYearMonthDay());
    assertEquals(toYearMonthDayResult, toDateTimeResult3.toYearMonthDay());
    assertEquals(Long.MIN_VALUE, toDateTimeISOResult.toInstant().getMillis());
    assertEquals(Long.MIN_VALUE, toInstantResult.getMillis());
    assertEquals(Long.MIN_VALUE, toDateTimeISOResult3.getMillis());
    assertEquals(Long.MIN_VALUE, toDateTimeISOResult.getMillis());
    assertEquals(Long.MIN_VALUE, toDateTimeResult2.getMillis());
    assertEquals(Long.MIN_VALUE, toDateTimeISOResult4.getMillis());
    assertEquals(Long.MIN_VALUE, toMutableDateTimeResult2.getMillis());
    assertEquals(Long.MIN_VALUE, toMutableDateTimeISOResult.getMillis());
    assertEquals(Long.MIN_VALUE, toDateTimeResult3.getMillis());
    assertEquals(Long.MIN_VALUE, toMutableDateTimeResult3.getMillis());
    assertEquals(Long.MIN_VALUE, toMutableDateTimeResult.getMillis());
    assertEquals(Long.MIN_VALUE, toMutableDateTimeISOResult2.getMillis());
    assertEquals(Long.MIN_VALUE, toMutableDateTimeISOResult3.getMillis());
    assertEquals(Long.MIN_VALUE, ((DateTime) actualValue).getMillis());
    assertEquals(Short.SIZE, toLocalDateTimeResult2.getDayOfMonth());
    assertEquals(Short.SIZE, toLocalDateTimeResult.getDayOfMonth());
    assertEquals(Short.SIZE, toLocalDateTimeResult2.getHourOfDay());
    assertEquals(Short.SIZE, toLocalDateTimeResult.getHourOfDay());
    assertEquals(Short.SIZE, toTimeOfDayResult.getHourOfDay());
    assertEquals(Short.SIZE, toTimeOfDayResult2.getHourOfDay());
    assertEquals(Short.SIZE, toYearMonthDayResult2.getDayOfMonth());
    assertEquals(Short.SIZE, toYearMonthDayResult.getDayOfMonth());
    assertEquals(Short.SIZE, toDateMidnightResult2.getDayOfMonth());
    assertEquals(Short.SIZE, toDateMidnightResult.getDayOfMonth());
    assertEquals(Short.SIZE, toDateTimeISOResult3.getDayOfMonth());
    assertEquals(Short.SIZE, toDateTimeISOResult.getDayOfMonth());
    assertEquals(Short.SIZE, toDateTimeResult2.getDayOfMonth());
    assertEquals(Short.SIZE, toDateTimeISOResult4.getDayOfMonth());
    assertEquals(Short.SIZE, toMutableDateTimeResult2.getDayOfMonth());
    assertEquals(Short.SIZE, toMutableDateTimeISOResult.getDayOfMonth());
    assertEquals(Short.SIZE, toDateTimeResult3.getDayOfMonth());
    assertEquals(Short.SIZE, toDateTimeResult.getDayOfMonth());
    assertEquals(Short.SIZE, toDateTimeISOResult2.getDayOfMonth());
    assertEquals(Short.SIZE, toMutableDateTimeResult4.getDayOfMonth());
    assertEquals(Short.SIZE, toMutableDateTimeResult3.getDayOfMonth());
    assertEquals(Short.SIZE, toMutableDateTimeResult.getDayOfMonth());
    assertEquals(Short.SIZE, toMutableDateTimeISOResult4.getDayOfMonth());
    assertEquals(Short.SIZE, toMutableDateTimeISOResult2.getDayOfMonth());
    assertEquals(Short.SIZE, toMutableDateTimeISOResult3.getDayOfMonth());
    assertEquals(Short.SIZE, ((DateTime) actualValue).getDayOfMonth());
    assertEquals(Short.SIZE, toDateTimeISOResult3.getHourOfDay());
    assertEquals(Short.SIZE, toDateTimeISOResult.getHourOfDay());
    assertEquals(Short.SIZE, toDateTimeResult2.getHourOfDay());
    assertEquals(Short.SIZE, toDateTimeISOResult4.getHourOfDay());
    assertEquals(Short.SIZE, toMutableDateTimeResult2.getHourOfDay());
    assertEquals(Short.SIZE, toMutableDateTimeISOResult.getHourOfDay());
    assertEquals(Short.SIZE, toDateTimeResult3.getHourOfDay());
    assertEquals(Short.SIZE, toMutableDateTimeResult3.getHourOfDay());
    assertEquals(Short.SIZE, toMutableDateTimeResult.getHourOfDay());
    assertEquals(Short.SIZE, toMutableDateTimeISOResult2.getHourOfDay());
    assertEquals(Short.SIZE, toMutableDateTimeISOResult3.getHourOfDay());
    assertEquals(Short.SIZE, ((DateTime) actualValue).getHourOfDay());
    assertEquals(Short.SIZE, values[2]);
    assertArrayEquals(new int[]{-292275055, 5, Short.SIZE}, toYearMonthDayResult.getValues());
    assertArrayEquals(new int[]{-292275055, 5, Short.SIZE, 60424192}, toLocalDateTimeResult2.getValues());
    assertArrayEquals(new int[]{-292275055, 5, Short.SIZE, 60424192}, toLocalDateTimeResult.getValues());
    assertArrayEquals(new int[]{Short.SIZE, 47, 4, 192}, toTimeOfDayResult.getValues());
    assertArrayEquals(new int[]{Short.SIZE, 47, 4, 192}, toTimeOfDayResult2.getValues());
  }

  /**
   * Test {@link JodaDateTimeType#getValue(ValueFields)}.
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.</li>
   *   <li>Then Chronology return {@link ISOChronology}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JodaDateTimeType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_givenNull_thenChronologyReturnISOChronology() {
    // Arrange
    JodaDateTimeType jodaDateTimeType = new JodaDateTimeType();

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

    // Act
    Object actualValue = jodaDateTimeType.getValue(valueFields);

    // Assert
    assertTrue(actualValue instanceof DateTime);
    Chronology chronology = ((DateTime) actualValue).getChronology();
    assertTrue(chronology instanceof ISOChronology);
    org.joda.time.LocalDateTime toLocalDateTimeResult = ((DateTime) actualValue).toLocalDateTime();
    assertEquals(4, toLocalDateTimeResult.getFieldTypes().length);
    org.joda.time.Instant toInstantResult = ((DateTime) actualValue).toInstant();
    assertEquals(actualValue, toInstantResult.toDateTime());
    assertEquals(actualValue, toInstantResult.toDateTimeISO());
    assertEquals(actualValue, toInstantResult.toMutableDateTime());
    assertEquals(actualValue, toInstantResult.toMutableDateTimeISO());
    assertEquals(actualValue, toLocalDateTimeResult.toDateTime());
    assertEquals(actualValue, ((DateTime) actualValue).toMutableDateTime());
    assertEquals(actualValue, ((DateTime) actualValue).toMutableDateTimeISO());
    DateTime.Property eraResult = ((DateTime) actualValue).era();
    DateMidnight toDateMidnightResult = ((DateTime) actualValue).toDateMidnight();
    assertEquals(eraResult, toDateMidnightResult.era());
    org.joda.time.Instant toInstantResult2 = toDateMidnightResult.toInstant();
    assertEquals(toDateMidnightResult, toInstantResult2.toDateTime());
    assertEquals(toDateMidnightResult, toInstantResult2.toDateTimeISO());
    assertEquals(toDateMidnightResult, toInstantResult2.toMutableDateTime());
    assertEquals(toDateMidnightResult, toInstantResult2.toMutableDateTimeISO());
    assertEquals(toDateMidnightResult, toDateMidnightResult.toDateTime());
    assertEquals(toDateMidnightResult, toDateMidnightResult.toDateTimeISO());
    assertEquals(toDateMidnightResult, toDateMidnightResult.toMutableDateTime());
    assertEquals(toDateMidnightResult, toDateMidnightResult.toMutableDateTimeISO());
    DateTime.Property weekyearResult = ((DateTime) actualValue).weekyear();
    assertEquals(weekyearResult, toDateMidnightResult.weekyear());
    DateTime.Property yearResult = ((DateTime) actualValue).year();
    assertEquals(yearResult, toDateMidnightResult.year());
    assertSame(actualValue, ((DateTime) actualValue).toDateTime());
    assertSame(actualValue, ((DateTime) actualValue).toDateTimeISO());
    assertSame(actualValue, eraResult.getDateTime());
    assertSame(actualValue, weekyearResult.getDateTime());
    assertSame(actualValue, yearResult.getDateTime());
    assertSame(toLocalDateTimeResult, toLocalDateTimeResult.era().getLocalDateTime());
    assertSame(toLocalDateTimeResult, toLocalDateTimeResult.weekyear().getLocalDateTime());
    assertSame(toLocalDateTimeResult, toLocalDateTimeResult.year().getLocalDateTime());
    assertSame(toInstantResult2, toInstantResult2.toInstant());
    assertSame(toInstantResult, toInstantResult.toInstant());
    assertSame(chronology, toDateMidnightResult.getChronology());
  }

  /**
   * Test {@link JodaDateTimeType#getValue(ValueFields)}.
   * <ul>
   *   <li>Then return toDateTimeISO weekyear AsShortText is
   * {@code -292275055}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JodaDateTimeType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_thenReturnToDateTimeISOWeekyearAsShortTextIs292275055() {
    // Arrange
    JodaDateTimeType jodaDateTimeType = new JodaDateTimeType();
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getLongValue()).thenReturn(Long.MAX_VALUE);

    // Act
    Object actualValue = jodaDateTimeType.getValue(valueFields);

    // Assert
    verify(valueFields).getLongValue();
    assertTrue(actualValue instanceof DateTime);
    DateTime toDateTimeISOResult = ((DateTime) actualValue).toDateTimeISO();
    DateTime.Property weekyearResult = toDateTimeISOResult.weekyear();
    assertEquals("-292275055", weekyearResult.getAsShortText());
    DateTime.Property weekyearResult2 = ((DateTime) actualValue).weekyear();
    assertEquals("-292275055", weekyearResult2.getAsShortText());
    LocalDateTime toLocalDateTimeResult = ((DateTime) actualValue).toLocalDateTime();
    LocalDateTime.Property weekyearResult3 = toLocalDateTimeResult.weekyear();
    assertEquals("-292275055", weekyearResult3.getAsShortText());
    assertEquals("-292275055", weekyearResult.getAsString());
    assertEquals("-292275055", weekyearResult2.getAsString());
    assertEquals("-292275055", weekyearResult3.getAsString());
    assertEquals("-292275055", weekyearResult.getAsText());
    assertEquals("-292275055", weekyearResult2.getAsText());
    assertEquals("-292275055", weekyearResult3.getAsText());
    DateTime toDateTimeISOResult2 = toDateTimeISOResult.toDateTimeISO();
    assertEquals("07:12:55.807", toDateTimeISOResult2.toLocalTime().toString());
    assertEquals("07:12:55.807", toDateTimeISOResult.toLocalTime().toString());
    Instant toInstantResult = ((DateTime) actualValue).toInstant();
    DateTime toDateTimeResult = toInstantResult.toDateTime();
    assertEquals("07:12:55.807", toDateTimeResult.toLocalTime().toString());
    DateTime toDateTimeISOResult3 = toInstantResult.toDateTimeISO();
    assertEquals("07:12:55.807", toDateTimeISOResult3.toLocalTime().toString());
    DateTime toDateTimeResult2 = toLocalDateTimeResult.toDateTime();
    assertEquals("07:12:55.807", toDateTimeResult2.toLocalTime().toString());
    assertEquals("07:12:55.807", ((DateTime) actualValue).toLocalTime().toString());
    LocalDateTime toLocalDateTimeResult2 = toDateTimeISOResult.toLocalDateTime();
    assertEquals("07:12:55.807", toLocalDateTimeResult2.toLocalTime().toString());
    assertEquals("07:12:55.807", toLocalDateTimeResult.toLocalTime().toString());
    TimeOfDay toTimeOfDayResult = toDateTimeISOResult.toTimeOfDay();
    assertEquals("07:12:55.807", toTimeOfDayResult.toLocalTime().toString());
    TimeOfDay toTimeOfDayResult2 = ((DateTime) actualValue).toTimeOfDay();
    assertEquals("07:12:55.807", toTimeOfDayResult2.toLocalTime().toString());
    DateTime.Property eraResult = toDateTimeISOResult.era();
    assertEquals("1", eraResult.getAsString());
    DateTime.Property yearResult = toDateTimeISOResult.year();
    assertEquals("292278994", yearResult.getAsShortText());
    DateTime.Property yearResult2 = ((DateTime) actualValue).year();
    assertEquals("292278994", yearResult2.getAsShortText());
    LocalDateTime.Property yearResult3 = toLocalDateTimeResult.year();
    assertEquals("292278994", yearResult3.getAsShortText());
    assertEquals("292278994", yearResult.getAsString());
    assertEquals("292278994", yearResult2.getAsString());
    assertEquals("292278994", yearResult3.getAsString());
    assertEquals("292278994", yearResult.getAsText());
    assertEquals("292278994", yearResult2.getAsText());
    assertEquals("292278994", yearResult3.getAsText());
    DateMidnight toDateMidnightResult = toDateTimeISOResult.toDateMidnight();
    assertEquals("292278994-08-17", toDateMidnightResult.toLocalDate().toString());
    DateMidnight toDateMidnightResult2 = ((DateTime) actualValue).toDateMidnight();
    assertEquals("292278994-08-17", toDateMidnightResult2.toLocalDate().toString());
    assertEquals("292278994-08-17", toDateTimeISOResult2.toLocalDate().toString());
    assertEquals("292278994-08-17", toDateTimeISOResult.toLocalDate().toString());
    assertEquals("292278994-08-17", toDateTimeResult.toLocalDate().toString());
    assertEquals("292278994-08-17", toDateTimeISOResult3.toLocalDate().toString());
    assertEquals("292278994-08-17", toDateTimeResult2.toLocalDate().toString());
    DateTime toDateTimeResult3 = toDateMidnightResult2.toDateTime();
    assertEquals("292278994-08-17", toDateTimeResult3.toLocalDate().toString());
    DateTime toDateTimeISOResult4 = toDateMidnightResult2.toDateTimeISO();
    assertEquals("292278994-08-17", toDateTimeISOResult4.toLocalDate().toString());
    org.joda.time.LocalDate toLocalDateResult = ((DateTime) actualValue).toLocalDate();
    assertEquals("292278994-08-17", toLocalDateResult.toString());
    assertEquals("292278994-08-17", toLocalDateTimeResult2.toLocalDate().toString());
    assertEquals("292278994-08-17", toLocalDateTimeResult.toLocalDate().toString());
    YearMonthDay toYearMonthDayResult = toDateMidnightResult2.toYearMonthDay();
    assertEquals("292278994-08-17", toYearMonthDayResult.toLocalDate().toString());
    YearMonthDay toYearMonthDayResult2 = ((DateTime) actualValue).toYearMonthDay();
    assertEquals("292278994-08-17", toYearMonthDayResult2.toLocalDate().toString());
    assertEquals("AD", eraResult.getAsShortText());
    assertEquals("AD", eraResult.getAsText());
    assertEquals(-292275055, toLocalDateTimeResult2.getWeekyear());
    assertEquals(-292275055, toLocalDateTimeResult.getWeekyear());
    assertEquals(-292275055, toDateTimeISOResult2.getWeekyear());
    assertEquals(-292275055, toDateTimeISOResult.getWeekyear());
    assertEquals(-292275055, toDateTimeResult.getWeekyear());
    assertEquals(-292275055, toDateTimeISOResult3.getWeekyear());
    MutableDateTime toMutableDateTimeResult = toInstantResult.toMutableDateTime();
    assertEquals(-292275055, toMutableDateTimeResult.getWeekyear());
    MutableDateTime toMutableDateTimeISOResult = toInstantResult.toMutableDateTimeISO();
    assertEquals(-292275055, toMutableDateTimeISOResult.getWeekyear());
    assertEquals(-292275055, toDateTimeResult2.getWeekyear());
    MutableDateTime toMutableDateTimeResult2 = toDateTimeISOResult.toMutableDateTime();
    assertEquals(-292275055, toMutableDateTimeResult2.getWeekyear());
    MutableDateTime toMutableDateTimeResult3 = ((DateTime) actualValue).toMutableDateTime();
    assertEquals(-292275055, toMutableDateTimeResult3.getWeekyear());
    MutableDateTime toMutableDateTimeISOResult2 = toDateTimeISOResult.toMutableDateTimeISO();
    assertEquals(-292275055, toMutableDateTimeISOResult2.getWeekyear());
    MutableDateTime toMutableDateTimeISOResult3 = ((DateTime) actualValue).toMutableDateTimeISO();
    assertEquals(-292275055, toMutableDateTimeISOResult3.getWeekyear());
    assertEquals(-292275055, ((DateTime) actualValue).getWeekyear());
    assertEquals(-292275055, weekyearResult.get());
    assertEquals(-292275055, weekyearResult2.get());
    assertEquals(-292275055, weekyearResult3.get());
    assertEquals(-9223309901257975809L, eraResult.remainder());
    DateTime.Property eraResult2 = ((DateTime) actualValue).era();
    assertEquals(-9223309901257975809L, eraResult2.remainder());
    assertEquals(-9223309901257975809L, toLocalDateTimeResult.era().remainder());
    assertEquals(-9223309901283951616L, toDateMidnightResult2.era().remainder());
    assertEquals(0, toDateMidnightResult.getHourOfDay());
    assertEquals(0, toDateMidnightResult.getMillisOfDay());
    assertEquals(0, toDateMidnightResult.getMillisOfSecond());
    assertEquals(0, toDateMidnightResult.getMinuteOfDay());
    assertEquals(0, toDateMidnightResult.getMinuteOfHour());
    assertEquals(0, toDateMidnightResult.getSecondOfDay());
    assertEquals(0, toDateMidnightResult.getSecondOfMinute());
    assertEquals(1, toLocalDateTimeResult2.getEra());
    assertEquals(1, toLocalDateTimeResult2.getWeekOfWeekyear());
    assertEquals(1, toDateTimeISOResult2.getEra());
    assertEquals(1, toDateTimeISOResult.getEra());
    assertEquals(1, toMutableDateTimeResult2.getEra());
    assertEquals(1, toMutableDateTimeISOResult2.getEra());
    assertEquals(1, toDateTimeISOResult2.getWeekOfWeekyear());
    assertEquals(1, toDateTimeISOResult.getWeekOfWeekyear());
    assertEquals(1, toMutableDateTimeResult2.getWeekOfWeekyear());
    assertEquals(1, toMutableDateTimeISOResult2.getWeekOfWeekyear());
    assertEquals(1, eraResult.get());
    assertEquals(12, toLocalDateTimeResult2.getMinuteOfHour());
    assertEquals(12, toLocalDateTimeResult.getMinuteOfHour());
    assertEquals(12, toTimeOfDayResult.getMinuteOfHour());
    assertEquals(12, toTimeOfDayResult2.getMinuteOfHour());
    assertEquals(12, toDateTimeISOResult2.getMinuteOfHour());
    assertEquals(12, toDateTimeISOResult.getMinuteOfHour());
    assertEquals(12, toDateTimeResult.getMinuteOfHour());
    assertEquals(12, toDateTimeISOResult3.getMinuteOfHour());
    assertEquals(12, toMutableDateTimeResult.getMinuteOfHour());
    assertEquals(12, toMutableDateTimeISOResult.getMinuteOfHour());
    assertEquals(12, toDateTimeResult2.getMinuteOfHour());
    assertEquals(12, toMutableDateTimeResult2.getMinuteOfHour());
    assertEquals(12, toMutableDateTimeResult3.getMinuteOfHour());
    assertEquals(12, toMutableDateTimeISOResult2.getMinuteOfHour());
    assertEquals(12, toMutableDateTimeISOResult3.getMinuteOfHour());
    assertEquals(12, ((DateTime) actualValue).getMinuteOfHour());
    assertEquals(17, toLocalDateTimeResult2.getDayOfMonth());
    assertEquals(17, toLocalDateTimeResult.getDayOfMonth());
    assertEquals(17, toYearMonthDayResult.getDayOfMonth());
    assertEquals(17, toYearMonthDayResult2.getDayOfMonth());
    assertEquals(17, toDateMidnightResult.getDayOfMonth());
    assertEquals(17, toDateMidnightResult2.getDayOfMonth());
    assertEquals(17, toDateTimeISOResult2.getDayOfMonth());
    assertEquals(17, toDateTimeISOResult.getDayOfMonth());
    assertEquals(17, toDateTimeResult.getDayOfMonth());
    assertEquals(17, toDateTimeISOResult3.getDayOfMonth());
    assertEquals(17, toMutableDateTimeResult.getDayOfMonth());
    assertEquals(17, toMutableDateTimeISOResult.getDayOfMonth());
    assertEquals(17, toDateTimeResult2.getDayOfMonth());
    assertEquals(17, toDateTimeResult3.getDayOfMonth());
    assertEquals(17, toDateTimeISOResult4.getDayOfMonth());
    MutableDateTime toMutableDateTimeResult4 = toDateMidnightResult2.toMutableDateTime();
    assertEquals(17, toMutableDateTimeResult4.getDayOfMonth());
    assertEquals(17, toMutableDateTimeResult2.getDayOfMonth());
    assertEquals(17, toMutableDateTimeResult3.getDayOfMonth());
    MutableDateTime toMutableDateTimeISOResult4 = toDateMidnightResult2.toMutableDateTimeISO();
    assertEquals(17, toMutableDateTimeISOResult4.getDayOfMonth());
    assertEquals(17, toMutableDateTimeISOResult2.getDayOfMonth());
    assertEquals(17, toMutableDateTimeISOResult3.getDayOfMonth());
    assertEquals(17, ((DateTime) actualValue).getDayOfMonth());
    int[] values = toYearMonthDayResult.getValues();
    assertEquals(17, values[2]);
    assertEquals(19699200000L, toDateMidnightResult2.year().remainder());
    assertEquals(19725175807L, yearResult.remainder());
    assertEquals(19725175807L, yearResult2.remainder());
    assertEquals(19725175807L, yearResult3.remainder());
    assertEquals(229, toLocalDateTimeResult2.getDayOfYear());
    assertEquals(229, toLocalDateTimeResult.getDayOfYear());
    assertEquals(229, toDateMidnightResult.getDayOfYear());
    assertEquals(229, toDateMidnightResult2.getDayOfYear());
    assertEquals(229, toDateTimeISOResult2.getDayOfYear());
    assertEquals(229, toDateTimeISOResult.getDayOfYear());
    assertEquals(229, toDateTimeResult.getDayOfYear());
    assertEquals(229, toDateTimeISOResult3.getDayOfYear());
    assertEquals(229, toMutableDateTimeResult.getDayOfYear());
    assertEquals(229, toMutableDateTimeISOResult.getDayOfYear());
    assertEquals(229, toDateTimeResult2.getDayOfYear());
    assertEquals(229, toDateTimeResult3.getDayOfYear());
    assertEquals(229, toDateTimeISOResult4.getDayOfYear());
    assertEquals(229, toMutableDateTimeResult4.getDayOfYear());
    assertEquals(229, toMutableDateTimeResult2.getDayOfYear());
    assertEquals(229, toMutableDateTimeResult3.getDayOfYear());
    assertEquals(229, toMutableDateTimeISOResult4.getDayOfYear());
    assertEquals(229, toMutableDateTimeISOResult2.getDayOfYear());
    assertEquals(229, toMutableDateTimeISOResult3.getDayOfYear());
    assertEquals(229, ((DateTime) actualValue).getDayOfYear());
    assertEquals(25975, toDateTimeISOResult2.getSecondOfDay());
    assertEquals(25975, toDateTimeISOResult.getSecondOfDay());
    assertEquals(25975, toDateTimeResult.getSecondOfDay());
    assertEquals(25975, toDateTimeISOResult3.getSecondOfDay());
    assertEquals(25975, toMutableDateTimeResult.getSecondOfDay());
    assertEquals(25975, toMutableDateTimeISOResult.getSecondOfDay());
    assertEquals(25975, toDateTimeResult2.getSecondOfDay());
    assertEquals(25975, toMutableDateTimeResult2.getSecondOfDay());
    assertEquals(25975, toMutableDateTimeResult3.getSecondOfDay());
    assertEquals(25975, toMutableDateTimeISOResult2.getSecondOfDay());
    assertEquals(25975, toMutableDateTimeISOResult3.getSecondOfDay());
    assertEquals(25975, ((DateTime) actualValue).getSecondOfDay());
    assertEquals(25975807, toLocalDateTimeResult2.getMillisOfDay());
    assertEquals(25975807, toLocalDateTimeResult.getMillisOfDay());
    assertEquals(25975807, toDateTimeISOResult2.getMillisOfDay());
    assertEquals(25975807, toDateTimeISOResult.getMillisOfDay());
    assertEquals(25975807, toDateTimeResult.getMillisOfDay());
    assertEquals(25975807, toDateTimeISOResult3.getMillisOfDay());
    assertEquals(25975807, toMutableDateTimeResult.getMillisOfDay());
    assertEquals(25975807, toMutableDateTimeISOResult.getMillisOfDay());
    assertEquals(25975807, toDateTimeResult2.getMillisOfDay());
    assertEquals(25975807, toMutableDateTimeResult2.getMillisOfDay());
    assertEquals(25975807, toMutableDateTimeResult3.getMillisOfDay());
    assertEquals(25975807, toMutableDateTimeISOResult2.getMillisOfDay());
    assertEquals(25975807, toMutableDateTimeISOResult3.getMillisOfDay());
    assertEquals(25975807, ((DateTime) actualValue).getMillisOfDay());
    assertEquals(2922789, toLocalDateTimeResult2.getCenturyOfEra());
    assertEquals(2922789, toLocalDateTimeResult.getCenturyOfEra());
    assertEquals(2922789, toDateTimeISOResult2.getCenturyOfEra());
    assertEquals(2922789, toDateTimeISOResult.getCenturyOfEra());
    assertEquals(2922789, toDateTimeResult.getCenturyOfEra());
    assertEquals(2922789, toDateTimeISOResult3.getCenturyOfEra());
    assertEquals(2922789, toMutableDateTimeResult.getCenturyOfEra());
    assertEquals(2922789, toMutableDateTimeISOResult.getCenturyOfEra());
    assertEquals(2922789, toDateTimeResult2.getCenturyOfEra());
    assertEquals(2922789, toMutableDateTimeResult2.getCenturyOfEra());
    assertEquals(2922789, toMutableDateTimeResult3.getCenturyOfEra());
    assertEquals(2922789, toMutableDateTimeISOResult2.getCenturyOfEra());
    assertEquals(2922789, toMutableDateTimeISOResult3.getCenturyOfEra());
    assertEquals(2922789, ((DateTime) actualValue).getCenturyOfEra());
    assertEquals(292278994, toLocalDateTimeResult2.getYear());
    assertEquals(292278994, toLocalDateTimeResult.getYear());
    assertEquals(292278994, toLocalDateTimeResult2.getYearOfEra());
    assertEquals(292278994, toLocalDateTimeResult.getYearOfEra());
    assertEquals(292278994, toYearMonthDayResult2.getYear());
    assertEquals(292278994, toDateTimeISOResult2.getYear());
    assertEquals(292278994, toDateTimeISOResult.getYear());
    assertEquals(292278994, toDateTimeResult.getYear());
    assertEquals(292278994, toDateTimeISOResult3.getYear());
    assertEquals(292278994, toMutableDateTimeResult.getYear());
    assertEquals(292278994, toMutableDateTimeISOResult.getYear());
    assertEquals(292278994, toDateTimeResult2.getYear());
    assertEquals(292278994, toMutableDateTimeResult2.getYear());
    assertEquals(292278994, toMutableDateTimeResult3.getYear());
    assertEquals(292278994, toMutableDateTimeISOResult2.getYear());
    assertEquals(292278994, toMutableDateTimeISOResult3.getYear());
    assertEquals(292278994, ((DateTime) actualValue).getYear());
    assertEquals(292278994, toDateTimeISOResult2.getYearOfEra());
    assertEquals(292278994, toDateTimeISOResult.getYearOfEra());
    assertEquals(292278994, toDateTimeResult.getYearOfEra());
    assertEquals(292278994, toDateTimeISOResult3.getYearOfEra());
    assertEquals(292278994, toMutableDateTimeResult.getYearOfEra());
    assertEquals(292278994, toMutableDateTimeISOResult.getYearOfEra());
    assertEquals(292278994, toDateTimeResult2.getYearOfEra());
    assertEquals(292278994, toMutableDateTimeResult2.getYearOfEra());
    assertEquals(292278994, toMutableDateTimeResult3.getYearOfEra());
    assertEquals(292278994, toMutableDateTimeISOResult2.getYearOfEra());
    assertEquals(292278994, toMutableDateTimeISOResult3.getYearOfEra());
    assertEquals(292278994, ((DateTime) actualValue).getYearOfEra());
    assertEquals(292278994, yearResult.get());
    assertEquals(292278994, yearResult2.get());
    assertEquals(292278994, yearResult3.get());
    assertEquals(3, values.length);
    assertEquals(432, toDateTimeISOResult2.getMinuteOfDay());
    assertEquals(432, toDateTimeISOResult.getMinuteOfDay());
    assertEquals(432, toDateTimeResult.getMinuteOfDay());
    assertEquals(432, toDateTimeISOResult3.getMinuteOfDay());
    assertEquals(432, toMutableDateTimeResult.getMinuteOfDay());
    assertEquals(432, toMutableDateTimeISOResult.getMinuteOfDay());
    assertEquals(432, toDateTimeResult2.getMinuteOfDay());
    assertEquals(432, toMutableDateTimeResult2.getMinuteOfDay());
    assertEquals(432, toMutableDateTimeResult3.getMinuteOfDay());
    assertEquals(432, toMutableDateTimeISOResult2.getMinuteOfDay());
    assertEquals(432, toMutableDateTimeISOResult3.getMinuteOfDay());
    assertEquals(432, ((DateTime) actualValue).getMinuteOfDay());
    assertEquals(55, toLocalDateTimeResult2.getSecondOfMinute());
    assertEquals(55, toLocalDateTimeResult.getSecondOfMinute());
    assertEquals(55, toTimeOfDayResult.getSecondOfMinute());
    assertEquals(55, toTimeOfDayResult2.getSecondOfMinute());
    assertEquals(55, toDateTimeISOResult2.getSecondOfMinute());
    assertEquals(55, toDateTimeISOResult.getSecondOfMinute());
    assertEquals(55, toDateTimeResult.getSecondOfMinute());
    assertEquals(55, toDateTimeISOResult3.getSecondOfMinute());
    assertEquals(55, toMutableDateTimeResult.getSecondOfMinute());
    assertEquals(55, toMutableDateTimeISOResult.getSecondOfMinute());
    assertEquals(55, toDateTimeResult2.getSecondOfMinute());
    assertEquals(55, toMutableDateTimeResult2.getSecondOfMinute());
    assertEquals(55, toMutableDateTimeResult3.getSecondOfMinute());
    assertEquals(55, toMutableDateTimeISOResult2.getSecondOfMinute());
    assertEquals(55, toMutableDateTimeISOResult3.getSecondOfMinute());
    assertEquals(55, ((DateTime) actualValue).getSecondOfMinute());
    assertEquals(552848384L, toDateMidnightResult2.weekyear().remainder());
    assertEquals(578824191L, weekyearResult.remainder());
    assertEquals(578824191L, weekyearResult2.remainder());
    assertEquals(578824191L, weekyearResult3.remainder());
    assertEquals(7, toLocalDateTimeResult2.getDayOfWeek());
    assertEquals(7, toLocalDateTimeResult.getDayOfWeek());
    assertEquals(7, toLocalDateTimeResult2.getHourOfDay());
    assertEquals(7, toLocalDateTimeResult.getHourOfDay());
    assertEquals(7, toTimeOfDayResult.getHourOfDay());
    assertEquals(7, toTimeOfDayResult2.getHourOfDay());
    assertEquals(7, toDateMidnightResult.getDayOfWeek());
    assertEquals(7, toDateMidnightResult2.getDayOfWeek());
    assertEquals(7, toDateTimeISOResult2.getDayOfWeek());
    assertEquals(7, toDateTimeISOResult.getDayOfWeek());
    assertEquals(7, toDateTimeResult.getDayOfWeek());
    assertEquals(7, toDateTimeISOResult3.getDayOfWeek());
    assertEquals(7, toMutableDateTimeResult.getDayOfWeek());
    assertEquals(7, toMutableDateTimeISOResult.getDayOfWeek());
    assertEquals(7, toDateTimeResult2.getDayOfWeek());
    assertEquals(7, toDateTimeResult3.getDayOfWeek());
    assertEquals(7, toDateTimeISOResult4.getDayOfWeek());
    assertEquals(7, toMutableDateTimeResult4.getDayOfWeek());
    assertEquals(7, toMutableDateTimeResult2.getDayOfWeek());
    assertEquals(7, toMutableDateTimeResult3.getDayOfWeek());
    assertEquals(7, toMutableDateTimeISOResult4.getDayOfWeek());
    assertEquals(7, toMutableDateTimeISOResult2.getDayOfWeek());
    assertEquals(7, toMutableDateTimeISOResult3.getDayOfWeek());
    assertEquals(7, ((DateTime) actualValue).getDayOfWeek());
    assertEquals(7, toDateTimeISOResult2.getHourOfDay());
    assertEquals(7, toDateTimeISOResult.getHourOfDay());
    assertEquals(7, toDateTimeResult.getHourOfDay());
    assertEquals(7, toDateTimeISOResult3.getHourOfDay());
    assertEquals(7, toMutableDateTimeResult.getHourOfDay());
    assertEquals(7, toMutableDateTimeISOResult.getHourOfDay());
    assertEquals(7, toDateTimeResult2.getHourOfDay());
    assertEquals(7, toMutableDateTimeResult2.getHourOfDay());
    assertEquals(7, toMutableDateTimeResult3.getHourOfDay());
    assertEquals(7, toMutableDateTimeISOResult2.getHourOfDay());
    assertEquals(7, toMutableDateTimeISOResult3.getHourOfDay());
    assertEquals(7, ((DateTime) actualValue).getHourOfDay());
    assertEquals(8, toLocalDateTimeResult2.getMonthOfYear());
    assertEquals(8, toLocalDateTimeResult.getMonthOfYear());
    assertEquals(8, toYearMonthDayResult2.getMonthOfYear());
    assertEquals(8, toDateTimeISOResult2.getMonthOfYear());
    assertEquals(8, toDateTimeISOResult.getMonthOfYear());
    assertEquals(8, toDateTimeResult.getMonthOfYear());
    assertEquals(8, toDateTimeISOResult3.getMonthOfYear());
    assertEquals(8, toMutableDateTimeResult.getMonthOfYear());
    assertEquals(8, toMutableDateTimeISOResult.getMonthOfYear());
    assertEquals(8, toDateTimeResult2.getMonthOfYear());
    assertEquals(8, toMutableDateTimeResult2.getMonthOfYear());
    assertEquals(8, toMutableDateTimeResult3.getMonthOfYear());
    assertEquals(8, toMutableDateTimeISOResult2.getMonthOfYear());
    assertEquals(8, toMutableDateTimeISOResult3.getMonthOfYear());
    assertEquals(8, ((DateTime) actualValue).getMonthOfYear());
    assertEquals(807, toLocalDateTimeResult2.getMillisOfSecond());
    assertEquals(807, toLocalDateTimeResult.getMillisOfSecond());
    assertEquals(807, toTimeOfDayResult.getMillisOfSecond());
    assertEquals(807, toTimeOfDayResult2.getMillisOfSecond());
    assertEquals(807, toDateTimeISOResult2.getMillisOfSecond());
    assertEquals(807, toDateTimeISOResult.getMillisOfSecond());
    assertEquals(807, toDateTimeResult.getMillisOfSecond());
    assertEquals(807, toDateTimeISOResult3.getMillisOfSecond());
    assertEquals(807, toMutableDateTimeResult.getMillisOfSecond());
    assertEquals(807, toMutableDateTimeISOResult.getMillisOfSecond());
    assertEquals(807, toDateTimeResult2.getMillisOfSecond());
    assertEquals(807, toMutableDateTimeResult2.getMillisOfSecond());
    assertEquals(807, toMutableDateTimeResult3.getMillisOfSecond());
    assertEquals(807, toMutableDateTimeISOResult2.getMillisOfSecond());
    assertEquals(807, toMutableDateTimeISOResult3.getMillisOfSecond());
    assertEquals(807, ((DateTime) actualValue).getMillisOfSecond());
    assertEquals(9223372036828800000L, toDateMidnightResult2.toInstant().getMillis());
    assertEquals(9223372036828800000L, toDateMidnightResult.getMillis());
    assertEquals(9223372036828800000L, toDateMidnightResult2.getMillis());
    assertEquals(9223372036828800000L, toDateTimeResult3.getMillis());
    assertEquals(9223372036828800000L, toDateTimeISOResult4.getMillis());
    assertEquals(9223372036828800000L, toMutableDateTimeResult4.getMillis());
    assertEquals(9223372036828800000L, toMutableDateTimeISOResult4.getMillis());
    assertEquals(94, toLocalDateTimeResult2.getYearOfCentury());
    assertEquals(94, toLocalDateTimeResult.getYearOfCentury());
    assertEquals(94, toDateTimeISOResult2.getYearOfCentury());
    assertEquals(94, toDateTimeISOResult.getYearOfCentury());
    assertEquals(94, toDateTimeResult.getYearOfCentury());
    assertEquals(94, toDateTimeISOResult3.getYearOfCentury());
    assertEquals(94, toMutableDateTimeResult.getYearOfCentury());
    assertEquals(94, toMutableDateTimeISOResult.getYearOfCentury());
    assertEquals(94, toDateTimeResult2.getYearOfCentury());
    assertEquals(94, toMutableDateTimeResult2.getYearOfCentury());
    assertEquals(94, toMutableDateTimeResult3.getYearOfCentury());
    assertEquals(94, toMutableDateTimeISOResult2.getYearOfCentury());
    assertEquals(94, toMutableDateTimeISOResult3.getYearOfCentury());
    assertEquals(94, ((DateTime) actualValue).getYearOfCentury());
    assertEquals(eraResult2, toDateMidnightResult.era());
    assertEquals(toLocalDateResult, toDateTimeISOResult2.toYearMonthDay());
    assertEquals(toLocalDateResult, toDateTimeISOResult.toYearMonthDay());
    assertEquals(weekyearResult2, toDateMidnightResult.weekyear());
    assertEquals(yearResult2, toDateMidnightResult.year());
    assertEquals(Long.MAX_VALUE, toDateTimeISOResult.toInstant().getMillis());
    assertEquals(Long.MAX_VALUE, toInstantResult.getMillis());
    assertEquals(Long.MAX_VALUE, toDateTimeISOResult2.getMillis());
    assertEquals(Long.MAX_VALUE, toDateTimeISOResult.getMillis());
    assertEquals(Long.MAX_VALUE, toDateTimeResult.getMillis());
    assertEquals(Long.MAX_VALUE, toDateTimeISOResult3.getMillis());
    assertEquals(Long.MAX_VALUE, toMutableDateTimeResult.getMillis());
    assertEquals(Long.MAX_VALUE, toMutableDateTimeISOResult.getMillis());
    assertEquals(Long.MAX_VALUE, toDateTimeResult2.getMillis());
    assertEquals(Long.MAX_VALUE, toMutableDateTimeResult2.getMillis());
    assertEquals(Long.MAX_VALUE, toMutableDateTimeResult3.getMillis());
    assertEquals(Long.MAX_VALUE, toMutableDateTimeISOResult2.getMillis());
    assertEquals(Long.MAX_VALUE, toMutableDateTimeISOResult3.getMillis());
    assertEquals(Long.MAX_VALUE, ((DateTime) actualValue).getMillis());
    assertArrayEquals(new int[]{292278994, 8, 17}, toYearMonthDayResult2.getValues());
    assertArrayEquals(new int[]{292278994, 8, 17, 25975807}, toLocalDateTimeResult2.getValues());
    assertArrayEquals(new int[]{292278994, 8, 17, 25975807}, toLocalDateTimeResult.getValues());
    assertArrayEquals(new int[]{7, 12, 55, 807}, toTimeOfDayResult.getValues());
    assertArrayEquals(new int[]{7, 12, 55, 807}, toTimeOfDayResult2.getValues());
  }

  /**
   * Test {@link JodaDateTimeType#getValue(ValueFields)}.
   * <ul>
   *   <li>Then return toMutableDateTime toDateTime toDateMidnight is
   * toDateMidnight.</li>
   * </ul>
   * <p>
   * Method under test: {@link JodaDateTimeType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_thenReturnToMutableDateTimeToDateTimeToDateMidnightIsToDateMidnight() {
    // Arrange
    JodaDateTimeType jodaDateTimeType = new JodaDateTimeType();
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getLongValue()).thenReturn(42L);

    // Act
    Object actualValue = jodaDateTimeType.getValue(valueFields);

    // Assert
    verify(valueFields).getLongValue();
    assertTrue(actualValue instanceof DateTime);
    DateMidnight toDateMidnightResult = ((DateTime) actualValue).toDateMidnight();
    YearMonthDay toYearMonthDayResult = toDateMidnightResult.toYearMonthDay();
    assertEquals(3, toYearMonthDayResult.getFields().length);
    YearMonthDay toYearMonthDayResult2 = ((DateTime) actualValue).toYearMonthDay();
    assertEquals(3, toYearMonthDayResult2.getFields().length);
    MutableDateTime toMutableDateTimeResult = ((DateTime) actualValue).toMutableDateTime();
    MutableDateTime toMutableDateTimeResult2 = toMutableDateTimeResult.toMutableDateTime();
    assertEquals(actualValue, toMutableDateTimeResult2.toDateTime());
    MutableDateTime toMutableDateTimeISOResult = toMutableDateTimeResult.toMutableDateTimeISO();
    assertEquals(actualValue, toMutableDateTimeISOResult.toDateTime());
    assertEquals(actualValue, toMutableDateTimeResult2.toDateTimeISO());
    assertEquals(actualValue, toMutableDateTimeISOResult.toDateTimeISO());
    DateTime toDateTimeResult = toMutableDateTimeResult.toDateTime();
    assertEquals(actualValue, toDateTimeResult.toMutableDateTime());
    MutableDateTime toMutableDateTimeISOResult2 = ((DateTime) actualValue).toMutableDateTimeISO();
    DateTime toDateTimeResult2 = toMutableDateTimeISOResult2.toDateTime();
    assertEquals(actualValue, toDateTimeResult2.toMutableDateTime());
    DateTime toDateTimeISOResult = toMutableDateTimeResult.toDateTimeISO();
    assertEquals(actualValue, toDateTimeISOResult.toMutableDateTime());
    DateTime toDateTimeISOResult2 = toMutableDateTimeISOResult2.toDateTimeISO();
    assertEquals(actualValue, toDateTimeISOResult2.toMutableDateTime());
    assertEquals(actualValue, toMutableDateTimeResult2.toMutableDateTime());
    assertEquals(actualValue, toMutableDateTimeISOResult.toMutableDateTime());
    assertEquals(actualValue, toDateTimeResult.toMutableDateTimeISO());
    assertEquals(actualValue, toDateTimeResult2.toMutableDateTimeISO());
    assertEquals(actualValue, toDateTimeISOResult.toMutableDateTimeISO());
    assertEquals(actualValue, toDateTimeISOResult2.toMutableDateTimeISO());
    assertEquals(actualValue, toMutableDateTimeResult2.toMutableDateTimeISO());
    assertEquals(actualValue, toMutableDateTimeISOResult.toMutableDateTimeISO());
    assertEquals(toDateMidnightResult, toDateTimeResult.toDateMidnight());
    assertEquals(toDateMidnightResult, toDateTimeResult2.toDateMidnight());
    assertEquals(toDateMidnightResult, toDateTimeISOResult.toDateMidnight());
    assertEquals(toDateMidnightResult, toDateTimeISOResult2.toDateMidnight());
    Instant toInstantResult = toDateMidnightResult.toInstant();
    assertEquals(toDateMidnightResult, toInstantResult.toDateTime());
    assertEquals(toDateMidnightResult, toInstantResult.toDateTimeISO());
    assertEquals(toDateMidnightResult, toInstantResult.toMutableDateTime());
    assertEquals(toDateMidnightResult, toInstantResult.toMutableDateTimeISO());
    assertEquals(toDateMidnightResult, toYearMonthDayResult.toDateMidnight());
    assertEquals(toDateMidnightResult, toYearMonthDayResult2.toDateMidnight());
    assertEquals(toDateMidnightResult, toYearMonthDayResult.toDateTimeAtMidnight());
    assertEquals(toDateMidnightResult, toYearMonthDayResult2.toDateTimeAtMidnight());
    LocalDateTime toLocalDateTimeResult = ((DateTime) actualValue).toLocalDateTime();
    assertEquals(toLocalDateTimeResult, toDateTimeResult.toLocalDateTime());
    assertEquals(toLocalDateTimeResult, toDateTimeResult2.toLocalDateTime());
    assertEquals(toLocalDateTimeResult, toDateTimeISOResult.toLocalDateTime());
    assertEquals(toLocalDateTimeResult, toDateTimeISOResult2.toLocalDateTime());
    DateTime toDateTimeResult3 = toDateMidnightResult.toDateTime();
    assertEquals(toInstantResult, toDateTimeResult3.toInstant());
    DateTime toDateTimeISOResult3 = toDateMidnightResult.toDateTimeISO();
    assertEquals(toInstantResult, toDateTimeISOResult3.toInstant());
    assertEquals(toInstantResult, toDateMidnightResult.toMutableDateTime().toInstant());
    assertEquals(toInstantResult, toDateMidnightResult.toMutableDateTimeISO().toInstant());
    Instant toInstantResult2 = ((DateTime) actualValue).toInstant();
    DateTime toDateTimeResult4 = toInstantResult2.toDateTime();
    assertEquals(toInstantResult2, toDateTimeResult4.toInstant());
    DateTime toDateTimeISOResult4 = toInstantResult2.toDateTimeISO();
    assertEquals(toInstantResult2, toDateTimeISOResult4.toInstant());
    assertEquals(toInstantResult2, toInstantResult2.toMutableDateTime().toInstant());
    assertEquals(toInstantResult2, toInstantResult2.toMutableDateTimeISO().toInstant());
    DateTime toDateTimeResult5 = toLocalDateTimeResult.toDateTime();
    assertEquals(toInstantResult2, toDateTimeResult5.toInstant());
    assertEquals(toInstantResult2, toDateTimeResult.toInstant());
    assertEquals(toInstantResult2, toDateTimeResult2.toInstant());
    assertEquals(toInstantResult2, toDateTimeISOResult.toInstant());
    assertEquals(toInstantResult2, toDateTimeISOResult2.toInstant());
    assertEquals(toInstantResult2, toMutableDateTimeResult2.toInstant());
    assertEquals(toInstantResult2, toMutableDateTimeResult.toInstant());
    assertEquals(toInstantResult2, toMutableDateTimeISOResult.toInstant());
    assertSame(actualValue, ((DateTime) actualValue).toDateTimeISO());
    assertSame(toDateTimeResult4, toDateTimeResult4.toDateTimeISO());
    assertSame(toDateTimeISOResult4, toDateTimeISOResult4.toDateTimeISO());
    assertSame(toDateTimeResult5, toDateTimeResult5.toDateTimeISO());
    assertSame(toDateTimeResult3, toDateTimeResult3.toDateTimeISO());
    assertSame(toDateTimeResult, toDateTimeResult.toDateTime());
    assertSame(toDateTimeResult, toDateTimeResult.toDateTimeISO());
    assertSame(toDateTimeResult2, toDateTimeResult2.toDateTime());
    assertSame(toDateTimeResult2, toDateTimeResult2.toDateTimeISO());
    assertSame(toDateTimeISOResult3, toDateTimeISOResult3.toDateTimeISO());
    assertSame(toDateTimeISOResult, toDateTimeISOResult.toDateTime());
    assertSame(toDateTimeISOResult, toDateTimeISOResult.toDateTimeISO());
    assertSame(toDateTimeISOResult2, toDateTimeISOResult2.toDateTime());
    assertSame(toDateTimeISOResult2, toDateTimeISOResult2.toDateTimeISO());
    assertSame(toMutableDateTimeResult, toMutableDateTimeResult.era().getMutableDateTime());
    assertSame(toMutableDateTimeISOResult2, toMutableDateTimeISOResult2.era().getMutableDateTime());
  }

  /**
   * Test {@link JodaDateTimeType#getValue(ValueFields)}.
   * <ul>
   *   <li>When {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default
   * constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JodaDateTimeType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_whenHistoricDetailVariableInstanceUpdateEntityImpl_thenReturnNull() {
    // Arrange
    JodaDateTimeType jodaDateTimeType = new JodaDateTimeType();

    // Act and Assert
    assertNull(jodaDateTimeType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link JodaDateTimeType}
   *   <li>{@link JodaDateTimeType#getTypeName()}
   *   <li>{@link JodaDateTimeType#isCachable()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    JodaDateTimeType actualJodaDateTimeType = new JodaDateTimeType();
    String actualTypeName = actualJodaDateTimeType.getTypeName();

    // Assert
    assertEquals("jodadatetime", actualTypeName);
    assertTrue(actualJodaDateTimeType.isCachable());
  }
}
