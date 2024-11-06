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
import static org.junit.Assert.assertTrue;
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
import org.junit.Test;

public class CompensateEventDefinitionParseHandlerDiffblueTest {
  /**
   * Test
   * {@link CompensateEventDefinitionParseHandler#executeParse(BpmnParse, CompensateEventDefinition)}
   * with {@code BpmnParse}, {@code CompensateEventDefinition}.
   * <p>
   * Method under test:
   * {@link CompensateEventDefinitionParseHandler#executeParse(BpmnParse, CompensateEventDefinition)}
   */
  @Test
  public void testExecuteParseWithBpmnParseCompensateEventDefinition() {
    // Arrange
    CompensateEventDefinitionParseHandler compensateEventDefinitionParseHandler = new CompensateEventDefinitionParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());

    BpmnParse bpmnParse = new BpmnParse(parser);
    bpmnParse.setCurrentFlowElement(new ThrowEvent());

    // Act
    compensateEventDefinitionParseHandler.executeParse(bpmnParse, new CompensateEventDefinition());

    // Assert
    FlowElement currentFlowElement = bpmnParse.getCurrentFlowElement();
    assertTrue(currentFlowElement instanceof ThrowEvent);
    assertTrue(
        ((ThrowEvent) currentFlowElement).getBehavior() instanceof IntermediateThrowCompensationEventActivityBehavior);
  }

  /**
   * Test
   * {@link CompensateEventDefinitionParseHandler#executeParse(BpmnParse, CompensateEventDefinition)}
   * with {@code BpmnParse}, {@code CompensateEventDefinition}.
   * <p>
   * Method under test:
   * {@link CompensateEventDefinitionParseHandler#executeParse(BpmnParse, CompensateEventDefinition)}
   */
  @Test
  public void testExecuteParseWithBpmnParseCompensateEventDefinition2() {
    // Arrange
    CompensateEventDefinitionParseHandler compensateEventDefinitionParseHandler = new CompensateEventDefinitionParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());

    BpmnParse bpmnParse = new BpmnParse(parser);
    bpmnParse.setCurrentFlowElement(new BoundaryEvent());

    // Act
    compensateEventDefinitionParseHandler.executeParse(bpmnParse, new CompensateEventDefinition());

    // Assert
    FlowElement currentFlowElement = bpmnParse.getCurrentFlowElement();
    assertTrue(currentFlowElement instanceof BoundaryEvent);
    Object behavior = ((BoundaryEvent) currentFlowElement).getBehavior();
    assertTrue(behavior instanceof BoundaryCompensateEventActivityBehavior);
    assertTrue(((BoundaryCompensateEventActivityBehavior) behavior).isInterrupting());
  }

  /**
   * Test getters and setters.
   * <p>
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
