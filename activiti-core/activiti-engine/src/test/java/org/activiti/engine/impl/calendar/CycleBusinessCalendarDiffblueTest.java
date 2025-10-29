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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.runtime.ClockReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CycleBusinessCalendarDiffblueTest {
  @Mock
  private ClockReader clockReader;

  @InjectMocks
  private CycleBusinessCalendar cycleBusinessCalendar;

  /**
   * Method under test: {@link CycleBusinessCalendar#resolveDuedate(String, int)}
   */
  @Test
  public void testResolveDuedate() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> cycleBusinessCalendar.resolveDuedate("2020-03-01", 3));
    assertThrows(ActivitiException.class, () -> cycleBusinessCalendar.resolveDuedate("2020/03/01", 3));
    assertThrows(ActivitiException.class, () -> cycleBusinessCalendar.resolveDuedate("20200301", 3));
    assertThrows(ActivitiException.class, () -> cycleBusinessCalendar.resolveDuedate(" \t", 3));
    assertThrows(ActivitiException.class, () -> cycleBusinessCalendar.resolveDuedate(",", 3));
    assertThrows(ActivitiException.class,
        () -> cycleBusinessCalendar.resolveDuedate("Minute and Second values must be between 0 and 59", 3));
    assertThrows(ActivitiException.class, () -> cycleBusinessCalendar.resolveDuedate(null, 3));
    assertThrows(ActivitiException.class, () -> cycleBusinessCalendar.resolveDuedate("42", 3));
    assertThrows(ActivitiException.class, () -> cycleBusinessCalendar.resolveDuedate("/", 3));
  }

  /**
   * Method under test:
   * {@link CycleBusinessCalendar#validateDuedate(String, int, Date, Date)}
   */
  @Test
  public void testValidateDuedate() {
    // Arrange
    Date endDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertTrue(cycleBusinessCalendar.validateDuedate("2020-03-01", 3, endDate,
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())));
  }

  /**
   * Method under test:
   * {@link CycleBusinessCalendar#validateDuedate(String, int, Date, Date)}
   */
  @Test
  public void testValidateDuedate2() {
    // Arrange
    Date endDate = Date.from(LocalDate.now().atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertTrue(cycleBusinessCalendar.validateDuedate("2020-03-01", 3, endDate,
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())));
  }

  /**
   * Method under test:
   * {@link CycleBusinessCalendar#validateDuedate(String, int, Date, Date)}
   */
  @Test
  public void testValidateDuedate3() {
    // Arrange
    Date endDate = Date.from(LocalDate.ofYearDay(1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertFalse(cycleBusinessCalendar.validateDuedate("2020-03-01", 3, endDate,
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())));
  }

  /**
   * Method under test:
   * {@link CycleBusinessCalendar#validateDuedate(String, int, java.util.Date, java.util.Date)}
   */
  @Test
  public void testValidateDuedate4() {
    // Arrange
    java.sql.Date endDate = mock(java.sql.Date.class);
    when(endDate.after(Mockito.<java.util.Date>any())).thenReturn(true);

    // Act
    Boolean actualValidateDuedateResult = cycleBusinessCalendar.validateDuedate("2020-03-01", 3, endDate,
        java.util.Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Assert
    verify(endDate).after(isA(java.util.Date.class));
    assertTrue(actualValidateDuedateResult);
  }
}
