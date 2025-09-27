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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.HashMap;
import java.util.Map;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.interceptor.DelegateInterceptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ExpressionResolver.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class ExpressionResolverDiffblueTest {
  @MockBean private DelegateInterceptor delegateInterceptor;

  @MockBean private ExpressionManager expressionManager;

  @Autowired private ExpressionResolver expressionResolver;

  @MockBean private ObjectMapper objectMapper;

  /**
   * Test {@link ExpressionResolver#ExpressionResolver(ExpressionManager, ObjectMapper,
   * DelegateInterceptor)}.
   *
   * <p>Method under test: {@link ExpressionResolver#ExpressionResolver(ExpressionManager,
   * ObjectMapper, DelegateInterceptor)}
   */
  @Test
  @DisplayName("Test new ExpressionResolver(ExpressionManager, ObjectMapper, DelegateInterceptor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExpressionResolver.<init>(ExpressionManager, ObjectMapper, DelegateInterceptor)"
  })
  void testNewExpressionResolver() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    JsonMapper mapper = JsonMapper.builder().findAndAddModules().build();

    // Act
    ExpressionResolver actualExpressionResolver =
        new ExpressionResolver(expressionManager, mapper, mock(DelegateInterceptor.class));

    // Assert
    assertFalse(actualExpressionResolver.containsExpression("Source"));
  }

  /**
   * Test {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator, Map)}.
   *
   * <p>Method under test: {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator,
   * Map)}
   */
  @Test
  @DisplayName("Test resolveExpressionsMap(ExpressionEvaluator, Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ExpressionResolver.resolveExpressionsMap(ExpressionEvaluator, Map)"})
  void testResolveExpressionsMap() {
    // Arrange
    ExpressionManager expressionManager = new ExpressionManager();
    JsonMapper mapper = JsonMapper.builder().findAndAddModules().build();

    ExpressionResolver expressionResolver =
        new ExpressionResolver(expressionManager, mapper, mock(DelegateInterceptor.class));

    ExpressionEvaluator expressionEvaluator = mock(ExpressionEvaluator.class);
    when(expressionEvaluator.evaluate(
            Mockito.<Expression>any(),
            Mockito.<ExpressionManager>any(),
            Mockito.<DelegateInterceptor>any()))
        .thenReturn("Evaluate");

    HashMap<String, Object> sourceMap = new HashMap<>();
    sourceMap.put("([\\$]\\{([^\\}]*)\\})", "42");
    sourceMap.put("foo", "${U}");

    // Act
    Map<String, Object> actualResolveExpressionsMapResult =
        expressionResolver.resolveExpressionsMap(expressionEvaluator, sourceMap);

    // Assert
    verify(expressionEvaluator)
        .evaluate(
            isA(Expression.class), isA(ExpressionManager.class), isA(DelegateInterceptor.class));
    assertEquals(2, actualResolveExpressionsMapResult.size());
    assertEquals("42", actualResolveExpressionsMapResult.get("([\\$]\\{([^\\}]*)\\})"));
    assertEquals("Evaluate", actualResolveExpressionsMapResult.get("foo"));
  }

  /**
   * Test {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator, Map)}.
   *
   * <ul>
   *   <li>Given empty string.
   *   <li>When {@link HashMap#HashMap()} {@code foo} is empty string.
   * </ul>
   *
   * <p>Method under test: {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test resolveExpressionsMap(ExpressionEvaluator, Map); given empty string; when HashMap() 'foo' is empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ExpressionResolver.resolveExpressionsMap(ExpressionEvaluator, Map)"})
  void testResolveExpressionsMap_givenEmptyString_whenHashMapFooIsEmptyString() {
    // Arrange
    ExpressionEvaluator expressionEvaluator = mock(ExpressionEvaluator.class);

    HashMap<String, Object> sourceMap = new HashMap<>();
    sourceMap.put("foo", "");

    // Act
    Map<String, Object> actualResolveExpressionsMapResult =
        expressionResolver.resolveExpressionsMap(expressionEvaluator, sourceMap);

    // Assert
    assertEquals(sourceMap, actualResolveExpressionsMapResult);
  }

  /**
   * Test {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator, Map)}.
   *
   * <ul>
   *   <li>Given {@link ObjectMapper}.
   *   <li>When {@link HashMap#HashMap()} {@code foo} is {@code 42}.
   *   <li>Then return {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test resolveExpressionsMap(ExpressionEvaluator, Map); given ObjectMapper; when HashMap() 'foo' is '42'; then return HashMap()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ExpressionResolver.resolveExpressionsMap(ExpressionEvaluator, Map)"})
  void testResolveExpressionsMap_givenObjectMapper_whenHashMapFooIs42_thenReturnHashMap() {
    // Arrange
    ExpressionEvaluator expressionEvaluator = mock(ExpressionEvaluator.class);

    HashMap<String, Object> sourceMap = new HashMap<>();
    sourceMap.put("foo", "42");

    // Act
    Map<String, Object> actualResolveExpressionsMapResult =
        expressionResolver.resolveExpressionsMap(expressionEvaluator, sourceMap);

    // Assert
    assertEquals(sourceMap, actualResolveExpressionsMapResult);
  }

  /**
   * Test {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator, Map)}.
   *
   * <ul>
   *   <li>Given {@link ObjectMapper}.
   *   <li>When {@link HashMap#HashMap()} {@code foo} is {@code 42}.
   *   <li>Then return {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test resolveExpressionsMap(ExpressionEvaluator, Map); given ObjectMapper; when HashMap() 'foo' is '42'; then return HashMap()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ExpressionResolver.resolveExpressionsMap(ExpressionEvaluator, Map)"})
  void testResolveExpressionsMap_givenObjectMapper_whenHashMapFooIs42_thenReturnHashMap2() {
    // Arrange
    ExpressionEvaluator expressionEvaluator = mock(ExpressionEvaluator.class);

    HashMap<String, Object> sourceMap = new HashMap<>();
    sourceMap.put("([\\$]\\{([^\\}]*)\\})", "42");
    sourceMap.put("foo", "42");

    // Act
    Map<String, Object> actualResolveExpressionsMapResult =
        expressionResolver.resolveExpressionsMap(expressionEvaluator, sourceMap);

    // Assert
    assertEquals(sourceMap, actualResolveExpressionsMapResult);
  }

  /**
   * Test {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator, Map)}.
   *
   * <ul>
   *   <li>Given {@link ObjectMapper}.
   *   <li>When {@link HashMap#HashMap()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test resolveExpressionsMap(ExpressionEvaluator, Map); given ObjectMapper; when HashMap(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ExpressionResolver.resolveExpressionsMap(ExpressionEvaluator, Map)"})
  void testResolveExpressionsMap_givenObjectMapper_whenHashMap_thenReturnEmpty() {
    // Arrange
    ExpressionEvaluator expressionEvaluator = mock(ExpressionEvaluator.class);

    // Act and Assert
    assertTrue(
        expressionResolver.resolveExpressionsMap(expressionEvaluator, new HashMap<>()).isEmpty());
  }

  /**
   * Test {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator, Map)}.
   *
   * <ul>
   *   <li>Given {@link ObjectNode#ObjectNode(JsonNodeFactory)} with nc is withExactBigDecimals
   *       {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test resolveExpressionsMap(ExpressionEvaluator, Map); given ObjectNode(JsonNodeFactory) with nc is withExactBigDecimals 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ExpressionResolver.resolveExpressionsMap(ExpressionEvaluator, Map)"})
  void testResolveExpressionsMap_givenObjectNodeWithNcIsWithExactBigDecimalsTrue()
      throws IllegalArgumentException {
    // Arrange
    Mockito.<Map<String, ?>>when(
            objectMapper.convertValue(
                Mockito.<Object>any(), Mockito.<TypeReference<Map<String, Object>>>any()))
        .thenReturn(new HashMap<>());
    ExpressionEvaluator expressionEvaluator = mock(ExpressionEvaluator.class);

    HashMap<String, Object> sourceMap = new HashMap<>();
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    sourceMap.put("foo", new ObjectNode(nc));

    // Act
    Map<String, Object> actualResolveExpressionsMapResult =
        expressionResolver.resolveExpressionsMap(expressionEvaluator, sourceMap);

    // Assert
    verify(objectMapper).convertValue(isA(Object.class), isA(TypeReference.class));
    assertEquals(1, actualResolveExpressionsMapResult.size());
    Object getResult = actualResolveExpressionsMapResult.get("foo");
    assertTrue(getResult instanceof Map);
    assertTrue(((Map<Object, Object>) getResult).isEmpty());
  }

  /**
   * Test {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator, Map)}.
   *
   * <ul>
   *   <li>Given one.
   *   <li>When {@link HashMap#HashMap()} {@code foo} is one.
   *   <li>Then return {@link HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test resolveExpressionsMap(ExpressionEvaluator, Map); given one; when HashMap() 'foo' is one; then return HashMap()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ExpressionResolver.resolveExpressionsMap(ExpressionEvaluator, Map)"})
  void testResolveExpressionsMap_givenOne_whenHashMapFooIsOne_thenReturnHashMap() {
    // Arrange
    ExpressionEvaluator expressionEvaluator = mock(ExpressionEvaluator.class);

    HashMap<String, Object> sourceMap = new HashMap<>();
    sourceMap.put("foo", 1);

    // Act
    Map<String, Object> actualResolveExpressionsMapResult =
        expressionResolver.resolveExpressionsMap(expressionEvaluator, sourceMap);

    // Assert
    assertEquals(sourceMap, actualResolveExpressionsMapResult);
  }

  /**
   * Test {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator, Map)}.
   *
   * <ul>
   *   <li>Then calls {@link ExpressionManager#createExpression(String)}.
   * </ul>
   *
   * <p>Method under test: {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test resolveExpressionsMap(ExpressionEvaluator, Map); then calls createExpression(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ExpressionResolver.resolveExpressionsMap(ExpressionEvaluator, Map)"})
  void testResolveExpressionsMap_thenCallsCreateExpression() {
    // Arrange
    when(expressionManager.createExpression(Mockito.<String>any()))
        .thenReturn(new FixedValue("Value"));

    ExpressionEvaluator expressionEvaluator = mock(ExpressionEvaluator.class);
    when(expressionEvaluator.evaluate(
            Mockito.<Expression>any(),
            Mockito.<ExpressionManager>any(),
            Mockito.<DelegateInterceptor>any()))
        .thenReturn("Evaluate");

    HashMap<String, Object> sourceMap = new HashMap<>();
    sourceMap.put("foo", "${U}");

    // Act
    Map<String, Object> actualResolveExpressionsMapResult =
        expressionResolver.resolveExpressionsMap(expressionEvaluator, sourceMap);

    // Assert
    verify(expressionManager).createExpression("${U}");
    verify(expressionEvaluator)
        .evaluate(
            isA(Expression.class), isA(ExpressionManager.class), isA(DelegateInterceptor.class));
    assertEquals(1, actualResolveExpressionsMapResult.size());
    assertEquals("Evaluate", actualResolveExpressionsMapResult.get("foo"));
  }

  /**
   * Test {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator, Map)}.
   *
   * <ul>
   *   <li>Then return {@code foo} is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExpressionResolver#resolveExpressionsMap(ExpressionEvaluator,
   * Map)}
   */
  @Test
  @DisplayName("Test resolveExpressionsMap(ExpressionEvaluator, Map); then return 'foo' is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Map ExpressionResolver.resolveExpressionsMap(ExpressionEvaluator, Map)"})
  void testResolveExpressionsMap_thenReturnFooIsNull() {
    // Arrange
    JsonMapper mapper = JsonMapper.builder().findAndAddModules().build();
    ExpressionResolver expressionResolver =
        new ExpressionResolver(null, mapper, mock(DelegateInterceptor.class));
    ExpressionEvaluator expressionEvaluator = mock(ExpressionEvaluator.class);

    HashMap<String, Object> sourceMap = new HashMap<>();
    sourceMap.put("([\\$]\\{([^\\}]*)\\})", "42");
    sourceMap.put("foo", "${U}");

    // Act
    Map<String, Object> actualResolveExpressionsMapResult =
        expressionResolver.resolveExpressionsMap(expressionEvaluator, sourceMap);

    // Assert
    assertEquals(2, actualResolveExpressionsMapResult.size());
    assertEquals("42", actualResolveExpressionsMapResult.get("([\\$]\\{([^\\}]*)\\})"));
    assertNull(actualResolveExpressionsMapResult.get("foo"));
  }

  /**
   * Test {@link ExpressionResolver#containsExpression(Object)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code foo} is {@code 42}.
   *   <li>Then calls {@link ObjectMapper#convertValue(Object, TypeReference)}.
   * </ul>
   *
   * <p>Method under test: {@link ExpressionResolver#containsExpression(Object)}
   */
  @Test
  @DisplayName(
      "Test containsExpression(Object); given HashMap() 'foo' is '42'; then calls convertValue(Object, TypeReference)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ExpressionResolver.containsExpression(Object)"})
  void testContainsExpression_givenHashMapFooIs42_thenCallsConvertValue()
      throws IllegalArgumentException {
    // Arrange
    HashMap<String, Object> stringObjectMap = new HashMap<>();
    stringObjectMap.put("foo", "42");
    Mockito.<Map<String, ?>>when(
            objectMapper.convertValue(
                Mockito.<Object>any(), Mockito.<TypeReference<Map<String, Object>>>any()))
        .thenReturn(stringObjectMap);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    // Act
    boolean actualContainsExpressionResult =
        expressionResolver.containsExpression(new ObjectNode(nc));

    // Assert
    verify(objectMapper).convertValue(isA(Object.class), isA(TypeReference.class));
    assertFalse(actualContainsExpressionResult);
  }

  /**
   * Test {@link ExpressionResolver#containsExpression(Object)}.
   *
   * <ul>
   *   <li>Given {@link ObjectMapper}.
   *   <li>When {@code null}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ExpressionResolver#containsExpression(Object)}
   */
  @Test
  @DisplayName(
      "Test containsExpression(Object); given ObjectMapper; when 'null'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ExpressionResolver.containsExpression(Object)"})
  void testContainsExpression_givenObjectMapper_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(expressionResolver.containsExpression(null));
  }

  /**
   * Test {@link ExpressionResolver#containsExpression(Object)}.
   *
   * <ul>
   *   <li>Given {@link ObjectMapper}.
   *   <li>When one.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ExpressionResolver#containsExpression(Object)}
   */
  @Test
  @DisplayName("Test containsExpression(Object); given ObjectMapper; when one; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ExpressionResolver.containsExpression(Object)"})
  void testContainsExpression_givenObjectMapper_whenOne_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(expressionResolver.containsExpression(1));
  }

  /**
   * Test {@link ExpressionResolver#containsExpression(Object)}.
   *
   * <ul>
   *   <li>Given {@link ObjectMapper}.
   *   <li>When {@code Source}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ExpressionResolver#containsExpression(Object)}
   */
  @Test
  @DisplayName(
      "Test containsExpression(Object); given ObjectMapper; when 'Source'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ExpressionResolver.containsExpression(Object)"})
  void testContainsExpression_givenObjectMapper_whenSource_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(expressionResolver.containsExpression("Source"));
  }

  /**
   * Test {@link ExpressionResolver#containsExpression(Object)}.
   *
   * <ul>
   *   <li>Given {@link ObjectMapper}.
   *   <li>When {@code ${U}}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ExpressionResolver#containsExpression(Object)}
   */
  @Test
  @DisplayName(
      "Test containsExpression(Object); given ObjectMapper; when '${U}'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ExpressionResolver.containsExpression(Object)"})
  void testContainsExpression_givenObjectMapper_whenU_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(expressionResolver.containsExpression("${U}"));
  }

  /**
   * Test {@link ExpressionResolver#containsExpression(Object)}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectMapper#convertValue(Object, TypeReference)}.
   * </ul>
   *
   * <p>Method under test: {@link ExpressionResolver#containsExpression(Object)}
   */
  @Test
  @DisplayName("Test containsExpression(Object); then calls convertValue(Object, TypeReference)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ExpressionResolver.containsExpression(Object)"})
  void testContainsExpression_thenCallsConvertValue() throws IllegalArgumentException {
    // Arrange
    Mockito.<Map<String, ?>>when(
            objectMapper.convertValue(
                Mockito.<Object>any(), Mockito.<TypeReference<Map<String, Object>>>any()))
        .thenReturn(new HashMap<>());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    // Act
    boolean actualContainsExpressionResult =
        expressionResolver.containsExpression(new ObjectNode(nc));

    // Assert
    verify(objectMapper).convertValue(isA(Object.class), isA(TypeReference.class));
    assertFalse(actualContainsExpressionResult);
  }
}
