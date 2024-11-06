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
package org.activiti.core.el.juel.tree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import jakarta.el.ValueExpression;
import java.lang.reflect.Method;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BindingsDiffblueTest {
  /**
   * Test {@link Bindings#Bindings(Method[], ValueExpression[])}.
   * <ul>
   *   <li>When array of {@link Method} with {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Bindings#Bindings(Method[], ValueExpression[])}
   */
  @Test
  @DisplayName("Test new Bindings(Method[], ValueExpression[]); when array of Method with 'null'")
  void testNewBindings_whenArrayOfMethodWithNull() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    Bindings actualBindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Assert
    assertFalse(actualBindings.isFunctionBound(1));
    assertFalse(actualBindings.isVariableBound(1));
  }

  /**
   * Test {@link Bindings#Bindings(Method[], ValueExpression[], TypeConverter)}.
   * <ul>
   *   <li>When array of {@link Method} with {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link Bindings#Bindings(Method[], ValueExpression[], TypeConverter)}
   */
  @Test
  @DisplayName("Test new Bindings(Method[], ValueExpression[], TypeConverter); when array of Method with 'null'")
  void testNewBindings_whenArrayOfMethodWithNull2() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    Bindings actualBindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}, mock(TypeConverter.class));

    // Assert
    assertFalse(actualBindings.isFunctionBound(1));
    assertFalse(actualBindings.isVariableBound(1));
  }

  /**
   * Test {@link Bindings#Bindings(Method[], ValueExpression[])}.
   * <ul>
   *   <li>When empty array of {@link Method}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Bindings#Bindings(Method[], ValueExpression[])}
   */
  @Test
  @DisplayName("Test new Bindings(Method[], ValueExpression[]); when empty array of Method")
  void testNewBindings_whenEmptyArrayOfMethod() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    Bindings actualBindings = new Bindings(new Method[]{},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Assert
    assertFalse(actualBindings.isFunctionBound(1));
    assertFalse(actualBindings.isVariableBound(1));
  }

  /**
   * Test {@link Bindings#Bindings(Method[], ValueExpression[], TypeConverter)}.
   * <ul>
   *   <li>When empty array of {@link Method}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link Bindings#Bindings(Method[], ValueExpression[], TypeConverter)}
   */
  @Test
  @DisplayName("Test new Bindings(Method[], ValueExpression[], TypeConverter); when empty array of Method")
  void testNewBindings_whenEmptyArrayOfMethod2() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    Bindings actualBindings = new Bindings(new Method[]{},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}, mock(TypeConverter.class));

    // Assert
    assertFalse(actualBindings.isFunctionBound(1));
    assertFalse(actualBindings.isVariableBound(1));
  }

  /**
   * Test {@link Bindings#Bindings(Method[], ValueExpression[])}.
   * <ul>
   *   <li>When empty array of {@link ValueExpression}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Bindings#Bindings(Method[], ValueExpression[])}
   */
  @Test
  @DisplayName("Test new Bindings(Method[], ValueExpression[]); when empty array of ValueExpression")
  void testNewBindings_whenEmptyArrayOfValueExpression() {
    // Arrange and Act
    Bindings actualBindings = new Bindings(new Method[]{null}, new ValueExpression[]{});

    // Assert
    assertFalse(actualBindings.isFunctionBound(1));
    assertFalse(actualBindings.isVariableBound(1));
  }

  /**
   * Test {@link Bindings#Bindings(Method[], ValueExpression[], TypeConverter)}.
   * <ul>
   *   <li>When empty array of {@link ValueExpression}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link Bindings#Bindings(Method[], ValueExpression[], TypeConverter)}
   */
  @Test
  @DisplayName("Test new Bindings(Method[], ValueExpression[], TypeConverter); when empty array of ValueExpression")
  void testNewBindings_whenEmptyArrayOfValueExpression2() {
    // Arrange and Act
    Bindings actualBindings = new Bindings(new Method[]{null}, new ValueExpression[]{}, mock(TypeConverter.class));

    // Assert
    assertFalse(actualBindings.isFunctionBound(1));
    assertFalse(actualBindings.isVariableBound(1));
  }

  /**
   * Test {@link Bindings#Bindings(Method[], ValueExpression[])}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Bindings#Bindings(Method[], ValueExpression[])}
   */
  @Test
  @DisplayName("Test new Bindings(Method[], ValueExpression[]); when 'null'")
  void testNewBindings_whenNull() {
    // Arrange and Act
    Bindings actualBindings = new Bindings(null, null);

    // Assert
    assertFalse(actualBindings.isFunctionBound(1));
    assertFalse(actualBindings.isVariableBound(1));
  }

  /**
   * Test {@link Bindings#Bindings(Method[], ValueExpression[], TypeConverter)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link Bindings#Bindings(Method[], ValueExpression[], TypeConverter)}
   */
  @Test
  @DisplayName("Test new Bindings(Method[], ValueExpression[], TypeConverter); when 'null'")
  void testNewBindings_whenNull2() {
    // Arrange and Act
    Bindings actualBindings = new Bindings(null, null, null);

    // Assert
    assertFalse(actualBindings.isFunctionBound(1));
    assertFalse(actualBindings.isVariableBound(1));
  }

  /**
   * Test {@link Bindings#getFunction(int)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Bindings#getFunction(int)}
   */
  @Test
  @DisplayName("Test getFunction(int); then return 'null'")
  void testGetFunction_thenReturnNull() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act and Assert
    assertNull((new Bindings(new Method[]{null, null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)})).getFunction(1));
  }

  /**
   * Test {@link Bindings#isFunctionBound(int)}.
   * <ul>
   *   <li>When minus one.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Bindings#isFunctionBound(int)}
   */
  @Test
  @DisplayName("Test isFunctionBound(int); when minus one; then return 'false'")
  void testIsFunctionBound_whenMinusOne_thenReturnFalse() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act and Assert
    assertFalse(
        (new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}))
            .isFunctionBound(-1));
  }

  /**
   * Test {@link Bindings#isFunctionBound(int)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Bindings#isFunctionBound(int)}
   */
  @Test
  @DisplayName("Test isFunctionBound(int); when one; then return 'false'")
  void testIsFunctionBound_whenOne_thenReturnFalse() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act and Assert
    assertFalse(
        (new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}))
            .isFunctionBound(1));
  }

  /**
   * Test {@link Bindings#isFunctionBound(int)}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Bindings#isFunctionBound(int)}
   */
  @Test
  @DisplayName("Test isFunctionBound(int); when zero; then return 'true'")
  void testIsFunctionBound_whenZero_thenReturnTrue() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act and Assert
    assertTrue(
        (new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}))
            .isFunctionBound(0));
  }

  /**
   * Test {@link Bindings#getVariable(int)}.
   * <p>
   * Method under test: {@link Bindings#getVariable(int)}
   */
  @Test
  @DisplayName("Test getVariable(int)")
  void testGetVariable() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    ObjectValueExpression objectValueExpression = new ObjectValueExpression(converter, "Object", type);

    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type2 = Object.class;
    ObjectValueExpression objectValueExpression2 = new ObjectValueExpression(converter2, "Object", type2);

    // Act and Assert
    assertSame(objectValueExpression2,
        (new Bindings(new Method[]{null}, new ValueExpression[]{objectValueExpression, objectValueExpression2}))
            .getVariable(1));
  }

  /**
   * Test {@link Bindings#isVariableBound(int)}.
   * <p>
   * Method under test: {@link Bindings#isVariableBound(int)}
   */
  @Test
  @DisplayName("Test isVariableBound(int)")
  void testIsVariableBound() {
    // Arrange, Act and Assert
    assertFalse((new Bindings(new Method[]{null}, new ValueExpression[]{null})).isVariableBound(0));
  }

  /**
   * Test {@link Bindings#isVariableBound(int)}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>When minus one.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Bindings#isVariableBound(int)}
   */
  @Test
  @DisplayName("Test isVariableBound(int); given 'java.lang.Object'; when minus one; then return 'false'")
  void testIsVariableBound_givenJavaLangObject_whenMinusOne_thenReturnFalse() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act and Assert
    assertFalse(
        (new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}))
            .isVariableBound(-1));
  }

  /**
   * Test {@link Bindings#isVariableBound(int)}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>When one.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Bindings#isVariableBound(int)}
   */
  @Test
  @DisplayName("Test isVariableBound(int); given 'java.lang.Object'; when one; then return 'false'")
  void testIsVariableBound_givenJavaLangObject_whenOne_thenReturnFalse() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act and Assert
    assertFalse(
        (new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}))
            .isVariableBound(1));
  }

  /**
   * Test {@link Bindings#isVariableBound(int)}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>When zero.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Bindings#isVariableBound(int)}
   */
  @Test
  @DisplayName("Test isVariableBound(int); given 'java.lang.Object'; when zero; then return 'true'")
  void testIsVariableBound_givenJavaLangObject_whenZero_thenReturnTrue() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act and Assert
    assertTrue(
        (new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}))
            .isVariableBound(0));
  }

  /**
   * Test {@link Bindings#convert(Object, Class)}.
   * <ul>
   *   <li>When {@code java.lang.Object}.</li>
   *   <li>Then return {@code Value}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Bindings#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when 'java.lang.Object'; then return 'Value'")
  void testConvert_whenJavaLangObject_thenReturnValue() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});
    Class<Object> type2 = Object.class;

    // Act and Assert
    assertEquals("Value", bindings.convert("Value", type2));
  }

  /**
   * Test {@link Bindings#equals(Object)}, and {@link Bindings#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Bindings#equals(Object)}
   *   <li>{@link Bindings#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type2 = Object.class;
    Bindings bindings2 = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter2, "Object", type2)});

    // Act and Assert
    assertEquals(bindings, bindings2);
    int expectedHashCodeResult = bindings.hashCode();
    assertEquals(expectedHashCodeResult, bindings2.hashCode());
  }

  /**
   * Test {@link Bindings#equals(Object)}, and {@link Bindings#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Bindings#equals(Object)}
   *   <li>{@link Bindings#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertEquals(bindings, bindings);
    int expectedHashCodeResult = bindings.hashCode();
    assertEquals(expectedHashCodeResult, bindings.hashCode());
  }

  /**
   * Test {@link Bindings#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Bindings#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type2 = Object.class;

    // Act and Assert
    assertNotEquals(bindings, new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter2, "Object", type2)}));
  }

  /**
   * Test {@link Bindings#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Bindings#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, 1, type)});
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type2 = Object.class;

    // Act and Assert
    assertNotEquals(bindings, new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter2, "Object", type2)}));
  }

  /**
   * Test {@link Bindings#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Bindings#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter2, "Object", type)});

    Class<Object> type2 = Object.class;
    Bindings bindings2 = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, bindings, type2)});
    TypeConverter converter3 = mock(TypeConverter.class);
    Class<Object> type3 = Object.class;

    // Act and Assert
    assertNotEquals(bindings2, new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter3, "Object", type3)}));
  }

  /**
   * Test {@link Bindings#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Bindings#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}, mock(TypeConverter.class));
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type2 = Object.class;

    // Act and Assert
    assertNotEquals(bindings, new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter2, "Object", type2)}));
  }

  /**
   * Test {@link Bindings#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Bindings#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type2 = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}, new Bindings(new Method[]{null},
            new ValueExpression[]{new ObjectValueExpression(converter2, "Object", type2)}));
    TypeConverter converter3 = mock(TypeConverter.class);
    Class<Object> type3 = Object.class;

    // Act and Assert
    assertNotEquals(bindings, new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter3, "Object", type3)}));
  }

  /**
   * Test {@link Bindings#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Bindings#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act and Assert
    assertNotEquals(
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}),
        null);
  }

  /**
   * Test {@link Bindings#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Bindings#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act and Assert
    assertNotEquals(
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}),
        "Different type to Bindings");
  }
}
