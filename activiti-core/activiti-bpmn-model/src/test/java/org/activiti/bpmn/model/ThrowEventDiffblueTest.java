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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ThrowEventDiffblueTest {
  /**
   * Test {@link ThrowEvent#clone()}.
   *
   * <ul>
   *   <li>Given {@link ThrowEvent} (default constructor).
   *   <li>Then return Behavior is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ThrowEvent#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ThrowEvent ThrowEvent.clone()"})
  public void testClone_givenThrowEvent_thenReturnBehaviorIsNull() {
    // Arrange and Act
    ThrowEvent actualCloneResult = new ThrowEvent().clone();

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
   * Test new {@link ThrowEvent} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link ThrowEvent}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ThrowEvent.<init>()"})
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
