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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.el.ELException;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class NumberOperationsDiffblueTest {
  /**
   * Test {@link NumberOperations#add(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.
   *   <li>When {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.
   *   <li>Then calls {@link TypeConverter#convert(Object, Class)}.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test add(TypeConverter, Object, Object); given BigDecimal(String) with '2.3'; when BigDecimal(String) with '2.3'; then calls convert(Object, Class)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.add(TypeConverter, Object, Object)"})
  void testAdd_givenBigDecimalWith23_whenBigDecimalWith23_thenCallsConvert() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenReturn(new BigDecimal("2.3"));
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act
    NumberOperations.add(converter, new BigDecimal("2.3"), "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#add(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.
   *   <li>When {@code null}.
   *   <li>Then return longValue is two.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test add(TypeConverter, Object, Object); given BigDecimal(String) with '2.3'; when 'null'; then return longValue is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.add(TypeConverter, Object, Object)"})
  void testAdd_givenBigDecimalWith23_whenNull_thenReturnLongValueIsTwo() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenReturn(new BigDecimal("2.3"));
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act
    Number actualAddResult = NumberOperations.add(converter, null, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertEquals(2L, actualAddResult.longValue());
  }

  /**
   * Test {@link NumberOperations#add(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link ELException#ELException()}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test add(TypeConverter, Object, Object); given ELException(); then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.add(TypeConverter, Object, Object)"})
  void testAdd_givenELException_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenThrow(new ELException());
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act and Assert
    assertThrows(
        ELException.class, () -> NumberOperations.add(converter, new BigDecimal("2.3"), "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#add(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link ELException#ELException()}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test add(TypeConverter, Object, Object); given ELException(); then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.add(TypeConverter, Object, Object)"})
  void testAdd_givenELException_thenThrowELException2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(BigInteger.class)))
        .thenThrow(new ELException());
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenReturn(new BigDecimal("2.3"));
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act and Assert
    assertThrows(
        ELException.class, () -> NumberOperations.add(converter, BigInteger.valueOf(42L), "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#add(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link ELException#ELException()}.
   *   <li>When {@code java.lang.Byte}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test add(TypeConverter, Object, Object); given ELException(); when 'java.lang.Byte'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.add(TypeConverter, Object, Object)"})
  void testAdd_givenELException_whenJavaLangByte_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenThrow(new ELException());
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenReturn(new BigDecimal("2.3"));
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.add(converter, "java.lang.Byte", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#add(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link ELException#ELException()}.
   *   <li>When {@code O1}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test add(TypeConverter, Object, Object); given ELException(); when 'O1'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.add(TypeConverter, Object, Object)"})
  void testAdd_givenELException_whenO1_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenThrow(new ELException());

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.add(converter, "O1", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#add(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given one.
   *   <li>When {@code O1}.
   *   <li>Then return longValue is two.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test add(TypeConverter, Object, Object); given one; when 'O1'; then return longValue is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.add(TypeConverter, Object, Object)"})
  void testAdd_givenOne_whenO1_thenReturnLongValueIsTwo() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act
    Number actualAddResult = NumberOperations.add(converter, "O1", "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertEquals(2L, actualAddResult.longValue());
  }

  /**
   * Test {@link NumberOperations#add(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given one.
   *   <li>When one.
   *   <li>Then return longValue is two.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test add(TypeConverter, Object, Object); given one; when one; then return longValue is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.add(TypeConverter, Object, Object)"})
  void testAdd_givenOne_whenOne_thenReturnLongValueIsTwo() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act
    Number actualAddResult = NumberOperations.add(converter, 1L, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertEquals(2L, actualAddResult.longValue());
  }

  /**
   * Test {@link NumberOperations#add(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given ten.
   *   <li>When {@code java.lang.Byte}.
   *   <li>Then return doubleValue is twenty.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test add(TypeConverter, Object, Object); given ten; when 'java.lang.Byte'; then return doubleValue is twenty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.add(TypeConverter, Object, Object)"})
  void testAdd_givenTen_whenJavaLangByte_thenReturnDoubleValueIsTwenty() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenReturn(new BigDecimal("2.3"));
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act
    Number actualAddResult = NumberOperations.add(converter, "java.lang.Byte", "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertEquals(20.0d, actualAddResult.doubleValue());
  }

  /**
   * Test {@link NumberOperations#add(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given ten.
   *   <li>When ten.
   *   <li>Then return doubleValue is twenty.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test add(TypeConverter, Object, Object); given ten; when ten; then return doubleValue is twenty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.add(TypeConverter, Object, Object)"})
  void testAdd_givenTen_whenTen_thenReturnDoubleValueIsTwenty() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenReturn(new BigDecimal("2.3"));
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act
    Number actualAddResult = NumberOperations.add(converter, 10.0f, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertEquals(20.0d, actualAddResult.doubleValue());
  }

  /**
   * Test {@link NumberOperations#add(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given ten.
   *   <li>When ten.
   *   <li>Then return doubleValue is twenty.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test add(TypeConverter, Object, Object); given ten; when ten; then return doubleValue is twenty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.add(TypeConverter, Object, Object)"})
  void testAdd_givenTen_whenTen_thenReturnDoubleValueIsTwenty2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenReturn(new BigDecimal("2.3"));
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act
    Number actualAddResult = NumberOperations.add(converter, 10.0d, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertEquals(20.0d, actualAddResult.doubleValue());
  }

  /**
   * Test {@link NumberOperations#add(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given valueOf forty-two.
   *   <li>Then return {@link BigInteger}.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test add(TypeConverter, Object, Object); given valueOf forty-two; then return BigInteger")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.add(TypeConverter, Object, Object)"})
  void testAdd_givenValueOfFortyTwo_thenReturnBigInteger() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(BigInteger.class)))
        .thenReturn(BigInteger.valueOf(42L));
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenReturn(new BigDecimal("2.3"));
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act
    Number actualAddResult = NumberOperations.add(converter, BigInteger.valueOf(42L), "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertTrue(actualAddResult instanceof BigInteger);
    BigInteger sqrtResult = ((BigInteger) actualAddResult).sqrt();
    BigInteger sqrtResult2 = sqrtResult.sqrt();
    assertEquals("3", sqrtResult2.toString());
    assertEquals("84", actualAddResult.toString());
    assertEquals("9", sqrtResult.toString());
    assertEquals(0, sqrtResult2.getLowestSetBit());
    assertEquals(0, sqrtResult.getLowestSetBit());
    assertEquals(1, sqrtResult2.signum());
    assertEquals(1, sqrtResult.signum());
    assertEquals(1, ((BigInteger) actualAddResult).signum());
    assertEquals(2, ((BigInteger) actualAddResult).getLowestSetBit());
  }

  /**
   * Test {@link NumberOperations#add(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given valueOf forty-two.
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)} return valueOf
   *       forty-two.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test add(TypeConverter, Object, Object); given valueOf forty-two; when TypeConverter convert(Object, Class) return valueOf forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.add(TypeConverter, Object, Object)"})
  void testAdd_givenValueOfFortyTwo_whenTypeConverterConvertReturnValueOfFortyTwo()
      throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(BigInteger.class)))
        .thenReturn(BigInteger.valueOf(42L));
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenReturn(new BigDecimal("2.3"));
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);
    BigInteger valueOfResult = BigInteger.valueOf(42L);

    // Act
    NumberOperations.add(converter, valueOfResult, new BigDecimal("2.3"));

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#add(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given valueOf forty-two.
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)} return valueOf
   *       forty-two.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test add(TypeConverter, Object, Object); given valueOf forty-two; when TypeConverter convert(Object, Class) return valueOf forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.add(TypeConverter, Object, Object)"})
  void testAdd_givenValueOfFortyTwo_whenTypeConverterConvertReturnValueOfFortyTwo2()
      throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(BigInteger.class)))
        .thenReturn(BigInteger.valueOf(42L));
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenReturn(new BigDecimal("2.3"));
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act
    NumberOperations.add(converter, BigInteger.valueOf(42L), 10.0f);

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#add(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>When {@link TypeConverter}.
   *   <li>Then return longValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test add(TypeConverter, Object, Object); when TypeConverter; then return longValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.add(TypeConverter, Object, Object)"})
  void testAdd_whenTypeConverter_thenReturnLongValueIsZero() {
    // Arrange and Act
    Number actualAddResult = NumberOperations.add(mock(TypeConverter.class), null, null);

    // Assert
    assertEquals(0L, actualAddResult.longValue());
  }

  /**
   * Test {@link NumberOperations#sub(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.
   *   <li>When {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.
   *   <li>Then calls {@link TypeConverter#convert(Object, Class)}.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test sub(TypeConverter, Object, Object); given BigDecimal(String) with '2.3'; when BigDecimal(String) with '2.3'; then calls convert(Object, Class)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.sub(TypeConverter, Object, Object)"})
  void testSub_givenBigDecimalWith23_whenBigDecimalWith23_thenCallsConvert() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenReturn(new BigDecimal("2.3"));
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act
    NumberOperations.sub(converter, new BigDecimal("2.3"), "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#sub(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.
   *   <li>When {@code null}.
   *   <li>Then return longValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test sub(TypeConverter, Object, Object); given BigDecimal(String) with '2.3'; when 'null'; then return longValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.sub(TypeConverter, Object, Object)"})
  void testSub_givenBigDecimalWith23_whenNull_thenReturnLongValueIsZero() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenReturn(new BigDecimal("2.3"));
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act
    Number actualSubResult = NumberOperations.sub(converter, null, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertEquals(0L, actualSubResult.longValue());
  }

  /**
   * Test {@link NumberOperations#sub(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link ELException#ELException()}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test sub(TypeConverter, Object, Object); given ELException(); then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.sub(TypeConverter, Object, Object)"})
  void testSub_givenELException_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenThrow(new ELException());
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act and Assert
    assertThrows(
        ELException.class, () -> NumberOperations.sub(converter, new BigDecimal("2.3"), "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#sub(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link ELException#ELException()}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test sub(TypeConverter, Object, Object); given ELException(); then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.sub(TypeConverter, Object, Object)"})
  void testSub_givenELException_thenThrowELException2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(BigInteger.class)))
        .thenThrow(new ELException());
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenReturn(new BigDecimal("2.3"));
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act and Assert
    assertThrows(
        ELException.class, () -> NumberOperations.sub(converter, BigInteger.valueOf(42L), "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#sub(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link ELException#ELException()}.
   *   <li>When {@code java.lang.Byte}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test sub(TypeConverter, Object, Object); given ELException(); when 'java.lang.Byte'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.sub(TypeConverter, Object, Object)"})
  void testSub_givenELException_whenJavaLangByte_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenThrow(new ELException());
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenReturn(new BigDecimal("2.3"));
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.sub(converter, "java.lang.Byte", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#sub(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link ELException#ELException()}.
   *   <li>When {@code O1}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test sub(TypeConverter, Object, Object); given ELException(); when 'O1'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.sub(TypeConverter, Object, Object)"})
  void testSub_givenELException_whenO1_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenThrow(new ELException());

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.sub(converter, "O1", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#sub(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given one.
   *   <li>When {@code O1}.
   *   <li>Then return longValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test sub(TypeConverter, Object, Object); given one; when 'O1'; then return longValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.sub(TypeConverter, Object, Object)"})
  void testSub_givenOne_whenO1_thenReturnLongValueIsZero() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act
    Number actualSubResult = NumberOperations.sub(converter, "O1", "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertEquals(0L, actualSubResult.longValue());
  }

  /**
   * Test {@link NumberOperations#sub(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given one.
   *   <li>When one.
   *   <li>Then return longValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test sub(TypeConverter, Object, Object); given one; when one; then return longValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.sub(TypeConverter, Object, Object)"})
  void testSub_givenOne_whenOne_thenReturnLongValueIsZero() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act
    Number actualSubResult = NumberOperations.sub(converter, 1L, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertEquals(0L, actualSubResult.longValue());
  }

  /**
   * Test {@link NumberOperations#sub(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given ten.
   *   <li>When {@code java.lang.Byte}.
   *   <li>Then return doubleValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test sub(TypeConverter, Object, Object); given ten; when 'java.lang.Byte'; then return doubleValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.sub(TypeConverter, Object, Object)"})
  void testSub_givenTen_whenJavaLangByte_thenReturnDoubleValueIsZero() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenReturn(new BigDecimal("2.3"));
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act
    Number actualSubResult = NumberOperations.sub(converter, "java.lang.Byte", "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertEquals(0.0d, actualSubResult.doubleValue());
  }

  /**
   * Test {@link NumberOperations#sub(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given ten.
   *   <li>When ten.
   *   <li>Then return doubleValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test sub(TypeConverter, Object, Object); given ten; when ten; then return doubleValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.sub(TypeConverter, Object, Object)"})
  void testSub_givenTen_whenTen_thenReturnDoubleValueIsZero() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenReturn(new BigDecimal("2.3"));
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act
    Number actualSubResult = NumberOperations.sub(converter, 10.0f, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertEquals(0.0d, actualSubResult.doubleValue());
  }

  /**
   * Test {@link NumberOperations#sub(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given ten.
   *   <li>When ten.
   *   <li>Then return doubleValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test sub(TypeConverter, Object, Object); given ten; when ten; then return doubleValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.sub(TypeConverter, Object, Object)"})
  void testSub_givenTen_whenTen_thenReturnDoubleValueIsZero2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenReturn(new BigDecimal("2.3"));
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act
    Number actualSubResult = NumberOperations.sub(converter, 10.0d, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertEquals(0.0d, actualSubResult.doubleValue());
  }

  /**
   * Test {@link NumberOperations#sub(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given valueOf forty-two.
   *   <li>Then return {@link BigInteger}.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test sub(TypeConverter, Object, Object); given valueOf forty-two; then return BigInteger")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.sub(TypeConverter, Object, Object)"})
  void testSub_givenValueOfFortyTwo_thenReturnBigInteger() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(BigInteger.class)))
        .thenReturn(BigInteger.valueOf(42L));
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenReturn(new BigDecimal("2.3"));
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act
    Number actualSubResult = NumberOperations.sub(converter, BigInteger.valueOf(42L), "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertTrue(actualSubResult instanceof BigInteger);
    assertEquals("0", actualSubResult.toString());
    assertEquals(-1, ((BigInteger) actualSubResult).getLowestSetBit());
    assertEquals(0, ((BigInteger) actualSubResult).signum());
    BigInteger expectedSqrtResult = ((BigInteger) actualSubResult).ZERO;
    assertSame(expectedSqrtResult, ((BigInteger) actualSubResult).sqrt());
  }

  /**
   * Test {@link NumberOperations#sub(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given valueOf forty-two.
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)} return valueOf
   *       forty-two.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test sub(TypeConverter, Object, Object); given valueOf forty-two; when TypeConverter convert(Object, Class) return valueOf forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.sub(TypeConverter, Object, Object)"})
  void testSub_givenValueOfFortyTwo_whenTypeConverterConvertReturnValueOfFortyTwo()
      throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(BigInteger.class)))
        .thenReturn(BigInteger.valueOf(42L));
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenReturn(new BigDecimal("2.3"));
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);
    BigInteger valueOfResult = BigInteger.valueOf(42L);

    // Act
    NumberOperations.sub(converter, valueOfResult, new BigDecimal("2.3"));

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#sub(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given valueOf forty-two.
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)} return valueOf
   *       forty-two.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test sub(TypeConverter, Object, Object); given valueOf forty-two; when TypeConverter convert(Object, Class) return valueOf forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.sub(TypeConverter, Object, Object)"})
  void testSub_givenValueOfFortyTwo_whenTypeConverterConvertReturnValueOfFortyTwo2()
      throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(BigInteger.class)))
        .thenReturn(BigInteger.valueOf(42L));
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenReturn(new BigDecimal("2.3"));
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act
    NumberOperations.sub(converter, BigInteger.valueOf(42L), 10.0f);

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#sub(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>When {@link TypeConverter}.
   *   <li>Then return longValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test sub(TypeConverter, Object, Object); when TypeConverter; then return longValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.sub(TypeConverter, Object, Object)"})
  void testSub_whenTypeConverter_thenReturnLongValueIsZero() {
    // Arrange and Act
    Number actualSubResult = NumberOperations.sub(mock(TypeConverter.class), null, null);

    // Assert
    assertEquals(0L, actualSubResult.longValue());
  }

  /**
   * Test {@link NumberOperations#mul(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.
   *   <li>When {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.
   *   <li>Then calls {@link TypeConverter#convert(Object, Class)}.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test mul(TypeConverter, Object, Object); given BigDecimal(String) with '2.3'; when BigDecimal(String) with '2.3'; then calls convert(Object, Class)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.mul(TypeConverter, Object, Object)"})
  void testMul_givenBigDecimalWith23_whenBigDecimalWith23_thenCallsConvert() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenReturn(new BigDecimal("2.3"));
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act
    NumberOperations.mul(converter, new BigDecimal("2.3"), "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#mul(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.
   *   <li>When {@code null}.
   *   <li>Then return longValue is one.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test mul(TypeConverter, Object, Object); given BigDecimal(String) with '2.3'; when 'null'; then return longValue is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.mul(TypeConverter, Object, Object)"})
  void testMul_givenBigDecimalWith23_whenNull_thenReturnLongValueIsOne() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenReturn(new BigDecimal("2.3"));
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act
    Number actualMulResult = NumberOperations.mul(converter, null, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertEquals(1L, actualMulResult.longValue());
  }

  /**
   * Test {@link NumberOperations#mul(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link ELException#ELException()}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test mul(TypeConverter, Object, Object); given ELException(); then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.mul(TypeConverter, Object, Object)"})
  void testMul_givenELException_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenThrow(new ELException());
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act and Assert
    assertThrows(
        ELException.class, () -> NumberOperations.mul(converter, new BigDecimal("2.3"), "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#mul(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link ELException#ELException()}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test mul(TypeConverter, Object, Object); given ELException(); then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.mul(TypeConverter, Object, Object)"})
  void testMul_givenELException_thenThrowELException2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(BigInteger.class)))
        .thenThrow(new ELException());
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenReturn(new BigDecimal("2.3"));
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act and Assert
    assertThrows(
        ELException.class, () -> NumberOperations.mul(converter, BigInteger.valueOf(42L), "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#mul(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link ELException#ELException()}.
   *   <li>When {@code java.lang.Byte}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test mul(TypeConverter, Object, Object); given ELException(); when 'java.lang.Byte'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.mul(TypeConverter, Object, Object)"})
  void testMul_givenELException_whenJavaLangByte_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenThrow(new ELException());
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenReturn(new BigDecimal("2.3"));
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mul(converter, "java.lang.Byte", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#mul(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link ELException#ELException()}.
   *   <li>When {@code O1}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test mul(TypeConverter, Object, Object); given ELException(); when 'O1'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.mul(TypeConverter, Object, Object)"})
  void testMul_givenELException_whenO1_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenThrow(new ELException());

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mul(converter, "O1", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#mul(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given one.
   *   <li>When {@code O1}.
   *   <li>Then return longValue is one.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test mul(TypeConverter, Object, Object); given one; when 'O1'; then return longValue is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.mul(TypeConverter, Object, Object)"})
  void testMul_givenOne_whenO1_thenReturnLongValueIsOne() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act
    Number actualMulResult = NumberOperations.mul(converter, "O1", "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertEquals(1L, actualMulResult.longValue());
  }

  /**
   * Test {@link NumberOperations#mul(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given one.
   *   <li>When one.
   *   <li>Then return longValue is one.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test mul(TypeConverter, Object, Object); given one; when one; then return longValue is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.mul(TypeConverter, Object, Object)"})
  void testMul_givenOne_whenOne_thenReturnLongValueIsOne() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act
    Number actualMulResult = NumberOperations.mul(converter, 1L, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertEquals(1L, actualMulResult.longValue());
  }

  /**
   * Test {@link NumberOperations#mul(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given ten.
   *   <li>When {@code java.lang.Byte}.
   *   <li>Then return doubleValue is one hundred.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test mul(TypeConverter, Object, Object); given ten; when 'java.lang.Byte'; then return doubleValue is one hundred")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.mul(TypeConverter, Object, Object)"})
  void testMul_givenTen_whenJavaLangByte_thenReturnDoubleValueIsOneHundred() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenReturn(new BigDecimal("2.3"));
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act
    Number actualMulResult = NumberOperations.mul(converter, "java.lang.Byte", "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertEquals(100.0d, actualMulResult.doubleValue());
  }

  /**
   * Test {@link NumberOperations#mul(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given ten.
   *   <li>When ten.
   *   <li>Then return doubleValue is one hundred.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test mul(TypeConverter, Object, Object); given ten; when ten; then return doubleValue is one hundred")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.mul(TypeConverter, Object, Object)"})
  void testMul_givenTen_whenTen_thenReturnDoubleValueIsOneHundred() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenReturn(new BigDecimal("2.3"));
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act
    Number actualMulResult = NumberOperations.mul(converter, 10.0f, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertEquals(100.0d, actualMulResult.doubleValue());
  }

  /**
   * Test {@link NumberOperations#mul(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given ten.
   *   <li>When ten.
   *   <li>Then return doubleValue is one hundred.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test mul(TypeConverter, Object, Object); given ten; when ten; then return doubleValue is one hundred")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.mul(TypeConverter, Object, Object)"})
  void testMul_givenTen_whenTen_thenReturnDoubleValueIsOneHundred2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenReturn(new BigDecimal("2.3"));
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act
    Number actualMulResult = NumberOperations.mul(converter, 10.0d, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertEquals(100.0d, actualMulResult.doubleValue());
  }

  /**
   * Test {@link NumberOperations#mul(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given valueOf forty-two.
   *   <li>Then return {@link BigInteger}.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test mul(TypeConverter, Object, Object); given valueOf forty-two; then return BigInteger")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.mul(TypeConverter, Object, Object)"})
  void testMul_givenValueOfFortyTwo_thenReturnBigInteger() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    BigInteger valueOfResult = BigInteger.valueOf(42L);
    when(converter.convert(Mockito.<Object>any(), eq(BigInteger.class))).thenReturn(valueOfResult);
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenReturn(new BigDecimal("2.3"));
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act
    Number actualMulResult = NumberOperations.mul(converter, BigInteger.valueOf(42L), "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertTrue(actualMulResult instanceof BigInteger);
    assertEquals("1764", actualMulResult.toString());
    assertEquals(1, ((BigInteger) actualMulResult).signum());
    assertEquals(2, ((BigInteger) actualMulResult).getLowestSetBit());
    assertEquals(valueOfResult, ((BigInteger) actualMulResult).sqrt());
  }

  /**
   * Test {@link NumberOperations#mul(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given valueOf forty-two.
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)} return valueOf
   *       forty-two.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test mul(TypeConverter, Object, Object); given valueOf forty-two; when TypeConverter convert(Object, Class) return valueOf forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.mul(TypeConverter, Object, Object)"})
  void testMul_givenValueOfFortyTwo_whenTypeConverterConvertReturnValueOfFortyTwo()
      throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(BigInteger.class)))
        .thenReturn(BigInteger.valueOf(42L));
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenReturn(new BigDecimal("2.3"));
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);
    BigInteger valueOfResult = BigInteger.valueOf(42L);

    // Act
    NumberOperations.mul(converter, valueOfResult, new BigDecimal("2.3"));

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#mul(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given valueOf forty-two.
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)} return valueOf
   *       forty-two.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test mul(TypeConverter, Object, Object); given valueOf forty-two; when TypeConverter convert(Object, Class) return valueOf forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.mul(TypeConverter, Object, Object)"})
  void testMul_givenValueOfFortyTwo_whenTypeConverterConvertReturnValueOfFortyTwo2()
      throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(BigInteger.class)))
        .thenReturn(BigInteger.valueOf(42L));
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenReturn(new BigDecimal("2.3"));
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act
    NumberOperations.mul(converter, BigInteger.valueOf(42L), 10.0f);

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#mul(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>When {@link TypeConverter}.
   *   <li>Then return longValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test mul(TypeConverter, Object, Object); when TypeConverter; then return longValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.mul(TypeConverter, Object, Object)"})
  void testMul_whenTypeConverter_thenReturnLongValueIsZero() {
    // Arrange and Act
    Number actualMulResult = NumberOperations.mul(mock(TypeConverter.class), null, null);

    // Assert
    assertEquals(0L, actualMulResult.longValue());
  }

  /**
   * Test {@link NumberOperations#div(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.
   *   <li>When {@code null}.
   *   <li>Then calls {@link TypeConverter#convert(Object, Class)}.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#div(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test div(TypeConverter, Object, Object); given BigDecimal(String) with '2.3'; when 'null'; then calls convert(Object, Class)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.div(TypeConverter, Object, Object)"})
  void testDiv_givenBigDecimalWith23_whenNull_thenCallsConvert() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenReturn(new BigDecimal("2.3"));
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);

    // Act
    NumberOperations.div(converter, null, new BigDecimal("2.3"));

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#div(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.
   *   <li>When {@code null}.
   *   <li>Then return doubleValue is one.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#div(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test div(TypeConverter, Object, Object); given BigDecimal(String) with '2.3'; when 'null'; then return doubleValue is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.div(TypeConverter, Object, Object)"})
  void testDiv_givenBigDecimalWith23_whenNull_thenReturnDoubleValueIsOne() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenReturn(new BigDecimal("2.3"));
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);

    // Act
    Number actualDivResult = NumberOperations.div(converter, null, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertEquals(1.0d, actualDivResult.doubleValue());
  }

  /**
   * Test {@link NumberOperations#div(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)} return {@link
   *       BigDecimal#BigDecimal(String)} with {@code 2.3}.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#div(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test div(TypeConverter, Object, Object); given BigDecimal(String) with '2.3'; when TypeConverter convert(Object, Class) return BigDecimal(String) with '2.3'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.div(TypeConverter, Object, Object)"})
  void testDiv_givenBigDecimalWith23_whenTypeConverterConvertReturnBigDecimalWith23()
      throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenReturn(new BigDecimal("2.3"));
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);

    // Act
    NumberOperations.div(converter, new BigDecimal("2.3"), "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#div(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.
   *   <li>When valueOf forty-two.
   *   <li>Then calls {@link TypeConverter#convert(Object, Class)}.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#div(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test div(TypeConverter, Object, Object); given BigDecimal(String) with '2.3'; when valueOf forty-two; then calls convert(Object, Class)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.div(TypeConverter, Object, Object)"})
  void testDiv_givenBigDecimalWith23_whenValueOfFortyTwo_thenCallsConvert() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenReturn(new BigDecimal("2.3"));
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);

    // Act
    NumberOperations.div(converter, BigInteger.valueOf(42L), "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#div(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link ELException#ELException()}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#div(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test div(TypeConverter, Object, Object); given ELException(); then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.div(TypeConverter, Object, Object)"})
  void testDiv_givenELException_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenThrow(new ELException());

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.div(converter, "O1", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#div(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link ELException#ELException()}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#div(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test div(TypeConverter, Object, Object); given ELException(); then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.div(TypeConverter, Object, Object)"})
  void testDiv_givenELException_thenThrowELException2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(BigDecimal.class)))
        .thenThrow(new ELException());
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);

    // Act and Assert
    assertThrows(
        ELException.class, () -> NumberOperations.div(converter, new BigDecimal("2.3"), "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#div(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given ten.
   *   <li>When {@code O1}.
   *   <li>Then return doubleValue is one.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#div(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test div(TypeConverter, Object, Object); given ten; when 'O1'; then return doubleValue is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.div(TypeConverter, Object, Object)"})
  void testDiv_givenTen_whenO1_thenReturnDoubleValueIsOne() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);

    // Act
    Number actualDivResult = NumberOperations.div(converter, "O1", "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertEquals(1.0d, actualDivResult.doubleValue());
  }

  /**
   * Test {@link NumberOperations#div(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>When {@link TypeConverter}.
   *   <li>Then return longValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#div(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test div(TypeConverter, Object, Object); when TypeConverter; then return longValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.div(TypeConverter, Object, Object)"})
  void testDiv_whenTypeConverter_thenReturnLongValueIsZero() {
    // Arrange and Act
    Number actualDivResult = NumberOperations.div(mock(TypeConverter.class), null, null);

    // Assert
    assertEquals(0L, actualDivResult.longValue());
  }

  /**
   * Test {@link NumberOperations#mod(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link ELException#ELException()}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#mod(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test mod(TypeConverter, Object, Object); given ELException(); then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.mod(TypeConverter, Object, Object)"})
  void testMod_givenELException_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenThrow(new ELException());

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mod(converter, "O1", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#mod(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link ELException#ELException()}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#mod(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test mod(TypeConverter, Object, Object); given ELException(); then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.mod(TypeConverter, Object, Object)"})
  void testMod_givenELException_thenThrowELException2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenThrow(new ELException());
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act and Assert
    assertThrows(
        ELException.class, () -> NumberOperations.mod(converter, new BigDecimal("2.3"), "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#mod(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link ELException#ELException()}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#mod(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test mod(TypeConverter, Object, Object); given ELException(); then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.mod(TypeConverter, Object, Object)"})
  void testMod_givenELException_thenThrowELException3() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(BigInteger.class)))
        .thenThrow(new ELException());
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act and Assert
    assertThrows(
        ELException.class, () -> NumberOperations.mod(converter, BigInteger.valueOf(42L), "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#mod(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given one.
   *   <li>When {@code O1}.
   *   <li>Then return longValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#mod(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test mod(TypeConverter, Object, Object); given one; when 'O1'; then return longValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.mod(TypeConverter, Object, Object)"})
  void testMod_givenOne_whenO1_thenReturnLongValueIsZero() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act
    Number actualModResult = NumberOperations.mod(converter, "O1", "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertEquals(0L, actualModResult.longValue());
  }

  /**
   * Test {@link NumberOperations#mod(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given one.
   *   <li>When one.
   *   <li>Then return longValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#mod(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test mod(TypeConverter, Object, Object); given one; when one; then return longValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.mod(TypeConverter, Object, Object)"})
  void testMod_givenOne_whenOne_thenReturnLongValueIsZero() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act
    Number actualModResult = NumberOperations.mod(converter, 1L, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertEquals(0L, actualModResult.longValue());
  }

  /**
   * Test {@link NumberOperations#mod(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given ten.
   *   <li>When {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.
   *   <li>Then return doubleValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#mod(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test mod(TypeConverter, Object, Object); given ten; when BigDecimal(String) with '2.3'; then return doubleValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.mod(TypeConverter, Object, Object)"})
  void testMod_givenTen_whenBigDecimalWith23_thenReturnDoubleValueIsZero() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act
    Number actualModResult = NumberOperations.mod(converter, new BigDecimal("2.3"), "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertEquals(0.0d, actualModResult.doubleValue());
  }

  /**
   * Test {@link NumberOperations#mod(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given ten.
   *   <li>When {@code java.lang.Byte}.
   *   <li>Then return doubleValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#mod(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test mod(TypeConverter, Object, Object); given ten; when 'java.lang.Byte'; then return doubleValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.mod(TypeConverter, Object, Object)"})
  void testMod_givenTen_whenJavaLangByte_thenReturnDoubleValueIsZero() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act
    Number actualModResult = NumberOperations.mod(converter, "java.lang.Byte", "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertEquals(0.0d, actualModResult.doubleValue());
  }

  /**
   * Test {@link NumberOperations#mod(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given ten.
   *   <li>When {@code null}.
   *   <li>Then return longValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#mod(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test mod(TypeConverter, Object, Object); given ten; when 'null'; then return longValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.mod(TypeConverter, Object, Object)"})
  void testMod_givenTen_whenNull_thenReturnLongValueIsZero() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act
    Number actualModResult = NumberOperations.mod(converter, null, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertEquals(0L, actualModResult.longValue());
  }

  /**
   * Test {@link NumberOperations#mod(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given ten.
   *   <li>When ten.
   *   <li>Then return doubleValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#mod(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test mod(TypeConverter, Object, Object); given ten; when ten; then return doubleValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.mod(TypeConverter, Object, Object)"})
  void testMod_givenTen_whenTen_thenReturnDoubleValueIsZero() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act
    Number actualModResult = NumberOperations.mod(converter, 10.0d, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertEquals(0.0d, actualModResult.doubleValue());
  }

  /**
   * Test {@link NumberOperations#mod(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given ten.
   *   <li>When ten.
   *   <li>Then return doubleValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#mod(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test mod(TypeConverter, Object, Object); given ten; when ten; then return doubleValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.mod(TypeConverter, Object, Object)"})
  void testMod_givenTen_whenTen_thenReturnDoubleValueIsZero2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act
    Number actualModResult = NumberOperations.mod(converter, 10.0f, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertEquals(0.0d, actualModResult.doubleValue());
  }

  /**
   * Test {@link NumberOperations#mod(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given valueOf forty-two.
   *   <li>Then return {@link BigInteger}.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#mod(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test mod(TypeConverter, Object, Object); given valueOf forty-two; then return BigInteger")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.mod(TypeConverter, Object, Object)"})
  void testMod_givenValueOfFortyTwo_thenReturnBigInteger() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(BigInteger.class)))
        .thenReturn(BigInteger.valueOf(42L));
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);

    // Act
    Number actualModResult = NumberOperations.mod(converter, BigInteger.valueOf(42L), "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertTrue(actualModResult instanceof BigInteger);
    assertEquals("0", actualModResult.toString());
    assertEquals(-1, ((BigInteger) actualModResult).getLowestSetBit());
    assertEquals(0, ((BigInteger) actualModResult).signum());
    BigInteger expectedSqrtResult = ((BigInteger) actualModResult).ZERO;
    assertSame(expectedSqrtResult, ((BigInteger) actualModResult).sqrt());
  }

  /**
   * Test {@link NumberOperations#mod(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given valueOf forty-two.
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)} return valueOf
   *       forty-two.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#mod(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test mod(TypeConverter, Object, Object); given valueOf forty-two; when TypeConverter convert(Object, Class) return valueOf forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.mod(TypeConverter, Object, Object)"})
  void testMod_givenValueOfFortyTwo_whenTypeConverterConvertReturnValueOfFortyTwo()
      throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(BigInteger.class)))
        .thenReturn(BigInteger.valueOf(42L));
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);
    BigInteger valueOfResult = BigInteger.valueOf(42L);

    // Act
    Number actualModResult = NumberOperations.mod(converter, valueOfResult, new BigDecimal("2.3"));

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertEquals(0.0d, actualModResult.doubleValue());
  }

  /**
   * Test {@link NumberOperations#mod(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>When {@link TypeConverter}.
   *   <li>Then return longValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#mod(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test mod(TypeConverter, Object, Object); when TypeConverter; then return longValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.mod(TypeConverter, Object, Object)"})
  void testMod_whenTypeConverter_thenReturnLongValueIsZero() {
    // Arrange and Act
    Number actualModResult = NumberOperations.mod(mock(TypeConverter.class), null, null);

    // Assert
    assertEquals(0L, actualModResult.longValue());
  }

  /**
   * Test {@link NumberOperations#neg(TypeConverter, Object)}.
   *
   * <ul>
   *   <li>Given {@link ELException#ELException()}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#neg(TypeConverter, Object)}
   */
  @Test
  @DisplayName("Test neg(TypeConverter, Object); given ELException(); then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.neg(TypeConverter, Object)"})
  void testNeg_givenELException_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenThrow(new ELException());

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.neg(converter, "Value"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#neg(TypeConverter, Object)}.
   *
   * <ul>
   *   <li>Given {@link ELException#ELException()}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#neg(TypeConverter, Object)}
   */
  @Test
  @DisplayName("Test neg(TypeConverter, Object); given ELException(); then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.neg(TypeConverter, Object)"})
  void testNeg_givenELException_thenThrowELException2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenThrow(new ELException());
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.neg(converter, "42"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#neg(TypeConverter, Object)}.
   *
   * <ul>
   *   <li>Given one.
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)} return one.
   *   <li>Then return longValue is minus one.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#neg(TypeConverter, Object)}
   */
  @Test
  @DisplayName(
      "Test neg(TypeConverter, Object); given one; when TypeConverter convert(Object, Class) return one; then return longValue is minus one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.neg(TypeConverter, Object)"})
  void testNeg_givenOne_whenTypeConverterConvertReturnOne_thenReturnLongValueIsMinusOne()
      throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Long.class))).thenReturn(1L);
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);

    // Act
    Number actualNegResult = NumberOperations.neg(converter, "42");

    // Assert
    verify(converter).convert(isA(Object.class), isA(Class.class));
    assertEquals(-1L, actualNegResult.longValue());
  }

  /**
   * Test {@link NumberOperations#neg(TypeConverter, Object)}.
   *
   * <ul>
   *   <li>Given ten.
   *   <li>When {@code Value}.
   *   <li>Then return doubleValue is minus ten.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#neg(TypeConverter, Object)}
   */
  @Test
  @DisplayName(
      "Test neg(TypeConverter, Object); given ten; when 'Value'; then return doubleValue is minus ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.neg(TypeConverter, Object)"})
  void testNeg_givenTen_whenValue_thenReturnDoubleValueIsMinusTen() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Double.class))).thenReturn(10.0d);

    // Act
    Number actualNegResult = NumberOperations.neg(converter, "Value");

    // Assert
    verify(converter).convert(isA(Object.class), isA(Class.class));
    assertEquals(-10.0d, actualNegResult.doubleValue());
  }

  /**
   * Test {@link NumberOperations#neg(TypeConverter, Object)}.
   *
   * <ul>
   *   <li>When {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#neg(TypeConverter, Object)}
   */
  @Test
  @DisplayName(
      "Test neg(TypeConverter, Object); when BigDecimal(String) with '2.3'; then does not throw")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.neg(TypeConverter, Object)"})
  void testNeg_whenBigDecimalWith23_thenDoesNotThrow() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);

    // Act
    assertDoesNotThrow(() -> NumberOperations.neg(converter, new BigDecimal("2.3")));
  }

  /**
   * Test {@link NumberOperations#neg(TypeConverter, Object)}.
   *
   * <ul>
   *   <li>When forty-two.
   *   <li>Then return intValue is minus forty-two.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#neg(TypeConverter, Object)}
   */
  @Test
  @DisplayName(
      "Test neg(TypeConverter, Object); when forty-two; then return intValue is minus forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.neg(TypeConverter, Object)"})
  void testNeg_whenFortyTwo_thenReturnIntValueIsMinusFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(-42, NumberOperations.neg(mock(TypeConverter.class), 42).intValue());
  }

  /**
   * Test {@link NumberOperations#neg(TypeConverter, Object)}.
   *
   * <ul>
   *   <li>When forty-two.
   *   <li>Then return longValue is minus forty-two.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#neg(TypeConverter, Object)}
   */
  @Test
  @DisplayName(
      "Test neg(TypeConverter, Object); when forty-two; then return longValue is minus forty-two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.neg(TypeConverter, Object)"})
  void testNeg_whenFortyTwo_thenReturnLongValueIsMinusFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(-42L, NumberOperations.neg(mock(TypeConverter.class), 42L).longValue());
  }

  /**
   * Test {@link NumberOperations#neg(TypeConverter, Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return longValue is zero.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#neg(TypeConverter, Object)}
   */
  @Test
  @DisplayName("Test neg(TypeConverter, Object); when 'null'; then return longValue is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.neg(TypeConverter, Object)"})
  void testNeg_whenNull_thenReturnLongValueIsZero() {
    // Arrange, Act and Assert
    assertEquals(0L, NumberOperations.neg(mock(TypeConverter.class), null).longValue());
  }

  /**
   * Test {@link NumberOperations#neg(TypeConverter, Object)}.
   *
   * <ul>
   *   <li>When ten.
   *   <li>Then return doubleValue is minus ten.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#neg(TypeConverter, Object)}
   */
  @Test
  @DisplayName("Test neg(TypeConverter, Object); when ten; then return doubleValue is minus ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.neg(TypeConverter, Object)"})
  void testNeg_whenTen_thenReturnDoubleValueIsMinusTen() {
    // Arrange, Act and Assert
    assertEquals(-10.0d, NumberOperations.neg(mock(TypeConverter.class), 10.0d).doubleValue());
  }

  /**
   * Test {@link NumberOperations#neg(TypeConverter, Object)}.
   *
   * <ul>
   *   <li>When ten.
   *   <li>Then return floatValue is minus ten.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#neg(TypeConverter, Object)}
   */
  @Test
  @DisplayName("Test neg(TypeConverter, Object); when ten; then return floatValue is minus ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.neg(TypeConverter, Object)"})
  void testNeg_whenTen_thenReturnFloatValueIsMinusTen() {
    // Arrange, Act and Assert
    assertEquals(-10.0f, NumberOperations.neg(mock(TypeConverter.class), 10.0f).floatValue());
  }

  /**
   * Test {@link NumberOperations#neg(TypeConverter, Object)}.
   *
   * <ul>
   *   <li>When valueOf forty-two.
   *   <li>Then return {@link BigInteger}.
   * </ul>
   *
   * <p>Method under test: {@link NumberOperations#neg(TypeConverter, Object)}
   */
  @Test
  @DisplayName("Test neg(TypeConverter, Object); when valueOf forty-two; then return BigInteger")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Number NumberOperations.neg(TypeConverter, Object)"})
  void testNeg_whenValueOfFortyTwo_thenReturnBigInteger() {
    // Arrange
    BigInteger valueOfResult = BigInteger.valueOf(42L);

    // Act
    Number actualNegResult = NumberOperations.neg(mock(TypeConverter.class), valueOfResult);

    // Assert
    assertTrue(actualNegResult instanceof BigInteger);
    assertEquals("-42", actualNegResult.toString());
    assertEquals(-1, ((BigInteger) actualNegResult).signum());
    assertEquals(1, ((BigInteger) actualNegResult).getLowestSetBit());
    BigInteger expectedSqrtResult = ((BigInteger) actualNegResult).TWO;
    assertEquals(expectedSqrtResult, valueOfResult.sqrt().sqrt());
  }
}
