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
import com.fasterxml.jackson.databind.node.BigIntegerNode;
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
import java.math.BigInteger;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.TimeZone;
import java.util.function.BiFunction;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.MessageFlow;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MessageFlowJsonConverterDiffblueTest {
  /**
   * Test {@link MessageFlowJsonConverter#fillJsonTypes(Map)}.
   * <ul>
   *   <li>Given {@code MessageFlow}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageFlowJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map); given 'MessageFlow'")
  void testFillJsonTypes_givenMessageFlow() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();
    convertersToBpmnMap.computeIfPresent("MessageFlow", mock(BiFunction.class));

    // Act
    MessageFlowJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(1, convertersToBpmnMap.size());
    Class<MessageFlowJsonConverter> expectedGetResult = MessageFlowJsonConverter.class;
    assertEquals(expectedGetResult, convertersToBpmnMap.get("MessageFlow"));
  }

  /**
   * Test {@link MessageFlowJsonConverter#fillJsonTypes(Map)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageFlowJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map); when HashMap()")
  void testFillJsonTypes_whenHashMap() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();

    // Act
    MessageFlowJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(1, convertersToBpmnMap.size());
    Class<MessageFlowJsonConverter> expectedGetResult = MessageFlowJsonConverter.class;
    assertEquals(expectedGetResult, convertersToBpmnMap.get("MessageFlow"));
  }

  /**
   * Test {@link MessageFlowJsonConverter#getStencilId(BaseElement)}.
   * <p>
   * Method under test: {@link MessageFlowJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement)")
  void testGetStencilId() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();

    // Act and Assert
    assertEquals("MessageFlow", messageFlowJsonConverter.getStencilId(new ActivitiListener()));
  }

  /**
   * Test
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <p>
   * Method under test:
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  void testConvertJsonToElement() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    BaseElement actualConvertJsonToElementResult = messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode,
        new HashMap<>());

    // Assert
    verify(arrayNode).iterator();
    verify(modelNode).get(eq("childShapes"));
    verify(elementNode).get(eq("resourceId"));
    assertTrue(actualConvertJsonToElementResult instanceof MessageFlow);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getMessageRef());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getName());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getTargetRef());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <p>
   * Method under test:
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  void testConvertJsonToElement2() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    BaseElement actualConvertJsonToElementResult = messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode,
        new HashMap<>());

    // Assert
    verify(arrayNode2).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(elementNode).get(eq("resourceId"));
    assertTrue(actualConvertJsonToElementResult instanceof MessageFlow);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getMessageRef());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getName());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getTargetRef());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <p>
   * Method under test:
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  void testConvertJsonToElement3() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.size()).thenReturn(3);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode2);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode3);

    // Act
    BaseElement actualConvertJsonToElementResult = messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode,
        new HashMap<>());

    // Assert
    verify(arrayNode3).iterator();
    verify(arrayNode, atLeast(1)).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(elementNode).get(eq("resourceId"));
    verify(arrayNode).size();
    assertTrue(actualConvertJsonToElementResult instanceof MessageFlow);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getMessageRef());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getName());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getTargetRef());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <p>
   * Method under test:
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  void testConvertJsonToElement4() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    when(arrayNode3.asText()).thenReturn("As Text");
    when(arrayNode3.iterator()).thenReturn(iteratorResult);
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
    BaseElement actualConvertJsonToElementResult = messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode,
        new HashMap<>());

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode5).iterator();
    verify(arrayNode3).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode3).get(eq("overrideid"));
    verify(arrayNode2).get(eq("resourceId"));
    verify(arrayNode3).size();
    verify(arrayNode).asText();
    verify(arrayNode3).asText();
    assertTrue(actualConvertJsonToElementResult instanceof MessageFlow);
    assertEquals("As Text", ((MessageFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getMessageRef());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getName());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getTargetRef());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <p>
   * Method under test:
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  void testConvertJsonToElement5() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);
    when(arrayNode3.asText()).thenReturn("As Text");
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
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
    BaseElement actualConvertJsonToElementResult = messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode,
        new HashMap<>());

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode3).isNull();
    verify(arrayNode6).iterator();
    verify(arrayNode4).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode5, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode4).get(eq("overrideid"));
    verify(arrayNode2).get(eq("resourceId"));
    verify(arrayNode4).size();
    verify(arrayNode).asText();
    verify(arrayNode4).asText();
    assertTrue(actualConvertJsonToElementResult instanceof MessageFlow);
    assertEquals("As Text", ((MessageFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getMessageRef());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getName());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getTargetRef());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add Instance.</li>
   *   <li>Then calls {@link ArrayNode#size()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayList() add Instance; then calls size()")
  void testConvertJsonToElement_givenArrayListAddInstance_thenCallsSize() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(MissingNode.getInstance());
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.size()).thenReturn(3);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode2);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode3);

    // Act
    BaseElement actualConvertJsonToElementResult = messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode,
        new HashMap<>());

    // Assert
    verify(arrayNode3).iterator();
    verify(arrayNode, atLeast(1)).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(elementNode).get(eq("resourceId"));
    verify(arrayNode).size();
    assertTrue(actualConvertJsonToElementResult instanceof MessageFlow);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getMessageRef());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getName());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getTargetRef());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add Instance.</li>
   *   <li>Then return SourceRef is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayList() add Instance; then return SourceRef is 'null'")
  void testConvertJsonToElement_givenArrayListAddInstance_thenReturnSourceRefIsNull() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(MissingNode.getInstance());
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    BaseElement actualConvertJsonToElementResult = messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode,
        new HashMap<>());

    // Assert
    verify(arrayNode).iterator();
    verify(modelNode).get(eq("childShapes"));
    verify(elementNode).get(eq("resourceId"));
    assertTrue(actualConvertJsonToElementResult instanceof MessageFlow);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getMessageRef());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getName());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getTargetRef());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return
   * Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode get(String) return Instance")
  void testConvertJsonToElement_givenArrayNodeGetReturnInstance() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    BaseElement actualConvertJsonToElementResult = messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode,
        new HashMap<>());

    // Assert
    verify(arrayNode2).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(elementNode).get(eq("resourceId"));
    assertTrue(actualConvertJsonToElementResult instanceof MessageFlow);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getMessageRef());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getName());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getTargetRef());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return
   * {@code null}.</li>
   *   <li>Then return SourceRef is {@code As Text}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode get(String) return 'null'; then return SourceRef is 'As Text'")
  void testConvertJsonToElement_givenArrayNodeGetReturnNull_thenReturnSourceRefIsAsText() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(null);
    when(arrayNode3.asText()).thenReturn("As Text");
    when(arrayNode3.iterator()).thenReturn(iteratorResult);
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
    BaseElement actualConvertJsonToElementResult = messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode,
        new HashMap<>());

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode5).iterator();
    verify(arrayNode3).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode3).get(eq("overrideid"));
    verify(arrayNode2).get(eq("resourceId"));
    verify(arrayNode3).size();
    verify(arrayNode).asText();
    verify(arrayNode3).asText();
    assertTrue(actualConvertJsonToElementResult instanceof MessageFlow);
    assertEquals("As Text", ((MessageFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getMessageRef());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getName());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getTargetRef());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link JsonNode#isNull()} return
   * {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode isNull() return 'false'")
  void testConvertJsonToElement_givenArrayNodeIsNullReturnFalse() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode3.asText()).thenReturn("As Text");
    when(arrayNode3.iterator()).thenReturn(iteratorResult);
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
    BaseElement actualConvertJsonToElementResult = messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode,
        new HashMap<>());

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode5).iterator();
    verify(arrayNode3).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode3).get(eq("overrideid"));
    verify(arrayNode).get(eq("resourceId"));
    verify(arrayNode2).get(eq("resourceId"));
    verify(arrayNode3).size();
    verify(arrayNode).asText();
    verify(arrayNode3).asText();
    assertTrue(actualConvertJsonToElementResult instanceof MessageFlow);
    assertEquals("As Text", ((MessageFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getMessageRef());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getName());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getTargetRef());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#size()} return three.</li>
   *   <li>Then calls {@link ArrayNode#size()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode size() return three; then calls size()")
  void testConvertJsonToElement_givenArrayNodeSizeReturnThree_thenCallsSize() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.size()).thenReturn(3);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode2);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode3);

    // Act
    BaseElement actualConvertJsonToElementResult = messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode,
        new HashMap<>());

    // Assert
    verify(arrayNode3).iterator();
    verify(arrayNode, atLeast(1)).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(elementNode).get(eq("resourceId"));
    verify(arrayNode).size();
    assertTrue(actualConvertJsonToElementResult instanceof MessageFlow);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getMessageRef());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getName());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getTargetRef());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  void testConvertJsonToElement_givenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    BaseElement actualConvertJsonToElementResult = messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode,
        new HashMap<>());

    // Assert
    verify(elementNode).get(eq("resourceId"));
    assertTrue(actualConvertJsonToElementResult instanceof MessageFlow);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getMessageRef());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getName());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getTargetRef());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  void testConvertJsonToElement_givenArrayNodeWithNfIsWithExactBigDecimalsTrue2() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Act
    BaseElement actualConvertJsonToElementResult = messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode,
        new HashMap<>());

    // Assert
    verify(modelNode).get(eq("childShapes"));
    verify(elementNode).get(eq("resourceId"));
    assertTrue(actualConvertJsonToElementResult instanceof MessageFlow);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getMessageRef());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getName());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getTargetRef());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link BigIntegerNode#BigIntegerNode(BigInteger)} with v is valueOf
   * one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given BigIntegerNode(BigInteger) with v is valueOf one")
  void testConvertJsonToElement_givenBigIntegerNodeWithVIsValueOfOne() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode2.iterator()).thenReturn(iteratorResult);
    when(arrayNode2.size()).thenReturn(3);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode3);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode4);

    // Act
    BaseElement actualConvertJsonToElementResult = messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode,
        new HashMap<>());

    // Assert
    verify(arrayNode4).iterator();
    verify(arrayNode2, atLeast(1)).iterator();
    verify(arrayNode3, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(elementNode).get(eq("resourceId"));
    verify(arrayNode).get(eq("resourceId"));
    verify(arrayNode2).size();
    assertTrue(actualConvertJsonToElementResult instanceof MessageFlow);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getMessageRef());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getName());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getTargetRef());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>Then return SourceRef is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given Instance; then return SourceRef is 'null'")
  void testConvertJsonToElement_givenInstance_thenReturnSourceRefIsNull() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    // Act
    BaseElement actualConvertJsonToElementResult = messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode,
        new HashMap<>());

    // Assert
    verify(modelNode).get(eq("childShapes"));
    verify(elementNode).get(eq("resourceId"));
    assertTrue(actualConvertJsonToElementResult instanceof MessageFlow);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getMessageRef());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getName());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getTargetRef());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>Then return SourceRef is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given Instance; then return SourceRef is 'null'")
  void testConvertJsonToElement_givenInstance_thenReturnSourceRefIsNull2() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    BaseElement actualConvertJsonToElementResult = messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode,
        new HashMap<>());

    // Assert
    verify(arrayNode).iterator();
    verify(modelNode).get(eq("childShapes"));
    verify(elementNode).get(eq("resourceId"));
    assertTrue(actualConvertJsonToElementResult instanceof MessageFlow);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getMessageRef());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getName());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getTargetRef());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>When Instance.</li>
   *   <li>Then return SourceRef is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given Instance; when Instance; then return SourceRef is 'null'")
  void testConvertJsonToElement_givenInstance_whenInstance_thenReturnSourceRefIsNull() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    BaseElement actualConvertJsonToElementResult = messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode,
        new HashMap<>());

    // Assert
    verify(elementNode).get(eq("resourceId"));
    assertTrue(actualConvertJsonToElementResult instanceof MessageFlow);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getMessageRef());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getName());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getTargetRef());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Then return SourceRef is {@code 1}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); then return SourceRef is '1'")
  void testConvertJsonToElement_thenReturnSourceRefIs1() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));
    when(arrayNode3.asText()).thenReturn("As Text");
    when(arrayNode3.iterator()).thenReturn(iteratorResult);
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
    BaseElement actualConvertJsonToElementResult = messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode,
        new HashMap<>());

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode5).iterator();
    verify(arrayNode3).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode3, atLeast(1)).get(eq("overrideid"));
    verify(arrayNode2).get(eq("resourceId"));
    verify(arrayNode3).size();
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof MessageFlow);
    assertEquals("1", ((MessageFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getMessageRef());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getName());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getTargetRef());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Then return SourceRef is {@code As Text}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); then return SourceRef is 'As Text'")
  void testConvertJsonToElement_thenReturnSourceRefIsAsText() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode3.asText()).thenReturn("As Text");
    when(arrayNode3.iterator()).thenReturn(iteratorResult);
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
    BaseElement actualConvertJsonToElementResult = messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode,
        new HashMap<>());

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode5).iterator();
    verify(arrayNode3).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode3).get(eq("overrideid"));
    verify(arrayNode2).get(eq("resourceId"));
    verify(arrayNode3).size();
    verify(arrayNode).asText();
    verify(arrayNode3).asText();
    assertTrue(actualConvertJsonToElementResult instanceof MessageFlow);
    assertEquals("As Text", ((MessageFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getMessageRef());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getName());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getTargetRef());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Then return SourceRef is {@code As Text}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); then return SourceRef is 'As Text'")
  void testConvertJsonToElement_thenReturnSourceRefIsAsText2() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);
    when(arrayNode3.asText()).thenReturn("As Text");
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
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
    BaseElement actualConvertJsonToElementResult = messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode,
        new HashMap<>());

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode3).isNull();
    verify(arrayNode6).iterator();
    verify(arrayNode4).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode5, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode4).get(eq("overrideid"));
    verify(arrayNode2).get(eq("resourceId"));
    verify(arrayNode4).size();
    verify(arrayNode).asText();
    verify(arrayNode4).asText();
    assertTrue(actualConvertJsonToElementResult instanceof MessageFlow);
    assertEquals("As Text", ((MessageFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getMessageRef());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getName());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getTargetRef());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  void testConvertJsonToElement_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    ArrayNode modelNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    BaseElement actualConvertJsonToElementResult = messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode,
        new HashMap<>());

    // Assert
    verify(elementNode).get(eq("resourceId"));
    assertTrue(actualConvertJsonToElementResult instanceof MessageFlow);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getMessageRef());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getName());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((MessageFlow) actualConvertJsonToElementResult).getTargetRef());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test new {@link MessageFlowJsonConverter} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link MessageFlowJsonConverter}
   */
  @Test
  @DisplayName("Test new MessageFlowJsonConverter (default constructor)")
  void testNewMessageFlowJsonConverter() throws MissingResourceException {
    // Arrange and Act
    MessageFlowJsonConverter actualMessageFlowJsonConverter = new MessageFlowJsonConverter();

    // Assert
    ObjectMapper objectMapper = actualMessageFlowJsonConverter.objectMapper;
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
    assertNull(actualMessageFlowJsonConverter.shapesArrayNode);
    assertNull(actualMessageFlowJsonConverter.flowElementNode);
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
    assertNull(actualMessageFlowJsonConverter.model);
    assertNull(actualMessageFlowJsonConverter.processor);
    assertEquals(0, factory.getFormatGeneratorFeatures());
    assertEquals(0, factory.getFormatParserFeatures());
    assertEquals(0, deserializationContext.getDeserializationFeatures());
    assertEquals(0, timeZone.getDSTSavings());
    assertEquals(0.0d, actualMessageFlowJsonConverter.subProcessX);
    assertEquals(0.0d, actualMessageFlowJsonConverter.subProcessY);
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
    ObjectMapper expectedCodec = actualMessageFlowJsonConverter.objectMapper;
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
