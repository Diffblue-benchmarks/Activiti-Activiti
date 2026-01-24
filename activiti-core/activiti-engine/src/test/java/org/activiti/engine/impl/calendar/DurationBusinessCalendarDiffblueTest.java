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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.engine.runtime.ClockReader;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DurationBusinessCalendarDiffblueTest {
  @Mock private ClockReader clockReader;

  @InjectMocks private DurationBusinessCalendar durationBusinessCalendar;

  /**
   * Test {@link DurationBusinessCalendar#resolveDuedate(String, int)} with {@code duedate}, {@code
   * maxIterations}.
   *
   * <p>Method under test: {@link DurationBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Date DurationBusinessCalendar.resolveDuedate(String, int)"})
  public void testResolveDuedateWithDuedateMaxIterations() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> new DurationBusinessCalendar(new DefaultClockImpl()).resolveDuedate("", 3));
  }

  /**
   * Test {@link DurationBusinessCalendar#resolveDuedate(String, int)} with {@code duedate}, {@code
   * maxIterations}.
   *
   * <p>Method under test: {@link DurationBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Date DurationBusinessCalendar.resolveDuedate(String, int)"})
  public void testResolveDuedateWithDuedateMaxIterations2() {
    // Arrange
    ClockReader clockReader = mock(ClockReader.class);
    when(clockReader.getCurrentTimeZone()).thenReturn(new SimpleTimeZone(-1, "/"));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> new DurationBusinessCalendar(clockReader).resolveDuedate("2020-03-01", 3));
    verify(clockReader).getCurrentTimeZone();
  }

  /**
   * Test {@link DurationBusinessCalendar#resolveDuedate(String, int)} with {@code duedate}, {@code
   * maxIterations}.
   *
   * <p>Method under test: {@link DurationBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Date DurationBusinessCalendar.resolveDuedate(String, int)"})
  public void testResolveDuedateWithDuedateMaxIterations3() {
    // Arrange
    ClockReader clockReader = mock(ClockReader.class);
    when(clockReader.getCurrentCalendar()).thenThrow(new ActivitiException("An error occurred"));
    when(clockReader.getCurrentTimeZone()).thenReturn(TimeZone.getTimeZone("America/Los_Angeles"));

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> new DurationBusinessCalendar(clockReader).resolveDuedate("2020/03/01", 3));
    verify(clockReader).getCurrentCalendar();
    verify(clockReader, atLeast(1)).getCurrentTimeZone();
  }

  /**
   * Test {@link DurationBusinessCalendar#resolveDuedate(String, int)} with {@code duedate}, {@code
   * maxIterations}.
   *
   * <p>Method under test: {@link DurationBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Date DurationBusinessCalendar.resolveDuedate(String, int)"})
  public void testResolveDuedateWithDuedateMaxIterations4() {
    // Arrange
    ClockReader clockReader = mock(ClockReader.class);
    when(clockReader.getCurrentCalendar()).thenReturn(new GregorianCalendar(1, 1, 1));
    when(clockReader.getCurrentTimeZone()).thenReturn(TimeZone.getTimeZone("/"));

    // Act
    new DurationBusinessCalendar(clockReader).resolveDuedate("2020/03/01", 3);

    // Assert
    verify(clockReader).getCurrentCalendar();
    verify(clockReader, atLeast(1)).getCurrentTimeZone();
  }

  /**
   * Test {@link DurationBusinessCalendar#resolveDuedate(String, int)} with {@code duedate}, {@code
   * maxIterations}.
   *
   * <p>Method under test: {@link DurationBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Date DurationBusinessCalendar.resolveDuedate(String, int)"})
  public void testResolveDuedateWithDuedateMaxIterations5() {
    // Arrange
    ClockReader clockReader = mock(ClockReader.class);
    when(clockReader.getCurrentCalendar()).thenReturn(new GregorianCalendar(1, 1, 1));
    when(clockReader.getCurrentTimeZone()).thenReturn(null);

    // Act
    new DurationBusinessCalendar(clockReader).resolveDuedate("2020/03/01", 3);

    // Assert
    verify(clockReader).getCurrentCalendar();
    verify(clockReader, atLeast(1)).getCurrentTimeZone();
  }

  /**
   * Test {@link DurationBusinessCalendar#resolveDuedate(String, int)} with {@code duedate}, {@code
   * maxIterations}.
   *
   * <p>Method under test: {@link DurationBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Date DurationBusinessCalendar.resolveDuedate(String, int)"})
  public void testResolveDuedateWithDuedateMaxIterations6() {
    // Arrange
    when(clockReader.getCurrentTimeZone()).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiException.class, () -> durationBusinessCalendar.resolveDuedate("2020-03-01", 3));
    verify(clockReader).getCurrentTimeZone();
  }

  /**
   * Test {@link DurationBusinessCalendar#resolveDuedate(String, int)} with {@code duedate}, {@code
   * maxIterations}.
   *
   * <ul>
   *   <li>Then calls {@link ClockReader#getCurrentCalendar()}.
   * </ul>
   *
   * <p>Method under test: {@link DurationBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Date DurationBusinessCalendar.resolveDuedate(String, int)"})
  public void testResolveDuedateWithDuedateMaxIterations_thenCallsGetCurrentCalendar() {
    // Arrange
    ClockReader clockReader = mock(ClockReader.class);
    when(clockReader.getCurrentCalendar()).thenReturn(new GregorianCalendar(1, 1, 1));
    when(clockReader.getCurrentTimeZone()).thenReturn(TimeZone.getTimeZone("America/Los_Angeles"));

    // Act
    new DurationBusinessCalendar(clockReader).resolveDuedate("2020/03/01", 3);

    // Assert
    verify(clockReader).getCurrentCalendar();
    verify(clockReader, atLeast(1)).getCurrentTimeZone();
  }

  /**
   * Test {@link DurationBusinessCalendar#resolveDuedate(String, int)} with {@code duedate}, {@code
   * maxIterations}.
   *
   * <ul>
   *   <li>When {@code P}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link DurationBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Date DurationBusinessCalendar.resolveDuedate(String, int)"})
  public void testResolveDuedateWithDuedateMaxIterations_whenP_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> new DurationBusinessCalendar(mock(ClockReader.class)).resolveDuedate("P", 3));
  }

  /**
   * Test {@link DurationBusinessCalendar#resolveDuedate(String, int)} with {@code duedate}, {@code
   * maxIterations}.
   *
   * <ul>
   *   <li>When {@code /}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link DurationBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.Date DurationBusinessCalendar.resolveDuedate(String, int)"})
  public void testResolveDuedateWithDuedateMaxIterations_whenSlash_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> new DurationBusinessCalendar(mock(ClockReader.class)).resolveDuedate("/", 3));
  }
}
