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
package org.activiti.api.runtime.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DateToStringConverter.class})
@ExtendWith(SpringExtension.class)
class DateToStringConverterDiffblueTest {
  @Autowired
  private DateToStringConverter dateToStringConverter;

  /**
   * Test {@link DateToStringConverter#convert(Date)} with {@code Date}.
   * <p>
   * Method under test: {@link DateToStringConverter#convert(Date)}
   */
  @Test
  @DisplayName("Test convert(Date) with 'Date'")
  void testConvertWithDate() {
    // Arrange, Act and Assert
    assertEquals("1970-01-01T00:00:00Z", dateToStringConverter
        .convert(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())));
  }

  /**
   * Test {@link DateToStringConverter#convert(Date)} with {@code Date}.
   * <ul>
   *   <li>Then calls {@link java.sql.Date#toInstant()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DateToStringConverter#convert(java.util.Date)}
   */
  @Test
  @DisplayName("Test convert(Date) with 'Date'; then calls toInstant()")
  void testConvertWithDate_thenCallsToInstant() {
    // Arrange
    java.sql.Date source = mock(java.sql.Date.class);
    when(source.toInstant()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    String actualConvertResult = dateToStringConverter.convert(source);

    // Assert
    verify(source).toInstant();
    assertEquals("1970-01-01T00:00:00Z", actualConvertResult);
  }
}
