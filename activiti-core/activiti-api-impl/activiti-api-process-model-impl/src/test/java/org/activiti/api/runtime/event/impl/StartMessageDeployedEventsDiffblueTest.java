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
package org.activiti.api.runtime.event.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.activiti.api.process.model.StartMessageDeploymentDefinition;
import org.activiti.api.process.model.events.StartMessageDeployedEvent;
import org.activiti.api.runtime.model.impl.ProcessDefinitionImpl;
import org.junit.jupiter.api.Test;

class StartMessageDeployedEventsDiffblueTest {
  /**
   * Method under test:
   * {@link StartMessageDeployedEvents#getStartMessageDeployedEvents()}
   */
  @Test
  void testGetStartMessageDeployedEvents() {
    // Arrange
    ArrayList<StartMessageDeployedEvent> processDeployedEvents = new ArrayList<>();

    // Act
    List<StartMessageDeployedEvent> actualStartMessageDeployedEvents = (new StartMessageDeployedEvents(
        processDeployedEvents)).getStartMessageDeployedEvents();

    // Assert
    assertTrue(actualStartMessageDeployedEvents.isEmpty());
    assertSame(processDeployedEvents, actualStartMessageDeployedEvents);
  }

  /**
   * Method under test:
   * {@link StartMessageDeployedEvents#getStartMessageDeployedEvents()}
   */
  @Test
  void testGetStartMessageDeployedEvents2() {
    // Arrange
    StartMessageDeploymentDefinition startMessageEventSubscription = mock(StartMessageDeploymentDefinition.class);
    when(startMessageEventSubscription.getProcessDefinition()).thenReturn(new ProcessDefinitionImpl());
    StartMessageDeployedEventImpl startMessageDeployedEventImpl = new StartMessageDeployedEventImpl(
        startMessageEventSubscription);

    ArrayList<StartMessageDeployedEvent> processDeployedEvents = new ArrayList<>();
    processDeployedEvents.add(startMessageDeployedEventImpl);

    // Act
    List<StartMessageDeployedEvent> actualStartMessageDeployedEvents = (new StartMessageDeployedEvents(
        processDeployedEvents)).getStartMessageDeployedEvents();

    // Assert
    verify(startMessageEventSubscription).getProcessDefinition();
    assertSame(processDeployedEvents, actualStartMessageDeployedEvents);
  }

  /**
   * Method under test:
   * {@link StartMessageDeployedEvents#StartMessageDeployedEvents(List)}
   */
  @Test
  void testNewStartMessageDeployedEvents() {
    // Arrange
    ArrayList<StartMessageDeployedEvent> processDeployedEvents = new ArrayList<>();

    // Act
    StartMessageDeployedEvents actualStartMessageDeployedEvents = new StartMessageDeployedEvents(processDeployedEvents);

    // Assert
    Object source = actualStartMessageDeployedEvents.getSource();
    assertTrue(source instanceof List);
    assertTrue(((List<Object>) source).isEmpty());
    assertSame(processDeployedEvents, source);
    assertSame(processDeployedEvents, actualStartMessageDeployedEvents.getStartMessageDeployedEvents());
  }

  /**
   * Method under test:
   * {@link StartMessageDeployedEvents#StartMessageDeployedEvents(List)}
   */
  @Test
  void testNewStartMessageDeployedEvents2() {
    // Arrange
    ArrayList<StartMessageDeployedEvent> processDeployedEvents = new ArrayList<>();
    processDeployedEvents.add(new StartMessageDeployedEventImpl());

    // Act
    StartMessageDeployedEvents actualStartMessageDeployedEvents = new StartMessageDeployedEvents(processDeployedEvents);

    // Assert
    assertSame(processDeployedEvents, actualStartMessageDeployedEvents.getSource());
    assertSame(processDeployedEvents, actualStartMessageDeployedEvents.getStartMessageDeployedEvents());
  }

  /**
   * Method under test:
   * {@link StartMessageDeployedEvents#StartMessageDeployedEvents(List)}
   */
  @Test
  void testNewStartMessageDeployedEvents3() {
    // Arrange
    ArrayList<StartMessageDeployedEvent> processDeployedEvents = new ArrayList<>();
    processDeployedEvents.add(new StartMessageDeployedEventImpl());
    processDeployedEvents.add(new StartMessageDeployedEventImpl());

    // Act
    StartMessageDeployedEvents actualStartMessageDeployedEvents = new StartMessageDeployedEvents(processDeployedEvents);

    // Assert
    assertSame(processDeployedEvents, actualStartMessageDeployedEvents.getSource());
    assertSame(processDeployedEvents, actualStartMessageDeployedEvents.getStartMessageDeployedEvents());
  }

  /**
   * Method under test:
   * {@link StartMessageDeployedEvents#StartMessageDeployedEvents(List)}
   */
  @Test
  void testNewStartMessageDeployedEvents4() {
    // Arrange
    StartMessageDeploymentDefinition startMessageEventSubscription = mock(StartMessageDeploymentDefinition.class);
    when(startMessageEventSubscription.getProcessDefinition()).thenReturn(new ProcessDefinitionImpl());
    StartMessageDeployedEventImpl startMessageDeployedEventImpl = new StartMessageDeployedEventImpl(
        startMessageEventSubscription);

    ArrayList<StartMessageDeployedEvent> processDeployedEvents = new ArrayList<>();
    processDeployedEvents.add(startMessageDeployedEventImpl);

    // Act
    StartMessageDeployedEvents actualStartMessageDeployedEvents = new StartMessageDeployedEvents(processDeployedEvents);

    // Assert
    verify(startMessageEventSubscription).getProcessDefinition();
    assertSame(processDeployedEvents, actualStartMessageDeployedEvents.getSource());
    assertSame(processDeployedEvents, actualStartMessageDeployedEvents.getStartMessageDeployedEvents());
  }
}
