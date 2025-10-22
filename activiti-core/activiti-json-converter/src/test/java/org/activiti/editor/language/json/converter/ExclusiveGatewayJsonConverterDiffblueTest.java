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
import org.activiti.bpmn.model.ExclusiveGateway;
import org.activiti.bpmn.model.FlowElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ExclusiveGatewayJsonConverterDiffblueTest {
  /**
   * Test {@link ExclusiveGatewayJsonConverter#fillJsonTypes(Map)}.
   * <p>
   * Method under test: {@link ExclusiveGatewayJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ExclusiveGatewayJsonConverter.fillJsonTypes(Map)"})
  void testFillJsonTypes() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();

    // Act
    ExclusiveGatewayJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(1, convertersToBpmnMap.size());
    Class<ExclusiveGatewayJsonConverter> expectedGetResult = ExclusiveGatewayJsonConverter.class;
    assertEquals(expectedGetResult, convertersToBpmnMap.get("ExclusiveGateway"));
  }

  /**
   * Test {@link ExclusiveGatewayJsonConverter#getStencilId(BaseElement)}.
   * <p>
   * Method under test: {@link ExclusiveGatewayJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String ExclusiveGatewayJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId() {
    // Arrange
    ExclusiveGatewayJsonConverter exclusiveGatewayJsonConverter = new ExclusiveGatewayJsonConverter();

    // Act and Assert
    assertEquals("ExclusiveGateway", exclusiveGatewayJsonConverter.getStencilId(new ActivitiListener()));
  }

  /**
   * Test {@link ExclusiveGatewayJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <p>
   * Method under test: {@link ExclusiveGatewayJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement ExclusiveGatewayJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
  void testConvertJsonToElement() {
    // Arrange
    ExclusiveGatewayJsonConverter exclusiveGatewayJsonConverter = new ExclusiveGatewayJsonConverter();
    MissingNode elementNode = MissingNode.getInstance();
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = exclusiveGatewayJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    assertTrue(actualConvertJsonToElementResult instanceof ExclusiveGateway);
    assertNull(((ExclusiveGateway) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((ExclusiveGateway) actualConvertJsonToElementResult).getDefaultFlow());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((ExclusiveGateway) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((ExclusiveGateway) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((ExclusiveGateway) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((ExclusiveGateway) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((ExclusiveGateway) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test new {@link ExclusiveGatewayJsonConverter} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link ExclusiveGatewayJsonConverter}
   */
  @Test
  @DisplayName("Test new ExclusiveGatewayJsonConverter (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ExclusiveGatewayJsonConverter.<init>()"})
  void testNewExclusiveGatewayJsonConverter() {
    // Arrange and Act
    ExclusiveGatewayJsonConverter actualExclusiveGatewayJsonConverter = new ExclusiveGatewayJsonConverter();

    // Assert
    assertNull(actualExclusiveGatewayJsonConverter.shapesArrayNode);
    assertNull(actualExclusiveGatewayJsonConverter.flowElementNode);
    assertNull(actualExclusiveGatewayJsonConverter.model);
    assertNull(actualExclusiveGatewayJsonConverter.processor);
    assertEquals(0.0d, actualExclusiveGatewayJsonConverter.subProcessX);
    assertEquals(0.0d, actualExclusiveGatewayJsonConverter.subProcessY);
  }
}
