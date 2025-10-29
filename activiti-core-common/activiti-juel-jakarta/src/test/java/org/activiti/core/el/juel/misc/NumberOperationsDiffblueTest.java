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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class NumberOperationsDiffblueTest {
  /**
   * Method under test:
   * {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  void testAdd() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any())).thenReturn(1L);

    // Act
    NumberOperations.add(converter, "O1", "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  void testAdd2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any())).thenReturn(1L);

    // Act
    NumberOperations.add(converter, 1L, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  void testAdd3() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any())).thenReturn(1L);

    // Act
    NumberOperations.add(converter, null, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  void testAdd4() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.add(converter, "O1", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  void testAdd5() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.add(converter, new BigDecimal("2.3"), "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  void testAdd6() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.add(converter, 10.0f, "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  void testAdd7() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.add(converter, 10.0d, "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  void testAdd8() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.add(converter, BigInteger.valueOf(1L), "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  void testAdd9() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.add(converter, "java.lang.Byte", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  void testAdd10() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.add(converter, "O1", new BigDecimal("2.3")));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  void testAdd11() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.add(converter, "O1", 10.0f));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#add(TypeConverter, Object, Object)}
   */
  @Test
  void testAdd12() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.add(converter, "O1", BigInteger.valueOf(1L)));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  void testSub() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any())).thenReturn(1L);

    // Act
    NumberOperations.sub(converter, "O1", "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  void testSub2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any())).thenReturn(1L);

    // Act
    NumberOperations.sub(converter, 1L, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  void testSub3() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any())).thenReturn(1L);

    // Act
    NumberOperations.sub(converter, null, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  void testSub4() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.sub(converter, "O1", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  void testSub5() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.sub(converter, new BigDecimal("2.3"), "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  void testSub6() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.sub(converter, 10.0f, "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  void testSub7() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.sub(converter, 10.0d, "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  void testSub8() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.sub(converter, BigInteger.valueOf(1L), "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  void testSub9() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.sub(converter, "java.lang.Byte", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  void testSub10() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.sub(converter, "O1", new BigDecimal("2.3")));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  void testSub11() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.sub(converter, "O1", 10.0f));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#sub(TypeConverter, Object, Object)}
   */
  @Test
  void testSub12() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.sub(converter, "O1", BigInteger.valueOf(1L)));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  void testMul() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any())).thenReturn(1L);

    // Act
    NumberOperations.mul(converter, "O1", "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  void testMul2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any())).thenReturn(1L);

    // Act
    NumberOperations.mul(converter, 1L, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  void testMul3() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any())).thenReturn(1L);

    // Act
    NumberOperations.mul(converter, null, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  void testMul4() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mul(converter, "O1", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  void testMul5() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mul(converter, new BigDecimal("2.3"), "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  void testMul6() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mul(converter, 10.0f, "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  void testMul7() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mul(converter, 10.0d, "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  void testMul8() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mul(converter, BigInteger.valueOf(1L), "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  void testMul9() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mul(converter, "java.lang.Byte", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  void testMul10() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mul(converter, "O1", new BigDecimal("2.3")));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  void testMul11() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mul(converter, "O1", 10.0f));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#mul(TypeConverter, Object, Object)}
   */
  @Test
  void testMul12() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mul(converter, "O1", BigInteger.valueOf(1L)));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#div(TypeConverter, Object, Object)}
   */
  @Test
  void testDiv() throws ELException {
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
   * Method under test:
   * {@link NumberOperations#div(TypeConverter, Object, Object)}
   */
  @Test
  void testDiv2() throws ELException {
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
   * Method under test:
   * {@link NumberOperations#div(TypeConverter, Object, Object)}
   */
  @Test
  void testDiv3() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Double>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.div(converter, "O1", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#div(TypeConverter, Object, Object)}
   */
  @Test
  void testDiv4() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Double>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.div(converter, new BigDecimal("2.3"), "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#div(TypeConverter, Object, Object)}
   */
  @Test
  void testDiv5() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Double>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.div(converter, BigInteger.valueOf(2L), "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#div(TypeConverter, Object, Object)}
   */
  @Test
  void testDiv6() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Double>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.div(converter, "O1", new BigDecimal("2.3")));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#mod(TypeConverter, Object, Object)}
   */
  @Test
  void testMod() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any())).thenReturn(1L);

    // Act
    NumberOperations.mod(converter, "O1", "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#mod(TypeConverter, Object, Object)}
   */
  @Test
  void testMod2() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any())).thenReturn(1L);

    // Act
    NumberOperations.mod(converter, 1L, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#mod(TypeConverter, Object, Object)}
   */
  @Test
  void testMod3() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any())).thenReturn(1L);

    // Act
    NumberOperations.mod(converter, null, "O2");

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#mod(TypeConverter, Object, Object)}
   */
  @Test
  void testMod4() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mod(converter, "O1", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#mod(TypeConverter, Object, Object)}
   */
  @Test
  void testMod5() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mod(converter, new BigDecimal("2.3"), "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#mod(TypeConverter, Object, Object)}
   */
  @Test
  void testMod6() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mod(converter, 10.0f, "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#mod(TypeConverter, Object, Object)}
   */
  @Test
  void testMod7() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mod(converter, 10.0d, "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#mod(TypeConverter, Object, Object)}
   */
  @Test
  void testMod8() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mod(converter, BigInteger.valueOf(1L), "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#mod(TypeConverter, Object, Object)}
   */
  @Test
  void testMod9() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mod(converter, "java.lang.Byte", "O2"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#mod(TypeConverter, Object, Object)}
   */
  @Test
  void testMod10() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mod(converter, "O1", new BigDecimal("2.3")));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test:
   * {@link NumberOperations#mod(TypeConverter, Object, Object)}
   */
  @Test
  void testMod11() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Long>>any()))
        .thenThrow(new ELException("An error occurred"));

    // Act and Assert
    assertThrows(ELException.class, () -> NumberOperations.mod(converter, "O1", BigInteger.valueOf(1L)));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Method under test: {@link NumberOperations#neg(TypeConverter, Object)}
   */
  @Test
  void testNeg() throws ELException {
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
