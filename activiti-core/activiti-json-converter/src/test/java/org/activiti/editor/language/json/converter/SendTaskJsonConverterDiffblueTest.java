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
import org.activiti.bpmn.model.SendTask;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SendTaskJsonConverterDiffblueTest {
  /**
   * Test {@link SendTaskJsonConverter#fillJsonTypes(Map)}.
   *
   * <p>Method under test: {@link SendTaskJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SendTaskJsonConverter.fillJsonTypes(Map)"})
  void testFillJsonTypes() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();

    // Act
    SendTaskJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(1, convertersToBpmnMap.size());
    Class<SendTaskJsonConverter> expectedGetResult = SendTaskJsonConverter.class;
    assertEquals(expectedGetResult, convertersToBpmnMap.get("SendTask"));
  }

  /**
   * Test {@link SendTaskJsonConverter#getStencilId(BaseElement)}.
   *
   * <p>Method under test: {@link SendTaskJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String SendTaskJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId() {
    // Arrange
    SendTaskJsonConverter sendTaskJsonConverter = new SendTaskJsonConverter();

    // Act and Assert
    assertEquals("SendTask", sendTaskJsonConverter.getStencilId(new ActivitiListener()));
  }

  /**
   * Test {@link SendTaskJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <p>Method under test: {@link SendTaskJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement SendTaskJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement() {
    // Arrange
    SendTaskJsonConverter sendTaskJsonConverter = new SendTaskJsonConverter();
    DoubleNode elementNode = DoubleNode.valueOf(10.0d);
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);

    // Act
    FlowElement actualConvertJsonToElementResult =
        sendTaskJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    assertTrue(actualConvertJsonToElementResult instanceof SendTask);
    assertNull(((SendTask) actualConvertJsonToElementResult).getBehavior());
    assertNull(((SendTask) actualConvertJsonToElementResult).getDefaultFlow());
    assertNull(((SendTask) actualConvertJsonToElementResult).getFailedJobRetryTimeCycleValue());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((SendTask) actualConvertJsonToElementResult).getImplementationType());
    assertNull(((SendTask) actualConvertJsonToElementResult).getOperationRef());
    assertNull(((SendTask) actualConvertJsonToElementResult).getType());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(((SendTask) actualConvertJsonToElementResult).getIoSpecification());
    assertNull(((SendTask) actualConvertJsonToElementResult).getLoopCharacteristics());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(
        ((SendTask) actualConvertJsonToElementResult).hasMultiInstanceLoopCharacteristics());
    assertFalse(((SendTask) actualConvertJsonToElementResult).isForCompensation());
    assertFalse(((SendTask) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((SendTask) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((SendTask) actualConvertJsonToElementResult).getBoundaryEvents().isEmpty());
    assertTrue(((SendTask) actualConvertJsonToElementResult).getDataInputAssociations().isEmpty());
    assertTrue(((SendTask) actualConvertJsonToElementResult).getDataOutputAssociations().isEmpty());
    assertTrue(((SendTask) actualConvertJsonToElementResult).getMapExceptions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((SendTask) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((SendTask) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(((SendTask) actualConvertJsonToElementResult).getFieldExtensions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((SendTask) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test new {@link SendTaskJsonConverter} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link SendTaskJsonConverter}
   */
  @Test
  @DisplayName("Test new SendTaskJsonConverter (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void SendTaskJsonConverter.<init>()"})
  void testNewSendTaskJsonConverter() {
    // Arrange and Act
    SendTaskJsonConverter actualSendTaskJsonConverter = new SendTaskJsonConverter();

    // Assert
    assertNull(actualSendTaskJsonConverter.shapesArrayNode);
    assertNull(actualSendTaskJsonConverter.flowElementNode);
    assertNull(actualSendTaskJsonConverter.model);
    assertNull(actualSendTaskJsonConverter.processor);
    assertEquals(0.0d, actualSendTaskJsonConverter.subProcessX);
    assertEquals(0.0d, actualSendTaskJsonConverter.subProcessY);
  }
}
