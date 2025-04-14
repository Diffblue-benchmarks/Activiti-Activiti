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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BusinessRuleTask;
import org.activiti.bpmn.model.FlowElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class BusinessRuleTaskJsonConverterDiffblueTest {
  /**
   * Test {@link BusinessRuleTaskJsonConverter#fillJsonTypes(Map)}.
   * <p>
   * Method under test: {@link BusinessRuleTaskJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BusinessRuleTaskJsonConverter.fillJsonTypes(Map)"})
  void testFillJsonTypes() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();

    // Act
    BusinessRuleTaskJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(1, convertersToBpmnMap.size());
    Class<BusinessRuleTaskJsonConverter> expectedGetResult = BusinessRuleTaskJsonConverter.class;
    assertEquals(expectedGetResult, convertersToBpmnMap.get("BusinessRule"));
  }

  /**
   * Test {@link BusinessRuleTaskJsonConverter#getStencilId(BaseElement)}.
   * <p>
   * Method under test: {@link BusinessRuleTaskJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BusinessRuleTaskJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId() {
    // Arrange
    BusinessRuleTaskJsonConverter businessRuleTaskJsonConverter = new BusinessRuleTaskJsonConverter();

    // Act and Assert
    assertEquals("BusinessRule", businessRuleTaskJsonConverter.getStencilId(new ActivitiListener()));
  }

  /**
   * Test {@link BusinessRuleTaskJsonConverter#convertElementToJson(ObjectNode, BaseElement)}.
   * <p>
   * Method under test: {@link BusinessRuleTaskJsonConverter#convertElementToJson(ObjectNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertElementToJson(ObjectNode, BaseElement)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BusinessRuleTaskJsonConverter.convertElementToJson(ObjectNode, BaseElement)"})
  void testConvertElementToJson() {
    // Arrange
    BusinessRuleTaskJsonConverter businessRuleTaskJsonConverter = new BusinessRuleTaskJsonConverter();
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    BusinessRuleTask baseElement = new BusinessRuleTask();
    baseElement.setExclude(false);
    baseElement.setClassName(null);
    baseElement.setResultVariableName(null);

    // Act
    businessRuleTaskJsonConverter.convertElementToJson(propertiesNode, baseElement);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    JsonNode actualNextResult = iteratorResult.next();
    JsonNode actualNextResult2 = iteratorResult.next();
    JsonNode actualNextResult3 = iteratorResult.next();
    boolean actualHasNextResult = iteratorResult.hasNext();
    assertTrue(nextResult instanceof NullNode);
    assertEquals("null", nextResult.toPrettyString());
    assertEquals(
        "{\n" + "  \"ruletask_class\" : null,\n" + "  \"ruletask_variables_input\" : null,\n"
            + "  \"ruletask_result\" : null,\n" + "  \"ruletask_rules\" : null\n" + "}",
        propertiesNode.toPrettyString());
    assertEquals(4, propertiesNode.size());
    assertEquals(JsonNodeType.NULL, nextResult.getNodeType());
    assertFalse(nextResult.isTextual());
    assertFalse(actualHasNextResult);
    assertTrue(nextResult.isNull());
    assertSame(nextResult, actualNextResult);
    assertSame(nextResult, actualNextResult2);
    assertSame(nextResult, actualNextResult3);
  }

  /**
   * Test {@link BusinessRuleTaskJsonConverter#convertElementToJson(ObjectNode, BaseElement)}.
   * <p>
   * Method under test: {@link BusinessRuleTaskJsonConverter#convertElementToJson(ObjectNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertElementToJson(ObjectNode, BaseElement)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BusinessRuleTaskJsonConverter.convertElementToJson(ObjectNode, BaseElement)"})
  void testConvertElementToJson2() {
    // Arrange
    BusinessRuleTaskJsonConverter businessRuleTaskJsonConverter = new BusinessRuleTaskJsonConverter();
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    BusinessRuleTask baseElement = new BusinessRuleTask();
    baseElement.setExclude(false);
    baseElement.setClassName("");
    baseElement.setResultVariableName(null);

    // Act
    businessRuleTaskJsonConverter.convertElementToJson(propertiesNode, baseElement);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    JsonNode nextResult2 = iteratorResult.next();
    JsonNode actualNextResult = iteratorResult.next();
    JsonNode actualNextResult2 = iteratorResult.next();
    boolean actualHasNextResult = iteratorResult.hasNext();
    assertTrue(nextResult2 instanceof NullNode);
    assertTrue(nextResult instanceof TextNode);
    assertEquals(
        "{\n" + "  \"ruletask_class\" : \"\",\n" + "  \"ruletask_variables_input\" : null,\n"
            + "  \"ruletask_result\" : null,\n" + "  \"ruletask_rules\" : null\n" + "}",
        propertiesNode.toPrettyString());
    assertFalse(actualHasNextResult);
    assertSame(nextResult2, actualNextResult);
    assertSame(nextResult2, actualNextResult2);
  }

  /**
   * Test {@link BusinessRuleTaskJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   *   <li>Then return {@link BusinessRuleTask}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BusinessRuleTaskJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); when HashMap(); then return BusinessRuleTask")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement BusinessRuleTaskJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
  void testConvertJsonToElement_whenHashMap_thenReturnBusinessRuleTask() {
    // Arrange
    BusinessRuleTaskJsonConverter businessRuleTaskJsonConverter = new BusinessRuleTaskJsonConverter();
    MissingNode elementNode = MissingNode.getInstance();
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = businessRuleTaskJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    assertTrue(actualConvertJsonToElementResult instanceof BusinessRuleTask);
    assertNull(((BusinessRuleTask) actualConvertJsonToElementResult).getBehavior());
    assertNull(((BusinessRuleTask) actualConvertJsonToElementResult).getDefaultFlow());
    assertNull(((BusinessRuleTask) actualConvertJsonToElementResult).getFailedJobRetryTimeCycleValue());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BusinessRuleTask) actualConvertJsonToElementResult).getClassName());
    assertNull(((BusinessRuleTask) actualConvertJsonToElementResult).getResultVariableName());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(((BusinessRuleTask) actualConvertJsonToElementResult).getIoSpecification());
    assertNull(((BusinessRuleTask) actualConvertJsonToElementResult).getLoopCharacteristics());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BusinessRuleTask) actualConvertJsonToElementResult).hasMultiInstanceLoopCharacteristics());
    assertFalse(((BusinessRuleTask) actualConvertJsonToElementResult).isForCompensation());
    assertFalse(((BusinessRuleTask) actualConvertJsonToElementResult).isExclude());
    assertFalse(((BusinessRuleTask) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BusinessRuleTask) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((BusinessRuleTask) actualConvertJsonToElementResult).getBoundaryEvents().isEmpty());
    assertTrue(((BusinessRuleTask) actualConvertJsonToElementResult).getDataInputAssociations().isEmpty());
    assertTrue(((BusinessRuleTask) actualConvertJsonToElementResult).getDataOutputAssociations().isEmpty());
    assertTrue(((BusinessRuleTask) actualConvertJsonToElementResult).getMapExceptions().isEmpty());
    assertTrue(((BusinessRuleTask) actualConvertJsonToElementResult).getInputVariables().isEmpty());
    assertTrue(((BusinessRuleTask) actualConvertJsonToElementResult).getRuleNames().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BusinessRuleTask) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BusinessRuleTask) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BusinessRuleTask) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test new {@link BusinessRuleTaskJsonConverter} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link BusinessRuleTaskJsonConverter}
   */
  @Test
  @DisplayName("Test new BusinessRuleTaskJsonConverter (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BusinessRuleTaskJsonConverter.<init>()"})
  void testNewBusinessRuleTaskJsonConverter() {
    // Arrange and Act
    BusinessRuleTaskJsonConverter actualBusinessRuleTaskJsonConverter = new BusinessRuleTaskJsonConverter();

    // Assert
    assertNull(actualBusinessRuleTaskJsonConverter.shapesArrayNode);
    assertNull(actualBusinessRuleTaskJsonConverter.flowElementNode);
    assertNull(actualBusinessRuleTaskJsonConverter.model);
    assertNull(actualBusinessRuleTaskJsonConverter.processor);
    assertEquals(0.0d, actualBusinessRuleTaskJsonConverter.subProcessX);
    assertEquals(0.0d, actualBusinessRuleTaskJsonConverter.subProcessY);
  }
}
