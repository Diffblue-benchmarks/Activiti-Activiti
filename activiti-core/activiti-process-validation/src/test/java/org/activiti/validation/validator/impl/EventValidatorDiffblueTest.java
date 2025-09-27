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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.CancelEventDefinition;
import org.activiti.bpmn.model.CompensateEventDefinition;
import org.activiti.bpmn.model.Event;
import org.activiti.bpmn.model.EventDefinition;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.Signal;
import org.activiti.bpmn.model.SignalEventDefinition;
import org.activiti.bpmn.model.TimerEventDefinition;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class EventValidatorDiffblueTest {
  /**
   * Test {@link EventValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <p>Method under test: {@link EventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    messageEventDefinition.setMessageExpression("Flow Elements Of Type");

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(messageEventDefinition);

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setEventDefinitions(eventDefinitions);

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
  }

  /**
   * Test {@link EventValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <p>Method under test: {@link EventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation2() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();
    signalEventDefinition.setSignalExpression("Flow Elements Of Type");

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(signalEventDefinition);

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setEventDefinitions(eventDefinitions);

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
  }

  /**
   * Test {@link EventValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <p>Method under test: {@link EventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation3() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new MessageEventDefinition());

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setEventDefinitions(eventDefinitions);

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
    assertEquals("MESSAGE_EVENT_MISSING_MESSAGE_REF", getResult.getDefaultDescription());
    assertEquals("MESSAGE_EVENT_MISSING_MESSAGE_REF", getResult.getKey());
    assertEquals("MESSAGE_EVENT_MISSING_MESSAGE_REF", getResult.getProblem());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link EventValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <p>Method under test: {@link EventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation4() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new SignalEventDefinition());

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setEventDefinitions(eventDefinitions);

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
    assertEquals("SIGNAL_EVENT_MISSING_SIGNAL_REF", getResult.getDefaultDescription());
    assertEquals("SIGNAL_EVENT_MISSING_SIGNAL_REF", getResult.getKey());
    assertEquals("SIGNAL_EVENT_MISSING_SIGNAL_REF", getResult.getProblem());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link EventValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <p>Method under test: {@link EventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation5() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    MessageEventDefinition messageEventDefinition = mock(MessageEventDefinition.class);
    when(messageEventDefinition.getMessageRef()).thenReturn("Message Ref");

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(messageEventDefinition);

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setEventDefinitions(eventDefinitions);

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
    verify(messageEventDefinition, atLeast(1)).getMessageRef();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getName();
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("MESSAGE_EVENT_INVALID_MESSAGE_REF", getResult.getDefaultDescription());
    assertEquals("MESSAGE_EVENT_INVALID_MESSAGE_REF", getResult.getKey());
    assertEquals("MESSAGE_EVENT_INVALID_MESSAGE_REF", getResult.getProblem());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link EventValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link CancelEventDefinition} (default
   *       constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link EventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given ArrayList() add CancelEventDefinition (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenArrayListAddCancelEventDefinition_thenArrayListEmpty() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setEventDefinitions(eventDefinitions);

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
  }

  /**
   * Test {@link EventValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link CompensateEventDefinition} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link EventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given ArrayList() add CompensateEventDefinition (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenArrayListAddCompensateEventDefinition() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CompensateEventDefinition());

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setEventDefinitions(eventDefinitions);

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
  }

  /**
   * Test {@link EventValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link BoundaryEvent} (default constructor) EventDefinitions is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link EventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given BoundaryEvent (default constructor) EventDefinitions is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenBoundaryEventEventDefinitionsIsNull() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setEventDefinitions(null);

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
  }

  /**
   * Test {@link EventValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link CompensateEventDefinition} (default constructor) ActivityRef is empty
   *       string.
   * </ul>
   *
   * <p>Method under test: {@link EventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given CompensateEventDefinition (default constructor) ActivityRef is empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenCompensateEventDefinitionActivityRefIsEmptyString() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    CompensateEventDefinition compensateEventDefinition = new CompensateEventDefinition();
    compensateEventDefinition.setActivityRef("");

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(compensateEventDefinition);

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setEventDefinitions(eventDefinitions);

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
  }

  /**
   * Test {@link EventValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@code false}.
   *   <li>Then {@link ArrayList#ArrayList()} first ProcessDefinitionId is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link EventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given 'false'; then ArrayList() first ProcessDefinitionId is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenFalse_thenArrayListFirstProcessDefinitionIdIs42() {
    // Arrange
    EventValidator eventValidator = new EventValidator();

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.containsMessageId(Mockito.<String>any())).thenReturn(false);

    MessageEventDefinition messageEventDefinition = mock(MessageEventDefinition.class);
    when(messageEventDefinition.getMessageRef()).thenReturn("Message Ref");

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(messageEventDefinition);

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setEventDefinitions(eventDefinitions);

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
    verify(bpmnModel).containsMessageId("Message Ref");
    verify(messageEventDefinition, atLeast(1)).getMessageRef();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getName();
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getProcessDefinitionId());
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
   * Test {@link EventValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link TimerEventDefinition} (default constructor) TimeCycle is {@code Flow
   *       Elements Of Type}.
   * </ul>
   *
   * <p>Method under test: {@link EventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given TimerEventDefinition (default constructor) TimeCycle is 'Flow Elements Of Type'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenTimerEventDefinitionTimeCycleIsFlowElementsOfType() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    TimerEventDefinition timerEventDefinition = new TimerEventDefinition();
    timerEventDefinition.setTimeCycle("Flow Elements Of Type");

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(timerEventDefinition);

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setEventDefinitions(eventDefinitions);

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
  }

  /**
   * Test {@link EventValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link TimerEventDefinition} (default constructor) TimeDate is {@code Flow Elements
   *       Of Type}.
   * </ul>
   *
   * <p>Method under test: {@link EventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given TimerEventDefinition (default constructor) TimeDate is 'Flow Elements Of Type'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenTimerEventDefinitionTimeDateIsFlowElementsOfType() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    TimerEventDefinition timerEventDefinition = new TimerEventDefinition();
    timerEventDefinition.setTimeDate("Flow Elements Of Type");

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(timerEventDefinition);

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setEventDefinitions(eventDefinitions);

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
  }

  /**
   * Test {@link EventValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link TimerEventDefinition} (default constructor) TimeDuration is {@code Flow
   *       Elements Of Type}.
   * </ul>
   *
   * <p>Method under test: {@link EventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given TimerEventDefinition (default constructor) TimeDuration is 'Flow Elements Of Type'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenTimerEventDefinitionTimeDurationIsFlowElementsOfType() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    TimerEventDefinition timerEventDefinition = new TimerEventDefinition();
    timerEventDefinition.setTimeDuration("Flow Elements Of Type");

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(timerEventDefinition);

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setEventDefinitions(eventDefinitions);

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
  }

  /**
   * Test {@link EventValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>When {@link BpmnModel} {@link BpmnModel#containsMessageId(String)} return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link EventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given 'true'; when BpmnModel containsMessageId(String) return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenTrue_whenBpmnModelContainsMessageIdReturnTrue() {
    // Arrange
    EventValidator eventValidator = new EventValidator();

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.containsMessageId(Mockito.<String>any())).thenReturn(true);

    MessageEventDefinition messageEventDefinition = mock(MessageEventDefinition.class);
    when(messageEventDefinition.getMessageRef()).thenReturn("Message Ref");

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(messageEventDefinition);

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setEventDefinitions(eventDefinitions);

    ArrayList<Event> eventList = new ArrayList<>();
    eventList.add(boundaryEvent);

    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(eventList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(bpmnModel).containsMessageId("Message Ref");
    verify(messageEventDefinition, atLeast(1)).getMessageRef();
    verify(process).findFlowElementsOfType(isA(Class.class));
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link EventValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link ValidationError} (default constructor) ActivityId is {@code 42}.
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link EventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given ValidationError (default constructor) ActivityId is '42'; then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenValidationErrorActivityIdIs42_thenArrayListSizeIsTwo() {
    // Arrange
    EventValidator eventValidator = new EventValidator();

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.containsMessageId(Mockito.<String>any())).thenReturn(false);

    MessageEventDefinition messageEventDefinition = mock(MessageEventDefinition.class);
    when(messageEventDefinition.getMessageRef()).thenReturn("Message Ref");

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(messageEventDefinition);

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setEventDefinitions(eventDefinitions);

    ArrayList<Event> eventList = new ArrayList<>();
    eventList.add(boundaryEvent);

    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<Event>>any())).thenReturn(eventList);

    ValidationError validationError = new ValidationError();
    validationError.setActivityId("42");
    validationError.setActivityName("MESSAGE_EVENT_INVALID_MESSAGE_REF");
    validationError.setDefaultDescription("MESSAGE_EVENT_INVALID_MESSAGE_REF");
    validationError.setKey("MESSAGE_EVENT_INVALID_MESSAGE_REF");
    validationError.setParams(new HashMap<>());
    validationError.setProblem("MESSAGE_EVENT_INVALID_MESSAGE_REF");
    validationError.setProcessDefinitionId("42");
    validationError.setProcessDefinitionName("MESSAGE_EVENT_INVALID_MESSAGE_REF");
    validationError.setValidatorSetName("MESSAGE_EVENT_INVALID_MESSAGE_REF");
    validationError.setWarning(true);
    validationError.setXmlColumnNumber(10);
    validationError.setXmlLineNumber(2);

    ArrayList<ValidationError> errors = new ArrayList<>();
    errors.add(validationError);

    // Act
    eventValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process).getId();
    verify(bpmnModel).containsMessageId("Message Ref");
    verify(messageEventDefinition, atLeast(1)).getMessageRef();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getName();
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
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
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link EventValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>When {@link BpmnModel} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link EventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); when BpmnModel (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_whenBpmnModel_thenArrayListEmpty() {
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
  }

  /**
   * Test {@link EventValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>When {@link Process} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link EventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); when Process (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_whenProcess_thenArrayListEmpty() {
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
  }

  /**
   * Test {@link EventValidator#handleSignalEventDefinition(BpmnModel, Process, Event,
   * EventDefinition, List)}.
   *
   * <p>Method under test: {@link EventValidator#handleSignalEventDefinition(BpmnModel, Process,
   * Event, EventDefinition, List)}
   */
  @Test
  @DisplayName("Test handleSignalEventDefinition(BpmnModel, Process, Event, EventDefinition, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventValidator.handleSignalEventDefinition(BpmnModel, Process, Event, EventDefinition, List)"
  })
  void testHandleSignalEventDefinition() {
    // Arrange
    EventValidator eventValidator = new EventValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(null);
    Process process = new Process();
    BoundaryEvent event = new BoundaryEvent();

    SignalEventDefinition eventDefinition = new SignalEventDefinition();
    eventDefinition.setSignalRef("");
    eventDefinition.setSignalExpression("");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.handleSignalEventDefinition(bpmnModel, process, event, eventDefinition, errors);

    // Assert
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("SIGNAL_EVENT_MISSING_SIGNAL_REF", getResult.getDefaultDescription());
    assertEquals("SIGNAL_EVENT_MISSING_SIGNAL_REF", getResult.getKey());
    assertEquals("SIGNAL_EVENT_MISSING_SIGNAL_REF", getResult.getProblem());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link EventValidator#handleSignalEventDefinition(BpmnModel, Process, Event,
   * EventDefinition, List)}.
   *
   * <p>Method under test: {@link EventValidator#handleSignalEventDefinition(BpmnModel, Process,
   * Event, EventDefinition, List)}
   */
  @Test
  @DisplayName("Test handleSignalEventDefinition(BpmnModel, Process, Event, EventDefinition, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventValidator.handleSignalEventDefinition(BpmnModel, Process, Event, EventDefinition, List)"
  })
  void testHandleSignalEventDefinition2() {
    // Arrange
    EventValidator eventValidator = new EventValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(null);
    Process process = new Process();
    BoundaryEvent event = new BoundaryEvent();

    SignalEventDefinition eventDefinition = new SignalEventDefinition();
    eventDefinition.setSignalRef("");
    eventDefinition.setSignalExpression("Event Definition");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.handleSignalEventDefinition(bpmnModel, process, event, eventDefinition, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link EventValidator#handleSignalEventDefinition(BpmnModel, Process, Event,
   * EventDefinition, List)}.
   *
   * <p>Method under test: {@link EventValidator#handleSignalEventDefinition(BpmnModel, Process,
   * Event, EventDefinition, List)}
   */
  @Test
  @DisplayName("Test handleSignalEventDefinition(BpmnModel, Process, Event, EventDefinition, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventValidator.handleSignalEventDefinition(BpmnModel, Process, Event, EventDefinition, List)"
  })
  void testHandleSignalEventDefinition3() {
    // Arrange
    EventValidator eventValidator = new EventValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(null);
    Process process = new Process();
    BoundaryEvent event = new BoundaryEvent();

    SignalEventDefinition eventDefinition = new SignalEventDefinition();
    eventDefinition.setSignalRef("Event Definition");
    eventDefinition.setSignalExpression("");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.handleSignalEventDefinition(bpmnModel, process, event, eventDefinition, errors);

    // Assert
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("SIGNAL_EVENT_INVALID_SIGNAL_REF", getResult.getDefaultDescription());
    assertEquals("SIGNAL_EVENT_INVALID_SIGNAL_REF", getResult.getKey());
    assertEquals("SIGNAL_EVENT_INVALID_SIGNAL_REF", getResult.getProblem());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link EventValidator#handleSignalEventDefinition(BpmnModel, Process, Event,
   * EventDefinition, List)}.
   *
   * <ul>
   *   <li>Given {@link Signal#Signal(String, String)} with id is {@code Event Definition} and
   *       {@code Name}.
   * </ul>
   *
   * <p>Method under test: {@link EventValidator#handleSignalEventDefinition(BpmnModel, Process,
   * Event, EventDefinition, List)}
   */
  @Test
  @DisplayName(
      "Test handleSignalEventDefinition(BpmnModel, Process, Event, EventDefinition, List); given Signal(String, String) with id is 'Event Definition' and 'Name'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventValidator.handleSignalEventDefinition(BpmnModel, Process, Event, EventDefinition, List)"
  })
  void testHandleSignalEventDefinition_givenSignalWithIdIsEventDefinitionAndName() {
    // Arrange
    EventValidator eventValidator = new EventValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(new Signal("Event Definition", "Name"));
    Process process = new Process();
    BoundaryEvent event = new BoundaryEvent();

    SignalEventDefinition eventDefinition = new SignalEventDefinition();
    eventDefinition.setSignalRef("Event Definition");
    eventDefinition.setSignalExpression("");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.handleSignalEventDefinition(bpmnModel, process, event, eventDefinition, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test {@link EventValidator#handleSignalEventDefinition(BpmnModel, Process, Event,
   * EventDefinition, List)}.
   *
   * <ul>
   *   <li>Then {@link BpmnModel} (default constructor) Signals size is one.
   * </ul>
   *
   * <p>Method under test: {@link EventValidator#handleSignalEventDefinition(BpmnModel, Process,
   * Event, EventDefinition, List)}
   */
  @Test
  @DisplayName(
      "Test handleSignalEventDefinition(BpmnModel, Process, Event, EventDefinition, List); then BpmnModel (default constructor) Signals size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventValidator.handleSignalEventDefinition(BpmnModel, Process, Event, EventDefinition, List)"
  })
  void testHandleSignalEventDefinition_thenBpmnModelSignalsSizeIsOne() {
    // Arrange
    EventValidator eventValidator = new EventValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(new Signal("42", "Name"));
    Process process = new Process();
    BoundaryEvent event = new BoundaryEvent();

    SignalEventDefinition eventDefinition = new SignalEventDefinition();
    eventDefinition.setSignalRef("Event Definition");
    eventDefinition.setSignalExpression("");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.handleSignalEventDefinition(bpmnModel, process, event, eventDefinition, errors);

    // Assert
    Collection<Signal> signals = bpmnModel.getSignals();
    assertEquals(1, signals.size());
    assertTrue(signals instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("SIGNAL_EVENT_INVALID_SIGNAL_REF", getResult.getDefaultDescription());
    assertEquals("SIGNAL_EVENT_INVALID_SIGNAL_REF", getResult.getKey());
    assertEquals("SIGNAL_EVENT_INVALID_SIGNAL_REF", getResult.getProblem());
  }

  /**
   * Test {@link EventValidator#handleSignalEventDefinition(BpmnModel, Process, Event,
   * EventDefinition, List)}.
   *
   * <ul>
   *   <li>When {@link SignalEventDefinition} (default constructor) SignalExpression is {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link EventValidator#handleSignalEventDefinition(BpmnModel, Process,
   * Event, EventDefinition, List)}
   */
  @Test
  @DisplayName(
      "Test handleSignalEventDefinition(BpmnModel, Process, Event, EventDefinition, List); when SignalEventDefinition (default constructor) SignalExpression is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventValidator.handleSignalEventDefinition(BpmnModel, Process, Event, EventDefinition, List)"
  })
  void testHandleSignalEventDefinition_whenSignalEventDefinitionSignalExpressionIsNull() {
    // Arrange
    EventValidator eventValidator = new EventValidator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(null);
    Process process = new Process();
    BoundaryEvent event = new BoundaryEvent();

    SignalEventDefinition eventDefinition = new SignalEventDefinition();
    eventDefinition.setSignalRef("");
    eventDefinition.setSignalExpression(null);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.handleSignalEventDefinition(bpmnModel, process, event, eventDefinition, errors);

    // Assert
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("SIGNAL_EVENT_MISSING_SIGNAL_REF", getResult.getDefaultDescription());
    assertEquals("SIGNAL_EVENT_MISSING_SIGNAL_REF", getResult.getKey());
    assertEquals("SIGNAL_EVENT_MISSING_SIGNAL_REF", getResult.getProblem());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link EventValidator#handleTimerEventDefinition(Process, Event, EventDefinition, List)}.
   *
   * <p>Method under test: {@link EventValidator#handleTimerEventDefinition(Process, Event,
   * EventDefinition, List)}
   */
  @Test
  @DisplayName("Test handleTimerEventDefinition(Process, Event, EventDefinition, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventValidator.handleTimerEventDefinition(Process, Event, EventDefinition, List)"
  })
  void testHandleTimerEventDefinition() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    Process process = new Process();
    BoundaryEvent event = new BoundaryEvent();

    TimerEventDefinition eventDefinition = new TimerEventDefinition();
    eventDefinition.setTimeDate("");
    eventDefinition.setTimeCycle("");
    eventDefinition.setTimeDuration("Event Definition");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.handleTimerEventDefinition(process, event, eventDefinition, errors);

    // Assert that nothing has changed
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test {@link EventValidator#handleTimerEventDefinition(Process, Event, EventDefinition, List)}.
   *
   * <p>Method under test: {@link EventValidator#handleTimerEventDefinition(Process, Event,
   * EventDefinition, List)}
   */
  @Test
  @DisplayName("Test handleTimerEventDefinition(Process, Event, EventDefinition, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventValidator.handleTimerEventDefinition(Process, Event, EventDefinition, List)"
  })
  void testHandleTimerEventDefinition2() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    Process process = new Process();
    BoundaryEvent event = new BoundaryEvent();

    TimerEventDefinition eventDefinition = new TimerEventDefinition();
    eventDefinition.setTimeDate("");
    eventDefinition.setTimeCycle("Event Definition");
    eventDefinition.setTimeDuration("");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.handleTimerEventDefinition(process, event, eventDefinition, errors);

    // Assert that nothing has changed
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test {@link EventValidator#handleTimerEventDefinition(Process, Event, EventDefinition, List)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link EventValidator#handleTimerEventDefinition(Process, Event,
   * EventDefinition, List)}
   */
  @Test
  @DisplayName(
      "Test handleTimerEventDefinition(Process, Event, EventDefinition, List); given 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventValidator.handleTimerEventDefinition(Process, Event, EventDefinition, List)"
  })
  void testHandleTimerEventDefinition_givenNull() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    Process process = new Process();
    BoundaryEvent event = new BoundaryEvent();

    TimerEventDefinition eventDefinition = new TimerEventDefinition();
    eventDefinition.setTimeDate("");
    eventDefinition.setTimeCycle("");
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
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link EventValidator#handleTimerEventDefinition(Process, Event, EventDefinition, List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link EventValidator#handleTimerEventDefinition(Process, Event,
   * EventDefinition, List)}
   */
  @Test
  @DisplayName(
      "Test handleTimerEventDefinition(Process, Event, EventDefinition, List); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventValidator.handleTimerEventDefinition(Process, Event, EventDefinition, List)"
  })
  void testHandleTimerEventDefinition_thenArrayListSizeIsOne() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    Process process = new Process();
    BoundaryEvent event = new BoundaryEvent();

    TimerEventDefinition eventDefinition = new TimerEventDefinition();
    eventDefinition.setTimeDate("");
    eventDefinition.setTimeCycle("");
    eventDefinition.setTimeDuration("");
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
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test {@link EventValidator#handleTimerEventDefinition(Process, Event, EventDefinition, List)}.
   *
   * <ul>
   *   <li>When {@link TimerEventDefinition} (default constructor) TimeDate is {@code Event
   *       Definition}.
   * </ul>
   *
   * <p>Method under test: {@link EventValidator#handleTimerEventDefinition(Process, Event,
   * EventDefinition, List)}
   */
  @Test
  @DisplayName(
      "Test handleTimerEventDefinition(Process, Event, EventDefinition, List); when TimerEventDefinition (default constructor) TimeDate is 'Event Definition'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventValidator.handleTimerEventDefinition(Process, Event, EventDefinition, List)"
  })
  void testHandleTimerEventDefinition_whenTimerEventDefinitionTimeDateIsEventDefinition() {
    // Arrange
    EventValidator eventValidator = new EventValidator();
    Process process = new Process();
    BoundaryEvent event = new BoundaryEvent();

    TimerEventDefinition eventDefinition = new TimerEventDefinition();
    eventDefinition.setTimeDate("Event Definition");
    eventDefinition.setTimeCycle("");
    eventDefinition.setTimeDuration("");
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventValidator.handleTimerEventDefinition(process, event, eventDefinition, errors);

    // Assert that nothing has changed
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(flowElements.isEmpty());
  }
}
