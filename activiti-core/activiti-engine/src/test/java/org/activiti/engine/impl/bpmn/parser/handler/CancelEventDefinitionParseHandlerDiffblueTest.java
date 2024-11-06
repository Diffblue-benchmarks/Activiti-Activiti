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
import static org.junit.Assert.assertTrue;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.CancelEventDefinition;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.impl.bpmn.behavior.BoundaryCancelEventActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.junit.Test;

public class CancelEventDefinitionParseHandlerDiffblueTest {
  /**
   * Test
   * {@link CancelEventDefinitionParseHandler#executeParse(BpmnParse, CancelEventDefinition)}
   * with {@code BpmnParse}, {@code CancelEventDefinition}.
   * <p>
   * Method under test:
   * {@link CancelEventDefinitionParseHandler#executeParse(BpmnParse, CancelEventDefinition)}
   */
  @Test
  public void testExecuteParseWithBpmnParseCancelEventDefinition() {
    // Arrange
    CancelEventDefinitionParseHandler cancelEventDefinitionParseHandler = new CancelEventDefinitionParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());

    BpmnParse bpmnParse = new BpmnParse(parser);
    bpmnParse.setCurrentFlowElement(new BoundaryEvent());

    // Act
    cancelEventDefinitionParseHandler.executeParse(bpmnParse, new CancelEventDefinition());

    // Assert
    FlowElement currentFlowElement = bpmnParse.getCurrentFlowElement();
    assertTrue(currentFlowElement instanceof BoundaryEvent);
    Object behavior = ((BoundaryEvent) currentFlowElement).getBehavior();
    assertTrue(behavior instanceof BoundaryCancelEventActivityBehavior);
    assertFalse(((BoundaryCancelEventActivityBehavior) behavior).isInterrupting());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link CancelEventDefinitionParseHandler}
   *   <li>{@link CancelEventDefinitionParseHandler#getHandledType()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends BaseElement> actualHandledType = (new CancelEventDefinitionParseHandler()).getHandledType();

    // Assert
    Class<CancelEventDefinition> expectedHandledType = CancelEventDefinition.class;
    assertEquals(expectedHandledType, actualHandledType);
  }
}
