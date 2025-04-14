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
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.impl.event.logger.handler.ActivityCompensatedEventHandler;
import org.activiti.engine.impl.event.logger.handler.EventLoggerEventHandler;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AbstractEventFlusherDiffblueTest {
  /**
   * Test {@link AbstractEventFlusher#getEventHandlers()}.
   * <p>
   * Method under test: {@link AbstractEventFlusher#getEventHandlers()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AbstractEventFlusher.getEventHandlers()"})
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractEventFlusher.setEventHandlers(List)"})
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractEventFlusher.setEventHandlers(List)"})
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractEventFlusher.setEventHandlers(List)"})
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
   * <p>
   * Method under test: {@link AbstractEventFlusher#addEventHandler(EventLoggerEventHandler)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractEventFlusher.addEventHandler(EventLoggerEventHandler)"})
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
}
