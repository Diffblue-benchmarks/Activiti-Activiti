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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ReceiveTaskDiffblueTest {
  /**
   * Test {@link ReceiveTask#clone()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link BoundaryEvent} (default constructor).
   *   <li>Then return BoundaryEvents size is one.
   * </ul>
   *
   * <p>Method under test: {@link ReceiveTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ReceiveTask ReceiveTask.clone()"})
  public void testClone_givenArrayListAddBoundaryEvent_thenReturnBoundaryEventsSizeIsOne() {
    // Arrange
    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvents.add(boundaryEvent);

    ReceiveTask receiveTask = new ReceiveTask();
    receiveTask.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    List<BoundaryEvent> boundaryEvents2 = receiveTask.clone().getBoundaryEvents();
    assertEquals(1, boundaryEvents2.size());
    assertSame(boundaryEvent, boundaryEvents2.get(0));
  }

  /**
   * Test {@link ReceiveTask#clone()}.
   *
   * <ul>
   *   <li>Given {@link ReceiveTask} (default constructor) ForCompensation is {@code true}.
   *   <li>Then return ForCompensation.
   * </ul>
   *
   * <p>Method under test: {@link ReceiveTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ReceiveTask ReceiveTask.clone()"})
  public void testClone_givenReceiveTaskForCompensationIsTrue_thenReturnForCompensation() {
    // Arrange
    ReceiveTask receiveTask = new ReceiveTask();
    receiveTask.setForCompensation(true);

    // Act
    ReceiveTask actualCloneResult = receiveTask.clone();

    // Assert
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
    assertTrue(actualCloneResult.isForCompensation());
  }

  /**
   * Test {@link ReceiveTask#clone()}.
   *
   * <ul>
   *   <li>Given {@link ReceiveTask} (default constructor).
   *   <li>Then return not ForCompensation.
   * </ul>
   *
   * <p>Method under test: {@link ReceiveTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ReceiveTask ReceiveTask.clone()"})
  public void testClone_givenReceiveTask_thenReturnNotForCompensation() {
    // Arrange and Act
    ReceiveTask actualCloneResult = new ReceiveTask().clone();

    // Assert
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertFalse(actualCloneResult.isForCompensation());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
  }

  /**
   * Test {@link ReceiveTask#clone()}.
   *
   * <ul>
   *   <li>Then return DataInputAssociations size is one.
   * </ul>
   *
   * <p>Method under test: {@link ReceiveTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ReceiveTask ReceiveTask.clone()"})
  public void testClone_thenReturnDataInputAssociationsSizeIsOne() {
    // Arrange
    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(new DataAssociation());

    ReceiveTask receiveTask = new ReceiveTask();
    receiveTask.setDataInputAssociations(dataInputAssociations);

    // Act and Assert
    List<DataAssociation> dataInputAssociations2 = receiveTask.clone().getDataInputAssociations();
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
   * Test {@link ReceiveTask#clone()}.
   *
   * <ul>
   *   <li>Then return IoSpecification Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ReceiveTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ReceiveTask ReceiveTask.clone()"})
  public void testClone_thenReturnIoSpecificationIdIsNull() {
    // Arrange
    ReceiveTask receiveTask = new ReceiveTask();
    receiveTask.setIoSpecification(new IOSpecification());

    // Act and Assert
    IOSpecification ioSpecification = receiveTask.clone().getIoSpecification();
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
   * Test {@link ReceiveTask#clone()}.
   *
   * <ul>
   *   <li>Then return LoopCharacteristics Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ReceiveTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ReceiveTask ReceiveTask.clone()"})
  public void testClone_thenReturnLoopCharacteristicsIdIsNull() {
    // Arrange
    ReceiveTask receiveTask = new ReceiveTask();
    receiveTask.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());

    // Act
    ReceiveTask actualCloneResult = receiveTask.clone();

    // Assert
    MultiInstanceLoopCharacteristics loopCharacteristics =
        actualCloneResult.getLoopCharacteristics();
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
   * Test {@link ReceiveTask#setValues(ManualTask)} with {@code ManualTask}.
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>Then {@link ReceiveTask} (default constructor) ForCompensation.
   * </ul>
   *
   * <p>Method under test: {@link ReceiveTask#setValues(ManualTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReceiveTask.setValues(ManualTask)"})
  public void testSetValuesWithManualTask_givenTrue_thenReceiveTaskForCompensation() {
    // Arrange
    ReceiveTask receiveTask = new ReceiveTask();

    ManualTask otherElement = new ManualTask();
    otherElement.setForCompensation(true);

    // Act
    receiveTask.setValues(otherElement);

    // Assert
    assertTrue(receiveTask.getDataInputAssociations().isEmpty());
    assertTrue(receiveTask.getDataOutputAssociations().isEmpty());
    assertTrue(receiveTask.isForCompensation());
  }

  /**
   * Test {@link ReceiveTask#setValues(ManualTask)} with {@code ManualTask}.
   *
   * <ul>
   *   <li>Then {@link ReceiveTask} (default constructor) DataInputAssociations size is one.
   * </ul>
   *
   * <p>Method under test: {@link ReceiveTask#setValues(ManualTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReceiveTask.setValues(ManualTask)"})
  public void testSetValuesWithManualTask_thenReceiveTaskDataInputAssociationsSizeIsOne() {
    // Arrange
    ReceiveTask receiveTask = new ReceiveTask();

    ArrayList<DataSpec> dataInputs = new ArrayList<>();
    dataInputs.add(new DataSpec());

    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(new DataSpec());

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(dataInputs);
    ioSpecification.setDataOutputs(dataOutputs);

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(new DataAssociation());

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    ManualTask otherElement = new ManualTask();
    otherElement.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    otherElement.setIoSpecification(ioSpecification);
    otherElement.setDataInputAssociations(dataInputAssociations);
    otherElement.setDataOutputAssociations(dataOutputAssociations);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    receiveTask.setValues(otherElement);

    // Assert
    List<DataAssociation> dataInputAssociations2 = receiveTask.getDataInputAssociations();
    assertEquals(1, dataInputAssociations2.size());
    DataAssociation getResult = dataInputAssociations2.get(0);
    assertNull(getResult.getId());
    List<DataAssociation> dataOutputAssociations2 = receiveTask.getDataOutputAssociations();
    assertEquals(1, dataOutputAssociations2.size());
    DataAssociation getResult2 = dataOutputAssociations2.get(0);
    assertNull(getResult2.getId());
    assertNull(getResult.getSourceRef());
    assertNull(getResult2.getSourceRef());
    assertNull(getResult.getTargetRef());
    assertNull(getResult2.getTargetRef());
    assertNull(getResult.getTransformation());
    assertNull(getResult2.getTransformation());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertTrue(getResult.getAssignments().isEmpty());
    assertTrue(getResult2.getAssignments().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link ReceiveTask#setValues(ManualTask)} with {@code ManualTask}.
   *
   * <ul>
   *   <li>When {@link ManualTask} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ReceiveTask#setValues(ManualTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReceiveTask.setValues(ManualTask)"})
  public void testSetValuesWithManualTask_whenManualTask() {
    // Arrange
    ReceiveTask receiveTask = new ReceiveTask();

    // Act
    receiveTask.setValues(new ManualTask());

    // Assert that nothing has changed
    assertTrue(receiveTask.getDataInputAssociations().isEmpty());
    assertTrue(receiveTask.getDataOutputAssociations().isEmpty());
  }

  /**
   * Test {@link ReceiveTask#setValues(ManualTask)} with {@code ManualTask}.
   *
   * <ul>
   *   <li>When {@link ManualTask} (default constructor) DataInputAssociations is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ReceiveTask#setValues(ManualTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReceiveTask.setValues(ManualTask)"})
  public void testSetValuesWithManualTask_whenManualTaskDataInputAssociationsIsNull() {
    // Arrange
    ReceiveTask receiveTask = new ReceiveTask();

    ArrayList<DataSpec> dataInputs = new ArrayList<>();
    dataInputs.add(new DataSpec());

    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(new DataSpec());

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(dataInputs);
    ioSpecification.setDataOutputs(dataOutputs);

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    ManualTask otherElement = new ManualTask();
    otherElement.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    otherElement.setIoSpecification(ioSpecification);
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
   * Test {@link ReceiveTask#setValues(ManualTask)} with {@code ManualTask}.
   *
   * <ul>
   *   <li>When {@link ManualTask} (default constructor) DataOutputAssociations is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ReceiveTask#setValues(ManualTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReceiveTask.setValues(ManualTask)"})
  public void testSetValuesWithManualTask_whenManualTaskDataOutputAssociationsIsNull() {
    // Arrange
    ReceiveTask receiveTask = new ReceiveTask();

    ArrayList<DataSpec> dataInputs = new ArrayList<>();
    dataInputs.add(new DataSpec());

    ArrayList<DataSpec> dataOutputs = new ArrayList<>();
    dataOutputs.add(new DataSpec());

    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(dataInputs);
    ioSpecification.setDataOutputs(dataOutputs);

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    ManualTask otherElement = new ManualTask();
    otherElement.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    otherElement.setIoSpecification(ioSpecification);
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
   * Test new {@link ReceiveTask} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link ReceiveTask}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReceiveTask.<init>()"})
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
