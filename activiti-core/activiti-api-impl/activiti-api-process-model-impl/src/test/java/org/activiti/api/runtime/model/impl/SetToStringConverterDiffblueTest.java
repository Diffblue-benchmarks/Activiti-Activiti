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
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.BeanProperty.Bogus;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.json.JsonMapper.Builder;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.jsontype.impl.AsArrayTypeSerializer;
import com.fasterxml.jackson.databind.jsontype.impl.ClassNameIdResolver;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;
import com.fasterxml.jackson.databind.type.PlaceholderForType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
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

@ContextConfiguration(classes = {SetToStringConverter.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class SetToStringConverterDiffblueTest {
  @MockBean private ObjectMapper objectMapper;

  @Autowired private SetToStringConverter setToStringConverter;

  /**
   * Test {@link SetToStringConverter#convert(Set)} with {@code Set}.
   *
   * <p>Method under test: {@link SetToStringConverter#convert(Set)}
   */
  @Test
  @DisplayName("Test convert(Set) with 'Set'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String SetToStringConverter.convert(Set)"})
  void testConvertWithSet() throws JsonMappingException {
    // Arrange
    SerializerFactory f = mock(SerializerFactory.class);
    Class<Object> type = Object.class;
    when(f.createSerializer(Mockito.<SerializerProvider>any(), Mockito.<JavaType>any()))
        .thenReturn(new Default(1, type));
    PlaceholderForType baseType = new PlaceholderForType(1);
    TypeFactory typeFactory = TypeFactory.defaultInstance();

    BasicPolymorphicTypeValidator.Builder builderResult = BasicPolymorphicTypeValidator.builder();
    Class<Object> baseTypeToDeny = Object.class;
    BasicPolymorphicTypeValidator ptv = builderResult.denyForExactBaseType(baseTypeToDeny).build();

    ClassNameIdResolver idRes = new ClassNameIdResolver(baseType, typeFactory, ptv);
    AsArrayTypeSerializer asArrayTypeSerializer = new AsArrayTypeSerializer(idRes, new Bogus());
    when(f.createTypeSerializer(Mockito.<SerializationConfig>any(), Mockito.<JavaType>any()))
        .thenReturn(asArrayTypeSerializer);

    Builder builderResult2 = JsonMapper.builder();
    builderResult2.serializerFactory(f);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult2.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult2.findAndAddModules().build();
    SetToStringConverter setToStringConverter = new SetToStringConverter(objectMapper);

    // Act and Assert
    assertThrows(RuntimeException.class, () -> setToStringConverter.convert(new HashSet<>()));
    verify(f).createSerializer(isA(SerializerProvider.class), isA(JavaType.class));
    verify(f).createTypeSerializer(isA(SerializationConfig.class), isA(JavaType.class));
  }

  /**
   * Test {@link SetToStringConverter#convert(Set)} with {@code Set}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link HashSet#HashSet()} add {@code 42}.
   *   <li>Then return {@code ["42"]}.
   * </ul>
   *
   * <p>Method under test: {@link SetToStringConverter#convert(Set)}
   */
  @Test
  @DisplayName(
      "Test convert(Set) with 'Set'; given '42'; when HashSet() add '42'; then return '[\"42\"]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String SetToStringConverter.convert(Set)"})
  void testConvertWithSet_given42_whenHashSetAdd42_thenReturn42() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    SetToStringConverter setToStringConverter = new SetToStringConverter(objectMapper);

    HashSet<Object> source = new HashSet<>();
    source.add("42");

    // Act and Assert
    assertEquals("[\"42\"]", setToStringConverter.convert(source));
  }

  /**
   * Test {@link SetToStringConverter#convert(Set)} with {@code Set}.
   *
   * <ul>
   *   <li>Given {@link StdKeySerializers.Default} {@link
   *       StdKeySerializers.Default#serializeWithType(Object, JsonGenerator, SerializerProvider,
   *       TypeSerializer)} throw {@link RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link SetToStringConverter#convert(Set)}
   */
  @Test
  @DisplayName(
      "Test convert(Set) with 'Set'; given Default serializeWithType(Object, JsonGenerator, SerializerProvider, TypeSerializer) throw RuntimeException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String SetToStringConverter.convert(Set)"})
  void testConvertWithSet_givenDefaultSerializeWithTypeThrowRuntimeException() throws IOException {
    // Arrange
    Default resultDefault = mock(Default.class);
    doThrow(new RuntimeException())
        .when(resultDefault)
        .serializeWithType(
            Mockito.<Object>any(),
            Mockito.<JsonGenerator>any(),
            Mockito.<SerializerProvider>any(),
            Mockito.<TypeSerializer>any());

    SerializerFactory f = mock(SerializerFactory.class);
    when(f.createSerializer(Mockito.<SerializerProvider>any(), Mockito.<JavaType>any()))
        .thenReturn(resultDefault);
    PlaceholderForType baseType = new PlaceholderForType(1);
    TypeFactory typeFactory = TypeFactory.defaultInstance();

    BasicPolymorphicTypeValidator.Builder builderResult = BasicPolymorphicTypeValidator.builder();
    Class<Object> baseTypeToDeny = Object.class;
    BasicPolymorphicTypeValidator ptv = builderResult.denyForExactBaseType(baseTypeToDeny).build();

    ClassNameIdResolver idRes = new ClassNameIdResolver(baseType, typeFactory, ptv);
    AsArrayTypeSerializer asArrayTypeSerializer = new AsArrayTypeSerializer(idRes, new Bogus());
    when(f.createTypeSerializer(Mockito.<SerializationConfig>any(), Mockito.<JavaType>any()))
        .thenReturn(asArrayTypeSerializer);

    Builder builderResult2 = JsonMapper.builder();
    builderResult2.serializerFactory(f);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult2.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult2.findAndAddModules().build();
    SetToStringConverter setToStringConverter = new SetToStringConverter(objectMapper);

    // Act and Assert
    assertThrows(RuntimeException.class, () -> setToStringConverter.convert(new HashSet<>()));
    verify(resultDefault)
        .serializeWithType(
            isA(Object.class),
            isA(JsonGenerator.class),
            isA(SerializerProvider.class),
            isA(TypeSerializer.class));
    verify(f).createSerializer(isA(SerializerProvider.class), isA(JavaType.class));
    verify(f).createTypeSerializer(isA(SerializationConfig.class), isA(JavaType.class));
  }

  /**
   * Test {@link SetToStringConverter#convert(Set)} with {@code Set}.
   *
   * <ul>
   *   <li>Given {@link ObjectMapper} {@link ObjectMapper#writeValueAsString(Object)} return {@code
   *       42}.
   *   <li>Then return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link SetToStringConverter#convert(Set)}
   */
  @Test
  @DisplayName(
      "Test convert(Set) with 'Set'; given ObjectMapper writeValueAsString(Object) return '42'; then return '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String SetToStringConverter.convert(Set)"})
  void testConvertWithSet_givenObjectMapperWriteValueAsStringReturn42_thenReturn42()
      throws JsonProcessingException {
    // Arrange
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");

    // Act
    String actualConvertResult = setToStringConverter.convert(new HashSet<>());

    // Assert
    verify(objectMapper).writeValueAsString(isA(Object.class));
    assertEquals("42", actualConvertResult);
  }

  /**
   * Test {@link SetToStringConverter#convert(Set)} with {@code Set}.
   *
   * <ul>
   *   <li>Given {@link ObjectMapper} {@link ObjectMapper#writeValueAsString(Object)} return {@code
   *       42}.
   *   <li>Then return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link SetToStringConverter#convert(Set)}
   */
  @Test
  @DisplayName(
      "Test convert(Set) with 'Set'; given ObjectMapper writeValueAsString(Object) return '42'; then return '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String SetToStringConverter.convert(Set)"})
  void testConvertWithSet_givenObjectMapperWriteValueAsStringReturn42_thenReturn422()
      throws JsonProcessingException {
    // Arrange
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");

    HashSet<Object> source = new HashSet<>();
    source.add("42");

    // Act
    String actualConvertResult = setToStringConverter.convert(source);

    // Assert
    verify(objectMapper).writeValueAsString(isA(Object.class));
    assertEquals("42", actualConvertResult);
  }

  /**
   * Test {@link SetToStringConverter#convert(Set)} with {@code Set}.
   *
   * <ul>
   *   <li>Given {@link ObjectMapper} {@link ObjectMapper#writeValueAsString(Object)} throw {@link
   *       RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link SetToStringConverter#convert(Set)}
   */
  @Test
  @DisplayName(
      "Test convert(Set) with 'Set'; given ObjectMapper writeValueAsString(Object) throw RuntimeException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String SetToStringConverter.convert(Set)"})
  void testConvertWithSet_givenObjectMapperWriteValueAsStringThrowRuntimeException()
      throws JsonProcessingException {
    // Arrange
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenThrow(new RuntimeException());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> setToStringConverter.convert(new HashSet<>()));
    verify(objectMapper).writeValueAsString(isA(Object.class));
  }

  /**
   * Test {@link SetToStringConverter#convert(Set)} with {@code Set}.
   *
   * <ul>
   *   <li>Given {@link SerializerFactory} {@link
   *       SerializerFactory#createSerializer(SerializerProvider, JavaType)} return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link SetToStringConverter#convert(Set)}
   */
  @Test
  @DisplayName(
      "Test convert(Set) with 'Set'; given SerializerFactory createSerializer(SerializerProvider, JavaType) return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String SetToStringConverter.convert(Set)"})
  void testConvertWithSet_givenSerializerFactoryCreateSerializerReturnNull()
      throws JsonMappingException {
    // Arrange
    SerializerFactory f = mock(SerializerFactory.class);
    when(f.createSerializer(Mockito.<SerializerProvider>any(), Mockito.<JavaType>any()))
        .thenReturn(null);
    PlaceholderForType baseType = new PlaceholderForType(1);
    TypeFactory typeFactory = TypeFactory.defaultInstance();

    BasicPolymorphicTypeValidator.Builder builderResult = BasicPolymorphicTypeValidator.builder();
    Class<Object> baseTypeToDeny = Object.class;
    BasicPolymorphicTypeValidator ptv = builderResult.denyForExactBaseType(baseTypeToDeny).build();

    ClassNameIdResolver idRes = new ClassNameIdResolver(baseType, typeFactory, ptv);
    AsArrayTypeSerializer asArrayTypeSerializer = new AsArrayTypeSerializer(idRes, new Bogus());
    when(f.createTypeSerializer(Mockito.<SerializationConfig>any(), Mockito.<JavaType>any()))
        .thenReturn(asArrayTypeSerializer);

    Builder builderResult2 = JsonMapper.builder();
    builderResult2.serializerFactory(f);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult2.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult2.findAndAddModules().build();
    SetToStringConverter setToStringConverter = new SetToStringConverter(objectMapper);

    // Act and Assert
    assertThrows(RuntimeException.class, () -> setToStringConverter.convert(new HashSet<>()));
    verify(f).createSerializer(isA(SerializerProvider.class), isA(JavaType.class));
    verify(f).createTypeSerializer(isA(SerializationConfig.class), isA(JavaType.class));
  }

  /**
   * Test {@link SetToStringConverter#convert(Set)} with {@code Set}.
   *
   * <ul>
   *   <li>Given two.
   *   <li>When {@link HashSet#HashSet()} add two.
   *   <li>Then return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link SetToStringConverter#convert(Set)}
   */
  @Test
  @DisplayName("Test convert(Set) with 'Set'; given two; when HashSet() add two; then return '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String SetToStringConverter.convert(Set)"})
  void testConvertWithSet_givenTwo_whenHashSetAddTwo_thenReturn42() throws JsonProcessingException {
    // Arrange
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");

    HashSet<Object> source = new HashSet<>();
    source.add("42");
    source.add(2);

    // Act
    String actualConvertResult = setToStringConverter.convert(source);

    // Assert
    verify(objectMapper).writeValueAsString(isA(Object.class));
    assertEquals("42", actualConvertResult);
  }

  /**
   * Test {@link SetToStringConverter#convert(Set)} with {@code Set}.
   *
   * <ul>
   *   <li>Given two.
   *   <li>When {@link HashSet#HashSet()} add two.
   *   <li>Then return {@code [2,"42"]}.
   * </ul>
   *
   * <p>Method under test: {@link SetToStringConverter#convert(Set)}
   */
  @Test
  @DisplayName(
      "Test convert(Set) with 'Set'; given two; when HashSet() add two; then return '[2,\"42\"]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String SetToStringConverter.convert(Set)"})
  void testConvertWithSet_givenTwo_whenHashSetAddTwo_thenReturn242() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    SetToStringConverter setToStringConverter = new SetToStringConverter(objectMapper);

    HashSet<Object> source = new HashSet<>();
    source.add(2);
    source.add("42");

    // Act and Assert
    assertEquals("[2,\"42\"]", setToStringConverter.convert(source));
  }

  /**
   * Test {@link SetToStringConverter#convert(Set)} with {@code Set}.
   *
   * <ul>
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link SetToStringConverter#convert(Set)}
   */
  @Test
  @DisplayName("Test convert(Set) with 'Set'; then return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String SetToStringConverter.convert(Set)"})
  void testConvertWithSet_thenReturnEmptyString() throws IOException {
    // Arrange
    Default resultDefault = mock(Default.class);
    doNothing()
        .when(resultDefault)
        .serializeWithType(
            Mockito.<Object>any(),
            Mockito.<JsonGenerator>any(),
            Mockito.<SerializerProvider>any(),
            Mockito.<TypeSerializer>any());

    SerializerFactory f = mock(SerializerFactory.class);
    when(f.createSerializer(Mockito.<SerializerProvider>any(), Mockito.<JavaType>any()))
        .thenReturn(resultDefault);
    PlaceholderForType baseType = new PlaceholderForType(1);
    TypeFactory typeFactory = TypeFactory.defaultInstance();

    BasicPolymorphicTypeValidator.Builder builderResult = BasicPolymorphicTypeValidator.builder();
    Class<Object> baseTypeToDeny = Object.class;
    BasicPolymorphicTypeValidator ptv = builderResult.denyForExactBaseType(baseTypeToDeny).build();

    ClassNameIdResolver idRes = new ClassNameIdResolver(baseType, typeFactory, ptv);
    AsArrayTypeSerializer asArrayTypeSerializer = new AsArrayTypeSerializer(idRes, new Bogus());
    when(f.createTypeSerializer(Mockito.<SerializationConfig>any(), Mockito.<JavaType>any()))
        .thenReturn(asArrayTypeSerializer);

    Builder builderResult2 = JsonMapper.builder();
    builderResult2.serializerFactory(f);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult2.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult2.findAndAddModules().build();
    SetToStringConverter setToStringConverter = new SetToStringConverter(objectMapper);

    // Act
    String actualConvertResult = setToStringConverter.convert(new HashSet<>());

    // Assert
    verify(resultDefault)
        .serializeWithType(
            isA(Object.class),
            isA(JsonGenerator.class),
            isA(SerializerProvider.class),
            isA(TypeSerializer.class));
    verify(f).createSerializer(isA(SerializerProvider.class), isA(JavaType.class));
    verify(f).createTypeSerializer(isA(SerializationConfig.class), isA(JavaType.class));
    assertEquals("", actualConvertResult);
  }

  /**
   * Test {@link SetToStringConverter#convert(Set)} with {@code Set}.
   *
   * <ul>
   *   <li>Then return {@code []}.
   * </ul>
   *
   * <p>Method under test: {@link SetToStringConverter#convert(Set)}
   */
  @Test
  @DisplayName("Test convert(Set) with 'Set'; then return '[]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String SetToStringConverter.convert(Set)"})
  void testConvertWithSet_thenReturnLeftSquareBracketRightSquareBracket() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    SetToStringConverter setToStringConverter = new SetToStringConverter(objectMapper);

    // Act and Assert
    assertEquals("[]", setToStringConverter.convert(new HashSet<>()));
  }

  /**
   * Test {@link SetToStringConverter#convert(Set)} with {@code Set}.
   *
   * <ul>
   *   <li>Then return {@code []}.
   * </ul>
   *
   * <p>Method under test: {@link SetToStringConverter#convert(Set)}
   */
  @Test
  @DisplayName("Test convert(Set) with 'Set'; then return '[]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String SetToStringConverter.convert(Set)"})
  void testConvertWithSet_thenReturnLeftSquareBracketRightSquareBracket2() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    SetToStringConverter setToStringConverter = new SetToStringConverter(objectMapper);

    // Act and Assert
    assertEquals("[]", setToStringConverter.convert(new HashSet<>()));
  }
}
