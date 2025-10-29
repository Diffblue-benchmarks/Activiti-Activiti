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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import java.util.Map;
import org.activiti.common.util.DateFormatterProvider;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.spring.process.variable.types.BigDecimalVariableType;
import org.activiti.spring.process.variable.types.DateVariableType;
import org.activiti.spring.process.variable.types.JavaObjectVariableType;
import org.activiti.spring.process.variable.types.JsonObjectVariableType;
import org.activiti.spring.process.variable.types.VariableType;
import org.activiti.spring.resources.DeploymentResourceLoader;
import org.junit.jupiter.api.Test;

class ProcessExtensionsAutoConfigurationDiffblueTest {
  /**
   * Method under test:
   * {@link ProcessExtensionsAutoConfiguration#initRepositoryServiceForDeploymentResourceLoader(RepositoryService, DeploymentResourceLoader)}
   */
  @Test
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
   * Method under test:
   * {@link ProcessExtensionsAutoConfiguration#variableTypeMap(ObjectMapper, DateFormatterProvider)}
   */
  @Test
  void testVariableTypeMap() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessExtensionsAutoConfiguration processExtensionsAutoConfiguration = new ProcessExtensionsAutoConfiguration();
    ObjectMapper objectMapper = new ObjectMapper();

    // Act
    Map<String, VariableType> actualVariableTypeMapResult = processExtensionsAutoConfiguration
        .variableTypeMap(objectMapper, new DateFormatterProvider("2020-03-01"));

    // Assert
    assertEquals(11, actualVariableTypeMapResult.size());
    VariableType getResult = actualVariableTypeMapResult.get("bigdecimal");
    assertTrue(getResult instanceof BigDecimalVariableType);
    VariableType getResult2 = actualVariableTypeMapResult.get("date");
    assertTrue(getResult2 instanceof DateVariableType);
    VariableType getResult3 = actualVariableTypeMapResult.get("datetime");
    assertTrue(getResult3 instanceof DateVariableType);
    VariableType getResult4 = actualVariableTypeMapResult.get("boolean");
    assertTrue(getResult4 instanceof JavaObjectVariableType);
    VariableType getResult5 = actualVariableTypeMapResult.get("integer");
    assertTrue(getResult5 instanceof JavaObjectVariableType);
    VariableType getResult6 = actualVariableTypeMapResult.get("string");
    assertTrue(getResult6 instanceof JavaObjectVariableType);
    VariableType getResult7 = actualVariableTypeMapResult.get("array");
    assertTrue(getResult7 instanceof JsonObjectVariableType);
    VariableType getResult8 = actualVariableTypeMapResult.get("content");
    assertTrue(getResult8 instanceof JsonObjectVariableType);
    VariableType getResult9 = actualVariableTypeMapResult.get("file");
    assertTrue(getResult9 instanceof JsonObjectVariableType);
    VariableType getResult10 = actualVariableTypeMapResult.get("folder");
    assertTrue(getResult10 instanceof JsonObjectVariableType);
    VariableType getResult11 = actualVariableTypeMapResult.get("json");
    assertTrue(getResult11 instanceof JsonObjectVariableType);
    assertNull(getResult7.getName());
    assertNull(getResult.getName());
    assertNull(getResult4.getName());
    assertNull(getResult8.getName());
    assertNull(getResult2.getName());
    assertNull(getResult3.getName());
    assertNull(getResult9.getName());
    assertNull(getResult10.getName());
    assertNull(getResult5.getName());
    assertNull(getResult11.getName());
    assertNull(getResult6.getName());
    Class<Boolean> expectedClazz = Boolean.class;
    assertEquals(expectedClazz, ((JavaObjectVariableType) getResult4).getClazz());
    Class<Integer> expectedClazz2 = Integer.class;
    assertEquals(expectedClazz2, ((JavaObjectVariableType) getResult5).getClazz());
    Class<String> expectedClazz3 = String.class;
    assertEquals(expectedClazz3, ((JavaObjectVariableType) getResult6).getClazz());
    Class<Date> expectedClazz4 = Date.class;
    Class clazz = ((DateVariableType) getResult2).getClazz();
    assertEquals(expectedClazz4, clazz);
    assertSame(objectMapper, ((JsonObjectVariableType) getResult7).getObjectMapper());
    assertSame(objectMapper, ((JsonObjectVariableType) getResult8).getObjectMapper());
    assertSame(objectMapper, ((JsonObjectVariableType) getResult9).getObjectMapper());
    assertSame(objectMapper, ((JsonObjectVariableType) getResult10).getObjectMapper());
    assertSame(objectMapper, ((JsonObjectVariableType) getResult11).getObjectMapper());
    assertSame(clazz, ((DateVariableType) getResult3).getClazz());
  }

  /**
   * Method under test:
   * {@link ProcessExtensionsAutoConfiguration#variableTypeMap(ObjectMapper, DateFormatterProvider)}
   */
  @Test
  void testVariableTypeMap2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessExtensionsAutoConfiguration processExtensionsAutoConfiguration = new ProcessExtensionsAutoConfiguration();
    ObjectMapper objectMapper = mock(ObjectMapper.class);

    // Act
    Map<String, VariableType> actualVariableTypeMapResult = processExtensionsAutoConfiguration
        .variableTypeMap(objectMapper, new DateFormatterProvider("2020-03-01"));

    // Assert
    assertEquals(11, actualVariableTypeMapResult.size());
    VariableType getResult = actualVariableTypeMapResult.get("bigdecimal");
    assertTrue(getResult instanceof BigDecimalVariableType);
    VariableType getResult2 = actualVariableTypeMapResult.get("date");
    assertTrue(getResult2 instanceof DateVariableType);
    VariableType getResult3 = actualVariableTypeMapResult.get("datetime");
    assertTrue(getResult3 instanceof DateVariableType);
    VariableType getResult4 = actualVariableTypeMapResult.get("boolean");
    assertTrue(getResult4 instanceof JavaObjectVariableType);
    VariableType getResult5 = actualVariableTypeMapResult.get("integer");
    assertTrue(getResult5 instanceof JavaObjectVariableType);
    VariableType getResult6 = actualVariableTypeMapResult.get("string");
    assertTrue(getResult6 instanceof JavaObjectVariableType);
    VariableType getResult7 = actualVariableTypeMapResult.get("array");
    assertTrue(getResult7 instanceof JsonObjectVariableType);
    VariableType getResult8 = actualVariableTypeMapResult.get("content");
    assertTrue(getResult8 instanceof JsonObjectVariableType);
    VariableType getResult9 = actualVariableTypeMapResult.get("file");
    assertTrue(getResult9 instanceof JsonObjectVariableType);
    VariableType getResult10 = actualVariableTypeMapResult.get("folder");
    assertTrue(getResult10 instanceof JsonObjectVariableType);
    VariableType getResult11 = actualVariableTypeMapResult.get("json");
    assertTrue(getResult11 instanceof JsonObjectVariableType);
    assertNull(getResult7.getName());
    assertNull(getResult.getName());
    assertNull(getResult4.getName());
    assertNull(getResult8.getName());
    assertNull(getResult2.getName());
    assertNull(getResult3.getName());
    assertNull(getResult9.getName());
    assertNull(getResult10.getName());
    assertNull(getResult5.getName());
    assertNull(getResult11.getName());
    assertNull(getResult6.getName());
    Class<Boolean> expectedClazz = Boolean.class;
    assertEquals(expectedClazz, ((JavaObjectVariableType) getResult4).getClazz());
    Class<Integer> expectedClazz2 = Integer.class;
    assertEquals(expectedClazz2, ((JavaObjectVariableType) getResult5).getClazz());
    Class<String> expectedClazz3 = String.class;
    assertEquals(expectedClazz3, ((JavaObjectVariableType) getResult6).getClazz());
    Class<Date> expectedClazz4 = Date.class;
    Class clazz = ((DateVariableType) getResult2).getClazz();
    assertEquals(expectedClazz4, clazz);
    assertSame(clazz, ((DateVariableType) getResult3).getClazz());
    assertSame(objectMapper, ((JsonObjectVariableType) getResult7).getObjectMapper());
    assertSame(objectMapper, ((JsonObjectVariableType) getResult8).getObjectMapper());
    assertSame(objectMapper, ((JsonObjectVariableType) getResult9).getObjectMapper());
    assertSame(objectMapper, ((JsonObjectVariableType) getResult10).getObjectMapper());
    assertSame(objectMapper, ((JsonObjectVariableType) getResult11).getObjectMapper());
  }
}
