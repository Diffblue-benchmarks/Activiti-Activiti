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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import jakarta.el.CompositeELResolver;
import jakarta.el.ELContext;
import jakarta.el.FunctionMapper;
import jakarta.el.VariableMapper;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.activiti.core.el.juel.ExpressionFactoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class JuelExpressionResolverDiffblueTest {
  /**
   * Test {@link JuelExpressionResolver#JuelExpressionResolver()}.
   *
   * <p>Method under test: {@link JuelExpressionResolver#JuelExpressionResolver()}
   */
  @Test
  @DisplayName("Test new JuelExpressionResolver()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void JuelExpressionResolver.<init>()"})
  void testNewJuelExpressionResolver() {
    // Arrange and Act
    JuelExpressionResolver actualJuelExpressionResolver = new JuelExpressionResolver();
    HashMap<String, Object> variables = new HashMap<>();
    Class<Object> type = Object.class;
    Object actualResolveExpressionResult =
        actualJuelExpressionResolver.resolveExpression("Expression", variables, type);

    // Assert
    ELContext buildContextResult = actualJuelExpressionResolver.buildContext(null);
    assertTrue(buildContextResult.getELResolver() instanceof CompositeELResolver);
    assertTrue(buildContextResult instanceof ActivitiElContext);
    FunctionMapper functionMapper = buildContextResult.getFunctionMapper();
    assertTrue(functionMapper instanceof ActivitiFunctionMapper);
    VariableMapper variableMapper = buildContextResult.getVariableMapper();
    assertTrue(variableMapper instanceof ActivitiVariablesMapper);
    assertEquals("Expression", actualResolveExpressionResult);
    assertNull(buildContextResult.getEvaluationListeners());
    assertNull(buildContextResult.getLocale());
    Map<String, Method> stringMethodMap = ((ActivitiFunctionMapper) functionMapper).map;
    assertEquals(2, stringMethodMap.size());
    assertFalse(buildContextResult.isPropertyResolved());
    assertTrue(stringMethodMap.containsKey(":list"));
    assertTrue(stringMethodMap.containsKey(":now"));
    assertTrue(((ActivitiVariablesMapper) variableMapper).map.isEmpty());
  }

  /**
   * Test {@link JuelExpressionResolver#JuelExpressionResolver()}.
   *
   * <p>Method under test: {@link JuelExpressionResolver#JuelExpressionResolver()}
   */
  @Test
  @DisplayName("Test new JuelExpressionResolver()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void JuelExpressionResolver.<init>()"})
  void testNewJuelExpressionResolver2() {
    // Arrange and Act
    JuelExpressionResolver actualJuelExpressionResolver = new JuelExpressionResolver();
    HashMap<String, Object> variables = new HashMap<>();
    Class<Object> type = Object.class;
    Object actualResolveExpressionResult =
        actualJuelExpressionResolver.resolveExpression(null, variables, type);

    // Assert
    ELContext buildContextResult = actualJuelExpressionResolver.buildContext(null);
    assertTrue(buildContextResult.getELResolver() instanceof CompositeELResolver);
    assertTrue(buildContextResult instanceof ActivitiElContext);
    FunctionMapper functionMapper = buildContextResult.getFunctionMapper();
    assertTrue(functionMapper instanceof ActivitiFunctionMapper);
    VariableMapper variableMapper = buildContextResult.getVariableMapper();
    assertTrue(variableMapper instanceof ActivitiVariablesMapper);
    assertNull(actualResolveExpressionResult);
    assertNull(buildContextResult.getEvaluationListeners());
    assertNull(buildContextResult.getLocale());
    Map<String, Method> stringMethodMap = ((ActivitiFunctionMapper) functionMapper).map;
    assertEquals(2, stringMethodMap.size());
    assertFalse(buildContextResult.isPropertyResolved());
    assertTrue(stringMethodMap.containsKey(":list"));
    assertTrue(stringMethodMap.containsKey(":now"));
    assertTrue(((ActivitiVariablesMapper) variableMapper).map.isEmpty());
  }

  /**
   * Test {@link JuelExpressionResolver#buildContext(Map)}.
   *
   * <ul>
   *   <li>Given {@link JuelExpressionResolver#JuelExpressionResolver()}.
   * </ul>
   *
   * <p>Method under test: {@link JuelExpressionResolver#buildContext(Map)}
   */
  @Test
  @DisplayName("Test buildContext(Map); given JuelExpressionResolver()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ELContext JuelExpressionResolver.buildContext(Map)"})
  void testBuildContext_givenJuelExpressionResolver() {
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
    assertSame(getResult, parameters[0].getDeclaringExecutable());
  }

  /**
   * Test {@link JuelExpressionResolver#buildContext(Map)}.
   *
   * <ul>
   *   <li>Then calls {@link CustomFunctionProvider#addCustomFunctions(ActivitiElContext)}.
   * </ul>
   *
   * <p>Method under test: {@link JuelExpressionResolver#buildContext(Map)}
   */
  @Test
  @DisplayName("Test buildContext(Map); then calls addCustomFunctions(ActivitiElContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ELContext JuelExpressionResolver.buildContext(Map)"})
  void testBuildContext_thenCallsAddCustomFunctions() {
    // Arrange
    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider);
    JuelExpressionResolver juelExpressionResolver =
        new JuelExpressionResolver(new ExpressionFactoryImpl(), customFunctionProviders);

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
    assertSame(getResult, parameters[0].getDeclaringExecutable());
  }

  /**
   * Test {@link JuelExpressionResolver#buildContext(Map)}.
   *
   * <ul>
   *   <li>Then calls {@link CustomFunctionProvider#addCustomFunctions(ActivitiElContext)}.
   * </ul>
   *
   * <p>Method under test: {@link JuelExpressionResolver#buildContext(Map)}
   */
  @Test
  @DisplayName("Test buildContext(Map); then calls addCustomFunctions(ActivitiElContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ELContext JuelExpressionResolver.buildContext(Map)"})
  void testBuildContext_thenCallsAddCustomFunctions2() {
    // Arrange
    CustomFunctionProvider customFunctionProvider = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider).addCustomFunctions(Mockito.<ActivitiElContext>any());

    CustomFunctionProvider customFunctionProvider2 = mock(CustomFunctionProvider.class);
    doNothing().when(customFunctionProvider2).addCustomFunctions(Mockito.<ActivitiElContext>any());

    ArrayList<CustomFunctionProvider> customFunctionProviders = new ArrayList<>();
    customFunctionProviders.add(customFunctionProvider2);
    customFunctionProviders.add(customFunctionProvider);
    JuelExpressionResolver juelExpressionResolver =
        new JuelExpressionResolver(new ExpressionFactoryImpl(), customFunctionProviders);

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
    assertSame(getResult, parameters[0].getDeclaringExecutable());
  }
}
