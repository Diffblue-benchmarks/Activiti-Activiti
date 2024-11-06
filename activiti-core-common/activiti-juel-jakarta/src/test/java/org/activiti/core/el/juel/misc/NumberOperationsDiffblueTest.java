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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import jakarta.el.ELException;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class NumberOperationsDiffblueTest {
  /**
   * Test {@link NumberOperations#add(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then calls {@link TypeConverter#convert(Object, Class)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test add(TypeConverter, Object, Object); given one; when 'null'; then calls convert(Object, Class)")
  void testAdd_givenOne_whenNull_thenCallsConvert() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any())).thenReturn(1L);

    // Act
    NumberOperations.add(converter, null, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#add(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When one.</li>
   *   <li>Then calls {@link TypeConverter#convert(Object, Class)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test add(TypeConverter, Object, Object); given one; when one; then calls convert(Object, Class)")
  void testAdd_givenOne_whenOne_thenCallsConvert() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any())).thenReturn(1L);

    // Act
    NumberOperations.add(converter, 1L, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#add(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)}
   * return one.</li>
   *   <li>Then calls {@link TypeConverter#convert(Object, Class)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test add(TypeConverter, Object, Object); given one; when TypeConverter convert(Object, Class) return one; then calls convert(Object, Class)")
  void testAdd_givenOne_whenTypeConverterConvertReturnOne_thenCallsConvert() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any())).thenReturn(1L);

    // Act
    NumberOperations.add(converter, "O1", "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#add(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test add(TypeConverter, Object, Object); when BigDecimal(String) with '2.3'; then throw ELException")
  void testAdd_whenBigDecimalWith23_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.add(converter, new BigDecimal("2.3"), "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#add(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test add(TypeConverter, Object, Object); when BigDecimal(String) with '2.3'; then throw ELException")
  void testAdd_whenBigDecimalWith23_thenThrowELException2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.add(converter, "O1", new BigDecimal("2.3")));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#add(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When {@code java.lang.Byte}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test add(TypeConverter, Object, Object); when 'java.lang.Byte'; then throw ELException")
  void testAdd_whenJavaLangByte_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.add(converter, "java.lang.Byte", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#add(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When {@code O1}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test add(TypeConverter, Object, Object); when 'O1'; then throw ELException")
  void testAdd_whenO1_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.add(converter, "O1", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#add(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test add(TypeConverter, Object, Object); when ten; then throw ELException")
  void testAdd_whenTen_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.add(converter, 10.0f, "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#add(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test add(TypeConverter, Object, Object); when ten; then throw ELException")
  void testAdd_whenTen_thenThrowELException2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.add(converter, 10.0d, "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#add(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test add(TypeConverter, Object, Object); when ten; then throw ELException")
  void testAdd_whenTen_thenThrowELException3() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.add(converter, "O1", 10.0f));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#add(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When valueOf one.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test add(TypeConverter, Object, Object); when valueOf one; then throw ELException")
  void testAdd_whenValueOfOne_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.add(converter, BigInteger.valueOf(1L), "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#add(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When valueOf one.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test add(TypeConverter, Object, Object); when valueOf one; then throw ELException")
  void testAdd_whenValueOfOne_thenThrowELException2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.add(converter, "O1", BigInteger.valueOf(1L)));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#sub(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then calls {@link TypeConverter#convert(Object, Class)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test sub(TypeConverter, Object, Object); given one; when 'null'; then calls convert(Object, Class)")
  void testSub_givenOne_whenNull_thenCallsConvert() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any())).thenReturn(1L);

    // Act
    NumberOperations.sub(converter, null, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#sub(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When one.</li>
   *   <li>Then calls {@link TypeConverter#convert(Object, Class)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test sub(TypeConverter, Object, Object); given one; when one; then calls convert(Object, Class)")
  void testSub_givenOne_whenOne_thenCallsConvert() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any())).thenReturn(1L);

    // Act
    NumberOperations.sub(converter, 1L, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#sub(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)}
   * return one.</li>
   *   <li>Then calls {@link TypeConverter#convert(Object, Class)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test sub(TypeConverter, Object, Object); given one; when TypeConverter convert(Object, Class) return one; then calls convert(Object, Class)")
  void testSub_givenOne_whenTypeConverterConvertReturnOne_thenCallsConvert() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any())).thenReturn(1L);

    // Act
    NumberOperations.sub(converter, "O1", "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#sub(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test sub(TypeConverter, Object, Object); when BigDecimal(String) with '2.3'; then throw ELException")
  void testSub_whenBigDecimalWith23_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.sub(converter, new BigDecimal("2.3"), "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#sub(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test sub(TypeConverter, Object, Object); when BigDecimal(String) with '2.3'; then throw ELException")
  void testSub_whenBigDecimalWith23_thenThrowELException2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.sub(converter, "O1", new BigDecimal("2.3")));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#sub(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When {@code java.lang.Byte}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test sub(TypeConverter, Object, Object); when 'java.lang.Byte'; then throw ELException")
  void testSub_whenJavaLangByte_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.sub(converter, "java.lang.Byte", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#sub(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When {@code O1}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test sub(TypeConverter, Object, Object); when 'O1'; then throw ELException")
  void testSub_whenO1_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.sub(converter, "O1", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#sub(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test sub(TypeConverter, Object, Object); when ten; then throw ELException")
  void testSub_whenTen_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.sub(converter, 10.0f, "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#sub(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test sub(TypeConverter, Object, Object); when ten; then throw ELException")
  void testSub_whenTen_thenThrowELException2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.sub(converter, 10.0d, "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#sub(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test sub(TypeConverter, Object, Object); when ten; then throw ELException")
  void testSub_whenTen_thenThrowELException3() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.sub(converter, "O1", 10.0f));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#sub(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When valueOf one.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test sub(TypeConverter, Object, Object); when valueOf one; then throw ELException")
  void testSub_whenValueOfOne_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.sub(converter, BigInteger.valueOf(1L), "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#sub(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When valueOf one.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test sub(TypeConverter, Object, Object); when valueOf one; then throw ELException")
  void testSub_whenValueOfOne_thenThrowELException2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.sub(converter, "O1", BigInteger.valueOf(1L)));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#mul(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then calls {@link TypeConverter#convert(Object, Class)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test mul(TypeConverter, Object, Object); given one; when 'null'; then calls convert(Object, Class)")
  void testMul_givenOne_whenNull_thenCallsConvert() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any())).thenReturn(1L);

    // Act
    NumberOperations.mul(converter, null, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#mul(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When one.</li>
   *   <li>Then calls {@link TypeConverter#convert(Object, Class)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test mul(TypeConverter, Object, Object); given one; when one; then calls convert(Object, Class)")
  void testMul_givenOne_whenOne_thenCallsConvert() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any())).thenReturn(1L);

    // Act
    NumberOperations.mul(converter, 1L, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#mul(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)}
   * return one.</li>
   *   <li>Then calls {@link TypeConverter#convert(Object, Class)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test mul(TypeConverter, Object, Object); given one; when TypeConverter convert(Object, Class) return one; then calls convert(Object, Class)")
  void testMul_givenOne_whenTypeConverterConvertReturnOne_thenCallsConvert() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any())).thenReturn(1L);

    // Act
    NumberOperations.mul(converter, "O1", "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#mul(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test mul(TypeConverter, Object, Object); when BigDecimal(String) with '2.3'; then throw ELException")
  void testMul_whenBigDecimalWith23_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mul(converter, new BigDecimal("2.3"), "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#mul(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test mul(TypeConverter, Object, Object); when BigDecimal(String) with '2.3'; then throw ELException")
  void testMul_whenBigDecimalWith23_thenThrowELException2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mul(converter, "O1", new BigDecimal("2.3")));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#mul(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When {@code java.lang.Byte}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test mul(TypeConverter, Object, Object); when 'java.lang.Byte'; then throw ELException")
  void testMul_whenJavaLangByte_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mul(converter, "java.lang.Byte", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#mul(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When {@code O1}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test mul(TypeConverter, Object, Object); when 'O1'; then throw ELException")
  void testMul_whenO1_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mul(converter, "O1", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#mul(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test mul(TypeConverter, Object, Object); when ten; then throw ELException")
  void testMul_whenTen_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mul(converter, 10.0f, "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#mul(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test mul(TypeConverter, Object, Object); when ten; then throw ELException")
  void testMul_whenTen_thenThrowELException2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mul(converter, 10.0d, "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#mul(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test mul(TypeConverter, Object, Object); when ten; then throw ELException")
  void testMul_whenTen_thenThrowELException3() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mul(converter, "O1", 10.0f));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#mul(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When valueOf one.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test mul(TypeConverter, Object, Object); when valueOf one; then throw ELException")
  void testMul_whenValueOfOne_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mul(converter, BigInteger.valueOf(1L), "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#mul(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When valueOf one.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test mul(TypeConverter, Object, Object); when valueOf one; then throw ELException")
  void testMul_whenValueOfOne_thenThrowELException2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mul(converter, "O1", BigInteger.valueOf(1L)));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#div(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given {@link ELException#ELException(String)} with pMessage is
   * {@code An error occurred}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#div(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test div(TypeConverter, Object, Object); given ELException(String) with pMessage is 'An error occurred'; then throw ELException")
  void testDiv_givenELExceptionWithPMessageIsAnErrorOccurred_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Double>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.div(converter, "O1", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#div(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given ten.</li>
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)}
   * return ten.</li>
   *   <li>Then return doubleValue is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#div(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test div(TypeConverter, Object, Object); given ten; when TypeConverter convert(Object, Class) return ten; then return doubleValue is one")
  void testDiv_givenTen_whenTypeConverterConvertReturnTen_thenReturnDoubleValueIsOne() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Double>>any())).thenReturn(10.0d);

    // Act
    Number actualDivResult = NumberOperations.div(converter, "O1", "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertEquals(1.0d, actualDivResult.doubleValue());
  }

  /**
   * Test {@link NumberOperations#div(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given ten.</li>
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)}
   * return ten.</li>
   *   <li>Then return doubleValue is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#div(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test div(TypeConverter, Object, Object); given ten; when TypeConverter convert(Object, Class) return ten; then return doubleValue is one")
  void testDiv_givenTen_whenTypeConverterConvertReturnTen_thenReturnDoubleValueIsOne2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Double>>any())).thenReturn(10.0d);

    // Act
    Number actualDivResult = NumberOperations.div(converter, null, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertEquals(1.0d, actualDivResult.doubleValue());
  }

  /**
   * Test {@link NumberOperations#div(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#div(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test div(TypeConverter, Object, Object); when BigDecimal(String) with '2.3'; then throw ELException")
  void testDiv_whenBigDecimalWith23_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Double>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.div(converter, new BigDecimal("2.3"), "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#div(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#div(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test div(TypeConverter, Object, Object); when BigDecimal(String) with '2.3'; then throw ELException")
  void testDiv_whenBigDecimalWith23_thenThrowELException2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Double>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.div(converter, "O1", new BigDecimal("2.3")));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#div(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When valueOf two.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#div(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test div(TypeConverter, Object, Object); when valueOf two; then throw ELException")
  void testDiv_whenValueOfTwo_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Double>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.div(converter, BigInteger.valueOf(2L), "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#mod(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then calls {@link TypeConverter#convert(Object, Class)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#mod(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test mod(TypeConverter, Object, Object); given one; when 'null'; then calls convert(Object, Class)")
  void testMod_givenOne_whenNull_thenCallsConvert() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any())).thenReturn(1L);

    // Act
    NumberOperations.mod(converter, null, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#mod(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When one.</li>
   *   <li>Then calls {@link TypeConverter#convert(Object, Class)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#mod(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test mod(TypeConverter, Object, Object); given one; when one; then calls convert(Object, Class)")
  void testMod_givenOne_whenOne_thenCallsConvert() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any())).thenReturn(1L);

    // Act
    NumberOperations.mod(converter, 1L, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#mod(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)}
   * return one.</li>
   *   <li>Then calls {@link TypeConverter#convert(Object, Class)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#mod(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test mod(TypeConverter, Object, Object); given one; when TypeConverter convert(Object, Class) return one; then calls convert(Object, Class)")
  void testMod_givenOne_whenTypeConverterConvertReturnOne_thenCallsConvert() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any())).thenReturn(1L);

    // Act
    NumberOperations.mod(converter, "O1", "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#mod(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#mod(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test mod(TypeConverter, Object, Object); when BigDecimal(String) with '2.3'; then throw ELException")
  void testMod_whenBigDecimalWith23_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mod(converter, new BigDecimal("2.3"), "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#mod(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#mod(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test mod(TypeConverter, Object, Object); when BigDecimal(String) with '2.3'; then throw ELException")
  void testMod_whenBigDecimalWith23_thenThrowELException2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mod(converter, "O1", new BigDecimal("2.3")));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#mod(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When {@code java.lang.Byte}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#mod(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test mod(TypeConverter, Object, Object); when 'java.lang.Byte'; then throw ELException")
  void testMod_whenJavaLangByte_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mod(converter, "java.lang.Byte", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#mod(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When {@code O1}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#mod(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test mod(TypeConverter, Object, Object); when 'O1'; then throw ELException")
  void testMod_whenO1_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mod(converter, "O1", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#mod(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#mod(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test mod(TypeConverter, Object, Object); when ten; then throw ELException")
  void testMod_whenTen_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mod(converter, 10.0f, "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#mod(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#mod(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test mod(TypeConverter, Object, Object); when ten; then throw ELException")
  void testMod_whenTen_thenThrowELException2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mod(converter, 10.0d, "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#mod(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When valueOf one.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#mod(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test mod(TypeConverter, Object, Object); when valueOf one; then throw ELException")
  void testMod_whenValueOfOne_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mod(converter, BigInteger.valueOf(1L), "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#mod(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When valueOf one.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link NumberOperations#mod(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test mod(TypeConverter, Object, Object); when valueOf one; then throw ELException")
  void testMod_whenValueOfOne_thenThrowELException2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mod(converter, "O1", BigInteger.valueOf(1L)));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link NumberOperations#neg(TypeConverter, Object)}.
   * <ul>
   *   <li>Given ten.</li>
   *   <li>When {@code Value}.</li>
   *   <li>Then return doubleValue is minus ten.</li>
   * </ul>
   * <p>
   * Method under test: {@link NumberOperations#neg(TypeConverter, Object)}
   */
  @Test
  @DisplayName("Test neg(TypeConverter, Object); given ten; when 'Value'; then return doubleValue is minus ten")
  void testNeg_givenTen_whenValue_thenReturnDoubleValueIsMinusTen() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Double>>any())).thenReturn(10.0d);

    // Act
    Number actualNegResult = NumberOperations.neg(converter, "Value");

    // Assert
    verify(converter).convert(isA(Object.class), isA(Class.class));
    assertEquals(-10.0d, actualNegResult.doubleValue());
  }
}
