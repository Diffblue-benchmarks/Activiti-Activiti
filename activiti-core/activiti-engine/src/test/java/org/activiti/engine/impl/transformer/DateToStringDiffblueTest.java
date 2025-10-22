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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.sql.Date;
import java.text.Format;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.apache.commons.lang3.time.FastDateFormat;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DateToStringDiffblueTest {
  /**
   * Test new {@link DateToString} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link DateToString}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DateToString.<init>()"})
  public void testNewDateToString() {
    // Arrange and Act
    DateToString actualDateToString = new DateToString();
    LocalDateTime atStartOfDayResult = LocalDate.ofYearDay(1, 1).atStartOfDay();

    // Assert
    Format format = actualDateToString.format;
    assertTrue(format instanceof FastDateFormat);
    assertEquals("02/01/0001", actualDateToString
        .transform(java.util.Date.from(atStartOfDayResult.atZone(ZoneOffset.ofTotalSeconds(1)).toInstant())));
    assertEquals("dd/MM/yyyy", ((FastDateFormat) format).getPattern());
    assertEquals(10, ((FastDateFormat) format).getMaxLengthEstimate());
  }

  /**
   * Test new {@link DateToString} (default constructor).
   * <ul>
   *   <li>Then {@link DateToString#format} return {@link FastDateFormat}.</li>
   * </ul>
   * <p>
   * Method under test: default or parameterless constructor of {@link DateToString}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DateToString.<init>()"})
  public void testNewDateToString_thenFormatReturnFastDateFormat() {
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
   * <ul>
   *   <li>Then return transform {@link Date} is {@code 01/01/1970}.</li>
   * </ul>
   * <p>
   * Method under test: default or parameterless constructor of {@link DateToString}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DateToString.<init>()"})
  public void testNewDateToString_thenReturnTransformDateIs01011970() {
    // Arrange and Act
    DateToString actualDateToString = new DateToString();
    Date date = mock(Date.class);
    when(date.getTime()).thenReturn(10L);
    Object actualTransformResult = actualDateToString.transform(date);

    // Assert
    verify(date).getTime();
    Format format = actualDateToString.format;
    assertTrue(format instanceof FastDateFormat);
    assertEquals("01/01/1970", actualTransformResult);
    assertEquals("dd/MM/yyyy", ((FastDateFormat) format).getPattern());
    assertEquals(10, ((FastDateFormat) format).getMaxLengthEstimate());
  }

  /**
   * Test new {@link DateToString} (default constructor).
   * <ul>
   *   <li>Then return transform {@link Date} is {@code 17/08/292278994}.</li>
   * </ul>
   * <p>
   * Method under test: default or parameterless constructor of {@link DateToString}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DateToString.<init>()"})
  public void testNewDateToString_thenReturnTransformDateIs1708292278994() {
    // Arrange and Act
    DateToString actualDateToString = new DateToString();
    Date date = mock(Date.class);
    when(date.getTime()).thenReturn(Long.MAX_VALUE);
    Object actualTransformResult = actualDateToString.transform(date);

    // Assert
    verify(date).getTime();
    Format format = actualDateToString.format;
    assertTrue(format instanceof FastDateFormat);
    assertEquals("17/08/292278994", actualTransformResult);
    assertEquals("dd/MM/yyyy", ((FastDateFormat) format).getPattern());
    assertEquals(10, ((FastDateFormat) format).getMaxLengthEstimate());
  }
}
