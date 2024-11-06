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
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ActionDefinitionDiffblueTest {
  /**
   * Test {@link ActionDefinition#getInputs()}.
   * <p>
   * Method under test: {@link ActionDefinition#getInputs()}
   */
  @Test
  @DisplayName("Test getInputs()")
  void testGetInputs() {
    // Arrange
    ActionDefinition actionDefinition = new ActionDefinition();
    actionDefinition.setDescription("The characteristics of someone or something");
    actionDefinition.setId("42");
    actionDefinition.setName("Name");
    actionDefinition.setOutputs(new ArrayList<>());
    actionDefinition.setInputs(new ArrayList<>());

    // Act and Assert
    assertTrue(actionDefinition.getInputs().isEmpty());
  }

  /**
   * Test {@link ActionDefinition#getInputs()}.
   * <ul>
   *   <li>Given {@link ActionDefinition} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ActionDefinition#getInputs()}
   */
  @Test
  @DisplayName("Test getInputs(); given ActionDefinition (default constructor)")
  void testGetInputs_givenActionDefinition() {
    // Arrange, Act and Assert
    assertTrue((new ActionDefinition()).getInputs().isEmpty());
  }

  /**
   * Test {@link ActionDefinition#getOutputs()}.
   * <p>
   * Method under test: {@link ActionDefinition#getOutputs()}
   */
  @Test
  @DisplayName("Test getOutputs()")
  void testGetOutputs() {
    // Arrange
    ActionDefinition actionDefinition = new ActionDefinition();
    actionDefinition.setDescription("The characteristics of someone or something");
    actionDefinition.setId("42");
    actionDefinition.setInputs(new ArrayList<>());
    actionDefinition.setName("Name");
    actionDefinition.setOutputs(new ArrayList<>());

    // Act and Assert
    assertTrue(actionDefinition.getOutputs().isEmpty());
  }

  /**
   * Test {@link ActionDefinition#getOutputs()}.
   * <ul>
   *   <li>Given {@link ActionDefinition} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ActionDefinition#getOutputs()}
   */
  @Test
  @DisplayName("Test getOutputs(); given ActionDefinition (default constructor)")
  void testGetOutputs_givenActionDefinition() {
    // Arrange, Act and Assert
    assertTrue((new ActionDefinition()).getOutputs().isEmpty());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ActionDefinition}
   *   <li>{@link ActionDefinition#setDescription(String)}
   *   <li>{@link ActionDefinition#setId(String)}
   *   <li>{@link ActionDefinition#setInputs(List)}
   *   <li>{@link ActionDefinition#setName(String)}
   *   <li>{@link ActionDefinition#setOutputs(List)}
   *   <li>{@link ActionDefinition#getDescription()}
   *   <li>{@link ActionDefinition#getId()}
   *   <li>{@link ActionDefinition#getName()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    ActionDefinition actualActionDefinition = new ActionDefinition();
    actualActionDefinition.setDescription("The characteristics of someone or something");
    actualActionDefinition.setId("42");
    actualActionDefinition.setInputs(new ArrayList<>());
    actualActionDefinition.setName("Name");
    actualActionDefinition.setOutputs(new ArrayList<>());
    String actualDescription = actualActionDefinition.getDescription();
    String actualId = actualActionDefinition.getId();

    // Assert that nothing has changed
    assertEquals("42", actualId);
    assertEquals("Name", actualActionDefinition.getName());
    assertEquals("The characteristics of someone or something", actualDescription);
  }
}
