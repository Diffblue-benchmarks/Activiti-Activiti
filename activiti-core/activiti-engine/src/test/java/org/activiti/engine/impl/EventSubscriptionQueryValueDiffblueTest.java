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
package org.activiti.engine.impl;

import static org.junit.Assert.assertEquals;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class EventSubscriptionQueryValueDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link EventSubscriptionQueryValue#EventSubscriptionQueryValue(String, String)}
   *   <li>{@link EventSubscriptionQueryValue#setEventName(String)}
   *   <li>{@link EventSubscriptionQueryValue#setEventType(String)}
   *   <li>{@link EventSubscriptionQueryValue#getEventName()}
   *   <li>{@link EventSubscriptionQueryValue#getEventType()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void EventSubscriptionQueryValue.<init>(String, String)",
      "String EventSubscriptionQueryValue.getEventName()", "String EventSubscriptionQueryValue.getEventType()",
      "void EventSubscriptionQueryValue.setEventName(String)", "void EventSubscriptionQueryValue.setEventType(String)"})
  public void testGettersAndSetters() {
    // Arrange and Act
    EventSubscriptionQueryValue actualEventSubscriptionQueryValue = new EventSubscriptionQueryValue("Event Name",
        "Event Type");
    actualEventSubscriptionQueryValue.setEventName("Event Name");
    actualEventSubscriptionQueryValue.setEventType("Event Type");
    String actualEventName = actualEventSubscriptionQueryValue.getEventName();

    // Assert
    assertEquals("Event Name", actualEventName);
    assertEquals("Event Type", actualEventSubscriptionQueryValue.getEventType());
  }
}
