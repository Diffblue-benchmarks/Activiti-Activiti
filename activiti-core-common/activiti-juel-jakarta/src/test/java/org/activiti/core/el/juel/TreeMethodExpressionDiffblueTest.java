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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.el.ELException;
import jakarta.el.FunctionMapper;
import jakarta.el.VariableMapper;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.core.el.juel.tree.FunctionNode;
import org.activiti.core.el.juel.tree.Tree;
import org.activiti.core.el.juel.tree.TreeBuilder;
import org.activiti.core.el.juel.tree.TreeBuilderException;
import org.activiti.core.el.juel.tree.TreeStore;
import org.activiti.core.el.juel.tree.impl.Cache;
import org.activiti.core.el.juel.tree.impl.ast.AstBinary;
import org.activiti.core.el.juel.tree.impl.ast.AstBinary.Operator;
import org.activiti.core.el.juel.tree.impl.ast.AstBracket;
import org.activiti.core.el.juel.tree.impl.ast.AstNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TreeMethodExpressionDiffblueTest {
  /**
   * Test {@link TreeMethodExpression#TreeMethodExpression(TreeStore, FunctionMapper,
   * VariableMapper, TypeConverter, String, Class, Class[])}.
   *
   * <ul>
   *   <li>When {@link FunctionMapper}.
   *   <li>Then throw {@link ELException}.
   * </ul>
   *
   * <p>Method under test: {@link TreeMethodExpression#TreeMethodExpression(TreeStore,
   * FunctionMapper, VariableMapper, TypeConverter, String, Class, Class[])}
   */
  @Test
  @DisplayName(
      "Test new TreeMethodExpression(TreeStore, FunctionMapper, VariableMapper, TypeConverter, String, Class, Class[]); when FunctionMapper; then throw ELException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void TreeMethodExpression.<init>(TreeStore, FunctionMapper, VariableMapper, TypeConverter, String, Class, Class[])"
  })
  void testNewTreeMethodExpression_whenFunctionMapper_thenThrowELException()
      throws TreeBuilderException {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();

    Tree tree = new Tree(root, functions, new ArrayList<>(), true);
    when(builder.build(Mockito.<String>any())).thenReturn(tree);
    TreeStore store = new TreeStore(builder, new Cache(3));
    FunctionMapper functions2 = mock(FunctionMapper.class);
    VariableMapper variables = mock(VariableMapper.class);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;
    Class<?>[] paramTypes = new Class[] {forNameResult};

    // Act and Assert
    assertThrows(
        ELException.class,
        () ->
            new TreeMethodExpression(
                store, functions2, variables, converter, "Expr", returnType, paramTypes));
    verify(builder).build("Expr");
  }

  /**
   * Test {@link TreeMethodExpression#isLiteralText()}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link TreeMethodExpression#isLiteralText()}
   */
  @Test
  @DisplayName("Test isLiteralText(); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean TreeMethodExpression.isLiteralText()"})
  void testIsLiteralText_thenReturnFalse() throws TreeBuilderException {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull base = new AstNull();
    AstBracket root = new AstBracket(base, new AstNull(), true, true);
    ArrayList<FunctionNode> functions = new ArrayList<>();

    Tree tree = new Tree(root, functions, new ArrayList<>(), true);
    when(builder.build(Mockito.<String>any())).thenReturn(tree);
    TreeStore store = new TreeStore(builder, new Cache(3));
    FunctionMapper functions2 = mock(FunctionMapper.class);
    VariableMapper variables = mock(VariableMapper.class);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;
    Class<?>[] paramTypes = new Class[] {forNameResult};

    TreeMethodExpression treeMethodExpression =
        new TreeMethodExpression(
            store, functions2, variables, converter, "Expr", returnType, paramTypes);

    // Act
    boolean actualIsLiteralTextResult = treeMethodExpression.isLiteralText();

    // Assert
    verify(builder).build("Expr");
    assertFalse(actualIsLiteralTextResult);
  }

  /**
   * Test {@link TreeMethodExpression#isParametersProvided()}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link TreeMethodExpression#isParametersProvided()}
   */
  @Test
  @DisplayName("Test isParametersProvided(); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean TreeMethodExpression.isParametersProvided()"})
  void testIsParametersProvided_thenReturnFalse() throws TreeBuilderException {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull base = new AstNull();
    AstBracket root = new AstBracket(base, new AstNull(), true, true);
    ArrayList<FunctionNode> functions = new ArrayList<>();

    Tree tree = new Tree(root, functions, new ArrayList<>(), true);
    when(builder.build(Mockito.<String>any())).thenReturn(tree);
    TreeStore store = new TreeStore(builder, new Cache(3));
    FunctionMapper functions2 = mock(FunctionMapper.class);
    VariableMapper variables = mock(VariableMapper.class);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;
    Class<?>[] paramTypes = new Class[] {forNameResult};

    TreeMethodExpression treeMethodExpression =
        new TreeMethodExpression(
            store, functions2, variables, converter, "Expr", returnType, paramTypes);

    // Act
    boolean actualIsParametersProvidedResult = treeMethodExpression.isParametersProvided();

    // Assert
    verify(builder).build("Expr");
    assertFalse(actualIsParametersProvidedResult);
  }

  /**
   * Test {@link TreeMethodExpression#dump(PrintWriter)}.
   *
   * <p>Method under test: {@link TreeMethodExpression#dump(PrintWriter)}
   */
  @Test
  @DisplayName("Test dump(PrintWriter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TreeMethodExpression.dump(PrintWriter)"})
  void testDump() throws TreeBuilderException {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull base = new AstNull();
    AstBracket root = new AstBracket(base, new AstNull(), true, true);
    ArrayList<FunctionNode> functions = new ArrayList<>();

    Tree tree = new Tree(root, functions, new ArrayList<>(), true);
    when(builder.build(Mockito.<String>any())).thenReturn(tree);
    TreeStore store = new TreeStore(builder, new Cache(3));
    FunctionMapper functions2 = mock(FunctionMapper.class);
    VariableMapper variables = mock(VariableMapper.class);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;
    Class<?>[] paramTypes = new Class[] {forNameResult};

    TreeMethodExpression treeMethodExpression =
        new TreeMethodExpression(
            store, functions2, variables, converter, "Expr", returnType, paramTypes);

    // Act
    treeMethodExpression.dump(new PrintWriter(new StringWriter()));

    // Assert
    verify(builder).build("Expr");
  }

  /**
   * Test {@link TreeMethodExpression#dump(PrintWriter)}.
   *
   * <p>Method under test: {@link TreeMethodExpression#dump(PrintWriter)}
   */
  @Test
  @DisplayName("Test dump(PrintWriter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TreeMethodExpression.dump(PrintWriter)"})
  void testDump2() throws TreeBuilderException {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull left = new AstNull();
    AstBinary base = new AstBinary(left, new AstNull(), mock(Operator.class));
    AstBracket root = new AstBracket(base, new AstNull(), true, true);
    ArrayList<FunctionNode> functions = new ArrayList<>();

    Tree tree = new Tree(root, functions, new ArrayList<>(), true);
    when(builder.build(Mockito.<String>any())).thenReturn(tree);
    TreeStore store = new TreeStore(builder, new Cache(3));
    FunctionMapper functions2 = mock(FunctionMapper.class);
    VariableMapper variables = mock(VariableMapper.class);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;
    Class<?>[] paramTypes = new Class[] {forNameResult};

    TreeMethodExpression treeMethodExpression =
        new TreeMethodExpression(
            store, functions2, variables, converter, "Expr", returnType, paramTypes);

    // Act
    treeMethodExpression.dump(new PrintWriter(new StringWriter()));

    // Assert
    verify(builder).build("Expr");
  }

  /**
   * Test {@link TreeMethodExpression#dump(PrintWriter)}.
   *
   * <p>Method under test: {@link TreeMethodExpression#dump(PrintWriter)}
   */
  @Test
  @DisplayName("Test dump(PrintWriter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TreeMethodExpression.dump(PrintWriter)"})
  void testDump3() throws TreeBuilderException {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull base = new AstNull();
    AstNull left = new AstNull();
    AstBinary property = new AstBinary(left, new AstNull(), mock(Operator.class));

    AstBracket root = new AstBracket(base, property, true, true);
    ArrayList<FunctionNode> functions = new ArrayList<>();

    Tree tree = new Tree(root, functions, new ArrayList<>(), true);
    when(builder.build(Mockito.<String>any())).thenReturn(tree);
    TreeStore store = new TreeStore(builder, new Cache(3));
    FunctionMapper functions2 = mock(FunctionMapper.class);
    VariableMapper variables = mock(VariableMapper.class);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;
    Class<?>[] paramTypes = new Class[] {forNameResult};

    TreeMethodExpression treeMethodExpression =
        new TreeMethodExpression(
            store, functions2, variables, converter, "Expr", returnType, paramTypes);

    // Act
    treeMethodExpression.dump(new PrintWriter(new StringWriter()));

    // Assert
    verify(builder).build("Expr");
  }
}
