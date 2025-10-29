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
   * Method under test:
   * {@link TimerEventHandler#createConfiguration(String, String, String)}
   */
  @Test
  public void testCreateConfiguration() {
    // Arrange, Act and Assert
    assertEquals("{\"activityId\":\"42\",\"calendarName\":\"Calendar Name\",\"timerEndDate\":\"2020-03-01\"}",
        TimerEventHandler.createConfiguration("42", "2020-03-01", "Calendar Name"));
    assertEquals("{\"calendarName\":\"Calendar Name\",\"timerEndDate\":\"2020-03-01\"}",
        TimerEventHandler.createConfiguration(null, "2020-03-01", "Calendar Name"));
    assertEquals("{\"activityId\":\"\",\"calendarName\":\"Calendar Name\",\"timerEndDate\":\"2020-03-01\"}",
        TimerEventHandler.createConfiguration("", "2020-03-01", "Calendar Name"));
    assertEquals("{\"activityId\":\"42\",\"calendarName\":\"Calendar Name\",\"timerEndDate\":\"2020/03/01\"}",
        TimerEventHandler.createConfiguration("42", "2020/03/01", "Calendar Name"));
    assertEquals("{\"activityId\":\"42\",\"calendarName\":\"Calendar Name\"}",
        TimerEventHandler.createConfiguration("42", null, "Calendar Name"));
    assertEquals("{\"activityId\":\"42\",\"timerEndDate\":\"2020-03-01\"}",
        TimerEventHandler.createConfiguration("42", "2020-03-01", null));
    assertEquals("{\"activityId\":\"\",\"calendarName\":\"Calendar Name\",\"timerEndDate\":\"\\\"\\\"\"}",
        TimerEventHandler.createConfiguration("", "\"\"", "Calendar Name"));
  }

  /**
   * Method under test:
   * {@link TimerEventHandler#setActivityIdToConfiguration(String, String)}
   */
  @Test
  public void testSetActivityIdToConfiguration() {
    // Arrange, Act and Assert
    assertEquals("Job Handler Configuration",
        TimerEventHandler.setActivityIdToConfiguration("Job Handler Configuration", "42"));
    assertEquals("", TimerEventHandler.setActivityIdToConfiguration("", "42"));
  }

  /**
   * Method under test:
   * {@link TimerEventHandler#getActivityIdFromConfiguration(String)}
   */
  @Test
  public void testGetActivityIdFromConfiguration() {
    // Arrange, Act and Assert
    assertEquals("Job Handler Configuration",
        TimerEventHandler.getActivityIdFromConfiguration("Job Handler Configuration"));
    assertEquals("", TimerEventHandler.getActivityIdFromConfiguration(""));
  }

  /**
   * Method under test:
   * {@link TimerEventHandler#geCalendarNameFromConfiguration(String)}
   */
  @Test
  public void testGeCalendarNameFromConfiguration() {
    // Arrange, Act and Assert
    assertEquals("", TimerEventHandler.geCalendarNameFromConfiguration("Job Handler Configuration"));
    assertEquals("", TimerEventHandler.geCalendarNameFromConfiguration(""));
  }

  /**
   * Method under test:
   * {@link TimerEventHandler#setEndDateToConfiguration(String, String)}
   */
  @Test
  public void testSetEndDateToConfiguration() {
    // Arrange, Act and Assert
    assertEquals("{\"activityId\":\"Job Handler Configuration\",\"timerEndDate\":\"2020-03-01\"}",
        TimerEventHandler.setEndDateToConfiguration("Job Handler Configuration", "2020-03-01"));
    assertEquals("{\"activityId\":\"{\",\"timerEndDate\":\"2020-03-01\"}",
        TimerEventHandler.setEndDateToConfiguration("{", "2020-03-01"));
    assertEquals("{\"activityId\":\"\",\"timerEndDate\":\"2020-03-01\"}",
        TimerEventHandler.setEndDateToConfiguration("", "2020-03-01"));
    assertEquals("{\"activityId\":\"Job Handler Configuration\",\"timerEndDate\":\"2020/03/01\"}",
        TimerEventHandler.setEndDateToConfiguration("Job Handler Configuration", "2020/03/01"));
    assertEquals("{\"activityId\":\"Job Handler Configuration\"}",
        TimerEventHandler.setEndDateToConfiguration("Job Handler Configuration", null));
    assertEquals("{\"activityId\":\"\",\"timerEndDate\":\"\\\"\\\"\"}",
        TimerEventHandler.setEndDateToConfiguration("", "\"\""));
  }

  /**
   * Method under test:
   * {@link TimerEventHandler#getEndDateFromConfiguration(String)}
   */
  @Test
  public void testGetEndDateFromConfiguration() {
    // Arrange, Act and Assert
    assertNull(TimerEventHandler.getEndDateFromConfiguration("Job Handler Configuration"));
    assertNull(TimerEventHandler.getEndDateFromConfiguration(""));
  }
}
