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
package org.activiti.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import java.time.DateTimeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DateFormatterProvider.class, String.class})
@ExtendWith(SpringExtension.class)
class DateFormatterProviderDiffblueTest {
  @Autowired
  private DateFormatterProvider dateFormatterProvider;

  /**
   * Test {@link DateFormatterProvider#DateFormatterProvider(String)}.
   * <p>
   * Method under test:
   * {@link DateFormatterProvider#DateFormatterProvider(String)}
   */
  @Test
  @DisplayName("Test new DateFormatterProvider(String)")
  void testNewDateFormatterProvider() {
    // Arrange and Act
    DateFormatterProvider actualDateFormatterProvider = new DateFormatterProvider("2020-03-01");

    // Assert
    assertEquals("2020-03-01", actualDateFormatterProvider.getDateFormatPattern());
    assertEquals("Z", actualDateFormatterProvider.getZoneId().toString());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link DateFormatterProvider#setDateFormatPattern(String)}
   *   <li>{@link DateFormatterProvider#getDateFormatPattern()}
   *   <li>{@link DateFormatterProvider#getZoneId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    DateFormatterProvider dateFormatterProvider = new DateFormatterProvider("2020-03-01");

    // Act
    dateFormatterProvider.setDateFormatPattern("2020-03-01");
    String actualDateFormatPattern = dateFormatterProvider.getDateFormatPattern();

    // Assert that nothing has changed
    assertEquals("2020-03-01", actualDateFormatPattern);
    assertEquals("Z", dateFormatterProvider.getZoneId().toString());
  }

  /**
   * Test {@link DateFormatterProvider#toDate(Object)}.
   * <ul>
   *   <li>When {@link java.sql.Date}.</li>
   *   <li>Then return {@link java.sql.Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DateFormatterProvider#toDate(Object)}
   */
  @Test
  @DisplayName("Test toDate(Object); when Date; then return Date")
  void testToDate_whenDate_thenReturnDate() {
    // Arrange
    java.sql.Date date = mock(java.sql.Date.class);

    // Act and Assert
    assertSame(date, dateFormatterProvider.toDate(date));
  }

  /**
   * Test {@link DateFormatterProvider#toDate(Object)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then throw {@link DateTimeException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DateFormatterProvider#toDate(Object)}
   */
  @Test
  @DisplayName("Test toDate(Object); when forty-two; then throw DateTimeException")
  void testToDate_whenFortyTwo_thenThrowDateTimeException() {
    // Arrange, Act and Assert
    assertThrows(DateTimeException.class, () -> dateFormatterProvider.toDate(42));
  }
}
