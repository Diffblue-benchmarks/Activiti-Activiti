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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class IntermediateCatchEventValidatorDiffblueTest {
  /**
   * Method under test:
   * {@link IntermediateCatchEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation() {
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
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
  }

  /**
   * Method under test:
   * {@link IntermediateCatchEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation2() {
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
   * Method under test:
   * {@link IntermediateCatchEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation3() {
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
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
  }

  /**
   * Method under test:
   * {@link IntermediateCatchEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation4() {
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
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
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
   * {@link IntermediateCatchEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation5() {
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
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getProcessDefinitionId());
    ValidationError getResult2 = errors.get(1);
    assertEquals("42", getResult2.getProcessDefinitionId());
    assertEquals("INTERMEDIATE_CATCH_EVENT_NO_EVENTDEFINITION", getResult.getDefaultDescription());
    assertEquals("INTERMEDIATE_CATCH_EVENT_NO_EVENTDEFINITION", getResult2.getDefaultDescription());
    assertEquals("INTERMEDIATE_CATCH_EVENT_NO_EVENTDEFINITION", getResult.getKey());
    assertEquals("INTERMEDIATE_CATCH_EVENT_NO_EVENTDEFINITION", getResult2.getKey());
    assertEquals("INTERMEDIATE_CATCH_EVENT_NO_EVENTDEFINITION", getResult.getProblem());
    assertEquals("INTERMEDIATE_CATCH_EVENT_NO_EVENTDEFINITION", getResult2.getProblem());
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
   * {@link IntermediateCatchEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation6() {
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
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("INTERMEDIATE_CATCH_EVENT_NO_EVENTDEFINITION", getResult.getDefaultDescription());
    assertEquals("INTERMEDIATE_CATCH_EVENT_NO_EVENTDEFINITION", getResult.getKey());
    assertEquals("INTERMEDIATE_CATCH_EVENT_NO_EVENTDEFINITION", getResult.getProblem());
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
   * {@link IntermediateCatchEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation7() {
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
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getActivityId());
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("INTERMEDIATE_CATCH_EVENT_INVALID_EVENTDEFINITION", getResult.getDefaultDescription());
    assertEquals("INTERMEDIATE_CATCH_EVENT_INVALID_EVENTDEFINITION", getResult.getKey());
    assertEquals("INTERMEDIATE_CATCH_EVENT_INVALID_EVENTDEFINITION", getResult.getProblem());
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
   * {@link IntermediateCatchEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation8() {
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
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
  }

  /**
   * Method under test:
   * {@link IntermediateCatchEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation9() {
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
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
  }

  /**
   * Method under test:
   * {@link IntermediateCatchEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  void testExecuteValidation10() {
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
    assertTrue(bpmnModel.getImports().isEmpty());
    assertTrue(bpmnModel.getInterfaces().isEmpty());
    assertTrue(bpmnModel.getPools().isEmpty());
    assertTrue(bpmnModel.getProcesses().isEmpty());
  }
}
