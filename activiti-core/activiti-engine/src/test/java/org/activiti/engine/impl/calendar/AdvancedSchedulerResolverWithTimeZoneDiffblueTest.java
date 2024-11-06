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
import java.util.TimeZone;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.engine.runtime.ClockReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AdvancedSchedulerResolverWithTimeZoneDiffblueTest {
  @InjectMocks
  private AdvancedSchedulerResolverWithTimeZone advancedSchedulerResolverWithTimeZone;

  /**
   * Test
   * {@link AdvancedSchedulerResolverWithTimeZone#resolve(String, ClockReader, TimeZone)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AdvancedSchedulerResolverWithTimeZone#resolve(String, ClockReader, TimeZone)}
   */
  @Test
  public void testResolve_when42_thenThrowActivitiException() {
    // Arrange
    DefaultClockImpl clockReader = new DefaultClockImpl();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> advancedSchedulerResolverWithTimeZone.resolve("42", clockReader,
        TimeZone.getTimeZone("America/Los_Angeles")));
  }

  /**
   * Test
   * {@link AdvancedSchedulerResolverWithTimeZone#resolve(String, ClockReader, TimeZone)}.
   * <ul>
   *   <li>When {@code 2020-03-01}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AdvancedSchedulerResolverWithTimeZone#resolve(String, ClockReader, TimeZone)}
   */
  @Test
  public void testResolve_when20200301_thenThrowActivitiException() {
    // Arrange
    DefaultClockImpl clockReader = new DefaultClockImpl();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> advancedSchedulerResolverWithTimeZone.resolve("2020-03-01", clockReader,
        TimeZone.getTimeZone("America/Los_Angeles")));
  }

  /**
   * Test
   * {@link AdvancedSchedulerResolverWithTimeZone#resolve(String, ClockReader, TimeZone)}.
   * <ul>
   *   <li>When {@code 2020/03/01}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AdvancedSchedulerResolverWithTimeZone#resolve(String, ClockReader, TimeZone)}
   */
  @Test
  public void testResolve_when20200301_thenThrowActivitiException2() {
    // Arrange
    DefaultClockImpl clockReader = new DefaultClockImpl();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> advancedSchedulerResolverWithTimeZone.resolve("2020/03/01", clockReader,
        TimeZone.getTimeZone("America/Los_Angeles")));
  }

  /**
   * Test
   * {@link AdvancedSchedulerResolverWithTimeZone#resolve(String, ClockReader, TimeZone)}.
   * <ul>
   *   <li>When {@code 20200301}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AdvancedSchedulerResolverWithTimeZone#resolve(String, ClockReader, TimeZone)}
   */
  @Test
  public void testResolve_when20200301_thenThrowActivitiException3() {
    // Arrange
    DefaultClockImpl clockReader = new DefaultClockImpl();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> advancedSchedulerResolverWithTimeZone.resolve("20200301", clockReader,
        TimeZone.getTimeZone("America/Los_Angeles")));
  }

  /**
   * Test
   * {@link AdvancedSchedulerResolverWithTimeZone#resolve(String, ClockReader, TimeZone)}.
   * <ul>
   *   <li>When {@code ,}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AdvancedSchedulerResolverWithTimeZone#resolve(String, ClockReader, TimeZone)}
   */
  @Test
  public void testResolve_whenComma_thenThrowActivitiException() {
    // Arrange
    DefaultClockImpl clockReader = new DefaultClockImpl();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> advancedSchedulerResolverWithTimeZone.resolve(",", clockReader,
        TimeZone.getTimeZone("America/Los_Angeles")));
  }

  /**
   * Test
   * {@link AdvancedSchedulerResolverWithTimeZone#resolve(String, ClockReader, TimeZone)}.
   * <ul>
   *   <li>When {@code Minute and Second values must be between 0 and 59}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AdvancedSchedulerResolverWithTimeZone#resolve(String, ClockReader, TimeZone)}
   */
  @Test
  public void testResolve_whenMinuteAndSecondValuesMustBeBetween0And59() {
    // Arrange
    DefaultClockImpl clockReader = new DefaultClockImpl();

    // Act and Assert
    assertThrows(ActivitiException.class,
        () -> advancedSchedulerResolverWithTimeZone.resolve("Minute and Second values must be between 0 and 59",
            clockReader, TimeZone.getTimeZone("America/Los_Angeles")));
  }

  /**
   * Test
   * {@link AdvancedSchedulerResolverWithTimeZone#resolve(String, ClockReader, TimeZone)}.
   * <ul>
   *   <li>When {@code /}.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AdvancedSchedulerResolverWithTimeZone#resolve(String, ClockReader, TimeZone)}
   */
  @Test
  public void testResolve_whenSlash_thenThrowActivitiException() {
    // Arrange
    DefaultClockImpl clockReader = new DefaultClockImpl();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> advancedSchedulerResolverWithTimeZone.resolve("/", clockReader,
        TimeZone.getTimeZone("America/Los_Angeles")));
  }

  /**
   * Test
   * {@link AdvancedSchedulerResolverWithTimeZone#resolve(String, ClockReader, TimeZone)}.
   * <ul>
   *   <li>When space tab.</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AdvancedSchedulerResolverWithTimeZone#resolve(String, ClockReader, TimeZone)}
   */
  @Test
  public void testResolve_whenSpaceTab_thenThrowActivitiException() {
    // Arrange
    DefaultClockImpl clockReader = new DefaultClockImpl();

    // Act and Assert
    assertThrows(ActivitiException.class, () -> advancedSchedulerResolverWithTimeZone.resolve(" \t", clockReader,
        TimeZone.getTimeZone("America/Los_Angeles")));
  }
}
