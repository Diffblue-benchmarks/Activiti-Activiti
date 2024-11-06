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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.ExclusiveGateway;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.Signal;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ExclusiveGatewayValidatorDiffblueTest {
  /**
   * Test
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}.
   * <p>
   * Method under test:
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  void testExecuteValidation() {
    // Arrange
    ExclusiveGatewayValidator exclusiveGatewayValidator = new ExclusiveGatewayValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<ExclusiveGateway> exclusiveGatewayList = new ArrayList<>();
    exclusiveGatewayList.add(new ExclusiveGateway());
    exclusiveGatewayList.add(new ExclusiveGateway());
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<ExclusiveGateway>>any())).thenReturn(exclusiveGatewayList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    exclusiveGatewayValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process, atLeast(1)).getId();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getName();
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult.getDefaultDescription());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult.getKey());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
  }

  /**
   * Test
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}.
   * <p>
   * Method under test:
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  void testExecuteValidation2() {
    // Arrange
    ExclusiveGatewayValidator exclusiveGatewayValidator = new ExclusiveGatewayValidator();
    BpmnModel bpmnModel = new BpmnModel();
    ExclusiveGateway exclusiveGateway = mock(ExclusiveGateway.class);
    when(exclusiveGateway.getXmlColumnNumber()).thenReturn(10);
    when(exclusiveGateway.getXmlRowNumber()).thenReturn(10);
    when(exclusiveGateway.getId()).thenReturn("42");
    when(exclusiveGateway.getName()).thenReturn("Name");
    when(exclusiveGateway.getOutgoingFlows()).thenReturn(new ArrayList<>());

    ArrayList<ExclusiveGateway> exclusiveGatewayList = new ArrayList<>();
    exclusiveGatewayList.add(exclusiveGateway);
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<ExclusiveGateway>>any())).thenReturn(exclusiveGatewayList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    exclusiveGatewayValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(exclusiveGateway).getId();
    verify(process).getId();
    verify(exclusiveGateway).getXmlColumnNumber();
    verify(exclusiveGateway).getXmlRowNumber();
    verify(exclusiveGateway).getName();
    verify(exclusiveGateway).getOutgoingFlows();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getName();
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult.getDefaultDescription());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult.getKey());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult.getProblem());
    assertFalse(getResult.isWarning());
  }

  /**
   * Test
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}.
   * <p>
   * Method under test:
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  void testExecuteValidation3() {
    // Arrange
    ExclusiveGatewayValidator exclusiveGatewayValidator = new ExclusiveGatewayValidator();
    BpmnModel bpmnModel = new BpmnModel();
    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getId()).thenReturn("42");
    when(sequenceFlow.getConditionExpression()).thenReturn("Condition Expression");

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(sequenceFlow);
    sequenceFlowList
        .add(new SequenceFlow("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", "EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW"));
    ExclusiveGateway exclusiveGateway = mock(ExclusiveGateway.class);
    when(exclusiveGateway.getDefaultFlow()).thenReturn("Default Flow");
    when(exclusiveGateway.getXmlColumnNumber()).thenReturn(10);
    when(exclusiveGateway.getXmlRowNumber()).thenReturn(10);
    when(exclusiveGateway.getId()).thenReturn("42");
    when(exclusiveGateway.getName()).thenReturn("Name");
    when(exclusiveGateway.getOutgoingFlows()).thenReturn(sequenceFlowList);

    ArrayList<ExclusiveGateway> exclusiveGatewayList = new ArrayList<>();
    exclusiveGatewayList.add(exclusiveGateway);
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<ExclusiveGateway>>any())).thenReturn(exclusiveGatewayList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    exclusiveGatewayValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(exclusiveGateway).getId();
    verify(process).getId();
    verify(sequenceFlow, atLeast(1)).getId();
    verify(exclusiveGateway).getXmlColumnNumber();
    verify(exclusiveGateway).getXmlRowNumber();
    verify(exclusiveGateway).getName();
    verify(exclusiveGateway, atLeast(1)).getOutgoingFlows();
    verify(exclusiveGateway).getDefaultFlow();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getName();
    verify(sequenceFlow).getConditionExpression();
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("EXCLUSIVE_GATEWAY_SEQ_FLOW_WITHOUT_CONDITIONS", getResult.getDefaultDescription());
    assertEquals("EXCLUSIVE_GATEWAY_SEQ_FLOW_WITHOUT_CONDITIONS", getResult.getKey());
    assertEquals("EXCLUSIVE_GATEWAY_SEQ_FLOW_WITHOUT_CONDITIONS", getResult.getProblem());
    assertTrue(getResult.isWarning());
  }

  /**
   * Test
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}.
   * <p>
   * Method under test:
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  void testExecuteValidation4() {
    // Arrange
    ExclusiveGatewayValidator exclusiveGatewayValidator = new ExclusiveGatewayValidator();
    BpmnModel bpmnModel = new BpmnModel();
    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getId()).thenReturn("42");
    when(sequenceFlow.getConditionExpression()).thenReturn("Condition Expression");
    SequenceFlow sequenceFlow2 = mock(SequenceFlow.class);
    when(sequenceFlow2.getId()).thenReturn("42");
    when(sequenceFlow2.getConditionExpression()).thenReturn("Condition Expression");

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(sequenceFlow2);
    sequenceFlowList.add(sequenceFlow);
    ExclusiveGateway exclusiveGateway = mock(ExclusiveGateway.class);
    when(exclusiveGateway.getDefaultFlow()).thenReturn("Default Flow");
    when(exclusiveGateway.getId()).thenReturn("42");
    when(exclusiveGateway.getOutgoingFlows()).thenReturn(sequenceFlowList);

    ArrayList<ExclusiveGateway> exclusiveGatewayList = new ArrayList<>();
    exclusiveGatewayList.add(exclusiveGateway);
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.findFlowElementsOfType(Mockito.<Class<ExclusiveGateway>>any())).thenReturn(exclusiveGatewayList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    exclusiveGatewayValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(sequenceFlow2, atLeast(1)).getId();
    verify(sequenceFlow, atLeast(1)).getId();
    verify(exclusiveGateway, atLeast(1)).getOutgoingFlows();
    verify(exclusiveGateway).getDefaultFlow();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(sequenceFlow2).getConditionExpression();
    verify(sequenceFlow).getConditionExpression();
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
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add
   * {@link SequenceFlow#SequenceFlow(String, String)} with {@code Source Ref} and
   * {@code Target Ref}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given ArrayList() add SequenceFlow(String, String) with 'Source Ref' and 'Target Ref'")
  void testExecuteValidation_givenArrayListAddSequenceFlowWithSourceRefAndTargetRef() {
    // Arrange
    ExclusiveGatewayValidator exclusiveGatewayValidator = new ExclusiveGatewayValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(new SequenceFlow("Source Ref", "Target Ref"));
    sequenceFlowList
        .add(new SequenceFlow("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", "EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW"));
    ExclusiveGateway exclusiveGateway = mock(ExclusiveGateway.class);
    when(exclusiveGateway.getDefaultFlow()).thenReturn("Default Flow");
    when(exclusiveGateway.getXmlColumnNumber()).thenReturn(10);
    when(exclusiveGateway.getXmlRowNumber()).thenReturn(10);
    when(exclusiveGateway.getId()).thenReturn("42");
    when(exclusiveGateway.getName()).thenReturn("Name");
    when(exclusiveGateway.getOutgoingFlows()).thenReturn(sequenceFlowList);

    ArrayList<ExclusiveGateway> exclusiveGatewayList = new ArrayList<>();
    exclusiveGatewayList.add(exclusiveGateway);
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<ExclusiveGateway>>any())).thenReturn(exclusiveGatewayList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    exclusiveGatewayValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(exclusiveGateway).getId();
    verify(process).getId();
    verify(exclusiveGateway).getXmlColumnNumber();
    verify(exclusiveGateway).getXmlRowNumber();
    verify(exclusiveGateway).getName();
    verify(exclusiveGateway, atLeast(1)).getOutgoingFlows();
    verify(exclusiveGateway).getDefaultFlow();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getName();
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("EXCLUSIVE_GATEWAY_SEQ_FLOW_WITHOUT_CONDITIONS", getResult.getDefaultDescription());
    assertEquals("EXCLUSIVE_GATEWAY_SEQ_FLOW_WITHOUT_CONDITIONS", getResult.getKey());
    assertEquals("EXCLUSIVE_GATEWAY_SEQ_FLOW_WITHOUT_CONDITIONS", getResult.getProblem());
    assertTrue(getResult.isWarning());
  }

  /**
   * Test
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then {@link BpmnModel} (default constructor) Resources {@link List}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given ArrayList(); then BpmnModel (default constructor) Resources List")
  void testExecuteValidation_givenArrayList_thenBpmnModelResourcesList() {
    // Arrange
    ExclusiveGatewayValidator exclusiveGatewayValidator = new ExclusiveGatewayValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<ExclusiveGateway>>any())).thenReturn(new ArrayList<>());
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    exclusiveGatewayValidator.executeValidation(bpmnModel, process, errors);

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
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link SequenceFlow} {@link SequenceFlow#getConditionExpression()}
   * return empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given SequenceFlow getConditionExpression() return empty string")
  void testExecuteValidation_givenSequenceFlowGetConditionExpressionReturnEmptyString() {
    // Arrange
    ExclusiveGatewayValidator exclusiveGatewayValidator = new ExclusiveGatewayValidator();
    BpmnModel bpmnModel = new BpmnModel();
    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getId()).thenReturn("42");
    when(sequenceFlow.getConditionExpression()).thenReturn("");

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(sequenceFlow);
    sequenceFlowList
        .add(new SequenceFlow("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", "EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW"));
    ExclusiveGateway exclusiveGateway = mock(ExclusiveGateway.class);
    when(exclusiveGateway.getDefaultFlow()).thenReturn("Default Flow");
    when(exclusiveGateway.getXmlColumnNumber()).thenReturn(10);
    when(exclusiveGateway.getXmlRowNumber()).thenReturn(10);
    when(exclusiveGateway.getId()).thenReturn("42");
    when(exclusiveGateway.getName()).thenReturn("Name");
    when(exclusiveGateway.getOutgoingFlows()).thenReturn(sequenceFlowList);

    ArrayList<ExclusiveGateway> exclusiveGatewayList = new ArrayList<>();
    exclusiveGatewayList.add(exclusiveGateway);
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<ExclusiveGateway>>any())).thenReturn(exclusiveGatewayList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    exclusiveGatewayValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(exclusiveGateway).getId();
    verify(process).getId();
    verify(sequenceFlow, atLeast(1)).getId();
    verify(exclusiveGateway).getXmlColumnNumber();
    verify(exclusiveGateway).getXmlRowNumber();
    verify(exclusiveGateway).getName();
    verify(exclusiveGateway, atLeast(1)).getOutgoingFlows();
    verify(exclusiveGateway).getDefaultFlow();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getName();
    verify(sequenceFlow).getConditionExpression();
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("EXCLUSIVE_GATEWAY_SEQ_FLOW_WITHOUT_CONDITIONS", getResult.getDefaultDescription());
    assertEquals("EXCLUSIVE_GATEWAY_SEQ_FLOW_WITHOUT_CONDITIONS", getResult.getKey());
    assertEquals("EXCLUSIVE_GATEWAY_SEQ_FLOW_WITHOUT_CONDITIONS", getResult.getProblem());
    assertTrue(getResult.isWarning());
  }

  /**
   * Test
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} first ActivityId is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() first ActivityId is 'null'")
  void testExecuteValidation_thenArrayListFirstActivityIdIsNull() {
    // Arrange
    ExclusiveGatewayValidator exclusiveGatewayValidator = new ExclusiveGatewayValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<ExclusiveGateway> exclusiveGatewayList = new ArrayList<>();
    exclusiveGatewayList.add(new ExclusiveGateway());
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<ExclusiveGateway>>any())).thenReturn(exclusiveGatewayList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    exclusiveGatewayValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process).getId();
    verify(process).findFlowElementsOfType(isA(Class.class));
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
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} second ActivityId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() second ActivityId is '42'")
  void testExecuteValidation_thenArrayListSecondActivityIdIs42() {
    // Arrange
    ExclusiveGatewayValidator exclusiveGatewayValidator = new ExclusiveGatewayValidator();
    BpmnModel bpmnModel = new BpmnModel();
    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getId()).thenReturn("42");
    when(sequenceFlow.getConditionExpression()).thenReturn("Condition Expression");

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(sequenceFlow);
    sequenceFlowList
        .add(new SequenceFlow("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", "EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW"));
    ExclusiveGateway exclusiveGateway = mock(ExclusiveGateway.class);
    when(exclusiveGateway.getDefaultFlow()).thenReturn("42");
    when(exclusiveGateway.getXmlColumnNumber()).thenReturn(10);
    when(exclusiveGateway.getXmlRowNumber()).thenReturn(10);
    when(exclusiveGateway.getId()).thenReturn("42");
    when(exclusiveGateway.getName()).thenReturn("Name");
    when(exclusiveGateway.getOutgoingFlows()).thenReturn(sequenceFlowList);

    ArrayList<ExclusiveGateway> exclusiveGatewayList = new ArrayList<>();
    exclusiveGatewayList.add(exclusiveGateway);
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<ExclusiveGateway>>any())).thenReturn(exclusiveGatewayList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    exclusiveGatewayValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(exclusiveGateway, atLeast(1)).getId();
    verify(process, atLeast(1)).getId();
    verify(sequenceFlow, atLeast(1)).getId();
    verify(exclusiveGateway, atLeast(1)).getXmlColumnNumber();
    verify(exclusiveGateway, atLeast(1)).getXmlRowNumber();
    verify(exclusiveGateway, atLeast(1)).getName();
    verify(exclusiveGateway, atLeast(1)).getOutgoingFlows();
    verify(exclusiveGateway).getDefaultFlow();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getName();
    verify(sequenceFlow).getConditionExpression();
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("42", getResult.getActivityId());
    ValidationError getResult2 = errors.get(0);
    assertEquals("EXCLUSIVE_GATEWAY_CONDITION_ON_DEFAULT_SEQ_FLOW", getResult2.getDefaultDescription());
    assertEquals("EXCLUSIVE_GATEWAY_CONDITION_ON_DEFAULT_SEQ_FLOW", getResult2.getKey());
    assertEquals("EXCLUSIVE_GATEWAY_CONDITION_ON_DEFAULT_SEQ_FLOW", getResult2.getProblem());
    assertEquals("EXCLUSIVE_GATEWAY_SEQ_FLOW_WITHOUT_CONDITIONS", getResult.getDefaultDescription());
    assertEquals("EXCLUSIVE_GATEWAY_SEQ_FLOW_WITHOUT_CONDITIONS", getResult.getKey());
    assertEquals("EXCLUSIVE_GATEWAY_SEQ_FLOW_WITHOUT_CONDITIONS", getResult.getProblem());
    assertEquals("Name", getResult.getActivityName());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
    assertTrue(getResult.isWarning());
  }

  /**
   * Test
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Then {@link BpmnModel} (default constructor) Resources {@link List}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then BpmnModel (default constructor) Resources List")
  void testExecuteValidation_thenBpmnModelResourcesList() {
    // Arrange
    ExclusiveGatewayValidator exclusiveGatewayValidator = new ExclusiveGatewayValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList
        .add(new SequenceFlow("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", "EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW"));
    ExclusiveGateway exclusiveGateway = mock(ExclusiveGateway.class);
    when(exclusiveGateway.getOutgoingFlows()).thenReturn(sequenceFlowList);

    ArrayList<ExclusiveGateway> exclusiveGatewayList = new ArrayList<>();
    exclusiveGatewayList.add(exclusiveGateway);
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<ExclusiveGateway>>any())).thenReturn(exclusiveGatewayList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    exclusiveGatewayValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(exclusiveGateway, atLeast(1)).getOutgoingFlows();
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
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>When {@link BpmnModel}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); when BpmnModel; then ArrayList() Empty")
  void testExecuteValidation_whenBpmnModel_thenArrayListEmpty() {
    // Arrange
    ExclusiveGatewayValidator exclusiveGatewayValidator = new ExclusiveGatewayValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    exclusiveGatewayValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>When {@link Process} (default constructor).</li>
   *   <li>Then {@link BpmnModel} (default constructor) Resources {@link List}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); when Process (default constructor); then BpmnModel (default constructor) Resources List")
  void testExecuteValidation_whenProcess_thenBpmnModelResourcesList() {
    // Arrange
    ExclusiveGatewayValidator exclusiveGatewayValidator = new ExclusiveGatewayValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    exclusiveGatewayValidator.executeValidation(bpmnModel, process, errors);

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
   * Test
   * {@link ExclusiveGatewayValidator#validateExclusiveGateway(Process, ExclusiveGateway, List)}.
   * <p>
   * Method under test:
   * {@link ExclusiveGatewayValidator#validateExclusiveGateway(Process, ExclusiveGateway, List)}
   */
  @Test
  @DisplayName("Test validateExclusiveGateway(Process, ExclusiveGateway, List)")
  void testValidateExclusiveGateway() {
    // Arrange
    ExclusiveGatewayValidator exclusiveGatewayValidator = new ExclusiveGatewayValidator();
    Process process = new Process();
    ExclusiveGateway exclusiveGateway = new ExclusiveGateway();

    HashMap<String, String> params = new HashMap<>();
    params.computeIfPresent("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", mock(BiFunction.class));

    ValidationError validationError = new ValidationError();
    validationError.setActivityId("42");
    validationError.setActivityName("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW");
    validationError.setDefaultDescription("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW");
    validationError.setKey("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW");
    validationError.setParams(params);
    validationError.setProblem("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW");
    validationError.setProcessDefinitionId("42");
    validationError.setProcessDefinitionName("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW");
    validationError.setValidatorSetName("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW");
    validationError.setWarning(true);
    validationError.setXmlColumnNumber(10);
    validationError.setXmlLineNumber(2);

    ArrayList<ValidationError> errors = new ArrayList<>();
    errors.add(validationError);

    // Act
    exclusiveGatewayValidator.validateExclusiveGateway(process, exclusiveGateway, errors);

    // Assert
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult.getActivityName());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult.getDefaultDescription());
    ValidationError getResult2 = errors.get(1);
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult2.getDefaultDescription());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult.getKey());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult2.getKey());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult.getProblem());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult2.getProblem());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult.getProcessDefinitionName());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult.getValidatorSetName());
    assertNull(getResult2.getActivityId());
    assertNull(getResult2.getActivityName());
    assertNull(getResult2.getProcessDefinitionId());
    assertNull(getResult2.getProcessDefinitionName());
    assertNull(getResult2.getValidatorSetName());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlLineNumber());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(2, getResult.getXmlLineNumber());
    assertFalse(getResult2.isWarning());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Test
   * {@link ExclusiveGatewayValidator#validateExclusiveGateway(Process, ExclusiveGateway, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExclusiveGatewayValidator#validateExclusiveGateway(Process, ExclusiveGateway, List)}
   */
  @Test
  @DisplayName("Test validateExclusiveGateway(Process, ExclusiveGateway, List); then ArrayList() size is two")
  void testValidateExclusiveGateway_thenArrayListSizeIsTwo() {
    // Arrange
    ExclusiveGatewayValidator exclusiveGatewayValidator = new ExclusiveGatewayValidator();
    Process process = new Process();
    ExclusiveGateway exclusiveGateway = new ExclusiveGateway();

    ValidationError validationError = new ValidationError();
    validationError.setActivityId("42");
    validationError.setActivityName("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW");
    validationError.setDefaultDescription("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW");
    validationError.setKey("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW");
    validationError.setParams(new HashMap<>());
    validationError.setProblem("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW");
    validationError.setProcessDefinitionId("42");
    validationError.setProcessDefinitionName("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW");
    validationError.setValidatorSetName("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW");
    validationError.setWarning(true);
    validationError.setXmlColumnNumber(10);
    validationError.setXmlLineNumber(2);

    ArrayList<ValidationError> errors = new ArrayList<>();
    errors.add(validationError);

    // Act
    exclusiveGatewayValidator.validateExclusiveGateway(process, exclusiveGateway, errors);

    // Assert
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult.getActivityName());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult.getDefaultDescription());
    ValidationError getResult2 = errors.get(1);
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult2.getDefaultDescription());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult.getKey());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult2.getKey());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult.getProblem());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult2.getProblem());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult.getProcessDefinitionName());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult.getValidatorSetName());
    assertNull(getResult2.getActivityId());
    assertNull(getResult2.getActivityName());
    assertNull(getResult2.getProcessDefinitionId());
    assertNull(getResult2.getProcessDefinitionName());
    assertNull(getResult2.getValidatorSetName());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlLineNumber());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(2, getResult.getXmlLineNumber());
    assertFalse(getResult2.isWarning());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Test
   * {@link ExclusiveGatewayValidator#validateExclusiveGateway(Process, ExclusiveGateway, List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then {@link Process} (default constructor) FlowElements
   * {@link List}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExclusiveGatewayValidator#validateExclusiveGateway(Process, ExclusiveGateway, List)}
   */
  @Test
  @DisplayName("Test validateExclusiveGateway(Process, ExclusiveGateway, List); when ArrayList(); then Process (default constructor) FlowElements List")
  void testValidateExclusiveGateway_whenArrayList_thenProcessFlowElementsList() {
    // Arrange
    ExclusiveGatewayValidator exclusiveGatewayValidator = new ExclusiveGatewayValidator();
    Process process = new Process();
    ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    exclusiveGatewayValidator.validateExclusiveGateway(process, exclusiveGateway, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult.getDefaultDescription());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult.getKey());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test
   * {@link ExclusiveGatewayValidator#validateExclusiveGateway(Process, ExclusiveGateway, List)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExclusiveGatewayValidator#validateExclusiveGateway(Process, ExclusiveGateway, List)}
   */
  @Test
  @DisplayName("Test validateExclusiveGateway(Process, ExclusiveGateway, List); when 'null'; then ArrayList() size is one")
  void testValidateExclusiveGateway_whenNull_thenArrayListSizeIsOne() {
    // Arrange
    ExclusiveGatewayValidator exclusiveGatewayValidator = new ExclusiveGatewayValidator();
    ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    exclusiveGatewayValidator.validateExclusiveGateway(null, exclusiveGateway, errors);

    // Assert
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult.getDefaultDescription());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult.getKey());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult.getProblem());
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
