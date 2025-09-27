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

import static org.junit.Assert.assertThrows;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.text.ParseException;
import java.util.TimeZone;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.engine.runtime.ClockReader;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class CronExpressionDiffblueTest {
  /**
   * Test {@link CronExpression#CronExpression(String, ClockReader)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then throw {@link ParseException}.
   * </ul>
   *
   * <p>Method under test: {@link CronExpression#CronExpression(String, ClockReader)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CronExpression.<init>(String, ClockReader)"})
  public void testNewCronExpression_when42_thenThrowParseException() throws ParseException {
    // Arrange, Act and Assert
    assertThrows(ParseException.class, () -> new CronExpression("42", new DefaultClockImpl()));
  }

  /**
   * Test {@link CronExpression#CronExpression(String, ClockReader, TimeZone)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then throw {@link ParseException}.
   * </ul>
   *
   * <p>Method under test: {@link CronExpression#CronExpression(String, ClockReader, TimeZone)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CronExpression.<init>(String, ClockReader, TimeZone)"})
  public void testNewCronExpression_when42_thenThrowParseException2() throws ParseException {
    // Arrange, Act and Assert
    assertThrows(
        ParseException.class,
        () ->
            new CronExpression(
                "42", new DefaultClockImpl(), TimeZone.getTimeZone("America/Los_Angeles")));
  }

  /**
   * Test {@link CronExpression#CronExpression(String, ClockReader)}.
   *
   * <ul>
   *   <li>When {@code Cron Expression}.
   *   <li>Then throw {@link ParseException}.
   * </ul>
   *
   * <p>Method under test: {@link CronExpression#CronExpression(String, ClockReader)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CronExpression.<init>(String, ClockReader)"})
  public void testNewCronExpression_whenCronExpression_thenThrowParseException()
      throws ParseException {
    // Arrange, Act and Assert
    assertThrows(
        ParseException.class, () -> new CronExpression("Cron Expression", new DefaultClockImpl()));
  }

  /**
   * Test {@link CronExpression#CronExpression(String, ClockReader, TimeZone)}.
   *
   * <ul>
   *   <li>When {@code Cron Expression}.
   *   <li>Then throw {@link ParseException}.
   * </ul>
   *
   * <p>Method under test: {@link CronExpression#CronExpression(String, ClockReader, TimeZone)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CronExpression.<init>(String, ClockReader, TimeZone)"})
  public void testNewCronExpression_whenCronExpression_thenThrowParseException2()
      throws ParseException {
    // Arrange, Act and Assert
    assertThrows(
        ParseException.class,
        () ->
            new CronExpression(
                "Cron Expression",
                new DefaultClockImpl(),
                TimeZone.getTimeZone("America/Los_Angeles")));
  }

  /**
   * Test {@link CronExpression#CronExpression(String, ClockReader)}.
   *
   * <ul>
   *   <li>When {@code L-9}.
   *   <li>Then throw {@link ParseException}.
   * </ul>
   *
   * <p>Method under test: {@link CronExpression#CronExpression(String, ClockReader)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CronExpression.<init>(String, ClockReader)"})
  public void testNewCronExpression_whenL9_thenThrowParseException() throws ParseException {
    // Arrange, Act and Assert
    assertThrows(ParseException.class, () -> new CronExpression("L-9", new DefaultClockImpl()));
  }

  /**
   * Test {@link CronExpression#CronExpression(String, ClockReader, TimeZone)}.
   *
   * <ul>
   *   <li>When {@code L-9}.
   *   <li>Then throw {@link ParseException}.
   * </ul>
   *
   * <p>Method under test: {@link CronExpression#CronExpression(String, ClockReader, TimeZone)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CronExpression.<init>(String, ClockReader, TimeZone)"})
  public void testNewCronExpression_whenL9_thenThrowParseException2() throws ParseException {
    // Arrange, Act and Assert
    assertThrows(
        ParseException.class,
        () ->
            new CronExpression(
                "L-9", new DefaultClockImpl(), TimeZone.getTimeZone("America/Los_Angeles")));
  }

  /**
   * Test {@link CronExpression#CronExpression(String, ClockReader)}.
   *
   * <ul>
   *   <li>When {@code ^L-[0-9]*[W]?}.
   *   <li>Then throw {@link ParseException}.
   * </ul>
   *
   * <p>Method under test: {@link CronExpression#CronExpression(String, ClockReader)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CronExpression.<init>(String, ClockReader)"})
  public void testNewCronExpression_whenL09W_thenThrowParseException() throws ParseException {
    // Arrange, Act and Assert
    assertThrows(
        ParseException.class, () -> new CronExpression("^L-[0-9]*[W]?", new DefaultClockImpl()));
  }

  /**
   * Test {@link CronExpression#CronExpression(String, ClockReader, TimeZone)}.
   *
   * <ul>
   *   <li>When {@code ^L-[0-9]*[W]?}.
   *   <li>Then throw {@link ParseException}.
   * </ul>
   *
   * <p>Method under test: {@link CronExpression#CronExpression(String, ClockReader, TimeZone)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CronExpression.<init>(String, ClockReader, TimeZone)"})
  public void testNewCronExpression_whenL09W_thenThrowParseException2() throws ParseException {
    // Arrange, Act and Assert
    assertThrows(
        ParseException.class,
        () ->
            new CronExpression(
                "^L-[0-9]*[W]?",
                new DefaultClockImpl(),
                TimeZone.getTimeZone("America/Los_Angeles")));
  }

  /**
   * Test {@link CronExpression#CronExpression(String, ClockReader)}.
   *
   * <ul>
   *   <li>When {@code L}.
   *   <li>Then throw {@link ParseException}.
   * </ul>
   *
   * <p>Method under test: {@link CronExpression#CronExpression(String, ClockReader)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CronExpression.<init>(String, ClockReader)"})
  public void testNewCronExpression_whenL_thenThrowParseException() throws ParseException {
    // Arrange, Act and Assert
    assertThrows(ParseException.class, () -> new CronExpression("L", new DefaultClockImpl()));
  }

  /**
   * Test {@link CronExpression#CronExpression(String, ClockReader, TimeZone)}.
   *
   * <ul>
   *   <li>When {@code L}.
   *   <li>Then throw {@link ParseException}.
   * </ul>
   *
   * <p>Method under test: {@link CronExpression#CronExpression(String, ClockReader, TimeZone)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CronExpression.<init>(String, ClockReader, TimeZone)"})
  public void testNewCronExpression_whenL_thenThrowParseException2() throws ParseException {
    // Arrange, Act and Assert
    assertThrows(
        ParseException.class,
        () ->
            new CronExpression(
                "L", new DefaultClockImpl(), TimeZone.getTimeZone("America/Los_Angeles")));
  }

  /**
   * Test {@link CronExpression#CronExpression(String, ClockReader)}.
   *
   * <ul>
   *   <li>When {@code LW}.
   *   <li>Then throw {@link ParseException}.
   * </ul>
   *
   * <p>Method under test: {@link CronExpression#CronExpression(String, ClockReader)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CronExpression.<init>(String, ClockReader)"})
  public void testNewCronExpression_whenLw_thenThrowParseException() throws ParseException {
    // Arrange, Act and Assert
    assertThrows(ParseException.class, () -> new CronExpression("LW", new DefaultClockImpl()));
  }

  /**
   * Test {@link CronExpression#CronExpression(String, ClockReader, TimeZone)}.
   *
   * <ul>
   *   <li>When {@code LW}.
   *   <li>Then throw {@link ParseException}.
   * </ul>
   *
   * <p>Method under test: {@link CronExpression#CronExpression(String, ClockReader, TimeZone)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CronExpression.<init>(String, ClockReader, TimeZone)"})
  public void testNewCronExpression_whenLw_thenThrowParseException2() throws ParseException {
    // Arrange, Act and Assert
    assertThrows(
        ParseException.class,
        () ->
            new CronExpression(
                "LW", new DefaultClockImpl(), TimeZone.getTimeZone("America/Los_Angeles")));
  }

  /**
   * Test {@link CronExpression#CronExpression(String, ClockReader)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link CronExpression#CronExpression(String, ClockReader)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CronExpression.<init>(String, ClockReader)"})
  public void testNewCronExpression_whenNull_thenThrowIllegalArgumentException()
      throws ParseException {
    // Arrange, Act and Assert
    assertThrows(
        IllegalArgumentException.class, () -> new CronExpression(null, new DefaultClockImpl()));
  }

  /**
   * Test {@link CronExpression#CronExpression(String, ClockReader, TimeZone)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link CronExpression#CronExpression(String, ClockReader, TimeZone)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void CronExpression.<init>(String, ClockReader, TimeZone)"})
  public void testNewCronExpression_whenNull_thenThrowIllegalArgumentException2()
      throws ParseException {
    // Arrange, Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () ->
            new CronExpression(
                null, new DefaultClockImpl(), TimeZone.getTimeZone("America/Los_Angeles")));
  }
}
