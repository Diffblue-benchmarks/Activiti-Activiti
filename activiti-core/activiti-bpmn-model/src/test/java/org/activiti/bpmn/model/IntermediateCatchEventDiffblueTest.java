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

public class IntermediateCatchEventDiffblueTest {
  /**
   * Test {@link IntermediateCatchEvent#clone()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   *   <li>Then return Behavior is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IntermediateCatchEvent#clone()}
   */
  @Test
  public void testClone_givenHashMapComputeIfPresentFooAndBiFunction_thenReturnBehaviorIsNull() {
    // Arrange
    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.computeIfPresent("foo", mock(BiFunction.class));

    IntermediateCatchEvent intermediateCatchEvent = new IntermediateCatchEvent();
    intermediateCatchEvent.setExtensionElements(null);
    intermediateCatchEvent.setAttributes(attributes);

    // Act
    IntermediateCatchEvent actualCloneResult = intermediateCatchEvent.clone();

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
   * Test {@link IntermediateCatchEvent#clone()}.
   * <ul>
   *   <li>Given {@link IntermediateCatchEvent} (default constructor).</li>
   *   <li>Then return Behavior is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IntermediateCatchEvent#clone()}
   */
  @Test
  public void testClone_givenIntermediateCatchEvent_thenReturnBehaviorIsNull() {
    // Arrange and Act
    IntermediateCatchEvent actualCloneResult = (new IntermediateCatchEvent()).clone();

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
   * Test {@link IntermediateCatchEvent#setValues(IntermediateCatchEvent)} with
   * {@code IntermediateCatchEvent}.
   * <ul>
   *   <li>Then calls {@link CancelEventDefinition#clone()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link IntermediateCatchEvent#setValues(IntermediateCatchEvent)}
   */
  @Test
  public void testSetValuesWithIntermediateCatchEvent_thenCallsClone() {
    // Arrange
    IntermediateCatchEvent intermediateCatchEvent = new IntermediateCatchEvent();
    CancelEventDefinition cancelEventDefinition = mock(CancelEventDefinition.class);
    when(cancelEventDefinition.clone()).thenReturn(new CancelEventDefinition());

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(cancelEventDefinition);

    IntermediateCatchEvent otherEvent = new IntermediateCatchEvent();
    otherEvent.setEventDefinitions(eventDefinitions);

    // Act
    intermediateCatchEvent.setValues(otherEvent);

    // Assert
    verify(cancelEventDefinition).clone();
  }

  /**
   * Test new {@link IntermediateCatchEvent} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link IntermediateCatchEvent}
   */
  @Test
  public void testNewIntermediateCatchEvent() {
    // Arrange and Act
    IntermediateCatchEvent actualIntermediateCatchEvent = new IntermediateCatchEvent();

    // Assert
    assertNull(actualIntermediateCatchEvent.getBehavior());
    assertNull(actualIntermediateCatchEvent.getId());
    assertNull(actualIntermediateCatchEvent.getDocumentation());
    assertNull(actualIntermediateCatchEvent.getName());
    assertNull(actualIntermediateCatchEvent.getParentContainer());
    assertEquals(0, actualIntermediateCatchEvent.getXmlColumnNumber());
    assertEquals(0, actualIntermediateCatchEvent.getXmlRowNumber());
    assertFalse(actualIntermediateCatchEvent.isAsynchronous());
    assertFalse(actualIntermediateCatchEvent.isNotExclusive());
    assertTrue(actualIntermediateCatchEvent.getEventDefinitions().isEmpty());
    assertTrue(actualIntermediateCatchEvent.getExecutionListeners().isEmpty());
    assertTrue(actualIntermediateCatchEvent.getIncomingFlows().isEmpty());
    assertTrue(actualIntermediateCatchEvent.getOutgoingFlows().isEmpty());
    assertTrue(actualIntermediateCatchEvent.getAttributes().isEmpty());
    assertTrue(actualIntermediateCatchEvent.getExtensionElements().isEmpty());
  }
}
