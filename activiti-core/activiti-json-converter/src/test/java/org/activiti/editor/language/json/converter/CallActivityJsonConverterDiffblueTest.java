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
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.MissingNode;
import java.util.HashMap;
import java.util.Map;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.CallActivity;
import org.activiti.bpmn.model.FlowElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CallActivityJsonConverterDiffblueTest {
  /**
   * Test {@link CallActivityJsonConverter#fillJsonTypes(Map)}.
   * <p>
   * Method under test: {@link CallActivityJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void CallActivityJsonConverter.fillJsonTypes(Map)"})
  void testFillJsonTypes() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();

    // Act
    CallActivityJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(1, convertersToBpmnMap.size());
    Class<CallActivityJsonConverter> expectedGetResult = CallActivityJsonConverter.class;
    assertEquals(expectedGetResult, convertersToBpmnMap.get("CallActivity"));
  }

  /**
   * Test {@link CallActivityJsonConverter#getStencilId(BaseElement)}.
   * <p>
   * Method under test: {@link CallActivityJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String CallActivityJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId() {
    // Arrange
    CallActivityJsonConverter callActivityJsonConverter = new CallActivityJsonConverter();

    // Act and Assert
    assertEquals("CallActivity", callActivityJsonConverter.getStencilId(new ActivitiListener()));
  }

  /**
   * Test {@link CallActivityJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CallActivityJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement CallActivityJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
  void testConvertJsonToElement_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    CallActivityJsonConverter callActivityJsonConverter = new CallActivityJsonConverter();
    ArrayNode elementNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = callActivityJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    assertTrue(actualConvertJsonToElementResult instanceof CallActivity);
    assertNull(((CallActivity) actualConvertJsonToElementResult).getBehavior());
    assertNull(((CallActivity) actualConvertJsonToElementResult).getDefaultFlow());
    assertNull(((CallActivity) actualConvertJsonToElementResult).getFailedJobRetryTimeCycleValue());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((CallActivity) actualConvertJsonToElementResult).getBusinessKey());
    assertNull(((CallActivity) actualConvertJsonToElementResult).getCalledElement());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(((CallActivity) actualConvertJsonToElementResult).getIoSpecification());
    assertNull(((CallActivity) actualConvertJsonToElementResult).getLoopCharacteristics());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((CallActivity) actualConvertJsonToElementResult).hasMultiInstanceLoopCharacteristics());
    assertFalse(((CallActivity) actualConvertJsonToElementResult).isForCompensation());
    assertFalse(((CallActivity) actualConvertJsonToElementResult).isInheritBusinessKey());
    assertFalse(((CallActivity) actualConvertJsonToElementResult).isInheritVariables());
    assertFalse(((CallActivity) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((CallActivity) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((CallActivity) actualConvertJsonToElementResult).getBoundaryEvents().isEmpty());
    assertTrue(((CallActivity) actualConvertJsonToElementResult).getDataInputAssociations().isEmpty());
    assertTrue(((CallActivity) actualConvertJsonToElementResult).getDataOutputAssociations().isEmpty());
    assertTrue(((CallActivity) actualConvertJsonToElementResult).getMapExceptions().isEmpty());
    assertTrue(((CallActivity) actualConvertJsonToElementResult).getInParameters().isEmpty());
    assertTrue(((CallActivity) actualConvertJsonToElementResult).getOutParameters().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((CallActivity) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((CallActivity) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((CallActivity) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test {@link CallActivityJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   *   <li>Then return {@link CallActivity}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CallActivityJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); when HashMap(); then return CallActivity")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement CallActivityJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
  void testConvertJsonToElement_whenHashMap_thenReturnCallActivity() {
    // Arrange
    CallActivityJsonConverter callActivityJsonConverter = new CallActivityJsonConverter();
    MissingNode elementNode = MissingNode.getInstance();
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = callActivityJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    assertTrue(actualConvertJsonToElementResult instanceof CallActivity);
    assertNull(((CallActivity) actualConvertJsonToElementResult).getBehavior());
    assertNull(((CallActivity) actualConvertJsonToElementResult).getDefaultFlow());
    assertNull(((CallActivity) actualConvertJsonToElementResult).getFailedJobRetryTimeCycleValue());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((CallActivity) actualConvertJsonToElementResult).getBusinessKey());
    assertNull(((CallActivity) actualConvertJsonToElementResult).getCalledElement());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(((CallActivity) actualConvertJsonToElementResult).getIoSpecification());
    assertNull(((CallActivity) actualConvertJsonToElementResult).getLoopCharacteristics());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((CallActivity) actualConvertJsonToElementResult).hasMultiInstanceLoopCharacteristics());
    assertFalse(((CallActivity) actualConvertJsonToElementResult).isForCompensation());
    assertFalse(((CallActivity) actualConvertJsonToElementResult).isInheritBusinessKey());
    assertFalse(((CallActivity) actualConvertJsonToElementResult).isInheritVariables());
    assertFalse(((CallActivity) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((CallActivity) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((CallActivity) actualConvertJsonToElementResult).getBoundaryEvents().isEmpty());
    assertTrue(((CallActivity) actualConvertJsonToElementResult).getDataInputAssociations().isEmpty());
    assertTrue(((CallActivity) actualConvertJsonToElementResult).getDataOutputAssociations().isEmpty());
    assertTrue(((CallActivity) actualConvertJsonToElementResult).getMapExceptions().isEmpty());
    assertTrue(((CallActivity) actualConvertJsonToElementResult).getInParameters().isEmpty());
    assertTrue(((CallActivity) actualConvertJsonToElementResult).getOutParameters().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((CallActivity) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((CallActivity) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((CallActivity) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test new {@link CallActivityJsonConverter} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link CallActivityJsonConverter}
   */
  @Test
  @DisplayName("Test new CallActivityJsonConverter (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void CallActivityJsonConverter.<init>()"})
  void testNewCallActivityJsonConverter() {
    // Arrange and Act
    CallActivityJsonConverter actualCallActivityJsonConverter = new CallActivityJsonConverter();

    // Assert
    assertNull(actualCallActivityJsonConverter.shapesArrayNode);
    assertNull(actualCallActivityJsonConverter.flowElementNode);
    assertNull(actualCallActivityJsonConverter.model);
    assertNull(actualCallActivityJsonConverter.processor);
    assertEquals(0.0d, actualCallActivityJsonConverter.subProcessX);
    assertEquals(0.0d, actualCallActivityJsonConverter.subProcessY);
  }
}
