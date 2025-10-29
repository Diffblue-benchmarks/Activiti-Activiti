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
import org.activiti.core.el.juel.util.RootPropertyResolver;
import org.activiti.core.el.juel.util.SimpleContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AstIdentifierDiffblueTest {
  /**
   * Method under test: {@link AstIdentifier#getType(Bindings, ELContext)}
   */
  @Test
  void testGetType() {
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
   * Method under test: {@link AstIdentifier#getType(Bindings, ELContext)}
   */
  @Test
  void testGetType2() {
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
   * Method under test: {@link AstIdentifier#getType(Bindings, ELContext)}
   */
  @Test
  void testGetType3() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier(null, 0);
    Bindings bindings = new Bindings(new Method[]{null}, new ValueExpression[]{null});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astIdentifier.getType(bindings, new SimpleContext()));
  }

  /**
   * Method under test: {@link AstIdentifier#getType(Bindings, ELContext)}
   */
  @Test
  void testGetType4() {
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
   * Method under test: {@link AstIdentifier#getType(Bindings, ELContext)}
   */
  @Test
  void testGetType5() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Bindings bindings = new Bindings(new Method[]{null}, new ValueExpression[]{null});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class,
        () -> astIdentifier.getType(bindings, new SimpleContext(new ArrayELResolver())));
  }

  /**
   * Method under test:
   * {@link AstIdentifier#getValueReference(Bindings, ELContext)}
   */
  @Test
  void testGetValueReference() {
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
   * Method under test:
   * {@link AstIdentifier#getValueReference(Bindings, ELContext)}
   */
  @Test
  void testGetValueReference2() {
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
   * Method under test:
   * {@link AstIdentifier#getValueReference(Bindings, ELContext)}
   */
  @Test
  void testGetValueReference3() throws TreeBuilderException {
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
   * Method under test: {@link AstIdentifier#eval(Bindings, ELContext)}
   */
  @Test
  void testEval() throws ELException {
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
   * Method under test: {@link AstIdentifier#eval(Bindings, ELContext)}
   */
  @Test
  void testEval2() throws ELException {
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
   * Method under test:
   * {@link AstIdentifier#setValue(Bindings, ELContext, Object)}
   */
  @Test
  void testSetValue() {
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
   * Method under test:
   * {@link AstIdentifier#setValue(Bindings, ELContext, Object)}
   */
  @Test
  void testSetValue2() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier(null, 0);
    Bindings bindings = new Bindings(new Method[]{null}, new ValueExpression[]{null});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astIdentifier.setValue(bindings, new SimpleContext(), "Value"));
  }

  /**
   * Method under test:
   * {@link AstIdentifier#setValue(Bindings, ELContext, Object)}
   */
  @Test
  void testSetValue3() {
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
   * Method under test:
   * {@link AstIdentifier#setValue(Bindings, ELContext, Object)}
   */
  @Test
  void testSetValue4() {
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
   * Method under test:
   * {@link AstIdentifier#setValue(Bindings, ELContext, Object)}
   */
  @Test
  void testSetValue5() {
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
   * Method under test:
   * {@link AstIdentifier#setValue(Bindings, ELContext, Object)}
   */
  @Test
  void testSetValue6() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Bindings bindings = new Bindings(new Method[]{null}, new ValueExpression[]{null});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class,
        () -> astIdentifier.setValue(bindings, new SimpleContext(new ArrayELResolver()), "Value"));
  }

  /**
   * Method under test: {@link AstIdentifier#isReadOnly(Bindings, ELContext)}
   */
  @Test
  void testIsReadOnly() {
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
   * Method under test: {@link AstIdentifier#isReadOnly(Bindings, ELContext)}
   */
  @Test
  void testIsReadOnly2() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Bindings bindings = new Bindings(new Method[]{null}, new ValueExpression[]{null});

    SimpleContext context = new SimpleContext();

    // Act and Assert
    assertFalse(astIdentifier.isReadOnly(bindings, context));
    assertTrue(context.isPropertyResolved());
  }

  /**
   * Method under test: {@link AstIdentifier#isReadOnly(Bindings, ELContext)}
   */
  @Test
  void testIsReadOnly3() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier(null, 0);
    Bindings bindings = new Bindings(new Method[]{null}, new ValueExpression[]{null});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class, () -> astIdentifier.isReadOnly(bindings, new SimpleContext()));
  }

  /**
   * Method under test: {@link AstIdentifier#isReadOnly(Bindings, ELContext)}
   */
  @Test
  void testIsReadOnly4() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Bindings bindings = new Bindings(new Method[]{null}, new ValueExpression[]{null});

    SimpleContext context = new SimpleContext(new RootPropertyResolver());

    // Act and Assert
    assertFalse(astIdentifier.isReadOnly(bindings, context));
    assertTrue(context.isPropertyResolved());
  }

  /**
   * Method under test: {@link AstIdentifier#isReadOnly(Bindings, ELContext)}
   */
  @Test
  void testIsReadOnly5() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    Bindings bindings = new Bindings(new Method[]{null}, new ValueExpression[]{null});

    // Act and Assert
    assertThrows(PropertyNotFoundException.class,
        () -> astIdentifier.isReadOnly(bindings, new SimpleContext(new ArrayELResolver())));
  }

  /**
   * Method under test:
   * {@link AstIdentifier#getMethodExpression(Bindings, ELContext, Class, Class[])}
   */
  @Test
  void testGetMethodExpression() throws ELException {
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
   * Method under test:
   * {@link AstIdentifier#getMethodExpression(Bindings, ELContext, Class, Class[])}
   */
  @Test
  void testGetMethodExpression2() throws ELException {
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
    Class<?> resultClass = paramTypes[0];
    assertEquals(expectedResultClass, resultClass);
    assertSame(forNameResult, resultClass);
  }

  /**
   * Method under test:
   * {@link AstIdentifier#getMethodExpression(Bindings, ELContext, Class, Class[])}
   */
  @Test
  void testGetMethodExpression3() throws ELException {
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
   * Method under test:
   * {@link AstIdentifier#getMethodExpression(Bindings, ELContext, Class, Class[])}
   */
  @Test
  void testGetMethodExpression4() throws ELException {
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
   * Method under test:
   * {@link AstIdentifier#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  void testGetMethodInfo() throws ELException {
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
   * Method under test:
   * {@link AstIdentifier#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  void testGetMethodInfo2() throws ELException {
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
   * Method under test:
   * {@link AstIdentifier#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  void testGetMethodInfo3() throws ELException {
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
   * Method under test:
   * {@link AstIdentifier#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  void testGetMethodInfo4() throws ELException {
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
   * Method under test:
   * {@link AstIdentifier#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  void testGetMethodInfo5() throws ELException {
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
   * Method under test:
   * {@link AstIdentifier#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  void testGetMethodInfo6() {
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
   * Method under test:
   * {@link AstIdentifier#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  void testGetMethodInfo7() {
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
   * Method under test:
   * {@link AstIdentifier#getMethodInfo(Bindings, ELContext, Class, Class[])}
   */
  @Test
  void testGetMethodInfo8() {
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
   * Method under test:
   * {@link AstIdentifier#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  void testInvoke() throws ELException {
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
   * Method under test:
   * {@link AstIdentifier#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  void testInvoke2() throws ELException {
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
    Class<?> resultClass = paramTypes[0];
    assertEquals(expectedResultClass, resultClass);
    assertSame(forNameResult, resultClass);
  }

  /**
   * Method under test:
   * {@link AstIdentifier#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  void testInvoke3() throws ELException {
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
   * Method under test:
   * {@link AstIdentifier#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  void testInvoke4() throws ELException {
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
   * Method under test:
   * {@link AstIdentifier#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  void testInvoke5() throws ELException {
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
   * Method under test:
   * {@link AstIdentifier#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  void testInvoke6() {
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
   * Method under test:
   * {@link AstIdentifier#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  void testInvoke7() {
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
   * Method under test:
   * {@link AstIdentifier#invoke(Bindings, ELContext, Class, Class[], Object[])}
   */
  @Test
  void testInvoke8() {
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
   * Method under test:
   * {@link AstIdentifier#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure() {
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
   * Method under test:
   * {@link AstIdentifier#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure2() {
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
   * Method under test:
   * {@link AstIdentifier#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure3() {
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
   * Method under test:
   * {@link AstIdentifier#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure4() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 1);
    StringBuilder b = new StringBuilder("foo");

    // Act
    astIdentifier.appendStructure(b, null);

    // Assert
    assertEquals("fooName", b.toString());
  }

  /**
   * Method under test:
   * {@link AstIdentifier#appendStructure(StringBuilder, Bindings)}
   */
  @Test
  void testAppendStructure5() {
    // Arrange
    AstIdentifier astIdentifier = new AstIdentifier("Name", 0);
    StringBuilder b = new StringBuilder("foo");

    // Act
    astIdentifier.appendStructure(b, new Bindings(new Method[]{null}, new ValueExpression[]{null}));

    // Assert
    assertEquals("fooName", b.toString());
  }

  /**
   * Method under test: {@link AstIdentifier#getChild(int)}
   */
  @Test
  void testGetChild() {
    // Arrange, Act and Assert
    assertNull((new AstIdentifier("Name", 1)).getChild(1));
  }

  /**
   * Method under test: {@link AstIdentifier#getChild(int)}
   */
  @Test
  void testGetChild2() {
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

  /**
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
  void testGettersAndSetters() {
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
  void testGettersAndSetters2() {
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
}
