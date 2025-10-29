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
import static org.mockito.Mockito.mock;
import java.util.Collection;
import java.util.List;
import org.activiti.bpmn.model.Activity;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultMessageExecutionContext;
import org.activiti.engine.impl.delegate.MessagePayloadMappingProvider;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;

public class ParallelMultiInstanceBehaviorDiffblueTest {
  /**
   * Method under test:
   * {@link ParallelMultiInstanceBehavior#createInstances(DelegateExecution)}
   */
  @Test
  public void testCreateInstances() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();

    ParallelMultiInstanceBehavior parallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        new AbstractBpmnActivityBehavior());
    parallelMultiInstanceBehavior.setLoopCardinalityExpression(new FixedValue(-1));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> parallelMultiInstanceBehavior
        .createInstances(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link ParallelMultiInstanceBehavior#ParallelMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}
   */
  @Test
  public void testNewParallelMultiInstanceBehavior() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    AbstractBpmnActivityBehavior originalActivityBehavior = new AbstractBpmnActivityBehavior();

    // Act
    ParallelMultiInstanceBehavior actualParallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        originalActivityBehavior);

    // Assert
    Activity activity2 = actualParallelMultiInstanceBehavior.activity;
    Collection<Artifact> artifacts = ((AdhocSubProcess) activity2).getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = ((AdhocSubProcess) activity2).getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(activity2 instanceof AdhocSubProcess);
    assertEquals("Parallel", ((AdhocSubProcess) activity2).getOrdering());
    assertEquals("loopCounter", actualParallelMultiInstanceBehavior.getCollectionElementIndexVariable());
    assertNull(activity2.getBehavior());
    assertNull(activity2.getDefaultFlow());
    assertNull(activity2.getFailedJobRetryTimeCycleValue());
    assertNull(((AdhocSubProcess) activity2).getCompletionCondition());
    assertNull(activity2.getId());
    assertNull(activity2.getDocumentation());
    assertNull(activity2.getName());
    assertNull(actualParallelMultiInstanceBehavior.getCollectionElementVariable());
    assertNull(actualParallelMultiInstanceBehavior.getCollectionVariable());
    assertNull(actualParallelMultiInstanceBehavior.getLoopDataOutputRef());
    assertNull(actualParallelMultiInstanceBehavior.getOutputDataItem());
    assertNull(activity2.getParentContainer());
    assertNull(activity2.getIoSpecification());
    assertNull(activity2.getLoopCharacteristics());
    assertNull(activity2.getSubProcess());
    assertNull(actualParallelMultiInstanceBehavior.getCollectionExpression());
    assertNull(actualParallelMultiInstanceBehavior.getCompletionConditionExpression());
    assertNull(actualParallelMultiInstanceBehavior.getLoopCardinalityExpression());
    assertNull(actualParallelMultiInstanceBehavior.getCommandContext());
    assertEquals(0, activity2.getXmlColumnNumber());
    assertEquals(0, activity2.getXmlRowNumber());
    assertFalse(activity2.hasMultiInstanceLoopCharacteristics());
    assertFalse(activity2.isForCompensation());
    assertFalse(((AdhocSubProcess) activity2).hasSequentialOrdering());
    assertFalse(activity2.isAsynchronous());
    assertFalse(activity2.isNotExclusive());
    assertFalse(actualParallelMultiInstanceBehavior.hasLoopDataOutputRef());
    assertFalse(actualParallelMultiInstanceBehavior.hasOutputDataItem());
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(activity2.getBoundaryEvents().isEmpty());
    assertTrue(activity2.getDataInputAssociations().isEmpty());
    assertTrue(activity2.getDataOutputAssociations().isEmpty());
    assertTrue(activity2.getMapExceptions().isEmpty());
    assertTrue(activity2.getExecutionListeners().isEmpty());
    assertTrue(activity2.getIncomingFlows().isEmpty());
    assertTrue(activity2.getOutgoingFlows().isEmpty());
    assertTrue(((AdhocSubProcess) activity2).getDataObjects().isEmpty());
    assertTrue(activity2.getAttributes().isEmpty());
    assertTrue(activity2.getExtensionElements().isEmpty());
    assertTrue(((AdhocSubProcess) activity2).getFlowElementMap().isEmpty());
    assertTrue(((AdhocSubProcess) activity2).hasParallelOrdering());
    assertTrue(((AdhocSubProcess) activity2).isCancelRemainingInstances());
    assertTrue(activity2.isExclusive());
    assertTrue(originalActivityBehavior.hasLoopCharacteristics());
    assertTrue(originalActivityBehavior.hasMultiInstanceCharacteristics());
    assertSame(originalActivityBehavior, actualParallelMultiInstanceBehavior.getInnerActivityBehavior());
  }

  /**
   * Method under test:
   * {@link ParallelMultiInstanceBehavior#ParallelMultiInstanceBehavior(Activity, AbstractBpmnActivityBehavior)}
   */
  @Test
  public void testNewParallelMultiInstanceBehavior2() {
    // Arrange
    AdhocSubProcess activity = new AdhocSubProcess();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    MessageEventDefinition messageEventDefinition2 = new MessageEventDefinition();
    EventSubProcessMessageStartEventActivityBehavior originalActivityBehavior = new EventSubProcessMessageStartEventActivityBehavior(
        messageEventDefinition, new DefaultMessageExecutionContext(messageEventDefinition2, new ExpressionManager(),
            mock(MessagePayloadMappingProvider.class)));

    // Act
    ParallelMultiInstanceBehavior actualParallelMultiInstanceBehavior = new ParallelMultiInstanceBehavior(activity,
        originalActivityBehavior);

    // Assert
    Activity activity2 = actualParallelMultiInstanceBehavior.activity;
    Collection<Artifact> artifacts = ((AdhocSubProcess) activity2).getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = ((AdhocSubProcess) activity2).getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(activity2 instanceof AdhocSubProcess);
    assertEquals("Parallel", ((AdhocSubProcess) activity2).getOrdering());
    assertEquals("loopCounter", actualParallelMultiInstanceBehavior.getCollectionElementIndexVariable());
    assertNull(activity2.getBehavior());
    assertNull(activity2.getDefaultFlow());
    assertNull(activity2.getFailedJobRetryTimeCycleValue());
    assertNull(((AdhocSubProcess) activity2).getCompletionCondition());
    assertNull(activity2.getId());
    assertNull(activity2.getDocumentation());
    assertNull(activity2.getName());
    assertNull(actualParallelMultiInstanceBehavior.getCollectionElementVariable());
    assertNull(actualParallelMultiInstanceBehavior.getCollectionVariable());
    assertNull(actualParallelMultiInstanceBehavior.getLoopDataOutputRef());
    assertNull(actualParallelMultiInstanceBehavior.getOutputDataItem());
    assertNull(activity2.getParentContainer());
    assertNull(activity2.getIoSpecification());
    assertNull(activity2.getLoopCharacteristics());
    assertNull(activity2.getSubProcess());
    assertNull(actualParallelMultiInstanceBehavior.getCollectionExpression());
    assertNull(actualParallelMultiInstanceBehavior.getCompletionConditionExpression());
    assertNull(actualParallelMultiInstanceBehavior.getLoopCardinalityExpression());
    assertNull(actualParallelMultiInstanceBehavior.getCommandContext());
    assertEquals(0, activity2.getXmlColumnNumber());
    assertEquals(0, activity2.getXmlRowNumber());
    assertFalse(activity2.hasMultiInstanceLoopCharacteristics());
    assertFalse(activity2.isForCompensation());
    assertFalse(((AdhocSubProcess) activity2).hasSequentialOrdering());
    assertFalse(activity2.isAsynchronous());
    assertFalse(activity2.isNotExclusive());
    assertFalse(actualParallelMultiInstanceBehavior.hasLoopDataOutputRef());
    assertFalse(actualParallelMultiInstanceBehavior.hasOutputDataItem());
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(activity2.getBoundaryEvents().isEmpty());
    assertTrue(activity2.getDataInputAssociations().isEmpty());
    assertTrue(activity2.getDataOutputAssociations().isEmpty());
    assertTrue(activity2.getMapExceptions().isEmpty());
    assertTrue(activity2.getExecutionListeners().isEmpty());
    assertTrue(activity2.getIncomingFlows().isEmpty());
    assertTrue(activity2.getOutgoingFlows().isEmpty());
    assertTrue(((AdhocSubProcess) activity2).getDataObjects().isEmpty());
    assertTrue(activity2.getAttributes().isEmpty());
    assertTrue(activity2.getExtensionElements().isEmpty());
    assertTrue(((AdhocSubProcess) activity2).getFlowElementMap().isEmpty());
    assertTrue(((AdhocSubProcess) activity2).hasParallelOrdering());
    assertTrue(((AdhocSubProcess) activity2).isCancelRemainingInstances());
    assertTrue(activity2.isExclusive());
    assertTrue(originalActivityBehavior.hasLoopCharacteristics());
    assertTrue(originalActivityBehavior.hasMultiInstanceCharacteristics());
    assertSame(originalActivityBehavior, actualParallelMultiInstanceBehavior.getInnerActivityBehavior());
  }
}
