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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.SendTask;
import org.activiti.engine.impl.bpmn.behavior.MailActivityBehavior;
import org.activiti.engine.impl.bpmn.helper.DefaultClassDelegateFactory;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class SendTaskParseHandlerDiffblueTest {
  /**
   * Test {@link SendTaskParseHandler#executeParse(BpmnParse, SendTask)} with {@code BpmnParse},
   * {@code SendTask}.
   *
   * <p>Method under test: {@link SendTaskParseHandler#executeParse(BpmnParse, SendTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SendTaskParseHandler.executeParse(BpmnParse, SendTask)"})
  public void testExecuteParseWithBpmnParseSendTask() {
    // Arrange
    SendTaskParseHandler sendTaskParseHandler = new SendTaskParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(
        new DefaultActivityBehaviorFactory(new DefaultClassDelegateFactory()));
    BpmnParse bpmnParse = new BpmnParse(parser);

    SendTask sendTask = new SendTask();
    sendTask.setType("mail");
    sendTask.setOperationRef("not empty");

    // Act
    sendTaskParseHandler.executeParse(bpmnParse, sendTask);

    // Assert
    Object behavior = sendTask.getBehavior();
    assertTrue(behavior instanceof MailActivityBehavior);
    assertNull(((MailActivityBehavior) behavior).getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link SendTaskParseHandler#executeParse(BpmnParse, SendTask)} with {@code BpmnParse},
   * {@code SendTask}.
   *
   * <ul>
   *   <li>Given {@link DefaultActivityBehaviorFactory#DefaultActivityBehaviorFactory()}.
   * </ul>
   *
   * <p>Method under test: {@link SendTaskParseHandler#executeParse(BpmnParse, SendTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SendTaskParseHandler.executeParse(BpmnParse, SendTask)"})
  public void testExecuteParseWithBpmnParseSendTask_givenDefaultActivityBehaviorFactory() {
    // Arrange
    SendTaskParseHandler sendTaskParseHandler = new SendTaskParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);

    SendTask sendTask = new SendTask();
    sendTask.setType("mail");
    sendTask.setOperationRef("not empty");

    // Act
    sendTaskParseHandler.executeParse(bpmnParse, sendTask);

    // Assert
    Object behavior = sendTask.getBehavior();
    assertTrue(behavior instanceof MailActivityBehavior);
    assertNull(((MailActivityBehavior) behavior).getMultiInstanceActivityBehavior());
  }

  /**
   * Test {@link SendTaskParseHandler#executeParse(BpmnParse, SendTask)} with {@code BpmnParse},
   * {@code SendTask}.
   *
   * <ul>
   *   <li>Given empty string.
   * </ul>
   *
   * <p>Method under test: {@link SendTaskParseHandler#executeParse(BpmnParse, SendTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SendTaskParseHandler.executeParse(BpmnParse, SendTask)"})
  public void testExecuteParseWithBpmnParseSendTask_givenEmptyString() {
    // Arrange
    SendTaskParseHandler sendTaskParseHandler = new SendTaskParseHandler();
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());

    SendTask sendTask = new SendTask();
    sendTask.setType("");
    sendTask.setOperationRef("not empty");

    // Act
    sendTaskParseHandler.executeParse(bpmnParse, sendTask);

    // Assert that nothing has changed
    assertNull(sendTask.getBehavior());
  }

  /**
   * Test {@link SendTaskParseHandler#executeParse(BpmnParse, SendTask)} with {@code BpmnParse},
   * {@code SendTask}.
   *
   * <ul>
   *   <li>When {@link SendTask} (default constructor) Type is {@code not empty}.
   * </ul>
   *
   * <p>Method under test: {@link SendTaskParseHandler#executeParse(BpmnParse, SendTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SendTaskParseHandler.executeParse(BpmnParse, SendTask)"})
  public void testExecuteParseWithBpmnParseSendTask_whenSendTaskTypeIsNotEmpty() {
    // Arrange
    SendTaskParseHandler sendTaskParseHandler = new SendTaskParseHandler();
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());

    SendTask sendTask = new SendTask();
    sendTask.setType("not empty");
    sendTask.setOperationRef("not empty");

    // Act
    sendTaskParseHandler.executeParse(bpmnParse, sendTask);

    // Assert that nothing has changed
    assertNull(sendTask.getBehavior());
  }

  /**
   * Test {@link SendTaskParseHandler#executeParse(BpmnParse, SendTask)} with {@code BpmnParse},
   * {@code SendTask}.
   *
   * <ul>
   *   <li>When {@link SendTask} (default constructor).
   *   <li>Then {@link SendTask} (default constructor) Behavior is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link SendTaskParseHandler#executeParse(BpmnParse, SendTask)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SendTaskParseHandler.executeParse(BpmnParse, SendTask)"})
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
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link SendTaskParseHandler}
   *   <li>{@link SendTaskParseHandler#getHandledType()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SendTaskParseHandler.<init>()",
    "Class SendTaskParseHandler.getHandledType()"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends BaseElement> actualHandledType = new SendTaskParseHandler().getHandledType();

    // Assert
    Class<SendTask> expectedHandledType = SendTask.class;
    assertEquals(expectedHandledType, actualHandledType);
  }
}
