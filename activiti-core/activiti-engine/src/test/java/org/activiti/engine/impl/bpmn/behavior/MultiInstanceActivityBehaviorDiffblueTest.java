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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.model.Activity;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BusinessRuleTask;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultMessageExecutionContext;
import org.activiti.engine.impl.delegate.MessagePayloadMappingProvider;
import org.activiti.engine.impl.el.ExpressionManager;
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
public class MultiInstanceActivityBehaviorDiffblueTest {
  @Mock
  private AbstractBpmnActivityBehavior abstractBpmnActivityBehavior;

  @Mock
  private Activity activity;

  @InjectMocks
  private ParallelMultiInstanceBehavior parallelMultiInstanceBehavior;

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#execute(DelegateExecution)}
   */
  @Test
  public void testExecute() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> parallelMultiInstanceBehavior.execute(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#execute(DelegateExecution)}
   */
  @Test
  public void testExecute2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopCardinalityExpression(new FixedValue(JSONObject.NULL));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> parallelMultiInstanceBehavior.execute(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#execute(DelegateExecution)}
   */
  @Test
  public void testExecute3() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setCollectionExpression(new FixedValue(JSONObject.NULL));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> parallelMultiInstanceBehavior.execute(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#execute(DelegateExecution)}
   */
  @Test
  public void testExecute4() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior
        .setCollectionVariable("Couldn't resolve collection expression nor variable reference");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> parallelMultiInstanceBehavior.execute(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#trigger(DelegateExecution, String, Object)}
   */
  @Test
  public void testTrigger() {
    // Arrange
    doNothing().when(abstractBpmnActivityBehavior)
        .trigger(Mockito.<DelegateExecution>any(), Mockito.<String>any(), Mockito.<Object>any());

    // Act
    parallelMultiInstanceBehavior.trigger(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Signal Name",
        JSONObject.NULL);

    // Assert that nothing has changed
    verify(abstractBpmnActivityBehavior).trigger(isA(DelegateExecution.class), eq("Signal Name"), isA(Object.class));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#lastExecutionEnded(DelegateExecution)}
   */
  @Test
  public void testLastExecutionEnded() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> parallelMultiInstanceBehavior
        .lastExecutionEnded(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#completed(DelegateExecution)}
   */
  @Test
  public void testCompleted() throws Exception {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> parallelMultiInstanceBehavior.completed(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#resolveNrOfInstances(DelegateExecution)}
   */
  @Test
  public void testResolveNrOfInstances() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> parallelMultiInstanceBehavior
        .resolveNrOfInstances(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#resolveNrOfInstances(DelegateExecution)}
   */
  @Test
  public void testResolveNrOfInstances2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopCardinalityExpression(null);
    parallelMultiInstanceBehavior.setCollectionExpression(null);
    parallelMultiInstanceBehavior.setCollectionVariable("foo");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> parallelMultiInstanceBehavior
        .resolveNrOfInstances(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#resolveNrOfInstances(DelegateExecution)}
   */
  @Test
  public void testResolveNrOfInstances3() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopCardinalityExpression(null);
    parallelMultiInstanceBehavior.setCollectionExpression(new FixedValue(JSONObject.NULL));
    parallelMultiInstanceBehavior.setCollectionVariable(null);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> parallelMultiInstanceBehavior
        .resolveNrOfInstances(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#resolveNrOfInstances(DelegateExecution)}
   */
  @Test
  public void testResolveNrOfInstances4() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopCardinalityExpression(new FixedValue(JSONObject.NULL));
    parallelMultiInstanceBehavior.setCollectionExpression(null);
    parallelMultiInstanceBehavior.setCollectionVariable(null);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> parallelMultiInstanceBehavior
        .resolveNrOfInstances(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#resolveNrOfInstances(DelegateExecution)}
   */
  @Test
  public void testResolveNrOfInstances5() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopCardinalityExpression(null);
    parallelMultiInstanceBehavior.setCollectionExpression(new FixedValue(new ArrayList<>()));
    parallelMultiInstanceBehavior.setCollectionVariable(null);

    // Act and Assert
    assertEquals(0, parallelMultiInstanceBehavior
        .resolveNrOfInstances(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#resolveNrOfInstances(DelegateExecution)}
   */
  @Test
  public void testResolveNrOfInstances6() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopCardinalityExpression(new FixedValue(42));
    parallelMultiInstanceBehavior.setCollectionExpression(null);
    parallelMultiInstanceBehavior.setCollectionVariable(null);

    // Act and Assert
    assertEquals(42, parallelMultiInstanceBehavior
        .resolveNrOfInstances(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#resolveNrOfInstances(DelegateExecution)}
   */
  @Test
  public void testResolveNrOfInstances7() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopCardinalityExpression(new FixedValue("42"));
    parallelMultiInstanceBehavior.setCollectionExpression(null);
    parallelMultiInstanceBehavior.setCollectionVariable(null);

    // Act and Assert
    assertEquals(42, parallelMultiInstanceBehavior
        .resolveNrOfInstances(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#resolveAndValidateCollection(DelegateExecution)}
   */
  @Test
  public void testResolveAndValidateCollection() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> parallelMultiInstanceBehavior
        .resolveAndValidateCollection(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#resolveAndValidateCollection(DelegateExecution)}
   */
  @Test
  public void testResolveAndValidateCollection2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setCollectionExpression(null);
    parallelMultiInstanceBehavior.setCollectionVariable("foo");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> parallelMultiInstanceBehavior
        .resolveAndValidateCollection(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#resolveAndValidateCollection(DelegateExecution)}
   */
  @Test
  public void testResolveAndValidateCollection3() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setCollectionExpression(new FixedValue(JSONObject.NULL));
    parallelMultiInstanceBehavior.setCollectionVariable(null);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> parallelMultiInstanceBehavior
        .resolveAndValidateCollection(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#resolveAndValidateCollection(DelegateExecution)}
   */
  @Test
  public void testResolveAndValidateCollection4() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    ArrayList<Object> objectList = new ArrayList<>();
    parallelMultiInstanceBehavior.setCollectionExpression(new FixedValue(objectList));
    parallelMultiInstanceBehavior.setCollectionVariable(null);

    // Act
    Collection actualResolveAndValidateCollectionResult = parallelMultiInstanceBehavior
        .resolveAndValidateCollection(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    assertTrue(actualResolveAndValidateCollectionResult instanceof List);
    assertTrue(actualResolveAndValidateCollectionResult.isEmpty());
    assertSame(objectList, actualResolveAndValidateCollectionResult);
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#resolveCollection(DelegateExecution)}
   */
  @Test
  public void testResolveCollection() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertNull(
        parallelMultiInstanceBehavior.resolveCollection(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#resolveCollection(DelegateExecution)}
   */
  @Test
  public void testResolveCollection2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setCollectionExpression(null);
    parallelMultiInstanceBehavior.setCollectionVariable("foo");

    // Act and Assert
    assertNull(
        parallelMultiInstanceBehavior.resolveCollection(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#resolveCollection(DelegateExecution)}
   */
  @Test
  public void testResolveCollection3() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new EventSubProcessMessageStartEventActivityBehavior(messageEventDefinition, new DefaultMessageExecutionContext(
            messageEventDefinition2, new ExpressionManager(), mock(MessagePayloadMappingProvider.class))));

    // Act and Assert
    assertNull(
        parallelMultiInstanceBehavior.resolveCollection(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test: {@link MultiInstanceActivityBehavior#usesCollection()}
   */
  @Test
  public void testUsesCollection() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    // Act and Assert
    assertFalse((new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior())).usesCollection());
  }

  /**
   * Method under test: {@link MultiInstanceActivityBehavior#usesCollection()}
   */
  @Test
  public void testUsesCollection2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setCollectionExpression(null);
    parallelMultiInstanceBehavior.setCollectionVariable("foo");

    // Act and Assert
    assertTrue(parallelMultiInstanceBehavior.usesCollection());
  }

  /**
   * Method under test: {@link MultiInstanceActivityBehavior#usesCollection()}
   */
  @Test
  public void testUsesCollection3() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setCollectionExpression(new FixedValue(JSONObject.NULL));
    parallelMultiInstanceBehavior.setCollectionVariable(null);

    // Act and Assert
    assertTrue(parallelMultiInstanceBehavior.usesCollection());
  }

  /**
   * Method under test: {@link MultiInstanceActivityBehavior#usesCollection()}
   */
  @Test
  public void testUsesCollection4() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();

    // Act and Assert
    assertFalse((new ParallelMultiInstanceBehavior(activity,
        new EventSubProcessMessageStartEventActivityBehavior(messageEventDefinition, new DefaultMessageExecutionContext(
            messageEventDefinition2, new ExpressionManager(), mock(MessagePayloadMappingProvider.class)))))
        .usesCollection());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#isExtraScopeNeeded(FlowNode)}
   */
  @Test
  public void testIsExtraScopeNeeded() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertFalse(parallelMultiInstanceBehavior.isExtraScopeNeeded(new AdhocSubProcess()));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#isExtraScopeNeeded(FlowNode)}
   */
  @Test
  public void testIsExtraScopeNeeded2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());

    AdhocSubProcess flowNode = new AdhocSubProcess();
    flowNode.setParentContainer(new AdhocSubProcess());

    // Act and Assert
    assertTrue(parallelMultiInstanceBehavior.isExtraScopeNeeded(flowNode));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#resolveLoopCardinality(DelegateExecution)}
   */
  @Test
  public void testResolveLoopCardinality() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopCardinalityExpression(new FixedValue(JSONObject.NULL));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> parallelMultiInstanceBehavior
        .resolveLoopCardinality(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#resolveLoopCardinality(DelegateExecution)}
   */
  @Test
  public void testResolveLoopCardinality2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopCardinalityExpression(new FixedValue(42));

    // Act and Assert
    assertEquals(42, parallelMultiInstanceBehavior
        .resolveLoopCardinality(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#resolveLoopCardinality(DelegateExecution)}
   */
  @Test
  public void testResolveLoopCardinality3() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopCardinalityExpression(new FixedValue("42"));

    // Act and Assert
    assertEquals(42, parallelMultiInstanceBehavior
        .resolveLoopCardinality(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#completionConditionSatisfied(DelegateExecution)}
   */
  @Test
  public void testCompletionConditionSatisfied() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertFalse(parallelMultiInstanceBehavior
        .completionConditionSatisfied(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#completionConditionSatisfied(DelegateExecution)}
   */
  @Test
  public void testCompletionConditionSatisfied2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setCompletionConditionExpression(new FixedValue(JSONObject.NULL));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> parallelMultiInstanceBehavior
        .completionConditionSatisfied(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#completionConditionSatisfied(DelegateExecution)}
   */
  @Test
  public void testCompletionConditionSatisfied3() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new EventSubProcessMessageStartEventActivityBehavior(messageEventDefinition, new DefaultMessageExecutionContext(
            messageEventDefinition2, new ExpressionManager(), mock(MessagePayloadMappingProvider.class))));

    // Act and Assert
    assertFalse(parallelMultiInstanceBehavior
        .completionConditionSatisfied(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#completionConditionSatisfied(DelegateExecution)}
   */
  @Test
  public void testCompletionConditionSatisfied4() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setCompletionConditionExpression(new FixedValue(true));

    // Act and Assert
    assertTrue(parallelMultiInstanceBehavior
        .completionConditionSatisfied(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#setLoopVariable(DelegateExecution, String, Object)}
   */
  @Test
  public void testSetLoopVariable() {
    // Arrange
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.setVariableLocal(Mockito.<String>any(), Mockito.<Object>any())).thenReturn(JSONObject.NULL);

    // Act
    parallelMultiInstanceBehavior.setLoopVariable(execution, "Variable Name", JSONObject.NULL);

    // Assert
    verify(execution).setVariableLocal(eq("Variable Name"), isA(Object.class));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#getLoopVariable(DelegateExecution, String)}
   */
  @Test
  public void testGetLoopVariable() {
    // Arrange, Act and Assert
    assertEquals(0,
        parallelMultiInstanceBehavior
            .getLoopVariable(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Variable Name")
            .intValue());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#getLoopVariable(DelegateExecution, String)}
   */
  @Test
  public void testGetLoopVariable2() {
    // Arrange
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getVariableLocal(Mockito.<String>any())).thenReturn(1);
    when(execution.getParent()).thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    Integer actualLoopVariable = parallelMultiInstanceBehavior.getLoopVariable(execution, "Variable Name");

    // Assert
    verify(execution).getParent();
    verify(execution).getVariableLocal(eq("Variable Name"));
    assertEquals(1, actualLoopVariable.intValue());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#getLoopVariable(DelegateExecution, String)}
   */
  @Test
  public void testGetLoopVariable3() {
    // Arrange
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getVariableLocal(Mockito.<String>any())).thenReturn(null);
    when(execution.getParent()).thenReturn(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Act
    Integer actualLoopVariable = parallelMultiInstanceBehavior.getLoopVariable(execution, "Variable Name");

    // Assert
    verify(execution).getParent();
    verify(execution).getVariableLocal(eq("Variable Name"));
    assertEquals(0, actualLoopVariable.intValue());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#getLocalLoopVariable(DelegateExecution, String)}
   */
  @Test
  public void testGetLocalLoopVariable() {
    // Arrange, Act and Assert
    assertNull(parallelMultiInstanceBehavior
        .getLocalLoopVariable(ExecutionEntityImpl.createWithEmptyRelationshipCollections(), "Variable Name"));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#getLocalLoopVariable(DelegateExecution, String)}
   */
  @Test
  public void testGetLocalLoopVariable2() {
    // Arrange
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getVariableLocal(Mockito.<String>any())).thenReturn(1);

    // Act
    Integer actualLocalLoopVariable = parallelMultiInstanceBehavior.getLocalLoopVariable(execution, "Variable Name");

    // Assert
    verify(execution).getVariableLocal(eq("Variable Name"));
    assertEquals(1, actualLocalLoopVariable.intValue());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#removeLocalLoopVariable(DelegateExecution, String)}
   */
  @Test
  public void testRemoveLocalLoopVariable() {
    // Arrange
    DelegateExecution execution = mock(DelegateExecution.class);
    doNothing().when(execution).removeVariableLocal(Mockito.<String>any());

    // Act
    parallelMultiInstanceBehavior.removeLocalLoopVariable(execution, "Variable Name");

    // Assert
    verify(execution).removeVariableLocal(eq("Variable Name"));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#getMultiInstanceRootExecution(DelegateExecution)}
   */
  @Test
  public void testGetMultiInstanceRootExecution() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertNull(parallelMultiInstanceBehavior
        .getMultiInstanceRootExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#getMultiInstanceRootExecution(DelegateExecution)}
   */
  @Test
  public void testGetMultiInstanceRootExecution2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    // Act and Assert
    assertNull((new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior()))
        .getMultiInstanceRootExecution(null));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#getMultiInstanceRootExecution(DelegateExecution)}
   */
  @Test
  public void testGetMultiInstanceRootExecution3() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new EventSubProcessMessageStartEventActivityBehavior(messageEventDefinition, new DefaultMessageExecutionContext(
            messageEventDefinition2, new ExpressionManager(), mock(MessagePayloadMappingProvider.class))));

    // Act and Assert
    assertNull(parallelMultiInstanceBehavior
        .getMultiInstanceRootExecution(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#getLoopCardinalityExpression()}
   */
  @Test
  public void testGetLoopCardinalityExpression() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    // Act and Assert
    assertNull((new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior()))
        .getLoopCardinalityExpression());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#getLoopCardinalityExpression()}
   */
  @Test
  public void testGetLoopCardinalityExpression2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();

    // Act and Assert
    assertNull((new ParallelMultiInstanceBehavior(activity,
        new EventSubProcessMessageStartEventActivityBehavior(messageEventDefinition, new DefaultMessageExecutionContext(
            messageEventDefinition2, new ExpressionManager(), mock(MessagePayloadMappingProvider.class)))))
        .getLoopCardinalityExpression());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#setLoopCardinalityExpression(Expression)}
   */
  @Test
  public void testSetLoopCardinalityExpression() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    FixedValue loopCardinalityExpression = new FixedValue(JSONObject.NULL);

    // Act
    parallelMultiInstanceBehavior.setLoopCardinalityExpression(loopCardinalityExpression);

    // Assert
    assertSame(loopCardinalityExpression, parallelMultiInstanceBehavior.getLoopCardinalityExpression());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#setLoopCardinalityExpression(Expression)}
   */
  @Test
  public void testSetLoopCardinalityExpression2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new EventSubProcessMessageStartEventActivityBehavior(messageEventDefinition, new DefaultMessageExecutionContext(
            messageEventDefinition2, new ExpressionManager(), mock(MessagePayloadMappingProvider.class))));
    FixedValue loopCardinalityExpression = new FixedValue(JSONObject.NULL);

    // Act
    parallelMultiInstanceBehavior.setLoopCardinalityExpression(loopCardinalityExpression);

    // Assert
    assertSame(loopCardinalityExpression, parallelMultiInstanceBehavior.getLoopCardinalityExpression());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#getCompletionConditionExpression()}
   */
  @Test
  public void testGetCompletionConditionExpression() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    // Act and Assert
    assertNull((new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior()))
        .getCompletionConditionExpression());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#getCompletionConditionExpression()}
   */
  @Test
  public void testGetCompletionConditionExpression2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();

    // Act and Assert
    assertNull((new ParallelMultiInstanceBehavior(activity,
        new EventSubProcessMessageStartEventActivityBehavior(messageEventDefinition, new DefaultMessageExecutionContext(
            messageEventDefinition2, new ExpressionManager(), mock(MessagePayloadMappingProvider.class)))))
        .getCompletionConditionExpression());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#setCompletionConditionExpression(Expression)}
   */
  @Test
  public void testSetCompletionConditionExpression() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    FixedValue completionConditionExpression = new FixedValue(JSONObject.NULL);

    // Act
    parallelMultiInstanceBehavior.setCompletionConditionExpression(completionConditionExpression);

    // Assert
    assertSame(completionConditionExpression, parallelMultiInstanceBehavior.getCompletionConditionExpression());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#setCompletionConditionExpression(Expression)}
   */
  @Test
  public void testSetCompletionConditionExpression2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new EventSubProcessMessageStartEventActivityBehavior(messageEventDefinition, new DefaultMessageExecutionContext(
            messageEventDefinition2, new ExpressionManager(), mock(MessagePayloadMappingProvider.class))));
    FixedValue completionConditionExpression = new FixedValue(JSONObject.NULL);

    // Act
    parallelMultiInstanceBehavior.setCompletionConditionExpression(completionConditionExpression);

    // Assert
    assertSame(completionConditionExpression, parallelMultiInstanceBehavior.getCompletionConditionExpression());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#getCollectionExpression()}
   */
  @Test
  public void testGetCollectionExpression() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    // Act and Assert
    assertNull(
        (new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior())).getCollectionExpression());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#getCollectionExpression()}
   */
  @Test
  public void testGetCollectionExpression2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();

    // Act and Assert
    assertNull((new ParallelMultiInstanceBehavior(activity,
        new EventSubProcessMessageStartEventActivityBehavior(messageEventDefinition, new DefaultMessageExecutionContext(
            messageEventDefinition2, new ExpressionManager(), mock(MessagePayloadMappingProvider.class)))))
        .getCollectionExpression());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#setCollectionExpression(Expression)}
   */
  @Test
  public void testSetCollectionExpression() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    FixedValue collectionExpression = new FixedValue(JSONObject.NULL);

    // Act
    parallelMultiInstanceBehavior.setCollectionExpression(collectionExpression);

    // Assert
    assertSame(collectionExpression, parallelMultiInstanceBehavior.getCollectionExpression());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#setCollectionExpression(Expression)}
   */
  @Test
  public void testSetCollectionExpression2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new EventSubProcessMessageStartEventActivityBehavior(messageEventDefinition, new DefaultMessageExecutionContext(
            messageEventDefinition2, new ExpressionManager(), mock(MessagePayloadMappingProvider.class))));
    FixedValue collectionExpression = new FixedValue(JSONObject.NULL);

    // Act
    parallelMultiInstanceBehavior.setCollectionExpression(collectionExpression);

    // Assert
    assertSame(collectionExpression, parallelMultiInstanceBehavior.getCollectionExpression());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#getCollectionVariable()}
   */
  @Test
  public void testGetCollectionVariable() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    // Act and Assert
    assertNull(
        (new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior())).getCollectionVariable());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#getCollectionVariable()}
   */
  @Test
  public void testGetCollectionVariable2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();

    // Act and Assert
    assertNull((new ParallelMultiInstanceBehavior(activity,
        new EventSubProcessMessageStartEventActivityBehavior(messageEventDefinition, new DefaultMessageExecutionContext(
            messageEventDefinition2, new ExpressionManager(), mock(MessagePayloadMappingProvider.class)))))
        .getCollectionVariable());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#setCollectionVariable(String)}
   */
  @Test
  public void testSetCollectionVariable() {
    // Arrange and Act
    parallelMultiInstanceBehavior.setCollectionVariable("Collection Variable");

    // Assert
    assertEquals("Collection Variable", parallelMultiInstanceBehavior.getCollectionVariable());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#getCollectionElementVariable()}
   */
  @Test
  public void testGetCollectionElementVariable() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    // Act and Assert
    assertNull((new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior()))
        .getCollectionElementVariable());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#getCollectionElementVariable()}
   */
  @Test
  public void testGetCollectionElementVariable2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();

    // Act and Assert
    assertNull((new ParallelMultiInstanceBehavior(activity,
        new EventSubProcessMessageStartEventActivityBehavior(messageEventDefinition, new DefaultMessageExecutionContext(
            messageEventDefinition2, new ExpressionManager(), mock(MessagePayloadMappingProvider.class)))))
        .getCollectionElementVariable());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#setCollectionElementVariable(String)}
   */
  @Test
  public void testSetCollectionElementVariable() {
    // Arrange and Act
    parallelMultiInstanceBehavior.setCollectionElementVariable("Collection Element Variable");

    // Assert
    assertEquals("Collection Element Variable", parallelMultiInstanceBehavior.getCollectionElementVariable());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#getCollectionElementIndexVariable()}
   */
  @Test
  public void testGetCollectionElementIndexVariable() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    // Act and Assert
    assertEquals("loopCounter", (new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior()))
        .getCollectionElementIndexVariable());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#getCollectionElementIndexVariable()}
   */
  @Test
  public void testGetCollectionElementIndexVariable2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();

    // Act and Assert
    assertEquals("loopCounter", (new ParallelMultiInstanceBehavior(activity,
        new EventSubProcessMessageStartEventActivityBehavior(messageEventDefinition, new DefaultMessageExecutionContext(
            messageEventDefinition2, new ExpressionManager(), mock(MessagePayloadMappingProvider.class)))))
        .getCollectionElementIndexVariable());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#setCollectionElementIndexVariable(String)}
   */
  @Test
  public void testSetCollectionElementIndexVariable() {
    // Arrange and Act
    parallelMultiInstanceBehavior.setCollectionElementIndexVariable("Collection Element Index Variable");

    // Assert
    assertEquals("Collection Element Index Variable",
        parallelMultiInstanceBehavior.getCollectionElementIndexVariable());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#setInnerActivityBehavior(AbstractBpmnActivityBehavior)}
   */
  @Test
  public void testSetInnerActivityBehavior() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    AbstractBpmnActivityBehavior innerActivityBehavior = new AbstractBpmnActivityBehavior();

    // Act
    parallelMultiInstanceBehavior.setInnerActivityBehavior(innerActivityBehavior);

    // Assert
    assertTrue(parallelMultiInstanceBehavior.activity instanceof AdhocSubProcess);
    assertTrue(innerActivityBehavior.hasLoopCharacteristics());
    assertTrue(innerActivityBehavior.hasMultiInstanceCharacteristics());
    assertSame(parallelMultiInstanceBehavior, innerActivityBehavior.getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#setInnerActivityBehavior(AbstractBpmnActivityBehavior)}
   */
  @Test
  public void testSetInnerActivityBehavior2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new EventSubProcessMessageStartEventActivityBehavior(messageEventDefinition, new DefaultMessageExecutionContext(
            messageEventDefinition2, new ExpressionManager(), mock(MessagePayloadMappingProvider.class))));
    AbstractBpmnActivityBehavior innerActivityBehavior = new AbstractBpmnActivityBehavior();

    // Act
    parallelMultiInstanceBehavior.setInnerActivityBehavior(innerActivityBehavior);

    // Assert
    assertTrue(parallelMultiInstanceBehavior.activity instanceof AdhocSubProcess);
    assertTrue(innerActivityBehavior.hasLoopCharacteristics());
    assertTrue(innerActivityBehavior.hasMultiInstanceCharacteristics());
    assertSame(innerActivityBehavior, parallelMultiInstanceBehavior.getInnerActivityBehavior());
    assertSame(parallelMultiInstanceBehavior, innerActivityBehavior.getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#getInnerActivityBehavior()}
   */
  @Test
  public void testGetInnerActivityBehavior() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());

    // Act and Assert
    assertSame(parallelMultiInstanceBehavior.innerActivityBehavior,
        parallelMultiInstanceBehavior.getInnerActivityBehavior());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#getInnerActivityBehavior()}
   */
  @Test
  public void testGetInnerActivityBehavior2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new EventSubProcessMessageStartEventActivityBehavior(messageEventDefinition, new DefaultMessageExecutionContext(
            messageEventDefinition2, new ExpressionManager(), mock(MessagePayloadMappingProvider.class))));

    // Act and Assert
    assertSame(parallelMultiInstanceBehavior.innerActivityBehavior,
        parallelMultiInstanceBehavior.getInnerActivityBehavior());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#getLoopDataOutputRef()}
   */
  @Test
  public void testGetLoopDataOutputRef() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    // Act and Assert
    assertNull(
        (new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior())).getLoopDataOutputRef());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#getLoopDataOutputRef()}
   */
  @Test
  public void testGetLoopDataOutputRef2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();

    // Act and Assert
    assertNull((new ParallelMultiInstanceBehavior(activity,
        new EventSubProcessMessageStartEventActivityBehavior(messageEventDefinition, new DefaultMessageExecutionContext(
            messageEventDefinition2, new ExpressionManager(), mock(MessagePayloadMappingProvider.class)))))
        .getLoopDataOutputRef());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#hasLoopDataOutputRef()}
   */
  @Test
  public void testHasLoopDataOutputRef() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    // Act and Assert
    assertFalse(
        (new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior())).hasLoopDataOutputRef());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#hasLoopDataOutputRef()}
   */
  @Test
  public void testHasLoopDataOutputRef2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopDataOutputRef("foo");

    // Act and Assert
    assertTrue(parallelMultiInstanceBehavior.hasLoopDataOutputRef());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#hasLoopDataOutputRef()}
   */
  @Test
  public void testHasLoopDataOutputRef3() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();

    // Act and Assert
    assertFalse((new ParallelMultiInstanceBehavior(activity,
        new EventSubProcessMessageStartEventActivityBehavior(messageEventDefinition, new DefaultMessageExecutionContext(
            messageEventDefinition2, new ExpressionManager(), mock(MessagePayloadMappingProvider.class)))))
        .hasLoopDataOutputRef());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#hasLoopDataOutputRef()}
   */
  @Test
  public void testHasLoopDataOutputRef4() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopDataOutputRef("");

    // Act and Assert
    assertFalse(parallelMultiInstanceBehavior.hasLoopDataOutputRef());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#setLoopDataOutputRef(String)}
   */
  @Test
  public void testSetLoopDataOutputRef() {
    // Arrange and Act
    parallelMultiInstanceBehavior.setLoopDataOutputRef("Loop Data Output Ref");

    // Assert
    assertEquals("Loop Data Output Ref", parallelMultiInstanceBehavior.getLoopDataOutputRef());
    assertTrue(parallelMultiInstanceBehavior.hasLoopDataOutputRef());
  }

  /**
   * Method under test: {@link MultiInstanceActivityBehavior#getOutputDataItem()}
   */
  @Test
  public void testGetOutputDataItem() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    // Act and Assert
    assertNull((new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior())).getOutputDataItem());
  }

  /**
   * Method under test: {@link MultiInstanceActivityBehavior#getOutputDataItem()}
   */
  @Test
  public void testGetOutputDataItem2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();

    // Act and Assert
    assertNull((new ParallelMultiInstanceBehavior(activity,
        new EventSubProcessMessageStartEventActivityBehavior(messageEventDefinition, new DefaultMessageExecutionContext(
            messageEventDefinition2, new ExpressionManager(), mock(MessagePayloadMappingProvider.class)))))
        .getOutputDataItem());
  }

  /**
   * Method under test: {@link MultiInstanceActivityBehavior#hasOutputDataItem()}
   */
  @Test
  public void testHasOutputDataItem() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    // Act and Assert
    assertFalse((new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior())).hasOutputDataItem());
  }

  /**
   * Method under test: {@link MultiInstanceActivityBehavior#hasOutputDataItem()}
   */
  @Test
  public void testHasOutputDataItem2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setOutputDataItem("foo");

    // Act and Assert
    assertTrue(parallelMultiInstanceBehavior.hasOutputDataItem());
  }

  /**
   * Method under test: {@link MultiInstanceActivityBehavior#hasOutputDataItem()}
   */
  @Test
  public void testHasOutputDataItem3() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();

    // Act and Assert
    assertFalse((new ParallelMultiInstanceBehavior(activity,
        new EventSubProcessMessageStartEventActivityBehavior(messageEventDefinition, new DefaultMessageExecutionContext(
            messageEventDefinition2, new ExpressionManager(), mock(MessagePayloadMappingProvider.class)))))
        .hasOutputDataItem());
  }

  /**
   * Method under test: {@link MultiInstanceActivityBehavior#hasOutputDataItem()}
   */
  @Test
  public void testHasOutputDataItem4() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setOutputDataItem("");

    // Act and Assert
    assertFalse(parallelMultiInstanceBehavior.hasOutputDataItem());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#setOutputDataItem(String)}
   */
  @Test
  public void testSetOutputDataItem() {
    // Arrange and Act
    parallelMultiInstanceBehavior.setOutputDataItem("Output Data Item");

    // Assert
    assertEquals("Output Data Item", parallelMultiInstanceBehavior.getOutputDataItem());
    assertTrue(parallelMultiInstanceBehavior.hasOutputDataItem());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#getResultElementItem(Map)}
   */
  @Test
  public void testGetResultElementItem() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());

    // Act
    Object actualResultElementItem = parallelMultiInstanceBehavior.getResultElementItem(new HashMap<>());

    // Assert
    assertTrue(actualResultElementItem instanceof Map);
    assertTrue(((Map<Object, Object>) actualResultElementItem).isEmpty());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#getResultElementItem(Map)}
   */
  @Test
  public void testGetResultElementItem2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setOutputDataItem("foo");

    // Act and Assert
    assertNull(parallelMultiInstanceBehavior.getResultElementItem(new HashMap<>()));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#getResultElementItem(Map)}
   */
  @Test
  public void testGetResultElementItem3() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new EventSubProcessMessageStartEventActivityBehavior(messageEventDefinition, new DefaultMessageExecutionContext(
            messageEventDefinition2, new ExpressionManager(), mock(MessagePayloadMappingProvider.class))));

    // Act
    Object actualResultElementItem = parallelMultiInstanceBehavior.getResultElementItem(new HashMap<>());

    // Assert
    assertTrue(actualResultElementItem instanceof Map);
    assertTrue(((Map<Object, Object>) actualResultElementItem).isEmpty());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#getResultElementItem(Map)}
   */
  @Test
  public void testGetResultElementItem4() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setOutputDataItem("");

    // Act
    Object actualResultElementItem = parallelMultiInstanceBehavior.getResultElementItem(new HashMap<>());

    // Assert
    assertTrue(actualResultElementItem instanceof Map);
    assertTrue(((Map<Object, Object>) actualResultElementItem).isEmpty());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#getResultElementItem(DelegateExecution)}
   */
  @Test
  public void testGetResultElementItem5() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());

    // Act
    Object actualResultElementItem = parallelMultiInstanceBehavior
        .getResultElementItem(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    assertTrue(actualResultElementItem instanceof Map);
    assertTrue(((Map<Object, Object>) actualResultElementItem).isEmpty());
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#getResultElementItem(DelegateExecution)}
   */
  @Test
  public void testGetResultElementItem6() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setOutputDataItem("foo");

    // Act and Assert
    assertNull(parallelMultiInstanceBehavior
        .getResultElementItem(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link MultiInstanceActivityBehavior#getResultElementItem(DelegateExecution)}
   */
  @Test
  public void testGetResultElementItem7() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();
    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new EventSubProcessMessageStartEventActivityBehavior(messageEventDefinition, new DefaultMessageExecutionContext(
            messageEventDefinition2, new ExpressionManager(), mock(MessagePayloadMappingProvider.class))));

    // Act
    Object actualResultElementItem = parallelMultiInstanceBehavior
        .getResultElementItem(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    assertTrue(actualResultElementItem instanceof Map);
    assertTrue(((Map<Object, Object>) actualResultElementItem).isEmpty());
  }

  /**
   * Method under test: {@link MultiInstanceActivityBehavior#getCommandContext()}
   */
  @Test
  public void testGetCommandContext() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    // Act and Assert
    assertNull((new ParallelMultiInstanceBehavior(activity, new AbstractBpmnActivityBehavior())).getCommandContext());
  }

  /**
   * Method under test: {@link MultiInstanceActivityBehavior#getCommandContext()}
   */
  @Test
  public void testGetCommandContext2() {
    // Arrange
    BusinessRuleTask activity = new BusinessRuleTask();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();

    // Act and Assert
    assertNull((new ParallelMultiInstanceBehavior(activity,
        new EventSubProcessMessageStartEventActivityBehavior(messageEventDefinition, new DefaultMessageExecutionContext(
            messageEventDefinition2, new ExpressionManager(), mock(MessagePayloadMappingProvider.class)))))
        .getCommandContext());
  }
}
