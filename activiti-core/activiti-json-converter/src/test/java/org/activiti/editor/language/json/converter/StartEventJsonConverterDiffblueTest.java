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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
   * <p>
   * Method under test: {@link StartEventJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void StartEventJsonConverter.fillJsonTypes(Map)"})
  void testFillJsonTypes() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();

    // Act
    StartEventJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(5, convertersToBpmnMap.size());
    Class<StartEventJsonConverter> expectedGetResult = StartEventJsonConverter.class;
    Class<? extends BaseBpmnJsonConverter> getResult = convertersToBpmnMap.get("StartMessageEvent");
    assertEquals(expectedGetResult, getResult);
    assertSame(getResult, convertersToBpmnMap.get("StartErrorEvent"));
    assertSame(getResult, convertersToBpmnMap.get("StartNoneEvent"));
    assertSame(getResult, convertersToBpmnMap.get("StartSignalEvent"));
    assertSame(getResult, convertersToBpmnMap.get("StartTimerEvent"));
  }

  /**
   * Test {@link StartEventJsonConverter#getStencilId(BaseElement)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link CancelEventDefinition} (default constructor).</li>
   *   <li>Then return {@code StartNoneEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StartEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement); given ArrayList() add CancelEventDefinition (default constructor); then return 'StartNoneEvent'")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ErrorEventDefinition} (default constructor).</li>
   *   <li>Then return {@code StartErrorEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StartEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement); given ArrayList() add ErrorEventDefinition (default constructor); then return 'StartErrorEvent'")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link TimerEventDefinition} (default constructor).</li>
   *   <li>Then return {@code StartTimerEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StartEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement); given ArrayList() add TimerEventDefinition (default constructor); then return 'StartTimerEvent'")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>Then return {@code StartMessageEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StartEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement); then return 'StartMessageEvent'")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>Then return {@code StartSignalEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StartEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement); then return 'StartSignalEvent'")
  @Tag("MaintainedByDiffblue")
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
   * Test {@link StartEventJsonConverter#convertElementToJson(ObjectNode, BaseElement)}.
   * <p>
   * Method under test: {@link StartEventJsonConverter#convertElementToJson(ObjectNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertElementToJson(ObjectNode, BaseElement)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void StartEventJsonConverter.convertElementToJson(ObjectNode, BaseElement)"})
  void testConvertElementToJson() {
    // Arrange
    StartEventJsonConverter startEventJsonConverter = new StartEventJsonConverter();
    startEventJsonConverter.setFormKeyMap(null);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    StartEvent baseElement = new StartEvent();
    baseElement.setInitiator("not empty");
    baseElement.setFormKey("not empty");

    // Act
    startEventJsonConverter.convertElementToJson(propertiesNode, baseElement);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode expectedTextNode = iteratorResult.next();
    JsonNode nextResult = iteratorResult.next();
    boolean actualHasNextResult = iteratorResult.hasNext();
    assertTrue(nextResult instanceof TextNode);
    assertEquals("{\n  \"initiator\" : \"not empty\",\n  \"formkeydefinition\" : \"not empty\"\n}",
        propertiesNode.toPrettyString());
    assertEquals(2, propertiesNode.size());
    assertFalse(actualHasNextResult);
    assertEquals(expectedTextNode, nextResult);
  }

  /**
   * Test {@link StartEventJsonConverter#convertElementToJson(ObjectNode, BaseElement)}.
   * <p>
   * Method under test: {@link StartEventJsonConverter#convertElementToJson(ObjectNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertElementToJson(ObjectNode, BaseElement)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void StartEventJsonConverter.convertElementToJson(ObjectNode, BaseElement)"})
  void testConvertElementToJson2() {
    // Arrange
    StartEventJsonConverter startEventJsonConverter = new StartEventJsonConverter();
    startEventJsonConverter.setFormKeyMap(null);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    StartEvent baseElement = new StartEvent();
    baseElement.setInitiator("not empty");
    baseElement.setFormKey(null);

    // Act
    startEventJsonConverter.convertElementToJson(propertiesNode, baseElement);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    assertTrue(iteratorResult.next() instanceof TextNode);
    assertEquals("{\n  \"initiator\" : \"not empty\"\n}", propertiesNode.toPrettyString());
    assertEquals(1, propertiesNode.size());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link StartEventJsonConverter#convertElementToJson(ObjectNode, BaseElement)}.
   * <p>
   * Method under test: {@link StartEventJsonConverter#convertElementToJson(ObjectNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertElementToJson(ObjectNode, BaseElement)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void StartEventJsonConverter.convertElementToJson(ObjectNode, BaseElement)"})
  void testConvertElementToJson3() {
    // Arrange
    StartEventJsonConverter startEventJsonConverter = new StartEventJsonConverter();
    startEventJsonConverter.setFormKeyMap(null);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    StartEvent baseElement = new StartEvent();
    baseElement.setInitiator(null);
    baseElement.setFormKey("not empty");

    // Act
    startEventJsonConverter.convertElementToJson(propertiesNode, baseElement);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    assertTrue(iteratorResult.next() instanceof TextNode);
    assertEquals("{\n  \"formkeydefinition\" : \"not empty\"\n}", propertiesNode.toPrettyString());
    assertEquals(1, propertiesNode.size());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link StartEventJsonConverter#convertElementToJson(ObjectNode, BaseElement)}.
   * <ul>
   *   <li>Given empty string.</li>
   *   <li>When {@link StartEvent} (default constructor) FormKey is empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link StartEventJsonConverter#convertElementToJson(ObjectNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertElementToJson(ObjectNode, BaseElement); given empty string; when StartEvent (default constructor) FormKey is empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void StartEventJsonConverter.convertElementToJson(ObjectNode, BaseElement)"})
  void testConvertElementToJson_givenEmptyString_whenStartEventFormKeyIsEmptyString() {
    // Arrange
    StartEventJsonConverter startEventJsonConverter = new StartEventJsonConverter();
    startEventJsonConverter.setFormKeyMap(null);
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    StartEvent baseElement = new StartEvent();
    baseElement.setInitiator("not empty");
    baseElement.setFormKey("");

    // Act
    startEventJsonConverter.convertElementToJson(propertiesNode, baseElement);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    assertTrue(iteratorResult.next() instanceof TextNode);
    assertEquals("{\n  \"initiator\" : \"not empty\"\n}", propertiesNode.toPrettyString());
    assertEquals(1, propertiesNode.size());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link StartEventJsonConverter#convertElementToJson(ObjectNode, BaseElement)}.
   * <ul>
   *   <li>Given {@link StartEventJsonConverter} (default constructor) FormKeyMap is {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StartEventJsonConverter#convertElementToJson(ObjectNode, BaseElement)}
   */
  @Test
  @DisplayName("Test convertElementToJson(ObjectNode, BaseElement); given StartEventJsonConverter (default constructor) FormKeyMap is HashMap()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void StartEventJsonConverter.convertElementToJson(ObjectNode, BaseElement)"})
  void testConvertElementToJson_givenStartEventJsonConverterFormKeyMapIsHashMap() {
    // Arrange
    StartEventJsonConverter startEventJsonConverter = new StartEventJsonConverter();
    startEventJsonConverter.setFormKeyMap(new HashMap<>());
    ObjectNode propertiesNode = new ObjectNode(JsonNodeFactory.withExactBigDecimals(true));

    StartEvent baseElement = new StartEvent();
    baseElement.setInitiator("not empty");
    baseElement.setFormKey("not empty");

    // Act
    startEventJsonConverter.convertElementToJson(propertiesNode, baseElement);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode expectedTextNode = iteratorResult.next();
    JsonNode nextResult = iteratorResult.next();
    boolean actualHasNextResult = iteratorResult.hasNext();
    assertTrue(nextResult instanceof TextNode);
    assertEquals("{\n  \"initiator\" : \"not empty\",\n  \"formkeydefinition\" : \"not empty\"\n}",
        propertiesNode.toPrettyString());
    assertEquals(2, propertiesNode.size());
    assertFalse(actualHasNextResult);
    assertEquals(expectedTextNode, nextResult);
  }

  /**
   * Test {@link StartEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link StartEventJsonConverter} (default constructor) FormMap is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StartEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given StartEventJsonConverter (default constructor) FormMap is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement StartEventJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
  void testConvertJsonToElement_givenStartEventJsonConverterFormMapIsNull() {
    // Arrange
    StartEventJsonConverter startEventJsonConverter = new StartEventJsonConverter();
    startEventJsonConverter.setFormMap(null);
    MissingNode elementNode = MissingNode.getInstance();
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = startEventJsonConverter.convertJsonToElement(elementNode, modelNode,
        new HashMap<>());

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
   * <ul>
   *   <li>Given {@link StartEventJsonConverter} (default constructor).</li>
   *   <li>Then return {@link StartEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StartEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given StartEventJsonConverter (default constructor); then return StartEvent")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement StartEventJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
  void testConvertJsonToElement_givenStartEventJsonConverter_thenReturnStartEvent() {
    // Arrange
    StartEventJsonConverter startEventJsonConverter = new StartEventJsonConverter();
    MissingNode elementNode = MissingNode.getInstance();
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = startEventJsonConverter.convertJsonToElement(elementNode, modelNode,
        new HashMap<>());

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
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StartEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement StartEventJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
  void testConvertJsonToElement_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    StartEventJsonConverter startEventJsonConverter = new StartEventJsonConverter();
    ArrayNode elementNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = startEventJsonConverter.convertJsonToElement(elementNode, modelNode,
        new HashMap<>());

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
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then {@link BoundaryEvent} (default constructor) ExtensionElements Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link StartEventJsonConverter#addExtensionElement(String, String, Event)}
   */
  @Test
  @DisplayName("Test addExtensionElement(String, String, Event); when empty string; then BoundaryEvent (default constructor) ExtensionElements Empty")
  @Tag("MaintainedByDiffblue")
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
   * <ul>
   *   <li>When {@code Name}.</li>
   *   <li>Then {@link BoundaryEvent} (default constructor) ExtensionElements size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link StartEventJsonConverter#addExtensionElement(String, String, Event)}
   */
  @Test
  @DisplayName("Test addExtensionElement(String, String, Event); when 'Name'; then BoundaryEvent (default constructor) ExtensionElements size is one")
  @Tag("MaintainedByDiffblue")
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
   * <p>
   * Method under test: default or parameterless constructor of {@link StartEventJsonConverter}
   */
  @Test
  @DisplayName("Test new StartEventJsonConverter (default constructor)")
  @Tag("MaintainedByDiffblue")
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
