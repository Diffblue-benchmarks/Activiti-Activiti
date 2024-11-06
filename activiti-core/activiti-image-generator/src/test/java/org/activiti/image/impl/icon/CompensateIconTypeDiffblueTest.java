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

class CompensateIconTypeDiffblueTest {
  /**
   * Test {@link CompensateIconType#getWidth()}.
   * <p>
   * Method under test: {@link CompensateIconType#getWidth()}
   */
  @Test
  @DisplayName("Test getWidth()")
  void testGetWidth() {
    // Arrange, Act and Assert
    assertEquals(15, (new CompensateIconType()).getWidth().intValue());
  }

  /**
   * Test {@link CompensateIconType#getHeight()}.
   * <p>
   * Method under test: {@link CompensateIconType#getHeight()}
   */
  @Test
  @DisplayName("Test getHeight()")
  void testGetHeight() {
    // Arrange, Act and Assert
    assertEquals(Short.SIZE, (new CompensateIconType()).getHeight().intValue());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link CompensateIconType}
   *   <li>{@link CompensateIconType#getAnchorValue()}
   *   <li>{@link CompensateIconType#getDValue()}
   *   <li>{@link CompensateIconType#getFillValue()}
   *   <li>{@link CompensateIconType#getStrokeValue()}
   *   <li>{@link CompensateIconType#getStrokeWidth()}
   *   <li>{@link CompensateIconType#getStyleValue()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    CompensateIconType actualCompensateIconType = new CompensateIconType();
    String actualAnchorValue = actualCompensateIconType.getAnchorValue();
    String actualDValue = actualCompensateIconType.getDValue();
    String actualFillValue = actualCompensateIconType.getFillValue();
    String actualStrokeValue = actualCompensateIconType.getStrokeValue();
    String actualStrokeWidth = actualCompensateIconType.getStrokeWidth();

    // Assert
    assertEquals("#585858", actualStrokeValue);
    assertEquals("1.4", actualStrokeWidth);
    assertEquals("none", actualFillValue);
    assertNull(actualAnchorValue);
    assertNull(actualDValue);
    assertNull(actualCompensateIconType.getStyleValue());
  }
}
