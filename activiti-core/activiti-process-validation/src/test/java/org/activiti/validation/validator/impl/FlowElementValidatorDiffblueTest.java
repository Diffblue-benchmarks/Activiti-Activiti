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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.activiti.bpmn.model.Activity;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.BooleanDataObject;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.BusinessRuleTask;
import org.activiti.bpmn.model.DataAssociation;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowElementsContainer;
import org.activiti.bpmn.model.MultiInstanceLoopCharacteristics;
import org.activiti.bpmn.model.Process;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class FlowElementValidatorDiffblueTest {
  /**
   * Test {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}.
   * <p>
   * Method under test: {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void FlowElementValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    BpmnModel bpmnModel = new BpmnModel();
    BusinessRuleTask element = mock(BusinessRuleTask.class);
    when(element.getXmlColumnNumber()).thenReturn(10);
    when(element.getXmlRowNumber()).thenReturn(10);
    when(element.getName()).thenReturn("Name");
    when(element.getId()).thenReturn("42");
    when(element.getDataInputAssociations()).thenReturn(new ArrayList<>());
    when(element.getDataOutputAssociations()).thenReturn(new ArrayList<>());
    when(element.getLoopCharacteristics()).thenReturn(new MultiInstanceLoopCharacteristics());
    doNothing().when(element).setParentContainer(Mockito.<FlowElementsContainer>any());

    Process process = new Process();
    process.addFlowElement(element);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(element, atLeast(1)).getDataInputAssociations();
    verify(element, atLeast(1)).getDataOutputAssociations();
    verify(element).getLoopCharacteristics();
    verify(element, atLeast(1)).getId();
    verify(element).getXmlColumnNumber();
    verify(element).getXmlRowNumber();
    verify(element).getName();
    verify(element).setParentContainer(isA(FlowElementsContainer.class));
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("MULTI_INSTANCE_MISSING_COLLECTION", getResult.getDefaultDescription());
    assertEquals("MULTI_INSTANCE_MISSING_COLLECTION", getResult.getKey());
    assertEquals("MULTI_INSTANCE_MISSING_COLLECTION", getResult.getProblem());
    assertEquals("Name", getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}.
   * <p>
   * Method under test: {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void FlowElementValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation2() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    BpmnModel bpmnModel = new BpmnModel();
    MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics = mock(MultiInstanceLoopCharacteristics.class);
    when(multiInstanceLoopCharacteristics.getLoopCardinality()).thenReturn("Loop Cardinality");
    BusinessRuleTask element = mock(BusinessRuleTask.class);
    when(element.getId()).thenReturn("42");
    when(element.getDataInputAssociations()).thenReturn(new ArrayList<>());
    when(element.getDataOutputAssociations()).thenReturn(new ArrayList<>());
    when(element.getLoopCharacteristics()).thenReturn(multiInstanceLoopCharacteristics);
    doNothing().when(element).setParentContainer(Mockito.<FlowElementsContainer>any());

    Process process = new Process();
    process.addFlowElement(element);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(element, atLeast(1)).getDataInputAssociations();
    verify(element, atLeast(1)).getDataOutputAssociations();
    verify(element).getLoopCharacteristics();
    verify(element, atLeast(1)).getId();
    verify(element).setParentContainer(isA(FlowElementsContainer.class));
    verify(multiInstanceLoopCharacteristics).getLoopCardinality();
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}.
   * <p>
   * Method under test: {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void FlowElementValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation3() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    BpmnModel bpmnModel = new BpmnModel();
    MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics = mock(MultiInstanceLoopCharacteristics.class);
    when(multiInstanceLoopCharacteristics.getInputDataItem()).thenReturn("Input Data Item");
    when(multiInstanceLoopCharacteristics.getLoopCardinality()).thenReturn(null);
    BusinessRuleTask element = mock(BusinessRuleTask.class);
    when(element.getId()).thenReturn("42");
    when(element.getDataInputAssociations()).thenReturn(new ArrayList<>());
    when(element.getDataOutputAssociations()).thenReturn(new ArrayList<>());
    when(element.getLoopCharacteristics()).thenReturn(multiInstanceLoopCharacteristics);
    doNothing().when(element).setParentContainer(Mockito.<FlowElementsContainer>any());

    Process process = new Process();
    process.addFlowElement(element);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(element, atLeast(1)).getDataInputAssociations();
    verify(element, atLeast(1)).getDataOutputAssociations();
    verify(element).getLoopCharacteristics();
    verify(element, atLeast(1)).getId();
    verify(element).setParentContainer(isA(FlowElementsContainer.class));
    verify(multiInstanceLoopCharacteristics).getInputDataItem();
    verify(multiInstanceLoopCharacteristics).getLoopCardinality();
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}.
   * <p>
   * Method under test: {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void FlowElementValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation4() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    BpmnModel bpmnModel = new BpmnModel();
    MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics = mock(MultiInstanceLoopCharacteristics.class);
    when(multiInstanceLoopCharacteristics.getInputDataItem()).thenReturn("Input Data Item");
    when(multiInstanceLoopCharacteristics.getLoopCardinality()).thenReturn("");
    BusinessRuleTask element = mock(BusinessRuleTask.class);
    when(element.getId()).thenReturn("42");
    when(element.getDataInputAssociations()).thenReturn(new ArrayList<>());
    when(element.getDataOutputAssociations()).thenReturn(new ArrayList<>());
    when(element.getLoopCharacteristics()).thenReturn(multiInstanceLoopCharacteristics);
    doNothing().when(element).setParentContainer(Mockito.<FlowElementsContainer>any());

    Process process = new Process();
    process.addFlowElement(element);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(element, atLeast(1)).getDataInputAssociations();
    verify(element, atLeast(1)).getDataOutputAssociations();
    verify(element).getLoopCharacteristics();
    verify(element, atLeast(1)).getId();
    verify(element).setParentContainer(isA(FlowElementsContainer.class));
    verify(multiInstanceLoopCharacteristics).getInputDataItem();
    verify(multiInstanceLoopCharacteristics).getLoopCardinality();
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given AdhocSubProcess (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void FlowElementValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenAdhocSubProcess() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    BpmnModel bpmnModel = new BpmnModel();

    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link BooleanDataObject} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given BooleanDataObject (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void FlowElementValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenBooleanDataObject() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    BpmnModel bpmnModel = new BpmnModel();

    Process process = new Process();
    process.addFlowElement(new BooleanDataObject());
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link BusinessRuleTask} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given BusinessRuleTask (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void FlowElementValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenBusinessRuleTask() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    BpmnModel bpmnModel = new BpmnModel();

    Process process = new Process();
    process.addFlowElement(new BusinessRuleTask());
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} first ActivityName is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() first ActivityName is '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void FlowElementValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_thenArrayListFirstActivityNameIs42() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
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
    flowElementValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getActivityName());
    assertEquals("42", getResult.getDefaultDescription());
    assertEquals("42", getResult.getKey());
    assertEquals("42", getResult.getProblem());
    assertEquals("42", getResult.getProcessDefinitionName());
    assertEquals("42", getResult.getValidatorSetName());
    assertEquals("Activity Id", getResult.getActivityId());
    assertEquals("Process Definition Id", getResult.getProcessDefinitionId());
    assertEquals(1, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} first ProcessDefinitionId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() first ProcessDefinitionId is '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void FlowElementValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_thenArrayListFirstProcessDefinitionIdIs42() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
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
    flowElementValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
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
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Test {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} second ActivityId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() second ActivityId is '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void FlowElementValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_thenArrayListSecondActivityIdIs42() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<DataAssociation> dataAssociationList = new ArrayList<>();
    dataAssociationList.add(new DataAssociation());
    BusinessRuleTask element = mock(BusinessRuleTask.class);
    when(element.getXmlColumnNumber()).thenReturn(10);
    when(element.getXmlRowNumber()).thenReturn(10);
    when(element.getName()).thenReturn("Name");
    when(element.getId()).thenReturn("42");
    when(element.getDataInputAssociations()).thenReturn(dataAssociationList);
    when(element.getDataOutputAssociations()).thenReturn(new ArrayList<>());
    when(element.getLoopCharacteristics()).thenReturn(new MultiInstanceLoopCharacteristics());
    doNothing().when(element).setParentContainer(Mockito.<FlowElementsContainer>any());

    Process process = new Process();
    process.addFlowElement(element);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(element, atLeast(1)).getDataInputAssociations();
    verify(element, atLeast(1)).getDataOutputAssociations();
    verify(element).getLoopCharacteristics();
    verify(element, atLeast(1)).getId();
    verify(element, atLeast(1)).getXmlColumnNumber();
    verify(element, atLeast(1)).getXmlRowNumber();
    verify(element, atLeast(1)).getName();
    verify(element).setParentContainer(isA(FlowElementsContainer.class));
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("42", getResult.getActivityId());
    assertEquals("DATA_ASSOCIATION_MISSING_TARGETREF", getResult.getDefaultDescription());
    assertEquals("DATA_ASSOCIATION_MISSING_TARGETREF", getResult.getKey());
    assertEquals("DATA_ASSOCIATION_MISSING_TARGETREF", getResult.getProblem());
    assertEquals("Name", getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} second ActivityId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() second ActivityId is '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void FlowElementValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_thenArrayListSecondActivityIdIs422() {
    // Arrange
    FlowElementValidator flowElementValidator = new FlowElementValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<DataAssociation> dataAssociationList = new ArrayList<>();
    dataAssociationList.add(new DataAssociation());
    BusinessRuleTask element = mock(BusinessRuleTask.class);
    when(element.getXmlColumnNumber()).thenReturn(10);
    when(element.getXmlRowNumber()).thenReturn(10);
    when(element.getName()).thenReturn("Name");
    when(element.getId()).thenReturn("42");
    when(element.getDataInputAssociations()).thenReturn(new ArrayList<>());
    when(element.getDataOutputAssociations()).thenReturn(dataAssociationList);
    when(element.getLoopCharacteristics()).thenReturn(new MultiInstanceLoopCharacteristics());
    doNothing().when(element).setParentContainer(Mockito.<FlowElementsContainer>any());

    Process process = new Process();
    process.addFlowElement(element);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    flowElementValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(element, atLeast(1)).getDataInputAssociations();
    verify(element, atLeast(1)).getDataOutputAssociations();
    verify(element).getLoopCharacteristics();
    verify(element, atLeast(1)).getId();
    verify(element, atLeast(1)).getXmlColumnNumber();
    verify(element, atLeast(1)).getXmlRowNumber();
    verify(element, atLeast(1)).getName();
    verify(element).setParentContainer(isA(FlowElementsContainer.class));
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("42", getResult.getActivityId());
    assertEquals("DATA_ASSOCIATION_MISSING_TARGETREF", getResult.getDefaultDescription());
    assertEquals("DATA_ASSOCIATION_MISSING_TARGETREF", getResult.getKey());
    assertEquals("DATA_ASSOCIATION_MISSING_TARGETREF", getResult.getProblem());
    assertEquals("Name", getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>When {@link Process} (default constructor).</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowElementValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); when Process (default constructor); then ArrayList() Empty")
  @Tag("MaintainedByDiffblue")
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
   * Test {@link FlowElementValidator#handleMultiInstanceLoopCharacteristics(Process, Activity, List)}.
   * <p>
   * Method under test: {@link FlowElementValidator#handleMultiInstanceLoopCharacteristics(Process, Activity, List)}
   */
  @Test
  @DisplayName("Test handleMultiInstanceLoopCharacteristics(Process, Activity, List)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void FlowElementValidator.handleMultiInstanceLoopCharacteristics(Process, Activity, List)"})
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
   * Test {@link FlowElementValidator#handleMultiInstanceLoopCharacteristics(Process, Activity, List)}.
   * <p>
   * Method under test: {@link FlowElementValidator#handleMultiInstanceLoopCharacteristics(Process, Activity, List)}
   */
  @Test
  @DisplayName("Test handleMultiInstanceLoopCharacteristics(Process, Activity, List)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void FlowElementValidator.handleMultiInstanceLoopCharacteristics(Process, Activity, List)"})
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
   * Test {@link FlowElementValidator#handleMultiInstanceLoopCharacteristics(Process, Activity, List)}.
   * <p>
   * Method under test: {@link FlowElementValidator#handleMultiInstanceLoopCharacteristics(Process, Activity, List)}
   */
  @Test
  @DisplayName("Test handleMultiInstanceLoopCharacteristics(Process, Activity, List)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void FlowElementValidator.handleMultiInstanceLoopCharacteristics(Process, Activity, List)"})
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
   * Test {@link FlowElementValidator#handleMultiInstanceLoopCharacteristics(Process, Activity, List)}.
   * <ul>
   *   <li>Given {@link MultiInstanceLoopCharacteristics} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowElementValidator#handleMultiInstanceLoopCharacteristics(Process, Activity, List)}
   */
  @Test
  @DisplayName("Test handleMultiInstanceLoopCharacteristics(Process, Activity, List); given MultiInstanceLoopCharacteristics (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void FlowElementValidator.handleMultiInstanceLoopCharacteristics(Process, Activity, List)"})
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
   * Test {@link FlowElementValidator#handleMultiInstanceLoopCharacteristics(Process, Activity, List)}.
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowElementValidator#handleMultiInstanceLoopCharacteristics(Process, Activity, List)}
   */
  @Test
  @DisplayName("Test handleMultiInstanceLoopCharacteristics(Process, Activity, List); when AdhocSubProcess (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void FlowElementValidator.handleMultiInstanceLoopCharacteristics(Process, Activity, List)"})
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
   * <ul>
   *   <li>Given {@link DataAssociation} (default constructor) TargetRef is {@code Activity}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}
   */
  @Test
  @DisplayName("Test handleDataAssociations(Process, Activity, List); given DataAssociation (default constructor) TargetRef is 'Activity'")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>Given {@link DataAssociation} (default constructor) TargetRef is {@code Activity}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}
   */
  @Test
  @DisplayName("Test handleDataAssociations(Process, Activity, List); given DataAssociation (default constructor) TargetRef is 'Activity'")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>Given {@link DataAssociation} (default constructor) TargetRef is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}
   */
  @Test
  @DisplayName("Test handleDataAssociations(Process, Activity, List); given DataAssociation (default constructor) TargetRef is empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void FlowElementValidator.handleDataAssociations(Process, Activity, List)"})
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
   * Test {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}
   */
  @Test
  @DisplayName("Test handleDataAssociations(Process, Activity, List); then ArrayList() size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void FlowElementValidator.handleDataAssociations(Process, Activity, List)"})
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
   * Test {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}
   */
  @Test
  @DisplayName("Test handleDataAssociations(Process, Activity, List); then ArrayList() size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void FlowElementValidator.handleDataAssociations(Process, Activity, List)"})
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
   * Test {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}
   */
  @Test
  @DisplayName("Test handleDataAssociations(Process, Activity, List); then ArrayList() size is two")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void FlowElementValidator.handleDataAssociations(Process, Activity, List)"})
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
   * Test {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}.
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor) DataOutputAssociations is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}
   */
  @Test
  @DisplayName("Test handleDataAssociations(Process, Activity, List); when AdhocSubProcess (default constructor) DataOutputAssociations is 'null'")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).</li>
   *   <li>Then {@link AdhocSubProcess} (default constructor) Artifacts {@link List}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FlowElementValidator#handleDataAssociations(Process, Activity, List)}
   */
  @Test
  @DisplayName("Test handleDataAssociations(Process, Activity, List); when AdhocSubProcess (default constructor); then AdhocSubProcess (default constructor) Artifacts List")
  @Tag("MaintainedByDiffblue")
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
