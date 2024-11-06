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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.CancelEventDefinition;
import org.activiti.bpmn.model.EventDefinition;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SignalEventDefinition;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.TimerEventDefinition;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class StartEventValidatorDiffblueTest {
  /**
   * Test {@link StartEventValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link Process#findFlowElementsOfType(Class, boolean)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given ArrayList(); then calls findFlowElementsOfType(Class, boolean)")
  void testExecuteValidation_givenArrayList_thenCallsFindFlowElementsOfType() {
    // Arrange
    StartEventValidator startEventValidator = new StartEventValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<FlowElement>>any(), anyBoolean())).thenReturn(new ArrayList<>());

    // Act
    startEventValidator.executeValidation(bpmnModel, process, new ArrayList<>());

    // Assert that nothing has changed
    verify(process).findFlowElementsOfType(isA(Class.class), eq(false));
  }

  /**
   * Test {@link StartEventValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link ValidationError} (default constructor) ActivityId is
   * {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given ValidationError (default constructor) ActivityId is '42'")
  void testExecuteValidation_givenValidationErrorActivityIdIs42() {
    // Arrange
    StartEventValidator startEventValidator = new StartEventValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<FlowElement>>any(), anyBoolean())).thenReturn(new ArrayList<>());

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
    startEventValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(process).findFlowElementsOfType(isA(Class.class), eq(false));
  }

  /**
   * Test {@link StartEventValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link ValidationError} (default constructor) ActivityId is
   * {@code Activity Id}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given ValidationError (default constructor) ActivityId is 'Activity Id'")
  void testExecuteValidation_givenValidationErrorActivityIdIsActivityId() {
    // Arrange
    StartEventValidator startEventValidator = new StartEventValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<FlowElement>>any(), anyBoolean())).thenReturn(new ArrayList<>());

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
    startEventValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(process).findFlowElementsOfType(isA(Class.class), eq(false));
  }

  /**
   * Test
   * {@link StartEventValidator#validateEventDefinitionTypes(List, Process, List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link MessageEventDefinition}
   * (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartEventValidator#validateEventDefinitionTypes(List, Process, List)}
   */
  @Test
  @DisplayName("Test validateEventDefinitionTypes(List, Process, List); given ArrayList() add MessageEventDefinition (default constructor)")
  void testValidateEventDefinitionTypes_givenArrayListAddMessageEventDefinition() {
    // Arrange
    StartEventValidator startEventValidator = new StartEventValidator();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new MessageEventDefinition());

    StartEvent startEvent = new StartEvent();
    startEvent.setEventDefinitions(eventDefinitions);

    ArrayList<StartEvent> startEvents = new ArrayList<>();
    startEvents.add(startEvent);
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    startEventValidator.validateEventDefinitionTypes(startEvents, process, errors);

    // Assert that nothing has changed
    Collection<Artifact> artifacts = process.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test
   * {@link StartEventValidator#validateEventDefinitionTypes(List, Process, List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link SignalEventDefinition}
   * (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartEventValidator#validateEventDefinitionTypes(List, Process, List)}
   */
  @Test
  @DisplayName("Test validateEventDefinitionTypes(List, Process, List); given ArrayList() add SignalEventDefinition (default constructor)")
  void testValidateEventDefinitionTypes_givenArrayListAddSignalEventDefinition() {
    // Arrange
    StartEventValidator startEventValidator = new StartEventValidator();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new SignalEventDefinition());

    StartEvent startEvent = new StartEvent();
    startEvent.setEventDefinitions(eventDefinitions);

    ArrayList<StartEvent> startEvents = new ArrayList<>();
    startEvents.add(startEvent);
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    startEventValidator.validateEventDefinitionTypes(startEvents, process, errors);

    // Assert that nothing has changed
    Collection<Artifact> artifacts = process.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test
   * {@link StartEventValidator#validateEventDefinitionTypes(List, Process, List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link TimerEventDefinition}
   * (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartEventValidator#validateEventDefinitionTypes(List, Process, List)}
   */
  @Test
  @DisplayName("Test validateEventDefinitionTypes(List, Process, List); given ArrayList() add TimerEventDefinition (default constructor)")
  void testValidateEventDefinitionTypes_givenArrayListAddTimerEventDefinition() {
    // Arrange
    StartEventValidator startEventValidator = new StartEventValidator();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new TimerEventDefinition());

    StartEvent startEvent = new StartEvent();
    startEvent.setEventDefinitions(eventDefinitions);

    ArrayList<StartEvent> startEvents = new ArrayList<>();
    startEvents.add(startEvent);
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    startEventValidator.validateEventDefinitionTypes(startEvents, process, errors);

    // Assert that nothing has changed
    Collection<Artifact> artifacts = process.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test
   * {@link StartEventValidator#validateEventDefinitionTypes(List, Process, List)}.
   * <ul>
   *   <li>Given {@link StartEvent} (default constructor) EventDefinitions is
   * {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartEventValidator#validateEventDefinitionTypes(List, Process, List)}
   */
  @Test
  @DisplayName("Test validateEventDefinitionTypes(List, Process, List); given StartEvent (default constructor) EventDefinitions is ArrayList()")
  void testValidateEventDefinitionTypes_givenStartEventEventDefinitionsIsArrayList() {
    // Arrange
    StartEventValidator startEventValidator = new StartEventValidator();

    StartEvent startEvent = new StartEvent();
    startEvent.setEventDefinitions(new ArrayList<>());

    ArrayList<StartEvent> startEvents = new ArrayList<>();
    startEvents.add(startEvent);
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    startEventValidator.validateEventDefinitionTypes(startEvents, process, errors);

    // Assert that nothing has changed
    Collection<Artifact> artifacts = process.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test
   * {@link StartEventValidator#validateEventDefinitionTypes(List, Process, List)}.
   * <ul>
   *   <li>Given {@link StartEvent} (default constructor) EventDefinitions is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartEventValidator#validateEventDefinitionTypes(List, Process, List)}
   */
  @Test
  @DisplayName("Test validateEventDefinitionTypes(List, Process, List); given StartEvent (default constructor) EventDefinitions is 'null'")
  void testValidateEventDefinitionTypes_givenStartEventEventDefinitionsIsNull() {
    // Arrange
    StartEventValidator startEventValidator = new StartEventValidator();

    StartEvent startEvent = new StartEvent();
    startEvent.setEventDefinitions(null);

    ArrayList<StartEvent> startEvents = new ArrayList<>();
    startEvents.add(startEvent);
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    startEventValidator.validateEventDefinitionTypes(startEvents, process, errors);

    // Assert that nothing has changed
    Collection<Artifact> artifacts = process.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
  }

  /**
   * Test
   * {@link StartEventValidator#validateEventDefinitionTypes(List, Process, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartEventValidator#validateEventDefinitionTypes(List, Process, List)}
   */
  @Test
  @DisplayName("Test validateEventDefinitionTypes(List, Process, List); then ArrayList() size is one")
  void testValidateEventDefinitionTypes_thenArrayListSizeIsOne() {
    // Arrange
    StartEventValidator startEventValidator = new StartEventValidator();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());

    StartEvent startEvent = new StartEvent();
    startEvent.setEventDefinitions(eventDefinitions);

    ArrayList<StartEvent> startEvents = new ArrayList<>();
    startEvents.add(startEvent);
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    startEventValidator.validateEventDefinitionTypes(startEvents, process, errors);

    // Assert
    Collection<Artifact> artifacts = process.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("START_EVENT_INVALID_EVENT_DEFINITION", getResult.getDefaultDescription());
    assertEquals("START_EVENT_INVALID_EVENT_DEFINITION", getResult.getKey());
    assertEquals("START_EVENT_INVALID_EVENT_DEFINITION", getResult.getProblem());
    assertNull(getResult.getActivityId());
    assertNull(getResult.getActivityName());
    assertNull(getResult.getProcessDefinitionId());
    assertNull(getResult.getProcessDefinitionName());
    assertNull(getResult.getValidatorSetName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlLineNumber());
    assertFalse(getResult.isWarning());
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(getResult.getParams().isEmpty());
  }

  /**
   * Test
   * {@link StartEventValidator#validateEventDefinitionTypes(List, Process, List)}.
   * <ul>
   *   <li>When {@link Process} (default constructor).</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartEventValidator#validateEventDefinitionTypes(List, Process, List)}
   */
  @Test
  @DisplayName("Test validateEventDefinitionTypes(List, Process, List); when Process (default constructor); then ArrayList() Empty")
  void testValidateEventDefinitionTypes_whenProcess_thenArrayListEmpty() {
    // Arrange
    StartEventValidator startEventValidator = new StartEventValidator();
    ArrayList<StartEvent> startEvents = new ArrayList<>();
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    startEventValidator.validateEventDefinitionTypes(startEvents, process, errors);

    // Assert that nothing has changed
    Collection<Artifact> artifacts = process.getArtifacts();
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = process.getFlowElements();
    assertTrue(flowElements instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(artifacts.isEmpty());
    assertTrue(flowElements.isEmpty());
  }
}
