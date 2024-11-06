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

class ErrorIconTypeDiffblueTest {
  /**
   * Test {@link ErrorIconType#getWidth()}.
   * <p>
   * Method under test: {@link ErrorIconType#getWidth()}
   */
  @Test
  @DisplayName("Test getWidth()")
  void testGetWidth() {
    // Arrange, Act and Assert
    assertEquals(17, (new ErrorIconType()).getWidth().intValue());
  }

  /**
   * Test {@link ErrorIconType#getHeight()}.
   * <p>
   * Method under test: {@link ErrorIconType#getHeight()}
   */
  @Test
  @DisplayName("Test getHeight()")
  void testGetHeight() {
    // Arrange, Act and Assert
    assertEquals(22, (new ErrorIconType()).getHeight().intValue());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ErrorIconType}
   *   <li>{@link ErrorIconType#getAnchorValue()}
   *   <li>{@link ErrorIconType#getDValue()}
   *   <li>{@link ErrorIconType#getFillValue()}
   *   <li>{@link ErrorIconType#getStrokeValue()}
   *   <li>{@link ErrorIconType#getStrokeWidth()}
   *   <li>{@link ErrorIconType#getStyleValue()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    ErrorIconType actualErrorIconType = new ErrorIconType();
    String actualAnchorValue = actualErrorIconType.getAnchorValue();
    String actualDValue = actualErrorIconType.getDValue();
    String actualFillValue = actualErrorIconType.getFillValue();
    String actualStrokeValue = actualErrorIconType.getStrokeValue();
    String actualStrokeWidth = actualErrorIconType.getStrokeWidth();

    // Assert
    assertEquals(" M21.820839 10.171502  L18.36734 23.58992  L12.541380000000002 13.281818999999999  L8.338651200000001"
        + " 19.071607  L12.048949000000002 5.832305699999999  L17.996148000000005 15.132659  L21.820839 10.171502"
        + "  z", actualDValue);
    assertEquals("#585858", actualStrokeValue);
    assertEquals("fill:none;stroke-width:1.5;stroke-linecap:butt;stroke-linejoin:miter;stroke-miterlimit:10",
        actualErrorIconType.getStyleValue());
    assertEquals("none", actualFillValue);
    assertNull(actualAnchorValue);
    assertNull(actualStrokeWidth);
  }
}
