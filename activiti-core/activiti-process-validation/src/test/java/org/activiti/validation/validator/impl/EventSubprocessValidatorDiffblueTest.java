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
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.CancelEventDefinition;
import org.activiti.bpmn.model.ErrorEventDefinition;
import org.activiti.bpmn.model.EventDefinition;
import org.activiti.bpmn.model.EventSubProcess;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.Signal;
import org.activiti.bpmn.model.SignalEventDefinition;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.SubProcess;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class EventSubprocessValidatorDiffblueTest {
  /**
   * Test {@link EventSubprocessValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ErrorEventDefinition} (default
   *       constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link EventSubprocessValidator#executeValidation(BpmnModel, Process,
   * List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given ArrayList() add ErrorEventDefinition (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventSubprocessValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenArrayListAddErrorEventDefinition_thenArrayListEmpty() {
    // Arrange
    EventSubprocessValidator eventSubprocessValidator = new EventSubprocessValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<EventSubProcess> eventSubProcessList = new ArrayList<>();
    eventSubProcessList.add(new EventSubProcess());

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new ErrorEventDefinition());

    StartEvent startEvent = new StartEvent();
    startEvent.setEventDefinitions(eventDefinitions);

    ArrayList<StartEvent> startEventList = new ArrayList<>();
    startEventList.add(startEvent);

    Process process = mock(Process.class);
    when(process.findFlowElementsInSubProcessOfType(
            Mockito.<SubProcess>any(), Mockito.<Class<StartEvent>>any()))
        .thenReturn(startEventList);
    when(process.findFlowElementsOfType(Mockito.<Class<EventSubProcess>>any()))
        .thenReturn(eventSubProcessList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventSubprocessValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(process).findFlowElementsInSubProcessOfType(isA(SubProcess.class), isA(Class.class));
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
   * Test {@link EventSubprocessValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link EventSubProcess} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link EventSubprocessValidator#executeValidation(BpmnModel, Process,
   * List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given ArrayList() add EventSubProcess (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventSubprocessValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenArrayListAddEventSubProcess_thenArrayListEmpty() {
    // Arrange
    EventSubprocessValidator eventSubprocessValidator = new EventSubprocessValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<EventSubProcess> eventSubProcessList = new ArrayList<>();
    eventSubProcessList.add(new EventSubProcess());

    ArrayList<StartEvent> startEventList = new ArrayList<>();
    startEventList.add(new StartEvent());

    Process process = mock(Process.class);
    when(process.findFlowElementsInSubProcessOfType(
            Mockito.<SubProcess>any(), Mockito.<Class<StartEvent>>any()))
        .thenReturn(startEventList);
    when(process.findFlowElementsOfType(Mockito.<Class<EventSubProcess>>any()))
        .thenReturn(eventSubProcessList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventSubprocessValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(process).findFlowElementsInSubProcessOfType(isA(SubProcess.class), isA(Class.class));
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
   * Test {@link EventSubprocessValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link MessageEventDefinition} (default
   *       constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link EventSubprocessValidator#executeValidation(BpmnModel, Process,
   * List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given ArrayList() add MessageEventDefinition (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventSubprocessValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenArrayListAddMessageEventDefinition_thenArrayListEmpty() {
    // Arrange
    EventSubprocessValidator eventSubprocessValidator = new EventSubprocessValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<EventSubProcess> eventSubProcessList = new ArrayList<>();
    eventSubProcessList.add(new EventSubProcess());

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new MessageEventDefinition());

    StartEvent startEvent = new StartEvent();
    startEvent.setEventDefinitions(eventDefinitions);

    ArrayList<StartEvent> startEventList = new ArrayList<>();
    startEventList.add(startEvent);

    Process process = mock(Process.class);
    when(process.findFlowElementsInSubProcessOfType(
            Mockito.<SubProcess>any(), Mockito.<Class<StartEvent>>any()))
        .thenReturn(startEventList);
    when(process.findFlowElementsOfType(Mockito.<Class<EventSubProcess>>any()))
        .thenReturn(eventSubProcessList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventSubprocessValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(process).findFlowElementsInSubProcessOfType(isA(SubProcess.class), isA(Class.class));
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
   * Test {@link EventSubprocessValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link EventSubprocessValidator#executeValidation(BpmnModel, Process,
   * List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given ArrayList() add 'null'; then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventSubprocessValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenArrayListAddNull_thenArrayListSizeIsOne() {
    // Arrange
    EventSubprocessValidator eventSubprocessValidator = new EventSubprocessValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<EventSubProcess> eventSubProcessList = new ArrayList<>();
    eventSubProcessList.add(null);

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());

    StartEvent startEvent = new StartEvent();
    startEvent.setEventDefinitions(eventDefinitions);

    ArrayList<StartEvent> startEventList = new ArrayList<>();
    startEventList.add(startEvent);

    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsInSubProcessOfType(
            Mockito.<SubProcess>any(), Mockito.<Class<StartEvent>>any()))
        .thenReturn(startEventList);
    when(process.findFlowElementsOfType(Mockito.<Class<EventSubProcess>>any()))
        .thenReturn(eventSubProcessList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventSubprocessValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process).getId();
    verify(process).findFlowElementsInSubProcessOfType(isNull(), isA(Class.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getName();
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals(
        "EVENT_SUBPROCESS_INVALID_START_EVENT_DEFINITION", getResult.getDefaultDescription());
    assertEquals("EVENT_SUBPROCESS_INVALID_START_EVENT_DEFINITION", getResult.getKey());
    assertEquals("EVENT_SUBPROCESS_INVALID_START_EVENT_DEFINITION", getResult.getProblem());
    assertEquals("Name", getResult.getProcessDefinitionName());
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
   * Test {@link EventSubprocessValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link SignalEventDefinition} (default
   *       constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link EventSubprocessValidator#executeValidation(BpmnModel, Process,
   * List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given ArrayList() add SignalEventDefinition (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventSubprocessValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenArrayListAddSignalEventDefinition_thenArrayListEmpty() {
    // Arrange
    EventSubprocessValidator eventSubprocessValidator = new EventSubprocessValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<EventSubProcess> eventSubProcessList = new ArrayList<>();
    eventSubProcessList.add(new EventSubProcess());

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new SignalEventDefinition());

    StartEvent startEvent = new StartEvent();
    startEvent.setEventDefinitions(eventDefinitions);

    ArrayList<StartEvent> startEventList = new ArrayList<>();
    startEventList.add(startEvent);

    Process process = mock(Process.class);
    when(process.findFlowElementsInSubProcessOfType(
            Mockito.<SubProcess>any(), Mockito.<Class<StartEvent>>any()))
        .thenReturn(startEventList);
    when(process.findFlowElementsOfType(Mockito.<Class<EventSubProcess>>any()))
        .thenReturn(eventSubProcessList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventSubprocessValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(process).findFlowElementsInSubProcessOfType(isA(SubProcess.class), isA(Class.class));
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
   * Test {@link EventSubprocessValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link EventSubprocessValidator#executeValidation(BpmnModel, Process,
   * List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given ArrayList(); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventSubprocessValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenArrayList_thenArrayListEmpty() {
    // Arrange
    EventSubprocessValidator eventSubprocessValidator = new EventSubprocessValidator();
    BpmnModel bpmnModel = new BpmnModel();

    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<EventSubProcess>>any()))
        .thenReturn(new ArrayList<>());
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventSubprocessValidator.executeValidation(bpmnModel, process, errors);

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
   * Test {@link EventSubprocessValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link StartEvent} (default constructor) EventDefinitions is {@code null}.
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link EventSubprocessValidator#executeValidation(BpmnModel, Process,
   * List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given StartEvent (default constructor) EventDefinitions is 'null'; then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventSubprocessValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenStartEventEventDefinitionsIsNull_thenArrayListEmpty() {
    // Arrange
    EventSubprocessValidator eventSubprocessValidator = new EventSubprocessValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<EventSubProcess> eventSubProcessList = new ArrayList<>();
    eventSubProcessList.add(new EventSubProcess());

    StartEvent startEvent = new StartEvent();
    startEvent.setEventDefinitions(null);

    ArrayList<StartEvent> startEventList = new ArrayList<>();
    startEventList.add(startEvent);

    Process process = mock(Process.class);
    when(process.findFlowElementsInSubProcessOfType(
            Mockito.<SubProcess>any(), Mockito.<Class<StartEvent>>any()))
        .thenReturn(startEventList);
    when(process.findFlowElementsOfType(Mockito.<Class<EventSubProcess>>any()))
        .thenReturn(eventSubProcessList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventSubprocessValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(process).findFlowElementsInSubProcessOfType(isA(SubProcess.class), isA(Class.class));
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
   * Test {@link EventSubprocessValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link SubProcess} (default constructor) addFlowElement {@link AdhocSubProcess}
   *       (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link EventSubprocessValidator#executeValidation(BpmnModel, Process,
   * List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given SubProcess (default constructor) addFlowElement AdhocSubProcess (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventSubprocessValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenSubProcessAddFlowElementAdhocSubProcess() {
    // Arrange
    EventSubprocessValidator eventSubprocessValidator = new EventSubprocessValidator();
    BpmnModel bpmnModel = new BpmnModel();

    SubProcess element = new SubProcess();
    element.addFlowElement(new AdhocSubProcess());

    SubProcess element2 = new SubProcess();
    element2.addFlowElement(element);

    SubProcess element3 = new SubProcess();
    element3.addFlowElement(element2);

    Process process = new Process();
    process.addFlowElement(element3);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventSubprocessValidator.executeValidation(bpmnModel, process, errors);

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
   * Test {@link EventSubprocessValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link EventSubprocessValidator#executeValidation(BpmnModel, Process,
   * List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventSubprocessValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_thenArrayListSizeIsOne() {
    // Arrange
    EventSubprocessValidator eventSubprocessValidator = new EventSubprocessValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<EventSubProcess> eventSubProcessList = new ArrayList<>();
    eventSubProcessList.add(new EventSubProcess());

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());

    StartEvent startEvent = new StartEvent();
    startEvent.setEventDefinitions(eventDefinitions);

    ArrayList<StartEvent> startEventList = new ArrayList<>();
    startEventList.add(startEvent);

    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsInSubProcessOfType(
            Mockito.<SubProcess>any(), Mockito.<Class<StartEvent>>any()))
        .thenReturn(startEventList);
    when(process.findFlowElementsOfType(Mockito.<Class<EventSubProcess>>any()))
        .thenReturn(eventSubProcessList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventSubprocessValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process).getId();
    verify(process).findFlowElementsInSubProcessOfType(isA(SubProcess.class), isA(Class.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getName();
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals(
        "EVENT_SUBPROCESS_INVALID_START_EVENT_DEFINITION", getResult.getDefaultDescription());
    assertEquals("EVENT_SUBPROCESS_INVALID_START_EVENT_DEFINITION", getResult.getKey());
    assertEquals("EVENT_SUBPROCESS_INVALID_START_EVENT_DEFINITION", getResult.getProblem());
    assertEquals("Name", getResult.getProcessDefinitionName());
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
   * Test {@link EventSubprocessValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link EventSubprocessValidator#executeValidation(BpmnModel, Process,
   * List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventSubprocessValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_thenArrayListSizeIsTwo() {
    // Arrange
    EventSubprocessValidator eventSubprocessValidator = new EventSubprocessValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<EventSubProcess> eventSubProcessList = new ArrayList<>();
    eventSubProcessList.add(new EventSubProcess());
    eventSubProcessList.add(new EventSubProcess());

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());

    StartEvent startEvent = new StartEvent();
    startEvent.setEventDefinitions(eventDefinitions);

    ArrayList<StartEvent> startEventList = new ArrayList<>();
    startEventList.add(startEvent);

    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsInSubProcessOfType(
            Mockito.<SubProcess>any(), Mockito.<Class<StartEvent>>any()))
        .thenReturn(startEventList);
    when(process.findFlowElementsOfType(Mockito.<Class<EventSubProcess>>any()))
        .thenReturn(eventSubProcessList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventSubprocessValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process, atLeast(1)).getId();
    verify(process, atLeast(1))
        .findFlowElementsInSubProcessOfType(Mockito.<SubProcess>any(), isA(Class.class));
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getName();
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals(
        "EVENT_SUBPROCESS_INVALID_START_EVENT_DEFINITION", getResult.getDefaultDescription());
    assertEquals("EVENT_SUBPROCESS_INVALID_START_EVENT_DEFINITION", getResult.getKey());
    assertEquals("EVENT_SUBPROCESS_INVALID_START_EVENT_DEFINITION", getResult.getProblem());
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
   * Test {@link EventSubprocessValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>When {@link Process} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link EventSubprocessValidator#executeValidation(BpmnModel, Process,
   * List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); when Process (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventSubprocessValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_whenProcess_thenArrayListEmpty() {
    // Arrange
    EventSubprocessValidator eventSubprocessValidator = new EventSubprocessValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventSubprocessValidator.executeValidation(bpmnModel, process, errors);

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
