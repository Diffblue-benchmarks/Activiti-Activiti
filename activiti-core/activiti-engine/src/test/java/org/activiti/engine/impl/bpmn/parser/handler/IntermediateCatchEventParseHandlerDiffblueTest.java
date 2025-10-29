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
import static org.junit.Assert.assertTrue;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.CancelEventDefinition;
import org.activiti.bpmn.model.IntermediateCatchEvent;
import org.activiti.bpmn.model.TimerEventDefinition;
import org.activiti.engine.impl.bpmn.behavior.IntermediateCatchEventActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParseHandlers;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.junit.Test;

public class IntermediateCatchEventParseHandlerDiffblueTest {
  /**
   * Method under test:
   * {@link IntermediateCatchEventParseHandler#executeParse(BpmnParse, IntermediateCatchEvent)}
   */
  @Test
  public void testExecuteParse() {
    // Arrange
    IntermediateCatchEventParseHandler intermediateCatchEventParseHandler = new IntermediateCatchEventParseHandler();
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());

    IntermediateCatchEvent event = new IntermediateCatchEvent();
    event.addEventDefinition(new CancelEventDefinition());

    // Act
    intermediateCatchEventParseHandler.executeParse(bpmnParse, event);

    // Assert that nothing has changed
    assertNull(event.getBehavior());
  }

  /**
   * Method under test:
   * {@link IntermediateCatchEventParseHandler#executeParse(BpmnParse, IntermediateCatchEvent)}
   */
  @Test
  public void testExecuteParse2() {
    // Arrange
    IntermediateCatchEventParseHandler intermediateCatchEventParseHandler = new IntermediateCatchEventParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);
    IntermediateCatchEvent event = new IntermediateCatchEvent();

    // Act
    intermediateCatchEventParseHandler.executeParse(bpmnParse, event);

    // Assert
    Object behavior = event.getBehavior();
    assertTrue(behavior instanceof IntermediateCatchEventActivityBehavior);
    assertNull(((IntermediateCatchEventActivityBehavior) behavior).getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link IntermediateCatchEventParseHandler#executeParse(BpmnParse, IntermediateCatchEvent)}
   */
  @Test
  public void testExecuteParse3() {
    // Arrange
    IntermediateCatchEventParseHandler intermediateCatchEventParseHandler = new IntermediateCatchEventParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setBpmnParserHandlers(new BpmnParseHandlers());
    BpmnParse bpmnParse = new BpmnParse(parser);

    IntermediateCatchEvent event = new IntermediateCatchEvent();
    event.addEventDefinition(new TimerEventDefinition());

    // Act
    intermediateCatchEventParseHandler.executeParse(bpmnParse, event);

    // Assert that nothing has changed
    assertNull(event.getBehavior());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link IntermediateCatchEventParseHandler}
   *   <li>{@link IntermediateCatchEventParseHandler#getHandledType()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends BaseElement> actualHandledType = (new IntermediateCatchEventParseHandler()).getHandledType();

    // Assert
    Class<IntermediateCatchEvent> expectedHandledType = IntermediateCatchEvent.class;
    assertEquals(expectedHandledType, actualHandledType);
  }
}
