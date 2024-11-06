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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.SendTask;
import org.activiti.bpmn.model.Signal;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SendTaskValidatorDiffblueTest {
  /**
   * Test {@link SendTaskValidator#executeValidation(BpmnModel, Process, List)}.
   * <p>
   * Method under test:
   * {@link SendTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  void testExecuteValidation() {
    // Arrange
    SendTaskValidator sendTaskValidator = new SendTaskValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SendTask> sendTaskList = new ArrayList<>();
    sendTaskList.add(new SendTask());
    sendTaskList.add(new SendTask());
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<SendTask>>any())).thenReturn(sendTaskList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sendTaskValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process, atLeast(1)).getId();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getName();
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("SEND_TASK_INVALID_IMPLEMENTATION", getResult.getDefaultDescription());
    assertEquals("SEND_TASK_INVALID_IMPLEMENTATION", getResult.getKey());
    assertEquals("SEND_TASK_INVALID_IMPLEMENTATION", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
  }

  /**
   * Test {@link SendTaskValidator#executeValidation(BpmnModel, Process, List)}.
   * <p>
   * Method under test:
   * {@link SendTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  void testExecuteValidation2() {
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
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then {@link BpmnModel} (default constructor) Resources {@link List}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SendTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given ArrayList(); then BpmnModel (default constructor) Resources List")
  void testExecuteValidation_givenArrayList_thenBpmnModelResourcesList() {
    // Arrange
    SendTaskValidator sendTaskValidator = new SendTaskValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<SendTask>>any())).thenReturn(new ArrayList<>());
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
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} first ActivityId is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SendTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() first ActivityId is 'null'")
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
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} first DefaultDescription is
   * {@code SEND_TASK_INVALID_TYPE}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SendTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() first DefaultDescription is 'SEND_TASK_INVALID_TYPE'")
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
    assertEquals("42", getResult.getActivityId());
    assertEquals("Name", getResult.getActivityName());
    assertEquals("SEND_TASK_INVALID_TYPE", getResult.getDefaultDescription());
    assertEquals("SEND_TASK_INVALID_TYPE", getResult.getKey());
    assertEquals("SEND_TASK_INVALID_TYPE", getResult.getProblem());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link SendTaskValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} second ActivityId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SendTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() second ActivityId is '42'")
  void testExecuteValidation_thenArrayListSecondActivityIdIs42() {
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
    assertEquals("MAIL_TASK_NO_CONTENT", getResult.getDefaultDescription());
    assertEquals("MAIL_TASK_NO_CONTENT", getResult.getKey());
    assertEquals("MAIL_TASK_NO_CONTENT", getResult.getProblem());
    ValidationError getResult2 = errors.get(0);
    assertEquals("MAIL_TASK_NO_RECIPIENT", getResult2.getDefaultDescription());
    assertEquals("MAIL_TASK_NO_RECIPIENT", getResult2.getKey());
    assertEquals("MAIL_TASK_NO_RECIPIENT", getResult2.getProblem());
    assertEquals("Name", getResult.getActivityName());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
  }

  /**
   * Test {@link SendTaskValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>When {@link BpmnModel}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SendTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); when BpmnModel; then ArrayList() Empty")
  void testExecuteValidation_whenBpmnModel_thenArrayListEmpty() {
    // Arrange
    SendTaskValidator sendTaskValidator = new SendTaskValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sendTaskValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link SendTaskValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>When {@link Process} (default constructor).</li>
   *   <li>Then {@link BpmnModel} (default constructor) Resources {@link List}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SendTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); when Process (default constructor); then BpmnModel (default constructor) Resources List")
  void testExecuteValidation_whenProcess_thenBpmnModelResourcesList() {
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
