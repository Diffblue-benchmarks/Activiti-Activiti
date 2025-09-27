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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonInclude.Value;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.cfg.CacheProvider;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import com.fasterxml.jackson.databind.cfg.DefaultCacheProvider;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.introspect.AccessorNamingStrategy;
import com.fasterxml.jackson.databind.introspect.AccessorNamingStrategy.Provider;
import com.fasterxml.jackson.databind.introspect.BasicClassIntrospector;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.introspect.DefaultAccessorNamingStrategy;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker.Std;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.jsontype.impl.StdSubtypeResolver;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.Impl;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import jakarta.el.ELContext;
import jakarta.el.PropertyNotWritableException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.TimeZone;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class JsonNodeELResolverDiffblueTest {
  /**
   * Test {@link JsonNodeELResolver#JsonNodeELResolver()}.
   *
   * <p>Method under test: {@link JsonNodeELResolver#JsonNodeELResolver()}
   */
  @Test
  @DisplayName("Test new JsonNodeELResolver()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void JsonNodeELResolver.<init>()"})
  void testNewJsonNodeELResolver() {
    // Arrange, Act and Assert
    ObjectMapper objectMapper = new JsonNodeELResolver().getObjectMapper();
    JsonFactory factory = objectMapper.getFactory();
    assertTrue(factory instanceof MappingJsonFactory);
    assertTrue(
        objectMapper.getDeserializationContext() instanceof DefaultDeserializationContext.Impl);
    assertTrue(objectMapper.getVisibilityChecker() instanceof Std);
    assertTrue(objectMapper.getPolymorphicTypeValidator() instanceof LaissezFaireSubTypeValidator);
    assertTrue(objectMapper.getSubtypeResolver() instanceof StdSubtypeResolver);
    assertTrue(objectMapper.getSerializerFactory() instanceof BeanSerializerFactory);
    assertTrue(objectMapper.getSerializerProvider() instanceof Impl);
    assertTrue(objectMapper.getSerializerProviderInstance() instanceof Impl);
    assertTrue(objectMapper.getDateFormat() instanceof StdDateFormat);
    assertNull(objectMapper.getInjectableValues());
    assertNull(objectMapper.getPropertyNamingStrategy());
    assertTrue(objectMapper.getRegisteredModuleIds().isEmpty());
    assertSame(factory, objectMapper.getJsonFactory());
  }

  /**
   * Test {@link JsonNodeELResolver#JsonNodeELResolver(boolean)}.
   *
   * <p>Method under test: {@link JsonNodeELResolver#JsonNodeELResolver(boolean)}
   */
  @Test
  @DisplayName("Test new JsonNodeELResolver(boolean)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void JsonNodeELResolver.<init>(boolean)"})
  void testNewJsonNodeELResolver2() {
    // Arrange, Act and Assert
    ObjectMapper objectMapper = new JsonNodeELResolver(true).getObjectMapper();
    JsonFactory factory = objectMapper.getFactory();
    assertTrue(factory instanceof MappingJsonFactory);
    assertTrue(
        objectMapper.getDeserializationContext() instanceof DefaultDeserializationContext.Impl);
    assertTrue(objectMapper.getVisibilityChecker() instanceof Std);
    assertTrue(objectMapper.getPolymorphicTypeValidator() instanceof LaissezFaireSubTypeValidator);
    assertTrue(objectMapper.getSubtypeResolver() instanceof StdSubtypeResolver);
    assertTrue(objectMapper.getSerializerFactory() instanceof BeanSerializerFactory);
    assertTrue(objectMapper.getSerializerProvider() instanceof Impl);
    assertTrue(objectMapper.getSerializerProviderInstance() instanceof Impl);
    assertTrue(objectMapper.getDateFormat() instanceof StdDateFormat);
    assertNull(objectMapper.getInjectableValues());
    assertNull(objectMapper.getPropertyNamingStrategy());
    assertTrue(objectMapper.getRegisteredModuleIds().isEmpty());
    assertSame(factory, objectMapper.getJsonFactory());
  }

  /**
   * Test {@link JsonNodeELResolver#getCommonPropertyType(ELContext, Object)}.
   *
   * <ul>
   *   <li>When {@code Base}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JsonNodeELResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getCommonPropertyType(ELContext, Object); when 'Base'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class JsonNodeELResolver.getCommonPropertyType(ELContext, Object)"})
  void testGetCommonPropertyType_whenBase_thenReturnNull() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver();

    // Act and Assert
    assertNull(jsonNodeELResolver.getCommonPropertyType(new ActivitiElContext(), "Base"));
  }

  /**
   * Test {@link JsonNodeELResolver#getCommonPropertyType(ELContext, Object)}.
   *
   * <ul>
   *   <li>When valueOf ten.
   *   <li>Then return {@link Object}.
   * </ul>
   *
   * <p>Method under test: {@link JsonNodeELResolver#getCommonPropertyType(ELContext, Object)}
   */
  @Test
  @DisplayName(
      "Test getCommonPropertyType(ELContext, Object); when valueOf ten; then return Object")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class JsonNodeELResolver.getCommonPropertyType(ELContext, Object)"})
  void testGetCommonPropertyType_whenValueOfTen_thenReturnObject() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver();

    // Act
    Class<?> actualCommonPropertyType =
        jsonNodeELResolver.getCommonPropertyType(
            new ActivitiElContext(), DoubleNode.valueOf(10.0d));

    // Assert
    Class<Object> expectedCommonPropertyType = Object.class;
    assertEquals(expectedCommonPropertyType, actualCommonPropertyType);
  }

  /**
   * Test {@link JsonNodeELResolver#getFeatureDescriptors(ELContext, Object)}.
   *
   * <ul>
   *   <li>When {@code Base}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JsonNodeELResolver#getFeatureDescriptors(ELContext, Object)}
   */
  @Test
  @DisplayName("Test getFeatureDescriptors(ELContext, Object); when 'Base'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Iterator JsonNodeELResolver.getFeatureDescriptors(ELContext, Object)"})
  void testGetFeatureDescriptors_whenBase_thenReturnNull() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver();

    // Act and Assert
    assertNull(jsonNodeELResolver.getFeatureDescriptors(new ActivitiElContext(), "Base"));
  }

  /**
   * Test {@link JsonNodeELResolver#getFeatureDescriptors(ELContext, Object)}.
   *
   * <ul>
   *   <li>When valueOf ten.
   *   <li>Then return not hasNext.
   * </ul>
   *
   * <p>Method under test: {@link JsonNodeELResolver#getFeatureDescriptors(ELContext, Object)}
   */
  @Test
  @DisplayName(
      "Test getFeatureDescriptors(ELContext, Object); when valueOf ten; then return not hasNext")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Iterator JsonNodeELResolver.getFeatureDescriptors(ELContext, Object)"})
  void testGetFeatureDescriptors_whenValueOfTen_thenReturnNotHasNext() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver();

    // Act and Assert
    assertFalse(
        jsonNodeELResolver
            .getFeatureDescriptors(new ActivitiElContext(), DoubleNode.valueOf(10.0d))
            .hasNext());
  }

  /**
   * Test {@link JsonNodeELResolver#getType(ELContext, Object, Object)}.
   *
   * <ul>
   *   <li>When {@link ActivitiElContext#ActivitiElContext()}.
   *   <li>Then {@link ActivitiElContext#ActivitiElContext()} PropertyResolved.
   * </ul>
   *
   * <p>Method under test: {@link JsonNodeELResolver#getType(ELContext, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test getType(ELContext, Object, Object); when ActivitiElContext(); then ActivitiElContext() PropertyResolved")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class JsonNodeELResolver.getType(ELContext, Object, Object)"})
  void testGetType_whenActivitiElContext_thenActivitiElContextPropertyResolved() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver();
    ActivitiElContext context = new ActivitiElContext();

    // Act
    Class<?> actualType =
        jsonNodeELResolver.getType(context, DoubleNode.valueOf(10.0d), "Property");

    // Assert
    assertTrue(context.isPropertyResolved());
    Class<Object> expectedType = Object.class;
    assertEquals(expectedType, actualType);
  }

  /**
   * Test {@link JsonNodeELResolver#getType(ELContext, Object, Object)}.
   *
   * <ul>
   *   <li>When {@code Base}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JsonNodeELResolver#getType(ELContext, Object, Object)}
   */
  @Test
  @DisplayName("Test getType(ELContext, Object, Object); when 'Base'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class JsonNodeELResolver.getType(ELContext, Object, Object)"})
  void testGetType_whenBase_thenReturnNull() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver();
    ActivitiElContext context = new ActivitiElContext();

    // Act and Assert
    assertNull(jsonNodeELResolver.getType(context, "Base", "Property"));
    assertFalse(context.isPropertyResolved());
  }

  /**
   * Test {@link JsonNodeELResolver#getValue(ELContext, Object, Object)}.
   *
   * <ul>
   *   <li>When {@link ActivitiElContext#ActivitiElContext()}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JsonNodeELResolver#getValue(ELContext, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test getValue(ELContext, Object, Object); when ActivitiElContext(); then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Object JsonNodeELResolver.getValue(ELContext, Object, Object)"})
  void testGetValue_whenActivitiElContext_thenReturnNull() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver();

    // Act and Assert
    assertNull(jsonNodeELResolver.getValue(new ActivitiElContext(), "Base", "Property"));
  }

  /**
   * Test {@link JsonNodeELResolver#getObjectMapper()}.
   *
   * <p>Method under test: {@link JsonNodeELResolver#getObjectMapper()}
   */
  @Test
  @DisplayName("Test getObjectMapper()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectMapper JsonNodeELResolver.getObjectMapper()"})
  void testGetObjectMapper() throws MissingResourceException {
    // Arrange and Act
    ObjectMapper actualObjectMapper = new JsonNodeELResolver().getObjectMapper();

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
    assertTrue(
        actualObjectMapper.getDeserializationContext()
            instanceof DefaultDeserializationContext.Impl);
    ClassIntrospector classIntrospector = deserializationConfig.getClassIntrospector();
    assertTrue(classIntrospector instanceof BasicClassIntrospector);
    Provider accessorNaming = deserializationConfig.getAccessorNaming();
    assertTrue(accessorNaming instanceof DefaultAccessorNamingStrategy.Provider);
    AnnotationIntrospector annotationIntrospector =
        deserializationConfig.getAnnotationIntrospector();
    assertTrue(annotationIntrospector instanceof JacksonAnnotationIntrospector);
    VisibilityChecker<?> visibilityChecker = actualObjectMapper.getVisibilityChecker();
    assertTrue(visibilityChecker instanceof Std);
    PolymorphicTypeValidator polymorphicTypeValidator =
        actualObjectMapper.getPolymorphicTypeValidator();
    assertTrue(polymorphicTypeValidator instanceof LaissezFaireSubTypeValidator);
    SubtypeResolver subtypeResolver = actualObjectMapper.getSubtypeResolver();
    assertTrue(subtypeResolver instanceof StdSubtypeResolver);
    assertTrue(actualObjectMapper.getSerializerFactory() instanceof BeanSerializerFactory);
    assertTrue(actualObjectMapper.getSerializerProvider() instanceof Impl);
    assertTrue(actualObjectMapper.getSerializerProviderInstance() instanceof Impl);
    DateFormat dateFormat = actualObjectMapper.getDateFormat();
    assertTrue(dateFormat instanceof StdDateFormat);
    Locale locale = deserializationConfig.getLocale();
    assertEquals("", locale.getCountry());
    assertEquals("", locale.getDisplayCountry());
    assertEquals("", locale.getDisplayScript());
    assertEquals("", locale.getDisplayVariant());
    assertEquals("", locale.getISO3Country());
    assertEquals("", locale.getScript());
    assertEquals("", locale.getVariant());
    TimeZone timeZone = deserializationConfig.getTimeZone();
    assertEquals("Coordinated Universal Time", timeZone.getDisplayName());
    assertEquals("English", locale.getDisplayLanguage());
    assertEquals("English", locale.getDisplayName());
    Base64Variant base64Variant = deserializationConfig.getBase64Variant();
    assertEquals("MIME-NO-LINEFEEDS", base64Variant.getName());
    assertEquals("MIME-NO-LINEFEEDS", base64Variant.toString());
    assertEquals("UTC", timeZone.getID());
    Version versionResult = actualObjectMapper.version();
    assertEquals("com.fasterxml.jackson.core", versionResult.getGroupId());
    assertEquals(
        "com.fasterxml.jackson.core/jackson-databind/2.17.2", versionResult.toFullString());
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
    Value defaultPropertyInclusion = deserializationConfig.getDefaultPropertyInclusion();
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
    assertEquals(Include.ALWAYS, serializationConfig.getSerializationInclusion());
    assertEquals(Include.USE_DEFAULTS, defaultPropertyInclusion.getContentInclusion());
    assertEquals(Include.USE_DEFAULTS, defaultPropertyInclusion.getValueInclusion());
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
   *
   * <ul>
   *   <li>Given {@link JsonNodeELResolver#JsonNodeELResolver(boolean)} with readOnly is {@code
   *       true}.
   *   <li>When {@code Base}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link JsonNodeELResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test isReadOnly(ELContext, Object, Object); given JsonNodeELResolver(boolean) with readOnly is 'true'; when 'Base'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JsonNodeELResolver.isReadOnly(ELContext, Object, Object)"})
  void testIsReadOnly_givenJsonNodeELResolverWithReadOnlyIsTrue_whenBase_thenReturnTrue() {
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
   *
   * <ul>
   *   <li>When {@code Base}.
   *   <li>Then not {@link ActivitiElContext#ActivitiElContext()} PropertyResolved.
   * </ul>
   *
   * <p>Method under test: {@link JsonNodeELResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test isReadOnly(ELContext, Object, Object); when 'Base'; then not ActivitiElContext() PropertyResolved")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JsonNodeELResolver.isReadOnly(ELContext, Object, Object)"})
  void testIsReadOnly_whenBase_thenNotActivitiElContextPropertyResolved() {
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
   *
   * <ul>
   *   <li>When valueOf ten.
   *   <li>Then {@link ActivitiElContext#ActivitiElContext()} PropertyResolved.
   * </ul>
   *
   * <p>Method under test: {@link JsonNodeELResolver#isReadOnly(ELContext, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test isReadOnly(ELContext, Object, Object); when valueOf ten; then ActivitiElContext() PropertyResolved")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JsonNodeELResolver.isReadOnly(ELContext, Object, Object)"})
  void testIsReadOnly_whenValueOfTen_thenActivitiElContextPropertyResolved() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver();
    ActivitiElContext context = new ActivitiElContext();

    // Act and Assert
    assertFalse(jsonNodeELResolver.isReadOnly(context, DoubleNode.valueOf(10.0d), "Property"));
    assertTrue(context.isPropertyResolved());
  }

  /**
   * Test {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}.
   *
   * <p>Method under test: {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName("Test setValue(ELContext, Object, Object, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void JsonNodeELResolver.setValue(ELContext, Object, Object, Object)"})
  void testSetValue() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver(false);
    ActivitiElContext context = new ActivitiElContext();
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode objectNode = new ObjectNode(nc);

    // Act
    jsonNodeELResolver.setValue(context, objectNode, "Property", null);

    // Assert
    Iterator<JsonNode> iteratorResult = objectNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof NullNode);
    assertEquals("null", nextResult.toPrettyString());
    assertEquals("{\n  \"Property\" : null\n}", objectNode.toPrettyString());
    assertEquals(JsonNodeType.NULL, nextResult.getNodeType());
    assertFalse(nextResult.isTextual());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult.isNull());
  }

  /**
   * Test {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}.
   *
   * <p>Method under test: {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName("Test setValue(ELContext, Object, Object, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void JsonNodeELResolver.setValue(ELContext, Object, Object, Object)"})
  void testSetValue2() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver(false);
    ActivitiElContext context = new ActivitiElContext();
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode objectNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode objectNode2 = new ObjectNode(nc2);
    objectNode2.putPOJO("Property Name", "Pojo");

    // Act
    jsonNodeELResolver.setValue(context, objectNode, objectNode2, null);

    // Assert
    Iterator<JsonNode> iteratorResult = objectNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof NullNode);
    assertEquals("null", nextResult.toPrettyString());
    assertEquals(
        "{\n  \"{\\\"Property Name\\\":\\\"Pojo\\\"}\" : null\n}", objectNode.toPrettyString());
    assertEquals(JsonNodeType.NULL, nextResult.getNodeType());
    assertFalse(nextResult.isTextual());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult.isNull());
  }

  /**
   * Test {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}.
   *
   * <p>Method under test: {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName("Test setValue(ELContext, Object, Object, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void JsonNodeELResolver.setValue(ELContext, Object, Object, Object)"})
  void testSetValue3() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver(false);
    ActivitiElContext context = new ActivitiElContext();
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode objectNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode objectNode2 = new ObjectNode(nc2);
    objectNode2.put("Field Name", new byte[] {'A', 1, 'A', 1, 'A', 1, 'A', 1});

    // Act
    jsonNodeELResolver.setValue(context, objectNode, objectNode2, null);

    // Assert
    Iterator<JsonNode> iteratorResult = objectNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof NullNode);
    assertEquals("null", nextResult.toPrettyString());
    assertEquals(
        "{\n  \"{\\\"Field Name\\\":\\\"QQFBAUEBQQE=\\\"}\" : null\n}",
        objectNode.toPrettyString());
    assertEquals(JsonNodeType.NULL, nextResult.getNodeType());
    assertFalse(nextResult.isTextual());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult.isNull());
  }

  /**
   * Test {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}.
   *
   * <p>Method under test: {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName("Test setValue(ELContext, Object, Object, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void JsonNodeELResolver.setValue(ELContext, Object, Object, Object)"})
  void testSetValue4() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver(false);
    ActivitiElContext context = new ActivitiElContext();
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode objectNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode objectNode2 = new ObjectNode(nc2);
    objectNode2.put("", new byte[] {'A', 1, 'A', 1, 'A', 1, 'A', 1});
    objectNode2.put("Field Name", new byte[] {'A', 1, 'A', 1, 'A', 1, 'A', 1});

    // Act
    jsonNodeELResolver.setValue(context, objectNode, objectNode2, null);

    // Assert
    Iterator<JsonNode> iteratorResult = objectNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof NullNode);
    assertEquals("null", nextResult.toPrettyString());
    assertEquals(
        "{\n  \"{\\\"\\\":\\\"QQFBAUEBQQE=\\\",\\\"Field Name\\\":\\\"QQFBAUEBQQE=\\\"}\" : null\n}",
        objectNode.toPrettyString());
    assertEquals(JsonNodeType.NULL, nextResult.getNodeType());
    assertFalse(nextResult.isTextual());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult.isNull());
  }

  /**
   * Test {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}.
   *
   * <p>Method under test: {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName("Test setValue(ELContext, Object, Object, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void JsonNodeELResolver.setValue(ELContext, Object, Object, Object)"})
  void testSetValue5() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver(false);
    ActivitiElContext context = new ActivitiElContext();
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode objectNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode objectNode2 = new ObjectNode(nc2);
    objectNode2.putPOJO("42", "Pojo");

    // Act
    jsonNodeELResolver.setValue(context, objectNode, objectNode2, null);

    // Assert
    Iterator<JsonNode> iteratorResult = objectNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof NullNode);
    assertEquals("null", nextResult.toPrettyString());
    assertEquals("{\n  \"{\\\"42\\\":\\\"Pojo\\\"}\" : null\n}", objectNode.toPrettyString());
    assertEquals(JsonNodeType.NULL, nextResult.getNodeType());
    assertFalse(nextResult.isTextual());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult.isNull());
  }

  /**
   * Test {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}.
   *
   * <p>Method under test: {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName("Test setValue(ELContext, Object, Object, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void JsonNodeELResolver.setValue(ELContext, Object, Object, Object)"})
  void testSetValue6() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver(false);
    ActivitiElContext context = new ActivitiElContext();
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode objectNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode objectNode2 = new ObjectNode(nc2);
    objectNode2.putPOJO("Property Name", true);

    // Act
    jsonNodeELResolver.setValue(context, objectNode, objectNode2, null);

    // Assert
    Iterator<JsonNode> iteratorResult = objectNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof NullNode);
    assertEquals("null", nextResult.toPrettyString());
    assertEquals("{\n  \"{\\\"Property Name\\\":true}\" : null\n}", objectNode.toPrettyString());
    assertEquals(JsonNodeType.NULL, nextResult.getNodeType());
    assertFalse(nextResult.isTextual());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult.isNull());
  }

  /**
   * Test {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}.
   *
   * <p>Method under test: {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName("Test setValue(ELContext, Object, Object, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void JsonNodeELResolver.setValue(ELContext, Object, Object, Object)"})
  void testSetValue7() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver(false);
    ActivitiElContext context = new ActivitiElContext();
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode objectNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode objectNode2 = new ObjectNode(nc2);
    JsonNodeFactory nc3 = JsonNodeFactory.withExactBigDecimals(true);
    objectNode2.putPOJO("Property Name", new ObjectNode(nc3));

    // Act
    jsonNodeELResolver.setValue(context, objectNode, objectNode2, null);

    // Assert
    Iterator<JsonNode> iteratorResult = objectNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof NullNode);
    assertEquals("null", nextResult.toPrettyString());
    assertEquals("{\n  \"{\\\"Property Name\\\":{}}\" : null\n}", objectNode.toPrettyString());
    assertEquals(JsonNodeType.NULL, nextResult.getNodeType());
    assertFalse(nextResult.isTextual());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult.isNull());
  }

  /**
   * Test {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}.
   *
   * <p>Method under test: {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName("Test setValue(ELContext, Object, Object, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void JsonNodeELResolver.setValue(ELContext, Object, Object, Object)"})
  void testSetValue8() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver(false);
    ActivitiElContext context = new ActivitiElContext();
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode objectNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode objectNode2 = new ObjectNode(nc2);
    objectNode2.putPOJO("Property Name", new BigDecimal("2.3"));

    // Act
    jsonNodeELResolver.setValue(context, objectNode, objectNode2, null);

    // Assert
    Iterator<JsonNode> iteratorResult = objectNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof NullNode);
    assertEquals("null", nextResult.toPrettyString());
    assertEquals("{\n  \"{\\\"Property Name\\\":2.3}\" : null\n}", objectNode.toPrettyString());
    assertEquals(JsonNodeType.NULL, nextResult.getNodeType());
    assertFalse(nextResult.isTextual());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult.isNull());
  }

  /**
   * Test {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}.
   *
   * <p>Method under test: {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName("Test setValue(ELContext, Object, Object, Object)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void JsonNodeELResolver.setValue(ELContext, Object, Object, Object)"})
  void testSetValue9() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver(false);
    ActivitiElContext context = new ActivitiElContext();
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode objectNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode objectNode2 = new ObjectNode(nc2);
    objectNode2.putPOJO("Property Name", 10.0d);

    // Act
    jsonNodeELResolver.setValue(context, objectNode, objectNode2, null);

    // Assert
    Iterator<JsonNode> iteratorResult = objectNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof NullNode);
    assertEquals("null", nextResult.toPrettyString());
    assertEquals("{\n  \"{\\\"Property Name\\\":10.0}\" : null\n}", objectNode.toPrettyString());
    assertEquals(JsonNodeType.NULL, nextResult.getNodeType());
    assertFalse(nextResult.isTextual());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult.isNull());
  }

  /**
   * Test {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}.
   *
   * <ul>
   *   <li>Then {@link ObjectNode#ObjectNode(JsonNodeFactory)} with nc is withExactBigDecimals
   *       {@code true} iterator next {@link TextNode}.
   * </ul>
   *
   * <p>Method under test: {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test setValue(ELContext, Object, Object, Object); then ObjectNode(JsonNodeFactory) with nc is withExactBigDecimals 'true' iterator next TextNode")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void JsonNodeELResolver.setValue(ELContext, Object, Object, Object)"})
  void testSetValue_thenObjectNodeWithNcIsWithExactBigDecimalsTrueIteratorNextTextNode() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver(false);
    ActivitiElContext context = new ActivitiElContext();
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode objectNode = new ObjectNode(nc);

    // Act
    jsonNodeELResolver.setValue(context, objectNode, "Property", "Value");

    // Assert
    Iterator<JsonNode> iteratorResult = objectNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof TextNode);
    assertEquals("\"Value\"", nextResult.toPrettyString());
    assertEquals("{\n  \"Property\" : \"Value\"\n}", objectNode.toPrettyString());
    assertEquals(JsonNodeType.STRING, nextResult.getNodeType());
    assertFalse(nextResult.isNull());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult.isTextual());
  }

  /**
   * Test {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}.
   *
   * <ul>
   *   <li>Then {@link ObjectNode#ObjectNode(JsonNodeFactory)} with nc is withExactBigDecimals
   *       {@code true} toPrettyString is {@code { "{}" : null }}.
   * </ul>
   *
   * <p>Method under test: {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test setValue(ELContext, Object, Object, Object); then ObjectNode(JsonNodeFactory) with nc is withExactBigDecimals 'true' toPrettyString is '{ \"{}\" : null }'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void JsonNodeELResolver.setValue(ELContext, Object, Object, Object)"})
  void testSetValue_thenObjectNodeWithNcIsWithExactBigDecimalsTrueToPrettyStringIsNull() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver(false);
    ActivitiElContext context = new ActivitiElContext();
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode objectNode = new ObjectNode(nc);
    JsonNodeFactory nc2 = JsonNodeFactory.withExactBigDecimals(true);

    // Act
    jsonNodeELResolver.setValue(context, objectNode, new ObjectNode(nc2), null);

    // Assert
    Iterator<JsonNode> iteratorResult = objectNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof NullNode);
    assertEquals("null", nextResult.toPrettyString());
    assertEquals("{\n  \"{}\" : null\n}", objectNode.toPrettyString());
    assertEquals(JsonNodeType.NULL, nextResult.getNodeType());
    assertFalse(nextResult.isTextual());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult.isNull());
  }

  /**
   * Test {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}.
   *
   * <ul>
   *   <li>Then throw {@link PropertyNotWritableException}.
   * </ul>
   *
   * <p>Method under test: {@link JsonNodeELResolver#setValue(ELContext, Object, Object, Object)}
   */
  @Test
  @DisplayName(
      "Test setValue(ELContext, Object, Object, Object); then throw PropertyNotWritableException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void JsonNodeELResolver.setValue(ELContext, Object, Object, Object)"})
  void testSetValue_thenThrowPropertyNotWritableException() {
    // Arrange
    JsonNodeELResolver jsonNodeELResolver = new JsonNodeELResolver(true);
    ActivitiElContext context = new ActivitiElContext();
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    // Act and Assert
    assertThrows(
        PropertyNotWritableException.class,
        () -> jsonNodeELResolver.setValue(context, new ObjectNode(nc), "Property", null));
  }
}
