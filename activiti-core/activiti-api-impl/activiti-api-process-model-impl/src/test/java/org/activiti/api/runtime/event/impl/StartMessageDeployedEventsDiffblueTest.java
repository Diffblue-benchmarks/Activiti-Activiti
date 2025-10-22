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
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.activiti.api.process.model.events.StartMessageDeployedEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class StartMessageDeployedEventsDiffblueTest {
  /**
   * Test {@link StartMessageDeployedEvents#StartMessageDeployedEvents(List)}.
   * <ul>
   *   <li>Then return Source size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link StartMessageDeployedEvents#StartMessageDeployedEvents(List)}
   */
  @Test
  @DisplayName("Test new StartMessageDeployedEvents(List); then return Source size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void StartMessageDeployedEvents.<init>(List)"})
  void testNewStartMessageDeployedEvents_thenReturnSourceSizeIsOne() {
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
   * <ul>
   *   <li>Then return Source size is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link StartMessageDeployedEvents#StartMessageDeployedEvents(List)}
   */
  @Test
  @DisplayName("Test new StartMessageDeployedEvents(List); then return Source size is two")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void StartMessageDeployedEvents.<init>(List)"})
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
   * Method under test: {@link StartMessageDeployedEvents#StartMessageDeployedEvents(List)}
   */
  @Test
  @DisplayName("Test new StartMessageDeployedEvents(List); when ArrayList(); then return Source Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void StartMessageDeployedEvents.<init>(List)"})
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
   * <p>
   * Method under test: {@link StartMessageDeployedEvents#getStartMessageDeployedEvents()}
   */
  @Test
  @DisplayName("Test getStartMessageDeployedEvents()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List StartMessageDeployedEvents.getStartMessageDeployedEvents()"})
  void testGetStartMessageDeployedEvents() {
    // Arrange, Act and Assert
    assertTrue((new StartMessageDeployedEvents(new ArrayList<>())).getStartMessageDeployedEvents().isEmpty());
  }
}
