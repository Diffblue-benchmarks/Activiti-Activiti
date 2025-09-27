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
package org.activiti.engine.impl.bpmn.helper;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class SkipExpressionUtilDiffblueTest {
  /**
   * Test {@link SkipExpressionUtil#isSkipExpressionEnabled(DelegateExecution, Expression)} with
   * {@code DelegateExecution}, {@code Expression}.
   *
   * <p>Method under test: {@link SkipExpressionUtil#isSkipExpressionEnabled(DelegateExecution,
   * Expression)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean SkipExpressionUtil.isSkipExpressionEnabled(DelegateExecution, Expression)"
  })
  public void testIsSkipExpressionEnabledWithDelegateExecutionExpression() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setTransientVariableLocal("_ACTIVITI_SKIP_EXPRESSION_ENABLED", JSONObject.NULL);

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            SkipExpressionUtil.isSkipExpressionEnabled(execution, new FixedValue(JSONObject.NULL)));
  }

  /**
   * Test {@link SkipExpressionUtil#isSkipExpressionEnabled(DelegateExecution, Expression)} with
   * {@code DelegateExecution}, {@code Expression}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link SkipExpressionUtil#isSkipExpressionEnabled(DelegateExecution,
   * Expression)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean SkipExpressionUtil.isSkipExpressionEnabled(DelegateExecution, Expression)"
  })
  public void testIsSkipExpressionEnabledWithDelegateExecutionExpression_thenReturnFalse() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    boolean actualIsSkipExpressionEnabledResult =
        SkipExpressionUtil.isSkipExpressionEnabled(execution, new FixedValue(JSONObject.NULL));

    // Assert
    assertFalse(actualIsSkipExpressionEnabledResult);
  }

  /**
   * Test {@link SkipExpressionUtil#isSkipExpressionEnabled(DelegateExecution, Expression)} with
   * {@code DelegateExecution}, {@code Expression}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link SkipExpressionUtil#isSkipExpressionEnabled(DelegateExecution,
   * Expression)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean SkipExpressionUtil.isSkipExpressionEnabled(DelegateExecution, Expression)"
  })
  public void testIsSkipExpressionEnabledWithDelegateExecutionExpression_thenReturnFalse2() {
    // Arrange, Act and Assert
    assertFalse(
        SkipExpressionUtil.isSkipExpressionEnabled(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections(), (Expression) null));
  }

  /**
   * Test {@link SkipExpressionUtil#isSkipExpressionEnabled(DelegateExecution, Expression)} with
   * {@code DelegateExecution}, {@code Expression}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link SkipExpressionUtil#isSkipExpressionEnabled(DelegateExecution,
   * Expression)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean SkipExpressionUtil.isSkipExpressionEnabled(DelegateExecution, Expression)"
  })
  public void testIsSkipExpressionEnabledWithDelegateExecutionExpression_thenReturnTrue() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setTransientVariableLocal("_ACTIVITI_SKIP_EXPRESSION_ENABLED", true);

    // Act
    boolean actualIsSkipExpressionEnabledResult =
        SkipExpressionUtil.isSkipExpressionEnabled(execution, new FixedValue(JSONObject.NULL));

    // Assert
    assertTrue(actualIsSkipExpressionEnabledResult);
  }

  /**
   * Test {@link SkipExpressionUtil#isSkipExpressionEnabled(DelegateExecution, String)} with {@code
   * DelegateExecution}, {@code String}.
   *
   * <p>Method under test: {@link SkipExpressionUtil#isSkipExpressionEnabled(DelegateExecution,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean SkipExpressionUtil.isSkipExpressionEnabled(DelegateExecution, String)"
  })
  public void testIsSkipExpressionEnabledWithDelegateExecutionString() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setTransientVariableLocal("_ACTIVITI_SKIP_EXPRESSION_ENABLED", JSONObject.NULL);

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> SkipExpressionUtil.isSkipExpressionEnabled(execution, "Skip Expression"));
  }

  /**
   * Test {@link SkipExpressionUtil#isSkipExpressionEnabled(DelegateExecution, String)} with {@code
   * DelegateExecution}, {@code String}.
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link SkipExpressionUtil#isSkipExpressionEnabled(DelegateExecution,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean SkipExpressionUtil.isSkipExpressionEnabled(DelegateExecution, String)"
  })
  public void testIsSkipExpressionEnabledWithDelegateExecutionString_givenTrue_thenReturnTrue() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setTransientVariableLocal("_ACTIVITI_SKIP_EXPRESSION_ENABLED", true);

    // Act and Assert
    assertTrue(SkipExpressionUtil.isSkipExpressionEnabled(execution, "Skip Expression"));
  }

  /**
   * Test {@link SkipExpressionUtil#isSkipExpressionEnabled(DelegateExecution, String)} with {@code
   * DelegateExecution}, {@code String}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link SkipExpressionUtil#isSkipExpressionEnabled(DelegateExecution,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean SkipExpressionUtil.isSkipExpressionEnabled(DelegateExecution, String)"
  })
  public void testIsSkipExpressionEnabledWithDelegateExecutionString_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(
        SkipExpressionUtil.isSkipExpressionEnabled(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Skip Expression"));
  }

  /**
   * Test {@link SkipExpressionUtil#isSkipExpressionEnabled(DelegateExecution, String)} with {@code
   * DelegateExecution}, {@code String}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link SkipExpressionUtil#isSkipExpressionEnabled(DelegateExecution,
   * String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean SkipExpressionUtil.isSkipExpressionEnabled(DelegateExecution, String)"
  })
  public void testIsSkipExpressionEnabledWithDelegateExecutionString_thenReturnFalse2() {
    // Arrange, Act and Assert
    assertFalse(
        SkipExpressionUtil.isSkipExpressionEnabled(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections(), (String) null));
  }

  /**
   * Test {@link SkipExpressionUtil#shouldSkipFlowElement(DelegateExecution, Expression)} with
   * {@code execution}, {@code skipExpression}.
   *
   * <p>Method under test: {@link SkipExpressionUtil#shouldSkipFlowElement(DelegateExecution,
   * Expression)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean SkipExpressionUtil.shouldSkipFlowElement(DelegateExecution, Expression)"
  })
  public void testShouldSkipFlowElementWithExecutionSkipExpression() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> SkipExpressionUtil.shouldSkipFlowElement(execution, new FixedValue(JSONObject.NULL)));
  }

  /**
   * Test {@link SkipExpressionUtil#shouldSkipFlowElement(DelegateExecution, Expression)} with
   * {@code execution}, {@code skipExpression}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link SkipExpressionUtil#shouldSkipFlowElement(DelegateExecution,
   * Expression)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean SkipExpressionUtil.shouldSkipFlowElement(DelegateExecution, Expression)"
  })
  public void testShouldSkipFlowElementWithExecutionSkipExpression_thenReturnFalse() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    boolean actualShouldSkipFlowElementResult =
        SkipExpressionUtil.shouldSkipFlowElement(execution, new FixedValue(false));

    // Assert
    assertFalse(actualShouldSkipFlowElementResult);
  }

  /**
   * Test {@link SkipExpressionUtil#shouldSkipFlowElement(DelegateExecution, Expression)} with
   * {@code execution}, {@code skipExpression}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link SkipExpressionUtil#shouldSkipFlowElement(DelegateExecution,
   * Expression)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean SkipExpressionUtil.shouldSkipFlowElement(DelegateExecution, Expression)"
  })
  public void testShouldSkipFlowElementWithExecutionSkipExpression_thenReturnTrue() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act
    boolean actualShouldSkipFlowElementResult =
        SkipExpressionUtil.shouldSkipFlowElement(execution, new FixedValue(true));

    // Assert
    assertTrue(actualShouldSkipFlowElementResult);
  }
}
