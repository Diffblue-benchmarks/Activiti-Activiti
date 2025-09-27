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
package org.activiti.validation.validator.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.Signal;
import org.activiti.bpmn.model.SubProcess;
import org.activiti.bpmn.model.UserTask;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UserTaskValidatorDiffblueTest {
  /**
   * Test {@link UserTaskValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <p>Method under test: {@link UserTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation() {
    // Arrange
    UserTaskValidator userTaskValidator = new UserTaskValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getImplementationType()).thenReturn("Implementation Type");
    when(activitiListener.getImplementation()).thenReturn("Implementation");

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(activitiListener);

    UserTask userTask = new UserTask();
    userTask.setTaskListeners(taskListeners);

    ArrayList<UserTask> userTaskList = new ArrayList<>();
    userTaskList.add(userTask);

    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<UserTask>>any())).thenReturn(userTaskList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    userTaskValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(activitiListener).getImplementation();
    verify(activitiListener).getImplementationType();
    verify(process).findFlowElementsOfType(isA(Class.class));
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link UserTaskValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link ActivitiListener} {@link ActivitiListener#getImplementationType()} return
   *       {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given ActivitiListener getImplementationType() return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenActivitiListenerGetImplementationTypeReturnNull() {
    // Arrange
    UserTaskValidator userTaskValidator = new UserTaskValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ActivitiListener activitiListener = mock(ActivitiListener.class);
    when(activitiListener.getImplementationType()).thenReturn(null);
    when(activitiListener.getImplementation()).thenReturn("Implementation");

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(activitiListener);

    UserTask userTask = new UserTask();
    userTask.setTaskListeners(taskListeners);

    ArrayList<UserTask> userTaskList = new ArrayList<>();
    userTaskList.add(userTask);

    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<UserTask>>any())).thenReturn(userTaskList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    userTaskValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(activitiListener).getImplementation();
    verify(activitiListener).getImplementationType();
    verify(process).getId();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getName();
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("Name", getResult.getProcessDefinitionName());
    assertEquals("USER_TASK_LISTENER_IMPLEMENTATION_MISSING", getResult.getDefaultDescription());
    assertEquals("USER_TASK_LISTENER_IMPLEMENTATION_MISSING", getResult.getKey());
    assertEquals("USER_TASK_LISTENER_IMPLEMENTATION_MISSING", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link UserTaskValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ActivitiListener} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given ArrayList() add ActivitiListener (default constructor); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenArrayListAddActivitiListener_thenArrayListSizeIsOne() {
    // Arrange
    UserTaskValidator userTaskValidator = new UserTaskValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(new ActivitiListener());

    UserTask userTask = new UserTask();
    userTask.setTaskListeners(taskListeners);

    ArrayList<UserTask> userTaskList = new ArrayList<>();
    userTaskList.add(userTask);

    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<UserTask>>any())).thenReturn(userTaskList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    userTaskValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process).getId();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getName();
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("Name", getResult.getProcessDefinitionName());
    assertEquals("USER_TASK_LISTENER_IMPLEMENTATION_MISSING", getResult.getDefaultDescription());
    assertEquals("USER_TASK_LISTENER_IMPLEMENTATION_MISSING", getResult.getKey());
    assertEquals("USER_TASK_LISTENER_IMPLEMENTATION_MISSING", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link UserTaskValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ActivitiListener} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given ArrayList() add ActivitiListener (default constructor); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenArrayListAddActivitiListener_thenArrayListSizeIsTwo() {
    // Arrange
    UserTaskValidator userTaskValidator = new UserTaskValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(new ActivitiListener());
    taskListeners.add(new ActivitiListener());

    UserTask userTask = new UserTask();
    userTask.setTaskListeners(taskListeners);

    ArrayList<UserTask> userTaskList = new ArrayList<>();
    userTaskList.add(userTask);

    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<UserTask>>any())).thenReturn(userTaskList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    userTaskValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process, atLeast(1)).getId();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getName();
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("Name", getResult.getProcessDefinitionName());
    assertEquals("USER_TASK_LISTENER_IMPLEMENTATION_MISSING", getResult.getDefaultDescription());
    assertEquals("USER_TASK_LISTENER_IMPLEMENTATION_MISSING", getResult.getKey());
    assertEquals("USER_TASK_LISTENER_IMPLEMENTATION_MISSING", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link UserTaskValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link UserTask} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given ArrayList() add UserTask (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenArrayListAddUserTask_thenArrayListEmpty() {
    // Arrange
    UserTaskValidator userTaskValidator = new UserTaskValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<UserTask> userTaskList = new ArrayList<>();
    userTaskList.add(new UserTask());

    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<UserTask>>any())).thenReturn(userTaskList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    userTaskValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(process).findFlowElementsOfType(isA(Class.class));
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link UserTaskValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link SubProcess} (default constructor) addFlowElement {@link AdhocSubProcess}
   *       (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link UserTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given SubProcess (default constructor) addFlowElement AdhocSubProcess (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenSubProcessAddFlowElementAdhocSubProcess() {
    // Arrange
    UserTaskValidator userTaskValidator = new UserTaskValidator();
    BpmnModel bpmnModel = new BpmnModel();

    SubProcess element = new SubProcess();
    element.addFlowElement(new AdhocSubProcess());

    SubProcess element2 = new SubProcess();
    element2.addFlowElement(element);

    SubProcess element3 = new SubProcess();
    element3.addFlowElement(element2);

    Process process = new Process();
    process.addFlowElement(element3);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    userTaskValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link UserTaskValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link UserTask} (default constructor) TaskListeners is {@code null}.
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given UserTask (default constructor) TaskListeners is 'null'; then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenUserTaskTaskListenersIsNull_thenArrayListEmpty() {
    // Arrange
    UserTaskValidator userTaskValidator = new UserTaskValidator();
    BpmnModel bpmnModel = new BpmnModel();

    UserTask userTask = new UserTask();
    userTask.setTaskListeners(null);

    ArrayList<UserTask> userTaskList = new ArrayList<>();
    userTaskList.add(userTask);

    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<UserTask>>any())).thenReturn(userTaskList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    userTaskValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(process).findFlowElementsOfType(isA(Class.class));
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link UserTaskValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>When {@link Process} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link UserTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); when Process (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UserTaskValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_whenProcess_thenArrayListEmpty() {
    // Arrange
    UserTaskValidator userTaskValidator = new UserTaskValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    userTaskValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }
}
