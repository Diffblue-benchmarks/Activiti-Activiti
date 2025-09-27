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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.el.ValueExpression;
import java.lang.reflect.Method;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class BindingsDiffblueTest {
  /**
   * Test {@link Bindings#Bindings(Method[], ValueExpression[])}.
   *
   * <ul>
   *   <li>When array of {@link Method} with {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Bindings#Bindings(Method[], ValueExpression[])}
   */
  @Test
  @DisplayName("Test new Bindings(Method[], ValueExpression[]); when array of Method with 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void Bindings.<init>(Method[], ValueExpression[])"})
  void testNewBindings_whenArrayOfMethodWithNull() {
    // Arrange
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    // Act
    Bindings actualBindings = new Bindings(functions, variables);

    // Assert
    assertFalse(actualBindings.isFunctionBound(1));
    assertFalse(actualBindings.isVariableBound(1));
  }

  /**
   * Test {@link Bindings#Bindings(Method[], ValueExpression[], TypeConverter)}.
   *
   * <ul>
   *   <li>When array of {@link Method} with {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Bindings#Bindings(Method[], ValueExpression[], TypeConverter)}
   */
  @Test
  @DisplayName(
      "Test new Bindings(Method[], ValueExpression[], TypeConverter); when array of Method with 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void Bindings.<init>(Method[], ValueExpression[], TypeConverter)"})
  void testNewBindings_whenArrayOfMethodWithNull2() {
    // Arrange
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    // Act
    Bindings actualBindings = new Bindings(functions, variables, mock(TypeConverter.class));

    // Assert
    assertFalse(actualBindings.isFunctionBound(1));
    assertFalse(actualBindings.isVariableBound(1));
  }

  /**
   * Test {@link Bindings#Bindings(Method[], ValueExpression[])}.
   *
   * <ul>
   *   <li>When empty array of {@link Method}.
   * </ul>
   *
   * <p>Method under test: {@link Bindings#Bindings(Method[], ValueExpression[])}
   */
  @Test
  @DisplayName("Test new Bindings(Method[], ValueExpression[]); when empty array of Method")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void Bindings.<init>(Method[], ValueExpression[])"})
  void testNewBindings_whenEmptyArrayOfMethod() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    // Act
    Bindings actualBindings = new Bindings(new Method[] {}, variables);

    // Assert
    assertFalse(actualBindings.isFunctionBound(1));
    assertFalse(actualBindings.isVariableBound(1));
  }

  /**
   * Test {@link Bindings#Bindings(Method[], ValueExpression[], TypeConverter)}.
   *
   * <ul>
   *   <li>When empty array of {@link Method}.
   * </ul>
   *
   * <p>Method under test: {@link Bindings#Bindings(Method[], ValueExpression[], TypeConverter)}
   */
  @Test
  @DisplayName(
      "Test new Bindings(Method[], ValueExpression[], TypeConverter); when empty array of Method")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void Bindings.<init>(Method[], ValueExpression[], TypeConverter)"})
  void testNewBindings_whenEmptyArrayOfMethod2() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    // Act
    Bindings actualBindings = new Bindings(new Method[] {}, variables, mock(TypeConverter.class));

    // Assert
    assertFalse(actualBindings.isFunctionBound(1));
    assertFalse(actualBindings.isVariableBound(1));
  }

  /**
   * Test {@link Bindings#Bindings(Method[], ValueExpression[])}.
   *
   * <ul>
   *   <li>When empty array of {@link ValueExpression}.
   * </ul>
   *
   * <p>Method under test: {@link Bindings#Bindings(Method[], ValueExpression[])}
   */
  @Test
  @DisplayName(
      "Test new Bindings(Method[], ValueExpression[]); when empty array of ValueExpression")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void Bindings.<init>(Method[], ValueExpression[])"})
  void testNewBindings_whenEmptyArrayOfValueExpression() {
    // Arrange
    Method[] functions = new Method[] {null};

    // Act
    Bindings actualBindings = new Bindings(functions, new ValueExpression[] {});

    // Assert
    assertFalse(actualBindings.isFunctionBound(1));
    assertFalse(actualBindings.isVariableBound(1));
  }

  /**
   * Test {@link Bindings#Bindings(Method[], ValueExpression[], TypeConverter)}.
   *
   * <ul>
   *   <li>When empty array of {@link ValueExpression}.
   * </ul>
   *
   * <p>Method under test: {@link Bindings#Bindings(Method[], ValueExpression[], TypeConverter)}
   */
  @Test
  @DisplayName(
      "Test new Bindings(Method[], ValueExpression[], TypeConverter); when empty array of ValueExpression")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void Bindings.<init>(Method[], ValueExpression[], TypeConverter)"})
  void testNewBindings_whenEmptyArrayOfValueExpression2() {
    // Arrange
    Method[] functions = new Method[] {null};

    // Act
    Bindings actualBindings =
        new Bindings(functions, new ValueExpression[] {}, mock(TypeConverter.class));

    // Assert
    assertFalse(actualBindings.isFunctionBound(1));
    assertFalse(actualBindings.isVariableBound(1));
  }

  /**
   * Test {@link Bindings#Bindings(Method[], ValueExpression[])}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Bindings#Bindings(Method[], ValueExpression[])}
   */
  @Test
  @DisplayName("Test new Bindings(Method[], ValueExpression[]); when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void Bindings.<init>(Method[], ValueExpression[])"})
  void testNewBindings_whenNull() {
    // Arrange and Act
    Bindings actualBindings = new Bindings(null, null);

    // Assert
    assertFalse(actualBindings.isFunctionBound(1));
    assertFalse(actualBindings.isVariableBound(1));
  }

  /**
   * Test {@link Bindings#Bindings(Method[], ValueExpression[], TypeConverter)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Bindings#Bindings(Method[], ValueExpression[], TypeConverter)}
   */
  @Test
  @DisplayName("Test new Bindings(Method[], ValueExpression[], TypeConverter); when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void Bindings.<init>(Method[], ValueExpression[], TypeConverter)"})
  void testNewBindings_whenNull2() {
    // Arrange and Act
    Bindings actualBindings = new Bindings(null, null, null);

    // Assert
    assertFalse(actualBindings.isFunctionBound(1));
    assertFalse(actualBindings.isVariableBound(1));
  }

  /**
   * Test {@link Bindings#getFunction(int)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Bindings#getFunction(int)}
   */
  @Test
  @DisplayName("Test getFunction(int); then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Method Bindings.getFunction(int)"})
  void testGetFunction_thenReturnNull() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};
    Bindings bindings = new Bindings(new Method[] {null, null}, variables);

    // Act and Assert
    assertNull(bindings.getFunction(1));
  }

  /**
   * Test {@link Bindings#isFunctionBound(int)}.
   *
   * <ul>
   *   <li>When minus one.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link Bindings#isFunctionBound(int)}
   */
  @Test
  @DisplayName("Test isFunctionBound(int); when minus one; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Bindings.isFunctionBound(int)"})
  void testIsFunctionBound_whenMinusOne_thenReturnFalse() {
    // Arrange
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertFalse(bindings.isFunctionBound(-1));
  }

  /**
   * Test {@link Bindings#isFunctionBound(int)}.
   *
   * <ul>
   *   <li>When one.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link Bindings#isFunctionBound(int)}
   */
  @Test
  @DisplayName("Test isFunctionBound(int); when one; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Bindings.isFunctionBound(int)"})
  void testIsFunctionBound_whenOne_thenReturnFalse() {
    // Arrange
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertFalse(bindings.isFunctionBound(1));
  }

  /**
   * Test {@link Bindings#isFunctionBound(int)}.
   *
   * <ul>
   *   <li>When zero.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link Bindings#isFunctionBound(int)}
   */
  @Test
  @DisplayName("Test isFunctionBound(int); when zero; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Bindings.isFunctionBound(int)"})
  void testIsFunctionBound_whenZero_thenReturnTrue() {
    // Arrange
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertTrue(bindings.isFunctionBound(0));
  }

  /**
   * Test {@link Bindings#getVariable(int)}.
   *
   * <p>Method under test: {@link Bindings#getVariable(int)}
   */
  @Test
  @DisplayName("Test getVariable(int)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ValueExpression Bindings.getVariable(int)"})
  void testGetVariable() {
    // Arrange
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type2 = Object.class;

    ObjectValueExpression objectValueExpression2 =
        new ObjectValueExpression(converter2, "Object", type2);
    Bindings bindings =
        new Bindings(
            functions, new ValueExpression[] {objectValueExpression, objectValueExpression2});

    // Act and Assert
    assertSame(objectValueExpression2, bindings.getVariable(1));
  }

  /**
   * Test {@link Bindings#isVariableBound(int)}.
   *
   * <p>Method under test: {@link Bindings#isVariableBound(int)}
   */
  @Test
  @DisplayName("Test isVariableBound(int)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Bindings.isVariableBound(int)"})
  void testIsVariableBound() {
    // Arrange
    Method[] functions = new Method[] {null};
    ValueExpression[] variables = new ValueExpression[] {null};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertFalse(bindings.isVariableBound(0));
  }

  /**
   * Test {@link Bindings#isVariableBound(int)}.
   *
   * <ul>
   *   <li>Given {@code Object}.
   *   <li>When minus one.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link Bindings#isVariableBound(int)}
   */
  @Test
  @DisplayName(
      "Test isVariableBound(int); given 'java.lang.Object'; when minus one; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Bindings.isVariableBound(int)"})
  void testIsVariableBound_givenJavaLangObject_whenMinusOne_thenReturnFalse() {
    // Arrange
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertFalse(bindings.isVariableBound(-1));
  }

  /**
   * Test {@link Bindings#isVariableBound(int)}.
   *
   * <ul>
   *   <li>Given {@code Object}.
   *   <li>When one.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link Bindings#isVariableBound(int)}
   */
  @Test
  @DisplayName("Test isVariableBound(int); given 'java.lang.Object'; when one; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Bindings.isVariableBound(int)"})
  void testIsVariableBound_givenJavaLangObject_whenOne_thenReturnFalse() {
    // Arrange
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertFalse(bindings.isVariableBound(1));
  }

  /**
   * Test {@link Bindings#isVariableBound(int)}.
   *
   * <ul>
   *   <li>Given {@code Object}.
   *   <li>When zero.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link Bindings#isVariableBound(int)}
   */
  @Test
  @DisplayName("Test isVariableBound(int); given 'java.lang.Object'; when zero; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Bindings.isVariableBound(int)"})
  void testIsVariableBound_givenJavaLangObject_whenZero_thenReturnTrue() {
    // Arrange
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertTrue(bindings.isVariableBound(0));
  }

  /**
   * Test {@link Bindings#convert(Object, Class)}.
   *
   * <ul>
   *   <li>When {@code Object}.
   *   <li>Then return {@code Value}.
   * </ul>
   *
   * <p>Method under test: {@link Bindings#convert(Object, Class)}
   */
  @Test
  @DisplayName("Test convert(Object, Class); when 'java.lang.Object'; then return 'Value'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object Bindings.convert(Object, Class)"})
  void testConvert_whenJavaLangObject_thenReturnValue() {
    // Arrange
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);
    Class<Object> type2 = Object.class;

    // Act and Assert
    assertEquals("Value", bindings.convert("Value", type2));
  }

  /**
   * Test {@link Bindings#equals(Object)}, and {@link Bindings#hashCode()}.
   *
   * <ul>
   *   <li>When other is equal.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link Bindings#equals(Object)}
   *   <li>{@link Bindings#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Bindings.equals(Object)", "int Bindings.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);
    Method[] functions2 = new Method[] {null};
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type2 = Object.class;

    ObjectValueExpression objectValueExpression2 =
        new ObjectValueExpression(converter2, "Object", type2);
    ValueExpression[] variables2 = new ValueExpression[] {objectValueExpression2};

    Bindings bindings2 = new Bindings(functions2, variables2);

    // Act and Assert
    assertEquals(bindings, bindings2);
    assertEquals(bindings.hashCode(), bindings2.hashCode());
  }

  /**
   * Test {@link Bindings#equals(Object)}, and {@link Bindings#hashCode()}.
   *
   * <ul>
   *   <li>When other is same.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link Bindings#equals(Object)}
   *   <li>{@link Bindings#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Bindings.equals(Object)", "int Bindings.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertEquals(bindings, bindings);
    int expectedHashCodeResult = bindings.hashCode();
    assertEquals(expectedHashCodeResult, bindings.hashCode());
  }

  /**
   * Test {@link Bindings#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link Bindings#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Bindings.equals(Object)", "int Bindings.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};
    Bindings bindings = new Bindings(new Method[] {}, variables);
    Method[] functions = new Method[] {null};
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type2 = Object.class;

    ObjectValueExpression objectValueExpression2 =
        new ObjectValueExpression(converter2, "Object", type2);
    ValueExpression[] variables2 = new ValueExpression[] {objectValueExpression2};

    Bindings bindings2 = new Bindings(functions, variables2);

    // Act and Assert
    assertNotEquals(bindings, bindings2);
  }

  /**
   * Test {@link Bindings#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link Bindings#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Bindings.equals(Object)", "int Bindings.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    ValueExpression[] variables =
        new ValueExpression[] {new ObjectValueExpression(converter, 1, type)};

    Bindings bindings = new Bindings(functions, variables);
    Method[] functions2 = new Method[] {null};
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type2 = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter2, "Object", type2);
    ValueExpression[] variables2 = new ValueExpression[] {objectValueExpression};

    Bindings bindings2 = new Bindings(functions2, variables2);

    // Act and Assert
    assertNotEquals(bindings, bindings2);
  }

  /**
   * Test {@link Bindings#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link Bindings#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Bindings.equals(Object)", "int Bindings.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Method[] functions2 = new Method[] {null};
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter2, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions2, variables);
    Class<Object> type2 = Object.class;

    ObjectValueExpression objectValueExpression2 =
        new ObjectValueExpression(converter, bindings, type2);
    ValueExpression[] variables2 = new ValueExpression[] {objectValueExpression2};

    Bindings bindings2 = new Bindings(functions, variables2);
    Method[] functions3 = new Method[] {null};
    TypeConverter converter3 = mock(TypeConverter.class);
    Class<Object> type3 = Object.class;

    ObjectValueExpression objectValueExpression3 =
        new ObjectValueExpression(converter3, "Object", type3);
    ValueExpression[] variables3 = new ValueExpression[] {objectValueExpression3};

    Bindings bindings3 = new Bindings(functions3, variables3);

    // Act and Assert
    assertNotEquals(bindings2, bindings3);
  }

  /**
   * Test {@link Bindings#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link Bindings#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Bindings.equals(Object)", "int Bindings.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables, mock(TypeConverter.class));
    Method[] functions2 = new Method[] {null};
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type2 = Object.class;

    ObjectValueExpression objectValueExpression2 =
        new ObjectValueExpression(converter2, "Object", type2);
    ValueExpression[] variables2 = new ValueExpression[] {objectValueExpression2};

    Bindings bindings2 = new Bindings(functions2, variables2);

    // Act and Assert
    assertNotEquals(bindings, bindings2);
  }

  /**
   * Test {@link Bindings#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link Bindings#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Bindings.equals(Object)", "int Bindings.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};
    Method[] functions2 = new Method[] {null};
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type2 = Object.class;

    ObjectValueExpression objectValueExpression2 =
        new ObjectValueExpression(converter2, "Object", type2);
    ValueExpression[] variables2 = new ValueExpression[] {objectValueExpression2};

    Bindings converter3 = new Bindings(functions2, variables2);

    Bindings bindings = new Bindings(functions, variables, converter3);
    Method[] functions3 = new Method[] {null};
    TypeConverter converter4 = mock(TypeConverter.class);
    Class<Object> type3 = Object.class;

    ObjectValueExpression objectValueExpression3 =
        new ObjectValueExpression(converter4, "Object", type3);
    ValueExpression[] variables3 = new ValueExpression[] {objectValueExpression3};

    Bindings bindings2 = new Bindings(functions3, variables3);

    // Act and Assert
    assertNotEquals(bindings, bindings2);
  }

  /**
   * Test {@link Bindings#equals(Object)}.
   *
   * <ul>
   *   <li>When other is {@code null}.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link Bindings#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Bindings.equals(Object)", "int Bindings.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertNotEquals(bindings, null);
  }

  /**
   * Test {@link Bindings#equals(Object)}.
   *
   * <ul>
   *   <li>When other is wrong type.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link Bindings#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean Bindings.equals(Object)", "int Bindings.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertNotEquals(bindings, "Different type to Bindings");
  }
}
