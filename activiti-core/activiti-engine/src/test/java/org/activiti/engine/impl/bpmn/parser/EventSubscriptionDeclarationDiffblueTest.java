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
package org.activiti.engine.impl.bpmn.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class EventSubscriptionDeclarationDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link EventSubscriptionDeclaration#EventSubscriptionDeclaration(String, String)}
   *   <li>{@link EventSubscriptionDeclaration#setActivityId(String)}
   *   <li>{@link EventSubscriptionDeclaration#setAsync(boolean)}
   *   <li>{@link EventSubscriptionDeclaration#setConfiguration(String)}
   *   <li>{@link EventSubscriptionDeclaration#setStartEvent(boolean)}
   *   <li>{@link EventSubscriptionDeclaration#getActivityId()}
   *   <li>{@link EventSubscriptionDeclaration#getConfiguration()}
   *   <li>{@link EventSubscriptionDeclaration#getEventName()}
   *   <li>{@link EventSubscriptionDeclaration#getEventType()}
   *   <li>{@link EventSubscriptionDeclaration#isAsync()}
   *   <li>{@link EventSubscriptionDeclaration#isStartEvent()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    EventSubscriptionDeclaration actualEventSubscriptionDeclaration = new EventSubscriptionDeclaration("Event Name",
        "Event Type");
    actualEventSubscriptionDeclaration.setActivityId("42");
    actualEventSubscriptionDeclaration.setAsync(true);
    actualEventSubscriptionDeclaration.setConfiguration("Configuration");
    actualEventSubscriptionDeclaration.setStartEvent(true);
    String actualActivityId = actualEventSubscriptionDeclaration.getActivityId();
    String actualConfiguration = actualEventSubscriptionDeclaration.getConfiguration();
    String actualEventName = actualEventSubscriptionDeclaration.getEventName();
    String actualEventType = actualEventSubscriptionDeclaration.getEventType();
    boolean actualIsAsyncResult = actualEventSubscriptionDeclaration.isAsync();

    // Assert that nothing has changed
    assertEquals("42", actualActivityId);
    assertEquals("Configuration", actualConfiguration);
    assertEquals("Event Name", actualEventName);
    assertEquals("Event Type", actualEventType);
    assertTrue(actualIsAsyncResult);
    assertTrue(actualEventSubscriptionDeclaration.isStartEvent());
  }
}
