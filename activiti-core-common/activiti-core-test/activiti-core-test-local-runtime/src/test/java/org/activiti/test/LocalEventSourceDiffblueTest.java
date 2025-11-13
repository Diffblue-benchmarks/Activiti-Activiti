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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.lang.Character.UnicodeScript;
import java.util.List;
import org.activiti.api.model.shared.event.RuntimeEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(MockitoExtension.class)
class LocalEventSourceDiffblueTest {
  @Mock private List<RuntimeEvent<?, ?>> list;

  @InjectMocks private LocalEventSource localEventSource;

  /**
   * Test {@link LocalEventSource#getEvents(Class)} with {@code eventType}.
   *
   * <ul>
   *   <li>Given {@link LocalEventSource} (default constructor).
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link LocalEventSource#getEvents(Class)}
   */
  @Test
  @DisplayName(
      "Test getEvents(Class) with 'eventType'; given LocalEventSource (default constructor); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List LocalEventSource.getEvents(Class)"})
  void testGetEventsWithEventType_givenLocalEventSource_thenReturnEmpty() {
    // Arrange
    LocalEventSource localEventSource = new LocalEventSource();
    Class<RuntimeEvent> forNameResult = RuntimeEvent.class;

    // Act and Assert
    assertTrue(
        localEventSource.getEvents((Class<RuntimeEvent<?, ?>>) (Class) forNameResult).isEmpty());
  }

  /**
   * Test {@link LocalEventSource#getEvents(Enum[])} with {@code eventTypes}.
   *
   * <p>Method under test: {@link LocalEventSource#getEvents(Object[])}
   */
  @Test
  @DisplayName("Test getEvents(Enum[]) with 'eventTypes'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List LocalEventSource.getEvents(java.lang.Enum[])"})
  void testGetEventsWithEventTypes() {
    // Arrange
    LocalEventSource localEventSource = new LocalEventSource();

    // Act and Assert
    assertTrue(localEventSource.getEvents(UnicodeScript.of(1)).isEmpty());
  }

  /**
   * Test {@link LocalEventSource#getTaskEvents()}.
   *
   * <p>Method under test: {@link LocalEventSource#getTaskEvents()}
   */
  @Test
  @DisplayName("Test getTaskEvents()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List LocalEventSource.getTaskEvents()"})
  void testGetTaskEvents() {
    // Arrange, Act and Assert
    assertTrue(new LocalEventSource().getTaskEvents().isEmpty());
  }

  /**
   * Test {@link LocalEventSource#getProcessInstanceEvents()}.
   *
   * <p>Method under test: {@link LocalEventSource#getProcessInstanceEvents()}
   */
  @Test
  @DisplayName("Test getProcessInstanceEvents()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List LocalEventSource.getProcessInstanceEvents()"})
  void testGetProcessInstanceEvents() {
    // Arrange, Act and Assert
    assertTrue(new LocalEventSource().getProcessInstanceEvents().isEmpty());
  }

  /**
   * Test {@link LocalEventSource#getTimerFiredEvents()}.
   *
   * <p>Method under test: {@link LocalEventSource#getTimerFiredEvents()}
   */
  @Test
  @DisplayName("Test getTimerFiredEvents()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List LocalEventSource.getTimerFiredEvents()"})
  void testGetTimerFiredEvents() {
    // Arrange, Act and Assert
    assertTrue(new LocalEventSource().getTimerFiredEvents().isEmpty());
  }

  /**
   * Test {@link LocalEventSource#getTimerScheduledEvents()}.
   *
   * <p>Method under test: {@link LocalEventSource#getTimerScheduledEvents()}
   */
  @Test
  @DisplayName("Test getTimerScheduledEvents()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List LocalEventSource.getTimerScheduledEvents()"})
  void testGetTimerScheduledEvents() {
    // Arrange, Act and Assert
    assertTrue(new LocalEventSource().getTimerScheduledEvents().isEmpty());
  }

  /**
   * Test {@link LocalEventSource#getTimerCancelledEvents()}.
   *
   * <p>Method under test: {@link LocalEventSource#getTimerCancelledEvents()}
   */
  @Test
  @DisplayName("Test getTimerCancelledEvents()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List LocalEventSource.getTimerCancelledEvents()"})
  void testGetTimerCancelledEvents() {
    // Arrange, Act and Assert
    assertTrue(new LocalEventSource().getTimerCancelledEvents().isEmpty());
  }

  /**
   * Test {@link LocalEventSource#clearEvents()}.
   *
   * <p>Method under test: {@link LocalEventSource#clearEvents()}
   */
  @Test
  @DisplayName("Test clearEvents()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void LocalEventSource.clearEvents()"})
  void testClearEvents() {
    // Arrange
    doNothing().when(list).clear();

    // Act
    localEventSource.clearEvents();

    // Assert
    verify(list).clear();
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link LocalEventSource}
   *   <li>{@link LocalEventSource#getEvents()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void LocalEventSource.<init>()", "List LocalEventSource.getEvents()"})
  void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertTrue(new LocalEventSource().getEvents().isEmpty());
  }
}
