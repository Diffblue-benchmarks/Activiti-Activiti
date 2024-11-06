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
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {LocalDateTimeToStringConverter.class})
@ExtendWith(SpringExtension.class)
class LocalDateTimeToStringConverterDiffblueTest {
  @Autowired
  private LocalDateTimeToStringConverter localDateTimeToStringConverter;

  /**
   * Test {@link LocalDateTimeToStringConverter#convert(LocalDateTime)} with
   * {@code LocalDateTime}.
   * <p>
   * Method under test:
   * {@link LocalDateTimeToStringConverter#convert(LocalDateTime)}
   */
  @Test
  @DisplayName("Test convert(LocalDateTime) with 'LocalDateTime'")
  void testConvertWithLocalDateTime() {
    // Arrange, Act and Assert
    assertEquals("1970-01-01T00:00:00",
        localDateTimeToStringConverter.convert(LocalDate.of(1970, 1, 1).atStartOfDay()));
  }
}
