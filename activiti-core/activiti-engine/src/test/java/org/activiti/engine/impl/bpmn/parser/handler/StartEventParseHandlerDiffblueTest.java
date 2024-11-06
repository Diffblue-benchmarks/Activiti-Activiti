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
package org.activiti.engine.impl.bpmn.parser.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.CancelEventDefinition;
import org.activiti.bpmn.model.ErrorEventDefinition;
import org.activiti.bpmn.model.EventSubProcess;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.engine.impl.bpmn.behavior.EventSubProcessErrorStartEventActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.junit.Test;

public class StartEventParseHandlerDiffblueTest {
  /**
   * Test {@link StartEventParseHandler#executeParse(BpmnParse, StartEvent)} with
   * {@code BpmnParse}, {@code StartEvent}.
   * <p>
   * Method under test:
   * {@link StartEventParseHandler#executeParse(BpmnParse, StartEvent)}
   */
  @Test
  public void testExecuteParseWithBpmnParseStartEvent() {
    // Arrange
    StartEventParseHandler startEventParseHandler = new StartEventParseHandler();

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setCurrentProcess(new Process());

    StartEvent element = new StartEvent();
    element.addEventDefinition(new CancelEventDefinition());

    // Act
    startEventParseHandler.executeParse(bpmnParse, element);

    // Assert
    assertSame(element, bpmnParse.getCurrentProcess().getInitialFlowElement());
  }

  /**
   * Test {@link StartEventParseHandler#executeParse(BpmnParse, StartEvent)} with
   * {@code BpmnParse}, {@code StartEvent}.
   * <p>
   * Method under test:
   * {@link StartEventParseHandler#executeParse(BpmnParse, StartEvent)}
   */
  @Test
  public void testExecuteParseWithBpmnParseStartEvent2() {
    // Arrange
    StartEventParseHandler startEventParseHandler = new StartEventParseHandler();

    Process currentProcess = new Process();
    AdhocSubProcess initialFlowElement = new AdhocSubProcess();
    currentProcess.setInitialFlowElement(initialFlowElement);

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setCurrentProcess(currentProcess);

    StartEvent element = new StartEvent();
    element.addEventDefinition(new CancelEventDefinition());

    // Act
    startEventParseHandler.executeParse(bpmnParse, element);

    // Assert that nothing has changed
    assertEquals(1, element.getEventDefinitions().size());
    assertTrue(element.getExecutionListeners().isEmpty());
    assertTrue(element.getIncomingFlows().isEmpty());
    assertTrue(element.getOutgoingFlows().isEmpty());
    assertTrue(element.getFormProperties().isEmpty());
    assertTrue(element.getAttributes().isEmpty());
    assertTrue(element.getExtensionElements().isEmpty());
    assertSame(initialFlowElement, bpmnParse.getCurrentProcess().getInitialFlowElement());
  }

  /**
   * Test {@link StartEventParseHandler#executeParse(BpmnParse, StartEvent)} with
   * {@code BpmnParse}, {@code StartEvent}.
   * <p>
   * Method under test:
   * {@link StartEventParseHandler#executeParse(BpmnParse, StartEvent)}
   */
  @Test
  public void testExecuteParseWithBpmnParseStartEvent3() {
    // Arrange
    StartEventParseHandler startEventParseHandler = new StartEventParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);

    StartEvent element = new StartEvent();
    element.setParentContainer(new EventSubProcess());
    element.addEventDefinition(new ErrorEventDefinition());

    // Act
    startEventParseHandler.executeParse(bpmnParse, element);

    // Assert
    Object behavior = element.getBehavior();
    assertTrue(behavior instanceof EventSubProcessErrorStartEventActivityBehavior);
    assertNull(((EventSubProcessErrorStartEventActivityBehavior) behavior).getMultiInstanceActivityBehavior());
    assertEquals(1, element.getEventDefinitions().size());
    assertTrue(element.getExecutionListeners().isEmpty());
    assertTrue(element.getIncomingFlows().isEmpty());
    assertTrue(element.getOutgoingFlows().isEmpty());
    assertTrue(element.getFormProperties().isEmpty());
    assertTrue(element.getAttributes().isEmpty());
    assertTrue(element.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link StartEventParseHandler#executeParse(BpmnParse, StartEvent)} with
   * {@code BpmnParse}, {@code StartEvent}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartEventParseHandler#executeParse(BpmnParse, StartEvent)}
   */
  @Test
  public void testExecuteParseWithBpmnParseStartEvent_givenAdhocSubProcess() {
    // Arrange
    StartEventParseHandler startEventParseHandler = new StartEventParseHandler();
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());

    StartEvent element = new StartEvent();
    element.setParentContainer(new AdhocSubProcess());
    element.addEventDefinition(new CancelEventDefinition());

    // Act
    startEventParseHandler.executeParse(bpmnParse, element);

    // Assert that nothing has changed
    assertEquals(1, element.getEventDefinitions().size());
    assertTrue(element.getExecutionListeners().isEmpty());
    assertTrue(element.getIncomingFlows().isEmpty());
    assertTrue(element.getOutgoingFlows().isEmpty());
    assertTrue(element.getFormProperties().isEmpty());
    assertTrue(element.getAttributes().isEmpty());
    assertTrue(element.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link StartEventParseHandler#executeParse(BpmnParse, StartEvent)} with
   * {@code BpmnParse}, {@code StartEvent}.
   * <ul>
   *   <li>Then {@link StartEvent} (default constructor) EventDefinitions size is
   * one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartEventParseHandler#executeParse(BpmnParse, StartEvent)}
   */
  @Test
  public void testExecuteParseWithBpmnParseStartEvent_thenStartEventEventDefinitionsSizeIsOne() {
    // Arrange
    StartEventParseHandler startEventParseHandler = new StartEventParseHandler();
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());

    StartEvent element = new StartEvent();
    element.setParentContainer(new EventSubProcess());
    element.addEventDefinition(new CancelEventDefinition());

    // Act
    startEventParseHandler.executeParse(bpmnParse, element);

    // Assert that nothing has changed
    assertEquals(1, element.getEventDefinitions().size());
    assertTrue(element.getExecutionListeners().isEmpty());
    assertTrue(element.getIncomingFlows().isEmpty());
    assertTrue(element.getOutgoingFlows().isEmpty());
    assertTrue(element.getFormProperties().isEmpty());
    assertTrue(element.getAttributes().isEmpty());
    assertTrue(element.getExtensionElements().isEmpty());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link StartEventParseHandler}
   *   <li>{@link StartEventParseHandler#getHandledType()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends BaseElement> actualHandledType = (new StartEventParseHandler()).getHandledType();

    // Assert
    Class<StartEvent> expectedHandledType = StartEvent.class;
    assertEquals(expectedHandledType, actualHandledType);
  }
}
