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
package org.activiti.engine.impl.calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.TimeZone;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.engine.runtime.ClockReader;
import org.junit.Test;

public class AdvancedCycleBusinessCalendarDiffblueTest {
  /**
   * Method under test:
   * {@link AdvancedCycleBusinessCalendar#getDefaultScheduleVersion()}
   */
  @Test
  public void testGetDefaultScheduleVersion() {
    // Arrange, Act and Assert
    assertEquals(2, (new AdvancedCycleBusinessCalendar(new DefaultClockImpl())).getDefaultScheduleVersion().intValue());
  }

  /**
   * Method under test:
   * {@link AdvancedCycleBusinessCalendar#getDefaultScheduleVersion()}
   */
  @Test
  public void testGetDefaultScheduleVersion2() {
    // Arrange
    AdvancedCycleBusinessCalendar advancedCycleBusinessCalendar = new AdvancedCycleBusinessCalendar(
        new DefaultClockImpl());
    advancedCycleBusinessCalendar.setDefaultScheduleVersion(1);

    // Act and Assert
    assertEquals(1, advancedCycleBusinessCalendar.getDefaultScheduleVersion().intValue());
  }

  /**
   * Method under test:
   * {@link AdvancedCycleBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  public void testResolveDuedate() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new AdvancedCycleBusinessCalendar(new DefaultClockImpl())).resolveDuedate("2020-03-01", 3));
  }

  /**
   * Method under test:
   * {@link AdvancedCycleBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  public void testResolveDuedate2() {
    // Arrange
    ClockReader clockReader = mock(ClockReader.class);
    when(clockReader.getCurrentTimeZone()).thenReturn(TimeZone.getTimeZone("America/Los_Angeles"));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new AdvancedCycleBusinessCalendar(clockReader)).resolveDuedate("DSTZONE", 3));
    verify(clockReader).getCurrentTimeZone();
  }
}
