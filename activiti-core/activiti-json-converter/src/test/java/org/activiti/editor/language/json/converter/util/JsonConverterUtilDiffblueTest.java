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
package org.activiti.editor.language.json.converter.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.MissingNode;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.activiti.editor.language.json.converter.util.JsonConverterUtil.JsonLookupResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class JsonConverterUtilDiffblueTest {
  /**
   * Test {@link JsonConverterUtil#getPropertyValueAsString(String, JsonNode)}.
   *
   * <ul>
   *   <li>When valueOf ten.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JsonConverterUtil#getPropertyValueAsString(String, JsonNode)}
   */
  @Test
  @DisplayName(
      "Test getPropertyValueAsString(String, JsonNode); when valueOf ten; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String JsonConverterUtil.getPropertyValueAsString(String, JsonNode)"})
  void testGetPropertyValueAsString_whenValueOfTen_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(JsonConverterUtil.getPropertyValueAsString("Name", DoubleNode.valueOf(10.0d)));
  }

  /**
   * Test {@link JsonConverterUtil#getPropertyValueAsBoolean(String, JsonNode, boolean)} with {@code
   * name}, {@code objectNode}, {@code defaultValue}.
   *
   * <ul>
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JsonConverterUtil#getPropertyValueAsBoolean(String, JsonNode,
   * boolean)}
   */
  @Test
  @DisplayName(
      "Test getPropertyValueAsBoolean(String, JsonNode, boolean) with 'name', 'objectNode', 'defaultValue'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean JsonConverterUtil.getPropertyValueAsBoolean(String, JsonNode, boolean)"
  })
  void testGetPropertyValueAsBooleanWithNameObjectNodeDefaultValue_thenReturnFalse() {
    // Arrange and Act
    boolean actualPropertyValueAsBoolean =
        JsonConverterUtil.getPropertyValueAsBoolean("Name", DoubleNode.valueOf(10.0d), false);

    // Assert
    assertFalse(actualPropertyValueAsBoolean);
  }

  /**
   * Test {@link JsonConverterUtil#getPropertyValueAsBoolean(String, JsonNode, boolean)} with {@code
   * name}, {@code objectNode}, {@code defaultValue}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link JsonConverterUtil#getPropertyValueAsBoolean(String, JsonNode,
   * boolean)}
   */
  @Test
  @DisplayName(
      "Test getPropertyValueAsBoolean(String, JsonNode, boolean) with 'name', 'objectNode', 'defaultValue'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean JsonConverterUtil.getPropertyValueAsBoolean(String, JsonNode, boolean)"
  })
  void testGetPropertyValueAsBooleanWithNameObjectNodeDefaultValue_thenReturnTrue() {
    // Arrange and Act
    boolean actualPropertyValueAsBoolean =
        JsonConverterUtil.getPropertyValueAsBoolean("Name", DoubleNode.valueOf(10.0d), true);

    // Assert
    assertTrue(actualPropertyValueAsBoolean);
  }

  /**
   * Test {@link JsonConverterUtil#getPropertyValueAsBoolean(String, JsonNode)} with {@code name},
   * {@code objectNode}.
   *
   * <ul>
   *   <li>When valueOf ten.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link JsonConverterUtil#getPropertyValueAsBoolean(String, JsonNode)}
   */
  @Test
  @DisplayName(
      "Test getPropertyValueAsBoolean(String, JsonNode) with 'name', 'objectNode'; when valueOf ten; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean JsonConverterUtil.getPropertyValueAsBoolean(String, JsonNode)"})
  void testGetPropertyValueAsBooleanWithNameObjectNode_whenValueOfTen_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(JsonConverterUtil.getPropertyValueAsBoolean("Name", DoubleNode.valueOf(10.0d)));
  }

  /**
   * Test {@link JsonConverterUtil#getPropertyValueAsList(String, JsonNode)}.
   *
   * <ul>
   *   <li>When valueOf ten.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link JsonConverterUtil#getPropertyValueAsList(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getPropertyValueAsList(String, JsonNode); when valueOf ten; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List JsonConverterUtil.getPropertyValueAsList(String, JsonNode)"})
  void testGetPropertyValueAsList_whenValueOfTen_thenReturnEmpty() {
    // Arrange and Act
    List<String> actualPropertyValueAsList =
        JsonConverterUtil.getPropertyValueAsList("Name", DoubleNode.valueOf(10.0d));

    // Assert
    assertTrue(actualPropertyValueAsList.isEmpty());
  }

  /**
   * Test {@link JsonConverterUtil#getProperty(String, JsonNode)}.
   *
   * <ul>
   *   <li>When valueOf ten.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JsonConverterUtil#getProperty(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getProperty(String, JsonNode); when valueOf ten; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"JsonNode JsonConverterUtil.getProperty(String, JsonNode)"})
  void testGetProperty_whenValueOfTen_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(JsonConverterUtil.getProperty("Name", DoubleNode.valueOf(10.0d)));
  }

  /**
   * Test {@link JsonConverterUtil#getBpmnProcessModelChildShapesPropertyValues(JsonNode, String,
   * List)}.
   *
   * <ul>
   *   <li>Given {@code childShapes}.
   * </ul>
   *
   * <p>Method under test: {@link
   * JsonConverterUtil#getBpmnProcessModelChildShapesPropertyValues(JsonNode, String, List)}
   */
  @Test
  @DisplayName(
      "Test getBpmnProcessModelChildShapesPropertyValues(JsonNode, String, List); given 'childShapes'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List JsonConverterUtil.getBpmnProcessModelChildShapesPropertyValues(JsonNode, String, List)"
  })
  void testGetBpmnProcessModelChildShapesPropertyValues_givenChildShapes() {
    // Arrange
    DoubleNode editorJsonNode = DoubleNode.valueOf(10.0d);

    ArrayList<String> allowedStencilTypes = new ArrayList<>();
    allowedStencilTypes.add("childShapes");

    // Act
    List<JsonLookupResult> actualBpmnProcessModelChildShapesPropertyValues =
        JsonConverterUtil.getBpmnProcessModelChildShapesPropertyValues(
            editorJsonNode, "Property Name", allowedStencilTypes);

    // Assert
    assertTrue(actualBpmnProcessModelChildShapesPropertyValues.isEmpty());
  }

  /**
   * Test {@link JsonConverterUtil#getBpmnProcessModelChildShapesPropertyValues(JsonNode, String,
   * List)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link
   * JsonConverterUtil#getBpmnProcessModelChildShapesPropertyValues(JsonNode, String, List)}
   */
  @Test
  @DisplayName(
      "Test getBpmnProcessModelChildShapesPropertyValues(JsonNode, String, List); given 'foo'; when ArrayList() add 'foo'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List JsonConverterUtil.getBpmnProcessModelChildShapesPropertyValues(JsonNode, String, List)"
  })
  void testGetBpmnProcessModelChildShapesPropertyValues_givenFoo_whenArrayListAddFoo() {
    // Arrange
    DoubleNode editorJsonNode = DoubleNode.valueOf(10.0d);

    ArrayList<String> allowedStencilTypes = new ArrayList<>();
    allowedStencilTypes.add("foo");
    allowedStencilTypes.add("childShapes");

    // Act
    List<JsonLookupResult> actualBpmnProcessModelChildShapesPropertyValues =
        JsonConverterUtil.getBpmnProcessModelChildShapesPropertyValues(
            editorJsonNode, "Property Name", allowedStencilTypes);

    // Assert
    assertTrue(actualBpmnProcessModelChildShapesPropertyValues.isEmpty());
  }

  /**
   * Test {@link JsonConverterUtil#getBpmnProcessModelChildShapesPropertyValues(JsonNode, String,
   * List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * JsonConverterUtil#getBpmnProcessModelChildShapesPropertyValues(JsonNode, String, List)}
   */
  @Test
  @DisplayName(
      "Test getBpmnProcessModelChildShapesPropertyValues(JsonNode, String, List); when ArrayList(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List JsonConverterUtil.getBpmnProcessModelChildShapesPropertyValues(JsonNode, String, List)"
  })
  void testGetBpmnProcessModelChildShapesPropertyValues_whenArrayList_thenReturnEmpty() {
    // Arrange
    DoubleNode editorJsonNode = DoubleNode.valueOf(10.0d);

    // Act
    List<JsonLookupResult> actualBpmnProcessModelChildShapesPropertyValues =
        JsonConverterUtil.getBpmnProcessModelChildShapesPropertyValues(
            editorJsonNode, "Property Name", new ArrayList<>());

    // Assert
    assertTrue(actualBpmnProcessModelChildShapesPropertyValues.isEmpty());
  }

  /**
   * Test {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String,
   * List, List)}.
   *
   * <p>Method under test: {@link
   * JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  @DisplayName("Test internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)"
  })
  void testInternalGetBpmnProcessChildShapePropertyValues() {
    // Arrange
    ArrayNode editorJsonNode = mock(ArrayNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(editorJsonNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));
    ArrayList<String> allowedStencilTypes = new ArrayList<>();
    ArrayList<JsonLookupResult> result = new ArrayList<>();

    // Act
    JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(
        editorJsonNode, "Property Name", allowedStencilTypes, result);

    // Assert that nothing has changed
    verify(editorJsonNode).get("childShapes");
    assertTrue(result.isEmpty());
  }

  /**
   * Test {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String,
   * List, List)}.
   *
   * <p>Method under test: {@link
   * JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  @DisplayName("Test internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)"
  })
  void testInternalGetBpmnProcessChildShapePropertyValues2() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);

    ArrayNode editorJsonNode = mock(ArrayNode.class);
    when(editorJsonNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayList<String> allowedStencilTypes = new ArrayList<>();

    ArrayList<JsonLookupResult> result = new ArrayList<>();
    DoubleNode jsonNode = DoubleNode.valueOf(10.0d);
    JsonLookupResult jsonLookupResult = new JsonLookupResult("childShapes", jsonNode);
    result.add(jsonLookupResult);

    // Act
    JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(
        editorJsonNode, "Property Name", allowedStencilTypes, result);

    // Assert that nothing has changed
    verify(arrayNode).iterator();
    verify(editorJsonNode).get("childShapes");
    verify(arrayNode).isArray();
    assertEquals(1, result.size());
    JsonNode jsonNode2 = result.get(0).getJsonNode();
    assertTrue(jsonNode2 instanceof DoubleNode);
    assertSame(jsonNode, jsonNode2);
  }

  /**
   * Test {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String,
   * List, List)}.
   *
   * <p>Method under test: {@link
   * JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  @DisplayName("Test internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)"
  })
  void testInternalGetBpmnProcessChildShapePropertyValues3() {
    // Arrange
    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    jsonNodeList.add(new ArrayNode(nf));

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);

    ArrayNode editorJsonNode = mock(ArrayNode.class);
    when(editorJsonNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayList<String> allowedStencilTypes = new ArrayList<>();
    ArrayList<JsonLookupResult> result = new ArrayList<>();

    // Act
    JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(
        editorJsonNode, "Property Name", allowedStencilTypes, result);

    // Assert that nothing has changed
    verify(arrayNode).iterator();
    verify(editorJsonNode).get("childShapes");
    verify(arrayNode).isArray();
    assertTrue(result.isEmpty());
  }

  /**
   * Test {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String,
   * List, List)}.
   *
   * <p>Method under test: {@link
   * JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  @DisplayName("Test internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)"
  })
  void testInternalGetBpmnProcessChildShapePropertyValues4() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.has(Mockito.<String>any())).thenReturn(true);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode2.isArray()).thenReturn(true);

    ArrayNode editorJsonNode = mock(ArrayNode.class);
    when(editorJsonNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    ArrayList<String> allowedStencilTypes = new ArrayList<>();
    ArrayList<JsonLookupResult> result = new ArrayList<>();

    // Act
    JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(
        editorJsonNode, "Property Name", allowedStencilTypes, result);

    // Assert that nothing has changed
    verify(arrayNode).has("childShapes");
    verify(arrayNode2).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(editorJsonNode).get("childShapes");
    verify(arrayNode2).isArray();
    assertTrue(result.isEmpty());
  }

  /**
   * Test {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String,
   * List, List)}.
   *
   * <p>Method under test: {@link
   * JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  @DisplayName("Test internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)"
  })
  void testInternalGetBpmnProcessChildShapePropertyValues5() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.has(Mockito.<String>any())).thenReturn(true);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode2);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.iterator()).thenReturn(jsonNodeList2.iterator());
    when(arrayNode3.isArray()).thenReturn(true);

    ArrayNode editorJsonNode = mock(ArrayNode.class);
    when(editorJsonNode.get(Mockito.<String>any())).thenReturn(arrayNode3);
    ArrayList<String> allowedStencilTypes = new ArrayList<>();
    ArrayList<JsonLookupResult> result = new ArrayList<>();

    // Act
    JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(
        editorJsonNode, "Property Name", allowedStencilTypes, result);

    // Assert that nothing has changed
    verify(arrayNode2).has("childShapes");
    verify(arrayNode3).iterator();
    verify(arrayNode).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(editorJsonNode).get("childShapes");
    verify(arrayNode, atLeast(1)).get("id");
    verify(arrayNode3).isArray();
    verify(arrayNode).isArray();
    assertTrue(result.isEmpty());
  }

  /**
   * Test {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String,
   * List, List)}.
   *
   * <p>Method under test: {@link
   * JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  @DisplayName("Test internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)"
  })
  void testInternalGetBpmnProcessChildShapePropertyValues6() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.has(Mockito.<String>any())).thenReturn(true);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode2);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.iterator()).thenReturn(jsonNodeList2.iterator());
    when(arrayNode3.isArray()).thenReturn(true);

    ArrayNode editorJsonNode = mock(ArrayNode.class);
    when(editorJsonNode.get(Mockito.<String>any())).thenReturn(arrayNode3);
    ArrayList<String> allowedStencilTypes = new ArrayList<>();
    ArrayList<JsonLookupResult> result = new ArrayList<>();

    // Act
    JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(
        editorJsonNode, "Property Name", allowedStencilTypes, result);

    // Assert that nothing has changed
    verify(arrayNode2).has("childShapes");
    verify(arrayNode3).iterator();
    verify(arrayNode).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(editorJsonNode).get("childShapes");
    verify(arrayNode, atLeast(1)).get("id");
    verify(arrayNode3).isArray();
    verify(arrayNode).isArray();
    assertTrue(result.isEmpty());
  }

  /**
   * Test {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String,
   * List, List)}.
   *
   * <p>Method under test: {@link
   * JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  @DisplayName("Test internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)"
  })
  void testInternalGetBpmnProcessChildShapePropertyValues7() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("As Text");
    when(arrayNode.has(Mockito.<String>any())).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);
    MissingNode instance = MissingNode.getInstance();
    when(arrayNode.get(Mockito.<String>any())).thenReturn(instance);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.has(Mockito.<String>any())).thenReturn(true);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode2);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.iterator()).thenReturn(jsonNodeList2.iterator());
    when(arrayNode3.isArray()).thenReturn(true);

    ArrayNode editorJsonNode = mock(ArrayNode.class);
    when(editorJsonNode.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayList<String> allowedStencilTypes = new ArrayList<>();
    allowedStencilTypes.add("");
    allowedStencilTypes.add("childShapes");
    ArrayList<JsonLookupResult> result = new ArrayList<>();

    // Act
    JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(
        editorJsonNode, "Property Name", allowedStencilTypes, result);

    // Assert
    verify(arrayNode).has("Property Name");
    verify(arrayNode2).has("childShapes");
    verify(arrayNode3).iterator();
    verify(arrayNode).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(editorJsonNode).get("childShapes");
    verify(arrayNode3).isArray();
    verify(arrayNode).isArray();
    verify(arrayNode).asText();
    assertEquals(1, result.size());
    assertSame(instance, result.get(0).getJsonNode());
  }

  /**
   * Test {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String,
   * List, List)}.
   *
   * <p>Method under test: {@link
   * JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  @DisplayName("Test internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)"
  })
  void testInternalGetBpmnProcessChildShapePropertyValues8() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("As Text");
    when(arrayNode.has(Mockito.<String>any())).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode arrayNode2 = new ArrayNode(nf);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.has(Mockito.<String>any())).thenReturn(true);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode3);

    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.iterator()).thenReturn(jsonNodeList2.iterator());
    when(arrayNode4.isArray()).thenReturn(true);

    ArrayNode editorJsonNode = mock(ArrayNode.class);
    when(editorJsonNode.get(Mockito.<String>any())).thenReturn(arrayNode4);

    ArrayList<String> allowedStencilTypes = new ArrayList<>();
    allowedStencilTypes.add("");
    allowedStencilTypes.add("childShapes");
    ArrayList<JsonLookupResult> result = new ArrayList<>();

    // Act
    JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(
        editorJsonNode, "Property Name", allowedStencilTypes, result);

    // Assert
    verify(arrayNode).has("Property Name");
    verify(arrayNode3).has("childShapes");
    verify(arrayNode4).iterator();
    verify(arrayNode).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).get(Mockito.<String>any());
    verify(editorJsonNode).get("childShapes");
    verify(arrayNode4).isArray();
    verify(arrayNode).isArray();
    verify(arrayNode).asText();
    assertEquals(1, result.size());
    assertSame(arrayNode2, result.get(0).getJsonNode());
  }

  /**
   * Test {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String,
   * List, List)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link
   * JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  @DisplayName(
      "Test internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List); given ArrayList() add valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)"
  })
  void testInternalGetBpmnProcessChildShapePropertyValues_givenArrayListAddValueOfTen() {
    // Arrange
    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(DoubleNode.valueOf(10.0d));

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);

    ArrayNode editorJsonNode = mock(ArrayNode.class);
    when(editorJsonNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayList<String> allowedStencilTypes = new ArrayList<>();
    ArrayList<JsonLookupResult> result = new ArrayList<>();

    // Act
    JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(
        editorJsonNode, "Property Name", allowedStencilTypes, result);

    // Assert that nothing has changed
    verify(arrayNode).iterator();
    verify(editorJsonNode).get("childShapes");
    verify(arrayNode).isArray();
    assertTrue(result.isEmpty());
  }

  /**
   * Test {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String,
   * List, List)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#has(String)} return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link
   * JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  @DisplayName(
      "Test internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List); given ArrayNode has(String) return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)"
  })
  void testInternalGetBpmnProcessChildShapePropertyValues_givenArrayNodeHasReturnFalse() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.has(Mockito.<String>any())).thenReturn(false);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.has(Mockito.<String>any())).thenReturn(true);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode2);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.iterator()).thenReturn(jsonNodeList2.iterator());
    when(arrayNode3.isArray()).thenReturn(true);

    ArrayNode editorJsonNode = mock(ArrayNode.class);
    when(editorJsonNode.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayList<String> allowedStencilTypes = new ArrayList<>();
    allowedStencilTypes.add("");
    allowedStencilTypes.add("childShapes");
    ArrayList<JsonLookupResult> result = new ArrayList<>();

    // Act
    JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(
        editorJsonNode, "Property Name", allowedStencilTypes, result);

    // Assert that nothing has changed
    verify(arrayNode).has("Property Name");
    verify(arrayNode2).has("childShapes");
    verify(arrayNode3).iterator();
    verify(arrayNode).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(editorJsonNode).get("childShapes");
    verify(arrayNode, atLeast(1)).get("id");
    verify(arrayNode3).isArray();
    verify(arrayNode).isArray();
    assertTrue(result.isEmpty());
  }

  /**
   * Test {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String,
   * List, List)}.
   *
   * <ul>
   *   <li>Given {@code childShapes}.
   * </ul>
   *
   * <p>Method under test: {@link
   * JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  @DisplayName(
      "Test internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List); given 'childShapes'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)"
  })
  void testInternalGetBpmnProcessChildShapePropertyValues_givenChildShapes() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);

    ArrayNode editorJsonNode = mock(ArrayNode.class);
    when(editorJsonNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<String> allowedStencilTypes = new ArrayList<>();
    allowedStencilTypes.add("childShapes");
    ArrayList<JsonLookupResult> result = new ArrayList<>();

    // Act
    JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(
        editorJsonNode, "Property Name", allowedStencilTypes, result);

    // Assert that nothing has changed
    verify(arrayNode).iterator();
    verify(editorJsonNode).get("childShapes");
    verify(arrayNode).isArray();
    assertTrue(result.isEmpty());
  }

  /**
   * Test {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String,
   * List, List)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link
   * JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  @DisplayName(
      "Test internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List); given 'foo'; when ArrayList() add 'foo'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)"
  })
  void testInternalGetBpmnProcessChildShapePropertyValues_givenFoo_whenArrayListAddFoo() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);

    ArrayNode editorJsonNode = mock(ArrayNode.class);
    when(editorJsonNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<String> allowedStencilTypes = new ArrayList<>();
    allowedStencilTypes.add("foo");
    allowedStencilTypes.add("childShapes");
    ArrayList<JsonLookupResult> result = new ArrayList<>();

    // Act
    JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(
        editorJsonNode, "Property Name", allowedStencilTypes, result);

    // Assert that nothing has changed
    verify(arrayNode).iterator();
    verify(editorJsonNode).get("childShapes");
    verify(arrayNode).isArray();
    assertTrue(result.isEmpty());
  }

  /**
   * Test {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String,
   * List, List)}.
   *
   * <ul>
   *   <li>Given valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link
   * JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  @DisplayName(
      "Test internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List); given valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)"
  })
  void testInternalGetBpmnProcessChildShapePropertyValues_givenValueOfTen() {
    // Arrange
    ArrayNode editorJsonNode = mock(ArrayNode.class);
    when(editorJsonNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));
    ArrayList<String> allowedStencilTypes = new ArrayList<>();
    ArrayList<JsonLookupResult> result = new ArrayList<>();

    // Act
    JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(
        editorJsonNode, "Property Name", allowedStencilTypes, result);

    // Assert that nothing has changed
    verify(editorJsonNode).get("childShapes");
    assertTrue(result.isEmpty());
  }

  /**
   * Test {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String,
   * List, List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  @DisplayName(
      "Test internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List); then ArrayList() Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)"
  })
  void testInternalGetBpmnProcessChildShapePropertyValues_thenArrayListEmpty() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);

    ArrayNode editorJsonNode = mock(ArrayNode.class);
    when(editorJsonNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayList<String> allowedStencilTypes = new ArrayList<>();
    ArrayList<JsonLookupResult> result = new ArrayList<>();

    // Act
    JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(
        editorJsonNode, "Property Name", allowedStencilTypes, result);

    // Assert that nothing has changed
    verify(arrayNode).iterator();
    verify(editorJsonNode).get("childShapes");
    verify(arrayNode).isArray();
    assertTrue(result.isEmpty());
  }

  /**
   * Test {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String,
   * List, List)}.
   *
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  @DisplayName(
      "Test internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List); then ArrayList() size is two")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)"
  })
  void testInternalGetBpmnProcessChildShapePropertyValues_thenArrayListSizeIsTwo() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);

    ArrayNode editorJsonNode = mock(ArrayNode.class);
    when(editorJsonNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayList<String> allowedStencilTypes = new ArrayList<>();

    ArrayList<JsonLookupResult> result = new ArrayList<>();
    DoubleNode jsonNode = DoubleNode.valueOf(10.0d);
    JsonLookupResult jsonLookupResult = new JsonLookupResult("childShapes", jsonNode);
    result.add(jsonLookupResult);
    JsonLookupResult jsonLookupResult2 =
        new JsonLookupResult("childShapes", DoubleNode.valueOf(10.0d));
    result.add(jsonLookupResult2);

    // Act
    JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(
        editorJsonNode, "Property Name", allowedStencilTypes, result);

    // Assert that nothing has changed
    verify(arrayNode).iterator();
    verify(editorJsonNode).get("childShapes");
    verify(arrayNode).isArray();
    assertEquals(2, result.size());
    JsonNode jsonNode2 = result.get(0).getJsonNode();
    assertTrue(jsonNode2 instanceof DoubleNode);
    assertSame(jsonNode, jsonNode2);
  }

  /**
   * Test {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String,
   * List, List)}.
   *
   * <ul>
   *   <li>When valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link
   * JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  @DisplayName(
      "Test internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List); when valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)"
  })
  void testInternalGetBpmnProcessChildShapePropertyValues_whenValueOfTen() {
    // Arrange
    DoubleNode editorJsonNode = DoubleNode.valueOf(10.0d);
    ArrayList<String> allowedStencilTypes = new ArrayList<>();
    ArrayList<JsonLookupResult> result = new ArrayList<>();

    // Act
    JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(
        editorJsonNode, "Property Name", allowedStencilTypes, result);

    // Assert that nothing has changed
    assertTrue(result.isEmpty());
  }

  /**
   * Test {@link JsonConverterUtil#getBpmnProcessModelFormReferences(JsonNode)}.
   *
   * <ul>
   *   <li>When valueOf ten.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link JsonConverterUtil#getBpmnProcessModelFormReferences(JsonNode)}
   */
  @Test
  @DisplayName(
      "Test getBpmnProcessModelFormReferences(JsonNode); when valueOf ten; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List JsonConverterUtil.getBpmnProcessModelFormReferences(JsonNode)"})
  void testGetBpmnProcessModelFormReferences_whenValueOfTen_thenReturnEmpty() {
    // Arrange and Act
    List<JsonLookupResult> actualBpmnProcessModelFormReferences =
        JsonConverterUtil.getBpmnProcessModelFormReferences(DoubleNode.valueOf(10.0d));

    // Assert
    assertTrue(actualBpmnProcessModelFormReferences.isEmpty());
  }

  /**
   * Test {@link JsonConverterUtil#getBpmnProcessModelDecisionTableReferences(JsonNode)}.
   *
   * <ul>
   *   <li>When valueOf ten.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * JsonConverterUtil#getBpmnProcessModelDecisionTableReferences(JsonNode)}
   */
  @Test
  @DisplayName(
      "Test getBpmnProcessModelDecisionTableReferences(JsonNode); when valueOf ten; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List JsonConverterUtil.getBpmnProcessModelDecisionTableReferences(JsonNode)"})
  void testGetBpmnProcessModelDecisionTableReferences_whenValueOfTen_thenReturnEmpty() {
    // Arrange and Act
    List<JsonLookupResult> actualBpmnProcessModelDecisionTableReferences =
        JsonConverterUtil.getBpmnProcessModelDecisionTableReferences(DoubleNode.valueOf(10.0d));

    // Assert
    assertTrue(actualBpmnProcessModelDecisionTableReferences.isEmpty());
  }

  /**
   * Test {@link JsonConverterUtil#getAppModelReferencedProcessModels(JsonNode)}.
   *
   * <p>Method under test: {@link JsonConverterUtil#getAppModelReferencedProcessModels(JsonNode)}
   */
  @Test
  @DisplayName("Test getAppModelReferencedProcessModels(JsonNode)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List JsonConverterUtil.getAppModelReferencedProcessModels(JsonNode)"})
  void testGetAppModelReferencedProcessModels() {
    // Arrange
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);

    // Act
    List<JsonNode> actualAppModelReferencedProcessModels =
        JsonConverterUtil.getAppModelReferencedProcessModels(new ArrayNode(nf));

    // Assert
    assertTrue(actualAppModelReferencedProcessModels.isEmpty());
  }

  /**
   * Test {@link JsonConverterUtil#getAppModelReferencedProcessModels(JsonNode)}.
   *
   * <ul>
   *   <li>When valueOf ten.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link JsonConverterUtil#getAppModelReferencedProcessModels(JsonNode)}
   */
  @Test
  @DisplayName(
      "Test getAppModelReferencedProcessModels(JsonNode); when valueOf ten; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List JsonConverterUtil.getAppModelReferencedProcessModels(JsonNode)"})
  void testGetAppModelReferencedProcessModels_whenValueOfTen_thenReturnEmpty() {
    // Arrange and Act
    List<JsonNode> actualAppModelReferencedProcessModels =
        JsonConverterUtil.getAppModelReferencedProcessModels(DoubleNode.valueOf(10.0d));

    // Assert
    assertTrue(actualAppModelReferencedProcessModels.isEmpty());
  }

  /**
   * Test {@link JsonConverterUtil#getAppModelReferencedModelIds(JsonNode)}.
   *
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link JsonConverterUtil#getAppModelReferencedModelIds(JsonNode)}
   */
  @Test
  @DisplayName(
      "Test getAppModelReferencedModelIds(JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Set JsonConverterUtil.getAppModelReferencedModelIds(JsonNode)"})
  void testGetAppModelReferencedModelIds_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);

    // Act
    Set<String> actualAppModelReferencedModelIds =
        JsonConverterUtil.getAppModelReferencedModelIds(new ArrayNode(nf));

    // Assert
    assertTrue(actualAppModelReferencedModelIds.isEmpty());
  }

  /**
   * Test {@link JsonConverterUtil#getAppModelReferencedModelIds(JsonNode)}.
   *
   * <ul>
   *   <li>When valueOf ten.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link JsonConverterUtil#getAppModelReferencedModelIds(JsonNode)}
   */
  @Test
  @DisplayName("Test getAppModelReferencedModelIds(JsonNode); when valueOf ten; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Set JsonConverterUtil.getAppModelReferencedModelIds(JsonNode)"})
  void testGetAppModelReferencedModelIds_whenValueOfTen_thenReturnEmpty() {
    // Arrange and Act
    Set<String> actualAppModelReferencedModelIds =
        JsonConverterUtil.getAppModelReferencedModelIds(DoubleNode.valueOf(10.0d));

    // Assert
    assertTrue(actualAppModelReferencedModelIds.isEmpty());
  }

  /**
   * Test {@link JsonConverterUtil#gatherLongPropertyFromJsonNodes(Iterable, String)}.
   *
   * <p>Method under test: {@link JsonConverterUtil#gatherLongPropertyFromJsonNodes(Iterable,
   * String)}
   */
  @Test
  @DisplayName("Test gatherLongPropertyFromJsonNodes(Iterable, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Set JsonConverterUtil.gatherLongPropertyFromJsonNodes(Iterable, String)"})
  void testGatherLongPropertyFromJsonNodes() {
    // Arrange
    LinkedHashSet<JsonNode> jsonNodes = new LinkedHashSet<>();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    jsonNodes.add(new ArrayNode(nf));

    // Act
    Set<Long> actualGatherLongPropertyFromJsonNodesResult =
        JsonConverterUtil.gatherLongPropertyFromJsonNodes(jsonNodes, "Property Name");

    // Assert
    assertTrue(actualGatherLongPropertyFromJsonNodesResult.isEmpty());
  }

  /**
   * Test {@link JsonConverterUtil#gatherLongPropertyFromJsonNodes(Iterable, String)}.
   *
   * <ul>
   *   <li>Given valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link JsonConverterUtil#gatherLongPropertyFromJsonNodes(Iterable,
   * String)}
   */
  @Test
  @DisplayName("Test gatherLongPropertyFromJsonNodes(Iterable, String); given valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Set JsonConverterUtil.gatherLongPropertyFromJsonNodes(Iterable, String)"})
  void testGatherLongPropertyFromJsonNodes_givenValueOfTen() {
    // Arrange
    LinkedHashSet<JsonNode> jsonNodes = new LinkedHashSet<>();
    jsonNodes.add(DoubleNode.valueOf(10.0d));

    // Act
    Set<Long> actualGatherLongPropertyFromJsonNodesResult =
        JsonConverterUtil.gatherLongPropertyFromJsonNodes(jsonNodes, "Property Name");

    // Assert
    assertTrue(actualGatherLongPropertyFromJsonNodesResult.isEmpty());
  }

  /**
   * Test {@link JsonConverterUtil#gatherLongPropertyFromJsonNodes(Iterable, String)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link JsonConverterUtil#gatherLongPropertyFromJsonNodes(Iterable,
   * String)}
   */
  @Test
  @DisplayName(
      "Test gatherLongPropertyFromJsonNodes(Iterable, String); when ArrayList(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Set JsonConverterUtil.gatherLongPropertyFromJsonNodes(Iterable, String)"})
  void testGatherLongPropertyFromJsonNodes_whenArrayList_thenReturnEmpty() {
    // Arrange and Act
    Set<Long> actualGatherLongPropertyFromJsonNodesResult =
        JsonConverterUtil.gatherLongPropertyFromJsonNodes(new ArrayList<>(), "Property Name");

    // Assert
    assertTrue(actualGatherLongPropertyFromJsonNodesResult.isEmpty());
  }

  /**
   * Test {@link JsonConverterUtil#gatherStringPropertyFromJsonNodes(Iterable, String)}.
   *
   * <p>Method under test: {@link JsonConverterUtil#gatherStringPropertyFromJsonNodes(Iterable,
   * String)}
   */
  @Test
  @DisplayName("Test gatherStringPropertyFromJsonNodes(Iterable, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Set JsonConverterUtil.gatherStringPropertyFromJsonNodes(Iterable, String)"})
  void testGatherStringPropertyFromJsonNodes() {
    // Arrange
    LinkedHashSet<JsonNode> jsonNodes = new LinkedHashSet<>();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    jsonNodes.add(new ArrayNode(nf));

    // Act
    Set<String> actualGatherStringPropertyFromJsonNodesResult =
        JsonConverterUtil.gatherStringPropertyFromJsonNodes(jsonNodes, "Property Name");

    // Assert
    assertTrue(actualGatherStringPropertyFromJsonNodesResult.isEmpty());
  }

  /**
   * Test {@link JsonConverterUtil#gatherStringPropertyFromJsonNodes(Iterable, String)}.
   *
   * <ul>
   *   <li>Given valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link JsonConverterUtil#gatherStringPropertyFromJsonNodes(Iterable,
   * String)}
   */
  @Test
  @DisplayName("Test gatherStringPropertyFromJsonNodes(Iterable, String); given valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Set JsonConverterUtil.gatherStringPropertyFromJsonNodes(Iterable, String)"})
  void testGatherStringPropertyFromJsonNodes_givenValueOfTen() {
    // Arrange
    LinkedHashSet<JsonNode> jsonNodes = new LinkedHashSet<>();
    jsonNodes.add(DoubleNode.valueOf(10.0d));

    // Act
    Set<String> actualGatherStringPropertyFromJsonNodesResult =
        JsonConverterUtil.gatherStringPropertyFromJsonNodes(jsonNodes, "Property Name");

    // Assert
    assertTrue(actualGatherStringPropertyFromJsonNodesResult.isEmpty());
  }

  /**
   * Test {@link JsonConverterUtil#gatherStringPropertyFromJsonNodes(Iterable, String)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link JsonConverterUtil#gatherStringPropertyFromJsonNodes(Iterable,
   * String)}
   */
  @Test
  @DisplayName(
      "Test gatherStringPropertyFromJsonNodes(Iterable, String); when ArrayList(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Set JsonConverterUtil.gatherStringPropertyFromJsonNodes(Iterable, String)"})
  void testGatherStringPropertyFromJsonNodes_whenArrayList_thenReturnEmpty() {
    // Arrange and Act
    Set<String> actualGatherStringPropertyFromJsonNodesResult =
        JsonConverterUtil.gatherStringPropertyFromJsonNodes(new ArrayList<>(), "Property Name");

    // Assert
    assertTrue(actualGatherStringPropertyFromJsonNodesResult.isEmpty());
  }

  /**
   * Test {@link JsonConverterUtil#filterOutJsonNodes(List)}.
   *
   * <ul>
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link JsonConverterUtil#filterOutJsonNodes(List)}
   */
  @Test
  @DisplayName("Test filterOutJsonNodes(List); then return size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List JsonConverterUtil.filterOutJsonNodes(List)"})
  void testFilterOutJsonNodes_thenReturnSizeIsOne() {
    // Arrange
    ArrayList<JsonLookupResult> lookupResults = new ArrayList<>();
    DoubleNode jsonNode = DoubleNode.valueOf(10.0d);
    JsonLookupResult jsonLookupResult = new JsonLookupResult("Name", jsonNode);
    lookupResults.add(jsonLookupResult);

    // Act
    List<JsonNode> actualFilterOutJsonNodesResult =
        JsonConverterUtil.filterOutJsonNodes(lookupResults);

    // Assert
    assertEquals(1, actualFilterOutJsonNodesResult.size());
    JsonNode getResult = actualFilterOutJsonNodesResult.get(0);
    assertTrue(getResult instanceof DoubleNode);
    assertSame(jsonNode, getResult);
  }

  /**
   * Test {@link JsonConverterUtil#filterOutJsonNodes(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link JsonConverterUtil#filterOutJsonNodes(List)}
   */
  @Test
  @DisplayName("Test filterOutJsonNodes(List); when ArrayList(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List JsonConverterUtil.filterOutJsonNodes(List)"})
  void testFilterOutJsonNodes_whenArrayList_thenReturnEmpty() {
    // Arrange and Act
    List<JsonNode> actualFilterOutJsonNodesResult =
        JsonConverterUtil.filterOutJsonNodes(new ArrayList<>());

    // Assert
    assertTrue(actualFilterOutJsonNodesResult.isEmpty());
  }

  /**
   * Test JsonLookupResult getters and setters.
   *
   * <ul>
   *   <li>When {@code 42}.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link JsonLookupResult#JsonLookupResult(String, String, JsonNode)}
   *   <li>{@link JsonLookupResult#setId(String)}
   *   <li>{@link JsonLookupResult#setJsonNode(JsonNode)}
   *   <li>{@link JsonLookupResult#setName(String)}
   *   <li>{@link JsonLookupResult#getId()}
   *   <li>{@link JsonLookupResult#getJsonNode()}
   *   <li>{@link JsonLookupResult#getName()}
   * </ul>
   */
  @Test
  @DisplayName("Test JsonLookupResult getters and setters; when '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void JsonLookupResult.<init>(String, JsonNode)",
    "void JsonLookupResult.<init>(String, String, JsonNode)",
    "String JsonLookupResult.getId()",
    "JsonNode JsonLookupResult.getJsonNode()",
    "String JsonLookupResult.getName()",
    "void JsonLookupResult.setId(String)",
    "void JsonLookupResult.setJsonNode(JsonNode)",
    "void JsonLookupResult.setName(String)"
  })
  void testJsonLookupResultGettersAndSetters_when42() {
    // Arrange and Act
    JsonLookupResult actualJsonLookupResult =
        new JsonLookupResult("42", "Name", DoubleNode.valueOf(10.0d));
    actualJsonLookupResult.setId("42");
    DoubleNode jsonNode = DoubleNode.valueOf(10.0d);
    actualJsonLookupResult.setJsonNode(jsonNode);
    actualJsonLookupResult.setName("Name");
    String actualId = actualJsonLookupResult.getId();
    JsonNode actualJsonNode = actualJsonLookupResult.getJsonNode();

    // Assert
    assertEquals("42", actualId);
    assertEquals("Name", actualJsonLookupResult.getName());
    assertSame(jsonNode, actualJsonNode);
  }

  /**
   * Test JsonLookupResult getters and setters.
   *
   * <ul>
   *   <li>When valueOf ten.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link JsonLookupResult#JsonLookupResult(String, JsonNode)}
   *   <li>{@link JsonLookupResult#setId(String)}
   *   <li>{@link JsonLookupResult#setJsonNode(JsonNode)}
   *   <li>{@link JsonLookupResult#setName(String)}
   *   <li>{@link JsonLookupResult#getId()}
   *   <li>{@link JsonLookupResult#getJsonNode()}
   *   <li>{@link JsonLookupResult#getName()}
   * </ul>
   */
  @Test
  @DisplayName("Test JsonLookupResult getters and setters; when valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void JsonLookupResult.<init>(String, JsonNode)",
    "void JsonLookupResult.<init>(String, String, JsonNode)",
    "String JsonLookupResult.getId()",
    "JsonNode JsonLookupResult.getJsonNode()",
    "String JsonLookupResult.getName()",
    "void JsonLookupResult.setId(String)",
    "void JsonLookupResult.setJsonNode(JsonNode)",
    "void JsonLookupResult.setName(String)"
  })
  void testJsonLookupResultGettersAndSetters_whenValueOfTen() {
    // Arrange and Act
    JsonLookupResult actualJsonLookupResult =
        new JsonLookupResult("Name", DoubleNode.valueOf(10.0d));
    actualJsonLookupResult.setId("42");
    DoubleNode jsonNode = DoubleNode.valueOf(10.0d);
    actualJsonLookupResult.setJsonNode(jsonNode);
    actualJsonLookupResult.setName("Name");
    String actualId = actualJsonLookupResult.getId();
    JsonNode actualJsonNode = actualJsonLookupResult.getJsonNode();

    // Assert
    assertEquals("42", actualId);
    assertEquals("Name", actualJsonLookupResult.getName());
    assertSame(jsonNode, actualJsonNode);
  }
}
