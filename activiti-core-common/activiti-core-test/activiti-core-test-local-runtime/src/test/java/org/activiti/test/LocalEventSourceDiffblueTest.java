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
   * Method under test: {@link LocalEventSource#getEvents(Class)}
   */
  @Test
  void testGetEvents() {
    // Arrange
    Class<RuntimeEvent> forNameResult = RuntimeEvent.class;

    // Act and Assert
    assertTrue(localEventSource.getEvents((Class<RuntimeEvent<?, ?>>) (Class) forNameResult).isEmpty());
  }

  /**
   * Method under test: {@link LocalEventSource#getEvents(Object[])}
   */
  @Test
  void testGetEvents2() {
    // Arrange, Act and Assert
    assertTrue(localEventSource.getEvents(Character.UnicodeScript.of(1)).isEmpty());
  }

  /**
   * Method under test: {@link LocalEventSource#getTaskEvents()}
   */
  @Test
  void testGetTaskEvents() {
    // Arrange, Act and Assert
    assertTrue(localEventSource.getTaskEvents().isEmpty());
  }

  /**
   * Method under test: {@link LocalEventSource#getProcessInstanceEvents()}
   */
  @Test
  void testGetProcessInstanceEvents() {
    // Arrange, Act and Assert
    assertTrue(localEventSource.getProcessInstanceEvents().isEmpty());
  }

  /**
   * Method under test: {@link LocalEventSource#getTimerFiredEvents()}
   */
  @Test
  void testGetTimerFiredEvents() {
    // Arrange, Act and Assert
    assertTrue(localEventSource.getTimerFiredEvents().isEmpty());
  }

  /**
   * Method under test: {@link LocalEventSource#getTimerScheduledEvents()}
   */
  @Test
  void testGetTimerScheduledEvents() {
    // Arrange, Act and Assert
    assertTrue(localEventSource.getTimerScheduledEvents().isEmpty());
  }

  /**
   * Method under test: {@link LocalEventSource#getTimerCancelledEvents()}
   */
  @Test
  void testGetTimerCancelledEvents() {
    // Arrange, Act and Assert
    assertTrue(localEventSource.getTimerCancelledEvents().isEmpty());
  }

  /**
   * Method under test: {@link LocalEventSource#clearEvents()}
   */
  @Test
  void testClearEvents() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    LocalEventSource localEventSource = new LocalEventSource();

    // Act
    localEventSource.clearEvents();

    // Assert
    assertTrue(localEventSource.getEvents().isEmpty());
    assertTrue(localEventSource.getProcessInstanceEvents().isEmpty());
    assertTrue(localEventSource.getTaskEvents().isEmpty());
    assertTrue(localEventSource.getTimerCancelledEvents().isEmpty());
    assertTrue(localEventSource.getTimerFiredEvents().isEmpty());
    assertTrue(localEventSource.getTimerScheduledEvents().isEmpty());
  }

  /**
   * Method under test: {@link LocalEventSource#clearEvents()}
   */
  @Test
  void testClearEvents2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    LocalEventSource localEventSource = new LocalEventSource();
    localEventSource.addCollectedEvents(mock(RuntimeEvent.class));

    // Act
    localEventSource.clearEvents();

    // Assert
    assertTrue(localEventSource.getEvents().isEmpty());
    assertTrue(localEventSource.getProcessInstanceEvents().isEmpty());
    assertTrue(localEventSource.getTaskEvents().isEmpty());
    assertTrue(localEventSource.getTimerCancelledEvents().isEmpty());
    assertTrue(localEventSource.getTimerFiredEvents().isEmpty());
    assertTrue(localEventSource.getTimerScheduledEvents().isEmpty());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link LocalEventSource}
   *   <li>{@link LocalEventSource#getEvents()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertTrue((new LocalEventSource()).getEvents().isEmpty());
  }
}
