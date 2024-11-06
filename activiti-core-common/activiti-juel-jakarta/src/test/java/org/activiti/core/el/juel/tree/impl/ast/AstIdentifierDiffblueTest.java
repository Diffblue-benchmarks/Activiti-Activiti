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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import jakarta.el.ArrayELResolver;
import jakarta.el.ELContext;
import jakarta.el.ELException;
import jakarta.el.FunctionMapper;
import jakarta.el.MethodInfo;
import jakarta.el.MethodNotFoundException;
import jakarta.el.PropertyNotFoundException;
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
import org.activiti.core.el.juel.tree.impl.ast.AstIdentifierTest.TestMethodExpression;
import org.activiti.core.el.juel.util.RootPropertyResolver;
import org.activiti.core.el.juel.util.SimpleContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AstIdentifierDiffblueTest {
  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code Name}.</li>
   * </ul>
   * <p>
   * Methods under test:
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
   * <ul>
   *   <li>When {@code true}.</li>
   * </ul>
   * <p>
   * Methods under test:
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
   * <ul>
   *   <li>Given {@link AstIdentifier#AstIdentifier(String, int)} with name is
   * {@code null} and index is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstIdentifier#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getType(Bindings, ELContext); given AstIdentifier(String, int) with name is 'null' and index is zero")
  void testGetType_givenAstIdentifierWithNameIsNullAndIndexIsZero() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier(null, 0);
    Bindings bindings = new Bindings(new Method[]{null}, new ValueExpression[]{null});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astIdentifier.getType(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstIdentifier#getType(Bindings, ELContext)}.
   * <ul>
   *   <li>Then {@link SimpleContext#SimpleContext(ELResolver)} with resolver is
   * {@link RootPropertyResolver#RootPropertyResolver()} PropertyResolved.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstIdentifier#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getType(Bindings, ELContext); then SimpleContext(ELResolver) with resolver is RootPropertyResolver() PropertyResolved")
  void testGetType_thenSimpleContextWithResolverIsRootPropertyResolverPropertyResolved() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Bindings bindings = new Bindings(new Method[]{null}, new ValueExpression[]{null});

    SimpleContext context = new SimpleContext(new RootPropertyResolver());

    // Act
    Class<?> actualType = astIdentifier.getType(bindings, context);

    // Assert
    assertTrue(context.isPropertyResolved());
    Class<Object> expectedType = Object.class;
    assertEquals(expectedType, actualType);
  }

  /**
   * Test {@link AstIdentifier#getType(Bindings, ELContext)}.
   * <ul>
   *   <li>When {@code java.lang.Object}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstIdentifier#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getType(Bindings, ELContext); when 'java.lang.Object'; then return 'null'")
  void testGetType_whenJavaLangObject_thenReturnNull() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    SimpleContext context = new SimpleContext();

    // Act and Assert
    assertNull(astIdentifier.getType(bindings, context));
    assertFalse(context.isPropertyResolved());
  }

  /**
   * Test {@link AstIdentifier#getType(Bindings, ELContext)}.
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext(ELResolver)} with resolver is
   * {@link ArrayELResolver#ArrayELResolver()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstIdentifier#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getType(Bindings, ELContext); when SimpleContext(ELResolver) with resolver is ArrayELResolver()")
  void testGetType_whenSimpleContextWithResolverIsArrayELResolver() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Bindings bindings = new Bindings(new Method[]{null}, new ValueExpression[]{null});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class,
        () -> astIdentifier.getType(bindings, new SimpleContext(new ArrayELResolver())));
  }

  /**
   * Test {@link AstIdentifier#getType(Bindings, ELContext)}.
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext()}.</li>
   *   <li>Then {@link SimpleContext#SimpleContext()} PropertyResolved.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstIdentifier#getType(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getType(Bindings, ELContext); when SimpleContext(); then SimpleContext() PropertyResolved")
  void testGetType_whenSimpleContext_thenSimpleContextPropertyResolved() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Bindings bindings = new Bindings(new Method[]{null}, new ValueExpression[]{null});

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
   * <ul>
   *   <li>Then calls {@link TreeBuilder#build(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstIdentifier#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getValueReference(Bindings, ELContext); then calls build(String)")
  void testGetValueReference_thenCallsBuild() throws TreeBuilderException {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();
    when(builder.build(Mockito.<String>any())).thenReturn(new Tree(root, functions, new ArrayList<>(), true));
    TreeStore store = new TreeStore(builder, new Cache(3));

    FunctionMapper functions2 = mock(FunctionMapper.class);
    VariableMapper variables = mock(VariableMapper.class);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new TreeValueExpression(store, functions2, variables, converter, "Expr", type)});

    // Act
    ValueReference actualValueReference = astIdentifier.getValueReference(bindings, new SimpleContext());

    // Assert
    verify(builder).build(eq("Expr"));
    assertNull(actualValueReference);
  }

  /**
   * Test {@link AstIdentifier#getValueReference(Bindings, ELContext)}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstIdentifier#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getValueReference(Bindings, ELContext); then return 'null'")
  void testGetValueReference_thenReturnNull() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act and Assert
    assertNull(astIdentifier.getValueReference(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstIdentifier#getValueReference(Bindings, ELContext)}.
   * <ul>
   *   <li>Then return Property is {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstIdentifier#getValueReference(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test getValueReference(Bindings, ELContext); then return Property is 'Name'")
  void testGetValueReference_thenReturnPropertyIsName() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Bindings bindings = new Bindings(new Method[]{null}, new ValueExpression[]{null});

    // Act
    ValueReference actualValueReference = astIdentifier.getValueReference(bindings, new SimpleContext());

    // Assert
    assertEquals("Name", actualValueReference.getProperty());
    assertNull(actualValueReference.getBase());
  }

  /**
   * Test {@link AstIdentifier#eval(Bindings, ELContext)}.
   * <ul>
   *   <li>Then calls {@link TreeBuilder#build(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstIdentifier#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext); then calls build(String)")
  void testEval_thenCallsBuild() throws ELException {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
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
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new TreeValueExpression(store, functions2, variables, converter, "Expr", type)});

    // Act
    Object actualEvalResult = astIdentifier.eval(bindings, new SimpleContext());

    // Assert
    verify(converter).convert(isNull(), isA(Class.class));
    verify(builder).build(eq("Expr"));
    assertEquals("Convert", actualEvalResult);
  }

  /**
   * Test {@link AstIdentifier#eval(Bindings, ELContext)}.
   * <ul>
   *   <li>Then return {@code Convert}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstIdentifier#eval(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test eval(Bindings, ELContext); then return 'Convert'")
  void testEval_thenReturnConvert() throws ELException {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn("Convert");
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    // Act
    Object actualEvalResult = astIdentifier.eval(bindings, new SimpleContext());

    // Assert
    verify(converter).convert(isA(Object.class), isA(Class.class));
    assertEquals("Convert", actualEvalResult);
  }

  /**
   * Test {@link AstIdentifier#setValue(Bindings, ELContext, Object)}.
   * <p>
   * Method under test:
   * {@link AstIdentifier#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object)")
  void testSetValue() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Bindings bindings = new Bindings(new Method[]{null}, new ValueExpression[]{null});

    StandardELContext context = new StandardELContext(new ExpressionFactoryImpl());

    // Act
    astIdentifier.setValue(bindings, context, "Value");

    // Assert
    assertTrue(context.isPropertyResolved());
  }

  /**
   * Test {@link AstIdentifier#setValue(Bindings, ELContext, Object)}.
   * <ul>
   *   <li>Given {@link AstIdentifier#AstIdentifier(String, int)} with name is
   * {@code null} and index is zero.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstIdentifier#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object); given AstIdentifier(String, int) with name is 'null' and index is zero")
  void testSetValue_givenAstIdentifierWithNameIsNullAndIndexIsZero() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier(null, 0);
    Bindings bindings = new Bindings(new Method[]{null}, new ValueExpression[]{null});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astIdentifier.setValue(bindings, new SimpleContext(), "Value"));
  }

  /**
   * Test {@link AstIdentifier#setValue(Bindings, ELContext, Object)}.
   * <ul>
   *   <li>Then {@link SimpleContext#SimpleContext(ELResolver)} with resolver is
   * {@link RootPropertyResolver#RootPropertyResolver()} PropertyResolved.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstIdentifier#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object); then SimpleContext(ELResolver) with resolver is RootPropertyResolver() PropertyResolved")
  void testSetValue_thenSimpleContextWithResolverIsRootPropertyResolverPropertyResolved() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Bindings bindings = new Bindings(new Method[]{null}, new ValueExpression[]{null});

    SimpleContext context = new SimpleContext(new RootPropertyResolver());

    // Act
    astIdentifier.setValue(bindings, context, "Value");

    // Assert
    assertTrue(context.isPropertyResolved());
  }

  /**
   * Test {@link AstIdentifier#setValue(Bindings, ELContext, Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link SimpleContext#SimpleContext()} PropertyResolved.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstIdentifier#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object); when 'null'; then SimpleContext() PropertyResolved")
  void testSetValue_whenNull_thenSimpleContextPropertyResolved() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Bindings bindings = new Bindings(new Method[]{null}, new ValueExpression[]{null});

    SimpleContext context = new SimpleContext();

    // Act
    astIdentifier.setValue(bindings, context, null);

    // Assert
    assertTrue(context.isPropertyResolved());
  }

  /**
   * Test {@link AstIdentifier#setValue(Bindings, ELContext, Object)}.
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext(ELResolver)} with resolver is
   * {@link ArrayELResolver#ArrayELResolver()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstIdentifier#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object); when SimpleContext(ELResolver) with resolver is ArrayELResolver()")
  void testSetValue_whenSimpleContextWithResolverIsArrayELResolver() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Bindings bindings = new Bindings(new Method[]{null}, new ValueExpression[]{null});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class,
        () -> astIdentifier.setValue(bindings, new SimpleContext(new ArrayELResolver()), "Value"));
  }

  /**
   * Test {@link AstIdentifier#setValue(Bindings, ELContext, Object)}.
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext()}.</li>
   *   <li>Then {@link SimpleContext#SimpleContext()} PropertyResolved.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstIdentifier#setValue(Bindings, ELContext, Object)}
   */
  @Test
  @DisplayName("Test setValue(Bindings, ELContext, Object); when SimpleContext(); then SimpleContext() PropertyResolved")
  void testSetValue_whenSimpleContext_thenSimpleContextPropertyResolved() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Bindings bindings = new Bindings(new Method[]{null}, new ValueExpression[]{null});

    SimpleContext context = new SimpleContext();

    // Act
    astIdentifier.setValue(bindings, context, "Value");

    // Assert
    assertTrue(context.isPropertyResolved());
  }

  /**
   * Test {@link AstIdentifier#isReadOnly(Bindings, ELContext)}.
   * <p>
   * Method under test: {@link AstIdentifier#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(Bindings, ELContext)")
  void testIsReadOnly() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Bindings bindings = new Bindings(new Method[]{null}, new ValueExpression[]{null});

    SimpleContext context = new SimpleContext(new RootPropertyResolver());

    // Act and Assert
    assertFalse(astIdentifier.isReadOnly(bindings, context));
    assertTrue(context.isPropertyResolved());
  }

  /**
   * Test {@link AstIdentifier#isReadOnly(Bindings, ELContext)}.
   * <ul>
   *   <li>Given {@link AstIdentifier#AstIdentifier(String, int)} with name is
   * {@code null} and index is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstIdentifier#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(Bindings, ELContext); given AstIdentifier(String, int) with name is 'null' and index is zero")
  void testIsReadOnly_givenAstIdentifierWithNameIsNullAndIndexIsZero() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier(null, 0);
    Bindings bindings = new Bindings(new Method[]{null}, new ValueExpression[]{null});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astIdentifier.isReadOnly(bindings, new SimpleContext()));
  }

  /**
   * Test {@link AstIdentifier#isReadOnly(Bindings, ELContext)}.
   * <ul>
   *   <li>When {@code java.lang.Object}.</li>
   *   <li>Then not {@link SimpleContext#SimpleContext()} PropertyResolved.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstIdentifier#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(Bindings, ELContext); when 'java.lang.Object'; then not SimpleContext() PropertyResolved")
  void testIsReadOnly_whenJavaLangObject_thenNotSimpleContextPropertyResolved() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    SimpleContext context = new SimpleContext();

    // Act
    boolean actualIsReadOnlyResult = astIdentifier.isReadOnly(bindings, context);

    // Assert
    assertFalse(context.isPropertyResolved());
    assertTrue(actualIsReadOnlyResult);
  }

  /**
   * Test {@link AstIdentifier#isReadOnly(Bindings, ELContext)}.
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext(ELResolver)} with resolver is
   * {@link ArrayELResolver#ArrayELResolver()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstIdentifier#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(Bindings, ELContext); when SimpleContext(ELResolver) with resolver is ArrayELResolver()")
  void testIsReadOnly_whenSimpleContextWithResolverIsArrayELResolver() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Bindings bindings = new Bindings(new Method[]{null}, new ValueExpression[]{null});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class,
        () -> astIdentifier.isReadOnly(bindings, new SimpleContext(new ArrayELResolver())));
  }

  /**
   * Test {@link AstIdentifier#isReadOnly(Bindings, ELContext)}.
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext()}.</li>
   *   <li>Then {@link SimpleContext#SimpleContext()} PropertyResolved.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstIdentifier#isReadOnly(Bindings, ELContext)}
   */
  @Test
  @DisplayName("Test isReadOnly(Bindings, ELContext); when SimpleContext(); then SimpleContext() PropertyResolved")
  void testIsReadOnly_whenSimpleContext_thenSimpleContextPropertyResolved() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Bindings bindings = new Bindings(new Method[]{null}, new ValueExpression[]{null});

    SimpleContext context = new SimpleContext();

    // Act and Assert
    assertFalse(astIdentifier.isReadOnly(bindings, context));
    assertTrue(context.isPropertyResolved());
  }

  /**
   * Test
   * {@link AstIdentifier#getMethodExpression(Bindings, ELContext, Class, Class[])}.
   * <ul>
   *   <li>Given {@code Convert}.</li>
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)}
   * return {@code Convert}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstIdentifier#getMethodExpression(Bindings, ELContext, Class, Class[])}
   */
  @Test
  @DisplayName("Test getMethodExpression(Bindings, ELContext, Class, Class[]); given 'Convert'; when TypeConverter convert(Object, Class) return 'Convert'")
  void testGetMethodExpression_givenConvert_whenTypeConverterConvertReturnConvert() throws ELException {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn("Convert");
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(MethodNotFoundException.class,
        () -> astIdentifier.getMethodExpression(bindings, context, returnType, new Class[]{forNameResult}));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test
   * {@link AstIdentifier#getMethodExpression(Bindings, ELContext, Class, Class[])}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)}
   * return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstIdentifier#getMethodExpression(Bindings, ELContext, Class, Class[])}
   */
  @Test
  @DisplayName("Test getMethodExpression(Bindings, ELContext, Class, Class[]); given 'null'; when TypeConverter convert(Object, Class) return 'null'")
  void testGetMethodExpression_givenNull_whenTypeConverterConvertReturnNull() throws ELException {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn(null);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(MethodNotFoundException.class,
        () -> astIdentifier.getMethodExpression(bindings, context, returnType, new Class[]{forNameResult}));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test
   * {@link AstIdentifier#getMethodExpression(Bindings, ELContext, Class, Class[])}.
   * <ul>
   *   <li>Given {@link TestMethodExpression}.</li>
   *   <li>Then array length is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstIdentifier#getMethodExpression(Bindings, ELContext, Class, Class[])}
   */
  @Test
  @DisplayName("Test getMethodExpression(Bindings, ELContext, Class, Class[]); given TestMethodExpression; then array length is one")
  void testGetMethodExpression_givenTestMethodExpression_thenArrayLengthIsOne() throws ELException {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Object>>any()))
        .thenReturn(mock(AstIdentifierTest.TestMethodExpression.class));
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;
    Class<?>[] paramTypes = new Class[]{forNameResult};

    // Act
    astIdentifier.getMethodExpression(bindings, context, returnType, paramTypes);

    // Assert
    verify(converter).convert(isA(Object.class), isA(Class.class));
    assertEquals(1, paramTypes.length);
    Class<Object> expectedResultClass = Object.class;
    assertEquals(expectedResultClass, paramTypes[0]);
  }

  /**
   * Test
   * {@link AstIdentifier#getMethodExpression(Bindings, ELContext, Class, Class[])}.
   * <ul>
   *   <li>Then calls {@link TreeBuilder#build(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstIdentifier#getMethodExpression(Bindings, ELContext, Class, Class[])}
   */
  @Test
  @DisplayName("Test getMethodExpression(Bindings, ELContext, Class, Class[]); then calls build(String)")
  void testGetMethodExpression_thenCallsBuild() throws ELException {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
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
    Bindings bindings = new Bindings(new Method[]{null}, new ValueExpression[]{
        new TreeValueExpression(store, functions2, variables, converter, "error.identifier.method.notamethod", type)});

    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(MethodNotFoundException.class,
        () -> astIdentifier.getMethodExpression(bindings, context, returnType, new Class[]{forNameResult}));
    verify(converter).convert(isNull(), isA(Class.class));
    verify(builder).build(eq("error.identifier.method.notamethod"));
  }

  /**
   * Test
   * {@link AstIdentifier#getMethodInfo(Bindings, ELContext, Class, Class[])}.
   * <p>
   * Method under test:
   * {@link AstIdentifier#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  @DisplayName("Test getMethodInfo(Bindings, ELContext, Class, Class[])")
  void testGetMethodInfo() throws ELException {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    AstIdentifierTest.TestMethodExpression testMethodExpression = mock(AstIdentifierTest.TestMethodExpression.class);
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;
    MethodInfo methodInfo = new MethodInfo("Name", returnType, new Class[]{forNameResult});

    when(testMethodExpression.getMethodInfo(Mockito.<ELContext>any())).thenReturn(methodInfo);
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn(testMethodExpression);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    SimpleContext context = new SimpleContext();
    Class<Object> returnType2 = Object.class;
    Class<Object> forNameResult2 = Object.class;

    // Act
    MethodInfo actualMethodInfo = astIdentifier.getMethodInfo(bindings, context, returnType2,
        new Class[]{forNameResult2});

    // Assert
    verify(converter).convert(isA(Object.class), isA(Class.class));
    verify(testMethodExpression).getMethodInfo(isA(ELContext.class));
    assertSame(methodInfo, actualMethodInfo);
  }

  /**
   * Test
   * {@link AstIdentifier#getMethodInfo(Bindings, ELContext, Class, Class[])}.
   * <p>
   * Method under test:
   * {@link AstIdentifier#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  @DisplayName("Test getMethodInfo(Bindings, ELContext, Class, Class[])")
  void testGetMethodInfo2() throws ELException {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();
    when(builder.build(Mockito.<String>any())).thenReturn(new Tree(root, functions, new ArrayList<>(), true));
    TreeStore store = new TreeStore(builder, new Cache(3));

    AstIdentifierTest.TestMethodExpression testMethodExpression = mock(AstIdentifierTest.TestMethodExpression.class);
    when(testMethodExpression.getMethodInfo(Mockito.<ELContext>any()))
        .thenThrow(new PropertyNotFoundException("An error occurred"));
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn(testMethodExpression);
    FunctionMapper functions2 = mock(FunctionMapper.class);
    VariableMapper variables = mock(VariableMapper.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new TreeValueExpression(store, functions2, variables, converter, "Expr", type)});

    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(PropertyNotFoundException.class,
        () -> astIdentifier.getMethodInfo(bindings, context, returnType, new Class[]{forNameResult}));
    verify(converter).convert(isNull(), isA(Class.class));
    verify(builder).build(eq("Expr"));
    verify(testMethodExpression).getMethodInfo(isA(ELContext.class));
  }

  /**
   * Test
   * {@link AstIdentifier#getMethodInfo(Bindings, ELContext, Class, Class[])}.
   * <ul>
   *   <li>Given {@code Convert}.</li>
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)}
   * return {@code Convert}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstIdentifier#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  @DisplayName("Test getMethodInfo(Bindings, ELContext, Class, Class[]); given 'Convert'; when TypeConverter convert(Object, Class) return 'Convert'")
  void testGetMethodInfo_givenConvert_whenTypeConverterConvertReturnConvert() throws ELException {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn("Convert");
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(MethodNotFoundException.class,
        () -> astIdentifier.getMethodInfo(bindings, context, returnType, new Class[]{forNameResult}));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test
   * {@link AstIdentifier#getMethodInfo(Bindings, ELContext, Class, Class[])}.
   * <ul>
   *   <li>Given {@code Convert}.</li>
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)}
   * return {@code Convert}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstIdentifier#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  @DisplayName("Test getMethodInfo(Bindings, ELContext, Class, Class[]); given 'Convert'; when TypeConverter convert(Object, Class) return 'Convert'")
  void testGetMethodInfo_givenConvert_whenTypeConverterConvertReturnConvert2() throws ELException {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
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
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new TreeValueExpression(store, functions2, variables, converter, "Expr", type)});

    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(MethodNotFoundException.class,
        () -> astIdentifier.getMethodInfo(bindings, context, returnType, new Class[]{forNameResult}));
    verify(converter).convert(isNull(), isA(Class.class));
    verify(builder).build(eq("Expr"));
  }

  /**
   * Test
   * {@link AstIdentifier#getMethodInfo(Bindings, ELContext, Class, Class[])}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)}
   * return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstIdentifier#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  @DisplayName("Test getMethodInfo(Bindings, ELContext, Class, Class[]); given 'null'; when TypeConverter convert(Object, Class) return 'null'")
  void testGetMethodInfo_givenNull_whenTypeConverterConvertReturnNull() throws ELException {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();
    when(builder.build(Mockito.<String>any())).thenReturn(new Tree(root, functions, new ArrayList<>(), true));
    TreeStore store = new TreeStore(builder, new Cache(3));

    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn(null);
    FunctionMapper functions2 = mock(FunctionMapper.class);
    VariableMapper variables = mock(VariableMapper.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new TreeValueExpression(store, functions2, variables, converter, "Expr", type)});

    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(MethodNotFoundException.class,
        () -> astIdentifier.getMethodInfo(bindings, context, returnType, new Class[]{forNameResult}));
    verify(converter).convert(isNull(), isA(Class.class));
    verify(builder).build(eq("Expr"));
  }

  /**
   * Test
   * {@link AstIdentifier#getMethodInfo(Bindings, ELContext, Class, Class[])}.
   * <ul>
   *   <li>Then throw {@link PropertyNotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstIdentifier#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  @DisplayName("Test getMethodInfo(Bindings, ELContext, Class, Class[]); then throw PropertyNotFoundException")
  void testGetMethodInfo_thenThrowPropertyNotFoundException() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier(null, 0);
    new PropertyNotFoundException("An error occurred");
    Bindings bindings = new Bindings(new Method[]{null}, new ValueExpression[]{null});

    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(PropertyNotFoundException.class,
        () -> astIdentifier.getMethodInfo(bindings, context, returnType, new Class[]{forNameResult}));
  }

  /**
   * Test
   * {@link AstIdentifier#getMethodInfo(Bindings, ELContext, Class, Class[])}.
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext(ELResolver)} with resolver is
   * {@link ArrayELResolver#ArrayELResolver()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstIdentifier#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  @DisplayName("Test getMethodInfo(Bindings, ELContext, Class, Class[]); when SimpleContext(ELResolver) with resolver is ArrayELResolver()")
  void testGetMethodInfo_whenSimpleContextWithResolverIsArrayELResolver() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier(null, 0);
    new PropertyNotFoundException("An error occurred");
    Bindings bindings = new Bindings(new Method[]{null}, new ValueExpression[]{null});

    SimpleContext context = new SimpleContext(new ArrayELResolver());
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(PropertyNotFoundException.class,
        () -> astIdentifier.getMethodInfo(bindings, context, returnType, new Class[]{forNameResult}));
  }

  /**
   * Test
   * {@link AstIdentifier#getMethodInfo(Bindings, ELContext, Class, Class[])}.
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext(ELResolver)} with resolver is
   * {@link RootPropertyResolver#RootPropertyResolver()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstIdentifier#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  @DisplayName("Test getMethodInfo(Bindings, ELContext, Class, Class[]); when SimpleContext(ELResolver) with resolver is RootPropertyResolver()")
  void testGetMethodInfo_whenSimpleContextWithResolverIsRootPropertyResolver() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier(null, 0);
    new PropertyNotFoundException("An error occurred");
    Bindings bindings = new Bindings(new Method[]{null}, new ValueExpression[]{null});

    SimpleContext context = new SimpleContext(new RootPropertyResolver());
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(PropertyNotFoundException.class,
        () -> astIdentifier.getMethodInfo(bindings, context, returnType, new Class[]{forNameResult}));
  }

  /**
   * Test
   * {@link AstIdentifier#invoke(Bindings, ELContext, Class, Class[], Object[])}.
   * <p>
   * Method under test:
   * {@link AstIdentifier#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(Bindings, ELContext, Class, Class[], Object[])")
  void testInvoke() throws ELException {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();
    when(builder.build(Mockito.<String>any())).thenReturn(new Tree(root, functions, new ArrayList<>(), true));
    TreeStore store = new TreeStore(builder, new Cache(3));

    AstIdentifierTest.TestMethodExpression testMethodExpression = mock(AstIdentifierTest.TestMethodExpression.class);
    when(testMethodExpression.invoke(Mockito.<ELContext>any(), Mockito.<Object[]>any()))
        .thenThrow(new PropertyNotFoundException("An error occurred"));
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn(testMethodExpression);
    FunctionMapper functions2 = mock(FunctionMapper.class);
    VariableMapper variables = mock(VariableMapper.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new TreeValueExpression(store, functions2, variables, converter, "Expr", type)});

    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(PropertyNotFoundException.class,
        () -> astIdentifier.invoke(bindings, context, returnType, new Class[]{forNameResult}, new Object[]{"Params"}));
    verify(converter).convert(isNull(), isA(Class.class));
    verify(builder).build(eq("Expr"));
    verify(testMethodExpression).invoke(isA(ELContext.class), isA(Object[].class));
  }

  /**
   * Test
   * {@link AstIdentifier#invoke(Bindings, ELContext, Class, Class[], Object[])}.
   * <ul>
   *   <li>Given {@code Convert}.</li>
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)}
   * return {@code Convert}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstIdentifier#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(Bindings, ELContext, Class, Class[], Object[]); given 'Convert'; when TypeConverter convert(Object, Class) return 'Convert'")
  void testInvoke_givenConvert_whenTypeConverterConvertReturnConvert() throws ELException {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn("Convert");
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(MethodNotFoundException.class,
        () -> astIdentifier.invoke(bindings, context, returnType, new Class[]{forNameResult}, new Object[]{"Params"}));
    verify(converter).convert(isA(Object.class), isA(Class.class));
  }

  /**
   * Test
   * {@link AstIdentifier#invoke(Bindings, ELContext, Class, Class[], Object[])}.
   * <ul>
   *   <li>Given {@code Convert}.</li>
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)}
   * return {@code Convert}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstIdentifier#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(Bindings, ELContext, Class, Class[], Object[]); given 'Convert'; when TypeConverter convert(Object, Class) return 'Convert'")
  void testInvoke_givenConvert_whenTypeConverterConvertReturnConvert2() throws ELException {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
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
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new TreeValueExpression(store, functions2, variables, converter, "Expr", type)});

    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(MethodNotFoundException.class,
        () -> astIdentifier.invoke(bindings, context, returnType, new Class[]{forNameResult}, new Object[]{"Params"}));
    verify(converter).convert(isNull(), isA(Class.class));
    verify(builder).build(eq("Expr"));
  }

  /**
   * Test
   * {@link AstIdentifier#invoke(Bindings, ELContext, Class, Class[], Object[])}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link TypeConverter} {@link TypeConverter#convert(Object, Class)}
   * return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstIdentifier#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(Bindings, ELContext, Class, Class[], Object[]); given 'null'; when TypeConverter convert(Object, Class) return 'null'")
  void testInvoke_givenNull_whenTypeConverterConvertReturnNull() throws ELException {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    TreeBuilder builder = mock(TreeBuilder.class);
    AstNull root = new AstNull();
    ArrayList<FunctionNode> functions = new ArrayList<>();
    when(builder.build(Mockito.<String>any())).thenReturn(new Tree(root, functions, new ArrayList<>(), true));
    TreeStore store = new TreeStore(builder, new Cache(3));

    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn(null);
    FunctionMapper functions2 = mock(FunctionMapper.class);
    VariableMapper variables = mock(VariableMapper.class);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new TreeValueExpression(store, functions2, variables, converter, "Expr", type)});

    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(MethodNotFoundException.class,
        () -> astIdentifier.invoke(bindings, context, returnType, new Class[]{forNameResult}, new Object[]{"Params"}));
    verify(converter).convert(isNull(), isA(Class.class));
    verify(builder).build(eq("Expr"));
  }

  /**
   * Test
   * {@link AstIdentifier#invoke(Bindings, ELContext, Class, Class[], Object[])}.
   * <ul>
   *   <li>Given {@link TestMethodExpression}
   * {@link TestMethodExpression#invoke(ELContext, Object[])} return
   * {@code Invoke}.</li>
   *   <li>Then return {@code Invoke}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstIdentifier#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(Bindings, ELContext, Class, Class[], Object[]); given TestMethodExpression invoke(ELContext, Object[]) return 'Invoke'; then return 'Invoke'")
  void testInvoke_givenTestMethodExpressionInvokeReturnInvoke_thenReturnInvoke() throws ELException {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    AstIdentifierTest.TestMethodExpression testMethodExpression = mock(AstIdentifierTest.TestMethodExpression.class);
    when(testMethodExpression.invoke(Mockito.<ELContext>any(), Mockito.<Object[]>any())).thenReturn("Invoke");
    TypeConverter converter = mock(TypeConverter.class);
    when(converter.convert(Mockito.<Object>any(), Mockito.<Class<Object>>any())).thenReturn(testMethodExpression);
    Class<Object> type = Object.class;
    Bindings bindings = new Bindings(new Method[]{null},
        new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)});

    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;
    Class<?>[] paramTypes = new Class[]{forNameResult};

    // Act
    Object actualInvokeResult = astIdentifier.invoke(bindings, context, returnType, paramTypes, new Object[]{"Params"});

    // Assert
    verify(converter).convert(isA(Object.class), isA(Class.class));
    verify(testMethodExpression).invoke(isA(ELContext.class), isA(Object[].class));
    assertEquals("Invoke", actualInvokeResult);
    assertEquals(1, paramTypes.length);
    Class<Object> expectedResultClass = Object.class;
    assertEquals(expectedResultClass, paramTypes[0]);
  }

  /**
   * Test
   * {@link AstIdentifier#invoke(Bindings, ELContext, Class, Class[], Object[])}.
   * <ul>
   *   <li>Then throw {@link PropertyNotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstIdentifier#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(Bindings, ELContext, Class, Class[], Object[]); then throw PropertyNotFoundException")
  void testInvoke_thenThrowPropertyNotFoundException() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier(null, 0);
    new PropertyNotFoundException("An error occurred");
    Bindings bindings = new Bindings(new Method[]{null}, new ValueExpression[]{null});

    SimpleContext context = new SimpleContext();
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(PropertyNotFoundException.class,
        () -> astIdentifier.invoke(bindings, context, returnType, new Class[]{forNameResult}, new Object[]{"Params"}));
  }

  /**
   * Test
   * {@link AstIdentifier#invoke(Bindings, ELContext, Class, Class[], Object[])}.
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext(ELResolver)} with resolver is
   * {@link ArrayELResolver#ArrayELResolver()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstIdentifier#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(Bindings, ELContext, Class, Class[], Object[]); when SimpleContext(ELResolver) with resolver is ArrayELResolver()")
  void testInvoke_whenSimpleContextWithResolverIsArrayELResolver() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier(null, 0);
    new PropertyNotFoundException("An error occurred");
    Bindings bindings = new Bindings(new Method[]{null}, new ValueExpression[]{null});

    SimpleContext context = new SimpleContext(new ArrayELResolver());
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(PropertyNotFoundException.class,
        () -> astIdentifier.invoke(bindings, context, returnType, new Class[]{forNameResult}, new Object[]{"Params"}));
  }

  /**
   * Test
   * {@link AstIdentifier#invoke(Bindings, ELContext, Class, Class[], Object[])}.
   * <ul>
   *   <li>When {@link SimpleContext#SimpleContext(ELResolver)} with resolver is
   * {@link RootPropertyResolver#RootPropertyResolver()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstIdentifier#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  @DisplayName("Test invoke(Bindings, ELContext, Class, Class[], Object[]); when SimpleContext(ELResolver) with resolver is RootPropertyResolver()")
  void testInvoke_whenSimpleContextWithResolverIsRootPropertyResolver() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier(null, 0);
    new PropertyNotFoundException("An error occurred");
    Bindings bindings = new Bindings(new Method[]{null}, new ValueExpression[]{null});

    SimpleContext context = new SimpleContext(new RootPropertyResolver());
    Class<Object> returnType = Object.class;
    Class<Object> forNameResult = Object.class;

    // Act and Assert
    assertThrows(PropertyNotFoundException.class,
        () -> astIdentifier.invoke(bindings, context, returnType, new Class[]{forNameResult}, new Object[]{"Params"}));
  }

  /**
   * Test {@link AstIdentifier#appendStructure(StringBuilder, Bindings)}.
   * <p>
   * Method under test:
   * {@link AstIdentifier#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings)")
  void testAppendStructure() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    StringBuilder b = new StringBuilder("foo");

    // Act
    astIdentifier.appendStructure(b, new Bindings(new Method[]{null}, new ValueExpression[]{null}));

    // Assert
    assertEquals("fooName", b.toString());
  }

  /**
   * Test {@link AstIdentifier#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Given {@link AstIdentifier#AstIdentifier(String, int)} with {@code Name}
   * and index is minus one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstIdentifier#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); given AstIdentifier(String, int) with 'Name' and index is minus one")
  void testAppendStructure_givenAstIdentifierWithNameAndIndexIsMinusOne() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", -1);
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astIdentifier.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("fooName", b.toString());
  }

  /**
   * Test {@link AstIdentifier#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Given {@link AstIdentifier#AstIdentifier(String, int)} with {@code Name}
   * and index is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstIdentifier#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); given AstIdentifier(String, int) with 'Name' and index is one")
  void testAppendStructure_givenAstIdentifierWithNameAndIndexIsOne() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 1);
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astIdentifier.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("fooName", b.toString());
  }

  /**
   * Test {@link AstIdentifier#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Given {@link AstIdentifier#AstIdentifier(String, int)} with {@code Name}
   * and index is one.</li>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstIdentifier#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); given AstIdentifier(String, int) with 'Name' and index is one; when 'null'")
  void testAppendStructure_givenAstIdentifierWithNameAndIndexIsOne_whenNull() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 1);
    StringBuilder b = new StringBuilder("foo");

    // Act
    astIdentifier.appendStructure(b, null);

    // Assert
    assertEquals("fooName", b.toString());
  }

  /**
   * Test {@link AstIdentifier#appendStructure(StringBuilder, Bindings)}.
   * <ul>
   *   <li>Then {@link StringBuilder#StringBuilder(String)} with {@code foo}
   * toString is {@code foo<var>}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AstIdentifier#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  @DisplayName("Test appendStructure(StringBuilder, Bindings); then StringBuilder(String) with 'foo' toString is 'foo<var>'")
  void testAppendStructure_thenStringBuilderWithFooToStringIsFooVar() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act
    astIdentifier.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Assert
    assertEquals("foo<var>", b.toString());
  }

  /**
   * Test {@link AstIdentifier#getChild(int)}.
   * <ul>
   *   <li>Given {@link AstIdentifier#AstIdentifier(String, int)} with {@code Name}
   * and index is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstIdentifier#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); given AstIdentifier(String, int) with 'Name' and index is one")
  void testGetChild_givenAstIdentifierWithNameAndIndexIsOne() {
    // Arrange, Act and Assert
    assertNull((new AstIdentifier("Name", 1)).getChild(1));
  }

  /**
   * Test {@link AstIdentifier#getChild(int)}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AstIdentifier#getChild(int)}
   */
  @Test
  @DisplayName("Test getChild(int); given 'java.lang.Object'")
  void testGetChild_givenJavaLangObject() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 1);
    StringBuilder b = new StringBuilder("foo");
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    astIdentifier.appendStructure(b,
        new Bindings(new Method[]{null}, new ValueExpression[]{new ObjectValueExpression(converter, "Object", type)}));

    // Act and Assert
    assertNull(astIdentifier.getChild(1));
  }
}
