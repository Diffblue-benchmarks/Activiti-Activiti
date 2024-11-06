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
package org.activiti.runtime.api.event.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ActivitiEntityEventHelperDiffblueTest {
  /**
   * Test {@link ActivitiEntityEventHelper#isProcessInstanceEntity(Object)}.
   * <ul>
   *   <li>When {@code Entity}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEntityEventHelper#isProcessInstanceEntity(Object)}
   */
  @Test
  @DisplayName("Test isProcessInstanceEntity(Object); when 'Entity'")
  void testIsProcessInstanceEntity_whenEntity() {
    // Arrange, Act and Assert
    assertFalse(ActivitiEntityEventHelper.isProcessInstanceEntity("Entity"));
  }

  /**
   * Test {@link ActivitiEntityEventHelper#isProcessInstanceEntity(Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiEntityEventHelper#isProcessInstanceEntity(Object)}
   */
  @Test
  @DisplayName("Test isProcessInstanceEntity(Object); when 'null'")
  void testIsProcessInstanceEntity_whenNull() {
    // Arrange, Act and Assert
    assertFalse(ActivitiEntityEventHelper.isProcessInstanceEntity(null));
  }
}
