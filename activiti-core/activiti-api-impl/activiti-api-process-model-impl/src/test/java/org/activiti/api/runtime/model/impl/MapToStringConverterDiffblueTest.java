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
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.BeanProperty.Bogus;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTypeResolverBuilder;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.json.JsonMapper.Builder;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.impl.AsArrayTypeSerializer;
import com.fasterxml.jackson.databind.jsontype.impl.AsDeductionTypeSerializer;
import com.fasterxml.jackson.databind.jsontype.impl.ClassNameIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
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

@ContextConfiguration(classes = {MapToStringConverter.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class MapToStringConverterDiffblueTest {
  @Autowired private MapToStringConverter mapToStringConverter;

  @MockBean private ObjectMapper objectMapper;

  /**
   * Test {@link MapToStringConverter#convert(Map)} with {@code Map}.
   *
   * <p>Method under test: {@link MapToStringConverter#convert(Map)}
   */
  @Test
  @DisplayName("Test convert(Map) with 'Map'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String MapToStringConverter.convert(Map)"})
  void testConvertWithMap() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new DefaultTypeResolverBuilder(DefaultTyping.JAVA_LANG_OBJECT));
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    MapToStringConverter mapToStringConverter = new MapToStringConverter(objectMapper);

    // Act and Assert
    assertThrows(RuntimeException.class, () -> mapToStringConverter.convert(new HashMap<>()));
  }

  /**
   * Test {@link MapToStringConverter#convert(Map)} with {@code Map}.
   *
   * <ul>
   *   <li>Given builder defaultLeniency {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link MapToStringConverter#convert(Map)}
   */
  @Test
  @DisplayName("Test convert(Map) with 'Map'; given builder defaultLeniency 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String MapToStringConverter.convert(Map)"})
  void testConvertWithMap_givenBuilderDefaultLeniencyTrue() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.defaultLeniency(true);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    MapToStringConverter mapToStringConverter = new MapToStringConverter(objectMapper);

    // Act and Assert
    assertEquals("{}", mapToStringConverter.convert(new HashMap<>()));
  }

  /**
   * Test {@link MapToStringConverter#convert(Map)} with {@code Map}.
   *
   * <ul>
   *   <li>Given builder DefaultTyping is {@link StdTypeResolverBuilder#StdTypeResolverBuilder()}.
   * </ul>
   *
   * <p>Method under test: {@link MapToStringConverter#convert(Map)}
   */
  @Test
  @DisplayName(
      "Test convert(Map) with 'Map'; given builder DefaultTyping is StdTypeResolverBuilder()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String MapToStringConverter.convert(Map)"})
  void testConvertWithMap_givenBuilderDefaultTypingIsStdTypeResolverBuilder() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new StdTypeResolverBuilder());
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    MapToStringConverter mapToStringConverter = new MapToStringConverter(objectMapper);

    // Act and Assert
    assertThrows(RuntimeException.class, () -> mapToStringConverter.convert(new HashMap<>()));
  }

  /**
   * Test {@link MapToStringConverter#convert(Map)} with {@code Map}.
   *
   * <ul>
   *   <li>Given {@link ClassNameIdResolver} {@link ClassNameIdResolver#idFromValue(Object)} return
   *       {@code 42}.
   *   <li>Then return {@code ["42",{}]}.
   * </ul>
   *
   * <p>Method under test: {@link MapToStringConverter#convert(Map)}
   */
  @Test
  @DisplayName(
      "Test convert(Map) with 'Map'; given ClassNameIdResolver idFromValue(Object) return '42'; then return '[\"42\",{}]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String MapToStringConverter.convert(Map)"})
  void testConvertWithMap_givenClassNameIdResolverIdFromValueReturn42_thenReturn42() {
    // Arrange
    ClassNameIdResolver idRes = mock(ClassNameIdResolver.class);
    when(idRes.idFromValue(Mockito.<Object>any())).thenReturn("42");
    AsArrayTypeSerializer asArrayTypeSerializer = new AsArrayTypeSerializer(idRes, new Bogus());

    StdTypeResolverBuilder typer = mock(StdTypeResolverBuilder.class);
    when(typer.buildTypeSerializer(
            Mockito.<SerializationConfig>any(),
            Mockito.<JavaType>any(),
            Mockito.<Collection<NamedType>>any()))
        .thenReturn(asArrayTypeSerializer);

    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(typer);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    MapToStringConverter mapToStringConverter = new MapToStringConverter(objectMapper);

    // Act
    String actualConvertResult = mapToStringConverter.convert(new HashMap<>());

    // Assert
    verify(idRes).idFromValue(isA(Object.class));
    verify(typer, atLeast(1))
        .buildTypeSerializer(isA(SerializationConfig.class), Mockito.<JavaType>any(), isNull());
    assertEquals("[\"42\",{}]", actualConvertResult);
  }

  /**
   * Test {@link MapToStringConverter#convert(Map)} with {@code Map}.
   *
   * <ul>
   *   <li>Given {@link ClassNameIdResolver} {@link ClassNameIdResolver#idFromValue(Object)} throw
   *       {@link RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link MapToStringConverter#convert(Map)}
   */
  @Test
  @DisplayName(
      "Test convert(Map) with 'Map'; given ClassNameIdResolver idFromValue(Object) throw RuntimeException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String MapToStringConverter.convert(Map)"})
  void testConvertWithMap_givenClassNameIdResolverIdFromValueThrowRuntimeException() {
    // Arrange
    ClassNameIdResolver idRes = mock(ClassNameIdResolver.class);
    when(idRes.idFromValue(Mockito.<Object>any())).thenThrow(new RuntimeException());
    AsArrayTypeSerializer asArrayTypeSerializer = new AsArrayTypeSerializer(idRes, new Bogus());

    StdTypeResolverBuilder typer = mock(StdTypeResolverBuilder.class);
    when(typer.buildTypeSerializer(
            Mockito.<SerializationConfig>any(),
            Mockito.<JavaType>any(),
            Mockito.<Collection<NamedType>>any()))
        .thenReturn(asArrayTypeSerializer);

    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(typer);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    MapToStringConverter mapToStringConverter = new MapToStringConverter(objectMapper);

    // Act and Assert
    assertThrows(RuntimeException.class, () -> mapToStringConverter.convert(new HashMap<>()));
    verify(idRes).idFromValue(isA(Object.class));
    verify(typer, atLeast(1))
        .buildTypeSerializer(isA(SerializationConfig.class), Mockito.<JavaType>any(), isNull());
  }

  /**
   * Test {@link MapToStringConverter#convert(Map)} with {@code Map}.
   *
   * <ul>
   *   <li>Given {@code Key}.
   *   <li>When {@link HashMap#HashMap()} {@code Key} is {@code Value}.
   *   <li>Then return {@code {"Key":"Value"}}.
   * </ul>
   *
   * <p>Method under test: {@link MapToStringConverter#convert(Map)}
   */
  @Test
  @DisplayName(
      "Test convert(Map) with 'Map'; given 'Key'; when HashMap() 'Key' is 'Value'; then return '{\"Key\":\"Value\"}'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String MapToStringConverter.convert(Map)"})
  void testConvertWithMap_givenKey_whenHashMapKeyIsValue_thenReturnKeyValue() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    MapToStringConverter mapToStringConverter = new MapToStringConverter(objectMapper);

    HashMap<String, Object> source = new HashMap<>();
    source.put("Key", "Value");

    // Act and Assert
    assertEquals("{\"Key\":\"Value\"}", mapToStringConverter.convert(source));
  }

  /**
   * Test {@link MapToStringConverter#convert(Map)} with {@code Map}.
   *
   * <ul>
   *   <li>Given {@link ObjectMapper} {@link ObjectMapper#writeValueAsString(Object)} return {@code
   *       42}.
   *   <li>Then return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link MapToStringConverter#convert(Map)}
   */
  @Test
  @DisplayName(
      "Test convert(Map) with 'Map'; given ObjectMapper writeValueAsString(Object) return '42'; then return '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String MapToStringConverter.convert(Map)"})
  void testConvertWithMap_givenObjectMapperWriteValueAsStringReturn42_thenReturn42()
      throws JsonProcessingException {
    // Arrange
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");

    // Act
    String actualConvertResult = mapToStringConverter.convert(new HashMap<>());

    // Assert
    verify(objectMapper).writeValueAsString(isA(Object.class));
    assertEquals("42", actualConvertResult);
  }

  /**
   * Test {@link MapToStringConverter#convert(Map)} with {@code Map}.
   *
   * <ul>
   *   <li>Given {@link ObjectMapper} {@link ObjectMapper#writeValueAsString(Object)} throw {@link
   *       RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link MapToStringConverter#convert(Map)}
   */
  @Test
  @DisplayName(
      "Test convert(Map) with 'Map'; given ObjectMapper writeValueAsString(Object) throw RuntimeException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String MapToStringConverter.convert(Map)"})
  void testConvertWithMap_givenObjectMapperWriteValueAsStringThrowRuntimeException()
      throws JsonProcessingException {
    // Arrange
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenThrow(new RuntimeException());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> mapToStringConverter.convert(new HashMap<>()));
    verify(objectMapper).writeValueAsString(isA(Object.class));
  }

  /**
   * Test {@link MapToStringConverter#convert(Map)} with {@code Map}.
   *
   * <ul>
   *   <li>Given {@link StdTypeResolverBuilder} {@link
   *       StdTypeResolverBuilder#buildTypeSerializer(SerializationConfig, JavaType, Collection)}
   *       return instance.
   * </ul>
   *
   * <p>Method under test: {@link MapToStringConverter#convert(Map)}
   */
  @Test
  @DisplayName(
      "Test convert(Map) with 'Map'; given StdTypeResolverBuilder buildTypeSerializer(SerializationConfig, JavaType, Collection) return instance")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String MapToStringConverter.convert(Map)"})
  void testConvertWithMap_givenStdTypeResolverBuilderBuildTypeSerializerReturnInstance() {
    // Arrange
    StdTypeResolverBuilder typer = mock(StdTypeResolverBuilder.class);
    when(typer.buildTypeSerializer(
            Mockito.<SerializationConfig>any(),
            Mockito.<JavaType>any(),
            Mockito.<Collection<NamedType>>any()))
        .thenReturn(AsDeductionTypeSerializer.instance());

    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(typer);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    MapToStringConverter mapToStringConverter = new MapToStringConverter(objectMapper);

    // Act
    String actualConvertResult = mapToStringConverter.convert(new HashMap<>());

    // Assert
    verify(typer, atLeast(1))
        .buildTypeSerializer(isA(SerializationConfig.class), Mockito.<JavaType>any(), isNull());
    assertEquals("{}", actualConvertResult);
  }

  /**
   * Test {@link MapToStringConverter#convert(Map)} with {@code Map}.
   *
   * <ul>
   *   <li>Then return {@code {}}.
   * </ul>
   *
   * <p>Method under test: {@link MapToStringConverter#convert(Map)}
   */
  @Test
  @DisplayName("Test convert(Map) with 'Map'; then return '{}'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String MapToStringConverter.convert(Map)"})
  void testConvertWithMap_thenReturnLeftCurlyBracketRightCurlyBracket() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    MapToStringConverter mapToStringConverter = new MapToStringConverter(objectMapper);

    // Act and Assert
    assertEquals("{}", mapToStringConverter.convert(new HashMap<>()));
  }

  /**
   * Test {@link MapToStringConverter#convert(Map)} with {@code Map}.
   *
   * <ul>
   *   <li>Then return {@code {}}.
   * </ul>
   *
   * <p>Method under test: {@link MapToStringConverter#convert(Map)}
   */
  @Test
  @DisplayName("Test convert(Map) with 'Map'; then return '{}'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String MapToStringConverter.convert(Map)"})
  void testConvertWithMap_thenReturnLeftCurlyBracketRightCurlyBracket2() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    MapToStringConverter mapToStringConverter = new MapToStringConverter(objectMapper);

    // Act and Assert
    assertEquals("{}", mapToStringConverter.convert(new HashMap<>()));
  }
}
