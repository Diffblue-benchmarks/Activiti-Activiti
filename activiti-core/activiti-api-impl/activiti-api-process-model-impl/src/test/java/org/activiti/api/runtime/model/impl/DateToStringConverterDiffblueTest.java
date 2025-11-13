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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DateToStringConverter.class})
@ExtendWith(SpringExtension.class)
class DateToStringConverterDiffblueTest {
  @Autowired private DateToStringConverter dateToStringConverter;

  /**
   * Test {@link DateToStringConverter#convert(Date)} with {@code Date}.
   *
   * <ul>
   *   <li>Then return {@code 1970-01-01T00:00:00Z}.
   * </ul>
   *
   * <p>Method under test: {@link DateToStringConverter#convert(Date)}
   */
  @Test
  @DisplayName("Test convert(Date) with 'Date'; then return '1970-01-01T00:00:00Z'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String DateToStringConverter.convert(Date)"})
  void testConvertWithDate_thenReturn19700101t000000z() {
    // Arrange, Act and Assert
    assertEquals(
        "1970-01-01T00:00:00Z",
        dateToStringConverter.convert(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())));
  }
}
