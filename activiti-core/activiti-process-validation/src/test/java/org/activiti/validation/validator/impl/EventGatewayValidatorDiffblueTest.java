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
import org.activiti.bpmn.model.EventGateway;
import org.activiti.bpmn.model.IntermediateCatchEvent;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.Signal;
import org.activiti.validation.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class EventGatewayValidatorDiffblueTest {
  /**
   * Test {@link EventGatewayValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link Process} {@link Process#getId()} return {@code 42}.
   *   <li>Then {@link ArrayList#ArrayList()} size is one.
   * </ul>
   *
   * <p>Method under test: {@link EventGatewayValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given '42'; when Process getId() return '42'; then ArrayList() size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventGatewayValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_given42_whenProcessGetIdReturn42_thenArrayListSizeIsOne() {
    // Arrange
    EventGatewayValidator eventGatewayValidator = new EventGatewayValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SequenceFlow> outgoingFlows = new ArrayList<>();
    outgoingFlows.add(new SequenceFlow("Source Ref", "Target Ref"));

    EventGateway eventGateway = new EventGateway();
    eventGateway.setOutgoingFlows(outgoingFlows);

    ArrayList<EventGateway> eventGatewayList = new ArrayList<>();
    eventGatewayList.add(eventGateway);

    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean()))
        .thenReturn(new AdhocSubProcess());
    when(process.findFlowElementsOfType(Mockito.<Class<EventGateway>>any()))
        .thenReturn(eventGatewayList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventGatewayValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process).getId();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getFlowElement("Target Ref", true);
    verify(process).getName();
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertEquals(1, errors.size());
    ValidationError getResult = errors.get(0);
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals(
        "EVENT_GATEWAY_ONLY_CONNECTED_TO_INTERMEDIATE_EVENTS", getResult.getDefaultDescription());
    assertEquals("EVENT_GATEWAY_ONLY_CONNECTED_TO_INTERMEDIATE_EVENTS", getResult.getKey());
    assertEquals("EVENT_GATEWAY_ONLY_CONNECTED_TO_INTERMEDIATE_EVENTS", getResult.getProblem());
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
   * Test {@link EventGatewayValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link EventGateway} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link EventGatewayValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given ArrayList() add EventGateway (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventGatewayValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenArrayListAddEventGateway_thenArrayListEmpty() {
    // Arrange
    EventGatewayValidator eventGatewayValidator = new EventGatewayValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<EventGateway> eventGatewayList = new ArrayList<>();
    eventGatewayList.add(new EventGateway());

    Process process = mock(Process.class);
    when(process.findFlowElementsOfType(Mockito.<Class<EventGateway>>any()))
        .thenReturn(eventGatewayList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventGatewayValidator.executeValidation(bpmnModel, process, errors);

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
   * Test {@link EventGatewayValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@link IntermediateCatchEvent} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link EventGatewayValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given IntermediateCatchEvent (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventGatewayValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenIntermediateCatchEvent() {
    // Arrange
    EventGatewayValidator eventGatewayValidator = new EventGatewayValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SequenceFlow> outgoingFlows = new ArrayList<>();
    outgoingFlows.add(new SequenceFlow("Source Ref", "Target Ref"));

    EventGateway eventGateway = new EventGateway();
    eventGateway.setOutgoingFlows(outgoingFlows);

    ArrayList<EventGateway> eventGatewayList = new ArrayList<>();
    eventGatewayList.add(eventGateway);

    Process process = mock(Process.class);
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean()))
        .thenReturn(new IntermediateCatchEvent());
    when(process.findFlowElementsOfType(Mockito.<Class<EventGateway>>any()))
        .thenReturn(eventGatewayList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventGatewayValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getFlowElement("Target Ref", true);
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link EventGatewayValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link Process} {@link Process#getFlowElement(String, boolean)} return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link EventGatewayValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); given 'null'; when Process getFlowElement(String, boolean) return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventGatewayValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_givenNull_whenProcessGetFlowElementReturnNull() {
    // Arrange
    EventGatewayValidator eventGatewayValidator = new EventGatewayValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SequenceFlow> outgoingFlows = new ArrayList<>();
    outgoingFlows.add(new SequenceFlow("Source Ref", "Target Ref"));

    EventGateway eventGateway = new EventGateway();
    eventGateway.setOutgoingFlows(outgoingFlows);

    ArrayList<EventGateway> eventGatewayList = new ArrayList<>();
    eventGatewayList.add(eventGateway);

    Process process = mock(Process.class);
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean())).thenReturn(null);
    when(process.findFlowElementsOfType(Mockito.<Class<EventGateway>>any()))
        .thenReturn(eventGatewayList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventGatewayValidator.executeValidation(bpmnModel, process, errors);

    // Assert that nothing has changed
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process).getFlowElement("Target Ref", true);
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(errors.isEmpty());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link EventGatewayValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link EventGatewayValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName("Test executeValidation(BpmnModel, Process, List); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventGatewayValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_thenArrayListSizeIsTwo() {
    // Arrange
    EventGatewayValidator eventGatewayValidator = new EventGatewayValidator();
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SequenceFlow> outgoingFlows = new ArrayList<>();
    outgoingFlows.add(
        new SequenceFlow(
            "EVENT_GATEWAY_ONLY_CONNECTED_TO_INTERMEDIATE_EVENTS",
            "EVENT_GATEWAY_ONLY_CONNECTED_TO_INTERMEDIATE_EVENTS"));
    outgoingFlows.add(new SequenceFlow("Source Ref", "Target Ref"));

    EventGateway eventGateway = new EventGateway();
    eventGateway.setOutgoingFlows(outgoingFlows);

    ArrayList<EventGateway> eventGatewayList = new ArrayList<>();
    eventGatewayList.add(eventGateway);

    Process process = mock(Process.class);
    when(process.getId()).thenReturn("42");
    when(process.getName()).thenReturn("Name");
    when(process.getFlowElement(Mockito.<String>any(), anyBoolean()))
        .thenReturn(new AdhocSubProcess());
    when(process.findFlowElementsOfType(Mockito.<Class<EventGateway>>any()))
        .thenReturn(eventGatewayList);
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventGatewayValidator.executeValidation(bpmnModel, process, errors);

    // Assert
    verify(process, atLeast(1)).getId();
    verify(process).findFlowElementsOfType(isA(Class.class));
    verify(process, atLeast(1)).getFlowElement(Mockito.<String>any(), eq(true));
    verify(process, atLeast(1)).getName();
    assertEquals(2, errors.size());
    ValidationError getResult = errors.get(1);
    assertEquals("42", getResult.getProcessDefinitionId());
    assertEquals(
        "EVENT_GATEWAY_ONLY_CONNECTED_TO_INTERMEDIATE_EVENTS", getResult.getDefaultDescription());
    assertEquals("EVENT_GATEWAY_ONLY_CONNECTED_TO_INTERMEDIATE_EVENTS", getResult.getKey());
    assertEquals("EVENT_GATEWAY_ONLY_CONNECTED_TO_INTERMEDIATE_EVENTS", getResult.getProblem());
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
   * Test {@link EventGatewayValidator#executeValidation(BpmnModel, Process, List)}.
   *
   * <ul>
   *   <li>When {@link Process} (default constructor).
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link EventGatewayValidator#executeValidation(BpmnModel, Process, List)}
   */
  @Test
  @DisplayName(
      "Test executeValidation(BpmnModel, Process, List); when Process (default constructor); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventGatewayValidator.executeValidation(BpmnModel, Process, List)"})
  void testExecuteValidation_whenProcess_thenArrayListEmpty() {
    // Arrange
    EventGatewayValidator eventGatewayValidator = new EventGatewayValidator();
    BpmnModel bpmnModel = new BpmnModel();
    Process process = new Process();
    ArrayList<ValidationError> errors = new ArrayList<>();

    // Act
    eventGatewayValidator.executeValidation(bpmnModel, process, errors);

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
