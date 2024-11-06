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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StartMessageDeployedEventsDiffblueTest {
  /**
   * Test {@link StartMessageDeployedEvents#StartMessageDeployedEvents(List)}.
   * <p>
   * Method under test:
   * {@link StartMessageDeployedEvents#StartMessageDeployedEvents(List)}
   */
  @Test
  @DisplayName("Test new StartMessageDeployedEvents(List)")
  void testNewStartMessageDeployedEvents() {
    // Arrange
    ArrayList<StartMessageDeployedEvent> processDeployedEvents = new ArrayList<>();
    StartMessageDeployedEventImpl startMessageDeployedEventImpl = new StartMessageDeployedEventImpl();
    processDeployedEvents.add(startMessageDeployedEventImpl);

    // Act and Assert
    Object source = (new StartMessageDeployedEvents(processDeployedEvents)).getSource();
    assertTrue(source instanceof List);
    assertEquals(1, ((List<StartMessageDeployedEventImpl>) source).size());
    assertSame(startMessageDeployedEventImpl, ((List<StartMessageDeployedEventImpl>) source).get(0));
  }

  /**
   * Test {@link StartMessageDeployedEvents#StartMessageDeployedEvents(List)}.
   * <p>
   * Method under test:
   * {@link StartMessageDeployedEvents#StartMessageDeployedEvents(List)}
   */
  @Test
  @DisplayName("Test new StartMessageDeployedEvents(List)")
  void testNewStartMessageDeployedEvents2() {
    // Arrange
    StartMessageDeploymentDefinition entity = mock(StartMessageDeploymentDefinition.class);
    when(entity.getProcessDefinition()).thenReturn(new ProcessDefinitionImpl());
    StartMessageDeployedEventImpl buildResult = StartMessageDeployedEventImpl.builder().withEntity(entity).build();

    ArrayList<StartMessageDeployedEvent> processDeployedEvents = new ArrayList<>();
    processDeployedEvents.add(buildResult);

    // Act
    StartMessageDeployedEvents actualStartMessageDeployedEvents = new StartMessageDeployedEvents(processDeployedEvents);

    // Assert
    verify(entity).getProcessDefinition();
    Object source = actualStartMessageDeployedEvents.getSource();
    assertTrue(source instanceof List);
    assertEquals(1, ((List<StartMessageDeployedEventImpl>) source).size());
    assertEquals(0, ((List<StartMessageDeployedEventImpl>) source).get(0).getProcessDefinitionVersion().intValue());
  }

  /**
   * Test {@link StartMessageDeployedEvents#StartMessageDeployedEvents(List)}.
   * <ul>
   *   <li>Then return Source size is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartMessageDeployedEvents#StartMessageDeployedEvents(List)}
   */
  @Test
  @DisplayName("Test new StartMessageDeployedEvents(List); then return Source size is two")
  void testNewStartMessageDeployedEvents_thenReturnSourceSizeIsTwo() {
    // Arrange
    ArrayList<StartMessageDeployedEvent> processDeployedEvents = new ArrayList<>();
    processDeployedEvents.add(new StartMessageDeployedEventImpl());
    StartMessageDeployedEventImpl startMessageDeployedEventImpl = new StartMessageDeployedEventImpl();
    processDeployedEvents.add(startMessageDeployedEventImpl);

    // Act and Assert
    Object source = (new StartMessageDeployedEvents(processDeployedEvents)).getSource();
    assertTrue(source instanceof List);
    assertEquals(2, ((List<StartMessageDeployedEventImpl>) source).size());
    assertSame(startMessageDeployedEventImpl, ((List<StartMessageDeployedEventImpl>) source).get(1));
  }

  /**
   * Test {@link StartMessageDeployedEvents#StartMessageDeployedEvents(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Source Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartMessageDeployedEvents#StartMessageDeployedEvents(List)}
   */
  @Test
  @DisplayName("Test new StartMessageDeployedEvents(List); when ArrayList(); then return Source Empty")
  void testNewStartMessageDeployedEvents_whenArrayList_thenReturnSourceEmpty() {
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
   * Test {@link StartMessageDeployedEvents#getStartMessageDeployedEvents()}.
   * <ul>
   *   <li>Then return {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartMessageDeployedEvents#getStartMessageDeployedEvents()}
   */
  @Test
  @DisplayName("Test getStartMessageDeployedEvents(); then return ArrayList()")
  void testGetStartMessageDeployedEvents_thenReturnArrayList() {
    // Arrange
    StartMessageDeploymentDefinition entity = mock(StartMessageDeploymentDefinition.class);
    when(entity.getProcessDefinition()).thenReturn(new ProcessDefinitionImpl());
    StartMessageDeployedEventImpl buildResult = StartMessageDeployedEventImpl.builder().withEntity(entity).build();

    ArrayList<StartMessageDeployedEvent> processDeployedEvents = new ArrayList<>();
    processDeployedEvents.add(buildResult);

    // Act
    List<StartMessageDeployedEvent> actualStartMessageDeployedEvents = (new StartMessageDeployedEvents(
        processDeployedEvents)).getStartMessageDeployedEvents();

    // Assert
    verify(entity).getProcessDefinition();
    assertSame(processDeployedEvents, actualStartMessageDeployedEvents);
  }

  /**
   * Test {@link StartMessageDeployedEvents#getStartMessageDeployedEvents()}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartMessageDeployedEvents#getStartMessageDeployedEvents()}
   */
  @Test
  @DisplayName("Test getStartMessageDeployedEvents(); then return Empty")
  void testGetStartMessageDeployedEvents_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue((new StartMessageDeployedEvents(new ArrayList<>())).getStartMessageDeployedEvents().isEmpty());
  }
}
