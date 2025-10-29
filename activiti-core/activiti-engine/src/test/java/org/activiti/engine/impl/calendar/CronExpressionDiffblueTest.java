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
import java.text.ParseException;
import java.util.TimeZone;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.engine.runtime.ClockReader;
import org.junit.Test;

public class CronExpressionDiffblueTest {
  /**
   * Method under test: {@link CronExpression#CronExpression(String, ClockReader)}
   */
  @Test
  public void testNewCronExpression() throws ParseException {
    // Arrange, Act and Assert
    assertThrows(ParseException.class, () -> new CronExpression("Cron Expression", new DefaultClockImpl()));

    assertThrows(ParseException.class, () -> new CronExpression("L-9", new DefaultClockImpl()));
    assertThrows(ParseException.class, () -> new CronExpression("L", new DefaultClockImpl()));
    assertThrows(ParseException.class, () -> new CronExpression("LW", new DefaultClockImpl()));
    assertThrows(ParseException.class, () -> new CronExpression("^L-[0-9]*[W]?", new DefaultClockImpl()));
    assertThrows(IllegalArgumentException.class, () -> new CronExpression(null, new DefaultClockImpl()));
    assertThrows(ParseException.class, () -> new CronExpression("42", new DefaultClockImpl()));
  }

  /**
   * Method under test:
   * {@link CronExpression#CronExpression(String, ClockReader, TimeZone)}
   */
  @Test
  public void testNewCronExpression2() throws ParseException {
    // Arrange
    DefaultClockImpl clockReader = new DefaultClockImpl();

    // Act and Assert
    assertThrows(ParseException.class,
        () -> new CronExpression("Cron Expression", clockReader, TimeZone.getTimeZone("America/Los_Angeles")));

  }

  /**
   * Method under test:
   * {@link CronExpression#CronExpression(String, ClockReader, TimeZone)}
   */
  @Test
  public void testNewCronExpression3() throws ParseException {
    // Arrange
    DefaultClockImpl clockReader = new DefaultClockImpl();

    // Act and Assert
    assertThrows(ParseException.class,
        () -> new CronExpression("L-9", clockReader, TimeZone.getTimeZone("America/Los_Angeles")));

  }

  /**
   * Method under test:
   * {@link CronExpression#CronExpression(String, ClockReader, TimeZone)}
   */
  @Test
  public void testNewCronExpression4() throws ParseException {
    // Arrange
    DefaultClockImpl clockReader = new DefaultClockImpl();

    // Act and Assert
    assertThrows(ParseException.class,
        () -> new CronExpression("L", clockReader, TimeZone.getTimeZone("America/Los_Angeles")));

  }

  /**
   * Method under test:
   * {@link CronExpression#CronExpression(String, ClockReader, TimeZone)}
   */
  @Test
  public void testNewCronExpression5() throws ParseException {
    // Arrange
    DefaultClockImpl clockReader = new DefaultClockImpl();

    // Act and Assert
    assertThrows(ParseException.class,
        () -> new CronExpression("LW", clockReader, TimeZone.getTimeZone("America/Los_Angeles")));

  }

  /**
   * Method under test:
   * {@link CronExpression#CronExpression(String, ClockReader, TimeZone)}
   */
  @Test
  public void testNewCronExpression6() throws ParseException {
    // Arrange
    DefaultClockImpl clockReader = new DefaultClockImpl();

    // Act and Assert
    assertThrows(ParseException.class,
        () -> new CronExpression("^L-[0-9]*[W]?", clockReader, TimeZone.getTimeZone("America/Los_Angeles")));

  }

  /**
   * Method under test:
   * {@link CronExpression#CronExpression(String, ClockReader, TimeZone)}
   */
  @Test
  public void testNewCronExpression7() throws ParseException {
    // Arrange
    DefaultClockImpl clockReader = new DefaultClockImpl();

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> new CronExpression(null, clockReader, TimeZone.getTimeZone("America/Los_Angeles")));

  }

  /**
   * Method under test:
   * {@link CronExpression#CronExpression(String, ClockReader, TimeZone)}
   */
  @Test
  public void testNewCronExpression8() throws ParseException {
    // Arrange
    DefaultClockImpl clockReader = new DefaultClockImpl();

    // Act and Assert
    assertThrows(ParseException.class,
        () -> new CronExpression("42", clockReader, TimeZone.getTimeZone("America/Los_Angeles")));

  }
}
