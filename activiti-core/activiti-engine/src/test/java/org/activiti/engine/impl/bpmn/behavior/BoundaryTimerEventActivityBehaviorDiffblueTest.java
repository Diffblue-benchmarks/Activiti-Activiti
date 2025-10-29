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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.TimerEventDefinition;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;

public class BoundaryTimerEventActivityBehaviorDiffblueTest {
  /**
   * Method under test:
   * {@link BoundaryTimerEventActivityBehavior#execute(DelegateExecution)}
   */
  @Test
  public void testExecute() {
    // Arrange
    BoundaryTimerEventActivityBehavior boundaryTimerEventActivityBehavior = new BoundaryTimerEventActivityBehavior(
        new TimerEventDefinition(), true);

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> boundaryTimerEventActivityBehavior.execute(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link BoundaryTimerEventActivityBehavior#BoundaryTimerEventActivityBehavior(TimerEventDefinition, boolean)}
   */
  @Test
  public void testNewBoundaryTimerEventActivityBehavior() {
    // Arrange and Act
    BoundaryTimerEventActivityBehavior actualBoundaryTimerEventActivityBehavior = new BoundaryTimerEventActivityBehavior(
        new TimerEventDefinition(), true);

    // Assert
    TimerEventDefinition timerEventDefinition = actualBoundaryTimerEventActivityBehavior.timerEventDefinition;
    assertNull(timerEventDefinition.getId());
    assertNull(timerEventDefinition.getCalendarName());
    assertNull(timerEventDefinition.getEndDate());
    assertNull(timerEventDefinition.getTimeCycle());
    assertNull(timerEventDefinition.getTimeDate());
    assertNull(timerEventDefinition.getTimeDuration());
    assertEquals(0, timerEventDefinition.getXmlColumnNumber());
    assertEquals(0, timerEventDefinition.getXmlRowNumber());
    assertTrue(timerEventDefinition.getAttributes().isEmpty());
    assertTrue(timerEventDefinition.getExtensionElements().isEmpty());
    assertTrue(actualBoundaryTimerEventActivityBehavior.isInterrupting());
  }

  /**
   * Method under test:
   * {@link BoundaryTimerEventActivityBehavior#BoundaryTimerEventActivityBehavior(TimerEventDefinition, boolean)}
   */
  @Test
  public void testNewBoundaryTimerEventActivityBehavior2() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.computeIfPresent("foo", mock(BiFunction.class));

    TimerEventDefinition timerEventDefinition = new TimerEventDefinition();
    timerEventDefinition.setExtensionElements(extensionElements);

    // Act
    BoundaryTimerEventActivityBehavior actualBoundaryTimerEventActivityBehavior = new BoundaryTimerEventActivityBehavior(
        timerEventDefinition, true);

    // Assert
    TimerEventDefinition timerEventDefinition2 = actualBoundaryTimerEventActivityBehavior.timerEventDefinition;
    assertNull(timerEventDefinition2.getId());
    assertNull(timerEventDefinition2.getCalendarName());
    assertNull(timerEventDefinition2.getEndDate());
    assertNull(timerEventDefinition2.getTimeCycle());
    assertNull(timerEventDefinition2.getTimeDate());
    assertNull(timerEventDefinition2.getTimeDuration());
    assertEquals(0, timerEventDefinition2.getXmlColumnNumber());
    assertEquals(0, timerEventDefinition2.getXmlRowNumber());
    assertTrue(timerEventDefinition2.getAttributes().isEmpty());
    Map<String, List<ExtensionElement>> extensionElements2 = timerEventDefinition2.getExtensionElements();
    assertTrue(extensionElements2.isEmpty());
    assertTrue(actualBoundaryTimerEventActivityBehavior.isInterrupting());
    assertSame(extensionElements, extensionElements2);
  }
}
