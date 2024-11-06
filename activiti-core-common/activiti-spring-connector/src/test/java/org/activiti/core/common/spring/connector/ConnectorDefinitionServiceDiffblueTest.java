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
package org.activiti.core.common.spring.connector;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.core.common.model.connector.ActionDefinition;
import org.activiti.core.common.model.connector.ConnectorDefinition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ConnectorDefinitionService.class, String.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ConnectorDefinitionServiceDiffblueTest {
  @Autowired
  private ConnectorDefinitionService connectorDefinitionService;

  @MockBean
  private ObjectMapper objectMapper;

  @MockBean
  private ResourcePatternResolver resourcePatternResolver;

  /**
   * Test
   * {@link ConnectorDefinitionService#ConnectorDefinitionService(String, ObjectMapper, ResourcePatternResolver)}.
   * <p>
   * Method under test:
   * {@link ConnectorDefinitionService#ConnectorDefinitionService(String, ObjectMapper, ResourcePatternResolver)}
   */
  @Test
  @DisplayName("Test new ConnectorDefinitionService(String, ObjectMapper, ResourcePatternResolver)")
  void testNewConnectorDefinitionService() throws IOException {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();

    // Act and Assert
    assertTrue((new ConnectorDefinitionService("Connector Root", objectMapper,
        new AnnotationConfigReactiveWebApplicationContext())).get().isEmpty());
  }

  /**
   * Test {@link ConnectorDefinitionService#get()}.
   * <p>
   * Method under test: {@link ConnectorDefinitionService#get()}
   */
  @Test
  @DisplayName("Test get()")
  void testGet() throws IOException {
    // Arrange, Act and Assert
    assertTrue(connectorDefinitionService.get().isEmpty());
  }

  /**
   * Test {@link ConnectorDefinitionService#validate(List)}.
   * <ul>
   *   <li>Given {@link ConnectorDefinition} (default constructor) Description is
   * {@code .}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConnectorDefinitionService#validate(List)}
   */
  @Test
  @DisplayName("Test validate(List); given ConnectorDefinition (default constructor) Description is '.'")
  void testValidate_givenConnectorDefinitionDescriptionIsDot() {
    // Arrange
    ConnectorDefinition connectorDefinition = new ConnectorDefinition();
    connectorDefinition.setActions(new HashMap<>());
    connectorDefinition.setDescription("The characteristics of someone or something");
    connectorDefinition.setId("42");
    connectorDefinition.setName("Name");

    ConnectorDefinition connectorDefinition2 = new ConnectorDefinition();
    connectorDefinition2.setActions(new HashMap<>());
    connectorDefinition2.setDescription(".");
    connectorDefinition2.setId(".");
    connectorDefinition2.setName("Name");

    ArrayList<ConnectorDefinition> connectorDefinitions = new ArrayList<>();
    connectorDefinitions.add(connectorDefinition2);
    connectorDefinitions.add(connectorDefinition);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> connectorDefinitionService.validate(connectorDefinitions));
  }

  /**
   * Test {@link ConnectorDefinitionService#validate(List)}.
   * <ul>
   *   <li>Given {@link ConnectorDefinition} {@link ConnectorDefinition#getName()}
   * return {@code Name}.</li>
   *   <li>Then calls {@link ConnectorDefinition#getName()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConnectorDefinitionService#validate(List)}
   */
  @Test
  @DisplayName("Test validate(List); given ConnectorDefinition getName() return 'Name'; then calls getName()")
  void testValidate_givenConnectorDefinitionGetNameReturnName_thenCallsGetName() {
    // Arrange
    ConnectorDefinition connectorDefinition = mock(ConnectorDefinition.class);
    when(connectorDefinition.getName()).thenReturn("Name");
    doNothing().when(connectorDefinition).setActions(Mockito.<Map<String, ActionDefinition>>any());
    doNothing().when(connectorDefinition).setDescription(Mockito.<String>any());
    doNothing().when(connectorDefinition).setId(Mockito.<String>any());
    doNothing().when(connectorDefinition).setName(Mockito.<String>any());
    connectorDefinition.setActions(new HashMap<>());
    connectorDefinition.setDescription("The characteristics of someone or something");
    connectorDefinition.setId("42");
    connectorDefinition.setName("Name");

    ArrayList<ConnectorDefinition> connectorDefinitions = new ArrayList<>();
    connectorDefinitions.add(connectorDefinition);

    // Act
    connectorDefinitionService.validate(connectorDefinitions);

    // Assert
    verify(connectorDefinition).getName();
    verify(connectorDefinition).setActions(isA(Map.class));
    verify(connectorDefinition).setDescription(eq("The characteristics of someone or something"));
    verify(connectorDefinition).setId(eq("42"));
    verify(connectorDefinition).setName(eq("Name"));
  }

  /**
   * Test {@link ConnectorDefinitionService#validate(List)}.
   * <ul>
   *   <li>Given {@link ConnectorDefinition} (default constructor) Name is
   * {@code .}.</li>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConnectorDefinitionService#validate(List)}
   */
  @Test
  @DisplayName("Test validate(List); given ConnectorDefinition (default constructor) Name is '.'; then throw IllegalStateException")
  void testValidate_givenConnectorDefinitionNameIsDot_thenThrowIllegalStateException() {
    // Arrange
    ConnectorDefinition connectorDefinition = new ConnectorDefinition();
    connectorDefinition.setActions(new HashMap<>());
    connectorDefinition.setDescription("The characteristics of someone or something");
    connectorDefinition.setId("42");
    connectorDefinition.setName(".");

    ArrayList<ConnectorDefinition> connectorDefinitions = new ArrayList<>();
    connectorDefinitions.add(connectorDefinition);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> connectorDefinitionService.validate(connectorDefinitions));
  }

  /**
   * Test {@link ConnectorDefinitionService#validate(List)}.
   * <ul>
   *   <li>Given {@link ConnectorDefinition} (default constructor) Name is empty
   * string.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConnectorDefinitionService#validate(List)}
   */
  @Test
  @DisplayName("Test validate(List); given ConnectorDefinition (default constructor) Name is empty string")
  void testValidate_givenConnectorDefinitionNameIsEmptyString() {
    // Arrange
    ConnectorDefinition connectorDefinition = new ConnectorDefinition();
    connectorDefinition.setActions(new HashMap<>());
    connectorDefinition.setDescription("The characteristics of someone or something");
    connectorDefinition.setId("42");
    connectorDefinition.setName("");

    ArrayList<ConnectorDefinition> connectorDefinitions = new ArrayList<>();
    connectorDefinitions.add(connectorDefinition);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> connectorDefinitionService.validate(connectorDefinitions));
  }

  /**
   * Test {@link ConnectorDefinitionService#validate(List)}.
   * <ul>
   *   <li>Given {@link ConnectorDefinition} (default constructor) Name is
   * {@code null}.</li>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConnectorDefinitionService#validate(List)}
   */
  @Test
  @DisplayName("Test validate(List); given ConnectorDefinition (default constructor) Name is 'null'; then throw IllegalStateException")
  void testValidate_givenConnectorDefinitionNameIsNull_thenThrowIllegalStateException() {
    // Arrange
    ConnectorDefinition connectorDefinition = new ConnectorDefinition();
    connectorDefinition.setActions(new HashMap<>());
    connectorDefinition.setDescription("The characteristics of someone or something");
    connectorDefinition.setId("42");
    connectorDefinition.setName(null);

    ArrayList<ConnectorDefinition> connectorDefinitions = new ArrayList<>();
    connectorDefinitions.add(connectorDefinition);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> connectorDefinitionService.validate(connectorDefinitions));
  }
}
