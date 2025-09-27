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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.el.ELException;
import java.awt.Component;
import java.awt.Component.BaselineResizeBehavior;
import java.lang.Character.UnicodeScript;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class TypeConverterImplDiffblueTest {
  /**
   * Test {@link TypeConverterImpl#coerceToBoolean(Object)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToBoolean(Object)}
   */
  @Test
  @DisplayName("Test coerceToBoolean(Object); when empty string; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Boolean TypeConverterImpl.coerceToBoolean(Object)"})
  void testCoerceToBoolean_whenEmptyString_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new TypeConverterImpl().coerceToBoolean(""));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBoolean(Object)}.
   *
   * <ul>
   *   <li>When forty-two.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToBoolean(Object)}
   */
  @Test
  @DisplayName("Test coerceToBoolean(Object); when forty-two; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Boolean TypeConverterImpl.coerceToBoolean(Object)"})
  void testCoerceToBoolean_whenFortyTwo_thenThrowELException() {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> new TypeConverterImpl().coerceToBoolean(42));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBoolean(Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToBoolean(Object)}
   */
  @Test
  @DisplayName("Test coerceToBoolean(Object); when 'null'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Boolean TypeConverterImpl.coerceToBoolean(Object)"})
  void testCoerceToBoolean_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new TypeConverterImpl().coerceToBoolean(null));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBoolean(Object)}.
   *
   * <ul>
   *   <li>When {@code true}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToBoolean(Object)}
   */
  @Test
  @DisplayName("Test coerceToBoolean(Object); when 'true'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Boolean TypeConverterImpl.coerceToBoolean(Object)"})
  void testCoerceToBoolean_whenTrue_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(new TypeConverterImpl().coerceToBoolean(true));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBoolean(Object)}.
   *
   * <ul>
   *   <li>When {@code Value}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToBoolean(Object)}
   */
  @Test
  @DisplayName("Test coerceToBoolean(Object); when 'Value'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Boolean TypeConverterImpl.coerceToBoolean(Object)"})
  void testCoerceToBoolean_whenValue_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new TypeConverterImpl().coerceToBoolean("Value"));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToCharacter(Object)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return charValue is null.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToCharacter(Object)}
   */
  @Test
  @DisplayName("Test coerceToCharacter(Object); when empty string; then return charValue is null")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Character TypeConverterImpl.coerceToCharacter(Object)"})
  void testCoerceToCharacter_whenEmptyString_thenReturnCharValueIsNull() {
    // Arrange, Act and Assert
    assertEquals('\u0000', new TypeConverterImpl().coerceToCharacter("").charValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToCharacter(Object)}.
   *
   * <ul>
   *   <li>When forty-two.
   *   <li>Then return charValue is {@code *}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToCharacter(Object)}
   */
  @Test
  @DisplayName("Test coerceToCharacter(Object); when forty-two; then return charValue is '*'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Character TypeConverterImpl.coerceToCharacter(Object)"})
  void testCoerceToCharacter_whenFortyTwo_thenReturnCharValueIsAsterisk() {
    // Arrange, Act and Assert
    assertEquals('*', new TypeConverterImpl().coerceToCharacter(42).charValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToCharacter(Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return charValue is null.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToCharacter(Object)}
   */
  @Test
  @DisplayName("Test coerceToCharacter(Object); when 'null'; then return charValue is null")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Character TypeConverterImpl.coerceToCharacter(Object)"})
  void testCoerceToCharacter_whenNull_thenReturnCharValueIsNull() {
    // Arrange, Act and Assert
    assertEquals('\u0000', new TypeConverterImpl().coerceToCharacter(null).charValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToCharacter(Object)}.
   *
   * <ul>
   *   <li>When start of heading.
   *   <li>Then return charValue is start of heading.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToCharacter(Object)}
   */
  @Test
  @DisplayName(
      "Test coerceToCharacter(Object); when start of heading; then return charValue is start of heading")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Character TypeConverterImpl.coerceToCharacter(Object)"})
  void testCoerceToCharacter_whenStartOfHeading_thenReturnCharValueIsStartOfHeading() {
    // Arrange, Act and Assert
    assertEquals('\u0001', new TypeConverterImpl().coerceToCharacter('\u0001').charValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToCharacter(Object)}.
   *
   * <ul>
   *   <li>When {@code Value}.
   *   <li>Then return charValue is {@code V}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToCharacter(Object)}
   */
  @Test
  @DisplayName("Test coerceToCharacter(Object); when 'Value'; then return charValue is 'V'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Character TypeConverterImpl.coerceToCharacter(Object)"})
  void testCoerceToCharacter_whenValue_thenReturnCharValueIsV() {
    // Arrange, Act and Assert
    assertEquals('V', new TypeConverterImpl().coerceToCharacter("Value").charValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBigDecimal(Object)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return {@link BigDecimal#BigDecimal(String)} with {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToBigDecimal(Object)}
   */
  @Test
  @DisplayName(
      "Test coerceToBigDecimal(Object); when '42'; then return BigDecimal(String) with '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BigDecimal TypeConverterImpl.coerceToBigDecimal(Object)"})
  void testCoerceToBigDecimal_when42_thenReturnBigDecimalWith42() {
    // Arrange and Act
    BigDecimal actualCoerceToBigDecimalResult = new TypeConverterImpl().coerceToBigDecimal("42");

    // Assert
    assertEquals(new BigDecimal("42"), actualCoerceToBigDecimalResult);
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBigDecimal(Object)}.
   *
   * <ul>
   *   <li>When {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.
   *   <li>Then return {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToBigDecimal(Object)}
   */
  @Test
  @DisplayName(
      "Test coerceToBigDecimal(Object); when BigDecimal(String) with '2.3'; then return BigDecimal(String) with '2.3'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BigDecimal TypeConverterImpl.coerceToBigDecimal(Object)"})
  void testCoerceToBigDecimal_whenBigDecimalWith23_thenReturnBigDecimalWith23() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();

    // Act
    BigDecimal actualCoerceToBigDecimalResult =
        typeConverterImpl.coerceToBigDecimal(new BigDecimal("2.3"));

    // Assert
    assertEquals(new BigDecimal("2.3"), actualCoerceToBigDecimalResult);
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBigDecimal(Object)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return {@link BigDecimal#BigDecimal(String)} with {@code 0}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToBigDecimal(Object)}
   */
  @Test
  @DisplayName(
      "Test coerceToBigDecimal(Object); when empty string; then return BigDecimal(String) with '0'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BigDecimal TypeConverterImpl.coerceToBigDecimal(Object)"})
  void testCoerceToBigDecimal_whenEmptyString_thenReturnBigDecimalWith0() {
    // Arrange and Act
    BigDecimal actualCoerceToBigDecimalResult = new TypeConverterImpl().coerceToBigDecimal("");

    // Assert
    assertEquals(new BigDecimal("0"), actualCoerceToBigDecimalResult);
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBigDecimal(Object)}.
   *
   * <ul>
   *   <li>When forty-two.
   *   <li>Then return {@link BigDecimal#BigDecimal(String)} with {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToBigDecimal(Object)}
   */
  @Test
  @DisplayName(
      "Test coerceToBigDecimal(Object); when forty-two; then return BigDecimal(String) with '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BigDecimal TypeConverterImpl.coerceToBigDecimal(Object)"})
  void testCoerceToBigDecimal_whenFortyTwo_thenReturnBigDecimalWith42() {
    // Arrange and Act
    BigDecimal actualCoerceToBigDecimalResult = new TypeConverterImpl().coerceToBigDecimal(42);

    // Assert
    assertEquals(new BigDecimal("42"), actualCoerceToBigDecimalResult);
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBigDecimal(Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@link BigDecimal#BigDecimal(String)} with {@code 0}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToBigDecimal(Object)}
   */
  @Test
  @DisplayName(
      "Test coerceToBigDecimal(Object); when 'null'; then return BigDecimal(String) with '0'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BigDecimal TypeConverterImpl.coerceToBigDecimal(Object)"})
  void testCoerceToBigDecimal_whenNull_thenReturnBigDecimalWith0() {
    // Arrange and Act
    BigDecimal actualCoerceToBigDecimalResult = new TypeConverterImpl().coerceToBigDecimal(null);

    // Assert
    assertEquals(new BigDecimal("0"), actualCoerceToBigDecimalResult);
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBigDecimal(Object)}.
   *
   * <ul>
   *   <li>When valueOf one.
   *   <li>Then return {@link BigDecimal#BigDecimal(String)} with {@code 1}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToBigDecimal(Object)}
   */
  @Test
  @DisplayName(
      "Test coerceToBigDecimal(Object); when valueOf one; then return BigDecimal(String) with '1'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BigDecimal TypeConverterImpl.coerceToBigDecimal(Object)"})
  void testCoerceToBigDecimal_whenValueOfOne_thenReturnBigDecimalWith1() {
    // Arrange and Act
    BigDecimal actualCoerceToBigDecimalResult =
        new TypeConverterImpl().coerceToBigDecimal(BigInteger.valueOf(1L));

    // Assert
    assertEquals(new BigDecimal("1"), actualCoerceToBigDecimalResult);
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBigDecimal(Object)}.
   *
   * <ul>
   *   <li>When {@code Value}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToBigDecimal(Object)}
   */
  @Test
  @DisplayName("Test coerceToBigDecimal(Object); when 'Value'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BigDecimal TypeConverterImpl.coerceToBigDecimal(Object)"})
  void testCoerceToBigDecimal_whenValue_thenThrowELException() {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> new TypeConverterImpl().coerceToBigDecimal("Value"));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBigInteger(Object)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return toString is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToBigInteger(Object)}
   */
  @Test
  @DisplayName("Test coerceToBigInteger(Object); when '42'; then return toString is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BigInteger TypeConverterImpl.coerceToBigInteger(Object)"})
  void testCoerceToBigInteger_when42_thenReturnToStringIs42() {
    // Arrange and Act
    BigInteger actualCoerceToBigIntegerResult = new TypeConverterImpl().coerceToBigInteger("42");

    // Assert
    assertEquals("42", actualCoerceToBigIntegerResult.toString());
    assertEquals(1, actualCoerceToBigIntegerResult.getLowestSetBit());
    assertEquals(1, actualCoerceToBigIntegerResult.signum());
    assertArrayEquals(new byte[] {'*'}, actualCoerceToBigIntegerResult.toByteArray());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBigInteger(Object)}.
   *
   * <ul>
   *   <li>When {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.
   *   <li>Then return {@link BigInteger#TWO}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToBigInteger(Object)}
   */
  @Test
  @DisplayName(
      "Test coerceToBigInteger(Object); when BigDecimal(String) with '2.3'; then return TWO")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BigInteger TypeConverterImpl.coerceToBigInteger(Object)"})
  void testCoerceToBigInteger_whenBigDecimalWith23_thenReturnTwo() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();

    // Act
    BigInteger actualCoerceToBigIntegerResult =
        typeConverterImpl.coerceToBigInteger(new BigDecimal("2.3"));

    // Assert
    assertSame(BigInteger.TWO, actualCoerceToBigIntegerResult);
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBigInteger(Object)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return {@link BigInteger#ZERO}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToBigInteger(Object)}
   */
  @Test
  @DisplayName("Test coerceToBigInteger(Object); when empty string; then return ZERO")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BigInteger TypeConverterImpl.coerceToBigInteger(Object)"})
  void testCoerceToBigInteger_whenEmptyString_thenReturnZero() {
    // Arrange, Act and Assert
    assertSame(BigInteger.ZERO, new TypeConverterImpl().coerceToBigInteger(""));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBigInteger(Object)}.
   *
   * <ul>
   *   <li>When forty-two.
   *   <li>Then return toString is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToBigInteger(Object)}
   */
  @Test
  @DisplayName("Test coerceToBigInteger(Object); when forty-two; then return toString is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BigInteger TypeConverterImpl.coerceToBigInteger(Object)"})
  void testCoerceToBigInteger_whenFortyTwo_thenReturnToStringIs42() {
    // Arrange and Act
    BigInteger actualCoerceToBigIntegerResult = new TypeConverterImpl().coerceToBigInteger(42);

    // Assert
    assertEquals("42", actualCoerceToBigIntegerResult.toString());
    assertEquals(1, actualCoerceToBigIntegerResult.getLowestSetBit());
    assertEquals(1, actualCoerceToBigIntegerResult.signum());
    assertArrayEquals(new byte[] {'*'}, actualCoerceToBigIntegerResult.toByteArray());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBigInteger(Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@link BigInteger#ZERO}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToBigInteger(Object)}
   */
  @Test
  @DisplayName("Test coerceToBigInteger(Object); when 'null'; then return ZERO")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BigInteger TypeConverterImpl.coerceToBigInteger(Object)"})
  void testCoerceToBigInteger_whenNull_thenReturnZero() {
    // Arrange, Act and Assert
    assertSame(BigInteger.ZERO, new TypeConverterImpl().coerceToBigInteger(null));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBigInteger(Object)}.
   *
   * <ul>
   *   <li>When valueOf one.
   *   <li>Then return {@link BigInteger#ONE}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToBigInteger(Object)}
   */
  @Test
  @DisplayName("Test coerceToBigInteger(Object); when valueOf one; then return ONE")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BigInteger TypeConverterImpl.coerceToBigInteger(Object)"})
  void testCoerceToBigInteger_whenValueOfOne_thenReturnOne() {
    // Arrange, Act and Assert
    assertSame(BigInteger.ONE, new TypeConverterImpl().coerceToBigInteger(BigInteger.valueOf(1L)));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToBigInteger(Object)}.
   *
   * <ul>
   *   <li>When {@code Value}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToBigInteger(Object)}
   */
  @Test
  @DisplayName("Test coerceToBigInteger(Object); when 'Value'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"BigInteger TypeConverterImpl.coerceToBigInteger(Object)"})
  void testCoerceToBigInteger_whenValue_thenThrowELException() {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> new TypeConverterImpl().coerceToBigInteger("Value"));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToDouble(Object)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return doubleValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToDouble(Object)}
   */
  @Test
  @DisplayName("Test coerceToDouble(Object); when '42'; then return doubleValue is forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Double TypeConverterImpl.coerceToDouble(Object)"})
  void testCoerceToDouble_when42_thenReturnDoubleValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(42.0d, new TypeConverterImpl().coerceToDouble("42").doubleValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToDouble(Object)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return doubleValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToDouble(Object)}
   */
  @Test
  @DisplayName("Test coerceToDouble(Object); when empty string; then return doubleValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Double TypeConverterImpl.coerceToDouble(Object)"})
  void testCoerceToDouble_whenEmptyString_thenReturnDoubleValueIsZero() {
    // Arrange, Act and Assert
    assertEquals(0.0d, new TypeConverterImpl().coerceToDouble("").doubleValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToDouble(Object)}.
   *
   * <ul>
   *   <li>When forty-two.
   *   <li>Then return doubleValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToDouble(Object)}
   */
  @Test
  @DisplayName("Test coerceToDouble(Object); when forty-two; then return doubleValue is forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Double TypeConverterImpl.coerceToDouble(Object)"})
  void testCoerceToDouble_whenFortyTwo_thenReturnDoubleValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(42.0d, new TypeConverterImpl().coerceToDouble(42).doubleValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToDouble(Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return doubleValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToDouble(Object)}
   */
  @Test
  @DisplayName("Test coerceToDouble(Object); when 'null'; then return doubleValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Double TypeConverterImpl.coerceToDouble(Object)"})
  void testCoerceToDouble_whenNull_thenReturnDoubleValueIsZero() {
    // Arrange, Act and Assert
    assertEquals(0.0d, new TypeConverterImpl().coerceToDouble(null).doubleValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToDouble(Object)}.
   *
   * <ul>
   *   <li>When ten.
   *   <li>Then return doubleValue is ten.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToDouble(Object)}
   */
  @Test
  @DisplayName("Test coerceToDouble(Object); when ten; then return doubleValue is ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Double TypeConverterImpl.coerceToDouble(Object)"})
  void testCoerceToDouble_whenTen_thenReturnDoubleValueIsTen() {
    // Arrange, Act and Assert
    assertEquals(10.0d, new TypeConverterImpl().coerceToDouble(10.0d).doubleValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToDouble(Object)}.
   *
   * <ul>
   *   <li>When {@code Value}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToDouble(Object)}
   */
  @Test
  @DisplayName("Test coerceToDouble(Object); when 'Value'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Double TypeConverterImpl.coerceToDouble(Object)"})
  void testCoerceToDouble_whenValue_thenThrowELException() {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> new TypeConverterImpl().coerceToDouble("Value"));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToFloat(Object)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return floatValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToFloat(Object)}
   */
  @Test
  @DisplayName("Test coerceToFloat(Object); when '42'; then return floatValue is forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Float TypeConverterImpl.coerceToFloat(Object)"})
  void testCoerceToFloat_when42_thenReturnFloatValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(42.0f, new TypeConverterImpl().coerceToFloat("42").floatValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToFloat(Object)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return floatValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToFloat(Object)}
   */
  @Test
  @DisplayName("Test coerceToFloat(Object); when empty string; then return floatValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Float TypeConverterImpl.coerceToFloat(Object)"})
  void testCoerceToFloat_whenEmptyString_thenReturnFloatValueIsZero() {
    // Arrange, Act and Assert
    assertEquals(0.0f, new TypeConverterImpl().coerceToFloat("").floatValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToFloat(Object)}.
   *
   * <ul>
   *   <li>When forty-two.
   *   <li>Then return floatValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToFloat(Object)}
   */
  @Test
  @DisplayName("Test coerceToFloat(Object); when forty-two; then return floatValue is forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Float TypeConverterImpl.coerceToFloat(Object)"})
  void testCoerceToFloat_whenFortyTwo_thenReturnFloatValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(42.0f, new TypeConverterImpl().coerceToFloat(42).floatValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToFloat(Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return floatValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToFloat(Object)}
   */
  @Test
  @DisplayName("Test coerceToFloat(Object); when 'null'; then return floatValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Float TypeConverterImpl.coerceToFloat(Object)"})
  void testCoerceToFloat_whenNull_thenReturnFloatValueIsZero() {
    // Arrange, Act and Assert
    assertEquals(0.0f, new TypeConverterImpl().coerceToFloat(null).floatValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToFloat(Object)}.
   *
   * <ul>
   *   <li>When ten.
   *   <li>Then return floatValue is ten.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToFloat(Object)}
   */
  @Test
  @DisplayName("Test coerceToFloat(Object); when ten; then return floatValue is ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Float TypeConverterImpl.coerceToFloat(Object)"})
  void testCoerceToFloat_whenTen_thenReturnFloatValueIsTen() {
    // Arrange, Act and Assert
    assertEquals(10.0f, new TypeConverterImpl().coerceToFloat(10.0f).floatValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToFloat(Object)}.
   *
   * <ul>
   *   <li>When {@code Value}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToFloat(Object)}
   */
  @Test
  @DisplayName("Test coerceToFloat(Object); when 'Value'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Float TypeConverterImpl.coerceToFloat(Object)"})
  void testCoerceToFloat_whenValue_thenThrowELException() {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> new TypeConverterImpl().coerceToFloat("Value"));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToLong(Object)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return longValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToLong(Object)}
   */
  @Test
  @DisplayName("Test coerceToLong(Object); when '42'; then return longValue is forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Long TypeConverterImpl.coerceToLong(Object)"})
  void testCoerceToLong_when42_thenReturnLongValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(42L, new TypeConverterImpl().coerceToLong("42").longValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToLong(Object)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return longValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToLong(Object)}
   */
  @Test
  @DisplayName("Test coerceToLong(Object); when empty string; then return longValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Long TypeConverterImpl.coerceToLong(Object)"})
  void testCoerceToLong_whenEmptyString_thenReturnLongValueIsZero() {
    // Arrange, Act and Assert
    assertEquals(0L, new TypeConverterImpl().coerceToLong("").longValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToLong(Object)}.
   *
   * <ul>
   *   <li>When forty-two.
   *   <li>Then return longValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToLong(Object)}
   */
  @Test
  @DisplayName("Test coerceToLong(Object); when forty-two; then return longValue is forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Long TypeConverterImpl.coerceToLong(Object)"})
  void testCoerceToLong_whenFortyTwo_thenReturnLongValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(42L, new TypeConverterImpl().coerceToLong(42L).longValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToLong(Object)}.
   *
   * <ul>
   *   <li>When forty-two.
   *   <li>Then return longValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToLong(Object)}
   */
  @Test
  @DisplayName("Test coerceToLong(Object); when forty-two; then return longValue is forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Long TypeConverterImpl.coerceToLong(Object)"})
  void testCoerceToLong_whenFortyTwo_thenReturnLongValueIsFortyTwo2() {
    // Arrange, Act and Assert
    assertEquals(42L, new TypeConverterImpl().coerceToLong(42).longValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToLong(Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return longValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToLong(Object)}
   */
  @Test
  @DisplayName("Test coerceToLong(Object); when 'null'; then return longValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Long TypeConverterImpl.coerceToLong(Object)"})
  void testCoerceToLong_whenNull_thenReturnLongValueIsZero() {
    // Arrange, Act and Assert
    assertEquals(0L, new TypeConverterImpl().coerceToLong(null).longValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToLong(Object)}.
   *
   * <ul>
   *   <li>When {@code Value}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToLong(Object)}
   */
  @Test
  @DisplayName("Test coerceToLong(Object); when 'Value'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Long TypeConverterImpl.coerceToLong(Object)"})
  void testCoerceToLong_whenValue_thenThrowELException() {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> new TypeConverterImpl().coerceToLong("Value"));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToInteger(Object)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return intValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToInteger(Object)}
   */
  @Test
  @DisplayName("Test coerceToInteger(Object); when '42'; then return intValue is forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Integer TypeConverterImpl.coerceToInteger(Object)"})
  void testCoerceToInteger_when42_thenReturnIntValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(42, new TypeConverterImpl().coerceToInteger("42").intValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToInteger(Object)}.
   *
   * <ul>
   *   <li>When {@code A}.
   *   <li>Then return intValue is sixty-five.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToInteger(Object)}
   */
  @Test
  @DisplayName("Test coerceToInteger(Object); when 'A'; then return intValue is sixty-five")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Integer TypeConverterImpl.coerceToInteger(Object)"})
  void testCoerceToInteger_whenA_thenReturnIntValueIsSixtyFive() {
    // Arrange, Act and Assert
    assertEquals(65, new TypeConverterImpl().coerceToInteger((byte) 'A').intValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToInteger(Object)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return intValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToInteger(Object)}
   */
  @Test
  @DisplayName("Test coerceToInteger(Object); when empty string; then return intValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Integer TypeConverterImpl.coerceToInteger(Object)"})
  void testCoerceToInteger_whenEmptyString_thenReturnIntValueIsZero() {
    // Arrange, Act and Assert
    assertEquals(0, new TypeConverterImpl().coerceToInteger("").intValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToInteger(Object)}.
   *
   * <ul>
   *   <li>When forty-two.
   *   <li>Then return intValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToInteger(Object)}
   */
  @Test
  @DisplayName("Test coerceToInteger(Object); when forty-two; then return intValue is forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Integer TypeConverterImpl.coerceToInteger(Object)"})
  void testCoerceToInteger_whenFortyTwo_thenReturnIntValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(42, new TypeConverterImpl().coerceToInteger(42).intValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToInteger(Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return intValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToInteger(Object)}
   */
  @Test
  @DisplayName("Test coerceToInteger(Object); when 'null'; then return intValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Integer TypeConverterImpl.coerceToInteger(Object)"})
  void testCoerceToInteger_whenNull_thenReturnIntValueIsZero() {
    // Arrange, Act and Assert
    assertEquals(0, new TypeConverterImpl().coerceToInteger(null).intValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToInteger(Object)}.
   *
   * <ul>
   *   <li>When {@code Value}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToInteger(Object)}
   */
  @Test
  @DisplayName("Test coerceToInteger(Object); when 'Value'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Integer TypeConverterImpl.coerceToInteger(Object)"})
  void testCoerceToInteger_whenValue_thenThrowELException() {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> new TypeConverterImpl().coerceToInteger("Value"));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToShort(Object)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return shortValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToShort(Object)}
   */
  @Test
  @DisplayName("Test coerceToShort(Object); when '42'; then return shortValue is forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Short TypeConverterImpl.coerceToShort(Object)"})
  void testCoerceToShort_when42_thenReturnShortValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals((short) 42, new TypeConverterImpl().coerceToShort("42").shortValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToShort(Object)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return shortValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToShort(Object)}
   */
  @Test
  @DisplayName("Test coerceToShort(Object); when empty string; then return shortValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Short TypeConverterImpl.coerceToShort(Object)"})
  void testCoerceToShort_whenEmptyString_thenReturnShortValueIsZero() {
    // Arrange, Act and Assert
    assertEquals((short) 0, new TypeConverterImpl().coerceToShort("").shortValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToShort(Object)}.
   *
   * <ul>
   *   <li>When forty-two.
   *   <li>Then return shortValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToShort(Object)}
   */
  @Test
  @DisplayName("Test coerceToShort(Object); when forty-two; then return shortValue is forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Short TypeConverterImpl.coerceToShort(Object)"})
  void testCoerceToShort_whenFortyTwo_thenReturnShortValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals((short) 42, new TypeConverterImpl().coerceToShort(42).shortValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToShort(Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return shortValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToShort(Object)}
   */
  @Test
  @DisplayName("Test coerceToShort(Object); when 'null'; then return shortValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Short TypeConverterImpl.coerceToShort(Object)"})
  void testCoerceToShort_whenNull_thenReturnShortValueIsZero() {
    // Arrange, Act and Assert
    assertEquals((short) 0, new TypeConverterImpl().coerceToShort(null).shortValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToShort(Object)}.
   *
   * <ul>
   *   <li>When one.
   *   <li>Then return shortValue is one.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToShort(Object)}
   */
  @Test
  @DisplayName("Test coerceToShort(Object); when one; then return shortValue is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Short TypeConverterImpl.coerceToShort(Object)"})
  void testCoerceToShort_whenOne_thenReturnShortValueIsOne() {
    // Arrange, Act and Assert
    assertEquals((short) 1, new TypeConverterImpl().coerceToShort((short) 1).shortValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToShort(Object)}.
   *
   * <ul>
   *   <li>When {@code Value}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToShort(Object)}
   */
  @Test
  @DisplayName("Test coerceToShort(Object); when 'Value'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Short TypeConverterImpl.coerceToShort(Object)"})
  void testCoerceToShort_whenValue_thenThrowELException() {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> new TypeConverterImpl().coerceToShort("Value"));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToByte(Object)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return byteValue is {@code *}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToByte(Object)}
   */
  @Test
  @DisplayName("Test coerceToByte(Object); when '42'; then return byteValue is '*'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Byte TypeConverterImpl.coerceToByte(Object)"})
  void testCoerceToByte_when42_thenReturnByteValueIsAsterisk() {
    // Arrange, Act and Assert
    assertEquals('*', new TypeConverterImpl().coerceToByte("42").byteValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToByte(Object)}.
   *
   * <ul>
   *   <li>When {@code A}.
   *   <li>Then return byteValue is {@code A}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToByte(Object)}
   */
  @Test
  @DisplayName("Test coerceToByte(Object); when 'A'; then return byteValue is 'A'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Byte TypeConverterImpl.coerceToByte(Object)"})
  void testCoerceToByte_whenA_thenReturnByteValueIsA() {
    // Arrange, Act and Assert
    assertEquals('A', new TypeConverterImpl().coerceToByte((byte) 'A').byteValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToByte(Object)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return byteValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToByte(Object)}
   */
  @Test
  @DisplayName("Test coerceToByte(Object); when empty string; then return byteValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Byte TypeConverterImpl.coerceToByte(Object)"})
  void testCoerceToByte_whenEmptyString_thenReturnByteValueIsZero() {
    // Arrange, Act and Assert
    assertEquals((byte) 0, new TypeConverterImpl().coerceToByte("").byteValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToByte(Object)}.
   *
   * <ul>
   *   <li>When forty-two.
   *   <li>Then return byteValue is {@code *}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToByte(Object)}
   */
  @Test
  @DisplayName("Test coerceToByte(Object); when forty-two; then return byteValue is '*'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Byte TypeConverterImpl.coerceToByte(Object)"})
  void testCoerceToByte_whenFortyTwo_thenReturnByteValueIsAsterisk() {
    // Arrange, Act and Assert
    assertEquals('*', new TypeConverterImpl().coerceToByte(42).byteValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToByte(Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return byteValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToByte(Object)}
   */
  @Test
  @DisplayName("Test coerceToByte(Object); when 'null'; then return byteValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Byte TypeConverterImpl.coerceToByte(Object)"})
  void testCoerceToByte_whenNull_thenReturnByteValueIsZero() {
    // Arrange, Act and Assert
    assertEquals((byte) 0, new TypeConverterImpl().coerceToByte(null).byteValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToByte(Object)}.
   *
   * <ul>
   *   <li>When {@code Value}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToByte(Object)}
   */
  @Test
  @DisplayName("Test coerceToByte(Object); when 'Value'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Byte TypeConverterImpl.coerceToByte(Object)"})
  void testCoerceToByte_whenValue_thenThrowELException() {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> new TypeConverterImpl().coerceToByte("Value"));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToString(Object)}.
   *
   * <ul>
   *   <li>When {@code COMMON}.
   *   <li>Then return {@code COMMON}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToString(Object)}
   */
  @Test
  @DisplayName("Test coerceToString(Object); when 'COMMON'; then return 'COMMON'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String TypeConverterImpl.coerceToString(Object)"})
  void testCoerceToString_whenCommon_thenReturnCommon() {
    // Arrange, Act and Assert
    assertEquals("COMMON", new TypeConverterImpl().coerceToString(UnicodeScript.COMMON));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToString(Object)}.
   *
   * <ul>
   *   <li>When forty-two.
   *   <li>Then return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToString(Object)}
   */
  @Test
  @DisplayName("Test coerceToString(Object); when forty-two; then return '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String TypeConverterImpl.coerceToString(Object)"})
  void testCoerceToString_whenFortyTwo_thenReturn42() {
    // Arrange, Act and Assert
    assertEquals("42", new TypeConverterImpl().coerceToString(42));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToString(Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToString(Object)}
   */
  @Test
  @DisplayName("Test coerceToString(Object); when 'null'; then return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String TypeConverterImpl.coerceToString(Object)"})
  void testCoerceToString_whenNull_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals("", new TypeConverterImpl().coerceToString(null));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToString(Object)}.
   *
   * <ul>
   *   <li>When {@code Value}.
   *   <li>Then return {@code Value}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToString(Object)}
   */
  @Test
  @DisplayName("Test coerceToString(Object); when 'Value'; then return 'Value'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String TypeConverterImpl.coerceToString(Object)"})
  void testCoerceToString_whenValue_thenReturnValue() {
    // Arrange, Act and Assert
    assertEquals("Value", new TypeConverterImpl().coerceToString("Value"));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToEnum(Object, Class)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToEnum(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToEnum(Object, Class); when empty string; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.Enum TypeConverterImpl.coerceToEnum(Object, Class)"})
  void testCoerceToEnum_whenEmptyString_thenReturnNull() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<BaselineResizeBehavior> type = BaselineResizeBehavior.class;

    // Act and Assert
    assertNull(typeConverterImpl.coerceToEnum("", type));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToEnum(Object, Class)}.
   *
   * <ul>
   *   <li>When forty-two.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToEnum(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToEnum(Object, Class); when forty-two; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.Enum TypeConverterImpl.coerceToEnum(Object, Class)"})
  void testCoerceToEnum_whenFortyTwo_thenThrowELException() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<BaselineResizeBehavior> type = BaselineResizeBehavior.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.coerceToEnum(42, type));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToEnum(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToEnum(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToEnum(Object, Class); when 'null'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.Enum TypeConverterImpl.coerceToEnum(Object, Class)"})
  void testCoerceToEnum_whenNull_thenReturnNull() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<BaselineResizeBehavior> type = BaselineResizeBehavior.class;

    // Act and Assert
    assertNull(typeConverterImpl.coerceToEnum(null, type));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToEnum(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code Value}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToEnum(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToEnum(Object, Class); when 'Value'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.Enum TypeConverterImpl.coerceToEnum(Object, Class)"})
  void testCoerceToEnum_whenValue_thenThrowELException() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<BaselineResizeBehavior> type = BaselineResizeBehavior.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.coerceToEnum("Value", type));
  }

  /**
   * Test {@link TypeConverterImpl#coerceStringToType(String, Class)}.
   *
   * <ul>
   *   <li>When {@code Boolean}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceStringToType(String, Class)}
   */
  @Test
  @DisplayName(
      "Test coerceStringToType(String, Class); when 'java.lang.Boolean'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceStringToType(String, Class)"})
  void testCoerceStringToType_whenJavaLangBoolean_thenReturnNull() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Boolean> type = Boolean.class;

    // Act and Assert
    assertNull(typeConverterImpl.coerceStringToType("", type));
  }

  /**
   * Test {@link TypeConverterImpl#coerceStringToType(String, Class)}.
   *
   * <ul>
   *   <li>When {@code Boolean}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceStringToType(String, Class)}
   */
  @Test
  @DisplayName(
      "Test coerceStringToType(String, Class); when 'java.lang.Boolean'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceStringToType(String, Class)"})
  void testCoerceStringToType_whenJavaLangBoolean_thenThrowELException() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Boolean> type = Boolean.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.coerceStringToType("42", type));
  }

  /**
   * Test {@link TypeConverterImpl#coerceStringToType(String, Class)}.
   *
   * <ul>
   *   <li>When {@code Byte}.
   *   <li>Then return byteValue is {@code *}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceStringToType(String, Class)}
   */
  @Test
  @DisplayName(
      "Test coerceStringToType(String, Class); when 'java.lang.Byte'; then return byteValue is '*'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceStringToType(String, Class)"})
  void testCoerceStringToType_whenJavaLangByte_thenReturnByteValueIsAsterisk() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Byte> type = Byte.class;

    // Act and Assert
    assertEquals('*', ((Byte) typeConverterImpl.coerceStringToType("42", type)).byteValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceStringToType(String, Class)}.
   *
   * <ul>
   *   <li>When {@code Object}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceStringToType(String, Class)}
   */
  @Test
  @DisplayName(
      "Test coerceStringToType(String, Class); when 'java.lang.Object'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceStringToType(String, Class)"})
  void testCoerceStringToType_whenJavaLangObject_thenReturnNull() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> type = Object.class;

    // Act and Assert
    assertNull(typeConverterImpl.coerceStringToType("", type));
  }

  /**
   * Test {@link TypeConverterImpl#coerceStringToType(String, Class)}.
   *
   * <ul>
   *   <li>When {@code Object}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceStringToType(String, Class)}
   */
  @Test
  @DisplayName(
      "Test coerceStringToType(String, Class); when 'java.lang.Object'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceStringToType(String, Class)"})
  void testCoerceStringToType_whenJavaLangObject_thenThrowELException() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> type = Object.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.coerceStringToType("42", type));
  }

  /**
   * Test {@link TypeConverterImpl#coerceStringToType(String, Class)}.
   *
   * <ul>
   *   <li>When {@code String}.
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceStringToType(String, Class)}
   */
  @Test
  @DisplayName(
      "Test coerceStringToType(String, Class); when 'java.lang.String'; then return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceStringToType(String, Class)"})
  void testCoerceStringToType_whenJavaLangString_thenReturnEmptyString() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<String> type = String.class;

    // Act and Assert
    assertEquals("", typeConverterImpl.coerceStringToType("", type));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return doubleValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when '42'; then return doubleValue is forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_when42_thenReturnDoubleValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(
        42.0d, ((Double) new TypeConverterImpl().coerceToType("42", Double.TYPE)).doubleValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return floatValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when '42'; then return floatValue is forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_when42_thenReturnFloatValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(
        42.0f, ((Float) new TypeConverterImpl().coerceToType("42", Float.TYPE)).floatValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return intValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when '42'; then return intValue is forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_when42_thenReturnIntValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(
        42, ((Integer) new TypeConverterImpl().coerceToType("42", Integer.TYPE)).intValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return longValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when '42'; then return longValue is forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_when42_thenReturnLongValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(42L, ((Long) new TypeConverterImpl().coerceToType("42", Long.TYPE)).longValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return shortValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when '42'; then return shortValue is forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_when42_thenReturnShortValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(
        (short) 42, ((Short) new TypeConverterImpl().coerceToType("42", Short.TYPE)).shortValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code A}.
   *   <li>Then return byteValue is {@code A}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when 'A'; then return byteValue is 'A'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenA_thenReturnByteValueIsA() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Byte> type = Byte.class;

    // Act and Assert
    assertEquals('A', ((Byte) typeConverterImpl.coerceToType((byte) 'A', type)).byteValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code A}.
   *   <li>Then return intValue is sixty-five.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when 'A'; then return intValue is sixty-five")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenA_thenReturnIntValueIsSixtyFive() {
    // Arrange, Act and Assert
    assertEquals(
        65, ((Integer) new TypeConverterImpl().coerceToType((byte) 'A', Integer.TYPE)).intValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return doubleValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName(
      "Test coerceToType(Object, Class); when empty string; then return doubleValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenEmptyString_thenReturnDoubleValueIsZero() {
    // Arrange, Act and Assert
    assertEquals(
        0.0d, ((Double) new TypeConverterImpl().coerceToType("", Double.TYPE)).doubleValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return floatValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName(
      "Test coerceToType(Object, Class); when empty string; then return floatValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenEmptyString_thenReturnFloatValueIsZero() {
    // Arrange, Act and Assert
    assertEquals(0.0f, ((Float) new TypeConverterImpl().coerceToType("", Float.TYPE)).floatValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return intValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when empty string; then return intValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenEmptyString_thenReturnIntValueIsZero() {
    // Arrange, Act and Assert
    assertEquals(0, ((Integer) new TypeConverterImpl().coerceToType("", Integer.TYPE)).intValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return longValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when empty string; then return longValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenEmptyString_thenReturnLongValueIsZero() {
    // Arrange, Act and Assert
    assertEquals(0L, ((Long) new TypeConverterImpl().coerceToType("", Long.TYPE)).longValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return shortValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName(
      "Test coerceToType(Object, Class); when empty string; then return shortValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenEmptyString_thenReturnShortValueIsZero() {
    // Arrange, Act and Assert
    assertEquals(
        (short) 0, ((Short) new TypeConverterImpl().coerceToType("", Short.TYPE)).shortValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When end of text.
   *   <li>Then return byteValue is three.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when end of text; then return byteValue is three")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenEndOfText_thenReturnByteValueIsThree() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Byte> type = Byte.class;

    // Act and Assert
    assertEquals((byte) 3, ((Byte) typeConverterImpl.coerceToType('\u0003', type)).byteValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When end of text.
   *   <li>Then return doubleValue is three.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName(
      "Test coerceToType(Object, Class); when end of text; then return doubleValue is three")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenEndOfText_thenReturnDoubleValueIsThree() {
    // Arrange, Act and Assert
    assertEquals(
        3.0d, ((Double) new TypeConverterImpl().coerceToType('\u0003', Double.TYPE)).doubleValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When end of text.
   *   <li>Then return floatValue is three.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName(
      "Test coerceToType(Object, Class); when end of text; then return floatValue is three")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenEndOfText_thenReturnFloatValueIsThree() {
    // Arrange, Act and Assert
    assertEquals(
        3.0f, ((Float) new TypeConverterImpl().coerceToType('\u0003', Float.TYPE)).floatValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When end of text.
   *   <li>Then return intValue is three.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when end of text; then return intValue is three")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenEndOfText_thenReturnIntValueIsThree() {
    // Arrange, Act and Assert
    assertEquals(
        3, ((Integer) new TypeConverterImpl().coerceToType('\u0003', Integer.TYPE)).intValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When end of text.
   *   <li>Then return longValue is three.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when end of text; then return longValue is three")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenEndOfText_thenReturnLongValueIsThree() {
    // Arrange, Act and Assert
    assertEquals(
        3L, ((Long) new TypeConverterImpl().coerceToType('\u0003', Long.TYPE)).longValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When end of text.
   *   <li>Then return shortValue is three.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName(
      "Test coerceToType(Object, Class); when end of text; then return shortValue is three")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenEndOfText_thenReturnShortValueIsThree() {
    // Arrange, Act and Assert
    assertEquals(
        (short) 3,
        ((Short) new TypeConverterImpl().coerceToType('\u0003', Short.TYPE)).shortValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code Boolean}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when 'java.lang.Boolean'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenJavaLangBoolean_thenReturnFalse() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Boolean> type = Boolean.class;

    // Act and Assert
    assertFalse((Boolean) typeConverterImpl.coerceToType("Value", type));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code Boolean}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when 'java.lang.Boolean'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenJavaLangBoolean_thenThrowELException() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Boolean> type = Boolean.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.coerceToType(42, type));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code Byte}.
   *   <li>Then return byteValue is {@code *}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName(
      "Test coerceToType(Object, Class); when 'java.lang.Byte'; then return byteValue is '*'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenJavaLangByte_thenReturnByteValueIsAsterisk() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Byte> type = Byte.class;

    // Act and Assert
    assertEquals('*', ((Byte) typeConverterImpl.coerceToType(42, type)).byteValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code Byte}.
   *   <li>Then return byteValue is {@code *}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName(
      "Test coerceToType(Object, Class); when 'java.lang.Byte'; then return byteValue is '*'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenJavaLangByte_thenReturnByteValueIsAsterisk2() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Byte> type = Byte.class;

    // Act and Assert
    assertEquals('*', ((Byte) typeConverterImpl.coerceToType("42", type)).byteValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code Byte}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when 'java.lang.Byte'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenJavaLangByte_thenThrowELException() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Byte> type = Byte.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.coerceToType("Value", type));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code Character}.
   *   <li>Then return charValue is {@code V}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName(
      "Test coerceToType(Object, Class); when 'java.lang.Character'; then return charValue is 'V'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenJavaLangCharacter_thenReturnCharValueIsV() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Character> type = Character.class;

    // Act and Assert
    assertEquals('V', ((Character) typeConverterImpl.coerceToType("Value", type)).charValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code Object}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when 'java.lang.Object'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenJavaLangObject_thenReturnNull() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> type = Object.class;

    // Act and Assert
    assertNull(typeConverterImpl.coerceToType(null, type));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code Object}.
   *   <li>Then return {@code Value}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when 'java.lang.Object'; then return 'Value'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenJavaLangObject_thenReturnValue() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> type = Object.class;

    // Act and Assert
    assertEquals("Value", typeConverterImpl.coerceToType("Value", type));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code Object}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when 'java.lang.Object'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenJavaLangObject_thenThrowELException() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.coerceToType(forNameResult, Long.TYPE));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code Object}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when 'java.lang.Object'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenJavaLangObject_thenThrowELException2() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(
        ELException.class, () -> typeConverterImpl.coerceToType(forNameResult, Double.TYPE));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code Object}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when 'java.lang.Object'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenJavaLangObject_thenThrowELException3() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(
        ELException.class, () -> typeConverterImpl.coerceToType(forNameResult, Integer.TYPE));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code Object}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when 'java.lang.Object'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenJavaLangObject_thenThrowELException4() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(
        ELException.class, () -> typeConverterImpl.coerceToType(forNameResult, Float.TYPE));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code Object}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when 'java.lang.Object'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenJavaLangObject_thenThrowELException5() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(
        ELException.class, () -> typeConverterImpl.coerceToType(forNameResult, Short.TYPE));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When one.
   *   <li>Then return shortValue is one.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when one; then return shortValue is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenOne_thenReturnShortValueIsOne() {
    // Arrange, Act and Assert
    assertEquals(
        (short) 1,
        ((Short) new TypeConverterImpl().coerceToType((short) 1, Short.TYPE)).shortValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When start of heading.
   *   <li>Then return charValue is start of heading.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName(
      "Test coerceToType(Object, Class); when start of heading; then return charValue is start of heading")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenStartOfHeading_thenReturnCharValueIsStartOfHeading() {
    // Arrange, Act and Assert
    assertEquals(
        '\u0001',
        ((Character) new TypeConverterImpl().coerceToType('\u0001', Character.TYPE)).charValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When ten.
   *   <li>Then return doubleValue is ten.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when ten; then return doubleValue is ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenTen_thenReturnDoubleValueIsTen() {
    // Arrange, Act and Assert
    assertEquals(
        10.0d, ((Double) new TypeConverterImpl().coerceToType(10.0d, Double.TYPE)).doubleValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When ten.
   *   <li>Then return floatValue is ten.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when ten; then return floatValue is ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenTen_thenReturnFloatValueIsTen() {
    // Arrange, Act and Assert
    assertEquals(
        10.0f, ((Float) new TypeConverterImpl().coerceToType(10.0f, Float.TYPE)).floatValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code true}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when 'true'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenTrue_thenReturnTrue() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Boolean> type = Boolean.class;

    // Act and Assert
    assertTrue((Boolean) typeConverterImpl.coerceToType(true, type));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code true}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when 'true'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenTrue_thenThrowELException() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Byte> type = Byte.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.coerceToType(true, type));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Byte#TYPE}.
   *   <li>Then return byteValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when TYPE; then return byteValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenType_thenReturnByteValueIsZero() {
    // Arrange, Act and Assert
    assertEquals(
        (byte) 0, ((Byte) new TypeConverterImpl().coerceToType(null, Byte.TYPE)).byteValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Byte#TYPE}.
   *   <li>Then return byteValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when TYPE; then return byteValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenType_thenReturnByteValueIsZero2() {
    // Arrange, Act and Assert
    assertEquals(
        (byte) 0, ((Byte) new TypeConverterImpl().coerceToType("", Byte.TYPE)).byteValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Character#TYPE}.
   *   <li>Then return charValue is {@code 4}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when TYPE; then return charValue is '4'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenType_thenReturnCharValueIs4() {
    // Arrange, Act and Assert
    assertEquals(
        '4', ((Character) new TypeConverterImpl().coerceToType("42", Character.TYPE)).charValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Character#TYPE}.
   *   <li>Then return charValue is {@code *}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when TYPE; then return charValue is '*'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenType_thenReturnCharValueIsAsterisk() {
    // Arrange, Act and Assert
    assertEquals(
        '*', ((Character) new TypeConverterImpl().coerceToType(42, Character.TYPE)).charValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Character#TYPE}.
   *   <li>Then return charValue is null.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when TYPE; then return charValue is null")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenType_thenReturnCharValueIsNull() {
    // Arrange, Act and Assert
    assertEquals(
        '\u0000',
        ((Character) new TypeConverterImpl().coerceToType(null, Character.TYPE)).charValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Character#TYPE}.
   *   <li>Then return charValue is null.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when TYPE; then return charValue is null")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenType_thenReturnCharValueIsNull2() {
    // Arrange, Act and Assert
    assertEquals(
        '\u0000',
        ((Character) new TypeConverterImpl().coerceToType("", Character.TYPE)).charValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Double#TYPE}.
   *   <li>Then return doubleValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when TYPE; then return doubleValue is forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenType_thenReturnDoubleValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(
        42.0d, ((Double) new TypeConverterImpl().coerceToType(42, Double.TYPE)).doubleValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Double#TYPE}.
   *   <li>Then return doubleValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when TYPE; then return doubleValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenType_thenReturnDoubleValueIsZero() {
    // Arrange, Act and Assert
    assertEquals(
        0.0d, ((Double) new TypeConverterImpl().coerceToType(null, Double.TYPE)).doubleValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Boolean#TYPE}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when TYPE; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenType_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((Boolean) new TypeConverterImpl().coerceToType(null, Boolean.TYPE));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Boolean#TYPE}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when TYPE; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenType_thenReturnFalse2() {
    // Arrange, Act and Assert
    assertFalse((Boolean) new TypeConverterImpl().coerceToType("", Boolean.TYPE));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Float#TYPE}.
   *   <li>Then return floatValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when TYPE; then return floatValue is forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenType_thenReturnFloatValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(
        42.0f, ((Float) new TypeConverterImpl().coerceToType(42, Float.TYPE)).floatValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Float#TYPE}.
   *   <li>Then return floatValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when TYPE; then return floatValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenType_thenReturnFloatValueIsZero() {
    // Arrange, Act and Assert
    assertEquals(
        0.0f, ((Float) new TypeConverterImpl().coerceToType(null, Float.TYPE)).floatValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Integer#TYPE}.
   *   <li>Then return intValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when TYPE; then return intValue is forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenType_thenReturnIntValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(42, ((Integer) new TypeConverterImpl().coerceToType(42, Integer.TYPE)).intValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Integer#TYPE}.
   *   <li>Then return intValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when TYPE; then return intValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenType_thenReturnIntValueIsZero() {
    // Arrange, Act and Assert
    assertEquals(
        0, ((Integer) new TypeConverterImpl().coerceToType(null, Integer.TYPE)).intValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Long#TYPE}.
   *   <li>Then return longValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when TYPE; then return longValue is forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenType_thenReturnLongValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(42L, ((Long) new TypeConverterImpl().coerceToType(42, Long.TYPE)).longValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Long#TYPE}.
   *   <li>Then return longValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when TYPE; then return longValue is forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenType_thenReturnLongValueIsFortyTwo2() {
    // Arrange, Act and Assert
    assertEquals(42L, ((Long) new TypeConverterImpl().coerceToType(42L, Long.TYPE)).longValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Long#TYPE}.
   *   <li>Then return longValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when TYPE; then return longValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenType_thenReturnLongValueIsZero() {
    // Arrange, Act and Assert
    assertEquals(0L, ((Long) new TypeConverterImpl().coerceToType(null, Long.TYPE)).longValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Short#TYPE}.
   *   <li>Then return shortValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when TYPE; then return shortValue is forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenType_thenReturnShortValueIsFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(
        (short) 42, ((Short) new TypeConverterImpl().coerceToType(42, Short.TYPE)).shortValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Short#TYPE}.
   *   <li>Then return shortValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when TYPE; then return shortValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenType_thenReturnShortValueIsZero() {
    // Arrange, Act and Assert
    assertEquals(
        (short) 0, ((Short) new TypeConverterImpl().coerceToType(null, Short.TYPE)).shortValue());
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Long#TYPE}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when TYPE; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenType_thenThrowELException() {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> new TypeConverterImpl().coerceToType("Value", Long.TYPE));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Double#TYPE}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when TYPE; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenType_thenThrowELException2() {
    // Arrange, Act and Assert
    assertThrows(
        ELException.class, () -> new TypeConverterImpl().coerceToType("Value", Double.TYPE));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Integer#TYPE}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when TYPE; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenType_thenThrowELException3() {
    // Arrange, Act and Assert
    assertThrows(
        ELException.class, () -> new TypeConverterImpl().coerceToType("Value", Integer.TYPE));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Float#TYPE}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when TYPE; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenType_thenThrowELException4() {
    // Arrange, Act and Assert
    assertThrows(
        ELException.class, () -> new TypeConverterImpl().coerceToType("Value", Float.TYPE));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Short#TYPE}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when TYPE; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenType_thenThrowELException5() {
    // Arrange, Act and Assert
    assertThrows(
        ELException.class, () -> new TypeConverterImpl().coerceToType("Value", Short.TYPE));
  }

  /**
   * Test {@link TypeConverterImpl#coerceToType(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Character#TYPE}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  @DisplayName("Test coerceToType(Object, Class); when TYPE; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.coerceToType(Object, Class)"})
  void testCoerceToType_whenType_thenThrowELException6() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(
        ELException.class, () -> typeConverterImpl.coerceToType(forNameResult, Character.TYPE));
  }

  /**
   * Test {@link TypeConverterImpl#equals(Object)}, and {@link TypeConverterImpl#hashCode()}.
   *
   * <ul>
   *   <li>When other is equal.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link TypeConverterImpl#equals(Object)}
   *   <li>{@link TypeConverterImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean TypeConverterImpl.equals(Object)",
    "int TypeConverterImpl.hashCode()"
  })
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    TypeConverterImpl typeConverterImpl2 = new TypeConverterImpl();

    // Act and Assert
    assertEquals(typeConverterImpl, typeConverterImpl2);
    assertEquals(typeConverterImpl.hashCode(), typeConverterImpl2.hashCode());
  }

  /**
   * Test {@link TypeConverterImpl#equals(Object)}, and {@link TypeConverterImpl#hashCode()}.
   *
   * <ul>
   *   <li>When other is same.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link TypeConverterImpl#equals(Object)}
   *   <li>{@link TypeConverterImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean TypeConverterImpl.equals(Object)",
    "int TypeConverterImpl.hashCode()"
  })
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
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean TypeConverterImpl.equals(Object)",
    "int TypeConverterImpl.hashCode()"
  })
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new TypeConverterImpl(), 1);
  }

  /**
   * Test {@link TypeConverterImpl#equals(Object)}.
   *
   * <ul>
   *   <li>When other is {@code null}.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean TypeConverterImpl.equals(Object)",
    "int TypeConverterImpl.hashCode()"
  })
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new TypeConverterImpl(), null);
  }

  /**
   * Test {@link TypeConverterImpl#equals(Object)}.
   *
   * <ul>
   *   <li>When other is wrong type.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean TypeConverterImpl.equals(Object)",
    "int TypeConverterImpl.hashCode()"
  })
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new TypeConverterImpl(), "Different type to TypeConverterImpl");
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return doubleValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when '42'; then return doubleValue is forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_when42_thenReturnDoubleValueIsFortyTwo() throws ELException {
    // Arrange, Act and Assert
    assertEquals(
        42.0d, ((Double) new TypeConverterImpl().convert("42", Double.TYPE)).doubleValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return floatValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when '42'; then return floatValue is forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_when42_thenReturnFloatValueIsFortyTwo() throws ELException {
    // Arrange, Act and Assert
    assertEquals(42.0f, ((Float) new TypeConverterImpl().convert("42", Float.TYPE)).floatValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return intValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when '42'; then return intValue is forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_when42_thenReturnIntValueIsFortyTwo() throws ELException {
    // Arrange, Act and Assert
    assertEquals(42, ((Integer) new TypeConverterImpl().convert("42", Integer.TYPE)).intValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return longValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when '42'; then return longValue is forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_when42_thenReturnLongValueIsFortyTwo() throws ELException {
    // Arrange, Act and Assert
    assertEquals(42L, ((Long) new TypeConverterImpl().convert("42", Long.TYPE)).longValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return shortValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when '42'; then return shortValue is forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_when42_thenReturnShortValueIsFortyTwo() throws ELException {
    // Arrange, Act and Assert
    assertEquals(
        (short) 42, ((Short) new TypeConverterImpl().convert("42", Short.TYPE)).shortValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code A}.
   *   <li>Then return byteValue is {@code A}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when 'A'; then return byteValue is 'A'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenA_thenReturnByteValueIsA() throws ELException {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Byte> type = Byte.class;

    // Act and Assert
    assertEquals('A', ((Byte) typeConverterImpl.convert((byte) 'A', type)).byteValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code A}.
   *   <li>Then return intValue is sixty-five.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when 'A'; then return intValue is sixty-five")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenA_thenReturnIntValueIsSixtyFive() throws ELException {
    // Arrange, Act and Assert
    assertEquals(
        65, ((Integer) new TypeConverterImpl().convert((byte) 'A', Integer.TYPE)).intValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return doubleValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when empty string; then return doubleValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenEmptyString_thenReturnDoubleValueIsZero() throws ELException {
    // Arrange, Act and Assert
    assertEquals(0.0d, ((Double) new TypeConverterImpl().convert("", Double.TYPE)).doubleValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return floatValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when empty string; then return floatValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenEmptyString_thenReturnFloatValueIsZero() throws ELException {
    // Arrange, Act and Assert
    assertEquals(0.0f, ((Float) new TypeConverterImpl().convert("", Float.TYPE)).floatValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return intValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when empty string; then return intValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenEmptyString_thenReturnIntValueIsZero() throws ELException {
    // Arrange, Act and Assert
    assertEquals(0, ((Integer) new TypeConverterImpl().convert("", Integer.TYPE)).intValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return longValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when empty string; then return longValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenEmptyString_thenReturnLongValueIsZero() throws ELException {
    // Arrange, Act and Assert
    assertEquals(0L, ((Long) new TypeConverterImpl().convert("", Long.TYPE)).longValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return shortValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when empty string; then return shortValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenEmptyString_thenReturnShortValueIsZero() throws ELException {
    // Arrange, Act and Assert
    assertEquals((short) 0, ((Short) new TypeConverterImpl().convert("", Short.TYPE)).shortValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When end of text.
   *   <li>Then return byteValue is three.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when end of text; then return byteValue is three")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenEndOfText_thenReturnByteValueIsThree() throws ELException {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Byte> type = Byte.class;

    // Act and Assert
    assertEquals((byte) 3, ((Byte) typeConverterImpl.convert('\u0003', type)).byteValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When end of text.
   *   <li>Then return doubleValue is three.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when end of text; then return doubleValue is three")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenEndOfText_thenReturnDoubleValueIsThree() throws ELException {
    // Arrange, Act and Assert
    assertEquals(
        3.0d, ((Double) new TypeConverterImpl().convert('\u0003', Double.TYPE)).doubleValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When end of text.
   *   <li>Then return floatValue is three.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when end of text; then return floatValue is three")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenEndOfText_thenReturnFloatValueIsThree() throws ELException {
    // Arrange, Act and Assert
    assertEquals(
        3.0f, ((Float) new TypeConverterImpl().convert('\u0003', Float.TYPE)).floatValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When end of text.
   *   <li>Then return intValue is three.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when end of text; then return intValue is three")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenEndOfText_thenReturnIntValueIsThree() throws ELException {
    // Arrange, Act and Assert
    assertEquals(3, ((Integer) new TypeConverterImpl().convert('\u0003', Integer.TYPE)).intValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When end of text.
   *   <li>Then return longValue is three.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when end of text; then return longValue is three")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenEndOfText_thenReturnLongValueIsThree() throws ELException {
    // Arrange, Act and Assert
    assertEquals(3L, ((Long) new TypeConverterImpl().convert('\u0003', Long.TYPE)).longValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When end of text.
   *   <li>Then return shortValue is three.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when end of text; then return shortValue is three")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenEndOfText_thenReturnShortValueIsThree() throws ELException {
    // Arrange, Act and Assert
    assertEquals(
        (short) 3, ((Short) new TypeConverterImpl().convert('\u0003', Short.TYPE)).shortValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code Boolean}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when 'java.lang.Boolean'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenJavaLangBoolean_thenReturnFalse() throws ELException {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Boolean> type = Boolean.class;

    // Act and Assert
    assertFalse((Boolean) typeConverterImpl.convert("Value", type));
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code Boolean}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when 'java.lang.Boolean'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenJavaLangBoolean_thenThrowELException() throws ELException {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Boolean> type = Boolean.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.convert(42, type));
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code Byte}.
   *   <li>Then return byteValue is {@code *}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when 'java.lang.Byte'; then return byteValue is '*'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenJavaLangByte_thenReturnByteValueIsAsterisk() throws ELException {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Byte> type = Byte.class;

    // Act and Assert
    assertEquals('*', ((Byte) typeConverterImpl.convert(42, type)).byteValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code Byte}.
   *   <li>Then return byteValue is {@code *}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when 'java.lang.Byte'; then return byteValue is '*'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenJavaLangByte_thenReturnByteValueIsAsterisk2() throws ELException {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Byte> type = Byte.class;

    // Act and Assert
    assertEquals('*', ((Byte) typeConverterImpl.convert("42", type)).byteValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code Byte}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when 'java.lang.Byte'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenJavaLangByte_thenThrowELException() throws ELException {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Byte> type = Byte.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.convert("Value", type));
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code Character}.
   *   <li>Then return charValue is {@code V}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName(
      "Test convert(Object, Class); when 'java.lang.Character'; then return charValue is 'V'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenJavaLangCharacter_thenReturnCharValueIsV() throws ELException {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Character> type = Character.class;

    // Act and Assert
    assertEquals('V', ((Character) typeConverterImpl.convert("Value", type)).charValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code Object}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when 'java.lang.Object'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenJavaLangObject_thenReturnNull() throws ELException {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> type = Object.class;

    // Act and Assert
    assertNull(typeConverterImpl.convert(null, type));
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code Object}.
   *   <li>Then return {@code Value}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when 'java.lang.Object'; then return 'Value'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenJavaLangObject_thenReturnValue() throws ELException {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> type = Object.class;

    // Act and Assert
    assertEquals("Value", typeConverterImpl.convert("Value", type));
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code Object}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when 'java.lang.Object'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenJavaLangObject_thenThrowELException() throws ELException {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.convert(forNameResult, Long.TYPE));
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code Object}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when 'java.lang.Object'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenJavaLangObject_thenThrowELException2() throws ELException {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.convert(forNameResult, Double.TYPE));
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code Object}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when 'java.lang.Object'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenJavaLangObject_thenThrowELException3() throws ELException {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.convert(forNameResult, Integer.TYPE));
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code Object}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when 'java.lang.Object'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenJavaLangObject_thenThrowELException4() throws ELException {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.convert(forNameResult, Float.TYPE));
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code Object}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when 'java.lang.Object'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenJavaLangObject_thenThrowELException5() throws ELException {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.convert(forNameResult, Short.TYPE));
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When one.
   *   <li>Then return shortValue is one.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when one; then return shortValue is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenOne_thenReturnShortValueIsOne() throws ELException {
    // Arrange, Act and Assert
    assertEquals(
        (short) 1, ((Short) new TypeConverterImpl().convert((short) 1, Short.TYPE)).shortValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When start of heading.
   *   <li>Then return charValue is start of heading.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName(
      "Test convert(Object, Class); when start of heading; then return charValue is start of heading")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenStartOfHeading_thenReturnCharValueIsStartOfHeading() throws ELException {
    // Arrange, Act and Assert
    assertEquals(
        '\u0001',
        ((Character) new TypeConverterImpl().convert('\u0001', Character.TYPE)).charValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When ten.
   *   <li>Then return doubleValue is ten.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when ten; then return doubleValue is ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenTen_thenReturnDoubleValueIsTen() throws ELException {
    // Arrange, Act and Assert
    assertEquals(
        10.0d, ((Double) new TypeConverterImpl().convert(10.0d, Double.TYPE)).doubleValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When ten.
   *   <li>Then return floatValue is ten.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when ten; then return floatValue is ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenTen_thenReturnFloatValueIsTen() throws ELException {
    // Arrange, Act and Assert
    assertEquals(10.0f, ((Float) new TypeConverterImpl().convert(10.0f, Float.TYPE)).floatValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code true}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when 'true'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenTrue_thenReturnTrue() throws ELException {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Boolean> type = Boolean.class;

    // Act and Assert
    assertTrue((Boolean) typeConverterImpl.convert(true, type));
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code true}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when 'true'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenTrue_thenThrowELException() throws ELException {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Byte> type = Byte.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.convert(true, type));
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Byte#TYPE}.
   *   <li>Then return byteValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when TYPE; then return byteValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenType_thenReturnByteValueIsZero() throws ELException {
    // Arrange, Act and Assert
    assertEquals((byte) 0, ((Byte) new TypeConverterImpl().convert(null, Byte.TYPE)).byteValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Byte#TYPE}.
   *   <li>Then return byteValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when TYPE; then return byteValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenType_thenReturnByteValueIsZero2() throws ELException {
    // Arrange, Act and Assert
    assertEquals((byte) 0, ((Byte) new TypeConverterImpl().convert("", Byte.TYPE)).byteValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Character#TYPE}.
   *   <li>Then return charValue is {@code 4}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when TYPE; then return charValue is '4'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenType_thenReturnCharValueIs4() throws ELException {
    // Arrange, Act and Assert
    assertEquals(
        '4', ((Character) new TypeConverterImpl().convert("42", Character.TYPE)).charValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Character#TYPE}.
   *   <li>Then return charValue is {@code *}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when TYPE; then return charValue is '*'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenType_thenReturnCharValueIsAsterisk() throws ELException {
    // Arrange, Act and Assert
    assertEquals(
        '*', ((Character) new TypeConverterImpl().convert(42, Character.TYPE)).charValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Character#TYPE}.
   *   <li>Then return charValue is null.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when TYPE; then return charValue is null")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenType_thenReturnCharValueIsNull() throws ELException {
    // Arrange, Act and Assert
    assertEquals(
        '\u0000', ((Character) new TypeConverterImpl().convert(null, Character.TYPE)).charValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Character#TYPE}.
   *   <li>Then return charValue is null.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when TYPE; then return charValue is null")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenType_thenReturnCharValueIsNull2() throws ELException {
    // Arrange, Act and Assert
    assertEquals(
        '\u0000', ((Character) new TypeConverterImpl().convert("", Character.TYPE)).charValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Double#TYPE}.
   *   <li>Then return doubleValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when TYPE; then return doubleValue is forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenType_thenReturnDoubleValueIsFortyTwo() throws ELException {
    // Arrange, Act and Assert
    assertEquals(42.0d, ((Double) new TypeConverterImpl().convert(42, Double.TYPE)).doubleValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Double#TYPE}.
   *   <li>Then return doubleValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when TYPE; then return doubleValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenType_thenReturnDoubleValueIsZero() throws ELException {
    // Arrange, Act and Assert
    assertEquals(0.0d, ((Double) new TypeConverterImpl().convert(null, Double.TYPE)).doubleValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Boolean#TYPE}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when TYPE; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenType_thenReturnFalse() throws ELException {
    // Arrange, Act and Assert
    assertFalse((Boolean) new TypeConverterImpl().convert(null, Boolean.TYPE));
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Boolean#TYPE}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when TYPE; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenType_thenReturnFalse2() throws ELException {
    // Arrange, Act and Assert
    assertFalse((Boolean) new TypeConverterImpl().convert("", Boolean.TYPE));
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Float#TYPE}.
   *   <li>Then return floatValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when TYPE; then return floatValue is forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenType_thenReturnFloatValueIsFortyTwo() throws ELException {
    // Arrange, Act and Assert
    assertEquals(42.0f, ((Float) new TypeConverterImpl().convert(42, Float.TYPE)).floatValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Float#TYPE}.
   *   <li>Then return floatValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when TYPE; then return floatValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenType_thenReturnFloatValueIsZero() throws ELException {
    // Arrange, Act and Assert
    assertEquals(0.0f, ((Float) new TypeConverterImpl().convert(null, Float.TYPE)).floatValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Integer#TYPE}.
   *   <li>Then return intValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when TYPE; then return intValue is forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenType_thenReturnIntValueIsFortyTwo() throws ELException {
    // Arrange, Act and Assert
    assertEquals(42, ((Integer) new TypeConverterImpl().convert(42, Integer.TYPE)).intValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Integer#TYPE}.
   *   <li>Then return intValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when TYPE; then return intValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenType_thenReturnIntValueIsZero() throws ELException {
    // Arrange, Act and Assert
    assertEquals(0, ((Integer) new TypeConverterImpl().convert(null, Integer.TYPE)).intValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Long#TYPE}.
   *   <li>Then return longValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when TYPE; then return longValue is forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenType_thenReturnLongValueIsFortyTwo() throws ELException {
    // Arrange, Act and Assert
    assertEquals(42L, ((Long) new TypeConverterImpl().convert(42, Long.TYPE)).longValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Long#TYPE}.
   *   <li>Then return longValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when TYPE; then return longValue is forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenType_thenReturnLongValueIsFortyTwo2() throws ELException {
    // Arrange, Act and Assert
    assertEquals(42L, ((Long) new TypeConverterImpl().convert(42L, Long.TYPE)).longValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Long#TYPE}.
   *   <li>Then return longValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when TYPE; then return longValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenType_thenReturnLongValueIsZero() throws ELException {
    // Arrange, Act and Assert
    assertEquals(0L, ((Long) new TypeConverterImpl().convert(null, Long.TYPE)).longValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Short#TYPE}.
   *   <li>Then return shortValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when TYPE; then return shortValue is forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenType_thenReturnShortValueIsFortyTwo() throws ELException {
    // Arrange, Act and Assert
    assertEquals(
        (short) 42, ((Short) new TypeConverterImpl().convert(42, Short.TYPE)).shortValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Short#TYPE}.
   *   <li>Then return shortValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when TYPE; then return shortValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenType_thenReturnShortValueIsZero() throws ELException {
    // Arrange, Act and Assert
    assertEquals(
        (short) 0, ((Short) new TypeConverterImpl().convert(null, Short.TYPE)).shortValue());
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Long#TYPE}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when TYPE; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenType_thenThrowELException() throws ELException {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> new TypeConverterImpl().convert("Value", Long.TYPE));
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Double#TYPE}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when TYPE; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenType_thenThrowELException2() throws ELException {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> new TypeConverterImpl().convert("Value", Double.TYPE));
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Integer#TYPE}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when TYPE; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenType_thenThrowELException3() throws ELException {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> new TypeConverterImpl().convert("Value", Integer.TYPE));
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Float#TYPE}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when TYPE; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenType_thenThrowELException4() throws ELException {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> new TypeConverterImpl().convert("Value", Float.TYPE));
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Short#TYPE}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when TYPE; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenType_thenThrowELException5() throws ELException {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> new TypeConverterImpl().convert("Value", Short.TYPE));
  }

  /**
   * Test {@link TypeConverterImpl#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@link Character#TYPE}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when TYPE; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TypeConverterImpl.convert(Object, Class)"})
  void testConvert_whenType_thenThrowELException6() throws ELException {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.convert(forNameResult, Character.TYPE));
  }
}
