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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.CancelEventDefinition;
import org.activiti.bpmn.model.CompensateEventDefinition;
import org.activiti.bpmn.model.ErrorEventDefinition;
import org.activiti.bpmn.model.EventDefinition;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.SignalEventDefinition;
import org.activiti.bpmn.model.TimerEventDefinition;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParseHandlers;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultListenerFactory;
import org.junit.Test;
import org.mockito.Mockito;

public class BoundaryEventParseHandlerDiffblueTest {
  /**
   * Test {@link BoundaryEventParseHandler#executeParse(BpmnParse, BoundaryEvent)}
   * with {@code BpmnParse}, {@code BoundaryEvent}.
   * <p>
   * Method under test:
   * {@link BoundaryEventParseHandler#executeParse(BpmnParse, BoundaryEvent)}
   */
  @Test
  public void testExecuteParseWithBpmnParseBoundaryEvent() {
    // Arrange
    BoundaryEventParseHandler boundaryEventParseHandler = new BoundaryEventParseHandler();
    BpmnParseHandlers bpmnParseHandlers = mock(BpmnParseHandlers.class);
    doNothing().when(bpmnParseHandlers).parseElement(Mockito.<BpmnParse>any(), Mockito.<BaseElement>any());
    BpmnParser parser = mock(BpmnParser.class);
    when(parser.getBpmnParserHandlers()).thenReturn(bpmnParseHandlers);
    when(parser.getActivityBehaviorFactory()).thenReturn(new DefaultActivityBehaviorFactory());
    when(parser.getListenerFactory()).thenReturn(new DefaultListenerFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);

    ArrayList<EventDefinition> eventDefinitionList = new ArrayList<>();
    eventDefinitionList.add(new CancelEventDefinition());
    BoundaryEvent boundaryEvent = mock(BoundaryEvent.class);
    when(boundaryEvent.getEventDefinitions()).thenReturn(eventDefinitionList);
    when(boundaryEvent.getAttachedToRef()).thenReturn(new AdhocSubProcess());

    // Act
    boundaryEventParseHandler.executeParse(bpmnParse, boundaryEvent);

    // Assert that nothing has changed
    verify(boundaryEvent).getAttachedToRef();
    verify(boundaryEvent, atLeast(1)).getEventDefinitions();
    verify(bpmnParseHandlers).parseElement(isA(BpmnParse.class), isA(BaseElement.class));
    verify(parser).getActivityBehaviorFactory();
    verify(parser).getBpmnParserHandlers();
    verify(parser).getListenerFactory();
  }

  /**
   * Test {@link BoundaryEventParseHandler#executeParse(BpmnParse, BoundaryEvent)}
   * with {@code BpmnParse}, {@code BoundaryEvent}.
   * <p>
   * Method under test:
   * {@link BoundaryEventParseHandler#executeParse(BpmnParse, BoundaryEvent)}
   */
  @Test
  public void testExecuteParseWithBpmnParseBoundaryEvent2() {
    // Arrange
    BoundaryEventParseHandler boundaryEventParseHandler = new BoundaryEventParseHandler();
    BpmnParseHandlers bpmnParseHandlers = mock(BpmnParseHandlers.class);
    doNothing().when(bpmnParseHandlers).parseElement(Mockito.<BpmnParse>any(), Mockito.<BaseElement>any());
    BpmnParser parser = mock(BpmnParser.class);
    when(parser.getBpmnParserHandlers()).thenReturn(bpmnParseHandlers);
    when(parser.getActivityBehaviorFactory()).thenReturn(new DefaultActivityBehaviorFactory());
    when(parser.getListenerFactory()).thenReturn(new DefaultListenerFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);

    ArrayList<EventDefinition> eventDefinitionList = new ArrayList<>();
    eventDefinitionList.add(new SignalEventDefinition());
    BoundaryEvent boundaryEvent = mock(BoundaryEvent.class);
    when(boundaryEvent.getEventDefinitions()).thenReturn(eventDefinitionList);
    when(boundaryEvent.getAttachedToRef()).thenReturn(new AdhocSubProcess());

    // Act
    boundaryEventParseHandler.executeParse(bpmnParse, boundaryEvent);

    // Assert that nothing has changed
    verify(boundaryEvent).getAttachedToRef();
    verify(boundaryEvent, atLeast(1)).getEventDefinitions();
    verify(bpmnParseHandlers).parseElement(isA(BpmnParse.class), isA(BaseElement.class));
    verify(parser).getActivityBehaviorFactory();
    verify(parser).getBpmnParserHandlers();
    verify(parser).getListenerFactory();
  }

  /**
   * Test {@link BoundaryEventParseHandler#executeParse(BpmnParse, BoundaryEvent)}
   * with {@code BpmnParse}, {@code BoundaryEvent}.
   * <p>
   * Method under test:
   * {@link BoundaryEventParseHandler#executeParse(BpmnParse, BoundaryEvent)}
   */
  @Test
  public void testExecuteParseWithBpmnParseBoundaryEvent3() {
    // Arrange
    BoundaryEventParseHandler boundaryEventParseHandler = new BoundaryEventParseHandler();
    BpmnParseHandlers bpmnParseHandlers = mock(BpmnParseHandlers.class);
    doNothing().when(bpmnParseHandlers).parseElement(Mockito.<BpmnParse>any(), Mockito.<BaseElement>any());
    BpmnParser parser = mock(BpmnParser.class);
    when(parser.getBpmnParserHandlers()).thenReturn(bpmnParseHandlers);
    when(parser.getActivityBehaviorFactory()).thenReturn(new DefaultActivityBehaviorFactory());
    when(parser.getListenerFactory()).thenReturn(new DefaultListenerFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);

    ArrayList<EventDefinition> eventDefinitionList = new ArrayList<>();
    eventDefinitionList.add(new CompensateEventDefinition());
    BoundaryEvent boundaryEvent = mock(BoundaryEvent.class);
    when(boundaryEvent.getEventDefinitions()).thenReturn(eventDefinitionList);
    when(boundaryEvent.getAttachedToRef()).thenReturn(new AdhocSubProcess());

    // Act
    boundaryEventParseHandler.executeParse(bpmnParse, boundaryEvent);

    // Assert that nothing has changed
    verify(boundaryEvent).getAttachedToRef();
    verify(boundaryEvent, atLeast(1)).getEventDefinitions();
    verify(bpmnParseHandlers).parseElement(isA(BpmnParse.class), isA(BaseElement.class));
    verify(parser).getActivityBehaviorFactory();
    verify(parser).getBpmnParserHandlers();
    verify(parser).getListenerFactory();
  }

  /**
   * Test {@link BoundaryEventParseHandler#executeParse(BpmnParse, BoundaryEvent)}
   * with {@code BpmnParse}, {@code BoundaryEvent}.
   * <p>
   * Method under test:
   * {@link BoundaryEventParseHandler#executeParse(BpmnParse, BoundaryEvent)}
   */
  @Test
  public void testExecuteParseWithBpmnParseBoundaryEvent4() {
    // Arrange
    BoundaryEventParseHandler boundaryEventParseHandler = new BoundaryEventParseHandler();
    BpmnParseHandlers bpmnParseHandlers = mock(BpmnParseHandlers.class);
    doNothing().when(bpmnParseHandlers).parseElement(Mockito.<BpmnParse>any(), Mockito.<BaseElement>any());
    BpmnParser parser = mock(BpmnParser.class);
    when(parser.getBpmnParserHandlers()).thenReturn(bpmnParseHandlers);
    when(parser.getActivityBehaviorFactory()).thenReturn(new DefaultActivityBehaviorFactory());
    when(parser.getListenerFactory()).thenReturn(new DefaultListenerFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);

    ArrayList<EventDefinition> eventDefinitionList = new ArrayList<>();
    eventDefinitionList.add(new MessageEventDefinition());
    BoundaryEvent boundaryEvent = mock(BoundaryEvent.class);
    when(boundaryEvent.getEventDefinitions()).thenReturn(eventDefinitionList);
    when(boundaryEvent.getAttachedToRef()).thenReturn(new AdhocSubProcess());

    // Act
    boundaryEventParseHandler.executeParse(bpmnParse, boundaryEvent);

    // Assert that nothing has changed
    verify(boundaryEvent).getAttachedToRef();
    verify(boundaryEvent, atLeast(1)).getEventDefinitions();
    verify(bpmnParseHandlers).parseElement(isA(BpmnParse.class), isA(BaseElement.class));
    verify(parser).getActivityBehaviorFactory();
    verify(parser).getBpmnParserHandlers();
    verify(parser).getListenerFactory();
  }

  /**
   * Test {@link BoundaryEventParseHandler#executeParse(BpmnParse, BoundaryEvent)}
   * with {@code BpmnParse}, {@code BoundaryEvent}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ErrorEventDefinition}
   * (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BoundaryEventParseHandler#executeParse(BpmnParse, BoundaryEvent)}
   */
  @Test
  public void testExecuteParseWithBpmnParseBoundaryEvent_givenArrayListAddErrorEventDefinition() {
    // Arrange
    BoundaryEventParseHandler boundaryEventParseHandler = new BoundaryEventParseHandler();
    BpmnParseHandlers bpmnParseHandlers = mock(BpmnParseHandlers.class);
    doNothing().when(bpmnParseHandlers).parseElement(Mockito.<BpmnParse>any(), Mockito.<BaseElement>any());
    BpmnParser parser = mock(BpmnParser.class);
    when(parser.getBpmnParserHandlers()).thenReturn(bpmnParseHandlers);
    when(parser.getActivityBehaviorFactory()).thenReturn(new DefaultActivityBehaviorFactory());
    when(parser.getListenerFactory()).thenReturn(new DefaultListenerFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);

    ArrayList<EventDefinition> eventDefinitionList = new ArrayList<>();
    eventDefinitionList.add(new ErrorEventDefinition());
    BoundaryEvent boundaryEvent = mock(BoundaryEvent.class);
    when(boundaryEvent.getEventDefinitions()).thenReturn(eventDefinitionList);
    when(boundaryEvent.getAttachedToRef()).thenReturn(new AdhocSubProcess());

    // Act
    boundaryEventParseHandler.executeParse(bpmnParse, boundaryEvent);

    // Assert that nothing has changed
    verify(boundaryEvent).getAttachedToRef();
    verify(boundaryEvent, atLeast(1)).getEventDefinitions();
    verify(bpmnParseHandlers).parseElement(isA(BpmnParse.class), isA(BaseElement.class));
    verify(parser).getActivityBehaviorFactory();
    verify(parser).getBpmnParserHandlers();
    verify(parser).getListenerFactory();
  }

  /**
   * Test {@link BoundaryEventParseHandler#executeParse(BpmnParse, BoundaryEvent)}
   * with {@code BpmnParse}, {@code BoundaryEvent}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link TimerEventDefinition}
   * (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BoundaryEventParseHandler#executeParse(BpmnParse, BoundaryEvent)}
   */
  @Test
  public void testExecuteParseWithBpmnParseBoundaryEvent_givenArrayListAddTimerEventDefinition() {
    // Arrange
    BoundaryEventParseHandler boundaryEventParseHandler = new BoundaryEventParseHandler();
    BpmnParseHandlers bpmnParseHandlers = mock(BpmnParseHandlers.class);
    doNothing().when(bpmnParseHandlers).parseElement(Mockito.<BpmnParse>any(), Mockito.<BaseElement>any());
    BpmnParser parser = mock(BpmnParser.class);
    when(parser.getBpmnParserHandlers()).thenReturn(bpmnParseHandlers);
    when(parser.getActivityBehaviorFactory()).thenReturn(new DefaultActivityBehaviorFactory());
    when(parser.getListenerFactory()).thenReturn(new DefaultListenerFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);

    ArrayList<EventDefinition> eventDefinitionList = new ArrayList<>();
    eventDefinitionList.add(new TimerEventDefinition());
    BoundaryEvent boundaryEvent = mock(BoundaryEvent.class);
    when(boundaryEvent.getEventDefinitions()).thenReturn(eventDefinitionList);
    when(boundaryEvent.getAttachedToRef()).thenReturn(new AdhocSubProcess());

    // Act
    boundaryEventParseHandler.executeParse(bpmnParse, boundaryEvent);

    // Assert that nothing has changed
    verify(boundaryEvent).getAttachedToRef();
    verify(boundaryEvent, atLeast(1)).getEventDefinitions();
    verify(bpmnParseHandlers).parseElement(isA(BpmnParse.class), isA(BaseElement.class));
    verify(parser).getActivityBehaviorFactory();
    verify(parser).getBpmnParserHandlers();
    verify(parser).getListenerFactory();
  }

  /**
   * Test {@link BoundaryEventParseHandler#executeParse(BpmnParse, BoundaryEvent)}
   * with {@code BpmnParse}, {@code BoundaryEvent}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link BaseElement#getId()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BoundaryEventParseHandler#executeParse(BpmnParse, BoundaryEvent)}
   */
  @Test
  public void testExecuteParseWithBpmnParseBoundaryEvent_givenArrayList_thenCallsGetId() {
    // Arrange
    BoundaryEventParseHandler boundaryEventParseHandler = new BoundaryEventParseHandler();
    BpmnParser parser = mock(BpmnParser.class);
    when(parser.getBpmnParserHandlers()).thenReturn(new BpmnParseHandlers());
    when(parser.getActivityBehaviorFactory()).thenReturn(new DefaultActivityBehaviorFactory());
    when(parser.getListenerFactory()).thenReturn(new DefaultListenerFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);
    BoundaryEvent boundaryEvent = mock(BoundaryEvent.class);
    when(boundaryEvent.getEventDefinitions()).thenReturn(new ArrayList<>());
    when(boundaryEvent.getId()).thenReturn("42");
    when(boundaryEvent.getAttachedToRef()).thenReturn(new AdhocSubProcess());

    // Act
    boundaryEventParseHandler.executeParse(bpmnParse, boundaryEvent);

    // Assert that nothing has changed
    verify(boundaryEvent).getId();
    verify(boundaryEvent).getAttachedToRef();
    verify(boundaryEvent).getEventDefinitions();
    verify(parser).getActivityBehaviorFactory();
    verify(parser).getBpmnParserHandlers();
    verify(parser).getListenerFactory();
  }

  /**
   * Test {@link BoundaryEventParseHandler#executeParse(BpmnParse, BoundaryEvent)}
   * with {@code BpmnParse}, {@code BoundaryEvent}.
   * <ul>
   *   <li>Given {@link BpmnParseHandlers} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BoundaryEventParseHandler#executeParse(BpmnParse, BoundaryEvent)}
   */
  @Test
  public void testExecuteParseWithBpmnParseBoundaryEvent_givenBpmnParseHandlers() {
    // Arrange
    BoundaryEventParseHandler boundaryEventParseHandler = new BoundaryEventParseHandler();
    BpmnParser parser = mock(BpmnParser.class);
    when(parser.getBpmnParserHandlers()).thenReturn(new BpmnParseHandlers());
    when(parser.getActivityBehaviorFactory()).thenReturn(new DefaultActivityBehaviorFactory());
    when(parser.getListenerFactory()).thenReturn(new DefaultListenerFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);

    ArrayList<EventDefinition> eventDefinitionList = new ArrayList<>();
    eventDefinitionList.add(new CancelEventDefinition());
    BoundaryEvent boundaryEvent = mock(BoundaryEvent.class);
    when(boundaryEvent.getEventDefinitions()).thenReturn(eventDefinitionList);
    when(boundaryEvent.getAttachedToRef()).thenReturn(new AdhocSubProcess());

    // Act
    boundaryEventParseHandler.executeParse(bpmnParse, boundaryEvent);

    // Assert that nothing has changed
    verify(boundaryEvent).getAttachedToRef();
    verify(boundaryEvent, atLeast(1)).getEventDefinitions();
    verify(parser).getActivityBehaviorFactory();
    verify(parser).getBpmnParserHandlers();
    verify(parser).getListenerFactory();
  }

  /**
   * Test {@link BoundaryEventParseHandler#executeParse(BpmnParse, BoundaryEvent)}
   * with {@code BpmnParse}, {@code BoundaryEvent}.
   * <ul>
   *   <li>When {@link BoundaryEvent} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BoundaryEventParseHandler#executeParse(BpmnParse, BoundaryEvent)}
   */
  @Test
  public void testExecuteParseWithBpmnParseBoundaryEvent_whenBoundaryEvent() {
    // Arrange
    BoundaryEventParseHandler boundaryEventParseHandler = new BoundaryEventParseHandler();
    BpmnParser parser = mock(BpmnParser.class);
    when(parser.getBpmnParserHandlers()).thenReturn(new BpmnParseHandlers());
    when(parser.getActivityBehaviorFactory()).thenReturn(new DefaultActivityBehaviorFactory());
    when(parser.getListenerFactory()).thenReturn(new DefaultListenerFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);

    // Act
    boundaryEventParseHandler.executeParse(bpmnParse, new BoundaryEvent());

    // Assert that nothing has changed
    verify(parser).getActivityBehaviorFactory();
    verify(parser).getBpmnParserHandlers();
    verify(parser).getListenerFactory();
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link BoundaryEventParseHandler}
   *   <li>{@link BoundaryEventParseHandler#getHandledType()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends BaseElement> actualHandledType = (new BoundaryEventParseHandler()).getHandledType();

    // Assert
    Class<BoundaryEvent> expectedHandledType = BoundaryEvent.class;
    assertEquals(expectedHandledType, actualHandledType);
  }
}
