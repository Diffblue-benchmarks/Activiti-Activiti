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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.CancelEventDefinition;
import org.activiti.bpmn.model.CompensateEventDefinition;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.Signal;
import org.activiti.bpmn.model.SignalEventDefinition;
import org.activiti.bpmn.model.ThrowEvent;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class IntermediateThrowEventValidatorDiffblueTest {
  /**
   * Test
   * {@link IntermediateThrowEventValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ThrowEvent} (default
   * constructor).</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link IntermediateThrowEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given ArrayList() add ThrowEvent (default constructor); then ArrayList() Empty")
  void testExecuteValidation_givenArrayListAddThrowEvent_thenArrayListEmpty() {
    // Arrange
    IntermediateThrowEventValidator intermediateThrowEventValidator = new IntermediateThrowEventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<ThrowEvent> throwEventList = new ArrayList<>();
    throwEventList.add(new ThrowEvent());
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<ThrowEvent>>any())).thenReturn(throwEventList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    intermediateThrowEventValidator.executeValidation(bpmnModel, process, errors);

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
   * {@link IntermediateThrowEventValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link IntermediateThrowEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given ArrayList(); then ArrayList() Empty")
  void testExecuteValidation_givenArrayList_thenArrayListEmpty() {
    // Arrange
    IntermediateThrowEventValidator intermediateThrowEventValidator = new IntermediateThrowEventValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<ThrowEvent>>any())).thenReturn(new ArrayList<>());
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    intermediateThrowEventValidator.executeValidation(bpmnModel, process, errors);

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
   * {@link IntermediateThrowEventValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link ThrowEvent} (default constructor) addEventDefinition
   * {@link CompensateEventDefinition} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link IntermediateThrowEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given ThrowEvent (default constructor) addEventDefinition CompensateEventDefinition (default constructor)")
  void testExecuteValidation_givenThrowEventAddEventDefinitionCompensateEventDefinition() {
    // Arrange
    IntermediateThrowEventValidator intermediateThrowEventValidator = new IntermediateThrowEventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ThrowEvent throwEvent = new ThrowEvent();
    throwEvent.addEventDefinition(new CompensateEventDefinition());

    ArrayList<ThrowEvent> throwEventList = new ArrayList<>();
    throwEventList.add(throwEvent);
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<ThrowEvent>>any())).thenReturn(throwEventList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    intermediateThrowEventValidator.executeValidation(bpmnModel, process, errors);

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
   * {@link IntermediateThrowEventValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link ThrowEvent} (default constructor) addEventDefinition
   * {@link MessageEventDefinition} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link IntermediateThrowEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given ThrowEvent (default constructor) addEventDefinition MessageEventDefinition (default constructor)")
  void testExecuteValidation_givenThrowEventAddEventDefinitionMessageEventDefinition() {
    // Arrange
    IntermediateThrowEventValidator intermediateThrowEventValidator = new IntermediateThrowEventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ThrowEvent throwEvent = new ThrowEvent();
    throwEvent.addEventDefinition(new MessageEventDefinition());

    ArrayList<ThrowEvent> throwEventList = new ArrayList<>();
    throwEventList.add(throwEvent);
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<ThrowEvent>>any())).thenReturn(throwEventList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    intermediateThrowEventValidator.executeValidation(bpmnModel, process, errors);

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
   * {@link IntermediateThrowEventValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link ThrowEvent} (default constructor) addEventDefinition
   * {@link SignalEventDefinition} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link IntermediateThrowEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given ThrowEvent (default constructor) addEventDefinition SignalEventDefinition (default constructor)")
  void testExecuteValidation_givenThrowEventAddEventDefinitionSignalEventDefinition() {
    // Arrange
    IntermediateThrowEventValidator intermediateThrowEventValidator = new IntermediateThrowEventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ThrowEvent throwEvent = new ThrowEvent();
    throwEvent.addEventDefinition(new SignalEventDefinition());

    ArrayList<ThrowEvent> throwEventList = new ArrayList<>();
    throwEventList.add(throwEvent);
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<ThrowEvent>>any())).thenReturn(throwEventList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    intermediateThrowEventValidator.executeValidation(bpmnModel, process, errors);

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
   * {@link IntermediateThrowEventValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} first ActivityId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link IntermediateThrowEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() first ActivityId is '42'")
  void testExecuteValidation_thenArrayListFirstActivityIdIs42() {
    // Arrange
    IntermediateThrowEventValidator intermediateThrowEventValidator = new IntermediateThrowEventValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<ThrowEvent>>any())).thenReturn(new ArrayList<>());

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
    intermediateThrowEventValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(process).findFlowElementsOfType(isA(Class.class));
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
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
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertTrue(getResult.isWarning());
  }

  /**
   * Test
   * {@link IntermediateThrowEventValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} first ProcessDefinitionName is
   * {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link IntermediateThrowEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() first ProcessDefinitionName is 'Name'")
  void testExecuteValidation_thenArrayListFirstProcessDefinitionNameIsName() {
    // Arrange
    IntermediateThrowEventValidator intermediateThrowEventValidator = new IntermediateThrowEventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ThrowEvent throwEvent = new ThrowEvent();
    throwEvent.addEventDefinition(new CancelEventDefinition());

    ArrayList<ThrowEvent> throwEventList = new ArrayList<>();
    throwEventList.add(throwEvent);
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<ThrowEvent>>any())).thenReturn(throwEventList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    intermediateThrowEventValidator.executeValidation(bpmnModel, process, errors);

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
    assertEquals("Name", getResult.getProcessDefinitionName());
    assertEquals("THROW_EVENT_INVALID_EVENTDEFINITION", getResult.getDefaultDescription());
    assertEquals("THROW_EVENT_INVALID_EVENTDEFINITION", getResult.getKey());
    assertEquals("THROW_EVENT_INVALID_EVENTDEFINITION", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test
   * {@link IntermediateThrowEventValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link IntermediateThrowEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() size is two")
  void testExecuteValidation_thenArrayListSizeIsTwo() {
    // Arrange
    IntermediateThrowEventValidator intermediateThrowEventValidator = new IntermediateThrowEventValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<ThrowEvent>>any())).thenReturn(new ArrayList<>());

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
    intermediateThrowEventValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(process).findFlowElementsOfType(isA(Class.class));
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
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
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test
   * {@link IntermediateThrowEventValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>When {@link BpmnModel}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link IntermediateThrowEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); when BpmnModel; then ArrayList() Empty")
  void testExecuteValidation_whenBpmnModel_thenArrayListEmpty() {
    // Arrange
    IntermediateThrowEventValidator intermediateThrowEventValidator = new IntermediateThrowEventValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    intermediateThrowEventValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test
   * {@link IntermediateThrowEventValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>When {@link Process} (default constructor).</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link IntermediateThrowEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); when Process (default constructor); then ArrayList() Empty")
  void testExecuteValidation_whenProcess_thenArrayListEmpty() {
    // Arrange
    IntermediateThrowEventValidator intermediateThrowEventValidator = new IntermediateThrowEventValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    intermediateThrowEventValidator.executeValidation(bpmnModel, process, errors);

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
