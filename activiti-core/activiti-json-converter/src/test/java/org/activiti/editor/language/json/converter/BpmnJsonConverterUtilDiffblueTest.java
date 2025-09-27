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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BigIntegerNode;
import com.fasterxml.jackson.databind.node.BinaryNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BooleanDataObject;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EventListener;
import org.activiti.bpmn.model.ItemDefinition;
import org.activiti.bpmn.model.Message;
import org.activiti.bpmn.model.Message.Builder;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.Signal;
import org.activiti.bpmn.model.ValuedDataObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BpmnJsonConverterUtilDiffblueTest {
  /**
   * Test {@link BpmnJsonConverterUtil#createChildShape(String, String, double, double, double,
   * double)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then iterator next return {@link NullNode}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#createChildShape(String, String, double,
   * double, double, double)}
   */
  @Test
  @DisplayName(
      "Test createChildShape(String, String, double, double, double, double); when 'null'; then iterator next return NullNode")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ObjectNode BpmnJsonConverterUtil.createChildShape(String, String, double, double, double, double)"
  })
  void testCreateChildShape_whenNull_thenIteratorNextReturnNullNode() {
    // Arrange and Act
    ObjectNode actualCreateChildShapeResult =
        BpmnJsonConverterUtil.createChildShape(null, null, 10.0d, 10.0d, 10.0d, 10.0d);

    // Assert
    Iterator<JsonNode> iteratorResult = actualCreateChildShapeResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    JsonNode nextResult2 = iteratorResult.next();
    JsonNode nextResult3 = iteratorResult.next();
    JsonNode nextResult4 = iteratorResult.next();
    boolean actualHasNextResult = iteratorResult.hasNext();
    assertTrue(nextResult3 instanceof ArrayNode);
    assertTrue(nextResult2 instanceof NullNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertTrue(nextResult4 instanceof ObjectNode);
    assertTrue(actualCreateChildShapeResult.traverse() instanceof TreeTraversingParser);
    assertEquals(
        "{\n"
            + "  \"bounds\" : {\n"
            + "    \"lowerRight\" : {\n"
            + "      \"x\" : 10.0,\n"
            + "      \"y\" : 10.0\n"
            + "    },\n"
            + "    \"upperLeft\" : {\n"
            + "      \"x\" : 10.0,\n"
            + "      \"y\" : 10.0\n"
            + "    }\n"
            + "  },\n"
            + "  \"resourceId\" : null,\n"
            + "  \"childShapes\" : [ ],\n"
            + "  \"stencil\" : {\n"
            + "    \"id\" : null\n"
            + "  }\n"
            + "}",
        actualCreateChildShapeResult.toPrettyString());
    assertFalse(actualHasNextResult);
  }

  /**
   * Test {@link BpmnJsonConverterUtil#createResourceNode(String)}.
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#createResourceNode(String)}
   */
  @Test
  @DisplayName("Test createResourceNode(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode BpmnJsonConverterUtil.createResourceNode(String)"})
  void testCreateResourceNode() {
    // Arrange and Act
    ObjectNode actualCreateResourceNodeResult = BpmnJsonConverterUtil.createResourceNode("");

    // Assert
    Iterator<JsonNode> iteratorResult = actualCreateResourceNodeResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof TextNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualCreateResourceNodeResult.traverse() instanceof TreeTraversingParser);
    assertEquals("\"\"", nextResult.toPrettyString());
    assertEquals("{\n  \"resourceId\" : \"\"\n}", actualCreateResourceNodeResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#createResourceNode(String)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return iterator next toPrettyString is {@code "42"}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#createResourceNode(String)}
   */
  @Test
  @DisplayName(
      "Test createResourceNode(String); when '42'; then return iterator next toPrettyString is '\"42\"'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode BpmnJsonConverterUtil.createResourceNode(String)"})
  void testCreateResourceNode_when42_thenReturnIteratorNextToPrettyStringIs42() {
    // Arrange and Act
    ObjectNode actualCreateResourceNodeResult = BpmnJsonConverterUtil.createResourceNode("42");

    // Assert
    Iterator<JsonNode> iteratorResult = actualCreateResourceNodeResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof TextNode);
    assertTrue(nextResult.traverse() instanceof TreeTraversingParser);
    assertTrue(actualCreateResourceNodeResult.traverse() instanceof TreeTraversingParser);
    assertEquals("\"42\"", nextResult.toPrettyString());
    assertEquals(
        "{\n  \"resourceId\" : \"42\"\n}", actualCreateResourceNodeResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#createResourceNode(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then iterator next return {@link NullNode}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#createResourceNode(String)}
   */
  @Test
  @DisplayName("Test createResourceNode(String); when 'null'; then iterator next return NullNode")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ObjectNode BpmnJsonConverterUtil.createResourceNode(String)"})
  void testCreateResourceNode_whenNull_thenIteratorNextReturnNullNode() {
    // Arrange and Act
    ObjectNode actualCreateResourceNodeResult = BpmnJsonConverterUtil.createResourceNode(null);

    // Assert
    Iterator<JsonNode> iteratorResult = actualCreateResourceNodeResult.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof NullNode);
    assertTrue(actualCreateResourceNodeResult.traverse() instanceof TreeTraversingParser);
    assertEquals("null", nextResult.toPrettyString());
    assertEquals("{\n  \"resourceId\" : null\n}", actualCreateResourceNodeResult.toPrettyString());
    assertEquals(JsonNodeType.NULL, nextResult.getNodeType());
    assertFalse(nextResult.isTextual());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult.isNull());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getStencilId(JsonNode)}.
   *
   * <ul>
   *   <li>When valueOf ten.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#getStencilId(JsonNode)}
   */
  @Test
  @DisplayName("Test getStencilId(JsonNode); when valueOf ten; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String BpmnJsonConverterUtil.getStencilId(JsonNode)"})
  void testGetStencilId_whenValueOfTen_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(BpmnJsonConverterUtil.getStencilId(DoubleNode.valueOf(10.0d)));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getElementId(JsonNode)}.
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#getElementId(JsonNode)}
   */
  @Test
  @DisplayName("Test getElementId(JsonNode)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String BpmnJsonConverterUtil.getElementId(JsonNode)"})
  void testGetElementId() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    String actualElementId = BpmnJsonConverterUtil.getElementId(objectNode);

    // Assert
    verify(objectNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode).get("overrideid");
    verify(arrayNode).asText();
    assertEquals("As Text", actualElementId);
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getElementId(JsonNode)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return {@link
   *       BinaryNode#BinaryNode(byte[])} with data is {@code AXAXAXAX} Bytes is {@code UTF-8}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#getElementId(JsonNode)}
   */
  @Test
  @DisplayName(
      "Test getElementId(JsonNode); given ArrayNode get(String) return BinaryNode(byte[]) with data is 'AXAXAXAX' Bytes is 'UTF-8'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String BpmnJsonConverterUtil.getElementId(JsonNode)"})
  void testGetElementId_givenArrayNodeGetReturnBinaryNodeWithDataIsAxaxaxaxBytesIsUtf8()
      throws UnsupportedEncodingException {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any()))
        .thenReturn(new BinaryNode("AXAXAXAX".getBytes("UTF-8")));

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    String actualElementId = BpmnJsonConverterUtil.getElementId(objectNode);

    // Assert
    verify(arrayNode, atLeast(1)).get("overrideid");
    verify(objectNode, atLeast(1)).get("properties");
    assertEquals("QVhBWEFYQVg=", actualElementId);
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getElementId(JsonNode)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.
   *   <li>Then return {@code As Text}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#getElementId(JsonNode)}
   */
  @Test
  @DisplayName(
      "Test getElementId(JsonNode); given ArrayNode get(String) return Instance; then return 'As Text'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String BpmnJsonConverterUtil.getElementId(JsonNode)"})
  void testGetElementId_givenArrayNodeGetReturnInstance_thenReturnAsText() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    String actualElementId = BpmnJsonConverterUtil.getElementId(objectNode);

    // Assert
    verify(objectNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode).get("overrideid");
    verify(arrayNode).asText();
    assertEquals("As Text", actualElementId);
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getElementId(JsonNode)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.
   *   <li>Then return {@code As Text}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#getElementId(JsonNode)}
   */
  @Test
  @DisplayName(
      "Test getElementId(JsonNode); given ArrayNode get(String) return Instance; then return 'As Text'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String BpmnJsonConverterUtil.getElementId(JsonNode)"})
  void testGetElementId_givenArrayNodeGetReturnInstance_thenReturnAsText2() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(NullNode.getInstance());
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    String actualElementId = BpmnJsonConverterUtil.getElementId(objectNode);

    // Assert
    verify(objectNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode).get("overrideid");
    verify(arrayNode).asText();
    assertEquals("As Text", actualElementId);
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getElementId(JsonNode)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return valueOf ten.
   *   <li>Then return {@code 10.0}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#getElementId(JsonNode)}
   */
  @Test
  @DisplayName(
      "Test getElementId(JsonNode); given ArrayNode get(String) return valueOf ten; then return '10.0'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String BpmnJsonConverterUtil.getElementId(JsonNode)"})
  void testGetElementId_givenArrayNodeGetReturnValueOfTen_thenReturn100() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    String actualElementId = BpmnJsonConverterUtil.getElementId(objectNode);

    // Assert
    verify(arrayNode, atLeast(1)).get("overrideid");
    verify(objectNode, atLeast(1)).get("properties");
    assertEquals("10.0", actualElementId);
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getElementId(JsonNode)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#isNull()} return {@code false}.
   *   <li>Then calls {@link ArrayNode#isNull()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#getElementId(JsonNode)}
   */
  @Test
  @DisplayName(
      "Test getElementId(JsonNode); given ArrayNode isNull() return 'false'; then calls isNull()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String BpmnJsonConverterUtil.getElementId(JsonNode)"})
  void testGetElementId_givenArrayNodeIsNullReturnFalse_thenCallsIsNull() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    String actualElementId = BpmnJsonConverterUtil.getElementId(objectNode);

    // Assert
    verify(arrayNode, atLeast(1)).isNull();
    verify(arrayNode2, atLeast(1)).get("overrideid");
    verify(objectNode, atLeast(1)).get("properties");
    verify(arrayNode, atLeast(1)).asText();
    assertEquals("As Text", actualElementId);
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getElementId(JsonNode)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#isNull()} return {@code true}.
   *   <li>Then calls {@link ArrayNode#isNull()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#getElementId(JsonNode)}
   */
  @Test
  @DisplayName(
      "Test getElementId(JsonNode); given ArrayNode isNull() return 'true'; then calls isNull()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String BpmnJsonConverterUtil.getElementId(JsonNode)"})
  void testGetElementId_givenArrayNodeIsNullReturnTrue_thenCallsIsNull() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.asText()).thenReturn("As Text");

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    String actualElementId = BpmnJsonConverterUtil.getElementId(objectNode);

    // Assert
    verify(arrayNode).isNull();
    verify(objectNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2).get("overrideid");
    verify(arrayNode2).asText();
    assertEquals("As Text", actualElementId);
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getElementId(JsonNode)}.
   *
   * <ul>
   *   <li>Given {@link BinaryNode#BinaryNode(byte[])} with data is {@code AXAXAXAX} Bytes is {@code
   *       UTF-8}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#getElementId(JsonNode)}
   */
  @Test
  @DisplayName(
      "Test getElementId(JsonNode); given BinaryNode(byte[]) with data is 'AXAXAXAX' Bytes is 'UTF-8'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String BpmnJsonConverterUtil.getElementId(JsonNode)"})
  void testGetElementId_givenBinaryNodeWithDataIsAxaxaxaxBytesIsUtf8()
      throws UnsupportedEncodingException {
    // Arrange
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any()))
        .thenReturn(new BinaryNode("AXAXAXAX".getBytes("UTF-8")));

    // Act
    String actualElementId = BpmnJsonConverterUtil.getElementId(objectNode);

    // Assert
    verify(objectNode, atLeast(1)).get(Mockito.<String>any());
    assertEquals("QVhBWEFYQVg=", actualElementId);
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getElementId(JsonNode)}.
   *
   * <ul>
   *   <li>Given valueOf ten.
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return valueOf ten.
   *   <li>Then return {@code 10.0}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#getElementId(JsonNode)}
   */
  @Test
  @DisplayName(
      "Test getElementId(JsonNode); given valueOf ten; when ArrayNode get(String) return valueOf ten; then return '10.0'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String BpmnJsonConverterUtil.getElementId(JsonNode)"})
  void testGetElementId_givenValueOfTen_whenArrayNodeGetReturnValueOfTen_thenReturn100() {
    // Arrange
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    // Act
    String actualElementId = BpmnJsonConverterUtil.getElementId(objectNode);

    // Assert
    verify(objectNode, atLeast(1)).get(Mockito.<String>any());
    assertEquals("10.0", actualElementId);
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertMessagesToJson(BpmnModel, ObjectNode)} with {@code
   * bpmnModel}, {@code propertiesNode}.
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertMessagesToJson(BpmnModel,
   * ObjectNode)}
   */
  @Test
  @DisplayName(
      "Test convertMessagesToJson(BpmnModel, ObjectNode) with 'bpmnModel', 'propertiesNode'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertMessagesToJson(BpmnModel, ObjectNode)"})
  void testConvertMessagesToJsonWithBpmnModelPropertiesNode() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode propertiesNode = new ObjectNode(nc);

    // Act
    BpmnJsonConverterUtil.convertMessagesToJson(bpmnModel, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ArrayNode);
    assertEquals("[ ]", nextResult.toPrettyString());
    assertEquals("{\n  \"messagedefinitions\" : [ ]\n}", propertiesNode.toPrettyString());
    assertEquals(0, nextResult.size());
    assertFalse(nextResult.elements().hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(nextResult.iterator().hasNext());
    assertTrue(nextResult.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertMessagesToJson(Collection, ObjectNode)} with {@code
   * messages}, {@code propertiesNode}.
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertMessagesToJson(Collection,
   * ObjectNode)}
   */
  @Test
  @DisplayName(
      "Test convertMessagesToJson(Collection, ObjectNode) with 'messages', 'propertiesNode'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertMessagesToJson(Collection, ObjectNode)"})
  void testConvertMessagesToJsonWithMessagesPropertiesNode() {
    // Arrange
    ArrayList<Message> messages = new ArrayList<>();
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode propertiesNode = new ObjectNode(nc);

    // Act
    BpmnJsonConverterUtil.convertMessagesToJson(messages, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ArrayNode);
    assertEquals("[ ]", nextResult.toPrettyString());
    assertEquals("{\n  \"messages\" : [ ]\n}", propertiesNode.toPrettyString());
    assertEquals(0, nextResult.size());
    assertFalse(nextResult.elements().hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(nextResult.iterator().hasNext());
    assertTrue(nextResult.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertMessagesToJson(Collection, ObjectNode)} with {@code
   * messages}, {@code propertiesNode}.
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertMessagesToJson(Collection,
   * ObjectNode)}
   */
  @Test
  @DisplayName(
      "Test convertMessagesToJson(Collection, ObjectNode) with 'messages', 'propertiesNode'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertMessagesToJson(Collection, ObjectNode)"})
  void testConvertMessagesToJsonWithMessagesPropertiesNode2() {
    // Arrange
    LinkedHashSet<Message> messages = new LinkedHashSet<>();
    Message message = new Message(null, null, null);
    messages.add(message);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode propertiesNode = new ObjectNode(nc);
    propertiesNode.put("messages", DoubleNode.valueOf(10.0d));

    // Act
    BpmnJsonConverterUtil.convertMessagesToJson(messages, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    assertTrue(iteratorResult.next() instanceof ArrayNode);
    assertEquals(
        "{\n"
            + "  \"messages\" : [ {\n"
            + "    \"message_id\" : null,\n"
            + "    \"message_name\" : null,\n"
            + "    \"message_item_ref\" : null\n"
            + "  } ]\n"
            + "}",
        propertiesNode.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertMessagesToJson(Collection, ObjectNode)} with {@code
   * messages}, {@code propertiesNode}.
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertMessagesToJson(Collection,
   * ObjectNode)}
   */
  @Test
  @DisplayName(
      "Test convertMessagesToJson(Collection, ObjectNode) with 'messages', 'propertiesNode'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertMessagesToJson(Collection, ObjectNode)"})
  void testConvertMessagesToJsonWithMessagesPropertiesNode3() {
    // Arrange
    LinkedHashSet<Message> messages = new LinkedHashSet<>();
    Message message = new Message(null, null, "Item Ref");
    messages.add(message);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode propertiesNode = new ObjectNode(nc);
    propertiesNode.put("messages", DoubleNode.valueOf(10.0d));

    // Act
    BpmnJsonConverterUtil.convertMessagesToJson(messages, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    assertTrue(iteratorResult.next() instanceof ArrayNode);
    assertEquals(
        "{\n"
            + "  \"messages\" : [ {\n"
            + "    \"message_id\" : null,\n"
            + "    \"message_name\" : null,\n"
            + "    \"message_item_ref\" : \"Item Ref\"\n"
            + "  } ]\n"
            + "}",
        propertiesNode.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertMessagesToJson(Collection, ObjectNode)} with {@code
   * messages}, {@code propertiesNode}.
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertMessagesToJson(Collection,
   * ObjectNode)}
   */
  @Test
  @DisplayName(
      "Test convertMessagesToJson(Collection, ObjectNode) with 'messages', 'propertiesNode'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertMessagesToJson(Collection, ObjectNode)"})
  void testConvertMessagesToJsonWithMessagesPropertiesNode4() {
    // Arrange
    ArrayList<Message> messages = new ArrayList<>();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    messages.add(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode propertiesNode = new ObjectNode(nc);

    // Act
    BpmnJsonConverterUtil.convertMessagesToJson(messages, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ArrayNode);
    Iterator<JsonNode> elementsResult = nextResult.elements();
    JsonNode nextResult2 = elementsResult.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult2.iterator();
    JsonNode nextResult3 = iteratorResult2.next();
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals(
        "[ {\n  \"message_id\" : \"\",\n  \"message_name\" : \"Name\",\n  \"message_item_ref\" : \"Item Ref\"\n} ]",
        nextResult.toPrettyString());
    assertEquals("\"\"", nextResult3.toPrettyString());
    assertEquals(
        "{\n  \"message_id\" : \"\",\n  \"message_name\" : \"Name\",\n  \"message_item_ref\" : \"Item Ref\"\n}",
        nextResult2.toPrettyString());
    assertEquals(
        "{\n"
            + "  \"messages\" : [ {\n"
            + "    \"message_id\" : \"\",\n"
            + "    \"message_name\" : \"Name\",\n"
            + "    \"message_item_ref\" : \"Item Ref\"\n"
            + "  } ]\n"
            + "}",
        propertiesNode.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertTrue(iteratorResult2.hasNext());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertListenersToJson(List, boolean, ObjectNode)}.
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertListenersToJson(List, boolean,
   * ObjectNode)}
   */
  @Test
  @DisplayName("Test convertListenersToJson(List, boolean, ObjectNode)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverterUtil.convertListenersToJson(List, boolean, ObjectNode)"
  })
  void testConvertListenersToJson() {
    // Arrange
    ArrayList<ActivitiListener> listeners = new ArrayList<>();
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode propertiesNode = new ObjectNode(nc);

    // Act
    BpmnJsonConverterUtil.convertListenersToJson(listeners, true, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ArrayNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals("[ ]", nextResult2.toPrettyString());
    assertEquals("{\n  \"executionListeners\" : [ ]\n}", nextResult.toPrettyString());
    assertEquals(
        "{\n  \"executionlisteners\" : {\n    \"executionListeners\" : [ ]\n  }\n}",
        propertiesNode.toPrettyString());
    assertEquals(0, nextResult2.size());
    assertFalse(nextResult2.elements().hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(nextResult2.iterator().hasNext());
    assertTrue(nextResult2.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertEventListenersToJson(List, ObjectNode)}.
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertEventListenersToJson(List,
   * ObjectNode)}
   */
  @Test
  @DisplayName("Test convertEventListenersToJson(List, ObjectNode)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertEventListenersToJson(List, ObjectNode)"})
  void testConvertEventListenersToJson() {
    // Arrange
    ArrayList<EventListener> listeners = new ArrayList<>();
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode propertiesNode = new ObjectNode(nc);

    // Act
    BpmnJsonConverterUtil.convertEventListenersToJson(listeners, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ArrayNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals("[ ]", nextResult2.toPrettyString());
    assertEquals("{\n  \"eventListeners\" : [ ]\n}", nextResult.toPrettyString());
    assertEquals(
        "{\n  \"eventlisteners\" : {\n    \"eventListeners\" : [ ]\n  }\n}",
        propertiesNode.toPrettyString());
    assertEquals(0, nextResult2.size());
    assertFalse(nextResult2.elements().hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(nextResult2.iterator().hasNext());
    assertTrue(nextResult2.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertEventListenersToJson(List, ObjectNode)}.
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertEventListenersToJson(List,
   * ObjectNode)}
   */
  @Test
  @DisplayName("Test convertEventListenersToJson(List, ObjectNode)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertEventListenersToJson(List, ObjectNode)"})
  void testConvertEventListenersToJson2() {
    // Arrange
    ArrayList<EventListener> listeners = new ArrayList<>();
    listeners.add(new EventListener());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode propertiesNode = new ObjectNode(nc);

    // Act
    BpmnJsonConverterUtil.convertEventListenersToJson(listeners, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ArrayNode);
    Iterator<JsonNode> elementsResult = nextResult2.elements();
    JsonNode nextResult3 = elementsResult.next();
    assertTrue(nextResult3 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals("[ { } ]", nextResult2.toPrettyString());
    assertEquals("{ }", nextResult3.toPrettyString());
    assertEquals("{\n  \"eventListeners\" : [ { } ]\n}", nextResult.toPrettyString());
    assertEquals(
        "{\n  \"eventlisteners\" : {\n    \"eventListeners\" : [ { } ]\n  }\n}",
        propertiesNode.toPrettyString());
    assertEquals(0, nextResult3.size());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(nextResult3.iterator().hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertTrue(nextResult3.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertEventListenersToJson(List, ObjectNode)}.
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertEventListenersToJson(List,
   * ObjectNode)}
   */
  @Test
  @DisplayName("Test convertEventListenersToJson(List, ObjectNode)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertEventListenersToJson(List, ObjectNode)"})
  void testConvertEventListenersToJson3() {
    // Arrange
    EventListener eventListener = new EventListener();
    eventListener.setEvents(",event");
    eventListener.setImplementation("not empty");
    eventListener.setEntityType("not empty");

    ArrayList<EventListener> listeners = new ArrayList<>();
    listeners.add(eventListener);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode propertiesNode = new ObjectNode(nc);

    // Act
    BpmnJsonConverterUtil.convertEventListenersToJson(listeners, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ArrayNode);
    Iterator<JsonNode> elementsResult = nextResult2.elements();
    JsonNode nextResult3 = elementsResult.next();
    assertTrue(nextResult3 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals(
        "[ {\n"
            + "  \"event\" : \",event\",\n"
            + "  \"events\" : [ {\n"
            + "    \"event\" : \"event\"\n"
            + "  } ],\n"
            + "  \"entityType\" : \"not empty\"\n"
            + "} ]",
        nextResult2.toPrettyString());
    assertEquals(
        "{\n"
            + "  \"eventListeners\" : [ {\n"
            + "    \"event\" : \",event\",\n"
            + "    \"events\" : [ {\n"
            + "      \"event\" : \"event\"\n"
            + "    } ],\n"
            + "    \"entityType\" : \"not empty\"\n"
            + "  } ]\n"
            + "}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"event\" : \",event\",\n  \"events\" : [ {\n    \"event\" : \"event\"\n  } ],\n  \"entityType\" : \"not empty\"\n}",
        nextResult3.toPrettyString());
    assertEquals(
        "{\n"
            + "  \"eventlisteners\" : {\n"
            + "    \"eventListeners\" : [ {\n"
            + "      \"event\" : \",event\",\n"
            + "      \"events\" : [ {\n"
            + "        \"event\" : \"event\"\n"
            + "      } ],\n"
            + "      \"entityType\" : \"not empty\"\n"
            + "    } ]\n"
            + "  }\n"
            + "}",
        propertiesNode.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertSignalDefinitionsToJson(BpmnModel, ObjectNode)}.
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertSignalDefinitionsToJson(BpmnModel,
   * ObjectNode)}
   */
  @Test
  @DisplayName("Test convertSignalDefinitionsToJson(BpmnModel, ObjectNode)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverterUtil.convertSignalDefinitionsToJson(BpmnModel, ObjectNode)"
  })
  void testConvertSignalDefinitionsToJson() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode propertiesNode = new ObjectNode(nc);

    // Act
    BpmnJsonConverterUtil.convertSignalDefinitionsToJson(bpmnModel, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ArrayNode);
    assertEquals("[ ]", nextResult.toPrettyString());
    assertEquals("{\n  \"signaldefinitions\" : [ ]\n}", propertiesNode.toPrettyString());
    assertEquals(0, nextResult.size());
    assertFalse(nextResult.elements().hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(nextResult.iterator().hasNext());
    assertTrue(nextResult.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertSignalDefinitionsToJson(BpmnModel, ObjectNode)}.
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertSignalDefinitionsToJson(BpmnModel,
   * ObjectNode)}
   */
  @Test
  @DisplayName("Test convertSignalDefinitionsToJson(BpmnModel, ObjectNode)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverterUtil.convertSignalDefinitionsToJson(BpmnModel, ObjectNode)"
  })
  void testConvertSignalDefinitionsToJson2() {
    // Arrange
    Signal signal = new Signal(null, null);
    signal.setScope(null);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(signal);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode propertiesNode = new ObjectNode(nc);
    propertiesNode.put("signaldefinitions", DoubleNode.valueOf(10.0d));

    // Act
    BpmnJsonConverterUtil.convertSignalDefinitionsToJson(bpmnModel, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    assertTrue(iteratorResult.next() instanceof ArrayNode);
    assertEquals(
        "{\n  \"signaldefinitions\" : [ {\n    \"id\" : null,\n    \"name\" : null,\n    \"scope\" : null\n  } ]\n}",
        propertiesNode.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertSignalDefinitionsToJson(BpmnModel, ObjectNode)}.
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertSignalDefinitionsToJson(BpmnModel,
   * ObjectNode)}
   */
  @Test
  @DisplayName("Test convertSignalDefinitionsToJson(BpmnModel, ObjectNode)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverterUtil.convertSignalDefinitionsToJson(BpmnModel, ObjectNode)"
  })
  void testConvertSignalDefinitionsToJson3() {
    // Arrange
    Signal signal = new Signal(null, null);
    signal.setScope("Bpmn Model");

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(signal);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode propertiesNode = new ObjectNode(nc);
    propertiesNode.put("signaldefinitions", DoubleNode.valueOf(10.0d));

    // Act
    BpmnJsonConverterUtil.convertSignalDefinitionsToJson(bpmnModel, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    assertTrue(iteratorResult.next() instanceof ArrayNode);
    assertEquals(
        "{\n  \"signaldefinitions\" : [ {\n    \"id\" : null,\n    \"name\" : null,\n    \"scope\" : \"Bpmn Model\"\n  } ]\n}",
        propertiesNode.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertSignalDefinitionsToJson(BpmnModel, ObjectNode)}.
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertSignalDefinitionsToJson(BpmnModel,
   * ObjectNode)}
   */
  @Test
  @DisplayName("Test convertSignalDefinitionsToJson(BpmnModel, ObjectNode)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BpmnJsonConverterUtil.convertSignalDefinitionsToJson(BpmnModel, ObjectNode)"
  })
  void testConvertSignalDefinitionsToJson4() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(new Signal("", "signaldefinitions"));
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode propertiesNode = new ObjectNode(nc);

    // Act
    BpmnJsonConverterUtil.convertSignalDefinitionsToJson(bpmnModel, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ArrayNode);
    Iterator<JsonNode> elementsResult = nextResult.elements();
    JsonNode nextResult2 = elementsResult.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult2 = nextResult2.iterator();
    assertTrue(iteratorResult2.next() instanceof TextNode);
    assertTrue(iteratorResult2.next() instanceof TextNode);
    assertEquals(
        "[ {\n  \"id\" : \"\",\n  \"name\" : \"signaldefinitions\",\n  \"scope\" : null\n} ]",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"id\" : \"\",\n  \"name\" : \"signaldefinitions\",\n  \"scope\" : null\n}",
        nextResult2.toPrettyString());
    assertEquals(
        "{\n"
            + "  \"signaldefinitions\" : [ {\n"
            + "    \"id\" : \"\",\n"
            + "    \"name\" : \"signaldefinitions\",\n"
            + "    \"scope\" : null\n"
            + "  } ]\n"
            + "}",
        propertiesNode.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertTrue(iteratorResult2.hasNext());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToMessages(JsonNode, BpmnModel)"})
  void testConvertJsonToMessages() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert that nothing has changed
    verify(arrayNode).get("messagedefinitions");
    verify(objectNode, atLeast(1)).get("properties");
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToMessages(JsonNode, BpmnModel)"})
  void testConvertJsonToMessages2() throws UnsupportedEncodingException {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any()))
        .thenReturn(new BinaryNode("AXAXAXAX".getBytes("UTF-8")));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.asText()).thenReturn("As Text");
    when(arrayNode2.isNull()).thenReturn(false);
    when(arrayNode2.isTextual()).thenReturn(true);
    when(arrayNode2.iterator()).thenReturn(iteratorResult);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode3);
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert
    verify(arrayNode2).isNull();
    verify(arrayNode2).isTextual();
    verify(arrayNode2).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3).get("messagedefinitions");
    verify(objectNode, atLeast(1)).get("properties");
    verify(arrayNode2, atLeast(1)).asText();
    assertEquals(1, element.getMessages().size());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add valueOf ten.
   *   <li>Then calls {@link ArrayNode#isTextual()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToMessages(JsonNode, BpmnModel); given ArrayList() add valueOf ten; then calls isTextual()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToMessages(JsonNode, BpmnModel)"})
  void testConvertJsonToMessages_givenArrayListAddValueOfTen_thenCallsIsTextual() {
    // Arrange
    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(DoubleNode.valueOf(10.0d));
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("As Text");
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.isTextual()).thenReturn(true);
    when(arrayNode.iterator()).thenReturn(iteratorResult);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert that nothing has changed
    verify(arrayNode).isNull();
    verify(arrayNode).isTextual();
    verify(arrayNode).iterator();
    verify(arrayNode2).get("messagedefinitions");
    verify(objectNode, atLeast(1)).get("properties");
    verify(arrayNode, atLeast(1)).asText();
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#asText()} return {@code 42}.
   *   <li>Then calls {@link ArrayNode#isTextual()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToMessages(JsonNode, BpmnModel); given ArrayNode asText() return '42'; then calls isTextual()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToMessages(JsonNode, BpmnModel)"})
  void testConvertJsonToMessages_givenArrayNodeAsTextReturn42_thenCallsIsTextual() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("42");
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.isTextual()).thenReturn(true);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert that nothing has changed
    verify(arrayNode).isNull();
    verify(arrayNode).isTextual();
    verify(arrayNode2).get("messagedefinitions");
    verify(objectNode, atLeast(1)).get("properties");
    verify(arrayNode, atLeast(1)).asText();
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#asText()} return {@code As Text}.
   *   <li>Then calls {@link ArrayNode#isTextual()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToMessages(JsonNode, BpmnModel); given ArrayNode asText() return 'As Text'; then calls isTextual()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToMessages(JsonNode, BpmnModel)"})
  void testConvertJsonToMessages_givenArrayNodeAsTextReturnAsText_thenCallsIsTextual() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("As Text");
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert that nothing has changed
    verify(arrayNode).isNull();
    verify(arrayNode).isTextual();
    verify(arrayNode).iterator();
    verify(arrayNode2).get("messagedefinitions");
    verify(objectNode, atLeast(1)).get("properties");
    verify(arrayNode, atLeast(1)).asText();
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#asText()} return empty string.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToMessages(JsonNode, BpmnModel); given ArrayNode asText() return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToMessages(JsonNode, BpmnModel)"})
  void testConvertJsonToMessages_givenArrayNodeAsTextReturnEmptyString() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("");
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert that nothing has changed
    verify(arrayNode).isNull();
    verify(arrayNode).isTextual();
    verify(arrayNode).iterator();
    verify(arrayNode2).get("messagedefinitions");
    verify(objectNode, atLeast(1)).get("properties");
    verify(arrayNode).asText();
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#asText()} return {@code null}.
   *   <li>Then calls {@link ArrayNode#isTextual()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToMessages(JsonNode, BpmnModel); given ArrayNode asText() return 'null'; then calls isTextual()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToMessages(JsonNode, BpmnModel)"})
  void testConvertJsonToMessages_givenArrayNodeAsTextReturnNull_thenCallsIsTextual() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn(null);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert that nothing has changed
    verify(arrayNode).isNull();
    verify(arrayNode).isTextual();
    verify(arrayNode).iterator();
    verify(arrayNode2).get("messagedefinitions");
    verify(objectNode, atLeast(1)).get("properties");
    verify(arrayNode).asText();
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToMessages(JsonNode, BpmnModel); given ArrayNode get(String) return valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToMessages(JsonNode, BpmnModel)"})
  void testConvertJsonToMessages_givenArrayNodeGetReturnValueOfTen() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert that nothing has changed
    verify(arrayNode).get("messagedefinitions");
    verify(objectNode, atLeast(1)).get("properties");
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#isNull()} return {@code true}.
   *   <li>Then calls {@link ArrayNode#isTextual()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToMessages(JsonNode, BpmnModel); given ArrayNode isNull() return 'true'; then calls isTextual()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToMessages(JsonNode, BpmnModel)"})
  void testConvertJsonToMessages_givenArrayNodeIsNullReturnTrue_thenCallsIsTextual() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.asText()).thenReturn("As Text");
    when(arrayNode3.isNull()).thenReturn(false);
    when(arrayNode3.isTextual()).thenReturn(true);
    when(arrayNode3.iterator()).thenReturn(iteratorResult);

    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode4);
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert that nothing has changed
    verify(arrayNode3).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode3).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode4).get("messagedefinitions");
    verify(objectNode, atLeast(1)).get("properties");
    verify(arrayNode3, atLeast(1)).asText();
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#isNull()} return {@code true}.
   *   <li>Then calls {@link ArrayNode#iterator()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToMessages(JsonNode, BpmnModel); given ArrayNode isNull() return 'true'; then calls iterator()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToMessages(JsonNode, BpmnModel)"})
  void testConvertJsonToMessages_givenArrayNodeIsNullReturnTrue_thenCallsIterator() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert that nothing has changed
    verify(arrayNode).isNull();
    verify(arrayNode).iterator();
    verify(arrayNode2).get("messagedefinitions");
    verify(objectNode, atLeast(1)).get("properties");
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   *
   * <ul>
   *   <li>Given valueOf ten.
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToMessages(JsonNode, BpmnModel); given valueOf ten; when ArrayNode get(String) return valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToMessages(JsonNode, BpmnModel)"})
  void testConvertJsonToMessages_givenValueOfTen_whenArrayNodeGetReturnValueOfTen() {
    // Arrange
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert that nothing has changed
    verify(objectNode, atLeast(1)).get("properties");
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   *
   * <ul>
   *   <li>Then {@link BpmnModel} (default constructor) Messages size is one.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToMessages(JsonNode, BpmnModel); then BpmnModel (default constructor) Messages size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToMessages(JsonNode, BpmnModel)"})
  void testConvertJsonToMessages_thenBpmnModelMessagesSizeIsOne() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.asText()).thenReturn("As Text");
    when(arrayNode2.isNull()).thenReturn(false);
    when(arrayNode2.isTextual()).thenReturn(true);
    when(arrayNode2.iterator()).thenReturn(iteratorResult);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode3);
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert
    verify(arrayNode2).isNull();
    verify(arrayNode2).isTextual();
    verify(arrayNode2).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3).get("messagedefinitions");
    verify(objectNode, atLeast(1)).get("properties");
    verify(arrayNode2, atLeast(1)).asText();
    assertEquals(1, element.getMessages().size());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   *
   * <ul>
   *   <li>When {@link BpmnModel} {@link BpmnModel#addMessage(Message)} does nothing.
   *   <li>Then calls {@link BpmnModel#addMessage(Message)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToMessages(JsonNode, BpmnModel); when BpmnModel addMessage(Message) does nothing; then calls addMessage(Message)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToMessages(JsonNode, BpmnModel)"})
  void testConvertJsonToMessages_whenBpmnModelAddMessageDoesNothing_thenCallsAddMessage() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(DoubleNode.valueOf(10.0d));
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.asText()).thenReturn("As Text");
    when(arrayNode3.isNull()).thenReturn(false);
    when(arrayNode3.isTextual()).thenReturn(true);
    when(arrayNode3.iterator()).thenReturn(iteratorResult);

    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode4);

    BpmnModel element = mock(BpmnModel.class);
    doNothing().when(element).addMessage(Mockito.<Message>any());

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert
    verify(arrayNode3).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode3).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode4).get("messagedefinitions");
    verify(objectNode, atLeast(1)).get("properties");
    verify(arrayNode3, atLeast(1)).asText();
    verify(arrayNode, atLeast(1)).asText();
    verify(element).addMessage(isA(Message.class));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   *
   * <ul>
   *   <li>When valueOf ten.
   *   <li>Then {@link BpmnModel} (default constructor) Messages Empty.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToMessages(JsonNode, BpmnModel); when valueOf ten; then BpmnModel (default constructor) Messages Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToMessages(JsonNode, BpmnModel)"})
  void testConvertJsonToMessages_whenValueOfTen_thenBpmnModelMessagesEmpty() {
    // Arrange
    DoubleNode objectNode = DoubleNode.valueOf(10.0d);
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert that nothing has changed
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement,
   * boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseListeners(JsonNode, BaseElement, boolean)"})
  void testParseListeners() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));
    when(arrayNode.isNull()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, new ActivitiListener(), true);

    // Assert
    verify(listenersNode).isNull();
    verify(arrayNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode).get("event");
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement,
   * boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseListeners(JsonNode, BaseElement, boolean)"})
  void testParseListeners2() throws UnsupportedEncodingException {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any()))
        .thenReturn(new BinaryNode("AXAXAXAX".getBytes("UTF-8")));
    when(arrayNode.isNull()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, new ActivitiListener(), true);

    // Assert
    verify(listenersNode).isNull();
    verify(arrayNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement,
   * boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseListeners(JsonNode, BaseElement, boolean)"})
  void testParseListeners3() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode2.isNull()).thenReturn(false);
    when(arrayNode2.asText()).thenReturn("As Text");

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.isNull()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode3);
    Iterator<JsonNode> iteratorResult = jsonNodeList2.iterator();

    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, new ActivitiListener(), true);

    // Assert
    verify(listenersNode).isNull();
    verify(arrayNode3).isNull();
    verify(arrayNode2, atLeast(1)).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode2).iterator();
    verify(arrayNode3, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode).get("name");
    verify(listenersNode, atLeast(1)).asText();
    verify(arrayNode2, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement,
   * boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseListeners(JsonNode, BaseElement, boolean)"})
  void testParseListeners4() throws UnsupportedEncodingException {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any()))
        .thenReturn(new BinaryNode("AXAXAXAX".getBytes("UTF-8")));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode2.isNull()).thenReturn(false);
    when(arrayNode2.asText()).thenReturn("As Text");

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.isNull()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode3);
    Iterator<JsonNode> iteratorResult = jsonNodeList2.iterator();

    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, new ActivitiListener(), true);

    // Assert
    verify(listenersNode).isNull();
    verify(arrayNode3).isNull();
    verify(arrayNode2, atLeast(1)).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode2).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).get(Mockito.<String>any());
    verify(listenersNode, atLeast(1)).asText();
    verify(arrayNode2, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link ArrayNode} {@link ArrayNode#asText()} return {@code 42}.
   *   <li>Then calls {@link ArrayNode#isTextual()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement,
   * boolean)}
   */
  @Test
  @DisplayName(
      "Test parseListeners(JsonNode, BaseElement, boolean); given '42'; when ArrayNode asText() return '42'; then calls isTextual()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseListeners(JsonNode, BaseElement, boolean)"})
  void testParseListeners_given42_whenArrayNodeAsTextReturn42_thenCallsIsTextual() {
    // Arrange
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("42");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, new ActivitiListener(), true);

    // Assert
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add valueOf ten.
   *   <li>Then calls {@link ArrayNode#get(String)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement,
   * boolean)}
   */
  @Test
  @DisplayName(
      "Test parseListeners(JsonNode, BaseElement, boolean); given ArrayList() add valueOf ten; then calls get(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseListeners(JsonNode, BaseElement, boolean)"})
  void testParseListeners_givenArrayListAddValueOfTen_thenCallsGet() {
    // Arrange
    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(DoubleNode.valueOf(10.0d));

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList2.iterator();

    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, new ActivitiListener(), true);

    // Assert
    verify(listenersNode).isNull();
    verify(arrayNode2).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(listenersNode, atLeast(1)).asText();
    verify(arrayNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add valueOf ten.
   *   <li>When {@link ArrayNode} {@link ArrayNode#asText()} return {@code As Text}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement,
   * boolean)}
   */
  @Test
  @DisplayName(
      "Test parseListeners(JsonNode, BaseElement, boolean); given ArrayList() add valueOf ten; when ArrayNode asText() return 'As Text'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseListeners(JsonNode, BaseElement, boolean)"})
  void testParseListeners_givenArrayListAddValueOfTen_whenArrayNodeAsTextReturnAsText() {
    // Arrange
    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(DoubleNode.valueOf(10.0d));
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, new ActivitiListener(), true);

    // Assert
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return {@link ArrayNode}.
   *   <li>Then calls {@link ArrayNode#get(String)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement,
   * boolean)}
   */
  @Test
  @DisplayName(
      "Test parseListeners(JsonNode, BaseElement, boolean); given ArrayNode get(String) return ArrayNode; then calls get(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseListeners(JsonNode, BaseElement, boolean)"})
  void testParseListeners_givenArrayNodeGetReturnArrayNode_thenCallsGet() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, new ActivitiListener(), true);

    // Assert
    verify(listenersNode).isNull();
    verify(arrayNode2).isNull();
    verify(arrayNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode2).get("event");
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return valueOf ten.
   *   <li>Then calls {@link ArrayNode#get(String)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement,
   * boolean)}
   */
  @Test
  @DisplayName(
      "Test parseListeners(JsonNode, BaseElement, boolean); given ArrayNode get(String) return valueOf ten; then calls get(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseListeners(JsonNode, BaseElement, boolean)"})
  void testParseListeners_givenArrayNodeGetReturnValueOfTen_thenCallsGet() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));
    when(arrayNode.isNull()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, new ActivitiListener(), true);

    // Assert
    verify(listenersNode).isNull();
    verify(arrayNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return valueOf ten.
   *   <li>Then calls {@link ArrayNode#get(String)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement,
   * boolean)}
   */
  @Test
  @DisplayName(
      "Test parseListeners(JsonNode, BaseElement, boolean); given ArrayNode get(String) return valueOf ten; then calls get(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseListeners(JsonNode, BaseElement, boolean)"})
  void testParseListeners_givenArrayNodeGetReturnValueOfTen_thenCallsGet2() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode2.isNull()).thenReturn(false);
    when(arrayNode2.asText()).thenReturn("As Text");

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);
    when(arrayNode3.isNull()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode3);
    Iterator<JsonNode> iteratorResult = jsonNodeList2.iterator();

    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, new ActivitiListener(), true);

    // Assert
    verify(listenersNode).isNull();
    verify(arrayNode3).isNull();
    verify(arrayNode2, atLeast(1)).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode2).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).get(Mockito.<String>any());
    verify(listenersNode, atLeast(1)).asText();
    verify(arrayNode2, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#iterator()} return {@link ArrayList#ArrayList()}
   *       iterator.
   *   <li>Then calls {@link ArrayNode#get(String)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement,
   * boolean)}
   */
  @Test
  @DisplayName(
      "Test parseListeners(JsonNode, BaseElement, boolean); given ArrayNode iterator() return ArrayList() iterator; then calls get(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseListeners(JsonNode, BaseElement, boolean)"})
  void testParseListeners_givenArrayNodeIteratorReturnArrayListIterator_thenCallsGet() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList2.iterator();

    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, new ActivitiListener(), true);

    // Assert
    verify(listenersNode).isNull();
    verify(arrayNode2).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(listenersNode, atLeast(1)).asText();
    verify(arrayNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#iterator()} return {@link ArrayList#ArrayList()}
   *       iterator.
   *   <li>Then calls {@link ArrayNode#get(String)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement,
   * boolean)}
   */
  @Test
  @DisplayName(
      "Test parseListeners(JsonNode, BaseElement, boolean); given ArrayNode iterator() return ArrayList() iterator; then calls get(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseListeners(JsonNode, BaseElement, boolean)"})
  void testParseListeners_givenArrayNodeIteratorReturnArrayListIterator_thenCallsGet2() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode3.isNull()).thenReturn(false);
    when(arrayNode3.asText()).thenReturn("As Text");

    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);
    when(arrayNode4.isNull()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode4);
    Iterator<JsonNode> iteratorResult = jsonNodeList2.iterator();

    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, new ActivitiListener(), true);

    // Assert
    verify(listenersNode).isNull();
    verify(arrayNode4).isNull();
    verify(arrayNode).isNull();
    verify(arrayNode3, atLeast(1)).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode3).iterator();
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2).get("name");
    verify(listenersNode, atLeast(1)).asText();
    verify(arrayNode3, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   *
   * <ul>
   *   <li>Given {@code As Text}.
   *   <li>When {@link ArrayNode} {@link ArrayNode#asText()} return {@code As Text}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement,
   * boolean)}
   */
  @Test
  @DisplayName(
      "Test parseListeners(JsonNode, BaseElement, boolean); given 'As Text'; when ArrayNode asText() return 'As Text'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseListeners(JsonNode, BaseElement, boolean)"})
  void testParseListeners_givenAsText_whenArrayNodeAsTextReturnAsText() {
    // Arrange
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(listenersNode.iterator()).thenReturn(jsonNodeList.iterator());

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, new ActivitiListener(), true);

    // Assert
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   *
   * <ul>
   *   <li>Given empty string.
   *   <li>When {@link ArrayNode} {@link ArrayNode#asText()} return empty string.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement,
   * boolean)}
   */
  @Test
  @DisplayName(
      "Test parseListeners(JsonNode, BaseElement, boolean); given empty string; when ArrayNode asText() return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseListeners(JsonNode, BaseElement, boolean)"})
  void testParseListeners_givenEmptyString_whenArrayNodeAsTextReturnEmptyString() {
    // Arrange
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(listenersNode.iterator()).thenReturn(jsonNodeList.iterator());

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, new ActivitiListener(), true);

    // Assert
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(listenersNode).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link ArrayNode} {@link ArrayNode#asText()} return {@code null}.
   *   <li>Then calls {@link ArrayNode#isTextual()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement,
   * boolean)}
   */
  @Test
  @DisplayName(
      "Test parseListeners(JsonNode, BaseElement, boolean); given 'null'; when ArrayNode asText() return 'null'; then calls isTextual()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseListeners(JsonNode, BaseElement, boolean)"})
  void testParseListeners_givenNull_whenArrayNodeAsTextReturnNull_thenCallsIsTextual() {
    // Arrange
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn(null);
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(listenersNode.iterator()).thenReturn(jsonNodeList.iterator());

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, new ActivitiListener(), true);

    // Assert
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(listenersNode).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   *
   * <ul>
   *   <li>Then {@link AdhocSubProcess} (default constructor) ExecutionListeners size is one.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement,
   * boolean)}
   */
  @Test
  @DisplayName(
      "Test parseListeners(JsonNode, BaseElement, boolean); then AdhocSubProcess (default constructor) ExecutionListeners size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseListeners(JsonNode, BaseElement, boolean)"})
  void testParseListeners_thenAdhocSubProcessExecutionListenersSizeIsOne() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList2.iterator();

    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);
    AdhocSubProcess element = new AdhocSubProcess();

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, element, true);

    // Assert
    verify(listenersNode).isNull();
    verify(arrayNode2).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(listenersNode, atLeast(1)).asText();
    verify(arrayNode, atLeast(1)).asText();
    List<ActivitiListener> executionListeners = element.getExecutionListeners();
    assertEquals(1, executionListeners.size());
    ActivitiListener getResult = executionListeners.get(0);
    assertEquals("As Text", getResult.getEvent());
    assertEquals("As Text", getResult.getImplementation());
    assertEquals("class", getResult.getImplementationType());
    assertNull(getResult.getInstance());
    assertNull(getResult.getCustomPropertiesResolverImplementation());
    assertNull(getResult.getCustomPropertiesResolverImplementationType());
    assertNull(getResult.getOnTransaction());
    assertNull(getResult.getId());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getFieldExtensions().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   *
   * <ul>
   *   <li>When {@link ArrayNode} {@link ArrayNode#isNull()} return {@code true}.
   *   <li>Then calls {@link ArrayNode#iterator()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement,
   * boolean)}
   */
  @Test
  @DisplayName(
      "Test parseListeners(JsonNode, BaseElement, boolean); when ArrayNode isNull() return 'true'; then calls iterator()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseListeners(JsonNode, BaseElement, boolean)"})
  void testParseListeners_whenArrayNodeIsNullReturnTrue_thenCallsIterator() {
    // Arrange
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.isNull()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(listenersNode.iterator()).thenReturn(jsonNodeList.iterator());

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, new ActivitiListener(), true);

    // Assert
    verify(listenersNode).isNull();
    verify(listenersNode).iterator();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}.
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test parseMessages(JsonNode, BpmnModel)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseMessages(JsonNode, BpmnModel)"})
  void testParseMessages() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);

    ArrayNode messagesNode = mock(ArrayNode.class);
    when(messagesNode.iterator()).thenReturn(jsonNodeList.iterator());
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.parseMessages(messagesNode, element);

    // Assert that nothing has changed
    verify(messagesNode).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add valueOf ten.
   *   <li>Then {@link BpmnModel} (default constructor) Messages Empty.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test parseMessages(JsonNode, BpmnModel); given ArrayList() add valueOf ten; then BpmnModel (default constructor) Messages Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseMessages(JsonNode, BpmnModel)"})
  void testParseMessages_givenArrayListAddValueOfTen_thenBpmnModelMessagesEmpty() {
    // Arrange
    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(DoubleNode.valueOf(10.0d));

    ArrayNode messagesNode = mock(ArrayNode.class);
    when(messagesNode.iterator()).thenReturn(jsonNodeList.iterator());
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.parseMessages(messagesNode, element);

    // Assert that nothing has changed
    verify(messagesNode).iterator();
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} iterator.
   *   <li>Then {@link BpmnModel} (default constructor) Messages Empty.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test parseMessages(JsonNode, BpmnModel); given ArrayList() iterator; then BpmnModel (default constructor) Messages Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseMessages(JsonNode, BpmnModel)"})
  void testParseMessages_givenArrayListIterator_thenBpmnModelMessagesEmpty() {
    // Arrange
    ArrayNode messagesNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(messagesNode.iterator()).thenReturn(jsonNodeList.iterator());
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.parseMessages(messagesNode, element);

    // Assert that nothing has changed
    verify(messagesNode).iterator();
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return {@link
   *       BinaryNode#BinaryNode(byte[])} with data is {@code AXAXAXAX} Bytes is {@code UTF-8}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test parseMessages(JsonNode, BpmnModel); given ArrayNode get(String) return BinaryNode(byte[]) with data is 'AXAXAXAX' Bytes is 'UTF-8'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseMessages(JsonNode, BpmnModel)"})
  void testParseMessages_givenArrayNodeGetReturnBinaryNodeWithDataIsAxaxaxaxBytesIsUtf8()
      throws UnsupportedEncodingException {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any()))
        .thenReturn(new BinaryNode("AXAXAXAX".getBytes("UTF-8")));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);

    ArrayNode messagesNode = mock(ArrayNode.class);
    when(messagesNode.iterator()).thenReturn(jsonNodeList.iterator());
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.parseMessages(messagesNode, element);

    // Assert
    verify(messagesNode).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    assertEquals(1, element.getMessages().size());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test parseMessages(JsonNode, BpmnModel); given ArrayNode get(String) return valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseMessages(JsonNode, BpmnModel)"})
  void testParseMessages_givenArrayNodeGetReturnValueOfTen() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);

    ArrayNode messagesNode = mock(ArrayNode.class);
    when(messagesNode.iterator()).thenReturn(jsonNodeList.iterator());
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.parseMessages(messagesNode, element);

    // Assert
    verify(messagesNode).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    assertEquals(1, element.getMessages().size());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#isNull()} return {@code true}.
   *   <li>Then calls {@link ArrayNode#isNull()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test parseMessages(JsonNode, BpmnModel); given ArrayNode isNull() return 'true'; then calls isNull()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseMessages(JsonNode, BpmnModel)"})
  void testParseMessages_givenArrayNodeIsNullReturnTrue_thenCallsIsNull() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);

    ArrayNode messagesNode = mock(ArrayNode.class);
    when(messagesNode.iterator()).thenReturn(jsonNodeList.iterator());
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.parseMessages(messagesNode, element);

    // Assert that nothing has changed
    verify(arrayNode, atLeast(1)).isNull();
    verify(messagesNode).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}.
   *
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test parseMessages(JsonNode, BpmnModel); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseMessages(JsonNode, BpmnModel)"})
  void testParseMessages_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode messagesNode = new ArrayNode(nf);
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.parseMessages(messagesNode, element);

    // Assert that nothing has changed
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}.
   *
   * <ul>
   *   <li>When {@link BpmnModel} {@link BpmnModel#addMessage(Message)} does nothing.
   *   <li>Then calls {@link ArrayNode#asText()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test parseMessages(JsonNode, BpmnModel); when BpmnModel addMessage(Message) does nothing; then calls asText()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseMessages(JsonNode, BpmnModel)"})
  void testParseMessages_whenBpmnModelAddMessageDoesNothing_thenCallsAsText() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(DoubleNode.valueOf(10.0d));
    jsonNodeList.add(arrayNode2);

    ArrayNode messagesNode = mock(ArrayNode.class);
    when(messagesNode.iterator()).thenReturn(jsonNodeList.iterator());

    BpmnModel element = mock(BpmnModel.class);
    doNothing().when(element).addMessage(Mockito.<Message>any());

    // Act
    BpmnJsonConverterUtil.parseMessages(messagesNode, element);

    // Assert
    verify(arrayNode, atLeast(1)).isNull();
    verify(messagesNode).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).asText();
    verify(element).addMessage(isA(Message.class));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then {@link BpmnModel} (default constructor) Messages Empty.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test parseMessages(JsonNode, BpmnModel); when 'null'; then BpmnModel (default constructor) Messages Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseMessages(JsonNode, BpmnModel)"})
  void testParseMessages_whenNull_thenBpmnModelMessagesEmpty() {
    // Arrange
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.parseMessages(null, element);

    // Assert that nothing has changed
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}.
   *
   * <ul>
   *   <li>When valueOf ten.
   *   <li>Then {@link BpmnModel} (default constructor) Messages Empty.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test parseMessages(JsonNode, BpmnModel); when valueOf ten; then BpmnModel (default constructor) Messages Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseMessages(JsonNode, BpmnModel)"})
  void testParseMessages_whenValueOfTen_thenBpmnModelMessagesEmpty() {
    // Arrange
    DoubleNode messagesNode = DoubleNode.valueOf(10.0d);
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.parseMessages(messagesNode, element);

    // Assert that nothing has changed
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseEventListeners(JsonNode, Process)"})
  void testParseEventListeners() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode).get("events");
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseEventListeners(JsonNode, Process)"})
  void testParseEventListeners2() {
    // Arrange
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);

    ArrayNode arrayNode = new ArrayNode(nf);
    arrayNode.add(DoubleNode.valueOf(10.0d));

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode2).get("events");
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseEventListeners(JsonNode, Process)"})
  void testParseEventListeners3() {
    // Arrange
    ArrayNode value = mock(ArrayNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(value.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));
    JsonNodeFactory nf2 = JsonNodeFactory.withExactBigDecimals(true);

    ArrayNode arrayNode = new ArrayNode(nf2);
    arrayNode.add(value);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(value).get("event");
    verify(arrayNode2).get("events");
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseEventListeners(JsonNode, Process)"})
  void testParseEventListeners4() throws UnsupportedEncodingException {
    // Arrange
    ArrayNode value = mock(ArrayNode.class);
    when(value.get(Mockito.<String>any())).thenReturn(new BinaryNode("AXAXAXAX".getBytes("UTF-8")));
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);

    ArrayNode arrayNode = new ArrayNode(nf);
    arrayNode.add(value);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(value).get("event");
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link ArrayNode} {@link ArrayNode#asText()} return {@code 42}.
   *   <li>Then calls {@link ArrayNode#asText()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName(
      "Test parseEventListeners(JsonNode, Process); given '42'; when ArrayNode asText() return '42'; then calls asText()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseEventListeners(JsonNode, Process)"})
  void testParseEventListeners_given42_whenArrayNodeAsTextReturn42_thenCallsAsText() {
    // Arrange
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("42");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process); given ArrayList() add valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseEventListeners(JsonNode, Process)"})
  void testParseEventListeners_givenArrayListAddValueOfTen() {
    // Arrange
    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(DoubleNode.valueOf(10.0d));
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return valueOf ten.
   *   <li>Then calls {@link ArrayNode#get(String)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName(
      "Test parseEventListeners(JsonNode, Process); given ArrayNode get(String) return valueOf ten; then calls get(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseEventListeners(JsonNode, Process)"})
  void testParseEventListeners_givenArrayNodeGetReturnValueOfTen_thenCallsGet() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode).get("events");
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return valueOf ten.
   *   <li>Then calls {@link ArrayNode#get(String)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName(
      "Test parseEventListeners(JsonNode, Process); given ArrayNode get(String) return valueOf ten; then calls get(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseEventListeners(JsonNode, Process)"})
  void testParseEventListeners_givenArrayNodeGetReturnValueOfTen_thenCallsGet2() {
    // Arrange
    ArrayNode value = mock(ArrayNode.class);
    when(value.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);

    ArrayNode arrayNode = new ArrayNode(nf);
    arrayNode.add(value);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(value).get("event");
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#isNull()} return {@code true}.
   *   <li>Then calls {@link ArrayNode#get(String)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName(
      "Test parseEventListeners(JsonNode, Process); given ArrayNode isNull() return 'true'; then calls get(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseEventListeners(JsonNode, Process)"})
  void testParseEventListeners_givenArrayNodeIsNullReturnTrue_thenCallsGet() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);

    ArrayNode value = mock(ArrayNode.class);
    when(value.get(Mockito.<String>any())).thenReturn(arrayNode);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);

    ArrayNode arrayNode2 = new ArrayNode(nf);
    arrayNode2.add(value);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode3);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(listenersNode).isNull();
    verify(arrayNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(value).get("event");
    verify(arrayNode3).get("events");
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code
   *       true} addObject.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName(
      "Test parseEventListeners(JsonNode, Process); given ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true' addObject")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseEventListeners(JsonNode, Process)"})
  void testParseEventListeners_givenArrayNodeWithNfIsWithExactBigDecimalsTrueAddObject() {
    // Arrange
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);

    ArrayNode arrayNode = new ArrayNode(nf);
    arrayNode.addObject();
    arrayNode.add(DoubleNode.valueOf(10.0d));

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();

    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode2).get("events");
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   *
   * <ul>
   *   <li>Given {@code As Text}.
   *   <li>When {@link ArrayNode} {@link ArrayNode#asText()} return {@code As Text}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName(
      "Test parseEventListeners(JsonNode, Process); given 'As Text'; when ArrayNode asText() return 'As Text'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseEventListeners(JsonNode, Process)"})
  void testParseEventListeners_givenAsText_whenArrayNodeAsTextReturnAsText() {
    // Arrange
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(listenersNode.iterator()).thenReturn(jsonNodeList.iterator());

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   *
   * <ul>
   *   <li>Given empty string.
   *   <li>When {@link ArrayNode} {@link ArrayNode#asText()} return empty string.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName(
      "Test parseEventListeners(JsonNode, Process); given empty string; when ArrayNode asText() return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseEventListeners(JsonNode, Process)"})
  void testParseEventListeners_givenEmptyString_whenArrayNodeAsTextReturnEmptyString() {
    // Arrange
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(listenersNode.iterator()).thenReturn(jsonNodeList.iterator());

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(listenersNode).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link ArrayNode} {@link ArrayNode#asText()} return {@code null}.
   *   <li>Then calls {@link ArrayNode#asText()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName(
      "Test parseEventListeners(JsonNode, Process); given 'null'; when ArrayNode asText() return 'null'; then calls asText()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseEventListeners(JsonNode, Process)"})
  void testParseEventListeners_givenNull_whenArrayNodeAsTextReturnNull_thenCallsAsText() {
    // Arrange
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn(null);
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(listenersNode.iterator()).thenReturn(jsonNodeList.iterator());

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(listenersNode).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   *
   * <ul>
   *   <li>When {@link ArrayNode} {@link ArrayNode#isNull()} return {@code true}.
   *   <li>Then calls {@link ArrayNode#iterator()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName(
      "Test parseEventListeners(JsonNode, Process); when ArrayNode isNull() return 'true'; then calls iterator()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseEventListeners(JsonNode, Process)"})
  void testParseEventListeners_whenArrayNodeIsNullReturnTrue_thenCallsIterator() {
    // Arrange
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.isNull()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(listenersNode.iterator()).thenReturn(jsonNodeList.iterator());

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(listenersNode).isNull();
    verify(listenersNode).iterator();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   *
   * <ul>
   *   <li>When {@link ArrayNode} {@link ArrayNode#isTextual()} return {@code false}.
   *   <li>Then calls {@link ArrayNode#isTextual()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName(
      "Test parseEventListeners(JsonNode, Process); when ArrayNode isTextual() return 'false'; then calls isTextual()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseEventListeners(JsonNode, Process)"})
  void testParseEventListeners_whenArrayNodeIsTextualReturnFalse_thenCallsIsTextual() {
    // Arrange
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(listenersNode.iterator()).thenReturn(jsonNodeList.iterator());

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}.
   *
   * <ul>
   *   <li>Given valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}
   */
  @Test
  @DisplayName("Test lookForSourceRef(String, JsonNode); given valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String BpmnJsonConverterUtil.lookForSourceRef(String, JsonNode)"})
  void testLookForSourceRef_givenValueOfTen() {
    // Arrange
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);

    ArrayNode childShapesNode = new ArrayNode(nf);
    childShapesNode.add(DoubleNode.valueOf(10.0d));

    // Act
    String actualLookForSourceRefResult =
        BpmnJsonConverterUtil.lookForSourceRef("42", childShapesNode);

    // Assert
    assertNull(actualLookForSourceRefResult);
  }

  /**
   * Test {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}.
   *
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}
   */
  @Test
  @DisplayName(
      "Test lookForSourceRef(String, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String BpmnJsonConverterUtil.lookForSourceRef(String, JsonNode)"})
  void testLookForSourceRef_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);

    // Act
    String actualLookForSourceRefResult =
        BpmnJsonConverterUtil.lookForSourceRef("42", new ArrayNode(nf));

    // Assert
    assertNull(actualLookForSourceRefResult);
  }

  /**
   * Test {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}.
   *
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code
   *       true} addObject.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}
   */
  @Test
  @DisplayName(
      "Test lookForSourceRef(String, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true' addObject")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String BpmnJsonConverterUtil.lookForSourceRef(String, JsonNode)"})
  void testLookForSourceRef_whenArrayNodeWithNfIsWithExactBigDecimalsTrueAddObject() {
    // Arrange
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);

    ArrayNode childShapesNode = new ArrayNode(nf);
    childShapesNode.addObject();
    childShapesNode.add(DoubleNode.valueOf(10.0d));

    // Act
    String actualLookForSourceRefResult =
        BpmnJsonConverterUtil.lookForSourceRef("42", childShapesNode);

    // Assert
    assertNull(actualLookForSourceRefResult);
  }

  /**
   * Test {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}.
   *
   * <ul>
   *   <li>When {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}
   */
  @Test
  @DisplayName("Test lookForSourceRef(String, JsonNode); when 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String BpmnJsonConverterUtil.lookForSourceRef(String, JsonNode)"})
  void testLookForSourceRef_whenNull() {
    // Arrange, Act and Assert
    assertNull(BpmnJsonConverterUtil.lookForSourceRef("42", null));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}.
   *
   * <ul>
   *   <li>When valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}
   */
  @Test
  @DisplayName("Test lookForSourceRef(String, JsonNode); when valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String BpmnJsonConverterUtil.lookForSourceRef(String, JsonNode)"})
  void testLookForSourceRef_whenValueOfTen() {
    // Arrange, Act and Assert
    assertNull(BpmnJsonConverterUtil.lookForSourceRef("42", DoubleNode.valueOf(10.0d)));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}.
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToDataProperties(JsonNode, BaseElement)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List BpmnJsonConverterUtil.convertJsonToDataProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToDataProperties() {
    // Arrange
    BinaryNode objectNode = new BinaryNode(new byte[] {-1, 1, 'A', 1, 'A', 1, 'A', 1});

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult =
        BpmnJsonConverterUtil.convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}.
   *
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToDataProperties(JsonNode, BaseElement); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List BpmnJsonConverterUtil.convertJsonToDataProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToDataProperties_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode objectNode = new ArrayNode(nf);

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult =
        BpmnJsonConverterUtil.convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}.
   *
   * <ul>
   *   <li>When {@link BigIntegerNode#BigIntegerNode(BigInteger)} with v is valueOf {@link
   *       Long#MAX_VALUE}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToDataProperties(JsonNode, BaseElement); when BigIntegerNode(BigInteger) with v is valueOf MAX_VALUE")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List BpmnJsonConverterUtil.convertJsonToDataProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToDataProperties_whenBigIntegerNodeWithVIsValueOfMax_value() {
    // Arrange
    BigIntegerNode objectNode = new BigIntegerNode(BigInteger.valueOf(Long.MAX_VALUE));

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult =
        BpmnJsonConverterUtil.convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}.
   *
   * <ul>
   *   <li>When {@link BigIntegerNode#BigIntegerNode(BigInteger)} with v is valueOf one.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToDataProperties(JsonNode, BaseElement); when BigIntegerNode(BigInteger) with v is valueOf one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List BpmnJsonConverterUtil.convertJsonToDataProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToDataProperties_whenBigIntegerNodeWithVIsValueOfOne() {
    // Arrange
    BigInteger v = BigInteger.valueOf(1L);
    BigIntegerNode objectNode = new BigIntegerNode(v);

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult =
        BpmnJsonConverterUtil.convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}.
   *
   * <ul>
   *   <li>When {@link BinaryNode#BinaryNode(byte[])} with data is array of {@code byte} with {@code
   *       A} and one.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToDataProperties(JsonNode, BaseElement); when BinaryNode(byte[]) with data is array of byte with 'A' and one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List BpmnJsonConverterUtil.convertJsonToDataProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToDataProperties_whenBinaryNodeWithDataIsArrayOfByteWithAAndOne() {
    // Arrange
    BinaryNode objectNode = new BinaryNode(new byte[] {'A', 1, 'A', 1, 'A', 1, 'A', 1});

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult =
        BpmnJsonConverterUtil.convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}.
   *
   * <ul>
   *   <li>When {@link BinaryNode#BinaryNode(byte[])} with data is empty array of {@code byte}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToDataProperties(JsonNode, BaseElement); when BinaryNode(byte[]) with data is empty array of byte")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List BpmnJsonConverterUtil.convertJsonToDataProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToDataProperties_whenBinaryNodeWithDataIsEmptyArrayOfByte() {
    // Arrange
    BinaryNode objectNode = new BinaryNode(new byte[] {});

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult =
        BpmnJsonConverterUtil.convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}.
   *
   * <ul>
   *   <li>When False.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToDataProperties(JsonNode, BaseElement); when False; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List BpmnJsonConverterUtil.convertJsonToDataProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToDataProperties_whenFalse_thenReturnEmpty() {
    // Arrange
    BooleanNode objectNode = BooleanNode.getFalse();

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult =
        BpmnJsonConverterUtil.convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}.
   *
   * <ul>
   *   <li>When Instance.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToDataProperties(JsonNode, BaseElement); when Instance; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List BpmnJsonConverterUtil.convertJsonToDataProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToDataProperties_whenInstance_thenReturnEmpty() {
    // Arrange
    MissingNode objectNode = MissingNode.getInstance();

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult =
        BpmnJsonConverterUtil.convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}.
   *
   * <ul>
   *   <li>When Instance.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToDataProperties(JsonNode, BaseElement); when Instance; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List BpmnJsonConverterUtil.convertJsonToDataProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToDataProperties_whenInstance_thenReturnEmpty2() {
    // Arrange
    NullNode objectNode = NullNode.getInstance();

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult =
        BpmnJsonConverterUtil.convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToDataProperties(JsonNode, BaseElement); when 'null'; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List BpmnJsonConverterUtil.convertJsonToDataProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToDataProperties_whenNull_thenReturnEmpty() {
    // Arrange and Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult =
        BpmnJsonConverterUtil.convertJsonToDataProperties(null, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}.
   *
   * <ul>
   *   <li>When True.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToDataProperties(JsonNode, BaseElement); when True; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List BpmnJsonConverterUtil.convertJsonToDataProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToDataProperties_whenTrue_thenReturnEmpty() {
    // Arrange
    BooleanNode objectNode = BooleanNode.getTrue();

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult =
        BpmnJsonConverterUtil.convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}.
   *
   * <ul>
   *   <li>When valueOf {@link Double#NaN}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToDataProperties(JsonNode, BaseElement); when valueOf NaN; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List BpmnJsonConverterUtil.convertJsonToDataProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToDataProperties_whenValueOfNaN_thenReturnEmpty() {
    // Arrange
    DoubleNode objectNode = DoubleNode.valueOf(Double.NaN);

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult =
        BpmnJsonConverterUtil.convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}.
   *
   * <ul>
   *   <li>When valueOf ten.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToDataProperties(JsonNode, BaseElement); when valueOf ten; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List BpmnJsonConverterUtil.convertJsonToDataProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToDataProperties_whenValueOfTen_thenReturnEmpty() {
    // Arrange
    DoubleNode objectNode = DoubleNode.valueOf(10.0d);

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult =
        BpmnJsonConverterUtil.convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertDataPropertiesToJson(List, ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@link ItemDefinition} (default constructor) StructureRef is {@code
   *       dataproperty_id}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertDataPropertiesToJson(List,
   * ObjectNode)}
   */
  @Test
  @DisplayName(
      "Test convertDataPropertiesToJson(List, ObjectNode); given ItemDefinition (default constructor) StructureRef is 'dataproperty_id'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertDataPropertiesToJson(List, ObjectNode)"})
  void testConvertDataPropertiesToJson_givenItemDefinitionStructureRefIsDatapropertyId() {
    // Arrange
    ItemDefinition itemSubjectRef = new ItemDefinition();
    itemSubjectRef.setStructureRef("dataproperty_id");

    BooleanDataObject booleanDataObject = new BooleanDataObject();
    booleanDataObject.setItemSubjectRef(itemSubjectRef);
    booleanDataObject.setValue("Data Objects");
    booleanDataObject.setId(null);
    booleanDataObject.setName(null);

    ArrayList<ValuedDataObject> dataObjects = new ArrayList<>();
    dataObjects.add(booleanDataObject);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode propertiesNode = new ObjectNode(nc);
    propertiesNode.put("dataproperties", DoubleNode.valueOf(10.0d));

    // Act
    BpmnJsonConverterUtil.convertDataPropertiesToJson(dataObjects, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ArrayNode);
    Iterator<JsonNode> elementsResult = nextResult2.elements();
    JsonNode nextResult3 = elementsResult.next();
    assertTrue(nextResult3 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals(
        "[ {\n"
            + "  \"dataproperty_id\" : null,\n"
            + "  \"dataproperty_name\" : null,\n"
            + "  \"dataproperty_type\" : \"dataproperty_id\",\n"
            + "  \"dataproperty_value\" : \"false\"\n"
            + "} ]",
        nextResult2.toPrettyString());
    assertEquals(
        "{\n"
            + "  \"dataproperties\" : {\n"
            + "    \"items\" : [ {\n"
            + "      \"dataproperty_id\" : null,\n"
            + "      \"dataproperty_name\" : null,\n"
            + "      \"dataproperty_type\" : \"dataproperty_id\",\n"
            + "      \"dataproperty_value\" : \"false\"\n"
            + "    } ]\n"
            + "  }\n"
            + "}",
        propertiesNode.toPrettyString());
    assertEquals(
        "{\n"
            + "  \"dataproperty_id\" : null,\n"
            + "  \"dataproperty_name\" : null,\n"
            + "  \"dataproperty_type\" : \"dataproperty_id\",\n"
            + "  \"dataproperty_value\" : \"false\"\n"
            + "}",
        nextResult3.toPrettyString());
    assertEquals(
        "{\n"
            + "  \"items\" : [ {\n"
            + "    \"dataproperty_id\" : null,\n"
            + "    \"dataproperty_name\" : null,\n"
            + "    \"dataproperty_type\" : \"dataproperty_id\",\n"
            + "    \"dataproperty_value\" : \"false\"\n"
            + "  } ]\n"
            + "}",
        nextResult.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertDataPropertiesToJson(List, ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@link ItemDefinition} (default constructor) StructureRef is empty string.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#convertDataPropertiesToJson(List,
   * ObjectNode)}
   */
  @Test
  @DisplayName(
      "Test convertDataPropertiesToJson(List, ObjectNode); given ItemDefinition (default constructor) StructureRef is empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertDataPropertiesToJson(List, ObjectNode)"})
  void testConvertDataPropertiesToJson_givenItemDefinitionStructureRefIsEmptyString() {
    // Arrange
    ItemDefinition itemSubjectRef = new ItemDefinition();
    itemSubjectRef.setStructureRef("");

    BooleanDataObject booleanDataObject = new BooleanDataObject();
    booleanDataObject.setItemSubjectRef(itemSubjectRef);
    booleanDataObject.setValue("Data Objects");
    booleanDataObject.setId(null);
    booleanDataObject.setName(null);

    ArrayList<ValuedDataObject> dataObjects = new ArrayList<>();
    dataObjects.add(booleanDataObject);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode propertiesNode = new ObjectNode(nc);
    propertiesNode.put("dataproperties", DoubleNode.valueOf(10.0d));

    // Act
    BpmnJsonConverterUtil.convertDataPropertiesToJson(dataObjects, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ArrayNode);
    Iterator<JsonNode> elementsResult = nextResult2.elements();
    JsonNode nextResult3 = elementsResult.next();
    assertTrue(nextResult3 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals(
        "[ {\n"
            + "  \"dataproperty_id\" : null,\n"
            + "  \"dataproperty_name\" : null,\n"
            + "  \"dataproperty_type\" : \"\",\n"
            + "  \"dataproperty_value\" : \"false\"\n"
            + "} ]",
        nextResult2.toPrettyString());
    assertEquals(
        "{\n"
            + "  \"dataproperties\" : {\n"
            + "    \"items\" : [ {\n"
            + "      \"dataproperty_id\" : null,\n"
            + "      \"dataproperty_name\" : null,\n"
            + "      \"dataproperty_type\" : \"\",\n"
            + "      \"dataproperty_value\" : \"false\"\n"
            + "    } ]\n"
            + "  }\n"
            + "}",
        propertiesNode.toPrettyString());
    assertEquals(
        "{\n"
            + "  \"dataproperty_id\" : null,\n"
            + "  \"dataproperty_name\" : null,\n"
            + "  \"dataproperty_type\" : \"\",\n"
            + "  \"dataproperty_value\" : \"false\"\n"
            + "}",
        nextResult3.toPrettyString());
    assertEquals(
        "{\n"
            + "  \"items\" : [ {\n"
            + "    \"dataproperty_id\" : null,\n"
            + "    \"dataproperty_name\" : null,\n"
            + "    \"dataproperty_type\" : \"\",\n"
            + "    \"dataproperty_value\" : \"false\"\n"
            + "  } ]\n"
            + "}",
        nextResult.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#validateIfNodeIsTextual(JsonNode)}.
   *
   * <ul>
   *   <li>When Instance.
   *   <li>Then return {@link MissingNode}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#validateIfNodeIsTextual(JsonNode)}
   */
  @Test
  @DisplayName("Test validateIfNodeIsTextual(JsonNode); when Instance; then return MissingNode")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"JsonNode BpmnJsonConverterUtil.validateIfNodeIsTextual(JsonNode)"})
  void testValidateIfNodeIsTextual_whenInstance_thenReturnMissingNode() {
    // Arrange and Act
    JsonNode actualValidateIfNodeIsTextualResult =
        BpmnJsonConverterUtil.validateIfNodeIsTextual(MissingNode.getInstance());

    // Assert
    assertTrue(actualValidateIfNodeIsTextualResult instanceof MissingNode);
    assertTrue(actualValidateIfNodeIsTextualResult.traverse() instanceof TreeTraversingParser);
    assertEquals("", actualValidateIfNodeIsTextualResult.toPrettyString());
    assertEquals(JsonNodeType.MISSING, actualValidateIfNodeIsTextualResult.getNodeType());
    assertFalse(actualValidateIfNodeIsTextualResult.isValueNode());
    assertTrue(actualValidateIfNodeIsTextualResult.isMissingNode());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#validateIfNodeIsTextual(JsonNode)}.
   *
   * <ul>
   *   <li>When Instance.
   *   <li>Then return {@link NullNode}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#validateIfNodeIsTextual(JsonNode)}
   */
  @Test
  @DisplayName("Test validateIfNodeIsTextual(JsonNode); when Instance; then return NullNode")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"JsonNode BpmnJsonConverterUtil.validateIfNodeIsTextual(JsonNode)"})
  void testValidateIfNodeIsTextual_whenInstance_thenReturnNullNode() {
    // Arrange and Act
    JsonNode actualValidateIfNodeIsTextualResult =
        BpmnJsonConverterUtil.validateIfNodeIsTextual(NullNode.getInstance());

    // Assert
    assertTrue(actualValidateIfNodeIsTextualResult instanceof NullNode);
    assertTrue(actualValidateIfNodeIsTextualResult.traverse() instanceof TreeTraversingParser);
    assertEquals("null", actualValidateIfNodeIsTextualResult.toPrettyString());
    assertEquals(JsonNodeType.NULL, actualValidateIfNodeIsTextualResult.getNodeType());
    assertTrue(actualValidateIfNodeIsTextualResult.isNull());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#validateIfNodeIsTextual(JsonNode)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#validateIfNodeIsTextual(JsonNode)}
   */
  @Test
  @DisplayName("Test validateIfNodeIsTextual(JsonNode); when 'null'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"JsonNode BpmnJsonConverterUtil.validateIfNodeIsTextual(JsonNode)"})
  void testValidateIfNodeIsTextual_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(BpmnJsonConverterUtil.validateIfNodeIsTextual(null));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#validateIfNodeIsTextual(JsonNode)}.
   *
   * <ul>
   *   <li>When valueOf ten.
   *   <li>Then return {@link DoubleNode}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#validateIfNodeIsTextual(JsonNode)}
   */
  @Test
  @DisplayName("Test validateIfNodeIsTextual(JsonNode); when valueOf ten; then return DoubleNode")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"JsonNode BpmnJsonConverterUtil.validateIfNodeIsTextual(JsonNode)"})
  void testValidateIfNodeIsTextual_whenValueOfTen_thenReturnDoubleNode() {
    // Arrange and Act
    JsonNode actualValidateIfNodeIsTextualResult =
        BpmnJsonConverterUtil.validateIfNodeIsTextual(DoubleNode.valueOf(10.0d));

    // Assert
    assertTrue(actualValidateIfNodeIsTextualResult instanceof DoubleNode);
    assertTrue(actualValidateIfNodeIsTextualResult.traverse() instanceof TreeTraversingParser);
    assertEquals("10.0", actualValidateIfNodeIsTextualResult.toPrettyString());
    assertEquals(JsonNodeType.NUMBER, actualValidateIfNodeIsTextualResult.getNodeType());
    assertFalse(((DoubleNode) actualValidateIfNodeIsTextualResult).isNaN());
    assertTrue(actualValidateIfNodeIsTextualResult.isDouble());
    assertTrue(actualValidateIfNodeIsTextualResult.isFloatingPointNumber());
    assertTrue(actualValidateIfNodeIsTextualResult.isNumber());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getValueAsString(String, JsonNode)}.
   *
   * <ul>
   *   <li>When valueOf ten.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#getValueAsString(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getValueAsString(String, JsonNode); when valueOf ten; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String BpmnJsonConverterUtil.getValueAsString(String, JsonNode)"})
  void testGetValueAsString_whenValueOfTen_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(BpmnJsonConverterUtil.getValueAsString("Name", DoubleNode.valueOf(10.0d)));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getPropertyValueAsString(String, JsonNode)}.
   *
   * <ul>
   *   <li>When valueOf ten.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#getPropertyValueAsString(String, JsonNode)}
   */
  @Test
  @DisplayName(
      "Test getPropertyValueAsString(String, JsonNode); when valueOf ten; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String BpmnJsonConverterUtil.getPropertyValueAsString(String, JsonNode)"})
  void testGetPropertyValueAsString_whenValueOfTen_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(BpmnJsonConverterUtil.getPropertyValueAsString("Name", DoubleNode.valueOf(10.0d)));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getProperty(String, JsonNode)}.
   *
   * <ul>
   *   <li>When valueOf ten.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnJsonConverterUtil#getProperty(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getProperty(String, JsonNode); when valueOf ten; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"JsonNode BpmnJsonConverterUtil.getProperty(String, JsonNode)"})
  void testGetProperty_whenValueOfTen_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(BpmnJsonConverterUtil.getProperty("Name", DoubleNode.valueOf(10.0d)));
  }
}
