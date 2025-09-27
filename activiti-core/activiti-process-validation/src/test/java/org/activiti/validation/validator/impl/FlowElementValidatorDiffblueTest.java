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
import org.activiti.bpmn.model.Activity;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.BooleanDataObject;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.BusinessRuleTask;
import org.activiti.bpmn.model.DataAssociation;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.MultiInstanceLoopCharacteristics;
import org.activiti.bpmn.model.Process;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class FlowElementValidatorDiffblueTest {
  /**
   * Test {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <p>Method under test: {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElementValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    BpmnModel bpmnModel = new BpmnModel();

    BusinessRuleTask element = new BusinessRuleTask();
    element.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    element.setDataInputAssociations(null);
    element.setDataOutputAssociations(null);

    Process process = new Process();
    process.addFlowElement(element);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("MULTI_INSTANCE_MISSING_COLLECTION", getResult.getDefaultDescription());
    assertEquals("MULTI_INSTANCE_MISSING_COLLECTION", getResult.getKey());
    assertEquals("MULTI_INSTANCE_MISSING_COLLECTION", getResult.getProblem());
  }

  /**
   * Test {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <p>Method under test: {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElementValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation2() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(new DataAssociation());

    BusinessRuleTask element = new BusinessRuleTask();
    element.setLoopCharacteristics(null);
    element.setDataInputAssociations(dataInputAssociations);
    element.setDataOutputAssociations(null);

    Process process = new Process();
    process.addFlowElement(element);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("DATA_ASSOCIATION_MISSING_TARGETREF", getResult.getDefaultDescription());
    assertEquals("DATA_ASSOCIATION_MISSING_TARGETREF", getResult.getKey());
    assertEquals("DATA_ASSOCIATION_MISSING_TARGETREF", getResult.getProblem());
  }

  /**
   * Test {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <p>Method under test: {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElementValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation3() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(new DataAssociation());

    BusinessRuleTask element = new BusinessRuleTask();
    element.setLoopCharacteristics(null);
    element.setDataInputAssociations(null);
    element.setDataOutputAssociations(dataOutputAssociations);

    Process process = new Process();
    process.addFlowElement(element);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("DATA_ASSOCIATION_MISSING_TARGETREF", getResult.getDefaultDescription());
    assertEquals("DATA_ASSOCIATION_MISSING_TARGETREF", getResult.getKey());
    assertEquals("DATA_ASSOCIATION_MISSING_TARGETREF", getResult.getProblem());
  }

  /**
   * Test {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <p>Method under test: {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElementValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation4() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    BpmnModel bpmnModel = new BpmnModel();

    MultiInstanceLoopCharacteristics loopCharacteristics = new MultiInstanceLoopCharacteristics();
    loopCharacteristics.setInputDataItem("MULTI_INSTANCE_MISSING_COLLECTION");

    BusinessRuleTask element = new BusinessRuleTask();
    element.setLoopCharacteristics(loopCharacteristics);
    element.setDataInputAssociations(null);
    element.setDataOutputAssociations(null);

    Process process = new Process();
    process.addFlowElement(element);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <p>Method under test: {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElementValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation5() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    BpmnModel bpmnModel = new BpmnModel();

    MultiInstanceLoopCharacteristics loopCharacteristics = new MultiInstanceLoopCharacteristics();
    loopCharacteristics.setLoopCardinality("MULTI_INSTANCE_MISSING_COLLECTION");

    BusinessRuleTask element = new BusinessRuleTask();
    element.setLoopCharacteristics(loopCharacteristics);
    element.setDataInputAssociations(null);
    element.setDataOutputAssociations(null);

    Process process = new Process();
    process.addFlowElement(element);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <p>Method under test: {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElementValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation6() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    BpmnModel bpmnModel = new BpmnModel();

    MultiInstanceLoopCharacteristics loopCharacteristics = new MultiInstanceLoopCharacteristics();
    loopCharacteristics.setInputDataItem("");

    BusinessRuleTask element = new BusinessRuleTask();
    element.setLoopCharacteristics(loopCharacteristics);
    element.setDataInputAssociations(null);
    element.setDataOutputAssociations(null);

    Process process = new Process();
    process.addFlowElement(element);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("MULTI_INSTANCE_MISSING_COLLECTION", getResult.getDefaultDescription());
    assertEquals("MULTI_INSTANCE_MISSING_COLLECTION", getResult.getKey());
    assertEquals("MULTI_INSTANCE_MISSING_COLLECTION", getResult.getProblem());
  }

  /**
   * Test {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <p>Method under test: {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElementValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation7() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    BpmnModel bpmnModel = new BpmnModel();

    DataAssociation dataAssociation = new DataAssociation();
    dataAssociation.setTargetRef("DATA_ASSOCIATION_MISSING_TARGETREF");

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(dataAssociation);

    BusinessRuleTask element = new BusinessRuleTask();
    element.setLoopCharacteristics(null);
    element.setDataInputAssociations(dataInputAssociations);
    element.setDataOutputAssociations(null);

    Process process = new Process();
    process.addFlowElement(element);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <p>Method under test: {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElementValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation8() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    BpmnModel bpmnModel = new BpmnModel();

    DataAssociation dataAssociation = new DataAssociation();
    dataAssociation.setTargetRef("DATA_ASSOCIATION_MISSING_TARGETREF");

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(dataAssociation);

    BusinessRuleTask element = new BusinessRuleTask();
    element.setLoopCharacteristics(null);
    element.setDataInputAssociations(null);
    element.setDataOutputAssociations(dataOutputAssociations);

    Process process = new Process();
    process.addFlowElement(element);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) addFlowElement {@link
   *       AdhocSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given AdhocSubProcess (default constructor) addFlowElement AdhocSubProcess (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElementValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenAdhocSubProcessAddFlowElementAdhocSubProcess() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    BpmnModel bpmnModel = new BpmnModel();

    AdhocSubProcess element = new AdhocSubProcess();
    element.addFlowElement(new AdhocSubProcess());
    element.setLoopCharacteristics(null);
    element.setDataInputAssociations(null);
    element.setDataOutputAssociations(null);

    Process process = new Process();
    process.addFlowElement(element);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) addFlowElement {@link
   *       BooleanDataObject} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given AdhocSubProcess (default constructor) addFlowElement BooleanDataObject (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElementValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenAdhocSubProcessAddFlowElementBooleanDataObject() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    BpmnModel bpmnModel = new BpmnModel();

    AdhocSubProcess element = new AdhocSubProcess();
    element.addFlowElement(new BooleanDataObject());
    element.setLoopCharacteristics(null);
    element.setDataInputAssociations(null);
    element.setDataOutputAssociations(null);

    Process process = new Process();
    process.addFlowElement(element);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) LoopCharacteristics is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given AdhocSubProcess (default constructor) LoopCharacteristics is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElementValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenAdhocSubProcessLoopCharacteristicsIsNull() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    BpmnModel bpmnModel = new BpmnModel();

    AdhocSubProcess element = new AdhocSubProcess();
    element.setLoopCharacteristics(null);
    element.setDataInputAssociations(null);
    element.setDataOutputAssociations(null);

    Process process = new Process();
    process.addFlowElement(element);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link DataAssociation} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given ArrayList() add DataAssociation (default constructor); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElementValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenArrayListAddDataAssociation_thenArrayListSizeIsTwo() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(new DataAssociation());
    dataInputAssociations.add(new DataAssociation());

    BusinessRuleTask element = new BusinessRuleTask();
    element.setLoopCharacteristics(null);
    element.setDataInputAssociations(dataInputAssociations);
    element.setDataOutputAssociations(null);

    Process process = new Process();
    process.addFlowElement(element);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("DATA_ASSOCIATION_MISSING_TARGETREF", getResult.getDefaultDescription());
    assertEquals("DATA_ASSOCIATION_MISSING_TARGETREF", getResult.getKey());
    assertEquals("DATA_ASSOCIATION_MISSING_TARGETREF", getResult.getProblem());
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
   * Test {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link BusinessRuleTask} (default constructor) DataInputAssociations is {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given BusinessRuleTask (default constructor) DataInputAssociations is ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElementValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenBusinessRuleTaskDataInputAssociationsIsArrayList() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    BpmnModel bpmnModel = new BpmnModel();

    BusinessRuleTask element = new BusinessRuleTask();
    element.setLoopCharacteristics(null);
    element.setDataInputAssociations(new ArrayList<>());
    element.setDataOutputAssociations(null);

    Process process = new Process();
    process.addFlowElement(element);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link BusinessRuleTask} (default constructor) DataInputAssociations is {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given BusinessRuleTask (default constructor) DataInputAssociations is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElementValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenBusinessRuleTaskDataInputAssociationsIsNull() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    BpmnModel bpmnModel = new BpmnModel();

    BusinessRuleTask element = new BusinessRuleTask();
    element.setLoopCharacteristics(null);
    element.setDataInputAssociations(null);
    element.setDataOutputAssociations(null);

    Process process = new Process();
    process.addFlowElement(element);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link BusinessRuleTask} (default constructor) DataOutputAssociations is {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given BusinessRuleTask (default constructor) DataOutputAssociations is ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElementValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenBusinessRuleTaskDataOutputAssociationsIsArrayList() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    BpmnModel bpmnModel = new BpmnModel();

    BusinessRuleTask element = new BusinessRuleTask();
    element.setLoopCharacteristics(null);
    element.setDataInputAssociations(null);
    element.setDataOutputAssociations(new ArrayList<>());

    Process process = new Process();
    process.addFlowElement(element);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link BusinessRuleTask} (default constructor) Id is {@code 42}.
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given BusinessRuleTask (default constructor) Id is '42'; then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElementValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenBusinessRuleTaskIdIs42_thenArrayListEmpty() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    BpmnModel bpmnModel = new BpmnModel();

    BusinessRuleTask element = new BusinessRuleTask();
    element.setId("42");
    element.setLoopCharacteristics(null);
    element.setDataInputAssociations(null);
    element.setDataOutputAssociations(null);

    Process process = new Process();
    process.addFlowElement(element);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>When {@link Process} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); when Process (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElementValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_whenProcess_thenArrayListEmpty() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link FlowElementValidator#handleMultiInstanceLoopCharacteristics(Process, Activity,
   * List)}.
   *
   * <p>Method under test: {@link
   * FlowElementValidator#handleMultiInstanceLoopCharacteristics(Process, Activity, List)}
   */
  @Test
  @DisplayName("Test handleMultiInstanceLoopCharacteristics(Process, Activity, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void FlowElementValidator.handleMultiInstanceLoopCharacteristics(Process, Activity, List)"
  })
  void testHandleMultiInstanceLoopCharacteristics() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    Process process = new Process();

    MultiInstanceLoopCharacteristics loopCharacteristics = new MultiInstanceLoopCharacteristics();
    loopCharacteristics.setLoopCardinality("");
    loopCharacteristics.setInputDataItem("Activity");

    AdhocSubProcess activity = new AdhocSubProcess();
    activity.setLoopCharacteristics(loopCharacteristics);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.handleMultiInstanceLoopCharacteristics(process, activity, errors);

    // Assert that nothing has changed
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    Collection<Artifact> artifacts = activity.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements2 = activity.getFlowElements();
    assertTrue(flowElements2 instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements2.isEmpty());
  }

  /**
   * Test {@link FlowElementValidator#handleMultiInstanceLoopCharacteristics(Process, Activity,
   * List)}.
   *
   * <p>Method under test: {@link
   * FlowElementValidator#handleMultiInstanceLoopCharacteristics(Process, Activity, List)}
   */
  @Test
  @DisplayName("Test handleMultiInstanceLoopCharacteristics(Process, Activity, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void FlowElementValidator.handleMultiInstanceLoopCharacteristics(Process, Activity, List)"
  })
  void testHandleMultiInstanceLoopCharacteristics2() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    Process process = new Process();

    MultiInstanceLoopCharacteristics loopCharacteristics = new MultiInstanceLoopCharacteristics();
    loopCharacteristics.setLoopCardinality("Activity");
    loopCharacteristics.setInputDataItem("");

    AdhocSubProcess activity = new AdhocSubProcess();
    activity.setLoopCharacteristics(loopCharacteristics);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.handleMultiInstanceLoopCharacteristics(process, activity, errors);

    // Assert that nothing has changed
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    Collection<Artifact> artifacts = activity.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements2 = activity.getFlowElements();
    assertTrue(flowElements2 instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements2.isEmpty());
  }

  /**
   * Test {@link FlowElementValidator#handleMultiInstanceLoopCharacteristics(Process, Activity,
   * List)}.
   *
   * <ul>
   *   <li>Given {@link MultiInstanceLoopCharacteristics} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link
   * FlowElementValidator#handleMultiInstanceLoopCharacteristics(Process, Activity, List)}
   */
  @Test
  @DisplayName(
      "Test handleMultiInstanceLoopCharacteristics(Process, Activity, List); given MultiInstanceLoopCharacteristics (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void FlowElementValidator.handleMultiInstanceLoopCharacteristics(Process, Activity, List)"
  })
  void testHandleMultiInstanceLoopCharacteristics_givenMultiInstanceLoopCharacteristics() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    Process process = new Process();

    AdhocSubProcess activity = new AdhocSubProcess();
    activity.setLoopCharacteristics(new MultiInstanceLoopCharacteristics());
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.handleMultiInstanceLoopCharacteristics(process, activity, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    Collection<FlowElement> flowElements2 = activity.getFlowElements();
    assertTrue(flowElements2 instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("MULTI_INSTANCE_MISSING_COLLECTION", getResult.getDefaultDescription());
    assertEquals("MULTI_INSTANCE_MISSING_COLLECTION", getResult.getKey());
    assertEquals("MULTI_INSTANCE_MISSING_COLLECTION", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(flowElements2.isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link FlowElementValidator#handleMultiInstanceLoopCharacteristics(Process, Activity,
   * List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * FlowElementValidator#handleMultiInstanceLoopCharacteristics(Process, Activity, List)}
   */
  @Test
  @DisplayName(
      "Test handleMultiInstanceLoopCharacteristics(Process, Activity, List); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void FlowElementValidator.handleMultiInstanceLoopCharacteristics(Process, Activity, List)"
  })
  void testHandleMultiInstanceLoopCharacteristics_thenArrayListSizeIsOne() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    Process process = new Process();

    MultiInstanceLoopCharacteristics loopCharacteristics = new MultiInstanceLoopCharacteristics();
    loopCharacteristics.setLoopCardinality("");
    loopCharacteristics.setInputDataItem("");

    AdhocSubProcess activity = new AdhocSubProcess();
    activity.setLoopCharacteristics(loopCharacteristics);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.handleMultiInstanceLoopCharacteristics(process, activity, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    Collection<FlowElement> flowElements2 = activity.getFlowElements();
    assertTrue(flowElements2 instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("MULTI_INSTANCE_MISSING_COLLECTION", getResult.getDefaultDescription());
    assertEquals("MULTI_INSTANCE_MISSING_COLLECTION", getResult.getKey());
    assertEquals("MULTI_INSTANCE_MISSING_COLLECTION", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(flowElements2.isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link FlowElementValidator#handleMultiInstanceLoopCharacteristics(Process, Activity,
   * List)}.
   *
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link
   * FlowElementValidator#handleMultiInstanceLoopCharacteristics(Process, Activity, List)}
   */
  @Test
  @DisplayName(
      "Test handleMultiInstanceLoopCharacteristics(Process, Activity, List); when AdhocSubProcess (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void FlowElementValidator.handleMultiInstanceLoopCharacteristics(Process, Activity, List)"
  })
  void testHandleMultiInstanceLoopCharacteristics_whenAdhocSubProcess() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    Process process = new Process();
    AdhocSubProcess activity = new AdhocSubProcess();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.handleMultiInstanceLoopCharacteristics(process, activity, errors);

    // Assert that nothing has changed
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    Collection<Artifact> artifacts = activity.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements2 = activity.getFlowElements();
    assertTrue(flowElements2 instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements2.isEmpty());
  }

  /**
   * Test {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}.
   *
   * <ul>
   *   <li>Given {@link DataAssociation} (default constructor) TargetRef is {@code Activity}.
   * </ul>
   *
   * <p>Method under test: {@link FlowElementValidator#handleDataAssociations(Process, Activity,
   * List)}
   */
  @Test
  @DisplayName(
      "Test handleDataAssociations(Process, Activity, List); given DataAssociation (default constructor) TargetRef is 'Activity'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElementValidator.handleDataAssociations(Process, Activity, List)"})
  void testHandleDataAssociations_givenDataAssociationTargetRefIsActivity() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    Process process = new Process();

    DataAssociation dataAssociation = new DataAssociation();
    dataAssociation.setTargetRef("Activity");

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(dataAssociation);

    AdhocSubProcess activity = new AdhocSubProcess();
    activity.setDataInputAssociations(null);
    activity.setDataOutputAssociations(dataOutputAssociations);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.handleDataAssociations(process, activity, errors);

    // Assert that nothing has changed
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    Collection<Artifact> artifacts = activity.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements2 = activity.getFlowElements();
    assertTrue(flowElements2 instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements2.isEmpty());
  }

  /**
   * Test {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}.
   *
   * <ul>
   *   <li>Given {@link DataAssociation} (default constructor) TargetRef is {@code Activity}.
   * </ul>
   *
   * <p>Method under test: {@link FlowElementValidator#handleDataAssociations(Process, Activity,
   * List)}
   */
  @Test
  @DisplayName(
      "Test handleDataAssociations(Process, Activity, List); given DataAssociation (default constructor) TargetRef is 'Activity'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElementValidator.handleDataAssociations(Process, Activity, List)"})
  void testHandleDataAssociations_givenDataAssociationTargetRefIsActivity2() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    Process process = new Process();

    DataAssociation dataAssociation = new DataAssociation();
    dataAssociation.setTargetRef("Activity");

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(dataAssociation);

    AdhocSubProcess activity = new AdhocSubProcess();
    activity.setDataInputAssociations(dataInputAssociations);
    activity.setDataOutputAssociations(null);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.handleDataAssociations(process, activity, errors);

    // Assert that nothing has changed
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    Collection<Artifact> artifacts = activity.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements2 = activity.getFlowElements();
    assertTrue(flowElements2 instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements2.isEmpty());
  }

  /**
   * Test {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}.
   *
   * <ul>
   *   <li>Given {@link DataAssociation} (default constructor) TargetRef is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link FlowElementValidator#handleDataAssociations(Process, Activity,
   * List)}
   */
  @Test
  @DisplayName(
      "Test handleDataAssociations(Process, Activity, List); given DataAssociation (default constructor) TargetRef is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElementValidator.handleDataAssociations(Process, Activity, List)"})
  void testHandleDataAssociations_givenDataAssociationTargetRefIsNull() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    Process process = new Process();

    DataAssociation dataAssociation = new DataAssociation();
    dataAssociation.setTargetRef(null);

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(dataAssociation);

    AdhocSubProcess activity = new AdhocSubProcess();
    activity.setDataInputAssociations(null);
    activity.setDataOutputAssociations(dataOutputAssociations);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.handleDataAssociations(process, activity, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    Collection<FlowElement> flowElements2 = activity.getFlowElements();
    assertTrue(flowElements2 instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("DATA_ASSOCIATION_MISSING_TARGETREF", getResult.getDefaultDescription());
    assertEquals("DATA_ASSOCIATION_MISSING_TARGETREF", getResult.getKey());
    assertEquals("DATA_ASSOCIATION_MISSING_TARGETREF", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(flowElements2.isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link FlowElementValidator#handleDataAssociations(Process, Activity,
   * List)}
   */
  @Test
  @DisplayName("Test handleDataAssociations(Process, Activity, List); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElementValidator.handleDataAssociations(Process, Activity, List)"})
  void testHandleDataAssociations_thenArrayListSizeIsOne() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    Process process = new Process();

    DataAssociation dataAssociation = new DataAssociation();
    dataAssociation.setTargetRef("");

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(dataAssociation);

    AdhocSubProcess activity = new AdhocSubProcess();
    activity.setDataInputAssociations(null);
    activity.setDataOutputAssociations(dataOutputAssociations);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.handleDataAssociations(process, activity, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    Collection<FlowElement> flowElements2 = activity.getFlowElements();
    assertTrue(flowElements2 instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("DATA_ASSOCIATION_MISSING_TARGETREF", getResult.getDefaultDescription());
    assertEquals("DATA_ASSOCIATION_MISSING_TARGETREF", getResult.getKey());
    assertEquals("DATA_ASSOCIATION_MISSING_TARGETREF", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(flowElements2.isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link FlowElementValidator#handleDataAssociations(Process, Activity,
   * List)}
   */
  @Test
  @DisplayName("Test handleDataAssociations(Process, Activity, List); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElementValidator.handleDataAssociations(Process, Activity, List)"})
  void testHandleDataAssociations_thenArrayListSizeIsOne2() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    Process process = new Process();

    DataAssociation dataAssociation = new DataAssociation();
    dataAssociation.setTargetRef("");

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(dataAssociation);

    AdhocSubProcess activity = new AdhocSubProcess();
    activity.setDataInputAssociations(dataInputAssociations);
    activity.setDataOutputAssociations(null);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.handleDataAssociations(process, activity, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    Collection<FlowElement> flowElements2 = activity.getFlowElements();
    assertTrue(flowElements2 instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("DATA_ASSOCIATION_MISSING_TARGETREF", getResult.getDefaultDescription());
    assertEquals("DATA_ASSOCIATION_MISSING_TARGETREF", getResult.getKey());
    assertEquals("DATA_ASSOCIATION_MISSING_TARGETREF", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(flowElements2.isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link FlowElementValidator#handleDataAssociations(Process, Activity,
   * List)}
   */
  @Test
  @DisplayName("Test handleDataAssociations(Process, Activity, List); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElementValidator.handleDataAssociations(Process, Activity, List)"})
  void testHandleDataAssociations_thenArrayListSizeIsTwo() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    Process process = new Process();

    DataAssociation dataAssociation = new DataAssociation();
    dataAssociation.setTargetRef("");

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(dataAssociation);

    DataAssociation dataAssociation2 = new DataAssociation();
    dataAssociation2.setTargetRef("");

    ArrayList<DataAssociation> dataOutputAssociations = new ArrayList<>();
    dataOutputAssociations.add(dataAssociation2);

    AdhocSubProcess activity = new AdhocSubProcess();
    activity.setDataInputAssociations(dataInputAssociations);
    activity.setDataOutputAssociations(dataOutputAssociations);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.handleDataAssociations(process, activity, errors);

    // Assert
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("DATA_ASSOCIATION_MISSING_TARGETREF", getResult.getDefaultDescription());
    assertEquals("DATA_ASSOCIATION_MISSING_TARGETREF", getResult.getKey());
    assertEquals("DATA_ASSOCIATION_MISSING_TARGETREF", getResult.getProblem());
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
   * Test {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}.
   *
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor) DataOutputAssociations is {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link FlowElementValidator#handleDataAssociations(Process, Activity,
   * List)}
   */
  @Test
  @DisplayName(
      "Test handleDataAssociations(Process, Activity, List); when AdhocSubProcess (default constructor) DataOutputAssociations is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElementValidator.handleDataAssociations(Process, Activity, List)"})
  void testHandleDataAssociations_whenAdhocSubProcessDataOutputAssociationsIsNull() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    Process process = new Process();

    AdhocSubProcess activity = new AdhocSubProcess();
    activity.setDataInputAssociations(null);
    activity.setDataOutputAssociations(null);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.handleDataAssociations(process, activity, errors);

    // Assert that nothing has changed
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    Collection<Artifact> artifacts = activity.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements2 = activity.getFlowElements();
    assertTrue(flowElements2 instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements2.isEmpty());
  }

  /**
   * Test {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}.
   *
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).
   *   <li>Then {@link AdhocSubProcess} (default constructor) Artifacts {@link List}.
   * </ul>
   *
   * <p>Method under test: {@link FlowElementValidator#handleDataAssociations(Process, Activity,
   * List)}
   */
  @Test
  @DisplayName(
      "Test handleDataAssociations(Process, Activity, List); when AdhocSubProcess (default constructor); then AdhocSubProcess (default constructor) Artifacts List")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void FlowElementValidator.handleDataAssociations(Process, Activity, List)"})
  void testHandleDataAssociations_whenAdhocSubProcess_thenAdhocSubProcessArtifactsList() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    Process process = new Process();
    AdhocSubProcess activity = new AdhocSubProcess();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.handleDataAssociations(process, activity, errors);

    // Assert that nothing has changed
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    Collection<Artifact> artifacts = activity.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements2 = activity.getFlowElements();
    assertTrue(flowElements2 instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements2.isEmpty());
  }
}
