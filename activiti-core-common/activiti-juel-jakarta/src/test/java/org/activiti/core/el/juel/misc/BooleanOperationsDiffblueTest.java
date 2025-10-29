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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BooleanOperationsDiffblueTest {
  /**
   * Method under test:
   * {@link BooleanOperations#lt(TypeConverter, Object, Object)}
   */
  @Test
  void testLt() {
    // Arrange, Act and Assert
    assertFalse(BooleanOperations.lt(mock(TypeConverter.class), null, null));
    assertFalse(BooleanOperations.lt(mock(TypeConverter.class), null, "O2"));
    assertFalse(BooleanOperations.lt(mock(TypeConverter.class), "O1", null));
  }

  /**
   * Method under test:
   * {@link BooleanOperations#lt(TypeConverter, Object, Object)}
   */
  @Test
  void testLt2() throws ELException {
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
   * Method under test:
   * {@link BooleanOperations#lt(TypeConverter, Object, Object)}
   */
  @Test
  void testLt3() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> BooleanOperations.lt(converter, "O1", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link BooleanOperations#lt(TypeConverter, Object, Object)}
   */
  @Test
  void testLt4() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> BooleanOperations.lt(converter, 2, "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link BooleanOperations#lt(TypeConverter, Object, Object)}
   */
  @Test
  void testLt5() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> BooleanOperations.lt(converter, "O1", 2));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link BooleanOperations#lt(TypeConverter, Object, Object)}
   */
  @Test
  void testLt6() throws ELException {
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
   * Method under test:
   * {@link BooleanOperations#gt(TypeConverter, Object, Object)}
   */
  @Test
  void testGt() {
    // Arrange, Act and Assert
    assertFalse(BooleanOperations.gt(mock(TypeConverter.class), null, null));
    assertFalse(BooleanOperations.gt(mock(TypeConverter.class), null, "O2"));
    assertFalse(BooleanOperations.gt(mock(TypeConverter.class), "O1", null));
  }

  /**
   * Method under test:
   * {@link BooleanOperations#gt(TypeConverter, Object, Object)}
   */
  @Test
  void testGt2() throws ELException {
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
   * Method under test:
   * {@link BooleanOperations#gt(TypeConverter, Object, Object)}
   */
  @Test
  void testGt3() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> BooleanOperations.gt(converter, "O1", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link BooleanOperations#gt(TypeConverter, Object, Object)}
   */
  @Test
  void testGt4() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> BooleanOperations.gt(converter, 2, "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link BooleanOperations#gt(TypeConverter, Object, Object)}
   */
  @Test
  void testGt5() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> BooleanOperations.gt(converter, "O1", 2));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link BooleanOperations#gt(TypeConverter, Object, Object)}
   */
  @Test
  void testGt6() throws ELException {
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
   * Method under test:
   * {@link BooleanOperations#ge(TypeConverter, Object, Object)}
   */
  @Test
  void testGe() {
    // Arrange, Act and Assert
    assertTrue(BooleanOperations.ge(mock(TypeConverter.class), null, null));
    assertFalse(BooleanOperations.ge(mock(TypeConverter.class), null, "O2"));
    assertFalse(BooleanOperations.ge(mock(TypeConverter.class), "O1", null));
  }

  /**
   * Method under test:
   * {@link BooleanOperations#ge(TypeConverter, Object, Object)}
   */
  @Test
  void testGe2() throws ELException {
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
   * Method under test:
   * {@link BooleanOperations#ge(TypeConverter, Object, Object)}
   */
  @Test
  void testGe3() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> BooleanOperations.ge(converter, "O1", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link BooleanOperations#ge(TypeConverter, Object, Object)}
   */
  @Test
  void testGe4() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> BooleanOperations.ge(converter, 2, "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link BooleanOperations#ge(TypeConverter, Object, Object)}
   */
  @Test
  void testGe5() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> BooleanOperations.ge(converter, "O1", 2));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link BooleanOperations#ge(TypeConverter, Object, Object)}
   */
  @Test
  void testGe6() throws ELException {
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
   * Method under test:
   * {@link BooleanOperations#le(TypeConverter, Object, Object)}
   */
  @Test
  void testLe() {
    // Arrange, Act and Assert
    assertTrue(BooleanOperations.le(mock(TypeConverter.class), null, null));
    assertFalse(BooleanOperations.le(mock(TypeConverter.class), null, "O2"));
    assertFalse(BooleanOperations.le(mock(TypeConverter.class), "O1", null));
  }

  /**
   * Method under test:
   * {@link BooleanOperations#le(TypeConverter, Object, Object)}
   */
  @Test
  void testLe2() throws ELException {
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
   * Method under test:
   * {@link BooleanOperations#le(TypeConverter, Object, Object)}
   */
  @Test
  void testLe3() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> BooleanOperations.le(converter, "O1", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link BooleanOperations#le(TypeConverter, Object, Object)}
   */
  @Test
  void testLe4() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> BooleanOperations.le(converter, 2, "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link BooleanOperations#le(TypeConverter, Object, Object)}
   */
  @Test
  void testLe5() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<String>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> BooleanOperations.le(converter, "O1", 2));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link BooleanOperations#le(TypeConverter, Object, Object)}
   */
  @Test
  void testLe6() throws ELException {
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
   * Method under test:
   * {@link BooleanOperations#eq(TypeConverter, Object, Object)}
   */
  @Test
  void testEq() throws ELException {
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
   * Method under test:
   * {@link BooleanOperations#eq(TypeConverter, Object, Object)}
   */
  @Test
  void testEq2() throws ELException {
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
   * Method under test:
   * {@link BooleanOperations#ne(TypeConverter, Object, Object)}
   */
  @Test
  void testNe() throws ELException {
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
   * Method under test:
   * {@link BooleanOperations#ne(TypeConverter, Object, Object)}
   */
  @Test
  void testNe2() throws ELException {
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
   * Method under test: {@link BooleanOperations#empty(TypeConverter, Object)}
   */
  @Test
  void testEmpty() {
    // Arrange, Act and Assert
    assertFalse(BooleanOperations.empty(mock(TypeConverter.class), "42"));
    assertTrue(BooleanOperations.empty(mock(TypeConverter.class), null));
    assertTrue(BooleanOperations.empty(mock(TypeConverter.class), ""));
  }

  /**
   * Method under test: {@link BooleanOperations#empty(TypeConverter, Object)}
   */
  @Test
  void testEmpty2() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);

    // Act and Assert
    assertTrue(BooleanOperations.empty(converter, new HashMap<>()));
  }

  /**
   * Method under test: {@link BooleanOperations#empty(TypeConverter, Object)}
   */
  @Test
  void testEmpty3() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);

    // Act and Assert
    assertTrue(BooleanOperations.empty(converter, new ArrayList<>()));
  }
}
