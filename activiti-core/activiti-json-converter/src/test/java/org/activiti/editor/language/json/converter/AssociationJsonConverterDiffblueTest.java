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
import java.util.Map;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.Association;
import org.activiti.bpmn.model.AssociationDirection;
import org.activiti.bpmn.model.BaseElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AssociationJsonConverterDiffblueTest {
  /**
   * Test {@link AssociationJsonConverter#fillJsonTypes(Map)}.
   *
   * <p>Method under test: {@link AssociationJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AssociationJsonConverter.fillJsonTypes(Map)"})
  void testFillJsonTypes() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();

    // Act
    AssociationJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(1, convertersToBpmnMap.size());
    Class<AssociationJsonConverter> expectedGetResult = AssociationJsonConverter.class;
    assertEquals(expectedGetResult, convertersToBpmnMap.get("Association"));
  }

  /**
   * Test {@link AssociationJsonConverter#getStencilId(BaseElement)}.
   *
   * <p>Method under test: {@link AssociationJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String AssociationJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertEquals("Association", associationJsonConverter.getStencilId(new ActivitiListener()));
  }

  /**
   * Test {@link AssociationJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <p>Method under test: {@link AssociationJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement AssociationJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

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
        associationJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode2).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(elementNode).get("resourceId");
    assertTrue(actualConvertJsonToElementResult instanceof Association);
    assertNull(((Association) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((Association) actualConvertJsonToElementResult).getTargetRef());
    assertNull(actualConvertJsonToElementResult.getId());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertEquals(
        AssociationDirection.NONE,
        ((Association) actualConvertJsonToElementResult).getAssociationDirection());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link AssociationJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add valueOf ten.
   *   <li>Then calls {@link ArrayNode#iterator()}.
   * </ul>
   *
   * <p>Method under test: {@link AssociationJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayList() add valueOf ten; then calls iterator()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement AssociationJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenArrayListAddValueOfTen_thenCallsIterator() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

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
        associationJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode).iterator();
    verify(modelNode).get("childShapes");
    verify(elementNode).get("resourceId");
    assertTrue(actualConvertJsonToElementResult instanceof Association);
    assertNull(((Association) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((Association) actualConvertJsonToElementResult).getTargetRef());
    assertNull(actualConvertJsonToElementResult.getId());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertEquals(
        AssociationDirection.NONE,
        ((Association) actualConvertJsonToElementResult).getAssociationDirection());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link AssociationJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add valueOf ten.
   *   <li>Then calls {@link ArrayNode#size()}.
   * </ul>
   *
   * <p>Method under test: {@link AssociationJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayList() add valueOf ten; then calls size()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement AssociationJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenArrayListAddValueOfTen_thenCallsSize() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

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
        associationJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode3).iterator();
    verify(arrayNode, atLeast(1)).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(elementNode).get("resourceId");
    verify(arrayNode).size();
    assertTrue(actualConvertJsonToElementResult instanceof Association);
    assertNull(((Association) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((Association) actualConvertJsonToElementResult).getTargetRef());
    assertNull(actualConvertJsonToElementResult.getId());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertEquals(
        AssociationDirection.NONE,
        ((Association) actualConvertJsonToElementResult).getAssociationDirection());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link AssociationJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.
   *   <li>Then calls {@link ArrayNode#asText()}.
   * </ul>
   *
   * <p>Method under test: {@link AssociationJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode get(String) return Instance; then calls asText()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement AssociationJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenArrayNodeGetReturnInstance_thenCallsAsText() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

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
        associationJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode5).iterator();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(elementNode).get("resourceId");
    verify(arrayNode2).get("resourceId");
    verify(arrayNode3).size();
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof Association);
    assertNull(((Association) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((Association) actualConvertJsonToElementResult).getTargetRef());
    assertNull(actualConvertJsonToElementResult.getId());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertEquals(
        AssociationDirection.NONE,
        ((Association) actualConvertJsonToElementResult).getAssociationDirection());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link AssociationJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return valueOf ten.
   *   <li>Then calls {@link ArrayNode#iterator()}.
   * </ul>
   *
   * <p>Method under test: {@link AssociationJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode get(String) return valueOf ten; then calls iterator()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement AssociationJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenArrayNodeGetReturnValueOfTen_thenCallsIterator() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

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
        associationJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode2).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(elementNode).get("resourceId");
    assertTrue(actualConvertJsonToElementResult instanceof Association);
    assertNull(((Association) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((Association) actualConvertJsonToElementResult).getTargetRef());
    assertNull(actualConvertJsonToElementResult.getId());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertEquals(
        AssociationDirection.NONE,
        ((Association) actualConvertJsonToElementResult).getAssociationDirection());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link AssociationJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link AssociationJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement AssociationJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode elementNode = mock(ArrayNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(elementNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);

    // Act
    BaseElement actualConvertJsonToElementResult =
        associationJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(elementNode).get("resourceId");
    assertTrue(actualConvertJsonToElementResult instanceof Association);
    assertNull(((Association) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((Association) actualConvertJsonToElementResult).getTargetRef());
    assertNull(actualConvertJsonToElementResult.getId());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertEquals(
        AssociationDirection.NONE,
        ((Association) actualConvertJsonToElementResult).getAssociationDirection());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link AssociationJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link AssociationJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement AssociationJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenArrayNodeWithNfIsWithExactBigDecimalsTrue2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayNode modelNode = mock(ArrayNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(modelNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));

    // Act
    BaseElement actualConvertJsonToElementResult =
        associationJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(modelNode).get("childShapes");
    verify(elementNode).get("resourceId");
    assertTrue(actualConvertJsonToElementResult instanceof Association);
    assertNull(((Association) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((Association) actualConvertJsonToElementResult).getTargetRef());
    assertNull(actualConvertJsonToElementResult.getId());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertEquals(
        AssociationDirection.NONE,
        ((Association) actualConvertJsonToElementResult).getAssociationDirection());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link AssociationJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link AssociationJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement AssociationJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenArrayNodeWithNfIsWithExactBigDecimalsTrue3() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

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
        associationJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode4).iterator();
    verify(arrayNode2, atLeast(1)).iterator();
    verify(arrayNode3, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(elementNode).get("resourceId");
    verify(arrayNode).get("resourceId");
    verify(arrayNode2).size();
    assertTrue(actualConvertJsonToElementResult instanceof Association);
    assertNull(((Association) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((Association) actualConvertJsonToElementResult).getTargetRef());
    assertNull(actualConvertJsonToElementResult.getId());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertEquals(
        AssociationDirection.NONE,
        ((Association) actualConvertJsonToElementResult).getAssociationDirection());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link AssociationJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>Given {@link BinaryNode#BinaryNode(byte[])} with data is {@code AXAXAXAX} Bytes is {@code
   *       UTF-8}.
   * </ul>
   *
   * <p>Method under test: {@link AssociationJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given BinaryNode(byte[]) with data is 'AXAXAXAX' Bytes is 'UTF-8'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement AssociationJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenBinaryNodeWithDataIsAxaxaxaxBytesIsUtf8()
      throws UnsupportedEncodingException {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any()))
        .thenReturn(new BinaryNode("AXAXAXAX".getBytes("UTF-8")));
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);

    // Act
    BaseElement actualConvertJsonToElementResult =
        associationJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(elementNode).get("resourceId");
    assertTrue(actualConvertJsonToElementResult instanceof Association);
    assertNull(((Association) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((Association) actualConvertJsonToElementResult).getTargetRef());
    assertNull(actualConvertJsonToElementResult.getId());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertEquals(
        AssociationDirection.NONE,
        ((Association) actualConvertJsonToElementResult).getAssociationDirection());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link AssociationJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>Given Instance.
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.
   * </ul>
   *
   * <p>Method under test: {@link AssociationJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given Instance; when ArrayNode get(String) return Instance")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement AssociationJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenInstance_whenArrayNodeGetReturnInstance() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);

    // Act
    BaseElement actualConvertJsonToElementResult =
        associationJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(elementNode).get("resourceId");
    assertTrue(actualConvertJsonToElementResult instanceof Association);
    assertNull(((Association) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((Association) actualConvertJsonToElementResult).getTargetRef());
    assertNull(actualConvertJsonToElementResult.getId());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertEquals(
        AssociationDirection.NONE,
        ((Association) actualConvertJsonToElementResult).getAssociationDirection());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link AssociationJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>Given valueOf ten.
   *   <li>Then calls {@link ArrayNode#iterator()}.
   * </ul>
   *
   * <p>Method under test: {@link AssociationJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given valueOf ten; then calls iterator()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement AssociationJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenValueOfTen_thenCallsIterator() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());

    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    BaseElement actualConvertJsonToElementResult =
        associationJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode).iterator();
    verify(modelNode).get("childShapes");
    verify(elementNode).get("resourceId");
    assertTrue(actualConvertJsonToElementResult instanceof Association);
    assertNull(((Association) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((Association) actualConvertJsonToElementResult).getTargetRef());
    assertNull(actualConvertJsonToElementResult.getId());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertEquals(
        AssociationDirection.NONE,
        ((Association) actualConvertJsonToElementResult).getAssociationDirection());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link AssociationJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>Given valueOf ten.
   *   <li>Then calls {@link ArrayNode#size()}.
   * </ul>
   *
   * <p>Method under test: {@link AssociationJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given valueOf ten; then calls size()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement AssociationJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenValueOfTen_thenCallsSize() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

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
        associationJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode3).iterator();
    verify(arrayNode, atLeast(1)).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(elementNode).get("resourceId");
    verify(arrayNode).size();
    assertTrue(actualConvertJsonToElementResult instanceof Association);
    assertNull(((Association) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((Association) actualConvertJsonToElementResult).getTargetRef());
    assertNull(actualConvertJsonToElementResult.getId());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertEquals(
        AssociationDirection.NONE,
        ((Association) actualConvertJsonToElementResult).getAssociationDirection());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link AssociationJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>Given valueOf ten.
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link AssociationJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given valueOf ten; when ArrayNode get(String) return valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement AssociationJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenValueOfTen_whenArrayNodeGetReturnValueOfTen() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    // Act
    BaseElement actualConvertJsonToElementResult =
        associationJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(modelNode).get("childShapes");
    verify(elementNode).get("resourceId");
    assertTrue(actualConvertJsonToElementResult instanceof Association);
    assertNull(((Association) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((Association) actualConvertJsonToElementResult).getTargetRef());
    assertNull(actualConvertJsonToElementResult.getId());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertEquals(
        AssociationDirection.NONE,
        ((Association) actualConvertJsonToElementResult).getAssociationDirection());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link AssociationJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>Given valueOf ten.
   *   <li>When valueOf ten.
   *   <li>Then return {@link Association}.
   * </ul>
   *
   * <p>Method under test: {@link AssociationJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); given valueOf ten; when valueOf ten; then return Association")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement AssociationJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_givenValueOfTen_whenValueOfTen_thenReturnAssociation() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);

    // Act
    BaseElement actualConvertJsonToElementResult =
        associationJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(elementNode).get("resourceId");
    assertTrue(actualConvertJsonToElementResult instanceof Association);
    assertNull(((Association) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((Association) actualConvertJsonToElementResult).getTargetRef());
    assertNull(actualConvertJsonToElementResult.getId());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertEquals(
        AssociationDirection.NONE,
        ((Association) actualConvertJsonToElementResult).getAssociationDirection());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link AssociationJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>Then calls {@link ArrayNode#asText()}.
   * </ul>
   *
   * <p>Method under test: {@link AssociationJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); then calls asText()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "BaseElement AssociationJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_thenCallsAsText() throws UnsupportedEncodingException {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

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
        associationJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    verify(arrayNode5).iterator();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get("childShapes");
    verify(elementNode).get("resourceId");
    verify(arrayNode2).get("resourceId");
    verify(arrayNode3).size();
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof Association);
    assertNull(((Association) actualConvertJsonToElementResult).getSourceRef());
    assertNull(((Association) actualConvertJsonToElementResult).getTargetRef());
    assertNull(actualConvertJsonToElementResult.getId());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertEquals(
        AssociationDirection.NONE,
        ((Association) actualConvertJsonToElementResult).getAssociationDirection());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test new {@link AssociationJsonConverter} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link AssociationJsonConverter}
   */
  @Test
  @DisplayName("Test new AssociationJsonConverter (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AssociationJsonConverter.<init>()"})
  void testNewAssociationJsonConverter() {
    // Arrange and Act
    AssociationJsonConverter actualAssociationJsonConverter = new AssociationJsonConverter();

    // Assert
    assertNull(actualAssociationJsonConverter.shapesArrayNode);
    assertNull(actualAssociationJsonConverter.flowElementNode);
    assertNull(actualAssociationJsonConverter.model);
    assertNull(actualAssociationJsonConverter.processor);
    assertEquals(0.0d, actualAssociationJsonConverter.subProcessX);
    assertEquals(0.0d, actualAssociationJsonConverter.subProcessY);
  }
}
