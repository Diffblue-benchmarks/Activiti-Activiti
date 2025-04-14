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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class SkipExpressionUtilDiffblueTest {
  /**
   * Test {@link SkipExpressionUtil#isSkipExpressionEnabled(DelegateExecution, Expression)} with {@code DelegateExecution}, {@code Expression}.
   * <p>
   * Method under test: {@link SkipExpressionUtil#isSkipExpressionEnabled(DelegateExecution, Expression)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean SkipExpressionUtil.isSkipExpressionEnabled(DelegateExecution, Expression)"})
  public void testIsSkipExpressionEnabledWithDelegateExecutionExpression() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setTransientVariableLocal("_ACTIVITI_SKIP_EXPRESSION_ENABLED", JSONObject.NULL);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> SkipExpressionUtil.isSkipExpressionEnabled(execution, new FixedValue(JSONObject.NULL)));
  }

  /**
   * Test {@link SkipExpressionUtil#isSkipExpressionEnabled(DelegateExecution, Expression)} with {@code DelegateExecution}, {@code Expression}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SkipExpressionUtil#isSkipExpressionEnabled(DelegateExecution, Expression)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean SkipExpressionUtil.isSkipExpressionEnabled(DelegateExecution, Expression)"})
  public void testIsSkipExpressionEnabledWithDelegateExecutionExpression_thenReturnFalse() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act and Assert
    assertFalse(SkipExpressionUtil.isSkipExpressionEnabled(execution, new FixedValue(JSONObject.NULL)));
  }

  /**
   * Test {@link SkipExpressionUtil#isSkipExpressionEnabled(DelegateExecution, Expression)} with {@code DelegateExecution}, {@code Expression}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SkipExpressionUtil#isSkipExpressionEnabled(DelegateExecution, Expression)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean SkipExpressionUtil.isSkipExpressionEnabled(DelegateExecution, Expression)"})
  public void testIsSkipExpressionEnabledWithDelegateExecutionExpression_thenReturnFalse2() {
    // Arrange, Act and Assert
    assertFalse(SkipExpressionUtil.isSkipExpressionEnabled(ExecutionEntityImpl.createWithEmptyRelationshipCollections(),
        (Expression) null));
  }

  /**
   * Test {@link SkipExpressionUtil#isSkipExpressionEnabled(DelegateExecution, Expression)} with {@code DelegateExecution}, {@code Expression}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SkipExpressionUtil#isSkipExpressionEnabled(DelegateExecution, Expression)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean SkipExpressionUtil.isSkipExpressionEnabled(DelegateExecution, Expression)"})
  public void testIsSkipExpressionEnabledWithDelegateExecutionExpression_thenReturnTrue() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setTransientVariableLocal("_ACTIVITI_SKIP_EXPRESSION_ENABLED", true);

    // Act and Assert
    assertTrue(SkipExpressionUtil.isSkipExpressionEnabled(execution, new FixedValue(JSONObject.NULL)));
  }

  /**
   * Test {@link SkipExpressionUtil#isSkipExpressionEnabled(DelegateExecution, String)} with {@code DelegateExecution}, {@code String}.
   * <p>
   * Method under test: {@link SkipExpressionUtil#isSkipExpressionEnabled(DelegateExecution, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean SkipExpressionUtil.isSkipExpressionEnabled(DelegateExecution, String)"})
  public void testIsSkipExpressionEnabledWithDelegateExecutionString() {
    // Arrange
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getVariable(Mockito.<String>any())).thenReturn(JSONObject.NULL);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> SkipExpressionUtil.isSkipExpressionEnabled(execution, "Skip Expression"));
    verify(execution).getVariable(eq("_ACTIVITI_SKIP_EXPRESSION_ENABLED"));
  }

  /**
   * Test {@link SkipExpressionUtil#isSkipExpressionEnabled(DelegateExecution, String)} with {@code DelegateExecution}, {@code String}.
   * <ul>
   *   <li>Given {@code true}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SkipExpressionUtil#isSkipExpressionEnabled(DelegateExecution, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean SkipExpressionUtil.isSkipExpressionEnabled(DelegateExecution, String)"})
  public void testIsSkipExpressionEnabledWithDelegateExecutionString_givenTrue_thenReturnTrue() {
    // Arrange
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getVariable(Mockito.<String>any())).thenReturn(true);

    // Act
    boolean actualIsSkipExpressionEnabledResult = SkipExpressionUtil.isSkipExpressionEnabled(execution,
        "Skip Expression");

    // Assert
    verify(execution).getVariable(eq("_ACTIVITI_SKIP_EXPRESSION_ENABLED"));
    assertTrue(actualIsSkipExpressionEnabledResult);
  }

  /**
   * Test {@link SkipExpressionUtil#isSkipExpressionEnabled(DelegateExecution, String)} with {@code DelegateExecution}, {@code String}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SkipExpressionUtil#isSkipExpressionEnabled(DelegateExecution, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean SkipExpressionUtil.isSkipExpressionEnabled(DelegateExecution, String)"})
  public void testIsSkipExpressionEnabledWithDelegateExecutionString_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(SkipExpressionUtil.isSkipExpressionEnabled(ExecutionEntityImpl.createWithEmptyRelationshipCollections(),
        "Skip Expression"));
  }

  /**
   * Test {@link SkipExpressionUtil#isSkipExpressionEnabled(DelegateExecution, String)} with {@code DelegateExecution}, {@code String}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SkipExpressionUtil#isSkipExpressionEnabled(DelegateExecution, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean SkipExpressionUtil.isSkipExpressionEnabled(DelegateExecution, String)"})
  public void testIsSkipExpressionEnabledWithDelegateExecutionString_thenReturnFalse2() {
    // Arrange, Act and Assert
    assertFalse(SkipExpressionUtil.isSkipExpressionEnabled(ExecutionEntityImpl.createWithEmptyRelationshipCollections(),
        (String) null));
  }

  /**
   * Test {@link SkipExpressionUtil#shouldSkipFlowElement(DelegateExecution, Expression)} with {@code execution}, {@code skipExpression}.
   * <p>
   * Method under test: {@link SkipExpressionUtil#shouldSkipFlowElement(DelegateExecution, Expression)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean SkipExpressionUtil.shouldSkipFlowElement(DelegateExecution, Expression)"})
  public void testShouldSkipFlowElementWithExecutionSkipExpression() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> SkipExpressionUtil.shouldSkipFlowElement(execution, new FixedValue(JSONObject.NULL)));
  }

  /**
   * Test {@link SkipExpressionUtil#shouldSkipFlowElement(DelegateExecution, Expression)} with {@code execution}, {@code skipExpression}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SkipExpressionUtil#shouldSkipFlowElement(DelegateExecution, Expression)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean SkipExpressionUtil.shouldSkipFlowElement(DelegateExecution, Expression)"})
  public void testShouldSkipFlowElementWithExecutionSkipExpression_thenReturnFalse() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act and Assert
    assertFalse(SkipExpressionUtil.shouldSkipFlowElement(execution, new FixedValue(false)));
  }

  /**
   * Test {@link SkipExpressionUtil#shouldSkipFlowElement(DelegateExecution, Expression)} with {@code execution}, {@code skipExpression}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SkipExpressionUtil#shouldSkipFlowElement(DelegateExecution, Expression)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean SkipExpressionUtil.shouldSkipFlowElement(DelegateExecution, Expression)"})
  public void testShouldSkipFlowElementWithExecutionSkipExpression_thenReturnTrue() {
    // Arrange
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();

    // Act and Assert
    assertTrue(SkipExpressionUtil.shouldSkipFlowElement(execution, new FixedValue(true)));
  }
}
