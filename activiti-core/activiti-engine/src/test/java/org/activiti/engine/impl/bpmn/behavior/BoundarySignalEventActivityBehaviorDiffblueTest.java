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
package org.activiti.engine.impl.bpmn.behavior;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.activiti.bpmn.model.Signal;
import org.activiti.bpmn.model.SignalEventDefinition;
import org.junit.Test;

public class BoundarySignalEventActivityBehaviorDiffblueTest {
  /**
   * Test
   * {@link BoundarySignalEventActivityBehavior#BoundarySignalEventActivityBehavior(SignalEventDefinition, Signal, boolean)}.
   * <p>
   * Method under test:
   * {@link BoundarySignalEventActivityBehavior#BoundarySignalEventActivityBehavior(SignalEventDefinition, Signal, boolean)}
   */
  @Test
  public void testNewBoundarySignalEventActivityBehavior() {
    // Arrange
    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();

    // Act
    BoundarySignalEventActivityBehavior actualBoundarySignalEventActivityBehavior = new BoundarySignalEventActivityBehavior(
        signalEventDefinition, new Signal("42", "Name"), true);

    // Assert
    Signal signal = actualBoundarySignalEventActivityBehavior.signal;
    assertEquals("42", signal.getId());
    assertEquals("Name", signal.getName());
    SignalEventDefinition signalEventDefinition2 = actualBoundarySignalEventActivityBehavior.signalEventDefinition;
    assertNull(signalEventDefinition2.getId());
    assertNull(signal.getScope());
    assertNull(signalEventDefinition2.getSignalExpression());
    assertNull(signalEventDefinition2.getSignalRef());
    assertEquals(0, signal.getXmlColumnNumber());
    assertEquals(0, signalEventDefinition2.getXmlColumnNumber());
    assertEquals(0, signal.getXmlRowNumber());
    assertEquals(0, signalEventDefinition2.getXmlRowNumber());
    assertFalse(signalEventDefinition2.isAsync());
    assertTrue(signal.getAttributes().isEmpty());
    assertTrue(signalEventDefinition2.getAttributes().isEmpty());
    assertTrue(signal.getExtensionElements().isEmpty());
    assertTrue(signalEventDefinition2.getExtensionElements().isEmpty());
    assertTrue(actualBoundarySignalEventActivityBehavior.isInterrupting());
  }
}
