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
package org.activiti.api.runtime.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ActivitiErrorMessageImplDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ActivitiErrorMessageImpl#ActivitiErrorMessageImpl()}
   *   <li>{@link ActivitiErrorMessageImpl#setCode(int)}
   *   <li>{@link ActivitiErrorMessageImpl#setMessage(String)}
   *   <li>{@link ActivitiErrorMessageImpl#getCode()}
   *   <li>{@link ActivitiErrorMessageImpl#getMessage()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    ActivitiErrorMessageImpl actualActivitiErrorMessageImpl = new ActivitiErrorMessageImpl();
    actualActivitiErrorMessageImpl.setCode(1);
    actualActivitiErrorMessageImpl.setMessage("Not all who wander are lost");
    int actualCode = actualActivitiErrorMessageImpl.getCode();

    // Assert that nothing has changed
    assertEquals("Not all who wander are lost", actualActivitiErrorMessageImpl.getMessage());
    assertEquals(1, actualCode);
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When one.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ActivitiErrorMessageImpl#ActivitiErrorMessageImpl(int, String)}
   *   <li>{@link ActivitiErrorMessageImpl#setCode(int)}
   *   <li>{@link ActivitiErrorMessageImpl#setMessage(String)}
   *   <li>{@link ActivitiErrorMessageImpl#getCode()}
   *   <li>{@link ActivitiErrorMessageImpl#getMessage()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters; when one")
  void testGettersAndSetters_whenOne() {
    // Arrange and Act
    ActivitiErrorMessageImpl actualActivitiErrorMessageImpl = new ActivitiErrorMessageImpl(1,
        "Not all who wander are lost");
    actualActivitiErrorMessageImpl.setCode(1);
    actualActivitiErrorMessageImpl.setMessage("Not all who wander are lost");
    int actualCode = actualActivitiErrorMessageImpl.getCode();

    // Assert that nothing has changed
    assertEquals("Not all who wander are lost", actualActivitiErrorMessageImpl.getMessage());
    assertEquals(1, actualCode);
  }
}
