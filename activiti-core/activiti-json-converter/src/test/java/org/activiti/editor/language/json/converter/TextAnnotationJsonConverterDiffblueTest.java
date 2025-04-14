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
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.TextAnnotation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class TextAnnotationJsonConverterDiffblueTest {
  /**
   * Test {@link TextAnnotationJsonConverter#fillJsonTypes(Map)}.
   * <p>
   * Method under test: {@link TextAnnotationJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void TextAnnotationJsonConverter.fillJsonTypes(Map)"})
  void testFillJsonTypes() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();

    // Act
    TextAnnotationJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(1, convertersToBpmnMap.size());
    Class<TextAnnotationJsonConverter> expectedGetResult = TextAnnotationJsonConverter.class;
    assertEquals(expectedGetResult, convertersToBpmnMap.get("TextAnnotation"));
  }

  /**
   * Test {@link TextAnnotationJsonConverter#getStencilId(BaseElement)}.
   * <p>
   * Method under test: {@link TextAnnotationJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String TextAnnotationJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId() {
    // Arrange
    TextAnnotationJsonConverter textAnnotationJsonConverter = new TextAnnotationJsonConverter();

    // Act and Assert
    assertEquals("TextAnnotation", textAnnotationJsonConverter.getStencilId(new ActivitiListener()));
  }

  /**
   * Test {@link TextAnnotationJsonConverter#convertElementToJson(ObjectNode, BaseElement)}.
   * <p>
   * Method under test: {@link TextAnnotationJsonConverter#convertElementToJson(ObjectNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertElementToJson(ObjectNode, BaseElement)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void TextAnnotationJsonConverter.convertElementToJson(ObjectNode, BaseElement)"})
  void testConvertElementToJson() {
    // Arrange
    TextAnnotationJsonConverter textAnnotationJsonConverter = new TextAnnotationJsonConverter();
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    TextAnnotation baseElement = new TextAnnotation();
    baseElement.setText("not empty");

    // Act
    textAnnotationJsonConverter.convertElementToJson(propertiesNode, baseElement);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    assertTrue(iteratorResult.next() instanceof TextNode);
    assertEquals("{\n  \"text\" : \"not empty\"\n}", propertiesNode.toPrettyString());
    assertEquals(1, propertiesNode.size());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link TextAnnotationJsonConverter#convertElementToJson(ObjectNode, BaseElement)}.
   * <ul>
   *   <li>Given empty string.</li>
   *   <li>When {@link TextAnnotation} (default constructor) Text is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link TextAnnotationJsonConverter#convertElementToJson(ObjectNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertElementToJson(ObjectNode, BaseElement); given empty string; when TextAnnotation (default constructor) Text is empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void TextAnnotationJsonConverter.convertElementToJson(ObjectNode, BaseElement)"})
  void testConvertElementToJson_givenEmptyString_whenTextAnnotationTextIsEmptyString() {
    // Arrange
    TextAnnotationJsonConverter textAnnotationJsonConverter = new TextAnnotationJsonConverter();
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    TextAnnotation baseElement = new TextAnnotation();
    baseElement.setText("");

    // Act
    textAnnotationJsonConverter.convertElementToJson(propertiesNode, baseElement);

    // Assert that nothing has changed
    assertEquals("{ }", propertiesNode.toPrettyString());
    assertEquals(0, propertiesNode.size());
    assertFalse(propertiesNode.iterator().hasNext());
    assertTrue(propertiesNode.isEmpty());
  }

  /**
   * Test {@link TextAnnotationJsonConverter#convertElementToJson(ObjectNode, BaseElement)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link TextAnnotation} (default constructor) Text is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TextAnnotationJsonConverter#convertElementToJson(ObjectNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertElementToJson(ObjectNode, BaseElement); given 'null'; when TextAnnotation (default constructor) Text is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void TextAnnotationJsonConverter.convertElementToJson(ObjectNode, BaseElement)"})
  void testConvertElementToJson_givenNull_whenTextAnnotationTextIsNull() {
    // Arrange
    TextAnnotationJsonConverter textAnnotationJsonConverter = new TextAnnotationJsonConverter();
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    TextAnnotation baseElement = new TextAnnotation();
    baseElement.setText(null);

    // Act
    textAnnotationJsonConverter.convertElementToJson(propertiesNode, baseElement);

    // Assert that nothing has changed
    assertEquals("{ }", propertiesNode.toPrettyString());
    assertEquals(0, propertiesNode.size());
    assertFalse(propertiesNode.iterator().hasNext());
    assertTrue(propertiesNode.isEmpty());
  }

  /**
   * Test {@link TextAnnotationJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   *   <li>Then return {@link TextAnnotation}.</li>
   * </ul>
   * <p>
   * Method under test: {@link TextAnnotationJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); when HashMap(); then return TextAnnotation")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BaseElement TextAnnotationJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
  void testConvertJsonToElement_whenHashMap_thenReturnTextAnnotation() {
    // Arrange
    TextAnnotationJsonConverter textAnnotationJsonConverter = new TextAnnotationJsonConverter();
    MissingNode elementNode = MissingNode.getInstance();
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    BaseElement actualConvertJsonToElementResult = textAnnotationJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    assertTrue(actualConvertJsonToElementResult instanceof TextAnnotation);
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((TextAnnotation) actualConvertJsonToElementResult).getText());
    assertNull(((TextAnnotation) actualConvertJsonToElementResult).getTextFormat());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
  }

  /**
   * Test new {@link TextAnnotationJsonConverter} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link TextAnnotationJsonConverter}
   */
  @Test
  @DisplayName("Test new TextAnnotationJsonConverter (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void TextAnnotationJsonConverter.<init>()"})
  void testNewTextAnnotationJsonConverter() {
    // Arrange and Act
    TextAnnotationJsonConverter actualTextAnnotationJsonConverter = new TextAnnotationJsonConverter();

    // Assert
    assertNull(actualTextAnnotationJsonConverter.shapesArrayNode);
    assertNull(actualTextAnnotationJsonConverter.flowElementNode);
    assertNull(actualTextAnnotationJsonConverter.model);
    assertNull(actualTextAnnotationJsonConverter.processor);
    assertEquals(0.0d, actualTextAnnotationJsonConverter.subProcessX);
    assertEquals(0.0d, actualTextAnnotationJsonConverter.subProcessY);
  }
}
