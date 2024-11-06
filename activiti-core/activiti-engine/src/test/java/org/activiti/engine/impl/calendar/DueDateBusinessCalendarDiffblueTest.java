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

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.runtime.ClockReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DueDateBusinessCalendarDiffblueTest {
  @Mock
  private ClockReader clockReader;

  @InjectMocks
  private DueDateBusinessCalendar dueDateBusinessCalendar;

  /**
   * Test {@link DueDateBusinessCalendar#resolveDuedate(String, int)} with
   * {@code duedate}, {@code maxIterations}.
   * <p>
   * Method under test:
   * {@link DueDateBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  public void testResolveDuedateWithDuedateMaxIterations() {
    // Arrange
    when(clockReader.getCurrentTime())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act
    dueDateBusinessCalendar.resolveDuedate("P", 3);

    // Assert
    verify(clockReader).getCurrentTime();
  }

  /**
   * Test {@link DueDateBusinessCalendar#resolveDuedate(String, int)} with
   * {@code duedate}, {@code maxIterations}.
   * <ul>
   *   <li>Given {@link ClockReader}.</li>
   *   <li>When {@code 2020/03/01}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DueDateBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  public void testResolveDuedateWithDuedateMaxIterations_givenClockReader_when20200301() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> dueDateBusinessCalendar.resolveDuedate("2020/03/01", 3));
  }

  /**
   * Test {@link DueDateBusinessCalendar#resolveDuedate(String, int)} with
   * {@code duedate}, {@code maxIterations}.
   * <ul>
   *   <li>Then calls {@link java.util.Date#getTime()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DueDateBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  public void testResolveDuedateWithDuedateMaxIterations_thenCallsGetTime() {
    // Arrange
    java.sql.Date date = mock(java.sql.Date.class);
    when(date.getTime()).thenThrow(new ActivitiException("An error occurred"));
    when(clockReader.getCurrentTime()).thenReturn(date);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dueDateBusinessCalendar.resolveDuedate("P", 3));
    verify(date).getTime();
    verify(clockReader).getCurrentTime();
  }

  /**
   * Test {@link DueDateBusinessCalendar#resolveDuedate(String, int)} with
   * {@code duedate}, {@code maxIterations}.
   * <ul>
   *   <li>When {@code Duedate}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DueDateBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  public void testResolveDuedateWithDuedateMaxIterations_whenDuedate() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> dueDateBusinessCalendar.resolveDuedate("Duedate", 3));
  }
}
