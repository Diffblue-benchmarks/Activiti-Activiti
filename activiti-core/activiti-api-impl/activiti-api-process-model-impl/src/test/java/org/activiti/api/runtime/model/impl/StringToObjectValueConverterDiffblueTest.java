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

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.BeanProperty.Bogus;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTypeResolverBuilder;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.cfg.DefaultCacheProvider;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.json.JsonMapper.Builder;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.impl.AsArrayTypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.impl.AsWrapperTypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.impl.ClassNameIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.PlaceholderForType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import java.util.Collection;
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

@ContextConfiguration(classes = {StringToObjectValueConverter.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class StringToObjectValueConverterDiffblueTest {
  @MockBean private ObjectMapper objectMapper;

  @Autowired private StringToObjectValueConverter stringToObjectValueConverter;

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   *
   * <p>Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString() throws JsonProcessingException {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.annotationIntrospector(new JacksonAnnotationIntrospector());
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    StringToObjectValueConverter stringToObjectValueConverter =
        new StringToObjectValueConverter(objectMapper);

    JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertNull(
        stringToObjectValueConverter
            .convert(jsonMapper.writeValueAsString(new ObjectValue()))
            .getObject());
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   *
   * <p>Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString2() throws JsonProcessingException {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.cacheProvider(
        DefaultCacheProvider.builder()
            .maxDeserializerCacheSize(3)
            .maxSerializerCacheSize(3)
            .maxTypeFactoryCacheSize(3)
            .build());
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    StringToObjectValueConverter stringToObjectValueConverter =
        new StringToObjectValueConverter(objectMapper);

    JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertNull(
        stringToObjectValueConverter
            .convert(jsonMapper.writeValueAsString(new ObjectValue()))
            .getObject());
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   *
   * <p>Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString3() throws JsonProcessingException {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new DefaultTypeResolverBuilder(DefaultTyping.JAVA_LANG_OBJECT));
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    StringToObjectValueConverter stringToObjectValueConverter =
        new StringToObjectValueConverter(objectMapper);

    JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () ->
            stringToObjectValueConverter.convert(jsonMapper.writeValueAsString(new ObjectValue())));
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   *
   * <p>Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString4() throws JsonProcessingException {
    // Arrange
    StdTypeResolverBuilder typer = mock(StdTypeResolverBuilder.class);
    PlaceholderForType bt = new PlaceholderForType(1);
    PlaceholderForType baseType = new PlaceholderForType(1);
    TypeFactory typeFactory = TypeFactory.defaultInstance();

    BasicPolymorphicTypeValidator.Builder builderResult = BasicPolymorphicTypeValidator.builder();
    Class<Object> baseTypeToDeny = Object.class;
    BasicPolymorphicTypeValidator ptv = builderResult.denyForExactBaseType(baseTypeToDeny).build();

    ClassNameIdResolver idRes = new ClassNameIdResolver(baseType, typeFactory, ptv);

    AsArrayTypeDeserializer src =
        new AsArrayTypeDeserializer(
            bt, idRes, "Type Property Name", true, new PlaceholderForType(1));
    AsArrayTypeDeserializer asArrayTypeDeserializer = new AsArrayTypeDeserializer(src, new Bogus());
    when(typer.buildTypeDeserializer(
            Mockito.<DeserializationConfig>any(),
            Mockito.<JavaType>any(),
            Mockito.<Collection<NamedType>>any()))
        .thenReturn(asArrayTypeDeserializer);
    Class<Object> forNameResult = Object.class;
    Mockito.<Class<?>>when(typer.getDefaultImpl()).thenReturn(forNameResult);

    Builder builderResult2 = JsonMapper.builder();
    builderResult2.setDefaultTyping(typer);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult2.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult2.findAndAddModules().build();
    StringToObjectValueConverter stringToObjectValueConverter =
        new StringToObjectValueConverter(objectMapper);

    JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () ->
            stringToObjectValueConverter.convert(jsonMapper.writeValueAsString(new ObjectValue())));
    verify(typer, atLeast(1))
        .buildTypeDeserializer(
            isA(DeserializationConfig.class),
            Mockito.<JavaType>any(),
            Mockito.<Collection<NamedType>>any());
    verify(typer, atLeast(1)).getDefaultImpl();
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   *
   * <p>Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString5() throws JsonProcessingException {
    // Arrange
    StdTypeResolverBuilder typer = mock(StdTypeResolverBuilder.class);
    PlaceholderForType bt = new PlaceholderForType(1);
    PlaceholderForType baseType = new PlaceholderForType(1);
    TypeFactory typeFactory = TypeFactory.defaultInstance();

    BasicPolymorphicTypeValidator.Builder builderResult = BasicPolymorphicTypeValidator.builder();
    Class<Object> baseTypeToDeny = Object.class;
    BasicPolymorphicTypeValidator ptv = builderResult.denyForExactBaseType(baseTypeToDeny).build();

    ClassNameIdResolver idRes = new ClassNameIdResolver(baseType, typeFactory, ptv);

    AsWrapperTypeDeserializer asWrapperTypeDeserializer =
        new AsWrapperTypeDeserializer(
            bt, idRes, "Type Property Name", true, new PlaceholderForType(1));
    when(typer.buildTypeDeserializer(
            Mockito.<DeserializationConfig>any(),
            Mockito.<JavaType>any(),
            Mockito.<Collection<NamedType>>any()))
        .thenReturn(asWrapperTypeDeserializer);
    Class<Object> forNameResult = Object.class;
    Mockito.<Class<?>>when(typer.getDefaultImpl()).thenReturn(forNameResult);

    Builder builderResult2 = JsonMapper.builder();
    builderResult2.setDefaultTyping(typer);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult2.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult2.findAndAddModules().build();
    StringToObjectValueConverter stringToObjectValueConverter =
        new StringToObjectValueConverter(objectMapper);

    JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () ->
            stringToObjectValueConverter.convert(jsonMapper.writeValueAsString(new ObjectValue())));
    verify(typer, atLeast(1))
        .buildTypeDeserializer(
            isA(DeserializationConfig.class),
            Mockito.<JavaType>any(),
            Mockito.<Collection<NamedType>>any());
    verify(typer, atLeast(1)).getDefaultImpl();
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   *
   * <p>Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString6() throws IOException {
    // Arrange
    JavaType javaType = mock(JavaType.class);
    when(javaType.isEnumType()).thenReturn(true);
    when(javaType.getContentType()).thenReturn(new PlaceholderForType(1));
    when(javaType.getKeyType()).thenReturn(new PlaceholderForType(1));
    when(javaType.isContainerType()).thenReturn(true);
    when(javaType.isAbstract()).thenReturn(true);
    when(javaType.isMapLikeType()).thenReturn(true);
    Class<Object> forNameResult = Object.class;
    Mockito.<Class<?>>when(javaType.getRawClass()).thenReturn(forNameResult);

    ClassNameIdResolver idRes = mock(ClassNameIdResolver.class);
    when(idRes.typeFromId(Mockito.<DatabindContext>any(), Mockito.<String>any()))
        .thenReturn(javaType);
    when(idRes.idFromBaseType()).thenReturn("jane.doe@example.org");
    PlaceholderForType bt = new PlaceholderForType(1);

    AsArrayTypeDeserializer src =
        new AsArrayTypeDeserializer(
            bt, idRes, "Type Property Name", true, new PlaceholderForType(1));
    AsArrayTypeDeserializer asArrayTypeDeserializer = new AsArrayTypeDeserializer(src, new Bogus());

    StdTypeResolverBuilder typer = mock(StdTypeResolverBuilder.class);
    when(typer.buildTypeDeserializer(
            Mockito.<DeserializationConfig>any(),
            Mockito.<JavaType>any(),
            Mockito.<Collection<NamedType>>any()))
        .thenReturn(asArrayTypeDeserializer);
    Class<Object> forNameResult2 = Object.class;
    Mockito.<Class<?>>when(typer.getDefaultImpl()).thenReturn(forNameResult2);

    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(typer);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    StringToObjectValueConverter stringToObjectValueConverter =
        new StringToObjectValueConverter(objectMapper);

    JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () ->
            stringToObjectValueConverter.convert(jsonMapper.writeValueAsString(new ObjectValue())));
    verify(javaType, atLeast(1)).getContentType();
    verify(javaType, atLeast(1)).getKeyType();
    verify(javaType, atLeast(1)).getRawClass();
    verify(javaType).isAbstract();
    verify(javaType, atLeast(1)).isContainerType();
    verify(javaType).isEnumType();
    verify(javaType, atLeast(1)).isMapLikeType();
    verify(idRes).typeFromId(isA(DatabindContext.class), eq("jane.doe@example.org"));
    verify(typer, atLeast(1))
        .buildTypeDeserializer(
            isA(DeserializationConfig.class),
            Mockito.<JavaType>any(),
            Mockito.<Collection<NamedType>>any());
    verify(typer, atLeast(1)).getDefaultImpl();
    verify(idRes).idFromBaseType();
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   *
   * <p>Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString7() throws JsonProcessingException {
    // Arrange
    StdTypeResolverBuilder typer = mock(StdTypeResolverBuilder.class);
    AsArrayTypeDeserializer src =
        new AsArrayTypeDeserializer(
            new PlaceholderForType(1),
            mock(ClassNameIdResolver.class),
            "Type Property Name",
            true,
            null);
    AsArrayTypeDeserializer asArrayTypeDeserializer = new AsArrayTypeDeserializer(src, new Bogus());
    when(typer.buildTypeDeserializer(
            Mockito.<DeserializationConfig>any(),
            Mockito.<JavaType>any(),
            Mockito.<Collection<NamedType>>any()))
        .thenReturn(asArrayTypeDeserializer);
    Class<Object> forNameResult = Object.class;
    Mockito.<Class<?>>when(typer.getDefaultImpl()).thenReturn(forNameResult);

    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(typer);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    StringToObjectValueConverter stringToObjectValueConverter =
        new StringToObjectValueConverter(objectMapper);

    JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () ->
            stringToObjectValueConverter.convert(jsonMapper.writeValueAsString(new ObjectValue())));
    verify(typer, atLeast(1))
        .buildTypeDeserializer(
            isA(DeserializationConfig.class),
            Mockito.<JavaType>any(),
            Mockito.<Collection<NamedType>>any());
    verify(typer, atLeast(1)).getDefaultImpl();
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   *
   * <p>Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString8() throws IOException {
    // Arrange
    ArrayType arrayType = mock(ArrayType.class);
    when(arrayType.getValueHandler()).thenReturn("Value Handler");

    JavaType javaType = mock(JavaType.class);
    when(javaType.isEnumType()).thenReturn(true);
    when(javaType.getContentType()).thenReturn(arrayType);
    when(javaType.getKeyType()).thenReturn(new PlaceholderForType(1));
    when(javaType.isContainerType()).thenReturn(true);
    when(javaType.isAbstract()).thenReturn(true);
    when(javaType.isMapLikeType()).thenReturn(true);
    Class<Object> forNameResult = Object.class;
    Mockito.<Class<?>>when(javaType.getRawClass()).thenReturn(forNameResult);

    ClassNameIdResolver idRes = mock(ClassNameIdResolver.class);
    when(idRes.typeFromId(Mockito.<DatabindContext>any(), Mockito.<String>any()))
        .thenReturn(javaType);
    when(idRes.idFromBaseType()).thenReturn("jane.doe@example.org");
    PlaceholderForType bt = new PlaceholderForType(1);

    AsArrayTypeDeserializer src =
        new AsArrayTypeDeserializer(
            bt, idRes, "Type Property Name", true, new PlaceholderForType(1));
    AsArrayTypeDeserializer asArrayTypeDeserializer = new AsArrayTypeDeserializer(src, new Bogus());

    StdTypeResolverBuilder typer = mock(StdTypeResolverBuilder.class);
    when(typer.buildTypeDeserializer(
            Mockito.<DeserializationConfig>any(),
            Mockito.<JavaType>any(),
            Mockito.<Collection<NamedType>>any()))
        .thenReturn(asArrayTypeDeserializer);
    Class<Object> forNameResult2 = Object.class;
    Mockito.<Class<?>>when(typer.getDefaultImpl()).thenReturn(forNameResult2);

    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(typer);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    StringToObjectValueConverter stringToObjectValueConverter =
        new StringToObjectValueConverter(objectMapper);

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () ->
            stringToObjectValueConverter.convert(
                JsonMapper.builder().findAndAddModules().build().writeValueAsString(42)));
    verify(javaType, atLeast(1)).getContentType();
    verify(javaType, atLeast(1)).getKeyType();
    verify(javaType, atLeast(1)).getRawClass();
    verify(arrayType, atLeast(1)).getValueHandler();
    verify(javaType).isAbstract();
    verify(javaType, atLeast(1)).isContainerType();
    verify(javaType).isEnumType();
    verify(javaType, atLeast(1)).isMapLikeType();
    verify(idRes).typeFromId(isA(DatabindContext.class), eq("jane.doe@example.org"));
    verify(typer, atLeast(1))
        .buildTypeDeserializer(
            isA(DeserializationConfig.class),
            Mockito.<JavaType>any(),
            Mockito.<Collection<NamedType>>any());
    verify(typer, atLeast(1)).getDefaultImpl();
    verify(idRes).idFromBaseType();
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   *
   * <p>Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString9() throws JsonProcessingException {
    // Arrange
    JavaType bt = mock(JavaType.class);
    Class<Object> forNameResult = Object.class;
    Mockito.<Class<?>>when(bt.getRawClass()).thenReturn(forNameResult);
    AsArrayTypeDeserializer src =
        new AsArrayTypeDeserializer(
            bt, mock(ClassNameIdResolver.class), "Type Property Name", true, null);
    AsArrayTypeDeserializer asArrayTypeDeserializer = new AsArrayTypeDeserializer(src, new Bogus());

    StdTypeResolverBuilder typer = mock(StdTypeResolverBuilder.class);
    when(typer.buildTypeDeserializer(
            Mockito.<DeserializationConfig>any(),
            Mockito.<JavaType>any(),
            Mockito.<Collection<NamedType>>any()))
        .thenReturn(asArrayTypeDeserializer);
    Class<Object> forNameResult2 = Object.class;
    Mockito.<Class<?>>when(typer.getDefaultImpl()).thenReturn(forNameResult2);

    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(typer);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    StringToObjectValueConverter stringToObjectValueConverter =
        new StringToObjectValueConverter(objectMapper);

    JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () ->
            stringToObjectValueConverter.convert(jsonMapper.writeValueAsString(new ObjectValue())));
    verify(bt, atLeast(1)).getRawClass();
    verify(typer, atLeast(1))
        .buildTypeDeserializer(
            isA(DeserializationConfig.class),
            Mockito.<JavaType>any(),
            Mockito.<Collection<NamedType>>any());
    verify(typer, atLeast(1)).getDefaultImpl();
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given builder addMixIn {@link Object} and {@link ObjectValue}.
   * </ul>
   *
   * <p>Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; given builder addMixIn Object and ObjectValue")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString_givenBuilderAddMixInObjectAndObjectValue()
      throws JsonProcessingException {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    Class<Object> target = Object.class;
    Class<ObjectValue> mixinSource = ObjectValue.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    StringToObjectValueConverter stringToObjectValueConverter =
        new StringToObjectValueConverter(objectMapper);

    JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertNull(
        stringToObjectValueConverter
            .convert(jsonMapper.writeValueAsString(new ObjectValue()))
            .getObject());
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given builder addMixIn {@link Object} and {@link Object}.
   *   <li>Then return Object is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName(
      "Test convert(String) with 'String'; given builder addMixIn Object and Object; then return Object is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString_givenBuilderAddMixInObjectAndObject_thenReturnObjectIsNull()
      throws JsonProcessingException {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    StringToObjectValueConverter stringToObjectValueConverter =
        new StringToObjectValueConverter(objectMapper);

    JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertNull(
        stringToObjectValueConverter
            .convert(jsonMapper.writeValueAsString(new ObjectValue()))
            .getObject());
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given builder addMixIn {@link ObjectValue} and {@link Object}.
   * </ul>
   *
   * <p>Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; given builder addMixIn ObjectValue and Object")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString_givenBuilderAddMixInObjectValueAndObject()
      throws JsonProcessingException {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    Class<ObjectValue> target = ObjectValue.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    StringToObjectValueConverter stringToObjectValueConverter =
        new StringToObjectValueConverter(objectMapper);

    JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertNull(
        stringToObjectValueConverter
            .convert(jsonMapper.writeValueAsString(new ObjectValue()))
            .getObject());
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given builder defaultLeniency {@code true}.
   *   <li>Then return Object is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName(
      "Test convert(String) with 'String'; given builder defaultLeniency 'true'; then return Object is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString_givenBuilderDefaultLeniencyTrue_thenReturnObjectIsNull()
      throws JsonProcessingException {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.defaultLeniency(true);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    StringToObjectValueConverter stringToObjectValueConverter =
        new StringToObjectValueConverter(objectMapper);

    JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertNull(
        stringToObjectValueConverter
            .convert(jsonMapper.writeValueAsString(new ObjectValue()))
            .getObject());
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given builder DefaultTyping is {@link StdTypeResolverBuilder#StdTypeResolverBuilder()}.
   * </ul>
   *
   * <p>Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName(
      "Test convert(String) with 'String'; given builder DefaultTyping is StdTypeResolverBuilder()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString_givenBuilderDefaultTypingIsStdTypeResolverBuilder()
      throws JsonProcessingException {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new StdTypeResolverBuilder());
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    StringToObjectValueConverter stringToObjectValueConverter =
        new StringToObjectValueConverter(objectMapper);

    JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () ->
            stringToObjectValueConverter.convert(jsonMapper.writeValueAsString(new ObjectValue())));
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given {@link JavaType} {@link JavaType#isContainerType()} throw {@link
   *       RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName(
      "Test convert(String) with 'String'; given JavaType isContainerType() throw RuntimeException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString_givenJavaTypeIsContainerTypeThrowRuntimeException()
      throws IOException {
    // Arrange
    JavaType javaType = mock(JavaType.class);
    when(javaType.isContainerType()).thenThrow(new RuntimeException());

    ClassNameIdResolver idRes = mock(ClassNameIdResolver.class);
    when(idRes.typeFromId(Mockito.<DatabindContext>any(), Mockito.<String>any()))
        .thenReturn(javaType);
    when(idRes.idFromBaseType()).thenReturn("jane.doe@example.org");
    PlaceholderForType bt = new PlaceholderForType(1);

    AsArrayTypeDeserializer src =
        new AsArrayTypeDeserializer(
            bt, idRes, "Type Property Name", true, new PlaceholderForType(1));
    AsArrayTypeDeserializer asArrayTypeDeserializer = new AsArrayTypeDeserializer(src, new Bogus());

    StdTypeResolverBuilder typer = mock(StdTypeResolverBuilder.class);
    when(typer.buildTypeDeserializer(
            Mockito.<DeserializationConfig>any(),
            Mockito.<JavaType>any(),
            Mockito.<Collection<NamedType>>any()))
        .thenReturn(asArrayTypeDeserializer);
    Class<Object> forNameResult = Object.class;
    Mockito.<Class<?>>when(typer.getDefaultImpl()).thenReturn(forNameResult);

    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(typer);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    StringToObjectValueConverter stringToObjectValueConverter =
        new StringToObjectValueConverter(objectMapper);

    JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () ->
            stringToObjectValueConverter.convert(jsonMapper.writeValueAsString(new ObjectValue())));
    verify(javaType).isContainerType();
    verify(idRes).typeFromId(isA(DatabindContext.class), eq("jane.doe@example.org"));
    verify(typer, atLeast(1))
        .buildTypeDeserializer(
            isA(DeserializationConfig.class),
            Mockito.<JavaType>any(),
            Mockito.<Collection<NamedType>>any());
    verify(typer, atLeast(1)).getDefaultImpl();
    verify(idRes).idFromBaseType();
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Then calls {@link ArrayType#getRawClass()}.
   * </ul>
   *
   * <p>Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; then calls getRawClass()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString_thenCallsGetRawClass() throws JsonProcessingException {
    // Arrange
    ArrayType baseType = mock(ArrayType.class);
    when(baseType.getContentType()).thenReturn(new PlaceholderForType(1));
    when(baseType.isArrayType()).thenReturn(true);
    when(baseType.isTypeOrSuperTypeOf(Mockito.<Class<?>>any())).thenReturn(true);
    Class<Object> forNameResult = Object.class;
    Mockito.<Class<?>>when(baseType.getRawClass()).thenReturn(forNameResult);
    TypeFactory typeFactory = TypeFactory.defaultInstance();

    BasicPolymorphicTypeValidator.Builder builderResult = BasicPolymorphicTypeValidator.builder();
    Class<Object> baseTypeToDeny = Object.class;
    BasicPolymorphicTypeValidator ptv = builderResult.denyForExactBaseType(baseTypeToDeny).build();

    ClassNameIdResolver idRes = new ClassNameIdResolver(baseType, typeFactory, ptv);
    PlaceholderForType bt = new PlaceholderForType(1);

    AsArrayTypeDeserializer src =
        new AsArrayTypeDeserializer(
            bt, idRes, "Type Property Name", true, new PlaceholderForType(1));
    AsArrayTypeDeserializer asArrayTypeDeserializer = new AsArrayTypeDeserializer(src, new Bogus());

    StdTypeResolverBuilder typer = mock(StdTypeResolverBuilder.class);
    when(typer.buildTypeDeserializer(
            Mockito.<DeserializationConfig>any(),
            Mockito.<JavaType>any(),
            Mockito.<Collection<NamedType>>any()))
        .thenReturn(asArrayTypeDeserializer);
    Class<Object> forNameResult2 = Object.class;
    Mockito.<Class<?>>when(typer.getDefaultImpl()).thenReturn(forNameResult2);

    Builder builderResult2 = JsonMapper.builder();
    builderResult2.setDefaultTyping(typer);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult2.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult2.findAndAddModules().build();
    StringToObjectValueConverter stringToObjectValueConverter =
        new StringToObjectValueConverter(objectMapper);

    JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () ->
            stringToObjectValueConverter.convert(jsonMapper.writeValueAsString(new ObjectValue())));
    verify(baseType, atLeast(1)).getRawClass();
    verify(baseType).isTypeOrSuperTypeOf(isA(Class.class));
    verify(typer, atLeast(1))
        .buildTypeDeserializer(
            isA(DeserializationConfig.class),
            Mockito.<JavaType>any(),
            Mockito.<Collection<NamedType>>any());
    verify(typer, atLeast(1)).getDefaultImpl();
    verify(baseType).getContentType();
    verify(baseType).isArrayType();
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Then calls {@link ArrayType#getValueHandler()}.
   * </ul>
   *
   * <p>Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; then calls getValueHandler()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString_thenCallsGetValueHandler() throws IOException {
    // Arrange
    ArrayType arrayType = mock(ArrayType.class);
    when(arrayType.getValueHandler()).thenReturn("Value Handler");

    JavaType javaType = mock(JavaType.class);
    when(javaType.isEnumType()).thenReturn(true);
    when(javaType.getContentType()).thenReturn(arrayType);
    when(javaType.getKeyType()).thenReturn(new PlaceholderForType(1));
    when(javaType.isContainerType()).thenReturn(true);
    when(javaType.isAbstract()).thenReturn(true);
    when(javaType.isMapLikeType()).thenReturn(true);
    Class<Object> forNameResult = Object.class;
    Mockito.<Class<?>>when(javaType.getRawClass()).thenReturn(forNameResult);

    ClassNameIdResolver idRes = mock(ClassNameIdResolver.class);
    when(idRes.typeFromId(Mockito.<DatabindContext>any(), Mockito.<String>any()))
        .thenReturn(javaType);
    when(idRes.idFromBaseType()).thenReturn("jane.doe@example.org");
    PlaceholderForType bt = new PlaceholderForType(1);

    AsArrayTypeDeserializer src =
        new AsArrayTypeDeserializer(
            bt, idRes, "Type Property Name", true, new PlaceholderForType(1));
    AsArrayTypeDeserializer asArrayTypeDeserializer = new AsArrayTypeDeserializer(src, new Bogus());

    StdTypeResolverBuilder typer = mock(StdTypeResolverBuilder.class);
    when(typer.buildTypeDeserializer(
            Mockito.<DeserializationConfig>any(),
            Mockito.<JavaType>any(),
            Mockito.<Collection<NamedType>>any()))
        .thenReturn(asArrayTypeDeserializer);
    Class<Object> forNameResult2 = Object.class;
    Mockito.<Class<?>>when(typer.getDefaultImpl()).thenReturn(forNameResult2);

    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(typer);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    StringToObjectValueConverter stringToObjectValueConverter =
        new StringToObjectValueConverter(objectMapper);

    JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () ->
            stringToObjectValueConverter.convert(jsonMapper.writeValueAsString(new ObjectValue())));
    verify(javaType, atLeast(1)).getContentType();
    verify(javaType, atLeast(1)).getKeyType();
    verify(javaType, atLeast(1)).getRawClass();
    verify(arrayType, atLeast(1)).getValueHandler();
    verify(javaType).isAbstract();
    verify(javaType, atLeast(1)).isContainerType();
    verify(javaType).isEnumType();
    verify(javaType, atLeast(1)).isMapLikeType();
    verify(idRes).typeFromId(isA(DatabindContext.class), eq("jane.doe@example.org"));
    verify(typer, atLeast(1))
        .buildTypeDeserializer(
            isA(DeserializationConfig.class),
            Mockito.<JavaType>any(),
            Mockito.<Collection<NamedType>>any());
    verify(typer, atLeast(1)).getDefaultImpl();
    verify(idRes).idFromBaseType();
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Then calls {@link JavaType#hasGenericTypes()}.
   * </ul>
   *
   * <p>Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; then calls hasGenericTypes()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString_thenCallsHasGenericTypes() throws IOException {
    // Arrange
    JavaType bt = mock(JavaType.class);
    when(bt.getKeyType()).thenReturn(new PlaceholderForType(1));
    when(bt.isContainerType()).thenReturn(true);
    when(bt.isAbstract()).thenReturn(true);
    when(bt.isMapLikeType()).thenReturn(true);
    when(bt.getContentType()).thenReturn(new PlaceholderForType(1));
    when(bt.isEnumType()).thenReturn(true);
    when(bt.hasRawClass(Mockito.<Class<?>>any())).thenReturn(true);
    Class<Object> forNameResult = Object.class;
    Mockito.<Class<?>>when(bt.getRawClass()).thenReturn(forNameResult);

    JavaType javaType = mock(JavaType.class);
    when(javaType.hasGenericTypes()).thenReturn(false);
    Class<Object> forNameResult2 = Object.class;
    Mockito.<Class<?>>when(javaType.getRawClass()).thenReturn(forNameResult2);

    ClassNameIdResolver idRes = mock(ClassNameIdResolver.class);
    when(idRes.typeFromId(Mockito.<DatabindContext>any(), Mockito.<String>any()))
        .thenReturn(javaType);
    when(idRes.idFromBaseType()).thenReturn("jane.doe@example.org");

    AsArrayTypeDeserializer src =
        new AsArrayTypeDeserializer(
            bt, idRes, "Type Property Name", true, new PlaceholderForType(1));
    AsArrayTypeDeserializer asArrayTypeDeserializer = new AsArrayTypeDeserializer(src, new Bogus());

    StdTypeResolverBuilder typer = mock(StdTypeResolverBuilder.class);
    when(typer.buildTypeDeserializer(
            Mockito.<DeserializationConfig>any(),
            Mockito.<JavaType>any(),
            Mockito.<Collection<NamedType>>any()))
        .thenReturn(asArrayTypeDeserializer);
    Class<Object> forNameResult3 = Object.class;
    Mockito.<Class<?>>when(typer.getDefaultImpl()).thenReturn(forNameResult3);

    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(typer);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();
    StringToObjectValueConverter stringToObjectValueConverter =
        new StringToObjectValueConverter(objectMapper);

    JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () ->
            stringToObjectValueConverter.convert(jsonMapper.writeValueAsString(new ObjectValue())));
    verify(bt, atLeast(1)).getContentType();
    verify(bt, atLeast(1)).getKeyType();
    verify(javaType).getRawClass();
    verify(bt, atLeast(1)).getRawClass();
    verify(javaType).hasGenericTypes();
    verify(bt).hasRawClass(isA(Class.class));
    verify(bt).isAbstract();
    verify(bt, atLeast(1)).isContainerType();
    verify(bt).isEnumType();
    verify(bt, atLeast(1)).isMapLikeType();
    verify(idRes).typeFromId(isA(DatabindContext.class), eq("jane.doe@example.org"));
    verify(typer, atLeast(1))
        .buildTypeDeserializer(
            isA(DeserializationConfig.class),
            Mockito.<JavaType>any(),
            Mockito.<Collection<NamedType>>any());
    verify(typer, atLeast(1)).getDefaultImpl();
    verify(idRes).idFromBaseType();
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Then return Object is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; then return Object is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString_thenReturnObjectIsNull() throws JsonProcessingException {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
    StringToObjectValueConverter stringToObjectValueConverter =
        new StringToObjectValueConverter(objectMapper);

    JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertNull(
        stringToObjectValueConverter
            .convert(jsonMapper.writeValueAsString(new ObjectValue()))
            .getObject());
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Then return {@link ObjectValue#ObjectValue()}.
   * </ul>
   *
   * <p>Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; then return ObjectValue()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString_thenReturnObjectValue() throws JsonProcessingException {
    // Arrange
    ObjectValue objectValue = new ObjectValue();
    when(objectMapper.readValue(Mockito.<String>any(), Mockito.<Class<ObjectValue>>any()))
        .thenReturn(objectValue);
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");

    // Act
    ObjectValue actualConvertResult =
        stringToObjectValueConverter.convert(objectMapper.writeValueAsString(new ObjectValue()));

    // Assert
    verify(objectMapper).readValue(eq("42"), isA(Class.class));
    verify(objectMapper).writeValueAsString(isA(Object.class));
    assertNull(actualConvertResult.getObject());
    assertSame(objectValue, actualConvertResult);
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; when 'null'; then throw RuntimeException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString_whenNull_thenThrowRuntimeException() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class, () -> new StringToObjectValueConverter(objectMapper).convert(null));
  }

  /**
   * Test {@link StringToObjectValueConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>When {@code Source}.
   *   <li>Then throw {@link RuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link StringToObjectValueConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; when 'Source'; then throw RuntimeException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectValue StringToObjectValueConverter.convert(String)"})
  void testConvertWithString_whenSource_thenThrowRuntimeException() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class,
        () -> new StringToObjectValueConverter(objectMapper).convert("Source"));
  }
}
