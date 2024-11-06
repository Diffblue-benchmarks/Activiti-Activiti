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
import static org.mockito.Mockito.mock;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.interceptor.DelegateInterceptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VariableScopeExpressionEvaluator.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class VariableScopeExpressionEvaluatorDiffblueTest {
  @MockBean
  private VariableScope variableScope;

  @Autowired
  private VariableScopeExpressionEvaluator variableScopeExpressionEvaluator;

  /**
   * Test
   * {@link VariableScopeExpressionEvaluator#evaluate(Expression, ExpressionManager, DelegateInterceptor)}.
   * <ul>
   *   <li>When {@link FixedValue#FixedValue(Object)} with {@code Value}.</li>
   *   <li>Then return {@code Value}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link VariableScopeExpressionEvaluator#evaluate(Expression, ExpressionManager, DelegateInterceptor)}
   */
  @Test
  @DisplayName("Test evaluate(Expression, ExpressionManager, DelegateInterceptor); when FixedValue(Object) with 'Value'; then return 'Value'")
  void testEvaluate_whenFixedValueWithValue_thenReturnValue() {
    // Arrange
    FixedValue expression = new FixedValue("Value");

    // Act and Assert
    assertEquals("Value", variableScopeExpressionEvaluator.evaluate(expression, new ExpressionManager(),
        mock(DelegateInterceptor.class)));
  }
}
