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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.sql.Date;
import java.text.Format;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;
import org.apache.commons.lang3.time.FastDateFormat;
import org.junit.Test;

public class DateToStringDiffblueTest {
  /**
   * Method under test: default or parameterless constructor of
   * {@link DateToString}
   */
  @Test
  public void testNewDateToString() throws MissingResourceException {
    // Arrange and Act
    DateToString actualDateToString = new DateToString();
    Date date = mock(Date.class);
    when(date.getTime()).thenReturn(10L);
    Object actualTransformResult = actualDateToString.transform(date);

    // Assert
    verify(date).getTime();
    Format format = actualDateToString.format;
    assertTrue(format instanceof FastDateFormat);
    Locale locale = ((FastDateFormat) format).getLocale();
    assertEquals("", locale.getCountry());
    assertEquals("", locale.getDisplayCountry());
    assertEquals("", locale.getDisplayScript());
    assertEquals("", locale.getDisplayVariant());
    assertEquals("", locale.getISO3Country());
    assertEquals("", locale.getScript());
    assertEquals("", locale.getVariant());
    assertEquals("01/01/1970", actualTransformResult);
    assertEquals("English", locale.getDisplayLanguage());
    assertEquals("English", locale.getDisplayName());
    assertEquals("dd/MM/yyyy", ((FastDateFormat) format).getPattern());
    assertEquals("en", locale.getLanguage());
    assertEquals("eng", locale.getISO3Language());
    assertEquals(0, ((FastDateFormat) format).getTimeZone().getDSTSavings());
    assertEquals(10, ((FastDateFormat) format).getMaxLengthEstimate());
    assertFalse(locale.hasExtensions());
    Set<Character> extensionKeys = locale.getExtensionKeys();
    assertTrue(extensionKeys.isEmpty());
    assertSame(extensionKeys, locale.getUnicodeLocaleAttributes());
    assertSame(extensionKeys, locale.getUnicodeLocaleKeys());
  }

  /**
   * Method under test: {@link DateToString#primTransform(Object)}
   */
  @Test
  public void testPrimTransform() throws Exception {
    // Arrange
    DateToString dateToString = new DateToString();
    Date date = mock(Date.class);
    when(date.getTime()).thenReturn(10L);

    // Act
    dateToString.primTransform(date);

    // Assert
    verify(date).getTime();
  }

  /**
   * Method under test: {@link DateToString#primTransform(Object)}
   */
  @Test
  public void testPrimTransform2() throws Exception {
    // Arrange
    DateToString dateToString = new DateToString();
    Date date = mock(Date.class);
    when(date.getTime()).thenReturn(Long.MAX_VALUE);

    // Act
    dateToString.primTransform(date);

    // Assert
    verify(date).getTime();
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link DateToString}
   */
  @Test
  public void testNewDateToString2() throws MissingResourceException {
    // Arrange and Act
    DateToString actualDateToString = new DateToString();
    Date date = mock(Date.class);
    when(date.getTime()).thenReturn(Long.MAX_VALUE);
    Object actualTransformResult = actualDateToString.transform(date);

    // Assert
    verify(date).getTime();
    Format format = actualDateToString.format;
    assertTrue(format instanceof FastDateFormat);
    Locale locale = ((FastDateFormat) format).getLocale();
    assertEquals("", locale.getCountry());
    assertEquals("", locale.getDisplayCountry());
    assertEquals("", locale.getDisplayScript());
    assertEquals("", locale.getDisplayVariant());
    assertEquals("", locale.getISO3Country());
    assertEquals("", locale.getScript());
    assertEquals("", locale.getVariant());
    assertEquals("17/08/292278994", actualTransformResult);
    assertEquals("English", locale.getDisplayLanguage());
    assertEquals("English", locale.getDisplayName());
    assertEquals("dd/MM/yyyy", ((FastDateFormat) format).getPattern());
    assertEquals("en", locale.getLanguage());
    assertEquals("eng", locale.getISO3Language());
    assertEquals(0, ((FastDateFormat) format).getTimeZone().getDSTSavings());
    assertEquals(10, ((FastDateFormat) format).getMaxLengthEstimate());
    assertFalse(locale.hasExtensions());
    Set<Character> extensionKeys = locale.getExtensionKeys();
    assertTrue(extensionKeys.isEmpty());
    assertSame(extensionKeys, locale.getUnicodeLocaleAttributes());
    assertSame(extensionKeys, locale.getUnicodeLocaleKeys());
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link DateToString}
   */
  @Test
  public void testNewDateToString3() throws MissingResourceException {
    // Arrange and Act
    DateToString actualDateToString = new DateToString();
    actualDateToString.transform(new java.util.Date());

    // Assert
    Format format = actualDateToString.format;
    assertTrue(format instanceof FastDateFormat);
    Locale locale = ((FastDateFormat) format).getLocale();
    assertEquals("", locale.getCountry());
    assertEquals("", locale.getDisplayCountry());
    assertEquals("", locale.getDisplayScript());
    assertEquals("", locale.getDisplayVariant());
    assertEquals("", locale.getISO3Country());
    assertEquals("", locale.getScript());
    assertEquals("", locale.getVariant());
    assertEquals("English", locale.getDisplayLanguage());
    assertEquals("English", locale.getDisplayName());
    assertEquals("dd/MM/yyyy", ((FastDateFormat) format).getPattern());
    assertEquals("en", locale.getLanguage());
    assertEquals("eng", locale.getISO3Language());
    assertEquals(0, ((FastDateFormat) format).getTimeZone().getDSTSavings());
    assertEquals(10, ((FastDateFormat) format).getMaxLengthEstimate());
    assertFalse(locale.hasExtensions());
    Set<Character> extensionKeys = locale.getExtensionKeys();
    assertTrue(extensionKeys.isEmpty());
    assertSame(extensionKeys, locale.getUnicodeLocaleAttributes());
    assertSame(extensionKeys, locale.getUnicodeLocaleKeys());
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link DateToString}
   */
  @Test
  public void testNewDateToString4() throws MissingResourceException {
    // Arrange and Act
    DateToString actualDateToString = new DateToString();
    LocalDateTime atStartOfDayResult = LocalDate.ofYearDay(1, 1).atStartOfDay();
    Object actualTransformResult = actualDateToString
        .transform(java.util.Date.from(atStartOfDayResult.atZone(ZoneOffset.ofTotalSeconds(1)).toInstant()));

    // Assert
    Format format = actualDateToString.format;
    assertTrue(format instanceof FastDateFormat);
    Locale locale = ((FastDateFormat) format).getLocale();
    assertEquals("", locale.getCountry());
    assertEquals("", locale.getDisplayCountry());
    assertEquals("", locale.getDisplayScript());
    assertEquals("", locale.getDisplayVariant());
    assertEquals("", locale.getISO3Country());
    assertEquals("", locale.getScript());
    assertEquals("", locale.getVariant());
    assertEquals("02/01/0001", actualTransformResult);
    assertEquals("English", locale.getDisplayLanguage());
    assertEquals("English", locale.getDisplayName());
    assertEquals("dd/MM/yyyy", ((FastDateFormat) format).getPattern());
    assertEquals("en", locale.getLanguage());
    assertEquals("eng", locale.getISO3Language());
    assertEquals(0, ((FastDateFormat) format).getTimeZone().getDSTSavings());
    assertEquals(10, ((FastDateFormat) format).getMaxLengthEstimate());
    assertFalse(locale.hasExtensions());
    Set<Character> extensionKeys = locale.getExtensionKeys();
    assertTrue(extensionKeys.isEmpty());
    assertSame(extensionKeys, locale.getUnicodeLocaleAttributes());
    assertSame(extensionKeys, locale.getUnicodeLocaleKeys());
  }
}
