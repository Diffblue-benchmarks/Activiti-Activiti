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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class BusinessRuleTaskDiffblueTest {
  /**
   * Test {@link BusinessRuleTask#clone()}.
   * <ul>
   *   <li>Given {@link BusinessRuleTask} (default constructor) Exclude is
   * {@code true}.</li>
   *   <li>Then return Exclude.</li>
   * </ul>
   * <p>
   * Method under test: {@link BusinessRuleTask#clone()}
   */
  @Test
  public void testClone_givenBusinessRuleTaskExcludeIsTrue_thenReturnExclude() {
    // Arrange
    BusinessRuleTask businessRuleTask = new BusinessRuleTask();
    businessRuleTask.setExclude(true);

    // Act
    BusinessRuleTask actualCloneResult = businessRuleTask.clone();

    // Assert
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
    assertTrue(actualCloneResult.getDataOutputAssociations().isEmpty());
    assertTrue(actualCloneResult.isExclude());
  }

  /**
   * Test {@link BusinessRuleTask#clone()}.
   * <ul>
   *   <li>Given {@link BusinessRuleTask} (default constructor).</li>
   *   <li>Then return not Exclude.</li>
   * </ul>
   * <p>
   * Method under test: {@link BusinessRuleTask#clone()}
   */
  @Test
  public void testClone_givenBusinessRuleTask_thenReturnNotExclude() {
    // Arrange and Act
    BusinessRuleTask actualCloneResult = (new BusinessRuleTask()).clone();

    // Assert
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertFalse(actualCloneResult.isExclude());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
    assertTrue(actualCloneResult.getDataOutputAssociations().isEmpty());
  }

  /**
   * Test {@link BusinessRuleTask#clone()}.
   * <ul>
   *   <li>Then return BoundaryEvents is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BusinessRuleTask#clone()}
   */
  @Test
  public void testClone_thenReturnBoundaryEventsIsArrayList() {
    // Arrange
    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    BusinessRuleTask businessRuleTask = new BusinessRuleTask();
    businessRuleTask.setLoopCharacteristics(null);
    businessRuleTask.setIoSpecification(null);
    businessRuleTask.setDataInputAssociations(null);
    businessRuleTask.setDataOutputAssociations(null);
    businessRuleTask.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    assertEquals(boundaryEvents, businessRuleTask.clone().getBoundaryEvents());
  }

  /**
   * Test {@link BusinessRuleTask#clone()}.
   * <ul>
   *   <li>Then return DataInputAssociations size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BusinessRuleTask#clone()}
   */
  @Test
  public void testClone_thenReturnDataInputAssociationsSizeIsOne() {
    // Arrange
    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    BusinessRuleTask businessRuleTask = new BusinessRuleTask();
    businessRuleTask.setLoopCharacteristics(null);
    businessRuleTask.setIoSpecification(null);
    businessRuleTask.setDataInputAssociations(dataInputAssociations);
    businessRuleTask.setDataOutputAssociations(null);
    businessRuleTask.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    List<DataAssociation> dataInputAssociations2 = businessRuleTask.clone().getDataInputAssociations();
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
   * Test {@link BusinessRuleTask#clone()}.
   * <ul>
   *   <li>Then return DataOutputAssociations size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BusinessRuleTask#clone()}
   */
  @Test
  public void testClone_thenReturnDataOutputAssociationsSizeIsOne() {
    // Arrange
    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    BusinessRuleTask businessRuleTask = new BusinessRuleTask();
    businessRuleTask.setLoopCharacteristics(null);
    businessRuleTask.setIoSpecification(null);
    businessRuleTask.setDataInputAssociations(null);
    businessRuleTask.setDataOutputAssociations(dataOutputAssociations);
    businessRuleTask.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    List<DataAssociation> dataOutputAssociations2 = businessRuleTask.clone().getDataOutputAssociations();
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
   * Test {@link BusinessRuleTask#clone()}.
   * <ul>
   *   <li>Then return IoSpecification Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BusinessRuleTask#clone()}
   */
  @Test
  public void testClone_thenReturnIoSpecificationIdIsNull() {
    // Arrange
    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    BusinessRuleTask businessRuleTask = new BusinessRuleTask();
    businessRuleTask.setLoopCharacteristics(null);
    businessRuleTask.setIoSpecification(new IOSpecification());
    businessRuleTask.setDataInputAssociations(null);
    businessRuleTask.setDataOutputAssociations(null);
    businessRuleTask.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    IOSpecification ioSpecification = businessRuleTask.clone().getIoSpecification();
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
   * Test {@link BusinessRuleTask#clone()}.
   * <ul>
   *   <li>Then return LoopCharacteristics Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BusinessRuleTask#clone()}
   */
  @Test
  public void testClone_thenReturnLoopCharacteristicsIdIsNull() {
    // Arrange
    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    BusinessRuleTask businessRuleTask = new BusinessRuleTask();
    businessRuleTask.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    businessRuleTask.setIoSpecification(null);
    businessRuleTask.setDataInputAssociations(null);
    businessRuleTask.setDataOutputAssociations(null);
    businessRuleTask.setBoundaryEvents(boundaryEvents);

    // Act
    BusinessRuleTask actualCloneResult = businessRuleTask.clone();

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
   * Test {@link BusinessRuleTask#setValues(BusinessRuleTask)} with
   * {@code BusinessRuleTask}.
   * <ul>
   *   <li>Then calls {@link MultiInstanceLoopCharacteristics#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BusinessRuleTask#setValues(BusinessRuleTask)}
   */
  @Test
  public void testSetValuesWithBusinessRuleTask_thenCallsClone() {
    // Arrange
    BusinessRuleTask businessRuleTask = new BusinessRuleTask();
    MultiInstanceLoopCharacteristics loopCharacteristics = mock(MultiInstanceLoopCharacteristics.class);
    when(loopCharacteristics.clone()).thenReturn(new MultiInstanceLoopCharacteristics());

    BusinessRuleTask otherElement = new BusinessRuleTask();
    otherElement.setLoopCharacteristics(loopCharacteristics);

    // Act
    businessRuleTask.setValues(otherElement);

    // Assert
    verify(loopCharacteristics).clone();
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link BusinessRuleTask}
   *   <li>{@link BusinessRuleTask#setClassName(String)}
   *   <li>{@link BusinessRuleTask#setExclude(boolean)}
   *   <li>{@link BusinessRuleTask#setInputVariables(List)}
   *   <li>{@link BusinessRuleTask#setResultVariableName(String)}
   *   <li>{@link BusinessRuleTask#setRuleNames(List)}
   *   <li>{@link BusinessRuleTask#getClassName()}
   *   <li>{@link BusinessRuleTask#getInputVariables()}
   *   <li>{@link BusinessRuleTask#getResultVariableName()}
   *   <li>{@link BusinessRuleTask#getRuleNames()}
   *   <li>{@link BusinessRuleTask#isExclude()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    BusinessRuleTask actualBusinessRuleTask = new BusinessRuleTask();
    actualBusinessRuleTask.setClassName("Class Name");
    actualBusinessRuleTask.setExclude(true);
    ArrayList<String> inputVariables = new ArrayList<>();
    actualBusinessRuleTask.setInputVariables(inputVariables);
    actualBusinessRuleTask.setResultVariableName("Result Variable Name");
    ArrayList<String> ruleNames = new ArrayList<>();
    actualBusinessRuleTask.setRuleNames(ruleNames);
    String actualClassName = actualBusinessRuleTask.getClassName();
    List<String> actualInputVariables = actualBusinessRuleTask.getInputVariables();
    String actualResultVariableName = actualBusinessRuleTask.getResultVariableName();
    List<String> actualRuleNames = actualBusinessRuleTask.getRuleNames();
    boolean actualIsExcludeResult = actualBusinessRuleTask.isExclude();

    // Assert that nothing has changed
    assertEquals("Class Name", actualClassName);
    assertEquals("Result Variable Name", actualResultVariableName);
    assertEquals(0, actualBusinessRuleTask.getXmlColumnNumber());
    assertEquals(0, actualBusinessRuleTask.getXmlRowNumber());
    assertFalse(actualBusinessRuleTask.isForCompensation());
    assertFalse(actualBusinessRuleTask.isAsynchronous());
    assertFalse(actualBusinessRuleTask.isNotExclusive());
    assertTrue(actualBusinessRuleTask.getBoundaryEvents().isEmpty());
    assertTrue(actualBusinessRuleTask.getDataInputAssociations().isEmpty());
    assertTrue(actualBusinessRuleTask.getDataOutputAssociations().isEmpty());
    assertTrue(actualBusinessRuleTask.getMapExceptions().isEmpty());
    assertTrue(actualInputVariables.isEmpty());
    assertTrue(actualRuleNames.isEmpty());
    assertTrue(actualBusinessRuleTask.getExecutionListeners().isEmpty());
    assertTrue(actualBusinessRuleTask.getIncomingFlows().isEmpty());
    assertTrue(actualBusinessRuleTask.getOutgoingFlows().isEmpty());
    assertTrue(actualBusinessRuleTask.getAttributes().isEmpty());
    assertTrue(actualBusinessRuleTask.getExtensionElements().isEmpty());
    assertTrue(actualIsExcludeResult);
    assertSame(inputVariables, actualInputVariables);
    assertSame(ruleNames, actualRuleNames);
  }
}
