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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Currency;
import org.junit.Test;

public class BigDecimalToStringDiffblueTest {
  /**
   * Test {@link BigDecimalToString#primTransform(Object)}.
   * <ul>
   *   <li>When {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.</li>
   *   <li>Then return {@code 2.3}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BigDecimalToString#primTransform(Object)}
   */
  @Test
  public void testPrimTransform_whenBigDecimalWith23_thenReturn23() throws Exception {
    // Arrange
    BigDecimalToString bigDecimalToString = new BigDecimalToString();

    // Act and Assert
    assertEquals("2.3", bigDecimalToString.primTransform(new BigDecimal("2.3")));
  }

  /**
   * Test new {@link BigDecimalToString} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link BigDecimalToString}
   */
  @Test
  public void testNewBigDecimalToString() {
    // Arrange, Act and Assert
    DecimalFormat decimalFormat = (new BigDecimalToString()).format;
    assertEquals("", decimalFormat.getNegativeSuffix());
    assertEquals("", decimalFormat.getPositivePrefix());
    assertEquals("", decimalFormat.getPositiveSuffix());
    assertEquals("#,##0.###", decimalFormat.toLocalizedPattern());
    assertEquals("#,##0.###", decimalFormat.toPattern());
    assertEquals("-", decimalFormat.getNegativePrefix());
    Currency currency = decimalFormat.getCurrency();
    assertEquals("British Pound", currency.getDisplayName());
    DecimalFormatSymbols decimalFormatSymbols = decimalFormat.getDecimalFormatSymbols();
    assertEquals("E", decimalFormatSymbols.getExponentSeparator());
    assertEquals("GBP", decimalFormatSymbols.getInternationalCurrencySymbol());
    assertEquals("GBP", currency.getCurrencyCode());
    assertEquals("GBP", currency.toString());
    assertEquals("NaN", decimalFormatSymbols.getNaN());
    assertEquals("£", decimalFormatSymbols.getCurrencySymbol());
    assertEquals("£", currency.getSymbol());
    assertEquals("∞", decimalFormatSymbols.getInfinity());
    assertEquals('#', decimalFormatSymbols.getDigit());
    assertEquals('%', decimalFormatSymbols.getPercent());
    assertEquals(',', decimalFormatSymbols.getGroupingSeparator());
    assertEquals('-', decimalFormatSymbols.getMinusSign());
    assertEquals('.', decimalFormatSymbols.getDecimalSeparator());
    assertEquals('.', decimalFormatSymbols.getMonetaryDecimalSeparator());
    assertEquals('0', decimalFormatSymbols.getZeroDigit());
    assertEquals(';', decimalFormatSymbols.getPatternSeparator());
    assertEquals('‰', decimalFormatSymbols.getPerMill());
    assertEquals(0, decimalFormat.getMinimumFractionDigits());
    assertEquals(1, decimalFormat.getMinimumIntegerDigits());
    assertEquals(1, decimalFormat.getMultiplier());
    assertEquals(2, currency.getDefaultFractionDigits());
    assertEquals(3, decimalFormat.getGroupingSize());
    assertEquals(3, decimalFormat.getMaximumFractionDigits());
    assertEquals(826, currency.getNumericCode());
    assertEquals(RoundingMode.HALF_EVEN, decimalFormat.getRoundingMode());
    assertFalse(decimalFormat.isDecimalSeparatorAlwaysShown());
    assertFalse(decimalFormat.isParseBigDecimal());
    assertFalse(decimalFormat.isParseIntegerOnly());
    assertTrue(decimalFormat.isGroupingUsed());
    assertEquals(Integer.MAX_VALUE, decimalFormat.getMaximumIntegerDigits());
  }
}
