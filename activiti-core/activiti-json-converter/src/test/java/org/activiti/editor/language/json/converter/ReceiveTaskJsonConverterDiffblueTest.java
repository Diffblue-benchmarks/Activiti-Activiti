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
import java.util.HashMap;
import java.util.Map;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.ReceiveTask;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ReceiveTaskJsonConverterDiffblueTest {
  /**
   * Test {@link ReceiveTaskJsonConverter#fillJsonTypes(Map)}.
   *
   * <p>Method under test: {@link ReceiveTaskJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReceiveTaskJsonConverter.fillJsonTypes(Map)"})
  void testFillJsonTypes() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();

    // Act
    ReceiveTaskJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(1, convertersToBpmnMap.size());
    Class<ReceiveTaskJsonConverter> expectedGetResult = ReceiveTaskJsonConverter.class;
    assertEquals(expectedGetResult, convertersToBpmnMap.get("ReceiveTask"));
  }

  /**
   * Test {@link ReceiveTaskJsonConverter#getStencilId(BaseElement)}.
   *
   * <p>Method under test: {@link ReceiveTaskJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ReceiveTaskJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId() {
    // Arrange
    ReceiveTaskJsonConverter receiveTaskJsonConverter = new ReceiveTaskJsonConverter();

    // Act and Assert
    assertEquals("ReceiveTask", receiveTaskJsonConverter.getStencilId(new ActivitiListener()));
  }

  /**
   * Test {@link ReceiveTaskJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <p>Method under test: {@link ReceiveTaskJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement ReceiveTaskJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement() {
    // Arrange
    ReceiveTaskJsonConverter receiveTaskJsonConverter = new ReceiveTaskJsonConverter();
    DoubleNode elementNode = DoubleNode.valueOf(10.0d);
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);

    // Act
    FlowElement actualConvertJsonToElementResult =
        receiveTaskJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    assertTrue(actualConvertJsonToElementResult instanceof ReceiveTask);
    assertNull(((ReceiveTask) actualConvertJsonToElementResult).getBehavior());
    assertNull(((ReceiveTask) actualConvertJsonToElementResult).getDefaultFlow());
    assertNull(((ReceiveTask) actualConvertJsonToElementResult).getFailedJobRetryTimeCycleValue());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(((ReceiveTask) actualConvertJsonToElementResult).getIoSpecification());
    assertNull(((ReceiveTask) actualConvertJsonToElementResult).getLoopCharacteristics());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(
        ((ReceiveTask) actualConvertJsonToElementResult).hasMultiInstanceLoopCharacteristics());
    assertFalse(((ReceiveTask) actualConvertJsonToElementResult).isForCompensation());
    assertFalse(((ReceiveTask) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((ReceiveTask) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((ReceiveTask) actualConvertJsonToElementResult).getBoundaryEvents().isEmpty());
    assertTrue(
        ((ReceiveTask) actualConvertJsonToElementResult).getDataInputAssociations().isEmpty());
    assertTrue(
        ((ReceiveTask) actualConvertJsonToElementResult).getDataOutputAssociations().isEmpty());
    assertTrue(((ReceiveTask) actualConvertJsonToElementResult).getMapExceptions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((ReceiveTask) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((ReceiveTask) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((ReceiveTask) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test new {@link ReceiveTaskJsonConverter} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link ReceiveTaskJsonConverter}
   */
  @Test
  @DisplayName("Test new ReceiveTaskJsonConverter (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ReceiveTaskJsonConverter.<init>()"})
  void testNewReceiveTaskJsonConverter() {
    // Arrange and Act
    ReceiveTaskJsonConverter actualReceiveTaskJsonConverter = new ReceiveTaskJsonConverter();

    // Assert
    assertNull(actualReceiveTaskJsonConverter.shapesArrayNode);
    assertNull(actualReceiveTaskJsonConverter.flowElementNode);
    assertNull(actualReceiveTaskJsonConverter.model);
    assertNull(actualReceiveTaskJsonConverter.processor);
    assertEquals(0.0d, actualReceiveTaskJsonConverter.subProcessX);
    assertEquals(0.0d, actualReceiveTaskJsonConverter.subProcessY);
  }
}
