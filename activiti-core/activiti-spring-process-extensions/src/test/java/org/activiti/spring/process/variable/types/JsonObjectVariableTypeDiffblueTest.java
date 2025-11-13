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
package org.activiti.spring.process.variable.types;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class JsonObjectVariableTypeDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link JsonObjectVariableType#JsonObjectVariableType(ObjectMapper)}
   *   <li>{@link JsonObjectVariableType#setObjectMapper(ObjectMapper)}
   *   <li>{@link JsonObjectVariableType#getObjectMapper()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void JsonObjectVariableType.<init>(ObjectMapper)",
    "ObjectMapper JsonObjectVariableType.getObjectMapper()",
    "void JsonObjectVariableType.setObjectMapper(ObjectMapper)"
  })
  void testGettersAndSetters() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act
    JsonObjectVariableType actualJsonObjectVariableType = new JsonObjectVariableType(objectMapper);
    JsonMapper objectMapper2 = JsonMapper.builder().findAndAddModules().build();
    actualJsonObjectVariableType.setObjectMapper(objectMapper2);
    ObjectMapper actualObjectMapper = actualJsonObjectVariableType.getObjectMapper();

    // Assert
    assertNull(actualJsonObjectVariableType.getName());
    assertSame(objectMapper2, actualObjectMapper);
  }
}
