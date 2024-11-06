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
import org.activiti.bpmn.model.ReceiveTask;
import org.activiti.engine.impl.bpmn.behavior.ReceiveTaskActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.junit.Test;

public class ReceiveTaskParseHandlerDiffblueTest {
  /**
   * Test {@link ReceiveTaskParseHandler#executeParse(BpmnParse, ReceiveTask)}
   * with {@code BpmnParse}, {@code ReceiveTask}.
   * <p>
   * Method under test:
   * {@link ReceiveTaskParseHandler#executeParse(BpmnParse, ReceiveTask)}
   */
  @Test
  public void testExecuteParseWithBpmnParseReceiveTask() {
    // Arrange
    ReceiveTaskParseHandler receiveTaskParseHandler = new ReceiveTaskParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);
    ReceiveTask receiveTask = new ReceiveTask();

    // Act
    receiveTaskParseHandler.executeParse(bpmnParse, receiveTask);

    // Assert
    Object behavior = receiveTask.getBehavior();
    assertTrue(behavior instanceof ReceiveTaskActivityBehavior);
    assertNull(((ReceiveTaskActivityBehavior) behavior).getMultiInstanceActivityBehavior());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ReceiveTaskParseHandler}
   *   <li>{@link ReceiveTaskParseHandler#getHandledType()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends BaseElement> actualHandledType = (new ReceiveTaskParseHandler()).getHandledType();

    // Assert
    Class<ReceiveTask> expectedHandledType = ReceiveTask.class;
    assertEquals(expectedHandledType, actualHandledType);
  }
}
