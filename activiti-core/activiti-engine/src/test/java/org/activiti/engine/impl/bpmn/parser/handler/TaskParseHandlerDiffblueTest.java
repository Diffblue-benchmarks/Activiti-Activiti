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
import org.activiti.bpmn.model.Task;
import org.activiti.engine.impl.bpmn.behavior.TaskActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.junit.Test;

public class TaskParseHandlerDiffblueTest {
  /**
   * Test {@link TaskParseHandler#executeParse(BpmnParse, Task)} with
   * {@code BpmnParse}, {@code Task}.
   * <ul>
   *   <li>Then {@link Task} (default constructor) Behavior
   * {@link TaskActivityBehavior}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TaskParseHandler#executeParse(BpmnParse, Task)}
   */
  @Test
  public void testExecuteParseWithBpmnParseTask_thenTaskBehaviorTaskActivityBehavior() {
    // Arrange
    TaskParseHandler taskParseHandler = new TaskParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);
    Task task = new Task();

    // Act
    taskParseHandler.executeParse(bpmnParse, task);

    // Assert
    Object behavior = task.getBehavior();
    assertTrue(behavior instanceof TaskActivityBehavior);
    assertNull(((TaskActivityBehavior) behavior).getMultiInstanceActivityBehavior());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link TaskParseHandler}
   *   <li>{@link TaskParseHandler#getHandledType()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends BaseElement> actualHandledType = (new TaskParseHandler()).getHandledType();

    // Assert
    Class<Task> expectedHandledType = Task.class;
    assertEquals(expectedHandledType, actualHandledType);
  }
}
