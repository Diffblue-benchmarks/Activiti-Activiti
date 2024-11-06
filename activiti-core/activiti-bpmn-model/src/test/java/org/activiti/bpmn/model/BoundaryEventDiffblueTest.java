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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import org.junit.Test;

public class BoundaryEventDiffblueTest {
  /**
   * Test {@link BoundaryEvent#hasErrorEventDefinition()}.
   * <p>
   * Method under test: {@link BoundaryEvent#hasErrorEventDefinition()}
   */
  @Test
  public void testHasErrorEventDefinition() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.addEventDefinition(new CancelEventDefinition());

    // Act and Assert
    assertFalse(boundaryEvent.hasErrorEventDefinition());
  }

  /**
   * Test {@link BoundaryEvent#hasErrorEventDefinition()}.
   * <p>
   * Method under test: {@link BoundaryEvent#hasErrorEventDefinition()}
   */
  @Test
  public void testHasErrorEventDefinition2() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.addEventDefinition(new CancelEventDefinition());
    boundaryEvent.addEventDefinition(new CancelEventDefinition());

    // Act and Assert
    assertFalse(boundaryEvent.hasErrorEventDefinition());
  }

  /**
   * Test {@link BoundaryEvent#hasErrorEventDefinition()}.
   * <ul>
   *   <li>Given {@link BoundaryEvent} (default constructor) EventDefinitions is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BoundaryEvent#hasErrorEventDefinition()}
   */
  @Test
  public void testHasErrorEventDefinition_givenBoundaryEventEventDefinitionsIsNull() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setEventDefinitions(null);

    // Act and Assert
    assertFalse(boundaryEvent.hasErrorEventDefinition());
  }

  /**
   * Test {@link BoundaryEvent#hasErrorEventDefinition()}.
   * <ul>
   *   <li>Given {@link BoundaryEvent} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BoundaryEvent#hasErrorEventDefinition()}
   */
  @Test
  public void testHasErrorEventDefinition_givenBoundaryEvent_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new BoundaryEvent()).hasErrorEventDefinition());
  }

  /**
   * Test {@link BoundaryEvent#hasErrorEventDefinition()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BoundaryEvent#hasErrorEventDefinition()}
   */
  @Test
  public void testHasErrorEventDefinition_thenReturnTrue() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.addEventDefinition(new ErrorEventDefinition());

    // Act and Assert
    assertTrue(boundaryEvent.hasErrorEventDefinition());
  }

  /**
   * Test {@link BoundaryEvent#clone()}.
   * <ul>
   *   <li>Given {@link BoundaryEvent} (default constructor).</li>
   *   <li>Then return Behavior is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BoundaryEvent#clone()}
   */
  @Test
  public void testClone_givenBoundaryEvent_thenReturnBehaviorIsNull() {
    // Arrange and Act
    BoundaryEvent actualCloneResult = (new BoundaryEvent()).clone();

    // Assert
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getAttachedToRefId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getAttachedToRef());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertTrue(actualCloneResult.getEventDefinitions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isCancelActivity());
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Test {@link BoundaryEvent#clone()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   *   <li>Then return Behavior is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BoundaryEvent#clone()}
   */
  @Test
  public void testClone_givenHashMapComputeIfPresentFooAndBiFunction_thenReturnBehaviorIsNull() {
    // Arrange
    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.computeIfPresent("foo", mock(BiFunction.class));

    BoundaryEvent boundaryEvent = new BoundaryEvent();
    boundaryEvent.setExtensionElements(null);
    boundaryEvent.setAttributes(attributes);

    // Act
    BoundaryEvent actualCloneResult = boundaryEvent.clone();

    // Assert
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getAttachedToRefId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getAttachedToRef());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertTrue(actualCloneResult.getEventDefinitions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isCancelActivity());
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Test {@link BoundaryEvent#setValues(BoundaryEvent)} with
   * {@code BoundaryEvent}.
   * <ul>
   *   <li>Then calls {@link CancelEventDefinition#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BoundaryEvent#setValues(BoundaryEvent)}
   */
  @Test
  public void testSetValuesWithBoundaryEvent_thenCallsClone() {
    // Arrange
    BoundaryEvent boundaryEvent = new BoundaryEvent();
    CancelEventDefinition cancelEventDefinition = mock(CancelEventDefinition.class);
    when(cancelEventDefinition.clone()).thenReturn(new CancelEventDefinition());

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(cancelEventDefinition);

    BoundaryEvent otherEvent = new BoundaryEvent();
    otherEvent.setEventDefinitions(eventDefinitions);

    // Act
    boundaryEvent.setValues(otherEvent);

    // Assert
    verify(cancelEventDefinition).clone();
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link BoundaryEvent}
   *   <li>{@link BoundaryEvent#setAttachedToRef(Activity)}
   *   <li>{@link BoundaryEvent#setAttachedToRefId(String)}
   *   <li>{@link BoundaryEvent#setCancelActivity(boolean)}
   *   <li>{@link BoundaryEvent#getAttachedToRef()}
   *   <li>{@link BoundaryEvent#getAttachedToRefId()}
   *   <li>{@link BoundaryEvent#isCancelActivity()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    BoundaryEvent actualBoundaryEvent = new BoundaryEvent();
    AdhocSubProcess attachedToRef = new AdhocSubProcess();
    actualBoundaryEvent.setAttachedToRef(attachedToRef);
    actualBoundaryEvent.setAttachedToRefId("42");
    actualBoundaryEvent.setCancelActivity(true);
    Activity actualAttachedToRef = actualBoundaryEvent.getAttachedToRef();
    String actualAttachedToRefId = actualBoundaryEvent.getAttachedToRefId();
    boolean actualIsCancelActivityResult = actualBoundaryEvent.isCancelActivity();

    // Assert that nothing has changed
    assertEquals("42", actualAttachedToRefId);
    assertEquals(0, actualBoundaryEvent.getXmlColumnNumber());
    assertEquals(0, actualBoundaryEvent.getXmlRowNumber());
    assertFalse(actualBoundaryEvent.isAsynchronous());
    assertFalse(actualBoundaryEvent.isNotExclusive());
    assertTrue(actualBoundaryEvent.getEventDefinitions().isEmpty());
    assertTrue(actualBoundaryEvent.getExecutionListeners().isEmpty());
    assertTrue(actualBoundaryEvent.getIncomingFlows().isEmpty());
    assertTrue(actualBoundaryEvent.getOutgoingFlows().isEmpty());
    assertTrue(actualBoundaryEvent.getAttributes().isEmpty());
    assertTrue(actualBoundaryEvent.getExtensionElements().isEmpty());
    assertTrue(actualIsCancelActivityResult);
    assertSame(attachedToRef, actualAttachedToRef);
  }
}
