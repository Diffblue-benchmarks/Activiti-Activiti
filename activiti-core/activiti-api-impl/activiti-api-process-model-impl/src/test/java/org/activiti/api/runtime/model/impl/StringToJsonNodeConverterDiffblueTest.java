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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {StringToJsonNodeConverter.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class StringToJsonNodeConverterDiffblueTest {
  @MockBean
  private ObjectMapper objectMapper;

  @Autowired
  private StringToJsonNodeConverter stringToJsonNodeConverter;

  /**
   * Test {@link StringToJsonNodeConverter#convert(String)} with {@code String}.
   * <ul>
   *   <li>Then return {@link ArrayNode}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringToJsonNodeConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; then return ArrayNode")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"JsonNode StringToJsonNodeConverter.convert(String)"})
  void testConvertWithString_thenReturnArrayNode() throws JsonProcessingException {
    // Arrange
    StringToJsonNodeConverter stringToJsonNodeConverter = new StringToJsonNodeConverter(
        JsonMapper.builder().findAndAddModules().build());
    JsonMapper buildResult = JsonMapper.builder().findAndAddModules().build();
    ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    JsonNode actualConvertResult = stringToJsonNodeConverter.convert(buildResult.writeValueAsString(arrayNode));

    // Assert
    assertTrue(actualConvertResult instanceof ArrayNode);
    assertEquals(arrayNode, actualConvertResult);
  }

  /**
   * Test {@link StringToJsonNodeConverter#convert(String)} with {@code String}.
   * <ul>
   *   <li>Then return Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringToJsonNodeConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; then return Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"JsonNode StringToJsonNodeConverter.convert(String)"})
  void testConvertWithString_thenReturnInstance() throws JsonProcessingException {
    // Arrange
    when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<JsonNode>>any()))
        .thenReturn(MissingNode.getInstance());
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");
    MissingNode instance = MissingNode.getInstance();

    // Act
    JsonNode actualConvertResult = stringToJsonNodeConverter.convert(objectMapper.writeValueAsString(instance));

    // Assert
    verify(objectMapper).readValue(eq("42"), isA(Class.class));
    verify(objectMapper).writeValueAsString(isA(Object.class));
    assertSame(instance, actualConvertResult);
  }

  /**
   * Test {@link StringToJsonNodeConverter#convert(String)} with {@code String}.
   * <ul>
   *   <li>Then return {@link NullNode#instance}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringToJsonNodeConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; then return instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"JsonNode StringToJsonNodeConverter.convert(String)"})
  void testConvertWithString_thenReturnInstance2() throws JsonProcessingException {
    // Arrange
    StringToJsonNodeConverter stringToJsonNodeConverter = new StringToJsonNodeConverter(
        JsonMapper.builder().findAndAddModules().build());
    JsonMapper buildResult = JsonMapper.builder().findAndAddModules().build();

    // Act
    JsonNode actualConvertResult = stringToJsonNodeConverter
        .convert(buildResult.writeValueAsString(MissingNode.getInstance()));

    // Assert
    assertSame(((NullNode) actualConvertResult).instance, actualConvertResult);
  }

  /**
   * Test {@link StringToJsonNodeConverter#convert(String)} with {@code String}.
   * <ul>
   *   <li>Then return {@link IntNode}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringToJsonNodeConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; then return IntNode")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"JsonNode StringToJsonNodeConverter.convert(String)"})
  void testConvertWithString_thenReturnIntNode() throws JsonProcessingException {
    // Arrange
    StringToJsonNodeConverter stringToJsonNodeConverter = new StringToJsonNodeConverter(
        JsonMapper.builder().findAndAddModules().build());

    // Act
    JsonNode actualConvertResult = stringToJsonNodeConverter
        .convert(JsonMapper.builder().findAndAddModules().build().writeValueAsString(42));

    // Assert
    assertTrue(actualConvertResult instanceof IntNode);
    assertTrue(actualConvertResult.traverse() instanceof TreeTraversingParser);
    assertEquals("42", actualConvertResult.toPrettyString());
    assertEquals(JsonNodeType.NUMBER, actualConvertResult.getNodeType());
    assertFalse(((IntNode) actualConvertResult).isNaN());
    assertTrue(actualConvertResult.isInt());
    assertTrue(actualConvertResult.isIntegralNumber());
    assertTrue(actualConvertResult.isNumber());
  }

  /**
   * Test {@link StringToJsonNodeConverter#convert(String)} with {@code String}.
   * <ul>
   *   <li>Then return {@link TextNode}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringToJsonNodeConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; then return TextNode")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"JsonNode StringToJsonNodeConverter.convert(String)"})
  void testConvertWithString_thenReturnTextNode() throws JsonProcessingException {
    // Arrange
    StringToJsonNodeConverter stringToJsonNodeConverter = new StringToJsonNodeConverter(
        JsonMapper.builder().findAndAddModules().build());

    // Act
    JsonNode actualConvertResult = stringToJsonNodeConverter
        .convert(JsonMapper.builder().findAndAddModules().build().writeValueAsString("42"));

    // Assert
    assertTrue(actualConvertResult instanceof TextNode);
    assertTrue(actualConvertResult.traverse() instanceof TreeTraversingParser);
    assertEquals("\"42\"", actualConvertResult.toPrettyString());
    assertEquals(JsonNodeType.STRING, actualConvertResult.getNodeType());
    assertTrue(actualConvertResult.isTextual());
  }

  /**
   * Test {@link StringToJsonNodeConverter#convert(String)} with {@code String}.
   * <ul>
   *   <li>When {@link ArrayNode} {@link ArrayNode#serialize(JsonGenerator, SerializerProvider)} does nothing.</li>
   *   <li>Then calls {@link ArrayNode#serialize(JsonGenerator, SerializerProvider)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringToJsonNodeConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; when ArrayNode serialize(JsonGenerator, SerializerProvider) does nothing; then calls serialize(JsonGenerator, SerializerProvider)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"JsonNode StringToJsonNodeConverter.convert(String)"})
  void testConvertWithString_whenArrayNodeSerializeDoesNothing_thenCallsSerialize() throws IOException {
    // Arrange
    StringToJsonNodeConverter stringToJsonNodeConverter = new StringToJsonNodeConverter(
        JsonMapper.builder().findAndAddModules().build());
    ArrayNode arrayNode = mock(ArrayNode.class);
    doNothing().when(arrayNode).serialize(Mockito.<JsonGenerator>any(), Mockito.<SerializerProvider>any());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> stringToJsonNodeConverter
        .convert(JsonMapper.builder().findAndAddModules().build().writeValueAsString(arrayNode)));
    verify(arrayNode).serialize(isA(JsonGenerator.class), isA(SerializerProvider.class));
  }

  /**
   * Test {@link StringToJsonNodeConverter#convert(String)} with {@code String}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringToJsonNodeConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; when 'null'; then throw RuntimeException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"JsonNode StringToJsonNodeConverter.convert(String)"})
  void testConvertWithString_whenNull_thenThrowRuntimeException() {
    // Arrange, Act and Assert
    assertThrows(RuntimeException.class,
        () -> (new StringToJsonNodeConverter(JsonMapper.builder().findAndAddModules().build())).convert(null));
  }

  /**
   * Test {@link StringToJsonNodeConverter#convert(String)} with {@code String}.
   * <ul>
   *   <li>When {@code Source}.</li>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StringToJsonNodeConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; when 'Source'; then throw RuntimeException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"JsonNode StringToJsonNodeConverter.convert(String)"})
  void testConvertWithString_whenSource_thenThrowRuntimeException() {
    // Arrange, Act and Assert
    assertThrows(RuntimeException.class,
        () -> (new StringToJsonNodeConverter(JsonMapper.builder().findAndAddModules().build())).convert("Source"));
  }
}
