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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
import com.fasterxml.jackson.databind.node.ArrayNode;
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
import org.activiti.bpmn.model.CompensateEventDefinition;
import org.activiti.bpmn.model.ErrorEventDefinition;
import org.activiti.bpmn.model.EventDefinition;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.SignalEventDefinition;
import org.activiti.bpmn.model.TimerEventDefinition;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BoundaryEventJsonConverterDiffblueTest {
  /**
   * Method under test: {@link BoundaryEventJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  void testFillJsonTypes() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();

    // Act
    BoundaryEventJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(6, convertersToBpmnMap.size());
    Class<BoundaryEventJsonConverter> expectedGetResult = BoundaryEventJsonConverter.class;
    Class<? extends BaseBpmnJsonConverter> getResult = convertersToBpmnMap.get("BoundaryErrorEvent");
    assertEquals(expectedGetResult, getResult);
    assertSame(getResult, convertersToBpmnMap.get("BoundaryCancelEvent"));
    assertSame(getResult, convertersToBpmnMap.get("BoundaryCompensationEvent"));
    assertSame(getResult, convertersToBpmnMap.get("BoundaryMessageEvent"));
    assertSame(getResult, convertersToBpmnMap.get("BoundarySignalEvent"));
    assertSame(getResult, convertersToBpmnMap.get("BoundaryTimerEvent"));
  }

  /**
   * Method under test: {@link BoundaryEventJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  void testFillJsonTypes2() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();
    convertersToBpmnMap.computeIfPresent("BoundaryTimerEvent", mock(BiFunction.class));

    // Act
    BoundaryEventJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(6, convertersToBpmnMap.size());
    Class<BoundaryEventJsonConverter> expectedGetResult = BoundaryEventJsonConverter.class;
    Class<? extends BaseBpmnJsonConverter> getResult = convertersToBpmnMap.get("BoundaryErrorEvent");
    assertEquals(expectedGetResult, getResult);
    assertSame(getResult, convertersToBpmnMap.get("BoundaryCancelEvent"));
    assertSame(getResult, convertersToBpmnMap.get("BoundaryCompensationEvent"));
    assertSame(getResult, convertersToBpmnMap.get("BoundaryMessageEvent"));
    assertSame(getResult, convertersToBpmnMap.get("BoundarySignalEvent"));
    assertSame(getResult, convertersToBpmnMap.get("BoundaryTimerEvent"));
  }

  /**
   * Method under test:
   * {@link BoundaryEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  void testGetStencilId() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());

    BoundaryEvent baseElement = new BoundaryEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("BoundaryCancelEvent", boundaryEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Method under test:
   * {@link BoundaryEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  void testGetStencilId2() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new ErrorEventDefinition());

    BoundaryEvent baseElement = new BoundaryEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("BoundaryErrorEvent", boundaryEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Method under test:
   * {@link BoundaryEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  void testGetStencilId3() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new SignalEventDefinition());

    BoundaryEvent baseElement = new BoundaryEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("BoundarySignalEvent", boundaryEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Method under test:
   * {@link BoundaryEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  void testGetStencilId4() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new MessageEventDefinition());

    BoundaryEvent baseElement = new BoundaryEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("BoundaryMessageEvent", boundaryEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Method under test:
   * {@link BoundaryEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  void testGetStencilId5() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CompensateEventDefinition());

    BoundaryEvent baseElement = new BoundaryEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("BoundaryCompensationEvent", boundaryEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Method under test:
   * {@link BoundaryEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  void testGetStencilId6() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());
    eventDefinitions.add(new CancelEventDefinition());

    BoundaryEvent baseElement = new BoundaryEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("BoundaryTimerEvent", boundaryEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Method under test:
   * {@link BoundaryEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  void testGetStencilId7() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(null);

    BoundaryEvent baseElement = new BoundaryEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("BoundaryTimerEvent", boundaryEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Method under test:
   * {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  void testConvertJsonToElement() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Method under test:
   * {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  void testConvertJsonToElement2() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Method under test:
   * {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  void testConvertJsonToElement3() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).get(eq("id"));
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Method under test:
   * {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  void testConvertJsonToElement4() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).get(eq("id"));
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Method under test:
   * {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  void testConvertJsonToElement5() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode modelNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).get(eq("id"));
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Method under test:
   * {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  void testConvertJsonToElement6() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode, atLeast(1)).get(eq("id"));
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Method under test:
   * {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  void testConvertJsonToElement7() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode, atLeast(1)).get(eq("id"));
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Method under test:
   * {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  void testConvertJsonToElement8() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode2 = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode2).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode, atLeast(1)).get(eq("id"));
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Method under test:
   * {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  void testConvertJsonToElement9() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(MissingNode.getInstance());
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode2).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode, atLeast(1)).get(eq("id"));
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Method under test:
   * {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  void testConvertJsonToElement10() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode2).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode, atLeast(1)).get(eq("id"));
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Method under test:
   * {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  void testConvertJsonToElement11() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode3);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.iterator()).thenReturn(jsonNodeList.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode4);

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode4).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode2, atLeast(1)).get(eq("id"));
    verify(arrayNode2).asText();
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Method under test:
   * {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  void testConvertJsonToElement12() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    ArrayNode arrayNode3 = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode3.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode3.size()).thenReturn(3);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode4);
    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode5);

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode5).iterator();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode2, atLeast(1)).get(eq("id"));
    verify(arrayNode3).size();
    verify(arrayNode2).asText();
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Method under test:
   * {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  void testConvertJsonToElement13() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("BoundaryTimerEvent");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    ArrayNode arrayNode3 = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode3.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode3.size()).thenReturn(3);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode4);
    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode5);

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode5).iterator();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode3).size();
    verify(arrayNode2).asText();
    verify(arrayNode, atLeast(1)).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    List<EventDefinition> eventDefinitions = ((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions();
    assertEquals(1, eventDefinitions.size());
    EventDefinition getResult = eventDefinitions.get(0);
    assertTrue(getResult instanceof TimerEventDefinition);
    assertEquals("BoundaryTimerEvent", ((TimerEventDefinition) getResult).getEndDate());
    assertEquals("BoundaryTimerEvent", ((TimerEventDefinition) getResult).getTimeDate());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(getResult.getId());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((TimerEventDefinition) getResult).getCalendarName());
    assertNull(((TimerEventDefinition) getResult).getTimeCycle());
    assertNull(((TimerEventDefinition) getResult).getTimeDuration());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Method under test:
   * {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  void testConvertJsonToElement14() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("BoundaryErrorEvent");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    ArrayNode arrayNode3 = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode3.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode3.size()).thenReturn(3);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode4);
    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode5);

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode5).iterator();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode3).size();
    verify(arrayNode2).asText();
    verify(arrayNode, atLeast(1)).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    List<EventDefinition> eventDefinitions = ((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions();
    assertEquals(1, eventDefinitions.size());
    EventDefinition getResult = eventDefinitions.get(0);
    assertTrue(getResult instanceof ErrorEventDefinition);
    assertEquals("BoundaryErrorEvent", ((ErrorEventDefinition) getResult).getErrorRef());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(getResult.getId());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Method under test:
   * {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  void testConvertJsonToElement15() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("BoundarySignalEvent");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    ArrayNode arrayNode3 = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode3.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode3.size()).thenReturn(3);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode4);
    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode5);

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode5).iterator();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode3).size();
    verify(arrayNode2).asText();
    verify(arrayNode, atLeast(1)).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    List<EventDefinition> eventDefinitions = ((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions();
    assertEquals(1, eventDefinitions.size());
    EventDefinition getResult = eventDefinitions.get(0);
    assertTrue(getResult instanceof SignalEventDefinition);
    assertEquals("BoundarySignalEvent", ((SignalEventDefinition) getResult).getSignalRef());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(getResult.getId());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((SignalEventDefinition) getResult).getSignalExpression());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertFalse(((SignalEventDefinition) getResult).isAsync());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Method under test:
   * {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  void testConvertJsonToElement16() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("BoundaryMessageEvent");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    ArrayNode arrayNode3 = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode3.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode3.size()).thenReturn(3);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode4);
    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode5);

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode5).iterator();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode3).size();
    verify(arrayNode2).asText();
    verify(arrayNode, atLeast(1)).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    List<EventDefinition> eventDefinitions = ((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions();
    assertEquals(1, eventDefinitions.size());
    EventDefinition getResult = eventDefinitions.get(0);
    assertTrue(getResult instanceof MessageEventDefinition);
    assertEquals("BoundaryMessageEvent", ((MessageEventDefinition) getResult).getMessageRef());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(getResult.getId());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((MessageEventDefinition) getResult).getCorrelationKey());
    assertNull(((MessageEventDefinition) getResult).getMessageExpression());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(((MessageEventDefinition) getResult).getFieldExtensions().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Method under test:
   * {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  void testConvertJsonToElement17() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("BoundaryCancelEvent");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    ArrayNode arrayNode3 = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode3.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode3.size()).thenReturn(3);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode4);
    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode5);

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode5).iterator();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode3).size();
    verify(arrayNode2).asText();
    verify(arrayNode, atLeast(1)).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    List<EventDefinition> eventDefinitions = ((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions();
    assertEquals(1, eventDefinitions.size());
    EventDefinition getResult = eventDefinitions.get(0);
    assertTrue(getResult instanceof CancelEventDefinition);
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(getResult.getId());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Method under test:
   * {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  void testConvertJsonToElement18() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("BoundaryCompensationEvent");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    ArrayNode arrayNode3 = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode3.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode3.size()).thenReturn(3);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode4);
    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode5);

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode5).iterator();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode3).size();
    verify(arrayNode2).asText();
    verify(arrayNode, atLeast(1)).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    List<EventDefinition> eventDefinitions = ((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions();
    assertEquals(1, eventDefinitions.size());
    EventDefinition getResult = eventDefinitions.get(0);
    assertTrue(getResult instanceof CompensateEventDefinition);
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(getResult.getId());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(((CompensateEventDefinition) getResult).getActivityRef());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((CompensateEventDefinition) getResult).isWaitForCompletion());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Method under test:
   * {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  void testConvertJsonToElement19() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(MissingNode.getInstance());
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode3.size()).thenReturn(3);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode4);
    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode5);

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode5).iterator();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode2, atLeast(1)).get(eq("id"));
    verify(arrayNode3).size();
    verify(arrayNode2).asText();
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Method under test:
   * {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  void testConvertJsonToElement20() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode3.size()).thenReturn(3);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode4);
    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode5);

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode5).iterator();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode2, atLeast(1)).get(eq("id"));
    verify(arrayNode3).size();
    verify(arrayNode2).asText();
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Method under test:
   * {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  void testConvertJsonToElement21() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode3);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode4.size()).thenReturn(3);
    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.get(Mockito.<String>any())).thenReturn(arrayNode4);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode5);
    ArrayNode arrayNode6 = mock(ArrayNode.class);
    when(arrayNode6.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode6);

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode6).iterator();
    verify(arrayNode4, atLeast(1)).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode5, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode2, atLeast(1)).get(eq("id"));
    verify(arrayNode3).get(eq("resourceId"));
    verify(arrayNode4).size();
    verify(arrayNode2).asText();
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Method under test:
   * {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  void testConvertJsonToElement22() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.asText()).thenReturn("");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode3);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode4.asText()).thenReturn("As Text");
    when(arrayNode4.iterator()).thenReturn(iteratorResult);
    when(arrayNode4.size()).thenReturn(3);
    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.get(Mockito.<String>any())).thenReturn(arrayNode4);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode5);
    ArrayNode arrayNode6 = mock(ArrayNode.class);
    when(arrayNode6.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode6);

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode6).iterator();
    verify(arrayNode4).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode5, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode2, atLeast(1)).get(eq("id"));
    verify(arrayNode4).get(eq("overrideid"));
    verify(arrayNode3).get(eq("resourceId"));
    verify(arrayNode4).size();
    verify(arrayNode2).asText();
    verify(arrayNode).asText();
    verify(arrayNode4).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    assertEquals("As Text", ((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link BoundaryEventJsonConverter}
   */
  @Test
  void testNewBoundaryEventJsonConverter() throws MissingResourceException {
    // Arrange and Act
    BoundaryEventJsonConverter actualBoundaryEventJsonConverter = new BoundaryEventJsonConverter();

    // Assert
    ObjectMapper objectMapper = actualBoundaryEventJsonConverter.objectMapper;
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
    assertEquals("JSON", factory.getFormatName());
    Base64Variant base64Variant = deserializationConfig.getBase64Variant();
    assertEquals("MIME-NO-LINEFEEDS", base64Variant.getName());
    assertEquals("MIME-NO-LINEFEEDS", base64Variant.toString());
    assertEquals("UTC", timeZone.getID());
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
    assertNull(actualBoundaryEventJsonConverter.shapesArrayNode);
    assertNull(actualBoundaryEventJsonConverter.flowElementNode);
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
    assertNull(actualBoundaryEventJsonConverter.model);
    assertNull(actualBoundaryEventJsonConverter.processor);
    assertEquals(0, factory.getFormatGeneratorFeatures());
    assertEquals(0, factory.getFormatParserFeatures());
    assertEquals(0, deserializationContext.getDeserializationFeatures());
    assertEquals(0, timeZone.getDSTSavings());
    assertEquals(0.0d, actualBoundaryEventJsonConverter.subProcessX);
    assertEquals(0.0d, actualBoundaryEventJsonConverter.subProcessY);
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
    ObjectMapper expectedCodec = actualBoundaryEventJsonConverter.objectMapper;
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
