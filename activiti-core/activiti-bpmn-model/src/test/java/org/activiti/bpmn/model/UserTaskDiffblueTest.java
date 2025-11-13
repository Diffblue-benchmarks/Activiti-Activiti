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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.activiti.bpmn.model.alfresco.AlfrescoUserTask;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class UserTaskDiffblueTest {
  /**
   * Test {@link UserTask#isExtended()}.
   *
   * <ul>
   *   <li>Given {@link UserTask} (default constructor) ExtensionId is {@code 42}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#isExtended()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean UserTask.isExtended()"})
  public void testIsExtended_givenUserTaskExtensionIdIs42_thenReturnTrue() {
    // Arrange
    UserTask userTask = new UserTask();
    userTask.setExtensionId("42");

    // Act and Assert
    assertTrue(userTask.isExtended());
  }

  /**
   * Test {@link UserTask#isExtended()}.
   *
   * <ul>
   *   <li>Given {@link UserTask} (default constructor) ExtensionId is empty string.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#isExtended()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean UserTask.isExtended()"})
  public void testIsExtended_givenUserTaskExtensionIdIsEmptyString_thenReturnFalse() {
    // Arrange
    UserTask userTask = new UserTask();
    userTask.setExtensionId("");

    // Act and Assert
    assertFalse(userTask.isExtended());
  }

  /**
   * Test {@link UserTask#isExtended()}.
   *
   * <ul>
   *   <li>Given {@link UserTask} (default constructor).
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#isExtended()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean UserTask.isExtended()"})
  public void testIsExtended_givenUserTask_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new UserTask().isExtended());
  }

  /**
   * Test {@link UserTask#addCustomUserIdentityLink(String, String)}.
   *
   * <ul>
   *   <li>Given {@link UserTask} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link UserTask#addCustomUserIdentityLink(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTask.addCustomUserIdentityLink(String, String)"})
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
   *
   * <ul>
   *   <li>Given {@link UserTask} (default constructor) addCustomUserIdentityLink {@code 42} and
   *       {@code Type}.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#addCustomUserIdentityLink(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTask.addCustomUserIdentityLink(String, String)"})
  public void testAddCustomUserIdentityLink_givenUserTaskAddCustomUserIdentityLink42AndType() {
    // Arrange
    UserTask userTask = new UserTask();
    userTask.addCustomUserIdentityLink("42", "Type");

    // Act
    userTask.addCustomUserIdentityLink("42", "Type");

    // Assert that nothing has changed
    Map<String, Set<String>> customUserIdentityLinks = userTask.getCustomUserIdentityLinks();
    assertEquals(1, customUserIdentityLinks.size());
    Set<String> getResult = customUserIdentityLinks.get("Type");
    assertEquals(1, getResult.size());
    assertTrue(getResult.contains("42"));
  }

  /**
   * Test {@link UserTask#addCustomGroupIdentityLink(String, String)}.
   *
   * <ul>
   *   <li>Given {@link UserTask} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link UserTask#addCustomGroupIdentityLink(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTask.addCustomGroupIdentityLink(String, String)"})
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
   *
   * <ul>
   *   <li>Given {@link UserTask} (default constructor) addCustomGroupIdentityLink {@code 42} and
   *       {@code Type}.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#addCustomGroupIdentityLink(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTask.addCustomGroupIdentityLink(String, String)"})
  public void testAddCustomGroupIdentityLink_givenUserTaskAddCustomGroupIdentityLink42AndType() {
    // Arrange
    UserTask userTask = new UserTask();
    userTask.addCustomGroupIdentityLink("42", "Type");

    // Act
    userTask.addCustomGroupIdentityLink("42", "Type");

    // Assert that nothing has changed
    Map<String, Set<String>> customGroupIdentityLinks = userTask.getCustomGroupIdentityLinks();
    assertEquals(1, customGroupIdentityLinks.size());
    Set<String> getResult = customGroupIdentityLinks.get("Type");
    assertEquals(1, getResult.size());
    assertTrue(getResult.contains("42"));
  }

  /**
   * Test {@link UserTask#clone()}.
   *
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor) FieldExtensions is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"UserTask UserTask.clone()"})
  public void testClone_givenActivitiListenerFieldExtensionsIsNull() {
    // Arrange
    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(new FormValue());

    FormProperty formProperty = new FormProperty();
    formProperty.setFormValues(formValues);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(null);

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(activitiListener);

    UserTask userTask = new UserTask();
    userTask.setFormProperties(formProperties);
    userTask.setTaskListeners(taskListeners);

    // Act
    UserTask actualCloneResult = userTask.clone();

    // Assert
    List<FormProperty> formProperties2 = actualCloneResult.getFormProperties();
    assertEquals(1, formProperties2.size());
    List<FormValue> formValues2 = formProperties2.get(0).getFormValues();
    assertEquals(1, formValues2.size());
    FormValue getResult = formValues2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    List<ActivitiListener> taskListeners2 = actualCloneResult.getTaskListeners();
    assertEquals(1, taskListeners2.size());
    assertTrue(taskListeners2.get(0).getFieldExtensions().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link UserTask#clone()}.
   *
   * <ul>
   *   <li>Given {@link AlfrescoUserTask} (default constructor).
   *   <li>Then return {@link AlfrescoUserTask}.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"UserTask UserTask.clone()"})
  public void testClone_givenAlfrescoUserTask_thenReturnAlfrescoUserTask() {
    // Arrange and Act
    AlfrescoUserTask actualCloneResult = new AlfrescoUserTask().clone();

    // Assert
    assertTrue(actualCloneResult instanceof AlfrescoUserTask);
    assertNull(((AlfrescoUserTask) actualCloneResult).getRunAs());
    assertNull(((AlfrescoUserTask) actualCloneResult).getScriptProcessor());
    assertTrue(actualCloneResult.getFormProperties().isEmpty());
  }

  /**
   * Test {@link UserTask#clone()}.
   *
   * <ul>
   *   <li>Given {@link FormProperty} (default constructor) FormValues is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"UserTask UserTask.clone()"})
  public void testClone_givenFormPropertyFormValuesIsNull() {
    // Arrange
    FormProperty formProperty = new FormProperty();
    formProperty.setFormValues(null);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(activitiListener);

    UserTask userTask = new UserTask();
    userTask.setFormProperties(formProperties);
    userTask.setTaskListeners(taskListeners);

    // Act
    UserTask actualCloneResult = userTask.clone();

    // Assert
    List<ActivitiListener> taskListeners2 = actualCloneResult.getTaskListeners();
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
    List<FormProperty> formProperties2 = actualCloneResult.getFormProperties();
    assertEquals(1, formProperties2.size());
    assertTrue(formProperties2.get(0).getFormValues().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link UserTask#clone()}.
   *
   * <ul>
   *   <li>Given {@link UserTask} (default constructor) DataInputAssociations is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"UserTask UserTask.clone()"})
  public void testClone_givenUserTaskDataInputAssociationsIsNull() {
    // Arrange
    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    UserTask userTask = new UserTask();
    userTask.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    userTask.setIoSpecification(new IOSpecification());
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
   *
   * <ul>
   *   <li>Given {@link UserTask} (default constructor) DataOutputAssociations is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"UserTask UserTask.clone()"})
  public void testClone_givenUserTaskDataOutputAssociationsIsNull() {
    // Arrange
    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    UserTask userTask = new UserTask();
    userTask.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    userTask.setIoSpecification(new IOSpecification());
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
   *
   * <ul>
   *   <li>Given {@link UserTask} (default constructor) TaskListeners is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"UserTask UserTask.clone()"})
  public void testClone_givenUserTaskTaskListenersIsNull() {
    // Arrange
    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(new FormValue());

    FormProperty formProperty = new FormProperty();
    formProperty.setFormValues(formValues);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);

    UserTask userTask = new UserTask();
    userTask.setFormProperties(formProperties);
    userTask.setTaskListeners(null);

    // Act and Assert
    List<FormProperty> formProperties2 = userTask.clone().getFormProperties();
    assertEquals(1, formProperties2.size());
    List<FormValue> formValues2 = formProperties2.get(0).getFormValues();
    assertEquals(1, formValues2.size());
    FormValue getResult = formValues2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link UserTask#clone()}.
   *
   * <ul>
   *   <li>Given {@link UserTask} (default constructor).
   *   <li>Then return IoSpecification is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"UserTask UserTask.clone()"})
  public void testClone_givenUserTask_thenReturnIoSpecificationIsNull() {
    // Arrange and Act
    UserTask actualCloneResult = new UserTask().clone();

    // Assert
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getTaskListeners().isEmpty());
  }

  /**
   * Test {@link UserTask#clone()}.
   *
   * <ul>
   *   <li>Then return Attributes size is one.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"UserTask UserTask.clone()"})
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
   *
   * <ul>
   *   <li>Then return Attributes size is two.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"UserTask UserTask.clone()"})
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
   *
   * <ul>
   *   <li>Then return DataInputAssociations size is one.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"UserTask UserTask.clone()"})
  public void testClone_thenReturnDataInputAssociationsSizeIsOne() {
    // Arrange
    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(new DataAssociation());

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    UserTask userTask = new UserTask();
    userTask.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    userTask.setIoSpecification(new IOSpecification());
    userTask.setDataInputAssociations(dataInputAssociations);
    userTask.setDataOutputAssociations(dataOutputAssociations);
    userTask.setBoundaryEvents(boundaryEvents);

    // Act
    UserTask actualCloneResult = userTask.clone();

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
   * Test {@link UserTask#clone()}.
   *
   * <ul>
   *   <li>Then return FormProperties first FormValues Empty.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"UserTask UserTask.clone()"})
  public void testClone_thenReturnFormPropertiesFirstFormValuesEmpty() {
    // Arrange
    FormProperty formProperty = new FormProperty();
    formProperty.setFormValues(new ArrayList<>());

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(activitiListener);

    UserTask userTask = new UserTask();
    userTask.setFormProperties(formProperties);
    userTask.setTaskListeners(taskListeners);

    // Act
    UserTask actualCloneResult = userTask.clone();

    // Assert
    List<ActivitiListener> taskListeners2 = actualCloneResult.getTaskListeners();
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
    List<FormProperty> formProperties2 = actualCloneResult.getFormProperties();
    assertEquals(1, formProperties2.size());
    assertTrue(formProperties2.get(0).getFormValues().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link UserTask#clone()}.
   *
   * <ul>
   *   <li>Then return TaskListeners first FieldExtensions Empty.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"UserTask UserTask.clone()"})
  public void testClone_thenReturnTaskListenersFirstFieldExtensionsEmpty() {
    // Arrange
    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(new FormValue());

    FormProperty formProperty = new FormProperty();
    formProperty.setFormValues(formValues);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(new ArrayList<>());

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(activitiListener);

    UserTask userTask = new UserTask();
    userTask.setFormProperties(formProperties);
    userTask.setTaskListeners(taskListeners);

    // Act
    UserTask actualCloneResult = userTask.clone();

    // Assert
    List<FormProperty> formProperties2 = actualCloneResult.getFormProperties();
    assertEquals(1, formProperties2.size());
    List<FormValue> formValues2 = formProperties2.get(0).getFormValues();
    assertEquals(1, formValues2.size());
    FormValue getResult = formValues2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    List<ActivitiListener> taskListeners2 = actualCloneResult.getTaskListeners();
    assertEquals(1, taskListeners2.size());
    assertTrue(taskListeners2.get(0).getFieldExtensions().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link UserTask#clone()}.
   *
   * <ul>
   *   <li>Then return TaskListeners first FieldExtensions size is one.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"UserTask UserTask.clone()"})
  public void testClone_thenReturnTaskListenersFirstFieldExtensionsSizeIsOne() {
    // Arrange
    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(new FormValue());

    FormProperty formProperty = new FormProperty();
    formProperty.setFormValues(formValues);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(activitiListener);

    UserTask userTask = new UserTask();
    userTask.setFormProperties(formProperties);
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
   * Test {@link UserTask#setValues(UserTask)} with {@code UserTask}.
   *
   * <ul>
   *   <li>Given {@link FormProperty} (default constructor) FormValues is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#setValues(UserTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTask.setValues(UserTask)"})
  public void testSetValuesWithUserTask_givenFormPropertyFormValuesIsNull() {
    // Arrange
    UserTask userTask = new UserTask();

    ArrayList<BoundaryEvent> boundaryEventList = new ArrayList<>();
    boundaryEventList.add(new BoundaryEvent());

    ArrayList<DataAssociation> dataAssociationList = new ArrayList<>();
    dataAssociationList.add(new DataAssociation());

    ArrayList<DataAssociation> dataAssociationList2 = new ArrayList<>();
    dataAssociationList2.add(new DataAssociation());

    FormProperty formProperty = new FormProperty();
    formProperty.setFormValues(null);

    ArrayList<FormProperty> formPropertyList = new ArrayList<>();
    formPropertyList.add(formProperty);

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(null);

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(activitiListener);

    AlfrescoUserTask otherElement = mock(AlfrescoUserTask.class);
    when(otherElement.getBoundaryEvents()).thenReturn(boundaryEventList);
    when(otherElement.getDataInputAssociations()).thenReturn(dataAssociationList);
    when(otherElement.getDataOutputAssociations()).thenReturn(dataAssociationList2);
    when(otherElement.getFormProperties()).thenReturn(formPropertyList);
    when(otherElement.getTaskListeners()).thenReturn(activitiListenerList);
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
    when(otherElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherElement.getCandidateGroups()).thenReturn(new ArrayList<>());
    when(otherElement.getCandidateUsers()).thenReturn(new ArrayList<>());
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
    assertNull(userTask.getCustomGroupIdentityLinks());
    assertNull(userTask.getCustomUserIdentityLinks());
    assertEquals(1, userTask.getBoundaryEvents().size());
    assertEquals(1, userTask.getDataInputAssociations().size());
    assertFalse(userTask.isExclusive());
    assertTrue(userTask.hasMultiInstanceLoopCharacteristics());
    assertTrue(userTask.isForCompensation());
    assertTrue(userTask.isAsynchronous());
    assertTrue(userTask.isNotExclusive());
    assertTrue(userTask.isExtended());
  }

  /**
   * Test {@link UserTask#setValues(UserTask)} with {@code UserTask}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code 42} is {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#setValues(UserTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTask.setValues(UserTask)"})
  public void testSetValuesWithUserTask_givenHashMap42IsArrayList() {
    // Arrange
    UserTask userTask = new UserTask();

    ArrayList<BoundaryEvent> boundaryEventList = new ArrayList<>();
    boundaryEventList.add(new BoundaryEvent());

    DataAssociation dataAssociation = mock(DataAssociation.class);
    when(dataAssociation.clone()).thenReturn(new DataAssociation());

    ArrayList<DataAssociation> dataAssociationList = new ArrayList<>();
    dataAssociationList.add(dataAssociation);

    DataAssociation dataAssociation2 = mock(DataAssociation.class);
    DataAssociation dataAssociation3 = new DataAssociation();
    when(dataAssociation2.clone()).thenReturn(dataAssociation3);

    ArrayList<DataAssociation> dataAssociationList2 = new ArrayList<>();
    dataAssociationList2.add(dataAssociation2);

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(new FormValue());

    FormProperty formProperty = mock(FormProperty.class);
    FormProperty formProperty2 = new FormProperty();
    when(formProperty.clone()).thenReturn(formProperty2);
    doNothing().when(formProperty).setFormValues(Mockito.<List<FormValue>>any());
    formProperty.setFormValues(formValues);

    ArrayList<FormProperty> formPropertyList = new ArrayList<>();
    formPropertyList.add(formProperty);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = mock(ActivitiListener.class);
    ActivitiListener activitiListener2 = new ActivitiListener();
    when(activitiListener.clone()).thenReturn(activitiListener2);
    doNothing().when(activitiListener).setFieldExtensions(Mockito.<List<FieldExtension>>any());
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(activitiListener);

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("42", new ArrayList<>());
    stringListMap.put("Key", new ArrayList<>());

    AlfrescoUserTask otherElement = mock(AlfrescoUserTask.class);
    when(otherElement.getBoundaryEvents()).thenReturn(boundaryEventList);
    when(otherElement.getDataInputAssociations()).thenReturn(dataAssociationList);
    when(otherElement.getDataOutputAssociations()).thenReturn(dataAssociationList2);
    when(otherElement.getFormProperties()).thenReturn(formPropertyList);
    when(otherElement.getTaskListeners()).thenReturn(activitiListenerList);
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
    when(otherElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherElement.getCandidateGroups()).thenReturn(new ArrayList<>());
    when(otherElement.getCandidateUsers()).thenReturn(new ArrayList<>());
    when(otherElement.getAttributes()).thenReturn(stringListMap);
    when(otherElement.getExtensionElements()).thenReturn(new HashMap<>());
    when(otherElement.getIoSpecification()).thenReturn(new IOSpecification());
    when(otherElement.getLoopCharacteristics()).thenReturn(new MultiInstanceLoopCharacteristics());

    // Act
    userTask.setValues(otherElement);

    // Assert
    verify(activitiListener).clone();
    verify(activitiListener).setFieldExtensions(isA(List.class));
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
    verify(dataAssociation).clone();
    verify(dataAssociation2).clone();
    verify(otherElement).getDocumentation();
    verify(otherElement, atLeast(1)).getExecutionListeners();
    verify(otherElement).getName();
    verify(otherElement).isAsynchronous();
    verify(otherElement).isNotExclusive();
    verify(formProperty).clone();
    verify(formProperty).setFormValues(isA(List.class));
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
    List<DataAssociation> dataOutputAssociations = userTask.getDataOutputAssociations();
    assertEquals(1, dataOutputAssociations.size());
    List<FormProperty> formProperties = userTask.getFormProperties();
    assertEquals(1, formProperties.size());
    List<ActivitiListener> taskListeners = userTask.getTaskListeners();
    assertEquals(1, taskListeners.size());
    ActivitiListener getResult = taskListeners.get(0);
    assertTrue(getResult.getFieldExtensions().isEmpty());
    assertSame(activitiListener2, getResult);
    assertSame(dataAssociation3, dataOutputAssociations.get(0));
    assertSame(formProperty2, formProperties.get(0));
  }

  /**
   * Test {@link UserTask#setValues(UserTask)} with {@code UserTask}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code 42} is {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#setValues(UserTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTask.setValues(UserTask)"})
  public void testSetValuesWithUserTask_givenHashMap42IsArrayList2() {
    // Arrange
    UserTask userTask = new UserTask();

    ArrayList<BoundaryEvent> boundaryEventList = new ArrayList<>();
    boundaryEventList.add(new BoundaryEvent());

    DataAssociation dataAssociation = mock(DataAssociation.class);
    when(dataAssociation.clone()).thenReturn(new DataAssociation());

    ArrayList<DataAssociation> dataAssociationList = new ArrayList<>();
    dataAssociationList.add(dataAssociation);

    DataAssociation dataAssociation2 = mock(DataAssociation.class);
    DataAssociation dataAssociation3 = new DataAssociation();
    when(dataAssociation2.clone()).thenReturn(dataAssociation3);

    ArrayList<DataAssociation> dataAssociationList2 = new ArrayList<>();
    dataAssociationList2.add(dataAssociation2);

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(new FormValue());

    FormProperty formProperty = mock(FormProperty.class);
    FormProperty formProperty2 = new FormProperty();
    when(formProperty.clone()).thenReturn(formProperty2);
    doNothing().when(formProperty).setFormValues(Mockito.<List<FormValue>>any());
    formProperty.setFormValues(formValues);

    ArrayList<FormProperty> formPropertyList = new ArrayList<>();
    formPropertyList.add(formProperty);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = mock(ActivitiListener.class);
    ActivitiListener activitiListener2 = new ActivitiListener();
    when(activitiListener.clone()).thenReturn(activitiListener2);
    doNothing().when(activitiListener).setFieldExtensions(Mockito.<List<FieldExtension>>any());
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(activitiListener);

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("42", new ArrayList<>());
    stringListMap.put("Key", new ArrayList<>());

    AlfrescoUserTask otherElement = mock(AlfrescoUserTask.class);
    when(otherElement.getBoundaryEvents()).thenReturn(boundaryEventList);
    when(otherElement.getDataInputAssociations()).thenReturn(dataAssociationList);
    when(otherElement.getDataOutputAssociations()).thenReturn(dataAssociationList2);
    when(otherElement.getFormProperties()).thenReturn(formPropertyList);
    when(otherElement.getTaskListeners()).thenReturn(activitiListenerList);
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
    when(otherElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherElement.getCandidateGroups()).thenReturn(new ArrayList<>());
    when(otherElement.getCandidateUsers()).thenReturn(new ArrayList<>());
    when(otherElement.getAttributes()).thenReturn(new HashMap<>());
    when(otherElement.getExtensionElements()).thenReturn(stringListMap);
    when(otherElement.getIoSpecification()).thenReturn(new IOSpecification());
    when(otherElement.getLoopCharacteristics()).thenReturn(new MultiInstanceLoopCharacteristics());

    // Act
    userTask.setValues(otherElement);

    // Assert
    verify(activitiListener).clone();
    verify(activitiListener).setFieldExtensions(isA(List.class));
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
    verify(dataAssociation).clone();
    verify(dataAssociation2).clone();
    verify(otherElement).getDocumentation();
    verify(otherElement, atLeast(1)).getExecutionListeners();
    verify(otherElement).getName();
    verify(otherElement).isAsynchronous();
    verify(otherElement).isNotExclusive();
    verify(formProperty).clone();
    verify(formProperty).setFormValues(isA(List.class));
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
    List<DataAssociation> dataOutputAssociations = userTask.getDataOutputAssociations();
    assertEquals(1, dataOutputAssociations.size());
    List<FormProperty> formProperties = userTask.getFormProperties();
    assertEquals(1, formProperties.size());
    List<ActivitiListener> taskListeners = userTask.getTaskListeners();
    assertEquals(1, taskListeners.size());
    ActivitiListener getResult = taskListeners.get(0);
    assertTrue(getResult.getFieldExtensions().isEmpty());
    assertSame(activitiListener2, getResult);
    assertSame(dataAssociation3, dataOutputAssociations.get(0));
    assertSame(formProperty2, formProperties.get(0));
  }

  /**
   * Test {@link UserTask#setValues(UserTask)} with {@code UserTask}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code Key} is {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#setValues(UserTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTask.setValues(UserTask)"})
  public void testSetValuesWithUserTask_givenHashMapKeyIsArrayList() {
    // Arrange
    UserTask userTask = new UserTask();

    ArrayList<BoundaryEvent> boundaryEventList = new ArrayList<>();
    boundaryEventList.add(new BoundaryEvent());

    DataAssociation dataAssociation = mock(DataAssociation.class);
    when(dataAssociation.clone()).thenReturn(new DataAssociation());

    ArrayList<DataAssociation> dataAssociationList = new ArrayList<>();
    dataAssociationList.add(dataAssociation);

    DataAssociation dataAssociation2 = mock(DataAssociation.class);
    DataAssociation dataAssociation3 = new DataAssociation();
    when(dataAssociation2.clone()).thenReturn(dataAssociation3);

    ArrayList<DataAssociation> dataAssociationList2 = new ArrayList<>();
    dataAssociationList2.add(dataAssociation2);

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(new FormValue());

    FormProperty formProperty = mock(FormProperty.class);
    FormProperty formProperty2 = new FormProperty();
    when(formProperty.clone()).thenReturn(formProperty2);
    doNothing().when(formProperty).setFormValues(Mockito.<List<FormValue>>any());
    formProperty.setFormValues(formValues);

    ArrayList<FormProperty> formPropertyList = new ArrayList<>();
    formPropertyList.add(formProperty);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = mock(ActivitiListener.class);
    ActivitiListener activitiListener2 = new ActivitiListener();
    when(activitiListener.clone()).thenReturn(activitiListener2);
    doNothing().when(activitiListener).setFieldExtensions(Mockito.<List<FieldExtension>>any());
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(activitiListener);

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("Key", new ArrayList<>());

    AlfrescoUserTask otherElement = mock(AlfrescoUserTask.class);
    when(otherElement.getBoundaryEvents()).thenReturn(boundaryEventList);
    when(otherElement.getDataInputAssociations()).thenReturn(dataAssociationList);
    when(otherElement.getDataOutputAssociations()).thenReturn(dataAssociationList2);
    when(otherElement.getFormProperties()).thenReturn(formPropertyList);
    when(otherElement.getTaskListeners()).thenReturn(activitiListenerList);
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
    when(otherElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherElement.getCandidateGroups()).thenReturn(new ArrayList<>());
    when(otherElement.getCandidateUsers()).thenReturn(new ArrayList<>());
    when(otherElement.getAttributes()).thenReturn(stringListMap);
    when(otherElement.getExtensionElements()).thenReturn(new HashMap<>());
    when(otherElement.getIoSpecification()).thenReturn(new IOSpecification());
    when(otherElement.getLoopCharacteristics()).thenReturn(new MultiInstanceLoopCharacteristics());

    // Act
    userTask.setValues(otherElement);

    // Assert
    verify(activitiListener).clone();
    verify(activitiListener).setFieldExtensions(isA(List.class));
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
    verify(dataAssociation).clone();
    verify(dataAssociation2).clone();
    verify(otherElement).getDocumentation();
    verify(otherElement, atLeast(1)).getExecutionListeners();
    verify(otherElement).getName();
    verify(otherElement).isAsynchronous();
    verify(otherElement).isNotExclusive();
    verify(formProperty).clone();
    verify(formProperty).setFormValues(isA(List.class));
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
    List<DataAssociation> dataOutputAssociations = userTask.getDataOutputAssociations();
    assertEquals(1, dataOutputAssociations.size());
    List<FormProperty> formProperties = userTask.getFormProperties();
    assertEquals(1, formProperties.size());
    List<ActivitiListener> taskListeners = userTask.getTaskListeners();
    assertEquals(1, taskListeners.size());
    ActivitiListener getResult = taskListeners.get(0);
    assertTrue(getResult.getFieldExtensions().isEmpty());
    assertSame(activitiListener2, getResult);
    assertSame(dataAssociation3, dataOutputAssociations.get(0));
    assertSame(formProperty2, formProperties.get(0));
  }

  /**
   * Test {@link UserTask#setValues(UserTask)} with {@code UserTask}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code Key} is {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#setValues(UserTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTask.setValues(UserTask)"})
  public void testSetValuesWithUserTask_givenHashMapKeyIsArrayList2() {
    // Arrange
    UserTask userTask = new UserTask();

    ArrayList<BoundaryEvent> boundaryEventList = new ArrayList<>();
    boundaryEventList.add(new BoundaryEvent());

    DataAssociation dataAssociation = mock(DataAssociation.class);
    when(dataAssociation.clone()).thenReturn(new DataAssociation());

    ArrayList<DataAssociation> dataAssociationList = new ArrayList<>();
    dataAssociationList.add(dataAssociation);

    DataAssociation dataAssociation2 = mock(DataAssociation.class);
    DataAssociation dataAssociation3 = new DataAssociation();
    when(dataAssociation2.clone()).thenReturn(dataAssociation3);

    ArrayList<DataAssociation> dataAssociationList2 = new ArrayList<>();
    dataAssociationList2.add(dataAssociation2);

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(new FormValue());

    FormProperty formProperty = mock(FormProperty.class);
    FormProperty formProperty2 = new FormProperty();
    when(formProperty.clone()).thenReturn(formProperty2);
    doNothing().when(formProperty).setFormValues(Mockito.<List<FormValue>>any());
    formProperty.setFormValues(formValues);

    ArrayList<FormProperty> formPropertyList = new ArrayList<>();
    formPropertyList.add(formProperty);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = mock(ActivitiListener.class);
    ActivitiListener activitiListener2 = new ActivitiListener();
    when(activitiListener.clone()).thenReturn(activitiListener2);
    doNothing().when(activitiListener).setFieldExtensions(Mockito.<List<FieldExtension>>any());
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(activitiListener);

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("Key", new ArrayList<>());

    AlfrescoUserTask otherElement = mock(AlfrescoUserTask.class);
    when(otherElement.getBoundaryEvents()).thenReturn(boundaryEventList);
    when(otherElement.getDataInputAssociations()).thenReturn(dataAssociationList);
    when(otherElement.getDataOutputAssociations()).thenReturn(dataAssociationList2);
    when(otherElement.getFormProperties()).thenReturn(formPropertyList);
    when(otherElement.getTaskListeners()).thenReturn(activitiListenerList);
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
    when(otherElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherElement.getCandidateGroups()).thenReturn(new ArrayList<>());
    when(otherElement.getCandidateUsers()).thenReturn(new ArrayList<>());
    when(otherElement.getAttributes()).thenReturn(new HashMap<>());
    when(otherElement.getExtensionElements()).thenReturn(stringListMap);
    when(otherElement.getIoSpecification()).thenReturn(new IOSpecification());
    when(otherElement.getLoopCharacteristics()).thenReturn(new MultiInstanceLoopCharacteristics());

    // Act
    userTask.setValues(otherElement);

    // Assert
    verify(activitiListener).clone();
    verify(activitiListener).setFieldExtensions(isA(List.class));
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
    verify(dataAssociation).clone();
    verify(dataAssociation2).clone();
    verify(otherElement).getDocumentation();
    verify(otherElement, atLeast(1)).getExecutionListeners();
    verify(otherElement).getName();
    verify(otherElement).isAsynchronous();
    verify(otherElement).isNotExclusive();
    verify(formProperty).clone();
    verify(formProperty).setFormValues(isA(List.class));
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
    List<DataAssociation> dataOutputAssociations = userTask.getDataOutputAssociations();
    assertEquals(1, dataOutputAssociations.size());
    List<FormProperty> formProperties = userTask.getFormProperties();
    assertEquals(1, formProperties.size());
    List<ActivitiListener> taskListeners = userTask.getTaskListeners();
    assertEquals(1, taskListeners.size());
    ActivitiListener getResult = taskListeners.get(0);
    assertTrue(getResult.getFieldExtensions().isEmpty());
    assertSame(activitiListener2, getResult);
    assertSame(dataAssociation3, dataOutputAssociations.get(0));
    assertSame(formProperty2, formProperties.get(0));
  }

  /**
   * Test {@link UserTask#setValues(UserTask)} with {@code UserTask}.
   *
   * <ul>
   *   <li>Then calls {@link IOSpecification#clone()}.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#setValues(UserTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTask.setValues(UserTask)"})
  public void testSetValuesWithUserTask_thenCallsClone() {
    // Arrange
    UserTask userTask = new UserTask();

    ArrayList<BoundaryEvent> boundaryEventList = new ArrayList<>();
    boundaryEventList.add(new BoundaryEvent());

    DataAssociation dataAssociation = mock(DataAssociation.class);
    when(dataAssociation.clone()).thenReturn(new DataAssociation());

    ArrayList<DataAssociation> dataAssociationList = new ArrayList<>();
    dataAssociationList.add(dataAssociation);

    DataAssociation dataAssociation2 = mock(DataAssociation.class);
    DataAssociation dataAssociation3 = new DataAssociation();
    when(dataAssociation2.clone()).thenReturn(dataAssociation3);

    ArrayList<DataAssociation> dataAssociationList2 = new ArrayList<>();
    dataAssociationList2.add(dataAssociation2);

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(new FormValue());

    FormProperty formProperty = mock(FormProperty.class);
    when(formProperty.clone()).thenReturn(new FormProperty());
    doNothing().when(formProperty).setFormValues(Mockito.<List<FormValue>>any());
    formProperty.setFormValues(formValues);

    ArrayList<FormProperty> formPropertyList = new ArrayList<>();
    formPropertyList.add(formProperty);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.clone()).thenReturn(new ActivitiListener());
    doNothing().when(activitiListener).setFieldExtensions(Mockito.<List<FieldExtension>>any());
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(activitiListener);

    IOSpecification ioSpecification = mock(IOSpecification.class);
    IOSpecification ioSpecification2 = new IOSpecification();
    when(ioSpecification.clone()).thenReturn(ioSpecification2);

    AlfrescoUserTask otherElement = mock(AlfrescoUserTask.class);
    when(otherElement.getBoundaryEvents()).thenReturn(boundaryEventList);
    when(otherElement.getDataInputAssociations()).thenReturn(dataAssociationList);
    when(otherElement.getDataOutputAssociations()).thenReturn(dataAssociationList2);
    when(otherElement.getFormProperties()).thenReturn(formPropertyList);
    when(otherElement.getTaskListeners()).thenReturn(activitiListenerList);
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
    when(otherElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherElement.getCandidateGroups()).thenReturn(new ArrayList<>());
    when(otherElement.getCandidateUsers()).thenReturn(new ArrayList<>());
    when(otherElement.getAttributes()).thenReturn(new HashMap<>());
    when(otherElement.getExtensionElements()).thenReturn(new HashMap<>());
    when(otherElement.getIoSpecification()).thenReturn(ioSpecification);
    when(otherElement.getLoopCharacteristics()).thenReturn(new MultiInstanceLoopCharacteristics());

    // Act
    userTask.setValues(otherElement);

    // Assert
    verify(activitiListener).clone();
    verify(activitiListener).setFieldExtensions(isA(List.class));
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
    verify(dataAssociation).clone();
    verify(dataAssociation2).clone();
    verify(otherElement).getDocumentation();
    verify(otherElement, atLeast(1)).getExecutionListeners();
    verify(otherElement).getName();
    verify(otherElement).isAsynchronous();
    verify(otherElement).isNotExclusive();
    verify(formProperty).clone();
    verify(formProperty).setFormValues(isA(List.class));
    verify(ioSpecification).clone();
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
    List<DataAssociation> dataOutputAssociations = userTask.getDataOutputAssociations();
    assertEquals(1, dataOutputAssociations.size());
    List<ActivitiListener> taskListeners = userTask.getTaskListeners();
    assertEquals(1, taskListeners.size());
    assertTrue(taskListeners.get(0).getFieldExtensions().isEmpty());
    assertSame(dataAssociation3, dataOutputAssociations.get(0));
    assertSame(ioSpecification2, userTask.getIoSpecification());
  }

  /**
   * Test {@link UserTask#setValues(UserTask)} with {@code UserTask}.
   *
   * <ul>
   *   <li>Then calls {@link MultiInstanceLoopCharacteristics#clone()}.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#setValues(UserTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTask.setValues(UserTask)"})
  public void testSetValuesWithUserTask_thenCallsClone2() {
    // Arrange
    UserTask userTask = new UserTask();

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

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(new FormValue());

    FormProperty formProperty = mock(FormProperty.class);
    FormProperty formProperty2 = new FormProperty();
    when(formProperty.clone()).thenReturn(formProperty2);
    doNothing().when(formProperty).setFormValues(Mockito.<List<FormValue>>any());
    formProperty.setFormValues(formValues);

    ArrayList<FormProperty> formPropertyList = new ArrayList<>();
    formPropertyList.add(formProperty);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = mock(ActivitiListener.class);
    ActivitiListener activitiListener2 = new ActivitiListener();
    when(activitiListener.clone()).thenReturn(activitiListener2);
    doNothing().when(activitiListener).setFieldExtensions(Mockito.<List<FieldExtension>>any());
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(activitiListener);

    IOSpecification ioSpecification = mock(IOSpecification.class);
    IOSpecification ioSpecification2 = new IOSpecification();
    when(ioSpecification.clone()).thenReturn(ioSpecification2);

    MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics =
        mock(MultiInstanceLoopCharacteristics.class);
    MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics2 =
        new MultiInstanceLoopCharacteristics();
    when(multiInstanceLoopCharacteristics.clone()).thenReturn(multiInstanceLoopCharacteristics2);

    AlfrescoUserTask otherElement = mock(AlfrescoUserTask.class);
    when(otherElement.getBoundaryEvents()).thenReturn(boundaryEventList);
    when(otherElement.getDataInputAssociations()).thenReturn(dataAssociationList);
    when(otherElement.getDataOutputAssociations()).thenReturn(dataAssociationList2);
    when(otherElement.getFormProperties()).thenReturn(formPropertyList);
    when(otherElement.getTaskListeners()).thenReturn(activitiListenerList);
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
    when(otherElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherElement.getCandidateGroups()).thenReturn(new ArrayList<>());
    when(otherElement.getCandidateUsers()).thenReturn(new ArrayList<>());
    when(otherElement.getAttributes()).thenReturn(new HashMap<>());
    when(otherElement.getExtensionElements()).thenReturn(new HashMap<>());
    when(otherElement.getIoSpecification()).thenReturn(ioSpecification);
    when(otherElement.getLoopCharacteristics()).thenReturn(multiInstanceLoopCharacteristics);

    // Act
    userTask.setValues(otherElement);

    // Assert
    verify(activitiListener).clone();
    verify(activitiListener).setFieldExtensions(isA(List.class));
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
    verify(dataAssociation).clone();
    verify(dataAssociation2).clone();
    verify(otherElement).getDocumentation();
    verify(otherElement, atLeast(1)).getExecutionListeners();
    verify(otherElement).getName();
    verify(otherElement).isAsynchronous();
    verify(otherElement).isNotExclusive();
    verify(formProperty).clone();
    verify(formProperty).setFormValues(isA(List.class));
    verify(ioSpecification).clone();
    verify(multiInstanceLoopCharacteristics).clone();
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
    List<FormProperty> formProperties = userTask.getFormProperties();
    assertEquals(1, formProperties.size());
    List<ActivitiListener> taskListeners = userTask.getTaskListeners();
    assertEquals(1, taskListeners.size());
    assertSame(activitiListener2, taskListeners.get(0));
    assertSame(formProperty2, formProperties.get(0));
    assertSame(ioSpecification2, userTask.getIoSpecification());
    assertSame(multiInstanceLoopCharacteristics2, userTask.getLoopCharacteristics());
  }

  /**
   * Test {@link UserTask#setValues(UserTask)} with {@code UserTask}.
   *
   * <ul>
   *   <li>Then not {@link UserTask} (default constructor) hasMultiInstanceLoopCharacteristics.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#setValues(UserTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTask.setValues(UserTask)"})
  public void testSetValuesWithUserTask_thenNotUserTaskHasMultiInstanceLoopCharacteristics() {
    // Arrange
    UserTask userTask = new UserTask();
    UserTask otherElement = new UserTask();

    // Act
    userTask.setValues(otherElement);

    // Assert that nothing has changed
    assertFalse(otherElement.hasMultiInstanceLoopCharacteristics());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.getBoundaryEvents().isEmpty());
    assertTrue(otherElement.getDataInputAssociations().isEmpty());
    assertTrue(otherElement.getDataOutputAssociations().isEmpty());
    assertTrue(otherElement.getFormProperties().isEmpty());
    assertTrue(otherElement.getTaskListeners().isEmpty());
    assertTrue(otherElement.getCustomGroupIdentityLinks().isEmpty());
    assertTrue(otherElement.getCustomUserIdentityLinks().isEmpty());
    assertTrue(otherElement.isExclusive());
  }

  /**
   * Test {@link UserTask#setValues(UserTask)} with {@code UserTask}.
   *
   * <ul>
   *   <li>Then {@link UserTask} (default constructor) DataInputAssociations is {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#setValues(UserTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTask.setValues(UserTask)"})
  public void testSetValuesWithUserTask_thenUserTaskDataInputAssociationsIsArrayList() {
    // Arrange
    UserTask userTask = new UserTask();

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(new DataAssociation());

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    UserTask otherElement = new UserTask();
    MultiInstanceLoopCharacteristics loopCharacteristics = new MultiInstanceLoopCharacteristics();
    otherElement.setLoopCharacteristics(loopCharacteristics);
    IOSpecification ioSpecification = new IOSpecification();
    otherElement.setIoSpecification(ioSpecification);
    otherElement.setDataInputAssociations(dataInputAssociations);
    otherElement.setDataOutputAssociations(dataOutputAssociations);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    userTask.setValues(otherElement);

    // Assert that nothing has changed
    List<BoundaryEvent> boundaryEvents2 = otherElement.getBoundaryEvents();
    assertEquals(1, boundaryEvents2.size());
    List<DataAssociation> dataInputAssociations2 = otherElement.getDataInputAssociations();
    assertEquals(1, dataInputAssociations2.size());
    List<DataAssociation> dataOutputAssociations2 = otherElement.getDataOutputAssociations();
    assertEquals(1, dataOutputAssociations2.size());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.getFormProperties().isEmpty());
    assertTrue(otherElement.getTaskListeners().isEmpty());
    assertTrue(otherElement.getCustomGroupIdentityLinks().isEmpty());
    assertTrue(otherElement.getCustomUserIdentityLinks().isEmpty());
    assertTrue(otherElement.hasMultiInstanceLoopCharacteristics());
    assertTrue(otherElement.isExclusive());
    assertSame(boundaryEvents, boundaryEvents2);
    assertSame(dataInputAssociations, dataInputAssociations2);
    assertSame(dataOutputAssociations, dataOutputAssociations2);
    assertSame(ioSpecification, otherElement.getIoSpecification());
    assertSame(loopCharacteristics, otherElement.getLoopCharacteristics());
  }

  /**
   * Test {@link UserTask#setValues(UserTask)} with {@code UserTask}.
   *
   * <ul>
   *   <li>Then {@link UserTask} (default constructor) DueDate is {@code 2020-03-01}.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#setValues(UserTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTask.setValues(UserTask)"})
  public void testSetValuesWithUserTask_thenUserTaskDueDateIs20200301() {
    // Arrange
    UserTask userTask = new UserTask();

    ArrayList<BoundaryEvent> boundaryEventList = new ArrayList<>();
    boundaryEventList.add(new BoundaryEvent());

    ArrayList<DataAssociation> dataAssociationList = new ArrayList<>();
    dataAssociationList.add(new DataAssociation());

    ArrayList<DataAssociation> dataAssociationList2 = new ArrayList<>();
    dataAssociationList2.add(new DataAssociation());

    FormProperty formProperty = new FormProperty();
    formProperty.setFormValues(new ArrayList<>());

    ArrayList<FormProperty> formPropertyList = new ArrayList<>();
    formPropertyList.add(formProperty);

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(new ArrayList<>());

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(activitiListener);

    AlfrescoUserTask otherElement = mock(AlfrescoUserTask.class);
    when(otherElement.getBoundaryEvents()).thenReturn(boundaryEventList);
    when(otherElement.getDataInputAssociations()).thenReturn(dataAssociationList);
    when(otherElement.getDataOutputAssociations()).thenReturn(dataAssociationList2);
    when(otherElement.getFormProperties()).thenReturn(formPropertyList);
    when(otherElement.getTaskListeners()).thenReturn(activitiListenerList);
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
    when(otherElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherElement.getCandidateGroups()).thenReturn(new ArrayList<>());
    when(otherElement.getCandidateUsers()).thenReturn(new ArrayList<>());
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
    assertNull(userTask.getCustomGroupIdentityLinks());
    assertNull(userTask.getCustomUserIdentityLinks());
    assertEquals(1, userTask.getBoundaryEvents().size());
    assertEquals(1, userTask.getDataInputAssociations().size());
    assertFalse(userTask.isExclusive());
    assertTrue(userTask.hasMultiInstanceLoopCharacteristics());
    assertTrue(userTask.isForCompensation());
    assertTrue(userTask.isAsynchronous());
    assertTrue(userTask.isNotExclusive());
    assertTrue(userTask.isExtended());
  }

  /**
   * Test {@link UserTask#setValues(UserTask)} with {@code UserTask}.
   *
   * <ul>
   *   <li>Then {@link UserTask} (default constructor) ExecutionListeners size is one.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#setValues(UserTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTask.setValues(UserTask)"})
  public void testSetValuesWithUserTask_thenUserTaskExecutionListenersSizeIsOne() {
    // Arrange
    UserTask userTask = new UserTask();

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

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(new FormValue());

    FormProperty formProperty = mock(FormProperty.class);
    when(formProperty.clone()).thenReturn(new FormProperty());
    doNothing().when(formProperty).setFormValues(Mockito.<List<FormValue>>any());
    formProperty.setFormValues(formValues);

    ArrayList<FormProperty> formPropertyList = new ArrayList<>();
    formPropertyList.add(formProperty);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.clone()).thenReturn(new ActivitiListener());
    doNothing().when(activitiListener).setFieldExtensions(Mockito.<List<FieldExtension>>any());
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> activitiListenerList2 = new ArrayList<>();
    activitiListenerList2.add(activitiListener);

    AlfrescoUserTask otherElement = mock(AlfrescoUserTask.class);
    when(otherElement.getBoundaryEvents()).thenReturn(boundaryEventList);
    when(otherElement.getDataInputAssociations()).thenReturn(dataAssociationList);
    when(otherElement.getDataOutputAssociations()).thenReturn(dataAssociationList2);
    when(otherElement.getFormProperties()).thenReturn(formPropertyList);
    when(otherElement.getTaskListeners()).thenReturn(activitiListenerList2);
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
    when(otherElement.getExecutionListeners()).thenReturn(activitiListenerList);
    when(otherElement.getCandidateGroups()).thenReturn(new ArrayList<>());
    when(otherElement.getCandidateUsers()).thenReturn(new ArrayList<>());
    when(otherElement.getAttributes()).thenReturn(new HashMap<>());
    when(otherElement.getExtensionElements()).thenReturn(new HashMap<>());
    when(otherElement.getIoSpecification()).thenReturn(new IOSpecification());
    when(otherElement.getLoopCharacteristics()).thenReturn(new MultiInstanceLoopCharacteristics());

    // Act
    userTask.setValues(otherElement);

    // Assert
    verify(activitiListener).clone();
    verify(activitiListener).setFieldExtensions(isA(List.class));
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
    verify(dataAssociation).clone();
    verify(dataAssociation2).clone();
    verify(otherElement).getDocumentation();
    verify(otherElement, atLeast(1)).getExecutionListeners();
    verify(otherElement).getName();
    verify(otherElement).isAsynchronous();
    verify(otherElement).isNotExclusive();
    verify(formProperty).clone();
    verify(formProperty).setFormValues(isA(List.class));
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
    List<ActivitiListener> executionListeners = userTask.getExecutionListeners();
    assertEquals(1, executionListeners.size());
    ActivitiListener getResult = executionListeners.get(0);
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
   *
   * <ul>
   *   <li>Then {@link UserTask} (default constructor) FormProperties first FormValues size is one.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#setValues(UserTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTask.setValues(UserTask)"})
  public void testSetValuesWithUserTask_thenUserTaskFormPropertiesFirstFormValuesSizeIsOne() {
    // Arrange
    UserTask userTask = new UserTask();

    ArrayList<BoundaryEvent> boundaryEventList = new ArrayList<>();
    boundaryEventList.add(new BoundaryEvent());

    ArrayList<DataAssociation> dataAssociationList = new ArrayList<>();
    dataAssociationList.add(new DataAssociation());

    ArrayList<DataAssociation> dataAssociationList2 = new ArrayList<>();
    dataAssociationList2.add(new DataAssociation());

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(new FormValue());

    FormProperty formProperty = new FormProperty();
    formProperty.setFormValues(formValues);

    ArrayList<FormProperty> formPropertyList = new ArrayList<>();
    formPropertyList.add(formProperty);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(activitiListener);

    AlfrescoUserTask otherElement = mock(AlfrescoUserTask.class);
    when(otherElement.getBoundaryEvents()).thenReturn(boundaryEventList);
    when(otherElement.getDataInputAssociations()).thenReturn(dataAssociationList);
    when(otherElement.getDataOutputAssociations()).thenReturn(dataAssociationList2);
    when(otherElement.getFormProperties()).thenReturn(formPropertyList);
    when(otherElement.getTaskListeners()).thenReturn(activitiListenerList);
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
    when(otherElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherElement.getCandidateGroups()).thenReturn(new ArrayList<>());
    when(otherElement.getCandidateUsers()).thenReturn(new ArrayList<>());
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
    List<FormProperty> formProperties = userTask.getFormProperties();
    assertEquals(1, formProperties.size());
    List<FormValue> formValues2 = formProperties.get(0).getFormValues();
    assertEquals(1, formValues2.size());
    FormValue getResult = formValues2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link UserTask#setValues(UserTask)} with {@code UserTask}.
   *
   * <ul>
   *   <li>Then {@link UserTask} (default constructor) FormProperties first FormValues size is one.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#setValues(UserTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTask.setValues(UserTask)"})
  public void testSetValuesWithUserTask_thenUserTaskFormPropertiesFirstFormValuesSizeIsOne2() {
    // Arrange
    UserTask userTask = new UserTask();

    ArrayList<BoundaryEvent> boundaryEventList = new ArrayList<>();
    boundaryEventList.add(new BoundaryEvent());

    DataAssociation dataAssociation = mock(DataAssociation.class);
    when(dataAssociation.clone()).thenReturn(new DataAssociation());

    ArrayList<DataAssociation> dataAssociationList = new ArrayList<>();
    dataAssociationList.add(dataAssociation);

    ArrayList<DataAssociation> dataAssociationList2 = new ArrayList<>();
    dataAssociationList2.add(new DataAssociation());

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(new FormValue());

    FormProperty formProperty = new FormProperty();
    formProperty.setFormValues(formValues);

    ArrayList<FormProperty> formPropertyList = new ArrayList<>();
    formPropertyList.add(formProperty);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(activitiListener);

    AlfrescoUserTask otherElement = mock(AlfrescoUserTask.class);
    when(otherElement.getBoundaryEvents()).thenReturn(boundaryEventList);
    when(otherElement.getDataInputAssociations()).thenReturn(dataAssociationList);
    when(otherElement.getDataOutputAssociations()).thenReturn(dataAssociationList2);
    when(otherElement.getFormProperties()).thenReturn(formPropertyList);
    when(otherElement.getTaskListeners()).thenReturn(activitiListenerList);
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
    when(otherElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherElement.getCandidateGroups()).thenReturn(new ArrayList<>());
    when(otherElement.getCandidateUsers()).thenReturn(new ArrayList<>());
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
    verify(dataAssociation).clone();
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
    List<FormProperty> formProperties = userTask.getFormProperties();
    assertEquals(1, formProperties.size());
    List<FormValue> formValues2 = formProperties.get(0).getFormValues();
    assertEquals(1, formValues2.size());
    FormValue getResult = formValues2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link UserTask#setValues(UserTask)} with {@code UserTask}.
   *
   * <ul>
   *   <li>Then {@link UserTask} (default constructor) FormProperties first FormValues size is one.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#setValues(UserTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTask.setValues(UserTask)"})
  public void testSetValuesWithUserTask_thenUserTaskFormPropertiesFirstFormValuesSizeIsOne3() {
    // Arrange
    UserTask userTask = new UserTask();

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

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(new FormValue());

    FormProperty formProperty = new FormProperty();
    formProperty.setFormValues(formValues);

    ArrayList<FormProperty> formPropertyList = new ArrayList<>();
    formPropertyList.add(formProperty);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(activitiListener);

    AlfrescoUserTask otherElement = mock(AlfrescoUserTask.class);
    when(otherElement.getBoundaryEvents()).thenReturn(boundaryEventList);
    when(otherElement.getDataInputAssociations()).thenReturn(dataAssociationList);
    when(otherElement.getDataOutputAssociations()).thenReturn(dataAssociationList2);
    when(otherElement.getFormProperties()).thenReturn(formPropertyList);
    when(otherElement.getTaskListeners()).thenReturn(activitiListenerList);
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
    when(otherElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherElement.getCandidateGroups()).thenReturn(new ArrayList<>());
    when(otherElement.getCandidateUsers()).thenReturn(new ArrayList<>());
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
    verify(dataAssociation).clone();
    verify(dataAssociation2).clone();
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
    List<FormProperty> formProperties = userTask.getFormProperties();
    assertEquals(1, formProperties.size());
    List<FormValue> formValues2 = formProperties.get(0).getFormValues();
    assertEquals(1, formValues2.size());
    FormValue getResult = formValues2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link UserTask#setValues(UserTask)} with {@code UserTask}.
   *
   * <ul>
   *   <li>Then {@link UserTask} (default constructor) TaskListeners first FieldExtensions Empty.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#setValues(UserTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTask.setValues(UserTask)"})
  public void testSetValuesWithUserTask_thenUserTaskTaskListenersFirstFieldExtensionsEmpty() {
    // Arrange
    UserTask userTask = new UserTask();

    ArrayList<BoundaryEvent> boundaryEventList = new ArrayList<>();
    boundaryEventList.add(new BoundaryEvent());

    DataAssociation dataAssociation = mock(DataAssociation.class);
    when(dataAssociation.clone()).thenReturn(new DataAssociation());

    ArrayList<DataAssociation> dataAssociationList = new ArrayList<>();
    dataAssociationList.add(dataAssociation);

    DataAssociation dataAssociation2 = mock(DataAssociation.class);
    DataAssociation dataAssociation3 = new DataAssociation();
    when(dataAssociation2.clone()).thenReturn(dataAssociation3);

    ArrayList<DataAssociation> dataAssociationList2 = new ArrayList<>();
    dataAssociationList2.add(dataAssociation2);

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(new FormValue());

    FormProperty formProperty = mock(FormProperty.class);
    FormProperty formProperty2 = new FormProperty();
    when(formProperty.clone()).thenReturn(formProperty2);
    doNothing().when(formProperty).setFormValues(Mockito.<List<FormValue>>any());
    formProperty.setFormValues(formValues);

    ArrayList<FormProperty> formPropertyList = new ArrayList<>();
    formPropertyList.add(formProperty);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = mock(ActivitiListener.class);
    ActivitiListener activitiListener2 = new ActivitiListener();
    when(activitiListener.clone()).thenReturn(activitiListener2);
    doNothing().when(activitiListener).setFieldExtensions(Mockito.<List<FieldExtension>>any());
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(activitiListener);

    AlfrescoUserTask otherElement = mock(AlfrescoUserTask.class);
    when(otherElement.getBoundaryEvents()).thenReturn(boundaryEventList);
    when(otherElement.getDataInputAssociations()).thenReturn(dataAssociationList);
    when(otherElement.getDataOutputAssociations()).thenReturn(dataAssociationList2);
    when(otherElement.getFormProperties()).thenReturn(formPropertyList);
    when(otherElement.getTaskListeners()).thenReturn(activitiListenerList);
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
    when(otherElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherElement.getCandidateGroups()).thenReturn(new ArrayList<>());
    when(otherElement.getCandidateUsers()).thenReturn(new ArrayList<>());
    when(otherElement.getAttributes()).thenReturn(new HashMap<>());
    when(otherElement.getExtensionElements()).thenReturn(new HashMap<>());
    when(otherElement.getIoSpecification()).thenReturn(new IOSpecification());
    when(otherElement.getLoopCharacteristics()).thenReturn(new MultiInstanceLoopCharacteristics());

    // Act
    userTask.setValues(otherElement);

    // Assert
    verify(activitiListener).clone();
    verify(activitiListener).setFieldExtensions(isA(List.class));
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
    verify(dataAssociation).clone();
    verify(dataAssociation2).clone();
    verify(otherElement).getDocumentation();
    verify(otherElement, atLeast(1)).getExecutionListeners();
    verify(otherElement).getName();
    verify(otherElement).isAsynchronous();
    verify(otherElement).isNotExclusive();
    verify(formProperty).clone();
    verify(formProperty).setFormValues(isA(List.class));
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
    List<DataAssociation> dataOutputAssociations = userTask.getDataOutputAssociations();
    assertEquals(1, dataOutputAssociations.size());
    List<FormProperty> formProperties = userTask.getFormProperties();
    assertEquals(1, formProperties.size());
    List<ActivitiListener> taskListeners = userTask.getTaskListeners();
    assertEquals(1, taskListeners.size());
    ActivitiListener getResult = taskListeners.get(0);
    assertTrue(getResult.getFieldExtensions().isEmpty());
    assertSame(activitiListener2, getResult);
    assertSame(dataAssociation3, dataOutputAssociations.get(0));
    assertSame(formProperty2, formProperties.get(0));
  }

  /**
   * Test {@link UserTask#setValues(UserTask)} with {@code UserTask}.
   *
   * <ul>
   *   <li>Then {@link UserTask} (default constructor) TaskListeners first FieldExtensions size is
   *       one.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#setValues(UserTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTask.setValues(UserTask)"})
  public void testSetValuesWithUserTask_thenUserTaskTaskListenersFirstFieldExtensionsSizeIsOne() {
    // Arrange
    UserTask userTask = new UserTask();

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

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(new FormValue());

    FormProperty formProperty = mock(FormProperty.class);
    when(formProperty.clone()).thenReturn(new FormProperty());
    doNothing().when(formProperty).setFormValues(Mockito.<List<FormValue>>any());
    formProperty.setFormValues(formValues);

    ArrayList<FormProperty> formPropertyList = new ArrayList<>();
    formPropertyList.add(formProperty);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(activitiListener);

    AlfrescoUserTask otherElement = mock(AlfrescoUserTask.class);
    when(otherElement.getBoundaryEvents()).thenReturn(boundaryEventList);
    when(otherElement.getDataInputAssociations()).thenReturn(dataAssociationList);
    when(otherElement.getDataOutputAssociations()).thenReturn(dataAssociationList2);
    when(otherElement.getFormProperties()).thenReturn(formPropertyList);
    when(otherElement.getTaskListeners()).thenReturn(activitiListenerList);
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
    when(otherElement.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherElement.getCandidateGroups()).thenReturn(new ArrayList<>());
    when(otherElement.getCandidateUsers()).thenReturn(new ArrayList<>());
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
    verify(dataAssociation).clone();
    verify(dataAssociation2).clone();
    verify(otherElement).getDocumentation();
    verify(otherElement, atLeast(1)).getExecutionListeners();
    verify(otherElement).getName();
    verify(otherElement).isAsynchronous();
    verify(otherElement).isNotExclusive();
    verify(formProperty).clone();
    verify(formProperty).setFormValues(isA(List.class));
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
    List<ActivitiListener> taskListeners = userTask.getTaskListeners();
    assertEquals(1, taskListeners.size());
    List<FieldExtension> fieldExtensions2 = taskListeners.get(0).getFieldExtensions();
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
   * Test {@link UserTask#setValues(UserTask)} with {@code UserTask}.
   *
   * <ul>
   *   <li>When {@link UserTask} (default constructor) DataInputAssociations is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#setValues(UserTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTask.setValues(UserTask)"})
  public void testSetValuesWithUserTask_whenUserTaskDataInputAssociationsIsNull() {
    // Arrange
    UserTask userTask = new UserTask();

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    UserTask otherElement = new UserTask();
    MultiInstanceLoopCharacteristics loopCharacteristics = new MultiInstanceLoopCharacteristics();
    otherElement.setLoopCharacteristics(loopCharacteristics);
    IOSpecification ioSpecification = new IOSpecification();
    otherElement.setIoSpecification(ioSpecification);
    otherElement.setDataInputAssociations(null);
    otherElement.setDataOutputAssociations(dataOutputAssociations);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    userTask.setValues(otherElement);

    // Assert that nothing has changed
    List<BoundaryEvent> boundaryEvents2 = otherElement.getBoundaryEvents();
    assertEquals(1, boundaryEvents2.size());
    List<DataAssociation> dataOutputAssociations2 = otherElement.getDataOutputAssociations();
    assertEquals(1, dataOutputAssociations2.size());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.getFormProperties().isEmpty());
    assertTrue(otherElement.getTaskListeners().isEmpty());
    assertTrue(otherElement.getCustomGroupIdentityLinks().isEmpty());
    assertTrue(otherElement.getCustomUserIdentityLinks().isEmpty());
    assertTrue(otherElement.hasMultiInstanceLoopCharacteristics());
    assertTrue(otherElement.isExclusive());
    assertSame(boundaryEvents, boundaryEvents2);
    assertSame(dataOutputAssociations, dataOutputAssociations2);
    assertSame(ioSpecification, otherElement.getIoSpecification());
    assertSame(loopCharacteristics, otherElement.getLoopCharacteristics());
  }

  /**
   * Test {@link UserTask#setValues(UserTask)} with {@code UserTask}.
   *
   * <ul>
   *   <li>When {@link UserTask} (default constructor) DataOutputAssociations is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link UserTask#setValues(UserTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTask.setValues(UserTask)"})
  public void testSetValuesWithUserTask_whenUserTaskDataOutputAssociationsIsNull() {
    // Arrange
    UserTask userTask = new UserTask();

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(new DataAssociation());

    ArrayList<BoundaryEvent> boundaryEvents = new ArrayList<>();
    boundaryEvents.add(new BoundaryEvent());

    UserTask otherElement = new UserTask();
    MultiInstanceLoopCharacteristics loopCharacteristics = new MultiInstanceLoopCharacteristics();
    otherElement.setLoopCharacteristics(loopCharacteristics);
    IOSpecification ioSpecification = new IOSpecification();
    otherElement.setIoSpecification(ioSpecification);
    otherElement.setDataInputAssociations(dataInputAssociations);
    otherElement.setDataOutputAssociations(null);
    otherElement.setBoundaryEvents(boundaryEvents);

    // Act
    userTask.setValues(otherElement);

    // Assert that nothing has changed
    List<BoundaryEvent> boundaryEvents2 = otherElement.getBoundaryEvents();
    assertEquals(1, boundaryEvents2.size());
    List<DataAssociation> dataInputAssociations2 = otherElement.getDataInputAssociations();
    assertEquals(1, dataInputAssociations2.size());
    assertFalse(otherElement.isForCompensation());
    assertFalse(otherElement.isAsynchronous());
    assertFalse(otherElement.isNotExclusive());
    assertFalse(otherElement.isExtended());
    assertTrue(otherElement.getFormProperties().isEmpty());
    assertTrue(otherElement.getTaskListeners().isEmpty());
    assertTrue(otherElement.getCustomGroupIdentityLinks().isEmpty());
    assertTrue(otherElement.getCustomUserIdentityLinks().isEmpty());
    assertTrue(otherElement.hasMultiInstanceLoopCharacteristics());
    assertTrue(otherElement.isExclusive());
    assertSame(boundaryEvents, boundaryEvents2);
    assertSame(dataInputAssociations, dataInputAssociations2);
    assertSame(ioSpecification, otherElement.getIoSpecification());
    assertSame(loopCharacteristics, otherElement.getLoopCharacteristics());
  }

  /**
   * Test {@link UserTask#accept(ReferenceOverrider)}.
   *
   * <p>Method under test: {@link UserTask#accept(ReferenceOverrider)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTask.accept(ReferenceOverrider)"})
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
   *
   * <p>Methods under test:
   *
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
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void UserTask.<init>()",
    "String UserTask.getAssignee()",
    "String UserTask.getBusinessCalendarName()",
    "List UserTask.getCandidateGroups()",
    "List UserTask.getCandidateUsers()",
    "String UserTask.getCategory()",
    "Map UserTask.getCustomGroupIdentityLinks()",
    "List UserTask.getCustomProperties()",
    "Map UserTask.getCustomUserIdentityLinks()",
    "String UserTask.getDueDate()",
    "String UserTask.getExtensionId()",
    "String UserTask.getFormKey()",
    "List UserTask.getFormProperties()",
    "String UserTask.getOwner()",
    "String UserTask.getPriority()",
    "String UserTask.getSkipExpression()",
    "List UserTask.getTaskListeners()",
    "void UserTask.setAssignee(String)",
    "void UserTask.setBusinessCalendarName(String)",
    "void UserTask.setCandidateGroups(List)",
    "void UserTask.setCandidateUsers(List)",
    "void UserTask.setCategory(String)",
    "void UserTask.setCustomGroupIdentityLinks(Map)",
    "void UserTask.setCustomProperties(List)",
    "void UserTask.setCustomUserIdentityLinks(Map)",
    "void UserTask.setDueDate(String)",
    "void UserTask.setExtensionId(String)",
    "void UserTask.setFormKey(String)",
    "void UserTask.setFormProperties(List)",
    "void UserTask.setOwner(String)",
    "void UserTask.setPriority(String)",
    "void UserTask.setSkipExpression(String)",
    "void UserTask.setTaskListeners(List)"
  })
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
    Map<String, Set<String>> actualCustomGroupIdentityLinks =
        actualUserTask.getCustomGroupIdentityLinks();
    List<CustomProperty> actualCustomProperties = actualUserTask.getCustomProperties();
    Map<String, Set<String>> actualCustomUserIdentityLinks =
        actualUserTask.getCustomUserIdentityLinks();
    String actualDueDate = actualUserTask.getDueDate();
    String actualExtensionId = actualUserTask.getExtensionId();
    String actualFormKey = actualUserTask.getFormKey();
    List<FormProperty> actualFormProperties = actualUserTask.getFormProperties();
    String actualOwner = actualUserTask.getOwner();
    String actualPriority = actualUserTask.getPriority();
    String actualSkipExpression = actualUserTask.getSkipExpression();
    List<ActivitiListener> actualTaskListeners = actualUserTask.getTaskListeners();

    // Assert
    assertEquals("2020-03-01", actualDueDate);
    assertEquals("42", actualExtensionId);
    assertEquals("Assignee", actualAssignee);
    assertEquals("Business Calendar Name", actualBusinessCalendarName);
    assertEquals("Category", actualCategory);
    assertEquals("Form Key", actualFormKey);
    assertEquals("Owner", actualOwner);
    assertEquals("Priority", actualPriority);
    assertEquals("Skip Expression", actualSkipExpression);
    assertNull(actualUserTask.getBehavior());
    assertNull(actualUserTask.getDefaultFlow());
    assertNull(actualUserTask.getFailedJobRetryTimeCycleValue());
    assertNull(actualUserTask.getId());
    assertNull(actualUserTask.getDocumentation());
    assertNull(actualUserTask.getName());
    assertNull(actualUserTask.getParentContainer());
    assertNull(actualUserTask.getIoSpecification());
    assertNull(actualUserTask.getLoopCharacteristics());
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
