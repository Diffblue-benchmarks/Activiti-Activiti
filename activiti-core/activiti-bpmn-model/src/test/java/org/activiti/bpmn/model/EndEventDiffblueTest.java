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

public class EndEventDiffblueTest {
  /**
   * Test {@link EndEvent#clone()}.
   * <ul>
   *   <li>Given {@link EndEvent} (default constructor).</li>
   *   <li>Then return Behavior is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EndEvent#clone()}
   */
  @Test
  public void testClone_givenEndEvent_thenReturnBehaviorIsNull() {
    // Arrange and Act
    EndEvent actualCloneResult = (new EndEvent()).clone();

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
   * Test {@link EndEvent#clone()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   *   <li>Then return Behavior is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EndEvent#clone()}
   */
  @Test
  public void testClone_givenHashMapComputeIfPresentFooAndBiFunction_thenReturnBehaviorIsNull() {
    // Arrange
    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.computeIfPresent("foo", mock(BiFunction.class));

    EndEvent endEvent = new EndEvent();
    endEvent.setExtensionElements(null);
    endEvent.setAttributes(attributes);

    // Act
    EndEvent actualCloneResult = endEvent.clone();

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
   * Test {@link EndEvent#setValues(EndEvent)} with {@code EndEvent}.
   * <ul>
   *   <li>Then calls {@link CancelEventDefinition#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EndEvent#setValues(EndEvent)}
   */
  @Test
  public void testSetValuesWithEndEvent_thenCallsClone() {
    // Arrange
    EndEvent endEvent = new EndEvent();
    CancelEventDefinition cancelEventDefinition = mock(CancelEventDefinition.class);
    when(cancelEventDefinition.clone()).thenReturn(new CancelEventDefinition());

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(cancelEventDefinition);

    EndEvent otherEvent = new EndEvent();
    otherEvent.setEventDefinitions(eventDefinitions);

    // Act
    endEvent.setValues(otherEvent);

    // Assert
    verify(cancelEventDefinition).clone();
  }

  /**
   * Test new {@link EndEvent} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link EndEvent}
   */
  @Test
  public void testNewEndEvent() {
    // Arrange and Act
    EndEvent actualEndEvent = new EndEvent();

    // Assert
    assertNull(actualEndEvent.getBehavior());
    assertNull(actualEndEvent.getId());
    assertNull(actualEndEvent.getDocumentation());
    assertNull(actualEndEvent.getName());
    assertNull(actualEndEvent.getParentContainer());
    assertEquals(0, actualEndEvent.getXmlColumnNumber());
    assertEquals(0, actualEndEvent.getXmlRowNumber());
    assertFalse(actualEndEvent.isAsynchronous());
    assertFalse(actualEndEvent.isNotExclusive());
    assertTrue(actualEndEvent.getEventDefinitions().isEmpty());
    assertTrue(actualEndEvent.getExecutionListeners().isEmpty());
    assertTrue(actualEndEvent.getIncomingFlows().isEmpty());
    assertTrue(actualEndEvent.getOutgoingFlows().isEmpty());
    assertTrue(actualEndEvent.getAttributes().isEmpty());
    assertTrue(actualEndEvent.getExtensionElements().isEmpty());
  }
}
