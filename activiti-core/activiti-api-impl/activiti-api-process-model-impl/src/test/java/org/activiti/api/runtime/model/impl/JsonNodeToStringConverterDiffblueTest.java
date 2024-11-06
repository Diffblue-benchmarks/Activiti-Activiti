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
package org.activiti.api.runtime.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {JsonNodeToStringConverter.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class JsonNodeToStringConverterDiffblueTest {
  @Autowired
  private JsonNodeToStringConverter jsonNodeToStringConverter;

  @MockBean
  private ObjectMapper objectMapper;

  /**
   * Test {@link JsonNodeToStringConverter#convert(JsonNode)} with
   * {@code JsonNode}.
   * <ul>
   *   <li>Given {@link ObjectMapper}
   * {@link ObjectMapper#writeValueAsString(Object)} return {@code 42}.</li>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonNodeToStringConverter#convert(JsonNode)}
   */
  @Test
  @DisplayName("Test convert(JsonNode) with 'JsonNode'; given ObjectMapper writeValueAsString(Object) return '42'; then return '42'")
  void testConvertWithJsonNode_givenObjectMapperWriteValueAsStringReturn42_thenReturn42()
      throws JsonProcessingException {
    // Arrange
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");

    // Act
    String actualConvertResult = jsonNodeToStringConverter.convert(MissingNode.getInstance());

    // Assert
    verify(objectMapper).writeValueAsString(isA(Object.class));
    assertEquals("42", actualConvertResult);
  }

  /**
   * Test {@link JsonNodeToStringConverter#convert(JsonNode)} with
   * {@code JsonNode}.
   * <ul>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonNodeToStringConverter#convert(JsonNode)}
   */
  @Test
  @DisplayName("Test convert(JsonNode) with 'JsonNode'; then throw RuntimeException")
  void testConvertWithJsonNode_thenThrowRuntimeException() throws JsonProcessingException {
    // Arrange
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenThrow(new RuntimeException("foo"));

    // Act and Assert
    assertThrows(RuntimeException.class, () -> jsonNodeToStringConverter.convert(MissingNode.getInstance()));
    verify(objectMapper).writeValueAsString(isA(Object.class));
  }
}
