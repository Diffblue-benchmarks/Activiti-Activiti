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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EventListener;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.delegate.event.BaseEntityEventListener;
import org.activiti.engine.delegate.event.impl.ActivitiEventSupport;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.BpmnParseHandlers;
import org.activiti.engine.impl.bpmn.parser.BpmnParser;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultListenerFactory;
import org.activiti.engine.impl.bpmn.parser.factory.ListenerFactory;
import org.junit.Test;
import org.mockito.Mockito;

public class ProcessParseHandlerDiffblueTest {
  /**
   * Test {@link ProcessParseHandler#executeParse(BpmnParse, Process)} with
   * {@code BpmnParse}, {@code Process}.
   * <ul>
   *   <li>Given {@code false}.</li>
   *   <li>When {@link BpmnParse}.</li>
   *   <li>Then calls {@link BaseElement#getId()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessParseHandler#executeParse(BpmnParse, Process)}
   */
  @Test
  public void testExecuteParseWithBpmnParseProcess_givenFalse_whenBpmnParse_thenCallsGetId() {
    // Arrange
    ProcessParseHandler processParseHandler = new ProcessParseHandler();
    BpmnParse bpmnParse = mock(BpmnParse.class);
    Process process = mock(Process.class);
    when(process.isExecutable()).thenReturn(false);
    when(process.getId()).thenReturn("42");

    // Act
    processParseHandler.executeParse(bpmnParse, process);

    // Assert that nothing has changed
    verify(process).getId();
    verify(process).isExecutable();
  }

  /**
   * Test {@link ProcessParseHandler#createEventListeners(BpmnParse, List)}.
   * <p>
   * Method under test:
   * {@link ProcessParseHandler#createEventListeners(BpmnParse, List)}
   */
  @Test
  public void testCreateEventListeners() {
    // Arrange
    ProcessParseHandler processParseHandler = new ProcessParseHandler();
    BpmnParser parser = mock(BpmnParser.class);
    when(parser.getBpmnParserHandlers()).thenReturn(new BpmnParseHandlers());
    when(parser.getActivityBehaviorFactory()).thenReturn(new DefaultActivityBehaviorFactory());
    when(parser.getListenerFactory()).thenReturn(new DefaultListenerFactory());

    BpmnParse bpmnParse = new BpmnParse(parser);
    bpmnParse.setCurrentFlowElement(new AdhocSubProcess());

    ArrayList<EventListener> eventListeners = new ArrayList<>();
    eventListeners.add(new EventListener());

    // Act
    processParseHandler.createEventListeners(bpmnParse, eventListeners);

    // Assert that nothing has changed
    verify(parser).getActivityBehaviorFactory();
    verify(parser).getBpmnParserHandlers();
    verify(parser).getListenerFactory();
  }

  /**
   * Test {@link ProcessParseHandler#createEventListeners(BpmnParse, List)}.
   * <ul>
   *   <li>Given {@link EventListener} {@link EventListener#getEvents()} return
   * empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessParseHandler#createEventListeners(BpmnParse, List)}
   */
  @Test
  public void testCreateEventListeners_givenEventListenerGetEventsReturnEmptyString() {
    // Arrange
    ProcessParseHandler processParseHandler = new ProcessParseHandler();
    BpmnParse bpmnParse = mock(BpmnParse.class);
    when(bpmnParse.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());
    EventListener eventListener = mock(EventListener.class);
    when(eventListener.getEvents()).thenReturn("");
    when(eventListener.getImplementationType()).thenReturn("Implementation Type");

    ArrayList<EventListener> eventListeners = new ArrayList<>();
    eventListeners.add(eventListener);

    // Act
    processParseHandler.createEventListeners(bpmnParse, eventListeners);

    // Assert that nothing has changed
    verify(eventListener).getEvents();
    verify(eventListener, atLeast(1)).getImplementationType();
    verify(bpmnParse).getCurrentFlowElement();
  }

  /**
   * Test {@link ProcessParseHandler#createEventListeners(BpmnParse, List)}.
   * <ul>
   *   <li>Given {@link EventListener} {@link EventListener#getEvents()} return
   * {@code ENTITY_CREATED}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessParseHandler#createEventListeners(BpmnParse, List)}
   */
  @Test
  public void testCreateEventListeners_givenEventListenerGetEventsReturnEntityCreated() {
    // Arrange
    ProcessParseHandler processParseHandler = new ProcessParseHandler();
    BpmnParse bpmnParse = mock(BpmnParse.class);
    when(bpmnParse.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());
    EventListener eventListener = mock(EventListener.class);
    when(eventListener.getEvents()).thenReturn("ENTITY_CREATED");
    when(eventListener.getImplementationType()).thenReturn("Implementation Type");

    ArrayList<EventListener> eventListeners = new ArrayList<>();
    eventListeners.add(eventListener);

    // Act
    processParseHandler.createEventListeners(bpmnParse, eventListeners);

    // Assert
    verify(eventListener).getEvents();
    verify(eventListener, atLeast(1)).getImplementationType();
    verify(bpmnParse).getCurrentFlowElement();
  }

  /**
   * Test {@link ProcessParseHandler#createEventListeners(BpmnParse, List)}.
   * <ul>
   *   <li>Given {@link EventListener} (default constructor).</li>
   *   <li>Then calls {@link BpmnParse#getCurrentFlowElement()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessParseHandler#createEventListeners(BpmnParse, List)}
   */
  @Test
  public void testCreateEventListeners_givenEventListener_thenCallsGetCurrentFlowElement() {
    // Arrange
    ProcessParseHandler processParseHandler = new ProcessParseHandler();
    BpmnParse bpmnParse = mock(BpmnParse.class);
    when(bpmnParse.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());

    ArrayList<EventListener> eventListeners = new ArrayList<>();
    eventListeners.add(new EventListener());

    // Act
    processParseHandler.createEventListeners(bpmnParse, eventListeners);

    // Assert that nothing has changed
    verify(bpmnParse).getCurrentFlowElement();
  }

  /**
   * Test {@link ProcessParseHandler#createEventListeners(BpmnParse, List)}.
   * <ul>
   *   <li>Then calls {@link BpmnModel#getEventSupport()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessParseHandler#createEventListeners(BpmnParse, List)}
   */
  @Test
  public void testCreateEventListeners_thenCallsGetEventSupport() {
    // Arrange
    ProcessParseHandler processParseHandler = new ProcessParseHandler();
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getEventSupport()).thenReturn(new ActivitiEventSupport());
    ListenerFactory listenerFactory = mock(ListenerFactory.class);
    when(listenerFactory.createClassDelegateEventListener(Mockito.<EventListener>any()))
        .thenReturn(new BaseEntityEventListener(true));
    BpmnParse bpmnParse = mock(BpmnParse.class);
    when(bpmnParse.getListenerFactory()).thenReturn(listenerFactory);
    when(bpmnParse.getBpmnModel()).thenReturn(bpmnModel);
    EventListener eventListener = mock(EventListener.class);
    when(eventListener.getEvents()).thenReturn(",");
    when(eventListener.getImplementationType()).thenReturn("class");

    ArrayList<EventListener> eventListeners = new ArrayList<>();
    eventListeners.add(eventListener);

    // Act
    processParseHandler.createEventListeners(bpmnParse, eventListeners);

    // Assert
    verify(bpmnModel).getEventSupport();
    verify(eventListener).getEvents();
    verify(eventListener).getImplementationType();
    verify(bpmnParse).getBpmnModel();
    verify(bpmnParse).getListenerFactory();
    verify(listenerFactory).createClassDelegateEventListener(isA(EventListener.class));
  }

  /**
   * Test {@link ProcessParseHandler#createEventListeners(BpmnParse, List)}.
   * <ul>
   *   <li>Then calls {@link EventListener#getEvents()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessParseHandler#createEventListeners(BpmnParse, List)}
   */
  @Test
  public void testCreateEventListeners_thenCallsGetEvents() {
    // Arrange
    ProcessParseHandler processParseHandler = new ProcessParseHandler();
    BpmnParse bpmnParse = mock(BpmnParse.class);
    when(bpmnParse.getCurrentFlowElement()).thenReturn(new AdhocSubProcess());
    EventListener eventListener = mock(EventListener.class);
    when(eventListener.getEvents()).thenReturn(",");
    when(eventListener.getImplementationType()).thenReturn("Implementation Type");

    ArrayList<EventListener> eventListeners = new ArrayList<>();
    eventListeners.add(eventListener);

    // Act
    processParseHandler.createEventListeners(bpmnParse, eventListeners);

    // Assert that nothing has changed
    verify(eventListener).getEvents();
    verify(eventListener, atLeast(1)).getImplementationType();
    verify(bpmnParse).getCurrentFlowElement();
  }

  /**
   * Test {@link ProcessParseHandler#createEventListeners(BpmnParse, List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link BpmnParser#getActivityBehaviorFactory()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessParseHandler#createEventListeners(BpmnParse, List)}
   */
  @Test
  public void testCreateEventListeners_whenArrayList_thenCallsGetActivityBehaviorFactory() {
    // Arrange
    ProcessParseHandler processParseHandler = new ProcessParseHandler();
    BpmnParser parser = mock(BpmnParser.class);
    when(parser.getBpmnParserHandlers()).thenReturn(new BpmnParseHandlers());
    when(parser.getActivityBehaviorFactory()).thenReturn(new DefaultActivityBehaviorFactory());
    when(parser.getListenerFactory()).thenReturn(new DefaultListenerFactory());
    BpmnParse bpmnParse = new BpmnParse(parser);

    // Act
    processParseHandler.createEventListeners(bpmnParse, new ArrayList<>());

    // Assert that nothing has changed
    verify(parser).getActivityBehaviorFactory();
    verify(parser).getBpmnParserHandlers();
    verify(parser).getListenerFactory();
  }

  /**
   * Test {@link ProcessParseHandler#getEventSupport(BpmnModel)}.
   * <ul>
   *   <li>When {@link BpmnModel} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessParseHandler#getEventSupport(BpmnModel)}
   */
  @Test
  public void testGetEventSupport_whenBpmnModel_thenReturnNull() {
    // Arrange
    ProcessParseHandler processParseHandler = new ProcessParseHandler();

    // Act and Assert
    assertNull(processParseHandler.getEventSupport(new BpmnModel()));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ProcessParseHandler}
   *   <li>{@link ProcessParseHandler#getHandledType()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Class<? extends BaseElement> actualHandledType = (new ProcessParseHandler()).getHandledType();

    // Assert
    Class<Process> expectedHandledType = Process.class;
    assertEquals(expectedHandledType, actualHandledType);
  }
}
