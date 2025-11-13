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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.ScriptTask;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ScriptTaskJsonConverterDiffblueTest {
  /**
   * Test {@link ScriptTaskJsonConverter#fillJsonTypes(Map)}.
   *
   * <p>Method under test: {@link ScriptTaskJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ScriptTaskJsonConverter.fillJsonTypes(Map)"})
  void testFillJsonTypes() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();

    // Act
    ScriptTaskJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(1, convertersToBpmnMap.size());
    Class<ScriptTaskJsonConverter> expectedGetResult = ScriptTaskJsonConverter.class;
    assertEquals(expectedGetResult, convertersToBpmnMap.get("ScriptTask"));
  }

  /**
   * Test {@link ScriptTaskJsonConverter#getStencilId(BaseElement)}.
   *
   * <p>Method under test: {@link ScriptTaskJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ScriptTaskJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId() {
    // Arrange
    ScriptTaskJsonConverter scriptTaskJsonConverter = new ScriptTaskJsonConverter();

    // Act and Assert
    assertEquals("ScriptTask", scriptTaskJsonConverter.getStencilId(new ActivitiListener()));
  }

  /**
   * Test {@link ScriptTaskJsonConverter#convertElementToJson(ObjectNode, BaseElement)}.
   *
   * <p>Method under test: {@link ScriptTaskJsonConverter#convertElementToJson(ObjectNode,
   * BaseElement)}
   */
  @Test
  @DisplayName("Test convertElementToJson(ObjectNode, BaseElement)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ScriptTaskJsonConverter.convertElementToJson(ObjectNode, BaseElement)"})
  void testConvertElementToJson() {
    // Arrange
    ScriptTaskJsonConverter scriptTaskJsonConverter = new ScriptTaskJsonConverter();
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode propertiesNode = new ObjectNode(nc);
    propertiesNode.put("scriptformat", DoubleNode.valueOf(10.0d));
    propertiesNode.put("scripttext", DoubleNode.valueOf(10.0d));

    ScriptTask baseElement = new ScriptTask();
    baseElement.setScriptFormat("Base Element");
    baseElement.setScript("Base Element");

    // Act
    scriptTaskJsonConverter.convertElementToJson(propertiesNode, baseElement);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode expectedTextNode = iteratorResult.next();
    JsonNode nextResult = iteratorResult.next();
    assertTrue(nextResult instanceof TextNode);
    assertFalse(iteratorResult.hasNext());
    assertEquals(expectedTextNode, nextResult);
  }

  /**
   * Test {@link ScriptTaskJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.
   *   <li>Then return {@link ScriptTask}.
   * </ul>
   *
   * <p>Method under test: {@link ScriptTaskJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); when HashMap(); then return ScriptTask")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement ScriptTaskJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_whenHashMap_thenReturnScriptTask() {
    // Arrange
    ScriptTaskJsonConverter scriptTaskJsonConverter = new ScriptTaskJsonConverter();
    DoubleNode elementNode = DoubleNode.valueOf(10.0d);
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);

    // Act
    FlowElement actualConvertJsonToElementResult =
        scriptTaskJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    assertTrue(actualConvertJsonToElementResult instanceof ScriptTask);
    assertNull(((ScriptTask) actualConvertJsonToElementResult).getBehavior());
    assertNull(((ScriptTask) actualConvertJsonToElementResult).getDefaultFlow());
    assertNull(((ScriptTask) actualConvertJsonToElementResult).getFailedJobRetryTimeCycleValue());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((ScriptTask) actualConvertJsonToElementResult).getResultVariable());
    assertNull(((ScriptTask) actualConvertJsonToElementResult).getScript());
    assertNull(((ScriptTask) actualConvertJsonToElementResult).getScriptFormat());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(((ScriptTask) actualConvertJsonToElementResult).getIoSpecification());
    assertNull(((ScriptTask) actualConvertJsonToElementResult).getLoopCharacteristics());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(
        ((ScriptTask) actualConvertJsonToElementResult).hasMultiInstanceLoopCharacteristics());
    assertFalse(((ScriptTask) actualConvertJsonToElementResult).isForCompensation());
    assertFalse(((ScriptTask) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((ScriptTask) actualConvertJsonToElementResult).isNotExclusive());
    assertFalse(((ScriptTask) actualConvertJsonToElementResult).isAutoStoreVariables());
    assertTrue(((ScriptTask) actualConvertJsonToElementResult).getBoundaryEvents().isEmpty());
    assertTrue(
        ((ScriptTask) actualConvertJsonToElementResult).getDataInputAssociations().isEmpty());
    assertTrue(
        ((ScriptTask) actualConvertJsonToElementResult).getDataOutputAssociations().isEmpty());
    assertTrue(((ScriptTask) actualConvertJsonToElementResult).getMapExceptions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((ScriptTask) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((ScriptTask) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((ScriptTask) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test new {@link ScriptTaskJsonConverter} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link ScriptTaskJsonConverter}
   */
  @Test
  @DisplayName("Test new ScriptTaskJsonConverter (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ScriptTaskJsonConverter.<init>()"})
  void testNewScriptTaskJsonConverter() {
    // Arrange and Act
    ScriptTaskJsonConverter actualScriptTaskJsonConverter = new ScriptTaskJsonConverter();

    // Assert
    assertNull(actualScriptTaskJsonConverter.shapesArrayNode);
    assertNull(actualScriptTaskJsonConverter.flowElementNode);
    assertNull(actualScriptTaskJsonConverter.model);
    assertNull(actualScriptTaskJsonConverter.processor);
    assertEquals(0.0d, actualScriptTaskJsonConverter.subProcessX);
    assertEquals(0.0d, actualScriptTaskJsonConverter.subProcessY);
  }
}
