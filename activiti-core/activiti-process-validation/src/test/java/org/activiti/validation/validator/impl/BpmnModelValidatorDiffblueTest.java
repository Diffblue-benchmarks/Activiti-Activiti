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
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.activiti.bpmn.model.BpmnModel;
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
   * <ul>
   *   <li>Given {@link BpmnModelValidator} (default constructor).</li>
   *   <li>When {@link BpmnModel} (default constructor).</li>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModelValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List); given BpmnModelValidator (default constructor); when BpmnModel (default constructor); then ArrayList() size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnModelValidator.validate(BpmnModel, List)"})
  void testValidate_givenBpmnModelValidator_whenBpmnModel_thenArrayListSizeIsOne() {
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
   * Test {@link BpmnModelValidator#validate(BpmnModel, List)}.
   * <ul>
   *   <li>Given {@link Process} (default constructor).</li>
   *   <li>When {@link BpmnModel} (default constructor) addProcess {@link Process} (default constructor).</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModelValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List); given Process (default constructor); when BpmnModel (default constructor) addProcess Process (default constructor); then ArrayList() Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnModelValidator.validate(BpmnModel, List)"})
  void testValidate_givenProcess_whenBpmnModelAddProcessProcess_thenArrayListEmpty() {
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
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link BpmnModelValidator#validate(BpmnModel, List)}.
   * <ul>
   *   <li>Given {@link Process} (default constructor).</li>
   *   <li>When {@link BpmnModel} (default constructor) addProcess {@link Process} (default constructor).</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModelValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List); given Process (default constructor); when BpmnModel (default constructor) addProcess Process (default constructor); then ArrayList() Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnModelValidator.validate(BpmnModel, List)"})
  void testValidate_givenProcess_whenBpmnModelAddProcessProcess_thenArrayListEmpty2() {
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
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link BpmnModelValidator#validate(BpmnModel, List)}.
   * <ul>
   *   <li>Given {@code Target Namespace}.</li>
   *   <li>When {@link BpmnModel} (default constructor) TargetNamespace is {@code Target Namespace}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModelValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List); given 'Target Namespace'; when BpmnModel (default constructor) TargetNamespace is 'Target Namespace'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnModelValidator.validate(BpmnModel, List)"})
  void testValidate_givenTargetNamespace_whenBpmnModelTargetNamespaceIsTargetNamespace() {
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
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link BpmnModelValidator#validate(BpmnModel, List)}.
   * <ul>
   *   <li>Given {@link ValidationError} (default constructor) ActivityId is {@code 42}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModelValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List); given ValidationError (default constructor) ActivityId is '42'; then ArrayList() size is two")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnModelValidator.validate(BpmnModel, List)"})
  void testValidate_givenValidationErrorActivityIdIs42_thenArrayListSizeIsTwo() {
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
   * <ul>
   *   <li>Given {@link Process} (default constructor).</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModelValidator#validateAtLeastOneExecutable(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validateAtLeastOneExecutable(BpmnModel, List); given Process (default constructor); then ArrayList() Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean BpmnModelValidator.validateAtLeastOneExecutable(BpmnModel, List)"})
  void testValidateAtLeastOneExecutable_givenProcess_thenArrayListEmpty() {
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
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(actualValidateAtLeastOneExecutableResult);
  }

  /**
   * Test {@link BpmnModelValidator#validateAtLeastOneExecutable(BpmnModel, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModelValidator#validateAtLeastOneExecutable(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validateAtLeastOneExecutable(BpmnModel, List); then ArrayList() size is two")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>When {@link BpmnModel} (default constructor).</li>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModelValidator#validateAtLeastOneExecutable(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validateAtLeastOneExecutable(BpmnModel, List); when BpmnModel (default constructor); then ArrayList() size is one")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>Given {@link Process} (default constructor) Id is {@code 42}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModelValidator#getProcessesWithSameId(List)}
   */
  @Test
  @DisplayName("Test getProcessesWithSameId(List); given Process (default constructor) Id is '42'; then return Empty")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>Given {@link Process} (default constructor) Name is {@code null}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModelValidator#getProcessesWithSameId(List)}
   */
  @Test
  @DisplayName("Test getProcessesWithSameId(List); given Process (default constructor) Name is 'null'; then return Empty")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>Given {@link Process} (default constructor).</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@link Process} (default constructor).</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModelValidator#getProcessesWithSameId(List)}
   */
  @Test
  @DisplayName("Test getProcessesWithSameId(List); given Process (default constructor); when ArrayList() add Process (default constructor); then return Empty")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnModelValidator#getProcessesWithSameId(List)}
   */
  @Test
  @DisplayName("Test getProcessesWithSameId(List); when ArrayList(); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List BpmnModelValidator.getProcessesWithSameId(List)"})
  void testGetProcessesWithSameId_whenArrayList_thenReturnEmpty() {
    // Arrange
    BpmnModelValidator bpmnModelValidator = new BpmnModelValidator();

    // Act and Assert
    assertTrue(bpmnModelValidator.getProcessesWithSameId(new ArrayList<>()).isEmpty());
  }
}
