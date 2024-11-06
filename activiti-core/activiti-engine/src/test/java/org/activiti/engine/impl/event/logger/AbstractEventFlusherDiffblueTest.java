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
   * Test {@link AbstractEventFlusher#getEventHandlers()}.
   * <p>
   * Method under test: {@link AbstractEventFlusher#getEventHandlers()}
   */
  @Test
  public void testGetEventHandlers() {
    // Arrange, Act and Assert
    assertTrue((new DatabaseEventFlusher()).getEventHandlers().isEmpty());
  }

  /**
   * Test {@link AbstractEventFlusher#setEventHandlers(List)}.
   * <ul>
   *   <li>Given {@link ActivityCompensatedEventHandler} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEventFlusher#setEventHandlers(List)}
   */
  @Test
  public void testSetEventHandlers_givenActivityCompensatedEventHandler() {
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
   * Test {@link AbstractEventFlusher#setEventHandlers(List)}.
   * <ul>
   *   <li>Given {@link ActivityCompensatedEventHandler} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEventFlusher#setEventHandlers(List)}
   */
  @Test
  public void testSetEventHandlers_givenActivityCompensatedEventHandler2() {
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
   * Test {@link AbstractEventFlusher#setEventHandlers(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractEventFlusher#setEventHandlers(List)}
   */
  @Test
  public void testSetEventHandlers_whenArrayList() {
    // Arrange
    DatabaseEventFlusher databaseEventFlusher = new DatabaseEventFlusher();
    ArrayList<EventLoggerEventHandler> eventHandlers = new ArrayList<>();

    // Act
    databaseEventFlusher.setEventHandlers(eventHandlers);

    // Assert
    assertSame(eventHandlers, databaseEventFlusher.getEventHandlers());
  }

  /**
   * Test {@link AbstractEventFlusher#addEventHandler(EventLoggerEventHandler)}.
   * <ul>
   *   <li>Given {@link Date}.</li>
   *   <li>When {@link ActivityCompensatedEventHandler} (default constructor)
   * TimeStamp is {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractEventFlusher#addEventHandler(EventLoggerEventHandler)}
   */
  @Test
  public void testAddEventHandler_givenDate_whenActivityCompensatedEventHandlerTimeStampIsDate() {
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

  /**
   * Test {@link AbstractEventFlusher#addEventHandler(EventLoggerEventHandler)}.
   * <ul>
   *   <li>When {@link ActivityCompensatedEventHandler} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractEventFlusher#addEventHandler(EventLoggerEventHandler)}
   */
  @Test
  public void testAddEventHandler_whenActivityCompensatedEventHandler() {
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
}
