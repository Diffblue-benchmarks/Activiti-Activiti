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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.TimeZone;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class BusinessCalendarImplDiffblueTest {
  /**
   * Test {@link BusinessCalendarImpl#validateDuedate(String, int, Date, Date)}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BusinessCalendarImpl#validateDuedate(String, int, Date, Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.lang.Boolean BusinessCalendarImpl.validateDuedate(String, int, Date, Date)"
  })
  public void testValidateDuedate_thenReturnFalse() {
    // Arrange
    AdvancedCycleBusinessCalendar advancedCycleBusinessCalendar =
        new AdvancedCycleBusinessCalendar(new DefaultClockImpl());

    LocalDate ofYearDayResult = LocalDate.ofYearDay(1, 1);
    Date endDate = Date.from(ofYearDayResult.atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertFalse(
        advancedCycleBusinessCalendar.validateDuedate(
            "2020-03-01",
            3,
            endDate,
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())));
  }

  /**
   * Test {@link BusinessCalendarImpl#validateDuedate(String, int, Date, Date)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BusinessCalendarImpl#validateDuedate(String, int, Date, Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.lang.Boolean BusinessCalendarImpl.validateDuedate(String, int, Date, Date)"
  })
  public void testValidateDuedate_thenReturnTrue() {
    // Arrange
    AdvancedCycleBusinessCalendar advancedCycleBusinessCalendar =
        new AdvancedCycleBusinessCalendar(new DefaultClockImpl());
    Date endDate =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertTrue(
        advancedCycleBusinessCalendar.validateDuedate(
            "2020-03-01",
            3,
            endDate,
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())));
  }

  /**
   * Test {@link BusinessCalendarImpl#validateDuedate(String, int, Date, Date)}.
   *
   * <ul>
   *   <li>When from now atStartOfDay atZone {@link ZoneOffset#UTC} toInstant.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BusinessCalendarImpl#validateDuedate(String, int, Date, Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.lang.Boolean BusinessCalendarImpl.validateDuedate(String, int, Date, Date)"
  })
  public void testValidateDuedate_whenFromNowAtStartOfDayAtZoneUtcToInstant_thenReturnTrue() {
    // Arrange
    AdvancedCycleBusinessCalendar advancedCycleBusinessCalendar =
        new AdvancedCycleBusinessCalendar(new DefaultClockImpl());
    Date endDate = Date.from(LocalDate.now().atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertTrue(
        advancedCycleBusinessCalendar.validateDuedate(
            "2020-03-01",
            3,
            endDate,
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())));
  }

  /**
   * Test {@link BusinessCalendarImpl#resolveEndDate(String)}.
   *
   * <p>Method under test: {@link BusinessCalendarImpl#resolveEndDate(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Date BusinessCalendarImpl.resolveEndDate(String)"})
  public void testResolveEndDate() {
    // Arrange
    DefaultClockImpl clockReader = mock(DefaultClockImpl.class);
    when(clockReader.getCurrentTimeZone()).thenReturn(TimeZone.getTimeZone("America/Los_Angeles"));

    AdvancedCycleBusinessCalendar advancedCycleBusinessCalendar =
        new AdvancedCycleBusinessCalendar(clockReader);
    advancedCycleBusinessCalendar.setDefaultScheduleVersion(1);

    // Act
    advancedCycleBusinessCalendar.resolveEndDate("2020-03-01");

    // Assert
    verify(clockReader).getCurrentTimeZone();
  }

  /**
   * Test {@link BusinessCalendarImpl#resolveEndDate(String)}.
   *
   * <ul>
   *   <li>Given {@link DefaultClockImpl} {@link DefaultClockImpl#getCurrentTimeZone()} return
   *       {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BusinessCalendarImpl#resolveEndDate(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Date BusinessCalendarImpl.resolveEndDate(String)"})
  public void testResolveEndDate_givenDefaultClockImplGetCurrentTimeZoneReturnNull() {
    // Arrange
    DefaultClockImpl clockReader = mock(DefaultClockImpl.class);
    when(clockReader.getCurrentTimeZone()).thenReturn(null);

    // Act
    new AdvancedCycleBusinessCalendar(clockReader).resolveEndDate("2020-03-01");

    // Assert
    verify(clockReader).getCurrentTimeZone();
  }

  /**
   * Test {@link BusinessCalendarImpl#resolveEndDate(String)}.
   *
   * <ul>
   *   <li>Given {@link DefaultClockImpl} {@link DefaultClockImpl#getCurrentTimeZone()} return
   *       TimeZone is {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link BusinessCalendarImpl#resolveEndDate(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Date BusinessCalendarImpl.resolveEndDate(String)"})
  public void testResolveEndDate_givenDefaultClockImplGetCurrentTimeZoneReturnTimeZoneIsFoo() {
    // Arrange
    DefaultClockImpl clockReader = mock(DefaultClockImpl.class);
    when(clockReader.getCurrentTimeZone()).thenReturn(TimeZone.getTimeZone("foo"));

    // Act
    new AdvancedCycleBusinessCalendar(clockReader).resolveEndDate("2020-03-01");

    // Assert
    verify(clockReader).getCurrentTimeZone();
  }

  /**
   * Test {@link BusinessCalendarImpl#resolveEndDate(String)}.
   *
   * <ul>
   *   <li>Then calls {@link DefaultClockImpl#getCurrentTimeZone()}.
   * </ul>
   *
   * <p>Method under test: {@link BusinessCalendarImpl#resolveEndDate(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Date BusinessCalendarImpl.resolveEndDate(String)"})
  public void testResolveEndDate_thenCallsGetCurrentTimeZone() {
    // Arrange
    DefaultClockImpl clockReader = mock(DefaultClockImpl.class);
    when(clockReader.getCurrentTimeZone()).thenReturn(TimeZone.getTimeZone("America/Los_Angeles"));

    // Act
    new AdvancedCycleBusinessCalendar(clockReader).resolveEndDate("2020-03-01");

    // Assert
    verify(clockReader).getCurrentTimeZone();
  }

  /**
   * Test {@link BusinessCalendarImpl#resolveEndDate(String)}.
   *
   * <ul>
   *   <li>Then return {@link SimpleDateFormat#SimpleDateFormat(String)} with {@code yyyy-MM-dd}
   *       format is {@code 2020-03-01}.
   * </ul>
   *
   * <p>Method under test: {@link BusinessCalendarImpl#resolveEndDate(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Date BusinessCalendarImpl.resolveEndDate(String)"})
  public void testResolveEndDate_thenReturnSimpleDateFormatWithYyyyMmDdFormatIs20200301() {
    // Arrange and Act
    Date actualResolveEndDateResult =
        new AdvancedCycleBusinessCalendar(new DefaultClockImpl()).resolveEndDate("2020-03-01");

    // Assert
    String actualFormatResult =
        new SimpleDateFormat("yyyy-MM-dd").format(actualResolveEndDateResult);
    assertEquals("2020-03-01", actualFormatResult);
  }
}
