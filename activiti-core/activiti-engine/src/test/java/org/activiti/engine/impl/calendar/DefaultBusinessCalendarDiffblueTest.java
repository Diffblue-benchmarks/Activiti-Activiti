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

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultBusinessCalendarDiffblueTest {
  @InjectMocks
  private DefaultBusinessCalendar defaultBusinessCalendar;

  /**
   * Test
   * {@link DefaultBusinessCalendar#validateDuedate(String, int, Date, Date)}.
   * <p>
   * Method under test:
   * {@link DefaultBusinessCalendar#validateDuedate(String, int, Date, Date)}
   */
  @Test
  public void testValidateDuedate() {
    // Arrange
    Date endDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertTrue(defaultBusinessCalendar.validateDuedate("2020-03-01", 3, endDate,
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())));
  }

  /**
   * Test
   * {@link DefaultBusinessCalendar#validateDuedate(String, int, Date, Date)}.
   * <ul>
   *   <li>When {@link java.sql.Date}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultBusinessCalendar#validateDuedate(String, int, java.util.Date, java.util.Date)}
   */
  @Test
  public void testValidateDuedate_whenDate() {
    // Arrange
    java.sql.Date endDate = mock(java.sql.Date.class);

    // Act and Assert
    assertTrue(defaultBusinessCalendar.validateDuedate("2020-03-01", 3, endDate,
        java.util.Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())));
  }

  /**
   * Test {@link DefaultBusinessCalendar#resolveEndDate(String)}.
   * <p>
   * Method under test: {@link DefaultBusinessCalendar#resolveEndDate(String)}
   */
  @Test
  public void testResolveEndDate() {
    // Arrange, Act and Assert
    assertNull(defaultBusinessCalendar.resolveEndDate("2020-03-01"));
  }

  /**
   * Test {@link DefaultBusinessCalendar#addSingleUnitQuantity(Date, String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultBusinessCalendar#addSingleUnitQuantity(Date, String)}
   */
  @Test
  public void testAddSingleUnitQuantity_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> defaultBusinessCalendar.addSingleUnitQuantity(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()),
            "java.lang.Integer"));
  }
}
