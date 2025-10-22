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
import java.util.HashMap;
import java.util.List;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BooleanDataObject;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BpmnModel;
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
  @Autowired
  private AsyncPropertyValidator asyncPropertyValidator;

  /**
   * Test {@link AsyncPropertyValidator#executeValidation(BpmnModel, Process, List)}.
   * <p>
   * Method under test: {@link AsyncPropertyValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AsyncPropertyValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    AsyncPropertyValidator asyncPropertyValidator = new AsyncPropertyValidator();
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
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AsyncPropertyValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given AdhocSubProcess (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AsyncPropertyValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenAdhocSubProcess() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    AsyncPropertyValidator asyncPropertyValidator = new AsyncPropertyValidator();
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
   * <ul>
   *   <li>Given {@link BooleanDataObject} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AsyncPropertyValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given BooleanDataObject (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AsyncPropertyValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenBooleanDataObject() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    AsyncPropertyValidator asyncPropertyValidator = new AsyncPropertyValidator();
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
   * <ul>
   *   <li>Given {@link BoundaryEvent} (default constructor).</li>
   *   <li>When {@link Process} (default constructor) addFlowElement {@link BoundaryEvent} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AsyncPropertyValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given BoundaryEvent (default constructor); when Process (default constructor) addFlowElement BoundaryEvent (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AsyncPropertyValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenBoundaryEvent_whenProcessAddFlowElementBoundaryEvent() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    AsyncPropertyValidator asyncPropertyValidator = new AsyncPropertyValidator();
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
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} first ProcessDefinitionId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AsyncPropertyValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() first ProcessDefinitionId is '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AsyncPropertyValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_thenArrayListFirstProcessDefinitionIdIs42() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    AsyncPropertyValidator asyncPropertyValidator = new AsyncPropertyValidator();
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
    asyncPropertyValidator.executeValidation(bpmnModel, process, errors);

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
   * Test {@link AsyncPropertyValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link AsyncPropertyValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() size is two")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AsyncPropertyValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_thenArrayListSizeIsTwo() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    AsyncPropertyValidator asyncPropertyValidator = new AsyncPropertyValidator();
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
    asyncPropertyValidator.executeValidation(bpmnModel, process, errors);

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
   * Test {@link AsyncPropertyValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>When {@link Process} (default constructor).</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link AsyncPropertyValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); when Process (default constructor); then ArrayList() Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AsyncPropertyValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_whenProcess_thenArrayListEmpty() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    AsyncPropertyValidator asyncPropertyValidator = new AsyncPropertyValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    asyncPropertyValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link AsyncPropertyValidator#validateFlowElementsInContainer(FlowElementsContainer, List, Process)}.
   * <p>
   * Method under test: {@link AsyncPropertyValidator#validateFlowElementsInContainer(FlowElementsContainer, List, Process)}
   */
  @Test
  @DisplayName("Test validateFlowElementsInContainer(FlowElementsContainer, List, Process)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void AsyncPropertyValidator.validateFlowElementsInContainer(FlowElementsContainer, List, Process)"})
  void testValidateFlowElementsInContainer() {
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
   * Test {@link AsyncPropertyValidator#validateFlowElementsInContainer(FlowElementsContainer, List, Process)}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AsyncPropertyValidator#validateFlowElementsInContainer(FlowElementsContainer, List, Process)}
   */
  @Test
  @DisplayName("Test validateFlowElementsInContainer(FlowElementsContainer, List, Process); given AdhocSubProcess (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void AsyncPropertyValidator.validateFlowElementsInContainer(FlowElementsContainer, List, Process)"})
  void testValidateFlowElementsInContainer_givenAdhocSubProcess() {
    // Arrange
    AdhocSubProcess container = new AdhocSubProcess();
    container.addFlowElement(new AdhocSubProcess());
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    asyncPropertyValidator.validateFlowElementsInContainer(container, errors, new Process());

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link AsyncPropertyValidator#validateFlowElementsInContainer(FlowElementsContainer, List, Process)}.
   * <ul>
   *   <li>Given {@link BooleanDataObject} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AsyncPropertyValidator#validateFlowElementsInContainer(FlowElementsContainer, List, Process)}
   */
  @Test
  @DisplayName("Test validateFlowElementsInContainer(FlowElementsContainer, List, Process); given BooleanDataObject (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void AsyncPropertyValidator.validateFlowElementsInContainer(FlowElementsContainer, List, Process)"})
  void testValidateFlowElementsInContainer_givenBooleanDataObject() {
    // Arrange
    AdhocSubProcess container = new AdhocSubProcess();
    container.addFlowElement(new BooleanDataObject());
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    asyncPropertyValidator.validateFlowElementsInContainer(container, errors, new Process());

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link AsyncPropertyValidator#validateFlowElementsInContainer(FlowElementsContainer, List, Process)}.
   * <ul>
   *   <li>Given {@link BoundaryEvent} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AsyncPropertyValidator#validateFlowElementsInContainer(FlowElementsContainer, List, Process)}
   */
  @Test
  @DisplayName("Test validateFlowElementsInContainer(FlowElementsContainer, List, Process); given BoundaryEvent (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void AsyncPropertyValidator.validateFlowElementsInContainer(FlowElementsContainer, List, Process)"})
  void testValidateFlowElementsInContainer_givenBoundaryEvent() {
    // Arrange
    AdhocSubProcess container = new AdhocSubProcess();
    container.addFlowElement(new BoundaryEvent());
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    asyncPropertyValidator.validateFlowElementsInContainer(container, errors, new Process());

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link AsyncPropertyValidator#validateFlowElementsInContainer(FlowElementsContainer, List, Process)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} first ProcessDefinitionId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AsyncPropertyValidator#validateFlowElementsInContainer(FlowElementsContainer, List, Process)}
   */
  @Test
  @DisplayName("Test validateFlowElementsInContainer(FlowElementsContainer, List, Process); then ArrayList() first ProcessDefinitionId is '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void AsyncPropertyValidator.validateFlowElementsInContainer(FlowElementsContainer, List, Process)"})
  void testValidateFlowElementsInContainer_thenArrayListFirstProcessDefinitionIdIs42() {
    // Arrange
    AdhocSubProcess container = new AdhocSubProcess();

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
    asyncPropertyValidator.validateFlowElementsInContainer(container, errors, new Process());

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
   * Test {@link AsyncPropertyValidator#validateFlowElementsInContainer(FlowElementsContainer, List, Process)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link AsyncPropertyValidator#validateFlowElementsInContainer(FlowElementsContainer, List, Process)}
   */
  @Test
  @DisplayName("Test validateFlowElementsInContainer(FlowElementsContainer, List, Process); then ArrayList() size is two")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void AsyncPropertyValidator.validateFlowElementsInContainer(FlowElementsContainer, List, Process)"})
  void testValidateFlowElementsInContainer_thenArrayListSizeIsTwo() {
    // Arrange
    AdhocSubProcess container = new AdhocSubProcess();

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
    asyncPropertyValidator.validateFlowElementsInContainer(container, errors, new Process());

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
   * Test {@link AsyncPropertyValidator#validateFlowElementsInContainer(FlowElementsContainer, List, Process)}.
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link AsyncPropertyValidator#validateFlowElementsInContainer(FlowElementsContainer, List, Process)}
   */
  @Test
  @DisplayName("Test validateFlowElementsInContainer(FlowElementsContainer, List, Process); when AdhocSubProcess (default constructor); then ArrayList() Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void AsyncPropertyValidator.validateFlowElementsInContainer(FlowElementsContainer, List, Process)"})
  void testValidateFlowElementsInContainer_whenAdhocSubProcess_thenArrayListEmpty() {
    // Arrange
    AdhocSubProcess container = new AdhocSubProcess();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    asyncPropertyValidator.validateFlowElementsInContainer(container, errors, new Process());

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }
}
