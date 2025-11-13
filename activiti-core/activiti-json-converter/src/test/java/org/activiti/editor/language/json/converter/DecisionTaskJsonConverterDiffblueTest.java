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
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.ServiceTask;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class DecisionTaskJsonConverterDiffblueTest {
  /**
   * Test {@link DecisionTaskJsonConverter#fillTypes(Map, Map)}.
   *
   * <p>Method under test: {@link DecisionTaskJsonConverter#fillTypes(Map, Map)}
   */
  @Test
  @DisplayName("Test fillTypes(Map, Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DecisionTaskJsonConverter.fillTypes(Map, Map)"})
  void testFillTypes() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();

    // Act
    DecisionTaskJsonConverter.fillTypes(convertersToBpmnMap, new HashMap<>());

    // Assert
    assertEquals(1, convertersToBpmnMap.size());
    Class<DecisionTaskJsonConverter> expectedGetResult = DecisionTaskJsonConverter.class;
    assertEquals(expectedGetResult, convertersToBpmnMap.get("DecisionTask"));
  }

  /**
   * Test {@link DecisionTaskJsonConverter#fillJsonTypes(Map)}.
   *
   * <p>Method under test: {@link DecisionTaskJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DecisionTaskJsonConverter.fillJsonTypes(Map)"})
  void testFillJsonTypes() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();

    // Act
    DecisionTaskJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(1, convertersToBpmnMap.size());
    Class<DecisionTaskJsonConverter> expectedGetResult = DecisionTaskJsonConverter.class;
    assertEquals(expectedGetResult, convertersToBpmnMap.get("DecisionTask"));
  }

  /**
   * Test {@link DecisionTaskJsonConverter#getStencilId(BaseElement)}.
   *
   * <p>Method under test: {@link DecisionTaskJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String DecisionTaskJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId() {
    // Arrange
    DecisionTaskJsonConverter decisionTaskJsonConverter = new DecisionTaskJsonConverter();

    // Act and Assert
    assertEquals("DecisionTask", decisionTaskJsonConverter.getStencilId(new ActivitiListener()));
  }

  /**
   * Test {@link DecisionTaskJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.
   *   <li>Then return {@link ServiceTask}.
   * </ul>
   *
   * <p>Method under test: {@link DecisionTaskJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); when HashMap(); then return ServiceTask")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement DecisionTaskJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_whenHashMap_thenReturnServiceTask() {
    // Arrange
    DecisionTaskJsonConverter decisionTaskJsonConverter = new DecisionTaskJsonConverter();
    DoubleNode elementNode = DoubleNode.valueOf(10.0d);
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);

    // Act
    FlowElement actualConvertJsonToElementResult =
        decisionTaskJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    assertTrue(actualConvertJsonToElementResult instanceof ServiceTask);
    assertEquals("dmn", ((ServiceTask) actualConvertJsonToElementResult).getType());
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
   * Test {@link DecisionTaskJsonConverter#addExtensionAttributeToExtension(ExtensionElement,
   * String, String)}.
   *
   * <ul>
   *   <li>Then {@link ExtensionElement} (default constructor) Attributes Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * DecisionTaskJsonConverter#addExtensionAttributeToExtension(ExtensionElement, String, String)}
   */
  @Test
  @DisplayName(
      "Test addExtensionAttributeToExtension(ExtensionElement, String, String); then ExtensionElement (default constructor) Attributes Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DecisionTaskJsonConverter.addExtensionAttributeToExtension(ExtensionElement, String, String)"
  })
  void testAddExtensionAttributeToExtension_thenExtensionElementAttributesEmpty() {
    // Arrange
    DecisionTaskJsonConverter decisionTaskJsonConverter = new DecisionTaskJsonConverter();
    ExtensionElement element = new ExtensionElement();

    // Act
    decisionTaskJsonConverter.addExtensionAttributeToExtension(element, "", "42");

    // Assert that nothing has changed
    assertTrue(element.getAttributes().isEmpty());
  }

  /**
   * Test {@link DecisionTaskJsonConverter#addExtensionAttributeToExtension(ExtensionElement,
   * String, String)}.
   *
   * <ul>
   *   <li>Then {@link ExtensionElement} (default constructor) Attributes size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * DecisionTaskJsonConverter#addExtensionAttributeToExtension(ExtensionElement, String, String)}
   */
  @Test
  @DisplayName(
      "Test addExtensionAttributeToExtension(ExtensionElement, String, String); then ExtensionElement (default constructor) Attributes size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DecisionTaskJsonConverter.addExtensionAttributeToExtension(ExtensionElement, String, String)"
  })
  void testAddExtensionAttributeToExtension_thenExtensionElementAttributesSizeIsOne() {
    // Arrange
    DecisionTaskJsonConverter decisionTaskJsonConverter = new DecisionTaskJsonConverter();
    ExtensionElement element = new ExtensionElement();

    // Act
    decisionTaskJsonConverter.addExtensionAttributeToExtension(element, "Attribute Name", "42");

    // Assert
    Map<String, List<ExtensionAttribute>> attributes = element.getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Attribute Name");
    assertEquals(1, getResult.size());
    ExtensionAttribute getResult2 = getResult.get(0);
    assertEquals("42", getResult2.getValue());
    assertEquals("Attribute Name", getResult2.getName());
    assertEquals("modeler", getResult2.getNamespacePrefix());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult2.getNamespace());
  }

  /**
   * Test new {@link DecisionTaskJsonConverter} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link DecisionTaskJsonConverter}
   */
  @Test
  @DisplayName("Test new DecisionTaskJsonConverter (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DecisionTaskJsonConverter.<init>()"})
  void testNewDecisionTaskJsonConverter() {
    // Arrange and Act
    DecisionTaskJsonConverter actualDecisionTaskJsonConverter = new DecisionTaskJsonConverter();

    // Assert
    assertNull(actualDecisionTaskJsonConverter.shapesArrayNode);
    assertNull(actualDecisionTaskJsonConverter.flowElementNode);
    assertNull(actualDecisionTaskJsonConverter.decisionTableMap);
    assertNull(actualDecisionTaskJsonConverter.model);
    assertNull(actualDecisionTaskJsonConverter.processor);
    assertEquals(0.0d, actualDecisionTaskJsonConverter.subProcessX);
    assertEquals(0.0d, actualDecisionTaskJsonConverter.subProcessY);
  }
}
