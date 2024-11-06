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
import org.activiti.bpmn.model.CallActivity;
import org.activiti.engine.impl.bpmn.behavior.CallActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.junit.Test;

public class CallActivityParseHandlerDiffblueTest {
  /**
   * Test {@link CallActivityParseHandler#executeParse(BpmnParse, CallActivity)}
   * with {@code BpmnParse}, {@code CallActivity}.
   * <p>
   * Method under test:
   * {@link CallActivityParseHandler#executeParse(BpmnParse, CallActivity)}
   */
  @Test
  public void testExecuteParseWithBpmnParseCallActivity() {
    // Arrange
    CallActivityParseHandler callActivityParseHandler = new CallActivityParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);
    CallActivity callActivity = new CallActivity();

    // Act
    callActivityParseHandler.executeParse(bpmnParse, callActivity);

    // Assert
    Object behavior = callActivity.getBehavior();
    assertTrue(behavior instanceof CallActivityBehavior);
    assertNull(((CallActivityBehavior) behavior).getProcessDefinitionKey());
    assertNull(((CallActivityBehavior) behavior).getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link CallActivityParseHandler#executeParse(BpmnParse, CallActivity)}
   * with {@code BpmnParse}, {@code CallActivity}.
   * <p>
   * Method under test:
   * {@link CallActivityParseHandler#executeParse(BpmnParse, CallActivity)}
   */
  @Test
  public void testExecuteParseWithBpmnParseCallActivity2() {
    // Arrange
    CallActivityParseHandler callActivityParseHandler = new CallActivityParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);

    CallActivity callActivity = new CallActivity();
    callActivity.setCalledElement("\\$+\\{+.+\\}");

    // Act
    callActivityParseHandler.executeParse(bpmnParse, callActivity);

    // Assert
    Object behavior = callActivity.getBehavior();
    assertTrue(behavior instanceof CallActivityBehavior);
    assertEquals("\\$+\\{+.+\\}", ((CallActivityBehavior) behavior).getProcessDefinitionKey());
    assertNull(((CallActivityBehavior) behavior).getMultiInstanceActivityBehavior());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link CallActivityParseHandler}
   *   <li>{@link CallActivityParseHandler#getHandledType()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends BaseElement> actualHandledType = (new CallActivityParseHandler()).getHandledType();

    // Assert
    Class<CallActivity> expectedHandledType = CallActivity.class;
    assertEquals(expectedHandledType, actualHandledType);
  }
}
