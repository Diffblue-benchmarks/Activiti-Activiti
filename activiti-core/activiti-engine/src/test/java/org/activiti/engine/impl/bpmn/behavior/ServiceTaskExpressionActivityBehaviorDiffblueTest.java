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
package org.activiti.engine.impl.bpmn.behavior;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ServiceTaskExpressionActivityBehaviorDiffblueTest {
  /**
   * Test {@link ServiceTaskExpressionActivityBehavior#ServiceTaskExpressionActivityBehavior(String,
   * Expression, Expression, String)}.
   *
   * <p>Method under test: {@link
   * ServiceTaskExpressionActivityBehavior#ServiceTaskExpressionActivityBehavior(String, Expression,
   * Expression, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ServiceTaskExpressionActivityBehavior.<init>(String, Expression, Expression, String)"
  })
  public void testNewServiceTaskExpressionActivityBehavior() {
    // Arrange
    FixedValue expression = new FixedValue(JSONObject.NULL);

    // Act
    ServiceTaskExpressionActivityBehavior actualServiceTaskExpressionActivityBehavior =
        new ServiceTaskExpressionActivityBehavior(
            "42", expression, new FixedValue(JSONObject.NULL), "Result Variable");

    // Assert
    Expression expression2 = actualServiceTaskExpressionActivityBehavior.expression;
    assertTrue(expression2 instanceof FixedValue);
    Expression expression3 = actualServiceTaskExpressionActivityBehavior.skipExpression;
    assertTrue(expression3 instanceof FixedValue);
    assertEquals("42", actualServiceTaskExpressionActivityBehavior.serviceTaskId);
    assertEquals("Result Variable", actualServiceTaskExpressionActivityBehavior.resultVariable);
    assertEquals("null", expression2.getExpressionText());
    assertEquals("null", expression3.getExpressionText());
    assertNull(actualServiceTaskExpressionActivityBehavior.getMultiInstanceActivityBehavior());
    assertFalse(actualServiceTaskExpressionActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualServiceTaskExpressionActivityBehavior.hasMultiInstanceCharacteristics());
  }

  /**
   * Test {@link ServiceTaskExpressionActivityBehavior#execute(DelegateExecution)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTaskExpressionActivityBehavior#execute(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ServiceTaskExpressionActivityBehavior.execute(DelegateExecution)"})
  public void testExecute_given42() {
    // Arrange
    FixedValue expression = new FixedValue(JSONObject.NULL);
    ServiceTaskExpressionActivityBehavior serviceTaskExpressionActivityBehavior =
        new ServiceTaskExpressionActivityBehavior(
            "42", expression, new FixedValue(true), "Result Variable");

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setProcessDefinitionId("42");
    execution.setTransientVariableLocal("_ACTIVITI_SKIP_EXPRESSION_ENABLED", true);

    // Act and Assert
    assertThrows(
        ActivitiException.class, () -> serviceTaskExpressionActivityBehavior.execute(execution));
  }

  /**
   * Test {@link ServiceTaskExpressionActivityBehavior#execute(DelegateExecution)}.
   *
   * <ul>
   *   <li>Given {@link FixedValue#FixedValue(Object)} with value is {@code true}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTaskExpressionActivityBehavior#execute(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ServiceTaskExpressionActivityBehavior.execute(DelegateExecution)"})
  public void testExecute_givenFixedValueWithValueIsTrue_thenThrowActivitiException() {
    // Arrange
    FixedValue expression = new FixedValue(JSONObject.NULL);
    ServiceTaskExpressionActivityBehavior serviceTaskExpressionActivityBehavior =
        new ServiceTaskExpressionActivityBehavior(
            "42", expression, new FixedValue(true), "Result Variable");

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setTransientVariableLocal("_ACTIVITI_SKIP_EXPRESSION_ENABLED", true);

    // Act and Assert
    assertThrows(
        ActivitiException.class, () -> serviceTaskExpressionActivityBehavior.execute(execution));
  }

  /**
   * Test {@link ServiceTaskExpressionActivityBehavior#execute(DelegateExecution)}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTaskExpressionActivityBehavior#execute(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ServiceTaskExpressionActivityBehavior.execute(DelegateExecution)"})
  public void testExecute_givenNull() {
    // Arrange
    FixedValue expression = new FixedValue(JSONObject.NULL);
    ServiceTaskExpressionActivityBehavior serviceTaskExpressionActivityBehavior =
        new ServiceTaskExpressionActivityBehavior(
            "42", expression, new FixedValue(JSONObject.NULL), "Result Variable");

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setTransientVariableLocal("_ACTIVITI_SKIP_EXPRESSION_ENABLED", JSONObject.NULL);

    // Act and Assert
    assertThrows(
        ActivitiException.class, () -> serviceTaskExpressionActivityBehavior.execute(execution));
  }

  /**
   * Test {@link ServiceTaskExpressionActivityBehavior#execute(DelegateExecution)}.
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTaskExpressionActivityBehavior#execute(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ServiceTaskExpressionActivityBehavior.execute(DelegateExecution)"})
  public void testExecute_givenTrue_thenThrowActivitiException() {
    // Arrange
    FixedValue expression = new FixedValue(JSONObject.NULL);
    ServiceTaskExpressionActivityBehavior serviceTaskExpressionActivityBehavior =
        new ServiceTaskExpressionActivityBehavior(
            "42", expression, new FixedValue(JSONObject.NULL), "Result Variable");

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setTransientVariableLocal("_ACTIVITI_SKIP_EXPRESSION_ENABLED", true);

    // Act and Assert
    assertThrows(
        ActivitiException.class, () -> serviceTaskExpressionActivityBehavior.execute(execution));
  }

  /**
   * Test {@link ServiceTaskExpressionActivityBehavior#execute(DelegateExecution)}.
   *
   * <ul>
   *   <li>When {@link ExecutionEntityImpl} (default constructor).
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link ServiceTaskExpressionActivityBehavior#execute(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ServiceTaskExpressionActivityBehavior.execute(DelegateExecution)"})
  public void testExecute_whenExecutionEntityImpl_thenThrowActivitiException() {
    // Arrange
    FixedValue expression = new FixedValue(JSONObject.NULL);
    ServiceTaskExpressionActivityBehavior serviceTaskExpressionActivityBehavior =
        new ServiceTaskExpressionActivityBehavior(
            "42", expression, new FixedValue(JSONObject.NULL), "Result Variable");

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> serviceTaskExpressionActivityBehavior.execute(new ExecutionEntityImpl()));
  }
}
