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
package org.activiti.runtime.api.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.interceptor.DelegateInterceptor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ExpressionResolver.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ExpressionResolverDiffblueTest {
  @MockBean
  private DelegateInterceptor delegateInterceptor;

  @MockBean
  private ExpressionManager expressionManager;

  @Autowired
  private ExpressionResolver expressionResolver;

  @MockBean
  private ObjectMapper objectMapper;

  /**
   * Method under test:
   * {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator, Map)}
   */
  @Test
  void testResolveExpressionsMap() {
    // Arrange
    ExpressionEvaluator expressionEvaluator = mock(ExpressionEvaluator.class);

    // Act and Assert
    assertTrue(expressionResolver.resolveExpressionsMap(expressionEvaluator, new HashMap<>()).isEmpty());
  }

  /**
   * Method under test:
   * {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator, Map)}
   */
  @Test
  void testResolveExpressionsMap2() {
    // Arrange
    ExpressionEvaluator expressionEvaluator = mock(ExpressionEvaluator.class);

    HashMap<String, Object> sourceMap = new HashMap<>();
    sourceMap.put("foo", "42");

    // Act
    Map<String, Object> actualResolveExpressionsMapResult = expressionResolver
        .resolveExpressionsMap(expressionEvaluator, sourceMap);

    // Assert
    assertEquals(1, actualResolveExpressionsMapResult.size());
    assertEquals("42", actualResolveExpressionsMapResult.get("foo"));
  }

  /**
   * Method under test:
   * {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator, Map)}
   */
  @Test
  void testResolveExpressionsMap3() {
    // Arrange
    ExpressionEvaluator expressionEvaluator = mock(ExpressionEvaluator.class);

    HashMap<String, Object> sourceMap = new HashMap<>();
    sourceMap.put("([\\$]\\{([^\\}]*)\\})", "42");
    sourceMap.put("foo", "42");

    // Act and Assert
    assertEquals(sourceMap, expressionResolver.resolveExpressionsMap(expressionEvaluator, sourceMap));
  }

  /**
   * Method under test:
   * {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator, Map)}
   */
  @Test
  void testResolveExpressionsMap4() {
    // Arrange
    when(expressionManager.createExpression(Mockito.<String>any())).thenReturn(new FixedValue("Value"));
    ExpressionEvaluator expressionEvaluator = mock(ExpressionEvaluator.class);
    when(expressionEvaluator.evaluate(Mockito.<Expression>any(), Mockito.<ExpressionManager>any(),
        Mockito.<DelegateInterceptor>any())).thenReturn("Evaluate");

    HashMap<String, Object> sourceMap = new HashMap<>();
    sourceMap.put("foo", "${U}");

    // Act
    Map<String, Object> actualResolveExpressionsMapResult = expressionResolver
        .resolveExpressionsMap(expressionEvaluator, sourceMap);

    // Assert
    verify(expressionManager).createExpression(eq("${U}"));
    verify(expressionEvaluator).evaluate(isA(Expression.class), isA(ExpressionManager.class),
        isA(DelegateInterceptor.class));
    assertEquals(1, actualResolveExpressionsMapResult.size());
    assertEquals("Evaluate", actualResolveExpressionsMapResult.get("foo"));
  }

  /**
   * Method under test:
   * {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator, Map)}
   */
  @Test
  void testResolveExpressionsMap5() {
    // Arrange
    ExpressionEvaluator expressionEvaluator = mock(ExpressionEvaluator.class);

    HashMap<String, Object> sourceMap = new HashMap<>();
    sourceMap.put("foo", 1);

    // Act
    Map<String, Object> actualResolveExpressionsMapResult = expressionResolver
        .resolveExpressionsMap(expressionEvaluator, sourceMap);

    // Assert
    assertEquals(1, actualResolveExpressionsMapResult.size());
    assertTrue(actualResolveExpressionsMapResult.containsKey("foo"));
  }

  /**
   * Method under test:
   * {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator, Map)}
   */
  @Test
  void testResolveExpressionsMap6() {
    // Arrange
    ExpressionEvaluator expressionEvaluator = mock(ExpressionEvaluator.class);

    HashMap<String, Object> sourceMap = new HashMap<>();
    sourceMap.put("foo", "");

    // Act
    Map<String, Object> actualResolveExpressionsMapResult = expressionResolver
        .resolveExpressionsMap(expressionEvaluator, sourceMap);

    // Assert
    assertEquals(1, actualResolveExpressionsMapResult.size());
    assertEquals("", actualResolveExpressionsMapResult.get("foo"));
  }

  /**
   * Method under test:
   * {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator, Map)}
   */
  @Test
  void testResolveExpressionsMap7() throws IllegalArgumentException {
    // Arrange
    Mockito
        .<Map<String, ?>>when(
            objectMapper.convertValue(Mockito.<Object>any(), Mockito.<TypeReference<Map<String, Object>>>any()))
        .thenReturn(new HashMap<>());
    ExpressionEvaluator expressionEvaluator = mock(ExpressionEvaluator.class);

    HashMap<String, Object> sourceMap = new HashMap<>();
    sourceMap.put("foo", new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Act
    Map<String, Object> actualResolveExpressionsMapResult = expressionResolver
        .resolveExpressionsMap(expressionEvaluator, sourceMap);

    // Assert
    verify(objectMapper).convertValue(isA(Object.class), isA(TypeReference.class));
    assertEquals(1, actualResolveExpressionsMapResult.size());
    Object getResult = actualResolveExpressionsMapResult.get("foo");
    assertTrue(getResult instanceof Map);
    assertTrue(((Map<Object, Object>) getResult).isEmpty());
  }

  /**
   * Method under test: {@link ExpressionResolver#containsExpression(Object)}
   */
  @Test
  void testContainsExpression() {
    // Arrange, Act and Assert
    assertFalse(expressionResolver.containsExpression("Source"));
    assertTrue(expressionResolver.containsExpression("${U}"));
    assertFalse(expressionResolver.containsExpression(1));
    assertFalse(expressionResolver.containsExpression(null));
  }

  /**
   * Method under test: {@link ExpressionResolver#containsExpression(Object)}
   */
  @Test
  void testContainsExpression2() throws IllegalArgumentException {
    // Arrange
    Mockito
        .<Map<String, ?>>when(
            objectMapper.convertValue(Mockito.<Object>any(), Mockito.<TypeReference<Map<String, Object>>>any()))
        .thenReturn(new HashMap<>());

    // Act
    boolean actualContainsExpressionResult = expressionResolver
        .containsExpression(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Assert
    verify(objectMapper).convertValue(isA(Object.class), isA(TypeReference.class));
    assertFalse(actualContainsExpressionResult);
  }

  /**
   * Method under test: {@link ExpressionResolver#containsExpression(Object)}
   */
  @Test
  void testContainsExpression3() throws IllegalArgumentException {
    // Arrange
    HashMap<String, Object> stringObjectMap = new HashMap<>();
    stringObjectMap.put("foo", "42");
    Mockito
        .<Map<String, ?>>when(
            objectMapper.convertValue(Mockito.<Object>any(), Mockito.<TypeReference<Map<String, Object>>>any()))
        .thenReturn(stringObjectMap);

    // Act
    boolean actualContainsExpressionResult = expressionResolver
        .containsExpression(new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Assert
    verify(objectMapper).convertValue(isA(Object.class), isA(TypeReference.class));
    assertFalse(actualContainsExpressionResult);
  }

  /**
   * Method under test:
   * {@link ExpressionResolver#ExpressionResolver(ExpressionManager, ObjectMapper, DelegateInterceptor)}
   */
  @Test
  void testNewExpressionResolver() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();

    // Act and Assert
    assertFalse((new ExpressionResolver(expressionManager, new ObjectMapper(), mock(DelegateInterceptor.class)))
        .containsExpression("Source"));
  }
}
