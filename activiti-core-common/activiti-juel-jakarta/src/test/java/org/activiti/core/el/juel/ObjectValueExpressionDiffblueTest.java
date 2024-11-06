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
package org.activiti.core.el.juel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import jakarta.el.ELContext;
import jakarta.el.ELException;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.core.el.juel.util.SimpleContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ObjectValueExpressionDiffblueTest {
  /**
   * Test
   * {@link ObjectValueExpression#ObjectValueExpression(TypeConverter, Object, Class)}.
   * <ul>
   *   <li>When {@code java.lang.Object}.</li>
   *   <li>Then return ExpressionString is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ObjectValueExpression#ObjectValueExpression(TypeConverter, Object, Class)}
   */
  @Test
  @DisplayName("Test new ObjectValueExpression(TypeConverter, Object, Class); when 'java.lang.Object'; then return ExpressionString is 'null'")
  void testNewObjectValueExpression_whenJavaLangObject_thenReturnExpressionStringIsNull() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    ObjectValueExpression actualObjectValueExpression = new ObjectValueExpression(converter, "Object", type);

    // Assert
    assertNull(actualObjectValueExpression.getExpressionString());
    assertFalse(actualObjectValueExpression.isLiteralText());
    Class<Object> expectedExpectedType = Object.class;
    assertEquals(expectedExpectedType, actualObjectValueExpression.getExpectedType());
  }

  /**
   * Test {@link ObjectValueExpression#equals(Object)}, and
   * {@link ObjectValueExpression#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ObjectValueExpression#equals(Object)}
   *   <li>{@link ObjectValueExpression#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    ObjectValueExpression objectValueExpression = new ObjectValueExpression(converter, "Object", type);
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type2 = Object.class;
    ObjectValueExpression objectValueExpression2 = new ObjectValueExpression(converter2, "Object", type2);

    // Act and Assert
    assertEquals(objectValueExpression, objectValueExpression2);
    int expectedHashCodeResult = objectValueExpression.hashCode();
    assertEquals(expectedHashCodeResult, objectValueExpression2.hashCode());
  }

  /**
   * Test {@link ObjectValueExpression#equals(Object)}, and
   * {@link ObjectValueExpression#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ObjectValueExpression#equals(Object)}
   *   <li>{@link ObjectValueExpression#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    ObjectValueExpression objectValueExpression = new ObjectValueExpression(converter2, "Object", type);

    Class<Object> type2 = Object.class;
    ObjectValueExpression objectValueExpression2 = new ObjectValueExpression(converter, objectValueExpression, type2);
    TypeConverter converter3 = mock(TypeConverter.class);
    TypeConverter converter4 = mock(TypeConverter.class);
    Class<Object> type3 = Object.class;
    ObjectValueExpression objectValueExpression3 = new ObjectValueExpression(converter4, "Object", type3);

    Class<Object> type4 = Object.class;
    ObjectValueExpression objectValueExpression4 = new ObjectValueExpression(converter3, objectValueExpression3, type4);

    // Act and Assert
    assertEquals(objectValueExpression2, objectValueExpression4);
    int expectedHashCodeResult = objectValueExpression2.hashCode();
    assertEquals(expectedHashCodeResult, objectValueExpression4.hashCode());
  }

  /**
   * Test {@link ObjectValueExpression#equals(Object)}, and
   * {@link ObjectValueExpression#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ObjectValueExpression#equals(Object)}
   *   <li>{@link ObjectValueExpression#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    ObjectValueExpression objectValueExpression = new ObjectValueExpression(converter, "Object", type);

    // Act and Assert
    assertEquals(objectValueExpression, objectValueExpression);
    int expectedHashCodeResult = objectValueExpression.hashCode();
    assertEquals(expectedHashCodeResult, objectValueExpression.hashCode());
  }

  /**
   * Test {@link ObjectValueExpression#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ObjectValueExpression#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    ObjectValueExpression objectValueExpression = new ObjectValueExpression(converter, 1, type);
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type2 = Object.class;

    // Act and Assert
    assertNotEquals(objectValueExpression, new ObjectValueExpression(converter2, "Object", type2));
  }

  /**
   * Test {@link ObjectValueExpression#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ObjectValueExpression#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    ObjectValueExpression objectValueExpression = new ObjectValueExpression(converter2, "Object", type);

    Class<Object> type2 = Object.class;
    ObjectValueExpression objectValueExpression2 = new ObjectValueExpression(converter, objectValueExpression, type2);
    TypeConverter converter3 = mock(TypeConverter.class);
    Class<Object> type3 = Object.class;

    // Act and Assert
    assertNotEquals(objectValueExpression2, new ObjectValueExpression(converter3, "Object", type3));
  }

  /**
   * Test {@link ObjectValueExpression#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ObjectValueExpression#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    ObjectValueExpression objectValueExpression = new ObjectValueExpression(converter, null, type);
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type2 = Object.class;

    // Act and Assert
    assertNotEquals(objectValueExpression, new ObjectValueExpression(converter2, "Object", type2));
  }

  /**
   * Test {@link ObjectValueExpression#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ObjectValueExpression#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<ObjectValueExpression> type = ObjectValueExpression.class;
    ObjectValueExpression objectValueExpression = new ObjectValueExpression(converter, "Object", type);
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type2 = Object.class;

    // Act and Assert
    assertNotEquals(objectValueExpression, new ObjectValueExpression(converter2, "Object", type2));
  }

  /**
   * Test {@link ObjectValueExpression#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ObjectValueExpression#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act and Assert
    assertNotEquals(new ObjectValueExpression(converter, "Object", type), null);
  }

  /**
   * Test {@link ObjectValueExpression#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ObjectValueExpression#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act and Assert
    assertNotEquals(new ObjectValueExpression(converter, "Object", type), "Different type to ObjectValueExpression");
  }

  /**
   * Test {@link ObjectValueExpression#getValue(ELContext)}.
   * <ul>
   *   <li>Given {@link TypeConverter} {@link TypeConverter#convert(Object, Class)}
   * return {@code Convert}.</li>
   *   <li>Then return {@code Convert}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ObjectValueExpression#getValue(ELContext)}
   */
  @Test
  @DisplayName("Test getValue(ELContext); given TypeConverter convert(Object, Class) return 'Convert'; then return 'Convert'")
  void testGetValue_givenTypeConverterConvertReturnConvert_thenReturnConvert() throws ELException {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn("Convert");
    Class<Object> type = Object.class;
    ObjectValueExpression objectValueExpression = new ObjectValueExpression(converter, "Object", type);

    // Act
    Object actualValue = objectValueExpression.getValue(new SimpleContext());

    // Assert
    verify(converter).convert(isA(Object.class), isA(Class.class));
    assertEquals("Convert", actualValue);
  }

  /**
   * Test {@link ObjectValueExpression#getType(ELContext)}.
   * <p>
   * Method under test: {@link ObjectValueExpression#getType(ELContext)}
   */
  @Test
  @DisplayName("Test getType(ELContext)")
  void testGetType() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    ObjectValueExpression objectValueExpression = new ObjectValueExpression(converter, "Object", type);

    // Act and Assert
    assertNull(objectValueExpression.getType(new SimpleContext()));
  }

  /**
   * Test {@link ObjectValueExpression#isReadOnly(ELContext)}.
   * <p>
   * Method under test: {@link ObjectValueExpression#isReadOnly(ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(ELContext)")
  void testIsReadOnly() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    ObjectValueExpression objectValueExpression = new ObjectValueExpression(converter, "Object", type);

    // Act and Assert
    assertTrue(objectValueExpression.isReadOnly(new SimpleContext()));
  }

  /**
   * Test {@link ObjectValueExpression#setValue(ELContext, Object)}.
   * <p>
   * Method under test: {@link ObjectValueExpression#setValue(ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(ELContext, Object)")
  void testSetValue() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    ObjectValueExpression objectValueExpression = new ObjectValueExpression(converter, "Object", type);

    // Act and Assert
    assertThrows(ELException.class, () -> objectValueExpression.setValue(new SimpleContext(), "Value"));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ObjectValueExpression#toString()}
   *   <li>{@link ObjectValueExpression#getExpectedType()}
   *   <li>{@link ObjectValueExpression#getExpressionString()}
   *   <li>{@link ObjectValueExpression#isLiteralText()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    ObjectValueExpression objectValueExpression = new ObjectValueExpression(converter, "Object", type);

    // Act
    String actualToStringResult = objectValueExpression.toString();
    Class<?> actualExpectedType = objectValueExpression.getExpectedType();
    String actualExpressionString = objectValueExpression.getExpressionString();

    // Assert
    assertEquals("ValueExpression(Object)", actualToStringResult);
    assertNull(actualExpressionString);
    assertFalse(objectValueExpression.isLiteralText());
    Class<Object> expectedExpectedType = Object.class;
    assertEquals(expectedExpectedType, actualExpectedType);
    assertSame(type, actualExpectedType);
  }
}
