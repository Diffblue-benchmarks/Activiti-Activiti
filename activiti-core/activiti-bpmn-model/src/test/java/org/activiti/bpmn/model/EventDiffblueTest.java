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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class EventDiffblueTest {
  /**
   * Test {@link Event#getEventDefinitions()}.
   *
   * <p>Method under test: {@link Event#getEventDefinitions()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List Event.getEventDefinitions()"})
  public void testGetEventDefinitions() {
    // Arrange, Act and Assert
    assertTrue(new BoundaryEvent().getEventDefinitions().isEmpty());
  }

  /**
   * Test {@link Event#setEventDefinitions(List)}.
   *
   * <ul>
   *   <li>Given {@link CancelEventDefinition} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link Event#setEventDefinitions(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Event.setEventDefinitions(List)"})
  public void testSetEventDefinitions_givenCancelEventDefinition() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());

    // Act
    boundaryEvent.setEventDefinitions(eventDefinitions);

    // Assert
    assertSame(eventDefinitions, boundaryEvent.getEventDefinitions());
  }

  /**
   * Test {@link Event#setEventDefinitions(List)}.
   *
   * <ul>
   *   <li>Given {@link CancelEventDefinition} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link Event#setEventDefinitions(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Event.setEventDefinitions(List)"})
  public void testSetEventDefinitions_givenCancelEventDefinition2() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());
    eventDefinitions.add(new CancelEventDefinition());

    // Act
    boundaryEvent.setEventDefinitions(eventDefinitions);

    // Assert
    assertSame(eventDefinitions, boundaryEvent.getEventDefinitions());
  }

  /**
   * Test {@link Event#setEventDefinitions(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link Event#setEventDefinitions(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Event.setEventDefinitions(List)"})
  public void testSetEventDefinitions_whenArrayList() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();

    // Act
    boundaryEvent.setEventDefinitions(eventDefinitions);

    // Assert
    assertSame(eventDefinitions, boundaryEvent.getEventDefinitions());
  }

  /**
   * Test {@link Event#addEventDefinition(EventDefinition)}.
   *
   * <p>Method under test: {@link Event#addEventDefinition(EventDefinition)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Event.addEventDefinition(EventDefinition)"})
  public void testAddEventDefinition() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    CancelEventDefinition eventDefinition = new CancelEventDefinition();

    // Act
    boundaryEvent.addEventDefinition(eventDefinition);

    // Assert
    List<EventDefinition> eventDefinitions = boundaryEvent.getEventDefinitions();
    assertEquals(1, eventDefinitions.size());
    assertSame(eventDefinition, eventDefinitions.get(0));
  }

  /**
   * Test {@link Event#setValues(Event)} with {@code Event}.
   *
   * <p>Method under test: {@link Event#setValues(Event)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Event.setValues(Event)"})
  public void testSetValuesWithEvent() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();

    CancelEventDefinition cancelEventDefinition = mock(CancelEventDefinition.class);
    CancelEventDefinition cancelEventDefinition2 = new CancelEventDefinition();
    when(cancelEventDefinition.clone()).thenReturn(cancelEventDefinition2);

    ArrayList<EventDefinition> eventDefinitionList = new ArrayList<>();
    eventDefinitionList.add(cancelEventDefinition);

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(activitiListener);

    EndEvent otherEvent = mock(EndEvent.class);
    when(otherEvent.getEventDefinitions()).thenReturn(eventDefinitionList);
    when(otherEvent.getExecutionListeners()).thenReturn(activitiListenerList);
    when(otherEvent.isAsynchronous()).thenReturn(true);
    when(otherEvent.isNotExclusive()).thenReturn(true);
    when(otherEvent.getId()).thenReturn("42");
    when(otherEvent.getDocumentation()).thenReturn("Documentation");
    when(otherEvent.getName()).thenReturn("Name");
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
    List<EventDefinition> eventDefinitions = boundaryEvent.getEventDefinitions();
    assertEquals(1, eventDefinitions.size());
    EventDefinition getResult = eventDefinitions.get(0);
    assertTrue(getResult instanceof CancelEventDefinition);
    assertEquals("42", boundaryEvent.getId());
    assertEquals("Documentation", boundaryEvent.getDocumentation());
    assertEquals("Name", boundaryEvent.getName());
    assertEquals(1, boundaryEvent.getExecutionListeners().size());
    assertFalse(boundaryEvent.isExclusive());
    assertTrue(boundaryEvent.isAsynchronous());
    assertTrue(boundaryEvent.isNotExclusive());
    assertSame(cancelEventDefinition2, getResult);
  }

  /**
   * Test {@link Event#setValues(Event)} with {@code Event}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link BoundaryEvent} (default constructor) EventDefinitions is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link Event#setValues(Event)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Event.setValues(Event)"})
  public void testSetValuesWithEvent_givenNull_whenBoundaryEventEventDefinitionsIsNull() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();

    BoundaryEvent otherEvent = new BoundaryEvent();
    otherEvent.setEventDefinitions(null);

    // Act
    boundaryEvent.setValues((Event) otherEvent);

    // Assert that nothing has changed
    assertFalse(otherEvent.isAsynchronous());
    assertFalse(otherEvent.isNotExclusive());
    assertTrue(otherEvent.getExecutionListeners().isEmpty());
    assertTrue(otherEvent.isExclusive());
  }

  /**
   * Test {@link Event#setValues(Event)} with {@code Event}.
   *
   * <ul>
   *   <li>Then {@link BoundaryEvent} (default constructor) EventDefinitions is {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link Event#setValues(Event)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Event.setValues(Event)"})
  public void testSetValuesWithEvent_thenBoundaryEventEventDefinitionsIsArrayList() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());

    BoundaryEvent otherEvent = new BoundaryEvent();
    otherEvent.setEventDefinitions(eventDefinitions);

    // Act
    boundaryEvent.setValues((Event) otherEvent);

    // Assert that nothing has changed
    assertFalse(otherEvent.isAsynchronous());
    assertFalse(otherEvent.isNotExclusive());
    assertTrue(otherEvent.getExecutionListeners().isEmpty());
    assertTrue(otherEvent.isExclusive());
    assertSame(eventDefinitions, otherEvent.getEventDefinitions());
  }

  /**
   * Test {@link Event#setValues(Event)} with {@code Event}.
   *
   * <ul>
   *   <li>Then {@link BoundaryEvent} (default constructor) EventDefinitions size is one.
   * </ul>
   *
   * <p>Method under test: {@link Event#setValues(Event)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Event.setValues(Event)"})
  public void testSetValuesWithEvent_thenBoundaryEventEventDefinitionsSizeIsOne() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();

    ArrayList<EventDefinition> eventDefinitionList = new ArrayList<>();
    eventDefinitionList.add(new CancelEventDefinition());

    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setFieldExtensions(fieldExtensions);

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(activitiListener);

    EndEvent otherEvent = mock(EndEvent.class);
    when(otherEvent.getEventDefinitions()).thenReturn(eventDefinitionList);
    when(otherEvent.getExecutionListeners()).thenReturn(activitiListenerList);
    when(otherEvent.isAsynchronous()).thenReturn(true);
    when(otherEvent.isNotExclusive()).thenReturn(true);
    when(otherEvent.getId()).thenReturn("42");
    when(otherEvent.getDocumentation()).thenReturn("Documentation");
    when(otherEvent.getName()).thenReturn("Name");
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
    assertTrue(eventDefinitions.get(0) instanceof CancelEventDefinition);
    assertEquals("42", boundaryEvent.getId());
    assertEquals("Documentation", boundaryEvent.getDocumentation());
    assertEquals("Name", boundaryEvent.getName());
    assertEquals(1, boundaryEvent.getExecutionListeners().size());
    assertFalse(boundaryEvent.isExclusive());
    assertTrue(boundaryEvent.isAsynchronous());
    assertTrue(boundaryEvent.isNotExclusive());
  }

  /**
   * Test {@link Event#setValues(Event)} with {@code Event}.
   *
   * <ul>
   *   <li>When {@link BoundaryEvent} (default constructor).
   *   <li>Then {@link BoundaryEvent} (default constructor) EventDefinitions Empty.
   * </ul>
   *
   * <p>Method under test: {@link Event#setValues(Event)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Event.setValues(Event)"})
  public void testSetValuesWithEvent_whenBoundaryEvent_thenBoundaryEventEventDefinitionsEmpty() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    BoundaryEvent otherEvent = new BoundaryEvent();

    // Act
    boundaryEvent.setValues((Event) otherEvent);

    // Assert that nothing has changed
    assertFalse(otherEvent.isAsynchronous());
    assertFalse(otherEvent.isNotExclusive());
    assertTrue(otherEvent.getEventDefinitions().isEmpty());
    assertTrue(otherEvent.getExecutionListeners().isEmpty());
    assertTrue(otherEvent.isExclusive());
  }
}
