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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BinaryNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.SequenceFlow;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SequenceFlowJsonConverterDiffblueTest {
  /**
   * Test {@link SequenceFlowJsonConverter#fillJsonTypes(Map)}.
   *
   * <p>Method under test: {@link SequenceFlowJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SequenceFlowJsonConverter.fillJsonTypes(Map)"})
  void testFillJsonTypes() {
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
   *
   * <p>Method under test: {@link SequenceFlowJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String SequenceFlowJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();

    // Act and Assert
    assertEquals("SequenceFlow", sequenceFlowJsonConverter.getStencilId(new ActivitiListener()));
  }

  /**
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <p>Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.isTextual()).thenReturn(true);

    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    when(arrayNode4.asText()).thenReturn("As Text");

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);

    ArrayNode arrayNode5 = mock(ArrayNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(arrayNode5.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode5);

    ArrayNode arrayNode6 = mock(ArrayNode.class);
    when(arrayNode6.iterator()).thenReturn(jsonNodeList.iterator());

    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode6);

    // Act
    FlowElement actualConvertJsonToElementResult =
        sequenceFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode6).iterator();
    verify(arrayNode5, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(arrayNode4).get("conditionsequenceflow");
    verify(arrayNode3, atLeast(1)).get("expression");
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <p>Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement2() throws UnsupportedEncodingException {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.isTextual()).thenReturn(true);

    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    when(arrayNode4.asText()).thenReturn("As Text");

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);

    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.get(Mockito.<String>any()))
        .thenReturn(new BinaryNode("AXAXAXAX".getBytes("UTF-8")));

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
    FlowElement actualConvertJsonToElementResult =
        sequenceFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode8).iterator();
    verify(arrayNode6, atLeast(1)).iterator();
    verify(arrayNode7, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(arrayNode4).get("conditionsequenceflow");
    verify(arrayNode3, atLeast(1)).get("expression");
    verify(arrayNode5).get("resourceId");
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <p>Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
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
    when(arrayNode4.isNull()).thenReturn(false);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    when(arrayNode4.asText()).thenReturn("As Text");

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);

    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.asText()).thenReturn("As Text");

    ArrayNode arrayNode6 = mock(ArrayNode.class);
    when(arrayNode6.get(Mockito.<String>any())).thenReturn(arrayNode5);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode6);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode7 = mock(ArrayNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(arrayNode7.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));
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
    FlowElement actualConvertJsonToElementResult =
        sequenceFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode4).isNull();
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode9).iterator();
    verify(arrayNode7).iterator();
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode8, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(arrayNode3, atLeast(1)).get("expression");
    verify(arrayNode7).get("overrideid");
    verify(arrayNode6).get("resourceId");
    verify(arrayNode7).size();
    verify(arrayNode4).asText();
    verify(arrayNode3).asText();
    verify(arrayNode).asText();
    verify(arrayNode7).asText();
    verify(arrayNode5).asText();
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add valueOf ten.
   *   <li>Then calls {@link ArrayNode#iterator()}.
   * </ul>
   *
   * <p>Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayList() add valueOf ten; then calls iterator()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenArrayListAddValueOfTen_thenCallsIterator() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.isTextual()).thenReturn(true);

    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    when(arrayNode4.asText()).thenReturn("As Text");

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(DoubleNode.valueOf(10.0d));

    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.iterator()).thenReturn(jsonNodeList.iterator());

    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode5);

    // Act
    FlowElement actualConvertJsonToElementResult =
        sequenceFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode5).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(arrayNode4).get("conditionsequenceflow");
    verify(arrayNode3, atLeast(1)).get("expression");
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add valueOf ten.
   *   <li>Then calls {@link ArrayNode#size()}.
   * </ul>
   *
   * <p>Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayList() add valueOf ten; then calls size()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenArrayListAddValueOfTen_thenCallsSize() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.isTextual()).thenReturn(true);

    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    when(arrayNode4.asText()).thenReturn("As Text");

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(DoubleNode.valueOf(10.0d));

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
    FlowElement actualConvertJsonToElementResult =
        sequenceFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode7).iterator();
    verify(arrayNode5, atLeast(1)).iterator();
    verify(arrayNode6, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(arrayNode4).get("conditionsequenceflow");
    verify(arrayNode3, atLeast(1)).get("expression");
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#asText()} return {@code variables}.
   *   <li>When valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode asText() return 'variables'; when valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenArrayNodeAsTextReturnVariables_whenValueOfTen() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("variables");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.isTextual()).thenReturn(true);

    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    when(arrayNode4.asText()).thenReturn("As Text");

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);

    // Act
    FlowElement actualConvertJsonToElementResult =
        sequenceFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode3).isNull();
    verify(arrayNode3).isTextual();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode4).get("conditionsequenceflow");
    verify(arrayNode3, atLeast(1)).get("expression");
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return {@code null}.
   *   <li>Then return SourceRef is {@code As Text}.
   * </ul>
   *
   * <p>Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode get(String) return 'null'; then return SourceRef is 'As Text'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
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
    when(arrayNode4.isNull()).thenReturn(false);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    when(arrayNode4.asText()).thenReturn("As Text");

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);

    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.asText()).thenReturn("As Text");

    ArrayNode arrayNode6 = mock(ArrayNode.class);
    when(arrayNode6.get(Mockito.<String>any())).thenReturn(arrayNode5);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode6);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode7 = mock(ArrayNode.class);
    when(arrayNode7.get(Mockito.<String>any())).thenReturn(null);
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
    FlowElement actualConvertJsonToElementResult =
        sequenceFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode4).isNull();
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode9).iterator();
    verify(arrayNode7).iterator();
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode8, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(arrayNode3, atLeast(1)).get("expression");
    verify(arrayNode7).get("overrideid");
    verify(arrayNode6).get("resourceId");
    verify(arrayNode7).size();
    verify(arrayNode4).asText();
    verify(arrayNode3).asText();
    verify(arrayNode).asText();
    verify(arrayNode7).asText();
    verify(arrayNode5).asText();
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return valueOf ten.
   *   <li>Then calls {@link ArrayNode#iterator()}.
   * </ul>
   *
   * <p>Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode get(String) return valueOf ten; then calls iterator()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenArrayNodeGetReturnValueOfTen_thenCallsIterator() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.isTextual()).thenReturn(true);

    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    when(arrayNode4.asText()).thenReturn("As Text");

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);

    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode5);

    ArrayNode arrayNode6 = mock(ArrayNode.class);
    when(arrayNode6.iterator()).thenReturn(jsonNodeList.iterator());

    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode6);

    // Act
    FlowElement actualConvertJsonToElementResult =
        sequenceFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode6).iterator();
    verify(arrayNode5, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(arrayNode4).get("conditionsequenceflow");
    verify(arrayNode3, atLeast(1)).get("expression");
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return valueOf ten.
   *   <li>Then calls {@link ArrayNode#size()}.
   * </ul>
   *
   * <p>Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode get(String) return valueOf ten; then calls size()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenArrayNodeGetReturnValueOfTen_thenCallsSize() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.isTextual()).thenReturn(true);

    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    when(arrayNode4.asText()).thenReturn("As Text");

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);

    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

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
    FlowElement actualConvertJsonToElementResult =
        sequenceFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode8).iterator();
    verify(arrayNode6, atLeast(1)).iterator();
    verify(arrayNode7, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(arrayNode4).get("conditionsequenceflow");
    verify(arrayNode3, atLeast(1)).get("expression");
    verify(arrayNode5).get("resourceId");
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return valueOf ten.
   *   <li>When valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode get(String) return valueOf ten; when valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenArrayNodeGetReturnValueOfTen_whenValueOfTen() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);

    // Act
    FlowElement actualConvertJsonToElementResult =
        sequenceFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode).get("conditionsequenceflow");
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return valueOf ten.
   *   <li>When valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode get(String) return valueOf ten; when valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenArrayNodeGetReturnValueOfTen_whenValueOfTen2() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));
    when(arrayNode.isTextual()).thenReturn(true);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.asText()).thenReturn("As Text");

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);

    // Act
    FlowElement actualConvertJsonToElementResult =
        sequenceFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode).isTextual();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2).get("conditionsequenceflow");
    verify(arrayNode, atLeast(1)).get("expression");
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#size()} return three.
   *   <li>Then calls {@link ArrayNode#size()}.
   * </ul>
   *
   * <p>Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode size() return three; then calls size()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
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
    FlowElement actualConvertJsonToElementResult =
        sequenceFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode7).iterator();
    verify(arrayNode5, atLeast(1)).iterator();
    verify(arrayNode6, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(arrayNode4).get("conditionsequenceflow");
    verify(arrayNode3, atLeast(1)).get("expression");
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.isTextual()).thenReturn(true);

    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    when(arrayNode4.asText()).thenReturn("As Text");

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);

    ArrayNode modelNode = mock(ArrayNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(modelNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));

    // Act
    FlowElement actualConvertJsonToElementResult =
        sequenceFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3).isTextual();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(arrayNode4).get("conditionsequenceflow");
    verify(arrayNode3, atLeast(1)).get("expression");
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>Given {@link BinaryNode#BinaryNode(byte[])} with data is {@code AXAXAXAX} Bytes is {@code
   *       UTF-8}.
   * </ul>
   *
   * <p>Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given BinaryNode(byte[]) with data is 'AXAXAXAX' Bytes is 'UTF-8'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenBinaryNodeWithDataIsAxaxaxaxBytesIsUtf8()
      throws UnsupportedEncodingException {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any()))
        .thenReturn(new BinaryNode("AXAXAXAX".getBytes("UTF-8")));
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);

    // Act
    FlowElement actualConvertJsonToElementResult =
        sequenceFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>Given valueOf ten.
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given valueOf ten; when ArrayNode get(String) return valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenValueOfTen_whenArrayNodeGetReturnValueOfTen() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);

    // Act
    FlowElement actualConvertJsonToElementResult =
        sequenceFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>Given valueOf ten.
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given valueOf ten; when ArrayNode get(String) return valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenValueOfTen_whenArrayNodeGetReturnValueOfTen2() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.isTextual()).thenReturn(true);

    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    when(arrayNode4.asText()).thenReturn("As Text");

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);

    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    // Act
    FlowElement actualConvertJsonToElementResult =
        sequenceFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3).isTextual();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(arrayNode4).get("conditionsequenceflow");
    verify(arrayNode3, atLeast(1)).get("expression");
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>Then calls {@link ArrayNode#iterator()}.
   * </ul>
   *
   * <p>Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); then calls iterator()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
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
    FlowElement actualConvertJsonToElementResult =
        sequenceFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode5).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(arrayNode4).get("conditionsequenceflow");
    verify(arrayNode3, atLeast(1)).get("expression");
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>Then return ConditionExpression is {@code 10.0}.
   * </ul>
   *
   * <p>Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); then return ConditionExpression is '10.0'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_thenReturnConditionExpressionIs100() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.isNull()).thenReturn(true);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isTextual()).thenReturn(true);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.asText()).thenReturn("As Text");

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode3);
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);

    // Act
    FlowElement actualConvertJsonToElementResult =
        sequenceFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode2).isNull();
    verify(arrayNode2).isTextual();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3).get("conditionsequenceflow");
    verify(arrayNode2, atLeast(1)).get("expression");
    verify(arrayNode3).asText();
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertEquals(
        "10.0", ((SequenceFlow) actualConvertJsonToElementResult).getConditionExpression());
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>Then return ConditionExpression is {@code As Text}.
   * </ul>
   *
   * <p>Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); then return ConditionExpression is 'As Text'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_thenReturnConditionExpressionIsAsText() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");
    when(arrayNode.isTextual()).thenReturn(true);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.asText()).thenReturn("As Text");

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);

    // Act
    FlowElement actualConvertJsonToElementResult =
        sequenceFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode).isTextual();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2).get("conditionsequenceflow");
    verify(arrayNode2).asText();
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertEquals(
        "As Text", ((SequenceFlow) actualConvertJsonToElementResult).getConditionExpression());
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>Then return ConditionExpression is {@code QVhBWEFYQVg=}.
   * </ul>
   *
   * <p>Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); then return ConditionExpression is 'QVhBWEFYQVg='")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_thenReturnConditionExpressionIsQVhBWEFYQVg()
      throws UnsupportedEncodingException {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any()))
        .thenReturn(new BinaryNode("AXAXAXAX".getBytes("UTF-8")));

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.isNull()).thenReturn(true);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isTextual()).thenReturn(true);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.asText()).thenReturn("As Text");

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode3);
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);

    // Act
    FlowElement actualConvertJsonToElementResult =
        sequenceFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode2).isNull();
    verify(arrayNode2).isTextual();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3).get("conditionsequenceflow");
    verify(arrayNode2, atLeast(1)).get("expression");
    verify(arrayNode3).asText();
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertEquals(
        "QVhBWEFYQVg=", ((SequenceFlow) actualConvertJsonToElementResult).getConditionExpression());
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>Then return SourceRef is {@code 10.0}.
   * </ul>
   *
   * <p>Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); then return SourceRef is '10.0'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_thenReturnSourceRefIs100() {
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
    when(arrayNode4.asText()).thenReturn("As Text");

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);

    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.asText()).thenReturn("As Text");

    ArrayNode arrayNode6 = mock(ArrayNode.class);
    when(arrayNode6.get(Mockito.<String>any())).thenReturn(arrayNode5);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode6);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode7 = mock(ArrayNode.class);
    when(arrayNode7.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));
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
    FlowElement actualConvertJsonToElementResult =
        sequenceFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode4).isNull();
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode9).iterator();
    verify(arrayNode7).iterator();
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode8, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(arrayNode3, atLeast(1)).get("expression");
    verify(arrayNode7, atLeast(1)).get("overrideid");
    verify(arrayNode6).get("resourceId");
    verify(arrayNode7).size();
    verify(arrayNode4).asText();
    verify(arrayNode3).asText();
    verify(arrayNode).asText();
    verify(arrayNode5).asText();
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertEquals("10.0", ((SequenceFlow) actualConvertJsonToElementResult).getSourceRef());
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>Then return SourceRef is {@code 10.0}.
   * </ul>
   *
   * <p>Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); then return SourceRef is '10.0'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_thenReturnSourceRefIs1002() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.isTextual()).thenReturn(true);

    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.isNull()).thenReturn(true);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    when(arrayNode4.asText()).thenReturn("As Text");

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);

    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.asText()).thenReturn("As Text");

    ArrayNode arrayNode6 = mock(ArrayNode.class);
    when(arrayNode6.get(Mockito.<String>any())).thenReturn(arrayNode5);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode6);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode7 = mock(ArrayNode.class);
    when(arrayNode7.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));
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
    FlowElement actualConvertJsonToElementResult =
        sequenceFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode4).isNull();
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode9).iterator();
    verify(arrayNode7).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode8, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(arrayNode4).get("conditionsequenceflow");
    verify(arrayNode3, atLeast(1)).get("expression");
    verify(arrayNode7, atLeast(1)).get("overrideid");
    verify(arrayNode6).get("resourceId");
    verify(arrayNode7).size();
    verify(arrayNode4).asText();
    verify(arrayNode).asText();
    verify(arrayNode5).asText();
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertEquals("10.0", ((SequenceFlow) actualConvertJsonToElementResult).getSourceRef());
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>Then return SourceRef is {@code As Text}.
   * </ul>
   *
   * <p>Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); then return SourceRef is 'As Text'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
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
    when(arrayNode4.isNull()).thenReturn(false);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    when(arrayNode4.asText()).thenReturn("As Text");

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);

    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.asText()).thenReturn("As Text");

    ArrayNode arrayNode6 = mock(ArrayNode.class);
    when(arrayNode6.get(Mockito.<String>any())).thenReturn(arrayNode5);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode6);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode7 = mock(ArrayNode.class);
    when(arrayNode7.isNull()).thenReturn(true);

    ArrayNode arrayNode8 = mock(ArrayNode.class);
    when(arrayNode8.get(Mockito.<String>any())).thenReturn(arrayNode7);
    when(arrayNode8.asText()).thenReturn("As Text");
    when(arrayNode8.iterator()).thenReturn(iteratorResult);
    when(arrayNode8.size()).thenReturn(3);

    ArrayNode arrayNode9 = mock(ArrayNode.class);
    when(arrayNode9.get(Mockito.<String>any())).thenReturn(arrayNode8);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode9);

    ArrayNode arrayNode10 = mock(ArrayNode.class);
    when(arrayNode10.iterator()).thenReturn(jsonNodeList2.iterator());

    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode10);

    // Act
    FlowElement actualConvertJsonToElementResult =
        sequenceFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode4).isNull();
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode7).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode10).iterator();
    verify(arrayNode8).iterator();
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode9, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(arrayNode3, atLeast(1)).get("expression");
    verify(arrayNode8).get("overrideid");
    verify(arrayNode6).get("resourceId");
    verify(arrayNode8).size();
    verify(arrayNode4).asText();
    verify(arrayNode3).asText();
    verify(arrayNode).asText();
    verify(arrayNode8).asText();
    verify(arrayNode5).asText();
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>Then return SourceRef is {@code QVhBWEFYQVg=}.
   * </ul>
   *
   * <p>Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); then return SourceRef is 'QVhBWEFYQVg='")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_thenReturnSourceRefIsQVhBWEFYQVg()
      throws UnsupportedEncodingException {
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
    when(arrayNode4.asText()).thenReturn("As Text");

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);

    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.asText()).thenReturn("As Text");

    ArrayNode arrayNode6 = mock(ArrayNode.class);
    when(arrayNode6.get(Mockito.<String>any())).thenReturn(arrayNode5);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode6);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode7 = mock(ArrayNode.class);
    when(arrayNode7.get(Mockito.<String>any()))
        .thenReturn(new BinaryNode("AXAXAXAX".getBytes("UTF-8")));
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
    FlowElement actualConvertJsonToElementResult =
        sequenceFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode4).isNull();
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode9).iterator();
    verify(arrayNode7).iterator();
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode8, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(arrayNode3, atLeast(1)).get("expression");
    verify(arrayNode7, atLeast(1)).get("overrideid");
    verify(arrayNode6).get("resourceId");
    verify(arrayNode7).size();
    verify(arrayNode4).asText();
    verify(arrayNode3).asText();
    verify(arrayNode).asText();
    verify(arrayNode5).asText();
    assertTrue(actualConvertJsonToElementResult instanceof SequenceFlow);
    assertEquals("QVhBWEFYQVg=", ((SequenceFlow) actualConvertJsonToElementResult).getSourceRef());
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>When valueOf ten.
   *   <li>Then return SourceRef is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); when valueOf ten; then return SourceRef is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_whenValueOfTen_thenReturnSourceRefIsNull() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.isTextual()).thenReturn(true);

    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    when(arrayNode4.asText()).thenReturn("As Text");

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode4);
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);

    // Act
    FlowElement actualConvertJsonToElementResult =
        sequenceFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode3).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3).isTextual();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode4).get("conditionsequenceflow");
    verify(arrayNode3, atLeast(1)).get("expression");
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
   * Test {@link SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}.
   *
   * <p>Method under test: {@link
   * SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setFieldConditionExpression(SequenceFlow, JsonNode)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SequenceFlowJsonConverter.setFieldConditionExpression(SequenceFlow, JsonNode)"
  })
  void testSetFieldConditionExpression() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    SequenceFlow flow = new SequenceFlow("Source Ref", "Target Ref");

    ArrayNode expressionNode = mock(ArrayNode.class);
    when(expressionNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    // Act
    sequenceFlowJsonConverter.setFieldConditionExpression(flow, expressionNode);

    // Assert
    verify(expressionNode, atLeast(1)).get(Mockito.<String>any());
    assertEquals("${10.0 10.0 10.0}", flow.getConditionExpression());
    Map<String, List<ExtensionElement>> extensionElements = flow.getExtensionElements();
    assertEquals(3, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("conditionFieldId");
    assertEquals(1, getResult.size());
    assertEquals("10.0", getResult.get(0).getElementText());
    List<ExtensionElement> getResult2 = extensionElements.get("conditionOperator");
    assertEquals(1, getResult2.size());
    assertEquals("10.0", getResult2.get(0).getElementText());
    List<ExtensionElement> getResult3 = extensionElements.get("conditionValue");
    assertEquals(1, getResult3.size());
    assertEquals("10.0", getResult3.get(0).getElementText());
  }

  /**
   * Test {@link SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}.
   *
   * <p>Method under test: {@link
   * SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setFieldConditionExpression(SequenceFlow, JsonNode)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SequenceFlowJsonConverter.setFieldConditionExpression(SequenceFlow, JsonNode)"
  })
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
    assertEquals("", getResult.get(0).getElementText());
    List<ExtensionElement> getResult2 = extensionElements.get("conditionOperator");
    assertEquals(1, getResult2.size());
    assertEquals("", getResult2.get(0).getElementText());
    List<ExtensionElement> getResult3 = extensionElements.get("conditionValue");
    assertEquals(1, getResult3.size());
    assertEquals("", getResult3.get(0).getElementText());
    assertEquals("${  }", flow.getConditionExpression());
  }

  /**
   * Test {@link SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}.
   *
   * <p>Method under test: {@link
   * SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setFieldConditionExpression(SequenceFlow, JsonNode)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SequenceFlowJsonConverter.setFieldConditionExpression(SequenceFlow, JsonNode)"
  })
  void testSetFieldConditionExpression3() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    SequenceFlow flow = new SequenceFlow("Source Ref", "Target Ref");

    ArrayNode expressionNode = mock(ArrayNode.class);
    when(expressionNode.get(Mockito.<String>any())).thenReturn(NullNode.getInstance());

    // Act
    sequenceFlowJsonConverter.setFieldConditionExpression(flow, expressionNode);

    // Assert that nothing has changed
    verify(expressionNode, atLeast(1)).get(Mockito.<String>any());
    assertTrue(flow.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#isNull()} return {@code false}.
   *   <li>Then calls {@link ArrayNode#isNull()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName(
      "Test setFieldConditionExpression(SequenceFlow, JsonNode); given ArrayNode isNull() return 'false'; then calls isNull()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SequenceFlowJsonConverter.setFieldConditionExpression(SequenceFlow, JsonNode)"
  })
  void testSetFieldConditionExpression_givenArrayNodeIsNullReturnFalse_thenCallsIsNull() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();

    SequenceFlow flow = mock(SequenceFlow.class);
    doNothing().when(flow).addExtensionElement(Mockito.<ExtensionElement>any());
    doNothing().when(flow).setConditionExpression(Mockito.<String>any());

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode expressionNode = mock(ArrayNode.class);
    when(expressionNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    sequenceFlowJsonConverter.setFieldConditionExpression(flow, expressionNode);

    // Assert
    verify(arrayNode, atLeast(1)).isNull();
    verify(expressionNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).asText();
    verify(flow, atLeast(1)).addExtensionElement(Mockito.<ExtensionElement>any());
    verify(flow).setConditionExpression("${As Text As Text As Text}");
  }

  /**
   * Test {@link SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}.
   *
   * <ul>
   *   <li>Given {@link BinaryNode#BinaryNode(byte[])} with data is {@code AXAXAXAX} Bytes is {@code
   *       UTF-8}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName(
      "Test setFieldConditionExpression(SequenceFlow, JsonNode); given BinaryNode(byte[]) with data is 'AXAXAXAX' Bytes is 'UTF-8'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SequenceFlowJsonConverter.setFieldConditionExpression(SequenceFlow, JsonNode)"
  })
  void testSetFieldConditionExpression_givenBinaryNodeWithDataIsAxaxaxaxBytesIsUtf8()
      throws UnsupportedEncodingException {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();

    SequenceFlow flow = mock(SequenceFlow.class);
    doNothing().when(flow).addExtensionElement(Mockito.<ExtensionElement>any());
    doNothing().when(flow).setConditionExpression(Mockito.<String>any());

    ArrayNode expressionNode = mock(ArrayNode.class);
    when(expressionNode.get(Mockito.<String>any()))
        .thenReturn(new BinaryNode("AXAXAXAX".getBytes("UTF-8")));

    // Act
    sequenceFlowJsonConverter.setFieldConditionExpression(flow, expressionNode);

    // Assert
    verify(expressionNode, atLeast(1)).get(Mockito.<String>any());
    verify(flow, atLeast(1)).addExtensionElement(Mockito.<ExtensionElement>any());
    verify(flow).setConditionExpression("${QVhBWEFYQVg= QVhBWEFYQVg= QVhBWEFYQVg=}");
  }

  /**
   * Test {@link SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}.
   *
   * <ul>
   *   <li>Given valueOf ten.
   *   <li>Then calls {@link SequenceFlow#addExtensionElement(ExtensionElement)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName(
      "Test setFieldConditionExpression(SequenceFlow, JsonNode); given valueOf ten; then calls addExtensionElement(ExtensionElement)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SequenceFlowJsonConverter.setFieldConditionExpression(SequenceFlow, JsonNode)"
  })
  void testSetFieldConditionExpression_givenValueOfTen_thenCallsAddExtensionElement() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();

    SequenceFlow flow = mock(SequenceFlow.class);
    doNothing().when(flow).addExtensionElement(Mockito.<ExtensionElement>any());
    doNothing().when(flow).setConditionExpression(Mockito.<String>any());

    ArrayNode expressionNode = mock(ArrayNode.class);
    when(expressionNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    // Act
    sequenceFlowJsonConverter.setFieldConditionExpression(flow, expressionNode);

    // Assert
    verify(expressionNode, atLeast(1)).get(Mockito.<String>any());
    verify(flow, atLeast(1)).addExtensionElement(Mockito.<ExtensionElement>any());
    verify(flow).setConditionExpression("${10.0 10.0 10.0}");
  }

  /**
   * Test {@link SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}.
   *
   * <ul>
   *   <li>When valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link
   * SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setFieldConditionExpression(SequenceFlow, JsonNode); when valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SequenceFlowJsonConverter.setFieldConditionExpression(SequenceFlow, JsonNode)"
  })
  void testSetFieldConditionExpression_whenValueOfTen() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    SequenceFlow flow = new SequenceFlow("Source Ref", "Target Ref");

    // Act
    sequenceFlowJsonConverter.setFieldConditionExpression(flow, DoubleNode.valueOf(10.0d));

    // Assert that nothing has changed
    assertTrue(flow.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}.
   *
   * <p>Method under test: {@link
   * SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setOutcomeConditionExpression(SequenceFlow, JsonNode)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SequenceFlowJsonConverter.setOutcomeConditionExpression(SequenceFlow, JsonNode)"
  })
  void testSetOutcomeConditionExpression() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    SequenceFlow flow = new SequenceFlow("Source Ref", "Target Ref");

    ArrayNode expressionNode = mock(ArrayNode.class);
    when(expressionNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    // Act
    sequenceFlowJsonConverter.setOutcomeConditionExpression(flow, expressionNode);

    // Assert
    verify(expressionNode, atLeast(1)).get(Mockito.<String>any());
    assertEquals("${form10outcome 10.0 10.0}", flow.getConditionExpression());
    Map<String, List<ExtensionElement>> extensionElements = flow.getExtensionElements();
    assertEquals(3, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("conditionFormId");
    assertEquals(1, getResult.size());
    assertEquals("10", getResult.get(0).getElementText());
    List<ExtensionElement> getResult2 = extensionElements.get("conditionOperator");
    assertEquals(1, getResult2.size());
    assertEquals("10.0", getResult2.get(0).getElementText());
    List<ExtensionElement> getResult3 = extensionElements.get("conditionOutcomeName");
    assertEquals(1, getResult3.size());
    assertEquals("10.0", getResult3.get(0).getElementText());
  }

  /**
   * Test {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}.
   *
   * <p>Method under test: {@link
   * SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setOutcomeConditionExpression(SequenceFlow, JsonNode)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SequenceFlowJsonConverter.setOutcomeConditionExpression(SequenceFlow, JsonNode)"
  })
  void testSetOutcomeConditionExpression2() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    SequenceFlow flow = new SequenceFlow("Source Ref", "Target Ref");

    ArrayNode expressionNode = mock(ArrayNode.class);
    when(expressionNode.get(Mockito.<String>any())).thenReturn(BooleanNode.getFalse());

    // Act
    sequenceFlowJsonConverter.setOutcomeConditionExpression(flow, expressionNode);

    // Assert
    verify(expressionNode, atLeast(1)).get(Mockito.<String>any());
    assertEquals("${form0outcome false false}", flow.getConditionExpression());
    Map<String, List<ExtensionElement>> extensionElements = flow.getExtensionElements();
    assertEquals(3, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("conditionFormId");
    assertEquals(1, getResult.size());
    assertEquals("0", getResult.get(0).getElementText());
    List<ExtensionElement> getResult2 = extensionElements.get("conditionOperator");
    assertEquals(1, getResult2.size());
    List<ExtensionElement> getResult3 = extensionElements.get("conditionOutcomeName");
    assertEquals(1, getResult3.size());
    assertEquals(Boolean.FALSE.toString(), getResult2.get(0).getElementText());
    assertEquals(Boolean.FALSE.toString(), getResult3.get(0).getElementText());
  }

  /**
   * Test {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}.
   *
   * <p>Method under test: {@link
   * SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setOutcomeConditionExpression(SequenceFlow, JsonNode)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SequenceFlowJsonConverter.setOutcomeConditionExpression(SequenceFlow, JsonNode)"
  })
  void testSetOutcomeConditionExpression3() {
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
    assertEquals("", getResult.get(0).getElementText());
    List<ExtensionElement> getResult2 = extensionElements.get("conditionOutcomeName");
    assertEquals(1, getResult2.size());
    assertEquals("", getResult2.get(0).getElementText());
    assertEquals("${form0outcome  }", flow.getConditionExpression());
    List<ExtensionElement> getResult3 = extensionElements.get("conditionFormId");
    assertEquals(1, getResult3.size());
    assertEquals("0", getResult3.get(0).getElementText());
  }

  /**
   * Test {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}.
   *
   * <p>Method under test: {@link
   * SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setOutcomeConditionExpression(SequenceFlow, JsonNode)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SequenceFlowJsonConverter.setOutcomeConditionExpression(SequenceFlow, JsonNode)"
  })
  void testSetOutcomeConditionExpression4() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    SequenceFlow flow = new SequenceFlow("Source Ref", "Target Ref");

    ArrayNode expressionNode = mock(ArrayNode.class);
    when(expressionNode.get(Mockito.<String>any())).thenReturn(NullNode.getInstance());

    // Act
    sequenceFlowJsonConverter.setOutcomeConditionExpression(flow, expressionNode);

    // Assert that nothing has changed
    verify(expressionNode, atLeast(1)).get(Mockito.<String>any());
    assertTrue(flow.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#asText()} return {@code As Text}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName(
      "Test setOutcomeConditionExpression(SequenceFlow, JsonNode); given ArrayNode asText() return 'As Text'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SequenceFlowJsonConverter.setOutcomeConditionExpression(SequenceFlow, JsonNode)"
  })
  void testSetOutcomeConditionExpression_givenArrayNodeAsTextReturnAsText() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();

    SequenceFlow flow = mock(SequenceFlow.class);
    doNothing().when(flow).addExtensionElement(Mockito.<ExtensionElement>any());
    doNothing().when(flow).setConditionExpression(Mockito.<String>any());

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");
    when(arrayNode.asLong()).thenReturn(1L);

    ArrayNode expressionNode = mock(ArrayNode.class);
    when(expressionNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    sequenceFlowJsonConverter.setOutcomeConditionExpression(flow, expressionNode);

    // Assert
    verify(arrayNode).asLong();
    verify(arrayNode, atLeast(1)).isNull();
    verify(expressionNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).asText();
    verify(flow, atLeast(1)).addExtensionElement(Mockito.<ExtensionElement>any());
    verify(flow).setConditionExpression("${form1outcome As Text As Text}");
  }

  /**
   * Test {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#asText()} return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName(
      "Test setOutcomeConditionExpression(SequenceFlow, JsonNode); given ArrayNode asText() return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SequenceFlowJsonConverter.setOutcomeConditionExpression(SequenceFlow, JsonNode)"
  })
  void testSetOutcomeConditionExpression_givenArrayNodeAsTextReturnNull() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    SequenceFlow flow = mock(SequenceFlow.class);

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn(null);
    when(arrayNode.asLong()).thenReturn(1L);

    ArrayNode expressionNode = mock(ArrayNode.class);
    when(expressionNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    sequenceFlowJsonConverter.setOutcomeConditionExpression(flow, expressionNode);

    // Assert
    verify(arrayNode).asLong();
    verify(arrayNode, atLeast(1)).isNull();
    verify(expressionNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).asText();
  }

  /**
   * Test {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}.
   *
   * <ul>
   *   <li>Given {@link BinaryNode#BinaryNode(byte[])} with data is {@code AXAXAXAX} Bytes is {@code
   *       UTF-8}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName(
      "Test setOutcomeConditionExpression(SequenceFlow, JsonNode); given BinaryNode(byte[]) with data is 'AXAXAXAX' Bytes is 'UTF-8'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SequenceFlowJsonConverter.setOutcomeConditionExpression(SequenceFlow, JsonNode)"
  })
  void testSetOutcomeConditionExpression_givenBinaryNodeWithDataIsAxaxaxaxBytesIsUtf8()
      throws UnsupportedEncodingException {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();

    SequenceFlow flow = mock(SequenceFlow.class);
    doNothing().when(flow).addExtensionElement(Mockito.<ExtensionElement>any());
    doNothing().when(flow).setConditionExpression(Mockito.<String>any());

    ArrayNode expressionNode = mock(ArrayNode.class);
    when(expressionNode.get(Mockito.<String>any()))
        .thenReturn(new BinaryNode("AXAXAXAX".getBytes("UTF-8")));

    // Act
    sequenceFlowJsonConverter.setOutcomeConditionExpression(flow, expressionNode);

    // Assert
    verify(expressionNode, atLeast(1)).get(Mockito.<String>any());
    verify(flow, atLeast(1)).addExtensionElement(Mockito.<ExtensionElement>any());
    verify(flow).setConditionExpression("${form0outcome QVhBWEFYQVg= QVhBWEFYQVg=}");
  }

  /**
   * Test {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}.
   *
   * <ul>
   *   <li>Given valueOf ten.
   *   <li>Then calls {@link SequenceFlow#addExtensionElement(ExtensionElement)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName(
      "Test setOutcomeConditionExpression(SequenceFlow, JsonNode); given valueOf ten; then calls addExtensionElement(ExtensionElement)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SequenceFlowJsonConverter.setOutcomeConditionExpression(SequenceFlow, JsonNode)"
  })
  void testSetOutcomeConditionExpression_givenValueOfTen_thenCallsAddExtensionElement() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();

    SequenceFlow flow = mock(SequenceFlow.class);
    doNothing().when(flow).addExtensionElement(Mockito.<ExtensionElement>any());
    doNothing().when(flow).setConditionExpression(Mockito.<String>any());

    ArrayNode expressionNode = mock(ArrayNode.class);
    when(expressionNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    // Act
    sequenceFlowJsonConverter.setOutcomeConditionExpression(flow, expressionNode);

    // Assert
    verify(expressionNode, atLeast(1)).get(Mockito.<String>any());
    verify(flow, atLeast(1)).addExtensionElement(Mockito.<ExtensionElement>any());
    verify(flow).setConditionExpression("${form10outcome 10.0 10.0}");
  }

  /**
   * Test {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}.
   *
   * <ul>
   *   <li>When valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link
   * SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setOutcomeConditionExpression(SequenceFlow, JsonNode); when valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SequenceFlowJsonConverter.setOutcomeConditionExpression(SequenceFlow, JsonNode)"
  })
  void testSetOutcomeConditionExpression_whenValueOfTen() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    SequenceFlow flow = new SequenceFlow("Source Ref", "Target Ref");

    // Act
    sequenceFlowJsonConverter.setOutcomeConditionExpression(flow, DoubleNode.valueOf(10.0d));

    // Assert that nothing has changed
    assertTrue(flow.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link SequenceFlowJsonConverter#addExtensionElement(String, String, SequenceFlow)}.
   *
   * <p>Method under test: {@link SequenceFlowJsonConverter#addExtensionElement(String, String,
   * SequenceFlow)}
   */
  @Test
  @DisplayName("Test addExtensionElement(String, String, SequenceFlow)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SequenceFlowJsonConverter.addExtensionElement(String, String, SequenceFlow)"
  })
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
   * Test {@link SequenceFlowJsonConverter#addExtensionElement(String, String, SequenceFlow)}.
   *
   * <p>Method under test: {@link SequenceFlowJsonConverter#addExtensionElement(String, String,
   * SequenceFlow)}
   */
  @Test
  @DisplayName("Test addExtensionElement(String, String, SequenceFlow)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SequenceFlowJsonConverter.addExtensionElement(String, String, SequenceFlow)"
  })
  void testAddExtensionElement2() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    SequenceFlow flow = new SequenceFlow("Source Ref", "Target Ref");

    // Act
    sequenceFlowJsonConverter.addExtensionElement("", "42", flow);

    // Assert that nothing has changed
    assertTrue(flow.getExtensionElements().isEmpty());
  }

  /**
   * Test new {@link SequenceFlowJsonConverter} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link SequenceFlowJsonConverter}
   */
  @Test
  @DisplayName("Test new SequenceFlowJsonConverter (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SequenceFlowJsonConverter.<init>()"})
  void testNewSequenceFlowJsonConverter() {
    // Arrange and Act
    SequenceFlowJsonConverter actualSequenceFlowJsonConverter = new SequenceFlowJsonConverter();

    // Assert
    assertNull(actualSequenceFlowJsonConverter.shapesArrayNode);
    assertNull(actualSequenceFlowJsonConverter.flowElementNode);
    assertNull(actualSequenceFlowJsonConverter.model);
    assertNull(actualSequenceFlowJsonConverter.processor);
    assertEquals(0.0d, actualSequenceFlowJsonConverter.subProcessX);
    assertEquals(0.0d, actualSequenceFlowJsonConverter.subProcessY);
  }
}
