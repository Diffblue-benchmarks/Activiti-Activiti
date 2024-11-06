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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.Signal;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SequenceflowValidatorDiffblueTest {
  /**
   * Test
   * {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   * <p>
   * Method under test:
   * {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  void testExecuteValidation() {
    // Arrange
    SequenceflowValidator sequenceflowValidator = new SequenceflowValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(new SequenceFlow(null, "Target Ref"));
    FlowElement flowElement = mock(FlowElement.class);
    when(flowElement.getId()).thenReturn("42");
    Process process = mock(Process.class);
    when(process.getFlowElementsContainer(Mockito.<String>any())).thenReturn(new AdhocSubProcess());
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(flowElement);
    when(process.findFlowElementsOfType(Mockito.<Class<SequenceFlow>>any())).thenReturn(sequenceFlowList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sequenceflowValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process).getId();
    verify(flowElement, atLeast(1)).getId();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getFlowElement(Mockito.<String>any(), eq(true));
    verify(process, atLeast(1)).getFlowElementsContainer(eq("42"));
    verify(process).getName();
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
  }

  /**
   * Test
   * {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   * <p>
   * Method under test:
   * {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  void testExecuteValidation2() {
    // Arrange
    SequenceflowValidator sequenceflowValidator = new SequenceflowValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(new SequenceFlow("", "Target Ref"));
    FlowElement flowElement = mock(FlowElement.class);
    when(flowElement.getId()).thenReturn("42");
    Process process = mock(Process.class);
    when(process.getFlowElementsContainer(Mockito.<String>any())).thenReturn(new AdhocSubProcess());
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(flowElement);
    when(process.findFlowElementsOfType(Mockito.<Class<SequenceFlow>>any())).thenReturn(sequenceFlowList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sequenceflowValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process).getId();
    verify(flowElement, atLeast(1)).getId();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getFlowElement(Mockito.<String>any(), eq(true));
    verify(process, atLeast(1)).getFlowElementsContainer(eq("42"));
    verify(process).getName();
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
  }

  /**
   * Test
   * {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   * <p>
   * Method under test:
   * {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  void testExecuteValidation3() {
    // Arrange
    SequenceflowValidator sequenceflowValidator = new SequenceflowValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(new SequenceFlow("Source Ref", null));
    FlowElement flowElement = mock(FlowElement.class);
    when(flowElement.getId()).thenReturn("42");
    Process process = mock(Process.class);
    when(process.getFlowElementsContainer(Mockito.<String>any())).thenReturn(new AdhocSubProcess());
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(flowElement);
    when(process.findFlowElementsOfType(Mockito.<Class<SequenceFlow>>any())).thenReturn(sequenceFlowList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sequenceflowValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process).getId();
    verify(flowElement, atLeast(1)).getId();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getFlowElement(Mockito.<String>any(), eq(true));
    verify(process, atLeast(1)).getFlowElementsContainer(eq("42"));
    verify(process).getName();
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("SEQ_FLOW_INVALID_TARGET", getResult.getDefaultDescription());
    assertEquals("SEQ_FLOW_INVALID_TARGET", getResult.getKey());
    assertEquals("SEQ_FLOW_INVALID_TARGET", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
  }

  /**
   * Test
   * {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add
   * {@link SequenceFlow#SequenceFlow(String, String)} with {@code Source Ref} and
   * {@code Target Ref}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given ArrayList() add SequenceFlow(String, String) with 'Source Ref' and 'Target Ref'")
  void testExecuteValidation_givenArrayListAddSequenceFlowWithSourceRefAndTargetRef() {
    // Arrange
    SequenceflowValidator sequenceflowValidator = new SequenceflowValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(new SequenceFlow("Source Ref", "Target Ref"));
    FlowElement flowElement = mock(FlowElement.class);
    when(flowElement.getId()).thenReturn("42");
    Process process = mock(Process.class);
    when(process.getFlowElementsContainer(Mockito.<String>any())).thenReturn(new AdhocSubProcess());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(flowElement);
    when(process.findFlowElementsOfType(Mockito.<Class<SequenceFlow>>any())).thenReturn(sequenceFlowList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sequenceflowValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(flowElement, atLeast(1)).getId();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getFlowElement(Mockito.<String>any(), eq(true));
    verify(process, atLeast(1)).getFlowElementsContainer(eq("42"));
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test
   * {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then {@link BpmnModel} (default constructor) Resources {@link List}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given ArrayList(); then BpmnModel (default constructor) Resources List")
  void testExecuteValidation_givenArrayList_thenBpmnModelResourcesList() {
    // Arrange
    SequenceflowValidator sequenceflowValidator = new SequenceflowValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<SequenceFlow>>any())).thenReturn(new ArrayList<>());
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sequenceflowValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(process).findFlowElementsOfType(isA(Class.class));
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test
   * {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link SequenceFlow} {@link SequenceFlow#getSourceRef()} return
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given SequenceFlow getSourceRef() return 'null'")
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
    FlowElement flowElement = mock(FlowElement.class);
    when(flowElement.getId()).thenReturn("42");
    Process process = mock(Process.class);
    when(process.getFlowElementsContainer(Mockito.<String>any())).thenReturn(new AdhocSubProcess());
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(flowElement);
    when(process.findFlowElementsOfType(Mockito.<Class<SequenceFlow>>any())).thenReturn(sequenceFlowList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sequenceflowValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process).getId();
    verify(sequenceFlow).getId();
    verify(flowElement, atLeast(1)).getId();
    verify(sequenceFlow).getXmlColumnNumber();
    verify(sequenceFlow).getXmlRowNumber();
    verify(sequenceFlow).getName();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getFlowElement(Mockito.<String>any(), eq(true));
    verify(process, atLeast(1)).getFlowElementsContainer(eq("42"));
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
   * Test
   * {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link SequenceFlow} {@link SequenceFlow#getSourceRef()} return
   * {@code Source Ref}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given SequenceFlow getSourceRef() return 'Source Ref'")
  void testExecuteValidation_givenSequenceFlowGetSourceRefReturnSourceRef() {
    // Arrange
    SequenceflowValidator sequenceflowValidator = new SequenceflowValidator();
    BpmnModel bpmnModel = new BpmnModel();
    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getId()).thenReturn("42");
    when(sequenceFlow.getConditionExpression()).thenReturn("Condition Expression");
    when(sequenceFlow.getSourceRef()).thenReturn("Source Ref");
    when(sequenceFlow.getTargetRef()).thenReturn("Target Ref");

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(sequenceFlow);
    FlowElement flowElement = mock(FlowElement.class);
    when(flowElement.getId()).thenReturn("42");
    Process process = mock(Process.class);
    when(process.getFlowElementsContainer(Mockito.<String>any())).thenReturn(new AdhocSubProcess());
    when(process.getId()).thenReturn("42");
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(flowElement);
    when(process.findFlowElementsOfType(Mockito.<Class<SequenceFlow>>any())).thenReturn(sequenceFlowList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sequenceflowValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(flowElement, atLeast(1)).getId();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getFlowElement(Mockito.<String>any(), eq(true));
    verify(process, atLeast(1)).getFlowElementsContainer(eq("42"));
    verify(sequenceFlow).getConditionExpression();
    verify(sequenceFlow).getSourceRef();
    verify(sequenceFlow).getTargetRef();
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test
   * {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link SequenceFlow} {@link SequenceFlow#getTargetRef()} return
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given SequenceFlow getTargetRef() return 'null'")
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
    FlowElement flowElement = mock(FlowElement.class);
    when(flowElement.getId()).thenReturn("42");
    Process process = mock(Process.class);
    when(process.getFlowElementsContainer(Mockito.<String>any())).thenReturn(new AdhocSubProcess());
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(flowElement);
    when(process.findFlowElementsOfType(Mockito.<Class<SequenceFlow>>any())).thenReturn(sequenceFlowList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sequenceflowValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process).getId();
    verify(sequenceFlow).getId();
    verify(flowElement, atLeast(1)).getId();
    verify(sequenceFlow).getXmlColumnNumber();
    verify(sequenceFlow).getXmlRowNumber();
    verify(sequenceFlow).getName();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getFlowElement(Mockito.<String>any(), eq(true));
    verify(process, atLeast(1)).getFlowElementsContainer(eq("42"));
    verify(process).getName();
    verify(sequenceFlow).getConditionExpression();
    verify(sequenceFlow).getSourceRef();
    verify(sequenceFlow).getTargetRef();
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("Name", getResult.getActivityName());
    assertEquals("SEQ_FLOW_INVALID_TARGET", getResult.getDefaultDescription());
    assertEquals("SEQ_FLOW_INVALID_TARGET", getResult.getKey());
    assertEquals("SEQ_FLOW_INVALID_TARGET", getResult.getProblem());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
  }

  /**
   * Test
   * {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} second ActivityId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() second ActivityId is '42'")
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
    FlowElement flowElement = mock(FlowElement.class);
    when(flowElement.getId()).thenReturn("42");
    Process process = mock(Process.class);
    when(process.getFlowElementsContainer(Mockito.<String>any())).thenReturn(null);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(flowElement);
    when(process.findFlowElementsOfType(Mockito.<Class<SequenceFlow>>any())).thenReturn(sequenceFlowList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sequenceflowValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(flowElement, atLeast(1)).getId();
    verify(process, atLeast(1)).getId();
    verify(sequenceFlow, atLeast(1)).getId();
    verify(sequenceFlow, atLeast(1)).getXmlColumnNumber();
    verify(sequenceFlow, atLeast(1)).getXmlRowNumber();
    verify(sequenceFlow, atLeast(1)).getName();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getFlowElement(Mockito.<String>any(), eq(true));
    verify(process, atLeast(1)).getFlowElementsContainer(eq("42"));
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
   * Test
   * {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} second ActivityId is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() second ActivityId is 'null'")
  void testExecuteValidation_thenArrayListSecondActivityIdIsNull() {
    // Arrange
    SequenceflowValidator sequenceflowValidator = new SequenceflowValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(new SequenceFlow("Source Ref", "Target Ref"));
    Process process = mock(Process.class);
    when(process.getFlowElementsContainer(Mockito.<String>any())).thenReturn(null);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(new AdhocSubProcess());
    when(process.findFlowElementsOfType(Mockito.<Class<SequenceFlow>>any())).thenReturn(sequenceFlowList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sequenceflowValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process, atLeast(1)).getId();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getFlowElement(Mockito.<String>any(), eq(true));
    verify(process, atLeast(1)).getFlowElementsContainer(isNull());
    verify(process, atLeast(1)).getName();
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
  }

  /**
   * Test
   * {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>When {@link BpmnModel}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); when BpmnModel; then ArrayList() Empty")
  void testExecuteValidation_whenBpmnModel_thenArrayListEmpty() {
    // Arrange
    SequenceflowValidator sequenceflowValidator = new SequenceflowValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sequenceflowValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test
   * {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>When {@link Process} {@link Process#getFlowElement(String, boolean)}
   * return {@link AdhocSubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); when Process getFlowElement(String, boolean) return AdhocSubProcess (default constructor)")
  void testExecuteValidation_whenProcessGetFlowElementReturnAdhocSubProcess() {
    // Arrange
    SequenceflowValidator sequenceflowValidator = new SequenceflowValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(new SequenceFlow("Source Ref", "Target Ref"));
    Process process = mock(Process.class);
    when(process.getFlowElementsContainer(Mockito.<String>any())).thenReturn(new AdhocSubProcess());
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(new AdhocSubProcess());
    when(process.findFlowElementsOfType(Mockito.<Class<SequenceFlow>>any())).thenReturn(sequenceFlowList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sequenceflowValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getFlowElement(Mockito.<String>any(), eq(true));
    verify(process, atLeast(1)).getFlowElementsContainer(isNull());
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test
   * {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>When {@link Process} {@link Process#getFlowElement(String, boolean)}
   * return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); when Process getFlowElement(String, boolean) return 'null'")
  void testExecuteValidation_whenProcessGetFlowElementReturnNull() {
    // Arrange
    SequenceflowValidator sequenceflowValidator = new SequenceflowValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(new SequenceFlow("Source Ref", "Target Ref"));
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(null);
    when(process.findFlowElementsOfType(Mockito.<Class<SequenceFlow>>any())).thenReturn(sequenceFlowList);
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
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
  }

  /**
   * Test
   * {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>When {@link Process} (default constructor).</li>
   *   <li>Then {@link BpmnModel} (default constructor) Resources {@link List}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceflowValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); when Process (default constructor); then BpmnModel (default constructor) Resources List")
  void testExecuteValidation_whenProcess_thenBpmnModelResourcesList() {
    // Arrange
    SequenceflowValidator sequenceflowValidator = new SequenceflowValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    sequenceflowValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }
}
