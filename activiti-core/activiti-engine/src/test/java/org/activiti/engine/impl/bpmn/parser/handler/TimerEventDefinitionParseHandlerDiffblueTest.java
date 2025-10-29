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
import static org.mockito.Mockito.mock;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.IntermediateCatchEvent;
import org.activiti.bpmn.model.TimerEventDefinition;
import org.activiti.engine.impl.bpmn.behavior.BoundaryTimerEventActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.IntermediateCatchTimerEventActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.cfg.BpmnParseFactory;
import org.junit.Test;

public class TimerEventDefinitionParseHandlerDiffblueTest {
  /**
   * Method under test:
   * {@link TimerEventDefinitionParseHandler#executeParse(BpmnParse, TimerEventDefinition)}
   */
  @Test
  public void testExecuteParse() {
    // Arrange
    TimerEventDefinitionParseHandler timerEventDefinitionParseHandler = new TimerEventDefinitionParseHandler();
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());

    // Act
    timerEventDefinitionParseHandler.executeParse(bpmnParse, new TimerEventDefinition());

    // Assert that nothing has changed
    assertNull(bpmnParse.getCurrentFlowElement());
  }

  /**
   * Method under test:
   * {@link TimerEventDefinitionParseHandler#executeParse(BpmnParse, TimerEventDefinition)}
   */
  @Test
  public void testExecuteParse2() {
    // Arrange
    TimerEventDefinitionParseHandler timerEventDefinitionParseHandler = new TimerEventDefinitionParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setBpmnParseFactory(mock(BpmnParseFactory.class));
    BpmnParse bpmnParse = new BpmnParse(parser);

    // Act
    timerEventDefinitionParseHandler.executeParse(bpmnParse, new TimerEventDefinition());

    // Assert that nothing has changed
    assertNull(bpmnParse.getCurrentFlowElement());
  }

  /**
   * Method under test:
   * {@link TimerEventDefinitionParseHandler#executeParse(BpmnParse, TimerEventDefinition)}
   */
  @Test
  public void testExecuteParse3() {
    // Arrange
    TimerEventDefinitionParseHandler timerEventDefinitionParseHandler = new TimerEventDefinitionParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());

    BpmnParse bpmnParse = new BpmnParse(parser);
    IntermediateCatchEvent currentFlowElement = new IntermediateCatchEvent();
    bpmnParse.setCurrentFlowElement(currentFlowElement);

    // Act
    timerEventDefinitionParseHandler.executeParse(bpmnParse, new TimerEventDefinition());

    // Assert
    FlowElement currentFlowElement2 = bpmnParse.getCurrentFlowElement();
    assertTrue(currentFlowElement2 instanceof IntermediateCatchEvent);
    Object behavior = ((IntermediateCatchEvent) currentFlowElement2).getBehavior();
    assertTrue(behavior instanceof IntermediateCatchTimerEventActivityBehavior);
    assertNull(((IntermediateCatchTimerEventActivityBehavior) behavior).getMultiInstanceActivityBehavior());
    assertSame(currentFlowElement, currentFlowElement2);
  }

  /**
   * Method under test:
   * {@link TimerEventDefinitionParseHandler#executeParse(BpmnParse, TimerEventDefinition)}
   */
  @Test
  public void testExecuteParse4() {
    // Arrange
    TimerEventDefinitionParseHandler timerEventDefinitionParseHandler = new TimerEventDefinitionParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());

    BpmnParse bpmnParse = new BpmnParse(parser);
    BoundaryEvent currentFlowElement = new BoundaryEvent();
    bpmnParse.setCurrentFlowElement(currentFlowElement);

    // Act
    timerEventDefinitionParseHandler.executeParse(bpmnParse, new TimerEventDefinition());

    // Assert
    FlowElement currentFlowElement2 = bpmnParse.getCurrentFlowElement();
    assertTrue(currentFlowElement2 instanceof BoundaryEvent);
    Object behavior = ((BoundaryEvent) currentFlowElement2).getBehavior();
    assertTrue(behavior instanceof BoundaryTimerEventActivityBehavior);
    assertTrue(((BoundaryTimerEventActivityBehavior) behavior).isInterrupting());
    assertSame(currentFlowElement, currentFlowElement2);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link TimerEventDefinitionParseHandler}
   *   <li>{@link TimerEventDefinitionParseHandler#getHandledType()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends BaseElement> actualHandledType = (new TimerEventDefinitionParseHandler()).getHandledType();

    // Assert
    Class<TimerEventDefinition> expectedHandledType = TimerEventDefinition.class;
    assertEquals(expectedHandledType, actualHandledType);
  }
}
