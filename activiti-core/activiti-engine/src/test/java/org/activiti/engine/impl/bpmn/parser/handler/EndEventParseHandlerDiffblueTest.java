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
import org.activiti.bpmn.model.CancelEventDefinition;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.TerminateEventDefinition;
import org.activiti.engine.impl.bpmn.behavior.CancelEndEventActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.NoneEndEventActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.TerminateEndEventActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.junit.Test;

public class EndEventParseHandlerDiffblueTest {
  /**
   * Test {@link EndEventParseHandler#executeParse(BpmnParse, EndEvent)} with
   * {@code BpmnParse}, {@code EndEvent}.
   * <p>
   * Method under test:
   * {@link EndEventParseHandler#executeParse(BpmnParse, EndEvent)}
   */
  @Test
  public void testExecuteParseWithBpmnParseEndEvent() {
    // Arrange
    EndEventParseHandler endEventParseHandler = new EndEventParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);
    EndEvent endEvent = new EndEvent();

    // Act
    endEventParseHandler.executeParse(bpmnParse, endEvent);

    // Assert
    assertTrue(endEvent.getBehavior() instanceof NoneEndEventActivityBehavior);
  }

  /**
   * Test {@link EndEventParseHandler#executeParse(BpmnParse, EndEvent)} with
   * {@code BpmnParse}, {@code EndEvent}.
   * <p>
   * Method under test:
   * {@link EndEventParseHandler#executeParse(BpmnParse, EndEvent)}
   */
  @Test
  public void testExecuteParseWithBpmnParseEndEvent2() {
    // Arrange
    EndEventParseHandler endEventParseHandler = new EndEventParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);

    EndEvent endEvent = new EndEvent();
    endEvent.addEventDefinition(new CancelEventDefinition());

    // Act
    endEventParseHandler.executeParse(bpmnParse, endEvent);

    // Assert
    assertTrue(endEvent.getBehavior() instanceof CancelEndEventActivityBehavior);
  }

  /**
   * Test {@link EndEventParseHandler#executeParse(BpmnParse, EndEvent)} with
   * {@code BpmnParse}, {@code EndEvent}.
   * <p>
   * Method under test:
   * {@link EndEventParseHandler#executeParse(BpmnParse, EndEvent)}
   */
  @Test
  public void testExecuteParseWithBpmnParseEndEvent3() {
    // Arrange
    EndEventParseHandler endEventParseHandler = new EndEventParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);

    EndEvent endEvent = new EndEvent();
    endEvent.addEventDefinition(new TerminateEventDefinition());

    // Act
    endEventParseHandler.executeParse(bpmnParse, endEvent);

    // Assert
    Object behavior = endEvent.getBehavior();
    assertTrue(behavior instanceof TerminateEndEventActivityBehavior);
    assertFalse(((TerminateEndEventActivityBehavior) behavior).isTerminateAll());
    assertFalse(((TerminateEndEventActivityBehavior) behavior).isTerminateMultiInstance());
  }

  /**
   * Test {@link EndEventParseHandler#executeParse(BpmnParse, EndEvent)} with
   * {@code BpmnParse}, {@code EndEvent}.
   * <p>
   * Method under test:
   * {@link EndEventParseHandler#executeParse(BpmnParse, EndEvent)}
   */
  @Test
  public void testExecuteParseWithBpmnParseEndEvent4() {
    // Arrange
    EndEventParseHandler endEventParseHandler = new EndEventParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);

    EndEvent endEvent = new EndEvent();
    endEvent.addEventDefinition(null);

    // Act
    endEventParseHandler.executeParse(bpmnParse, endEvent);

    // Assert
    assertTrue(endEvent.getBehavior() instanceof NoneEndEventActivityBehavior);
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link EndEventParseHandler}
   *   <li>{@link EndEventParseHandler#getHandledType()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends BaseElement> actualHandledType = (new EndEventParseHandler()).getHandledType();

    // Assert
    Class<EndEvent> expectedHandledType = EndEvent.class;
    assertEquals(expectedHandledType, actualHandledType);
  }
}
