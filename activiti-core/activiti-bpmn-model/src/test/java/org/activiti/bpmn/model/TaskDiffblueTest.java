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
   * Test {@link Task#clone()}.
   * <ul>
   *   <li>Given {@link BusinessRuleTask} (default constructor).</li>
   *   <li>Then return {@link BusinessRuleTask}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Task#clone()}
   */
  @Test
  public void testClone_givenBusinessRuleTask_thenReturnBusinessRuleTask() {
    // Arrange and Act
    BusinessRuleTask actualCloneResult = (new BusinessRuleTask()).clone();

    // Assert
    assertTrue(actualCloneResult instanceof BusinessRuleTask);
    assertNull(((BusinessRuleTask) actualCloneResult).getClassName());
    assertNull(((BusinessRuleTask) actualCloneResult).getResultVariableName());
    assertFalse(((BusinessRuleTask) actualCloneResult).isExclude());
    assertTrue(((BusinessRuleTask) actualCloneResult).getInputVariables().isEmpty());
    assertTrue(((BusinessRuleTask) actualCloneResult).getRuleNames().isEmpty());
  }

  /**
   * Test {@link Task#clone()}.
   * <ul>
   *   <li>Given {@link Task} (default constructor) ForCompensation is
   * {@code true}.</li>
   *   <li>Then return ForCompensation.</li>
   * </ul>
   * <p>
   * Method under test: {@link Task#clone()}
   */
  @Test
  public void testClone_givenTaskForCompensationIsTrue_thenReturnForCompensation() {
    // Arrange
    Task task = new Task();
    task.setForCompensation(true);

    // Act
    FlowElement actualCloneResult = task.clone();

    // Assert
    assertTrue(actualCloneResult instanceof Task);
    assertNull(((Task) actualCloneResult).getIoSpecification());
    assertNull(((Task) actualCloneResult).getLoopCharacteristics());
    assertFalse(((Task) actualCloneResult).hasMultiInstanceLoopCharacteristics());
    assertTrue(((Task) actualCloneResult).getBoundaryEvents().isEmpty());
    assertTrue(((Task) actualCloneResult).getDataInputAssociations().isEmpty());
    assertTrue(((Task) actualCloneResult).getDataOutputAssociations().isEmpty());
    assertTrue(((Task) actualCloneResult).isForCompensation());
  }

  /**
   * Test {@link Task#clone()}.
   * <ul>
   *   <li>Given {@link Task} (default constructor).</li>
   *   <li>Then return not ForCompensation.</li>
   * </ul>
   * <p>
   * Method under test: {@link Task#clone()}
   */
  @Test
  public void testClone_givenTask_thenReturnNotForCompensation() {
    // Arrange and Act
    FlowElement actualCloneResult = (new Task()).clone();

    // Assert
    assertTrue(actualCloneResult instanceof Task);
    assertNull(((Task) actualCloneResult).getIoSpecification());
    assertNull(((Task) actualCloneResult).getLoopCharacteristics());
    assertFalse(((Task) actualCloneResult).hasMultiInstanceLoopCharacteristics());
    assertFalse(((Task) actualCloneResult).isForCompensation());
    assertTrue(((Task) actualCloneResult).getBoundaryEvents().isEmpty());
    assertTrue(((Task) actualCloneResult).getDataInputAssociations().isEmpty());
    assertTrue(((Task) actualCloneResult).getDataOutputAssociations().isEmpty());
  }

  /**
   * Test {@link Task#clone()}.
   * <ul>
   *   <li>Then return BoundaryEvents is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Task#clone()}
   */
  @Test
  public void testClone_thenReturnBoundaryEventsIsArrayList() {
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
    assertTrue(((Task) actualCloneResult).getIoSpecification().getDataOutputs().isEmpty());
    assertEquals(boundaryEvents, ((Task) actualCloneResult).getBoundaryEvents());
  }

  /**
   * Test {@link Task#clone()}.
   * <ul>
   *   <li>Then return BoundaryEvents is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Task#clone()}
   */
  @Test
  public void testClone_thenReturnBoundaryEventsIsArrayList2() {
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
    assertTrue(((Task) actualCloneResult).getIoSpecification().getDataOutputs().isEmpty());
    assertEquals(boundaryEvents, ((Task) actualCloneResult).getBoundaryEvents());
  }

  /**
   * Test {@link Task#clone()}.
   * <ul>
   *   <li>Then return DataInputAssociations size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link Task#clone()}
   */
  @Test
  public void testClone_thenReturnDataInputAssociationsSizeIsOne() {
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
    List<DataAssociation> dataInputAssociations2 = ((Task) actualCloneResult).getDataInputAssociations();
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
   * Test {@link Task#clone()}.
   * <ul>
   *   <li>Then return DataOutputAssociations size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link Task#clone()}
   */
  @Test
  public void testClone_thenReturnDataOutputAssociationsSizeIsOne() {
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
    List<DataAssociation> dataOutputAssociations2 = ((Task) actualCloneResult).getDataOutputAssociations();
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
   * Test {@link Task#clone()}.
   * <ul>
   *   <li>Then return IoSpecification DataOutputs size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link Task#clone()}
   */
  @Test
  public void testClone_thenReturnIoSpecificationDataOutputsSizeIsOne() {
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
    List<DataSpec> dataOutputs2 = ((Task) actualCloneResult).getIoSpecification().getDataOutputs();
    assertEquals(1, dataOutputs2.size());
    DataSpec getResult = dataOutputs2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getItemSubjectRef());
    assertNull(getResult.getName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertFalse(getResult.isCollection());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Task#clone()}.
   * <ul>
   *   <li>Then return IoSpecification Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Task#clone()}
   */
  @Test
  public void testClone_thenReturnIoSpecificationIdIsNull() {
    // Arrange
    Task task = new Task();
    task.setIoSpecification(new IOSpecification());

    // Act
    FlowElement actualCloneResult = task.clone();

    // Assert
    assertTrue(actualCloneResult instanceof Task);
    IOSpecification ioSpecification = ((Task) actualCloneResult).getIoSpecification();
    assertNull(ioSpecification.getId());
    assertEquals(0, ioSpecification.getXmlColumnNumber());
    assertEquals(0, ioSpecification.getXmlRowNumber());
    assertTrue(((Task) actualCloneResult).getBoundaryEvents().isEmpty());
    assertTrue(ioSpecification.getDataInputRefs().isEmpty());
    assertTrue(ioSpecification.getDataInputs().isEmpty());
    assertTrue(ioSpecification.getDataOutputRefs().isEmpty());
    assertTrue(ioSpecification.getDataOutputs().isEmpty());
    assertTrue(ioSpecification.getAttributes().isEmpty());
    assertTrue(ioSpecification.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Task#clone()}.
   * <ul>
   *   <li>Then return LoopCharacteristics Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Task#clone()}
   */
  @Test
  public void testClone_thenReturnLoopCharacteristicsIdIsNull() {
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
    MultiInstanceLoopCharacteristics loopCharacteristics = ((Task) actualCloneResult).getLoopCharacteristics();
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
    assertTrue(((Task) actualCloneResult).hasMultiInstanceLoopCharacteristics());
  }

  /**
   * Test {@link Task#setValues(Task)} with {@code Task}.
   * <p>
   * Method under test: {@link Task#setValues(Task)}
   */
  @Test
  public void testSetValuesWithTask() {
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
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertTrue(otherElement.hasMultiInstanceLoopCharacteristics());
    assertTrue(otherElement.isExclusive());
    assertSame(ioSpecification, otherElement.getIoSpecification());
    assertSame(loopCharacteristics, otherElement.getLoopCharacteristics());
  }

  /**
   * Test {@link Task#setValues(Task)} with {@code Task}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link DataSpec} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Task#setValues(Task)}
   */
  @Test
  public void testSetValuesWithTask_givenArrayListAddDataSpec() {
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
    assertNull(otherElement.getLoopCharacteristics());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertTrue(otherElement.isExclusive());
    assertSame(ioSpecification, otherElement.getIoSpecification());
  }

  /**
   * Test {@link Task#setValues(Task)} with {@code Task}.
   * <ul>
   *   <li>Given {@link IOSpecification} (default constructor) DataOutputs is
   * {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Task#setValues(Task)}
   */
  @Test
  public void testSetValuesWithTask_givenIOSpecificationDataOutputsIsArrayList() {
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
    assertNull(otherElement.getLoopCharacteristics());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertTrue(otherElement.isExclusive());
    assertSame(ioSpecification, otherElement.getIoSpecification());
  }

  /**
   * Test {@link Task#setValues(Task)} with {@code Task}.
   * <ul>
   *   <li>Given {@link IOSpecification} (default constructor).</li>
   *   <li>Then {@link Task} (default constructor) IoSpecification Id is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Task#setValues(Task)}
   */
  @Test
  public void testSetValuesWithTask_givenIOSpecification_thenTaskIoSpecificationIdIsNull() {
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
   * Test {@link Task#setValues(Task)} with {@code Task}.
   * <ul>
   *   <li>Then calls {@link MultiInstanceLoopCharacteristics#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Task#setValues(Task)}
   */
  @Test
  public void testSetValuesWithTask_thenCallsClone() {
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
    assertTrue(task.hasMultiInstanceLoopCharacteristics());
    assertTrue(task.isForCompensation());
    assertTrue(task.isAsynchronous());
    assertTrue(task.isNotExclusive());
    assertSame(ioSpecification2, task.getIoSpecification());
    assertSame(multiInstanceLoopCharacteristics2, task.getLoopCharacteristics());
  }

  /**
   * Test {@link Task#setValues(Task)} with {@code Task}.
   * <ul>
   *   <li>Then calls {@link DataAssociation#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Task#setValues(Task)}
   */
  @Test
  public void testSetValuesWithTask_thenCallsClone2() {
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
    assertNull(otherElement.getLoopCharacteristics());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertTrue(otherElement.isExclusive());
    assertSame(ioSpecification, otherElement.getIoSpecification());
  }

  /**
   * Test {@link Task#setValues(Task)} with {@code Task}.
   * <ul>
   *   <li>Then calls {@link DataAssociation#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Task#setValues(Task)}
   */
  @Test
  public void testSetValuesWithTask_thenCallsClone3() {
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
    assertNull(otherElement.getLoopCharacteristics());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertTrue(otherElement.isExclusive());
    assertSame(ioSpecification, otherElement.getIoSpecification());
  }

  /**
   * Test {@link Task#setValues(Task)} with {@code Task}.
   * <ul>
   *   <li>Then {@link Task} (default constructor) DataInputAssociations size is
   * one.</li>
   * </ul>
   * <p>
   * Method under test: {@link Task#setValues(Task)}
   */
  @Test
  public void testSetValuesWithTask_thenTaskDataInputAssociationsSizeIsOne() {
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
    List<DataAssociation> dataInputAssociations = task.getDataInputAssociations();
    assertEquals(1, dataInputAssociations.size());
    DataAssociation getResult = dataInputAssociations.get(0);
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
   * Test {@link Task#setValues(Task)} with {@code Task}.
   * <ul>
   *   <li>Then {@link Task} (default constructor) DataOutputAssociations size is
   * one.</li>
   * </ul>
   * <p>
   * Method under test: {@link Task#setValues(Task)}
   */
  @Test
  public void testSetValuesWithTask_thenTaskDataOutputAssociationsSizeIsOne() {
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
    List<DataAssociation> dataOutputAssociations = task.getDataOutputAssociations();
    assertEquals(1, dataOutputAssociations.size());
    DataAssociation getResult = dataOutputAssociations.get(0);
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
   * Test {@link Task#setValues(Task)} with {@code Task}.
   * <ul>
   *   <li>Then {@link Task} (default constructor) LoopCharacteristics Id is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Task#setValues(Task)}
   */
  @Test
  public void testSetValuesWithTask_thenTaskLoopCharacteristicsIdIsNull() {
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
    assertTrue(loopCharacteristics.getAttributes().isEmpty());
    assertTrue(loopCharacteristics.getExtensionElements().isEmpty());
    assertTrue(task.hasMultiInstanceLoopCharacteristics());
    assertTrue(task.isForCompensation());
    assertTrue(task.isAsynchronous());
    assertTrue(task.isNotExclusive());
    assertSame(ioSpecification2, task.getIoSpecification());
  }

  /**
   * Test {@link Task#setValues(Task)} with {@code Task}.
   * <ul>
   *   <li>When {@link Task} (default constructor) DataInputAssociations is
   * {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Task#setValues(Task)}
   */
  @Test
  public void testSetValuesWithTask_whenTaskDataInputAssociationsIsArrayList() {
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
    assertNull(otherElement.getLoopCharacteristics());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertTrue(otherElement.isExclusive());
    assertSame(ioSpecification, otherElement.getIoSpecification());
  }

  /**
   * Test {@link Task#setValues(Task)} with {@code Task}.
   * <ul>
   *   <li>When {@link Task} (default constructor) DataInputAssociations is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Task#setValues(Task)}
   */
  @Test
  public void testSetValuesWithTask_whenTaskDataInputAssociationsIsNull() {
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
    assertNull(otherElement.getLoopCharacteristics());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertTrue(otherElement.isExclusive());
    assertSame(ioSpecification, otherElement.getIoSpecification());
  }

  /**
   * Test {@link Task#setValues(Task)} with {@code Task}.
   * <ul>
   *   <li>When {@link Task} (default constructor) DataOutputAssociations is
   * {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Task#setValues(Task)}
   */
  @Test
  public void testSetValuesWithTask_whenTaskDataOutputAssociationsIsArrayList() {
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
    assertNull(otherElement.getLoopCharacteristics());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertTrue(otherElement.isExclusive());
    assertSame(ioSpecification, otherElement.getIoSpecification());
  }

  /**
   * Test {@link Task#setValues(Task)} with {@code Task}.
   * <ul>
   *   <li>When {@link Task} (default constructor).</li>
   *   <li>Then {@link Task} (default constructor) IoSpecification is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Task#setValues(Task)}
   */
  @Test
  public void testSetValuesWithTask_whenTask_thenTaskIoSpecificationIsNull() {
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
    assertTrue(otherElement.isExclusive());
  }

  /**
   * Test new {@link Task} (default constructor).
   * <p>
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
