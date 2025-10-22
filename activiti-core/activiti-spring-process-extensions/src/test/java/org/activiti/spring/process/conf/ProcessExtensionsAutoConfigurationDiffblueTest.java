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
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.Map;
import org.activiti.common.util.DateFormatterProvider;
import org.activiti.spring.process.variable.types.BigDecimalVariableType;
import org.activiti.spring.process.variable.types.DateVariableType;
import org.activiti.spring.process.variable.types.JavaObjectVariableType;
import org.activiti.spring.process.variable.types.JsonObjectVariableType;
import org.activiti.spring.process.variable.types.VariableType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProcessExtensionsAutoConfigurationDiffblueTest {
  @InjectMocks
  private ProcessExtensionsAutoConfiguration processExtensionsAutoConfiguration;

  /**
   * Test {@link ProcessExtensionsAutoConfiguration#variableTypeMap(ObjectMapper, DateFormatterProvider)}.
   * <p>
   * Method under test: {@link ProcessExtensionsAutoConfiguration#variableTypeMap(ObjectMapper, DateFormatterProvider)}
   */
  @Test
  @DisplayName("Test variableTypeMap(ObjectMapper, DateFormatterProvider)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Map ProcessExtensionsAutoConfiguration.variableTypeMap(ObjectMapper, DateFormatterProvider)"})
  void testVariableTypeMap() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act
    Map<String, VariableType> actualVariableTypeMapResult = processExtensionsAutoConfiguration
        .variableTypeMap(objectMapper, new DateFormatterProvider("2020-03-01"));

    // Assert
    assertEquals(11, actualVariableTypeMapResult.size());
    assertTrue(actualVariableTypeMapResult.get("bigdecimal") instanceof BigDecimalVariableType);
    assertTrue(actualVariableTypeMapResult.get("date") instanceof DateVariableType);
    assertTrue(actualVariableTypeMapResult.get("datetime") instanceof DateVariableType);
    assertTrue(actualVariableTypeMapResult.get("boolean") instanceof JavaObjectVariableType);
    assertTrue(actualVariableTypeMapResult.get("integer") instanceof JavaObjectVariableType);
    assertTrue(actualVariableTypeMapResult.get("string") instanceof JavaObjectVariableType);
    assertTrue(actualVariableTypeMapResult.get("array") instanceof JsonObjectVariableType);
    assertTrue(actualVariableTypeMapResult.get("content") instanceof JsonObjectVariableType);
    assertTrue(actualVariableTypeMapResult.get("file") instanceof JsonObjectVariableType);
    assertTrue(actualVariableTypeMapResult.get("folder") instanceof JsonObjectVariableType);
    assertTrue(actualVariableTypeMapResult.get("json") instanceof JsonObjectVariableType);
  }
}
