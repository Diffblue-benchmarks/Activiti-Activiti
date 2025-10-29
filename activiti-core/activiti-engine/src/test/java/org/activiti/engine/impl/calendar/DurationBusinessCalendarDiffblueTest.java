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
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.runtime.ClockReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DurationBusinessCalendarDiffblueTest {
  @Mock
  private ClockReader clockReader;

  @InjectMocks
  private DurationBusinessCalendar durationBusinessCalendar;

  /**
   * Method under test:
   * {@link DurationBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  public void testResolveDuedate() {
    // Arrange
    when(clockReader.getCurrentTimeZone()).thenReturn(new SimpleTimeZone(-1, "/"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> durationBusinessCalendar.resolveDuedate("2020-03-01", 3));
    verify(clockReader).getCurrentTimeZone();
  }

  /**
   * Method under test:
   * {@link DurationBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  public void testResolveDuedate2() {
    // Arrange
    when(clockReader.getCurrentCalendar()).thenReturn(new GregorianCalendar(1, 1, 1));
    when(clockReader.getCurrentTimeZone()).thenReturn(TimeZone.getTimeZone("America/Los_Angeles"));

    // Act
    durationBusinessCalendar.resolveDuedate("2020/03/01", 3);

    // Assert
    verify(clockReader).getCurrentCalendar();
    verify(clockReader, atLeast(1)).getCurrentTimeZone();
  }

  /**
   * Method under test:
   * {@link DurationBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  public void testResolveDuedate3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> durationBusinessCalendar.resolveDuedate("/", 3));
  }

  /**
   * Method under test:
   * {@link DurationBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  public void testResolveDuedate4() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> durationBusinessCalendar.resolveDuedate("P", 3));
  }

  /**
   * Method under test:
   * {@link DurationBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  public void testResolveDuedate5() {
    // Arrange
    when(clockReader.getCurrentTimeZone()).thenReturn(TimeZone.getTimeZone("America/Los_Angeles"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> durationBusinessCalendar.resolveDuedate("Duedate", 3));
    verify(clockReader).getCurrentTimeZone();
  }

  /**
   * Method under test:
   * {@link DurationBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  public void testResolveDuedate6() {
    // Arrange
    when(clockReader.getCurrentCalendar()).thenThrow(new ActivitiException("An error occurred"));
    when(clockReader.getCurrentTimeZone()).thenReturn(TimeZone.getTimeZone("America/Los_Angeles"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> durationBusinessCalendar.resolveDuedate("2020/03/01", 3));
    verify(clockReader).getCurrentCalendar();
    verify(clockReader, atLeast(1)).getCurrentTimeZone();
  }
}
