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
import java.util.List;
import org.activiti.bpmn.model.Activity;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.DataAssociation;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.MultiInstanceLoopCharacteristics;
import org.activiti.bpmn.model.Process;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FlowElementValidatorDiffblueTest {
  /**
   * Test
   * {@link FlowElementValidator#handleMultiInstanceLoopCharacteristics(Process, Activity, List)}.
   * <p>
   * Method under test:
   * {@link FlowElementValidator#handleMultiInstanceLoopCharacteristics(Process, Activity, List)}
   */
  @Test
  @DisplayName("Test handleMultiInstanceLoopCharacteristics(Process, Activity, List)")
  void testHandleMultiInstanceLoopCharacteristics() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    Process process = new Process();

    MultiInstanceLoopCharacteristics loopCharacteristics = new MultiInstanceLoopCharacteristics();
    loopCharacteristics.setInputDataItem(null);
    loopCharacteristics.setLoopCardinality("Activity");

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
   * Test
   * {@link FlowElementValidator#handleMultiInstanceLoopCharacteristics(Process, Activity, List)}.
   * <p>
   * Method under test:
   * {@link FlowElementValidator#handleMultiInstanceLoopCharacteristics(Process, Activity, List)}
   */
  @Test
  @DisplayName("Test handleMultiInstanceLoopCharacteristics(Process, Activity, List)")
  void testHandleMultiInstanceLoopCharacteristics2() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    Process process = new Process();

    MultiInstanceLoopCharacteristics loopCharacteristics = new MultiInstanceLoopCharacteristics();
    loopCharacteristics.setInputDataItem("Activity");
    loopCharacteristics.setLoopCardinality(null);

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
   * Test
   * {@link FlowElementValidator#handleMultiInstanceLoopCharacteristics(Process, Activity, List)}.
   * <p>
   * Method under test:
   * {@link FlowElementValidator#handleMultiInstanceLoopCharacteristics(Process, Activity, List)}
   */
  @Test
  @DisplayName("Test handleMultiInstanceLoopCharacteristics(Process, Activity, List)")
  void testHandleMultiInstanceLoopCharacteristics3() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    Process process = new Process();

    MultiInstanceLoopCharacteristics loopCharacteristics = new MultiInstanceLoopCharacteristics();
    loopCharacteristics.setInputDataItem(null);
    loopCharacteristics.setLoopCardinality("");

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
   * Test
   * {@link FlowElementValidator#handleMultiInstanceLoopCharacteristics(Process, Activity, List)}.
   * <ul>
   *   <li>Given {@link MultiInstanceLoopCharacteristics} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link FlowElementValidator#handleMultiInstanceLoopCharacteristics(Process, Activity, List)}
   */
  @Test
  @DisplayName("Test handleMultiInstanceLoopCharacteristics(Process, Activity, List); given MultiInstanceLoopCharacteristics (default constructor)")
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
   * Test
   * {@link FlowElementValidator#handleMultiInstanceLoopCharacteristics(Process, Activity, List)}.
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link FlowElementValidator#handleMultiInstanceLoopCharacteristics(Process, Activity, List)}
   */
  @Test
  @DisplayName("Test handleMultiInstanceLoopCharacteristics(Process, Activity, List); when AdhocSubProcess (default constructor)")
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
   * Test
   * {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}.
   * <ul>
   *   <li>Given {@link DataAssociation} (default constructor) TargetRef is
   * {@code Activity}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}
   */
  @Test
  @DisplayName("Test handleDataAssociations(Process, Activity, List); given DataAssociation (default constructor) TargetRef is 'Activity'")
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
   * Test
   * {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}.
   * <ul>
   *   <li>Given {@link DataAssociation} (default constructor) TargetRef is
   * {@code Activity}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}
   */
  @Test
  @DisplayName("Test handleDataAssociations(Process, Activity, List); given DataAssociation (default constructor) TargetRef is 'Activity'")
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
   * Test
   * {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}.
   * <ul>
   *   <li>Given {@link DataAssociation} (default constructor) TargetRef is empty
   * string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}
   */
  @Test
  @DisplayName("Test handleDataAssociations(Process, Activity, List); given DataAssociation (default constructor) TargetRef is empty string")
  void testHandleDataAssociations_givenDataAssociationTargetRefIsEmptyString() {
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
   * Test
   * {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}
   */
  @Test
  @DisplayName("Test handleDataAssociations(Process, Activity, List); then ArrayList() size is one")
  void testHandleDataAssociations_thenArrayListSizeIsOne() {
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
   * Test
   * {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}
   */
  @Test
  @DisplayName("Test handleDataAssociations(Process, Activity, List); then ArrayList() size is one")
  void testHandleDataAssociations_thenArrayListSizeIsOne2() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    Process process = new Process();

    DataAssociation dataAssociation = new DataAssociation();
    dataAssociation.setTargetRef(null);

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
   * Test
   * {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}
   */
  @Test
  @DisplayName("Test handleDataAssociations(Process, Activity, List); then ArrayList() size is two")
  void testHandleDataAssociations_thenArrayListSizeIsTwo() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    Process process = new Process();

    DataAssociation dataAssociation = new DataAssociation();
    dataAssociation.setTargetRef(null);

    ArrayList<DataAssociation> dataInputAssociations = new ArrayList<>();
    dataInputAssociations.add(dataAssociation);

    DataAssociation dataAssociation2 = new DataAssociation();
    dataAssociation2.setTargetRef(null);

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
   * Test
   * {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}.
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor) DataOutputAssociations
   * is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}
   */
  @Test
  @DisplayName("Test handleDataAssociations(Process, Activity, List); when AdhocSubProcess (default constructor) DataOutputAssociations is 'null'")
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
   * Test
   * {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}.
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).</li>
   *   <li>Then {@link AdhocSubProcess} (default constructor) Artifacts
   * {@link List}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}
   */
  @Test
  @DisplayName("Test handleDataAssociations(Process, Activity, List); when AdhocSubProcess (default constructor); then AdhocSubProcess (default constructor) Artifacts List")
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
