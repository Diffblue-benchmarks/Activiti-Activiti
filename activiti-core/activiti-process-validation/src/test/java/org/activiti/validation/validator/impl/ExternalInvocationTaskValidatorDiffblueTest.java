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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.activiti.bpmn.model.FieldExtension;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SendTask;
import org.activiti.bpmn.model.TaskWithFieldExtensions;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.Test;

class ExternalInvocationTaskValidatorDiffblueTest {
  /**
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForEmail(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  void testValidateFieldDeclarationsForEmail() {
    // Arrange
    SendTaskValidator sendTaskValidator = new SendTaskValidator();
    Process process = new Process();
    SendTask task = new SendTask();
    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sendTaskValidator.validateFieldDeclarationsForEmail(process, task, fieldExtensions, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("MAIL_TASK_NO_CONTENT", getResult.getDefaultDescription());
    assertEquals("MAIL_TASK_NO_CONTENT", getResult.getKey());
    assertEquals("MAIL_TASK_NO_CONTENT", getResult.getProblem());
    ValidationError getResult2 = errors.get(0);
    assertEquals("MAIL_TASK_NO_RECIPIENT", getResult2.getDefaultDescription());
    assertEquals("MAIL_TASK_NO_RECIPIENT", getResult2.getKey());
    assertEquals("MAIL_TASK_NO_RECIPIENT", getResult2.getProblem());
    assertNull(getResult2.getActivityId());
    assertNull(getResult.getActivityId());
    assertNull(getResult2.getActivityName());
    assertNull(getResult.getActivityName());
    assertNull(getResult2.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult2.getProcessDefinitionName());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult2.getValidatorSetName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlLineNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult2.isWarning());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(task.getBoundaryEvents().isEmpty());
    assertTrue(task.getDataInputAssociations().isEmpty());
    assertTrue(task.getDataOutputAssociations().isEmpty());
    assertTrue(task.getMapExceptions().isEmpty());
    assertTrue(task.getExecutionListeners().isEmpty());
    assertTrue(task.getIncomingFlows().isEmpty());
    assertTrue(task.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(task.getFieldExtensions().isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForEmail(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  void testValidateFieldDeclarationsForEmail2() {
    // Arrange
    SendTaskValidator sendTaskValidator = new SendTaskValidator();
    Process process = new Process();
    SendTask task = new SendTask();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setFieldName("to");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sendTaskValidator.validateFieldDeclarationsForEmail(process, task, fieldExtensions, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("MAIL_TASK_NO_CONTENT", getResult.getDefaultDescription());
    assertEquals("MAIL_TASK_NO_CONTENT", getResult.getKey());
    assertEquals("MAIL_TASK_NO_CONTENT", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(task.getBoundaryEvents().isEmpty());
    assertTrue(task.getDataInputAssociations().isEmpty());
    assertTrue(task.getDataOutputAssociations().isEmpty());
    assertTrue(task.getMapExceptions().isEmpty());
    assertTrue(task.getExecutionListeners().isEmpty());
    assertTrue(task.getIncomingFlows().isEmpty());
    assertTrue(task.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(task.getFieldExtensions().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForEmail(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  void testValidateFieldDeclarationsForEmail3() {
    // Arrange
    SendTaskValidator sendTaskValidator = new SendTaskValidator();
    Process process = new Process();
    SendTask task = new SendTask();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setFieldName("html");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sendTaskValidator.validateFieldDeclarationsForEmail(process, task, fieldExtensions, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("MAIL_TASK_NO_RECIPIENT", getResult.getDefaultDescription());
    assertEquals("MAIL_TASK_NO_RECIPIENT", getResult.getKey());
    assertEquals("MAIL_TASK_NO_RECIPIENT", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(task.getBoundaryEvents().isEmpty());
    assertTrue(task.getDataInputAssociations().isEmpty());
    assertTrue(task.getDataOutputAssociations().isEmpty());
    assertTrue(task.getMapExceptions().isEmpty());
    assertTrue(task.getExecutionListeners().isEmpty());
    assertTrue(task.getIncomingFlows().isEmpty());
    assertTrue(task.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(task.getFieldExtensions().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForEmail(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  void testValidateFieldDeclarationsForEmail4() {
    // Arrange
    SendTaskValidator sendTaskValidator = new SendTaskValidator();
    Process process = new Process();
    SendTask task = new SendTask();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setFieldName("htmlVar");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sendTaskValidator.validateFieldDeclarationsForEmail(process, task, fieldExtensions, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("MAIL_TASK_NO_RECIPIENT", getResult.getDefaultDescription());
    assertEquals("MAIL_TASK_NO_RECIPIENT", getResult.getKey());
    assertEquals("MAIL_TASK_NO_RECIPIENT", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(task.getBoundaryEvents().isEmpty());
    assertTrue(task.getDataInputAssociations().isEmpty());
    assertTrue(task.getDataOutputAssociations().isEmpty());
    assertTrue(task.getMapExceptions().isEmpty());
    assertTrue(task.getExecutionListeners().isEmpty());
    assertTrue(task.getIncomingFlows().isEmpty());
    assertTrue(task.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(task.getFieldExtensions().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForEmail(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  void testValidateFieldDeclarationsForEmail5() {
    // Arrange
    SendTaskValidator sendTaskValidator = new SendTaskValidator();
    Process process = new Process();
    SendTask task = new SendTask();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setFieldName("text");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sendTaskValidator.validateFieldDeclarationsForEmail(process, task, fieldExtensions, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("MAIL_TASK_NO_RECIPIENT", getResult.getDefaultDescription());
    assertEquals("MAIL_TASK_NO_RECIPIENT", getResult.getKey());
    assertEquals("MAIL_TASK_NO_RECIPIENT", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(task.getBoundaryEvents().isEmpty());
    assertTrue(task.getDataInputAssociations().isEmpty());
    assertTrue(task.getDataOutputAssociations().isEmpty());
    assertTrue(task.getMapExceptions().isEmpty());
    assertTrue(task.getExecutionListeners().isEmpty());
    assertTrue(task.getIncomingFlows().isEmpty());
    assertTrue(task.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(task.getFieldExtensions().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForEmail(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  void testValidateFieldDeclarationsForEmail6() {
    // Arrange
    SendTaskValidator sendTaskValidator = new SendTaskValidator();
    Process process = new Process();
    SendTask task = new SendTask();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setFieldName("textVar");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sendTaskValidator.validateFieldDeclarationsForEmail(process, task, fieldExtensions, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("MAIL_TASK_NO_RECIPIENT", getResult.getDefaultDescription());
    assertEquals("MAIL_TASK_NO_RECIPIENT", getResult.getKey());
    assertEquals("MAIL_TASK_NO_RECIPIENT", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(task.getBoundaryEvents().isEmpty());
    assertTrue(task.getDataInputAssociations().isEmpty());
    assertTrue(task.getDataOutputAssociations().isEmpty());
    assertTrue(task.getMapExceptions().isEmpty());
    assertTrue(task.getExecutionListeners().isEmpty());
    assertTrue(task.getIncomingFlows().isEmpty());
    assertTrue(task.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(task.getFieldExtensions().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForShell(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  void testValidateFieldDeclarationsForShell() {
    // Arrange
    SendTaskValidator sendTaskValidator = new SendTaskValidator();
    Process process = new Process();
    SendTask task = new SendTask();
    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sendTaskValidator.validateFieldDeclarationsForShell(process, task, fieldExtensions, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("SHELL_TASK_NO_COMMAND", getResult.getDefaultDescription());
    assertEquals("SHELL_TASK_NO_COMMAND", getResult.getKey());
    assertEquals("SHELL_TASK_NO_COMMAND", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(task.getBoundaryEvents().isEmpty());
    assertTrue(task.getDataInputAssociations().isEmpty());
    assertTrue(task.getDataOutputAssociations().isEmpty());
    assertTrue(task.getMapExceptions().isEmpty());
    assertTrue(task.getExecutionListeners().isEmpty());
    assertTrue(task.getIncomingFlows().isEmpty());
    assertTrue(task.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(task.getFieldExtensions().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForShell(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  void testValidateFieldDeclarationsForShell2() {
    // Arrange
    SendTaskValidator sendTaskValidator = new SendTaskValidator();
    Process process = new Process();
    SendTask task = new SendTask();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setFieldName("command");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sendTaskValidator.validateFieldDeclarationsForShell(process, task, fieldExtensions, errors);

    // Assert that nothing has changed
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(task.getBoundaryEvents().isEmpty());
    assertTrue(task.getDataInputAssociations().isEmpty());
    assertTrue(task.getDataOutputAssociations().isEmpty());
    assertTrue(task.getMapExceptions().isEmpty());
    assertTrue(task.getExecutionListeners().isEmpty());
    assertTrue(task.getIncomingFlows().isEmpty());
    assertTrue(task.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForShell(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  void testValidateFieldDeclarationsForShell3() {
    // Arrange
    SendTaskValidator sendTaskValidator = new SendTaskValidator();
    Process process = new Process();
    SendTask task = new SendTask();
    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();

    ValidationError validationError = new ValidationError();
    validationError.setActivityId("42");
    validationError.setActivityName("SHELL_TASK_NO_COMMAND");
    validationError.setDefaultDescription("SHELL_TASK_NO_COMMAND");
    validationError.setKey("SHELL_TASK_NO_COMMAND");
    validationError.setParams(new HashMap<>());
    validationError.setProblem("SHELL_TASK_NO_COMMAND");
    validationError.setProcessDefinitionId("42");
    validationError.setProcessDefinitionName("SHELL_TASK_NO_COMMAND");
    validationError.setValidatorSetName("SHELL_TASK_NO_COMMAND");
    validationError.setWarning(true);
    validationError.setXmlColumnNumber(10);
    validationError.setXmlLineNumber(2);

    ArrayList<ValidationError> errors = new ArrayList<>();
    errors.add(validationError);

    // Act
    sendTaskValidator.validateFieldDeclarationsForShell(process, task, fieldExtensions, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("SHELL_TASK_NO_COMMAND", getResult.getDefaultDescription());
    assertEquals("SHELL_TASK_NO_COMMAND", getResult.getKey());
    assertEquals("SHELL_TASK_NO_COMMAND", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(task.getBoundaryEvents().isEmpty());
    assertTrue(task.getDataInputAssociations().isEmpty());
    assertTrue(task.getDataOutputAssociations().isEmpty());
    assertTrue(task.getMapExceptions().isEmpty());
    assertTrue(task.getExecutionListeners().isEmpty());
    assertTrue(task.getIncomingFlows().isEmpty());
    assertTrue(task.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(task.getFieldExtensions().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertSame(validationError, errors.get(0));
  }

  /**
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForShell(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  void testValidateFieldDeclarationsForShell4() {
    // Arrange
    SendTaskValidator sendTaskValidator = new SendTaskValidator();
    Process process = new Process();
    SendTask task = new SendTask();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setStringValue("42");
    fieldExtension.setFieldName("wait");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sendTaskValidator.validateFieldDeclarationsForShell(process, task, fieldExtensions, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("SHELL_TASK_INVALID_PARAM", getResult.getDefaultDescription());
    assertEquals("SHELL_TASK_INVALID_PARAM", getResult.getKey());
    assertEquals("SHELL_TASK_INVALID_PARAM", getResult.getProblem());
    ValidationError getResult2 = errors.get(1);
    assertEquals("SHELL_TASK_NO_COMMAND", getResult2.getDefaultDescription());
    assertEquals("SHELL_TASK_NO_COMMAND", getResult2.getKey());
    assertEquals("SHELL_TASK_NO_COMMAND", getResult2.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult2.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult2.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult2.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult2.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertNull(getResult2.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertEquals(0, getResult2.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertFalse(getResult2.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(task.getBoundaryEvents().isEmpty());
    assertTrue(task.getDataInputAssociations().isEmpty());
    assertTrue(task.getDataOutputAssociations().isEmpty());
    assertTrue(task.getMapExceptions().isEmpty());
    assertTrue(task.getExecutionListeners().isEmpty());
    assertTrue(task.getIncomingFlows().isEmpty());
    assertTrue(task.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(task.getFieldExtensions().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForShell(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  void testValidateFieldDeclarationsForShell5() {
    // Arrange
    SendTaskValidator sendTaskValidator = new SendTaskValidator();
    Process process = new Process();
    SendTask task = new SendTask();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setStringValue(Boolean.TRUE.toString());
    fieldExtension.setFieldName("wait");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sendTaskValidator.validateFieldDeclarationsForShell(process, task, fieldExtensions, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("SHELL_TASK_NO_COMMAND", getResult.getDefaultDescription());
    assertEquals("SHELL_TASK_NO_COMMAND", getResult.getKey());
    assertEquals("SHELL_TASK_NO_COMMAND", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(task.getBoundaryEvents().isEmpty());
    assertTrue(task.getDataInputAssociations().isEmpty());
    assertTrue(task.getDataOutputAssociations().isEmpty());
    assertTrue(task.getMapExceptions().isEmpty());
    assertTrue(task.getExecutionListeners().isEmpty());
    assertTrue(task.getIncomingFlows().isEmpty());
    assertTrue(task.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(task.getFieldExtensions().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForShell(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  void testValidateFieldDeclarationsForShell6() {
    // Arrange
    SendTaskValidator sendTaskValidator = new SendTaskValidator();
    Process process = new Process();
    SendTask task = new SendTask();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setStringValue(Boolean.FALSE.toString());
    fieldExtension.setFieldName("wait");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sendTaskValidator.validateFieldDeclarationsForShell(process, task, fieldExtensions, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("SHELL_TASK_NO_COMMAND", getResult.getDefaultDescription());
    assertEquals("SHELL_TASK_NO_COMMAND", getResult.getKey());
    assertEquals("SHELL_TASK_NO_COMMAND", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(task.getBoundaryEvents().isEmpty());
    assertTrue(task.getDataInputAssociations().isEmpty());
    assertTrue(task.getDataOutputAssociations().isEmpty());
    assertTrue(task.getMapExceptions().isEmpty());
    assertTrue(task.getExecutionListeners().isEmpty());
    assertTrue(task.getIncomingFlows().isEmpty());
    assertTrue(task.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(task.getFieldExtensions().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForDmn(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  void testValidateFieldDeclarationsForDmn() {
    // Arrange
    SendTaskValidator sendTaskValidator = new SendTaskValidator();
    Process process = new Process();
    SendTask task = new SendTask();
    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sendTaskValidator.validateFieldDeclarationsForDmn(process, task, fieldExtensions, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("DMN_TASK_NO_KEY", getResult.getDefaultDescription());
    assertEquals("DMN_TASK_NO_KEY", getResult.getKey());
    assertEquals("DMN_TASK_NO_KEY", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(task.getBoundaryEvents().isEmpty());
    assertTrue(task.getDataInputAssociations().isEmpty());
    assertTrue(task.getDataOutputAssociations().isEmpty());
    assertTrue(task.getMapExceptions().isEmpty());
    assertTrue(task.getExecutionListeners().isEmpty());
    assertTrue(task.getIncomingFlows().isEmpty());
    assertTrue(task.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(task.getFieldExtensions().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForDmn(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  void testValidateFieldDeclarationsForDmn2() {
    // Arrange
    SendTaskValidator sendTaskValidator = new SendTaskValidator();
    Process process = new Process();
    SendTask task = new SendTask();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setFieldName("decisionTableReferenceKey");
    fieldExtension.setStringValue(null);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sendTaskValidator.validateFieldDeclarationsForDmn(process, task, fieldExtensions, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("DMN_TASK_NO_KEY", getResult.getDefaultDescription());
    assertEquals("DMN_TASK_NO_KEY", getResult.getKey());
    assertEquals("DMN_TASK_NO_KEY", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(task.getBoundaryEvents().isEmpty());
    assertTrue(task.getDataInputAssociations().isEmpty());
    assertTrue(task.getDataOutputAssociations().isEmpty());
    assertTrue(task.getMapExceptions().isEmpty());
    assertTrue(task.getExecutionListeners().isEmpty());
    assertTrue(task.getIncomingFlows().isEmpty());
    assertTrue(task.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(task.getFieldExtensions().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForDmn(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  void testValidateFieldDeclarationsForDmn3() {
    // Arrange
    SendTaskValidator sendTaskValidator = new SendTaskValidator();
    Process process = new Process();
    SendTask task = new SendTask();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setFieldName("decisionTableReferenceKey");
    fieldExtension.setStringValue("Field Extensions");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sendTaskValidator.validateFieldDeclarationsForDmn(process, task, fieldExtensions, errors);

    // Assert that nothing has changed
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(task.getBoundaryEvents().isEmpty());
    assertTrue(task.getDataInputAssociations().isEmpty());
    assertTrue(task.getDataOutputAssociations().isEmpty());
    assertTrue(task.getMapExceptions().isEmpty());
    assertTrue(task.getExecutionListeners().isEmpty());
    assertTrue(task.getIncomingFlows().isEmpty());
    assertTrue(task.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForDmn(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  void testValidateFieldDeclarationsForDmn4() {
    // Arrange
    SendTaskValidator sendTaskValidator = new SendTaskValidator();
    Process process = new Process();
    SendTask task = new SendTask();
    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();

    ValidationError validationError = new ValidationError();
    validationError.setActivityId("42");
    validationError.setActivityName("DMN_TASK_NO_KEY");
    validationError.setDefaultDescription("DMN_TASK_NO_KEY");
    validationError.setKey("DMN_TASK_NO_KEY");
    validationError.setParams(new HashMap<>());
    validationError.setProblem("DMN_TASK_NO_KEY");
    validationError.setProcessDefinitionId("42");
    validationError.setProcessDefinitionName("DMN_TASK_NO_KEY");
    validationError.setValidatorSetName("DMN_TASK_NO_KEY");
    validationError.setWarning(true);
    validationError.setXmlColumnNumber(10);
    validationError.setXmlLineNumber(2);

    ArrayList<ValidationError> errors = new ArrayList<>();
    errors.add(validationError);

    // Act
    sendTaskValidator.validateFieldDeclarationsForDmn(process, task, fieldExtensions, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("DMN_TASK_NO_KEY", getResult.getDefaultDescription());
    assertEquals("DMN_TASK_NO_KEY", getResult.getKey());
    assertEquals("DMN_TASK_NO_KEY", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(task.getBoundaryEvents().isEmpty());
    assertTrue(task.getDataInputAssociations().isEmpty());
    assertTrue(task.getDataOutputAssociations().isEmpty());
    assertTrue(task.getMapExceptions().isEmpty());
    assertTrue(task.getExecutionListeners().isEmpty());
    assertTrue(task.getIncomingFlows().isEmpty());
    assertTrue(task.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(task.getFieldExtensions().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertSame(validationError, errors.get(0));
  }

  /**
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForDmn(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  void testValidateFieldDeclarationsForDmn5() {
    // Arrange
    SendTaskValidator sendTaskValidator = new SendTaskValidator();
    Process process = new Process();
    SendTask task = new SendTask();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setFieldName("DMN_TASK_NO_KEY");
    fieldExtension.setStringValue(null);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sendTaskValidator.validateFieldDeclarationsForDmn(process, task, fieldExtensions, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("DMN_TASK_NO_KEY", getResult.getDefaultDescription());
    assertEquals("DMN_TASK_NO_KEY", getResult.getKey());
    assertEquals("DMN_TASK_NO_KEY", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(task.getBoundaryEvents().isEmpty());
    assertTrue(task.getDataInputAssociations().isEmpty());
    assertTrue(task.getDataOutputAssociations().isEmpty());
    assertTrue(task.getMapExceptions().isEmpty());
    assertTrue(task.getExecutionListeners().isEmpty());
    assertTrue(task.getIncomingFlows().isEmpty());
    assertTrue(task.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(task.getFieldExtensions().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForDmn(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  void testValidateFieldDeclarationsForDmn6() {
    // Arrange
    SendTaskValidator sendTaskValidator = new SendTaskValidator();
    Process process = new Process();
    SendTask task = new SendTask();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setFieldName("decisionTableReferenceKey");
    fieldExtension.setStringValue("");

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(fieldExtension);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sendTaskValidator.validateFieldDeclarationsForDmn(process, task, fieldExtensions, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("DMN_TASK_NO_KEY", getResult.getDefaultDescription());
    assertEquals("DMN_TASK_NO_KEY", getResult.getKey());
    assertEquals("DMN_TASK_NO_KEY", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(task.getBoundaryEvents().isEmpty());
    assertTrue(task.getDataInputAssociations().isEmpty());
    assertTrue(task.getDataOutputAssociations().isEmpty());
    assertTrue(task.getMapExceptions().isEmpty());
    assertTrue(task.getExecutionListeners().isEmpty());
    assertTrue(task.getIncomingFlows().isEmpty());
    assertTrue(task.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(task.getFieldExtensions().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }
}
