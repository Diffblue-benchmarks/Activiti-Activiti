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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.activiti.core.el.juel.ExpressionFactoryImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class JuelExpressionResolverDiffblueTest {
  /**
   * Method under test:
   * {@link JuelExpressionResolver#resolveExpression(String, Map, Class)}
   */
  @Test
  void testResolveExpression() {
    // Arrange
    JuelExpressionResolver juelExpressionResolver = new JuelExpressionResolver();
    HashMap<String, Object> variables = new HashMap<>();
    Class<Object> type = Object.class;

    // Act and Assert
    assertEquals("Expression", juelExpressionResolver.resolveExpression("Expression", variables, type));
  }

  /**
   * Method under test:
   * {@link JuelExpressionResolver#resolveExpression(String, Map, Class)}
   */
  @Test
  void testResolveExpression2() {
    // Arrange
    JuelExpressionResolver juelExpressionResolver = new JuelExpressionResolver();
    HashMap<String, Object> variables = new HashMap<>();
    Class<Object> type = Object.class;

    // Act and Assert
    assertNull(juelExpressionResolver.resolveExpression(null, variables, type));
  }

  /**
   * Method under test:
   * {@link JuelExpressionResolver#resolveExpression(String, Map, Class)}
   */
  @Test
  void testResolveExpression3() {
    // Arrange
    JuelExpressionResolver juelExpressionResolver = new JuelExpressionResolver();

    HashMap<String, Object> variables = new HashMap<>();
    variables.computeIfPresent("now", mock(BiFunction.class));
    Class<Object> type = Object.class;

    // Act and Assert
    assertEquals("Expression", juelExpressionResolver.resolveExpression("Expression", variables, type));
  }

  /**
   * Method under test:
   * {@link JuelExpressionResolver#resolveExpression(String, Map, Class)}
   */
  @Test
  void testResolveExpression4() {
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
   * Method under test:
   * {@link JuelExpressionResolver#resolveExpression(String, Map, Class)}
   */
  @Test
  void testResolveExpression5() {
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
}
