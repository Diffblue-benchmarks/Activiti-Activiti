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
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
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
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.ser.impl.FailingSerializer;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ArrayIterator;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.TimeZone;
import java.util.function.BiFunction;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.CancelEventDefinition;
import org.activiti.bpmn.model.ErrorEventDefinition;
import org.activiti.bpmn.model.Event;
import org.activiti.bpmn.model.EventDefinition;
import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.SignalEventDefinition;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.TimerEventDefinition;
import org.activiti.editor.language.json.model.ModelInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StartEventJsonConverterDiffblueTest {
  /**
   * Test {@link StartEventJsonConverter#fillJsonTypes(Map)}.
   * <ul>
   *   <li>Given {@code StartNoneEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StartEventJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map); given 'StartNoneEvent'")
  void testFillJsonTypes_givenStartNoneEvent() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();
    convertersToBpmnMap.computeIfPresent("StartNoneEvent", mock(BiFunction.class));

    // Act
    StartEventJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(5, convertersToBpmnMap.size());
    Class<StartEventJsonConverter> expectedGetResult = StartEventJsonConverter.class;
    Class<? extends BaseBpmnJsonConverter> getResult = convertersToBpmnMap.get("StartMessageEvent");
    assertEquals(expectedGetResult, getResult);
    assertSame(getResult, convertersToBpmnMap.get("StartErrorEvent"));
    assertSame(getResult, convertersToBpmnMap.get("StartNoneEvent"));
    assertSame(getResult, convertersToBpmnMap.get("StartSignalEvent"));
    assertSame(getResult, convertersToBpmnMap.get("StartTimerEvent"));
  }

  /**
   * Test {@link StartEventJsonConverter#fillJsonTypes(Map)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StartEventJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map); when HashMap()")
  void testFillJsonTypes_whenHashMap() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();

    // Act
    StartEventJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(5, convertersToBpmnMap.size());
    Class<StartEventJsonConverter> expectedGetResult = StartEventJsonConverter.class;
    Class<? extends BaseBpmnJsonConverter> getResult = convertersToBpmnMap.get("StartMessageEvent");
    assertEquals(expectedGetResult, getResult);
    assertSame(getResult, convertersToBpmnMap.get("StartErrorEvent"));
    assertSame(getResult, convertersToBpmnMap.get("StartNoneEvent"));
    assertSame(getResult, convertersToBpmnMap.get("StartSignalEvent"));
    assertSame(getResult, convertersToBpmnMap.get("StartTimerEvent"));
  }

  /**
   * Test {@link StartEventJsonConverter#getStencilId(BaseElement)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link CancelEventDefinition}
   * (default constructor).</li>
   *   <li>Then return {@code StartNoneEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StartEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement); given ArrayList() add CancelEventDefinition (default constructor); then return 'StartNoneEvent'")
  void testGetStencilId_givenArrayListAddCancelEventDefinition_thenReturnStartNoneEvent() {
    // Arrange
    StartEventJsonConverter startEventJsonConverter = new StartEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());

    BoundaryEvent baseElement = new BoundaryEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("StartNoneEvent", startEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Test {@link StartEventJsonConverter#getStencilId(BaseElement)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ErrorEventDefinition}
   * (default constructor).</li>
   *   <li>Then return {@code StartErrorEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StartEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement); given ArrayList() add ErrorEventDefinition (default constructor); then return 'StartErrorEvent'")
  void testGetStencilId_givenArrayListAddErrorEventDefinition_thenReturnStartErrorEvent() {
    // Arrange
    StartEventJsonConverter startEventJsonConverter = new StartEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new ErrorEventDefinition());

    BoundaryEvent baseElement = new BoundaryEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("StartErrorEvent", startEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Test {@link StartEventJsonConverter#getStencilId(BaseElement)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link TimerEventDefinition}
   * (default constructor).</li>
   *   <li>Then return {@code StartTimerEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StartEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement); given ArrayList() add TimerEventDefinition (default constructor); then return 'StartTimerEvent'")
  void testGetStencilId_givenArrayListAddTimerEventDefinition_thenReturnStartTimerEvent() {
    // Arrange
    StartEventJsonConverter startEventJsonConverter = new StartEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new TimerEventDefinition());

    BoundaryEvent baseElement = new BoundaryEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("StartTimerEvent", startEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Test {@link StartEventJsonConverter#getStencilId(BaseElement)}.
   * <ul>
   *   <li>Then return {@code StartMessageEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StartEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement); then return 'StartMessageEvent'")
  void testGetStencilId_thenReturnStartMessageEvent() {
    // Arrange
    StartEventJsonConverter startEventJsonConverter = new StartEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new MessageEventDefinition());

    BoundaryEvent baseElement = new BoundaryEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("StartMessageEvent", startEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Test {@link StartEventJsonConverter#getStencilId(BaseElement)}.
   * <ul>
   *   <li>Then return {@code StartSignalEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StartEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement); then return 'StartSignalEvent'")
  void testGetStencilId_thenReturnStartSignalEvent() {
    // Arrange
    StartEventJsonConverter startEventJsonConverter = new StartEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new SignalEventDefinition());

    BoundaryEvent baseElement = new BoundaryEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("StartSignalEvent", startEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Test
   * {@link StartEventJsonConverter#convertElementToJson(ObjectNode, BaseElement)}.
   * <p>
   * Method under test:
   * {@link StartEventJsonConverter#convertElementToJson(ObjectNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertElementToJson(ObjectNode, BaseElement)")
  void testConvertElementToJson() {
    // Arrange
    StartEventJsonConverter startEventJsonConverter = new StartEventJsonConverter();
    startEventJsonConverter.setFormKeyMap(null);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    StartEvent baseElement = new StartEvent();
    baseElement.setFormKey(null);
    baseElement.setInitiator(null);

    // Act
    startEventJsonConverter.convertElementToJson(propertiesNode, baseElement);

    // Assert that nothing has changed
    assertEquals("{ }", propertiesNode.toPrettyString());
    assertEquals(0, propertiesNode.size());
    assertFalse(propertiesNode.iterator().hasNext());
    assertTrue(propertiesNode.isEmpty());
  }

  /**
   * Test
   * {@link StartEventJsonConverter#convertElementToJson(ObjectNode, BaseElement)}.
   * <p>
   * Method under test:
   * {@link StartEventJsonConverter#convertElementToJson(ObjectNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertElementToJson(ObjectNode, BaseElement)")
  void testConvertElementToJson2() throws IOException {
    // Arrange
    StartEventJsonConverter startEventJsonConverter = new StartEventJsonConverter();
    startEventJsonConverter.setFormKeyMap(null);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    StartEvent baseElement = new StartEvent();
    baseElement.setFormKey(null);
    baseElement.setInitiator("Base Element");

    // Act
    startEventJsonConverter.convertElementToJson(propertiesNode, baseElement);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    assertEquals("\"Base Element\"", nextResult.toPrettyString());
    assertEquals("{\n  \"initiator\" : \"Base Element\"\n}", propertiesNode.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult.getValueAsString());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, nextResult.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble());
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(1, propertiesNode.size());
    assertEquals(JsonNodeType.STRING, nextResult.getNodeType());
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
    assertFalse(nextResult.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(nextResult.iterator().hasNext());
    assertTrue(nextResult.isEmpty());
    assertTrue(nextResult.isTextual());
    assertTrue(nextResult.isValueNode());
  }

  /**
   * Test
   * {@link StartEventJsonConverter#convertElementToJson(ObjectNode, BaseElement)}.
   * <p>
   * Method under test:
   * {@link StartEventJsonConverter#convertElementToJson(ObjectNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertElementToJson(ObjectNode, BaseElement)")
  void testConvertElementToJson3() throws IOException {
    // Arrange
    StartEventJsonConverter startEventJsonConverter = new StartEventJsonConverter();
    startEventJsonConverter.setFormKeyMap(null);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    StartEvent baseElement = new StartEvent();
    baseElement.setFormKey("Base Element");
    baseElement.setInitiator(null);

    // Act
    startEventJsonConverter.convertElementToJson(propertiesNode, baseElement);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    assertEquals("\"Base Element\"", nextResult.toPrettyString());
    assertEquals("{\n  \"formkeydefinition\" : \"Base Element\"\n}", propertiesNode.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult.getValueAsString());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, nextResult.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble());
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(1, propertiesNode.size());
    assertEquals(JsonNodeType.STRING, nextResult.getNodeType());
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
    assertFalse(nextResult.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(nextResult.iterator().hasNext());
    assertTrue(nextResult.isEmpty());
    assertTrue(nextResult.isTextual());
    assertTrue(nextResult.isValueNode());
  }

  /**
   * Test
   * {@link StartEventJsonConverter#convertElementToJson(ObjectNode, BaseElement)}.
   * <p>
   * Method under test:
   * {@link StartEventJsonConverter#convertElementToJson(ObjectNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertElementToJson(ObjectNode, BaseElement)")
  void testConvertElementToJson4() throws IOException {
    // Arrange
    HashMap<String, ModelInfo> formKeyMap = new HashMap<>();
    formKeyMap.computeIfPresent("formkeydefinition", mock(BiFunction.class));

    StartEventJsonConverter startEventJsonConverter = new StartEventJsonConverter();
    startEventJsonConverter.setFormKeyMap(formKeyMap);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    StartEvent baseElement = new StartEvent();
    baseElement.setFormKey("Base Element");
    baseElement.setInitiator(null);

    // Act
    startEventJsonConverter.convertElementToJson(propertiesNode, baseElement);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    assertEquals("\"Base Element\"", nextResult.toPrettyString());
    assertEquals("{\n  \"formkeydefinition\" : \"Base Element\"\n}", propertiesNode.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult.getValueAsString());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, nextResult.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble());
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(1, propertiesNode.size());
    assertEquals(JsonNodeType.STRING, nextResult.getNodeType());
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
    assertFalse(nextResult.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(nextResult.iterator().hasNext());
    assertTrue(nextResult.isEmpty());
    assertTrue(nextResult.isTextual());
    assertTrue(nextResult.isValueNode());
  }

  /**
   * Test
   * {@link StartEventJsonConverter#convertElementToJson(ObjectNode, BaseElement)}.
   * <ul>
   *   <li>Given empty string.</li>
   *   <li>When {@link StartEvent} (default constructor) FormKey is empty
   * string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartEventJsonConverter#convertElementToJson(ObjectNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertElementToJson(ObjectNode, BaseElement); given empty string; when StartEvent (default constructor) FormKey is empty string")
  void testConvertElementToJson_givenEmptyString_whenStartEventFormKeyIsEmptyString() {
    // Arrange
    StartEventJsonConverter startEventJsonConverter = new StartEventJsonConverter();
    startEventJsonConverter.setFormKeyMap(null);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    StartEvent baseElement = new StartEvent();
    baseElement.setFormKey("");
    baseElement.setInitiator(null);

    // Act
    startEventJsonConverter.convertElementToJson(propertiesNode, baseElement);

    // Assert that nothing has changed
    assertEquals("{ }", propertiesNode.toPrettyString());
    assertEquals(0, propertiesNode.size());
    assertFalse(propertiesNode.iterator().hasNext());
    assertTrue(propertiesNode.isEmpty());
  }

  /**
   * Test
   * {@link StartEventJsonConverter#convertElementToJson(ObjectNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link StartEventJsonConverter} (default constructor) FormKeyMap is
   * {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartEventJsonConverter#convertElementToJson(ObjectNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertElementToJson(ObjectNode, BaseElement); given StartEventJsonConverter (default constructor) FormKeyMap is HashMap()")
  void testConvertElementToJson_givenStartEventJsonConverterFormKeyMapIsHashMap() throws IOException {
    // Arrange
    StartEventJsonConverter startEventJsonConverter = new StartEventJsonConverter();
    startEventJsonConverter.setFormKeyMap(new HashMap<>());
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    StartEvent baseElement = new StartEvent();
    baseElement.setFormKey("Base Element");
    baseElement.setInitiator(null);

    // Act
    startEventJsonConverter.convertElementToJson(propertiesNode, baseElement);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof TextNode);
    JsonParser traverseResult = nextResult.traverse();
    assertTrue(traverseResult instanceof TreeTraversingParser);
    JsonStreamContext parsingContext = traverseResult.getParsingContext();
    assertEquals("ROOT", parsingContext.getTypeDesc());
    assertEquals("\"Base Element\"", nextResult.toPrettyString());
    assertEquals("{\n  \"formkeydefinition\" : \"Base Element\"\n}", propertiesNode.toPrettyString());
    assertNull(traverseResult.getBinaryValue());
    assertNull(traverseResult.getSchema());
    assertNull(traverseResult.getCurrentToken());
    assertNull(traverseResult.getLastClearedToken());
    assertNull(traverseResult.getCodec());
    assertNull(traverseResult.getNonBlockingInputFeeder());
    assertNull(traverseResult.getCurrentValue());
    assertNull(traverseResult.getEmbeddedObject());
    assertNull(traverseResult.getInputSource());
    assertNull(traverseResult.getObjectId());
    assertNull(traverseResult.getTypeId());
    assertNull(parsingContext.getCurrentValue());
    assertNull(traverseResult.getCurrentName());
    assertNull(traverseResult.getText());
    assertNull(traverseResult.getValueAsString());
    assertEquals(0, traverseResult.getCurrentTokenId());
    assertEquals(0, traverseResult.getFeatureMask());
    assertEquals(0, traverseResult.getFormatFeatures());
    assertEquals(0, traverseResult.getTextOffset());
    assertEquals(0, traverseResult.getValueAsInt());
    assertEquals(0, parsingContext.getCurrentIndex());
    assertEquals(0, parsingContext.getEntryCount());
    assertEquals(0, parsingContext.getNestingDepth());
    assertEquals(0, nextResult.size());
    assertEquals(0.0d, traverseResult.getValueAsDouble());
    assertEquals(0L, traverseResult.getValueAsLong());
    assertEquals(1, propertiesNode.size());
    assertEquals(JsonNodeType.STRING, nextResult.getNodeType());
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
    assertFalse(nextResult.isArray());
    assertFalse(nextResult.isBigDecimal());
    assertFalse(nextResult.isBigInteger());
    assertFalse(nextResult.isBinary());
    assertFalse(nextResult.isBoolean());
    assertFalse(nextResult.isContainerNode());
    assertFalse(nextResult.isDouble());
    assertFalse(nextResult.isFloat());
    assertFalse(nextResult.isFloatingPointNumber());
    assertFalse(nextResult.isInt());
    assertFalse(nextResult.isIntegralNumber());
    assertFalse(nextResult.isLong());
    assertFalse(nextResult.isMissingNode());
    assertFalse(nextResult.isNull());
    assertFalse(nextResult.isNumber());
    assertFalse(nextResult.isObject());
    assertFalse(nextResult.isPojo());
    assertFalse(nextResult.isShort());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
    assertFalse(nextResult.iterator().hasNext());
    assertTrue(nextResult.isEmpty());
    assertTrue(nextResult.isTextual());
    assertTrue(nextResult.isValueNode());
  }

  /**
   * Test
   * {@link StartEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given HashMap() computeIfPresent 'foo' and BiFunction")
  void testConvertJsonToElement_givenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, String> formMap = new HashMap<>();
    formMap.computeIfPresent("foo", mock(BiFunction.class));

    StartEventJsonConverter startEventJsonConverter = new StartEventJsonConverter();
    startEventJsonConverter.setFormMap(formMap);
    MissingNode elementNode = MissingNode.getInstance();
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = startEventJsonConverter.convertJsonToElement(elementNode, modelNode,
        new HashMap<>());

    // Assert
    assertTrue(actualConvertJsonToElementResult instanceof StartEvent);
    assertNull(((StartEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((StartEvent) actualConvertJsonToElementResult).getFormKey());
    assertNull(((StartEvent) actualConvertJsonToElementResult).getInitiator());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((StartEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((StartEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertFalse(((StartEvent) actualConvertJsonToElementResult).isInterrupting());
    assertTrue(((StartEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((StartEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((StartEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(((StartEvent) actualConvertJsonToElementResult).getFormProperties().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((StartEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test
   * {@link StartEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link StartEventJsonConverter} (default constructor).</li>
   *   <li>Then return {@link StartEvent}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given StartEventJsonConverter (default constructor); then return StartEvent")
  void testConvertJsonToElement_givenStartEventJsonConverter_thenReturnStartEvent() {
    // Arrange
    StartEventJsonConverter startEventJsonConverter = new StartEventJsonConverter();
    MissingNode elementNode = MissingNode.getInstance();
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = startEventJsonConverter.convertJsonToElement(elementNode, modelNode,
        new HashMap<>());

    // Assert
    assertTrue(actualConvertJsonToElementResult instanceof StartEvent);
    assertNull(((StartEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((StartEvent) actualConvertJsonToElementResult).getFormKey());
    assertNull(((StartEvent) actualConvertJsonToElementResult).getInitiator());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((StartEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((StartEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertFalse(((StartEvent) actualConvertJsonToElementResult).isInterrupting());
    assertTrue(((StartEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((StartEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((StartEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(((StartEvent) actualConvertJsonToElementResult).getFormProperties().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((StartEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test
   * {@link StartEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  void testConvertJsonToElement_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    StartEventJsonConverter startEventJsonConverter = new StartEventJsonConverter();
    ArrayNode elementNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = startEventJsonConverter.convertJsonToElement(elementNode, modelNode,
        new HashMap<>());

    // Assert
    assertTrue(actualConvertJsonToElementResult instanceof StartEvent);
    assertNull(((StartEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((StartEvent) actualConvertJsonToElementResult).getFormKey());
    assertNull(((StartEvent) actualConvertJsonToElementResult).getInitiator());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((StartEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((StartEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertFalse(((StartEvent) actualConvertJsonToElementResult).isInterrupting());
    assertTrue(((StartEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((StartEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((StartEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(((StartEvent) actualConvertJsonToElementResult).getFormProperties().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((StartEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test
   * {@link StartEventJsonConverter#addExtensionElement(String, String, Event)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then {@link BoundaryEvent} (default constructor) ExtensionElements
   * Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartEventJsonConverter#addExtensionElement(String, String, Event)}
   */
  @Test
  @DisplayName("Test addExtensionElement(String, String, Event); when empty string; then BoundaryEvent (default constructor) ExtensionElements Empty")
  void testAddExtensionElement_whenEmptyString_thenBoundaryEventExtensionElementsEmpty() {
    // Arrange
    StartEventJsonConverter startEventJsonConverter = new StartEventJsonConverter();
    BoundaryEvent event = new BoundaryEvent();

    // Act
    startEventJsonConverter.addExtensionElement("", "Element Text", event);

    // Assert
    assertTrue(event.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link StartEventJsonConverter#addExtensionElement(String, String, Event)}.
   * <ul>
   *   <li>When {@code Name}.</li>
   *   <li>Then {@link BoundaryEvent} (default constructor) ExtensionElements size
   * is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartEventJsonConverter#addExtensionElement(String, String, Event)}
   */
  @Test
  @DisplayName("Test addExtensionElement(String, String, Event); when 'Name'; then BoundaryEvent (default constructor) ExtensionElements size is one")
  void testAddExtensionElement_whenName_thenBoundaryEventExtensionElementsSizeIsOne() {
    // Arrange
    StartEventJsonConverter startEventJsonConverter = new StartEventJsonConverter();
    BoundaryEvent event = new BoundaryEvent();

    // Act
    startEventJsonConverter.addExtensionElement("Name", "Element Text", event);

    // Assert
    Map<String, List<ExtensionElement>> extensionElements = event.getExtensionElements();
    assertEquals(1, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("Name");
    assertEquals(1, getResult.size());
    ExtensionElement getResult2 = getResult.get(0);
    assertEquals("Element Text", getResult2.getElementText());
    assertEquals("Name", getResult2.getName());
    assertEquals("modeler", getResult2.getNamespacePrefix());
    assertNull(getResult2.getId());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(getResult2.getChildElements().isEmpty());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult2.getNamespace());
  }

  /**
   * Test new {@link StartEventJsonConverter} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link StartEventJsonConverter}
   */
  @Test
  @DisplayName("Test new StartEventJsonConverter (default constructor)")
  void testNewStartEventJsonConverter() throws MissingResourceException {
    // Arrange and Act
    StartEventJsonConverter actualStartEventJsonConverter = new StartEventJsonConverter();

    // Assert
    ObjectMapper objectMapper = actualStartEventJsonConverter.objectMapper;
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
    assertNull(actualStartEventJsonConverter.shapesArrayNode);
    assertNull(actualStartEventJsonConverter.flowElementNode);
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
    assertNull(actualStartEventJsonConverter.formMap);
    assertNull(actualStartEventJsonConverter.formKeyMap);
    assertNull(dateFormat.getTimeZone());
    assertNull(actualStartEventJsonConverter.model);
    assertNull(actualStartEventJsonConverter.processor);
    assertEquals(0, factory.getFormatGeneratorFeatures());
    assertEquals(0, factory.getFormatParserFeatures());
    assertEquals(0, deserializationContext.getDeserializationFeatures());
    assertEquals(0, timeZone.getDSTSavings());
    assertEquals(0.0d, actualStartEventJsonConverter.subProcessX);
    assertEquals(0.0d, actualStartEventJsonConverter.subProcessY);
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
    ObjectMapper expectedCodec = actualStartEventJsonConverter.objectMapper;
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