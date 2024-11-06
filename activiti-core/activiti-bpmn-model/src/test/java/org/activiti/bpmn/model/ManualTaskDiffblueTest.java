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
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.mockito.Mockito;

public class ManualTaskDiffblueTest {
  /**
   * Test {@link ManualTask#clone()}.
   * <ul>
   *   <li>Given {@link ManualTask} (default constructor) ForCompensation is
   * {@code true}.</li>
   *   <li>Then return ForCompensation.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManualTask#clone()}
   */
  @Test
  public void testClone_givenManualTaskForCompensationIsTrue_thenReturnForCompensation() {
    // Arrange
    ManualTask manualTask = new ManualTask();
    manualTask.setForCompensation(true);

    // Act
    ManualTask actualCloneResult = manualTask.clone();

    // Assert
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
    assertTrue(actualCloneResult.getDataOutputAssociations().isEmpty());
    assertTrue(actualCloneResult.isForCompensation());
  }

  /**
   * Test {@link ManualTask#clone()}.
   * <ul>
   *   <li>Given {@link ManualTask} (default constructor).</li>
   *   <li>Then return not ForCompensation.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManualTask#clone()}
   */
  @Test
  public void testClone_givenManualTask_thenReturnNotForCompensation() {
    // Arrange and Act
    ManualTask actualCloneResult = (new ManualTask()).clone();

    // Assert
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertFalse(actualCloneResult.isForCompensation());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
    assertTrue(actualCloneResult.getDataOutputAssociations().isEmpty());
  }

  /**
   * Test {@link ManualTask#clone()}.
   * <ul>
   *   <li>Then return BoundaryEvents is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManualTask#clone()}
   */
  @Test
  public void testClone_thenReturnBoundaryEventsIsArrayList() {
    // Arrange
    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    ManualTask manualTask = new ManualTask();
    manualTask.setLoopCharacteristics(null);
    manualTask.setIoSpecification(null);
    manualTask.setDataInputAssociations(null);
    manualTask.setDataOutputAssociations(null);
    manualTask.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    assertEquals(boundaryEvents, manualTask.clone().getBoundaryEvents());
  }

  /**
   * Test {@link ManualTask#clone()}.
   * <ul>
   *   <li>Then return DataInputAssociations size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManualTask#clone()}
   */
  @Test
  public void testClone_thenReturnDataInputAssociationsSizeIsOne() {
    // Arrange
    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    ManualTask manualTask = new ManualTask();
    manualTask.setLoopCharacteristics(null);
    manualTask.setIoSpecification(null);
    manualTask.setDataInputAssociations(dataInputAssociations);
    manualTask.setDataOutputAssociations(null);
    manualTask.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    List<DataAssociation> dataInputAssociations2 = manualTask.clone().getDataInputAssociations();
    assertEquals(1, dataInputAssociations2.size());
    DataAssociation getResult = dataInputAssociations2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getSourceRef());
    assertNull(getResult.getTargetRef());
    assertNull(getResult.getTransformation());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAssignments().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link ManualTask#clone()}.
   * <ul>
   *   <li>Then return DataOutputAssociations size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManualTask#clone()}
   */
  @Test
  public void testClone_thenReturnDataOutputAssociationsSizeIsOne() {
    // Arrange
    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    ManualTask manualTask = new ManualTask();
    manualTask.setLoopCharacteristics(null);
    manualTask.setIoSpecification(null);
    manualTask.setDataInputAssociations(null);
    manualTask.setDataOutputAssociations(dataOutputAssociations);
    manualTask.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    List<DataAssociation> dataOutputAssociations2 = manualTask.clone().getDataOutputAssociations();
    assertEquals(1, dataOutputAssociations2.size());
    DataAssociation getResult = dataOutputAssociations2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getSourceRef());
    assertNull(getResult.getTargetRef());
    assertNull(getResult.getTransformation());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAssignments().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link ManualTask#clone()}.
   * <ul>
   *   <li>Then return IoSpecification Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManualTask#clone()}
   */
  @Test
  public void testClone_thenReturnIoSpecificationIdIsNull() {
    // Arrange
    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    ManualTask manualTask = new ManualTask();
    manualTask.setLoopCharacteristics(null);
    manualTask.setIoSpecification(new IOSpecification());
    manualTask.setDataInputAssociations(null);
    manualTask.setDataOutputAssociations(null);
    manualTask.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    IOSpecification ioSpecification = manualTask.clone().getIoSpecification();
    assertNull(ioSpecification.getId());
    assertEquals(0, ioSpecification.getXmlColumnNumber());
    assertEquals(0, ioSpecification.getXmlRowNumber());
    assertTrue(ioSpecification.getDataInputRefs().isEmpty());
    assertTrue(ioSpecification.getDataInputs().isEmpty());
    assertTrue(ioSpecification.getDataOutputRefs().isEmpty());
    assertTrue(ioSpecification.getDataOutputs().isEmpty());
    assertTrue(ioSpecification.getAttributes().isEmpty());
    assertTrue(ioSpecification.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link ManualTask#clone()}.
   * <ul>
   *   <li>Then return LoopCharacteristics Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManualTask#clone()}
   */
  @Test
  public void testClone_thenReturnLoopCharacteristicsIdIsNull() {
    // Arrange
    ManualTask manualTask = new ManualTask();
    manualTask.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());

    // Act
    ManualTask actualCloneResult = manualTask.clone();

    // Assert
    MultiInstanceLoopCharacteristics loopCharacteristics = actualCloneResult.getLoopCharacteristics();
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
    assertFalse(loopCharacteristics.isSequential());
    assertTrue(loopCharacteristics.getAttributes().isEmpty());
    assertTrue(loopCharacteristics.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.hasMultiInstanceLoopCharacteristics());
  }

  /**
   * Test {@link ManualTask#setValues(ManualTask)} with {@code ManualTask}.
   * <ul>
   *   <li>Then calls {@link IOSpecification#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ManualTask#setValues(ManualTask)}
   */
  @Test
  public void testSetValuesWithManualTask_thenCallsClone() {
    // Arrange
    ManualTask manualTask = new ManualTask();
    IOSpecification ioSpecification = mock(IOSpecification.class);
    when(ioSpecification.clone()).thenReturn(new IOSpecification());
    doNothing().when(ioSpecification).setDataInputs(Mockito.<List<DataSpec>>any());
    doNothing().when(ioSpecification).setDataOutputs(Mockito.<List<DataSpec>>any());
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
    manualTask.setValues(otherElement);

    // Assert
    verify(ioSpecification).clone();
    verify(ioSpecification).setDataInputs(isNull());
    verify(ioSpecification).setDataOutputs(isNull());
  }

  /**
   * Test new {@link ManualTask} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link ManualTask}
   */
  @Test
  public void testNewManualTask() {
    // Arrange and Act
    ManualTask actualManualTask = new ManualTask();

    // Assert
    assertNull(actualManualTask.getBehavior());
    assertNull(actualManualTask.getDefaultFlow());
    assertNull(actualManualTask.getFailedJobRetryTimeCycleValue());
    assertNull(actualManualTask.getId());
    assertNull(actualManualTask.getDocumentation());
    assertNull(actualManualTask.getName());
    assertNull(actualManualTask.getParentContainer());
    assertNull(actualManualTask.getIoSpecification());
    assertNull(actualManualTask.getLoopCharacteristics());
    assertEquals(0, actualManualTask.getXmlColumnNumber());
    assertEquals(0, actualManualTask.getXmlRowNumber());
    assertFalse(actualManualTask.isForCompensation());
    assertFalse(actualManualTask.isAsynchronous());
    assertFalse(actualManualTask.isNotExclusive());
    assertTrue(actualManualTask.getBoundaryEvents().isEmpty());
    assertTrue(actualManualTask.getDataInputAssociations().isEmpty());
    assertTrue(actualManualTask.getDataOutputAssociations().isEmpty());
    assertTrue(actualManualTask.getMapExceptions().isEmpty());
    assertTrue(actualManualTask.getExecutionListeners().isEmpty());
    assertTrue(actualManualTask.getIncomingFlows().isEmpty());
    assertTrue(actualManualTask.getOutgoingFlows().isEmpty());
    assertTrue(actualManualTask.getAttributes().isEmpty());
    assertTrue(actualManualTask.getExtensionElements().isEmpty());
  }
}
