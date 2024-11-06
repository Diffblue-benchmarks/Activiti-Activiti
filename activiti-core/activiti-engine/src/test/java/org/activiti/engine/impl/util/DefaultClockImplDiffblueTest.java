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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
   * Test {@link DefaultClockImpl#setCurrentTime(Date)}.
   * <ul>
   *   <li>Given ten.</li>
   *   <li>When {@link java.sql.Date} {@link java.util.Date#getTime()} return
   * ten.</li>
   *   <li>Then calls {@link java.util.Date#getTime()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultClockImpl#setCurrentTime(java.util.Date)}
   */
  @Test
  public void testSetCurrentTime_givenTen_whenDateGetTimeReturnTen_thenCallsGetTime() {
    // Arrange
    java.sql.Date currentTime = mock(java.sql.Date.class);
    when(currentTime.getTime()).thenReturn(10L);

    // Act
    defaultClockImpl.setCurrentTime(currentTime);

    // Assert
    verify(currentTime).getTime();
  }

  /**
   * Test {@link DefaultClockImpl#getCurrentTimeZone()}.
   * <p>
   * Method under test: {@link DefaultClockImpl#getCurrentTimeZone()}
   */
  @Test
  public void testGetCurrentTimeZone() {
    // Arrange and Act
    TimeZone actualCurrentTimeZone = defaultClockImpl.getCurrentTimeZone();

    // Assert
    assertEquals("Greenwich Mean Time", actualCurrentTimeZone.getDisplayName());
    assertEquals(3600000, actualCurrentTimeZone.getDSTSavings());
    String expectedID = System.getProperty("user.timezone");
    assertEquals(expectedID, actualCurrentTimeZone.getID());
  }
}
