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
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ObjectValueDiffblueTest {
  /**
   * Test getters and setters.
   * <ul>
   *   <li>Then return Object is {@code null}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ObjectValue#ObjectValue()}
   *   <li>{@link ObjectValue#getObject()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters; then return Object is 'null'")
  void testGettersAndSetters_thenReturnObjectIsNull() {
    // Arrange, Act and Assert
    assertNull((new ObjectValue()).getObject());
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code Object}.</li>
   *   <li>Then return {@code Object}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ObjectValue#ObjectValue(Object)}
   *   <li>{@link ObjectValue#getObject()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters; when 'Object'; then return 'Object'")
  void testGettersAndSetters_whenObject_thenReturnObject() {
    // Arrange, Act and Assert
    assertEquals("Object", (new ObjectValue("Object")).getObject());
  }
}
