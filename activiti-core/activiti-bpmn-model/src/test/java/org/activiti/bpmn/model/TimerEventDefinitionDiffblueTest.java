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
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import org.junit.Test;

public class TimerEventDefinitionDiffblueTest {
  /**
   * Test {@link TimerEventDefinition#clone()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TimerEventDefinition#clone()}
   */
  @Test
  public void testClone_givenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.computeIfPresent("foo", mock(BiFunction.class));

    TimerEventDefinition timerEventDefinition = new TimerEventDefinition();
    timerEventDefinition.setExtensionElements(extensionElements);

    // Act
    TimerEventDefinition actualCloneResult = timerEventDefinition.clone();

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
   * Test {@link TimerEventDefinition#clone()}.
   * <ul>
   *   <li>Given {@link TimerEventDefinition} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link TimerEventDefinition#clone()}
   */
  @Test
  public void testClone_givenTimerEventDefinition() {
    // Arrange and Act
    TimerEventDefinition actualCloneResult = (new TimerEventDefinition()).clone();

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
   * Test {@link TimerEventDefinition#setValues(TimerEventDefinition)} with
   * {@code otherDefinition}.
   * <ul>
   *   <li>Then calls {@link ExtensionElement#getName()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerEventDefinition#setValues(TimerEventDefinition)}
   */
  @Test
  public void testSetValuesWithOtherDefinition_thenCallsGetName() {
    // Arrange
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getName()).thenReturn("Name");

    TimerEventDefinition timerEventDefinition = new TimerEventDefinition();
    timerEventDefinition.addExtensionElement(extensionElement);

    // Act
    timerEventDefinition.setValues(new TimerEventDefinition());

    // Assert
    verify(extensionElement, atLeast(1)).getName();
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
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

    // Assert that nothing has changed
    assertEquals("2020-03-01", actualEndDate);
    assertEquals("2020-03-01", actualTimeDate);
    assertEquals("Calendar Name", actualCalendarName);
    assertEquals("Time Cycle", actualTimeCycle);
    assertEquals("Time Duration", actualTimerEventDefinition.getTimeDuration());
    assertEquals(0, actualTimerEventDefinition.getXmlColumnNumber());
    assertEquals(0, actualTimerEventDefinition.getXmlRowNumber());
    assertTrue(actualTimerEventDefinition.getAttributes().isEmpty());
    assertTrue(actualTimerEventDefinition.getExtensionElements().isEmpty());
  }
}
