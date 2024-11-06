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
import java.util.ArrayList;
import java.util.List;
import org.activiti.api.process.model.events.ApplicationDeployedEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ApplicationDeployedEventsDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ApplicationDeployedEvents#ApplicationDeployedEvents(List)}
   *   <li>{@link ApplicationDeployedEvents#getApplicationDeployedEvents()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    ArrayList<ApplicationDeployedEvent> applicationDeployedEvents = new ArrayList<>();

    // Act
    List<ApplicationDeployedEvent> actualApplicationDeployedEvents = (new ApplicationDeployedEvents(
        applicationDeployedEvents)).getApplicationDeployedEvents();

    // Assert
    assertTrue(actualApplicationDeployedEvents.isEmpty());
    assertSame(applicationDeployedEvents, actualApplicationDeployedEvents);
  }
}
