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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.Signal;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class BpmnModelValidatorDiffblueTest {
  /**
   * Test {@link BpmnModelValidator#validate(BpmnModel, List)}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor) Executable is {@code false}.
   *   <li>Then {@link BpmnModel} (default constructor) MainProcess Artifacts {@link List}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnModelValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validate(BpmnModel, List); given Process (default constructor) Executable is 'false'; then BpmnModel (default constructor) MainProcess Artifacts List")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnModelValidator.validate(BpmnModel, List)"})
  void testValidate_givenProcessExecutableIsFalse_thenBpmnModelMainProcessArtifactsList() {
    // Arrange
    BpmnModelValidator bpmnModelValidator = new BpmnModelValidator();

    Process process = new Process();
    process.setExecutable(false);
    process.setId("Bpmn Model");
    process.setName("Bpmn Model");
    process.setDocumentation("Bpmn Model");

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);
    bpmnModel.setTargetNamespace("Bpmn Model");

    // Act
    bpmnModelValidator.validate(bpmnModel, new ArrayList<>());

    // Assert
    Process mainProcess = bpmnModel.getMainProcess();
    Collection<Artifact> artifacts = mainProcess.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = mainProcess.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test {@link BpmnModelValidator#validate(BpmnModel, List)}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor) Executable is {@code true}.
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link BpmnModelValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validate(BpmnModel, List); given Process (default constructor) Executable is 'true'; then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnModelValidator.validate(BpmnModel, List)"})
  void testValidate_givenProcessExecutableIsTrue_thenArrayListEmpty() {
    // Arrange
    BpmnModelValidator bpmnModelValidator = new BpmnModelValidator();

    Process process = new Process();
    process.setExecutable(true);
    process.setId("Bpmn Model");
    process.setName("Bpmn Model");
    process.setDocumentation("Bpmn Model");

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);
    bpmnModel.setTargetNamespace("Bpmn Model");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    bpmnModelValidator.validate(bpmnModel, errors);

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
   * Test {@link BpmnModelValidator#validate(BpmnModel, List)}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor) Name is {@code null}.
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link BpmnModelValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validate(BpmnModel, List); given Process (default constructor) Name is 'null'; then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnModelValidator.validate(BpmnModel, List)"})
  void testValidate_givenProcessNameIsNull_thenArrayListEmpty() {
    // Arrange
    BpmnModelValidator bpmnModelValidator = new BpmnModelValidator();

    Process process = new Process();
    process.setName(null);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);
    bpmnModel.setTargetNamespace("Bpmn Model");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    bpmnModelValidator.validate(bpmnModel, errors);

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
   * Test {@link BpmnModelValidator#validate(BpmnModel, List)}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor) Name is {@code null}.
   *   <li>Then {@link BpmnModel} (default constructor) MainProcess Artifacts {@link List}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnModelValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validate(BpmnModel, List); given Process (default constructor) Name is 'null'; then BpmnModel (default constructor) MainProcess Artifacts List")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnModelValidator.validate(BpmnModel, List)"})
  void testValidate_givenProcessNameIsNull_thenBpmnModelMainProcessArtifactsList() {
    // Arrange
    BpmnModelValidator bpmnModelValidator = new BpmnModelValidator();

    Process process = new Process();
    process.setExecutable(false);
    process.setId("Bpmn Model");
    process.setName(null);
    process.setDocumentation("Bpmn Model");

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);
    bpmnModel.setTargetNamespace("Bpmn Model");

    // Act
    bpmnModelValidator.validate(bpmnModel, new ArrayList<>());

    // Assert
    Process mainProcess = bpmnModel.getMainProcess();
    Collection<Artifact> artifacts = mainProcess.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = mainProcess.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test {@link BpmnModelValidator#validate(BpmnModel, List)}.
   *
   * <ul>
   *   <li>When {@link BpmnModel} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link BpmnModelValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validate(BpmnModel, List); when BpmnModel (default constructor); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnModelValidator.validate(BpmnModel, List)"})
  void testValidate_whenBpmnModel_thenArrayListSizeIsOne() {
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
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link BpmnModelValidator#validateAtLeastOneExecutable(BpmnModel, List)}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link BpmnModelValidator#validateAtLeastOneExecutable(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validateAtLeastOneExecutable(BpmnModel, List); given Process (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BpmnModelValidator.validateAtLeastOneExecutable(BpmnModel, List)"})
  void testValidateAtLeastOneExecutable_givenProcess_thenArrayListEmpty() {
    // Arrange
    BpmnModelValidator bpmnModelValidator = new BpmnModelValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    boolean actualValidateAtLeastOneExecutableResult =
        bpmnModelValidator.validateAtLeastOneExecutable(bpmnModel, errors);

    // Assert
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(actualValidateAtLeastOneExecutableResult);
  }

  /**
   * Test {@link BpmnModelValidator#validateAtLeastOneExecutable(BpmnModel, List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link BpmnModelValidator#validateAtLeastOneExecutable(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validateAtLeastOneExecutable(BpmnModel, List); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BpmnModelValidator.validateAtLeastOneExecutable(BpmnModel, List)"})
  void testValidateAtLeastOneExecutable_thenArrayListSizeIsTwo() {
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
    bpmnModelValidator.validateAtLeastOneExecutable(bpmnModel, errors);

    // Assert
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
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link BpmnModelValidator#validateAtLeastOneExecutable(BpmnModel, List)}.
   *
   * <ul>
   *   <li>Then {@link BpmnModel} (default constructor) MainProcess Artifacts {@link List}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnModelValidator#validateAtLeastOneExecutable(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validateAtLeastOneExecutable(BpmnModel, List); then BpmnModel (default constructor) MainProcess Artifacts List")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BpmnModelValidator.validateAtLeastOneExecutable(BpmnModel, List)"})
  void testValidateAtLeastOneExecutable_thenBpmnModelMainProcessArtifactsList() {
    // Arrange
    BpmnModelValidator bpmnModelValidator = new BpmnModelValidator();

    Process process = new Process();
    process.setExecutable(false);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act
    bpmnModelValidator.validateAtLeastOneExecutable(bpmnModel, new ArrayList<>());

    // Assert
    Process mainProcess = bpmnModel.getMainProcess();
    Collection<Artifact> artifacts = mainProcess.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = mainProcess.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test {@link BpmnModelValidator#validateAtLeastOneExecutable(BpmnModel, List)}.
   *
   * <ul>
   *   <li>When {@link BpmnModel} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link BpmnModelValidator#validateAtLeastOneExecutable(BpmnModel, List)}
   */
  @Test
  @DisplayName(
      "Test validateAtLeastOneExecutable(BpmnModel, List); when BpmnModel (default constructor); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BpmnModelValidator.validateAtLeastOneExecutable(BpmnModel, List)"})
  void testValidateAtLeastOneExecutable_whenBpmnModel_thenArrayListSizeIsOne() {
    // Arrange
    BpmnModelValidator bpmnModelValidator = new BpmnModelValidator();
    BpmnModel bpmnModel = new BpmnModel();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    bpmnModelValidator.validateAtLeastOneExecutable(bpmnModel, errors);

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
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link BpmnModelValidator#getProcessesWithSameId(List)}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor) Id is {@code 42}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link BpmnModelValidator#getProcessesWithSameId(List)}
   */
  @Test
  @DisplayName(
      "Test getProcessesWithSameId(List); given Process (default constructor) Id is '42'; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List BpmnModelValidator.getProcessesWithSameId(List)"})
  void testGetProcessesWithSameId_givenProcessIdIs42_thenReturnEmpty() {
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
   * Test {@link BpmnModelValidator#getProcessesWithSameId(List)}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor) Name is {@code null}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link BpmnModelValidator#getProcessesWithSameId(List)}
   */
  @Test
  @DisplayName(
      "Test getProcessesWithSameId(List); given Process (default constructor) Name is 'null'; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List BpmnModelValidator.getProcessesWithSameId(List)"})
  void testGetProcessesWithSameId_givenProcessNameIsNull_thenReturnEmpty() {
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
   * Test {@link BpmnModelValidator#getProcessesWithSameId(List)}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor).
   *   <li>When {@link ArrayList#ArrayList()} add {@link Process} (default constructor).
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link BpmnModelValidator#getProcessesWithSameId(List)}
   */
  @Test
  @DisplayName(
      "Test getProcessesWithSameId(List); given Process (default constructor); when ArrayList() add Process (default constructor); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List BpmnModelValidator.getProcessesWithSameId(List)"})
  void testGetProcessesWithSameId_givenProcess_whenArrayListAddProcess_thenReturnEmpty() {
    // Arrange
    BpmnModelValidator bpmnModelValidator = new BpmnModelValidator();

    ArrayList<Process> processes = new ArrayList<>();
    processes.add(new Process());
    processes.add(new Process());

    // Act and Assert
    assertTrue(bpmnModelValidator.getProcessesWithSameId(processes).isEmpty());
  }

  /**
   * Test {@link BpmnModelValidator#getProcessesWithSameId(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link BpmnModelValidator#getProcessesWithSameId(List)}
   */
  @Test
  @DisplayName("Test getProcessesWithSameId(List); when ArrayList(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List BpmnModelValidator.getProcessesWithSameId(List)"})
  void testGetProcessesWithSameId_whenArrayList_thenReturnEmpty() {
    // Arrange
    BpmnModelValidator bpmnModelValidator = new BpmnModelValidator();

    // Act and Assert
    assertTrue(bpmnModelValidator.getProcessesWithSameId(new ArrayList<>()).isEmpty());
  }
}
