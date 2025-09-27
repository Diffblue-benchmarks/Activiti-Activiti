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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class TaskDiffblueTest {
  /**
   * Test {@link Task#clone()}.
   *
   * <ul>
   *   <li>Given {@link BusinessRuleTask} (default constructor).
   *   <li>Then return {@link BusinessRuleTask}.
   * </ul>
   *
   * <p>Method under test: {@link Task#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElement Task.clone()"})
  public void testClone_givenBusinessRuleTask_thenReturnBusinessRuleTask() {
    // Arrange and Act
    BusinessRuleTask actualCloneResult = new BusinessRuleTask().clone();

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
   *
   * <ul>
   *   <li>Given {@link IOSpecification} (default constructor) DataOutputs is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Task#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElement Task.clone()"})
  public void testClone_givenIOSpecificationDataOutputsIsNull() {
    // Arrange
    IOSpecification ioSpecification = new IOSpecification();
    ioSpecification.setDataInputs(null);
    ioSpecification.setDataOutputs(null);

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    Task task = new Task();
    task.setLoopCharacteristics(null);
    task.setIoSpecification(ioSpecification);
    task.setDataInputAssociations(null);
    task.setDataOutputAssociations(null);
    task.setBoundaryEvents(boundaryEvents);

    // Act
    FlowElement actualCloneResult = task.clone();

    // Assert
    assertTrue(actualCloneResult instanceof Task);
    IOSpecification ioSpecification2 = ((Task) actualCloneResult).getIoSpecification();
    assertNull(ioSpecification2.getId());
    assertEquals(0, ioSpecification2.getXmlColumnNumber());
    assertEquals(0, ioSpecification2.getXmlRowNumber());
    assertTrue(ioSpecification2.getDataInputRefs().isEmpty());
    assertTrue(ioSpecification2.getDataInputs().isEmpty());
    assertTrue(ioSpecification2.getDataOutputRefs().isEmpty());
    assertTrue(ioSpecification2.getDataOutputs().isEmpty());
    assertTrue(ioSpecification2.getAttributes().isEmpty());
    assertTrue(ioSpecification2.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Task#clone()}.
   *
   * <ul>
   *   <li>Given {@link Task} (default constructor) ForCompensation is {@code true}.
   *   <li>Then return ForCompensation.
   * </ul>
   *
   * <p>Method under test: {@link Task#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElement Task.clone()"})
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
   *
   * <ul>
   *   <li>Given {@link Task} (default constructor) IoSpecification is {@code null}.
   *   <li>Then return BoundaryEvents size is one.
   * </ul>
   *
   * <p>Method under test: {@link Task#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElement Task.clone()"})
  public void testClone_givenTaskIoSpecificationIsNull_thenReturnBoundaryEventsSizeIsOne() {
    // Arrange
    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvents.add(boundaryEvent);

    Task task = new Task();
    task.setLoopCharacteristics(null);
    task.setIoSpecification(null);
    task.setDataInputAssociations(null);
    task.setDataOutputAssociations(null);
    task.setBoundaryEvents(boundaryEvents);

    // Act
    FlowElement actualCloneResult = task.clone();

    // Assert
    assertTrue(actualCloneResult instanceof Task);
    List<BoundaryEvent> boundaryEvents2 = ((Task) actualCloneResult).getBoundaryEvents();
    assertEquals(1, boundaryEvents2.size());
    assertSame(boundaryEvent, boundaryEvents2.get(0));
  }

  /**
   * Test {@link Task#clone()}.
   *
   * <ul>
   *   <li>Given {@link Task} (default constructor).
   *   <li>Then return not ForCompensation.
   * </ul>
   *
   * <p>Method under test: {@link Task#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElement Task.clone()"})
  public void testClone_givenTask_thenReturnNotForCompensation() {
    // Arrange and Act
    FlowElement actualCloneResult = new Task().clone();

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
   *
   * <ul>
   *   <li>Then return DataInputAssociations size is one.
   * </ul>
   *
   * <p>Method under test: {@link Task#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElement Task.clone()"})
  public void testClone_thenReturnDataInputAssociationsSizeIsOne() {
    // Arrange
    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    Task task = new Task();
    task.setLoopCharacteristics(null);
    task.setIoSpecification(null);
    task.setDataInputAssociations(dataInputAssociations);
    task.setDataOutputAssociations(null);
    task.setBoundaryEvents(boundaryEvents);

    // Act
    FlowElement actualCloneResult = task.clone();

    // Assert
    assertTrue(actualCloneResult instanceof Task);
    List<DataAssociation> dataInputAssociations2 =
        ((Task) actualCloneResult).getDataInputAssociations();
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
   *
   * <ul>
   *   <li>Then return DataOutputAssociations size is one.
   * </ul>
   *
   * <p>Method under test: {@link Task#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElement Task.clone()"})
  public void testClone_thenReturnDataOutputAssociationsSizeIsOne() {
    // Arrange
    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    Task task = new Task();
    task.setLoopCharacteristics(null);
    task.setIoSpecification(null);
    task.setDataInputAssociations(null);
    task.setDataOutputAssociations(dataOutputAssociations);
    task.setBoundaryEvents(boundaryEvents);

    // Act
    FlowElement actualCloneResult = task.clone();

    // Assert
    assertTrue(actualCloneResult instanceof Task);
    List<DataAssociation> dataOutputAssociations2 =
        ((Task) actualCloneResult).getDataOutputAssociations();
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
   *
   * <ul>
   *   <li>Then return IoSpecification DataOutputs size is one.
   * </ul>
   *
   * <p>Method under test: {@link Task#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElement Task.clone()"})
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
    task.setLoopCharacteristics(null);
    task.setIoSpecification(ioSpecification);
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
   *
   * <ul>
   *   <li>Then return IoSpecification Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Task#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElement Task.clone()"})
  public void testClone_thenReturnIoSpecificationIdIsNull() {
    // Arrange
    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    Task task = new Task();
    task.setLoopCharacteristics(null);
    task.setIoSpecification(new IOSpecification());
    task.setDataInputAssociations(null);
    task.setDataOutputAssociations(null);
    task.setBoundaryEvents(boundaryEvents);

    // Act
    FlowElement actualCloneResult = task.clone();

    // Assert
    assertTrue(actualCloneResult instanceof Task);
    IOSpecification ioSpecification = ((Task) actualCloneResult).getIoSpecification();
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
   * Test {@link Task#clone()}.
   *
   * <ul>
   *   <li>Then return LoopCharacteristics Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Task#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"FlowElement Task.clone()"})
  public void testClone_thenReturnLoopCharacteristicsIdIsNull() {
    // Arrange
    Task task = new Task();
    task.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());

    // Act
    FlowElement actualCloneResult = task.clone();

    // Assert
    assertTrue(actualCloneResult instanceof Task);
    MultiInstanceLoopCharacteristics loopCharacteristics =
        ((Task) actualCloneResult).getLoopCharacteristics();
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
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.
   *   <li>Then {@link Task} (default constructor) LoopCharacteristics Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Task#setValues(Task)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Task.setValues(Task)"})
  public void testSetValuesWithTask_givenArrayList_thenTaskLoopCharacteristicsIdIsNull() {
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
    assertFalse(loopCharacteristics.isSequential());
    assertTrue(loopCharacteristics.getAttributes().isEmpty());
    assertTrue(loopCharacteristics.getExtensionElements().isEmpty());
    assertTrue(task.hasMultiInstanceLoopCharacteristics());
  }

  /**
   * Test {@link Task#setValues(Task)} with {@code Task}.
   *
   * <ul>
   *   <li>Then {@link Task} (default constructor) BoundaryEvents size is one.
   * </ul>
   *
   * <p>Method under test: {@link Task#setValues(Task)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Task.setValues(Task)"})
  public void testSetValuesWithTask_thenTaskBoundaryEventsSizeIsOne() {
    // Arrange
    Task task = new Task();

    ArrayList<BoundaryEvent> boundaryEventList = new ArrayList<>();
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEventList.add(boundaryEvent);

    BusinessRuleTask otherElement = mock(BusinessRuleTask.class);
    when(otherElement.getBoundaryEvents()).thenReturn(boundaryEventList);
    when(otherElement.getDataInputAssociations()).thenReturn(null);
    when(otherElement.getDataOutputAssociations()).thenReturn(null);
    when(otherElement.getExecutionListeners()).thenReturn(null);
    when(otherElement.getAttributes()).thenReturn(null);
    when(otherElement.getExtensionElements()).thenReturn(null);
    when(otherElement.getIoSpecification()).thenReturn(null);
    when(otherElement.getLoopCharacteristics()).thenReturn(null);
    when(otherElement.isForCompensation()).thenReturn(true);
    when(otherElement.isAsynchronous()).thenReturn(true);
    when(otherElement.isNotExclusive()).thenReturn(true);
    when(otherElement.getDefaultFlow()).thenReturn("Default Flow");
    when(otherElement.getFailedJobRetryTimeCycleValue()).thenReturn("42");
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getDocumentation()).thenReturn("Documentation");
    when(otherElement.getName()).thenReturn("Name");

    // Act
    task.setValues(otherElement);

    // Assert
    verify(otherElement).getBoundaryEvents();
    verify(otherElement).getDataInputAssociations();
    verify(otherElement).getDataOutputAssociations();
    verify(otherElement).getDefaultFlow();
    verify(otherElement).getFailedJobRetryTimeCycleValue();
    verify(otherElement).getIoSpecification();
    verify(otherElement).getLoopCharacteristics();
    verify(otherElement).isForCompensation();
    verify(otherElement).getAttributes();
    verify(otherElement).getExtensionElements();
    verify(otherElement).getId();
    verify(otherElement).getDocumentation();
    verify(otherElement).getExecutionListeners();
    verify(otherElement).getName();
    verify(otherElement).isAsynchronous();
    verify(otherElement).isNotExclusive();
    List<BoundaryEvent> boundaryEvents = task.getBoundaryEvents();
    assertEquals(1, boundaryEvents.size());
    assertSame(boundaryEvent, boundaryEvents.get(0));
  }

  /**
   * Test {@link Task#setValues(Task)} with {@code Task}.
   *
   * <ul>
   *   <li>Then {@link Task} (default constructor) DataInputAssociations first is {@link
   *       DataAssociation} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link Task#setValues(Task)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Task.setValues(Task)"})
  public void testSetValuesWithTask_thenTaskDataInputAssociationsFirstIsDataAssociation() {
    // Arrange
    Task task = new Task();

    ArrayList<BoundaryEvent> boundaryEventList = new ArrayList<>();
    boundaryEventList.add(new BoundaryEvent());

    DataAssociation dataAssociation = mock(DataAssociation.class);
    DataAssociation dataAssociation2 = new DataAssociation();
    when(dataAssociation.clone()).thenReturn(dataAssociation2);

    ArrayList<DataAssociation> dataAssociationList = new ArrayList<>();
    dataAssociationList.add(dataAssociation);

    ArrayList<DataAssociation> dataAssociationList2 = new ArrayList<>();
    dataAssociationList2.add(new DataAssociation());

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(new ActivitiListener());

    BusinessRuleTask otherElement = mock(BusinessRuleTask.class);
    when(otherElement.getBoundaryEvents()).thenReturn(boundaryEventList);
    when(otherElement.getDataInputAssociations()).thenReturn(dataAssociationList);
    when(otherElement.getDataOutputAssociations()).thenReturn(dataAssociationList2);
    when(otherElement.getExecutionListeners()).thenReturn(activitiListenerList);
    when(otherElement.getAttributes()).thenReturn(null);
    when(otherElement.getExtensionElements()).thenReturn(null);
    when(otherElement.getIoSpecification()).thenReturn(null);
    when(otherElement.getLoopCharacteristics()).thenReturn(null);
    when(otherElement.isForCompensation()).thenReturn(true);
    when(otherElement.isAsynchronous()).thenReturn(true);
    when(otherElement.isNotExclusive()).thenReturn(true);
    when(otherElement.getDefaultFlow()).thenReturn("Default Flow");
    when(otherElement.getFailedJobRetryTimeCycleValue()).thenReturn("42");
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getDocumentation()).thenReturn("Documentation");
    when(otherElement.getName()).thenReturn("Name");

    // Act
    task.setValues(otherElement);

    // Assert
    verify(otherElement).getBoundaryEvents();
    verify(otherElement, atLeast(1)).getDataInputAssociations();
    verify(otherElement, atLeast(1)).getDataOutputAssociations();
    verify(otherElement).getDefaultFlow();
    verify(otherElement).getFailedJobRetryTimeCycleValue();
    verify(otherElement).getIoSpecification();
    verify(otherElement).getLoopCharacteristics();
    verify(otherElement).isForCompensation();
    verify(otherElement).getAttributes();
    verify(otherElement).getExtensionElements();
    verify(otherElement).getId();
    verify(dataAssociation).clone();
    verify(otherElement).getDocumentation();
    verify(otherElement, atLeast(1)).getExecutionListeners();
    verify(otherElement).getName();
    verify(otherElement).isAsynchronous();
    verify(otherElement).isNotExclusive();
    List<DataAssociation> dataInputAssociations = task.getDataInputAssociations();
    assertEquals(1, dataInputAssociations.size());
    assertEquals(1, task.getDataOutputAssociations().size());
    assertEquals(1, task.getExecutionListeners().size());
    assertSame(dataAssociation2, dataInputAssociations.get(0));
  }

  /**
   * Test {@link Task#setValues(Task)} with {@code Task}.
   *
   * <ul>
   *   <li>Then {@link Task} (default constructor) DataInputAssociations size is one.
   * </ul>
   *
   * <p>Method under test: {@link Task#setValues(Task)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Task.setValues(Task)"})
  public void testSetValuesWithTask_thenTaskDataInputAssociationsSizeIsOne() {
    // Arrange
    Task task = new Task();

    ArrayList<BoundaryEvent> boundaryEventList = new ArrayList<>();
    boundaryEventList.add(new BoundaryEvent());

    ArrayList<DataAssociation> dataAssociationList = new ArrayList<>();
    dataAssociationList.add(new DataAssociation());

    ArrayList<DataAssociation> dataAssociationList2 = new ArrayList<>();
    dataAssociationList2.add(new DataAssociation());

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(new ActivitiListener());

    BusinessRuleTask otherElement = mock(BusinessRuleTask.class);
    when(otherElement.getBoundaryEvents()).thenReturn(boundaryEventList);
    when(otherElement.getDataInputAssociations()).thenReturn(dataAssociationList);
    when(otherElement.getDataOutputAssociations()).thenReturn(dataAssociationList2);
    when(otherElement.getExecutionListeners()).thenReturn(activitiListenerList);
    when(otherElement.getAttributes()).thenReturn(null);
    when(otherElement.getExtensionElements()).thenReturn(null);
    when(otherElement.getIoSpecification()).thenReturn(null);
    when(otherElement.getLoopCharacteristics()).thenReturn(null);
    when(otherElement.isForCompensation()).thenReturn(true);
    when(otherElement.isAsynchronous()).thenReturn(true);
    when(otherElement.isNotExclusive()).thenReturn(true);
    when(otherElement.getDefaultFlow()).thenReturn("Default Flow");
    when(otherElement.getFailedJobRetryTimeCycleValue()).thenReturn("42");
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getDocumentation()).thenReturn("Documentation");
    when(otherElement.getName()).thenReturn("Name");

    // Act
    task.setValues(otherElement);

    // Assert
    verify(otherElement).getBoundaryEvents();
    verify(otherElement, atLeast(1)).getDataInputAssociations();
    verify(otherElement, atLeast(1)).getDataOutputAssociations();
    verify(otherElement).getDefaultFlow();
    verify(otherElement).getFailedJobRetryTimeCycleValue();
    verify(otherElement).getIoSpecification();
    verify(otherElement).getLoopCharacteristics();
    verify(otherElement).isForCompensation();
    verify(otherElement).getAttributes();
    verify(otherElement).getExtensionElements();
    verify(otherElement).getId();
    verify(otherElement).getDocumentation();
    verify(otherElement, atLeast(1)).getExecutionListeners();
    verify(otherElement).getName();
    verify(otherElement).isAsynchronous();
    verify(otherElement).isNotExclusive();
    assertEquals(1, task.getDataInputAssociations().size());
    assertEquals(1, task.getDataOutputAssociations().size());
    assertEquals(1, task.getExecutionListeners().size());
  }

  /**
   * Test {@link Task#setValues(Task)} with {@code Task}.
   *
   * <ul>
   *   <li>Then {@link Task} (default constructor) DataOutputAssociations first is {@link
   *       DataAssociation} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link Task#setValues(Task)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Task.setValues(Task)"})
  public void testSetValuesWithTask_thenTaskDataOutputAssociationsFirstIsDataAssociation() {
    // Arrange
    Task task = new Task();

    ArrayList<BoundaryEvent> boundaryEventList = new ArrayList<>();
    boundaryEventList.add(new BoundaryEvent());

    DataAssociation dataAssociation = mock(DataAssociation.class);
    DataAssociation dataAssociation2 = new DataAssociation();
    when(dataAssociation.clone()).thenReturn(dataAssociation2);

    ArrayList<DataAssociation> dataAssociationList = new ArrayList<>();
    dataAssociationList.add(dataAssociation);

    DataAssociation dataAssociation3 = mock(DataAssociation.class);
    DataAssociation dataAssociation4 = new DataAssociation();
    when(dataAssociation3.clone()).thenReturn(dataAssociation4);

    ArrayList<DataAssociation> dataAssociationList2 = new ArrayList<>();
    dataAssociationList2.add(dataAssociation3);

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(new ActivitiListener());

    BusinessRuleTask otherElement = mock(BusinessRuleTask.class);
    when(otherElement.getBoundaryEvents()).thenReturn(boundaryEventList);
    when(otherElement.getDataInputAssociations()).thenReturn(dataAssociationList);
    when(otherElement.getDataOutputAssociations()).thenReturn(dataAssociationList2);
    when(otherElement.getExecutionListeners()).thenReturn(activitiListenerList);
    when(otherElement.getAttributes()).thenReturn(null);
    when(otherElement.getExtensionElements()).thenReturn(null);
    when(otherElement.getIoSpecification()).thenReturn(null);
    when(otherElement.getLoopCharacteristics()).thenReturn(null);
    when(otherElement.isForCompensation()).thenReturn(true);
    when(otherElement.isAsynchronous()).thenReturn(true);
    when(otherElement.isNotExclusive()).thenReturn(true);
    when(otherElement.getDefaultFlow()).thenReturn("Default Flow");
    when(otherElement.getFailedJobRetryTimeCycleValue()).thenReturn("42");
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getDocumentation()).thenReturn("Documentation");
    when(otherElement.getName()).thenReturn("Name");

    // Act
    task.setValues(otherElement);

    // Assert
    verify(otherElement).getBoundaryEvents();
    verify(otherElement, atLeast(1)).getDataInputAssociations();
    verify(otherElement, atLeast(1)).getDataOutputAssociations();
    verify(otherElement).getDefaultFlow();
    verify(otherElement).getFailedJobRetryTimeCycleValue();
    verify(otherElement).getIoSpecification();
    verify(otherElement).getLoopCharacteristics();
    verify(otherElement).isForCompensation();
    verify(otherElement).getAttributes();
    verify(otherElement).getExtensionElements();
    verify(otherElement).getId();
    verify(dataAssociation).clone();
    verify(dataAssociation3).clone();
    verify(otherElement).getDocumentation();
    verify(otherElement, atLeast(1)).getExecutionListeners();
    verify(otherElement).getName();
    verify(otherElement).isAsynchronous();
    verify(otherElement).isNotExclusive();
    List<DataAssociation> dataInputAssociations = task.getDataInputAssociations();
    assertEquals(1, dataInputAssociations.size());
    List<DataAssociation> dataOutputAssociations = task.getDataOutputAssociations();
    assertEquals(1, dataOutputAssociations.size());
    assertEquals(1, task.getExecutionListeners().size());
    assertSame(dataAssociation2, dataInputAssociations.get(0));
    assertSame(dataAssociation4, dataOutputAssociations.get(0));
  }

  /**
   * Test {@link Task#setValues(Task)} with {@code Task}.
   *
   * <ul>
   *   <li>Then {@link Task} (default constructor) IoSpecification Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Task#setValues(Task)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Task.setValues(Task)"})
  public void testSetValuesWithTask_thenTaskIoSpecificationIdIsNull() {
    // Arrange
    Task task = new Task();

    ArrayList<BoundaryEvent> boundaryEventList = new ArrayList<>();
    boundaryEventList.add(new BoundaryEvent());

    DataAssociation dataAssociation = mock(DataAssociation.class);
    when(dataAssociation.clone()).thenReturn(new DataAssociation());

    ArrayList<DataAssociation> dataAssociationList = new ArrayList<>();
    dataAssociationList.add(dataAssociation);

    DataAssociation dataAssociation2 = mock(DataAssociation.class);
    when(dataAssociation2.clone()).thenReturn(new DataAssociation());

    ArrayList<DataAssociation> dataAssociationList2 = new ArrayList<>();
    dataAssociationList2.add(dataAssociation2);

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(new ActivitiListener());

    IOSpecification ioSpecification = mock(IOSpecification.class);
    when(ioSpecification.clone()).thenReturn(new IOSpecification());

    BusinessRuleTask otherElement = mock(BusinessRuleTask.class);
    when(otherElement.getBoundaryEvents()).thenReturn(boundaryEventList);
    when(otherElement.getDataInputAssociations()).thenReturn(dataAssociationList);
    when(otherElement.getDataOutputAssociations()).thenReturn(dataAssociationList2);
    when(otherElement.getExecutionListeners()).thenReturn(activitiListenerList);
    when(otherElement.getAttributes()).thenReturn(null);
    when(otherElement.getExtensionElements()).thenReturn(null);
    when(otherElement.getIoSpecification()).thenReturn(ioSpecification);
    when(otherElement.getLoopCharacteristics()).thenReturn(null);
    when(otherElement.isForCompensation()).thenReturn(true);
    when(otherElement.isAsynchronous()).thenReturn(true);
    when(otherElement.isNotExclusive()).thenReturn(true);
    when(otherElement.getDefaultFlow()).thenReturn("Default Flow");
    when(otherElement.getFailedJobRetryTimeCycleValue()).thenReturn("42");
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getDocumentation()).thenReturn("Documentation");
    when(otherElement.getName()).thenReturn("Name");

    // Act
    task.setValues(otherElement);

    // Assert
    verify(otherElement).getBoundaryEvents();
    verify(otherElement, atLeast(1)).getDataInputAssociations();
    verify(otherElement, atLeast(1)).getDataOutputAssociations();
    verify(otherElement).getDefaultFlow();
    verify(otherElement).getFailedJobRetryTimeCycleValue();
    verify(otherElement, atLeast(1)).getIoSpecification();
    verify(otherElement).getLoopCharacteristics();
    verify(otherElement).isForCompensation();
    verify(otherElement).getAttributes();
    verify(otherElement).getExtensionElements();
    verify(otherElement).getId();
    verify(dataAssociation).clone();
    verify(dataAssociation2).clone();
    verify(otherElement).getDocumentation();
    verify(otherElement, atLeast(1)).getExecutionListeners();
    verify(otherElement).getName();
    verify(otherElement).isAsynchronous();
    verify(otherElement).isNotExclusive();
    verify(ioSpecification).clone();
    IOSpecification ioSpecification2 = task.getIoSpecification();
    assertNull(ioSpecification2.getId());
    assertEquals(0, ioSpecification2.getXmlColumnNumber());
    assertEquals(0, ioSpecification2.getXmlRowNumber());
    assertTrue(ioSpecification2.getDataInputRefs().isEmpty());
    assertTrue(ioSpecification2.getDataInputs().isEmpty());
    assertTrue(ioSpecification2.getDataOutputRefs().isEmpty());
    assertTrue(ioSpecification2.getDataOutputs().isEmpty());
    assertTrue(ioSpecification2.getAttributes().isEmpty());
    assertTrue(ioSpecification2.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Task#setValues(Task)} with {@code Task}.
   *
   * <ul>
   *   <li>Then {@link Task} (default constructor) IoSpecification is {@link IOSpecification}
   *       (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link Task#setValues(Task)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Task.setValues(Task)"})
  public void testSetValuesWithTask_thenTaskIoSpecificationIsIOSpecification() {
    // Arrange
    Task task = new Task();

    ArrayList<BoundaryEvent> boundaryEventList = new ArrayList<>();
    boundaryEventList.add(new BoundaryEvent());

    DataAssociation dataAssociation = mock(DataAssociation.class);
    when(dataAssociation.clone()).thenReturn(new DataAssociation());

    ArrayList<DataAssociation> dataAssociationList = new ArrayList<>();
    dataAssociationList.add(dataAssociation);

    DataAssociation dataAssociation2 = mock(DataAssociation.class);
    when(dataAssociation2.clone()).thenReturn(new DataAssociation());

    ArrayList<DataAssociation> dataAssociationList2 = new ArrayList<>();
    dataAssociationList2.add(dataAssociation2);

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(new ActivitiListener());

    IOSpecification ioSpecification = mock(IOSpecification.class);
    IOSpecification ioSpecification2 = new IOSpecification();
    when(ioSpecification.clone()).thenReturn(ioSpecification2);

    MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics =
        mock(MultiInstanceLoopCharacteristics.class);
    MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics2 =
        new MultiInstanceLoopCharacteristics();
    when(multiInstanceLoopCharacteristics.clone()).thenReturn(multiInstanceLoopCharacteristics2);

    BusinessRuleTask otherElement = mock(BusinessRuleTask.class);
    when(otherElement.getBoundaryEvents()).thenReturn(boundaryEventList);
    when(otherElement.getDataInputAssociations()).thenReturn(dataAssociationList);
    when(otherElement.getDataOutputAssociations()).thenReturn(dataAssociationList2);
    when(otherElement.getExecutionListeners()).thenReturn(activitiListenerList);
    when(otherElement.getAttributes()).thenReturn(null);
    when(otherElement.getExtensionElements()).thenReturn(null);
    when(otherElement.getIoSpecification()).thenReturn(ioSpecification);
    when(otherElement.getLoopCharacteristics()).thenReturn(multiInstanceLoopCharacteristics);
    when(otherElement.isForCompensation()).thenReturn(true);
    when(otherElement.isAsynchronous()).thenReturn(true);
    when(otherElement.isNotExclusive()).thenReturn(true);
    when(otherElement.getDefaultFlow()).thenReturn("Default Flow");
    when(otherElement.getFailedJobRetryTimeCycleValue()).thenReturn("42");
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getDocumentation()).thenReturn("Documentation");
    when(otherElement.getName()).thenReturn("Name");

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
    verify(otherElement).getAttributes();
    verify(otherElement).getExtensionElements();
    verify(otherElement).getId();
    verify(dataAssociation).clone();
    verify(dataAssociation2).clone();
    verify(otherElement).getDocumentation();
    verify(otherElement, atLeast(1)).getExecutionListeners();
    verify(otherElement).getName();
    verify(otherElement).isAsynchronous();
    verify(otherElement).isNotExclusive();
    verify(ioSpecification).clone();
    verify(multiInstanceLoopCharacteristics).clone();
    assertTrue(task.hasMultiInstanceLoopCharacteristics());
    assertSame(ioSpecification2, task.getIoSpecification());
    assertSame(multiInstanceLoopCharacteristics2, task.getLoopCharacteristics());
  }

  /**
   * Test new {@link Task} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link Task}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Task.<init>()"})
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
