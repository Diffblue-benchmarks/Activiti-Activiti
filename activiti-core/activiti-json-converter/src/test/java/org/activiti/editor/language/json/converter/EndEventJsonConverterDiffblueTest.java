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
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.ErrorEventDefinition;
import org.activiti.bpmn.model.EventDefinition;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.TerminateEventDefinition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class EndEventJsonConverterDiffblueTest {
  /**
   * Test {@link EndEventJsonConverter#fillJsonTypes(Map)}.
   * <p>
   * Method under test: {@link EndEventJsonConverter#fillJsonTypes(Map)}
   */
  @Test
  @DisplayName("Test fillJsonTypes(Map)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void EndEventJsonConverter.fillJsonTypes(Map)"})
  void testFillJsonTypes() {
    // Arrange
    HashMap<String, Class<? extends BaseBpmnJsonConverter>> convertersToBpmnMap = new HashMap<>();

    // Act
    EndEventJsonConverter.fillJsonTypes(convertersToBpmnMap);

    // Assert
    assertEquals(4, convertersToBpmnMap.size());
    Class<EndEventJsonConverter> expectedGetResult = EndEventJsonConverter.class;
    Class<? extends BaseBpmnJsonConverter> getResult = convertersToBpmnMap.get("EndCancelEvent");
    assertEquals(expectedGetResult, getResult);
    assertSame(getResult, convertersToBpmnMap.get("EndErrorEvent"));
    assertSame(getResult, convertersToBpmnMap.get("EndNoneEvent"));
    assertSame(getResult, convertersToBpmnMap.get("EndTerminateEvent"));
  }

  /**
   * Test {@link EndEventJsonConverter#getStencilId(BaseElement)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link CancelEventDefinition} (default constructor).</li>
   *   <li>Then return {@code EndCancelEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EndEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement); given ArrayList() add CancelEventDefinition (default constructor); then return 'EndCancelEvent'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String EndEventJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId_givenArrayListAddCancelEventDefinition_thenReturnEndCancelEvent() {
    // Arrange
    EndEventJsonConverter endEventJsonConverter = new EndEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());

    EndEvent baseElement = new EndEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("EndCancelEvent", endEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Test {@link EndEventJsonConverter#getStencilId(BaseElement)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link CancelEventDefinition} (default constructor).</li>
   *   <li>Then return {@code EndNoneEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EndEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement); given ArrayList() add CancelEventDefinition (default constructor); then return 'EndNoneEvent'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String EndEventJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId_givenArrayListAddCancelEventDefinition_thenReturnEndNoneEvent() {
    // Arrange
    EndEventJsonConverter endEventJsonConverter = new EndEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());
    eventDefinitions.add(new CancelEventDefinition());

    EndEvent baseElement = new EndEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("EndNoneEvent", endEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Test {@link EndEventJsonConverter#getStencilId(BaseElement)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ErrorEventDefinition} (default constructor).</li>
   *   <li>Then return {@code EndErrorEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EndEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement); given ArrayList() add ErrorEventDefinition (default constructor); then return 'EndErrorEvent'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String EndEventJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId_givenArrayListAddErrorEventDefinition_thenReturnEndErrorEvent() {
    // Arrange
    EndEventJsonConverter endEventJsonConverter = new EndEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new ErrorEventDefinition());

    EndEvent baseElement = new EndEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("EndErrorEvent", endEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Test {@link EndEventJsonConverter#getStencilId(BaseElement)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code null}.</li>
   *   <li>Then return {@code EndNoneEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EndEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement); given ArrayList() add 'null'; then return 'EndNoneEvent'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String EndEventJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId_givenArrayListAddNull_thenReturnEndNoneEvent() {
    // Arrange
    EndEventJsonConverter endEventJsonConverter = new EndEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(null);

    EndEvent baseElement = new EndEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("EndNoneEvent", endEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Test {@link EndEventJsonConverter#getStencilId(BaseElement)}.
   * <ul>
   *   <li>Then return {@code EndTerminateEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EndEventJsonConverter#getStencilId(BaseElement)}
   */
  @Test
  @DisplayName("Test getStencilId(BaseElement); then return 'EndTerminateEvent'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String EndEventJsonConverter.getStencilId(BaseElement)"})
  void testGetStencilId_thenReturnEndTerminateEvent() {
    // Arrange
    EndEventJsonConverter endEventJsonConverter = new EndEventJsonConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new TerminateEventDefinition());

    EndEvent baseElement = new EndEvent();
    baseElement.setEventDefinitions(eventDefinitions);

    // Act and Assert
    assertEquals("EndTerminateEvent", endEventJsonConverter.getStencilId(baseElement));
  }

  /**
   * Test {@link EndEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   *   <li>Then return {@link EndEvent}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EndEventJsonConverter#convertJsonToElement(JsonNode, JsonNode, Map)}
   */
  @Test
  @DisplayName("Test convertJsonToElement(JsonNode, JsonNode, Map); when HashMap(); then return EndEvent")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"FlowElement EndEventJsonConverter.convertJsonToElement(JsonNode, JsonNode, Map)"})
  void testConvertJsonToElement_whenHashMap_thenReturnEndEvent() {
    // Arrange
    EndEventJsonConverter endEventJsonConverter = new EndEventJsonConverter();
    MissingNode elementNode = MissingNode.getInstance();
    MissingNode modelNode = MissingNode.getInstance();

    // Act
    FlowElement actualConvertJsonToElementResult = endEventJsonConverter.convertJsonToElement(elementNode, modelNode,
        new HashMap<>());

    // Assert
    assertTrue(actualConvertJsonToElementResult instanceof EndEvent);
    assertNull(((EndEvent) actualConvertJsonToElementResult).getBehavior());
    assertNull(actualConvertJsonToElementResult.getId());
    assertNull(actualConvertJsonToElementResult.getDocumentation());
    assertNull(actualConvertJsonToElementResult.getName());
    assertNull(actualConvertJsonToElementResult.getParentContainer());
    assertNull(actualConvertJsonToElementResult.getSubProcess());
    assertEquals(0, actualConvertJsonToElementResult.getXmlColumnNumber());
    assertEquals(0, actualConvertJsonToElementResult.getXmlRowNumber());
    assertFalse(((EndEvent) actualConvertJsonToElementResult).isAsynchronous());
    assertFalse(((EndEvent) actualConvertJsonToElementResult).isNotExclusive());
    assertTrue(((EndEvent) actualConvertJsonToElementResult).getEventDefinitions().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExecutionListeners().isEmpty());
    assertTrue(((EndEvent) actualConvertJsonToElementResult).getIncomingFlows().isEmpty());
    assertTrue(((EndEvent) actualConvertJsonToElementResult).getOutgoingFlows().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getAttributes().isEmpty());
    assertTrue(actualConvertJsonToElementResult.getExtensionElements().isEmpty());
    assertTrue(((EndEvent) actualConvertJsonToElementResult).isExclusive());
  }

  /**
   * Test new {@link EndEventJsonConverter} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link EndEventJsonConverter}
   */
  @Test
  @DisplayName("Test new EndEventJsonConverter (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void EndEventJsonConverter.<init>()"})
  void testNewEndEventJsonConverter() {
    // Arrange and Act
    EndEventJsonConverter actualEndEventJsonConverter = new EndEventJsonConverter();

    // Assert
    assertNull(actualEndEventJsonConverter.shapesArrayNode);
    assertNull(actualEndEventJsonConverter.flowElementNode);
    assertNull(actualEndEventJsonConverter.model);
    assertNull(actualEndEventJsonConverter.processor);
    assertEquals(0.0d, actualEndEventJsonConverter.subProcessX);
    assertEquals(0.0d, actualEndEventJsonConverter.subProcessY);
  }
}
