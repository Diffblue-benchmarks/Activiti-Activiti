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
package org.activiti.bpmn.converter.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommaSplitterDiffblueTest {
  /**
   * Test {@link CommaSplitter#splitCommas(String)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommaSplitter#splitCommas(String)}
   */
  @Test
  @DisplayName("Test splitCommas(String); when empty string; then return Empty")
  void testSplitCommas_whenEmptyString_thenReturnEmpty() {
    // Arrange and Act
    List<String> actualSplitCommasResult = CommaSplitter.splitCommas("");

    // Assert
    assertTrue(actualSplitCommasResult.isEmpty());
  }

  /**
   * Test {@link CommaSplitter#splitCommas(String)}.
   * <ul>
   *   <li>When {@code St}.</li>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link CommaSplitter#splitCommas(String)}
   */
  @Test
  @DisplayName("Test splitCommas(String); when 'St'; then return size is one")
  void testSplitCommas_whenSt_thenReturnSizeIsOne() {
    // Arrange and Act
    List<String> actualSplitCommasResult = CommaSplitter.splitCommas("St");

    // Assert
    assertEquals(1, actualSplitCommasResult.size());
    assertEquals("St", actualSplitCommasResult.get(0));
  }
}
