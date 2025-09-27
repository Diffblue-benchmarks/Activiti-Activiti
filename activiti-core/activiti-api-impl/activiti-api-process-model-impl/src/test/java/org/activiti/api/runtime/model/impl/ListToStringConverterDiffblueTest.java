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
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.impl.AsArrayTypeSerializer;
import com.fasterxml.jackson.databind.jsontype.impl.AsDeductionTypeSerializer;
import com.fasterxml.jackson.databind.jsontype.impl.AsExternalTypeSerializer;
import com.fasterxml.jackson.databind.jsontype.impl.AsWrapperTypeSerializer;
import com.fasterxml.jackson.databind.jsontype.impl.ClassNameIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import com.fasterxml.jackson.databind.type.PlaceholderForType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

@ContextConfiguration(classes = {ListToStringConverter.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class ListToStringConverterDiffblueTest {
  @Autowired private ListToStringConverter listToStringConverter;

  @MockBean private ObjectMapper objectMapper;

  /**
   * Test {@link ListToStringConverter#convert(List)} with {@code List}.
   *
   * <p>Method under test: {@link ListToStringConverter#convert(List)}
   */
  @Test
  @DisplayName("Test convert(List) with 'List'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ListToStringConverter.convert(List)"})
  void testConvertWithList() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new DefaultTypeResolverBuilder(DefaultTyping.JAVA_LANG_OBJECT));
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    ListToStringConverter listToStringConverter = new ListToStringConverter(objectMapper);

    // Act and Assert
    assertThrows(RuntimeException.class, () -> listToStringConverter.convert(new ArrayList<>()));
  }

  /**
   * Test {@link ListToStringConverter#convert(List)} with {@code List}.
   *
   * <p>Method under test: {@link ListToStringConverter#convert(List)}
   */
  @Test
  @DisplayName("Test convert(List) with 'List'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ListToStringConverter.convert(List)"})
  void testConvertWithList2() {
    // Arrange
    StdTypeResolverBuilder typer = mock(StdTypeResolverBuilder.class);
    PlaceholderForType baseType = new PlaceholderForType(1);
    TypeFactory typeFactory = TypeFactory.defaultInstance();

    BasicPolymorphicTypeValidator.Builder builderResult = BasicPolymorphicTypeValidator.builder();
    Class<Object> baseTypeToDeny = Object.class;
    BasicPolymorphicTypeValidator ptv = builderResult.denyForExactBaseType(baseTypeToDeny).build();

    ClassNameIdResolver idRes = new ClassNameIdResolver(baseType, typeFactory, ptv);
    AsExternalTypeSerializer asExternalTypeSerializer =
        new AsExternalTypeSerializer(idRes, new Bogus(), "Prop Name");
    when(typer.buildTypeSerializer(
            Mockito.<SerializationConfig>any(),
            Mockito.<JavaType>any(),
            Mockito.<Collection<NamedType>>any()))
        .thenReturn(asExternalTypeSerializer);

    Builder builderResult2 = JsonMapper.builder();
    builderResult2.setDefaultTyping(typer);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult2.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult2.findAndAddModules().build();
    ListToStringConverter listToStringConverter = new ListToStringConverter(objectMapper);

    // Act and Assert
    assertThrows(RuntimeException.class, () -> listToStringConverter.convert(new ArrayList<>()));
    verify(typer, atLeast(1))
        .buildTypeSerializer(isA(SerializationConfig.class), Mockito.<JavaType>any(), isNull());
  }

  /**
   * Test {@link ListToStringConverter#convert(List)} with {@code List}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.
   *   <li>Then return {@code ["42"]}.
   * </ul>
   *
   * <p>Method under test: {@link ListToStringConverter#convert(List)}
   */
  @Test
  @DisplayName(
      "Test convert(List) with 'List'; given '42'; when ArrayList() add '42'; then return '[\"42\"]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ListToStringConverter.convert(List)"})
  void testConvertWithList_given42_whenArrayListAdd42_thenReturn42() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    ListToStringConverter listToStringConverter = new ListToStringConverter(objectMapper);

    ArrayList<Object> source = new ArrayList<>();
    source.add("42");

    // Act and Assert
    assertEquals("[\"42\"]", listToStringConverter.convert(source));
  }

  /**
   * Test {@link ListToStringConverter#convert(List)} with {@code List}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.
   *   <li>Then return {@code ["42","42"]}.
   * </ul>
   *
   * <p>Method under test: {@link ListToStringConverter#convert(List)}
   */
  @Test
  @DisplayName(
      "Test convert(List) with 'List'; given '42'; when ArrayList() add '42'; then return '[\"42\",\"42\"]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ListToStringConverter.convert(List)"})
  void testConvertWithList_given42_whenArrayListAdd42_thenReturn4242() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    ListToStringConverter listToStringConverter = new ListToStringConverter(objectMapper);

    ArrayList<Object> source = new ArrayList<>();
    source.add("42");
    source.add("42");

    // Act and Assert
    assertEquals("[\"42\",\"42\"]", listToStringConverter.convert(source));
  }

  /**
   * Test {@link ListToStringConverter#convert(List)} with {@code List}.
   *
   * <ul>
   *   <li>Given builder defaultLeniency {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ListToStringConverter#convert(List)}
   */
  @Test
  @DisplayName("Test convert(List) with 'List'; given builder defaultLeniency 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ListToStringConverter.convert(List)"})
  void testConvertWithList_givenBuilderDefaultLeniencyTrue() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.defaultLeniency(true);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    ListToStringConverter listToStringConverter = new ListToStringConverter(objectMapper);

    // Act and Assert
    assertEquals("[]", listToStringConverter.convert(new ArrayList<>()));
  }

  /**
   * Test {@link ListToStringConverter#convert(List)} with {@code List}.
   *
   * <ul>
   *   <li>Given builder DefaultTyping is {@link StdTypeResolverBuilder#StdTypeResolverBuilder()}.
   * </ul>
   *
   * <p>Method under test: {@link ListToStringConverter#convert(List)}
   */
  @Test
  @DisplayName(
      "Test convert(List) with 'List'; given builder DefaultTyping is StdTypeResolverBuilder()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ListToStringConverter.convert(List)"})
  void testConvertWithList_givenBuilderDefaultTypingIsStdTypeResolverBuilder() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new StdTypeResolverBuilder());
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    ListToStringConverter listToStringConverter = new ListToStringConverter(objectMapper);

    // Act and Assert
    assertThrows(RuntimeException.class, () -> listToStringConverter.convert(new ArrayList<>()));
  }

  /**
   * Test {@link ListToStringConverter#convert(List)} with {@code List}.
   *
   * <ul>
   *   <li>Given {@link ClassNameIdResolver} {@link ClassNameIdResolver#idFromValue(Object)} return
   *       {@code 42}.
   *   <li>Then return {@code ["42",[]]}.
   * </ul>
   *
   * <p>Method under test: {@link ListToStringConverter#convert(List)}
   */
  @Test
  @DisplayName(
      "Test convert(List) with 'List'; given ClassNameIdResolver idFromValue(Object) return '42'; then return '[\"42\",[]]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ListToStringConverter.convert(List)"})
  void testConvertWithList_givenClassNameIdResolverIdFromValueReturn42_thenReturn42() {
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
    ListToStringConverter listToStringConverter = new ListToStringConverter(objectMapper);

    // Act
    String actualConvertResult = listToStringConverter.convert(new ArrayList<>());

    // Assert
    verify(idRes).idFromValue(isA(Object.class));
    verify(typer, atLeast(1))
        .buildTypeSerializer(isA(SerializationConfig.class), Mockito.<JavaType>any(), isNull());
    assertEquals("[\"42\",[]]", actualConvertResult);
  }

  /**
   * Test {@link ListToStringConverter#convert(List)} with {@code List}.
   *
   * <ul>
   *   <li>Given {@link ClassNameIdResolver} {@link ClassNameIdResolver#idFromValue(Object)} throw
   *       {@link RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link ListToStringConverter#convert(List)}
   */
  @Test
  @DisplayName(
      "Test convert(List) with 'List'; given ClassNameIdResolver idFromValue(Object) throw RuntimeException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ListToStringConverter.convert(List)"})
  void testConvertWithList_givenClassNameIdResolverIdFromValueThrowRuntimeException() {
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
    ListToStringConverter listToStringConverter = new ListToStringConverter(objectMapper);

    // Act and Assert
    assertThrows(RuntimeException.class, () -> listToStringConverter.convert(new ArrayList<>()));
    verify(idRes).idFromValue(isA(Object.class));
    verify(typer, atLeast(1))
        .buildTypeSerializer(isA(SerializationConfig.class), Mockito.<JavaType>any(), isNull());
  }

  /**
   * Test {@link ListToStringConverter#convert(List)} with {@code List}.
   *
   * <ul>
   *   <li>Given {@link ObjectMapper} {@link ObjectMapper#writeValueAsString(Object)} return {@code
   *       42}.
   *   <li>Then return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ListToStringConverter#convert(List)}
   */
  @Test
  @DisplayName(
      "Test convert(List) with 'List'; given ObjectMapper writeValueAsString(Object) return '42'; then return '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ListToStringConverter.convert(List)"})
  void testConvertWithList_givenObjectMapperWriteValueAsStringReturn42_thenReturn42()
      throws JsonProcessingException {
    // Arrange
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");

    // Act
    String actualConvertResult = listToStringConverter.convert(new ArrayList<>());

    // Assert
    verify(objectMapper).writeValueAsString(isA(Object.class));
    assertEquals("42", actualConvertResult);
  }

  /**
   * Test {@link ListToStringConverter#convert(List)} with {@code List}.
   *
   * <ul>
   *   <li>Given {@link ObjectMapper} {@link ObjectMapper#writeValueAsString(Object)} return {@code
   *       42}.
   *   <li>Then return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ListToStringConverter#convert(List)}
   */
  @Test
  @DisplayName(
      "Test convert(List) with 'List'; given ObjectMapper writeValueAsString(Object) return '42'; then return '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ListToStringConverter.convert(List)"})
  void testConvertWithList_givenObjectMapperWriteValueAsStringReturn42_thenReturn422()
      throws JsonProcessingException {
    // Arrange
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");

    ArrayList<Object> source = new ArrayList<>();
    source.add("42");

    // Act
    String actualConvertResult = listToStringConverter.convert(source);

    // Assert
    verify(objectMapper).writeValueAsString(isA(Object.class));
    assertEquals("42", actualConvertResult);
  }

  /**
   * Test {@link ListToStringConverter#convert(List)} with {@code List}.
   *
   * <ul>
   *   <li>Given {@link ObjectMapper} {@link ObjectMapper#writeValueAsString(Object)} return {@code
   *       42}.
   *   <li>Then return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link ListToStringConverter#convert(List)}
   */
  @Test
  @DisplayName(
      "Test convert(List) with 'List'; given ObjectMapper writeValueAsString(Object) return '42'; then return '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ListToStringConverter.convert(List)"})
  void testConvertWithList_givenObjectMapperWriteValueAsStringReturn42_thenReturn423()
      throws JsonProcessingException {
    // Arrange
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");

    ArrayList<Object> source = new ArrayList<>();
    source.add("42");
    source.add("42");

    // Act
    String actualConvertResult = listToStringConverter.convert(source);

    // Assert
    verify(objectMapper).writeValueAsString(isA(Object.class));
    assertEquals("42", actualConvertResult);
  }

  /**
   * Test {@link ListToStringConverter#convert(List)} with {@code List}.
   *
   * <ul>
   *   <li>Given {@link ObjectMapper} {@link ObjectMapper#writeValueAsString(Object)} throw {@link
   *       RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link ListToStringConverter#convert(List)}
   */
  @Test
  @DisplayName(
      "Test convert(List) with 'List'; given ObjectMapper writeValueAsString(Object) throw RuntimeException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ListToStringConverter.convert(List)"})
  void testConvertWithList_givenObjectMapperWriteValueAsStringThrowRuntimeException()
      throws JsonProcessingException {
    // Arrange
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenThrow(new RuntimeException());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> listToStringConverter.convert(new ArrayList<>()));
    verify(objectMapper).writeValueAsString(isA(Object.class));
  }

  /**
   * Test {@link ListToStringConverter#convert(List)} with {@code List}.
   *
   * <ul>
   *   <li>Then return {@code ["ArrayList",[]]}.
   * </ul>
   *
   * <p>Method under test: {@link ListToStringConverter#convert(List)}
   */
  @Test
  @DisplayName("Test convert(List) with 'List'; then return '[\"java.util.ArrayList\",[]]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ListToStringConverter.convert(List)"})
  void testConvertWithList_thenReturnJavaUtilArrayList() {
    // Arrange
    StdTypeResolverBuilder typer = mock(StdTypeResolverBuilder.class);
    PlaceholderForType baseType = new PlaceholderForType(1);
    TypeFactory typeFactory = TypeFactory.defaultInstance();

    BasicPolymorphicTypeValidator.Builder builderResult = BasicPolymorphicTypeValidator.builder();
    Class<Object> baseTypeToDeny = Object.class;
    BasicPolymorphicTypeValidator ptv = builderResult.denyForExactBaseType(baseTypeToDeny).build();

    ClassNameIdResolver idRes = new ClassNameIdResolver(baseType, typeFactory, ptv);
    AsArrayTypeSerializer asArrayTypeSerializer = new AsArrayTypeSerializer(idRes, new Bogus());
    when(typer.buildTypeSerializer(
            Mockito.<SerializationConfig>any(),
            Mockito.<JavaType>any(),
            Mockito.<Collection<NamedType>>any()))
        .thenReturn(asArrayTypeSerializer);

    Builder builderResult2 = JsonMapper.builder();
    builderResult2.setDefaultTyping(typer);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult2.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult2.findAndAddModules().build();
    ListToStringConverter listToStringConverter = new ListToStringConverter(objectMapper);

    // Act
    String actualConvertResult = listToStringConverter.convert(new ArrayList<>());

    // Assert
    verify(typer, atLeast(1))
        .buildTypeSerializer(isA(SerializationConfig.class), Mockito.<JavaType>any(), isNull());
    assertEquals("[\"java.util.ArrayList\",[]]", actualConvertResult);
  }

  /**
   * Test {@link ListToStringConverter#convert(List)} with {@code List}.
   *
   * <ul>
   *   <li>Then return {@code {"ArrayList":[]}}.
   * </ul>
   *
   * <p>Method under test: {@link ListToStringConverter#convert(List)}
   */
  @Test
  @DisplayName("Test convert(List) with 'List'; then return '{\"java.util.ArrayList\":[]}'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ListToStringConverter.convert(List)"})
  void testConvertWithList_thenReturnJavaUtilArrayList2() {
    // Arrange
    StdTypeResolverBuilder typer = mock(StdTypeResolverBuilder.class);
    PlaceholderForType baseType = new PlaceholderForType(1);
    TypeFactory typeFactory = TypeFactory.defaultInstance();

    BasicPolymorphicTypeValidator.Builder builderResult = BasicPolymorphicTypeValidator.builder();
    Class<Object> baseTypeToDeny = Object.class;
    BasicPolymorphicTypeValidator ptv = builderResult.denyForExactBaseType(baseTypeToDeny).build();

    ClassNameIdResolver idRes = new ClassNameIdResolver(baseType, typeFactory, ptv);
    AsWrapperTypeSerializer asWrapperTypeSerializer =
        new AsWrapperTypeSerializer(idRes, new Bogus());
    when(typer.buildTypeSerializer(
            Mockito.<SerializationConfig>any(),
            Mockito.<JavaType>any(),
            Mockito.<Collection<NamedType>>any()))
        .thenReturn(asWrapperTypeSerializer);

    Builder builderResult2 = JsonMapper.builder();
    builderResult2.setDefaultTyping(typer);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult2.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult2.findAndAddModules().build();
    ListToStringConverter listToStringConverter = new ListToStringConverter(objectMapper);

    // Act
    String actualConvertResult = listToStringConverter.convert(new ArrayList<>());

    // Assert
    verify(typer, atLeast(1))
        .buildTypeSerializer(isA(SerializationConfig.class), Mockito.<JavaType>any(), isNull());
    assertEquals("{\"java.util.ArrayList\":[]}", actualConvertResult);
  }

  /**
   * Test {@link ListToStringConverter#convert(List)} with {@code List}.
   *
   * <ul>
   *   <li>Then return {@code []}.
   * </ul>
   *
   * <p>Method under test: {@link ListToStringConverter#convert(List)}
   */
  @Test
  @DisplayName("Test convert(List) with 'List'; then return '[]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ListToStringConverter.convert(List)"})
  void testConvertWithList_thenReturnLeftSquareBracketRightSquareBracket() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    ListToStringConverter listToStringConverter = new ListToStringConverter(objectMapper);

    // Act and Assert
    assertEquals("[]", listToStringConverter.convert(new ArrayList<>()));
  }

  /**
   * Test {@link ListToStringConverter#convert(List)} with {@code List}.
   *
   * <ul>
   *   <li>Then return {@code []}.
   * </ul>
   *
   * <p>Method under test: {@link ListToStringConverter#convert(List)}
   */
  @Test
  @DisplayName("Test convert(List) with 'List'; then return '[]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ListToStringConverter.convert(List)"})
  void testConvertWithList_thenReturnLeftSquareBracketRightSquareBracket2() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    ListToStringConverter listToStringConverter = new ListToStringConverter(objectMapper);

    // Act and Assert
    assertEquals("[]", listToStringConverter.convert(new ArrayList<>()));
  }

  /**
   * Test {@link ListToStringConverter#convert(List)} with {@code List}.
   *
   * <ul>
   *   <li>Then return {@code ["null",[]]}.
   * </ul>
   *
   * <p>Method under test: {@link ListToStringConverter#convert(List)}
   */
  @Test
  @DisplayName("Test convert(List) with 'List'; then return '[\"null\",[]]'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ListToStringConverter.convert(List)"})
  void testConvertWithList_thenReturnNull() {
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
    ListToStringConverter listToStringConverter = new ListToStringConverter(objectMapper);

    // Act
    String actualConvertResult = listToStringConverter.convert(new ArrayList<>());

    // Assert
    verify(typer, atLeast(1))
        .buildTypeSerializer(isA(SerializationConfig.class), Mockito.<JavaType>any(), isNull());
    assertEquals("[\"null\",[]]", actualConvertResult);
  }
}
