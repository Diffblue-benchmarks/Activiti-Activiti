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
import static org.mockito.Mockito.mock;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.SendTask;
import org.activiti.engine.impl.bpmn.behavior.MailActivityBehavior;
import org.activiti.engine.impl.bpmn.helper.DefaultClassDelegateFactory;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.cfg.BpmnParseFactory;
import org.junit.Test;

public class SendTaskParseHandlerDiffblueTest {
  /**
   * Method under test:
   * {@link SendTaskParseHandler#executeParse(BpmnParse, SendTask)}
   */
  @Test
  public void testExecuteParse() {
    // Arrange
    SendTaskParseHandler sendTaskParseHandler = new SendTaskParseHandler();
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());
    SendTask sendTask = new SendTask();

    // Act
    sendTaskParseHandler.executeParse(bpmnParse, sendTask);

    // Assert that nothing has changed
    assertNull(sendTask.getBehavior());
  }

  /**
   * Method under test:
   * {@link SendTaskParseHandler#executeParse(BpmnParse, SendTask)}
   */
  @Test
  public void testExecuteParse2() {
    // Arrange
    SendTaskParseHandler sendTaskParseHandler = new SendTaskParseHandler();
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());

    SendTask sendTask = new SendTask();
    sendTask.setType("Send Task");
    sendTask.setOperationRef(null);

    // Act
    sendTaskParseHandler.executeParse(bpmnParse, sendTask);

    // Assert that nothing has changed
    assertNull(sendTask.getBehavior());
  }

  /**
   * Method under test:
   * {@link SendTaskParseHandler#executeParse(BpmnParse, SendTask)}
   */
  @Test
  public void testExecuteParse3() {
    // Arrange
    SendTaskParseHandler sendTaskParseHandler = new SendTaskParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setBpmnParseFactory(mock(BpmnParseFactory.class));
    BpmnParse bpmnParse = new BpmnParse(parser);
    SendTask sendTask = new SendTask();

    // Act
    sendTaskParseHandler.executeParse(bpmnParse, sendTask);

    // Assert that nothing has changed
    assertNull(sendTask.getBehavior());
  }

  /**
   * Method under test:
   * {@link SendTaskParseHandler#executeParse(BpmnParse, SendTask)}
   */
  @Test
  public void testExecuteParse4() {
    // Arrange
    SendTaskParseHandler sendTaskParseHandler = new SendTaskParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);

    SendTask sendTask = new SendTask();
    sendTask.setType("mail");
    sendTask.setOperationRef(null);

    // Act
    sendTaskParseHandler.executeParse(bpmnParse, sendTask);

    // Assert
    Object behavior = sendTask.getBehavior();
    assertTrue(behavior instanceof MailActivityBehavior);
    assertNull(((MailActivityBehavior) behavior).getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link SendTaskParseHandler#executeParse(BpmnParse, SendTask)}
   */
  @Test
  public void testExecuteParse5() {
    // Arrange
    SendTaskParseHandler sendTaskParseHandler = new SendTaskParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory(new DefaultClassDelegateFactory()));
    BpmnParse bpmnParse = new BpmnParse(parser);

    SendTask sendTask = new SendTask();
    sendTask.setType("mail");
    sendTask.setOperationRef(null);

    // Act
    sendTaskParseHandler.executeParse(bpmnParse, sendTask);

    // Assert
    Object behavior = sendTask.getBehavior();
    assertTrue(behavior instanceof MailActivityBehavior);
    assertNull(((MailActivityBehavior) behavior).getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link SendTaskParseHandler#executeParse(BpmnParse, SendTask)}
   */
  @Test
  public void testExecuteParse6() {
    // Arrange
    SendTaskParseHandler sendTaskParseHandler = new SendTaskParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);

    SendTask sendTask = new SendTask();
    sendTask.setType("");
    sendTask.setOperationRef(null);

    // Act
    sendTaskParseHandler.executeParse(bpmnParse, sendTask);

    // Assert that nothing has changed
    assertNull(sendTask.getBehavior());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link SendTaskParseHandler}
   *   <li>{@link SendTaskParseHandler#getHandledType()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends BaseElement> actualHandledType = (new SendTaskParseHandler()).getHandledType();

    // Assert
    Class<SendTask> expectedHandledType = SendTask.class;
    assertEquals(expectedHandledType, actualHandledType);
  }
}
