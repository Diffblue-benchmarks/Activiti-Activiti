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
package org.activiti.engine.impl.cfg;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class PerformanceSettingsDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link PerformanceSettings}
   *   <li>{@link PerformanceSettings#setEnableEagerExecutionTreeFetching(boolean)}
   *   <li>{@link PerformanceSettings#setEnableExecutionRelationshipCounts(boolean)}
   *   <li>{@link PerformanceSettings#setEnableLocalization(boolean)}
   *   <li>
   * {@link PerformanceSettings#setValidateExecutionRelationshipCountConfigOnBoot(boolean)}
   *   <li>{@link PerformanceSettings#isEnableEagerExecutionTreeFetching()}
   *   <li>{@link PerformanceSettings#isEnableExecutionRelationshipCounts()}
   *   <li>{@link PerformanceSettings#isEnableLocalization()}
   *   <li>
   * {@link PerformanceSettings#isValidateExecutionRelationshipCountConfigOnBoot()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    PerformanceSettings actualPerformanceSettings = new PerformanceSettings();
    actualPerformanceSettings.setEnableEagerExecutionTreeFetching(true);
    actualPerformanceSettings.setEnableExecutionRelationshipCounts(true);
    actualPerformanceSettings.setEnableLocalization(true);
    actualPerformanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(true);
    boolean actualIsEnableEagerExecutionTreeFetchingResult = actualPerformanceSettings
        .isEnableEagerExecutionTreeFetching();
    boolean actualIsEnableExecutionRelationshipCountsResult = actualPerformanceSettings
        .isEnableExecutionRelationshipCounts();
    boolean actualIsEnableLocalizationResult = actualPerformanceSettings.isEnableLocalization();

    // Assert that nothing has changed
    assertTrue(actualIsEnableEagerExecutionTreeFetchingResult);
    assertTrue(actualIsEnableExecutionRelationshipCountsResult);
    assertTrue(actualIsEnableLocalizationResult);
    assertTrue(actualPerformanceSettings.isValidateExecutionRelationshipCountConfigOnBoot());
  }
}
