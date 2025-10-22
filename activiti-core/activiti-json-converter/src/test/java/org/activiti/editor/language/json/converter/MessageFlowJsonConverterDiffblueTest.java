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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BigIntegerNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.MissingNode;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.MessageFlow;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MessageFlowJsonConverterDiffblueTest {
  /**
   * Test {@link MessageFlowJsonConverter#fillJsonTypes(Map)}.
   * <p>
   * Method under test: {@link MessageFlowJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void MessageFlowJsonConverter.fillJsonTypes(Map)"})
  void testFillJsonTypes() {
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
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String MessageFlowJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();

    // Act and Assert
    assertEquals("MessageFlow", messageFlowJsonConverter.getStencilId(new ActivitiListener()));
  }

  /**
   * Test {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <p>
   * Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <p>
   * Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <p>
   * Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <p>
   * Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <p>
   * Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add Instance.</li>
   *   <li>Then calls {@link ArrayNode#size()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayList() add Instance; then calls size()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add Instance.</li>
   *   <li>Then return SourceRef is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayList() add Instance; then return SourceRef is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode get(String) return Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return {@code null}.</li>
   *   <li>Then return SourceRef is {@code As Text}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode get(String) return 'null'; then return SourceRef is 'As Text'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link JsonNode#isNull()} return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode isNull() return 'false'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#size()} return three.</li>
   *   <li>Then calls {@link ArrayNode#size()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode size() return three; then calls size()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link BigIntegerNode#BigIntegerNode(BigInteger)} with v is valueOf one.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given BigIntegerNode(BigInteger) with v is valueOf one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>Then return SourceRef is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given Instance; then return SourceRef is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>Then return SourceRef is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given Instance; then return SourceRef is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>When Instance.</li>
   *   <li>Then return SourceRef is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given Instance; when Instance; then return SourceRef is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Then return SourceRef is {@code 1}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); then return SourceRef is '1'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Then return SourceRef is {@code As Text}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); then return SourceRef is 'As Text'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Then return SourceRef is {@code As Text}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); then return SourceRef is 'As Text'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Test {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
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
   * Method under test: default or parameterless constructor of {@link MessageFlowJsonConverter}
   */
  @Test
  @DisplayName("Test new MessageFlowJsonConverter (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void MessageFlowJsonConverter.<init>()"})
  void testNewMessageFlowJsonConverter() {
    // Arrange and Act
    MessageFlowJsonConverter actualMessageFlowJsonConverter = new MessageFlowJsonConverter();

    // Assert
    assertNull(actualMessageFlowJsonConverter.shapesArrayNode);
    assertNull(actualMessageFlowJsonConverter.flowElementNode);
    assertNull(actualMessageFlowJsonConverter.model);
    assertNull(actualMessageFlowJsonConverter.processor);
    assertEquals(0.0d, actualMessageFlowJsonConverter.subProcessX);
    assertEquals(0.0d, actualMessageFlowJsonConverter.subProcessY);
  }
}
