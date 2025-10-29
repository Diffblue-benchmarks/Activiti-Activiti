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
package org.activiti.core.el.juel.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import jakarta.el.ELResolver;
import jakarta.el.FunctionMapper;
import jakarta.el.ValueExpression;
import jakarta.el.VariableMapper;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.junit.jupiter.api.Test;

class SimpleContextDiffblueTest {
  /**
   * Method under test: default or parameterless constructor of
   * {@link SimpleContext.Functions}
   */
  @Test
  void testFunctionsNewFunctions() {
    // Arrange, Act and Assert
    assertTrue((new SimpleContext.Functions()).map.isEmpty());
  }

  /**
   * Method under test:
   * {@link SimpleContext.Functions#resolveFunction(String, String)}
   */
  @Test
  void testFunctionsResolveFunction() {
    // Arrange, Act and Assert
    assertNull((new SimpleContext.Functions()).resolveFunction("Prefix", "Local Name"));
  }

  /**
   * Method under test:
   * {@link SimpleContext.Functions#setFunction(String, String, Method)}
   */
  @Test
  void testFunctionsSetFunction() {
    // Arrange
    SimpleContext.Functions functions = new SimpleContext.Functions();

    // Act
    functions.setFunction("Prefix", "Local Name", null);

    // Assert
    Map<String, Method> stringMethodMap = functions.map;
    assertEquals(1, stringMethodMap.size());
    assertNull(stringMethodMap.get("Prefix:Local Name"));
  }

  /**
   * Method under test: {@link SimpleContext#setFunction(String, String, Method)}
   */
  @Test
  void testSetFunction() {
    // Arrange
    SimpleContext simpleContext = new SimpleContext();

    // Act
    simpleContext.setFunction("Prefix", "Local Name", null);

    // Assert
    FunctionMapper functionMapper = simpleContext.getFunctionMapper();
    assertTrue(functionMapper instanceof SimpleContext.Functions);
    VariableMapper variableMapper = simpleContext.getVariableMapper();
    assertTrue(variableMapper instanceof SimpleContext.Variables);
    Map<String, Method> stringMethodMap = ((SimpleContext.Functions) functionMapper).map;
    assertEquals(1, stringMethodMap.size());
    assertNull(stringMethodMap.get("Prefix:Local Name"));
    assertTrue(((SimpleContext.Variables) variableMapper).map.isEmpty());
  }

  /**
   * Method under test: {@link SimpleContext#setFunction(String, String, Method)}
   */
  @Test
  void testSetFunction2() {
    // Arrange
    SimpleContext simpleContext = new SimpleContext();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    simpleContext.setVariable("Name", new ObjectValueExpression(converter, "Object", type));

    // Act
    simpleContext.setFunction("Prefix", "Local Name", null);

    // Assert
    FunctionMapper functionMapper = simpleContext.getFunctionMapper();
    assertTrue(functionMapper instanceof SimpleContext.Functions);
    VariableMapper variableMapper = simpleContext.getVariableMapper();
    assertTrue(variableMapper instanceof SimpleContext.Variables);
    Map<String, Method> stringMethodMap = ((SimpleContext.Functions) functionMapper).map;
    assertEquals(1, stringMethodMap.size());
    assertNull(stringMethodMap.get("Prefix:Local Name"));
    Map<String, ValueExpression> stringValueExpressionMap = ((SimpleContext.Variables) variableMapper).map;
    assertEquals(1, stringValueExpressionMap.size());
    assertTrue(stringValueExpressionMap.containsKey("Name"));
  }

  /**
   * Method under test: {@link SimpleContext#setVariable(String, ValueExpression)}
   */
  @Test
  void testSetVariable() {
    // Arrange
    SimpleContext simpleContext = new SimpleContext();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    ObjectValueExpression expression = new ObjectValueExpression(converter, "Object", type);

    // Act
    ValueExpression actualSetVariableResult = simpleContext.setVariable("Name", expression);

    // Assert
    VariableMapper variableMapper = simpleContext.getVariableMapper();
    assertTrue(variableMapper instanceof SimpleContext.Variables);
    assertNull(actualSetVariableResult);
    Map<String, ValueExpression> stringValueExpressionMap = ((SimpleContext.Variables) variableMapper).map;
    assertEquals(1, stringValueExpressionMap.size());
    assertSame(expression, stringValueExpressionMap.get("Name"));
  }

  /**
   * Method under test: {@link SimpleContext#getFunctionMapper()}
   */
  @Test
  void testGetFunctionMapper() {
    // Arrange
    SimpleContext simpleContext = new SimpleContext();

    // Act
    FunctionMapper actualFunctionMapper = simpleContext.getFunctionMapper();

    // Assert
    assertTrue(actualFunctionMapper instanceof SimpleContext.Functions);
    VariableMapper variableMapper = simpleContext.getVariableMapper();
    assertTrue(variableMapper instanceof SimpleContext.Variables);
    assertTrue(((SimpleContext.Functions) actualFunctionMapper).map.isEmpty());
    assertSame(((SimpleContext.Functions) actualFunctionMapper).map, ((SimpleContext.Variables) variableMapper).map);
  }

  /**
   * Method under test: {@link SimpleContext#getFunctionMapper()}
   */
  @Test
  void testGetFunctionMapper2() {
    // Arrange
    SimpleContext simpleContext = new SimpleContext();
    simpleContext.setFunction("Prefix", "Local Name", null);

    // Act
    FunctionMapper actualFunctionMapper = simpleContext.getFunctionMapper();

    // Assert
    assertTrue(actualFunctionMapper instanceof SimpleContext.Functions);
    VariableMapper variableMapper = simpleContext.getVariableMapper();
    assertTrue(variableMapper instanceof SimpleContext.Variables);
    Map<String, Method> stringMethodMap = ((SimpleContext.Functions) actualFunctionMapper).map;
    assertEquals(1, stringMethodMap.size());
    assertNull(stringMethodMap.get("Prefix:Local Name"));
    assertTrue(((SimpleContext.Variables) variableMapper).map.isEmpty());
  }

  /**
   * Method under test: {@link SimpleContext#getFunctionMapper()}
   */
  @Test
  void testGetFunctionMapper3() {
    // Arrange
    SimpleContext simpleContext = new SimpleContext();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    simpleContext.setVariable("Name", new ObjectValueExpression(converter, "Object", type));

    // Act
    FunctionMapper actualFunctionMapper = simpleContext.getFunctionMapper();

    // Assert
    assertTrue(actualFunctionMapper instanceof SimpleContext.Functions);
    VariableMapper variableMapper = simpleContext.getVariableMapper();
    assertTrue(variableMapper instanceof SimpleContext.Variables);
    Map<String, ValueExpression> stringValueExpressionMap = ((SimpleContext.Variables) variableMapper).map;
    assertEquals(1, stringValueExpressionMap.size());
    assertTrue(stringValueExpressionMap.containsKey("Name"));
    assertTrue(((SimpleContext.Functions) actualFunctionMapper).map.isEmpty());
  }

  /**
   * Method under test: {@link SimpleContext#getVariableMapper()}
   */
  @Test
  void testGetVariableMapper() {
    // Arrange
    SimpleContext simpleContext = new SimpleContext();

    // Act
    VariableMapper actualVariableMapper = simpleContext.getVariableMapper();

    // Assert
    FunctionMapper functionMapper = simpleContext.getFunctionMapper();
    assertTrue(functionMapper instanceof SimpleContext.Functions);
    assertTrue(actualVariableMapper instanceof SimpleContext.Variables);
    assertTrue(((SimpleContext.Variables) actualVariableMapper).map.isEmpty());
    assertSame(((SimpleContext.Variables) actualVariableMapper).map, ((SimpleContext.Functions) functionMapper).map);
  }

  /**
   * Method under test: {@link SimpleContext#getVariableMapper()}
   */
  @Test
  void testGetVariableMapper2() {
    // Arrange
    SimpleContext simpleContext = new SimpleContext();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    ObjectValueExpression expression = new ObjectValueExpression(converter, "Object", type);

    simpleContext.setVariable("Name", expression);

    // Act
    VariableMapper actualVariableMapper = simpleContext.getVariableMapper();

    // Assert
    FunctionMapper functionMapper = simpleContext.getFunctionMapper();
    assertTrue(functionMapper instanceof SimpleContext.Functions);
    assertTrue(actualVariableMapper instanceof SimpleContext.Variables);
    Map<String, ValueExpression> stringValueExpressionMap = ((SimpleContext.Variables) actualVariableMapper).map;
    assertEquals(1, stringValueExpressionMap.size());
    assertTrue(((SimpleContext.Functions) functionMapper).map.isEmpty());
    assertSame(expression, stringValueExpressionMap.get("Name"));
  }

  /**
   * Method under test: {@link SimpleContext#getELResolver()}
   */
  @Test
  void testGetELResolver() {
    // Arrange, Act and Assert
    assertTrue((new SimpleContext()).getELResolver() instanceof SimpleResolver);
  }

  /**
   * Method under test: {@link SimpleContext#getELResolver()}
   */
  @Test
  void testGetELResolver2() {
    // Arrange
    SimpleContext simpleContext = new SimpleContext();
    RootPropertyResolver resolver = new RootPropertyResolver();
    simpleContext.setELResolver(resolver);

    // Act
    ELResolver actualELResolver = simpleContext.getELResolver();

    // Assert
    Iterable<String> propertiesResult = ((RootPropertyResolver) actualELResolver).properties();
    assertTrue(propertiesResult instanceof Set);
    assertTrue(actualELResolver instanceof RootPropertyResolver);
    assertTrue(((Set<String>) propertiesResult).isEmpty());
    assertSame(resolver, actualELResolver);
  }

  /**
   * Method under test: {@link SimpleContext#getELResolver()}
   */
  @Test
  void testGetELResolver3() {
    // Arrange
    SimpleContext simpleContext = new SimpleContext();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    simpleContext.setVariable("Name", new ObjectValueExpression(converter, "Object", type));

    // Act and Assert
    assertTrue(simpleContext.getELResolver() instanceof SimpleResolver);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SimpleContext#SimpleContext()}
   *   <li>{@link SimpleContext#setELResolver(ELResolver)}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    SimpleContext actualSimpleContext = new SimpleContext();
    RootPropertyResolver resolver = new RootPropertyResolver();
    actualSimpleContext.setELResolver(resolver);

    // Assert
    ELResolver eLResolver = actualSimpleContext.getELResolver();
    assertTrue(eLResolver instanceof RootPropertyResolver);
    FunctionMapper functionMapper = actualSimpleContext.getFunctionMapper();
    assertTrue(functionMapper instanceof SimpleContext.Functions);
    assertTrue(actualSimpleContext.getVariableMapper() instanceof SimpleContext.Variables);
    assertNull(actualSimpleContext.getEvaluationListeners());
    assertNull(actualSimpleContext.getLocale());
    assertFalse(actualSimpleContext.isPropertyResolved());
    assertTrue(((SimpleContext.Functions) functionMapper).map.isEmpty());
    assertSame(resolver, eLResolver);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SimpleContext#SimpleContext(ELResolver)}
   *   <li>{@link SimpleContext#setELResolver(ELResolver)}
   * </ul>
   */
  @Test
  void testGettersAndSetters2() {
    // Arrange and Act
    SimpleContext actualSimpleContext = new SimpleContext(new RootPropertyResolver());
    RootPropertyResolver resolver = new RootPropertyResolver();
    actualSimpleContext.setELResolver(resolver);

    // Assert
    ELResolver eLResolver = actualSimpleContext.getELResolver();
    assertTrue(eLResolver instanceof RootPropertyResolver);
    FunctionMapper functionMapper = actualSimpleContext.getFunctionMapper();
    assertTrue(functionMapper instanceof SimpleContext.Functions);
    assertTrue(actualSimpleContext.getVariableMapper() instanceof SimpleContext.Variables);
    assertNull(actualSimpleContext.getEvaluationListeners());
    assertNull(actualSimpleContext.getLocale());
    assertFalse(actualSimpleContext.isPropertyResolved());
    assertTrue(((SimpleContext.Functions) functionMapper).map.isEmpty());
    assertSame(resolver, eLResolver);
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link SimpleContext.Variables}
   */
  @Test
  void testVariablesNewVariables() {
    // Arrange, Act and Assert
    assertTrue((new SimpleContext.Variables()).map.isEmpty());
  }

  /**
   * Method under test: {@link SimpleContext.Variables#resolveVariable(String)}
   */
  @Test
  void testVariablesResolveVariable() {
    // Arrange, Act and Assert
    assertNull((new SimpleContext.Variables()).resolveVariable("Variable"));
  }

  /**
   * Method under test: {@link SimpleContext.Variables#resolveVariable(String)}
   */
  @Test
  void testVariablesResolveVariable2() {
    // Arrange
    SimpleContext.Variables variables = new SimpleContext.Variables();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    ObjectValueExpression expression = new ObjectValueExpression(converter, "Object", type);

    variables.setVariable("Variable", expression);

    // Act and Assert
    assertSame(expression, variables.resolveVariable("Variable"));
  }

  /**
   * Method under test:
   * {@link SimpleContext.Variables#setVariable(String, ValueExpression)}
   */
  @Test
  void testVariablesSetVariable() {
    // Arrange
    SimpleContext.Variables variables = new SimpleContext.Variables();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    ObjectValueExpression expression = new ObjectValueExpression(converter, "Object", type);

    // Act and Assert
    assertNull(variables.setVariable("Variable", expression));
    Map<String, ValueExpression> stringValueExpressionMap = variables.map;
    assertEquals(1, stringValueExpressionMap.size());
    assertSame(expression, stringValueExpressionMap.get("Variable"));
  }
}
