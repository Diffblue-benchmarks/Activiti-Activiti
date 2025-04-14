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
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTypeResolverBuilder;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.cfg.CacheProvider;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.json.JsonMapper.Builder;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BinaryNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.util.LRUMap;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

@ContextConfiguration(classes = {JsonNodeToStringConverter.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class JsonNodeToStringConverterDiffblueTest {
  @Autowired
  private JsonNodeToStringConverter jsonNodeToStringConverter;

  @MockBean
  private ObjectMapper objectMapper;

  /**
   * Test {@link JsonNodeToStringConverter#convert(JsonNode)} with {@code JsonNode}.
   * <p>
   * Method under test: {@link JsonNodeToStringConverter#convert(JsonNode)}
   */
  @Test
  @DisplayName("Test convert(JsonNode) with 'JsonNode'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String JsonNodeToStringConverter.convert(JsonNode)"})
  void testConvertWithJsonNode() throws JsonProcessingException {
    // Arrange
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenThrow(new RuntimeException("foo"));

    // Act and Assert
    assertThrows(RuntimeException.class, () -> jsonNodeToStringConverter.convert(MissingNode.getInstance()));
    verify(objectMapper).writeValueAsString(isA(Object.class));
  }

  /**
   * Test {@link JsonNodeToStringConverter#convert(JsonNode)} with {@code JsonNode}.
   * <p>
   * Method under test: {@link JsonNodeToStringConverter#convert(JsonNode)}
   */
  @Test
  @DisplayName("Test convert(JsonNode) with 'JsonNode'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String JsonNodeToStringConverter.convert(JsonNode)"})
  void testConvertWithJsonNode2() throws IOException {
    // Arrange
    CacheProvider cacheProvider = mock(CacheProvider.class);
    when(cacheProvider.forDeserializerCache(Mockito.<DeserializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forSerializerCache(Mockito.<SerializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forTypeFactory()).thenReturn(new LRUMap<>(1, 3));
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new DefaultTypeResolverBuilder(DefaultTyping.JAVA_LANG_OBJECT));
    builderResult.cacheProvider(cacheProvider);
    JsonNodeToStringConverter jsonNodeToStringConverter = new JsonNodeToStringConverter(
        builderResult.findAndAddModules().build());
    ArrayNode source = mock(ArrayNode.class);
    doNothing().when(source).serialize(Mockito.<JsonGenerator>any(), Mockito.<SerializerProvider>any());

    // Act
    String actualConvertResult = jsonNodeToStringConverter.convert(source);

    // Assert
    verify(cacheProvider).forDeserializerCache(isNull());
    verify(cacheProvider).forSerializerCache(isNull());
    verify(cacheProvider).forTypeFactory();
    verify(source).serialize(isA(JsonGenerator.class), isA(SerializerProvider.class));
    assertEquals("", actualConvertResult);
  }

  /**
   * Test {@link JsonNodeToStringConverter#convert(JsonNode)} with {@code JsonNode}.
   * <p>
   * Method under test: {@link JsonNodeToStringConverter#convert(JsonNode)}
   */
  @Test
  @DisplayName("Test convert(JsonNode) with 'JsonNode'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String JsonNodeToStringConverter.convert(JsonNode)"})
  void testConvertWithJsonNode3() throws IOException {
    // Arrange
    CacheProvider cacheProvider = mock(CacheProvider.class);
    when(cacheProvider.forDeserializerCache(Mockito.<DeserializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forSerializerCache(Mockito.<SerializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forTypeFactory()).thenReturn(new LRUMap<>(1, 3));
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new DefaultTypeResolverBuilder(DefaultTyping.OBJECT_AND_NON_CONCRETE));
    builderResult.cacheProvider(cacheProvider);
    JsonNodeToStringConverter jsonNodeToStringConverter = new JsonNodeToStringConverter(
        builderResult.findAndAddModules().build());
    ArrayNode source = mock(ArrayNode.class);
    doNothing().when(source).serialize(Mockito.<JsonGenerator>any(), Mockito.<SerializerProvider>any());

    // Act
    String actualConvertResult = jsonNodeToStringConverter.convert(source);

    // Assert
    verify(cacheProvider).forDeserializerCache(isNull());
    verify(cacheProvider).forSerializerCache(isNull());
    verify(cacheProvider).forTypeFactory();
    verify(source).serialize(isA(JsonGenerator.class), isA(SerializerProvider.class));
    assertEquals("", actualConvertResult);
  }

  /**
   * Test {@link JsonNodeToStringConverter#convert(JsonNode)} with {@code JsonNode}.
   * <p>
   * Method under test: {@link JsonNodeToStringConverter#convert(JsonNode)}
   */
  @Test
  @DisplayName("Test convert(JsonNode) with 'JsonNode'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String JsonNodeToStringConverter.convert(JsonNode)"})
  void testConvertWithJsonNode4() throws IOException {
    // Arrange
    CacheProvider cacheProvider = mock(CacheProvider.class);
    when(cacheProvider.forDeserializerCache(Mockito.<DeserializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forSerializerCache(Mockito.<SerializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forTypeFactory()).thenReturn(new LRUMap<>(1, 3));
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new DefaultTypeResolverBuilder(DefaultTyping.NON_CONCRETE_AND_ARRAYS));
    builderResult.cacheProvider(cacheProvider);
    JsonNodeToStringConverter jsonNodeToStringConverter = new JsonNodeToStringConverter(
        builderResult.findAndAddModules().build());
    ArrayNode source = mock(ArrayNode.class);
    doNothing().when(source).serialize(Mockito.<JsonGenerator>any(), Mockito.<SerializerProvider>any());

    // Act
    String actualConvertResult = jsonNodeToStringConverter.convert(source);

    // Assert
    verify(cacheProvider).forDeserializerCache(isNull());
    verify(cacheProvider).forSerializerCache(isNull());
    verify(cacheProvider).forTypeFactory();
    verify(source).serialize(isA(JsonGenerator.class), isA(SerializerProvider.class));
    assertEquals("", actualConvertResult);
  }

  /**
   * Test {@link JsonNodeToStringConverter#convert(JsonNode)} with {@code JsonNode}.
   * <p>
   * Method under test: {@link JsonNodeToStringConverter#convert(JsonNode)}
   */
  @Test
  @DisplayName("Test convert(JsonNode) with 'JsonNode'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String JsonNodeToStringConverter.convert(JsonNode)"})
  void testConvertWithJsonNode5() throws IOException {
    // Arrange
    CacheProvider cacheProvider = mock(CacheProvider.class);
    when(cacheProvider.forDeserializerCache(Mockito.<DeserializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forSerializerCache(Mockito.<SerializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forTypeFactory()).thenReturn(new LRUMap<>(1, 3));
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new DefaultTypeResolverBuilder(DefaultTyping.NON_FINAL));
    builderResult.cacheProvider(cacheProvider);
    JsonNodeToStringConverter jsonNodeToStringConverter = new JsonNodeToStringConverter(
        builderResult.findAndAddModules().build());
    ArrayNode source = mock(ArrayNode.class);
    doNothing().when(source).serialize(Mockito.<JsonGenerator>any(), Mockito.<SerializerProvider>any());

    // Act
    String actualConvertResult = jsonNodeToStringConverter.convert(source);

    // Assert
    verify(cacheProvider).forDeserializerCache(isNull());
    verify(cacheProvider).forSerializerCache(isNull());
    verify(cacheProvider).forTypeFactory();
    verify(source).serialize(isA(JsonGenerator.class), isA(SerializerProvider.class));
    assertEquals("", actualConvertResult);
  }

  /**
   * Test {@link JsonNodeToStringConverter#convert(JsonNode)} with {@code JsonNode}.
   * <ul>
   *   <li>Given builder DefaultTyping is {@link StdTypeResolverBuilder#StdTypeResolverBuilder()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonNodeToStringConverter#convert(JsonNode)}
   */
  @Test
  @DisplayName("Test convert(JsonNode) with 'JsonNode'; given builder DefaultTyping is StdTypeResolverBuilder()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String JsonNodeToStringConverter.convert(JsonNode)"})
  void testConvertWithJsonNode_givenBuilderDefaultTypingIsStdTypeResolverBuilder() {
    // Arrange
    CacheProvider cacheProvider = mock(CacheProvider.class);
    when(cacheProvider.forDeserializerCache(Mockito.<DeserializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forSerializerCache(Mockito.<SerializationConfig>any())).thenReturn(new LRUMap<>(1, 3));
    when(cacheProvider.forTypeFactory()).thenReturn(new LRUMap<>(1, 3));
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new StdTypeResolverBuilder());
    builderResult.cacheProvider(cacheProvider);

    // Act and Assert
    assertThrows(RuntimeException.class,
        () -> (new JsonNodeToStringConverter(builderResult.findAndAddModules().build()))
            .convert(mock(ArrayNode.class)));
    verify(cacheProvider).forDeserializerCache(isNull());
    verify(cacheProvider).forSerializerCache(isNull());
    verify(cacheProvider).forTypeFactory();
  }

  /**
   * Test {@link JsonNodeToStringConverter#convert(JsonNode)} with {@code JsonNode}.
   * <ul>
   *   <li>Given {@link ObjectMapper} {@link ObjectMapper#writeValueAsString(Object)} return {@code 42}.</li>
   *   <li>Then return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonNodeToStringConverter#convert(JsonNode)}
   */
  @Test
  @DisplayName("Test convert(JsonNode) with 'JsonNode'; given ObjectMapper writeValueAsString(Object) return '42'; then return '42'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String JsonNodeToStringConverter.convert(JsonNode)"})
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
   * Test {@link JsonNodeToStringConverter#convert(JsonNode)} with {@code JsonNode}.
   * <ul>
   *   <li>Then return {@code "QVhBWEFYQVg="}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonNodeToStringConverter#convert(JsonNode)}
   */
  @Test
  @DisplayName("Test convert(JsonNode) with 'JsonNode'; then return '\"QVhBWEFYQVg=\"'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String JsonNodeToStringConverter.convert(JsonNode)"})
  void testConvertWithJsonNode_thenReturnQVhBWEFYQVg() throws UnsupportedEncodingException {
    // Arrange
    JsonNodeToStringConverter jsonNodeToStringConverter = new JsonNodeToStringConverter(
        JsonMapper.builder().findAndAddModules().build());

    // Act and Assert
    assertEquals("\"QVhBWEFYQVg=\"", jsonNodeToStringConverter.convert(new BinaryNode("AXAXAXAX".getBytes("UTF-8"))));
  }

  /**
   * Test {@link JsonNodeToStringConverter#convert(JsonNode)} with {@code JsonNode}.
   * <ul>
   *   <li>When {@link ArrayNode} {@link ArrayNode#serialize(JsonGenerator, SerializerProvider)} does nothing.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonNodeToStringConverter#convert(JsonNode)}
   */
  @Test
  @DisplayName("Test convert(JsonNode) with 'JsonNode'; when ArrayNode serialize(JsonGenerator, SerializerProvider) does nothing; then return empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String JsonNodeToStringConverter.convert(JsonNode)"})
  void testConvertWithJsonNode_whenArrayNodeSerializeDoesNothing_thenReturnEmptyString() throws IOException {
    // Arrange
    JsonNodeToStringConverter jsonNodeToStringConverter = new JsonNodeToStringConverter(
        JsonMapper.builder().findAndAddModules().build());
    ArrayNode source = mock(ArrayNode.class);
    doNothing().when(source).serialize(Mockito.<JsonGenerator>any(), Mockito.<SerializerProvider>any());

    // Act
    String actualConvertResult = jsonNodeToStringConverter.convert(source);

    // Assert
    verify(source).serialize(isA(JsonGenerator.class), isA(SerializerProvider.class));
    assertEquals("", actualConvertResult);
  }

  /**
   * Test {@link JsonNodeToStringConverter#convert(JsonNode)} with {@code JsonNode}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonNodeToStringConverter#convert(JsonNode)}
   */
  @Test
  @DisplayName("Test convert(JsonNode) with 'JsonNode'; when Instance; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String JsonNodeToStringConverter.convert(JsonNode)"})
  void testConvertWithJsonNode_whenInstance_thenReturnNull() {
    // Arrange
    JsonNodeToStringConverter jsonNodeToStringConverter = new JsonNodeToStringConverter(
        JsonMapper.builder().findAndAddModules().build());

    // Act and Assert
    assertEquals("null", jsonNodeToStringConverter.convert(MissingNode.getInstance()));
  }

  /**
   * Test {@link JsonNodeToStringConverter#convert(JsonNode)} with {@code JsonNode}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonNodeToStringConverter#convert(JsonNode)}
   */
  @Test
  @DisplayName("Test convert(JsonNode) with 'JsonNode'; when 'null'; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String JsonNodeToStringConverter.convert(JsonNode)"})
  void testConvertWithJsonNode_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertEquals("null",
        (new JsonNodeToStringConverter(JsonMapper.builder().findAndAddModules().build())).convert(null));
  }
}
