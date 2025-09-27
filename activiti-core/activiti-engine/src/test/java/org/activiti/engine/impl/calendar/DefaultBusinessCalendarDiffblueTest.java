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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DefaultBusinessCalendarDiffblueTest {
  /**
   * Test {@link DefaultBusinessCalendar#validateDuedate(String, int, Date, Date)}.
   *
   * <p>Method under test: {@link DefaultBusinessCalendar#validateDuedate(String, int, Date, Date)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.lang.Boolean DefaultBusinessCalendar.validateDuedate(String, int, Date, Date)"
  })
  public void testValidateDuedate() {
    // Arrange
    DefaultBusinessCalendar defaultBusinessCalendar = new DefaultBusinessCalendar();
    Date endDate =
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertTrue(
        defaultBusinessCalendar.validateDuedate(
            "2020-03-01",
            3,
            endDate,
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())));
  }

  /**
   * Test {@link DefaultBusinessCalendar#resolveEndDate(String)}.
   *
   * <p>Method under test: {@link DefaultBusinessCalendar#resolveEndDate(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Date DefaultBusinessCalendar.resolveEndDate(String)"})
  public void testResolveEndDate() {
    // Arrange, Act and Assert
    assertNull(new DefaultBusinessCalendar().resolveEndDate("2020-03-01"));
  }

  /**
   * Test {@link DefaultBusinessCalendar#addSingleUnitQuantity(Date, String)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultBusinessCalendar#addSingleUnitQuantity(Date, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Date DefaultBusinessCalendar.addSingleUnitQuantity(Date, String)"})
  public void testAddSingleUnitQuantity_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    DefaultBusinessCalendar defaultBusinessCalendar = new DefaultBusinessCalendar();

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () ->
            defaultBusinessCalendar.addSingleUnitQuantity(
                Date.from(
                    LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()),
                "java.lang.Integer"));
  }
}
