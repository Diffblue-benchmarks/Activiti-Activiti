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
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import org.junit.Test;

public class EventListenerDiffblueTest {
  /**
   * Test {@link EventListener#clone()}.
   * <ul>
   *   <li>Given {@link EventListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link EventListener#clone()}
   */
  @Test
  public void testClone_givenEventListener() {
    // Arrange and Act
    EventListener actualCloneResult = (new EventListener()).clone();

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
   * Test {@link EventListener#clone()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventListener#clone()}
   */
  @Test
  public void testClone_givenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.computeIfPresent("foo", mock(BiFunction.class));

    EventListener eventListener = new EventListener();
    eventListener.setExtensionElements(extensionElements);

    // Act
    EventListener actualCloneResult = eventListener.clone();

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
   * Test {@link EventListener#setValues(EventListener)} with
   * {@code otherListener}.
   * <ul>
   *   <li>Then calls {@link ExtensionElement#getName()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventListener#setValues(EventListener)}
   */
  @Test
  public void testSetValuesWithOtherListener_thenCallsGetName() {
    // Arrange
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getName()).thenReturn("Name");

    EventListener eventListener = new EventListener();
    eventListener.addExtensionElement(extensionElement);

    // Act
    eventListener.setValues(new EventListener());

    // Assert
    verify(extensionElement, atLeast(1)).getName();
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
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

    // Assert that nothing has changed
    assertEquals("Entity Type", actualEntityType);
    assertEquals("Events", actualEvents);
    assertEquals("Implementation Type", actualEventListener.getImplementationType());
    assertEquals("Implementation", actualImplementation);
    assertEquals(0, actualEventListener.getXmlColumnNumber());
    assertEquals(0, actualEventListener.getXmlRowNumber());
    assertTrue(actualEventListener.getAttributes().isEmpty());
    assertTrue(actualEventListener.getExtensionElements().isEmpty());
  }
}
