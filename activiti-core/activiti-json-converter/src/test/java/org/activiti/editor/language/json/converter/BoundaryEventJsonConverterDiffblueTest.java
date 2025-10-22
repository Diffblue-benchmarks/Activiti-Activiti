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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.MissingNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.CancelEventDefinition;
import org.activiti.bpmn.model.CompensateEventDefinition;
import org.activiti.bpmn.model.ErrorEventDefinition;
import org.activiti.bpmn.model.EventDefinition;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.SignalEventDefinition;
import org.activiti.bpmn.model.TimerEventDefinition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BoundaryEventJsonConverterDiffblueTest {
  /**
   * Test {@link BoundaryEventJsonConverter#fillJsonTypes(Map)}.
   * <p>
   * Method under test: {@link BoundaryEventJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BoundaryEventJsonConverter.fillJsonTypes(Map)"})
  void testFillJsonTypes() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();

    // Act
    BoundaryEventJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(6, convertersToBpmnMap.size());
    Class<BoundaryEventJsonConverter> expectedGetResult = BoundaryEventJsonConverter.class;
    Class<? extends BaseBpmnJsonConverter> getResult = convertersToBpmnMap.get("BoundaryErrorEvent");
    assertEquals(expectedGetResult, getResult);
    assertSame(getResult, convertersToBpmnMap.get("BoundaryCancelEvent"));
    assertSame(getResult, convertersToBpmnMap.get("BoundaryCompensationEvent"));
    assertSame(getResult, convertersToBpmnMap.get("BoundaryMessageEvent"));
    assertSame(getResult, convertersToBpmnMap.get("BoundarySignalEvent"));
    assertSame(getResult, convertersToBpmnMap.get("BoundaryTimerEvent"));
  }

  /**
   * Test {@link BoundaryEventJsonConverter#getStencilId(BaseElement)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.</li>
   *   <li>Then return {@code BoundaryTimerEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BoundaryEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement); given ArrayList() add 'null'; then return 'BoundaryTimerEvent'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BoundaryEventJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId_givenArrayListAddNull_thenReturnBoundaryTimerEvent() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(null);

    BoundaryEvent baseElement = new BoundaryEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("BoundaryTimerEvent", boundaryEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Test {@link BoundaryEventJsonConverter#getStencilId(BaseElement)}.
   * <ul>
   *   <li>Then return {@code BoundaryCancelEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BoundaryEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement); then return 'BoundaryCancelEvent'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BoundaryEventJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId_thenReturnBoundaryCancelEvent() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());

    BoundaryEvent baseElement = new BoundaryEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("BoundaryCancelEvent", boundaryEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Test {@link BoundaryEventJsonConverter#getStencilId(BaseElement)}.
   * <ul>
   *   <li>Then return {@code BoundaryCompensationEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BoundaryEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement); then return 'BoundaryCompensationEvent'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BoundaryEventJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId_thenReturnBoundaryCompensationEvent() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CompensateEventDefinition());

    BoundaryEvent baseElement = new BoundaryEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("BoundaryCompensationEvent", boundaryEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Test {@link BoundaryEventJsonConverter#getStencilId(BaseElement)}.
   * <ul>
   *   <li>Then return {@code BoundaryErrorEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BoundaryEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement); then return 'BoundaryErrorEvent'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BoundaryEventJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId_thenReturnBoundaryErrorEvent() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new ErrorEventDefinition());

    BoundaryEvent baseElement = new BoundaryEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("BoundaryErrorEvent", boundaryEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Test {@link BoundaryEventJsonConverter#getStencilId(BaseElement)}.
   * <ul>
   *   <li>Then return {@code BoundaryMessageEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BoundaryEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement); then return 'BoundaryMessageEvent'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BoundaryEventJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId_thenReturnBoundaryMessageEvent() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new MessageEventDefinition());

    BoundaryEvent baseElement = new BoundaryEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("BoundaryMessageEvent", boundaryEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Test {@link BoundaryEventJsonConverter#getStencilId(BaseElement)}.
   * <ul>
   *   <li>Then return {@code BoundarySignalEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BoundaryEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement); then return 'BoundarySignalEvent'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BoundaryEventJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId_thenReturnBoundarySignalEvent() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new SignalEventDefinition());

    BoundaryEvent baseElement = new BoundaryEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("BoundarySignalEvent", boundaryEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Test {@link BoundaryEventJsonConverter#getStencilId(BaseElement)}.
   * <ul>
   *   <li>Then return {@code BoundaryTimerEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BoundaryEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement); then return 'BoundaryTimerEvent'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String BoundaryEventJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId_thenReturnBoundaryTimerEvent() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());
    eventDefinitions.add(new CancelEventDefinition());

    BoundaryEvent baseElement = new BoundaryEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("BoundaryTimerEvent", boundaryEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Test {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <p>
   * Method under test: {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement BoundaryEventJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
  void testConvertJsonToElement() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).get(eq("id"));
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <p>
   * Method under test: {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement BoundaryEventJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
  void testConvertJsonToElement2() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode2).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode, atLeast(1)).get(eq("id"));
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <p>
   * Method under test: {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement BoundaryEventJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
  void testConvertJsonToElement3() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode3);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.iterator()).thenReturn(jsonNodeList.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode4);

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode4).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode2, atLeast(1)).get(eq("id"));
    verify(arrayNode2).asText();
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <p>
   * Method under test: {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement BoundaryEventJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
  void testConvertJsonToElement4() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("BoundaryCompensationEvent");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    ArrayNode arrayNode3 = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode3.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode3.size()).thenReturn(3);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode4);
    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode5);

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode5).iterator();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode3).size();
    verify(arrayNode2).asText();
    verify(arrayNode, atLeast(1)).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    List<EventDefinition> eventDefinitions = ((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions();
    assertEquals(1, eventDefinitions.size());
    EventDefinition getResult = eventDefinitions.get(0);
    assertTrue(getResult instanceof CompensateEventDefinition);
    assertNull(((CompensateEventDefinition) getResult).getActivityRef());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertTrue(((CompensateEventDefinition) getResult).isWaitForCompletion());
  }

  /**
   * Test {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <p>
   * Method under test: {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement BoundaryEventJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
  void testConvertJsonToElement5() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode3.size()).thenReturn(3);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode4);
    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode5);

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode5).iterator();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode2, atLeast(1)).get(eq("id"));
    verify(arrayNode3).size();
    verify(arrayNode2).asText();
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayList() add Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement BoundaryEventJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
  void testConvertJsonToElement_givenArrayListAddInstance() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(MissingNode.getInstance());
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode2).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode, atLeast(1)).get(eq("id"));
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add Instance.</li>
   *   <li>Then calls {@link ArrayNode#size()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayList() add Instance; then calls size()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement BoundaryEventJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
  void testConvertJsonToElement_givenArrayListAddInstance_thenCallsSize() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(MissingNode.getInstance());
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode3.size()).thenReturn(3);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode4);
    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode5);

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode5).iterator();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode2, atLeast(1)).get(eq("id"));
    verify(arrayNode3).size();
    verify(arrayNode2).asText();
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode get(String) return Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement BoundaryEventJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
  void testConvertJsonToElement_givenArrayNodeGetReturnInstance() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode arrayNode2 = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode2).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode, atLeast(1)).get(eq("id"));
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   *   <li>Then calls {@link ArrayNode#size()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode get(String) return Instance; then calls size()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement BoundaryEventJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
  void testConvertJsonToElement_givenArrayNodeGetReturnInstance_thenCallsSize() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode3);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode4.size()).thenReturn(3);
    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.get(Mockito.<String>any())).thenReturn(arrayNode4);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode5);
    ArrayNode arrayNode6 = mock(ArrayNode.class);
    when(arrayNode6.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode6);

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode6).iterator();
    verify(arrayNode4, atLeast(1)).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode5, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode2, atLeast(1)).get(eq("id"));
    verify(arrayNode3).get(eq("resourceId"));
    verify(arrayNode4).size();
    verify(arrayNode2).asText();
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   *   <li>When Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode get(String) return Instance; when Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement BoundaryEventJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
  void testConvertJsonToElement_givenArrayNodeGetReturnInstance_whenInstance() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).get(eq("id"));
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#size()} return three.</li>
   *   <li>Then calls {@link ArrayNode#size()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode size() return three; then calls size()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement BoundaryEventJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
  void testConvertJsonToElement_givenArrayNodeSizeReturnThree_thenCallsSize() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    ArrayNode arrayNode3 = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode3.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode3.size()).thenReturn(3);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode4);
    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode5);

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode5).iterator();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode2, atLeast(1)).get(eq("id"));
    verify(arrayNode3).size();
    verify(arrayNode2).asText();
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement BoundaryEventJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
  void testConvertJsonToElement_givenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement BoundaryEventJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
  void testConvertJsonToElement_givenArrayNodeWithNfIsWithExactBigDecimalsTrue2() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(JsonNodeFactory.withExactBigDecimals(true)));

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode, atLeast(1)).get(eq("id"));
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given Instance; when ArrayNode get(String) return Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement BoundaryEventJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
  void testConvertJsonToElement_givenInstance_whenArrayNodeGetReturnInstance() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Given Instance.</li>
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); given Instance; when ArrayNode get(String) return Instance")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement BoundaryEventJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
  void testConvertJsonToElement_givenInstance_whenArrayNodeGetReturnInstance2() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode, atLeast(1)).get(eq("id"));
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Then EventDefinitions first return {@link CancelEventDefinition}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); then EventDefinitions first return CancelEventDefinition")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement BoundaryEventJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
  void testConvertJsonToElement_thenEventDefinitionsFirstReturnCancelEventDefinition() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("BoundaryCancelEvent");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    ArrayNode arrayNode3 = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode3.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode3.size()).thenReturn(3);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode4);
    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode5);

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode5).iterator();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode3).size();
    verify(arrayNode2).asText();
    verify(arrayNode, atLeast(1)).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    List<EventDefinition> eventDefinitions = ((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions();
    assertEquals(1, eventDefinitions.size());
    EventDefinition getResult = eventDefinitions.get(0);
    assertTrue(getResult instanceof CancelEventDefinition);
    assertNull(getResult.getId());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Then EventDefinitions first return {@link ErrorEventDefinition}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); then EventDefinitions first return ErrorEventDefinition")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement BoundaryEventJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
  void testConvertJsonToElement_thenEventDefinitionsFirstReturnErrorEventDefinition() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("BoundaryErrorEvent");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    ArrayNode arrayNode3 = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode3.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode3.size()).thenReturn(3);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode4);
    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode5);

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode5).iterator();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode3).size();
    verify(arrayNode2).asText();
    verify(arrayNode, atLeast(1)).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    List<EventDefinition> eventDefinitions = ((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions();
    assertEquals(1, eventDefinitions.size());
    EventDefinition getResult = eventDefinitions.get(0);
    assertTrue(getResult instanceof ErrorEventDefinition);
    assertEquals("BoundaryErrorEvent", ((ErrorEventDefinition) getResult).getErrorRef());
    assertNull(getResult.getId());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Then EventDefinitions first return {@link MessageEventDefinition}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); then EventDefinitions first return MessageEventDefinition")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement BoundaryEventJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
  void testConvertJsonToElement_thenEventDefinitionsFirstReturnMessageEventDefinition() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("BoundaryMessageEvent");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    ArrayNode arrayNode3 = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode3.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode3.size()).thenReturn(3);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode4);
    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode5);

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode5).iterator();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode3).size();
    verify(arrayNode2).asText();
    verify(arrayNode, atLeast(1)).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    List<EventDefinition> eventDefinitions = ((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions();
    assertEquals(1, eventDefinitions.size());
    EventDefinition getResult = eventDefinitions.get(0);
    assertTrue(getResult instanceof MessageEventDefinition);
    assertEquals("BoundaryMessageEvent", ((MessageEventDefinition) getResult).getMessageRef());
    assertNull(((MessageEventDefinition) getResult).getCorrelationKey());
    assertNull(((MessageEventDefinition) getResult).getMessageExpression());
    assertTrue(((MessageEventDefinition) getResult).getFieldExtensions().isEmpty());
  }

  /**
   * Test {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Then EventDefinitions first return {@link SignalEventDefinition}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); then EventDefinitions first return SignalEventDefinition")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement BoundaryEventJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
  void testConvertJsonToElement_thenEventDefinitionsFirstReturnSignalEventDefinition() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("BoundarySignalEvent");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    ArrayNode arrayNode3 = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode3.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode3.size()).thenReturn(3);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode4);
    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode5);

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode5).iterator();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode3).size();
    verify(arrayNode2).asText();
    verify(arrayNode, atLeast(1)).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    List<EventDefinition> eventDefinitions = ((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions();
    assertEquals(1, eventDefinitions.size());
    EventDefinition getResult = eventDefinitions.get(0);
    assertTrue(getResult instanceof SignalEventDefinition);
    assertEquals("BoundarySignalEvent", ((SignalEventDefinition) getResult).getSignalRef());
    assertNull(((SignalEventDefinition) getResult).getSignalExpression());
    assertFalse(((SignalEventDefinition) getResult).isAsync());
  }

  /**
   * Test {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Then EventDefinitions first return {@link TimerEventDefinition}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); then EventDefinitions first return TimerEventDefinition")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement BoundaryEventJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
  void testConvertJsonToElement_thenEventDefinitionsFirstReturnTimerEventDefinition() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("BoundaryTimerEvent");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    ArrayNode arrayNode3 = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode3.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode3.size()).thenReturn(3);
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode4);
    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode5);

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode5).iterator();
    verify(arrayNode3, atLeast(1)).iterator();
    verify(arrayNode4, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode3).size();
    verify(arrayNode2).asText();
    verify(arrayNode, atLeast(1)).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    List<EventDefinition> eventDefinitions = ((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions();
    assertEquals(1, eventDefinitions.size());
    EventDefinition getResult = eventDefinitions.get(0);
    assertTrue(getResult instanceof TimerEventDefinition);
    assertEquals("BoundaryTimerEvent", ((TimerEventDefinition) getResult).getEndDate());
    assertEquals("BoundaryTimerEvent", ((TimerEventDefinition) getResult).getTimeDate());
    assertNull(((TimerEventDefinition) getResult).getCalendarName());
    assertNull(((TimerEventDefinition) getResult).getTimeCycle());
    assertNull(((TimerEventDefinition) getResult).getTimeDuration());
  }

  /**
   * Test {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>Then return AttachedToRefId is {@code As Text}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); then return AttachedToRefId is 'As Text'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement BoundaryEventJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
  void testConvertJsonToElement_thenReturnAttachedToRefIdIsAsText() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);
    when(arrayNode2.asText()).thenReturn("");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode2);
    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode3);
    Iterator<JsonNode> iteratorResult = jsonNodeList.iterator();
    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode4.asText()).thenReturn("As Text");
    when(arrayNode4.iterator()).thenReturn(iteratorResult);
    when(arrayNode4.size()).thenReturn(3);
    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.get(Mockito.<String>any())).thenReturn(arrayNode4);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode5);
    ArrayNode arrayNode6 = mock(ArrayNode.class);
    when(arrayNode6.iterator()).thenReturn(jsonNodeList2.iterator());
    ArrayNode modelNode = mock(ArrayNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(arrayNode6);

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(arrayNode6).iterator();
    verify(arrayNode4).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode5, atLeast(1)).get(Mockito.<String>any());
    verify(modelNode).get(eq("childShapes"));
    verify(arrayNode2, atLeast(1)).get(eq("id"));
    verify(arrayNode4).get(eq("overrideid"));
    verify(arrayNode3).get(eq("resourceId"));
    verify(arrayNode4).size();
    verify(arrayNode2).asText();
    verify(arrayNode).asText();
    verify(arrayNode4).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    assertEquals("As Text", ((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BoundaryEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement BoundaryEventJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
  void testConvertJsonToElement_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    BoundaryEventJsonConverter boundaryEventJsonConverter = new BoundaryEventJsonConverter();
    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    when(arrayNode.asText()).thenReturn("As Text");
    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ArrayNode modelNode = new ArrayNode(JsonNodeFactory.withExactBigDecimals(true));

    // Act
    FlowElement actualConvertJsonToElementResult = boundaryEventJsonConverter.convertJsonToElement(elementNode,
        modelNode, new HashMap<>());

    // Assert
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode, atLeast(1)).get(eq("id"));
    verify(arrayNode).asText();
    assertTrue(actualConvertJsonToElementResult instanceof BoundaryEvent);
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRefId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(((BoundaryEvent) actualConvertJsonToElementResult).getAttachedToRef());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((BoundaryEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isCancelActivity());
    assertTrue(((BoundaryEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test new {@link BoundaryEventJsonConverter} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link BoundaryEventJsonConverter}
   */
  @Test
  @DisplayName("Test new BoundaryEventJsonConverter (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BoundaryEventJsonConverter.<init>()"})
  void testNewBoundaryEventJsonConverter() {
    // Arrange and Act
    BoundaryEventJsonConverter actualBoundaryEventJsonConverter = new BoundaryEventJsonConverter();

    // Assert
    assertNull(actualBoundaryEventJsonConverter.shapesArrayNode);
    assertNull(actualBoundaryEventJsonConverter.flowElementNode);
    assertNull(actualBoundaryEventJsonConverter.model);
    assertNull(actualBoundaryEventJsonConverter.processor);
    assertEquals(0.0d, actualBoundaryEventJsonConverter.subProcessX);
    assertEquals(0.0d, actualBoundaryEventJsonConverter.subProcessY);
  }
}
