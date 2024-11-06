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
package org.activiti.engine.impl.calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.TimeZone;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.engine.runtime.ClockReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BusinessCalendarImplDiffblueTest {
  @Mock
  private ClockReader clockReader;

  @InjectMocks
  private DueDateBusinessCalendar dueDateBusinessCalendar;

  /**
   * Test {@link BusinessCalendarImpl#validateDuedate(String, int, Date, Date)}.
   * <ul>
   *   <li>Given {@code true}.</li>
   *   <li>When {@link java.sql.Date} {@link java.util.Date#after(Date)} return
   * {@code true}.</li>
   *   <li>Then calls {@link java.util.Date#after(Date)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BusinessCalendarImpl#validateDuedate(String, int, java.util.Date, java.util.Date)}
   */
  @Test
  public void testValidateDuedate_givenTrue_whenDateAfterReturnTrue_thenCallsAfter() {
    // Arrange
    java.sql.Date endDate = mock(java.sql.Date.class);
    when(endDate.after(Mockito.<java.util.Date>any())).thenReturn(true);

    // Act
    Boolean actualValidateDuedateResult = dueDateBusinessCalendar.validateDuedate("2020-03-01", 3, endDate,
        java.util.Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Assert
    verify(endDate).after(isA(java.util.Date.class));
    assertTrue(actualValidateDuedateResult);
  }

  /**
   * Test {@link BusinessCalendarImpl#validateDuedate(String, int, Date, Date)}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BusinessCalendarImpl#validateDuedate(String, int, Date, Date)}
   */
  @Test
  public void testValidateDuedate_thenReturnFalse() {
    // Arrange
    Date endDate = Date.from(LocalDate.ofYearDay(1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertFalse(dueDateBusinessCalendar.validateDuedate("2020-03-01", 3, endDate,
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())));
  }

  /**
   * Test {@link BusinessCalendarImpl#validateDuedate(String, int, Date, Date)}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BusinessCalendarImpl#validateDuedate(String, int, Date, Date)}
   */
  @Test
  public void testValidateDuedate_thenReturnTrue() {
    // Arrange
    Date endDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertTrue(dueDateBusinessCalendar.validateDuedate("2020-03-01", 3, endDate,
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())));
  }

  /**
   * Test {@link BusinessCalendarImpl#validateDuedate(String, int, Date, Date)}.
   * <ul>
   *   <li>When from now atStartOfDay atZone {@link ZoneOffset#UTC} toInstant.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BusinessCalendarImpl#validateDuedate(String, int, Date, Date)}
   */
  @Test
  public void testValidateDuedate_whenFromNowAtStartOfDayAtZoneUtcToInstant_thenReturnTrue() {
    // Arrange
    Date endDate = Date.from(LocalDate.now().atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertTrue(dueDateBusinessCalendar.validateDuedate("2020-03-01", 3, endDate,
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())));
  }

  /**
   * Test {@link BusinessCalendarImpl#resolveEndDate(String)}.
   * <ul>
   *   <li>Given {@link DefaultClockImpl}
   * {@link DefaultClockImpl#getCurrentTimeZone()} return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BusinessCalendarImpl#resolveEndDate(String)}
   */
  @Test
  public void testResolveEndDate_givenDefaultClockImplGetCurrentTimeZoneReturnNull() {
    // Arrange
    DefaultClockImpl clockReader = mock(DefaultClockImpl.class);
    when(clockReader.getCurrentTimeZone()).thenReturn(null);

    // Act
    (new AdvancedCycleBusinessCalendar(clockReader)).resolveEndDate("2020-03-01");

    // Assert
    verify(clockReader).getCurrentTimeZone();
  }

  /**
   * Test {@link BusinessCalendarImpl#resolveEndDate(String)}.
   * <ul>
   *   <li>Given {@link DefaultClockImpl}
   * {@link DefaultClockImpl#getCurrentTimeZone()} return TimeZone is
   * {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BusinessCalendarImpl#resolveEndDate(String)}
   */
  @Test
  public void testResolveEndDate_givenDefaultClockImplGetCurrentTimeZoneReturnTimeZoneIsFoo() {
    // Arrange
    DefaultClockImpl clockReader = mock(DefaultClockImpl.class);
    when(clockReader.getCurrentTimeZone()).thenReturn(TimeZone.getTimeZone("foo"));

    // Act
    (new AdvancedCycleBusinessCalendar(clockReader)).resolveEndDate("2020-03-01");

    // Assert
    verify(clockReader).getCurrentTimeZone();
  }

  /**
   * Test {@link BusinessCalendarImpl#resolveEndDate(String)}.
   * <ul>
   *   <li>Then calls {@link DefaultClockImpl#getCurrentTimeZone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BusinessCalendarImpl#resolveEndDate(String)}
   */
  @Test
  public void testResolveEndDate_thenCallsGetCurrentTimeZone() {
    // Arrange
    DefaultClockImpl clockReader = mock(DefaultClockImpl.class);
    when(clockReader.getCurrentTimeZone()).thenReturn(TimeZone.getTimeZone("America/Los_Angeles"));

    // Act
    (new AdvancedCycleBusinessCalendar(clockReader)).resolveEndDate("2020-03-01");

    // Assert
    verify(clockReader).getCurrentTimeZone();
  }

  /**
   * Test {@link BusinessCalendarImpl#resolveEndDate(String)}.
   * <ul>
   *   <li>Then return {@link SimpleDateFormat#SimpleDateFormat(String)} with
   * {@code yyyy-MM-dd} format is {@code 2020-03-01}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BusinessCalendarImpl#resolveEndDate(String)}
   */
  @Test
  public void testResolveEndDate_thenReturnSimpleDateFormatWithYyyyMmDdFormatIs20200301() {
    // Arrange and Act
    Date actualResolveEndDateResult = (new AdvancedCycleBusinessCalendar(new DefaultClockImpl()))
        .resolveEndDate("2020-03-01");

    // Assert
    assertEquals("2020-03-01", (new SimpleDateFormat("yyyy-MM-dd")).format(actualResolveEndDateResult));
  }
}
