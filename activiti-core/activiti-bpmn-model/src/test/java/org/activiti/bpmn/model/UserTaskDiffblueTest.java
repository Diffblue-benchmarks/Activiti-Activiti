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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.activiti.bpmn.model.alfresco.AlfrescoUserTask;
import org.junit.Test;
import org.mockito.Mockito;

public class UserTaskDiffblueTest {
  /**
   * Test {@link UserTask#isExtended()}.
   * <ul>
   *   <li>Given {@link UserTask} (default constructor) ExtensionId is empty
   * string.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserTask#isExtended()}
   */
  @Test
  public void testIsExtended_givenUserTaskExtensionIdIsEmptyString_thenReturnFalse() {
    // Arrange
    UserTask userTask = new UserTask();
    userTask.setExtensionId("");

    // Act and Assert
    assertFalse(userTask.isExtended());
  }

  /**
   * Test {@link UserTask#isExtended()}.
   * <ul>
   *   <li>Given {@link UserTask} (default constructor) ExtensionId is
   * {@code foo}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserTask#isExtended()}
   */
  @Test
  public void testIsExtended_givenUserTaskExtensionIdIsFoo_thenReturnTrue() {
    // Arrange
    UserTask userTask = new UserTask();
    userTask.setExtensionId("foo");

    // Act and Assert
    assertTrue(userTask.isExtended());
  }

  /**
   * Test {@link UserTask#isExtended()}.
   * <ul>
   *   <li>Given {@link UserTask} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserTask#isExtended()}
   */
  @Test
  public void testIsExtended_givenUserTask_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new UserTask()).isExtended());
  }

  /**
   * Test {@link UserTask#addCustomUserIdentityLink(String, String)}.
   * <ul>
   *   <li>Given {@link UserTask} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link UserTask#addCustomUserIdentityLink(String, String)}
   */
  @Test
  public void testAddCustomUserIdentityLink_givenUserTask() {
    // Arrange
    UserTask userTask = new UserTask();

    // Act
    userTask.addCustomUserIdentityLink("42", "Type");

    // Assert
    Map<String, Set<String>> customUserIdentityLinks = userTask.getCustomUserIdentityLinks();
    assertEquals(1, customUserIdentityLinks.size());
    Set<String> getResult = customUserIdentityLinks.get("Type");
    assertEquals(1, getResult.size());
    assertTrue(getResult.contains("42"));
  }

  /**
   * Test {@link UserTask#addCustomUserIdentityLink(String, String)}.
   * <ul>
   *   <li>Given {@link UserTask} (default constructor) addCustomUserIdentityLink
   * {@code 42} and {@code Type}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserTask#addCustomUserIdentityLink(String, String)}
   */
  @Test
  public void testAddCustomUserIdentityLink_givenUserTaskAddCustomUserIdentityLink42AndType() {
    // Arrange
    UserTask userTask = new UserTask();
    userTask.addCustomUserIdentityLink("42", "Type");

    // Act
    userTask.addCustomUserIdentityLink("42", "Type");

    // Assert
    Map<String, Set<String>> customUserIdentityLinks = userTask.getCustomUserIdentityLinks();
    assertEquals(1, customUserIdentityLinks.size());
    Set<String> getResult = customUserIdentityLinks.get("Type");
    assertEquals(1, getResult.size());
    assertTrue(getResult.contains("42"));
  }

  /**
   * Test {@link UserTask#addCustomGroupIdentityLink(String, String)}.
   * <ul>
   *   <li>Given {@link UserTask} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link UserTask#addCustomGroupIdentityLink(String, String)}
   */
  @Test
  public void testAddCustomGroupIdentityLink_givenUserTask() {
    // Arrange
    UserTask userTask = new UserTask();

    // Act
    userTask.addCustomGroupIdentityLink("42", "Type");

    // Assert
    Map<String, Set<String>> customGroupIdentityLinks = userTask.getCustomGroupIdentityLinks();
    assertEquals(1, customGroupIdentityLinks.size());
    Set<String> getResult = customGroupIdentityLinks.get("Type");
    assertEquals(1, getResult.size());
    assertTrue(getResult.contains("42"));
  }

  /**
   * Test {@link UserTask#addCustomGroupIdentityLink(String, String)}.
   * <ul>
   *   <li>Given {@link UserTask} (default constructor) addCustomGroupIdentityLink
   * {@code 42} and {@code Type}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link UserTask#addCustomGroupIdentityLink(String, String)}
   */
  @Test
  public void testAddCustomGroupIdentityLink_givenUserTaskAddCustomGroupIdentityLink42AndType() {
    // Arrange
    UserTask userTask = new UserTask();
    userTask.addCustomGroupIdentityLink("42", "Type");

    // Act
    userTask.addCustomGroupIdentityLink("42", "Type");

    // Assert
    Map<String, Set<String>> customGroupIdentityLinks = userTask.getCustomGroupIdentityLinks();
    assertEquals(1, customGroupIdentityLinks.size());
    Set<String> getResult = customGroupIdentityLinks.get("Type");
    assertEquals(1, getResult.size());
    assertTrue(getResult.contains("42"));
  }

  /**
   * Test {@link UserTask#clone()}.
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor) FieldExtensions is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserTask#clone()}
   */
  @Test
  public void testClone_givenActivitiListenerFieldExtensionsIsNull() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(null);

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(activitiListener);

    UserTask userTask = new UserTask();
    userTask.setFormProperties(null);
    userTask.setTaskListeners(taskListeners);

    // Act and Assert
    List<ActivitiListener> taskListeners2 = userTask.clone().getTaskListeners();
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
   * Test {@link UserTask#clone()}.
   * <ul>
   *   <li>Given {@link AlfrescoUserTask} (default constructor).</li>
   *   <li>Then return {@link AlfrescoUserTask}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserTask#clone()}
   */
  @Test
  public void testClone_givenAlfrescoUserTask_thenReturnAlfrescoUserTask() {
    // Arrange and Act
    AlfrescoUserTask actualCloneResult = (new AlfrescoUserTask()).clone();

    // Assert
    assertTrue(actualCloneResult instanceof AlfrescoUserTask);
    assertNull(((AlfrescoUserTask) actualCloneResult).getRunAs());
    assertNull(((AlfrescoUserTask) actualCloneResult).getScriptProcessor());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
  }

  /**
   * Test {@link UserTask#clone()}.
   * <ul>
   *   <li>Given {@link FormProperty} (default constructor) FormValues is
   * {@code null}.</li>
   *   <li>Then return FormProperties size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserTask#clone()}
   */
  @Test
  public void testClone_givenFormPropertyFormValuesIsNull_thenReturnFormPropertiesSizeIsOne() {
    // Arrange
    FormProperty formProperty = new FormProperty();
    formProperty.setFormValues(null);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);

    UserTask userTask = new UserTask();
    userTask.setFormProperties(formProperties);
    userTask.setTaskListeners(null);

    // Act and Assert
    List<FormProperty> formProperties2 = userTask.clone().getFormProperties();
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
   * Test {@link UserTask#clone()}.
   * <ul>
   *   <li>Given {@link UserTask} (default constructor) TaskListeners is
   * {@code null}.</li>
   *   <li>Then return IoSpecification is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserTask#clone()}
   */
  @Test
  public void testClone_givenUserTaskTaskListenersIsNull_thenReturnIoSpecificationIsNull() {
    // Arrange
    UserTask userTask = new UserTask();
    userTask.setFormProperties(null);
    userTask.setTaskListeners(null);

    // Act
    UserTask actualCloneResult = userTask.clone();

    // Assert
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
    assertTrue(actualCloneResult.getDataOutputAssociations().isEmpty());
    assertTrue(actualCloneResult.getFormProperties().isEmpty());
    assertTrue(actualCloneResult.getTaskListeners().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
  }

  /**
   * Test {@link UserTask#clone()}.
   * <ul>
   *   <li>Given {@link UserTask} (default constructor).</li>
   *   <li>Then return IoSpecification is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserTask#clone()}
   */
  @Test
  public void testClone_givenUserTask_thenReturnIoSpecificationIsNull() {
    // Arrange and Act
    UserTask actualCloneResult = (new UserTask()).clone();

    // Assert
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
    assertTrue(actualCloneResult.getDataOutputAssociations().isEmpty());
    assertTrue(actualCloneResult.getFormProperties().isEmpty());
    assertTrue(actualCloneResult.getTaskListeners().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
  }

  /**
   * Test {@link UserTask#clone()}.
   * <ul>
   *   <li>Then return Attributes size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserTask#clone()}
   */
  @Test
  public void testClone_thenReturnAttributesSizeIsOne() {
    // Arrange
    UserTask userTask = new UserTask();
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    userTask.addAttribute(attribute);

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = userTask.clone().getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link UserTask#clone()}.
   * <ul>
   *   <li>Then return Attributes size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserTask#clone()}
   */
  @Test
  public void testClone_thenReturnAttributesSizeIsTwo() {
    // Arrange
    UserTask userTask = new UserTask();
    ExtensionAttribute attribute = new ExtensionAttribute("42");
    userTask.addAttribute(attribute);
    userTask.addAttribute(new ExtensionAttribute("Name"));

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = userTask.clone().getAttributes();
    assertEquals(2, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("42");
    assertEquals(1, getResult.size());
    assertTrue(attributes.containsKey("Name"));
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link UserTask#clone()}.
   * <ul>
   *   <li>Then return BoundaryEvents is {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserTask#clone()}
   */
  @Test
  public void testClone_thenReturnBoundaryEventsIsArrayList() {
    // Arrange
    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    UserTask userTask = new UserTask();
    userTask.setLoopCharacteristics(null);
    userTask.setIoSpecification(null);
    userTask.setDataInputAssociations(null);
    userTask.setDataOutputAssociations(null);
    userTask.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    assertEquals(boundaryEvents, userTask.clone().getBoundaryEvents());
  }

  /**
   * Test {@link UserTask#clone()}.
   * <ul>
   *   <li>Then return DataInputAssociations size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserTask#clone()}
   */
  @Test
  public void testClone_thenReturnDataInputAssociationsSizeIsOne() {
    // Arrange
    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    UserTask userTask = new UserTask();
    userTask.setLoopCharacteristics(null);
    userTask.setIoSpecification(null);
    userTask.setDataInputAssociations(dataInputAssociations);
    userTask.setDataOutputAssociations(null);
    userTask.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    List<DataAssociation> dataInputAssociations2 = userTask.clone().getDataInputAssociations();
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
   * Test {@link UserTask#clone()}.
   * <ul>
   *   <li>Then return DataOutputAssociations size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserTask#clone()}
   */
  @Test
  public void testClone_thenReturnDataOutputAssociationsSizeIsOne() {
    // Arrange
    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    UserTask userTask = new UserTask();
    userTask.setLoopCharacteristics(null);
    userTask.setIoSpecification(null);
    userTask.setDataInputAssociations(null);
    userTask.setDataOutputAssociations(dataOutputAssociations);
    userTask.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    List<DataAssociation> dataOutputAssociations2 = userTask.clone().getDataOutputAssociations();
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
   * Test {@link UserTask#clone()}.
   * <ul>
   *   <li>Then return IoSpecification Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserTask#clone()}
   */
  @Test
  public void testClone_thenReturnIoSpecificationIdIsNull() {
    // Arrange
    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    UserTask userTask = new UserTask();
    userTask.setLoopCharacteristics(null);
    userTask.setIoSpecification(new IOSpecification());
    userTask.setDataInputAssociations(null);
    userTask.setDataOutputAssociations(null);
    userTask.setBoundaryEvents(boundaryEvents);

    // Act and Assert
    IOSpecification ioSpecification = userTask.clone().getIoSpecification();
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
   * Test {@link UserTask#clone()}.
   * <ul>
   *   <li>Then return LoopCharacteristics Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserTask#clone()}
   */
  @Test
  public void testClone_thenReturnLoopCharacteristicsIdIsNull() {
    // Arrange
    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    UserTask userTask = new UserTask();
    userTask.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    userTask.setIoSpecification(null);
    userTask.setDataInputAssociations(null);
    userTask.setDataOutputAssociations(null);
    userTask.setBoundaryEvents(boundaryEvents);

    // Act
    UserTask actualCloneResult = userTask.clone();

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
   * Test {@link UserTask#clone()}.
   * <ul>
   *   <li>Then return TaskListeners first FieldExtensions size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserTask#clone()}
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

    UserTask userTask = new UserTask();
    userTask.setFormProperties(null);
    userTask.setTaskListeners(taskListeners);

    // Act and Assert
    List<ActivitiListener> taskListeners2 = userTask.clone().getTaskListeners();
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
   * Test {@link UserTask#clone()}.
   * <ul>
   *   <li>Then return TaskListeners first Instance is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserTask#clone()}
   */
  @Test
  public void testClone_thenReturnTaskListenersFirstInstanceIsNull() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(new ArrayList<>());

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(activitiListener);

    UserTask userTask = new UserTask();
    userTask.setFormProperties(null);
    userTask.setTaskListeners(taskListeners);

    // Act and Assert
    List<ActivitiListener> taskListeners2 = userTask.clone().getTaskListeners();
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
   * Test {@link UserTask#setValues(UserTask)} with {@code UserTask}.
   * <p>
   * Method under test: {@link UserTask#setValues(UserTask)}
   */
  @Test
  public void testSetValuesWithUserTask() {
    // Arrange
    UserTask userTask = new UserTask();

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    UserTask otherElement = new UserTask();
    otherElement.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    otherElement.setIoSpecification(null);
    otherElement.setDataInputAssociations(null);
    otherElement.setDataOutputAssociations(null);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    userTask.setValues(otherElement);

    // Assert
    assertNull(otherElement.getDefaultFlow());
    assertNull(otherElement.getFailedJobRetryTimeCycleValue());
    assertNull(otherElement.getId());
    MultiInstanceLoopCharacteristics loopCharacteristics = otherElement.getLoopCharacteristics();
    assertNull(loopCharacteristics.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(loopCharacteristics.getCompletionCondition());
    assertNull(loopCharacteristics.getElementIndexVariable());
    assertNull(loopCharacteristics.getElementVariable());
    assertNull(loopCharacteristics.getInputDataItem());
    assertNull(loopCharacteristics.getLoopCardinality());
    assertNull(loopCharacteristics.getLoopDataOutputRef());
    assertNull(loopCharacteristics.getOutputDataItem());
    assertNull(otherElement.getAssignee());
    assertNull(otherElement.getCategory());
    assertNull(otherElement.getDueDate());
    assertNull(otherElement.getExtensionId());
    assertNull(otherElement.getFormKey());
    assertNull(otherElement.getOwner());
    assertNull(otherElement.getPriority());
    assertNull(otherElement.getSkipExpression());
    assertEquals(0, loopCharacteristics.getXmlColumnNumber());
    assertEquals(0, loopCharacteristics.getXmlRowNumber());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(loopCharacteristics.isSequential());
    assertFalse(otherElement.isExtended());
    assertTrue(loopCharacteristics.getAttributes().isEmpty());
    assertTrue(loopCharacteristics.getExtensionElements().isEmpty());
    assertTrue(otherElement.getCustomGroupIdentityLinks().isEmpty());
    assertTrue(otherElement.getCustomUserIdentityLinks().isEmpty());
    assertTrue(otherElement.hasMultiInstanceLoopCharacteristics());
    assertTrue(otherElement.isExclusive());
  }

  /**
   * Test {@link UserTask#setValues(UserTask)} with {@code UserTask}.
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor) FieldExtensions is
   * {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserTask#setValues(UserTask)}
   */
  @Test
  public void testSetValuesWithUserTask_givenActivitiListenerFieldExtensionsIsArrayList() {
    // Arrange
    UserTask userTask = new UserTask();

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(new ArrayList<>());

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(activitiListener);

    UserTask otherElement = new UserTask();
    otherElement.setFormProperties(null);
    otherElement.setTaskListeners(taskListeners);

    // Act
    userTask.setValues(otherElement);

    // Assert
    assertNull(otherElement.getDefaultFlow());
    assertNull(otherElement.getFailedJobRetryTimeCycleValue());
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getAssignee());
    assertNull(otherElement.getCategory());
    assertNull(otherElement.getDueDate());
    assertNull(otherElement.getExtensionId());
    assertNull(otherElement.getFormKey());
    assertNull(otherElement.getOwner());
    assertNull(otherElement.getPriority());
    assertNull(otherElement.getSkipExpression());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.getCustomGroupIdentityLinks().isEmpty());
    assertTrue(otherElement.getCustomUserIdentityLinks().isEmpty());
    assertTrue(otherElement.isExclusive());
  }

  /**
   * Test {@link UserTask#setValues(UserTask)} with {@code UserTask}.
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor) FieldExtensions is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserTask#setValues(UserTask)}
   */
  @Test
  public void testSetValuesWithUserTask_givenActivitiListenerFieldExtensionsIsNull() {
    // Arrange
    UserTask userTask = new UserTask();

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(null);

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(activitiListener);

    UserTask otherElement = new UserTask();
    otherElement.setFormProperties(null);
    otherElement.setTaskListeners(taskListeners);

    // Act
    userTask.setValues(otherElement);

    // Assert
    assertNull(otherElement.getDefaultFlow());
    assertNull(otherElement.getFailedJobRetryTimeCycleValue());
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getAssignee());
    assertNull(otherElement.getCategory());
    assertNull(otherElement.getDueDate());
    assertNull(otherElement.getExtensionId());
    assertNull(otherElement.getFormKey());
    assertNull(otherElement.getOwner());
    assertNull(otherElement.getPriority());
    assertNull(otherElement.getSkipExpression());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.getCustomGroupIdentityLinks().isEmpty());
    assertTrue(otherElement.getCustomUserIdentityLinks().isEmpty());
    assertTrue(otherElement.isExclusive());
  }

  /**
   * Test {@link UserTask#setValues(UserTask)} with {@code UserTask}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link DataAssociation} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link UserTask#setValues(UserTask)}
   */
  @Test
  public void testSetValuesWithUserTask_givenArrayListAddDataAssociation() {
    // Arrange
    UserTask userTask = new UserTask();

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    UserTask otherElement = new UserTask();
    otherElement.setLoopCharacteristics(null);
    otherElement.setIoSpecification(null);
    otherElement.setDataInputAssociations(null);
    otherElement.setDataOutputAssociations(dataOutputAssociations);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    userTask.setValues(otherElement);

    // Assert
    assertNull(otherElement.getDefaultFlow());
    assertNull(otherElement.getFailedJobRetryTimeCycleValue());
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getAssignee());
    assertNull(otherElement.getCategory());
    assertNull(otherElement.getDueDate());
    assertNull(otherElement.getExtensionId());
    assertNull(otherElement.getFormKey());
    assertNull(otherElement.getOwner());
    assertNull(otherElement.getPriority());
    assertNull(otherElement.getSkipExpression());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.getCustomGroupIdentityLinks().isEmpty());
    assertTrue(otherElement.getCustomUserIdentityLinks().isEmpty());
    assertTrue(otherElement.isExclusive());
  }

  /**
   * Test {@link UserTask#setValues(UserTask)} with {@code UserTask}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link DataAssociation} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link UserTask#setValues(UserTask)}
   */
  @Test
  public void testSetValuesWithUserTask_givenArrayListAddDataAssociation2() {
    // Arrange
    UserTask userTask = new UserTask();

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    UserTask otherElement = new UserTask();
    otherElement.setLoopCharacteristics(null);
    otherElement.setIoSpecification(null);
    otherElement.setDataInputAssociations(dataInputAssociations);
    otherElement.setDataOutputAssociations(null);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    userTask.setValues(otherElement);

    // Assert
    assertNull(otherElement.getDefaultFlow());
    assertNull(otherElement.getFailedJobRetryTimeCycleValue());
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getAssignee());
    assertNull(otherElement.getCategory());
    assertNull(otherElement.getDueDate());
    assertNull(otherElement.getExtensionId());
    assertNull(otherElement.getFormKey());
    assertNull(otherElement.getOwner());
    assertNull(otherElement.getPriority());
    assertNull(otherElement.getSkipExpression());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.getCustomGroupIdentityLinks().isEmpty());
    assertTrue(otherElement.getCustomUserIdentityLinks().isEmpty());
    assertTrue(otherElement.isExclusive());
  }

  /**
   * Test {@link UserTask#setValues(UserTask)} with {@code UserTask}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link FieldExtension} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link UserTask#setValues(UserTask)}
   */
  @Test
  public void testSetValuesWithUserTask_givenArrayListAddFieldExtension() {
    // Arrange
    UserTask userTask = new UserTask();

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(activitiListener);

    UserTask otherElement = new UserTask();
    otherElement.setFormProperties(null);
    otherElement.setTaskListeners(taskListeners);

    // Act
    userTask.setValues(otherElement);

    // Assert
    assertNull(otherElement.getDefaultFlow());
    assertNull(otherElement.getFailedJobRetryTimeCycleValue());
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getAssignee());
    assertNull(otherElement.getCategory());
    assertNull(otherElement.getDueDate());
    assertNull(otherElement.getExtensionId());
    assertNull(otherElement.getFormKey());
    assertNull(otherElement.getOwner());
    assertNull(otherElement.getPriority());
    assertNull(otherElement.getSkipExpression());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.getCustomGroupIdentityLinks().isEmpty());
    assertTrue(otherElement.getCustomUserIdentityLinks().isEmpty());
    assertTrue(otherElement.isExclusive());
  }

  /**
   * Test {@link UserTask#setValues(UserTask)} with {@code UserTask}.
   * <ul>
   *   <li>Given {@code true}.</li>
   *   <li>Then {@link UserTask} (default constructor) DueDate is
   * {@code 2020-03-01}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserTask#setValues(UserTask)}
   */
  @Test
  public void testSetValuesWithUserTask_givenTrue_thenUserTaskDueDateIs20200301() {
    // Arrange
    UserTask userTask = new UserTask();
    AlfrescoUserTask otherElement = mock(AlfrescoUserTask.class);
    when(otherElement.isForCompensation()).thenReturn(true);
    when(otherElement.isAsynchronous()).thenReturn(true);
    when(otherElement.isNotExclusive()).thenReturn(true);
    when(otherElement.getDefaultFlow()).thenReturn("Default Flow");
    when(otherElement.getFailedJobRetryTimeCycleValue()).thenReturn("42");
    when(otherElement.getId()).thenReturn("42");
    when(otherElement.getDocumentation()).thenReturn("Documentation");
    when(otherElement.getName()).thenReturn("Name");
    when(otherElement.getAssignee()).thenReturn("Assignee");
    when(otherElement.getCategory()).thenReturn("Category");
    when(otherElement.getDueDate()).thenReturn("2020-03-01");
    when(otherElement.getExtensionId()).thenReturn("42");
    when(otherElement.getFormKey()).thenReturn("Form Key");
    when(otherElement.getOwner()).thenReturn("Owner");
    when(otherElement.getPriority()).thenReturn("Priority");
    when(otherElement.getSkipExpression()).thenReturn("Skip Expression");
    when(otherElement.getBoundaryEvents()).thenReturn(new ArrayList<>());
    when(otherElement.getDataInputAssociations()).thenReturn(new ArrayList<>());
    when(otherElement.getDataOutputAssociations()).thenReturn(new ArrayList<>());
    when(otherElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherElement.getCandidateGroups()).thenReturn(new ArrayList<>());
    when(otherElement.getCandidateUsers()).thenReturn(new ArrayList<>());
    when(otherElement.getFormProperties()).thenReturn(new ArrayList<>());
    when(otherElement.getTaskListeners()).thenReturn(new ArrayList<>());
    when(otherElement.getAttributes()).thenReturn(new HashMap<>());
    when(otherElement.getExtensionElements()).thenReturn(new HashMap<>());
    when(otherElement.getIoSpecification()).thenReturn(new IOSpecification());
    when(otherElement.getLoopCharacteristics()).thenReturn(new MultiInstanceLoopCharacteristics());

    // Act
    userTask.setValues(otherElement);

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
    verify(otherElement).getAssignee();
    verify(otherElement).getCandidateGroups();
    verify(otherElement).getCandidateUsers();
    verify(otherElement).getCategory();
    verify(otherElement).getDueDate();
    verify(otherElement).getExtensionId();
    verify(otherElement).getFormKey();
    verify(otherElement, atLeast(1)).getFormProperties();
    verify(otherElement).getOwner();
    verify(otherElement).getPriority();
    verify(otherElement).getSkipExpression();
    verify(otherElement, atLeast(1)).getTaskListeners();
    assertEquals("2020-03-01", userTask.getDueDate());
    assertEquals("42", userTask.getFailedJobRetryTimeCycleValue());
    assertEquals("42", userTask.getId());
    assertEquals("42", userTask.getExtensionId());
    assertEquals("Assignee", userTask.getAssignee());
    assertEquals("Category", userTask.getCategory());
    assertEquals("Default Flow", userTask.getDefaultFlow());
    assertEquals("Documentation", userTask.getDocumentation());
    assertEquals("Form Key", userTask.getFormKey());
    assertEquals("Name", userTask.getName());
    assertEquals("Owner", userTask.getOwner());
    assertEquals("Priority", userTask.getPriority());
    assertEquals("Skip Expression", userTask.getSkipExpression());
    IOSpecification ioSpecification = userTask.getIoSpecification();
    assertNull(ioSpecification.getId());
    MultiInstanceLoopCharacteristics loopCharacteristics = userTask.getLoopCharacteristics();
    assertNull(loopCharacteristics.getId());
    assertNull(loopCharacteristics.getCompletionCondition());
    assertNull(loopCharacteristics.getElementIndexVariable());
    assertNull(loopCharacteristics.getElementVariable());
    assertNull(loopCharacteristics.getInputDataItem());
    assertNull(loopCharacteristics.getLoopCardinality());
    assertNull(loopCharacteristics.getLoopDataOutputRef());
    assertNull(loopCharacteristics.getOutputDataItem());
    assertNull(userTask.getCustomGroupIdentityLinks());
    assertNull(userTask.getCustomUserIdentityLinks());
    assertEquals(0, ioSpecification.getXmlColumnNumber());
    assertEquals(0, loopCharacteristics.getXmlColumnNumber());
    assertEquals(0, ioSpecification.getXmlRowNumber());
    assertEquals(0, loopCharacteristics.getXmlRowNumber());
    assertFalse(userTask.isExclusive());
    assertFalse(loopCharacteristics.isSequential());
    assertTrue(ioSpecification.getDataInputRefs().isEmpty());
    assertTrue(ioSpecification.getDataInputs().isEmpty());
    assertTrue(ioSpecification.getDataOutputRefs().isEmpty());
    assertTrue(ioSpecification.getDataOutputs().isEmpty());
    assertTrue(ioSpecification.getAttributes().isEmpty());
    assertTrue(loopCharacteristics.getAttributes().isEmpty());
    assertTrue(ioSpecification.getExtensionElements().isEmpty());
    assertTrue(loopCharacteristics.getExtensionElements().isEmpty());
    assertTrue(userTask.hasMultiInstanceLoopCharacteristics());
    assertTrue(userTask.isForCompensation());
    assertTrue(userTask.isAsynchronous());
    assertTrue(userTask.isNotExclusive());
    assertTrue(userTask.isExtended());
  }

  /**
   * Test {@link UserTask#setValues(UserTask)} with {@code UserTask}.
   * <ul>
   *   <li>Then calls {@link MultiInstanceLoopCharacteristics#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserTask#setValues(UserTask)}
   */
  @Test
  public void testSetValuesWithUserTask_thenCallsClone() {
    // Arrange
    UserTask userTask = new UserTask();
    MultiInstanceLoopCharacteristics loopCharacteristics = mock(MultiInstanceLoopCharacteristics.class);
    when(loopCharacteristics.clone()).thenReturn(new MultiInstanceLoopCharacteristics());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    UserTask otherElement = new UserTask();
    otherElement.setLoopCharacteristics(loopCharacteristics);
    otherElement.setIoSpecification(null);
    otherElement.setDataInputAssociations(null);
    otherElement.setDataOutputAssociations(null);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    userTask.setValues(otherElement);

    // Assert
    verify(loopCharacteristics).clone();
    assertNull(otherElement.getDefaultFlow());
    assertNull(otherElement.getFailedJobRetryTimeCycleValue());
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getAssignee());
    assertNull(otherElement.getCategory());
    assertNull(otherElement.getDueDate());
    assertNull(otherElement.getExtensionId());
    assertNull(otherElement.getFormKey());
    assertNull(otherElement.getOwner());
    assertNull(otherElement.getPriority());
    assertNull(otherElement.getSkipExpression());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.getCustomGroupIdentityLinks().isEmpty());
    assertTrue(otherElement.getCustomUserIdentityLinks().isEmpty());
    assertTrue(otherElement.hasMultiInstanceLoopCharacteristics());
    assertTrue(otherElement.isExclusive());
  }

  /**
   * Test {@link UserTask#setValues(UserTask)} with {@code UserTask}.
   * <ul>
   *   <li>Then calls {@link IOSpecification#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserTask#setValues(UserTask)}
   */
  @Test
  public void testSetValuesWithUserTask_thenCallsClone2() {
    // Arrange
    UserTask userTask = new UserTask();
    MultiInstanceLoopCharacteristics loopCharacteristics = mock(MultiInstanceLoopCharacteristics.class);
    when(loopCharacteristics.clone()).thenReturn(new MultiInstanceLoopCharacteristics());
    IOSpecification ioSpecification = mock(IOSpecification.class);
    when(ioSpecification.clone()).thenReturn(new IOSpecification());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    UserTask otherElement = new UserTask();
    otherElement.setLoopCharacteristics(loopCharacteristics);
    otherElement.setIoSpecification(ioSpecification);
    otherElement.setDataInputAssociations(null);
    otherElement.setDataOutputAssociations(null);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    userTask.setValues(otherElement);

    // Assert
    verify(ioSpecification).clone();
    verify(loopCharacteristics).clone();
    assertNull(otherElement.getDefaultFlow());
    assertNull(otherElement.getFailedJobRetryTimeCycleValue());
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getAssignee());
    assertNull(otherElement.getCategory());
    assertNull(otherElement.getDueDate());
    assertNull(otherElement.getExtensionId());
    assertNull(otherElement.getFormKey());
    assertNull(otherElement.getOwner());
    assertNull(otherElement.getPriority());
    assertNull(otherElement.getSkipExpression());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.getCustomGroupIdentityLinks().isEmpty());
    assertTrue(otherElement.getCustomUserIdentityLinks().isEmpty());
    assertTrue(otherElement.hasMultiInstanceLoopCharacteristics());
    assertTrue(otherElement.isExclusive());
  }

  /**
   * Test {@link UserTask#setValues(UserTask)} with {@code UserTask}.
   * <ul>
   *   <li>Then calls {@link DataAssociation#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserTask#setValues(UserTask)}
   */
  @Test
  public void testSetValuesWithUserTask_thenCallsClone3() {
    // Arrange
    UserTask userTask = new UserTask();
    DataAssociation dataAssociation = mock(DataAssociation.class);
    when(dataAssociation.clone()).thenReturn(new DataAssociation());

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(dataAssociation);

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    UserTask otherElement = new UserTask();
    otherElement.setLoopCharacteristics(null);
    otherElement.setIoSpecification(null);
    otherElement.setDataInputAssociations(null);
    otherElement.setDataOutputAssociations(dataOutputAssociations);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    userTask.setValues(otherElement);

    // Assert
    verify(dataAssociation).clone();
    assertNull(otherElement.getDefaultFlow());
    assertNull(otherElement.getFailedJobRetryTimeCycleValue());
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getAssignee());
    assertNull(otherElement.getCategory());
    assertNull(otherElement.getDueDate());
    assertNull(otherElement.getExtensionId());
    assertNull(otherElement.getFormKey());
    assertNull(otherElement.getOwner());
    assertNull(otherElement.getPriority());
    assertNull(otherElement.getSkipExpression());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.getCustomGroupIdentityLinks().isEmpty());
    assertTrue(otherElement.getCustomUserIdentityLinks().isEmpty());
    assertTrue(otherElement.isExclusive());
  }

  /**
   * Test {@link UserTask#setValues(UserTask)} with {@code UserTask}.
   * <ul>
   *   <li>Then calls {@link DataAssociation#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserTask#setValues(UserTask)}
   */
  @Test
  public void testSetValuesWithUserTask_thenCallsClone4() {
    // Arrange
    UserTask userTask = new UserTask();
    DataAssociation dataAssociation = mock(DataAssociation.class);
    when(dataAssociation.clone()).thenReturn(new DataAssociation());

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(dataAssociation);

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    UserTask otherElement = new UserTask();
    otherElement.setLoopCharacteristics(null);
    otherElement.setIoSpecification(null);
    otherElement.setDataInputAssociations(dataInputAssociations);
    otherElement.setDataOutputAssociations(null);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    userTask.setValues(otherElement);

    // Assert
    verify(dataAssociation).clone();
    assertNull(otherElement.getDefaultFlow());
    assertNull(otherElement.getFailedJobRetryTimeCycleValue());
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getAssignee());
    assertNull(otherElement.getCategory());
    assertNull(otherElement.getDueDate());
    assertNull(otherElement.getExtensionId());
    assertNull(otherElement.getFormKey());
    assertNull(otherElement.getOwner());
    assertNull(otherElement.getPriority());
    assertNull(otherElement.getSkipExpression());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.getCustomGroupIdentityLinks().isEmpty());
    assertTrue(otherElement.getCustomUserIdentityLinks().isEmpty());
    assertTrue(otherElement.isExclusive());
  }

  /**
   * Test {@link UserTask#setValues(UserTask)} with {@code UserTask}.
   * <ul>
   *   <li>When {@link UserTask} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link UserTask#setValues(UserTask)}
   */
  @Test
  public void testSetValuesWithUserTask_whenUserTask() {
    // Arrange
    UserTask userTask = new UserTask();
    UserTask otherElement = new UserTask();

    // Act
    userTask.setValues(otherElement);

    // Assert
    assertNull(otherElement.getDefaultFlow());
    assertNull(otherElement.getFailedJobRetryTimeCycleValue());
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getAssignee());
    assertNull(otherElement.getCategory());
    assertNull(otherElement.getDueDate());
    assertNull(otherElement.getExtensionId());
    assertNull(otherElement.getFormKey());
    assertNull(otherElement.getOwner());
    assertNull(otherElement.getPriority());
    assertNull(otherElement.getSkipExpression());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.getCustomGroupIdentityLinks().isEmpty());
    assertTrue(otherElement.getCustomUserIdentityLinks().isEmpty());
    assertTrue(otherElement.isExclusive());
  }

  /**
   * Test {@link UserTask#setValues(UserTask)} with {@code UserTask}.
   * <ul>
   *   <li>When {@link UserTask} (default constructor) IoSpecification is
   * {@link IOSpecification} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link UserTask#setValues(UserTask)}
   */
  @Test
  public void testSetValuesWithUserTask_whenUserTaskIoSpecificationIsIOSpecification() {
    // Arrange
    UserTask userTask = new UserTask();

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    UserTask otherElement = new UserTask();
    otherElement.setLoopCharacteristics(null);
    otherElement.setIoSpecification(new IOSpecification());
    otherElement.setDataInputAssociations(null);
    otherElement.setDataOutputAssociations(null);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    userTask.setValues(otherElement);

    // Assert
    assertNull(otherElement.getDefaultFlow());
    assertNull(otherElement.getFailedJobRetryTimeCycleValue());
    assertNull(otherElement.getId());
    IOSpecification ioSpecification = otherElement.getIoSpecification();
    assertNull(ioSpecification.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getAssignee());
    assertNull(otherElement.getCategory());
    assertNull(otherElement.getDueDate());
    assertNull(otherElement.getExtensionId());
    assertNull(otherElement.getFormKey());
    assertNull(otherElement.getOwner());
    assertNull(otherElement.getPriority());
    assertNull(otherElement.getSkipExpression());
    assertEquals(0, ioSpecification.getXmlColumnNumber());
    assertEquals(0, ioSpecification.getXmlRowNumber());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(ioSpecification.getDataInputRefs().isEmpty());
    assertTrue(ioSpecification.getDataInputs().isEmpty());
    assertTrue(ioSpecification.getDataOutputRefs().isEmpty());
    assertTrue(ioSpecification.getDataOutputs().isEmpty());
    assertTrue(ioSpecification.getAttributes().isEmpty());
    assertTrue(ioSpecification.getExtensionElements().isEmpty());
    assertTrue(otherElement.getCustomGroupIdentityLinks().isEmpty());
    assertTrue(otherElement.getCustomUserIdentityLinks().isEmpty());
    assertTrue(otherElement.isExclusive());
  }

  /**
   * Test {@link UserTask#setValues(UserTask)} with {@code UserTask}.
   * <ul>
   *   <li>When {@link UserTask} (default constructor) LoopCharacteristics is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserTask#setValues(UserTask)}
   */
  @Test
  public void testSetValuesWithUserTask_whenUserTaskLoopCharacteristicsIsNull() {
    // Arrange
    UserTask userTask = new UserTask();

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    UserTask otherElement = new UserTask();
    otherElement.setLoopCharacteristics(null);
    otherElement.setIoSpecification(null);
    otherElement.setDataInputAssociations(null);
    otherElement.setDataOutputAssociations(null);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    userTask.setValues(otherElement);

    // Assert
    assertNull(otherElement.getDefaultFlow());
    assertNull(otherElement.getFailedJobRetryTimeCycleValue());
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getAssignee());
    assertNull(otherElement.getCategory());
    assertNull(otherElement.getDueDate());
    assertNull(otherElement.getExtensionId());
    assertNull(otherElement.getFormKey());
    assertNull(otherElement.getOwner());
    assertNull(otherElement.getPriority());
    assertNull(otherElement.getSkipExpression());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.getCustomGroupIdentityLinks().isEmpty());
    assertTrue(otherElement.getCustomUserIdentityLinks().isEmpty());
    assertTrue(otherElement.isExclusive());
  }

  /**
   * Test {@link UserTask#setValues(UserTask)} with {@code UserTask}.
   * <ul>
   *   <li>When {@link UserTask} (default constructor) TaskListeners is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UserTask#setValues(UserTask)}
   */
  @Test
  public void testSetValuesWithUserTask_whenUserTaskTaskListenersIsNull() {
    // Arrange
    UserTask userTask = new UserTask();

    UserTask otherElement = new UserTask();
    otherElement.setFormProperties(null);
    otherElement.setTaskListeners(null);

    // Act
    userTask.setValues(otherElement);

    // Assert
    assertNull(otherElement.getDefaultFlow());
    assertNull(otherElement.getFailedJobRetryTimeCycleValue());
    assertNull(otherElement.getId());
    assertNull(otherElement.getDocumentation());
    assertNull(otherElement.getName());
    assertNull(otherElement.getAssignee());
    assertNull(otherElement.getCategory());
    assertNull(otherElement.getDueDate());
    assertNull(otherElement.getExtensionId());
    assertNull(otherElement.getFormKey());
    assertNull(otherElement.getOwner());
    assertNull(otherElement.getPriority());
    assertNull(otherElement.getSkipExpression());
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.getCustomGroupIdentityLinks().isEmpty());
    assertTrue(otherElement.getCustomUserIdentityLinks().isEmpty());
    assertTrue(otherElement.isExclusive());
  }

  /**
   * Test {@link UserTask#accept(ReferenceOverrider)}.
   * <p>
   * Method under test: {@link UserTask#accept(ReferenceOverrider)}
   */
  @Test
  public void testAccept() {
    // Arrange
    UserTask userTask = new UserTask();
    ReferenceOverrider referenceOverrider = mock(ReferenceOverrider.class);
    doNothing().when(referenceOverrider).override(Mockito.<UserTask>any());

    // Act
    userTask.accept(referenceOverrider);

    // Assert
    verify(referenceOverrider).override(isA(UserTask.class));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link UserTask}
   *   <li>{@link UserTask#setAssignee(String)}
   *   <li>{@link UserTask#setBusinessCalendarName(String)}
   *   <li>{@link UserTask#setCandidateGroups(List)}
   *   <li>{@link UserTask#setCandidateUsers(List)}
   *   <li>{@link UserTask#setCategory(String)}
   *   <li>{@link UserTask#setCustomGroupIdentityLinks(Map)}
   *   <li>{@link UserTask#setCustomProperties(List)}
   *   <li>{@link UserTask#setCustomUserIdentityLinks(Map)}
   *   <li>{@link UserTask#setDueDate(String)}
   *   <li>{@link UserTask#setExtensionId(String)}
   *   <li>{@link UserTask#setFormKey(String)}
   *   <li>{@link UserTask#setFormProperties(List)}
   *   <li>{@link UserTask#setOwner(String)}
   *   <li>{@link UserTask#setPriority(String)}
   *   <li>{@link UserTask#setSkipExpression(String)}
   *   <li>{@link UserTask#setTaskListeners(List)}
   *   <li>{@link UserTask#getAssignee()}
   *   <li>{@link UserTask#getBusinessCalendarName()}
   *   <li>{@link UserTask#getCandidateGroups()}
   *   <li>{@link UserTask#getCandidateUsers()}
   *   <li>{@link UserTask#getCategory()}
   *   <li>{@link UserTask#getCustomGroupIdentityLinks()}
   *   <li>{@link UserTask#getCustomProperties()}
   *   <li>{@link UserTask#getCustomUserIdentityLinks()}
   *   <li>{@link UserTask#getDueDate()}
   *   <li>{@link UserTask#getExtensionId()}
   *   <li>{@link UserTask#getFormKey()}
   *   <li>{@link UserTask#getFormProperties()}
   *   <li>{@link UserTask#getOwner()}
   *   <li>{@link UserTask#getPriority()}
   *   <li>{@link UserTask#getSkipExpression()}
   *   <li>{@link UserTask#getTaskListeners()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    UserTask actualUserTask = new UserTask();
    actualUserTask.setAssignee("Assignee");
    actualUserTask.setBusinessCalendarName("Business Calendar Name");
    ArrayList<String> candidateGroups = new ArrayList<>();
    actualUserTask.setCandidateGroups(candidateGroups);
    ArrayList<String> candidateUsers = new ArrayList<>();
    actualUserTask.setCandidateUsers(candidateUsers);
    actualUserTask.setCategory("Category");
    HashMap<String, Set<String>> customGroupIdentityLinks = new HashMap<>();
    actualUserTask.setCustomGroupIdentityLinks(customGroupIdentityLinks);
    ArrayList<CustomProperty> customProperties = new ArrayList<>();
    actualUserTask.setCustomProperties(customProperties);
    HashMap<String, Set<String>> customUserIdentityLinks = new HashMap<>();
    actualUserTask.setCustomUserIdentityLinks(customUserIdentityLinks);
    actualUserTask.setDueDate("2020-03-01");
    actualUserTask.setExtensionId("42");
    actualUserTask.setFormKey("Form Key");
    ArrayList<FormProperty> formProperties = new ArrayList<>();
    actualUserTask.setFormProperties(formProperties);
    actualUserTask.setOwner("Owner");
    actualUserTask.setPriority("Priority");
    actualUserTask.setSkipExpression("Skip Expression");
    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    actualUserTask.setTaskListeners(taskListeners);
    String actualAssignee = actualUserTask.getAssignee();
    String actualBusinessCalendarName = actualUserTask.getBusinessCalendarName();
    List<String> actualCandidateGroups = actualUserTask.getCandidateGroups();
    List<String> actualCandidateUsers = actualUserTask.getCandidateUsers();
    String actualCategory = actualUserTask.getCategory();
    Map<String, Set<String>> actualCustomGroupIdentityLinks = actualUserTask.getCustomGroupIdentityLinks();
    List<CustomProperty> actualCustomProperties = actualUserTask.getCustomProperties();
    Map<String, Set<String>> actualCustomUserIdentityLinks = actualUserTask.getCustomUserIdentityLinks();
    String actualDueDate = actualUserTask.getDueDate();
    String actualExtensionId = actualUserTask.getExtensionId();
    String actualFormKey = actualUserTask.getFormKey();
    List<FormProperty> actualFormProperties = actualUserTask.getFormProperties();
    String actualOwner = actualUserTask.getOwner();
    String actualPriority = actualUserTask.getPriority();
    String actualSkipExpression = actualUserTask.getSkipExpression();
    List<ActivitiListener> actualTaskListeners = actualUserTask.getTaskListeners();

    // Assert that nothing has changed
    assertEquals("2020-03-01", actualDueDate);
    assertEquals("42", actualExtensionId);
    assertEquals("Assignee", actualAssignee);
    assertEquals("Business Calendar Name", actualBusinessCalendarName);
    assertEquals("Category", actualCategory);
    assertEquals("Form Key", actualFormKey);
    assertEquals("Owner", actualOwner);
    assertEquals("Priority", actualPriority);
    assertEquals("Skip Expression", actualSkipExpression);
    assertEquals(0, actualUserTask.getXmlColumnNumber());
    assertEquals(0, actualUserTask.getXmlRowNumber());
    assertFalse(actualUserTask.isForCompensation());
    assertFalse(actualUserTask.isAsynchronous());
    assertFalse(actualUserTask.isNotExclusive());
    assertTrue(actualUserTask.getBoundaryEvents().isEmpty());
    assertTrue(actualUserTask.getDataInputAssociations().isEmpty());
    assertTrue(actualUserTask.getDataOutputAssociations().isEmpty());
    assertTrue(actualUserTask.getMapExceptions().isEmpty());
    assertTrue(actualUserTask.getExecutionListeners().isEmpty());
    assertTrue(actualUserTask.getIncomingFlows().isEmpty());
    assertTrue(actualUserTask.getOutgoingFlows().isEmpty());
    assertTrue(actualCandidateGroups.isEmpty());
    assertTrue(actualCandidateUsers.isEmpty());
    assertTrue(actualCustomProperties.isEmpty());
    assertTrue(actualFormProperties.isEmpty());
    assertTrue(actualTaskListeners.isEmpty());
    assertTrue(actualUserTask.getAttributes().isEmpty());
    assertTrue(actualUserTask.getExtensionElements().isEmpty());
    assertTrue(actualCustomGroupIdentityLinks.isEmpty());
    assertTrue(actualCustomUserIdentityLinks.isEmpty());
    assertSame(candidateGroups, actualCandidateGroups);
    assertSame(candidateUsers, actualCandidateUsers);
    assertSame(customProperties, actualCustomProperties);
    assertSame(formProperties, actualFormProperties);
    assertSame(taskListeners, actualTaskListeners);
    assertSame(customGroupIdentityLinks, actualCustomGroupIdentityLinks);
    assertSame(customUserIdentityLinks, actualCustomUserIdentityLinks);
  }
}
