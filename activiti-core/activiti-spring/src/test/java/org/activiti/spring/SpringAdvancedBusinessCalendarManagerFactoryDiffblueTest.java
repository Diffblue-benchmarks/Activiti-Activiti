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
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.engine.runtime.Clock;
import org.junit.Test;

public class SpringAdvancedBusinessCalendarManagerFactoryDiffblueTest {
  /**
   * Method under test:
   * {@link SpringAdvancedBusinessCalendarManagerFactory#getClock()}
   */
  @Test
  public void testGetClock() {
    // Arrange
    SpringAdvancedBusinessCalendarManagerFactory springAdvancedBusinessCalendarManagerFactory = new SpringAdvancedBusinessCalendarManagerFactory();
    springAdvancedBusinessCalendarManagerFactory.setDefaultScheduleVersion(1);
    DefaultClockImpl clock = new DefaultClockImpl();
    springAdvancedBusinessCalendarManagerFactory.setClock(clock);

    // Act and Assert
    assertSame(clock, springAdvancedBusinessCalendarManagerFactory.getClock());
  }

  /**
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
