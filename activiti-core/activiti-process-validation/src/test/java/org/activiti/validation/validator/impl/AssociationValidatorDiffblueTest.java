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
import org.activiti.bpmn.model.Association;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.Signal;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.Test;

class AssociationValidatorDiffblueTest {
  /**
   * Method under test: {@link AssociationValidator#validate(BpmnModel, List)}
   */
  @Test
  void testValidate() {
    // Arrange
    AssociationValidator associationValidator = new AssociationValidator();
    BpmnModel bpmnModel = new BpmnModel();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    associationValidator.validate(bpmnModel, errors);

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
  }

  /**
   * Method under test: {@link AssociationValidator#validate(BpmnModel, List)}
   */
  @Test
  void testValidate2() {
    // Arrange
    AssociationValidator associationValidator = new AssociationValidator();

    BpmnModel bpmnModel = new BpmnModel();
    Process process = new Process();
    bpmnModel.addProcess(process);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    associationValidator.validate(bpmnModel, errors);

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
    assertSame(process, bpmnModel.getMainProcess());
  }

  /**
   * Method under test: {@link AssociationValidator#validate(BpmnModel, List)}
   */
  @Test
  void testValidate3() {
    // Arrange
    AssociationValidator associationValidator = new AssociationValidator();
    BpmnModel bpmnModel = new BpmnModel();

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
    associationValidator.validate(bpmnModel, errors);

    // Assert that nothing has changed
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, errors.size());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertSame(validationError, errors.get(0));
  }

  /**
   * Method under test: {@link AssociationValidator#validate(BpmnModel, List)}
   */
  @Test
  void testValidate4() {
    // Arrange
    AssociationValidator associationValidator = new AssociationValidator();
    BpmnModel bpmnModel = new BpmnModel();

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
    associationValidator.validate(bpmnModel, errors);

    // Assert that nothing has changed
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(2, errors.size());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertSame(validationError2, errors.get(0));
    assertSame(validationError, errors.get(1));
  }

  /**
   * Method under test: {@link AssociationValidator#validate(BpmnModel, List)}
   */
  @Test
  void testValidate5() {
    // Arrange
    AssociationValidator associationValidator = new AssociationValidator();

    Process process = new Process();
    process.addArtifact(new Association());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    associationValidator.validate(bpmnModel, errors);

    // Assert
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("ASSOCIATION_INVALID_SOURCE_REFERENCE", getResult.getDefaultDescription());
    assertEquals("ASSOCIATION_INVALID_SOURCE_REFERENCE", getResult.getKey());
    assertEquals("ASSOCIATION_INVALID_SOURCE_REFERENCE", getResult.getProblem());
    ValidationError getResult2 = errors.get(1);
    assertEquals("ASSOCIATION_INVALID_TARGET_REFERENCE", getResult2.getDefaultDescription());
    assertEquals("ASSOCIATION_INVALID_TARGET_REFERENCE", getResult2.getKey());
    assertEquals("ASSOCIATION_INVALID_TARGET_REFERENCE", getResult2.getProblem());
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
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
    assertSame(process, bpmnModel.getMainProcess());
  }

  /**
   * Method under test:
   * {@link AssociationValidator#validate(Process, Association, List)}
   */
  @Test
  void testValidate6() {
    // Arrange
    AssociationValidator associationValidator = new AssociationValidator();
    Process process = new Process();
    Association association = new Association();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    associationValidator.validate(process, association, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("ASSOCIATION_INVALID_SOURCE_REFERENCE", getResult.getDefaultDescription());
    assertEquals("ASSOCIATION_INVALID_SOURCE_REFERENCE", getResult.getKey());
    assertEquals("ASSOCIATION_INVALID_SOURCE_REFERENCE", getResult.getProblem());
    ValidationError getResult2 = errors.get(1);
    assertEquals("ASSOCIATION_INVALID_TARGET_REFERENCE", getResult2.getDefaultDescription());
    assertEquals("ASSOCIATION_INVALID_TARGET_REFERENCE", getResult2.getKey());
    assertEquals("ASSOCIATION_INVALID_TARGET_REFERENCE", getResult2.getProblem());
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
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link AssociationValidator#validate(Process, Association, List)}
   */
  @Test
  void testValidate7() {
    // Arrange
    AssociationValidator associationValidator = new AssociationValidator();
    Process process = new Process();

    Association association = new Association();
    association.setSourceRef(null);
    association.setTargetRef("Association");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    associationValidator.validate(process, association, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("ASSOCIATION_INVALID_SOURCE_REFERENCE", getResult.getDefaultDescription());
    assertEquals("ASSOCIATION_INVALID_SOURCE_REFERENCE", getResult.getKey());
    assertEquals("ASSOCIATION_INVALID_SOURCE_REFERENCE", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
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
   * {@link AssociationValidator#validate(Process, Association, List)}
   */
  @Test
  void testValidate8() {
    // Arrange
    AssociationValidator associationValidator = new AssociationValidator();
    Process process = new Process();

    Association association = new Association();
    association.setSourceRef("Association");
    association.setTargetRef(null);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    associationValidator.validate(process, association, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("ASSOCIATION_INVALID_TARGET_REFERENCE", getResult.getDefaultDescription());
    assertEquals("ASSOCIATION_INVALID_TARGET_REFERENCE", getResult.getKey());
    assertEquals("ASSOCIATION_INVALID_TARGET_REFERENCE", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
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
   * {@link AssociationValidator#validate(Process, Association, List)}
   */
  @Test
  void testValidate9() {
    // Arrange
    AssociationValidator associationValidator = new AssociationValidator();
    Process process = new Process();

    Association association = new Association();
    association.setSourceRef("Association");
    association.setTargetRef("Association");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    associationValidator.validate(process, association, errors);

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
   * {@link AssociationValidator#validate(Process, Association, List)}
   */
  @Test
  void testValidate10() {
    // Arrange
    AssociationValidator associationValidator = new AssociationValidator();
    Process process = new Process();

    Association association = new Association();
    association.setSourceRef("");
    association.setTargetRef("Association");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    associationValidator.validate(process, association, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("ASSOCIATION_INVALID_SOURCE_REFERENCE", getResult.getDefaultDescription());
    assertEquals("ASSOCIATION_INVALID_SOURCE_REFERENCE", getResult.getKey());
    assertEquals("ASSOCIATION_INVALID_SOURCE_REFERENCE", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
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
}
