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
import java.util.List;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.Signal;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ExecutionListenerValidatorDiffblueTest {
  /**
   * Test {@link ExecutionListenerValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor) Implementation is {@code Process}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionListenerValidator#executeValidation(BpmnModel, Process,
   * List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given ActivitiListener (default constructor) Implementation is 'Process'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionListenerValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenActivitiListenerImplementationIsProcess() {
    // Arrange
    ExecutionListenerValidator executionListenerValidator = new ExecutionListenerValidator();
    BpmnModel bpmnModel = new BpmnModel();

    SequenceFlow element = new SequenceFlow("Source Ref", "Target Ref");
    element.setExecutionListeners(null);

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setImplementation("Process");
    activitiListener.setImplementationType(null);
    activitiListener.setOnTransaction(null);

    ArrayList<ActivitiListener> executionListeners = new ArrayList<>();
    executionListeners.add(activitiListener);

    Process process = new Process();
    process.addFlowElement(element);
    process.setExecutionListeners(executionListeners);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    executionListenerValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    Collection<Artifact> artifacts = process.getArtifacts();
    assertTrue(artifacts instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getDefaultDescription());
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getKey());
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getProblem());
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
    assertTrue(artifacts.isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link ExecutionListenerValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor) ImplementationType is {@code
   *       Process}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionListenerValidator#executeValidation(BpmnModel, Process,
   * List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given ActivitiListener (default constructor) ImplementationType is 'Process'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionListenerValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenActivitiListenerImplementationTypeIsProcess() {
    // Arrange
    ExecutionListenerValidator executionListenerValidator = new ExecutionListenerValidator();
    BpmnModel bpmnModel = new BpmnModel();

    SequenceFlow element = new SequenceFlow("Source Ref", "Target Ref");
    element.setExecutionListeners(null);

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setImplementation("Process");
    activitiListener.setImplementationType("Process");
    activitiListener.setOnTransaction(null);

    ArrayList<ActivitiListener> executionListeners = new ArrayList<>();
    executionListeners.add(activitiListener);

    Process process = new Process();
    process.addFlowElement(element);
    process.setExecutionListeners(executionListeners);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    executionListenerValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    Collection<Artifact> artifacts = process.getArtifacts();
    assertTrue(artifacts instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(artifacts.isEmpty());
  }

  /**
   * Test {@link ExecutionListenerValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor) OnTransaction is {@code Process}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionListenerValidator#executeValidation(BpmnModel, Process,
   * List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given ActivitiListener (default constructor) OnTransaction is 'Process'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionListenerValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenActivitiListenerOnTransactionIsProcess() {
    // Arrange
    ExecutionListenerValidator executionListenerValidator = new ExecutionListenerValidator();
    BpmnModel bpmnModel = new BpmnModel();

    SequenceFlow element = new SequenceFlow("Source Ref", "Target Ref");
    element.setExecutionListeners(null);

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setImplementation(null);
    activitiListener.setImplementationType(null);
    activitiListener.setOnTransaction("Process");

    ArrayList<ActivitiListener> executionListeners = new ArrayList<>();
    executionListeners.add(activitiListener);

    Process process = new Process();
    process.addFlowElement(element);
    process.setExecutionListeners(executionListeners);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    executionListenerValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    Collection<Artifact> artifacts = process.getArtifacts();
    assertTrue(artifacts instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getDefaultDescription());
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getKey());
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getProblem());
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
    assertTrue(artifacts.isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link ExecutionListenerValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link Process} (default constructor) ExecutionListeners is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionListenerValidator#executeValidation(BpmnModel, Process,
   * List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given 'null'; when Process (default constructor) ExecutionListeners is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionListenerValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenNull_whenProcessExecutionListenersIsNull() {
    // Arrange
    ExecutionListenerValidator executionListenerValidator = new ExecutionListenerValidator();
    BpmnModel bpmnModel = new BpmnModel();

    SequenceFlow element = new SequenceFlow("Source Ref", "Target Ref");
    element.setExecutionListeners(null);

    Process process = new Process();
    process.addFlowElement(element);
    process.setExecutionListeners(null);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    executionListenerValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    Collection<Artifact> artifacts = process.getArtifacts();
    assertTrue(artifacts instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(artifacts.isEmpty());
  }

  /**
   * Test {@link ExecutionListenerValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionListenerValidator#executeValidation(BpmnModel, Process,
   * List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionListenerValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_thenArrayListSizeIsOne() {
    // Arrange
    ExecutionListenerValidator executionListenerValidator = new ExecutionListenerValidator();
    BpmnModel bpmnModel = new BpmnModel();

    SequenceFlow element = new SequenceFlow("Source Ref", "Target Ref");
    element.setExecutionListeners(null);

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setImplementation(null);
    activitiListener.setImplementationType(null);
    activitiListener.setOnTransaction(null);

    ArrayList<ActivitiListener> executionListeners = new ArrayList<>();
    executionListeners.add(activitiListener);

    Process process = new Process();
    process.addFlowElement(element);
    process.setExecutionListeners(executionListeners);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    executionListenerValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    Collection<Artifact> artifacts = process.getArtifacts();
    assertTrue(artifacts instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getDefaultDescription());
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getKey());
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getProblem());
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
    assertTrue(artifacts.isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link ExecutionListenerValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionListenerValidator#executeValidation(BpmnModel, Process,
   * List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionListenerValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_thenArrayListSizeIsTwo() {
    // Arrange
    ExecutionListenerValidator executionListenerValidator = new ExecutionListenerValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setImplementation(null);
    activitiListener.setImplementationType(null);
    activitiListener.setOnTransaction(null);

    ArrayList<ActivitiListener> executionListeners = new ArrayList<>();
    executionListeners.add(activitiListener);

    SequenceFlow element = new SequenceFlow("Source Ref", "Target Ref");
    element.setExecutionListeners(executionListeners);

    ActivitiListener activitiListener2 = new ActivitiListener();
    activitiListener2.setImplementation(null);
    activitiListener2.setImplementationType(null);
    activitiListener2.setOnTransaction(null);

    ArrayList<ActivitiListener> executionListeners2 = new ArrayList<>();
    executionListeners2.add(activitiListener2);

    Process process = new Process();
    process.addFlowElement(element);
    process.setExecutionListeners(executionListeners2);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    executionListenerValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getDefaultDescription());
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getKey());
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getProblem());
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
   * Test {@link ExecutionListenerValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>When {@link Process} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionListenerValidator#executeValidation(BpmnModel, Process,
   * List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); when Process (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExecutionListenerValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_whenProcess_thenArrayListEmpty() {
    // Arrange
    ExecutionListenerValidator executionListenerValidator = new ExecutionListenerValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    executionListenerValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    Collection<Artifact> artifacts = process.getArtifacts();
    assertTrue(artifacts instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(artifacts.isEmpty());
  }

  /**
   * Test {@link ExecutionListenerValidator#validateListeners(Process, BaseElement, List, List)}.
   *
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor) Implementation is {@code Listeners}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionListenerValidator#validateListeners(Process, BaseElement,
   * List, List)}
   */
  @Test
  @DisplayName(
      "Test validateListeners(Process, BaseElement, List, List); given ActivitiListener (default constructor) Implementation is 'Listeners'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionListenerValidator.validateListeners(Process, BaseElement, List, List)"
  })
  void testValidateListeners_givenActivitiListenerImplementationIsListeners() {
    // Arrange
    ExecutionListenerValidator executionListenerValidator = new ExecutionListenerValidator();
    Process process = new Process();
    ActivitiListener baseElement = new ActivitiListener();

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setImplementation("Listeners");
    activitiListener.setImplementationType(null);
    activitiListener.setOnTransaction(null);

    ArrayList<ActivitiListener> listeners = new ArrayList<>();
    listeners.add(activitiListener);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    executionListenerValidator.validateListeners(process, baseElement, listeners, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getDefaultDescription());
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getKey());
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getProblem());
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
   * Test {@link ExecutionListenerValidator#validateListeners(Process, BaseElement, List, List)}.
   *
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor) Implementation is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionListenerValidator#validateListeners(Process, BaseElement,
   * List, List)}
   */
  @Test
  @DisplayName(
      "Test validateListeners(Process, BaseElement, List, List); given ActivitiListener (default constructor) Implementation is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionListenerValidator.validateListeners(Process, BaseElement, List, List)"
  })
  void testValidateListeners_givenActivitiListenerImplementationIsNull() {
    // Arrange
    ExecutionListenerValidator executionListenerValidator = new ExecutionListenerValidator();
    Process process = new Process();
    ActivitiListener baseElement = new ActivitiListener();

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setImplementation(null);
    activitiListener.setImplementationType(null);
    activitiListener.setOnTransaction(null);

    ArrayList<ActivitiListener> listeners = new ArrayList<>();
    listeners.add(activitiListener);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    executionListenerValidator.validateListeners(process, baseElement, listeners, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getDefaultDescription());
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getKey());
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getProblem());
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
   * Test {@link ExecutionListenerValidator#validateListeners(Process, BaseElement, List, List)}.
   *
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor) ImplementationType is {@code
   *       Listeners}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionListenerValidator#validateListeners(Process, BaseElement,
   * List, List)}
   */
  @Test
  @DisplayName(
      "Test validateListeners(Process, BaseElement, List, List); given ActivitiListener (default constructor) ImplementationType is 'Listeners'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionListenerValidator.validateListeners(Process, BaseElement, List, List)"
  })
  void testValidateListeners_givenActivitiListenerImplementationTypeIsListeners() {
    // Arrange
    ExecutionListenerValidator executionListenerValidator = new ExecutionListenerValidator();
    Process process = new Process();
    ActivitiListener baseElement = new ActivitiListener();

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setImplementation("Listeners");
    activitiListener.setImplementationType("Listeners");
    activitiListener.setOnTransaction(null);

    ArrayList<ActivitiListener> listeners = new ArrayList<>();
    listeners.add(activitiListener);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    executionListenerValidator.validateListeners(process, baseElement, listeners, errors);

    // Assert that nothing has changed
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test {@link ExecutionListenerValidator#validateListeners(Process, BaseElement, List, List)}.
   *
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor) OnTransaction is {@code Listeners}.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionListenerValidator#validateListeners(Process, BaseElement,
   * List, List)}
   */
  @Test
  @DisplayName(
      "Test validateListeners(Process, BaseElement, List, List); given ActivitiListener (default constructor) OnTransaction is 'Listeners'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionListenerValidator.validateListeners(Process, BaseElement, List, List)"
  })
  void testValidateListeners_givenActivitiListenerOnTransactionIsListeners() {
    // Arrange
    ExecutionListenerValidator executionListenerValidator = new ExecutionListenerValidator();
    Process process = new Process();
    ActivitiListener baseElement = new ActivitiListener();

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setImplementation(null);
    activitiListener.setImplementationType(null);
    activitiListener.setOnTransaction("Listeners");

    ArrayList<ActivitiListener> listeners = new ArrayList<>();
    listeners.add(activitiListener);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    executionListenerValidator.validateListeners(process, baseElement, listeners, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getDefaultDescription());
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getKey());
    assertEquals("EXECUTION_LISTENER_IMPLEMENTATION_MISSING", getResult.getProblem());
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
   * Test {@link ExecutionListenerValidator#validateListeners(Process, BaseElement, List, List)}.
   *
   * <ul>
   *   <li>When {@link ActivitiListener} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionListenerValidator#validateListeners(Process, BaseElement,
   * List, List)}
   */
  @Test
  @DisplayName(
      "Test validateListeners(Process, BaseElement, List, List); when ActivitiListener (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionListenerValidator.validateListeners(Process, BaseElement, List, List)"
  })
  void testValidateListeners_whenActivitiListener_thenArrayListEmpty() {
    // Arrange
    ExecutionListenerValidator executionListenerValidator = new ExecutionListenerValidator();
    Process process = new Process();
    ActivitiListener baseElement = new ActivitiListener();
    ArrayList<ActivitiListener> listeners = new ArrayList<>();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    executionListenerValidator.validateListeners(process, baseElement, listeners, errors);

    // Assert that nothing has changed
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test {@link ExecutionListenerValidator#validateListeners(Process, BaseElement, List, List)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link ExecutionListenerValidator#validateListeners(Process, BaseElement,
   * List, List)}
   */
  @Test
  @DisplayName(
      "Test validateListeners(Process, BaseElement, List, List); when 'null'; then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ExecutionListenerValidator.validateListeners(Process, BaseElement, List, List)"
  })
  void testValidateListeners_whenNull_thenArrayListEmpty() {
    // Arrange
    ExecutionListenerValidator executionListenerValidator = new ExecutionListenerValidator();
    Process process = new Process();
    ActivitiListener baseElement = new ActivitiListener();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    executionListenerValidator.validateListeners(process, baseElement, null, errors);

    // Assert that nothing has changed
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
  }
}
