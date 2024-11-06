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

class MessageIconTypeDiffblueTest {
  /**
   * Test {@link MessageIconType#getWidth()}.
   * <p>
   * Method under test: {@link MessageIconType#getWidth()}
   */
  @Test
  @DisplayName("Test getWidth()")
  void testGetWidth() {
    // Arrange, Act and Assert
    assertEquals(17, (new MessageIconType()).getWidth().intValue());
  }

  /**
   * Test {@link MessageIconType#getHeight()}.
   * <p>
   * Method under test: {@link MessageIconType#getHeight()}
   */
  @Test
  @DisplayName("Test getHeight()")
  void testGetHeight() {
    // Arrange, Act and Assert
    assertEquals(13, (new MessageIconType()).getHeight().intValue());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link MessageIconType}
   *   <li>{@link MessageIconType#getAnchorValue()}
   *   <li>{@link MessageIconType#getDValue()}
   *   <li>{@link MessageIconType#getFillValue()}
   *   <li>{@link MessageIconType#getStrokeValue()}
   *   <li>{@link MessageIconType#getStrokeWidth()}
   *   <li>{@link MessageIconType#getStyleValue()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    MessageIconType actualMessageIconType = new MessageIconType();
    String actualAnchorValue = actualMessageIconType.getAnchorValue();
    String actualDValue = actualMessageIconType.getDValue();
    String actualFillValue = actualMessageIconType.getFillValue();
    String actualStrokeValue = actualMessageIconType.getStrokeValue();
    String actualStrokeWidth = actualMessageIconType.getStrokeWidth();

    // Assert
    assertEquals(" m0 1.5  l0 13  l17 0  l0 -13  z M1.5 3  L6 7.5  L1.5 12  z M3.5 3  L13.5 3  L8.5 8  z m12 0  l0 9 "
        + " l-4.5 -4.5  z M7 8.5  L8.5 10  L10 8.5  L14.5 13  L2.5 13  z", actualDValue);
    assertEquals("#585858", actualFillValue);
    assertEquals("1", actualStrokeWidth);
    assertEquals("none", actualStrokeValue);
    assertNull(actualAnchorValue);
    assertNull(actualMessageIconType.getStyleValue());
  }
}
