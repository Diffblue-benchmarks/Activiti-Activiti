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
    sequenceFlowList.add(new SequenceFlow("SEQ_FLOW_INVALID_SRC", "SEQ_FLOW_INVALID_SRC"));

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getId()).thenReturn("42");

    Process process = mock(Process.class);
    when(process.getFlowElementsContainer(Mockito.<String>any())).thenReturn(new AdhocSubProcess());
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(adhocSubProcess);
    when(process.findFlowElementsOfType(Mockito.<Class<SequenceFlow>>any()))
        .thenReturn(sequenceFlowList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sequenceflowValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(adhocSubProcess, atLeast(1)).getId();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getFlowElement("SEQ_FLOW_INVALID_SRC", true);
    verify(process, atLeast(1)).getFlowElementsContainer("42");
    assertTrue(errors.isEmpty());
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
    sequenceFlowList.add(new SequenceFlow());

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getId()).thenThrow(new RuntimeException());

    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(adhocSubProcess);
    when(process.findFlowElementsOfType(Mockito.<Class<SequenceFlow>>any()))
        .thenReturn(sequenceFlowList);

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () -> sequenceflowValidator.executeValidation(bpmnModel, process, new ArrayList<>()));
    verify(adhocSubProcess).getId();
    verify(process, atLeast(1)).getId();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getFlowElement(null, true);
    verify(process, atLeast(1)).getName();
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
   *   <li>Given {@link RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given RuntimeException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SequenceflowValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenRuntimeException() {
    // Arrange
    SequenceflowValidator sequenceflowValidator = new SequenceflowValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(new SequenceFlow());

    Process process = mock(Process.class);
    when(process.getId()).thenThrow(new RuntimeException());
    when(process.findFlowElementsOfType(Mockito.<Class<SequenceFlow>>any()))
        .thenReturn(sequenceFlowList);

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () -> sequenceflowValidator.executeValidation(bpmnModel, process, new ArrayList<>()));
    verify(process).getId();
    verify(process).findFlowElementsOfType(isA(Class.class));
  }

  /**
   * Test {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link SequenceFlow} {@link SequenceFlow#getSourceRef()} return empty string.
   * </ul>
   *
   * <p>Method under test: {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given SequenceFlow getSourceRef() return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SequenceflowValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenSequenceFlowGetSourceRefReturnEmptyString() {
    // Arrange
    SequenceflowValidator sequenceflowValidator = new SequenceflowValidator();
    BpmnModel bpmnModel = new BpmnModel();

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getXmlColumnNumber()).thenReturn(10);
    when(sequenceFlow.getXmlRowNumber()).thenReturn(10);
    when(sequenceFlow.getId()).thenReturn("42");
    when(sequenceFlow.getName()).thenReturn("Name");
    when(sequenceFlow.getConditionExpression()).thenReturn("Condition Expression");
    when(sequenceFlow.getSourceRef()).thenReturn("");
    when(sequenceFlow.getTargetRef()).thenReturn("Target Ref");

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(sequenceFlow);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getId()).thenReturn("42");

    Process process = mock(Process.class);
    when(process.getFlowElementsContainer(Mockito.<String>any())).thenReturn(new AdhocSubProcess());
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(adhocSubProcess);
    when(process.findFlowElementsOfType(Mockito.<Class<SequenceFlow>>any()))
        .thenReturn(sequenceFlowList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sequenceflowValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process).getId();
    verify(sequenceFlow).getId();
    verify(adhocSubProcess, atLeast(1)).getId();
    verify(sequenceFlow).getXmlColumnNumber();
    verify(sequenceFlow).getXmlRowNumber();
    verify(sequenceFlow).getName();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getFlowElement(Mockito.<String>any(), eq(true));
    verify(process, atLeast(1)).getFlowElementsContainer("42");
    verify(process).getName();
    verify(sequenceFlow).getConditionExpression();
    verify(sequenceFlow).getSourceRef();
    verify(sequenceFlow).getTargetRef();
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("Name", getResult.getActivityName());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
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
    when(process.getFlowElementsContainer(Mockito.<String>any())).thenReturn(new AdhocSubProcess());
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(adhocSubProcess);
    when(process.findFlowElementsOfType(Mockito.<Class<SequenceFlow>>any()))
        .thenReturn(sequenceFlowList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sequenceflowValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process).getId();
    verify(sequenceFlow).getId();
    verify(adhocSubProcess, atLeast(1)).getId();
    verify(sequenceFlow).getXmlColumnNumber();
    verify(sequenceFlow).getXmlRowNumber();
    verify(sequenceFlow).getName();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getFlowElement(Mockito.<String>any(), eq(true));
    verify(process, atLeast(1)).getFlowElementsContainer("42");
    verify(process).getName();
    verify(sequenceFlow).getConditionExpression();
    verify(sequenceFlow).getSourceRef();
    verify(sequenceFlow).getTargetRef();
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("Name", getResult.getActivityName());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
  }

  /**
   * Test {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SequenceflowValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_thenArrayListEmpty() {
    // Arrange
    SequenceflowValidator sequenceflowValidator = new SequenceflowValidator();
    BpmnModel bpmnModel = new BpmnModel();

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getConditionExpression()).thenReturn("Condition Expression");
    when(sequenceFlow.getSourceRef()).thenReturn("Source Ref");
    when(sequenceFlow.getTargetRef()).thenReturn("Target Ref");

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(sequenceFlow);

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getId()).thenReturn("42");

    Process process = mock(Process.class);
    when(process.getFlowElementsContainer(Mockito.<String>any())).thenReturn(new AdhocSubProcess());
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(adhocSubProcess);
    when(process.findFlowElementsOfType(Mockito.<Class<SequenceFlow>>any()))
        .thenReturn(sequenceFlowList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sequenceflowValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(adhocSubProcess, atLeast(1)).getId();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getFlowElement(Mockito.<String>any(), eq(true));
    verify(process, atLeast(1)).getFlowElementsContainer("42");
    verify(sequenceFlow).getConditionExpression();
    verify(sequenceFlow).getSourceRef();
    verify(sequenceFlow).getTargetRef();
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} first ActivityId is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); then ArrayList() first ActivityId is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SequenceflowValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_thenArrayListFirstActivityIdIsNull() {
    // Arrange
    SequenceflowValidator sequenceflowValidator = new SequenceflowValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(new SequenceFlow());

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getId()).thenReturn("42");

    Process process = mock(Process.class);
    when(process.getFlowElementsContainer(Mockito.<String>any())).thenReturn(new AdhocSubProcess());
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
    verify(process, atLeast(1)).getFlowElement(null, true);
    verify(process, atLeast(1)).getFlowElementsContainer("42");
    verify(process, atLeast(1)).getName();
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(0);
    assertNull(getResult.getActivityId());
    ValidationError getResult2 = errors.get(1);
    assertNull(getResult2.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult2.getActivityName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertEquals(0, getResult2.getXmlLineNumber());
  }

  /**
   * Test {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} first DefaultDescription is {@code
   *       SEQ_FLOW_INVALID_TARGET}.
   * </ul>
   *
   * <p>Method under test: {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); then ArrayList() first DefaultDescription is 'SEQ_FLOW_INVALID_TARGET'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SequenceflowValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_thenArrayListFirstDefaultDescriptionIsSeqFlowInvalidTarget() {
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
    when(process.getFlowElementsContainer(Mockito.<String>any())).thenReturn(new AdhocSubProcess());
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(adhocSubProcess);
    when(process.findFlowElementsOfType(Mockito.<Class<SequenceFlow>>any()))
        .thenReturn(sequenceFlowList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sequenceflowValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process).getId();
    verify(sequenceFlow).getId();
    verify(adhocSubProcess, atLeast(1)).getId();
    verify(sequenceFlow).getXmlColumnNumber();
    verify(sequenceFlow).getXmlRowNumber();
    verify(sequenceFlow).getName();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getFlowElement(Mockito.<String>any(), eq(true));
    verify(process, atLeast(1)).getFlowElementsContainer("42");
    verify(process).getName();
    verify(sequenceFlow).getConditionExpression();
    verify(sequenceFlow).getSourceRef();
    verify(sequenceFlow).getTargetRef();
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("SEQ_FLOW_INVALID_TARGET", getResult.getDefaultDescription());
    assertEquals("SEQ_FLOW_INVALID_TARGET", getResult.getKey());
    assertEquals("SEQ_FLOW_INVALID_TARGET", getResult.getProblem());
  }

  /**
   * Test {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} second ActivityId is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); then ArrayList() second ActivityId is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SequenceflowValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_thenArrayListSecondActivityIdIs42() {
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
    ValidationError getResult = errors.get(1);
    assertEquals("42", getResult.getActivityId());
    assertEquals("Name", getResult.getActivityName());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
  }

  /**
   * Test {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is four.
   * </ul>
   *
   * <p>Method under test: {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() size is four")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SequenceflowValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_thenArrayListSizeIsFour() {
    // Arrange
    SequenceflowValidator sequenceflowValidator = new SequenceflowValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(new SequenceFlow());

    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(null);
    when(process.findFlowElementsOfType(Mockito.<Class<SequenceFlow>>any()))
        .thenReturn(sequenceFlowList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sequenceflowValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process, atLeast(1)).getId();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getFlowElement(null, true);
    verify(process, atLeast(1)).getName();
    assertEquals(4, errors.size());
    ValidationError getResult = errors.get(2);
    assertEquals("42", getResult.getProcessDefinitionId());
    ValidationError getResult2 = errors.get(3);
    assertEquals("42", getResult2.getProcessDefinitionId());
    assertEquals("Name", getResult.getProcessDefinitionName());
    assertEquals("Name", getResult2.getProcessDefinitionName());
    assertEquals("SEQ_FLOW_INVALID_SRC", getResult.getDefaultDescription());
    assertEquals("SEQ_FLOW_INVALID_SRC", getResult.getKey());
    assertEquals("SEQ_FLOW_INVALID_SRC", getResult.getProblem());
    assertEquals("SEQ_FLOW_INVALID_TARGET", getResult2.getDefaultDescription());
    assertEquals("SEQ_FLOW_INVALID_TARGET", getResult2.getKey());
    assertEquals("SEQ_FLOW_INVALID_TARGET", getResult2.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult2.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult2.getActivityName());
    assertNull(getResult.getValidatorSetName());
    assertNull(getResult2.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertEquals(0, getResult2.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertFalse(getResult2.isWarning());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
  }

  /**
   * Test {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is six.
   * </ul>
   *
   * <p>Method under test: {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() size is six")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SequenceflowValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_thenArrayListSizeIsSix() {
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
    sequenceFlowList.add(new SequenceFlow());
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
    verify(sequenceFlow, atLeast(1)).getId();
    verify(adhocSubProcess, atLeast(1)).getId();
    verify(process, atLeast(1)).getId();
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
    assertEquals(6, errors.size());
    ValidationError getResult = errors.get(4);
    assertEquals("42", getResult.getActivityId());
    ValidationError getResult2 = errors.get(5);
    assertEquals("42", getResult2.getActivityId());
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("42", getResult2.getProcessDefinitionId());
    assertEquals("Name", getResult.getActivityName());
    assertEquals("Name", getResult2.getActivityName());
    assertEquals("Name", getResult.getProcessDefinitionName());
    assertEquals("Name", getResult2.getProcessDefinitionName());
    assertEquals("SEQ_FLOW_INVALID_SRC", getResult.getDefaultDescription());
    assertEquals("SEQ_FLOW_INVALID_SRC", getResult.getKey());
    assertEquals("SEQ_FLOW_INVALID_SRC", getResult.getProblem());
    assertEquals("SEQ_FLOW_INVALID_TARGET", getResult2.getDefaultDescription());
    assertEquals("SEQ_FLOW_INVALID_TARGET", getResult2.getKey());
    assertEquals("SEQ_FLOW_INVALID_TARGET", getResult2.getProblem());
    assertNull(getResult.getValidatorSetName());
    assertNull(getResult2.getValidatorSetName());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult2.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
    assertEquals(10, getResult2.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertFalse(getResult2.isWarning());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
  }

  /**
   * Test {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>When {@link Process} {@link Process#getFlowElement(String, boolean)} return {@link
   *       AdhocSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); when Process getFlowElement(String, boolean) return AdhocSubProcess (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SequenceflowValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_whenProcessGetFlowElementReturnAdhocSubProcess() {
    // Arrange
    SequenceflowValidator sequenceflowValidator = new SequenceflowValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(new SequenceFlow());

    Process process = mock(Process.class);
    when(process.getFlowElementsContainer(Mockito.<String>any())).thenReturn(new AdhocSubProcess());
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
    verify(process, atLeast(1)).getFlowElement(null, true);
    verify(process, atLeast(1)).getFlowElementsContainer(null);
    verify(process, atLeast(1)).getName();
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(0);
    assertNull(getResult.getActivityId());
    ValidationError getResult2 = errors.get(1);
    assertNull(getResult2.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult2.getActivityName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertEquals(0, getResult2.getXmlLineNumber());
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
