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
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class EventSubscriptionManagerDiffblueTest {
  /**
   * Test {@link EventSubscriptionManager#addMessageEventSubscriptions(ProcessDefinitionEntity, Process, BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionManager#addMessageEventSubscriptions(ProcessDefinitionEntity, Process, BpmnModel)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void EventSubscriptionManager.addMessageEventSubscriptions(ProcessDefinitionEntity, Process, BpmnModel)"})
  public void testAddMessageEventSubscriptions_givenArrayList() {
    // Arrange
    EventSubscriptionManager eventSubscriptionManager = new EventSubscriptionManager();
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();
    Process process = mock(Process.class);
    when(process.getFlowElements()).thenReturn(new ArrayList<>());

    // Act
    eventSubscriptionManager.addMessageEventSubscriptions(processDefinition, process, new BpmnModel());

    // Assert
    verify(process).getFlowElements();
  }

  /**
   * Test {@link EventSubscriptionManager#addMessageEventSubscriptions(ProcessDefinitionEntity, Process, BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link AdhocSubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionManager#addMessageEventSubscriptions(ProcessDefinitionEntity, Process, BpmnModel)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void EventSubscriptionManager.addMessageEventSubscriptions(ProcessDefinitionEntity, Process, BpmnModel)"})
  public void testAddMessageEventSubscriptions_givenArrayListAddAdhocSubProcess() {
    // Arrange
    EventSubscriptionManager eventSubscriptionManager = new EventSubscriptionManager();
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();

    ArrayList<FlowElement> flowElementList = new ArrayList<>();
    flowElementList.add(new AdhocSubProcess());
    Process process = mock(Process.class);
    when(process.getFlowElements()).thenReturn(flowElementList);

    // Act
    eventSubscriptionManager.addMessageEventSubscriptions(processDefinition, process, new BpmnModel());

    // Assert
    verify(process, atLeast(1)).getFlowElements();
  }

  /**
   * Test {@link EventSubscriptionManager#addMessageEventSubscriptions(ProcessDefinitionEntity, Process, BpmnModel)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionManager#addMessageEventSubscriptions(ProcessDefinitionEntity, Process, BpmnModel)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void EventSubscriptionManager.addMessageEventSubscriptions(ProcessDefinitionEntity, Process, BpmnModel)"})
  public void testAddMessageEventSubscriptions_thenThrowActivitiException() {
    // Arrange
    EventSubscriptionManager eventSubscriptionManager = new EventSubscriptionManager();
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();
    Process process = mock(Process.class);
    when(process.getFlowElements()).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> eventSubscriptionManager.addMessageEventSubscriptions(processDefinition, process, new BpmnModel()));
    verify(process).getFlowElements();
  }

  /**
   * Test {@link EventSubscriptionManager#insertMessageEvent(MessageEventDefinition, StartEvent, ProcessDefinitionEntity, BpmnModel)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventSubscriptionManager#insertMessageEvent(MessageEventDefinition, StartEvent, ProcessDefinitionEntity, BpmnModel)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void EventSubscriptionManager.insertMessageEvent(MessageEventDefinition, StartEvent, ProcessDefinitionEntity, BpmnModel)"})
  public void testInsertMessageEvent_thenThrowActivitiException() {
    // Arrange
    EventSubscriptionManager eventSubscriptionManager = new EventSubscriptionManager();
    MessageEventDefinition messageEventDefinition = new MessageEventDefinition();
    StartEvent startEvent = new StartEvent();
    ProcessDefinitionEntityImpl processDefinition = new ProcessDefinitionEntityImpl();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getMessage(Mockito.<String>any())).thenThrow(new ActivitiException("An error occurred"));
    when(bpmnModel.containsMessageId(Mockito.<String>any())).thenReturn(true);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> eventSubscriptionManager.insertMessageEvent(messageEventDefinition,
        startEvent, processDefinition, bpmnModel));
    verify(bpmnModel).containsMessageId(isNull());
    verify(bpmnModel).getMessage(isNull());
  }
}
