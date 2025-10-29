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
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.Signal;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.Test;

class BpmnModelValidatorDiffblueTest {
  /**
   * Method under test: {@link BpmnModelValidator#validate(BpmnModel, List)}
   */
  @Test
  void testValidate() {
    // Arrange
    BpmnModelValidator bpmnModelValidator = new BpmnModelValidator();
    BpmnModel bpmnModel = new BpmnModel();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    bpmnModelValidator.validate(bpmnModel, errors);

    // Assert
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE", getResult.getDefaultDescription());
    assertEquals("ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE", getResult.getKey());
    assertEquals("ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
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
   * Method under test: {@link BpmnModelValidator#validate(BpmnModel, List)}
   */
  @Test
  void testValidate2() {
    // Arrange
    BpmnModelValidator bpmnModelValidator = new BpmnModelValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    bpmnModelValidator.validate(bpmnModel, errors);

    // Assert that nothing has changed
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, bpmnModel.getProcesses().size());
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
  }

  /**
   * Method under test: {@link BpmnModelValidator#validate(BpmnModel, List)}
   */
  @Test
  void testValidate3() {
    // Arrange
    BpmnModelValidator bpmnModelValidator = new BpmnModelValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());
    bpmnModel.addProcess(new Process());
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    bpmnModelValidator.validate(bpmnModel, errors);

    // Assert that nothing has changed
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(2, bpmnModel.getProcesses().size());
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
  }

  /**
   * Method under test: {@link BpmnModelValidator#validate(BpmnModel, List)}
   */
  @Test
  void testValidate4() {
    // Arrange
    BpmnModelValidator bpmnModelValidator = new BpmnModelValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.setTargetNamespace("Target Namespace");
    bpmnModel.addProcess(new Process());
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    bpmnModelValidator.validate(bpmnModel, errors);

    // Assert that nothing has changed
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, bpmnModel.getProcesses().size());
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
  }

  /**
   * Method under test: {@link BpmnModelValidator#validate(BpmnModel, List)}
   */
  @Test
  void testValidate5() {
    // Arrange
    BpmnModelValidator bpmnModelValidator = new BpmnModelValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ValidationError validationError = new ValidationError();
    validationError.setActivityId("42");
    validationError.setActivityName("ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE");
    validationError.setDefaultDescription("ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE");
    validationError.setKey("ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE");
    validationError.setParams(new HashMap<>());
    validationError.setProblem("ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE");
    validationError.setProcessDefinitionId("42");
    validationError.setProcessDefinitionName("ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE");
    validationError.setValidatorSetName("ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE");
    validationError.setWarning(true);
    validationError.setXmlColumnNumber(10);
    validationError.setXmlLineNumber(2);

    ArrayList<ValidationError> errors = new ArrayList<>();
    errors.add(validationError);

    // Act
    bpmnModelValidator.validate(bpmnModel, errors);

    // Assert
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE", getResult.getDefaultDescription());
    assertEquals("ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE", getResult.getKey());
    assertEquals("ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
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
    assertSame(validationError, errors.get(0));
  }

  /**
   * Method under test:
   * {@link BpmnModelValidator#handleBPMNModelConstraints(BpmnModel, List)}
   */
  @Test
  void testHandleBPMNModelConstraints() {
    // Arrange
    BpmnModelValidator bpmnModelValidator = new BpmnModelValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getTargetNamespace()).thenReturn("Target Namespace");

    // Act
    bpmnModelValidator.handleBPMNModelConstraints(bpmnModel, new ArrayList<>());

    // Assert that nothing has changed
    verify(bpmnModel, atLeast(1)).getTargetNamespace();
  }

  /**
   * Method under test:
   * {@link BpmnModelValidator#handleBPMNModelConstraints(BpmnModel, List)}
   */
  @Test
  void testHandleBPMNModelConstraints2() {
    // Arrange
    BpmnModelValidator bpmnModelValidator = new BpmnModelValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getTargetNamespace()).thenReturn("Target Namespace");

    ValidationError validationError = new ValidationError();
    validationError.setActivityId("42");
    validationError.setActivityName("Activity Name");
    validationError.setDefaultDescription("Default Description");
    validationError.setKey("Key");
    validationError.setParams(new HashMap<>());
    validationError.setProblem("Problem");
    validationError.setProcessDefinitionId("42");
    validationError.setProcessDefinitionName("Process Definition Name");
    validationError.setValidatorSetName("Validator Set Name");
    validationError.setWarning(true);
    validationError.setXmlColumnNumber(10);
    validationError.setXmlLineNumber(2);

    ArrayList<ValidationError> errors = new ArrayList<>();
    errors.add(validationError);

    // Act
    bpmnModelValidator.handleBPMNModelConstraints(bpmnModel, errors);

    // Assert that nothing has changed
    verify(bpmnModel, atLeast(1)).getTargetNamespace();
  }

  /**
   * Method under test:
   * {@link BpmnModelValidator#handleBPMNModelConstraints(BpmnModel, List)}
   */
  @Test
  void testHandleBPMNModelConstraints3() {
    // Arrange
    BpmnModelValidator bpmnModelValidator = new BpmnModelValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getTargetNamespace()).thenReturn("Target Namespace");

    ValidationError validationError = new ValidationError();
    validationError.setActivityId("42");
    validationError.setActivityName("Activity Name");
    validationError.setDefaultDescription("Default Description");
    validationError.setKey("Key");
    validationError.setParams(new HashMap<>());
    validationError.setProblem("Problem");
    validationError.setProcessDefinitionId("42");
    validationError.setProcessDefinitionName("Process Definition Name");
    validationError.setValidatorSetName("Validator Set Name");
    validationError.setWarning(true);
    validationError.setXmlColumnNumber(10);
    validationError.setXmlLineNumber(2);

    ValidationError validationError2 = new ValidationError();
    validationError2.setActivityId("Activity Id");
    validationError2.setActivityName("42");
    validationError2.setDefaultDescription("42");
    validationError2.setKey("42");
    validationError2.setParams(new HashMap<>());
    validationError2.setProblem("42");
    validationError2.setProcessDefinitionId("Process Definition Id");
    validationError2.setProcessDefinitionName("42");
    validationError2.setValidatorSetName("42");
    validationError2.setWarning(false);
    validationError2.setXmlColumnNumber(255);
    validationError2.setXmlLineNumber(10);

    ArrayList<ValidationError> errors = new ArrayList<>();
    errors.add(validationError2);
    errors.add(validationError);

    // Act
    bpmnModelValidator.handleBPMNModelConstraints(bpmnModel, errors);

    // Assert that nothing has changed
    verify(bpmnModel, atLeast(1)).getTargetNamespace();
  }

  /**
   * Method under test:
   * {@link BpmnModelValidator#validateAtLeastOneExecutable(BpmnModel, List)}
   */
  @Test
  void testValidateAtLeastOneExecutable() {
    // Arrange
    BpmnModelValidator bpmnModelValidator = new BpmnModelValidator();
    BpmnModel bpmnModel = new BpmnModel();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    boolean actualValidateAtLeastOneExecutableResult = bpmnModelValidator.validateAtLeastOneExecutable(bpmnModel,
        errors);

    // Assert
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE", getResult.getDefaultDescription());
    assertEquals("ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE", getResult.getKey());
    assertEquals("ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertFalse(actualValidateAtLeastOneExecutableResult);
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
   * {@link BpmnModelValidator#validateAtLeastOneExecutable(BpmnModel, List)}
   */
  @Test
  void testValidateAtLeastOneExecutable2() {
    // Arrange
    BpmnModelValidator bpmnModelValidator = new BpmnModelValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    boolean actualValidateAtLeastOneExecutableResult = bpmnModelValidator.validateAtLeastOneExecutable(bpmnModel,
        errors);

    // Assert
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, bpmnModel.getProcesses().size());
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(actualValidateAtLeastOneExecutableResult);
  }

  /**
   * Method under test:
   * {@link BpmnModelValidator#validateAtLeastOneExecutable(BpmnModel, List)}
   */
  @Test
  void testValidateAtLeastOneExecutable3() {
    // Arrange
    BpmnModelValidator bpmnModelValidator = new BpmnModelValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ValidationError validationError = new ValidationError();
    validationError.setActivityId("42");
    validationError.setActivityName("ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE");
    validationError.setDefaultDescription("ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE");
    validationError.setKey("ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE");
    validationError.setParams(new HashMap<>());
    validationError.setProblem("ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE");
    validationError.setProcessDefinitionId("42");
    validationError.setProcessDefinitionName("ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE");
    validationError.setValidatorSetName("ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE");
    validationError.setWarning(true);
    validationError.setXmlColumnNumber(10);
    validationError.setXmlLineNumber(2);

    ArrayList<ValidationError> errors = new ArrayList<>();
    errors.add(validationError);

    // Act
    boolean actualValidateAtLeastOneExecutableResult = bpmnModelValidator.validateAtLeastOneExecutable(bpmnModel,
        errors);

    // Assert
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE", getResult.getDefaultDescription());
    assertEquals("ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE", getResult.getKey());
    assertEquals("ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertFalse(actualValidateAtLeastOneExecutableResult);
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertSame(validationError, errors.get(0));
  }

  /**
   * Method under test:
   * {@link BpmnModelValidator#validateAtLeastOneExecutable(BpmnModel, List)}
   */
  @Test
  void testValidateAtLeastOneExecutable4() {
    // Arrange
    BpmnModelValidator bpmnModelValidator = new BpmnModelValidator();
    BpmnModel bpmnModel = new BpmnModel();

    HashMap<String, String> params = new HashMap<>();
    params.computeIfPresent("ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE", mock(BiFunction.class));

    ValidationError validationError = new ValidationError();
    validationError.setActivityId("42");
    validationError.setActivityName("ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE");
    validationError.setDefaultDescription("ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE");
    validationError.setKey("ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE");
    validationError.setParams(params);
    validationError.setProblem("ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE");
    validationError.setProcessDefinitionId("42");
    validationError.setProcessDefinitionName("ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE");
    validationError.setValidatorSetName("ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE");
    validationError.setWarning(true);
    validationError.setXmlColumnNumber(10);
    validationError.setXmlLineNumber(2);

    ArrayList<ValidationError> errors = new ArrayList<>();
    errors.add(validationError);

    // Act
    boolean actualValidateAtLeastOneExecutableResult = bpmnModelValidator.validateAtLeastOneExecutable(bpmnModel,
        errors);

    // Assert
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE", getResult.getDefaultDescription());
    assertEquals("ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE", getResult.getKey());
    assertEquals("ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertFalse(actualValidateAtLeastOneExecutableResult);
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertSame(validationError, errors.get(0));
  }

  /**
   * Method under test: {@link BpmnModelValidator#getProcessesWithSameId(List)}
   */
  @Test
  void testGetProcessesWithSameId() {
    // Arrange
    BpmnModelValidator bpmnModelValidator = new BpmnModelValidator();

    // Act and Assert
    assertTrue(bpmnModelValidator.getProcessesWithSameId(new ArrayList<>()).isEmpty());
  }

  /**
   * Method under test: {@link BpmnModelValidator#getProcessesWithSameId(List)}
   */
  @Test
  void testGetProcessesWithSameId2() {
    // Arrange
    BpmnModelValidator bpmnModelValidator = new BpmnModelValidator();

    Process process = new Process();
    process.setName(null);

    ArrayList<Process> processes = new ArrayList<>();
    processes.add(process);

    // Act and Assert
    assertTrue(bpmnModelValidator.getProcessesWithSameId(processes).isEmpty());
  }

  /**
   * Method under test: {@link BpmnModelValidator#getProcessesWithSameId(List)}
   */
  @Test
  void testGetProcessesWithSameId3() {
    // Arrange
    BpmnModelValidator bpmnModelValidator = new BpmnModelValidator();

    Process process = new Process();
    process.setId("42");
    process.setName("Processes");

    ArrayList<Process> processes = new ArrayList<>();
    processes.add(process);

    // Act and Assert
    assertTrue(bpmnModelValidator.getProcessesWithSameId(processes).isEmpty());
  }

  /**
   * Method under test: {@link BpmnModelValidator#getProcessesWithSameId(List)}
   */
  @Test
  void testGetProcessesWithSameId4() {
    // Arrange
    BpmnModelValidator bpmnModelValidator = new BpmnModelValidator();

    ArrayList<Process> processes = new ArrayList<>();
    processes.add(new Process());
    processes.add(new Process());

    // Act and Assert
    assertTrue(bpmnModelValidator.getProcessesWithSameId(processes).isEmpty());
  }
}
