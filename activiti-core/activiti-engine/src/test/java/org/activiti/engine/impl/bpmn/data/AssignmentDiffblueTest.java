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
package org.activiti.engine.impl.bpmn.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;

public class AssignmentDiffblueTest {
  /**
   * Test {@link Assignment#Assignment(Expression, Expression)}.
   * <p>
   * Method under test: {@link Assignment#Assignment(Expression, Expression)}
   */
  @Test
  public void testNewAssignment() {
    // Arrange
    FixedValue fromExpression = new FixedValue(JSONObject.NULL);

    // Act
    Assignment actualAssignment = new Assignment(fromExpression, new FixedValue(JSONObject.NULL));

    // Assert
    Expression expression = actualAssignment.fromExpression;
    assertTrue(expression instanceof FixedValue);
    Expression expression2 = actualAssignment.toExpression;
    assertTrue(expression2 instanceof FixedValue);
    assertEquals("null", expression.getExpressionText());
    assertEquals("null", expression2.getExpressionText());
  }

  /**
   * Test {@link Assignment#evaluate(DelegateExecution)}.
   * <ul>
   *   <li>Given {@link FixedValue}
   * {@link FixedValue#setValue(Object, VariableScope)} does nothing.</li>
   *   <li>Then calls {@link FixedValue#setValue(Object, VariableScope)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Assignment#evaluate(DelegateExecution)}
   */
  @Test
  public void testEvaluate_givenFixedValueSetValueDoesNothing_thenCallsSetValue() {
    // Arrange
    FixedValue toExpression = mock(FixedValue.class);
    doNothing().when(toExpression).setValue(Mockito.<Object>any(), Mockito.<VariableScope>any());
    Assignment assignment = new Assignment(new FixedValue(JSONObject.NULL), toExpression);

    // Act
    assignment.evaluate(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert that nothing has changed
    verify(toExpression).setValue(isA(Object.class), isA(VariableScope.class));
  }
}
