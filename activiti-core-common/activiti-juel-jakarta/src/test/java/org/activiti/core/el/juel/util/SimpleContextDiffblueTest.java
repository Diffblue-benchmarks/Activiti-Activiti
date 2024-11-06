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
import org.activiti.core.el.juel.util.SimpleContext.Functions;
import org.activiti.core.el.juel.util.SimpleContext.Variables;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SimpleContextDiffblueTest {
  /**
   * Test Functions new {@link Functions} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link SimpleContext.Functions}
   */
  @Test
  @DisplayName("Test Functions new Functions (default constructor)")
  void testFunctionsNewFunctions() {
    // Arrange, Act and Assert
    assertTrue((new SimpleContext.Functions()).map.isEmpty());
  }

  /**
   * Test Functions {@link Functions#resolveFunction(String, String)}.
   * <p>
   * Method under test:
   * {@link SimpleContext.Functions#resolveFunction(String, String)}
   */
  @Test
  @DisplayName("Test Functions resolveFunction(String, String)")
  void testFunctionsResolveFunction() {
    // Arrange, Act and Assert
    assertNull((new SimpleContext.Functions()).resolveFunction("Prefix", "Local Name"));
  }

  /**
   * Test Functions {@link Functions#setFunction(String, String, Method)}.
   * <p>
   * Method under test:
   * {@link SimpleContext.Functions#setFunction(String, String, Method)}
   */
  @Test
  @DisplayName("Test Functions setFunction(String, String, Method)")
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
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SimpleContext#SimpleContext()}
   *   <li>{@link SimpleContext#setELResolver(ELResolver)}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
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
   * Test getters and setters.
   * <ul>
   *   <li>When {@link RootPropertyResolver#RootPropertyResolver()}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SimpleContext#SimpleContext(ELResolver)}
   *   <li>{@link SimpleContext#setELResolver(ELResolver)}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters; when RootPropertyResolver()")
  void testGettersAndSetters_whenRootPropertyResolver() {
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
   * Test {@link SimpleContext#setFunction(String, String, Method)}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>Then {@link SimpleContext#SimpleContext()} VariableMapper
   * {@link Variables#map} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link SimpleContext#setFunction(String, String, Method)}
   */
  @Test
  @DisplayName("Test setFunction(String, String, Method); given 'java.lang.Object'; then SimpleContext() VariableMapper map size is one")
  void testSetFunction_givenJavaLangObject_thenSimpleContextVariableMapperMapSizeIsOne() {
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
   * Test {@link SimpleContext#setFunction(String, String, Method)}.
   * <ul>
   *   <li>Given {@link SimpleContext#SimpleContext()}.</li>
   *   <li>Then {@link SimpleContext#SimpleContext()} VariableMapper
   * {@link Variables#map} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link SimpleContext#setFunction(String, String, Method)}
   */
  @Test
  @DisplayName("Test setFunction(String, String, Method); given SimpleContext(); then SimpleContext() VariableMapper map Empty")
  void testSetFunction_givenSimpleContext_thenSimpleContextVariableMapperMapEmpty() {
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
   * Test {@link SimpleContext#setVariable(String, ValueExpression)}.
   * <p>
   * Method under test: {@link SimpleContext#setVariable(String, ValueExpression)}
   */
  @Test
  @DisplayName("Test setVariable(String, ValueExpression)")
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
   * Test {@link SimpleContext#getFunctionMapper()}.
   * <ul>
   *   <li>Given {@link SimpleContext#SimpleContext()}.</li>
   *   <li>Then {@link SimpleContext#SimpleContext()} VariableMapper
   * {@link Variables#map} is {@link Functions#map}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SimpleContext#getFunctionMapper()}
   */
  @Test
  @DisplayName("Test getFunctionMapper(); given SimpleContext(); then SimpleContext() VariableMapper map is map")
  void testGetFunctionMapper_givenSimpleContext_thenSimpleContextVariableMapperMapIsMap() {
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
   * Test {@link SimpleContext#getFunctionMapper()}.
   * <ul>
   *   <li>Then return {@link Functions#map} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link SimpleContext#getFunctionMapper()}
   */
  @Test
  @DisplayName("Test getFunctionMapper(); then return map size is one")
  void testGetFunctionMapper_thenReturnMapSizeIsOne() {
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
   * Test {@link SimpleContext#getFunctionMapper()}.
   * <ul>
   *   <li>Then {@link SimpleContext#SimpleContext()} VariableMapper
   * {@link Variables#map} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link SimpleContext#getFunctionMapper()}
   */
  @Test
  @DisplayName("Test getFunctionMapper(); then SimpleContext() VariableMapper map size is one")
  void testGetFunctionMapper_thenSimpleContextVariableMapperMapSizeIsOne() {
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
   * Test {@link SimpleContext#getVariableMapper()}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>Then return {@link Variables#map} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link SimpleContext#getVariableMapper()}
   */
  @Test
  @DisplayName("Test getVariableMapper(); given 'java.lang.Object'; then return map size is one")
  void testGetVariableMapper_givenJavaLangObject_thenReturnMapSizeIsOne() {
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
   * Test {@link SimpleContext#getVariableMapper()}.
   * <ul>
   *   <li>Given {@link SimpleContext#SimpleContext()}.</li>
   *   <li>Then return {@link Variables#map} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link SimpleContext#getVariableMapper()}
   */
  @Test
  @DisplayName("Test getVariableMapper(); given SimpleContext(); then return map Empty")
  void testGetVariableMapper_givenSimpleContext_thenReturnMapEmpty() {
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
   * Test {@link SimpleContext#getELResolver()}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>Then return {@link SimpleResolver}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SimpleContext#getELResolver()}
   */
  @Test
  @DisplayName("Test getELResolver(); given 'java.lang.Object'; then return SimpleResolver")
  void testGetELResolver_givenJavaLangObject_thenReturnSimpleResolver() {
    // Arrange
    SimpleContext simpleContext = new SimpleContext();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    simpleContext.setVariable("Name", new ObjectValueExpression(converter, "Object", type));

    // Act and Assert
    assertTrue(simpleContext.getELResolver() instanceof SimpleResolver);
  }

  /**
   * Test {@link SimpleContext#getELResolver()}.
   * <ul>
   *   <li>Given {@link SimpleContext#SimpleContext()}.</li>
   *   <li>Then return {@link SimpleResolver}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SimpleContext#getELResolver()}
   */
  @Test
  @DisplayName("Test getELResolver(); given SimpleContext(); then return SimpleResolver")
  void testGetELResolver_givenSimpleContext_thenReturnSimpleResolver() {
    // Arrange, Act and Assert
    assertTrue((new SimpleContext()).getELResolver() instanceof SimpleResolver);
  }

  /**
   * Test {@link SimpleContext#getELResolver()}.
   * <ul>
   *   <li>Then properties return {@link Set}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SimpleContext#getELResolver()}
   */
  @Test
  @DisplayName("Test getELResolver(); then properties return Set")
  void testGetELResolver_thenPropertiesReturnSet() {
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
   * Test Variables new {@link Variables} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link SimpleContext.Variables}
   */
  @Test
  @DisplayName("Test Variables new Variables (default constructor)")
  void testVariablesNewVariables() {
    // Arrange, Act and Assert
    assertTrue((new SimpleContext.Variables()).map.isEmpty());
  }

  /**
   * Test Variables {@link Variables#resolveVariable(String)}.
   * <p>
   * Method under test: {@link SimpleContext.Variables#resolveVariable(String)}
   */
  @Test
  @DisplayName("Test Variables resolveVariable(String)")
  void testVariablesResolveVariable() {
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
   * Test Variables {@link Variables#resolveVariable(String)}.
   * <ul>
   *   <li>Given {@link Variables} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SimpleContext.Variables#resolveVariable(String)}
   */
  @Test
  @DisplayName("Test Variables resolveVariable(String); given Variables (default constructor); then return 'null'")
  void testVariablesResolveVariable_givenVariables_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new SimpleContext.Variables()).resolveVariable("Variable"));
  }

  /**
   * Test Variables {@link Variables#setVariable(String, ValueExpression)}.
   * <p>
   * Method under test:
   * {@link SimpleContext.Variables#setVariable(String, ValueExpression)}
   */
  @Test
  @DisplayName("Test Variables setVariable(String, ValueExpression)")
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
