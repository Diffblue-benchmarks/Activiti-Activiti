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
import org.activiti.bpmn.model.ManualTask;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ManualTaskJsonConverterDiffblueTest {
  /**
   * Test {@link ManualTaskJsonConverter#fillJsonTypes(Map)}.
   *
   * <p>Method under test: {@link ManualTaskJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ManualTaskJsonConverter.fillJsonTypes(Map)"})
  void testFillJsonTypes() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();

    // Act
    ManualTaskJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(1, convertersToBpmnMap.size());
    Class<ManualTaskJsonConverter> expectedGetResult = ManualTaskJsonConverter.class;
    assertEquals(expectedGetResult, convertersToBpmnMap.get("ManualTask"));
  }

  /**
   * Test {@link ManualTaskJsonConverter#getStencilId(BaseElement)}.
   *
   * <p>Method under test: {@link ManualTaskJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ManualTaskJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId() {
    // Arrange
    ManualTaskJsonConverter manualTaskJsonConverter = new ManualTaskJsonConverter();

    // Act and Assert
    assertEquals("ManualTask", manualTaskJsonConverter.getStencilId(new ActivitiListener()));
  }

  /**
   * Test {@link ManualTaskJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <p>Method under test: {@link ManualTaskJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement ManualTaskJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement() {
    // Arrange
    ManualTaskJsonConverter manualTaskJsonConverter = new ManualTaskJsonConverter();
    DoubleNode elementNode = DoubleNode.valueOf(10.0d);
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);

    // Act
    FlowElement actualConvertJsonToElementResult =
        manualTaskJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    assertTrue(actualConvertJsonToElementResult instanceof ManualTask);
    assertNull(((ManualTask) actualConvertJsonToElementResult).getBehavior());
    assertNull(((ManualTask) actualConvertJsonToElementResult).getDefaultFlow());
    assertNull(((ManualTask) actualConvertJsonToElementResult).getFailedJobRetryTimeCycleValue());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(((ManualTask) actualConvertJsonToElementResult).getIoSpecification());
    assertNull(((ManualTask) actualConvertJsonToElementResult).getLoopCharacteristics());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(
        ((ManualTask) actualConvertJsonToElementResult).hasMultiInstanceLoopCharacteristics());
    assertFalse(((ManualTask) actualConvertJsonToElementResult).isForCompensation());
    assertFalse(((ManualTask) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((ManualTask) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((ManualTask) actualConvertJsonToElementResult).getBoundaryEvents().isEmpty());
    assertTrue(
        ((ManualTask) actualConvertJsonToElementResult).getDataInputAssociations().isEmpty());
    assertTrue(
        ((ManualTask) actualConvertJsonToElementResult).getDataOutputAssociations().isEmpty());
    assertTrue(((ManualTask) actualConvertJsonToElementResult).getMapExceptions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((ManualTask) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((ManualTask) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((ManualTask) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test new {@link ManualTaskJsonConverter} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link ManualTaskJsonConverter}
   */
  @Test
  @DisplayName("Test new ManualTaskJsonConverter (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ManualTaskJsonConverter.<init>()"})
  void testNewManualTaskJsonConverter() {
    // Arrange and Act
    ManualTaskJsonConverter actualManualTaskJsonConverter = new ManualTaskJsonConverter();

    // Assert
    assertNull(actualManualTaskJsonConverter.shapesArrayNode);
    assertNull(actualManualTaskJsonConverter.flowElementNode);
    assertNull(actualManualTaskJsonConverter.model);
    assertNull(actualManualTaskJsonConverter.processor);
    assertEquals(0.0d, actualManualTaskJsonConverter.subProcessX);
    assertEquals(0.0d, actualManualTaskJsonConverter.subProcessY);
  }
}
