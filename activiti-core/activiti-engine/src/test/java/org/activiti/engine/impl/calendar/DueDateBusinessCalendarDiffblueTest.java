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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.runtime.ClockReader;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.postgresql.util.PGTime;

@RunWith(MockitoJUnitRunner.class)
public class DueDateBusinessCalendarDiffblueTest {
  @Mock private ClockReader clockReader;

  @InjectMocks private DueDateBusinessCalendar dueDateBusinessCalendar;

  /**
   * Test {@link DueDateBusinessCalendar#resolveDuedate(String, int)} with {@code duedate}, {@code
   * maxIterations}.
   *
   * <p>Method under test: {@link DueDateBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Date DueDateBusinessCalendar.resolveDuedate(String, int)"})
  public void testResolveDuedateWithDuedateMaxIterations() {
    // Arrange
    when(clockReader.getCurrentTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act
    dueDateBusinessCalendar.resolveDuedate("P", 3);

    // Assert
    verify(clockReader).getCurrentTime();
  }

  /**
   * Test {@link DueDateBusinessCalendar#resolveDuedate(String, int)} with {@code duedate}, {@code
   * maxIterations}.
   *
   * <p>Method under test: {@link DueDateBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Date DueDateBusinessCalendar.resolveDuedate(String, int)"})
  public void testResolveDuedateWithDuedateMaxIterations2() {
    // Arrange
    when(clockReader.getCurrentTime()).thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dueDateBusinessCalendar.resolveDuedate("P", 3));
    verify(clockReader).getCurrentTime();
  }

  /**
   * Test {@link DueDateBusinessCalendar#resolveDuedate(String, int)} with {@code duedate}, {@code
   * maxIterations}.
   *
   * <ul>
   *   <li>Then calls {@link PGTime#getTime()}.
   * </ul>
   *
   * <p>Method under test: {@link DueDateBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Date DueDateBusinessCalendar.resolveDuedate(String, int)"})
  public void testResolveDuedateWithDuedateMaxIterations_thenCallsGetTime() {
    // Arrange
    PGTime pgTime = mock(PGTime.class);
    when(pgTime.getTime()).thenThrow(new ActivitiException("An error occurred"));
    when(clockReader.getCurrentTime()).thenReturn(pgTime);

    // Act and Assert
    assertThrows(ActivitiException.class, () -> dueDateBusinessCalendar.resolveDuedate("P", 3));
    verify(pgTime).getTime();
    verify(clockReader).getCurrentTime();
  }

  /**
   * Test {@link DueDateBusinessCalendar#resolveDuedate(String, int)} with {@code duedate}, {@code
   * maxIterations}.
   *
   * <ul>
   *   <li>When {@code 2020/03/01}.
   * </ul>
   *
   * <p>Method under test: {@link DueDateBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Date DueDateBusinessCalendar.resolveDuedate(String, int)"})
  public void testResolveDuedateWithDuedateMaxIterations_when20200301() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class, () -> dueDateBusinessCalendar.resolveDuedate("2020/03/01", 3));
  }

  /**
   * Test {@link DueDateBusinessCalendar#resolveDuedate(String, int)} with {@code duedate}, {@code
   * maxIterations}.
   *
   * <ul>
   *   <li>When {@code Duedate}.
   * </ul>
   *
   * <p>Method under test: {@link DueDateBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Date DueDateBusinessCalendar.resolveDuedate(String, int)"})
  public void testResolveDuedateWithDuedateMaxIterations_whenDuedate() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiException.class, () -> dueDateBusinessCalendar.resolveDuedate("Duedate", 3));
  }
}
