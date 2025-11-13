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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BigIntegerNode;
import com.fasterxml.jackson.databind.node.BinaryNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.Association;
import org.activiti.bpmn.model.AssociationDirection;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.ErrorEventDefinition;
import org.activiti.bpmn.model.Event;
import org.activiti.bpmn.model.EventDefinition;
import org.activiti.bpmn.model.FieldExtension;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowElementsContainer;
import org.activiti.bpmn.model.FormProperty;
import org.activiti.bpmn.model.FormValue;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.Resource;
import org.activiti.bpmn.model.ServiceTask;
import org.activiti.bpmn.model.Signal;
import org.activiti.bpmn.model.SignalEventDefinition;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.TimerEventDefinition;
import org.activiti.bpmn.model.UserTask;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BaseBpmnJsonConverterDiffblueTest {
  /**
   * Test {@link BaseBpmnJsonConverter#processDataStoreReferences(FlowElementsContainer, String,
   * ArrayNode)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.
   *   <li>Then calls {@link AdhocSubProcess#getFlowElements()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * BaseBpmnJsonConverter#processDataStoreReferences(FlowElementsContainer, String, ArrayNode)}
   */
  @Test
  @DisplayName(
      "Test processDataStoreReferences(FlowElementsContainer, String, ArrayNode); given ArrayList(); then calls getFlowElements()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.processDataStoreReferences(FlowElementsContainer, String, ArrayNode)"
  })
  void testProcessDataStoreReferences_givenArrayList_thenCallsGetFlowElements() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    AdhocSubProcess container = mock(AdhocSubProcess.class);
    when(container.getFlowElements()).thenReturn(new ArrayList<>());
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);

    // Act
    associationJsonConverter.processDataStoreReferences(container, "42", new ArrayNode(nf));

    // Assert
    verify(container).getFlowElements();
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor,
   * BaseElement, Map, BpmnModel)}.
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode,
   * ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)"
  })
  void testConvertToBpmnModel() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("not empty");
    when(jsonNode.isNull()).thenReturn(false);

    JsonNode jsonNode2 = mock(JsonNode.class);
    when(jsonNode2.asText()).thenReturn("As Text");
    when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(jsonNode2);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    jsonNodeList.add(new ArrayNode(nf));

    JsonNode jsonNode3 = mock(JsonNode.class);
    when(jsonNode3.iterator()).thenReturn(jsonNodeList.iterator());

    JsonNode modelNode = mock(JsonNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(jsonNode3);
    BpmnJsonConverter processor = new BpmnJsonConverter();

    AdhocSubProcess parentElement = mock(AdhocSubProcess.class);
    doNothing().when(parentElement).addArtifact(Mockito.<Artifact>any());
    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    associationJsonConverter.convertToBpmnModel(
        elementNode, modelNode, processor, parentElement, shapeMap, bpmnModel);

    // Assert that nothing has changed
    verify(jsonNode2).asText();
    verify(jsonNode, atLeast(1)).asText();
    verify(modelNode).get("childShapes");
    verify(jsonNode2, atLeast(1)).get("overrideid");
    verify(jsonNode, atLeast(1)).isNull();
    verify(jsonNode3).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(parentElement).addArtifact(isA(Artifact.class));
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor,
   * BaseElement, Map, BpmnModel)}.
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode,
   * ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)"
  })
  void testConvertToBpmnModel2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("not empty");
    when(jsonNode.isNull()).thenReturn(false);

    JsonNode jsonNode2 = mock(JsonNode.class);
    when(jsonNode2.asText()).thenReturn("As Text");
    when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(jsonNode2);

    ArrayNode arrayNode = mock(ArrayNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);

    JsonNode jsonNode3 = mock(JsonNode.class);
    when(jsonNode3.iterator()).thenReturn(jsonNodeList.iterator());

    JsonNode modelNode = mock(JsonNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(jsonNode3);
    BpmnJsonConverter processor = new BpmnJsonConverter();

    AdhocSubProcess parentElement = mock(AdhocSubProcess.class);
    doNothing().when(parentElement).addArtifact(Mockito.<Artifact>any());
    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    associationJsonConverter.convertToBpmnModel(
        elementNode, modelNode, processor, parentElement, shapeMap, bpmnModel);

    // Assert that nothing has changed
    verify(jsonNode2).asText();
    verify(jsonNode, atLeast(1)).asText();
    verify(modelNode).get("childShapes");
    verify(jsonNode2, atLeast(1)).get("overrideid");
    verify(jsonNode, atLeast(1)).isNull();
    verify(jsonNode3).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(parentElement).addArtifact(isA(Artifact.class));
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor,
   * BaseElement, Map, BpmnModel)}.
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode,
   * ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)"
  })
  void testConvertToBpmnModel3() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("not empty");
    when(jsonNode.isNull()).thenReturn(false);

    JsonNode jsonNode2 = mock(JsonNode.class);
    when(jsonNode2.asText()).thenReturn("As Text");
    when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(jsonNode2);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    jsonNodeList.add(new ArrayNode(nf));

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.size()).thenReturn(3);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode2);

    JsonNode jsonNode3 = mock(JsonNode.class);
    when(jsonNode3.iterator()).thenReturn(jsonNodeList2.iterator());

    JsonNode modelNode = mock(JsonNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(jsonNode3);
    BpmnJsonConverter processor = new BpmnJsonConverter();

    AdhocSubProcess parentElement = mock(AdhocSubProcess.class);
    doNothing().when(parentElement).addArtifact(Mockito.<Artifact>any());
    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    associationJsonConverter.convertToBpmnModel(
        elementNode, modelNode, processor, parentElement, shapeMap, bpmnModel);

    // Assert that nothing has changed
    verify(jsonNode2).asText();
    verify(jsonNode, atLeast(1)).asText();
    verify(modelNode).get("childShapes");
    verify(jsonNode2, atLeast(1)).get("overrideid");
    verify(jsonNode, atLeast(1)).isNull();
    verify(jsonNode3).iterator();
    verify(arrayNode, atLeast(1)).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode).size();
    verify(parentElement).addArtifact(isA(Artifact.class));
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor,
   * BaseElement, Map, BpmnModel)}.
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode,
   * ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)"
  })
  void testConvertToBpmnModel4() throws UnsupportedEncodingException {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("not empty");
    when(jsonNode.isNull()).thenReturn(false);

    JsonNode jsonNode2 = mock(JsonNode.class);
    when(jsonNode2.asText()).thenReturn("As Text");
    when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(jsonNode2);

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any()))
        .thenReturn(new BinaryNode("AXAXAXAX".getBytes("UTF-8")));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode2.size()).thenReturn(3);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode3);

    JsonNode jsonNode3 = mock(JsonNode.class);
    when(jsonNode3.iterator()).thenReturn(jsonNodeList2.iterator());

    JsonNode modelNode = mock(JsonNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(jsonNode3);
    BpmnJsonConverter processor = new BpmnJsonConverter();

    AdhocSubProcess parentElement = mock(AdhocSubProcess.class);
    doNothing().when(parentElement).addArtifact(Mockito.<Artifact>any());
    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    associationJsonConverter.convertToBpmnModel(
        elementNode, modelNode, processor, parentElement, shapeMap, bpmnModel);

    // Assert that nothing has changed
    verify(jsonNode2).asText();
    verify(jsonNode, atLeast(1)).asText();
    verify(modelNode).get("childShapes");
    verify(jsonNode2, atLeast(1)).get("overrideid");
    verify(jsonNode, atLeast(1)).isNull();
    verify(jsonNode3).iterator();
    verify(arrayNode2, atLeast(1)).iterator();
    verify(arrayNode3, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode).get("resourceId");
    verify(arrayNode2).size();
    verify(parentElement).addArtifact(isA(Artifact.class));
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor,
   * BaseElement, Map, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add valueOf ten.
   *   <li>Then calls {@link JsonNode#iterator()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode,
   * ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); given ArrayList() add valueOf ten; then calls iterator()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)"
  })
  void testConvertToBpmnModel_givenArrayListAddValueOfTen_thenCallsIterator() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("not empty");
    when(jsonNode.isNull()).thenReturn(false);

    JsonNode jsonNode2 = mock(JsonNode.class);
    when(jsonNode2.asText()).thenReturn("As Text");
    when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(jsonNode2);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(DoubleNode.valueOf(10.0d));

    JsonNode jsonNode3 = mock(JsonNode.class);
    when(jsonNode3.iterator()).thenReturn(jsonNodeList.iterator());

    JsonNode modelNode = mock(JsonNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(jsonNode3);
    BpmnJsonConverter processor = new BpmnJsonConverter();

    AdhocSubProcess parentElement = mock(AdhocSubProcess.class);
    doNothing().when(parentElement).addArtifact(Mockito.<Artifact>any());
    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    associationJsonConverter.convertToBpmnModel(
        elementNode, modelNode, processor, parentElement, shapeMap, bpmnModel);

    // Assert that nothing has changed
    verify(jsonNode2).asText();
    verify(jsonNode, atLeast(1)).asText();
    verify(modelNode).get("childShapes");
    verify(jsonNode2, atLeast(1)).get("overrideid");
    verify(jsonNode, atLeast(1)).isNull();
    verify(jsonNode3).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(parentElement).addArtifact(isA(Artifact.class));
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor,
   * BaseElement, Map, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add valueOf ten.
   *   <li>Then calls {@link ArrayNode#iterator()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode,
   * ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); given ArrayList() add valueOf ten; then calls iterator()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)"
  })
  void testConvertToBpmnModel_givenArrayListAddValueOfTen_thenCallsIterator2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("not empty");
    when(jsonNode.isNull()).thenReturn(false);

    JsonNode jsonNode2 = mock(JsonNode.class);
    when(jsonNode2.asText()).thenReturn("As Text");
    when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(jsonNode2);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(DoubleNode.valueOf(10.0d));

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.size()).thenReturn(3);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode2);

    JsonNode jsonNode3 = mock(JsonNode.class);
    when(jsonNode3.iterator()).thenReturn(jsonNodeList2.iterator());

    JsonNode modelNode = mock(JsonNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(jsonNode3);
    BpmnJsonConverter processor = new BpmnJsonConverter();

    AdhocSubProcess parentElement = mock(AdhocSubProcess.class);
    doNothing().when(parentElement).addArtifact(Mockito.<Artifact>any());
    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    associationJsonConverter.convertToBpmnModel(
        elementNode, modelNode, processor, parentElement, shapeMap, bpmnModel);

    // Assert that nothing has changed
    verify(jsonNode2).asText();
    verify(jsonNode, atLeast(1)).asText();
    verify(modelNode).get("childShapes");
    verify(jsonNode2, atLeast(1)).get("overrideid");
    verify(jsonNode, atLeast(1)).isNull();
    verify(jsonNode3).iterator();
    verify(arrayNode, atLeast(1)).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode).size();
    verify(parentElement).addArtifact(isA(Artifact.class));
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor,
   * BaseElement, Map, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return valueOf ten.
   *   <li>Then calls {@link JsonNode#iterator()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode,
   * ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); given ArrayNode get(String) return valueOf ten; then calls iterator()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)"
  })
  void testConvertToBpmnModel_givenArrayNodeGetReturnValueOfTen_thenCallsIterator() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("not empty");
    when(jsonNode.isNull()).thenReturn(false);

    JsonNode jsonNode2 = mock(JsonNode.class);
    when(jsonNode2.asText()).thenReturn("As Text");
    when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(jsonNode2);

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);

    JsonNode jsonNode3 = mock(JsonNode.class);
    when(jsonNode3.iterator()).thenReturn(jsonNodeList.iterator());

    JsonNode modelNode = mock(JsonNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(jsonNode3);
    BpmnJsonConverter processor = new BpmnJsonConverter();

    AdhocSubProcess parentElement = mock(AdhocSubProcess.class);
    doNothing().when(parentElement).addArtifact(Mockito.<Artifact>any());
    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    associationJsonConverter.convertToBpmnModel(
        elementNode, modelNode, processor, parentElement, shapeMap, bpmnModel);

    // Assert that nothing has changed
    verify(jsonNode2).asText();
    verify(jsonNode, atLeast(1)).asText();
    verify(modelNode).get("childShapes");
    verify(jsonNode2, atLeast(1)).get("overrideid");
    verify(jsonNode, atLeast(1)).isNull();
    verify(jsonNode3).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(parentElement).addArtifact(isA(Artifact.class));
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor,
   * BaseElement, Map, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return valueOf ten.
   *   <li>Then calls {@link ArrayNode#iterator()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode,
   * ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); given ArrayNode get(String) return valueOf ten; then calls iterator()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)"
  })
  void testConvertToBpmnModel_givenArrayNodeGetReturnValueOfTen_thenCallsIterator2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("not empty");
    when(jsonNode.isNull()).thenReturn(false);

    JsonNode jsonNode2 = mock(JsonNode.class);
    when(jsonNode2.asText()).thenReturn("As Text");
    when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(jsonNode2);

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode2.size()).thenReturn(3);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode3);

    JsonNode jsonNode3 = mock(JsonNode.class);
    when(jsonNode3.iterator()).thenReturn(jsonNodeList2.iterator());

    JsonNode modelNode = mock(JsonNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(jsonNode3);
    BpmnJsonConverter processor = new BpmnJsonConverter();

    AdhocSubProcess parentElement = mock(AdhocSubProcess.class);
    doNothing().when(parentElement).addArtifact(Mockito.<Artifact>any());
    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    associationJsonConverter.convertToBpmnModel(
        elementNode, modelNode, processor, parentElement, shapeMap, bpmnModel);

    // Assert that nothing has changed
    verify(jsonNode2).asText();
    verify(jsonNode, atLeast(1)).asText();
    verify(modelNode).get("childShapes");
    verify(jsonNode2, atLeast(1)).get("overrideid");
    verify(jsonNode, atLeast(1)).isNull();
    verify(jsonNode3).iterator();
    verify(arrayNode2, atLeast(1)).iterator();
    verify(arrayNode3, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode).get("resourceId");
    verify(arrayNode2).size();
    verify(parentElement).addArtifact(isA(Artifact.class));
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor,
   * BaseElement, Map, BpmnModel)}.
   *
   * <ul>
   *   <li>Given Instance.
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode,
   * ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); given Instance; when ArrayNode get(String) return Instance")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)"
  })
  void testConvertToBpmnModel_givenInstance_whenArrayNodeGetReturnInstance() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);
    BpmnJsonConverter processor = new BpmnJsonConverter();
    ActivitiListener parentElement = new ActivitiListener();
    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    associationJsonConverter.convertToBpmnModel(
        elementNode, modelNode, processor, parentElement, shapeMap, bpmnModel);

    // Assert that nothing has changed
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor,
   * BaseElement, Map, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link JsonNode} {@link JsonNode#get(String)} return Instance.
   *   <li>When {@link ActivitiListener} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode,
   * ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); given JsonNode get(String) return Instance; when ActivitiListener (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)"
  })
  void testConvertToBpmnModel_givenJsonNodeGetReturnInstance_whenActivitiListener() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("As Text");
    when(jsonNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(jsonNode);
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);
    BpmnJsonConverter processor = new BpmnJsonConverter();
    ActivitiListener parentElement = new ActivitiListener();
    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    associationJsonConverter.convertToBpmnModel(
        elementNode, modelNode, processor, parentElement, shapeMap, bpmnModel);

    // Assert that nothing has changed
    verify(jsonNode, atLeast(1)).asText();
    verify(jsonNode).get("overrideid");
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor,
   * BaseElement, Map, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link JsonNode} {@link JsonNode#get(String)} return Instance.
   *   <li>When {@link ActivitiListener} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode,
   * ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); given JsonNode get(String) return Instance; when ActivitiListener (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)"
  })
  void testConvertToBpmnModel_givenJsonNodeGetReturnInstance_whenActivitiListener2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("As Text");
    when(jsonNode.get(Mockito.<String>any())).thenReturn(NullNode.getInstance());

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(jsonNode);
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);
    BpmnJsonConverter processor = new BpmnJsonConverter();
    ActivitiListener parentElement = new ActivitiListener();
    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    associationJsonConverter.convertToBpmnModel(
        elementNode, modelNode, processor, parentElement, shapeMap, bpmnModel);

    // Assert that nothing has changed
    verify(jsonNode, atLeast(1)).asText();
    verify(jsonNode).get("overrideid");
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor,
   * BaseElement, Map, BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link JsonNode} {@link JsonNode#get(String)} return valueOf ten.
   *   <li>When {@link ActivitiListener} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode,
   * ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); given JsonNode get(String) return valueOf ten; when ActivitiListener (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)"
  })
  void testConvertToBpmnModel_givenJsonNodeGetReturnValueOfTen_whenActivitiListener() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("As Text");
    when(jsonNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(jsonNode);
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);
    BpmnJsonConverter processor = new BpmnJsonConverter();
    ActivitiListener parentElement = new ActivitiListener();
    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    associationJsonConverter.convertToBpmnModel(
        elementNode, modelNode, processor, parentElement, shapeMap, bpmnModel);

    // Assert that nothing has changed
    verify(jsonNode).asText();
    verify(jsonNode, atLeast(1)).get("overrideid");
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor,
   * BaseElement, Map, BpmnModel)}.
   *
   * <ul>
   *   <li>Given valueOf ten.
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode,
   * ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); given valueOf ten; when ArrayNode get(String) return valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)"
  })
  void testConvertToBpmnModel_givenValueOfTen_whenArrayNodeGetReturnValueOfTen() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);
    BpmnJsonConverter processor = new BpmnJsonConverter();
    ActivitiListener parentElement = new ActivitiListener();
    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    associationJsonConverter.convertToBpmnModel(
        elementNode, modelNode, processor, parentElement, shapeMap, bpmnModel);

    // Assert that nothing has changed
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor,
   * BaseElement, Map, BpmnModel)}.
   *
   * <ul>
   *   <li>Given valueOf ten.
   *   <li>When {@link JsonNode} {@link JsonNode#get(String)} return valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode,
   * ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); given valueOf ten; when JsonNode get(String) return valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)"
  })
  void testConvertToBpmnModel_givenValueOfTen_whenJsonNodeGetReturnValueOfTen() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("not empty");
    when(jsonNode.isNull()).thenReturn(false);

    JsonNode jsonNode2 = mock(JsonNode.class);
    when(jsonNode2.asText()).thenReturn("As Text");
    when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(jsonNode2);

    JsonNode modelNode = mock(JsonNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));
    BpmnJsonConverter processor = new BpmnJsonConverter();

    AdhocSubProcess parentElement = mock(AdhocSubProcess.class);
    doNothing().when(parentElement).addArtifact(Mockito.<Artifact>any());
    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    associationJsonConverter.convertToBpmnModel(
        elementNode, modelNode, processor, parentElement, shapeMap, bpmnModel);

    // Assert that nothing has changed
    verify(jsonNode2).asText();
    verify(jsonNode, atLeast(1)).asText();
    verify(modelNode).get("childShapes");
    verify(jsonNode2, atLeast(1)).get("overrideid");
    verify(jsonNode, atLeast(1)).isNull();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(parentElement).addArtifact(isA(Artifact.class));
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor,
   * BaseElement, Map, BpmnModel)}.
   *
   * <ul>
   *   <li>Then calls {@link JsonNode#iterator()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode,
   * ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); then calls iterator()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)"
  })
  void testConvertToBpmnModel_thenCallsIterator() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("not empty");
    when(jsonNode.isNull()).thenReturn(false);

    JsonNode jsonNode2 = mock(JsonNode.class);
    when(jsonNode2.asText()).thenReturn("As Text");
    when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(jsonNode2);

    JsonNode jsonNode3 = mock(JsonNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(jsonNode3.iterator()).thenReturn(jsonNodeList.iterator());

    JsonNode modelNode = mock(JsonNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(jsonNode3);
    BpmnJsonConverter processor = new BpmnJsonConverter();

    AdhocSubProcess parentElement = mock(AdhocSubProcess.class);
    doNothing().when(parentElement).addArtifact(Mockito.<Artifact>any());
    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    associationJsonConverter.convertToBpmnModel(
        elementNode, modelNode, processor, parentElement, shapeMap, bpmnModel);

    // Assert that nothing has changed
    verify(jsonNode2).asText();
    verify(jsonNode, atLeast(1)).asText();
    verify(modelNode).get("childShapes");
    verify(jsonNode2, atLeast(1)).get("overrideid");
    verify(jsonNode, atLeast(1)).isNull();
    verify(jsonNode3).iterator();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(parentElement).addArtifact(isA(Artifact.class));
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor,
   * BaseElement, Map, BpmnModel)}.
   *
   * <ul>
   *   <li>Then calls {@link ArrayNode#iterator()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode,
   * ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); then calls iterator()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)"
  })
  void testConvertToBpmnModel_thenCallsIterator2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("not empty");
    when(jsonNode.isNull()).thenReturn(false);

    JsonNode jsonNode2 = mock(JsonNode.class);
    when(jsonNode2.asText()).thenReturn("As Text");
    when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(jsonNode2);

    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());
    when(arrayNode.size()).thenReturn(3);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList2 = new ArrayList<>();
    jsonNodeList2.add(arrayNode2);

    JsonNode jsonNode3 = mock(JsonNode.class);
    when(jsonNode3.iterator()).thenReturn(jsonNodeList2.iterator());

    JsonNode modelNode = mock(JsonNode.class);
    when(modelNode.get(Mockito.<String>any())).thenReturn(jsonNode3);
    BpmnJsonConverter processor = new BpmnJsonConverter();

    AdhocSubProcess parentElement = mock(AdhocSubProcess.class);
    doNothing().when(parentElement).addArtifact(Mockito.<Artifact>any());
    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    associationJsonConverter.convertToBpmnModel(
        elementNode, modelNode, processor, parentElement, shapeMap, bpmnModel);

    // Assert that nothing has changed
    verify(jsonNode2).asText();
    verify(jsonNode, atLeast(1)).asText();
    verify(modelNode).get("childShapes");
    verify(jsonNode2, atLeast(1)).get("overrideid");
    verify(jsonNode, atLeast(1)).isNull();
    verify(jsonNode3).iterator();
    verify(arrayNode, atLeast(1)).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode).size();
    verify(parentElement).addArtifact(isA(Artifact.class));
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor,
   * BaseElement, Map, BpmnModel)}.
   *
   * <ul>
   *   <li>When {@link ActivitiListener} (default constructor).
   *   <li>Then calls {@link JsonNode#isNull()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode,
   * ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); when ActivitiListener (default constructor); then calls isNull()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)"
  })
  void testConvertToBpmnModel_whenActivitiListener_thenCallsIsNull() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("not empty");
    when(jsonNode.isNull()).thenReturn(false);

    JsonNode jsonNode2 = mock(JsonNode.class);
    when(jsonNode2.asText()).thenReturn("As Text");
    when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(jsonNode2);
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);
    BpmnJsonConverter processor = new BpmnJsonConverter();
    ActivitiListener parentElement = new ActivitiListener();
    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    associationJsonConverter.convertToBpmnModel(
        elementNode, modelNode, processor, parentElement, shapeMap, bpmnModel);

    // Assert that nothing has changed
    verify(jsonNode2).asText();
    verify(jsonNode, atLeast(1)).asText();
    verify(jsonNode2, atLeast(1)).get("overrideid");
    verify(jsonNode, atLeast(1)).isNull();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor,
   * BaseElement, Map, BpmnModel)}.
   *
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).
   *   <li>Then {@link AdhocSubProcess} (default constructor) Artifacts size is one.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode,
   * ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); when AdhocSubProcess (default constructor); then AdhocSubProcess (default constructor) Artifacts size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)"
  })
  void testConvertToBpmnModel_whenAdhocSubProcess_thenAdhocSubProcessArtifactsSizeIsOne() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("not empty");
    when(jsonNode.isNull()).thenReturn(false);

    JsonNode jsonNode2 = mock(JsonNode.class);
    when(jsonNode2.asText()).thenReturn("As Text");
    when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(jsonNode2);
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);
    BpmnJsonConverter processor = new BpmnJsonConverter();
    AdhocSubProcess parentElement = new AdhocSubProcess();
    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    associationJsonConverter.convertToBpmnModel(
        elementNode, modelNode, processor, parentElement, shapeMap, bpmnModel);

    // Assert
    verify(jsonNode2).asText();
    verify(jsonNode, atLeast(1)).asText();
    verify(jsonNode2, atLeast(1)).get("overrideid");
    verify(jsonNode, atLeast(1)).isNull();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    Collection<Artifact> artifacts = parentElement.getArtifacts();
    assertEquals(1, artifacts.size());
    assertTrue(artifacts instanceof List);
    Collection<FlowElement> flowElements = parentElement.getFlowElements();
    assertTrue(flowElements instanceof List);
    Artifact getResult = ((List<Artifact>) artifacts).get(0);
    assertTrue(getResult instanceof Association);
    assertEquals("not empty", getResult.getId());
    assertNull(((Association) getResult).getSourceRef());
    assertNull(((Association) getResult).getTargetRef());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertEquals(AssociationDirection.NONE, ((Association) getResult).getAssociationDirection());
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
    assertTrue(flowElements.isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor,
   * BaseElement, Map, BpmnModel)}.
   *
   * <ul>
   *   <li>When valueOf ten.
   *   <li>Then calls {@link AdhocSubProcess#addArtifact(Artifact)}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertToBpmnModel(JsonNode, JsonNode,
   * ActivityProcessor, BaseElement, Map, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel); when valueOf ten; then calls addArtifact(Artifact)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertToBpmnModel(JsonNode, JsonNode, ActivityProcessor, BaseElement, Map, BpmnModel)"
  })
  void testConvertToBpmnModel_whenValueOfTen_thenCallsAddArtifact() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("not empty");
    when(jsonNode.isNull()).thenReturn(false);

    JsonNode jsonNode2 = mock(JsonNode.class);
    when(jsonNode2.asText()).thenReturn("As Text");
    when(jsonNode2.get(Mockito.<String>any())).thenReturn(jsonNode);

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(jsonNode2);
    DoubleNode modelNode = DoubleNode.valueOf(10.0d);
    BpmnJsonConverter processor = new BpmnJsonConverter();

    AdhocSubProcess parentElement = mock(AdhocSubProcess.class);
    doNothing().when(parentElement).addArtifact(Mockito.<Artifact>any());
    HashMap<String, JsonNode> shapeMap = new HashMap<>();
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    associationJsonConverter.convertToBpmnModel(
        elementNode, modelNode, processor, parentElement, shapeMap, bpmnModel);

    // Assert that nothing has changed
    verify(jsonNode2).asText();
    verify(jsonNode, atLeast(1)).asText();
    verify(jsonNode2, atLeast(1)).get("overrideid");
    verify(jsonNode, atLeast(1)).isNull();
    verify(elementNode, atLeast(1)).get(Mockito.<String>any());
    verify(parentElement).addArtifact(isA(Artifact.class));
    Collection<Resource> resources = bpmnModel.getResources();
    assertTrue(resources instanceof List);
    Collection<Signal> signals = bpmnModel.getSignals();
    assertTrue(signals instanceof List);
    assertTrue(resources.isEmpty());
    assertTrue(signals.isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#setPropertyValue(String, String, ObjectNode)}.
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#setPropertyValue(String, String,
   * ObjectNode)}
   */
  @Test
  @DisplayName("Test setPropertyValue(String, String, ObjectNode)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.setPropertyValue(String, String, ObjectNode)"})
  void testSetPropertyValue() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode propertiesNode = new ObjectNode(nc);

    // Act
    associationJsonConverter.setPropertyValue("Name", "42", propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    assertTrue(iteratorResult.next() instanceof TextNode);
    assertEquals(1, propertiesNode.size());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#setPropertyValue(String, String, ObjectNode)}.
   *
   * <ul>
   *   <li>Then {@link ObjectNode#ObjectNode(JsonNodeFactory)} with nc is withExactBigDecimals
   *       {@code true} size is zero.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#setPropertyValue(String, String,
   * ObjectNode)}
   */
  @Test
  @DisplayName(
      "Test setPropertyValue(String, String, ObjectNode); then ObjectNode(JsonNodeFactory) with nc is withExactBigDecimals 'true' size is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.setPropertyValue(String, String, ObjectNode)"})
  void testSetPropertyValue_thenObjectNodeWithNcIsWithExactBigDecimalsTrueSizeIsZero() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode propertiesNode = new ObjectNode(nc);

    // Act
    associationJsonConverter.setPropertyValue("Name", null, propertiesNode);

    // Assert that nothing has changed
    assertEquals(0, propertiesNode.size());
    assertFalse(propertiesNode.iterator().hasNext());
    assertTrue(propertiesNode.isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#setPropertyValue(String, String, ObjectNode)}.
   *
   * <ul>
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#setPropertyValue(String, String,
   * ObjectNode)}
   */
  @Test
  @DisplayName("Test setPropertyValue(String, String, ObjectNode); when empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.setPropertyValue(String, String, ObjectNode)"})
  void testSetPropertyValue_whenEmptyString() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode propertiesNode = new ObjectNode(nc);

    // Act
    associationJsonConverter.setPropertyValue("Name", "", propertiesNode);

    // Assert that nothing has changed
    assertEquals(0, propertiesNode.size());
    assertFalse(propertiesNode.iterator().hasNext());
    assertTrue(propertiesNode.isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}.
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test addFormProperties(List, ObjectNode)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addFormProperties(List, ObjectNode)"})
  void testAddFormProperties() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    FormValue formValue = new FormValue();
    formValue.setName("Form Properties");
    formValue.setId("Form Properties");

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(formValue);

    FormProperty formProperty = new FormProperty();
    formProperty.setExpression("not empty");
    formProperty.setVariable("not empty");
    formProperty.setDatePattern("not empty");
    formProperty.setFormValues(formValues);
    formProperty.setId("Form Properties");
    formProperty.setName("Form Properties");
    formProperty.setType("Form Properties");

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode propertiesNode = new ObjectNode(nc);
    propertiesNode.put("formproperties", DoubleNode.valueOf(10.0d));

    // Act
    associationJsonConverter.addFormProperties(formProperties, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ArrayNode);
    Iterator<JsonNode> elementsResult = nextResult2.elements();
    JsonNode nextResult3 = elementsResult.next();
    assertTrue(nextResult3 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult3.iterator();
    assertTrue(iteratorResult3.next() instanceof TextNode);
    assertTrue(iteratorResult3.next() instanceof TextNode);
    assertEquals(10, nextResult3.size());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertTrue(iteratorResult3.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}.
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test addFormProperties(List, ObjectNode)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addFormProperties(List, ObjectNode)"})
  void testAddFormProperties2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    FormProperty formProperty = new FormProperty();
    formProperty.setExpression("not empty");
    formProperty.setVariable("not empty");
    formProperty.setDatePattern("not empty");
    formProperty.setFormValues(new ArrayList<>());
    formProperty.setId("Form Properties");
    formProperty.setName("Form Properties");
    formProperty.setType("Form Properties");

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode propertiesNode = new ObjectNode(nc);
    propertiesNode.put("formproperties", DoubleNode.valueOf(10.0d));

    // Act
    associationJsonConverter.addFormProperties(formProperties, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ArrayNode);
    Iterator<JsonNode> elementsResult = nextResult2.elements();
    JsonNode nextResult3 = elementsResult.next();
    assertTrue(nextResult3 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult3.iterator();
    assertTrue(iteratorResult3.next() instanceof TextNode);
    assertTrue(iteratorResult3.next() instanceof TextNode);
    assertEquals(9, nextResult3.size());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertTrue(iteratorResult3.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@link FormProperty} (default constructor) DatePattern is empty string.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}
   */
  @Test
  @DisplayName(
      "Test addFormProperties(List, ObjectNode); given FormProperty (default constructor) DatePattern is empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addFormProperties(List, ObjectNode)"})
  void testAddFormProperties_givenFormPropertyDatePatternIsEmptyString() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    FormValue formValue = new FormValue();
    formValue.setName("Form Properties");
    formValue.setId("Form Properties");

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(formValue);

    FormProperty formProperty = new FormProperty();
    formProperty.setExpression("not empty");
    formProperty.setVariable("not empty");
    formProperty.setDatePattern("");
    formProperty.setFormValues(formValues);
    formProperty.setId("Form Properties");
    formProperty.setName("Form Properties");
    formProperty.setType("Form Properties");

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode propertiesNode = new ObjectNode(nc);
    propertiesNode.put("formproperties", DoubleNode.valueOf(10.0d));

    // Act
    associationJsonConverter.addFormProperties(formProperties, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ArrayNode);
    Iterator<JsonNode> elementsResult = nextResult2.elements();
    JsonNode nextResult3 = elementsResult.next();
    assertTrue(nextResult3 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult3.iterator();
    assertTrue(iteratorResult3.next() instanceof TextNode);
    assertTrue(iteratorResult3.next() instanceof TextNode);
    assertEquals(9, nextResult3.size());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertTrue(iteratorResult3.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@link FormProperty} (default constructor) FormValues is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}
   */
  @Test
  @DisplayName(
      "Test addFormProperties(List, ObjectNode); given FormProperty (default constructor) FormValues is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addFormProperties(List, ObjectNode)"})
  void testAddFormProperties_givenFormPropertyFormValuesIsNull() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    FormProperty formProperty = new FormProperty();
    formProperty.setExpression("not empty");
    formProperty.setVariable("not empty");
    formProperty.setDatePattern("not empty");
    formProperty.setFormValues(null);
    formProperty.setId("Form Properties");
    formProperty.setName("Form Properties");
    formProperty.setType("Form Properties");

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode propertiesNode = new ObjectNode(nc);
    propertiesNode.put("formproperties", DoubleNode.valueOf(10.0d));

    // Act
    associationJsonConverter.addFormProperties(formProperties, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ArrayNode);
    Iterator<JsonNode> elementsResult = nextResult2.elements();
    JsonNode nextResult3 = elementsResult.next();
    assertTrue(nextResult3 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult3.iterator();
    assertTrue(iteratorResult3.next() instanceof TextNode);
    assertTrue(iteratorResult3.next() instanceof TextNode);
    assertEquals(9, nextResult3.size());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertTrue(iteratorResult3.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@link FormProperty} (default constructor) Type is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}
   */
  @Test
  @DisplayName(
      "Test addFormProperties(List, ObjectNode); given FormProperty (default constructor) Type is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addFormProperties(List, ObjectNode)"})
  void testAddFormProperties_givenFormPropertyTypeIsNull() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    FormValue formValue = new FormValue();
    formValue.setName("Form Properties");
    formValue.setId("Form Properties");

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(formValue);

    FormProperty formProperty = new FormProperty();
    formProperty.setExpression("not empty");
    formProperty.setVariable("not empty");
    formProperty.setDatePattern("not empty");
    formProperty.setFormValues(formValues);
    formProperty.setId("Form Properties");
    formProperty.setName("Form Properties");
    formProperty.setType(null);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode propertiesNode = new ObjectNode(nc);
    propertiesNode.put("formproperties", DoubleNode.valueOf(10.0d));

    // Act
    associationJsonConverter.addFormProperties(formProperties, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ArrayNode);
    Iterator<JsonNode> elementsResult = nextResult2.elements();
    JsonNode nextResult3 = elementsResult.next();
    assertTrue(nextResult3 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult3.iterator();
    assertTrue(iteratorResult3.next() instanceof TextNode);
    assertTrue(iteratorResult3.next() instanceof TextNode);
    assertEquals(10, nextResult3.size());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertTrue(iteratorResult3.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@link FormValue} (default constructor) Name is empty string.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}
   */
  @Test
  @DisplayName(
      "Test addFormProperties(List, ObjectNode); given FormValue (default constructor) Name is empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addFormProperties(List, ObjectNode)"})
  void testAddFormProperties_givenFormValueNameIsEmptyString() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    FormValue formValue = new FormValue();
    formValue.setName("");
    formValue.setId("Form Properties");

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(formValue);

    FormProperty formProperty = new FormProperty();
    formProperty.setExpression("not empty");
    formProperty.setVariable("not empty");
    formProperty.setDatePattern("not empty");
    formProperty.setFormValues(formValues);
    formProperty.setId("Form Properties");
    formProperty.setName("Form Properties");
    formProperty.setType("Form Properties");

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode propertiesNode = new ObjectNode(nc);
    propertiesNode.put("formproperties", DoubleNode.valueOf(10.0d));

    // Act
    associationJsonConverter.addFormProperties(formProperties, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ArrayNode);
    Iterator<JsonNode> elementsResult = nextResult2.elements();
    JsonNode nextResult3 = elementsResult.next();
    assertTrue(nextResult3 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult3.iterator();
    assertTrue(iteratorResult3.next() instanceof TextNode);
    assertTrue(iteratorResult3.next() instanceof TextNode);
    assertEquals(10, nextResult3.size());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertTrue(iteratorResult3.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}.
   *
   * <ul>
   *   <li>Then {@link ObjectNode#ObjectNode(JsonNodeFactory)} with nc is withExactBigDecimals
   *       {@code true} size is one.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}
   */
  @Test
  @DisplayName(
      "Test addFormProperties(List, ObjectNode); then ObjectNode(JsonNodeFactory) with nc is withExactBigDecimals 'true' size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addFormProperties(List, ObjectNode)"})
  void testAddFormProperties_thenObjectNodeWithNcIsWithExactBigDecimalsTrueSizeIsOne() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(new FormProperty());
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode propertiesNode = new ObjectNode(nc);

    // Act
    associationJsonConverter.addFormProperties(formProperties, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    assertTrue(iteratorResult.next() instanceof ObjectNode);
    assertEquals(1, propertiesNode.size());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}.
   *
   * <ul>
   *   <li>Then {@link ObjectNode#ObjectNode(JsonNodeFactory)} with nc is withExactBigDecimals
   *       {@code true} size is zero.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#addFormProperties(List, ObjectNode)}
   */
  @Test
  @DisplayName(
      "Test addFormProperties(List, ObjectNode); then ObjectNode(JsonNodeFactory) with nc is withExactBigDecimals 'true' size is zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addFormProperties(List, ObjectNode)"})
  void testAddFormProperties_thenObjectNodeWithNcIsWithExactBigDecimalsTrueSizeIsZero() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayList<FormProperty> formProperties = new ArrayList<>();
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode propertiesNode = new ObjectNode(nc);

    // Act
    associationJsonConverter.addFormProperties(formProperties, propertiesNode);

    // Assert that nothing has changed
    assertEquals(0, propertiesNode.size());
    assertFalse(propertiesNode.iterator().hasNext());
    assertTrue(propertiesNode.isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addFieldExtensions(List, ObjectNode)}.
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#addFieldExtensions(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test addFieldExtensions(List, ObjectNode)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addFieldExtensions(List, ObjectNode)"})
  void testAddFieldExtensions() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setStringValue("not empty");
    fieldExtension.setExpression("not empty");
    fieldExtension.setFieldName("Extensions");

    ArrayList<FieldExtension> extensions = new ArrayList<>();
    extensions.add(fieldExtension);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode propertiesNode = new ObjectNode(nc);
    propertiesNode.put("servicetaskfields", DoubleNode.valueOf(10.0d));

    // Act
    associationJsonConverter.addFieldExtensions(extensions, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ArrayNode);
    Iterator<JsonNode> elementsResult = nextResult2.elements();
    JsonNode nextResult3 = elementsResult.next();
    assertTrue(nextResult3 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult3.iterator();
    JsonNode nextResult4 = iteratorResult3.next();
    assertTrue(nextResult4 instanceof TextNode);
    assertEquals(3, nextResult3.size());
    assertEquals(JsonNodeType.STRING, nextResult4.getNodeType());
    assertFalse(nextResult4.isNull());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertTrue(nextResult4.isTextual());
    assertTrue(iteratorResult3.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addFieldExtensions(List, ObjectNode)}.
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#addFieldExtensions(List, ObjectNode)}
   */
  @Test
  @DisplayName("Test addFieldExtensions(List, ObjectNode)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addFieldExtensions(List, ObjectNode)"})
  void testAddFieldExtensions2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setStringValue("not empty");
    fieldExtension.setExpression("not empty");
    fieldExtension.setFieldName(null);

    ArrayList<FieldExtension> extensions = new ArrayList<>();
    extensions.add(fieldExtension);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode propertiesNode = new ObjectNode(nc);
    propertiesNode.put("servicetaskfields", DoubleNode.valueOf(10.0d));

    // Act
    associationJsonConverter.addFieldExtensions(extensions, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ArrayNode);
    Iterator<JsonNode> elementsResult = nextResult2.elements();
    JsonNode nextResult3 = elementsResult.next();
    Iterator<JsonNode> iteratorResult3 = nextResult3.iterator();
    JsonNode nextResult4 = iteratorResult3.next();
    assertTrue(nextResult4 instanceof NullNode);
    assertTrue(nextResult3 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    assertEquals(JsonNodeType.NULL, nextResult4.getNodeType());
    assertFalse(nextResult4.isTextual());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertTrue(nextResult4.isNull());
    assertTrue(iteratorResult3.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addFieldExtensions(List, ObjectNode)}.
   *
   * <ul>
   *   <li>Given {@link FieldExtension} (default constructor) FieldName is empty string.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#addFieldExtensions(List, ObjectNode)}
   */
  @Test
  @DisplayName(
      "Test addFieldExtensions(List, ObjectNode); given FieldExtension (default constructor) FieldName is empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addFieldExtensions(List, ObjectNode)"})
  void testAddFieldExtensions_givenFieldExtensionFieldNameIsEmptyString() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    FieldExtension fieldExtension = new FieldExtension();
    fieldExtension.setStringValue("not empty");
    fieldExtension.setExpression("not empty");
    fieldExtension.setFieldName("");

    ArrayList<FieldExtension> extensions = new ArrayList<>();
    extensions.add(fieldExtension);
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);

    ObjectNode propertiesNode = new ObjectNode(nc);
    propertiesNode.put("servicetaskfields", DoubleNode.valueOf(10.0d));

    // Act
    associationJsonConverter.addFieldExtensions(extensions, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    JsonNode nextResult = iteratorResult.next();
    Iterator<JsonNode> iteratorResult2 = nextResult.iterator();
    JsonNode nextResult2 = iteratorResult2.next();
    assertTrue(nextResult2 instanceof ArrayNode);
    Iterator<JsonNode> elementsResult = nextResult2.elements();
    JsonNode nextResult3 = elementsResult.next();
    assertTrue(nextResult3 instanceof ObjectNode);
    assertTrue(nextResult instanceof ObjectNode);
    Iterator<JsonNode> iteratorResult3 = nextResult3.iterator();
    JsonNode nextResult4 = iteratorResult3.next();
    assertTrue(nextResult4 instanceof TextNode);
    assertEquals(3, nextResult3.size());
    assertEquals(JsonNodeType.STRING, nextResult4.getNodeType());
    assertFalse(nextResult4.isNull());
    assertFalse(elementsResult.hasNext());
    assertFalse(iteratorResult.hasNext());
    assertFalse(iteratorResult2.hasNext());
    assertTrue(nextResult4.isTextual());
    assertTrue(iteratorResult3.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addFieldExtensions(List, ObjectNode)}.
   *
   * <ul>
   *   <li>Then {@link ObjectNode#ObjectNode(JsonNodeFactory)} with nc is withExactBigDecimals
   *       {@code true} size is one.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#addFieldExtensions(List, ObjectNode)}
   */
  @Test
  @DisplayName(
      "Test addFieldExtensions(List, ObjectNode); then ObjectNode(JsonNodeFactory) with nc is withExactBigDecimals 'true' size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addFieldExtensions(List, ObjectNode)"})
  void testAddFieldExtensions_thenObjectNodeWithNcIsWithExactBigDecimalsTrueSizeIsOne() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayList<FieldExtension> extensions = new ArrayList<>();
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    ObjectNode propertiesNode = new ObjectNode(nc);

    // Act
    associationJsonConverter.addFieldExtensions(extensions, propertiesNode);

    // Assert
    Iterator<JsonNode> iteratorResult = propertiesNode.iterator();
    assertTrue(iteratorResult.next() instanceof ObjectNode);
    assertEquals(1, propertiesNode.size());
    assertFalse(propertiesNode.isEmpty());
    assertFalse(iteratorResult.hasNext());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToFormProperties() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert
    verify(arrayNode).get("formproperties");
    verify(objectNode, atLeast(1)).get("properties");
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToFormProperties2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    ArrayList<FormProperty> formProperties = new ArrayList<>();
    JsonNodeFactory nc = JsonNodeFactory.withExactBigDecimals(true);
    associationJsonConverter.addFormProperties(formProperties, new ObjectNode(nc));

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("As Text");
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.isTextual()).thenReturn(true);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode).isTextual();
    verify(arrayNode).get("formProperties");
    verify(arrayNode2).get("formproperties");
    verify(objectNode, atLeast(1)).get("properties");
    verify(arrayNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToFormProperties3() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("As Text");
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.isTextual()).thenReturn(true);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode).isTextual();
    verify(arrayNode).get("formProperties");
    verify(arrayNode2).get("formproperties");
    verify(objectNode, atLeast(1)).get("properties");
    verify(arrayNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToFormProperties4() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    jsonNodeList.add(new ArrayNode(nf));

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.asText()).thenReturn("As Text");
    when(arrayNode2.isNull()).thenReturn(false);
    when(arrayNode2.isTextual()).thenReturn(true);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode3);

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert
    verify(arrayNode2).isNull();
    verify(arrayNode2).isTextual();
    verify(arrayNode).iterator();
    verify(arrayNode2).get("formProperties");
    verify(arrayNode3).get("formproperties");
    verify(objectNode, atLeast(1)).get("properties");
    verify(arrayNode2, atLeast(1)).asText();
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToFormProperties5() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.asText()).thenReturn("As Text");
    when(arrayNode3.isNull()).thenReturn(false);
    when(arrayNode3.isTextual()).thenReturn(true);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode4);

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert
    verify(arrayNode3).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode2).iterator();
    verify(arrayNode3).get("formProperties");
    verify(arrayNode4).get("formproperties");
    verify(arrayNode).get("id");
    verify(objectNode, atLeast(1)).get("properties");
    verify(arrayNode3, atLeast(1)).asText();
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToFormProperties6() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    BigInteger v = BigInteger.valueOf(42L);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new BigIntegerNode(v));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.asText()).thenReturn("As Text");
    when(arrayNode3.isNull()).thenReturn(false);
    when(arrayNode3.isTextual()).thenReturn(true);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode4);

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert
    verify(arrayNode3).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode2).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3).get("formProperties");
    verify(arrayNode4).get("formproperties");
    verify(objectNode, atLeast(1)).get("properties");
    verify(arrayNode3, atLeast(1)).asText();
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToFormProperties7() throws UnsupportedEncodingException {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any()))
        .thenReturn(new BinaryNode("AXAXAXAX".getBytes("UTF-8")));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.asText()).thenReturn("As Text");
    when(arrayNode3.isNull()).thenReturn(false);
    when(arrayNode3.isTextual()).thenReturn(true);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode4);

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert
    verify(arrayNode3).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode2).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3).get("formProperties");
    verify(arrayNode4).get("formproperties");
    verify(objectNode, atLeast(1)).get("properties");
    verify(arrayNode3, atLeast(1)).asText();
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add valueOf ten.
   *   <li>Then calls {@link ArrayNode#iterator()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToFormProperties(JsonNode, BaseElement); given ArrayList() add valueOf ten; then calls iterator()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToFormProperties_givenArrayListAddValueOfTen_thenCallsIterator() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(DoubleNode.valueOf(10.0d));

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.asText()).thenReturn("As Text");
    when(arrayNode2.isNull()).thenReturn(false);
    when(arrayNode2.isTextual()).thenReturn(true);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode3);

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert
    verify(arrayNode2).isNull();
    verify(arrayNode2).isTextual();
    verify(arrayNode).iterator();
    verify(arrayNode2).get("formProperties");
    verify(arrayNode3).get("formproperties");
    verify(objectNode, atLeast(1)).get("properties");
    verify(arrayNode2, atLeast(1)).asText();
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#asText()} return {@code 42}.
   *   <li>Then calls {@link ArrayNode#asText()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToFormProperties(JsonNode, BaseElement); given ArrayNode asText() return '42'; then calls asText()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToFormProperties_givenArrayNodeAsTextReturn42_thenCallsAsText() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("42");
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.isTextual()).thenReturn(true);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode).isTextual();
    verify(arrayNode2).get("formproperties");
    verify(objectNode, atLeast(1)).get("properties");
    verify(arrayNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#asText()} return empty string.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToFormProperties(JsonNode, BaseElement); given ArrayNode asText() return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToFormProperties_givenArrayNodeAsTextReturnEmptyString() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("");
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.isTextual()).thenReturn(true);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode).isTextual();
    verify(arrayNode).get("formProperties");
    verify(arrayNode2).get("formproperties");
    verify(objectNode, atLeast(1)).get("properties");
    verify(arrayNode).asText();
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#asText()} return {@code null}.
   *   <li>Then calls {@link ArrayNode#asText()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToFormProperties(JsonNode, BaseElement); given ArrayNode asText() return 'null'; then calls asText()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToFormProperties_givenArrayNodeAsTextReturnNull_thenCallsAsText() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn(null);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.isTextual()).thenReturn(true);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode).isTextual();
    verify(arrayNode).get("formProperties");
    verify(arrayNode2).get("formproperties");
    verify(objectNode, atLeast(1)).get("properties");
    verify(arrayNode).asText();
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return False.
   *   <li>Then calls {@link ArrayNode#iterator()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToFormProperties(JsonNode, BaseElement); given ArrayNode get(String) return False; then calls iterator()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToFormProperties_givenArrayNodeGetReturnFalse_thenCallsIterator() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(BooleanNode.getFalse());

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.asText()).thenReturn("As Text");
    when(arrayNode3.isNull()).thenReturn(false);
    when(arrayNode3.isTextual()).thenReturn(true);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode4);

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert
    verify(arrayNode3).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode2).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3).get("formProperties");
    verify(arrayNode4).get("formproperties");
    verify(objectNode, atLeast(1)).get("properties");
    verify(arrayNode3, atLeast(1)).asText();
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToFormProperties(JsonNode, BaseElement); given ArrayNode get(String) return valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToFormProperties_givenArrayNodeGetReturnValueOfTen() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode);

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert
    verify(arrayNode).get("formproperties");
    verify(objectNode, atLeast(1)).get("properties");
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToFormProperties(JsonNode, BaseElement); given ArrayNode get(String) return valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToFormProperties_givenArrayNodeGetReturnValueOfTen2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.asText()).thenReturn("As Text");
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.isTextual()).thenReturn(true);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode).isTextual();
    verify(arrayNode).get("formProperties");
    verify(arrayNode2).get("formproperties");
    verify(objectNode, atLeast(1)).get("properties");
    verify(arrayNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToFormProperties(JsonNode, BaseElement); given ArrayNode get(String) return valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToFormProperties_givenArrayNodeGetReturnValueOfTen3() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.iterator()).thenReturn(jsonNodeList.iterator());

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.asText()).thenReturn("As Text");
    when(arrayNode3.isNull()).thenReturn(false);
    when(arrayNode3.isTextual()).thenReturn(true);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode4);

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert
    verify(arrayNode3).isNull();
    verify(arrayNode3).isTextual();
    verify(arrayNode2).iterator();
    verify(arrayNode, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode3).get("formProperties");
    verify(arrayNode4).get("formproperties");
    verify(objectNode, atLeast(1)).get("properties");
    verify(arrayNode3, atLeast(1)).asText();
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#isNull()} return {@code true}.
   *   <li>Then calls {@link ArrayNode#isNull()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToFormProperties(JsonNode, BaseElement); given ArrayNode isNull() return 'true'; then calls isNull()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToFormProperties_givenArrayNodeIsNullReturnTrue_thenCallsIsNull() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));
    when(arrayNode.isNull()).thenReturn(true);

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode).get("formProperties");
    verify(arrayNode2).get("formproperties");
    verify(objectNode, atLeast(1)).get("properties");
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#isNull()} return {@code true}.
   *   <li>Then calls {@link ArrayNode#iterator()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToFormProperties(JsonNode, BaseElement); given ArrayNode isNull() return 'true'; then calls iterator()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToFormProperties_givenArrayNodeIsNullReturnTrue_thenCallsIterator() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.iterator()).thenReturn(jsonNodeList.iterator());

    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.asText()).thenReturn("As Text");
    when(arrayNode4.isNull()).thenReturn(false);
    when(arrayNode4.isTextual()).thenReturn(true);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.get(Mockito.<String>any())).thenReturn(arrayNode4);

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode5);

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert
    verify(arrayNode4).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(arrayNode4).isTextual();
    verify(arrayNode3).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode4).get("formProperties");
    verify(arrayNode5).get("formproperties");
    verify(objectNode, atLeast(1)).get("properties");
    verify(arrayNode4, atLeast(1)).asText();
    verify(arrayNode, atLeast(1)).asText();
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#isTextual()} return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToFormProperties(JsonNode, BaseElement); given ArrayNode isTextual() return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToFormProperties_givenArrayNodeIsTextualReturnFalse() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(false);
    when(arrayNode.isTextual()).thenReturn(false);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode2);

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert
    verify(arrayNode).isNull();
    verify(arrayNode).isTextual();
    verify(arrayNode).get("formProperties");
    verify(arrayNode2).get("formproperties");
    verify(objectNode, atLeast(1)).get("properties");
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToFormProperties(JsonNode, BaseElement); given ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToFormProperties_givenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode objectNode = mock(ArrayNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(objectNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert
    verify(objectNode, atLeast(1)).get("properties");
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   *
   * <ul>
   *   <li>Given valueOf ten.
   *   <li>When {@link ArrayNode} {@link ArrayNode#get(String)} return valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToFormProperties(JsonNode, BaseElement); given valueOf ten; when ArrayNode get(String) return valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToFormProperties_givenValueOfTen_whenArrayNodeGetReturnValueOfTen() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert
    verify(objectNode, atLeast(1)).get("properties");
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   *
   * <ul>
   *   <li>Then calls {@link ArrayNode#iterator()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName("Test convertJsonToFormProperties(JsonNode, BaseElement); then calls iterator()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToFormProperties_thenCallsIterator() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    when(arrayNode.iterator()).thenReturn(jsonNodeList.iterator());

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.asText()).thenReturn("As Text");
    when(arrayNode2.isNull()).thenReturn(false);
    when(arrayNode2.isTextual()).thenReturn(true);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.get(Mockito.<String>any())).thenReturn(arrayNode2);

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode3);

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, new ActivitiListener());

    // Assert
    verify(arrayNode2).isNull();
    verify(arrayNode2).isTextual();
    verify(arrayNode).iterator();
    verify(arrayNode2).get("formProperties");
    verify(arrayNode3).get("formproperties");
    verify(objectNode, atLeast(1)).get("properties");
    verify(arrayNode2, atLeast(1)).asText();
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   *
   * <ul>
   *   <li>Then {@link StartEvent} (default constructor) FormProperties size is one.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToFormProperties(JsonNode, BaseElement); then StartEvent (default constructor) FormProperties size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToFormProperties_thenStartEventFormPropertiesSizeIsOne() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.iterator()).thenReturn(jsonNodeList.iterator());

    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.asText()).thenReturn("As Text");
    when(arrayNode4.isNull()).thenReturn(false);
    when(arrayNode4.isTextual()).thenReturn(true);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.get(Mockito.<String>any())).thenReturn(arrayNode4);

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode5);
    StartEvent element = new StartEvent();

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, element);

    // Assert
    verify(arrayNode4).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(arrayNode4).isTextual();
    verify(arrayNode3).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode4).get("formProperties");
    verify(arrayNode5).get("formproperties");
    verify(objectNode, atLeast(1)).get("properties");
    verify(arrayNode4, atLeast(1)).asText();
    verify(arrayNode, atLeast(1)).asText();
    List<FormProperty> formProperties = element.getFormProperties();
    assertEquals(1, formProperties.size());
    FormProperty getResult = formProperties.get(0);
    assertEquals("As Text", getResult.getId());
    assertNull(getResult.getDatePattern());
    assertNull(getResult.getDefaultExpression());
    assertNull(getResult.getExpression());
    assertNull(getResult.getName());
    assertNull(getResult.getType());
    assertNull(getResult.getVariable());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertFalse(getResult.isReadable());
    assertFalse(getResult.isRequired());
    assertFalse(getResult.isWriteable());
    assertTrue(getResult.getFormValues().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode, BaseElement)}.
   *
   * <ul>
   *   <li>When {@link UserTask} (default constructor).
   *   <li>Then {@link UserTask} (default constructor) FormProperties size is one.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertJsonToFormProperties(JsonNode,
   * BaseElement)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToFormProperties(JsonNode, BaseElement); when UserTask (default constructor); then UserTask (default constructor) FormProperties size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnJsonConverter.convertJsonToFormProperties(JsonNode, BaseElement)"
  })
  void testConvertJsonToFormProperties_whenUserTask_thenUserTaskFormPropertiesSizeIsOne() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.isNull()).thenReturn(true);
    when(arrayNode.asText()).thenReturn("As Text");

    ArrayNode arrayNode2 = mock(ArrayNode.class);
    when(arrayNode2.get(Mockito.<String>any())).thenReturn(arrayNode);

    ArrayList<JsonNode> jsonNodeList = new ArrayList<>();
    jsonNodeList.add(arrayNode2);

    ArrayNode arrayNode3 = mock(ArrayNode.class);
    when(arrayNode3.iterator()).thenReturn(jsonNodeList.iterator());

    ArrayNode arrayNode4 = mock(ArrayNode.class);
    when(arrayNode4.asText()).thenReturn("As Text");
    when(arrayNode4.isNull()).thenReturn(false);
    when(arrayNode4.isTextual()).thenReturn(true);
    when(arrayNode4.get(Mockito.<String>any())).thenReturn(arrayNode3);

    ArrayNode arrayNode5 = mock(ArrayNode.class);
    when(arrayNode5.get(Mockito.<String>any())).thenReturn(arrayNode4);

    ArrayNode objectNode = mock(ArrayNode.class);
    when(objectNode.get(Mockito.<String>any())).thenReturn(arrayNode5);
    UserTask element = new UserTask();

    // Act
    associationJsonConverter.convertJsonToFormProperties(objectNode, element);

    // Assert
    verify(arrayNode4).isNull();
    verify(arrayNode, atLeast(1)).isNull();
    verify(arrayNode4).isTextual();
    verify(arrayNode3).iterator();
    verify(arrayNode2, atLeast(1)).get(Mockito.<String>any());
    verify(arrayNode4).get("formProperties");
    verify(arrayNode5).get("formproperties");
    verify(objectNode, atLeast(1)).get("properties");
    verify(arrayNode4, atLeast(1)).asText();
    verify(arrayNode, atLeast(1)).asText();
    List<FormProperty> formProperties = element.getFormProperties();
    assertEquals(1, formProperties.size());
    FormProperty getResult = formProperties.get(0);
    assertEquals("As Text", getResult.getId());
    assertNull(getResult.getDatePattern());
    assertNull(getResult.getDefaultExpression());
    assertNull(getResult.getExpression());
    assertNull(getResult.getName());
    assertNull(getResult.getType());
    assertNull(getResult.getVariable());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertFalse(getResult.isReadable());
    assertFalse(getResult.isRequired());
    assertFalse(getResult.isWriteable());
    assertTrue(getResult.getFormValues().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToTimerDefinition(JsonNode, Event)}.
   *
   * <ul>
   *   <li>Then {@link BoundaryEvent} (default constructor) EventDefinitions size is one.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertJsonToTimerDefinition(JsonNode,
   * Event)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToTimerDefinition(JsonNode, Event); then BoundaryEvent (default constructor) EventDefinitions size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.convertJsonToTimerDefinition(JsonNode, Event)"})
  void testConvertJsonToTimerDefinition_thenBoundaryEventEventDefinitionsSizeIsOne() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    DoubleNode objectNode = DoubleNode.valueOf(10.0d);
    BoundaryEvent event = new BoundaryEvent();

    // Act
    associationJsonConverter.convertJsonToTimerDefinition(objectNode, event);

    // Assert
    List<EventDefinition> eventDefinitions = event.getEventDefinitions();
    assertEquals(1, eventDefinitions.size());
    EventDefinition getResult = eventDefinitions.get(0);
    assertTrue(getResult instanceof TimerEventDefinition);
    assertNull(getResult.getId());
    assertNull(((TimerEventDefinition) getResult).getCalendarName());
    assertNull(((TimerEventDefinition) getResult).getEndDate());
    assertNull(((TimerEventDefinition) getResult).getTimeCycle());
    assertNull(((TimerEventDefinition) getResult).getTimeDate());
    assertNull(((TimerEventDefinition) getResult).getTimeDuration());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToTimerDefinition(JsonNode, Event)}.
   *
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertJsonToTimerDefinition(JsonNode,
   * Event)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToTimerDefinition(JsonNode, Event); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.convertJsonToTimerDefinition(JsonNode, Event)"})
  void testConvertJsonToTimerDefinition_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode objectNode = new ArrayNode(nf);
    BoundaryEvent event = new BoundaryEvent();

    // Act
    associationJsonConverter.convertJsonToTimerDefinition(objectNode, event);

    // Assert
    List<EventDefinition> eventDefinitions = event.getEventDefinitions();
    assertEquals(1, eventDefinitions.size());
    EventDefinition getResult = eventDefinitions.get(0);
    assertTrue(getResult instanceof TimerEventDefinition);
    assertNull(getResult.getId());
    assertNull(((TimerEventDefinition) getResult).getCalendarName());
    assertNull(((TimerEventDefinition) getResult).getEndDate());
    assertNull(((TimerEventDefinition) getResult).getTimeCycle());
    assertNull(((TimerEventDefinition) getResult).getTimeDate());
    assertNull(((TimerEventDefinition) getResult).getTimeDuration());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToSignalDefinition(JsonNode, Event)}.
   *
   * <ul>
   *   <li>Then {@link BoundaryEvent} (default constructor) EventDefinitions size is one.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertJsonToSignalDefinition(JsonNode,
   * Event)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToSignalDefinition(JsonNode, Event); then BoundaryEvent (default constructor) EventDefinitions size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.convertJsonToSignalDefinition(JsonNode, Event)"})
  void testConvertJsonToSignalDefinition_thenBoundaryEventEventDefinitionsSizeIsOne() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    DoubleNode objectNode = DoubleNode.valueOf(10.0d);
    BoundaryEvent event = new BoundaryEvent();

    // Act
    associationJsonConverter.convertJsonToSignalDefinition(objectNode, event);

    // Assert
    List<EventDefinition> eventDefinitions = event.getEventDefinitions();
    assertEquals(1, eventDefinitions.size());
    EventDefinition getResult = eventDefinitions.get(0);
    assertTrue(getResult instanceof SignalEventDefinition);
    assertNull(getResult.getId());
    assertNull(((SignalEventDefinition) getResult).getSignalExpression());
    assertNull(((SignalEventDefinition) getResult).getSignalRef());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertFalse(((SignalEventDefinition) getResult).isAsync());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToSignalDefinition(JsonNode, Event)}.
   *
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertJsonToSignalDefinition(JsonNode,
   * Event)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToSignalDefinition(JsonNode, Event); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.convertJsonToSignalDefinition(JsonNode, Event)"})
  void testConvertJsonToSignalDefinition_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode objectNode = new ArrayNode(nf);
    BoundaryEvent event = new BoundaryEvent();

    // Act
    associationJsonConverter.convertJsonToSignalDefinition(objectNode, event);

    // Assert
    List<EventDefinition> eventDefinitions = event.getEventDefinitions();
    assertEquals(1, eventDefinitions.size());
    EventDefinition getResult = eventDefinitions.get(0);
    assertTrue(getResult instanceof SignalEventDefinition);
    assertNull(getResult.getId());
    assertNull(((SignalEventDefinition) getResult).getSignalExpression());
    assertNull(((SignalEventDefinition) getResult).getSignalRef());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertFalse(((SignalEventDefinition) getResult).isAsync());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToMessageDefinition(JsonNode, Event)}.
   *
   * <ul>
   *   <li>Then {@link BoundaryEvent} (default constructor) EventDefinitions size is one.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertJsonToMessageDefinition(JsonNode,
   * Event)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToMessageDefinition(JsonNode, Event); then BoundaryEvent (default constructor) EventDefinitions size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.convertJsonToMessageDefinition(JsonNode, Event)"})
  void testConvertJsonToMessageDefinition_thenBoundaryEventEventDefinitionsSizeIsOne() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    DoubleNode objectNode = DoubleNode.valueOf(10.0d);
    BoundaryEvent event = new BoundaryEvent();

    // Act
    associationJsonConverter.convertJsonToMessageDefinition(objectNode, event);

    // Assert
    List<EventDefinition> eventDefinitions = event.getEventDefinitions();
    assertEquals(1, eventDefinitions.size());
    EventDefinition getResult = eventDefinitions.get(0);
    assertTrue(getResult instanceof MessageEventDefinition);
    assertNull(getResult.getId());
    assertNull(((MessageEventDefinition) getResult).getCorrelationKey());
    assertNull(((MessageEventDefinition) getResult).getMessageExpression());
    assertNull(((MessageEventDefinition) getResult).getMessageRef());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(((MessageEventDefinition) getResult).getFieldExtensions().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToMessageDefinition(JsonNode, Event)}.
   *
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertJsonToMessageDefinition(JsonNode,
   * Event)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToMessageDefinition(JsonNode, Event); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.convertJsonToMessageDefinition(JsonNode, Event)"})
  void testConvertJsonToMessageDefinition_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode objectNode = new ArrayNode(nf);
    BoundaryEvent event = new BoundaryEvent();

    // Act
    associationJsonConverter.convertJsonToMessageDefinition(objectNode, event);

    // Assert
    List<EventDefinition> eventDefinitions = event.getEventDefinitions();
    assertEquals(1, eventDefinitions.size());
    EventDefinition getResult = eventDefinitions.get(0);
    assertTrue(getResult instanceof MessageEventDefinition);
    assertNull(getResult.getId());
    assertNull(((MessageEventDefinition) getResult).getCorrelationKey());
    assertNull(((MessageEventDefinition) getResult).getMessageExpression());
    assertNull(((MessageEventDefinition) getResult).getMessageRef());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(((MessageEventDefinition) getResult).getFieldExtensions().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToErrorDefinition(JsonNode, Event)}.
   *
   * <ul>
   *   <li>Then {@link BoundaryEvent} (default constructor) EventDefinitions size is one.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertJsonToErrorDefinition(JsonNode,
   * Event)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToErrorDefinition(JsonNode, Event); then BoundaryEvent (default constructor) EventDefinitions size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.convertJsonToErrorDefinition(JsonNode, Event)"})
  void testConvertJsonToErrorDefinition_thenBoundaryEventEventDefinitionsSizeIsOne() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    DoubleNode objectNode = DoubleNode.valueOf(10.0d);
    BoundaryEvent event = new BoundaryEvent();

    // Act
    associationJsonConverter.convertJsonToErrorDefinition(objectNode, event);

    // Assert
    List<EventDefinition> eventDefinitions = event.getEventDefinitions();
    assertEquals(1, eventDefinitions.size());
    EventDefinition getResult = eventDefinitions.get(0);
    assertTrue(getResult instanceof ErrorEventDefinition);
    assertNull(getResult.getId());
    assertNull(((ErrorEventDefinition) getResult).getErrorRef());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertJsonToErrorDefinition(JsonNode, Event)}.
   *
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertJsonToErrorDefinition(JsonNode,
   * Event)}
   */
  @Test
  @DisplayName(
      "Test convertJsonToErrorDefinition(JsonNode, Event); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.convertJsonToErrorDefinition(JsonNode, Event)"})
  void testConvertJsonToErrorDefinition_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode objectNode = new ArrayNode(nf);
    BoundaryEvent event = new BoundaryEvent();

    // Act
    associationJsonConverter.convertJsonToErrorDefinition(objectNode, event);

    // Assert
    List<EventDefinition> eventDefinitions = event.getEventDefinitions();
    assertEquals(1, eventDefinitions.size());
    EventDefinition getResult = eventDefinitions.get(0);
    assertTrue(getResult instanceof ErrorEventDefinition);
    assertNull(getResult.getId());
    assertNull(((ErrorEventDefinition) getResult).getErrorRef());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#getValueAsString(String, JsonNode)}.
   *
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#getValueAsString(String, JsonNode)}
   */
  @Test
  @DisplayName(
      "Test getValueAsString(String, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String BaseBpmnJsonConverter.getValueAsString(String, JsonNode)"})
  void testGetValueAsString_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);

    // Act and Assert
    assertNull(associationJsonConverter.getValueAsString("Name", new ArrayNode(nf)));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#getValueAsString(String, JsonNode)}.
   *
   * <ul>
   *   <li>When valueOf ten.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#getValueAsString(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getValueAsString(String, JsonNode); when valueOf ten; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String BaseBpmnJsonConverter.getValueAsString(String, JsonNode)"})
  void testGetValueAsString_whenValueOfTen_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new AssociationJsonConverter().getValueAsString("Name", DoubleNode.valueOf(10.0d)));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#getValueAsBoolean(String, JsonNode)}.
   *
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#getValueAsBoolean(String, JsonNode)}
   */
  @Test
  @DisplayName(
      "Test getValueAsBoolean(String, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BaseBpmnJsonConverter.getValueAsBoolean(String, JsonNode)"})
  void testGetValueAsBoolean_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);

    // Act and Assert
    assertFalse(associationJsonConverter.getValueAsBoolean("Name", new ArrayNode(nf)));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#getValueAsBoolean(String, JsonNode)}.
   *
   * <ul>
   *   <li>When valueOf ten.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#getValueAsBoolean(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getValueAsBoolean(String, JsonNode); when valueOf ten; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BaseBpmnJsonConverter.getValueAsBoolean(String, JsonNode)"})
  void testGetValueAsBoolean_whenValueOfTen_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(
        new AssociationJsonConverter().getValueAsBoolean("Name", DoubleNode.valueOf(10.0d)));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#getValueAsList(String, JsonNode)}.
   *
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code
   *       true}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#getValueAsList(String, JsonNode)}
   */
  @Test
  @DisplayName(
      "Test getValueAsList(String, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List BaseBpmnJsonConverter.getValueAsList(String, JsonNode)"})
  void testGetValueAsList_whenArrayNodeWithNfIsWithExactBigDecimalsTrue_thenReturnEmpty() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);

    // Act and Assert
    assertTrue(associationJsonConverter.getValueAsList("Name", new ArrayNode(nf)).isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#getValueAsList(String, JsonNode)}.
   *
   * <ul>
   *   <li>When valueOf ten.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#getValueAsList(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getValueAsList(String, JsonNode); when valueOf ten; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List BaseBpmnJsonConverter.getValueAsList(String, JsonNode)"})
  void testGetValueAsList_whenValueOfTen_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(
        new AssociationJsonConverter().getValueAsList("Name", DoubleNode.valueOf(10.0d)).isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)} with {@code name},
   * {@code elementNode}, {@code task}.
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, JsonNode, ServiceTask) with 'name', 'elementNode', 'task'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, JsonNode, ServiceTask)"})
  void testAddFieldWithNameElementNodeTask() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode elementNode = new ArrayNode(nf);
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("CamelTask", elementNode, task);

    // Assert that nothing has changed
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)} with {@code name},
   * {@code elementNode}, {@code task}.
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, JsonNode, ServiceTask) with 'name', 'elementNode', 'task'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, JsonNode, ServiceTask)"})
  void testAddFieldWithNameElementNodeTask2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode elementNode = mock(ArrayNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(elementNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("CamelTask", elementNode, task);

    // Assert that nothing has changed
    verify(elementNode, atLeast(1)).get("properties");
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)} with {@code name},
   * {@code elementNode}, {@code task}.
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, JsonNode, ServiceTask) with 'name', 'elementNode', 'task'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, JsonNode, ServiceTask)"})
  void testAddFieldWithNameElementNodeTask3() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("CamelTask", elementNode, task);

    // Assert
    verify(arrayNode).get("CamelTask");
    verify(elementNode, atLeast(1)).get("properties");
    List<FieldExtension> fieldExtensions = task.getFieldExtensions();
    assertEquals(1, fieldExtensions.size());
    FieldExtension getResult = fieldExtensions.get(0);
    assertEquals("10.0", getResult.getStringValue());
    assertEquals("k", getResult.getFieldName());
    assertNull(getResult.getId());
    assertNull(getResult.getExpression());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)} with {@code name},
   * {@code elementNode}, {@code task}.
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, JsonNode, ServiceTask) with 'name', 'elementNode', 'task'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, JsonNode, ServiceTask)"})
  void testAddFieldWithNameElementNodeTask4() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("CamelTask", elementNode, task);

    // Assert that nothing has changed
    verify(arrayNode).get("CamelTask");
    verify(elementNode, atLeast(1)).get("properties");
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)} with {@code name},
   * {@code elementNode}, {@code task}.
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, JsonNode, ServiceTask) with 'name', 'elementNode', 'task'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, JsonNode, ServiceTask)"})
  void testAddFieldWithNameElementNodeTask5() throws UnsupportedEncodingException {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any()))
        .thenReturn(new BinaryNode("A\bA\bA\bA\b".getBytes("UTF-8")));

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("CamelTask", elementNode, task);

    // Assert
    verify(arrayNode).get("CamelTask");
    verify(elementNode, atLeast(1)).get("properties");
    List<FieldExtension> fieldExtensions = task.getFieldExtensions();
    assertEquals(1, fieldExtensions.size());
    FieldExtension getResult = fieldExtensions.get(0);
    assertEquals("QQhBCEEIQQg=", getResult.getStringValue());
    assertEquals("k", getResult.getFieldName());
    assertNull(getResult.getId());
    assertNull(getResult.getExpression());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)} with {@code name},
   * {@code elementNode}, {@code task}.
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, JsonNode, ServiceTask) with 'name', 'elementNode', 'task'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, JsonNode, ServiceTask)"})
  void testAddFieldWithNameElementNodeTask6() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("${");

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(jsonNode);

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("CamelTask", elementNode, task);

    // Assert
    verify(jsonNode, atLeast(1)).asText();
    verify(arrayNode).get("CamelTask");
    verify(elementNode, atLeast(1)).get("properties");
    List<FieldExtension> fieldExtensions = task.getFieldExtensions();
    assertEquals(1, fieldExtensions.size());
    FieldExtension getResult = fieldExtensions.get(0);
    assertEquals("${", getResult.getStringValue());
    assertEquals("k", getResult.getFieldName());
    assertNull(getResult.getId());
    assertNull(getResult.getExpression());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)} with {@code name},
   * {@code elementNode}, {@code task}.
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName("Test addField(String, JsonNode, ServiceTask) with 'name', 'elementNode', 'task'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, JsonNode, ServiceTask)"})
  void testAddFieldWithNameElementNodeTask7() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("#{");

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(jsonNode);

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("CamelTask", elementNode, task);

    // Assert
    verify(jsonNode, atLeast(1)).asText();
    verify(arrayNode).get("CamelTask");
    verify(elementNode, atLeast(1)).get("properties");
    List<FieldExtension> fieldExtensions = task.getFieldExtensions();
    assertEquals(1, fieldExtensions.size());
    FieldExtension getResult = fieldExtensions.get(0);
    assertEquals("#{", getResult.getStringValue());
    assertEquals("k", getResult.getFieldName());
    assertNull(getResult.getId());
    assertNull(getResult.getExpression());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)} with {@code name},
   * {@code elementNode}, {@code task}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName(
      "Test addField(String, JsonNode, ServiceTask) with 'name', 'elementNode', 'task'; given ArrayNode get(String) return Instance")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, JsonNode, ServiceTask)"})
  void testAddFieldWithNameElementNodeTask_givenArrayNodeGetReturnInstance() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("CamelTask", elementNode, task);

    // Assert that nothing has changed
    verify(arrayNode).get("CamelTask");
    verify(elementNode, atLeast(1)).get("properties");
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)} with {@code name},
   * {@code elementNode}, {@code task}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName(
      "Test addField(String, JsonNode, ServiceTask) with 'name', 'elementNode', 'task'; given ArrayNode get(String) return Instance")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, JsonNode, ServiceTask)"})
  void testAddFieldWithNameElementNodeTask_givenArrayNodeGetReturnInstance2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(NullNode.getInstance());

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("CamelTask", elementNode, task);

    // Assert that nothing has changed
    verify(arrayNode).get("CamelTask");
    verify(elementNode, atLeast(1)).get("properties");
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)} with {@code name},
   * {@code elementNode}, {@code task}.
   *
   * <ul>
   *   <li>Given valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName(
      "Test addField(String, JsonNode, ServiceTask) with 'name', 'elementNode', 'task'; given valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, JsonNode, ServiceTask)"})
  void testAddFieldWithNameElementNodeTask_givenValueOfTen() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("CamelTask", elementNode, task);

    // Assert that nothing has changed
    verify(elementNode, atLeast(1)).get("properties");
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)} with {@code name},
   * {@code elementNode}, {@code task}.
   *
   * <ul>
   *   <li>When valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#addField(String, JsonNode, ServiceTask)}
   */
  @Test
  @DisplayName(
      "Test addField(String, JsonNode, ServiceTask) with 'name', 'elementNode', 'task'; when valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, JsonNode, ServiceTask)"})
  void testAddFieldWithNameElementNodeTask_whenValueOfTen() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    DoubleNode elementNode = DoubleNode.valueOf(10.0d);
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("CamelTask", elementNode, task);

    // Assert that nothing has changed
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)} with {@code
   * name}, {@code propertyName}, {@code elementNode}, {@code task}.
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#addField(String, String, JsonNode,
   * ServiceTask)}
   */
  @Test
  @DisplayName(
      "Test addField(String, String, JsonNode, ServiceTask) with 'name', 'propertyName', 'elementNode', 'task'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, String, JsonNode, ServiceTask)"})
  void testAddFieldWithNamePropertyNameElementNodeTask() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    ArrayNode elementNode = new ArrayNode(nf);
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("Name", "Property Name", elementNode, task);

    // Assert that nothing has changed
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)} with {@code
   * name}, {@code propertyName}, {@code elementNode}, {@code task}.
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#addField(String, String, JsonNode,
   * ServiceTask)}
   */
  @Test
  @DisplayName(
      "Test addField(String, String, JsonNode, ServiceTask) with 'name', 'propertyName', 'elementNode', 'task'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, String, JsonNode, ServiceTask)"})
  void testAddFieldWithNamePropertyNameElementNodeTask2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode elementNode = mock(ArrayNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(elementNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("Name", "Property Name", elementNode, task);

    // Assert that nothing has changed
    verify(elementNode, atLeast(1)).get("properties");
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)} with {@code
   * name}, {@code propertyName}, {@code elementNode}, {@code task}.
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#addField(String, String, JsonNode,
   * ServiceTask)}
   */
  @Test
  @DisplayName(
      "Test addField(String, String, JsonNode, ServiceTask) with 'name', 'propertyName', 'elementNode', 'task'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, String, JsonNode, ServiceTask)"})
  void testAddFieldWithNamePropertyNameElementNodeTask3() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("Name", "Property Name", elementNode, task);

    // Assert
    verify(arrayNode).get("Property Name");
    verify(elementNode, atLeast(1)).get("properties");
    List<FieldExtension> fieldExtensions = task.getFieldExtensions();
    assertEquals(1, fieldExtensions.size());
    FieldExtension getResult = fieldExtensions.get(0);
    assertEquals("10.0", getResult.getStringValue());
    assertEquals("Name", getResult.getFieldName());
    assertNull(getResult.getId());
    assertNull(getResult.getExpression());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)} with {@code
   * name}, {@code propertyName}, {@code elementNode}, {@code task}.
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#addField(String, String, JsonNode,
   * ServiceTask)}
   */
  @Test
  @DisplayName(
      "Test addField(String, String, JsonNode, ServiceTask) with 'name', 'propertyName', 'elementNode', 'task'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, String, JsonNode, ServiceTask)"})
  void testAddFieldWithNamePropertyNameElementNodeTask4() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(new ArrayNode(nf));

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("Name", "Property Name", elementNode, task);

    // Assert that nothing has changed
    verify(arrayNode).get("Property Name");
    verify(elementNode, atLeast(1)).get("properties");
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)} with {@code
   * name}, {@code propertyName}, {@code elementNode}, {@code task}.
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#addField(String, String, JsonNode,
   * ServiceTask)}
   */
  @Test
  @DisplayName(
      "Test addField(String, String, JsonNode, ServiceTask) with 'name', 'propertyName', 'elementNode', 'task'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, String, JsonNode, ServiceTask)"})
  void testAddFieldWithNamePropertyNameElementNodeTask5() throws UnsupportedEncodingException {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any()))
        .thenReturn(new BinaryNode("AXAXAXAX".getBytes("UTF-8")));

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("Name", "Property Name", elementNode, task);

    // Assert
    verify(arrayNode).get("Property Name");
    verify(elementNode, atLeast(1)).get("properties");
    List<FieldExtension> fieldExtensions = task.getFieldExtensions();
    assertEquals(1, fieldExtensions.size());
    FieldExtension getResult = fieldExtensions.get(0);
    assertEquals("Name", getResult.getFieldName());
    assertEquals("QVhBWEFYQVg=", getResult.getStringValue());
    assertNull(getResult.getId());
    assertNull(getResult.getExpression());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)} with {@code
   * name}, {@code propertyName}, {@code elementNode}, {@code task}.
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#addField(String, String, JsonNode,
   * ServiceTask)}
   */
  @Test
  @DisplayName(
      "Test addField(String, String, JsonNode, ServiceTask) with 'name', 'propertyName', 'elementNode', 'task'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, String, JsonNode, ServiceTask)"})
  void testAddFieldWithNamePropertyNameElementNodeTask6() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("${");

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(jsonNode);

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("Name", "Property Name", elementNode, task);

    // Assert
    verify(jsonNode, atLeast(1)).asText();
    verify(arrayNode).get("Property Name");
    verify(elementNode, atLeast(1)).get("properties");
    List<FieldExtension> fieldExtensions = task.getFieldExtensions();
    assertEquals(1, fieldExtensions.size());
    FieldExtension getResult = fieldExtensions.get(0);
    assertEquals("${", getResult.getStringValue());
    assertEquals("Name", getResult.getFieldName());
    assertNull(getResult.getId());
    assertNull(getResult.getExpression());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)} with {@code
   * name}, {@code propertyName}, {@code elementNode}, {@code task}.
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#addField(String, String, JsonNode,
   * ServiceTask)}
   */
  @Test
  @DisplayName(
      "Test addField(String, String, JsonNode, ServiceTask) with 'name', 'propertyName', 'elementNode', 'task'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, String, JsonNode, ServiceTask)"})
  void testAddFieldWithNamePropertyNameElementNodeTask7() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    JsonNode jsonNode = mock(JsonNode.class);
    when(jsonNode.asText()).thenReturn("#{");

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(jsonNode);

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("Name", "Property Name", elementNode, task);

    // Assert
    verify(jsonNode, atLeast(1)).asText();
    verify(arrayNode).get("Property Name");
    verify(elementNode, atLeast(1)).get("properties");
    List<FieldExtension> fieldExtensions = task.getFieldExtensions();
    assertEquals(1, fieldExtensions.size());
    FieldExtension getResult = fieldExtensions.get(0);
    assertEquals("#{", getResult.getStringValue());
    assertEquals("Name", getResult.getFieldName());
    assertNull(getResult.getId());
    assertNull(getResult.getExpression());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)} with {@code
   * name}, {@code propertyName}, {@code elementNode}, {@code task}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#addField(String, String, JsonNode,
   * ServiceTask)}
   */
  @Test
  @DisplayName(
      "Test addField(String, String, JsonNode, ServiceTask) with 'name', 'propertyName', 'elementNode', 'task'; given ArrayNode get(String) return Instance")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, String, JsonNode, ServiceTask)"})
  void testAddFieldWithNamePropertyNameElementNodeTask_givenArrayNodeGetReturnInstance() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(MissingNode.getInstance());

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("Name", "Property Name", elementNode, task);

    // Assert that nothing has changed
    verify(arrayNode).get("Property Name");
    verify(elementNode, atLeast(1)).get("properties");
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)} with {@code
   * name}, {@code propertyName}, {@code elementNode}, {@code task}.
   *
   * <ul>
   *   <li>Given {@link ArrayNode} {@link ArrayNode#get(String)} return Instance.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#addField(String, String, JsonNode,
   * ServiceTask)}
   */
  @Test
  @DisplayName(
      "Test addField(String, String, JsonNode, ServiceTask) with 'name', 'propertyName', 'elementNode', 'task'; given ArrayNode get(String) return Instance")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, String, JsonNode, ServiceTask)"})
  void testAddFieldWithNamePropertyNameElementNodeTask_givenArrayNodeGetReturnInstance2() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode arrayNode = mock(ArrayNode.class);
    when(arrayNode.get(Mockito.<String>any())).thenReturn(NullNode.getInstance());

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(arrayNode);
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("Name", "Property Name", elementNode, task);

    // Assert that nothing has changed
    verify(arrayNode).get("Property Name");
    verify(elementNode, atLeast(1)).get("properties");
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)} with {@code
   * name}, {@code propertyName}, {@code elementNode}, {@code task}.
   *
   * <ul>
   *   <li>Given valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#addField(String, String, JsonNode,
   * ServiceTask)}
   */
  @Test
  @DisplayName(
      "Test addField(String, String, JsonNode, ServiceTask) with 'name', 'propertyName', 'elementNode', 'task'; given valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, String, JsonNode, ServiceTask)"})
  void testAddFieldWithNamePropertyNameElementNodeTask_givenValueOfTen() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayNode elementNode = mock(ArrayNode.class);
    when(elementNode.get(Mockito.<String>any())).thenReturn(DoubleNode.valueOf(10.0d));
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("Name", "Property Name", elementNode, task);

    // Assert that nothing has changed
    verify(elementNode, atLeast(1)).get("properties");
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#addField(String, String, JsonNode, ServiceTask)} with {@code
   * name}, {@code propertyName}, {@code elementNode}, {@code task}.
   *
   * <ul>
   *   <li>When valueOf ten.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#addField(String, String, JsonNode,
   * ServiceTask)}
   */
  @Test
  @DisplayName(
      "Test addField(String, String, JsonNode, ServiceTask) with 'name', 'propertyName', 'elementNode', 'task'; when valueOf ten")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BaseBpmnJsonConverter.addField(String, String, JsonNode, ServiceTask)"})
  void testAddFieldWithNamePropertyNameElementNodeTask_whenValueOfTen() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    DoubleNode elementNode = DoubleNode.valueOf(10.0d);
    ServiceTask task = new ServiceTask();

    // Act
    associationJsonConverter.addField("Name", "Property Name", elementNode, task);

    // Assert that nothing has changed
    assertTrue(task.getFieldExtensions().isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#getPropertyValueAsString(String, JsonNode)}.
   *
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#getPropertyValueAsString(String, JsonNode)}
   */
  @Test
  @DisplayName(
      "Test getPropertyValueAsString(String, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String BaseBpmnJsonConverter.getPropertyValueAsString(String, JsonNode)"})
  void testGetPropertyValueAsString_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);

    // Act and Assert
    assertNull(associationJsonConverter.getPropertyValueAsString("Name", new ArrayNode(nf)));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#getPropertyValueAsString(String, JsonNode)}.
   *
   * <ul>
   *   <li>When valueOf ten.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#getPropertyValueAsString(String, JsonNode)}
   */
  @Test
  @DisplayName(
      "Test getPropertyValueAsString(String, JsonNode); when valueOf ten; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String BaseBpmnJsonConverter.getPropertyValueAsString(String, JsonNode)"})
  void testGetPropertyValueAsString_whenValueOfTen_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(
        new AssociationJsonConverter().getPropertyValueAsString("Name", DoubleNode.valueOf(10.0d)));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#getPropertyValueAsBoolean(String, JsonNode)}.
   *
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#getPropertyValueAsBoolean(String, JsonNode)}
   */
  @Test
  @DisplayName(
      "Test getPropertyValueAsBoolean(String, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BaseBpmnJsonConverter.getPropertyValueAsBoolean(String, JsonNode)"})
  void testGetPropertyValueAsBoolean_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);

    // Act and Assert
    assertFalse(associationJsonConverter.getPropertyValueAsBoolean("Name", new ArrayNode(nf)));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#getPropertyValueAsBoolean(String, JsonNode)}.
   *
   * <ul>
   *   <li>When valueOf ten.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#getPropertyValueAsBoolean(String, JsonNode)}
   */
  @Test
  @DisplayName(
      "Test getPropertyValueAsBoolean(String, JsonNode); when valueOf ten; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean BaseBpmnJsonConverter.getPropertyValueAsBoolean(String, JsonNode)"})
  void testGetPropertyValueAsBoolean_whenValueOfTen_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(
        new AssociationJsonConverter()
            .getPropertyValueAsBoolean("Name", DoubleNode.valueOf(10.0d)));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#getPropertyValueAsList(String, JsonNode)}.
   *
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code
   *       true}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#getPropertyValueAsList(String, JsonNode)}
   */
  @Test
  @DisplayName(
      "Test getPropertyValueAsList(String, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List BaseBpmnJsonConverter.getPropertyValueAsList(String, JsonNode)"})
  void testGetPropertyValueAsList_whenArrayNodeWithNfIsWithExactBigDecimalsTrue() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);

    // Act and Assert
    assertTrue(
        associationJsonConverter.getPropertyValueAsList("Name", new ArrayNode(nf)).isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#getPropertyValueAsList(String, JsonNode)}.
   *
   * <ul>
   *   <li>When valueOf ten.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#getPropertyValueAsList(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getPropertyValueAsList(String, JsonNode); when valueOf ten; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List BaseBpmnJsonConverter.getPropertyValueAsList(String, JsonNode)"})
  void testGetPropertyValueAsList_whenValueOfTen_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(
        new AssociationJsonConverter()
            .getPropertyValueAsList("Name", DoubleNode.valueOf(10.0d))
            .isEmpty());
  }

  /**
   * Test {@link BaseBpmnJsonConverter#getProperty(String, JsonNode)}.
   *
   * <ul>
   *   <li>When {@link ArrayNode#ArrayNode(JsonNodeFactory)} with nf is withExactBigDecimals {@code
   *       true}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#getProperty(String, JsonNode)}
   */
  @Test
  @DisplayName(
      "Test getProperty(String, JsonNode); when ArrayNode(JsonNodeFactory) with nf is withExactBigDecimals 'true'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"JsonNode BaseBpmnJsonConverter.getProperty(String, JsonNode)"})
  void testGetProperty_whenArrayNodeWithNfIsWithExactBigDecimalsTrue_thenReturnNull() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();
    JsonNodeFactory nf = JsonNodeFactory.withExactBigDecimals(true);

    // Act and Assert
    assertNull(associationJsonConverter.getProperty("Name", new ArrayNode(nf)));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#getProperty(String, JsonNode)}.
   *
   * <ul>
   *   <li>When valueOf ten.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#getProperty(String, JsonNode)}
   */
  @Test
  @DisplayName("Test getProperty(String, JsonNode); when valueOf ten; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"JsonNode BaseBpmnJsonConverter.getProperty(String, JsonNode)"})
  void testGetProperty_whenValueOfTen_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new AssociationJsonConverter().getProperty("Name", DoubleNode.valueOf(10.0d)));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertListToCommaSeparatedString(List)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.
   *   <li>Then return {@code 42,foo}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertListToCommaSeparatedString(List)}
   */
  @Test
  @DisplayName(
      "Test convertListToCommaSeparatedString(List); given '42'; when ArrayList() add '42'; then return '42,foo'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String BaseBpmnJsonConverter.convertListToCommaSeparatedString(List)"})
  void testConvertListToCommaSeparatedString_given42_whenArrayListAdd42_thenReturn42Foo() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("42");
    stringList.add("foo");

    // Act and Assert
    assertEquals("42,foo", associationJsonConverter.convertListToCommaSeparatedString(stringList));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertListToCommaSeparatedString(List)}.
   *
   * <ul>
   *   <li>Given {@code String List}.
   *   <li>Then return {@code String List}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertListToCommaSeparatedString(List)}
   */
  @Test
  @DisplayName(
      "Test convertListToCommaSeparatedString(List); given 'String List'; then return 'String List'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String BaseBpmnJsonConverter.convertListToCommaSeparatedString(List)"})
  void testConvertListToCommaSeparatedString_givenStringList_thenReturnStringList() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("String List");

    // Act and Assert
    assertEquals(
        "String List", associationJsonConverter.convertListToCommaSeparatedString(stringList));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertListToCommaSeparatedString(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertListToCommaSeparatedString(List)}
   */
  @Test
  @DisplayName("Test convertListToCommaSeparatedString(List); when ArrayList(); then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String BaseBpmnJsonConverter.convertListToCommaSeparatedString(List)"})
  void testConvertListToCommaSeparatedString_whenArrayList_thenReturnNull() {
    // Arrange
    AssociationJsonConverter associationJsonConverter = new AssociationJsonConverter();

    // Act and Assert
    assertNull(associationJsonConverter.convertListToCommaSeparatedString(new ArrayList<>()));
  }

  /**
   * Test {@link BaseBpmnJsonConverter#convertListToCommaSeparatedString(List)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnJsonConverter#convertListToCommaSeparatedString(List)}
   */
  @Test
  @DisplayName("Test convertListToCommaSeparatedString(List); when 'null'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String BaseBpmnJsonConverter.convertListToCommaSeparatedString(List)"})
  void testConvertListToCommaSeparatedString_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new AssociationJsonConverter().convertListToCommaSeparatedString(null));
  }
}
