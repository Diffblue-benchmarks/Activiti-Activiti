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
package org.activiti.engine.impl.bpmn.listener;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.activiti.bpmn.model.Task;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.examples.bpmn.tasklistener.CurrentTaskTransactionDependentTaskListener;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DelegateExpressionTransactionDependentTaskListenerDiffblueTest {
  @InjectMocks
  private DelegateExpressionTransactionDependentTaskListener
      delegateExpressionTransactionDependentTaskListener;

  @Mock private Expression expression;

  /**
   * Test {@link
   * DelegateExpressionTransactionDependentTaskListener#DelegateExpressionTransactionDependentTaskListener(Expression)}.
   *
   * <p>Method under test: {@link
   * DelegateExpressionTransactionDependentTaskListener#DelegateExpressionTransactionDependentTaskListener(Expression)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DelegateExpressionTransactionDependentTaskListener.<init>(Expression)"})
  public void testNewDelegateExpressionTransactionDependentTaskListener() {
    // Arrange and Act
    DelegateExpressionTransactionDependentTaskListener
        actualDelegateExpressionTransactionDependentTaskListener =
            new DelegateExpressionTransactionDependentTaskListener(new FixedValue(JSONObject.NULL));

    // Assert
    Expression expression = actualDelegateExpressionTransactionDependentTaskListener.expression;
    assertTrue(expression instanceof FixedValue);
    assertEquals("null", expression.getExpressionText());
    assertEquals(
        "null", actualDelegateExpressionTransactionDependentTaskListener.getExpressionText());
  }

  /**
   * Test {@link DelegateExpressionTransactionDependentTaskListener#notify(String, String, Task,
   * Map, Map)} with {@code String}, {@code String}, {@code Task}, {@code Map}, {@code Map}.
   *
   * <p>Method under test: {@link DelegateExpressionTransactionDependentTaskListener#notify(String,
   * String, Task, Map, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DelegateExpressionTransactionDependentTaskListener.notify(String, String, Task, Map, Map)"
  })
  public void testNotifyWithStringStringTaskMapMap() {
    // Arrange
    when(expression.getValue(Mockito.<VariableScope>any()))
        .thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    Task task = new Task();
    HashMap<String, Object> executionVariables = new HashMap<>();

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            delegateExpressionTransactionDependentTaskListener.notify(
                "42", "42", task, executionVariables, new HashMap<>()));
    verify(expression).getValue(isA(VariableScope.class));
  }

  /**
   * Test {@link DelegateExpressionTransactionDependentTaskListener#notify(String, String, Task,
   * Map, Map)} with {@code String}, {@code String}, {@code Task}, {@code Map}, {@code Map}.
   *
   * <p>Method under test: {@link DelegateExpressionTransactionDependentTaskListener#notify(String,
   * String, Task, Map, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DelegateExpressionTransactionDependentTaskListener.notify(String, String, Task, Map, Map)"
  })
  public void testNotifyWithStringStringTaskMapMap2() {
    // Arrange
    when(expression.getValue(Mockito.<VariableScope>any()))
        .thenReturn(new CurrentTaskTransactionDependentTaskListener());
    Task task = new Task();
    HashMap<String, Object> executionVariables = new HashMap<>();

    // Act
    delegateExpressionTransactionDependentTaskListener.notify(
        "42", "42", task, executionVariables, new HashMap<>());

    // Assert
    verify(expression).getValue(isA(VariableScope.class));
  }

  /**
   * Test {@link DelegateExpressionTransactionDependentTaskListener#notify(String, String, Task,
   * Map, Map)} with {@code String}, {@code String}, {@code Task}, {@code Map}, {@code Map}.
   *
   * <ul>
   *   <li>Given {@link Expression} {@link Expression#getValue(VariableScope)} return {@link
   *       JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link DelegateExpressionTransactionDependentTaskListener#notify(String,
   * String, Task, Map, Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DelegateExpressionTransactionDependentTaskListener.notify(String, String, Task, Map, Map)"
  })
  public void testNotifyWithStringStringTaskMapMap_givenExpressionGetValueReturnNull() {
    // Arrange
    when(expression.getValue(Mockito.<VariableScope>any())).thenReturn(JSONObject.NULL);
    Task task = new Task();
    HashMap<String, Object> executionVariables = new HashMap<>();

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            delegateExpressionTransactionDependentTaskListener.notify(
                "42", "42", task, executionVariables, new HashMap<>()));
    verify(expression).getValue(isA(VariableScope.class));
  }

  /**
   * Test {@link DelegateExpressionTransactionDependentTaskListener#getExpressionText()}.
   *
   * <ul>
   *   <li>Given {@link FixedValue#FixedValue(Object)} with value is {@link JSONObject#NULL}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DelegateExpressionTransactionDependentTaskListener#getExpressionText()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String DelegateExpressionTransactionDependentTaskListener.getExpressionText()"
  })
  public void testGetExpressionText_givenFixedValueWithValueIsNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertEquals(
        "null",
        new DelegateExpressionTransactionDependentTaskListener(new FixedValue(JSONObject.NULL))
            .getExpressionText());
  }
}
