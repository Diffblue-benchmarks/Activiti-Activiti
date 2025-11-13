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
package org.activiti.engine.impl.bpmn.deployer;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.CancelEventDefinition;
import org.activiti.bpmn.model.EventDefinition;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.test.util.TestProcessUtil;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class EventSubscriptionManagerDiffblueTest {
  /**
   * Test {@link EventSubscriptionManager#addMessageEventSubscriptions(ProcessDefinitionEntity,
   * Process, BpmnModel)}.
   *
   * <p>Method under test: {@link
   * EventSubscriptionManager#addMessageEventSubscriptions(ProcessDefinitionEntity, Process,
   * BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventSubscriptionManager.addMessageEventSubscriptions(ProcessDefinitionEntity, Process, BpmnModel)"
  })
  public void testAddMessageEventSubscriptions() {
    // Arrange
    EventSubscriptionManager eventSubscriptionManager = new EventSubscriptionManager();
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    Process process = mock(Process.class);
    when(process.getFlowElements()).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionManager.addMessageEventSubscriptions(
                processDefinition, process, TestProcessUtil.createOneTaskBpmnModel()));
    verify(process).getFlowElements();
  }

  /**
   * Test {@link EventSubscriptionManager#addMessageEventSubscriptions(ProcessDefinitionEntity,
   * Process, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link CancelEventDefinition} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionManager#addMessageEventSubscriptions(ProcessDefinitionEntity, Process,
   * BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventSubscriptionManager.addMessageEventSubscriptions(ProcessDefinitionEntity, Process, BpmnModel)"
  })
  public void testAddMessageEventSubscriptions_givenArrayListAddCancelEventDefinition() {
    // Arrange
    EventSubscriptionManager eventSubscriptionManager = new EventSubscriptionManager();
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    messageEventDefinition.setMessageRef(":");

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());
    eventDefinitions.add(messageEventDefinition);

    StartEvent startEvent = new StartEvent();
    startEvent.setEventDefinitions(eventDefinitions);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(startEvent);

    Process process = mock(Process.class);
    when(process.getFlowElements()).thenReturn(flowElementSet);

    // Act
    eventSubscriptionManager.addMessageEventSubscriptions(
        processDefinition, process, TestProcessUtil.createOneTaskBpmnModel());

    // Assert
    verify(process, atLeast(1)).getFlowElements();
  }

  /**
   * Test {@link EventSubscriptionManager#addMessageEventSubscriptions(ProcessDefinitionEntity,
   * Process, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link StartEvent} (default constructor) EventDefinitions is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionManager#addMessageEventSubscriptions(ProcessDefinitionEntity, Process,
   * BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventSubscriptionManager.addMessageEventSubscriptions(ProcessDefinitionEntity, Process, BpmnModel)"
  })
  public void testAddMessageEventSubscriptions_givenStartEventEventDefinitionsIsNull() {
    // Arrange
    EventSubscriptionManager eventSubscriptionManager = new EventSubscriptionManager();
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    StartEvent startEvent = new StartEvent();
    startEvent.setEventDefinitions(null);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(startEvent);

    Process process = mock(Process.class);
    when(process.getFlowElements()).thenReturn(flowElementSet);

    // Act
    eventSubscriptionManager.addMessageEventSubscriptions(
        processDefinition, process, TestProcessUtil.createOneTaskBpmnModel());

    // Assert
    verify(process, atLeast(1)).getFlowElements();
  }

  /**
   * Test {@link EventSubscriptionManager#addMessageEventSubscriptions(ProcessDefinitionEntity,
   * Process, BpmnModel)}.
   *
   * <ul>
   *   <li>Then calls {@link BpmnModel#containsMessageId(String)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionManager#addMessageEventSubscriptions(ProcessDefinitionEntity, Process,
   * BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventSubscriptionManager.addMessageEventSubscriptions(ProcessDefinitionEntity, Process, BpmnModel)"
  })
  public void testAddMessageEventSubscriptions_thenCallsContainsMessageId() {
    // Arrange
    EventSubscriptionManager eventSubscriptionManager = new EventSubscriptionManager();
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    messageEventDefinition.setMessageRef(":");

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(messageEventDefinition);

    StartEvent startEvent = new StartEvent();
    startEvent.setEventDefinitions(eventDefinitions);

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(startEvent);

    Process process = mock(Process.class);
    when(process.getFlowElements()).thenReturn(flowElementSet);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getMessage(Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));
    when(bpmnModel.containsMessageId(Mockito.<String>any())).thenReturn(true);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionManager.addMessageEventSubscriptions(
                processDefinition, process, bpmnModel));
    verify(bpmnModel).containsMessageId(":");
    verify(bpmnModel).getMessage(":");
    verify(process, atLeast(1)).getFlowElements();
  }

  /**
   * Test {@link EventSubscriptionManager#addMessageEventSubscriptions(ProcessDefinitionEntity,
   * Process, BpmnModel)}.
   *
   * <ul>
   *   <li>Then calls {@link Process#getFlowElements()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionManager#addMessageEventSubscriptions(ProcessDefinitionEntity, Process,
   * BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventSubscriptionManager.addMessageEventSubscriptions(ProcessDefinitionEntity, Process, BpmnModel)"
  })
  public void testAddMessageEventSubscriptions_thenCallsGetFlowElements() {
    // Arrange
    EventSubscriptionManager eventSubscriptionManager = new EventSubscriptionManager();
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    StartEvent startEvent = new StartEvent();
    startEvent.setEventDefinitions(new ArrayList<>());

    LinkedHashSet<FlowElement> flowElementSet = new LinkedHashSet<>();
    flowElementSet.add(startEvent);

    Process process = mock(Process.class);
    when(process.getFlowElements()).thenReturn(flowElementSet);

    // Act
    eventSubscriptionManager.addMessageEventSubscriptions(
        processDefinition, process, TestProcessUtil.createOneTaskBpmnModel());

    // Assert
    verify(process, atLeast(1)).getFlowElements();
  }

  /**
   * Test {@link EventSubscriptionManager#addMessageEventSubscriptions(ProcessDefinitionEntity,
   * Process, BpmnModel)}.
   *
   * <ul>
   *   <li>When createOneTaskProcessWithId {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionManager#addMessageEventSubscriptions(ProcessDefinitionEntity, Process,
   * BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventSubscriptionManager.addMessageEventSubscriptions(ProcessDefinitionEntity, Process, BpmnModel)"
  })
  public void testAddMessageEventSubscriptions_whenCreateOneTaskProcessWithId42() {
    // Arrange
    EventSubscriptionManager eventSubscriptionManager = new EventSubscriptionManager();
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();
    Process process = TestProcessUtil.createOneTaskProcessWithId("42");

    // Act and Assert
    eventSubscriptionManager.addMessageEventSubscriptions(
        processDefinition, process, TestProcessUtil.createOneTaskBpmnModel());
  }

  /**
   * Test {@link EventSubscriptionManager#addMessageEventSubscriptions(ProcessDefinitionEntity,
   * Process, BpmnModel)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionManager#addMessageEventSubscriptions(ProcessDefinitionEntity, Process,
   * BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventSubscriptionManager.addMessageEventSubscriptions(ProcessDefinitionEntity, Process, BpmnModel)"
  })
  public void testAddMessageEventSubscriptions_whenNull_thenDoesNotThrow() {
    // Arrange
    EventSubscriptionManager eventSubscriptionManager = new EventSubscriptionManager();
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    // Act and Assert
    eventSubscriptionManager.addMessageEventSubscriptions(
        processDefinition, null, TestProcessUtil.createOneTaskBpmnModel());
  }

  /**
   * Test {@link EventSubscriptionManager#addMessageEventSubscriptions(ProcessDefinitionEntity,
   * Process, BpmnModel)}.
   *
   * <ul>
   *   <li>When {@link Process} (default constructor).
   *   <li>Then does not throw.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionManager#addMessageEventSubscriptions(ProcessDefinitionEntity, Process,
   * BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventSubscriptionManager.addMessageEventSubscriptions(ProcessDefinitionEntity, Process, BpmnModel)"
  })
  public void testAddMessageEventSubscriptions_whenProcess_thenDoesNotThrow() {
    // Arrange
    EventSubscriptionManager eventSubscriptionManager = new EventSubscriptionManager();
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();
    Process process = new Process();

    // Act and Assert
    eventSubscriptionManager.addMessageEventSubscriptions(
        processDefinition, process, TestProcessUtil.createOneTaskBpmnModel());
  }

  /**
   * Test {@link EventSubscriptionManager#insertMessageEvent(MessageEventDefinition, StartEvent,
   * ProcessDefinitionEntity, BpmnModel)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * EventSubscriptionManager#insertMessageEvent(MessageEventDefinition, StartEvent,
   * ProcessDefinitionEntity, BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventSubscriptionManager.insertMessageEvent(MessageEventDefinition, StartEvent, ProcessDefinitionEntity, BpmnModel)"
  })
  public void testInsertMessageEvent_thenThrowActivitiException() {
    // Arrange
    EventSubscriptionManager eventSubscriptionManager = new EventSubscriptionManager();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    StartEvent startEvent = new StartEvent();
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getMessage(Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));
    when(bpmnModel.containsMessageId(Mockito.<String>any())).thenReturn(true);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            eventSubscriptionManager.insertMessageEvent(
                messageEventDefinition, startEvent, processDefinition, bpmnModel));
    verify(bpmnModel).containsMessageId(null);
    verify(bpmnModel).getMessage(null);
  }
}
