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
package org.activiti.image.impl.icon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TaskIconTypeDiffblueTest {
  /**
   * Test {@link TaskIconType#getAnchorValue()}.
   * <p>
   * Method under test: {@link TaskIconType#getAnchorValue()}
   */
  @Test
  @DisplayName("Test getAnchorValue()")
  void testGetAnchorValue() {
    // Arrange, Act and Assert
    assertEquals("top left", (new BusinessRuleTaskIconType()).getAnchorValue());
  }

  /**
   * Test {@link TaskIconType#getStrokeValue()}.
   * <p>
   * Method under test: {@link TaskIconType#getStrokeValue()}
   */
  @Test
  @DisplayName("Test getStrokeValue()")
  void testGetStrokeValue() {
    // Arrange, Act and Assert
    assertNull((new BusinessRuleTaskIconType()).getStrokeValue());
  }

  /**
   * Test {@link TaskIconType#getFillValue()}.
   * <p>
   * Method under test: {@link TaskIconType#getFillValue()}
   */
  @Test
  @DisplayName("Test getFillValue()")
  void testGetFillValue() {
    // Arrange, Act and Assert
    assertNull((new BusinessRuleTaskIconType()).getFillValue());
  }

  /**
   * Test {@link TaskIconType#getWidth()}.
   * <p>
   * Method under test: {@link TaskIconType#getWidth()}
   */
  @Test
  @DisplayName("Test getWidth()")
  void testGetWidth() {
    // Arrange, Act and Assert
    assertNull((new BusinessRuleTaskIconType()).getWidth());
  }

  /**
   * Test {@link TaskIconType#getHeight()}.
   * <p>
   * Method under test: {@link TaskIconType#getHeight()}
   */
  @Test
  @DisplayName("Test getHeight()")
  void testGetHeight() {
    // Arrange, Act and Assert
    assertNull((new BusinessRuleTaskIconType()).getHeight());
  }

  /**
   * Test {@link TaskIconType#getStrokeWidth()}.
   * <p>
   * Method under test: {@link TaskIconType#getStrokeWidth()}
   */
  @Test
  @DisplayName("Test getStrokeWidth()")
  void testGetStrokeWidth() {
    // Arrange, Act and Assert
    assertNull((new BusinessRuleTaskIconType()).getStrokeWidth());
  }
}
