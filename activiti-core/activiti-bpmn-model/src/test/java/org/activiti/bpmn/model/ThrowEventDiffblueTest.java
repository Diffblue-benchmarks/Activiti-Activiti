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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import org.junit.Test;

public class ThrowEventDiffblueTest {
  /**
   * Test {@link ThrowEvent#clone()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   *   <li>Then return Behavior is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ThrowEvent#clone()}
   */
  @Test
  public void testClone_givenHashMapComputeIfPresentFooAndBiFunction_thenReturnBehaviorIsNull() {
    // Arrange
    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.computeIfPresent("foo", mock(BiFunction.class));

    ThrowEvent throwEvent = new ThrowEvent();
    throwEvent.setExtensionElements(null);
    throwEvent.setAttributes(attributes);

    // Act
    ThrowEvent actualCloneResult = throwEvent.clone();

    // Assert
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
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
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Test {@link ThrowEvent#clone()}.
   * <ul>
   *   <li>Given {@link ThrowEvent} (default constructor).</li>
   *   <li>Then return Behavior is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ThrowEvent#clone()}
   */
  @Test
  public void testClone_givenThrowEvent_thenReturnBehaviorIsNull() {
    // Arrange and Act
    ThrowEvent actualCloneResult = (new ThrowEvent()).clone();

    // Assert
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
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
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Test {@link ThrowEvent#setValues(ThrowEvent)} with {@code ThrowEvent}.
   * <ul>
   *   <li>Then calls {@link CancelEventDefinition#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ThrowEvent#setValues(ThrowEvent)}
   */
  @Test
  public void testSetValuesWithThrowEvent_thenCallsClone() {
    // Arrange
    ThrowEvent throwEvent = new ThrowEvent();
    CancelEventDefinition cancelEventDefinition = mock(CancelEventDefinition.class);
    when(cancelEventDefinition.clone()).thenReturn(new CancelEventDefinition());

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(cancelEventDefinition);

    ThrowEvent otherEvent = new ThrowEvent();
    otherEvent.setEventDefinitions(eventDefinitions);

    // Act
    throwEvent.setValues(otherEvent);

    // Assert
    verify(cancelEventDefinition).clone();
  }

  /**
   * Test new {@link ThrowEvent} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link ThrowEvent}
   */
  @Test
  public void testNewThrowEvent() {
    // Arrange and Act
    ThrowEvent actualThrowEvent = new ThrowEvent();

    // Assert
    assertNull(actualThrowEvent.getBehavior());
    assertNull(actualThrowEvent.getId());
    assertNull(actualThrowEvent.getDocumentation());
    assertNull(actualThrowEvent.getName());
    assertNull(actualThrowEvent.getParentContainer());
    assertEquals(0, actualThrowEvent.getXmlColumnNumber());
    assertEquals(0, actualThrowEvent.getXmlRowNumber());
    assertFalse(actualThrowEvent.isAsynchronous());
    assertFalse(actualThrowEvent.isNotExclusive());
    assertTrue(actualThrowEvent.getEventDefinitions().isEmpty());
    assertTrue(actualThrowEvent.getExecutionListeners().isEmpty());
    assertTrue(actualThrowEvent.getIncomingFlows().isEmpty());
    assertTrue(actualThrowEvent.getOutgoingFlows().isEmpty());
    assertTrue(actualThrowEvent.getAttributes().isEmpty());
    assertTrue(actualThrowEvent.getExtensionElements().isEmpty());
  }
}
