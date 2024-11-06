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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import jakarta.el.ELContext;
import jakarta.el.ELException;
import jakarta.el.FunctionMapper;
import jakarta.el.ValueReference;
import jakarta.el.VariableMapper;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.core.el.juel.tree.FunctionNode;
import org.activiti.core.el.juel.tree.IdentifierNode;
import org.activiti.core.el.juel.tree.Tree;
import org.activiti.core.el.juel.tree.TreeBuilder;
import org.activiti.core.el.juel.tree.TreeBuilderException;
import org.activiti.core.el.juel.tree.TreeStore;
import org.activiti.core.el.juel.tree.impl.Cache;
import org.activiti.core.el.juel.tree.impl.ast.AstBinary;
import org.activiti.core.el.juel.tree.impl.ast.AstBinary.Operator;
import org.activiti.core.el.juel.tree.impl.ast.AstBracket;
import org.activiti.core.el.juel.tree.impl.ast.AstIdentifier;
import org.activiti.core.el.juel.tree.impl.ast.AstNull;
import org.activiti.core.el.juel.util.SimpleContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TreeValueExpressionDiffblueTest {
  /**
   * Test
   * {@link TreeValueExpression#TreeValueExpression(TreeStore, FunctionMapper, VariableMapper, TypeConverter, String, Class)}.
   * <ul>
   *   <li>When {@link FunctionMapper}.</li>
   *   <li>Then return ExpressionString is {@code Expr}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TreeValueExpression#TreeValueExpression(TreeStore, FunctionMapper, VariableMapper, TypeConverter, String, Class)}
   */
  @Test
  @DisplayName("Test new TreeValueExpression(TreeStore, FunctionMapper, VariableMapper, TypeConverter, String, Class); when FunctionMapper; then return ExpressionString is 'Expr'")
  void testNewTreeValueExpression_whenFunctionMapper_thenReturnExpressionStringIsExpr() throws TreeBuilderException {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();
    when(builder.build(Mockito.<String>any())).thenReturn(new Tree(root, functions, new ArrayList<>(), true));
    TreeStore store = new TreeStore(builder, new Cache(3));

    FunctionMapper functions2 = mock(FunctionMapper.class);
    VariableMapper variables = mock(VariableMapper.class);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    TreeValueExpression actualTreeValueExpression = new TreeValueExpression(store, functions2, variables, converter,
        "Expr", type);

    // Assert
    verify(builder).build(eq("Expr"));
    assertEquals("Expr", actualTreeValueExpression.getExpressionString());
    assertFalse(actualTreeValueExpression.isLeftValue());
    assertFalse(actualTreeValueExpression.isLiteralText());
    assertTrue(actualTreeValueExpression.isDeferred());
    Class<Object> expectedExpectedType = Object.class;
    assertEquals(expectedExpectedType, actualTreeValueExpression.getExpectedType());
  }

  /**
   * Test {@link TreeValueExpression#getType(ELContext)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TreeValueExpression#getType(ELContext)}
   */
  @Test
  @DisplayName("Test getType(ELContext); then return 'null'")
  void testGetType_thenReturnNull() throws ELException {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();
    when(builder.build(Mockito.<String>any())).thenReturn(new Tree(root, functions, new ArrayList<>(), true));
    TreeStore store = new TreeStore(builder, new Cache(3));

    FunctionMapper functions2 = mock(FunctionMapper.class);
    VariableMapper variables = mock(VariableMapper.class);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    TreeValueExpression treeValueExpression = new TreeValueExpression(store, functions2, variables, converter, "Expr",
        type);

    // Act
    Class<?> actualType = treeValueExpression.getType(new SimpleContext());

    // Assert
    verify(builder).build(eq("Expr"));
    assertNull(actualType);
  }

  /**
   * Test {@link TreeValueExpression#getValue(ELContext)}.
   * <p>
   * Method under test: {@link TreeValueExpression#getValue(ELContext)}
   */
  @Test
  @DisplayName("Test getValue(ELContext)")
  void testGetValue() throws ELException {
    // Arrange
    ArrayList<IdentifierNode> identifiers = new ArrayList<>();
    identifiers.add(new AstIdentifier("Name", 1));
    identifiers.add(new AstIdentifier("Name", 1));
    AstIdentifier root = new AstIdentifier("Name", 1);

    TreeBuilder builder = mock(TreeBuilder.class);
    when(builder.build(Mockito.<String>any())).thenReturn(new Tree(root, new ArrayList<>(), identifiers, true));
    TreeStore store = new TreeStore(builder, new Cache(3));

    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn("Convert");
    Class<Object> type = Object.class;
    VariableMapper variables = mock(VariableMapper.class);
    when(variables.resolveVariable(Mockito.<String>any()))
        .thenReturn(new ObjectValueExpression(converter, "Object", type));
    TypeConverter converter2 = mock(TypeConverter.class);
    when(converter2.convert(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn("Convert");
    FunctionMapper functions = mock(FunctionMapper.class);
    Class<Object> type2 = Object.class;
    TreeValueExpression treeValueExpression = new TreeValueExpression(store, functions, variables, converter2, "Expr",
        type2);

    // Act
    Object actualValue = treeValueExpression.getValue(new SimpleContext());

    // Assert
    verify(variables, atLeast(1)).resolveVariable(eq("Name"));
    verify(converter).convert(isA(Object.class), isA(Class.class));
    verify(converter2).convert(isA(Object.class), isA(Class.class));
    verify(builder).build(eq("Expr"));
    assertEquals("Convert", actualValue);
  }

  /**
   * Test {@link TreeValueExpression#getValue(ELContext)}.
   * <ul>
   *   <li>Then return {@code Convert}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TreeValueExpression#getValue(ELContext)}
   */
  @Test
  @DisplayName("Test getValue(ELContext); then return 'Convert'")
  void testGetValue_thenReturnConvert() throws ELException {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();
    when(builder.build(Mockito.<String>any())).thenReturn(new Tree(root, functions, new ArrayList<>(), true));
    TreeStore store = new TreeStore(builder, new Cache(3));

    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn("Convert");
    FunctionMapper functions2 = mock(FunctionMapper.class);
    VariableMapper variables = mock(VariableMapper.class);
    Class<Object> type = Object.class;
    TreeValueExpression treeValueExpression = new TreeValueExpression(store, functions2, variables, converter, "Expr",
        type);

    // Act
    Object actualValue = treeValueExpression.getValue(new SimpleContext());

    // Assert
    verify(converter).convert(isNull(), isA(Class.class));
    verify(builder).build(eq("Expr"));
    assertEquals("Convert", actualValue);
  }

  /**
   * Test {@link TreeValueExpression#isReadOnly(ELContext)}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TreeValueExpression#isReadOnly(ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(ELContext); then return 'true'")
  void testIsReadOnly_thenReturnTrue() throws ELException {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();
    when(builder.build(Mockito.<String>any())).thenReturn(new Tree(root, functions, new ArrayList<>(), true));
    TreeStore store = new TreeStore(builder, new Cache(3));

    FunctionMapper functions2 = mock(FunctionMapper.class);
    VariableMapper variables = mock(VariableMapper.class);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    TreeValueExpression treeValueExpression = new TreeValueExpression(store, functions2, variables, converter, "Expr",
        type);

    // Act
    boolean actualIsReadOnlyResult = treeValueExpression.isReadOnly(new SimpleContext());

    // Assert
    verify(builder).build(eq("Expr"));
    assertTrue(actualIsReadOnlyResult);
  }

  /**
   * Test {@link TreeValueExpression#setValue(ELContext, Object)}.
   * <ul>
   *   <li>Then {@link SimpleContext#SimpleContext()} PropertyResolved.</li>
   * </ul>
   * <p>
   * Method under test: {@link TreeValueExpression#setValue(ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(ELContext, Object); then SimpleContext() PropertyResolved")
  void testSetValue_thenSimpleContextPropertyResolved() throws ELException {
    // Arrange
    ArrayList<IdentifierNode> identifiers = new ArrayList<>();
    identifiers.add(new AstIdentifier("Name", 1));
    identifiers.add(new AstIdentifier("Name", 1));
    AstIdentifier root = new AstIdentifier("error.value.set.rvalue", 0);

    TreeBuilder builder = mock(TreeBuilder.class);
    when(builder.build(Mockito.<String>any())).thenReturn(new Tree(root, new ArrayList<>(), identifiers, true));
    TreeStore store = new TreeStore(builder, new Cache(3));

    VariableMapper variables = mock(VariableMapper.class);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    when(variables.resolveVariable(Mockito.<String>any()))
        .thenReturn(new ObjectValueExpression(converter, "Object", type));
    TypeConverter converter2 = mock(TypeConverter.class);
    when(converter2.convert(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn("Convert");
    FunctionMapper functions = mock(FunctionMapper.class);
    Class<Object> type2 = Object.class;
    TreeValueExpression treeValueExpression = new TreeValueExpression(store, functions, variables, converter2, "Expr",
        type2);
    SimpleContext context = new SimpleContext();

    // Act
    treeValueExpression.setValue(context, "Value");

    // Assert
    verify(variables, atLeast(1)).resolveVariable(eq("Name"));
    verify(converter2).convert(isA(Object.class), isA(Class.class));
    verify(builder).build(eq("Expr"));
    assertTrue(context.isPropertyResolved());
  }

  /**
   * Test {@link TreeValueExpression#isLiteralText()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TreeValueExpression#isLiteralText()}
   */
  @Test
  @DisplayName("Test isLiteralText(); then return 'false'")
  void testIsLiteralText_thenReturnFalse() throws TreeBuilderException {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();
    when(builder.build(Mockito.<String>any())).thenReturn(new Tree(root, functions, new ArrayList<>(), true));
    TreeStore store = new TreeStore(builder, new Cache(3));

    FunctionMapper functions2 = mock(FunctionMapper.class);
    VariableMapper variables = mock(VariableMapper.class);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    boolean actualIsLiteralTextResult = (new TreeValueExpression(store, functions2, variables, converter, "Expr", type))
        .isLiteralText();

    // Assert
    verify(builder).build(eq("Expr"));
    assertFalse(actualIsLiteralTextResult);
  }

  /**
   * Test {@link TreeValueExpression#getValueReference(ELContext)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TreeValueExpression#getValueReference(ELContext)}
   */
  @Test
  @DisplayName("Test getValueReference(ELContext); then return 'null'")
  void testGetValueReference_thenReturnNull() throws TreeBuilderException {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();
    when(builder.build(Mockito.<String>any())).thenReturn(new Tree(root, functions, new ArrayList<>(), true));
    TreeStore store = new TreeStore(builder, new Cache(3));

    FunctionMapper functions2 = mock(FunctionMapper.class);
    VariableMapper variables = mock(VariableMapper.class);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    TreeValueExpression treeValueExpression = new TreeValueExpression(store, functions2, variables, converter, "Expr",
        type);

    // Act
    ValueReference actualValueReference = treeValueExpression.getValueReference(new SimpleContext());

    // Assert
    verify(builder).build(eq("Expr"));
    assertNull(actualValueReference);
  }

  /**
   * Test {@link TreeValueExpression#isLeftValue()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TreeValueExpression#isLeftValue()}
   */
  @Test
  @DisplayName("Test isLeftValue(); then return 'false'")
  void testIsLeftValue_thenReturnFalse() throws TreeBuilderException {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();
    when(builder.build(Mockito.<String>any())).thenReturn(new Tree(root, functions, new ArrayList<>(), true));
    TreeStore store = new TreeStore(builder, new Cache(3));

    FunctionMapper functions2 = mock(FunctionMapper.class);
    VariableMapper variables = mock(VariableMapper.class);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    boolean actualIsLeftValueResult = (new TreeValueExpression(store, functions2, variables, converter, "Expr", type))
        .isLeftValue();

    // Assert
    verify(builder).build(eq("Expr"));
    assertFalse(actualIsLeftValueResult);
  }

  /**
   * Test {@link TreeValueExpression#isLeftValue()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TreeValueExpression#isLeftValue()}
   */
  @Test
  @DisplayName("Test isLeftValue(); then return 'true'")
  void testIsLeftValue_thenReturnTrue() throws TreeBuilderException {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull base = new AstNull();
    AstBracket root = new AstBracket(base, new AstNull(), true, true);

    ArrayList<FunctionNode> functions = new ArrayList<>();
    when(builder.build(Mockito.<String>any())).thenReturn(new Tree(root, functions, new ArrayList<>(), true));
    TreeStore store = new TreeStore(builder, new Cache(3));

    FunctionMapper functions2 = mock(FunctionMapper.class);
    VariableMapper variables = mock(VariableMapper.class);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    boolean actualIsLeftValueResult = (new TreeValueExpression(store, functions2, variables, converter, "Expr", type))
        .isLeftValue();

    // Assert
    verify(builder).build(eq("Expr"));
    assertTrue(actualIsLeftValueResult);
  }

  /**
   * Test {@link TreeValueExpression#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TreeValueExpression#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() throws TreeBuilderException {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();
    when(builder.build(Mockito.<String>any())).thenReturn(new Tree(root, functions, new ArrayList<>(), true));
    TreeStore store = new TreeStore(builder, new Cache(3));

    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    TreeValueExpression treeValueExpression = new TreeValueExpression(store, null, null, converter, "Expr", type);
    TreeBuilder builder2 = mock(TreeBuilder.class);
    AstNull root2 = new AstNull();
    ArrayList<FunctionNode> functions2 = new ArrayList<>();
    when(builder2.build(Mockito.<String>any())).thenReturn(new Tree(root2, functions2, new ArrayList<>(), true));
    TreeStore store2 = new TreeStore(builder2, new Cache(3));

    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type2 = Object.class;

    // Act and Assert
    assertNotEquals(treeValueExpression, new TreeValueExpression(store2, null, null, converter2, "Expr", type2));
  }

  /**
   * Test {@link TreeValueExpression#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TreeValueExpression#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() throws TreeBuilderException {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();
    when(builder.build(Mockito.<String>any())).thenReturn(new Tree(root, functions, new ArrayList<>(), true));
    TreeStore store = new TreeStore(builder, new Cache(3));

    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act and Assert
    assertNotEquals(new TreeValueExpression(store, null, null, converter, "Expr", type), 4);
  }

  /**
   * Test {@link TreeValueExpression#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TreeValueExpression#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() throws TreeBuilderException {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();
    when(builder.build(Mockito.<String>any())).thenReturn(new Tree(root, functions, new ArrayList<>(), true));
    TreeStore store = new TreeStore(builder, new Cache(3));

    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act and Assert
    assertNotEquals(new TreeValueExpression(store, null, null, converter, "Expr", type), null);
  }

  /**
   * Test {@link TreeValueExpression#dump(PrintWriter)}.
   * <p>
   * Method under test: {@link TreeValueExpression#dump(PrintWriter)}
   */
  @Test
  @DisplayName("Test dump(PrintWriter)")
  void testDump() throws TreeBuilderException {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();
    when(builder.build(Mockito.<String>any())).thenReturn(new Tree(root, functions, new ArrayList<>(), true));
    TreeStore store = new TreeStore(builder, new Cache(3));

    FunctionMapper functions2 = mock(FunctionMapper.class);
    VariableMapper variables = mock(VariableMapper.class);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    TreeValueExpression treeValueExpression = new TreeValueExpression(store, functions2, variables, converter, "Expr",
        type);

    // Act
    treeValueExpression.dump(new PrintWriter(new StringWriter()));

    // Assert
    verify(builder).build(eq("Expr"));
  }

  /**
   * Test {@link TreeValueExpression#dump(PrintWriter)}.
   * <ul>
   *   <li>Given {@link AstBinary#AstBinary(AstNode, AstNode, Operator)} with left
   * is {@link AstNull} (default constructor) and right is {@link AstNull}
   * (default constructor) and {@link Operator}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TreeValueExpression#dump(PrintWriter)}
   */
  @Test
  @DisplayName("Test dump(PrintWriter); given AstBinary(AstNode, AstNode, Operator) with left is AstNull (default constructor) and right is AstNull (default constructor) and Operator")
  void testDump_givenAstBinaryWithLeftIsAstNullAndRightIsAstNullAndOperator() throws TreeBuilderException {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull left = new AstNull();
    AstBinary root = new AstBinary(left, new AstNull(), mock(AstBinary.Operator.class));

    ArrayList<FunctionNode> functions = new ArrayList<>();
    when(builder.build(Mockito.<String>any())).thenReturn(new Tree(root, functions, new ArrayList<>(), true));
    TreeStore store = new TreeStore(builder, new Cache(3));

    FunctionMapper functions2 = mock(FunctionMapper.class);
    VariableMapper variables = mock(VariableMapper.class);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    TreeValueExpression treeValueExpression = new TreeValueExpression(store, functions2, variables, converter, "Expr",
        type);

    // Act
    treeValueExpression.dump(new PrintWriter(new StringWriter()));

    // Assert
    verify(builder).build(eq("Expr"));
  }
}
