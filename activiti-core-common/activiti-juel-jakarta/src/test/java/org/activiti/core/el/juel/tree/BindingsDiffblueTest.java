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
import org.junit.jupiter.api.Test;

class BindingsDiffblueTest {
  /**
   * Method under test: {@link Bindings#getFunction(int)}
   */
  @Test
  void testGetFunction() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act and Assert
    assertNull((new Bindings(new Method[]{null, null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)})).getFunction(1));
  }

  /**
   * Method under test: {@link Bindings#isFunctionBound(int)}
   */
  @Test
  void testIsFunctionBound() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act and Assert
    assertFalse(
        (new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}))
            .isFunctionBound(1));
  }

  /**
   * Method under test: {@link Bindings#isFunctionBound(int)}
   */
  @Test
  void testIsFunctionBound2() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act and Assert
    assertTrue(
        (new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}))
            .isFunctionBound(0));
  }

  /**
   * Method under test: {@link Bindings#isFunctionBound(int)}
   */
  @Test
  void testIsFunctionBound3() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act and Assert
    assertFalse(
        (new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}))
            .isFunctionBound(-1));
  }

  /**
   * Method under test: {@link Bindings#getVariable(int)}
   */
  @Test
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
   * Method under test: {@link Bindings#isVariableBound(int)}
   */
  @Test
  void testIsVariableBound() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act and Assert
    assertFalse(
        (new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}))
            .isVariableBound(1));
  }

  /**
   * Method under test: {@link Bindings#isVariableBound(int)}
   */
  @Test
  void testIsVariableBound2() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act and Assert
    assertTrue(
        (new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}))
            .isVariableBound(0));
  }

  /**
   * Method under test: {@link Bindings#isVariableBound(int)}
   */
  @Test
  void testIsVariableBound3() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act and Assert
    assertFalse(
        (new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}))
            .isVariableBound(-1));
  }

  /**
   * Method under test: {@link Bindings#isVariableBound(int)}
   */
  @Test
  void testIsVariableBound4() {
    // Arrange, Act and Assert
    assertFalse((new Bindings(new Method[]{null}, new ValueExpression[]{null})).isVariableBound(0));
  }

  /**
   * Method under test: {@link Bindings#convert(Object, Class)}
   */
  @Test
  void testConvert() {
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
   * Methods under test:
   * <ul>
   *   <li>{@link Bindings#equals(Object)}
   *   <li>{@link Bindings#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link Bindings#equals(Object)}
   *   <li>{@link Bindings#hashCode()}
   * </ul>
   */
  @Test
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
   * Method under test: {@link Bindings#equals(Object)}
   */
  @Test
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
   * Method under test: {@link Bindings#equals(Object)}
   */
  @Test
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
   * Method under test: {@link Bindings#equals(Object)}
   */
  @Test
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
   * Method under test: {@link Bindings#equals(Object)}
   */
  @Test
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
   * Method under test: {@link Bindings#equals(Object)}
   */
  @Test
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
   * Method under test: {@link Bindings#equals(Object)}
   */
  @Test
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
   * Method under test: {@link Bindings#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act and Assert
    assertNotEquals(
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}),
        "Different type to Bindings");
  }

  /**
   * Method under test: {@link Bindings#Bindings(Method[], ValueExpression[])}
   */
  @Test
  void testNewBindings() {
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
   * Method under test: {@link Bindings#Bindings(Method[], ValueExpression[])}
   */
  @Test
  void testNewBindings2() {
    // Arrange and Act
    Bindings actualBindings = new Bindings(null, null);

    // Assert
    assertFalse(actualBindings.isFunctionBound(1));
    assertFalse(actualBindings.isVariableBound(1));
  }

  /**
   * Method under test: {@link Bindings#Bindings(Method[], ValueExpression[])}
   */
  @Test
  void testNewBindings3() {
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
   * Method under test: {@link Bindings#Bindings(Method[], ValueExpression[])}
   */
  @Test
  void testNewBindings4() {
    // Arrange and Act
    Bindings actualBindings = new Bindings(new Method[]{null}, new ValueExpression[]{});

    // Assert
    assertFalse(actualBindings.isFunctionBound(1));
    assertFalse(actualBindings.isVariableBound(1));
  }

  /**
   * Method under test:
   * {@link Bindings#Bindings(Method[], ValueExpression[], TypeConverter)}
   */
  @Test
  void testNewBindings5() {
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
   * Method under test:
   * {@link Bindings#Bindings(Method[], ValueExpression[], TypeConverter)}
   */
  @Test
  void testNewBindings6() {
    // Arrange and Act
    Bindings actualBindings = new Bindings(null, null, null);

    // Assert
    assertFalse(actualBindings.isFunctionBound(1));
    assertFalse(actualBindings.isVariableBound(1));
  }

  /**
   * Method under test:
   * {@link Bindings#Bindings(Method[], ValueExpression[], TypeConverter)}
   */
  @Test
  void testNewBindings7() {
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
   * Method under test:
   * {@link Bindings#Bindings(Method[], ValueExpression[], TypeConverter)}
   */
  @Test
  void testNewBindings8() {
    // Arrange and Act
    Bindings actualBindings = new Bindings(new Method[]{null}, new ValueExpression[]{}, mock(TypeConverter.class));

    // Assert
    assertFalse(actualBindings.isFunctionBound(1));
    assertFalse(actualBindings.isVariableBound(1));
  }
}
