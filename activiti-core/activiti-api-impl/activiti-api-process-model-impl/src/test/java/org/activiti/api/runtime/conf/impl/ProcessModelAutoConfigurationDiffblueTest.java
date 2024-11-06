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
import org.junit.jupiter.api.DisplayName;
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
   * Test {@link ProcessModelAutoConfiguration#conversionService()}.
   * <p>
   * Method under test: {@link ProcessModelAutoConfiguration#conversionService()}
   */
  @Test
  @DisplayName("Test conversionService()")
  void testConversionService() {
    // Arrange, Act and Assert
    assertTrue(processModelAutoConfiguration.conversionService() instanceof ApplicationConversionService);
  }

  /**
   * Test
   * {@link ProcessModelAutoConfiguration#stringToMapConverter(ObjectMapper)}.
   * <p>
   * Method under test:
   * {@link ProcessModelAutoConfiguration#stringToMapConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test stringToMapConverter(ObjectMapper)")
  void testStringToMapConverter() throws JsonProcessingException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessModelAutoConfiguration processModelAutoConfiguration = new ProcessModelAutoConfiguration();
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.readValue(Mockito.<String>any(), Mockito.<JavaType>any())).thenReturn(new HashMap<>());
    when(objectMapper.getTypeFactory()).thenReturn(TypeFactory.defaultInstance());

    // Act
    Map<String, Object> actualConvertResult = processModelAutoConfiguration.stringToMapConverter(objectMapper)
        .convert("Source");

    // Assert
    verify(objectMapper).getTypeFactory();
    verify(objectMapper).readValue(eq("Source"), isA(JavaType.class));
    assertTrue(actualConvertResult.isEmpty());
  }

  /**
   * Test
   * {@link ProcessModelAutoConfiguration#mapToStringConverter(ObjectMapper)}.
   * <p>
   * Method under test:
   * {@link ProcessModelAutoConfiguration#mapToStringConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test mapToStringConverter(ObjectMapper)")
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
   * Test
   * {@link ProcessModelAutoConfiguration#mapToStringConverter(ObjectMapper)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>Then return convert {@link HashMap#HashMap()} is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessModelAutoConfiguration#mapToStringConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test mapToStringConverter(ObjectMapper); given '42'; then return convert HashMap() is '42'")
  void testMapToStringConverter_given42_thenReturnConvertHashMapIs42() throws JsonProcessingException {
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
   * Test
   * {@link ProcessModelAutoConfiguration#stringToJsonNodeConverter(ObjectMapper)}.
   * <p>
   * Method under test:
   * {@link ProcessModelAutoConfiguration#stringToJsonNodeConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test stringToJsonNodeConverter(ObjectMapper)")
  void testStringToJsonNodeConverter() throws JsonProcessingException {
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
   * Test
   * {@link ProcessModelAutoConfiguration#stringToJsonNodeConverter(ObjectMapper)}.
   * <ul>
   *   <li>Given {@link DeserializationProblemHandler}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessModelAutoConfiguration#stringToJsonNodeConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test stringToJsonNodeConverter(ObjectMapper); given DeserializationProblemHandler")
  void testStringToJsonNodeConverter_givenDeserializationProblemHandler() throws JsonProcessingException {
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
   * Test
   * {@link ProcessModelAutoConfiguration#stringToJsonNodeConverter(ObjectMapper)}.
   * <ul>
   *   <li>Then calls {@link TypeFactory#constructType(Type)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessModelAutoConfiguration#stringToJsonNodeConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test stringToJsonNodeConverter(ObjectMapper); then calls constructType(Type)")
  void testStringToJsonNodeConverter_thenCallsConstructType() throws JsonProcessingException {
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
   * Test
   * {@link ProcessModelAutoConfiguration#stringToJsonNodeConverter(ObjectMapper)}.
   * <ul>
   *   <li>When {@link ObjectMapper#ObjectMapper()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessModelAutoConfiguration#stringToJsonNodeConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test stringToJsonNodeConverter(ObjectMapper); when ObjectMapper()")
  void testStringToJsonNodeConverter_whenObjectMapper() throws JsonProcessingException {
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
   * Test
   * {@link ProcessModelAutoConfiguration#jsonNodeToStringConverter(ObjectMapper)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>Then return convert Instance is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessModelAutoConfiguration#jsonNodeToStringConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test jsonNodeToStringConverter(ObjectMapper); given '42'; then return convert Instance is '42'")
  void testJsonNodeToStringConverter_given42_thenReturnConvertInstanceIs42() throws JsonProcessingException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessModelAutoConfiguration processModelAutoConfiguration = new ProcessModelAutoConfiguration();
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.writeValueAsString(Mockito.<Object>any())).thenReturn("42");

    // Act
    JsonNodeToStringConverter actualJsonNodeToStringConverterResult = processModelAutoConfiguration
        .jsonNodeToStringConverter(objectMapper);
    String actualConvertResult = actualJsonNodeToStringConverterResult.convert(MissingNode.getInstance());

    // Assert
    verify(objectMapper).writeValueAsString(isA(Object.class));
    assertEquals("42", actualConvertResult);
  }

  /**
   * Test
   * {@link ProcessModelAutoConfiguration#jsonNodeToStringConverter(ObjectMapper)}.
   * <ul>
   *   <li>Then Instance traverse {@link TreeTraversingParser}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessModelAutoConfiguration#jsonNodeToStringConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test jsonNodeToStringConverter(ObjectMapper); then Instance traverse TreeTraversingParser")
  void testJsonNodeToStringConverter_thenInstanceTraverseTreeTraversingParser() {
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
   * Test {@link ProcessModelAutoConfiguration#dateToStringConverter()}.
   * <p>
   * Method under test:
   * {@link ProcessModelAutoConfiguration#dateToStringConverter()}
   */
  @Test
  @DisplayName("Test dateToStringConverter()")
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
   * Test {@link ProcessModelAutoConfiguration#dateToStringConverter()}.
   * <ul>
   *   <li>Then return convert {@link Date} is {@code 1970-01-01T00:00:00Z}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessModelAutoConfiguration#dateToStringConverter()}
   */
  @Test
  @DisplayName("Test dateToStringConverter(); then return convert Date is '1970-01-01T00:00:00Z'")
  void testDateToStringConverter_thenReturnConvertDateIs19700101t000000z() {
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
   * Test {@link ProcessModelAutoConfiguration#localDateTimeToStringConverter()}.
   * <p>
   * Method under test:
   * {@link ProcessModelAutoConfiguration#localDateTimeToStringConverter()}
   */
  @Test
  @DisplayName("Test localDateTimeToStringConverter()")
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
   * Test {@link ProcessModelAutoConfiguration#localDateToStringConverter()}.
   * <p>
   * Method under test:
   * {@link ProcessModelAutoConfiguration#localDateToStringConverter()}
   */
  @Test
  @DisplayName("Test localDateToStringConverter()")
  void testLocalDateToStringConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    LocalDateToStringConverter actualLocalDateToStringConverterResult = (new ProcessModelAutoConfiguration())
        .localDateToStringConverter();

    // Assert
    assertEquals("1970-01-01", actualLocalDateToStringConverterResult.convert(LocalDate.of(1970, 1, 1)));
  }

  /**
   * Test
   * {@link ProcessModelAutoConfiguration#sringToListConverter(ObjectMapper)}.
   * <p>
   * Method under test:
   * {@link ProcessModelAutoConfiguration#sringToListConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test sringToListConverter(ObjectMapper)")
  void testSringToListConverter() throws JsonProcessingException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessModelAutoConfiguration processModelAutoConfiguration = new ProcessModelAutoConfiguration();
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.readValue(Mockito.<String>any(), Mockito.<JavaType>any())).thenReturn(new ArrayList<>());
    when(objectMapper.getTypeFactory()).thenReturn(TypeFactory.defaultInstance());

    // Act
    List<Object> actualConvertResult = processModelAutoConfiguration.sringToListConverter(objectMapper)
        .convert("Source");

    // Assert
    verify(objectMapper).getTypeFactory();
    verify(objectMapper).readValue(eq("Source"), isA(JavaType.class));
    assertTrue(actualConvertResult.isEmpty());
  }

  /**
   * Test
   * {@link ProcessModelAutoConfiguration#listToStringConverter(ObjectMapper)}.
   * <p>
   * Method under test:
   * {@link ProcessModelAutoConfiguration#listToStringConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test listToStringConverter(ObjectMapper)")
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
   * Test
   * {@link ProcessModelAutoConfiguration#listToStringConverter(ObjectMapper)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>Then return convert {@link ArrayList#ArrayList()} is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessModelAutoConfiguration#listToStringConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test listToStringConverter(ObjectMapper); given '42'; then return convert ArrayList() is '42'")
  void testListToStringConverter_given42_thenReturnConvertArrayListIs42() throws JsonProcessingException {
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
   * Test
   * {@link ProcessModelAutoConfiguration#stringToSetConverter(ObjectMapper)}.
   * <p>
   * Method under test:
   * {@link ProcessModelAutoConfiguration#stringToSetConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test stringToSetConverter(ObjectMapper)")
  void testStringToSetConverter() throws JsonProcessingException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessModelAutoConfiguration processModelAutoConfiguration = new ProcessModelAutoConfiguration();
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    when(objectMapper.readValue(Mockito.<String>any(), Mockito.<JavaType>any())).thenReturn(new HashSet<>());
    when(objectMapper.getTypeFactory()).thenReturn(TypeFactory.defaultInstance());

    // Act
    Set<Object> actualConvertResult = processModelAutoConfiguration.stringToSetConverter(objectMapper)
        .convert("Source");

    // Assert
    verify(objectMapper).getTypeFactory();
    verify(objectMapper).readValue(eq("Source"), isA(JavaType.class));
    assertTrue(actualConvertResult.isEmpty());
  }

  /**
   * Test
   * {@link ProcessModelAutoConfiguration#setToStringConverter(ObjectMapper)}.
   * <p>
   * Method under test:
   * {@link ProcessModelAutoConfiguration#setToStringConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test setToStringConverter(ObjectMapper)")
  void testSetToStringConverter() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessModelAutoConfiguration processModelAutoConfiguration = new ProcessModelAutoConfiguration();

    // Act
    SetToStringConverter actualSetToStringConverterResult = processModelAutoConfiguration
        .setToStringConverter(new ObjectMapper());

    // Assert
    assertEquals("[]", actualSetToStringConverterResult.convert(new HashSet<>()));
  }

  /**
   * Test
   * {@link ProcessModelAutoConfiguration#setToStringConverter(ObjectMapper)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>Then return convert {@link HashSet#HashSet()} is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessModelAutoConfiguration#setToStringConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test setToStringConverter(ObjectMapper); given '42'; then return convert HashSet() is '42'")
  void testSetToStringConverter_given42_thenReturnConvertHashSetIs42() throws JsonProcessingException {
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
   * Test
   * {@link ProcessModelAutoConfiguration#stringToObjectValueConverter(ObjectMapper)}.
   * <p>
   * Method under test:
   * {@link ProcessModelAutoConfiguration#stringToObjectValueConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test stringToObjectValueConverter(ObjectMapper)")
  void testStringToObjectValueConverter() throws JsonProcessingException {
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
   * Test
   * {@link ProcessModelAutoConfiguration#stringToObjectValueConverter(ObjectMapper)}.
   * <ul>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessModelAutoConfiguration#stringToObjectValueConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test stringToObjectValueConverter(ObjectMapper); then throw RuntimeException")
  void testStringToObjectValueConverter_thenThrowRuntimeException() throws JsonProcessingException {
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
   * Test
   * {@link ProcessModelAutoConfiguration#stringToObjectValueConverter(ObjectMapper)}.
   * <ul>
   *   <li>When {@link ObjectMapper#ObjectMapper()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessModelAutoConfiguration#stringToObjectValueConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test stringToObjectValueConverter(ObjectMapper); when ObjectMapper()")
  void testStringToObjectValueConverter_whenObjectMapper() throws JsonProcessingException {
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
   * Test
   * {@link ProcessModelAutoConfiguration#objectValueToStringConverter(ObjectMapper)}.
   * <p>
   * Method under test:
   * {@link ProcessModelAutoConfiguration#objectValueToStringConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test objectValueToStringConverter(ObjectMapper)")
  void testObjectValueToStringConverter() {
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
   * Test
   * {@link ProcessModelAutoConfiguration#objectValueToStringConverter(ObjectMapper)}.
   * <p>
   * Method under test:
   * {@link ProcessModelAutoConfiguration#objectValueToStringConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test objectValueToStringConverter(ObjectMapper)")
  void testObjectValueToStringConverter2() {
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
   * Test
   * {@link ProcessModelAutoConfiguration#objectValueToStringConverter(ObjectMapper)}.
   * <ul>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessModelAutoConfiguration#objectValueToStringConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test objectValueToStringConverter(ObjectMapper); then throw RuntimeException")
  void testObjectValueToStringConverter_thenThrowRuntimeException() {
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
   * Test
   * {@link ProcessModelAutoConfiguration#objectValueToStringConverter(ObjectMapper)}.
   * <ul>
   *   <li>When {@link ObjectMapper#ObjectMapper()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessModelAutoConfiguration#objectValueToStringConverter(ObjectMapper)}
   */
  @Test
  @DisplayName("Test objectValueToStringConverter(ObjectMapper); when ObjectMapper()")
  void testObjectValueToStringConverter_whenObjectMapper() {
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
   * Test new {@link ProcessModelAutoConfiguration} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link ProcessModelAutoConfiguration}
   */
  @Test
  @DisplayName("Test new ProcessModelAutoConfiguration (default constructor)")
  void testNewProcessModelAutoConfiguration() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange, Act and Assert
    assertTrue((new ProcessModelAutoConfiguration()).conversionService() instanceof ApplicationConversionService);
  }
}
