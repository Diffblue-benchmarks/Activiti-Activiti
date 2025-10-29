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
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.MissingNode;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class JsonConverterUtilDiffblueTest {
  /**
   * Method under test:
   * {@link JsonConverterUtil#getPropertyValueAsString(String, JsonNode)}
   */
  @Test
  void testGetPropertyValueAsString() {
    // Arrange, Act and Assert
    assertNull(JsonConverterUtil.getPropertyValueAsString("Name", MissingNode.getInstance()));
  }

  /**
   * Method under test:
   * {@link JsonConverterUtil#getPropertyValueAsBoolean(String, JsonNode)}
   */
  @Test
  void testGetPropertyValueAsBoolean() {
    // Arrange, Act and Assert
    assertFalse(JsonConverterUtil.getPropertyValueAsBoolean("Name", MissingNode.getInstance()));
    assertTrue(JsonConverterUtil.getPropertyValueAsBoolean("Name", MissingNode.getInstance(), true));
    assertFalse(JsonConverterUtil.getPropertyValueAsBoolean("Name", MissingNode.getInstance(), false));
  }

  /**
   * Method under test:
   * {@link JsonConverterUtil#getPropertyValueAsList(String, JsonNode)}
   */
  @Test
  void testGetPropertyValueAsList() {
    // Arrange and Act
    List<String> actualPropertyValueAsList = JsonConverterUtil.getPropertyValueAsList("Name",
        MissingNode.getInstance());

    // Assert
    assertTrue(actualPropertyValueAsList.isEmpty());
  }

  /**
   * Method under test: {@link JsonConverterUtil#getProperty(String, JsonNode)}
   */
  @Test
  void testGetProperty() {
    // Arrange, Act and Assert
    assertNull(JsonConverterUtil.getProperty("Name", MissingNode.getInstance()));
  }

  /**
   * Method under test:
   * {@link JsonConverterUtil#getBpmnProcessModelChildShapesPropertyValues(JsonNode, String, List)}
   */
  @Test
  void testGetBpmnProcessModelChildShapesPropertyValues() {
    // Arrange
    MissingNode editorJsonNode = MissingNode.getInstance();

    // Act
    List<JsonConverterUtil.JsonLookupResult> actualBpmnProcessModelChildShapesPropertyValues = JsonConverterUtil
        .getBpmnProcessModelChildShapesPropertyValues(editorJsonNode, "Property Name", new ArrayList<>());

    // Assert
    assertTrue(actualBpmnProcessModelChildShapesPropertyValues.isEmpty());
  }

  /**
   * Method under test:
   * {@link JsonConverterUtil#getBpmnProcessModelChildShapesPropertyValues(JsonNode, String, List)}
   */
  @Test
  void testGetBpmnProcessModelChildShapesPropertyValues2() {
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
   * Method under test:
   * {@link JsonConverterUtil#getBpmnProcessModelChildShapesPropertyValues(JsonNode, String, List)}
   */
  @Test
  void testGetBpmnProcessModelChildShapesPropertyValues3() {
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
   * Method under test:
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  void testInternalGetBpmnProcessChildShapePropertyValues() {
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
   * Method under test:
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  void testInternalGetBpmnProcessChildShapePropertyValues2() {
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
   * Method under test:
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  void testInternalGetBpmnProcessChildShapePropertyValues3() {
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
   * Method under test:
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  void testInternalGetBpmnProcessChildShapePropertyValues4() {
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
   * Method under test:
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  void testInternalGetBpmnProcessChildShapePropertyValues5() {
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
   * Method under test:
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  void testInternalGetBpmnProcessChildShapePropertyValues6() {
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
   * Method under test:
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  void testInternalGetBpmnProcessChildShapePropertyValues7() {
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
   * Method under test:
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  void testInternalGetBpmnProcessChildShapePropertyValues8() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);
    ArrayNode editorJsonNode = mock(ArrayNode.class);
    when(editorJsonNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayList<String> allowedStencilTypes = new ArrayList<>();

    ArrayList<JsonConverterUtil.JsonLookupResult> result = new ArrayList<>();
    JsonConverterUtil.JsonLookupResult jsonLookupResult = new JsonConverterUtil.JsonLookupResult("childShapes",
        MissingNode.getInstance());

    result.add(jsonLookupResult);

    // Act
    JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(editorJsonNode, "Property Name",
        allowedStencilTypes, result);

    // Assert that nothing has changed
    verify(arrayNode).iterator();
    verify(editorJsonNode).get(eq("childShapes"));
    verify(arrayNode).isArray();
    assertEquals(1, result.size());
    assertSame(jsonLookupResult, result.get(0));
  }

  /**
   * Method under test:
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  void testInternalGetBpmnProcessChildShapePropertyValues9() {
    // Arrange
    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.isArray()).thenReturn(true);
    ArrayNode editorJsonNode = mock(ArrayNode.class);
    when(editorJsonNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayList<String> allowedStencilTypes = new ArrayList<>();

    ArrayList<JsonConverterUtil.JsonLookupResult> result = new ArrayList<>();
    JsonConverterUtil.JsonLookupResult jsonLookupResult = new JsonConverterUtil.JsonLookupResult("childShapes",
        MissingNode.getInstance());

    result.add(jsonLookupResult);
    result.add(new JsonConverterUtil.JsonLookupResult("childShapes", MissingNode.getInstance()));

    // Act
    JsonConverterUtil.internalGetBpmnProcessChildShapePropertyValues(editorJsonNode, "Property Name",
        allowedStencilTypes, result);

    // Assert that nothing has changed
    verify(arrayNode).iterator();
    verify(editorJsonNode).get(eq("childShapes"));
    verify(arrayNode).isArray();
    assertEquals(2, result.size());
    assertSame(jsonLookupResult, result.get(0));
  }

  /**
   * Method under test:
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  void testInternalGetBpmnProcessChildShapePropertyValues10() {
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
   * Method under test:
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  void testInternalGetBpmnProcessChildShapePropertyValues11() {
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
   * Method under test:
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  void testInternalGetBpmnProcessChildShapePropertyValues12() {
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
   * Method under test:
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  void testInternalGetBpmnProcessChildShapePropertyValues13() {
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
   * Method under test:
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  void testInternalGetBpmnProcessChildShapePropertyValues14() {
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
    JsonConverterUtil.JsonLookupResult getResult = result.get(0);
    assertEquals("", getResult.getName());
    assertEquals("As Text", getResult.getId());
    assertSame(instance, getResult.getJsonNode());
  }

  /**
   * Method under test:
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  void testInternalGetBpmnProcessChildShapePropertyValues15() {
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
   * Method under test:
   * {@link JsonConverterUtil#internalGetBpmnProcessChildShapePropertyValues(JsonNode, String, List, List)}
   */
  @Test
  void testInternalGetBpmnProcessChildShapePropertyValues16() {
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
    JsonConverterUtil.JsonLookupResult getResult = result.get(0);
    assertEquals("", getResult.getName());
    assertEquals("As Text", getResult.getId());
    assertSame(arrayNode2, getResult.getJsonNode());
  }

  /**
   * Method under test:
   * {@link JsonConverterUtil#getBpmnProcessModelFormReferences(JsonNode)}
   */
  @Test
  void testGetBpmnProcessModelFormReferences() {
    // Arrange and Act
    List<JsonConverterUtil.JsonLookupResult> actualBpmnProcessModelFormReferences = JsonConverterUtil
        .getBpmnProcessModelFormReferences(MissingNode.getInstance());

    // Assert
    assertTrue(actualBpmnProcessModelFormReferences.isEmpty());
  }

  /**
   * Method under test:
   * {@link JsonConverterUtil#getBpmnProcessModelDecisionTableReferences(JsonNode)}
   */
  @Test
  void testGetBpmnProcessModelDecisionTableReferences() {
    // Arrange and Act
    List<JsonConverterUtil.JsonLookupResult> actualBpmnProcessModelDecisionTableReferences = JsonConverterUtil
        .getBpmnProcessModelDecisionTableReferences(MissingNode.getInstance());

    // Assert
    assertTrue(actualBpmnProcessModelDecisionTableReferences.isEmpty());
  }

  /**
   * Method under test:
   * {@link JsonConverterUtil#getAppModelReferencedProcessModels(JsonNode)}
   */
  @Test
  void testGetAppModelReferencedProcessModels() {
    // Arrange and Act
    List<JsonNode> actualAppModelReferencedProcessModels = JsonConverterUtil
        .getAppModelReferencedProcessModels(MissingNode.getInstance());

    // Assert
    assertTrue(actualAppModelReferencedProcessModels.isEmpty());
  }

  /**
   * Method under test:
   * {@link JsonConverterUtil#getAppModelReferencedProcessModels(JsonNode)}
   */
  @Test
  void testGetAppModelReferencedProcessModels2() {
    // Arrange and Act
    List<JsonNode> actualAppModelReferencedProcessModels = JsonConverterUtil
        .getAppModelReferencedProcessModels(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Assert
    assertTrue(actualAppModelReferencedProcessModels.isEmpty());
  }

  /**
   * Method under test:
   * {@link JsonConverterUtil#getAppModelReferencedModelIds(JsonNode)}
   */
  @Test
  void testGetAppModelReferencedModelIds() {
    // Arrange and Act
    Set<String> actualAppModelReferencedModelIds = JsonConverterUtil
        .getAppModelReferencedModelIds(MissingNode.getInstance());

    // Assert
    assertTrue(actualAppModelReferencedModelIds.isEmpty());
  }

  /**
   * Method under test:
   * {@link JsonConverterUtil#getAppModelReferencedModelIds(JsonNode)}
   */
  @Test
  void testGetAppModelReferencedModelIds2() {
    // Arrange and Act
    Set<String> actualAppModelReferencedModelIds = JsonConverterUtil
        .getAppModelReferencedModelIds(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Assert
    assertTrue(actualAppModelReferencedModelIds.isEmpty());
  }

  /**
   * Method under test:
   * {@link JsonConverterUtil#gatherLongPropertyFromJsonNodes(Iterable, String)}
   */
  @Test
  void testGatherLongPropertyFromJsonNodes() {
    // Arrange and Act
    Set<Long> actualGatherLongPropertyFromJsonNodesResult = JsonConverterUtil
        .gatherLongPropertyFromJsonNodes(new ArrayList<>(), "Property Name");

    // Assert
    assertTrue(actualGatherLongPropertyFromJsonNodesResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link JsonConverterUtil#gatherLongPropertyFromJsonNodes(Iterable, String)}
   */
  @Test
  void testGatherLongPropertyFromJsonNodes2() {
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
   * Method under test:
   * {@link JsonConverterUtil#gatherLongPropertyFromJsonNodes(Iterable, String)}
   */
  @Test
  void testGatherLongPropertyFromJsonNodes3() {
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
   * Method under test:
   * {@link JsonConverterUtil#gatherStringPropertyFromJsonNodes(Iterable, String)}
   */
  @Test
  void testGatherStringPropertyFromJsonNodes() {
    // Arrange and Act
    Set<String> actualGatherStringPropertyFromJsonNodesResult = JsonConverterUtil
        .gatherStringPropertyFromJsonNodes(new ArrayList<>(), "Property Name");

    // Assert
    assertTrue(actualGatherStringPropertyFromJsonNodesResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link JsonConverterUtil#gatherStringPropertyFromJsonNodes(Iterable, String)}
   */
  @Test
  void testGatherStringPropertyFromJsonNodes2() {
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
   * Method under test:
   * {@link JsonConverterUtil#gatherStringPropertyFromJsonNodes(Iterable, String)}
   */
  @Test
  void testGatherStringPropertyFromJsonNodes3() {
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
   * Method under test: {@link JsonConverterUtil#filterOutJsonNodes(List)}
   */
  @Test
  void testFilterOutJsonNodes() {
    // Arrange and Act
    List<JsonNode> actualFilterOutJsonNodesResult = JsonConverterUtil.filterOutJsonNodes(new ArrayList<>());

    // Assert
    assertTrue(actualFilterOutJsonNodesResult.isEmpty());
  }

  /**
   * Method under test: {@link JsonConverterUtil#filterOutJsonNodes(List)}
   */
  @Test
  void testFilterOutJsonNodes2() {
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
  void testJsonLookupResultGettersAndSetters() {
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

  /**
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
  void testJsonLookupResultGettersAndSetters2() {
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
}
