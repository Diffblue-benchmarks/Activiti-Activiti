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

class ScriptTaskIconTypeDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ScriptTaskIconType}
   *   <li>{@link ScriptTaskIconType#getDValue()}
   *   <li>{@link ScriptTaskIconType#getStyleValue()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    ScriptTaskIconType actualScriptTaskIconType = new ScriptTaskIconType();
    String actualDValue = actualScriptTaskIconType.getDValue();

    // Assert
    assertEquals("fill:#72a7d0;stroke:none", actualScriptTaskIconType.getStyleValue());
    assertEquals("m 5,2 0,0.094 c 0.23706,0.064 0.53189,0.1645 0.8125,0.375 0.5582,0.4186 1.05109,1.228 1.15625,2.5312"
        + " l 8.03125,0 1,0 1,0 c 0,-3 -2,-3 -2,-3 l -10,0 z M 4,3 4,13 2,13 c 0,3 2,3 2,3 l 9,0 c 0,0 2,0 2,-3"
        + " L 15,6 6,6 6,5.5 C 6,4.1111 5.5595,3.529 5.1875,3.25 4.8155,2.971 4.5,3 4.5,3 L 4,3 z", actualDValue);
  }
}
