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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ConnectorDefinitionDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ConnectorDefinition}
   *   <li>{@link ConnectorDefinition#setActions(Map)}
   *   <li>{@link ConnectorDefinition#setDescription(String)}
   *   <li>{@link ConnectorDefinition#setId(String)}
   *   <li>{@link ConnectorDefinition#setName(String)}
   *   <li>{@link ConnectorDefinition#getActions()}
   *   <li>{@link ConnectorDefinition#getDescription()}
   *   <li>{@link ConnectorDefinition#getId()}
   *   <li>{@link ConnectorDefinition#getName()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    ConnectorDefinition actualConnectorDefinition = new ConnectorDefinition();
    HashMap<String, ActionDefinition> actions = new HashMap<>();
    actualConnectorDefinition.setActions(actions);
    actualConnectorDefinition.setDescription("The characteristics of someone or something");
    actualConnectorDefinition.setId("42");
    actualConnectorDefinition.setName("Name");
    Map<String, ActionDefinition> actualActions = actualConnectorDefinition.getActions();
    String actualDescription = actualConnectorDefinition.getDescription();
    String actualId = actualConnectorDefinition.getId();

    // Assert that nothing has changed
    assertEquals("42", actualId);
    assertEquals("Name", actualConnectorDefinition.getName());
    assertEquals("The characteristics of someone or something", actualDescription);
    assertTrue(actualActions.isEmpty());
    assertSame(actions, actualActions);
  }
}
