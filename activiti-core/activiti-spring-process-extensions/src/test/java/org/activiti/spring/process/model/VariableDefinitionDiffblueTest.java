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
package org.activiti.spring.process.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VariableDefinitionDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link VariableDefinition#VariableDefinition()}
   *   <li>{@link VariableDefinition#setValue(Object)}
   *   <li>{@link VariableDefinition#getValue()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    VariableDefinition actualVariableDefinition = new VariableDefinition();
    actualVariableDefinition.setValue("Value");

    // Assert that nothing has changed
    assertEquals("Value", actualVariableDefinition.getValue());
    assertFalse(actualVariableDefinition.isAnalytics());
    assertFalse(actualVariableDefinition.isRequired());
  }

  /**
   * Test {@link VariableDefinition#VariableDefinition(String, Object)}.
   * <p>
   * Method under test:
   * {@link VariableDefinition#VariableDefinition(String, Object)}
   */
  @Test
  @DisplayName("Test new VariableDefinition(String, Object)")
  void testNewVariableDefinition() {
    // Arrange and Act
    VariableDefinition actualVariableDefinition = new VariableDefinition("Type", "Value");

    // Assert
    assertEquals("Type", actualVariableDefinition.getType());
    assertEquals("Value", actualVariableDefinition.getValue());
    assertNull(actualVariableDefinition.getDisplay());
    assertNull(actualVariableDefinition.getDescription());
    assertNull(actualVariableDefinition.getDisplayName());
    assertNull(actualVariableDefinition.getId());
    assertNull(actualVariableDefinition.getName());
    assertFalse(actualVariableDefinition.isAnalytics());
    assertFalse(actualVariableDefinition.isRequired());
  }
}
