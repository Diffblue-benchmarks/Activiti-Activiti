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
package org.activiti.engine.impl.event.logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiActivityCancelledEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiActivityEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiVariableUpdatedEventImpl;
import org.activiti.engine.impl.event.logger.handler.ActivityCompensatedEventHandler;
import org.activiti.engine.impl.event.logger.handler.ActivityErrorReceivedEventHandler;
import org.activiti.engine.impl.event.logger.handler.ActivitySignaledEventHandler;
import org.activiti.engine.impl.event.logger.handler.EventLoggerEventHandler;
import org.activiti.engine.impl.event.logger.handler.SequenceFlowTakenEventHandler;
import org.activiti.engine.impl.event.logger.handler.TaskAssignedEventHandler;
import org.activiti.engine.impl.event.logger.handler.VariableDeletedEventHandler;
import org.activiti.engine.impl.event.logger.handler.VariableUpdatedEventHandler;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.engine.runtime.Clock;
import org.junit.Test;

public class EventLoggerDiffblueTest {
  /**
   * Method under test: {@link EventLogger#getEventHandler(ActivitiEvent)}
   */
  @Test
  public void testGetEventHandler() {
    // Arrange
    EventLogger eventLogger = new EventLogger();

    // Act and Assert
    assertNull(eventLogger.getEventHandler(new ActivitiActivityCancelledEventImpl()));
  }

  /**
   * Method under test: {@link EventLogger#getEventHandler(ActivitiEvent)}
   */
  @Test
  public void testGetEventHandler2() {
    // Arrange
    EventLogger eventLogger = new EventLogger();
    eventLogger.addEventLoggerListener(mock(EventLoggerListener.class));

    // Act and Assert
    assertNull(eventLogger.getEventHandler(new ActivitiActivityCancelledEventImpl()));
  }

  /**
   * Method under test: {@link EventLogger#getEventHandler(ActivitiEvent)}
   */
  @Test
  public void testGetEventHandler3() {
    // Arrange
    EventLogger eventLogger = new EventLogger();

    // Act and Assert
    assertNull(eventLogger.getEventHandler(new ActivitiVariableUpdatedEventImpl()));
  }

  /**
   * Method under test: {@link EventLogger#getEventHandler(ActivitiEvent)}
   */
  @Test
  public void testGetEventHandler4() {
    // Arrange
    EventLogger eventLogger = new EventLogger();
    eventLogger.setClock(new DefaultClockImpl());

    // Act and Assert
    assertTrue(
        eventLogger.getEventHandler(new ActivitiVariableUpdatedEventImpl()) instanceof VariableUpdatedEventHandler);
  }

  /**
   * Method under test: {@link EventLogger#getEventHandler(ActivitiEvent)}
   */
  @Test
  public void testGetEventHandler5() {
    // Arrange
    EventLogger eventLogger = new EventLogger();
    Class<EventLoggerEventHandler> eventHandlerClass = EventLoggerEventHandler.class;
    eventLogger.addEventHandler(ActivitiEventType.ENTITY_CREATED, eventHandlerClass);

    // Act and Assert
    assertNull(eventLogger.getEventHandler(new ActivitiActivityEventImpl(ActivitiEventType.ENTITY_CREATED)));
  }

  /**
   * Method under test:
   * {@link EventLogger#instantiateEventHandler(ActivitiEvent, Class)}
   */
  @Test
  public void testInstantiateEventHandler() {
    // Arrange
    EventLogger eventLogger = new EventLogger();
    ActivitiActivityCancelledEventImpl event = new ActivitiActivityCancelledEventImpl();
    Class<EventLoggerEventHandler> eventHandlerClass = EventLoggerEventHandler.class;

    // Act and Assert
    assertNull(eventLogger.instantiateEventHandler(event, eventHandlerClass));
  }

  /**
   * Method under test:
   * {@link EventLogger#instantiateEventHandler(ActivitiEvent, Class)}
   */
  @Test
  public void testInstantiateEventHandler2() {
    // Arrange
    EventLogger eventLogger = new EventLogger();
    eventLogger.addEventLoggerListener(mock(EventLoggerListener.class));
    ActivitiActivityCancelledEventImpl event = new ActivitiActivityCancelledEventImpl();
    Class<EventLoggerEventHandler> eventHandlerClass = EventLoggerEventHandler.class;

    // Act and Assert
    assertNull(eventLogger.instantiateEventHandler(event, eventHandlerClass));
  }

  /**
   * Method under test:
   * {@link EventLogger#instantiateEventHandler(ActivitiEvent, Class)}
   */
  @Test
  public void testInstantiateEventHandler3() {
    // Arrange
    EventLogger eventLogger = new EventLogger();
    ActivitiActivityCancelledEventImpl event = new ActivitiActivityCancelledEventImpl();
    Class<VariableUpdatedEventHandler> eventHandlerClass = VariableUpdatedEventHandler.class;

    // Act and Assert
    assertNull(eventLogger.instantiateEventHandler(event, eventHandlerClass));
  }

  /**
   * Method under test:
   * {@link EventLogger#instantiateEventHandler(ActivitiEvent, Class)}
   */
  @Test
  public void testInstantiateEventHandler4() {
    // Arrange
    EventLogger eventLogger = new EventLogger();
    eventLogger.setClock(new DefaultClockImpl());
    ActivitiActivityCancelledEventImpl event = new ActivitiActivityCancelledEventImpl();
    Class<VariableUpdatedEventHandler> eventHandlerClass = VariableUpdatedEventHandler.class;

    // Act and Assert
    assertTrue(eventLogger.instantiateEventHandler(event, eventHandlerClass) instanceof VariableUpdatedEventHandler);
  }

  /**
   * Method under test:
   * {@link EventLogger#addEventHandler(ActivitiEventType, Class)}
   */
  @Test
  public void testAddEventHandler() {
    // Arrange
    EventLogger eventLogger = new EventLogger();
    Class<EventLoggerEventHandler> eventHandlerClass = EventLoggerEventHandler.class;

    // Act
    eventLogger.addEventHandler(ActivitiEventType.ENTITY_CREATED, eventHandlerClass);

    // Assert
    Map<ActivitiEventType, Class<? extends EventLoggerEventHandler>> activitiEventTypeResultClassMap = eventLogger.eventHandlers;
    assertEquals(14, activitiEventTypeResultClassMap.size());
    assertTrue(activitiEventTypeResultClassMap.containsKey(ActivitiEventType.ACTIVITY_COMPENSATE));
    assertTrue(activitiEventTypeResultClassMap.containsKey(ActivitiEventType.ACTIVITY_ERROR_RECEIVED));
    assertTrue(activitiEventTypeResultClassMap.containsKey(ActivitiEventType.ACTIVITY_SIGNALED));
    assertTrue(activitiEventTypeResultClassMap.containsKey(ActivitiEventType.SEQUENCEFLOW_TAKEN));
    assertTrue(activitiEventTypeResultClassMap.containsKey(ActivitiEventType.TASK_ASSIGNED));
    assertTrue(activitiEventTypeResultClassMap.containsKey(ActivitiEventType.VARIABLE_DELETED));
  }

  /**
   * Method under test:
   * {@link EventLogger#addEventHandler(ActivitiEventType, Class)}
   */
  @Test
  public void testAddEventHandler2() {
    // Arrange
    EventLogger eventLogger = new EventLogger();
    eventLogger.addEventLoggerListener(mock(EventLoggerListener.class));
    Class<EventLoggerEventHandler> eventHandlerClass = EventLoggerEventHandler.class;

    // Act
    eventLogger.addEventHandler(ActivitiEventType.ENTITY_CREATED, eventHandlerClass);

    // Assert
    Map<ActivitiEventType, Class<? extends EventLoggerEventHandler>> activitiEventTypeResultClassMap = eventLogger.eventHandlers;
    assertEquals(14, activitiEventTypeResultClassMap.size());
    assertTrue(activitiEventTypeResultClassMap.containsKey(ActivitiEventType.ACTIVITY_COMPENSATE));
    assertTrue(activitiEventTypeResultClassMap.containsKey(ActivitiEventType.ACTIVITY_ERROR_RECEIVED));
    assertTrue(activitiEventTypeResultClassMap.containsKey(ActivitiEventType.ACTIVITY_SIGNALED));
    assertTrue(activitiEventTypeResultClassMap.containsKey(ActivitiEventType.SEQUENCEFLOW_TAKEN));
    assertTrue(activitiEventTypeResultClassMap.containsKey(ActivitiEventType.TASK_ASSIGNED));
    assertTrue(activitiEventTypeResultClassMap.containsKey(ActivitiEventType.VARIABLE_DELETED));
  }

  /**
   * Method under test:
   * {@link EventLogger#addEventLoggerListener(EventLoggerListener)}
   */
  @Test
  public void testAddEventLoggerListener() {
    // Arrange
    EventLogger eventLogger = new EventLogger();
    EventLoggerListener listener = mock(EventLoggerListener.class);

    // Act
    eventLogger.addEventLoggerListener(listener);

    // Assert
    List<EventLoggerListener> listeners = eventLogger.getListeners();
    assertEquals(1, listeners.size());
    assertSame(listener, listeners.get(0));
  }

  /**
   * Method under test:
   * {@link EventLogger#addEventLoggerListener(EventLoggerListener)}
   */
  @Test
  public void testAddEventLoggerListener2() {
    // Arrange
    EventLogger eventLogger = new EventLogger();
    ArrayList<EventLoggerListener> listeners = new ArrayList<>();
    eventLogger.setListeners(listeners);
    EventLoggerListener listener = mock(EventLoggerListener.class);

    // Act
    eventLogger.addEventLoggerListener(listener);

    // Assert
    List<EventLoggerListener> listeners2 = eventLogger.getListeners();
    assertEquals(1, listeners2.size());
    assertSame(listeners, listeners2);
    assertSame(listener, listeners2.get(0));
  }

  /**
   * Method under test: {@link EventLogger#createEventFlusher()}
   */
  @Test
  public void testCreateEventFlusher() {
    // Arrange, Act and Assert
    assertNull((new EventLogger()).createEventFlusher());
  }

  /**
   * Method under test: {@link EventLogger#createEventFlusher()}
   */
  @Test
  public void testCreateEventFlusher2() {
    // Arrange
    EventLogger eventLogger = new EventLogger();
    eventLogger.addEventLoggerListener(mock(EventLoggerListener.class));

    // Act and Assert
    assertNull(eventLogger.createEventFlusher());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link EventLogger#setClock(Clock)}
   *   <li>{@link EventLogger#setListeners(List)}
   *   <li>{@link EventLogger#setObjectMapper(ObjectMapper)}
   *   <li>{@link EventLogger#getClock()}
   *   <li>{@link EventLogger#getListeners()}
   *   <li>{@link EventLogger#getObjectMapper()}
   *   <li>{@link EventLogger#isFailOnException()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    EventLogger eventLogger = new EventLogger();
    DefaultClockImpl clock = new DefaultClockImpl();

    // Act
    eventLogger.setClock(clock);
    ArrayList<EventLoggerListener> listeners = new ArrayList<>();
    eventLogger.setListeners(listeners);
    ObjectMapper objectMapper = new ObjectMapper();
    eventLogger.setObjectMapper(objectMapper);
    Clock actualClock = eventLogger.getClock();
    List<EventLoggerListener> actualListeners = eventLogger.getListeners();
    ObjectMapper actualObjectMapper = eventLogger.getObjectMapper();

    // Assert that nothing has changed
    assertFalse(eventLogger.isFailOnException());
    assertTrue(actualListeners.isEmpty());
    assertSame(objectMapper, actualObjectMapper);
    assertSame(listeners, actualListeners);
    assertSame(clock, actualClock);
  }

  /**
   * Method under test: {@link EventLogger#EventLogger()}
   */
  @Test
  public void testNewEventLogger() {
    // Arrange and Act
    EventLogger actualEventLogger = new EventLogger();

    // Assert
    assertNull(actualEventLogger.getObjectMapper());
    assertNull(actualEventLogger.getListeners());
    assertNull(actualEventLogger.getClock());
    Map<ActivitiEventType, Class<? extends EventLoggerEventHandler>> activitiEventTypeResultClassMap = actualEventLogger.eventHandlers;
    assertEquals(13, activitiEventTypeResultClassMap.size());
    assertFalse(actualEventLogger.isFailOnException());
    Class<ActivityCompensatedEventHandler> expectedGetResult = ActivityCompensatedEventHandler.class;
    assertEquals(expectedGetResult, activitiEventTypeResultClassMap.get(ActivitiEventType.ACTIVITY_COMPENSATE));
    Class<ActivityErrorReceivedEventHandler> expectedGetResult2 = ActivityErrorReceivedEventHandler.class;
    assertEquals(expectedGetResult2, activitiEventTypeResultClassMap.get(ActivitiEventType.ACTIVITY_ERROR_RECEIVED));
    Class<ActivitySignaledEventHandler> expectedGetResult3 = ActivitySignaledEventHandler.class;
    assertEquals(expectedGetResult3, activitiEventTypeResultClassMap.get(ActivitiEventType.ACTIVITY_SIGNALED));
    Class<SequenceFlowTakenEventHandler> expectedGetResult4 = SequenceFlowTakenEventHandler.class;
    assertEquals(expectedGetResult4, activitiEventTypeResultClassMap.get(ActivitiEventType.SEQUENCEFLOW_TAKEN));
    Class<TaskAssignedEventHandler> expectedGetResult5 = TaskAssignedEventHandler.class;
    assertEquals(expectedGetResult5, activitiEventTypeResultClassMap.get(ActivitiEventType.TASK_ASSIGNED));
    Class<VariableDeletedEventHandler> expectedGetResult6 = VariableDeletedEventHandler.class;
    assertEquals(expectedGetResult6, activitiEventTypeResultClassMap.get(ActivitiEventType.VARIABLE_DELETED));
  }

  /**
   * Method under test: {@link EventLogger#EventLogger(Clock, ObjectMapper)}
   */
  @Test
  public void testNewEventLogger2() {
    // Arrange
    DefaultClockImpl clock = new DefaultClockImpl();
    ObjectMapper objectMapper = new ObjectMapper();

    // Act
    EventLogger actualEventLogger = new EventLogger(clock, objectMapper);

    // Assert
    assertNull(actualEventLogger.getListeners());
    Map<ActivitiEventType, Class<? extends EventLoggerEventHandler>> activitiEventTypeResultClassMap = actualEventLogger.eventHandlers;
    assertEquals(13, activitiEventTypeResultClassMap.size());
    assertFalse(actualEventLogger.isFailOnException());
    Class<ActivityCompensatedEventHandler> expectedGetResult = ActivityCompensatedEventHandler.class;
    assertEquals(expectedGetResult, activitiEventTypeResultClassMap.get(ActivitiEventType.ACTIVITY_COMPENSATE));
    Class<ActivityErrorReceivedEventHandler> expectedGetResult2 = ActivityErrorReceivedEventHandler.class;
    assertEquals(expectedGetResult2, activitiEventTypeResultClassMap.get(ActivitiEventType.ACTIVITY_ERROR_RECEIVED));
    Class<ActivitySignaledEventHandler> expectedGetResult3 = ActivitySignaledEventHandler.class;
    assertEquals(expectedGetResult3, activitiEventTypeResultClassMap.get(ActivitiEventType.ACTIVITY_SIGNALED));
    Class<SequenceFlowTakenEventHandler> expectedGetResult4 = SequenceFlowTakenEventHandler.class;
    assertEquals(expectedGetResult4, activitiEventTypeResultClassMap.get(ActivitiEventType.SEQUENCEFLOW_TAKEN));
    Class<TaskAssignedEventHandler> expectedGetResult5 = TaskAssignedEventHandler.class;
    assertEquals(expectedGetResult5, activitiEventTypeResultClassMap.get(ActivitiEventType.TASK_ASSIGNED));
    Class<VariableDeletedEventHandler> expectedGetResult6 = VariableDeletedEventHandler.class;
    assertEquals(expectedGetResult6, activitiEventTypeResultClassMap.get(ActivitiEventType.VARIABLE_DELETED));
    assertSame(objectMapper, actualEventLogger.getObjectMapper());
    assertSame(clock, actualEventLogger.getClock());
  }

  /**
   * Method under test: {@link EventLogger#EventLogger(Clock, ObjectMapper)}
   */
  @Test
  public void testNewEventLogger3() {
    // Arrange
    DefaultClockImpl clock = new DefaultClockImpl();

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializerFactory(mock(SerializerFactory.class));

    // Act
    EventLogger actualEventLogger = new EventLogger(clock, objectMapper);

    // Assert
    assertNull(actualEventLogger.getListeners());
    Map<ActivitiEventType, Class<? extends EventLoggerEventHandler>> activitiEventTypeResultClassMap = actualEventLogger.eventHandlers;
    assertEquals(13, activitiEventTypeResultClassMap.size());
    assertFalse(actualEventLogger.isFailOnException());
    Class<ActivityCompensatedEventHandler> expectedGetResult = ActivityCompensatedEventHandler.class;
    assertEquals(expectedGetResult, activitiEventTypeResultClassMap.get(ActivitiEventType.ACTIVITY_COMPENSATE));
    Class<ActivityErrorReceivedEventHandler> expectedGetResult2 = ActivityErrorReceivedEventHandler.class;
    assertEquals(expectedGetResult2, activitiEventTypeResultClassMap.get(ActivitiEventType.ACTIVITY_ERROR_RECEIVED));
    Class<ActivitySignaledEventHandler> expectedGetResult3 = ActivitySignaledEventHandler.class;
    assertEquals(expectedGetResult3, activitiEventTypeResultClassMap.get(ActivitiEventType.ACTIVITY_SIGNALED));
    Class<SequenceFlowTakenEventHandler> expectedGetResult4 = SequenceFlowTakenEventHandler.class;
    assertEquals(expectedGetResult4, activitiEventTypeResultClassMap.get(ActivitiEventType.SEQUENCEFLOW_TAKEN));
    Class<TaskAssignedEventHandler> expectedGetResult5 = TaskAssignedEventHandler.class;
    assertEquals(expectedGetResult5, activitiEventTypeResultClassMap.get(ActivitiEventType.TASK_ASSIGNED));
    Class<VariableDeletedEventHandler> expectedGetResult6 = VariableDeletedEventHandler.class;
    assertEquals(expectedGetResult6, activitiEventTypeResultClassMap.get(ActivitiEventType.VARIABLE_DELETED));
    assertSame(objectMapper, actualEventLogger.getObjectMapper());
    assertSame(clock, actualEventLogger.getClock());
  }
}
