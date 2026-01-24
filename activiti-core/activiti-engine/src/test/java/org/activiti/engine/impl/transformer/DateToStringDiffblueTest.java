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
package org.activiti.engine.impl.transformer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.sql.Date;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.apache.commons.lang3.time.FastDateFormat;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DateToStringDiffblueTest {
  /**
   * Test new {@link DateToString} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link DateToString}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DateToString.<init>()"})
  public void testNewDateToString() {
    // Arrange and Act
    DateToString actualDateToString = new DateToString();
    Date date = new Date(1, 1, 1);
    Object actualTransformResult = actualDateToString.transform(date);

    // Assert
    Format format = actualDateToString.format;
    assertTrue(format instanceof FastDateFormat);
    assertEquals("01/02/1901", actualTransformResult);
    String actualFormatResult = new SimpleDateFormat("yyyy-MM-dd").format(date);
    assertEquals("1901-02-01", actualFormatResult);
    assertEquals("dd/MM/yyyy", ((FastDateFormat) format).getPattern());
    assertEquals(10, ((FastDateFormat) format).getMaxLengthEstimate());
  }

  /**
   * Test new {@link DateToString} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link DateToString}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DateToString.<init>()"})
  public void testNewDateToString2() {
    // Arrange and Act
    DateToString actualDateToString = new DateToString();
    Date date = new Date(Integer.MIN_VALUE, 1, 1);
    Object actualTransformResult = actualDateToString.transform(date);

    // Assert
    Format format = actualDateToString.format;
    assertTrue(format instanceof FastDateFormat);
    String actualFormatResult = new SimpleDateFormat("yyyy-MM-dd").format(date);
    assertEquals("190690352-01-21", actualFormatResult);
    assertEquals("21/01/190690352", actualTransformResult);
    assertEquals("dd/MM/yyyy", ((FastDateFormat) format).getPattern());
    assertEquals(10, ((FastDateFormat) format).getMaxLengthEstimate());
  }

  /**
   * Test new {@link DateToString} (default constructor).
   *
   * <ul>
   *   <li>Then {@link DateToString#format} return {@link FastDateFormat}.
   * </ul>
   *
   * <p>Method under test: default or parameterless constructor of {@link DateToString}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DateToString.<init>()"})
  public void testNewDateToString_thenFormatReturnFastDateFormat() {
    // Arrange, Act and Assert
    Format format = new DateToString().format;
    assertTrue(format instanceof FastDateFormat);
    assertEquals("dd/MM/yyyy", ((FastDateFormat) format).getPattern());
    assertEquals(10, ((FastDateFormat) format).getMaxLengthEstimate());
  }

  /**
   * Test new {@link DateToString} (default constructor).
   *
   * <ul>
   *   <li>Then {@link DateToString#format} return {@link FastDateFormat}.
   * </ul>
   *
   * <p>Method under test: default or parameterless constructor of {@link DateToString}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DateToString.<init>()"})
  public void testNewDateToString_thenFormatReturnFastDateFormat2() {
    // Arrange and Act
    DateToString actualDateToString = new DateToString();
    actualDateToString.transform(
        java.util.Date.from(
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Assert
    Format format = actualDateToString.format;
    assertTrue(format instanceof FastDateFormat);
    assertEquals("dd/MM/yyyy", ((FastDateFormat) format).getPattern());
    assertEquals(10, ((FastDateFormat) format).getMaxLengthEstimate());
  }

  /**
   * Test new {@link DateToString} (default constructor).
   *
   * <ul>
   *   <li>Then {@link DateToString#format} return {@link FastDateFormat}.
   * </ul>
   *
   * <p>Method under test: default or parameterless constructor of {@link DateToString}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DateToString.<init>()"})
  public void testNewDateToString_thenFormatReturnFastDateFormat3() {
    // Arrange and Act
    DateToString actualDateToString = new DateToString();
    actualDateToString.transform(new java.util.Date());

    // Assert
    Format format = actualDateToString.format;
    assertTrue(format instanceof FastDateFormat);
    assertEquals("dd/MM/yyyy", ((FastDateFormat) format).getPattern());
    assertEquals(10, ((FastDateFormat) format).getMaxLengthEstimate());
  }

  /**
   * Test new {@link DateToString} (default constructor).
   *
   * <ul>
   *   <li>Then {@link DateToString#format} return {@link FastDateFormat}.
   * </ul>
   *
   * <p>Method under test: default or parameterless constructor of {@link DateToString}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DateToString.<init>()"})
  public void testNewDateToString_thenFormatReturnFastDateFormat4() {
    // Arrange and Act
    DateToString actualDateToString = new DateToString();
    LocalDate ofYearDayResult = LocalDate.ofYearDay(1, 1);
    actualDateToString.transform(
        java.util.Date.from(ofYearDayResult.atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Assert
    Format format = actualDateToString.format;
    assertTrue(format instanceof FastDateFormat);
    assertEquals("dd/MM/yyyy", ((FastDateFormat) format).getPattern());
    assertEquals(10, ((FastDateFormat) format).getMaxLengthEstimate());
  }
}
