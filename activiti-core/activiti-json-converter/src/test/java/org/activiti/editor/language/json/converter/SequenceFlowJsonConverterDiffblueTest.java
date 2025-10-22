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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BigIntegerNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.ContainerNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.MissingNode;
import java.math.BigInteger;
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
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map)")
  @Tag("MaintainedByDiffblue")
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
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String SequenceFlowJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();

    // Act and Assert
    assertEquals("SequenceFlow", sequenceFlowJsonConverter.getStencilId(new ActivitiListener()));
  }

  /**
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add Instance.</li>
   *   <li>Then calls {@link JsonNode#iterator()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayList() add Instance; then calls iterator()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add Instance.</li>
   *   <li>Then calls {@link ArrayNode#size()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayList() add Instance; then calls size()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ContainerNode#asText()} return {@code variables}.</li>
   *   <li>When Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode asText() return 'variables'; when Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return {@link BigIntegerNode#BigIntegerNode(BigInteger)} with v is valueOf one.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode get(String) return BigIntegerNode(BigInteger) with v is valueOf one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   *   <li>Then calls {@link JsonNode#iterator()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode get(String) return Instance; then calls iterator()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   *   <li>When Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode get(String) return Instance; when Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   *   <li>When Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode get(String) return Instance; when Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return {@code null}.</li>
   *   <li>Then return SourceRef is {@code As Text}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode get(String) return 'null'; then return SourceRef is 'As Text'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link JsonNode#isNull()} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode isNull() return 'false'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#size()} return three.</li>
   *   <li>Then calls {@link ArrayNode#size()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode size() return three; then calls size()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#size()} return three.</li>
   *   <li>Then calls {@link ArrayNode#size()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode size() return three; then calls size()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given Instance; when ArrayNode get(String) return Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given Instance; when ArrayNode get(String) return Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Then calls {@link JsonNode#iterator()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); then calls iterator()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Then return ConditionExpression is {@code 1}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); then return ConditionExpression is '1'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Then return ConditionExpression is {@code As Text}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); then return ConditionExpression is 'As Text'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Then return ConditionExpression is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); then return ConditionExpression is empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Then return ConditionExpression is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); then return ConditionExpression is empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Then return SourceRef is {@code 1}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); then return SourceRef is '1'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Then return SourceRef is {@code As Text}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); then return SourceRef is 'As Text'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Then return SourceRef is {@code As Text}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); then return SourceRef is 'As Text'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Then return SourceRef is {@code As Text}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); then return SourceRef is 'As Text'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return SourceRef is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); when Instance; then return SourceRef is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement SequenceFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}.
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setFieldConditionExpression(SequenceFlow, JsonNode)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SequenceFlowJsonConverter.setFieldConditionExpression(SequenceFlow, JsonNode)"})
  void testSetFieldConditionExpression() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    SequenceFlow flow = new SequenceFlow("Source Ref", "Target Ref");

    ArrayNode expressionNode = mock(ArrayNode.class);
    when(expressionNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    // Act
    sequenceFlowJsonConverter.setFieldConditionExpression(flow, expressionNode);

    // Assert
    verify(expressionNode, atLeast(1)).get(Mockito.<String>any());
    assertEquals("${  }", flow.getConditionExpression());
    Map<String, List<ExtensionElement>> extensionElements = flow.getExtensionElements();
    assertEquals(3, extensionElements.size());
    assertEquals(1, extensionElements.get("conditionFieldId").size());
    assertEquals(1, extensionElements.get("conditionOperator").size());
    assertEquals(1, extensionElements.get("conditionValue").size());
  }

  /**
   * Test {@link SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link JsonNode#isNull()} return {@code true}.</li>
   *   <li>Then calls {@link JsonNode#isNull()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setFieldConditionExpression(SequenceFlow, JsonNode); given ArrayNode isNull() return 'true'; then calls isNull()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SequenceFlowJsonConverter.setFieldConditionExpression(SequenceFlow, JsonNode)"})
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

    // Assert
    verify(arrayNode, atLeast(1)).isNull();
    verify(expressionNode, atLeast(1)).get(Mockito.<String>any());
  }

  /**
   * Test {@link SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setFieldConditionExpression(SequenceFlow, JsonNode); given ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SequenceFlowJsonConverter.setFieldConditionExpression(SequenceFlow, JsonNode)"})
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
   * Test {@link SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}.
   * <ul>
   *   <li>Given {@link BigIntegerNode#BigIntegerNode(BigInteger)} with v is valueOf one.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setFieldConditionExpression(SequenceFlow, JsonNode); given BigIntegerNode(BigInteger) with v is valueOf one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SequenceFlowJsonConverter.setFieldConditionExpression(SequenceFlow, JsonNode)"})
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
   * Test {@link SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>Then calls {@link BaseElement#addExtensionElement(ExtensionElement)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setFieldConditionExpression(SequenceFlow, JsonNode); given Instance; then calls addExtensionElement(ExtensionElement)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SequenceFlowJsonConverter.setFieldConditionExpression(SequenceFlow, JsonNode)"})
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
   * Test {@link SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setFieldConditionExpression(SequenceFlow, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SequenceFlowJsonConverter.setFieldConditionExpression(SequenceFlow, JsonNode)"})
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
   * Test {@link SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}.
   * <ul>
   *   <li>When Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#setFieldConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setFieldConditionExpression(SequenceFlow, JsonNode); when Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SequenceFlowJsonConverter.setFieldConditionExpression(SequenceFlow, JsonNode)"})
  void testSetFieldConditionExpression_whenInstance() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    SequenceFlow flow = new SequenceFlow("Source Ref", "Target Ref");

    // Act
    sequenceFlowJsonConverter.setFieldConditionExpression(flow, MissingNode.getInstance());

    // Assert that nothing has changed
    assertTrue(flow.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}.
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setOutcomeConditionExpression(SequenceFlow, JsonNode)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SequenceFlowJsonConverter.setOutcomeConditionExpression(SequenceFlow, JsonNode)"})
  void testSetOutcomeConditionExpression() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    SequenceFlow flow = new SequenceFlow("Source Ref", "Target Ref");

    ArrayNode expressionNode = mock(ArrayNode.class);
    when(expressionNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    // Act
    sequenceFlowJsonConverter.setOutcomeConditionExpression(flow, expressionNode);

    // Assert
    verify(expressionNode, atLeast(1)).get(Mockito.<String>any());
    assertEquals("${form0outcome  }", flow.getConditionExpression());
    Map<String, List<ExtensionElement>> extensionElements = flow.getExtensionElements();
    assertEquals(3, extensionElements.size());
    assertEquals(1, extensionElements.get("conditionFormId").size());
    assertEquals(1, extensionElements.get("conditionOperator").size());
    assertEquals(1, extensionElements.get("conditionOutcomeName").size());
  }

  /**
   * Test {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link JsonNode#isNull()} return {@code true}.</li>
   *   <li>Then calls {@link JsonNode#isNull()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setOutcomeConditionExpression(SequenceFlow, JsonNode); given ArrayNode isNull() return 'true'; then calls isNull()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SequenceFlowJsonConverter.setOutcomeConditionExpression(SequenceFlow, JsonNode)"})
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

    // Assert
    verify(arrayNode, atLeast(1)).isNull();
    verify(expressionNode, atLeast(1)).get(Mockito.<String>any());
  }

  /**
   * Test {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setOutcomeConditionExpression(SequenceFlow, JsonNode); given ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SequenceFlowJsonConverter.setOutcomeConditionExpression(SequenceFlow, JsonNode)"})
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
   * Test {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}.
   * <ul>
   *   <li>Given {@link BigIntegerNode#BigIntegerNode(BigInteger)} with v is valueOf one.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setOutcomeConditionExpression(SequenceFlow, JsonNode); given BigIntegerNode(BigInteger) with v is valueOf one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SequenceFlowJsonConverter.setOutcomeConditionExpression(SequenceFlow, JsonNode)"})
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
   * Test {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}.
   * <ul>
   *   <li>Given False.</li>
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return False.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setOutcomeConditionExpression(SequenceFlow, JsonNode); given False; when ArrayNode get(String) return False")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SequenceFlowJsonConverter.setOutcomeConditionExpression(SequenceFlow, JsonNode)"})
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
   * Test {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setOutcomeConditionExpression(SequenceFlow, JsonNode); given Instance; when ArrayNode get(String) return Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SequenceFlowJsonConverter.setOutcomeConditionExpression(SequenceFlow, JsonNode)"})
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
   * Test {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setOutcomeConditionExpression(SequenceFlow, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SequenceFlowJsonConverter.setOutcomeConditionExpression(SequenceFlow, JsonNode)"})
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
   * Test {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}.
   * <ul>
   *   <li>When Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#setOutcomeConditionExpression(SequenceFlow, JsonNode)}
   */
  @Test
  @DisplayName("Test setOutcomeConditionExpression(SequenceFlow, JsonNode); when Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SequenceFlowJsonConverter.setOutcomeConditionExpression(SequenceFlow, JsonNode)"})
  void testSetOutcomeConditionExpression_whenInstance() {
    // Arrange
    SequenceFlowJsonConverter sequenceFlowJsonConverter = new SequenceFlowJsonConverter();
    SequenceFlow flow = new SequenceFlow("Source Ref", "Target Ref");

    // Act
    sequenceFlowJsonConverter.setOutcomeConditionExpression(flow, MissingNode.getInstance());

    // Assert that nothing has changed
    assertTrue(flow.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link SequenceFlowJsonConverter#addExtensionElement(String, String, SequenceFlow)}.
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#addExtensionElement(String, String, SequenceFlow)}
   */
  @Test
  @DisplayName("Test addExtensionElement(String, String, SequenceFlow)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SequenceFlowJsonConverter.addExtensionElement(String, String, SequenceFlow)"})
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
   * <p>
   * Method under test: {@link SequenceFlowJsonConverter#addExtensionElement(String, String, SequenceFlow)}
   */
  @Test
  @DisplayName("Test addExtensionElement(String, String, SequenceFlow)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SequenceFlowJsonConverter.addExtensionElement(String, String, SequenceFlow)"})
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
   * <p>
   * Method under test: default or parameterless constructor of {@link SequenceFlowJsonConverter}
   */
  @Test
  @DisplayName("Test new SequenceFlowJsonConverter (default constructor)")
  @Tag("MaintainedByDiffblue")
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
