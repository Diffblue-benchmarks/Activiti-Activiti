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
package org.activiti.common.util.conf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.activiti.common.util.DateFormatterProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ActivitiCoreCommonUtilAutoConfiguration.class})
@ExtendWith(SpringExtension.class)
class ActivitiCoreCommonUtilAutoConfigurationDiffblueTest {
  @Autowired
  private ActivitiCoreCommonUtilAutoConfiguration activitiCoreCommonUtilAutoConfiguration;

  /**
   * Test
   * {@link ActivitiCoreCommonUtilAutoConfiguration#dateFormatterProvider(String)}.
   * <p>
   * Method under test:
   * {@link ActivitiCoreCommonUtilAutoConfiguration#dateFormatterProvider(String)}
   */
  @Test
  @DisplayName("Test dateFormatterProvider(String)")
  void testDateFormatterProvider() {
    // Arrange and Act
    DateFormatterProvider actualDateFormatterProviderResult = activitiCoreCommonUtilAutoConfiguration
        .dateFormatterProvider("2020-03-01");

    // Assert
    assertEquals("2020-03-01", actualDateFormatterProviderResult.getDateFormatPattern());
    assertEquals("Z", actualDateFormatterProviderResult.getZoneId().toString());
  }
}
