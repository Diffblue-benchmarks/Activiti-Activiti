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
import org.junit.jupiter.api.Test;

class ActivitiElContextDiffblueTest {
  /**
   * Method under test: {@link ActivitiElContext#getFunctionMapper()}
   */
  @Test
  void testGetFunctionMapper() {
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
   * Method under test: {@link ActivitiElContext#getFunctionMapper()}
   */
  @Test
  void testGetFunctionMapper2() {
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
   * Method under test: {@link ActivitiElContext#getFunctionMapper()}
   */
  @Test
  void testGetFunctionMapper3() {
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
   * Method under test: {@link ActivitiElContext#getVariableMapper()}
   */
  @Test
  void testGetVariableMapper() {
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
   * Method under test: {@link ActivitiElContext#getVariableMapper()}
   */
  @Test
  void testGetVariableMapper2() {
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
   * Method under test:
   * {@link ActivitiElContext#setFunction(String, String, Method)}
   */
  @Test
  void testSetFunction() {
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
   * Method under test:
   * {@link ActivitiElContext#setFunction(String, String, Method)}
   */
  @Test
  void testSetFunction2() {
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
   * Method under test:
   * {@link ActivitiElContext#setVariable(String, ValueExpression)}
   */
  @Test
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

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ActivitiElContext#ActivitiElContext()}
   *   <li>{@link ActivitiElContext#getELResolver()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
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
   * Methods under test:
   * <ul>
   *   <li>{@link ActivitiElContext#ActivitiElContext(ELResolver)}
   *   <li>{@link ActivitiElContext#getELResolver()}
   * </ul>
   */
  @Test
  void testGettersAndSetters2() {
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
}
