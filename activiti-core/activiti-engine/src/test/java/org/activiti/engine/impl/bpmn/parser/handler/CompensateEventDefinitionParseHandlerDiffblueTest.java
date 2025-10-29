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
import org.activiti.bpmn.model.CompensateEventDefinition;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.ThrowEvent;
import org.activiti.engine.impl.bpmn.behavior.BoundaryCompensateEventActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.IntermediateThrowCompensationEventActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.cfg.BpmnParseFactory;
import org.junit.Test;

public class CompensateEventDefinitionParseHandlerDiffblueTest {
  /**
   * Method under test:
   * {@link CompensateEventDefinitionParseHandler#executeParse(BpmnParse, CompensateEventDefinition)}
   */
  @Test
  public void testExecuteParse() {
    // Arrange
    CompensateEventDefinitionParseHandler compensateEventDefinitionParseHandler = new CompensateEventDefinitionParseHandler();
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());

    // Act
    compensateEventDefinitionParseHandler.executeParse(bpmnParse, new CompensateEventDefinition());

    // Assert that nothing has changed
    assertNull(bpmnParse.getCurrentFlowElement());
  }

  /**
   * Method under test:
   * {@link CompensateEventDefinitionParseHandler#executeParse(BpmnParse, CompensateEventDefinition)}
   */
  @Test
  public void testExecuteParse2() {
    // Arrange
    CompensateEventDefinitionParseHandler compensateEventDefinitionParseHandler = new CompensateEventDefinitionParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setBpmnParseFactory(mock(BpmnParseFactory.class));
    BpmnParse bpmnParse = new BpmnParse(parser);

    // Act
    compensateEventDefinitionParseHandler.executeParse(bpmnParse, new CompensateEventDefinition());

    // Assert that nothing has changed
    assertNull(bpmnParse.getCurrentFlowElement());
  }

  /**
   * Method under test:
   * {@link CompensateEventDefinitionParseHandler#executeParse(BpmnParse, CompensateEventDefinition)}
   */
  @Test
  public void testExecuteParse3() {
    // Arrange
    CompensateEventDefinitionParseHandler compensateEventDefinitionParseHandler = new CompensateEventDefinitionParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());

    BpmnParse bpmnParse = new BpmnParse(parser);
    ThrowEvent currentFlowElement = new ThrowEvent();
    bpmnParse.setCurrentFlowElement(currentFlowElement);

    // Act
    compensateEventDefinitionParseHandler.executeParse(bpmnParse, new CompensateEventDefinition());

    // Assert
    FlowElement currentFlowElement2 = bpmnParse.getCurrentFlowElement();
    assertTrue(currentFlowElement2 instanceof ThrowEvent);
    assertTrue(
        ((ThrowEvent) currentFlowElement2).getBehavior() instanceof IntermediateThrowCompensationEventActivityBehavior);
    assertSame(currentFlowElement, currentFlowElement2);
  }

  /**
   * Method under test:
   * {@link CompensateEventDefinitionParseHandler#executeParse(BpmnParse, CompensateEventDefinition)}
   */
  @Test
  public void testExecuteParse4() {
    // Arrange
    CompensateEventDefinitionParseHandler compensateEventDefinitionParseHandler = new CompensateEventDefinitionParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());

    BpmnParse bpmnParse = new BpmnParse(parser);
    BoundaryEvent currentFlowElement = new BoundaryEvent();
    bpmnParse.setCurrentFlowElement(currentFlowElement);

    // Act
    compensateEventDefinitionParseHandler.executeParse(bpmnParse, new CompensateEventDefinition());

    // Assert
    FlowElement currentFlowElement2 = bpmnParse.getCurrentFlowElement();
    assertTrue(currentFlowElement2 instanceof BoundaryEvent);
    Object behavior = ((BoundaryEvent) currentFlowElement2).getBehavior();
    assertTrue(behavior instanceof BoundaryCompensateEventActivityBehavior);
    assertTrue(((BoundaryCompensateEventActivityBehavior) behavior).isInterrupting());
    assertSame(currentFlowElement, currentFlowElement2);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link CompensateEventDefinitionParseHandler}
   *   <li>{@link CompensateEventDefinitionParseHandler#getHandledType()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends BaseElement> actualHandledType = (new CompensateEventDefinitionParseHandler()).getHandledType();

    // Assert
    Class<CompensateEventDefinition> expectedHandledType = CompensateEventDefinition.class;
    assertEquals(expectedHandledType, actualHandledType);
  }
}
