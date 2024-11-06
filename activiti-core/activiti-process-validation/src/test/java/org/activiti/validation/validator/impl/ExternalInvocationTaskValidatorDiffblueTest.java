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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ExternalInvocationTaskValidatorDiffblueTest {
  /**
   * Test
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForEmail(Process, TaskWithFieldExtensions, List, List)}.
   * <p>
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForEmail(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  @DisplayName("Test validateFieldDeclarationsForEmail(Process, TaskWithFieldExtensions, List, List)")
  void testValidateFieldDeclarationsForEmail() {
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
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForEmail(Process, TaskWithFieldExtensions, List, List)}.
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor) FieldName is
   * {@code html}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForEmail(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  @DisplayName("Test validateFieldDeclarationsForEmail(Process, TaskWithFieldExtensions, List, List); given FieldExtension (default constructor) FieldName is 'html'")
  void testValidateFieldDeclarationsForEmail_givenFieldExtensionFieldNameIsHtml() {
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
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForEmail(Process, TaskWithFieldExtensions, List, List)}.
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor) FieldName is
   * {@code htmlVar}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForEmail(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  @DisplayName("Test validateFieldDeclarationsForEmail(Process, TaskWithFieldExtensions, List, List); given FieldExtension (default constructor) FieldName is 'htmlVar'")
  void testValidateFieldDeclarationsForEmail_givenFieldExtensionFieldNameIsHtmlVar() {
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
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForEmail(Process, TaskWithFieldExtensions, List, List)}.
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor) FieldName is
   * {@code text}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForEmail(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  @DisplayName("Test validateFieldDeclarationsForEmail(Process, TaskWithFieldExtensions, List, List); given FieldExtension (default constructor) FieldName is 'text'")
  void testValidateFieldDeclarationsForEmail_givenFieldExtensionFieldNameIsText() {
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
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForEmail(Process, TaskWithFieldExtensions, List, List)}.
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor) FieldName is
   * {@code textVar}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForEmail(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  @DisplayName("Test validateFieldDeclarationsForEmail(Process, TaskWithFieldExtensions, List, List); given FieldExtension (default constructor) FieldName is 'textVar'")
  void testValidateFieldDeclarationsForEmail_givenFieldExtensionFieldNameIsTextVar() {
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
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForEmail(Process, TaskWithFieldExtensions, List, List)}.
   * <ul>
   *   <li>When {@link SendTask} (default constructor).</li>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForEmail(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  @DisplayName("Test validateFieldDeclarationsForEmail(Process, TaskWithFieldExtensions, List, List); when SendTask (default constructor); then ArrayList() size is two")
  void testValidateFieldDeclarationsForEmail_whenSendTask_thenArrayListSizeIsTwo() {
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
   * Test
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForShell(Process, TaskWithFieldExtensions, List, List)}.
   * <p>
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForShell(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  @DisplayName("Test validateFieldDeclarationsForShell(Process, TaskWithFieldExtensions, List, List)")
  void testValidateFieldDeclarationsForShell() {
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
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("SHELL_TASK_INVALID_PARAM", getResult.getDefaultDescription());
    assertEquals("SHELL_TASK_INVALID_PARAM", getResult.getKey());
    assertEquals("SHELL_TASK_INVALID_PARAM", getResult.getProblem());
    ValidationError getResult2 = errors.get(1);
    assertEquals("SHELL_TASK_NO_COMMAND", getResult2.getDefaultDescription());
    assertEquals("SHELL_TASK_NO_COMMAND", getResult2.getKey());
    assertEquals("SHELL_TASK_NO_COMMAND", getResult2.getProblem());
    assertNull(getResult2.getActivityId());
    assertNull(getResult2.getActivityName());
    assertNull(getResult2.getProcessDefinitionId());
    assertNull(getResult2.getProcessDefinitionName());
    assertNull(getResult2.getValidatorSetName());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlLineNumber());
    assertFalse(getResult2.isWarning());
    assertTrue(getResult2.getParams().isEmpty());
  }

  /**
   * Test
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForShell(Process, TaskWithFieldExtensions, List, List)}.
   * <p>
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForShell(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  @DisplayName("Test validateFieldDeclarationsForShell(Process, TaskWithFieldExtensions, List, List)")
  void testValidateFieldDeclarationsForShell2() {
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
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForShell(Process, TaskWithFieldExtensions, List, List)}.
   * <p>
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForShell(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  @DisplayName("Test validateFieldDeclarationsForShell(Process, TaskWithFieldExtensions, List, List)")
  void testValidateFieldDeclarationsForShell3() {
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
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForShell(Process, TaskWithFieldExtensions, List, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForShell(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  @DisplayName("Test validateFieldDeclarationsForShell(Process, TaskWithFieldExtensions, List, List); then ArrayList() Empty")
  void testValidateFieldDeclarationsForShell_thenArrayListEmpty() {
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
  }

  /**
   * Test
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForShell(Process, TaskWithFieldExtensions, List, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} first ActivityId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForShell(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  @DisplayName("Test validateFieldDeclarationsForShell(Process, TaskWithFieldExtensions, List, List); then ArrayList() first ActivityId is '42'")
  void testValidateFieldDeclarationsForShell_thenArrayListFirstActivityIdIs42() {
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
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("SHELL_TASK_NO_COMMAND", getResult.getActivityName());
    assertEquals("SHELL_TASK_NO_COMMAND", getResult.getDefaultDescription());
    ValidationError getResult2 = errors.get(1);
    assertEquals("SHELL_TASK_NO_COMMAND", getResult2.getDefaultDescription());
    assertEquals("SHELL_TASK_NO_COMMAND", getResult.getKey());
    assertEquals("SHELL_TASK_NO_COMMAND", getResult2.getKey());
    assertEquals("SHELL_TASK_NO_COMMAND", getResult.getProblem());
    assertEquals("SHELL_TASK_NO_COMMAND", getResult2.getProblem());
    assertEquals("SHELL_TASK_NO_COMMAND", getResult.getProcessDefinitionName());
    assertEquals("SHELL_TASK_NO_COMMAND", getResult.getValidatorSetName());
    assertNull(getResult2.getActivityId());
    assertNull(getResult2.getActivityName());
    assertNull(getResult2.getProcessDefinitionId());
    assertNull(getResult2.getProcessDefinitionName());
    assertNull(getResult2.getValidatorSetName());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlLineNumber());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(2, getResult.getXmlLineNumber());
    assertFalse(getResult2.isWarning());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Test
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForShell(Process, TaskWithFieldExtensions, List, List)}.
   * <ul>
   *   <li>When {@link SendTask} (default constructor).</li>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForShell(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  @DisplayName("Test validateFieldDeclarationsForShell(Process, TaskWithFieldExtensions, List, List); when SendTask (default constructor); then ArrayList() size is one")
  void testValidateFieldDeclarationsForShell_whenSendTask_thenArrayListSizeIsOne() {
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
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForDmn(Process, TaskWithFieldExtensions, List, List)}.
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor) FieldName is
   * {@code DMN_TASK_NO_KEY}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForDmn(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  @DisplayName("Test validateFieldDeclarationsForDmn(Process, TaskWithFieldExtensions, List, List); given FieldExtension (default constructor) FieldName is 'DMN_TASK_NO_KEY'")
  void testValidateFieldDeclarationsForDmn_givenFieldExtensionFieldNameIsDmnTaskNoKey() {
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
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForDmn(Process, TaskWithFieldExtensions, List, List)}.
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor) StringValue is empty
   * string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForDmn(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  @DisplayName("Test validateFieldDeclarationsForDmn(Process, TaskWithFieldExtensions, List, List); given FieldExtension (default constructor) StringValue is empty string")
  void testValidateFieldDeclarationsForDmn_givenFieldExtensionStringValueIsEmptyString() {
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
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForDmn(Process, TaskWithFieldExtensions, List, List)}.
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor) StringValue is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForDmn(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  @DisplayName("Test validateFieldDeclarationsForDmn(Process, TaskWithFieldExtensions, List, List); given FieldExtension (default constructor) StringValue is 'null'")
  void testValidateFieldDeclarationsForDmn_givenFieldExtensionStringValueIsNull() {
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
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForDmn(Process, TaskWithFieldExtensions, List, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForDmn(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  @DisplayName("Test validateFieldDeclarationsForDmn(Process, TaskWithFieldExtensions, List, List); then ArrayList() Empty")
  void testValidateFieldDeclarationsForDmn_thenArrayListEmpty() {
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
  }

  /**
   * Test
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForDmn(Process, TaskWithFieldExtensions, List, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForDmn(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  @DisplayName("Test validateFieldDeclarationsForDmn(Process, TaskWithFieldExtensions, List, List); then ArrayList() size is two")
  void testValidateFieldDeclarationsForDmn_thenArrayListSizeIsTwo() {
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
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForDmn(Process, TaskWithFieldExtensions, List, List)}.
   * <ul>
   *   <li>When {@link SendTask} (default constructor).</li>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExternalInvocationTaskValidator#validateFieldDeclarationsForDmn(Process, TaskWithFieldExtensions, List, List)}
   */
  @Test
  @DisplayName("Test validateFieldDeclarationsForDmn(Process, TaskWithFieldExtensions, List, List); when SendTask (default constructor); then ArrayList() size is one")
  void testValidateFieldDeclarationsForDmn_whenSendTask_thenArrayListSizeIsOne() {
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
    assertTrue(getResult.getParams().isEmpty());
  }
}
