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
import org.activiti.bpmn.model.ServiceTask;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class MailTaskJsonConverterDiffblueTest {
  /**
   * Test {@link MailTaskJsonConverter#fillTypes(Map, Map)}.
   *
   * <p>Method under test: {@link MailTaskJsonConverter#fillTypes(Map, Map)}
   */
  @Test
  @DisplayName("Test fillTypes(Map, Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailTaskJsonConverter.fillTypes(Map, Map)"})
  void testFillTypes() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();

    // Act
    MailTaskJsonConverter.fillTypes(convertersToBpmnMap, new HashMap<>());

    // Assert
    assertEquals(1, convertersToBpmnMap.size());
    Class<MailTaskJsonConverter> expectedGetResult = MailTaskJsonConverter.class;
    assertEquals(expectedGetResult, convertersToBpmnMap.get("MailTask"));
  }

  /**
   * Test {@link MailTaskJsonConverter#fillJsonTypes(Map)}.
   *
   * <p>Method under test: {@link MailTaskJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailTaskJsonConverter.fillJsonTypes(Map)"})
  void testFillJsonTypes() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();

    // Act
    MailTaskJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(1, convertersToBpmnMap.size());
    Class<MailTaskJsonConverter> expectedGetResult = MailTaskJsonConverter.class;
    assertEquals(expectedGetResult, convertersToBpmnMap.get("MailTask"));
  }

  /**
   * Test {@link MailTaskJsonConverter#getStencilId(BaseElement)}.
   *
   * <p>Method under test: {@link MailTaskJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String MailTaskJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId() {
    // Arrange
    MailTaskJsonConverter mailTaskJsonConverter = new MailTaskJsonConverter();

    // Act and Assert
    assertEquals("MailTask", mailTaskJsonConverter.getStencilId(new ActivitiListener()));
  }

  /**
   * Test {@link MailTaskJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.
   *   <li>Then return {@link ServiceTask}.
   * </ul>
   *
   * <p>Method under test: {@link MailTaskJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); when HashMap(); then return ServiceTask")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement MailTaskJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_whenHashMap_thenReturnServiceTask() {
    // Arrange
    MailTaskJsonConverter mailTaskJsonConverter = new MailTaskJsonConverter();
    DoubleNode elementNode = DoubleNode.valueOf(10.0d);
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);

    // Act
    FlowElement actualConvertJsonToElementResult =
        mailTaskJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    assertTrue(actualConvertJsonToElementResult instanceof ServiceTask);
    assertEquals("mail", ((ServiceTask) actualConvertJsonToElementResult).getType());
    assertNull(((ServiceTask) actualConvertJsonToElementResult).getBehavior());
    assertNull(((ServiceTask) actualConvertJsonToElementResult).getDefaultFlow());
    assertNull(((ServiceTask) actualConvertJsonToElementResult).getFailedJobRetryTimeCycleValue());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((ServiceTask) actualConvertJsonToElementResult).getExtensionId());
    assertNull(((ServiceTask) actualConvertJsonToElementResult).getImplementation());
    assertNull(((ServiceTask) actualConvertJsonToElementResult).getImplementationType());
    assertNull(((ServiceTask) actualConvertJsonToElementResult).getOperationRef());
    assertNull(((ServiceTask) actualConvertJsonToElementResult).getResultVariableName());
    assertNull(((ServiceTask) actualConvertJsonToElementResult).getSkipExpression());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(((ServiceTask) actualConvertJsonToElementResult).getIoSpecification());
    assertNull(((ServiceTask) actualConvertJsonToElementResult).getLoopCharacteristics());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(
        ((ServiceTask) actualConvertJsonToElementResult).hasMultiInstanceLoopCharacteristics());
    assertFalse(((ServiceTask) actualConvertJsonToElementResult).isForCompensation());
    assertFalse(((ServiceTask) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((ServiceTask) actualConvertJsonToElementResult).isNotExclusive());
    assertFalse(((ServiceTask) actualConvertJsonToElementResult).isExtended());
    assertTrue(((ServiceTask) actualConvertJsonToElementResult).getBoundaryEvents().isEmpty());
    assertTrue(
        ((ServiceTask) actualConvertJsonToElementResult).getDataInputAssociations().isEmpty());
    assertTrue(
        ((ServiceTask) actualConvertJsonToElementResult).getDataOutputAssociations().isEmpty());
    assertTrue(((ServiceTask) actualConvertJsonToElementResult).getMapExceptions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((ServiceTask) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((ServiceTask) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(((ServiceTask) actualConvertJsonToElementResult).getCustomProperties().isEmpty());
    assertTrue(((ServiceTask) actualConvertJsonToElementResult).getFieldExtensions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((ServiceTask) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test new {@link MailTaskJsonConverter} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link MailTaskJsonConverter}
   */
  @Test
  @DisplayName("Test new MailTaskJsonConverter (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void MailTaskJsonConverter.<init>()"})
  void testNewMailTaskJsonConverter() {
    // Arrange and Act
    MailTaskJsonConverter actualMailTaskJsonConverter = new MailTaskJsonConverter();

    // Assert
    assertNull(actualMailTaskJsonConverter.shapesArrayNode);
    assertNull(actualMailTaskJsonConverter.flowElementNode);
    assertNull(actualMailTaskJsonConverter.model);
    assertNull(actualMailTaskJsonConverter.processor);
    assertEquals(0.0d, actualMailTaskJsonConverter.subProcessX);
    assertEquals(0.0d, actualMailTaskJsonConverter.subProcessY);
  }
}
