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
import static org.junit.jupiter.api.Assertions.assertSame;
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
import org.activiti.core.el.juel.tree.impl.ast.AstBracket;
import org.activiti.core.el.juel.tree.impl.ast.AstIdentifier;
import org.activiti.core.el.juel.tree.impl.ast.AstNull;
import org.activiti.core.el.juel.util.SimpleContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TreeValueExpressionDiffblueTest {
  /**
   * Method under test: {@link TreeValueExpression#getType(ELContext)}
   */
  @Test
  void testGetType() throws ELException {
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
   * Method under test: {@link TreeValueExpression#getValue(ELContext)}
   */
  @Test
  void testGetValue() throws ELException {
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
   * Method under test: {@link TreeValueExpression#getValue(ELContext)}
   */
  @Test
  void testGetValue2() throws ELException {
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
   * Method under test: {@link TreeValueExpression#isReadOnly(ELContext)}
   */
  @Test
  void testIsReadOnly() throws ELException {
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
   * Method under test: {@link TreeValueExpression#setValue(ELContext, Object)}
   */
  @Test
  void testSetValue() throws ELException {
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
   * Method under test: {@link TreeValueExpression#isLiteralText()}
   */
  @Test
  void testIsLiteralText() throws TreeBuilderException {
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
   * Method under test: {@link TreeValueExpression#getValueReference(ELContext)}
   */
  @Test
  void testGetValueReference() throws TreeBuilderException {
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
   * Method under test: {@link TreeValueExpression#isLeftValue()}
   */
  @Test
  void testIsLeftValue() throws TreeBuilderException {
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
   * Method under test: {@link TreeValueExpression#isLeftValue()}
   */
  @Test
  void testIsLeftValue2() throws TreeBuilderException {
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
   * Method under test: {@link TreeValueExpression#dump(PrintWriter)}
   */
  @Test
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
   * Method under test: {@link TreeValueExpression#dump(PrintWriter)}
   */
  @Test
  void testDump2() throws TreeBuilderException {
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

  /**
   * Method under test: {@link TreeValueExpression#equals(Object)}
   */
  @Test
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
   * Method under test: {@link TreeValueExpression#equals(Object)}
   */
  @Test
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
   * Method under test: {@link TreeValueExpression#equals(Object)}
   */
  @Test
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
   * Method under test:
   * {@link TreeValueExpression#TreeValueExpression(TreeStore, FunctionMapper, VariableMapper, TypeConverter, String, Class)}
   */
  @Test
  void testNewTreeValueExpression() throws TreeBuilderException {
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
    Class<?> expectedType = actualTreeValueExpression.getExpectedType();
    assertEquals(expectedExpectedType, expectedType);
    assertSame(type, expectedType);
  }
}
