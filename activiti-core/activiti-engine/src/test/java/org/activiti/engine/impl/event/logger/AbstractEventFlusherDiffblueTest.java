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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.impl.event.logger.handler.ActivityCompensatedEventHandler;
import org.activiti.engine.impl.event.logger.handler.EventLoggerEventHandler;
import org.junit.Test;

public class AbstractEventFlusherDiffblueTest {
  /**
   * Method under test: {@link AbstractEventFlusher#getEventHandlers()}
   */
  @Test
  public void testGetEventHandlers() {
    // Arrange
    DatabaseEventFlusher databaseEventFlusher = new DatabaseEventFlusher();

    // Act
    List<EventLoggerEventHandler> actualEventHandlers = databaseEventFlusher.getEventHandlers();

    // Assert
    assertTrue(actualEventHandlers.isEmpty());
    assertSame(databaseEventFlusher.eventHandlers, actualEventHandlers);
  }

  /**
   * Method under test: {@link AbstractEventFlusher#setEventHandlers(List)}
   */
  @Test
  public void testSetEventHandlers() {
    // Arrange
    DatabaseEventFlusher databaseEventFlusher = new DatabaseEventFlusher();
    ArrayList<EventLoggerEventHandler> eventHandlers = new ArrayList<>();

    // Act
    databaseEventFlusher.setEventHandlers(eventHandlers);

    // Assert
    assertSame(eventHandlers, databaseEventFlusher.getEventHandlers());
  }

  /**
   * Method under test: {@link AbstractEventFlusher#setEventHandlers(List)}
   */
  @Test
  public void testSetEventHandlers2() {
    // Arrange
    DatabaseEventFlusher databaseEventFlusher = new DatabaseEventFlusher();

    ArrayList<EventLoggerEventHandler> eventHandlers = new ArrayList<>();
    eventHandlers.add(new ActivityCompensatedEventHandler());

    // Act
    databaseEventFlusher.setEventHandlers(eventHandlers);

    // Assert
    assertSame(eventHandlers, databaseEventFlusher.getEventHandlers());
  }

  /**
   * Method under test: {@link AbstractEventFlusher#setEventHandlers(List)}
   */
  @Test
  public void testSetEventHandlers3() {
    // Arrange
    DatabaseEventFlusher databaseEventFlusher = new DatabaseEventFlusher();

    ArrayList<EventLoggerEventHandler> eventHandlers = new ArrayList<>();
    eventHandlers.add(new ActivityCompensatedEventHandler());
    eventHandlers.add(new ActivityCompensatedEventHandler());

    // Act
    databaseEventFlusher.setEventHandlers(eventHandlers);

    // Assert
    assertSame(eventHandlers, databaseEventFlusher.getEventHandlers());
  }

  /**
   * Method under test:
   * {@link AbstractEventFlusher#addEventHandler(EventLoggerEventHandler)}
   */
  @Test
  public void testAddEventHandler() {
    // Arrange
    DatabaseEventFlusher databaseEventFlusher = new DatabaseEventFlusher();
    ActivityCompensatedEventHandler databaseEventLoggerEventHandler = new ActivityCompensatedEventHandler();

    // Act
    databaseEventFlusher.addEventHandler(databaseEventLoggerEventHandler);

    // Assert
    List<EventLoggerEventHandler> eventHandlers = databaseEventFlusher.getEventHandlers();
    assertEquals(1, eventHandlers.size());
    assertSame(databaseEventLoggerEventHandler, eventHandlers.get(0));
  }

  /**
   * Method under test:
   * {@link AbstractEventFlusher#addEventHandler(EventLoggerEventHandler)}
   */
  @Test
  public void testAddEventHandler2() {
    // Arrange
    DatabaseEventFlusher databaseEventFlusher = new DatabaseEventFlusher();

    ActivityCompensatedEventHandler databaseEventLoggerEventHandler = new ActivityCompensatedEventHandler();
    databaseEventLoggerEventHandler.setTimeStamp(mock(Date.class));

    // Act
    databaseEventFlusher.addEventHandler(databaseEventLoggerEventHandler);

    // Assert
    List<EventLoggerEventHandler> eventHandlers = databaseEventFlusher.getEventHandlers();
    assertEquals(1, eventHandlers.size());
    assertSame(databaseEventLoggerEventHandler, eventHandlers.get(0));
  }
}
