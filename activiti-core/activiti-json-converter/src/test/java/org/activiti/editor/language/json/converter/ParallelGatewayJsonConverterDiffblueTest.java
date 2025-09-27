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
import org.activiti.bpmn.model.ParallelGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ParallelGatewayJsonConverterDiffblueTest {
  /**
   * Test {@link ParallelGatewayJsonConverter#fillJsonTypes(Map)}.
   *
   * <p>Method under test: {@link ParallelGatewayJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ParallelGatewayJsonConverter.fillJsonTypes(Map)"})
  void testFillJsonTypes() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();

    // Act
    ParallelGatewayJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(1, convertersToBpmnMap.size());
    Class<ParallelGatewayJsonConverter> expectedGetResult = ParallelGatewayJsonConverter.class;
    assertEquals(expectedGetResult, convertersToBpmnMap.get("ParallelGateway"));
  }

  /**
   * Test {@link ParallelGatewayJsonConverter#getStencilId(BaseElement)}.
   *
   * <p>Method under test: {@link ParallelGatewayJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ParallelGatewayJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId() {
    // Arrange
    ParallelGatewayJsonConverter parallelGatewayJsonConverter = new ParallelGatewayJsonConverter();

    // Act and Assert
    assertEquals(
        "ParallelGateway", parallelGatewayJsonConverter.getStencilId(new ActivitiListener()));
  }

  /**
   * Test {@link ParallelGatewayJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <p>Method under test: {@link ParallelGatewayJsonConverter#convertJsonToElement(JsonNode,
   * JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement ParallelGatewayJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement() {
    // Arrange
    ParallelGatewayJsonConverter parallelGatewayJsonConverter = new ParallelGatewayJsonConverter();
    DoubleNode elementNode = DoubleNode.valueOf(10.0d);
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);

    // Act
    FlowElement actualConvertJsonToElementResult =
        parallelGatewayJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    assertTrue(actualConvertJsonToElementResult instanceof ParallelGateway);
    assertNull(((ParallelGateway) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((ParallelGateway) actualConvertJsonToElementResult).getDefaultFlow());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((ParallelGateway) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((ParallelGateway) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((ParallelGateway) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((ParallelGateway) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((ParallelGateway) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test new {@link ParallelGatewayJsonConverter} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link
   * ParallelGatewayJsonConverter}
   */
  @Test
  @DisplayName("Test new ParallelGatewayJsonConverter (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ParallelGatewayJsonConverter.<init>()"})
  void testNewParallelGatewayJsonConverter() {
    // Arrange and Act
    ParallelGatewayJsonConverter actualParallelGatewayJsonConverter =
        new ParallelGatewayJsonConverter();

    // Assert
    assertNull(actualParallelGatewayJsonConverter.shapesArrayNode);
    assertNull(actualParallelGatewayJsonConverter.flowElementNode);
    assertNull(actualParallelGatewayJsonConverter.model);
    assertNull(actualParallelGatewayJsonConverter.processor);
    assertEquals(0.0d, actualParallelGatewayJsonConverter.subProcessX);
    assertEquals(0.0d, actualParallelGatewayJsonConverter.subProcessY);
  }
}
