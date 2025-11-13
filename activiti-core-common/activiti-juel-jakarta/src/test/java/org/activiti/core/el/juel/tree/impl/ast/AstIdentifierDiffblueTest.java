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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.el.ArrayELResolver;
import jakarta.el.BeanNameELResolver;
import jakarta.el.BeanNameResolver;
import jakarta.el.CompositeELResolver;
import jakarta.el.ELContext;
import jakarta.el.ELException;
import jakarta.el.FunctionMapper;
import jakarta.el.MethodNotFoundException;
import jakarta.el.PropertyNotFoundException;
import jakarta.el.PropertyNotWritableException;
import jakarta.el.StandardELContext;
import jakarta.el.ValueExpression;
import jakarta.el.ValueReference;
import jakarta.el.VariableMapper;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.activiti.core.el.juel.ExpressionFactoryImpl;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.TreeValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.core.el.juel.tree.Bindings;
import org.activiti.core.el.juel.tree.FunctionNode;
import org.activiti.core.el.juel.tree.Tree;
import org.activiti.core.el.juel.tree.TreeBuilder;
import org.activiti.core.el.juel.tree.TreeBuilderException;
import org.activiti.core.el.juel.tree.TreeStore;
import org.activiti.core.el.juel.tree.impl.Cache;
import org.activiti.core.el.juel.util.SimpleContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AstIdentifierDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <ul>
   *   <li>When {@code Name}.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link AstIdentifier#AstIdentifier(String, int)}
   *   <li>{@link AstIdentifier#getCardinality()}
   *   <li>{@link AstIdentifier#getIndex()}
   *   <li>{@link AstIdentifier#getName()}
   *   <li>{@link AstIdentifier#isLeftValue()}
   *   <li>{@link AstIdentifier#isLiteralText()}
   *   <li>{@link AstIdentifier#isMethodInvocation()}
   *   <li>{@link AstIdentifier#toString()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters; when 'Name'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AstIdentifier.<init>(String, int)",
    "void AstIdentifier.<init>(String, int, boolean)",
    "int AstIdentifier.getCardinality()",
    "int AstIdentifier.getIndex()",
    "String AstIdentifier.getName()",
    "boolean AstIdentifier.isLeftValue()",
    "boolean AstIdentifier.isLiteralText()",
    "boolean AstIdentifier.isMethodInvocation()",
    "String AstIdentifier.toString()"
  })
  void testGettersAndSetters_whenName() {
    // Arrange and Act
    AstIdentifier actualAstIdentifier = new AstIdentifier("Name", 1);
    int actualCardinality = actualAstIdentifier.getCardinality();
    int actualIndex = actualAstIdentifier.getIndex();
    String actualName = actualAstIdentifier.getName();
    boolean actualIsLeftValueResult = actualAstIdentifier.isLeftValue();
    boolean actualIsLiteralTextResult = actualAstIdentifier.isLiteralText();
    boolean actualIsMethodInvocationResult = actualAstIdentifier.isMethodInvocation();

    // Assert
    assertEquals("Name", actualName);
    assertEquals("Name", actualAstIdentifier.toString());
    assertEquals(0, actualCardinality);
    assertEquals(1, actualIndex);
    assertFalse(actualIsLiteralTextResult);
    assertFalse(actualIsMethodInvocationResult);
    assertTrue(actualIsLeftValueResult);
  }

  /**
   * Test getters and setters.
   *
   * <ul>
   *   <li>When {@code true}.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link AstIdentifier#AstIdentifier(String, int, boolean)}
   *   <li>{@link AstIdentifier#getCardinality()}
   *   <li>{@link AstIdentifier#getIndex()}
   *   <li>{@link AstIdentifier#getName()}
   *   <li>{@link AstIdentifier#isLeftValue()}
   *   <li>{@link AstIdentifier#isLiteralText()}
   *   <li>{@link AstIdentifier#isMethodInvocation()}
   *   <li>{@link AstIdentifier#toString()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters; when 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AstIdentifier.<init>(String, int)",
    "void AstIdentifier.<init>(String, int, boolean)",
    "int AstIdentifier.getCardinality()",
    "int AstIdentifier.getIndex()",
    "String AstIdentifier.getName()",
    "boolean AstIdentifier.isLeftValue()",
    "boolean AstIdentifier.isLiteralText()",
    "boolean AstIdentifier.isMethodInvocation()",
    "String AstIdentifier.toString()"
  })
  void testGettersAndSetters_whenTrue() {
    // Arrange and Act
    AstIdentifier actualAstIdentifier = new AstIdentifier("Name", 1, true);
    int actualCardinality = actualAstIdentifier.getCardinality();
    int actualIndex = actualAstIdentifier.getIndex();
    String actualName = actualAstIdentifier.getName();
    boolean actualIsLeftValueResult = actualAstIdentifier.isLeftValue();
    boolean actualIsLiteralTextResult = actualAstIdentifier.isLiteralText();
    boolean actualIsMethodInvocationResult = actualAstIdentifier.isMethodInvocation();

    // Assert
    assertEquals("Name", actualName);
    assertEquals("Name", actualAstIdentifier.toString());
    assertEquals(0, actualCardinality);
    assertEquals(1, actualIndex);
    assertFalse(actualIsLiteralTextResult);
    assertFalse(actualIsMethodInvocationResult);
    assertTrue(actualIsLeftValueResult);
  }

  /**
   * Test {@link AstIdentifier#getType(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstIdentifier#AstIdentifier(String, int)} with name is {@code null} and
   *       index is zero.
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test getType(Bindings, ELContext); given AstIdentifier(String, int) with name is 'null' and index is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class AstIdentifier.getType(Bindings, ELContext)"})
  void testGetType_givenAstIdentifierWithNameIsNullAndIndexIsZero() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier(null, 0);
    Method[] functions = new Method[] {null};
    ValueExpression[] variables = new ValueExpression[] {null};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astIdentifier.getType(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstIdentifier#getType(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link PropertyNotFoundException#PropertyNotFoundException()}.
   *   <li>Then calls {@link BeanNameResolver#isNameResolved(String)}.
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test getType(Bindings, ELContext); given PropertyNotFoundException(); then calls isNameResolved(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class AstIdentifier.getType(Bindings, ELContext)"})
  void testGetType_givenPropertyNotFoundException_thenCallsIsNameResolved() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Method[] functions = new Method[] {null};
    ValueExpression[] variables = new ValueExpression[] {null};

    Bindings bindings = new Bindings(functions, variables);

    BeanNameResolver beanNameResolver = mock(BeanNameResolver.class);
    when(beanNameResolver.isNameResolved(Mockito.<String>any()))
        .thenThrow(new PropertyNotFoundException());
    BeanNameELResolver resolver = new BeanNameELResolver(beanNameResolver);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astIdentifier.getType(bindings, new SimpleContext(resolver)));
    verify(beanNameResolver).isNameResolved("Name");
  }

  /**
   * Test {@link AstIdentifier#getType(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>When {@code Object}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getType(Bindings, ELContext); when 'java.lang.Object'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class AstIdentifier.getType(Bindings, ELContext)"})
  void testGetType_whenJavaLangObject_thenReturnNull() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);
    SimpleContext context = new SimpleContext();

    // Act and Assert
    assertNull(astIdentifier.getType(bindings, context));
    assertFalse(context.isPropertyResolved());
  }

  /**
   * Test {@link AstIdentifier#getType(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext(ELResolver)} with resolver is {@link
   *       ArrayELResolver#ArrayELResolver()}.
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test getType(Bindings, ELContext); when SimpleContext(ELResolver) with resolver is ArrayELResolver()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class AstIdentifier.getType(Bindings, ELContext)"})
  void testGetType_whenSimpleContextWithResolverIsArrayELResolver() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Method[] functions = new Method[] {null};
    ValueExpression[] variables = new ValueExpression[] {null};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astIdentifier.getType(bindings, new SimpleContext(new ArrayELResolver())));
  }

  /**
   * Test {@link AstIdentifier#getType(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext(ELResolver)} with resolver is {@link
   *       CompositeELResolver} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test getType(Bindings, ELContext); when SimpleContext(ELResolver) with resolver is CompositeELResolver (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class AstIdentifier.getType(Bindings, ELContext)"})
  void testGetType_whenSimpleContextWithResolverIsCompositeELResolver() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Method[] functions = new Method[] {null};
    ValueExpression[] variables = new ValueExpression[] {null};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astIdentifier.getType(bindings, new SimpleContext(new CompositeELResolver())));
  }

  /**
   * Test {@link AstIdentifier#getType(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext()}.
   *   <li>Then {@link SimpleContext#SimpleContext()} PropertyResolved.
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test getType(Bindings, ELContext); when SimpleContext(); then SimpleContext() PropertyResolved")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class AstIdentifier.getType(Bindings, ELContext)"})
  void testGetType_whenSimpleContext_thenSimpleContextPropertyResolved() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Method[] functions = new Method[] {null};
    ValueExpression[] variables = new ValueExpression[] {null};

    Bindings bindings = new Bindings(functions, variables);
    SimpleContext context = new SimpleContext();

    // Act
    Class<?> actualType = astIdentifier.getType(bindings, context);

    // Assert
    assertTrue(context.isPropertyResolved());
    Class<Object> expectedType = Object.class;
    assertEquals(expectedType, actualType);
  }

  /**
   * Test {@link AstIdentifier#getValueReference(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Then calls {@link TreeBuilder#build(String)}.
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getValueReference(Bindings, ELContext); then calls build(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ValueReference AstIdentifier.getValueReference(Bindings, ELContext)"})
  void testGetValueReference_thenCallsBuild() throws TreeBuilderException {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);

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
    ValueExpression[] variables2 = new ValueExpression[] {treeValueExpression};
    Method[] functions3 = new Method[] {null};

    Bindings bindings = new Bindings(functions3, variables2);

    // Act
    ValueReference actualValueReference =
        astIdentifier.getValueReference(bindings, new SimpleContext());

    // Assert
    verify(builder).build("Expr");
    assertNull(actualValueReference);
  }

  /**
   * Test {@link AstIdentifier#getValueReference(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getValueReference(Bindings, ELContext); then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ValueReference AstIdentifier.getValueReference(Bindings, ELContext)"})
  void testGetValueReference_thenReturnNull() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertNull(astIdentifier.getValueReference(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstIdentifier#getValueReference(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Then return Property is {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getValueReference(Bindings, ELContext); then return Property is 'Name'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ValueReference AstIdentifier.getValueReference(Bindings, ELContext)"})
  void testGetValueReference_thenReturnPropertyIsName() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Method[] functions = new Method[] {null};
    ValueExpression[] variables = new ValueExpression[] {null};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    ValueReference actualValueReference =
        astIdentifier.getValueReference(bindings, new SimpleContext());

    // Assert
    assertEquals("Name", actualValueReference.getProperty());
    assertNull(actualValueReference.getBase());
  }

  /**
   * Test {@link AstIdentifier#eval(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Then calls {@link TreeBuilder#build(String)}.
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext); then calls build(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstIdentifier.eval(Bindings, ELContext)"})
  void testEval_thenCallsBuild() throws ELException {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);

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
    Method[] functions3 = new Method[] {null};

    Bindings bindings = new Bindings(functions3, variables2);

    // Act
    Object actualEvalResult = astIdentifier.eval(bindings, new SimpleContext());

    // Assert
    verify(converter).convert(isNull(), isA(Class.class));
    verify(builder).build("Expr");
    assertEquals("Convert", actualEvalResult);
  }

  /**
   * Test {@link AstIdentifier#eval(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Then return {@code Convert}.
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext); then return 'Convert'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstIdentifier.eval(Bindings, ELContext)"})
  void testEval_thenReturnConvert() throws ELException {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);

    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Object.class))).thenReturn("Convert");
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};
    Method[] functions = new Method[] {null};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    Object actualEvalResult = astIdentifier.eval(bindings, new SimpleContext());

    // Assert
    verify(converter).convert(isA(Object.class), isA(Class.class));
    assertEquals("Convert", actualEvalResult);
  }

  /**
   * Test {@link AstIdentifier#setValue(Bindings, ELContext, Object)}.
   *
   * <p>Method under test: {@link AstIdentifier#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstIdentifier.setValue(Bindings, ELContext, Object)"})
  void testSetValue() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Method[] functions = new Method[] {null};
    ValueExpression[] variables = new ValueExpression[] {null};

    Bindings bindings = new Bindings(functions, variables);
    StandardELContext context = new StandardELContext(new ExpressionFactoryImpl());

    // Act
    astIdentifier.setValue(bindings, context, "Value");

    // Assert
    assertTrue(context.isPropertyResolved());
  }

  /**
   * Test {@link AstIdentifier#setValue(Bindings, ELContext, Object)}.
   *
   * <ul>
   *   <li>Given {@link AstIdentifier#AstIdentifier(String, int)} with name is {@code null} and
   *       index is zero.
   *   <li>When {@link SimpleContext#SimpleContext()}.
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName(
      "Test setValue(Bindings, ELContext, Object); given AstIdentifier(String, int) with name is 'null' and index is zero; when SimpleContext()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstIdentifier.setValue(Bindings, ELContext, Object)"})
  void testSetValue_givenAstIdentifierWithNameIsNullAndIndexIsZero_whenSimpleContext() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier(null, 0);
    Method[] functions = new Method[] {null};
    ValueExpression[] variables = new ValueExpression[] {null};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astIdentifier.setValue(bindings, new SimpleContext(), "Value"));
  }

  /**
   * Test {@link AstIdentifier#setValue(Bindings, ELContext, Object)}.
   *
   * <ul>
   *   <li>Then {@link SimpleContext#SimpleContext(ELResolver)} with resolver is {@link
   *       BeanNameELResolver#BeanNameELResolver(BeanNameResolver)} PropertyResolved.
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName(
      "Test setValue(Bindings, ELContext, Object); then SimpleContext(ELResolver) with resolver is BeanNameELResolver(BeanNameResolver) PropertyResolved")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstIdentifier.setValue(Bindings, ELContext, Object)"})
  void testSetValue_thenSimpleContextWithResolverIsBeanNameELResolverPropertyResolved()
      throws PropertyNotWritableException {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Method[] functions = new Method[] {null};
    ValueExpression[] variables = new ValueExpression[] {null};

    Bindings bindings = new Bindings(functions, variables);

    BeanNameResolver beanNameResolver = mock(BeanNameResolver.class);
    doNothing().when(beanNameResolver).setBeanValue(Mockito.<String>any(), Mockito.<Object>any());
    when(beanNameResolver.isReadOnly(Mockito.<String>any())).thenReturn(true);
    when(beanNameResolver.isNameResolved(Mockito.<String>any())).thenReturn(true);
    BeanNameELResolver resolver = new BeanNameELResolver(beanNameResolver);
    SimpleContext context = new SimpleContext(resolver);

    // Act
    astIdentifier.setValue(bindings, context, "Value");

    // Assert
    verify(beanNameResolver, atLeast(1)).isNameResolved("Name");
    verify(beanNameResolver).isReadOnly("Name");
    verify(beanNameResolver).setBeanValue(eq("Name"), isA(Object.class));
    assertTrue(context.isPropertyResolved());
  }

  /**
   * Test {@link AstIdentifier#setValue(Bindings, ELContext, Object)}.
   *
   * <ul>
   *   <li>When {@link BeanNameResolver} {@link BeanNameResolver#isNameResolved(String)} throw
   *       {@link PropertyNotFoundException#PropertyNotFoundException()}.
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName(
      "Test setValue(Bindings, ELContext, Object); when BeanNameResolver isNameResolved(String) throw PropertyNotFoundException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstIdentifier.setValue(Bindings, ELContext, Object)"})
  void testSetValue_whenBeanNameResolverIsNameResolvedThrowPropertyNotFoundException() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Method[] functions = new Method[] {null};
    ValueExpression[] variables = new ValueExpression[] {null};

    Bindings bindings = new Bindings(functions, variables);

    BeanNameResolver beanNameResolver = mock(BeanNameResolver.class);
    when(beanNameResolver.isNameResolved(Mockito.<String>any()))
        .thenThrow(new PropertyNotFoundException());
    BeanNameELResolver resolver = new BeanNameELResolver(beanNameResolver);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astIdentifier.setValue(bindings, new SimpleContext(resolver), "Value"));
    verify(beanNameResolver).isNameResolved("Name");
  }

  /**
   * Test {@link AstIdentifier#setValue(Bindings, ELContext, Object)}.
   *
   * <ul>
   *   <li>When {@link BeanNameResolver} {@link BeanNameResolver#setBeanValue(String, Object)} throw
   *       {@link PropertyNotFoundException#PropertyNotFoundException()}.
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName(
      "Test setValue(Bindings, ELContext, Object); when BeanNameResolver setBeanValue(String, Object) throw PropertyNotFoundException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstIdentifier.setValue(Bindings, ELContext, Object)"})
  void testSetValue_whenBeanNameResolverSetBeanValueThrowPropertyNotFoundException()
      throws PropertyNotWritableException {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Method[] functions = new Method[] {null};
    ValueExpression[] variables = new ValueExpression[] {null};

    Bindings bindings = new Bindings(functions, variables);

    BeanNameResolver beanNameResolver = mock(BeanNameResolver.class);
    doThrow(new PropertyNotFoundException())
        .when(beanNameResolver)
        .setBeanValue(Mockito.<String>any(), Mockito.<Object>any());
    when(beanNameResolver.isReadOnly(Mockito.<String>any())).thenReturn(true);
    when(beanNameResolver.isNameResolved(Mockito.<String>any())).thenReturn(true);
    BeanNameELResolver resolver = new BeanNameELResolver(beanNameResolver);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astIdentifier.setValue(bindings, new SimpleContext(resolver), "Value"));
    verify(beanNameResolver, atLeast(1)).isNameResolved("Name");
    verify(beanNameResolver).isReadOnly("Name");
    verify(beanNameResolver).setBeanValue(eq("Name"), isA(Object.class));
  }

  /**
   * Test {@link AstIdentifier#setValue(Bindings, ELContext, Object)}.
   *
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext(ELResolver)} with resolver is {@link
   *       ArrayELResolver#ArrayELResolver()}.
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName(
      "Test setValue(Bindings, ELContext, Object); when SimpleContext(ELResolver) with resolver is ArrayELResolver()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstIdentifier.setValue(Bindings, ELContext, Object)"})
  void testSetValue_whenSimpleContextWithResolverIsArrayELResolver() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Method[] functions = new Method[] {null};
    ValueExpression[] variables = new ValueExpression[] {null};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astIdentifier.setValue(bindings, new SimpleContext(new ArrayELResolver()), "Value"));
  }

  /**
   * Test {@link AstIdentifier#setValue(Bindings, ELContext, Object)}.
   *
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext(ELResolver)} with resolver is {@link
   *       CompositeELResolver} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName(
      "Test setValue(Bindings, ELContext, Object); when SimpleContext(ELResolver) with resolver is CompositeELResolver (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstIdentifier.setValue(Bindings, ELContext, Object)"})
  void testSetValue_whenSimpleContextWithResolverIsCompositeELResolver() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Method[] functions = new Method[] {null};
    ValueExpression[] variables = new ValueExpression[] {null};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () ->
            astIdentifier.setValue(
                bindings, new SimpleContext(new CompositeELResolver()), "Value"));
  }

  /**
   * Test {@link AstIdentifier#setValue(Bindings, ELContext, Object)}.
   *
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext()}.
   *   <li>Then {@link SimpleContext#SimpleContext()} PropertyResolved.
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName(
      "Test setValue(Bindings, ELContext, Object); when SimpleContext(); then SimpleContext() PropertyResolved")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstIdentifier.setValue(Bindings, ELContext, Object)"})
  void testSetValue_whenSimpleContext_thenSimpleContextPropertyResolved() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Method[] functions = new Method[] {null};
    ValueExpression[] variables = new ValueExpression[] {null};

    Bindings bindings = new Bindings(functions, variables);
    SimpleContext context = new SimpleContext();

    // Act
    astIdentifier.setValue(bindings, context, "Value");

    // Assert
    assertTrue(context.isPropertyResolved());
  }

  /**
   * Test {@link AstIdentifier#isReadOnly(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link AstIdentifier#AstIdentifier(String, int)} with name is {@code null} and
   *       index is zero.
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test isReadOnly(Bindings, ELContext); given AstIdentifier(String, int) with name is 'null' and index is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AstIdentifier.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly_givenAstIdentifierWithNameIsNullAndIndexIsZero() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier(null, 0);
    Method[] functions = new Method[] {null};
    ValueExpression[] variables = new ValueExpression[] {null};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astIdentifier.isReadOnly(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstIdentifier#isReadOnly(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>Given {@link PropertyNotFoundException#PropertyNotFoundException()}.
   *   <li>Then calls {@link BeanNameResolver#isNameResolved(String)}.
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test isReadOnly(Bindings, ELContext); given PropertyNotFoundException(); then calls isNameResolved(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AstIdentifier.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly_givenPropertyNotFoundException_thenCallsIsNameResolved() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Method[] functions = new Method[] {null};
    ValueExpression[] variables = new ValueExpression[] {null};

    Bindings bindings = new Bindings(functions, variables);

    BeanNameResolver beanNameResolver = mock(BeanNameResolver.class);
    when(beanNameResolver.isNameResolved(Mockito.<String>any()))
        .thenThrow(new PropertyNotFoundException());
    BeanNameELResolver resolver = new BeanNameELResolver(beanNameResolver);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astIdentifier.isReadOnly(bindings, new SimpleContext(resolver)));
    verify(beanNameResolver).isNameResolved("Name");
  }

  /**
   * Test {@link AstIdentifier#isReadOnly(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>When {@code Object}.
   *   <li>Then not {@link SimpleContext#SimpleContext()} PropertyResolved.
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test isReadOnly(Bindings, ELContext); when 'java.lang.Object'; then not SimpleContext() PropertyResolved")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AstIdentifier.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly_whenJavaLangObject_thenNotSimpleContextPropertyResolved() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);
    SimpleContext context = new SimpleContext();

    // Act
    boolean actualIsReadOnlyResult = astIdentifier.isReadOnly(bindings, context);

    // Assert
    assertFalse(context.isPropertyResolved());
    assertTrue(actualIsReadOnlyResult);
  }

  /**
   * Test {@link AstIdentifier#isReadOnly(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext(ELResolver)} with resolver is {@link
   *       ArrayELResolver#ArrayELResolver()}.
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test isReadOnly(Bindings, ELContext); when SimpleContext(ELResolver) with resolver is ArrayELResolver()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AstIdentifier.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly_whenSimpleContextWithResolverIsArrayELResolver() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Method[] functions = new Method[] {null};
    ValueExpression[] variables = new ValueExpression[] {null};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astIdentifier.isReadOnly(bindings, new SimpleContext(new ArrayELResolver())));
  }

  /**
   * Test {@link AstIdentifier#isReadOnly(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext(ELResolver)} with resolver is {@link
   *       CompositeELResolver} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName(
      "Test isReadOnly(Bindings, ELContext); when SimpleContext(ELResolver) with resolver is CompositeELResolver (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AstIdentifier.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly_whenSimpleContextWithResolverIsCompositeELResolver() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Method[] functions = new Method[] {null};
    ValueExpression[] variables = new ValueExpression[] {null};

    Bindings bindings = new Bindings(functions, variables);

    // Act and Assert
    assertThrows(
        PropertyNotFoundException.class,
        () -> astIdentifier.isReadOnly(bindings, new SimpleContext(new CompositeELResolver())));
  }

  /**
   * Test {@link AstIdentifier#isReadOnly(Bindings, ELContext)}.
   *
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext()}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(Bindings, ELContext); when SimpleContext(); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AstIdentifier.isReadOnly(Bindings, ELContext)"})
  void testIsReadOnly_whenSimpleContext_thenReturnFalse() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Method[] functions = new Method[] {null};
    ValueExpression[] variables = new ValueExpression[] {null};

    Bindings bindings = new Bindings(functions, variables);
    SimpleContext context = new SimpleContext();

    // Act and Assert
    assertFalse(astIdentifier.isReadOnly(bindings, context));
    assertTrue(context.isPropertyResolved());
  }

  /**
   * Test {@link AstIdentifier#getMethodExpression(Bindings, ELContext, Class, Class[])}.
   *
   * <ul>
   *   <li>Given {@link AstIdentifier#AstIdentifier(String, int)} with {@code Name} and index is
   *       two.
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#getMethodExpression(Bindings, ELContext, Class,
   * Class[])}
   */
  @Test
  @DisplayName(
      "Test getMethodExpression(Bindings, ELContext, Class, Class[]); given AstIdentifier(String, int) with 'Name' and index is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "jakarta.el.MethodExpression AstIdentifier.getMethodExpression(Bindings, ELContext, Class, Class[])"
  })
  void testGetMethodExpression_givenAstIdentifierWithNameAndIndexIsTwo() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 2);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type2 = Object.class;

    ObjectValueExpression objectValueExpression2 =
        new ObjectValueExpression(converter2, "Object", type2);
    TypeConverter converter3 = mock(TypeConverter.class);
    Class<Object> type3 = Object.class;

    ObjectValueExpression objectValueExpression3 =
        new ObjectValueExpression(converter3, "Object", type3);
    Bindings bindings =
        new Bindings(
            functions,
            new ValueExpression[] {
              objectValueExpression, objectValueExpression2, objectValueExpression3
            });
    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(
        MethodNotFoundException.class,
        () ->
            astIdentifier.getMethodExpression(
                bindings, context, returnType, new Class[] {forNameResult}));
  }

  /**
   * Test {@link AstIdentifier#getMethodExpression(Bindings, ELContext, Class, Class[])}.
   *
   * <ul>
   *   <li>Given {@code Convert}.
   *   <li>Then calls {@link TypeConverter#convert(Object, Class)}.
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#getMethodExpression(Bindings, ELContext, Class,
   * Class[])}
   */
  @Test
  @DisplayName(
      "Test getMethodExpression(Bindings, ELContext, Class, Class[]); given 'Convert'; then calls convert(Object, Class)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "jakarta.el.MethodExpression AstIdentifier.getMethodExpression(Bindings, ELContext, Class, Class[])"
  })
  void testGetMethodExpression_givenConvert_thenCallsConvert() throws ELException {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);

    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Object.class))).thenReturn("Convert");
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};
    Method[] functions = new Method[] {null};

    Bindings bindings = new Bindings(functions, variables);
    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(
        MethodNotFoundException.class,
        () ->
            astIdentifier.getMethodExpression(
                bindings, context, returnType, new Class[] {forNameResult}));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link AstIdentifier#getMethodExpression(Bindings, ELContext, Class, Class[])}.
   *
   * <ul>
   *   <li>Then calls {@link TreeBuilder#build(String)}.
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#getMethodExpression(Bindings, ELContext, Class,
   * Class[])}
   */
  @Test
  @DisplayName(
      "Test getMethodExpression(Bindings, ELContext, Class, Class[]); then calls build(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "jakarta.el.MethodExpression AstIdentifier.getMethodExpression(Bindings, ELContext, Class, Class[])"
  })
  void testGetMethodExpression_thenCallsBuild() throws ELException {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);

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
        new TreeValueExpression(
            store, functions2, variables, converter, "error.identifier.method.notamethod", type);
    ValueExpression[] variables2 = new ValueExpression[] {treeValueExpression};
    Method[] functions3 = new Method[] {null};

    Bindings bindings = new Bindings(functions3, variables2);
    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(
        MethodNotFoundException.class,
        () ->
            astIdentifier.getMethodExpression(
                bindings, context, returnType, new Class[] {forNameResult}));
    verify(converter).convert(isNull(), isA(Class.class));
    verify(builder).build("error.identifier.method.notamethod");
  }

  /**
   * Test {@link AstIdentifier#getMethodInfo(Bindings, ELContext, Class, Class[])}.
   *
   * <ul>
   *   <li>Given {@link AstIdentifier#AstIdentifier(String, int)} with {@code Name} and index is
   *       two.
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  @DisplayName(
      "Test getMethodInfo(Bindings, ELContext, Class, Class[]); given AstIdentifier(String, int) with 'Name' and index is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "jakarta.el.MethodInfo AstIdentifier.getMethodInfo(Bindings, ELContext, Class, Class[])"
  })
  void testGetMethodInfo_givenAstIdentifierWithNameAndIndexIsTwo() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 2);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type2 = Object.class;

    ObjectValueExpression objectValueExpression2 =
        new ObjectValueExpression(converter2, "Object", type2);
    TypeConverter converter3 = mock(TypeConverter.class);
    Class<Object> type3 = Object.class;

    ObjectValueExpression objectValueExpression3 =
        new ObjectValueExpression(converter3, "Object", type3);
    Bindings bindings =
        new Bindings(
            functions,
            new ValueExpression[] {
              objectValueExpression, objectValueExpression2, objectValueExpression3
            });
    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(
        MethodNotFoundException.class,
        () ->
            astIdentifier.getMethodInfo(
                bindings, context, returnType, new Class[] {forNameResult}));
  }

  /**
   * Test {@link AstIdentifier#getMethodInfo(Bindings, ELContext, Class, Class[])}.
   *
   * <ul>
   *   <li>Given {@code Convert}.
   *   <li>Then calls {@link TypeConverter#convert(Object, Class)}.
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  @DisplayName(
      "Test getMethodInfo(Bindings, ELContext, Class, Class[]); given 'Convert'; then calls convert(Object, Class)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "jakarta.el.MethodInfo AstIdentifier.getMethodInfo(Bindings, ELContext, Class, Class[])"
  })
  void testGetMethodInfo_givenConvert_thenCallsConvert() throws ELException {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);

    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Object.class))).thenReturn("Convert");
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};
    Method[] functions = new Method[] {null};

    Bindings bindings = new Bindings(functions, variables);
    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(
        MethodNotFoundException.class,
        () ->
            astIdentifier.getMethodInfo(
                bindings, context, returnType, new Class[] {forNameResult}));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link AstIdentifier#getMethodInfo(Bindings, ELContext, Class, Class[])}.
   *
   * <ul>
   *   <li>Then calls {@link TreeBuilder#build(String)}.
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  @DisplayName("Test getMethodInfo(Bindings, ELContext, Class, Class[]); then calls build(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "jakarta.el.MethodInfo AstIdentifier.getMethodInfo(Bindings, ELContext, Class, Class[])"
  })
  void testGetMethodInfo_thenCallsBuild() throws ELException {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);

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
        new TreeValueExpression(
            store, functions2, variables, converter, "error.identifier.method.notamethod", type);
    ValueExpression[] variables2 = new ValueExpression[] {treeValueExpression};
    Method[] functions3 = new Method[] {null};

    Bindings bindings = new Bindings(functions3, variables2);
    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(
        MethodNotFoundException.class,
        () ->
            astIdentifier.getMethodInfo(
                bindings, context, returnType, new Class[] {forNameResult}));
    verify(converter).convert(isNull(), isA(Class.class));
    verify(builder).build("error.identifier.method.notamethod");
  }

  /**
   * Test {@link AstIdentifier#invoke(Bindings, ELContext, Class, Class[], Object[])}.
   *
   * <ul>
   *   <li>Given {@link AstIdentifier#AstIdentifier(String, int)} with {@code Name} and index is
   *       two.
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#invoke(Bindings, ELContext, Class, Class[],
   * Object[])}
   */
  @Test
  @DisplayName(
      "Test invoke(Bindings, ELContext, Class, Class[], Object[]); given AstIdentifier(String, int) with 'Name' and index is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstIdentifier.invoke(Bindings, ELContext, Class, Class[], Object[])"})
  void testInvoke_givenAstIdentifierWithNameAndIndexIsTwo() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 2);
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type2 = Object.class;

    ObjectValueExpression objectValueExpression2 =
        new ObjectValueExpression(converter2, "Object", type2);
    TypeConverter converter3 = mock(TypeConverter.class);
    Class<Object> type3 = Object.class;

    ObjectValueExpression objectValueExpression3 =
        new ObjectValueExpression(converter3, "Object", type3);
    Bindings bindings =
        new Bindings(
            functions,
            new ValueExpression[] {
              objectValueExpression, objectValueExpression2, objectValueExpression3
            });
    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(
        MethodNotFoundException.class,
        () ->
            astIdentifier.invoke(
                bindings,
                context,
                returnType,
                new Class[] {forNameResult},
                new Object[] {"Params"}));
  }

  /**
   * Test {@link AstIdentifier#invoke(Bindings, ELContext, Class, Class[], Object[])}.
   *
   * <ul>
   *   <li>Given {@code Convert}.
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)} return {@code
   *       Convert}.
   *   <li>Then calls {@link TypeConverter#convert(Object, Class)}.
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#invoke(Bindings, ELContext, Class, Class[],
   * Object[])}
   */
  @Test
  @DisplayName(
      "Test invoke(Bindings, ELContext, Class, Class[], Object[]); given 'Convert'; when TypeConverter convert(Object, Class) return 'Convert'; then calls convert(Object, Class)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstIdentifier.invoke(Bindings, ELContext, Class, Class[], Object[])"})
  void testInvoke_givenConvert_whenTypeConverterConvertReturnConvert_thenCallsConvert()
      throws ELException {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);

    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), eq(Object.class))).thenReturn("Convert");
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};
    Method[] functions = new Method[] {null};

    Bindings bindings = new Bindings(functions, variables);
    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(
        MethodNotFoundException.class,
        () ->
            astIdentifier.invoke(
                bindings,
                context,
                returnType,
                new Class[] {forNameResult},
                new Object[] {"Params"}));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test {@link AstIdentifier#invoke(Bindings, ELContext, Class, Class[], Object[])}.
   *
   * <ul>
   *   <li>Then calls {@link TreeBuilder#build(String)}.
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#invoke(Bindings, ELContext, Class, Class[],
   * Object[])}
   */
  @Test
  @DisplayName(
      "Test invoke(Bindings, ELContext, Class, Class[], Object[]); then calls build(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object AstIdentifier.invoke(Bindings, ELContext, Class, Class[], Object[])"})
  void testInvoke_thenCallsBuild() throws ELException {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);

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
        new TreeValueExpression(
            store, functions2, variables, converter, "error.identifier.method.notamethod", type);
    ValueExpression[] variables2 = new ValueExpression[] {treeValueExpression};
    Method[] functions3 = new Method[] {null};

    Bindings bindings = new Bindings(functions3, variables2);
    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(
        MethodNotFoundException.class,
        () ->
            astIdentifier.invoke(
                bindings,
                context,
                returnType,
                new Class[] {forNameResult},
                new Object[] {"Params"}));
    verify(converter).convert(isNull(), isA(Class.class));
    verify(builder).build("error.identifier.method.notamethod");
  }

  /**
   * Test {@link AstIdentifier#appendStructure(StringBuilder, Bindings)}.
   *
   * <p>Method under test: {@link AstIdentifier#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstIdentifier.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    ValueExpression[] variables = new ValueExpression[] {null};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astIdentifier.appendStructure(b, bindings);

    // Assert
    assertEquals("StrName", b.toString());
  }

  /**
   * Test {@link AstIdentifier#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Given {@link AstIdentifier#AstIdentifier(String, int)} with {@code Name} and index is
   *       minus one.
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); given AstIdentifier(String, int) with 'Name' and index is minus one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstIdentifier.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_givenAstIdentifierWithNameAndIndexIsMinusOne() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", -1);
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astIdentifier.appendStructure(b, bindings);

    // Assert
    assertEquals("StrName", b.toString());
  }

  /**
   * Test {@link AstIdentifier#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Given {@link AstIdentifier#AstIdentifier(String, int)} with {@code Name} and index is
   *       one.
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); given AstIdentifier(String, int) with 'Name' and index is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstIdentifier.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_givenAstIdentifierWithNameAndIndexIsOne() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 1);
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astIdentifier.appendStructure(b, bindings);

    // Assert
    assertEquals("StrName", b.toString());
  }

  /**
   * Test {@link AstIdentifier#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Given {@link AstIdentifier#AstIdentifier(String, int)} with {@code Name} and index is
   *       zero.
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); given AstIdentifier(String, int) with 'Name' and index is zero; when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstIdentifier.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_givenAstIdentifierWithNameAndIndexIsZero_whenNull() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    StringBuilder b = new StringBuilder("Str");

    // Act
    astIdentifier.appendStructure(b, null);

    // Assert
    assertEquals("StrName", b.toString());
  }

  /**
   * Test {@link AstIdentifier#appendStructure(StringBuilder, Bindings)}.
   *
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code Str} toString is {@code
   *       Str<var>}.
   * </ul>
   *
   * <p>Method under test: {@link AstIdentifier#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName(
      "Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'Str' toString is 'Str<var>'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AstIdentifier.appendStructure(StringBuilder, Bindings)"})
  void testAppendStructure_thenStringBuilderWithStrToStringIsStrVar() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    StringBuilder b = new StringBuilder("Str");
    Method[] functions = new Method[] {null};
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    ObjectValueExpression objectValueExpression =
        new ObjectValueExpression(converter, "Object", type);
    ValueExpression[] variables = new ValueExpression[] {objectValueExpression};

    Bindings bindings = new Bindings(functions, variables);

    // Act
    astIdentifier.appendStructure(b, bindings);

    // Assert
    assertEquals("Str<var>", b.toString());
  }

  /**
   * Test {@link AstIdentifier#getChild(int)}.
   *
   * <p>Method under test: {@link AstIdentifier#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.core.el.juel.tree.impl.ast.AstNode AstIdentifier.getChild(int)"})
  void testGetChild() {
    // Arrange, Act and Assert
    assertNull(new AstIdentifier("Name", 1).getChild(1));
  }
}
