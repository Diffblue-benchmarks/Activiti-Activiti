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
import org.junit.jupiter.api.Test;

class TypeConverterImplDiffblueTest {
  /**
   * Method under test: {@link TypeConverterImpl#coerceToBoolean(Object)}
   */
  @Test
  void testCoerceToBoolean() {
    // Arrange, Act and Assert
    assertFalse((new TypeConverterImpl()).coerceToBoolean("Value"));
    assertFalse((new TypeConverterImpl()).coerceToBoolean(null));
    assertFalse((new TypeConverterImpl()).coerceToBoolean(""));
    assertTrue((new TypeConverterImpl()).coerceToBoolean(true));
    assertThrows(ELException.class, () -> (new TypeConverterImpl()).coerceToBoolean(42));
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToCharacter(Object)}
   */
  @Test
  void testCoerceToCharacter() {
    // Arrange, Act and Assert
    assertEquals('V', (new TypeConverterImpl()).coerceToCharacter("Value").charValue());
    assertEquals('\u0000', (new TypeConverterImpl()).coerceToCharacter(null).charValue());
    assertEquals('\u0000', (new TypeConverterImpl()).coerceToCharacter("").charValue());
    assertEquals('*', (new TypeConverterImpl()).coerceToCharacter(42).charValue());
    assertEquals('\u0001', (new TypeConverterImpl()).coerceToCharacter('\u0001').charValue());
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToBigDecimal(Object)}
   */
  @Test
  void testCoerceToBigDecimal() {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> (new TypeConverterImpl()).coerceToBigDecimal("Value"));
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToBigDecimal(Object)}
   */
  @Test
  void testCoerceToBigDecimal2() {
    // Arrange and Act
    BigDecimal actualCoerceToBigDecimalResult = (new TypeConverterImpl()).coerceToBigDecimal(null);

    // Assert
    assertEquals(new BigDecimal("0"), actualCoerceToBigDecimalResult);
    assertSame(actualCoerceToBigDecimalResult.ZERO, actualCoerceToBigDecimalResult);
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToBigDecimal(Object)}
   */
  @Test
  void testCoerceToBigDecimal3() {
    // Arrange and Act
    BigDecimal actualCoerceToBigDecimalResult = (new TypeConverterImpl()).coerceToBigDecimal("");

    // Assert
    assertEquals(new BigDecimal("0"), actualCoerceToBigDecimalResult);
    assertSame(actualCoerceToBigDecimalResult.ZERO, actualCoerceToBigDecimalResult);
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToBigDecimal(Object)}
   */
  @Test
  void testCoerceToBigDecimal4() {
    // Arrange and Act
    BigDecimal actualCoerceToBigDecimalResult = (new TypeConverterImpl()).coerceToBigDecimal(42);

    // Assert
    assertEquals(new BigDecimal("42"), actualCoerceToBigDecimalResult);
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToBigDecimal(Object)}
   */
  @Test
  void testCoerceToBigDecimal5() {
    // Arrange and Act
    BigDecimal actualCoerceToBigDecimalResult = (new TypeConverterImpl()).coerceToBigDecimal("42");

    // Assert
    assertEquals(new BigDecimal("42"), actualCoerceToBigDecimalResult);
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToBigDecimal(Object)}
   */
  @Test
  void testCoerceToBigDecimal6() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    BigDecimal bigDecimal = new BigDecimal("2.3");

    // Act
    BigDecimal actualCoerceToBigDecimalResult = typeConverterImpl.coerceToBigDecimal(bigDecimal);

    // Assert
    assertEquals(new BigDecimal("2.3"), actualCoerceToBigDecimalResult);
    assertSame(bigDecimal, actualCoerceToBigDecimalResult);
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToBigDecimal(Object)}
   */
  @Test
  void testCoerceToBigDecimal7() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();

    // Act
    BigDecimal actualCoerceToBigDecimalResult = typeConverterImpl.coerceToBigDecimal(BigInteger.valueOf(1L));

    // Assert
    assertEquals(new BigDecimal("1"), actualCoerceToBigDecimalResult);
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToBigInteger(Object)}
   */
  @Test
  void testCoerceToBigInteger() {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> (new TypeConverterImpl()).coerceToBigInteger("Value"));
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToBigInteger(Object)}
   */
  @Test
  void testCoerceToBigInteger2() {
    // Arrange and Act
    BigInteger actualCoerceToBigIntegerResult = (new TypeConverterImpl()).coerceToBigInteger(null);

    // Assert
    assertSame(actualCoerceToBigIntegerResult.ZERO, actualCoerceToBigIntegerResult);
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToBigInteger(Object)}
   */
  @Test
  void testCoerceToBigInteger3() {
    // Arrange and Act
    BigInteger actualCoerceToBigIntegerResult = (new TypeConverterImpl()).coerceToBigInteger("");

    // Assert
    assertSame(actualCoerceToBigIntegerResult.ZERO, actualCoerceToBigIntegerResult);
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToBigInteger(Object)}
   */
  @Test
  void testCoerceToBigInteger4() {
    // Arrange and Act
    BigInteger actualCoerceToBigIntegerResult = (new TypeConverterImpl()).coerceToBigInteger(42);

    // Assert
    assertEquals("42", actualCoerceToBigIntegerResult.toString());
    assertEquals(1, actualCoerceToBigIntegerResult.getLowestSetBit());
    assertEquals(1, actualCoerceToBigIntegerResult.signum());
    assertArrayEquals(new byte[]{'*'}, actualCoerceToBigIntegerResult.toByteArray());
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToBigInteger(Object)}
   */
  @Test
  void testCoerceToBigInteger5() {
    // Arrange and Act
    BigInteger actualCoerceToBigIntegerResult = (new TypeConverterImpl()).coerceToBigInteger("42");

    // Assert
    assertEquals("42", actualCoerceToBigIntegerResult.toString());
    assertEquals(1, actualCoerceToBigIntegerResult.getLowestSetBit());
    assertEquals(1, actualCoerceToBigIntegerResult.signum());
    assertArrayEquals(new byte[]{'*'}, actualCoerceToBigIntegerResult.toByteArray());
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToBigInteger(Object)}
   */
  @Test
  void testCoerceToBigInteger6() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();

    // Act
    BigInteger actualCoerceToBigIntegerResult = typeConverterImpl.coerceToBigInteger(BigInteger.valueOf(1L));

    // Assert
    assertSame(actualCoerceToBigIntegerResult.ONE, actualCoerceToBigIntegerResult);
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToBigInteger(Object)}
   */
  @Test
  void testCoerceToBigInteger7() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();

    // Act
    BigInteger actualCoerceToBigIntegerResult = typeConverterImpl.coerceToBigInteger(new BigDecimal("2.3"));

    // Assert
    assertSame(actualCoerceToBigIntegerResult.TWO, actualCoerceToBigIntegerResult);
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToDouble(Object)}
   */
  @Test
  void testCoerceToDouble() {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> (new TypeConverterImpl()).coerceToDouble("Value"));
    assertEquals(0.0d, (new TypeConverterImpl()).coerceToDouble(null).doubleValue());
    assertEquals(0.0d, (new TypeConverterImpl()).coerceToDouble("").doubleValue());
    assertEquals(42.0d, (new TypeConverterImpl()).coerceToDouble(42).doubleValue());
    assertEquals(42.0d, (new TypeConverterImpl()).coerceToDouble("42").doubleValue());
    assertEquals(10.0d, (new TypeConverterImpl()).coerceToDouble(10.0d).doubleValue());
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToFloat(Object)}
   */
  @Test
  void testCoerceToFloat() {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> (new TypeConverterImpl()).coerceToFloat("Value"));
    assertEquals(0.0f, (new TypeConverterImpl()).coerceToFloat(null).floatValue());
    assertEquals(0.0f, (new TypeConverterImpl()).coerceToFloat("").floatValue());
    assertEquals(42.0f, (new TypeConverterImpl()).coerceToFloat(42).floatValue());
    assertEquals(42.0f, (new TypeConverterImpl()).coerceToFloat("42").floatValue());
    assertEquals(10.0f, (new TypeConverterImpl()).coerceToFloat(10.0f).floatValue());
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToLong(Object)}
   */
  @Test
  void testCoerceToLong() {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> (new TypeConverterImpl()).coerceToLong("Value"));
    assertEquals(0L, (new TypeConverterImpl()).coerceToLong(null).longValue());
    assertEquals(0L, (new TypeConverterImpl()).coerceToLong("").longValue());
    assertEquals(42L, (new TypeConverterImpl()).coerceToLong(42L).longValue());
    assertEquals(42L, (new TypeConverterImpl()).coerceToLong(42).longValue());
    assertEquals(42L, (new TypeConverterImpl()).coerceToLong("42").longValue());
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToInteger(Object)}
   */
  @Test
  void testCoerceToInteger() {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> (new TypeConverterImpl()).coerceToInteger("Value"));
    assertEquals(0, (new TypeConverterImpl()).coerceToInteger(null).intValue());
    assertEquals(0, (new TypeConverterImpl()).coerceToInteger("").intValue());
    assertEquals(42, (new TypeConverterImpl()).coerceToInteger(42).intValue());
    assertEquals(42, (new TypeConverterImpl()).coerceToInteger("42").intValue());
    assertEquals(65, (new TypeConverterImpl()).coerceToInteger((byte) 'A').intValue());
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToShort(Object)}
   */
  @Test
  void testCoerceToShort() {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> (new TypeConverterImpl()).coerceToShort("Value"));
    assertEquals((short) 0, (new TypeConverterImpl()).coerceToShort(null).shortValue());
    assertEquals((short) 0, (new TypeConverterImpl()).coerceToShort("").shortValue());
    assertEquals((short) 42, (new TypeConverterImpl()).coerceToShort(42).shortValue());
    assertEquals((short) 42, (new TypeConverterImpl()).coerceToShort("42").shortValue());
    assertEquals((short) 1, (new TypeConverterImpl()).coerceToShort((short) 1).shortValue());
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToByte(Object)}
   */
  @Test
  void testCoerceToByte() {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> (new TypeConverterImpl()).coerceToByte("Value"));
    assertEquals((byte) 0, (new TypeConverterImpl()).coerceToByte(null).byteValue());
    assertEquals((byte) 0, (new TypeConverterImpl()).coerceToByte("").byteValue());
    assertEquals('A', (new TypeConverterImpl()).coerceToByte((byte) 'A').byteValue());
    assertEquals('*', (new TypeConverterImpl()).coerceToByte(42).byteValue());
    assertEquals('*', (new TypeConverterImpl()).coerceToByte("42").byteValue());
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToString(Object)}
   */
  @Test
  void testCoerceToString() {
    // Arrange, Act and Assert
    assertEquals("Value", (new TypeConverterImpl()).coerceToString("Value"));
    assertEquals("", (new TypeConverterImpl()).coerceToString(null));
    assertEquals("42", (new TypeConverterImpl()).coerceToString(42));
    assertEquals("COMMON", (new TypeConverterImpl()).coerceToString(Character.UnicodeScript.COMMON));
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToEnum(Object, Class)}
   */
  @Test
  void testCoerceToEnum() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Component.BaselineResizeBehavior> type = Component.BaselineResizeBehavior.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.coerceToEnum("Value", type));
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToEnum(Object, Class)}
   */
  @Test
  void testCoerceToEnum2() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Component.BaselineResizeBehavior> type = Component.BaselineResizeBehavior.class;

    // Act and Assert
    assertNull(typeConverterImpl.coerceToEnum(null, type));
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToEnum(Object, Class)}
   */
  @Test
  void testCoerceToEnum3() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Component.BaselineResizeBehavior> type = Component.BaselineResizeBehavior.class;

    // Act and Assert
    assertNull(typeConverterImpl.coerceToEnum("", type));
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToEnum(Object, Class)}
   */
  @Test
  void testCoerceToEnum4() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Component.BaselineResizeBehavior> type = Component.BaselineResizeBehavior.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.coerceToEnum(42, type));
  }

  /**
   * Method under test:
   * {@link TypeConverterImpl#coerceStringToType(String, Class)}
   */
  @Test
  void testCoerceStringToType() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> type = Object.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.coerceStringToType("42", type));
  }

  /**
   * Method under test:
   * {@link TypeConverterImpl#coerceStringToType(String, Class)}
   */
  @Test
  void testCoerceStringToType2() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> type = Object.class;

    // Act and Assert
    assertNull(typeConverterImpl.coerceStringToType("", type));
  }

  /**
   * Method under test:
   * {@link TypeConverterImpl#coerceStringToType(String, Class)}
   */
  @Test
  void testCoerceStringToType3() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Boolean> type = Boolean.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.coerceStringToType("42", type));
  }

  /**
   * Method under test:
   * {@link TypeConverterImpl#coerceStringToType(String, Class)}
   */
  @Test
  void testCoerceStringToType4() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Boolean> type = Boolean.class;

    // Act and Assert
    assertNull(typeConverterImpl.coerceStringToType("", type));
  }

  /**
   * Method under test:
   * {@link TypeConverterImpl#coerceStringToType(String, Class)}
   */
  @Test
  void testCoerceStringToType5() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<String> type = String.class;

    // Act and Assert
    assertEquals("", typeConverterImpl.coerceStringToType("", type));
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  void testCoerceToType() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> type = Object.class;

    // Act and Assert
    assertEquals("Value", typeConverterImpl.coerceToType("Value", type));
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  void testCoerceToType2() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> type = Object.class;

    // Act and Assert
    assertNull(typeConverterImpl.coerceToType(null, type));
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  void testCoerceToType3() {
    // Arrange, Act and Assert
    assertEquals(0.0d, ((Double) (new TypeConverterImpl()).coerceToType(null, Double.TYPE)).doubleValue());
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  void testCoerceToType4() {
    // Arrange, Act and Assert
    assertEquals(0.0f, ((Float) (new TypeConverterImpl()).coerceToType(null, Float.TYPE)).floatValue());
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  void testCoerceToType5() {
    // Arrange, Act and Assert
    assertEquals(0.0d, ((Double) (new TypeConverterImpl()).coerceToType("", Double.TYPE)).doubleValue());
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  void testCoerceToType6() {
    // Arrange, Act and Assert
    assertEquals(0.0f, ((Float) (new TypeConverterImpl()).coerceToType("", Float.TYPE)).floatValue());
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  void testCoerceToType7() {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> (new TypeConverterImpl()).coerceToType("Value", Long.TYPE));
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  void testCoerceToType8() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Byte> type = Byte.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.coerceToType("Value", type));
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  void testCoerceToType9() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Boolean> type = Boolean.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.coerceToType(42, type));
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  void testCoerceToType10() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Byte> type = Byte.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.coerceToType(true, type));
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  void testCoerceToType11() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Character> type = Character.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.coerceToType(true, type));
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  void testCoerceToType12() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.coerceToType(forNameResult, Long.TYPE));
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  void testCoerceToType13() {
    // Arrange, Act and Assert
    assertEquals(42.0d, ((Double) (new TypeConverterImpl()).coerceToType(42, Double.TYPE)).doubleValue());
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  void testCoerceToType14() {
    // Arrange, Act and Assert
    assertEquals(42.0d, ((Double) (new TypeConverterImpl()).coerceToType("42", Double.TYPE)).doubleValue());
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  void testCoerceToType15() {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> (new TypeConverterImpl()).coerceToType("Value", Double.TYPE));
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  void testCoerceToType16() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.coerceToType(forNameResult, Double.TYPE));
  }

  /**
   * Method under test: {@link TypeConverterImpl#coerceToType(Object, Class)}
   */
  @Test
  void testCoerceToType17() {
    // Arrange, Act and Assert
    assertEquals(10.0d, ((Double) (new TypeConverterImpl()).coerceToType(10.0d, Double.TYPE)).doubleValue());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link TypeConverterImpl#equals(Object)}
   *   <li>{@link TypeConverterImpl#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link TypeConverterImpl#equals(Object)}
   *   <li>{@link TypeConverterImpl#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();

    // Act and Assert
    assertEquals(typeConverterImpl, typeConverterImpl);
    int expectedHashCodeResult = typeConverterImpl.hashCode();
    assertEquals(expectedHashCodeResult, typeConverterImpl.hashCode());
  }

  /**
   * Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  void testConvert() throws ELException {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> type = Object.class;

    // Act and Assert
    assertEquals("Value", typeConverterImpl.convert("Value", type));
  }

  /**
   * Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  void testConvert2() throws ELException {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> type = Object.class;

    // Act and Assert
    assertNull(typeConverterImpl.convert(null, type));
  }

  /**
   * Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  void testConvert3() throws ELException {
    // Arrange, Act and Assert
    assertEquals(0.0d, ((Double) (new TypeConverterImpl()).convert(null, Double.TYPE)).doubleValue());
  }

  /**
   * Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  void testConvert4() throws ELException {
    // Arrange, Act and Assert
    assertEquals(0.0f, ((Float) (new TypeConverterImpl()).convert(null, Float.TYPE)).floatValue());
  }

  /**
   * Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  void testConvert5() throws ELException {
    // Arrange, Act and Assert
    assertEquals(0.0d, ((Double) (new TypeConverterImpl()).convert("", Double.TYPE)).doubleValue());
  }

  /**
   * Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  void testConvert6() throws ELException {
    // Arrange, Act and Assert
    assertEquals(0.0f, ((Float) (new TypeConverterImpl()).convert("", Float.TYPE)).floatValue());
  }

  /**
   * Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  void testConvert7() throws ELException {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> (new TypeConverterImpl()).convert("Value", Long.TYPE));
  }

  /**
   * Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  void testConvert8() throws ELException {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Byte> type = Byte.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.convert("Value", type));
  }

  /**
   * Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  void testConvert9() throws ELException {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Boolean> type = Boolean.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.convert(42, type));
  }

  /**
   * Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  void testConvert10() throws ELException {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Byte> type = Byte.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.convert(true, type));
  }

  /**
   * Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  void testConvert11() throws ELException {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Character> type = Character.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.convert(true, type));
  }

  /**
   * Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  void testConvert12() throws ELException {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.convert(forNameResult, Long.TYPE));
  }

  /**
   * Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  void testConvert13() throws ELException {
    // Arrange, Act and Assert
    assertEquals(42.0d, ((Double) (new TypeConverterImpl()).convert(42, Double.TYPE)).doubleValue());
  }

  /**
   * Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  void testConvert14() throws ELException {
    // Arrange, Act and Assert
    assertEquals(42.0d, ((Double) (new TypeConverterImpl()).convert("42", Double.TYPE)).doubleValue());
  }

  /**
   * Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  void testConvert15() throws ELException {
    // Arrange, Act and Assert
    assertThrows(ELException.class, () -> (new TypeConverterImpl()).convert("Value", Double.TYPE));
  }

  /**
   * Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  void testConvert16() throws ELException {
    // Arrange
    TypeConverterImpl typeConverterImpl = new TypeConverterImpl();
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(ELException.class, () -> typeConverterImpl.convert(forNameResult, Double.TYPE));
  }

  /**
   * Method under test: {@link TypeConverterImpl#convert(Object, Class)}
   */
  @Test
  void testConvert17() throws ELException {
    // Arrange, Act and Assert
    assertEquals(10.0d, ((Double) (new TypeConverterImpl()).convert(10.0d, Double.TYPE)).doubleValue());
  }

  /**
   * Method under test: {@link TypeConverterImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new TypeConverterImpl(), 1);
  }

  /**
   * Method under test: {@link TypeConverterImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new TypeConverterImpl(), null);
  }

  /**
   * Method under test: {@link TypeConverterImpl#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new TypeConverterImpl(), "Different type to TypeConverterImpl");
  }
}
