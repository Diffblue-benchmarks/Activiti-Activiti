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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.bpmn.parser.FieldDeclaration;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ServiceTaskDelegateExpressionActivityBehaviorDiffblueTest {
  @Mock
  private Expression expression;

  @Mock
  private List<FieldDeclaration> list;

  @InjectMocks
  private ServiceTaskDelegateExpressionActivityBehavior serviceTaskDelegateExpressionActivityBehavior;

  @InjectMocks
  private String string;

  /**
   * Test
   * {@link ServiceTaskDelegateExpressionActivityBehavior#ServiceTaskDelegateExpressionActivityBehavior(String, Expression, Expression, List)}.
   * <ul>
   *   <li>Given {@link FieldDeclaration#FieldDeclaration()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ServiceTaskDelegateExpressionActivityBehavior#ServiceTaskDelegateExpressionActivityBehavior(String, Expression, Expression, List)}
   */
  @Test
  public void testNewServiceTaskDelegateExpressionActivityBehavior_givenFieldDeclaration() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(new FieldDeclaration());

    // Act
    ServiceTaskDelegateExpressionActivityBehavior actualServiceTaskDelegateExpressionActivityBehavior = new ServiceTaskDelegateExpressionActivityBehavior(
        "42", expression, expression, fieldDeclarations);

    // Assert
    assertEquals("42", actualServiceTaskDelegateExpressionActivityBehavior.serviceTaskId);
    assertNull(actualServiceTaskDelegateExpressionActivityBehavior.getMultiInstanceActivityBehavior());
    assertFalse(actualServiceTaskDelegateExpressionActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualServiceTaskDelegateExpressionActivityBehavior.hasMultiInstanceCharacteristics());
  }

  /**
   * Test
   * {@link ServiceTaskDelegateExpressionActivityBehavior#ServiceTaskDelegateExpressionActivityBehavior(String, Expression, Expression, List)}.
   * <ul>
   *   <li>Given {@link FieldDeclaration#FieldDeclaration()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ServiceTaskDelegateExpressionActivityBehavior#ServiceTaskDelegateExpressionActivityBehavior(String, Expression, Expression, List)}
   */
  @Test
  public void testNewServiceTaskDelegateExpressionActivityBehavior_givenFieldDeclaration2() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(new FieldDeclaration());
    fieldDeclarations.add(new FieldDeclaration());

    // Act
    ServiceTaskDelegateExpressionActivityBehavior actualServiceTaskDelegateExpressionActivityBehavior = new ServiceTaskDelegateExpressionActivityBehavior(
        "42", expression, expression, fieldDeclarations);

    // Assert
    assertEquals("42", actualServiceTaskDelegateExpressionActivityBehavior.serviceTaskId);
    assertNull(actualServiceTaskDelegateExpressionActivityBehavior.getMultiInstanceActivityBehavior());
    assertFalse(actualServiceTaskDelegateExpressionActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualServiceTaskDelegateExpressionActivityBehavior.hasMultiInstanceCharacteristics());
  }

  /**
   * Test
   * {@link ServiceTaskDelegateExpressionActivityBehavior#ServiceTaskDelegateExpressionActivityBehavior(String, Expression, Expression, List)}.
   * <ul>
   *   <li>Given {@link FieldDeclaration}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ServiceTaskDelegateExpressionActivityBehavior#ServiceTaskDelegateExpressionActivityBehavior(String, Expression, Expression, List)}
   */
  @Test
  public void testNewServiceTaskDelegateExpressionActivityBehavior_givenFieldDeclaration3() {
    // Arrange
    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(mock(FieldDeclaration.class));

    // Act
    ServiceTaskDelegateExpressionActivityBehavior actualServiceTaskDelegateExpressionActivityBehavior = new ServiceTaskDelegateExpressionActivityBehavior(
        "42", expression, expression, fieldDeclarations);

    // Assert
    assertEquals("42", actualServiceTaskDelegateExpressionActivityBehavior.serviceTaskId);
    assertNull(actualServiceTaskDelegateExpressionActivityBehavior.getMultiInstanceActivityBehavior());
    assertFalse(actualServiceTaskDelegateExpressionActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualServiceTaskDelegateExpressionActivityBehavior.hasMultiInstanceCharacteristics());
  }

  /**
   * Test
   * {@link ServiceTaskDelegateExpressionActivityBehavior#ServiceTaskDelegateExpressionActivityBehavior(String, Expression, Expression, List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ServiceTaskDelegateExpressionActivityBehavior#ServiceTaskDelegateExpressionActivityBehavior(String, Expression, Expression, List)}
   */
  @Test
  public void testNewServiceTaskDelegateExpressionActivityBehavior_whenArrayList() {
    // Arrange and Act
    ServiceTaskDelegateExpressionActivityBehavior actualServiceTaskDelegateExpressionActivityBehavior = new ServiceTaskDelegateExpressionActivityBehavior(
        "42", expression, expression, new ArrayList<>());

    // Assert
    assertEquals("42", actualServiceTaskDelegateExpressionActivityBehavior.serviceTaskId);
    assertNull(actualServiceTaskDelegateExpressionActivityBehavior.getMultiInstanceActivityBehavior());
    assertFalse(actualServiceTaskDelegateExpressionActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualServiceTaskDelegateExpressionActivityBehavior.hasMultiInstanceCharacteristics());
  }

  /**
   * Test
   * {@link ServiceTaskDelegateExpressionActivityBehavior#trigger(DelegateExecution, String, Object)}.
   * <ul>
   *   <li>Given {@link AbstractBpmnActivityBehavior}
   * {@link FlowNodeActivityBehavior#trigger(DelegateExecution, String, Object)}
   * does nothing.</li>
   *   <li>Then calls
   * {@link FlowNodeActivityBehavior#trigger(DelegateExecution, String, Object)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ServiceTaskDelegateExpressionActivityBehavior#trigger(DelegateExecution, String, Object)}
   */
  @Test
  public void testTrigger_givenAbstractBpmnActivityBehaviorTriggerDoesNothing_thenCallsTrigger() {
    // Arrange
    AbstractBpmnActivityBehavior abstractBpmnActivityBehavior = mock(AbstractBpmnActivityBehavior.class);
    doNothing().when(abstractBpmnActivityBehavior)
        .trigger(Mockito.<DelegateExecution>any(), Mockito.<String>any(), Mockito.<Object>any());
    when(expression.getValue(Mockito.<VariableScope>any())).thenReturn(abstractBpmnActivityBehavior);
    when(list.size()).thenReturn(0);

    // Act
    serviceTaskDelegateExpressionActivityBehavior.trigger(ExecutionEntityImpl.createWithEmptyRelationshipCollections(),
        "Signal Name", JSONObject.NULL);

    // Assert
    verify(list).size();
    verify(expression).getValue(isA(VariableScope.class));
    verify(abstractBpmnActivityBehavior).trigger(isA(DelegateExecution.class), eq("Signal Name"), isA(Object.class));
  }

  /**
   * Test
   * {@link ServiceTaskDelegateExpressionActivityBehavior#trigger(DelegateExecution, String, Object)}.
   * <ul>
   *   <li>Given {@link Expression} {@link Expression#getValue(VariableScope)}
   * return {@link JSONObject#NULL}.</li>
   *   <li>Then calls {@link List#size()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ServiceTaskDelegateExpressionActivityBehavior#trigger(DelegateExecution, String, Object)}
   */
  @Test
  public void testTrigger_givenExpressionGetValueReturnNull_thenCallsSize() {
    // Arrange
    when(expression.getValue(Mockito.<VariableScope>any())).thenReturn(JSONObject.NULL);
    when(list.size()).thenReturn(0);

    // Act
    serviceTaskDelegateExpressionActivityBehavior.trigger(ExecutionEntityImpl.createWithEmptyRelationshipCollections(),
        "Signal Name", JSONObject.NULL);

    // Assert
    verify(list).size();
    verify(expression).getValue(isA(VariableScope.class));
  }

  /**
   * Test
   * {@link ServiceTaskDelegateExpressionActivityBehavior#execute(DelegateExecution)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ServiceTaskDelegateExpressionActivityBehavior#execute(DelegateExecution)}
   */
  @Test
  public void testExecute_given42() {
    // Arrange
    FixedValue expression = new FixedValue(JSONObject.NULL);
    FixedValue skipExpression = new FixedValue(true);
    ServiceTaskDelegateExpressionActivityBehavior serviceTaskDelegateExpressionActivityBehavior = new ServiceTaskDelegateExpressionActivityBehavior(
        "42", expression, skipExpression, new ArrayList<>());
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setProcessDefinitionId("42");
    execution.setTransientVariableLocal("_ACTIVITI_SKIP_EXPRESSION_ENABLED", true);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> serviceTaskDelegateExpressionActivityBehavior.execute(execution));
  }

  /**
   * Test
   * {@link ServiceTaskDelegateExpressionActivityBehavior#execute(DelegateExecution)}.
   * <ul>
   *   <li>Given {@link FixedValue#FixedValue(Object)} with value is
   * {@code true}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ServiceTaskDelegateExpressionActivityBehavior#execute(DelegateExecution)}
   */
  @Test
  public void testExecute_givenFixedValueWithValueIsTrue_thenThrowActivitiException() {
    // Arrange
    FixedValue expression = new FixedValue(JSONObject.NULL);
    FixedValue skipExpression = new FixedValue(true);
    ServiceTaskDelegateExpressionActivityBehavior serviceTaskDelegateExpressionActivityBehavior = new ServiceTaskDelegateExpressionActivityBehavior(
        "42", expression, skipExpression, new ArrayList<>());
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setTransientVariableLocal("_ACTIVITI_SKIP_EXPRESSION_ENABLED", true);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> serviceTaskDelegateExpressionActivityBehavior.execute(execution));
  }

  /**
   * Test
   * {@link ServiceTaskDelegateExpressionActivityBehavior#execute(DelegateExecution)}.
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ServiceTaskDelegateExpressionActivityBehavior#execute(DelegateExecution)}
   */
  @Test
  public void testExecute_givenNull() {
    // Arrange
    FixedValue expression = new FixedValue(JSONObject.NULL);
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);
    ServiceTaskDelegateExpressionActivityBehavior serviceTaskDelegateExpressionActivityBehavior = new ServiceTaskDelegateExpressionActivityBehavior(
        "42", expression, skipExpression, new ArrayList<>());
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setTransientVariableLocal("_ACTIVITI_SKIP_EXPRESSION_ENABLED", JSONObject.NULL);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> serviceTaskDelegateExpressionActivityBehavior.execute(execution));
  }

  /**
   * Test
   * {@link ServiceTaskDelegateExpressionActivityBehavior#execute(DelegateExecution)}.
   * <ul>
   *   <li>Given {@code true}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ServiceTaskDelegateExpressionActivityBehavior#execute(DelegateExecution)}
   */
  @Test
  public void testExecute_givenTrue_thenThrowActivitiException() {
    // Arrange
    FixedValue expression = new FixedValue(JSONObject.NULL);
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);
    ServiceTaskDelegateExpressionActivityBehavior serviceTaskDelegateExpressionActivityBehavior = new ServiceTaskDelegateExpressionActivityBehavior(
        "42", expression, skipExpression, new ArrayList<>());
    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setTransientVariableLocal("_ACTIVITI_SKIP_EXPRESSION_ENABLED", true);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> serviceTaskDelegateExpressionActivityBehavior.execute(execution));
  }

  /**
   * Test
   * {@link ServiceTaskDelegateExpressionActivityBehavior#execute(DelegateExecution)}.
   * <ul>
   *   <li>When {@link ExecutionEntityImpl} (default constructor).</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ServiceTaskDelegateExpressionActivityBehavior#execute(DelegateExecution)}
   */
  @Test
  public void testExecute_whenExecutionEntityImpl_thenThrowActivitiException() {
    // Arrange
    FixedValue expression = new FixedValue(JSONObject.NULL);
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);
    ServiceTaskDelegateExpressionActivityBehavior serviceTaskDelegateExpressionActivityBehavior = new ServiceTaskDelegateExpressionActivityBehavior(
        "42", expression, skipExpression, new ArrayList<>());

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> serviceTaskDelegateExpressionActivityBehavior.execute(new ExecutionEntityImpl()));
  }
}
