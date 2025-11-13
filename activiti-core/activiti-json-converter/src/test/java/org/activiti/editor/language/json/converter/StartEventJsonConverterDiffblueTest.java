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
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.CancelEventDefinition;
import org.activiti.bpmn.model.ErrorEventDefinition;
import org.activiti.bpmn.model.Event;
import org.activiti.bpmn.model.EventDefinition;
import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.SignalEventDefinition;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.TimerEventDefinition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class StartEventJsonConverterDiffblueTest {
  /**
   * Test {@link StartEventJsonConverter#fillJsonTypes(Map)}.
   *
   * <p>Method under test: {@link StartEventJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartEventJsonConverter.fillJsonTypes(Map)"})
  void testFillJsonTypes() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();

    // Act
    StartEventJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(5, convertersToBpmnMap.size());
    Class<StartEventJsonConverter> expectedGetResult = StartEventJsonConverter.class;
    assertEquals(expectedGetResult, convertersToBpmnMap.get("StartErrorEvent"));
    Class<StartEventJsonConverter> expectedGetResult2 = StartEventJsonConverter.class;
    assertEquals(expectedGetResult2, convertersToBpmnMap.get("StartMessageEvent"));
    Class<StartEventJsonConverter> expectedGetResult3 = StartEventJsonConverter.class;
    assertEquals(expectedGetResult3, convertersToBpmnMap.get("StartNoneEvent"));
    Class<StartEventJsonConverter> expectedGetResult4 = StartEventJsonConverter.class;
    assertEquals(expectedGetResult4, convertersToBpmnMap.get("StartSignalEvent"));
    Class<StartEventJsonConverter> expectedGetResult5 = StartEventJsonConverter.class;
    assertEquals(expectedGetResult5, convertersToBpmnMap.get("StartTimerEvent"));
  }

  /**
   * Test {@link StartEventJsonConverter#getStencilId(BaseElement)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link CancelEventDefinition} (default
   *       constructor).
   *   <li>Then return {@code StartNoneEvent}.
   * </ul>
   *
   * <p>Method under test: {@link StartEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName(
      "Test getStencilId(BaseElement); given ArrayList() add CancelEventDefinition (default constructor); then return 'StartNoneEvent'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String StartEventJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId_givenArrayListAddCancelEventDefinition_thenReturnStartNoneEvent() {
    // Arrange
    StartEventJsonConverter startEventJsonConverter = new StartEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());

    BoundaryEvent baseElement = new BoundaryEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("StartNoneEvent", startEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Test {@link StartEventJsonConverter#getStencilId(BaseElement)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ErrorEventDefinition} (default
   *       constructor).
   *   <li>Then return {@code StartErrorEvent}.
   * </ul>
   *
   * <p>Method under test: {@link StartEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName(
      "Test getStencilId(BaseElement); given ArrayList() add ErrorEventDefinition (default constructor); then return 'StartErrorEvent'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String StartEventJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId_givenArrayListAddErrorEventDefinition_thenReturnStartErrorEvent() {
    // Arrange
    StartEventJsonConverter startEventJsonConverter = new StartEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new ErrorEventDefinition());

    BoundaryEvent baseElement = new BoundaryEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("StartErrorEvent", startEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Test {@link StartEventJsonConverter#getStencilId(BaseElement)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link TimerEventDefinition} (default
   *       constructor).
   *   <li>Then return {@code StartTimerEvent}.
   * </ul>
   *
   * <p>Method under test: {@link StartEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName(
      "Test getStencilId(BaseElement); given ArrayList() add TimerEventDefinition (default constructor); then return 'StartTimerEvent'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String StartEventJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId_givenArrayListAddTimerEventDefinition_thenReturnStartTimerEvent() {
    // Arrange
    StartEventJsonConverter startEventJsonConverter = new StartEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new TimerEventDefinition());

    BoundaryEvent baseElement = new BoundaryEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("StartTimerEvent", startEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Test {@link StartEventJsonConverter#getStencilId(BaseElement)}.
   *
   * <ul>
   *   <li>Then return {@code StartMessageEvent}.
   * </ul>
   *
   * <p>Method under test: {@link StartEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement); then return 'StartMessageEvent'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String StartEventJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId_thenReturnStartMessageEvent() {
    // Arrange
    StartEventJsonConverter startEventJsonConverter = new StartEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new MessageEventDefinition());

    BoundaryEvent baseElement = new BoundaryEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("StartMessageEvent", startEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Test {@link StartEventJsonConverter#getStencilId(BaseElement)}.
   *
   * <ul>
   *   <li>Then return {@code StartSignalEvent}.
   * </ul>
   *
   * <p>Method under test: {@link StartEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement); then return 'StartSignalEvent'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String StartEventJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId_thenReturnStartSignalEvent() {
    // Arrange
    StartEventJsonConverter startEventJsonConverter = new StartEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new SignalEventDefinition());

    BoundaryEvent baseElement = new BoundaryEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("StartSignalEvent", startEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Test {@link StartEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link StartEventJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement StartEventJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    StartEventJsonConverter startEventJsonConverter = new StartEventJsonConverter();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode elementNode = new ArrayNode(nf);
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);

    // Act
    FlowElement actualConvertJsonToElementResult =
        startEventJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    assertTrue(actualConvertJsonToElementResult instanceof StartEvent);
    assertNull(((StartEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((StartEvent) actualConvertJsonToElementResult).getFormKey());
    assertNull(((StartEvent) actualConvertJsonToElementResult).getInitiator());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((StartEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((StartEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertFalse(((StartEvent) actualConvertJsonToElementResult).isInterrupting());
    assertTrue(((StartEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((StartEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((StartEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(((StartEvent) actualConvertJsonToElementResult).getFormProperties().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((StartEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test {@link StartEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   *
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.
   *   <li>Then return {@link StartEvent}.
   * </ul>
   *
   * <p>Method under test: {@link StartEventJsonConverter#convertJsonToElement(JsonNode, JsonNode,
   * Map)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToElement(JsonNode, JsonNode, Map); when HashMap(); then return StartEvent")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "FlowElement StartEventJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"
  })
  void testConvertJsonToElement_whenHashMap_thenReturnStartEvent() {
    // Arrange
    StartEventJsonConverter startEventJsonConverter = new StartEventJsonConverter();
    DoubleNode elementNode = DoubleNode.valueOf(10.0d);
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);

    // Act
    FlowElement actualConvertJsonToElementResult =
        startEventJsonConverter.convertJsonToElement(elementNode, modelNode, new HashMap<>());

    // Assert
    assertTrue(actualConvertJsonToElementResult instanceof StartEvent);
    assertNull(((StartEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((StartEvent) actualConvertJsonToElementResult).getFormKey());
    assertNull(((StartEvent) actualConvertJsonToElementResult).getInitiator());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((StartEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((StartEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertFalse(((StartEvent) actualConvertJsonToElementResult).isInterrupting());
    assertTrue(((StartEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((StartEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((StartEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(((StartEvent) actualConvertJsonToElementResult).getFormProperties().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((StartEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test {@link StartEventJsonConverter#addExtensionElement(String, String, Event)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then {@link BoundaryEvent} (default constructor) ExtensionElements Empty.
   * </ul>
   *
   * <p>Method under test: {@link StartEventJsonConverter#addExtensionElement(String, String,
   * Event)}
   */
  @Test
  @DisplayName(
      "Test addExtensionElement(String, String, Event); when empty string; then BoundaryEvent (default constructor) ExtensionElements Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartEventJsonConverter.addExtensionElement(String, String, Event)"})
  void testAddExtensionElement_whenEmptyString_thenBoundaryEventExtensionElementsEmpty() {
    // Arrange
    StartEventJsonConverter startEventJsonConverter = new StartEventJsonConverter();
    BoundaryEvent event = new BoundaryEvent();

    // Act
    startEventJsonConverter.addExtensionElement("", "Element Text", event);

    // Assert that nothing has changed
    assertTrue(event.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link StartEventJsonConverter#addExtensionElement(String, String, Event)}.
   *
   * <ul>
   *   <li>When {@code Name}.
   *   <li>Then {@link BoundaryEvent} (default constructor) ExtensionElements size is one.
   * </ul>
   *
   * <p>Method under test: {@link StartEventJsonConverter#addExtensionElement(String, String,
   * Event)}
   */
  @Test
  @DisplayName(
      "Test addExtensionElement(String, String, Event); when 'Name'; then BoundaryEvent (default constructor) ExtensionElements size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartEventJsonConverter.addExtensionElement(String, String, Event)"})
  void testAddExtensionElement_whenName_thenBoundaryEventExtensionElementsSizeIsOne() {
    // Arrange
    StartEventJsonConverter startEventJsonConverter = new StartEventJsonConverter();
    BoundaryEvent event = new BoundaryEvent();

    // Act
    startEventJsonConverter.addExtensionElement("Name", "Element Text", event);

    // Assert
    Map<String, List<ExtensionElement>> extensionElements = event.getExtensionElements();
    assertEquals(1, extensionElements.size());
    List<ExtensionElement> getResult = extensionElements.get("Name");
    assertEquals(1, getResult.size());
    ExtensionElement getResult2 = getResult.get(0);
    assertEquals("Element Text", getResult2.getElementText());
    assertEquals("Name", getResult2.getName());
    assertEquals("modeler", getResult2.getNamespacePrefix());
    assertNull(getResult2.getId());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(getResult2.getChildElements().isEmpty());
    assertEquals(BaseBpmnJsonConverter.NAMESPACE, getResult2.getNamespace());
  }

  /**
   * Test new {@link StartEventJsonConverter} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link StartEventJsonConverter}
   */
  @Test
  @DisplayName("Test new StartEventJsonConverter (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartEventJsonConverter.<init>()"})
  void testNewStartEventJsonConverter() {
    // Arrange and Act
    StartEventJsonConverter actualStartEventJsonConverter = new StartEventJsonConverter();

    // Assert
    assertNull(actualStartEventJsonConverter.shapesArrayNode);
    assertNull(actualStartEventJsonConverter.flowElementNode);
    assertNull(actualStartEventJsonConverter.formMap);
    assertNull(actualStartEventJsonConverter.formKeyMap);
    assertNull(actualStartEventJsonConverter.model);
    assertNull(actualStartEventJsonConverter.processor);
    assertEquals(0.0d, actualStartEventJsonConverter.subProcessX);
    assertEquals(0.0d, actualStartEventJsonConverter.subProcessY);
  }
}
