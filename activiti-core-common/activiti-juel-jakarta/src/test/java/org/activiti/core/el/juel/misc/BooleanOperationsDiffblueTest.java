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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import jakarta.el.ELException;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BooleanOperationsDiffblueTest {
  /**
   * Test {@link BooleanOperations#lt(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given {@code Convert}.</li>
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)}
   * return {@code Convert}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BooleanOperations#lt(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test lt(TypeConverter, Object, Object); given 'Convert'; when TypeConverter convert(Object, Class) return 'Convert'; then return 'false'")
  void testLt_givenConvert_whenTypeConverterConvertReturnConvert_thenReturnFalse() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any())).thenReturn("Convert");

    // Act
    boolean actualLtResult = BooleanOperations.lt(converter, "O1", "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertFalse(actualLtResult);
  }

  /**
   * Test {@link BooleanOperations#lt(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given {@link ELException#ELException(String)} with pMessage is
   * {@code An error occurred}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BooleanOperations#lt(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test lt(TypeConverter, Object, Object); given ELException(String) with pMessage is 'An error occurred'; then throw ELException")
  void testLt_givenELExceptionWithPMessageIsAnErrorOccurred_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> BooleanOperations.lt(converter, "O1", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link BooleanOperations#lt(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given {@link ELException#ELException(String)} with pMessage is
   * {@code An error occurred}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BooleanOperations#lt(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test lt(TypeConverter, Object, Object); given ELException(String) with pMessage is 'An error occurred'; then throw ELException")
  void testLt_givenELExceptionWithPMessageIsAnErrorOccurred_thenThrowELException2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> BooleanOperations.lt(converter, 2, "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link BooleanOperations#lt(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given {@link ELException#ELException(String)} with pMessage is
   * {@code An error occurred}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BooleanOperations#lt(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test lt(TypeConverter, Object, Object); given ELException(String) with pMessage is 'An error occurred'; then throw ELException")
  void testLt_givenELExceptionWithPMessageIsAnErrorOccurred_thenThrowELException3() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> BooleanOperations.lt(converter, "O1", 2));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link BooleanOperations#lt(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)}
   * return one.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BooleanOperations#lt(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test lt(TypeConverter, Object, Object); given one; when TypeConverter convert(Object, Class) return one; then return 'false'")
  void testLt_givenOne_whenTypeConverterConvertReturnOne_thenReturnFalse() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any())).thenReturn(1L);

    // Act
    boolean actualLtResult = BooleanOperations.lt(converter, 42, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertFalse(actualLtResult);
  }

  /**
   * Test {@link BooleanOperations#lt(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When {@link TypeConverter}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BooleanOperations#lt(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test lt(TypeConverter, Object, Object); when TypeConverter; then return 'false'")
  void testLt_whenTypeConverter_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(BooleanOperations.lt(mock(TypeConverter.class), null, null));
    assertFalse(BooleanOperations.lt(mock(TypeConverter.class), null, "O2"));
    assertFalse(BooleanOperations.lt(mock(TypeConverter.class), "O1", null));
  }

  /**
   * Test {@link BooleanOperations#gt(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given {@code Convert}.</li>
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)}
   * return {@code Convert}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BooleanOperations#gt(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test gt(TypeConverter, Object, Object); given 'Convert'; when TypeConverter convert(Object, Class) return 'Convert'; then return 'false'")
  void testGt_givenConvert_whenTypeConverterConvertReturnConvert_thenReturnFalse() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any())).thenReturn("Convert");

    // Act
    boolean actualGtResult = BooleanOperations.gt(converter, "O1", "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertFalse(actualGtResult);
  }

  /**
   * Test {@link BooleanOperations#gt(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given {@link ELException#ELException(String)} with pMessage is
   * {@code An error occurred}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BooleanOperations#gt(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test gt(TypeConverter, Object, Object); given ELException(String) with pMessage is 'An error occurred'; then throw ELException")
  void testGt_givenELExceptionWithPMessageIsAnErrorOccurred_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> BooleanOperations.gt(converter, "O1", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link BooleanOperations#gt(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given {@link ELException#ELException(String)} with pMessage is
   * {@code An error occurred}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BooleanOperations#gt(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test gt(TypeConverter, Object, Object); given ELException(String) with pMessage is 'An error occurred'; then throw ELException")
  void testGt_givenELExceptionWithPMessageIsAnErrorOccurred_thenThrowELException2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> BooleanOperations.gt(converter, 2, "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link BooleanOperations#gt(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given {@link ELException#ELException(String)} with pMessage is
   * {@code An error occurred}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BooleanOperations#gt(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test gt(TypeConverter, Object, Object); given ELException(String) with pMessage is 'An error occurred'; then throw ELException")
  void testGt_givenELExceptionWithPMessageIsAnErrorOccurred_thenThrowELException3() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> BooleanOperations.gt(converter, "O1", 2));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link BooleanOperations#gt(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)}
   * return one.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BooleanOperations#gt(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test gt(TypeConverter, Object, Object); given one; when TypeConverter convert(Object, Class) return one; then return 'false'")
  void testGt_givenOne_whenTypeConverterConvertReturnOne_thenReturnFalse() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any())).thenReturn(1L);

    // Act
    boolean actualGtResult = BooleanOperations.gt(converter, 42, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertFalse(actualGtResult);
  }

  /**
   * Test {@link BooleanOperations#gt(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When {@link TypeConverter}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BooleanOperations#gt(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test gt(TypeConverter, Object, Object); when TypeConverter; then return 'false'")
  void testGt_whenTypeConverter_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(BooleanOperations.gt(mock(TypeConverter.class), null, null));
    assertFalse(BooleanOperations.gt(mock(TypeConverter.class), null, "O2"));
    assertFalse(BooleanOperations.gt(mock(TypeConverter.class), "O1", null));
  }

  /**
   * Test {@link BooleanOperations#ge(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given {@code Convert}.</li>
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)}
   * return {@code Convert}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BooleanOperations#ge(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test ge(TypeConverter, Object, Object); given 'Convert'; when TypeConverter convert(Object, Class) return 'Convert'; then return 'true'")
  void testGe_givenConvert_whenTypeConverterConvertReturnConvert_thenReturnTrue() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any())).thenReturn("Convert");

    // Act
    boolean actualGeResult = BooleanOperations.ge(converter, "O1", "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertTrue(actualGeResult);
  }

  /**
   * Test {@link BooleanOperations#ge(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given {@link ELException#ELException(String)} with pMessage is
   * {@code An error occurred}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BooleanOperations#ge(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test ge(TypeConverter, Object, Object); given ELException(String) with pMessage is 'An error occurred'; then throw ELException")
  void testGe_givenELExceptionWithPMessageIsAnErrorOccurred_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> BooleanOperations.ge(converter, "O1", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link BooleanOperations#ge(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given {@link ELException#ELException(String)} with pMessage is
   * {@code An error occurred}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BooleanOperations#ge(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test ge(TypeConverter, Object, Object); given ELException(String) with pMessage is 'An error occurred'; then throw ELException")
  void testGe_givenELExceptionWithPMessageIsAnErrorOccurred_thenThrowELException2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> BooleanOperations.ge(converter, 2, "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link BooleanOperations#ge(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given {@link ELException#ELException(String)} with pMessage is
   * {@code An error occurred}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BooleanOperations#ge(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test ge(TypeConverter, Object, Object); given ELException(String) with pMessage is 'An error occurred'; then throw ELException")
  void testGe_givenELExceptionWithPMessageIsAnErrorOccurred_thenThrowELException3() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> BooleanOperations.ge(converter, "O1", 2));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link BooleanOperations#ge(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)}
   * return one.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BooleanOperations#ge(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test ge(TypeConverter, Object, Object); given one; when TypeConverter convert(Object, Class) return one; then return 'true'")
  void testGe_givenOne_whenTypeConverterConvertReturnOne_thenReturnTrue() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any())).thenReturn(1L);

    // Act
    boolean actualGeResult = BooleanOperations.ge(converter, 42, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertTrue(actualGeResult);
  }

  /**
   * Test {@link BooleanOperations#ge(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When {@link TypeConverter}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BooleanOperations#ge(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test ge(TypeConverter, Object, Object); when TypeConverter; then return 'false'")
  void testGe_whenTypeConverter_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(BooleanOperations.ge(mock(TypeConverter.class), null, "O2"));
    assertFalse(BooleanOperations.ge(mock(TypeConverter.class), "O1", null));
  }

  /**
   * Test {@link BooleanOperations#ge(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When {@link TypeConverter}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BooleanOperations#ge(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test ge(TypeConverter, Object, Object); when TypeConverter; then return 'true'")
  void testGe_whenTypeConverter_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(BooleanOperations.ge(mock(TypeConverter.class), null, null));
  }

  /**
   * Test {@link BooleanOperations#le(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given {@code Convert}.</li>
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)}
   * return {@code Convert}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BooleanOperations#le(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test le(TypeConverter, Object, Object); given 'Convert'; when TypeConverter convert(Object, Class) return 'Convert'; then return 'true'")
  void testLe_givenConvert_whenTypeConverterConvertReturnConvert_thenReturnTrue() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any())).thenReturn("Convert");

    // Act
    boolean actualLeResult = BooleanOperations.le(converter, "O1", "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertTrue(actualLeResult);
  }

  /**
   * Test {@link BooleanOperations#le(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given {@link ELException#ELException(String)} with pMessage is
   * {@code An error occurred}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BooleanOperations#le(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test le(TypeConverter, Object, Object); given ELException(String) with pMessage is 'An error occurred'; then throw ELException")
  void testLe_givenELExceptionWithPMessageIsAnErrorOccurred_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> BooleanOperations.le(converter, "O1", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link BooleanOperations#le(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given {@link ELException#ELException(String)} with pMessage is
   * {@code An error occurred}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BooleanOperations#le(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test le(TypeConverter, Object, Object); given ELException(String) with pMessage is 'An error occurred'; then throw ELException")
  void testLe_givenELExceptionWithPMessageIsAnErrorOccurred_thenThrowELException2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> BooleanOperations.le(converter, 2, "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link BooleanOperations#le(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given {@link ELException#ELException(String)} with pMessage is
   * {@code An error occurred}.</li>
   *   <li>Then throw {@link ELException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BooleanOperations#le(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test le(TypeConverter, Object, Object); given ELException(String) with pMessage is 'An error occurred'; then throw ELException")
  void testLe_givenELExceptionWithPMessageIsAnErrorOccurred_thenThrowELException3() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> BooleanOperations.le(converter, "O1", 2));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link BooleanOperations#le(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)}
   * return one.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BooleanOperations#le(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test le(TypeConverter, Object, Object); given one; when TypeConverter convert(Object, Class) return one; then return 'true'")
  void testLe_givenOne_whenTypeConverterConvertReturnOne_thenReturnTrue() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any())).thenReturn(1L);

    // Act
    boolean actualLeResult = BooleanOperations.le(converter, 42, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertTrue(actualLeResult);
  }

  /**
   * Test {@link BooleanOperations#le(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When {@link TypeConverter}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BooleanOperations#le(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test le(TypeConverter, Object, Object); when TypeConverter; then return 'false'")
  void testLe_whenTypeConverter_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(BooleanOperations.le(mock(TypeConverter.class), null, "O2"));
    assertFalse(BooleanOperations.le(mock(TypeConverter.class), "O1", null));
  }

  /**
   * Test {@link BooleanOperations#le(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>When {@link TypeConverter}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BooleanOperations#le(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test le(TypeConverter, Object, Object); when TypeConverter; then return 'true'")
  void testLe_whenTypeConverter_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(BooleanOperations.le(mock(TypeConverter.class), null, null));
  }

  /**
   * Test {@link BooleanOperations#eq(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given {@code Convert}.</li>
   *   <li>When {@code COMMON}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BooleanOperations#eq(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test eq(TypeConverter, Object, Object); given 'Convert'; when 'COMMON'; then return 'false'")
  void testEq_givenConvert_whenCommon_thenReturnFalse() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any())).thenReturn("Convert");

    // Act
    boolean actualEqResult = BooleanOperations.eq(converter, Character.UnicodeScript.COMMON, "O2");

    // Assert
    verify(converter).convert(isA(Object.class), isA(Class.class));
    assertFalse(actualEqResult);
  }

  /**
   * Test {@link BooleanOperations#eq(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given {@code Convert}.</li>
   *   <li>When {@code O1}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BooleanOperations#eq(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test eq(TypeConverter, Object, Object); given 'Convert'; when 'O1'; then return 'true'")
  void testEq_givenConvert_whenO1_thenReturnTrue() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any())).thenReturn("Convert");

    // Act
    boolean actualEqResult = BooleanOperations.eq(converter, "O1", "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertTrue(actualEqResult);
  }

  /**
   * Test {@link BooleanOperations#ne(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given {@code Convert}.</li>
   *   <li>When {@code COMMON}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BooleanOperations#ne(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test ne(TypeConverter, Object, Object); given 'Convert'; when 'COMMON'; then return 'true'")
  void testNe_givenConvert_whenCommon_thenReturnTrue() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any())).thenReturn("Convert");

    // Act
    boolean actualNeResult = BooleanOperations.ne(converter, Character.UnicodeScript.COMMON, "O2");

    // Assert
    verify(converter).convert(isA(Object.class), isA(Class.class));
    assertTrue(actualNeResult);
  }

  /**
   * Test {@link BooleanOperations#ne(TypeConverter, Object, Object)}.
   * <ul>
   *   <li>Given {@code Convert}.</li>
   *   <li>When {@code O1}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BooleanOperations#ne(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test ne(TypeConverter, Object, Object); given 'Convert'; when 'O1'; then return 'false'")
  void testNe_givenConvert_whenO1_thenReturnFalse() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any())).thenReturn("Convert");

    // Act
    boolean actualNeResult = BooleanOperations.ne(converter, "O1", "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertFalse(actualNeResult);
  }

  /**
   * Test {@link BooleanOperations#empty(TypeConverter, Object)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BooleanOperations#empty(TypeConverter, Object)}
   */
  @Test
  @DisplayName("Test empty(TypeConverter, Object); when '42'; then return 'false'")
  void testEmpty_when42_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(BooleanOperations.empty(mock(TypeConverter.class), "42"));
  }

  /**
   * Test {@link BooleanOperations#empty(TypeConverter, Object)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BooleanOperations#empty(TypeConverter, Object)}
   */
  @Test
  @DisplayName("Test empty(TypeConverter, Object); when ArrayList(); then return 'true'")
  void testEmpty_whenArrayList_thenReturnTrue() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);

    // Act and Assert
    assertTrue(BooleanOperations.empty(converter, new ArrayList<>()));
  }

  /**
   * Test {@link BooleanOperations#empty(TypeConverter, Object)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BooleanOperations#empty(TypeConverter, Object)}
   */
  @Test
  @DisplayName("Test empty(TypeConverter, Object); when empty string; then return 'true'")
  void testEmpty_whenEmptyString_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(BooleanOperations.empty(mock(TypeConverter.class), ""));
  }

  /**
   * Test {@link BooleanOperations#empty(TypeConverter, Object)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BooleanOperations#empty(TypeConverter, Object)}
   */
  @Test
  @DisplayName("Test empty(TypeConverter, Object); when HashMap(); then return 'true'")
  void testEmpty_whenHashMap_thenReturnTrue() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);

    // Act and Assert
    assertTrue(BooleanOperations.empty(converter, new HashMap<>()));
  }

  /**
   * Test {@link BooleanOperations#empty(TypeConverter, Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BooleanOperations#empty(TypeConverter, Object)}
   */
  @Test
  @DisplayName("Test empty(TypeConverter, Object); when 'null'; then return 'true'")
  void testEmpty_whenNull_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(BooleanOperations.empty(mock(TypeConverter.class), null));
  }
}
