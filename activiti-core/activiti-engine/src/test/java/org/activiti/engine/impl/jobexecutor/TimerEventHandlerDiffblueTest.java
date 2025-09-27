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
package org.activiti.engine.impl.jobexecutor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class TimerEventHandlerDiffblueTest {
  /**
   * Test {@link TimerEventHandler#createConfiguration(String, String, String)}.
   *
   * <p>Method under test: {@link TimerEventHandler#createConfiguration(String, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TimerEventHandler.createConfiguration(String, String, String)"})
  public void testCreateConfiguration() {
    // Arrange and Act
    String actualCreateConfigurationResult =
        TimerEventHandler.createConfiguration("42", "2020-03-01", "Calendar Name");

    // Assert
    assertEquals(
        "{\"activityId\":\"42\",\"calendarName\":\"Calendar Name\",\"timerEndDate\":\"2020-03-01\"}",
        actualCreateConfigurationResult);
  }

  /**
   * Test {@link TimerEventHandler#createConfiguration(String, String, String)}.
   *
   * <p>Method under test: {@link TimerEventHandler#createConfiguration(String, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TimerEventHandler.createConfiguration(String, String, String)"})
  public void testCreateConfiguration2() {
    // Arrange and Act
    String actualCreateConfigurationResult =
        TimerEventHandler.createConfiguration("", "2020-03-01", "Calendar Name");

    // Assert
    assertEquals(
        "{\"activityId\":\"\",\"calendarName\":\"Calendar Name\",\"timerEndDate\":\"2020-03-01\"}",
        actualCreateConfigurationResult);
  }

  /**
   * Test {@link TimerEventHandler#createConfiguration(String, String, String)}.
   *
   * <p>Method under test: {@link TimerEventHandler#createConfiguration(String, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TimerEventHandler.createConfiguration(String, String, String)"})
  public void testCreateConfiguration3() {
    // Arrange and Act
    String actualCreateConfigurationResult =
        TimerEventHandler.createConfiguration("42", "2020/03/01", "Calendar Name");

    // Assert
    assertEquals(
        "{\"activityId\":\"42\",\"calendarName\":\"Calendar Name\",\"timerEndDate\":\"2020/03/01\"}",
        actualCreateConfigurationResult);
  }

  /**
   * Test {@link TimerEventHandler#createConfiguration(String, String, String)}.
   *
   * <ul>
   *   <li>Then return {@code {"activityId":"","calendarName":"Calendar
   *       Name","timerEndDate":"\"\""}}.
   * </ul>
   *
   * <p>Method under test: {@link TimerEventHandler#createConfiguration(String, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TimerEventHandler.createConfiguration(String, String, String)"})
  public void testCreateConfiguration_thenReturnActivityIdCalendarNameCalendarNameTimerEndDate() {
    // Arrange and Act
    String actualCreateConfigurationResult =
        TimerEventHandler.createConfiguration("", "\"\"", "Calendar Name");

    // Assert
    assertEquals(
        "{\"activityId\":\"\",\"calendarName\":\"Calendar Name\",\"timerEndDate\":\"\\\"\\\"\"}",
        actualCreateConfigurationResult);
  }

  /**
   * Test {@link TimerEventHandler#createConfiguration(String, String, String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code {}}.
   * </ul>
   *
   * <p>Method under test: {@link TimerEventHandler#createConfiguration(String, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TimerEventHandler.createConfiguration(String, String, String)"})
  public void testCreateConfiguration_whenNull_thenReturnLeftCurlyBracketRightCurlyBracket() {
    // Arrange and Act
    String actualCreateConfigurationResult =
        TimerEventHandler.createConfiguration(null, null, null);

    // Assert
    assertEquals("{}", actualCreateConfigurationResult);
  }

  /**
   * Test {@link TimerEventHandler#setActivityIdToConfiguration(String, String)}.
   *
   * <ul>
   *   <li>Then return {@code Job Handler Configuration}.
   * </ul>
   *
   * <p>Method under test: {@link TimerEventHandler#setActivityIdToConfiguration(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TimerEventHandler.setActivityIdToConfiguration(String, String)"})
  public void testSetActivityIdToConfiguration_thenReturnJobHandlerConfiguration() {
    // Arrange, Act and Assert
    assertEquals(
        "Job Handler Configuration",
        TimerEventHandler.setActivityIdToConfiguration("Job Handler Configuration", "42"));
  }

  /**
   * Test {@link TimerEventHandler#setActivityIdToConfiguration(String, String)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link TimerEventHandler#setActivityIdToConfiguration(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TimerEventHandler.setActivityIdToConfiguration(String, String)"})
  public void testSetActivityIdToConfiguration_whenEmptyString_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals("", TimerEventHandler.setActivityIdToConfiguration("", "42"));
  }

  /**
   * Test {@link TimerEventHandler#getActivityIdFromConfiguration(String)}.
   *
   * <ul>
   *   <li>Then return {@code Job Handler Configuration}.
   * </ul>
   *
   * <p>Method under test: {@link TimerEventHandler#getActivityIdFromConfiguration(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TimerEventHandler.getActivityIdFromConfiguration(String)"})
  public void testGetActivityIdFromConfiguration_thenReturnJobHandlerConfiguration() {
    // Arrange, Act and Assert
    assertEquals(
        "Job Handler Configuration",
        TimerEventHandler.getActivityIdFromConfiguration("Job Handler Configuration"));
  }

  /**
   * Test {@link TimerEventHandler#getActivityIdFromConfiguration(String)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link TimerEventHandler#getActivityIdFromConfiguration(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TimerEventHandler.getActivityIdFromConfiguration(String)"})
  public void testGetActivityIdFromConfiguration_whenEmptyString_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals("", TimerEventHandler.getActivityIdFromConfiguration(""));
  }

  /**
   * Test {@link TimerEventHandler#geCalendarNameFromConfiguration(String)}.
   *
   * <ul>
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link TimerEventHandler#geCalendarNameFromConfiguration(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TimerEventHandler.geCalendarNameFromConfiguration(String)"})
  public void testGeCalendarNameFromConfiguration_whenEmptyString() {
    // Arrange, Act and Assert
    assertEquals("", TimerEventHandler.geCalendarNameFromConfiguration(""));
  }

  /**
   * Test {@link TimerEventHandler#geCalendarNameFromConfiguration(String)}.
   *
   * <ul>
   *   <li>When {@code Job Handler Configuration}.
   * </ul>
   *
   * <p>Method under test: {@link TimerEventHandler#geCalendarNameFromConfiguration(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TimerEventHandler.geCalendarNameFromConfiguration(String)"})
  public void testGeCalendarNameFromConfiguration_whenJobHandlerConfiguration() {
    // Arrange, Act and Assert
    assertEquals(
        "", TimerEventHandler.geCalendarNameFromConfiguration("Job Handler Configuration"));
  }

  /**
   * Test {@link TimerEventHandler#setEndDateToConfiguration(String, String)}.
   *
   * <p>Method under test: {@link TimerEventHandler#setEndDateToConfiguration(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TimerEventHandler.setEndDateToConfiguration(String, String)"})
  public void testSetEndDateToConfiguration() {
    // Arrange, Act and Assert
    assertEquals(
        "{\"activityId\":\"Job Handler Configuration\",\"timerEndDate\":\"2020-03-01\"}",
        TimerEventHandler.setEndDateToConfiguration("Job Handler Configuration", "2020-03-01"));
  }

  /**
   * Test {@link TimerEventHandler#setEndDateToConfiguration(String, String)}.
   *
   * <p>Method under test: {@link TimerEventHandler#setEndDateToConfiguration(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TimerEventHandler.setEndDateToConfiguration(String, String)"})
  public void testSetEndDateToConfiguration2() {
    // Arrange, Act and Assert
    assertEquals(
        "{\"activityId\":\"Job Handler Configuration\",\"timerEndDate\":\"2020/03/01\"}",
        TimerEventHandler.setEndDateToConfiguration("Job Handler Configuration", "2020/03/01"));
  }

  /**
   * Test {@link TimerEventHandler#setEndDateToConfiguration(String, String)}.
   *
   * <ul>
   *   <li>Then return {@code {"activityId":"Job Handler Configuration"}}.
   * </ul>
   *
   * <p>Method under test: {@link TimerEventHandler#setEndDateToConfiguration(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TimerEventHandler.setEndDateToConfiguration(String, String)"})
  public void testSetEndDateToConfiguration_thenReturnActivityIdJobHandlerConfiguration() {
    // Arrange, Act and Assert
    assertEquals(
        "{\"activityId\":\"Job Handler Configuration\"}",
        TimerEventHandler.setEndDateToConfiguration("Job Handler Configuration", null));
  }

  /**
   * Test {@link TimerEventHandler#setEndDateToConfiguration(String, String)}.
   *
   * <ul>
   *   <li>Then return {@code {"activityId":"","timerEndDate":"\"\""}}.
   * </ul>
   *
   * <p>Method under test: {@link TimerEventHandler#setEndDateToConfiguration(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TimerEventHandler.setEndDateToConfiguration(String, String)"})
  public void testSetEndDateToConfiguration_thenReturnActivityIdTimerEndDate() {
    // Arrange, Act and Assert
    assertEquals(
        "{\"activityId\":\"\",\"timerEndDate\":\"\\\"\\\"\"}",
        TimerEventHandler.setEndDateToConfiguration("", "\"\""));
  }

  /**
   * Test {@link TimerEventHandler#setEndDateToConfiguration(String, String)}.
   * <ul>
   *   <li>Then return {@code {"activityId":"{","timerEndDate":"2020-03-01"}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TimerEventHandler#setEndDateToConfiguration(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TimerEventHandler.setEndDateToConfiguration(String, String)"})
  public void testSetEndDateToConfiguration_thenReturnActivityIdTimerEndDate20200301() {
    // Arrange, Act and Assert
    assertEquals(
        "{\"activityId\":\"{\",\"timerEndDate\":\"2020-03-01\"}",
        TimerEventHandler.setEndDateToConfiguration("{", "2020-03-01"));
  }

  /**
   * Test {@link TimerEventHandler#setEndDateToConfiguration(String, String)}.
   *
   * <ul>
   *   <li>Then return {@code {"activityId":"","timerEndDate":"2020-03-01"}}.
   * </ul>
   *
   * <p>Method under test: {@link TimerEventHandler#setEndDateToConfiguration(String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TimerEventHandler.setEndDateToConfiguration(String, String)"})
  public void testSetEndDateToConfiguration_thenReturnActivityIdTimerEndDate202003012() {
    // Arrange, Act and Assert
    assertEquals(
        "{\"activityId\":\"\",\"timerEndDate\":\"2020-03-01\"}",
        TimerEventHandler.setEndDateToConfiguration("", "2020-03-01"));
  }

  /**
   * Test {@link TimerEventHandler#getEndDateFromConfiguration(String)}.
   *
   * <ul>
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link TimerEventHandler#getEndDateFromConfiguration(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TimerEventHandler.getEndDateFromConfiguration(String)"})
  public void testGetEndDateFromConfiguration_whenEmptyString() {
    // Arrange, Act and Assert
    assertNull(TimerEventHandler.getEndDateFromConfiguration(""));
  }

  /**
   * Test {@link TimerEventHandler#getEndDateFromConfiguration(String)}.
   *
   * <ul>
   *   <li>When {@code Job Handler Configuration}.
   * </ul>
   *
   * <p>Method under test: {@link TimerEventHandler#getEndDateFromConfiguration(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String TimerEventHandler.getEndDateFromConfiguration(String)"})
  public void testGetEndDateFromConfiguration_whenJobHandlerConfiguration() {
    // Arrange, Act and Assert
    assertNull(TimerEventHandler.getEndDateFromConfiguration("Job Handler Configuration"));
  }
}
