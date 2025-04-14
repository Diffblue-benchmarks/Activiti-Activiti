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
import com.fasterxml.jackson.databind.node.MissingNode;
import java.util.HashMap;
import java.util.Map;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.InclusiveGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class InclusiveGatewayJsonConverterDiffblueTest {
  /**
   * Test {@link InclusiveGatewayJsonConverter#fillJsonTypes(Map)}.
   * <p>
   * Method under test: {@link InclusiveGatewayJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InclusiveGatewayJsonConverter.fillJsonTypes(Map)"})
  void testFillJsonTypes() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();

    // Act
    InclusiveGatewayJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(1, convertersToBpmnMap.size());
    Class<InclusiveGatewayJsonConverter> expectedGetResult = InclusiveGatewayJsonConverter.class;
    assertEquals(expectedGetResult, convertersToBpmnMap.get("InclusiveGateway"));
  }

  /**
   * Test {@link InclusiveGatewayJsonConverter#getStencilId(BaseElement)}.
   * <p>
   * Method under test: {@link InclusiveGatewayJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String InclusiveGatewayJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId() {
    // Arrange
    InclusiveGatewayJsonConverter inclusiveGatewayJsonConverter = new InclusiveGatewayJsonConverter();

    // Act and Assert
    assertEquals("InclusiveGateway", inclusiveGatewayJsonConverter.getStencilId(new ActivitiListener()));
  }

  /**
   * Test {@link InclusiveGatewayJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <p>
   * Method under test: {@link InclusiveGatewayJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement InclusiveGatewayJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
  void testConvertJsonToElement() {
    // Arrange
    InclusiveGatewayJsonConverter inclusiveGatewayJsonConverter = new InclusiveGatewayJsonConverter();
    MissingNode elementNode = MissingNode.getInstance();
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = inclusiveGatewayJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    assertTrue(actualConvertJsonToElementResult instanceof InclusiveGateway);
    assertNull(((InclusiveGateway) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((InclusiveGateway) actualConvertJsonToElementResult).getDefaultFlow());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((InclusiveGateway) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((InclusiveGateway) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((InclusiveGateway) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((InclusiveGateway) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((InclusiveGateway) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test new {@link InclusiveGatewayJsonConverter} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link InclusiveGatewayJsonConverter}
   */
  @Test
  @DisplayName("Test new InclusiveGatewayJsonConverter (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InclusiveGatewayJsonConverter.<init>()"})
  void testNewInclusiveGatewayJsonConverter() {
    // Arrange and Act
    InclusiveGatewayJsonConverter actualInclusiveGatewayJsonConverter = new InclusiveGatewayJsonConverter();

    // Assert
    assertNull(actualInclusiveGatewayJsonConverter.shapesArrayNode);
    assertNull(actualInclusiveGatewayJsonConverter.flowElementNode);
    assertNull(actualInclusiveGatewayJsonConverter.model);
    assertNull(actualInclusiveGatewayJsonConverter.processor);
    assertEquals(0.0d, actualInclusiveGatewayJsonConverter.subProcessX);
    assertEquals(0.0d, actualInclusiveGatewayJsonConverter.subProcessY);
  }
}
