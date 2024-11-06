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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReceiveTaskIconTypeDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ReceiveTaskIconType}
   *   <li>{@link ReceiveTaskIconType#getDValue()}
   *   <li>{@link ReceiveTaskIconType#getStyleValue()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    ReceiveTaskIconType actualReceiveTaskIconType = new ReceiveTaskIconType();
    String actualDValue = actualReceiveTaskIconType.getDValue();

    // Assert
    assertEquals("fill:#16964d;stroke:none;", actualReceiveTaskIconType.getStyleValue());
    assertEquals("m 0.5,2.5 0,13 17,0 0,-13 z M 2,4 6.5,8.5 2,13 z M 4,4 14,4 9,9 z m 12,0 0,9 -4.5,-4.5 z M 7.5,9.5"
        + " 9,11 10.5,9.5 15,14 3,14 z", actualDValue);
  }
}
