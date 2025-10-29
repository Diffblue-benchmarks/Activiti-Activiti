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
import java.time.ZoneOffset;
import java.util.Date;
import java.util.TimeZone;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntityImpl;
import org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.joda.time.Chronology;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeZone;
import org.joda.time.DurationField;
import org.joda.time.Instant;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.YearMonthDay;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.field.MillisDurationField;
import org.joda.time.field.PreciseDateTimeField;
import org.joda.time.field.PreciseDurationField;
import org.joda.time.field.ScaledDurationField;
import org.joda.time.field.UnsupportedDurationField;
import org.joda.time.tz.FixedDateTimeZone;
import org.junit.Test;

public class JodaDateTimeTypeDiffblueTest {
  /**
   * Method under test: {@link JodaDateTimeType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore() {
    // Arrange, Act and Assert
    assertFalse((new JodaDateTimeType()).isAbleToStore(JSONObject.NULL));
    assertTrue((new JodaDateTimeType()).isAbleToStore(null));
  }

  /**
   * Method under test: {@link JodaDateTimeType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue() {
    // Arrange
    JodaDateTimeType jodaDateTimeType = new JodaDateTimeType();

    // Act and Assert
    assertNull(jodaDateTimeType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Method under test: {@link JodaDateTimeType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue2() {
    // Arrange
    JodaDateTimeType jodaDateTimeType = new JodaDateTimeType();

    HistoricVariableInstanceEntityImpl valueFields = new HistoricVariableInstanceEntityImpl();
    valueFields.setCachedValue(JSONObject.NULL);
    valueFields
        .setCreateTime(Date.from(java.time.LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setDeleted(true);
    valueFields.setDoubleValue(10.0d);
    valueFields.setExecutionId("42");
    valueFields.setId("42");
    valueFields.setInserted(true);
    valueFields.setLastUpdatedTime(
        Date.from(java.time.LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
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
    org.joda.time.Instant toInstantResult = ((DateTime) actualValue).toInstant();
    Chronology chronology = toInstantResult.getChronology();
    assertTrue(chronology instanceof ISOChronology);
    Chronology chronology2 = ((DateTime) actualValue).getChronology();
    assertTrue(chronology2 instanceof ISOChronology);
    org.joda.time.LocalDateTime toLocalDateTimeResult = ((DateTime) actualValue).toLocalDateTime();
    DateTimeField[] fields = toLocalDateTimeResult.getFields();
    assertTrue(fields[3] instanceof PreciseDateTimeField);
    org.joda.time.LocalDateTime.Property weekyearResult = toLocalDateTimeResult.weekyear();
    assertTrue(weekyearResult.getLeapDurationField() instanceof PreciseDurationField);
    org.joda.time.LocalDateTime.Property yearResult = toLocalDateTimeResult.year();
    assertTrue(yearResult.getLeapDurationField() instanceof PreciseDurationField);
    DateTime.Property eraResult = ((DateTime) actualValue).era();
    DurationField durationField = eraResult.getDurationField();
    assertTrue(durationField instanceof UnsupportedDurationField);
    DateTimeZone zone = ((DateTime) actualValue).getZone();
    assertTrue(zone instanceof FixedDateTimeZone);
    LocalTime toLocalTimeResult = ((DateTime) actualValue).toLocalTime();
    assertEquals("00:00:00.042", toLocalTimeResult.toString());
    assertEquals("00:00:00.042", toLocalDateTimeResult.toLocalTime().toString());
    assertEquals("1", eraResult.getAsString());
    org.joda.time.LocalDateTime.Property eraResult2 = toLocalDateTimeResult.era();
    assertEquals("1", eraResult2.getAsString());
    DateTime.Property weekyearResult2 = ((DateTime) actualValue).weekyear();
    assertEquals("1970", weekyearResult2.getAsShortText());
    DateTime.Property yearResult2 = ((DateTime) actualValue).year();
    assertEquals("1970", yearResult2.getAsShortText());
    assertEquals("1970", weekyearResult.getAsShortText());
    assertEquals("1970", yearResult.getAsShortText());
    assertEquals("1970", weekyearResult2.getAsString());
    assertEquals("1970", yearResult2.getAsString());
    assertEquals("1970", weekyearResult.getAsString());
    assertEquals("1970", yearResult.getAsString());
    assertEquals("1970", weekyearResult2.getAsText());
    assertEquals("1970", yearResult2.getAsText());
    assertEquals("1970", weekyearResult.getAsText());
    assertEquals("1970", yearResult.getAsText());
    DateMidnight toDateMidnightResult = ((DateTime) actualValue).toDateMidnight();
    assertEquals("1970-01-01", toDateMidnightResult.toLocalDate().toString());
    org.joda.time.LocalDate toLocalDateResult = ((DateTime) actualValue).toLocalDate();
    assertEquals("1970-01-01", toLocalDateResult.toString());
    assertEquals("1970-01-01", toLocalDateTimeResult.toLocalDate().toString());
    assertEquals("AD", eraResult.getAsShortText());
    assertEquals("AD", eraResult2.getAsShortText());
    assertEquals("AD", eraResult.getAsText());
    assertEquals("AD", eraResult2.getAsText());
    DateTimeZone zone2 = toInstantResult.getZone();
    assertEquals("UTC", zone2.getID());
    DateTimeFieldType fieldType = eraResult.getFieldType();
    assertEquals("era", fieldType.getName());
    assertEquals("era", eraResult.getName());
    assertEquals("era", eraResult2.getName());
    assertEquals("weekyear", weekyearResult2.getName());
    assertEquals("weekyear", weekyearResult.getName());
    assertEquals("year", yearResult2.getName());
    assertEquals("year", yearResult.getName());
    assertNull(eraResult.getLeapDurationField());
    assertNull(eraResult2.getLeapDurationField());
    assertNull(eraResult.getRangeDurationField());
    assertNull(weekyearResult2.getRangeDurationField());
    assertNull(yearResult2.getRangeDurationField());
    assertNull(eraResult2.getRangeDurationField());
    assertNull(weekyearResult.getRangeDurationField());
    assertNull(yearResult.getRangeDurationField());
    assertEquals(-292275054, weekyearResult2.getMinimumValue());
    assertEquals(-292275054, yearResult2.getMinimumValue());
    assertEquals(-292275054, weekyearResult.getMinimumValue());
    assertEquals(-292275054, yearResult.getMinimumValue());
    assertEquals(-292275054, weekyearResult2.getMinimumValueOverall());
    assertEquals(-292275054, yearResult2.getMinimumValueOverall());
    assertEquals(-292275054, weekyearResult.getMinimumValueOverall());
    assertEquals(-292275054, yearResult.getMinimumValueOverall());
    assertEquals(0, toLocalDateTimeResult.getHourOfDay());
    assertEquals(0, toLocalDateTimeResult.getMinuteOfHour());
    assertEquals(0, toLocalDateTimeResult.getSecondOfMinute());
    assertEquals(0, toDateMidnightResult.getHourOfDay());
    assertEquals(0, ((DateTime) actualValue).getHourOfDay());
    assertEquals(0, toDateMidnightResult.getMillisOfDay());
    assertEquals(0, toDateMidnightResult.getMillisOfSecond());
    assertEquals(0, toDateMidnightResult.getMinuteOfDay());
    assertEquals(0, ((DateTime) actualValue).getMinuteOfDay());
    assertEquals(0, toDateMidnightResult.getMinuteOfHour());
    assertEquals(0, ((DateTime) actualValue).getMinuteOfHour());
    assertEquals(0, toDateMidnightResult.getSecondOfDay());
    assertEquals(0, ((DateTime) actualValue).getSecondOfDay());
    assertEquals(0, toDateMidnightResult.getSecondOfMinute());
    assertEquals(0, ((DateTime) actualValue).getSecondOfMinute());
    assertEquals(0, eraResult.getLeapAmount());
    assertEquals(0, yearResult2.getLeapAmount());
    assertEquals(0, eraResult2.getLeapAmount());
    assertEquals(0, yearResult.getLeapAmount());
    assertEquals(0, eraResult.getMinimumValue());
    assertEquals(0, eraResult2.getMinimumValue());
    assertEquals(0, eraResult.getMinimumValueOverall());
    assertEquals(0, eraResult2.getMinimumValueOverall());
    org.joda.time.Instant toInstantResult2 = toDateMidnightResult.toInstant();
    assertEquals(0L, toInstantResult2.getMillis());
    assertEquals(0L, toDateMidnightResult.getMillis());
    assertEquals(1, toLocalDateTimeResult.getDayOfMonth());
    assertEquals(1, toLocalDateTimeResult.getDayOfYear());
    assertEquals(1, toLocalDateTimeResult.getEra());
    assertEquals(1, toLocalDateTimeResult.getMonthOfYear());
    assertEquals(1, toLocalDateTimeResult.getWeekOfWeekyear());
    assertEquals(1, toDateMidnightResult.getDayOfMonth());
    assertEquals(1, ((DateTime) actualValue).getDayOfMonth());
    assertEquals(1, toDateMidnightResult.getDayOfYear());
    assertEquals(1, ((DateTime) actualValue).getDayOfYear());
    assertEquals(1, toDateMidnightResult.getEra());
    assertEquals(1, ((DateTime) actualValue).getEra());
    assertEquals(1, toDateMidnightResult.getMonthOfYear());
    assertEquals(1, ((DateTime) actualValue).getMonthOfYear());
    assertEquals(1, toDateMidnightResult.getWeekOfWeekyear());
    assertEquals(1, ((DateTime) actualValue).getWeekOfWeekyear());
    assertEquals(1, eraResult.get());
    assertEquals(1, eraResult2.get());
    assertEquals(1, weekyearResult2.getLeapAmount());
    assertEquals(1, weekyearResult.getLeapAmount());
    assertEquals(1, eraResult.getMaximumValue());
    assertEquals(1, eraResult2.getMaximumValue());
    assertEquals(1, eraResult.getMaximumValueOverall());
    assertEquals(1, eraResult2.getMaximumValueOverall());
    assertEquals(19, toLocalDateTimeResult.getCenturyOfEra());
    assertEquals(19, toDateMidnightResult.getCenturyOfEra());
    assertEquals(19, ((DateTime) actualValue).getCenturyOfEra());
    assertEquals(1970, toLocalDateTimeResult.getWeekyear());
    assertEquals(1970, toLocalDateTimeResult.getYear());
    assertEquals(1970, toLocalDateTimeResult.getYearOfEra());
    assertEquals(1970, toDateMidnightResult.getWeekyear());
    assertEquals(1970, ((DateTime) actualValue).getWeekyear());
    assertEquals(1970, toDateMidnightResult.getYear());
    assertEquals(1970, ((DateTime) actualValue).getYear());
    assertEquals(1970, toDateMidnightResult.getYearOfEra());
    assertEquals(1970, ((DateTime) actualValue).getYearOfEra());
    assertEquals(1970, weekyearResult2.get());
    assertEquals(1970, yearResult2.get());
    assertEquals(1970, weekyearResult.get());
    assertEquals(1970, yearResult.get());
    assertEquals(259200042L, weekyearResult2.remainder());
    assertEquals(259200042L, weekyearResult.remainder());
    assertEquals(292278993, weekyearResult2.getMaximumValue());
    assertEquals(292278993, yearResult2.getMaximumValue());
    assertEquals(292278993, weekyearResult.getMaximumValue());
    assertEquals(292278993, yearResult.getMaximumValue());
    assertEquals(292278993, weekyearResult2.getMaximumValueOverall());
    assertEquals(292278993, yearResult2.getMaximumValueOverall());
    assertEquals(292278993, weekyearResult.getMaximumValueOverall());
    assertEquals(292278993, yearResult.getMaximumValueOverall());
    assertEquals(4, toLocalDateTimeResult.getDayOfWeek());
    assertEquals(4, toLocalDateTimeResult.size());
    assertEquals(4, toDateMidnightResult.getDayOfWeek());
    assertEquals(4, ((DateTime) actualValue).getDayOfWeek());
    assertEquals(4, toLocalDateTimeResult.getFieldTypes().length);
    assertEquals(4, fields.length);
    assertEquals(42, toLocalDateTimeResult.getMillisOfDay());
    assertEquals(42, toLocalDateTimeResult.getMillisOfSecond());
    assertEquals(42, ((DateTime) actualValue).getMillisOfDay());
    assertEquals(42, ((DateTime) actualValue).getMillisOfSecond());
    assertEquals(42L, toInstantResult.getMillis());
    assertEquals(42L, ((DateTime) actualValue).getMillis());
    assertEquals(42L, yearResult2.remainder());
    assertEquals(42L, yearResult.remainder());
    assertEquals(62135596800042L, eraResult.remainder());
    assertEquals(62135596800042L, eraResult2.remainder());
    assertEquals(70, toLocalDateTimeResult.getYearOfCentury());
    assertEquals(70, toDateMidnightResult.getYearOfCentury());
    assertEquals(70, ((DateTime) actualValue).getYearOfCentury());
    assertFalse(eraResult.isLeap());
    assertFalse(yearResult2.isLeap());
    assertFalse(eraResult2.isLeap());
    assertFalse(yearResult.isLeap());
    assertTrue(zone.isFixed());
    assertTrue(weekyearResult2.isLeap());
    assertTrue(weekyearResult.isLeap());
    assertEquals(actualValue, toInstantResult.toDateTime());
    assertEquals(actualValue, toInstantResult.toDateTimeISO());
    assertEquals(actualValue, toInstantResult.toMutableDateTime());
    assertEquals(actualValue, toInstantResult.toMutableDateTimeISO());
    assertEquals(actualValue, toLocalDateTimeResult.toDateTime());
    assertEquals(actualValue, ((DateTime) actualValue).toMutableDateTime());
    assertEquals(actualValue, ((DateTime) actualValue).toMutableDateTimeISO());
    assertEquals(eraResult, toDateMidnightResult.era());
    assertEquals(toDateMidnightResult, toInstantResult2.toDateTime());
    assertEquals(toDateMidnightResult, toInstantResult2.toDateTimeISO());
    assertEquals(toDateMidnightResult, toInstantResult2.toMutableDateTime());
    assertEquals(toDateMidnightResult, toInstantResult2.toMutableDateTimeISO());
    assertEquals(toDateMidnightResult, toDateMidnightResult.toDateTime());
    assertEquals(toDateMidnightResult, toDateMidnightResult.toDateTimeISO());
    assertEquals(toDateMidnightResult, toDateMidnightResult.toMutableDateTime());
    assertEquals(toDateMidnightResult, toDateMidnightResult.toMutableDateTimeISO());
    assertEquals(toLocalDateResult, toDateMidnightResult.toYearMonthDay());
    assertEquals(toLocalDateResult, ((DateTime) actualValue).toYearMonthDay());
    assertEquals(toLocalTimeResult, ((DateTime) actualValue).toTimeOfDay());
    assertEquals(weekyearResult2, toDateMidnightResult.weekyear());
    assertEquals(yearResult2, toDateMidnightResult.year());
    String expectedID = System.getProperty("user.timezone");
    assertEquals(expectedID, zone.getID());
    assertSame(actualValue, ((DateTime) actualValue).toDateTime());
    assertSame(actualValue, ((DateTime) actualValue).toDateTimeISO());
    assertSame(actualValue, eraResult.getDateTime());
    assertSame(actualValue, weekyearResult2.getDateTime());
    assertSame(actualValue, yearResult2.getDateTime());
    assertSame(toLocalDateTimeResult, eraResult2.getLocalDateTime());
    assertSame(toLocalDateTimeResult, weekyearResult.getLocalDateTime());
    assertSame(toLocalDateTimeResult, yearResult.getLocalDateTime());
    assertSame(zone2, toInstantResult2.getZone());
    assertSame(toInstantResult2, toInstantResult2.toInstant());
    assertSame(toInstantResult, toInstantResult.toInstant());
    assertSame(fieldType, eraResult2.getFieldType());
    DateTimeField expectedField = fields[0];
    assertSame(expectedField, yearResult.getField());
    assertSame(chronology, toInstantResult2.getChronology());
    assertSame(chronology, toLocalDateTimeResult.getChronology());
    assertSame(chronology2, toDateMidnightResult.getChronology());
    assertSame(durationField, chronology2.eras());
    assertSame(durationField, eraResult2.getDurationField());
    assertSame(zone, chronology2.getZone());
    assertSame(zone, toDateMidnightResult.getZone());
    assertArrayEquals(new int[]{1970, 1, 1, 42}, toLocalDateTimeResult.getValues());
  }

  /**
   * Method under test: {@link JodaDateTimeType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue3() {
    // Arrange
    JodaDateTimeType jodaDateTimeType = new JodaDateTimeType();
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getLongValue()).thenReturn(42L);

    // Act
    Object actualValue = jodaDateTimeType.getValue(valueFields);

    // Assert
    verify(valueFields).getLongValue();
    assertTrue(actualValue instanceof DateTime);
    Instant toInstantResult = ((DateTime) actualValue).toInstant();
    Chronology chronology = toInstantResult.getChronology();
    assertTrue(chronology instanceof ISOChronology);
    Chronology chronology2 = ((DateTime) actualValue).getChronology();
    assertTrue(chronology2 instanceof ISOChronology);
    LocalDateTime toLocalDateTimeResult = ((DateTime) actualValue).toLocalDateTime();
    DateTimeField[] fields = toLocalDateTimeResult.getFields();
    assertTrue(fields[3] instanceof PreciseDateTimeField);
    LocalDateTime.Property weekyearResult = toLocalDateTimeResult.weekyear();
    assertTrue(weekyearResult.getLeapDurationField() instanceof PreciseDurationField);
    LocalDateTime.Property yearResult = toLocalDateTimeResult.year();
    assertTrue(yearResult.getLeapDurationField() instanceof PreciseDurationField);
    DateTime.Property eraResult = ((DateTime) actualValue).era();
    DurationField durationField = eraResult.getDurationField();
    assertTrue(durationField instanceof UnsupportedDurationField);
    DateTimeZone zone = ((DateTime) actualValue).getZone();
    assertTrue(zone instanceof FixedDateTimeZone);
    LocalTime toLocalTimeResult = ((DateTime) actualValue).toLocalTime();
    assertEquals("00:00:00.042", toLocalTimeResult.toString());
    assertEquals("00:00:00.042", toLocalDateTimeResult.toLocalTime().toString());
    assertEquals("1", eraResult.getAsString());
    LocalDateTime.Property eraResult2 = toLocalDateTimeResult.era();
    assertEquals("1", eraResult2.getAsString());
    DateTime.Property weekyearResult2 = ((DateTime) actualValue).weekyear();
    assertEquals("1970", weekyearResult2.getAsShortText());
    DateTime.Property yearResult2 = ((DateTime) actualValue).year();
    assertEquals("1970", yearResult2.getAsShortText());
    assertEquals("1970", weekyearResult.getAsShortText());
    assertEquals("1970", yearResult.getAsShortText());
    assertEquals("1970", weekyearResult2.getAsString());
    assertEquals("1970", yearResult2.getAsString());
    assertEquals("1970", weekyearResult.getAsString());
    assertEquals("1970", yearResult.getAsString());
    assertEquals("1970", weekyearResult2.getAsText());
    assertEquals("1970", yearResult2.getAsText());
    assertEquals("1970", weekyearResult.getAsText());
    assertEquals("1970", yearResult.getAsText());
    DateMidnight toDateMidnightResult = ((DateTime) actualValue).toDateMidnight();
    assertEquals("1970-01-01", toDateMidnightResult.toLocalDate().toString());
    LocalDate toLocalDateResult = ((DateTime) actualValue).toLocalDate();
    assertEquals("1970-01-01", toLocalDateResult.toString());
    assertEquals("1970-01-01", toLocalDateTimeResult.toLocalDate().toString());
    assertEquals("AD", eraResult.getAsShortText());
    assertEquals("AD", eraResult2.getAsShortText());
    assertEquals("AD", eraResult.getAsText());
    assertEquals("AD", eraResult2.getAsText());
    DateTimeZone zone2 = toInstantResult.getZone();
    assertEquals("UTC", zone2.getID());
    DateTimeFieldType fieldType = eraResult.getFieldType();
    assertEquals("era", fieldType.getName());
    assertEquals("era", eraResult.getName());
    assertEquals("era", eraResult2.getName());
    assertEquals("weekyear", weekyearResult2.getName());
    assertEquals("weekyear", weekyearResult.getName());
    assertEquals("year", yearResult2.getName());
    assertEquals("year", yearResult.getName());
    assertNull(eraResult.getLeapDurationField());
    assertNull(eraResult2.getLeapDurationField());
    assertNull(eraResult.getRangeDurationField());
    assertNull(weekyearResult2.getRangeDurationField());
    assertNull(yearResult2.getRangeDurationField());
    assertNull(eraResult2.getRangeDurationField());
    assertNull(weekyearResult.getRangeDurationField());
    assertNull(yearResult.getRangeDurationField());
    assertEquals(-292275054, weekyearResult2.getMinimumValue());
    assertEquals(-292275054, yearResult2.getMinimumValue());
    assertEquals(-292275054, weekyearResult.getMinimumValue());
    assertEquals(-292275054, yearResult.getMinimumValue());
    assertEquals(-292275054, weekyearResult2.getMinimumValueOverall());
    assertEquals(-292275054, yearResult2.getMinimumValueOverall());
    assertEquals(-292275054, weekyearResult.getMinimumValueOverall());
    assertEquals(-292275054, yearResult.getMinimumValueOverall());
    assertEquals(0, toLocalDateTimeResult.getHourOfDay());
    assertEquals(0, toLocalDateTimeResult.getMinuteOfHour());
    assertEquals(0, toLocalDateTimeResult.getSecondOfMinute());
    assertEquals(0, toDateMidnightResult.getHourOfDay());
    assertEquals(0, ((DateTime) actualValue).getHourOfDay());
    assertEquals(0, toDateMidnightResult.getMillisOfDay());
    assertEquals(0, toDateMidnightResult.getMillisOfSecond());
    assertEquals(0, toDateMidnightResult.getMinuteOfDay());
    assertEquals(0, ((DateTime) actualValue).getMinuteOfDay());
    assertEquals(0, toDateMidnightResult.getMinuteOfHour());
    assertEquals(0, ((DateTime) actualValue).getMinuteOfHour());
    assertEquals(0, toDateMidnightResult.getSecondOfDay());
    assertEquals(0, ((DateTime) actualValue).getSecondOfDay());
    assertEquals(0, toDateMidnightResult.getSecondOfMinute());
    assertEquals(0, ((DateTime) actualValue).getSecondOfMinute());
    assertEquals(0, eraResult.getLeapAmount());
    assertEquals(0, yearResult2.getLeapAmount());
    assertEquals(0, eraResult2.getLeapAmount());
    assertEquals(0, yearResult.getLeapAmount());
    assertEquals(0, eraResult.getMinimumValue());
    assertEquals(0, eraResult2.getMinimumValue());
    assertEquals(0, eraResult.getMinimumValueOverall());
    assertEquals(0, eraResult2.getMinimumValueOverall());
    Instant toInstantResult2 = toDateMidnightResult.toInstant();
    assertEquals(0L, toInstantResult2.getMillis());
    assertEquals(0L, toDateMidnightResult.getMillis());
    assertEquals(1, toLocalDateTimeResult.getDayOfMonth());
    assertEquals(1, toLocalDateTimeResult.getDayOfYear());
    assertEquals(1, toLocalDateTimeResult.getEra());
    assertEquals(1, toLocalDateTimeResult.getMonthOfYear());
    assertEquals(1, toLocalDateTimeResult.getWeekOfWeekyear());
    assertEquals(1, toDateMidnightResult.getDayOfMonth());
    assertEquals(1, ((DateTime) actualValue).getDayOfMonth());
    assertEquals(1, toDateMidnightResult.getDayOfYear());
    assertEquals(1, ((DateTime) actualValue).getDayOfYear());
    assertEquals(1, toDateMidnightResult.getEra());
    assertEquals(1, ((DateTime) actualValue).getEra());
    assertEquals(1, toDateMidnightResult.getMonthOfYear());
    assertEquals(1, ((DateTime) actualValue).getMonthOfYear());
    assertEquals(1, toDateMidnightResult.getWeekOfWeekyear());
    assertEquals(1, ((DateTime) actualValue).getWeekOfWeekyear());
    assertEquals(1, eraResult.get());
    assertEquals(1, eraResult2.get());
    assertEquals(1, weekyearResult2.getLeapAmount());
    assertEquals(1, weekyearResult.getLeapAmount());
    assertEquals(1, eraResult.getMaximumValue());
    assertEquals(1, eraResult2.getMaximumValue());
    assertEquals(1, eraResult.getMaximumValueOverall());
    assertEquals(1, eraResult2.getMaximumValueOverall());
    assertEquals(19, toLocalDateTimeResult.getCenturyOfEra());
    assertEquals(19, toDateMidnightResult.getCenturyOfEra());
    assertEquals(19, ((DateTime) actualValue).getCenturyOfEra());
    assertEquals(1970, toLocalDateTimeResult.getWeekyear());
    assertEquals(1970, toLocalDateTimeResult.getYear());
    assertEquals(1970, toLocalDateTimeResult.getYearOfEra());
    assertEquals(1970, toDateMidnightResult.getWeekyear());
    assertEquals(1970, ((DateTime) actualValue).getWeekyear());
    assertEquals(1970, toDateMidnightResult.getYear());
    assertEquals(1970, ((DateTime) actualValue).getYear());
    assertEquals(1970, toDateMidnightResult.getYearOfEra());
    assertEquals(1970, ((DateTime) actualValue).getYearOfEra());
    assertEquals(1970, weekyearResult2.get());
    assertEquals(1970, yearResult2.get());
    assertEquals(1970, weekyearResult.get());
    assertEquals(1970, yearResult.get());
    assertEquals(259200042L, weekyearResult2.remainder());
    assertEquals(259200042L, weekyearResult.remainder());
    assertEquals(292278993, weekyearResult2.getMaximumValue());
    assertEquals(292278993, yearResult2.getMaximumValue());
    assertEquals(292278993, weekyearResult.getMaximumValue());
    assertEquals(292278993, yearResult.getMaximumValue());
    assertEquals(292278993, weekyearResult2.getMaximumValueOverall());
    assertEquals(292278993, yearResult2.getMaximumValueOverall());
    assertEquals(292278993, weekyearResult.getMaximumValueOverall());
    assertEquals(292278993, yearResult.getMaximumValueOverall());
    assertEquals(4, toLocalDateTimeResult.getDayOfWeek());
    assertEquals(4, toLocalDateTimeResult.size());
    assertEquals(4, toDateMidnightResult.getDayOfWeek());
    assertEquals(4, ((DateTime) actualValue).getDayOfWeek());
    assertEquals(4, toLocalDateTimeResult.getFieldTypes().length);
    assertEquals(4, fields.length);
    assertEquals(42, toLocalDateTimeResult.getMillisOfDay());
    assertEquals(42, toLocalDateTimeResult.getMillisOfSecond());
    assertEquals(42, ((DateTime) actualValue).getMillisOfDay());
    assertEquals(42, ((DateTime) actualValue).getMillisOfSecond());
    assertEquals(42L, toInstantResult.getMillis());
    assertEquals(42L, ((DateTime) actualValue).getMillis());
    assertEquals(42L, yearResult2.remainder());
    assertEquals(42L, yearResult.remainder());
    assertEquals(62135596800042L, eraResult.remainder());
    assertEquals(62135596800042L, eraResult2.remainder());
    assertEquals(70, toLocalDateTimeResult.getYearOfCentury());
    assertEquals(70, toDateMidnightResult.getYearOfCentury());
    assertEquals(70, ((DateTime) actualValue).getYearOfCentury());
    assertFalse(eraResult.isLeap());
    assertFalse(yearResult2.isLeap());
    assertFalse(eraResult2.isLeap());
    assertFalse(yearResult.isLeap());
    assertTrue(zone.isFixed());
    assertTrue(weekyearResult2.isLeap());
    assertTrue(weekyearResult.isLeap());
    assertEquals(actualValue, toInstantResult.toDateTime());
    assertEquals(actualValue, toInstantResult.toDateTimeISO());
    assertEquals(actualValue, toInstantResult.toMutableDateTime());
    assertEquals(actualValue, toInstantResult.toMutableDateTimeISO());
    assertEquals(actualValue, toLocalDateTimeResult.toDateTime());
    assertEquals(actualValue, ((DateTime) actualValue).toMutableDateTime());
    assertEquals(actualValue, ((DateTime) actualValue).toMutableDateTimeISO());
    assertEquals(eraResult, toDateMidnightResult.era());
    assertEquals(toDateMidnightResult, toInstantResult2.toDateTime());
    assertEquals(toDateMidnightResult, toInstantResult2.toDateTimeISO());
    assertEquals(toDateMidnightResult, toInstantResult2.toMutableDateTime());
    assertEquals(toDateMidnightResult, toInstantResult2.toMutableDateTimeISO());
    assertEquals(toDateMidnightResult, toDateMidnightResult.toDateTime());
    assertEquals(toDateMidnightResult, toDateMidnightResult.toDateTimeISO());
    assertEquals(toDateMidnightResult, toDateMidnightResult.toMutableDateTime());
    assertEquals(toDateMidnightResult, toDateMidnightResult.toMutableDateTimeISO());
    assertEquals(toLocalDateResult, toDateMidnightResult.toYearMonthDay());
    assertEquals(toLocalDateResult, ((DateTime) actualValue).toYearMonthDay());
    assertEquals(toLocalTimeResult, ((DateTime) actualValue).toTimeOfDay());
    assertEquals(weekyearResult2, toDateMidnightResult.weekyear());
    assertEquals(yearResult2, toDateMidnightResult.year());
    String expectedID = System.getProperty("user.timezone");
    assertEquals(expectedID, zone.getID());
    assertSame(actualValue, ((DateTime) actualValue).toDateTime());
    assertSame(actualValue, ((DateTime) actualValue).toDateTimeISO());
    assertSame(actualValue, eraResult.getDateTime());
    assertSame(actualValue, weekyearResult2.getDateTime());
    assertSame(actualValue, yearResult2.getDateTime());
    assertSame(toLocalDateTimeResult, eraResult2.getLocalDateTime());
    assertSame(toLocalDateTimeResult, weekyearResult.getLocalDateTime());
    assertSame(toLocalDateTimeResult, yearResult.getLocalDateTime());
    assertSame(zone2, toInstantResult2.getZone());
    assertSame(toInstantResult2, toInstantResult2.toInstant());
    assertSame(toInstantResult, toInstantResult.toInstant());
    assertSame(fieldType, eraResult2.getFieldType());
    DateTimeField expectedField = fields[0];
    assertSame(expectedField, yearResult.getField());
    assertSame(chronology, toInstantResult2.getChronology());
    assertSame(chronology, toLocalDateTimeResult.getChronology());
    assertSame(chronology2, toDateMidnightResult.getChronology());
    assertSame(durationField, chronology2.eras());
    assertSame(durationField, eraResult2.getDurationField());
    assertSame(zone, chronology2.getZone());
    assertSame(zone, toDateMidnightResult.getZone());
    assertArrayEquals(new int[]{1970, 1, 1, 42}, toLocalDateTimeResult.getValues());
  }

  /**
   * Method under test: {@link JodaDateTimeType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue4() {
    // Arrange
    JodaDateTimeType jodaDateTimeType = new JodaDateTimeType();
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getLongValue()).thenReturn(Long.MAX_VALUE);

    // Act
    Object actualValue = jodaDateTimeType.getValue(valueFields);

    // Assert
    verify(valueFields).getLongValue();
    assertTrue(actualValue instanceof DateTime);
    Chronology chronology = ((DateTime) actualValue).getChronology();
    assertTrue(chronology instanceof ISOChronology);
    assertTrue(chronology.millis() instanceof MillisDurationField);
    LocalDateTime toLocalDateTimeResult = ((DateTime) actualValue).toLocalDateTime();
    DateTimeField[] fields = toLocalDateTimeResult.getFields();
    assertTrue(fields[3] instanceof PreciseDateTimeField);
    assertTrue(chronology.halfdays() instanceof PreciseDurationField);
    assertTrue(chronology.hours() instanceof PreciseDurationField);
    assertTrue(chronology.minutes() instanceof PreciseDurationField);
    assertTrue(chronology.seconds() instanceof PreciseDurationField);
    DateTime.Property weekyearResult = ((DateTime) actualValue).weekyear();
    DurationField leapDurationField = weekyearResult.getLeapDurationField();
    assertTrue(leapDurationField instanceof PreciseDurationField);
    DateTime.Property yearResult = ((DateTime) actualValue).year();
    DurationField leapDurationField2 = yearResult.getLeapDurationField();
    assertTrue(leapDurationField2 instanceof PreciseDurationField);
    assertTrue(chronology.centuries() instanceof ScaledDurationField);
    DateTime.Property eraResult = ((DateTime) actualValue).era();
    DurationField durationField = eraResult.getDurationField();
    assertTrue(durationField instanceof UnsupportedDurationField);
    assertEquals("-292275055", weekyearResult.getAsShortText());
    assertEquals("-292275055", weekyearResult.getAsString());
    assertEquals("-292275055", weekyearResult.getAsText());
    LocalTime toLocalTimeResult = ((DateTime) actualValue).toLocalTime();
    assertEquals("07:12:55.807", toLocalTimeResult.toString());
    assertEquals("07:12:55.807", toLocalDateTimeResult.toLocalTime().toString());
    assertEquals("1", eraResult.getAsString());
    assertEquals("292278994", yearResult.getAsShortText());
    assertEquals("292278994", yearResult.getAsString());
    assertEquals("292278994", yearResult.getAsText());
    DateMidnight toDateMidnightResult = ((DateTime) actualValue).toDateMidnight();
    assertEquals("292278994-08-17", toDateMidnightResult.toLocalDate().toString());
    LocalDate toLocalDateResult = ((DateTime) actualValue).toLocalDate();
    assertEquals("292278994-08-17", toLocalDateResult.toString());
    assertEquals("292278994-08-17", toLocalDateTimeResult.toLocalDate().toString());
    assertEquals("AD", eraResult.getAsShortText());
    assertEquals("AD", eraResult.getAsText());
    DateTimeZone zone = ((DateTime) actualValue).getZone();
    TimeZone toTimeZoneResult = zone.toTimeZone();
    assertEquals("Coordinated Universal Time", toTimeZoneResult.getDisplayName());
    assertEquals("UTC", toTimeZoneResult.getID());
    assertEquals("UTC", zone.getID());
    assertEquals("era", eraResult.getFieldType().getName());
    assertEquals("era", eraResult.getName());
    assertEquals("weekyear", weekyearResult.getName());
    assertEquals("year", yearResult.getName());
    assertNull(eraResult.getLeapDurationField());
    assertNull(eraResult.getRangeDurationField());
    assertNull(weekyearResult.getRangeDurationField());
    assertNull(yearResult.getRangeDurationField());
    assertEquals(-292275054, weekyearResult.getMinimumValue());
    assertEquals(-292275054, yearResult.getMinimumValue());
    assertEquals(-292275054, weekyearResult.getMinimumValueOverall());
    assertEquals(-292275054, yearResult.getMinimumValueOverall());
    assertEquals(-292275055, toLocalDateTimeResult.getWeekyear());
    assertEquals(-292275055, toDateMidnightResult.getWeekyear());
    assertEquals(-292275055, ((DateTime) actualValue).getWeekyear());
    assertEquals(-292275055, weekyearResult.get());
    assertEquals(-9223309901257975809L, eraResult.remainder());
    assertEquals(0, toTimeZoneResult.getDSTSavings());
    assertEquals(0, toDateMidnightResult.getHourOfDay());
    assertEquals(0, toDateMidnightResult.getMillisOfDay());
    assertEquals(0, toDateMidnightResult.getMillisOfSecond());
    assertEquals(0, toDateMidnightResult.getMinuteOfDay());
    assertEquals(0, toDateMidnightResult.getMinuteOfHour());
    assertEquals(0, toDateMidnightResult.getSecondOfDay());
    assertEquals(0, toDateMidnightResult.getSecondOfMinute());
    assertEquals(0, eraResult.getLeapAmount());
    assertEquals(0, weekyearResult.getLeapAmount());
    assertEquals(0, yearResult.getLeapAmount());
    assertEquals(0, eraResult.getMinimumValue());
    assertEquals(0, eraResult.getMinimumValueOverall());
    assertEquals(1, toLocalDateTimeResult.getEra());
    assertEquals(1, toLocalDateTimeResult.getWeekOfWeekyear());
    assertEquals(1, toDateMidnightResult.getEra());
    assertEquals(1, ((DateTime) actualValue).getEra());
    assertEquals(1, toDateMidnightResult.getWeekOfWeekyear());
    assertEquals(1, ((DateTime) actualValue).getWeekOfWeekyear());
    assertEquals(1, eraResult.get());
    assertEquals(1, eraResult.getMaximumValue());
    assertEquals(1, eraResult.getMaximumValueOverall());
    assertEquals(12, toLocalDateTimeResult.getMinuteOfHour());
    assertEquals(12, ((DateTime) actualValue).getMinuteOfHour());
    assertEquals(17, toLocalDateTimeResult.getDayOfMonth());
    assertEquals(17, toDateMidnightResult.getDayOfMonth());
    assertEquals(17, ((DateTime) actualValue).getDayOfMonth());
    assertEquals(19725175807L, yearResult.remainder());
    assertEquals(229, toLocalDateTimeResult.getDayOfYear());
    assertEquals(229, toDateMidnightResult.getDayOfYear());
    assertEquals(229, ((DateTime) actualValue).getDayOfYear());
    assertEquals(25975, ((DateTime) actualValue).getSecondOfDay());
    assertEquals(25975807, toLocalDateTimeResult.getMillisOfDay());
    assertEquals(25975807, ((DateTime) actualValue).getMillisOfDay());
    assertEquals(2922789, toLocalDateTimeResult.getCenturyOfEra());
    assertEquals(2922789, toDateMidnightResult.getCenturyOfEra());
    assertEquals(2922789, ((DateTime) actualValue).getCenturyOfEra());
    assertEquals(292278993, weekyearResult.getMaximumValue());
    assertEquals(292278993, yearResult.getMaximumValue());
    assertEquals(292278993, weekyearResult.getMaximumValueOverall());
    assertEquals(292278993, yearResult.getMaximumValueOverall());
    assertEquals(292278994, toLocalDateTimeResult.getYear());
    assertEquals(292278994, toLocalDateTimeResult.getYearOfEra());
    assertEquals(292278994, toDateMidnightResult.getYear());
    assertEquals(292278994, ((DateTime) actualValue).getYear());
    assertEquals(292278994, toDateMidnightResult.getYearOfEra());
    assertEquals(292278994, ((DateTime) actualValue).getYearOfEra());
    assertEquals(292278994, yearResult.get());
    assertEquals(4, toLocalDateTimeResult.size());
    assertEquals(4, toLocalDateTimeResult.getFieldTypes().length);
    assertEquals(4, fields.length);
    assertEquals(432, ((DateTime) actualValue).getMinuteOfDay());
    assertEquals(55, toLocalDateTimeResult.getSecondOfMinute());
    assertEquals(55, ((DateTime) actualValue).getSecondOfMinute());
    assertEquals(578824191L, weekyearResult.remainder());
    assertEquals(7, toLocalDateTimeResult.getDayOfWeek());
    assertEquals(7, toLocalDateTimeResult.getHourOfDay());
    assertEquals(7, toDateMidnightResult.getDayOfWeek());
    assertEquals(7, ((DateTime) actualValue).getDayOfWeek());
    assertEquals(7, ((DateTime) actualValue).getHourOfDay());
    assertEquals(8, toLocalDateTimeResult.getMonthOfYear());
    assertEquals(8, toDateMidnightResult.getMonthOfYear());
    assertEquals(8, ((DateTime) actualValue).getMonthOfYear());
    assertEquals(807, toLocalDateTimeResult.getMillisOfSecond());
    assertEquals(807, ((DateTime) actualValue).getMillisOfSecond());
    assertEquals(9223372036828800000L, toDateMidnightResult.getMillis());
    assertEquals(94, toLocalDateTimeResult.getYearOfCentury());
    assertEquals(94, toDateMidnightResult.getYearOfCentury());
    assertEquals(94, ((DateTime) actualValue).getYearOfCentury());
    assertFalse(eraResult.isLeap());
    assertFalse(weekyearResult.isLeap());
    assertFalse(yearResult.isLeap());
    assertEquals(actualValue, ((DateTime) actualValue).toDateTimeISO());
    assertEquals(actualValue, toLocalDateTimeResult.toDateTime());
    assertEquals(actualValue, ((DateTime) actualValue).toInstant());
    assertEquals(actualValue, ((DateTime) actualValue).toMutableDateTime());
    assertEquals(actualValue, ((DateTime) actualValue).toMutableDateTimeISO());
    assertEquals(eraResult, toDateMidnightResult.era());
    assertEquals(eraResult, toLocalDateTimeResult.era());
    assertEquals(toDateMidnightResult, toDateMidnightResult.toDateTime());
    assertEquals(toDateMidnightResult, toDateMidnightResult.toDateTimeISO());
    assertEquals(toDateMidnightResult, toDateMidnightResult.toInstant());
    assertEquals(toDateMidnightResult, toDateMidnightResult.toMutableDateTime());
    assertEquals(toDateMidnightResult, toDateMidnightResult.toMutableDateTimeISO());
    assertEquals(toLocalDateResult, toDateMidnightResult.toYearMonthDay());
    assertEquals(toLocalDateResult, ((DateTime) actualValue).toYearMonthDay());
    assertEquals(toLocalTimeResult, ((DateTime) actualValue).toTimeOfDay());
    assertEquals(weekyearResult, toDateMidnightResult.weekyear());
    assertEquals(weekyearResult, toLocalDateTimeResult.weekyear());
    assertEquals(yearResult, toDateMidnightResult.year());
    assertEquals(yearResult, toLocalDateTimeResult.year());
    assertEquals(Long.MAX_VALUE, ((DateTime) actualValue).getMillis());
    assertSame(actualValue, ((DateTime) actualValue).toDateTime());
    assertSame(actualValue, eraResult.getDateTime());
    assertSame(actualValue, weekyearResult.getDateTime());
    assertSame(actualValue, yearResult.getDateTime());
    assertSame(zone, chronology.getZone());
    assertSame(zone, toDateMidnightResult.getZone());
    assertSame(chronology, toLocalDateTimeResult.getChronology());
    assertSame(chronology, toDateMidnightResult.getChronology());
    assertSame(leapDurationField, chronology.weeks());
    assertSame(leapDurationField2, chronology.days());
    assertSame(durationField, chronology.eras());
    assertArrayEquals(new int[]{292278994, 8, 17, 25975807}, toLocalDateTimeResult.getValues());
  }

  /**
   * Method under test: {@link JodaDateTimeType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue5() {
    // Arrange
    JodaDateTimeType jodaDateTimeType = new JodaDateTimeType();
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getLongValue()).thenReturn(Long.MIN_VALUE);

    // Act
    Object actualValue = jodaDateTimeType.getValue(valueFields);

    // Assert
    verify(valueFields).getLongValue();
    assertTrue(actualValue instanceof DateTime);
    Chronology chronology = ((DateTime) actualValue).getChronology();
    assertTrue(chronology instanceof ISOChronology);
    assertTrue(chronology.millis() instanceof MillisDurationField);
    LocalDateTime toLocalDateTimeResult = ((DateTime) actualValue).toLocalDateTime();
    DateTimeField[] fields = toLocalDateTimeResult.getFields();
    assertTrue(fields[3] instanceof PreciseDateTimeField);
    assertTrue(chronology.halfdays() instanceof PreciseDurationField);
    assertTrue(chronology.hours() instanceof PreciseDurationField);
    assertTrue(chronology.minutes() instanceof PreciseDurationField);
    assertTrue(chronology.seconds() instanceof PreciseDurationField);
    DateTime.Property weekyearResult = ((DateTime) actualValue).weekyear();
    DurationField leapDurationField = weekyearResult.getLeapDurationField();
    assertTrue(leapDurationField instanceof PreciseDurationField);
    DateTime.Property yearResult = ((DateTime) actualValue).year();
    DurationField leapDurationField2 = yearResult.getLeapDurationField();
    assertTrue(leapDurationField2 instanceof PreciseDurationField);
    assertTrue(chronology.centuries() instanceof ScaledDurationField);
    DateTime.Property eraResult = ((DateTime) actualValue).era();
    DurationField durationField = eraResult.getDurationField();
    assertTrue(durationField instanceof UnsupportedDurationField);
    DateMidnight toDateMidnightResult = ((DateTime) actualValue).toDateMidnight();
    DateMidnight.Property weekyearResult2 = toDateMidnightResult.weekyear();
    assertEquals("-292275055", weekyearResult2.getAsShortText());
    assertEquals("-292275055", yearResult.getAsShortText());
    assertEquals("-292275055", weekyearResult2.getAsString());
    assertEquals("-292275055", yearResult.getAsString());
    assertEquals("-292275055", weekyearResult2.getAsText());
    assertEquals("-292275055", yearResult.getAsText());
    YearMonthDay toYearMonthDayResult = ((DateTime) actualValue).toYearMonthDay();
    assertEquals("-292275055-05-16", toYearMonthDayResult.toLocalDate().toString());
    assertEquals("0", eraResult.getAsString());
    DateMidnight.Property eraResult2 = toDateMidnightResult.era();
    assertEquals("1", eraResult2.getAsString());
    LocalTime toLocalTimeResult = ((DateTime) actualValue).toLocalTime();
    assertEquals("16:47:04.192", toLocalTimeResult.toString());
    assertEquals("16:47:04.192", toLocalDateTimeResult.toLocalTime().toString());
    DateMidnight.Property yearResult2 = toDateMidnightResult.year();
    assertEquals("292278994", yearResult2.getAsShortText());
    assertEquals("292278994", weekyearResult.getAsShortText());
    assertEquals("292278994", yearResult2.getAsString());
    assertEquals("292278994", weekyearResult.getAsString());
    assertEquals("292278994", yearResult2.getAsText());
    assertEquals("292278994", weekyearResult.getAsText());
    assertEquals("292278994-08-16", toDateMidnightResult.toLocalDate().toString());
    LocalDate toLocalDateResult = ((DateTime) actualValue).toLocalDate();
    assertEquals("292278994-08-16", toLocalDateResult.toString());
    assertEquals("292278994-08-16", toLocalDateTimeResult.toLocalDate().toString());
    assertEquals("AD", eraResult2.getAsShortText());
    assertEquals("AD", eraResult2.getAsText());
    assertEquals("BC", eraResult.getAsShortText());
    assertEquals("BC", eraResult.getAsText());
    DateTimeZone zone = ((DateTime) actualValue).getZone();
    TimeZone toTimeZoneResult = zone.toTimeZone();
    assertEquals("Coordinated Universal Time", toTimeZoneResult.getDisplayName());
    assertEquals("UTC", toTimeZoneResult.getID());
    assertEquals("UTC", zone.getID());
    DateTimeFieldType fieldType = eraResult.getFieldType();
    assertEquals("era", fieldType.getName());
    assertEquals("era", eraResult2.getName());
    assertEquals("era", eraResult.getName());
    assertEquals("weekyear", weekyearResult2.getName());
    assertEquals("weekyear", weekyearResult.getName());
    assertEquals("year", yearResult2.getName());
    assertEquals("year", yearResult.getName());
    assertNull(eraResult2.getLeapDurationField());
    assertNull(eraResult.getLeapDurationField());
    assertNull(eraResult2.getRangeDurationField());
    assertNull(weekyearResult2.getRangeDurationField());
    assertNull(yearResult2.getRangeDurationField());
    assertNull(eraResult.getRangeDurationField());
    assertNull(weekyearResult.getRangeDurationField());
    assertNull(yearResult.getRangeDurationField());
    assertEquals(-292275054, weekyearResult2.getMinimumValue());
    assertEquals(-292275054, yearResult2.getMinimumValue());
    assertEquals(-292275054, weekyearResult.getMinimumValue());
    assertEquals(-292275054, yearResult.getMinimumValue());
    assertEquals(-292275054, weekyearResult2.getMinimumValueOverall());
    assertEquals(-292275054, yearResult2.getMinimumValueOverall());
    assertEquals(-292275054, weekyearResult.getMinimumValueOverall());
    assertEquals(-292275054, yearResult.getMinimumValueOverall());
    assertEquals(-292275055, toLocalDateTimeResult.getYear());
    assertEquals(-292275055, toYearMonthDayResult.getYear());
    assertEquals(-292275055, toDateMidnightResult.getWeekyear());
    assertEquals(-292275055, ((DateTime) actualValue).getYear());
    assertEquals(-292275055, weekyearResult2.get());
    assertEquals(-292275055, yearResult.get());
    assertEquals(-9223309901318400000L, eraResult2.remainder());
    assertEquals(0, toTimeZoneResult.getDSTSavings());
    assertEquals(0, toLocalDateTimeResult.getEra());
    assertEquals(0, ((DateTime) actualValue).getEra());
    assertEquals(0, eraResult.get());
    assertEquals(0, eraResult2.getLeapAmount());
    assertEquals(0, weekyearResult2.getLeapAmount());
    assertEquals(0, yearResult2.getLeapAmount());
    assertEquals(0, eraResult.getLeapAmount());
    assertEquals(0, weekyearResult.getLeapAmount());
    assertEquals(0, yearResult.getLeapAmount());
    assertEquals(0, eraResult2.getMinimumValue());
    assertEquals(0, eraResult.getMinimumValue());
    assertEquals(0, eraResult2.getMinimumValueOverall());
    assertEquals(0, eraResult.getMinimumValueOverall());
    assertEquals(0L, eraResult.remainder());
    assertEquals(1, toDateMidnightResult.getEra());
    assertEquals(1, toDateMidnightResult.getWeekOfWeekyear());
    assertEquals(1, eraResult2.get());
    assertEquals(1, eraResult2.getMaximumValue());
    assertEquals(1, eraResult.getMaximumValue());
    assertEquals(1, eraResult2.getMaximumValueOverall());
    assertEquals(1, eraResult.getMaximumValueOverall());
    assertEquals(1007, ((DateTime) actualValue).getMinuteOfDay());
    assertEquals(11724424192L, yearResult.remainder());
    assertEquals(136, toLocalDateTimeResult.getDayOfYear());
    assertEquals(136, ((DateTime) actualValue).getDayOfYear());
    assertEquals(14, toDateMidnightResult.getHourOfDay());
    assertEquals(192, toLocalDateTimeResult.getMillisOfSecond());
    assertEquals(192, ((DateTime) actualValue).getMillisOfSecond());
    assertEquals(19664751616L, yearResult2.remainder());
    assertEquals(228, toDateMidnightResult.getDayOfYear());
    assertEquals(25, toDateMidnightResult.getMinuteOfHour());
    assertEquals(2922750, toLocalDateTimeResult.getCenturyOfEra());
    assertEquals(2922750, ((DateTime) actualValue).getCenturyOfEra());
    assertEquals(292275056, toLocalDateTimeResult.getYearOfEra());
    assertEquals(292275056, ((DateTime) actualValue).getYearOfEra());
    assertEquals(2922789, toDateMidnightResult.getCenturyOfEra());
    assertEquals(292278993, weekyearResult2.getMaximumValue());
    assertEquals(292278993, yearResult2.getMaximumValue());
    assertEquals(292278993, weekyearResult.getMaximumValue());
    assertEquals(292278993, yearResult.getMaximumValue());
    assertEquals(292278993, weekyearResult2.getMaximumValueOverall());
    assertEquals(292278993, yearResult2.getMaximumValueOverall());
    assertEquals(292278993, weekyearResult.getMaximumValueOverall());
    assertEquals(292278993, yearResult.getMaximumValueOverall());
    assertEquals(292278994, toLocalDateTimeResult.getWeekyear());
    assertEquals(292278994, ((DateTime) actualValue).getWeekyear());
    assertEquals(292278994, toDateMidnightResult.getYear());
    assertEquals(292278994, toDateMidnightResult.getYearOfEra());
    assertEquals(292278994, yearResult2.get());
    assertEquals(292278994, weekyearResult.get());
    assertEquals(3, toYearMonthDayResult.size());
    DateTimeFieldType[] fieldTypes = toYearMonthDayResult.getFieldTypes();
    assertEquals(3, fieldTypes.length);
    DateTimeField[] fields2 = toYearMonthDayResult.getFields();
    assertEquals(3, fields2.length);
    assertEquals(4, toLocalDateTimeResult.getSecondOfMinute());
    assertEquals(4, toLocalDateTimeResult.size());
    assertEquals(4, ((DateTime) actualValue).getSecondOfMinute());
    DateTimeFieldType[] fieldTypes2 = toLocalDateTimeResult.getFieldTypes();
    assertEquals(4, fieldTypes2.length);
    assertEquals(4, fields.length);
    assertEquals(47, toLocalDateTimeResult.getMinuteOfHour());
    assertEquals(47, ((DateTime) actualValue).getMinuteOfHour());
    assertEquals(5, toLocalDateTimeResult.getMonthOfYear());
    assertEquals(5, toYearMonthDayResult.getMonthOfYear());
    assertEquals(5, ((DateTime) actualValue).getMonthOfYear());
    assertEquals(51, toDateMidnightResult.getSecondOfMinute());
    assertEquals(518400000L, weekyearResult2.remainder());
    assertEquals(51951, toDateMidnightResult.getSecondOfDay());
    assertEquals(51951616, toDateMidnightResult.getMillisOfDay());
    assertEquals(52, toLocalDateTimeResult.getWeekOfWeekyear());
    assertEquals(52, ((DateTime) actualValue).getWeekOfWeekyear());
    assertEquals(55, toLocalDateTimeResult.getYearOfCentury());
    assertEquals(55, ((DateTime) actualValue).getYearOfCentury());
    assertEquals(578824192L, weekyearResult.remainder());
    assertEquals(6, toLocalDateTimeResult.getDayOfWeek());
    assertEquals(6, toDateMidnightResult.getDayOfWeek());
    assertEquals(6, ((DateTime) actualValue).getDayOfWeek());
    assertEquals(60424, ((DateTime) actualValue).getSecondOfDay());
    assertEquals(60424192, toLocalDateTimeResult.getMillisOfDay());
    assertEquals(60424192, ((DateTime) actualValue).getMillisOfDay());
    assertEquals(616, toDateMidnightResult.getMillisOfSecond());
    assertEquals(8, toDateMidnightResult.getMonthOfYear());
    assertEquals(865, toDateMidnightResult.getMinuteOfDay());
    assertEquals(9223372036794351616L, toDateMidnightResult.getMillis());
    assertEquals(94, toDateMidnightResult.getYearOfCentury());
    assertFalse(eraResult2.isLeap());
    assertFalse(weekyearResult2.isLeap());
    assertFalse(yearResult2.isLeap());
    assertFalse(eraResult.isLeap());
    assertFalse(weekyearResult.isLeap());
    assertFalse(yearResult.isLeap());
    assertEquals(actualValue, ((DateTime) actualValue).toDateTimeISO());
    assertEquals(actualValue, toLocalDateTimeResult.toDateTime());
    assertEquals(actualValue, toYearMonthDayResult.toDateTimeAtMidnight());
    assertEquals(actualValue, ((DateTime) actualValue).toInstant());
    assertEquals(actualValue, ((DateTime) actualValue).toMutableDateTime());
    assertEquals(actualValue, ((DateTime) actualValue).toMutableDateTimeISO());
    assertEquals(eraResult, toLocalDateTimeResult.era());
    assertEquals(toDateMidnightResult, toDateMidnightResult.toDateTime());
    assertEquals(toDateMidnightResult, toDateMidnightResult.toDateTimeISO());
    assertEquals(toDateMidnightResult, toDateMidnightResult.toInstant());
    assertEquals(toDateMidnightResult, toDateMidnightResult.toMutableDateTime());
    assertEquals(toDateMidnightResult, toDateMidnightResult.toMutableDateTimeISO());
    assertEquals(toLocalDateResult, toDateMidnightResult.toYearMonthDay());
    assertEquals(toLocalTimeResult, ((DateTime) actualValue).toTimeOfDay());
    assertEquals(weekyearResult, toLocalDateTimeResult.weekyear());
    assertEquals(yearResult, toLocalDateTimeResult.year());
    assertEquals(Long.MIN_VALUE, ((DateTime) actualValue).getMillis());
    assertEquals(Short.SIZE, toLocalDateTimeResult.getDayOfMonth());
    assertEquals(Short.SIZE, toLocalDateTimeResult.getHourOfDay());
    assertEquals(Short.SIZE, toYearMonthDayResult.getDayOfMonth());
    assertEquals(Short.SIZE, toDateMidnightResult.getDayOfMonth());
    assertEquals(Short.SIZE, ((DateTime) actualValue).getDayOfMonth());
    assertEquals(Short.SIZE, ((DateTime) actualValue).getHourOfDay());
    assertSame(actualValue, ((DateTime) actualValue).toDateTime());
    assertSame(actualValue, eraResult.getDateTime());
    assertSame(actualValue, weekyearResult.getDateTime());
    assertSame(actualValue, yearResult.getDateTime());
    assertSame(toDateMidnightResult, eraResult2.getDateMidnight());
    assertSame(toDateMidnightResult, weekyearResult2.getDateMidnight());
    assertSame(toDateMidnightResult, yearResult2.getDateMidnight());
    assertSame(zone, chronology.getZone());
    assertSame(zone, toDateMidnightResult.getZone());
    assertSame(fieldType, eraResult2.getFieldType());
    assertSame(fields[1], fields2[1]);
    assertSame(fields[2], fields2[2]);
    assertSame(fieldTypes2[1], fieldTypes[1]);
    assertSame(fieldTypes2[2], fieldTypes[2]);
    assertSame(chronology, toLocalDateTimeResult.getChronology());
    assertSame(chronology, toDateMidnightResult.getChronology());
    assertSame(chronology, toYearMonthDayResult.getChronology());
    assertSame(leapDurationField, chronology.weeks());
    assertSame(leapDurationField, weekyearResult2.getLeapDurationField());
    assertSame(leapDurationField2, chronology.days());
    assertSame(leapDurationField2, yearResult2.getLeapDurationField());
    assertSame(durationField, chronology.eras());
    assertSame(durationField, eraResult2.getDurationField());
    assertArrayEquals(new int[]{-292275055, 5, Short.SIZE}, toYearMonthDayResult.getValues());
    assertArrayEquals(new int[]{-292275055, 5, Short.SIZE, 60424192}, toLocalDateTimeResult.getValues());
  }

  /**
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
