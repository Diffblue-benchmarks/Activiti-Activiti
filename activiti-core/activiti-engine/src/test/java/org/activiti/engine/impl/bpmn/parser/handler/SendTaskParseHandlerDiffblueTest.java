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
   * Test {@link SendTaskParseHandler#executeParse(BpmnParse, SendTask)} with
   * {@code BpmnParse}, {@code SendTask}.
   * <p>
   * Method under test:
   * {@link SendTaskParseHandler#executeParse(BpmnParse, SendTask)}
   */
  @Test
  public void testExecuteParseWithBpmnParseSendTask() {
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
   * Test {@link SendTaskParseHandler#executeParse(BpmnParse, SendTask)} with
   * {@code BpmnParse}, {@code SendTask}.
   * <ul>
   *   <li>Given {@link BpmnParseFactory}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SendTaskParseHandler#executeParse(BpmnParse, SendTask)}
   */
  @Test
  public void testExecuteParseWithBpmnParseSendTask_givenBpmnParseFactory() {
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
   * Test {@link SendTaskParseHandler#executeParse(BpmnParse, SendTask)} with
   * {@code BpmnParse}, {@code SendTask}.
   * <ul>
   *   <li>Given empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SendTaskParseHandler#executeParse(BpmnParse, SendTask)}
   */
  @Test
  public void testExecuteParseWithBpmnParseSendTask_givenEmptyString() {
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
   * Test {@link SendTaskParseHandler#executeParse(BpmnParse, SendTask)} with
   * {@code BpmnParse}, {@code SendTask}.
   * <ul>
   *   <li>Given {@code Send Task}.</li>
   *   <li>When {@link SendTask} (default constructor) Type is
   * {@code Send Task}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SendTaskParseHandler#executeParse(BpmnParse, SendTask)}
   */
  @Test
  public void testExecuteParseWithBpmnParseSendTask_givenSendTask_whenSendTaskTypeIsSendTask() {
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
   * Test {@link SendTaskParseHandler#executeParse(BpmnParse, SendTask)} with
   * {@code BpmnParse}, {@code SendTask}.
   * <ul>
   *   <li>Then {@link SendTask} (default constructor) Behavior
   * {@link MailActivityBehavior}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SendTaskParseHandler#executeParse(BpmnParse, SendTask)}
   */
  @Test
  public void testExecuteParseWithBpmnParseSendTask_thenSendTaskBehaviorMailActivityBehavior() {
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
   * Test {@link SendTaskParseHandler#executeParse(BpmnParse, SendTask)} with
   * {@code BpmnParse}, {@code SendTask}.
   * <ul>
   *   <li>When {@link SendTask} (default constructor).</li>
   *   <li>Then {@link SendTask} (default constructor) Behavior is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SendTaskParseHandler#executeParse(BpmnParse, SendTask)}
   */
  @Test
  public void testExecuteParseWithBpmnParseSendTask_whenSendTask_thenSendTaskBehaviorIsNull() {
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
   * Test getters and setters.
   * <p>
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
