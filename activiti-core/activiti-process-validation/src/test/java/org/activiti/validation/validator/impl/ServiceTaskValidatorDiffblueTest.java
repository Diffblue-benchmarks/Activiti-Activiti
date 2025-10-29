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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.ServiceTask;
import org.activiti.bpmn.model.Signal;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ServiceTaskValidatorDiffblueTest {
  /**
   * Method under test:
   * {@link ServiceTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
  }

  /**
   * Method under test:
   * {@link ServiceTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation2() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Method under test:
   * {@link ServiceTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation3() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<ServiceTask>>any())).thenReturn(new ArrayList<>());
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(process).findFlowElementsOfType(isA(Class.class));
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
  }

  /**
   * Method under test:
   * {@link ServiceTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation4() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<ServiceTask> serviceTaskList = new ArrayList<>();
    serviceTaskList.add(new ServiceTask());
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<ServiceTask>>any())).thenReturn(serviceTaskList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.executeValidation(bpmnModel, process, errors);

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
    assertEquals("SERVICE_TASK_MISSING_IMPLEMENTATION", getResult.getDefaultDescription());
    assertEquals("SERVICE_TASK_MISSING_IMPLEMENTATION", getResult.getKey());
    assertEquals("SERVICE_TASK_MISSING_IMPLEMENTATION", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ServiceTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation5() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<ServiceTask> serviceTaskList = new ArrayList<>();
    serviceTaskList.add(new ServiceTask());
    serviceTaskList.add(new ServiceTask());
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<ServiceTask>>any())).thenReturn(serviceTaskList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process, atLeast(1)).getId();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getName();
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getProcessDefinitionId());
    ValidationError getResult2 = errors.get(1);
    assertEquals("42", getResult2.getProcessDefinitionId());
    assertEquals("Name", getResult.getProcessDefinitionName());
    assertEquals("Name", getResult2.getProcessDefinitionName());
    assertEquals("SERVICE_TASK_MISSING_IMPLEMENTATION", getResult.getDefaultDescription());
    assertEquals("SERVICE_TASK_MISSING_IMPLEMENTATION", getResult2.getDefaultDescription());
    assertEquals("SERVICE_TASK_MISSING_IMPLEMENTATION", getResult.getKey());
    assertEquals("SERVICE_TASK_MISSING_IMPLEMENTATION", getResult2.getKey());
    assertEquals("SERVICE_TASK_MISSING_IMPLEMENTATION", getResult.getProblem());
    assertEquals("SERVICE_TASK_MISSING_IMPLEMENTATION", getResult2.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult2.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult2.getActivityName());
    assertNull(getResult.getValidatorSetName());
    assertNull(getResult2.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertEquals(0, getResult2.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertFalse(getResult2.isWarning());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ServiceTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation6() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    BpmnModel bpmnModel = new BpmnModel();
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getXmlColumnNumber()).thenReturn(10);
    when(serviceTask.getXmlRowNumber()).thenReturn(10);
    when(serviceTask.getId()).thenReturn("42");
    when(serviceTask.getName()).thenReturn("Name");
    when(serviceTask.getImplementationType()).thenReturn("Implementation Type");
    when(serviceTask.getResultVariableName()).thenReturn("Result Variable Name");
    when(serviceTask.getType()).thenReturn("Type");

    ArrayList<ServiceTask> serviceTaskList = new ArrayList<>();
    serviceTaskList.add(serviceTask);
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<ServiceTask>>any())).thenReturn(serviceTaskList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process).getId();
    verify(serviceTask).getId();
    verify(serviceTask).getXmlColumnNumber();
    verify(serviceTask).getXmlRowNumber();
    verify(serviceTask).getName();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getName();
    verify(serviceTask, atLeast(1)).getImplementationType();
    verify(serviceTask).getResultVariableName();
    verify(serviceTask, atLeast(1)).getType();
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("Name", getResult.getActivityName());
    assertEquals("Name", getResult.getProcessDefinitionName());
    assertEquals("SERVICE_TASK_INVALID_TYPE", getResult.getDefaultDescription());
    assertEquals("SERVICE_TASK_INVALID_TYPE", getResult.getKey());
    assertEquals("SERVICE_TASK_INVALID_TYPE", getResult.getProblem());
    assertNull(getResult.getValidatorSetName());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ServiceTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation7() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    BpmnModel bpmnModel = new BpmnModel();
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getXmlColumnNumber()).thenReturn(10);
    when(serviceTask.getXmlRowNumber()).thenReturn(10);
    when(serviceTask.getId()).thenReturn("42");
    when(serviceTask.getName()).thenReturn("Name");
    when(serviceTask.getImplementationType()).thenReturn("class");
    when(serviceTask.getResultVariableName()).thenReturn("Result Variable Name");
    when(serviceTask.getType()).thenReturn("Type");

    ArrayList<ServiceTask> serviceTaskList = new ArrayList<>();
    serviceTaskList.add(serviceTask);
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<ServiceTask>>any())).thenReturn(serviceTaskList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process, atLeast(1)).getId();
    verify(serviceTask, atLeast(1)).getId();
    verify(serviceTask, atLeast(1)).getXmlColumnNumber();
    verify(serviceTask, atLeast(1)).getXmlRowNumber();
    verify(serviceTask, atLeast(1)).getName();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getName();
    verify(serviceTask, atLeast(1)).getImplementationType();
    verify(serviceTask).getResultVariableName();
    verify(serviceTask, atLeast(1)).getType();
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getActivityId());
    ValidationError getResult2 = errors.get(1);
    assertEquals("42", getResult2.getActivityId());
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("42", getResult2.getProcessDefinitionId());
    assertEquals("Name", getResult.getActivityName());
    assertEquals("Name", getResult2.getActivityName());
    assertEquals("Name", getResult.getProcessDefinitionName());
    assertEquals("Name", getResult2.getProcessDefinitionName());
    assertEquals("SERVICE_TASK_INVALID_TYPE", getResult.getDefaultDescription());
    assertEquals("SERVICE_TASK_INVALID_TYPE", getResult.getKey());
    assertEquals("SERVICE_TASK_INVALID_TYPE", getResult.getProblem());
    assertEquals("SERVICE_TASK_RESULT_VAR_NAME_WITH_DELEGATE", getResult2.getDefaultDescription());
    assertEquals("SERVICE_TASK_RESULT_VAR_NAME_WITH_DELEGATE", getResult2.getKey());
    assertEquals("SERVICE_TASK_RESULT_VAR_NAME_WITH_DELEGATE", getResult2.getProblem());
    assertNull(getResult.getValidatorSetName());
    assertNull(getResult2.getValidatorSetName());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult2.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
    assertEquals(10, getResult2.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertFalse(getResult2.isWarning());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ServiceTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation8() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    BpmnModel bpmnModel = new BpmnModel();
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getXmlColumnNumber()).thenReturn(10);
    when(serviceTask.getXmlRowNumber()).thenReturn(10);
    when(serviceTask.getId()).thenReturn("42");
    when(serviceTask.getName()).thenReturn("Name");
    when(serviceTask.getImplementationType()).thenReturn("delegateExpression");
    when(serviceTask.getResultVariableName()).thenReturn("Result Variable Name");
    when(serviceTask.getType()).thenReturn("Type");

    ArrayList<ServiceTask> serviceTaskList = new ArrayList<>();
    serviceTaskList.add(serviceTask);
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<ServiceTask>>any())).thenReturn(serviceTaskList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process, atLeast(1)).getId();
    verify(serviceTask, atLeast(1)).getId();
    verify(serviceTask, atLeast(1)).getXmlColumnNumber();
    verify(serviceTask, atLeast(1)).getXmlRowNumber();
    verify(serviceTask, atLeast(1)).getName();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getName();
    verify(serviceTask, atLeast(1)).getImplementationType();
    verify(serviceTask).getResultVariableName();
    verify(serviceTask, atLeast(1)).getType();
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getActivityId());
    ValidationError getResult2 = errors.get(1);
    assertEquals("42", getResult2.getActivityId());
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("42", getResult2.getProcessDefinitionId());
    assertEquals("Name", getResult.getActivityName());
    assertEquals("Name", getResult2.getActivityName());
    assertEquals("Name", getResult.getProcessDefinitionName());
    assertEquals("Name", getResult2.getProcessDefinitionName());
    assertEquals("SERVICE_TASK_INVALID_TYPE", getResult.getDefaultDescription());
    assertEquals("SERVICE_TASK_INVALID_TYPE", getResult.getKey());
    assertEquals("SERVICE_TASK_INVALID_TYPE", getResult.getProblem());
    assertEquals("SERVICE_TASK_RESULT_VAR_NAME_WITH_DELEGATE", getResult2.getDefaultDescription());
    assertEquals("SERVICE_TASK_RESULT_VAR_NAME_WITH_DELEGATE", getResult2.getKey());
    assertEquals("SERVICE_TASK_RESULT_VAR_NAME_WITH_DELEGATE", getResult2.getProblem());
    assertNull(getResult.getValidatorSetName());
    assertNull(getResult2.getValidatorSetName());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult2.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
    assertEquals(10, getResult2.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertFalse(getResult2.isWarning());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ServiceTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation9() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    BpmnModel bpmnModel = new BpmnModel();
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getXmlColumnNumber()).thenReturn(10);
    when(serviceTask.getXmlRowNumber()).thenReturn(10);
    when(serviceTask.getId()).thenReturn("42");
    when(serviceTask.getName()).thenReturn("Name");
    when(serviceTask.getImplementationType()).thenReturn("Implementation Type");
    when(serviceTask.getResultVariableName()).thenReturn("");
    when(serviceTask.getType()).thenReturn("Type");

    ArrayList<ServiceTask> serviceTaskList = new ArrayList<>();
    serviceTaskList.add(serviceTask);
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<ServiceTask>>any())).thenReturn(serviceTaskList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process).getId();
    verify(serviceTask).getId();
    verify(serviceTask).getXmlColumnNumber();
    verify(serviceTask).getXmlRowNumber();
    verify(serviceTask).getName();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getName();
    verify(serviceTask, atLeast(1)).getImplementationType();
    verify(serviceTask).getResultVariableName();
    verify(serviceTask, atLeast(1)).getType();
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("Name", getResult.getActivityName());
    assertEquals("Name", getResult.getProcessDefinitionName());
    assertEquals("SERVICE_TASK_INVALID_TYPE", getResult.getDefaultDescription());
    assertEquals("SERVICE_TASK_INVALID_TYPE", getResult.getKey());
    assertEquals("SERVICE_TASK_INVALID_TYPE", getResult.getProblem());
    assertNull(getResult.getValidatorSetName());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ServiceTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation10() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    BpmnModel bpmnModel = new BpmnModel();
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getFieldExtensions()).thenReturn(new ArrayList<>());
    when(serviceTask.getXmlColumnNumber()).thenReturn(10);
    when(serviceTask.getXmlRowNumber()).thenReturn(10);
    when(serviceTask.getId()).thenReturn("42");
    when(serviceTask.getName()).thenReturn("Name");
    when(serviceTask.getImplementationType()).thenReturn("Implementation Type");
    when(serviceTask.getResultVariableName()).thenReturn("Result Variable Name");
    when(serviceTask.getType()).thenReturn("mail");

    ArrayList<ServiceTask> serviceTaskList = new ArrayList<>();
    serviceTaskList.add(serviceTask);
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<ServiceTask>>any())).thenReturn(serviceTaskList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process, atLeast(1)).getId();
    verify(serviceTask, atLeast(1)).getId();
    verify(serviceTask, atLeast(1)).getXmlColumnNumber();
    verify(serviceTask, atLeast(1)).getXmlRowNumber();
    verify(serviceTask, atLeast(1)).getName();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getName();
    verify(serviceTask, atLeast(1)).getImplementationType();
    verify(serviceTask).getResultVariableName();
    verify(serviceTask, atLeast(1)).getType();
    verify(serviceTask).getFieldExtensions();
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getActivityId());
    ValidationError getResult2 = errors.get(1);
    assertEquals("42", getResult2.getActivityId());
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("42", getResult2.getProcessDefinitionId());
    assertEquals("MAIL_TASK_NO_CONTENT", getResult2.getDefaultDescription());
    assertEquals("MAIL_TASK_NO_CONTENT", getResult2.getKey());
    assertEquals("MAIL_TASK_NO_CONTENT", getResult2.getProblem());
    assertEquals("MAIL_TASK_NO_RECIPIENT", getResult.getDefaultDescription());
    assertEquals("MAIL_TASK_NO_RECIPIENT", getResult.getKey());
    assertEquals("MAIL_TASK_NO_RECIPIENT", getResult.getProblem());
    assertEquals("Name", getResult.getActivityName());
    assertEquals("Name", getResult2.getActivityName());
    assertEquals("Name", getResult.getProcessDefinitionName());
    assertEquals("Name", getResult2.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertNull(getResult2.getValidatorSetName());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult2.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
    assertEquals(10, getResult2.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertFalse(getResult2.isWarning());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ServiceTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation11() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    BpmnModel bpmnModel = new BpmnModel();
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getImplementation()).thenReturn("Implementation");
    when(serviceTask.getImplementationType()).thenReturn("Implementation Type");
    when(serviceTask.getResultVariableName()).thenReturn("Result Variable Name");
    when(serviceTask.getType()).thenReturn("");

    ArrayList<ServiceTask> serviceTaskList = new ArrayList<>();
    serviceTaskList.add(serviceTask);
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<ServiceTask>>any())).thenReturn(serviceTaskList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(serviceTask).getImplementation();
    verify(serviceTask, atLeast(1)).getImplementationType();
    verify(serviceTask).getResultVariableName();
    verify(serviceTask, atLeast(1)).getType();
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
  }

  /**
   * Method under test:
   * {@link ServiceTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation12() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    BpmnModel bpmnModel = new BpmnModel();
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getXmlColumnNumber()).thenReturn(10);
    when(serviceTask.getXmlRowNumber()).thenReturn(10);
    when(serviceTask.getId()).thenReturn("42");
    when(serviceTask.getName()).thenReturn("Name");
    when(serviceTask.getImplementation()).thenReturn("");
    when(serviceTask.getImplementationType()).thenReturn("Implementation Type");
    when(serviceTask.getResultVariableName()).thenReturn("Result Variable Name");
    when(serviceTask.getType()).thenReturn("");

    ArrayList<ServiceTask> serviceTaskList = new ArrayList<>();
    serviceTaskList.add(serviceTask);
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<ServiceTask>>any())).thenReturn(serviceTaskList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process).getId();
    verify(serviceTask).getId();
    verify(serviceTask).getXmlColumnNumber();
    verify(serviceTask).getXmlRowNumber();
    verify(serviceTask).getName();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getName();
    verify(serviceTask).getImplementation();
    verify(serviceTask, atLeast(1)).getImplementationType();
    verify(serviceTask).getResultVariableName();
    verify(serviceTask, atLeast(1)).getType();
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("Name", getResult.getActivityName());
    assertEquals("Name", getResult.getProcessDefinitionName());
    assertEquals("SERVICE_TASK_MISSING_IMPLEMENTATION", getResult.getDefaultDescription());
    assertEquals("SERVICE_TASK_MISSING_IMPLEMENTATION", getResult.getKey());
    assertEquals("SERVICE_TASK_MISSING_IMPLEMENTATION", getResult.getProblem());
    assertNull(getResult.getValidatorSetName());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ServiceTaskValidator#verifyImplementation(Process, ServiceTask, List)}
   */
  @Test
  void testVerifyImplementation() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    Process process = new Process();
    ServiceTask serviceTask = new ServiceTask();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.verifyImplementation(process, serviceTask, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("SERVICE_TASK_MISSING_IMPLEMENTATION", getResult.getDefaultDescription());
    assertEquals("SERVICE_TASK_MISSING_IMPLEMENTATION", getResult.getKey());
    assertEquals("SERVICE_TASK_MISSING_IMPLEMENTATION", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(serviceTask.getBoundaryEvents().isEmpty());
    assertTrue(serviceTask.getDataInputAssociations().isEmpty());
    assertTrue(serviceTask.getDataOutputAssociations().isEmpty());
    assertTrue(serviceTask.getMapExceptions().isEmpty());
    assertTrue(serviceTask.getExecutionListeners().isEmpty());
    assertTrue(serviceTask.getIncomingFlows().isEmpty());
    assertTrue(serviceTask.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(serviceTask.getCustomProperties().isEmpty());
    assertTrue(serviceTask.getFieldExtensions().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ServiceTaskValidator#verifyImplementation(Process, ServiceTask, List)}
   */
  @Test
  void testVerifyImplementation2() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    Process process = new Process();

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setType(null);
    serviceTask.setImplementation("Service Task");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.verifyImplementation(process, serviceTask, errors);

    // Assert that nothing has changed
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(serviceTask.getBoundaryEvents().isEmpty());
    assertTrue(serviceTask.getDataInputAssociations().isEmpty());
    assertTrue(serviceTask.getDataOutputAssociations().isEmpty());
    assertTrue(serviceTask.getMapExceptions().isEmpty());
    assertTrue(serviceTask.getExecutionListeners().isEmpty());
    assertTrue(serviceTask.getIncomingFlows().isEmpty());
    assertTrue(serviceTask.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(serviceTask.getCustomProperties().isEmpty());
    assertTrue(serviceTask.getFieldExtensions().isEmpty());
  }

  /**
   * Method under test:
   * {@link ServiceTaskValidator#verifyImplementation(Process, ServiceTask, List)}
   */
  @Test
  void testVerifyImplementation3() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    Process process = new Process();

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setType("Service Task");
    serviceTask.setImplementation(null);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.verifyImplementation(process, serviceTask, errors);

    // Assert that nothing has changed
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(serviceTask.getBoundaryEvents().isEmpty());
    assertTrue(serviceTask.getDataInputAssociations().isEmpty());
    assertTrue(serviceTask.getDataOutputAssociations().isEmpty());
    assertTrue(serviceTask.getMapExceptions().isEmpty());
    assertTrue(serviceTask.getExecutionListeners().isEmpty());
    assertTrue(serviceTask.getIncomingFlows().isEmpty());
    assertTrue(serviceTask.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(serviceTask.getCustomProperties().isEmpty());
    assertTrue(serviceTask.getFieldExtensions().isEmpty());
  }

  /**
   * Method under test:
   * {@link ServiceTaskValidator#verifyImplementation(Process, ServiceTask, List)}
   */
  @Test
  void testVerifyImplementation4() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    ServiceTask serviceTask = new ServiceTask();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.verifyImplementation(null, serviceTask, errors);

    // Assert
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("SERVICE_TASK_MISSING_IMPLEMENTATION", getResult.getDefaultDescription());
    assertEquals("SERVICE_TASK_MISSING_IMPLEMENTATION", getResult.getKey());
    assertEquals("SERVICE_TASK_MISSING_IMPLEMENTATION", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(serviceTask.getBoundaryEvents().isEmpty());
    assertTrue(serviceTask.getDataInputAssociations().isEmpty());
    assertTrue(serviceTask.getDataOutputAssociations().isEmpty());
    assertTrue(serviceTask.getMapExceptions().isEmpty());
    assertTrue(serviceTask.getExecutionListeners().isEmpty());
    assertTrue(serviceTask.getIncomingFlows().isEmpty());
    assertTrue(serviceTask.getOutgoingFlows().isEmpty());
    assertTrue(serviceTask.getCustomProperties().isEmpty());
    assertTrue(serviceTask.getFieldExtensions().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ServiceTaskValidator#verifyImplementation(Process, ServiceTask, List)}
   */
  @Test
  void testVerifyImplementation5() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    Process process = new Process();
    ServiceTask serviceTask = new ServiceTask();

    ValidationError validationError = new ValidationError();
    validationError.setActivityId("42");
    validationError.setActivityName("SERVICE_TASK_MISSING_IMPLEMENTATION");
    validationError.setDefaultDescription("SERVICE_TASK_MISSING_IMPLEMENTATION");
    validationError.setKey("SERVICE_TASK_MISSING_IMPLEMENTATION");
    validationError.setParams(new HashMap<>());
    validationError.setProblem("SERVICE_TASK_MISSING_IMPLEMENTATION");
    validationError.setProcessDefinitionId("42");
    validationError.setProcessDefinitionName("SERVICE_TASK_MISSING_IMPLEMENTATION");
    validationError.setValidatorSetName("SERVICE_TASK_MISSING_IMPLEMENTATION");
    validationError.setWarning(true);
    validationError.setXmlColumnNumber(10);
    validationError.setXmlLineNumber(2);

    ArrayList<ValidationError> errors = new ArrayList<>();
    errors.add(validationError);

    // Act
    serviceTaskValidator.verifyImplementation(process, serviceTask, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("SERVICE_TASK_MISSING_IMPLEMENTATION", getResult.getDefaultDescription());
    assertEquals("SERVICE_TASK_MISSING_IMPLEMENTATION", getResult.getKey());
    assertEquals("SERVICE_TASK_MISSING_IMPLEMENTATION", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(serviceTask.getBoundaryEvents().isEmpty());
    assertTrue(serviceTask.getDataInputAssociations().isEmpty());
    assertTrue(serviceTask.getDataOutputAssociations().isEmpty());
    assertTrue(serviceTask.getMapExceptions().isEmpty());
    assertTrue(serviceTask.getExecutionListeners().isEmpty());
    assertTrue(serviceTask.getIncomingFlows().isEmpty());
    assertTrue(serviceTask.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(serviceTask.getCustomProperties().isEmpty());
    assertTrue(serviceTask.getFieldExtensions().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertSame(validationError, errors.get(0));
  }

  /**
   * Method under test:
   * {@link ServiceTaskValidator#verifyImplementation(Process, ServiceTask, List)}
   */
  @Test
  void testVerifyImplementation6() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    Process process = new Process();

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setType("");
    serviceTask.setImplementation("Service Task");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.verifyImplementation(process, serviceTask, errors);

    // Assert that nothing has changed
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(serviceTask.getBoundaryEvents().isEmpty());
    assertTrue(serviceTask.getDataInputAssociations().isEmpty());
    assertTrue(serviceTask.getDataOutputAssociations().isEmpty());
    assertTrue(serviceTask.getMapExceptions().isEmpty());
    assertTrue(serviceTask.getExecutionListeners().isEmpty());
    assertTrue(serviceTask.getIncomingFlows().isEmpty());
    assertTrue(serviceTask.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(serviceTask.getCustomProperties().isEmpty());
    assertTrue(serviceTask.getFieldExtensions().isEmpty());
  }

  /**
   * Method under test:
   * {@link ServiceTaskValidator#verifyType(Process, ServiceTask, List)}
   */
  @Test
  void testVerifyType() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    Process process = new Process();
    ServiceTask serviceTask = new ServiceTask();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.verifyType(process, serviceTask, errors);

    // Assert that nothing has changed
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(serviceTask.getBoundaryEvents().isEmpty());
    assertTrue(serviceTask.getDataInputAssociations().isEmpty());
    assertTrue(serviceTask.getDataOutputAssociations().isEmpty());
    assertTrue(serviceTask.getMapExceptions().isEmpty());
    assertTrue(serviceTask.getExecutionListeners().isEmpty());
    assertTrue(serviceTask.getIncomingFlows().isEmpty());
    assertTrue(serviceTask.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(serviceTask.getCustomProperties().isEmpty());
    assertTrue(serviceTask.getFieldExtensions().isEmpty());
  }

  /**
   * Method under test:
   * {@link ServiceTaskValidator#verifyType(Process, ServiceTask, List)}
   */
  @Test
  void testVerifyType2() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    Process process = new Process();

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setType(ServiceTask.MAIL_TASK);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.verifyType(process, serviceTask, errors);

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
    assertTrue(serviceTask.getBoundaryEvents().isEmpty());
    assertTrue(serviceTask.getDataInputAssociations().isEmpty());
    assertTrue(serviceTask.getDataOutputAssociations().isEmpty());
    assertTrue(serviceTask.getMapExceptions().isEmpty());
    assertTrue(serviceTask.getExecutionListeners().isEmpty());
    assertTrue(serviceTask.getIncomingFlows().isEmpty());
    assertTrue(serviceTask.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(serviceTask.getCustomProperties().isEmpty());
    assertTrue(serviceTask.getFieldExtensions().isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ServiceTaskValidator#verifyType(Process, ServiceTask, List)}
   */
  @Test
  void testVerifyType3() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    Process process = new Process();

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setType("mule");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.verifyType(process, serviceTask, errors);

    // Assert that nothing has changed
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(serviceTask.getBoundaryEvents().isEmpty());
    assertTrue(serviceTask.getDataInputAssociations().isEmpty());
    assertTrue(serviceTask.getDataOutputAssociations().isEmpty());
    assertTrue(serviceTask.getMapExceptions().isEmpty());
    assertTrue(serviceTask.getExecutionListeners().isEmpty());
    assertTrue(serviceTask.getIncomingFlows().isEmpty());
    assertTrue(serviceTask.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(serviceTask.getCustomProperties().isEmpty());
    assertTrue(serviceTask.getFieldExtensions().isEmpty());
  }

  /**
   * Method under test:
   * {@link ServiceTaskValidator#verifyType(Process, ServiceTask, List)}
   */
  @Test
  void testVerifyType4() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    Process process = new Process();

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setType("camel");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.verifyType(process, serviceTask, errors);

    // Assert that nothing has changed
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(serviceTask.getBoundaryEvents().isEmpty());
    assertTrue(serviceTask.getDataInputAssociations().isEmpty());
    assertTrue(serviceTask.getDataOutputAssociations().isEmpty());
    assertTrue(serviceTask.getMapExceptions().isEmpty());
    assertTrue(serviceTask.getExecutionListeners().isEmpty());
    assertTrue(serviceTask.getIncomingFlows().isEmpty());
    assertTrue(serviceTask.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(serviceTask.getCustomProperties().isEmpty());
    assertTrue(serviceTask.getFieldExtensions().isEmpty());
  }

  /**
   * Method under test:
   * {@link ServiceTaskValidator#verifyType(Process, ServiceTask, List)}
   */
  @Test
  void testVerifyType5() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    Process process = new Process();

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setType("shell");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.verifyType(process, serviceTask, errors);

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
    assertTrue(serviceTask.getBoundaryEvents().isEmpty());
    assertTrue(serviceTask.getDataInputAssociations().isEmpty());
    assertTrue(serviceTask.getDataOutputAssociations().isEmpty());
    assertTrue(serviceTask.getMapExceptions().isEmpty());
    assertTrue(serviceTask.getExecutionListeners().isEmpty());
    assertTrue(serviceTask.getIncomingFlows().isEmpty());
    assertTrue(serviceTask.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(serviceTask.getCustomProperties().isEmpty());
    assertTrue(serviceTask.getFieldExtensions().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ServiceTaskValidator#verifyType(Process, ServiceTask, List)}
   */
  @Test
  void testVerifyType6() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    Process process = new Process();

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setType(ServiceTask.DMN_TASK);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.verifyType(process, serviceTask, errors);

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
    assertTrue(serviceTask.getBoundaryEvents().isEmpty());
    assertTrue(serviceTask.getDataInputAssociations().isEmpty());
    assertTrue(serviceTask.getDataOutputAssociations().isEmpty());
    assertTrue(serviceTask.getMapExceptions().isEmpty());
    assertTrue(serviceTask.getExecutionListeners().isEmpty());
    assertTrue(serviceTask.getIncomingFlows().isEmpty());
    assertTrue(serviceTask.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(serviceTask.getCustomProperties().isEmpty());
    assertTrue(serviceTask.getFieldExtensions().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ServiceTaskValidator#verifyType(Process, ServiceTask, List)}
   */
  @Test
  void testVerifyType7() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    Process process = new Process();

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setType("Service Task");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.verifyType(process, serviceTask, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("SERVICE_TASK_INVALID_TYPE", getResult.getDefaultDescription());
    assertEquals("SERVICE_TASK_INVALID_TYPE", getResult.getKey());
    assertEquals("SERVICE_TASK_INVALID_TYPE", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(serviceTask.getBoundaryEvents().isEmpty());
    assertTrue(serviceTask.getDataInputAssociations().isEmpty());
    assertTrue(serviceTask.getDataOutputAssociations().isEmpty());
    assertTrue(serviceTask.getMapExceptions().isEmpty());
    assertTrue(serviceTask.getExecutionListeners().isEmpty());
    assertTrue(serviceTask.getIncomingFlows().isEmpty());
    assertTrue(serviceTask.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(serviceTask.getCustomProperties().isEmpty());
    assertTrue(serviceTask.getFieldExtensions().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ServiceTaskValidator#verifyType(Process, ServiceTask, List)}
   */
  @Test
  void testVerifyType8() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    Process process = new Process();

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setType("");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.verifyType(process, serviceTask, errors);

    // Assert that nothing has changed
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(serviceTask.getBoundaryEvents().isEmpty());
    assertTrue(serviceTask.getDataInputAssociations().isEmpty());
    assertTrue(serviceTask.getDataOutputAssociations().isEmpty());
    assertTrue(serviceTask.getMapExceptions().isEmpty());
    assertTrue(serviceTask.getExecutionListeners().isEmpty());
    assertTrue(serviceTask.getIncomingFlows().isEmpty());
    assertTrue(serviceTask.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(serviceTask.getCustomProperties().isEmpty());
    assertTrue(serviceTask.getFieldExtensions().isEmpty());
  }

  /**
   * Method under test:
   * {@link ServiceTaskValidator#verifyResultVariableName(Process, ServiceTask, List)}
   */
  @Test
  void testVerifyResultVariableName() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    Process process = new Process();
    ServiceTask serviceTask = new ServiceTask();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.verifyResultVariableName(process, serviceTask, errors);

    // Assert that nothing has changed
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
  }

  /**
   * Method under test:
   * {@link ServiceTaskValidator#verifyResultVariableName(Process, ServiceTask, List)}
   */
  @Test
  void testVerifyResultVariableName2() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    Process process = mock(Process.class);
    ServiceTask serviceTask = new ServiceTask();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.verifyResultVariableName(process, serviceTask, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Method under test:
   * {@link ServiceTaskValidator#verifyResultVariableName(Process, ServiceTask, List)}
   */
  @Test
  void testVerifyResultVariableName3() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    Process process = new Process();
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getImplementationType()).thenReturn("Implementation Type");
    when(serviceTask.getResultVariableName()).thenReturn("Result Variable Name");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.verifyResultVariableName(process, serviceTask, errors);

    // Assert that nothing has changed
    verify(serviceTask, atLeast(1)).getImplementationType();
    verify(serviceTask).getResultVariableName();
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
  }

  /**
   * Method under test:
   * {@link ServiceTaskValidator#verifyResultVariableName(Process, ServiceTask, List)}
   */
  @Test
  void testVerifyResultVariableName4() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    Process process = new Process();
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getXmlColumnNumber()).thenReturn(10);
    when(serviceTask.getXmlRowNumber()).thenReturn(10);
    when(serviceTask.getId()).thenReturn("42");
    when(serviceTask.getName()).thenReturn("Name");
    when(serviceTask.getImplementationType()).thenReturn("class");
    when(serviceTask.getResultVariableName()).thenReturn("Result Variable Name");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.verifyResultVariableName(process, serviceTask, errors);

    // Assert
    verify(serviceTask).getId();
    verify(serviceTask).getXmlColumnNumber();
    verify(serviceTask).getXmlRowNumber();
    verify(serviceTask).getName();
    verify(serviceTask).getImplementationType();
    verify(serviceTask).getResultVariableName();
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("Name", getResult.getActivityName());
    assertEquals("SERVICE_TASK_RESULT_VAR_NAME_WITH_DELEGATE", getResult.getDefaultDescription());
    assertEquals("SERVICE_TASK_RESULT_VAR_NAME_WITH_DELEGATE", getResult.getKey());
    assertEquals("SERVICE_TASK_RESULT_VAR_NAME_WITH_DELEGATE", getResult.getProblem());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ServiceTaskValidator#verifyResultVariableName(Process, ServiceTask, List)}
   */
  @Test
  void testVerifyResultVariableName5() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getXmlColumnNumber()).thenReturn(10);
    when(serviceTask.getXmlRowNumber()).thenReturn(10);
    when(serviceTask.getId()).thenReturn("42");
    when(serviceTask.getName()).thenReturn("Name");
    when(serviceTask.getImplementationType()).thenReturn("class");
    when(serviceTask.getResultVariableName()).thenReturn("Result Variable Name");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.verifyResultVariableName(null, serviceTask, errors);

    // Assert
    verify(serviceTask).getId();
    verify(serviceTask).getXmlColumnNumber();
    verify(serviceTask).getXmlRowNumber();
    verify(serviceTask).getName();
    verify(serviceTask).getImplementationType();
    verify(serviceTask).getResultVariableName();
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("Name", getResult.getActivityName());
    assertEquals("SERVICE_TASK_RESULT_VAR_NAME_WITH_DELEGATE", getResult.getDefaultDescription());
    assertEquals("SERVICE_TASK_RESULT_VAR_NAME_WITH_DELEGATE", getResult.getKey());
    assertEquals("SERVICE_TASK_RESULT_VAR_NAME_WITH_DELEGATE", getResult.getProblem());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ServiceTaskValidator#verifyResultVariableName(Process, ServiceTask, List)}
   */
  @Test
  void testVerifyResultVariableName6() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    Process process = new Process();
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getResultVariableName()).thenReturn("");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.verifyResultVariableName(process, serviceTask, errors);

    // Assert that nothing has changed
    verify(serviceTask).getResultVariableName();
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
  }

  /**
   * Method under test:
   * {@link ServiceTaskValidator#verifyResultVariableName(Process, ServiceTask, List)}
   */
  @Test
  void testVerifyResultVariableName7() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    Process process = new Process();
    ServiceTask serviceTask = mock(ServiceTask.class);
    when(serviceTask.getXmlColumnNumber()).thenReturn(10);
    when(serviceTask.getXmlRowNumber()).thenReturn(10);
    when(serviceTask.getId()).thenReturn("42");
    when(serviceTask.getName()).thenReturn("Name");
    when(serviceTask.getImplementationType()).thenReturn("class");
    when(serviceTask.getResultVariableName()).thenReturn("Result Variable Name");

    ValidationError validationError = new ValidationError();
    validationError.setActivityId("42");
    validationError.setActivityName("class");
    validationError.setDefaultDescription("class");
    validationError.setKey("class");
    validationError.setParams(new HashMap<>());
    validationError.setProblem("class");
    validationError.setProcessDefinitionId("42");
    validationError.setProcessDefinitionName("class");
    validationError.setValidatorSetName("class");
    validationError.setWarning(true);
    validationError.setXmlColumnNumber(10);
    validationError.setXmlLineNumber(2);

    ArrayList<ValidationError> errors = new ArrayList<>();
    errors.add(validationError);

    // Act
    serviceTaskValidator.verifyResultVariableName(process, serviceTask, errors);

    // Assert
    verify(serviceTask).getId();
    verify(serviceTask).getXmlColumnNumber();
    verify(serviceTask).getXmlRowNumber();
    verify(serviceTask).getName();
    verify(serviceTask).getImplementationType();
    verify(serviceTask).getResultVariableName();
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("42", getResult.getActivityId());
    assertEquals("Name", getResult.getActivityName());
    assertEquals("SERVICE_TASK_RESULT_VAR_NAME_WITH_DELEGATE", getResult.getDefaultDescription());
    assertEquals("SERVICE_TASK_RESULT_VAR_NAME_WITH_DELEGATE", getResult.getKey());
    assertEquals("SERVICE_TASK_RESULT_VAR_NAME_WITH_DELEGATE", getResult.getProblem());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertSame(validationError, errors.get(0));
  }
}
