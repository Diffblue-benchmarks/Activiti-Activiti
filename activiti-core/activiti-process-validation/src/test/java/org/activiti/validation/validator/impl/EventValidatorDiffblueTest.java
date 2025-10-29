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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.CancelEventDefinition;
import org.activiti.bpmn.model.CompensateEventDefinition;
import org.activiti.bpmn.model.Event;
import org.activiti.bpmn.model.EventDefinition;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.Signal;
import org.activiti.bpmn.model.SignalEventDefinition;
import org.activiti.bpmn.model.TimerEventDefinition;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class EventValidatorDiffblueTest {
  /**
   * Method under test:
   * {@link EventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.executeValidation(bpmnModel, process, errors);

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
   * {@link EventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation2() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Method under test:
   * {@link EventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation3() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.executeValidation(bpmnModel, process, errors);

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
   * {@link EventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation4() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<Event> eventList = new ArrayList<>();
    eventList.add(new BoundaryEvent());
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(eventList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.executeValidation(bpmnModel, process, errors);

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
   * {@link EventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation5() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());

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
    eventValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(process).findFlowElementsOfType(isA(Class.class));
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, errors.size());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertSame(validationError, errors.get(0));
  }

  /**
   * Method under test:
   * {@link EventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation6() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(new ArrayList<>());

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
    eventValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(process).findFlowElementsOfType(isA(Class.class));
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(2, errors.size());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
    assertSame(validationError2, errors.get(0));
  }

  /**
   * Method under test:
   * {@link EventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation7() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.addEventDefinition(new CancelEventDefinition());

    ArrayList<Event> eventList = new ArrayList<>();
    eventList.add(boundaryEvent);
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(eventList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.executeValidation(bpmnModel, process, errors);

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
   * {@link EventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation8() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.addEventDefinition(new MessageEventDefinition());

    ArrayList<Event> eventList = new ArrayList<>();
    eventList.add(boundaryEvent);
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(eventList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.executeValidation(bpmnModel, process, errors);

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
    assertEquals("MESSAGE_EVENT_MISSING_MESSAGE_REF", getResult.getDefaultDescription());
    assertEquals("MESSAGE_EVENT_MISSING_MESSAGE_REF", getResult.getKey());
    assertEquals("MESSAGE_EVENT_MISSING_MESSAGE_REF", getResult.getProblem());
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
   * {@link EventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation9() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.addEventDefinition(new CompensateEventDefinition());

    ArrayList<Event> eventList = new ArrayList<>();
    eventList.add(boundaryEvent);
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(eventList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.executeValidation(bpmnModel, process, errors);

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
   * {@link EventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation10() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = new BpmnModel();
    MessageEventDefinition eventDefinition = mock(MessageEventDefinition.class);
    when(eventDefinition.getMessageRef()).thenReturn("Message Ref");

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.addEventDefinition(eventDefinition);

    ArrayList<Event> eventList = new ArrayList<>();
    eventList.add(boundaryEvent);
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(eventList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process).getId();
    verify(eventDefinition, atLeast(1)).getMessageRef();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getName();
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("MESSAGE_EVENT_INVALID_MESSAGE_REF", getResult.getDefaultDescription());
    assertEquals("MESSAGE_EVENT_INVALID_MESSAGE_REF", getResult.getKey());
    assertEquals("MESSAGE_EVENT_INVALID_MESSAGE_REF", getResult.getProblem());
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
   * {@link EventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation11() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.containsMessageId(Mockito.<String>any())).thenReturn(true);
    MessageEventDefinition eventDefinition = mock(MessageEventDefinition.class);
    when(eventDefinition.getMessageRef()).thenReturn("Message Ref");

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.addEventDefinition(eventDefinition);

    ArrayList<Event> eventList = new ArrayList<>();
    eventList.add(boundaryEvent);
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(eventList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(bpmnModel).containsMessageId(eq("Message Ref"));
    verify(eventDefinition, atLeast(1)).getMessageRef();
    verify(process).findFlowElementsOfType(isA(Class.class));
    assertTrue(errors.isEmpty());
  }

  /**
   * Method under test:
   * {@link EventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation12() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    MessageEventDefinition eventDefinition = mock(MessageEventDefinition.class);
    when(eventDefinition.getMessageExpression()).thenReturn("Message Expression");
    when(eventDefinition.getMessageRef()).thenReturn(null);

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.addEventDefinition(eventDefinition);

    ArrayList<Event> eventList = new ArrayList<>();
    eventList.add(boundaryEvent);
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(eventList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(eventDefinition).getMessageExpression();
    verify(eventDefinition).getMessageRef();
    verify(process).findFlowElementsOfType(isA(Class.class));
    assertTrue(errors.isEmpty());
  }

  /**
   * Method under test:
   * {@link EventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation13() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    MessageEventDefinition eventDefinition = mock(MessageEventDefinition.class);
    when(eventDefinition.getMessageExpression()).thenReturn("Message Expression");
    when(eventDefinition.getMessageRef()).thenReturn("");

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.addEventDefinition(eventDefinition);

    ArrayList<Event> eventList = new ArrayList<>();
    eventList.add(boundaryEvent);
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(eventList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(eventDefinition).getMessageExpression();
    verify(eventDefinition).getMessageRef();
    verify(process).findFlowElementsOfType(isA(Class.class));
    assertTrue(errors.isEmpty());
  }

  /**
   * Method under test:
   * {@link EventValidator#handleMessageEventDefinition(BpmnModel, Process, Event, EventDefinition, List)}
   */
  @Test
  void testHandleMessageEventDefinition() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    doNothing().when(bpmnModel).addDefinitionsAttribute(Mockito.<ExtensionAttribute>any());
    bpmnModel.addDefinitionsAttribute(new ExtensionAttribute("Name"));
    Process process = new Process();
    BoundaryEvent event = new BoundaryEvent();
    MessageEventDefinition eventDefinition = new MessageEventDefinition();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.handleMessageEventDefinition(bpmnModel, process, event, eventDefinition, errors);

    // Assert
    verify(bpmnModel).addDefinitionsAttribute(isA(ExtensionAttribute.class));
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("MESSAGE_EVENT_MISSING_MESSAGE_REF", getResult.getDefaultDescription());
    assertEquals("MESSAGE_EVENT_MISSING_MESSAGE_REF", getResult.getKey());
    assertEquals("MESSAGE_EVENT_MISSING_MESSAGE_REF", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(event.getEventDefinitions().isEmpty());
    assertTrue(event.getExecutionListeners().isEmpty());
    assertTrue(event.getIncomingFlows().isEmpty());
    assertTrue(event.getOutgoingFlows().isEmpty());
    assertTrue(eventDefinition.getFieldExtensions().isEmpty());
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
   * {@link EventValidator#handleMessageEventDefinition(BpmnModel, Process, Event, EventDefinition, List)}
   */
  @Test
  void testHandleMessageEventDefinition2() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.containsMessageId(Mockito.<String>any())).thenReturn(true);
    doNothing().when(bpmnModel).addDefinitionsAttribute(Mockito.<ExtensionAttribute>any());
    bpmnModel.addDefinitionsAttribute(new ExtensionAttribute("Name"));
    Process process = new Process();
    BoundaryEvent event = new BoundaryEvent();
    MessageEventDefinition eventDefinition = mock(MessageEventDefinition.class);
    when(eventDefinition.getMessageRef()).thenReturn("Message Ref");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.handleMessageEventDefinition(bpmnModel, process, event, eventDefinition, errors);

    // Assert that nothing has changed
    verify(bpmnModel).addDefinitionsAttribute(isA(ExtensionAttribute.class));
    verify(bpmnModel).containsMessageId(eq("Message Ref"));
    verify(eventDefinition, atLeast(1)).getMessageRef();
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(event.getEventDefinitions().isEmpty());
    assertTrue(event.getExecutionListeners().isEmpty());
    assertTrue(event.getIncomingFlows().isEmpty());
    assertTrue(event.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
  }

  /**
   * Method under test:
   * {@link EventValidator#handleMessageEventDefinition(BpmnModel, Process, Event, EventDefinition, List)}
   */
  @Test
  void testHandleMessageEventDefinition3() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.containsMessageId(Mockito.<String>any())).thenReturn(false);
    doNothing().when(bpmnModel).addDefinitionsAttribute(Mockito.<ExtensionAttribute>any());
    bpmnModel.addDefinitionsAttribute(new ExtensionAttribute("Name"));
    Process process = new Process();
    BoundaryEvent event = new BoundaryEvent();
    MessageEventDefinition eventDefinition = mock(MessageEventDefinition.class);
    when(eventDefinition.getMessageRef()).thenReturn("Message Ref");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.handleMessageEventDefinition(bpmnModel, process, event, eventDefinition, errors);

    // Assert
    verify(bpmnModel).addDefinitionsAttribute(isA(ExtensionAttribute.class));
    verify(bpmnModel).containsMessageId(eq("Message Ref"));
    verify(eventDefinition, atLeast(1)).getMessageRef();
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("MESSAGE_EVENT_INVALID_MESSAGE_REF", getResult.getDefaultDescription());
    assertEquals("MESSAGE_EVENT_INVALID_MESSAGE_REF", getResult.getKey());
    assertEquals("MESSAGE_EVENT_INVALID_MESSAGE_REF", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(event.getEventDefinitions().isEmpty());
    assertTrue(event.getExecutionListeners().isEmpty());
    assertTrue(event.getIncomingFlows().isEmpty());
    assertTrue(event.getOutgoingFlows().isEmpty());
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
   * {@link EventValidator#handleMessageEventDefinition(BpmnModel, Process, Event, EventDefinition, List)}
   */
  @Test
  void testHandleMessageEventDefinition4() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    doNothing().when(bpmnModel).addDefinitionsAttribute(Mockito.<ExtensionAttribute>any());
    bpmnModel.addDefinitionsAttribute(new ExtensionAttribute("Name"));
    Process process = new Process();
    BoundaryEvent event = new BoundaryEvent();
    MessageEventDefinition eventDefinition = mock(MessageEventDefinition.class);
    when(eventDefinition.getMessageExpression()).thenReturn("Message Expression");
    when(eventDefinition.getMessageRef()).thenReturn("");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.handleMessageEventDefinition(bpmnModel, process, event, eventDefinition, errors);

    // Assert that nothing has changed
    verify(bpmnModel).addDefinitionsAttribute(isA(ExtensionAttribute.class));
    verify(eventDefinition).getMessageExpression();
    verify(eventDefinition).getMessageRef();
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(event.getEventDefinitions().isEmpty());
    assertTrue(event.getExecutionListeners().isEmpty());
    assertTrue(event.getIncomingFlows().isEmpty());
    assertTrue(event.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
  }

  /**
   * Method under test:
   * {@link EventValidator#handleMessageEventDefinition(BpmnModel, Process, Event, EventDefinition, List)}
   */
  @Test
  void testHandleMessageEventDefinition5() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.containsMessageId(Mockito.<String>any())).thenReturn(false);
    doNothing().when(bpmnModel).addDefinitionsAttribute(Mockito.<ExtensionAttribute>any());
    bpmnModel.addDefinitionsAttribute(new ExtensionAttribute("Name"));
    BoundaryEvent event = new BoundaryEvent();
    MessageEventDefinition eventDefinition = mock(MessageEventDefinition.class);
    when(eventDefinition.getMessageRef()).thenReturn("Message Ref");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.handleMessageEventDefinition(bpmnModel, null, event, eventDefinition, errors);

    // Assert
    verify(bpmnModel).addDefinitionsAttribute(isA(ExtensionAttribute.class));
    verify(bpmnModel).containsMessageId(eq("Message Ref"));
    verify(eventDefinition, atLeast(1)).getMessageRef();
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("MESSAGE_EVENT_INVALID_MESSAGE_REF", getResult.getDefaultDescription());
    assertEquals("MESSAGE_EVENT_INVALID_MESSAGE_REF", getResult.getKey());
    assertEquals("MESSAGE_EVENT_INVALID_MESSAGE_REF", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(event.getEventDefinitions().isEmpty());
    assertTrue(event.getExecutionListeners().isEmpty());
    assertTrue(event.getIncomingFlows().isEmpty());
    assertTrue(event.getOutgoingFlows().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link EventValidator#handleMessageEventDefinition(BpmnModel, Process, Event, EventDefinition, List)}
   */
  @Test
  void testHandleMessageEventDefinition6() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.containsMessageId(Mockito.<String>any())).thenReturn(false);
    doNothing().when(bpmnModel).addDefinitionsAttribute(Mockito.<ExtensionAttribute>any());
    bpmnModel.addDefinitionsAttribute(new ExtensionAttribute("Name"));
    Process process = new Process();
    BoundaryEvent event = new BoundaryEvent();
    MessageEventDefinition eventDefinition = mock(MessageEventDefinition.class);
    when(eventDefinition.getMessageRef()).thenReturn("Message Ref");

    ValidationError validationError = new ValidationError();
    validationError.setActivityId("MESSAGE_EVENT_INVALID_MESSAGE_REF");
    validationError.setActivityName("Activity Name");
    validationError.setDefaultDescription("Default Description");
    validationError.setKey("Key");
    validationError.setParams(new HashMap<>());
    validationError.setProblem("Problem");
    validationError.setProcessDefinitionId("MESSAGE_EVENT_INVALID_MESSAGE_REF");
    validationError.setProcessDefinitionName("Process Definition Name");
    validationError.setValidatorSetName("Validator Set Name");
    validationError.setWarning(false);
    validationError.setXmlColumnNumber(1);
    validationError.setXmlLineNumber(10);

    ArrayList<ValidationError> errors = new ArrayList<>();
    errors.add(validationError);

    // Act
    eventValidator.handleMessageEventDefinition(bpmnModel, process, event, eventDefinition, errors);

    // Assert
    verify(bpmnModel).addDefinitionsAttribute(isA(ExtensionAttribute.class));
    verify(bpmnModel).containsMessageId(eq("Message Ref"));
    verify(eventDefinition, atLeast(1)).getMessageRef();
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("MESSAGE_EVENT_INVALID_MESSAGE_REF", getResult.getDefaultDescription());
    assertEquals("MESSAGE_EVENT_INVALID_MESSAGE_REF", getResult.getKey());
    assertEquals("MESSAGE_EVENT_INVALID_MESSAGE_REF", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(event.getEventDefinitions().isEmpty());
    assertTrue(event.getExecutionListeners().isEmpty());
    assertTrue(event.getIncomingFlows().isEmpty());
    assertTrue(event.getOutgoingFlows().isEmpty());
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
   * {@link EventValidator#handleMessageEventDefinition(BpmnModel, Process, Event, EventDefinition, List)}
   */
  @Test
  void testHandleMessageEventDefinition7() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.containsMessageId(Mockito.<String>any())).thenReturn(false);
    doNothing().when(bpmnModel).addDefinitionsAttribute(Mockito.<ExtensionAttribute>any());
    bpmnModel.addDefinitionsAttribute(new ExtensionAttribute("Name"));
    MessageEventDefinition eventDefinition = mock(MessageEventDefinition.class);
    when(eventDefinition.getMessageRef()).thenReturn("Message Ref");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.handleMessageEventDefinition(bpmnModel, null, null, eventDefinition, errors);

    // Assert
    verify(bpmnModel).addDefinitionsAttribute(isA(ExtensionAttribute.class));
    verify(bpmnModel).containsMessageId(eq("Message Ref"));
    verify(eventDefinition, atLeast(1)).getMessageRef();
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("MESSAGE_EVENT_INVALID_MESSAGE_REF", getResult.getDefaultDescription());
    assertEquals("MESSAGE_EVENT_INVALID_MESSAGE_REF", getResult.getKey());
    assertEquals("MESSAGE_EVENT_INVALID_MESSAGE_REF", getResult.getProblem());
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
   * Method under test:
   * {@link EventValidator#handleSignalEventDefinition(BpmnModel, Process, Event, EventDefinition, List)}
   */
  @Test
  void testHandleSignalEventDefinition() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    doNothing().when(bpmnModel).addDefinitionsAttribute(Mockito.<ExtensionAttribute>any());
    bpmnModel.addDefinitionsAttribute(new ExtensionAttribute("Name"));
    Process process = new Process();
    BoundaryEvent event = new BoundaryEvent();
    SignalEventDefinition eventDefinition = new SignalEventDefinition();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.handleSignalEventDefinition(bpmnModel, process, event, eventDefinition, errors);

    // Assert
    verify(bpmnModel).addDefinitionsAttribute(isA(ExtensionAttribute.class));
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("SIGNAL_EVENT_MISSING_SIGNAL_REF", getResult.getDefaultDescription());
    assertEquals("SIGNAL_EVENT_MISSING_SIGNAL_REF", getResult.getKey());
    assertEquals("SIGNAL_EVENT_MISSING_SIGNAL_REF", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(event.getEventDefinitions().isEmpty());
    assertTrue(event.getExecutionListeners().isEmpty());
    assertTrue(event.getIncomingFlows().isEmpty());
    assertTrue(event.getOutgoingFlows().isEmpty());
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
   * {@link EventValidator#handleSignalEventDefinition(BpmnModel, Process, Event, EventDefinition, List)}
   */
  @Test
  void testHandleSignalEventDefinition2() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.containsSignalId(Mockito.<String>any())).thenReturn(true);
    doNothing().when(bpmnModel).addDefinitionsAttribute(Mockito.<ExtensionAttribute>any());
    bpmnModel.addDefinitionsAttribute(new ExtensionAttribute("Name"));
    Process process = new Process();
    BoundaryEvent event = new BoundaryEvent();
    SignalEventDefinition eventDefinition = mock(SignalEventDefinition.class);
    when(eventDefinition.getSignalRef()).thenReturn("Signal Ref");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.handleSignalEventDefinition(bpmnModel, process, event, eventDefinition, errors);

    // Assert that nothing has changed
    verify(bpmnModel).addDefinitionsAttribute(isA(ExtensionAttribute.class));
    verify(bpmnModel).containsSignalId(eq("Signal Ref"));
    verify(eventDefinition, atLeast(1)).getSignalRef();
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(event.getEventDefinitions().isEmpty());
    assertTrue(event.getExecutionListeners().isEmpty());
    assertTrue(event.getIncomingFlows().isEmpty());
    assertTrue(event.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
  }

  /**
   * Method under test:
   * {@link EventValidator#handleSignalEventDefinition(BpmnModel, Process, Event, EventDefinition, List)}
   */
  @Test
  void testHandleSignalEventDefinition3() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.containsSignalId(Mockito.<String>any())).thenReturn(false);
    doNothing().when(bpmnModel).addDefinitionsAttribute(Mockito.<ExtensionAttribute>any());
    bpmnModel.addDefinitionsAttribute(new ExtensionAttribute("Name"));
    Process process = new Process();
    BoundaryEvent event = new BoundaryEvent();
    SignalEventDefinition eventDefinition = mock(SignalEventDefinition.class);
    when(eventDefinition.getSignalRef()).thenReturn("Signal Ref");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.handleSignalEventDefinition(bpmnModel, process, event, eventDefinition, errors);

    // Assert
    verify(bpmnModel).addDefinitionsAttribute(isA(ExtensionAttribute.class));
    verify(bpmnModel).containsSignalId(eq("Signal Ref"));
    verify(eventDefinition, atLeast(1)).getSignalRef();
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("SIGNAL_EVENT_INVALID_SIGNAL_REF", getResult.getDefaultDescription());
    assertEquals("SIGNAL_EVENT_INVALID_SIGNAL_REF", getResult.getKey());
    assertEquals("SIGNAL_EVENT_INVALID_SIGNAL_REF", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(event.getEventDefinitions().isEmpty());
    assertTrue(event.getExecutionListeners().isEmpty());
    assertTrue(event.getIncomingFlows().isEmpty());
    assertTrue(event.getOutgoingFlows().isEmpty());
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
   * {@link EventValidator#handleSignalEventDefinition(BpmnModel, Process, Event, EventDefinition, List)}
   */
  @Test
  void testHandleSignalEventDefinition4() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    doNothing().when(bpmnModel).addDefinitionsAttribute(Mockito.<ExtensionAttribute>any());
    bpmnModel.addDefinitionsAttribute(new ExtensionAttribute("Name"));
    Process process = new Process();
    BoundaryEvent event = new BoundaryEvent();
    SignalEventDefinition eventDefinition = mock(SignalEventDefinition.class);
    when(eventDefinition.getSignalExpression()).thenReturn("Signal Expression");
    when(eventDefinition.getSignalRef()).thenReturn("");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.handleSignalEventDefinition(bpmnModel, process, event, eventDefinition, errors);

    // Assert that nothing has changed
    verify(bpmnModel).addDefinitionsAttribute(isA(ExtensionAttribute.class));
    verify(eventDefinition).getSignalExpression();
    verify(eventDefinition).getSignalRef();
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(event.getEventDefinitions().isEmpty());
    assertTrue(event.getExecutionListeners().isEmpty());
    assertTrue(event.getIncomingFlows().isEmpty());
    assertTrue(event.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
  }

  /**
   * Method under test:
   * {@link EventValidator#handleSignalEventDefinition(BpmnModel, Process, Event, EventDefinition, List)}
   */
  @Test
  void testHandleSignalEventDefinition5() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.containsSignalId(Mockito.<String>any())).thenReturn(false);
    doNothing().when(bpmnModel).addDefinitionsAttribute(Mockito.<ExtensionAttribute>any());
    bpmnModel.addDefinitionsAttribute(new ExtensionAttribute("Name"));
    BoundaryEvent event = new BoundaryEvent();
    SignalEventDefinition eventDefinition = mock(SignalEventDefinition.class);
    when(eventDefinition.getSignalRef()).thenReturn("Signal Ref");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.handleSignalEventDefinition(bpmnModel, null, event, eventDefinition, errors);

    // Assert
    verify(bpmnModel).addDefinitionsAttribute(isA(ExtensionAttribute.class));
    verify(bpmnModel).containsSignalId(eq("Signal Ref"));
    verify(eventDefinition, atLeast(1)).getSignalRef();
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("SIGNAL_EVENT_INVALID_SIGNAL_REF", getResult.getDefaultDescription());
    assertEquals("SIGNAL_EVENT_INVALID_SIGNAL_REF", getResult.getKey());
    assertEquals("SIGNAL_EVENT_INVALID_SIGNAL_REF", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(event.getEventDefinitions().isEmpty());
    assertTrue(event.getExecutionListeners().isEmpty());
    assertTrue(event.getIncomingFlows().isEmpty());
    assertTrue(event.getOutgoingFlows().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link EventValidator#handleSignalEventDefinition(BpmnModel, Process, Event, EventDefinition, List)}
   */
  @Test
  void testHandleSignalEventDefinition6() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.containsSignalId(Mockito.<String>any())).thenReturn(false);
    doNothing().when(bpmnModel).addDefinitionsAttribute(Mockito.<ExtensionAttribute>any());
    bpmnModel.addDefinitionsAttribute(new ExtensionAttribute("Name"));
    Process process = new Process();
    BoundaryEvent event = new BoundaryEvent();
    SignalEventDefinition eventDefinition = mock(SignalEventDefinition.class);
    when(eventDefinition.getSignalRef()).thenReturn("Signal Ref");

    ValidationError validationError = new ValidationError();
    validationError.setActivityId("SIGNAL_EVENT_INVALID_SIGNAL_REF");
    validationError.setActivityName("Activity Name");
    validationError.setDefaultDescription("Default Description");
    validationError.setKey("Key");
    validationError.setParams(new HashMap<>());
    validationError.setProblem("Problem");
    validationError.setProcessDefinitionId("SIGNAL_EVENT_INVALID_SIGNAL_REF");
    validationError.setProcessDefinitionName("Process Definition Name");
    validationError.setValidatorSetName("Validator Set Name");
    validationError.setWarning(false);
    validationError.setXmlColumnNumber(1);
    validationError.setXmlLineNumber(10);

    ArrayList<ValidationError> errors = new ArrayList<>();
    errors.add(validationError);

    // Act
    eventValidator.handleSignalEventDefinition(bpmnModel, process, event, eventDefinition, errors);

    // Assert
    verify(bpmnModel).addDefinitionsAttribute(isA(ExtensionAttribute.class));
    verify(bpmnModel).containsSignalId(eq("Signal Ref"));
    verify(eventDefinition, atLeast(1)).getSignalRef();
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("SIGNAL_EVENT_INVALID_SIGNAL_REF", getResult.getDefaultDescription());
    assertEquals("SIGNAL_EVENT_INVALID_SIGNAL_REF", getResult.getKey());
    assertEquals("SIGNAL_EVENT_INVALID_SIGNAL_REF", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(event.getEventDefinitions().isEmpty());
    assertTrue(event.getExecutionListeners().isEmpty());
    assertTrue(event.getIncomingFlows().isEmpty());
    assertTrue(event.getOutgoingFlows().isEmpty());
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
   * {@link EventValidator#handleSignalEventDefinition(BpmnModel, Process, Event, EventDefinition, List)}
   */
  @Test
  void testHandleSignalEventDefinition7() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.containsSignalId(Mockito.<String>any())).thenReturn(false);
    doNothing().when(bpmnModel).addDefinitionsAttribute(Mockito.<ExtensionAttribute>any());
    bpmnModel.addDefinitionsAttribute(new ExtensionAttribute("Name"));
    SignalEventDefinition eventDefinition = mock(SignalEventDefinition.class);
    when(eventDefinition.getSignalRef()).thenReturn("Signal Ref");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.handleSignalEventDefinition(bpmnModel, null, null, eventDefinition, errors);

    // Assert
    verify(bpmnModel).addDefinitionsAttribute(isA(ExtensionAttribute.class));
    verify(bpmnModel).containsSignalId(eq("Signal Ref"));
    verify(eventDefinition, atLeast(1)).getSignalRef();
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("SIGNAL_EVENT_INVALID_SIGNAL_REF", getResult.getDefaultDescription());
    assertEquals("SIGNAL_EVENT_INVALID_SIGNAL_REF", getResult.getKey());
    assertEquals("SIGNAL_EVENT_INVALID_SIGNAL_REF", getResult.getProblem());
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
   * Method under test:
   * {@link EventValidator#handleTimerEventDefinition(Process, Event, EventDefinition, List)}
   */
  @Test
  void testHandleTimerEventDefinition() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    Process process = new Process();
    BoundaryEvent event = new BoundaryEvent();

    TimerEventDefinition eventDefinition = new TimerEventDefinition();
    eventDefinition.setTimeCycle(null);
    eventDefinition.setTimeDate(null);
    eventDefinition.setTimeDuration(null);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.handleTimerEventDefinition(process, event, eventDefinition, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("EVENT_TIMER_MISSING_CONFIGURATION", getResult.getDefaultDescription());
    assertEquals("EVENT_TIMER_MISSING_CONFIGURATION", getResult.getKey());
    assertEquals("EVENT_TIMER_MISSING_CONFIGURATION", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(event.getEventDefinitions().isEmpty());
    assertTrue(event.getExecutionListeners().isEmpty());
    assertTrue(event.getIncomingFlows().isEmpty());
    assertTrue(event.getOutgoingFlows().isEmpty());
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
   * {@link EventValidator#handleTimerEventDefinition(Process, Event, EventDefinition, List)}
   */
  @Test
  void testHandleTimerEventDefinition2() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    Process process = new Process();
    BoundaryEvent event = new BoundaryEvent();

    TimerEventDefinition eventDefinition = new TimerEventDefinition();
    eventDefinition.setTimeCycle(null);
    eventDefinition.setTimeDate(null);
    eventDefinition.setTimeDuration("Event Definition");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.handleTimerEventDefinition(process, event, eventDefinition, errors);

    // Assert that nothing has changed
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(event.getEventDefinitions().isEmpty());
    assertTrue(event.getExecutionListeners().isEmpty());
    assertTrue(event.getIncomingFlows().isEmpty());
    assertTrue(event.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
  }

  /**
   * Method under test:
   * {@link EventValidator#handleTimerEventDefinition(Process, Event, EventDefinition, List)}
   */
  @Test
  void testHandleTimerEventDefinition3() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    Process process = new Process();
    BoundaryEvent event = new BoundaryEvent();

    TimerEventDefinition eventDefinition = new TimerEventDefinition();
    eventDefinition.setTimeCycle(null);
    eventDefinition.setTimeDate("Event Definition");
    eventDefinition.setTimeDuration(null);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.handleTimerEventDefinition(process, event, eventDefinition, errors);

    // Assert that nothing has changed
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(event.getEventDefinitions().isEmpty());
    assertTrue(event.getExecutionListeners().isEmpty());
    assertTrue(event.getIncomingFlows().isEmpty());
    assertTrue(event.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
  }

  /**
   * Method under test:
   * {@link EventValidator#handleTimerEventDefinition(Process, Event, EventDefinition, List)}
   */
  @Test
  void testHandleTimerEventDefinition4() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    Process process = new Process();
    BoundaryEvent event = new BoundaryEvent();

    TimerEventDefinition eventDefinition = new TimerEventDefinition();
    eventDefinition.setTimeCycle("Event Definition");
    eventDefinition.setTimeDate(null);
    eventDefinition.setTimeDuration(null);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.handleTimerEventDefinition(process, event, eventDefinition, errors);

    // Assert that nothing has changed
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(event.getEventDefinitions().isEmpty());
    assertTrue(event.getExecutionListeners().isEmpty());
    assertTrue(event.getIncomingFlows().isEmpty());
    assertTrue(event.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
  }

  /**
   * Method under test:
   * {@link EventValidator#handleTimerEventDefinition(Process, Event, EventDefinition, List)}
   */
  @Test
  void testHandleTimerEventDefinition5() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    Process process = new Process();
    BoundaryEvent event = new BoundaryEvent();

    TimerEventDefinition eventDefinition = new TimerEventDefinition();
    eventDefinition.setTimeCycle("");
    eventDefinition.setTimeDate(null);
    eventDefinition.setTimeDuration(null);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.handleTimerEventDefinition(process, event, eventDefinition, errors);

    // Assert
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("EVENT_TIMER_MISSING_CONFIGURATION", getResult.getDefaultDescription());
    assertEquals("EVENT_TIMER_MISSING_CONFIGURATION", getResult.getKey());
    assertEquals("EVENT_TIMER_MISSING_CONFIGURATION", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(event.getEventDefinitions().isEmpty());
    assertTrue(event.getExecutionListeners().isEmpty());
    assertTrue(event.getIncomingFlows().isEmpty());
    assertTrue(event.getOutgoingFlows().isEmpty());
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
   * {@link EventValidator#handleCompensationEventDefinition(BpmnModel, Process, Event, EventDefinition, List)}
   */
  @Test
  void testHandleCompensationEventDefinition() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    doNothing().when(bpmnModel).addDefinitionsAttribute(Mockito.<ExtensionAttribute>any());
    bpmnModel.addDefinitionsAttribute(new ExtensionAttribute("Name"));
    Process process = new Process();
    BoundaryEvent event = new BoundaryEvent();
    CompensateEventDefinition eventDefinition = new CompensateEventDefinition();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.handleCompensationEventDefinition(bpmnModel, process, event, eventDefinition, errors);

    // Assert that nothing has changed
    verify(bpmnModel).addDefinitionsAttribute(isA(ExtensionAttribute.class));
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(event.getEventDefinitions().isEmpty());
    assertTrue(event.getExecutionListeners().isEmpty());
    assertTrue(event.getIncomingFlows().isEmpty());
    assertTrue(event.getOutgoingFlows().isEmpty());
    assertTrue(process.getCandidateStarterGroups().isEmpty());
    assertTrue(process.getCandidateStarterUsers().isEmpty());
    assertTrue(process.getDataObjects().isEmpty());
    assertTrue(process.getEventListeners().isEmpty());
    assertTrue(process.getExecutionListeners().isEmpty());
    assertTrue(process.getLanes().isEmpty());
  }

  /**
   * Method under test:
   * {@link EventValidator#handleCompensationEventDefinition(BpmnModel, Process, Event, EventDefinition, List)}
   */
  @Test
  void testHandleCompensationEventDefinition2() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    doNothing().when(bpmnModel).addDefinitionsAttribute(Mockito.<ExtensionAttribute>any());
    bpmnModel.addDefinitionsAttribute(new ExtensionAttribute("Name"));
    Process process = new Process();
    BoundaryEvent event = new BoundaryEvent();
    CompensateEventDefinition eventDefinition = mock(CompensateEventDefinition.class);
    when(eventDefinition.getActivityRef()).thenReturn("Activity Ref");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.handleCompensationEventDefinition(bpmnModel, process, event, eventDefinition, errors);

    // Assert
    verify(bpmnModel).addDefinitionsAttribute(isA(ExtensionAttribute.class));
    verify(eventDefinition, atLeast(1)).getActivityRef();
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("COMPENSATE_EVENT_INVALID_ACTIVITY_REF", getResult.getDefaultDescription());
    assertEquals("COMPENSATE_EVENT_INVALID_ACTIVITY_REF", getResult.getKey());
    assertEquals("COMPENSATE_EVENT_INVALID_ACTIVITY_REF", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(flowElements.isEmpty());
    assertTrue(event.getEventDefinitions().isEmpty());
    assertTrue(event.getExecutionListeners().isEmpty());
    assertTrue(event.getIncomingFlows().isEmpty());
    assertTrue(event.getOutgoingFlows().isEmpty());
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
   * {@link EventValidator#handleCompensationEventDefinition(BpmnModel, Process, Event, EventDefinition, List)}
   */
  @Test
  void testHandleCompensationEventDefinition3() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    doNothing().when(bpmnModel).addDefinitionsAttribute(Mockito.<ExtensionAttribute>any());
    bpmnModel.addDefinitionsAttribute(new ExtensionAttribute("Name"));
    Process process = mock(Process.class);
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(new AdhocSubProcess());
    BoundaryEvent event = new BoundaryEvent();
    CompensateEventDefinition eventDefinition = mock(CompensateEventDefinition.class);
    when(eventDefinition.getActivityRef()).thenReturn("Activity Ref");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.handleCompensationEventDefinition(bpmnModel, process, event, eventDefinition, errors);

    // Assert that nothing has changed
    verify(bpmnModel).addDefinitionsAttribute(isA(ExtensionAttribute.class));
    verify(eventDefinition, atLeast(1)).getActivityRef();
    verify(process).getFlowElement(eq("Activity Ref"), eq(true));
    assertTrue(errors.isEmpty());
    assertTrue(event.getEventDefinitions().isEmpty());
    assertTrue(event.getExecutionListeners().isEmpty());
    assertTrue(event.getIncomingFlows().isEmpty());
    assertTrue(event.getOutgoingFlows().isEmpty());
  }

  /**
   * Method under test:
   * {@link EventValidator#handleCompensationEventDefinition(BpmnModel, Process, Event, EventDefinition, List)}
   */
  @Test
  void testHandleCompensationEventDefinition4() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    doNothing().when(bpmnModel).addDefinitionsAttribute(Mockito.<ExtensionAttribute>any());
    bpmnModel.addDefinitionsAttribute(new ExtensionAttribute("Name"));
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(null);
    BoundaryEvent event = new BoundaryEvent();
    CompensateEventDefinition eventDefinition = mock(CompensateEventDefinition.class);
    when(eventDefinition.getActivityRef()).thenReturn("Activity Ref");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.handleCompensationEventDefinition(bpmnModel, process, event, eventDefinition, errors);

    // Assert
    verify(process).getId();
    verify(bpmnModel).addDefinitionsAttribute(isA(ExtensionAttribute.class));
    verify(eventDefinition, atLeast(1)).getActivityRef();
    verify(process).getFlowElement(eq("Activity Ref"), eq(true));
    verify(process).getName();
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("COMPENSATE_EVENT_INVALID_ACTIVITY_REF", getResult.getDefaultDescription());
    assertEquals("COMPENSATE_EVENT_INVALID_ACTIVITY_REF", getResult.getKey());
    assertEquals("COMPENSATE_EVENT_INVALID_ACTIVITY_REF", getResult.getProblem());
    assertEquals("Name", getResult.getProcessDefinitionName());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(event.getEventDefinitions().isEmpty());
    assertTrue(event.getExecutionListeners().isEmpty());
    assertTrue(event.getIncomingFlows().isEmpty());
    assertTrue(event.getOutgoingFlows().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link EventValidator#handleCompensationEventDefinition(BpmnModel, Process, Event, EventDefinition, List)}
   */
  @Test
  void testHandleCompensationEventDefinition5() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    doNothing().when(bpmnModel).addDefinitionsAttribute(Mockito.<ExtensionAttribute>any());
    bpmnModel.addDefinitionsAttribute(new ExtensionAttribute("Name"));
    Process process = mock(Process.class);
    BoundaryEvent event = new BoundaryEvent();
    CompensateEventDefinition eventDefinition = mock(CompensateEventDefinition.class);
    when(eventDefinition.getActivityRef()).thenReturn("");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.handleCompensationEventDefinition(bpmnModel, process, event, eventDefinition, errors);

    // Assert that nothing has changed
    verify(bpmnModel).addDefinitionsAttribute(isA(ExtensionAttribute.class));
    verify(eventDefinition).getActivityRef();
    assertTrue(errors.isEmpty());
    assertTrue(event.getEventDefinitions().isEmpty());
    assertTrue(event.getExecutionListeners().isEmpty());
    assertTrue(event.getIncomingFlows().isEmpty());
    assertTrue(event.getOutgoingFlows().isEmpty());
  }

  /**
   * Method under test:
   * {@link EventValidator#handleCompensationEventDefinition(BpmnModel, Process, Event, EventDefinition, List)}
   */
  @Test
  void testHandleCompensationEventDefinition6() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    doNothing().when(bpmnModel).addDefinitionsAttribute(Mockito.<ExtensionAttribute>any());
    bpmnModel.addDefinitionsAttribute(new ExtensionAttribute("Name"));
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(null);
    CompensateEventDefinition eventDefinition = mock(CompensateEventDefinition.class);
    when(eventDefinition.getActivityRef()).thenReturn("Activity Ref");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.handleCompensationEventDefinition(bpmnModel, process, null, eventDefinition, errors);

    // Assert
    verify(process).getId();
    verify(bpmnModel).addDefinitionsAttribute(isA(ExtensionAttribute.class));
    verify(eventDefinition, atLeast(1)).getActivityRef();
    verify(process).getFlowElement(eq("Activity Ref"), eq(true));
    verify(process).getName();
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("COMPENSATE_EVENT_INVALID_ACTIVITY_REF", getResult.getDefaultDescription());
    assertEquals("COMPENSATE_EVENT_INVALID_ACTIVITY_REF", getResult.getKey());
    assertEquals("COMPENSATE_EVENT_INVALID_ACTIVITY_REF", getResult.getProblem());
    assertEquals("Name", getResult.getProcessDefinitionName());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Method under test:
   * {@link EventValidator#handleCompensationEventDefinition(BpmnModel, Process, Event, EventDefinition, List)}
   */
  @Test
  void testHandleCompensationEventDefinition7() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    doNothing().when(bpmnModel).addDefinitionsAttribute(Mockito.<ExtensionAttribute>any());
    bpmnModel.addDefinitionsAttribute(new ExtensionAttribute("Name"));
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(null);
    BoundaryEvent event = new BoundaryEvent();
    CompensateEventDefinition eventDefinition = mock(CompensateEventDefinition.class);
    when(eventDefinition.getActivityRef()).thenReturn("Activity Ref");

    ValidationError validationError = new ValidationError();
    validationError.setActivityId("COMPENSATE_EVENT_INVALID_ACTIVITY_REF");
    validationError.setActivityName("Activity Name");
    validationError.setDefaultDescription("Default Description");
    validationError.setKey("Key");
    validationError.setParams(new HashMap<>());
    validationError.setProblem("Problem");
    validationError.setProcessDefinitionId("COMPENSATE_EVENT_INVALID_ACTIVITY_REF");
    validationError.setProcessDefinitionName("Process Definition Name");
    validationError.setValidatorSetName("Validator Set Name");
    validationError.setWarning(false);
    validationError.setXmlColumnNumber(1);
    validationError.setXmlLineNumber(10);

    ArrayList<ValidationError> errors = new ArrayList<>();
    errors.add(validationError);

    // Act
    eventValidator.handleCompensationEventDefinition(bpmnModel, process, event, eventDefinition, errors);

    // Assert
    verify(process).getId();
    verify(bpmnModel).addDefinitionsAttribute(isA(ExtensionAttribute.class));
    verify(eventDefinition, atLeast(1)).getActivityRef();
    verify(process).getFlowElement(eq("Activity Ref"), eq(true));
    verify(process).getName();
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("COMPENSATE_EVENT_INVALID_ACTIVITY_REF", getResult.getDefaultDescription());
    assertEquals("COMPENSATE_EVENT_INVALID_ACTIVITY_REF", getResult.getKey());
    assertEquals("COMPENSATE_EVENT_INVALID_ACTIVITY_REF", getResult.getProblem());
    assertEquals("Name", getResult.getProcessDefinitionName());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(event.getEventDefinitions().isEmpty());
    assertTrue(event.getExecutionListeners().isEmpty());
    assertTrue(event.getIncomingFlows().isEmpty());
    assertTrue(event.getOutgoingFlows().isEmpty());
    assertTrue(getResult.getParams().isEmpty());
    assertSame(validationError, errors.get(0));
  }
}
