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
package org.activiti.editor.language.json.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ModelInfoDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ModelInfo#ModelInfo(String, String, String)}
   *   <li>{@link ModelInfo#setId(String)}
   *   <li>{@link ModelInfo#setKey(String)}
   *   <li>{@link ModelInfo#setName(String)}
   *   <li>{@link ModelInfo#getId()}
   *   <li>{@link ModelInfo#getKey()}
   *   <li>{@link ModelInfo#getName()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    ModelInfo actualModelInfo = new ModelInfo("42", "Name", "Key");
    actualModelInfo.setId("42");
    actualModelInfo.setKey("Key");
    actualModelInfo.setName("Name");
    String actualId = actualModelInfo.getId();
    String actualKey = actualModelInfo.getKey();

    // Assert that nothing has changed
    assertEquals("42", actualId);
    assertEquals("Key", actualKey);
    assertEquals("Name", actualModelInfo.getName());
  }
}
