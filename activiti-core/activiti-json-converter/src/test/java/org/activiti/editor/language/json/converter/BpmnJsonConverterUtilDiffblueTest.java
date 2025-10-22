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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BigIntegerNode;
import com.fasterxml.jackson.databind.node.BinaryNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.ContainerNode;
import com.fasterxml.jackson.databind.node.DecimalNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
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
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.Signal;
import org.activiti.bpmn.model.UserTask;
import org.activiti.bpmn.model.ValuedDataObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BpmnJsonConverterUtilDiffblueTest {
  /**
   * Test {@link BpmnJsonConverterUtil#createChildShape(String, String, double, double, double, double)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then iterator next return {@link NullNode}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#createChildShape(String, String, double, double, double, double)}
   */
  @Test
  @DisplayName("Test createChildShape(String, String, double, double, double, double); when 'null'; then iterator next return NullNode")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "ObjectNode BpmnJsonConverterUtil.createChildShape(String, String, double, double, double, double)"})
  void testCreateChildShape_whenNull_thenIteratorNextReturnNullNode() {
    // Arrange and Act
    ObjectNode actualCreateChildShapeResult = BpmnJsonConverterUtil.createChildShape(null, null, 10.0d, 10.0d, 10.0d,
        10.0d);

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
    assertEquals("{\n" + "  \"bounds\" : {\n" + "    \"lowerRight\" : {\n" + "      \"x\" : 10.0,\n"
        + "      \"y\" : 10.0\n" + "    },\n" + "    \"upperLeft\" : {\n" + "      \"x\" : 10.0,\n"
        + "      \"y\" : 10.0\n" + "    }\n" + "  },\n" + "  \"resourceId\" : null,\n" + "  \"childShapes\" : [ ],\n"
        + "  \"stencil\" : {\n" + "    \"id\" : null\n" + "  }\n" + "}", actualCreateChildShapeResult.toPrettyString());
    assertFalse(actualHasNextResult);
  }

  /**
   * Test {@link BpmnJsonConverterUtil#createResourceNode(String)}.
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#createResourceNode(String)}
   */
  @Test
  @DisplayName("Test createResourceNode(String)")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return iterator next toPrettyString is {@code "42"}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#createResourceNode(String)}
   */
  @Test
  @DisplayName("Test createResourceNode(String); when '42'; then return iterator next toPrettyString is '\"42\"'")
  @Tag("MaintainedByDiffblue")
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
    assertEquals("{\n  \"resourceId\" : \"42\"\n}", actualCreateResourceNodeResult.toPrettyString());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#createResourceNode(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then iterator next return {@link NullNode}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#createResourceNode(String)}
   */
  @Test
  @DisplayName("Test createResourceNode(String); when 'null'; then iterator next return NullNode")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#getStencilId(JsonNode)}
   */
  @Test
  @DisplayName("Test getStencilId(JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BpmnJsonConverterUtil.getStencilId(JsonNode)"})
  void testGetStencilId_whenArrayNodeWithNfIsWithExactBigDecimalsTrue_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(BpmnJsonConverterUtil.getStencilId(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getStencilId(JsonNode)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#getStencilId(JsonNode)}
   */
  @Test
  @DisplayName("Test getStencilId(JsonNode); when Instance; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BpmnJsonConverterUtil.getStencilId(JsonNode)"})
  void testGetStencilId_whenInstance_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(BpmnJsonConverterUtil.getStencilId(MissingNode.getInstance()));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getElementId(JsonNode)}.
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#getElementId(JsonNode)}
   */
  @Test
  @DisplayName("Test getElementId(JsonNode)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BpmnJsonConverterUtil.getElementId(JsonNode)"})
  void testGetElementId() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    String actualElementId = BpmnJsonConverterUtil.getElementId(objectNode);

    // Assert
    verify(objectNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode).get(eq("overrideid"));
    verify(arrayNode).asText();
    assertEquals("As Text", actualElementId);
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getElementId(JsonNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   *   <li>Then return {@code As Text}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#getElementId(JsonNode)}
   */
  @Test
  @DisplayName("Test getElementId(JsonNode); given ArrayNode get(String) return Instance; then return 'As Text'")
  @Tag("MaintainedByDiffblue")
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
    verify(arrayNode).get(eq("overrideid"));
    verify(arrayNode).asText();
    assertEquals("As Text", actualElementId);
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getElementId(JsonNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   *   <li>Then return {@code As Text}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#getElementId(JsonNode)}
   */
  @Test
  @DisplayName("Test getElementId(JsonNode); given ArrayNode get(String) return Instance; then return 'As Text'")
  @Tag("MaintainedByDiffblue")
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
    verify(arrayNode).get(eq("overrideid"));
    verify(arrayNode).asText();
    assertEquals("As Text", actualElementId);
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getElementId(JsonNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link JsonNode#isNull()} return {@code true}.</li>
   *   <li>Then calls {@link JsonNode#isNull()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#getElementId(JsonNode)}
   */
  @Test
  @DisplayName("Test getElementId(JsonNode); given ArrayNode isNull() return 'true'; then calls isNull()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BpmnJsonConverterUtil.getElementId(JsonNode)"})
  void testGetElementId_givenArrayNodeIsNullReturnTrue_thenCallsIsNull() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");
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
    verify(arrayNode2).get(eq("overrideid"));
    verify(arrayNode2).asText();
    assertEquals("As Text", actualElementId);
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getElementId(JsonNode)}.
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#getElementId(JsonNode)}
   */
  @Test
  @DisplayName("Test getElementId(JsonNode); given ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BpmnJsonConverterUtil.getElementId(JsonNode)"})
  void testGetElementId_givenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Act
    String actualElementId = BpmnJsonConverterUtil.getElementId(objectNode);

    // Assert
    verify(objectNode, atLeast(1)).get(Mockito.<String>any());
    assertEquals("", actualElementId);
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getElementId(JsonNode)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#getElementId(JsonNode)}
   */
  @Test
  @DisplayName("Test getElementId(JsonNode); given Instance; when ArrayNode get(String) return Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BpmnJsonConverterUtil.getElementId(JsonNode)"})
  void testGetElementId_givenInstance_whenArrayNodeGetReturnInstance() {
    // Arrange
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    // Act
    String actualElementId = BpmnJsonConverterUtil.getElementId(objectNode);

    // Assert
    verify(objectNode, atLeast(1)).get(Mockito.<String>any());
    assertEquals("", actualElementId);
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getElementId(JsonNode)}.
   * <ul>
   *   <li>Then return {@code 1}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#getElementId(JsonNode)}
   */
  @Test
  @DisplayName("Test getElementId(JsonNode); then return '1'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BpmnJsonConverterUtil.getElementId(JsonNode)"})
  void testGetElementId_thenReturn1() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    String actualElementId = BpmnJsonConverterUtil.getElementId(objectNode);

    // Assert
    verify(arrayNode, atLeast(1)).get(eq("overrideid"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    assertEquals("1", actualElementId);
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertMessagesToJson(BpmnModel, ObjectNode)} with {@code bpmnModel}, {@code propertiesNode}.
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertMessagesToJson(BpmnModel, ObjectNode)}
   */
  @Test
  @DisplayName("Test convertMessagesToJson(BpmnModel, ObjectNode) with 'bpmnModel', 'propertiesNode'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertMessagesToJson(BpmnModel, ObjectNode)"})
  void testConvertMessagesToJsonWithBpmnModelPropertiesNode() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

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
   * Test {@link BpmnJsonConverterUtil#convertMessagesToJson(Collection, ObjectNode)} with {@code messages}, {@code propertiesNode}.
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertMessagesToJson(Collection, ObjectNode)}
   */
  @Test
  @DisplayName("Test convertMessagesToJson(Collection, ObjectNode) with 'messages', 'propertiesNode'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertMessagesToJson(Collection, ObjectNode)"})
  void testConvertMessagesToJsonWithMessagesPropertiesNode() {
    // Arrange
    ArrayList<Message> messages = new ArrayList<>();
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

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
   * Test {@link BpmnJsonConverterUtil#convertMessagesToJson(Collection, ObjectNode)} with {@code messages}, {@code propertiesNode}.
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertMessagesToJson(Collection, ObjectNode)}
   */
  @Test
  @DisplayName("Test convertMessagesToJson(Collection, ObjectNode) with 'messages', 'propertiesNode'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertMessagesToJson(Collection, ObjectNode)"})
  void testConvertMessagesToJsonWithMessagesPropertiesNode2() {
    // Arrange
    Message message = new Message("42", "Name", "Item Ref");
    message.setId(null);
    message.setItemRef(null);
    message.setName(null);

    LinkedHashSet<Message> messages = new LinkedHashSet<>();
    messages.add(message);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    BpmnJsonConverterUtil.convertMessagesToJson(messages, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof ArrayNode);
    Iterator<JsonNode> elementsResult = nextResult.elements();
    JsonNode nextResult2 = elementsResult.next();
    assertTrue(nextResult2 instanceof ObjectNode);
    assertEquals("[ {\n  \"message_id\" : null,\n  \"message_name\" : null,\n  \"message_item_ref\" : null\n} ]",
        nextResult.toPrettyString());
    assertEquals("{\n  \"message_id\" : null,\n  \"message_name\" : null,\n  \"message_item_ref\" : null\n}",
        nextResult2.toPrettyString());
    assertEquals("{\n" + "  \"messages\" : [ {\n" + "    \"message_id\" : null,\n" + "    \"message_name\" : null,\n"
        + "    \"message_item_ref\" : null\n" + "  } ]\n" + "}", propertiesNode.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertTrue(nextResult2.iterator().hasNext());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertMessagesToJson(Collection, ObjectNode)} with {@code messages}, {@code propertiesNode}.
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertMessagesToJson(Collection, ObjectNode)}
   */
  @Test
  @DisplayName("Test convertMessagesToJson(Collection, ObjectNode) with 'messages', 'propertiesNode'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertMessagesToJson(Collection, ObjectNode)"})
  void testConvertMessagesToJsonWithMessagesPropertiesNode3() {
    // Arrange
    ArrayList<Message> messages = new ArrayList<>();
    Builder builderResult = Message.builder();
    Builder attributesResult = builderResult.attributes(new HashMap<>());
    Message buildResult = attributesResult.extensionElements(new HashMap<>())
        .id("")
        .itemRef("Item Ref")
        .name("Name")
        .xmlColumnNumber(10)
        .xmlRowNumber(10)
        .build();
    messages.add(buildResult);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

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
    assertTrue(iteratorResult2.next() instanceof TextNode);
    assertEquals(
        "[ {\n  \"message_id\" : \"\",\n  \"message_name\" : \"Name\",\n  \"message_item_ref\" : \"Item Ref\"\n} ]",
        nextResult.toPrettyString());
    assertEquals(
        "{\n  \"message_id\" : \"\",\n  \"message_name\" : \"Name\",\n  \"message_item_ref\" : \"Item Ref\"\n}",
        nextResult2.toPrettyString());
    assertEquals("{\n" + "  \"messages\" : [ {\n" + "    \"message_id\" : \"\",\n"
        + "    \"message_name\" : \"Name\",\n" + "    \"message_item_ref\" : \"Item Ref\"\n" + "  } ]\n" + "}",
        propertiesNode.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertTrue(iteratorResult2.hasNext());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertListenersToJson(List, boolean, ObjectNode)}.
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertListenersToJson(List, boolean, ObjectNode)}
   */
  @Test
  @DisplayName("Test convertListenersToJson(List, boolean, ObjectNode)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertListenersToJson(List, boolean, ObjectNode)"})
  void testConvertListenersToJson() {
    // Arrange
    ArrayList<ActivitiListener> listeners = new ArrayList<>();
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

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
    assertEquals("{\n  \"executionlisteners\" : {\n    \"executionListeners\" : [ ]\n  }\n}",
        propertiesNode.toPrettyString());
    assertEquals(0, nextResult2.size());
    assertFalse(nextResult2.elements().hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(nextResult2.iterator().hasNext());
    assertTrue(nextResult2.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertListenersToJson(List, boolean, ObjectNode)}.
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertListenersToJson(List, boolean, ObjectNode)}
   */
  @Test
  @DisplayName("Test convertListenersToJson(List, boolean, ObjectNode)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertListenersToJson(List, boolean, ObjectNode)"})
  void testConvertListenersToJson2() {
    // Arrange
    ArrayList<ActivitiListener> listeners = new ArrayList<>();
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    BpmnJsonConverterUtil.convertListenersToJson(listeners, false, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ArrayNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals("[ ]", nextResult2.toPrettyString());
    assertEquals("{\n  \"taskListeners\" : [ ]\n}", nextResult.toPrettyString());
    assertEquals("{\n  \"tasklisteners\" : {\n    \"taskListeners\" : [ ]\n  }\n}", propertiesNode.toPrettyString());
    assertEquals(0, nextResult2.size());
    assertFalse(nextResult2.elements().hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(nextResult2.iterator().hasNext());
    assertTrue(nextResult2.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertListenersToJson(List, boolean, ObjectNode)}.
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertListenersToJson(List, boolean, ObjectNode)}
   */
  @Test
  @DisplayName("Test convertListenersToJson(List, boolean, ObjectNode)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertListenersToJson(List, boolean, ObjectNode)"})
  void testConvertListenersToJson3() {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setEvent("executionlisteners");

    ArrayList<ActivitiListener> listeners = new ArrayList<>();
    listeners.add(activitiListener);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    BpmnJsonConverterUtil.convertListenersToJson(listeners, true, propertiesNode);

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
    Iterator<JsonNode> iteratorResult3 = nextResult3.iterator();
    assertTrue(iteratorResult3.next() instanceof TextNode);
    assertEquals("[ {\n  \"event\" : \"executionlisteners\"\n} ]", nextResult2.toPrettyString());
    assertEquals("{\n  \"event\" : \"executionlisteners\"\n}", nextResult3.toPrettyString());
    assertEquals("{\n  \"executionListeners\" : [ {\n    \"event\" : \"executionlisteners\"\n  } ]\n}",
        nextResult.toPrettyString());
    assertEquals(
        "{\n" + "  \"executionlisteners\" : {\n" + "    \"executionListeners\" : [ {\n"
            + "      \"event\" : \"executionlisteners\"\n" + "    } ]\n" + "  }\n" + "}",
        propertiesNode.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult3.hasNext());
    assertFalse(iteratorResult2.hasNext());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertEventListenersToJson(List, ObjectNode)}.
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertEventListenersToJson(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test convertEventListenersToJson(List, ObjectNode)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertEventListenersToJson(List, ObjectNode)"})
  void testConvertEventListenersToJson() {
    // Arrange
    ArrayList<EventListener> listeners = new ArrayList<>();
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

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
    assertEquals("{\n  \"eventlisteners\" : {\n    \"eventListeners\" : [ ]\n  }\n}", propertiesNode.toPrettyString());
    assertEquals(0, nextResult2.size());
    assertFalse(nextResult2.elements().hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(nextResult2.iterator().hasNext());
    assertTrue(nextResult2.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertSignalDefinitionsToJson(BpmnModel, ObjectNode)}.
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertSignalDefinitionsToJson(BpmnModel, ObjectNode)}
   */
  @Test
  @DisplayName("Test convertSignalDefinitionsToJson(BpmnModel, ObjectNode)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertSignalDefinitionsToJson(BpmnModel, ObjectNode)"})
  void testConvertSignalDefinitionsToJson() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

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
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertSignalDefinitionsToJson(BpmnModel, ObjectNode)}
   */
  @Test
  @DisplayName("Test convertSignalDefinitionsToJson(BpmnModel, ObjectNode)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertSignalDefinitionsToJson(BpmnModel, ObjectNode)"})
  void testConvertSignalDefinitionsToJson2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(new Signal("42", "signaldefinitions"));
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

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
    JsonNode nextResult3 = iteratorResult2.next();
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("[ {\n  \"id\" : \"42\",\n  \"name\" : \"signaldefinitions\",\n  \"scope\" : null\n} ]",
        nextResult.toPrettyString());
    assertEquals("\"42\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"id\" : \"42\",\n  \"name\" : \"signaldefinitions\",\n  \"scope\" : null\n}",
        nextResult2.toPrettyString());
    assertEquals("{\n" + "  \"signaldefinitions\" : [ {\n" + "    \"id\" : \"42\",\n"
        + "    \"name\" : \"signaldefinitions\",\n" + "    \"scope\" : null\n" + "  } ]\n" + "}",
        propertiesNode.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertTrue(iteratorResult2.hasNext());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertSignalDefinitionsToJson(BpmnModel, ObjectNode)}.
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertSignalDefinitionsToJson(BpmnModel, ObjectNode)}
   */
  @Test
  @DisplayName("Test convertSignalDefinitionsToJson(BpmnModel, ObjectNode)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertSignalDefinitionsToJson(BpmnModel, ObjectNode)"})
  void testConvertSignalDefinitionsToJson3() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addSignal(new Signal("", "signaldefinitions"));
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

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
    JsonNode nextResult3 = iteratorResult2.next();
    assertTrue(nextResult3 instanceof TextNode);
    assertEquals("[ {\n  \"id\" : \"\",\n  \"name\" : \"signaldefinitions\",\n  \"scope\" : null\n} ]",
        nextResult.toPrettyString());
    assertEquals("\"\"", nextResult3.toPrettyString());
    assertEquals("{\n  \"id\" : \"\",\n  \"name\" : \"signaldefinitions\",\n  \"scope\" : null\n}",
        nextResult2.toPrettyString());
    assertEquals("{\n" + "  \"signaldefinitions\" : [ {\n" + "    \"id\" : \"\",\n"
        + "    \"name\" : \"signaldefinitions\",\n" + "    \"scope\" : null\n" + "  } ]\n" + "}",
        propertiesNode.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertTrue(iteratorResult2.hasNext());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add Instance.</li>
   *   <li>Then calls {@link JsonNode#asText()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToListeners(JsonNode, BaseElement); given ArrayList() add Instance; then calls asText()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToListeners(JsonNode, BaseElement)"})
  void testConvertJsonToListeners_givenArrayListAddInstance_thenCallsAsText() {
    // Arrange
    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(MissingNode.getInstance());
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("As Text");
    when(jsonNode.isNull()).thenReturn(false);
    when(jsonNode.isTextual()).thenReturn(true);
    when(jsonNode.iterator()).thenReturn(iteratorResult);
    JsonNode jsonNode2 = mock(JsonNode.class);
    when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);
    when(jsonNode2.isNull()).thenReturn(true);
    when(jsonNode2.isTextual()).thenReturn(true);
    JsonNode jsonNode3 = mock(JsonNode.class);
    when(jsonNode3.get(Mockito.<String>any())).thenReturn(jsonNode2);
    JsonNode objectNode = mock(JsonNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(jsonNode3);

    // Act
    BpmnJsonConverterUtil.convertJsonToListeners(objectNode, new ActivitiListener());

    // Assert
    verify(jsonNode, atLeast(1)).asText();
    verify(jsonNode2).get(eq("executionListeners"));
    verify(jsonNode3).get(eq("executionlisteners"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(jsonNode2).isNull();
    verify(jsonNode).isNull();
    verify(jsonNode).isTextual();
    verify(jsonNode).iterator();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>When {@link JsonNode} {@link JsonNode#get(String)} return Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToListeners(JsonNode, BaseElement); given Instance; when JsonNode get(String) return Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToListeners(JsonNode, BaseElement)"})
  void testConvertJsonToListeners_givenInstance_whenJsonNodeGetReturnInstance() {
    // Arrange
    JsonNode objectNode = mock(JsonNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    // Act
    BpmnJsonConverterUtil.convertJsonToListeners(objectNode, new ActivitiListener());

    // Assert
    verify(objectNode, atLeast(1)).get(eq("properties"));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link JsonNode} {@link JsonNode#asText()} return {@code 42}.</li>
   *   <li>Then calls {@link JsonNode#asText()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToListeners(JsonNode, BaseElement); given JsonNode asText() return '42'; then calls asText()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToListeners(JsonNode, BaseElement)"})
  void testConvertJsonToListeners_givenJsonNodeAsTextReturn42_thenCallsAsText() {
    // Arrange
    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("42");
    when(jsonNode.isNull()).thenReturn(false);
    when(jsonNode.isTextual()).thenReturn(true);
    JsonNode jsonNode2 = mock(JsonNode.class);
    when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);
    when(jsonNode2.isNull()).thenReturn(true);
    when(jsonNode2.isTextual()).thenReturn(true);
    JsonNode jsonNode3 = mock(JsonNode.class);
    when(jsonNode3.get(Mockito.<String>any())).thenReturn(jsonNode2);
    JsonNode objectNode = mock(JsonNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(jsonNode3);

    // Act
    BpmnJsonConverterUtil.convertJsonToListeners(objectNode, new ActivitiListener());

    // Assert
    verify(jsonNode, atLeast(1)).asText();
    verify(jsonNode2).get(eq("executionListeners"));
    verify(jsonNode3).get(eq("executionlisteners"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(jsonNode2).isNull();
    verify(jsonNode).isNull();
    verify(jsonNode).isTextual();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link JsonNode} {@link JsonNode#asText()} return {@code As Text}.</li>
   *   <li>Then calls {@link JsonNode#asText()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToListeners(JsonNode, BaseElement); given JsonNode asText() return 'As Text'; then calls asText()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToListeners(JsonNode, BaseElement)"})
  void testConvertJsonToListeners_givenJsonNodeAsTextReturnAsText_thenCallsAsText() {
    // Arrange
    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("As Text");
    when(jsonNode.isNull()).thenReturn(false);
    when(jsonNode.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(jsonNode.iterator()).thenReturn(jsonNodeList.iterator());
    JsonNode jsonNode2 = mock(JsonNode.class);
    when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);
    when(jsonNode2.isNull()).thenReturn(true);
    when(jsonNode2.isTextual()).thenReturn(true);
    JsonNode jsonNode3 = mock(JsonNode.class);
    when(jsonNode3.get(Mockito.<String>any())).thenReturn(jsonNode2);
    JsonNode objectNode = mock(JsonNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(jsonNode3);

    // Act
    BpmnJsonConverterUtil.convertJsonToListeners(objectNode, new ActivitiListener());

    // Assert
    verify(jsonNode, atLeast(1)).asText();
    verify(jsonNode2).get(eq("executionListeners"));
    verify(jsonNode3).get(eq("executionlisteners"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(jsonNode2).isNull();
    verify(jsonNode).isNull();
    verify(jsonNode).isTextual();
    verify(jsonNode).iterator();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link JsonNode} {@link JsonNode#asText()} return {@code As Text}.</li>
   *   <li>Then calls {@link JsonNode#asText()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToListeners(JsonNode, BaseElement); given JsonNode asText() return 'As Text'; then calls asText()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToListeners(JsonNode, BaseElement)"})
  void testConvertJsonToListeners_givenJsonNodeAsTextReturnAsText_thenCallsAsText2() {
    // Arrange
    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("As Text");
    when(jsonNode.isNull()).thenReturn(false);
    when(jsonNode.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(jsonNode.iterator()).thenReturn(jsonNodeList.iterator());
    JsonNode jsonNode2 = mock(JsonNode.class);
    when(jsonNode2.asText()).thenReturn("As Text");
    when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);
    when(jsonNode2.isNull()).thenReturn(false);
    when(jsonNode2.isTextual()).thenReturn(true);
    JsonNode jsonNode3 = mock(JsonNode.class);
    when(jsonNode3.get(Mockito.<String>any())).thenReturn(jsonNode2);
    JsonNode objectNode = mock(JsonNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(jsonNode3);

    // Act
    BpmnJsonConverterUtil.convertJsonToListeners(objectNode, new ActivitiListener());

    // Assert
    verify(jsonNode2, atLeast(1)).asText();
    verify(jsonNode, atLeast(1)).asText();
    verify(jsonNode2).get(eq("executionListeners"));
    verify(jsonNode3).get(eq("executionlisteners"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(jsonNode2).isNull();
    verify(jsonNode).isNull();
    verify(jsonNode2).isTextual();
    verify(jsonNode).isTextual();
    verify(jsonNode).iterator();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link JsonNode} {@link JsonNode#asText()} return {@code As Text}.</li>
   *   <li>When {@link UserTask} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToListeners(JsonNode, BaseElement); given JsonNode asText() return 'As Text'; when UserTask (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToListeners(JsonNode, BaseElement)"})
  void testConvertJsonToListeners_givenJsonNodeAsTextReturnAsText_whenUserTask() {
    // Arrange
    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("As Text");
    when(jsonNode.isNull()).thenReturn(false);
    when(jsonNode.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(jsonNode.iterator()).thenReturn(jsonNodeList.iterator());
    JsonNode jsonNode2 = mock(JsonNode.class);
    when(jsonNode2.asText()).thenReturn("As Text");
    when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);
    when(jsonNode2.isNull()).thenReturn(false);
    when(jsonNode2.isTextual()).thenReturn(true);
    JsonNode jsonNode3 = mock(JsonNode.class);
    when(jsonNode3.get(Mockito.<String>any())).thenReturn(jsonNode2);
    JsonNode objectNode = mock(JsonNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(jsonNode3);

    // Act
    BpmnJsonConverterUtil.convertJsonToListeners(objectNode, new UserTask());

    // Assert
    verify(jsonNode2, atLeast(1)).asText();
    verify(jsonNode, atLeast(1)).asText();
    verify(jsonNode3, atLeast(1)).get(Mockito.<String>any());
    verify(jsonNode2, atLeast(1)).get(Mockito.<String>any());
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(jsonNode2, atLeast(1)).isNull();
    verify(jsonNode, atLeast(1)).isNull();
    verify(jsonNode2, atLeast(1)).isTextual();
    verify(jsonNode, atLeast(1)).isTextual();
    verify(jsonNode, atLeast(1)).iterator();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link JsonNode} {@link JsonNode#asText()} return empty string.</li>
   *   <li>Then calls {@link JsonNode#asText()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToListeners(JsonNode, BaseElement); given JsonNode asText() return empty string; then calls asText()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToListeners(JsonNode, BaseElement)"})
  void testConvertJsonToListeners_givenJsonNodeAsTextReturnEmptyString_thenCallsAsText() {
    // Arrange
    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("");
    when(jsonNode.isNull()).thenReturn(false);
    when(jsonNode.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(jsonNode.iterator()).thenReturn(jsonNodeList.iterator());
    JsonNode jsonNode2 = mock(JsonNode.class);
    when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);
    when(jsonNode2.isNull()).thenReturn(true);
    when(jsonNode2.isTextual()).thenReturn(true);
    JsonNode jsonNode3 = mock(JsonNode.class);
    when(jsonNode3.get(Mockito.<String>any())).thenReturn(jsonNode2);
    JsonNode objectNode = mock(JsonNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(jsonNode3);

    // Act
    BpmnJsonConverterUtil.convertJsonToListeners(objectNode, new ActivitiListener());

    // Assert
    verify(jsonNode).asText();
    verify(jsonNode2).get(eq("executionListeners"));
    verify(jsonNode3).get(eq("executionlisteners"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(jsonNode2).isNull();
    verify(jsonNode).isNull();
    verify(jsonNode).isTextual();
    verify(jsonNode).iterator();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link JsonNode} {@link JsonNode#asText()} return {@code null}.</li>
   *   <li>Then calls {@link JsonNode#asText()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToListeners(JsonNode, BaseElement); given JsonNode asText() return 'null'; then calls asText()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToListeners(JsonNode, BaseElement)"})
  void testConvertJsonToListeners_givenJsonNodeAsTextReturnNull_thenCallsAsText() {
    // Arrange
    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn(null);
    when(jsonNode.isNull()).thenReturn(false);
    when(jsonNode.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(jsonNode.iterator()).thenReturn(jsonNodeList.iterator());
    JsonNode jsonNode2 = mock(JsonNode.class);
    when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);
    when(jsonNode2.isNull()).thenReturn(true);
    when(jsonNode2.isTextual()).thenReturn(true);
    JsonNode jsonNode3 = mock(JsonNode.class);
    when(jsonNode3.get(Mockito.<String>any())).thenReturn(jsonNode2);
    JsonNode objectNode = mock(JsonNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(jsonNode3);

    // Act
    BpmnJsonConverterUtil.convertJsonToListeners(objectNode, new ActivitiListener());

    // Assert
    verify(jsonNode).asText();
    verify(jsonNode2).get(eq("executionListeners"));
    verify(jsonNode3).get(eq("executionlisteners"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(jsonNode2).isNull();
    verify(jsonNode).isNull();
    verify(jsonNode).isTextual();
    verify(jsonNode).iterator();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link JsonNode} {@link JsonNode#get(String)} return False.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToListeners(JsonNode, BaseElement); given JsonNode get(String) return False")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToListeners(JsonNode, BaseElement)"})
  void testConvertJsonToListeners_givenJsonNodeGetReturnFalse() {
    // Arrange
    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.get(Mockito.<String>any())).thenReturn(BooleanNode.getFalse());
    JsonNode objectNode = mock(JsonNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(jsonNode);

    // Act
    BpmnJsonConverterUtil.convertJsonToListeners(objectNode, new ActivitiListener());

    // Assert
    verify(jsonNode).get(eq("executionlisteners"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link JsonNode} {@link JsonNode#get(String)} return Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToListeners(JsonNode, BaseElement); given JsonNode get(String) return Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToListeners(JsonNode, BaseElement)"})
  void testConvertJsonToListeners_givenJsonNodeGetReturnInstance() {
    // Arrange
    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    JsonNode objectNode = mock(JsonNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(jsonNode);

    // Act
    BpmnJsonConverterUtil.convertJsonToListeners(objectNode, new ActivitiListener());

    // Assert
    verify(jsonNode).get(eq("executionlisteners"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link JsonNode} {@link JsonNode#get(String)} return Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToListeners(JsonNode, BaseElement); given JsonNode get(String) return Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToListeners(JsonNode, BaseElement)"})
  void testConvertJsonToListeners_givenJsonNodeGetReturnInstance2() {
    // Arrange
    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.get(Mockito.<String>any())).thenReturn(NullNode.getInstance());
    JsonNode objectNode = mock(JsonNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(jsonNode);

    // Act
    BpmnJsonConverterUtil.convertJsonToListeners(objectNode, new ActivitiListener());

    // Assert
    verify(jsonNode).get(eq("executionlisteners"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link JsonNode} {@link JsonNode#get(String)} return Instance.</li>
   *   <li>Then calls {@link JsonNode#isNull()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToListeners(JsonNode, BaseElement); given JsonNode get(String) return Instance; then calls isNull()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToListeners(JsonNode, BaseElement)"})
  void testConvertJsonToListeners_givenJsonNodeGetReturnInstance_thenCallsIsNull() {
    // Arrange
    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(jsonNode.isNull()).thenReturn(true);
    JsonNode jsonNode2 = mock(JsonNode.class);
    when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);
    JsonNode objectNode = mock(JsonNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(jsonNode2);

    // Act
    BpmnJsonConverterUtil.convertJsonToListeners(objectNode, new ActivitiListener());

    // Assert
    verify(jsonNode).get(eq("executionListeners"));
    verify(jsonNode2).get(eq("executionlisteners"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(jsonNode).isNull();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link JsonNode} {@link JsonNode#isNull()} return {@code true}.</li>
   *   <li>Then calls {@link JsonNode#iterator()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToListeners(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToListeners(JsonNode, BaseElement); given JsonNode isNull() return 'true'; then calls iterator()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToListeners(JsonNode, BaseElement)"})
  void testConvertJsonToListeners_givenJsonNodeIsNullReturnTrue_thenCallsIterator() {
    // Arrange
    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.isNull()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(jsonNode.iterator()).thenReturn(jsonNodeList.iterator());
    JsonNode jsonNode2 = mock(JsonNode.class);
    when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);
    when(jsonNode2.isNull()).thenReturn(true);
    JsonNode jsonNode3 = mock(JsonNode.class);
    when(jsonNode3.get(Mockito.<String>any())).thenReturn(jsonNode2);
    JsonNode objectNode = mock(JsonNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(jsonNode3);

    // Act
    BpmnJsonConverterUtil.convertJsonToListeners(objectNode, new ActivitiListener());

    // Assert
    verify(jsonNode2).get(eq("executionListeners"));
    verify(jsonNode3).get(eq("executionlisteners"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(jsonNode2).isNull();
    verify(jsonNode).isNull();
    verify(jsonNode).iterator();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToMessages(JsonNode, BpmnModel)"})
  void testConvertJsonToMessages() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert that nothing has changed
    verify(arrayNode).get(eq("messagedefinitions"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToMessages(JsonNode, BpmnModel)"})
  void testConvertJsonToMessages2() {
    // Arrange
    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
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
    verify(arrayNode2).get(eq("messagedefinitions"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(arrayNode, atLeast(1)).asText();
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToMessages(JsonNode, BpmnModel)"})
  void testConvertJsonToMessages3() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

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

    // Assert that nothing has changed
    verify(arrayNode2).isNull();
    verify(arrayNode2).isTextual();
    verify(arrayNode2).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3).get(eq("messagedefinitions"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(arrayNode2, atLeast(1)).asText();
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add Instance.</li>
   *   <li>Then calls {@link JsonNode#isTextual()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel); given ArrayList() add Instance; then calls isTextual()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToMessages(JsonNode, BpmnModel)"})
  void testConvertJsonToMessages_givenArrayListAddInstance_thenCallsIsTextual() {
    // Arrange
    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(MissingNode.getInstance());
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
    verify(arrayNode2).get(eq("messagedefinitions"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(arrayNode, atLeast(1)).asText();
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ContainerNode#asText()} return {@code 42}.</li>
   *   <li>Then calls {@link JsonNode#isTextual()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel); given ArrayNode asText() return '42'; then calls isTextual()")
  @Tag("MaintainedByDiffblue")
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
    verify(arrayNode2).get(eq("messagedefinitions"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(arrayNode, atLeast(1)).asText();
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ContainerNode#asText()} return {@code As Text}.</li>
   *   <li>Then calls {@link JsonNode#isTextual()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel); given ArrayNode asText() return 'As Text'; then calls isTextual()")
  @Tag("MaintainedByDiffblue")
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
    verify(arrayNode2).get(eq("messagedefinitions"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(arrayNode, atLeast(1)).asText();
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ContainerNode#asText()} return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel); given ArrayNode asText() return empty string")
  @Tag("MaintainedByDiffblue")
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
    verify(arrayNode2).get(eq("messagedefinitions"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(arrayNode).asText();
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ContainerNode#asText()} return {@code null}.</li>
   *   <li>Then calls {@link JsonNode#isTextual()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel); given ArrayNode asText() return 'null'; then calls isTextual()")
  @Tag("MaintainedByDiffblue")
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
    verify(arrayNode2).get(eq("messagedefinitions"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(arrayNode).asText();
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return {@link BigIntegerNode#BigIntegerNode(BigInteger)} with v is valueOf one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel); given ArrayNode get(String) return BigIntegerNode(BigInteger) with v is valueOf one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToMessages(JsonNode, BpmnModel)"})
  void testConvertJsonToMessages_givenArrayNodeGetReturnBigIntegerNodeWithVIsValueOfOne() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert that nothing has changed
    verify(arrayNode).get(eq("messagedefinitions"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel); given ArrayNode get(String) return Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToMessages(JsonNode, BpmnModel)"})
  void testConvertJsonToMessages_givenArrayNodeGetReturnInstance() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert that nothing has changed
    verify(arrayNode).get(eq("messagedefinitions"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   *   <li>Then calls {@link JsonNode#isTextual()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel); given ArrayNode get(String) return Instance; then calls isTextual()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToMessages(JsonNode, BpmnModel)"})
  void testConvertJsonToMessages_givenArrayNodeGetReturnInstance_thenCallsIsTextual() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

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

    // Assert that nothing has changed
    verify(arrayNode2).isNull();
    verify(arrayNode2).isTextual();
    verify(arrayNode2).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3).get(eq("messagedefinitions"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(arrayNode2, atLeast(1)).asText();
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link JsonNode#isNull()} return {@code true}.</li>
   *   <li>Then calls {@link JsonNode#isTextual()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel); given ArrayNode isNull() return 'true'; then calls isTextual()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToMessages(JsonNode, BpmnModel)"})
  void testConvertJsonToMessages_givenArrayNodeIsNullReturnTrue_thenCallsIsTextual() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");
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
    verify(arrayNode4).get(eq("messagedefinitions"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(arrayNode3, atLeast(1)).asText();
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link JsonNode#isNull()} return {@code true}.</li>
   *   <li>Then calls {@link JsonNode#iterator()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel); given ArrayNode isNull() return 'true'; then calls iterator()")
  @Tag("MaintainedByDiffblue")
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
    verify(arrayNode2).get(eq("messagedefinitions"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel); given ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToMessages(JsonNode, BpmnModel)"})
  void testConvertJsonToMessages_givenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert that nothing has changed
    verify(objectNode, atLeast(1)).get(eq("properties"));
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel); given Instance; when ArrayNode get(String) return Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToMessages(JsonNode, BpmnModel)"})
  void testConvertJsonToMessages_givenInstance_whenArrayNodeGetReturnInstance() {
    // Arrange
    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert that nothing has changed
    verify(objectNode, atLeast(1)).get(eq("properties"));
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>Then {@link BpmnModel} (default constructor) Messages size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel); then BpmnModel (default constructor) Messages size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToMessages(JsonNode, BpmnModel)"})
  void testConvertJsonToMessages_thenBpmnModelMessagesSizeIsOne() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));

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
    verify(arrayNode3).get(eq("messagedefinitions"));
    verify(objectNode, atLeast(1)).get(eq("properties"));
    verify(arrayNode2, atLeast(1)).asText();
    assertEquals(1, element.getMessages().size());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToMessages(JsonNode, BpmnModel)"})
  void testConvertJsonToMessages_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    ArrayNode objectNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert that nothing has changed
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then {@link BpmnModel} (default constructor) Messages Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertJsonToMessages(JsonNode, BpmnModel); when Instance; then BpmnModel (default constructor) Messages Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertJsonToMessages(JsonNode, BpmnModel)"})
  void testConvertJsonToMessages_whenInstance_thenBpmnModelMessagesEmpty() {
    // Arrange
    MissingNode objectNode = MissingNode.getInstance();
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.convertJsonToMessages(objectNode, element);

    // Assert that nothing has changed
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseListeners(JsonNode, BaseElement, boolean)"})
  void testParseListeners() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.isTextual()).thenReturn(true);

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
    verify(arrayNode).get(eq("event"));
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseListeners(JsonNode, BaseElement, boolean)"})
  void testParseListeners2() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.asText()).thenReturn("As Text");
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);
    when(arrayNode2.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList2.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);
    SequenceFlow element = new SequenceFlow("Error converting textual node", "Error converting textual node");

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, element, true);

    // Assert
    verify(listenersNode).isNull();
    verify(arrayNode2).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(listenersNode).isTextual();
    verify(arrayNode2).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(listenersNode, atLeast(1)).asText();
    verify(arrayNode2, atLeast(1)).asText();
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
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayNode} {@link ContainerNode#asText()} return {@code 42}.</li>
   *   <li>Then calls {@link JsonNode#isTextual()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); given '42'; when ArrayNode asText() return '42'; then calls isTextual()")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); given ArrayList() add ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseListeners(JsonNode, BaseElement, boolean)"})
  void testParseListeners_givenArrayListAddArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
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
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add Instance.</li>
   *   <li>Then calls {@link ArrayNode#get(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); given ArrayList() add Instance; then calls get(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseListeners(JsonNode, BaseElement, boolean)"})
  void testParseListeners_givenArrayListAddInstance_thenCallsGet() {
    // Arrange
    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(MissingNode.getInstance());
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(true);
    when(arrayNode2.isTextual()).thenReturn(true);

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
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add Instance.</li>
   *   <li>When {@link ArrayNode} {@link ContainerNode#asText()} return {@code As Text}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); given ArrayList() add Instance; when ArrayNode asText() return 'As Text'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseListeners(JsonNode, BaseElement, boolean)"})
  void testParseListeners_givenArrayListAddInstance_whenArrayNodeAsTextReturnAsText() {
    // Arrange
    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(MissingNode.getInstance());
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
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return {@link BigIntegerNode#BigIntegerNode(BigInteger)} with v is valueOf one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); given ArrayNode get(String) return BigIntegerNode(BigInteger) with v is valueOf one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseListeners(JsonNode, BaseElement, boolean)"})
  void testParseListeners_givenArrayNodeGetReturnBigIntegerNodeWithVIsValueOfOne() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.isTextual()).thenReturn(true);

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
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   *   <li>Then calls {@link ArrayNode#get(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); given ArrayNode get(String) return Instance; then calls get(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseListeners(JsonNode, BaseElement, boolean)"})
  void testParseListeners_givenArrayNodeGetReturnInstance_thenCallsGet() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.isTextual()).thenReturn(true);

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
    verify(arrayNode).get(eq("event"));
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link JsonNode#isNull()} return {@code true}.</li>
   *   <li>Then calls {@link ArrayNode#get(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); given ArrayNode isNull() return 'true'; then calls get(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseListeners(JsonNode, BaseElement, boolean)"})
  void testParseListeners_givenArrayNodeIsNullReturnTrue_thenCallsGet() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(true);
    when(arrayNode2.isTextual()).thenReturn(true);

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
    verify(arrayNode2).get(eq("event"));
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link JsonNode#isNull()} return {@code true}.</li>
   *   <li>Then calls {@link ArrayNode#get(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); given ArrayNode isNull() return 'true'; then calls get(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseListeners(JsonNode, BaseElement, boolean)"})
  void testParseListeners_givenArrayNodeIsNullReturnTrue_thenCallsGet2() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(true);
    when(arrayNode2.isTextual()).thenReturn(true);

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
   * <ul>
   *   <li>Given {@link ArrayNode} {@link JsonNode#iterator()} return {@link ArrayList#ArrayList()} iterator.</li>
   *   <li>Then calls {@link ArrayNode#get(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); given ArrayNode iterator() return ArrayList() iterator; then calls get(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseListeners(JsonNode, BaseElement, boolean)"})
  void testParseListeners_givenArrayNodeIteratorReturnArrayListIterator_thenCallsGet() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.asText()).thenReturn("As Text");
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);
    when(arrayNode2.isTextual()).thenReturn(true);

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
    verify(arrayNode2).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(listenersNode, atLeast(1)).asText();
    verify(arrayNode2, atLeast(1)).asText();
    verify(arrayNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   * <ul>
   *   <li>Given {@code As Text}.</li>
   *   <li>When {@link ArrayNode} {@link ContainerNode#asText()} return {@code As Text}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); given 'As Text'; when ArrayNode asText() return 'As Text'")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>Given empty string.</li>
   *   <li>When {@link ArrayNode} {@link ContainerNode#asText()} return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); given empty string; when ArrayNode asText() return empty string")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link ArrayNode} {@link ContainerNode#asText()} return {@code null}.</li>
   *   <li>Then calls {@link JsonNode#isTextual()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); given 'null'; when ArrayNode asText() return 'null'; then calls isTextual()")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>Then {@link AdhocSubProcess} (default constructor) ExecutionListeners size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); then AdhocSubProcess (default constructor) ExecutionListeners size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseListeners(JsonNode, BaseElement, boolean)"})
  void testParseListeners_thenAdhocSubProcessExecutionListenersSizeIsOne() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.asText()).thenReturn("As Text");
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);
    when(arrayNode2.isTextual()).thenReturn(true);

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
    verify(arrayNode2).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(listenersNode, atLeast(1)).asText();
    verify(arrayNode2, atLeast(1)).asText();
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
   * <ul>
   *   <li>When {@link ArrayNode} {@link JsonNode#isNull()} return {@code true}.</li>
   *   <li>Then calls {@link JsonNode#iterator()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); when ArrayNode isNull() return 'true'; then calls iterator()")
  @Tag("MaintainedByDiffblue")
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
   * Test {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link Process} (default constructor) ExecutionListeners Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); when 'null'; then Process (default constructor) ExecutionListeners Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseListeners(JsonNode, BaseElement, boolean)"})
  void testParseListeners_whenNull_thenProcessExecutionListenersEmpty() {
    // Arrange
    Process element = new Process();

    // Act
    BpmnJsonConverterUtil.parseListeners(null, element, false);

    // Assert that nothing has changed
    assertTrue(element.getExecutionListeners().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}.
   * <ul>
   *   <li>When {@link Process} (default constructor).</li>
   *   <li>Then {@link Process} (default constructor) ExecutionListeners size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); when Process (default constructor); then Process (default constructor) ExecutionListeners size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseListeners(JsonNode, BaseElement, boolean)"})
  void testParseListeners_whenProcess_thenProcessExecutionListenersSizeIsOne() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.asText()).thenReturn("As Text");
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);
    when(arrayNode2.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList2.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);
    Process element = new Process();

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, element, true);

    // Assert
    verify(listenersNode).isNull();
    verify(arrayNode2).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(listenersNode).isTextual();
    verify(arrayNode2).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(listenersNode, atLeast(1)).asText();
    verify(arrayNode2, atLeast(1)).asText();
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
   * <ul>
   *   <li>When {@link UserTask} (default constructor).</li>
   *   <li>Then {@link UserTask} (default constructor) ExecutionListeners size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); when UserTask (default constructor); then UserTask (default constructor) ExecutionListeners size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseListeners(JsonNode, BaseElement, boolean)"})
  void testParseListeners_whenUserTask_thenUserTaskExecutionListenersSizeIsOne() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.asText()).thenReturn("As Text");
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);
    when(arrayNode2.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList2.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);
    UserTask element = new UserTask();

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, element, false);

    // Assert
    verify(listenersNode).isNull();
    verify(arrayNode2).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(listenersNode).isTextual();
    verify(arrayNode2).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(listenersNode, atLeast(1)).asText();
    verify(arrayNode2, atLeast(1)).asText();
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
   * <ul>
   *   <li>When {@link UserTask} (default constructor).</li>
   *   <li>Then {@link UserTask} (default constructor) TaskListeners size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseListeners(JsonNode, BaseElement, boolean)}
   */
  @Test
  @DisplayName("Test parseListeners(JsonNode, BaseElement, boolean); when UserTask (default constructor); then UserTask (default constructor) TaskListeners size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseListeners(JsonNode, BaseElement, boolean)"})
  void testParseListeners_whenUserTask_thenUserTaskTaskListenersSizeIsOne() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.asText()).thenReturn("As Text");
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.isNull()).thenReturn(false);
    when(arrayNode2.isTextual()).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList2.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult);
    UserTask element = new UserTask();

    // Act
    BpmnJsonConverterUtil.parseListeners(listenersNode, element, true);

    // Assert
    verify(listenersNode).isNull();
    verify(arrayNode2).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(listenersNode).isTextual();
    verify(arrayNode2).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(listenersNode, atLeast(1)).asText();
    verify(arrayNode2, atLeast(1)).asText();
    verify(arrayNode, atLeast(1)).asText();
    List<ActivitiListener> taskListeners = element.getTaskListeners();
    assertEquals(1, taskListeners.size());
    ActivitiListener getResult = taskListeners.get(0);
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
   * Test {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}.
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test parseMessages(JsonNode, BpmnModel)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseMessages(JsonNode, BpmnModel)"})
  void testParseMessages() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

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
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test parseMessages(JsonNode, BpmnModel); given ArrayList() add ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseMessages(JsonNode, BpmnModel)"})
  void testParseMessages_givenArrayListAddArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
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
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add Instance.</li>
   *   <li>Then calls {@link JsonNode#iterator()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test parseMessages(JsonNode, BpmnModel); given ArrayList() add Instance; then calls iterator()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseMessages(JsonNode, BpmnModel)"})
  void testParseMessages_givenArrayListAddInstance_thenCallsIterator() {
    // Arrange
    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(MissingNode.getInstance());
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
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} iterator.</li>
   *   <li>Then calls {@link JsonNode#iterator()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test parseMessages(JsonNode, BpmnModel); given ArrayList() iterator; then calls iterator()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseMessages(JsonNode, BpmnModel)"})
  void testParseMessages_givenArrayListIterator_thenCallsIterator() {
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
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   *   <li>Then calls {@link ArrayNode#get(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test parseMessages(JsonNode, BpmnModel); given ArrayNode get(String) return Instance; then calls get(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseMessages(JsonNode, BpmnModel)"})
  void testParseMessages_givenArrayNodeGetReturnInstance_thenCallsGet() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

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
   * <ul>
   *   <li>Given {@link ArrayNode} {@link JsonNode#isNull()} return {@code true}.</li>
   *   <li>Then calls {@link JsonNode#isNull()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test parseMessages(JsonNode, BpmnModel); given ArrayNode isNull() return 'true'; then calls isNull()")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>Then {@link BpmnModel} (default constructor) Messages size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test parseMessages(JsonNode, BpmnModel); then BpmnModel (default constructor) Messages size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseMessages(JsonNode, BpmnModel)"})
  void testParseMessages_thenBpmnModelMessagesSizeIsOne() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));

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
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test parseMessages(JsonNode, BpmnModel); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseMessages(JsonNode, BpmnModel)"})
  void testParseMessages_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    ArrayNode messagesNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.parseMessages(messagesNode, element);

    // Assert that nothing has changed
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then {@link BpmnModel} (default constructor) Messages Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test parseMessages(JsonNode, BpmnModel); when Instance; then BpmnModel (default constructor) Messages Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseMessages(JsonNode, BpmnModel)"})
  void testParseMessages_whenInstance_thenBpmnModelMessagesEmpty() {
    // Arrange
    MissingNode messagesNode = MissingNode.getInstance();
    BpmnModel element = new BpmnModel();

    // Act
    BpmnJsonConverterUtil.parseMessages(messagesNode, element);

    // Assert that nothing has changed
    assertTrue(element.getMessages().isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then {@link BpmnModel} (default constructor) Messages Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseMessages(JsonNode, BpmnModel)}
   */
  @Test
  @DisplayName("Test parseMessages(JsonNode, BpmnModel); when 'null'; then BpmnModel (default constructor) Messages Empty")
  @Tag("MaintainedByDiffblue")
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
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseEventListeners(JsonNode, Process)"})
  void testParseEventListeners() {
    // Arrange
    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
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
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseEventListeners(JsonNode, Process)"})
  void testParseEventListeners2() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

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
    verify(arrayNode).get(eq("events"));
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseEventListeners(JsonNode, Process)"})
  void testParseEventListeners3() {
    // Arrange
    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.size()).thenReturn(3);
    when(arrayNode.isArray()).thenReturn(true);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList2.iterator();
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
    verify(arrayNode).iterator();
    verify(arrayNode2).get(eq("events"));
    verify(arrayNode).isArray();
    verify(arrayNode).size();
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseEventListeners(JsonNode, Process)"})
  void testParseEventListeners4() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode2.size()).thenReturn(3);
    when(arrayNode2.isArray()).thenReturn(true);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode3);
    Iterator<JsonNode> iteratorResult = jsonNodeList2.iterator();
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
    verify(arrayNode2).iterator();
    verify(arrayNode).get(eq("event"));
    verify(arrayNode3).get(eq("events"));
    verify(arrayNode2).isArray();
    verify(arrayNode2).size();
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayNode} {@link ContainerNode#asText()} return {@code 42}.</li>
   *   <li>Then calls {@link JsonNode#isTextual()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process); given '42'; when ArrayNode asText() return '42'; then calls isTextual()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseEventListeners(JsonNode, Process)"})
  void testParseEventListeners_given42_whenArrayNodeAsTextReturn42_thenCallsIsTextual() {
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
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add Instance.</li>
   *   <li>Then calls {@link ArrayNode#isArray()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process); given ArrayList() add Instance; then calls isArray()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseEventListeners(JsonNode, Process)"})
  void testParseEventListeners_givenArrayListAddInstance_thenCallsIsArray() {
    // Arrange
    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(MissingNode.getInstance());
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.size()).thenReturn(3);
    when(arrayNode.isArray()).thenReturn(true);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList2.iterator();
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
    verify(arrayNode).iterator();
    verify(arrayNode2).get(eq("events"));
    verify(arrayNode).isArray();
    verify(arrayNode).size();
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add Instance.</li>
   *   <li>Then calls {@link JsonNode#isTextual()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process); given ArrayList() add Instance; then calls isTextual()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseEventListeners(JsonNode, Process)"})
  void testParseEventListeners_givenArrayListAddInstance_thenCallsIsTextual() {
    // Arrange
    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(MissingNode.getInstance());
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
   * <ul>
   *   <li>Given {@link ArrayNode} {@link JsonNode#asBoolean()} return {@code false}.</li>
   *   <li>Then calls {@link JsonNode#asBoolean()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process); given ArrayNode asBoolean() return 'false'; then calls asBoolean()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseEventListeners(JsonNode, Process)"})
  void testParseEventListeners_givenArrayNodeAsBooleanReturnFalse_thenCallsAsBoolean() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.asBoolean()).thenReturn(false);
    when(arrayNode2.isNull()).thenReturn(true);
    when(arrayNode2.asText()).thenReturn("As Text");
    when(arrayNode2.iterator()).thenReturn(iteratorResult);
    when(arrayNode2.size()).thenReturn(3);
    when(arrayNode2.isArray()).thenReturn(true);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode3);
    Iterator<JsonNode> iteratorResult2 = jsonNodeList2.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult2);

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(arrayNode2).asBoolean();
    verify(listenersNode).isNull();
    verify(arrayNode2, atLeast(1)).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode2).iterator();
    verify(arrayNode3, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode).get(eq("event"));
    verify(arrayNode2).isArray();
    verify(arrayNode2).size();
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ContainerNode#asText()} return {@code As Text}.</li>
   *   <li>Then calls {@link JsonNode#asBoolean()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process); given ArrayNode asText() return 'As Text'; then calls asBoolean()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseEventListeners(JsonNode, Process)"})
  void testParseEventListeners_givenArrayNodeAsTextReturnAsText_thenCallsAsBoolean() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.asBoolean()).thenReturn(true);
    when(arrayNode2.isNull()).thenReturn(true);
    when(arrayNode2.asText()).thenReturn("As Text");
    when(arrayNode2.iterator()).thenReturn(iteratorResult);
    when(arrayNode2.size()).thenReturn(3);
    when(arrayNode2.isArray()).thenReturn(true);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode3);
    Iterator<JsonNode> iteratorResult2 = jsonNodeList2.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult2);

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(arrayNode2).asBoolean();
    verify(listenersNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode2).iterator();
    verify(arrayNode3, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode).get(eq("event"));
    verify(arrayNode2).isArray();
    verify(arrayNode2).size();
    verify(listenersNode, atLeast(1)).asText();
    verify(arrayNode2, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ContainerNode#asText()} return {@code As Text}.</li>
   *   <li>Then calls {@link ArrayNode#isArray()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process); given ArrayNode asText() return 'As Text'; then calls isArray()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseEventListeners(JsonNode, Process)"})
  void testParseEventListeners_givenArrayNodeAsTextReturnAsText_thenCallsIsArray() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.isNull()).thenReturn(true);
    when(arrayNode3.asText()).thenReturn("As Text");
    when(arrayNode3.iterator()).thenReturn(iteratorResult);
    when(arrayNode3.size()).thenReturn(3);
    when(arrayNode3.isArray()).thenReturn(true);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode4);
    Iterator<JsonNode> iteratorResult2 = jsonNodeList2.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult2);

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(listenersNode).isNull();
    verify(arrayNode).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode3).iterator();
    verify(arrayNode2).get(eq("event"));
    verify(arrayNode4).get(eq("events"));
    verify(arrayNode3).isArray();
    verify(arrayNode3).size();
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ContainerNode#asText()} return {@code error}.</li>
   *   <li>Then calls {@link JsonNode#asBoolean()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process); given ArrayNode asText() return 'error'; then calls asBoolean()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseEventListeners(JsonNode, Process)"})
  void testParseEventListeners_givenArrayNodeAsTextReturnError_thenCallsAsBoolean() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.asBoolean()).thenReturn(true);
    when(arrayNode2.isNull()).thenReturn(true);
    when(arrayNode2.asText()).thenReturn("error");
    when(arrayNode2.iterator()).thenReturn(iteratorResult);
    when(arrayNode2.size()).thenReturn(3);
    when(arrayNode2.isArray()).thenReturn(true);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode3);
    Iterator<JsonNode> iteratorResult2 = jsonNodeList2.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult2);

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(arrayNode2).asBoolean();
    verify(listenersNode).isNull();
    verify(arrayNode2).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode2).iterator();
    verify(arrayNode3, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode).get(eq("event"));
    verify(arrayNode2).isArray();
    verify(arrayNode2).size();
    verify(arrayNode2).asText();
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ContainerNode#asText()} return {@code globalSignal}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process); given ArrayNode asText() return 'globalSignal'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseEventListeners(JsonNode, Process)"})
  void testParseEventListeners_givenArrayNodeAsTextReturnGlobalSignal() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.asBoolean()).thenReturn(true);
    when(arrayNode2.isNull()).thenReturn(true);
    when(arrayNode2.asText()).thenReturn("globalSignal");
    when(arrayNode2.iterator()).thenReturn(iteratorResult);
    when(arrayNode2.size()).thenReturn(3);
    when(arrayNode2.isArray()).thenReturn(true);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode3);
    Iterator<JsonNode> iteratorResult2 = jsonNodeList2.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult2);

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(arrayNode2).asBoolean();
    verify(listenersNode).isNull();
    verify(arrayNode2).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode2).iterator();
    verify(arrayNode3, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode).get(eq("event"));
    verify(arrayNode2).isArray();
    verify(arrayNode2).size();
    verify(listenersNode, atLeast(1)).asText();
    verify(arrayNode2, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ContainerNode#asText()} return {@code message}.</li>
   *   <li>Then calls {@link JsonNode#asBoolean()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process); given ArrayNode asText() return 'message'; then calls asBoolean()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseEventListeners(JsonNode, Process)"})
  void testParseEventListeners_givenArrayNodeAsTextReturnMessage_thenCallsAsBoolean() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.asBoolean()).thenReturn(true);
    when(arrayNode2.isNull()).thenReturn(true);
    when(arrayNode2.asText()).thenReturn("message");
    when(arrayNode2.iterator()).thenReturn(iteratorResult);
    when(arrayNode2.size()).thenReturn(3);
    when(arrayNode2.isArray()).thenReturn(true);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode3);
    Iterator<JsonNode> iteratorResult2 = jsonNodeList2.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult2);

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(arrayNode2).asBoolean();
    verify(listenersNode).isNull();
    verify(arrayNode2).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode2).iterator();
    verify(arrayNode3, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode).get(eq("event"));
    verify(arrayNode2).isArray();
    verify(arrayNode2).size();
    verify(listenersNode, atLeast(1)).asText();
    verify(arrayNode2, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ContainerNode#asText()} return {@code signal}.</li>
   *   <li>Then calls {@link JsonNode#asBoolean()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process); given ArrayNode asText() return 'signal'; then calls asBoolean()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseEventListeners(JsonNode, Process)"})
  void testParseEventListeners_givenArrayNodeAsTextReturnSignal_thenCallsAsBoolean() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(BigInteger.valueOf(1L)));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.asBoolean()).thenReturn(true);
    when(arrayNode2.isNull()).thenReturn(true);
    when(arrayNode2.asText()).thenReturn("signal");
    when(arrayNode2.iterator()).thenReturn(iteratorResult);
    when(arrayNode2.size()).thenReturn(3);
    when(arrayNode2.isArray()).thenReturn(true);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode3);
    Iterator<JsonNode> iteratorResult2 = jsonNodeList2.iterator();
    ArrayNode listenersNode = mock(ArrayNode.class);
    when(listenersNode.asText()).thenReturn("As Text");
    when(listenersNode.isNull()).thenReturn(false);
    when(listenersNode.isTextual()).thenReturn(true);
    when(listenersNode.iterator()).thenReturn(iteratorResult2);

    // Act
    BpmnJsonConverterUtil.parseEventListeners(listenersNode, new Process());

    // Assert
    verify(arrayNode2).asBoolean();
    verify(listenersNode).isNull();
    verify(arrayNode2).isNull();
    verify(listenersNode).isTextual();
    verify(listenersNode).iterator();
    verify(arrayNode2).iterator();
    verify(arrayNode3, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode).get(eq("event"));
    verify(arrayNode2).isArray();
    verify(arrayNode2).size();
    verify(listenersNode, atLeast(1)).asText();
    verify(arrayNode2, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   *   <li>Then calls {@link ArrayNode#get(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process); given ArrayNode get(String) return Instance; then calls get(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseEventListeners(JsonNode, Process)"})
  void testParseEventListeners_givenArrayNodeGetReturnInstance_thenCallsGet() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

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
    verify(arrayNode).get(eq("events"));
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   *   <li>Then calls {@link ArrayNode#isArray()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process); given ArrayNode get(String) return Instance; then calls isArray()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseEventListeners(JsonNode, Process)"})
  void testParseEventListeners_givenArrayNodeGetReturnInstance_thenCallsIsArray() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode2.size()).thenReturn(3);
    when(arrayNode2.isArray()).thenReturn(true);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode3);
    Iterator<JsonNode> iteratorResult = jsonNodeList2.iterator();
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
    verify(arrayNode2).iterator();
    verify(arrayNode).get(eq("event"));
    verify(arrayNode3).get(eq("events"));
    verify(arrayNode2).isArray();
    verify(arrayNode2).size();
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <ul>
   *   <li>Given {@code As Text}.</li>
   *   <li>Then calls {@link JsonNode#isTextual()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process); given 'As Text'; then calls isTextual()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseEventListeners(JsonNode, Process)"})
  void testParseEventListeners_givenAsText_thenCallsIsTextual() {
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
   * <ul>
   *   <li>Given empty string.</li>
   *   <li>When {@link ArrayNode} {@link ContainerNode#asText()} return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process); given empty string; when ArrayNode asText() return empty string")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link ArrayNode} {@link ContainerNode#asText()} return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process); given 'null'; when ArrayNode asText() return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseEventListeners(JsonNode, Process)"})
  void testParseEventListeners_givenNull_whenArrayNodeAsTextReturnNull() {
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
   * <ul>
   *   <li>Then calls {@link ArrayNode#isArray()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process); then calls isArray()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.parseEventListeners(JsonNode, Process)"})
  void testParseEventListeners_thenCallsIsArray() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.size()).thenReturn(3);
    when(arrayNode.isArray()).thenReturn(true);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode2);
    Iterator<JsonNode> iteratorResult = jsonNodeList2.iterator();
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
    verify(arrayNode).iterator();
    verify(arrayNode2).get(eq("events"));
    verify(arrayNode).isArray();
    verify(arrayNode).size();
    verify(listenersNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}.
   * <ul>
   *   <li>When {@link ArrayNode} {@link JsonNode#isNull()} return {@code true}.</li>
   *   <li>Then calls {@link JsonNode#iterator()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#parseEventListeners(JsonNode, Process)}
   */
  @Test
  @DisplayName("Test parseEventListeners(JsonNode, Process); when ArrayNode isNull() return 'true'; then calls iterator()")
  @Tag("MaintainedByDiffblue")
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
   * Test {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}.
   * <ul>
   *   <li>Given Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}
   */
  @Test
  @DisplayName("Test lookForSourceRef(String, JsonNode); given Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BpmnJsonConverterUtil.lookForSourceRef(String, JsonNode)"})
  void testLookForSourceRef_givenInstance() {
    // Arrange
    ArrayNode childShapesNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    childShapesNode.add(MissingNode.getInstance());

    // Act and Assert
    assertNull(BpmnJsonConverterUtil.lookForSourceRef("42", childShapesNode));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}
   */
  @Test
  @DisplayName("Test lookForSourceRef(String, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BpmnJsonConverterUtil.lookForSourceRef(String, JsonNode)"})
  void testLookForSourceRef_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange, Act and Assert
    assertNull(BpmnJsonConverterUtil.lookForSourceRef("42", new ArrayNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true} addArray.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}
   */
  @Test
  @DisplayName("Test lookForSourceRef(String, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true' addArray")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BpmnJsonConverterUtil.lookForSourceRef(String, JsonNode)"})
  void testLookForSourceRef_whenArrayNodeWithNfIsWithExactBigDecimalsTrueAddArray() {
    // Arrange
    ArrayNode childShapesNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    childShapesNode.addArray();
    childShapesNode.add(MissingNode.getInstance());

    // Act and Assert
    assertNull(BpmnJsonConverterUtil.lookForSourceRef("42", childShapesNode));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true} addObject.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}
   */
  @Test
  @DisplayName("Test lookForSourceRef(String, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true' addObject")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BpmnJsonConverterUtil.lookForSourceRef(String, JsonNode)"})
  void testLookForSourceRef_whenArrayNodeWithNfIsWithExactBigDecimalsTrueAddObject() {
    // Arrange
    ArrayNode childShapesNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    childShapesNode.addObject();
    childShapesNode.add(MissingNode.getInstance());

    // Act and Assert
    assertNull(BpmnJsonConverterUtil.lookForSourceRef("42", childShapesNode));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}.
   * <ul>
   *   <li>When Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}
   */
  @Test
  @DisplayName("Test lookForSourceRef(String, JsonNode); when Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BpmnJsonConverterUtil.lookForSourceRef(String, JsonNode)"})
  void testLookForSourceRef_whenInstance() {
    // Arrange, Act and Assert
    assertNull(BpmnJsonConverterUtil.lookForSourceRef("42", MissingNode.getInstance()));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#lookForSourceRef(String, JsonNode)}
   */
  @Test
  @DisplayName("Test lookForSourceRef(String, JsonNode); when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BpmnJsonConverterUtil.lookForSourceRef(String, JsonNode)"})
  void testLookForSourceRef_whenNull() {
    // Arrange, Act and Assert
    assertNull(BpmnJsonConverterUtil.lookForSourceRef("42", null));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToDataProperties(JsonNode, BaseElement); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List BpmnJsonConverterUtil.convertJsonToDataProperties(JsonNode, BaseElement)"})
  void testConvertJsonToDataProperties_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    ArrayNode objectNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult = BpmnJsonConverterUtil
        .convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>When {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToDataProperties(JsonNode, BaseElement); when BigDecimal(String) with '2.3'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List BpmnJsonConverterUtil.convertJsonToDataProperties(JsonNode, BaseElement)"})
  void testConvertJsonToDataProperties_whenBigDecimalWith23() {
    // Arrange
    DecimalNode objectNode = new DecimalNode(new BigDecimal("2.3"));

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult = BpmnJsonConverterUtil
        .convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>When {@link BigIntegerNode#BigIntegerNode(BigInteger)} with v is valueOf one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToDataProperties(JsonNode, BaseElement); when BigIntegerNode(BigInteger) with v is valueOf one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List BpmnJsonConverterUtil.convertJsonToDataProperties(JsonNode, BaseElement)"})
  void testConvertJsonToDataProperties_whenBigIntegerNodeWithVIsValueOfOne() {
    // Arrange
    BigIntegerNode objectNode = new BigIntegerNode(BigInteger.valueOf(1L));

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult = BpmnJsonConverterUtil
        .convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>When {@link BinaryNode#BinaryNode(byte[])} with data is {@code AXAXAXAX} Bytes is {@code UTF-8}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToDataProperties(JsonNode, BaseElement); when BinaryNode(byte[]) with data is 'AXAXAXAX' Bytes is 'UTF-8'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List BpmnJsonConverterUtil.convertJsonToDataProperties(JsonNode, BaseElement)"})
  void testConvertJsonToDataProperties_whenBinaryNodeWithDataIsAxaxaxaxBytesIsUtf8()
      throws UnsupportedEncodingException {
    // Arrange
    BinaryNode objectNode = new BinaryNode("AXAXAXAX".getBytes("UTF-8"));

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult = BpmnJsonConverterUtil
        .convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>When False.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToDataProperties(JsonNode, BaseElement); when False")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List BpmnJsonConverterUtil.convertJsonToDataProperties(JsonNode, BaseElement)"})
  void testConvertJsonToDataProperties_whenFalse() {
    // Arrange
    BooleanNode objectNode = BooleanNode.getFalse();

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult = BpmnJsonConverterUtil
        .convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>When Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToDataProperties(JsonNode, BaseElement); when Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List BpmnJsonConverterUtil.convertJsonToDataProperties(JsonNode, BaseElement)"})
  void testConvertJsonToDataProperties_whenInstance() {
    // Arrange
    MissingNode objectNode = MissingNode.getInstance();

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult = BpmnJsonConverterUtil
        .convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>When Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToDataProperties(JsonNode, BaseElement); when Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List BpmnJsonConverterUtil.convertJsonToDataProperties(JsonNode, BaseElement)"})
  void testConvertJsonToDataProperties_whenInstance2() {
    // Arrange
    NullNode objectNode = NullNode.getInstance();

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult = BpmnJsonConverterUtil
        .convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToDataProperties(JsonNode, BaseElement); when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List BpmnJsonConverterUtil.convertJsonToDataProperties(JsonNode, BaseElement)"})
  void testConvertJsonToDataProperties_whenNull() {
    // Arrange and Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult = BpmnJsonConverterUtil
        .convertJsonToDataProperties(null, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}.
   * <ul>
   *   <li>When True.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertJsonToDataProperties(JsonNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToDataProperties(JsonNode, BaseElement); when True")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List BpmnJsonConverterUtil.convertJsonToDataProperties(JsonNode, BaseElement)"})
  void testConvertJsonToDataProperties_whenTrue() {
    // Arrange
    BooleanNode objectNode = BooleanNode.getTrue();

    // Act
    List<ValuedDataObject> actualConvertJsonToDataPropertiesResult = BpmnJsonConverterUtil
        .convertJsonToDataProperties(objectNode, new ActivitiListener());

    // Assert
    assertTrue(actualConvertJsonToDataPropertiesResult.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertDataPropertiesToJson(List, ObjectNode)}.
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertDataPropertiesToJson(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test convertDataPropertiesToJson(List, ObjectNode)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertDataPropertiesToJson(List, ObjectNode)"})
  void testConvertDataPropertiesToJson() {
    // Arrange
    ArrayList<ValuedDataObject> dataObjects = new ArrayList<>();
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    BpmnJsonConverterUtil.convertDataPropertiesToJson(dataObjects, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ArrayNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals("[ ]", nextResult2.toPrettyString());
    assertEquals("{\n  \"dataproperties\" : {\n    \"items\" : [ ]\n  }\n}", propertiesNode.toPrettyString());
    assertEquals("{\n  \"items\" : [ ]\n}", nextResult.toPrettyString());
    assertEquals(0, nextResult2.size());
    assertFalse(nextResult2.elements().hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertFalse(nextResult2.iterator().hasNext());
    assertTrue(nextResult2.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertDataPropertiesToJson(List, ObjectNode)}.
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertDataPropertiesToJson(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test convertDataPropertiesToJson(List, ObjectNode)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertDataPropertiesToJson(List, ObjectNode)"})
  void testConvertDataPropertiesToJson2() {
    // Arrange
    ItemDefinition itemSubjectRef = new ItemDefinition();
    itemSubjectRef.setStructureRef("dataproperty_id");

    BooleanDataObject booleanDataObject = new BooleanDataObject();
    booleanDataObject.setItemSubjectRef(itemSubjectRef);
    booleanDataObject.setId(null);
    booleanDataObject.setName(null);

    ArrayList<ValuedDataObject> dataObjects = new ArrayList<>();
    dataObjects.add(booleanDataObject);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

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
        "[ {\n" + "  \"dataproperty_id\" : null,\n" + "  \"dataproperty_name\" : null,\n"
            + "  \"dataproperty_type\" : \"dataproperty_id\",\n" + "  \"dataproperty_value\" : \"\"\n" + "} ]",
        nextResult2.toPrettyString());
    assertEquals("{\n" + "  \"dataproperties\" : {\n" + "    \"items\" : [ {\n" + "      \"dataproperty_id\" : null,\n"
        + "      \"dataproperty_name\" : null,\n" + "      \"dataproperty_type\" : \"dataproperty_id\",\n"
        + "      \"dataproperty_value\" : \"\"\n" + "    } ]\n" + "  }\n" + "}", propertiesNode.toPrettyString());
    assertEquals(
        "{\n" + "  \"dataproperty_id\" : null,\n" + "  \"dataproperty_name\" : null,\n"
            + "  \"dataproperty_type\" : \"dataproperty_id\",\n" + "  \"dataproperty_value\" : \"\"\n" + "}",
        nextResult3.toPrettyString());
    assertEquals("{\n" + "  \"items\" : [ {\n" + "    \"dataproperty_id\" : null,\n"
        + "    \"dataproperty_name\" : null,\n" + "    \"dataproperty_type\" : \"dataproperty_id\",\n"
        + "    \"dataproperty_value\" : \"\"\n" + "  } ]\n" + "}", nextResult.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#convertDataPropertiesToJson(List, ObjectNode)}.
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#convertDataPropertiesToJson(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test convertDataPropertiesToJson(List, ObjectNode)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BpmnJsonConverterUtil.convertDataPropertiesToJson(List, ObjectNode)"})
  void testConvertDataPropertiesToJson3() {
    // Arrange
    ItemDefinition itemSubjectRef = new ItemDefinition();
    itemSubjectRef.setStructureRef("dataproperty_id");

    BooleanDataObject booleanDataObject = new BooleanDataObject();
    booleanDataObject.setValue("Value");
    booleanDataObject.setItemSubjectRef(itemSubjectRef);
    booleanDataObject.setId(null);
    booleanDataObject.setName(null);

    ArrayList<ValuedDataObject> dataObjects = new ArrayList<>();
    dataObjects.add(booleanDataObject);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

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
        "[ {\n" + "  \"dataproperty_id\" : null,\n" + "  \"dataproperty_name\" : null,\n"
            + "  \"dataproperty_type\" : \"dataproperty_id\",\n" + "  \"dataproperty_value\" : \"false\"\n" + "} ]",
        nextResult2.toPrettyString());
    assertEquals(
        "{\n" + "  \"dataproperties\" : {\n" + "    \"items\" : [ {\n" + "      \"dataproperty_id\" : null,\n"
            + "      \"dataproperty_name\" : null,\n" + "      \"dataproperty_type\" : \"dataproperty_id\",\n"
            + "      \"dataproperty_value\" : \"false\"\n" + "    } ]\n" + "  }\n" + "}",
        propertiesNode.toPrettyString());
    assertEquals(
        "{\n" + "  \"dataproperty_id\" : null,\n" + "  \"dataproperty_name\" : null,\n"
            + "  \"dataproperty_type\" : \"dataproperty_id\",\n" + "  \"dataproperty_value\" : \"false\"\n" + "}",
        nextResult3.toPrettyString());
    assertEquals("{\n" + "  \"items\" : [ {\n" + "    \"dataproperty_id\" : null,\n"
        + "    \"dataproperty_name\" : null,\n" + "    \"dataproperty_type\" : \"dataproperty_id\",\n"
        + "    \"dataproperty_value\" : \"false\"\n" + "  } ]\n" + "}", nextResult.toPrettyString());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#validateIfNodeIsTextual(JsonNode)}.
   * <ul>
   *   <li>Then return {@link ArrayNode}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#validateIfNodeIsTextual(JsonNode)}
   */
  @Test
  @DisplayName("Test validateIfNodeIsTextual(JsonNode); then return ArrayNode")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"JsonNode BpmnJsonConverterUtil.validateIfNodeIsTextual(JsonNode)"})
  void testValidateIfNodeIsTextual_thenReturnArrayNode() {
    // Arrange and Act
    JsonNode actualValidateIfNodeIsTextualResult = BpmnJsonConverterUtil
        .validateIfNodeIsTextual(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Assert
    assertTrue(actualValidateIfNodeIsTextualResult instanceof ArrayNode);
    assertTrue(actualValidateIfNodeIsTextualResult.traverse() instanceof TreeTraversingParser);
    assertEquals("[ ]", actualValidateIfNodeIsTextualResult.toPrettyString());
    assertEquals(0, actualValidateIfNodeIsTextualResult.size());
    assertEquals(JsonNodeType.ARRAY, actualValidateIfNodeIsTextualResult.getNodeType());
    assertFalse(actualValidateIfNodeIsTextualResult.elements().hasNext());
    assertTrue(actualValidateIfNodeIsTextualResult.isArray());
    assertTrue(actualValidateIfNodeIsTextualResult.isContainerNode());
    assertTrue(actualValidateIfNodeIsTextualResult.isEmpty());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#validateIfNodeIsTextual(JsonNode)}.
   * <ul>
   *   <li>Then return {@link BigIntegerNode}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#validateIfNodeIsTextual(JsonNode)}
   */
  @Test
  @DisplayName("Test validateIfNodeIsTextual(JsonNode); then return BigIntegerNode")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"JsonNode BpmnJsonConverterUtil.validateIfNodeIsTextual(JsonNode)"})
  void testValidateIfNodeIsTextual_thenReturnBigIntegerNode() {
    // Arrange and Act
    JsonNode actualValidateIfNodeIsTextualResult = BpmnJsonConverterUtil
        .validateIfNodeIsTextual(new BigIntegerNode(BigInteger.valueOf(1L)));

    // Assert
    assertTrue(actualValidateIfNodeIsTextualResult instanceof BigIntegerNode);
    assertTrue(actualValidateIfNodeIsTextualResult.traverse() instanceof TreeTraversingParser);
    assertEquals("1", actualValidateIfNodeIsTextualResult.toPrettyString());
    assertEquals(JsonNodeType.NUMBER, actualValidateIfNodeIsTextualResult.getNodeType());
    assertFalse(((BigIntegerNode) actualValidateIfNodeIsTextualResult).isNaN());
    assertTrue(actualValidateIfNodeIsTextualResult.isBigInteger());
    assertTrue(actualValidateIfNodeIsTextualResult.isIntegralNumber());
    assertTrue(actualValidateIfNodeIsTextualResult.isNumber());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#validateIfNodeIsTextual(JsonNode)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return {@link MissingNode}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#validateIfNodeIsTextual(JsonNode)}
   */
  @Test
  @DisplayName("Test validateIfNodeIsTextual(JsonNode); when Instance; then return MissingNode")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"JsonNode BpmnJsonConverterUtil.validateIfNodeIsTextual(JsonNode)"})
  void testValidateIfNodeIsTextual_whenInstance_thenReturnMissingNode() {
    // Arrange and Act
    JsonNode actualValidateIfNodeIsTextualResult = BpmnJsonConverterUtil
        .validateIfNodeIsTextual(MissingNode.getInstance());

    // Assert
    assertTrue(actualValidateIfNodeIsTextualResult instanceof MissingNode);
    assertTrue(actualValidateIfNodeIsTextualResult.traverse() instanceof TreeTraversingParser);
    assertEquals("", actualValidateIfNodeIsTextualResult.toPrettyString());
    assertEquals(JsonNodeType.MISSING, actualValidateIfNodeIsTextualResult.getNodeType());
    assertTrue(actualValidateIfNodeIsTextualResult.isMissingNode());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#validateIfNodeIsTextual(JsonNode)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return {@link NullNode}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#validateIfNodeIsTextual(JsonNode)}
   */
  @Test
  @DisplayName("Test validateIfNodeIsTextual(JsonNode); when Instance; then return NullNode")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"JsonNode BpmnJsonConverterUtil.validateIfNodeIsTextual(JsonNode)"})
  void testValidateIfNodeIsTextual_whenInstance_thenReturnNullNode() {
    // Arrange and Act
    JsonNode actualValidateIfNodeIsTextualResult = BpmnJsonConverterUtil
        .validateIfNodeIsTextual(NullNode.getInstance());

    // Assert
    assertTrue(actualValidateIfNodeIsTextualResult instanceof NullNode);
    assertTrue(actualValidateIfNodeIsTextualResult.traverse() instanceof TreeTraversingParser);
    assertEquals("null", actualValidateIfNodeIsTextualResult.toPrettyString());
    assertEquals(JsonNodeType.NULL, actualValidateIfNodeIsTextualResult.getNodeType());
    assertTrue(actualValidateIfNodeIsTextualResult.isNull());
  }

  /**
   * Test {@link BpmnJsonConverterUtil#validateIfNodeIsTextual(JsonNode)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#validateIfNodeIsTextual(JsonNode)}
   */
  @Test
  @DisplayName("Test validateIfNodeIsTextual(JsonNode); when 'null'; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"JsonNode BpmnJsonConverterUtil.validateIfNodeIsTextual(JsonNode)"})
  void testValidateIfNodeIsTextual_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(BpmnJsonConverterUtil.validateIfNodeIsTextual(null));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getValueAsString(String, JsonNode)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#getValueAsString(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getValueAsString(String, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BpmnJsonConverterUtil.getValueAsString(String, JsonNode)"})
  void testGetValueAsString_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange, Act and Assert
    assertNull(
        BpmnJsonConverterUtil.getValueAsString("Name", new ArrayNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getValueAsString(String, JsonNode)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#getValueAsString(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getValueAsString(String, JsonNode); when Instance; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BpmnJsonConverterUtil.getValueAsString(String, JsonNode)"})
  void testGetValueAsString_whenInstance_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(BpmnJsonConverterUtil.getValueAsString("Name", MissingNode.getInstance()));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getPropertyValueAsString(String, JsonNode)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#getPropertyValueAsString(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getPropertyValueAsString(String, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BpmnJsonConverterUtil.getPropertyValueAsString(String, JsonNode)"})
  void testGetPropertyValueAsString_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange, Act and Assert
    assertNull(BpmnJsonConverterUtil.getPropertyValueAsString("Name",
        new ArrayNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getPropertyValueAsString(String, JsonNode)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#getPropertyValueAsString(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getPropertyValueAsString(String, JsonNode); when Instance; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BpmnJsonConverterUtil.getPropertyValueAsString(String, JsonNode)"})
  void testGetPropertyValueAsString_whenInstance_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(BpmnJsonConverterUtil.getPropertyValueAsString("Name", MissingNode.getInstance()));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getProperty(String, JsonNode)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#getProperty(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getProperty(String, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"JsonNode BpmnJsonConverterUtil.getProperty(String, JsonNode)"})
  void testGetProperty_whenArrayNodeWithNfIsWithExactBigDecimalsTrue_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(BpmnJsonConverterUtil.getProperty("Name", new ArrayNode(JsonNodeFactory.withExactBigDecimals(true))));
  }

  /**
   * Test {@link BpmnJsonConverterUtil#getProperty(String, JsonNode)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BpmnJsonConverterUtil#getProperty(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getProperty(String, JsonNode); when Instance; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"JsonNode BpmnJsonConverterUtil.getProperty(String, JsonNode)"})
  void testGetProperty_whenInstance_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(BpmnJsonConverterUtil.getProperty("Name", MissingNode.getInstance()));
  }
}
