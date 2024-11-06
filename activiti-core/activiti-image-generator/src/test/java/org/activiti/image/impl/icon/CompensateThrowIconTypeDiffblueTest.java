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

class CompensateThrowIconTypeDiffblueTest {
  /**
   * Test {@link CompensateThrowIconType#getWidth()}.
   * <p>
   * Method under test: {@link CompensateThrowIconType#getWidth()}
   */
  @Test
  @DisplayName("Test getWidth()")
  void testGetWidth() {
    // Arrange, Act and Assert
    assertEquals(15, (new CompensateThrowIconType()).getWidth().intValue());
  }

  /**
   * Test {@link CompensateThrowIconType#getHeight()}.
   * <p>
   * Method under test: {@link CompensateThrowIconType#getHeight()}
   */
  @Test
  @DisplayName("Test getHeight()")
  void testGetHeight() {
    // Arrange, Act and Assert
    assertEquals(Short.SIZE, (new CompensateThrowIconType()).getHeight().intValue());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link CompensateThrowIconType}
   *   <li>{@link CompensateThrowIconType#getFillValue()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("#585858", (new CompensateThrowIconType()).getFillValue());
  }
}
