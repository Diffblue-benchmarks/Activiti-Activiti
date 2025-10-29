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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;

public class EventDiffblueTest {
  /**
   * Method under test: {@link Event#getEventDefinitions()}
   */
  @Test
  public void testGetEventDefinitions() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();

    // Act
    List<EventDefinition> actualEventDefinitions = boundaryEvent.getEventDefinitions();

    // Assert
    assertTrue(actualEventDefinitions.isEmpty());
    assertSame(boundaryEvent.eventDefinitions, actualEventDefinitions);
  }

  /**
   * Method under test: {@link Event#setEventDefinitions(List)}
   */
  @Test
  public void testSetEventDefinitions() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();

    // Act
    boundaryEvent.setEventDefinitions(eventDefinitions);

    // Assert
    assertTrue(boundaryEvent.getIncomingFlows().isEmpty());
    assertTrue(boundaryEvent.getOutgoingFlows().isEmpty());
    assertSame(eventDefinitions, boundaryEvent.getEventDefinitions());
  }

  /**
   * Method under test: {@link Event#setEventDefinitions(List)}
   */
  @Test
  public void testSetEventDefinitions2() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());

    // Act
    boundaryEvent.setEventDefinitions(eventDefinitions);

    // Assert
    assertTrue(boundaryEvent.getIncomingFlows().isEmpty());
    assertTrue(boundaryEvent.getOutgoingFlows().isEmpty());
    assertSame(eventDefinitions, boundaryEvent.getEventDefinitions());
  }

  /**
   * Method under test: {@link Event#setEventDefinitions(List)}
   */
  @Test
  public void testSetEventDefinitions3() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());
    eventDefinitions.add(new CancelEventDefinition());

    // Act
    boundaryEvent.setEventDefinitions(eventDefinitions);

    // Assert
    assertTrue(boundaryEvent.getIncomingFlows().isEmpty());
    assertTrue(boundaryEvent.getOutgoingFlows().isEmpty());
    assertSame(eventDefinitions, boundaryEvent.getEventDefinitions());
  }

  /**
   * Method under test: {@link Event#addEventDefinition(EventDefinition)}
   */
  @Test
  public void testAddEventDefinition() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    CancelEventDefinition eventDefinition = new CancelEventDefinition();

    // Act
    boundaryEvent.addEventDefinition(eventDefinition);

    // Assert
    List<EventDefinition> eventDefinitions = boundaryEvent.getEventDefinitions();
    assertEquals(1, eventDefinitions.size());
    assertTrue(boundaryEvent.getIncomingFlows().isEmpty());
    assertTrue(boundaryEvent.getOutgoingFlows().isEmpty());
    assertSame(eventDefinition, eventDefinitions.get(0));
  }

  /**
   * Method under test: {@link Event#setValues(Event)}
   */
  @Test
  public void testSetValues() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    BoundaryEvent otherEvent = new BoundaryEvent();

    // Act
    boundaryEvent.setValues((Event) otherEvent);

    // Assert
    assertNull(otherEvent.getId());
    assertNull(otherEvent.getDocumentation());
    assertNull(otherEvent.getName());
    assertFalse(otherEvent.isAsynchronous());
    assertFalse(otherEvent.isNotExclusive());
    assertTrue(otherEvent.getEventDefinitions().isEmpty());
    assertTrue(otherEvent.isExclusive());
  }

  /**
   * Method under test: {@link Event#setValues(Event)}
   */
  @Test
  public void testSetValues2() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();

    BoundaryEvent otherEvent = new BoundaryEvent();
    otherEvent.setEventDefinitions(null);

    // Act
    boundaryEvent.setValues((Event) otherEvent);

    // Assert
    assertNull(otherEvent.getId());
    assertNull(otherEvent.getDocumentation());
    assertNull(otherEvent.getName());
    assertNull(otherEvent.getEventDefinitions());
    assertFalse(otherEvent.isAsynchronous());
    assertFalse(otherEvent.isNotExclusive());
    assertTrue(otherEvent.isExclusive());
  }

  /**
   * Method under test: {@link Event#setValues(Event)}
   */
  @Test
  public void testSetValues3() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());

    BoundaryEvent otherEvent = new BoundaryEvent();
    otherEvent.setEventDefinitions(eventDefinitions);

    // Act
    boundaryEvent.setValues((Event) otherEvent);

    // Assert
    assertNull(otherEvent.getId());
    assertNull(otherEvent.getDocumentation());
    assertNull(otherEvent.getName());
    assertFalse(otherEvent.isAsynchronous());
    assertFalse(otherEvent.isNotExclusive());
    assertTrue(otherEvent.isExclusive());
    assertSame(eventDefinitions, otherEvent.getEventDefinitions());
  }

  /**
   * Method under test: {@link Event#setValues(Event)}
   */
  @Test
  public void testSetValues4() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    EndEvent otherEvent = mock(EndEvent.class);
    when(otherEvent.isAsynchronous()).thenReturn(true);
    when(otherEvent.isNotExclusive()).thenReturn(true);
    when(otherEvent.getId()).thenReturn("42");
    when(otherEvent.getDocumentation()).thenReturn("Documentation");
    when(otherEvent.getName()).thenReturn("Name");
    when(otherEvent.getEventDefinitions()).thenReturn(new ArrayList<>());
    when(otherEvent.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherEvent.getAttributes()).thenReturn(new HashMap<>());
    when(otherEvent.getExtensionElements()).thenReturn(new HashMap<>());

    // Act
    boundaryEvent.setValues(otherEvent);

    // Assert
    verify(otherEvent, atLeast(1)).getAttributes();
    verify(otherEvent, atLeast(1)).getExtensionElements();
    verify(otherEvent).getId();
    verify(otherEvent, atLeast(1)).getEventDefinitions();
    verify(otherEvent).getDocumentation();
    verify(otherEvent, atLeast(1)).getExecutionListeners();
    verify(otherEvent).getName();
    verify(otherEvent).isAsynchronous();
    verify(otherEvent).isNotExclusive();
    assertEquals("42", boundaryEvent.getId());
    assertEquals("Documentation", boundaryEvent.getDocumentation());
    assertEquals("Name", boundaryEvent.getName());
    assertFalse(boundaryEvent.isExclusive());
    assertTrue(boundaryEvent.getEventDefinitions().isEmpty());
    assertTrue(boundaryEvent.isAsynchronous());
    assertTrue(boundaryEvent.isNotExclusive());
  }

  /**
   * Method under test: {@link Event#setValues(Event)}
   */
  @Test
  public void testSetValues5() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();

    ArrayList<EventDefinition> eventDefinitionList = new ArrayList<>();
    eventDefinitionList.add(new CancelEventDefinition());
    EndEvent otherEvent = mock(EndEvent.class);
    when(otherEvent.isAsynchronous()).thenReturn(true);
    when(otherEvent.isNotExclusive()).thenReturn(true);
    when(otherEvent.getId()).thenReturn("42");
    when(otherEvent.getDocumentation()).thenReturn("Documentation");
    when(otherEvent.getName()).thenReturn("Name");
    when(otherEvent.getEventDefinitions()).thenReturn(eventDefinitionList);
    when(otherEvent.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherEvent.getAttributes()).thenReturn(new HashMap<>());
    when(otherEvent.getExtensionElements()).thenReturn(new HashMap<>());

    // Act
    boundaryEvent.setValues(otherEvent);

    // Assert
    verify(otherEvent, atLeast(1)).getAttributes();
    verify(otherEvent, atLeast(1)).getExtensionElements();
    verify(otherEvent).getId();
    verify(otherEvent, atLeast(1)).getEventDefinitions();
    verify(otherEvent).getDocumentation();
    verify(otherEvent, atLeast(1)).getExecutionListeners();
    verify(otherEvent).getName();
    verify(otherEvent).isAsynchronous();
    verify(otherEvent).isNotExclusive();
    List<EventDefinition> eventDefinitions = boundaryEvent.getEventDefinitions();
    assertEquals(1, eventDefinitions.size());
    EventDefinition getResult = eventDefinitions.get(0);
    assertTrue(getResult instanceof CancelEventDefinition);
    assertEquals("42", boundaryEvent.getId());
    assertEquals("Documentation", boundaryEvent.getDocumentation());
    assertEquals("Name", boundaryEvent.getName());
    assertNull(getResult.getId());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertFalse(boundaryEvent.isExclusive());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(boundaryEvent.isAsynchronous());
    assertTrue(boundaryEvent.isNotExclusive());
  }

  /**
   * Method under test: {@link Event#setValues(Event)}
   */
  @Test
  public void testSetValues6() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    CancelEventDefinition cancelEventDefinition = mock(CancelEventDefinition.class);
    CancelEventDefinition cancelEventDefinition2 = new CancelEventDefinition();
    when(cancelEventDefinition.clone()).thenReturn(cancelEventDefinition2);

    ArrayList<EventDefinition> eventDefinitionList = new ArrayList<>();
    eventDefinitionList.add(cancelEventDefinition);
    EndEvent otherEvent = mock(EndEvent.class);
    when(otherEvent.isAsynchronous()).thenReturn(true);
    when(otherEvent.isNotExclusive()).thenReturn(true);
    when(otherEvent.getId()).thenReturn("42");
    when(otherEvent.getDocumentation()).thenReturn("Documentation");
    when(otherEvent.getName()).thenReturn("Name");
    when(otherEvent.getEventDefinitions()).thenReturn(eventDefinitionList);
    when(otherEvent.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherEvent.getAttributes()).thenReturn(new HashMap<>());
    when(otherEvent.getExtensionElements()).thenReturn(new HashMap<>());

    // Act
    boundaryEvent.setValues(otherEvent);

    // Assert
    verify(otherEvent, atLeast(1)).getAttributes();
    verify(otherEvent, atLeast(1)).getExtensionElements();
    verify(otherEvent).getId();
    verify(cancelEventDefinition).clone();
    verify(otherEvent, atLeast(1)).getEventDefinitions();
    verify(otherEvent).getDocumentation();
    verify(otherEvent, atLeast(1)).getExecutionListeners();
    verify(otherEvent).getName();
    verify(otherEvent).isAsynchronous();
    verify(otherEvent).isNotExclusive();
    assertEquals("42", boundaryEvent.getId());
    assertEquals("Documentation", boundaryEvent.getDocumentation());
    assertEquals("Name", boundaryEvent.getName());
    List<EventDefinition> eventDefinitions = boundaryEvent.getEventDefinitions();
    assertEquals(1, eventDefinitions.size());
    assertFalse(boundaryEvent.isExclusive());
    assertTrue(boundaryEvent.isAsynchronous());
    assertTrue(boundaryEvent.isNotExclusive());
    assertSame(cancelEventDefinition2, eventDefinitions.get(0));
  }
}
