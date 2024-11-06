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
import org.activiti.bpmn.model.Association;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.Signal;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AssociationValidatorDiffblueTest {
  /**
   * Test {@link AssociationValidator#validate(BpmnModel, List)} with
   * {@code bpmnModel}, {@code errors}.
   * <p>
   * Method under test: {@link AssociationValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List) with 'bpmnModel', 'errors'")
  void testValidateWithBpmnModelErrors() {
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
    Collection<FlowElement> flowElements = bpmnModel.getMainProcess().getFlowElements();
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
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
  }

  /**
   * Test {@link AssociationValidator#validate(BpmnModel, List)} with
   * {@code bpmnModel}, {@code errors}.
   * <ul>
   *   <li>Given {@link Process} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AssociationValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List) with 'bpmnModel', 'errors'; given Process (default constructor)")
  void testValidateWithBpmnModelErrors_givenProcess() {
    // Arrange
    AssociationValidator associationValidator = new AssociationValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    associationValidator.validate(bpmnModel, errors);

    // Assert that nothing has changed
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    Collection<FlowElement> flowElements = bpmnModel.getMainProcess().getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test {@link AssociationValidator#validate(BpmnModel, List)} with
   * {@code bpmnModel}, {@code errors}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} second ActivityId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AssociationValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List) with 'bpmnModel', 'errors'; then ArrayList() second ActivityId is '42'")
  void testValidateWithBpmnModelErrors_thenArrayListSecondActivityIdIs42() {
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
    ValidationError getResult = errors.get(1);
    assertEquals("42", getResult.getActivityId());
    ValidationError getResult2 = errors.get(0);
    assertEquals("42", getResult2.getActivityName());
    assertEquals("42", getResult2.getDefaultDescription());
    assertEquals("42", getResult2.getKey());
    assertEquals("42", getResult2.getProblem());
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("42", getResult2.getProcessDefinitionName());
    assertEquals("42", getResult2.getValidatorSetName());
    assertEquals("Activity Id", getResult2.getActivityId());
    assertEquals("Activity Name", getResult.getActivityName());
    assertEquals("Default Description", getResult.getDefaultDescription());
    assertEquals("Key", getResult.getKey());
    assertEquals("Problem", getResult.getProblem());
    assertEquals("Process Definition Id", getResult2.getProcessDefinitionId());
    assertEquals("Process Definition Name", getResult.getProcessDefinitionName());
    assertEquals("Validator Set Name", getResult.getValidatorSetName());
    assertEquals(1, getResult2.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult2.getXmlLineNumber());
    assertEquals(2, getResult.getXmlLineNumber());
    assertFalse(getResult2.isWarning());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Test {@link AssociationValidator#validate(BpmnModel, List)} with
   * {@code bpmnModel}, {@code errors}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link AssociationValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List) with 'bpmnModel', 'errors'; then ArrayList() size is one")
  void testValidateWithBpmnModelErrors_thenArrayListSizeIsOne() {
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
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("Activity Name", getResult.getActivityName());
    assertEquals("Default Description", getResult.getDefaultDescription());
    assertEquals("Key", getResult.getKey());
    assertEquals("Problem", getResult.getProblem());
    assertEquals("Process Definition Name", getResult.getProcessDefinitionName());
    assertEquals("Validator Set Name", getResult.getValidatorSetName());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(2, getResult.getXmlLineNumber());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Test {@link AssociationValidator#validate(BpmnModel, List)} with
   * {@code bpmnModel}, {@code errors}.
   * <ul>
   *   <li>When {@link BpmnModel} (default constructor).</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link AssociationValidator#validate(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test validate(BpmnModel, List) with 'bpmnModel', 'errors'; when BpmnModel (default constructor); then ArrayList() Empty")
  void testValidateWithBpmnModelErrors_whenBpmnModel_thenArrayListEmpty() {
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
  }

  /**
   * Test {@link AssociationValidator#validate(Process, Association, List)} with
   * {@code process}, {@code association}, {@code errors}.
   * <p>
   * Method under test:
   * {@link AssociationValidator#validate(Process, Association, List)}
   */
  @Test
  @DisplayName("Test validate(Process, Association, List) with 'process', 'association', 'errors'")
  void testValidateWithProcessAssociationErrors() {
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
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("ASSOCIATION_INVALID_TARGET_REFERENCE", getResult.getDefaultDescription());
    assertEquals("ASSOCIATION_INVALID_TARGET_REFERENCE", getResult.getKey());
    assertEquals("ASSOCIATION_INVALID_TARGET_REFERENCE", getResult.getProblem());
  }

  /**
   * Test {@link AssociationValidator#validate(Process, Association, List)} with
   * {@code process}, {@code association}, {@code errors}.
   * <ul>
   *   <li>Given empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AssociationValidator#validate(Process, Association, List)}
   */
  @Test
  @DisplayName("Test validate(Process, Association, List) with 'process', 'association', 'errors'; given empty string")
  void testValidateWithProcessAssociationErrors_givenEmptyString() {
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
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("ASSOCIATION_INVALID_SOURCE_REFERENCE", getResult.getDefaultDescription());
    assertEquals("ASSOCIATION_INVALID_SOURCE_REFERENCE", getResult.getKey());
    assertEquals("ASSOCIATION_INVALID_SOURCE_REFERENCE", getResult.getProblem());
  }

  /**
   * Test {@link AssociationValidator#validate(Process, Association, List)} with
   * {@code process}, {@code association}, {@code errors}.
   * <ul>
   *   <li>Then {@link Process} (default constructor) FlowElements
   * {@link List}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AssociationValidator#validate(Process, Association, List)}
   */
  @Test
  @DisplayName("Test validate(Process, Association, List) with 'process', 'association', 'errors'; then Process (default constructor) FlowElements List")
  void testValidateWithProcessAssociationErrors_thenProcessFlowElementsList() {
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
  }

  /**
   * Test {@link AssociationValidator#validate(Process, Association, List)} with
   * {@code process}, {@code association}, {@code errors}.
   * <ul>
   *   <li>When {@link Association} (default constructor) SourceRef is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AssociationValidator#validate(Process, Association, List)}
   */
  @Test
  @DisplayName("Test validate(Process, Association, List) with 'process', 'association', 'errors'; when Association (default constructor) SourceRef is 'null'")
  void testValidateWithProcessAssociationErrors_whenAssociationSourceRefIsNull() {
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
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("ASSOCIATION_INVALID_SOURCE_REFERENCE", getResult.getDefaultDescription());
    assertEquals("ASSOCIATION_INVALID_SOURCE_REFERENCE", getResult.getKey());
    assertEquals("ASSOCIATION_INVALID_SOURCE_REFERENCE", getResult.getProblem());
  }

  /**
   * Test {@link AssociationValidator#validate(Process, Association, List)} with
   * {@code process}, {@code association}, {@code errors}.
   * <ul>
   *   <li>When {@link Association} (default constructor).</li>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AssociationValidator#validate(Process, Association, List)}
   */
  @Test
  @DisplayName("Test validate(Process, Association, List) with 'process', 'association', 'errors'; when Association (default constructor); then ArrayList() size is two")
  void testValidateWithProcessAssociationErrors_whenAssociation_thenArrayListSizeIsTwo() {
    // Arrange
    AssociationValidator associationValidator = new AssociationValidator();
    Process process = new Process();
    Association association = new Association();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    associationValidator.validate(process, association, errors);

    // Assert
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
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
    assertTrue(getResult.getParams().isEmpty());
  }
}
