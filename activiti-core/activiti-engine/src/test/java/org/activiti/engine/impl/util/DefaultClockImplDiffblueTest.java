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
package org.activiti.engine.impl.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultClockImplDiffblueTest {
  @InjectMocks
  private DefaultClockImpl defaultClockImpl;

  /**
   * Method under test: {@link DefaultClockImpl#setCurrentTime(java.util.Date)}
   */
  @Test
  public void testSetCurrentTime() {
    // Arrange
    java.sql.Date currentTime = mock(java.sql.Date.class);
    when(currentTime.getTime()).thenReturn(10L);

    // Act
    defaultClockImpl.setCurrentTime(currentTime);

    // Assert
    verify(currentTime).getTime();
  }

  /**
   * Method under test: {@link DefaultClockImpl#getCurrentCalendar()}
   */
  @Test
  public void testGetCurrentCalendar() {
    // Arrange and Act
    Calendar actualCurrentCalendar = defaultClockImpl.getCurrentCalendar();

    // Assert
    assertTrue(actualCurrentCalendar instanceof GregorianCalendar);
    TimeZone timeZone = actualCurrentCalendar.getTimeZone();
    assertEquals("Coordinated Universal Time", timeZone.getDisplayName());
    assertEquals("gregory", actualCurrentCalendar.getCalendarType());
    assertEquals(-62133091200000L, actualCurrentCalendar.getTimeInMillis());
    assertEquals(0, timeZone.getDSTSavings());
    assertEquals(1, actualCurrentCalendar.getFirstDayOfWeek());
    assertEquals(1, actualCurrentCalendar.getMinimalDaysInFirstWeek());
    assertEquals(1, actualCurrentCalendar.getWeekYear());
    assertTrue(actualCurrentCalendar.isLenient());
    assertTrue(actualCurrentCalendar.isWeekDateSupported());
    String expectedID = System.getProperty("user.timezone");
    assertEquals(expectedID, timeZone.getID());
    assertEquals(Double.PRECISION, actualCurrentCalendar.getWeeksInWeekYear());
  }

  /**
   * Method under test: {@link DefaultClockImpl#getCurrentCalendar(TimeZone)}
   */
  @Test
  public void testGetCurrentCalendar2() {
    // Arrange
    TimeZone timeZone = TimeZone.getTimeZone("America/Los_Angeles");

    // Act
    Calendar actualCurrentCalendar = defaultClockImpl.getCurrentCalendar(timeZone);

    // Assert
    assertTrue(actualCurrentCalendar instanceof GregorianCalendar);
    assertEquals("gregory", actualCurrentCalendar.getCalendarType());
    assertEquals(-62133091200000L, actualCurrentCalendar.getTimeInMillis());
    assertEquals(1, actualCurrentCalendar.getFirstDayOfWeek());
    assertEquals(1, actualCurrentCalendar.getMinimalDaysInFirstWeek());
    assertEquals(1, actualCurrentCalendar.getWeekYear());
    assertTrue(actualCurrentCalendar.isLenient());
    assertTrue(actualCurrentCalendar.isWeekDateSupported());
    assertEquals(Double.PRECISION, actualCurrentCalendar.getWeeksInWeekYear());
    assertSame(timeZone, actualCurrentCalendar.getTimeZone());
  }

  /**
   * Method under test: {@link DefaultClockImpl#getCurrentTimeZone()}
   */
  @Test
  public void testGetCurrentTimeZone() {
    // Arrange and Act
    TimeZone actualCurrentTimeZone = defaultClockImpl.getCurrentTimeZone();

    // Assert
    assertEquals("Coordinated Universal Time", actualCurrentTimeZone.getDisplayName());
    assertEquals(0, actualCurrentTimeZone.getDSTSavings());
    String expectedID = System.getProperty("user.timezone");
    assertEquals(expectedID, actualCurrentTimeZone.getID());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link DefaultClockImpl}
   *   <li>{@link DefaultClockImpl#reset()}
   *   <li>{@link DefaultClockImpl#setCurrentCalendar(Calendar)}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    DefaultClockImpl actualDefaultClockImpl = new DefaultClockImpl();
    actualDefaultClockImpl.reset();
    GregorianCalendar currentTime = new GregorianCalendar(1, 1, 1);

    actualDefaultClockImpl.setCurrentCalendar(currentTime);

    // Assert
    Calendar currentCalendar = actualDefaultClockImpl.getCurrentCalendar();
    assertTrue(currentCalendar instanceof GregorianCalendar);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    assertEquals("0001-02-01", simpleDateFormat.format(actualDefaultClockImpl.getCurrentTime()));
    assertEquals(0, actualDefaultClockImpl.getCurrentTimeZone().getDSTSavings());
    assertEquals(currentTime, currentCalendar);
  }
}
