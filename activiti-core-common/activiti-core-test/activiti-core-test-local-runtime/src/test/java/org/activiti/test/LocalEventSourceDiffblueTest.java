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
package org.activiti.test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import org.activiti.api.model.shared.event.RuntimeEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {LocalEventSource.class})
@ExtendWith(SpringExtension.class)
class LocalEventSourceDiffblueTest {
  @Autowired
  private LocalEventSource localEventSource;

  /**
   * Test {@link LocalEventSource#getEvents(Class)} with {@code eventType}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link LocalEventSource#getEvents(Class)}
   */
  @Test
  @DisplayName("Test getEvents(Class) with 'eventType'; then return Empty")
  void testGetEventsWithEventType_thenReturnEmpty() {
    // Arrange
    Class<RuntimeEvent> forNameResult = RuntimeEvent.class;

    // Act and Assert
    assertTrue(localEventSource.getEvents((Class<RuntimeEvent<?, ?>>) (Class) forNameResult).isEmpty());
  }

  /**
   * Test {@link LocalEventSource#getEvents(Enum[])} with {@code eventTypes}.
   * <p>
   * Method under test: {@link LocalEventSource#getEvents(Object[])}
   */
  @Test
  @DisplayName("Test getEvents(Enum[]) with 'eventTypes'")
  void testGetEventsWithEventTypes() {
    // Arrange, Act and Assert
    assertTrue(localEventSource.getEvents(Character.UnicodeScript.of(1)).isEmpty());
  }

  /**
   * Test {@link LocalEventSource#getTaskEvents()}.
   * <p>
   * Method under test: {@link LocalEventSource#getTaskEvents()}
   */
  @Test
  @DisplayName("Test getTaskEvents()")
  void testGetTaskEvents() {
    // Arrange, Act and Assert
    assertTrue(localEventSource.getTaskEvents().isEmpty());
  }

  /**
   * Test {@link LocalEventSource#getProcessInstanceEvents()}.
   * <p>
   * Method under test: {@link LocalEventSource#getProcessInstanceEvents()}
   */
  @Test
  @DisplayName("Test getProcessInstanceEvents()")
  void testGetProcessInstanceEvents() {
    // Arrange, Act and Assert
    assertTrue(localEventSource.getProcessInstanceEvents().isEmpty());
  }

  /**
   * Test {@link LocalEventSource#getTimerFiredEvents()}.
   * <p>
   * Method under test: {@link LocalEventSource#getTimerFiredEvents()}
   */
  @Test
  @DisplayName("Test getTimerFiredEvents()")
  void testGetTimerFiredEvents() {
    // Arrange, Act and Assert
    assertTrue(localEventSource.getTimerFiredEvents().isEmpty());
  }

  /**
   * Test {@link LocalEventSource#getTimerScheduledEvents()}.
   * <p>
   * Method under test: {@link LocalEventSource#getTimerScheduledEvents()}
   */
  @Test
  @DisplayName("Test getTimerScheduledEvents()")
  void testGetTimerScheduledEvents() {
    // Arrange, Act and Assert
    assertTrue(localEventSource.getTimerScheduledEvents().isEmpty());
  }

  /**
   * Test {@link LocalEventSource#getTimerCancelledEvents()}.
   * <p>
   * Method under test: {@link LocalEventSource#getTimerCancelledEvents()}
   */
  @Test
  @DisplayName("Test getTimerCancelledEvents()")
  void testGetTimerCancelledEvents() {
    // Arrange, Act and Assert
    assertTrue(localEventSource.getTimerCancelledEvents().isEmpty());
  }

  /**
   * Test {@link LocalEventSource#clearEvents()}.
   * <ul>
   *   <li>Given {@link LocalEventSource} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link LocalEventSource#clearEvents()}
   */
  @Test
  @DisplayName("Test clearEvents(); given LocalEventSource (default constructor)")
  void testClearEvents_givenLocalEventSource() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    LocalEventSource localEventSource = new LocalEventSource();

    // Act
    localEventSource.clearEvents();

    // Assert
    assertTrue(localEventSource.getEvents().isEmpty());
  }

  /**
   * Test {@link LocalEventSource#clearEvents()}.
   * <ul>
   *   <li>Given {@link LocalEventSource} (default constructor) addCollectedEvents
   * {@link RuntimeEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LocalEventSource#clearEvents()}
   */
  @Test
  @DisplayName("Test clearEvents(); given LocalEventSource (default constructor) addCollectedEvents RuntimeEvent")
  void testClearEvents_givenLocalEventSourceAddCollectedEventsRuntimeEvent() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    LocalEventSource localEventSource = new LocalEventSource();
    localEventSource.addCollectedEvents(mock(RuntimeEvent.class));

    // Act
    localEventSource.clearEvents();

    // Assert
    assertTrue(localEventSource.getEvents().isEmpty());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link LocalEventSource}
   *   <li>{@link LocalEventSource#getEvents()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertTrue((new LocalEventSource()).getEvents().isEmpty());
  }
}
