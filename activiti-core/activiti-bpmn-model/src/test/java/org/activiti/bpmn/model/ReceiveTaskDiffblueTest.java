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
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ReceiveTaskDiffblueTest {
  /**
   * Method under test: {@link ReceiveTask#clone()}
   */
  @Test
  public void testClone() {
    // Arrange and Act
    ReceiveTask actualCloneResult = (new ReceiveTask()).clone();

    // Assert
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getDefaultFlow());
    assertNull(actualCloneResult.getFailedJobRetryTimeCycleValue());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertFalse(actualCloneResult.isForCompensation());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
    assertTrue(actualCloneResult.getDataOutputAssociations().isEmpty());
    assertTrue(actualCloneResult.getMapExceptions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Method under test: {@link ReceiveTask#clone()}
   */
  @Test
  public void testClone2() {
    // Arrange
    ReceiveTask receiveTask = new ReceiveTask();
    receiveTask.setForCompensation(true);

    // Act
    ReceiveTask actualCloneResult = receiveTask.clone();

    // Assert
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getDefaultFlow());
    assertNull(actualCloneResult.getFailedJobRetryTimeCycleValue());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
    assertTrue(actualCloneResult.getDataOutputAssociations().isEmpty());
    assertTrue(actualCloneResult.getMapExceptions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isForCompensation());
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Method under test: {@link ReceiveTask#clone()}
   */
  @Test
  public void testClone3() {
    // Arrange
    ReceiveTask receiveTask = new ReceiveTask();
    receiveTask.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());

    // Act
    ReceiveTask actualCloneResult = receiveTask.clone();

    // Assert
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getDefaultFlow());
    assertNull(actualCloneResult.getFailedJobRetryTimeCycleValue());
    MultiInstanceLoopCharacteristics loopCharacteristics = actualCloneResult.getLoopCharacteristics();
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
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, loopCharacteristics.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, loopCharacteristics.getXmlRowNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.isForCompensation());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertFalse(loopCharacteristics.isSequential());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
    assertTrue(actualCloneResult.getDataOutputAssociations().isEmpty());
    assertTrue(actualCloneResult.getMapExceptions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(loopCharacteristics.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(loopCharacteristics.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Method under test: {@link ReceiveTask#clone()}
   */
  @Test
  public void testClone4() {
    // Arrange
    ReceiveTask receiveTask = new ReceiveTask();
    receiveTask.setIoSpecification(new IOSpecification());

    // Act
    ReceiveTask actualCloneResult = receiveTask.clone();

    // Assert
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getDefaultFlow());
    assertNull(actualCloneResult.getFailedJobRetryTimeCycleValue());
    IOSpecification ioSpecification = actualCloneResult.getIoSpecification();
    assertNull(ioSpecification.getId());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, ioSpecification.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, ioSpecification.getXmlRowNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertFalse(actualCloneResult.isForCompensation());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
    assertTrue(actualCloneResult.getDataOutputAssociations().isEmpty());
    assertTrue(actualCloneResult.getMapExceptions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(ioSpecification.getDataInputRefs().isEmpty());
    assertTrue(ioSpecification.getDataInputs().isEmpty());
    assertTrue(ioSpecification.getDataOutputRefs().isEmpty());
    assertTrue(ioSpecification.getDataOutputs().isEmpty());
    assertTrue(ioSpecification.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(ioSpecification.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Method under test: {@link ReceiveTask#setValues(ManualTask)}
   */
  @Test
  public void testSetValues() {
    // Arrange
    ReceiveTask receiveTask = new ReceiveTask();

    // Act
    receiveTask.setValues(new ManualTask());

    // Assert
    assertNull(receiveTask.getIoSpecification());
    assertNull(receiveTask.getLoopCharacteristics());
    assertFalse(receiveTask.hasMultiInstanceLoopCharacteristics());
    assertFalse(receiveTask.isForCompensation());
    assertTrue(receiveTask.getBoundaryEvents().isEmpty());
    assertTrue(receiveTask.getDataInputAssociations().isEmpty());
    assertTrue(receiveTask.getDataOutputAssociations().isEmpty());
  }

  /**
   * Method under test: {@link ReceiveTask#setValues(ManualTask)}
   */
  @Test
  public void testSetValues2() {
    // Arrange
    ReceiveTask receiveTask = new ReceiveTask();

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(null);
    ioSpecification.setDataOutputs(null);

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    ManualTask otherElement = new ManualTask();
    otherElement.setIoSpecification(ioSpecification);
    otherElement.setLoopCharacteristics(null);
    otherElement.setDataInputAssociations(null);
    otherElement.setDataOutputAssociations(null);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    receiveTask.setValues(otherElement);

    // Assert
    IOSpecification ioSpecification2 = receiveTask.getIoSpecification();
    assertNull(ioSpecification2.getId());
    assertNull(receiveTask.getLoopCharacteristics());
    assertEquals(0, ioSpecification2.getXmlColumnNumber());
    assertEquals(0, ioSpecification2.getXmlRowNumber());
    assertFalse(receiveTask.hasMultiInstanceLoopCharacteristics());
    assertFalse(receiveTask.isForCompensation());
    assertTrue(receiveTask.getDataInputAssociations().isEmpty());
    assertTrue(receiveTask.getDataOutputAssociations().isEmpty());
    assertTrue(ioSpecification2.getDataInputRefs().isEmpty());
    assertTrue(ioSpecification2.getDataInputs().isEmpty());
    assertTrue(ioSpecification2.getDataOutputRefs().isEmpty());
    assertTrue(ioSpecification2.getDataOutputs().isEmpty());
    assertTrue(ioSpecification2.getAttributes().isEmpty());
    assertTrue(ioSpecification2.getExtensionElements().isEmpty());
    assertEquals(boundaryEvents, receiveTask.getBoundaryEvents());
  }

  /**
   * Method under test: {@link ReceiveTask#setValues(ManualTask)}
   */
  @Test
  public void testSetValues3() {
    // Arrange
    ReceiveTask receiveTask = new ReceiveTask();

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(null);
    ioSpecification.setDataOutputs(null);

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    ManualTask otherElement = new ManualTask();
    otherElement.setIoSpecification(ioSpecification);
    otherElement.setLoopCharacteristics(null);
    otherElement.setDataInputAssociations(null);
    otherElement.setDataOutputAssociations(dataOutputAssociations);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    receiveTask.setValues(otherElement);

    // Assert
    List<DataAssociation> dataOutputAssociations2 = receiveTask.getDataOutputAssociations();
    assertEquals(1, dataOutputAssociations2.size());
    DataAssociation getResult = dataOutputAssociations2.get(0);
    assertNull(getResult.getId());
    IOSpecification ioSpecification2 = receiveTask.getIoSpecification();
    assertNull(ioSpecification2.getId());
    assertNull(getResult.getSourceRef());
    assertNull(getResult.getTargetRef());
    assertNull(getResult.getTransformation());
    assertNull(receiveTask.getLoopCharacteristics());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, ioSpecification2.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertEquals(0, ioSpecification2.getXmlRowNumber());
    assertFalse(receiveTask.hasMultiInstanceLoopCharacteristics());
    assertFalse(receiveTask.isForCompensation());
    assertTrue(receiveTask.getDataInputAssociations().isEmpty());
    assertTrue(getResult.getAssignments().isEmpty());
    assertTrue(ioSpecification2.getDataInputRefs().isEmpty());
    assertTrue(ioSpecification2.getDataInputs().isEmpty());
    assertTrue(ioSpecification2.getDataOutputRefs().isEmpty());
    assertTrue(ioSpecification2.getDataOutputs().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(ioSpecification2.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(ioSpecification2.getExtensionElements().isEmpty());
    assertEquals(boundaryEvents, receiveTask.getBoundaryEvents());
  }

  /**
   * Method under test: {@link ReceiveTask#setValues(ManualTask)}
   */
  @Test
  public void testSetValues4() {
    // Arrange
    ReceiveTask receiveTask = new ReceiveTask();

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(null);
    ioSpecification.setDataOutputs(null);

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    ManualTask otherElement = new ManualTask();
    otherElement.setIoSpecification(ioSpecification);
    otherElement.setLoopCharacteristics(null);
    otherElement.setDataInputAssociations(dataInputAssociations);
    otherElement.setDataOutputAssociations(null);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    receiveTask.setValues(otherElement);

    // Assert
    List<DataAssociation> dataInputAssociations2 = receiveTask.getDataInputAssociations();
    assertEquals(1, dataInputAssociations2.size());
    DataAssociation getResult = dataInputAssociations2.get(0);
    assertNull(getResult.getId());
    IOSpecification ioSpecification2 = receiveTask.getIoSpecification();
    assertNull(ioSpecification2.getId());
    assertNull(getResult.getSourceRef());
    assertNull(getResult.getTargetRef());
    assertNull(getResult.getTransformation());
    assertNull(receiveTask.getLoopCharacteristics());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, ioSpecification2.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertEquals(0, ioSpecification2.getXmlRowNumber());
    assertFalse(receiveTask.hasMultiInstanceLoopCharacteristics());
    assertFalse(receiveTask.isForCompensation());
    assertTrue(receiveTask.getDataOutputAssociations().isEmpty());
    assertTrue(getResult.getAssignments().isEmpty());
    assertTrue(ioSpecification2.getDataInputRefs().isEmpty());
    assertTrue(ioSpecification2.getDataInputs().isEmpty());
    assertTrue(ioSpecification2.getDataOutputRefs().isEmpty());
    assertTrue(ioSpecification2.getDataOutputs().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(ioSpecification2.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(ioSpecification2.getExtensionElements().isEmpty());
    assertEquals(boundaryEvents, receiveTask.getBoundaryEvents());
  }

  /**
   * Method under test: {@link ReceiveTask#setValues(ManualTask)}
   */
  @Test
  public void testSetValues5() {
    // Arrange
    ReceiveTask receiveTask = new ReceiveTask();

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(null);
    ioSpecification.setDataOutputs(null);

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    ManualTask otherElement = new ManualTask();
    otherElement.setIoSpecification(ioSpecification);
    otherElement.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    otherElement.setDataInputAssociations(null);
    otherElement.setDataOutputAssociations(null);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    receiveTask.setValues(otherElement);

    // Assert
    IOSpecification ioSpecification2 = receiveTask.getIoSpecification();
    assertNull(ioSpecification2.getId());
    MultiInstanceLoopCharacteristics loopCharacteristics = receiveTask.getLoopCharacteristics();
    assertNull(loopCharacteristics.getId());
    assertNull(loopCharacteristics.getCompletionCondition());
    assertNull(loopCharacteristics.getElementIndexVariable());
    assertNull(loopCharacteristics.getElementVariable());
    assertNull(loopCharacteristics.getInputDataItem());
    assertNull(loopCharacteristics.getLoopCardinality());
    assertNull(loopCharacteristics.getLoopDataOutputRef());
    assertNull(loopCharacteristics.getOutputDataItem());
    assertEquals(0, ioSpecification2.getXmlColumnNumber());
    assertEquals(0, loopCharacteristics.getXmlColumnNumber());
    assertEquals(0, ioSpecification2.getXmlRowNumber());
    assertEquals(0, loopCharacteristics.getXmlRowNumber());
    assertFalse(receiveTask.isForCompensation());
    assertFalse(loopCharacteristics.isSequential());
    assertTrue(receiveTask.getDataInputAssociations().isEmpty());
    assertTrue(receiveTask.getDataOutputAssociations().isEmpty());
    assertTrue(ioSpecification2.getDataInputRefs().isEmpty());
    assertTrue(ioSpecification2.getDataInputs().isEmpty());
    assertTrue(ioSpecification2.getDataOutputRefs().isEmpty());
    assertTrue(ioSpecification2.getDataOutputs().isEmpty());
    assertTrue(ioSpecification2.getAttributes().isEmpty());
    assertTrue(loopCharacteristics.getAttributes().isEmpty());
    assertTrue(ioSpecification2.getExtensionElements().isEmpty());
    assertTrue(loopCharacteristics.getExtensionElements().isEmpty());
    assertTrue(receiveTask.hasMultiInstanceLoopCharacteristics());
    assertEquals(boundaryEvents, receiveTask.getBoundaryEvents());
  }

  /**
   * Method under test: {@link ReceiveTask#setValues(ManualTask)}
   */
  @Test
  public void testSetValues6() {
    // Arrange
    ReceiveTask receiveTask = new ReceiveTask();

    ManualTask otherElement = new ManualTask();
    otherElement.setForCompensation(true);

    // Act
    receiveTask.setValues(otherElement);

    // Assert
    assertNull(receiveTask.getIoSpecification());
    assertNull(receiveTask.getLoopCharacteristics());
    assertFalse(receiveTask.hasMultiInstanceLoopCharacteristics());
    assertTrue(receiveTask.getBoundaryEvents().isEmpty());
    assertTrue(receiveTask.getDataInputAssociations().isEmpty());
    assertTrue(receiveTask.getDataOutputAssociations().isEmpty());
    assertTrue(receiveTask.isForCompensation());
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link ReceiveTask}
   */
  @Test
  public void testNewReceiveTask() {
    // Arrange and Act
    ReceiveTask actualReceiveTask = new ReceiveTask();

    // Assert
    assertNull(actualReceiveTask.getBehavior());
    assertNull(actualReceiveTask.getDefaultFlow());
    assertNull(actualReceiveTask.getFailedJobRetryTimeCycleValue());
    assertNull(actualReceiveTask.getId());
    assertNull(actualReceiveTask.getDocumentation());
    assertNull(actualReceiveTask.getName());
    assertNull(actualReceiveTask.getParentContainer());
    assertNull(actualReceiveTask.getIoSpecification());
    assertNull(actualReceiveTask.getLoopCharacteristics());
    assertEquals(0, actualReceiveTask.getXmlColumnNumber());
    assertEquals(0, actualReceiveTask.getXmlRowNumber());
    assertFalse(actualReceiveTask.isForCompensation());
    assertFalse(actualReceiveTask.isAsynchronous());
    assertFalse(actualReceiveTask.isNotExclusive());
    assertTrue(actualReceiveTask.getBoundaryEvents().isEmpty());
    assertTrue(actualReceiveTask.getDataInputAssociations().isEmpty());
    assertTrue(actualReceiveTask.getDataOutputAssociations().isEmpty());
    assertTrue(actualReceiveTask.getMapExceptions().isEmpty());
    assertTrue(actualReceiveTask.getExecutionListeners().isEmpty());
    assertTrue(actualReceiveTask.getIncomingFlows().isEmpty());
    assertTrue(actualReceiveTask.getOutgoingFlows().isEmpty());
    assertTrue(actualReceiveTask.getAttributes().isEmpty());
    assertTrue(actualReceiveTask.getExtensionElements().isEmpty());
  }
}
