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
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.BooleanDataObject;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.CancelEventDefinition;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.Signal;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class BoundaryEventValidatorDiffblueTest {
  /**
   * Test {@link BoundaryEventValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <p>Method under test: {@link BoundaryEventValidator#executeValidation(BpmnModel, Process,
   * List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BoundaryEventValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation() {
    // Arrange
    BoundaryEventValidator boundaryEventValidator = new BoundaryEventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    BoundaryEvent element = new BoundaryEvent();
    element.addEventDefinition(new CancelEventDefinition());

    Process process = new Process();
    process.addFlowElement(element);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    boundaryEventValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    Collection<Artifact> artifacts = process.getArtifacts();
    assertTrue(artifacts instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("BOUNDARY_EVENT_CANCEL_ONLY_ON_TRANSACTION", getResult.getDefaultDescription());
    assertEquals("BOUNDARY_EVENT_CANCEL_ONLY_ON_TRANSACTION", getResult.getKey());
    assertEquals("BOUNDARY_EVENT_CANCEL_ONLY_ON_TRANSACTION", getResult.getProblem());
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
   * Test {@link BoundaryEventValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BoundaryEventValidator#executeValidation(BpmnModel, Process,
   * List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given AdhocSubProcess (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BoundaryEventValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenAdhocSubProcess() {
    // Arrange
    BoundaryEventValidator boundaryEventValidator = new BoundaryEventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    boundaryEventValidator.executeValidation(bpmnModel, process, errors);

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
   * Test {@link BoundaryEventValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) addFlowElement {@link
   *       AdhocSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BoundaryEventValidator#executeValidation(BpmnModel, Process,
   * List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given AdhocSubProcess (default constructor) addFlowElement AdhocSubProcess (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BoundaryEventValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenAdhocSubProcessAddFlowElementAdhocSubProcess() {
    // Arrange
    BoundaryEventValidator boundaryEventValidator = new BoundaryEventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    AdhocSubProcess element = new AdhocSubProcess();
    element.addFlowElement(new AdhocSubProcess());

    Process process = new Process();
    process.addFlowElement(element);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    boundaryEventValidator.executeValidation(bpmnModel, process, errors);

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
   * Test {@link BoundaryEventValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) addFlowElement {@link
   *       BooleanDataObject} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BoundaryEventValidator#executeValidation(BpmnModel, Process,
   * List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given AdhocSubProcess (default constructor) addFlowElement BooleanDataObject (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BoundaryEventValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenAdhocSubProcessAddFlowElementBooleanDataObject() {
    // Arrange
    BoundaryEventValidator boundaryEventValidator = new BoundaryEventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    AdhocSubProcess element = new AdhocSubProcess();
    element.addFlowElement(new BooleanDataObject());

    Process process = new Process();
    process.addFlowElement(element);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    boundaryEventValidator.executeValidation(bpmnModel, process, errors);

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
   * Test {@link BoundaryEventValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link BooleanDataObject} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BoundaryEventValidator#executeValidation(BpmnModel, Process,
   * List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given BooleanDataObject (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BoundaryEventValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenBooleanDataObject() {
    // Arrange
    BoundaryEventValidator boundaryEventValidator = new BoundaryEventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    Process process = new Process();
    process.addFlowElement(new BooleanDataObject());
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    boundaryEventValidator.executeValidation(bpmnModel, process, errors);

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
   * Test {@link BoundaryEventValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link BoundaryEvent} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BoundaryEventValidator#executeValidation(BpmnModel, Process,
   * List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given BoundaryEvent (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BoundaryEventValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenBoundaryEvent() {
    // Arrange
    BoundaryEventValidator boundaryEventValidator = new BoundaryEventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    Process process = new Process();
    process.addFlowElement(new BoundaryEvent());
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    boundaryEventValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    Collection<Artifact> artifacts = process.getArtifacts();
    assertTrue(artifacts instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("BOUNDARY_EVENT_NO_EVENT_DEFINITION", getResult.getDefaultDescription());
    assertEquals("BOUNDARY_EVENT_NO_EVENT_DEFINITION", getResult.getKey());
    assertEquals("BOUNDARY_EVENT_NO_EVENT_DEFINITION", getResult.getProblem());
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
   * Test {@link BoundaryEventValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor).
   *   <li>Then {@link BpmnModel} (default constructor) MainProcess Artifacts {@link List}.
   * </ul>
   *
   * <p>Method under test: {@link BoundaryEventValidator#executeValidation(BpmnModel, Process,
   * List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given Process (default constructor); then BpmnModel (default constructor) MainProcess Artifacts List")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BoundaryEventValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenProcess_thenBpmnModelMainProcessArtifactsList() {
    // Arrange
    BoundaryEventValidator boundaryEventValidator = new BoundaryEventValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());

    BoundaryEvent element = new BoundaryEvent();
    element.addEventDefinition(new CancelEventDefinition());

    Process process = new Process();
    process.addFlowElement(element);

    // Act
    boundaryEventValidator.executeValidation(bpmnModel, process, new ArrayList<>());

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
   * Test {@link BoundaryEventValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Then {@link BpmnModel} (default constructor) MainProcess FlowElements size is one.
   * </ul>
   *
   * <p>Method under test: {@link BoundaryEventValidator#executeValidation(BpmnModel, Process,
   * List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); then BpmnModel (default constructor) MainProcess FlowElements size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BoundaryEventValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_thenBpmnModelMainProcessFlowElementsSizeIsOne() {
    // Arrange
    BoundaryEventValidator boundaryEventValidator = new BoundaryEventValidator();

    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    BoundaryEvent element = new BoundaryEvent();
    element.addEventDefinition(new CancelEventDefinition());

    Process process2 = new Process();
    process2.addFlowElement(element);

    // Act
    boundaryEventValidator.executeValidation(bpmnModel, process2, new ArrayList<>());

    // Assert
    Collection<FlowElement> flowElements = bpmnModel.getMainProcess().getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    FlowElement getResult = ((List<FlowElement>) flowElements).get(0);
    Collection<Artifact> artifacts = ((AdhocSubProcess) getResult).getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements2 = ((AdhocSubProcess) getResult).getFlowElements();
    assertTrue(flowElements2 instanceof List);
    assertTrue(getResult instanceof AdhocSubProcess);
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements2.isEmpty());
  }

  /**
   * Test {@link BoundaryEventValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Then {@link Process} (default constructor) FlowElements size is one.
   * </ul>
   *
   * <p>Method under test: {@link BoundaryEventValidator#executeValidation(BpmnModel, Process,
   * List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); then Process (default constructor) FlowElements size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BoundaryEventValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_thenProcessFlowElementsSizeIsOne() {
    // Arrange
    BoundaryEventValidator boundaryEventValidator = new BoundaryEventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    AdhocSubProcess element = new AdhocSubProcess();
    element.addFlowElement(new BoundaryEvent());

    Process process = new Process();
    process.addFlowElement(element);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    boundaryEventValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    FlowElement getResult = ((List<FlowElement>) flowElements).get(0);
    Collection<Artifact> artifacts = ((AdhocSubProcess) getResult).getArtifacts();
    assertTrue(artifacts instanceof List);
    assertTrue(getResult instanceof AdhocSubProcess);
    assertEquals(1, errors.size());
    ValidationError getResult2 = errors.get(0);
    assertEquals("BOUNDARY_EVENT_NO_EVENT_DEFINITION", getResult2.getDefaultDescription());
    assertEquals("BOUNDARY_EVENT_NO_EVENT_DEFINITION", getResult2.getKey());
    assertEquals("BOUNDARY_EVENT_NO_EVENT_DEFINITION", getResult2.getProblem());
    assertTrue(artifacts.isEmpty());
  }

  /**
   * Test {@link BoundaryEventValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>When {@link Process} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link BoundaryEventValidator#executeValidation(BpmnModel, Process,
   * List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); when Process (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BoundaryEventValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_whenProcess_thenArrayListEmpty() {
    // Arrange
    BoundaryEventValidator boundaryEventValidator = new BoundaryEventValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    boundaryEventValidator.executeValidation(bpmnModel, process, errors);

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
}
