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
package org.activiti.bpmn.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;

public class TaskDiffblueTest {
  /**
   * Method under test: {@link Task#clone()}
   */
  @Test
  public void testClone() {
    // Arrange and Act
    FlowElement actualCloneResult = (new Task()).clone();

    // Assert
    assertTrue(actualCloneResult instanceof Task);
    assertNull(((Task) actualCloneResult).getBehavior());
    assertNull(((Task) actualCloneResult).getDefaultFlow());
    assertNull(((Task) actualCloneResult).getFailedJobRetryTimeCycleValue());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(((Task) actualCloneResult).getIoSpecification());
    assertNull(((Task) actualCloneResult).getLoopCharacteristics());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(((Task) actualCloneResult).hasMultiInstanceLoopCharacteristics());
    assertFalse(((Task) actualCloneResult).isForCompensation());
    assertFalse(((Task) actualCloneResult).isAsynchronous());
    assertFalse(((Task) actualCloneResult).isNotExclusive());
    assertTrue(((Task) actualCloneResult).getBoundaryEvents().isEmpty());
    assertTrue(((Task) actualCloneResult).getDataInputAssociations().isEmpty());
    assertTrue(((Task) actualCloneResult).getDataOutputAssociations().isEmpty());
    assertTrue(((Task) actualCloneResult).getMapExceptions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(((Task) actualCloneResult).getIncomingFlows().isEmpty());
    assertTrue(((Task) actualCloneResult).getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(((Task) actualCloneResult).isExclusive());
  }

  /**
   * Method under test: {@link Task#clone()}
   */
  @Test
  public void testClone2() {
    // Arrange
    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(null);
    ioSpecification.setDataOutputs(null);

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    Task task = new Task();
    task.setIoSpecification(ioSpecification);
    task.setLoopCharacteristics(null);
    task.setDataInputAssociations(null);
    task.setDataOutputAssociations(null);
    task.setBoundaryEvents(boundaryEvents);

    // Act
    FlowElement actualCloneResult = task.clone();

    // Assert
    assertTrue(actualCloneResult instanceof Task);
    assertNull(((Task) actualCloneResult).getBehavior());
    assertNull(((Task) actualCloneResult).getDefaultFlow());
    assertNull(((Task) actualCloneResult).getFailedJobRetryTimeCycleValue());
    IOSpecification ioSpecification2 = ((Task) actualCloneResult).getIoSpecification();
    assertNull(ioSpecification2.getId());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(((Task) actualCloneResult).getLoopCharacteristics());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, ioSpecification2.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, ioSpecification2.getXmlRowNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(((Task) actualCloneResult).hasMultiInstanceLoopCharacteristics());
    assertFalse(((Task) actualCloneResult).isForCompensation());
    assertFalse(((Task) actualCloneResult).isAsynchronous());
    assertFalse(((Task) actualCloneResult).isNotExclusive());
    assertTrue(((Task) actualCloneResult).getDataInputAssociations().isEmpty());
    assertTrue(((Task) actualCloneResult).getDataOutputAssociations().isEmpty());
    assertTrue(((Task) actualCloneResult).getMapExceptions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(((Task) actualCloneResult).getIncomingFlows().isEmpty());
    assertTrue(((Task) actualCloneResult).getOutgoingFlows().isEmpty());
    assertTrue(ioSpecification2.getDataInputRefs().isEmpty());
    assertTrue(ioSpecification2.getDataInputs().isEmpty());
    assertTrue(ioSpecification2.getDataOutputRefs().isEmpty());
    assertTrue(ioSpecification2.getDataOutputs().isEmpty());
    assertTrue(ioSpecification2.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(ioSpecification2.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(((Task) actualCloneResult).isExclusive());
    assertEquals(boundaryEvents, ((Task) actualCloneResult).getBoundaryEvents());
  }

  /**
   * Method under test: {@link Task#clone()}
   */
  @Test
  public void testClone3() {
    // Arrange
    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(null);
    ioSpecification.setDataOutputs(null);

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    Task task = new Task();
    task.setIoSpecification(ioSpecification);
    task.setLoopCharacteristics(null);
    task.setDataInputAssociations(null);
    task.setDataOutputAssociations(dataOutputAssociations);
    task.setBoundaryEvents(boundaryEvents);

    // Act
    FlowElement actualCloneResult = task.clone();

    // Assert
    assertTrue(actualCloneResult instanceof Task);
    assertNull(((Task) actualCloneResult).getBehavior());
    assertNull(((Task) actualCloneResult).getDefaultFlow());
    assertNull(((Task) actualCloneResult).getFailedJobRetryTimeCycleValue());
    List<DataAssociation> dataOutputAssociations2 = ((Task) actualCloneResult).getDataOutputAssociations();
    assertEquals(1, dataOutputAssociations2.size());
    DataAssociation getResult = dataOutputAssociations2.get(0);
    assertNull(getResult.getId());
    IOSpecification ioSpecification2 = ((Task) actualCloneResult).getIoSpecification();
    assertNull(ioSpecification2.getId());
    assertNull(actualCloneResult.getId());
    assertNull(getResult.getSourceRef());
    assertNull(getResult.getTargetRef());
    assertNull(getResult.getTransformation());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(((Task) actualCloneResult).getLoopCharacteristics());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, ioSpecification2.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertEquals(0, ioSpecification2.getXmlRowNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(((Task) actualCloneResult).hasMultiInstanceLoopCharacteristics());
    assertFalse(((Task) actualCloneResult).isForCompensation());
    assertFalse(((Task) actualCloneResult).isAsynchronous());
    assertFalse(((Task) actualCloneResult).isNotExclusive());
    assertTrue(((Task) actualCloneResult).getDataInputAssociations().isEmpty());
    assertTrue(((Task) actualCloneResult).getMapExceptions().isEmpty());
    assertTrue(getResult.getAssignments().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(((Task) actualCloneResult).getIncomingFlows().isEmpty());
    assertTrue(((Task) actualCloneResult).getOutgoingFlows().isEmpty());
    assertTrue(ioSpecification2.getDataInputRefs().isEmpty());
    assertTrue(ioSpecification2.getDataInputs().isEmpty());
    assertTrue(ioSpecification2.getDataOutputRefs().isEmpty());
    assertTrue(ioSpecification2.getDataOutputs().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(ioSpecification2.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(ioSpecification2.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(((Task) actualCloneResult).isExclusive());
    assertEquals(boundaryEvents, ((Task) actualCloneResult).getBoundaryEvents());
  }

  /**
   * Method under test: {@link Task#clone()}
   */
  @Test
  public void testClone4() {
    // Arrange
    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(null);
    ioSpecification.setDataOutputs(null);

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    Task task = new Task();
    task.setIoSpecification(ioSpecification);
    task.setLoopCharacteristics(null);
    task.setDataInputAssociations(dataInputAssociations);
    task.setDataOutputAssociations(null);
    task.setBoundaryEvents(boundaryEvents);

    // Act
    FlowElement actualCloneResult = task.clone();

    // Assert
    assertTrue(actualCloneResult instanceof Task);
    assertNull(((Task) actualCloneResult).getBehavior());
    assertNull(((Task) actualCloneResult).getDefaultFlow());
    assertNull(((Task) actualCloneResult).getFailedJobRetryTimeCycleValue());
    List<DataAssociation> dataInputAssociations2 = ((Task) actualCloneResult).getDataInputAssociations();
    assertEquals(1, dataInputAssociations2.size());
    DataAssociation getResult = dataInputAssociations2.get(0);
    assertNull(getResult.getId());
    IOSpecification ioSpecification2 = ((Task) actualCloneResult).getIoSpecification();
    assertNull(ioSpecification2.getId());
    assertNull(actualCloneResult.getId());
    assertNull(getResult.getSourceRef());
    assertNull(getResult.getTargetRef());
    assertNull(getResult.getTransformation());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(((Task) actualCloneResult).getLoopCharacteristics());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, ioSpecification2.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertEquals(0, ioSpecification2.getXmlRowNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(((Task) actualCloneResult).hasMultiInstanceLoopCharacteristics());
    assertFalse(((Task) actualCloneResult).isForCompensation());
    assertFalse(((Task) actualCloneResult).isAsynchronous());
    assertFalse(((Task) actualCloneResult).isNotExclusive());
    assertTrue(((Task) actualCloneResult).getDataOutputAssociations().isEmpty());
    assertTrue(((Task) actualCloneResult).getMapExceptions().isEmpty());
    assertTrue(getResult.getAssignments().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(((Task) actualCloneResult).getIncomingFlows().isEmpty());
    assertTrue(((Task) actualCloneResult).getOutgoingFlows().isEmpty());
    assertTrue(ioSpecification2.getDataInputRefs().isEmpty());
    assertTrue(ioSpecification2.getDataInputs().isEmpty());
    assertTrue(ioSpecification2.getDataOutputRefs().isEmpty());
    assertTrue(ioSpecification2.getDataOutputs().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(ioSpecification2.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(ioSpecification2.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(((Task) actualCloneResult).isExclusive());
    assertEquals(boundaryEvents, ((Task) actualCloneResult).getBoundaryEvents());
  }

  /**
   * Method under test: {@link Task#clone()}
   */
  @Test
  public void testClone5() {
    // Arrange
    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(null);
    ioSpecification.setDataOutputs(null);

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    Task task = new Task();
    task.setIoSpecification(ioSpecification);
    task.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    task.setDataInputAssociations(null);
    task.setDataOutputAssociations(null);
    task.setBoundaryEvents(boundaryEvents);

    // Act
    FlowElement actualCloneResult = task.clone();

    // Assert
    assertTrue(actualCloneResult instanceof Task);
    assertNull(((Task) actualCloneResult).getBehavior());
    assertNull(((Task) actualCloneResult).getDefaultFlow());
    assertNull(((Task) actualCloneResult).getFailedJobRetryTimeCycleValue());
    IOSpecification ioSpecification2 = ((Task) actualCloneResult).getIoSpecification();
    assertNull(ioSpecification2.getId());
    MultiInstanceLoopCharacteristics loopCharacteristics = ((Task) actualCloneResult).getLoopCharacteristics();
    assertNull(loopCharacteristics.getId());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(loopCharacteristics.getCompletionCondition());
    assertNull(loopCharacteristics.getElementIndexVariable());
    assertNull(loopCharacteristics.getElementVariable());
    assertNull(loopCharacteristics.getInputDataItem());
    assertNull(loopCharacteristics.getLoopCardinality());
    assertNull(loopCharacteristics.getLoopDataOutputRef());
    assertNull(loopCharacteristics.getOutputDataItem());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, ioSpecification2.getXmlColumnNumber());
    assertEquals(0, loopCharacteristics.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, ioSpecification2.getXmlRowNumber());
    assertEquals(0, loopCharacteristics.getXmlRowNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(((Task) actualCloneResult).isForCompensation());
    assertFalse(((Task) actualCloneResult).isAsynchronous());
    assertFalse(((Task) actualCloneResult).isNotExclusive());
    assertFalse(loopCharacteristics.isSequential());
    assertTrue(((Task) actualCloneResult).getDataInputAssociations().isEmpty());
    assertTrue(((Task) actualCloneResult).getDataOutputAssociations().isEmpty());
    assertTrue(((Task) actualCloneResult).getMapExceptions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(((Task) actualCloneResult).getIncomingFlows().isEmpty());
    assertTrue(((Task) actualCloneResult).getOutgoingFlows().isEmpty());
    assertTrue(ioSpecification2.getDataInputRefs().isEmpty());
    assertTrue(ioSpecification2.getDataInputs().isEmpty());
    assertTrue(ioSpecification2.getDataOutputRefs().isEmpty());
    assertTrue(ioSpecification2.getDataOutputs().isEmpty());
    assertTrue(ioSpecification2.getAttributes().isEmpty());
    assertTrue(loopCharacteristics.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(ioSpecification2.getExtensionElements().isEmpty());
    assertTrue(loopCharacteristics.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(((Task) actualCloneResult).hasMultiInstanceLoopCharacteristics());
    assertTrue(((Task) actualCloneResult).isExclusive());
    assertEquals(boundaryEvents, ((Task) actualCloneResult).getBoundaryEvents());
  }

  /**
   * Method under test: {@link Task#clone()}
   */
  @Test
  public void testClone6() {
    // Arrange
    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(null);
    ioSpecification.setDataOutputs(new ArrayList<>());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    Task task = new Task();
    task.setIoSpecification(ioSpecification);
    task.setLoopCharacteristics(null);
    task.setDataInputAssociations(null);
    task.setDataOutputAssociations(null);
    task.setBoundaryEvents(boundaryEvents);

    // Act
    FlowElement actualCloneResult = task.clone();

    // Assert
    assertTrue(actualCloneResult instanceof Task);
    assertNull(((Task) actualCloneResult).getBehavior());
    assertNull(((Task) actualCloneResult).getDefaultFlow());
    assertNull(((Task) actualCloneResult).getFailedJobRetryTimeCycleValue());
    IOSpecification ioSpecification2 = ((Task) actualCloneResult).getIoSpecification();
    assertNull(ioSpecification2.getId());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(((Task) actualCloneResult).getLoopCharacteristics());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, ioSpecification2.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, ioSpecification2.getXmlRowNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(((Task) actualCloneResult).hasMultiInstanceLoopCharacteristics());
    assertFalse(((Task) actualCloneResult).isForCompensation());
    assertFalse(((Task) actualCloneResult).isAsynchronous());
    assertFalse(((Task) actualCloneResult).isNotExclusive());
    assertTrue(((Task) actualCloneResult).getDataInputAssociations().isEmpty());
    assertTrue(((Task) actualCloneResult).getDataOutputAssociations().isEmpty());
    assertTrue(((Task) actualCloneResult).getMapExceptions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(((Task) actualCloneResult).getIncomingFlows().isEmpty());
    assertTrue(((Task) actualCloneResult).getOutgoingFlows().isEmpty());
    assertTrue(ioSpecification2.getDataInputRefs().isEmpty());
    assertTrue(ioSpecification2.getDataInputs().isEmpty());
    assertTrue(ioSpecification2.getDataOutputRefs().isEmpty());
    assertTrue(ioSpecification2.getDataOutputs().isEmpty());
    assertTrue(ioSpecification2.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(ioSpecification2.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(((Task) actualCloneResult).isExclusive());
    assertEquals(boundaryEvents, ((Task) actualCloneResult).getBoundaryEvents());
  }

  /**
   * Method under test: {@link Task#clone()}
   */
  @Test
  public void testClone7() {
    // Arrange
    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(new DataSpec());

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(null);
    ioSpecification.setDataOutputs(dataOutputs);

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    Task task = new Task();
    task.setIoSpecification(ioSpecification);
    task.setLoopCharacteristics(null);
    task.setDataInputAssociations(null);
    task.setDataOutputAssociations(null);
    task.setBoundaryEvents(boundaryEvents);

    // Act
    FlowElement actualCloneResult = task.clone();

    // Assert
    assertTrue(actualCloneResult instanceof Task);
    assertNull(((Task) actualCloneResult).getBehavior());
    assertNull(((Task) actualCloneResult).getDefaultFlow());
    assertNull(((Task) actualCloneResult).getFailedJobRetryTimeCycleValue());
    IOSpecification ioSpecification2 = ((Task) actualCloneResult).getIoSpecification();
    List<DataSpec> dataOutputs2 = ioSpecification2.getDataOutputs();
    assertEquals(1, dataOutputs2.size());
    DataSpec getResult = dataOutputs2.get(0);
    assertNull(getResult.getId());
    assertNull(ioSpecification2.getId());
    assertNull(actualCloneResult.getId());
    assertNull(getResult.getItemSubjectRef());
    assertNull(getResult.getName());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(((Task) actualCloneResult).getLoopCharacteristics());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, ioSpecification2.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertEquals(0, ioSpecification2.getXmlRowNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(((Task) actualCloneResult).hasMultiInstanceLoopCharacteristics());
    assertFalse(((Task) actualCloneResult).isForCompensation());
    assertFalse(getResult.isCollection());
    assertFalse(((Task) actualCloneResult).isAsynchronous());
    assertFalse(((Task) actualCloneResult).isNotExclusive());
    assertTrue(((Task) actualCloneResult).getDataInputAssociations().isEmpty());
    assertTrue(((Task) actualCloneResult).getDataOutputAssociations().isEmpty());
    assertTrue(((Task) actualCloneResult).getMapExceptions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(((Task) actualCloneResult).getIncomingFlows().isEmpty());
    assertTrue(((Task) actualCloneResult).getOutgoingFlows().isEmpty());
    assertTrue(ioSpecification2.getDataInputRefs().isEmpty());
    assertTrue(ioSpecification2.getDataInputs().isEmpty());
    assertTrue(ioSpecification2.getDataOutputRefs().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(ioSpecification2.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(ioSpecification2.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(((Task) actualCloneResult).isExclusive());
    assertEquals(boundaryEvents, ((Task) actualCloneResult).getBoundaryEvents());
  }

  /**
   * Method under test: {@link Task#clone()}
   */
  @Test
  public void testClone8() {
    // Arrange and Act
    BusinessRuleTask actualCloneResult = (new BusinessRuleTask()).clone();

    // Assert
    assertTrue(actualCloneResult instanceof BusinessRuleTask);
    assertNull(((BusinessRuleTask) actualCloneResult).getBehavior());
    assertNull(((BusinessRuleTask) actualCloneResult).getDefaultFlow());
    assertNull(((BusinessRuleTask) actualCloneResult).getFailedJobRetryTimeCycleValue());
    assertNull(actualCloneResult.getId());
    assertNull(((BusinessRuleTask) actualCloneResult).getClassName());
    assertNull(((BusinessRuleTask) actualCloneResult).getResultVariableName());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(((BusinessRuleTask) actualCloneResult).getIoSpecification());
    assertNull(((BusinessRuleTask) actualCloneResult).getLoopCharacteristics());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(((BusinessRuleTask) actualCloneResult).hasMultiInstanceLoopCharacteristics());
    assertFalse(((BusinessRuleTask) actualCloneResult).isForCompensation());
    assertFalse(((BusinessRuleTask) actualCloneResult).isExclude());
    assertFalse(((BusinessRuleTask) actualCloneResult).isAsynchronous());
    assertFalse(((BusinessRuleTask) actualCloneResult).isNotExclusive());
    assertTrue(((BusinessRuleTask) actualCloneResult).getBoundaryEvents().isEmpty());
    assertTrue(((BusinessRuleTask) actualCloneResult).getDataInputAssociations().isEmpty());
    assertTrue(((BusinessRuleTask) actualCloneResult).getDataOutputAssociations().isEmpty());
    assertTrue(((BusinessRuleTask) actualCloneResult).getMapExceptions().isEmpty());
    assertTrue(((BusinessRuleTask) actualCloneResult).getInputVariables().isEmpty());
    assertTrue(((BusinessRuleTask) actualCloneResult).getRuleNames().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(((BusinessRuleTask) actualCloneResult).getIncomingFlows().isEmpty());
    assertTrue(((BusinessRuleTask) actualCloneResult).getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(((BusinessRuleTask) actualCloneResult).isExclusive());
  }

  /**
   * Method under test: {@link Task#clone()}
   */
  @Test
  public void testClone9() {
    // Arrange
    Task task = new Task();
    task.setForCompensation(true);

    // Act
    FlowElement actualCloneResult = task.clone();

    // Assert
    assertTrue(actualCloneResult instanceof Task);
    assertNull(((Task) actualCloneResult).getBehavior());
    assertNull(((Task) actualCloneResult).getDefaultFlow());
    assertNull(((Task) actualCloneResult).getFailedJobRetryTimeCycleValue());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(((Task) actualCloneResult).getIoSpecification());
    assertNull(((Task) actualCloneResult).getLoopCharacteristics());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(((Task) actualCloneResult).hasMultiInstanceLoopCharacteristics());
    assertFalse(((Task) actualCloneResult).isAsynchronous());
    assertFalse(((Task) actualCloneResult).isNotExclusive());
    assertTrue(((Task) actualCloneResult).getBoundaryEvents().isEmpty());
    assertTrue(((Task) actualCloneResult).getDataInputAssociations().isEmpty());
    assertTrue(((Task) actualCloneResult).getDataOutputAssociations().isEmpty());
    assertTrue(((Task) actualCloneResult).getMapExceptions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(((Task) actualCloneResult).getIncomingFlows().isEmpty());
    assertTrue(((Task) actualCloneResult).getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(((Task) actualCloneResult).isForCompensation());
    assertTrue(((Task) actualCloneResult).isExclusive());
  }

  /**
   * Method under test: {@link Task#clone()}
   */
  @Test
  public void testClone10() {
    // Arrange
    Task task = new Task();
    task.setIoSpecification(new IOSpecification());

    // Act
    FlowElement actualCloneResult = task.clone();

    // Assert
    assertTrue(actualCloneResult instanceof Task);
    assertNull(((Task) actualCloneResult).getBehavior());
    assertNull(((Task) actualCloneResult).getDefaultFlow());
    assertNull(((Task) actualCloneResult).getFailedJobRetryTimeCycleValue());
    IOSpecification ioSpecification = ((Task) actualCloneResult).getIoSpecification();
    assertNull(ioSpecification.getId());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(((Task) actualCloneResult).getLoopCharacteristics());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, ioSpecification.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, ioSpecification.getXmlRowNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(((Task) actualCloneResult).hasMultiInstanceLoopCharacteristics());
    assertFalse(((Task) actualCloneResult).isForCompensation());
    assertFalse(((Task) actualCloneResult).isAsynchronous());
    assertFalse(((Task) actualCloneResult).isNotExclusive());
    assertTrue(((Task) actualCloneResult).getBoundaryEvents().isEmpty());
    assertTrue(((Task) actualCloneResult).getDataInputAssociations().isEmpty());
    assertTrue(((Task) actualCloneResult).getDataOutputAssociations().isEmpty());
    assertTrue(((Task) actualCloneResult).getMapExceptions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(((Task) actualCloneResult).getIncomingFlows().isEmpty());
    assertTrue(((Task) actualCloneResult).getOutgoingFlows().isEmpty());
    assertTrue(ioSpecification.getDataInputRefs().isEmpty());
    assertTrue(ioSpecification.getDataInputs().isEmpty());
    assertTrue(ioSpecification.getDataOutputRefs().isEmpty());
    assertTrue(ioSpecification.getDataOutputs().isEmpty());
    assertTrue(ioSpecification.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(ioSpecification.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(((Task) actualCloneResult).isExclusive());
  }

  /**
   * Method under test: {@link Task#setValues(Task)}
   */
  @Test
  public void testSetValues() {
    // Arrange
    Task task = new Task();
    Task otherElement = new Task();

    // Act
    task.setValues(otherElement);

    // Assert
    assertNull(otherElement.getDefaultFlow());
    assertNull(otherElement.getFailedJobRetryTimeCycleValue());
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getIoSpecification());
    assertNull(otherElement.getLoopCharacteristics());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertTrue(otherElement.getDataInputAssociations().isEmpty());
    assertTrue(otherElement.getDataOutputAssociations().isEmpty());
    assertTrue(otherElement.isExclusive());
  }

  /**
   * Method under test: {@link Task#setValues(Task)}
   */
  @Test
  public void testSetValues2() {
    // Arrange
    Task task = new Task();

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(null);
    ioSpecification.setDataOutputs(null);

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    Task otherElement = new Task();
    otherElement.setIoSpecification(ioSpecification);
    otherElement.setLoopCharacteristics(null);
    otherElement.setDataInputAssociations(null);
    otherElement.setDataOutputAssociations(null);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    task.setValues(otherElement);

    // Assert
    assertNull(otherElement.getDefaultFlow());
    assertNull(otherElement.getFailedJobRetryTimeCycleValue());
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getDataInputAssociations());
    assertNull(otherElement.getDataOutputAssociations());
    assertNull(otherElement.getLoopCharacteristics());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertTrue(otherElement.isExclusive());
    assertSame(ioSpecification, otherElement.getIoSpecification());
  }

  /**
   * Method under test: {@link Task#setValues(Task)}
   */
  @Test
  public void testSetValues3() {
    // Arrange
    Task task = new Task();

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(null);
    ioSpecification.setDataOutputs(null);

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    Task otherElement = new Task();
    otherElement.setIoSpecification(ioSpecification);
    otherElement.setLoopCharacteristics(null);
    otherElement.setDataInputAssociations(null);
    otherElement.setDataOutputAssociations(dataOutputAssociations);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    task.setValues(otherElement);

    // Assert
    assertNull(otherElement.getDefaultFlow());
    assertNull(otherElement.getFailedJobRetryTimeCycleValue());
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getDataInputAssociations());
    assertNull(otherElement.getLoopCharacteristics());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertTrue(otherElement.isExclusive());
    assertSame(dataOutputAssociations, otherElement.getDataOutputAssociations());
    assertSame(ioSpecification, otherElement.getIoSpecification());
  }

  /**
   * Method under test: {@link Task#setValues(Task)}
   */
  @Test
  public void testSetValues4() {
    // Arrange
    Task task = new Task();

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(null);
    ioSpecification.setDataOutputs(null);

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    Task otherElement = new Task();
    otherElement.setIoSpecification(ioSpecification);
    otherElement.setLoopCharacteristics(null);
    otherElement.setDataInputAssociations(dataInputAssociations);
    otherElement.setDataOutputAssociations(null);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    task.setValues(otherElement);

    // Assert
    assertNull(otherElement.getDefaultFlow());
    assertNull(otherElement.getFailedJobRetryTimeCycleValue());
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getDataOutputAssociations());
    assertNull(otherElement.getLoopCharacteristics());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertTrue(otherElement.isExclusive());
    assertSame(dataInputAssociations, otherElement.getDataInputAssociations());
    assertSame(ioSpecification, otherElement.getIoSpecification());
  }

  /**
   * Method under test: {@link Task#setValues(Task)}
   */
  @Test
  public void testSetValues5() {
    // Arrange
    Task task = new Task();

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(null);
    ioSpecification.setDataOutputs(null);

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    Task otherElement = new Task();
    otherElement.setIoSpecification(ioSpecification);
    MultiInstanceLoopCharacteristics loopCharacteristics = new MultiInstanceLoopCharacteristics();
    otherElement.setLoopCharacteristics(loopCharacteristics);
    otherElement.setDataInputAssociations(null);
    otherElement.setDataOutputAssociations(null);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    task.setValues(otherElement);

    // Assert
    assertNull(otherElement.getDefaultFlow());
    assertNull(otherElement.getFailedJobRetryTimeCycleValue());
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getDataInputAssociations());
    assertNull(otherElement.getDataOutputAssociations());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertTrue(otherElement.hasMultiInstanceLoopCharacteristics());
    assertTrue(otherElement.isExclusive());
    assertSame(ioSpecification, otherElement.getIoSpecification());
    assertSame(loopCharacteristics, otherElement.getLoopCharacteristics());
  }

  /**
   * Method under test: {@link Task#setValues(Task)}
   */
  @Test
  public void testSetValues6() {
    // Arrange
    Task task = new Task();

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(null);
    ioSpecification.setDataOutputs(new ArrayList<>());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    Task otherElement = new Task();
    otherElement.setIoSpecification(ioSpecification);
    otherElement.setLoopCharacteristics(null);
    otherElement.setDataInputAssociations(null);
    otherElement.setDataOutputAssociations(null);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    task.setValues(otherElement);

    // Assert
    assertNull(otherElement.getDefaultFlow());
    assertNull(otherElement.getFailedJobRetryTimeCycleValue());
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getDataInputAssociations());
    assertNull(otherElement.getDataOutputAssociations());
    assertNull(otherElement.getLoopCharacteristics());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertTrue(otherElement.isExclusive());
    assertSame(ioSpecification, otherElement.getIoSpecification());
  }

  /**
   * Method under test: {@link Task#setValues(Task)}
   */
  @Test
  public void testSetValues7() {
    // Arrange
    Task task = new Task();

    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(new DataSpec());

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(null);
    ioSpecification.setDataOutputs(dataOutputs);

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    Task otherElement = new Task();
    otherElement.setIoSpecification(ioSpecification);
    otherElement.setLoopCharacteristics(null);
    otherElement.setDataInputAssociations(null);
    otherElement.setDataOutputAssociations(null);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    task.setValues(otherElement);

    // Assert
    assertNull(otherElement.getDefaultFlow());
    assertNull(otherElement.getFailedJobRetryTimeCycleValue());
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getDataInputAssociations());
    assertNull(otherElement.getDataOutputAssociations());
    assertNull(otherElement.getLoopCharacteristics());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertTrue(otherElement.isExclusive());
    assertSame(ioSpecification, otherElement.getIoSpecification());
  }

  /**
   * Method under test: {@link Task#setValues(Task)}
   */
  @Test
  public void testSetValues8() {
    // Arrange
    Task task = new Task();
    BusinessRuleTask otherElement = mock(BusinessRuleTask.class);
    when(otherElement.isForCompensation()).thenReturn(true);
    when(otherElement.isAsynchronous()).thenReturn(true);
    when(otherElement.isNotExclusive()).thenReturn(true);
    when(otherElement.getDefaultFlow()).thenReturn("Default Flow");
    when(otherElement.getFailedJobRetryTimeCycleValue()).thenReturn("42");
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getDocumentation()).thenReturn("Documentation");
    when(otherElement.getName()).thenReturn("Name");
    when(otherElement.getBoundaryEvents()).thenReturn(new ArrayList<>());
    when(otherElement.getDataInputAssociations()).thenReturn(new ArrayList<>());
    when(otherElement.getDataOutputAssociations()).thenReturn(new ArrayList<>());
    when(otherElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherElement.getAttributes()).thenReturn(new HashMap<>());
    when(otherElement.getExtensionElements()).thenReturn(new HashMap<>());
    when(otherElement.getIoSpecification()).thenReturn(new IOSpecification());
    when(otherElement.getLoopCharacteristics()).thenReturn(new MultiInstanceLoopCharacteristics());

    // Act
    task.setValues(otherElement);

    // Assert
    verify(otherElement).getBoundaryEvents();
    verify(otherElement, atLeast(1)).getDataInputAssociations();
    verify(otherElement, atLeast(1)).getDataOutputAssociations();
    verify(otherElement).getDefaultFlow();
    verify(otherElement).getFailedJobRetryTimeCycleValue();
    verify(otherElement, atLeast(1)).getIoSpecification();
    verify(otherElement, atLeast(1)).getLoopCharacteristics();
    verify(otherElement).isForCompensation();
    verify(otherElement, atLeast(1)).getAttributes();
    verify(otherElement, atLeast(1)).getExtensionElements();
    verify(otherElement).getId();
    verify(otherElement).getDocumentation();
    verify(otherElement, atLeast(1)).getExecutionListeners();
    verify(otherElement).getName();
    verify(otherElement).isAsynchronous();
    verify(otherElement).isNotExclusive();
    assertEquals("42", task.getFailedJobRetryTimeCycleValue());
    assertEquals("42", task.getId());
    assertEquals("Default Flow", task.getDefaultFlow());
    assertEquals("Documentation", task.getDocumentation());
    assertEquals("Name", task.getName());
    IOSpecification ioSpecification = task.getIoSpecification();
    assertNull(ioSpecification.getId());
    MultiInstanceLoopCharacteristics loopCharacteristics = task.getLoopCharacteristics();
    assertNull(loopCharacteristics.getId());
    assertNull(loopCharacteristics.getCompletionCondition());
    assertNull(loopCharacteristics.getElementIndexVariable());
    assertNull(loopCharacteristics.getElementVariable());
    assertNull(loopCharacteristics.getInputDataItem());
    assertNull(loopCharacteristics.getLoopCardinality());
    assertNull(loopCharacteristics.getLoopDataOutputRef());
    assertNull(loopCharacteristics.getOutputDataItem());
    assertEquals(0, ioSpecification.getXmlColumnNumber());
    assertEquals(0, loopCharacteristics.getXmlColumnNumber());
    assertEquals(0, ioSpecification.getXmlRowNumber());
    assertEquals(0, loopCharacteristics.getXmlRowNumber());
    assertFalse(task.isExclusive());
    assertFalse(loopCharacteristics.isSequential());
    assertTrue(task.getDataInputAssociations().isEmpty());
    assertTrue(task.getDataOutputAssociations().isEmpty());
    assertTrue(ioSpecification.getDataInputRefs().isEmpty());
    assertTrue(ioSpecification.getDataInputs().isEmpty());
    assertTrue(ioSpecification.getDataOutputRefs().isEmpty());
    assertTrue(ioSpecification.getDataOutputs().isEmpty());
    assertTrue(ioSpecification.getAttributes().isEmpty());
    assertTrue(loopCharacteristics.getAttributes().isEmpty());
    assertTrue(ioSpecification.getExtensionElements().isEmpty());
    assertTrue(loopCharacteristics.getExtensionElements().isEmpty());
    assertTrue(task.hasMultiInstanceLoopCharacteristics());
    assertTrue(task.isForCompensation());
    assertTrue(task.isAsynchronous());
    assertTrue(task.isNotExclusive());
  }

  /**
   * Method under test: {@link Task#setValues(Task)}
   */
  @Test
  public void testSetValues9() {
    // Arrange
    Task task = new Task();

    ArrayList<DataAssociation> dataAssociationList = new ArrayList<>();
    dataAssociationList.add(new DataAssociation());
    BusinessRuleTask otherElement = mock(BusinessRuleTask.class);
    when(otherElement.isForCompensation()).thenReturn(true);
    when(otherElement.isAsynchronous()).thenReturn(true);
    when(otherElement.isNotExclusive()).thenReturn(true);
    when(otherElement.getDefaultFlow()).thenReturn("Default Flow");
    when(otherElement.getFailedJobRetryTimeCycleValue()).thenReturn("42");
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getDocumentation()).thenReturn("Documentation");
    when(otherElement.getName()).thenReturn("Name");
    when(otherElement.getBoundaryEvents()).thenReturn(new ArrayList<>());
    when(otherElement.getDataInputAssociations()).thenReturn(dataAssociationList);
    when(otherElement.getDataOutputAssociations()).thenReturn(new ArrayList<>());
    when(otherElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherElement.getAttributes()).thenReturn(new HashMap<>());
    when(otherElement.getExtensionElements()).thenReturn(new HashMap<>());
    when(otherElement.getIoSpecification()).thenReturn(new IOSpecification());
    when(otherElement.getLoopCharacteristics()).thenReturn(new MultiInstanceLoopCharacteristics());

    // Act
    task.setValues(otherElement);

    // Assert
    verify(otherElement).getBoundaryEvents();
    verify(otherElement, atLeast(1)).getDataInputAssociations();
    verify(otherElement, atLeast(1)).getDataOutputAssociations();
    verify(otherElement).getDefaultFlow();
    verify(otherElement).getFailedJobRetryTimeCycleValue();
    verify(otherElement, atLeast(1)).getIoSpecification();
    verify(otherElement, atLeast(1)).getLoopCharacteristics();
    verify(otherElement).isForCompensation();
    verify(otherElement, atLeast(1)).getAttributes();
    verify(otherElement, atLeast(1)).getExtensionElements();
    verify(otherElement).getId();
    verify(otherElement).getDocumentation();
    verify(otherElement, atLeast(1)).getExecutionListeners();
    verify(otherElement).getName();
    verify(otherElement).isAsynchronous();
    verify(otherElement).isNotExclusive();
    assertEquals("42", task.getFailedJobRetryTimeCycleValue());
    assertEquals("42", task.getId());
    assertEquals("Default Flow", task.getDefaultFlow());
    assertEquals("Documentation", task.getDocumentation());
    assertEquals("Name", task.getName());
    List<DataAssociation> dataInputAssociations = task.getDataInputAssociations();
    assertEquals(1, dataInputAssociations.size());
    DataAssociation getResult = dataInputAssociations.get(0);
    assertNull(getResult.getId());
    IOSpecification ioSpecification = task.getIoSpecification();
    assertNull(ioSpecification.getId());
    MultiInstanceLoopCharacteristics loopCharacteristics = task.getLoopCharacteristics();
    assertNull(loopCharacteristics.getId());
    assertNull(getResult.getSourceRef());
    assertNull(getResult.getTargetRef());
    assertNull(getResult.getTransformation());
    assertNull(loopCharacteristics.getCompletionCondition());
    assertNull(loopCharacteristics.getElementIndexVariable());
    assertNull(loopCharacteristics.getElementVariable());
    assertNull(loopCharacteristics.getInputDataItem());
    assertNull(loopCharacteristics.getLoopCardinality());
    assertNull(loopCharacteristics.getLoopDataOutputRef());
    assertNull(loopCharacteristics.getOutputDataItem());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, ioSpecification.getXmlColumnNumber());
    assertEquals(0, loopCharacteristics.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertEquals(0, ioSpecification.getXmlRowNumber());
    assertEquals(0, loopCharacteristics.getXmlRowNumber());
    assertFalse(task.isExclusive());
    assertFalse(loopCharacteristics.isSequential());
    assertTrue(task.getDataOutputAssociations().isEmpty());
    assertTrue(getResult.getAssignments().isEmpty());
    assertTrue(ioSpecification.getDataInputRefs().isEmpty());
    assertTrue(ioSpecification.getDataInputs().isEmpty());
    assertTrue(ioSpecification.getDataOutputRefs().isEmpty());
    assertTrue(ioSpecification.getDataOutputs().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(ioSpecification.getAttributes().isEmpty());
    assertTrue(loopCharacteristics.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(ioSpecification.getExtensionElements().isEmpty());
    assertTrue(loopCharacteristics.getExtensionElements().isEmpty());
    assertTrue(task.hasMultiInstanceLoopCharacteristics());
    assertTrue(task.isForCompensation());
    assertTrue(task.isAsynchronous());
    assertTrue(task.isNotExclusive());
  }

  /**
   * Method under test: {@link Task#setValues(Task)}
   */
  @Test
  public void testSetValues10() {
    // Arrange
    Task task = new Task();

    ArrayList<DataAssociation> dataAssociationList = new ArrayList<>();
    dataAssociationList.add(new DataAssociation());
    BusinessRuleTask otherElement = mock(BusinessRuleTask.class);
    when(otherElement.isForCompensation()).thenReturn(true);
    when(otherElement.isAsynchronous()).thenReturn(true);
    when(otherElement.isNotExclusive()).thenReturn(true);
    when(otherElement.getDefaultFlow()).thenReturn("Default Flow");
    when(otherElement.getFailedJobRetryTimeCycleValue()).thenReturn("42");
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getDocumentation()).thenReturn("Documentation");
    when(otherElement.getName()).thenReturn("Name");
    when(otherElement.getBoundaryEvents()).thenReturn(new ArrayList<>());
    when(otherElement.getDataInputAssociations()).thenReturn(new ArrayList<>());
    when(otherElement.getDataOutputAssociations()).thenReturn(dataAssociationList);
    when(otherElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherElement.getAttributes()).thenReturn(new HashMap<>());
    when(otherElement.getExtensionElements()).thenReturn(new HashMap<>());
    when(otherElement.getIoSpecification()).thenReturn(new IOSpecification());
    when(otherElement.getLoopCharacteristics()).thenReturn(new MultiInstanceLoopCharacteristics());

    // Act
    task.setValues(otherElement);

    // Assert
    verify(otherElement).getBoundaryEvents();
    verify(otherElement, atLeast(1)).getDataInputAssociations();
    verify(otherElement, atLeast(1)).getDataOutputAssociations();
    verify(otherElement).getDefaultFlow();
    verify(otherElement).getFailedJobRetryTimeCycleValue();
    verify(otherElement, atLeast(1)).getIoSpecification();
    verify(otherElement, atLeast(1)).getLoopCharacteristics();
    verify(otherElement).isForCompensation();
    verify(otherElement, atLeast(1)).getAttributes();
    verify(otherElement, atLeast(1)).getExtensionElements();
    verify(otherElement).getId();
    verify(otherElement).getDocumentation();
    verify(otherElement, atLeast(1)).getExecutionListeners();
    verify(otherElement).getName();
    verify(otherElement).isAsynchronous();
    verify(otherElement).isNotExclusive();
    assertEquals("42", task.getFailedJobRetryTimeCycleValue());
    assertEquals("42", task.getId());
    assertEquals("Default Flow", task.getDefaultFlow());
    assertEquals("Documentation", task.getDocumentation());
    assertEquals("Name", task.getName());
    List<DataAssociation> dataOutputAssociations = task.getDataOutputAssociations();
    assertEquals(1, dataOutputAssociations.size());
    DataAssociation getResult = dataOutputAssociations.get(0);
    assertNull(getResult.getId());
    IOSpecification ioSpecification = task.getIoSpecification();
    assertNull(ioSpecification.getId());
    MultiInstanceLoopCharacteristics loopCharacteristics = task.getLoopCharacteristics();
    assertNull(loopCharacteristics.getId());
    assertNull(getResult.getSourceRef());
    assertNull(getResult.getTargetRef());
    assertNull(getResult.getTransformation());
    assertNull(loopCharacteristics.getCompletionCondition());
    assertNull(loopCharacteristics.getElementIndexVariable());
    assertNull(loopCharacteristics.getElementVariable());
    assertNull(loopCharacteristics.getInputDataItem());
    assertNull(loopCharacteristics.getLoopCardinality());
    assertNull(loopCharacteristics.getLoopDataOutputRef());
    assertNull(loopCharacteristics.getOutputDataItem());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, ioSpecification.getXmlColumnNumber());
    assertEquals(0, loopCharacteristics.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertEquals(0, ioSpecification.getXmlRowNumber());
    assertEquals(0, loopCharacteristics.getXmlRowNumber());
    assertFalse(task.isExclusive());
    assertFalse(loopCharacteristics.isSequential());
    assertTrue(task.getDataInputAssociations().isEmpty());
    assertTrue(getResult.getAssignments().isEmpty());
    assertTrue(ioSpecification.getDataInputRefs().isEmpty());
    assertTrue(ioSpecification.getDataInputs().isEmpty());
    assertTrue(ioSpecification.getDataOutputRefs().isEmpty());
    assertTrue(ioSpecification.getDataOutputs().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(ioSpecification.getAttributes().isEmpty());
    assertTrue(loopCharacteristics.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(ioSpecification.getExtensionElements().isEmpty());
    assertTrue(loopCharacteristics.getExtensionElements().isEmpty());
    assertTrue(task.hasMultiInstanceLoopCharacteristics());
    assertTrue(task.isForCompensation());
    assertTrue(task.isAsynchronous());
    assertTrue(task.isNotExclusive());
  }

  /**
   * Method under test: {@link Task#setValues(Task)}
   */
  @Test
  public void testSetValues11() {
    // Arrange
    Task task = new Task();
    IOSpecification ioSpecification = mock(IOSpecification.class);
    IOSpecification ioSpecification2 = new IOSpecification();
    when(ioSpecification.clone()).thenReturn(ioSpecification2);
    BusinessRuleTask otherElement = mock(BusinessRuleTask.class);
    when(otherElement.isForCompensation()).thenReturn(true);
    when(otherElement.isAsynchronous()).thenReturn(true);
    when(otherElement.isNotExclusive()).thenReturn(true);
    when(otherElement.getDefaultFlow()).thenReturn("Default Flow");
    when(otherElement.getFailedJobRetryTimeCycleValue()).thenReturn("42");
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getDocumentation()).thenReturn("Documentation");
    when(otherElement.getName()).thenReturn("Name");
    when(otherElement.getBoundaryEvents()).thenReturn(new ArrayList<>());
    when(otherElement.getDataInputAssociations()).thenReturn(new ArrayList<>());
    when(otherElement.getDataOutputAssociations()).thenReturn(new ArrayList<>());
    when(otherElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherElement.getAttributes()).thenReturn(new HashMap<>());
    when(otherElement.getExtensionElements()).thenReturn(new HashMap<>());
    when(otherElement.getIoSpecification()).thenReturn(ioSpecification);
    when(otherElement.getLoopCharacteristics()).thenReturn(new MultiInstanceLoopCharacteristics());

    // Act
    task.setValues(otherElement);

    // Assert
    verify(otherElement).getBoundaryEvents();
    verify(otherElement, atLeast(1)).getDataInputAssociations();
    verify(otherElement, atLeast(1)).getDataOutputAssociations();
    verify(otherElement).getDefaultFlow();
    verify(otherElement).getFailedJobRetryTimeCycleValue();
    verify(otherElement, atLeast(1)).getIoSpecification();
    verify(otherElement, atLeast(1)).getLoopCharacteristics();
    verify(otherElement).isForCompensation();
    verify(otherElement, atLeast(1)).getAttributes();
    verify(otherElement, atLeast(1)).getExtensionElements();
    verify(otherElement).getId();
    verify(otherElement).getDocumentation();
    verify(otherElement, atLeast(1)).getExecutionListeners();
    verify(otherElement).getName();
    verify(otherElement).isAsynchronous();
    verify(otherElement).isNotExclusive();
    verify(ioSpecification).clone();
    assertEquals("42", task.getFailedJobRetryTimeCycleValue());
    assertEquals("42", task.getId());
    assertEquals("Default Flow", task.getDefaultFlow());
    assertEquals("Documentation", task.getDocumentation());
    assertEquals("Name", task.getName());
    MultiInstanceLoopCharacteristics loopCharacteristics = task.getLoopCharacteristics();
    assertNull(loopCharacteristics.getId());
    assertNull(loopCharacteristics.getCompletionCondition());
    assertNull(loopCharacteristics.getElementIndexVariable());
    assertNull(loopCharacteristics.getElementVariable());
    assertNull(loopCharacteristics.getInputDataItem());
    assertNull(loopCharacteristics.getLoopCardinality());
    assertNull(loopCharacteristics.getLoopDataOutputRef());
    assertNull(loopCharacteristics.getOutputDataItem());
    assertEquals(0, loopCharacteristics.getXmlColumnNumber());
    assertEquals(0, loopCharacteristics.getXmlRowNumber());
    assertFalse(task.isExclusive());
    assertFalse(loopCharacteristics.isSequential());
    assertTrue(task.getDataInputAssociations().isEmpty());
    assertTrue(task.getDataOutputAssociations().isEmpty());
    assertTrue(loopCharacteristics.getAttributes().isEmpty());
    assertTrue(loopCharacteristics.getExtensionElements().isEmpty());
    assertTrue(task.hasMultiInstanceLoopCharacteristics());
    assertTrue(task.isForCompensation());
    assertTrue(task.isAsynchronous());
    assertTrue(task.isNotExclusive());
    assertSame(ioSpecification2, task.getIoSpecification());
  }

  /**
   * Method under test: {@link Task#setValues(Task)}
   */
  @Test
  public void testSetValues12() {
    // Arrange
    Task task = new Task();
    IOSpecification ioSpecification = mock(IOSpecification.class);
    IOSpecification ioSpecification2 = new IOSpecification();
    when(ioSpecification.clone()).thenReturn(ioSpecification2);
    MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics = mock(MultiInstanceLoopCharacteristics.class);
    MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics2 = new MultiInstanceLoopCharacteristics();
    when(multiInstanceLoopCharacteristics.clone()).thenReturn(multiInstanceLoopCharacteristics2);
    BusinessRuleTask otherElement = mock(BusinessRuleTask.class);
    when(otherElement.isForCompensation()).thenReturn(true);
    when(otherElement.isAsynchronous()).thenReturn(true);
    when(otherElement.isNotExclusive()).thenReturn(true);
    when(otherElement.getDefaultFlow()).thenReturn("Default Flow");
    when(otherElement.getFailedJobRetryTimeCycleValue()).thenReturn("42");
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getDocumentation()).thenReturn("Documentation");
    when(otherElement.getName()).thenReturn("Name");
    when(otherElement.getBoundaryEvents()).thenReturn(new ArrayList<>());
    when(otherElement.getDataInputAssociations()).thenReturn(new ArrayList<>());
    when(otherElement.getDataOutputAssociations()).thenReturn(new ArrayList<>());
    when(otherElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherElement.getAttributes()).thenReturn(new HashMap<>());
    when(otherElement.getExtensionElements()).thenReturn(new HashMap<>());
    when(otherElement.getIoSpecification()).thenReturn(ioSpecification);
    when(otherElement.getLoopCharacteristics()).thenReturn(multiInstanceLoopCharacteristics);

    // Act
    task.setValues(otherElement);

    // Assert
    verify(otherElement).getBoundaryEvents();
    verify(otherElement, atLeast(1)).getDataInputAssociations();
    verify(otherElement, atLeast(1)).getDataOutputAssociations();
    verify(otherElement).getDefaultFlow();
    verify(otherElement).getFailedJobRetryTimeCycleValue();
    verify(otherElement, atLeast(1)).getIoSpecification();
    verify(otherElement, atLeast(1)).getLoopCharacteristics();
    verify(otherElement).isForCompensation();
    verify(otherElement, atLeast(1)).getAttributes();
    verify(otherElement, atLeast(1)).getExtensionElements();
    verify(otherElement).getId();
    verify(otherElement).getDocumentation();
    verify(otherElement, atLeast(1)).getExecutionListeners();
    verify(otherElement).getName();
    verify(otherElement).isAsynchronous();
    verify(otherElement).isNotExclusive();
    verify(ioSpecification).clone();
    verify(multiInstanceLoopCharacteristics).clone();
    assertEquals("42", task.getFailedJobRetryTimeCycleValue());
    assertEquals("42", task.getId());
    assertEquals("Default Flow", task.getDefaultFlow());
    assertEquals("Documentation", task.getDocumentation());
    assertEquals("Name", task.getName());
    assertFalse(task.isExclusive());
    assertTrue(task.getDataInputAssociations().isEmpty());
    assertTrue(task.getDataOutputAssociations().isEmpty());
    assertTrue(task.hasMultiInstanceLoopCharacteristics());
    assertTrue(task.isForCompensation());
    assertTrue(task.isAsynchronous());
    assertTrue(task.isNotExclusive());
    assertSame(ioSpecification2, task.getIoSpecification());
    assertSame(multiInstanceLoopCharacteristics2, task.getLoopCharacteristics());
  }

  /**
   * Method under test: {@link Task#setValues(Task)}
   */
  @Test
  public void testSetValues13() {
    // Arrange
    Task task = new Task();

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(null);
    ioSpecification.setDataOutputs(null);
    DataAssociation dataAssociation = mock(DataAssociation.class);
    when(dataAssociation.clone()).thenReturn(new DataAssociation());

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(dataAssociation);

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    Task otherElement = new Task();
    otherElement.setIoSpecification(ioSpecification);
    otherElement.setLoopCharacteristics(null);
    otherElement.setDataInputAssociations(null);
    otherElement.setDataOutputAssociations(dataOutputAssociations);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    task.setValues(otherElement);

    // Assert
    verify(dataAssociation).clone();
    assertNull(otherElement.getDefaultFlow());
    assertNull(otherElement.getFailedJobRetryTimeCycleValue());
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getDataInputAssociations());
    assertNull(otherElement.getLoopCharacteristics());
    List<DataAssociation> dataOutputAssociations2 = otherElement.getDataOutputAssociations();
    assertEquals(1, dataOutputAssociations2.size());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertTrue(otherElement.isExclusive());
    assertSame(dataOutputAssociations, dataOutputAssociations2);
    assertSame(ioSpecification, otherElement.getIoSpecification());
  }

  /**
   * Method under test: {@link Task#setValues(Task)}
   */
  @Test
  public void testSetValues14() {
    // Arrange
    Task task = new Task();

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(null);
    ioSpecification.setDataOutputs(null);
    DataAssociation dataAssociation = mock(DataAssociation.class);
    when(dataAssociation.clone()).thenReturn(new DataAssociation());

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(dataAssociation);

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    Task otherElement = new Task();
    otherElement.setIoSpecification(ioSpecification);
    otherElement.setLoopCharacteristics(null);
    otherElement.setDataInputAssociations(dataInputAssociations);
    otherElement.setDataOutputAssociations(null);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    task.setValues(otherElement);

    // Assert
    verify(dataAssociation).clone();
    assertNull(otherElement.getDefaultFlow());
    assertNull(otherElement.getFailedJobRetryTimeCycleValue());
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getDataOutputAssociations());
    assertNull(otherElement.getLoopCharacteristics());
    List<DataAssociation> dataInputAssociations2 = otherElement.getDataInputAssociations();
    assertEquals(1, dataInputAssociations2.size());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertTrue(otherElement.isExclusive());
    assertSame(dataInputAssociations, dataInputAssociations2);
    assertSame(ioSpecification, otherElement.getIoSpecification());
  }

  /**
   * Method under test: default or parameterless constructor of {@link Task}
   */
  @Test
  public void testNewTask() {
    // Arrange and Act
    Task actualTask = new Task();

    // Assert
    assertNull(actualTask.getBehavior());
    assertNull(actualTask.getDefaultFlow());
    assertNull(actualTask.getFailedJobRetryTimeCycleValue());
    assertNull(actualTask.getId());
    assertNull(actualTask.getDocumentation());
    assertNull(actualTask.getName());
    assertNull(actualTask.getParentContainer());
    assertNull(actualTask.getIoSpecification());
    assertNull(actualTask.getLoopCharacteristics());
    assertEquals(0, actualTask.getXmlColumnNumber());
    assertEquals(0, actualTask.getXmlRowNumber());
    assertFalse(actualTask.isForCompensation());
    assertFalse(actualTask.isAsynchronous());
    assertFalse(actualTask.isNotExclusive());
    assertTrue(actualTask.getBoundaryEvents().isEmpty());
    assertTrue(actualTask.getDataInputAssociations().isEmpty());
    assertTrue(actualTask.getDataOutputAssociations().isEmpty());
    assertTrue(actualTask.getMapExceptions().isEmpty());
    assertTrue(actualTask.getExecutionListeners().isEmpty());
    assertTrue(actualTask.getIncomingFlows().isEmpty());
    assertTrue(actualTask.getOutgoingFlows().isEmpty());
    assertTrue(actualTask.getAttributes().isEmpty());
    assertTrue(actualTask.getExtensionElements().isEmpty());
  }
}
