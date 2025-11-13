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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.CancelEventDefinition;
import org.activiti.bpmn.model.EventSubProcess;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.engine.impl.bpmn.behavior.NoneStartEventActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.test.util.TestProcessUtil;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class StartEventParseHandlerDiffblueTest {
  /**
   * Test {@link StartEventParseHandler#executeParse(BpmnParse, StartEvent)} with {@code BpmnParse},
   * {@code StartEvent}.
   *
   * <p>Method under test: {@link StartEventParseHandler#executeParse(BpmnParse, StartEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartEventParseHandler.executeParse(BpmnParse, StartEvent)"})
  public void testExecuteParseWithBpmnParseStartEvent() {
    // Arrange
    StartEventParseHandler startEventParseHandler = new StartEventParseHandler();

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setCurrentProcess(TestProcessUtil.createOneTaskProcessWithId("42"));

    StartEvent element = new StartEvent();
    element.addEventDefinition(new CancelEventDefinition());

    // Act
    startEventParseHandler.executeParse(bpmnParse, element);

    // Assert
    FlowElement initialFlowElement = bpmnParse.getCurrentProcess().getInitialFlowElement();
    assertTrue(initialFlowElement instanceof StartEvent);
    assertNull(((StartEvent) initialFlowElement).getBehavior());
    assertNull(initialFlowElement.getId());
    assertNull(initialFlowElement.getDocumentation());
    assertNull(initialFlowElement.getName());
    assertNull(((StartEvent) initialFlowElement).getFormKey());
    assertNull(((StartEvent) initialFlowElement).getInitiator());
    assertNull(initialFlowElement.getParentContainer());
    assertNull(initialFlowElement.getSubProcess());
    assertEquals(0, initialFlowElement.getXmlColumnNumber());
    assertEquals(0, initialFlowElement.getXmlRowNumber());
    assertEquals(1, ((StartEvent) initialFlowElement).getEventDefinitions().size());
    assertFalse(((StartEvent) initialFlowElement).isAsynchronous());
    assertFalse(((StartEvent) initialFlowElement).isNotExclusive());
    assertFalse(((StartEvent) initialFlowElement).isInterrupting());
    assertTrue(initialFlowElement.getExecutionListeners().isEmpty());
    assertTrue(((StartEvent) initialFlowElement).getIncomingFlows().isEmpty());
    assertTrue(((StartEvent) initialFlowElement).getOutgoingFlows().isEmpty());
    assertTrue(((StartEvent) initialFlowElement).getFormProperties().isEmpty());
    assertTrue(initialFlowElement.getAttributes().isEmpty());
    assertTrue(initialFlowElement.getExtensionElements().isEmpty());
    assertTrue(((StartEvent) initialFlowElement).isExclusive());
  }

  /**
   * Test {@link StartEventParseHandler#executeParse(BpmnParse, StartEvent)} with {@code BpmnParse},
   * {@code StartEvent}.
   *
   * <p>Method under test: {@link StartEventParseHandler#executeParse(BpmnParse, StartEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartEventParseHandler.executeParse(BpmnParse, StartEvent)"})
  public void testExecuteParseWithBpmnParseStartEvent2() {
    // Arrange
    StartEventParseHandler startEventParseHandler = new StartEventParseHandler();

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());

    StartEvent element = new StartEvent();
    element.setParentContainer(new AdhocSubProcess());

    // Act
    startEventParseHandler.executeParse(bpmnParse, element);

    // Assert
    assertTrue(element.getBehavior() instanceof NoneStartEventActivityBehavior);
  }

  /**
   * Test {@link StartEventParseHandler#executeParse(BpmnParse, StartEvent)} with {@code BpmnParse},
   * {@code StartEvent}.
   *
   * <p>Method under test: {@link StartEventParseHandler#executeParse(BpmnParse, StartEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartEventParseHandler.executeParse(BpmnParse, StartEvent)"})
  public void testExecuteParseWithBpmnParseStartEvent3() {
    // Arrange
    StartEventParseHandler startEventParseHandler = new StartEventParseHandler();

    Process currentProcess = TestProcessUtil.createOneTaskProcessWithId("42");
    AdhocSubProcess initialFlowElement = new AdhocSubProcess();
    currentProcess.setInitialFlowElement(initialFlowElement);

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setCurrentProcess(currentProcess);

    StartEvent element = new StartEvent();
    element.addEventDefinition(new CancelEventDefinition());

    // Act
    startEventParseHandler.executeParse(bpmnParse, element);

    // Assert that nothing has changed
    assertSame(initialFlowElement, bpmnParse.getCurrentProcess().getInitialFlowElement());
  }

  /**
   * Test {@link StartEventParseHandler#executeParse(BpmnParse, StartEvent)} with {@code BpmnParse},
   * {@code StartEvent}.
   *
   * <p>Method under test: {@link StartEventParseHandler#executeParse(BpmnParse, StartEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartEventParseHandler.executeParse(BpmnParse, StartEvent)"})
  public void testExecuteParseWithBpmnParseStartEvent4() {
    // Arrange
    StartEventParseHandler startEventParseHandler = new StartEventParseHandler();

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setCurrentProcessDefinition(new ProcessDefinitionEntityImpl());
    bpmnParse.setCurrentProcess(TestProcessUtil.createOneTaskProcessWithId("42"));

    StartEvent element = new StartEvent();
    element.setFormKey("Form Key");
    element.addEventDefinition(new CancelEventDefinition());

    // Act
    startEventParseHandler.executeParse(bpmnParse, element);

    // Assert
    FlowElement initialFlowElement = bpmnParse.getCurrentProcess().getInitialFlowElement();
    assertTrue(initialFlowElement instanceof StartEvent);
    ProcessDefinitionEntity currentProcessDefinition = bpmnParse.getCurrentProcessDefinition();
    assertTrue(currentProcessDefinition instanceof ProcessDefinitionEntityImpl);
    assertTrue(currentProcessDefinition.getHasStartFormKey());
    assertTrue(currentProcessDefinition.hasStartFormKey());
    assertSame(element, initialFlowElement);
  }

  /**
   * Test {@link StartEventParseHandler#executeParse(BpmnParse, StartEvent)} with {@code BpmnParse},
   * {@code StartEvent}.
   *
   * <p>Method under test: {@link StartEventParseHandler#executeParse(BpmnParse, StartEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartEventParseHandler.executeParse(BpmnParse, StartEvent)"})
  public void testExecuteParseWithBpmnParseStartEvent5() {
    // Arrange
    StartEventParseHandler startEventParseHandler = new StartEventParseHandler();

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setCurrentProcessDefinition(new ProcessDefinitionEntityImpl());
    bpmnParse.setCurrentProcess(TestProcessUtil.createOneTaskProcessWithId("42"));

    StartEvent element = new StartEvent();
    element.setFormKey("");
    element.addEventDefinition(new CancelEventDefinition());

    // Act
    startEventParseHandler.executeParse(bpmnParse, element);

    // Assert
    FlowElement initialFlowElement = bpmnParse.getCurrentProcess().getInitialFlowElement();
    assertTrue(initialFlowElement instanceof StartEvent);
    assertEquals("", ((StartEvent) initialFlowElement).getFormKey());
    assertNull(((StartEvent) initialFlowElement).getBehavior());
    assertNull(initialFlowElement.getId());
    assertNull(initialFlowElement.getDocumentation());
    assertNull(initialFlowElement.getName());
    assertNull(((StartEvent) initialFlowElement).getInitiator());
    assertNull(initialFlowElement.getParentContainer());
    assertNull(initialFlowElement.getSubProcess());
    assertEquals(0, initialFlowElement.getXmlColumnNumber());
    assertEquals(0, initialFlowElement.getXmlRowNumber());
    assertEquals(1, ((StartEvent) initialFlowElement).getEventDefinitions().size());
    assertFalse(((StartEvent) initialFlowElement).isAsynchronous());
    assertFalse(((StartEvent) initialFlowElement).isNotExclusive());
    assertFalse(((StartEvent) initialFlowElement).isInterrupting());
    assertTrue(initialFlowElement.getExecutionListeners().isEmpty());
    assertTrue(((StartEvent) initialFlowElement).getIncomingFlows().isEmpty());
    assertTrue(((StartEvent) initialFlowElement).getOutgoingFlows().isEmpty());
    assertTrue(((StartEvent) initialFlowElement).getFormProperties().isEmpty());
    assertTrue(initialFlowElement.getAttributes().isEmpty());
    assertTrue(initialFlowElement.getExtensionElements().isEmpty());
    assertTrue(((StartEvent) initialFlowElement).isExclusive());
  }

  /**
   * Test {@link StartEventParseHandler#executeParse(BpmnParse, StartEvent)} with {@code BpmnParse},
   * {@code StartEvent}.
   *
   * <ul>
   *   <li>Given {@link EventSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link StartEventParseHandler#executeParse(BpmnParse, StartEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartEventParseHandler.executeParse(BpmnParse, StartEvent)"})
  public void testExecuteParseWithBpmnParseStartEvent_givenEventSubProcess() {
    // Arrange
    StartEventParseHandler startEventParseHandler = new StartEventParseHandler();
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());

    StartEvent element = new StartEvent();
    element.setParentContainer(new EventSubProcess());
    element.addEventDefinition(new CancelEventDefinition());

    // Act
    startEventParseHandler.executeParse(bpmnParse, element);

    // Assert that nothing has changed
    assertNull(element.getBehavior());
  }

  /**
   * Test {@link StartEventParseHandler#executeParse(BpmnParse, StartEvent)} with {@code BpmnParse},
   * {@code StartEvent}.
   *
   * <ul>
   *   <li>Given {@link EventSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link StartEventParseHandler#executeParse(BpmnParse, StartEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartEventParseHandler.executeParse(BpmnParse, StartEvent)"})
  public void testExecuteParseWithBpmnParseStartEvent_givenEventSubProcess2() {
    // Arrange
    StartEventParseHandler startEventParseHandler = new StartEventParseHandler();

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());

    StartEvent element = new StartEvent();
    element.setParentContainer(new EventSubProcess());

    // Act
    startEventParseHandler.executeParse(bpmnParse, element);

    // Assert that nothing has changed
    assertNull(element.getBehavior());
  }

  /**
   * Test {@link StartEventParseHandler#executeParse(BpmnParse, StartEvent)} with {@code BpmnParse},
   * {@code StartEvent}.
   *
   * <ul>
   *   <li>Then {@link StartEvent} (default constructor) Behavior is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link StartEventParseHandler#executeParse(BpmnParse, StartEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartEventParseHandler.executeParse(BpmnParse, StartEvent)"})
  public void testExecuteParseWithBpmnParseStartEvent_thenStartEventBehaviorIsNull() {
    // Arrange
    StartEventParseHandler startEventParseHandler = new StartEventParseHandler();
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());

    StartEvent element = new StartEvent();
    element.setParentContainer(new AdhocSubProcess());
    element.addEventDefinition(new CancelEventDefinition());

    // Act
    startEventParseHandler.executeParse(bpmnParse, element);

    // Assert that nothing has changed
    assertNull(element.getBehavior());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link StartEventParseHandler}
   *   <li>{@link StartEventParseHandler#getHandledType()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void StartEventParseHandler.<init>()",
    "Class StartEventParseHandler.getHandledType()"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends BaseElement> actualHandledType = new StartEventParseHandler().getHandledType();

    // Assert
    Class<StartEvent> expectedHandledType = StartEvent.class;
    assertEquals(expectedHandledType, actualHandledType);
  }
}
