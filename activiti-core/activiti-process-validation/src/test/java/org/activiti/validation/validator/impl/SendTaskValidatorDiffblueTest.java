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
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BooleanDataObject;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.SendTask;
import org.activiti.bpmn.model.Signal;
import org.activiti.bpmn.model.SubProcess;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SendTaskValidatorDiffblueTest {
  /**
   * Test {@link SendTaskValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link SendTask} (default constructor) Type is {@code camel}.
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link SendTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given SendTask (default constructor) Type is 'camel'; then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SendTaskValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenSendTaskTypeIsCamel_thenArrayListEmpty() {
    // Arrange
    SendTaskValidator sendTaskValidator = new SendTaskValidator();
    BpmnModel bpmnModel = new BpmnModel();

    SendTask sendTask = new SendTask();
    sendTask.setType("camel");

    ArrayList<SendTask> sendTaskList = new ArrayList<>();
    sendTaskList.add(sendTask);

    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<SendTask>>any())).thenReturn(sendTaskList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sendTaskValidator.executeValidation(bpmnModel, process, errors);

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
   * Test {@link SendTaskValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link SendTask} (default constructor) Type is {@code mule}.
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link SendTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given SendTask (default constructor) Type is 'mule'; then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SendTaskValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenSendTaskTypeIsMule_thenArrayListEmpty() {
    // Arrange
    SendTaskValidator sendTaskValidator = new SendTaskValidator();
    BpmnModel bpmnModel = new BpmnModel();

    SendTask sendTask = new SendTask();
    sendTask.setType("mule");

    ArrayList<SendTask> sendTaskList = new ArrayList<>();
    sendTaskList.add(sendTask);

    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<SendTask>>any())).thenReturn(sendTaskList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sendTaskValidator.executeValidation(bpmnModel, process, errors);

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
   * Test {@link SendTaskValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link SubProcess} (default constructor) addFlowElement {@link AdhocSubProcess}
   *       (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link SendTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given SubProcess (default constructor) addFlowElement AdhocSubProcess (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SendTaskValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenSubProcessAddFlowElementAdhocSubProcess() {
    // Arrange
    SendTaskValidator sendTaskValidator = new SendTaskValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.setInterfaces(null);

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
    sendTaskValidator.executeValidation(bpmnModel, process, errors);

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
   * Test {@link SendTaskValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link SubProcess} (default constructor) addFlowElement {@link BooleanDataObject}
   *       (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link SendTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given SubProcess (default constructor) addFlowElement BooleanDataObject (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SendTaskValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenSubProcessAddFlowElementBooleanDataObject() {
    // Arrange
    SendTaskValidator sendTaskValidator = new SendTaskValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.setInterfaces(null);

    SubProcess element = new SubProcess();
    element.addFlowElement(new BooleanDataObject());

    SubProcess element2 = new SubProcess();
    element2.addFlowElement(element);

    SubProcess element3 = new SubProcess();
    element3.addFlowElement(element2);

    Process process = new Process();
    process.addFlowElement(element3);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sendTaskValidator.executeValidation(bpmnModel, process, errors);

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
   * Test {@link SendTaskValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} first ActivityId is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link SendTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); then ArrayList() first ActivityId is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SendTaskValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_thenArrayListFirstActivityIdIs42() {
    // Arrange
    SendTaskValidator sendTaskValidator = new SendTaskValidator();
    BpmnModel bpmnModel = new BpmnModel();

    SendTask sendTask = mock(SendTask.class);
    when(sendTask.getXmlColumnNumber()).thenReturn(10);
    when(sendTask.getXmlRowNumber()).thenReturn(10);
    when(sendTask.getId()).thenReturn("42");
    when(sendTask.getName()).thenReturn("Name");
    when(sendTask.getImplementationType()).thenReturn("Implementation Type");
    when(sendTask.getType()).thenReturn("");

    ArrayList<SendTask> sendTaskList = new ArrayList<>();
    sendTaskList.add(sendTask);

    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<SendTask>>any())).thenReturn(sendTaskList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sendTaskValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process).getId();
    verify(sendTask).getId();
    verify(sendTask).getXmlColumnNumber();
    verify(sendTask).getXmlRowNumber();
    verify(sendTask).getName();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getName();
    verify(sendTask, atLeast(1)).getImplementationType();
    verify(sendTask, atLeast(1)).getType();
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("Name", getResult.getActivityName());
    assertEquals("SEND_TASK_INVALID_IMPLEMENTATION", getResult.getDefaultDescription());
    assertEquals("SEND_TASK_INVALID_IMPLEMENTATION", getResult.getKey());
    assertEquals("SEND_TASK_INVALID_IMPLEMENTATION", getResult.getProblem());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
  }

  /**
   * Test {@link SendTaskValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} first ActivityId is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link SendTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); then ArrayList() first ActivityId is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SendTaskValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_thenArrayListFirstActivityIdIsNull() {
    // Arrange
    SendTaskValidator sendTaskValidator = new SendTaskValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SendTask> sendTaskList = new ArrayList<>();
    sendTaskList.add(new SendTask());

    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<SendTask>>any())).thenReturn(sendTaskList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sendTaskValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process).getId();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getName();
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
  }

  /**
   * Test {@link SendTaskValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} first DefaultDescription is {@code
   *       SEND_TASK_INVALID_TYPE}.
   * </ul>
   *
   * <p>Method under test: {@link SendTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); then ArrayList() first DefaultDescription is 'SEND_TASK_INVALID_TYPE'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SendTaskValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_thenArrayListFirstDefaultDescriptionIsSendTaskInvalidType() {
    // Arrange
    SendTaskValidator sendTaskValidator = new SendTaskValidator();
    BpmnModel bpmnModel = new BpmnModel();

    SendTask sendTask = mock(SendTask.class);
    when(sendTask.getXmlColumnNumber()).thenReturn(10);
    when(sendTask.getXmlRowNumber()).thenReturn(10);
    when(sendTask.getId()).thenReturn("42");
    when(sendTask.getName()).thenReturn("Name");
    when(sendTask.getImplementationType()).thenReturn("Implementation Type");
    when(sendTask.getType()).thenReturn("Type");

    ArrayList<SendTask> sendTaskList = new ArrayList<>();
    sendTaskList.add(sendTask);

    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<SendTask>>any())).thenReturn(sendTaskList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sendTaskValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process).getId();
    verify(sendTask).getId();
    verify(sendTask).getXmlColumnNumber();
    verify(sendTask).getXmlRowNumber();
    verify(sendTask).getName();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getName();
    verify(sendTask).getImplementationType();
    verify(sendTask, atLeast(1)).getType();
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("SEND_TASK_INVALID_TYPE", getResult.getDefaultDescription());
    assertEquals("SEND_TASK_INVALID_TYPE", getResult.getKey());
    assertEquals("SEND_TASK_INVALID_TYPE", getResult.getProblem());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link SendTaskValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link SendTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SendTaskValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_thenArrayListSizeIsTwo() {
    // Arrange
    SendTaskValidator sendTaskValidator = new SendTaskValidator();
    BpmnModel bpmnModel = new BpmnModel();

    SendTask sendTask = mock(SendTask.class);
    when(sendTask.getFieldExtensions()).thenReturn(new ArrayList<>());
    when(sendTask.getXmlColumnNumber()).thenReturn(10);
    when(sendTask.getXmlRowNumber()).thenReturn(10);
    when(sendTask.getId()).thenReturn("42");
    when(sendTask.getName()).thenReturn("Name");
    when(sendTask.getImplementationType()).thenReturn("Implementation Type");
    when(sendTask.getType()).thenReturn("mail");

    ArrayList<SendTask> sendTaskList = new ArrayList<>();
    sendTaskList.add(sendTask);

    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<SendTask>>any())).thenReturn(sendTaskList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sendTaskValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process, atLeast(1)).getId();
    verify(sendTask, atLeast(1)).getId();
    verify(sendTask, atLeast(1)).getXmlColumnNumber();
    verify(sendTask, atLeast(1)).getXmlRowNumber();
    verify(sendTask, atLeast(1)).getName();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getName();
    verify(sendTask).getImplementationType();
    verify(sendTask, atLeast(1)).getType();
    verify(sendTask).getFieldExtensions();
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("42", getResult.getActivityId());
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("MAIL_TASK_NO_CONTENT", getResult.getDefaultDescription());
    assertEquals("MAIL_TASK_NO_CONTENT", getResult.getKey());
    assertEquals("MAIL_TASK_NO_CONTENT", getResult.getProblem());
    ValidationError getResult2 = errors.get(0);
    assertEquals("MAIL_TASK_NO_RECIPIENT", getResult2.getDefaultDescription());
    assertEquals("MAIL_TASK_NO_RECIPIENT", getResult2.getKey());
    assertEquals("MAIL_TASK_NO_RECIPIENT", getResult2.getProblem());
    assertEquals("Name", getResult.getActivityName());
    assertEquals("Name", getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link SendTaskValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>When {@link Process} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link SendTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); when Process (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SendTaskValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_whenProcess_thenArrayListEmpty() {
    // Arrange
    SendTaskValidator sendTaskValidator = new SendTaskValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sendTaskValidator.executeValidation(bpmnModel, process, errors);

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
