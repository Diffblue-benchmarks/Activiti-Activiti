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
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.bpmn.model.TimerEventDefinition;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class BoundaryTimerEventActivityBehaviorDiffblueTest {
  /**
   * Test {@link
   * BoundaryTimerEventActivityBehavior#BoundaryTimerEventActivityBehavior(TimerEventDefinition,
   * boolean)}.
   *
   * <p>Method under test: {@link
   * BoundaryTimerEventActivityBehavior#BoundaryTimerEventActivityBehavior(TimerEventDefinition,
   * boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BoundaryTimerEventActivityBehavior.<init>(TimerEventDefinition, boolean)"
  })
  public void testNewBoundaryTimerEventActivityBehavior() {
    // Arrange and Act
    BoundaryTimerEventActivityBehavior actualBoundaryTimerEventActivityBehavior =
        new BoundaryTimerEventActivityBehavior(new TimerEventDefinition(), true);

    // Assert
    TimerEventDefinition timerEventDefinition =
        actualBoundaryTimerEventActivityBehavior.timerEventDefinition;
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
   * Test {@link BoundaryTimerEventActivityBehavior#execute(DelegateExecution)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link BoundaryTimerEventActivityBehavior#execute(DelegateExecution)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void BoundaryTimerEventActivityBehavior.execute(DelegateExecution)"})
  public void testExecute_thenThrowActivitiException() {
    // Arrange
    BoundaryTimerEventActivityBehavior boundaryTimerEventActivityBehavior =
        new BoundaryTimerEventActivityBehavior(new TimerEventDefinition(), true);

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            boundaryTimerEventActivityBehavior.execute(
                ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }
}
