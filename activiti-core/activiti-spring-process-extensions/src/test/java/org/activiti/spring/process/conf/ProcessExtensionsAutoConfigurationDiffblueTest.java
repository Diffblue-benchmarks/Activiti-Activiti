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
package org.activiti.spring.process.conf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.activiti.common.util.DateFormatterProvider;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.spring.process.variable.types.DateVariableType;
import org.activiti.spring.process.variable.types.JsonObjectVariableType;
import org.activiti.spring.process.variable.types.VariableType;
import org.activiti.spring.resources.DeploymentResourceLoader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProcessExtensionsAutoConfigurationDiffblueTest {
  /**
   * Test
   * {@link ProcessExtensionsAutoConfiguration#initRepositoryServiceForDeploymentResourceLoader(RepositoryService, DeploymentResourceLoader)}.
   * <p>
   * Method under test:
   * {@link ProcessExtensionsAutoConfiguration#initRepositoryServiceForDeploymentResourceLoader(RepositoryService, DeploymentResourceLoader)}
   */
  @Test
  @DisplayName("Test initRepositoryServiceForDeploymentResourceLoader(RepositoryService, DeploymentResourceLoader)")
  void testInitRepositoryServiceForDeploymentResourceLoader() throws Exception {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessExtensionsAutoConfiguration processExtensionsAutoConfiguration = new ProcessExtensionsAutoConfiguration();
    RepositoryServiceImpl repositoryService = mock(RepositoryServiceImpl.class);
    DeploymentResourceLoader deploymentResourceLoader = new DeploymentResourceLoader();

    // Act
    processExtensionsAutoConfiguration
        .initRepositoryServiceForDeploymentResourceLoader(repositoryService, deploymentResourceLoader)
        .afterPropertiesSet();

    // Assert
    assertTrue(deploymentResourceLoader.loadResourcesForDeployment("42", null).isEmpty());
  }

  /**
   * Test
   * {@link ProcessExtensionsAutoConfiguration#variableTypeMap(ObjectMapper, DateFormatterProvider)}.
   * <ul>
   *   <li>When {@link ObjectMapper#ObjectMapper()}.</li>
   *   <li>Then return {@code array} ObjectMapper is
   * {@link ObjectMapper#ObjectMapper()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessExtensionsAutoConfiguration#variableTypeMap(ObjectMapper, DateFormatterProvider)}
   */
  @Test
  @DisplayName("Test variableTypeMap(ObjectMapper, DateFormatterProvider); when ObjectMapper(); then return 'array' ObjectMapper is ObjectMapper()")
  void testVariableTypeMap_whenObjectMapper_thenReturnArrayObjectMapperIsObjectMapper() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessExtensionsAutoConfiguration processExtensionsAutoConfiguration = new ProcessExtensionsAutoConfiguration();
    ObjectMapper objectMapper = new ObjectMapper();

    // Act
    Map<String, VariableType> actualVariableTypeMapResult = processExtensionsAutoConfiguration
        .variableTypeMap(objectMapper, new DateFormatterProvider("2020-03-01"));

    // Assert
    assertEquals(11, actualVariableTypeMapResult.size());
    assertTrue(actualVariableTypeMapResult.get("datetime") instanceof DateVariableType);
    VariableType getResult = actualVariableTypeMapResult.get("array");
    assertTrue(getResult instanceof JsonObjectVariableType);
    VariableType getResult2 = actualVariableTypeMapResult.get("content");
    assertTrue(getResult2 instanceof JsonObjectVariableType);
    VariableType getResult3 = actualVariableTypeMapResult.get("file");
    assertTrue(getResult3 instanceof JsonObjectVariableType);
    VariableType getResult4 = actualVariableTypeMapResult.get("folder");
    assertTrue(getResult4 instanceof JsonObjectVariableType);
    VariableType getResult5 = actualVariableTypeMapResult.get("json");
    assertTrue(getResult5 instanceof JsonObjectVariableType);
    assertTrue(actualVariableTypeMapResult.containsKey("bigdecimal"));
    assertTrue(actualVariableTypeMapResult.containsKey("boolean"));
    assertTrue(actualVariableTypeMapResult.containsKey("date"));
    assertTrue(actualVariableTypeMapResult.containsKey("integer"));
    assertTrue(actualVariableTypeMapResult.containsKey("string"));
    assertSame(objectMapper, ((JsonObjectVariableType) getResult).getObjectMapper());
    assertSame(objectMapper, ((JsonObjectVariableType) getResult2).getObjectMapper());
    assertSame(objectMapper, ((JsonObjectVariableType) getResult3).getObjectMapper());
    assertSame(objectMapper, ((JsonObjectVariableType) getResult4).getObjectMapper());
    assertSame(objectMapper, ((JsonObjectVariableType) getResult5).getObjectMapper());
  }

  /**
   * Test
   * {@link ProcessExtensionsAutoConfiguration#variableTypeMap(ObjectMapper, DateFormatterProvider)}.
   * <ul>
   *   <li>When {@link ObjectMapper}.</li>
   *   <li>Then return {@code array} ObjectMapper is {@link ObjectMapper}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessExtensionsAutoConfiguration#variableTypeMap(ObjectMapper, DateFormatterProvider)}
   */
  @Test
  @DisplayName("Test variableTypeMap(ObjectMapper, DateFormatterProvider); when ObjectMapper; then return 'array' ObjectMapper is ObjectMapper")
  void testVariableTypeMap_whenObjectMapper_thenReturnArrayObjectMapperIsObjectMapper2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessExtensionsAutoConfiguration processExtensionsAutoConfiguration = new ProcessExtensionsAutoConfiguration();
    ObjectMapper objectMapper = mock(ObjectMapper.class);

    // Act
    Map<String, VariableType> actualVariableTypeMapResult = processExtensionsAutoConfiguration
        .variableTypeMap(objectMapper, new DateFormatterProvider("2020-03-01"));

    // Assert
    assertEquals(11, actualVariableTypeMapResult.size());
    assertTrue(actualVariableTypeMapResult.get("datetime") instanceof DateVariableType);
    VariableType getResult = actualVariableTypeMapResult.get("array");
    assertTrue(getResult instanceof JsonObjectVariableType);
    VariableType getResult2 = actualVariableTypeMapResult.get("content");
    assertTrue(getResult2 instanceof JsonObjectVariableType);
    VariableType getResult3 = actualVariableTypeMapResult.get("file");
    assertTrue(getResult3 instanceof JsonObjectVariableType);
    VariableType getResult4 = actualVariableTypeMapResult.get("folder");
    assertTrue(getResult4 instanceof JsonObjectVariableType);
    VariableType getResult5 = actualVariableTypeMapResult.get("json");
    assertTrue(getResult5 instanceof JsonObjectVariableType);
    assertTrue(actualVariableTypeMapResult.containsKey("bigdecimal"));
    assertTrue(actualVariableTypeMapResult.containsKey("boolean"));
    assertTrue(actualVariableTypeMapResult.containsKey("date"));
    assertTrue(actualVariableTypeMapResult.containsKey("integer"));
    assertTrue(actualVariableTypeMapResult.containsKey("string"));
    assertSame(objectMapper, ((JsonObjectVariableType) getResult).getObjectMapper());
    assertSame(objectMapper, ((JsonObjectVariableType) getResult2).getObjectMapper());
    assertSame(objectMapper, ((JsonObjectVariableType) getResult3).getObjectMapper());
    assertSame(objectMapper, ((JsonObjectVariableType) getResult4).getObjectMapper());
    assertSame(objectMapper, ((JsonObjectVariableType) getResult5).getObjectMapper());
  }
}
