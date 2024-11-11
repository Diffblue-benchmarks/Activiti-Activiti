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

class TimerIconTypeDiffblueTest {
  /**
   * Test {@link TimerIconType#getWidth()}.
   * <p>
   * Method under test: {@link TimerIconType#getWidth()}
   */
  @Test
  @DisplayName("Test getWidth()")
  void testGetWidth() {
    // Arrange, Act and Assert
    assertEquals(20, (new TimerIconType()).getWidth().intValue());
  }

  /**
   * Test {@link TimerIconType#getHeight()}.
   * <p>
   * Method under test: {@link TimerIconType#getHeight()}
   */
  @Test
  @DisplayName("Test getHeight()")
  void testGetHeight() {
    // Arrange, Act and Assert
    assertEquals(20, (new TimerIconType()).getHeight().intValue());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link TimerIconType}
   *   <li>{@link TimerIconType#getAnchorValue()}
   *   <li>{@link TimerIconType#getDValue()}
   *   <li>{@link TimerIconType#getFillValue()}
   *   <li>{@link TimerIconType#getStrokeValue()}
   *   <li>{@link TimerIconType#getStrokeWidth()}
   *   <li>{@link TimerIconType#getStyleValue()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    TimerIconType actualTimerIconType = new TimerIconType();
    String actualAnchorValue = actualTimerIconType.getAnchorValue();
    String actualDValue = actualTimerIconType.getDValue();
    String actualFillValue = actualTimerIconType.getFillValue();
    String actualStrokeValue = actualTimerIconType.getStrokeValue();
    String actualStrokeWidth = actualTimerIconType.getStrokeWidth();

    // Assert
    assertEquals("#585858", actualFillValue);
    assertEquals("M 10 0 C 4.4771525 0 0 4.4771525 0 10 C 0 15.522847 4.4771525 20 10 20 C 15.522847 20 20 15.522847 20"
        + " 10 C 20 4.4771525 15.522847 1.1842379e-15 10 0 z M 9.09375 1.03125 C 9.2292164 1.0174926 9.362825"
        + " 1.0389311 9.5 1.03125 L 9.5 3.5 L 10.5 3.5 L 10.5 1.03125 C 15.063526 1.2867831 18.713217 4.9364738"
        + " 18.96875 9.5 L 16.5 9.5 L 16.5 10.5 L 18.96875 10.5 C 18.713217 15.063526 15.063526 18.713217 10.5"
        + " 18.96875 L 10.5 16.5 L 9.5 16.5 L 9.5 18.96875 C 4.9364738 18.713217 1.2867831 15.063526 1.03125 10.5"
        + " L 3.5 10.5 L 3.5 9.5 L 1.03125 9.5 C 1.279102 5.0736488 4.7225326 1.4751713 9.09375 1.03125 z M 9.5"
        + " 5 L 9.5 8.0625 C 8.6373007 8.2844627 8 9.0680195 8 10 C 8 11.104569 8.8954305 12 10 12 C 10.931981"
        + " 12 11.715537 11.362699 11.9375 10.5 L 14 10.5 L 14 9.5 L 11.9375 9.5 C 11.756642 8.7970599 11.20294"
        + " 8.2433585 10.5 8.0625 L 10.5 5 L 9.5 5 z ", actualDValue);
    assertEquals("none", actualStrokeValue);
    assertNull(actualAnchorValue);
    assertNull(actualStrokeWidth);
    assertNull(actualTimerIconType.getStyleValue());
  }
}