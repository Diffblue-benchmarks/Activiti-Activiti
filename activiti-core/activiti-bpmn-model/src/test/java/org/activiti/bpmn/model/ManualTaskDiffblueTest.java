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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ManualTaskDiffblueTest {
  /**
   * Test {@link ManualTask#clone()}.
   *
   * <ul>
   *   <li>Given {@link ManualTask} (default constructor) DataInputAssociations is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ManualTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ManualTask ManualTask.clone()"})
  public void testClone_givenManualTaskDataInputAssociationsIsNull() {
    // Arrange
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

    ManualTask manualTask = new ManualTask();
    manualTask.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    manualTask.setIoSpecification(ioSpecification);
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
   *
   * <ul>
   *   <li>Given {@link ManualTask} (default constructor) DataOutputAssociations is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ManualTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ManualTask ManualTask.clone()"})
  public void testClone_givenManualTaskDataOutputAssociationsIsNull() {
    // Arrange
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

    ManualTask manualTask = new ManualTask();
    manualTask.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    manualTask.setIoSpecification(ioSpecification);
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
   *
   * <ul>
   *   <li>Given {@link ManualTask} (default constructor) ForCompensation is {@code true}.
   *   <li>Then return ForCompensation.
   * </ul>
   *
   * <p>Method under test: {@link ManualTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ManualTask ManualTask.clone()"})
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
    assertTrue(actualCloneResult.isForCompensation());
  }

  /**
   * Test {@link ManualTask#clone()}.
   *
   * <ul>
   *   <li>Given {@link ManualTask} (default constructor).
   *   <li>Then return IoSpecification is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ManualTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ManualTask ManualTask.clone()"})
  public void testClone_givenManualTask_thenReturnIoSpecificationIsNull() {
    // Arrange and Act
    ManualTask actualCloneResult = new ManualTask().clone();

    // Assert
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
  }

  /**
   * Test {@link ManualTask#clone()}.
   *
   * <ul>
   *   <li>Then return DataInputAssociations size is one.
   * </ul>
   *
   * <p>Method under test: {@link ManualTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ManualTask ManualTask.clone()"})
  public void testClone_thenReturnDataInputAssociationsSizeIsOne() {
    // Arrange
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

    ManualTask manualTask = new ManualTask();
    manualTask.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    manualTask.setIoSpecification(ioSpecification);
    manualTask.setDataInputAssociations(dataInputAssociations);
    manualTask.setDataOutputAssociations(dataOutputAssociations);
    manualTask.setBoundaryEvents(boundaryEvents);

    // Act
    ManualTask actualCloneResult = manualTask.clone();

    // Assert
    List<DataAssociation> dataInputAssociations2 = actualCloneResult.getDataInputAssociations();
    assertEquals(1, dataInputAssociations2.size());
    DataAssociation getResult = dataInputAssociations2.get(0);
    assertNull(getResult.getId());
    List<DataAssociation> dataOutputAssociations2 = actualCloneResult.getDataOutputAssociations();
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
   * Test new {@link ManualTask} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link ManualTask}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ManualTask.<init>()"})
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
