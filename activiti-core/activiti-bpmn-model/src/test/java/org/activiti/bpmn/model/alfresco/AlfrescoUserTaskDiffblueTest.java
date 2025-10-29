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
package org.activiti.bpmn.model.alfresco;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.FieldExtension;
import org.activiti.bpmn.model.FormProperty;
import org.activiti.bpmn.model.MultiInstanceLoopCharacteristics;
import org.junit.Test;

public class AlfrescoUserTaskDiffblueTest {
  /**
   * Method under test: {@link AlfrescoUserTask#clone()}
   */
  @Test
  public void testClone() {
    // Arrange and Act
    AlfrescoUserTask actualCloneResult = (new AlfrescoUserTask()).clone();

    // Assert
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getDefaultFlow());
    assertNull(actualCloneResult.getFailedJobRetryTimeCycleValue());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getAssignee());
    assertNull(actualCloneResult.getBusinessCalendarName());
    assertNull(actualCloneResult.getCategory());
    assertNull(actualCloneResult.getDueDate());
    assertNull(actualCloneResult.getExtensionId());
    assertNull(actualCloneResult.getFormKey());
    assertNull(actualCloneResult.getOwner());
    assertNull(actualCloneResult.getPriority());
    assertNull(actualCloneResult.getSkipExpression());
    assertNull(actualCloneResult.getRunAs());
    assertNull(actualCloneResult.getScriptProcessor());
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
    assertFalse(actualCloneResult.isExtended());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
    assertTrue(actualCloneResult.getDataOutputAssociations().isEmpty());
    assertTrue(actualCloneResult.getMapExceptions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getCandidateGroups().isEmpty());
    assertTrue(actualCloneResult.getCandidateUsers().isEmpty());
    assertTrue(actualCloneResult.getCustomProperties().isEmpty());
    assertTrue(actualCloneResult.getFormProperties().isEmpty());
    assertTrue(actualCloneResult.getTaskListeners().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getCustomGroupIdentityLinks().isEmpty());
    assertTrue(actualCloneResult.getCustomUserIdentityLinks().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Method under test: {@link AlfrescoUserTask#clone()}
   */
  @Test
  public void testClone2() {
    // Arrange
    AlfrescoUserTask alfrescoUserTask = new AlfrescoUserTask();
    alfrescoUserTask.setFormProperties(null);
    alfrescoUserTask.setTaskListeners(null);

    // Act
    AlfrescoUserTask actualCloneResult = alfrescoUserTask.clone();

    // Assert
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getDefaultFlow());
    assertNull(actualCloneResult.getFailedJobRetryTimeCycleValue());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getAssignee());
    assertNull(actualCloneResult.getBusinessCalendarName());
    assertNull(actualCloneResult.getCategory());
    assertNull(actualCloneResult.getDueDate());
    assertNull(actualCloneResult.getExtensionId());
    assertNull(actualCloneResult.getFormKey());
    assertNull(actualCloneResult.getOwner());
    assertNull(actualCloneResult.getPriority());
    assertNull(actualCloneResult.getSkipExpression());
    assertNull(actualCloneResult.getRunAs());
    assertNull(actualCloneResult.getScriptProcessor());
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
    assertFalse(actualCloneResult.isExtended());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
    assertTrue(actualCloneResult.getDataOutputAssociations().isEmpty());
    assertTrue(actualCloneResult.getMapExceptions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getCandidateGroups().isEmpty());
    assertTrue(actualCloneResult.getCandidateUsers().isEmpty());
    assertTrue(actualCloneResult.getCustomProperties().isEmpty());
    assertTrue(actualCloneResult.getFormProperties().isEmpty());
    assertTrue(actualCloneResult.getTaskListeners().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getCustomGroupIdentityLinks().isEmpty());
    assertTrue(actualCloneResult.getCustomUserIdentityLinks().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Method under test: {@link AlfrescoUserTask#clone()}
   */
  @Test
  public void testClone3() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(null);

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(activitiListener);

    AlfrescoUserTask alfrescoUserTask = new AlfrescoUserTask();
    alfrescoUserTask.setFormProperties(null);
    alfrescoUserTask.setTaskListeners(taskListeners);

    // Act
    AlfrescoUserTask actualCloneResult = alfrescoUserTask.clone();

    // Assert
    List<ActivitiListener> taskListeners2 = actualCloneResult.getTaskListeners();
    assertEquals(1, taskListeners2.size());
    ActivitiListener getResult = taskListeners2.get(0);
    assertNull(getResult.getInstance());
    assertNull(actualCloneResult.getBehavior());
    assertNull(getResult.getCustomPropertiesResolverImplementation());
    assertNull(getResult.getCustomPropertiesResolverImplementationType());
    assertNull(getResult.getEvent());
    assertNull(getResult.getImplementation());
    assertNull(getResult.getImplementationType());
    assertNull(getResult.getOnTransaction());
    assertNull(actualCloneResult.getDefaultFlow());
    assertNull(actualCloneResult.getFailedJobRetryTimeCycleValue());
    assertNull(getResult.getId());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getAssignee());
    assertNull(actualCloneResult.getBusinessCalendarName());
    assertNull(actualCloneResult.getCategory());
    assertNull(actualCloneResult.getDueDate());
    assertNull(actualCloneResult.getExtensionId());
    assertNull(actualCloneResult.getFormKey());
    assertNull(actualCloneResult.getOwner());
    assertNull(actualCloneResult.getPriority());
    assertNull(actualCloneResult.getSkipExpression());
    assertNull(actualCloneResult.getRunAs());
    assertNull(actualCloneResult.getScriptProcessor());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertFalse(actualCloneResult.isForCompensation());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertFalse(actualCloneResult.isExtended());
    assertTrue(getResult.getFieldExtensions().isEmpty());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
    assertTrue(actualCloneResult.getDataOutputAssociations().isEmpty());
    assertTrue(actualCloneResult.getMapExceptions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getCandidateGroups().isEmpty());
    assertTrue(actualCloneResult.getCandidateUsers().isEmpty());
    assertTrue(actualCloneResult.getCustomProperties().isEmpty());
    assertTrue(actualCloneResult.getFormProperties().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getCustomGroupIdentityLinks().isEmpty());
    assertTrue(actualCloneResult.getCustomUserIdentityLinks().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Method under test: {@link AlfrescoUserTask#clone()}
   */
  @Test
  public void testClone4() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(new ArrayList<>());

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(activitiListener);

    AlfrescoUserTask alfrescoUserTask = new AlfrescoUserTask();
    alfrescoUserTask.setFormProperties(null);
    alfrescoUserTask.setTaskListeners(taskListeners);

    // Act
    AlfrescoUserTask actualCloneResult = alfrescoUserTask.clone();

    // Assert
    List<ActivitiListener> taskListeners2 = actualCloneResult.getTaskListeners();
    assertEquals(1, taskListeners2.size());
    ActivitiListener getResult = taskListeners2.get(0);
    assertNull(getResult.getInstance());
    assertNull(actualCloneResult.getBehavior());
    assertNull(getResult.getCustomPropertiesResolverImplementation());
    assertNull(getResult.getCustomPropertiesResolverImplementationType());
    assertNull(getResult.getEvent());
    assertNull(getResult.getImplementation());
    assertNull(getResult.getImplementationType());
    assertNull(getResult.getOnTransaction());
    assertNull(actualCloneResult.getDefaultFlow());
    assertNull(actualCloneResult.getFailedJobRetryTimeCycleValue());
    assertNull(getResult.getId());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getAssignee());
    assertNull(actualCloneResult.getBusinessCalendarName());
    assertNull(actualCloneResult.getCategory());
    assertNull(actualCloneResult.getDueDate());
    assertNull(actualCloneResult.getExtensionId());
    assertNull(actualCloneResult.getFormKey());
    assertNull(actualCloneResult.getOwner());
    assertNull(actualCloneResult.getPriority());
    assertNull(actualCloneResult.getSkipExpression());
    assertNull(actualCloneResult.getRunAs());
    assertNull(actualCloneResult.getScriptProcessor());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertFalse(actualCloneResult.isForCompensation());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertFalse(actualCloneResult.isExtended());
    assertTrue(getResult.getFieldExtensions().isEmpty());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
    assertTrue(actualCloneResult.getDataOutputAssociations().isEmpty());
    assertTrue(actualCloneResult.getMapExceptions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getCandidateGroups().isEmpty());
    assertTrue(actualCloneResult.getCandidateUsers().isEmpty());
    assertTrue(actualCloneResult.getCustomProperties().isEmpty());
    assertTrue(actualCloneResult.getFormProperties().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getCustomGroupIdentityLinks().isEmpty());
    assertTrue(actualCloneResult.getCustomUserIdentityLinks().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Method under test: {@link AlfrescoUserTask#clone()}
   */
  @Test
  public void testClone5() {
    // Arrange
    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(activitiListener);

    AlfrescoUserTask alfrescoUserTask = new AlfrescoUserTask();
    alfrescoUserTask.setFormProperties(null);
    alfrescoUserTask.setTaskListeners(taskListeners);

    // Act
    AlfrescoUserTask actualCloneResult = alfrescoUserTask.clone();

    // Assert
    List<ActivitiListener> taskListeners2 = actualCloneResult.getTaskListeners();
    assertEquals(1, taskListeners2.size());
    ActivitiListener getResult = taskListeners2.get(0);
    assertNull(getResult.getInstance());
    assertNull(actualCloneResult.getBehavior());
    assertNull(getResult.getCustomPropertiesResolverImplementation());
    assertNull(getResult.getCustomPropertiesResolverImplementationType());
    assertNull(getResult.getEvent());
    assertNull(getResult.getImplementation());
    assertNull(getResult.getImplementationType());
    assertNull(getResult.getOnTransaction());
    assertNull(actualCloneResult.getDefaultFlow());
    assertNull(actualCloneResult.getFailedJobRetryTimeCycleValue());
    assertNull(getResult.getId());
    List<FieldExtension> fieldExtensions2 = getResult.getFieldExtensions();
    assertEquals(1, fieldExtensions2.size());
    FieldExtension getResult2 = fieldExtensions2.get(0);
    assertNull(getResult2.getId());
    assertNull(actualCloneResult.getId());
    assertNull(getResult2.getExpression());
    assertNull(getResult2.getFieldName());
    assertNull(getResult2.getStringValue());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getAssignee());
    assertNull(actualCloneResult.getBusinessCalendarName());
    assertNull(actualCloneResult.getCategory());
    assertNull(actualCloneResult.getDueDate());
    assertNull(actualCloneResult.getExtensionId());
    assertNull(actualCloneResult.getFormKey());
    assertNull(actualCloneResult.getOwner());
    assertNull(actualCloneResult.getPriority());
    assertNull(actualCloneResult.getSkipExpression());
    assertNull(actualCloneResult.getRunAs());
    assertNull(actualCloneResult.getScriptProcessor());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertFalse(actualCloneResult.isForCompensation());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertFalse(actualCloneResult.isExtended());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
    assertTrue(actualCloneResult.getDataOutputAssociations().isEmpty());
    assertTrue(actualCloneResult.getMapExceptions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getCandidateGroups().isEmpty());
    assertTrue(actualCloneResult.getCandidateUsers().isEmpty());
    assertTrue(actualCloneResult.getCustomProperties().isEmpty());
    assertTrue(actualCloneResult.getFormProperties().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getCustomGroupIdentityLinks().isEmpty());
    assertTrue(actualCloneResult.getCustomUserIdentityLinks().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Method under test: {@link AlfrescoUserTask#clone()}
   */
  @Test
  public void testClone6() {
    // Arrange
    FormProperty formProperty = new FormProperty();
    formProperty.setFormValues(null);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);

    AlfrescoUserTask alfrescoUserTask = new AlfrescoUserTask();
    alfrescoUserTask.setFormProperties(formProperties);
    alfrescoUserTask.setTaskListeners(null);

    // Act
    AlfrescoUserTask actualCloneResult = alfrescoUserTask.clone();

    // Assert
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getDefaultFlow());
    assertNull(actualCloneResult.getFailedJobRetryTimeCycleValue());
    List<FormProperty> formProperties2 = actualCloneResult.getFormProperties();
    assertEquals(1, formProperties2.size());
    FormProperty getResult = formProperties2.get(0);
    assertNull(getResult.getId());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(getResult.getDatePattern());
    assertNull(getResult.getDefaultExpression());
    assertNull(getResult.getExpression());
    assertNull(getResult.getName());
    assertNull(getResult.getType());
    assertNull(getResult.getVariable());
    assertNull(actualCloneResult.getAssignee());
    assertNull(actualCloneResult.getBusinessCalendarName());
    assertNull(actualCloneResult.getCategory());
    assertNull(actualCloneResult.getDueDate());
    assertNull(actualCloneResult.getExtensionId());
    assertNull(actualCloneResult.getFormKey());
    assertNull(actualCloneResult.getOwner());
    assertNull(actualCloneResult.getPriority());
    assertNull(actualCloneResult.getSkipExpression());
    assertNull(actualCloneResult.getRunAs());
    assertNull(actualCloneResult.getScriptProcessor());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertFalse(actualCloneResult.isForCompensation());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertFalse(getResult.isRequired());
    assertFalse(actualCloneResult.isExtended());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
    assertTrue(actualCloneResult.getDataOutputAssociations().isEmpty());
    assertTrue(actualCloneResult.getMapExceptions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(getResult.getFormValues().isEmpty());
    assertTrue(actualCloneResult.getCandidateGroups().isEmpty());
    assertTrue(actualCloneResult.getCandidateUsers().isEmpty());
    assertTrue(actualCloneResult.getCustomProperties().isEmpty());
    assertTrue(actualCloneResult.getTaskListeners().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getCustomGroupIdentityLinks().isEmpty());
    assertTrue(actualCloneResult.getCustomUserIdentityLinks().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
    assertTrue(getResult.isReadable());
    assertTrue(getResult.isWriteable());
  }

  /**
   * Method under test: {@link AlfrescoUserTask#clone()}
   */
  @Test
  public void testClone7() {
    // Arrange
    FormProperty formProperty = new FormProperty();
    formProperty.setFormValues(null);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(new FormProperty());
    formProperties.add(formProperty);

    AlfrescoUserTask alfrescoUserTask = new AlfrescoUserTask();
    alfrescoUserTask.setFormProperties(formProperties);
    alfrescoUserTask.setTaskListeners(null);

    // Act
    AlfrescoUserTask actualCloneResult = alfrescoUserTask.clone();

    // Assert
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getDefaultFlow());
    assertNull(actualCloneResult.getFailedJobRetryTimeCycleValue());
    List<FormProperty> formProperties2 = actualCloneResult.getFormProperties();
    assertEquals(2, formProperties2.size());
    FormProperty getResult = formProperties2.get(0);
    assertNull(getResult.getId());
    FormProperty getResult2 = formProperties2.get(1);
    assertNull(getResult2.getId());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(getResult.getDatePattern());
    assertNull(getResult2.getDatePattern());
    assertNull(getResult.getDefaultExpression());
    assertNull(getResult2.getDefaultExpression());
    assertNull(getResult.getExpression());
    assertNull(getResult2.getExpression());
    assertNull(getResult.getName());
    assertNull(getResult2.getName());
    assertNull(getResult.getType());
    assertNull(getResult2.getType());
    assertNull(getResult.getVariable());
    assertNull(getResult2.getVariable());
    assertNull(actualCloneResult.getAssignee());
    assertNull(actualCloneResult.getBusinessCalendarName());
    assertNull(actualCloneResult.getCategory());
    assertNull(actualCloneResult.getDueDate());
    assertNull(actualCloneResult.getExtensionId());
    assertNull(actualCloneResult.getFormKey());
    assertNull(actualCloneResult.getOwner());
    assertNull(actualCloneResult.getPriority());
    assertNull(actualCloneResult.getSkipExpression());
    assertNull(actualCloneResult.getRunAs());
    assertNull(actualCloneResult.getScriptProcessor());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertFalse(actualCloneResult.isForCompensation());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertFalse(getResult.isRequired());
    assertFalse(getResult2.isRequired());
    assertFalse(actualCloneResult.isExtended());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
    assertTrue(actualCloneResult.getDataOutputAssociations().isEmpty());
    assertTrue(actualCloneResult.getMapExceptions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(getResult.getFormValues().isEmpty());
    assertTrue(getResult2.getFormValues().isEmpty());
    assertTrue(actualCloneResult.getCandidateGroups().isEmpty());
    assertTrue(actualCloneResult.getCandidateUsers().isEmpty());
    assertTrue(actualCloneResult.getCustomProperties().isEmpty());
    assertTrue(actualCloneResult.getTaskListeners().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getCustomGroupIdentityLinks().isEmpty());
    assertTrue(actualCloneResult.getCustomUserIdentityLinks().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
    assertTrue(getResult.isReadable());
    assertTrue(getResult2.isReadable());
    assertTrue(getResult.isWriteable());
    assertTrue(getResult2.isWriteable());
  }

  /**
   * Method under test: {@link AlfrescoUserTask#setValues(AlfrescoUserTask)}
   */
  @Test
  public void testSetValues() {
    // Arrange
    AlfrescoUserTask alfrescoUserTask = new AlfrescoUserTask();
    MultiInstanceLoopCharacteristics loopCharacteristics = mock(MultiInstanceLoopCharacteristics.class);
    when(loopCharacteristics.clone()).thenReturn(new MultiInstanceLoopCharacteristics());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    AlfrescoUserTask otherElement = new AlfrescoUserTask();
    otherElement.setLoopCharacteristics(loopCharacteristics);
    otherElement.setIoSpecification(null);
    otherElement.setDataInputAssociations(null);
    otherElement.setDataOutputAssociations(null);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    alfrescoUserTask.setValues(otherElement);

    // Assert
    verify(loopCharacteristics).clone();
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link AlfrescoUserTask}
   *   <li>{@link AlfrescoUserTask#setRunAs(String)}
   *   <li>{@link AlfrescoUserTask#setScriptProcessor(String)}
   *   <li>{@link AlfrescoUserTask#getRunAs()}
   *   <li>{@link AlfrescoUserTask#getScriptProcessor()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    AlfrescoUserTask actualAlfrescoUserTask = new AlfrescoUserTask();
    actualAlfrescoUserTask.setRunAs("Run As");
    actualAlfrescoUserTask.setScriptProcessor("Script Processor");
    String actualRunAs = actualAlfrescoUserTask.getRunAs();

    // Assert that nothing has changed
    assertEquals("Run As", actualRunAs);
    assertEquals("Script Processor", actualAlfrescoUserTask.getScriptProcessor());
    assertEquals(0, actualAlfrescoUserTask.getXmlColumnNumber());
    assertEquals(0, actualAlfrescoUserTask.getXmlRowNumber());
    assertFalse(actualAlfrescoUserTask.isForCompensation());
    assertFalse(actualAlfrescoUserTask.isAsynchronous());
    assertFalse(actualAlfrescoUserTask.isNotExclusive());
    assertTrue(actualAlfrescoUserTask.getBoundaryEvents().isEmpty());
    assertTrue(actualAlfrescoUserTask.getDataInputAssociations().isEmpty());
    assertTrue(actualAlfrescoUserTask.getDataOutputAssociations().isEmpty());
    assertTrue(actualAlfrescoUserTask.getMapExceptions().isEmpty());
    assertTrue(actualAlfrescoUserTask.getExecutionListeners().isEmpty());
    assertTrue(actualAlfrescoUserTask.getIncomingFlows().isEmpty());
    assertTrue(actualAlfrescoUserTask.getOutgoingFlows().isEmpty());
    assertTrue(actualAlfrescoUserTask.getCandidateGroups().isEmpty());
    assertTrue(actualAlfrescoUserTask.getCandidateUsers().isEmpty());
    assertTrue(actualAlfrescoUserTask.getCustomProperties().isEmpty());
    assertTrue(actualAlfrescoUserTask.getFormProperties().isEmpty());
    assertTrue(actualAlfrescoUserTask.getTaskListeners().isEmpty());
    assertTrue(actualAlfrescoUserTask.getAttributes().isEmpty());
    assertTrue(actualAlfrescoUserTask.getExtensionElements().isEmpty());
    assertTrue(actualAlfrescoUserTask.getCustomGroupIdentityLinks().isEmpty());
    assertTrue(actualAlfrescoUserTask.getCustomUserIdentityLinks().isEmpty());
  }
}
