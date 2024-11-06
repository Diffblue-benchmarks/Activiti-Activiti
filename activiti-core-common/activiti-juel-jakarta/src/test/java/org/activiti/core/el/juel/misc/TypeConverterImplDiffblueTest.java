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
package org.activiti.core.el.juel.misc;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import jakarta.el.ELException;
import java.awt.Component;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TypeConverterImplDiffblueTest {
  /**
   * Test {@link TypeConverterImpl#coerceToBoolean(Object)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToBoolean(Object)}
   */
  @Test
  @DisplayName("Test coerceToBoolean(Object); when empty string; then return 'false'")
  void testCoerceToBoolean_whenEmptyString_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new TypeConverterImpl()).coerceToBoolean(""));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBoolean(Object)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToBoolean(Object)}
   */
  @Test
  @DisplayName("Test coerceToBoolean(Object); when forty-two; then throw ELException")
  void testCoerceToBoolean_whenFortyTwo_thenThrowELException() {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> (new TypeConverterImpl()).coerceToBoolean(42));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBoolean(Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToBoolean(Object)}
   */
  @Test
  @DisplayName("Test coerceToBoolean(Object); when 'null'; then return 'false'")
  void testCoerceToBoolean_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new TypeConverterImpl()).coerceToBoolean(null));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBoolean(Object)}.
   * <ul>
   *   <li>When {@code true}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToBoolean(Object)}
   */
  @Test
  @DisplayName("Test coerceToBoolean(Object); when 'true'; then return 'true'")
  void testCoerceToBoolean_whenTrue_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((new TypeConverterImpl()).coerceToBoolean(true));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBoolean(Object)}.
   * <ul>
   *   <li>When {@code Value}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToBoolean(Object)}
   */
  @Test
  @DisplayName("Test coerceToBoolean(Object); when 'Value'; then return 'false'")
  void testCoerceToBoolean_whenValue_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new TypeConverterImpl()).coerceToBoolean("Value"));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToCharacter(Object)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return charValue is null.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToCharacter(Object)}
   */
  @Test
  @DisplayName("Test coerceToCharacter(Object); when empty string; then return charValue is null")
  void testCoerceToCharacter_whenEmptyString_thenReturnCharValueIsNull() {
    // Arrange, Act and Assert
    assertEquals('\u0000', (new TypeConverterImpl()).coerceToCharacter("").charValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToCharacter(Object)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return charValue is {@code *}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToCharacter(Object)}
   */
  @Test
  @DisplayName("Test coerceToCharacter(Object); when forty-two; then return charValue is '*'")
  void testCoerceToCharacter_whenFortyTwo_thenReturnCharValueIsAsterisk() {
    // Arrange, Act and Assert
    assertEquals('*', (new TypeConverterImpl()).coerceToCharacter(42).charValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToCharacter(Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return charValue is null.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToCharacter(Object)}
   */
  @Test
  @DisplayName("Test coerceToCharacter(Object); when 'null'; then return charValue is null")
  void testCoerceToCharacter_whenNull_thenReturnCharValueIsNull() {
    // Arrange, Act and Assert
    assertEquals('\u0000', (new TypeConverterImpl()).coerceToCharacter(null).charValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToCharacter(Object)}.
   * <ul>
   *   <li>When start of heading.</li>
   *   <li>Then return charValue is start of heading.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToCharacter(Object)}
   */
  @Test
  @DisplayName("Test coerceToCharacter(Object); when start of heading; then return charValue is start of heading")
  void testCoerceToCharacter_whenStartOfHeading_thenReturnCharValueIsStartOfHeading() {
    // Arrange, Act and Assert
    assertEquals('\u0001', (new TypeConverterImpl()).coerceToCharacter('\u0001').charValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToCharacter(Object)}.
   * <ul>
   *   <li>When {@code Value}.</li>
   *   <li>Then return charValue is {@code V}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToCharacter(Object)}
   */
  @Test
  @DisplayName("Test coerceToCharacter(Object); when 'Value'; then return charValue is 'V'")
  void testCoerceToCharacter_whenValue_thenReturnCharValueIsV() {
    // Arrange, Act and Assert
    assertEquals('V', (new TypeConverterImpl()).coerceToCharacter("Value").charValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBigDecimal(Object)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return {@link BigDecimal#BigDecimal(String)} with {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToBigDecimal(Object)}
   */
  @Test
  @DisplayName("Test coerceToBigDecimal(Object); when '42'; then return BigDecimal(String) with '42'")
  void testCoerceToBigDecimal_when42_thenReturnBigDecimalWith42() {
    // Arrange and Act
    BigDecimal actualCoerceToBigDecimalResult = (new TypeConverterImpl()).coerceToBigDecimal("42");

    // Assert
    assertEquals(new BigDecimal("42"), actualCoerceToBigDecimalResult);
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBigDecimal(Object)}.
   * <ul>
   *   <li>When {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.</li>
   *   <li>Then return {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToBigDecimal(Object)}
   */
  @Test
  @DisplayName("Test coerceToBigDecimal(Object); when BigDecimal(String) with '2.3'; then return BigDecimal(String) with '2.3'")
  void testCoerceToBigDecimal_whenBigDecimalWith23_thenReturnBigDecimalWith23() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();

    // Act
    BigDecimal actualCoerceToBigDecimalResult = typeConverterImpl.coerceToBigDecimal(new BigDecimal("2.3"));

    // Assert
    assertEquals(new BigDecimal("2.3"), actualCoerceToBigDecimalResult);
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBigDecimal(Object)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return {@link BigDecimal#BigDecimal(String)} with {@code 0}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToBigDecimal(Object)}
   */
  @Test
  @DisplayName("Test coerceToBigDecimal(Object); when empty string; then return BigDecimal(String) with '0'")
  void testCoerceToBigDecimal_whenEmptyString_thenReturnBigDecimalWith0() {
    // Arrange and Act
    BigDecimal actualCoerceToBigDecimalResult = (new TypeConverterImpl()).coerceToBigDecimal("");

    // Assert
    assertEquals(new BigDecimal("0"), actualCoerceToBigDecimalResult);
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBigDecimal(Object)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@link BigDecimal#BigDecimal(String)} with {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToBigDecimal(Object)}
   */
  @Test
  @DisplayName("Test coerceToBigDecimal(Object); when forty-two; then return BigDecimal(String) with '42'")
  void testCoerceToBigDecimal_whenFortyTwo_thenReturnBigDecimalWith42() {
    // Arrange and Act
    BigDecimal actualCoerceToBigDecimalResult = (new TypeConverterImpl()).coerceToBigDecimal(42);

    // Assert
    assertEquals(new BigDecimal("42"), actualCoerceToBigDecimalResult);
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBigDecimal(Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@link BigDecimal#BigDecimal(String)} with {@code 0}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToBigDecimal(Object)}
   */
  @Test
  @DisplayName("Test coerceToBigDecimal(Object); when 'null'; then return BigDecimal(String) with '0'")
  void testCoerceToBigDecimal_whenNull_thenReturnBigDecimalWith0() {
    // Arrange and Act
    BigDecimal actualCoerceToBigDecimalResult = (new TypeConverterImpl()).coerceToBigDecimal(null);

    // Assert
    assertEquals(new BigDecimal("0"), actualCoerceToBigDecimalResult);
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBigDecimal(Object)}.
   * <ul>
   *   <li>When valueOf one.</li>
   *   <li>Then return {@link BigDecimal#BigDecimal(String)} with {@code 1}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToBigDecimal(Object)}
   */
  @Test
  @DisplayName("Test coerceToBigDecimal(Object); when valueOf one; then return BigDecimal(String) with '1'")
  void testCoerceToBigDecimal_whenValueOfOne_thenReturnBigDecimalWith1() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();

    // Act
    BigDecimal actualCoerceToBigDecimalResult = typeConverterImpl.coerceToBigDecimal(BigInteger.valueOf(1L));

    // Assert
    assertEquals(new BigDecimal("1"), actualCoerceToBigDecimalResult);
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBigDecimal(Object)}.
   * <ul>
   *   <li>When {@code Value}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToBigDecimal(Object)}
   */
  @Test
  @DisplayName("Test coerceToBigDecimal(Object); when 'Value'; then throw ELException")
  void testCoerceToBigDecimal_whenValue_thenThrowELException() {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> (new TypeConverterImpl()).coerceToBigDecimal("Value"));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBigInteger(Object)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return toString is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToBigInteger(Object)}
   */
  @Test
  @DisplayName("Test coerceToBigInteger(Object); when '42'; then return toString is '42'")
  void testCoerceToBigInteger_when42_thenReturnToStringIs42() {
    // Arrange and Act
    BigInteger actualCoerceToBigIntegerResult = (new TypeConverterImpl()).coerceToBigInteger("42");

    // Assert
    assertEquals("42", actualCoerceToBigIntegerResult.toString());
    assertEquals(1, actualCoerceToBigIntegerResult.getLowestSetBit());
    assertEquals(1, actualCoerceToBigIntegerResult.signum());
    assertArrayEquals(new byte[]{'*'}, actualCoerceToBigIntegerResult.toByteArray());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBigInteger(Object)}.
   * <ul>
   *   <li>When {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.</li>
   *   <li>Then return {@link BigInteger#TWO}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToBigInteger(Object)}
   */
  @Test
  @DisplayName("Test coerceToBigInteger(Object); when BigDecimal(String) with '2.3'; then return TWO")
  void testCoerceToBigInteger_whenBigDecimalWith23_thenReturnTwo() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();

    // Act
    BigInteger actualCoerceToBigIntegerResult = typeConverterImpl.coerceToBigInteger(new BigDecimal("2.3"));

    // Assert
    assertSame(actualCoerceToBigIntegerResult.TWO, actualCoerceToBigIntegerResult);
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBigInteger(Object)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return {@link BigInteger#ZERO}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToBigInteger(Object)}
   */
  @Test
  @DisplayName("Test coerceToBigInteger(Object); when empty string; then return ZERO")
  void testCoerceToBigInteger_whenEmptyString_thenReturnZero() {
    // Arrange and Act
    BigInteger actualCoerceToBigIntegerResult = (new TypeConverterImpl()).coerceToBigInteger("");

    // Assert
    assertSame(actualCoerceToBigIntegerResult.ZERO, actualCoerceToBigIntegerResult);
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBigInteger(Object)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return toString is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToBigInteger(Object)}
   */
  @Test
  @DisplayName("Test coerceToBigInteger(Object); when forty-two; then return toString is '42'")
  void testCoerceToBigInteger_whenFortyTwo_thenReturnToStringIs42() {
    // Arrange and Act
    BigInteger actualCoerceToBigIntegerResult = (new TypeConverterImpl()).coerceToBigInteger(42);

    // Assert
    assertEquals("42", actualCoerceToBigIntegerResult.toString());
    assertEquals(1, actualCoerceToBigIntegerResult.getLowestSetBit());
    assertEquals(1, actualCoerceToBigIntegerResult.signum());
    assertArrayEquals(new byte[]{'*'}, actualCoerceToBigIntegerResult.toByteArray());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBigInteger(Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@link BigInteger#ZERO}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToBigInteger(Object)}
   */
  @Test
  @DisplayName("Test coerceToBigInteger(Object); when 'null'; then return ZERO")
  void testCoerceToBigInteger_whenNull_thenReturnZero() {
    // Arrange and Act
    BigInteger actualCoerceToBigIntegerResult = (new TypeConverterImpl()).coerceToBigInteger(null);

    // Assert
    assertSame(actualCoerceToBigIntegerResult.ZERO, actualCoerceToBigIntegerResult);
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBigInteger(Object)}.
   * <ul>
   *   <li>When valueOf one.</li>
   *   <li>Then return {@link BigInteger#ONE}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToBigInteger(Object)}
   */
  @Test
  @DisplayName("Test coerceToBigInteger(Object); when valueOf one; then return ONE")
  void testCoerceToBigInteger_whenValueOfOne_thenReturnOne() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();

    // Act
    BigInteger actualCoerceToBigIntegerResult = typeConverterImpl.coerceToBigInteger(BigInteger.valueOf(1L));

    // Assert
    assertSame(actualCoerceToBigIntegerResult.ONE, actualCoerceToBigIntegerResult);
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBigInteger(Object)}.
   * <ul>
   *   <li>When {@code Value}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToBigInteger(Object)}
   */
  @Test
  @DisplayName("Test coerceToBigInteger(Object); when 'Value'; then throw ELException")
  void testCoerceToBigInteger_whenValue_thenThrowELException() {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> (new TypeConverterImpl()).coerceToBigInteger("Value"));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToDouble(Object)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return doubleValue is forty-two.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToDouble(Object)}
   */
  @Test
  @DisplayName("Test coerceToDouble(Object); when '42'; then return doubleValue is forty-two")
  void testCoerceToDouble_when42_thenReturnDoubleValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(42.0d, (new TypeConverterImpl()).coerceToDouble("42").doubleValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToDouble(Object)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return doubleValue is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToDouble(Object)}
   */
  @Test
  @DisplayName("Test coerceToDouble(Object); when empty string; then return doubleValue is zero")
  void testCoerceToDouble_whenEmptyString_thenReturnDoubleValueIsZero() {
    // Arrange, Act and Assert
    assertEquals(0.0d, (new TypeConverterImpl()).coerceToDouble("").doubleValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToDouble(Object)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return doubleValue is forty-two.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToDouble(Object)}
   */
  @Test
  @DisplayName("Test coerceToDouble(Object); when forty-two; then return doubleValue is forty-two")
  void testCoerceToDouble_whenFortyTwo_thenReturnDoubleValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(42.0d, (new TypeConverterImpl()).coerceToDouble(42).doubleValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToDouble(Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return doubleValue is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToDouble(Object)}
   */
  @Test
  @DisplayName("Test coerceToDouble(Object); when 'null'; then return doubleValue is zero")
  void testCoerceToDouble_whenNull_thenReturnDoubleValueIsZero() {
    // Arrange, Act and Assert
    assertEquals(0.0d, (new TypeConverterImpl()).coerceToDouble(null).doubleValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToDouble(Object)}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then return doubleValue is ten.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToDouble(Object)}
   */
  @Test
  @DisplayName("Test coerceToDouble(Object); when ten; then return doubleValue is ten")
  void testCoerceToDouble_whenTen_thenReturnDoubleValueIsTen() {
    // Arrange, Act and Assert
    assertEquals(10.0d, (new TypeConverterImpl()).coerceToDouble(10.0d).doubleValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToDouble(Object)}.
   * <ul>
   *   <li>When {@code Value}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToDouble(Object)}
   */
  @Test
  @DisplayName("Test coerceToDouble(Object); when 'Value'; then throw ELException")
  void testCoerceToDouble_whenValue_thenThrowELException() {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> (new TypeConverterImpl()).coerceToDouble("Value"));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToFloat(Object)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return floatValue is forty-two.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToFloat(Object)}
   */
  @Test
  @DisplayName("Test coerceToFloat(Object); when '42'; then return floatValue is forty-two")
  void testCoerceToFloat_when42_thenReturnFloatValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(42.0f, (new TypeConverterImpl()).coerceToFloat("42").floatValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToFloat(Object)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return floatValue is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToFloat(Object)}
   */
  @Test
  @DisplayName("Test coerceToFloat(Object); when empty string; then return floatValue is zero")
  void testCoerceToFloat_whenEmptyString_thenReturnFloatValueIsZero() {
    // Arrange, Act and Assert
    assertEquals(0.0f, (new TypeConverterImpl()).coerceToFloat("").floatValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToFloat(Object)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return floatValue is forty-two.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToFloat(Object)}
   */
  @Test
  @DisplayName("Test coerceToFloat(Object); when forty-two; then return floatValue is forty-two")
  void testCoerceToFloat_whenFortyTwo_thenReturnFloatValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(42.0f, (new TypeConverterImpl()).coerceToFloat(42).floatValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToFloat(Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return floatValue is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToFloat(Object)}
   */
  @Test
  @DisplayName("Test coerceToFloat(Object); when 'null'; then return floatValue is zero")
  void testCoerceToFloat_whenNull_thenReturnFloatValueIsZero() {
    // Arrange, Act and Assert
    assertEquals(0.0f, (new TypeConverterImpl()).coerceToFloat(null).floatValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToFloat(Object)}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then return floatValue is ten.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToFloat(Object)}
   */
  @Test
  @DisplayName("Test coerceToFloat(Object); when ten; then return floatValue is ten")
  void testCoerceToFloat_whenTen_thenReturnFloatValueIsTen() {
    // Arrange, Act and Assert
    assertEquals(10.0f, (new TypeConverterImpl()).coerceToFloat(10.0f).floatValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToFloat(Object)}.
   * <ul>
   *   <li>When {@code Value}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToFloat(Object)}
   */
  @Test
  @DisplayName("Test coerceToFloat(Object); when 'Value'; then throw ELException")
  void testCoerceToFloat_whenValue_thenThrowELException() {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> (new TypeConverterImpl()).coerceToFloat("Value"));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToLong(Object)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return longValue is forty-two.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToLong(Object)}
   */
  @Test
  @DisplayName("Test coerceToLong(Object); when '42'; then return longValue is forty-two")
  void testCoerceToLong_when42_thenReturnLongValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(42L, (new TypeConverterImpl()).coerceToLong("42").longValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToLong(Object)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return longValue is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToLong(Object)}
   */
  @Test
  @DisplayName("Test coerceToLong(Object); when empty string; then return longValue is zero")
  void testCoerceToLong_whenEmptyString_thenReturnLongValueIsZero() {
    // Arrange, Act and Assert
    assertEquals(0L, (new TypeConverterImpl()).coerceToLong("").longValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToLong(Object)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return longValue is forty-two.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToLong(Object)}
   */
  @Test
  @DisplayName("Test coerceToLong(Object); when forty-two; then return longValue is forty-two")
  void testCoerceToLong_whenFortyTwo_thenReturnLongValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(42L, (new TypeConverterImpl()).coerceToLong(42L).longValue());
    assertEquals(42L, (new TypeConverterImpl()).coerceToLong(42).longValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToLong(Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return longValue is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToLong(Object)}
   */
  @Test
  @DisplayName("Test coerceToLong(Object); when 'null'; then return longValue is zero")
  void testCoerceToLong_whenNull_thenReturnLongValueIsZero() {
    // Arrange, Act and Assert
    assertEquals(0L, (new TypeConverterImpl()).coerceToLong(null).longValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToLong(Object)}.
   * <ul>
   *   <li>When {@code Value}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToLong(Object)}
   */
  @Test
  @DisplayName("Test coerceToLong(Object); when 'Value'; then throw ELException")
  void testCoerceToLong_whenValue_thenThrowELException() {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> (new TypeConverterImpl()).coerceToLong("Value"));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToInteger(Object)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return intValue is forty-two.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToInteger(Object)}
   */
  @Test
  @DisplayName("Test coerceToInteger(Object); when '42'; then return intValue is forty-two")
  void testCoerceToInteger_when42_thenReturnIntValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(42, (new TypeConverterImpl()).coerceToInteger("42").intValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToInteger(Object)}.
   * <ul>
   *   <li>When {@code A}.</li>
   *   <li>Then return intValue is sixty-five.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToInteger(Object)}
   */
  @Test
  @DisplayName("Test coerceToInteger(Object); when 'A'; then return intValue is sixty-five")
  void testCoerceToInteger_whenA_thenReturnIntValueIsSixtyFive() {
    // Arrange, Act and Assert
    assertEquals(65, (new TypeConverterImpl()).coerceToInteger((byte) 'A').intValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToInteger(Object)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return intValue is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToInteger(Object)}
   */
  @Test
  @DisplayName("Test coerceToInteger(Object); when empty string; then return intValue is zero")
  void testCoerceToInteger_whenEmptyString_thenReturnIntValueIsZero() {
    // Arrange, Act and Assert
    assertEquals(0, (new TypeConverterImpl()).coerceToInteger("").intValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToInteger(Object)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return intValue is forty-two.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToInteger(Object)}
   */
  @Test
  @DisplayName("Test coerceToInteger(Object); when forty-two; then return intValue is forty-two")
  void testCoerceToInteger_whenFortyTwo_thenReturnIntValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(42, (new TypeConverterImpl()).coerceToInteger(42).intValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToInteger(Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return intValue is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToInteger(Object)}
   */
  @Test
  @DisplayName("Test coerceToInteger(Object); when 'null'; then return intValue is zero")
  void testCoerceToInteger_whenNull_thenReturnIntValueIsZero() {
    // Arrange, Act and Assert
    assertEquals(0, (new TypeConverterImpl()).coerceToInteger(null).intValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToInteger(Object)}.
   * <ul>
   *   <li>When {@code Value}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToInteger(Object)}
   */
  @Test
  @DisplayName("Test coerceToInteger(Object); when 'Value'; then throw ELException")
  void testCoerceToInteger_whenValue_thenThrowELException() {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> (new TypeConverterImpl()).coerceToInteger("Value"));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToShort(Object)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return shortValue is forty-two.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToShort(Object)}
   */
  @Test
  @DisplayName("Test coerceToShort(Object); when '42'; then return shortValue is forty-two")
  void testCoerceToShort_when42_thenReturnShortValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals((short) 42, (new TypeConverterImpl()).coerceToShort("42").shortValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToShort(Object)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return shortValue is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToShort(Object)}
   */
  @Test
  @DisplayName("Test coerceToShort(Object); when empty string; then return shortValue is zero")
  void testCoerceToShort_whenEmptyString_thenReturnShortValueIsZero() {
    // Arrange, Act and Assert
    assertEquals((short) 0, (new TypeConverterImpl()).coerceToShort("").shortValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToShort(Object)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return shortValue is forty-two.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToShort(Object)}
   */
  @Test
  @DisplayName("Test coerceToShort(Object); when forty-two; then return shortValue is forty-two")
  void testCoerceToShort_whenFortyTwo_thenReturnShortValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals((short) 42, (new TypeConverterImpl()).coerceToShort(42).shortValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToShort(Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return shortValue is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToShort(Object)}
   */
  @Test
  @DisplayName("Test coerceToShort(Object); when 'null'; then return shortValue is zero")
  void testCoerceToShort_whenNull_thenReturnShortValueIsZero() {
    // Arrange, Act and Assert
    assertEquals((short) 0, (new TypeConverterImpl()).coerceToShort(null).shortValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToShort(Object)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return shortValue is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToShort(Object)}
   */
  @Test
  @DisplayName("Test coerceToShort(Object); when one; then return shortValue is one")
  void testCoerceToShort_whenOne_thenReturnShortValueIsOne() {
    // Arrange, Act and Assert
    assertEquals((short) 1, (new TypeConverterImpl()).coerceToShort((short) 1).shortValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToShort(Object)}.
   * <ul>
   *   <li>When {@code Value}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToShort(Object)}
   */
  @Test
  @DisplayName("Test coerceToShort(Object); when 'Value'; then throw ELException")
  void testCoerceToShort_whenValue_thenThrowELException() {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> (new TypeConverterImpl()).coerceToShort("Value"));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToByte(Object)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return byteValue is {@code *}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToByte(Object)}
   */
  @Test
  @DisplayName("Test coerceToByte(Object); when '42'; then return byteValue is '*'")
  void testCoerceToByte_when42_thenReturnByteValueIsAsterisk() {
    // Arrange, Act and Assert
    assertEquals('*', (new TypeConverterImpl()).coerceToByte("42").byteValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToByte(Object)}.
   * <ul>
   *   <li>When {@code A}.</li>
   *   <li>Then return byteValue is {@code A}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToByte(Object)}
   */
  @Test
  @DisplayName("Test coerceToByte(Object); when 'A'; then return byteValue is 'A'")
  void testCoerceToByte_whenA_thenReturnByteValueIsA() {
    // Arrange, Act and Assert
    assertEquals('A', (new TypeConverterImpl()).coerceToByte((byte) 'A').byteValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToByte(Object)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return byteValue is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToByte(Object)}
   */
  @Test
  @DisplayName("Test coerceToByte(Object); when empty string; then return byteValue is zero")
  void testCoerceToByte_whenEmptyString_thenReturnByteValueIsZero() {
    // Arrange, Act and Assert
    assertEquals((byte) 0, (new TypeConverterImpl()).coerceToByte("").byteValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToByte(Object)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return byteValue is {@code *}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToByte(Object)}
   */
  @Test
  @DisplayName("Test coerceToByte(Object); when forty-two; then return byteValue is '*'")
  void testCoerceToByte_whenFortyTwo_thenReturnByteValueIsAsterisk() {
    // Arrange, Act and Assert
    assertEquals('*', (new TypeConverterImpl()).coerceToByte(42).byteValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToByte(Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return byteValue is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToByte(Object)}
   */
  @Test
  @DisplayName("Test coerceToByte(Object); when 'null'; then return byteValue is zero")
  void testCoerceToByte_whenNull_thenReturnByteValueIsZero() {
    // Arrange, Act and Assert
    assertEquals((byte) 0, (new TypeConverterImpl()).coerceToByte(null).byteValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToByte(Object)}.
   * <ul>
   *   <li>When {@code Value}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToByte(Object)}
   */
  @Test
  @DisplayName("Test coerceToByte(Object); when 'Value'; then throw ELException")
  void testCoerceToByte_whenValue_thenThrowELException() {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> (new TypeConverterImpl()).coerceToByte("Value"));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToString(Object)}.
   * <ul>
   *   <li>When {@code COMMON}.</li>
   *   <li>Then return {@code COMMON}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToString(Object)}
   */
  @Test
  @DisplayName("Test coerceToString(Object); when 'COMMON'; then return 'COMMON'")
  void testCoerceToString_whenCommon_thenReturnCommon() {
    // Arrange, Act and Assert
    assertEquals("COMMON", (new TypeConverterImpl()).coerceToString(Character.UnicodeScript.COMMON));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToString(Object)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToString(Object)}
   */
  @Test
  @DisplayName("Test coerceToString(Object); when forty-two; then return '42'")
  void testCoerceToString_whenFortyTwo_thenReturn42() {
    // Arrange, Act and Assert
    assertEquals("42", (new TypeConverterImpl()).coerceToString(42));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToString(Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToString(Object)}
   */
  @Test
  @DisplayName("Test coerceToString(Object); when 'null'; then return empty string")
  void testCoerceToString_whenNull_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals("", (new TypeConverterImpl()).coerceToString(null));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToString(Object)}.
   * <ul>
   *   <li>When {@code Value}.</li>
   *   <li>Then return {@code Value}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToString(Object)}
   */
  @Test
  @DisplayName("Test coerceToString(Object); when 'Value'; then return 'Value'")
  void testCoerceToString_whenValue_thenReturnValue() {
    // Arrange, Act and Assert
    assertEquals("Value", (new TypeConverterImpl()).coerceToString("Value"));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToEnum(Object, Class)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToEnum(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToEnum(Object, Class); when empty string; then return 'null'")
  void testCoerceToEnum_whenEmptyString_thenReturnNull() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Component.BaselineResizeBehavior> type = Component.BaselineResizeBehavior.class;

    // Act and Assert
    assertNull(typeConverterImpl.coerceToEnum("", type));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToEnum(Object, Class)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToEnum(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToEnum(Object, Class); when forty-two; then throw ELException")
  void testCoerceToEnum_whenFortyTwo_thenThrowELException() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Component.BaselineResizeBehavior> type = Component.BaselineResizeBehavior.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.coerceToEnum(42, type));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToEnum(Object, Class)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToEnum(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToEnum(Object, Class); when 'null'; then return 'null'")
  void testCoerceToEnum_whenNull_thenReturnNull() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Component.BaselineResizeBehavior> type = Component.BaselineResizeBehavior.class;

    // Act and Assert
    assertNull(typeConverterImpl.coerceToEnum(null, type));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToEnum(Object, Class)}.
   * <ul>
   *   <li>When {@code Value}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToEnum(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToEnum(Object, Class); when 'Value'; then throw ELException")
  void testCoerceToEnum_whenValue_thenThrowELException() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Component.BaselineResizeBehavior> type = Component.BaselineResizeBehavior.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.coerceToEnum("Value", type));
  }

  /**
   * Test {@link TypeConverterImpl#coerceStringToType(String, Class)}.
   * <ul>
   *   <li>When {@code java.lang.Boolean}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TypeConverterImpl#coerceStringToType(String, Class)}
   */
  @Test
  @DisplayName("Test coerceStringToType(String, Class); when 'java.lang.Boolean'; then return 'null'")
  void testCoerceStringToType_whenJavaLangBoolean_thenReturnNull() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Boolean> type = Boolean.class;

    // Act and Assert
    assertNull(typeConverterImpl.coerceStringToType("", type));
  }

  /**
   * Test {@link TypeConverterImpl#coerceStringToType(String, Class)}.
   * <ul>
   *   <li>When {@code java.lang.Boolean}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TypeConverterImpl#coerceStringToType(String, Class)}
   */
  @Test
  @DisplayName("Test coerceStringToType(String, Class); when 'java.lang.Boolean'; then throw ELException")
  void testCoerceStringToType_whenJavaLangBoolean_thenThrowELException() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Boolean> type = Boolean.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.coerceStringToType("42", type));
  }

  /**
   * Test {@link TypeConverterImpl#coerceStringToType(String, Class)}.
   * <ul>
   *   <li>When {@code java.lang.Object}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TypeConverterImpl#coerceStringToType(String, Class)}
   */
  @Test
  @DisplayName("Test coerceStringToType(String, Class); when 'java.lang.Object'; then return 'null'")
  void testCoerceStringToType_whenJavaLangObject_thenReturnNull() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> type = Object.class;

    // Act and Assert
    assertNull(typeConverterImpl.coerceStringToType("", type));
  }

  /**
   * Test {@link TypeConverterImpl#coerceStringToType(String, Class)}.
   * <ul>
   *   <li>When {@code java.lang.Object}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TypeConverterImpl#coerceStringToType(String, Class)}
   */
  @Test
  @DisplayName("Test coerceStringToType(String, Class); when 'java.lang.Object'; then throw ELException")
  void testCoerceStringToType_whenJavaLangObject_thenThrowELException() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> type = Object.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.coerceStringToType("42", type));
  }

  /**
   * Test {@link TypeConverterImpl#coerceStringToType(String, Class)}.
   * <ul>
   *   <li>When {@code java.lang.String}.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TypeConverterImpl#coerceStringToType(String, Class)}
   */
  @Test
  @DisplayName("Test coerceStringToType(String, Class); when 'java.lang.String'; then return empty string")
  void testCoerceStringToType_whenJavaLangString_thenReturnEmptyString() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<String> type = String.class;

    // Act and Assert
    assertEquals("", typeConverterImpl.coerceStringToType("", type));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return doubleValue is forty-two.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when '42'; then return doubleValue is forty-two")
  void testCoerceToType_when42_thenReturnDoubleValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(42.0d, ((Double) (new TypeConverterImpl()).coerceToType("42", Double.TYPE)).doubleValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return doubleValue is forty-two.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when forty-two; then return doubleValue is forty-two")
  void testCoerceToType_whenFortyTwo_thenReturnDoubleValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(42.0d, ((Double) (new TypeConverterImpl()).coerceToType(42, Double.TYPE)).doubleValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   * <ul>
   *   <li>When {@code java.lang.Boolean}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when 'java.lang.Boolean'; then throw ELException")
  void testCoerceToType_whenJavaLangBoolean_thenThrowELException() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Boolean> type = Boolean.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.coerceToType(42, type));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   * <ul>
   *   <li>When {@code java.lang.Byte}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when 'java.lang.Byte'; then throw ELException")
  void testCoerceToType_whenJavaLangByte_thenThrowELException() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Byte> type = Byte.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.coerceToType("Value", type));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   * <ul>
   *   <li>When {@code java.lang.Object}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when 'java.lang.Object'; then return 'null'")
  void testCoerceToType_whenJavaLangObject_thenReturnNull() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> type = Object.class;

    // Act and Assert
    assertNull(typeConverterImpl.coerceToType(null, type));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   * <ul>
   *   <li>When {@code java.lang.Object}.</li>
   *   <li>Then return {@code Value}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when 'java.lang.Object'; then return 'Value'")
  void testCoerceToType_whenJavaLangObject_thenReturnValue() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> type = Object.class;

    // Act and Assert
    assertEquals("Value", typeConverterImpl.coerceToType("Value", type));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   * <ul>
   *   <li>When {@code java.lang.Object}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when 'java.lang.Object'; then throw ELException")
  void testCoerceToType_whenJavaLangObject_thenThrowELException() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.coerceToType(forNameResult, Long.TYPE));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   * <ul>
   *   <li>When {@code java.lang.Object}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when 'java.lang.Object'; then throw ELException")
  void testCoerceToType_whenJavaLangObject_thenThrowELException2() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.coerceToType(forNameResult, Double.TYPE));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then return doubleValue is ten.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when ten; then return doubleValue is ten")
  void testCoerceToType_whenTen_thenReturnDoubleValueIsTen() {
    // Arrange, Act and Assert
    assertEquals(10.0d, ((Double) (new TypeConverterImpl()).coerceToType(10.0d, Double.TYPE)).doubleValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   * <ul>
   *   <li>When {@code true}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when 'true'; then throw ELException")
  void testCoerceToType_whenTrue_thenThrowELException() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Byte> type = Byte.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.coerceToType(true, type));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   * <ul>
   *   <li>When {@code true}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when 'true'; then throw ELException")
  void testCoerceToType_whenTrue_thenThrowELException2() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Character> type = Character.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.coerceToType(true, type));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   * <ul>
   *   <li>When {@link Double#TYPE}.</li>
   *   <li>Then return doubleValue is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when TYPE; then return doubleValue is zero")
  void testCoerceToType_whenType_thenReturnDoubleValueIsZero() {
    // Arrange, Act and Assert
    assertEquals(0.0d, ((Double) (new TypeConverterImpl()).coerceToType(null, Double.TYPE)).doubleValue());
    assertEquals(0.0d, ((Double) (new TypeConverterImpl()).coerceToType("", Double.TYPE)).doubleValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   * <ul>
   *   <li>When {@link Float#TYPE}.</li>
   *   <li>Then return floatValue is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when TYPE; then return floatValue is zero")
  void testCoerceToType_whenType_thenReturnFloatValueIsZero() {
    // Arrange, Act and Assert
    assertEquals(0.0f, ((Float) (new TypeConverterImpl()).coerceToType(null, Float.TYPE)).floatValue());
    assertEquals(0.0f, ((Float) (new TypeConverterImpl()).coerceToType("", Float.TYPE)).floatValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   * <ul>
   *   <li>When {@code Value}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when 'Value'; then throw ELException")
  void testCoerceToType_whenValue_thenThrowELException() {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> (new TypeConverterImpl()).coerceToType("Value", Long.TYPE));
    assertThrows(ELException.class, () -> (new TypeConverterImpl()).coerceToType("Value", Double.TYPE));
  }

  /**
   * Test {@link TypeConverterImpl#equals(Object)}, and
   * {@link TypeConverterImpl#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link TypeConverterImpl#equals(Object)}
   *   <li>{@link TypeConverterImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    TypeConverterImpl typeConverterImpl2 = new TypeConverterImpl();

    // Act and Assert
    assertEquals(typeConverterImpl, typeConverterImpl2);
    int expectedHashCodeResult = typeConverterImpl.hashCode();
    assertEquals(expectedHashCodeResult, typeConverterImpl2.hashCode());
  }

  /**
   * Test {@link TypeConverterImpl#equals(Object)}, and
   * {@link TypeConverterImpl#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link TypeConverterImpl#equals(Object)}
   *   <li>{@link TypeConverterImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();

    // Act and Assert
    assertEquals(typeConverterImpl, typeConverterImpl);
    int expectedHashCodeResult = typeConverterImpl.hashCode();
    assertEquals(expectedHashCodeResult, typeConverterImpl.hashCode());
  }

  /**
   * Test {@link TypeConverterImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new TypeConverterImpl(), 1);
  }

  /**
   * Test {@link TypeConverterImpl#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new TypeConverterImpl(), null);
  }

  /**
   * Test {@link TypeConverterImpl#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new TypeConverterImpl(), "Different type to TypeConverterImpl");
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return doubleValue is forty-two.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when '42'; then return doubleValue is forty-two")
  void testConvert_when42_thenReturnDoubleValueIsFortyTwo() throws ELException {
    // Arrange, Act and Assert
    assertEquals(42.0d, ((Double) (new TypeConverterImpl()).convert("42", Double.TYPE)).doubleValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then return doubleValue is forty-two.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when forty-two; then return doubleValue is forty-two")
  void testConvert_whenFortyTwo_thenReturnDoubleValueIsFortyTwo() throws ELException {
    // Arrange, Act and Assert
    assertEquals(42.0d, ((Double) (new TypeConverterImpl()).convert(42, Double.TYPE)).doubleValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   * <ul>
   *   <li>When {@code java.lang.Boolean}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when 'java.lang.Boolean'; then throw ELException")
  void testConvert_whenJavaLangBoolean_thenThrowELException() throws ELException {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Boolean> type = Boolean.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.convert(42, type));
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   * <ul>
   *   <li>When {@code java.lang.Byte}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when 'java.lang.Byte'; then throw ELException")
  void testConvert_whenJavaLangByte_thenThrowELException() throws ELException {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Byte> type = Byte.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.convert("Value", type));
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   * <ul>
   *   <li>When {@code java.lang.Object}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when 'java.lang.Object'; then return 'null'")
  void testConvert_whenJavaLangObject_thenReturnNull() throws ELException {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> type = Object.class;

    // Act and Assert
    assertNull(typeConverterImpl.convert(null, type));
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   * <ul>
   *   <li>When {@code java.lang.Object}.</li>
   *   <li>Then return {@code Value}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when 'java.lang.Object'; then return 'Value'")
  void testConvert_whenJavaLangObject_thenReturnValue() throws ELException {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> type = Object.class;

    // Act and Assert
    assertEquals("Value", typeConverterImpl.convert("Value", type));
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   * <ul>
   *   <li>When {@code java.lang.Object}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when 'java.lang.Object'; then throw ELException")
  void testConvert_whenJavaLangObject_thenThrowELException() throws ELException {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.convert(forNameResult, Long.TYPE));
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   * <ul>
   *   <li>When {@code java.lang.Object}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when 'java.lang.Object'; then throw ELException")
  void testConvert_whenJavaLangObject_thenThrowELException2() throws ELException {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.convert(forNameResult, Double.TYPE));
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then return doubleValue is ten.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when ten; then return doubleValue is ten")
  void testConvert_whenTen_thenReturnDoubleValueIsTen() throws ELException {
    // Arrange, Act and Assert
    assertEquals(10.0d, ((Double) (new TypeConverterImpl()).convert(10.0d, Double.TYPE)).doubleValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   * <ul>
   *   <li>When {@code true}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when 'true'; then throw ELException")
  void testConvert_whenTrue_thenThrowELException() throws ELException {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Byte> type = Byte.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.convert(true, type));
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   * <ul>
   *   <li>When {@code true}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when 'true'; then throw ELException")
  void testConvert_whenTrue_thenThrowELException2() throws ELException {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Character> type = Character.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.convert(true, type));
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   * <ul>
   *   <li>When {@link Double#TYPE}.</li>
   *   <li>Then return doubleValue is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when TYPE; then return doubleValue is zero")
  void testConvert_whenType_thenReturnDoubleValueIsZero() throws ELException {
    // Arrange, Act and Assert
    assertEquals(0.0d, ((Double) (new TypeConverterImpl()).convert(null, Double.TYPE)).doubleValue());
    assertEquals(0.0d, ((Double) (new TypeConverterImpl()).convert("", Double.TYPE)).doubleValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   * <ul>
   *   <li>When {@link Float#TYPE}.</li>
   *   <li>Then return floatValue is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when TYPE; then return floatValue is zero")
  void testConvert_whenType_thenReturnFloatValueIsZero() throws ELException {
    // Arrange, Act and Assert
    assertEquals(0.0f, ((Float) (new TypeConverterImpl()).convert(null, Float.TYPE)).floatValue());
    assertEquals(0.0f, ((Float) (new TypeConverterImpl()).convert("", Float.TYPE)).floatValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   * <ul>
   *   <li>When {@code Value}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when 'Value'; then throw ELException")
  void testConvert_whenValue_thenThrowELException() throws ELException {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> (new TypeConverterImpl()).convert("Value", Long.TYPE));
    assertThrows(ELException.class, () -> (new TypeConverterImpl()).convert("Value", Double.TYPE));
  }
}
