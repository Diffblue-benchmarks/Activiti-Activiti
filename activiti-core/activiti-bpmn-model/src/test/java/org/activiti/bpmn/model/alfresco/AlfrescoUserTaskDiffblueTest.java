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
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.FieldExtension;
import org.activiti.bpmn.model.FormProperty;
import org.junit.Test;
import org.mockito.Mockito;

public class AlfrescoUserTaskDiffblueTest {
  /**
   * Test {@link AlfrescoUserTask#clone()}.
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor) FieldExtensions is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AlfrescoUserTask#clone()}
   */
  @Test
  public void testClone_givenActivitiListenerFieldExtensionsIsNull() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(null);

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(activitiListener);

    AlfrescoUserTask alfrescoUserTask = new AlfrescoUserTask();
    alfrescoUserTask.setFormProperties(null);
    alfrescoUserTask.setTaskListeners(taskListeners);

    // Act and Assert
    List<ActivitiListener> taskListeners2 = alfrescoUserTask.clone().getTaskListeners();
    assertEquals(1, taskListeners2.size());
    ActivitiListener getResult = taskListeners2.get(0);
    assertNull(getResult.getInstance());
    assertNull(getResult.getCustomPropertiesResolverImplementation());
    assertNull(getResult.getCustomPropertiesResolverImplementationType());
    assertNull(getResult.getEvent());
    assertNull(getResult.getImplementation());
    assertNull(getResult.getImplementationType());
    assertNull(getResult.getOnTransaction());
    assertNull(getResult.getId());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getFieldExtensions().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link AlfrescoUserTask#clone()}.
   * <ul>
   *   <li>Given {@link AlfrescoUserTask} (default constructor) TaskListeners is
   * {@code null}.</li>
   *   <li>Then return Behavior is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AlfrescoUserTask#clone()}
   */
  @Test
  public void testClone_givenAlfrescoUserTaskTaskListenersIsNull_thenReturnBehaviorIsNull() {
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
   * Test {@link AlfrescoUserTask#clone()}.
   * <ul>
   *   <li>Given {@link AlfrescoUserTask} (default constructor).</li>
   *   <li>Then return Behavior is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AlfrescoUserTask#clone()}
   */
  @Test
  public void testClone_givenAlfrescoUserTask_thenReturnBehaviorIsNull() {
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
   * Test {@link AlfrescoUserTask#clone()}.
   * <ul>
   *   <li>Given {@link FormProperty} (default constructor) FormValues is
   * {@code null}.</li>
   *   <li>Then return FormProperties size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link AlfrescoUserTask#clone()}
   */
  @Test
  public void testClone_givenFormPropertyFormValuesIsNull_thenReturnFormPropertiesSizeIsOne() {
    // Arrange
    FormProperty formProperty = new FormProperty();
    formProperty.setFormValues(null);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);

    AlfrescoUserTask alfrescoUserTask = new AlfrescoUserTask();
    alfrescoUserTask.setFormProperties(formProperties);
    alfrescoUserTask.setTaskListeners(null);

    // Act and Assert
    List<FormProperty> formProperties2 = alfrescoUserTask.clone().getFormProperties();
    assertEquals(1, formProperties2.size());
    FormProperty getResult = formProperties2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getDatePattern());
    assertNull(getResult.getDefaultExpression());
    assertNull(getResult.getExpression());
    assertNull(getResult.getName());
    assertNull(getResult.getType());
    assertNull(getResult.getVariable());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertFalse(getResult.isRequired());
    assertTrue(getResult.getFormValues().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(getResult.isReadable());
    assertTrue(getResult.isWriteable());
  }

  /**
   * Test {@link AlfrescoUserTask#clone()}.
   * <ul>
   *   <li>Given {@link FormProperty} (default constructor) FormValues is
   * {@code null}.</li>
   *   <li>Then return FormProperties size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link AlfrescoUserTask#clone()}
   */
  @Test
  public void testClone_givenFormPropertyFormValuesIsNull_thenReturnFormPropertiesSizeIsTwo() {
    // Arrange
    FormProperty formProperty = new FormProperty();
    formProperty.setFormValues(null);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(new FormProperty());
    formProperties.add(formProperty);

    AlfrescoUserTask alfrescoUserTask = new AlfrescoUserTask();
    alfrescoUserTask.setFormProperties(formProperties);
    alfrescoUserTask.setTaskListeners(null);

    // Act and Assert
    List<FormProperty> formProperties2 = alfrescoUserTask.clone().getFormProperties();
    assertEquals(2, formProperties2.size());
    FormProperty getResult = formProperties2.get(1);
    assertNull(getResult.getId());
    assertNull(getResult.getDatePattern());
    assertNull(getResult.getDefaultExpression());
    assertNull(getResult.getExpression());
    assertNull(getResult.getName());
    assertNull(getResult.getType());
    assertNull(getResult.getVariable());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertFalse(getResult.isRequired());
    assertTrue(getResult.getFormValues().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(getResult.isReadable());
    assertTrue(getResult.isWriteable());
  }

  /**
   * Test {@link AlfrescoUserTask#clone()}.
   * <ul>
   *   <li>Then return TaskListeners first FieldExtensions size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link AlfrescoUserTask#clone()}
   */
  @Test
  public void testClone_thenReturnTaskListenersFirstFieldExtensionsSizeIsOne() {
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

    // Act and Assert
    List<ActivitiListener> taskListeners2 = alfrescoUserTask.clone().getTaskListeners();
    assertEquals(1, taskListeners2.size());
    List<FieldExtension> fieldExtensions2 = taskListeners2.get(0).getFieldExtensions();
    assertEquals(1, fieldExtensions2.size());
    FieldExtension getResult = fieldExtensions2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getExpression());
    assertNull(getResult.getFieldName());
    assertNull(getResult.getStringValue());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link AlfrescoUserTask#clone()}.
   * <ul>
   *   <li>Then return TaskListeners first Instance is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AlfrescoUserTask#clone()}
   */
  @Test
  public void testClone_thenReturnTaskListenersFirstInstanceIsNull() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(new ArrayList<>());

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(activitiListener);

    AlfrescoUserTask alfrescoUserTask = new AlfrescoUserTask();
    alfrescoUserTask.setFormProperties(null);
    alfrescoUserTask.setTaskListeners(taskListeners);

    // Act and Assert
    List<ActivitiListener> taskListeners2 = alfrescoUserTask.clone().getTaskListeners();
    assertEquals(1, taskListeners2.size());
    ActivitiListener getResult = taskListeners2.get(0);
    assertNull(getResult.getInstance());
    assertNull(getResult.getCustomPropertiesResolverImplementation());
    assertNull(getResult.getCustomPropertiesResolverImplementationType());
    assertNull(getResult.getEvent());
    assertNull(getResult.getImplementation());
    assertNull(getResult.getImplementationType());
    assertNull(getResult.getOnTransaction());
    assertNull(getResult.getId());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getFieldExtensions().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link AlfrescoUserTask#setValues(AlfrescoUserTask)} with
   * {@code AlfrescoUserTask}.
   * <ul>
   *   <li>Then calls {@link ActivitiListener#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AlfrescoUserTask#setValues(AlfrescoUserTask)}
   */
  @Test
  public void testSetValuesWithAlfrescoUserTask_thenCallsClone() {
    // Arrange
    AlfrescoUserTask alfrescoUserTask = new AlfrescoUserTask();
    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.clone()).thenReturn(new ActivitiListener());
    doNothing().when(activitiListener).setFieldExtensions(Mockito.<List<FieldExtension>>any());
    activitiListener.setFieldExtensions(null);

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(activitiListener);

    AlfrescoUserTask otherElement = new AlfrescoUserTask();
    otherElement.setFormProperties(null);
    otherElement.setTaskListeners(taskListeners);

    // Act
    alfrescoUserTask.setValues(otherElement);

    // Assert
    verify(activitiListener).clone();
    verify(activitiListener).setFieldExtensions(isNull());
  }

  /**
   * Test getters and setters.
   * <p>
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
