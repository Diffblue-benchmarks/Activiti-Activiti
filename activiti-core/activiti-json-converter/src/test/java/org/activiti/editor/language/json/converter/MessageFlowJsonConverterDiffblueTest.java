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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BinaryNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.MissingNode;
import java.io.UnsupportedEncodingException;
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
   *
   * <p>Method under test: {@link MessageFlowJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
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
   *
   * <p>Method under test: {@link MessageFlowJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String MessageFlowJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();

    // Act and Assert
    assertEquals("MessageFlow", messageFlowJsonConverter.getStencilId(new ActivitiListener()));
  }

  /**
   * Test {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <p>Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayNode arrayNode = mock(ArrayNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());

    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    BaseElement actualConvertJsonToElementResult =
        messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode2).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(elementNode).get("resourceId");
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
   *
   * <p>Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement2() throws UnsupportedEncodingException {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));
    when(arrayNode.asText()).thenReturn("QVhBWEFYQVg=");

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any()))
        .thenReturn(new BinaryNode("AXAXAXAX".getBytes("UTF-8")));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));
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
    BaseElement actualConvertJsonToElementResult =
        messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode5).iterator();
    verify(arrayNode3).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(arrayNode3).get("overrideid");
    verify(arrayNode).get("resourceId");
    verify(arrayNode2).get("resourceId");
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
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayList() add valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenArrayListAddValueOfTen() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(DoubleNode.valueOf(10.0d));

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());

    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    BaseElement actualConvertJsonToElementResult =
        messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode).iterator();
    verify(modelNode).get("childShapes");
    verify(elementNode).get("resourceId");
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
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add valueOf ten.
   *   <li>Then calls {@link ArrayNode#size()}.
   * </ul>
   *
   * <p>Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayList() add valueOf ten; then calls size()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenArrayListAddValueOfTen_thenCallsSize() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(DoubleNode.valueOf(10.0d));

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
    BaseElement actualConvertJsonToElementResult =
        messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode3).iterator();
    verify(arrayNode, atLeast(1)).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(elementNode).get("resourceId");
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
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#asText()} return {@code As Text}.
   *   <li>Then calls {@link ArrayNode#asText()}.
   * </ul>
   *
   * <p>Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode asText() return 'As Text'; then calls asText()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenArrayNodeAsTextReturnAsText_thenCallsAsText()
      throws UnsupportedEncodingException {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any()))
        .thenReturn(new BinaryNode("AXAXAXAX".getBytes("UTF-8")));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);

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
    BaseElement actualConvertJsonToElementResult =
        messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode5).iterator();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(elementNode).get("resourceId");
    verify(arrayNode2).get("resourceId");
    verify(arrayNode3).size();
    verify(arrayNode).asText();
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
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.
   * </ul>
   *
   * <p>Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode get(String) return Instance")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenArrayNodeGetReturnInstance()
      throws UnsupportedEncodingException {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode.asText()).thenReturn("QVhBWEFYQVg=");

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any()))
        .thenReturn(new BinaryNode("AXAXAXAX".getBytes("UTF-8")));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));
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
    BaseElement actualConvertJsonToElementResult =
        messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode5).iterator();
    verify(arrayNode3).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(arrayNode3, atLeast(1)).get("overrideid");
    verify(arrayNode).get("resourceId");
    verify(arrayNode2).get("resourceId");
    verify(arrayNode3).size();
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof MessageFlow);
    assertEquals("10.0", ((MessageFlow) actualConvertJsonToElementResult).getSourceRef());
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
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.
   *   <li>Then calls {@link ArrayNode#asText()}.
   * </ul>
   *
   * <p>Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode get(String) return Instance; then calls asText()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenArrayNodeGetReturnInstance_thenCallsAsText() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);

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
    BaseElement actualConvertJsonToElementResult =
        messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode5).iterator();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(elementNode).get("resourceId");
    verify(arrayNode2).get("resourceId");
    verify(arrayNode3).size();
    verify(arrayNode).asText();
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
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return {@code null}.
   *   <li>Then return SourceRef is {@code As Text}.
   * </ul>
   *
   * <p>Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode get(String) return 'null'; then return SourceRef is 'As Text'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenArrayNodeGetReturnNull_thenReturnSourceRefIsAsText()
      throws UnsupportedEncodingException {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));
    when(arrayNode.asText()).thenReturn("QVhBWEFYQVg=");

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any()))
        .thenReturn(new BinaryNode("AXAXAXAX".getBytes("UTF-8")));

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
    BaseElement actualConvertJsonToElementResult =
        messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode5).iterator();
    verify(arrayNode3).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(arrayNode3).get("overrideid");
    verify(arrayNode).get("resourceId");
    verify(arrayNode2).get("resourceId");
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
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#isNull()} return {@code true}.
   *   <li>Then return SourceRef is {@code 10.0}.
   * </ul>
   *
   * <p>Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode isNull() return 'true'; then return SourceRef is '10.0'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenArrayNodeIsNullReturnTrue_thenReturnSourceRefIs100()
      throws UnsupportedEncodingException {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("QVhBWEFYQVg=");

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any()))
        .thenReturn(new BinaryNode("AXAXAXAX".getBytes("UTF-8")));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));
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
    BaseElement actualConvertJsonToElementResult =
        messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode5).iterator();
    verify(arrayNode3).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(arrayNode3, atLeast(1)).get("overrideid");
    verify(arrayNode2).get("resourceId");
    verify(arrayNode3).size();
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof MessageFlow);
    assertEquals("10.0", ((MessageFlow) actualConvertJsonToElementResult).getSourceRef());
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
   *
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayNode modelNode = mock(ArrayNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(modelNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));

    // Act
    BaseElement actualConvertJsonToElementResult =
        messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(modelNode).get("childShapes");
    verify(elementNode).get("resourceId");
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
   *
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenArrayNodeWithNfIsWithExactBigDecimalsTrue2() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();

    ArrayNode elementNode = mock(ArrayNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(elementNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());
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
    BaseElement actualConvertJsonToElementResult =
        messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode4).iterator();
    verify(arrayNode2, atLeast(1)).iterator();
    verify(arrayNode3, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(elementNode).get("resourceId");
    verify(arrayNode).get("resourceId");
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
   *
   * <ul>
   *   <li>Given {@link BinaryNode#BinaryNode(byte[])} with data is {@code AXAXAXAX} Bytes is {@code
   *       UTF-8}.
   * </ul>
   *
   * <p>Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given BinaryNode(byte[]) with data is 'AXAXAXAX' Bytes is 'UTF-8'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenBinaryNodeWithDataIsAxaxaxaxBytesIsUtf8()
      throws UnsupportedEncodingException {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any()))
        .thenReturn(new BinaryNode("AXAXAXAX".getBytes("UTF-8")));
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);

    // Act
    BaseElement actualConvertJsonToElementResult =
        messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(elementNode).get("resourceId");
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
   *
   * <ul>
   *   <li>Given Instance.
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.
   * </ul>
   *
   * <p>Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given Instance; when ArrayNode get(String) return Instance")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenInstance_whenArrayNodeGetReturnInstance() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);

    // Act
    BaseElement actualConvertJsonToElementResult =
        messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(elementNode).get("resourceId");
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
   *
   * <ul>
   *   <li>Given valueOf ten.
   *   <li>Then calls {@link ArrayNode#size()}.
   * </ul>
   *
   * <p>Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given valueOf ten; then calls size()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenValueOfTen_thenCallsSize() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

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
    BaseElement actualConvertJsonToElementResult =
        messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode3).iterator();
    verify(arrayNode, atLeast(1)).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(elementNode).get("resourceId");
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
   *
   * <ul>
   *   <li>Given valueOf ten.
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given valueOf ten; when ArrayNode get(String) return valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenValueOfTen_whenArrayNodeGetReturnValueOfTen() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    // Act
    BaseElement actualConvertJsonToElementResult =
        messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(modelNode).get("childShapes");
    verify(elementNode).get("resourceId");
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
   *
   * <ul>
   *   <li>Given valueOf ten.
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given valueOf ten; when ArrayNode get(String) return valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenValueOfTen_whenArrayNodeGetReturnValueOfTen2() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());

    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    BaseElement actualConvertJsonToElementResult =
        messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode).iterator();
    verify(modelNode).get("childShapes");
    verify(elementNode).get("resourceId");
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
   *
   * <ul>
   *   <li>Given valueOf ten.
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given valueOf ten; when ArrayNode get(String) return valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenValueOfTen_whenArrayNodeGetReturnValueOfTen3() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());

    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    BaseElement actualConvertJsonToElementResult =
        messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode2).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(elementNode).get("resourceId");
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
   *
   * <ul>
   *   <li>Given valueOf ten.
   *   <li>When valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given valueOf ten; when valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenValueOfTen_whenValueOfTen() {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);

    // Act
    BaseElement actualConvertJsonToElementResult =
        messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(elementNode).get("resourceId");
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
   *
   * <ul>
   *   <li>Then return SourceRef is {@code 10.0}.
   * </ul>
   *
   * <p>Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); then return SourceRef is '10.0'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_thenReturnSourceRefIs100() throws UnsupportedEncodingException {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));
    when(arrayNode.asText()).thenReturn("QVhBWEFYQVg=");

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any()))
        .thenReturn(new BinaryNode("AXAXAXAX".getBytes("UTF-8")));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));
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
    BaseElement actualConvertJsonToElementResult =
        messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode5).iterator();
    verify(arrayNode3).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(arrayNode3, atLeast(1)).get("overrideid");
    verify(arrayNode).get("resourceId");
    verify(arrayNode2).get("resourceId");
    verify(arrayNode3).size();
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof MessageFlow);
    assertEquals("10.0", ((MessageFlow) actualConvertJsonToElementResult).getSourceRef());
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
   *
   * <ul>
   *   <li>Then return SourceRef is {@code 10.0}.
   * </ul>
   *
   * <p>Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); then return SourceRef is '10.0'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_thenReturnSourceRefIs1002() throws UnsupportedEncodingException {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.get(Mockito.<String>any()))
        .thenReturn(new BinaryNode("AXAXAXAX".getBytes("UTF-8")));
    when(arrayNode.asText()).thenReturn("QVhBWEFYQVg=");

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any()))
        .thenReturn(new BinaryNode("AXAXAXAX".getBytes("UTF-8")));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));
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
    BaseElement actualConvertJsonToElementResult =
        messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode5).iterator();
    verify(arrayNode3).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(arrayNode3, atLeast(1)).get("overrideid");
    verify(arrayNode).get("resourceId");
    verify(arrayNode2).get("resourceId");
    verify(arrayNode3).size();
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof MessageFlow);
    assertEquals("10.0", ((MessageFlow) actualConvertJsonToElementResult).getSourceRef());
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
   *
   * <ul>
   *   <li>Then return SourceRef is {@code As Text}.
   * </ul>
   *
   * <p>Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); then return SourceRef is 'As Text'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_thenReturnSourceRefIsAsText() throws UnsupportedEncodingException {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));
    when(arrayNode.asText()).thenReturn("QVhBWEFYQVg=");

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any()))
        .thenReturn(new BinaryNode("AXAXAXAX".getBytes("UTF-8")));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);

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
    BaseElement actualConvertJsonToElementResult =
        messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode3).isNull();
    verify(arrayNode6).iterator();
    verify(arrayNode4).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode5, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(arrayNode4).get("overrideid");
    verify(arrayNode).get("resourceId");
    verify(arrayNode2).get("resourceId");
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
   *
   * <ul>
   *   <li>Then return SourceRef is {@code QVhBWEFYQVg=}.
   * </ul>
   *
   * <p>Method under test: {@link MessageFlowJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); then return SourceRef is 'QVhBWEFYQVg='")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement MessageFlowJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_thenReturnSourceRefIsQVhBWEFYQVg()
      throws UnsupportedEncodingException {
    // Arrange
    MessageFlowJsonConverter messageFlowJsonConverter = new MessageFlowJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));
    when(arrayNode.asText()).thenReturn("QVhBWEFYQVg=");

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any()))
        .thenReturn(new BinaryNode("AXAXAXAX".getBytes("UTF-8")));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any()))
        .thenReturn(new BinaryNode("AXAXAXAX".getBytes("UTF-8")));
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
    BaseElement actualConvertJsonToElementResult =
        messageFlowJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode5).iterator();
    verify(arrayNode3).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(arrayNode3, atLeast(1)).get("overrideid");
    verify(arrayNode).get("resourceId");
    verify(arrayNode2).get("resourceId");
    verify(arrayNode3).size();
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof MessageFlow);
    assertEquals("QVhBWEFYQVg=", ((MessageFlow) actualConvertJsonToElementResult).getSourceRef());
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
   * Test new {@link MessageFlowJsonConverter} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link MessageFlowJsonConverter}
   */
  @Test
  @DisplayName("Test new MessageFlowJsonConverter (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
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
