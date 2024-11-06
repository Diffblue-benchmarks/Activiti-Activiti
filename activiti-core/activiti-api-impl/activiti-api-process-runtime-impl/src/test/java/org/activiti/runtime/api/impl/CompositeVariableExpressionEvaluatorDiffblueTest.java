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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.engine.ActivitiException;
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

@ContextConfiguration(classes = {CompositeVariableExpressionEvaluator.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CompositeVariableExpressionEvaluatorDiffblueTest {
  @Autowired
  private CompositeVariableExpressionEvaluator compositeVariableExpressionEvaluator;

  @MockBean
  private SimpleMapExpressionEvaluator simpleMapExpressionEvaluator;

  @MockBean
  private VariableScopeExpressionEvaluator variableScopeExpressionEvaluator;

  /**
   * Test
   * {@link CompositeVariableExpressionEvaluator#evaluate(Expression, ExpressionManager, DelegateInterceptor)}.
   * <ul>
   *   <li>Given {@link SimpleMapExpressionEvaluator}
   * {@link SimpleMapExpressionEvaluator#evaluate(Expression, ExpressionManager, DelegateInterceptor)}
   * return {@code Evaluate}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CompositeVariableExpressionEvaluator#evaluate(Expression, ExpressionManager, DelegateInterceptor)}
   */
  @Test
  @DisplayName("Test evaluate(Expression, ExpressionManager, DelegateInterceptor); given SimpleMapExpressionEvaluator evaluate(Expression, ExpressionManager, DelegateInterceptor) return 'Evaluate'")
  void testEvaluate_givenSimpleMapExpressionEvaluatorEvaluateReturnEvaluate() {
    // Arrange
    when(simpleMapExpressionEvaluator.evaluate(Mockito.<Expression>any(), Mockito.<ExpressionManager>any(),
        Mockito.<DelegateInterceptor>any())).thenReturn("Evaluate");
    FixedValue expression = new FixedValue("Value");

    // Act
    Object actualEvaluateResult = compositeVariableExpressionEvaluator.evaluate(expression, new ExpressionManager(),
        mock(DelegateInterceptor.class));

    // Assert
    verify(simpleMapExpressionEvaluator).evaluate(isA(Expression.class), isA(ExpressionManager.class),
        isA(DelegateInterceptor.class));
    assertEquals("Evaluate", actualEvaluateResult);
  }

  /**
   * Test
   * {@link CompositeVariableExpressionEvaluator#evaluate(Expression, ExpressionManager, DelegateInterceptor)}.
   * <ul>
   *   <li>Given {@link VariableScopeExpressionEvaluator}
   * {@link VariableScopeExpressionEvaluator#evaluate(Expression, ExpressionManager, DelegateInterceptor)}
   * return {@code Evaluate}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CompositeVariableExpressionEvaluator#evaluate(Expression, ExpressionManager, DelegateInterceptor)}
   */
  @Test
  @DisplayName("Test evaluate(Expression, ExpressionManager, DelegateInterceptor); given VariableScopeExpressionEvaluator evaluate(Expression, ExpressionManager, DelegateInterceptor) return 'Evaluate'")
  void testEvaluate_givenVariableScopeExpressionEvaluatorEvaluateReturnEvaluate() {
    // Arrange
    when(simpleMapExpressionEvaluator.evaluate(Mockito.<Expression>any(), Mockito.<ExpressionManager>any(),
        Mockito.<DelegateInterceptor>any())).thenThrow(new ActivitiException("An error occurred"));
    when(variableScopeExpressionEvaluator.evaluate(Mockito.<Expression>any(), Mockito.<ExpressionManager>any(),
        Mockito.<DelegateInterceptor>any())).thenReturn("Evaluate");
    FixedValue expression = new FixedValue("Value");

    // Act
    Object actualEvaluateResult = compositeVariableExpressionEvaluator.evaluate(expression, new ExpressionManager(),
        mock(DelegateInterceptor.class));

    // Assert
    verify(simpleMapExpressionEvaluator).evaluate(isA(Expression.class), isA(ExpressionManager.class),
        isA(DelegateInterceptor.class));
    verify(variableScopeExpressionEvaluator).evaluate(isA(Expression.class), isA(ExpressionManager.class),
        isA(DelegateInterceptor.class));
    assertEquals("Evaluate", actualEvaluateResult);
  }

  /**
   * Test
   * {@link CompositeVariableExpressionEvaluator#evaluate(Expression, ExpressionManager, DelegateInterceptor)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CompositeVariableExpressionEvaluator#evaluate(Expression, ExpressionManager, DelegateInterceptor)}
   */
  @Test
  @DisplayName("Test evaluate(Expression, ExpressionManager, DelegateInterceptor); then throw ActivitiException")
  void testEvaluate_thenThrowActivitiException() {
    // Arrange
    when(simpleMapExpressionEvaluator.evaluate(Mockito.<Expression>any(), Mockito.<ExpressionManager>any(),
        Mockito.<DelegateInterceptor>any())).thenThrow(new ActivitiException("An error occurred"));
    when(variableScopeExpressionEvaluator.evaluate(Mockito.<Expression>any(), Mockito.<ExpressionManager>any(),
        Mockito.<DelegateInterceptor>any())).thenThrow(new ActivitiException("An error occurred"));
    FixedValue expression = new FixedValue("Value");

    // Act and Assert
    assertThrows(ActivitiException.class, () -> compositeVariableExpressionEvaluator.evaluate(expression,
        new ExpressionManager(), mock(DelegateInterceptor.class)));
    verify(simpleMapExpressionEvaluator).evaluate(isA(Expression.class), isA(ExpressionManager.class),
        isA(DelegateInterceptor.class));
    verify(variableScopeExpressionEvaluator).evaluate(isA(Expression.class), isA(ExpressionManager.class),
        isA(DelegateInterceptor.class));
  }
}
