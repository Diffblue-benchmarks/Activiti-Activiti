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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.Map;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.el.JuelExpression;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.examples.bpmn.executionlistener.CurrentActivityTransactionDependentExecutionListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DelegateExpressionTransactionDependentExecutionListenerDiffblueTest {
  @InjectMocks
  private DelegateExpressionTransactionDependentExecutionListener delegateExpressionTransactionDependentExecutionListener;

  @Mock
  private Expression expression;

  /**
   * Method under test:
   * {@link DelegateExpressionTransactionDependentExecutionListener#notify(String, String, FlowElement, Map, Map)}
   */
  @Test
  public void testNotify() {
    // Arrange
    when(expression.getValue(Mockito.<VariableScope>any())).thenReturn(JSONObject.NULL);
    AdhocSubProcess flowElement = new AdhocSubProcess();
    HashMap<String, Object> executionVariables = new HashMap<>();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> delegateExpressionTransactionDependentExecutionListener
        .notify("42", "42", flowElement, executionVariables, new HashMap<>()));
    verify(expression).getValue(isA(VariableScope.class));
  }

  /**
   * Method under test:
   * {@link DelegateExpressionTransactionDependentExecutionListener#notify(String, String, FlowElement, Map, Map)}
   */
  @Test
  public void testNotify2() {
    // Arrange
    when(expression.getValue(Mockito.<VariableScope>any()))
        .thenReturn(new CurrentActivityTransactionDependentExecutionListener());
    AdhocSubProcess flowElement = new AdhocSubProcess();
    HashMap<String, Object> executionVariables = new HashMap<>();

    // Act
    delegateExpressionTransactionDependentExecutionListener.notify("42", "42", flowElement, executionVariables,
        new HashMap<>());

    // Assert
    verify(expression).getValue(isA(VariableScope.class));
  }

  /**
   * Method under test:
   * {@link DelegateExpressionTransactionDependentExecutionListener#getExpressionText()}
   */
  @Test
  public void testGetExpressionText() {
    // Arrange, Act and Assert
    assertEquals("null", (new DelegateExpressionTransactionDependentExecutionListener(new FixedValue(JSONObject.NULL)))
        .getExpressionText());
  }

  /**
   * Method under test:
   * {@link DelegateExpressionTransactionDependentExecutionListener#getExpressionText()}
   */
  @Test
  public void testGetExpressionText2() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act and Assert
    assertEquals("null",
        (new DelegateExpressionTransactionDependentExecutionListener(
            new JuelExpression(new ObjectValueExpression(converter, JSONObject.NULL, type), "null")))
            .getExpressionText());
  }

  /**
   * Method under test:
   * {@link DelegateExpressionTransactionDependentExecutionListener#DelegateExpressionTransactionDependentExecutionListener(Expression)}
   */
  @Test
  public void testNewDelegateExpressionTransactionDependentExecutionListener() {
    // Arrange and Act
    DelegateExpressionTransactionDependentExecutionListener actualDelegateExpressionTransactionDependentExecutionListener = new DelegateExpressionTransactionDependentExecutionListener(
        new FixedValue(JSONObject.NULL));

    // Assert
    Expression expression = actualDelegateExpressionTransactionDependentExecutionListener.expression;
    assertTrue(expression instanceof FixedValue);
    assertEquals("null", expression.getExpressionText());
    assertEquals("null", actualDelegateExpressionTransactionDependentExecutionListener.getExpressionText());
  }
}
