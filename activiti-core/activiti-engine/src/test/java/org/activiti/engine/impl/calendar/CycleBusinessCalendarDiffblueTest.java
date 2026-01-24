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
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.engine.runtime.ClockReader;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class CycleBusinessCalendarDiffblueTest {
  /**
   * Test {@link CycleBusinessCalendar#resolveDuedate(String, int)} with {@code duedateDescription},
   * {@code maxIterations}.
   *
   * <p>Method under test: {@link CycleBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Date CycleBusinessCalendar.resolveDuedate(String, int)"})
  public void testResolveDuedateWithDuedateDescriptionMaxIterations() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            new CycleBusinessCalendar(new DefaultClockImpl())
                .resolveDuedate("Minute and Second values must be between 0 and 59", 3));
  }

  /**
   * Test {@link CycleBusinessCalendar#resolveDuedate(String, int)} with {@code duedateDescription},
   * {@code maxIterations}.
   *
   * <ul>
   *   <li>When {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link CycleBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Date CycleBusinessCalendar.resolveDuedate(String, int)"})
  public void testResolveDuedateWithDuedateDescriptionMaxIterations_when42() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> new CycleBusinessCalendar(new DefaultClockImpl()).resolveDuedate("42", 3));
  }

  /**
   * Test {@link CycleBusinessCalendar#resolveDuedate(String, int)} with {@code duedateDescription},
   * {@code maxIterations}.
   *
   * <ul>
   *   <li>When {@code 2020-03-01}.
   * </ul>
   *
   * <p>Method under test: {@link CycleBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Date CycleBusinessCalendar.resolveDuedate(String, int)"})
  public void testResolveDuedateWithDuedateDescriptionMaxIterations_when20200301() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> new CycleBusinessCalendar(new DefaultClockImpl()).resolveDuedate("2020-03-01", 3));
  }

  /**
   * Test {@link CycleBusinessCalendar#resolveDuedate(String, int)} with {@code duedateDescription},
   * {@code maxIterations}.
   *
   * <ul>
   *   <li>When {@code 2020/03/01}.
   * </ul>
   *
   * <p>Method under test: {@link CycleBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Date CycleBusinessCalendar.resolveDuedate(String, int)"})
  public void testResolveDuedateWithDuedateDescriptionMaxIterations_when202003012() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> new CycleBusinessCalendar(new DefaultClockImpl()).resolveDuedate("2020/03/01", 3));
  }

  /**
   * Test {@link CycleBusinessCalendar#resolveDuedate(String, int)} with {@code duedateDescription},
   * {@code maxIterations}.
   *
   * <ul>
   *   <li>When {@code 20200301}.
   * </ul>
   *
   * <p>Method under test: {@link CycleBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Date CycleBusinessCalendar.resolveDuedate(String, int)"})
  public void testResolveDuedateWithDuedateDescriptionMaxIterations_when202003013() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> new CycleBusinessCalendar(new DefaultClockImpl()).resolveDuedate("20200301", 3));
  }

  /**
   * Test {@link CycleBusinessCalendar#resolveDuedate(String, int)} with {@code duedateDescription},
   * {@code maxIterations}.
   *
   * <ul>
   *   <li>When {@code ,}.
   * </ul>
   *
   * <p>Method under test: {@link CycleBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Date CycleBusinessCalendar.resolveDuedate(String, int)"})
  public void testResolveDuedateWithDuedateDescriptionMaxIterations_whenComma() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> new CycleBusinessCalendar(new DefaultClockImpl()).resolveDuedate(",", 3));
  }

  /**
   * Test {@link CycleBusinessCalendar#resolveDuedate(String, int)} with {@code duedateDescription},
   * {@code maxIterations}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link CycleBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Date CycleBusinessCalendar.resolveDuedate(String, int)"})
  public void testResolveDuedateWithDuedateDescriptionMaxIterations_whenNull() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> new CycleBusinessCalendar(new DefaultClockImpl()).resolveDuedate(null, 3));
  }

  /**
   * Test {@link CycleBusinessCalendar#resolveDuedate(String, int)} with {@code duedateDescription},
   * {@code maxIterations}.
   *
   * <ul>
   *   <li>When {@code /}.
   * </ul>
   *
   * <p>Method under test: {@link CycleBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Date CycleBusinessCalendar.resolveDuedate(String, int)"})
  public void testResolveDuedateWithDuedateDescriptionMaxIterations_whenSlash() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> new CycleBusinessCalendar(new DefaultClockImpl()).resolveDuedate("/", 3));
  }

  /**
   * Test {@link CycleBusinessCalendar#resolveDuedate(String, int)} with {@code duedateDescription},
   * {@code maxIterations}.
   *
   * <ul>
   *   <li>When space tab.
   * </ul>
   *
   * <p>Method under test: {@link CycleBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Date CycleBusinessCalendar.resolveDuedate(String, int)"})
  public void testResolveDuedateWithDuedateDescriptionMaxIterations_whenSpaceTab() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> new CycleBusinessCalendar(new DefaultClockImpl()).resolveDuedate(" \t", 3));
  }

  /**
   * Test {@link CycleBusinessCalendar#validateDuedate(String, int, Date, Date)}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link CycleBusinessCalendar#validateDuedate(String, int, Date, Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.lang.Boolean CycleBusinessCalendar.validateDuedate(String, int, Date, Date)"
  })
  public void testValidateDuedate_thenReturnFalse() {
    // Arrange
    CycleBusinessCalendar cycleBusinessCalendar = new CycleBusinessCalendar(new DefaultClockImpl());

    LocalDate ofYearDayResult = LocalDate.ofYearDay(1, 1);
    Date endDate = Date.from(ofYearDayResult.atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertFalse(
        cycleBusinessCalendar.validateDuedate(
            "2020-03-01",
            3,
            endDate,
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())));
  }

  /**
   * Test {@link CycleBusinessCalendar#validateDuedate(String, int, Date, Date)}.
   *
   * <ul>
   *   <li>When {@code 2020-03-01}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link CycleBusinessCalendar#validateDuedate(String, int, Date, Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.lang.Boolean CycleBusinessCalendar.validateDuedate(String, int, Date, Date)"
  })
  public void testValidateDuedate_when20200301_thenReturnTrue() {
    // Arrange
    CycleBusinessCalendar cycleBusinessCalendar = new CycleBusinessCalendar(new DefaultClockImpl());
    Date endDate =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertTrue(
        cycleBusinessCalendar.validateDuedate(
            "2020-03-01",
            3,
            endDate,
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())));
  }

  /**
   * Test {@link CycleBusinessCalendar#validateDuedate(String, int, Date, Date)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link CycleBusinessCalendar#validateDuedate(String, int, Date, Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.lang.Boolean CycleBusinessCalendar.validateDuedate(String, int, Date, Date)"
  })
  public void testValidateDuedate_whenEmptyString_thenReturnTrue() {
    // Arrange
    CycleBusinessCalendar cycleBusinessCalendar = new CycleBusinessCalendar(new DefaultClockImpl());

    // Act and Assert
    assertTrue(
        cycleBusinessCalendar.validateDuedate(
            "",
            3,
            null,
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())));
  }

  /**
   * Test {@link CycleBusinessCalendar#validateDuedate(String, int, Date, Date)}.
   *
   * <ul>
   *   <li>When from now atStartOfDay atZone {@link ZoneOffset#UTC} toInstant.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link CycleBusinessCalendar#validateDuedate(String, int, Date, Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.lang.Boolean CycleBusinessCalendar.validateDuedate(String, int, Date, Date)"
  })
  public void testValidateDuedate_whenFromNowAtStartOfDayAtZoneUtcToInstant_thenReturnTrue() {
    // Arrange
    CycleBusinessCalendar cycleBusinessCalendar = new CycleBusinessCalendar(new DefaultClockImpl());
    Date endDate = Date.from(LocalDate.now().atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertTrue(
        cycleBusinessCalendar.validateDuedate(
            "2020-03-01",
            3,
            endDate,
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())));
  }

  /**
   * Test {@link CycleBusinessCalendar#validateDuedate(String, int, Date, Date)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link CycleBusinessCalendar#validateDuedate(String, int, Date, Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.lang.Boolean CycleBusinessCalendar.validateDuedate(String, int, Date, Date)"
  })
  public void testValidateDuedate_whenNull_thenReturnTrue() {
    // Arrange
    CycleBusinessCalendar cycleBusinessCalendar = new CycleBusinessCalendar(new DefaultClockImpl());

    // Act and Assert
    assertTrue(
        cycleBusinessCalendar.validateDuedate(
            null,
            3,
            null,
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())));
  }
}
