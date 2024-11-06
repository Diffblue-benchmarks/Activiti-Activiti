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
import java.util.List;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.CancelEventDefinition;
import org.activiti.bpmn.model.EventDefinition;
import org.activiti.bpmn.model.IntermediateCatchEvent;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.Signal;
import org.activiti.bpmn.model.SignalEventDefinition;
import org.activiti.bpmn.model.TimerEventDefinition;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class IntermediateCatchEventValidatorDiffblueTest {
  /**
   * Test
   * {@link IntermediateCatchEventValidator#executeValidation(BpmnModel, Process, List)}.
   * <p>
   * Method under test:
   * {@link IntermediateCatchEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List)")
  void testExecuteValidation() {
    // Arrange
    IntermediateCatchEventValidator intermediateCatchEventValidator = new IntermediateCatchEventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<EventDefinition> eventDefinitionList = new ArrayList<>();
    eventDefinitionList.add(new CancelEventDefinition());
    IntermediateCatchEvent intermediateCatchEvent = mock(IntermediateCatchEvent.class);
    when(intermediateCatchEvent.getXmlColumnNumber()).thenReturn(10);
    when(intermediateCatchEvent.getXmlRowNumber()).thenReturn(10);
    when(intermediateCatchEvent.getId()).thenReturn("42");
    when(intermediateCatchEvent.getName()).thenReturn("Name");
    when(intermediateCatchEvent.getEventDefinitions()).thenReturn(eventDefinitionList);

    ArrayList<IntermediateCatchEvent> intermediateCatchEventList = new ArrayList<>();
    intermediateCatchEventList.add(intermediateCatchEvent);
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<IntermediateCatchEvent>>any()))
        .thenReturn(intermediateCatchEventList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    intermediateCatchEventValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(intermediateCatchEvent).getId();
    verify(process).getId();
    verify(intermediateCatchEvent).getXmlColumnNumber();
    verify(intermediateCatchEvent).getXmlRowNumber();
    verify(intermediateCatchEvent, atLeast(1)).getEventDefinitions();
    verify(intermediateCatchEvent).getName();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getName();
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("INTERMEDIATE_CATCH_EVENT_INVALID_EVENTDEFINITION", getResult.getDefaultDescription());
    assertEquals("INTERMEDIATE_CATCH_EVENT_INVALID_EVENTDEFINITION", getResult.getKey());
    assertEquals("INTERMEDIATE_CATCH_EVENT_INVALID_EVENTDEFINITION", getResult.getProblem());
    assertEquals("Name", getResult.getActivityName());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
  }

  /**
   * Test
   * {@link IntermediateCatchEventValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link MessageEventDefinition}
   * (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link IntermediateCatchEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given ArrayList() add MessageEventDefinition (default constructor)")
  void testExecuteValidation_givenArrayListAddMessageEventDefinition() {
    // Arrange
    IntermediateCatchEventValidator intermediateCatchEventValidator = new IntermediateCatchEventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<EventDefinition> eventDefinitionList = new ArrayList<>();
    eventDefinitionList.add(new MessageEventDefinition());
    IntermediateCatchEvent intermediateCatchEvent = mock(IntermediateCatchEvent.class);
    when(intermediateCatchEvent.getEventDefinitions()).thenReturn(eventDefinitionList);

    ArrayList<IntermediateCatchEvent> intermediateCatchEventList = new ArrayList<>();
    intermediateCatchEventList.add(intermediateCatchEvent);
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<IntermediateCatchEvent>>any()))
        .thenReturn(intermediateCatchEventList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    intermediateCatchEventValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(intermediateCatchEvent, atLeast(1)).getEventDefinitions();
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
   * {@link IntermediateCatchEventValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link SignalEventDefinition}
   * (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link IntermediateCatchEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given ArrayList() add SignalEventDefinition (default constructor)")
  void testExecuteValidation_givenArrayListAddSignalEventDefinition() {
    // Arrange
    IntermediateCatchEventValidator intermediateCatchEventValidator = new IntermediateCatchEventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<EventDefinition> eventDefinitionList = new ArrayList<>();
    eventDefinitionList.add(new SignalEventDefinition());
    IntermediateCatchEvent intermediateCatchEvent = mock(IntermediateCatchEvent.class);
    when(intermediateCatchEvent.getEventDefinitions()).thenReturn(eventDefinitionList);

    ArrayList<IntermediateCatchEvent> intermediateCatchEventList = new ArrayList<>();
    intermediateCatchEventList.add(intermediateCatchEvent);
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<IntermediateCatchEvent>>any()))
        .thenReturn(intermediateCatchEventList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    intermediateCatchEventValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(intermediateCatchEvent, atLeast(1)).getEventDefinitions();
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
   * {@link IntermediateCatchEventValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link TimerEventDefinition}
   * (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link IntermediateCatchEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given ArrayList() add TimerEventDefinition (default constructor)")
  void testExecuteValidation_givenArrayListAddTimerEventDefinition() {
    // Arrange
    IntermediateCatchEventValidator intermediateCatchEventValidator = new IntermediateCatchEventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<EventDefinition> eventDefinitionList = new ArrayList<>();
    eventDefinitionList.add(new TimerEventDefinition());
    IntermediateCatchEvent intermediateCatchEvent = mock(IntermediateCatchEvent.class);
    when(intermediateCatchEvent.getEventDefinitions()).thenReturn(eventDefinitionList);

    ArrayList<IntermediateCatchEvent> intermediateCatchEventList = new ArrayList<>();
    intermediateCatchEventList.add(intermediateCatchEvent);
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<IntermediateCatchEvent>>any()))
        .thenReturn(intermediateCatchEventList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    intermediateCatchEventValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(intermediateCatchEvent, atLeast(1)).getEventDefinitions();
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
   * {@link IntermediateCatchEventValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then {@link BpmnModel} (default constructor) Resources {@link List}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link IntermediateCatchEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); given ArrayList(); then BpmnModel (default constructor) Resources List")
  void testExecuteValidation_givenArrayList_thenBpmnModelResourcesList() {
    // Arrange
    IntermediateCatchEventValidator intermediateCatchEventValidator = new IntermediateCatchEventValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<IntermediateCatchEvent>>any())).thenReturn(new ArrayList<>());
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    intermediateCatchEventValidator.executeValidation(bpmnModel, process, errors);

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
   * {@link IntermediateCatchEventValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} first ActivityId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link IntermediateCatchEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() first ActivityId is '42'")
  void testExecuteValidation_thenArrayListFirstActivityIdIs42() {
    // Arrange
    IntermediateCatchEventValidator intermediateCatchEventValidator = new IntermediateCatchEventValidator();
    BpmnModel bpmnModel = new BpmnModel();
    IntermediateCatchEvent intermediateCatchEvent = mock(IntermediateCatchEvent.class);
    when(intermediateCatchEvent.getXmlColumnNumber()).thenReturn(10);
    when(intermediateCatchEvent.getXmlRowNumber()).thenReturn(10);
    when(intermediateCatchEvent.getId()).thenReturn("42");
    when(intermediateCatchEvent.getName()).thenReturn("Name");
    when(intermediateCatchEvent.getEventDefinitions()).thenReturn(new ArrayList<>());

    ArrayList<IntermediateCatchEvent> intermediateCatchEventList = new ArrayList<>();
    intermediateCatchEventList.add(intermediateCatchEvent);
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<IntermediateCatchEvent>>any()))
        .thenReturn(intermediateCatchEventList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    intermediateCatchEventValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(intermediateCatchEvent).getId();
    verify(process).getId();
    verify(intermediateCatchEvent).getXmlColumnNumber();
    verify(intermediateCatchEvent).getXmlRowNumber();
    verify(intermediateCatchEvent).getEventDefinitions();
    verify(intermediateCatchEvent).getName();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getName();
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("Name", getResult.getActivityName());
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlLineNumber());
  }

  /**
   * Test
   * {@link IntermediateCatchEventValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} first ActivityId is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link IntermediateCatchEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() first ActivityId is 'null'")
  void testExecuteValidation_thenArrayListFirstActivityIdIsNull() {
    // Arrange
    IntermediateCatchEventValidator intermediateCatchEventValidator = new IntermediateCatchEventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<IntermediateCatchEvent> intermediateCatchEventList = new ArrayList<>();
    intermediateCatchEventList.add(new IntermediateCatchEvent());
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<IntermediateCatchEvent>>any()))
        .thenReturn(intermediateCatchEventList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    intermediateCatchEventValidator.executeValidation(bpmnModel, process, errors);

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
   * {@link IntermediateCatchEventValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link IntermediateCatchEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() size is two")
  void testExecuteValidation_thenArrayListSizeIsTwo() {
    // Arrange
    IntermediateCatchEventValidator intermediateCatchEventValidator = new IntermediateCatchEventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<IntermediateCatchEvent> intermediateCatchEventList = new ArrayList<>();
    intermediateCatchEventList.add(new IntermediateCatchEvent());
    intermediateCatchEventList.add(new IntermediateCatchEvent());
    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findFlowElementsOfType(Mockito.<Class<IntermediateCatchEvent>>any()))
        .thenReturn(intermediateCatchEventList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    intermediateCatchEventValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process, atLeast(1)).getId();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getName();
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("INTERMEDIATE_CATCH_EVENT_NO_EVENTDEFINITION", getResult.getDefaultDescription());
    assertEquals("INTERMEDIATE_CATCH_EVENT_NO_EVENTDEFINITION", getResult.getKey());
    assertEquals("INTERMEDIATE_CATCH_EVENT_NO_EVENTDEFINITION", getResult.getProblem());
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
   * Test
   * {@link IntermediateCatchEventValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>When {@link BpmnModel}.</li>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link IntermediateCatchEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); when BpmnModel; then ArrayList() Empty")
  void testExecuteValidation_whenBpmnModel_thenArrayListEmpty() {
    // Arrange
    IntermediateCatchEventValidator intermediateCatchEventValidator = new IntermediateCatchEventValidator();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    intermediateCatchEventValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    assertTrue(errors.isEmpty());
  }

  /**
   * Test
   * {@link IntermediateCatchEventValidator#executeValidation(BpmnModel, Process, List)}.
   * <ul>
   *   <li>When {@link Process} (default constructor).</li>
   *   <li>Then {@link BpmnModel} (default constructor) Resources {@link List}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link IntermediateCatchEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); when Process (default constructor); then BpmnModel (default constructor) Resources List")
  void testExecuteValidation_whenProcess_thenBpmnModelResourcesList() {
    // Arrange
    IntermediateCatchEventValidator intermediateCatchEventValidator = new IntermediateCatchEventValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    intermediateCatchEventValidator.executeValidation(bpmnModel, process, errors);

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
