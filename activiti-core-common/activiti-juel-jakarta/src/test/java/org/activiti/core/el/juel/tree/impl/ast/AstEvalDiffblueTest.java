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
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.el.ELContext;
import jakarta.el.ELException;
import jakarta.el.FunctionMapper;
import jakarta.el.MethodInfo;
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
import org.activiti.core.el.juel.tree.impl.ast.AstIdentifierTest.TestMethodExpression;
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
   * <p>Method under test: {@link AstEval#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstEval.eval(Bindings, ELContext)"})
  void testEval() throws ELException {
    // Arrange
    AstEval astEval = new AstEval(new AstIdentifier("Name", 0), true);

    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();

    Tree tree = new Tree(root, functions, new ArrayList<>(), true);
    when(builder.build(Mockito.<String>any())).thenReturn(tree);
    TreeStore store = new TreeStore(builder, new Cache(3));

    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Object>>any()))
        .thenReturn("Convert");
    FunctionMapper functions2 = mock(FunctionMapper.class);
    VariableMapper variables = mock(VariableMapper.class);
    Class<Object> type = Object.class;

    TreeValueExpression treeValueExpression =
        new TreeValueExpression(store, functions2, variables, converter, "Expr", type);
    ValueExpression[] variables2 = new ValueExpression[] {treeValueExpression};
    Method[] functions3 = new Method[] {null};

    Bindings bindings = new Bindings(functions3, variables2);

    // Act
    Object actualEvalResult = astEval.eval(bindings, new SimpleContext());

    // Assert
    verify(converter).convert(isNull(), isA(Class.class));
    verify(builder).build("Expr");
    assertEquals("Convert", actualEvalResult);
  }

  /**
   * Test {@link AstEval#eval(Bindings, ELContext)}.
   *
   * <p>Method under test: {@link AstEval#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstEval.eval(Bindings, ELContext)"})
  void testEval2() throws ELException {
    // Arrange
    AstEval astEval = new AstEval(new AstIdentifier("Name", 0), true);

    TreeBuilder builder = mock(TreeBuilder.class);
    AstEval root = new AstEval(new AstNull(), true);
    ArrayList<FunctionNode> functions = new ArrayList<>();

    Tree tree = new Tree(root, functions, new ArrayList<>(), true);
    when(builder.build(Mockito.<String>any())).thenReturn(tree);
    TreeStore store = new TreeStore(builder, new Cache(3));

    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Object>>any()))
        .thenReturn("Convert");
    FunctionMapper functions2 = mock(FunctionMapper.class);
    VariableMapper variables = mock(VariableMapper.class);
    Class<Object> type = Object.class;

    TreeValueExpression treeValueExpression =
        new TreeValueExpression(store, functions2, variables, converter, "Expr", type);
    ValueExpression[] variables2 = new ValueExpression[] {treeValueExpression};
    Method[] functions3 = new Method[] {null};

    Bindings bindings = new Bindings(functions3, variables2);

    // Act
    Object actualEvalResult = astEval.eval(bindings, new SimpleContext());

    // Assert
    verify(converter).convert(isNull(), isA(Class.class));
    verify(builder).build("Expr");
    assertEquals("Convert", actualEvalResult);
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
   * Test {@link AstEval#eval(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstIdentifier#AstIdentifier(String, int)} with {@code Name} and index is
   *       zero.
   *   <li>Then return {@code Convert}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test eval(Bindings, ELContext); given AstIdentifier(String, int) with 'Name' and index is zero; then return 'Convert'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstEval.eval(Bindings, ELContext)"})
  void testEval_givenAstIdentifierWithNameAndIndexIsZero_thenReturnConvert() throws ELException {
    // Arrange
    AstEval astEval = new AstEval(new AstIdentifier("Name", 0), true);

    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Object>>any()))
        .thenReturn("Convert");
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};
    Method[] functions = new Method[] {null};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    Object actualEvalResult = astEval.eval(bindings, new SimpleContext());

    // Assert
    verify(converter).convert(isA(Object.class), isA(Class.class));
    assertEquals("Convert", actualEvalResult);
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
  @MethodsUnderTest({"String AstEval.toString()"})
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
  @MethodsUnderTest({"String AstEval.toString()"})
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
    StringBuilder b = new StringBuilder("foo");
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
    assertEquals("foo#{#{()}", b.toString());
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
    StringBuilder b = new StringBuilder("foo");
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
    assertEquals("foo#{#{()}", b.toString());
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
    StringBuilder b = new StringBuilder("foo");
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
    assertEquals("foo#{#{}", b.toString());
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
    StringBuilder b = new StringBuilder("foo");
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
    assertEquals("foo#{#{}", b.toString());
  }

  /**
   * Test {@link AstEval#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo} toString is {@code
   *       foo#{<fn>()}}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foo#{<fn>()}'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstEval.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithFooToStringIsFooFn() {
    // Arrange
    AstEval astEval =
        new AstEval(new AstFunction("#{", 0, new AstParameters(new ArrayList<>())), true);
    StringBuilder b = new StringBuilder("foo");
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
    assertEquals("foo#{<fn>()}", b.toString());
  }

  /**
   * Test {@link AstEval#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo} toString is {@code
   *       foo#{null}}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foo#{null}'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstEval.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithFooToStringIsFooNull() {
    // Arrange
    AstEval astEval = new AstEval(new AstNull(), true);
    StringBuilder b = new StringBuilder("foo");
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
    assertEquals("foo#{null}", b.toString());
  }

  /**
   * Test {@link AstEval#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo} toString is {@code
   *       foo${null}}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foo${null}'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstEval.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithFooToStringIsFooNull2() {
    // Arrange
    AstEval astEval = new AstEval(new AstNull(), false);
    StringBuilder b = new StringBuilder("foo");
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
    assertEquals("foo${null}", b.toString());
  }

  /**
   * Test {@link AstEval#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo} toString is {@code
   *       foo#{#{null}}}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foo#{#{null}}'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstEval.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithFooToStringIsFooNull3() {
    // Arrange
    AstEval astEval = new AstEval(new AstEval(new AstNull(), true), true);
    StringBuilder b = new StringBuilder("foo");
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
    assertEquals("foo#{#{null}}", b.toString());
  }

  /**
   * Test {@link AstEval#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo} toString is {@code
   *       foo#{<var>}}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foo#{<var>}'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstEval.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithFooToStringIsFooVar() {
    // Arrange
    AstEval astEval = new AstEval(new AstIdentifier("#{", 0), true);
    StringBuilder b = new StringBuilder("foo");
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
    assertEquals("foo#{<var>}", b.toString());
  }

  /**
   * Test {@link AstEval#getMethodInfo(Bindings, ELContext, Class, Class[])}.
   *
   * <p>Method under test: {@link AstEval#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  @DisplayName("Test getMethodInfo(Bindings, ELContext, Class, Class[])")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"MethodInfo AstEval.getMethodInfo(Bindings, ELContext, Class, Class[])"})
  void testGetMethodInfo() throws ELException {
    // Arrange
    AstEval astEval = new AstEval(new AstIdentifier("Name", 0), true);

    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();

    Tree tree = new Tree(root, functions, new ArrayList<>(), true);
    when(builder.build(Mockito.<String>any())).thenReturn(tree);
    TreeStore store = new TreeStore(builder, new Cache(3));

    TestMethodExpression testMethodExpression = mock(TestMethodExpression.class);
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;
    Class<?>[] paramTypes = new Class[] {forNameResult};

    MethodInfo methodInfo = new MethodInfo("Name", returnType, paramTypes);
    when(testMethodExpression.getMethodInfo(Mockito.<ELContext>any())).thenReturn(methodInfo);

    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Object>>any()))
        .thenReturn(testMethodExpression);
    FunctionMapper functions2 = mock(FunctionMapper.class);
    VariableMapper variables = mock(VariableMapper.class);
    Class<Object> type = Object.class;

    TreeValueExpression treeValueExpression =
        new TreeValueExpression(store, functions2, variables, converter, "Expr", type);
    ValueExpression[] variables2 = new ValueExpression[] {treeValueExpression};
    Method[] functions3 = new Method[] {null};

    Bindings bindings = new Bindings(functions3, variables2);
    SimpleContext context = new SimpleContext();
    Class<Object> returnType2 = Object.class;
    Class<Object> forNameResult2 = Object.class;
    Class<?>[] paramTypes2 = new Class[] {forNameResult2};

    // Act
    MethodInfo actualMethodInfo =
        astEval.getMethodInfo(bindings, context, returnType2, paramTypes2);

    // Assert
    verify(converter).convert(isNull(), isA(Class.class));
    verify(builder).build("Expr");
    verify(testMethodExpression).getMethodInfo(isA(ELContext.class));
    assertEquals(1, paramTypes2.length);
    assertSame(methodInfo, actualMethodInfo);
    assertSame(forNameResult2, paramTypes2[0]);
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
  @MethodsUnderTest({"MethodInfo AstEval.getMethodInfo(Bindings, ELContext, Class, Class[])"})
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
    Class<?>[] paramTypes = new Class[] {forNameResult};

    // Act and Assert
    assertNull(astEval.getMethodInfo(bindings, context, returnType, paramTypes));
    assertEquals(1, paramTypes.length);
    Class<Object> expectedResultClass = Object.class;
    assertEquals(expectedResultClass, paramTypes[0]);
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
  @MethodsUnderTest({"MethodInfo AstEval.getMethodInfo(Bindings, ELContext, Class, Class[])"})
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
    Class<?>[] paramTypes = new Class[] {forNameResult};

    // Act and Assert
    assertNull(astEval.getMethodInfo(bindings, context, returnType, paramTypes));
    assertEquals(1, paramTypes.length);
    Class<Object> expectedResultClass = Object.class;
    assertEquals(expectedResultClass, paramTypes[0]);
  }

  /**
   * Test {@link AstEval#invoke(Bindings, ELContext, Class, Class[], Object[])}.
   *
   * <ul>
   *   <li>Given {@link TestMethodExpression} {@link TestMethodExpression#invoke(ELContext,
   *       Object[])} return {@code Invoke}.
   *   <li>Then return {@code Invoke}.
   * </ul>
   *
   * <p>Method under test: {@link AstEval#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  @DisplayName(
      "Test invoke(Bindings, ELContext, Class, Class[], Object[]); given TestMethodExpression invoke(ELContext, Object[]) return 'Invoke'; then return 'Invoke'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstEval.invoke(Bindings, ELContext, Class, Class[], Object[])"})
  void testInvoke_givenTestMethodExpressionInvokeReturnInvoke_thenReturnInvoke()
      throws ELException {
    // Arrange
    AstEval astEval = new AstEval(new AstIdentifier("error.method.invalid", 0), true);

    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();

    Tree tree = new Tree(root, functions, new ArrayList<>(), true);
    when(builder.build(Mockito.<String>any())).thenReturn(tree);
    TreeStore store = new TreeStore(builder, new Cache(3));

    TestMethodExpression testMethodExpression = mock(TestMethodExpression.class);
    when(testMethodExpression.invoke(Mockito.<ELContext>any(), Mockito.<Object[]>any()))
        .thenReturn("Invoke");

    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Object>>any()))
        .thenReturn(testMethodExpression);
    FunctionMapper functions2 = mock(FunctionMapper.class);
    VariableMapper variables = mock(VariableMapper.class);
    Class<Object> type = Object.class;

    TreeValueExpression treeValueExpression =
        new TreeValueExpression(
            store, functions2, variables, converter, "error.method.invalid", type);
    ValueExpression[] variables2 = new ValueExpression[] {treeValueExpression};
    Method[] functions3 = new Method[] {null};

    Bindings bindings = new Bindings(functions3, variables2);
    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;
    Class<?>[] paramTypes = new Class[] {forNameResult};

    // Act
    Object actualInvokeResult =
        astEval.invoke(bindings, context, returnType, paramTypes, new Object[] {"Param Values"});

    // Assert
    verify(converter).convert(isNull(), isA(Class.class));
    verify(builder).build("error.method.invalid");
    verify(testMethodExpression).invoke(isA(ELContext.class), isA(Object[].class));
    assertEquals("Invoke", actualInvokeResult);
    assertEquals(1, paramTypes.length);
    Class<Object> expectedResultClass = Object.class;
    assertEquals(expectedResultClass, paramTypes[0]);
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
