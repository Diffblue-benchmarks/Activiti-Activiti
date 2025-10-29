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
import static org.junit.jupiter.api.Assertions.assertSame;
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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ExclusiveGatewayValidatorDiffblueTest {
  /**
   * Method under test:
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation() {
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
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation2() {
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
   * Method under test:
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation3() {
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
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation4() {
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
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult.getDefaultDescription());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult.getKey());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult.getProblem());
    assertEquals("Name", getResult.getProcessDefinitionName());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation5() {
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
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getProcessDefinitionId());
    ValidationError getResult2 = errors.get(1);
    assertEquals("42", getResult2.getProcessDefinitionId());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult.getDefaultDescription());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult2.getDefaultDescription());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult.getKey());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult2.getKey());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult.getProblem());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult2.getProblem());
    assertEquals("Name", getResult.getProcessDefinitionName());
    assertEquals("Name", getResult2.getProcessDefinitionName());
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
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation6() {
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
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult.getDefaultDescription());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult.getKey());
    assertEquals("EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW", getResult.getProblem());
    assertEquals("Name", getResult.getActivityName());
    assertEquals("Name", getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation7() {
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
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation8() {
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
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("EXCLUSIVE_GATEWAY_SEQ_FLOW_WITHOUT_CONDITIONS", getResult.getDefaultDescription());
    assertEquals("EXCLUSIVE_GATEWAY_SEQ_FLOW_WITHOUT_CONDITIONS", getResult.getKey());
    assertEquals("EXCLUSIVE_GATEWAY_SEQ_FLOW_WITHOUT_CONDITIONS", getResult.getProblem());
    assertEquals("Name", getResult.getActivityName());
    assertEquals("Name", getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Method under test:
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation9() {
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
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("EXCLUSIVE_GATEWAY_SEQ_FLOW_WITHOUT_CONDITIONS", getResult.getDefaultDescription());
    assertEquals("EXCLUSIVE_GATEWAY_SEQ_FLOW_WITHOUT_CONDITIONS", getResult.getKey());
    assertEquals("EXCLUSIVE_GATEWAY_SEQ_FLOW_WITHOUT_CONDITIONS", getResult.getProblem());
    assertEquals("Name", getResult.getActivityName());
    assertEquals("Name", getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Method under test:
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation10() {
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
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getActivityId());
    ValidationError getResult2 = errors.get(1);
    assertEquals("42", getResult2.getActivityId());
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("42", getResult2.getProcessDefinitionId());
    assertEquals("EXCLUSIVE_GATEWAY_CONDITION_ON_DEFAULT_SEQ_FLOW", getResult.getDefaultDescription());
    assertEquals("EXCLUSIVE_GATEWAY_CONDITION_ON_DEFAULT_SEQ_FLOW", getResult.getKey());
    assertEquals("EXCLUSIVE_GATEWAY_CONDITION_ON_DEFAULT_SEQ_FLOW", getResult.getProblem());
    assertEquals("EXCLUSIVE_GATEWAY_SEQ_FLOW_WITHOUT_CONDITIONS", getResult2.getDefaultDescription());
    assertEquals("EXCLUSIVE_GATEWAY_SEQ_FLOW_WITHOUT_CONDITIONS", getResult2.getKey());
    assertEquals("EXCLUSIVE_GATEWAY_SEQ_FLOW_WITHOUT_CONDITIONS", getResult2.getProblem());
    assertEquals("Name", getResult.getActivityName());
    assertEquals("Name", getResult2.getActivityName());
    assertEquals("Name", getResult.getProcessDefinitionName());
    assertEquals("Name", getResult2.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertNull(getResult2.getValidatorSetName());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult2.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
    assertEquals(10, getResult2.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult2.getParams().isEmpty());
    assertTrue(getResult2.isWarning());
  }

  /**
   * Method under test:
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation11() {
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
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("EXCLUSIVE_GATEWAY_SEQ_FLOW_WITHOUT_CONDITIONS", getResult.getDefaultDescription());
    assertEquals("EXCLUSIVE_GATEWAY_SEQ_FLOW_WITHOUT_CONDITIONS", getResult.getKey());
    assertEquals("EXCLUSIVE_GATEWAY_SEQ_FLOW_WITHOUT_CONDITIONS", getResult.getProblem());
    assertEquals("Name", getResult.getActivityName());
    assertEquals("Name", getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Method under test:
   * {@link ExclusiveGatewayValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation12() {
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
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExclusiveGatewayValidator#validateExclusiveGateway(Process, ExclusiveGateway, List)}
   */
  @Test
  void testValidateExclusiveGateway() {
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
    assertTrue(exclusiveGateway.getExecutionListeners().isEmpty());
    assertTrue(exclusiveGateway.getIncomingFlows().isEmpty());
    assertTrue(exclusiveGateway.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExclusiveGatewayValidator#validateExclusiveGateway(Process, ExclusiveGateway, List)}
   */
  @Test
  void testValidateExclusiveGateway2() {
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
    assertTrue(exclusiveGateway.getExecutionListeners().isEmpty());
    assertTrue(exclusiveGateway.getIncomingFlows().isEmpty());
    assertTrue(exclusiveGateway.getOutgoingFlows().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link ExclusiveGatewayValidator#validateExclusiveGateway(Process, ExclusiveGateway, List)}
   */
  @Test
  void testValidateExclusiveGateway3() {
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
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
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
    assertTrue(exclusiveGateway.getExecutionListeners().isEmpty());
    assertTrue(exclusiveGateway.getIncomingFlows().isEmpty());
    assertTrue(exclusiveGateway.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertSame(validationError, errors.get(0));
  }

  /**
   * Method under test:
   * {@link ExclusiveGatewayValidator#validateExclusiveGateway(Process, ExclusiveGateway, List)}
   */
  @Test
  void testValidateExclusiveGateway4() {
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
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
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
    assertTrue(exclusiveGateway.getExecutionListeners().isEmpty());
    assertTrue(exclusiveGateway.getIncomingFlows().isEmpty());
    assertTrue(exclusiveGateway.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertSame(validationError, errors.get(0));
  }
}
