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
import com.fasterxml.jackson.databind.node.MissingNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.CancelEventDefinition;
import org.activiti.bpmn.model.EventDefinition;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.IntermediateCatchEvent;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.SignalEventDefinition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CatchEventJsonConverterDiffblueTest {
  /**
   * Test {@link CatchEventJsonConverter#fillJsonTypes(Map)}.
   * <p>
   * Method under test: {@link CatchEventJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void CatchEventJsonConverter.fillJsonTypes(Map)"})
  void testFillJsonTypes() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();

    // Act
    CatchEventJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(3, convertersToBpmnMap.size());
    Class<CatchEventJsonConverter> expectedGetResult = CatchEventJsonConverter.class;
    Class<? extends BaseBpmnJsonConverter> getResult = convertersToBpmnMap.get("CatchMessageEvent");
    assertEquals(expectedGetResult, getResult);
    assertSame(getResult, convertersToBpmnMap.get("CatchSignalEvent"));
    assertSame(getResult, convertersToBpmnMap.get("CatchTimerEvent"));
  }

  /**
   * Test {@link CatchEventJsonConverter#getStencilId(BaseElement)}.
   * <ul>
   *   <li>Then return {@code CatchMessageEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CatchEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement); then return 'CatchMessageEvent'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String CatchEventJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId_thenReturnCatchMessageEvent() {
    // Arrange
    CatchEventJsonConverter catchEventJsonConverter = new CatchEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new MessageEventDefinition());

    IntermediateCatchEvent baseElement = new IntermediateCatchEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("CatchMessageEvent", catchEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Test {@link CatchEventJsonConverter#getStencilId(BaseElement)}.
   * <ul>
   *   <li>Then return {@code CatchSignalEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CatchEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement); then return 'CatchSignalEvent'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String CatchEventJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId_thenReturnCatchSignalEvent() {
    // Arrange
    CatchEventJsonConverter catchEventJsonConverter = new CatchEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new SignalEventDefinition());

    IntermediateCatchEvent baseElement = new IntermediateCatchEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("CatchSignalEvent", catchEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Test {@link CatchEventJsonConverter#getStencilId(BaseElement)}.
   * <ul>
   *   <li>Then return {@code CatchTimerEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CatchEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement); then return 'CatchTimerEvent'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String CatchEventJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId_thenReturnCatchTimerEvent() {
    // Arrange
    CatchEventJsonConverter catchEventJsonConverter = new CatchEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());

    IntermediateCatchEvent baseElement = new IntermediateCatchEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("CatchTimerEvent", catchEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Test {@link CatchEventJsonConverter#getStencilId(BaseElement)}.
   * <ul>
   *   <li>Then return {@code CatchTimerEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CatchEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement); then return 'CatchTimerEvent'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String CatchEventJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId_thenReturnCatchTimerEvent2() {
    // Arrange
    CatchEventJsonConverter catchEventJsonConverter = new CatchEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());
    eventDefinitions.add(new CancelEventDefinition());

    IntermediateCatchEvent baseElement = new IntermediateCatchEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("CatchTimerEvent", catchEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Test {@link CatchEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   *   <li>Then return {@link IntermediateCatchEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CatchEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); when HashMap(); then return IntermediateCatchEvent")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement CatchEventJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
  void testConvertJsonToElement_whenHashMap_thenReturnIntermediateCatchEvent() {
    // Arrange
    CatchEventJsonConverter catchEventJsonConverter = new CatchEventJsonConverter();
    MissingNode elementNode = MissingNode.getInstance();
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = catchEventJsonConverter.convertJsonToElement(elementNode, modelNode,
        new HashMap<>());

    // Assert
    assertTrue(actualConvertJsonToElementResult instanceof IntermediateCatchEvent);
    assertNull(((IntermediateCatchEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((IntermediateCatchEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((IntermediateCatchEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((IntermediateCatchEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((IntermediateCatchEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((IntermediateCatchEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((IntermediateCatchEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test new {@link CatchEventJsonConverter} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link CatchEventJsonConverter}
   */
  @Test
  @DisplayName("Test new CatchEventJsonConverter (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void CatchEventJsonConverter.<init>()"})
  void testNewCatchEventJsonConverter() {
    // Arrange and Act
    CatchEventJsonConverter actualCatchEventJsonConverter = new CatchEventJsonConverter();

    // Assert
    assertNull(actualCatchEventJsonConverter.shapesArrayNode);
    assertNull(actualCatchEventJsonConverter.flowElementNode);
    assertNull(actualCatchEventJsonConverter.model);
    assertNull(actualCatchEventJsonConverter.processor);
    assertEquals(0.0d, actualCatchEventJsonConverter.subProcessX);
    assertEquals(0.0d, actualCatchEventJsonConverter.subProcessY);
  }
}
