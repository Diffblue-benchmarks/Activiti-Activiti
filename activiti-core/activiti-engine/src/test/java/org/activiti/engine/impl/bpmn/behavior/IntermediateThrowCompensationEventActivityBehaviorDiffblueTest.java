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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import org.activiti.bpmn.model.CompensateEventDefinition;
import org.activiti.bpmn.model.ExtensionElement;
import org.junit.Test;

public class IntermediateThrowCompensationEventActivityBehaviorDiffblueTest {
  /**
   * Test
   * {@link IntermediateThrowCompensationEventActivityBehavior#IntermediateThrowCompensationEventActivityBehavior(CompensateEventDefinition)}.
   * <p>
   * Method under test:
   * {@link IntermediateThrowCompensationEventActivityBehavior#IntermediateThrowCompensationEventActivityBehavior(CompensateEventDefinition)}
   */
  @Test
  public void testNewIntermediateThrowCompensationEventActivityBehavior() {
    // Arrange, Act and Assert
    CompensateEventDefinition compensateEventDefinition = (new IntermediateThrowCompensationEventActivityBehavior(
        new CompensateEventDefinition())).compensateEventDefinition;
    assertNull(compensateEventDefinition.getId());
    assertNull(compensateEventDefinition.getActivityRef());
    assertEquals(0, compensateEventDefinition.getXmlColumnNumber());
    assertEquals(0, compensateEventDefinition.getXmlRowNumber());
    assertTrue(compensateEventDefinition.getAttributes().isEmpty());
    assertTrue(compensateEventDefinition.getExtensionElements().isEmpty());
    assertTrue(compensateEventDefinition.isWaitForCompletion());
  }

  /**
   * Test
   * {@link IntermediateThrowCompensationEventActivityBehavior#IntermediateThrowCompensationEventActivityBehavior(CompensateEventDefinition)}.
   * <p>
   * Method under test:
   * {@link IntermediateThrowCompensationEventActivityBehavior#IntermediateThrowCompensationEventActivityBehavior(CompensateEventDefinition)}
   */
  @Test
  public void testNewIntermediateThrowCompensationEventActivityBehavior2() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.computeIfPresent("foo", mock(BiFunction.class));

    CompensateEventDefinition compensateEventDefinition = new CompensateEventDefinition();
    compensateEventDefinition.setExtensionElements(extensionElements);

    // Act and Assert
    CompensateEventDefinition compensateEventDefinition2 = (new IntermediateThrowCompensationEventActivityBehavior(
        compensateEventDefinition)).compensateEventDefinition;
    assertNull(compensateEventDefinition2.getId());
    assertNull(compensateEventDefinition2.getActivityRef());
    assertEquals(0, compensateEventDefinition2.getXmlColumnNumber());
    assertEquals(0, compensateEventDefinition2.getXmlRowNumber());
    assertTrue(compensateEventDefinition2.getAttributes().isEmpty());
    assertTrue(compensateEventDefinition2.getExtensionElements().isEmpty());
    assertTrue(compensateEventDefinition2.isWaitForCompletion());
  }
}
