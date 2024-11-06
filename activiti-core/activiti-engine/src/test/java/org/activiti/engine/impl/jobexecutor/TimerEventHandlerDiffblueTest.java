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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TimerEventHandlerDiffblueTest {
  @InjectMocks
  private TimerEventHandler timerEventHandler;

  /**
   * Test {@link TimerEventHandler#createConfiguration(String, String, String)}.
   * <p>
   * Method under test:
   * {@link TimerEventHandler#createConfiguration(String, String, String)}
   */
  @Test
  public void testCreateConfiguration() {
    // Arrange, Act and Assert
    assertEquals("{\"activityId\":\"42\",\"calendarName\":\"Calendar Name\",\"timerEndDate\":\"2020-03-01\"}",
        TimerEventHandler.createConfiguration("42", "2020-03-01", "Calendar Name"));
    assertEquals("{\"activityId\":\"\",\"calendarName\":\"Calendar Name\",\"timerEndDate\":\"2020-03-01\"}",
        TimerEventHandler.createConfiguration("", "2020-03-01", "Calendar Name"));
    assertEquals("{\"activityId\":\"42\",\"calendarName\":\"Calendar Name\",\"timerEndDate\":\"2020/03/01\"}",
        TimerEventHandler.createConfiguration("42", "2020/03/01", "Calendar Name"));
  }

  /**
   * Test {@link TimerEventHandler#createConfiguration(String, String, String)}.
   * <ul>
   *   <li>Then return {@code {"activityId":"","calendarName":"Calendar
   * Name","timerEndDate":"\"\""}}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerEventHandler#createConfiguration(String, String, String)}
   */
  @Test
  public void testCreateConfiguration_thenReturnActivityIdCalendarNameCalendarNameTimerEndDate() {
    // Arrange, Act and Assert
    assertEquals("{\"activityId\":\"\",\"calendarName\":\"Calendar Name\",\"timerEndDate\":\"\\\"\\\"\"}",
        TimerEventHandler.createConfiguration("", "\"\"", "Calendar Name"));
  }

  /**
   * Test {@link TimerEventHandler#createConfiguration(String, String, String)}.
   * <ul>
   *   <li>Then return {@code {"calendarName":"Calendar
   * Name","timerEndDate":"2020-03-01"}}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerEventHandler#createConfiguration(String, String, String)}
   */
  @Test
  public void testCreateConfiguration_thenReturnCalendarNameCalendarNameTimerEndDate20200301() {
    // Arrange, Act and Assert
    assertEquals("{\"calendarName\":\"Calendar Name\",\"timerEndDate\":\"2020-03-01\"}",
        TimerEventHandler.createConfiguration(null, "2020-03-01", "Calendar Name"));
  }

  /**
   * Test {@link TimerEventHandler#createConfiguration(String, String, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code {"activityId":"42","calendarName":"Calendar
   * Name"}}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerEventHandler#createConfiguration(String, String, String)}
   */
  @Test
  public void testCreateConfiguration_whenNull_thenReturnActivityId42CalendarNameCalendarName() {
    // Arrange, Act and Assert
    assertEquals("{\"activityId\":\"42\",\"calendarName\":\"Calendar Name\"}",
        TimerEventHandler.createConfiguration("42", null, "Calendar Name"));
  }

  /**
   * Test {@link TimerEventHandler#createConfiguration(String, String, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code {"activityId":"42","timerEndDate":"2020-03-01"}}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerEventHandler#createConfiguration(String, String, String)}
   */
  @Test
  public void testCreateConfiguration_whenNull_thenReturnActivityId42TimerEndDate20200301() {
    // Arrange, Act and Assert
    assertEquals("{\"activityId\":\"42\",\"timerEndDate\":\"2020-03-01\"}",
        TimerEventHandler.createConfiguration("42", "2020-03-01", null));
  }

  /**
   * Test {@link TimerEventHandler#setActivityIdToConfiguration(String, String)}.
   * <ul>
   *   <li>Then return {@code Job Handler Configuration}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerEventHandler#setActivityIdToConfiguration(String, String)}
   */
  @Test
  public void testSetActivityIdToConfiguration_thenReturnJobHandlerConfiguration() {
    // Arrange, Act and Assert
    assertEquals("Job Handler Configuration",
        TimerEventHandler.setActivityIdToConfiguration("Job Handler Configuration", "42"));
  }

  /**
   * Test {@link TimerEventHandler#setActivityIdToConfiguration(String, String)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerEventHandler#setActivityIdToConfiguration(String, String)}
   */
  @Test
  public void testSetActivityIdToConfiguration_whenEmptyString_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals("", TimerEventHandler.setActivityIdToConfiguration("", "42"));
  }

  /**
   * Test {@link TimerEventHandler#getActivityIdFromConfiguration(String)}.
   * <ul>
   *   <li>Then return {@code Job Handler Configuration}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerEventHandler#getActivityIdFromConfiguration(String)}
   */
  @Test
  public void testGetActivityIdFromConfiguration_thenReturnJobHandlerConfiguration() {
    // Arrange, Act and Assert
    assertEquals("Job Handler Configuration",
        TimerEventHandler.getActivityIdFromConfiguration("Job Handler Configuration"));
  }

  /**
   * Test {@link TimerEventHandler#getActivityIdFromConfiguration(String)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerEventHandler#getActivityIdFromConfiguration(String)}
   */
  @Test
  public void testGetActivityIdFromConfiguration_whenEmptyString_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals("", TimerEventHandler.getActivityIdFromConfiguration(""));
  }

  /**
   * Test {@link TimerEventHandler#geCalendarNameFromConfiguration(String)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerEventHandler#geCalendarNameFromConfiguration(String)}
   */
  @Test
  public void testGeCalendarNameFromConfiguration_whenEmptyString() {
    // Arrange, Act and Assert
    assertEquals("", TimerEventHandler.geCalendarNameFromConfiguration(""));
  }

  /**
   * Test {@link TimerEventHandler#geCalendarNameFromConfiguration(String)}.
   * <ul>
   *   <li>When {@code Job Handler Configuration}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerEventHandler#geCalendarNameFromConfiguration(String)}
   */
  @Test
  public void testGeCalendarNameFromConfiguration_whenJobHandlerConfiguration() {
    // Arrange, Act and Assert
    assertEquals("", TimerEventHandler.geCalendarNameFromConfiguration("Job Handler Configuration"));
  }

  /**
   * Test {@link TimerEventHandler#setEndDateToConfiguration(String, String)}.
   * <p>
   * Method under test:
   * {@link TimerEventHandler#setEndDateToConfiguration(String, String)}
   */
  @Test
  public void testSetEndDateToConfiguration() {
    // Arrange, Act and Assert
    assertEquals("{\"activityId\":\"Job Handler Configuration\",\"timerEndDate\":\"2020-03-01\"}",
        TimerEventHandler.setEndDateToConfiguration("Job Handler Configuration", "2020-03-01"));
    assertEquals("{\"activityId\":\"Job Handler Configuration\",\"timerEndDate\":\"2020/03/01\"}",
        TimerEventHandler.setEndDateToConfiguration("Job Handler Configuration", "2020/03/01"));
  }

  /**
   * Test {@link TimerEventHandler#setEndDateToConfiguration(String, String)}.
   * <ul>
   *   <li>Then return {@code {"activityId":"Job Handler Configuration"}}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerEventHandler#setEndDateToConfiguration(String, String)}
   */
  @Test
  public void testSetEndDateToConfiguration_thenReturnActivityIdJobHandlerConfiguration() {
    // Arrange, Act and Assert
    assertEquals("{\"activityId\":\"Job Handler Configuration\"}",
        TimerEventHandler.setEndDateToConfiguration("Job Handler Configuration", null));
  }

  /**
   * Test {@link TimerEventHandler#setEndDateToConfiguration(String, String)}.
   * <ul>
   *   <li>Then return {@code {"activityId":"","timerEndDate":"\"\""}}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerEventHandler#setEndDateToConfiguration(String, String)}
   */
  @Test
  public void testSetEndDateToConfiguration_thenReturnActivityIdTimerEndDate() {
    // Arrange, Act and Assert
    assertEquals("{\"activityId\":\"\",\"timerEndDate\":\"\\\"\\\"\"}",
        TimerEventHandler.setEndDateToConfiguration("", "\"\""));
  }

  /**
   * Test {@link TimerEventHandler#setEndDateToConfiguration(String, String)}.
   * <ul>
   *   <li>Then return {@code {"activityId":"{","timerEndDate":"2020-03-01"}}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TimerEventHandler#setEndDateToConfiguration(String,
   * String)}
   */
  @Test
  public void testSetEndDateToConfiguration_thenReturnActivityIdTimerEndDate20200301() {
    // Arrange, Act and Assert
    assertEquals("{\"activityId\":\"{\",\"timerEndDate\":\"2020-03-01\"}",
        TimerEventHandler.setEndDateToConfiguration("{", "2020-03-01"));
    assertEquals("{\"activityId\":\"\",\"timerEndDate\":\"2020-03-01\"}",
        TimerEventHandler.setEndDateToConfiguration("", "2020-03-01"));
  }

  /**
   * Test {@link TimerEventHandler#getEndDateFromConfiguration(String)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerEventHandler#getEndDateFromConfiguration(String)}
   */
  @Test
  public void testGetEndDateFromConfiguration_whenEmptyString() {
    // Arrange, Act and Assert
    assertNull(TimerEventHandler.getEndDateFromConfiguration(""));
  }

  /**
   * Test {@link TimerEventHandler#getEndDateFromConfiguration(String)}.
   * <ul>
   *   <li>When {@code Job Handler Configuration}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TimerEventHandler#getEndDateFromConfiguration(String)}
   */
  @Test
  public void testGetEndDateFromConfiguration_whenJobHandlerConfiguration() {
    // Arrange, Act and Assert
    assertNull(TimerEventHandler.getEndDateFromConfiguration("Job Handler Configuration"));
  }
}
