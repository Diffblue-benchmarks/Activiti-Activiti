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
package org.activiti.core.el.juel.tree.impl.ast;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.el.ELContext;
import jakarta.el.ELException;
import jakarta.el.ValueExpression;
import java.lang.reflect.Method;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.core.el.juel.tree.Bindings;
import org.activiti.core.el.juel.util.SimpleContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class AstTextDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link AstText#AstText(String)}
   *   <li>{@link AstText#toString()}
   *   <li>{@link AstText#getCardinality()}
   *   <li>{@link AstText#isLeftValue()}
   *   <li>{@link AstText#isLiteralText()}
   *   <li>{@link AstText#isMethodInvocation()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AstText.<init>(String)",
    "int AstText.getCardinality()",
    "boolean AstText.isLeftValue()",
    "boolean AstText.isLiteralText()",
    "boolean AstText.isMethodInvocation()",
    "String AstText.toString()"
  })
  void testGettersAndSetters() {
    // Arrange and Act
    AstText actualAstText = new AstText("42");
    String actualToStringResult = actualAstText.toString();
    int actualCardinality = actualAstText.getCardinality();
    boolean actualIsLeftValueResult = actualAstText.isLeftValue();
    boolean actualIsLiteralTextResult = actualAstText.isLiteralText();

    // Assert
    assertEquals("\"42\"", actualToStringResult);
    assertEquals(0, actualCardinality);
    assertFalse(actualIsLeftValueResult);
    assertFalse(actualAstText.isMethodInvocation());
    assertTrue(actualIsLiteralTextResult);
  }

  /**
   * Test {@link AstText#getType(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstText#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getType(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class AstText.getType(Bindings, ELContext)"})
  void testGetType() {
    // Arrange
    AstText astText = new AstText("42");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertNull(astText.getType(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstText#isReadOnly(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstText#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AstText.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly() {
    // Arrange
    AstText astText = new AstText("42");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertTrue(astText.isReadOnly(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstText#setValue(Bindings, ELContext, Object)}.
   *
   * <ul>
   *   <li>Given {@link AstText#AstText(String)} with value is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link AstText#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName(
      "Test setValue(Bindings, ELContext, Object); given AstText(String) with value is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstText.setValue(Bindings, ELContext, Object)"})
  void testSetValue_givenAstTextWithValueIs42() {
    // Arrange
    AstText astText = new AstText("42");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(ELException.class, () -> astText.setValue(bindings, new SimpleContext(), "Value"));
  }

  /**
   * Test {@link AstText#setValue(Bindings, ELContext, Object)}.
   *
   * <ul>
   *   <li>Given {@link AstText#AstText(String)} with value is empty string.
   * </ul>
   *
   * <p>Method under test: {@link AstText#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName(
      "Test setValue(Bindings, ELContext, Object); given AstText(String) with value is empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstText.setValue(Bindings, ELContext, Object)"})
  void testSetValue_givenAstTextWithValueIsEmptyString() {
    // Arrange
    AstText astText = new AstText("");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(ELException.class, () -> astText.setValue(bindings, new SimpleContext(), "Value"));
  }

  /**
   * Test {@link AstText#getValueReference(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstText#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getValueReference(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"jakarta.el.ValueReference AstText.getValueReference(Bindings, ELContext)"})
  void testGetValueReference() {
    // Arrange
    AstText astText = new AstText("42");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertNull(astText.getValueReference(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstText#eval(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstText#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstText.eval(Bindings, ELContext)"})
  void testEval() {
    // Arrange
    AstText astText = new AstText("42");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertEquals("42", astText.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstText#getMethodInfo(Bindings, ELContext, Class, Class[])}.
   *
   * <p>Method under test: {@link AstText#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  @DisplayName("Test getMethodInfo(Bindings, ELContext, Class, Class[])")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "jakarta.el.MethodInfo AstText.getMethodInfo(Bindings, ELContext, Class, Class[])"
  })
  void testGetMethodInfo() {
    // Arrange
    AstText astText = new AstText("42");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);
    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;
    Class<?>[] paramTypes = new Class[] {forNameResult};

    // Act and Assert
    assertNull(astText.getMethodInfo(bindings, context, returnType, paramTypes));
    assertEquals(1, paramTypes.length);
    Class<Object> expectedResultClass = Object.class;
    assertEquals(expectedResultClass, paramTypes[0]);
  }

  /**
   * Test {@link AstText#invoke(Bindings, ELContext, Class, Class[], Object[])}.
   *
   * <ul>
   *   <li>Then return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link AstText#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(Bindings, ELContext, Class, Class[], Object[]); then return '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstText.invoke(Bindings, ELContext, Class, Class[], Object[])"})
  void testInvoke_thenReturn42() {
    // Arrange
    AstText astText = new AstText("42");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);
    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;
    Class<?>[] paramTypes = new Class[] {forNameResult};

    // Act and Assert
    assertEquals(
        "42",
        astText.invoke(bindings, context, returnType, paramTypes, new Object[] {"Param Values"}));
    assertEquals(1, paramTypes.length);
    Class<Object> expectedResultClass = Object.class;
    assertEquals(expectedResultClass, paramTypes[0]);
  }

  /**
   * Test {@link AstText#invoke(Bindings, ELContext, Class, Class[], Object[])}.
   *
   * <ul>
   *   <li>Then return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link AstText#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(Bindings, ELContext, Class, Class[], Object[]); then return '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstText.invoke(Bindings, ELContext, Class, Class[], Object[])"})
  void testInvoke_thenReturn422() {
    // Arrange
    AstText astText = new AstText("42");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);
    SimpleContext context = new SimpleContext();
    Class<Object> forNameResult = Object.class;
    Class<?>[] paramTypes = new Class[] {forNameResult};

    // Act and Assert
    assertEquals(
        "42", astText.invoke(bindings, context, null, paramTypes, new Object[] {"Param Values"}));
    assertEquals(1, paramTypes.length);
    Class<Object> expectedResultClass = Object.class;
    assertEquals(expectedResultClass, paramTypes[0]);
  }

  /**
   * Test {@link AstText#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo} toString is {@code
   *       foo}.
   * </ul>
   *
   * <p>Method under test: {@link AstText#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foo'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstText.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoo() {
    // Arrange
    AstText astText = new AstText("");
    StringBuilder b = new StringBuilder("foo");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astText.appendStructure(b, bindings);

    // Assert that nothing has changed
    assertEquals("foo", b.toString());
  }

  /**
   * Test {@link AstText#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo} toString is {@code
   *       foo42}.
   * </ul>
   *
   * <p>Method under test: {@link AstText#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foo42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstText.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoo42() {
    // Arrange
    AstText astText = new AstText("42");
    StringBuilder b = new StringBuilder("foo");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astText.appendStructure(b, bindings);

    // Assert
    assertEquals("foo42", b.toString());
  }

  /**
   * Test {@link AstText#getChild(int)}.
   *
   * <p>Method under test: {@link AstText#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.core.el.juel.tree.impl.ast.AstNode AstText.getChild(int)"})
  void testGetChild() {
    // Arrange, Act and Assert
    assertNull(new AstText("42").getChild(1));
  }
}
