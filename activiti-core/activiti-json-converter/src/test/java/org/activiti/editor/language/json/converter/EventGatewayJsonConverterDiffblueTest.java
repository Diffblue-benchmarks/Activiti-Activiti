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
import org.activiti.bpmn.model.EventGateway;
import org.activiti.bpmn.model.FlowElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class EventGatewayJsonConverterDiffblueTest {
  /**
   * Test {@link EventGatewayJsonConverter#fillJsonTypes(Map)}.
   *
   * <p>Method under test: {@link EventGatewayJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventGatewayJsonConverter.fillJsonTypes(Map)"})
  void testFillJsonTypes() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();

    // Act
    EventGatewayJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(1, convertersToBpmnMap.size());
    Class<EventGatewayJsonConverter> expectedGetResult = EventGatewayJsonConverter.class;
    assertEquals(expectedGetResult, convertersToBpmnMap.get("EventGateway"));
  }

  /**
   * Test {@link EventGatewayJsonConverter#getStencilId(BaseElement)}.
   *
   * <p>Method under test: {@link EventGatewayJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String EventGatewayJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId() {
    // Arrange
    EventGatewayJsonConverter eventGatewayJsonConverter = new EventGatewayJsonConverter();

    // Act and Assert
    assertEquals("EventGateway", eventGatewayJsonConverter.getStencilId(new ActivitiListener()));
  }

  /**
   * Test {@link EventGatewayJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <p>Method under test: {@link EventGatewayJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement EventGatewayJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement() {
    // Arrange
    EventGatewayJsonConverter eventGatewayJsonConverter = new EventGatewayJsonConverter();
    DoubleNode elementNode = DoubleNode.valueOf(10.0d);
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);

    // Act
    FlowElement actualConvertJsonToElementResult =
        eventGatewayJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    assertTrue(actualConvertJsonToElementResult instanceof EventGateway);
    assertNull(((EventGateway) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((EventGateway) actualConvertJsonToElementResult).getDefaultFlow());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((EventGateway) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((EventGateway) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((EventGateway) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((EventGateway) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((EventGateway) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test new {@link EventGatewayJsonConverter} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link EventGatewayJsonConverter}
   */
  @Test
  @DisplayName("Test new EventGatewayJsonConverter (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void EventGatewayJsonConverter.<init>()"})
  void testNewEventGatewayJsonConverter() {
    // Arrange and Act
    EventGatewayJsonConverter actualEventGatewayJsonConverter = new EventGatewayJsonConverter();

    // Assert
    assertNull(actualEventGatewayJsonConverter.shapesArrayNode);
    assertNull(actualEventGatewayJsonConverter.flowElementNode);
    assertNull(actualEventGatewayJsonConverter.model);
    assertNull(actualEventGatewayJsonConverter.processor);
    assertEquals(0.0d, actualEventGatewayJsonConverter.subProcessX);
    assertEquals(0.0d, actualEventGatewayJsonConverter.subProcessY);
  }
}
