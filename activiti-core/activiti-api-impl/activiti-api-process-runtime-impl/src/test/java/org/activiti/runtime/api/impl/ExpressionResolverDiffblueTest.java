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
import org.junit.jupiter.api.DisplayName;
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
   * Test
   * {@link ExpressionResolver#ExpressionResolver(ExpressionManager, ObjectMapper, DelegateInterceptor)}.
   * <p>
   * Method under test:
   * {@link ExpressionResolver#ExpressionResolver(ExpressionManager, ObjectMapper, DelegateInterceptor)}
   */
  @Test
  @DisplayName("Test new ExpressionResolver(ExpressionManager, ObjectMapper, DelegateInterceptor)")
  void testNewExpressionResolver() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();

    // Act and Assert
    assertFalse((new ExpressionResolver(expressionManager, new ObjectMapper(), mock(DelegateInterceptor.class)))
        .containsExpression("Source"));
  }

  /**
   * Test
   * {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator, Map)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link HashMap#HashMap()} {@code foo} is {@code 42}.</li>
   *   <li>Then return {@code foo} is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator, Map)}
   */
  @Test
  @DisplayName("Test resolveExpressionsMap(ExpressionEvaluator, Map); given '42'; when HashMap() 'foo' is '42'; then return 'foo' is '42'")
  void testResolveExpressionsMap_given42_whenHashMapFooIs42_thenReturnFooIs42() {
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
   * Test
   * {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator, Map)}.
   * <ul>
   *   <li>Given empty string.</li>
   *   <li>Then return {@code foo} is empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator, Map)}
   */
  @Test
  @DisplayName("Test resolveExpressionsMap(ExpressionEvaluator, Map); given empty string; then return 'foo' is empty string")
  void testResolveExpressionsMap_givenEmptyString_thenReturnFooIsEmptyString() {
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
   * Test
   * {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator, Map)}.
   * <ul>
   *   <li>Given {@link ExpressionManager}.</li>
   *   <li>When {@link HashMap#HashMap()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator, Map)}
   */
  @Test
  @DisplayName("Test resolveExpressionsMap(ExpressionEvaluator, Map); given ExpressionManager; when HashMap(); then return Empty")
  void testResolveExpressionsMap_givenExpressionManager_whenHashMap_thenReturnEmpty() {
    // Arrange
    ExpressionEvaluator expressionEvaluator = mock(ExpressionEvaluator.class);

    // Act and Assert
    assertTrue(expressionResolver.resolveExpressionsMap(expressionEvaluator, new HashMap<>()).isEmpty());
  }

  /**
   * Test
   * {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator, Map)}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>When {@link HashMap#HashMap()} {@code foo} is one.</li>
   *   <li>Then return containsKey {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator, Map)}
   */
  @Test
  @DisplayName("Test resolveExpressionsMap(ExpressionEvaluator, Map); given one; when HashMap() 'foo' is one; then return containsKey 'foo'")
  void testResolveExpressionsMap_givenOne_whenHashMapFooIsOne_thenReturnContainsKeyFoo() {
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
   * Test
   * {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator, Map)}.
   * <ul>
   *   <li>Then {@code foo} return {@link Map}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator, Map)}
   */
  @Test
  @DisplayName("Test resolveExpressionsMap(ExpressionEvaluator, Map); then 'foo' return Map")
  void testResolveExpressionsMap_thenFooReturnMap() throws IllegalArgumentException {
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
   * Test
   * {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator, Map)}.
   * <ul>
   *   <li>Then return {@code foo} is {@code Evaluate}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator, Map)}
   */
  @Test
  @DisplayName("Test resolveExpressionsMap(ExpressionEvaluator, Map); then return 'foo' is 'Evaluate'")
  void testResolveExpressionsMap_thenReturnFooIsEvaluate() {
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
   * Test
   * {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator, Map)}.
   * <ul>
   *   <li>Then return size is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator, Map)}
   */
  @Test
  @DisplayName("Test resolveExpressionsMap(ExpressionEvaluator, Map); then return size is two")
  void testResolveExpressionsMap_thenReturnSizeIsTwo() {
    // Arrange
    ExpressionEvaluator expressionEvaluator = mock(ExpressionEvaluator.class);

    HashMap<String, Object> sourceMap = new HashMap<>();
    sourceMap.put("([\\$]\\{([^\\}]*)\\})", "42");
    sourceMap.put("foo", "42");

    // Act
    Map<String, Object> actualResolveExpressionsMapResult = expressionResolver
        .resolveExpressionsMap(expressionEvaluator, sourceMap);

    // Assert
    assertEquals(2, actualResolveExpressionsMapResult.size());
    assertEquals("42", actualResolveExpressionsMapResult.get("([\\$]\\{([^\\}]*)\\})"));
    assertEquals("42", actualResolveExpressionsMapResult.get("foo"));
  }

  /**
   * Test {@link ExpressionResolver#containsExpression(Object)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code foo} is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExpressionResolver#containsExpression(Object)}
   */
  @Test
  @DisplayName("Test containsExpression(Object); given HashMap() 'foo' is '42'")
  void testContainsExpression_givenHashMapFooIs42() throws IllegalArgumentException {
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
   * Test {@link ExpressionResolver#containsExpression(Object)}.
   * <ul>
   *   <li>Given {@link ObjectMapper}.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExpressionResolver#containsExpression(Object)}
   */
  @Test
  @DisplayName("Test containsExpression(Object); given ObjectMapper; when 'null'; then return 'false'")
  void testContainsExpression_givenObjectMapper_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(expressionResolver.containsExpression(null));
  }

  /**
   * Test {@link ExpressionResolver#containsExpression(Object)}.
   * <ul>
   *   <li>Given {@link ObjectMapper}.</li>
   *   <li>When one.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExpressionResolver#containsExpression(Object)}
   */
  @Test
  @DisplayName("Test containsExpression(Object); given ObjectMapper; when one; then return 'false'")
  void testContainsExpression_givenObjectMapper_whenOne_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(expressionResolver.containsExpression(1));
  }

  /**
   * Test {@link ExpressionResolver#containsExpression(Object)}.
   * <ul>
   *   <li>Given {@link ObjectMapper}.</li>
   *   <li>When {@code Source}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExpressionResolver#containsExpression(Object)}
   */
  @Test
  @DisplayName("Test containsExpression(Object); given ObjectMapper; when 'Source'; then return 'false'")
  void testContainsExpression_givenObjectMapper_whenSource_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(expressionResolver.containsExpression("Source"));
  }

  /**
   * Test {@link ExpressionResolver#containsExpression(Object)}.
   * <ul>
   *   <li>Given {@link ObjectMapper}.</li>
   *   <li>When {@code ${U}}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExpressionResolver#containsExpression(Object)}
   */
  @Test
  @DisplayName("Test containsExpression(Object); given ObjectMapper; when '${U}'; then return 'true'")
  void testContainsExpression_givenObjectMapper_whenU_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(expressionResolver.containsExpression("${U}"));
  }

  /**
   * Test {@link ExpressionResolver#containsExpression(Object)}.
   * <ul>
   *   <li>When {@link ObjectNode#ObjectNode(JsonNodeFactory)} with nc is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExpressionResolver#containsExpression(Object)}
   */
  @Test
  @DisplayName("Test containsExpression(Object); when ObjectNode(JsonNodeFactory) with nc is withExactBigDecimals 'true'")
  void testContainsExpression_whenObjectNodeWithNcIsWithExactBigDecimalsTrue() throws IllegalArgumentException {
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
}
