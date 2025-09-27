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
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.el.ELContext;
import jakarta.el.ELException;
import jakarta.el.FunctionMapper;
import jakarta.el.PropertyNotFoundException;
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
   *   <li>Given {@code Convert}.
   *   <li>Then calls {@link TypeConverter#convert(Object, Class)}.
   * </ul>
   *
   * <p>Method under test: {@link AstBracket#getProperty(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test getProperty(Bindings, ELContext); given 'Convert'; then calls convert(Object, Class)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstBracket.getProperty(Bindings, ELContext)"})
  void testGetProperty_givenConvert_thenCallsConvert() throws ELException {
    // Arrange
    AstNull base = new AstNull();
    AstIdentifier base2 = new AstIdentifier("Name", 0);
    AstBracket property = new AstBracket(base2, new AstNull(), true, true);

    AstBracket astBracket = new AstBracket(base, property, true, true);

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
    Object actualProperty = astBracket.getProperty(bindings, new SimpleContext());

    // Assert
    verify(converter).convert(isA(Object.class), isA(Class.class));
    assertNull(actualProperty);
  }

  /**
   * Test {@link AstBracket#getProperty(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Then calls {@link TreeBuilder#build(String)}.
   * </ul>
   *
   * <p>Method under test: {@link AstBracket#getProperty(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getProperty(Bindings, ELContext); then calls build(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstBracket.getProperty(Bindings, ELContext)"})
  void testGetProperty_thenCallsBuild() throws ELException {
    // Arrange
    AstNull base = new AstNull();
    AstIdentifier base2 = new AstIdentifier("Name", 0);
    AstBracket property = new AstBracket(base2, new AstNull(), true, true);

    AstBracket astBracket = new AstBracket(base, property, true, true);

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
    Object actualProperty = astBracket.getProperty(bindings, new SimpleContext());

    // Assert
    verify(converter).convert(isNull(), isA(Class.class));
    verify(builder).build("Expr");
    assertNull(actualProperty);
  }

  /**
   * Test {@link AstBracket#getProperty(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link AstBracket#getProperty(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getProperty(Bindings, ELContext); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstBracket.getProperty(Bindings, ELContext)"})
  void testGetProperty_thenReturnTrue() throws ELException {
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
    AstBracket property = new AstBracket(base, new AstNull(), true, false);
    AstBracket astBracket = new AstBracket(new AstNull(), property, true, true);
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
        () -> astBracket.getProperty(bindings, new SimpleContext()));
    verify(operator)
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
    StringBuilder b = new StringBuilder("foo");
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
    assertEquals("foonull()[null]", b.toString());
  }

  /**
   * Test {@link AstBracket#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo} toString is {@code
   *       foo<fn>()[null]}.
   * </ul>
   *
   * <p>Method under test: {@link AstBracket#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foo<fn>()[null]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstBracket.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithFooToStringIsFooFnNull() {
    // Arrange
    AstFunction base = new AstFunction("null", 0, new AstParameters(new ArrayList<>()));
    AstBracket astBracket = new AstBracket(base, new AstNull(), true, true);
    StringBuilder b = new StringBuilder("foo");
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
    assertEquals("foo<fn>()[null]", b.toString());
  }

  /**
   * Test {@link AstBracket#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo} toString is {@code
   *       foonull[null]}.
   * </ul>
   *
   * <p>Method under test: {@link AstBracket#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foonull[null]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstBracket.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoonullNull() {
    // Arrange
    AstNull base = new AstNull();
    AstBracket astBracket = new AstBracket(base, new AstNull(), true, true);
    StringBuilder b = new StringBuilder("foo");
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
    assertEquals("foonull[null]", b.toString());
  }

  /**
   * Test {@link AstBracket#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo} toString is {@code
   *       foonull()[null]}.
   * </ul>
   *
   * <p>Method under test: {@link AstBracket#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foonull()[null]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstBracket.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoonullNull2() {
    // Arrange
    AstFunction base = new AstFunction("null", 1, new AstParameters(new ArrayList<>()));
    AstBracket astBracket = new AstBracket(base, new AstNull(), true, true);
    StringBuilder b = new StringBuilder("foo");
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
    assertEquals("foonull()[null]", b.toString());
  }

  /**
   * Test {@link AstBracket#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo} toString is {@code
   *       foonull[null()]}.
   * </ul>
   *
   * <p>Method under test: {@link AstBracket#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foonull[null()]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstBracket.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoonullNull3() {
    // Arrange
    AstNull base = new AstNull();
    AstBracket astBracket =
        new AstBracket(
            base, new AstFunction("null", 1, new AstParameters(new ArrayList<>())), true, true);
    StringBuilder b = new StringBuilder("foo");
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
    assertEquals("foonull[null()]", b.toString());
  }

  /**
   * Test {@link AstBracket#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo} toString is {@code
   *       foonull[null][null]}.
   * </ul>
   *
   * <p>Method under test: {@link AstBracket#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foonull[null][null]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstBracket.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoonullNullNull() {
    // Arrange
    AstNull base = new AstNull();
    AstBracket base2 = new AstBracket(base, new AstNull(), true, true);
    AstBracket astBracket = new AstBracket(base2, new AstNull(), true, true);
    StringBuilder b = new StringBuilder("foo");
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
    assertEquals("foonull[null][null]", b.toString());
  }

  /**
   * Test {@link AstBracket#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo} toString is {@code
   *       foonull.null[null]}.
   * </ul>
   *
   * <p>Method under test: {@link AstBracket#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foonull.null[null]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstBracket.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoonullNullNull2() {
    // Arrange
    AstDot base = new AstDot(new AstNull(), "null", true);
    AstBracket astBracket = new AstBracket(base, new AstNull(), true, true);
    StringBuilder b = new StringBuilder("foo");
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
    assertEquals("foonull.null[null]", b.toString());
  }

  /**
   * Test {@link AstBracket#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo} toString is {@code
   *       foonull[null[null]]}.
   * </ul>
   *
   * <p>Method under test: {@link AstBracket#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foonull[null[null]]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstBracket.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoonullNullNull3() {
    // Arrange
    AstNull base = new AstNull();
    AstNull base2 = new AstNull();
    AstBracket property = new AstBracket(base2, new AstNull(), true, true);

    AstBracket astBracket = new AstBracket(base, property, true, true);
    StringBuilder b = new StringBuilder("foo");
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
    assertEquals("foonull[null[null]]", b.toString());
  }

  /**
   * Test {@link AstBracket#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo} toString is {@code
   *       foonull[null.null]}.
   * </ul>
   *
   * <p>Method under test: {@link AstBracket#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foonull[null.null]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstBracket.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoonullNullNull4() {
    // Arrange
    AstNull base = new AstNull();
    AstDot property = new AstDot(new AstNull(), "null", true);

    AstBracket astBracket = new AstBracket(base, property, true, true);
    StringBuilder b = new StringBuilder("foo");
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
    assertEquals("foonull[null.null]", b.toString());
  }

  /**
   * Test {@link AstBracket#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo} toString is {@code
   *       foonull().null[null]}.
   * </ul>
   *
   * <p>Method under test: {@link AstBracket#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foonull().null[null]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstBracket.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoonullNullNull5() {
    // Arrange
    AstDot base =
        new AstDot(new AstFunction("null", 1, new AstParameters(new ArrayList<>())), "null", true);
    AstBracket astBracket = new AstBracket(base, new AstNull(), true, true);
    StringBuilder b = new StringBuilder("foo");
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
    assertEquals("foonull().null[null]", b.toString());
  }

  /**
   * Test {@link AstBracket#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo} toString is {@code
   *       foonull[null].null[null]}.
   * </ul>
   *
   * <p>Method under test: {@link AstBracket#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foonull[null].null[null]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstBracket.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoonullNullNullNull() {
    // Arrange
    AstNull base = new AstNull();
    AstBracket base2 = new AstBracket(base, new AstNull(), true, true);
    AstDot base3 = new AstDot(base2, "null", true);
    AstBracket astBracket = new AstBracket(base3, new AstNull(), true, true);
    StringBuilder b = new StringBuilder("foo");
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
    assertEquals("foonull[null].null[null]", b.toString());
  }

  /**
   * Test {@link AstBracket#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo} toString is {@code
   *       foonull.null.null[null]}.
   * </ul>
   *
   * <p>Method under test: {@link AstBracket#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foonull.null.null[null]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstBracket.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithFooToStringIsFoonullNullNullNull2() {
    // Arrange
    AstDot base = new AstDot(new AstNull(), "null", true);
    AstDot base2 = new AstDot(base, "null", true);
    AstBracket astBracket = new AstBracket(base2, new AstNull(), true, true);
    StringBuilder b = new StringBuilder("foo");
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
    assertEquals("foonull.null.null[null]", b.toString());
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
