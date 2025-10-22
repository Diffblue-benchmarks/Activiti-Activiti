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
import com.diffblue.cover.annotations.MethodsUnderTest;
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
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SimpleContextDiffblueTest {
  /**
   * Test Functions new {@link Functions} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link Functions}
   */
  @Test
  @DisplayName("Test Functions new Functions (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Functions.<init>()"})
  void testFunctionsNewFunctions() {
    // Arrange, Act and Assert
    assertTrue((new Functions()).map.isEmpty());
  }

  /**
   * Test Functions {@link Functions#resolveFunction(String, String)}.
   * <p>
   * Method under test: {@link Functions#resolveFunction(String, String)}
   */
  @Test
  @DisplayName("Test Functions resolveFunction(String, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Method Functions.resolveFunction(String, String)"})
  void testFunctionsResolveFunction() {
    // Arrange, Act and Assert
    assertNull((new Functions()).resolveFunction("Prefix", "Local Name"));
  }

  /**
   * Test Functions {@link Functions#setFunction(String, String, Method)}.
   * <p>
   * Method under test: {@link Functions#setFunction(String, String, Method)}
   */
  @Test
  @DisplayName("Test Functions setFunction(String, String, Method)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Functions.setFunction(String, String, Method)"})
  void testFunctionsSetFunction() {
    // Arrange
    Functions functions = new Functions();

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
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SimpleContext.<init>()", "void SimpleContext.<init>(ELResolver)",
      "void SimpleContext.setELResolver(ELResolver)"})
  void testGettersAndSetters() {
    // Arrange and Act
    SimpleContext actualSimpleContext = new SimpleContext();
    RootPropertyResolver resolver = new RootPropertyResolver();
    actualSimpleContext.setELResolver(resolver);

    // Assert
    ELResolver eLResolver = actualSimpleContext.getELResolver();
    assertTrue(eLResolver instanceof RootPropertyResolver);
    FunctionMapper functionMapper = actualSimpleContext.getFunctionMapper();
    assertTrue(functionMapper instanceof Functions);
    assertTrue(actualSimpleContext.getVariableMapper() instanceof Variables);
    assertNull(actualSimpleContext.getEvaluationListeners());
    assertNull(actualSimpleContext.getLocale());
    assertFalse(actualSimpleContext.isPropertyResolved());
    assertTrue(((Functions) functionMapper).map.isEmpty());
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
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SimpleContext.<init>()", "void SimpleContext.<init>(ELResolver)",
      "void SimpleContext.setELResolver(ELResolver)"})
  void testGettersAndSetters_whenRootPropertyResolver() {
    // Arrange and Act
    SimpleContext actualSimpleContext = new SimpleContext(new RootPropertyResolver());
    RootPropertyResolver resolver = new RootPropertyResolver();
    actualSimpleContext.setELResolver(resolver);

    // Assert
    ELResolver eLResolver = actualSimpleContext.getELResolver();
    assertTrue(eLResolver instanceof RootPropertyResolver);
    FunctionMapper functionMapper = actualSimpleContext.getFunctionMapper();
    assertTrue(functionMapper instanceof Functions);
    assertTrue(actualSimpleContext.getVariableMapper() instanceof Variables);
    assertNull(actualSimpleContext.getEvaluationListeners());
    assertNull(actualSimpleContext.getLocale());
    assertFalse(actualSimpleContext.isPropertyResolved());
    assertTrue(((Functions) functionMapper).map.isEmpty());
    assertSame(resolver, eLResolver);
  }

  /**
   * Test {@link SimpleContext#setFunction(String, String, Method)}.
   * <p>
   * Method under test: {@link SimpleContext#setFunction(String, String, Method)}
   */
  @Test
  @DisplayName("Test setFunction(String, String, Method)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SimpleContext.setFunction(String, String, Method)"})
  void testSetFunction() {
    // Arrange
    SimpleContext simpleContext = new SimpleContext();

    // Act
    simpleContext.setFunction("Prefix", "Local Name", null);

    // Assert
    FunctionMapper functionMapper = simpleContext.getFunctionMapper();
    assertTrue(functionMapper instanceof Functions);
    VariableMapper variableMapper = simpleContext.getVariableMapper();
    assertTrue(variableMapper instanceof Variables);
    Map<String, Method> stringMethodMap = ((Functions) functionMapper).map;
    assertEquals(1, stringMethodMap.size());
    assertNull(stringMethodMap.get("Prefix:Local Name"));
    assertTrue(((Variables) variableMapper).map.isEmpty());
  }

  /**
   * Test {@link SimpleContext#setVariable(String, ValueExpression)}.
   * <p>
   * Method under test: {@link SimpleContext#setVariable(String, ValueExpression)}
   */
  @Test
  @DisplayName("Test setVariable(String, ValueExpression)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ValueExpression SimpleContext.setVariable(String, ValueExpression)"})
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
    assertTrue(variableMapper instanceof Variables);
    assertNull(actualSetVariableResult);
    Map<String, ValueExpression> stringValueExpressionMap = ((Variables) variableMapper).map;
    assertEquals(1, stringValueExpressionMap.size());
    assertSame(expression, stringValueExpressionMap.get("Name"));
  }

  /**
   * Test {@link SimpleContext#getFunctionMapper()}.
   * <ul>
   *   <li>Then return {@link Functions#map} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link SimpleContext#getFunctionMapper()}
   */
  @Test
  @DisplayName("Test getFunctionMapper(); then return map Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FunctionMapper SimpleContext.getFunctionMapper()"})
  void testGetFunctionMapper_thenReturnMapEmpty() {
    // Arrange
    SimpleContext simpleContext = new SimpleContext();

    // Act
    FunctionMapper actualFunctionMapper = simpleContext.getFunctionMapper();

    // Assert
    assertTrue(actualFunctionMapper instanceof Functions);
    VariableMapper variableMapper = simpleContext.getVariableMapper();
    assertTrue(variableMapper instanceof Variables);
    assertTrue(((Functions) actualFunctionMapper).map.isEmpty());
    assertSame(((Functions) actualFunctionMapper).map, ((Variables) variableMapper).map);
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
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FunctionMapper SimpleContext.getFunctionMapper()"})
  void testGetFunctionMapper_thenReturnMapSizeIsOne() {
    // Arrange
    SimpleContext simpleContext = new SimpleContext();
    simpleContext.setFunction("Prefix", "Local Name", null);

    // Act
    FunctionMapper actualFunctionMapper = simpleContext.getFunctionMapper();

    // Assert
    assertTrue(actualFunctionMapper instanceof Functions);
    VariableMapper variableMapper = simpleContext.getVariableMapper();
    assertTrue(variableMapper instanceof Variables);
    Map<String, Method> stringMethodMap = ((Functions) actualFunctionMapper).map;
    assertEquals(1, stringMethodMap.size());
    assertNull(stringMethodMap.get("Prefix:Local Name"));
    assertTrue(((Variables) variableMapper).map.isEmpty());
  }

  /**
   * Test {@link SimpleContext#getVariableMapper()}.
   * <ul>
   *   <li>Given {@code Object}.</li>
   *   <li>Then return {@link Variables#map} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link SimpleContext#getVariableMapper()}
   */
  @Test
  @DisplayName("Test getVariableMapper(); given 'java.lang.Object'; then return map size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"VariableMapper SimpleContext.getVariableMapper()"})
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
    assertTrue(functionMapper instanceof Functions);
    assertTrue(actualVariableMapper instanceof Variables);
    Map<String, ValueExpression> stringValueExpressionMap = ((Variables) actualVariableMapper).map;
    assertEquals(1, stringValueExpressionMap.size());
    assertTrue(((Functions) functionMapper).map.isEmpty());
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
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"VariableMapper SimpleContext.getVariableMapper()"})
  void testGetVariableMapper_givenSimpleContext_thenReturnMapEmpty() {
    // Arrange
    SimpleContext simpleContext = new SimpleContext();

    // Act
    VariableMapper actualVariableMapper = simpleContext.getVariableMapper();

    // Assert
    FunctionMapper functionMapper = simpleContext.getFunctionMapper();
    assertTrue(functionMapper instanceof Functions);
    assertTrue(actualVariableMapper instanceof Variables);
    assertTrue(((Variables) actualVariableMapper).map.isEmpty());
    assertSame(((Variables) actualVariableMapper).map, ((Functions) functionMapper).map);
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
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ELResolver SimpleContext.getELResolver()"})
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
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ELResolver SimpleContext.getELResolver()"})
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
   * Method under test: default or parameterless constructor of {@link Variables}
   */
  @Test
  @DisplayName("Test Variables new Variables (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Variables.<init>()"})
  void testVariablesNewVariables() {
    // Arrange, Act and Assert
    assertTrue((new Variables()).map.isEmpty());
  }

  /**
   * Test Variables {@link Variables#resolveVariable(String)}.
   * <p>
   * Method under test: {@link Variables#resolveVariable(String)}
   */
  @Test
  @DisplayName("Test Variables resolveVariable(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ValueExpression Variables.resolveVariable(String)"})
  void testVariablesResolveVariable() {
    // Arrange, Act and Assert
    assertNull((new Variables()).resolveVariable("Variable"));
  }

  /**
   * Test Variables {@link Variables#setVariable(String, ValueExpression)}.
   * <p>
   * Method under test: {@link Variables#setVariable(String, ValueExpression)}
   */
  @Test
  @DisplayName("Test Variables setVariable(String, ValueExpression)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ValueExpression Variables.setVariable(String, ValueExpression)"})
  void testVariablesSetVariable() {
    // Arrange
    Variables variables = new Variables();
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
