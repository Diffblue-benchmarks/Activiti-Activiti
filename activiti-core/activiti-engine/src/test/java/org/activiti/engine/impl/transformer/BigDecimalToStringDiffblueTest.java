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
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Currency;
import java.util.Locale;
import java.util.MissingResourceException;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class BigDecimalToStringDiffblueTest {
  /**
   * Test {@link BigDecimalToString#primTransform(Object)}.
   *
   * <ul>
   *   <li>When {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.
   *   <li>Then return {@code 2.3}.
   * </ul>
   *
   * <p>Method under test: {@link BigDecimalToString#primTransform(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object BigDecimalToString.primTransform(Object)"})
  public void testPrimTransform_whenBigDecimalWith23_thenReturn23() throws Exception {
    // Arrange
    BigDecimalToString bigDecimalToString = new BigDecimalToString();

    // Act
    Object actualPrimTransformResult = bigDecimalToString.primTransform(new BigDecimal("2.3"));

    // Assert
    assertEquals("2.3", actualPrimTransformResult);
  }

  /**
   * Test new {@link BigDecimalToString} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link BigDecimalToString}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BigDecimalToString.<init>()"})
  public void testNewBigDecimalToString() throws MissingResourceException {
    // Arrange, Act and Assert
    DecimalFormat decimalFormat = new BigDecimalToString().format;
    assertEquals("", decimalFormat.getNegativeSuffix());
    assertEquals("", decimalFormat.getPositivePrefix());
    assertEquals("", decimalFormat.getPositiveSuffix());
    DecimalFormatSymbols decimalFormatSymbols = decimalFormat.getDecimalFormatSymbols();
    Locale locale = decimalFormatSymbols.getLocale();
    assertEquals("", locale.getCountry());
    assertEquals("", locale.getDisplayCountry());
    assertEquals("", locale.getDisplayScript());
    assertEquals("", locale.getDisplayVariant());
    assertEquals("", locale.getISO3Country());
    assertEquals("", locale.getScript());
    assertEquals("", locale.getVariant());
    assertEquals("#,##0.###", decimalFormat.toLocalizedPattern());
    assertEquals("#,##0.###", decimalFormat.toPattern());
    assertEquals("-", decimalFormat.getNegativePrefix());
    Currency currency = decimalFormat.getCurrency();
    assertEquals("999", currency.getNumericCodeAsString());
    assertEquals("E", decimalFormatSymbols.getExponentSeparator());
    assertEquals("English", locale.getDisplayLanguage());
    assertEquals("English", locale.getDisplayName());
    assertEquals("NaN", decimalFormatSymbols.getNaN());
    assertEquals("Unknown Currency", currency.getDisplayName());
    assertEquals("XXX", decimalFormatSymbols.getInternationalCurrencySymbol());
    assertEquals("XXX", currency.getCurrencyCode());
    assertEquals("XXX", currency.toString());
    assertEquals("¤", decimalFormatSymbols.getCurrencySymbol());
    assertEquals("¤", currency.getSymbol());
    assertEquals("∞", decimalFormatSymbols.getInfinity());
    assertEquals("en", locale.getLanguage());
    assertEquals("eng", locale.getISO3Language());
    assertEquals('#', decimalFormatSymbols.getDigit());
    assertEquals('%', decimalFormatSymbols.getPercent());
    assertEquals(',', decimalFormatSymbols.getGroupingSeparator());
    assertEquals(',', decimalFormatSymbols.getMonetaryGroupingSeparator());
    assertEquals('-', decimalFormatSymbols.getMinusSign());
    assertEquals('.', decimalFormatSymbols.getDecimalSeparator());
    assertEquals('.', decimalFormatSymbols.getMonetaryDecimalSeparator());
    assertEquals('0', decimalFormatSymbols.getZeroDigit());
    assertEquals(';', decimalFormatSymbols.getPatternSeparator());
    assertEquals('‰', decimalFormatSymbols.getPerMill());
    assertEquals(-1, currency.getDefaultFractionDigits());
    assertEquals(0, decimalFormat.getMinimumFractionDigits());
    assertEquals(1, decimalFormat.getMinimumIntegerDigits());
    assertEquals(1, decimalFormat.getMultiplier());
    assertEquals(3, decimalFormat.getGroupingSize());
    assertEquals(3, decimalFormat.getMaximumFractionDigits());
    assertEquals(999, currency.getNumericCode());
    assertEquals(RoundingMode.HALF_EVEN, decimalFormat.getRoundingMode());
    assertFalse(decimalFormat.isDecimalSeparatorAlwaysShown());
    assertFalse(decimalFormat.isParseBigDecimal());
    assertFalse(decimalFormat.isParseIntegerOnly());
    assertFalse(locale.hasExtensions());
    assertTrue(decimalFormat.isGroupingUsed());
    assertTrue(locale.getExtensionKeys().isEmpty());
    assertEquals(Integer.MAX_VALUE, decimalFormat.getMaximumIntegerDigits());
  }
}
