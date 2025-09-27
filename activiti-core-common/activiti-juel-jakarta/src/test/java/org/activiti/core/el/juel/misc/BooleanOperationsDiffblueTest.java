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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.el.ELException;
import java.lang.Character.UnicodeScript;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BooleanOperationsDiffblueTest {
  /**
   * Test {@link BooleanOperations#lt(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@code Convert}.
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)} return {@code
   *       Convert}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#lt(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test lt(TypeConverter, Object, Object); given 'Convert'; when TypeConverter convert(Object, Class) return 'Convert'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.lt(TypeConverter, Object, Object)"})
  void testLt_givenConvert_whenTypeConverterConvertReturnConvert_thenReturnFalse()
      throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenReturn("Convert");

    // Act
    boolean actualLtResult = BooleanOperations.lt(converter, "O1", "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertFalse(actualLtResult);
  }

  /**
   * Test {@link BooleanOperations#lt(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link ELException#ELException(String)} with pMessage is {@code An error occurred}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#lt(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test lt(TypeConverter, Object, Object); given ELException(String) with pMessage is 'An error occurred'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.lt(TypeConverter, Object, Object)"})
  void testLt_givenELExceptionWithPMessageIsAnErrorOccurred_thenThrowELException()
      throws ELException {
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
   *
   * <ul>
   *   <li>Given {@link ELException#ELException(String)} with pMessage is {@code An error occurred}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#lt(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test lt(TypeConverter, Object, Object); given ELException(String) with pMessage is 'An error occurred'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.lt(TypeConverter, Object, Object)"})
  void testLt_givenELExceptionWithPMessageIsAnErrorOccurred_thenThrowELException2()
      throws ELException {
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
   *
   * <ul>
   *   <li>Given {@link ELException#ELException(String)} with pMessage is {@code An error occurred}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#lt(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test lt(TypeConverter, Object, Object); given ELException(String) with pMessage is 'An error occurred'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.lt(TypeConverter, Object, Object)"})
  void testLt_givenELExceptionWithPMessageIsAnErrorOccurred_thenThrowELException3()
      throws ELException {
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
   *
   * <ul>
   *   <li>Given one.
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)} return one.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#lt(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test lt(TypeConverter, Object, Object); given one; when TypeConverter convert(Object, Class) return one; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.lt(TypeConverter, Object, Object)"})
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
   *
   * <ul>
   *   <li>When {@link TypeConverter}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#lt(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test lt(TypeConverter, Object, Object); when TypeConverter; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.lt(TypeConverter, Object, Object)"})
  void testLt_whenTypeConverter_thenReturnFalse() {
    // Arrange and Act
    boolean actualLtResult = BooleanOperations.lt(mock(TypeConverter.class), null, null);

    // Assert
    assertFalse(actualLtResult);
  }

  /**
   * Test {@link BooleanOperations#lt(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>When {@link TypeConverter}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#lt(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test lt(TypeConverter, Object, Object); when TypeConverter; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.lt(TypeConverter, Object, Object)"})
  void testLt_whenTypeConverter_thenReturnFalse2() {
    // Arrange and Act
    boolean actualLtResult = BooleanOperations.lt(mock(TypeConverter.class), null, "O2");

    // Assert
    assertFalse(actualLtResult);
  }

  /**
   * Test {@link BooleanOperations#lt(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>When {@link TypeConverter}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#lt(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test lt(TypeConverter, Object, Object); when TypeConverter; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.lt(TypeConverter, Object, Object)"})
  void testLt_whenTypeConverter_thenReturnFalse3() {
    // Arrange and Act
    boolean actualLtResult = BooleanOperations.lt(mock(TypeConverter.class), "O1", null);

    // Assert
    assertFalse(actualLtResult);
  }

  /**
   * Test {@link BooleanOperations#gt(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@code Convert}.
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)} return {@code
   *       Convert}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#gt(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test gt(TypeConverter, Object, Object); given 'Convert'; when TypeConverter convert(Object, Class) return 'Convert'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.gt(TypeConverter, Object, Object)"})
  void testGt_givenConvert_whenTypeConverterConvertReturnConvert_thenReturnFalse()
      throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenReturn("Convert");

    // Act
    boolean actualGtResult = BooleanOperations.gt(converter, "O1", "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertFalse(actualGtResult);
  }

  /**
   * Test {@link BooleanOperations#gt(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link ELException#ELException(String)} with pMessage is {@code An error occurred}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#gt(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test gt(TypeConverter, Object, Object); given ELException(String) with pMessage is 'An error occurred'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.gt(TypeConverter, Object, Object)"})
  void testGt_givenELExceptionWithPMessageIsAnErrorOccurred_thenThrowELException()
      throws ELException {
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
   *
   * <ul>
   *   <li>Given {@link ELException#ELException(String)} with pMessage is {@code An error occurred}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#gt(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test gt(TypeConverter, Object, Object); given ELException(String) with pMessage is 'An error occurred'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.gt(TypeConverter, Object, Object)"})
  void testGt_givenELExceptionWithPMessageIsAnErrorOccurred_thenThrowELException2()
      throws ELException {
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
   *
   * <ul>
   *   <li>Given {@link ELException#ELException(String)} with pMessage is {@code An error occurred}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#gt(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test gt(TypeConverter, Object, Object); given ELException(String) with pMessage is 'An error occurred'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.gt(TypeConverter, Object, Object)"})
  void testGt_givenELExceptionWithPMessageIsAnErrorOccurred_thenThrowELException3()
      throws ELException {
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
   *
   * <ul>
   *   <li>Given one.
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)} return one.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#gt(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test gt(TypeConverter, Object, Object); given one; when TypeConverter convert(Object, Class) return one; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.gt(TypeConverter, Object, Object)"})
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
   *
   * <ul>
   *   <li>When {@link TypeConverter}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#gt(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test gt(TypeConverter, Object, Object); when TypeConverter; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.gt(TypeConverter, Object, Object)"})
  void testGt_whenTypeConverter_thenReturnFalse() {
    // Arrange and Act
    boolean actualGtResult = BooleanOperations.gt(mock(TypeConverter.class), null, null);

    // Assert
    assertFalse(actualGtResult);
  }

  /**
   * Test {@link BooleanOperations#gt(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>When {@link TypeConverter}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#gt(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test gt(TypeConverter, Object, Object); when TypeConverter; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.gt(TypeConverter, Object, Object)"})
  void testGt_whenTypeConverter_thenReturnFalse2() {
    // Arrange and Act
    boolean actualGtResult = BooleanOperations.gt(mock(TypeConverter.class), null, "O2");

    // Assert
    assertFalse(actualGtResult);
  }

  /**
   * Test {@link BooleanOperations#gt(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>When {@link TypeConverter}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#gt(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test gt(TypeConverter, Object, Object); when TypeConverter; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.gt(TypeConverter, Object, Object)"})
  void testGt_whenTypeConverter_thenReturnFalse3() {
    // Arrange and Act
    boolean actualGtResult = BooleanOperations.gt(mock(TypeConverter.class), "O1", null);

    // Assert
    assertFalse(actualGtResult);
  }

  /**
   * Test {@link BooleanOperations#ge(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@code Convert}.
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)} return {@code
   *       Convert}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#ge(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test ge(TypeConverter, Object, Object); given 'Convert'; when TypeConverter convert(Object, Class) return 'Convert'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.ge(TypeConverter, Object, Object)"})
  void testGe_givenConvert_whenTypeConverterConvertReturnConvert_thenReturnTrue()
      throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenReturn("Convert");

    // Act
    boolean actualGeResult = BooleanOperations.ge(converter, "O1", "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertTrue(actualGeResult);
  }

  /**
   * Test {@link BooleanOperations#ge(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link ELException#ELException(String)} with pMessage is {@code An error occurred}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#ge(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test ge(TypeConverter, Object, Object); given ELException(String) with pMessage is 'An error occurred'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.ge(TypeConverter, Object, Object)"})
  void testGe_givenELExceptionWithPMessageIsAnErrorOccurred_thenThrowELException()
      throws ELException {
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
   *
   * <ul>
   *   <li>Given {@link ELException#ELException(String)} with pMessage is {@code An error occurred}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#ge(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test ge(TypeConverter, Object, Object); given ELException(String) with pMessage is 'An error occurred'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.ge(TypeConverter, Object, Object)"})
  void testGe_givenELExceptionWithPMessageIsAnErrorOccurred_thenThrowELException2()
      throws ELException {
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
   *
   * <ul>
   *   <li>Given {@link ELException#ELException(String)} with pMessage is {@code An error occurred}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#ge(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test ge(TypeConverter, Object, Object); given ELException(String) with pMessage is 'An error occurred'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.ge(TypeConverter, Object, Object)"})
  void testGe_givenELExceptionWithPMessageIsAnErrorOccurred_thenThrowELException3()
      throws ELException {
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
   *
   * <ul>
   *   <li>Given one.
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)} return one.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#ge(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test ge(TypeConverter, Object, Object); given one; when TypeConverter convert(Object, Class) return one; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.ge(TypeConverter, Object, Object)"})
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
   *
   * <ul>
   *   <li>When {@link TypeConverter}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#ge(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test ge(TypeConverter, Object, Object); when TypeConverter; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.ge(TypeConverter, Object, Object)"})
  void testGe_whenTypeConverter_thenReturnFalse() {
    // Arrange and Act
    boolean actualGeResult = BooleanOperations.ge(mock(TypeConverter.class), null, "O2");

    // Assert
    assertFalse(actualGeResult);
  }

  /**
   * Test {@link BooleanOperations#ge(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>When {@link TypeConverter}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#ge(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test ge(TypeConverter, Object, Object); when TypeConverter; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.ge(TypeConverter, Object, Object)"})
  void testGe_whenTypeConverter_thenReturnFalse2() {
    // Arrange and Act
    boolean actualGeResult = BooleanOperations.ge(mock(TypeConverter.class), "O1", null);

    // Assert
    assertFalse(actualGeResult);
  }

  /**
   * Test {@link BooleanOperations#ge(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>When {@link TypeConverter}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#ge(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test ge(TypeConverter, Object, Object); when TypeConverter; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.ge(TypeConverter, Object, Object)"})
  void testGe_whenTypeConverter_thenReturnTrue() {
    // Arrange and Act
    boolean actualGeResult = BooleanOperations.ge(mock(TypeConverter.class), null, null);

    // Assert
    assertTrue(actualGeResult);
  }

  /**
   * Test {@link BooleanOperations#le(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@code Convert}.
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)} return {@code
   *       Convert}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#le(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test le(TypeConverter, Object, Object); given 'Convert'; when TypeConverter convert(Object, Class) return 'Convert'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.le(TypeConverter, Object, Object)"})
  void testLe_givenConvert_whenTypeConverterConvertReturnConvert_thenReturnTrue()
      throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenReturn("Convert");

    // Act
    boolean actualLeResult = BooleanOperations.le(converter, "O1", "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertTrue(actualLeResult);
  }

  /**
   * Test {@link BooleanOperations#le(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link ELException#ELException(String)} with pMessage is {@code An error occurred}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#le(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test le(TypeConverter, Object, Object); given ELException(String) with pMessage is 'An error occurred'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.le(TypeConverter, Object, Object)"})
  void testLe_givenELExceptionWithPMessageIsAnErrorOccurred_thenThrowELException()
      throws ELException {
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
   *
   * <ul>
   *   <li>Given {@link ELException#ELException(String)} with pMessage is {@code An error occurred}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#le(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test le(TypeConverter, Object, Object); given ELException(String) with pMessage is 'An error occurred'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.le(TypeConverter, Object, Object)"})
  void testLe_givenELExceptionWithPMessageIsAnErrorOccurred_thenThrowELException2()
      throws ELException {
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
   *
   * <ul>
   *   <li>Given {@link ELException#ELException(String)} with pMessage is {@code An error occurred}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#le(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test le(TypeConverter, Object, Object); given ELException(String) with pMessage is 'An error occurred'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.le(TypeConverter, Object, Object)"})
  void testLe_givenELExceptionWithPMessageIsAnErrorOccurred_thenThrowELException3()
      throws ELException {
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
   *
   * <ul>
   *   <li>Given one.
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)} return one.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#le(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test le(TypeConverter, Object, Object); given one; when TypeConverter convert(Object, Class) return one; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.le(TypeConverter, Object, Object)"})
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
   *
   * <ul>
   *   <li>When {@link TypeConverter}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#le(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test le(TypeConverter, Object, Object); when TypeConverter; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.le(TypeConverter, Object, Object)"})
  void testLe_whenTypeConverter_thenReturnFalse() {
    // Arrange and Act
    boolean actualLeResult = BooleanOperations.le(mock(TypeConverter.class), null, "O2");

    // Assert
    assertFalse(actualLeResult);
  }

  /**
   * Test {@link BooleanOperations#le(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>When {@link TypeConverter}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#le(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test le(TypeConverter, Object, Object); when TypeConverter; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.le(TypeConverter, Object, Object)"})
  void testLe_whenTypeConverter_thenReturnFalse2() {
    // Arrange and Act
    boolean actualLeResult = BooleanOperations.le(mock(TypeConverter.class), "O1", null);

    // Assert
    assertFalse(actualLeResult);
  }

  /**
   * Test {@link BooleanOperations#le(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>When {@link TypeConverter}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#le(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test le(TypeConverter, Object, Object); when TypeConverter; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.le(TypeConverter, Object, Object)"})
  void testLe_whenTypeConverter_thenReturnTrue() {
    // Arrange and Act
    boolean actualLeResult = BooleanOperations.le(mock(TypeConverter.class), null, null);

    // Assert
    assertTrue(actualLeResult);
  }

  /**
   * Test {@link BooleanOperations#eq(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@code Convert}.
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)} return {@code
   *       Convert}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#eq(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test eq(TypeConverter, Object, Object); given 'Convert'; when TypeConverter convert(Object, Class) return 'Convert'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.eq(TypeConverter, Object, Object)"})
  void testEq_givenConvert_whenTypeConverterConvertReturnConvert_thenReturnFalse()
      throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenReturn("Convert");

    // Act
    boolean actualEqResult = BooleanOperations.eq(converter, UnicodeScript.COMMON, "O2");

    // Assert
    verify(converter).convert(isA(Object.class), isA(Class.class));
    assertFalse(actualEqResult);
  }

  /**
   * Test {@link BooleanOperations#eq(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@code Convert}.
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)} return {@code
   *       Convert}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#eq(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test eq(TypeConverter, Object, Object); given 'Convert'; when TypeConverter convert(Object, Class) return 'Convert'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.eq(TypeConverter, Object, Object)"})
  void testEq_givenConvert_whenTypeConverterConvertReturnConvert_thenReturnFalse2()
      throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenReturn("Convert");

    // Act
    boolean actualEqResult = BooleanOperations.eq(converter, "O1", UnicodeScript.COMMON);

    // Assert
    verify(converter).convert(isA(Object.class), isA(Class.class));
    assertFalse(actualEqResult);
  }

  /**
   * Test {@link BooleanOperations#eq(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@code Convert}.
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)} return {@code
   *       Convert}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#eq(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test eq(TypeConverter, Object, Object); given 'Convert'; when TypeConverter convert(Object, Class) return 'Convert'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.eq(TypeConverter, Object, Object)"})
  void testEq_givenConvert_whenTypeConverterConvertReturnConvert_thenReturnTrue()
      throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenReturn("Convert");

    // Act
    boolean actualEqResult = BooleanOperations.eq(converter, "O1", "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertTrue(actualEqResult);
  }

  /**
   * Test {@link BooleanOperations#eq(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link ELException#ELException(String)} with pMessage is {@code An error occurred}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#eq(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test eq(TypeConverter, Object, Object); given ELException(String) with pMessage is 'An error occurred'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.eq(TypeConverter, Object, Object)"})
  void testEq_givenELExceptionWithPMessageIsAnErrorOccurred_thenThrowELException()
      throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> BooleanOperations.eq(converter, "O1", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link BooleanOperations#eq(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given one.
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)} return one.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#eq(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test eq(TypeConverter, Object, Object); given one; when TypeConverter convert(Object, Class) return one; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.eq(TypeConverter, Object, Object)"})
  void testEq_givenOne_whenTypeConverterConvertReturnOne_thenReturnTrue() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any())).thenReturn(1L);

    // Act
    boolean actualEqResult = BooleanOperations.eq(converter, 42, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertTrue(actualEqResult);
  }

  /**
   * Test {@link BooleanOperations#eq(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>When {@code COMMON}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#eq(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test eq(TypeConverter, Object, Object); when 'COMMON'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.eq(TypeConverter, Object, Object)"})
  void testEq_whenCommon_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(
        ELException.class, () -> BooleanOperations.eq(converter, UnicodeScript.COMMON, "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link BooleanOperations#eq(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>When {@code COMMON}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#eq(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test eq(TypeConverter, Object, Object); when 'COMMON'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.eq(TypeConverter, Object, Object)"})
  void testEq_whenCommon_thenThrowELException2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(
        ELException.class, () -> BooleanOperations.eq(converter, "O1", UnicodeScript.COMMON));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link BooleanOperations#eq(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>When two.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#eq(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test eq(TypeConverter, Object, Object); when two; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.eq(TypeConverter, Object, Object)"})
  void testEq_whenTwo_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> BooleanOperations.eq(converter, 2, "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link BooleanOperations#eq(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>When two.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#eq(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test eq(TypeConverter, Object, Object); when two; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.eq(TypeConverter, Object, Object)"})
  void testEq_whenTwo_thenThrowELException2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> BooleanOperations.eq(converter, "O1", 2));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link BooleanOperations#eq(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>When {@link TypeConverter}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#eq(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test eq(TypeConverter, Object, Object); when TypeConverter; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.eq(TypeConverter, Object, Object)"})
  void testEq_whenTypeConverter_thenReturnFalse() {
    // Arrange and Act
    boolean actualEqResult = BooleanOperations.eq(mock(TypeConverter.class), null, "O2");

    // Assert
    assertFalse(actualEqResult);
  }

  /**
   * Test {@link BooleanOperations#eq(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>When {@link TypeConverter}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#eq(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test eq(TypeConverter, Object, Object); when TypeConverter; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.eq(TypeConverter, Object, Object)"})
  void testEq_whenTypeConverter_thenReturnFalse2() {
    // Arrange and Act
    boolean actualEqResult = BooleanOperations.eq(mock(TypeConverter.class), "O1", null);

    // Assert
    assertFalse(actualEqResult);
  }

  /**
   * Test {@link BooleanOperations#eq(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>When {@link TypeConverter}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#eq(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test eq(TypeConverter, Object, Object); when TypeConverter; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.eq(TypeConverter, Object, Object)"})
  void testEq_whenTypeConverter_thenReturnTrue() {
    // Arrange and Act
    boolean actualEqResult = BooleanOperations.eq(mock(TypeConverter.class), null, null);

    // Assert
    assertTrue(actualEqResult);
  }

  /**
   * Test {@link BooleanOperations#ne(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@code Convert}.
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)} return {@code
   *       Convert}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#ne(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test ne(TypeConverter, Object, Object); given 'Convert'; when TypeConverter convert(Object, Class) return 'Convert'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.ne(TypeConverter, Object, Object)"})
  void testNe_givenConvert_whenTypeConverterConvertReturnConvert_thenReturnFalse()
      throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenReturn("Convert");

    // Act
    boolean actualNeResult = BooleanOperations.ne(converter, "O1", "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertFalse(actualNeResult);
  }

  /**
   * Test {@link BooleanOperations#ne(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@code Convert}.
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)} return {@code
   *       Convert}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#ne(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test ne(TypeConverter, Object, Object); given 'Convert'; when TypeConverter convert(Object, Class) return 'Convert'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.ne(TypeConverter, Object, Object)"})
  void testNe_givenConvert_whenTypeConverterConvertReturnConvert_thenReturnTrue()
      throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenReturn("Convert");

    // Act
    boolean actualNeResult = BooleanOperations.ne(converter, UnicodeScript.COMMON, "O2");

    // Assert
    verify(converter).convert(isA(Object.class), isA(Class.class));
    assertTrue(actualNeResult);
  }

  /**
   * Test {@link BooleanOperations#ne(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@code Convert}.
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)} return {@code
   *       Convert}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#ne(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test ne(TypeConverter, Object, Object); given 'Convert'; when TypeConverter convert(Object, Class) return 'Convert'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.ne(TypeConverter, Object, Object)"})
  void testNe_givenConvert_whenTypeConverterConvertReturnConvert_thenReturnTrue2()
      throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenReturn("Convert");

    // Act
    boolean actualNeResult = BooleanOperations.ne(converter, "O1", UnicodeScript.COMMON);

    // Assert
    verify(converter).convert(isA(Object.class), isA(Class.class));
    assertTrue(actualNeResult);
  }

  /**
   * Test {@link BooleanOperations#ne(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given {@link ELException#ELException(String)} with pMessage is {@code An error occurred}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#ne(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test ne(TypeConverter, Object, Object); given ELException(String) with pMessage is 'An error occurred'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.ne(TypeConverter, Object, Object)"})
  void testNe_givenELExceptionWithPMessageIsAnErrorOccurred_thenThrowELException()
      throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> BooleanOperations.ne(converter, "O1", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link BooleanOperations#ne(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>Given one.
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)} return one.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#ne(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test ne(TypeConverter, Object, Object); given one; when TypeConverter convert(Object, Class) return one; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.ne(TypeConverter, Object, Object)"})
  void testNe_givenOne_whenTypeConverterConvertReturnOne_thenReturnFalse() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any())).thenReturn(1L);

    // Act
    boolean actualNeResult = BooleanOperations.ne(converter, 1, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    assertFalse(actualNeResult);
  }

  /**
   * Test {@link BooleanOperations#ne(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>When {@code COMMON}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#ne(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test ne(TypeConverter, Object, Object); when 'COMMON'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.ne(TypeConverter, Object, Object)"})
  void testNe_whenCommon_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(
        ELException.class, () -> BooleanOperations.ne(converter, UnicodeScript.COMMON, "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link BooleanOperations#ne(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>When {@code COMMON}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#ne(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test ne(TypeConverter, Object, Object); when 'COMMON'; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.ne(TypeConverter, Object, Object)"})
  void testNe_whenCommon_thenThrowELException2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(
        ELException.class, () -> BooleanOperations.ne(converter, "O1", UnicodeScript.COMMON));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link BooleanOperations#ne(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>When two.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#ne(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test ne(TypeConverter, Object, Object); when two; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.ne(TypeConverter, Object, Object)"})
  void testNe_whenTwo_thenThrowELException() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> BooleanOperations.ne(converter, 2, "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link BooleanOperations#ne(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>When two.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#ne(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test ne(TypeConverter, Object, Object); when two; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.ne(TypeConverter, Object, Object)"})
  void testNe_whenTwo_thenThrowELException2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> BooleanOperations.ne(converter, "O1", 2));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link BooleanOperations#ne(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>When {@link TypeConverter}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#ne(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test ne(TypeConverter, Object, Object); when TypeConverter; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.ne(TypeConverter, Object, Object)"})
  void testNe_whenTypeConverter_thenReturnFalse() {
    // Arrange and Act
    boolean actualNeResult = BooleanOperations.ne(mock(TypeConverter.class), null, null);

    // Assert
    assertFalse(actualNeResult);
  }

  /**
   * Test {@link BooleanOperations#ne(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>When {@link TypeConverter}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#ne(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test ne(TypeConverter, Object, Object); when TypeConverter; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.ne(TypeConverter, Object, Object)"})
  void testNe_whenTypeConverter_thenReturnTrue() {
    // Arrange and Act
    boolean actualNeResult = BooleanOperations.ne(mock(TypeConverter.class), null, "O2");

    // Assert
    assertTrue(actualNeResult);
  }

  /**
   * Test {@link BooleanOperations#ne(TypeConverter, Object, Object)}.
   *
   * <ul>
   *   <li>When {@link TypeConverter}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#ne(TypeConverter, Object, Object)}
   */
  @Test
  @DisplayName("Test ne(TypeConverter, Object, Object); when TypeConverter; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.ne(TypeConverter, Object, Object)"})
  void testNe_whenTypeConverter_thenReturnTrue2() {
    // Arrange and Act
    boolean actualNeResult = BooleanOperations.ne(mock(TypeConverter.class), "O1", null);

    // Assert
    assertTrue(actualNeResult);
  }

  /**
   * Test {@link BooleanOperations#empty(TypeConverter, Object)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#empty(TypeConverter, Object)}
   */
  @Test
  @DisplayName("Test empty(TypeConverter, Object); when '42'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.empty(TypeConverter, Object)"})
  void testEmpty_when42_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(BooleanOperations.empty(mock(TypeConverter.class), "42"));
  }

  /**
   * Test {@link BooleanOperations#empty(TypeConverter, Object)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#empty(TypeConverter, Object)}
   */
  @Test
  @DisplayName("Test empty(TypeConverter, Object); when ArrayList(); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.empty(TypeConverter, Object)"})
  void testEmpty_whenArrayList_thenReturnTrue() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);

    // Act and Assert
    assertTrue(BooleanOperations.empty(converter, new ArrayList<>()));
  }

  /**
   * Test {@link BooleanOperations#empty(TypeConverter, Object)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#empty(TypeConverter, Object)}
   */
  @Test
  @DisplayName("Test empty(TypeConverter, Object); when empty string; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.empty(TypeConverter, Object)"})
  void testEmpty_whenEmptyString_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(BooleanOperations.empty(mock(TypeConverter.class), ""));
  }

  /**
   * Test {@link BooleanOperations#empty(TypeConverter, Object)}.
   *
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#empty(TypeConverter, Object)}
   */
  @Test
  @DisplayName("Test empty(TypeConverter, Object); when HashMap(); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.empty(TypeConverter, Object)"})
  void testEmpty_whenHashMap_thenReturnTrue() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);

    // Act and Assert
    assertTrue(BooleanOperations.empty(converter, new HashMap<>()));
  }

  /**
   * Test {@link BooleanOperations#empty(TypeConverter, Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BooleanOperations#empty(TypeConverter, Object)}
   */
  @Test
  @DisplayName("Test empty(TypeConverter, Object); when 'null'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BooleanOperations.empty(TypeConverter, Object)"})
  void testEmpty_whenNull_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(BooleanOperations.empty(mock(TypeConverter.class), null));
  }
}
