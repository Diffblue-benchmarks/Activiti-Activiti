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
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import org.junit.Test;

public class EventGatewayDiffblueTest {
  /**
   * Test {@link EventGateway#clone()}.
   * <ul>
   *   <li>Given {@link EventGateway} (default constructor) Asynchronous is
   * {@code true}.</li>
   *   <li>Then return Asynchronous.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventGateway#clone()}
   */
  @Test
  public void testClone_givenEventGatewayAsynchronousIsTrue_thenReturnAsynchronous() {
    // Arrange
    EventGateway eventGateway = new EventGateway();
    eventGateway.setAsynchronous(true);

    // Act
    EventGateway actualCloneResult = eventGateway.clone();

    // Assert
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getDefaultFlow());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.isNotExclusive());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isAsynchronous());
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Test {@link EventGateway#clone()}.
   * <ul>
   *   <li>Given {@link EventGateway} (default constructor).</li>
   *   <li>Then return not Asynchronous.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventGateway#clone()}
   */
  @Test
  public void testClone_givenEventGateway_thenReturnNotAsynchronous() {
    // Arrange and Act
    EventGateway actualCloneResult = (new EventGateway()).clone();

    // Assert
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getDefaultFlow());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Test {@link EventGateway#clone()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   *   <li>Then return not Asynchronous.</li>
   * </ul>
   * <p>
   * Method under test: {@link EventGateway#clone()}
   */
  @Test
  public void testClone_givenHashMapComputeIfPresentFooAndBiFunction_thenReturnNotAsynchronous() {
    // Arrange
    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.computeIfPresent("foo", mock(BiFunction.class));

    EventGateway eventGateway = new EventGateway();
    eventGateway.setExtensionElements(null);
    eventGateway.setAttributes(attributes);

    // Act
    EventGateway actualCloneResult = eventGateway.clone();

    // Assert
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getDefaultFlow());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Test new {@link EventGateway} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link EventGateway}
   */
  @Test
  public void testNewEventGateway() {
    // Arrange and Act
    EventGateway actualEventGateway = new EventGateway();

    // Assert
    assertNull(actualEventGateway.getBehavior());
    assertNull(actualEventGateway.getId());
    assertNull(actualEventGateway.getDocumentation());
    assertNull(actualEventGateway.getName());
    assertNull(actualEventGateway.getDefaultFlow());
    assertNull(actualEventGateway.getParentContainer());
    assertEquals(0, actualEventGateway.getXmlColumnNumber());
    assertEquals(0, actualEventGateway.getXmlRowNumber());
    assertFalse(actualEventGateway.isAsynchronous());
    assertFalse(actualEventGateway.isNotExclusive());
    assertTrue(actualEventGateway.getExecutionListeners().isEmpty());
    assertTrue(actualEventGateway.getIncomingFlows().isEmpty());
    assertTrue(actualEventGateway.getOutgoingFlows().isEmpty());
    assertTrue(actualEventGateway.getAttributes().isEmpty());
    assertTrue(actualEventGateway.getExtensionElements().isEmpty());
  }
}
