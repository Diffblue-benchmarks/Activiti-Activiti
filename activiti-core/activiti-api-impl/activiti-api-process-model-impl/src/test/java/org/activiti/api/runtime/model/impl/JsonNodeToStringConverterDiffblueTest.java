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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTypeResolverBuilder;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.json.JsonMapper.Builder;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BinaryNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
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
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class JsonNodeToStringConverterDiffblueTest {
  @Autowired private JsonNodeToStringConverter jsonNodeToStringConverter;

  @MockBean private ObjectMapper objectMapper;

  /**
   * Test {@link JsonNodeToStringConverter#convert(JsonNode)} with {@code JsonNode}.
   *
   * <p>Method under test: {@link JsonNodeToStringConverter#convert(JsonNode)}
   */
  @Test
  @DisplayName("Test convert(JsonNode) with 'JsonNode'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String JsonNodeToStringConverter.convert(JsonNode)"})
  void testConvertWithJsonNode() throws IOException {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new DefaultTypeResolverBuilder(DefaultTyping.JAVA_LANG_OBJECT));
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    JsonNodeToStringConverter jsonNodeToStringConverter =
        new JsonNodeToStringConverter(objectMapper);

    ArrayNode source = mock(ArrayNode.class);
    doNothing()
        .when(source)
        .serialize(Mockito.<JsonGenerator>any(), Mockito.<SerializerProvider>any());

    // Act
    String actualConvertResult = jsonNodeToStringConverter.convert(source);

    // Assert
    verify(source).serialize(isA(JsonGenerator.class), isA(SerializerProvider.class));
    assertEquals("", actualConvertResult);
  }

  /**
   * Test {@link JsonNodeToStringConverter#convert(JsonNode)} with {@code JsonNode}.
   *
   * <p>Method under test: {@link JsonNodeToStringConverter#convert(JsonNode)}
   */
  @Test
  @DisplayName("Test convert(JsonNode) with 'JsonNode'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String JsonNodeToStringConverter.convert(JsonNode)"})
  void testConvertWithJsonNode2() throws IOException {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(
        new DefaultTypeResolverBuilder(DefaultTyping.OBJECT_AND_NON_CONCRETE));
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    JsonNodeToStringConverter jsonNodeToStringConverter =
        new JsonNodeToStringConverter(objectMapper);

    ArrayNode source = mock(ArrayNode.class);
    doNothing()
        .when(source)
        .serialize(Mockito.<JsonGenerator>any(), Mockito.<SerializerProvider>any());

    // Act
    String actualConvertResult = jsonNodeToStringConverter.convert(source);

    // Assert
    verify(source).serialize(isA(JsonGenerator.class), isA(SerializerProvider.class));
    assertEquals("", actualConvertResult);
  }

  /**
   * Test {@link JsonNodeToStringConverter#convert(JsonNode)} with {@code JsonNode}.
   *
   * <p>Method under test: {@link JsonNodeToStringConverter#convert(JsonNode)}
   */
  @Test
  @DisplayName("Test convert(JsonNode) with 'JsonNode'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String JsonNodeToStringConverter.convert(JsonNode)"})
  void testConvertWithJsonNode3() throws IOException {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(
        new DefaultTypeResolverBuilder(DefaultTyping.NON_CONCRETE_AND_ARRAYS));
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    JsonNodeToStringConverter jsonNodeToStringConverter =
        new JsonNodeToStringConverter(objectMapper);

    ArrayNode source = mock(ArrayNode.class);
    doNothing()
        .when(source)
        .serialize(Mockito.<JsonGenerator>any(), Mockito.<SerializerProvider>any());

    // Act
    String actualConvertResult = jsonNodeToStringConverter.convert(source);

    // Assert
    verify(source).serialize(isA(JsonGenerator.class), isA(SerializerProvider.class));
    assertEquals("", actualConvertResult);
  }

  /**
   * Test {@link JsonNodeToStringConverter#convert(JsonNode)} with {@code JsonNode}.
   *
   * <p>Method under test: {@link JsonNodeToStringConverter#convert(JsonNode)}
   */
  @Test
  @DisplayName("Test convert(JsonNode) with 'JsonNode'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String JsonNodeToStringConverter.convert(JsonNode)"})
  void testConvertWithJsonNode4() throws IOException {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new DefaultTypeResolverBuilder(DefaultTyping.NON_FINAL));
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    JsonNodeToStringConverter jsonNodeToStringConverter =
        new JsonNodeToStringConverter(objectMapper);

    ArrayNode source = mock(ArrayNode.class);
    doNothing()
        .when(source)
        .serialize(Mockito.<JsonGenerator>any(), Mockito.<SerializerProvider>any());

    // Act
    String actualConvertResult = jsonNodeToStringConverter.convert(source);

    // Assert
    verify(source).serialize(isA(JsonGenerator.class), isA(SerializerProvider.class));
    assertEquals("", actualConvertResult);
  }

  /**
   * Test {@link JsonNodeToStringConverter#convert(JsonNode)} with {@code JsonNode}.
   *
   * <p>Method under test: {@link JsonNodeToStringConverter#convert(JsonNode)}
   */
  @Test
  @DisplayName("Test convert(JsonNode) with 'JsonNode'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String JsonNodeToStringConverter.convert(JsonNode)"})
  void testConvertWithJsonNode5() throws IOException {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(
        new DefaultTypeResolverBuilder(DefaultTyping.NON_FINAL_AND_ENUMS));
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    JsonNodeToStringConverter jsonNodeToStringConverter =
        new JsonNodeToStringConverter(objectMapper);

    ArrayNode source = mock(ArrayNode.class);
    doNothing()
        .when(source)
        .serialize(Mockito.<JsonGenerator>any(), Mockito.<SerializerProvider>any());

    // Act
    String actualConvertResult = jsonNodeToStringConverter.convert(source);

    // Assert
    verify(source).serialize(isA(JsonGenerator.class), isA(SerializerProvider.class));
    assertEquals("", actualConvertResult);
  }

  /**
   * Test {@link JsonNodeToStringConverter#convert(JsonNode)} with {@code JsonNode}.
   *
   * <p>Method under test: {@link JsonNodeToStringConverter#convert(JsonNode)}
   */
  @Test
  @DisplayName("Test convert(JsonNode) with 'JsonNode'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String JsonNodeToStringConverter.convert(JsonNode)"})
  void testConvertWithJsonNode6() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new DefaultTypeResolverBuilder(DefaultTyping.EVERYTHING));
    JsonMapper objectMapper = builderResult.findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () -> new JsonNodeToStringConverter(objectMapper).convert(mock(ArrayNode.class)));
  }

  /**
   * Test {@link JsonNodeToStringConverter#convert(JsonNode)} with {@code JsonNode}.
   *
   * <ul>
   *   <li>Given builder DefaultTyping is {@link StdTypeResolverBuilder#StdTypeResolverBuilder()}.
   * </ul>
   *
   * <p>Method under test: {@link JsonNodeToStringConverter#convert(JsonNode)}
   */
  @Test
  @DisplayName(
      "Test convert(JsonNode) with 'JsonNode'; given builder DefaultTyping is StdTypeResolverBuilder()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String JsonNodeToStringConverter.convert(JsonNode)"})
  void testConvertWithJsonNode_givenBuilderDefaultTypingIsStdTypeResolverBuilder() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new StdTypeResolverBuilder());
    JsonMapper objectMapper = builderResult.findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () -> new JsonNodeToStringConverter(objectMapper).convert(mock(ArrayNode.class)));
  }

  /**
   * Test {@link JsonNodeToStringConverter#convert(JsonNode)} with {@code JsonNode}.
   *
   * <ul>
   *   <li>Given {@link ObjectMapper} {@link ObjectMapper#writeValueAsString(Object)} return {@code
   *       42}.
   *   <li>Then return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link JsonNodeToStringConverter#convert(JsonNode)}
   */
  @Test
  @DisplayName(
      "Test convert(JsonNode) with 'JsonNode'; given ObjectMapper writeValueAsString(Object) return '42'; then return '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String JsonNodeToStringConverter.convert(JsonNode)"})
  void testConvertWithJsonNode_givenObjectMapperWriteValueAsStringReturn42_thenReturn42()
      throws JsonProcessingException {
    // Arrange
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");

    // Act
    String actualConvertResult = jsonNodeToStringConverter.convert(DoubleNode.valueOf(10.0d));

    // Assert
    verify(objectMapper).writeValueAsString(isA(Object.class));
    assertEquals("42", actualConvertResult);
  }

  /**
   * Test {@link JsonNodeToStringConverter#convert(JsonNode)} with {@code JsonNode}.
   *
   * <ul>
   *   <li>Given {@link ObjectMapper} {@link ObjectMapper#writeValueAsString(Object)} throw {@link
   *       RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link JsonNodeToStringConverter#convert(JsonNode)}
   */
  @Test
  @DisplayName(
      "Test convert(JsonNode) with 'JsonNode'; given ObjectMapper writeValueAsString(Object) throw RuntimeException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String JsonNodeToStringConverter.convert(JsonNode)"})
  void testConvertWithJsonNode_givenObjectMapperWriteValueAsStringThrowRuntimeException()
      throws JsonProcessingException {
    // Arrange
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenThrow(new RuntimeException());

    // Act and Assert
    assertThrows(
        RuntimeException.class, () -> jsonNodeToStringConverter.convert(DoubleNode.valueOf(10.0d)));
    verify(objectMapper).writeValueAsString(isA(Object.class));
  }

  /**
   * Test {@link JsonNodeToStringConverter#convert(JsonNode)} with {@code JsonNode}.
   *
   * <ul>
   *   <li>Given {@link RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link JsonNodeToStringConverter#convert(JsonNode)}
   */
  @Test
  @DisplayName("Test convert(JsonNode) with 'JsonNode'; given RuntimeException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String JsonNodeToStringConverter.convert(JsonNode)"})
  void testConvertWithJsonNode_givenRuntimeException() throws IOException {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonNodeToStringConverter jsonNodeToStringConverter =
        new JsonNodeToStringConverter(objectMapper);

    ArrayNode source = mock(ArrayNode.class);
    doThrow(new RuntimeException())
        .when(source)
        .serialize(Mockito.<JsonGenerator>any(), Mockito.<SerializerProvider>any());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> jsonNodeToStringConverter.convert(source));
    verify(source).serialize(isA(JsonGenerator.class), isA(SerializerProvider.class));
  }

  /**
   * Test {@link JsonNodeToStringConverter#convert(JsonNode)} with {@code JsonNode}.
   *
   * <ul>
   *   <li>Then return {@code "QVhBWEFYQVg="}.
   * </ul>
   *
   * <p>Method under test: {@link JsonNodeToStringConverter#convert(JsonNode)}
   */
  @Test
  @DisplayName("Test convert(JsonNode) with 'JsonNode'; then return '\"QVhBWEFYQVg=\"'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String JsonNodeToStringConverter.convert(JsonNode)"})
  void testConvertWithJsonNode_thenReturnQVhBWEFYQVg() throws UnsupportedEncodingException {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonNodeToStringConverter jsonNodeToStringConverter =
        new JsonNodeToStringConverter(objectMapper);

    // Act
    String actualConvertResult =
        jsonNodeToStringConverter.convert(new BinaryNode("AXAXAXAX".getBytes("UTF-8")));

    // Assert
    assertEquals("\"QVhBWEFYQVg=\"", actualConvertResult);
  }

  /**
   * Test {@link JsonNodeToStringConverter#convert(JsonNode)} with {@code JsonNode}.
   *
   * <ul>
   *   <li>When {@link ArrayNode} {@link ArrayNode#serialize(JsonGenerator, SerializerProvider)}
   *       does nothing.
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link JsonNodeToStringConverter#convert(JsonNode)}
   */
  @Test
  @DisplayName(
      "Test convert(JsonNode) with 'JsonNode'; when ArrayNode serialize(JsonGenerator, SerializerProvider) does nothing; then return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String JsonNodeToStringConverter.convert(JsonNode)"})
  void testConvertWithJsonNode_whenArrayNodeSerializeDoesNothing_thenReturnEmptyString()
      throws IOException {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    JsonNodeToStringConverter jsonNodeToStringConverter =
        new JsonNodeToStringConverter(objectMapper);

    ArrayNode source = mock(ArrayNode.class);
    doNothing()
        .when(source)
        .serialize(Mockito.<JsonGenerator>any(), Mockito.<SerializerProvider>any());

    // Act
    String actualConvertResult = jsonNodeToStringConverter.convert(source);

    // Assert
    verify(source).serialize(isA(JsonGenerator.class), isA(SerializerProvider.class));
    assertEquals("", actualConvertResult);
  }

  /**
   * Test {@link JsonNodeToStringConverter#convert(JsonNode)} with {@code JsonNode}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JsonNodeToStringConverter#convert(JsonNode)}
   */
  @Test
  @DisplayName("Test convert(JsonNode) with 'JsonNode'; when 'null'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String JsonNodeToStringConverter.convert(JsonNode)"})
  void testConvertWithJsonNode_whenNull_thenReturnNull() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertEquals("null", new JsonNodeToStringConverter(objectMapper).convert(null));
  }

  /**
   * Test {@link JsonNodeToStringConverter#convert(JsonNode)} with {@code JsonNode}.
   *
   * <ul>
   *   <li>When valueOf ten.
   *   <li>Then return {@code 10.0}.
   * </ul>
   *
   * <p>Method under test: {@link JsonNodeToStringConverter#convert(JsonNode)}
   */
  @Test
  @DisplayName("Test convert(JsonNode) with 'JsonNode'; when valueOf ten; then return '10.0'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String JsonNodeToStringConverter.convert(JsonNode)"})
  void testConvertWithJsonNode_whenValueOfTen_thenReturn100() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertEquals(
        "10.0", new JsonNodeToStringConverter(objectMapper).convert(DoubleNode.valueOf(10.0d)));
  }
}
