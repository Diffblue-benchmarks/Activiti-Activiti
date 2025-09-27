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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SequenceflowValidatorDiffblueTest {
  /**
   * Test {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <p>Method under test: {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SequenceflowValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation() {
    // Arrange
    SequenceflowValidator sequenceflowValidator = new SequenceflowValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(new SequenceFlow("Source Ref", "Target Ref"));

    Process process = mock(Process.class);
    when(process.getFlowElementsContainer(Mockito.<String>any())).thenReturn(null);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean()))
        .thenReturn(new AdhocSubProcess());
    when(process.findFlowElementsOfType(Mockito.<Class<SequenceFlow>>any()))
        .thenReturn(sequenceFlowList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sequenceflowValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process, atLeast(1)).getId();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getFlowElement(Mockito.<String>any(), eq(true));
    verify(process, atLeast(1)).getFlowElementsContainer(null);
    verify(process, atLeast(1)).getName();
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("SEQ_FLOW_INVALID_TARGET", getResult.getDefaultDescription());
    assertEquals("SEQ_FLOW_INVALID_TARGET", getResult.getKey());
    assertEquals("SEQ_FLOW_INVALID_TARGET", getResult.getProblem());
  }

  /**
   * Test {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <p>Method under test: {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SequenceflowValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation2() {
    // Arrange
    SequenceflowValidator sequenceflowValidator = new SequenceflowValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(new SequenceFlow("Source Ref", "Target Ref"));

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getId()).thenReturn("42");

    Process process = mock(Process.class);
    when(process.getFlowElementsContainer(Mockito.<String>any())).thenReturn(null);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(adhocSubProcess);
    when(process.findFlowElementsOfType(Mockito.<Class<SequenceFlow>>any()))
        .thenReturn(sequenceFlowList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sequenceflowValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(adhocSubProcess, atLeast(1)).getId();
    verify(process, atLeast(1)).getId();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getFlowElement(Mockito.<String>any(), eq(true));
    verify(process, atLeast(1)).getFlowElementsContainer("42");
    verify(process, atLeast(1)).getName();
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("SEQ_FLOW_INVALID_TARGET", getResult.getDefaultDescription());
    assertEquals("SEQ_FLOW_INVALID_TARGET", getResult.getKey());
    assertEquals("SEQ_FLOW_INVALID_TARGET", getResult.getProblem());
  }

  /**
   * Test {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <p>Method under test: {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SequenceflowValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation3() {
    // Arrange
    SequenceflowValidator sequenceflowValidator = new SequenceflowValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(new SequenceFlow(null, "Target Ref"));

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getId()).thenReturn("42");

    Process process = mock(Process.class);
    when(process.getFlowElementsContainer(Mockito.<String>any())).thenReturn(null);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(adhocSubProcess);
    when(process.findFlowElementsOfType(Mockito.<Class<SequenceFlow>>any()))
        .thenReturn(sequenceFlowList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sequenceflowValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(adhocSubProcess, atLeast(1)).getId();
    verify(process, atLeast(1)).getId();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getFlowElement(Mockito.<String>any(), eq(true));
    verify(process, atLeast(1)).getFlowElementsContainer("42");
    verify(process, atLeast(1)).getName();
    assertEquals(3, errors.size());
    ValidationError getResult = errors.get(2);
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
  }

  /**
   * Test {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <p>Method under test: {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SequenceflowValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation4() {
    // Arrange
    SequenceflowValidator sequenceflowValidator = new SequenceflowValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(new SequenceFlow("", "Target Ref"));

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getId()).thenReturn("42");

    Process process = mock(Process.class);
    when(process.getFlowElementsContainer(Mockito.<String>any())).thenReturn(null);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(adhocSubProcess);
    when(process.findFlowElementsOfType(Mockito.<Class<SequenceFlow>>any()))
        .thenReturn(sequenceFlowList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sequenceflowValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(adhocSubProcess, atLeast(1)).getId();
    verify(process, atLeast(1)).getId();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getFlowElement(Mockito.<String>any(), eq(true));
    verify(process, atLeast(1)).getFlowElementsContainer("42");
    verify(process, atLeast(1)).getName();
    assertEquals(3, errors.size());
    ValidationError getResult = errors.get(2);
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
  }

  /**
   * Test {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <p>Method under test: {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SequenceflowValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation5() {
    // Arrange
    SequenceflowValidator sequenceflowValidator = new SequenceflowValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    SequenceFlow sequenceFlow = new SequenceFlow("Source Ref", null);
    sequenceFlowList.add(sequenceFlow);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getId()).thenReturn("42");

    Process process = mock(Process.class);
    when(process.getFlowElementsContainer(Mockito.<String>any())).thenReturn(null);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(adhocSubProcess);
    when(process.findFlowElementsOfType(Mockito.<Class<SequenceFlow>>any()))
        .thenReturn(sequenceFlowList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sequenceflowValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(adhocSubProcess, atLeast(1)).getId();
    verify(process, atLeast(1)).getId();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getFlowElement(Mockito.<String>any(), eq(true));
    verify(process, atLeast(1)).getFlowElementsContainer("42");
    verify(process, atLeast(1)).getName();
    assertEquals(3, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("SEQ_FLOW_INVALID_TARGET", getResult.getDefaultDescription());
    assertEquals("SEQ_FLOW_INVALID_TARGET", getResult.getKey());
    assertEquals("SEQ_FLOW_INVALID_TARGET", getResult.getProblem());
    ValidationError getResult2 = errors.get(2);
    assertNull(getResult2.getActivityId());
    assertNull(getResult2.getActivityName());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlLineNumber());
  }

  /**
   * Test {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} {@link AdhocSubProcess#getId()} throw {@link
   *       RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given AdhocSubProcess getId() throw RuntimeException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SequenceflowValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenAdhocSubProcessGetIdThrowRuntimeException() {
    // Arrange
    SequenceflowValidator sequenceflowValidator = new SequenceflowValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(new SequenceFlow("Source Ref", "Target Ref"));

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getId()).thenThrow(new RuntimeException());

    Process process = mock(Process.class);
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(adhocSubProcess);
    when(process.findFlowElementsOfType(Mockito.<Class<SequenceFlow>>any()))
        .thenReturn(sequenceFlowList);

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () -> sequenceflowValidator.executeValidation(bpmnModel, process, new ArrayList<>()));
    verify(adhocSubProcess).getId();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getFlowElement(Mockito.<String>any(), eq(true));
  }

  /**
   * Test {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given ArrayList(); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SequenceflowValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenArrayList_thenArrayListEmpty() {
    // Arrange
    SequenceflowValidator sequenceflowValidator = new SequenceflowValidator();
    BpmnModel bpmnModel = new BpmnModel();

    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<SequenceFlow>>any()))
        .thenReturn(new ArrayList<>());
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sequenceflowValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(process).findFlowElementsOfType(isA(Class.class));
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link SequenceFlow} {@link SequenceFlow#getSourceRef()} return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given SequenceFlow getSourceRef() return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SequenceflowValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenSequenceFlowGetSourceRefReturnNull() {
    // Arrange
    SequenceflowValidator sequenceflowValidator = new SequenceflowValidator();
    BpmnModel bpmnModel = new BpmnModel();

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getXmlColumnNumber()).thenReturn(10);
    when(sequenceFlow.getXmlRowNumber()).thenReturn(10);
    when(sequenceFlow.getId()).thenReturn("42");
    when(sequenceFlow.getName()).thenReturn("Name");
    when(sequenceFlow.getConditionExpression()).thenReturn("Condition Expression");
    when(sequenceFlow.getSourceRef()).thenReturn(null);
    when(sequenceFlow.getTargetRef()).thenReturn("Target Ref");

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(sequenceFlow);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getId()).thenReturn("42");

    Process process = mock(Process.class);
    when(process.getFlowElementsContainer(Mockito.<String>any())).thenReturn(null);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(adhocSubProcess);
    when(process.findFlowElementsOfType(Mockito.<Class<SequenceFlow>>any()))
        .thenReturn(sequenceFlowList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sequenceflowValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(adhocSubProcess, atLeast(1)).getId();
    verify(process, atLeast(1)).getId();
    verify(sequenceFlow, atLeast(1)).getId();
    verify(sequenceFlow, atLeast(1)).getXmlColumnNumber();
    verify(sequenceFlow, atLeast(1)).getXmlRowNumber();
    verify(sequenceFlow, atLeast(1)).getName();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getFlowElement(Mockito.<String>any(), eq(true));
    verify(process, atLeast(1)).getFlowElementsContainer("42");
    verify(process, atLeast(1)).getName();
    verify(sequenceFlow).getConditionExpression();
    verify(sequenceFlow).getSourceRef();
    verify(sequenceFlow).getTargetRef();
    assertEquals(3, errors.size());
    ValidationError getResult = errors.get(2);
    assertEquals("42", getResult.getActivityId());
    assertEquals("Name", getResult.getActivityName());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
  }

  /**
   * Test {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link SequenceFlow} {@link SequenceFlow#getSourceRef()} throw {@link
   *       RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given SequenceFlow getSourceRef() throw RuntimeException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SequenceflowValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenSequenceFlowGetSourceRefThrowRuntimeException() {
    // Arrange
    SequenceflowValidator sequenceflowValidator = new SequenceflowValidator();
    BpmnModel bpmnModel = new BpmnModel();

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getSourceRef()).thenThrow(new RuntimeException());

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(sequenceFlow);

    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<SequenceFlow>>any()))
        .thenReturn(sequenceFlowList);

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

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () -> sequenceflowValidator.executeValidation(bpmnModel, process, errors));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(sequenceFlow).getSourceRef();
  }

  /**
   * Test {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link SequenceFlow} {@link SequenceFlow#getTargetRef()} return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given SequenceFlow getTargetRef() return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SequenceflowValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenSequenceFlowGetTargetRefReturnNull() {
    // Arrange
    SequenceflowValidator sequenceflowValidator = new SequenceflowValidator();
    BpmnModel bpmnModel = new BpmnModel();

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getXmlColumnNumber()).thenReturn(10);
    when(sequenceFlow.getXmlRowNumber()).thenReturn(10);
    when(sequenceFlow.getId()).thenReturn("42");
    when(sequenceFlow.getName()).thenReturn("Name");
    when(sequenceFlow.getConditionExpression()).thenReturn("Condition Expression");
    when(sequenceFlow.getSourceRef()).thenReturn("Source Ref");
    when(sequenceFlow.getTargetRef()).thenReturn(null);

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(sequenceFlow);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getId()).thenReturn("42");

    Process process = mock(Process.class);
    when(process.getFlowElementsContainer(Mockito.<String>any())).thenReturn(null);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(adhocSubProcess);
    when(process.findFlowElementsOfType(Mockito.<Class<SequenceFlow>>any()))
        .thenReturn(sequenceFlowList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sequenceflowValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(adhocSubProcess, atLeast(1)).getId();
    verify(process, atLeast(1)).getId();
    verify(sequenceFlow, atLeast(1)).getId();
    verify(sequenceFlow, atLeast(1)).getXmlColumnNumber();
    verify(sequenceFlow, atLeast(1)).getXmlRowNumber();
    verify(sequenceFlow, atLeast(1)).getName();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getFlowElement(Mockito.<String>any(), eq(true));
    verify(process, atLeast(1)).getFlowElementsContainer("42");
    verify(process, atLeast(1)).getName();
    verify(sequenceFlow).getConditionExpression();
    verify(sequenceFlow).getSourceRef();
    verify(sequenceFlow).getTargetRef();
    assertEquals(3, errors.size());
    ValidationError getResult = errors.get(2);
    assertEquals("42", getResult.getActivityId());
    assertEquals("Name", getResult.getActivityName());
    ValidationError getResult2 = errors.get(0);
    assertEquals("SEQ_FLOW_INVALID_TARGET", getResult2.getDefaultDescription());
    assertEquals("SEQ_FLOW_INVALID_TARGET", getResult2.getKey());
    assertEquals("SEQ_FLOW_INVALID_TARGET", getResult2.getProblem());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
  }

  /**
   * Test {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} first ActivityId is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); then ArrayList() first ActivityId is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SequenceflowValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_thenArrayListFirstActivityIdIs42() {
    // Arrange
    SequenceflowValidator sequenceflowValidator = new SequenceflowValidator();
    BpmnModel bpmnModel = new BpmnModel();

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getXmlColumnNumber()).thenReturn(10);
    when(sequenceFlow.getXmlRowNumber()).thenReturn(10);
    when(sequenceFlow.getId()).thenReturn("42");
    when(sequenceFlow.getName()).thenReturn("Name");
    when(sequenceFlow.getConditionExpression()).thenReturn("Condition Expression");
    when(sequenceFlow.getSourceRef()).thenReturn("Source Ref");
    when(sequenceFlow.getTargetRef()).thenReturn("Target Ref");

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(sequenceFlow);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getId()).thenReturn("42");

    Process process = mock(Process.class);
    when(process.getFlowElementsContainer(Mockito.<String>any())).thenReturn(null);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(adhocSubProcess);
    when(process.findFlowElementsOfType(Mockito.<Class<SequenceFlow>>any()))
        .thenReturn(sequenceFlowList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sequenceflowValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(adhocSubProcess, atLeast(1)).getId();
    verify(process, atLeast(1)).getId();
    verify(sequenceFlow, atLeast(1)).getId();
    verify(sequenceFlow, atLeast(1)).getXmlColumnNumber();
    verify(sequenceFlow, atLeast(1)).getXmlRowNumber();
    verify(sequenceFlow, atLeast(1)).getName();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getFlowElement(Mockito.<String>any(), eq(true));
    verify(process, atLeast(1)).getFlowElementsContainer("42");
    verify(process, atLeast(1)).getName();
    verify(sequenceFlow).getConditionExpression();
    verify(sequenceFlow).getSourceRef();
    verify(sequenceFlow).getTargetRef();
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getActivityId());
    ValidationError getResult2 = errors.get(1);
    assertEquals("42", getResult2.getActivityId());
    assertEquals("Name", getResult.getActivityName());
    assertEquals("Name", getResult2.getActivityName());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult2.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
    assertEquals(10, getResult2.getXmlLineNumber());
  }

  /**
   * Test {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>When {@link Process} {@link Process#getFlowElement(String, boolean)} return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); when Process getFlowElement(String, boolean) return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SequenceflowValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_whenProcessGetFlowElementReturnNull() {
    // Arrange
    SequenceflowValidator sequenceflowValidator = new SequenceflowValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(new SequenceFlow("Source Ref", "Target Ref"));

    Process process = mock(Process.class);
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(null);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<SequenceFlow>>any()))
        .thenReturn(sequenceFlowList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sequenceflowValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process, atLeast(1)).getId();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getFlowElement(Mockito.<String>any(), eq(true));
    verify(process, atLeast(1)).getName();
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("SEQ_FLOW_INVALID_TARGET", getResult.getDefaultDescription());
    assertEquals("SEQ_FLOW_INVALID_TARGET", getResult.getKey());
    assertEquals("SEQ_FLOW_INVALID_TARGET", getResult.getProblem());
  }

  /**
   * Test {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>When {@link Process} {@link Process#getFlowElement(String, boolean)} throw {@link
   *       RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); when Process getFlowElement(String, boolean) throw RuntimeException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SequenceflowValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_whenProcessGetFlowElementThrowRuntimeException() {
    // Arrange
    SequenceflowValidator sequenceflowValidator = new SequenceflowValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(new SequenceFlow("Source Ref", "Target Ref"));

    Process process = mock(Process.class);
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean()))
        .thenThrow(new RuntimeException());
    when(process.findFlowElementsOfType(Mockito.<Class<SequenceFlow>>any()))
        .thenReturn(sequenceFlowList);

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () -> sequenceflowValidator.executeValidation(bpmnModel, process, new ArrayList<>()));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getFlowElement("Source Ref", true);
  }

  /**
   * Test {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>When {@link Process} {@link Process#getFlowElementsContainer(String)} return {@link
   *       AdhocSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); when Process getFlowElementsContainer(String) return AdhocSubProcess (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SequenceflowValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_whenProcessGetFlowElementsContainerReturnAdhocSubProcess() {
    // Arrange
    SequenceflowValidator sequenceflowValidator = new SequenceflowValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(new SequenceFlow("Source Ref", "Target Ref"));

    Process process = mock(Process.class);
    when(process.getFlowElementsContainer(Mockito.<String>any())).thenReturn(new AdhocSubProcess());
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean()))
        .thenReturn(new AdhocSubProcess());
    when(process.findFlowElementsOfType(Mockito.<Class<SequenceFlow>>any()))
        .thenReturn(sequenceFlowList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sequenceflowValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getFlowElement(Mockito.<String>any(), eq(true));
    verify(process, atLeast(1)).getFlowElementsContainer(null);
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>When {@link Process} {@link Process#getFlowElementsContainer(String)} throw {@link
   *       RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); when Process getFlowElementsContainer(String) throw RuntimeException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SequenceflowValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_whenProcessGetFlowElementsContainerThrowRuntimeException() {
    // Arrange
    SequenceflowValidator sequenceflowValidator = new SequenceflowValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(new SequenceFlow("Source Ref", "Target Ref"));

    Process process = mock(Process.class);
    when(process.getFlowElementsContainer(Mockito.<String>any())).thenThrow(new RuntimeException());
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean()))
        .thenReturn(new AdhocSubProcess());
    when(process.findFlowElementsOfType(Mockito.<Class<SequenceFlow>>any()))
        .thenReturn(sequenceFlowList);

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () -> sequenceflowValidator.executeValidation(bpmnModel, process, new ArrayList<>()));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getFlowElement(Mockito.<String>any(), eq(true));
    verify(process).getFlowElementsContainer(null);
  }

  /**
   * Test {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>When {@link Process} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); when Process (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SequenceflowValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_whenProcess_thenArrayListEmpty() {
    // Arrange
    SequenceflowValidator sequenceflowValidator = new SequenceflowValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sequenceflowValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }
}
