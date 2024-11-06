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

class ErrorThrowIconTypeDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ErrorThrowIconType}
   *   <li>{@link ErrorThrowIconType#getAnchorValue()}
   *   <li>{@link ErrorThrowIconType#getDValue()}
   *   <li>{@link ErrorThrowIconType#getFillValue()}
   *   <li>{@link ErrorThrowIconType#getStrokeWidth()}
   *   <li>{@link ErrorThrowIconType#getStyleValue()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    ErrorThrowIconType actualErrorThrowIconType = new ErrorThrowIconType();
    String actualAnchorValue = actualErrorThrowIconType.getAnchorValue();
    String actualDValue = actualErrorThrowIconType.getDValue();
    String actualFillValue = actualErrorThrowIconType.getFillValue();
    String actualStrokeWidth = actualErrorThrowIconType.getStrokeWidth();

    // Assert
    assertEquals(
        " M20.820839 9.171502  L17.36734 22.58992  L11.54138 12.281818999999999  L7.3386512 18.071607  L11.048949"
            + " 4.832305699999999  L16.996148 14.132659  L20.820839 9.171502  z",
        actualDValue);
    assertEquals("#585858", actualFillValue);
    assertEquals("stroke-width:1.5;stroke-linecap:butt;stroke-linejoin:miter;stroke-miterlimit:10",
        actualErrorThrowIconType.getStyleValue());
    assertNull(actualAnchorValue);
    assertNull(actualStrokeWidth);
  }
}
