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
package org.activiti.core.el;

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
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ActivitiElContextDiffblueTest {
  /**
   * Test getters and setters.
   * <ul>
   *   <li>Then return ELResolver is
   * {@link JsonNodeELResolver#JsonNodeELResolver()}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ActivitiElContext#ActivitiElContext(ELResolver)}
   *   <li>{@link ActivitiElContext#getELResolver()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters; then return ELResolver is JsonNodeELResolver()")
  void testGettersAndSetters_thenReturnELResolverIsJsonNodeELResolver() {
    // Arrange
    JsonNodeELResolver elResolver = new JsonNodeELResolver();

    // Act
    ActivitiElContext actualActivitiElContext = new ActivitiElContext(elResolver);
    ELResolver actualELResolver = actualActivitiElContext.getELResolver();

    // Assert
    FunctionMapper functionMapper = actualActivitiElContext.getFunctionMapper();
    assertTrue(functionMapper instanceof ActivitiFunctionMapper);
    assertTrue(actualActivitiElContext.getVariableMapper() instanceof ActivitiVariablesMapper);
    assertNull(actualActivitiElContext.getEvaluationListeners());
    assertNull(actualActivitiElContext.getLocale());
    assertFalse(actualActivitiElContext.isPropertyResolved());
    assertTrue(((ActivitiFunctionMapper) functionMapper).map.isEmpty());
    assertSame(elResolver, actualELResolver);
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>Then return ELResolver is {@code null}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ActivitiElContext#ActivitiElContext()}
   *   <li>{@link ActivitiElContext#getELResolver()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters; then return ELResolver is 'null'")
  void testGettersAndSetters_thenReturnELResolverIsNull() {
    // Arrange and Act
    ActivitiElContext actualActivitiElContext = new ActivitiElContext();
    ELResolver actualELResolver = actualActivitiElContext.getELResolver();

    // Assert
    FunctionMapper functionMapper = actualActivitiElContext.getFunctionMapper();
    assertTrue(functionMapper instanceof ActivitiFunctionMapper);
    assertTrue(actualActivitiElContext.getVariableMapper() instanceof ActivitiVariablesMapper);
    assertNull(actualELResolver);
    assertNull(actualActivitiElContext.getEvaluationListeners());
    assertNull(actualActivitiElContext.getLocale());
    assertFalse(actualActivitiElContext.isPropertyResolved());
    assertTrue(((ActivitiFunctionMapper) functionMapper).map.isEmpty());
  }

  /**
   * Test {@link ActivitiElContext#getFunctionMapper()}.
   * <ul>
   *   <li>Then {@link ActivitiElContext#ActivitiElContext()} VariableMapper
   * {@link ActivitiVariablesMapper#map} is
   * {@link ActivitiFunctionMapper#map}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiElContext#getFunctionMapper()}
   */
  @Test
  @DisplayName("Test getFunctionMapper(); then ActivitiElContext() VariableMapper map is map")
  void testGetFunctionMapper_thenActivitiElContextVariableMapperMapIsMap() {
    // Arrange
    ActivitiElContext activitiElContext = new ActivitiElContext();

    // Act
    FunctionMapper actualFunctionMapper = activitiElContext.getFunctionMapper();

    // Assert
    assertTrue(actualFunctionMapper instanceof ActivitiFunctionMapper);
    VariableMapper variableMapper = activitiElContext.getVariableMapper();
    assertTrue(variableMapper instanceof ActivitiVariablesMapper);
    assertTrue(((ActivitiFunctionMapper) actualFunctionMapper).map.isEmpty());
    assertSame(((ActivitiFunctionMapper) actualFunctionMapper).map, ((ActivitiVariablesMapper) variableMapper).map);
  }

  /**
   * Test {@link ActivitiElContext#getFunctionMapper()}.
   * <ul>
   *   <li>Then {@link ActivitiElContext#ActivitiElContext()} VariableMapper
   * {@link ActivitiVariablesMapper#map} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiElContext#getFunctionMapper()}
   */
  @Test
  @DisplayName("Test getFunctionMapper(); then ActivitiElContext() VariableMapper map size is one")
  void testGetFunctionMapper_thenActivitiElContextVariableMapperMapSizeIsOne() {
    // Arrange
    ActivitiElContext activitiElContext = new ActivitiElContext();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    activitiElContext.setVariable("Name", new ObjectValueExpression(converter, "Object", type));

    // Act
    FunctionMapper actualFunctionMapper = activitiElContext.getFunctionMapper();

    // Assert
    assertTrue(actualFunctionMapper instanceof ActivitiFunctionMapper);
    VariableMapper variableMapper = activitiElContext.getVariableMapper();
    assertTrue(variableMapper instanceof ActivitiVariablesMapper);
    Map<String, ValueExpression> stringValueExpressionMap = ((ActivitiVariablesMapper) variableMapper).map;
    assertEquals(1, stringValueExpressionMap.size());
    assertTrue(stringValueExpressionMap.containsKey("Name"));
    assertTrue(((ActivitiFunctionMapper) actualFunctionMapper).map.isEmpty());
  }

  /**
   * Test {@link ActivitiElContext#getFunctionMapper()}.
   * <ul>
   *   <li>Then return {@link ActivitiFunctionMapper#map} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiElContext#getFunctionMapper()}
   */
  @Test
  @DisplayName("Test getFunctionMapper(); then return map size is one")
  void testGetFunctionMapper_thenReturnMapSizeIsOne() {
    // Arrange
    ActivitiElContext activitiElContext = new ActivitiElContext();
    activitiElContext.setFunction("Prefix", "Local Name", null);

    // Act
    FunctionMapper actualFunctionMapper = activitiElContext.getFunctionMapper();

    // Assert
    assertTrue(actualFunctionMapper instanceof ActivitiFunctionMapper);
    VariableMapper variableMapper = activitiElContext.getVariableMapper();
    assertTrue(variableMapper instanceof ActivitiVariablesMapper);
    Map<String, Method> stringMethodMap = ((ActivitiFunctionMapper) actualFunctionMapper).map;
    assertEquals(1, stringMethodMap.size());
    assertNull(stringMethodMap.get("Prefix:Local Name"));
    assertTrue(((ActivitiVariablesMapper) variableMapper).map.isEmpty());
  }

  /**
   * Test {@link ActivitiElContext#getVariableMapper()}.
   * <ul>
   *   <li>Given {@link ActivitiElContext#ActivitiElContext()}.</li>
   *   <li>Then return {@link ActivitiVariablesMapper#map} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiElContext#getVariableMapper()}
   */
  @Test
  @DisplayName("Test getVariableMapper(); given ActivitiElContext(); then return map Empty")
  void testGetVariableMapper_givenActivitiElContext_thenReturnMapEmpty() {
    // Arrange
    ActivitiElContext activitiElContext = new ActivitiElContext();

    // Act
    VariableMapper actualVariableMapper = activitiElContext.getVariableMapper();

    // Assert
    FunctionMapper functionMapper = activitiElContext.getFunctionMapper();
    assertTrue(functionMapper instanceof ActivitiFunctionMapper);
    assertTrue(actualVariableMapper instanceof ActivitiVariablesMapper);
    assertTrue(((ActivitiVariablesMapper) actualVariableMapper).map.isEmpty());
    assertSame(((ActivitiVariablesMapper) actualVariableMapper).map, ((ActivitiFunctionMapper) functionMapper).map);
  }

  /**
   * Test {@link ActivitiElContext#getVariableMapper()}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>Then return {@link ActivitiVariablesMapper#map} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiElContext#getVariableMapper()}
   */
  @Test
  @DisplayName("Test getVariableMapper(); given 'java.lang.Object'; then return map size is one")
  void testGetVariableMapper_givenJavaLangObject_thenReturnMapSizeIsOne() {
    // Arrange
    ActivitiElContext activitiElContext = new ActivitiElContext();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    ObjectValueExpression expression = new ObjectValueExpression(converter, "Object", type);

    activitiElContext.setVariable("Name", expression);

    // Act
    VariableMapper actualVariableMapper = activitiElContext.getVariableMapper();

    // Assert
    FunctionMapper functionMapper = activitiElContext.getFunctionMapper();
    assertTrue(functionMapper instanceof ActivitiFunctionMapper);
    assertTrue(actualVariableMapper instanceof ActivitiVariablesMapper);
    Map<String, ValueExpression> stringValueExpressionMap = ((ActivitiVariablesMapper) actualVariableMapper).map;
    assertEquals(1, stringValueExpressionMap.size());
    assertTrue(((ActivitiFunctionMapper) functionMapper).map.isEmpty());
    assertSame(expression, stringValueExpressionMap.get("Name"));
  }

  /**
   * Test {@link ActivitiElContext#setFunction(String, String, Method)}.
   * <ul>
   *   <li>Then {@link ActivitiElContext#ActivitiElContext()} VariableMapper
   * {@link ActivitiVariablesMapper#map} Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiElContext#setFunction(String, String, Method)}
   */
  @Test
  @DisplayName("Test setFunction(String, String, Method); then ActivitiElContext() VariableMapper map Empty")
  void testSetFunction_thenActivitiElContextVariableMapperMapEmpty() {
    // Arrange
    ActivitiElContext activitiElContext = new ActivitiElContext();

    // Act
    activitiElContext.setFunction("Prefix", "Local Name", null);

    // Assert
    FunctionMapper functionMapper = activitiElContext.getFunctionMapper();
    assertTrue(functionMapper instanceof ActivitiFunctionMapper);
    VariableMapper variableMapper = activitiElContext.getVariableMapper();
    assertTrue(variableMapper instanceof ActivitiVariablesMapper);
    Map<String, Method> stringMethodMap = ((ActivitiFunctionMapper) functionMapper).map;
    assertEquals(1, stringMethodMap.size());
    assertNull(stringMethodMap.get("Prefix:Local Name"));
    assertTrue(((ActivitiVariablesMapper) variableMapper).map.isEmpty());
  }

  /**
   * Test {@link ActivitiElContext#setFunction(String, String, Method)}.
   * <ul>
   *   <li>Then {@link ActivitiElContext#ActivitiElContext()} VariableMapper
   * {@link ActivitiVariablesMapper#map} size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiElContext#setFunction(String, String, Method)}
   */
  @Test
  @DisplayName("Test setFunction(String, String, Method); then ActivitiElContext() VariableMapper map size is one")
  void testSetFunction_thenActivitiElContextVariableMapperMapSizeIsOne() {
    // Arrange
    ActivitiElContext activitiElContext = new ActivitiElContext();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    activitiElContext.setVariable("Name", new ObjectValueExpression(converter, "Object", type));

    // Act
    activitiElContext.setFunction("Prefix", "Local Name", null);

    // Assert
    FunctionMapper functionMapper = activitiElContext.getFunctionMapper();
    assertTrue(functionMapper instanceof ActivitiFunctionMapper);
    VariableMapper variableMapper = activitiElContext.getVariableMapper();
    assertTrue(variableMapper instanceof ActivitiVariablesMapper);
    Map<String, Method> stringMethodMap = ((ActivitiFunctionMapper) functionMapper).map;
    assertEquals(1, stringMethodMap.size());
    assertNull(stringMethodMap.get("Prefix:Local Name"));
    Map<String, ValueExpression> stringValueExpressionMap = ((ActivitiVariablesMapper) variableMapper).map;
    assertEquals(1, stringValueExpressionMap.size());
    assertTrue(stringValueExpressionMap.containsKey("Name"));
  }

  /**
   * Test {@link ActivitiElContext#setVariable(String, ValueExpression)}.
   * <p>
   * Method under test:
   * {@link ActivitiElContext#setVariable(String, ValueExpression)}
   */
  @Test
  @DisplayName("Test setVariable(String, ValueExpression)")
  void testSetVariable() {
    // Arrange
    ActivitiElContext activitiElContext = new ActivitiElContext();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    ObjectValueExpression expression = new ObjectValueExpression(converter, "Object", type);

    // Act
    ValueExpression actualSetVariableResult = activitiElContext.setVariable("Name", expression);

    // Assert
    VariableMapper variableMapper = activitiElContext.getVariableMapper();
    assertTrue(variableMapper instanceof ActivitiVariablesMapper);
    assertNull(actualSetVariableResult);
    Map<String, ValueExpression> stringValueExpressionMap = ((ActivitiVariablesMapper) variableMapper).map;
    assertEquals(1, stringValueExpressionMap.size());
    assertSame(expression, stringValueExpressionMap.get("Name"));
  }
}
