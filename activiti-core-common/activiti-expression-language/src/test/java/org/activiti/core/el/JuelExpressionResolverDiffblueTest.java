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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import jakarta.el.ELContext;
import jakarta.el.FunctionMapper;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.activiti.core.el.juel.ExpressionFactoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class JuelExpressionResolverDiffblueTest {
  /**
   * Test {@link JuelExpressionResolver#resolveExpression(String, Map, Class)}.
   * <ul>
   *   <li>Given {@link JuelExpressionResolver#JuelExpressionResolver()}.</li>
   *   <li>Then return {@code Expression}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JuelExpressionResolver#resolveExpression(String, Map, Class)}
   */
  @Test
  @DisplayName("Test resolveExpression(String, Map, Class); given JuelExpressionResolver(); then return 'Expression'")
  void testResolveExpression_givenJuelExpressionResolver_thenReturnExpression() {
    // Arrange
    JuelExpressionResolver juelExpressionResolver = new JuelExpressionResolver();
    HashMap<String, Object> variables = new HashMap<>();
    Class<Object> type = Object.class;

    // Act and Assert
    assertEquals("Expression", juelExpressionResolver.resolveExpression("Expression", variables, type));
  }

  /**
   * Test {@link JuelExpressionResolver#resolveExpression(String, Map, Class)}.
   * <ul>
   *   <li>Given {@link JuelExpressionResolver#JuelExpressionResolver()}.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JuelExpressionResolver#resolveExpression(String, Map, Class)}
   */
  @Test
  @DisplayName("Test resolveExpression(String, Map, Class); given JuelExpressionResolver(); when 'null'; then return 'null'")
  void testResolveExpression_givenJuelExpressionResolver_whenNull_thenReturnNull() {
    // Arrange
    JuelExpressionResolver juelExpressionResolver = new JuelExpressionResolver();
    HashMap<String, Object> variables = new HashMap<>();
    Class<Object> type = Object.class;

    // Act and Assert
    assertNull(juelExpressionResolver.resolveExpression(null, variables, type));
  }

  /**
   * Test {@link JuelExpressionResolver#resolveExpression(String, Map, Class)}.
   * <ul>
   *   <li>Given {@code now}.</li>
   *   <li>When {@link HashMap#HashMap()} computeIfPresent {@code now} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JuelExpressionResolver#resolveExpression(String, Map, Class)}
   */
  @Test
  @DisplayName("Test resolveExpression(String, Map, Class); given 'now'; when HashMap() computeIfPresent 'now' and BiFunction")
  void testResolveExpression_givenNow_whenHashMapComputeIfPresentNowAndBiFunction() {
    // Arrange
    JuelExpressionResolver juelExpressionResolver = new JuelExpressionResolver();

    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("now", mock(BiFunction.class));
    Class<Object> type = Object.class;

    // Act and Assert
    assertEquals("Expression", juelExpressionResolver.resolveExpression("Expression", variables, type));
  }

  /**
   * Test {@link JuelExpressionResolver#resolveExpression(String, Map, Class)}.
   * <ul>
   *   <li>Then calls
   * {@link CustomFunctionProvider#addCustomFunctions(ActivitiElContext)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JuelExpressionResolver#resolveExpression(String, Map, Class)}
   */
  @Test
  @DisplayName("Test resolveExpression(String, Map, Class); then calls addCustomFunctions(ActivitiElContext)")
  void testResolveExpression_thenCallsAddCustomFunctions() {
    // Arrange
    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider);
    JuelExpressionResolver juelExpressionResolver = new JuelExpressionResolver(new ExpressionFactoryImpl(),
        customFunctionProviders);
    HashMap<String, Object> variables = new HashMap<>();
    Class<Object> type = Object.class;

    // Act
    Object actualResolveExpressionResult = juelExpressionResolver.resolveExpression("Expression", variables, type);

    // Assert
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertEquals("Expression", actualResolveExpressionResult);
  }

  /**
   * Test {@link JuelExpressionResolver#resolveExpression(String, Map, Class)}.
   * <ul>
   *   <li>Then calls
   * {@link CustomFunctionProvider#addCustomFunctions(ActivitiElContext)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JuelExpressionResolver#resolveExpression(String, Map, Class)}
   */
  @Test
  @DisplayName("Test resolveExpression(String, Map, Class); then calls addCustomFunctions(ActivitiElContext)")
  void testResolveExpression_thenCallsAddCustomFunctions2() {
    // Arrange
    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());
    CustomFunctionProvider customFunctionProvider2 = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider2).addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider2);
    customFunctionProviders.add(customFunctionProvider);
    JuelExpressionResolver juelExpressionResolver = new JuelExpressionResolver(new ExpressionFactoryImpl(),
        customFunctionProviders);
    HashMap<String, Object> variables = new HashMap<>();
    Class<Object> type = Object.class;

    // Act
    Object actualResolveExpressionResult = juelExpressionResolver.resolveExpression("Expression", variables, type);

    // Assert
    verify(customFunctionProvider2).addCustomFunctions(isA(ActivitiElContext.class));
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertEquals("Expression", actualResolveExpressionResult);
  }

  /**
   * Test {@link JuelExpressionResolver#buildContext(Map)}.
   * <ul>
   *   <li>Given {@link JuelExpressionResolver#JuelExpressionResolver()}.</li>
   *   <li>When {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JuelExpressionResolver#buildContext(Map)}
   */
  @Test
  @DisplayName("Test buildContext(Map); given JuelExpressionResolver(); when HashMap()")
  void testBuildContext_givenJuelExpressionResolver_whenHashMap() {
    // Arrange
    JuelExpressionResolver juelExpressionResolver = new JuelExpressionResolver();

    // Act
    ELContext actualBuildContextResult = juelExpressionResolver.buildContext(new HashMap<>());

    // Assert
    assertTrue(actualBuildContextResult instanceof ActivitiElContext);
    FunctionMapper functionMapper = actualBuildContextResult.getFunctionMapper();
    assertTrue(functionMapper instanceof ActivitiFunctionMapper);
    Map<String, Method> stringMethodMap = ((ActivitiFunctionMapper) functionMapper).map;
    assertEquals(2, stringMethodMap.size());
    Method getResult = stringMethodMap.get(":list");
    Parameter[] parameters = getResult.getParameters();
    assertEquals(1, parameters.length);
    assertEquals(1, getResult.getParameterTypes().length);
    assertSame(getResult, (parameters[0]).getDeclaringExecutable());
  }

  /**
   * Test {@link JuelExpressionResolver#buildContext(Map)}.
   * <ul>
   *   <li>Given {@code now}.</li>
   *   <li>When {@link HashMap#HashMap()} computeIfPresent {@code now} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JuelExpressionResolver#buildContext(Map)}
   */
  @Test
  @DisplayName("Test buildContext(Map); given 'now'; when HashMap() computeIfPresent 'now' and BiFunction")
  void testBuildContext_givenNow_whenHashMapComputeIfPresentNowAndBiFunction() {
    // Arrange
    JuelExpressionResolver juelExpressionResolver = new JuelExpressionResolver();

    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("now", mock(BiFunction.class));

    // Act
    ELContext actualBuildContextResult = juelExpressionResolver.buildContext(variables);

    // Assert
    assertTrue(actualBuildContextResult instanceof ActivitiElContext);
    FunctionMapper functionMapper = actualBuildContextResult.getFunctionMapper();
    assertTrue(functionMapper instanceof ActivitiFunctionMapper);
    Map<String, Method> stringMethodMap = ((ActivitiFunctionMapper) functionMapper).map;
    assertEquals(2, stringMethodMap.size());
    Method getResult = stringMethodMap.get(":list");
    Parameter[] parameters = getResult.getParameters();
    assertEquals(1, parameters.length);
    assertEquals(1, getResult.getParameterTypes().length);
    assertSame(getResult, (parameters[0]).getDeclaringExecutable());
  }

  /**
   * Test {@link JuelExpressionResolver#buildContext(Map)}.
   * <ul>
   *   <li>Then calls
   * {@link CustomFunctionProvider#addCustomFunctions(ActivitiElContext)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JuelExpressionResolver#buildContext(Map)}
   */
  @Test
  @DisplayName("Test buildContext(Map); then calls addCustomFunctions(ActivitiElContext)")
  void testBuildContext_thenCallsAddCustomFunctions() {
    // Arrange
    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider);
    JuelExpressionResolver juelExpressionResolver = new JuelExpressionResolver(new ExpressionFactoryImpl(),
        customFunctionProviders);

    // Act
    ELContext actualBuildContextResult = juelExpressionResolver.buildContext(new HashMap<>());

    // Assert
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertTrue(actualBuildContextResult instanceof ActivitiElContext);
    FunctionMapper functionMapper = actualBuildContextResult.getFunctionMapper();
    assertTrue(functionMapper instanceof ActivitiFunctionMapper);
    Map<String, Method> stringMethodMap = ((ActivitiFunctionMapper) functionMapper).map;
    assertEquals(2, stringMethodMap.size());
    Method getResult = stringMethodMap.get(":list");
    Parameter[] parameters = getResult.getParameters();
    assertEquals(1, parameters.length);
    assertEquals(1, getResult.getParameterTypes().length);
    assertSame(getResult, (parameters[0]).getDeclaringExecutable());
  }

  /**
   * Test {@link JuelExpressionResolver#buildContext(Map)}.
   * <ul>
   *   <li>Then calls
   * {@link CustomFunctionProvider#addCustomFunctions(ActivitiElContext)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JuelExpressionResolver#buildContext(Map)}
   */
  @Test
  @DisplayName("Test buildContext(Map); then calls addCustomFunctions(ActivitiElContext)")
  void testBuildContext_thenCallsAddCustomFunctions2() {
    // Arrange
    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());
    CustomFunctionProvider customFunctionProvider2 = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider2).addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider2);
    customFunctionProviders.add(customFunctionProvider);
    JuelExpressionResolver juelExpressionResolver = new JuelExpressionResolver(new ExpressionFactoryImpl(),
        customFunctionProviders);

    // Act
    ELContext actualBuildContextResult = juelExpressionResolver.buildContext(new HashMap<>());

    // Assert
    verify(customFunctionProvider2).addCustomFunctions(isA(ActivitiElContext.class));
    verify(customFunctionProvider).addCustomFunctions(isA(ActivitiElContext.class));
    assertTrue(actualBuildContextResult instanceof ActivitiElContext);
    FunctionMapper functionMapper = actualBuildContextResult.getFunctionMapper();
    assertTrue(functionMapper instanceof ActivitiFunctionMapper);
    Map<String, Method> stringMethodMap = ((ActivitiFunctionMapper) functionMapper).map;
    assertEquals(2, stringMethodMap.size());
    Method getResult = stringMethodMap.get(":list");
    Parameter[] parameters = getResult.getParameters();
    assertEquals(1, parameters.length);
    assertEquals(1, getResult.getParameterTypes().length);
    assertSame(getResult, (parameters[0]).getDeclaringExecutable());
  }
}
