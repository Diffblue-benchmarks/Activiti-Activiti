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
import org.activiti.bpmn.model.ScriptTask;
import org.activiti.engine.impl.bpmn.behavior.ScriptTaskActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.junit.Test;

public class ScriptTaskParseHandlerDiffblueTest {
  /**
   * Test {@link ScriptTaskParseHandler#executeParse(BpmnParse, ScriptTask)} with
   * {@code BpmnParse}, {@code ScriptTask}.
   * <p>
   * Method under test:
   * {@link ScriptTaskParseHandler#executeParse(BpmnParse, ScriptTask)}
   */
  @Test
  public void testExecuteParseWithBpmnParseScriptTask() {
    // Arrange
    ScriptTaskParseHandler scriptTaskParseHandler = new ScriptTaskParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);
    ScriptTask scriptTask = new ScriptTask();

    // Act
    scriptTaskParseHandler.executeParse(bpmnParse, scriptTask);

    // Assert
    Object behavior = scriptTask.getBehavior();
    assertTrue(behavior instanceof ScriptTaskActivityBehavior);
    assertNull(((ScriptTaskActivityBehavior) behavior).getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link ScriptTaskParseHandler#executeParse(BpmnParse, ScriptTask)} with
   * {@code BpmnParse}, {@code ScriptTask}.
   * <p>
   * Method under test:
   * {@link ScriptTaskParseHandler#executeParse(BpmnParse, ScriptTask)}
   */
  @Test
  public void testExecuteParseWithBpmnParseScriptTask2() {
    // Arrange
    ScriptTaskParseHandler scriptTaskParseHandler = new ScriptTaskParseHandler();

    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    bpmnParse.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    ScriptTask scriptTask = new ScriptTask();

    // Act
    scriptTaskParseHandler.executeParse(bpmnParse, scriptTask);

    // Assert
    Object behavior = scriptTask.getBehavior();
    assertTrue(behavior instanceof ScriptTaskActivityBehavior);
    assertNull(((ScriptTaskActivityBehavior) behavior).getMultiInstanceActivityBehavior());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ScriptTaskParseHandler}
   *   <li>{@link ScriptTaskParseHandler#getHandledType()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends BaseElement> actualHandledType = (new ScriptTaskParseHandler()).getHandledType();

    // Assert
    Class<ScriptTask> expectedHandledType = ScriptTask.class;
    assertEquals(expectedHandledType, actualHandledType);
  }
}
