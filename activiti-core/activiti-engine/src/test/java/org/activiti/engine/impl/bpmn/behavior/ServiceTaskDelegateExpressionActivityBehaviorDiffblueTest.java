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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.bpmn.parser.FieldDeclaration;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class ServiceTaskDelegateExpressionActivityBehaviorDiffblueTest {
  /**
   * Test {@link
   * ServiceTaskDelegateExpressionActivityBehavior#ServiceTaskDelegateExpressionActivityBehavior(String,
   * Expression, Expression, List)}.
   *
   * <ul>
   *   <li>Given {@link FieldDeclaration#FieldDeclaration()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ServiceTaskDelegateExpressionActivityBehavior#ServiceTaskDelegateExpressionActivityBehavior(String,
   * Expression, Expression, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ServiceTaskDelegateExpressionActivityBehavior.<init>(String, Expression, Expression, List)"
  })
  public void testNewServiceTaskDelegateExpressionActivityBehavior_givenFieldDeclaration() {
    // Arrange
    FixedValue expression = new FixedValue(JSONObject.NULL);
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(new FieldDeclaration());

    // Act
    ServiceTaskDelegateExpressionActivityBehavior
        actualServiceTaskDelegateExpressionActivityBehavior =
            new ServiceTaskDelegateExpressionActivityBehavior(
                "42", expression, skipExpression, fieldDeclarations);

    // Assert
    Expression expression2 = actualServiceTaskDelegateExpressionActivityBehavior.expression;
    assertTrue(expression2 instanceof FixedValue);
    Expression expression3 = actualServiceTaskDelegateExpressionActivityBehavior.skipExpression;
    assertTrue(expression3 instanceof FixedValue);
    assertEquals("42", actualServiceTaskDelegateExpressionActivityBehavior.serviceTaskId);
    assertEquals("null", expression2.getExpressionText());
    assertEquals("null", expression3.getExpressionText());
    assertNull(
        actualServiceTaskDelegateExpressionActivityBehavior.getMultiInstanceActivityBehavior());
    assertFalse(actualServiceTaskDelegateExpressionActivityBehavior.hasLoopCharacteristics());
    assertFalse(
        actualServiceTaskDelegateExpressionActivityBehavior.hasMultiInstanceCharacteristics());
  }

  /**
   * Test {@link
   * ServiceTaskDelegateExpressionActivityBehavior#ServiceTaskDelegateExpressionActivityBehavior(String,
   * Expression, Expression, List)}.
   *
   * <ul>
   *   <li>Given {@link FieldDeclaration#FieldDeclaration()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ServiceTaskDelegateExpressionActivityBehavior#ServiceTaskDelegateExpressionActivityBehavior(String,
   * Expression, Expression, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ServiceTaskDelegateExpressionActivityBehavior.<init>(String, Expression, Expression, List)"
  })
  public void testNewServiceTaskDelegateExpressionActivityBehavior_givenFieldDeclaration2() {
    // Arrange
    FixedValue expression = new FixedValue(JSONObject.NULL);
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);

    ArrayList<FieldDeclaration> fieldDeclarations = new ArrayList<>();
    fieldDeclarations.add(new FieldDeclaration());
    fieldDeclarations.add(new FieldDeclaration());

    // Act
    ServiceTaskDelegateExpressionActivityBehavior
        actualServiceTaskDelegateExpressionActivityBehavior =
            new ServiceTaskDelegateExpressionActivityBehavior(
                "42", expression, skipExpression, fieldDeclarations);

    // Assert
    Expression expression2 = actualServiceTaskDelegateExpressionActivityBehavior.expression;
    assertTrue(expression2 instanceof FixedValue);
    Expression expression3 = actualServiceTaskDelegateExpressionActivityBehavior.skipExpression;
    assertTrue(expression3 instanceof FixedValue);
    assertEquals("42", actualServiceTaskDelegateExpressionActivityBehavior.serviceTaskId);
    assertEquals("null", expression2.getExpressionText());
    assertEquals("null", expression3.getExpressionText());
    assertNull(
        actualServiceTaskDelegateExpressionActivityBehavior.getMultiInstanceActivityBehavior());
    assertFalse(actualServiceTaskDelegateExpressionActivityBehavior.hasLoopCharacteristics());
    assertFalse(
        actualServiceTaskDelegateExpressionActivityBehavior.hasMultiInstanceCharacteristics());
  }

  /**
   * Test {@link
   * ServiceTaskDelegateExpressionActivityBehavior#ServiceTaskDelegateExpressionActivityBehavior(String,
   * Expression, Expression, List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ServiceTaskDelegateExpressionActivityBehavior#ServiceTaskDelegateExpressionActivityBehavior(String,
   * Expression, Expression, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ServiceTaskDelegateExpressionActivityBehavior.<init>(String, Expression, Expression, List)"
  })
  public void testNewServiceTaskDelegateExpressionActivityBehavior_whenArrayList() {
    // Arrange
    FixedValue expression = new FixedValue(JSONObject.NULL);
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);

    // Act
    ServiceTaskDelegateExpressionActivityBehavior
        actualServiceTaskDelegateExpressionActivityBehavior =
            new ServiceTaskDelegateExpressionActivityBehavior(
                "42", expression, skipExpression, new ArrayList<>());

    // Assert
    Expression expression2 = actualServiceTaskDelegateExpressionActivityBehavior.expression;
    assertTrue(expression2 instanceof FixedValue);
    Expression expression3 = actualServiceTaskDelegateExpressionActivityBehavior.skipExpression;
    assertTrue(expression3 instanceof FixedValue);
    assertEquals("42", actualServiceTaskDelegateExpressionActivityBehavior.serviceTaskId);
    assertEquals("null", expression2.getExpressionText());
    assertEquals("null", expression3.getExpressionText());
    assertNull(
        actualServiceTaskDelegateExpressionActivityBehavior.getMultiInstanceActivityBehavior());
    assertFalse(actualServiceTaskDelegateExpressionActivityBehavior.hasLoopCharacteristics());
    assertFalse(
        actualServiceTaskDelegateExpressionActivityBehavior.hasMultiInstanceCharacteristics());
  }

  /**
   * Test {@link ServiceTaskDelegateExpressionActivityBehavior#trigger(DelegateExecution, String,
   * Object)}.
   *
   * <p>Method under test: {@link
   * ServiceTaskDelegateExpressionActivityBehavior#trigger(DelegateExecution, String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ServiceTaskDelegateExpressionActivityBehavior.trigger(DelegateExecution, String, Object)"
  })
  public void testTrigger() {
    // Arrange
    FixedValue expression = new FixedValue(JSONObject.NULL);
    ServiceTaskDelegateExpressionActivityBehavior serviceTaskDelegateExpressionActivityBehavior =
        new ServiceTaskDelegateExpressionActivityBehavior(
            "42", expression, new FixedValue(JSONObject.NULL), null);

    // Act and Assert
    serviceTaskDelegateExpressionActivityBehavior.trigger(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections(),
        "Signal Name",
        JSONObject.NULL);
  }

  /**
   * Test {@link ServiceTaskDelegateExpressionActivityBehavior#trigger(DelegateExecution, String,
   * Object)}.
   *
   * <ul>
   *   <li>Given {@link AbstractBpmnActivityBehavior} {@link
   *       AbstractBpmnActivityBehavior#trigger(DelegateExecution, String, Object)} does nothing.
   *   <li>Then calls {@link AbstractBpmnActivityBehavior#trigger(DelegateExecution, String,
   *       Object)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ServiceTaskDelegateExpressionActivityBehavior#trigger(DelegateExecution, String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ServiceTaskDelegateExpressionActivityBehavior.trigger(DelegateExecution, String, Object)"
  })
  public void testTrigger_givenAbstractBpmnActivityBehaviorTriggerDoesNothing_thenCallsTrigger() {
    // Arrange
    AbstractBpmnActivityBehavior abstractBpmnActivityBehavior =
        mock(AbstractBpmnActivityBehavior.class);
    doNothing()
        .when(abstractBpmnActivityBehavior)
        .trigger(Mockito.<DelegateExecution>any(), Mockito.<String>any(), Mockito.<Object>any());
    FixedValue expression = new FixedValue(abstractBpmnActivityBehavior);
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);

    ServiceTaskDelegateExpressionActivityBehavior serviceTaskDelegateExpressionActivityBehavior =
        new ServiceTaskDelegateExpressionActivityBehavior(
            "42", expression, skipExpression, new ArrayList<>());

    // Act
    serviceTaskDelegateExpressionActivityBehavior.trigger(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections(),
        "Signal Name",
        JSONObject.NULL);

    // Assert
    verify(abstractBpmnActivityBehavior)
        .trigger(isA(DelegateExecution.class), eq("Signal Name"), isA(Object.class));
  }

  /**
   * Test {@link ServiceTaskDelegateExpressionActivityBehavior#trigger(DelegateExecution, String,
   * Object)}.
   *
   * <ul>
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link
   * ServiceTaskDelegateExpressionActivityBehavior#trigger(DelegateExecution, String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ServiceTaskDelegateExpressionActivityBehavior.trigger(DelegateExecution, String, Object)"
  })
  public void testTrigger_thenDoesNotThrow() {
    // Arrange
    FixedValue expression = new FixedValue(JSONObject.NULL);
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);

    ServiceTaskDelegateExpressionActivityBehavior serviceTaskDelegateExpressionActivityBehavior =
        new ServiceTaskDelegateExpressionActivityBehavior(
            "42", expression, skipExpression, new ArrayList<>());

    // Act and Assert
    serviceTaskDelegateExpressionActivityBehavior.trigger(
        ExecutionEntityImpl.createWithEmptyRelationshipCollections(),
        "Signal Name",
        JSONObject.NULL);
  }

  /**
   * Test {@link ServiceTaskDelegateExpressionActivityBehavior#trigger(DelegateExecution, String,
   * Object)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ServiceTaskDelegateExpressionActivityBehavior#trigger(DelegateExecution, String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ServiceTaskDelegateExpressionActivityBehavior.trigger(DelegateExecution, String, Object)"
  })
  public void testTrigger_thenThrowActivitiException() {
    // Arrange
    FixedValue expression = new FixedValue(new AbstractBpmnActivityBehavior());
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);

    ServiceTaskDelegateExpressionActivityBehavior serviceTaskDelegateExpressionActivityBehavior =
        new ServiceTaskDelegateExpressionActivityBehavior(
            "42", expression, skipExpression, new ArrayList<>());

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            serviceTaskDelegateExpressionActivityBehavior.trigger(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections(),
                "Signal Name",
                JSONObject.NULL));
  }

  /**
   * Test {@link ServiceTaskDelegateExpressionActivityBehavior#execute(DelegateExecution)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ServiceTaskDelegateExpressionActivityBehavior#execute(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ServiceTaskDelegateExpressionActivityBehavior.execute(DelegateExecution)"
  })
  public void testExecute_given42() {
    // Arrange
    FixedValue expression = new FixedValue(JSONObject.NULL);
    FixedValue skipExpression = new FixedValue(true);

    ServiceTaskDelegateExpressionActivityBehavior serviceTaskDelegateExpressionActivityBehavior =
        new ServiceTaskDelegateExpressionActivityBehavior(
            "42", expression, skipExpression, new ArrayList<>());

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setProcessDefinitionId("42");
    execution.setTransientVariableLocal("_ACTIVITI_SKIP_EXPRESSION_ENABLED", true);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> serviceTaskDelegateExpressionActivityBehavior.execute(execution));
  }

  /**
   * Test {@link ServiceTaskDelegateExpressionActivityBehavior#execute(DelegateExecution)}.
   *
   * <ul>
   *   <li>Given {@link FixedValue#FixedValue(Object)} with value is {@code true}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ServiceTaskDelegateExpressionActivityBehavior#execute(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ServiceTaskDelegateExpressionActivityBehavior.execute(DelegateExecution)"
  })
  public void testExecute_givenFixedValueWithValueIsTrue_thenThrowActivitiException() {
    // Arrange
    FixedValue expression = new FixedValue(JSONObject.NULL);
    FixedValue skipExpression = new FixedValue(true);

    ServiceTaskDelegateExpressionActivityBehavior serviceTaskDelegateExpressionActivityBehavior =
        new ServiceTaskDelegateExpressionActivityBehavior(
            "42", expression, skipExpression, new ArrayList<>());

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setTransientVariableLocal("_ACTIVITI_SKIP_EXPRESSION_ENABLED", true);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> serviceTaskDelegateExpressionActivityBehavior.execute(execution));
  }

  /**
   * Test {@link ServiceTaskDelegateExpressionActivityBehavior#execute(DelegateExecution)}.
   *
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ServiceTaskDelegateExpressionActivityBehavior#execute(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ServiceTaskDelegateExpressionActivityBehavior.execute(DelegateExecution)"
  })
  public void testExecute_givenNull() {
    // Arrange
    FixedValue expression = new FixedValue(JSONObject.NULL);
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);

    ServiceTaskDelegateExpressionActivityBehavior serviceTaskDelegateExpressionActivityBehavior =
        new ServiceTaskDelegateExpressionActivityBehavior(
            "42", expression, skipExpression, new ArrayList<>());

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setTransientVariableLocal("_ACTIVITI_SKIP_EXPRESSION_ENABLED", JSONObject.NULL);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> serviceTaskDelegateExpressionActivityBehavior.execute(execution));
  }

  /**
   * Test {@link ServiceTaskDelegateExpressionActivityBehavior#execute(DelegateExecution)}.
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ServiceTaskDelegateExpressionActivityBehavior#execute(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ServiceTaskDelegateExpressionActivityBehavior.execute(DelegateExecution)"
  })
  public void testExecute_givenTrue_thenThrowActivitiException() {
    // Arrange
    FixedValue expression = new FixedValue(JSONObject.NULL);
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);

    ServiceTaskDelegateExpressionActivityBehavior serviceTaskDelegateExpressionActivityBehavior =
        new ServiceTaskDelegateExpressionActivityBehavior(
            "42", expression, skipExpression, new ArrayList<>());

    ExecutionEntityImpl execution = ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    execution.setTransientVariableLocal("_ACTIVITI_SKIP_EXPRESSION_ENABLED", true);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> serviceTaskDelegateExpressionActivityBehavior.execute(execution));
  }

  /**
   * Test {@link ServiceTaskDelegateExpressionActivityBehavior#execute(DelegateExecution)}.
   *
   * <ul>
   *   <li>When {@link ExecutionEntityImpl} (default constructor).
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ServiceTaskDelegateExpressionActivityBehavior#execute(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ServiceTaskDelegateExpressionActivityBehavior.execute(DelegateExecution)"
  })
  public void testExecute_whenExecutionEntityImpl_thenThrowActivitiException() {
    // Arrange
    FixedValue expression = new FixedValue(JSONObject.NULL);
    FixedValue skipExpression = new FixedValue(JSONObject.NULL);

    ServiceTaskDelegateExpressionActivityBehavior serviceTaskDelegateExpressionActivityBehavior =
        new ServiceTaskDelegateExpressionActivityBehavior(
            "42", expression, skipExpression, new ArrayList<>());

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> serviceTaskDelegateExpressionActivityBehavior.execute(new ExecutionEntityImpl()));
  }
}
