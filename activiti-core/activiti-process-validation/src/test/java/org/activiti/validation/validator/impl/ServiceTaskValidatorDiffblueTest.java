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
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.ServiceTask;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ServiceTaskValidatorDiffblueTest {
  /**
   * Test {@link ServiceTaskValidator#executeValidation(BpmnModel, Process, List)}.
   * <p>
   * Method under test: {@link ServiceTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ServiceTaskValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation() {
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
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("SERVICE_TASK_MISSING_IMPLEMENTATION", getResult.getDefaultDescription());
    assertEquals("SERVICE_TASK_MISSING_IMPLEMENTATION", getResult.getKey());
    assertEquals("SERVICE_TASK_MISSING_IMPLEMENTATION", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
  }

  /**
   * Test {@link ServiceTaskValidator#executeValidation(BpmnModel, Process, List)}.
   * <p>
   * Method under test: {@link ServiceTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ServiceTaskValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation2() {
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
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("SERVICE_TASK_INVALID_TYPE", getResult.getDefaultDescription());
    assertEquals("SERVICE_TASK_INVALID_TYPE", getResult.getKey());
    assertEquals("SERVICE_TASK_INVALID_TYPE", getResult.getProblem());
  }

  /**
   * Test {@link ServiceTaskValidator#executeValidation(BpmnModel, Process, List)}.
   * <p>
   * Method under test: {@link ServiceTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ServiceTaskValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation3() {
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
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("42", getResult.getActivityId());
    assertEquals("Name", getResult.getActivityName());
    assertEquals("SERVICE_TASK_RESULT_VAR_NAME_WITH_DELEGATE", getResult.getDefaultDescription());
    assertEquals("SERVICE_TASK_RESULT_VAR_NAME_WITH_DELEGATE", getResult.getKey());
    assertEquals("SERVICE_TASK_RESULT_VAR_NAME_WITH_DELEGATE", getResult.getProblem());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
  }

  /**
   * Test {@link ServiceTaskValidator#executeValidation(BpmnModel, Process, List)}.
   * <p>
   * Method under test: {@link ServiceTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ServiceTaskValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation4() {
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
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("SERVICE_TASK_MISSING_IMPLEMENTATION", getResult.getDefaultDescription());
    assertEquals("SERVICE_TASK_MISSING_IMPLEMENTATION", getResult.getKey());
    assertEquals("SERVICE_TASK_MISSING_IMPLEMENTATION", getResult.getProblem());
  }

  /**
   * Test {@link ServiceTaskValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given ArrayList(); then ArrayList() Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ServiceTaskValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenArrayList_thenArrayListEmpty() {
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
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link ServiceTaskValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link ServiceTask} {@link ServiceTask#getImplementationType()} return {@code class}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given ServiceTask getImplementationType() return 'class'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ServiceTaskValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenServiceTaskGetImplementationTypeReturnClass() {
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
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("42", getResult.getActivityId());
    assertEquals("Name", getResult.getActivityName());
    assertEquals("SERVICE_TASK_RESULT_VAR_NAME_WITH_DELEGATE", getResult.getDefaultDescription());
    assertEquals("SERVICE_TASK_RESULT_VAR_NAME_WITH_DELEGATE", getResult.getKey());
    assertEquals("SERVICE_TASK_RESULT_VAR_NAME_WITH_DELEGATE", getResult.getProblem());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
  }

  /**
   * Test {@link ServiceTaskValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link ServiceTask} {@link ServiceTask#getResultVariableName()} return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given ServiceTask getResultVariableName() return empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ServiceTaskValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenServiceTaskGetResultVariableNameReturnEmptyString() {
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
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("SERVICE_TASK_INVALID_TYPE", getResult.getDefaultDescription());
    assertEquals("SERVICE_TASK_INVALID_TYPE", getResult.getKey());
    assertEquals("SERVICE_TASK_INVALID_TYPE", getResult.getProblem());
  }

  /**
   * Test {@link ServiceTaskValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} first ActivityId is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() first ActivityId is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ServiceTaskValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_thenArrayListFirstActivityIdIsNull() {
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
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
  }

  /**
   * Test {@link ServiceTaskValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} second DefaultDescription is {@code MAIL_TASK_NO_CONTENT}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() second DefaultDescription is 'MAIL_TASK_NO_CONTENT'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ServiceTaskValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_thenArrayListSecondDefaultDescriptionIsMailTaskNoContent() {
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
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("MAIL_TASK_NO_CONTENT", getResult.getDefaultDescription());
    assertEquals("MAIL_TASK_NO_CONTENT", getResult.getKey());
    assertEquals("MAIL_TASK_NO_CONTENT", getResult.getProblem());
    ValidationError getResult2 = errors.get(0);
    assertEquals("MAIL_TASK_NO_RECIPIENT", getResult2.getDefaultDescription());
    assertEquals("MAIL_TASK_NO_RECIPIENT", getResult2.getKey());
    assertEquals("MAIL_TASK_NO_RECIPIENT", getResult2.getProblem());
  }

  /**
   * Test {@link ServiceTaskValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Then calls {@link ServiceTask#getImplementation()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then calls getImplementation()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ServiceTaskValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_thenCallsGetImplementation() {
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
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link ServiceTaskValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>When {@link Process} (default constructor).</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTaskValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); when Process (default constructor); then ArrayList() Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ServiceTaskValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_whenProcess_thenArrayListEmpty() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link ServiceTaskValidator#verifyImplementation(Process, ServiceTask, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTaskValidator#verifyImplementation(Process, ServiceTask, List)}
   */
  @Test
  @DisplayName("Test verifyImplementation(Process, ServiceTask, List); then ArrayList() size is two")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ServiceTaskValidator.verifyImplementation(Process, ServiceTask, List)"})
  void testVerifyImplementation_thenArrayListSizeIsTwo() {
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
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link ServiceTaskValidator#verifyImplementation(Process, ServiceTask, List)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTaskValidator#verifyImplementation(Process, ServiceTask, List)}
   */
  @Test
  @DisplayName("Test verifyImplementation(Process, ServiceTask, List); when 'null'; then ArrayList() size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ServiceTaskValidator.verifyImplementation(Process, ServiceTask, List)"})
  void testVerifyImplementation_whenNull_thenArrayListSizeIsOne() {
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
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link ServiceTaskValidator#verifyImplementation(Process, ServiceTask, List)}.
   * <ul>
   *   <li>When {@link ServiceTask} (default constructor) Implementation is {@code Service Task}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTaskValidator#verifyImplementation(Process, ServiceTask, List)}
   */
  @Test
  @DisplayName("Test verifyImplementation(Process, ServiceTask, List); when ServiceTask (default constructor) Implementation is 'Service Task'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ServiceTaskValidator.verifyImplementation(Process, ServiceTask, List)"})
  void testVerifyImplementation_whenServiceTaskImplementationIsServiceTask() {
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
  }

  /**
   * Test {@link ServiceTaskValidator#verifyImplementation(Process, ServiceTask, List)}.
   * <ul>
   *   <li>When {@link ServiceTask} (default constructor) Type is empty string.</li>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTaskValidator#verifyImplementation(Process, ServiceTask, List)}
   */
  @Test
  @DisplayName("Test verifyImplementation(Process, ServiceTask, List); when ServiceTask (default constructor) Type is empty string; then ArrayList() size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ServiceTaskValidator.verifyImplementation(Process, ServiceTask, List)"})
  void testVerifyImplementation_whenServiceTaskTypeIsEmptyString_thenArrayListSizeIsOne() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    Process process = new Process();

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setType("");
    serviceTask.setImplementation("");
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
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link ServiceTaskValidator#verifyImplementation(Process, ServiceTask, List)}.
   * <ul>
   *   <li>When {@link ServiceTask} (default constructor) Type is {@code Service Task}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTaskValidator#verifyImplementation(Process, ServiceTask, List)}
   */
  @Test
  @DisplayName("Test verifyImplementation(Process, ServiceTask, List); when ServiceTask (default constructor) Type is 'Service Task'; then ArrayList() Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ServiceTaskValidator.verifyImplementation(Process, ServiceTask, List)"})
  void testVerifyImplementation_whenServiceTaskTypeIsServiceTask_thenArrayListEmpty() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    Process process = new Process();

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setType("Service Task");
    serviceTask.setImplementation("");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.verifyImplementation(process, serviceTask, errors);

    // Assert that nothing has changed
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test {@link ServiceTaskValidator#verifyImplementation(Process, ServiceTask, List)}.
   * <ul>
   *   <li>When {@link ServiceTask} (default constructor).</li>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTaskValidator#verifyImplementation(Process, ServiceTask, List)}
   */
  @Test
  @DisplayName("Test verifyImplementation(Process, ServiceTask, List); when ServiceTask (default constructor); then ArrayList() size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ServiceTaskValidator.verifyImplementation(Process, ServiceTask, List)"})
  void testVerifyImplementation_whenServiceTask_thenArrayListSizeIsOne() {
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
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link ServiceTaskValidator#verifyType(Process, ServiceTask, List)}.
   * <ul>
   *   <li>Given {@code camel}.</li>
   *   <li>When {@link ServiceTask} (default constructor) Type is {@code camel}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTaskValidator#verifyType(Process, ServiceTask, List)}
   */
  @Test
  @DisplayName("Test verifyType(Process, ServiceTask, List); given 'camel'; when ServiceTask (default constructor) Type is 'camel'; then ArrayList() Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ServiceTaskValidator.verifyType(Process, ServiceTask, List)"})
  void testVerifyType_givenCamel_whenServiceTaskTypeIsCamel_thenArrayListEmpty() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    Process process = new Process();

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setType("camel");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.verifyType(process, serviceTask, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link ServiceTaskValidator#verifyType(Process, ServiceTask, List)}.
   * <ul>
   *   <li>Given {@link ServiceTask#DMN_TASK}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} first DefaultDescription is {@code DMN_TASK_NO_KEY}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTaskValidator#verifyType(Process, ServiceTask, List)}
   */
  @Test
  @DisplayName("Test verifyType(Process, ServiceTask, List); given DMN_TASK; then ArrayList() first DefaultDescription is 'DMN_TASK_NO_KEY'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ServiceTaskValidator.verifyType(Process, ServiceTask, List)"})
  void testVerifyType_givenDmn_task_thenArrayListFirstDefaultDescriptionIsDmnTaskNoKey() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    Process process = new Process();

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setType(ServiceTask.DMN_TASK);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.verifyType(process, serviceTask, errors);

    // Assert
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("DMN_TASK_NO_KEY", getResult.getDefaultDescription());
    assertEquals("DMN_TASK_NO_KEY", getResult.getKey());
    assertEquals("DMN_TASK_NO_KEY", getResult.getProblem());
  }

  /**
   * Test {@link ServiceTaskValidator#verifyType(Process, ServiceTask, List)}.
   * <ul>
   *   <li>Given empty string.</li>
   *   <li>When {@link ServiceTask} (default constructor) Type is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTaskValidator#verifyType(Process, ServiceTask, List)}
   */
  @Test
  @DisplayName("Test verifyType(Process, ServiceTask, List); given empty string; when ServiceTask (default constructor) Type is empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ServiceTaskValidator.verifyType(Process, ServiceTask, List)"})
  void testVerifyType_givenEmptyString_whenServiceTaskTypeIsEmptyString() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    Process process = new Process();

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setType("");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.verifyType(process, serviceTask, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link ServiceTaskValidator#verifyType(Process, ServiceTask, List)}.
   * <ul>
   *   <li>Given {@link ServiceTask#MAIL_TASK}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTaskValidator#verifyType(Process, ServiceTask, List)}
   */
  @Test
  @DisplayName("Test verifyType(Process, ServiceTask, List); given MAIL_TASK; then ArrayList() size is two")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ServiceTaskValidator.verifyType(Process, ServiceTask, List)"})
  void testVerifyType_givenMail_task_thenArrayListSizeIsTwo() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    Process process = new Process();

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setType(ServiceTask.MAIL_TASK);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.verifyType(process, serviceTask, errors);

    // Assert
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("MAIL_TASK_NO_CONTENT", getResult.getDefaultDescription());
    assertEquals("MAIL_TASK_NO_CONTENT", getResult.getKey());
    assertEquals("MAIL_TASK_NO_CONTENT", getResult.getProblem());
    ValidationError getResult2 = errors.get(0);
    assertEquals("MAIL_TASK_NO_RECIPIENT", getResult2.getDefaultDescription());
    assertEquals("MAIL_TASK_NO_RECIPIENT", getResult2.getKey());
    assertEquals("MAIL_TASK_NO_RECIPIENT", getResult2.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link ServiceTaskValidator#verifyType(Process, ServiceTask, List)}.
   * <ul>
   *   <li>Given {@code mule}.</li>
   *   <li>When {@link ServiceTask} (default constructor) Type is {@code mule}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTaskValidator#verifyType(Process, ServiceTask, List)}
   */
  @Test
  @DisplayName("Test verifyType(Process, ServiceTask, List); given 'mule'; when ServiceTask (default constructor) Type is 'mule'; then ArrayList() Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ServiceTaskValidator.verifyType(Process, ServiceTask, List)"})
  void testVerifyType_givenMule_whenServiceTaskTypeIsMule_thenArrayListEmpty() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    Process process = new Process();

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setType("mule");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.verifyType(process, serviceTask, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link ServiceTaskValidator#verifyType(Process, ServiceTask, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} first DefaultDescription is {@code SERVICE_TASK_INVALID_TYPE}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTaskValidator#verifyType(Process, ServiceTask, List)}
   */
  @Test
  @DisplayName("Test verifyType(Process, ServiceTask, List); then ArrayList() first DefaultDescription is 'SERVICE_TASK_INVALID_TYPE'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ServiceTaskValidator.verifyType(Process, ServiceTask, List)"})
  void testVerifyType_thenArrayListFirstDefaultDescriptionIsServiceTaskInvalidType() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    Process process = new Process();

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setType("not empty");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.verifyType(process, serviceTask, errors);

    // Assert
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("SERVICE_TASK_INVALID_TYPE", getResult.getDefaultDescription());
    assertEquals("SERVICE_TASK_INVALID_TYPE", getResult.getKey());
    assertEquals("SERVICE_TASK_INVALID_TYPE", getResult.getProblem());
  }

  /**
   * Test {@link ServiceTaskValidator#verifyType(Process, ServiceTask, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} first DefaultDescription is {@code SHELL_TASK_NO_COMMAND}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTaskValidator#verifyType(Process, ServiceTask, List)}
   */
  @Test
  @DisplayName("Test verifyType(Process, ServiceTask, List); then ArrayList() first DefaultDescription is 'SHELL_TASK_NO_COMMAND'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ServiceTaskValidator.verifyType(Process, ServiceTask, List)"})
  void testVerifyType_thenArrayListFirstDefaultDescriptionIsShellTaskNoCommand() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    Process process = new Process();

    ServiceTask serviceTask = new ServiceTask();
    serviceTask.setType("shell");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.verifyType(process, serviceTask, errors);

    // Assert
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("SHELL_TASK_NO_COMMAND", getResult.getDefaultDescription());
    assertEquals("SHELL_TASK_NO_COMMAND", getResult.getKey());
    assertEquals("SHELL_TASK_NO_COMMAND", getResult.getProblem());
  }

  /**
   * Test {@link ServiceTaskValidator#verifyType(Process, ServiceTask, List)}.
   * <ul>
   *   <li>When {@link ServiceTask} (default constructor).</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTaskValidator#verifyType(Process, ServiceTask, List)}
   */
  @Test
  @DisplayName("Test verifyType(Process, ServiceTask, List); when ServiceTask (default constructor); then ArrayList() Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ServiceTaskValidator.verifyType(Process, ServiceTask, List)"})
  void testVerifyType_whenServiceTask_thenArrayListEmpty() {
    // Arrange
    ServiceTaskValidator serviceTaskValidator = new ServiceTaskValidator();
    Process process = new Process();
    ServiceTask serviceTask = new ServiceTask();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    serviceTaskValidator.verifyType(process, serviceTask, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link ServiceTaskValidator#verifyResultVariableName(Process, ServiceTask, List)}.
   * <ul>
   *   <li>Given empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTaskValidator#verifyResultVariableName(Process, ServiceTask, List)}
   */
  @Test
  @DisplayName("Test verifyResultVariableName(Process, ServiceTask, List); given empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ServiceTaskValidator.verifyResultVariableName(Process, ServiceTask, List)"})
  void testVerifyResultVariableName_givenEmptyString() {
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
  }

  /**
   * Test {@link ServiceTaskValidator#verifyResultVariableName(Process, ServiceTask, List)}.
   * <ul>
   *   <li>Given {@code Implementation Type}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTaskValidator#verifyResultVariableName(Process, ServiceTask, List)}
   */
  @Test
  @DisplayName("Test verifyResultVariableName(Process, ServiceTask, List); given 'Implementation Type'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ServiceTaskValidator.verifyResultVariableName(Process, ServiceTask, List)"})
  void testVerifyResultVariableName_givenImplementationType() {
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
  }

  /**
   * Test {@link ServiceTaskValidator#verifyResultVariableName(Process, ServiceTask, List)}.
   * <ul>
   *   <li>Given ten.</li>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTaskValidator#verifyResultVariableName(Process, ServiceTask, List)}
   */
  @Test
  @DisplayName("Test verifyResultVariableName(Process, ServiceTask, List); given ten; then ArrayList() size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ServiceTaskValidator.verifyResultVariableName(Process, ServiceTask, List)"})
  void testVerifyResultVariableName_givenTen_thenArrayListSizeIsOne() {
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
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link ServiceTaskValidator#verifyResultVariableName(Process, ServiceTask, List)}.
   * <ul>
   *   <li>Given ten.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTaskValidator#verifyResultVariableName(Process, ServiceTask, List)}
   */
  @Test
  @DisplayName("Test verifyResultVariableName(Process, ServiceTask, List); given ten; when 'null'; then ArrayList() size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ServiceTaskValidator.verifyResultVariableName(Process, ServiceTask, List)"})
  void testVerifyResultVariableName_givenTen_whenNull_thenArrayListSizeIsOne() {
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
   * Test {@link ServiceTaskValidator#verifyResultVariableName(Process, ServiceTask, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTaskValidator#verifyResultVariableName(Process, ServiceTask, List)}
   */
  @Test
  @DisplayName("Test verifyResultVariableName(Process, ServiceTask, List); then ArrayList() size is two")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ServiceTaskValidator.verifyResultVariableName(Process, ServiceTask, List)"})
  void testVerifyResultVariableName_thenArrayListSizeIsTwo() {
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
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link ServiceTaskValidator#verifyResultVariableName(Process, ServiceTask, List)}.
   * <ul>
   *   <li>When {@link ServiceTask} (default constructor).</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link ServiceTaskValidator#verifyResultVariableName(Process, ServiceTask, List)}
   */
  @Test
  @DisplayName("Test verifyResultVariableName(Process, ServiceTask, List); when ServiceTask (default constructor); then ArrayList() Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ServiceTaskValidator.verifyResultVariableName(Process, ServiceTask, List)"})
  void testVerifyResultVariableName_whenServiceTask_thenArrayListEmpty() {
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
  }
}
