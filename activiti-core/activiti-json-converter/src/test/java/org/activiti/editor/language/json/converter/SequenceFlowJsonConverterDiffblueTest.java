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
import static org.mockito.Mockito.doNothing;
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
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.ContainerNode;
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
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.TimeZone;
import java.util.function.BiFunction;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.SequenceFlow;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SequenceFlowJsonConverterDiffblueTest {
  /**
   * Test {@link SequenceFlowJsonConverter#fillJsonTypes(Map)}.
   * <ul>
   *   <li>Given {@code SequenceFlow}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map); given 'SequenceFlow'")
  void testFillJsonTypes_givenSequenceFlow() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();
    convertersToBpmnMap.computeIfPresent("SequenceFlow", mock(BiFunction.class));

    // Act
    SequenceFlowJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(1, convertersToBpmnMap.size());
    Class<SequenceFlowJsonConverter> expectedGetResult = SequenceFlowJsonConverter.class;
    assertEquals(expectedGetResult, convertersToBpmnMap.get("SequenceFlow"));
  }

  /**
   * Test {@link SequenceFlowJsonConverter#fillJsonTypes(Map)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map); when HashMap()")
  void testFillJsonTypes_whenHashMap() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();

    // Act
    SequenceFlowJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(1, convertersToBpmnMap.size());
    Class<SequenceFlowJsonConverter> expectedGetResult = SequenceFlowJsonConverter.class;
    assertEquals(expectedGetResult, convertersToBpmnMap.get("SequenceFlow"));
  }

  /**
   * Test {@link SequenceFlowJsonConverter#getStencilId(BaseElement)}.
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement)")
  void testGetStencilId() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();

    // Act and Assert
    assertEquals("SequenceFlow", sequenceFlowJsonConverter.getStencilId(new ActivitiListener()));
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  void testConvertJsonToElement() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = sequenceFlowJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode).get(eq("conditionsequenceflow"));
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getConditionExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceFlowElement());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetFlowElement());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((SequenceFlow) actualConvertJsonToElementResult).getWaypoints().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  void testConvertJsonToElement2() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    when(arrayNode.isTextual()).thenReturn(true);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = sequenceFlowJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode).isTextual();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2).get(eq("conditionsequenceflow"));
    verify(arrayNode, atLeast(1)).get(eq("expression"));
    verify(arrayNode2).asText();
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getConditionExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceFlowElement());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetFlowElement());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((SequenceFlow) actualConvertJsonToElementResult).getWaypoints().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  void testConvertJsonToElement3() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);
    when(arrayNode3.asText()).thenReturn("As Text");
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.isTextual()).thenReturn(true);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    when(arrayNode4.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.iterator()).thenReturn(jsonNodeList.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode5);

    // Act
    FlowElement actualConvertJsonToElementResult = sequenceFlowJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode5).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode4).get(eq("conditionsequenceflow"));
    verify(arrayNode3, atLeast(1)).get(eq("expression"));
    verify(arrayNode4).asText();
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getConditionExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceFlowElement());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetFlowElement());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((SequenceFlow) actualConvertJsonToElementResult).getWaypoints().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  void testConvertJsonToElement4() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);
    when(arrayNode3.asText()).thenReturn("As Text");
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.isTextual()).thenReturn(true);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    when(arrayNode4.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);
    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode5);
    ArrayNode arrayNode6 = mock(ArrayNode.class);
    when(arrayNode6.iterator()).thenReturn(jsonNodeList.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode6);

    // Act
    FlowElement actualConvertJsonToElementResult = sequenceFlowJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode6).iterator();
    verify(arrayNode5, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode4).get(eq("conditionsequenceflow"));
    verify(arrayNode3, atLeast(1)).get(eq("expression"));
    verify(arrayNode4).asText();
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getConditionExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceFlowElement());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetFlowElement());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((SequenceFlow) actualConvertJsonToElementResult).getWaypoints().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  void testConvertJsonToElement5() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);
    when(arrayNode3.asText()).thenReturn("As Text");
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.isTextual()).thenReturn(true);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    when(arrayNode4.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode5.size()).thenReturn(3);
    ArrayNode arrayNode6 = mock(ArrayNode.class);
    when(arrayNode6.get(Mockito.<String>any())).thenReturn(arrayNode5);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode6);
    ArrayNode arrayNode7 = mock(ArrayNode.class);
    when(arrayNode7.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode7);

    // Act
    FlowElement actualConvertJsonToElementResult = sequenceFlowJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode7).iterator();
    verify(arrayNode5, atLeast(1)).iterator();
    verify(arrayNode6, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode4).get(eq("conditionsequenceflow"));
    verify(arrayNode3, atLeast(1)).get(eq("expression"));
    verify(arrayNode5).size();
    verify(arrayNode4).asText();
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getConditionExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceFlowElement());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetFlowElement());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((SequenceFlow) actualConvertJsonToElementResult).getWaypoints().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add Instance.</li>
   *   <li>Then calls {@link JsonNode#iterator()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayList() add Instance; then calls iterator()")
  void testConvertJsonToElement_givenArrayListAddInstance_thenCallsIterator() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);
    when(arrayNode3.asText()).thenReturn("As Text");
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.isTextual()).thenReturn(true);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    when(arrayNode4.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(MissingNode.getInstance());
    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.iterator()).thenReturn(jsonNodeList.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode5);

    // Act
    FlowElement actualConvertJsonToElementResult = sequenceFlowJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode5).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode4).get(eq("conditionsequenceflow"));
    verify(arrayNode3, atLeast(1)).get(eq("expression"));
    verify(arrayNode4).asText();
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getConditionExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceFlowElement());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetFlowElement());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((SequenceFlow) actualConvertJsonToElementResult).getWaypoints().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add Instance.</li>
   *   <li>Then calls {@link ArrayNode#size()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayList() add Instance; then calls size()")
  void testConvertJsonToElement_givenArrayListAddInstance_thenCallsSize() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);
    when(arrayNode3.asText()).thenReturn("As Text");
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.isTextual()).thenReturn(true);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    when(arrayNode4.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(MissingNode.getInstance());
    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode5.size()).thenReturn(3);
    ArrayNode arrayNode6 = mock(ArrayNode.class);
    when(arrayNode6.get(Mockito.<String>any())).thenReturn(arrayNode5);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode6);
    ArrayNode arrayNode7 = mock(ArrayNode.class);
    when(arrayNode7.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode7);

    // Act
    FlowElement actualConvertJsonToElementResult = sequenceFlowJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode7).iterator();
    verify(arrayNode5, atLeast(1)).iterator();
    verify(arrayNode6, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode4).get(eq("conditionsequenceflow"));
    verify(arrayNode3, atLeast(1)).get(eq("expression"));
    verify(arrayNode5).size();
    verify(arrayNode4).asText();
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getConditionExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceFlowElement());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetFlowElement());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((SequenceFlow) actualConvertJsonToElementResult).getWaypoints().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ContainerNode#asText()} return
   * {@code variables}.</li>
   *   <li>When Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode asText() return 'variables'; when Instance")
  void testConvertJsonToElement_givenArrayNodeAsTextReturnVariables_whenInstance() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("variables");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);
    when(arrayNode3.asText()).thenReturn("As Text");
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.isTextual()).thenReturn(true);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    when(arrayNode4.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = sequenceFlowJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode3).isNull();
    verify(arrayNode3).isTextual();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode4).get(eq("conditionsequenceflow"));
    verify(arrayNode3, atLeast(1)).get(eq("expression"));
    verify(arrayNode4).asText();
    verify(arrayNode, atLeast(1)).asText();
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getConditionExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceFlowElement());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetFlowElement());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((SequenceFlow) actualConvertJsonToElementResult).getWaypoints().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return
   * {@link BigIntegerNode#BigIntegerNode(BigInteger)} with v is valueOf one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode get(String) return BigIntegerNode(BigInteger) with v is valueOf one")
  void testConvertJsonToElement_givenArrayNodeGetReturnBigIntegerNodeWithVIsValueOfOne() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = sequenceFlowJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode).get(eq("conditionsequenceflow"));
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getConditionExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceFlowElement());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetFlowElement());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((SequenceFlow) actualConvertJsonToElementResult).getWaypoints().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return
   * Instance.</li>
   *   <li>Then calls {@link JsonNode#iterator()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode get(String) return Instance; then calls iterator()")
  void testConvertJsonToElement_givenArrayNodeGetReturnInstance_thenCallsIterator() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);
    when(arrayNode3.asText()).thenReturn("As Text");
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.isTextual()).thenReturn(true);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    when(arrayNode4.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);
    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode5);
    ArrayNode arrayNode6 = mock(ArrayNode.class);
    when(arrayNode6.iterator()).thenReturn(jsonNodeList.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode6);

    // Act
    FlowElement actualConvertJsonToElementResult = sequenceFlowJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode6).iterator();
    verify(arrayNode5, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode4).get(eq("conditionsequenceflow"));
    verify(arrayNode3, atLeast(1)).get(eq("expression"));
    verify(arrayNode4).asText();
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getConditionExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceFlowElement());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetFlowElement());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((SequenceFlow) actualConvertJsonToElementResult).getWaypoints().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return
   * Instance.</li>
   *   <li>When Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode get(String) return Instance; when Instance")
  void testConvertJsonToElement_givenArrayNodeGetReturnInstance_whenInstance() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = sequenceFlowJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode).get(eq("conditionsequenceflow"));
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getConditionExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceFlowElement());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetFlowElement());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((SequenceFlow) actualConvertJsonToElementResult).getWaypoints().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return
   * Instance.</li>
   *   <li>When Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode get(String) return Instance; when Instance")
  void testConvertJsonToElement_givenArrayNodeGetReturnInstance_whenInstance2() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode.isTextual()).thenReturn(true);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = sequenceFlowJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode).isTextual();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2).get(eq("conditionsequenceflow"));
    verify(arrayNode, atLeast(1)).get(eq("expression"));
    verify(arrayNode2).asText();
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getConditionExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceFlowElement());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetFlowElement());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((SequenceFlow) actualConvertJsonToElementResult).getWaypoints().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return
   * {@code null}.</li>
   *   <li>Then return SourceRef is {@code As Text}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode get(String) return 'null'; then return SourceRef is 'As Text'")
  void testConvertJsonToElement_givenArrayNodeGetReturnNull_thenReturnSourceRefIsAsText() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);
    when(arrayNode3.asText()).thenReturn("As Text");
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.isTextual()).thenReturn(true);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.isNull()).thenReturn(true);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    when(arrayNode4.asText()).thenReturn("");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);
    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode5);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode6 = mock(ArrayNode.class);
    when(arrayNode6.get(Mockito.<String>any())).thenReturn(null);
    when(arrayNode6.asText()).thenReturn("As Text");
    when(arrayNode6.iterator()).thenReturn(iteratorResult);
    when(arrayNode6.size()).thenReturn(3);
    ArrayNode arrayNode7 = mock(ArrayNode.class);
    when(arrayNode7.get(Mockito.<String>any())).thenReturn(arrayNode6);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode7);
    ArrayNode arrayNode8 = mock(ArrayNode.class);
    when(arrayNode8.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode8);

    // Act
    FlowElement actualConvertJsonToElementResult = sequenceFlowJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode4).isNull();
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode8).iterator();
    verify(arrayNode6).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode7, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode4).get(eq("conditionsequenceflow"));
    verify(arrayNode3, atLeast(1)).get(eq("expression"));
    verify(arrayNode6).get(eq("overrideid"));
    verify(arrayNode5).get(eq("resourceId"));
    verify(arrayNode6).size();
    verify(arrayNode4).asText();
    verify(arrayNode).asText();
    verify(arrayNode6).asText();
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertEquals("As Text", ((SequenceFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getConditionExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceFlowElement());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetFlowElement());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((SequenceFlow) actualConvertJsonToElementResult).getWaypoints().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link JsonNode#isNull()} return
   * {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode isNull() return 'false'")
  void testConvertJsonToElement_givenArrayNodeIsNullReturnFalse() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);
    when(arrayNode3.asText()).thenReturn("As Text");
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.isTextual()).thenReturn(true);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.isNull()).thenReturn(false);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    when(arrayNode4.asText()).thenReturn("");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);
    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode5);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode6 = mock(ArrayNode.class);
    when(arrayNode6.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode6.asText()).thenReturn("As Text");
    when(arrayNode6.iterator()).thenReturn(iteratorResult);
    when(arrayNode6.size()).thenReturn(3);
    ArrayNode arrayNode7 = mock(ArrayNode.class);
    when(arrayNode7.get(Mockito.<String>any())).thenReturn(arrayNode6);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode7);
    ArrayNode arrayNode8 = mock(ArrayNode.class);
    when(arrayNode8.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode8);

    // Act
    FlowElement actualConvertJsonToElementResult = sequenceFlowJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode4).isNull();
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode8).iterator();
    verify(arrayNode6).iterator();
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode7, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode3, atLeast(1)).get(eq("expression"));
    verify(arrayNode6).get(eq("overrideid"));
    verify(arrayNode5).get(eq("resourceId"));
    verify(arrayNode6).size();
    verify(arrayNode4).asText();
    verify(arrayNode3).asText();
    verify(arrayNode).asText();
    verify(arrayNode6).asText();
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertEquals("As Text", ((SequenceFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getConditionExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceFlowElement());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetFlowElement());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((SequenceFlow) actualConvertJsonToElementResult).getWaypoints().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#size()} return three.</li>
   *   <li>Then calls {@link ArrayNode#size()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode size() return three; then calls size()")
  void testConvertJsonToElement_givenArrayNodeSizeReturnThree_thenCallsSize() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);
    when(arrayNode3.asText()).thenReturn("As Text");
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.isTextual()).thenReturn(true);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    when(arrayNode4.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);
    ArrayNode arrayNode5 = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode5.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode5.size()).thenReturn(3);
    ArrayNode arrayNode6 = mock(ArrayNode.class);
    when(arrayNode6.get(Mockito.<String>any())).thenReturn(arrayNode5);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode6);
    ArrayNode arrayNode7 = mock(ArrayNode.class);
    when(arrayNode7.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode7);

    // Act
    FlowElement actualConvertJsonToElementResult = sequenceFlowJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode7).iterator();
    verify(arrayNode5, atLeast(1)).iterator();
    verify(arrayNode6, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode4).get(eq("conditionsequenceflow"));
    verify(arrayNode3, atLeast(1)).get(eq("expression"));
    verify(arrayNode5).size();
    verify(arrayNode4).asText();
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getConditionExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceFlowElement());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetFlowElement());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((SequenceFlow) actualConvertJsonToElementResult).getWaypoints().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#size()} return three.</li>
   *   <li>Then calls {@link ArrayNode#size()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode size() return three; then calls size()")
  void testConvertJsonToElement_givenArrayNodeSizeReturnThree_thenCallsSize2() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);
    when(arrayNode3.asText()).thenReturn("As Text");
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.isTextual()).thenReturn(true);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    when(arrayNode4.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);
    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode5);
    ArrayNode arrayNode6 = mock(ArrayNode.class);
    when(arrayNode6.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode6.size()).thenReturn(3);
    ArrayNode arrayNode7 = mock(ArrayNode.class);
    when(arrayNode7.get(Mockito.<String>any())).thenReturn(arrayNode6);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode7);
    ArrayNode arrayNode8 = mock(ArrayNode.class);
    when(arrayNode8.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode8);

    // Act
    FlowElement actualConvertJsonToElementResult = sequenceFlowJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode8).iterator();
    verify(arrayNode6, atLeast(1)).iterator();
    verify(arrayNode7, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode4).get(eq("conditionsequenceflow"));
    verify(arrayNode3, atLeast(1)).get(eq("expression"));
    verify(arrayNode5).get(eq("resourceId"));
    verify(arrayNode6).size();
    verify(arrayNode4).asText();
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getConditionExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceFlowElement());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetFlowElement());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((SequenceFlow) actualConvertJsonToElementResult).getWaypoints().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  void testConvertJsonToElement_givenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = sequenceFlowJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getConditionExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceFlowElement());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetFlowElement());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((SequenceFlow) actualConvertJsonToElementResult).getWaypoints().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  void testConvertJsonToElement_givenArrayNodeWithNfIsWithExactBigDecimalsTrue2() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);
    when(arrayNode3.asText()).thenReturn("As Text");
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.isTextual()).thenReturn(true);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    when(arrayNode4.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Act
    FlowElement actualConvertJsonToElementResult = sequenceFlowJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3).isTextual();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode4).get(eq("conditionsequenceflow"));
    verify(arrayNode3, atLeast(1)).get(eq("expression"));
    verify(arrayNode4).asText();
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getConditionExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceFlowElement());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetFlowElement());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((SequenceFlow) actualConvertJsonToElementResult).getWaypoints().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return
   * Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given Instance; when ArrayNode get(String) return Instance")
  void testConvertJsonToElement_givenInstance_whenArrayNodeGetReturnInstance() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = sequenceFlowJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getConditionExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceFlowElement());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetFlowElement());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((SequenceFlow) actualConvertJsonToElementResult).getWaypoints().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return
   * Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given Instance; when ArrayNode get(String) return Instance")
  void testConvertJsonToElement_givenInstance_whenArrayNodeGetReturnInstance2() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);
    when(arrayNode3.asText()).thenReturn("As Text");
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.isTextual()).thenReturn(true);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    when(arrayNode4.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    // Act
    FlowElement actualConvertJsonToElementResult = sequenceFlowJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3).isTextual();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode4).get(eq("conditionsequenceflow"));
    verify(arrayNode3, atLeast(1)).get(eq("expression"));
    verify(arrayNode4).asText();
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getConditionExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceFlowElement());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetFlowElement());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((SequenceFlow) actualConvertJsonToElementResult).getWaypoints().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Then calls {@link JsonNode#iterator()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); then calls iterator()")
  void testConvertJsonToElement_thenCallsIterator() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);
    when(arrayNode3.asText()).thenReturn("As Text");
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.isTextual()).thenReturn(true);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    when(arrayNode4.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);
    ArrayNode arrayNode5 = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode5.iterator()).thenReturn(jsonNodeList.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode5);

    // Act
    FlowElement actualConvertJsonToElementResult = sequenceFlowJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode5).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode4).get(eq("conditionsequenceflow"));
    verify(arrayNode3, atLeast(1)).get(eq("expression"));
    verify(arrayNode4).asText();
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getConditionExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceFlowElement());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetFlowElement());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((SequenceFlow) actualConvertJsonToElementResult).getWaypoints().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Then return ConditionExpression is {@code 1}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); then return ConditionExpression is '1'")
  void testConvertJsonToElement_thenReturnConditionExpressionIs1() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.isNull()).thenReturn(true);
    when(arrayNode2.asText()).thenReturn("As Text");
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isTextual()).thenReturn(true);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode3);
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = sequenceFlowJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode2).isNull();
    verify(arrayNode2).isTextual();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3).get(eq("conditionsequenceflow"));
    verify(arrayNode2, atLeast(1)).get(eq("expression"));
    verify(arrayNode3).asText();
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertEquals("1", ((SequenceFlow) actualConvertJsonToElementResult).getConditionExpression());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceFlowElement());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetFlowElement());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((SequenceFlow) actualConvertJsonToElementResult).getWaypoints().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Then return ConditionExpression is {@code As Text}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); then return ConditionExpression is 'As Text'")
  void testConvertJsonToElement_thenReturnConditionExpressionIsAsText() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode.isTextual()).thenReturn(true);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = sequenceFlowJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode).isTextual();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2).get(eq("conditionsequenceflow"));
    verify(arrayNode2).asText();
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertEquals("As Text", ((SequenceFlow) actualConvertJsonToElementResult).getConditionExpression());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceFlowElement());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetFlowElement());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((SequenceFlow) actualConvertJsonToElementResult).getWaypoints().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Then return ConditionExpression is empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); then return ConditionExpression is empty string")
  void testConvertJsonToElement_thenReturnConditionExpressionIsEmptyString() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.isNull()).thenReturn(true);
    when(arrayNode2.asText()).thenReturn("As Text");
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isTextual()).thenReturn(true);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode3);
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = sequenceFlowJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode2).isNull();
    verify(arrayNode2).isTextual();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3).get(eq("conditionsequenceflow"));
    verify(arrayNode2, atLeast(1)).get(eq("expression"));
    verify(arrayNode3).asText();
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertEquals("", ((SequenceFlow) actualConvertJsonToElementResult).getConditionExpression());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceFlowElement());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetFlowElement());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((SequenceFlow) actualConvertJsonToElementResult).getWaypoints().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Then return ConditionExpression is empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); then return ConditionExpression is empty string")
  void testConvertJsonToElement_thenReturnConditionExpressionIsEmptyString2() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.isNull()).thenReturn(true);
    when(arrayNode2.asText()).thenReturn("As Text");
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isTextual()).thenReturn(true);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode3);
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = sequenceFlowJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode2).isNull();
    verify(arrayNode2).isTextual();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3).get(eq("conditionsequenceflow"));
    verify(arrayNode2, atLeast(1)).get(eq("expression"));
    verify(arrayNode3).asText();
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertEquals("", ((SequenceFlow) actualConvertJsonToElementResult).getConditionExpression());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceFlowElement());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetFlowElement());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((SequenceFlow) actualConvertJsonToElementResult).getWaypoints().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Then return SourceRef is {@code 1}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); then return SourceRef is '1'")
  void testConvertJsonToElement_thenReturnSourceRefIs1() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);
    when(arrayNode3.asText()).thenReturn("As Text");
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.isTextual()).thenReturn(true);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.isNull()).thenReturn(true);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    when(arrayNode4.asText()).thenReturn("");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);
    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode5);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode6 = mock(ArrayNode.class);
    when(arrayNode6.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));
    when(arrayNode6.asText()).thenReturn("As Text");
    when(arrayNode6.iterator()).thenReturn(iteratorResult);
    when(arrayNode6.size()).thenReturn(3);
    ArrayNode arrayNode7 = mock(ArrayNode.class);
    when(arrayNode7.get(Mockito.<String>any())).thenReturn(arrayNode6);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode7);
    ArrayNode arrayNode8 = mock(ArrayNode.class);
    when(arrayNode8.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode8);

    // Act
    FlowElement actualConvertJsonToElementResult = sequenceFlowJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode4).isNull();
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode8).iterator();
    verify(arrayNode6).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode7, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode4).get(eq("conditionsequenceflow"));
    verify(arrayNode3, atLeast(1)).get(eq("expression"));
    verify(arrayNode6, atLeast(1)).get(eq("overrideid"));
    verify(arrayNode5).get(eq("resourceId"));
    verify(arrayNode6).size();
    verify(arrayNode4).asText();
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertEquals("1", ((SequenceFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getConditionExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceFlowElement());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetFlowElement());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((SequenceFlow) actualConvertJsonToElementResult).getWaypoints().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Then return SourceRef is {@code As Text}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); then return SourceRef is 'As Text'")
  void testConvertJsonToElement_thenReturnSourceRefIsAsText() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);
    when(arrayNode3.asText()).thenReturn("As Text");
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.isTextual()).thenReturn(true);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.isNull()).thenReturn(true);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    when(arrayNode4.asText()).thenReturn("");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);
    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode5);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode6 = mock(ArrayNode.class);
    when(arrayNode6.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode6.asText()).thenReturn("As Text");
    when(arrayNode6.iterator()).thenReturn(iteratorResult);
    when(arrayNode6.size()).thenReturn(3);
    ArrayNode arrayNode7 = mock(ArrayNode.class);
    when(arrayNode7.get(Mockito.<String>any())).thenReturn(arrayNode6);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode7);
    ArrayNode arrayNode8 = mock(ArrayNode.class);
    when(arrayNode8.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode8);

    // Act
    FlowElement actualConvertJsonToElementResult = sequenceFlowJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode4).isNull();
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode8).iterator();
    verify(arrayNode6).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode7, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode4).get(eq("conditionsequenceflow"));
    verify(arrayNode3, atLeast(1)).get(eq("expression"));
    verify(arrayNode6).get(eq("overrideid"));
    verify(arrayNode5).get(eq("resourceId"));
    verify(arrayNode6).size();
    verify(arrayNode4).asText();
    verify(arrayNode).asText();
    verify(arrayNode6).asText();
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertEquals("As Text", ((SequenceFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getConditionExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceFlowElement());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetFlowElement());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((SequenceFlow) actualConvertJsonToElementResult).getWaypoints().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Then return SourceRef is {@code As Text}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); then return SourceRef is 'As Text'")
  void testConvertJsonToElement_thenReturnSourceRefIsAsText2() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);
    when(arrayNode3.asText()).thenReturn("As Text");
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.isTextual()).thenReturn(true);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.isNull()).thenReturn(true);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    when(arrayNode4.asText()).thenReturn("");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);
    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode5);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode6 = mock(ArrayNode.class);
    when(arrayNode6.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    when(arrayNode6.asText()).thenReturn("As Text");
    when(arrayNode6.iterator()).thenReturn(iteratorResult);
    when(arrayNode6.size()).thenReturn(3);
    ArrayNode arrayNode7 = mock(ArrayNode.class);
    when(arrayNode7.get(Mockito.<String>any())).thenReturn(arrayNode6);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode7);
    ArrayNode arrayNode8 = mock(ArrayNode.class);
    when(arrayNode8.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode8);

    // Act
    FlowElement actualConvertJsonToElementResult = sequenceFlowJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode4).isNull();
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode8).iterator();
    verify(arrayNode6).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode7, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode4).get(eq("conditionsequenceflow"));
    verify(arrayNode3, atLeast(1)).get(eq("expression"));
    verify(arrayNode6).get(eq("overrideid"));
    verify(arrayNode5).get(eq("resourceId"));
    verify(arrayNode6).size();
    verify(arrayNode4).asText();
    verify(arrayNode).asText();
    verify(arrayNode6).asText();
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertEquals("As Text", ((SequenceFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getConditionExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceFlowElement());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetFlowElement());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((SequenceFlow) actualConvertJsonToElementResult).getWaypoints().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Then return SourceRef is {@code As Text}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); then return SourceRef is 'As Text'")
  void testConvertJsonToElement_thenReturnSourceRefIsAsText3() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);
    when(arrayNode3.asText()).thenReturn("As Text");
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.isTextual()).thenReturn(true);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.isNull()).thenReturn(true);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    when(arrayNode4.asText()).thenReturn("");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);
    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode5);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode6 = mock(ArrayNode.class);
    when(arrayNode6.isNull()).thenReturn(true);
    when(arrayNode6.asText()).thenReturn("As Text");
    ArrayNode arrayNode7 = mock(ArrayNode.class);
    when(arrayNode7.get(Mockito.<String>any())).thenReturn(arrayNode6);
    when(arrayNode7.asText()).thenReturn("As Text");
    when(arrayNode7.iterator()).thenReturn(iteratorResult);
    when(arrayNode7.size()).thenReturn(3);
    ArrayNode arrayNode8 = mock(ArrayNode.class);
    when(arrayNode8.get(Mockito.<String>any())).thenReturn(arrayNode7);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode8);
    ArrayNode arrayNode9 = mock(ArrayNode.class);
    when(arrayNode9.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode9);

    // Act
    FlowElement actualConvertJsonToElementResult = sequenceFlowJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode4).isNull();
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode6).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode9).iterator();
    verify(arrayNode7).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode8, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode4).get(eq("conditionsequenceflow"));
    verify(arrayNode3, atLeast(1)).get(eq("expression"));
    verify(arrayNode7).get(eq("overrideid"));
    verify(arrayNode5).get(eq("resourceId"));
    verify(arrayNode7).size();
    verify(arrayNode4).asText();
    verify(arrayNode).asText();
    verify(arrayNode7).asText();
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertEquals("As Text", ((SequenceFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getConditionExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceFlowElement());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetFlowElement());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((SequenceFlow) actualConvertJsonToElementResult).getWaypoints().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  void testConvertJsonToElement_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);
    when(arrayNode3.asText()).thenReturn("As Text");
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.isTextual()).thenReturn(true);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    when(arrayNode4.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);
    ArrayNode modelNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    FlowElement actualConvertJsonToElementResult = sequenceFlowJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3).isTextual();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode4).get(eq("conditionsequenceflow"));
    verify(arrayNode3, atLeast(1)).get(eq("expression"));
    verify(arrayNode4).asText();
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getConditionExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceFlowElement());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetFlowElement());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((SequenceFlow) actualConvertJsonToElementResult).getWaypoints().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return SourceRef is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); when Instance; then return SourceRef is 'null'")
  void testConvertJsonToElement_whenInstance_thenReturnSourceRefIsNull() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);
    when(arrayNode3.asText()).thenReturn("As Text");
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.isTextual()).thenReturn(true);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    when(arrayNode4.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = sequenceFlowJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3).isTextual();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode4).get(eq("conditionsequenceflow"));
    verify(arrayNode3, atLeast(1)).get(eq("expression"));
    verify(arrayNode4).asText();
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getConditionExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetRef());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getSourceFlowElement());
    assertNull(((SequenceFlow) actualConvertJsonToElementResult).getTargetFlowElement());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((SequenceFlow) actualConvertJsonToElementResult).getWaypoints().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}.
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setFieldConditionExpression(SequenceFlow, JsonNode)")
  void testSetFieldConditionExpression() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    SequenceFlow flow = new SequenceFlow("Source Ref", "Target Ref");

    // Act
    sequenceFlowJsonConverter.setFieldConditionExpression(flow, MissingNode.getInstance());

    // Assert that nothing has changed
    assertTrue(flow.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}.
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setFieldConditionExpression(SequenceFlow, JsonNode)")
  void testSetFieldConditionExpression2() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    SequenceFlow flow = new SequenceFlow("Source Ref", "Target Ref");

    ArrayNode expressionNode = mock(ArrayNode.class);
    when(expressionNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    // Act
    sequenceFlowJsonConverter.setFieldConditionExpression(flow, expressionNode);

    // Assert
    verify(expressionNode, atLeast(1)).get(Mockito.<String>any());
    Map<String, List<ExtensionElement>> extensionElements = flow.getExtensionElements();
    assertEquals(3, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("conditionFieldId");
    assertEquals(1, getResult.size());
    ExtensionElement getResult2 = getResult.get(0);
    assertEquals("", getResult2.getElementText());
    List<ExtensionElement> getResult3 = extensionElements.get("conditionOperator");
    assertEquals(1, getResult3.size());
    ExtensionElement getResult4 = getResult3.get(0);
    assertEquals("", getResult4.getElementText());
    List<ExtensionElement> getResult5 = extensionElements.get("conditionValue");
    assertEquals(1, getResult5.size());
    ExtensionElement getResult6 = getResult5.get(0);
    assertEquals("", getResult6.getElementText());
    assertEquals("${  }", flow.getConditionExpression());
    assertEquals("conditionFieldId", getResult2.getName());
    assertEquals("conditionOperator", getResult4.getName());
    assertEquals("conditionValue", getResult6.getName());
    assertEquals("modeler", getResult2.getNamespacePrefix());
    assertEquals("modeler", getResult4.getNamespacePrefix());
    assertEquals("modeler", getResult6.getNamespacePrefix());
    assertNull(getResult2.getId());
    assertNull(getResult4.getId());
    assertNull(getResult6.getId());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult4.getXmlColumnNumber());
    assertEquals(0, getResult6.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertEquals(0, getResult4.getXmlRowNumber());
    assertEquals(0, getResult6.getXmlRowNumber());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult4.getAttributes().isEmpty());
    assertTrue(getResult6.getAttributes().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(getResult4.getExtensionElements().isEmpty());
    assertTrue(getResult6.getExtensionElements().isEmpty());
    assertTrue(getResult2.getChildElements().isEmpty());
    assertTrue(getResult4.getChildElements().isEmpty());
    assertTrue(getResult6.getChildElements().isEmpty());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult2.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult4.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult6.getNamespace());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link JsonNode#isNull()} return
   * {@code true}.</li>
   *   <li>Then calls {@link JsonNode#isNull()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setFieldConditionExpression(SequenceFlow, JsonNode); given ArrayNode isNull() return 'true'; then calls isNull()")
  void testSetFieldConditionExpression_givenArrayNodeIsNullReturnTrue_thenCallsIsNull() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    SequenceFlow flow = mock(SequenceFlow.class);
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    ArrayNode expressionNode = mock(ArrayNode.class);
    when(expressionNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    sequenceFlowJsonConverter.setFieldConditionExpression(flow, expressionNode);

    // Assert that nothing has changed
    verify(arrayNode, atLeast(1)).isNull();
    verify(expressionNode, atLeast(1)).get(Mockito.<String>any());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setFieldConditionExpression(SequenceFlow, JsonNode); given ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  void testSetFieldConditionExpression_givenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    SequenceFlow flow = mock(SequenceFlow.class);
    doNothing().when(flow).addExtensionElement(Mockito.<ExtensionElement>any());
    doNothing().when(flow).setConditionExpression(Mockito.<String>any());
    ArrayNode expressionNode = mock(ArrayNode.class);
    when(expressionNode.get(Mockito.<String>any()))
        .thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Act
    sequenceFlowJsonConverter.setFieldConditionExpression(flow, expressionNode);

    // Assert
    verify(expressionNode, atLeast(1)).get(Mockito.<String>any());
    verify(flow, atLeast(1)).addExtensionElement(Mockito.<ExtensionElement>any());
    verify(flow).setConditionExpression(eq("${  }"));
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}.
   * <ul>
   *   <li>Given {@link BigIntegerNode#BigIntegerNode(BigInteger)} with v is valueOf
   * one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setFieldConditionExpression(SequenceFlow, JsonNode); given BigIntegerNode(BigInteger) with v is valueOf one")
  void testSetFieldConditionExpression_givenBigIntegerNodeWithVIsValueOfOne() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    SequenceFlow flow = mock(SequenceFlow.class);
    doNothing().when(flow).addExtensionElement(Mockito.<ExtensionElement>any());
    doNothing().when(flow).setConditionExpression(Mockito.<String>any());
    ArrayNode expressionNode = mock(ArrayNode.class);
    when(expressionNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));

    // Act
    sequenceFlowJsonConverter.setFieldConditionExpression(flow, expressionNode);

    // Assert
    verify(expressionNode, atLeast(1)).get(Mockito.<String>any());
    verify(flow, atLeast(1)).addExtensionElement(Mockito.<ExtensionElement>any());
    verify(flow).setConditionExpression(eq("${1 1 1}"));
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>Then calls
   * {@link BaseElement#addExtensionElement(ExtensionElement)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setFieldConditionExpression(SequenceFlow, JsonNode); given Instance; then calls addExtensionElement(ExtensionElement)")
  void testSetFieldConditionExpression_givenInstance_thenCallsAddExtensionElement() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    SequenceFlow flow = mock(SequenceFlow.class);
    doNothing().when(flow).addExtensionElement(Mockito.<ExtensionElement>any());
    doNothing().when(flow).setConditionExpression(Mockito.<String>any());
    ArrayNode expressionNode = mock(ArrayNode.class);
    when(expressionNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    // Act
    sequenceFlowJsonConverter.setFieldConditionExpression(flow, expressionNode);

    // Assert
    verify(expressionNode, atLeast(1)).get(Mockito.<String>any());
    verify(flow, atLeast(1)).addExtensionElement(Mockito.<ExtensionElement>any());
    verify(flow).setConditionExpression(eq("${  }"));
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setFieldConditionExpression(SequenceFlow, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  void testSetFieldConditionExpression_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    SequenceFlow flow = new SequenceFlow("Source Ref", "Target Ref");

    // Act
    sequenceFlowJsonConverter.setFieldConditionExpression(flow,
        new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Assert that nothing has changed
    assertTrue(flow.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}.
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setOutcomeConditionExpression(SequenceFlow, JsonNode)")
  void testSetOutcomeConditionExpression() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    SequenceFlow flow = new SequenceFlow("Source Ref", "Target Ref");

    // Act
    sequenceFlowJsonConverter.setOutcomeConditionExpression(flow, MissingNode.getInstance());

    // Assert that nothing has changed
    assertTrue(flow.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}.
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setOutcomeConditionExpression(SequenceFlow, JsonNode)")
  void testSetOutcomeConditionExpression2() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    SequenceFlow flow = new SequenceFlow("Source Ref", "Target Ref");

    ArrayNode expressionNode = mock(ArrayNode.class);
    when(expressionNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    // Act
    sequenceFlowJsonConverter.setOutcomeConditionExpression(flow, expressionNode);

    // Assert
    verify(expressionNode, atLeast(1)).get(Mockito.<String>any());
    Map<String, List<ExtensionElement>> extensionElements = flow.getExtensionElements();
    assertEquals(3, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("conditionOperator");
    assertEquals(1, getResult.size());
    ExtensionElement getResult2 = getResult.get(0);
    assertEquals("", getResult2.getElementText());
    List<ExtensionElement> getResult3 = extensionElements.get("conditionOutcomeName");
    assertEquals(1, getResult3.size());
    ExtensionElement getResult4 = getResult3.get(0);
    assertEquals("", getResult4.getElementText());
    assertEquals("${form0outcome  }", flow.getConditionExpression());
    List<ExtensionElement> getResult5 = extensionElements.get("conditionFormId");
    assertEquals(1, getResult5.size());
    ExtensionElement getResult6 = getResult5.get(0);
    assertEquals("0", getResult6.getElementText());
    assertEquals("conditionFormId", getResult6.getName());
    assertEquals("conditionOperator", getResult2.getName());
    assertEquals("conditionOutcomeName", getResult4.getName());
    assertEquals("modeler", getResult6.getNamespacePrefix());
    assertEquals("modeler", getResult2.getNamespacePrefix());
    assertEquals("modeler", getResult4.getNamespacePrefix());
    assertNull(getResult6.getId());
    assertNull(getResult2.getId());
    assertNull(getResult4.getId());
    assertEquals(0, getResult6.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult4.getXmlColumnNumber());
    assertEquals(0, getResult6.getXmlRowNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertEquals(0, getResult4.getXmlRowNumber());
    assertTrue(getResult6.getAttributes().isEmpty());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult4.getAttributes().isEmpty());
    assertTrue(getResult6.getExtensionElements().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(getResult4.getExtensionElements().isEmpty());
    assertTrue(getResult6.getChildElements().isEmpty());
    assertTrue(getResult2.getChildElements().isEmpty());
    assertTrue(getResult4.getChildElements().isEmpty());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult6.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult2.getNamespace());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult4.getNamespace());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link JsonNode#isNull()} return
   * {@code true}.</li>
   *   <li>Then calls {@link JsonNode#isNull()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setOutcomeConditionExpression(SequenceFlow, JsonNode); given ArrayNode isNull() return 'true'; then calls isNull()")
  void testSetOutcomeConditionExpression_givenArrayNodeIsNullReturnTrue_thenCallsIsNull() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    SequenceFlow flow = mock(SequenceFlow.class);
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    ArrayNode expressionNode = mock(ArrayNode.class);
    when(expressionNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    sequenceFlowJsonConverter.setOutcomeConditionExpression(flow, expressionNode);

    // Assert that nothing has changed
    verify(arrayNode, atLeast(1)).isNull();
    verify(expressionNode, atLeast(1)).get(Mockito.<String>any());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setOutcomeConditionExpression(SequenceFlow, JsonNode); given ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  void testSetOutcomeConditionExpression_givenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    SequenceFlow flow = mock(SequenceFlow.class);
    doNothing().when(flow).addExtensionElement(Mockito.<ExtensionElement>any());
    doNothing().when(flow).setConditionExpression(Mockito.<String>any());
    ArrayNode expressionNode = mock(ArrayNode.class);
    when(expressionNode.get(Mockito.<String>any()))
        .thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Act
    sequenceFlowJsonConverter.setOutcomeConditionExpression(flow, expressionNode);

    // Assert
    verify(expressionNode, atLeast(1)).get(Mockito.<String>any());
    verify(flow, atLeast(1)).addExtensionElement(Mockito.<ExtensionElement>any());
    verify(flow).setConditionExpression(eq("${form0outcome  }"));
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}.
   * <ul>
   *   <li>Given {@link BigIntegerNode#BigIntegerNode(BigInteger)} with v is valueOf
   * one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setOutcomeConditionExpression(SequenceFlow, JsonNode); given BigIntegerNode(BigInteger) with v is valueOf one")
  void testSetOutcomeConditionExpression_givenBigIntegerNodeWithVIsValueOfOne() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    SequenceFlow flow = mock(SequenceFlow.class);
    doNothing().when(flow).addExtensionElement(Mockito.<ExtensionElement>any());
    doNothing().when(flow).setConditionExpression(Mockito.<String>any());
    ArrayNode expressionNode = mock(ArrayNode.class);
    when(expressionNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));

    // Act
    sequenceFlowJsonConverter.setOutcomeConditionExpression(flow, expressionNode);

    // Assert
    verify(expressionNode, atLeast(1)).get(Mockito.<String>any());
    verify(flow, atLeast(1)).addExtensionElement(Mockito.<ExtensionElement>any());
    verify(flow).setConditionExpression(eq("${form1outcome 1 1}"));
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}.
   * <ul>
   *   <li>Given False.</li>
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return False.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setOutcomeConditionExpression(SequenceFlow, JsonNode); given False; when ArrayNode get(String) return False")
  void testSetOutcomeConditionExpression_givenFalse_whenArrayNodeGetReturnFalse() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    SequenceFlow flow = mock(SequenceFlow.class);
    doNothing().when(flow).addExtensionElement(Mockito.<ExtensionElement>any());
    doNothing().when(flow).setConditionExpression(Mockito.<String>any());
    ArrayNode expressionNode = mock(ArrayNode.class);
    when(expressionNode.get(Mockito.<String>any())).thenReturn(BooleanNode.getFalse());

    // Act
    sequenceFlowJsonConverter.setOutcomeConditionExpression(flow, expressionNode);

    // Assert
    verify(expressionNode, atLeast(1)).get(Mockito.<String>any());
    verify(flow, atLeast(1)).addExtensionElement(Mockito.<ExtensionElement>any());
    verify(flow).setConditionExpression(eq("${form0outcome false false}"));
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return
   * Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setOutcomeConditionExpression(SequenceFlow, JsonNode); given Instance; when ArrayNode get(String) return Instance")
  void testSetOutcomeConditionExpression_givenInstance_whenArrayNodeGetReturnInstance() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    SequenceFlow flow = mock(SequenceFlow.class);
    doNothing().when(flow).addExtensionElement(Mockito.<ExtensionElement>any());
    doNothing().when(flow).setConditionExpression(Mockito.<String>any());
    ArrayNode expressionNode = mock(ArrayNode.class);
    when(expressionNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    // Act
    sequenceFlowJsonConverter.setOutcomeConditionExpression(flow, expressionNode);

    // Assert
    verify(expressionNode, atLeast(1)).get(Mockito.<String>any());
    verify(flow, atLeast(1)).addExtensionElement(Mockito.<ExtensionElement>any());
    verify(flow).setConditionExpression(eq("${form0outcome  }"));
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setOutcomeConditionExpression(SequenceFlow, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  void testSetOutcomeConditionExpression_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    SequenceFlow flow = new SequenceFlow("Source Ref", "Target Ref");

    // Act
    sequenceFlowJsonConverter.setOutcomeConditionExpression(flow,
        new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Assert that nothing has changed
    assertTrue(flow.getExtensionElements().isEmpty());
  }

  /**
   * Test
   * {@link SequenceFlowJsonConverter#addExtensionElement(String, String, SequenceFlow)}.
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#addExtensionElement(String, String, SequenceFlow)}
   */
  @Test
  @DisplayName("Test addExtensionElement(String, String, SequenceFlow)")
  void testAddExtensionElement() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    SequenceFlow flow = new SequenceFlow("Source Ref", "Target Ref");

    // Act
    sequenceFlowJsonConverter.addExtensionElement("Name", "42", flow);

    // Assert
    Map<String, List<ExtensionElement>> extensionElements = flow.getExtensionElements();
    assertEquals(1, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("Name");
    assertEquals(1, getResult.size());
    ExtensionElement getResult2 = getResult.get(0);
    assertEquals("42", getResult2.getElementText());
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
   * Test
   * {@link SequenceFlowJsonConverter#addExtensionElement(String, String, SequenceFlow)}.
   * <p>
   * Method under test:
   * {@link SequenceFlowJsonConverter#addExtensionElement(String, String, SequenceFlow)}
   */
  @Test
  @DisplayName("Test addExtensionElement(String, String, SequenceFlow)")
  void testAddExtensionElement2() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    SequenceFlow flow = new SequenceFlow("Source Ref", "Target Ref");

    // Act
    sequenceFlowJsonConverter.addExtensionElement("", "42", flow);

    // Assert
    assertTrue(flow.getExtensionElements().isEmpty());
  }

  /**
   * Test new {@link SequenceFlowJsonConverter} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link SequenceFlowJsonConverter}
   */
  @Test
  @DisplayName("Test new SequenceFlowJsonConverter (default constructor)")
  void testNewSequenceFlowJsonConverter() throws MissingResourceException {
    // Arrange and Act
    SequenceFlowJsonConverter actualSequenceFlowJsonConverter = new SequenceFlowJsonConverter();

    // Assert
    ObjectMapper objectMapper = actualSequenceFlowJsonConverter.objectMapper;
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
    assertNull(actualSequenceFlowJsonConverter.shapesArrayNode);
    assertNull(actualSequenceFlowJsonConverter.flowElementNode);
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
    assertNull(actualSequenceFlowJsonConverter.model);
    assertNull(actualSequenceFlowJsonConverter.processor);
    assertEquals(0, factory.getFormatGeneratorFeatures());
    assertEquals(0, factory.getFormatParserFeatures());
    assertEquals(0, deserializationContext.getDeserializationFeatures());
    assertEquals(0, timeZone.getDSTSavings());
    assertEquals(0.0d, actualSequenceFlowJsonConverter.subProcessX);
    assertEquals(0.0d, actualSequenceFlowJsonConverter.subProcessY);
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
    ObjectMapper expectedCodec = actualSequenceFlowJsonConverter.objectMapper;
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
