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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import jakarta.el.ELContext;
import jakarta.el.ELException;
import java.util.List;
import java.util.Properties;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.core.el.juel.misc.TypeConverterImpl;
import org.activiti.core.el.juel.tree.ExpressionNode;
import org.activiti.core.el.juel.tree.FunctionNode;
import org.activiti.core.el.juel.tree.Tree;
import org.activiti.core.el.juel.tree.TreeBuilder;
import org.activiti.core.el.juel.tree.TreeBuilderException;
import org.activiti.core.el.juel.tree.TreeStore;
import org.activiti.core.el.juel.tree.impl.Builder;
import org.activiti.core.el.juel.tree.impl.Cache;
import org.activiti.core.el.juel.tree.impl.ast.AstText;
import org.activiti.core.el.juel.util.SimpleContext;
import org.junit.jupiter.api.Test;

class ExpressionFactoryImplDiffblueTest {
  /**
   * Method under test:
   * {@link ExpressionFactoryImpl#createTreeStore(int, ExpressionFactoryImpl.Profile, Properties)}
   */
  @Test
  void testCreateTreeStore() {
    // Arrange
    ExpressionFactoryImpl expressionFactoryImpl = new ExpressionFactoryImpl();

    // Act and Assert
    assertTrue(expressionFactoryImpl.createTreeStore(3, ExpressionFactoryImpl.Profile.JEE5, new Properties())
        .getBuilder() instanceof Builder);
  }

  /**
   * Method under test:
   * {@link ExpressionFactoryImpl#createTreeStore(int, ExpressionFactoryImpl.Profile, Properties)}
   */
  @Test
  void testCreateTreeStore2() {
    // Arrange
    ExpressionFactoryImpl expressionFactoryImpl = new ExpressionFactoryImpl();

    // Act and Assert
    assertTrue(expressionFactoryImpl.createTreeStore(0, ExpressionFactoryImpl.Profile.JEE5, new Properties())
        .getBuilder() instanceof Builder);
  }

  /**
   * Method under test:
   * {@link ExpressionFactoryImpl#createTreeStore(int, ExpressionFactoryImpl.Profile, Properties)}
   */
  @Test
  void testCreateTreeStore3() {
    // Arrange
    ExpressionFactoryImpl expressionFactoryImpl = new ExpressionFactoryImpl();

    // Act and Assert
    assertTrue(expressionFactoryImpl.createTreeStore(3, ExpressionFactoryImpl.Profile.JEE6, new Properties())
        .getBuilder() instanceof Builder);
  }

  /**
   * Method under test:
   * {@link ExpressionFactoryImpl#createTypeConverter(Properties)}
   */
  @Test
  void testCreateTypeConverter() throws ELException {
    // Arrange
    ExpressionFactoryImpl expressionFactoryImpl = new ExpressionFactoryImpl();

    // Act
    TypeConverter actualCreateTypeConverterResult = expressionFactoryImpl.createTypeConverter(new Properties());
    Class<Object> type = Object.class;

    // Assert
    assertTrue(actualCreateTypeConverterResult instanceof TypeConverterImpl);
    assertEquals("Value", actualCreateTypeConverterResult.convert("Value", type));
  }

  /**
   * Method under test:
   * {@link ExpressionFactoryImpl#createTypeConverter(Properties)}
   */
  @Test
  void testCreateTypeConverter2() throws ELException {
    // Arrange and Act
    TypeConverter actualCreateTypeConverterResult = (new ExpressionFactoryImpl()).createTypeConverter(null);
    Class<Object> type = Object.class;

    // Assert
    assertTrue(actualCreateTypeConverterResult instanceof TypeConverterImpl);
    assertEquals("Value", actualCreateTypeConverterResult.convert("Value", type));
  }

  /**
   * Method under test:
   * {@link ExpressionFactoryImpl#createTreeBuilder(Properties, Builder.Feature[])}
   */
  @Test
  void testCreateTreeBuilder() throws TreeBuilderException {
    // Arrange
    ExpressionFactoryImpl expressionFactoryImpl = new ExpressionFactoryImpl();

    // Act
    TreeBuilder actualCreateTreeBuilderResult = expressionFactoryImpl.createTreeBuilder(new Properties(),
        Builder.Feature.METHOD_INVOCATIONS);
    Tree actualBuildResult = actualCreateTreeBuilderResult.build("Expression");

    // Assert
    Tree buildResult = actualCreateTreeBuilderResult.build("Expression");
    Iterable<FunctionNode> functionNodes = buildResult.getFunctionNodes();
    assertTrue(functionNodes instanceof List);
    assertTrue(actualCreateTreeBuilderResult instanceof Builder);
    ExpressionNode root = buildResult.getRoot();
    assertTrue(root instanceof AstText);
    ExpressionNode root2 = actualBuildResult.getRoot();
    assertTrue(root2 instanceof AstText);
    assertEquals(0, root.getCardinality());
    assertEquals(0, root2.getCardinality());
    assertFalse(root.isLeftValue());
    assertFalse(root2.isLeftValue());
    assertFalse(root.isMethodInvocation());
    assertFalse(root2.isMethodInvocation());
    assertFalse(buildResult.isDeferred());
    assertFalse(actualBuildResult.isDeferred());
    assertTrue(((List<FunctionNode>) functionNodes).isEmpty());
    assertTrue(root.isLiteralText());
    assertTrue(root2.isLiteralText());
    assertTrue(((Builder) actualCreateTreeBuilderResult).isEnabled(Builder.Feature.METHOD_INVOCATIONS));
    assertSame(functionNodes, actualBuildResult.getFunctionNodes());
    assertSame(functionNodes, buildResult.getIdentifierNodes());
    assertSame(functionNodes, actualBuildResult.getIdentifierNodes());
  }

  /**
   * Method under test:
   * {@link ExpressionFactoryImpl#createTreeBuilder(Properties, Builder.Feature[])}
   */
  @Test
  void testCreateTreeBuilder2() throws TreeBuilderException {
    // Arrange
    ExpressionFactoryImpl expressionFactoryImpl = new ExpressionFactoryImpl();

    // Act
    TreeBuilder actualCreateTreeBuilderResult = expressionFactoryImpl.createTreeBuilder(new Properties(),
        Builder.Feature.METHOD_INVOCATIONS);
    Tree actualBuildResult = actualCreateTreeBuilderResult.build("");

    // Assert
    Tree buildResult = actualCreateTreeBuilderResult.build("Expression");
    Iterable<FunctionNode> functionNodes = buildResult.getFunctionNodes();
    assertTrue(functionNodes instanceof List);
    assertTrue(actualCreateTreeBuilderResult instanceof Builder);
    ExpressionNode root = actualBuildResult.getRoot();
    assertTrue(root instanceof AstText);
    ExpressionNode root2 = buildResult.getRoot();
    assertTrue(root2 instanceof AstText);
    assertEquals(0, root.getCardinality());
    assertEquals(0, root2.getCardinality());
    assertFalse(root.isLeftValue());
    assertFalse(root2.isLeftValue());
    assertFalse(root.isMethodInvocation());
    assertFalse(root2.isMethodInvocation());
    assertFalse(actualBuildResult.isDeferred());
    assertFalse(buildResult.isDeferred());
    assertTrue(((List<FunctionNode>) functionNodes).isEmpty());
    assertTrue(root.isLiteralText());
    assertTrue(root2.isLiteralText());
    assertTrue(((Builder) actualCreateTreeBuilderResult).isEnabled(Builder.Feature.METHOD_INVOCATIONS));
    assertSame(functionNodes, actualBuildResult.getFunctionNodes());
    assertSame(functionNodes, actualBuildResult.getIdentifierNodes());
    assertSame(functionNodes, buildResult.getIdentifierNodes());
  }

  /**
   * Method under test: {@link ExpressionFactoryImpl#coerceToType(Object, Class)}
   */
  @Test
  void testCoerceToType() {
    // Arrange
    ExpressionFactoryImpl expressionFactoryImpl = new ExpressionFactoryImpl();
    Class<Object> targetType = Object.class;

    // Act and Assert
    assertEquals("Obj", expressionFactoryImpl.coerceToType("Obj", targetType));
  }

  /**
   * Method under test:
   * {@link ExpressionFactoryImpl#createValueExpression(ELContext, String, Class)}
   */
  @Test
  void testCreateValueExpression() {
    // Arrange
    ExpressionFactoryImpl expressionFactoryImpl = new ExpressionFactoryImpl();
    SimpleContext context = new SimpleContext();
    Class<Object> expectedType = Object.class;

    // Act
    TreeValueExpression actualCreateValueExpressionResult = expressionFactoryImpl.createValueExpression(context,
        "Expression", expectedType);

    // Assert
    assertEquals("Expression", actualCreateValueExpressionResult.getExpressionString());
    assertFalse(actualCreateValueExpressionResult.isDeferred());
    assertFalse(actualCreateValueExpressionResult.isLeftValue());
    assertTrue(actualCreateValueExpressionResult.isLiteralText());
    Class<Object> expectedExpectedType = Object.class;
    Class<?> expectedType2 = actualCreateValueExpressionResult.getExpectedType();
    assertEquals(expectedExpectedType, expectedType2);
    assertSame(expectedType, expectedType2);
  }

  /**
   * Method under test:
   * {@link ExpressionFactoryImpl#createValueExpression(ELContext, String, Class)}
   */
  @Test
  void testCreateValueExpression2() {
    // Arrange
    ExpressionFactoryImpl expressionFactoryImpl = new ExpressionFactoryImpl();
    SimpleContext context = new SimpleContext();
    Class<Object> expectedType = Object.class;

    // Act
    TreeValueExpression actualCreateValueExpressionResult = expressionFactoryImpl.createValueExpression(context, "",
        expectedType);

    // Assert
    assertEquals("", actualCreateValueExpressionResult.getExpressionString());
    assertFalse(actualCreateValueExpressionResult.isDeferred());
    assertFalse(actualCreateValueExpressionResult.isLeftValue());
    assertTrue(actualCreateValueExpressionResult.isLiteralText());
    Class<Object> expectedExpectedType = Object.class;
    Class<?> expectedType2 = actualCreateValueExpressionResult.getExpectedType();
    assertEquals(expectedExpectedType, expectedType2);
    assertSame(expectedType, expectedType2);
  }

  /**
   * Method under test:
   * {@link ExpressionFactoryImpl#createValueExpression(ELContext, String, Class)}
   */
  @Test
  void testCreateValueExpression3() {
    // Arrange
    ExpressionFactoryImpl expressionFactoryImpl = new ExpressionFactoryImpl();

    SimpleContext context = new SimpleContext();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    context.setVariable("Name", new ObjectValueExpression(converter, "Object", type));
    Class<Object> expectedType = Object.class;

    // Act
    TreeValueExpression actualCreateValueExpressionResult = expressionFactoryImpl.createValueExpression(context,
        "Expression", expectedType);

    // Assert
    assertEquals("Expression", actualCreateValueExpressionResult.getExpressionString());
    assertFalse(actualCreateValueExpressionResult.isDeferred());
    assertFalse(actualCreateValueExpressionResult.isLeftValue());
    assertTrue(actualCreateValueExpressionResult.isLiteralText());
    Class<Object> expectedExpectedType = Object.class;
    Class<?> expectedType2 = actualCreateValueExpressionResult.getExpectedType();
    assertEquals(expectedExpectedType, expectedType2);
    assertSame(expectedType, expectedType2);
  }

  /**
   * Method under test:
   * {@link ExpressionFactoryImpl#createValueExpression(Object, Class)}
   */
  @Test
  void testCreateValueExpression4() {
    // Arrange
    ExpressionFactoryImpl expressionFactoryImpl = new ExpressionFactoryImpl();
    Class<Object> expectedType = Object.class;

    // Act
    ObjectValueExpression actualCreateValueExpressionResult = expressionFactoryImpl.createValueExpression("Instance",
        expectedType);

    // Assert
    assertNull(actualCreateValueExpressionResult.getExpressionString());
    assertFalse(actualCreateValueExpressionResult.isLiteralText());
    Class<Object> expectedExpectedType = Object.class;
    Class<?> expectedType2 = actualCreateValueExpressionResult.getExpectedType();
    assertEquals(expectedExpectedType, expectedType2);
    assertSame(expectedType, expectedType2);
  }

  /**
   * Method under test:
   * {@link ExpressionFactoryImpl#createMethodExpression(ELContext, String, Class, Class[])}
   */
  @Test
  void testCreateMethodExpression() {
    // Arrange
    ExpressionFactoryImpl expressionFactoryImpl = new ExpressionFactoryImpl();
    SimpleContext context = new SimpleContext();
    Class<Object> expectedReturnType = Object.class;
    Class<Object> forNameResult = Object.class;
    Class<?>[] expectedParamTypes = new Class[]{forNameResult};

    // Act
    TreeMethodExpression actualCreateMethodExpressionResult = expressionFactoryImpl.createMethodExpression(context,
        "Expression", expectedReturnType, expectedParamTypes);

    // Assert
    assertEquals("Expression", actualCreateMethodExpressionResult.getExpressionString());
    assertEquals(1, expectedParamTypes.length);
    assertFalse(actualCreateMethodExpressionResult.isDeferred());
    assertFalse(actualCreateMethodExpressionResult.isParametersProvided());
    assertTrue(actualCreateMethodExpressionResult.isLiteralText());
    Class<Object> expectedResultClass = Object.class;
    Class<?> resultClass = expectedParamTypes[0];
    assertEquals(expectedResultClass, resultClass);
    assertSame(forNameResult, resultClass);
  }

  /**
   * Method under test:
   * {@link ExpressionFactoryImpl#createMethodExpression(ELContext, String, Class, Class[])}
   */
  @Test
  void testCreateMethodExpression2() {
    // Arrange
    ExpressionFactoryImpl expressionFactoryImpl = new ExpressionFactoryImpl();
    SimpleContext context = new SimpleContext();
    Class<Object> expectedReturnType = Object.class;
    Class<Object> forNameResult = Object.class;
    Class<?>[] expectedParamTypes = new Class[]{forNameResult};

    // Act
    TreeMethodExpression actualCreateMethodExpressionResult = expressionFactoryImpl.createMethodExpression(context, "",
        expectedReturnType, expectedParamTypes);

    // Assert
    assertEquals("", actualCreateMethodExpressionResult.getExpressionString());
    assertEquals(1, expectedParamTypes.length);
    assertFalse(actualCreateMethodExpressionResult.isDeferred());
    assertFalse(actualCreateMethodExpressionResult.isParametersProvided());
    assertTrue(actualCreateMethodExpressionResult.isLiteralText());
    Class<Object> expectedResultClass = Object.class;
    Class<?> resultClass = expectedParamTypes[0];
    assertEquals(expectedResultClass, resultClass);
    assertSame(forNameResult, resultClass);
  }

  /**
   * Method under test:
   * {@link ExpressionFactoryImpl#createMethodExpression(ELContext, String, Class, Class[])}
   */
  @Test
  void testCreateMethodExpression3() {
    // Arrange
    ExpressionFactoryImpl expressionFactoryImpl = new ExpressionFactoryImpl();

    SimpleContext context = new SimpleContext();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    context.setVariable("Name", new ObjectValueExpression(converter, "Object", type));
    Class<Object> expectedReturnType = Object.class;
    Class<Object> forNameResult = Object.class;
    Class<?>[] expectedParamTypes = new Class[]{forNameResult};

    // Act
    TreeMethodExpression actualCreateMethodExpressionResult = expressionFactoryImpl.createMethodExpression(context,
        "Expression", expectedReturnType, expectedParamTypes);

    // Assert
    assertEquals("Expression", actualCreateMethodExpressionResult.getExpressionString());
    assertEquals(1, expectedParamTypes.length);
    assertFalse(actualCreateMethodExpressionResult.isDeferred());
    assertFalse(actualCreateMethodExpressionResult.isParametersProvided());
    assertTrue(actualCreateMethodExpressionResult.isLiteralText());
    Class<Object> expectedResultClass = Object.class;
    Class<?> resultClass = expectedParamTypes[0];
    assertEquals(expectedResultClass, resultClass);
    assertSame(forNameResult, resultClass);
  }

  /**
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(TreeStore, TypeConverter)}
   */
  @Test
  void testNewExpressionFactoryImpl() {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);

    // Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl(new TreeStore(builder, new Cache(3)),
        mock(TypeConverter.class));

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Method under test: {@link ExpressionFactoryImpl#ExpressionFactoryImpl()}
   */
  @Test
  void testNewExpressionFactoryImpl2() {
    // Arrange and Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl();

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties)}
   */
  @Test
  void testNewExpressionFactoryImpl3() {
    // Arrange and Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl(new Properties());

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties)}
   */
  @Test
  void testNewExpressionFactoryImpl4() {
    // Arrange and Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl((Properties) null);

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties)}
   */
  @Test
  void testNewExpressionFactoryImpl5() {
    // Arrange
    Properties properties = new Properties();
    properties.put(ExpressionFactoryImpl.PROP_CACHE_SIZE, "Properties");

    // Act and Assert
    assertThrows(ELException.class, () -> new ExpressionFactoryImpl(properties));
  }

  /**
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties)}
   */
  @Test
  void testNewExpressionFactoryImpl6() {
    // Arrange
    Properties properties = new Properties();
    properties.put(ExpressionFactoryImpl.PROP_METHOD_INVOCATIONS, "Properties");

    // Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl(properties);

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties)}
   */
  @Test
  void testNewExpressionFactoryImpl7() {
    // Arrange
    Properties properties = new Properties();
    properties.put(ExpressionFactoryImpl.PROP_VAR_ARGS, "Properties");

    // Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl(properties);

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties)}
   */
  @Test
  void testNewExpressionFactoryImpl8() {
    // Arrange
    Properties properties = new Properties();
    properties.put(ExpressionFactoryImpl.PROP_CACHE_SIZE, "42");

    // Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl(properties);

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties, TypeConverter)}
   */
  @Test
  void testNewExpressionFactoryImpl9() {
    // Arrange and Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl(new Properties(),
        mock(TypeConverter.class));

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties, TypeConverter)}
   */
  @Test
  void testNewExpressionFactoryImpl10() {
    // Arrange and Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl((Properties) null,
        mock(TypeConverter.class));

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties, TypeConverter)}
   */
  @Test
  void testNewExpressionFactoryImpl11() {
    // Arrange
    Properties properties = new Properties();
    properties.put(ExpressionFactoryImpl.PROP_CACHE_SIZE, "Properties");

    // Act and Assert
    assertThrows(ELException.class, () -> new ExpressionFactoryImpl(properties, mock(TypeConverter.class)));

  }

  /**
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties, TypeConverter)}
   */
  @Test
  void testNewExpressionFactoryImpl12() {
    // Arrange
    Properties properties = new Properties();
    properties.put(ExpressionFactoryImpl.PROP_METHOD_INVOCATIONS, "Properties");

    // Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl(properties,
        mock(TypeConverter.class));

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties, TypeConverter)}
   */
  @Test
  void testNewExpressionFactoryImpl13() {
    // Arrange
    Properties properties = new Properties();
    properties.put(ExpressionFactoryImpl.PROP_VAR_ARGS, "Properties");

    // Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl(properties,
        mock(TypeConverter.class));

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(Properties, TypeConverter)}
   */
  @Test
  void testNewExpressionFactoryImpl14() {
    // Arrange
    Properties properties = new Properties();
    properties.put(ExpressionFactoryImpl.PROP_CACHE_SIZE, "42");

    // Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl(properties,
        mock(TypeConverter.class));

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(ExpressionFactoryImpl.Profile)}
   */
  @Test
  void testNewExpressionFactoryImpl15() {
    // Arrange and Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl(ExpressionFactoryImpl.Profile.JEE5);

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(ExpressionFactoryImpl.Profile)}
   */
  @Test
  void testNewExpressionFactoryImpl16() {
    // Arrange and Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl(ExpressionFactoryImpl.Profile.JEE6);

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(ExpressionFactoryImpl.Profile, Properties)}
   */
  @Test
  void testNewExpressionFactoryImpl17() {
    // Arrange and Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl(ExpressionFactoryImpl.Profile.JEE5,
        new Properties());

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(ExpressionFactoryImpl.Profile, Properties)}
   */
  @Test
  void testNewExpressionFactoryImpl18() {
    // Arrange and Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl(ExpressionFactoryImpl.Profile.JEE6,
        new Properties());

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(ExpressionFactoryImpl.Profile, Properties, TypeConverter)}
   */
  @Test
  void testNewExpressionFactoryImpl19() {
    // Arrange and Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl(ExpressionFactoryImpl.Profile.JEE5,
        new Properties(), mock(TypeConverter.class));

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(ExpressionFactoryImpl.Profile, Properties, TypeConverter)}
   */
  @Test
  void testNewExpressionFactoryImpl20() {
    // Arrange and Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl(ExpressionFactoryImpl.Profile.JEE6,
        new Properties(), mock(TypeConverter.class));

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Method under test:
   * {@link ExpressionFactoryImpl#ExpressionFactoryImpl(TreeStore)}
   */
  @Test
  void testNewExpressionFactoryImpl21() {
    // Arrange
    TreeBuilder builder = mock(TreeBuilder.class);

    // Act
    ExpressionFactoryImpl actualExpressionFactoryImpl = new ExpressionFactoryImpl(new TreeStore(builder, new Cache(3)));

    // Assert
    assertNull(actualExpressionFactoryImpl.getStreamELResolver());
    assertNull(actualExpressionFactoryImpl.getInitFunctionMap());
  }

  /**
   * Method under test:
   * {@link ExpressionFactoryImpl.Profile#contains(Builder.Feature)}
   */
  @Test
  void testProfileContains() {
    // Arrange, Act and Assert
    assertFalse(ExpressionFactoryImpl.Profile.JEE5.contains(Builder.Feature.METHOD_INVOCATIONS));
    assertTrue(ExpressionFactoryImpl.Profile.JEE6.contains(Builder.Feature.METHOD_INVOCATIONS));
  }

  /**
   * Method under test: {@link ExpressionFactoryImpl.Profile#features()}
   */
  @Test
  void testProfileFeatures() {
    // Arrange, Act and Assert
    assertEquals(0, ExpressionFactoryImpl.Profile.JEE5.features().length);
  }
}
