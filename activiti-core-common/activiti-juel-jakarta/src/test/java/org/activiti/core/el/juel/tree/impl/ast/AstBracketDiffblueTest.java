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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.el.CompositeELResolver;
import jakarta.el.ELContext;
import jakarta.el.ELException;
import jakarta.el.PropertyNotFoundException;
import jakarta.el.ValueExpression;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.core.el.juel.tree.Bindings;
import org.activiti.core.el.juel.tree.impl.ast.AstBinary.Operator;
import org.activiti.core.el.juel.util.SimpleContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AstBracketDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link AstBracket#AstBracket(AstNode, AstNode, boolean, boolean)}
   *   <li>{@link AstBracket#toString()}
   *   <li>{@link AstBracket#getCardinality()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AstBracket.<init>(AstNode, AstNode, boolean, boolean)",
    "void AstBracket.<init>(AstNode, AstNode, boolean, boolean, boolean)",
    "int AstBracket.getCardinality()",
    "String AstBracket.toString()"
  })
  void testGettersAndSetters() {
    // Arrange
    AstNull base = new AstNull();

    // Act
    AstBracket actualAstBracket = new AstBracket(base, new AstNull(), true, true);
    String actualToStringResult = actualAstBracket.toString();

    // Assert
    assertEquals("[...]", actualToStringResult);
    assertEquals(2, actualAstBracket.getCardinality());
    assertTrue(actualAstBracket.isLeftValue());
    assertSame(base, actualAstBracket.getPrefix());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link AstBracket#AstBracket(AstNode, AstNode, boolean, boolean, boolean)}
   *   <li>{@link AstBracket#toString()}
   *   <li>{@link AstBracket#getCardinality()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AstBracket.<init>(AstNode, AstNode, boolean, boolean)",
    "void AstBracket.<init>(AstNode, AstNode, boolean, boolean, boolean)",
    "int AstBracket.getCardinality()",
    "String AstBracket.toString()"
  })
  void testGettersAndSetters2() {
    // Arrange
    AstNull base = new AstNull();

    // Act
    AstBracket actualAstBracket = new AstBracket(base, new AstNull(), true, true, true);
    String actualToStringResult = actualAstBracket.toString();

    // Assert
    assertEquals("[...]", actualToStringResult);
    assertEquals(2, actualAstBracket.getCardinality());
    assertTrue(actualAstBracket.isLeftValue());
    assertSame(base, actualAstBracket.getPrefix());
  }

  /**
   * Test {@link AstBracket#getProperty(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstBracket#getProperty(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getProperty(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstBracket.getProperty(Bindings, ELContext)"})
  void testGetProperty() throws ELException {
    // Arrange
    AstNull base = new AstNull();
    AstBracket astBracket = new AstBracket(base, new AstNull(), true, true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertNull(astBracket.getProperty(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstBracket#getProperty(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstBracket#getProperty(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getProperty(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstBracket.getProperty(Bindings, ELContext)"})
  void testGetProperty2() throws ELException {
    // Arrange
    AstNull base = new AstNull();
    AstNull base2 = new AstNull();
    AstBracket property = new AstBracket(base2, new AstNull(), true, true);

    AstBracket astBracket = new AstBracket(base, property, true, true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertNull(astBracket.getProperty(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstBracket#getProperty(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstBracket#getProperty(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getProperty(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstBracket.getProperty(Bindings, ELContext)"})
  void testGetProperty3() throws ELException {
    // Arrange
    AstNull base = new AstNull();
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    AstChoice property = new AstChoice(question, yes, new AstNull());

    AstBracket astBracket = new AstBracket(base, property, true, true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertNull(astBracket.getProperty(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstBracket#getProperty(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstBracket#getProperty(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getProperty(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstBracket.getProperty(Bindings, ELContext)"})
  void testGetProperty4() throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);
    AstBracket property = new AstBracket(base, new AstNull(), true, true);
    AstBracket astBracket = new AstBracket(new AstNull(), property, true, true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    Object actualProperty = astBracket.getProperty(bindings, new SimpleContext());

    // Assert
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
    assertNull(actualProperty);
  }

  /**
   * Test {@link AstBracket#getProperty(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstBracket#getProperty(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getProperty(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstBracket.getProperty(Bindings, ELContext)"})
  void testGetProperty5() throws ELException {
    // Arrange
    AstNull base = new AstNull();
    AstNull base2 = new AstNull();
    AstBracket base3 = new AstBracket(base2, new AstNull(), true, true);
    AstBracket property = new AstBracket(base3, new AstNull(), true, true);

    AstBracket astBracket = new AstBracket(base, property, true, true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertNull(astBracket.getProperty(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstBracket#getProperty(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstBracket#getProperty(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getProperty(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstBracket.getProperty(Bindings, ELContext)"})
  void testGetProperty6() throws ELException {
    // Arrange
    AstNull base = new AstNull();
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    AstChoice base2 = new AstChoice(question, yes, new AstNull());
    AstBracket property = new AstBracket(base2, new AstNull(), true, true);

    AstBracket astBracket = new AstBracket(base, property, true, true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertNull(astBracket.getProperty(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstBracket#getProperty(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstBoolean#AstBoolean(boolean)} with value is {@code false}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link AstBracket#getProperty(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test getProperty(Bindings, ELContext); given AstBoolean(boolean) with value is 'false'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstBracket.getProperty(Bindings, ELContext)"})
  void testGetProperty_givenAstBooleanWithValueIsFalse_thenReturnFalse() throws ELException {
    // Arrange
    AstNull base = new AstNull();
    AstBracket astBracket = new AstBracket(base, new AstBoolean(false), true, true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertFalse((Boolean) astBracket.getProperty(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstBracket#getProperty(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstBoolean#AstBoolean(boolean)} with value is {@code true}.
   *   <li>When {@link SimpleContext#SimpleContext()}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AstBracket#getProperty(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test getProperty(Bindings, ELContext); given AstBoolean(boolean) with value is 'true'; when SimpleContext(); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstBracket.getProperty(Bindings, ELContext)"})
  void testGetProperty_givenAstBooleanWithValueIsTrue_whenSimpleContext_thenReturnTrue()
      throws ELException {
    // Arrange
    AstNull base = new AstNull();
    AstBracket astBracket = new AstBracket(base, new AstBoolean(true), true, true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertTrue((Boolean) astBracket.getProperty(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstBracket#getProperty(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Then throw {@link PropertyNotFoundException}.
   * </ul>
   *
   * <p>Method under test: {@link AstBracket#getProperty(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getProperty(Bindings, ELContext); then throw PropertyNotFoundException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstBracket.getProperty(Bindings, ELContext)"})
  void testGetProperty_thenThrowPropertyNotFoundException() throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary base = new AstBinary(left, new AstNull(), operator);

    Operator operator2 = mock(Operator.class);
    when(operator2.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left2 = new AstNull();

    AstBinary property = new AstBinary(left2, new AstNull(), operator2);

    AstBracket property2 = new AstBracket(base, property, true, true);
    AstBracket astBracket = new AstBracket(new AstNull(), property2, true, true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astBracket.getProperty(bindings, new SimpleContext(new CompositeELResolver())));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
    verify(operator2)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
  }

  /**
   * Test {@link AstBracket#appendStructure(StringBuilder, Bindings)}.
   *
   * <p>Method under test: {@link AstBracket#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstBracket.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure() {
    // Arrange
    AstFunction base = new AstFunction("null", -1, new AstParameters(new ArrayList<>()));
    AstBracket astBracket = new AstBracket(base, new AstNull(), true, true);
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astBracket.appendStructure(b, bindings);

    // Assert
    assertEquals("Strnull()[null]", b.toString());
  }

  /**
   * Test {@link AstBracket#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code Str} toString is {@code
   *       Str<fn>()[null]}.
   * </ul>
   *
   * <p>Method under test: {@link AstBracket#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'Str' toString is 'Str<fn>()[null]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstBracket.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithStrToStringIsStrFnNull() {
    // Arrange
    AstFunction base = new AstFunction("null", 0, new AstParameters(new ArrayList<>()));
    AstBracket astBracket = new AstBracket(base, new AstNull(), true, true);
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astBracket.appendStructure(b, bindings);

    // Assert
    assertEquals("Str<fn>()[null]", b.toString());
  }

  /**
   * Test {@link AstBracket#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code Str} toString is {@code
   *       Strnull[null]}.
   * </ul>
   *
   * <p>Method under test: {@link AstBracket#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'Str' toString is 'Strnull[null]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstBracket.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithStrToStringIsStrnullNull() {
    // Arrange
    AstNull base = new AstNull();
    AstBracket astBracket = new AstBracket(base, new AstNull(), true, true);
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astBracket.appendStructure(b, bindings);

    // Assert
    assertEquals("Strnull[null]", b.toString());
  }

  /**
   * Test {@link AstBracket#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code Str} toString is {@code
   *       Strnull()[null]}.
   * </ul>
   *
   * <p>Method under test: {@link AstBracket#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'Str' toString is 'Strnull()[null]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstBracket.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithStrToStringIsStrnullNull2() {
    // Arrange
    AstFunction base = new AstFunction("null", 1, new AstParameters(new ArrayList<>()));
    AstBracket astBracket = new AstBracket(base, new AstNull(), true, true);
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astBracket.appendStructure(b, bindings);

    // Assert
    assertEquals("Strnull()[null]", b.toString());
  }

  /**
   * Test {@link AstBracket#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code Str} toString is {@code
   *       Strnull[null()]}.
   * </ul>
   *
   * <p>Method under test: {@link AstBracket#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'Str' toString is 'Strnull[null()]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstBracket.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithStrToStringIsStrnullNull3() {
    // Arrange
    AstNull base = new AstNull();
    AstBracket astBracket =
        new AstBracket(
            base, new AstFunction("null", 1, new AstParameters(new ArrayList<>())), true, true);
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astBracket.appendStructure(b, bindings);

    // Assert
    assertEquals("Strnull[null()]", b.toString());
  }

  /**
   * Test {@link AstBracket#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code Str} toString is {@code
   *       Strnull[null][null]}.
   * </ul>
   *
   * <p>Method under test: {@link AstBracket#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'Str' toString is 'Strnull[null][null]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstBracket.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithStrToStringIsStrnullNullNull() {
    // Arrange
    AstNull base = new AstNull();
    AstBracket base2 = new AstBracket(base, new AstNull(), true, true);
    AstBracket astBracket = new AstBracket(base2, new AstNull(), true, true);
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astBracket.appendStructure(b, bindings);

    // Assert
    assertEquals("Strnull[null][null]", b.toString());
  }

  /**
   * Test {@link AstBracket#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code Str} toString is {@code
   *       Strnull.null[null]}.
   * </ul>
   *
   * <p>Method under test: {@link AstBracket#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'Str' toString is 'Strnull.null[null]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstBracket.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithStrToStringIsStrnullNullNull2() {
    // Arrange
    AstDot base = new AstDot(new AstNull(), "null", true);
    AstBracket astBracket = new AstBracket(base, new AstNull(), true, true);
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astBracket.appendStructure(b, bindings);

    // Assert
    assertEquals("Strnull.null[null]", b.toString());
  }

  /**
   * Test {@link AstBracket#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code Str} toString is {@code
   *       Strnull[null[null]]}.
   * </ul>
   *
   * <p>Method under test: {@link AstBracket#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'Str' toString is 'Strnull[null[null]]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstBracket.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithStrToStringIsStrnullNullNull3() {
    // Arrange
    AstNull base = new AstNull();
    AstNull base2 = new AstNull();
    AstBracket property = new AstBracket(base2, new AstNull(), true, true);

    AstBracket astBracket = new AstBracket(base, property, true, true);
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astBracket.appendStructure(b, bindings);

    // Assert
    assertEquals("Strnull[null[null]]", b.toString());
  }

  /**
   * Test {@link AstBracket#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code Str} toString is {@code
   *       Strnull[null.null]}.
   * </ul>
   *
   * <p>Method under test: {@link AstBracket#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'Str' toString is 'Strnull[null.null]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstBracket.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithStrToStringIsStrnullNullNull4() {
    // Arrange
    AstNull base = new AstNull();
    AstDot property = new AstDot(new AstNull(), "null", true);

    AstBracket astBracket = new AstBracket(base, property, true, true);
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astBracket.appendStructure(b, bindings);

    // Assert
    assertEquals("Strnull[null.null]", b.toString());
  }

  /**
   * Test {@link AstBracket#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code Str} toString is {@code
   *       Strnull().null[null]}.
   * </ul>
   *
   * <p>Method under test: {@link AstBracket#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'Str' toString is 'Strnull().null[null]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstBracket.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithStrToStringIsStrnullNullNull5() {
    // Arrange
    AstDot base =
        new AstDot(new AstFunction("null", 1, new AstParameters(new ArrayList<>())), "null", true);
    AstBracket astBracket = new AstBracket(base, new AstNull(), true, true);
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astBracket.appendStructure(b, bindings);

    // Assert
    assertEquals("Strnull().null[null]", b.toString());
  }

  /**
   * Test {@link AstBracket#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code Str} toString is {@code
   *       Strnull[null].null[null]}.
   * </ul>
   *
   * <p>Method under test: {@link AstBracket#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'Str' toString is 'Strnull[null].null[null]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstBracket.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithStrToStringIsStrnullNullNullNull() {
    // Arrange
    AstNull base = new AstNull();
    AstBracket base2 = new AstBracket(base, new AstNull(), true, true);
    AstDot base3 = new AstDot(base2, "null", true);
    AstBracket astBracket = new AstBracket(base3, new AstNull(), true, true);
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astBracket.appendStructure(b, bindings);

    // Assert
    assertEquals("Strnull[null].null[null]", b.toString());
  }

  /**
   * Test {@link AstBracket#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code Str} toString is {@code
   *       Strnull.null.null[null]}.
   * </ul>
   *
   * <p>Method under test: {@link AstBracket#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'Str' toString is 'Strnull.null.null[null]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstBracket.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithStrToStringIsStrnullNullNullNull2() {
    // Arrange
    AstDot base = new AstDot(new AstNull(), "null", true);
    AstDot base2 = new AstDot(base, "null", true);
    AstBracket astBracket = new AstBracket(base2, new AstNull(), true, true);
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astBracket.appendStructure(b, bindings);

    // Assert
    assertEquals("Strnull.null.null[null]", b.toString());
  }

  /**
   * Test {@link AstBracket#getChild(int)}.
   *
   * <p>Method under test: {@link AstBracket#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"AstNode AstBracket.getChild(int)"})
  void testGetChild() {
    // Arrange
    AstNull base = new AstNull();
    AstBracket astBracket = new AstBracket(base, new AstNull(), true, true);

    // Act
    AstNode actualChild = astBracket.getChild(1);

    // Assert
    assertSame(astBracket.property, actualChild);
  }

  /**
   * Test {@link AstBracket#getChild(int)}.
   *
   * <ul>
   *   <li>When minus one.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AstBracket#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); when minus one; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"AstNode AstBracket.getChild(int)"})
  void testGetChild_whenMinusOne_thenReturnNull() {
    // Arrange
    AstNull base = new AstNull();
    AstBracket astBracket = new AstBracket(base, new AstNull(), true, true);

    // Act and Assert
    assertNull(astBracket.getChild(-1));
  }

  /**
   * Test {@link AstBracket#getChild(int)}.
   *
   * <ul>
   *   <li>When zero.
   *   <li>Then return {@link AstNull} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link AstBracket#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); when zero; then return AstNull (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"AstNode AstBracket.getChild(int)"})
  void testGetChild_whenZero_thenReturnAstNull() {
    // Arrange
    AstNull base = new AstNull();
    AstBracket astBracket = new AstBracket(base, new AstNull(), true, true);

    // Act and Assert
    assertSame(base, astBracket.getChild(0));
  }
}
