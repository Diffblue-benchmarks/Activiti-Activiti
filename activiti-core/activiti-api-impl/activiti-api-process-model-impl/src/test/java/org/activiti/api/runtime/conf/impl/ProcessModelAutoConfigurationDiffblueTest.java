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
package org.activiti.api.runtime.conf.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTypeResolverBuilder;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.json.JsonMapper.Builder;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.impl.AsArrayTypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.impl.ClassNameIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.fasterxml.jackson.databind.type.PlaceholderForType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.activiti.api.runtime.model.impl.DateToStringConverter;
import org.activiti.api.runtime.model.impl.ListToStringConverter;
import org.activiti.api.runtime.model.impl.MapToStringConverter;
import org.activiti.api.runtime.model.impl.ObjectValue;
import org.activiti.api.runtime.model.impl.ObjectValueToStringConverter;
import org.activiti.api.runtime.model.impl.SetToStringConverter;
import org.activiti.api.runtime.model.impl.StringToJsonNodeConverter;
import org.activiti.api.runtime.model.impl.StringToObjectValueConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ProcessModelAutoConfiguration.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class ProcessModelAutoConfigurationDiffblueTest {
  @MockBean private Converter<Object, Object> converter;

  @Autowired private ProcessModelAutoConfiguration processModelAutoConfiguration;

  @Autowired private Set<Converter<Object, Object>> set;

  /**
   * Test {@link ProcessModelAutoConfiguration#conversionService()}.
   *
   * <p>Method under test: {@link ProcessModelAutoConfiguration#conversionService()}
   */
  @Test
  @DisplayName("Test conversionService()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.springframework.format.support.FormattingConversionService ProcessModelAutoConfiguration.conversionService()"
  })
  void testConversionService() {
    // Arrange, Act and Assert
    assertTrue(
        processModelAutoConfiguration.conversionService() instanceof ApplicationConversionService);
  }

  /**
   * Test {@link ProcessModelAutoConfiguration#stringToMapConverter(ObjectMapper)}.
   *
   * <p>Method under test: {@link ProcessModelAutoConfiguration#stringToMapConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test stringToMapConverter(ObjectMapper)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.runtime.model.impl.StringToMapConverter ProcessModelAutoConfiguration.stringToMapConverter(ObjectMapper)"
  })
  void testStringToMapConverter() {
    // Arrange, Act and Assert
    assertThrows(
        RuntimeException.class,
        () ->
            processModelAutoConfiguration
                .stringToMapConverter(JsonMapper.builder().findAndAddModules().build())
                .convert("Source"));
  }

  /**
   * Test {@link ProcessModelAutoConfiguration#mapToStringConverter(ObjectMapper)}.
   *
   * <p>Method under test: {@link ProcessModelAutoConfiguration#mapToStringConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test mapToStringConverter(ObjectMapper)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "MapToStringConverter ProcessModelAutoConfiguration.mapToStringConverter(ObjectMapper)"
  })
  void testMapToStringConverter() {
    // Arrange and Act
    MapToStringConverter actualMapToStringConverterResult =
        processModelAutoConfiguration.mapToStringConverter(
            JsonMapper.builder().findAndAddModules().build());

    // Assert
    assertEquals("{}", actualMapToStringConverterResult.convert(new HashMap<>()));
  }

  /**
   * Test {@link ProcessModelAutoConfiguration#stringToJsonNodeConverter(ObjectMapper)}.
   *
   * <p>Method under test: {@link
   * ProcessModelAutoConfiguration#stringToJsonNodeConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test stringToJsonNodeConverter(ObjectMapper)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "StringToJsonNodeConverter ProcessModelAutoConfiguration.stringToJsonNodeConverter(ObjectMapper)"
  })
  void testStringToJsonNodeConverter() throws JsonProcessingException {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new DefaultTypeResolverBuilder(DefaultTyping.JAVA_LANG_OBJECT));

    // Act
    StringToJsonNodeConverter actualStringToJsonNodeConverterResult =
        processModelAutoConfiguration.stringToJsonNodeConverter(
            builderResult.findAndAddModules().build());
    DoubleNode valueOfResult = DoubleNode.valueOf(10.0d);
    JsonNode actualConvertResult =
        actualStringToJsonNodeConverterResult.convert(
            JsonMapper.builder().findAndAddModules().build().writeValueAsString(valueOfResult));

    // Assert
    assertTrue(actualConvertResult instanceof DoubleNode);
    assertEquals(valueOfResult, actualConvertResult);
  }

  /**
   * Test {@link ProcessModelAutoConfiguration#stringToJsonNodeConverter(ObjectMapper)}.
   *
   * <ul>
   *   <li>When builder findAndAddModules build.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessModelAutoConfiguration#stringToJsonNodeConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test stringToJsonNodeConverter(ObjectMapper); when builder findAndAddModules build")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "StringToJsonNodeConverter ProcessModelAutoConfiguration.stringToJsonNodeConverter(ObjectMapper)"
  })
  void testStringToJsonNodeConverter_whenBuilderFindAndAddModulesBuild()
      throws JsonProcessingException {
    // Arrange and Act
    StringToJsonNodeConverter actualStringToJsonNodeConverterResult =
        processModelAutoConfiguration.stringToJsonNodeConverter(
            JsonMapper.builder().findAndAddModules().build());
    DoubleNode valueOfResult = DoubleNode.valueOf(10.0d);
    JsonNode actualConvertResult =
        actualStringToJsonNodeConverterResult.convert(
            JsonMapper.builder().findAndAddModules().build().writeValueAsString(valueOfResult));

    // Assert
    assertTrue(actualConvertResult instanceof DoubleNode);
    assertEquals(valueOfResult, actualConvertResult);
  }

  /**
   * Test {@link ProcessModelAutoConfiguration#jsonNodeToStringConverter(ObjectMapper)}.
   *
   * <p>Method under test: {@link
   * ProcessModelAutoConfiguration#jsonNodeToStringConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test jsonNodeToStringConverter(ObjectMapper)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.runtime.model.impl.JsonNodeToStringConverter ProcessModelAutoConfiguration.jsonNodeToStringConverter(ObjectMapper)"
  })
  void testJsonNodeToStringConverter() {
    // Arrange
    DoubleNode source = DoubleNode.valueOf(10.0d);

    // Act
    String actualConvertResult =
        processModelAutoConfiguration
            .jsonNodeToStringConverter(JsonMapper.builder().findAndAddModules().build())
            .convert(source);

    // Assert
    assertTrue(source.traverse() instanceof TreeTraversingParser);
    assertEquals("10.0", actualConvertResult);
  }

  /**
   * Test {@link ProcessModelAutoConfiguration#dateToStringConverter()}.
   *
   * <p>Method under test: {@link ProcessModelAutoConfiguration#dateToStringConverter()}
   */
  @Test
  @DisplayName("Test dateToStringConverter()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"DateToStringConverter ProcessModelAutoConfiguration.dateToStringConverter()"})
  void testDateToStringConverter() {
    // Arrange and Act
    DateToStringConverter actualDateToStringConverterResult =
        processModelAutoConfiguration.dateToStringConverter();

    // Assert
    assertEquals(
        "1970-01-01T00:00:00Z",
        actualDateToStringConverterResult.convert(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())));
  }

  /**
   * Test {@link ProcessModelAutoConfiguration#localDateTimeToStringConverter()}.
   *
   * <p>Method under test: {@link ProcessModelAutoConfiguration#localDateTimeToStringConverter()}
   */
  @Test
  @DisplayName("Test localDateTimeToStringConverter()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.runtime.model.impl.LocalDateTimeToStringConverter ProcessModelAutoConfiguration.localDateTimeToStringConverter()"
  })
  void testLocalDateTimeToStringConverter() {
    // Arrange and Act
    String actualConvertResult =
        processModelAutoConfiguration
            .localDateTimeToStringConverter()
            .convert(LocalDate.of(1970, 1, 1).atStartOfDay());

    // Assert
    assertEquals("1970-01-01T00:00:00", actualConvertResult);
  }

  /**
   * Test {@link ProcessModelAutoConfiguration#localDateToStringConverter()}.
   *
   * <p>Method under test: {@link ProcessModelAutoConfiguration#localDateToStringConverter()}
   */
  @Test
  @DisplayName("Test localDateToStringConverter()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.runtime.model.impl.LocalDateToStringConverter ProcessModelAutoConfiguration.localDateToStringConverter()"
  })
  void testLocalDateToStringConverter() {
    // Arrange, Act and Assert
    assertEquals(
        "1970-01-01",
        processModelAutoConfiguration
            .localDateToStringConverter()
            .convert(LocalDate.of(1970, 1, 1)));
  }

  /**
   * Test {@link ProcessModelAutoConfiguration#sringToListConverter(ObjectMapper)}.
   *
   * <p>Method under test: {@link ProcessModelAutoConfiguration#sringToListConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test sringToListConverter(ObjectMapper)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.runtime.model.impl.StringToListConverter ProcessModelAutoConfiguration.sringToListConverter(ObjectMapper)"
  })
  void testSringToListConverter() {
    // Arrange, Act and Assert
    assertThrows(
        RuntimeException.class,
        () ->
            processModelAutoConfiguration
                .sringToListConverter(JsonMapper.builder().findAndAddModules().build())
                .convert("Source"));
  }

  /**
   * Test {@link ProcessModelAutoConfiguration#listToStringConverter(ObjectMapper)}.
   *
   * <p>Method under test: {@link ProcessModelAutoConfiguration#listToStringConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test listToStringConverter(ObjectMapper)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ListToStringConverter ProcessModelAutoConfiguration.listToStringConverter(ObjectMapper)"
  })
  void testListToStringConverter() {
    // Arrange and Act
    ListToStringConverter actualListToStringConverterResult =
        processModelAutoConfiguration.listToStringConverter(
            JsonMapper.builder().findAndAddModules().build());

    // Assert
    assertEquals("[]", actualListToStringConverterResult.convert(new ArrayList<>()));
  }

  /**
   * Test {@link ProcessModelAutoConfiguration#stringToSetConverter(ObjectMapper)}.
   *
   * <p>Method under test: {@link ProcessModelAutoConfiguration#stringToSetConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test stringToSetConverter(ObjectMapper)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.api.runtime.model.impl.StringToSetConverter ProcessModelAutoConfiguration.stringToSetConverter(ObjectMapper)"
  })
  void testStringToSetConverter() {
    // Arrange, Act and Assert
    assertThrows(
        RuntimeException.class,
        () ->
            processModelAutoConfiguration
                .stringToSetConverter(JsonMapper.builder().findAndAddModules().build())
                .convert("Source"));
  }

  /**
   * Test {@link ProcessModelAutoConfiguration#setToStringConverter(ObjectMapper)}.
   *
   * <p>Method under test: {@link ProcessModelAutoConfiguration#setToStringConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test setToStringConverter(ObjectMapper)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "SetToStringConverter ProcessModelAutoConfiguration.setToStringConverter(ObjectMapper)"
  })
  void testSetToStringConverter() {
    // Arrange and Act
    SetToStringConverter actualSetToStringConverterResult =
        processModelAutoConfiguration.setToStringConverter(
            JsonMapper.builder().findAndAddModules().build());

    // Assert
    assertEquals("[]", actualSetToStringConverterResult.convert(new HashSet<>()));
  }

  /**
   * Test {@link ProcessModelAutoConfiguration#stringToObjectValueConverter(ObjectMapper)}.
   *
   * <p>Method under test: {@link
   * ProcessModelAutoConfiguration#stringToObjectValueConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test stringToObjectValueConverter(ObjectMapper)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "StringToObjectValueConverter ProcessModelAutoConfiguration.stringToObjectValueConverter(ObjectMapper)"
  })
  void testStringToObjectValueConverter() throws JsonProcessingException {
    // Arrange and Act
    StringToObjectValueConverter actualStringToObjectValueConverterResult =
        processModelAutoConfiguration.stringToObjectValueConverter(
            JsonMapper.builder().findAndAddModules().build());
    JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();
    String source = jsonMapper.writeValueAsString(new ObjectValue("Object"));

    // Assert
    assertEquals("Object", actualStringToObjectValueConverterResult.convert(source).getObject());
  }

  /**
   * Test {@link ProcessModelAutoConfiguration#stringToObjectValueConverter(ObjectMapper)}.
   *
   * <p>Method under test: {@link
   * ProcessModelAutoConfiguration#stringToObjectValueConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test stringToObjectValueConverter(ObjectMapper)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "StringToObjectValueConverter ProcessModelAutoConfiguration.stringToObjectValueConverter(ObjectMapper)"
  })
  void testStringToObjectValueConverter2() throws JsonProcessingException {
    // Arrange
    Builder builderResult = JsonMapper.builder();
    builderResult.setDefaultTyping(new DefaultTypeResolverBuilder(DefaultTyping.JAVA_LANG_OBJECT));

    // Act
    StringToObjectValueConverter actualStringToObjectValueConverterResult =
        processModelAutoConfiguration.stringToObjectValueConverter(
            builderResult.findAndAddModules().build());
    JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();
    String source = jsonMapper.writeValueAsString(new ObjectValue("Object"));

    // Assert
    assertThrows(
        RuntimeException.class, () -> actualStringToObjectValueConverterResult.convert(source));
  }

  /**
   * Test {@link ProcessModelAutoConfiguration#stringToObjectValueConverter(ObjectMapper)}.
   *
   * <ul>
   *   <li>Then calls {@link StdTypeResolverBuilder#buildTypeDeserializer(DeserializationConfig,
   *       JavaType, Collection)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessModelAutoConfiguration#stringToObjectValueConverter(ObjectMapper)}
   */
  @Test
  @DisplayName(
      "Test stringToObjectValueConverter(ObjectMapper); then calls buildTypeDeserializer(DeserializationConfig, JavaType, Collection)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "StringToObjectValueConverter ProcessModelAutoConfiguration.stringToObjectValueConverter(ObjectMapper)"
  })
  void testStringToObjectValueConverter_thenCallsBuildTypeDeserializer()
      throws JsonProcessingException {
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

    // Act
    StringToObjectValueConverter actualStringToObjectValueConverterResult =
        processModelAutoConfiguration.stringToObjectValueConverter(
            builderResult2.findAndAddModules().build());
    JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();
    String source = jsonMapper.writeValueAsString(new ObjectValue("Object"));

    // Assert
    assertThrows(
        RuntimeException.class, () -> actualStringToObjectValueConverterResult.convert(source));
    verify(typer, atLeast(1))
        .buildTypeDeserializer(
            isA(DeserializationConfig.class),
            Mockito.<JavaType>any(),
            Mockito.<Collection<NamedType>>any());
    verify(typer, atLeast(1)).getDefaultImpl();
  }

  /**
   * Test {@link ProcessModelAutoConfiguration#objectValueToStringConverter(ObjectMapper)}.
   *
   * <p>Method under test: {@link
   * ProcessModelAutoConfiguration#objectValueToStringConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test objectValueToStringConverter(ObjectMapper)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectValueToStringConverter ProcessModelAutoConfiguration.objectValueToStringConverter(ObjectMapper)"
  })
  void testObjectValueToStringConverter() {
    // Arrange and Act
    ObjectValueToStringConverter actualObjectValueToStringConverterResult =
        processModelAutoConfiguration.objectValueToStringConverter(
            JsonMapper.builder().findAndAddModules().build());
    String actualConvertResult =
        actualObjectValueToStringConverterResult.convert(new ObjectValue("Object"));

    // Assert
    assertEquals("{\"object\":\"Object\"}", actualConvertResult);
  }
}
