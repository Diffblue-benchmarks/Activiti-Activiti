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
package org.activiti.spring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.engine.runtime.Clock;
import org.junit.Test;

public class SpringAdvancedBusinessCalendarManagerFactoryDiffblueTest {
  /**
   * Test {@link SpringAdvancedBusinessCalendarManagerFactory#getClock()}.
   * <ul>
   *   <li>Then CurrentCalendar return {@link GregorianCalendar}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SpringAdvancedBusinessCalendarManagerFactory#getClock()}
   */
  @Test
  public void testGetClock_thenCurrentCalendarReturnGregorianCalendar() {
    // Arrange and Act
    Clock actualClock = (new SpringAdvancedBusinessCalendarManagerFactory()).getClock();

    // Assert
    Calendar currentCalendar = actualClock.getCurrentCalendar();
    assertTrue(currentCalendar instanceof GregorianCalendar);
    assertTrue(actualClock instanceof DefaultClockImpl);
    assertEquals("gregory", currentCalendar.getCalendarType());
    assertEquals(2, currentCalendar.getFirstDayOfWeek());
    assertEquals(4, currentCalendar.getMinimalDaysInFirstWeek());
    assertTrue(currentCalendar.isLenient());
    assertTrue(currentCalendar.isWeekDateSupported());
  }

  /**
   * Test {@link SpringAdvancedBusinessCalendarManagerFactory#getClock()}.
   * <ul>
   *   <li>Then return {@link DefaultClockImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SpringAdvancedBusinessCalendarManagerFactory#getClock()}
   */
  @Test
  public void testGetClock_thenReturnDefaultClockImpl() {
    // Arrange
    SpringAdvancedBusinessCalendarManagerFactory springAdvancedBusinessCalendarManagerFactory = new SpringAdvancedBusinessCalendarManagerFactory();
    springAdvancedBusinessCalendarManagerFactory.setDefaultScheduleVersion(1);
    DefaultClockImpl clock = new DefaultClockImpl();
    springAdvancedBusinessCalendarManagerFactory.setClock(clock);

    // Act and Assert
    assertSame(clock, springAdvancedBusinessCalendarManagerFactory.getClock());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link SpringAdvancedBusinessCalendarManagerFactory}
   *   <li>{@link SpringAdvancedBusinessCalendarManagerFactory#setClock(Clock)}
   *   <li>
   * {@link SpringAdvancedBusinessCalendarManagerFactory#setDefaultScheduleVersion(Integer)}
   *   <li>
   * {@link SpringAdvancedBusinessCalendarManagerFactory#getDefaultScheduleVersion()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    SpringAdvancedBusinessCalendarManagerFactory actualSpringAdvancedBusinessCalendarManagerFactory = new SpringAdvancedBusinessCalendarManagerFactory();
    DefaultClockImpl clock = new DefaultClockImpl();
    actualSpringAdvancedBusinessCalendarManagerFactory.setClock(clock);
    actualSpringAdvancedBusinessCalendarManagerFactory.setDefaultScheduleVersion(1);

    // Assert that nothing has changed
    assertEquals(1, actualSpringAdvancedBusinessCalendarManagerFactory.getDefaultScheduleVersion().intValue());
    assertSame(clock, actualSpringAdvancedBusinessCalendarManagerFactory.getClock());
  }
}
