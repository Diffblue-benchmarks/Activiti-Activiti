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
package org.activiti.editor.language.json.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.ObjectCodec;
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
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.ser.impl.FailingSerializer;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ArrayIterator;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.TimeZone;
import java.util.function.BiFunction;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.CancelEventDefinition;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.ErrorEventDefinition;
import org.activiti.bpmn.model.EventDefinition;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.TerminateEventDefinition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EndEventJsonConverterDiffblueTest {
  /**
   * Test {@link EndEventJsonConverter#fillJsonTypes(Map)}.
   * <ul>
   *   <li>Given {@code EndNoneEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EndEventJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map); given 'EndNoneEvent'")
  void testFillJsonTypes_givenEndNoneEvent() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();
    convertersToBpmnMap.computeIfPresent("EndNoneEvent", mock(BiFunction.class));

    // Act
    EndEventJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(4, convertersToBpmnMap.size());
    Class<EndEventJsonConverter> expectedGetResult = EndEventJsonConverter.class;
    Class<? extends BaseBpmnJsonConverter> getResult = convertersToBpmnMap.get("EndCancelEvent");
    assertEquals(expectedGetResult, getResult);
    assertSame(getResult, convertersToBpmnMap.get("EndErrorEvent"));
    assertSame(getResult, convertersToBpmnMap.get("EndNoneEvent"));
    assertSame(getResult, convertersToBpmnMap.get("EndTerminateEvent"));
  }

  /**
   * Test {@link EndEventJsonConverter#fillJsonTypes(Map)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EndEventJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map); when HashMap()")
  void testFillJsonTypes_whenHashMap() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();

    // Act
    EndEventJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(4, convertersToBpmnMap.size());
    Class<EndEventJsonConverter> expectedGetResult = EndEventJsonConverter.class;
    Class<? extends BaseBpmnJsonConverter> getResult = convertersToBpmnMap.get("EndCancelEvent");
    assertEquals(expectedGetResult, getResult);
    assertSame(getResult, convertersToBpmnMap.get("EndErrorEvent"));
    assertSame(getResult, convertersToBpmnMap.get("EndNoneEvent"));
    assertSame(getResult, convertersToBpmnMap.get("EndTerminateEvent"));
  }

  /**
   * Test {@link EndEventJsonConverter#getStencilId(BaseElement)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link CancelEventDefinition}
   * (default constructor).</li>
   *   <li>Then return {@code EndCancelEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EndEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement); given ArrayList() add CancelEventDefinition (default constructor); then return 'EndCancelEvent'")
  void testGetStencilId_givenArrayListAddCancelEventDefinition_thenReturnEndCancelEvent() {
    // Arrange
    EndEventJsonConverter endEventJsonConverter = new EndEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());

    EndEvent baseElement = new EndEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("EndCancelEvent", endEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Test {@link EndEventJsonConverter#getStencilId(BaseElement)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link CancelEventDefinition}
   * (default constructor).</li>
   *   <li>Then return {@code EndNoneEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EndEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement); given ArrayList() add CancelEventDefinition (default constructor); then return 'EndNoneEvent'")
  void testGetStencilId_givenArrayListAddCancelEventDefinition_thenReturnEndNoneEvent() {
    // Arrange
    EndEventJsonConverter endEventJsonConverter = new EndEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());
    eventDefinitions.add(new CancelEventDefinition());

    EndEvent baseElement = new EndEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("EndNoneEvent", endEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Test {@link EndEventJsonConverter#getStencilId(BaseElement)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ErrorEventDefinition}
   * (default constructor).</li>
   *   <li>Then return {@code EndErrorEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EndEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement); given ArrayList() add ErrorEventDefinition (default constructor); then return 'EndErrorEvent'")
  void testGetStencilId_givenArrayListAddErrorEventDefinition_thenReturnEndErrorEvent() {
    // Arrange
    EndEventJsonConverter endEventJsonConverter = new EndEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new ErrorEventDefinition());

    EndEvent baseElement = new EndEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("EndErrorEvent", endEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Test {@link EndEventJsonConverter#getStencilId(BaseElement)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.</li>
   *   <li>Then return {@code EndNoneEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EndEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement); given ArrayList() add 'null'; then return 'EndNoneEvent'")
  void testGetStencilId_givenArrayListAddNull_thenReturnEndNoneEvent() {
    // Arrange
    EndEventJsonConverter endEventJsonConverter = new EndEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(null);

    EndEvent baseElement = new EndEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("EndNoneEvent", endEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Test {@link EndEventJsonConverter#getStencilId(BaseElement)}.
   * <ul>
   *   <li>Then return {@code EndTerminateEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EndEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement); then return 'EndTerminateEvent'")
  void testGetStencilId_thenReturnEndTerminateEvent() {
    // Arrange
    EndEventJsonConverter endEventJsonConverter = new EndEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new TerminateEventDefinition());

    EndEvent baseElement = new EndEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("EndTerminateEvent", endEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Test
   * {@link EndEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@code stencil}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EndEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given 'stencil'")
  void testConvertJsonToElement_givenStencil() {
    // Arrange
    EndEventJsonConverter endEventJsonConverter = new EndEventJsonConverter();
    MissingNode elementNode = MissingNode.getInstance();
    MissingNode modelNode = MissingNode.getInstance();

    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    shapeMap.computeIfPresent("stencil", mock(BiFunction.class));

    // Act
    FlowElement actualConvertJsonToElementResult = endEventJsonConverter.convertJsonToElement(elementNode, modelNode,
        shapeMap);

    // Assert
    assertTrue(actualConvertJsonToElementResult instanceof EndEvent);
    assertNull(((EndEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((EndEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((EndEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((EndEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((EndEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((EndEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((EndEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test
   * {@link EndEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   *   <li>Then return {@link EndEvent}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link EndEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); when HashMap(); then return EndEvent")
  void testConvertJsonToElement_whenHashMap_thenReturnEndEvent() {
    // Arrange
    EndEventJsonConverter endEventJsonConverter = new EndEventJsonConverter();
    MissingNode elementNode = MissingNode.getInstance();
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = endEventJsonConverter.convertJsonToElement(elementNode, modelNode,
        new HashMap<>());

    // Assert
    assertTrue(actualConvertJsonToElementResult instanceof EndEvent);
    assertNull(((EndEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((EndEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((EndEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((EndEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((EndEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((EndEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((EndEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test new {@link EndEventJsonConverter} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link EndEventJsonConverter}
   */
  @Test
  @DisplayName("Test new EndEventJsonConverter (default constructor)")
  void testNewEndEventJsonConverter() throws MissingResourceException {
    // Arrange and Act
    EndEventJsonConverter actualEndEventJsonConverter = new EndEventJsonConverter();

    // Assert
    ObjectMapper objectMapper = actualEndEventJsonConverter.objectMapper;
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
    assertNull(actualEndEventJsonConverter.shapesArrayNode);
    assertNull(actualEndEventJsonConverter.flowElementNode);
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
    assertNull(actualEndEventJsonConverter.model);
    assertNull(actualEndEventJsonConverter.processor);
    assertEquals(0, factory.getFormatGeneratorFeatures());
    assertEquals(0, factory.getFormatParserFeatures());
    assertEquals(0, deserializationContext.getDeserializationFeatures());
    assertEquals(0, timeZone.getDSTSavings());
    assertEquals(0.0d, actualEndEventJsonConverter.subProcessX);
    assertEquals(0.0d, actualEndEventJsonConverter.subProcessY);
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
    ObjectMapper expectedCodec = actualEndEventJsonConverter.objectMapper;
    assertSame(expectedCodec, factory.getCodec());
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
}
