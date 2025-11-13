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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.CancelEventDefinition;
import org.activiti.bpmn.model.EventDefinition;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.SignalEventDefinition;
import org.activiti.bpmn.model.ThrowEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ThrowEventJsonConverterDiffblueTest {
  /**
   * Test {@link ThrowEventJsonConverter#fillJsonTypes(Map)}.
   *
   * <p>Method under test: {@link ThrowEventJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ThrowEventJsonConverter.fillJsonTypes(Map)"})
  void testFillJsonTypes() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();

    // Act
    ThrowEventJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(2, convertersToBpmnMap.size());
    Class<ThrowEventJsonConverter> expectedGetResult = ThrowEventJsonConverter.class;
    assertEquals(expectedGetResult, convertersToBpmnMap.get("ThrowNoneEvent"));
    Class<ThrowEventJsonConverter> expectedGetResult2 = ThrowEventJsonConverter.class;
    assertEquals(expectedGetResult2, convertersToBpmnMap.get("ThrowSignalEvent"));
  }

  /**
   * Test {@link ThrowEventJsonConverter#getStencilId(BaseElement)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link CancelEventDefinition} (default
   *       constructor).
   *   <li>Then return {@code ThrowNoneEvent}.
   * </ul>
   *
   * <p>Method under test: {@link ThrowEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName(
      "Test getStencilId(BaseElement); given ArrayList() add CancelEventDefinition (default constructor); then return 'ThrowNoneEvent'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ThrowEventJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId_givenArrayListAddCancelEventDefinition_thenReturnThrowNoneEvent() {
    // Arrange
    ThrowEventJsonConverter throwEventJsonConverter = new ThrowEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());

    ThrowEvent baseElement = new ThrowEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("ThrowNoneEvent", throwEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Test {@link ThrowEventJsonConverter#getStencilId(BaseElement)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link CancelEventDefinition} (default
   *       constructor).
   *   <li>Then return {@code ThrowNoneEvent}.
   * </ul>
   *
   * <p>Method under test: {@link ThrowEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName(
      "Test getStencilId(BaseElement); given ArrayList() add CancelEventDefinition (default constructor); then return 'ThrowNoneEvent'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ThrowEventJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId_givenArrayListAddCancelEventDefinition_thenReturnThrowNoneEvent2() {
    // Arrange
    ThrowEventJsonConverter throwEventJsonConverter = new ThrowEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());
    eventDefinitions.add(new CancelEventDefinition());

    ThrowEvent baseElement = new ThrowEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("ThrowNoneEvent", throwEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Test {@link ThrowEventJsonConverter#getStencilId(BaseElement)}.
   *
   * <ul>
   *   <li>Then return {@code ThrowSignalEvent}.
   * </ul>
   *
   * <p>Method under test: {@link ThrowEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement); then return 'ThrowSignalEvent'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String ThrowEventJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId_thenReturnThrowSignalEvent() {
    // Arrange
    ThrowEventJsonConverter throwEventJsonConverter = new ThrowEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new SignalEventDefinition());

    ThrowEvent baseElement = new ThrowEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("ThrowSignalEvent", throwEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Test {@link ThrowEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.
   *   <li>Then return {@link ThrowEvent}.
   * </ul>
   *
   * <p>Method under test: {@link ThrowEventJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); when HashMap(); then return ThrowEvent")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement ThrowEventJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_whenHashMap_thenReturnThrowEvent() {
    // Arrange
    ThrowEventJsonConverter throwEventJsonConverter = new ThrowEventJsonConverter();
    DoubleNode elementNode = DoubleNode.valueOf(10.0d);
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);

    // Act
    FlowElement actualConvertJsonToElementResult =
        throwEventJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    assertTrue(actualConvertJsonToElementResult instanceof ThrowEvent);
    assertNull(((ThrowEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((ThrowEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((ThrowEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((ThrowEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((ThrowEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((ThrowEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((ThrowEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test new {@link ThrowEventJsonConverter} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link ThrowEventJsonConverter}
   */
  @Test
  @DisplayName("Test new ThrowEventJsonConverter (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ThrowEventJsonConverter.<init>()"})
  void testNewThrowEventJsonConverter() {
    // Arrange and Act
    ThrowEventJsonConverter actualThrowEventJsonConverter = new ThrowEventJsonConverter();

    // Assert
    assertNull(actualThrowEventJsonConverter.shapesArrayNode);
    assertNull(actualThrowEventJsonConverter.flowElementNode);
    assertNull(actualThrowEventJsonConverter.model);
    assertNull(actualThrowEventJsonConverter.processor);
    assertEquals(0.0d, actualThrowEventJsonConverter.subProcessX);
    assertEquals(0.0d, actualThrowEventJsonConverter.subProcessY);
  }
}
