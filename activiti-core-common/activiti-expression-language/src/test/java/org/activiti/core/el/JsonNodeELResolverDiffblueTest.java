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
package org.activiti.core.el;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.cfg.CacheProvider;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import com.fasterxml.jackson.databind.cfg.DefaultCacheProvider;
import com.fasterxml.jackson.databind.cfg.DeserializerFactoryConfig;
import com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig;
import com.fasterxml.jackson.databind.deser.BeanDeserializerFactory;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.deser.DeserializerFactory;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.introspect.AccessorNamingStrategy;
import com.fasterxml.jackson.databind.introspect.BasicClassIntrospector;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.introspect.DefaultAccessorNamingStrategy;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.jsontype.impl.StdSubtypeResolver;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.DecimalNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.ser.impl.FailingSerializer;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ArrayIterator;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import jakarta.el.ELContext;
import jakarta.el.PropertyNotWritableException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.TimeZone;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JsonNodeELResolverDiffblueTest {
  /**
   * Test {@link JsonNodeELResolver#JsonNodeELResolver()}.
   * <p>
   * Method under test: {@link JsonNodeELResolver#JsonNodeELResolver()}
   */
  @Test
  @DisplayName("Test new JsonNodeELResolver()")
  void testNewJsonNodeELResolver() throws MissingResourceException {
    // Arrange, Act and Assert
    ObjectMapper objectMapper = (new JsonNodeELResolver()).getObjectMapper();
    SerializationConfig serializationConfig = objectMapper.getSerializationConfig();
    assertTrue(serializationConfig.getDefaultPrettyPrinter() instanceof DefaultPrettyPrinter);
    JsonFactory factory = objectMapper.getFactory();
    assertTrue(factory instanceof MappingJsonFactory);
    DeserializationConfig deserializationConfig = objectMapper.getDeserializationConfig();
    ContextAttributes attributes = deserializationConfig.getAttributes();
    assertTrue(attributes instanceof ContextAttributes.Impl);
    CacheProvider cacheProvider = deserializationConfig.getCacheProvider();
    assertTrue(cacheProvider instanceof DefaultCacheProvider);
    DeserializationContext deserializationContext = objectMapper.getDeserializationContext();
    DeserializerFactory factory2 = deserializationContext.getFactory();
    assertTrue(factory2 instanceof BeanDeserializerFactory);
    assertTrue(deserializationContext instanceof DefaultDeserializationContext.Impl);
    ClassIntrospector classIntrospector = deserializationConfig.getClassIntrospector();
    assertTrue(classIntrospector instanceof BasicClassIntrospector);
    AccessorNamingStrategy.Provider accessorNaming = deserializationConfig.getAccessorNaming();
    assertTrue(accessorNaming instanceof DefaultAccessorNamingStrategy.Provider);
    AnnotationIntrospector annotationIntrospector = deserializationConfig.getAnnotationIntrospector();
    assertTrue(annotationIntrospector instanceof JacksonAnnotationIntrospector);
    VisibilityChecker<?> visibilityChecker = objectMapper.getVisibilityChecker();
    assertTrue(visibilityChecker instanceof VisibilityChecker.Std);
    PolymorphicTypeValidator polymorphicTypeValidator = objectMapper.getPolymorphicTypeValidator();
    assertTrue(polymorphicTypeValidator instanceof LaissezFaireSubTypeValidator);
    SubtypeResolver subtypeResolver = objectMapper.getSubtypeResolver();
    assertTrue(subtypeResolver instanceof StdSubtypeResolver);
    SerializerFactory serializerFactory = objectMapper.getSerializerFactory();
    assertTrue(serializerFactory instanceof BeanSerializerFactory);
    SerializerProvider serializerProvider = objectMapper.getSerializerProvider();
    assertTrue(serializerProvider instanceof DefaultSerializerProvider.Impl);
    SerializerProvider serializerProviderInstance = objectMapper.getSerializerProviderInstance();
    assertTrue(serializerProviderInstance instanceof DefaultSerializerProvider.Impl);
    JsonSerializer<Object> defaultNullKeySerializer = serializerProvider.getDefaultNullKeySerializer();
    assertTrue(defaultNullKeySerializer instanceof FailingSerializer);
    JsonSerializer<Object> defaultNullValueSerializer = serializerProvider.getDefaultNullValueSerializer();
    assertTrue(defaultNullValueSerializer instanceof NullSerializer);
    DeserializerFactoryConfig factoryConfig = ((BeanDeserializerFactory) factory2).getFactoryConfig();
    Iterable<Deserializers> deserializersResult = factoryConfig.deserializers();
    assertTrue(deserializersResult instanceof ArrayIterator);
    SerializerFactoryConfig factoryConfig2 = ((BeanSerializerFactory) serializerFactory).getFactoryConfig();
    Iterable<Serializers> serializersResult = factoryConfig2.serializers();
    assertTrue(serializersResult instanceof ArrayIterator);
    DateFormat dateFormat = objectMapper.getDateFormat();
    assertTrue(dateFormat instanceof StdDateFormat);
    assertEquals(" ", factory.getRootValueSeparator());
    Locale locale = deserializationConfig.getLocale();
    assertEquals("", locale.getDisplayScript());
    assertEquals("", locale.getDisplayVariant());
    assertEquals("", locale.getScript());
    assertEquals("", locale.getVariant());
    TimeZone timeZone = deserializationConfig.getTimeZone();
    assertEquals("Coordinated Universal Time", timeZone.getDisplayName());
    assertEquals("English (United Kingdom)", locale.getDisplayName());
    assertEquals("English", locale.getDisplayLanguage());
    assertEquals("GB", locale.getCountry());
    assertEquals("GBR", locale.getISO3Country());
    assertEquals("JSON", factory.getFormatName());
    Base64Variant base64Variant = deserializationConfig.getBase64Variant();
    assertEquals("MIME-NO-LINEFEEDS", base64Variant.getName());
    assertEquals("MIME-NO-LINEFEEDS", base64Variant.toString());
    assertEquals("UTC", timeZone.getID());
    assertEquals("United Kingdom", locale.getDisplayCountry());
    assertEquals("[one of: 'yyyy-MM-dd'T'HH:mm:ss.SSSX', 'EEE, dd MMM yyyy HH:mm:ss zzz' (lenient)]",
        ((StdDateFormat) dateFormat).toPattern());
    Version versionResult = factory.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    Version versionResult2 = objectMapper.version();
    assertEquals("com.fasterxml.jackson.core", versionResult2.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-core/2.17.2", versionResult.toFullString());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult2.toFullString());
    assertEquals("en", locale.getLanguage());
    assertEquals("eng", locale.getISO3Language());
    assertEquals("jackson-core", versionResult.getArtifactId());
    assertEquals("jackson-databind", versionResult2.getArtifactId());
    assertEquals('=', base64Variant.getPaddingChar());
    assertNull(serializerProvider.getGenerator());
    assertNull(serializerProviderInstance.getGenerator());
    assertNull(deserializationContext.getParser());
    assertNull(factory.getCharacterEscapes());
    assertNull(factory.getInputDecorator());
    assertNull(factory.getOutputDecorator());
    assertNull(deserializationContext.getConfig());
    assertNull(objectMapper.getInjectableValues());
    assertNull(deserializationContext.getContextualType());
    assertNull(defaultNullKeySerializer.getDelegatee());
    assertNull(defaultNullValueSerializer.getDelegatee());
    assertNull(deserializationConfig.getFullRootName());
    assertNull(serializationConfig.getFullRootName());
    assertNull(objectMapper.getPropertyNamingStrategy());
    assertNull(deserializationConfig.getPropertyNamingStrategy());
    assertNull(serializationConfig.getPropertyNamingStrategy());
    assertNull(serializerProvider.getConfig());
    assertNull(deserializationConfig.getHandlerInstantiator());
    assertNull(serializationConfig.getHandlerInstantiator());
    assertNull(serializationConfig.getFilterProvider());
    assertNull(serializerProviderInstance.getFilterProvider());
    assertNull(deserializationConfig.getProblemHandlers());
    assertNull(deserializationConfig.getDefaultMergeable());
    assertNull(serializationConfig.getDefaultMergeable());
    assertNull(factory.getFormatReadFeatureType());
    assertNull(factory.getFormatWriteFeatureType());
    JsonInclude.Value defaultPropertyInclusion = deserializationConfig.getDefaultPropertyInclusion();
    assertNull(defaultPropertyInclusion.getContentFilter());
    assertNull(defaultPropertyInclusion.getValueFilter());
    assertNull(deserializationContext.getActiveView());
    assertNull(serializerProvider.getActiveView());
    assertNull(serializerProviderInstance.getActiveView());
    assertNull(deserializationConfig.getActiveView());
    assertNull(serializationConfig.getActiveView());
    TypeFactory typeFactory = objectMapper.getTypeFactory();
    assertNull(typeFactory.getClassLoader());
    assertNull(deserializationConfig.getRootName());
    assertNull(serializationConfig.getRootName());
    assertNull(dateFormat.getNumberFormat());
    assertNull(dateFormat.getCalendar());
    assertNull(dateFormat.getTimeZone());
    assertEquals(0, factory.getFormatGeneratorFeatures());
    assertEquals(0, factory.getFormatParserFeatures());
    assertEquals(0, deserializationContext.getDeserializationFeatures());
    assertEquals(0, timeZone.getDSTSavings());
    assertEquals(1, factory.getParserFeatures());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(17, versionResult2.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult2.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(2, versionResult2.getPatchLevel());
    assertEquals(2079, factory.getGeneratorFeatures());
    assertEquals(21771068, serializationConfig.getSerializationFeatures());
    assertEquals(31, factory.getFactoryFeatures());
    assertEquals(473998480, deserializationConfig.getDeserializationFeatures());
    JsonNodeFactory nodeFactory = objectMapper.getNodeFactory();
    assertEquals(9999, nodeFactory.getMaxElementIndexForInsert());
    assertEquals(JsonInclude.Include.ALWAYS, serializationConfig.getSerializationInclusion());
    assertEquals(JsonInclude.Include.USE_DEFAULTS, defaultPropertyInclusion.getContentInclusion());
    assertEquals(JsonInclude.Include.USE_DEFAULTS, defaultPropertyInclusion.getValueInclusion());
    JsonSetter.Value defaultSetterInfo = deserializationConfig.getDefaultSetterInfo();
    assertEquals(Nulls.DEFAULT, defaultSetterInfo.getContentNulls());
    assertEquals(Nulls.DEFAULT, defaultSetterInfo.getValueNulls());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult2.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult2.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(versionResult2.isUnknownVersion());
    assertFalse(defaultNullKeySerializer.isUnwrappingSerializer());
    assertFalse(defaultNullValueSerializer.isUnwrappingSerializer());
    assertFalse(factoryConfig.hasAbstractTypeResolvers());
    assertFalse(factoryConfig.hasDeserializerModifiers());
    assertFalse(factoryConfig.hasDeserializers());
    assertFalse(factoryConfig.hasValueInstantiators());
    assertFalse(deserializationConfig.hasExplicitTimeZone());
    assertFalse(serializationConfig.hasExplicitTimeZone());
    assertFalse(factoryConfig2.hasKeySerializers());
    assertFalse(factoryConfig2.hasSerializerModifiers());
    assertFalse(factoryConfig2.hasSerializers());
    assertFalse(((ArrayIterator<Deserializers>) deserializersResult).hasNext());
    assertFalse(((ArrayIterator<Serializers>) serializersResult).hasNext());
    assertFalse(locale.hasExtensions());
    assertTrue(factoryConfig.hasKeyDeserializers());
    assertTrue(deserializationConfig.isAnnotationProcessingEnabled());
    assertTrue(serializationConfig.isAnnotationProcessingEnabled());
    assertTrue(((StdDateFormat) dateFormat).isColonIncludedInTimeZone());
    assertTrue(dateFormat.isLenient());
    Set<Object> registeredModuleIds = objectMapper.getRegisteredModuleIds();
    assertTrue(registeredModuleIds.isEmpty());
    assertEquals(Integer.MAX_VALUE, base64Variant.getMaxLineLength());
    assertEquals('=', base64Variant.getPaddingByte());
    assertSame(nodeFactory, deserializationConfig.getNodeFactory());
    assertSame(registeredModuleIds, locale.getExtensionKeys());
    assertSame(registeredModuleIds, locale.getUnicodeLocaleAttributes());
    assertSame(registeredModuleIds, locale.getUnicodeLocaleKeys());
    assertSame(serializationConfig, serializerProviderInstance.getConfig());
    assertSame(typeFactory, serializerProviderInstance.getTypeFactory());
    assertSame(typeFactory, deserializationConfig.getTypeFactory());
    assertSame(typeFactory, serializationConfig.getTypeFactory());
    assertSame(versionResult2, annotationIntrospector.version());
    assertSame(base64Variant, serializationConfig.getBase64Variant());
    assertSame(locale, serializerProviderInstance.getLocale());
    assertSame(locale, serializationConfig.getLocale());
    assertSame(timeZone, serializerProviderInstance.getTimeZone());
    assertSame(timeZone, serializationConfig.getTimeZone());
    assertSame(defaultPropertyInclusion, serializationConfig.getDefaultPropertyInclusion());
    assertSame(defaultSetterInfo, serializationConfig.getDefaultSetterInfo());
    assertSame(objectMapper, factory.getCodec());
    assertSame(factory, objectMapper.getJsonFactory());
    assertSame(attributes, serializationConfig.getAttributes());
    assertSame(cacheProvider, serializationConfig.getCacheProvider());
    assertSame(classIntrospector, serializationConfig.getClassIntrospector());
    assertSame(accessorNaming, serializationConfig.getAccessorNaming());
    assertSame(annotationIntrospector, serializerProviderInstance.getAnnotationIntrospector());
    assertSame(annotationIntrospector, serializationConfig.getAnnotationIntrospector());
    assertSame(visibilityChecker, deserializationConfig.getDefaultVisibilityChecker());
    assertSame(visibilityChecker, serializationConfig.getDefaultVisibilityChecker());
    assertSame(polymorphicTypeValidator, deserializationConfig.getPolymorphicTypeValidator());
    assertSame(polymorphicTypeValidator, serializationConfig.getPolymorphicTypeValidator());
    assertSame(subtypeResolver, deserializationConfig.getSubtypeResolver());
    assertSame(subtypeResolver, serializationConfig.getSubtypeResolver());
    assertSame(defaultNullKeySerializer, serializerProviderInstance.getDefaultNullKeySerializer());
    assertSame(defaultNullValueSerializer, serializerProviderInstance.getDefaultNullValueSerializer());
    assertSame(dateFormat, deserializationConfig.getDateFormat());
    assertSame(dateFormat, serializationConfig.getDateFormat());
  }

  /**
   * Test {@link JsonNodeELResolver#JsonNodeELResolver(boolean)}.
   * <p>
   * Method under test: {@link JsonNodeELResolver#JsonNodeELResolver(boolean)}
   */
  @Test
  @DisplayName("Test new JsonNodeELResolver(boolean)")
  void testNewJsonNodeELResolver2() throws MissingResourceException {
    // Arrange, Act and Assert
    ObjectMapper objectMapper = (new JsonNodeELResolver(true)).getObjectMapper();
    SerializationConfig serializationConfig = objectMapper.getSerializationConfig();
    assertTrue(serializationConfig.getDefaultPrettyPrinter() instanceof DefaultPrettyPrinter);
    JsonFactory factory = objectMapper.getFactory();
    assertTrue(factory instanceof MappingJsonFactory);
    DeserializationConfig deserializationConfig = objectMapper.getDeserializationConfig();
    ContextAttributes attributes = deserializationConfig.getAttributes();
    assertTrue(attributes instanceof ContextAttributes.Impl);
    CacheProvider cacheProvider = deserializationConfig.getCacheProvider();
    assertTrue(cacheProvider instanceof DefaultCacheProvider);
    DeserializationContext deserializationContext = objectMapper.getDeserializationContext();
    DeserializerFactory factory2 = deserializationContext.getFactory();
    assertTrue(factory2 instanceof BeanDeserializerFactory);
    assertTrue(deserializationContext instanceof DefaultDeserializationContext.Impl);
    ClassIntrospector classIntrospector = deserializationConfig.getClassIntrospector();
    assertTrue(classIntrospector instanceof BasicClassIntrospector);
    AccessorNamingStrategy.Provider accessorNaming = deserializationConfig.getAccessorNaming();
    assertTrue(accessorNaming instanceof DefaultAccessorNamingStrategy.Provider);
    AnnotationIntrospector annotationIntrospector = deserializationConfig.getAnnotationIntrospector();
    assertTrue(annotationIntrospector instanceof JacksonAnnotationIntrospector);
    VisibilityChecker<?> visibilityChecker = objectMapper.getVisibilityChecker();
    assertTrue(visibilityChecker instanceof VisibilityChecker.Std);
    PolymorphicTypeValidator polymorphicTypeValidator = objectMapper.getPolymorphicTypeValidator();
    assertTrue(polymorphicTypeValidator instanceof LaissezFaireSubTypeValidator);
    SubtypeResolver subtypeResolver = objectMapper.getSubtypeResolver();
    assertTrue(subtypeResolver instanceof StdSubtypeResolver);
    SerializerFactory serializerFactory = objectMapper.getSerializerFactory();
    assertTrue(serializerFactory instanceof BeanSerializerFactory);
    SerializerProvider serializerProvider = objectMapper.getSerializerProvider();
    assertTrue(serializerProvider instanceof DefaultSerializerProvider.Impl);
    SerializerProvider serializerProviderInstance = objectMapper.getSerializerProviderInstance();
    assertTrue(serializerProviderInstance instanceof DefaultSerializerProvider.Impl);
    JsonSerializer<Object> defaultNullKeySerializer = serializerProvider.getDefaultNullKeySerializer();
    assertTrue(defaultNullKeySerializer instanceof FailingSerializer);
    JsonSerializer<Object> defaultNullValueSerializer = serializerProvider.getDefaultNullValueSerializer();
    assertTrue(defaultNullValueSerializer instanceof NullSerializer);
    DeserializerFactoryConfig factoryConfig = ((BeanDeserializerFactory) factory2).getFactoryConfig();
    Iterable<Deserializers> deserializersResult = factoryConfig.deserializers();
    assertTrue(deserializersResult instanceof ArrayIterator);
    SerializerFactoryConfig factoryConfig2 = ((BeanSerializerFactory) serializerFactory).getFactoryConfig();
    Iterable<Serializers> serializersResult = factoryConfig2.serializers();
    assertTrue(serializersResult instanceof ArrayIterator);
    DateFormat dateFormat = objectMapper.getDateFormat();
    assertTrue(dateFormat instanceof StdDateFormat);
    assertEquals(" ", factory.getRootValueSeparator());
    Locale locale = deserializationConfig.getLocale();
    assertEquals("", locale.getDisplayScript());
    assertEquals("", locale.getDisplayVariant());
    assertEquals("", locale.getScript());
    assertEquals("", locale.getVariant());
    TimeZone timeZone = deserializationConfig.getTimeZone();
    assertEquals("Coordinated Universal Time", timeZone.getDisplayName());
    assertEquals("English (United Kingdom)", locale.getDisplayName());
    assertEquals("English", locale.getDisplayLanguage());
    assertEquals("GB", locale.getCountry());
    assertEquals("GBR", locale.getISO3Country());
    assertEquals("JSON", factory.getFormatName());
    Base64Variant base64Variant = deserializationConfig.getBase64Variant();
    assertEquals("MIME-NO-LINEFEEDS", base64Variant.getName());
    assertEquals("MIME-NO-LINEFEEDS", base64Variant.toString());
    assertEquals("UTC", timeZone.getID());
    assertEquals("United Kingdom", locale.getDisplayCountry());
    assertEquals("[one of: 'yyyy-MM-dd'T'HH:mm:ss.SSSX', 'EEE, dd MMM yyyy HH:mm:ss zzz' (lenient)]",
        ((StdDateFormat) dateFormat).toPattern());
    Version versionResult = factory.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    Version versionResult2 = objectMapper.version();
    assertEquals("com.fasterxml.jackson.core", versionResult2.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-core/2.17.2", versionResult.toFullString());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult2.toFullString());
    assertEquals("en", locale.getLanguage());
    assertEquals("eng", locale.getISO3Language());
    assertEquals("jackson-core", versionResult.getArtifactId());
    assertEquals("jackson-databind", versionResult2.getArtifactId());
    assertEquals('=', base64Variant.getPaddingChar());
    assertNull(serializerProvider.getGenerator());
    assertNull(serializerProviderInstance.getGenerator());
    assertNull(deserializationContext.getParser());
    assertNull(factory.getCharacterEscapes());
    assertNull(factory.getInputDecorator());
    assertNull(factory.getOutputDecorator());
    assertNull(deserializationContext.getConfig());
    assertNull(objectMapper.getInjectableValues());
    assertNull(deserializationContext.getContextualType());
    assertNull(defaultNullKeySerializer.getDelegatee());
    assertNull(defaultNullValueSerializer.getDelegatee());
    assertNull(deserializationConfig.getFullRootName());
    assertNull(serializationConfig.getFullRootName());
    assertNull(objectMapper.getPropertyNamingStrategy());
    assertNull(deserializationConfig.getPropertyNamingStrategy());
    assertNull(serializationConfig.getPropertyNamingStrategy());
    assertNull(serializerProvider.getConfig());
    assertNull(deserializationConfig.getHandlerInstantiator());
    assertNull(serializationConfig.getHandlerInstantiator());
    assertNull(serializationConfig.getFilterProvider());
    assertNull(serializerProviderInstance.getFilterProvider());
    assertNull(deserializationConfig.getProblemHandlers());
    assertNull(deserializationConfig.getDefaultMergeable());
    assertNull(serializationConfig.getDefaultMergeable());
    assertNull(factory.getFormatReadFeatureType());
    assertNull(factory.getFormatWriteFeatureType());
    JsonInclude.Value defaultPropertyInclusion = deserializationConfig.getDefaultPropertyInclusion();
    assertNull(defaultPropertyInclusion.getContentFilter());
    assertNull(defaultPropertyInclusion.getValueFilter());
    assertNull(deserializationContext.getActiveView());
    assertNull(serializerProvider.getActiveView());
    assertNull(serializerProviderInstance.getActiveView());
    assertNull(deserializationConfig.getActiveView());
    assertNull(serializationConfig.getActiveView());
    TypeFactory typeFactory = objectMapper.getTypeFactory();
    assertNull(typeFactory.getClassLoader());
    assertNull(deserializationConfig.getRootName());
    assertNull(serializationConfig.getRootName());
    assertNull(dateFormat.getNumberFormat());
    assertNull(dateFormat.getCalendar());
    assertNull(dateFormat.getTimeZone());
    assertEquals(0, factory.getFormatGeneratorFeatures());
    assertEquals(0, factory.getFormatParserFeatures());
    assertEquals(0, deserializationContext.getDeserializationFeatures());
    assertEquals(0, timeZone.getDSTSavings());
    assertEquals(1, factory.getParserFeatures());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(17, versionResult2.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult2.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(2, versionResult2.getPatchLevel());
    assertEquals(2079, factory.getGeneratorFeatures());
    assertEquals(21771068, serializationConfig.getSerializationFeatures());
    assertEquals(31, factory.getFactoryFeatures());
    assertEquals(473998480, deserializationConfig.getDeserializationFeatures());
    JsonNodeFactory nodeFactory = objectMapper.getNodeFactory();
    assertEquals(9999, nodeFactory.getMaxElementIndexForInsert());
    assertEquals(JsonInclude.Include.ALWAYS, serializationConfig.getSerializationInclusion());
    assertEquals(JsonInclude.Include.USE_DEFAULTS, defaultPropertyInclusion.getContentInclusion());
    assertEquals(JsonInclude.Include.USE_DEFAULTS, defaultPropertyInclusion.getValueInclusion());
    JsonSetter.Value defaultSetterInfo = deserializationConfig.getDefaultSetterInfo();
    assertEquals(Nulls.DEFAULT, defaultSetterInfo.getContentNulls());
    assertEquals(Nulls.DEFAULT, defaultSetterInfo.getValueNulls());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult2.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult2.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(versionResult2.isUnknownVersion());
    assertFalse(defaultNullKeySerializer.isUnwrappingSerializer());
    assertFalse(defaultNullValueSerializer.isUnwrappingSerializer());
    assertFalse(factoryConfig.hasAbstractTypeResolvers());
    assertFalse(factoryConfig.hasDeserializerModifiers());
    assertFalse(factoryConfig.hasDeserializers());
    assertFalse(factoryConfig.hasValueInstantiators());
    assertFalse(deserializationConfig.hasExplicitTimeZone());
    assertFalse(serializationConfig.hasExplicitTimeZone());
    assertFalse(factoryConfig2.hasKeySerializers());
    assertFalse(factoryConfig2.hasSerializerModifiers());
    assertFalse(factoryConfig2.hasSerializers());
    assertFalse(((ArrayIterator<Deserializers>) deserializersResult).hasNext());
    assertFalse(((ArrayIterator<Serializers>) serializersResult).hasNext());
    assertFalse(locale.hasExtensions());
    assertTrue(factoryConfig.hasKeyDeserializers());
    assertTrue(deserializationConfig.isAnnotationProcessingEnabled());
    assertTrue(serializationConfig.isAnnotationProcessingEnabled());
    assertTrue(((StdDateFormat) dateFormat).isColonIncludedInTimeZone());
    assertTrue(dateFormat.isLenient());
    Set<Object> registeredModuleIds = objectMapper.getRegisteredModuleIds();
    assertTrue(registeredModuleIds.isEmpty());
    assertEquals(Integer.MAX_VALUE, base64Variant.getMaxLineLength());
    assertEquals('=', base64Variant.getPaddingByte());
    assertSame(nodeFactory, deserializationConfig.getNodeFactory());
    assertSame(registeredModuleIds, locale.getExtensionKeys());
    assertSame(registeredModuleIds, locale.getUnicodeLocaleAttributes());
    assertSame(registeredModuleIds, locale.getUnicodeLocaleKeys());
    assertSame(serializationConfig, serializerProviderInstance.getConfig());
    assertSame(typeFactory, serializerProviderInstance.getTypeFactory());
    assertSame(typeFactory, deserializationConfig.getTypeFactory());
    assertSame(typeFactory, serializationConfig.getTypeFactory());
    assertSame(versionResult2, annotationIntrospector.version());
    assertSame(base64Variant, serializationConfig.getBase64Variant());
    assertSame(locale, serializerProviderInstance.getLocale());
    assertSame(locale, serializationConfig.getLocale());
    assertSame(timeZone, serializerProviderInstance.getTimeZone());
    assertSame(timeZone, serializationConfig.getTimeZone());
    assertSame(defaultPropertyInclusion, serializationConfig.getDefaultPropertyInclusion());
    assertSame(defaultSetterInfo, serializationConfig.getDefaultSetterInfo());
    assertSame(objectMapper, factory.getCodec());
    assertSame(factory, objectMapper.getJsonFactory());
    assertSame(attributes, serializationConfig.getAttributes());
    assertSame(cacheProvider, serializationConfig.getCacheProvider());
    assertSame(classIntrospector, serializationConfig.getClassIntrospector());
    assertSame(accessorNaming, serializationConfig.getAccessorNaming());
    assertSame(annotationIntrospector, serializerProviderInstance.getAnnotationIntrospector());
    assertSame(annotationIntrospector, serializationConfig.getAnnotationIntrospector());
    assertSame(visibilityChecker, deserializationConfig.getDefaultVisibilityChecker());
    assertSame(visibilityChecker, serializationConfig.getDefaultVisibilityChecker());
    assertSame(polymorphicTypeValidator, deserializationConfig.getPolymorphicTypeValidator());
    assertSame(polymorphicTypeValidator, serializationConfig.getPolymorphicTypeValidator());
    assertSame(subtypeResolver, deserializationConfig.getSubtypeResolver());
    assertSame(subtypeResolver, serializationConfig.getSubtypeResolver());
    assertSame(defaultNullKeySerializer, serializerProviderInstance.getDefaultNullKeySerializer());
    assertSame(defaultNullValueSerializer, serializerProviderInstance.getDefaultNullValueSerializer());
    assertSame(dateFormat, deserializationConfig.getDateFormat());
    assertSame(dateFormat, serializationConfig.getDateFormat());
  }

  /**
   * Test {@link JsonNodeELResolver#getCommonPropertyType(ELContext, Object)}.
   * <ul>
   *   <li>Given {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonNodeELResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getCommonPropertyType(ELContext, Object); given 'Name'")
  void testGetCommonPropertyType_givenName() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver();

    ActivitiElContext context = new ActivitiElContext();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    context.setVariable("Name", new ObjectValueExpression(converter, "Object", type));

    // Act and Assert
    assertNull(jsonNodeELResolver.getCommonPropertyType(context, "Base"));
  }

  /**
   * Test {@link JsonNodeELResolver#getCommonPropertyType(ELContext, Object)}.
   * <ul>
   *   <li>When {@link ActivitiElContext#ActivitiElContext()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonNodeELResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getCommonPropertyType(ELContext, Object); when ActivitiElContext(); then return 'null'")
  void testGetCommonPropertyType_whenActivitiElContext_thenReturnNull() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver();

    // Act and Assert
    assertNull(jsonNodeELResolver.getCommonPropertyType(new ActivitiElContext(), "Base"));
  }

  /**
   * Test {@link JsonNodeELResolver#getCommonPropertyType(ELContext, Object)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return {@link Object}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonNodeELResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getCommonPropertyType(ELContext, Object); when Instance; then return Object")
  void testGetCommonPropertyType_whenInstance_thenReturnObject() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver();
    ActivitiElContext context = new ActivitiElContext();

    // Act
    Class<?> actualCommonPropertyType = jsonNodeELResolver.getCommonPropertyType(context, MissingNode.getInstance());

    // Assert
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, actualCommonPropertyType);
  }

  /**
   * Test {@link JsonNodeELResolver#getFeatureDescriptors(ELContext, Object)}.
   * <ul>
   *   <li>Given {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonNodeELResolver#getFeatureDescriptors(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getFeatureDescriptors(ELContext, Object); given 'Name'")
  void testGetFeatureDescriptors_givenName() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver();

    ActivitiElContext context = new ActivitiElContext();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    context.setVariable("Name", new ObjectValueExpression(converter, "Object", type));

    // Act and Assert
    assertNull(jsonNodeELResolver.getFeatureDescriptors(context, "Base"));
  }

  /**
   * Test {@link JsonNodeELResolver#getFeatureDescriptors(ELContext, Object)}.
   * <ul>
   *   <li>When {@link ActivitiElContext#ActivitiElContext()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonNodeELResolver#getFeatureDescriptors(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getFeatureDescriptors(ELContext, Object); when ActivitiElContext(); then return 'null'")
  void testGetFeatureDescriptors_whenActivitiElContext_thenReturnNull() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver();

    // Act and Assert
    assertNull(jsonNodeELResolver.getFeatureDescriptors(new ActivitiElContext(), "Base"));
  }

  /**
   * Test {@link JsonNodeELResolver#getFeatureDescriptors(ELContext, Object)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return not hasNext.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonNodeELResolver#getFeatureDescriptors(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getFeatureDescriptors(ELContext, Object); when Instance; then return not hasNext")
  void testGetFeatureDescriptors_whenInstance_thenReturnNotHasNext() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver();
    ActivitiElContext context = new ActivitiElContext();

    // Act and Assert
    assertFalse(jsonNodeELResolver.getFeatureDescriptors(context, MissingNode.getInstance()).hasNext());
  }

  /**
   * Test {@link JsonNodeELResolver#getType(ELContext, Object, Object)}.
   * <ul>
   *   <li>Given {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonNodeELResolver#getType(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getType(ELContext, Object, Object); given 'Name'")
  void testGetType_givenName() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver();

    ActivitiElContext context = new ActivitiElContext();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    context.setVariable("Name", new ObjectValueExpression(converter, "Object", type));

    // Act and Assert
    assertNull(jsonNodeELResolver.getType(context, "Base", "Property"));
    assertFalse(context.isPropertyResolved());
  }

  /**
   * Test {@link JsonNodeELResolver#getType(ELContext, Object, Object)}.
   * <ul>
   *   <li>When {@link ActivitiElContext#ActivitiElContext()}.</li>
   *   <li>Then {@link ActivitiElContext#ActivitiElContext()} PropertyResolved.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonNodeELResolver#getType(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getType(ELContext, Object, Object); when ActivitiElContext(); then ActivitiElContext() PropertyResolved")
  void testGetType_whenActivitiElContext_thenActivitiElContextPropertyResolved() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver();
    ActivitiElContext context = new ActivitiElContext();

    // Act
    Class<?> actualType = jsonNodeELResolver.getType(context, MissingNode.getInstance(), "Property");

    // Assert
    assertTrue(context.isPropertyResolved());
    Class<Object> expectedType = Object.class;
    assertEquals(expectedType, actualType);
  }

  /**
   * Test {@link JsonNodeELResolver#getType(ELContext, Object, Object)}.
   * <ul>
   *   <li>When {@link ActivitiElContext#ActivitiElContext()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonNodeELResolver#getType(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getType(ELContext, Object, Object); when ActivitiElContext(); then return 'null'")
  void testGetType_whenActivitiElContext_thenReturnNull() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver();
    ActivitiElContext context = new ActivitiElContext();

    // Act and Assert
    assertNull(jsonNodeELResolver.getType(context, "Base", "Property"));
    assertFalse(context.isPropertyResolved());
  }

  /**
   * Test {@link JsonNodeELResolver#getValue(ELContext, Object, Object)}.
   * <ul>
   *   <li>Given {@link JsonNodeELResolver#JsonNodeELResolver()}.</li>
   *   <li>When {@code Base}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonNodeELResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getValue(ELContext, Object, Object); given JsonNodeELResolver(); when 'Base'; then return 'null'")
  void testGetValue_givenJsonNodeELResolver_whenBase_thenReturnNull() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver();

    // Act and Assert
    assertNull(jsonNodeELResolver.getValue(new ActivitiElContext(), "Base", "Property"));
  }

  /**
   * Test {@link JsonNodeELResolver#getValue(ELContext, Object, Object)}.
   * <ul>
   *   <li>Given {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonNodeELResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getValue(ELContext, Object, Object); given 'Name'")
  void testGetValue_givenName() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver();

    ActivitiElContext context = new ActivitiElContext();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    context.setVariable("Name", new ObjectValueExpression(converter, "Object", type));

    // Act and Assert
    assertNull(jsonNodeELResolver.getValue(context, "Base", "Property"));
  }

  /**
   * Test {@link JsonNodeELResolver#getObjectMapper()}.
   * <p>
   * Method under test: {@link JsonNodeELResolver#getObjectMapper()}
   */
  @Test
  @DisplayName("Test getObjectMapper()")
  void testGetObjectMapper() throws MissingResourceException {
    // Arrange and Act
    ObjectMapper actualObjectMapper = (new JsonNodeELResolver()).getObjectMapper();

    // Assert
    SerializationConfig serializationConfig = actualObjectMapper.getSerializationConfig();
    assertTrue(serializationConfig.getDefaultPrettyPrinter() instanceof DefaultPrettyPrinter);
    JsonFactory factory = actualObjectMapper.getFactory();
    assertTrue(factory instanceof MappingJsonFactory);
    DeserializationConfig deserializationConfig = actualObjectMapper.getDeserializationConfig();
    ContextAttributes attributes = deserializationConfig.getAttributes();
    assertTrue(attributes instanceof ContextAttributes.Impl);
    CacheProvider cacheProvider = deserializationConfig.getCacheProvider();
    assertTrue(cacheProvider instanceof DefaultCacheProvider);
    assertTrue(actualObjectMapper.getDeserializationContext() instanceof DefaultDeserializationContext.Impl);
    ClassIntrospector classIntrospector = deserializationConfig.getClassIntrospector();
    assertTrue(classIntrospector instanceof BasicClassIntrospector);
    AccessorNamingStrategy.Provider accessorNaming = deserializationConfig.getAccessorNaming();
    assertTrue(accessorNaming instanceof DefaultAccessorNamingStrategy.Provider);
    AnnotationIntrospector annotationIntrospector = deserializationConfig.getAnnotationIntrospector();
    assertTrue(annotationIntrospector instanceof JacksonAnnotationIntrospector);
    VisibilityChecker<?> visibilityChecker = actualObjectMapper.getVisibilityChecker();
    assertTrue(visibilityChecker instanceof VisibilityChecker.Std);
    PolymorphicTypeValidator polymorphicTypeValidator = actualObjectMapper.getPolymorphicTypeValidator();
    assertTrue(polymorphicTypeValidator instanceof LaissezFaireSubTypeValidator);
    SubtypeResolver subtypeResolver = actualObjectMapper.getSubtypeResolver();
    assertTrue(subtypeResolver instanceof StdSubtypeResolver);
    assertTrue(actualObjectMapper.getSerializerFactory() instanceof BeanSerializerFactory);
    assertTrue(actualObjectMapper.getSerializerProvider() instanceof DefaultSerializerProvider.Impl);
    assertTrue(actualObjectMapper.getSerializerProviderInstance() instanceof DefaultSerializerProvider.Impl);
    DateFormat dateFormat = actualObjectMapper.getDateFormat();
    assertTrue(dateFormat instanceof StdDateFormat);
    Locale locale = deserializationConfig.getLocale();
    assertEquals("", locale.getDisplayScript());
    assertEquals("", locale.getDisplayVariant());
    assertEquals("", locale.getScript());
    assertEquals("", locale.getVariant());
    TimeZone timeZone = deserializationConfig.getTimeZone();
    assertEquals("Coordinated Universal Time", timeZone.getDisplayName());
    assertEquals("English (United Kingdom)", locale.getDisplayName());
    assertEquals("English", locale.getDisplayLanguage());
    assertEquals("GB", locale.getCountry());
    assertEquals("GBR", locale.getISO3Country());
    Base64Variant base64Variant = deserializationConfig.getBase64Variant();
    assertEquals("MIME-NO-LINEFEEDS", base64Variant.getName());
    assertEquals("MIME-NO-LINEFEEDS", base64Variant.toString());
    assertEquals("UTC", timeZone.getID());
    assertEquals("United Kingdom", locale.getDisplayCountry());
    Version versionResult = actualObjectMapper.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals("com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
    assertEquals("en", locale.getLanguage());
    assertEquals("eng", locale.getISO3Language());
    assertEquals("jackson-databind", versionResult.getArtifactId());
    assertEquals('=', base64Variant.getPaddingChar());
    assertNull(actualObjectMapper.getInjectableValues());
    assertNull(deserializationConfig.getFullRootName());
    assertNull(serializationConfig.getFullRootName());
    assertNull(actualObjectMapper.getPropertyNamingStrategy());
    assertNull(deserializationConfig.getPropertyNamingStrategy());
    assertNull(serializationConfig.getPropertyNamingStrategy());
    assertNull(deserializationConfig.getHandlerInstantiator());
    assertNull(serializationConfig.getHandlerInstantiator());
    assertNull(serializationConfig.getFilterProvider());
    assertNull(deserializationConfig.getProblemHandlers());
    assertNull(deserializationConfig.getDefaultMergeable());
    assertNull(serializationConfig.getDefaultMergeable());
    JsonInclude.Value defaultPropertyInclusion = deserializationConfig.getDefaultPropertyInclusion();
    assertNull(defaultPropertyInclusion.getContentFilter());
    assertNull(defaultPropertyInclusion.getValueFilter());
    assertNull(deserializationConfig.getActiveView());
    assertNull(serializationConfig.getActiveView());
    assertNull(actualObjectMapper.getTypeFactory().getClassLoader());
    assertNull(deserializationConfig.getRootName());
    assertNull(serializationConfig.getRootName());
    assertEquals(0, timeZone.getDSTSavings());
    assertEquals(17, versionResult.getMinorVersion());
    assertEquals(2, versionResult.getMajorVersion());
    assertEquals(2, versionResult.getPatchLevel());
    assertEquals(21771068, serializationConfig.getSerializationFeatures());
    assertEquals(473998480, deserializationConfig.getDeserializationFeatures());
    assertEquals(9999, actualObjectMapper.getNodeFactory().getMaxElementIndexForInsert());
    assertEquals(JsonInclude.Include.ALWAYS, serializationConfig.getSerializationInclusion());
    assertEquals(JsonInclude.Include.USE_DEFAULTS, defaultPropertyInclusion.getContentInclusion());
    assertEquals(JsonInclude.Include.USE_DEFAULTS, defaultPropertyInclusion.getValueInclusion());
    JsonSetter.Value defaultSetterInfo = deserializationConfig.getDefaultSetterInfo();
    assertEquals(Nulls.DEFAULT, defaultSetterInfo.getContentNulls());
    assertEquals(Nulls.DEFAULT, defaultSetterInfo.getValueNulls());
    assertFalse(versionResult.isSnapshot());
    assertFalse(versionResult.isUknownVersion());
    assertFalse(versionResult.isUnknownVersion());
    assertFalse(deserializationConfig.hasExplicitTimeZone());
    assertFalse(serializationConfig.hasExplicitTimeZone());
    assertFalse(locale.hasExtensions());
    assertTrue(deserializationConfig.isAnnotationProcessingEnabled());
    assertTrue(serializationConfig.isAnnotationProcessingEnabled());
    assertTrue(actualObjectMapper.getRegisteredModuleIds().isEmpty());
    assertEquals(Integer.MAX_VALUE, base64Variant.getMaxLineLength());
    assertEquals('=', base64Variant.getPaddingByte());
    assertSame(factory, actualObjectMapper.getJsonFactory());
    assertSame(attributes, serializationConfig.getAttributes());
    assertSame(cacheProvider, serializationConfig.getCacheProvider());
    assertSame(classIntrospector, serializationConfig.getClassIntrospector());
    assertSame(accessorNaming, serializationConfig.getAccessorNaming());
    assertSame(annotationIntrospector, serializationConfig.getAnnotationIntrospector());
    assertSame(visibilityChecker, deserializationConfig.getDefaultVisibilityChecker());
    assertSame(visibilityChecker, serializationConfig.getDefaultVisibilityChecker());
    assertSame(polymorphicTypeValidator, deserializationConfig.getPolymorphicTypeValidator());
    assertSame(polymorphicTypeValidator, serializationConfig.getPolymorphicTypeValidator());
    assertSame(subtypeResolver, deserializationConfig.getSubtypeResolver());
    assertSame(subtypeResolver, serializationConfig.getSubtypeResolver());
    assertSame(dateFormat, deserializationConfig.getDateFormat());
    assertSame(dateFormat, serializationConfig.getDateFormat());
  }

  /**
   * Test {@link JsonNodeELResolver#isReadOnly(ELContext, Object, Object)}.
   * <ul>
   *   <li>Given {@link JsonNodeELResolver#JsonNodeELResolver(boolean)} with
   * readOnly is {@code true}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonNodeELResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test isReadOnly(ELContext, Object, Object); given JsonNodeELResolver(boolean) with readOnly is 'true'; then return 'true'")
  void testIsReadOnly_givenJsonNodeELResolverWithReadOnlyIsTrue_thenReturnTrue() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver(true);
    ActivitiElContext context = new ActivitiElContext();

    // Act
    boolean actualIsReadOnlyResult = jsonNodeELResolver.isReadOnly(context, "Base", "Property");

    // Assert
    assertFalse(context.isPropertyResolved());
    assertTrue(actualIsReadOnlyResult);
  }

  /**
   * Test {@link JsonNodeELResolver#isReadOnly(ELContext, Object, Object)}.
   * <ul>
   *   <li>Given {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonNodeELResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test isReadOnly(ELContext, Object, Object); given 'Name'")
  void testIsReadOnly_givenName() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver();

    ActivitiElContext context = new ActivitiElContext();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    context.setVariable("Name", new ObjectValueExpression(converter, "Object", type));

    // Act
    boolean actualIsReadOnlyResult = jsonNodeELResolver.isReadOnly(context, "Base", "Property");

    // Assert
    assertFalse(context.isPropertyResolved());
    assertFalse(actualIsReadOnlyResult);
  }

  /**
   * Test {@link JsonNodeELResolver#isReadOnly(ELContext, Object, Object)}.
   * <ul>
   *   <li>When {@link ActivitiElContext#ActivitiElContext()}.</li>
   *   <li>Then not {@link ActivitiElContext#ActivitiElContext()}
   * PropertyResolved.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonNodeELResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test isReadOnly(ELContext, Object, Object); when ActivitiElContext(); then not ActivitiElContext() PropertyResolved")
  void testIsReadOnly_whenActivitiElContext_thenNotActivitiElContextPropertyResolved() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver();
    ActivitiElContext context = new ActivitiElContext();

    // Act
    boolean actualIsReadOnlyResult = jsonNodeELResolver.isReadOnly(context, "Base", "Property");

    // Assert
    assertFalse(context.isPropertyResolved());
    assertFalse(actualIsReadOnlyResult);
  }

  /**
   * Test {@link JsonNodeELResolver#isReadOnly(ELContext, Object, Object)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then {@link ActivitiElContext#ActivitiElContext()} PropertyResolved.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonNodeELResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test isReadOnly(ELContext, Object, Object); when Instance; then ActivitiElContext() PropertyResolved")
  void testIsReadOnly_whenInstance_thenActivitiElContextPropertyResolved() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver();
    ActivitiElContext context = new ActivitiElContext();

    // Act and Assert
    assertFalse(jsonNodeELResolver.isReadOnly(context, MissingNode.getInstance(), "Property"));
    assertTrue(context.isPropertyResolved());
  }

  /**
   * Test {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}.
   * <p>
   * Method under test:
   * {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName("Test setValue(ELContext, Object, Object, Object)")
  void testSetValue() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver();
    ActivitiElContext context = new ActivitiElContext();
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    jsonNodeELResolver.setValue(context, objectNode, "Property", "Value");

    // Assert
    Iterator<JsonNode> iteratorResult = objectNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof TextNode);
    assertEquals("\"Value\"", nextResult.toPrettyString());
    assertEquals("{\n  \"Property\" : \"Value\"\n}", objectNode.toPrettyString());
    assertEquals(JsonNodeType.STRING, nextResult.getNodeType());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult.isTextual());
  }

  /**
   * Test {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}.
   * <p>
   * Method under test:
   * {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName("Test setValue(ELContext, Object, Object, Object)")
  void testSetValue2() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver();
    ActivitiElContext context = new ActivitiElContext();
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    jsonNodeELResolver.setValue(context, objectNode, "Property", true);

    // Assert
    Iterator<JsonNode> iteratorResult = objectNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof BooleanNode);
    assertEquals("{\n  \"Property\" : true\n}", objectNode.toPrettyString());
    assertEquals(JsonNodeType.BOOLEAN, nextResult.getNodeType());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult.isBoolean());
    String expectedToPrettyStringResult = Boolean.TRUE.toString();
    assertEquals(expectedToPrettyStringResult, nextResult.toPrettyString());
  }

  /**
   * Test {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}.
   * <p>
   * Method under test:
   * {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName("Test setValue(ELContext, Object, Object, Object)")
  void testSetValue3() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver();
    ActivitiElContext context = new ActivitiElContext();
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    jsonNodeELResolver.setValue(context, objectNode, "Property", false);

    // Assert
    Iterator<JsonNode> iteratorResult = objectNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof BooleanNode);
    assertEquals("{\n  \"Property\" : false\n}", objectNode.toPrettyString());
    assertEquals(JsonNodeType.BOOLEAN, nextResult.getNodeType());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult.isBoolean());
    String expectedToPrettyStringResult = Boolean.FALSE.toString();
    assertEquals(expectedToPrettyStringResult, nextResult.toPrettyString());
  }

  /**
   * Test {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}.
   * <p>
   * Method under test:
   * {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName("Test setValue(ELContext, Object, Object, Object)")
  void testSetValue4() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver();
    ActivitiElContext context = new ActivitiElContext();
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    jsonNodeELResolver.setValue(context, objectNode, "Property",
        new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Assert
    Iterator<JsonNode> iteratorResult = objectNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof TextNode);
    assertEquals("\"{}\"", nextResult.toPrettyString());
    assertEquals("{\n  \"Property\" : \"{}\"\n}", objectNode.toPrettyString());
    assertEquals(JsonNodeType.STRING, nextResult.getNodeType());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult.isTextual());
  }

  /**
   * Test {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}.
   * <p>
   * Method under test:
   * {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName("Test setValue(ELContext, Object, Object, Object)")
  void testSetValue5() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver();
    ActivitiElContext context = new ActivitiElContext();
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    jsonNodeELResolver.setValue(context, objectNode, "Property", new BigDecimal("2.3"));

    // Assert
    Iterator<JsonNode> iteratorResult = objectNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof DecimalNode);
    assertEquals("2.3", nextResult.toPrettyString());
    assertEquals("{\n  \"Property\" : 2.3\n}", objectNode.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult.isBigDecimal());
    assertTrue(nextResult.isFloatingPointNumber());
  }

  /**
   * Test {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}.
   * <p>
   * Method under test:
   * {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName("Test setValue(ELContext, Object, Object, Object)")
  void testSetValue6() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver();
    ActivitiElContext context = new ActivitiElContext();
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    jsonNodeELResolver.setValue(context, objectNode, "Property", 10.0d);

    // Assert
    Iterator<JsonNode> iteratorResult = objectNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof DoubleNode);
    assertEquals("10.0", nextResult.toPrettyString());
    assertEquals("{\n  \"Property\" : 10.0\n}", objectNode.toPrettyString());
    assertFalse(((DoubleNode) nextResult).isNaN());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult.isDouble());
    assertTrue(nextResult.isFloatingPointNumber());
  }

  /**
   * Test {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}.
   * <p>
   * Method under test:
   * {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName("Test setValue(ELContext, Object, Object, Object)")
  void testSetValue7() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver();
    ActivitiElContext context = new ActivitiElContext();
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    jsonNodeELResolver.setValue(context, objectNode, "Property", "");

    // Assert
    Iterator<JsonNode> iteratorResult = objectNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof TextNode);
    assertEquals("\"\"", nextResult.toPrettyString());
    assertEquals("{\n  \"Property\" : \"\"\n}", objectNode.toPrettyString());
    assertEquals(JsonNodeType.STRING, nextResult.getNodeType());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult.isTextual());
  }

  /**
   * Test {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}.
   * <ul>
   *   <li>Given {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName("Test setValue(ELContext, Object, Object, Object); given 'Name'")
  void testSetValue_givenName() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver();

    ActivitiElContext context = new ActivitiElContext();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    context.setVariable("Name", new ObjectValueExpression(converter, "Object", type));

    // Act
    jsonNodeELResolver.setValue(context, "Base", "Property", "Value");

    // Assert that nothing has changed
    assertFalse(context.isPropertyResolved());
  }

  /**
   * Test {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}.
   * <ul>
   *   <li>Then {@link ObjectNode#ObjectNode(JsonNodeFactory)} with nc is
   * withExactBigDecimals {@code true} iterator next {@link LongNode}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName("Test setValue(ELContext, Object, Object, Object); then ObjectNode(JsonNodeFactory) with nc is withExactBigDecimals 'true' iterator next LongNode")
  void testSetValue_thenObjectNodeWithNcIsWithExactBigDecimalsTrueIteratorNextLongNode() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver();
    ActivitiElContext context = new ActivitiElContext();
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    jsonNodeELResolver.setValue(context, objectNode, "Property", 42L);

    // Assert
    Iterator<JsonNode> iteratorResult = objectNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof LongNode);
    assertEquals("42", nextResult.toPrettyString());
    assertEquals("{\n  \"Property\" : 42\n}", objectNode.toPrettyString());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult.isIntegralNumber());
    assertTrue(nextResult.isLong());
  }

  /**
   * Test {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}.
   * <ul>
   *   <li>Then {@link ObjectNode#ObjectNode(JsonNodeFactory)} with nc is
   * withExactBigDecimals {@code true} iterator next {@link NullNode}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName("Test setValue(ELContext, Object, Object, Object); then ObjectNode(JsonNodeFactory) with nc is withExactBigDecimals 'true' iterator next NullNode")
  void testSetValue_thenObjectNodeWithNcIsWithExactBigDecimalsTrueIteratorNextNullNode() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver();
    ActivitiElContext context = new ActivitiElContext();
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    jsonNodeELResolver.setValue(context, objectNode, "Property", null);

    // Assert
    Iterator<JsonNode> iteratorResult = objectNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof NullNode);
    assertEquals("null", nextResult.toPrettyString());
    assertEquals("{\n  \"Property\" : null\n}", objectNode.toPrettyString());
    assertEquals(JsonNodeType.NULL, nextResult.getNodeType());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult.isNull());
  }

  /**
   * Test {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}.
   * <ul>
   *   <li>Then {@link ObjectNode#ObjectNode(JsonNodeFactory)} with nc is
   * withExactBigDecimals {@code true} toPrettyString is {@code { "{}" : "Value"
   * }}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName("Test setValue(ELContext, Object, Object, Object); then ObjectNode(JsonNodeFactory) with nc is withExactBigDecimals 'true' toPrettyString is '{ \"{}\" : \"Value\" }'")
  void testSetValue_thenObjectNodeWithNcIsWithExactBigDecimalsTrueToPrettyStringIsValue() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver();
    ActivitiElContext context = new ActivitiElContext();
    ObjectNode objectNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    jsonNodeELResolver.setValue(context, objectNode, new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)),
        "Value");

    // Assert
    Iterator<JsonNode> iteratorResult = objectNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof TextNode);
    assertEquals("\"Value\"", nextResult.toPrettyString());
    assertEquals("{\n  \"{}\" : \"Value\"\n}", objectNode.toPrettyString());
    assertEquals(JsonNodeType.STRING, nextResult.getNodeType());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult.isTextual());
  }

  /**
   * Test {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}.
   * <ul>
   *   <li>Then throw {@link PropertyNotWritableException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName("Test setValue(ELContext, Object, Object, Object); then throw PropertyNotWritableException")
  void testSetValue_thenThrowPropertyNotWritableException() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver(true);
    ActivitiElContext context = new ActivitiElContext();

    // Act and Assert
    assertThrows(PropertyNotWritableException.class, () -> jsonNodeELResolver.setValue(context,
        new ObjectNode(JsonNodeFactory.withExactBigDecimals(true)), "Property", "Value"));
  }

  /**
   * Test {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}.
   * <ul>
   *   <li>When {@code Base}.</li>
   *   <li>Then not {@link ActivitiElContext#ActivitiElContext()}
   * PropertyResolved.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName("Test setValue(ELContext, Object, Object, Object); when 'Base'; then not ActivitiElContext() PropertyResolved")
  void testSetValue_whenBase_thenNotActivitiElContextPropertyResolved() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver();
    ActivitiElContext context = new ActivitiElContext();

    // Act
    jsonNodeELResolver.setValue(context, "Base", "Property", "Value");

    // Assert that nothing has changed
    assertFalse(context.isPropertyResolved());
  }
}
