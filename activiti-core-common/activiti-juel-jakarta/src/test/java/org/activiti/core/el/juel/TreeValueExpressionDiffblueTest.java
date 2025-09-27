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
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TreeValueExpressionDiffblueTest {
  /**
   * Test {@link TreeValueExpression#TreeValueExpression(TreeStore, FunctionMapper, VariableMapper,
   * TypeConverter, String, Class)}.
   *
   * <ul>
   *   <li>Then calls {@link VariableMapper#resolveVariable(String)}.
   * </ul>
   *
   * <p>Method under test: {@link TreeValueExpression#TreeValueExpression(TreeStore, FunctionMapper,
   * VariableMapper, TypeConverter, String, Class)}
   */
  @Test
  @DisplayName(
      "Test new TreeValueExpression(TreeStore, FunctionMapper, VariableMapper, TypeConverter, String, Class); then calls resolveVariable(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void TreeValueExpression.<init>(TreeStore, FunctionMapper, VariableMapper, TypeConverter, String, Class)"
  })
  void testNewTreeValueExpression_thenCallsResolveVariable() throws TreeBuilderException {
    // Arrange
    ArrayList<IdentifierNode> identifiers = new ArrayList<>();
    identifiers.add(new AstIdentifier("Name", 1));
    identifiers.add(new AstIdentifier("error.function.notfound", 1));
    AstNull root = new AstNull();

    Tree tree = new Tree(root, new ArrayList<>(), identifiers, true);

    TreeBuilder builder = mock(TreeBuilder.class);
    when(builder.build(Mockito.<String>any())).thenReturn(tree);
    TreeStore store = new TreeStore(builder, null);
    FunctionMapper functions = mock(FunctionMapper.class);

    VariableMapper variables = mock(VariableMapper.class);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    when(variables.resolveVariable(Mockito.<String>any())).thenReturn(objectValueExpression);
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type2 = Object.class;

    // Act
    TreeValueExpression actualTreeValueExpression =
        new TreeValueExpression(store, functions, variables, converter2, "Expr", type2);

    // Assert
    verify(variables, atLeast(1)).resolveVariable(Mockito.<String>any());
    verify(builder).build("Expr");
    assertEquals("Expr", actualTreeValueExpression.getExpressionString());
    assertFalse(actualTreeValueExpression.isLeftValue());
    assertFalse(actualTreeValueExpression.isLiteralText());
    assertTrue(actualTreeValueExpression.isDeferred());
    Class<Object> expectedExpectedType = Object.class;
    assertEquals(expectedExpectedType, actualTreeValueExpression.getExpectedType());
  }

  /**
   * Test {@link TreeValueExpression#TreeValueExpression(TreeStore, FunctionMapper, VariableMapper,
   * TypeConverter, String, Class)}.
   *
   * <ul>
   *   <li>Then return not Deferred.
   * </ul>
   *
   * <p>Method under test: {@link TreeValueExpression#TreeValueExpression(TreeStore, FunctionMapper,
   * VariableMapper, TypeConverter, String, Class)}
   */
  @Test
  @DisplayName(
      "Test new TreeValueExpression(TreeStore, FunctionMapper, VariableMapper, TypeConverter, String, Class); then return not Deferred")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void TreeValueExpression.<init>(TreeStore, FunctionMapper, VariableMapper, TypeConverter, String, Class)"
  })
  void testNewTreeValueExpression_thenReturnNotDeferred() throws TreeBuilderException {
    // Arrange
    ArrayList<IdentifierNode> identifiers = new ArrayList<>();
    identifiers.add(new AstIdentifier("Name", 1));
    identifiers.add(new AstIdentifier("error.function.notfound", 1));
    AstNull root = new AstNull();

    Tree tree = new Tree(root, new ArrayList<>(), identifiers, false);

    TreeBuilder builder = mock(TreeBuilder.class);
    when(builder.build(Mockito.<String>any())).thenReturn(tree);
    TreeStore store = new TreeStore(builder, null);
    FunctionMapper functions = mock(FunctionMapper.class);

    VariableMapper variables = mock(VariableMapper.class);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    when(variables.resolveVariable(Mockito.<String>any())).thenReturn(objectValueExpression);
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type2 = Object.class;

    // Act
    TreeValueExpression actualTreeValueExpression =
        new TreeValueExpression(store, functions, variables, converter2, "Expr", type2);

    // Assert
    verify(variables, atLeast(1)).resolveVariable(Mockito.<String>any());
    verify(builder).build("Expr");
    assertEquals("Expr", actualTreeValueExpression.getExpressionString());
    assertFalse(actualTreeValueExpression.isDeferred());
    assertFalse(actualTreeValueExpression.isLeftValue());
    assertFalse(actualTreeValueExpression.isLiteralText());
    Class<Object> expectedExpectedType = Object.class;
    assertEquals(expectedExpectedType, actualTreeValueExpression.getExpectedType());
  }

  /**
   * Test {@link TreeValueExpression#TreeValueExpression(TreeStore, FunctionMapper, VariableMapper,
   * TypeConverter, String, Class)}.
   *
   * <ul>
   *   <li>When {@link FunctionMapper}.
   *   <li>Then return Deferred.
   * </ul>
   *
   * <p>Method under test: {@link TreeValueExpression#TreeValueExpression(TreeStore, FunctionMapper,
   * VariableMapper, TypeConverter, String, Class)}
   */
  @Test
  @DisplayName(
      "Test new TreeValueExpression(TreeStore, FunctionMapper, VariableMapper, TypeConverter, String, Class); when FunctionMapper; then return Deferred")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void TreeValueExpression.<init>(TreeStore, FunctionMapper, VariableMapper, TypeConverter, String, Class)"
  })
  void testNewTreeValueExpression_whenFunctionMapper_thenReturnDeferred()
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
    Class<Object> type = Object.class;

    // Act
    TreeValueExpression actualTreeValueExpression =
        new TreeValueExpression(store, functions2, variables, converter, "Expr", type);

    // Assert
    verify(builder).build("Expr");
    assertEquals("Expr", actualTreeValueExpression.getExpressionString());
    assertFalse(actualTreeValueExpression.isLeftValue());
    assertFalse(actualTreeValueExpression.isLiteralText());
    assertTrue(actualTreeValueExpression.isDeferred());
    Class<Object> expectedExpectedType = Object.class;
    assertEquals(expectedExpectedType, actualTreeValueExpression.getExpectedType());
  }

  /**
   * Test {@link TreeValueExpression#TreeValueExpression(TreeStore, FunctionMapper, VariableMapper,
   * TypeConverter, String, Class)}.
   *
   * <ul>
   *   <li>When {@link FunctionMapper}.
   *   <li>Then return Deferred.
   * </ul>
   *
   * <p>Method under test: {@link TreeValueExpression#TreeValueExpression(TreeStore, FunctionMapper,
   * VariableMapper, TypeConverter, String, Class)}
   */
  @Test
  @DisplayName(
      "Test new TreeValueExpression(TreeStore, FunctionMapper, VariableMapper, TypeConverter, String, Class); when FunctionMapper; then return Deferred")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void TreeValueExpression.<init>(TreeStore, FunctionMapper, VariableMapper, TypeConverter, String, Class)"
  })
  void testNewTreeValueExpression_whenFunctionMapper_thenReturnDeferred2()
      throws TreeBuilderException {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();

    Tree tree = new Tree(root, functions, new ArrayList<>(), true);
    when(builder.build(Mockito.<String>any())).thenReturn(tree);
    TreeStore store = new TreeStore(builder, null);
    FunctionMapper functions2 = mock(FunctionMapper.class);
    VariableMapper variables = mock(VariableMapper.class);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    TreeValueExpression actualTreeValueExpression =
        new TreeValueExpression(store, functions2, variables, converter, "Expr", type);

    // Assert
    verify(builder).build("Expr");
    assertEquals("Expr", actualTreeValueExpression.getExpressionString());
    assertFalse(actualTreeValueExpression.isLeftValue());
    assertFalse(actualTreeValueExpression.isLiteralText());
    assertTrue(actualTreeValueExpression.isDeferred());
    Class<Object> expectedExpectedType = Object.class;
    assertEquals(expectedExpectedType, actualTreeValueExpression.getExpectedType());
  }

  /**
   * Test {@link TreeValueExpression#getType(ELContext)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link TreeValueExpression#getType(ELContext)}
   */
  @Test
  @DisplayName("Test getType(ELContext); then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class TreeValueExpression.getType(ELContext)"})
  void testGetType_thenReturnNull() throws ELException {
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
    Class<Object> type = Object.class;

    TreeValueExpression treeValueExpression =
        new TreeValueExpression(store, functions2, variables, converter, "Expr", type);

    // Act
    Class<?> actualType = treeValueExpression.getType(new SimpleContext());

    // Assert
    verify(builder).build("Expr");
    assertNull(actualType);
  }

  /**
   * Test {@link TreeValueExpression#getValue(ELContext)}.
   *
   * <p>Method under test: {@link TreeValueExpression#getValue(ELContext)}
   */
  @Test
  @DisplayName("Test getValue(ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TreeValueExpression.getValue(ELContext)"})
  void testGetValue() throws ELException {
    // Arrange
    ArrayList<IdentifierNode> identifiers = new ArrayList<>();
    identifiers.add(new AstIdentifier("Name", 1));
    identifiers.add(new AstIdentifier("Name", 1));
    AstIdentifier root = new AstIdentifier("Name", 1);

    Tree tree = new Tree(root, new ArrayList<>(), identifiers, true);

    TreeBuilder builder = mock(TreeBuilder.class);
    when(builder.build(Mockito.<String>any())).thenReturn(tree);
    TreeStore store = new TreeStore(builder, new Cache(3));

    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Object>>any()))
        .thenReturn("Convert");
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);

    VariableMapper variables = mock(VariableMapper.class);
    when(variables.resolveVariable(Mockito.<String>any())).thenReturn(objectValueExpression);

    TypeConverter converter2 = mock(TypeConverter.class);
    when(converter2.convert(Mockito.<Object>any(), Mockito.<Class<Object>>any()))
        .thenReturn("Convert");
    FunctionMapper functions = mock(FunctionMapper.class);
    Class<Object> type2 = Object.class;

    TreeValueExpression treeValueExpression =
        new TreeValueExpression(store, functions, variables, converter2, "Expr", type2);

    // Act
    Object actualValue = treeValueExpression.getValue(new SimpleContext());

    // Assert
    verify(variables, atLeast(1)).resolveVariable("Name");
    verify(converter).convert(isA(Object.class), isA(Class.class));
    verify(converter2).convert(isA(Object.class), isA(Class.class));
    verify(builder).build("Expr");
    assertEquals("Convert", actualValue);
  }

  /**
   * Test {@link TreeValueExpression#getValue(ELContext)}.
   *
   * <p>Method under test: {@link TreeValueExpression#getValue(ELContext)}
   */
  @Test
  @DisplayName("Test getValue(ELContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TreeValueExpression.getValue(ELContext)"})
  void testGetValue2() throws ELException {
    // Arrange
    ArrayList<IdentifierNode> identifiers = new ArrayList<>();
    identifiers.add(new AstIdentifier("Name", 1));
    identifiers.add(new AstIdentifier("Name", 1));
    AstIdentifier root = new AstIdentifier("Name", 1);

    Tree tree = new Tree(root, new ArrayList<>(), identifiers, true);

    TreeBuilder builder = mock(TreeBuilder.class);
    when(builder.build(Mockito.<String>any())).thenReturn(tree);
    TreeStore store = new TreeStore(builder, new Cache(3));

    TreeBuilder builder2 = mock(TreeBuilder.class);
    AstNull root2 = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();

    Tree tree2 = new Tree(root2, functions, new ArrayList<>(), true);
    when(builder2.build(Mockito.<String>any())).thenReturn(tree2);
    TreeStore store2 = new TreeStore(builder2, new Cache(3));

    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Object>>any()))
        .thenReturn("Convert");
    FunctionMapper functions2 = mock(FunctionMapper.class);
    VariableMapper variables = mock(VariableMapper.class);
    Class<Object> type = Object.class;

    TreeValueExpression treeValueExpression =
        new TreeValueExpression(store2, functions2, variables, converter, "Expr", type);

    VariableMapper variables2 = mock(VariableMapper.class);
    when(variables2.resolveVariable(Mockito.<String>any())).thenReturn(treeValueExpression);

    TypeConverter converter2 = mock(TypeConverter.class);
    when(converter2.convert(Mockito.<Object>any(), Mockito.<Class<Object>>any()))
        .thenReturn("Convert");
    FunctionMapper functions3 = mock(FunctionMapper.class);
    Class<Object> type2 = Object.class;

    TreeValueExpression treeValueExpression2 =
        new TreeValueExpression(store, functions3, variables2, converter2, "Expr", type2);

    // Act
    Object actualValue = treeValueExpression2.getValue(new SimpleContext());

    // Assert
    verify(variables2, atLeast(1)).resolveVariable("Name");
    verify(converter).convert(isNull(), isA(Class.class));
    verify(converter2).convert(isA(Object.class), isA(Class.class));
    verify(builder).build("Expr");
    verify(builder2).build("Expr");
    assertEquals("Convert", actualValue);
  }

  /**
   * Test {@link TreeValueExpression#getValue(ELContext)}.
   *
   * <ul>
   *   <li>Then return {@code Convert}.
   * </ul>
   *
   * <p>Method under test: {@link TreeValueExpression#getValue(ELContext)}
   */
  @Test
  @DisplayName("Test getValue(ELContext); then return 'Convert'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object TreeValueExpression.getValue(ELContext)"})
  void testGetValue_thenReturnConvert() throws ELException {
    // Arrange
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

    // Act
    Object actualValue = treeValueExpression.getValue(new SimpleContext());

    // Assert
    verify(converter).convert(isNull(), isA(Class.class));
    verify(builder).build("Expr");
    assertEquals("Convert", actualValue);
  }

  /**
   * Test {@link TreeValueExpression#isReadOnly(ELContext)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link TreeValueExpression#isReadOnly(ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(ELContext); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean TreeValueExpression.isReadOnly(ELContext)"})
  void testIsReadOnly_thenReturnTrue() throws ELException {
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
    Class<Object> type = Object.class;

    TreeValueExpression treeValueExpression =
        new TreeValueExpression(store, functions2, variables, converter, "Expr", type);

    // Act
    boolean actualIsReadOnlyResult = treeValueExpression.isReadOnly(new SimpleContext());

    // Assert
    verify(builder).build("Expr");
    assertTrue(actualIsReadOnlyResult);
  }

  /**
   * Test {@link TreeValueExpression#setValue(ELContext, Object)}.
   *
   * <ul>
   *   <li>Then {@link SimpleContext#SimpleContext()} PropertyResolved.
   * </ul>
   *
   * <p>Method under test: {@link TreeValueExpression#setValue(ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(ELContext, Object); then SimpleContext() PropertyResolved")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TreeValueExpression.setValue(ELContext, Object)"})
  void testSetValue_thenSimpleContextPropertyResolved() throws ELException {
    // Arrange
    ArrayList<IdentifierNode> identifiers = new ArrayList<>();
    identifiers.add(new AstIdentifier("Name", 1));
    identifiers.add(new AstIdentifier("Name", 1));
    AstIdentifier root = new AstIdentifier("error.value.set.rvalue", 0);

    Tree tree = new Tree(root, new ArrayList<>(), identifiers, true);

    TreeBuilder builder = mock(TreeBuilder.class);
    when(builder.build(Mockito.<String>any())).thenReturn(tree);
    TreeStore store = new TreeStore(builder, new Cache(3));

    VariableMapper variables = mock(VariableMapper.class);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    when(variables.resolveVariable(Mockito.<String>any())).thenReturn(objectValueExpression);

    TypeConverter converter2 = mock(TypeConverter.class);
    when(converter2.convert(Mockito.<Object>any(), Mockito.<Class<Object>>any()))
        .thenReturn("Convert");
    FunctionMapper functions = mock(FunctionMapper.class);
    Class<Object> type2 = Object.class;

    TreeValueExpression treeValueExpression =
        new TreeValueExpression(store, functions, variables, converter2, "Expr", type2);
    SimpleContext context = new SimpleContext();

    // Act
    treeValueExpression.setValue(context, "Value");

    // Assert
    verify(variables, atLeast(1)).resolveVariable("Name");
    verify(converter2).convert(isA(Object.class), isA(Class.class));
    verify(builder).build("Expr");
    assertTrue(context.isPropertyResolved());
  }

  /**
   * Test {@link TreeValueExpression#isLiteralText()}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link TreeValueExpression#isLiteralText()}
   */
  @Test
  @DisplayName("Test isLiteralText(); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean TreeValueExpression.isLiteralText()"})
  void testIsLiteralText_thenReturnFalse() throws TreeBuilderException {
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
    Class<Object> type = Object.class;

    TreeValueExpression treeValueExpression =
        new TreeValueExpression(store, functions2, variables, converter, "Expr", type);

    // Act
    boolean actualIsLiteralTextResult = treeValueExpression.isLiteralText();

    // Assert
    verify(builder).build("Expr");
    assertFalse(actualIsLiteralTextResult);
  }

  /**
   * Test {@link TreeValueExpression#getValueReference(ELContext)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link TreeValueExpression#getValueReference(ELContext)}
   */
  @Test
  @DisplayName("Test getValueReference(ELContext); then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ValueReference TreeValueExpression.getValueReference(ELContext)"})
  void testGetValueReference_thenReturnNull() throws TreeBuilderException {
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
    Class<Object> type = Object.class;

    TreeValueExpression treeValueExpression =
        new TreeValueExpression(store, functions2, variables, converter, "Expr", type);

    // Act
    ValueReference actualValueReference =
        treeValueExpression.getValueReference(new SimpleContext());

    // Assert
    verify(builder).build("Expr");
    assertNull(actualValueReference);
  }

  /**
   * Test {@link TreeValueExpression#isLeftValue()}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link TreeValueExpression#isLeftValue()}
   */
  @Test
  @DisplayName("Test isLeftValue(); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean TreeValueExpression.isLeftValue()"})
  void testIsLeftValue_thenReturnFalse() throws TreeBuilderException {
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
    Class<Object> type = Object.class;

    TreeValueExpression treeValueExpression =
        new TreeValueExpression(store, functions2, variables, converter, "Expr", type);

    // Act
    boolean actualIsLeftValueResult = treeValueExpression.isLeftValue();

    // Assert
    verify(builder).build("Expr");
    assertFalse(actualIsLeftValueResult);
  }

  /**
   * Test {@link TreeValueExpression#isLeftValue()}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link TreeValueExpression#isLeftValue()}
   */
  @Test
  @DisplayName("Test isLeftValue(); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean TreeValueExpression.isLeftValue()"})
  void testIsLeftValue_thenReturnTrue() throws TreeBuilderException {
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
    Class<Object> type = Object.class;

    TreeValueExpression treeValueExpression =
        new TreeValueExpression(store, functions2, variables, converter, "Expr", type);

    // Act
    boolean actualIsLeftValueResult = treeValueExpression.isLeftValue();

    // Assert
    verify(builder).build("Expr");
    assertTrue(actualIsLeftValueResult);
  }

  /**
   * Test {@link TreeValueExpression#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link TreeValueExpression#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean TreeValueExpression.equals(Object)",
    "int TreeValueExpression.hashCode()"
  })
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() throws TreeBuilderException {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();

    Tree tree = new Tree(root, functions, new ArrayList<>(), true);
    when(builder.build(Mockito.<String>any())).thenReturn(tree);
    TreeStore store = new TreeStore(builder, new Cache(3));
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    TreeValueExpression treeValueExpression =
        new TreeValueExpression(store, null, null, converter, "Expr", type);

    TreeBuilder builder2 = mock(TreeBuilder.class);
    AstNull root2 = new AstNull();
    ArrayList<FunctionNode> functions2 = new ArrayList<>();

    Tree tree2 = new Tree(root2, functions2, new ArrayList<>(), true);
    when(builder2.build(Mockito.<String>any())).thenReturn(tree2);
    TreeStore store2 = new TreeStore(builder2, new Cache(3));
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type2 = Object.class;

    // Act and Assert
    assertNotEquals(
        treeValueExpression,
        new TreeValueExpression(store2, null, null, converter2, "Expr", type2));
  }

  /**
   * Test {@link TreeValueExpression#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link TreeValueExpression#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean TreeValueExpression.equals(Object)",
    "int TreeValueExpression.hashCode()"
  })
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() throws TreeBuilderException {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();

    Tree tree = new Tree(root, functions, new ArrayList<>(), true);
    when(builder.build(Mockito.<String>any())).thenReturn(tree);
    TreeStore store = new TreeStore(builder, new Cache(3));
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act and Assert
    assertNotEquals(new TreeValueExpression(store, null, null, converter, "Expr", type), 4);
  }

  /**
   * Test {@link TreeValueExpression#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link TreeValueExpression#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean TreeValueExpression.equals(Object)",
    "int TreeValueExpression.hashCode()"
  })
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() throws TreeBuilderException {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();

    Tree tree = new Tree(root, functions, new ArrayList<>(), true);
    when(builder.build(Mockito.<String>any())).thenReturn(tree);
    TreeStore store = new TreeStore(builder, new Cache(3));
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act and Assert
    assertNotEquals(new TreeValueExpression(store, null, null, converter, "Expr", type), null);
  }

  /**
   * Test {@link TreeValueExpression#dump(PrintWriter)}.
   *
   * <p>Method under test: {@link TreeValueExpression#dump(PrintWriter)}
   */
  @Test
  @DisplayName("Test dump(PrintWriter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TreeValueExpression.dump(PrintWriter)"})
  void testDump() throws TreeBuilderException {
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
    Class<Object> type = Object.class;

    TreeValueExpression treeValueExpression =
        new TreeValueExpression(store, functions2, variables, converter, "Expr", type);

    // Act
    treeValueExpression.dump(new PrintWriter(new StringWriter()));

    // Assert
    verify(builder).build("Expr");
  }

  /**
   * Test {@link TreeValueExpression#dump(PrintWriter)}.
   *
   * <ul>
   *   <li>Given {@link AstBinary#AstBinary(AstNode, AstNode, Operator)} with left is {@link
   *       AstBinary#AstBinary(AstNode, AstNode, Operator)} and right is {@link AstNull} (default
   *       constructor) and {@link Operator}.
   * </ul>
   *
   * <p>Method under test: {@link TreeValueExpression#dump(PrintWriter)}
   */
  @Test
  @DisplayName(
      "Test dump(PrintWriter); given AstBinary(AstNode, AstNode, Operator) with left is AstBinary(AstNode, AstNode, Operator) and right is AstNull (default constructor) and Operator")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TreeValueExpression.dump(PrintWriter)"})
  void testDump_givenAstBinaryWithLeftIsAstBinaryAndRightIsAstNullAndOperator()
      throws TreeBuilderException {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull left = new AstNull();
    AstBinary left2 = new AstBinary(left, new AstNull(), mock(Operator.class));
    AstBinary root = new AstBinary(left2, new AstNull(), mock(Operator.class));
    ArrayList<FunctionNode> functions = new ArrayList<>();

    Tree tree = new Tree(root, functions, new ArrayList<>(), true);
    when(builder.build(Mockito.<String>any())).thenReturn(tree);
    TreeStore store = new TreeStore(builder, new Cache(3));
    FunctionMapper functions2 = mock(FunctionMapper.class);
    VariableMapper variables = mock(VariableMapper.class);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    TreeValueExpression treeValueExpression =
        new TreeValueExpression(store, functions2, variables, converter, "Expr", type);

    // Act
    treeValueExpression.dump(new PrintWriter(new StringWriter()));

    // Assert
    verify(builder).build("Expr");
  }

  /**
   * Test {@link TreeValueExpression#dump(PrintWriter)}.
   *
   * <ul>
   *   <li>Given {@link AstBinary#AstBinary(AstNode, AstNode, Operator)} with left is {@link
   *       AstNull} (default constructor) and right is {@link AstBinary#AstBinary(AstNode, AstNode,
   *       Operator)} and {@link Operator}.
   * </ul>
   *
   * <p>Method under test: {@link TreeValueExpression#dump(PrintWriter)}
   */
  @Test
  @DisplayName(
      "Test dump(PrintWriter); given AstBinary(AstNode, AstNode, Operator) with left is AstNull (default constructor) and right is AstBinary(AstNode, AstNode, Operator) and Operator")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TreeValueExpression.dump(PrintWriter)"})
  void testDump_givenAstBinaryWithLeftIsAstNullAndRightIsAstBinaryAndOperator()
      throws TreeBuilderException {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull left = new AstNull();
    AstNull left2 = new AstNull();
    AstBinary right = new AstBinary(left2, new AstNull(), mock(Operator.class));

    AstBinary root = new AstBinary(left, right, mock(Operator.class));
    ArrayList<FunctionNode> functions = new ArrayList<>();

    Tree tree = new Tree(root, functions, new ArrayList<>(), true);
    when(builder.build(Mockito.<String>any())).thenReturn(tree);
    TreeStore store = new TreeStore(builder, new Cache(3));
    FunctionMapper functions2 = mock(FunctionMapper.class);
    VariableMapper variables = mock(VariableMapper.class);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    TreeValueExpression treeValueExpression =
        new TreeValueExpression(store, functions2, variables, converter, "Expr", type);

    // Act
    treeValueExpression.dump(new PrintWriter(new StringWriter()));

    // Assert
    verify(builder).build("Expr");
  }

  /**
   * Test {@link TreeValueExpression#dump(PrintWriter)}.
   *
   * <ul>
   *   <li>Given {@link AstBinary#AstBinary(AstNode, AstNode, Operator)} with left is {@link
   *       AstNull} (default constructor) and right is {@link AstNull} (default constructor) and
   *       {@link Operator}.
   * </ul>
   *
   * <p>Method under test: {@link TreeValueExpression#dump(PrintWriter)}
   */
  @Test
  @DisplayName(
      "Test dump(PrintWriter); given AstBinary(AstNode, AstNode, Operator) with left is AstNull (default constructor) and right is AstNull (default constructor) and Operator")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TreeValueExpression.dump(PrintWriter)"})
  void testDump_givenAstBinaryWithLeftIsAstNullAndRightIsAstNullAndOperator()
      throws TreeBuilderException {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull left = new AstNull();
    AstBinary root = new AstBinary(left, new AstNull(), mock(Operator.class));
    ArrayList<FunctionNode> functions = new ArrayList<>();

    Tree tree = new Tree(root, functions, new ArrayList<>(), true);
    when(builder.build(Mockito.<String>any())).thenReturn(tree);
    TreeStore store = new TreeStore(builder, new Cache(3));
    FunctionMapper functions2 = mock(FunctionMapper.class);
    VariableMapper variables = mock(VariableMapper.class);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    TreeValueExpression treeValueExpression =
        new TreeValueExpression(store, functions2, variables, converter, "Expr", type);

    // Act
    treeValueExpression.dump(new PrintWriter(new StringWriter()));

    // Assert
    verify(builder).build("Expr");
  }
}
