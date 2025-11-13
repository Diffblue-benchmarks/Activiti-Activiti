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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.TimeZone;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.engine.runtime.ClockReader;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AdvancedSchedulerResolverWithTimeZoneDiffblueTest {
  /**
   * Test {@link AdvancedSchedulerResolverWithTimeZone#resolve(String, ClockReader, TimeZone)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link AdvancedSchedulerResolverWithTimeZone#resolve(String, ClockReader,
   * TimeZone)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.util.Date AdvancedSchedulerResolverWithTimeZone.resolve(String, ClockReader, TimeZone)"
  })
  public void testResolve_when42_thenThrowActivitiException() {
    // Arrange
    AdvancedSchedulerResolverWithTimeZone advancedSchedulerResolverWithTimeZone =
        new AdvancedSchedulerResolverWithTimeZone();

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            advancedSchedulerResolverWithTimeZone.resolve(
                "42", new DefaultClockImpl(), TimeZone.getTimeZone("America/Los_Angeles")));
  }

  /**
   * Test {@link AdvancedSchedulerResolverWithTimeZone#resolve(String, ClockReader, TimeZone)}.
   *
   * <ul>
   *   <li>When {@code 2020-03-01}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link AdvancedSchedulerResolverWithTimeZone#resolve(String, ClockReader,
   * TimeZone)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.util.Date AdvancedSchedulerResolverWithTimeZone.resolve(String, ClockReader, TimeZone)"
  })
  public void testResolve_when20200301_thenThrowActivitiException() {
    // Arrange
    AdvancedSchedulerResolverWithTimeZone advancedSchedulerResolverWithTimeZone =
        new AdvancedSchedulerResolverWithTimeZone();

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            advancedSchedulerResolverWithTimeZone.resolve(
                "2020-03-01", new DefaultClockImpl(), TimeZone.getTimeZone("America/Los_Angeles")));
  }

  /**
   * Test {@link AdvancedSchedulerResolverWithTimeZone#resolve(String, ClockReader, TimeZone)}.
   *
   * <ul>
   *   <li>When {@code 2020/03/01}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link AdvancedSchedulerResolverWithTimeZone#resolve(String, ClockReader,
   * TimeZone)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.util.Date AdvancedSchedulerResolverWithTimeZone.resolve(String, ClockReader, TimeZone)"
  })
  public void testResolve_when20200301_thenThrowActivitiException2() {
    // Arrange
    AdvancedSchedulerResolverWithTimeZone advancedSchedulerResolverWithTimeZone =
        new AdvancedSchedulerResolverWithTimeZone();

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            advancedSchedulerResolverWithTimeZone.resolve(
                "2020/03/01", new DefaultClockImpl(), TimeZone.getTimeZone("America/Los_Angeles")));
  }

  /**
   * Test {@link AdvancedSchedulerResolverWithTimeZone#resolve(String, ClockReader, TimeZone)}.
   *
   * <ul>
   *   <li>When {@code 20200301}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link AdvancedSchedulerResolverWithTimeZone#resolve(String, ClockReader,
   * TimeZone)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.util.Date AdvancedSchedulerResolverWithTimeZone.resolve(String, ClockReader, TimeZone)"
  })
  public void testResolve_when20200301_thenThrowActivitiException3() {
    // Arrange
    AdvancedSchedulerResolverWithTimeZone advancedSchedulerResolverWithTimeZone =
        new AdvancedSchedulerResolverWithTimeZone();

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            advancedSchedulerResolverWithTimeZone.resolve(
                "20200301", new DefaultClockImpl(), TimeZone.getTimeZone("America/Los_Angeles")));
  }

  /**
   * Test {@link AdvancedSchedulerResolverWithTimeZone#resolve(String, ClockReader, TimeZone)}.
   *
   * <ul>
   *   <li>When {@code ,}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link AdvancedSchedulerResolverWithTimeZone#resolve(String, ClockReader,
   * TimeZone)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.util.Date AdvancedSchedulerResolverWithTimeZone.resolve(String, ClockReader, TimeZone)"
  })
  public void testResolve_whenComma_thenThrowActivitiException() {
    // Arrange
    AdvancedSchedulerResolverWithTimeZone advancedSchedulerResolverWithTimeZone =
        new AdvancedSchedulerResolverWithTimeZone();

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            advancedSchedulerResolverWithTimeZone.resolve(
                ",", new DefaultClockImpl(), TimeZone.getTimeZone("America/Los_Angeles")));
  }

  /**
   * Test {@link AdvancedSchedulerResolverWithTimeZone#resolve(String, ClockReader, TimeZone)}.
   *
   * <ul>
   *   <li>When {@code Minute and Second values must be between 0 and 59}.
   * </ul>
   *
   * <p>Method under test: {@link AdvancedSchedulerResolverWithTimeZone#resolve(String, ClockReader,
   * TimeZone)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.util.Date AdvancedSchedulerResolverWithTimeZone.resolve(String, ClockReader, TimeZone)"
  })
  public void testResolve_whenMinuteAndSecondValuesMustBeBetween0And59() {
    // Arrange
    AdvancedSchedulerResolverWithTimeZone advancedSchedulerResolverWithTimeZone =
        new AdvancedSchedulerResolverWithTimeZone();

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            advancedSchedulerResolverWithTimeZone.resolve(
                "Minute and Second values must be between 0 and 59",
                new DefaultClockImpl(),
                TimeZone.getTimeZone("America/Los_Angeles")));
  }

  /**
   * Test {@link AdvancedSchedulerResolverWithTimeZone#resolve(String, ClockReader, TimeZone)}.
   *
   * <ul>
   *   <li>When {@code /}.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link AdvancedSchedulerResolverWithTimeZone#resolve(String, ClockReader,
   * TimeZone)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.util.Date AdvancedSchedulerResolverWithTimeZone.resolve(String, ClockReader, TimeZone)"
  })
  public void testResolve_whenSlash_thenThrowActivitiException() {
    // Arrange
    AdvancedSchedulerResolverWithTimeZone advancedSchedulerResolverWithTimeZone =
        new AdvancedSchedulerResolverWithTimeZone();

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            advancedSchedulerResolverWithTimeZone.resolve(
                "/", new DefaultClockImpl(), TimeZone.getTimeZone("America/Los_Angeles")));
  }

  /**
   * Test {@link AdvancedSchedulerResolverWithTimeZone#resolve(String, ClockReader, TimeZone)}.
   *
   * <ul>
   *   <li>When space tab.
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link AdvancedSchedulerResolverWithTimeZone#resolve(String, ClockReader,
   * TimeZone)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.util.Date AdvancedSchedulerResolverWithTimeZone.resolve(String, ClockReader, TimeZone)"
  })
  public void testResolve_whenSpaceTab_thenThrowActivitiException() {
    // Arrange
    AdvancedSchedulerResolverWithTimeZone advancedSchedulerResolverWithTimeZone =
        new AdvancedSchedulerResolverWithTimeZone();

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () ->
            advancedSchedulerResolverWithTimeZone.resolve(
                " \t", new DefaultClockImpl(), TimeZone.getTimeZone("America/Los_Angeles")));
  }
}
