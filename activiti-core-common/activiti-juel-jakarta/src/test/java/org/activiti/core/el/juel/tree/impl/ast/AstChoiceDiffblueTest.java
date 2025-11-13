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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.el.ELContext;
import jakarta.el.ELException;
import jakarta.el.FunctionMapper;
import jakarta.el.ValueExpression;
import jakarta.el.VariableMapper;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.TreeValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.core.el.juel.tree.Bindings;
import org.activiti.core.el.juel.tree.FunctionNode;
import org.activiti.core.el.juel.tree.Tree;
import org.activiti.core.el.juel.tree.TreeBuilder;
import org.activiti.core.el.juel.tree.TreeStore;
import org.activiti.core.el.juel.tree.impl.Cache;
import org.activiti.core.el.juel.tree.impl.ast.AstBinary.Operator;
import org.activiti.core.el.juel.util.SimpleContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AstChoiceDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link AstChoice#AstChoice(AstNode, AstNode, AstNode)}
   *   <li>{@link AstChoice#toString()}
   *   <li>{@link AstChoice#getCardinality()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AstChoice.<init>(AstNode, AstNode, AstNode)",
    "int AstChoice.getCardinality()",
    "String AstChoice.toString()"
  })
  void testGettersAndSetters() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    // Act
    AstChoice actualAstChoice = new AstChoice(question, yes, new AstNull());
    String actualToStringResult = actualAstChoice.toString();

    // Assert
    assertEquals("?", actualToStringResult);
    assertEquals(3, actualAstChoice.getCardinality());
  }

  /**
   * Test {@link AstChoice#eval(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstChoice#AstChoice(AstNode, AstNode, AstNode)} with question is {@link
   *       AstBinary#AstBinary(AstNode, AstNode, Operator)} and yes is {@link
   *       AstChoice#AstChoice(AstNode, AstNode, AstNode)} and no is {@link AstNull} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link AstChoice#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test eval(Bindings, ELContext); given AstChoice(AstNode, AstNode, AstNode) with question is AstBinary(AstNode, AstNode, Operator) and yes is AstChoice(AstNode, AstNode, AstNode) and no is AstNull (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstChoice.eval(Bindings, ELContext)"})
  void testEval_givenAstChoiceWithQuestionIsAstBinaryAndYesIsAstChoiceAndNoIsAstNull()
      throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary question = new AstBinary(left, new AstNull(), operator);
    AstNull question2 = new AstNull();
    AstNull yes = new AstNull();

    AstChoice yes2 = new AstChoice(question2, yes, new AstNull());

    AstChoice astChoice = new AstChoice(question, yes2, new AstNull());

    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Boolean.class))).thenReturn(true);
    Method[] functions = new Method[] {null};
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter2, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables, converter);

    // Act
    Object actualEvalResult = astChoice.eval(bindings, new SimpleContext());

    // Assert
    verify(converter, atLeast(1)).convert(Mockito.<Object>any(), isA(Class.class));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
    assertNull(actualEvalResult);
  }

  /**
   * Test {@link AstChoice#eval(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstChoice#AstChoice(AstNode, AstNode, AstNode)} with question is {@link
   *       AstBoolean#AstBoolean(boolean)} and yes is {@link AstNull} (default constructor) and no
   *       is {@link AstNull} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link AstChoice#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test eval(Bindings, ELContext); given AstChoice(AstNode, AstNode, AstNode) with question is AstBoolean(boolean) and yes is AstNull (default constructor) and no is AstNull (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstChoice.eval(Bindings, ELContext)"})
  void testEval_givenAstChoiceWithQuestionIsAstBooleanAndYesIsAstNullAndNoIsAstNull()
      throws ELException {
    // Arrange
    AstBoolean question = new AstBoolean(true);
    AstNull yes = new AstNull();

    AstChoice astChoice = new AstChoice(question, yes, new AstNull());
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertNull(astChoice.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstChoice#eval(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstChoice#AstChoice(AstNode, AstNode, AstNode)} with question is {@link
   *       AstChoice#AstChoice(AstNode, AstNode, AstNode)} and yes is {@link AstNull} (default
   *       constructor) and no is {@link AstNull} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link AstChoice#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test eval(Bindings, ELContext); given AstChoice(AstNode, AstNode, AstNode) with question is AstChoice(AstNode, AstNode, AstNode) and yes is AstNull (default constructor) and no is AstNull (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstChoice.eval(Bindings, ELContext)"})
  void testEval_givenAstChoiceWithQuestionIsAstChoiceAndYesIsAstNullAndNoIsAstNull()
      throws ELException {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    AstChoice question2 = new AstChoice(question, yes, new AstNull());
    AstNull yes2 = new AstNull();

    AstChoice astChoice = new AstChoice(question2, yes2, new AstNull());

    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Boolean.class))).thenReturn(true);
    Method[] functions = new Method[] {null};
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter2, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables, converter);

    // Act
    Object actualEvalResult = astChoice.eval(bindings, new SimpleContext());

    // Assert
    verify(converter, atLeast(1)).convert(isNull(), isA(Class.class));
    assertNull(actualEvalResult);
  }

  /**
   * Test {@link AstChoice#eval(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@code Convert}.
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)} return {@code
   *       Convert}.
   *   <li>Then return {@code Eval}.
   * </ul>
   *
   * <p>Method under test: {@link AstChoice#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test eval(Bindings, ELContext); given 'Convert'; when TypeConverter convert(Object, Class) return 'Convert'; then return 'Eval'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstChoice.eval(Bindings, ELContext)"})
  void testEval_givenConvert_whenTypeConverterConvertReturnConvert_thenReturnEval()
      throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary yes = new AstBinary(left, new AstNull(), operator);
    AstIdentifier question = new AstIdentifier("Name", 0);

    AstChoice astChoice = new AstChoice(question, yes, new AstNull());

    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Object.class))).thenReturn("Convert");
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    TypeConverter converter2 = mock(TypeConverter.class);
    when(converter2.convert(Mockito.<Object>any(), eq(Boolean.class))).thenReturn(true);
    Method[] functions = new Method[] {null};

    Bindings bindings = new Bindings(functions, variables, converter2);

    // Act
    Object actualEvalResult = astChoice.eval(bindings, new SimpleContext());

    // Assert
    verify(converter2).convert(isA(Object.class), isA(Class.class));
    verify(converter).convert(isA(Object.class), isA(Class.class));
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
    assertEquals("Eval", actualEvalResult);
  }

  /**
   * Test {@link AstChoice#eval(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Then calls {@link TreeBuilder#build(String)}.
   * </ul>
   *
   * <p>Method under test: {@link AstChoice#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext); then calls build(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstChoice.eval(Bindings, ELContext)"})
  void testEval_thenCallsBuild() throws ELException {
    // Arrange
    Operator operator = mock(Operator.class);
    when(operator.eval(
            Mockito.<Bindings>any(),
            Mockito.<ELContext>any(),
            Mockito.<AstNode>any(),
            Mockito.<AstNode>any()))
        .thenReturn("Eval");
    AstNull left = new AstNull();

    AstBinary yes = new AstBinary(left, new AstNull(), operator);
    AstIdentifier question = new AstIdentifier("Name", 0);

    AstChoice astChoice = new AstChoice(question, yes, new AstNull());

    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();

    Tree tree = new Tree(root, functions, new ArrayList<>(), true);
    when(builder.build(Mockito.<String>any())).thenReturn(tree);
    TreeStore store = new TreeStore(builder, new Cache(3));

    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Object.class))).thenReturn("Convert");
    FunctionMapper functions2 = mock(FunctionMapper.class);
    VariableMapper variables = mock(VariableMapper.class);
    Class<Object> type = Object.class;

    TreeValueExpression treeValueExpression =
        new TreeValueExpression(store, functions2, variables, converter, "Expr", type);
    ValueExpression[] variables2 = new ValueExpression[] {treeValueExpression};

    TypeConverter converter2 = mock(TypeConverter.class);
    when(converter2.convert(Mockito.<Object>any(), eq(Boolean.class))).thenReturn(true);
    Method[] functions3 = new Method[] {null};

    Bindings bindings = new Bindings(functions3, variables2, converter2);

    // Act
    Object actualEvalResult = astChoice.eval(bindings, new SimpleContext());

    // Assert
    verify(converter2).convert(isA(Object.class), isA(Class.class));
    verify(converter).convert(isNull(), isA(Class.class));
    verify(builder).build("Expr");
    verify(operator)
        .eval(isA(Bindings.class), isA(ELContext.class), isA(AstNode.class), isA(AstNode.class));
    assertEquals("Eval", actualEvalResult);
  }

  /**
   * Test {@link AstChoice#eval(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AstChoice#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext); then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstChoice.eval(Bindings, ELContext)"})
  void testEval_thenReturnNull() throws ELException {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    AstChoice astChoice = new AstChoice(question, yes, new AstNull());
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertNull(astChoice.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstChoice#eval(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AstChoice#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstChoice.eval(Bindings, ELContext)"})
  void testEval_thenReturnTrue() throws ELException {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    AstChoice astChoice = new AstChoice(question, yes, new AstBoolean(true));
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertTrue((Boolean) astChoice.eval(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstChoice#appendStructure(StringBuilder, Bindings)}.
   *
   * <p>Method under test: {@link AstChoice#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstChoice.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    AstChoice question2 = new AstChoice(question, yes, new AstNull());
    AstNull yes2 = new AstNull();

    AstChoice astChoice = new AstChoice(question2, yes2, new AstNull());
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astChoice.appendStructure(b, bindings);

    // Assert
    assertEquals("Strnull ? null : null ? null : null", b.toString());
  }

  /**
   * Test {@link AstChoice#appendStructure(StringBuilder, Bindings)}.
   *
   * <p>Method under test: {@link AstChoice#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstChoice.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure2() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    AstNull question2 = new AstNull();
    AstNull yes2 = new AstNull();

    AstChoice no = new AstChoice(question2, yes2, new AstNull());

    AstChoice astChoice = new AstChoice(question, yes, no);
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astChoice.appendStructure(b, bindings);

    // Assert
    assertEquals("Strnull ? null : null ? null : null", b.toString());
  }

  /**
   * Test {@link AstChoice#appendStructure(StringBuilder, Bindings)}.
   *
   * <p>Method under test: {@link AstChoice#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstChoice.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure3() {
    // Arrange
    AstFunction question = new AstFunction("null", -1, new AstParameters(new ArrayList<>()));
    AstNull yes = new AstNull();

    AstChoice astChoice = new AstChoice(question, yes, new AstNull());
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astChoice.appendStructure(b, bindings);

    // Assert
    assertEquals("Strnull() ? null : null", b.toString());
  }

  /**
   * Test {@link AstChoice#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code Str} toString is {@code
   *       Str<fn>() ? null : null}.
   * </ul>
   *
   * <p>Method under test: {@link AstChoice#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'Str' toString is 'Str<fn>() ? null : null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstChoice.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithStrToStringIsStrFnNullNull() {
    // Arrange
    AstFunction question = new AstFunction("null", 0, new AstParameters(new ArrayList<>()));
    AstNull yes = new AstNull();

    AstChoice astChoice = new AstChoice(question, yes, new AstNull());
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astChoice.appendStructure(b, bindings);

    // Assert
    assertEquals("Str<fn>() ? null : null", b.toString());
  }

  /**
   * Test {@link AstChoice#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code Str} toString is {@code
   *       Strnull ? null : null}.
   * </ul>
   *
   * <p>Method under test: {@link AstChoice#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'Str' toString is 'Strnull ? null : null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstChoice.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithStrToStringIsStrnullNullNull() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    AstChoice astChoice = new AstChoice(question, yes, new AstNull());
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astChoice.appendStructure(b, bindings);

    // Assert
    assertEquals("Strnull ? null : null", b.toString());
  }

  /**
   * Test {@link AstChoice#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code Str} toString is {@code
   *       Strnull() ? null : null}.
   * </ul>
   *
   * <p>Method under test: {@link AstChoice#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'Str' toString is 'Strnull() ? null : null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstChoice.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithStrToStringIsStrnullNullNull2() {
    // Arrange
    AstFunction question = new AstFunction("null", 1, new AstParameters(new ArrayList<>()));
    AstNull yes = new AstNull();

    AstChoice astChoice = new AstChoice(question, yes, new AstNull());
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astChoice.appendStructure(b, bindings);

    // Assert
    assertEquals("Strnull() ? null : null", b.toString());
  }

  /**
   * Test {@link AstChoice#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code Str} toString is {@code
   *       Strnull ? null() : null}.
   * </ul>
   *
   * <p>Method under test: {@link AstChoice#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'Str' toString is 'Strnull ? null() : null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstChoice.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithStrToStringIsStrnullNullNull3() {
    // Arrange
    AstNull question = new AstNull();
    AstFunction yes = new AstFunction("null", 1, new AstParameters(new ArrayList<>()));

    AstChoice astChoice = new AstChoice(question, yes, new AstNull());
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astChoice.appendStructure(b, bindings);

    // Assert
    assertEquals("Strnull ? null() : null", b.toString());
  }

  /**
   * Test {@link AstChoice#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code Str} toString is {@code
   *       Strnull ? null : null()}.
   * </ul>
   *
   * <p>Method under test: {@link AstChoice#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'Str' toString is 'Strnull ? null : null()'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstChoice.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithStrToStringIsStrnullNullNull4() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    AstChoice astChoice =
        new AstChoice(
            question, yes, new AstFunction("null", 1, new AstParameters(new ArrayList<>())));
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astChoice.appendStructure(b, bindings);

    // Assert
    assertEquals("Strnull ? null : null()", b.toString());
  }

  /**
   * Test {@link AstChoice#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code Str} toString is {@code
   *       Strnull ? null ? null : null : null}.
   * </ul>
   *
   * <p>Method under test: {@link AstChoice#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'Str' toString is 'Strnull ? null ? null : null : null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstChoice.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithStrToStringIsStrnullNullNullNullNull() {
    // Arrange
    AstNull question = new AstNull();
    AstNull question2 = new AstNull();
    AstNull yes = new AstNull();

    AstChoice yes2 = new AstChoice(question2, yes, new AstNull());

    AstChoice astChoice = new AstChoice(question, yes2, new AstNull());
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astChoice.appendStructure(b, bindings);

    // Assert
    assertEquals("Strnull ? null ? null : null : null", b.toString());
  }

  /**
   * Test {@link AstChoice#getChild(int)}.
   *
   * <ul>
   *   <li>When minus one.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AstChoice#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); when minus one; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"AstNode AstChoice.getChild(int)"})
  void testGetChild_whenMinusOne_thenReturnNull() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    AstChoice astChoice = new AstChoice(question, yes, new AstNull());

    // Act and Assert
    assertNull(astChoice.getChild(-1));
  }

  /**
   * Test {@link AstChoice#getChild(int)}.
   *
   * <ul>
   *   <li>When one.
   *   <li>Then return {@link AstNull} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link AstChoice#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); when one; then return AstNull (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"AstNode AstChoice.getChild(int)"})
  void testGetChild_whenOne_thenReturnAstNull() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    AstChoice astChoice = new AstChoice(question, yes, new AstNull());

    // Act and Assert
    assertSame(yes, astChoice.getChild(1));
  }

  /**
   * Test {@link AstChoice#getChild(int)}.
   *
   * <ul>
   *   <li>When two.
   *   <li>Then return {@link AstNull} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link AstChoice#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); when two; then return AstNull (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"AstNode AstChoice.getChild(int)"})
  void testGetChild_whenTwo_thenReturnAstNull() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();
    AstNull no = new AstNull();

    AstChoice astChoice = new AstChoice(question, yes, no);

    // Act and Assert
    assertSame(no, astChoice.getChild(2));
  }

  /**
   * Test {@link AstChoice#getChild(int)}.
   *
   * <ul>
   *   <li>When zero.
   *   <li>Then return {@link AstNull} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link AstChoice#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); when zero; then return AstNull (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"AstNode AstChoice.getChild(int)"})
  void testGetChild_whenZero_thenReturnAstNull() {
    // Arrange
    AstNull question = new AstNull();
    AstNull yes = new AstNull();

    AstChoice astChoice = new AstChoice(question, yes, new AstNull());

    // Act and Assert
    assertSame(question, astChoice.getChild(0));
  }
}
