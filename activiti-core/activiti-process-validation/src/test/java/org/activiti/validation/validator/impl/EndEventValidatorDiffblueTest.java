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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.CancelEventDefinition;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.EventDefinition;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.Signal;
import org.activiti.bpmn.model.SubProcess;
import org.activiti.bpmn.model.Transaction;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class EndEventValidatorDiffblueTest {
  /**
   * Test {@link EndEventValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link Process} {@link Process#getId()} return {@code 42}.
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link EndEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given '42'; when Process getId() return '42'; then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EndEventValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_given42_whenProcessGetIdReturn42_thenArrayListSizeIsOne() {
    // Arrange
    EndEventValidator endEventValidator = new EndEventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());

    EndEvent endEvent = new EndEvent();
    endEvent.setEventDefinitions(eventDefinitions);

    ArrayList<EndEvent> endEventList = new ArrayList<>();
    endEventList.add(endEvent);

    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findParent(Mockito.<FlowElement>any())).thenReturn(new AdhocSubProcess());
    when(process.findFlowElementsOfType(Mockito.<Class<EndEvent>>any())).thenReturn(endEventList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    endEventValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process).getId();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).findParent(isA(FlowElement.class));
    verify(process).getName();
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("END_EVENT_CANCEL_ONLY_INSIDE_TRANSACTION", getResult.getDefaultDescription());
    assertEquals("END_EVENT_CANCEL_ONLY_INSIDE_TRANSACTION", getResult.getKey());
    assertEquals("END_EVENT_CANCEL_ONLY_INSIDE_TRANSACTION", getResult.getProblem());
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
   * Test {@link EndEventValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link EndEvent} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link EndEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given ArrayList() add EndEvent (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EndEventValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenArrayListAddEndEvent_thenArrayListEmpty() {
    // Arrange
    EndEventValidator endEventValidator = new EndEventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<EndEvent> endEventList = new ArrayList<>();
    endEventList.add(new EndEvent());

    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<EndEvent>>any())).thenReturn(endEventList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    endEventValidator.executeValidation(bpmnModel, process, errors);

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
   * Test {@link EndEventValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link EndEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given ArrayList() add 'null'; then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EndEventValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenArrayListAddNull_thenArrayListEmpty() {
    // Arrange
    EndEventValidator endEventValidator = new EndEventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(null);

    EndEvent endEvent = new EndEvent();
    endEvent.setEventDefinitions(eventDefinitions);

    ArrayList<EndEvent> endEventList = new ArrayList<>();
    endEventList.add(endEvent);

    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<EndEvent>>any())).thenReturn(endEventList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    endEventValidator.executeValidation(bpmnModel, process, errors);

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
   * Test {@link EndEventValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link EndEvent} (default constructor) EventDefinitions is {@code null}.
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link EndEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given EndEvent (default constructor) EventDefinitions is 'null'; then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EndEventValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenEndEventEventDefinitionsIsNull_thenArrayListEmpty() {
    // Arrange
    EndEventValidator endEventValidator = new EndEventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    EndEvent endEvent = new EndEvent();
    endEvent.setEventDefinitions(null);

    ArrayList<EndEvent> endEventList = new ArrayList<>();
    endEventList.add(endEvent);

    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<EndEvent>>any())).thenReturn(endEventList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    endEventValidator.executeValidation(bpmnModel, process, errors);

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
   * Test {@link EndEventValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link SubProcess} (default constructor) addFlowElement {@link AdhocSubProcess}
   *       (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link EndEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given SubProcess (default constructor) addFlowElement AdhocSubProcess (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EndEventValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenSubProcessAddFlowElementAdhocSubProcess() {
    // Arrange
    EndEventValidator endEventValidator = new EndEventValidator();
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
    endEventValidator.executeValidation(bpmnModel, process, errors);

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
   * Test {@link EndEventValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link Transaction} (default constructor).
   *   <li>When {@link Process} {@link Process#findParent(FlowElement)} return {@link Transaction}
   *       (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link EndEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given Transaction (default constructor); when Process findParent(FlowElement) return Transaction (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EndEventValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenTransaction_whenProcessFindParentReturnTransaction() {
    // Arrange
    EndEventValidator endEventValidator = new EndEventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());

    EndEvent endEvent = new EndEvent();
    endEvent.setEventDefinitions(eventDefinitions);

    ArrayList<EndEvent> endEventList = new ArrayList<>();
    endEventList.add(endEvent);

    Process process = mock(Process.class);
    when(process.findParent(Mockito.<FlowElement>any())).thenReturn(new Transaction());
    when(process.findFlowElementsOfType(Mockito.<Class<EndEvent>>any())).thenReturn(endEventList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    endEventValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).findParent(isA(FlowElement.class));
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link EndEventValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link EndEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EndEventValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_thenArrayListSizeIsTwo() {
    // Arrange
    EndEventValidator endEventValidator = new EndEventValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());

    EndEvent endEvent = new EndEvent();
    endEvent.setEventDefinitions(eventDefinitions);

    ArrayList<EndEvent> endEventList = new ArrayList<>();
    endEventList.add(endEvent);

    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.findParent(Mockito.<FlowElement>any())).thenReturn(new AdhocSubProcess());
    when(process.findFlowElementsOfType(Mockito.<Class<EndEvent>>any())).thenReturn(endEventList);

    ValidationError validationError = new ValidationError();
    validationError.setActivityId("END_EVENT_CANCEL_ONLY_INSIDE_TRANSACTION");
    validationError.setActivityName("Activity Name");
    validationError.setDefaultDescription("Default Description");
    validationError.setKey("Key");
    validationError.setParams(new HashMap<>());
    validationError.setProblem("Problem");
    validationError.setProcessDefinitionId("END_EVENT_CANCEL_ONLY_INSIDE_TRANSACTION");
    validationError.setProcessDefinitionName("Process Definition Name");
    validationError.setValidatorSetName("Validator Set Name");
    validationError.setWarning(false);
    validationError.setXmlColumnNumber(1);
    validationError.setXmlLineNumber(10);

    ArrayList<ValidationError> errors = new ArrayList<>();
    errors.add(validationError);

    // Act
    endEventValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process).getId();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).findParent(isA(FlowElement.class));
    verify(process).getName();
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals("END_EVENT_CANCEL_ONLY_INSIDE_TRANSACTION", getResult.getDefaultDescription());
    assertEquals("END_EVENT_CANCEL_ONLY_INSIDE_TRANSACTION", getResult.getKey());
    assertEquals("END_EVENT_CANCEL_ONLY_INSIDE_TRANSACTION", getResult.getProblem());
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
   * Test {@link EndEventValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>When {@link Process} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link EndEventValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); when Process (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EndEventValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_whenProcess_thenArrayListEmpty() {
    // Arrange
    EndEventValidator endEventValidator = new EndEventValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    endEventValidator.executeValidation(bpmnModel, process, errors);

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
