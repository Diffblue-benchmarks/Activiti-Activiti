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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class TimerEventDefinitionDiffblueTest {
  /**
   * Test {@link TimerEventDefinition#clone()}.
   *
   * <p>Method under test: {@link TimerEventDefinition#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"TimerEventDefinition TimerEventDefinition.clone()"})
  public void testClone() {
    // Arrange and Act
    TimerEventDefinition actualCloneResult = new TimerEventDefinition().clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getCalendarName());
    assertNull(actualCloneResult.getEndDate());
    assertNull(actualCloneResult.getTimeCycle());
    assertNull(actualCloneResult.getTimeDate());
    assertNull(actualCloneResult.getTimeDuration());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link TimerEventDefinition}
   *   <li>{@link TimerEventDefinition#setCalendarName(String)}
   *   <li>{@link TimerEventDefinition#setEndDate(String)}
   *   <li>{@link TimerEventDefinition#setTimeCycle(String)}
   *   <li>{@link TimerEventDefinition#setTimeDate(String)}
   *   <li>{@link TimerEventDefinition#setTimeDuration(String)}
   *   <li>{@link TimerEventDefinition#getCalendarName()}
   *   <li>{@link TimerEventDefinition#getEndDate()}
   *   <li>{@link TimerEventDefinition#getTimeCycle()}
   *   <li>{@link TimerEventDefinition#getTimeDate()}
   *   <li>{@link TimerEventDefinition#getTimeDuration()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void TimerEventDefinition.<init>()",
    "String TimerEventDefinition.getCalendarName()",
    "String TimerEventDefinition.getEndDate()",
    "String TimerEventDefinition.getTimeCycle()",
    "String TimerEventDefinition.getTimeDate()",
    "String TimerEventDefinition.getTimeDuration()",
    "void TimerEventDefinition.setCalendarName(String)",
    "void TimerEventDefinition.setEndDate(String)",
    "void TimerEventDefinition.setTimeCycle(String)",
    "void TimerEventDefinition.setTimeDate(String)",
    "void TimerEventDefinition.setTimeDuration(String)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    TimerEventDefinition actualTimerEventDefinition = new TimerEventDefinition();
    actualTimerEventDefinition.setCalendarName("Calendar Name");
    actualTimerEventDefinition.setEndDate("2020-03-01");
    actualTimerEventDefinition.setTimeCycle("Time Cycle");
    actualTimerEventDefinition.setTimeDate("2020-03-01");
    actualTimerEventDefinition.setTimeDuration("Time Duration");
    String actualCalendarName = actualTimerEventDefinition.getCalendarName();
    String actualEndDate = actualTimerEventDefinition.getEndDate();
    String actualTimeCycle = actualTimerEventDefinition.getTimeCycle();
    String actualTimeDate = actualTimerEventDefinition.getTimeDate();

    // Assert
    assertEquals("2020-03-01", actualEndDate);
    assertEquals("2020-03-01", actualTimeDate);
    assertEquals("Calendar Name", actualCalendarName);
    assertEquals("Time Cycle", actualTimeCycle);
    assertEquals("Time Duration", actualTimerEventDefinition.getTimeDuration());
    assertNull(actualTimerEventDefinition.getId());
    assertEquals(0, actualTimerEventDefinition.getXmlColumnNumber());
    assertEquals(0, actualTimerEventDefinition.getXmlRowNumber());
    assertTrue(actualTimerEventDefinition.getAttributes().isEmpty());
    assertTrue(actualTimerEventDefinition.getExtensionElements().isEmpty());
  }
}
