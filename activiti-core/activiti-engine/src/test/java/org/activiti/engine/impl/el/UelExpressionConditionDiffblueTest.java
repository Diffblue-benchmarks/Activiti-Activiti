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
package org.activiti.engine.impl.el;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UelExpressionConditionDiffblueTest {
  @Mock
  private Expression expression;

  @InjectMocks
  private UelExpressionCondition uelExpressionCondition;

  /**
   * Method under test:
   * {@link UelExpressionCondition#evaluate(String, DelegateExecution)}
   */
  @Test
  public void testEvaluate() {
    // Arrange
    when(expression.getValue(Mockito.<VariableScope>any())).thenReturn(JSONObject.NULL);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> uelExpressionCondition.evaluate("42", ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    verify(expression).getValue(isA(VariableScope.class));
  }

  /**
   * Method under test:
   * {@link UelExpressionCondition#evaluate(String, DelegateExecution)}
   */
  @Test
  public void testEvaluate2() {
    // Arrange
    when(expression.getValue(Mockito.<VariableScope>any())).thenReturn(true);

    // Act
    boolean actualEvaluateResult = uelExpressionCondition.evaluate("42",
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(expression).getValue(isA(VariableScope.class));
    assertTrue(actualEvaluateResult);
  }

  /**
   * Method under test:
   * {@link UelExpressionCondition#evaluate(String, DelegateExecution)}
   */
  @Test
  public void testEvaluate3() {
    // Arrange
    when(expression.getValue(Mockito.<VariableScope>any())).thenReturn(false);

    // Act
    boolean actualEvaluateResult = uelExpressionCondition.evaluate("42",
        ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    verify(expression).getValue(isA(VariableScope.class));
    assertFalse(actualEvaluateResult);
  }

  /**
   * Method under test:
   * {@link UelExpressionCondition#evaluate(String, DelegateExecution)}
   */
  @Test
  public void testEvaluate4() {
    // Arrange
    when(expression.getValue(Mockito.<VariableScope>any())).thenReturn(null);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> uelExpressionCondition.evaluate("42", ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    verify(expression).getValue(isA(VariableScope.class));
  }

  /**
   * Method under test:
   * {@link UelExpressionCondition#evaluate(String, DelegateExecution)}
   */
  @Test
  public void testEvaluate5() {
    // Arrange
    when(expression.getValue(Mockito.<VariableScope>any())).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> uelExpressionCondition.evaluate("42", ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
    verify(expression).getValue(isA(VariableScope.class));
  }

  /**
   * Method under test:
   * {@link UelExpressionCondition#UelExpressionCondition(Expression)}
   */
  @Test
  public void testNewUelExpressionCondition() {
    // Arrange, Act and Assert
    Expression expression = (new UelExpressionCondition(new FixedValue(JSONObject.NULL))).expression;
    assertTrue(expression instanceof FixedValue);
    assertEquals("null", expression.getExpressionText());
  }
}
