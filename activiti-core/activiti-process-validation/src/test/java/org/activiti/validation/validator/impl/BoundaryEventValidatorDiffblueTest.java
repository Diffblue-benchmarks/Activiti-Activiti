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
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.BooleanDataObject;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.Signal;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.Test;

class BoundaryEventValidatorDiffblueTest {
  /**
   * Method under test:
   * {@link BoundaryEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation() {
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
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
  }

  /**
   * Method under test:
   * {@link BoundaryEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation2() {
    // Arrange
    BoundaryEventValidator boundaryEventValidator = new BoundaryEventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    Process process = new Process();
    AdhocSubProcess element = new AdhocSubProcess();
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
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(artifacts.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
  }

  /**
   * Method under test:
   * {@link BoundaryEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation3() {
    // Arrange
    BoundaryEventValidator boundaryEventValidator = new BoundaryEventValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = new Process();

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
    boundaryEventValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    Collection<Artifact> artifacts = process.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, errors.size());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertSame(validationError, errors.get(0));
  }

  /**
   * Method under test:
   * {@link BoundaryEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation4() {
    // Arrange
    BoundaryEventValidator boundaryEventValidator = new BoundaryEventValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = new Process();

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
    validationError2.setXmlColumnNumber(1);
    validationError2.setXmlLineNumber(10);

    ArrayList<ValidationError> errors = new ArrayList<>();
    errors.add(validationError2);
    errors.add(validationError);

    // Act
    boundaryEventValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    Collection<Artifact> artifacts = process.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(2, errors.size());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertSame(validationError2, errors.get(0));
  }

  /**
   * Method under test:
   * {@link BoundaryEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation5() {
    // Arrange
    BoundaryEventValidator boundaryEventValidator = new BoundaryEventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    Process process = new Process();
    BooleanDataObject element = new BooleanDataObject();
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
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(artifacts.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
  }

  /**
   * Method under test:
   * {@link BoundaryEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation6() {
    // Arrange
    BoundaryEventValidator boundaryEventValidator = new BoundaryEventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    Process process = new Process();
    BoundaryEvent element = new BoundaryEvent();
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
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
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
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
  }

  /**
   * Method under test:
   * {@link BoundaryEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation7() {
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

    // Assert
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    Collection<Artifact> artifacts = process.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(artifacts.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertSame(element, ((List<FlowElement>) flowElements).get(0));
  }
}
