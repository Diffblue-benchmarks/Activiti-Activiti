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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.json.JsonMapper.Builder;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.PlaceholderForType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import java.lang.reflect.Type;
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
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class StringToJsonNodeConverterDiffblueTest {
  @MockBean private ObjectMapper objectMapper;

  @Autowired private StringToJsonNodeConverter stringToJsonNodeConverter;

  /**
   * Test {@link StringToJsonNodeConverter#convert(String)} with {@code String}.
   *
   * <p>Method under test: {@link StringToJsonNodeConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"JsonNode StringToJsonNodeConverter.convert(String)"})
  void testConvertWithString() throws IOException {
    // Arrange
    TypeFactory f = mock(TypeFactory.class);
    when(f.constructType(Mockito.<Type>any())).thenReturn(new PlaceholderForType(1));
    JsonMapper m = JsonMapper.builder().findAndAddModules().build();

    Builder builder = new Builder(m);
    builder.typeFactory(f);
    JsonMapper objectMapper = builder.findAndAddModules().build();
    StringToJsonNodeConverter stringToJsonNodeConverter =
        new StringToJsonNodeConverter(objectMapper);

    ArrayNode arrayNode = mock(ArrayNode.class);
    doNothing()
        .when(arrayNode)
        .serialize(Mockito.<JsonGenerator>any(), Mockito.<SerializerProvider>any());

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () ->
            stringToJsonNodeConverter.convert(
                JsonMapper.builder().findAndAddModules().build().writeValueAsString(arrayNode)));
    verify(arrayNode).serialize(isA(JsonGenerator.class), isA(SerializerProvider.class));
    verify(f).constructType(isA(Type.class));
  }

  /**
   * Test {@link StringToJsonNodeConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given {@link ArrayType} {@link ArrayType#getRawClass()} throw {@link
   *       RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link StringToJsonNodeConverter#convert(String)}
   */
  @Test
  @DisplayName(
      "Test convert(String) with 'String'; given ArrayType getRawClass() throw RuntimeException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"JsonNode StringToJsonNodeConverter.convert(String)"})
  void testConvertWithString_givenArrayTypeGetRawClassThrowRuntimeException() throws IOException {
    // Arrange
    ArrayType arrayType = mock(ArrayType.class);
    Mockito.<Class<?>>when(arrayType.getRawClass()).thenThrow(new RuntimeException());

    TypeFactory f = mock(TypeFactory.class);
    when(f.constructType(Mockito.<Type>any())).thenReturn(arrayType);
    JsonMapper m = JsonMapper.builder().findAndAddModules().build();

    Builder builder = new Builder(m);
    builder.typeFactory(f);
    JsonMapper objectMapper = builder.findAndAddModules().build();
    StringToJsonNodeConverter stringToJsonNodeConverter =
        new StringToJsonNodeConverter(objectMapper);

    ArrayNode arrayNode = mock(ArrayNode.class);
    doNothing()
        .when(arrayNode)
        .serialize(Mockito.<JsonGenerator>any(), Mockito.<SerializerProvider>any());

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () ->
            stringToJsonNodeConverter.convert(
                JsonMapper.builder().findAndAddModules().build().writeValueAsString(arrayNode)));
    verify(arrayType).getRawClass();
    verify(arrayNode).serialize(isA(JsonGenerator.class), isA(SerializerProvider.class));
    verify(f).constructType(isA(Type.class));
  }

  /**
   * Test {@link StringToJsonNodeConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given {@code Object}.
   *   <li>Then calls {@link ArrayType#getRawClass()}.
   * </ul>
   *
   * <p>Method under test: {@link StringToJsonNodeConverter#convert(String)}
   */
  @Test
  @DisplayName(
      "Test convert(String) with 'String'; given 'java.lang.Object'; then calls getRawClass()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"JsonNode StringToJsonNodeConverter.convert(String)"})
  void testConvertWithString_givenJavaLangObject_thenCallsGetRawClass() throws IOException {
    // Arrange
    ArrayType arrayType = mock(ArrayType.class);
    Class<Object> forNameResult = Object.class;
    Mockito.<Class<?>>when(arrayType.getRawClass()).thenReturn(forNameResult);

    TypeFactory f = mock(TypeFactory.class);
    when(f.constructType(Mockito.<Type>any())).thenReturn(arrayType);
    JsonMapper m = JsonMapper.builder().findAndAddModules().build();

    Builder builder = new Builder(m);
    builder.typeFactory(f);
    JsonMapper objectMapper = builder.findAndAddModules().build();
    StringToJsonNodeConverter stringToJsonNodeConverter =
        new StringToJsonNodeConverter(objectMapper);

    ArrayNode arrayNode = mock(ArrayNode.class);
    doNothing()
        .when(arrayNode)
        .serialize(Mockito.<JsonGenerator>any(), Mockito.<SerializerProvider>any());

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () ->
            stringToJsonNodeConverter.convert(
                JsonMapper.builder().findAndAddModules().build().writeValueAsString(arrayNode)));
    verify(arrayType).getRawClass();
    verify(arrayNode).serialize(isA(JsonGenerator.class), isA(SerializerProvider.class));
    verify(f).constructType(isA(Type.class));
  }

  /**
   * Test {@link StringToJsonNodeConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given {@link TypeFactory} {@link TypeFactory#constructType(Type)} return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link StringToJsonNodeConverter#convert(String)}
   */
  @Test
  @DisplayName(
      "Test convert(String) with 'String'; given TypeFactory constructType(Type) return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"JsonNode StringToJsonNodeConverter.convert(String)"})
  void testConvertWithString_givenTypeFactoryConstructTypeReturnNull() throws IOException {
    // Arrange
    TypeFactory f = mock(TypeFactory.class);
    when(f.constructType(Mockito.<Type>any())).thenReturn(null);
    JsonMapper m = JsonMapper.builder().findAndAddModules().build();

    Builder builder = new Builder(m);
    builder.typeFactory(f);
    JsonMapper objectMapper = builder.findAndAddModules().build();
    StringToJsonNodeConverter stringToJsonNodeConverter =
        new StringToJsonNodeConverter(objectMapper);

    ArrayNode arrayNode = mock(ArrayNode.class);
    doNothing()
        .when(arrayNode)
        .serialize(Mockito.<JsonGenerator>any(), Mockito.<SerializerProvider>any());

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () ->
            stringToJsonNodeConverter.convert(
                JsonMapper.builder().findAndAddModules().build().writeValueAsString(arrayNode)));
    verify(arrayNode).serialize(isA(JsonGenerator.class), isA(SerializerProvider.class));
    verify(f).constructType(isA(Type.class));
  }

  /**
   * Test {@link StringToJsonNodeConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectMapper#writeValueAsString(Object)}.
   * </ul>
   *
   * <p>Method under test: {@link StringToJsonNodeConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; then calls writeValueAsString(Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"JsonNode StringToJsonNodeConverter.convert(String)"})
  void testConvertWithString_thenCallsWriteValueAsString() throws JsonProcessingException {
    // Arrange
    DoubleNode valueOfResult = DoubleNode.valueOf(10.0d);
    when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<JsonNode>>any()))
        .thenReturn(valueOfResult);
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");

    // Act
    JsonNode actualConvertResult =
        stringToJsonNodeConverter.convert(
            objectMapper.writeValueAsString(DoubleNode.valueOf(10.0d)));

    // Assert
    verify(objectMapper).readValue(eq("42"), isA(Class.class));
    verify(objectMapper).writeValueAsString(isA(Object.class));
    assertSame(valueOfResult, actualConvertResult);
  }

  /**
   * Test {@link StringToJsonNodeConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Then return {@link ArrayNode}.
   * </ul>
   *
   * <p>Method under test: {@link StringToJsonNodeConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; then return ArrayNode")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"JsonNode StringToJsonNodeConverter.convert(String)"})
  void testConvertWithString_thenReturnArrayNode() throws JsonProcessingException {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    StringToJsonNodeConverter stringToJsonNodeConverter =
        new StringToJsonNodeConverter(objectMapper);

    JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode arrayNode = new ArrayNode(nf);
    String source = jsonMapper.writeValueAsString(arrayNode);

    // Act
    JsonNode actualConvertResult = stringToJsonNodeConverter.convert(source);

    // Assert
    assertTrue(actualConvertResult instanceof ArrayNode);
    assertEquals(arrayNode, actualConvertResult);
  }

  /**
   * Test {@link StringToJsonNodeConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Then return {@link DoubleNode}.
   * </ul>
   *
   * <p>Method under test: {@link StringToJsonNodeConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; then return DoubleNode")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"JsonNode StringToJsonNodeConverter.convert(String)"})
  void testConvertWithString_thenReturnDoubleNode() throws JsonProcessingException {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    StringToJsonNodeConverter stringToJsonNodeConverter =
        new StringToJsonNodeConverter(objectMapper);
    DoubleNode valueOfResult = DoubleNode.valueOf(10.0d);

    // Act
    JsonNode actualConvertResult =
        stringToJsonNodeConverter.convert(
            JsonMapper.builder().findAndAddModules().build().writeValueAsString(valueOfResult));

    // Assert
    assertTrue(actualConvertResult instanceof DoubleNode);
    assertEquals(valueOfResult, actualConvertResult);
  }

  /**
   * Test {@link StringToJsonNodeConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Then return {@link BooleanNode#FALSE}.
   * </ul>
   *
   * <p>Method under test: {@link StringToJsonNodeConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; then return FALSE")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"JsonNode StringToJsonNodeConverter.convert(String)"})
  void testConvertWithString_thenReturnFalse() throws JsonProcessingException {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    StringToJsonNodeConverter stringToJsonNodeConverter =
        new StringToJsonNodeConverter(objectMapper);

    // Act
    JsonNode actualConvertResult =
        stringToJsonNodeConverter.convert(
            JsonMapper.builder()
                .findAndAddModules()
                .build()
                .writeValueAsString(BooleanNode.getFalse()));

    // Assert
    assertSame(((BooleanNode) actualConvertResult).FALSE, actualConvertResult);
  }

  /**
   * Test {@link StringToJsonNodeConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Then return {@link NullNode#instance}.
   * </ul>
   *
   * <p>Method under test: {@link StringToJsonNodeConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; then return instance")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"JsonNode StringToJsonNodeConverter.convert(String)"})
  void testConvertWithString_thenReturnInstance() throws JsonProcessingException {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    StringToJsonNodeConverter stringToJsonNodeConverter =
        new StringToJsonNodeConverter(objectMapper);

    // Act
    JsonNode actualConvertResult =
        stringToJsonNodeConverter.convert(
            JsonMapper.builder().findAndAddModules().build().writeValueAsString(null));

    // Assert
    assertSame(((NullNode) actualConvertResult).instance, actualConvertResult);
  }

  /**
   * Test {@link StringToJsonNodeConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Then return {@link IntNode}.
   * </ul>
   *
   * <p>Method under test: {@link StringToJsonNodeConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; then return IntNode")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"JsonNode StringToJsonNodeConverter.convert(String)"})
  void testConvertWithString_thenReturnIntNode() throws JsonProcessingException {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    StringToJsonNodeConverter stringToJsonNodeConverter =
        new StringToJsonNodeConverter(objectMapper);

    // Act
    JsonNode actualConvertResult =
        stringToJsonNodeConverter.convert(
            JsonMapper.builder().findAndAddModules().build().writeValueAsString(42));

    // Assert
    assertTrue(actualConvertResult instanceof IntNode);
    assertTrue(actualConvertResult.traverse() instanceof TreeTraversingParser);
    assertEquals("42", actualConvertResult.toPrettyString());
    assertFalse(((IntNode) actualConvertResult).isNaN());
    assertTrue(actualConvertResult.isInt());
    assertTrue(actualConvertResult.isIntegralNumber());
  }

  /**
   * Test {@link StringToJsonNodeConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Then return {@link TextNode}.
   * </ul>
   *
   * <p>Method under test: {@link StringToJsonNodeConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; then return TextNode")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"JsonNode StringToJsonNodeConverter.convert(String)"})
  void testConvertWithString_thenReturnTextNode() throws JsonProcessingException {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    StringToJsonNodeConverter stringToJsonNodeConverter =
        new StringToJsonNodeConverter(objectMapper);

    // Act
    JsonNode actualConvertResult =
        stringToJsonNodeConverter.convert(
            JsonMapper.builder().findAndAddModules().build().writeValueAsString("42"));

    // Assert
    assertTrue(actualConvertResult instanceof TextNode);
    assertTrue(actualConvertResult.traverse() instanceof TreeTraversingParser);
    assertEquals("\"42\"", actualConvertResult.toPrettyString());
    assertEquals(JsonNodeType.STRING, actualConvertResult.getNodeType());
    assertTrue(actualConvertResult.isTextual());
  }

  /**
   * Test {@link StringToJsonNodeConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>When {@link ArrayNode} {@link ArrayNode#serialize(JsonGenerator, SerializerProvider)}
   *       does nothing.
   *   <li>Then calls {@link ArrayNode#serialize(JsonGenerator, SerializerProvider)}.
   * </ul>
   *
   * <p>Method under test: {@link StringToJsonNodeConverter#convert(String)}
   */
  @Test
  @DisplayName(
      "Test convert(String) with 'String'; when ArrayNode serialize(JsonGenerator, SerializerProvider) does nothing; then calls serialize(JsonGenerator, SerializerProvider)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"JsonNode StringToJsonNodeConverter.convert(String)"})
  void testConvertWithString_whenArrayNodeSerializeDoesNothing_thenCallsSerialize()
      throws IOException {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    StringToJsonNodeConverter stringToJsonNodeConverter =
        new StringToJsonNodeConverter(objectMapper);

    ArrayNode arrayNode = mock(ArrayNode.class);
    doNothing()
        .when(arrayNode)
        .serialize(Mockito.<JsonGenerator>any(), Mockito.<SerializerProvider>any());

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () ->
            stringToJsonNodeConverter.convert(
                JsonMapper.builder().findAndAddModules().build().writeValueAsString(arrayNode)));
    verify(arrayNode).serialize(isA(JsonGenerator.class), isA(SerializerProvider.class));
  }

  /**
   * Test {@link StringToJsonNodeConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link StringToJsonNodeConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; when 'null'; then throw RuntimeException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"JsonNode StringToJsonNodeConverter.convert(String)"})
  void testConvertWithString_whenNull_thenThrowRuntimeException() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class, () -> new StringToJsonNodeConverter(objectMapper).convert(null));
  }

  /**
   * Test {@link StringToJsonNodeConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>When {@code Source}.
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link StringToJsonNodeConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; when 'Source'; then throw RuntimeException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"JsonNode StringToJsonNodeConverter.convert(String)"})
  void testConvertWithString_whenSource_thenThrowRuntimeException() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () -> new StringToJsonNodeConverter(objectMapper).convert("Source"));
  }
}
