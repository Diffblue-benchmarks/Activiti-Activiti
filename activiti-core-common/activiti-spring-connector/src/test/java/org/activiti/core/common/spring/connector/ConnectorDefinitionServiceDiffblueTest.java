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
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.activiti.core.common.model.connector.ConnectorDefinition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

  /**
   * Test {@link ConnectorDefinitionService#ConnectorDefinitionService(String, ObjectMapper, ResourcePatternResolver)}.
   * <p>
   * Method under test: {@link ConnectorDefinitionService#ConnectorDefinitionService(String, ObjectMapper, ResourcePatternResolver)}
   */
  @Test
  @DisplayName("Test new ConnectorDefinitionService(String, ObjectMapper, ResourcePatternResolver)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ConnectorDefinitionService.<init>(String, ObjectMapper, ResourcePatternResolver)"})
  void testNewConnectorDefinitionService() throws IOException {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

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
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List ConnectorDefinitionService.get()"})
  void testGet() throws IOException {
    // Arrange, Act and Assert
    assertTrue(connectorDefinitionService.get().isEmpty());
  }

  /**
   * Test {@link ConnectorDefinitionService#validate(List)}.
   * <ul>
   *   <li>Given {@link ConnectorDefinition} (default constructor) Name is {@code .}.</li>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConnectorDefinitionService#validate(List)}
   */
  @Test
  @DisplayName("Test validate(List); given ConnectorDefinition (default constructor) Name is '.'; then throw IllegalStateException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ConnectorDefinitionService.validate(List)"})
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
   *   <li>Given {@link ConnectorDefinition} (default constructor) Name is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConnectorDefinitionService#validate(List)}
   */
  @Test
  @DisplayName("Test validate(List); given ConnectorDefinition (default constructor) Name is empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ConnectorDefinitionService.validate(List)"})
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
   *   <li>Given {@link ConnectorDefinition} (default constructor) Name is {@code Name}.</li>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConnectorDefinitionService#validate(List)}
   */
  @Test
  @DisplayName("Test validate(List); given ConnectorDefinition (default constructor) Name is 'Name'; then throw IllegalStateException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ConnectorDefinitionService.validate(List)"})
  void testValidate_givenConnectorDefinitionNameIsName_thenThrowIllegalStateException() {
    // Arrange
    ConnectorDefinition connectorDefinition = new ConnectorDefinition();
    connectorDefinition.setActions(new HashMap<>());
    connectorDefinition.setDescription("The characteristics of someone or something");
    connectorDefinition.setId("42");
    connectorDefinition.setName("Name");

    ConnectorDefinition connectorDefinition2 = new ConnectorDefinition();
    connectorDefinition2.setActions(new HashMap<>());
    connectorDefinition2.setDescription("Description");
    connectorDefinition2.setId("Id");
    connectorDefinition2.setName("42");

    ConnectorDefinition connectorDefinition3 = new ConnectorDefinition();
    connectorDefinition3.setActions(new HashMap<>());
    connectorDefinition3.setDescription("Description");
    connectorDefinition3.setId("Id");
    connectorDefinition3.setName("42");

    ArrayList<ConnectorDefinition> connectorDefinitions = new ArrayList<>();
    connectorDefinitions.add(connectorDefinition3);
    connectorDefinitions.add(connectorDefinition2);
    connectorDefinitions.add(connectorDefinition);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> connectorDefinitionService.validate(connectorDefinitions));
  }

  /**
   * Test {@link ConnectorDefinitionService#validate(List)}.
   * <ul>
   *   <li>Given {@link ConnectorDefinition} (default constructor) Name is {@code null}.</li>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConnectorDefinitionService#validate(List)}
   */
  @Test
  @DisplayName("Test validate(List); given ConnectorDefinition (default constructor) Name is 'null'; then throw IllegalStateException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ConnectorDefinitionService.validate(List)"})
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
