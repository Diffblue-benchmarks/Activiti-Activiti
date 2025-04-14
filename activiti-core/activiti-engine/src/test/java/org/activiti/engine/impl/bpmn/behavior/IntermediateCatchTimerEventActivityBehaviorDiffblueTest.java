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
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.bpmn.model.TimerEventDefinition;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class IntermediateCatchTimerEventActivityBehaviorDiffblueTest {
  /**
   * Test {@link IntermediateCatchTimerEventActivityBehavior#IntermediateCatchTimerEventActivityBehavior(TimerEventDefinition)}.
   * <p>
   * Method under test: {@link IntermediateCatchTimerEventActivityBehavior#IntermediateCatchTimerEventActivityBehavior(TimerEventDefinition)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void IntermediateCatchTimerEventActivityBehavior.<init>(TimerEventDefinition)"})
  public void testNewIntermediateCatchTimerEventActivityBehavior() {
    // Arrange and Act
    IntermediateCatchTimerEventActivityBehavior actualIntermediateCatchTimerEventActivityBehavior = new IntermediateCatchTimerEventActivityBehavior(
        new TimerEventDefinition());

    // Assert
    TimerEventDefinition timerEventDefinition = actualIntermediateCatchTimerEventActivityBehavior.timerEventDefinition;
    assertNull(timerEventDefinition.getId());
    assertNull(timerEventDefinition.getCalendarName());
    assertNull(timerEventDefinition.getEndDate());
    assertNull(timerEventDefinition.getTimeCycle());
    assertNull(timerEventDefinition.getTimeDate());
    assertNull(timerEventDefinition.getTimeDuration());
    assertNull(actualIntermediateCatchTimerEventActivityBehavior.getMultiInstanceActivityBehavior());
    assertEquals(0, timerEventDefinition.getXmlColumnNumber());
    assertEquals(0, timerEventDefinition.getXmlRowNumber());
    assertFalse(actualIntermediateCatchTimerEventActivityBehavior.hasLoopCharacteristics());
    assertFalse(actualIntermediateCatchTimerEventActivityBehavior.hasMultiInstanceCharacteristics());
    assertTrue(timerEventDefinition.getAttributes().isEmpty());
    assertTrue(timerEventDefinition.getExtensionElements().isEmpty());
  }
}
