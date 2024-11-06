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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.List;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.CancelEventDefinition;
import org.activiti.bpmn.model.CompensateEventDefinition;
import org.activiti.bpmn.model.EventDefinition;
import org.activiti.bpmn.model.Message;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.ThrowEvent;
import org.activiti.engine.impl.bpmn.behavior.IntermediateThrowCompensationEventActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.IntermediateThrowMessageEventActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.IntermediateThrowNoneEventActivityBehavior;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultMessageExecutionContext;
import org.activiti.engine.impl.bpmn.parser.factory.MessageExecutionContext;
import org.activiti.engine.impl.delegate.BpmnMessagePayloadMappingProvider;
import org.activiti.engine.impl.delegate.DefaultThrowMessageJavaDelegate;
import org.junit.Test;

public class IntermediateThrowEventParseHandlerDiffblueTest {
  /**
   * Test
   * {@link IntermediateThrowEventParseHandler#executeParse(BpmnParse, ThrowEvent)}
   * with {@code BpmnParse}, {@code ThrowEvent}.
   * <p>
   * Method under test:
   * {@link IntermediateThrowEventParseHandler#executeParse(BpmnParse, ThrowEvent)}
   */
  @Test
  public void testExecuteParseWithBpmnParseThrowEvent() {
    // Arrange
    IntermediateThrowEventParseHandler intermediateThrowEventParseHandler = new IntermediateThrowEventParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);
    ThrowEvent intermediateEvent = new ThrowEvent();

    // Act
    intermediateThrowEventParseHandler.executeParse(bpmnParse, intermediateEvent);

    // Assert
    assertTrue(intermediateEvent.getBehavior() instanceof IntermediateThrowNoneEventActivityBehavior);
  }

  /**
   * Test
   * {@link IntermediateThrowEventParseHandler#executeParse(BpmnParse, ThrowEvent)}
   * with {@code BpmnParse}, {@code ThrowEvent}.
   * <p>
   * Method under test:
   * {@link IntermediateThrowEventParseHandler#executeParse(BpmnParse, ThrowEvent)}
   */
  @Test
  public void testExecuteParseWithBpmnParseThrowEvent2() {
    // Arrange
    IntermediateThrowEventParseHandler intermediateThrowEventParseHandler = new IntermediateThrowEventParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);

    ThrowEvent intermediateEvent = new ThrowEvent();
    intermediateEvent.addEventDefinition(new CompensateEventDefinition());

    // Act
    intermediateThrowEventParseHandler.executeParse(bpmnParse, intermediateEvent);

    // Assert
    assertTrue(intermediateEvent.getBehavior() instanceof IntermediateThrowCompensationEventActivityBehavior);
  }

  /**
   * Test
   * {@link IntermediateThrowEventParseHandler#executeParse(BpmnParse, ThrowEvent)}
   * with {@code BpmnParse}, {@code ThrowEvent}.
   * <ul>
   *   <li>Then {@link ThrowEvent} (default constructor) Behavior is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link IntermediateThrowEventParseHandler#executeParse(BpmnParse, ThrowEvent)}
   */
  @Test
  public void testExecuteParseWithBpmnParseThrowEvent_thenThrowEventBehaviorIsNull() {
    // Arrange
    IntermediateThrowEventParseHandler intermediateThrowEventParseHandler = new IntermediateThrowEventParseHandler();
    BpmnParse bpmnParse = new BpmnParse(new BpmnParser());

    ThrowEvent intermediateEvent = new ThrowEvent();
    intermediateEvent.addEventDefinition(new CancelEventDefinition());

    // Act
    intermediateThrowEventParseHandler.executeParse(bpmnParse, intermediateEvent);

    // Assert that nothing has changed
    assertNull(intermediateEvent.getBehavior());
  }

  /**
   * Test
   * {@link IntermediateThrowEventParseHandler#executeParse(BpmnParse, ThrowEvent)}
   * with {@code BpmnParse}, {@code ThrowEvent}.
   * <ul>
   *   <li>Then {@link ThrowEvent} (default constructor) EventDefinitions size is
   * one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link IntermediateThrowEventParseHandler#executeParse(BpmnParse, ThrowEvent)}
   */
  @Test
  public void testExecuteParseWithBpmnParseThrowEvent_thenThrowEventEventDefinitionsSizeIsOne() {
    // Arrange
    IntermediateThrowEventParseHandler intermediateThrowEventParseHandler = new IntermediateThrowEventParseHandler();

    BpmnParser parser = new BpmnParser();
    parser.setActivityBehaviorFactory(new DefaultActivityBehaviorFactory());

    BpmnModel bpmnModel = new BpmnModel();
    Message.Builder builderResult = Message.builder();
    Message.Builder attributesResult = builderResult.attributes(new HashMap<>());
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

    ThrowEvent intermediateEvent = new ThrowEvent();
    intermediateEvent.addEventDefinition(eventDefinition);

    // Act
    intermediateThrowEventParseHandler.executeParse(bpmnParse, intermediateEvent);

    // Assert
    List<EventDefinition> eventDefinitions = intermediateEvent.getEventDefinitions();
    assertEquals(1, eventDefinitions.size());
    EventDefinition getResult = eventDefinitions.get(0);
    assertTrue(getResult instanceof MessageEventDefinition);
    Object behavior = intermediateEvent.getBehavior();
    assertTrue(behavior instanceof IntermediateThrowMessageEventActivityBehavior);
    MessageExecutionContext messageExecutionContext = ((IntermediateThrowMessageEventActivityBehavior) behavior)
        .getMessageExecutionContext();
    assertTrue(messageExecutionContext instanceof DefaultMessageExecutionContext);
    assertTrue(((DefaultMessageExecutionContext) messageExecutionContext)
        .getMessagePayloadMappingProvider() instanceof BpmnMessagePayloadMappingProvider);
    assertTrue(((IntermediateThrowMessageEventActivityBehavior) behavior)
        .getDelegate() instanceof DefaultThrowMessageJavaDelegate);
    assertEquals("Name", ((MessageEventDefinition) getResult).getMessageRef());
    assertNull(((DefaultMessageExecutionContext) messageExecutionContext).getExpressionManager());
    assertSame(eventDefinition, ((IntermediateThrowMessageEventActivityBehavior) behavior).getMessageEventDefinition());
    assertSame(intermediateEvent, ((IntermediateThrowMessageEventActivityBehavior) behavior).getThrowEvent());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link IntermediateThrowEventParseHandler}
   *   <li>{@link IntermediateThrowEventParseHandler#getHandledType()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends BaseElement> actualHandledType = (new IntermediateThrowEventParseHandler()).getHandledType();

    // Assert
    Class<ThrowEvent> expectedHandledType = ThrowEvent.class;
    assertEquals(expectedHandledType, actualHandledType);
  }
}
