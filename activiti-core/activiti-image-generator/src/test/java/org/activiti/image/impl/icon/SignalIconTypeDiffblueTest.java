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

class SignalIconTypeDiffblueTest {
  /**
   * Test {@link SignalIconType#getWidth()}.
   * <p>
   * Method under test: {@link SignalIconType#getWidth()}
   */
  @Test
  @DisplayName("Test getWidth()")
  void testGetWidth() {
    // Arrange, Act and Assert
    assertEquals(17, (new SignalIconType()).getWidth().intValue());
  }

  /**
   * Test {@link SignalIconType#getHeight()}.
   * <p>
   * Method under test: {@link SignalIconType#getHeight()}
   */
  @Test
  @DisplayName("Test getHeight()")
  void testGetHeight() {
    // Arrange, Act and Assert
    assertEquals(15, (new SignalIconType()).getHeight().intValue());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link SignalIconType}
   *   <li>{@link SignalIconType#getAnchorValue()}
   *   <li>{@link SignalIconType#getDValue()}
   *   <li>{@link SignalIconType#getFillValue()}
   *   <li>{@link SignalIconType#getStrokeValue()}
   *   <li>{@link SignalIconType#getStrokeWidth()}
   *   <li>{@link SignalIconType#getStyleValue()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    SignalIconType actualSignalIconType = new SignalIconType();
    String actualAnchorValue = actualSignalIconType.getAnchorValue();
    String actualDValue = actualSignalIconType.getDValue();
    String actualFillValue = actualSignalIconType.getFillValue();
    String actualStrokeValue = actualSignalIconType.getStrokeValue();
    String actualStrokeWidth = actualSignalIconType.getStrokeWidth();

    // Assert
    assertEquals(" M7.7124971 20.247342  L22.333334 20.247342  L15.022915000000001 7.575951200000001  L7.7124971"
        + " 20.247342  z", actualDValue);
    assertEquals("#585858", actualStrokeValue);
    assertEquals("fill:none;stroke-width:1.4;stroke-miterlimit:4;stroke-dasharray:none",
        actualSignalIconType.getStyleValue());
    assertEquals("none", actualFillValue);
    assertNull(actualAnchorValue);
    assertNull(actualStrokeWidth);
  }
}
