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
package org.activiti.spring.boot.process.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
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
import org.activiti.bpmn.model.FlowElementsContainer;
import org.activiti.bpmn.model.Process;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AsyncPropertyValidator.class})
@ExtendWith(SpringExtension.class)
class AsyncPropertyValidatorDiffblueTest {
  @Autowired private AsyncPropertyValidator asyncPropertyValidator;

  /**
   * Test {@link AsyncPropertyValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link AsyncPropertyValidator#executeValidation(BpmnModel, Process,
   * List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given AdhocSubProcess (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AsyncPropertyValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenAdhocSubProcess() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    asyncPropertyValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link AsyncPropertyValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link BooleanDataObject} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link AsyncPropertyValidator#executeValidation(BpmnModel, Process,
   * List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given BooleanDataObject (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AsyncPropertyValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenBooleanDataObject() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    Process process = new Process();
    process.addFlowElement(new BooleanDataObject());
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    asyncPropertyValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link AsyncPropertyValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link BoundaryEvent} (default constructor).
   *   <li>When {@link Process} (default constructor) addFlowElement {@link BoundaryEvent} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link AsyncPropertyValidator#executeValidation(BpmnModel, Process,
   * List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given BoundaryEvent (default constructor); when Process (default constructor) addFlowElement BoundaryEvent (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AsyncPropertyValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenBoundaryEvent_whenProcessAddFlowElementBoundaryEvent() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    Process process = new Process();
    process.addFlowElement(new BoundaryEvent());
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    asyncPropertyValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link AsyncPropertyValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link ValidationError} (default constructor) ActivityId is {@code 42}.
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link AsyncPropertyValidator#executeValidation(BpmnModel, Process,
   * List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given ValidationError (default constructor) ActivityId is '42'; then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AsyncPropertyValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenValidationErrorActivityIdIs42_thenArrayListSizeIsTwo() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    AdhocSubProcess element = mock(AdhocSubProcess.class);
    when(element.getXmlColumnNumber()).thenReturn(10);
    when(element.getXmlRowNumber()).thenReturn(10);
    when(element.getName()).thenReturn("Name");
    when(element.isAsynchronous()).thenReturn(true);
    when(element.getId()).thenReturn("42");
    when(element.getFlowElements()).thenReturn(new ArrayList<>());
    when(element.getFlowElementMap()).thenReturn(new HashMap<>());
    doNothing().when(element).setParentContainer(Mockito.<FlowElementsContainer>any());

    Process process = new Process();
    process.addFlowElement(element);

    ValidationError validationError = new ValidationError();
    validationError.setActivityId("42");
    validationError.setActivityName("FLOW_ELEMENT_ASYNC_NOT_AVAILABLE");
    validationError.setDefaultDescription("FLOW_ELEMENT_ASYNC_NOT_AVAILABLE");
    validationError.setKey("FLOW_ELEMENT_ASYNC_NOT_AVAILABLE");
    validationError.setParams(new HashMap<>());
    validationError.setProblem("FLOW_ELEMENT_ASYNC_NOT_AVAILABLE");
    validationError.setProcessDefinitionId("42");
    validationError.setProcessDefinitionName("FLOW_ELEMENT_ASYNC_NOT_AVAILABLE");
    validationError.setValidatorSetName("FLOW_ELEMENT_ASYNC_NOT_AVAILABLE");
    validationError.setWarning(true);
    validationError.setXmlColumnNumber(10);
    validationError.setXmlLineNumber(2);

    ArrayList<ValidationError> errors = new ArrayList<>();
    errors.add(validationError);

    // Act
    asyncPropertyValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(element, atLeast(1)).getId();
    verify(element).getXmlColumnNumber();
    verify(element).getXmlRowNumber();
    verify(element).getName();
    verify(element).setParentContainer(isA(FlowElementsContainer.class));
    verify(element).isAsynchronous();
    verify(element).getFlowElementMap();
    verify(element).getFlowElements();
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("42", getResult.getActivityId());
    assertEquals("FLOW_ELEMENT_ASYNC_NOT_AVAILABLE", getResult.getDefaultDescription());
    assertEquals("FLOW_ELEMENT_ASYNC_NOT_AVAILABLE", getResult.getKey());
    assertEquals("FLOW_ELEMENT_ASYNC_NOT_AVAILABLE", getResult.getProblem());
    assertEquals("Name", getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Test {@link AsyncPropertyValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link AsyncPropertyValidator#executeValidation(BpmnModel, Process,
   * List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AsyncPropertyValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_thenArrayListSizeIsOne() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();

    AdhocSubProcess element = mock(AdhocSubProcess.class);
    when(element.getXmlColumnNumber()).thenReturn(10);
    when(element.getXmlRowNumber()).thenReturn(10);
    when(element.getName()).thenReturn("Name");
    when(element.isAsynchronous()).thenReturn(true);
    when(element.getId()).thenReturn("42");
    when(element.getFlowElements()).thenReturn(new ArrayList<>());
    when(element.getFlowElementMap()).thenReturn(new HashMap<>());
    doNothing().when(element).setParentContainer(Mockito.<FlowElementsContainer>any());

    Process process = new Process();
    process.addFlowElement(element);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    asyncPropertyValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(element, atLeast(1)).getId();
    verify(element).getXmlColumnNumber();
    verify(element).getXmlRowNumber();
    verify(element).getName();
    verify(element).setParentContainer(isA(FlowElementsContainer.class));
    verify(element).isAsynchronous();
    verify(element).getFlowElementMap();
    verify(element).getFlowElements();
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("FLOW_ELEMENT_ASYNC_NOT_AVAILABLE", getResult.getDefaultDescription());
    assertEquals("FLOW_ELEMENT_ASYNC_NOT_AVAILABLE", getResult.getKey());
    assertEquals("FLOW_ELEMENT_ASYNC_NOT_AVAILABLE", getResult.getProblem());
    assertEquals("Name", getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Test {@link AsyncPropertyValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>When {@link Process} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link AsyncPropertyValidator#executeValidation(BpmnModel, Process,
   * List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); when Process (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AsyncPropertyValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_whenProcess_thenArrayListEmpty() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    asyncPropertyValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link AsyncPropertyValidator#validateFlowElementsInContainer(FlowElementsContainer, List,
   * Process)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) Asynchronous is {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AsyncPropertyValidator#validateFlowElementsInContainer(FlowElementsContainer, List, Process)}
   */
  @Test
  @DisplayName(
      "Test validateFlowElementsInContainer(FlowElementsContainer, List, Process); given AdhocSubProcess (default constructor) Asynchronous is 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AsyncPropertyValidator.validateFlowElementsInContainer(FlowElementsContainer, List, Process)"
  })
  void testValidateFlowElementsInContainer_givenAdhocSubProcessAsynchronousIsFalse() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    element.setAsynchronous(false);

    AdhocSubProcess container = new AdhocSubProcess();
    container.addFlowElement(element);
    ArrayList<ValidationError> errors = new ArrayList<>();
    Process process = new Process();

    // Act
    asyncPropertyValidator.validateFlowElementsInContainer(container, errors, process);

    // Assert that nothing has changed
    Collection<Artifact> artifacts = process.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test {@link AsyncPropertyValidator#validateFlowElementsInContainer(FlowElementsContainer, List,
   * Process)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.
   *   <li>Then calls {@link AdhocSubProcess#getId()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AsyncPropertyValidator#validateFlowElementsInContainer(FlowElementsContainer, List, Process)}
   */
  @Test
  @DisplayName(
      "Test validateFlowElementsInContainer(FlowElementsContainer, List, Process); given ArrayList() add 'null'; then calls getId()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AsyncPropertyValidator.validateFlowElementsInContainer(FlowElementsContainer, List, Process)"
  })
  void testValidateFlowElementsInContainer_givenArrayListAddNull_thenCallsGetId() {
    // Arrange
    ArrayList<FlowElement> flowElementList = new ArrayList<>();
    flowElementList.add(null);

    AdhocSubProcess element = mock(AdhocSubProcess.class);
    when(element.getXmlColumnNumber()).thenReturn(10);
    when(element.getXmlRowNumber()).thenReturn(10);
    when(element.getName()).thenReturn("Name");
    when(element.isAsynchronous()).thenReturn(true);
    when(element.getId()).thenReturn("42");
    when(element.getFlowElements()).thenReturn(flowElementList);
    when(element.getFlowElementMap()).thenReturn(new HashMap<>());
    doNothing().when(element).setParentContainer(Mockito.<FlowElementsContainer>any());
    doNothing().when(element).setAsynchronous(anyBoolean());
    element.setAsynchronous(false);

    AdhocSubProcess container = new AdhocSubProcess();
    container.addFlowElement(element);
    ArrayList<ValidationError> errors = new ArrayList<>();
    Process process = new Process();

    // Act
    asyncPropertyValidator.validateFlowElementsInContainer(container, errors, process);

    // Assert
    verify(element, atLeast(1)).getId();
    verify(element).getXmlColumnNumber();
    verify(element).getXmlRowNumber();
    verify(element).getName();
    verify(element).setParentContainer(isA(FlowElementsContainer.class));
    verify(element).isAsynchronous();
    verify(element).setAsynchronous(false);
    verify(element).getFlowElementMap();
    verify(element).getFlowElements();
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test {@link AsyncPropertyValidator#validateFlowElementsInContainer(FlowElementsContainer, List,
   * Process)}.
   *
   * <ul>
   *   <li>Given {@link BoundaryEvent} (default constructor) Asynchronous is {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AsyncPropertyValidator#validateFlowElementsInContainer(FlowElementsContainer, List, Process)}
   */
  @Test
  @DisplayName(
      "Test validateFlowElementsInContainer(FlowElementsContainer, List, Process); given BoundaryEvent (default constructor) Asynchronous is 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AsyncPropertyValidator.validateFlowElementsInContainer(FlowElementsContainer, List, Process)"
  })
  void testValidateFlowElementsInContainer_givenBoundaryEventAsynchronousIsFalse() {
    // Arrange
    BoundaryEvent element = new BoundaryEvent();
    element.setAsynchronous(false);

    AdhocSubProcess container = new AdhocSubProcess();
    container.addFlowElement(element);
    ArrayList<ValidationError> errors = new ArrayList<>();
    Process process = new Process();

    // Act
    asyncPropertyValidator.validateFlowElementsInContainer(container, errors, process);

    // Assert that nothing has changed
    Collection<Artifact> artifacts = process.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test {@link AsyncPropertyValidator#validateFlowElementsInContainer(FlowElementsContainer, List,
   * Process)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * AsyncPropertyValidator#validateFlowElementsInContainer(FlowElementsContainer, List, Process)}
   */
  @Test
  @DisplayName(
      "Test validateFlowElementsInContainer(FlowElementsContainer, List, Process); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AsyncPropertyValidator.validateFlowElementsInContainer(FlowElementsContainer, List, Process)"
  })
  void testValidateFlowElementsInContainer_thenArrayListSizeIsOne() {
    // Arrange
    AdhocSubProcess element = mock(AdhocSubProcess.class);
    when(element.getXmlColumnNumber()).thenReturn(10);
    when(element.getXmlRowNumber()).thenReturn(10);
    when(element.getName()).thenReturn("Name");
    when(element.isAsynchronous()).thenReturn(true);
    when(element.getId()).thenReturn("42");
    when(element.getFlowElements()).thenReturn(new ArrayList<>());
    when(element.getFlowElementMap()).thenReturn(new HashMap<>());
    doNothing().when(element).setParentContainer(Mockito.<FlowElementsContainer>any());
    doNothing().when(element).setAsynchronous(anyBoolean());
    element.setAsynchronous(false);

    AdhocSubProcess container = new AdhocSubProcess();
    container.addFlowElement(element);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    asyncPropertyValidator.validateFlowElementsInContainer(container, errors, new Process());

    // Assert
    verify(element, atLeast(1)).getId();
    verify(element).getXmlColumnNumber();
    verify(element).getXmlRowNumber();
    verify(element).getName();
    verify(element).setParentContainer(isA(FlowElementsContainer.class));
    verify(element).isAsynchronous();
    verify(element).setAsynchronous(false);
    verify(element).getFlowElementMap();
    verify(element).getFlowElements();
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("FLOW_ELEMENT_ASYNC_NOT_AVAILABLE", getResult.getDefaultDescription());
    assertEquals("FLOW_ELEMENT_ASYNC_NOT_AVAILABLE", getResult.getKey());
    assertEquals("FLOW_ELEMENT_ASYNC_NOT_AVAILABLE", getResult.getProblem());
    assertEquals("Name", getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Test {@link AsyncPropertyValidator#validateFlowElementsInContainer(FlowElementsContainer, List,
   * Process)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * AsyncPropertyValidator#validateFlowElementsInContainer(FlowElementsContainer, List, Process)}
   */
  @Test
  @DisplayName(
      "Test validateFlowElementsInContainer(FlowElementsContainer, List, Process); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AsyncPropertyValidator.validateFlowElementsInContainer(FlowElementsContainer, List, Process)"
  })
  void testValidateFlowElementsInContainer_thenArrayListSizeIsTwo() {
    // Arrange
    AsyncPropertyValidator asyncPropertyValidator = new AsyncPropertyValidator();

    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    adhocSubProcess.setAsynchronous(true);

    ArrayList<FlowElement> flowElementList = new ArrayList<>();
    flowElementList.add(adhocSubProcess);

    AdhocSubProcess element = mock(AdhocSubProcess.class);
    when(element.getXmlColumnNumber()).thenReturn(10);
    when(element.getXmlRowNumber()).thenReturn(10);
    when(element.getName()).thenReturn("Name");
    when(element.isAsynchronous()).thenReturn(true);
    when(element.getId()).thenReturn("42");
    when(element.getFlowElements()).thenReturn(flowElementList);
    when(element.getFlowElementMap()).thenReturn(new HashMap<>());
    doNothing().when(element).setParentContainer(Mockito.<FlowElementsContainer>any());
    doNothing().when(element).setAsynchronous(anyBoolean());
    element.setAsynchronous(false);

    AdhocSubProcess container = new AdhocSubProcess();
    container.addFlowElement(element);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    asyncPropertyValidator.validateFlowElementsInContainer(container, errors, new Process());

    // Assert
    verify(element, atLeast(1)).getId();
    verify(element).getXmlColumnNumber();
    verify(element).getXmlRowNumber();
    verify(element).getName();
    verify(element).setParentContainer(isA(FlowElementsContainer.class));
    verify(element).isAsynchronous();
    verify(element).setAsynchronous(false);
    verify(element).getFlowElementMap();
    verify(element).getFlowElements();
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("42", getResult.getActivityId());
    assertEquals("FLOW_ELEMENT_ASYNC_NOT_AVAILABLE", getResult.getDefaultDescription());
    assertEquals("FLOW_ELEMENT_ASYNC_NOT_AVAILABLE", getResult.getKey());
    assertEquals("FLOW_ELEMENT_ASYNC_NOT_AVAILABLE", getResult.getProblem());
    assertEquals("Name", getResult.getActivityName());
    ValidationError getResult2 = errors.get(0);
    assertNull(getResult2.getActivityId());
    assertNull(getResult2.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlLineNumber());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Test {@link AsyncPropertyValidator#validateFlowElementsInContainer(FlowElementsContainer, List,
   * Process)}.
   *
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).
   *   <li>Then {@link Process} (default constructor) Artifacts {@link List}.
   * </ul>
   *
   * <p>Method under test: {@link
   * AsyncPropertyValidator#validateFlowElementsInContainer(FlowElementsContainer, List, Process)}
   */
  @Test
  @DisplayName(
      "Test validateFlowElementsInContainer(FlowElementsContainer, List, Process); when AdhocSubProcess (default constructor); then Process (default constructor) Artifacts List")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AsyncPropertyValidator.validateFlowElementsInContainer(FlowElementsContainer, List, Process)"
  })
  void testValidateFlowElementsInContainer_whenAdhocSubProcess_thenProcessArtifactsList() {
    // Arrange
    AdhocSubProcess container = new AdhocSubProcess();
    ArrayList<ValidationError> errors = new ArrayList<>();
    Process process = new Process();

    // Act
    asyncPropertyValidator.validateFlowElementsInContainer(container, errors, process);

    // Assert that nothing has changed
    Collection<Artifact> artifacts = process.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
  }
}
