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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {LocalDateToStringConverter.class})
@ExtendWith(SpringExtension.class)
class LocalDateToStringConverterDiffblueTest {
  @Autowired
  private LocalDateToStringConverter localDateToStringConverter;

  /**
   * Test {@link LocalDateToStringConverter#convert(LocalDate)} with
   * {@code LocalDate}.
   * <ul>
   *   <li>When {@link LocalDate} with {@code 1970} and one and one.</li>
   *   <li>Then return {@code 1970-01-01}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LocalDateToStringConverter#convert(LocalDate)}
   */
  @Test
  @DisplayName("Test convert(LocalDate) with 'LocalDate'; when LocalDate with '1970' and one and one; then return '1970-01-01'")
  void testConvertWithLocalDate_whenLocalDateWith1970AndOneAndOne_thenReturn19700101() {
    // Arrange, Act and Assert
    assertEquals("1970-01-01", localDateToStringConverter.convert(LocalDate.of(1970, 1, 1)));
  }
}
