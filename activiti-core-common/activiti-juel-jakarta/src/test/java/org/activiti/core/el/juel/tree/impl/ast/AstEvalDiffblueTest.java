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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.el.ELContext;
import jakarta.el.ValueExpression;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.core.el.juel.tree.Bindings;
import org.activiti.core.el.juel.util.SimpleContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AstEvalDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link AstEval#AstEval(AstNode, boolean)}
   *   <li>{@link AstEval#getCardinality()}
   *   <li>{@link AstEval#isDeferred()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AstEval.<init>(AstNode, boolean)",
    "int AstEval.getCardinality()",
    "boolean AstEval.isDeferred()"
  })
  void testGettersAndSetters() {
    // Arrange and Act
    AstEval actualAstEval = new AstEval(new AstNull(), true);
    int actualCardinality = actualAstEval.getCardinality();

    // Assert
    assertEquals(1, actualCardinality);
    assertTrue(actualAstEval.isDeferred());
  }

  /**
   * Test {@link AstEval#isLeftValue()}.
   *
   * <ul>
   *   <li>Given {@link AstEval#AstEval(AstNode, boolean)} with child is {@link
   *       AstEval#AstEval(AstNode, boolean)} and deferred is {@code true}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#isLeftValue()}
   */
  @Test
  @DisplayName(
      "Test isLeftValue(); given AstEval(AstNode, boolean) with child is AstEval(AstNode, boolean) and deferred is 'true'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AstEval.isLeftValue()"})
  void testIsLeftValue_givenAstEvalWithChildIsAstEvalAndDeferredIsTrue_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new AstEval(new AstEval(new AstNull(), true), true).isLeftValue());
  }

  /**
   * Test {@link AstEval#isLeftValue()}.
   *
   * <ul>
   *   <li>Given {@link AstEval#AstEval(AstNode, boolean)} with child is {@link AstNull} (default
   *       constructor) and deferred is {@code true}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#isLeftValue()}
   */
  @Test
  @DisplayName(
      "Test isLeftValue(); given AstEval(AstNode, boolean) with child is AstNull (default constructor) and deferred is 'true'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AstEval.isLeftValue()"})
  void testIsLeftValue_givenAstEvalWithChildIsAstNullAndDeferredIsTrue_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new AstEval(new AstNull(), true).isLeftValue());
  }

  /**
   * Test {@link AstEval#isLeftValue()}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#isLeftValue()}
   */
  @Test
  @DisplayName("Test isLeftValue(); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AstEval.isLeftValue()"})
  void testIsLeftValue_thenReturnTrue() {
    // Arrange
    AstNull base = new AstNull();
    AstBracket child = new AstBracket(base, new AstNull(), true, true);

    // Act and Assert
    assertTrue(new AstEval(child, true).isLeftValue());
  }

  /**
   * Test {@link AstEval#isMethodInvocation()}.
   *
   * <ul>
   *   <li>Given {@link AstEval#AstEval(AstNode, boolean)} with child is {@link
   *       AstEval#AstEval(AstNode, boolean)} and deferred is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#isMethodInvocation()}
   */
  @Test
  @DisplayName(
      "Test isMethodInvocation(); given AstEval(AstNode, boolean) with child is AstEval(AstNode, boolean) and deferred is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AstEval.isMethodInvocation()"})
  void testIsMethodInvocation_givenAstEvalWithChildIsAstEvalAndDeferredIsTrue() {
    // Arrange, Act and Assert
    assertFalse(new AstEval(new AstEval(new AstNull(), true), true).isMethodInvocation());
  }

  /**
   * Test {@link AstEval#isMethodInvocation()}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#isMethodInvocation()}
   */
  @Test
  @DisplayName("Test isMethodInvocation(); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AstEval.isMethodInvocation()"})
  void testIsMethodInvocation_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new AstEval(new AstNull(), true).isMethodInvocation());
  }

  /**
   * Test {@link AstEval#getValueReference(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstEval#AstEval(AstNode, boolean)} with child is {@link
   *       AstEval#AstEval(AstNode, boolean)} and deferred is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test getValueReference(Bindings, ELContext); given AstEval(AstNode, boolean) with child is AstEval(AstNode, boolean) and deferred is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"jakarta.el.ValueReference AstEval.getValueReference(Bindings, ELContext)"})
  void testGetValueReference_givenAstEvalWithChildIsAstEvalAndDeferredIsTrue() {
    // Arrange
    AstEval astEval = new AstEval(new AstEval(new AstNull(), true), true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertNull(astEval.getValueReference(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstEval#getValueReference(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstEval#AstEval(AstNode, boolean)} with child is {@link AstNull} (default
   *       constructor) and deferred is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test getValueReference(Bindings, ELContext); given AstEval(AstNode, boolean) with child is AstNull (default constructor) and deferred is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"jakarta.el.ValueReference AstEval.getValueReference(Bindings, ELContext)"})
  void testGetValueReference_givenAstEvalWithChildIsAstNullAndDeferredIsTrue() {
    // Arrange
    AstEval astEval = new AstEval(new AstNull(), true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertNull(astEval.getValueReference(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstEval#getValueReference(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstIdentifier#AstIdentifier(String, int)} with {@code Name} and index is
   *       zero.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test getValueReference(Bindings, ELContext); given AstIdentifier(String, int) with 'Name' and index is zero; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"jakarta.el.ValueReference AstEval.getValueReference(Bindings, ELContext)"})
  void testGetValueReference_givenAstIdentifierWithNameAndIndexIsZero_thenReturnNull() {
    // Arrange
    AstEval astEval = new AstEval(new AstIdentifier("Name", 0), true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertNull(astEval.getValueReference(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstEval#eval(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstBoolean#AstBoolean(boolean)} with value is {@code false}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test eval(Bindings, ELContext); given AstBoolean(boolean) with value is 'false'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstEval.eval(Bindings, ELContext)"})
  void testEval_givenAstBooleanWithValueIsFalse_thenReturnFalse() {
    // Arrange
    AstEval astEval = new AstEval(new AstBoolean(false), true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertFalse((Boolean) astEval.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstEval#eval(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstBoolean#AstBoolean(boolean)} with value is {@code true}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test eval(Bindings, ELContext); given AstBoolean(boolean) with value is 'true'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstEval.eval(Bindings, ELContext)"})
  void testEval_givenAstBooleanWithValueIsTrue_thenReturnTrue() {
    // Arrange
    AstEval astEval = new AstEval(new AstBoolean(true), true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertTrue((Boolean) astEval.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstEval#eval(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstChoice#AstChoice(AstNode, AstNode, AstNode)} with question is {@link
   *       AstNull} (default constructor) and yes is {@link AstNull} (default constructor) and no is
   *       {@link AstNull} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link AstEval#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test eval(Bindings, ELContext); given AstChoice(AstNode, AstNode, AstNode) with question is AstNull (default constructor) and yes is AstNull (default constructor) and no is AstNull (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstEval.eval(Bindings, ELContext)"})
  void testEval_givenAstChoiceWithQuestionIsAstNullAndYesIsAstNullAndNoIsAstNull() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    AstChoice child = new AstChoice(question, yes, new AstNull());
    AstEval astEval = new AstEval(child, true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertNull(astEval.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstEval#eval(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstEval#AstEval(AstNode, boolean)} with child is {@link
   *       AstEval#AstEval(AstNode, boolean)} and deferred is {@code true}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test eval(Bindings, ELContext); given AstEval(AstNode, boolean) with child is AstEval(AstNode, boolean) and deferred is 'true'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstEval.eval(Bindings, ELContext)"})
  void testEval_givenAstEvalWithChildIsAstEvalAndDeferredIsTrue_thenReturnNull() {
    // Arrange
    AstEval astEval = new AstEval(new AstEval(new AstNull(), true), true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertNull(astEval.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstEval#eval(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstEval#AstEval(AstNode, boolean)} with child is {@link AstNull} (default
   *       constructor) and deferred is {@code true}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test eval(Bindings, ELContext); given AstEval(AstNode, boolean) with child is AstNull (default constructor) and deferred is 'true'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstEval.eval(Bindings, ELContext)"})
  void testEval_givenAstEvalWithChildIsAstNullAndDeferredIsTrue_thenReturnNull() {
    // Arrange
    AstEval astEval = new AstEval(new AstNull(), true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertNull(astEval.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstEval#toString()}.
   *
   * <ul>
   *   <li>Then return {@code ${...}}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#toString()}
   */
  @Test
  @DisplayName("Test toString(); then return '${...}'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String AstEval.toString()"})
  void testToString_thenReturnDollarSignLeftCurlyBracketDotDotDotRightCurlyBracket() {
    // Arrange, Act and Assert
    assertEquals("${...}", new AstEval(new AstNull(), false).toString());
  }

  /**
   * Test {@link AstEval#toString()}.
   *
   * <ul>
   *   <li>Then return {@code #{...}}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#toString()}
   */
  @Test
  @DisplayName("Test toString(); then return '#{...}'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String AstEval.toString()"})
  void testToString_thenReturnNumberSignLeftCurlyBracketDotDotDotRightCurlyBracket() {
    // Arrange, Act and Assert
    assertEquals("#{...}", new AstEval(new AstNull(), true).toString());
  }

  /**
   * Test {@link AstEval#appendStructure(StringBuilder, Bindings)}.
   *
   * <p>Method under test: {@link AstEval#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstEval.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure() {
    // Arrange
    AstEval astEval =
        new AstEval(new AstFunction("#{", 1, new AstParameters(new ArrayList<>())), true);
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astEval.appendStructure(b, bindings);

    // Assert
    assertEquals("Str#{#{()}", b.toString());
  }

  /**
   * Test {@link AstEval#appendStructure(StringBuilder, Bindings)}.
   *
   * <p>Method under test: {@link AstEval#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstEval.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure2() {
    // Arrange
    AstEval astEval =
        new AstEval(new AstFunction("#{", -1, new AstParameters(new ArrayList<>())), true);
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astEval.appendStructure(b, bindings);

    // Assert
    assertEquals("Str#{#{()}", b.toString());
  }

  /**
   * Test {@link AstEval#appendStructure(StringBuilder, Bindings)}.
   *
   * <p>Method under test: {@link AstEval#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstEval.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure3() {
    // Arrange
    AstEval astEval = new AstEval(new AstIdentifier("#{", 1), true);
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astEval.appendStructure(b, bindings);

    // Assert
    assertEquals("Str#{#{}", b.toString());
  }

  /**
   * Test {@link AstEval#appendStructure(StringBuilder, Bindings)}.
   *
   * <p>Method under test: {@link AstEval#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstEval.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure4() {
    // Arrange
    AstEval astEval = new AstEval(new AstIdentifier("#{", -1), true);
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astEval.appendStructure(b, bindings);

    // Assert
    assertEquals("Str#{#{}", b.toString());
  }

  /**
   * Test {@link AstEval#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code Str} toString is {@code
   *       Str#{<fn>()}}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'Str' toString is 'Str#{<fn>()}'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstEval.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithStrToStringIsStrFn() {
    // Arrange
    AstEval astEval =
        new AstEval(new AstFunction("#{", 0, new AstParameters(new ArrayList<>())), true);
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astEval.appendStructure(b, bindings);

    // Assert
    assertEquals("Str#{<fn>()}", b.toString());
  }

  /**
   * Test {@link AstEval#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code Str} toString is {@code
   *       Str#{null}}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'Str' toString is 'Str#{null}'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstEval.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithStrToStringIsStrNull() {
    // Arrange
    AstEval astEval = new AstEval(new AstNull(), true);
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astEval.appendStructure(b, bindings);

    // Assert
    assertEquals("Str#{null}", b.toString());
  }

  /**
   * Test {@link AstEval#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code Str} toString is {@code
   *       Str${null}}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'Str' toString is 'Str${null}'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstEval.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithStrToStringIsStrNull2() {
    // Arrange
    AstEval astEval = new AstEval(new AstNull(), false);
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astEval.appendStructure(b, bindings);

    // Assert
    assertEquals("Str${null}", b.toString());
  }

  /**
   * Test {@link AstEval#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code Str} toString is {@code
   *       Str#{#{null}}}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'Str' toString is 'Str#{#{null}}'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstEval.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithStrToStringIsStrNull3() {
    // Arrange
    AstEval astEval = new AstEval(new AstEval(new AstNull(), true), true);
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astEval.appendStructure(b, bindings);

    // Assert
    assertEquals("Str#{#{null}}", b.toString());
  }

  /**
   * Test {@link AstEval#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code Str} toString is {@code
   *       Str#{<var>}}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'Str' toString is 'Str#{<var>}'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstEval.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithStrToStringIsStrVar() {
    // Arrange
    AstEval astEval = new AstEval(new AstIdentifier("#{", 0), true);
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astEval.appendStructure(b, bindings);

    // Assert
    assertEquals("Str#{<var>}", b.toString());
  }

  /**
   * Test {@link AstEval#getMethodInfo(Bindings, ELContext, Class, Class[])}.
   *
   * <ul>
   *   <li>Given {@link AstEval#AstEval(AstNode, boolean)} with child is {@link
   *       AstEval#AstEval(AstNode, boolean)} and deferred is {@code true}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  @DisplayName(
      "Test getMethodInfo(Bindings, ELContext, Class, Class[]); given AstEval(AstNode, boolean) with child is AstEval(AstNode, boolean) and deferred is 'true'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "jakarta.el.MethodInfo AstEval.getMethodInfo(Bindings, ELContext, Class, Class[])"
  })
  void testGetMethodInfo_givenAstEvalWithChildIsAstEvalAndDeferredIsTrue_thenReturnNull() {
    // Arrange
    AstEval astEval = new AstEval(new AstEval(new AstNull(), true), true);
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

    // Act and Assert
    assertNull(astEval.getMethodInfo(bindings, context, returnType, new Class[] {forNameResult}));
  }

  /**
   * Test {@link AstEval#getMethodInfo(Bindings, ELContext, Class, Class[])}.
   *
   * <ul>
   *   <li>Given {@link AstEval#AstEval(AstNode, boolean)} with child is {@link AstNull} (default
   *       constructor) and deferred is {@code true}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  @DisplayName(
      "Test getMethodInfo(Bindings, ELContext, Class, Class[]); given AstEval(AstNode, boolean) with child is AstNull (default constructor) and deferred is 'true'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "jakarta.el.MethodInfo AstEval.getMethodInfo(Bindings, ELContext, Class, Class[])"
  })
  void testGetMethodInfo_givenAstEvalWithChildIsAstNullAndDeferredIsTrue_thenReturnNull() {
    // Arrange
    AstEval astEval = new AstEval(new AstNull(), true);
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

    // Act and Assert
    assertNull(astEval.getMethodInfo(bindings, context, returnType, new Class[] {forNameResult}));
  }

  /**
   * Test {@link AstEval#invoke(Bindings, ELContext, Class, Class[], Object[])}.
   *
   * <ul>
   *   <li>Given {@link AstBinary} {@link AstBinary#invoke(Bindings, ELContext, Class, Class[],
   *       Object[])} return {@code Invoke}.
   *   <li>Then return {@code Invoke}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  @DisplayName(
      "Test invoke(Bindings, ELContext, Class, Class[], Object[]); given AstBinary invoke(Bindings, ELContext, Class, Class[], Object[]) return 'Invoke'; then return 'Invoke'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstEval.invoke(Bindings, ELContext, Class, Class[], Object[])"})
  void testInvoke_givenAstBinaryInvokeReturnInvoke_thenReturnInvoke() {
    // Arrange
    AstBinary child = mock(AstBinary.class);
    when(child.invoke(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<Class<?>>any(),
            Mockito.<Class<Object>[]>any(),
            Mockito.<Object[]>any()))
        .thenReturn("Invoke");
    AstEval astEval = new AstEval(child, true);
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

    // Act
    Object actualInvokeResult =
        astEval.invoke(
            bindings,
            context,
            returnType,
            new Class[] {forNameResult},
            new Object[] {"Param Values"});

    // Assert
    verify(child)
        .invoke(
            isA(Bindings.class),
            isA(ELContext.class),
            isA(Class.class),
            isA(Class[].class),
            isA(Object[].class));
    assertEquals("Invoke", actualInvokeResult);
  }

  /**
   * Test {@link AstEval#getType(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstEval#AstEval(AstNode, boolean)} with child is {@link
   *       AstEval#AstEval(AstNode, boolean)} and deferred is {@code true}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test getType(Bindings, ELContext); given AstEval(AstNode, boolean) with child is AstEval(AstNode, boolean) and deferred is 'true'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class AstEval.getType(Bindings, ELContext)"})
  void testGetType_givenAstEvalWithChildIsAstEvalAndDeferredIsTrue_thenReturnNull() {
    // Arrange
    AstEval astEval = new AstEval(new AstEval(new AstNull(), true), true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertNull(astEval.getType(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstEval#getType(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstEval#AstEval(AstNode, boolean)} with child is {@link AstNull} (default
   *       constructor) and deferred is {@code true}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test getType(Bindings, ELContext); given AstEval(AstNode, boolean) with child is AstNull (default constructor) and deferred is 'true'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class AstEval.getType(Bindings, ELContext)"})
  void testGetType_givenAstEvalWithChildIsAstNullAndDeferredIsTrue_thenReturnNull() {
    // Arrange
    AstEval astEval = new AstEval(new AstNull(), true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertNull(astEval.getType(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstEval#getType(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstIdentifier#AstIdentifier(String, int)} with {@code Name} and index is
   *       zero.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test getType(Bindings, ELContext); given AstIdentifier(String, int) with 'Name' and index is zero; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class AstEval.getType(Bindings, ELContext)"})
  void testGetType_givenAstIdentifierWithNameAndIndexIsZero_thenReturnNull() {
    // Arrange
    AstEval astEval = new AstEval(new AstIdentifier("Name", 0), true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertNull(astEval.getType(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstEval#isLiteralText()}.
   *
   * <ul>
   *   <li>Given {@link AstEval#AstEval(AstNode, boolean)} with child is {@link
   *       AstEval#AstEval(AstNode, boolean)} and deferred is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#isLiteralText()}
   */
  @Test
  @DisplayName(
      "Test isLiteralText(); given AstEval(AstNode, boolean) with child is AstEval(AstNode, boolean) and deferred is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AstEval.isLiteralText()"})
  void testIsLiteralText_givenAstEvalWithChildIsAstEvalAndDeferredIsTrue() {
    // Arrange, Act and Assert
    assertFalse(new AstEval(new AstEval(new AstNull(), true), true).isLiteralText());
  }

  /**
   * Test {@link AstEval#isLiteralText()}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#isLiteralText()}
   */
  @Test
  @DisplayName("Test isLiteralText(); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AstEval.isLiteralText()"})
  void testIsLiteralText_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new AstEval(new AstNull(), true).isLiteralText());
  }

  /**
   * Test {@link AstEval#isReadOnly(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstEval#AstEval(AstNode, boolean)} with child is {@link
   *       AstEval#AstEval(AstNode, boolean)} and deferred is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test isReadOnly(Bindings, ELContext); given AstEval(AstNode, boolean) with child is AstEval(AstNode, boolean) and deferred is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AstEval.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly_givenAstEvalWithChildIsAstEvalAndDeferredIsTrue() {
    // Arrange
    AstEval astEval = new AstEval(new AstEval(new AstNull(), true), true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);
    SimpleContext context = new SimpleContext();

    // Act
    boolean actualIsReadOnlyResult = astEval.isReadOnly(bindings, context);

    // Assert
    assertFalse(context.isPropertyResolved());
    assertTrue(actualIsReadOnlyResult);
  }

  /**
   * Test {@link AstEval#isReadOnly(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstEval#AstEval(AstNode, boolean)} with child is {@link AstNull} (default
   *       constructor) and deferred is {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test isReadOnly(Bindings, ELContext); given AstEval(AstNode, boolean) with child is AstNull (default constructor) and deferred is 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AstEval.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly_givenAstEvalWithChildIsAstNullAndDeferredIsTrue() {
    // Arrange
    AstEval astEval = new AstEval(new AstNull(), true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);
    SimpleContext context = new SimpleContext();

    // Act
    boolean actualIsReadOnlyResult = astEval.isReadOnly(bindings, context);

    // Assert
    assertFalse(context.isPropertyResolved());
    assertTrue(actualIsReadOnlyResult);
  }

  /**
   * Test {@link AstEval#isReadOnly(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstIdentifier#AstIdentifier(String, int)} with {@code Name} and index is
   *       zero.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test isReadOnly(Bindings, ELContext); given AstIdentifier(String, int) with 'Name' and index is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AstEval.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly_givenAstIdentifierWithNameAndIndexIsZero() {
    // Arrange
    AstEval astEval = new AstEval(new AstIdentifier("Name", 0), true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);
    SimpleContext context = new SimpleContext();

    // Act
    boolean actualIsReadOnlyResult = astEval.isReadOnly(bindings, context);

    // Assert
    assertFalse(context.isPropertyResolved());
    assertTrue(actualIsReadOnlyResult);
  }

  /**
   * Test {@link AstEval#isReadOnly(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(Bindings, ELContext); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AstEval.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly_thenReturnFalse() {
    // Arrange
    AstEval astEval = new AstEval(new AstIdentifier("Name", 0), true);
    Method[] functions = new Method[] {null};
    ValueExpression[] variables = new ValueExpression[] {null};

    Bindings bindings = new Bindings(functions, variables);
    SimpleContext context = new SimpleContext();

    // Act and Assert
    assertFalse(astEval.isReadOnly(bindings, context));
    assertTrue(context.isPropertyResolved());
  }

  /**
   * Test {@link AstEval#setValue(Bindings, ELContext, Object)}.
   *
   * <ul>
   *   <li>Given {@link AstBinary} {@link AstBinary#setValue(Bindings, ELContext, Object)} does
   *       nothing.
   *   <li>Then calls {@link AstBinary#setValue(Bindings, ELContext, Object)}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName(
      "Test setValue(Bindings, ELContext, Object); given AstBinary setValue(Bindings, ELContext, Object) does nothing; then calls setValue(Bindings, ELContext, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstEval.setValue(Bindings, ELContext, Object)"})
  void testSetValue_givenAstBinarySetValueDoesNothing_thenCallsSetValue() {
    // Arrange
    AstBinary child = mock(AstBinary.class);
    doNothing()
        .when(child)
        .setValue(Mockito.<Bindings>any(), Mockito.<ELContext>any(), Mockito.<Object>any());
    AstEval astEval = new AstEval(child, true);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astEval.setValue(bindings, new SimpleContext(), "Value");

    // Assert
    verify(child).setValue(isA(Bindings.class), isA(ELContext.class), isA(Object.class));
  }

  /**
   * Test {@link AstEval#getChild(int)}.
   *
   * <ul>
   *   <li>When one.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); when one; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"AstNode AstEval.getChild(int)"})
  void testGetChild_whenOne_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new AstEval(new AstNull(), true).getChild(1));
  }

  /**
   * Test {@link AstEval#getChild(int)}.
   *
   * <ul>
   *   <li>When zero.
   *   <li>Then return {@link AstNull} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link AstEval#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); when zero; then return AstNull (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"AstNode AstEval.getChild(int)"})
  void testGetChild_whenZero_thenReturnAstNull() {
    // Arrange
    AstNull child = new AstNull();

    // Act and Assert
    assertSame(child, new AstEval(child, true).getChild(0));
  }
}
