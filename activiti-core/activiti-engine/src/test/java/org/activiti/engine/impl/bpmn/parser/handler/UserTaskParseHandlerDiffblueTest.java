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
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.junit.Test;

public class UserTaskParseHandlerDiffblueTest {
  /**
   * Test {@link UserTaskParseHandler#executeParse(BpmnParse, UserTask)} with
   * {@code BpmnParse}, {@code UserTask}.
   * <p>
   * Method under test:
   * {@link UserTaskParseHandler#executeParse(BpmnParse, UserTask)}
   */
  @Test
  public void testExecuteParseWithBpmnParseUserTask() {
    // Arrange
    UserTaskParseHandler userTaskParseHandler = new UserTaskParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);
    UserTask userTask = new UserTask();

    // Act
    userTaskParseHandler.executeParse(bpmnParse, userTask);

    // Assert
    Object behavior = userTask.getBehavior();
    assertTrue(behavior instanceof UserTaskActivityBehavior);
    assertNull(((UserTaskActivityBehavior) behavior).getMultiInstanceActivityBehavior());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link UserTaskParseHandler}
   *   <li>{@link UserTaskParseHandler#getHandledType()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends BaseElement> actualHandledType = (new UserTaskParseHandler()).getHandledType();

    // Assert
    Class<UserTask> expectedHandledType = UserTask.class;
    assertEquals(expectedHandledType, actualHandledType);
  }
}
