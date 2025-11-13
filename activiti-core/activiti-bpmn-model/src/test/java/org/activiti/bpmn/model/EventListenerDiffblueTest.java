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
package org.activiti.bpmn.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class EventListenerDiffblueTest {
  /**
   * Test {@link EventListener#clone()}.
   *
   * <p>Method under test: {@link EventListener#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"EventListener EventListener.clone()"})
  public void testClone() {
    // Arrange and Act
    EventListener actualCloneResult = new EventListener().clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getEntityType());
    assertNull(actualCloneResult.getEvents());
    assertNull(actualCloneResult.getImplementation());
    assertNull(actualCloneResult.getImplementationType());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link EventListener}
   *   <li>{@link EventListener#setEntityType(String)}
   *   <li>{@link EventListener#setEvents(String)}
   *   <li>{@link EventListener#setImplementation(String)}
   *   <li>{@link EventListener#setImplementationType(String)}
   *   <li>{@link EventListener#getEntityType()}
   *   <li>{@link EventListener#getEvents()}
   *   <li>{@link EventListener#getImplementation()}
   *   <li>{@link EventListener#getImplementationType()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void EventListener.<init>()",
    "String EventListener.getEntityType()",
    "String EventListener.getEvents()",
    "String EventListener.getImplementation()",
    "String EventListener.getImplementationType()",
    "void EventListener.setEntityType(String)",
    "void EventListener.setEvents(String)",
    "void EventListener.setImplementation(String)",
    "void EventListener.setImplementationType(String)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    EventListener actualEventListener = new EventListener();
    actualEventListener.setEntityType("Entity Type");
    actualEventListener.setEvents("Events");
    actualEventListener.setImplementation("Implementation");
    actualEventListener.setImplementationType("Implementation Type");
    String actualEntityType = actualEventListener.getEntityType();
    String actualEvents = actualEventListener.getEvents();
    String actualImplementation = actualEventListener.getImplementation();

    // Assert
    assertEquals("Entity Type", actualEntityType);
    assertEquals("Events", actualEvents);
    assertEquals("Implementation Type", actualEventListener.getImplementationType());
    assertEquals("Implementation", actualImplementation);
    assertNull(actualEventListener.getId());
    assertEquals(0, actualEventListener.getXmlColumnNumber());
    assertEquals(0, actualEventListener.getXmlRowNumber());
    assertTrue(actualEventListener.getAttributes().isEmpty());
    assertTrue(actualEventListener.getExtensionElements().isEmpty());
  }
}
