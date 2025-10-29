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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.BaseSettings;
import com.fasterxml.jackson.databind.cfg.CoercionConfigs;
import com.fasterxml.jackson.databind.cfg.ConfigOverrides;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.databind.introspect.BasicClassIntrospector;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.SimpleMixInResolver;
import com.fasterxml.jackson.databind.jsontype.DefaultBaseTypeLimitingValidator;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.impl.AsArrayTypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.impl.ClassNameIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.StdSubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.fasterxml.jackson.databind.type.PlaceholderForType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.RootNameLookup;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import org.activiti.api.runtime.model.impl.DateToStringConverter;
import org.activiti.api.runtime.model.impl.JsonNodeToStringConverter;
import org.activiti.api.runtime.model.impl.ListToStringConverter;
import org.activiti.api.runtime.model.impl.LocalDateTimeToStringConverter;
import org.activiti.api.runtime.model.impl.LocalDateToStringConverter;
import org.activiti.api.runtime.model.impl.MapToStringConverter;
import org.activiti.api.runtime.model.impl.ObjectValue;
import org.activiti.api.runtime.model.impl.ObjectValueToStringConverter;
import org.activiti.api.runtime.model.impl.SetToStringConverter;
import org.activiti.api.runtime.model.impl.StringToJsonNodeConverter;
import org.activiti.api.runtime.model.impl.StringToObjectValueConverter;
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
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ProcessModelAutoConfigurationDiffblueTest {
  @MockBean
  private Converter<Object, Object> converter;

  @Autowired
  private ProcessModelAutoConfiguration processModelAutoConfiguration;

  @Autowired
  private Set<Converter<Object, Object>> set;

  /**
   * Method under test: {@link ProcessModelAutoConfiguration#conversionService()}
   */
  @Test
  void testConversionService() {
    // Arrange, Act and Assert
    assertTrue(processModelAutoConfiguration.conversionService() instanceof ApplicationConversionService);
  }

  /**
   * Method under test:
   * {@link ProcessModelAutoConfiguration#stringToMapConverter(ObjectMapper)}
   */
  @Test
  void testStringToMapConverter() throws JsonProcessingException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessModelAutoConfiguration processModelAutoConfiguration = new ProcessModelAutoConfiguration();
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    when(objectMapper.readValue(Mockito.<String>any(), Mockito.<JavaType>any())).thenReturn(objectObjectMap);
    when(objectMapper.getTypeFactory()).thenReturn(TypeFactory.defaultInstance());

    // Act
    Map<String, Object> actualConvertResult = processModelAutoConfiguration.stringToMapConverter(objectMapper)
        .convert("Source");

    // Assert
    verify(objectMapper).getTypeFactory();
    verify(objectMapper).readValue(eq("Source"), isA(JavaType.class));
    assertTrue(actualConvertResult.isEmpty());
    assertSame(objectObjectMap, actualConvertResult);
  }

  /**
   * Method under test:
   * {@link ProcessModelAutoConfiguration#mapToStringConverter(ObjectMapper)}
   */
  @Test
  void testMapToStringConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessModelAutoConfiguration processModelAutoConfiguration = new ProcessModelAutoConfiguration();

    // Act
    MapToStringConverter actualMapToStringConverterResult = processModelAutoConfiguration
        .mapToStringConverter(new ObjectMapper());

    // Assert
    assertEquals("{}", actualMapToStringConverterResult.convert(new HashMap<>()));
  }

  /**
   * Method under test:
   * {@link ProcessModelAutoConfiguration#mapToStringConverter(ObjectMapper)}
   */
  @Test
  void testMapToStringConverter2() throws JsonProcessingException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessModelAutoConfiguration processModelAutoConfiguration = new ProcessModelAutoConfiguration();
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");

    // Act
    MapToStringConverter actualMapToStringConverterResult = processModelAutoConfiguration
        .mapToStringConverter(objectMapper);
    String actualConvertResult = actualMapToStringConverterResult.convert(new HashMap<>());

    // Assert
    verify(objectMapper).writeValueAsString(isA(Object.class));
    assertEquals("42", actualConvertResult);
  }

  /**
   * Method under test:
   * {@link ProcessModelAutoConfiguration#stringToJsonNodeConverter(ObjectMapper)}
   */
  @Test
  void testStringToJsonNodeConverter() throws JsonProcessingException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessModelAutoConfiguration processModelAutoConfiguration = new ProcessModelAutoConfiguration();

    // Act
    StringToJsonNodeConverter actualStringToJsonNodeConverterResult = processModelAutoConfiguration
        .stringToJsonNodeConverter(new ObjectMapper());
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode actualConvertResult = actualStringToJsonNodeConverterResult
        .convert(objectMapper.writeValueAsString(MissingNode.getInstance()));

    // Assert
    assertSame(((NullNode) actualConvertResult).instance, actualConvertResult);
  }

  /**
   * Method under test:
   * {@link ProcessModelAutoConfiguration#stringToJsonNodeConverter(ObjectMapper)}
   */
  @Test
  void testStringToJsonNodeConverter2() throws JsonProcessingException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessModelAutoConfiguration processModelAutoConfiguration = new ProcessModelAutoConfiguration();

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.addHandler(mock(DeserializationProblemHandler.class));

    // Act
    StringToJsonNodeConverter actualStringToJsonNodeConverterResult = processModelAutoConfiguration
        .stringToJsonNodeConverter(objectMapper);
    ObjectMapper objectMapper2 = new ObjectMapper();
    JsonNode actualConvertResult = actualStringToJsonNodeConverterResult
        .convert(objectMapper2.writeValueAsString(MissingNode.getInstance()));

    // Assert
    assertSame(((NullNode) actualConvertResult).instance, actualConvertResult);
  }

  /**
   * Method under test:
   * {@link ProcessModelAutoConfiguration#stringToJsonNodeConverter(ObjectMapper)}
   */
  @Test
  void testStringToJsonNodeConverter3() throws JsonProcessingException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessModelAutoConfiguration processModelAutoConfiguration = new ProcessModelAutoConfiguration();

    ObjectMapper objectMapper = new ObjectMapper();
    BasicClassIntrospector ci = new BasicClassIntrospector();
    JacksonAnnotationIntrospector ai = new JacksonAnnotationIntrospector();
    PropertyNamingStrategy pns = new PropertyNamingStrategy();
    TypeFactory tf = TypeFactory.defaultInstance();
    ObjectMapper.DefaultTypeResolverBuilder typer = new ObjectMapper.DefaultTypeResolverBuilder(
        ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");
    Locale locale = Locale.getDefault();
    TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
    Base64Variant defaultBase64 = Base64Variants.getDefaultVariant();
    BaseSettings base = new BaseSettings(ci, ai, pns, tf, typer, dateFormat, null, locale, tz, defaultBase64,
        new DefaultBaseTypeLimitingValidator());

    StdSubtypeResolver str = new StdSubtypeResolver();
    SimpleMixInResolver mixins = new SimpleMixInResolver(null);
    RootNameLookup rootNames = new RootNameLookup();
    ConfigOverrides configOverrides = new ConfigOverrides();
    objectMapper.setConfig(
        new DeserializationConfig(base, str, mixins, rootNames, configOverrides, new CoercionConfigs(), null));
    objectMapper.addHandler(mock(DeserializationProblemHandler.class));

    // Act
    StringToJsonNodeConverter actualStringToJsonNodeConverterResult = processModelAutoConfiguration
        .stringToJsonNodeConverter(objectMapper);
    ObjectMapper objectMapper2 = new ObjectMapper();
    JsonNode actualConvertResult = actualStringToJsonNodeConverterResult
        .convert(objectMapper2.writeValueAsString(MissingNode.getInstance()));

    // Assert
    assertSame(((NullNode) actualConvertResult).instance, actualConvertResult);
  }

  /**
   * Method under test:
   * {@link ProcessModelAutoConfiguration#stringToJsonNodeConverter(ObjectMapper)}
   */
  @Test
  void testStringToJsonNodeConverter4() throws JsonProcessingException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessModelAutoConfiguration processModelAutoConfiguration = new ProcessModelAutoConfiguration();
    TypeFactory tf = mock(TypeFactory.class);
    when(tf.constructType(Mockito.<Type>any())).thenReturn(new PlaceholderForType(1));
    BasicClassIntrospector ci = new BasicClassIntrospector();
    JacksonAnnotationIntrospector ai = new JacksonAnnotationIntrospector();
    PropertyNamingStrategy pns = new PropertyNamingStrategy();
    ObjectMapper.DefaultTypeResolverBuilder typer = new ObjectMapper.DefaultTypeResolverBuilder(
        ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");
    Locale locale = Locale.getDefault();
    TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
    Base64Variant defaultBase64 = Base64Variants.getDefaultVariant();
    BaseSettings base = new BaseSettings(ci, ai, pns, tf, typer, dateFormat, null, locale, tz, defaultBase64,
        new DefaultBaseTypeLimitingValidator());

    StdSubtypeResolver str = new StdSubtypeResolver();
    SimpleMixInResolver mixins = new SimpleMixInResolver(null);
    RootNameLookup rootNames = new RootNameLookup();
    ConfigOverrides configOverrides = new ConfigOverrides();
    DeserializationConfig config = new DeserializationConfig(base, str, mixins, rootNames, configOverrides,
        new CoercionConfigs(), null);

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setConfig(config);
    objectMapper.addHandler(mock(DeserializationProblemHandler.class));

    // Act
    StringToJsonNodeConverter actualStringToJsonNodeConverterResult = processModelAutoConfiguration
        .stringToJsonNodeConverter(objectMapper);
    ObjectMapper objectMapper2 = new ObjectMapper();
    JsonNode actualConvertResult = actualStringToJsonNodeConverterResult
        .convert(objectMapper2.writeValueAsString(MissingNode.getInstance()));

    // Assert
    verify(tf).constructType(isA(Type.class));
    assertSame(((NullNode) actualConvertResult).instance, actualConvertResult);
  }

  /**
   * Method under test:
   * {@link ProcessModelAutoConfiguration#jsonNodeToStringConverter(ObjectMapper)}
   */
  @Test
  void testJsonNodeToStringConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessModelAutoConfiguration processModelAutoConfiguration = new ProcessModelAutoConfiguration();

    // Act
    JsonNodeToStringConverter actualJsonNodeToStringConverterResult = processModelAutoConfiguration
        .jsonNodeToStringConverter(new ObjectMapper());
    MissingNode source = MissingNode.getInstance();
    String actualConvertResult = actualJsonNodeToStringConverterResult.convert(source);

    // Assert
    assertTrue(source.traverse() instanceof TreeTraversingParser);
    assertEquals("null", actualConvertResult);
  }

  /**
   * Method under test:
   * {@link ProcessModelAutoConfiguration#jsonNodeToStringConverter(ObjectMapper)}
   */
  @Test
  void testJsonNodeToStringConverter2() throws JsonProcessingException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessModelAutoConfiguration processModelAutoConfiguration = new ProcessModelAutoConfiguration();
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");

    // Act
    JsonNodeToStringConverter actualJsonNodeToStringConverterResult = processModelAutoConfiguration
        .jsonNodeToStringConverter(objectMapper);
    MissingNode source = MissingNode.getInstance();
    String actualConvertResult = actualJsonNodeToStringConverterResult.convert(source);

    // Assert
    verify(objectMapper).writeValueAsString(isA(Object.class));
    assertTrue(source.traverse() instanceof TreeTraversingParser);
    assertEquals("42", actualConvertResult);
  }

  /**
   * Method under test:
   * {@link ProcessModelAutoConfiguration#dateToStringConverter()}
   */
  @Test
  void testDateToStringConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    DateToStringConverter actualDateToStringConverterResult = (new ProcessModelAutoConfiguration())
        .dateToStringConverter();

    // Assert
    assertEquals("1970-01-01T00:00:00Z", actualDateToStringConverterResult
        .convert(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())));
  }

  /**
   * Method under test:
   * {@link ProcessModelAutoConfiguration#dateToStringConverter()}
   */
  @Test
  void testDateToStringConverter2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    DateToStringConverter actualDateToStringConverterResult = (new ProcessModelAutoConfiguration())
        .dateToStringConverter();
    java.sql.Date source = mock(java.sql.Date.class);
    when(source.toInstant()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    String actualConvertResult = actualDateToStringConverterResult.convert(source);

    // Assert
    verify(source).toInstant();
    assertEquals("1970-01-01T00:00:00Z", actualConvertResult);
  }

  /**
   * Method under test:
   * {@link ProcessModelAutoConfiguration#localDateTimeToStringConverter()}
   */
  @Test
  void testLocalDateTimeToStringConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    LocalDateTimeToStringConverter actualLocalDateTimeToStringConverterResult = (new ProcessModelAutoConfiguration())
        .localDateTimeToStringConverter();

    // Assert
    assertEquals("1970-01-01T00:00:00",
        actualLocalDateTimeToStringConverterResult.convert(LocalDate.of(1970, 1, 1).atStartOfDay()));
  }

  /**
   * Method under test:
   * {@link ProcessModelAutoConfiguration#localDateToStringConverter()}
   */
  @Test
  void testLocalDateToStringConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    LocalDateToStringConverter actualLocalDateToStringConverterResult = (new ProcessModelAutoConfiguration())
        .localDateToStringConverter();

    // Assert
    assertEquals("1970-01-01", actualLocalDateToStringConverterResult.convert(LocalDate.of(1970, 1, 1)));
  }

  /**
   * Method under test:
   * {@link ProcessModelAutoConfiguration#sringToListConverter(ObjectMapper)}
   */
  @Test
  void testSringToListConverter() throws JsonProcessingException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessModelAutoConfiguration processModelAutoConfiguration = new ProcessModelAutoConfiguration();
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    ArrayList<Object> objectList = new ArrayList<>();
    when(objectMapper.readValue(Mockito.<String>any(), Mockito.<JavaType>any())).thenReturn(objectList);
    when(objectMapper.getTypeFactory()).thenReturn(TypeFactory.defaultInstance());

    // Act
    List<Object> actualConvertResult = processModelAutoConfiguration.sringToListConverter(objectMapper)
        .convert("Source");

    // Assert
    verify(objectMapper).getTypeFactory();
    verify(objectMapper).readValue(eq("Source"), isA(JavaType.class));
    assertTrue(actualConvertResult.isEmpty());
    assertSame(objectList, actualConvertResult);
  }

  /**
   * Method under test:
   * {@link ProcessModelAutoConfiguration#listToStringConverter(ObjectMapper)}
   */
  @Test
  void testListToStringConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessModelAutoConfiguration processModelAutoConfiguration = new ProcessModelAutoConfiguration();

    // Act
    ListToStringConverter actualListToStringConverterResult = processModelAutoConfiguration
        .listToStringConverter(new ObjectMapper());

    // Assert
    assertEquals("[]", actualListToStringConverterResult.convert(new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link ProcessModelAutoConfiguration#listToStringConverter(ObjectMapper)}
   */
  @Test
  void testListToStringConverter2() throws JsonProcessingException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessModelAutoConfiguration processModelAutoConfiguration = new ProcessModelAutoConfiguration();
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");

    // Act
    ListToStringConverter actualListToStringConverterResult = processModelAutoConfiguration
        .listToStringConverter(objectMapper);
    String actualConvertResult = actualListToStringConverterResult.convert(new ArrayList<>());

    // Assert
    verify(objectMapper).writeValueAsString(isA(Object.class));
    assertEquals("42", actualConvertResult);
  }

  /**
   * Method under test:
   * {@link ProcessModelAutoConfiguration#stringToSetConverter(ObjectMapper)}
   */
  @Test
  void testStringToSetConverter() throws JsonProcessingException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessModelAutoConfiguration processModelAutoConfiguration = new ProcessModelAutoConfiguration();
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    HashSet<Object> objectSet = new HashSet<>();
    when(objectMapper.readValue(Mockito.<String>any(), Mockito.<JavaType>any())).thenReturn(objectSet);
    when(objectMapper.getTypeFactory()).thenReturn(TypeFactory.defaultInstance());

    // Act
    Set<Object> actualConvertResult = processModelAutoConfiguration.stringToSetConverter(objectMapper)
        .convert("Source");

    // Assert
    verify(objectMapper).getTypeFactory();
    verify(objectMapper).readValue(eq("Source"), isA(JavaType.class));
    assertTrue(actualConvertResult.isEmpty());
    assertSame(objectSet, actualConvertResult);
  }

  /**
   * Method under test:
   * {@link ProcessModelAutoConfiguration#setToStringConverter(ObjectMapper)}
   */
  @Test
  void testSetToStringConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessModelAutoConfiguration processModelAutoConfiguration = new ProcessModelAutoConfiguration();
    ObjectMapper objectMapper = new ObjectMapper();

    // Act
    SetToStringConverter actualSetToStringConverterResult = processModelAutoConfiguration
        .setToStringConverter(objectMapper);

    // Assert
    assertEquals("[]", actualSetToStringConverterResult.convert(new HashSet<>()));
    assertTrue(objectMapper.getRegisteredModuleIds().isEmpty());
  }

  /**
   * Method under test:
   * {@link ProcessModelAutoConfiguration#setToStringConverter(ObjectMapper)}
   */
  @Test
  void testSetToStringConverter2() throws JsonProcessingException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessModelAutoConfiguration processModelAutoConfiguration = new ProcessModelAutoConfiguration();
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");

    // Act
    SetToStringConverter actualSetToStringConverterResult = processModelAutoConfiguration
        .setToStringConverter(objectMapper);
    String actualConvertResult = actualSetToStringConverterResult.convert(new HashSet<>());

    // Assert
    verify(objectMapper).writeValueAsString(isA(Object.class));
    assertEquals("42", actualConvertResult);
  }

  /**
   * Method under test:
   * {@link ProcessModelAutoConfiguration#stringToObjectValueConverter(ObjectMapper)}
   */
  @Test
  void testStringToObjectValueConverter() throws JsonProcessingException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessModelAutoConfiguration processModelAutoConfiguration = new ProcessModelAutoConfiguration();

    // Act
    StringToObjectValueConverter actualStringToObjectValueConverterResult = processModelAutoConfiguration
        .stringToObjectValueConverter(new ObjectMapper());
    ObjectMapper objectMapper = new ObjectMapper();

    // Assert
    assertEquals("Object",
        actualStringToObjectValueConverterResult.convert(objectMapper.writeValueAsString(new ObjectValue("Object")))
            .getObject());
  }

  /**
   * Method under test:
   * {@link ProcessModelAutoConfiguration#stringToObjectValueConverter(ObjectMapper)}
   */
  @Test
  void testStringToObjectValueConverter2() throws JsonProcessingException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessModelAutoConfiguration processModelAutoConfiguration = new ProcessModelAutoConfiguration();

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.addHandler(mock(DeserializationProblemHandler.class));

    // Act
    StringToObjectValueConverter actualStringToObjectValueConverterResult = processModelAutoConfiguration
        .stringToObjectValueConverter(objectMapper);
    ObjectMapper objectMapper2 = new ObjectMapper();

    // Assert
    assertEquals("Object",
        actualStringToObjectValueConverterResult.convert(objectMapper2.writeValueAsString(new ObjectValue("Object")))
            .getObject());
  }

  /**
   * Method under test:
   * {@link ProcessModelAutoConfiguration#stringToObjectValueConverter(ObjectMapper)}
   */
  @Test
  void testStringToObjectValueConverter3() throws JsonProcessingException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessModelAutoConfiguration processModelAutoConfiguration = new ProcessModelAutoConfiguration();

    ObjectMapper objectMapper = new ObjectMapper();
    BasicClassIntrospector ci = new BasicClassIntrospector();
    JacksonAnnotationIntrospector ai = new JacksonAnnotationIntrospector();
    PropertyNamingStrategy pns = new PropertyNamingStrategy();
    TypeFactory tf = TypeFactory.defaultInstance();
    ObjectMapper.DefaultTypeResolverBuilder typer = new ObjectMapper.DefaultTypeResolverBuilder(
        ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");
    Locale locale = Locale.getDefault();
    TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
    Base64Variant defaultBase64 = Base64Variants.getDefaultVariant();
    BaseSettings base = new BaseSettings(ci, ai, pns, tf, typer, dateFormat, null, locale, tz, defaultBase64,
        new DefaultBaseTypeLimitingValidator());

    StdSubtypeResolver str = new StdSubtypeResolver();
    SimpleMixInResolver mixins = new SimpleMixInResolver(null);
    RootNameLookup rootNames = new RootNameLookup();
    ConfigOverrides configOverrides = new ConfigOverrides();
    objectMapper.setConfig(
        new DeserializationConfig(base, str, mixins, rootNames, configOverrides, new CoercionConfigs(), null));
    objectMapper.addHandler(mock(DeserializationProblemHandler.class));

    // Act
    StringToObjectValueConverter actualStringToObjectValueConverterResult = processModelAutoConfiguration
        .stringToObjectValueConverter(objectMapper);
    ObjectMapper objectMapper2 = new ObjectMapper();

    // Assert
    assertThrows(RuntimeException.class, () -> actualStringToObjectValueConverterResult
        .convert(objectMapper2.writeValueAsString(new ObjectValue("Object"))));
  }

  /**
   * Method under test:
   * {@link ProcessModelAutoConfiguration#objectValueToStringConverter(ObjectMapper)}
   */
  @Test
  void testObjectValueToStringConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessModelAutoConfiguration processModelAutoConfiguration = new ProcessModelAutoConfiguration();

    // Act
    ObjectValueToStringConverter actualObjectValueToStringConverterResult = processModelAutoConfiguration
        .objectValueToStringConverter(new ObjectMapper());

    // Assert
    assertEquals("{\"object\":\"Object\"}",
        actualObjectValueToStringConverterResult.convert(new ObjectValue("Object")));
  }

  /**
   * Method under test:
   * {@link ProcessModelAutoConfiguration#objectValueToStringConverter(ObjectMapper)}
   */
  @Test
  void testObjectValueToStringConverter2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessModelAutoConfiguration processModelAutoConfiguration = new ProcessModelAutoConfiguration();

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.addHandler(mock(DeserializationProblemHandler.class));

    // Act
    ObjectValueToStringConverter actualObjectValueToStringConverterResult = processModelAutoConfiguration
        .objectValueToStringConverter(objectMapper);

    // Assert
    assertEquals("{\"object\":\"Object\"}",
        actualObjectValueToStringConverterResult.convert(new ObjectValue("Object")));
  }

  /**
   * Method under test:
   * {@link ProcessModelAutoConfiguration#objectValueToStringConverter(ObjectMapper)}
   */
  @Test
  void testObjectValueToStringConverter3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessModelAutoConfiguration processModelAutoConfiguration = new ProcessModelAutoConfiguration();

    ObjectMapper objectMapper = new ObjectMapper();
    BasicClassIntrospector ci = new BasicClassIntrospector();
    JacksonAnnotationIntrospector ai = new JacksonAnnotationIntrospector();
    PropertyNamingStrategy pns = new PropertyNamingStrategy();
    TypeFactory tf = TypeFactory.defaultInstance();
    ObjectMapper.DefaultTypeResolverBuilder typer = new ObjectMapper.DefaultTypeResolverBuilder(
        ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");
    Locale locale = Locale.getDefault();
    TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
    Base64Variant defaultBase64 = Base64Variants.getDefaultVariant();
    BaseSettings base = new BaseSettings(ci, ai, pns, tf, typer, dateFormat, null, locale, tz, defaultBase64,
        new DefaultBaseTypeLimitingValidator());

    StdSubtypeResolver str = new StdSubtypeResolver();
    SimpleMixInResolver mixins = new SimpleMixInResolver(null);
    RootNameLookup rootNames = new RootNameLookup();
    ConfigOverrides configOverrides = new ConfigOverrides();
    objectMapper.setConfig(
        new DeserializationConfig(base, str, mixins, rootNames, configOverrides, new CoercionConfigs(), null));
    objectMapper.addHandler(mock(DeserializationProblemHandler.class));

    // Act
    ObjectValueToStringConverter actualObjectValueToStringConverterResult = processModelAutoConfiguration
        .objectValueToStringConverter(objectMapper);

    // Assert
    assertThrows(RuntimeException.class,
        () -> actualObjectValueToStringConverterResult.convert(new ObjectValue("Object")));
  }

  /**
   * Method under test:
   * {@link ProcessModelAutoConfiguration#objectValueToStringConverter(ObjectMapper)}
   */
  @Test
  void testObjectValueToStringConverter4() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessModelAutoConfiguration processModelAutoConfiguration = new ProcessModelAutoConfiguration();
    StdTypeResolverBuilder typer = mock(StdTypeResolverBuilder.class);
    PlaceholderForType bt = new PlaceholderForType(1);
    PlaceholderForType baseType = new PlaceholderForType(1);
    TypeFactory typeFactory = TypeFactory.defaultInstance();
    ClassNameIdResolver idRes = new ClassNameIdResolver(baseType, typeFactory, new DefaultBaseTypeLimitingValidator());

    AsArrayTypeDeserializer src = new AsArrayTypeDeserializer(bt, idRes, "Type Property Name", true,
        new PlaceholderForType(1));

    when(typer.buildTypeDeserializer(Mockito.<DeserializationConfig>any(), Mockito.<JavaType>any(),
        Mockito.<Collection<NamedType>>any())).thenReturn(new AsArrayTypeDeserializer(src, new BeanProperty.Bogus()));
    Class<Object> forNameResult = Object.class;
    Mockito.<Class<?>>when(typer.getDefaultImpl()).thenReturn(forNameResult);
    BasicClassIntrospector ci = new BasicClassIntrospector();
    JacksonAnnotationIntrospector ai = new JacksonAnnotationIntrospector();
    PropertyNamingStrategy pns = new PropertyNamingStrategy();
    TypeFactory tf = TypeFactory.defaultInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");
    Locale locale = Locale.getDefault();
    TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
    Base64Variant defaultBase64 = Base64Variants.getDefaultVariant();
    BaseSettings base = new BaseSettings(ci, ai, pns, tf, typer, dateFormat, null, locale, tz, defaultBase64,
        new DefaultBaseTypeLimitingValidator());

    StdSubtypeResolver str = new StdSubtypeResolver();
    SimpleMixInResolver mixins = new SimpleMixInResolver(null);
    RootNameLookup rootNames = new RootNameLookup();
    ConfigOverrides configOverrides = new ConfigOverrides();
    DeserializationConfig config = new DeserializationConfig(base, str, mixins, rootNames, configOverrides,
        new CoercionConfigs(), null);

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setConfig(config);
    objectMapper.addHandler(mock(DeserializationProblemHandler.class));

    // Act
    ObjectValueToStringConverter actualObjectValueToStringConverterResult = processModelAutoConfiguration
        .objectValueToStringConverter(objectMapper);
    String actualConvertResult = actualObjectValueToStringConverterResult.convert(new ObjectValue("Object"));

    // Assert
    verify(typer, atLeast(1)).buildTypeDeserializer(isA(DeserializationConfig.class), Mockito.<JavaType>any(),
        Mockito.<Collection<NamedType>>any());
    verify(typer, atLeast(1)).getDefaultImpl();
    assertEquals("{\"Type Property Name\":\"java.lang.Object\",\"object\":\"Object\"}", actualConvertResult);
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link ProcessModelAutoConfiguration}
   */
  @Test
  void testNewProcessModelAutoConfiguration() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange, Act and Assert
    assertTrue((new ProcessModelAutoConfiguration()).conversionService() instanceof ApplicationConversionService);
  }
}
