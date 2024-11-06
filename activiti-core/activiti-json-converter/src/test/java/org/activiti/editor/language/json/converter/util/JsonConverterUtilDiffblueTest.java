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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ContainerNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.MissingNode;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class JsonConverterUtilDiffblueTest {
  /**
   * Test {@link JsonConverterUtil#getPropertyValueAsString(String, JsonNode)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#getPropertyValueAsString(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getPropertyValueAsString(String, JsonNode); when Instance; then return 'null'")
  void testGetPropertyValueAsString_whenInstance_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(JsonConverterUtil.getPropertyValueAsString("Name", MissingNode.getInstance()));
  }

  /**
   * Test
   * {@link JsonConverterUtil#getPropertyValueAsBoolean(String, JsonNode, boolean)}
   * with {@code name}, {@code objectNode}, {@code defaultValue}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#getPropertyValueAsBoolean(String, JsonNode, boolean)}
   */
  @Test
  @DisplayName("Test getPropertyValueAsBoolean(String, JsonNode, boolean) with 'name', 'objectNode', 'defaultValue'; then return 'false'")
  void testGetPropertyValueAsBooleanWithNameObjectNodeDefaultValue_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(JsonConverterUtil.getPropertyValueAsBoolean("Name", MissingNode.getInstance(), false));
  }

  /**
   * Test
   * {@link JsonConverterUtil#getPropertyValueAsBoolean(String, JsonNode, boolean)}
   * with {@code name}, {@code objectNode}, {@code defaultValue}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#getPropertyValueAsBoolean(String, JsonNode, boolean)}
   */
  @Test
  @DisplayName("Test getPropertyValueAsBoolean(String, JsonNode, boolean) with 'name', 'objectNode', 'defaultValue'; then return 'true'")
  void testGetPropertyValueAsBooleanWithNameObjectNodeDefaultValue_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(JsonConverterUtil.getPropertyValueAsBoolean("Name", MissingNode.getInstance(), true));
  }

  /**
   * Test {@link JsonConverterUtil#getPropertyValueAsBoolean(String, JsonNode)}
   * with {@code name}, {@code objectNode}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#getPropertyValueAsBoolean(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getPropertyValueAsBoolean(String, JsonNode) with 'name', 'objectNode'; when Instance; then return 'false'")
  void testGetPropertyValueAsBooleanWithNameObjectNode_whenInstance_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(JsonConverterUtil.getPropertyValueAsBoolean("Name", MissingNode.getInstance()));
  }

  /**
   * Test {@link JsonConverterUtil#getPropertyValueAsList(String, JsonNode)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#getPropertyValueAsList(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getPropertyValueAsList(String, JsonNode); when Instance; then return Empty")
  void testGetPropertyValueAsList_whenInstance_thenReturnEmpty() {
    // Arrange and Act
    List<String> actualPropertyValueAsList = JsonConverterUtil.getPropertyValueAsList("Name",
        MissingNode.getInstance());

    // Assert
    assertTrue(actualPropertyValueAsList.isEmpty());
  }

  /**
   * Test {@link JsonConverterUtil#getProperty(String, JsonNode)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonConverterUtil#getProperty(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getProperty(String, JsonNode); when Instance; then return 'null'")
  void testGetProperty_whenInstance_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(JsonConverterUtil.getProperty("Name", MissingNode.getInstance()));
  }

  /**
   * Test
   * {@link JsonConverterUtil#getBpmnProcessModelChildShapesPropertyValues(JsonNode, String, List)}.
   * <ul>
   *   <li>Given {@code childShapes}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#getBpmnProcessModelChildShapesPropertyValues(JsonNode, String, List)}
   */
  @Test
  @DisplayName("Test getBpmnProcessModelChildShapesPropertyValues(JsonNode, String, List); given 'childShapes'")
  void testGetBpmnProcessModelChildShapesPropertyValues_givenChildShapes() {
    // Arrange
    MissingNode editorJsonNode = MissingNode.getInstance();

    ArrayList<String> allowedStencilTypes = new ArrayList<>();
    allowedStencilTypes.add("childShapes");

    // Act
    List<JsonConverterUtil.JsonLookupResult> actualBpmnProcessModelChildShapesPropertyValues = JsonConverterUtil
        .getBpmnProcessModelChildShapesPropertyValues(editorJsonNode, "Property Name", allowedStencilTypes);

    // Assert
    assertTrue(actualBpmnProcessModelChildShapesPropertyValues.isEmpty());
  }

  /**
   * Test
   * {@link JsonConverterUtil#getBpmnProcessModelChildShapesPropertyValues(JsonNode, String, List)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#getBpmnProcessModelChildShapesPropertyValues(JsonNode, String, List)}
   */
  @Test
  @DisplayName("Test getBpmnProcessModelChildShapesPropertyValues(JsonNode, String, List); given 'foo'; when ArrayList() add 'foo'")
  void testGetBpmnProcessModelChildShapesPropertyValues_givenFoo_whenArrayListAddFoo() {
    // Arrange
    MissingNode editorJsonNode = MissingNode.getInstance();

    ArrayList<String> allowedStencilTypes = new ArrayList<>();
    allowedStencilTypes.add("foo");
    allowedStencilTypes.add("childShapes");

    // Act
    List<JsonConverterUtil.JsonLookupResult> actualBpmnProcessModelChildShapesPropertyValues = JsonConverterUtil
        .getBpmnProcessModelChildShapesPropertyValues(editorJsonNode, "Property Name", allowedStencilTypes);

    // Assert
    assertTrue(actualBpmnProcessModelChildShapesPropertyValues.isEmpty());
  }

  /**
   * Test
   * {@link JsonConverterUtil#getBpmnProcessModelChildShapesPropertyValues(JsonNode, String, List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#getBpmnProcessModelChildShapesPropertyValues(JsonNode, String, List)}
   */
  @Test
  @DisplayName("Test getBpmnProcessModelChildShapesPropertyValues(JsonNode, String, List); when ArrayList(); then return Empty")
  void testGetBpmnProcessModelChildShapesPropertyValues_whenArrayList_thenReturnEmpty() {
    // Arrange
    MissingNode editorJsonNode = MissingNode.getInstance();

    // Act
    List<JsonConverterUtil.JsonLookupResult> actualBpmnProcessModelChildShapesPropertyValues = JsonConverterUtil
        .getBpmnProcessModelChildShapesPropertyValues(editorJsonNode, "Property Name", new ArrayList<>());

    // Assert
    assertTrue(actualBpmnProcessModelChildShapesPropertyValues.isEmpty());
  }

  /**
   * Test
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}.
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  @DisplayName("Test internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)")
  void testInternalGetBpmnProcessChildShapePropertyValues() {
    // Arrange
    ArrayNode editorJsonNode = mock(ArrayNode.class);
    when(editorJsonNode.get(Mockito.<String>any()))
        .thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ArrayList<String> allowedStencilTypes = new ArrayList<>();
    ArrayList<JsonConverterUtil.JsonLookupResult> result = new ArrayList<>();

    // Act
    JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(editorJsonNode, "Property Name",
        allowedStencilTypes, result);

    // Assert that nothing has changed
    verify(editorJsonNode).get(eq("childShapes"));
    assertTrue(result.isEmpty());
  }

  /**
   * Test
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}.
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  @DisplayName("Test internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)")
  void testInternalGetBpmnProcessChildShapePropertyValues2() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);
    ArrayNode editorJsonNode = mock(ArrayNode.class);
    when(editorJsonNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayList<String> allowedStencilTypes = new ArrayList<>();

    ArrayList<JsonConverterUtil.JsonLookupResult> result = new ArrayList<>();
    MissingNode jsonNode = MissingNode.getInstance();
    result.add(new JsonConverterUtil.JsonLookupResult("childShapes", jsonNode));

    // Act
    JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(editorJsonNode, "Property Name",
        allowedStencilTypes, result);

    // Assert that nothing has changed
    verify(arrayNode).iterator();
    verify(editorJsonNode).get(eq("childShapes"));
    verify(arrayNode).isArray();
    assertEquals(1, result.size());
    JsonConverterUtil.JsonLookupResult getResult = result.get(0);
    assertEquals("childShapes", getResult.getName());
    assertSame(jsonNode, getResult.getJsonNode());
  }

  /**
   * Test
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}.
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  @DisplayName("Test internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)")
  void testInternalGetBpmnProcessChildShapePropertyValues3() {
    // Arrange
    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);
    ArrayNode editorJsonNode = mock(ArrayNode.class);
    when(editorJsonNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayList<String> allowedStencilTypes = new ArrayList<>();
    ArrayList<JsonConverterUtil.JsonLookupResult> result = new ArrayList<>();

    // Act
    JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(editorJsonNode, "Property Name",
        allowedStencilTypes, result);

    // Assert that nothing has changed
    verify(arrayNode).iterator();
    verify(editorJsonNode).get(eq("childShapes"));
    verify(arrayNode).isArray();
    assertTrue(result.isEmpty());
  }

  /**
   * Test
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}.
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  @DisplayName("Test internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)")
  void testInternalGetBpmnProcessChildShapePropertyValues4() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.has(Mockito.<String>any())).thenReturn(true);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode2.isArray()).thenReturn(true);
    ArrayNode editorJsonNode = mock(ArrayNode.class);
    when(editorJsonNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    ArrayList<String> allowedStencilTypes = new ArrayList<>();
    ArrayList<JsonConverterUtil.JsonLookupResult> result = new ArrayList<>();

    // Act
    JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(editorJsonNode, "Property Name",
        allowedStencilTypes, result);

    // Assert that nothing has changed
    verify(arrayNode).has(eq("childShapes"));
    verify(arrayNode2).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(editorJsonNode).get(eq("childShapes"));
    verify(arrayNode2).isArray();
    assertTrue(result.isEmpty());
  }

  /**
   * Test
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}.
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  @DisplayName("Test internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)")
  void testInternalGetBpmnProcessChildShapePropertyValues5() {
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
    ArrayList<JsonConverterUtil.JsonLookupResult> result = new ArrayList<>();

    // Act
    JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(editorJsonNode, "Property Name",
        allowedStencilTypes, result);

    // Assert that nothing has changed
    verify(arrayNode2).has(eq("childShapes"));
    verify(arrayNode3).iterator();
    verify(arrayNode).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(editorJsonNode).get(eq("childShapes"));
    verify(arrayNode, atLeast(1)).get(eq("id"));
    verify(arrayNode3).isArray();
    verify(arrayNode).isArray();
    assertTrue(result.isEmpty());
  }

  /**
   * Test
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}.
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  @DisplayName("Test internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)")
  void testInternalGetBpmnProcessChildShapePropertyValues6() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
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
    ArrayList<JsonConverterUtil.JsonLookupResult> result = new ArrayList<>();

    // Act
    JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(editorJsonNode, "Property Name",
        allowedStencilTypes, result);

    // Assert that nothing has changed
    verify(arrayNode2).has(eq("childShapes"));
    verify(arrayNode3).iterator();
    verify(arrayNode).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(editorJsonNode).get(eq("childShapes"));
    verify(arrayNode, atLeast(1)).get(eq("id"));
    verify(arrayNode3).isArray();
    verify(arrayNode).isArray();
    assertTrue(result.isEmpty());
  }

  /**
   * Test
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}.
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  @DisplayName("Test internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)")
  void testInternalGetBpmnProcessChildShapePropertyValues7() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("As Text");
    when(arrayNode.has(Mockito.<String>any())).thenReturn(true);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);
    ArrayNode arrayNode2 = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
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
    ArrayList<JsonConverterUtil.JsonLookupResult> result = new ArrayList<>();

    // Act
    JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(editorJsonNode, "Property Name",
        allowedStencilTypes, result);

    // Assert
    verify(arrayNode).has(eq("Property Name"));
    verify(arrayNode3).has(eq("childShapes"));
    verify(arrayNode4).iterator();
    verify(arrayNode).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).get(Mockito.<String>any());
    verify(editorJsonNode).get(eq("childShapes"));
    verify(arrayNode4).isArray();
    verify(arrayNode).isArray();
    verify(arrayNode).asText();
    assertEquals(1, result.size());
    assertSame(arrayNode2, result.get(0).getJsonNode());
  }

  /**
   * Test
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  @DisplayName("Test internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List); given ArrayList() add Instance")
  void testInternalGetBpmnProcessChildShapePropertyValues_givenArrayListAddInstance() {
    // Arrange
    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(MissingNode.getInstance());
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);
    ArrayNode editorJsonNode = mock(ArrayNode.class);
    when(editorJsonNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayList<String> allowedStencilTypes = new ArrayList<>();
    ArrayList<JsonConverterUtil.JsonLookupResult> result = new ArrayList<>();

    // Act
    JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(editorJsonNode, "Property Name",
        allowedStencilTypes, result);

    // Assert that nothing has changed
    verify(arrayNode).iterator();
    verify(editorJsonNode).get(eq("childShapes"));
    verify(arrayNode).isArray();
    assertTrue(result.isEmpty());
  }

  /**
   * Test
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link JsonNode#has(String)} return
   * {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  @DisplayName("Test internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List); given ArrayNode has(String) return 'false'")
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
    ArrayList<JsonConverterUtil.JsonLookupResult> result = new ArrayList<>();

    // Act
    JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(editorJsonNode, "Property Name",
        allowedStencilTypes, result);

    // Assert that nothing has changed
    verify(arrayNode).has(eq("Property Name"));
    verify(arrayNode2).has(eq("childShapes"));
    verify(arrayNode3).iterator();
    verify(arrayNode).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(editorJsonNode).get(eq("childShapes"));
    verify(arrayNode, atLeast(1)).get(eq("id"));
    verify(arrayNode3).isArray();
    verify(arrayNode).isArray();
    assertTrue(result.isEmpty());
  }

  /**
   * Test
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}.
   * <ul>
   *   <li>Given {@code childShapes}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  @DisplayName("Test internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List); given 'childShapes'")
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
    ArrayList<JsonConverterUtil.JsonLookupResult> result = new ArrayList<>();

    // Act
    JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(editorJsonNode, "Property Name",
        allowedStencilTypes, result);

    // Assert that nothing has changed
    verify(arrayNode).iterator();
    verify(editorJsonNode).get(eq("childShapes"));
    verify(arrayNode).isArray();
    assertTrue(result.isEmpty());
  }

  /**
   * Test
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  @DisplayName("Test internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List); given 'foo'; when ArrayList() add 'foo'")
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
    ArrayList<JsonConverterUtil.JsonLookupResult> result = new ArrayList<>();

    // Act
    JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(editorJsonNode, "Property Name",
        allowedStencilTypes, result);

    // Assert that nothing has changed
    verify(arrayNode).iterator();
    verify(editorJsonNode).get(eq("childShapes"));
    verify(arrayNode).isArray();
    assertTrue(result.isEmpty());
  }

  /**
   * Test
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}.
   * <ul>
   *   <li>Given Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  @DisplayName("Test internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List); given Instance")
  void testInternalGetBpmnProcessChildShapePropertyValues_givenInstance() {
    // Arrange
    ArrayNode editorJsonNode = mock(ArrayNode.class);
    when(editorJsonNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    ArrayList<String> allowedStencilTypes = new ArrayList<>();
    ArrayList<JsonConverterUtil.JsonLookupResult> result = new ArrayList<>();

    // Act
    JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(editorJsonNode, "Property Name",
        allowedStencilTypes, result);

    // Assert that nothing has changed
    verify(editorJsonNode).get(eq("childShapes"));
    assertTrue(result.isEmpty());
  }

  /**
   * Test
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  @DisplayName("Test internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List); then ArrayList() Empty")
  void testInternalGetBpmnProcessChildShapePropertyValues_thenArrayListEmpty() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);
    ArrayNode editorJsonNode = mock(ArrayNode.class);
    when(editorJsonNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayList<String> allowedStencilTypes = new ArrayList<>();
    ArrayList<JsonConverterUtil.JsonLookupResult> result = new ArrayList<>();

    // Act
    JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(editorJsonNode, "Property Name",
        allowedStencilTypes, result);

    // Assert that nothing has changed
    verify(arrayNode).iterator();
    verify(editorJsonNode).get(eq("childShapes"));
    verify(arrayNode).isArray();
    assertTrue(result.isEmpty());
  }

  /**
   * Test
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}.
   * <ul>
   *   <li>Then {@link ArrayList#ArrayList()} size is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  @DisplayName("Test internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List); then ArrayList() size is two")
  void testInternalGetBpmnProcessChildShapePropertyValues_thenArrayListSizeIsTwo() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);
    ArrayNode editorJsonNode = mock(ArrayNode.class);
    when(editorJsonNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayList<String> allowedStencilTypes = new ArrayList<>();

    ArrayList<JsonConverterUtil.JsonLookupResult> result = new ArrayList<>();
    result.add(new JsonConverterUtil.JsonLookupResult("childShapes", MissingNode.getInstance()));
    MissingNode jsonNode = MissingNode.getInstance();
    result.add(new JsonConverterUtil.JsonLookupResult("childShapes", jsonNode));

    // Act
    JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(editorJsonNode, "Property Name",
        allowedStencilTypes, result);

    // Assert that nothing has changed
    verify(arrayNode).iterator();
    verify(editorJsonNode).get(eq("childShapes"));
    verify(arrayNode).isArray();
    assertEquals(2, result.size());
    JsonConverterUtil.JsonLookupResult getResult = result.get(0);
    assertEquals("childShapes", getResult.getName());
    assertSame(jsonNode, getResult.getJsonNode());
  }

  /**
   * Test
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}.
   * <ul>
   *   <li>Then calls {@link ContainerNode#asText()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  @DisplayName("Test internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List); then calls asText()")
  void testInternalGetBpmnProcessChildShapePropertyValues_thenCallsAsText() {
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
    ArrayList<JsonConverterUtil.JsonLookupResult> result = new ArrayList<>();

    // Act
    JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(editorJsonNode, "Property Name",
        allowedStencilTypes, result);

    // Assert
    verify(arrayNode).has(eq("Property Name"));
    verify(arrayNode2).has(eq("childShapes"));
    verify(arrayNode3).iterator();
    verify(arrayNode).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(editorJsonNode).get(eq("childShapes"));
    verify(arrayNode3).isArray();
    verify(arrayNode).isArray();
    verify(arrayNode).asText();
    assertEquals(1, result.size());
    assertSame(instance, result.get(0).getJsonNode());
  }

  /**
   * Test
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}.
   * <ul>
   *   <li>When Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  @DisplayName("Test internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List); when Instance")
  void testInternalGetBpmnProcessChildShapePropertyValues_whenInstance() {
    // Arrange
    MissingNode editorJsonNode = MissingNode.getInstance();
    ArrayList<String> allowedStencilTypes = new ArrayList<>();
    ArrayList<JsonConverterUtil.JsonLookupResult> result = new ArrayList<>();

    // Act
    JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(editorJsonNode, "Property Name",
        allowedStencilTypes, result);

    // Assert that nothing has changed
    assertTrue(result.isEmpty());
  }

  /**
   * Test {@link JsonConverterUtil#getBpmnProcessModelFormReferences(JsonNode)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#getBpmnProcessModelFormReferences(JsonNode)}
   */
  @Test
  @DisplayName("Test getBpmnProcessModelFormReferences(JsonNode); when Instance; then return Empty")
  void testGetBpmnProcessModelFormReferences_whenInstance_thenReturnEmpty() {
    // Arrange and Act
    List<JsonConverterUtil.JsonLookupResult> actualBpmnProcessModelFormReferences = JsonConverterUtil
        .getBpmnProcessModelFormReferences(MissingNode.getInstance());

    // Assert
    assertTrue(actualBpmnProcessModelFormReferences.isEmpty());
  }

  /**
   * Test
   * {@link JsonConverterUtil#getBpmnProcessModelDecisionTableReferences(JsonNode)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#getBpmnProcessModelDecisionTableReferences(JsonNode)}
   */
  @Test
  @DisplayName("Test getBpmnProcessModelDecisionTableReferences(JsonNode); when Instance; then return Empty")
  void testGetBpmnProcessModelDecisionTableReferences_whenInstance_thenReturnEmpty() {
    // Arrange and Act
    List<JsonConverterUtil.JsonLookupResult> actualBpmnProcessModelDecisionTableReferences = JsonConverterUtil
        .getBpmnProcessModelDecisionTableReferences(MissingNode.getInstance());

    // Assert
    assertTrue(actualBpmnProcessModelDecisionTableReferences.isEmpty());
  }

  /**
   * Test {@link JsonConverterUtil#getAppModelReferencedProcessModels(JsonNode)}.
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#getAppModelReferencedProcessModels(JsonNode)}
   */
  @Test
  @DisplayName("Test getAppModelReferencedProcessModels(JsonNode)")
  void testGetAppModelReferencedProcessModels() {
    // Arrange and Act
    List<JsonNode> actualAppModelReferencedProcessModels = JsonConverterUtil
        .getAppModelReferencedProcessModels(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Assert
    assertTrue(actualAppModelReferencedProcessModels.isEmpty());
  }

  /**
   * Test {@link JsonConverterUtil#getAppModelReferencedProcessModels(JsonNode)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#getAppModelReferencedProcessModels(JsonNode)}
   */
  @Test
  @DisplayName("Test getAppModelReferencedProcessModels(JsonNode); when Instance; then return Empty")
  void testGetAppModelReferencedProcessModels_whenInstance_thenReturnEmpty() {
    // Arrange and Act
    List<JsonNode> actualAppModelReferencedProcessModels = JsonConverterUtil
        .getAppModelReferencedProcessModels(MissingNode.getInstance());

    // Assert
    assertTrue(actualAppModelReferencedProcessModels.isEmpty());
  }

  /**
   * Test {@link JsonConverterUtil#getAppModelReferencedModelIds(JsonNode)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is
   * withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#getAppModelReferencedModelIds(JsonNode)}
   */
  @Test
  @DisplayName("Test getAppModelReferencedModelIds(JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  void testGetAppModelReferencedModelIds_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange and Act
    Set<String> actualAppModelReferencedModelIds = JsonConverterUtil
        .getAppModelReferencedModelIds(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Assert
    assertTrue(actualAppModelReferencedModelIds.isEmpty());
  }

  /**
   * Test {@link JsonConverterUtil#getAppModelReferencedModelIds(JsonNode)}.
   * <ul>
   *   <li>When Instance.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#getAppModelReferencedModelIds(JsonNode)}
   */
  @Test
  @DisplayName("Test getAppModelReferencedModelIds(JsonNode); when Instance; then return Empty")
  void testGetAppModelReferencedModelIds_whenInstance_thenReturnEmpty() {
    // Arrange and Act
    Set<String> actualAppModelReferencedModelIds = JsonConverterUtil
        .getAppModelReferencedModelIds(MissingNode.getInstance());

    // Assert
    assertTrue(actualAppModelReferencedModelIds.isEmpty());
  }

  /**
   * Test
   * {@link JsonConverterUtil#gatherLongPropertyFromJsonNodes(Iterable, String)}.
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#gatherLongPropertyFromJsonNodes(Iterable, String)}
   */
  @Test
  @DisplayName("Test gatherLongPropertyFromJsonNodes(Iterable, String)")
  void testGatherLongPropertyFromJsonNodes() {
    // Arrange
    LinkedHashSet<JsonNode> jsonNodes = new LinkedHashSet<>();
    jsonNodes.add(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Act
    Set<Long> actualGatherLongPropertyFromJsonNodesResult = JsonConverterUtil.gatherLongPropertyFromJsonNodes(jsonNodes,
        "Property Name");

    // Assert
    assertTrue(actualGatherLongPropertyFromJsonNodesResult.isEmpty());
  }

  /**
   * Test
   * {@link JsonConverterUtil#gatherLongPropertyFromJsonNodes(Iterable, String)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>When {@link LinkedHashSet#LinkedHashSet()} add Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#gatherLongPropertyFromJsonNodes(Iterable, String)}
   */
  @Test
  @DisplayName("Test gatherLongPropertyFromJsonNodes(Iterable, String); given Instance; when LinkedHashSet() add Instance")
  void testGatherLongPropertyFromJsonNodes_givenInstance_whenLinkedHashSetAddInstance() {
    // Arrange
    LinkedHashSet<JsonNode> jsonNodes = new LinkedHashSet<>();
    jsonNodes.add(MissingNode.getInstance());

    // Act
    Set<Long> actualGatherLongPropertyFromJsonNodesResult = JsonConverterUtil.gatherLongPropertyFromJsonNodes(jsonNodes,
        "Property Name");

    // Assert
    assertTrue(actualGatherLongPropertyFromJsonNodesResult.isEmpty());
  }

  /**
   * Test
   * {@link JsonConverterUtil#gatherLongPropertyFromJsonNodes(Iterable, String)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#gatherLongPropertyFromJsonNodes(Iterable, String)}
   */
  @Test
  @DisplayName("Test gatherLongPropertyFromJsonNodes(Iterable, String); when ArrayList(); then return Empty")
  void testGatherLongPropertyFromJsonNodes_whenArrayList_thenReturnEmpty() {
    // Arrange and Act
    Set<Long> actualGatherLongPropertyFromJsonNodesResult = JsonConverterUtil
        .gatherLongPropertyFromJsonNodes(new ArrayList<>(), "Property Name");

    // Assert
    assertTrue(actualGatherLongPropertyFromJsonNodesResult.isEmpty());
  }

  /**
   * Test
   * {@link JsonConverterUtil#gatherStringPropertyFromJsonNodes(Iterable, String)}.
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#gatherStringPropertyFromJsonNodes(Iterable, String)}
   */
  @Test
  @DisplayName("Test gatherStringPropertyFromJsonNodes(Iterable, String)")
  void testGatherStringPropertyFromJsonNodes() {
    // Arrange
    LinkedHashSet<JsonNode> jsonNodes = new LinkedHashSet<>();
    jsonNodes.add(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Act
    Set<String> actualGatherStringPropertyFromJsonNodesResult = JsonConverterUtil
        .gatherStringPropertyFromJsonNodes(jsonNodes, "Property Name");

    // Assert
    assertTrue(actualGatherStringPropertyFromJsonNodesResult.isEmpty());
  }

  /**
   * Test
   * {@link JsonConverterUtil#gatherStringPropertyFromJsonNodes(Iterable, String)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>When {@link LinkedHashSet#LinkedHashSet()} add Instance.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#gatherStringPropertyFromJsonNodes(Iterable, String)}
   */
  @Test
  @DisplayName("Test gatherStringPropertyFromJsonNodes(Iterable, String); given Instance; when LinkedHashSet() add Instance")
  void testGatherStringPropertyFromJsonNodes_givenInstance_whenLinkedHashSetAddInstance() {
    // Arrange
    LinkedHashSet<JsonNode> jsonNodes = new LinkedHashSet<>();
    jsonNodes.add(MissingNode.getInstance());

    // Act
    Set<String> actualGatherStringPropertyFromJsonNodesResult = JsonConverterUtil
        .gatherStringPropertyFromJsonNodes(jsonNodes, "Property Name");

    // Assert
    assertTrue(actualGatherStringPropertyFromJsonNodesResult.isEmpty());
  }

  /**
   * Test
   * {@link JsonConverterUtil#gatherStringPropertyFromJsonNodes(Iterable, String)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link JsonConverterUtil#gatherStringPropertyFromJsonNodes(Iterable, String)}
   */
  @Test
  @DisplayName("Test gatherStringPropertyFromJsonNodes(Iterable, String); when ArrayList(); then return Empty")
  void testGatherStringPropertyFromJsonNodes_whenArrayList_thenReturnEmpty() {
    // Arrange and Act
    Set<String> actualGatherStringPropertyFromJsonNodesResult = JsonConverterUtil
        .gatherStringPropertyFromJsonNodes(new ArrayList<>(), "Property Name");

    // Assert
    assertTrue(actualGatherStringPropertyFromJsonNodesResult.isEmpty());
  }

  /**
   * Test {@link JsonConverterUtil#filterOutJsonNodes(List)}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonConverterUtil#filterOutJsonNodes(List)}
   */
  @Test
  @DisplayName("Test filterOutJsonNodes(List); then return size is one")
  void testFilterOutJsonNodes_thenReturnSizeIsOne() {
    // Arrange
    ArrayList<JsonConverterUtil.JsonLookupResult> lookupResults = new ArrayList<>();
    MissingNode jsonNode = MissingNode.getInstance();
    lookupResults.add(new JsonConverterUtil.JsonLookupResult("Name", jsonNode));

    // Act
    List<JsonNode> actualFilterOutJsonNodesResult = JsonConverterUtil.filterOutJsonNodes(lookupResults);

    // Assert
    assertEquals(1, actualFilterOutJsonNodesResult.size());
    assertSame(jsonNode, actualFilterOutJsonNodesResult.get(0));
  }

  /**
   * Test {@link JsonConverterUtil#filterOutJsonNodes(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link JsonConverterUtil#filterOutJsonNodes(List)}
   */
  @Test
  @DisplayName("Test filterOutJsonNodes(List); when ArrayList(); then return Empty")
  void testFilterOutJsonNodes_whenArrayList_thenReturnEmpty() {
    // Arrange and Act
    List<JsonNode> actualFilterOutJsonNodesResult = JsonConverterUtil.filterOutJsonNodes(new ArrayList<>());

    // Assert
    assertTrue(actualFilterOutJsonNodesResult.isEmpty());
  }

  /**
   * Test JsonLookupResult getters and setters.
   * <ul>
   *   <li>When {@code 42}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link JsonConverterUtil.JsonLookupResult#JsonLookupResult(String, String, JsonNode)}
   *   <li>{@link JsonConverterUtil.JsonLookupResult#setId(String)}
   *   <li>{@link JsonConverterUtil.JsonLookupResult#setJsonNode(JsonNode)}
   *   <li>{@link JsonConverterUtil.JsonLookupResult#setName(String)}
   *   <li>{@link JsonConverterUtil.JsonLookupResult#getId()}
   *   <li>{@link JsonConverterUtil.JsonLookupResult#getJsonNode()}
   *   <li>{@link JsonConverterUtil.JsonLookupResult#getName()}
   * </ul>
   */
  @Test
  @DisplayName("Test JsonLookupResult getters and setters; when '42'")
  void testJsonLookupResultGettersAndSetters_when42() {
    // Arrange and Act
    JsonConverterUtil.JsonLookupResult actualJsonLookupResult = new JsonConverterUtil.JsonLookupResult("42", "Name",
        MissingNode.getInstance());
    actualJsonLookupResult.setId("42");
    MissingNode jsonNode = MissingNode.getInstance();
    actualJsonLookupResult.setJsonNode(jsonNode);
    actualJsonLookupResult.setName("Name");
    String actualId = actualJsonLookupResult.getId();
    JsonNode actualJsonNode = actualJsonLookupResult.getJsonNode();

    // Assert that nothing has changed
    assertEquals("42", actualId);
    assertEquals("Name", actualJsonLookupResult.getName());
    assertSame(jsonNode, actualJsonNode);
  }

  /**
   * Test JsonLookupResult getters and setters.
   * <ul>
   *   <li>When Instance.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link JsonConverterUtil.JsonLookupResult#JsonLookupResult(String, JsonNode)}
   *   <li>{@link JsonConverterUtil.JsonLookupResult#setId(String)}
   *   <li>{@link JsonConverterUtil.JsonLookupResult#setJsonNode(JsonNode)}
   *   <li>{@link JsonConverterUtil.JsonLookupResult#setName(String)}
   *   <li>{@link JsonConverterUtil.JsonLookupResult#getId()}
   *   <li>{@link JsonConverterUtil.JsonLookupResult#getJsonNode()}
   *   <li>{@link JsonConverterUtil.JsonLookupResult#getName()}
   * </ul>
   */
  @Test
  @DisplayName("Test JsonLookupResult getters and setters; when Instance")
  void testJsonLookupResultGettersAndSetters_whenInstance() {
    // Arrange and Act
    JsonConverterUtil.JsonLookupResult actualJsonLookupResult = new JsonConverterUtil.JsonLookupResult("Name",
        MissingNode.getInstance());
    actualJsonLookupResult.setId("42");
    MissingNode jsonNode = MissingNode.getInstance();
    actualJsonLookupResult.setJsonNode(jsonNode);
    actualJsonLookupResult.setName("Name");
    String actualId = actualJsonLookupResult.getId();
    JsonNode actualJsonNode = actualJsonLookupResult.getJsonNode();

    // Assert that nothing has changed
    assertEquals("42", actualId);
    assertEquals("Name", actualJsonLookupResult.getName());
    assertSame(jsonNode, actualJsonNode);
  }
}
