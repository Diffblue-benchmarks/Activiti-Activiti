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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiActivityCancelledEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiActivityEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.delegate.event.impl.ActivitiVariableUpdatedEventImpl;
import org.activiti.engine.impl.event.logger.handler.ActivityCompensatedEventHandler;
import org.activiti.engine.impl.event.logger.handler.EventLoggerEventHandler;
import org.activiti.engine.impl.event.logger.handler.SequenceFlowTakenEventHandler;
import org.activiti.engine.impl.event.logger.handler.TaskCompletedEventHandler;
import org.activiti.engine.impl.event.logger.handler.TaskCreatedEventHandler;
import org.activiti.engine.impl.event.logger.handler.VariableCreatedEventHandler;
import org.activiti.engine.impl.event.logger.handler.VariableDeletedEventHandler;
import org.activiti.engine.impl.event.logger.handler.VariableUpdatedEventHandler;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.engine.runtime.Clock;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class EventLoggerDiffblueTest {
  /**
   * Test {@link EventLogger#EventLogger()}.
   *
   * <p>Method under test: {@link EventLogger#EventLogger()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventLogger.<init>()"})
  public void testNewEventLogger() {
    // Arrange and Act
    EventLogger actualEventLogger = new EventLogger();

    // Assert
    assertNull(actualEventLogger.getObjectMapper());
    assertNull(actualEventLogger.getListeners());
    assertNull(actualEventLogger.getClock());
    Map<ActivitiEventType, Class<? extends EventLoggerEventHandler>>
        activitiEventTypeResultClassMap = actualEventLogger.eventHandlers;
    assertEquals(13, activitiEventTypeResultClassMap.size());
    assertFalse(actualEventLogger.isFailOnException());
    Class<ActivityCompensatedEventHandler> expectedGetResult =
        ActivityCompensatedEventHandler.class;
    assertEquals(
        expectedGetResult,
        activitiEventTypeResultClassMap.get(ActivitiEventType.ACTIVITY_COMPENSATE));
    Class<SequenceFlowTakenEventHandler> expectedGetResult2 = SequenceFlowTakenEventHandler.class;
    assertEquals(
        expectedGetResult2,
        activitiEventTypeResultClassMap.get(ActivitiEventType.SEQUENCEFLOW_TAKEN));
    Class<TaskCompletedEventHandler> expectedGetResult3 = TaskCompletedEventHandler.class;
    assertEquals(
        expectedGetResult3, activitiEventTypeResultClassMap.get(ActivitiEventType.TASK_COMPLETED));
    Class<TaskCreatedEventHandler> expectedGetResult4 = TaskCreatedEventHandler.class;
    assertEquals(
        expectedGetResult4, activitiEventTypeResultClassMap.get(ActivitiEventType.TASK_CREATED));
    Class<VariableCreatedEventHandler> expectedGetResult5 = VariableCreatedEventHandler.class;
    assertEquals(
        expectedGetResult5,
        activitiEventTypeResultClassMap.get(ActivitiEventType.VARIABLE_CREATED));
    Class<VariableDeletedEventHandler> expectedGetResult6 = VariableDeletedEventHandler.class;
    assertEquals(
        expectedGetResult6,
        activitiEventTypeResultClassMap.get(ActivitiEventType.VARIABLE_DELETED));
  }

  /**
   * Test {@link EventLogger#EventLogger(Clock, ObjectMapper)}.
   *
   * <p>Method under test: {@link EventLogger#EventLogger(Clock, ObjectMapper)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventLogger.<init>(Clock, ObjectMapper)"})
  public void testNewEventLogger2() {
    // Arrange
    DefaultClockImpl clock = new DefaultClockImpl();
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act
    EventLogger actualEventLogger = new EventLogger(clock, objectMapper);

    // Assert
    ObjectMapper objectMapper2 = actualEventLogger.getObjectMapper();
    assertTrue(objectMapper2 instanceof JsonMapper);
    Clock clock2 = actualEventLogger.getClock();
    assertTrue(clock2 instanceof DefaultClockImpl);
    assertNull(actualEventLogger.getListeners());
    Map<ActivitiEventType, Class<? extends EventLoggerEventHandler>>
        activitiEventTypeResultClassMap = actualEventLogger.eventHandlers;
    assertEquals(13, activitiEventTypeResultClassMap.size());
    assertFalse(actualEventLogger.isFailOnException());
    assertTrue(activitiEventTypeResultClassMap.containsKey(ActivitiEventType.ACTIVITY_COMPENSATE));
    assertTrue(activitiEventTypeResultClassMap.containsKey(ActivitiEventType.SEQUENCEFLOW_TAKEN));
    assertTrue(activitiEventTypeResultClassMap.containsKey(ActivitiEventType.TASK_COMPLETED));
    assertTrue(activitiEventTypeResultClassMap.containsKey(ActivitiEventType.TASK_CREATED));
    assertTrue(activitiEventTypeResultClassMap.containsKey(ActivitiEventType.VARIABLE_CREATED));
    assertTrue(activitiEventTypeResultClassMap.containsKey(ActivitiEventType.VARIABLE_DELETED));
    assertSame(clock, clock2);
    assertSame(objectMapper, objectMapper2);
  }

  /**
   * Test {@link EventLogger#getEventHandler(ActivitiEvent)}.
   *
   * <p>Method under test: {@link EventLogger#getEventHandler(ActivitiEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"EventLoggerEventHandler EventLogger.getEventHandler(ActivitiEvent)"})
  public void testGetEventHandler() {
    // Arrange
    EventLogger eventLogger = new EventLogger();
    Class<EventLoggerEventHandler> eventHandlerClass = EventLoggerEventHandler.class;
    eventLogger.addEventHandler(ActivitiEventType.ENTITY_CREATED, eventHandlerClass);

    // Act
    EventLoggerEventHandler actualEventHandler =
        eventLogger.getEventHandler(
            new ActivitiActivityEventImpl(ActivitiEventType.ENTITY_CREATED));

    // Assert
    assertNull(actualEventHandler);
  }

  /**
   * Test {@link EventLogger#getEventHandler(ActivitiEvent)}.
   *
   * <p>Method under test: {@link EventLogger#getEventHandler(ActivitiEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"EventLoggerEventHandler EventLogger.getEventHandler(ActivitiEvent)"})
  public void testGetEventHandler2() {
    // Arrange
    DefaultClockImpl clock = new DefaultClockImpl();
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    EventLogger eventLogger = new EventLogger(clock, objectMapper);

    // Act
    EventLoggerEventHandler actualEventHandler =
        eventLogger.getEventHandler(
            new ActivitiEntityEventImpl(1, ActivitiEventType.ENTITY_INITIALIZED));

    // Assert
    assertNull(actualEventHandler);
  }

  /**
   * Test {@link EventLogger#getEventHandler(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link EventLogger#getEventHandler(ActivitiEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"EventLoggerEventHandler EventLogger.getEventHandler(ActivitiEvent)"})
  public void testGetEventHandler_given42() {
    // Arrange
    DefaultClockImpl clock = new DefaultClockImpl();
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    EventLogger eventLogger = new EventLogger(clock, objectMapper);

    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    createWithEmptyRelationshipCollectionsResult.setProcessInstanceId("42");

    // Act
    EventLoggerEventHandler actualEventHandler =
        eventLogger.getEventHandler(
            new ActivitiEntityEventImpl(
                createWithEmptyRelationshipCollectionsResult,
                ActivitiEventType.ENTITY_INITIALIZED));

    // Assert
    assertNull(actualEventHandler);
  }

  /**
   * Test {@link EventLogger#getEventHandler(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Given {@link EventLogger#EventLogger()}.
   *   <li>When {@link ActivitiActivityCancelledEventImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link EventLogger#getEventHandler(ActivitiEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"EventLoggerEventHandler EventLogger.getEventHandler(ActivitiEvent)"})
  public void testGetEventHandler_givenEventLogger_whenActivitiActivityCancelledEventImpl() {
    // Arrange
    EventLogger eventLogger = new EventLogger();

    // Act and Assert
    assertNull(eventLogger.getEventHandler(new ActivitiActivityCancelledEventImpl()));
  }

  /**
   * Test {@link EventLogger#getEventHandler(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Given {@link EventLogger#EventLogger()}.
   *   <li>When {@link ActivitiVariableUpdatedEventImpl} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link EventLogger#getEventHandler(ActivitiEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"EventLoggerEventHandler EventLogger.getEventHandler(ActivitiEvent)"})
  public void testGetEventHandler_givenEventLogger_whenActivitiVariableUpdatedEventImpl() {
    // Arrange
    EventLogger eventLogger = new EventLogger();

    // Act and Assert
    assertNull(eventLogger.getEventHandler(new ActivitiVariableUpdatedEventImpl()));
  }

  /**
   * Test {@link EventLogger#getEventHandler(ActivitiEvent)}.
   *
   * <ul>
   *   <li>Then return {@link VariableUpdatedEventHandler}.
   * </ul>
   *
   * <p>Method under test: {@link EventLogger#getEventHandler(ActivitiEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"EventLoggerEventHandler EventLogger.getEventHandler(ActivitiEvent)"})
  public void testGetEventHandler_thenReturnVariableUpdatedEventHandler() {
    // Arrange
    EventLogger eventLogger = new EventLogger();
    eventLogger.setClock(new DefaultClockImpl());

    // Act and Assert
    assertTrue(
        eventLogger.getEventHandler(new ActivitiVariableUpdatedEventImpl())
            instanceof VariableUpdatedEventHandler);
  }

  /**
   * Test {@link EventLogger#instantiateEventHandler(ActivitiEvent, Class)}.
   *
   * <p>Method under test: {@link EventLogger#instantiateEventHandler(ActivitiEvent, Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "EventLoggerEventHandler EventLogger.instantiateEventHandler(ActivitiEvent, Class)"
  })
  public void testInstantiateEventHandler() {
    // Arrange
    EventLogger eventLogger = new EventLogger();
    ActivitiActivityCancelledEventImpl event = new ActivitiActivityCancelledEventImpl();
    Class<EventLoggerEventHandler> eventHandlerClass = EventLoggerEventHandler.class;

    // Act and Assert
    assertNull(eventLogger.instantiateEventHandler(event, eventHandlerClass));
  }

  /**
   * Test {@link EventLogger#instantiateEventHandler(ActivitiEvent, Class)}.
   *
   * <p>Method under test: {@link EventLogger#instantiateEventHandler(ActivitiEvent, Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "EventLoggerEventHandler EventLogger.instantiateEventHandler(ActivitiEvent, Class)"
  })
  public void testInstantiateEventHandler2() {
    // Arrange
    EventLogger eventLogger = new EventLogger();
    ActivitiActivityCancelledEventImpl event = new ActivitiActivityCancelledEventImpl();
    Class<VariableUpdatedEventHandler> eventHandlerClass = VariableUpdatedEventHandler.class;

    // Act and Assert
    assertNull(eventLogger.instantiateEventHandler(event, eventHandlerClass));
  }

  /**
   * Test {@link EventLogger#instantiateEventHandler(ActivitiEvent, Class)}.
   *
   * <ul>
   *   <li>Then return {@link VariableUpdatedEventHandler}.
   * </ul>
   *
   * <p>Method under test: {@link EventLogger#instantiateEventHandler(ActivitiEvent, Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "EventLoggerEventHandler EventLogger.instantiateEventHandler(ActivitiEvent, Class)"
  })
  public void testInstantiateEventHandler_thenReturnVariableUpdatedEventHandler() {
    // Arrange
    EventLogger eventLogger = new EventLogger();
    eventLogger.setClock(new DefaultClockImpl());
    ActivitiActivityCancelledEventImpl event = new ActivitiActivityCancelledEventImpl();
    Class<VariableUpdatedEventHandler> eventHandlerClass = VariableUpdatedEventHandler.class;

    // Act and Assert
    assertTrue(
        eventLogger.instantiateEventHandler(event, eventHandlerClass)
            instanceof VariableUpdatedEventHandler);
  }

  /**
   * Test {@link EventLogger#addEventHandler(ActivitiEventType, Class)}.
   *
   * <p>Method under test: {@link EventLogger#addEventHandler(ActivitiEventType, Class)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventLogger.addEventHandler(ActivitiEventType, Class)"})
  public void testAddEventHandler() {
    // Arrange
    EventLogger eventLogger = new EventLogger();
    Class<EventLoggerEventHandler> eventHandlerClass = EventLoggerEventHandler.class;

    // Act
    eventLogger.addEventHandler(ActivitiEventType.ENTITY_CREATED, eventHandlerClass);

    // Assert
    Map<ActivitiEventType, Class<? extends EventLoggerEventHandler>>
        activitiEventTypeResultClassMap = eventLogger.eventHandlers;
    assertEquals(14, activitiEventTypeResultClassMap.size());
    assertTrue(activitiEventTypeResultClassMap.containsKey(ActivitiEventType.ACTIVITY_COMPENSATE));
    assertTrue(activitiEventTypeResultClassMap.containsKey(ActivitiEventType.SEQUENCEFLOW_TAKEN));
    assertTrue(activitiEventTypeResultClassMap.containsKey(ActivitiEventType.TASK_COMPLETED));
    assertTrue(activitiEventTypeResultClassMap.containsKey(ActivitiEventType.TASK_CREATED));
    assertTrue(activitiEventTypeResultClassMap.containsKey(ActivitiEventType.VARIABLE_CREATED));
    Class<EventLoggerEventHandler> expectedGetResult = EventLoggerEventHandler.class;
    assertEquals(
        expectedGetResult, activitiEventTypeResultClassMap.get(ActivitiEventType.ENTITY_CREATED));
  }

  /**
   * Test {@link EventLogger#addEventLoggerListener(EventLoggerListener)}.
   *
   * <p>Method under test: {@link EventLogger#addEventLoggerListener(EventLoggerListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventLogger.addEventLoggerListener(EventLoggerListener)"})
  public void testAddEventLoggerListener() {
    // Arrange
    DefaultClockImpl clock = new DefaultClockImpl();
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    EventLogger eventLogger = new EventLogger(clock, objectMapper);
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
   * Test {@link EventLogger#addEventLoggerListener(EventLoggerListener)}.
   *
   * <ul>
   *   <li>Given {@link EventLogger#EventLogger()}.
   *   <li>Then {@link EventLogger#EventLogger()} Listeners size is one.
   * </ul>
   *
   * <p>Method under test: {@link EventLogger#addEventLoggerListener(EventLoggerListener)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventLogger.addEventLoggerListener(EventLoggerListener)"})
  public void testAddEventLoggerListener_givenEventLogger_thenEventLoggerListenersSizeIsOne() {
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
   * Test {@link EventLogger#createEventFlusher()}.
   *
   * <p>Method under test: {@link EventLogger#createEventFlusher()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.event.logger.EventFlusher EventLogger.createEventFlusher()"
  })
  public void testCreateEventFlusher() {
    // Arrange, Act and Assert
    assertNull(new EventLogger().createEventFlusher());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
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
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Clock EventLogger.getClock()",
    "List EventLogger.getListeners()",
    "ObjectMapper EventLogger.getObjectMapper()",
    "boolean EventLogger.isFailOnException()",
    "void EventLogger.setClock(Clock)",
    "void EventLogger.setListeners(List)",
    "void EventLogger.setObjectMapper(ObjectMapper)"
  })
  public void testGettersAndSetters() {
    // Arrange
    EventLogger eventLogger = new EventLogger();
    DefaultClockImpl clock = new DefaultClockImpl();

    // Act
    eventLogger.setClock(clock);
    ArrayList<EventLoggerListener> listeners = new ArrayList<>();
    eventLogger.setListeners(listeners);
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    eventLogger.setObjectMapper(objectMapper);
    Clock actualClock = eventLogger.getClock();
    List<EventLoggerListener> actualListeners = eventLogger.getListeners();
    ObjectMapper actualObjectMapper = eventLogger.getObjectMapper();

    // Assert
    assertFalse(eventLogger.isFailOnException());
    assertTrue(actualListeners.isEmpty());
    assertSame(listeners, actualListeners);
    assertSame(clock, actualClock);
    assertSame(objectMapper, actualObjectMapper);
  }
}
