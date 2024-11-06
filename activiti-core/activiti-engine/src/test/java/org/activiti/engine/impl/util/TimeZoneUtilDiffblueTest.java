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
package org.activiti.engine.impl.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import org.junit.Test;

public class TimeZoneUtilDiffblueTest {
  /**
   * Test {@link TimeZoneUtil#convertToTimeZone(Calendar, TimeZone)}.
   * <ul>
   *   <li>Then return {@link GregorianCalendar}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TimeZoneUtil#convertToTimeZone(Calendar, TimeZone)}
   */
  @Test
  public void testConvertToTimeZone_thenReturnGregorianCalendar() {
    // Arrange
    GregorianCalendar time = new GregorianCalendar(1, 1, 1);

    TimeZone timeZone = TimeZone.getTimeZone("America/Los_Angeles");

    // Act
    Calendar actualConvertToTimeZoneResult = TimeZoneUtil.convertToTimeZone(time, timeZone);

    // Assert
    assertTrue(actualConvertToTimeZoneResult instanceof GregorianCalendar);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    assertEquals("0001-02-01", simpleDateFormat.format(actualConvertToTimeZoneResult.getTime()));
    assertEquals("gregory", actualConvertToTimeZoneResult.getCalendarType());
    assertEquals(1, actualConvertToTimeZoneResult.getWeekYear());
    assertEquals(2, actualConvertToTimeZoneResult.getFirstDayOfWeek());
    assertEquals(4, actualConvertToTimeZoneResult.getMinimalDaysInFirstWeek());
    assertEquals(52, actualConvertToTimeZoneResult.getWeeksInWeekYear());
    assertTrue(actualConvertToTimeZoneResult.isLenient());
    assertTrue(actualConvertToTimeZoneResult.isWeekDateSupported());
    assertSame(timeZone, actualConvertToTimeZoneResult.getTimeZone());
  }
}
