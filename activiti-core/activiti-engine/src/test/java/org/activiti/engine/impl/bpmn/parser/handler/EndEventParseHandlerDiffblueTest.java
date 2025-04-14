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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.List;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.CancelEventDefinition;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.EventDefinition;
import org.activiti.bpmn.model.Message;
import org.activiti.bpmn.model.Message.Builder;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.TerminateEventDefinition;
import org.activiti.engine.impl.bpmn.behavior.CancelEndEventActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.NoneEndEventActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.TerminateEndEventActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.ThrowMessageEndEventActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultMessageExecutionContext;
import org.activiti.engine.impl.bpmn.parser.factory.MessageExecutionContext;
import org.activiti.engine.impl.delegate.BpmnMessagePayloadMappingProvider;
import org.activiti.engine.impl.delegate.DefaultThrowMessageJavaDelegate;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class EndEventParseHandlerDiffblueTest {
  /**
   * Test {@link EndEventParseHandler#executeParse(BpmnParse, EndEvent)} with {@code BpmnParse}, {@code EndEvent}.
   * <p>
   * Method under test: {@link EndEventParseHandler#executeParse(BpmnParse, EndEvent)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void EndEventParseHandler.executeParse(BpmnParse, EndEvent)"})
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
   * Test {@link EndEventParseHandler#executeParse(BpmnParse, EndEvent)} with {@code BpmnParse}, {@code EndEvent}.
   * <p>
   * Method under test: {@link EndEventParseHandler#executeParse(BpmnParse, EndEvent)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void EndEventParseHandler.executeParse(BpmnParse, EndEvent)"})
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
   * Test {@link EndEventParseHandler#executeParse(BpmnParse, EndEvent)} with {@code BpmnParse}, {@code EndEvent}.
   * <p>
   * Method under test: {@link EndEventParseHandler#executeParse(BpmnParse, EndEvent)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void EndEventParseHandler.executeParse(BpmnParse, EndEvent)"})
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
   * Test {@link EndEventParseHandler#executeParse(BpmnParse, EndEvent)} with {@code BpmnParse}, {@code EndEvent}.
   * <p>
   * Method under test: {@link EndEventParseHandler#executeParse(BpmnParse, EndEvent)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void EndEventParseHandler.executeParse(BpmnParse, EndEvent)"})
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
   * Test {@link EndEventParseHandler#executeParse(BpmnParse, EndEvent)} with {@code BpmnParse}, {@code EndEvent}.
   * <ul>
   *   <li>Then {@link EndEvent} (default constructor) EventDefinitions size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link EndEventParseHandler#executeParse(BpmnParse, EndEvent)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void EndEventParseHandler.executeParse(BpmnParse, EndEvent)"})
  public void testExecuteParseWithBpmnParseEndEvent_thenEndEventEventDefinitionsSizeIsOne() {
    // Arrange
    EndEventParseHandler endEventParseHandler = new EndEventParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());

    BpmnModel bpmnModel = new BpmnModel();
    Builder builderResult = Message.builder();
    Builder attributesResult = builderResult.attributes(new HashMap<>());
    Message message = attributesResult.extensionElements(new HashMap<>())
        .id("42")
        .itemRef("Item Ref")
        .name("Name")
        .xmlColumnNumber(10)
        .xmlRowNumber(10)
        .build();
    bpmnModel.addMessage(message);

    BpmnParse bpmnParse = new BpmnParse(parser);
    bpmnParse.setBpmnModel(bpmnModel);

    MessageEventDefinition eventDefinition = new MessageEventDefinition();
    eventDefinition.setMessageRef("42");

    EndEvent endEvent = new EndEvent();
    endEvent.addEventDefinition(eventDefinition);

    // Act
    endEventParseHandler.executeParse(bpmnParse, endEvent);

    // Assert
    List<EventDefinition> eventDefinitions = endEvent.getEventDefinitions();
    assertEquals(1, eventDefinitions.size());
    EventDefinition getResult = eventDefinitions.get(0);
    assertTrue(getResult instanceof MessageEventDefinition);
    Object behavior = endEvent.getBehavior();
    assertTrue(behavior instanceof ThrowMessageEndEventActivityBehavior);
    MessageExecutionContext messageExecutionContext = ((ThrowMessageEndEventActivityBehavior) behavior)
        .getMessageExecutionContext();
    assertTrue(messageExecutionContext instanceof DefaultMessageExecutionContext);
    assertTrue(((DefaultMessageExecutionContext) messageExecutionContext)
        .getMessagePayloadMappingProvider() instanceof BpmnMessagePayloadMappingProvider);
    assertTrue(
        ((ThrowMessageEndEventActivityBehavior) behavior).getDelegate() instanceof DefaultThrowMessageJavaDelegate);
    assertEquals("Name", ((MessageEventDefinition) getResult).getMessageRef());
    assertNull(((DefaultMessageExecutionContext) messageExecutionContext).getExpressionManager());
    assertSame(endEvent, ((ThrowMessageEndEventActivityBehavior) behavior).getEndEvent());
    assertSame(eventDefinition, ((ThrowMessageEndEventActivityBehavior) behavior).getMessageEventDefinition());
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void EndEventParseHandler.<init>()", "Class EndEventParseHandler.getHandledType()"})
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends BaseElement> actualHandledType = (new EndEventParseHandler()).getHandledType();

    // Assert
    Class<EndEvent> expectedHandledType = EndEvent.class;
    assertEquals(expectedHandledType, actualHandledType);
  }
}
