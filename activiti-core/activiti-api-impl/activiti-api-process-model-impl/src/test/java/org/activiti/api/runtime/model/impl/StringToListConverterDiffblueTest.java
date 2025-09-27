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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.BeanProperty.Bogus;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTypeResolverBuilder;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.cfg.DefaultCacheProvider;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
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

@ContextConfiguration(classes = {StringToListConverter.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class StringToListConverterDiffblueTest {
  @MockBean private ObjectMapper objectMapper;

  @Autowired private StringToListConverter stringToListConverter;

  /**
   * Test {@link StringToListConverter#convert(String)} with {@code String}.
   *
   * <p>Method under test: {@link StringToListConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List StringToListConverter.convert(String)"})
  void testConvertWithString() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.cacheProvider(
        DefaultCacheProvider.builder()
            .maxDeserializerCacheSize(3)
            .maxSerializerCacheSize(3)
            .maxTypeFactoryCacheSize(3)
            .build());
    JsonMapper objectMapper = builderResult.findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class, () -> new StringToListConverter(objectMapper).convert("Source"));
  }

  /**
   * Test {@link StringToListConverter#convert(String)} with {@code String}.
   *
   * <p>Method under test: {@link StringToListConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List StringToListConverter.convert(String)"})
  void testConvertWithString2() throws IOException {
    // Arrange
    DeserializationProblemHandler h = mock(DeserializationProblemHandler.class);
    when(h.handleUnexpectedToken(
            Mockito.<DeserializationContext>any(),
            Mockito.<JavaType>any(),
            Mockito.<JsonToken>any(),
            Mockito.<JsonParser>any(),
            Mockito.<String>any()))
        .thenReturn("Handle Unexpected Token");

    Builder builderResult = JsonMapper.builder();
    builderResult.cacheProvider(
        DefaultCacheProvider.builder()
            .maxDeserializerCacheSize(3)
            .maxSerializerCacheSize(3)
            .maxTypeFactoryCacheSize(3)
            .build());
    builderResult.addHandler(h);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class, () -> new StringToListConverter(objectMapper).convert("42"));
    verify(h)
        .handleUnexpectedToken(
            isA(DeserializationContext.class),
            isA(JavaType.class),
            eq(JsonToken.VALUE_NUMBER_INT),
            isA(JsonParser.class),
            (String) isNull());
  }

  /**
   * Test {@link StringToListConverter#convert(String)} with {@code String}.
   *
   * <p>Method under test: {@link StringToListConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List StringToListConverter.convert(String)"})
  void testConvertWithString3() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new DefaultTypeResolverBuilder(DefaultTyping.JAVA_LANG_OBJECT));
    builderResult.addHandler(mock(DeserializationProblemHandler.class));
    JsonMapper objectMapper = builderResult.findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class, () -> new StringToListConverter(objectMapper).convert("42"));
  }

  /**
   * Test {@link StringToListConverter#convert(String)} with {@code String}.
   *
   * <p>Method under test: {@link StringToListConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List StringToListConverter.convert(String)"})
  void testConvertWithString4() {
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
    builderResult2.addHandler(mock(DeserializationProblemHandler.class));
    JsonMapper objectMapper = builderResult2.findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class, () -> new StringToListConverter(objectMapper).convert("42"));
    verify(typer, atLeast(1))
        .buildTypeDeserializer(
            isA(DeserializationConfig.class),
            Mockito.<JavaType>any(),
            Mockito.<Collection<NamedType>>any());
    verify(typer, atLeast(1)).getDefaultImpl();
  }

  /**
   * Test {@link StringToListConverter#convert(String)} with {@code String}.
   *
   * <p>Method under test: {@link StringToListConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List StringToListConverter.convert(String)"})
  void testConvertWithString5() {
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
    builderResult2.addHandler(mock(DeserializationProblemHandler.class));
    JsonMapper objectMapper = builderResult2.findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class, () -> new StringToListConverter(objectMapper).convert("42"));
    verify(typer, atLeast(1))
        .buildTypeDeserializer(
            isA(DeserializationConfig.class),
            Mockito.<JavaType>any(),
            Mockito.<Collection<NamedType>>any());
    verify(typer, atLeast(1)).getDefaultImpl();
  }

  /**
   * Test {@link StringToListConverter#convert(String)} with {@code String}.
   *
   * <p>Method under test: {@link StringToListConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List StringToListConverter.convert(String)"})
  void testConvertWithString6() {
    // Arrange
    ArrayType bt = mock(ArrayType.class);
    Class<Object> forNameResult = Object.class;
    Mockito.<Class<?>>when(bt.getRawClass()).thenReturn(forNameResult);
    PlaceholderForType baseType = new PlaceholderForType(1);
    TypeFactory typeFactory = TypeFactory.defaultInstance();

    BasicPolymorphicTypeValidator.Builder builderResult = BasicPolymorphicTypeValidator.builder();
    Class<Object> baseTypeToDeny = Object.class;
    BasicPolymorphicTypeValidator ptv = builderResult.denyForExactBaseType(baseTypeToDeny).build();

    ClassNameIdResolver idRes = new ClassNameIdResolver(baseType, typeFactory, ptv);

    AsWrapperTypeDeserializer asWrapperTypeDeserializer =
        new AsWrapperTypeDeserializer(
            bt, idRes, "Type Property Name", true, new PlaceholderForType(1));

    StdTypeResolverBuilder typer = mock(StdTypeResolverBuilder.class);
    when(typer.buildTypeDeserializer(
            Mockito.<DeserializationConfig>any(),
            Mockito.<JavaType>any(),
            Mockito.<Collection<NamedType>>any()))
        .thenReturn(asWrapperTypeDeserializer);
    Class<Object> forNameResult2 = Object.class;
    Mockito.<Class<?>>when(typer.getDefaultImpl()).thenReturn(forNameResult2);

    Builder builderResult2 = JsonMapper.builder();
    builderResult2.setDefaultTyping(typer);
    builderResult2.addHandler(mock(DeserializationProblemHandler.class));
    JsonMapper objectMapper = builderResult2.findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class, () -> new StringToListConverter(objectMapper).convert("42"));
    verify(bt, atLeast(1)).getRawClass();
    verify(typer, atLeast(1))
        .buildTypeDeserializer(
            isA(DeserializationConfig.class),
            Mockito.<JavaType>any(),
            Mockito.<Collection<NamedType>>any());
    verify(typer, atLeast(1)).getDefaultImpl();
  }

  /**
   * Test {@link StringToListConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given {@link ArrayType} {@link ArrayType#isArrayType()} return {@code false}.
   *   <li>Then calls {@link ArrayType#toCanonical()}.
   * </ul>
   *
   * <p>Method under test: {@link StringToListConverter#convert(String)}
   */
  @Test
  @DisplayName(
      "Test convert(String) with 'String'; given ArrayType isArrayType() return 'false'; then calls toCanonical()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List StringToListConverter.convert(String)"})
  void testConvertWithString_givenArrayTypeIsArrayTypeReturnFalse_thenCallsToCanonical() {
    // Arrange
    ArrayType baseType = mock(ArrayType.class);
    when(baseType.isArrayType()).thenReturn(false);
    when(baseType.isTypeOrSuperTypeOf(Mockito.<Class<?>>any())).thenReturn(true);
    when(baseType.toCanonical()).thenReturn("Canonical");
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
    builderResult2.addHandler(mock(DeserializationProblemHandler.class));
    JsonMapper objectMapper = builderResult2.findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class, () -> new StringToListConverter(objectMapper).convert("42"));
    verify(baseType, atLeast(1)).getRawClass();
    verify(baseType).isTypeOrSuperTypeOf(isA(Class.class));
    verify(typer, atLeast(1))
        .buildTypeDeserializer(
            isA(DeserializationConfig.class),
            Mockito.<JavaType>any(),
            Mockito.<Collection<NamedType>>any());
    verify(typer, atLeast(1)).getDefaultImpl();
    verify(baseType).isArrayType();
    verify(baseType).toCanonical();
  }

  /**
   * Test {@link StringToListConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given builder addMixIn {@link Object} and {@link Object}.
   * </ul>
   *
   * <p>Method under test: {@link StringToListConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; given builder addMixIn Object and Object")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List StringToListConverter.convert(String)"})
  void testConvertWithString_givenBuilderAddMixInObjectAndObject() throws IOException {
    // Arrange
    DeserializationProblemHandler h = mock(DeserializationProblemHandler.class);
    when(h.handleUnexpectedToken(
            Mockito.<DeserializationContext>any(),
            Mockito.<JavaType>any(),
            Mockito.<JsonToken>any(),
            Mockito.<JsonParser>any(),
            Mockito.<String>any()))
        .thenReturn("Handle Unexpected Token");

    Builder builderResult = JsonMapper.builder();
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;

    builderResult.addMixIn(target, mixinSource);
    builderResult.addHandler(h);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class, () -> new StringToListConverter(objectMapper).convert("42"));
    verify(h)
        .handleUnexpectedToken(
            isA(DeserializationContext.class),
            isA(JavaType.class),
            eq(JsonToken.VALUE_NUMBER_INT),
            isA(JsonParser.class),
            (String) isNull());
  }

  /**
   * Test {@link StringToListConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given builder defaultLeniency {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link StringToListConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; given builder defaultLeniency 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List StringToListConverter.convert(String)"})
  void testConvertWithString_givenBuilderDefaultLeniencyTrue() throws IOException {
    // Arrange
    DeserializationProblemHandler h = mock(DeserializationProblemHandler.class);
    when(h.handleUnexpectedToken(
            Mockito.<DeserializationContext>any(),
            Mockito.<JavaType>any(),
            Mockito.<JsonToken>any(),
            Mockito.<JsonParser>any(),
            Mockito.<String>any()))
        .thenReturn("Handle Unexpected Token");

    Builder builderResult = JsonMapper.builder();
    builderResult.defaultLeniency(true);
    builderResult.addHandler(h);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class, () -> new StringToListConverter(objectMapper).convert("42"));
    verify(h)
        .handleUnexpectedToken(
            isA(DeserializationContext.class),
            isA(JavaType.class),
            eq(JsonToken.VALUE_NUMBER_INT),
            isA(JsonParser.class),
            (String) isNull());
  }

  /**
   * Test {@link StringToListConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given builder DefaultTyping is {@link StdTypeResolverBuilder#StdTypeResolverBuilder()}.
   *   <li>When {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link StringToListConverter#convert(String)}
   */
  @Test
  @DisplayName(
      "Test convert(String) with 'String'; given builder DefaultTyping is StdTypeResolverBuilder(); when '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List StringToListConverter.convert(String)"})
  void testConvertWithString_givenBuilderDefaultTypingIsStdTypeResolverBuilder_when42() {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new StdTypeResolverBuilder());
    builderResult.addHandler(mock(DeserializationProblemHandler.class));
    JsonMapper objectMapper = builderResult.findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class, () -> new StringToListConverter(objectMapper).convert("42"));
  }

  /**
   * Test {@link StringToListConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given {@link ObjectMapper} {@link ObjectMapper#getTypeFactory()} return defaultInstance.
   * </ul>
   *
   * <p>Method under test: {@link StringToListConverter#convert(String)}
   */
  @Test
  @DisplayName(
      "Test convert(String) with 'String'; given ObjectMapper getTypeFactory() return defaultInstance")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List StringToListConverter.convert(String)"})
  void testConvertWithString_givenObjectMapperGetTypeFactoryReturnDefaultInstance()
      throws JsonProcessingException {
    // Arrange
    when(objectMapper.readValue(Mockito.<String>any(), Mockito.<JavaType>any()))
        .thenThrow(new RuntimeException());
    when(objectMapper.getTypeFactory()).thenReturn(TypeFactory.defaultInstance());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> stringToListConverter.convert("Source"));
    verify(objectMapper).getTypeFactory();
    verify(objectMapper).readValue(eq("Source"), isA(JavaType.class));
  }

  /**
   * Test {@link StringToListConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Given {@link ObjectMapper} {@link ObjectMapper#getTypeFactory()} throw {@link
   *       RuntimeException#RuntimeException()}.
   * </ul>
   *
   * <p>Method under test: {@link StringToListConverter#convert(String)}
   */
  @Test
  @DisplayName(
      "Test convert(String) with 'String'; given ObjectMapper getTypeFactory() throw RuntimeException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List StringToListConverter.convert(String)"})
  void testConvertWithString_givenObjectMapperGetTypeFactoryThrowRuntimeException() {
    // Arrange
    when(objectMapper.getTypeFactory()).thenThrow(new RuntimeException());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> stringToListConverter.convert("Source"));
    verify(objectMapper).getTypeFactory();
  }

  /**
   * Test {@link StringToListConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Then calls {@link TypeFactory#constructParametricType(Class, Class[])}.
   * </ul>
   *
   * <p>Method under test: {@link StringToListConverter#convert(String)}
   */
  @Test
  @DisplayName(
      "Test convert(String) with 'String'; then calls constructParametricType(Class, Class[])")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List StringToListConverter.convert(String)"})
  void testConvertWithString_thenCallsConstructParametricType() throws JsonProcessingException {
    // Arrange
    TypeFactory typeFactory = mock(TypeFactory.class);
    when(typeFactory.constructParametricType(Mockito.<Class<?>>any(), isA(Class[].class)))
        .thenReturn(new PlaceholderForType(1));
    when(objectMapper.readValue(Mockito.<String>any(), Mockito.<JavaType>any()))
        .thenThrow(new RuntimeException());
    when(objectMapper.getTypeFactory()).thenReturn(typeFactory);

    // Act and Assert
    assertThrows(RuntimeException.class, () -> stringToListConverter.convert("Source"));
    verify(objectMapper).getTypeFactory();
    verify(objectMapper).readValue(eq("Source"), isA(JavaType.class));
    verify(typeFactory).constructParametricType(isA(Class.class), isA(Class[].class));
  }

  /**
   * Test {@link StringToListConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Then calls {@link ArrayType#getContentType()}.
   * </ul>
   *
   * <p>Method under test: {@link StringToListConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; then calls getContentType()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List StringToListConverter.convert(String)"})
  void testConvertWithString_thenCallsGetContentType() {
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
    builderResult2.addHandler(mock(DeserializationProblemHandler.class));
    JsonMapper objectMapper = builderResult2.findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class, () -> new StringToListConverter(objectMapper).convert("42"));
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
   * Test {@link StringToListConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>Then calls {@link
   *       DeserializationProblemHandler#handleUnexpectedToken(DeserializationContext, JavaType,
   *       JsonToken, JsonParser, String)}.
   * </ul>
   *
   * <p>Method under test: {@link StringToListConverter#convert(String)}
   */
  @Test
  @DisplayName(
      "Test convert(String) with 'String'; then calls handleUnexpectedToken(DeserializationContext, JavaType, JsonToken, JsonParser, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List StringToListConverter.convert(String)"})
  void testConvertWithString_thenCallsHandleUnexpectedToken() throws IOException {
    // Arrange
    DeserializationProblemHandler h = mock(DeserializationProblemHandler.class);
    when(h.handleUnexpectedToken(
            Mockito.<DeserializationContext>any(),
            Mockito.<JavaType>any(),
            Mockito.<JsonToken>any(),
            Mockito.<JsonParser>any(),
            Mockito.<String>any()))
        .thenReturn("Handle Unexpected Token");

    Builder builderResult = JsonMapper.builder();
    builderResult.addHandler(h);
    JsonMapper objectMapper = builderResult.findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class, () -> new StringToListConverter(objectMapper).convert("42"));
    verify(h)
        .handleUnexpectedToken(
            isA(DeserializationContext.class),
            isA(JavaType.class),
            eq(JsonToken.VALUE_NUMBER_INT),
            isA(JsonParser.class),
            (String) isNull());
  }

  /**
   * Test {@link StringToListConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>When {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link StringToListConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; when '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List StringToListConverter.convert(String)"})
  void testConvertWithString_when42() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class, () -> new StringToListConverter(objectMapper).convert("42"));
  }

  /**
   * Test {@link StringToListConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link StringToListConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; when empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List StringToListConverter.convert(String)"})
  void testConvertWithString_whenEmptyString() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertThrows(RuntimeException.class, () -> new StringToListConverter(objectMapper).convert(""));
  }

  /**
   * Test {@link StringToListConverter#convert(String)} with {@code String}.
   *
   * <ul>
   *   <li>When {@code Source}.
   * </ul>
   *
   * <p>Method under test: {@link StringToListConverter#convert(String)}
   */
  @Test
  @DisplayName("Test convert(String) with 'String'; when 'Source'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List StringToListConverter.convert(String)"})
  void testConvertWithString_whenSource() {
    // Arrange
    JsonMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    // Act and Assert
    assertThrows(
        RuntimeException.class, () -> new StringToListConverter(objectMapper).convert("Source"));
  }
}
