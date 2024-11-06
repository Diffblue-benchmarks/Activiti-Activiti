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
package org.activiti.core.common.model.connector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VariableDefinitionDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link VariableDefinition}
   *   <li>{@link VariableDefinition#setAnalytics(boolean)}
   *   <li>{@link VariableDefinition#setDescription(String)}
   *   <li>{@link VariableDefinition#setDisplay(Boolean)}
   *   <li>{@link VariableDefinition#setDisplayName(String)}
   *   <li>{@link VariableDefinition#setId(String)}
   *   <li>{@link VariableDefinition#setName(String)}
   *   <li>{@link VariableDefinition#setRequired(boolean)}
   *   <li>{@link VariableDefinition#setType(String)}
   *   <li>{@link VariableDefinition#getDescription()}
   *   <li>{@link VariableDefinition#getDisplay()}
   *   <li>{@link VariableDefinition#getDisplayName()}
   *   <li>{@link VariableDefinition#getId()}
   *   <li>{@link VariableDefinition#getName()}
   *   <li>{@link VariableDefinition#getType()}
   *   <li>{@link VariableDefinition#isAnalytics()}
   *   <li>{@link VariableDefinition#isRequired()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    VariableDefinition actualVariableDefinition = new VariableDefinition();
    actualVariableDefinition.setAnalytics(true);
    actualVariableDefinition.setDescription("The characteristics of someone or something");
    actualVariableDefinition.setDisplay(true);
    actualVariableDefinition.setDisplayName("Display Name");
    actualVariableDefinition.setId("42");
    actualVariableDefinition.setName("Name");
    actualVariableDefinition.setRequired(true);
    actualVariableDefinition.setType("Type");
    String actualDescription = actualVariableDefinition.getDescription();
    Boolean actualDisplay = actualVariableDefinition.getDisplay();
    String actualDisplayName = actualVariableDefinition.getDisplayName();
    String actualId = actualVariableDefinition.getId();
    String actualName = actualVariableDefinition.getName();
    String actualType = actualVariableDefinition.getType();
    boolean actualIsAnalyticsResult = actualVariableDefinition.isAnalytics();

    // Assert that nothing has changed
    assertEquals("42", actualId);
    assertEquals("Display Name", actualDisplayName);
    assertEquals("Name", actualName);
    assertEquals("The characteristics of someone or something", actualDescription);
    assertEquals("Type", actualType);
    assertTrue(actualDisplay);
    assertTrue(actualIsAnalyticsResult);
    assertTrue(actualVariableDefinition.isRequired());
  }
}
