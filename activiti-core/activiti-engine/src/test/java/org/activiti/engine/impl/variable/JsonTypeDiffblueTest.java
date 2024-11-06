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
package org.activiti.engine.impl.variable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTypeResolverBuilder;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.cfg.BaseSettings;
import com.fasterxml.jackson.databind.cfg.CoercionConfigs;
import com.fasterxml.jackson.databind.cfg.ConfigOverrides;
import com.fasterxml.jackson.databind.cfg.DatatypeFeatures;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.introspect.BasicClassIntrospector;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.SimpleMixInResolver;
import com.fasterxml.jackson.databind.jsontype.DefaultBaseTypeLimitingValidator;
import com.fasterxml.jackson.databind.jsontype.impl.AsDeductionTypeSerializer;
import com.fasterxml.jackson.databind.jsontype.impl.StdSubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.databind.ser.impl.FailingSerializer;
import com.fasterxml.jackson.databind.ser.std.NumberSerializers;
import com.fasterxml.jackson.databind.ser.std.NumberSerializers.FloatSerializer;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.RootNameLookup;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntityImpl;
import org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;

public class JsonTypeDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link JsonType#JsonType(int, ObjectMapper, boolean, JsonTypeConverter)}
   *   <li>{@link JsonType#getTypeName()}
   *   <li>{@link JsonType#isCachable()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();

    // Act
    JsonType actualJsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));
    String actualTypeName = actualJsonType.getTypeName();

    // Assert
    assertTrue(actualJsonType.isCachable());
    assertEquals(JsonType.JSON, actualTypeName);
  }

  /**
   * Test {@link JsonType#getValue(ValueFields)}.
   * <p>
   * Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue() {
    // Arrange
    JsonType jsonType = new JsonType(3, null, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getName()).thenReturn("Name");
    when(valueFields.getTextValue()).thenReturn("42");

    // Act
    Object actualValue = jsonType.getValue(valueFields);

    // Assert
    verify(valueFields).getName();
    verify(valueFields, atLeast(1)).getTextValue();
    assertNull(actualValue);
  }

  /**
   * Test {@link JsonType#getValue(ValueFields)}.
   * <p>
   * Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue2() throws IOException {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    JsonType jsonType = new JsonType(3, objectMapper, true, new JsonTypeConverter(new ObjectMapper(), null));
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getTextValue()).thenReturn("42");

    // Act
    Object actualValue = jsonType.getValue(valueFields);

    // Assert
    verify(valueFields, atLeast(1)).getTextValue();
    assertTrue(actualValue instanceof IntNode);
    JsonParser traverseResult = ((IntNode) actualValue).traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    assertEquals("42", ((IntNode) actualValue).toPrettyString());
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    Version versionResult = traverseResult.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
    assertEquals("jackson-databind", versionResult.getArtifactId());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    JsonLocation currentLocation = traverseResult.getCurrentLocation();
    assertNull(currentLocation.getSourceRef());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult.getValueAsString());
    assertEquals(-1, currentLocation.getColumnNr());
    assertEquals(-1, currentLocation.getLineNr());
    assertEquals(-1L, currentLocation.getByteOffset());
    assertEquals(-1L, currentLocation.getCharOffset());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, ((IntNode) actualValue).size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(JsonNodeType.NUMBER, ((IntNode) actualValue).getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(((IntNode) actualValue).isArray());
    assertFalse(((IntNode) actualValue).isBigDecimal());
    assertFalse(((IntNode) actualValue).isBigInteger());
    assertFalse(((IntNode) actualValue).isBinary());
    assertFalse(((IntNode) actualValue).isBoolean());
    assertFalse(((IntNode) actualValue).isContainerNode());
    assertFalse(((IntNode) actualValue).isDouble());
    assertFalse(((IntNode) actualValue).isFloat());
    assertFalse(((IntNode) actualValue).isFloatingPointNumber());
    assertFalse(((IntNode) actualValue).isLong());
    assertFalse(((IntNode) actualValue).isMissingNode());
    assertFalse(((IntNode) actualValue).isNull());
    assertFalse(((IntNode) actualValue).isObject());
    assertFalse(((IntNode) actualValue).isPojo());
    assertFalse(((IntNode) actualValue).isShort());
    assertFalse(((IntNode) actualValue).isTextual());
    assertFalse(((IntNode) actualValue).isNaN());
    assertFalse(((IntNode) actualValue).iterator().hasNext());
    assertTrue(((IntNode) actualValue).isNumber());
    assertTrue(((IntNode) actualValue).isValueNode());
    assertTrue(((IntNode) actualValue).isInt());
    assertTrue(((IntNode) actualValue).isIntegralNumber());
    assertTrue(((IntNode) actualValue).isEmpty());
    assertSame(currentLocation, traverseResult.getTokenLocation());
  }

  /**
   * Test {@link JsonType#getValue(ValueFields)}.
   * <p>
   * Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue3() {
    // Arrange
    JsonType jsonType = new JsonType(3, new ObjectMapper(), true, null);
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getName()).thenReturn("Name");
    when(valueFields.getTextValue()).thenReturn("42");

    // Act
    Object actualValue = jsonType.getValue(valueFields);

    // Assert
    verify(valueFields).getName();
    verify(valueFields, atLeast(1)).getTextValue();
    assertNull(actualValue);
  }

  /**
   * Test {@link JsonType#getValue(ValueFields)}.
   * <p>
   * Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue4() throws IOException {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    JsonType jsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getTextValue2()).thenReturn("com.fasterxml.jackson.databind.node.IntNode");
    when(valueFields.getTextValue()).thenReturn("42");

    // Act
    Object actualValue = jsonType.getValue(valueFields);

    // Assert
    verify(valueFields, atLeast(1)).getTextValue();
    verify(valueFields, atLeast(1)).getTextValue2();
    assertTrue(actualValue instanceof IntNode);
    JsonParser traverseResult = ((IntNode) actualValue).traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    assertEquals("42", ((IntNode) actualValue).toPrettyString());
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    Version versionResult = traverseResult.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
    assertEquals("jackson-databind", versionResult.getArtifactId());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    JsonLocation currentLocation = traverseResult.getCurrentLocation();
    assertNull(currentLocation.getSourceRef());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult.getValueAsString());
    assertEquals(-1, currentLocation.getColumnNr());
    assertEquals(-1, currentLocation.getLineNr());
    assertEquals(-1L, currentLocation.getByteOffset());
    assertEquals(-1L, currentLocation.getCharOffset());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, ((IntNode) actualValue).size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(JsonNodeType.NUMBER, ((IntNode) actualValue).getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(((IntNode) actualValue).isArray());
    assertFalse(((IntNode) actualValue).isBigDecimal());
    assertFalse(((IntNode) actualValue).isBigInteger());
    assertFalse(((IntNode) actualValue).isBinary());
    assertFalse(((IntNode) actualValue).isBoolean());
    assertFalse(((IntNode) actualValue).isContainerNode());
    assertFalse(((IntNode) actualValue).isDouble());
    assertFalse(((IntNode) actualValue).isFloat());
    assertFalse(((IntNode) actualValue).isFloatingPointNumber());
    assertFalse(((IntNode) actualValue).isLong());
    assertFalse(((IntNode) actualValue).isMissingNode());
    assertFalse(((IntNode) actualValue).isNull());
    assertFalse(((IntNode) actualValue).isObject());
    assertFalse(((IntNode) actualValue).isPojo());
    assertFalse(((IntNode) actualValue).isShort());
    assertFalse(((IntNode) actualValue).isTextual());
    assertFalse(((IntNode) actualValue).isNaN());
    assertFalse(((IntNode) actualValue).iterator().hasNext());
    assertTrue(((IntNode) actualValue).isNumber());
    assertTrue(((IntNode) actualValue).isValueNode());
    assertTrue(((IntNode) actualValue).isInt());
    assertTrue(((IntNode) actualValue).isIntegralNumber());
    assertTrue(((IntNode) actualValue).isEmpty());
    assertSame(currentLocation, traverseResult.getTokenLocation());
  }

  /**
   * Test {@link JsonType#getValue(ValueFields)}.
   * <p>
   * Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue5() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    JsonType jsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getName()).thenReturn("Name");
    when(valueFields.getTextValue()).thenReturn("com.fasterxml.jackson.databind.node.IntNode");

    // Act
    Object actualValue = jsonType.getValue(valueFields);

    // Assert
    verify(valueFields).getName();
    verify(valueFields, atLeast(1)).getTextValue();
    assertNull(actualValue);
  }

  /**
   * Test {@link JsonType#getValue(ValueFields)}.
   * <p>
   * Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue6() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    JacksonAnnotationIntrospector ai = new JacksonAnnotationIntrospector();
    PropertyNamingStrategy pns = new PropertyNamingStrategy();
    TypeFactory tf = TypeFactory.defaultInstance();
    ObjectMapper.DefaultTypeResolverBuilder typer = new ObjectMapper.DefaultTypeResolverBuilder(
        ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");
    HandlerInstantiator hi = mock(HandlerInstantiator.class);
    Locale locale = Locale.getDefault();
    TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
    Base64Variant defaultBase64 = Base64Variants.getDefaultVariant();
    BaseSettings base = new BaseSettings(null, ai, pns, tf, typer, dateFormat, hi, locale, tz, defaultBase64,
        new DefaultBaseTypeLimitingValidator());

    StdSubtypeResolver str = new StdSubtypeResolver();
    SimpleMixInResolver mixins = new SimpleMixInResolver(mock(ClassIntrospector.MixInResolver.class));
    RootNameLookup rootNames = new RootNameLookup();
    ConfigOverrides configOverrides = new ConfigOverrides();
    objectMapper.setConfig(new DeserializationConfig(base, str, mixins, rootNames, configOverrides,
        new CoercionConfigs(), mock(DatatypeFeatures.class)));
    JsonType jsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getName()).thenReturn("Name");
    when(valueFields.getTextValue()).thenReturn("42");

    // Act
    Object actualValue = jsonType.getValue(valueFields);

    // Assert
    verify(valueFields).getName();
    verify(valueFields, atLeast(1)).getTextValue();
    assertNull(actualValue);
  }

  /**
   * Test {@link JsonType#getValue(ValueFields)}.
   * <p>
   * Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue7() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    BasicClassIntrospector ci = new BasicClassIntrospector();
    JacksonAnnotationIntrospector ai = new JacksonAnnotationIntrospector();
    PropertyNamingStrategy pns = new PropertyNamingStrategy();
    ObjectMapper.DefaultTypeResolverBuilder typer = new ObjectMapper.DefaultTypeResolverBuilder(
        ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");
    HandlerInstantiator hi = mock(HandlerInstantiator.class);
    Locale locale = Locale.getDefault();
    TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
    Base64Variant defaultBase64 = Base64Variants.getDefaultVariant();
    BaseSettings base = new BaseSettings(ci, ai, pns, null, typer, dateFormat, hi, locale, tz, defaultBase64,
        new DefaultBaseTypeLimitingValidator());

    StdSubtypeResolver str = new StdSubtypeResolver();
    SimpleMixInResolver mixins = new SimpleMixInResolver(mock(ClassIntrospector.MixInResolver.class));
    RootNameLookup rootNames = new RootNameLookup();
    ConfigOverrides configOverrides = new ConfigOverrides();
    objectMapper.setConfig(new DeserializationConfig(base, str, mixins, rootNames, configOverrides,
        new CoercionConfigs(), mock(DatatypeFeatures.class)));
    JsonType jsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getName()).thenReturn("Name");
    when(valueFields.getTextValue()).thenReturn("42");

    // Act
    Object actualValue = jsonType.getValue(valueFields);

    // Assert
    verify(valueFields).getName();
    verify(valueFields, atLeast(1)).getTextValue();
    assertNull(actualValue);
  }

  /**
   * Test {@link JsonType#getValue(ValueFields)}.
   * <p>
   * Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue8() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    BasicClassIntrospector ci = new BasicClassIntrospector();
    JacksonAnnotationIntrospector ai = new JacksonAnnotationIntrospector();
    PropertyNamingStrategy pns = new PropertyNamingStrategy();
    TypeFactory tf = TypeFactory.defaultInstance();
    StdTypeResolverBuilder typer = new StdTypeResolverBuilder();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");
    HandlerInstantiator hi = mock(HandlerInstantiator.class);
    Locale locale = Locale.getDefault();
    TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
    Base64Variant defaultBase64 = Base64Variants.getDefaultVariant();
    BaseSettings base = new BaseSettings(ci, ai, pns, tf, typer, dateFormat, hi, locale, tz, defaultBase64,
        new DefaultBaseTypeLimitingValidator());

    StdSubtypeResolver str = new StdSubtypeResolver();
    SimpleMixInResolver mixins = new SimpleMixInResolver(mock(ClassIntrospector.MixInResolver.class));
    RootNameLookup rootNames = new RootNameLookup();
    ConfigOverrides configOverrides = new ConfigOverrides();
    objectMapper.setConfig(new DeserializationConfig(base, str, mixins, rootNames, configOverrides,
        new CoercionConfigs(), mock(DatatypeFeatures.class)));
    JsonType jsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getName()).thenReturn("Name");
    when(valueFields.getTextValue()).thenReturn("42");

    // Act
    Object actualValue = jsonType.getValue(valueFields);

    // Assert
    verify(valueFields).getName();
    verify(valueFields, atLeast(1)).getTextValue();
    assertNull(actualValue);
  }

  /**
   * Test {@link JsonType#getValue(ValueFields)}.
   * <p>
   * Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue9() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    StdSubtypeResolver str = new StdSubtypeResolver();
    SimpleMixInResolver mixins = new SimpleMixInResolver(mock(ClassIntrospector.MixInResolver.class));
    RootNameLookup rootNames = new RootNameLookup();
    ConfigOverrides configOverrides = new ConfigOverrides();
    objectMapper.setConfig(new DeserializationConfig(null, str, mixins, rootNames, configOverrides,
        new CoercionConfigs(), mock(DatatypeFeatures.class)));
    JsonType jsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getName()).thenReturn("Name");
    when(valueFields.getTextValue()).thenReturn("42");

    // Act
    Object actualValue = jsonType.getValue(valueFields);

    // Assert
    verify(valueFields).getName();
    verify(valueFields, atLeast(1)).getTextValue();
    assertNull(actualValue);
  }

  /**
   * Test {@link JsonType#getValue(ValueFields)}.
   * <p>
   * Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue10() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    BasicClassIntrospector ci = new BasicClassIntrospector();
    JacksonAnnotationIntrospector ai = new JacksonAnnotationIntrospector();
    PropertyNamingStrategy pns = new PropertyNamingStrategy();
    TypeFactory tf = TypeFactory.defaultInstance();
    ObjectMapper.DefaultTypeResolverBuilder typer = new ObjectMapper.DefaultTypeResolverBuilder(
        ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");
    HandlerInstantiator hi = mock(HandlerInstantiator.class);
    Locale locale = Locale.getDefault();
    TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
    Base64Variant defaultBase64 = Base64Variants.getDefaultVariant();
    BaseSettings base = new BaseSettings(ci, ai, pns, tf, typer, dateFormat, hi, locale, tz, defaultBase64,
        new DefaultBaseTypeLimitingValidator());

    StdSubtypeResolver str = new StdSubtypeResolver();
    SimpleMixInResolver mixins = new SimpleMixInResolver(mock(ClassIntrospector.MixInResolver.class));
    RootNameLookup rootNames = new RootNameLookup();
    objectMapper.setConfig(new DeserializationConfig(base, str, mixins, rootNames, null, new CoercionConfigs(),
        mock(DatatypeFeatures.class)));
    JsonType jsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getName()).thenReturn("Name");
    when(valueFields.getTextValue()).thenReturn("42");

    // Act
    Object actualValue = jsonType.getValue(valueFields);

    // Assert
    verify(valueFields).getName();
    verify(valueFields, atLeast(1)).getTextValue();
    assertNull(actualValue);
  }

  /**
   * Test {@link JsonType#getValue(ValueFields)}.
   * <ul>
   *   <li>Given
   * {@link DefaultTypeResolverBuilder#DefaultTypeResolverBuilder(DefaultTyping)}
   * with t is {@code JAVA_LANG_OBJECT}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_givenDefaultTypeResolverBuilderWithTIsJavaLangObject() throws IOException {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    BasicClassIntrospector ci = new BasicClassIntrospector();
    JacksonAnnotationIntrospector ai = new JacksonAnnotationIntrospector();
    PropertyNamingStrategy pns = new PropertyNamingStrategy();
    TypeFactory tf = TypeFactory.defaultInstance();
    ObjectMapper.DefaultTypeResolverBuilder typer = new ObjectMapper.DefaultTypeResolverBuilder(
        ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");
    HandlerInstantiator hi = mock(HandlerInstantiator.class);
    Locale locale = Locale.getDefault();
    TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
    Base64Variant defaultBase64 = Base64Variants.getDefaultVariant();
    BaseSettings base = new BaseSettings(ci, ai, pns, tf, typer, dateFormat, hi, locale, tz, defaultBase64,
        new DefaultBaseTypeLimitingValidator());

    StdSubtypeResolver str = new StdSubtypeResolver();
    SimpleMixInResolver mixins = new SimpleMixInResolver(mock(ClassIntrospector.MixInResolver.class));
    RootNameLookup rootNames = new RootNameLookup();
    ConfigOverrides configOverrides = new ConfigOverrides();
    objectMapper.setConfig(new DeserializationConfig(base, str, mixins, rootNames, configOverrides,
        new CoercionConfigs(), mock(DatatypeFeatures.class)));
    JsonType jsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getName()).thenReturn("Name");
    when(valueFields.getTextValue2()).thenReturn("42");
    when(valueFields.getTextValue()).thenReturn("42");

    // Act
    Object actualValue = jsonType.getValue(valueFields);

    // Assert
    verify(valueFields).getName();
    verify(valueFields, atLeast(1)).getTextValue();
    verify(valueFields, atLeast(1)).getTextValue2();
    assertTrue(actualValue instanceof IntNode);
    JsonParser traverseResult = ((IntNode) actualValue).traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    assertEquals("42", ((IntNode) actualValue).toPrettyString());
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    Version versionResult = traverseResult.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
    assertEquals("jackson-databind", versionResult.getArtifactId());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    JsonLocation currentLocation = traverseResult.getCurrentLocation();
    assertNull(currentLocation.getSourceRef());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult.getValueAsString());
    assertEquals(-1, currentLocation.getColumnNr());
    assertEquals(-1, currentLocation.getLineNr());
    assertEquals(-1L, currentLocation.getByteOffset());
    assertEquals(-1L, currentLocation.getCharOffset());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, ((IntNode) actualValue).size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(JsonNodeType.NUMBER, ((IntNode) actualValue).getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(((IntNode) actualValue).isArray());
    assertFalse(((IntNode) actualValue).isBigDecimal());
    assertFalse(((IntNode) actualValue).isBigInteger());
    assertFalse(((IntNode) actualValue).isBinary());
    assertFalse(((IntNode) actualValue).isBoolean());
    assertFalse(((IntNode) actualValue).isContainerNode());
    assertFalse(((IntNode) actualValue).isDouble());
    assertFalse(((IntNode) actualValue).isFloat());
    assertFalse(((IntNode) actualValue).isFloatingPointNumber());
    assertFalse(((IntNode) actualValue).isLong());
    assertFalse(((IntNode) actualValue).isMissingNode());
    assertFalse(((IntNode) actualValue).isNull());
    assertFalse(((IntNode) actualValue).isObject());
    assertFalse(((IntNode) actualValue).isPojo());
    assertFalse(((IntNode) actualValue).isShort());
    assertFalse(((IntNode) actualValue).isTextual());
    assertFalse(((IntNode) actualValue).isNaN());
    assertFalse(((IntNode) actualValue).iterator().hasNext());
    assertTrue(((IntNode) actualValue).isNumber());
    assertTrue(((IntNode) actualValue).isValueNode());
    assertTrue(((IntNode) actualValue).isInt());
    assertTrue(((IntNode) actualValue).isIntegralNumber());
    assertTrue(((IntNode) actualValue).isEmpty());
    assertSame(currentLocation, traverseResult.getTokenLocation());
  }

  /**
   * Test {@link JsonType#getValue(ValueFields)}.
   * <ul>
   *   <li>Given
   * {@link DefaultTypeResolverBuilder#DefaultTypeResolverBuilder(DefaultTyping)}
   * with t is {@code NON_CONCRETE_AND_ARRAYS}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_givenDefaultTypeResolverBuilderWithTIsNonConcreteAndArrays() throws IOException {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    BasicClassIntrospector ci = new BasicClassIntrospector();
    JacksonAnnotationIntrospector ai = new JacksonAnnotationIntrospector();
    PropertyNamingStrategy pns = new PropertyNamingStrategy();
    TypeFactory tf = TypeFactory.defaultInstance();
    ObjectMapper.DefaultTypeResolverBuilder typer = new ObjectMapper.DefaultTypeResolverBuilder(
        ObjectMapper.DefaultTyping.NON_CONCRETE_AND_ARRAYS);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");
    HandlerInstantiator hi = mock(HandlerInstantiator.class);
    Locale locale = Locale.getDefault();
    TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
    Base64Variant defaultBase64 = Base64Variants.getDefaultVariant();
    BaseSettings base = new BaseSettings(ci, ai, pns, tf, typer, dateFormat, hi, locale, tz, defaultBase64,
        new DefaultBaseTypeLimitingValidator());

    StdSubtypeResolver str = new StdSubtypeResolver();
    SimpleMixInResolver mixins = new SimpleMixInResolver(mock(ClassIntrospector.MixInResolver.class));
    RootNameLookup rootNames = new RootNameLookup();
    ConfigOverrides configOverrides = new ConfigOverrides();
    objectMapper.setConfig(new DeserializationConfig(base, str, mixins, rootNames, configOverrides,
        new CoercionConfigs(), mock(DatatypeFeatures.class)));
    JsonType jsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getName()).thenReturn("Name");
    when(valueFields.getTextValue2()).thenReturn("42");
    when(valueFields.getTextValue()).thenReturn("42");

    // Act
    Object actualValue = jsonType.getValue(valueFields);

    // Assert
    verify(valueFields).getName();
    verify(valueFields, atLeast(1)).getTextValue();
    verify(valueFields, atLeast(1)).getTextValue2();
    assertTrue(actualValue instanceof IntNode);
    JsonParser traverseResult = ((IntNode) actualValue).traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    assertEquals("42", ((IntNode) actualValue).toPrettyString());
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    Version versionResult = traverseResult.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
    assertEquals("jackson-databind", versionResult.getArtifactId());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    JsonLocation currentLocation = traverseResult.getCurrentLocation();
    assertNull(currentLocation.getSourceRef());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult.getValueAsString());
    assertEquals(-1, currentLocation.getColumnNr());
    assertEquals(-1, currentLocation.getLineNr());
    assertEquals(-1L, currentLocation.getByteOffset());
    assertEquals(-1L, currentLocation.getCharOffset());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, ((IntNode) actualValue).size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(JsonNodeType.NUMBER, ((IntNode) actualValue).getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(((IntNode) actualValue).isArray());
    assertFalse(((IntNode) actualValue).isBigDecimal());
    assertFalse(((IntNode) actualValue).isBigInteger());
    assertFalse(((IntNode) actualValue).isBinary());
    assertFalse(((IntNode) actualValue).isBoolean());
    assertFalse(((IntNode) actualValue).isContainerNode());
    assertFalse(((IntNode) actualValue).isDouble());
    assertFalse(((IntNode) actualValue).isFloat());
    assertFalse(((IntNode) actualValue).isFloatingPointNumber());
    assertFalse(((IntNode) actualValue).isLong());
    assertFalse(((IntNode) actualValue).isMissingNode());
    assertFalse(((IntNode) actualValue).isNull());
    assertFalse(((IntNode) actualValue).isObject());
    assertFalse(((IntNode) actualValue).isPojo());
    assertFalse(((IntNode) actualValue).isShort());
    assertFalse(((IntNode) actualValue).isTextual());
    assertFalse(((IntNode) actualValue).isNaN());
    assertFalse(((IntNode) actualValue).iterator().hasNext());
    assertTrue(((IntNode) actualValue).isNumber());
    assertTrue(((IntNode) actualValue).isValueNode());
    assertTrue(((IntNode) actualValue).isInt());
    assertTrue(((IntNode) actualValue).isIntegralNumber());
    assertTrue(((IntNode) actualValue).isEmpty());
    assertSame(currentLocation, traverseResult.getTokenLocation());
  }

  /**
   * Test {@link JsonType#getValue(ValueFields)}.
   * <ul>
   *   <li>Given
   * {@link DefaultTypeResolverBuilder#DefaultTypeResolverBuilder(DefaultTyping)}
   * with t is {@code NON_FINAL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_givenDefaultTypeResolverBuilderWithTIsNonFinal() throws IOException {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    BasicClassIntrospector ci = new BasicClassIntrospector();
    JacksonAnnotationIntrospector ai = new JacksonAnnotationIntrospector();
    PropertyNamingStrategy pns = new PropertyNamingStrategy();
    TypeFactory tf = TypeFactory.defaultInstance();
    ObjectMapper.DefaultTypeResolverBuilder typer = new ObjectMapper.DefaultTypeResolverBuilder(
        ObjectMapper.DefaultTyping.NON_FINAL);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");
    HandlerInstantiator hi = mock(HandlerInstantiator.class);
    Locale locale = Locale.getDefault();
    TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
    Base64Variant defaultBase64 = Base64Variants.getDefaultVariant();
    BaseSettings base = new BaseSettings(ci, ai, pns, tf, typer, dateFormat, hi, locale, tz, defaultBase64,
        new DefaultBaseTypeLimitingValidator());

    StdSubtypeResolver str = new StdSubtypeResolver();
    SimpleMixInResolver mixins = new SimpleMixInResolver(mock(ClassIntrospector.MixInResolver.class));
    RootNameLookup rootNames = new RootNameLookup();
    ConfigOverrides configOverrides = new ConfigOverrides();
    objectMapper.setConfig(new DeserializationConfig(base, str, mixins, rootNames, configOverrides,
        new CoercionConfigs(), mock(DatatypeFeatures.class)));
    JsonType jsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getName()).thenReturn("Name");
    when(valueFields.getTextValue2()).thenReturn("42");
    when(valueFields.getTextValue()).thenReturn("42");

    // Act
    Object actualValue = jsonType.getValue(valueFields);

    // Assert
    verify(valueFields).getName();
    verify(valueFields, atLeast(1)).getTextValue();
    verify(valueFields, atLeast(1)).getTextValue2();
    assertTrue(actualValue instanceof IntNode);
    JsonParser traverseResult = ((IntNode) actualValue).traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    assertEquals("42", ((IntNode) actualValue).toPrettyString());
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    Version versionResult = traverseResult.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
    assertEquals("jackson-databind", versionResult.getArtifactId());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    JsonLocation currentLocation = traverseResult.getCurrentLocation();
    assertNull(currentLocation.getSourceRef());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult.getValueAsString());
    assertEquals(-1, currentLocation.getColumnNr());
    assertEquals(-1, currentLocation.getLineNr());
    assertEquals(-1L, currentLocation.getByteOffset());
    assertEquals(-1L, currentLocation.getCharOffset());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, ((IntNode) actualValue).size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(JsonNodeType.NUMBER, ((IntNode) actualValue).getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(((IntNode) actualValue).isArray());
    assertFalse(((IntNode) actualValue).isBigDecimal());
    assertFalse(((IntNode) actualValue).isBigInteger());
    assertFalse(((IntNode) actualValue).isBinary());
    assertFalse(((IntNode) actualValue).isBoolean());
    assertFalse(((IntNode) actualValue).isContainerNode());
    assertFalse(((IntNode) actualValue).isDouble());
    assertFalse(((IntNode) actualValue).isFloat());
    assertFalse(((IntNode) actualValue).isFloatingPointNumber());
    assertFalse(((IntNode) actualValue).isLong());
    assertFalse(((IntNode) actualValue).isMissingNode());
    assertFalse(((IntNode) actualValue).isNull());
    assertFalse(((IntNode) actualValue).isObject());
    assertFalse(((IntNode) actualValue).isPojo());
    assertFalse(((IntNode) actualValue).isShort());
    assertFalse(((IntNode) actualValue).isTextual());
    assertFalse(((IntNode) actualValue).isNaN());
    assertFalse(((IntNode) actualValue).iterator().hasNext());
    assertTrue(((IntNode) actualValue).isNumber());
    assertTrue(((IntNode) actualValue).isValueNode());
    assertTrue(((IntNode) actualValue).isInt());
    assertTrue(((IntNode) actualValue).isIntegralNumber());
    assertTrue(((IntNode) actualValue).isEmpty());
    assertSame(currentLocation, traverseResult.getTokenLocation());
  }

  /**
   * Test {@link JsonType#getValue(ValueFields)}.
   * <ul>
   *   <li>Given
   * {@link DefaultTypeResolverBuilder#DefaultTypeResolverBuilder(DefaultTyping)}
   * with t is {@code OBJECT_AND_NON_CONCRETE}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_givenDefaultTypeResolverBuilderWithTIsObjectAndNonConcrete() throws IOException {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    BasicClassIntrospector ci = new BasicClassIntrospector();
    JacksonAnnotationIntrospector ai = new JacksonAnnotationIntrospector();
    PropertyNamingStrategy pns = new PropertyNamingStrategy();
    TypeFactory tf = TypeFactory.defaultInstance();
    ObjectMapper.DefaultTypeResolverBuilder typer = new ObjectMapper.DefaultTypeResolverBuilder(
        ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");
    HandlerInstantiator hi = mock(HandlerInstantiator.class);
    Locale locale = Locale.getDefault();
    TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
    Base64Variant defaultBase64 = Base64Variants.getDefaultVariant();
    BaseSettings base = new BaseSettings(ci, ai, pns, tf, typer, dateFormat, hi, locale, tz, defaultBase64,
        new DefaultBaseTypeLimitingValidator());

    StdSubtypeResolver str = new StdSubtypeResolver();
    SimpleMixInResolver mixins = new SimpleMixInResolver(mock(ClassIntrospector.MixInResolver.class));
    RootNameLookup rootNames = new RootNameLookup();
    ConfigOverrides configOverrides = new ConfigOverrides();
    objectMapper.setConfig(new DeserializationConfig(base, str, mixins, rootNames, configOverrides,
        new CoercionConfigs(), mock(DatatypeFeatures.class)));
    JsonType jsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getName()).thenReturn("Name");
    when(valueFields.getTextValue2()).thenReturn("42");
    when(valueFields.getTextValue()).thenReturn("42");

    // Act
    Object actualValue = jsonType.getValue(valueFields);

    // Assert
    verify(valueFields).getName();
    verify(valueFields, atLeast(1)).getTextValue();
    verify(valueFields, atLeast(1)).getTextValue2();
    assertTrue(actualValue instanceof IntNode);
    JsonParser traverseResult = ((IntNode) actualValue).traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    assertEquals("42", ((IntNode) actualValue).toPrettyString());
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    Version versionResult = traverseResult.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
    assertEquals("jackson-databind", versionResult.getArtifactId());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    JsonLocation currentLocation = traverseResult.getCurrentLocation();
    assertNull(currentLocation.getSourceRef());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult.getValueAsString());
    assertEquals(-1, currentLocation.getColumnNr());
    assertEquals(-1, currentLocation.getLineNr());
    assertEquals(-1L, currentLocation.getByteOffset());
    assertEquals(-1L, currentLocation.getCharOffset());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, ((IntNode) actualValue).size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(JsonNodeType.NUMBER, ((IntNode) actualValue).getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(((IntNode) actualValue).isArray());
    assertFalse(((IntNode) actualValue).isBigDecimal());
    assertFalse(((IntNode) actualValue).isBigInteger());
    assertFalse(((IntNode) actualValue).isBinary());
    assertFalse(((IntNode) actualValue).isBoolean());
    assertFalse(((IntNode) actualValue).isContainerNode());
    assertFalse(((IntNode) actualValue).isDouble());
    assertFalse(((IntNode) actualValue).isFloat());
    assertFalse(((IntNode) actualValue).isFloatingPointNumber());
    assertFalse(((IntNode) actualValue).isLong());
    assertFalse(((IntNode) actualValue).isMissingNode());
    assertFalse(((IntNode) actualValue).isNull());
    assertFalse(((IntNode) actualValue).isObject());
    assertFalse(((IntNode) actualValue).isPojo());
    assertFalse(((IntNode) actualValue).isShort());
    assertFalse(((IntNode) actualValue).isTextual());
    assertFalse(((IntNode) actualValue).isNaN());
    assertFalse(((IntNode) actualValue).iterator().hasNext());
    assertTrue(((IntNode) actualValue).isNumber());
    assertTrue(((IntNode) actualValue).isValueNode());
    assertTrue(((IntNode) actualValue).isInt());
    assertTrue(((IntNode) actualValue).isIntegralNumber());
    assertTrue(((IntNode) actualValue).isEmpty());
    assertSame(currentLocation, traverseResult.getTokenLocation());
  }

  /**
   * Test {@link JsonType#getValue(ValueFields)}.
   * <ul>
   *   <li>Given empty string.</li>
   *   <li>When {@link ValueFields} {@link ValueFields#getTextValue()} return empty
   * string.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_givenEmptyString_whenValueFieldsGetTextValueReturnEmptyString() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    JsonType jsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getTextValue()).thenReturn("");

    // Act
    Object actualValue = jsonType.getValue(valueFields);

    // Assert
    verify(valueFields, atLeast(1)).getTextValue();
    assertNull(actualValue);
  }

  /**
   * Test {@link JsonType#getValue(ValueFields)}.
   * <ul>
   *   <li>Given {@link JSONObject#NULL}.</li>
   *   <li>When {@link HistoricVariableInstanceEntityImpl} (default constructor)
   * CachedValue is {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_givenNull_whenHistoricVariableInstanceEntityImplCachedValueIsNull() throws IOException {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    JsonType jsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));

    HistoricVariableInstanceEntityImpl valueFields = new HistoricVariableInstanceEntityImpl();
    valueFields.setCachedValue(JSONObject.NULL);
    valueFields.setCreateTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setDeleted(true);
    valueFields.setDoubleValue(10.0d);
    valueFields.setExecutionId("42");
    valueFields.setId("42");
    valueFields.setInserted(true);
    valueFields
        .setLastUpdatedTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    valueFields.setLongValue(42L);
    valueFields.setName("Name");
    valueFields.setProcessInstanceId("42");
    valueFields.setRevision(1);
    valueFields.setTaskId("42");
    valueFields.setTextValue("42");
    valueFields.setTextValue2("42");
    valueFields.setUpdated(true);
    valueFields.setVariableType(new BigDecimalType());

    // Act
    Object actualValue = jsonType.getValue(valueFields);

    // Assert
    assertTrue(actualValue instanceof IntNode);
    JsonParser traverseResult = ((IntNode) actualValue).traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    assertEquals("42", ((IntNode) actualValue).toPrettyString());
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    Version versionResult = traverseResult.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
    assertEquals("jackson-databind", versionResult.getArtifactId());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    JsonLocation currentLocation = traverseResult.getCurrentLocation();
    assertNull(currentLocation.getSourceRef());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult.getValueAsString());
    assertEquals(-1, currentLocation.getColumnNr());
    assertEquals(-1, currentLocation.getLineNr());
    assertEquals(-1L, currentLocation.getByteOffset());
    assertEquals(-1L, currentLocation.getCharOffset());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, ((IntNode) actualValue).size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(JsonNodeType.NUMBER, ((IntNode) actualValue).getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(((IntNode) actualValue).isArray());
    assertFalse(((IntNode) actualValue).isBigDecimal());
    assertFalse(((IntNode) actualValue).isBigInteger());
    assertFalse(((IntNode) actualValue).isBinary());
    assertFalse(((IntNode) actualValue).isBoolean());
    assertFalse(((IntNode) actualValue).isContainerNode());
    assertFalse(((IntNode) actualValue).isDouble());
    assertFalse(((IntNode) actualValue).isFloat());
    assertFalse(((IntNode) actualValue).isFloatingPointNumber());
    assertFalse(((IntNode) actualValue).isLong());
    assertFalse(((IntNode) actualValue).isMissingNode());
    assertFalse(((IntNode) actualValue).isNull());
    assertFalse(((IntNode) actualValue).isObject());
    assertFalse(((IntNode) actualValue).isPojo());
    assertFalse(((IntNode) actualValue).isShort());
    assertFalse(((IntNode) actualValue).isTextual());
    assertFalse(((IntNode) actualValue).isNaN());
    assertFalse(((IntNode) actualValue).iterator().hasNext());
    assertTrue(((IntNode) actualValue).isNumber());
    assertTrue(((IntNode) actualValue).isValueNode());
    assertTrue(((IntNode) actualValue).isInt());
    assertTrue(((IntNode) actualValue).isIntegralNumber());
    assertTrue(((IntNode) actualValue).isEmpty());
    assertSame(currentLocation, traverseResult.getTokenLocation());
  }

  /**
   * Test {@link JsonType#getValue(ValueFields)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link ValueFields} {@link ValueFields#getTextValue2()} return
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_givenNull_whenValueFieldsGetTextValue2ReturnNull() throws IOException {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    JsonType jsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getTextValue2()).thenReturn(null);
    when(valueFields.getTextValue()).thenReturn("42");

    // Act
    Object actualValue = jsonType.getValue(valueFields);

    // Assert
    verify(valueFields, atLeast(1)).getTextValue();
    verify(valueFields).getTextValue2();
    assertTrue(actualValue instanceof IntNode);
    JsonParser traverseResult = ((IntNode) actualValue).traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    assertEquals("42", ((IntNode) actualValue).toPrettyString());
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    Version versionResult = traverseResult.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
    assertEquals("jackson-databind", versionResult.getArtifactId());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    JsonLocation currentLocation = traverseResult.getCurrentLocation();
    assertNull(currentLocation.getSourceRef());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult.getValueAsString());
    assertEquals(-1, currentLocation.getColumnNr());
    assertEquals(-1, currentLocation.getLineNr());
    assertEquals(-1L, currentLocation.getByteOffset());
    assertEquals(-1L, currentLocation.getCharOffset());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, ((IntNode) actualValue).size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(JsonNodeType.NUMBER, ((IntNode) actualValue).getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(((IntNode) actualValue).isArray());
    assertFalse(((IntNode) actualValue).isBigDecimal());
    assertFalse(((IntNode) actualValue).isBigInteger());
    assertFalse(((IntNode) actualValue).isBinary());
    assertFalse(((IntNode) actualValue).isBoolean());
    assertFalse(((IntNode) actualValue).isContainerNode());
    assertFalse(((IntNode) actualValue).isDouble());
    assertFalse(((IntNode) actualValue).isFloat());
    assertFalse(((IntNode) actualValue).isFloatingPointNumber());
    assertFalse(((IntNode) actualValue).isLong());
    assertFalse(((IntNode) actualValue).isMissingNode());
    assertFalse(((IntNode) actualValue).isNull());
    assertFalse(((IntNode) actualValue).isObject());
    assertFalse(((IntNode) actualValue).isPojo());
    assertFalse(((IntNode) actualValue).isShort());
    assertFalse(((IntNode) actualValue).isTextual());
    assertFalse(((IntNode) actualValue).isNaN());
    assertFalse(((IntNode) actualValue).iterator().hasNext());
    assertTrue(((IntNode) actualValue).isNumber());
    assertTrue(((IntNode) actualValue).isValueNode());
    assertTrue(((IntNode) actualValue).isInt());
    assertTrue(((IntNode) actualValue).isIntegralNumber());
    assertTrue(((IntNode) actualValue).isEmpty());
    assertSame(currentLocation, traverseResult.getTokenLocation());
  }

  /**
   * Test {@link JsonType#getValue(ValueFields)}.
   * <ul>
   *   <li>When {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default
   * constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_whenHistoricDetailVariableInstanceUpdateEntityImpl_thenReturnNull() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    JsonType jsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));

    // Act and Assert
    assertNull(jsonType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link JsonType#getValue(ValueFields)}.
   * <ul>
   *   <li>When {@link ValueFields} {@link ValueFields#getTextValue2()} return
   * {@code 42}.</li>
   *   <li>Then calls {@link ValueFields#getTextValue2()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue_whenValueFieldsGetTextValue2Return42_thenCallsGetTextValue2() throws IOException {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    JsonType jsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));
    ValueFields valueFields = mock(ValueFields.class);
    when(valueFields.getName()).thenReturn("Name");
    when(valueFields.getTextValue2()).thenReturn("42");
    when(valueFields.getTextValue()).thenReturn("42");

    // Act
    Object actualValue = jsonType.getValue(valueFields);

    // Assert
    verify(valueFields).getName();
    verify(valueFields, atLeast(1)).getTextValue();
    verify(valueFields, atLeast(1)).getTextValue2();
    assertTrue(actualValue instanceof IntNode);
    JsonParser traverseResult = ((IntNode) actualValue).traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    assertEquals("42", ((IntNode) actualValue).toPrettyString());
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    Version versionResult = traverseResult.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
    assertEquals("jackson-databind", versionResult.getArtifactId());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    JsonLocation currentLocation = traverseResult.getCurrentLocation();
    assertNull(currentLocation.getSourceRef());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult.getValueAsString());
    assertEquals(-1, currentLocation.getColumnNr());
    assertEquals(-1, currentLocation.getLineNr());
    assertEquals(-1L, currentLocation.getByteOffset());
    assertEquals(-1L, currentLocation.getCharOffset());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, ((IntNode) actualValue).size());
    assertEquals(0.0d, traverseResult.getValueAsDouble(), 0.0);
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(JsonNodeType.NUMBER, ((IntNode) actualValue).getNodeType());
    assertFalse(traverseResult.getValueAsBoolean());
    assertFalse(traverseResult.hasCurrentToken());
    assertFalse(traverseResult.hasTextCharacters());
    assertFalse(traverseResult.isClosed());
    assertFalse(traverseResult.isExpectedNumberIntToken());
    assertFalse(traverseResult.isExpectedStartArrayToken());
    assertFalse(traverseResult.isExpectedStartObjectToken());
    assertFalse(traverseResult.isNaN());
    assertFalse(parsingContext.hasCurrentIndex());
    assertFalse(parsingContext.hasCurrentName());
    assertFalse(parsingContext.hasPathSegment());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(((IntNode) actualValue).isArray());
    assertFalse(((IntNode) actualValue).isBigDecimal());
    assertFalse(((IntNode) actualValue).isBigInteger());
    assertFalse(((IntNode) actualValue).isBinary());
    assertFalse(((IntNode) actualValue).isBoolean());
    assertFalse(((IntNode) actualValue).isContainerNode());
    assertFalse(((IntNode) actualValue).isDouble());
    assertFalse(((IntNode) actualValue).isFloat());
    assertFalse(((IntNode) actualValue).isFloatingPointNumber());
    assertFalse(((IntNode) actualValue).isLong());
    assertFalse(((IntNode) actualValue).isMissingNode());
    assertFalse(((IntNode) actualValue).isNull());
    assertFalse(((IntNode) actualValue).isObject());
    assertFalse(((IntNode) actualValue).isPojo());
    assertFalse(((IntNode) actualValue).isShort());
    assertFalse(((IntNode) actualValue).isTextual());
    assertFalse(((IntNode) actualValue).isNaN());
    assertFalse(((IntNode) actualValue).iterator().hasNext());
    assertTrue(((IntNode) actualValue).isNumber());
    assertTrue(((IntNode) actualValue).isValueNode());
    assertTrue(((IntNode) actualValue).isInt());
    assertTrue(((IntNode) actualValue).isIntegralNumber());
    assertTrue(((IntNode) actualValue).isEmpty());
    assertSame(currentLocation, traverseResult.getTokenLocation());
  }

  /**
   * Test {@link JsonType#setValue(Object, ValueFields)}.
   * <p>
   * Method under test: {@link JsonType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue() throws JsonMappingException {
    // Arrange
    SerializerFactory f = mock(SerializerFactory.class);
    Class<Object> type = Object.class;
    when(f.createSerializer(Mockito.<SerializerProvider>any(), Mockito.<JavaType>any()))
        .thenReturn(new StdKeySerializers.Default(1, type));
    when(f.createTypeSerializer(Mockito.<SerializationConfig>any(), Mockito.<JavaType>any()))
        .thenReturn(AsDeductionTypeSerializer.instance());

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializerFactory(f);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    objectMapper.addMixIn(target, mixinSource);
    JsonType jsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    jsonType.setValue(JSONObject.NULL, valueFields);

    // Assert
    verify(f).createSerializer(isA(SerializerProvider.class), isA(JavaType.class));
    verify(f).createTypeSerializer(isA(SerializationConfig.class), isA(JavaType.class));
    assertNull(valueFields.getTextValue());
    assertNull(valueFields.getTextValue2());
  }

  /**
   * Test {@link JsonType#setValue(Object, ValueFields)}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue_givenJavaLangObject() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    objectMapper.addMixIn(target, mixinSource);
    JsonType jsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    jsonType.setValue(JSONObject.NULL, valueFields);

    // Assert
    assertNull(valueFields.getTextValue());
    assertNull(valueFields.getTextValue2());
  }

  /**
   * Test {@link JsonType#setValue(Object, ValueFields)}.
   * <ul>
   *   <li>Given {@link SerializerFactory}
   * {@link SerializerFactory#createSerializer(SerializerProvider, JavaType)}
   * return {@link FloatSerializer} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue_givenSerializerFactoryCreateSerializerReturnFloatSerializer() throws JsonMappingException {
    // Arrange
    SerializerFactory f = mock(SerializerFactory.class);
    when(f.createSerializer(Mockito.<SerializerProvider>any(), Mockito.<JavaType>any()))
        .thenReturn(new NumberSerializers.FloatSerializer());
    when(f.createTypeSerializer(Mockito.<SerializationConfig>any(), Mockito.<JavaType>any()))
        .thenReturn(AsDeductionTypeSerializer.instance());

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializerFactory(f);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    objectMapper.addMixIn(target, mixinSource);
    JsonType jsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    jsonType.setValue(JSONObject.NULL, valueFields);

    // Assert
    verify(f).createSerializer(isA(SerializerProvider.class), isA(JavaType.class));
    verify(f).createTypeSerializer(isA(SerializationConfig.class), isA(JavaType.class));
    assertNull(valueFields.getTextValue());
    assertNull(valueFields.getTextValue2());
  }

  /**
   * Test {@link JsonType#setValue(Object, ValueFields)}.
   * <ul>
   *   <li>Then {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default
   * constructor) TextValue is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue_thenHistoricDetailVariableInstanceUpdateEntityImplTextValueIs42() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    JsonType jsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    jsonType.setValue(42, valueFields);

    // Assert
    assertEquals("42", valueFields.getTextValue());
    assertEquals("java.lang.Integer", valueFields.getTextValue2());
  }

  /**
   * Test {@link JsonType#setValue(Object, ValueFields)}.
   * <ul>
   *   <li>Then {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default
   * constructor) TextValue is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue_thenHistoricDetailVariableInstanceUpdateEntityImplTextValueIsNull() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    JsonType jsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    jsonType.setValue(JSONObject.NULL, valueFields);

    // Assert
    assertNull(valueFields.getTextValue());
    assertNull(valueFields.getTextValue2());
  }

  /**
   * Test {@link JsonType#setValue(Object, ValueFields)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#setValue(Object, ValueFields)}
   */
  @Test
  public void testSetValue_whenNull() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    JsonType jsonType = new JsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"));
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    jsonType.setValue(null, valueFields);

    // Assert
    assertEquals("null", valueFields.getTextValue());
    assertNull(valueFields.getTextValue2());
  }

  /**
   * Test {@link JsonType#isAbleToStore(Object)}.
   * <p>
   * Method under test: {@link JsonType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore() throws JsonMappingException {
    // Arrange
    SerializerFactory f = mock(SerializerFactory.class);
    when(f.createTypeSerializer(Mockito.<SerializationConfig>any(), Mockito.<JavaType>any()))
        .thenReturn(AsDeductionTypeSerializer.instance());
    Class<Object> type = Object.class;
    when(f.createSerializer(Mockito.<SerializerProvider>any(), Mockito.<JavaType>any()))
        .thenReturn(new StdKeySerializers.Default(1, type));

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializerFactory(f);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    objectMapper.addMixIn(target, mixinSource);

    // Act
    boolean actualIsAbleToStoreResult = (new JsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"))).isAbleToStore(JSONObject.NULL);

    // Assert
    verify(f).createSerializer(isA(SerializerProvider.class), isA(JavaType.class));
    verify(f).createTypeSerializer(isA(SerializationConfig.class), isA(JavaType.class));
    assertFalse(actualIsAbleToStoreResult);
  }

  /**
   * Test {@link JsonType#isAbleToStore(Object)}.
   * <p>
   * Method under test: {@link JsonType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore2() throws JsonMappingException {
    // Arrange
    SerializerFactory f = mock(SerializerFactory.class);
    when(f.createTypeSerializer(Mockito.<SerializationConfig>any(), Mockito.<JavaType>any())).thenReturn(null);
    Class<Object> type = Object.class;
    when(f.createSerializer(Mockito.<SerializerProvider>any(), Mockito.<JavaType>any()))
        .thenReturn(new StdKeySerializers.Default(1, type));

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializerFactory(f);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    objectMapper.addMixIn(target, mixinSource);

    // Act
    boolean actualIsAbleToStoreResult = (new JsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"))).isAbleToStore(JSONObject.NULL);

    // Assert
    verify(f).createSerializer(isA(SerializerProvider.class), isA(JavaType.class));
    verify(f).createTypeSerializer(isA(SerializationConfig.class), isA(JavaType.class));
    assertFalse(actualIsAbleToStoreResult);
  }

  /**
   * Test {@link JsonType#isAbleToStore(Object)}.
   * <p>
   * Method under test: {@link JsonType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore3() throws JsonMappingException {
    // Arrange
    SerializerFactory f = mock(SerializerFactory.class);
    Class<Object> type = Object.class;
    when(f.createSerializer(Mockito.<SerializerProvider>any(), Mockito.<JavaType>any()))
        .thenReturn(new StdKeySerializers.Default(1, type));

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializerFactory(f);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    objectMapper.addMixIn(target, mixinSource);

    // Act
    boolean actualIsAbleToStoreResult = (new JsonType(3, objectMapper, false,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"))).isAbleToStore(JSONObject.NULL);

    // Assert
    verify(f).createSerializer(isA(SerializerProvider.class), isA(JavaType.class));
    assertFalse(actualIsAbleToStoreResult);
  }

  /**
   * Test {@link JsonType#isAbleToStore(Object)}.
   * <p>
   * Method under test: {@link JsonType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore4() throws JsonMappingException {
    // Arrange
    SerializerFactory f = mock(SerializerFactory.class);
    when(f.createTypeSerializer(Mockito.<SerializationConfig>any(), Mockito.<JavaType>any())).thenReturn(null);
    Class<Object> type = Object.class;
    when(f.createSerializer(Mockito.<SerializerProvider>any(), Mockito.<JavaType>any()))
        .thenReturn(new StdKeySerializers.Default(4, type));

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializerFactory(f);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    objectMapper.addMixIn(target, mixinSource);

    // Act
    boolean actualIsAbleToStoreResult = (new JsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"))).isAbleToStore(JSONObject.NULL);

    // Assert
    verify(f).createSerializer(isA(SerializerProvider.class), isA(JavaType.class));
    verify(f).createTypeSerializer(isA(SerializationConfig.class), isA(JavaType.class));
    assertFalse(actualIsAbleToStoreResult);
  }

  /**
   * Test {@link JsonType#isAbleToStore(Object)}.
   * <p>
   * Method under test: {@link JsonType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore5() throws JsonMappingException {
    // Arrange
    SerializerFactory f = mock(SerializerFactory.class);
    when(f.createTypeSerializer(Mockito.<SerializationConfig>any(), Mockito.<JavaType>any())).thenReturn(null);
    Class<Object> type = Object.class;
    when(f.createSerializer(Mockito.<SerializerProvider>any(), Mockito.<JavaType>any()))
        .thenReturn(new StdKeySerializers.Default(7, type));

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializerFactory(f);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    objectMapper.addMixIn(target, mixinSource);

    // Act
    boolean actualIsAbleToStoreResult = (new JsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"))).isAbleToStore(JSONObject.NULL);

    // Assert
    verify(f).createSerializer(isA(SerializerProvider.class), isA(JavaType.class));
    verify(f).createTypeSerializer(isA(SerializationConfig.class), isA(JavaType.class));
    assertFalse(actualIsAbleToStoreResult);
  }

  /**
   * Test {@link JsonType#isAbleToStore(Object)}.
   * <p>
   * Method under test: {@link JsonType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore6() throws JsonMappingException {
    // Arrange
    SerializerFactory f = mock(SerializerFactory.class);
    when(f.createTypeSerializer(Mockito.<SerializationConfig>any(), Mockito.<JavaType>any())).thenReturn(null);
    Class<Object> type = Object.class;
    when(f.createSerializer(Mockito.<SerializerProvider>any(), Mockito.<JavaType>any()))
        .thenReturn(new StdKeySerializers.Default(8, type));

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializerFactory(f);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    objectMapper.addMixIn(target, mixinSource);

    // Act
    boolean actualIsAbleToStoreResult = (new JsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"))).isAbleToStore(JSONObject.NULL);

    // Assert
    verify(f).createSerializer(isA(SerializerProvider.class), isA(JavaType.class));
    verify(f).createTypeSerializer(isA(SerializationConfig.class), isA(JavaType.class));
    assertFalse(actualIsAbleToStoreResult);
  }

  /**
   * Test {@link JsonType#isAbleToStore(Object)}.
   * <p>
   * Method under test: {@link JsonType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore7() throws JsonMappingException {
    // Arrange
    SerializerFactory f = mock(SerializerFactory.class);
    when(f.createTypeSerializer(Mockito.<SerializationConfig>any(), Mockito.<JavaType>any())).thenReturn(null);
    when(f.createSerializer(Mockito.<SerializerProvider>any(), Mockito.<JavaType>any()))
        .thenReturn(new FailingSerializer("Msg"));

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializerFactory(f);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    objectMapper.addMixIn(target, mixinSource);

    // Act
    boolean actualIsAbleToStoreResult = (new JsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"))).isAbleToStore(JSONObject.NULL);

    // Assert
    verify(f).createSerializer(isA(SerializerProvider.class), isA(JavaType.class));
    verify(f).createTypeSerializer(isA(SerializationConfig.class), isA(JavaType.class));
    assertFalse(actualIsAbleToStoreResult);
  }

  /**
   * Test {@link JsonType#isAbleToStore(Object)}.
   * <ul>
   *   <li>Given {@code com.fasterxml.jackson.databind.JsonNode}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_givenComFasterxmlJacksonDatabindJsonNode() throws JsonMappingException {
    // Arrange
    SerializerFactory f = mock(SerializerFactory.class);
    when(f.createTypeSerializer(Mockito.<SerializationConfig>any(), Mockito.<JavaType>any()))
        .thenReturn(AsDeductionTypeSerializer.instance());
    Class<JsonNode> type = JsonNode.class;
    when(f.createSerializer(Mockito.<SerializerProvider>any(), Mockito.<JavaType>any()))
        .thenReturn(new StdKeySerializers.Default(1, type));

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializerFactory(f);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    objectMapper.addMixIn(target, mixinSource);

    // Act
    boolean actualIsAbleToStoreResult = (new JsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"))).isAbleToStore(JSONObject.NULL);

    // Assert
    verify(f).createSerializer(isA(SerializerProvider.class), isA(JavaType.class));
    verify(f).createTypeSerializer(isA(SerializationConfig.class), isA(JavaType.class));
    assertFalse(actualIsAbleToStoreResult);
  }

  /**
   * Test {@link JsonType#isAbleToStore(Object)}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_givenJavaLangObject_whenNull_thenReturnFalse() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    objectMapper.addMixIn(target, mixinSource);

    // Act and Assert
    assertFalse(
        (new JsonType(3, objectMapper, true, new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson")))
            .isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test {@link JsonType#isAbleToStore(Object)}.
   * <ul>
   *   <li>Given {@link ObjectMapper#ObjectMapper()} configure
   * {@code WRAP_ROOT_VALUE} and {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_givenObjectMapperConfigureWrapRootValueAndTrue() throws JsonMappingException {
    // Arrange
    SerializerFactory f = mock(SerializerFactory.class);
    when(f.createTypeSerializer(Mockito.<SerializationConfig>any(), Mockito.<JavaType>any()))
        .thenReturn(AsDeductionTypeSerializer.instance());
    Class<Object> type = Object.class;
    when(f.createSerializer(Mockito.<SerializerProvider>any(), Mockito.<JavaType>any()))
        .thenReturn(new StdKeySerializers.Default(1, type));

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
    objectMapper.setSerializerFactory(f);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    objectMapper.addMixIn(target, mixinSource);

    // Act
    boolean actualIsAbleToStoreResult = (new JsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"))).isAbleToStore(JSONObject.NULL);

    // Assert
    verify(f).createSerializer(isA(SerializerProvider.class), isA(JavaType.class));
    verify(f).createTypeSerializer(isA(SerializationConfig.class), isA(JavaType.class));
    assertFalse(actualIsAbleToStoreResult);
  }

  /**
   * Test {@link JsonType#isAbleToStore(Object)}.
   * <ul>
   *   <li>Given {@code org.activiti.engine.impl.variable.JsonType}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_givenOrgActivitiEngineImplVariableJsonType() throws JsonMappingException {
    // Arrange
    SerializerFactory f = mock(SerializerFactory.class);
    when(f.createTypeSerializer(Mockito.<SerializationConfig>any(), Mockito.<JavaType>any()))
        .thenReturn(AsDeductionTypeSerializer.instance());
    Class<JsonType> type = JsonType.class;
    when(f.createSerializer(Mockito.<SerializerProvider>any(), Mockito.<JavaType>any()))
        .thenReturn(new StdKeySerializers.Default(1, type));

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializerFactory(f);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    objectMapper.addMixIn(target, mixinSource);

    // Act
    boolean actualIsAbleToStoreResult = (new JsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"))).isAbleToStore(JSONObject.NULL);

    // Assert
    verify(f).createSerializer(isA(SerializerProvider.class), isA(JavaType.class));
    verify(f).createTypeSerializer(isA(SerializationConfig.class), isA(JavaType.class));
    assertFalse(actualIsAbleToStoreResult);
  }

  /**
   * Test {@link JsonType#isAbleToStore(Object)}.
   * <ul>
   *   <li>Given {@link SerializerFactory}
   * {@link SerializerFactory#createSerializer(SerializerProvider, JavaType)}
   * return {@link FloatSerializer} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_givenSerializerFactoryCreateSerializerReturnFloatSerializer()
      throws JsonMappingException {
    // Arrange
    SerializerFactory f = mock(SerializerFactory.class);
    when(f.createTypeSerializer(Mockito.<SerializationConfig>any(), Mockito.<JavaType>any()))
        .thenReturn(AsDeductionTypeSerializer.instance());
    when(f.createSerializer(Mockito.<SerializerProvider>any(), Mockito.<JavaType>any()))
        .thenReturn(new NumberSerializers.FloatSerializer());

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializerFactory(f);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    objectMapper.addMixIn(target, mixinSource);

    // Act
    boolean actualIsAbleToStoreResult = (new JsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"))).isAbleToStore(JSONObject.NULL);

    // Assert
    verify(f).createSerializer(isA(SerializerProvider.class), isA(JavaType.class));
    verify(f).createTypeSerializer(isA(SerializationConfig.class), isA(JavaType.class));
    assertFalse(actualIsAbleToStoreResult);
  }

  /**
   * Test {@link JsonType#isAbleToStore(Object)}.
   * <ul>
   *   <li>Given {@link SerializerFactory}
   * {@link SerializerFactory#createSerializer(SerializerProvider, JavaType)}
   * return {@link ToStringSerializer#ToStringSerializer()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_givenSerializerFactoryCreateSerializerReturnToStringSerializer()
      throws JsonMappingException {
    // Arrange
    SerializerFactory f = mock(SerializerFactory.class);
    when(f.createTypeSerializer(Mockito.<SerializationConfig>any(), Mockito.<JavaType>any()))
        .thenReturn(AsDeductionTypeSerializer.instance());
    when(f.createSerializer(Mockito.<SerializerProvider>any(), Mockito.<JavaType>any()))
        .thenReturn(new ToStringSerializer());

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializerFactory(f);
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    objectMapper.addMixIn(target, mixinSource);

    // Act
    boolean actualIsAbleToStoreResult = (new JsonType(3, objectMapper, true,
        new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson"))).isAbleToStore(JSONObject.NULL);

    // Assert
    verify(f).createSerializer(isA(SerializerProvider.class), isA(JavaType.class));
    verify(f).createTypeSerializer(isA(SerializationConfig.class), isA(JavaType.class));
    assertFalse(actualIsAbleToStoreResult);
  }

  /**
   * Test {@link JsonType#isAbleToStore(Object)}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_thenReturnFalse() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();

    // Act and Assert
    assertFalse(
        (new JsonType(3, objectMapper, true, new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson")))
            .isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test {@link JsonType#isAbleToStore(Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore_whenNull_thenReturnTrue() {
    // Arrange
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializerFactory(mock(SerializerFactory.class));
    Class<Object> target = Object.class;
    Class<Object> mixinSource = Object.class;
    objectMapper.addMixIn(target, mixinSource);

    // Act and Assert
    assertTrue(
        (new JsonType(3, objectMapper, true, new JsonTypeConverter(new ObjectMapper(), "Java Class Field For Jackson")))
            .isAbleToStore(null));
  }
}
